package yetanotherx.bukkitplugin.ModTRS;

import java.text.MessageFormat;

import org.bukkit.ChatColor;

public class ModTRSMessage {
    
    public static String messageSent = ChatColor.GOLD + "Thank you. Your message has been sent.";
    public static String messageNotSent = ChatColor.RED + "Your message could not be sent at this time.";

    public static String internalError = ChatColor.RED + "Internal error.";
    
    public static String modlist = ChatColor.AQUA + "Moderators: " + ChatColor.YELLOW + "{0}";
    public static String modbroadcast = ChatColor.WHITE + "[" + ChatColor.RED + "Mod-Only Broadcast" + ChatColor.WHITE + "] " + ChatColor.GREEN + "{0}";
    
    
    public static String parse( String string, Object[] arguments ) {
	
	return MessageFormat.format( string, arguments );
	
    }


}
