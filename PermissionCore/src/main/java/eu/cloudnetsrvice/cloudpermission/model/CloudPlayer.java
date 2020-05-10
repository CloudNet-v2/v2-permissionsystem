package eu.cloudnetsrvice.cloudpermission.model;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.Objects;

public class CloudPlayer extends OfflinePlayer {

    public static final Type TYPE = TypeToken.get(CloudPlayer.class).getType();

    private PlayerConnection playerConnection;
    private String proxy;
    private String server;
    private Timestamp loginTimeStamp;
    private PlayerExecutor playerExecutor;

    public CloudPlayer(OfflinePlayer player, PlayerConnection onlineConnection, String proxy) {
        super(player.getUniqueId(),
            player.getName(),
            player.getLastLogin(),
            player.getFirstLogin(),
            player.getLastPlayerConnection(),
            player.getPermissionEntity());
        this.playerConnection = onlineConnection;
        this.proxy = proxy;
        this.server = null;
        this.playerExecutor = new PlayerExecutor();
        this.loginTimeStamp = new Timestamp(System.currentTimeMillis());
    }

    public static OfflinePlayer newOfflinePlayer(OfflinePlayer cloudPlayer) {
        return new OfflinePlayer(cloudPlayer.getUniqueId(),
            cloudPlayer.getName(),
            cloudPlayer.getLastLogin(),
            cloudPlayer.getFirstLogin(),
            cloudPlayer.getLastPlayerConnection(),
            cloudPlayer.getPermissionEntity());
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CloudPlayer)) {
            return false;
        }
        final CloudPlayer that = (CloudPlayer) o;
        return Objects.equals(playerConnection, that.playerConnection) &&
               Objects.equals(proxy, that.proxy) &&
               Objects.equals(server, that.server) &&
               Objects.equals(loginTimeStamp, that.loginTimeStamp) &&
               Objects.equals(playerExecutor, that.playerExecutor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerConnection, proxy, server, loginTimeStamp, playerExecutor);
    }

    @Override
    public String toString() {
        return "CloudPlayer{" +
               "playerConnection=" + playerConnection +
               ", proxy='" + proxy + '\'' +
               ", server='" + server + '\'' +
               ", loginTimeStamp=" + loginTimeStamp +
               ", playerExecutor=" + playerExecutor +
               '}';
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
