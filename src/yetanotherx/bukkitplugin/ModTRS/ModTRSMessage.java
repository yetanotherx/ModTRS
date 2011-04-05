package yetanotherx.bukkitplugin.ModTRS;

import java.text.MessageFormat;

import org.bukkit.ChatColor;

public class ModTRSMessage {
    
    public static String messageSent = ChatColor.GOLD + "Thank you. Your message has been sent.";
    public static String messageNotSent = ChatColor.RED + "Your message could not be sent at this time.";

    
    public static String internalError = ChatColor.RED + "Internal error.";
    
    
    public static String modlist = ChatColor.AQUA + "Moderators: " + ChatColor.YELLOW + "{0}";
    public static String modbroadcast = ChatColor.WHITE + "[" + ChatColor.RED + "Mod-Only Broadcast" + ChatColor.WHITE + "] " + ChatColor.GREEN + "{0}";
    
    
    public static String noSuchRequest = ChatColor.RED + "No request found with that ID number.";
    public static String infoForRequest = ChatColor.AQUA + "--------- Mod Request #{0} - {1} " + ChatColor.AQUA + "---------";
    public static String filedBy = ChatColor.YELLOW + "Filed by " + ChatColor.GREEN + "{2}" + ChatColor.YELLOW + " at " + ChatColor.GREEN + " {3}" + ChatColor.YELLOW + " at " + ChatColor.GREEN + "{4}, {5}, {6}";
    public static String handledBy = ChatColor.YELLOW + "Handled by " + ChatColor.LIGHT_PURPLE + "{0}" + ChatColor.YELLOW + " at " + ChatColor.LIGHT_PURPLE + " {1}";
    public static String requestText = ChatColor.GRAY + "{7}";
    
    
    public static String lineBreak = ChatColor.AQUA + "-------------------------------------------";
    
    
    public static String helpCommand = ChatColor.WHITE + "/{0} " + ChatColor.GRAY + " - " + ChatColor.YELLOW+ "{1}";
    
    public static String claimed = ChatColor.GOLD + "Request #{0} has been assigned to you.";
    public static String hold = ChatColor.GOLD + "Request #{0} is now on hold.";
    public static String closedMod = ChatColor.GOLD + "Request #{0} has been completed.";
    public static String closedUser = ChatColor.GOLD + "A mod has completed your request.";
    
    
    public static String parse( String string, Object[] arguments ) {
	
	return MessageFormat.format( string, arguments );
	
    }


}
