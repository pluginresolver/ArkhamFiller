package org.arkhamnetwork.filler.config;

import lombok.Data;

import java.util.List;

/**
 * Created by Matt on 15/08/2017.
 */
@Data
public class SettingsConfig {

    private String masterGUITitle;

    private double range;

    private List<MenuItem> menuItems;
}
