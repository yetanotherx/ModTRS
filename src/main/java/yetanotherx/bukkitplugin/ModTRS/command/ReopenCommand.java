package yetanotherx.bukkitplugin.ModTRS.command;

import java.sql.SQLException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import yetanotherx.bukkitplugin.ModTRS.ModTRS;
import yetanotherx.bukkitplugin.ModTRS.event.CommandReopenEvent;
import yetanotherx.bukkitplugin.ModTRS.event.EventHandler;
import yetanotherx.bukkitplugin.ModTRS.util.ModTRSFunctions;
import yetanotherx.bukkitplugin.ModTRS.model.ModTRSRequest;
import yetanotherx.bukkitplugin.ModTRS.model.ModTRSUser;
import yetanotherx.bukkitplugin.ModTRS.util.Message;
import yetanotherx.bukkitplugin.ModTRS.validator.OneArgumentIntegerValidator;
import yetanotherx.bukkitplugin.ModTRS.validator.ValidatorHandler;

public class ReopenCommand implements CommandExecutor {

    private ModTRS parent;

    public ReopenCommand(ModTRS parent) {
        this.parent = parent;
        ValidatorHandler.getInstance().registerValidator("reopen", new OneArgumentIntegerValidator(this, parent));

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {

        ModTRSCommandSender player = new ModTRSCommandSender(sender);

        CommandReopenEvent event = new CommandReopenEvent(Integer.parseInt(args[0].replace(",", "")), player);
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

                if (!request.getStatusText(false).equals("Closed") && !request.getStatusText(false).equals("On Hold")) {
                    player.sendMessage(Message.parse("reopen.not_closed"));
                    return true;
                }

                request.setModId(0);
                request.setModTimestamp(0);
                request.setStatus(0);
                parent.getAPI().saveRow(request);

                ModTRSFunctions.messageMods(Message.parse("reopen.reopened.mods", request.getId()), player.getServer());

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
