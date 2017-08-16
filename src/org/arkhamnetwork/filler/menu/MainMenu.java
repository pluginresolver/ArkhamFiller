package org.arkhamnetwork.filler.menu;

import org.arkhamnetwork.arkhammenus.absraction.Menu;
import org.arkhamnetwork.arkhammenus.menus.ClickType;
import org.arkhamnetwork.arkhammenus.menus.MenuButton;
import org.arkhamnetwork.filler.ArkhamFiller;
import org.arkhamnetwork.filler.config.MenuItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Matt on 15/08/2017.
 */
public class MainMenu extends Menu {

    List<MenuItem> items = ArkhamFiller.getInstance().getCfg().getCurrentConfig().getMenuItems();

    public MainMenu() {
        super(ArkhamFiller.getInstance().getCfg().getCurrentConfig().getMasterGUITitle(), 9);

        setConstructInventory((player) -> {
            Inventory inv = Bukkit.createInventory(null, getSize(), ArkhamFiller.getInstance().getCfg().getCurrentConfig().getMasterGUITitle());


            for (int i = 0; i < items.size(); i++) {

                ItemStack item = items.get(i).toItem();

                inv.setItem(i, item);
            }
            return inv;
        });

        items.forEach((menuItem) -> {

            System.out.println("Button");

            MenuButton button = new MenuButton();

            ItemStack item = menuItem.toItem();

            int i = menuItem.getDisplaySlot();

            button.setClickAction((click, pl) -> {

                System.out.println(click.name());

                if (click == ClickType.LEFT) {

                    //Individual Fill

                    Material type = item.getType();

                    Location playerLocation = pl.getLocation();

                    pl.closeInventory();

                    new BukkitRunnable() {
                        public void run() {
                            pl.openInventory(ArkhamFiller.getInstance().getSingleFillMenu().getConstructInventory().apply(pl, new ItemStack(type)));
                        }
                    }.runTaskLater(ArkhamFiller.getInstance(), 1L);

                } else if (click == ClickType.RIGHT) {
                    //Fill all
                }

            });

            setButton(i, button);
        });

    }
}
