package eu.cloudnetservice.cloudpermission.module.listener;

import de.dytanic.cloudnet.event.IEventListener;
import de.dytanic.cloudnetcore.api.event.network.UpdateAllEvent;
import eu.cloudnetservice.cloudpermission.module.PermissionModule;
import eu.cloudnetsrvice.cloudpermission.model.PermissionPool;

public final class UpdateAllListener implements IEventListener<UpdateAllEvent> {

    @Override
    public void onCall(UpdateAllEvent event) {
        final PermissionPool permissionPool = PermissionModule.getInstance().getPermissionPool();
        permissionPool.setAvailable(PermissionModule.getInstance().getConfigPermission().isEnabled());
        permissionPool.getGroups().clear();
        permissionPool.getGroups().putAll(PermissionModule.getInstance().getConfigPermission().loadAll());
        event.getNetworkManager().getModuleProperties().append("permissionPool", permissionPool);
    }
}
