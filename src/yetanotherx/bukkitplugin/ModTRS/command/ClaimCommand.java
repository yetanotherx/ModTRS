package yetanotherx.bukkitplugin.ModTRS.command;

import java.sql.SQLException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import yetanotherx.bukkitplugin.ModTRS.ModTRS;
import yetanotherx.bukkitplugin.ModTRS.ModTRSFunction;
import yetanotherx.bukkitplugin.ModTRS.ModTRSMessage;
import yetanotherx.bukkitplugin.ModTRS.ModTRSPermissions;
import yetanotherx.bukkitplugin.ModTRS.sql.ModTRSRequest;
import yetanotherx.bukkitplugin.ModTRS.sql.ModTRSRequestTable;
import yetanotherx.bukkitplugin.ModTRS.sql.ModTRSUser;
import yetanotherx.bukkitplugin.ModTRS.sql.ModTRSUserTable;
import yetanotherx.bukkitplugin.ModTRS.validator.CompleteValidator;
import yetanotherx.bukkitplugin.ModTRS.validator.ModTRSValidatorHandler;

public class ClaimCommand implements CommandExecutor {

    private ModTRS parent;

    public ClaimCommand(ModTRS parent) {
        this.parent = parent;
	ModTRSValidatorHandler.getInstance().registerValidator( "claim", new CompleteValidator(this, parent) );
	
    }



    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {

	Player player = (Player) sender;
	
	if( !ModTRSPermissions.has(player, "modtrs.command.complete") ) {
	    ModTRSMessage.general.sendPermissionError(player);
	    return true;
	}

	try {
	    ModTRSRequest request = ModTRSRequestTable.getRequestFromId( parent, Integer.parseInt( args[0] ) );


	    if( request != null ) {

		ModTRSUser user = ModTRSUserTable.getUserFromName(parent, player.getName());

		if( user == null ) {
		    user = new ModTRSUser();
		    user.setName(player.getName());
		    user.insert( parent );
		    user = ModTRSUserTable.getUserFromName(parent, player.getName());
		}

                if( request.getModId() != 0 ) {
                    //TODO: Deny
                }
                if( !request.getStatusText(false).equals("Open") ) {
                    //TODO: Deny
                }

		request.setModId(user.getId());
		request.setModTimestamp(System.currentTimeMillis());
		request.setStatus(1);
		request.update( parent );
		
		ModTRSFunction.messageMods( ModTRSMessage.claim.getClaimedMods(user.getName(), request.getId() ), player.getServer() );

	    }
	    else {
		ModTRSMessage.general.sendNoSuchRequest(player, Integer.parseInt( args[0] ) );
	    }

	}
	catch( SQLException e ) {
	    e.printStackTrace();
	    ModTRSMessage.general.sendInternalError(player);
	}

	return true;

    }

}
