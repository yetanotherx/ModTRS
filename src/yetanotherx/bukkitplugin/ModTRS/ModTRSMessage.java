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
    public static String infoForRequest = ChatColor.AQUA + "Mod Request #{0} - {10}";
    public static String filedBy = ChatColor.YELLOW + "  Filed by " + ChatColor.GREEN + "{0}" + ChatColor.YELLOW + " at " + ChatColor.GREEN + " {1}";
    public static String handledBy = ChatColor.YELLOW + "  Handled by " + ChatColor.LIGHT_PURPLE + "{0}" + ChatColor.YELLOW + " at " + ChatColor.LIGHT_PURPLE + " {1}";
    public static String requestText = ChatColor.GRAY + "  {8}";
    public static String commandsOne = ChatColor.GOLD + "Claim this request: /modreq-claim {0}";
    public static String commandsTwo = ChatColor.GOLD + "Close this request: /modreq-complete {0}";
    public static String commandsThree = ChatColor.GOLD + "Put this request on hold: /modreq-hold {0}";
    
    public static String lineBreak = ChatColor.AQUA + "-------------------------------------------";
    
    public static String parse( String string, Object[] arguments ) {
	
	return MessageFormat.format( string, arguments );
	
    }


}
