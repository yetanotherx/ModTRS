package yetanotherx.bukkitplugin.ModTRS.command;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import yetanotherx.bukkitplugin.ModTRS.validator.EmptyValidator;
import yetanotherx.bukkitplugin.ModTRS.validator.ValidatorHandler;
import yetanotherx.bukkitplugin.ModTRS.ModTRS;
import yetanotherx.bukkitplugin.ModTRS.event.CommandHelpEvent;
import yetanotherx.bukkitplugin.ModTRS.event.EventHandler;
import yetanotherx.bukkitplugin.ModTRS.util.ModTRSHelp;

public class HelpCommand implements CommandExecutor {

    public HelpCommand(ModTRS parent) {
	ValidatorHandler.getInstance().registerValidator( "help", new EmptyValidator(this, parent) );
	
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {

	ModTRSCommandSender player = new ModTRSCommandSender(sender);

        CommandHelpEvent event = new CommandHelpEvent(player);
        EventHandler.getInstance().dispatch(event);
        if (event.isCancelled()) {
            return true;
        }

        player = event.getSender();
	
	ArrayList<String> commands = ModTRSHelp.getMessages(player);
	
	for( String commandString : commands ) {
	    player.sendMessage(commandString);
	}
	

	return true;

    }
}
