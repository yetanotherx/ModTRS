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
import yetanotherx.bukkitplugin.ModTRS.ModTRSSettings;
import yetanotherx.bukkitplugin.ModTRS.sql.ModTRSRequest;
import yetanotherx.bukkitplugin.ModTRS.sql.ModTRSUser;
import yetanotherx.bukkitplugin.ModTRS.sql.ModTRSUserTable;
import yetanotherx.bukkitplugin.ModTRS.validator.ModTRSValidatorHandler;
import yetanotherx.bukkitplugin.ModTRS.validator.ModreqValidator;

public class ModreqCommand implements CommandExecutor {
    private ModTRS parent;

    public ModreqCommand(ModTRS parent) {
        this.parent = parent;
	ModTRSValidatorHandler.getInstance().registerValidator( "modreq", new ModreqValidator(this, parent) );    
    }



    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {

	String[] split = args;
	String joined = ModTRSFunction.implode(split, " ");
	Player player = (Player) sender;

	if( !ModTRSPermissions.has(player, "modtrs.command.modreq", false) ) {
	    ModTRSMessage.general.sendPermissionError(player);
	    return true;
	}

	try {
	    
	    for( String blacklist : ModTRSSettings.blacklist ) {
		if( joined.indexOf(blacklist) != -1 ) {
                    ModTRSMessage.modreq.sendBlacklisted(player);
		    return true;
		}
	    }
	    
	    ModTRSUser user = ModTRSUserTable.getUserFromName(parent, player.getName());

	    if( user == null ) {
		user = new ModTRSUser();
		user.setName(player.getName());
		user.insert(parent);
		user = ModTRSUserTable.getUserFromName(parent, player.getName());
	    }

	    ModTRSRequest request = new ModTRSRequest();

	    request.setUserId(user.getId());
	    request.setText(joined);
	    request.setTimestamp(System.currentTimeMillis());
	    request.setWorld(player.getWorld().getName());
	    request.setX(player.getLocation().getBlockX());
	    request.setY(player.getLocation().getBlockY());
	    request.setZ(player.getLocation().getBlockZ());
	    request.insert(parent);
            ModTRSMessage.modreq.sendMessageSentUser(player);

	    if( ModTRSSettings.notifyMods ) {
		ModTRSFunction.messageMods( ModTRSMessage.modreq.getMessageSentMod(), player.getServer() );
	    }

	}
	catch( SQLException e ) {
	    e.printStackTrace();
	    ModTRSMessage.general.sendInternalError(player);
	}

	return true;

    }

}
