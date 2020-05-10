package eu.cloudnetservice.cloudpermission.module.network.packet.in;

import de.dytanic.cloudnet.lib.network.protocol.packet.PacketInHandler;
import de.dytanic.cloudnet.lib.network.protocol.packet.PacketSender;
import de.dytanic.cloudnet.lib.utility.document.Document;
import de.dytanic.cloudnetcore.CloudNet;
import eu.cloudnetservice.cloudpermission.module.PermissionModule;
import eu.cloudnetsrvice.cloudpermission.model.PermissionGroup;

public class PacketInUpdatePermissionGroup extends PacketInHandler {

    @Override
    public void handleInput(Document packet, PacketSender packetSender) {
        PermissionGroup permissionGroup = packet.getObject("permissionGroup", PermissionGroup.TYPE);
        PermissionModule.getInstance().getConfigPermission().updatePermissionGroup(permissionGroup);
        CloudNet.getInstance().getNetworkManager().reload();
        CloudNet.getInstance().getNetworkManager().updateAll0();
    }
}
