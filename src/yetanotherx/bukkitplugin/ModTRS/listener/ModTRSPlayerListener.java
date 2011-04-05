package yetanotherx.bukkitplugin.ModTRS.listener;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;

import yetanotherx.bukkitplugin.ModTRS.ModTRS;
import yetanotherx.bukkitplugin.ModTRS.ModTRSMessage;
import yetanotherx.bukkitplugin.ModTRS.ModTRSPermissions;
import yetanotherx.bukkitplugin.ModTRS.sql.ModTRSRequest;
import yetanotherx.bukkitplugin.ModTRS.sql.ModTRSRequestTable;

public class ModTRSPlayerListener extends PlayerListener{

    public ModTRSPlayerListener(ModTRS parent) {
    }

    @Override
    public void onPlayerJoin(PlayerJoinEvent event) {

	Player player = event.getPlayer();

	if( ModTRSPermissions.has(player, "modtrs.mod") ) {

	    try {
		ArrayList<ModTRSRequest> requests = ModTRSRequestTable.getOpenRequests("open");


		if( requests.size() != 0 ) {
		    player.sendMessage( ModTRSMessage.openRequests );
		}

	    }
	    catch( Exception e ) {
		e.printStackTrace();
	    }

	}
    }




}

