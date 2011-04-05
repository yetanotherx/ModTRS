package yetanotherx.bukkitplugin.ModTRS.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import yetanotherx.bukkitplugin.ModTRS.ModTRS;

public class HoldCommand implements CommandExecutor {

    private ModTRS parent;

    public HoldCommand(ModTRS parent) {
	this.parent = parent;
    }



    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {

	String[] split = args;
	String joined = CommandHandler.implode(split, " ");
	String commandName = command.getName().toLowerCase();
	Player player = (Player) sender;

	return false;

    }

}
