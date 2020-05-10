package eu.cloudnetsrvice.cloudpermission.model;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class PermissionPool {
    public static final Type TYPE = TypeToken.get(PermissionPool.class).getType();
    private final Map<String, PermissionGroup> groups = new HashMap<>();
    private boolean available = true;
    private PermissionGroup defaultGroup;

    public PermissionPool() {
        getDefaultGroup();
    }

    public PermissionGroup getDefaultGroup() {
        if (this.defaultGroup == null) {
            for (PermissionGroup group : groups.values()) {
                if (group.isDefaultGroup()) {
                    this.defaultGroup = group;
                }
            }
        }
        return this.defaultGroup;
    }

    public PermissionEntity getNewPermissionEntity(PlayerConnection playerWhereAmI) {
        return new PermissionEntity(playerWhereAmI.getUniqueId(),
            new HashMap<>(),
            null,
            null,
            Collections.singletonList(new GroupEntityData(getDefaultGroup().getName(), 0L)));
    }

    public PermissionEntity getNewPermissionEntity(OfflinePlayer playerWhereAmI) {
        return new PermissionEntity(playerWhereAmI.getUniqueId(),
            new HashMap<>(),
            null,
            null,
            Collections.singletonList(new GroupEntityData(getDefaultGroup().getName(), 0L)));
    }

    public Map<String, PermissionGroup> getGroups() {
        return groups;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
