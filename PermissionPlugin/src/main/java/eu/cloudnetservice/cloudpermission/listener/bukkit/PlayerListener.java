package eu.cloudnetservice.cloudpermission.listener.bukkit;

import de.dytanic.cloudnet.api.CloudAPI;
import de.dytanic.cloudnet.bridge.internal.util.ReflectionUtil;
import eu.cloudnetservice.cloudpermission.model.CloudPermissible;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.lang.reflect.Field;
import java.util.logging.Level;

public class PlayerListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void playerLogin(PlayerLoginEvent event) {
        CloudAPI.getInstance().getLogger().logp(Level.FINEST,
            this.getClass().getSimpleName(),
            "handleFirst",
            String.format("Handling player login event: %s%n", event));
        if (CloudAPI.getInstance().getPermissionPool() != null && CloudAPI.getInstance().getPermissionPool().isAvailable()) {
            try {
                Field field;
                Class<?> clazz = ReflectionUtil.reflectCraftClazz(".entity.CraftHumanEntity");

                if (clazz != null) {
                    field = clazz.getDeclaredField("perm");
                } else {
                    field = Class.forName("net.glowstone.entity.GlowHumanEntity").getDeclaredField("permissions");
                }

                field.setAccessible(true);
                final CloudPermissible cloudPermissible = new CloudPermissible(event.getPlayer());
                field.set(event.getPlayer(), cloudPermissible);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
