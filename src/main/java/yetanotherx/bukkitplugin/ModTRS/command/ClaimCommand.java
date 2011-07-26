package yetanotherx.bukkitplugin.ModTRS.command;

import java.sql.SQLException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import yetanotherx.bukkitplugin.ModTRS.ModTRS;
import yetanotherx.bukkitplugin.ModTRS.event.CommandClaimEvent;
import yetanotherx.bukkitplugin.ModTRS.event.EventHandler;
import yetanotherx.bukkitplugin.ModTRS.util.ModTRSFunctions;
import yetanotherx.bukkitplugin.ModTRS.model.ModTRSRequest;
import yetanotherx.bukkitplugin.ModTRS.model.ModTRSUser;
import yetanotherx.bukkitplugin.ModTRS.util.Message;
import yetanotherx.bukkitplugin.ModTRS.validator.CompleteValidator;
import yetanotherx.bukkitplugin.ModTRS.validator.ValidatorHandler;

public class ClaimCommand implements CommandExecutor {

    private ModTRS parent;

    public ClaimCommand(ModTRS parent) {
        this.parent = parent;
        ValidatorHandler.getInstance().registerValidator("claim", new CompleteValidator(this, parent));

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {

        ModTRSCommandSender player = new ModTRSCommandSender(sender);

        CommandClaimEvent event = new CommandClaimEvent(Integer.parseInt(args[0]), player);
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

                if (request.getModId() != 0) {
                    player.sendMessage(Message.parse("claim.already_claimed"));
                    return true;
                }
                if (!request.getStatusText(false).equals("Open")) {
                    player.sendMessage(Message.parse("claim.not_open"));
                    return true;
                }

                request.setModId(user.getId());
                request.setModTimestamp(System.currentTimeMillis());
                request.setStatus(1);
                parent.getAPI().saveRow(request);

                ModTRSFunctions.messageMods(Message.parse("claim.mods", user.getName(), request.getId()), player.getServer());
                
                ModTRSUser user_player = parent.getTableHandler().getUser().getUserFromId(request.getUserId());

                Player target = player.getServer().getPlayer(user_player.getName());
                if (target != null) {
                    target.sendMessage(Message.parse("claim.user", player.getName()));
                    target.sendMessage(Message.parse("claim.text", request.getText()));
                }

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
