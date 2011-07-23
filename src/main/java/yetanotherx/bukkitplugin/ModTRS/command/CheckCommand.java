package yetanotherx.bukkitplugin.ModTRS.command;

//Java imports
import com.avaje.ebean.PagingList;
import java.text.SimpleDateFormat;
import java.util.Calendar;

//Bukkit imports
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

//ModTRS imports
import yetanotherx.bukkitplugin.ModTRS.ModTRS;
import yetanotherx.bukkitplugin.ModTRS.util.ModTRSFunctions;
import yetanotherx.bukkitplugin.ModTRS.util.ModTRSSettings;
import yetanotherx.bukkitplugin.ModTRS.event.CommandCheckEvent;
import yetanotherx.bukkitplugin.ModTRS.event.EventHandler;
import yetanotherx.bukkitplugin.ModTRS.model.ModTRSRequest;
import yetanotherx.bukkitplugin.ModTRS.util.Message;
import yetanotherx.bukkitplugin.ModTRS.validator.CheckValidator;
import yetanotherx.bukkitplugin.ModTRS.validator.ValidatorHandler;

public class CheckCommand implements CommandExecutor {

    private ModTRS parent;

    public CheckCommand(ModTRS parent) {
        this.parent = parent;
        ValidatorHandler.getInstance().registerValidator("check", new CheckValidator(this, parent));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {

        //Default parameter values
        String[] parameters = this.getParameters(args);
        int page = Integer.parseInt(parameters[0]);
        String type = parameters[1];
        
        ModTRSCommandSender player = new ModTRSCommandSender(sender);

        if (!parameters[2].isEmpty()) {
            CheckIdCommand checkid = new CheckIdCommand(parent);

            if (ValidatorHandler.getInstance().hasValidator("check-id")) {
                if (!ValidatorHandler.getInstance().getValidator("check-id").isValid(new String[]{parameters[2].trim()})) {
                    return false;
                }
            }
            return checkid.onCommand(sender, command, commandLabel, new String[]{parameters[2].trim()});
        }

        CommandCheckEvent event = new CommandCheckEvent(page, type, player);
        EventHandler.getInstance().dispatch(event);
        if (event.isCancelled()) {
            return true;
        }

        page = event.getPage();
        type = event.getRequestType();
        player = event.getSender();

        if (!player.hasPerm("modtrs.command.check")) {
            player.sendMessage(Message.parse("general.error.permission"));
        } else {
            try {
                PagingList<ModTRSRequest> pager = parent.getTableHandler().getRequest().getRequestsPager(type, ModTRSSettings.reqsPerPage);

                String ucfirst = type.toUpperCase().substring(0, 1) + type.substring(1);
                player.sendMessage(Message.parse("check.intro", pager.getTotalRowCount(), ucfirst));

                if (pager.getTotalRowCount() == 0) {
                    player.sendMessage(Message.parse("check.no_reqs"));
                }

                int count = 0;
                for (ModTRSRequest request : pager.getPage(page - 1).getList()) {
                    count++;

                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat(CommandHandler.TIMEDATE_FORMAT);
                    calendar.setTimeInMillis(request.getTstamp());

                    String substring = request.getText();

                    if (substring.length() >= 20) {
                        substring = substring.substring(0, 20) + "...";
                    }

                    boolean online = ModTRSFunctions.isUserOnline(parent.getTableHandler().getUser().getUserFromId(request.getUserId()).getName(), player.getServer());
                    if (request.getStatus() == 1) {
                        player.sendMessage(Message.parse("check.item.claimed", request.getId(), sdf.format(calendar.getTime()), ( online ) ? ChatColor.GREEN : ChatColor.RED, parent.getTableHandler().getUser().getUserFromId(request.getUserId()).getName(), parent.getTableHandler().getUser().getUserFromId(request.getModId()).getName()));
                    } else {
                        player.sendMessage(Message.parse("check.item.unclaimed", request.getId(), sdf.format(calendar.getTime()), ( online ) ? ChatColor.GREEN : ChatColor.RED, parent.getTableHandler().getUser().getUserFromId(request.getUserId()).getName(), substring));
                    }
                }

                int reqs_left = pager.getTotalRowCount() - (page) * pager.getPageSize();
                if (reqs_left > 0) {
                    try {
                        player.sendMessage(Message.parse("check.more", reqs_left, page + 1));
                        
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                player.sendMessage(Message.parse("general.error.internal"));
            }

        }

        return true;

    }

    private String[] getParameters(String[] args) {
        int page = 1;
        String type = "open";
        String id = "";

        for (String arg : args) {
            if (arg.length() < 2) {
                arg += " ";
            }

            if (arg.substring(0, 2).equals("p:")) {
                page = Integer.parseInt(arg.substring(2));
            } else if (arg.substring(0, 2).equals("t:")) {
                type = arg.substring(2);
            } else {
                id = arg;
            }
        }

        return new String[]{Integer.toString(page), type, id};
    }
}
