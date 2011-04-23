package yetanotherx.bukkitplugin.ModTRS.message;

import java.text.MessageFormat;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class BroadcastMessages extends ModTRSMessageBase implements IModTRSMessage {

    public void sendBroadcast( Player player, String user, String text ) {
        player.sendMessage( this.getBroadcast( user, text ) );
    }

    public String getBroadcast( String user, String text ) {

        return MessageFormat.format(
                ChatColor.WHITE + "[" + ChatColor.RED + "Mod broadcast" + ChatColor.WHITE + " - " + ChatColor.RED + "{0}" + ChatColor.WHITE + "] " + ChatColor.GREEN + "{1}",
                new Object[] { user, text }
        );

    }

}
