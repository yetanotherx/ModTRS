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

public class ReopenCommand implements CommandExecutor {

    public ReopenCommand(ModTRS parent) {
        ModTRSValidatorHandler.getInstance().registerValidator("reopen", new CompleteValidator(this, parent));

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {

        Player player = (Player) sender;

        if (!ModTRSPermissions.has(player, "modtrs.command.complete")) {
            player.sendMessage(ModTRSMessage.noPermission);
            return true;
        }

        try {
            ModTRSRequest request = ModTRSRequestTable.getRequestFromId(Integer.parseInt(args[0]));


            if (request != null) {

                ModTRSUser user = ModTRSUserTable.getUserFromName(player.getName());

                if (user == null) {
                    user = new ModTRSUser();
                    user.setName(player.getName());
                    user.insert();
                    user = ModTRSUserTable.getUserFromName(player.getName());
                }

                request.setModId(0);
                request.setModTimestamp(0);
                request.setStatus(0);
                request.update();

                ModTRSFunction.messageMods(ModTRSMessage.parse(ModTRSMessage.reopened, new Object[]{request.getId(), user.getName()}), player.getServer());

            } else {
                player.sendMessage(ModTRSMessage.noSuchRequest);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            player.sendMessage(ModTRSMessage.internalError);
        }

        return true;

    }
}
