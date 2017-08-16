package org.arkhamnetwork.filler.menu;

import org.arkhamnetwork.arkhammenus.absraction.Menu;
import org.arkhamnetwork.arkhammenus.absraction.TypedMenu;
import org.arkhamnetwork.arkhammenus.menus.MenuButton;
import org.arkhamnetwork.arkhammenus.menus.TypedMenuButton;
import org.arkhamnetwork.arkhammenus.menus.TypedSelector;
import org.arkhamnetwork.filler.ArkhamFiller;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.ContainerBlock;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matt on 15/08/2017.
 */
public class SingleFillMenu extends TypedMenu<ItemStack> {

    private List<ItemStack> items = new ArrayList<>();

    public SingleFillMenu() {
        super("Filler");

        setConstructInventory((player, typed) -> {
            Inventory inv = Bukkit.createInventory(null, 54, "Filler");

            Location playerLocation = player.getLocation();

            double radius = ArkhamFiller.getInstance().getCfg().getCurrentConfig().getRange();

            Chunk c = playerLocation.getChunk();
            int count = 0;

            for (BlockState s : c.getTileEntities()) {

                if (s.getType() == typed.getType()) {


                    ItemStack i = typed.clone();
                    ItemMeta meta = i.getItemMeta();

                    meta.setDisplayName("X: " + s.getX() + " Y: " + s.getY() + " Z: " + s.getZ());
                    i.setItemMeta(meta);

                    inv.setItem(count, i);
                    items.add(i);
                    count++;

                    if (count > 54) {
                        break;
                    }
                }
            }

            return inv;
        });
        final int[] count = {0};

        items.forEach(i -> {

            System.out.println("Constructing button " + i);

            TypedMenuButton<ItemStack> button = new TypedMenuButton<>();

            button.setAction((click, item, player) -> {
                System.out.println("Action");
                Location loc = getLocationFromString(player, i.getItemMeta().getDisplayName());

                System.out.println(loc.getBlockX());
                System.out.println(loc.getBlockY());
                System.out.println(loc.getBlockZ());


                Block block = player.getWorld().getBlockAt(loc);

                BlockState state = block.getState();

                System.out.println("Detecting click on typed menu");

                if (state instanceof ContainerBlock) {

                    ContainerBlock container = (ContainerBlock) block;

                    player.closeInventory();

                    new BukkitRunnable() {
                        public void run() {
                            player.openInventory(container.getInventory());
                        }
                    }.runTaskLater(ArkhamFiller.getInstance(), 1L);
                }

            });
            setButton(count[0], button);

            count[0]++;
        });
    }

    private Location getLocationFromString(Player player, String name) {

        //X: x Y: y Z: z
        String[] split = name.split(" ");
        int x = Integer.parseInt(split[1]);
        int y = Integer.parseInt(split[3]);
        int z = Integer.parseInt(split[5]);

        return new Location(player.getWorld(), x, y, z);
    }

    @Override
    public TypedSelector getSelector() {
        return new TypedSelector();
    }
}
