package yetanotherx.bukkitplugin.ModTRS.command;

import java.sql.SQLException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import yetanotherx.bukkitplugin.ModTRS.ModTRS;
import yetanotherx.bukkitplugin.ModTRS.ModTRSMessage;
import yetanotherx.bukkitplugin.ModTRS.sql.ModTRSRequest;
import yetanotherx.bukkitplugin.ModTRS.sql.ModTRSRequestTable;
import yetanotherx.bukkitplugin.ModTRS.sql.ModTRSUser;
import yetanotherx.bukkitplugin.ModTRS.sql.ModTRSUserTable;

public class ClaimCommand implements CommandExecutor {

    public ClaimCommand(ModTRS parent) {
    }



    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {

	Player player = (Player) sender;

	try {
	    ModTRSRequest request = ModTRSRequestTable.getRequestFromId( Integer.parseInt( args[0] ) );


	    if( request != null ) {

		ModTRSUser user = ModTRSUserTable.getUserFromName(player.getName());

		if( user == null ) {
		    user = new ModTRSUser();
		    user.setName(player.getName());
		    user.insert();
		    user = ModTRSUserTable.getUserFromName(player.getName());
		}

		request.setModId(user.getId());
		request.setModTimestamp(System.currentTimeMillis());
		request.setStatus(1);
		request.update();
		
		player.sendMessage( ModTRSMessage.parse( ModTRSMessage.claimed, new Object[] { request.getId() } ) );

	    }
	    else {
		player.sendMessage( ModTRSMessage.noSuchRequest );
	    }

	}
	catch( SQLException e ) {
	    e.printStackTrace();
	    player.sendMessage( ModTRSMessage.internalError );
	}

	return true;

    }

}
