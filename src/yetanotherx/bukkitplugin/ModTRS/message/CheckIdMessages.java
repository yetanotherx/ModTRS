package yetanotherx.bukkitplugin.ModTRS.message;

import java.text.MessageFormat;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CheckIdMessages extends ModTRSMessageBase implements IModTRSMessage {

    public void sendCheckIdHeader( Player player, int id, String status ) {
        player.sendMessage( this.getCheckIdHeader( id, status ) );
    }

    public String getCheckIdHeader( int id, String status ) {

        return MessageFormat.format(
                ChatColor.AQUA + "--------- Mod Request #{0} - {1} " + ChatColor.AQUA + "---------",
                new Object[] { id, status }
        );

    }

    public void sendFiledBy( Player player, String user, String timestamp, int x, int y, int z ) {
        player.sendMessage( this.getFiledBy( user, timestamp, x, y, z ) );
    }

    public String getFiledBy( String user, String timestamp, int x, int y, int z ) {

        return MessageFormat.format(
                ChatColor.YELLOW + "Filed by " + ChatColor.GREEN + "{0}" + ChatColor.YELLOW + " at " + ChatColor.GREEN + " {1}" + ChatColor.YELLOW + " at " + ChatColor.GREEN + "{2}, {3}, {4}",
                new Object[] { user, timestamp, x, y, z }
        );

    }

    public void sendHandledBy( Player player, String user, String timestamp ) {
        player.sendMessage( this.getHandledBy( user, timestamp ) );
    }

    public String getHandledBy( String user, String timestamp ) {

        return MessageFormat.format(
                ChatColor.YELLOW + "Handled by " + ChatColor.LIGHT_PURPLE + "{0}" + ChatColor.YELLOW + " at " + ChatColor.LIGHT_PURPLE + " {1}",
                new Object[] { user, timestamp }
        );

    }
    
    public void sendText( Player player, String text ) {
        player.sendMessage( this.getText( text ) );
    }

    public String getText( String text ) {

        return MessageFormat.format(
                ChatColor.GRAY + "{0}",
                new Object[] { text }
        );

    }

    public void sendModComment( Player player, String text ) {
        player.sendMessage( this.getModComment( text ) );
    }

    public String getModComment( String text ) {

        return MessageFormat.format(
                ChatColor.GOLD + "Mod comment - " + ChatColor.GRAY + "{0}",
                new Object[] { text }
        );

    }



}
