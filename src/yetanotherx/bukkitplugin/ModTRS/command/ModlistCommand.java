package yetanotherx.bukkitplugin.ModTRS.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import yetanotherx.bukkitplugin.ModTRS.ModTRS;
import yetanotherx.bukkitplugin.ModTRS.ModTRSMessage;
import yetanotherx.bukkitplugin.ModTRS.ModTRSPermissions;

public class ModlistCommand implements CommandExecutor {

    public ModlistCommand(ModTRS parent) {
    }



    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {

	Player player = (Player) sender;
	
	if( !ModTRSPermissions.has(player, "modtrs.command.modlist") ) {
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
	
	mods = mods.substring(0, mods.length() - 2);
	String[] modlist = {mods};
	
	player.sendMessage( ModTRSMessage.parse( ModTRSMessage.modlist, modlist) );
	
	return true;

    }

}
