package yetanotherx.bukkitplugin.ModTRS.listener;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;

import yetanotherx.bukkitplugin.ModTRS.ModTRS;
import yetanotherx.bukkitplugin.ModTRS.ModTRSMessage;
import yetanotherx.bukkitplugin.ModTRS.ModTRSPermissions;
import yetanotherx.bukkitplugin.ModTRS.ModTRSSettings;
import yetanotherx.bukkitplugin.ModTRS.sql.ModTRSRequest;
import yetanotherx.bukkitplugin.ModTRS.sql.ModTRSRequestTable;

public class ModTRSPlayerListener extends PlayerListener{

    private ModTRS parent;

    public ModTRSPlayerListener(ModTRS parent) {
        this.parent = parent;
    }

    /**
     * Simply notifies a user that there are open requests when they join
     */
    @Override
    public void onPlayerJoin(PlayerJoinEvent event) {

	Player player = event.getPlayer();
	
	if( !ModTRSSettings.notifyMods ) {
	    return;
	}
	
	if( ModTRSPermissions.has(player, "modtrs.mod") ) {

	    try {
		ArrayList<ModTRSRequest> requests = ModTRSRequestTable.getOpenRequests(parent, "open");

		if( !requests.isEmpty() ) {
		    ModTRSMessage.general.sendOpenRequests(player, requests.size());
		}

	    }
	    catch( Exception e ) {
		e.printStackTrace();
	    }

	}
    }




}

