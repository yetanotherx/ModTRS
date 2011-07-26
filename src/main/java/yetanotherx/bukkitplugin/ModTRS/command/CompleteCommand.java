package yetanotherx.bukkitplugin.ModTRS.command;

import java.sql.SQLException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import yetanotherx.bukkitplugin.ModTRS.ModTRS;
import yetanotherx.bukkitplugin.ModTRS.event.CommandCompleteEvent;
import yetanotherx.bukkitplugin.ModTRS.event.EventHandler;
import yetanotherx.bukkitplugin.ModTRS.util.ModTRSFunctions;
import yetanotherx.bukkitplugin.ModTRS.model.ModTRSRequest;
import yetanotherx.bukkitplugin.ModTRS.model.ModTRSUser;
import yetanotherx.bukkitplugin.ModTRS.util.Message;
import yetanotherx.bukkitplugin.ModTRS.validator.OneArgumentIntegerValidator;
import yetanotherx.bukkitplugin.ModTRS.validator.ValidatorHandler;

public class CompleteCommand implements CommandExecutor {

    private ModTRS parent;

    public CompleteCommand(ModTRS parent) {
        this.parent = parent;
        ValidatorHandler.getInstance().registerValidator("complete", new OneArgumentIntegerValidator(this, parent));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {

        ModTRSCommandSender player = new ModTRSCommandSender(sender);

        CommandCompleteEvent event = new CommandCompleteEvent(Integer.parseInt(args[0]), player);
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

                if (request.getStatusText(false).equals("Closed")) {
                    player.sendMessage(Message.parse("closed.not_open"));
                    return true;
                }

                String message = "";
                if (args.length > 1) {
                    String[] args2 = args.clone();
                    args2[0] = "";
                    if (args[1].equals("-silent")) {
                        args2[1] = "";
                    }
                    message = ModTRSFunctions.implode(args2, " ").trim();
                }

                request.setModId(user.getId());
                request.setModTimestamp(System.currentTimeMillis());
                request.setModComment(message);
                request.setStatus(3);
                parent.getAPI().saveRow(request);

                ModTRSFunctions.messageMods(Message.parse("closed.mods", request.getId()), player.getServer());
                if (!message.isEmpty()) {
                    ModTRSFunctions.messageMods(Message.parse("closed.mod_comment", message), player.getServer());
                }
                
                ModTRSUser user_player = parent.getTableHandler().getUser().getUserFromId(request.getUserId());

                Player target = player.getServer().getPlayer(user_player.getName());
                if (target != null) {
                    if (args.length > 1 && args[1].equals("-silent")) {
                    } else {
                        target.sendMessage(Message.parse("closed.user", player.getName()));
                        target.sendMessage(Message.parse("closed.text", request.getText()));
                        if (!message.isEmpty()) {
                            target.sendMessage(Message.parse("closed.mod_comment", message));
                        }
                    }
                    request.setNotifiedOfCompletion(1);
                    parent.getAPI().saveRow(request);
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
