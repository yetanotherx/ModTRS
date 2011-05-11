package yetanotherx.bukkitplugin.ModTRS.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import yetanotherx.bukkitplugin.ModTRS.ModTRS;
import yetanotherx.bukkitplugin.ModTRS.ModTRSMessage;
import yetanotherx.bukkitplugin.ModTRS.ModTRSPermissions;
import yetanotherx.bukkitplugin.ModTRS.ModTRSSettings;
import yetanotherx.bukkitplugin.ModTRS.validator.ModTRSValidatorHandler;
import yetanotherx.bukkitplugin.ModTRS.validator.ReloadValidator;

public class ReloadCommand implements CommandExecutor {

    public ReloadCommand(ModTRS parent) {
        ModTRSValidatorHandler.getInstance().registerValidator("modtrs-reload", new ReloadValidator(this, parent));

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {

        if (!(sender instanceof ConsoleCommandSender)) {
            Player player = (Player) sender;


            if (!ModTRSPermissions.has(player, "modtrs.command.reload", false)) {
                ModTRSMessage.general.sendPermissionError(player);
                return true;
            }

        }

        ModTRSSettings.load();

        sender.sendMessage(ChatColor.GRAY + "ModTRS configuration loaded");

        return true;

    }
}
