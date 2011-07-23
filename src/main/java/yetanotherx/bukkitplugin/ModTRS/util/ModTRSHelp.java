package yetanotherx.bukkitplugin.ModTRS.util;

import yetanotherx.bukkitplugin.ModTRS.command.ModTRSCommandSender;
import java.util.ArrayList;

import yetanotherx.bukkitplugin.ModTRS.ModTRS;

/**
 * Fix /modtrs help to only show commands the user has permission to use.
 */
public class ModTRSHelp {

    /**
     * List of all help messages
     * 
     * String[0] = Command syntax
     * String[1] = Description
     * String[2] = Necessary permission
     * String[3] = Only set if it is to be given to all users if Permissions is not enabled
     */
    private final static ArrayList<String[]> helpCommands = new ArrayList<String[]>();

    /**
     * Registers all the help commands
     */
    static {
        helpCommands.add(new String[]{"modreq [message]", "Request help from a moderator", "modtrs.command.modreq", "anyone"});
        helpCommands.add(new String[]{"check [t:open|all|held]", "List all open requests", "modtrs.command.check"});
        helpCommands.add(new String[]{"check-id [#]", "Get info about a request", "modtrs.command.check"});
        helpCommands.add(new String[]{"tp-id [#]", "Teleport to the location of a request", "modtrs.command.teleport"});
        helpCommands.add(new String[]{"claim [#]", "Claim the request", "modtrs.command.complete"});
        helpCommands.add(new String[]{"unclaim [#]", "Unclaim the request", "modtrs.command.complete"});
        helpCommands.add(new String[]{"done [#]", "Mark the request as completed", "modtrs.command.complete"});
        helpCommands.add(new String[]{"reopen [#]", "Reopen a closed or held request", "modtrs.command.complete"});
        helpCommands.add(new String[]{"hold [#]", "Put the request on hold", "modtrs.command.complete"});
        helpCommands.add(new String[]{"mod-broadcast [message]", "Send a message to all moderators", "modtrs.command.broadcast"});
        helpCommands.add(new String[]{"modlist", "List all moderators", "modtrs.command.modlist", "anyone"});
        helpCommands.add(new String[]{"modtrs [reload|version]", "Various ModTRS functions", "modtrs.command.modtrs"});
    }

    /**
     * Returns a list of commands that a player can use.
     */
    public static ArrayList<String> getMessages(ModTRSCommandSender player) {

        ArrayList<String> messages = new ArrayList<String>();

        for (String[] command : helpCommands) {
            //The fourth field is added to ones that non-ops can use when using ops.txt
            if (command.length > 3) {
                if (player.hasPerm(command[2], false)) {
                    messages.add(Message.parse("modreq.help.list_item", command[0], command[1]));
                }
            } else {
                if (player.hasPerm(command[2])) {
                    messages.add(Message.parse("modreq.help.list_item", command[0], command[1]));
                }
            }
        }

        return messages;

    }
}
