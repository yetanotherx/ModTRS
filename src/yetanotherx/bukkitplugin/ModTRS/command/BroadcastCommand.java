package yetanotherx.bukkitplugin.ModTRS.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import yetanotherx.bukkitplugin.ModTRS.ModTRS;
import yetanotherx.bukkitplugin.ModTRS.ModTRSMessage;
import yetanotherx.bukkitplugin.ModTRS.ModTRSPermissions;

public class BroadcastCommand implements CommandExecutor {

    public BroadcastCommand(ModTRS parent) {
    }



    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {

	String[] split = args;
	String joined = CommandHandler.implode(split, " ");
	Player player = (Player) sender;
	
	if( !ModTRSPermissions.has(player, "modtrs.command.broadcast") ) {
	    player.sendMessage(ModTRSMessage.noPermission);
	    return true;
	}
	
	Player[] players = player.getServer().getOnlinePlayers();

	String[] message = {joined};

	for( Player user : players ) {
	    if( ModTRSPermissions.has(user, "modtrs.mod") ) {
		user.sendMessage(ModTRSMessage.parse( ModTRSMessage.modbroadcast, message ) );
	    }
	}

	return true;

    }

}
