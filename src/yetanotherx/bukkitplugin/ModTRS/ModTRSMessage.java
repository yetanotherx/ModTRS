package yetanotherx.bukkitplugin.ModTRS;

import java.text.MessageFormat;

import org.bukkit.ChatColor;

public class ModTRSMessage {
    
    /**
     * /modreq messages
     */
    public static String messageSent = ChatColor.GOLD + "Thank you. Your message has been sent.";
    public static String newRequests = ChatColor.GREEN + "New mod request filed; use /check to see them";
    public static String blocked = ChatColor.RED + "Your message is invalid. Please read the instructions.";
    public static String messageNotSent = ChatColor.RED + "Your message could not be sent at this time.";
    public static String helpCommand = ChatColor.WHITE + "/{0} " + ChatColor.GRAY + " - " + ChatColor.YELLOW+ "{1}";
    public static String openRequests = ChatColor.GREEN + "There are open mod requests. Type /check to see them.";
    
    /**
     * /check messages
     */
    public static String listIntro = ChatColor.AQUA + "--------- {0} Mod Requests - " + ChatColor.YELLOW + "{1} " + ChatColor.AQUA + "---------";
    public static String tooMany = ChatColor.RED + "{0} more mod request(s) available. Use /check p:# to switch through pages.";
    public static String listItem = ChatColor.GOLD + "#{0}. " + ChatColor.GREEN + "{1} " + ChatColor.GOLD + "by " + ChatColor.GREEN + "{2} " + ChatColor.GOLD + "- " + ChatColor.GRAY + "{3}";
    public static String listItemClaimed = ChatColor.GOLD + "#{0}. " + ChatColor.GREEN + "{1} " + ChatColor.GOLD + "by " + ChatColor.GREEN + "{2} " + ChatColor.GOLD + "- " + ChatColor.LIGHT_PURPLE + "Claimed by {4}";
    public static String noRequests = ChatColor.GOLD + "There are no mod requests right now.";
    public static String otherDb = ChatColor.AQUA + "Currently {0} requests on other servers: " + ChatColor.YELLOW + "{1}";
    
    /**
     * /check-id
     */
    public static String noSuchRequest = ChatColor.RED + "No request found with that ID number.";
    public static String infoForRequest = ChatColor.AQUA + "--------- Mod Request #{0} - {1} " + ChatColor.AQUA + "---------";
    public static String filedBy = ChatColor.YELLOW + "Filed by " + ChatColor.GREEN + "{2}" + ChatColor.YELLOW + " at " + ChatColor.GREEN + " {3}" + ChatColor.YELLOW + " at " + ChatColor.GREEN + "{4}, {5}, {6}";
    public static String handledBy = ChatColor.YELLOW + "Handled by " + ChatColor.LIGHT_PURPLE + "{0}" + ChatColor.YELLOW + " at " + ChatColor.LIGHT_PURPLE + " {1}";
    public static String requestText = ChatColor.GRAY + "{7}";
    
    
    /**
     * /modlist and /mod-broadcast
     */
    public static String modlist = ChatColor.AQUA + "Moderators: " + ChatColor.YELLOW + "{0}";
    public static String noModerators = ChatColor.AQUA + "There are no moderators online right now.";
    public static String modbroadcast = ChatColor.WHITE + "[" + ChatColor.RED + "Mod-Only Broadcast" + ChatColor.WHITE + "] " + ChatColor.GREEN + "{0}";
    
    /**
     * /claim, /unclaim, /done, /hold, /reopen
     */
    public static String claimed = ChatColor.GOLD + "Request #{0} has been assigned to you.";
    public static String unclaimed = ChatColor.GOLD + "Request #{0} is no longer assigned to you.";
    public static String cantunclaim = ChatColor.RED + "You can only unclaim your own requests.";
    public static String hold = ChatColor.GOLD + "Request #{0} is now on hold.";
    public static String closedMod = ChatColor.GOLD + "Request #{0} has been completed.";
    public static String claimedOtherMods = ChatColor.GOLD + "Request #{0} has been assigned to {1}.";
    public static String unclaimedOtherMods = ChatColor.GOLD + "Request #{0} is no longer assigned.";
    public static String closedUser = ChatColor.GOLD + "A mod has completed your request.";
    public static String reopened = ChatColor.GOLD + "Request #{0} has been reopened.";

    /**
     * Generic messages
     */
    public static String internalError = ChatColor.RED + "Internal error.";
    public static String noPermission = ChatColor.RED + "You do not have permission to use this command";
    
    
    /**
     * Shortcut for MessageFormat.format
     */
    public static String parse( String string, Object[] arguments ) {
	
	return MessageFormat.format( string, arguments );
	
    }


}
