package yetanotherx.bukkitplugin.ModTRS.message;

import java.text.MessageFormat;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ReopenMessages extends ModTRSMessageBase implements IModTRSMessage {

    public void sendReopened( Player player, int id ) {
        player.sendMessage( this.getReopened( id ) );
    }

    public String getReopened( int id ) {

        return MessageFormat.format(
                ChatColor.GOLD + "Request #{0} has been reopened.",
                new Object[] { id }
        );

    }

    public void sendNotClosed( Player player ) {
        player.sendMessage( this.getNotClosed() );
    }

    public String getNotClosed() {

        return ChatColor.RED + "Only closed requests may be reopened.";

    }

}
