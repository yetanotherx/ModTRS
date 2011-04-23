package yetanotherx.bukkitplugin.ModTRS.message;

import java.text.MessageFormat;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class GeneralMessages extends ModTRSMessageBase implements IModTRSMessage {

    public void sendInternalError( Player player ) {
        player.sendMessage(this.getInternalError());
    }
    
    public void sendPermissionError( Player player ) {
        player.sendMessage(this.getPermissionError());
    }

    public String getInternalError() {

        return ChatColor.RED + "An internal error occurred. Please contact an administrator.";

    }

    public String getPermissionError() {

        return ChatColor.RED + "You do not have permission to use this command.";

    }



    public void sendOpenRequests( Player player, int count ) {
        player.sendMessage(this.getOpenRequests( count ));
    }

    public String getOpenRequests( int count ) {

        return MessageFormat.format(
                ChatColor.GREEN + "There are {0} open mod requests. Type /check to see them.",
                new Object[] { count }
        );

    }

    public void sendNoSuchRequest( Player player, int id ) {
        player.sendMessage(this.getNoSuchRequest( id ));
    }

    public String getNoSuchRequest( int id ) {

        return MessageFormat.format(
                ChatColor.RED + "Request #{0} was not found.",
                new Object[] { id }
        );

    }

}
