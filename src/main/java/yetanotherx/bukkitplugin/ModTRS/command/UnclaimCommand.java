package yetanotherx.bukkitplugin.ModTRS.command;

import java.sql.SQLException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import yetanotherx.bukkitplugin.ModTRS.ModTRS;
import yetanotherx.bukkitplugin.ModTRS.event.CommandUnclaimEvent;
import yetanotherx.bukkitplugin.ModTRS.event.EventHandler;
import yetanotherx.bukkitplugin.ModTRS.util.ModTRSFunctions;
import yetanotherx.bukkitplugin.ModTRS.model.ModTRSRequest;
import yetanotherx.bukkitplugin.ModTRS.model.ModTRSUser;
import yetanotherx.bukkitplugin.ModTRS.util.Message;
import yetanotherx.bukkitplugin.ModTRS.validator.OneArgumentIntegerValidator;
import yetanotherx.bukkitplugin.ModTRS.validator.ValidatorHandler;

public class UnclaimCommand implements CommandExecutor {

    private ModTRS parent;

    public UnclaimCommand(ModTRS parent) {
        this.parent = parent;
        ValidatorHandler.getInstance().registerValidator("unclaim", new OneArgumentIntegerValidator(this, parent));

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {

        ModTRSCommandSender player = new ModTRSCommandSender(sender);

        CommandUnclaimEvent event = new CommandUnclaimEvent(Integer.parseInt(args[0]), player);
        EventHandler.getInstance().dispatch(event);
        if (event.isCancelled()) {
            return true;
        }

        player = event.getSender();
        int id = event.getId();

        if (!player.hasPerm("modtrs.command.complete")) {
            player.sendMessage(Message.parse("general.error.permission"));
            return true;
        }

        try {
            ModTRSRequest request = parent.getTableHandler().getRequest().getRequestFromId(id);


            if (request != null) {

                ModTRSUser mod = parent.getAPI().getUserFromName(player.getName());

                if (request.getModId() != mod.getId()) {
                    if (!player.hasPerm("modtrs.command.complete.override")) {
                        player.sendMessage(Message.parse("unclaim.not_yours"));
                        return true;
                    }

                }

                if (!request.getStatusText(false).equals("Claimed")) {
                    player.sendMessage(Message.parse("unclaim.not_claimed"));
                    return true;
                }


                request.setModId(0);
                request.setModTimestamp((long) 0);
                request.setStatus(0);
                parent.getAPI().saveRow(request);
                
                ModTRSUser user = parent.getTableHandler().getUser().getUserFromId(request.getUserId());
                if( parent.getServer().getPlayer(user.getName()) != null ) {
                    parent.getServer().getPlayer(user.getName()).sendMessage(Message.parse("unclaim.unclaimed.user", mod.getName()));
                }

                ModTRSFunctions.messageMods(Message.parse("unclaim.unclaimed.mods", request.getId()), player.getServer());

            } else {
                player.sendMessage(Message.parse("general.error.not_a_req", id));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            player.sendMessage(Message.parse("general.error.internal"));
        }

        return true;

    }
}
