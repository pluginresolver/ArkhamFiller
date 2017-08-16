package org.arkhamnetwork.filler.config;

import lombok.Data;
import org.bukkit.Material;

/**
 * Created by Matt on 15/08/2017.
 */
@Data
public class MenuItem {

    private Material displayItem;
    private String displayName;
    private int displaySlot;


}
