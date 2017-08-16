package org.arkhamnetwork.filler.menu;

import org.arkhamnetwork.arkhammenus.absraction.Menu;
import org.arkhamnetwork.arkhammenus.menus.MenuButton;
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
public class SingleFillMenu extends Menu {

    private ItemStack item;

    private List<ItemStack> items = new ArrayList<>();

    public SingleFillMenu(ItemStack item) {
        super("Fill " + item.getItemMeta().getDisplayName());
        this.item = item;

        setConstructInventory((player -> {
            Inventory inv = Bukkit.createInventory(null, 54, "Fill " + item.getItemMeta().getDisplayName());

            Location playerLocation = player.getLocation();

            double radius = ArkhamFiller.getInstance().getCfg().getCurrentConfig().getRange();

            Chunk c = playerLocation.getChunk();
            int count = 0;

            for (BlockState s : c.getTileEntities()) {

                if (s.getType() == item.getType()) {


                    ItemStack i = item.clone();
                    ItemMeta meta = i.getItemMeta();

                    meta.setDisplayName("X: " + s.getX() + " Y: " + s.getY() + " Z: " + s.getZ());
                    i.setItemMeta(meta);

                    inv.setItem(count, i);

                    count++;

                    if (count > 54) {
                        break;
                    }
                }
            }

            return inv;
        }));

        items.forEach(i -> {
            int count = 0;


            MenuButton button = new MenuButton();

            button.setClickAction((click, player) -> {
                Location loc = getLocationFromString(player, i.getItemMeta().getDisplayName());

                System.out.println(loc.getBlockX());
                System.out.println(loc.getBlockY());
                System.out.println(loc.getBlockZ());


                Block block = player.getWorld().getBlockAt(loc);

                if (block instanceof ContainerBlock) {

                    ContainerBlock container = (ContainerBlock) block;

                    player.closeInventory();

                    new BukkitRunnable() {
                        public void run() {
                            player.openInventory(container.getInventory());
                        }
                    }.runTaskLater(ArkhamFiller.getInstance(), 1L);
                }

            });
            setButton(count, button);

            count++;
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
}
