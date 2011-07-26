package yetanotherx.bukkitplugin.ModTRS.command;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.bukkit.ChatColor;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import yetanotherx.bukkitplugin.ModTRS.ModTRS;
import yetanotherx.bukkitplugin.ModTRS.util.ModTRSFunctions;
import yetanotherx.bukkitplugin.ModTRS.event.EventHandler;
import yetanotherx.bukkitplugin.ModTRS.event.CommandCheckIdEvent;
import yetanotherx.bukkitplugin.ModTRS.model.ModTRSRequest;
import yetanotherx.bukkitplugin.ModTRS.model.ModTRSUser;
import yetanotherx.bukkitplugin.ModTRS.util.Message;
import yetanotherx.bukkitplugin.ModTRS.validator.OneArgumentIntegerValidator;
import yetanotherx.bukkitplugin.ModTRS.validator.ValidatorHandler;

public class CheckIdCommand implements CommandExecutor {

    private ModTRS parent;

    public CheckIdCommand(ModTRS parent) {
        this.parent = parent;
        ValidatorHandler.getInstance().registerValidator("check-id", new OneArgumentIntegerValidator(this, parent));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {

        ModTRSCommandSender player = new ModTRSCommandSender(sender);

        CommandCheckIdEvent event = new CommandCheckIdEvent(Integer.parseInt(args[0]), player);
        EventHandler.getInstance().dispatch(event);
        if (event.isCancelled()) {
            return true;
        }

        player = event.getSender();
        int id = event.getId();

        if (!player.hasPerm("modtrs.command.check")) {
            player.sendMessage(Message.parse("general.error.permission"));
        } else {

            try {
                ModTRSRequest request = parent.getTableHandler().getRequest().getRequestFromId(id);

                if (request != null) {

                    Calendar calendar = Calendar.getInstance();
                    Calendar calendarMod = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat(CommandHandler.TIMEDATE_FORMAT);

                    calendar.setTimeInMillis(request.getTstamp());
                    calendarMod.setTimeInMillis(request.getModTimestamp());

                    ModTRSUser filedUser = parent.getTableHandler().getUser().getUserFromId(request.getUserId());
                    ModTRSUser modUser = parent.getTableHandler().getUser().getUserFromId(request.getModId());

                    player.sendMessage(Message.parse("checkid.intro", request.getId(), request.getStatusText(true)));

                    boolean online = ModTRSFunctions.isUserOnline(filedUser.getName(), player.getServer());
                    player.sendMessage(Message.parse("checkid.filedby", ( online ) ? ChatColor.GREEN : ChatColor.RED, filedUser.getName(), sdf.format(calendar.getTime()), request.getX(), request.getY(), request.getZ()));

                    if (request.getModId() != 0) {
                        player.sendMessage(Message.parse("checkid.handledby", modUser.getName(), sdf.format(calendarMod.getTime())));
                        if (request.getModComment() != null && !request.getModComment().isEmpty()) {
                            player.sendMessage(Message.parse("checkid.mod_comment", request.getModComment()));
                        }
                    }

                    player.sendMessage(Message.parse("checkid.text", request.getText()));

                } else {
                    player.sendMessage(Message.parse("general.error.not_a_req", id));
                }

            } catch (SQLException e) {
                e.printStackTrace();
                player.sendMessage(Message.parse("general.error.internal"));
            }

        }

        return true;

    }
}
