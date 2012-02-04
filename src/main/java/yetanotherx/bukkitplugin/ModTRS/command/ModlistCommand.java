package yetanotherx.bukkitplugin.ModTRS.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import yetanotherx.bukkitplugin.ModTRS.ModTRS;
import yetanotherx.bukkitplugin.ModTRS.event.CommandModlistEvent;
import yetanotherx.bukkitplugin.ModTRS.event.EventHandler;
import yetanotherx.bukkitplugin.ModTRS.util.Message;
import yetanotherx.bukkitplugin.ModTRS.util.ModTRSPermissions;
import yetanotherx.bukkitplugin.ModTRS.validator.NoArgumentsValidator;
import yetanotherx.bukkitplugin.ModTRS.validator.ValidatorHandler;

public class ModlistCommand implements CommandExecutor {

    public ModlistCommand(ModTRS parent) {
        ValidatorHandler.getInstance().registerValidator("modlist", new NoArgumentsValidator(this, parent));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {

        ModTRSCommandSender player = new ModTRSCommandSender(sender);

        CommandModlistEvent event = new CommandModlistEvent(player);
        EventHandler.getInstance().dispatch(event);
        if (event.isCancelled()) {
            return true;
        }

        player = event.getSender();

        if (!player.hasPerm("modtrs.command.modlist", false)) {
            player.sendMessage(Message.parse("general.error.permission"));
            return true;
        }

        Player[] players = player.getServer().getOnlinePlayers();
        String mods = "";

        for (Player user : players) {
            // Skip invisible mods
            if (sender instanceof Player && !((Player)sender).canSee(user)) {
                continue;
            }

            if (ModTRSPermissions.has(user, "modtrs.mod")) {
                mods = mods + user.getName() + ", ";
            }
        }

        if (mods.length() == 0) {
            player.sendMessage(Message.parse("modlist.no_mods"));
            return true;
        }

        mods = mods.substring(0, mods.length() - 2);

        player.sendMessage(Message.parse("modlist.message", mods));
        return true;

    }
}
