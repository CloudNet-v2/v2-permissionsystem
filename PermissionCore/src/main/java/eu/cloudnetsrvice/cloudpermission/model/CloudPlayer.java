package eu.cloudnetsrvice.cloudpermission.model;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.Objects;

public class CloudPlayer extends OfflinePlayer {

    public static final Type TYPE = (new TypeToken<CloudPlayer>() {
    }).getType();
    private PlayerConnection playerConnection;
    private String proxy;
    private String server;
    private Timestamp loginTimeStamp;
    private PlayerExecutor playerExecutor;

    public CloudPlayer(OfflinePlayer player, PlayerConnection onlineConnection, String proxy) {
        super(player.getUniqueId(), player.getName(), player.getLastLogin(), player.getFirstLogin(), player.getLastPlayerConnection(), player.getPermissionEntity());
        this.playerConnection = onlineConnection;
        this.proxy = proxy;
        this.server = null;
        this.playerExecutor = new PlayerExecutor();
        this.loginTimeStamp = new Timestamp(System.currentTimeMillis());
    }

    public static OfflinePlayer newOfflinePlayer(OfflinePlayer cloudPlayer) {
        return new OfflinePlayer(cloudPlayer.getUniqueId(), cloudPlayer.getName(),  cloudPlayer.getLastLogin(), cloudPlayer.getFirstLogin(), cloudPlayer.getLastPlayerConnection(), cloudPlayer.getPermissionEntity());
    }

    public int hashCode() {
        int result = this.playerConnection != null ? this.playerConnection.hashCode() : 0;
        result = 31 * result + (this.proxy != null ? this.proxy.hashCode() : 0);
        result = 31 * result + (this.server != null ? this.server.hashCode() : 0);
        result = 31 * result + (this.loginTimeStamp != null ? this.loginTimeStamp.hashCode() : 0);
        result = 31 * result + (this.playerExecutor != null ? this.playerExecutor.hashCode() : 0);
        return result;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof CloudPlayer)) {
            return false;
        } else {
            CloudPlayer that = (CloudPlayer)o;
            return Objects.equals(this.playerConnection, that.playerConnection) && Objects.equals(this.proxy, that.proxy) && Objects.equals(this.server, that.server) && Objects.equals(this.loginTimeStamp, that.loginTimeStamp) && Objects.equals(this.playerExecutor, that.playerExecutor);
        }
    }

    public String toString() {
        return "CloudPlayer{playerConnection=" + this.playerConnection + ", proxy='" + this.proxy + '\'' + ", server='" + this.server + '\'' + ", loginTimeStamp=" + this.loginTimeStamp + ", playerExecutor=" + this.playerExecutor + "} " + super.toString();
    }

    public String getServer() {
        return this.server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getProxy() {
        return this.proxy;
    }

    public void setProxy(String proxy) {
        this.proxy = proxy;
    }

    public PlayerConnection getPlayerConnection() {
        return this.playerConnection;
    }

    public void setPlayerConnection(PlayerConnection playerConnection) {
        this.playerConnection = playerConnection;
    }

    public Timestamp getLoginTimeStamp() {
        return this.loginTimeStamp;
    }

    public void setLoginTimeStamp(Timestamp loginTimeStamp) {
        this.loginTimeStamp = loginTimeStamp;
    }

    public PlayerExecutor getPlayerExecutor() {
        return this.playerExecutor;
    }

    public void setPlayerExecutor(PlayerExecutor playerExecutor) {
        this.playerExecutor = playerExecutor;
    }

}
