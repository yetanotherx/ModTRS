package yetanotherx.bukkitplugin.ModTRS;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

public class ModTRSPermissions {

    private static PermissionHandler Permissions;
    
    public static void load( ModTRS parent ) {
	Plugin perm_plugin = parent.getServer().getPluginManager().getPlugin("Permissions");

	if( Permissions == null ) {
	    if( perm_plugin != null ) {
		//Permissions found, enable it now
		parent.getServer().getPluginManager().enablePlugin( perm_plugin );
		Permissions = ( (Permissions) perm_plugin ).getHandler();
	    }
	    else {
		//Permissions not found. Disable plugin
		ModTRS.log.severe("Permissions plugin not found, disabling plugin. (version" + parent.getDescription().getVersion() + ")");
		parent.getServer().getPluginManager().disablePlugin(parent);
	    }
	}


    }
    
    public static boolean has( Player player, String permission ) {
	return Permissions.has(player, permission);
    }

}