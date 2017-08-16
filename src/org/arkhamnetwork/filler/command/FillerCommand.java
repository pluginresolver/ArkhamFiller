package org.arkhamnetwork.filler.command;

import org.arkhamnetwork.filler.ArkhamFiller;
import org.arkhamnetwork.filler.menu.MainMenu;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Matt on 15/08/2017.
 */
public class FillerCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (cmd.getName().equalsIgnoreCase("filler") && sender instanceof Player) {

            Player player = (Player) sender;

            player.openInventory(ArkhamFiller.getInstance().getMainMenu().getConstructInventory().apply(player));
            player.sendMessage(ChatColor.GREEN + "Opening Main Menu...");
        }

        return true;
    }
}
