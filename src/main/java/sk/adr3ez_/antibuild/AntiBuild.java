package sk.adr3ez_.antibuild;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.vehicle.VehicleDestroyEvent;
import org.bukkit.plugin.java.JavaPlugin;
import sk.adr3ez_.antibuild.files.Config;
import sk.adr3ez_.antibuild.utils.commands;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public final class AntiBuild extends JavaPlugin implements Listener {

    public static ArrayList<Player> allowed = new ArrayList<>();
    private static final ArrayList<String> blacklistedWorlds = new ArrayList<>();
    public static Config config;

    @Override
    public void onEnable() {
        // Plugin startup logic
        new File(String.valueOf(this.getDataFolder())).mkdirs();

        config = new Config(this);
        getServer().getPluginManager().registerEvents(this, this);
        this.getCommand("antibuild").setExecutor(new commands());

        //Load blacklistedWorlds
        blacklistedWorlds.addAll(config.get().getStringList("Blacklisted-worlds"));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        if (!blacklistedWorlds.contains(Objects.requireNonNull(e.getBlock().getLocation().getWorld()).getName())) {
            if (!allowed.contains(p)) {
                if (config.get().getBoolean("Settings.title.noBuild")) {
                    p.sendTitle(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(config.get().getString("Messages.title.noBuild.title"))),
                            ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(config.get().getString("Messages.title.noBuild.subtitle"))),
                            config.get().getInt("Messages.title.noBuild.fadeIn"),
                            config.get().getInt("Messages.title.noBuild.stay"),
                            config.get().getInt("Messages.title.noBuild.fadeOut"));
                } else {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(config.get().getString("Messages.noBuild"))));
                }
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDestroy(BlockBreakEvent e) {
        Player p = e.getPlayer();

        if (!blacklistedWorlds.contains(Objects.requireNonNull(e.getBlock().getLocation().getWorld()).getName())) {
            if (!allowed.contains(p)) {
                if (config.get().getBoolean("Settings.title.noDestroy")) {
                    p.sendTitle(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(config.get().getString("Messages.title.noDestroy.title"))),
                            ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(config.get().getString("Messages.title.noDestroy.subtitle"))),
                            config.get().getInt("Messages.title.noDestroy.fadeIn"),
                            config.get().getInt("Messages.title.noDestroy.stay"),
                            config.get().getInt("Messages.title.noDestroy.fadeOut"));
                } else {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(config.get().getString("Messages.noDestroy"))));
                }
                e.setCancelled(true);
            }
        }
    }

    /*@EventHandler
    public void onCropsDestroy(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        if (!blacklistedWorlds.contains(e.getClickedBlock().getLocation().getWorld().getName())) {
            if (e.getAction() == Action.PHYSICAL) {
                if (e.getMaterial() == Material.FARMLAND) {
                    if (!allowed.contains(p)) {
                        if (config.get().getBoolean("Settings.title.noDestroy")) {
                            p.sendTitle(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(config.get().getString("Messages.title.noDestroy.title"))),
                                    ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(config.get().getString("Messages.title.noDestroy.subtitle"))),
                                    config.get().getInt("Messages.title.noDestroy.fadeIn"),
                                    config.get().getInt("Messages.title.noDestroy.stay"),
                                    config.get().getInt("Messages.title.noDestroy.fadeOut"));
                        } else {
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(config.get().getString("Messages.noDestroy"))));
                        }
                        e.setCancelled(true);
                    }
                }
            }
        }
    }*/

    @EventHandler
    public void onVehicleDestroy(VehicleDestroyEvent e) {
        if (!blacklistedWorlds.contains(e.getVehicle().getLocation().getWorld().getName())) {
            if (e.getAttacker() instanceof Player && !allowed.contains((Player) e.getAttacker())) {
                if (config.get().getBoolean("Settings.title.noDestroy")) {
                    ((Player) e.getAttacker()).getPlayer().sendTitle(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(config.get().getString("Messages.title.noDestroy.title"))),
                            ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(config.get().getString("Messages.title.noDestroy.subtitle"))),
                            config.get().getInt("Messages.title.noDestroy.fadeIn"),
                            config.get().getInt("Messages.title.noDestroy.stay"),
                            config.get().getInt("Messages.title.noDestroy.fadeOut"));
                } else {
                    ((Player) e.getAttacker()).getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(config.get().getString("Messages.noDestroy"))));
                }
                e.setCancelled(true);
            }
        }
    }

    //NEEDS UPDATE, Making trouble with lower versions
   /* @EventHandler
    public void onVehiclePlace(EntityPlaceEvent e) {
        if (!blacklistedWorlds.contains(e.getEntity().getLocation().getWorld().getName())) {
            if (!allowed.contains(e.getPlayer())) {
                if (config.get().getBoolean("Settings.title.noBuild")) {
                    e.getPlayer().sendTitle(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(config.get().getString("Messages.title.noBuild.title"))),
                            ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(config.get().getString("Messages.title.noBuild.subtitle"))),
                            config.get().getInt("Messages.title.noBuild.fadeIn"),
                            config.get().getInt("Messages.title.noBuild.stay"),
                            config.get().getInt("Messages.title.noBuild.fadeOut"));
                } else {
                    e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(config.get().getString("Messages.noBuild"))));
                }
                e.setCancelled(true);
            }
        }
    }*/

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        allowed.remove(e.getPlayer());
        if (e.getPlayer().hasPermission(config.get().getString("Permissions.use"))) {
            if (config.get().getBoolean("Settings.auto-allowed-with-permission")) {
                allowed.add(e.getPlayer());
                e.getPlayer().sendMessage("Building mode has been turned.");
            }
        }
    }
    public static void reload() {
        AntiBuild.config.reloadFiles();
        blacklistedWorlds.clear();
        blacklistedWorlds.addAll(config.get().getStringList("Blacklisted-worlds"));
    }
}
