package sk.adr3ez_.antibuild.utils;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import sk.adr3ez_.antibuild.AntiBuild;

public class commands implements CommandExecutor {

    String help = "\n&8*----------* &6&lAntiBuild &7help page 1/1 &8*----------*" +
            "\n&6- /ab help &fShows help message" +
            "\n&6- /ab toggle [player] &fToggle antibuild" +
            "\n&6- /ab reload &fReload files" +
            "\n&7  [optional]\n";

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
// /ab toggle 1
        if (sender.hasPermission(AntiBuild.config.get().getString("Permissions.use"))) {
            if (args.length != 0) {
                if (args[0].equalsIgnoreCase("help")) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', help));
                } else if (args[0].equalsIgnoreCase("toggle")) {
                    if (args.length == 1) {
                        if (sender instanceof Player) {
                            String mode;

                            if (AntiBuild.allowed.contains(sender)) {
                                AntiBuild.allowed.remove(sender);
                                mode = AntiBuild.config.get().getString("Messages.placeholder.toff");
                            } else {
                                AntiBuild.allowed.add(((Player) sender));
                                mode = AntiBuild.config.get().getString("Messages.placeholder.ton");
                            }
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                    AntiBuild.config.get().getString("Messages.toggle")
                                            .replaceAll("%mode%", mode))
                            );
                        } else {
                            sender.sendMessage("Please add player name.");
                        }
                    } else if (args.length >= 2) {
                        if (Bukkit.getServer().getPlayer(args[1]) != null) {
                            Player tplayer = Bukkit.getPlayer(args[1]);
                            String mode;

                            if (AntiBuild.allowed.contains(tplayer)) {
                                AntiBuild.allowed.remove(tplayer);
                                //mode = AntiBuild.config.get().getString("Messages.off");
                                mode = AntiBuild.config.get().getString("Messages.placeholder.toff");
                            } else {
                                AntiBuild.allowed.add(tplayer);
                                //mode = AntiBuild.config.get().getString("Messages.on");
                                mode = AntiBuild.config.get().getString("Messages.placeholder.ton");
                            }
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                    AntiBuild.config.get().getString("Messages.toggledto")
                                            .replaceAll("%player%", tplayer.getName())
                                            .replaceAll("%mode%", mode))
                            );
                            tplayer.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                    AntiBuild.config.get().getString("Messages.toggledby")
                                            .replaceAll("%player%", sender.getName())
                                            .replaceAll("%mode%", mode))
                            );
                        } else {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                    AntiBuild.config.get().getString("Messages.playernull")));
                        }
                    }
                } else if (args[0].equalsIgnoreCase("reload")) {
                    AntiBuild.config.reloadFiles();
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            AntiBuild.config.get().getString("Messages.reload"))
                    );
                } else {
                    sender.sendMessage("This command doesn't exist!");
                }
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', help));
            }
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', AntiBuild.config.get().getString("Messages.noPerms")));
        }

        return false;
    }
}
