package eu.cloudnetsrvice.cloudpermission.model;

import java.util.ArrayList;
import java.util.HashMap;

public class DefaultPermissionGroup extends PermissionGroup {
    public DefaultPermissionGroup(String name) {
        super(name, "§c", "§e", "§f", "§7", 98, 0, false, new HashMap<>(), new HashMap<>(), new ArrayList<>());
    }
}
