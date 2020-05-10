package eu.cloudnetservice.cloudpermission;

import de.dytanic.cloudnet.api.CloudAPI;
import de.dytanic.cloudnet.api.network.packet.api.sync.PacketAPIOutGetOfflinePlayer;
import de.dytanic.cloudnet.lib.network.protocol.packet.result.Result;
import eu.cloudnetservice.cloudpermission.packet.out.PacketOutUpdatePermissionGroup;
import eu.cloudnetservice.cloudpermission.packet.out.PacketOutUpdatePlayer;
import eu.cloudnetsrvice.cloudpermission.model.*;

import java.util.HashSet;
import java.util.UUID;
import java.util.logging.Level;
import java.util.stream.Collectors;

public final class PermissionAPI {

    private static PermissionAPI instance = new PermissionAPI();

    public PermissionAPI() {
        instance = this;
    }

    public static PermissionAPI getInstance() {
        return instance;
    }

    /**
     * Returns an offline player by their UUID.
     * If the player is not cached, the master is queried.
     *
     * @param uniqueId the UUID of the player.
     *
     * @return the offline player or null, if the player is not registered on the network.
     */
    public OfflinePlayer getOfflinePlayer(UUID uniqueId) {
        if (CloudAPI.getInstance().getCloudService() != null) {
            de.dytanic.cloudnet.lib.player.CloudPlayer cloudPlayer = CloudAPI.getInstance().getOnlinePlayer(uniqueId);
            if (cloudPlayer != null) {
                return getNewPlayer(cloudPlayer);
            }
        }

        Result result = CloudAPI.getInstance().getNetworkConnection().getPacketManager().sendQuery(new PacketAPIOutGetOfflinePlayer(uniqueId),
            CloudAPI.getInstance().getNetworkConnection());
        return result.getResult().getObject("player", OfflinePlayer.TYPE);
    }

    /**
     * Transform a old 2.1.17 cloud player into module cloud player
     * @param cloudPlayer old player model
     * @return new player model
     */
    public CloudPlayer getNewPlayer(de.dytanic.cloudnet.lib.player.CloudPlayer cloudPlayer) {
        return new CloudPlayer(
            new OfflinePlayer(
                cloudPlayer.getUniqueId(),
                cloudPlayer.getName(),
                cloudPlayer.getLastLogin(),
                cloudPlayer.getFirstLogin(),
                getNewConnection(cloudPlayer.getLastPlayerConnection()),
                getNewPermissionEntity(cloudPlayer.getPermissionEntity())),
            getNewConnection(cloudPlayer.getPlayerConnection()),
            cloudPlayer.getProxy());
    }

    /**
     * Transform a old 2.1.17 player connection into a new one from module
     * @param connection the old connection
     * @return new connection model
     */
    private PlayerConnection getNewConnection(de.dytanic.cloudnet.lib.player.PlayerConnection connection) {
        return new PlayerConnection(
            connection.getUniqueId(),
            connection.getName(),
            connection.getVersion(),
            connection.getHost(),
            connection.getPort(),
            connection.isOnlineMode(),
            connection.isLegacy());
    }

    /**
     * Transform a old 2.1.17 permission entity model to a new one from module
     * @param permissionEntity old entity model
     * @return new entity model
     */
    public PermissionEntity getNewPermissionEntity(de.dytanic.cloudnet.lib.player.permission.PermissionEntity permissionEntity) {
        return new PermissionEntity(
            permissionEntity.getUniqueId(),
            permissionEntity.getPermissions(),
            permissionEntity.getPrefix(),
            permissionEntity.getSuffix(),
            getNewGroupEntityData(permissionEntity));
    }

    private HashSet<GroupEntityData> getNewGroupEntityData(de.dytanic.cloudnet.lib.player.permission.PermissionEntity permissionEntity) {
        return permissionEntity.getGroups().stream().map(ged -> new GroupEntityData(ged.getGroup(),
            ged.getTimeout())).collect(Collectors.toCollection(HashSet::new));
    }

    /**
     * Returns the permission group from the permissions-system
     */
    public PermissionGroup getPermissionGroup(String group) {
        if (CloudAPI.getInstance().getCloudNetwork().getModules().contains("permissionPool")) {
            return this.getPermissionPool().getGroups().get(group);
        }
        return null;
    }

    /**
     * @return the pool of permissions including all permission groups and the default group for the cloud network.
     */
    public PermissionPool getPermissionPool() {
        return CloudAPI.getInstance().getCloudNetwork().getModules().getObject("permissionPool", PermissionPool.TYPE);
    }

    /**
     * Updates the given permission group.
     *
     * @param permissionGroup the permission group to update.
     */
    public void updatePermissionGroup(PermissionGroup permissionGroup) {
        CloudAPI.getInstance().getLogger().logp(Level.FINEST,
            this.getClass().getSimpleName(),
            "updatePermissionGroup",
            String.format("Updating permission group: %s%n", permissionGroup));
        CloudAPI.getInstance().getNetworkConnection().sendPacket(new PacketOutUpdatePermissionGroup(permissionGroup));
    }

    /**
     * Updates a player on the master.
     *
     * @param cloudPlayer the player to update.
     */
    public void updatePlayer(CloudPlayer cloudPlayer) {
        CloudAPI.getInstance().getLogger().logp(Level.FINEST,
            this.getClass().getSimpleName(),
            "updatePlayer",
            String.format("Updating cloud player: %s%n", cloudPlayer));
        CloudAPI.getInstance().getNetworkConnection().sendPacket(new PacketOutUpdatePlayer(CloudPlayer.newOfflinePlayer(cloudPlayer)));
    }

    /**
     * Updates an offline player on the master.
     *
     * @param offlinePlayer the offline player to update.
     */
    public void updatePlayer(OfflinePlayer offlinePlayer) {
        CloudAPI.getInstance().getLogger().logp(Level.FINEST,
            this.getClass().getSimpleName(),
            "updatePlayer",
            String.format("Updating offline player: %s%n", offlinePlayer));
        CloudAPI.getInstance().getNetworkConnection().sendPacket(new PacketOutUpdatePlayer(offlinePlayer));
    }
}
