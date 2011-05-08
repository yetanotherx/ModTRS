package yetanotherx.bukkitplugin.ModTRS.command;

import java.sql.SQLException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import yetanotherx.bukkitplugin.ModTRS.ModTRS;
import yetanotherx.bukkitplugin.ModTRS.ModTRSFunction;
import yetanotherx.bukkitplugin.ModTRS.ModTRSMessage;
import yetanotherx.bukkitplugin.ModTRS.ModTRSPermissions;
import yetanotherx.bukkitplugin.ModTRS.sql.ModTRSRequest;
import yetanotherx.bukkitplugin.ModTRS.sql.ModTRSRequestTable;
import yetanotherx.bukkitplugin.ModTRS.sql.ModTRSUser;
import yetanotherx.bukkitplugin.ModTRS.sql.ModTRSUserTable;
import yetanotherx.bukkitplugin.ModTRS.validator.CompleteValidator;
import yetanotherx.bukkitplugin.ModTRS.validator.ModTRSValidatorHandler;

public class CompleteCommand implements CommandExecutor {

    private ModTRS parent;

    public CompleteCommand(ModTRS parent) {
        this.parent = parent;
        ModTRSValidatorHandler.getInstance().registerValidator("complete", new CompleteValidator(this, parent));
        ModTRSValidatorHandler.getInstance().registerValidator("done", new CompleteValidator(this, parent));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {

        Player player = (Player) sender;

        if (!ModTRSPermissions.has(player, "modtrs.command.complete")) {
            ModTRSMessage.general.sendPermissionError(player);
            return true;
        }

        try {
            ModTRSRequest request = ModTRSRequestTable.getRequestFromId(parent, Integer.parseInt(args[0]));


            if (request != null) {

                ModTRSUser user = ModTRSUserTable.getUserFromName(parent, player.getName());

                if (user == null) {
                    user = new ModTRSUser();
                    user.setName(player.getName());
                    user.insert(parent);
                    user = ModTRSUserTable.getUserFromName(parent, player.getName());
                }

                if (request.getStatusText(false).equals("Closed")) {
                    //TODO: Deny
                }

                String message = "";
                if (args.length > 1) {
                    String[] args2 = args.clone();
                    args2[0] = "";
                    if(  args[1].equals("-silent") ) {
                        args2[1] = "";
                    }
                    message = ModTRSFunction.implode(args2, " ").trim();
                }

                request.setModId(user.getId());
                request.setModTimestamp(System.currentTimeMillis());
                request.setModComment(message);
                request.setStatus(3);
                request.update(parent);

                ModTRSFunction.messageMods(ModTRSMessage.closed.getClosedMod(request.getId()), player.getServer());

                ModTRSUser user_player = ModTRSUserTable.getUserFromId(parent, request.getUserId());

                Player target = player.getServer().getPlayer(user_player.getName());
                if (target != null) {
                    if (args.length > 1 && args[1].equals("-silent")) {
                    } else {
                        ModTRSMessage.closed.sendClosedUser(target, player.getName());
                        if (!message.equals("")) {
                            ModTRSMessage.closed.sendModComment(target, message);
                        }
                    }
                }

            } else {
                ModTRSMessage.general.sendNoSuchRequest(player, Integer.parseInt(args[0]));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            ModTRSMessage.general.sendInternalError(player);
        }

        return true;

    }
}
