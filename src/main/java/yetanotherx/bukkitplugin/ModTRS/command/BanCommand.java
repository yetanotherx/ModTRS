package yetanotherx.bukkitplugin.ModTRS.command;

import java.sql.SQLException;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import yetanotherx.bukkitplugin.ModTRS.ModTRS;
import yetanotherx.bukkitplugin.ModTRS.event.CommandBanEvent;
import yetanotherx.bukkitplugin.ModTRS.event.EventHandler;
import yetanotherx.bukkitplugin.ModTRS.model.ModTRSUser;
import yetanotherx.bukkitplugin.ModTRS.util.Message;
import yetanotherx.bukkitplugin.ModTRS.util.ModTRSFunctions;
import yetanotherx.bukkitplugin.ModTRS.validator.OneArgumentValidator;
import yetanotherx.bukkitplugin.ModTRS.validator.ValidatorHandler;

public class BanCommand implements CommandExecutor {

    private ModTRS parent;

    public BanCommand(ModTRS parent) {
        this.parent = parent;
        ValidatorHandler.getInstance().registerValidator("modreq-ban", new OneArgumentValidator(this, parent));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {

        ModTRSCommandSender player = new ModTRSCommandSender(sender);

        CommandBanEvent event = new CommandBanEvent(args[0], player);
        EventHandler.getInstance().dispatch(event);
        if (event.isCancelled()) {
            return true;
        }

        player = event.getSender();
        String username = event.getUsername();

        if (!player.hasPerm("modtrs.command.ban")) {
            player.sendMessage(Message.parse("general.error.permission"));
            return true;
        }

        try {
            ModTRSUser user = parent.getAPI().getUserFromName(username);

            if( user.getBanned() == 1 ) {
                player.sendMessage(Message.parse("ban.already_banned", user.getName()));
                return true;
            }
            
            user.setBanned(1);
            user.save(parent);
            
            ModTRSFunctions.messageMods(Message.parse("ban.mods", user.getName()), player.getServer());
            

        } catch (SQLException e) {
            e.printStackTrace();
            player.sendMessage(Message.parse("general.error.internal"));
        }

        return true;

    }
    
}
