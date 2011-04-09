package yetanotherx.bukkitplugin.ModTRS.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import yetanotherx.bukkitplugin.ModTRS.ModTRS;
import yetanotherx.bukkitplugin.ModTRS.ModTRSMessage;
import yetanotherx.bukkitplugin.ModTRS.ModTRSPermissions;
import yetanotherx.bukkitplugin.ModTRS.validator.ModTRSValidatorHandler;
import yetanotherx.bukkitplugin.ModTRS.validator.ModlistValidator;

public class ModlistCommand implements CommandExecutor {

    public ModlistCommand(ModTRS parent) {
	ModTRSValidatorHandler.getInstance().registerValidator( "modlist", new ModlistValidator(this, parent) );
	    
    }



    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {

	Player player = (Player) sender;
	
	if( !ModTRSPermissions.has(player, "modtrs.command.modlist", false) ) {
	    player.sendMessage(ModTRSMessage.noPermission);
	    return true;
	}
	
	Player[] players = player.getServer().getOnlinePlayers();
	String mods = "";
	
	for( Player user : players ) {
	    if( ModTRSPermissions.has(user, "modtrs.mod") ) {
		mods = mods + user.getName() + ", ";
	    }
	}
	
	if( mods.length() == 0 ) {
	    player.sendMessage( ModTRSMessage.noModerators );
	    return true;
	}
	
	mods = mods.substring(0, mods.length() - 2);
	String[] modlist = {mods};
	
	player.sendMessage( ModTRSMessage.parse( ModTRSMessage.modlist, modlist) );
	
	return true;

    }

}
