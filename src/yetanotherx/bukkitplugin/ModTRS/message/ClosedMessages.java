package yetanotherx.bukkitplugin.ModTRS.message;

import java.text.MessageFormat;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ClosedMessages extends ModTRSMessageBase implements IModTRSMessage {

    public void sendClosedMod( Player player, int id ) {
        player.sendMessage( this.getClosedMod( id ) );
    }

    public String getClosedMod( int id ) {

        return MessageFormat.format(
                ChatColor.GOLD + "Request #{0} has been completed.",
                new Object[] { id }
        );

    }

    public void sendClosedUser( Player player, String mod ) {
        player.sendMessage( this.getClosedUser( mod ) );
    }

    public String getClosedUser( String mod ) {

        return MessageFormat.format(
                ChatColor.GOLD + "{0} has completed your request.",
                new Object[] { mod }
        );

    }

    public void sendClosedUser( Player player, String mod, String text ) {
        player.sendMessage( this.getClosedUser( mod, text ) );
    }

    public String getClosedUser( String mod, String text ) {

        return MessageFormat.format(
                ChatColor.GOLD + "{0} has completed your request. Comment: {1}",
                new Object[] { mod, text }
        );

    }

    public void sendClosedUserOffline( Player player, String mod ) {
        player.sendMessage( this.getClosedUserOffline( mod ) );
    }

    public String getClosedUserOffline( String mod ) {

        return MessageFormat.format(
                ChatColor.GOLD + "{0} has completed your request while you were offline.",
                new Object[] { mod }
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

    public void sendNotOpen( Player player ) {
        player.sendMessage( this.getNotOpen() );
    }

    public String getNotOpen() {

        return ChatColor.RED + "Only open or requests may be closed.";

    }

}
