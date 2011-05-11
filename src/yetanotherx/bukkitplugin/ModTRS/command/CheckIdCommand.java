package yetanotherx.bukkitplugin.ModTRS.command;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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

public class CheckIdCommand implements CommandExecutor {

    private ModTRS parent;

    public CheckIdCommand(ModTRS parent) {
        this.parent = parent;
	ModTRSValidatorHandler.getInstance().registerValidator( "check-id", new CompleteValidator(this, parent) );
    }



    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {

	Player player = (Player) sender;

	if( !ModTRSPermissions.has(player, "modtrs.command.check") ) {
	    ModTRSMessage.general.sendPermissionError(player);
	}
	else {

	    try {
		ModTRSRequest request = ModTRSRequestTable.getRequestFromId( parent, Integer.parseInt( args[0] ) );


		if( request != null ) {

		    Calendar calendar = Calendar.getInstance();
		    Calendar calendarMod = Calendar.getInstance();
		    SimpleDateFormat sdf = new SimpleDateFormat(CommandHandler.TIMEDATE_FORMAT);

		    calendar.setTimeInMillis( request.getTimestamp() );
		    calendarMod.setTimeInMillis( request.getModTimestamp() );

		    ModTRSUser filedUser = ModTRSUserTable.getUserFromId(parent, request.getUserId());
		    ModTRSUser modUser = ModTRSUserTable.getUserFromId(parent, request.getModId());

                    ModTRSMessage.checkid.sendCheckIdHeader(player, request.getId(), request.getStatusText(true) );
		    
                    boolean online = ModTRSFunction.isUserOnline(filedUser.getName(), player.getServer());
                    ModTRSMessage.checkid.sendFiledBy(player, filedUser.getName(), sdf.format(calendar.getTime()), request.getX(), request.getY(), request.getZ(), online);

		    if( request.getModId() != 0 ) {
			ModTRSMessage.checkid.sendHandledBy(player, modUser.getName(), sdf.format(calendarMod.getTime()));
                        if( request.getModComment() != null && !request.getModComment().equals("") ) {
                            ModTRSMessage.checkid.sendModComment(player, request.getModComment());
                        }
                    }

                    ModTRSMessage.checkid.sendText(player, request.getText());

		}
		else {
		    ModTRSMessage.general.sendNoSuchRequest(player, Integer.parseInt( args[0] ) );
		}

	    }
	    catch( SQLException e ) {
		e.printStackTrace();
		ModTRSMessage.general.sendInternalError(player);
	    }

	}

	return true;

    }



}
