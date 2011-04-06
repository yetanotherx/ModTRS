package yetanotherx.bukkitplugin.ModTRS.command;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import yetanotherx.bukkitplugin.ModTRS.validator.HelpValidator;
import yetanotherx.bukkitplugin.ModTRS.validator.ModTRSValidatorHandler;
import yetanotherx.bukkitplugin.ModTRS.ModTRS;
import yetanotherx.bukkitplugin.ModTRS.ModTRSHelp;

public class HelpCommand implements CommandExecutor {

    public HelpCommand(ModTRS parent) {
	ModTRSValidatorHandler.getInstance().registerValidator( "modreq-help", new HelpValidator(this, parent) );
	
    }



    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {

	Player player = (Player) sender;
	
	ArrayList<String> commands = ModTRSHelp.getMessages(player);
	
	for( String commandString : commands ) {
	    player.sendMessage(commandString);
	}
	

	return true;

    }
}
