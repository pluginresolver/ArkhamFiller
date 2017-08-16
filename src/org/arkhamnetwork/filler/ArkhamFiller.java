package org.arkhamnetwork.filler;

import com.redmancometh.redcore.RedPlugin;
import com.redmancometh.redcore.config.ConfigManager;
import com.redmancometh.redcore.mediators.NulledObjectManager;
import com.redmancometh.redcore.mediators.ObjectManager;
import lombok.Getter;
import org.arkhamnetwork.arkhammenus.MenuManager;
import org.arkhamnetwork.arkhammenus.Menus;
import org.arkhamnetwork.filler.command.FillerCommand;
import org.arkhamnetwork.filler.config.SettingsConfig;
import org.arkhamnetwork.filler.menu.MainMenu;
import org.arkhamnetwork.filler.menu.SingleFillMenu;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Matt on 15/08/2017.
 */
public class ArkhamFiller extends JavaPlugin implements RedPlugin {

    private List<Class> classList = new CopyOnWriteArrayList<>();

    private SessionFactory factory;

    /*
        Configs
     */

    @Getter
    private MenuManager menuManager;

    private ConfigManager<SettingsConfig> cfg;

    @Getter
    private MainMenu mainMenu;

    @Override
    public void onEnable() {
        super.onEnable();

        cfg = new ConfigManager<>("settings.json", SettingsConfig.class);

        cfg.init(this);

        menuManager = Menus.getInstance().getMenuManager();

        menuManager.addMenu(mainMenu = new MainMenu());

        getCommand("filler").setExecutor(new FillerCommand());
    }

    public void reloadConfigs() {
        cfg.init(this);
    }

    public ConfigManager<SettingsConfig> getCfg() {
        return cfg;
    }

    public static ArkhamFiller getInstance() {
        return (ArkhamFiller) Bukkit.getPluginManager().getPlugin("ArkhamFiller");
    }

    public List<Class> getMappedClasses() {
        return classList;
    }

    public JavaPlugin getBukkitPlugin() {
        return this;
    }

    public ObjectManager getManager() {
        return new NulledObjectManager();
    }

    public SessionFactory getInternalFactory() {
        return factory;
    }

    public void setInternalFactory(SessionFactory sessionFactory) {
        this.factory = sessionFactory;
    }
}
