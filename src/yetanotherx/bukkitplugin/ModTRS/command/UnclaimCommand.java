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

public class UnclaimCommand implements CommandExecutor {
    private ModTRS parent;

    public UnclaimCommand(ModTRS parent) {
        this.parent = parent;
	ModTRSValidatorHandler.getInstance().registerValidator( "unclaim", new CompleteValidator(this, parent) );
	
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

		ModTRSUser user = ModTRSUserTable.getUserFromName( parent, player.getName());

		if( user == null ) {
		    user = new ModTRSUser();
		    user.setName(player.getName());
		    user.insert(parent);
		    user = ModTRSUserTable.getUserFromName(parent, player.getName());
		}

		if( request.getModId() != user.getId() ) {
		    if( !ModTRSPermissions.has(player, "modtrs.command.complete.override") ) {
			ModTRSMessage.unclaim.sendNotClaimedByMod(player);
			return true;
		    }

		}

                if( !request.getStatusText(false).equals("Claimed") ) {
                    //TODO: Deny
                }


		request.setModId(0);
		request.setModTimestamp( (long) 0 );
		request.setStatus(0);
		request.update(parent);

		ModTRSFunction.messageMods( ModTRSMessage.unclaim.getUnclaimedMods( request.getId()), player.getServer() );
		
	    }
	    else {
		ModTRSMessage.general.sendNoSuchRequest(player, Integer.parseInt( args[0] ));
	    }

	}
	catch( SQLException e ) {
	    e.printStackTrace();
	    ModTRSMessage.general.sendInternalError(player);
	}

	return true;

    }

}
