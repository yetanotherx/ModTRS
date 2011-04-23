package yetanotherx.bukkitplugin.ModTRS.message;

import java.text.MessageFormat;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ModlistMessages extends ModTRSMessageBase implements IModTRSMessage {

    public void sendModList( Player player, String list ) {
        player.sendMessage( this.getModList( list ) );
    }

    public String getModList( String list ) {

        return MessageFormat.format(
                ChatColor.AQUA + "Moderators: " + ChatColor.YELLOW + "{0}",
                new Object[] { list }
        );

    }

    public void sendNoMods( Player player ) {
        player.sendMessage( this.getNoMods() );
    }

    public String getNoMods() {

        return ChatColor.AQUA + "There are no moderators online right now.";

    }

}
