package eu.cloudnetservice.cloudpermission.model;

import de.dytanic.cloudnet.api.CloudAPI;
import de.dytanic.cloudnet.bridge.CloudServer;
import eu.cloudnetservice.cloudpermission.PermissionAPI;
import eu.cloudnetsrvice.cloudpermission.model.CloudPlayer;
import eu.cloudnetsrvice.cloudpermission.model.PermissionEntity;
import eu.cloudnetsrvice.cloudpermission.model.PermissionGroup;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissibleBase;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachmentInfo;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class CloudPermissible extends PermissibleBase {

    private UUID uniqueId;

    private Map<String, PermissionAttachmentInfo> permissions = new ConcurrentHashMap<>();

    public CloudPermissible(Player player) {
        super(player);
        this.uniqueId = player.getUniqueId();

        player.setOp(false);
        recalculatePermissions();
    }

    @Override
    public boolean isOp() {
        return false;
    }

    @Override
    public boolean isPermissionSet(String name) {
        return hasPermission(name);
    }

    @Override
    public boolean isPermissionSet(Permission perm) {
        return hasPermission(perm.getName());
    }

    @Override
    public boolean hasPermission(String inName) {
        if (inName.equalsIgnoreCase("bukkit.broadcast.user")) {
            return true;
        }

        CloudPlayer cloudPlayer = PermissionAPI.getInstance().getNewPlayer(CloudServer.getInstance().getCloudPlayers().get(this.uniqueId));
        if (cloudPlayer != null) {
            boolean hasPermission = cloudPlayer.getPermissionEntity().hasPermission(PermissionAPI.getInstance().getPermissionPool(),
                inName,
                CloudAPI.getInstance().getGroup());
            CloudAPI.getInstance().getLogger().finest(cloudPlayer.getName() + " hasPermission \"" + inName + "\": " + hasPermission);
            return hasPermission;

        } else {
            return false;
        }
    }

    @Override
    public boolean hasPermission(Permission perm) {
        return hasPermission(perm.getName());
    }

    @Override
    public void recalculatePermissions() {
        if (this.permissions != null) {
            this.permissions.clear();
        } else {
            this.permissions = new ConcurrentHashMap<>();
        }

        if (this.uniqueId == null) {
            return;
        }

        PermissionEntity permissionEntity = PermissionAPI.getInstance().getNewPermissionEntity(CloudServer.getInstance().getCloudPlayers().get(
            this.uniqueId).getPermissionEntity());
        final Map<String, Boolean> playerPermissions = permissionEntity.getPermissions();
        playerPermissions.forEach((key, value) -> {
            PermissionAttachmentInfo permissionAttachmentInfo = new PermissionAttachmentInfo(this, key, null, value);
            permissions.put(key, permissionAttachmentInfo);
        });
        permissionEntity.getGroups().stream().map(g -> PermissionAPI.getInstance().getPermissionGroup(g.getGroup()))
                        .filter(Objects::nonNull)
                        .flatMap(g -> {
                            Stream.Builder<PermissionGroup> builder = Stream.<PermissionGroup>builder().add(g);
                            g.getImplementGroups()
                             .stream()
                             .map(i -> PermissionAPI.getInstance().getPermissionGroup(i))
                             .filter(Objects::nonNull)
                             .forEach(builder);

                            return builder.build();
                        })
                        .forEach(g -> {
                            g.getPermissions().forEach((key, value) -> {
                                PermissionAttachmentInfo permissionAttachmentInfo = new PermissionAttachmentInfo(this, key, null, value);
                                permissions.put(key, permissionAttachmentInfo);
                            });
                            g.getServerGroupPermissions().getOrDefault(CloudAPI.getInstance().getGroup(), Collections.emptyList()).forEach(
                                key -> {
                                    PermissionAttachmentInfo permissionAttachmentInfo = new PermissionAttachmentInfo(this, key, null, true);
                                    permissions.put(key, permissionAttachmentInfo);
                                });
                        });
    }

    @Override
    public Set<PermissionAttachmentInfo> getEffectivePermissions() {
        return new HashSet<>(permissions.values());
    }

    public UUID getUniqueId() {
        return uniqueId;
    }
}
