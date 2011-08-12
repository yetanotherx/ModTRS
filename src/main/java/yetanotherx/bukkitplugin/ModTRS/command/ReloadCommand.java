package yetanotherx.bukkitplugin.ModTRS.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import yetanotherx.bukkitplugin.ModTRS.ModTRS;
import yetanotherx.bukkitplugin.ModTRS.event.CommandReloadEvent;
import yetanotherx.bukkitplugin.ModTRS.event.EventHandler;
import yetanotherx.bukkitplugin.ModTRS.util.Message;
import yetanotherx.bukkitplugin.ModTRS.util.ModTRSSettings;
import yetanotherx.bukkitplugin.ModTRS.validator.NoArgumentsValidator;
import yetanotherx.bukkitplugin.ModTRS.validator.ValidatorHandler;

public class ReloadCommand implements CommandExecutor {

    private ModTRS parent;
    
    public ReloadCommand(ModTRS parent) {
        this.parent = parent;
        ValidatorHandler.getInstance().registerValidator("reload", new NoArgumentsValidator(this, parent));

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {

        ModTRSCommandSender player = new ModTRSCommandSender(sender);

        CommandReloadEvent event = new CommandReloadEvent(player);
        EventHandler.getInstance().dispatch(event);
        if (event.isCancelled()) {
            return true;
        }

        player = event.getSender();

        if (!player.hasPerm("modtrs.command.reload", false)) {
            player.sendMessage(Message.parse("general.error.permission"));
            return true;
        }
        ModTRSSettings.load(parent);

        sender.sendMessage(ChatColor.GRAY + "ModTRS configuration loaded");

        return true;

    }
}
