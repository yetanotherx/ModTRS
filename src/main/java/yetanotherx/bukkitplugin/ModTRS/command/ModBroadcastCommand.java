package yetanotherx.bukkitplugin.ModTRS.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import yetanotherx.bukkitplugin.ModTRS.validator.BroadcastValidator;
import yetanotherx.bukkitplugin.ModTRS.validator.ValidatorHandler;
import yetanotherx.bukkitplugin.ModTRS.ModTRS;
import yetanotherx.bukkitplugin.ModTRS.util.ModTRSFunctions;
import yetanotherx.bukkitplugin.ModTRS.event.EventHandler;
import yetanotherx.bukkitplugin.ModTRS.event.CommandModBroadcastEvent;
import yetanotherx.bukkitplugin.ModTRS.util.Message;

public class ModBroadcastCommand implements CommandExecutor {

    public ModBroadcastCommand(ModTRS parent) {
	ValidatorHandler.getInstance().registerValidator( "mod-broadcast", new BroadcastValidator(this, parent) );
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {

	String joined = ModTRSFunctions.implode(args, " ");
	ModTRSCommandSender player = new ModTRSCommandSender(sender);

        CommandModBroadcastEvent event = new CommandModBroadcastEvent(joined, player);
        EventHandler.getInstance().dispatch(event);
        if (event.isCancelled()) {
            return true;
        }

        player = event.getSender();
        joined = event.getText();
	
	if( !player.hasPerm("modtrs.command.broadcast") ) {
	    player.sendMessage(Message.parse("general.error.permission"));
	}
	else {
	    ModTRSFunctions.messageMods( Message.parse("mod_broadcast.message", player.getName(), joined), player.getServer() );
	}

	return true;

    }

}
