package yetanotherx.bukkitplugin.ModTRS.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import yetanotherx.bukkitplugin.ModTRS.validator.ValidatorHandler;
import yetanotherx.bukkitplugin.ModTRS.ModTRS;
import yetanotherx.bukkitplugin.ModTRS.event.CommandVersionEvent;
import yetanotherx.bukkitplugin.ModTRS.event.EventHandler;
import yetanotherx.bukkitplugin.ModTRS.util.Message;
import yetanotherx.bukkitplugin.ModTRS.validator.OneArgumentValidator;

public class VersionCommand implements CommandExecutor {

    private ModTRS parent;
    
    public VersionCommand(ModTRS parent) {
        this.parent = parent;
	ValidatorHandler.getInstance().registerValidator( "version", new OneArgumentValidator(this, parent) );
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {

	ModTRSCommandSender player = new ModTRSCommandSender(sender);

        CommandVersionEvent event = new CommandVersionEvent(player);
        EventHandler.getInstance().dispatch(event);
        if (event.isCancelled()) {
            return true;
        }

        player = event.getSender();
	
	if( !player.hasPerm("modtrs.command.version", false) ) {
	    player.sendMessage(Message.parse("general.error.permission"));
	}
	else {
	    player.sendMessage("[ModTRS] You're running " + parent.getDescription().getName() + " version " + parent.getDescription().getVersion());
	}

	return true;

    }

}
