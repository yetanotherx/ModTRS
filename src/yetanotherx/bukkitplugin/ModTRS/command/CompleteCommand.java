package yetanotherx.bukkitplugin.ModTRS.command;

import java.sql.SQLException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import yetanotherx.bukkitplugin.ModTRS.ModTRS;
import yetanotherx.bukkitplugin.ModTRS.ModTRSMessage;
import yetanotherx.bukkitplugin.ModTRS.ModTRSPermissions;
import yetanotherx.bukkitplugin.ModTRS.sql.ModTRSRequest;
import yetanotherx.bukkitplugin.ModTRS.sql.ModTRSRequestTable;
import yetanotherx.bukkitplugin.ModTRS.sql.ModTRSUser;
import yetanotherx.bukkitplugin.ModTRS.sql.ModTRSUserTable;
import yetanotherx.bukkitplugin.ModTRS.validator.CompleteValidator;
import yetanotherx.bukkitplugin.ModTRS.validator.ModTRSValidatorHandler;

public class CompleteCommand implements CommandExecutor {

    public CompleteCommand(ModTRS parent) {
	ModTRSValidatorHandler.getInstance().registerValidator( "complete", new CompleteValidator(this, parent) );
	ModTRSValidatorHandler.getInstance().registerValidator( "done", new CompleteValidator(this, parent) );
	
    }



    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {

	Player player = (Player) sender;
	
	if( !ModTRSPermissions.has(player, "modtrs.command.complete") ) {
	    player.sendMessage(ModTRSMessage.noPermission);
	    return true;
	}

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
		request.setStatus(3);
		request.update();
		
		player.sendMessage( ModTRSMessage.parse( ModTRSMessage.closedMod, new Object[] { request.getId() } ) );
		
		ModTRSUser user_player = ModTRSUserTable.getUserFromId(request.getUserId());
		
		for( Player cur_user : player.getServer().getOnlinePlayers() ) {
		    if( cur_user.getName().equals( user_player.getName() ) ) {
			cur_user.sendMessage( ModTRSMessage.parse( ModTRSMessage.closedUser, new Object[] { request.getId() } ) );
		    }
		}
		
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
