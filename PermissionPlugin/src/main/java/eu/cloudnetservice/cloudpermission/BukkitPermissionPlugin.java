package eu.cloudnetservice.cloudpermission;

import eu.cloudnetservice.cloudpermission.listener.bukkit.PlayerListener;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitPermissionPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
    }
}
