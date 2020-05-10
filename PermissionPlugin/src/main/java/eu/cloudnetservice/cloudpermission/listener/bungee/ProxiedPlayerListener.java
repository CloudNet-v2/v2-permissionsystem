package eu.cloudnetservice.cloudpermission.listener.bungee;

import de.dytanic.cloudnet.api.CloudAPI;
import de.dytanic.cloudnet.bridge.CloudProxy;
import eu.cloudnetservice.cloudpermission.PermissionAPI;
import eu.cloudnetservice.cloudpermission.model.CloudPlayerCommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PermissionCheckEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ProxiedPlayerListener implements Listener {

    @EventHandler
    public void handlePermissionCheck(PermissionCheckEvent e) {
        if (PermissionAPI.getInstance().getPermissionPool() == null || !PermissionAPI.getInstance().getPermissionPool().isAvailable()) {
            return;
        }

        if (e.getSender() instanceof ProxiedPlayer) {
            if (CloudProxy.getInstance().getCloudPlayers().containsKey(((ProxiedPlayer) e.getSender()).getUniqueId())) {
                e.setHasPermission(PermissionAPI.getInstance().getNewPermissionEntity(CloudProxy.getInstance()
                                                                                                .getCloudPlayers()
                                                                                                .get(((ProxiedPlayer) e.getSender()).getUniqueId()).getPermissionEntity())
                                                .hasPermission(PermissionAPI.getInstance().getPermissionPool(),
                                                    e.getPermission(),
                                                    CloudAPI.getInstance().getGroup()));
            }
        } else if (e.getSender() instanceof CloudPlayerCommandSender) {
            e.setHasPermission(((CloudPlayerCommandSender) e.getSender()).getCloudPlayer()
                                                                         .getPermissionEntity()
                                                                         .hasPermission(PermissionAPI.getInstance().getPermissionPool(),
                                                                             e.getPermission(),
                                                                             CloudAPI.getInstance().getGroup()));
        }
    }

}
