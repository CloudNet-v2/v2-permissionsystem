package eu.cloudnetservice.cloudpermission.packet.out;

import de.dytanic.cloudnet.lib.network.protocol.packet.Packet;
import de.dytanic.cloudnet.lib.utility.document.Document;
import eu.cloudnetsrvice.cloudpermission.model.OfflinePlayer;

public class PacketOutUpdatePlayer extends Packet {
    public PacketOutUpdatePlayer(OfflinePlayer offlinePlayer) {
        super(302, new Document("player", offlinePlayer));
    }
}
