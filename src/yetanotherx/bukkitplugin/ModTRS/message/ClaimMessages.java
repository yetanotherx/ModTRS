package yetanotherx.bukkitplugin.ModTRS.message;

import java.text.MessageFormat;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ClaimMessages extends ModTRSMessageBase implements IModTRSMessage {

    public void sendClaimedMod( Player player, int id ) {
        player.sendMessage( this.getClaimedMod( id ) );
    }

    public String getClaimedMod( int id ) {

        return MessageFormat.format(
                ChatColor.GOLD + "Request #{0} has been assigned to you.",
                new Object[] { id }
        );

    }

    public void sendClaimedUser( Player player, String mod ) {
        player.sendMessage( this.getClaimedUser( mod ) );
    }

    public String getClaimedUser( String mod ) {

        return MessageFormat.format(
                ChatColor.GOLD + "{0} is handling your request.",
                new Object[] { mod }
        );

    }

    public void sendClaimedMods( Player player, String mod, int id ) {
        player.sendMessage( this.getClaimedMods( mod, id ) );
    }

    public String getClaimedMods( String mod, int id ) {

        return MessageFormat.format(
                ChatColor.GOLD + "{0} is now handling request #{1}.",
                new Object[] { mod, id }
        );

    }

    public void sendNotOpen( Player player ) {
        player.sendMessage( this.getNotOpen() );
    }

    public String getNotOpen() {

        return ChatColor.RED + "You can only claim open requests.";

    }

}
