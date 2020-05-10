package eu.cloudnetservice.cloudpermission;

import de.dytanic.cloudnet.bridge.internal.command.proxied.CommandPermissions;
import eu.cloudnetservice.cloudpermission.listener.bungee.ProxiedPlayerListener;
import net.md_5.bungee.api.plugin.Plugin;

public class BungeePermissionPlugin extends Plugin {

    @Override
    public void onEnable() {
        getProxy().getPluginManager().registerListener(this, new ProxiedPlayerListener());
        if (PermissionAPI.getInstance().getPermissionPool() != null && PermissionAPI.getInstance().getPermissionPool().isAvailable()) {
            getProxy().getPluginManager().registerCommand(this, new CommandPermissions());
        }
    }
}
