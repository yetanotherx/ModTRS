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
    private HashMap<CommandExecutor, HashMap<String, CommandExecutor>> subCommands = new HashMap<CommandExecutor, HashMap<String, CommandExecutor>>();
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

        ModreqCommand modreqCommand = new ModreqCommand(parent);
        ModTRSCommand modTRSCommand = new ModTRSCommand(parent);

        handler.registerCommand("modreq", modreqCommand);
        handler.registerCommand("modtrs", modTRSCommand);
        handler.registerCommand("check", new CheckCommand(parent));
        handler.registerCommand("check-id", new CheckIdCommand(parent));
        handler.registerCommand("tp-id", new TeleportCommand(parent));
        handler.registerCommand("claim", new ClaimCommand(parent));
        handler.registerCommand("unclaim", new UnclaimCommand(parent));
        handler.registerCommand("complete", new CompleteCommand(parent));
        handler.registerCommand("reopen", new ReopenCommand(parent));
        handler.registerCommand("hold", new HoldCommand(parent));
        handler.registerCommand("mod-broadcast", new ModBroadcastCommand(parent));
        handler.registerCommand("modlist", new ModlistCommand(parent));

        handler.registerSubCommand(modreqCommand, "help", new HelpCommand(parent));
        handler.registerSubCommand(modreqCommand, "ban", new BanCommand(parent));
        handler.registerSubCommand(modreqCommand, "unban", new UnbanCommand(parent));
        handler.registerSubCommand(modTRSCommand, "reload", new ReloadCommand(parent));
        handler.registerSubCommand(modTRSCommand, "version", new VersionCommand(parent));

        return handler;
    }

    public void registerCommand(String name, CommandExecutor command) {
        commands.put(name, command);
    }

    public void registerSubCommand(CommandExecutor parent, String subcommand, CommandExecutor child) {
        if (subCommands.containsKey(parent)) {
            subCommands.get(parent).put(subcommand, child);
        } else {
            HashMap<String, CommandExecutor> tempMap = new HashMap<String, CommandExecutor>();
            tempMap.put(subcommand, child);
            subCommands.put(parent, tempMap);
        }
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

        try {
            if (!commands.containsKey(commandName)) {
                return false;
            }

            CommandExecutor commandEx = commands.get(commandName);

            if (args.length != 0 && subCommands.containsKey(commandEx)) {
                if (subCommands.get(commandEx).containsKey(args[0])) {
                    commandName = args[0];
                    commandEx = subCommands.get(commandEx).get(args[0]);
                    command = new FakeCommand(commandName);
                    args = ModTRSFunctions.removeFirstArg(args);
                }
            }

            if (ValidatorHandler.getInstance().hasValidator(commandName)) {
                if (!ValidatorHandler.getInstance().getValidator(commandName).isValid(args)) {
                    return false;
                }
            }
            return commandEx.onCommand(sender, command, commandName, args);

        } catch (Exception e) {
            e.printStackTrace();
            player.sendMessage(Message.parse("general.error.internal"));
            return true;
        }
    }
}
