package yetanotherx.bukkitplugin.ModTRS.util;

import org.bukkit.plugin.Plugin;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;
import org.bukkit.entity.Player;
import yetanotherx.bukkitplugin.ModTRS.ModTRS;

public class ModTRSPermissions {

    /**
     * Permission handlers
     */
    private static PermissionHandler Permissions;
    private static HandlerType handlerType;

    public enum HandlerType {

        PERMISSIONS,
        VANILLA,
    }

    /**
     * Check if permissions is installed, and initiate it
     */
    public static void load(ModTRS parent) {
        ModTRS.log.debug("Loading Permissions handler");

        Plugin perm_plugin = parent.getServer().getPluginManager().getPlugin("Permissions");

        if (Permissions == null) {

            if (perm_plugin != null) {
                Permissions = ((Permissions) perm_plugin).getHandler();
                handlerType = HandlerType.PERMISSIONS;

                ModTRS.log.info("Using Permissions version " + perm_plugin.getDescription().getVersion() + " for permission handling");
            } else {
                handlerType = HandlerType.VANILLA;

                ModTRS.log.info("Neither Permissions or GroupManager found. Using ops.txt for permission handling");
            }
        }

    }

    public static PermissionHandler getPermissions() {
        return Permissions;
    }

    public static HandlerType getHandlerType() {
        return handlerType;
    }
    
    public static boolean has(Player player, String permission, boolean restricted) {
        switch (ModTRSPermissions.getHandlerType()) {
            case PERMISSIONS:
                return Permissions.has(player, permission);
            default:
                if (restricted) {
                    return player.hasPermission(permission);
                }
                return true;

        }

    }

    public static boolean has(Player player, String permission) {
        return has(player, permission, true);
    }

}
