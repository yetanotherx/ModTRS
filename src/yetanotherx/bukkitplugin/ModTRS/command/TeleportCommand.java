package yetanotherx.bukkitplugin.ModTRS.command;

import java.sql.SQLException;
import org.bukkit.Location;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import yetanotherx.bukkitplugin.ModTRS.ModTRS;
import yetanotherx.bukkitplugin.ModTRS.ModTRSMessage;
import yetanotherx.bukkitplugin.ModTRS.ModTRSPermissions;
import yetanotherx.bukkitplugin.ModTRS.sql.ModTRSRequest;
import yetanotherx.bukkitplugin.ModTRS.sql.ModTRSRequestTable;
import yetanotherx.bukkitplugin.ModTRS.validator.CompleteValidator;
import yetanotherx.bukkitplugin.ModTRS.validator.ModTRSValidatorHandler;

public class TeleportCommand implements CommandExecutor {
    private ModTRS parent;

    public TeleportCommand(ModTRS parent) {
        this.parent = parent;
	ModTRSValidatorHandler.getInstance().registerValidator( "tp-id", new CompleteValidator(this, parent) );

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {

	Player player = (Player) sender;

	if( !ModTRSPermissions.has(player, "modtrs.command.teleport") ) {
	    ModTRSMessage.general.sendPermissionError(player);
	    return true;
	}

	try {
	    ModTRSRequest request = ModTRSRequestTable.getRequestFromId( parent, Integer.parseInt( args[0] ) );


	    if( request != null ) {

                Location location = new Location( player.getWorld(), request.getX(), request.getY() + 1, request.getZ() );
		player.teleport(location);

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
