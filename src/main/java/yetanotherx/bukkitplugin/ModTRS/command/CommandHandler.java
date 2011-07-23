package yetanotherx.bukkitplugin.ModTRS.command;

import java.util.Arrays;
import java.util.HashMap;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.LinkedList;
import java.util.List;

import yetanotherx.bukkitplugin.ModTRS.validator.ValidatorHandler;
import yetanotherx.bukkitplugin.ModTRS.ModTRS;
import yetanotherx.bukkitplugin.ModTRS.util.Message;
import yetanotherx.bukkitplugin.ModTRS.util.ModTRSFunctions;
import yetanotherx.bukkitplugin.ModTRS.util.ModTRSSettings;

public class CommandHandler implements CommandExecutor {

    /**
     * All the command executors
     */
    private HashMap<String, CommandExecutor> commands = new HashMap<String, CommandExecutor>();
    /**
     * ModTRS plugin
     */
    private ModTRS parent;
    /**
     * Format for timestamps
     */
    public static String TIMEDATE_FORMAT = "MMM.d@kk.mm.ss";

    public CommandHandler(ModTRS parent) {
        this.parent = parent;
    }

    /**
     * Registers all the commands
     */
    public static CommandHandler load(ModTRS parent) {

        ModTRS.log.debug("Loading command handlers");
        CommandHandler handler = new CommandHandler(parent);

        handler.registerCommand("modreq-help", new HelpCommand(parent));
        handler.registerCommand("modreq", new ModreqCommand(parent));
        handler.registerCommand("check", new CheckCommand(parent));
        handler.registerCommand("check-id", new CheckIdCommand(parent));
        handler.registerCommand("tp-id", new TeleportCommand(parent));
        handler.registerCommand("claim", new ClaimCommand(parent));
        handler.registerCommand("unclaim", new UnclaimCommand(parent));
        handler.registerCommand("done", new CompleteCommand(parent));
        handler.registerCommand("complete", new CompleteCommand(parent));
        handler.registerCommand("reopen", new ReopenCommand(parent));
        handler.registerCommand("hold", new HoldCommand(parent));
        handler.registerCommand("mod-broadcast", new ModBroadcastCommand(parent));
        handler.registerCommand("modlist", new ModlistCommand(parent));
        handler.registerCommand("modtrs-reload", new ReloadCommand(parent));

        return handler;
    }

    public void registerCommand(String name, CommandExecutor command) {
        commands.put(name, command);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {

        String commandName = command.getName().toLowerCase();

        //What's this hackery? /command arg  arg2 threw an error. Strip unnecessary spaces
        List<String> temp_list = new LinkedList<String>();
        temp_list.addAll(Arrays.asList(args));
        while (temp_list.contains("")) {
            temp_list.remove("");
        }
        args = temp_list.toArray(new String[0]);

        ModTRSCommandSender player = new ModTRSCommandSender(sender);
        if (!player.isValidSender()) {
            return true;
        }

        if (ModTRSSettings.logCommands && commands.containsKey(commandName)) {
            ModTRS.log.info("Command: /" + command.getName().toLowerCase() + " " + ModTRSFunctions.implode(args, " "));
        }


        /**
         * What's this hackery?
         * /modreq should go to ModreqCommand, but /modreq help should go to HelpCommand. Eww.
         */
        if (commandName.equals("modreq")) {

            if (args.length == 1 && args[0].equals("help")) {

                if (!commands.containsKey("modreq-help")) {
                    return false;
                }

                if (ValidatorHandler.getInstance().hasValidator("modreq-help")) {
                    if (!ValidatorHandler.getInstance().getValidator("modreq-help").isValid(args)) {
                        return false;
                    }
                }
                return commands.get("modreq-help").onCommand(sender, command, commandLabel, args);

            } else if (args.length > 0) {

                try {
                    if (!commands.containsKey("modreq")) {
                        return false;
                    }

                    if (ValidatorHandler.getInstance().hasValidator("modreq")) {
                        if (!ValidatorHandler.getInstance().getValidator("modreq").isValid(args)) {
                            return false;
                        }
                    }
                    return commands.get("modreq").onCommand(sender, command, commandLabel, args);
                } catch (Exception e) {
                    e.printStackTrace();
                    sender.sendMessage(Message.parse("modreq.error.general"));
                    return true;
                }

            } else {
                return false;
            }
        } else if (commandName.equals("modtrs")) {

            if (args.length == 0) {
                return true;
            } else if (args[0].equalsIgnoreCase("version")) {
                player.sendMessage("[ModTRS] You're running " + parent.getDescription().getName() + " version " + parent.getDescription().getVersion());
            } else if (args[0].equalsIgnoreCase("reload")) {
                return (new ReloadCommand(parent)).onCommand(sender, command, commandLabel, args);
            }

        } else {
            try {
                if (!commands.containsKey(commandName)) {

                    return false;
                }

                if (ValidatorHandler.getInstance().hasValidator(commandName)) {
                    if (!ValidatorHandler.getInstance().getValidator(commandName).isValid(args)) {
                        return false;
                    }
                }
                return commands.get(commandName).onCommand(sender, command, commandLabel, args);

            } catch (Exception e) {
                e.printStackTrace();
                player.sendMessage(Message.parse("general.error.internal"));
                return true;
            }
        }


        return true;
    }
}
