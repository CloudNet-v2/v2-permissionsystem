package eu.cloudnetsrvice.cloudpermission.model;

import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.UUID;

public class OfflinePlayer implements Serializable {

    public static final Type TYPE = TypeToken.get(OfflinePlayer.class).getType();

    protected UUID uniqueId;

    protected String name;

    protected Long lastLogin;

    protected Long firstLogin;

    protected PlayerConnection lastPlayerConnection;

    protected PermissionEntity permissionEntity;

    public OfflinePlayer(UUID uniqueId,
                         String name,
                         Long lastLogin,
                         Long firstLogin,
                         PlayerConnection lastPlayerConnection,
                         PermissionEntity permissionEntity) {
        this.uniqueId = uniqueId;
        this.name = name;
        this.lastLogin = lastLogin;
        this.firstLogin = firstLogin;
        this.lastPlayerConnection = lastPlayerConnection;
        this.permissionEntity = permissionEntity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(UUID uniqueId) {
        this.uniqueId = uniqueId;
    }


    public Long getFirstLogin() {
        return firstLogin;
    }

    public void setFirstLogin(Long firstLogin) {
        this.firstLogin = firstLogin;
    }

    public Long getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Long lastLogin) {
        this.lastLogin = lastLogin;
    }

    public PermissionEntity getPermissionEntity() {
        return permissionEntity;
    }

    public void setPermissionEntity(PermissionEntity permissionEntity) {
        this.permissionEntity = permissionEntity;
    }

    public PlayerConnection getLastPlayerConnection() {
        return lastPlayerConnection;
    }

    public void setLastPlayerConnection(PlayerConnection lastPlayerConnection) {
        this.lastPlayerConnection = lastPlayerConnection;
    }

}
