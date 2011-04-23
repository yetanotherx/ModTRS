package yetanotherx.bukkitplugin.ModTRS.message;

import java.text.MessageFormat;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class HoldMessages extends ModTRSMessageBase implements IModTRSMessage {

    public void sendHeld( Player player, int id ) {
        player.sendMessage( this.getHeld( id ) );
    }

    public String getHeld( int id ) {

        return MessageFormat.format(
                ChatColor.GOLD + "Request #{0} is now on hold.",
                new Object[] { id }
        );

    }

    public void sendNotOpen( Player player ) {
        player.sendMessage( this.getNotOpen() );
    }

    public String getNotOpen() {

        return ChatColor.RED + "Only open or claimed requests can be put on hold.";

    }

}
