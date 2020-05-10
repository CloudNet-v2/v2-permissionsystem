package eu.cloudnetservice.cloudpermission.module;

import de.dytanic.cloudnet.lib.network.protocol.packet.PacketRC;
import de.dytanic.cloudnetcore.CloudNet;
import de.dytanic.cloudnetcore.api.CoreModule;
import eu.cloudnetservice.cloudpermission.module.command.CommandPermissions;
import eu.cloudnetservice.cloudpermission.module.config.ConfigPermissions;
import eu.cloudnetservice.cloudpermission.module.database.PlayerDatabase;
import eu.cloudnetservice.cloudpermission.module.listener.PlayerInitListener;
import eu.cloudnetservice.cloudpermission.module.listener.UpdateAllListener;
import eu.cloudnetservice.cloudpermission.module.network.packet.in.PacketInUpdatePermissionGroup;
import eu.cloudnetsrvice.cloudpermission.model.PermissionPool;

public class PermissionModule extends CoreModule {

    private static PermissionModule instance;
    private ConfigPermissions configPermission;
    private PermissionPool permissionPool;
    private PlayerDatabase playerDatabase;

    public static PermissionModule getInstance() {
        return instance;
    }

    public ConfigPermissions getConfigPermission() {
        return configPermission;
    }

    public PermissionPool getPermissionPool() {
        return permissionPool;
    }

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onBootstrap() {
        try {
            configPermission = new ConfigPermissions();
        } catch (Exception e) {
            e.printStackTrace();
        }
        playerDatabase = new PlayerDatabase(CloudNet.getInstance().getDatabaseManager().getDatabase("cloudnet_internal_players"));
        permissionPool = new PermissionPool();
        permissionPool.setAvailable(configPermission.isEnabled0());
        permissionPool.getGroups().putAll(configPermission.loadAll0());

        getCloud().getNetworkManager().getModuleProperties().append("permissionPool", permissionPool);

        getCloud().getEventManager().registerListener(this, new PlayerInitListener());
        getCloud().getEventManager().registerListener(this, new UpdateAllListener());

        getCloud().getCommandManager().registerCommand(new CommandPermissions());

        getCloud().getPacketManager().registerHandler(PacketRC.CN_CORE + 1, PacketInUpdatePermissionGroup.class);
    }

    public PlayerDatabase getPlayerDatabase() {
        return playerDatabase;
    }
}
