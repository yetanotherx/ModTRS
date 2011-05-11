package yetanotherx.bukkitplugin.ModTRS.message;

import java.text.MessageFormat;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CheckMessages extends ModTRSMessageBase implements IModTRSMessage {

    public void sendListIntro( Player player, int count, String status ) {
        player.sendMessage(this.getListIntro( count, status ));
    }

    public String getListIntro( int count, String status ) {

        return MessageFormat.format(
                ChatColor.AQUA + "--------- {0} Mod Requests - " + ChatColor.YELLOW + "{1} " + ChatColor.AQUA + "---------",
                new Object[] { count, status }
        );

    }

    public void sendListItem( Player player, int id, String timestamp, String user, String excerpt, boolean online ) {
        player.sendMessage(this.getListItem( id, timestamp, user, excerpt, online ));
    }

    public String getListItem( int id, String timestamp, String user, String excerpt, boolean online ) {
        ChatColor color = ChatColor.GREEN;
        if( !online ) {
            color = ChatColor.RED;
        }
        
        return MessageFormat.format(
                ChatColor.GOLD + "#{0}. {1} by " + color + "{2} " + ChatColor.GOLD + "- " + ChatColor.GRAY + "{3}",
                new Object[] {  id, timestamp, user, excerpt }
        );

    }

    public void sendListItemClaimed( Player player, int id, String timestamp, String user, String mod, boolean online ) {
        player.sendMessage(this.getListItemClaimed( id, timestamp, user, mod, online ));
    }

    public String getListItemClaimed( int id, String timestamp, String user, String mod, boolean online ) {

        ChatColor color = ChatColor.GREEN;
        if( !online ) {
            color = ChatColor.RED;
        }

        return MessageFormat.format(
                ChatColor.GOLD + "#{0}. {1} by " + color + "{2} " + ChatColor.GOLD + "- " + ChatColor.LIGHT_PURPLE + "Claimed by {3}",
                new Object[] { id, timestamp, user, mod }
        );

    }


    public void sendMorePages( Player player, int count ) {
        player.sendMessage(this.getMorePages( count ));
    }

    public String getMorePages( int count ) {

        return MessageFormat.format(
                ChatColor.RED + "{0} more mod request(s) available. Use /check p:# to switch through pages.",
                new Object[] { count }
        );

    }


    public void sendOtherServers( Player player, int count, String text ) {
        player.sendMessage(this.getOtherServers( count, text ));
    }

    public String getOtherServers( int count, String text ) {

        return MessageFormat.format(
                ChatColor.AQUA + "Currently {0} requests on other servers: " + ChatColor.YELLOW + "{1}",
                new Object[] { count, text }
        );

    }



    public void sendNoRequests( Player player ) {
        player.sendMessage(this.getNoRequests());
    }

    public String getNoRequests() {

        return ChatColor.GOLD + "There are no mod requests right now.";

    }

}
