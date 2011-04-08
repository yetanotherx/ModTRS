package yetanotherx.bukkitplugin.ModTRS.command;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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

public class CheckIdCommand implements CommandExecutor {

    public CheckIdCommand(ModTRS parent) {
	ModTRSValidatorHandler.getInstance().registerValidator( "check-id", new CompleteValidator(this, parent) );
    }



    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {

	Player player = (Player) sender;

	if( !ModTRSPermissions.has(player, "modtrs.command.check") ) {
	    player.sendMessage(ModTRSMessage.noPermission);
	}
	else {

	    try {
		ModTRSRequest request = ModTRSRequestTable.getRequestFromId( Integer.parseInt( args[0] ) );


		if( request != null ) {

		    Calendar calendar = Calendar.getInstance();
		    Calendar calendarMod = Calendar.getInstance();
		    SimpleDateFormat sdf = new SimpleDateFormat(CommandHandler.TIMEDATE_FORMAT);

		    calendar.setTimeInMillis( request.getTimestamp() );
		    calendarMod.setTimeInMillis( request.getModTimestamp() );

		    ModTRSUser filedUser = ModTRSUserTable.getUserFromId(request.getUserId());
		    ModTRSUser modUser = ModTRSUserTable.getUserFromId(request.getModId());

		    Object[] parameters = { 
			    request.getId(), request.getStatusText(true),
			    filedUser.getName(), sdf.format(calendar.getTime()),
			    request.getX(), request.getY(), request.getZ(),
			    request.getText() };


		    player.sendMessage( ModTRSMessage.parse( ModTRSMessage.infoForRequest, parameters ) );
		    player.sendMessage( ModTRSMessage.parse( ModTRSMessage.filedBy, parameters ) );

		    if( request.getModId() != 0 ) {
			Object[] modParameters = { modUser.getName(), sdf.format(calendarMod.getTime()) };
			player.sendMessage( ModTRSMessage.parse( ModTRSMessage.handledBy, modParameters ) );
		    }

		    player.sendMessage( ModTRSMessage.parse( ModTRSMessage.requestText, parameters ) );

		}
		else {
		    player.sendMessage( ModTRSMessage.noSuchRequest );
		}

	    }
	    catch( SQLException e ) {
		e.printStackTrace();
		player.sendMessage( ModTRSMessage.internalError );
	    }

	}

	return true;

    }



}
