package org.arkhamnetwork.filler.config;

import lombok.Data;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matt on 15/08/2017.
 */
@Data
public class MenuItem {

    private Material displayItem;
    private String displayName;
    private int displaySlot;

    private List<String> lore;

    public ItemStack toItem() {
        ItemStack item = new ItemStack(displayItem, 1);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName));

        List<String> l = new ArrayList<>();

        for (String s : lore) {
            l.add(ChatColor.translateAlternateColorCodes('&', s));
        }
        meta.setLore(l);
        item.setItemMeta(meta);
        return item;
    }
}
