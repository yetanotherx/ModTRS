package yetanotherx.bukkitplugin.ModTRS.command;

import java.sql.SQLException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import yetanotherx.bukkitplugin.ModTRS.ModTRS;
import yetanotherx.bukkitplugin.ModTRS.event.CommandHoldEvent;
import yetanotherx.bukkitplugin.ModTRS.event.EventHandler;
import yetanotherx.bukkitplugin.ModTRS.util.ModTRSFunctions;
import yetanotherx.bukkitplugin.ModTRS.model.ModTRSRequest;
import yetanotherx.bukkitplugin.ModTRS.model.ModTRSUser;
import yetanotherx.bukkitplugin.ModTRS.util.Message;
import yetanotherx.bukkitplugin.ModTRS.validator.CompleteValidator;
import yetanotherx.bukkitplugin.ModTRS.validator.ValidatorHandler;

public class HoldCommand implements CommandExecutor {

    private ModTRS parent;

    public HoldCommand(ModTRS parent) {
        this.parent = parent;
        ValidatorHandler.getInstance().registerValidator("hold", new CompleteValidator(this, parent));

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {

        ModTRSCommandSender player = new ModTRSCommandSender(sender);

        CommandHoldEvent event = new CommandHoldEvent(Integer.parseInt(args[0]), player);
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

                ModTRSUser user = parent.getAPI().getUserFromName(player.getName());

                if (request.getStatusText(false).equals("Closed") || request.getStatusText(false).equals("On Hold")) {
                    player.sendMessage(Message.parse("hold.not_open"));
                    return true;
                }

                request.setModId(user.getId());
                request.setModTimestamp(System.currentTimeMillis());
                request.setStatus(2);
                parent.getDatabase().save(request);

                ModTRSFunctions.messageMods(Message.parse("hold.held.mods", request.getId()), player.getServer());

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
