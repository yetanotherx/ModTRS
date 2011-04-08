package yetanotherx.bukkitplugin.ModTRS.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import yetanotherx.bukkitplugin.ModTRS.validator.BroadcastValidator;
import yetanotherx.bukkitplugin.ModTRS.validator.ModTRSValidatorHandler;
import yetanotherx.bukkitplugin.ModTRS.ModTRS;
import yetanotherx.bukkitplugin.ModTRS.ModTRSFunction;
import yetanotherx.bukkitplugin.ModTRS.ModTRSMessage;
import yetanotherx.bukkitplugin.ModTRS.ModTRSPermissions;

public class BroadcastCommand implements CommandExecutor {

    public BroadcastCommand(ModTRS parent) {
	ModTRSValidatorHandler.getInstance().registerValidator( "mod-broadcast", new BroadcastValidator(this, parent) );
    }



    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {

	String[] split = args;
	String joined = ModTRSFunction.implode(split, " ");
	Player player = (Player) sender;
	
	if( !ModTRSPermissions.has(player, "modtrs.command.broadcast") ) {
	    player.sendMessage(ModTRSMessage.noPermission);
	}
	else {
	    ModTRSFunction.messageMods( ModTRSMessage.parse( ModTRSMessage.modbroadcast, new String[] {joined} ), player.getServer() );
	}

	return true;

    }

}
