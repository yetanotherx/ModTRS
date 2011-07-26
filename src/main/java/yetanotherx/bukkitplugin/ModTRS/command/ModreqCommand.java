package yetanotherx.bukkitplugin.ModTRS.command;

import java.sql.SQLException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import yetanotherx.bukkitplugin.ModTRS.ModTRS;
import yetanotherx.bukkitplugin.ModTRS.event.CommandModreqEvent;
import yetanotherx.bukkitplugin.ModTRS.event.EventHandler;
import yetanotherx.bukkitplugin.ModTRS.util.ModTRSFunctions;
import yetanotherx.bukkitplugin.ModTRS.util.ModTRSSettings;
import yetanotherx.bukkitplugin.ModTRS.model.ModTRSRequest;
import yetanotherx.bukkitplugin.ModTRS.model.ModTRSUser;
import yetanotherx.bukkitplugin.ModTRS.util.Message;
import yetanotherx.bukkitplugin.ModTRS.validator.ValidatorHandler;
import yetanotherx.bukkitplugin.ModTRS.validator.ModreqValidator;

public class ModreqCommand implements CommandExecutor {

    private ModTRS parent;

    public ModreqCommand(ModTRS parent) {
        this.parent = parent;
        ValidatorHandler.getInstance().registerValidator("modreq", new ModreqValidator(this, parent));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {

        String joined = ModTRSFunctions.implode(args, " ");
        ModTRSCommandSender player = new ModTRSCommandSender(sender);

        CommandModreqEvent event = new CommandModreqEvent(joined, player);
        EventHandler.getInstance().dispatch(event);
        if (event.isCancelled()) {
            return true;
        }
        
        joined = event.getText();
        player = event.getSender();

        if (!player.hasPerm("modtrs.command.modreq", false)) {
            player.sendMessage(Message.parse("general.error.permission"));
            return true;
        }

        try {

            for (String blacklist : ModTRSSettings.blacklist) {
                if (joined.indexOf(blacklist) != -1) {
                    player.sendMessage(Message.parse("modreq.error.blacklisted"));
                    return true;
                }
            }

            ModTRSUser user = parent.getAPI().getUserFromName(player.getName());
            
            if( user.getBanned() != 0 ) {
                player.sendMessage(Message.parse("modreq.error.banned"));
                return true;
            }

            if (ModTRSSettings.maxRequests > 0) {

                int request_count = parent.getTableHandler().getRequest().getNumberOfRequestsFromUser(user.getId());

                if (request_count >= ModTRSSettings.maxRequests) {
                    player.sendMessage(Message.parse("modreq.error.too_many", request_count));
                    return true;
                }

            }

            ModTRSRequest request = new ModTRSRequest();    
            request.setUserId(user.getId());
            request.setText(joined);
            request.setTstamp(System.currentTimeMillis());
            request.setWorld(player.getWorld().getName());
            request.setX(player.getLocation().getBlockX());
            request.setY(player.getLocation().getBlockY());
            request.setZ(player.getLocation().getBlockZ());
            parent.getAPI().saveRow(request);
            player.sendMessage(Message.parse("modreq.sent.user"));

            if (ModTRSSettings.notifyMods) {
                ModTRSFunctions.messageMods(Message.parse("modreq.sent.mods"), player.getServer());
            }

        } catch (SQLException e) {
            e.printStackTrace();
            player.sendMessage(Message.parse("general.error.internal"));
        }

        return true;

    }
}
