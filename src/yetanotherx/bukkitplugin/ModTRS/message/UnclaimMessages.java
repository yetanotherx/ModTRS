package yetanotherx.bukkitplugin.ModTRS.message;

import java.text.MessageFormat;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class UnclaimMessages extends ModTRSMessageBase implements IModTRSMessage {

    public void sendUnclaimedMod( Player player, int id ) {
        player.sendMessage( this.getUnclaimedMod( id ) );
    }

    public String getUnclaimedMod( int id ) {

        return MessageFormat.format(
                ChatColor.GOLD + "Request #{0} is no longer assigned to you.",
                new Object[] { id }
        );

    }

    public void sendUnclaimedUser( Player player, String mod ) {
        player.sendMessage( this.getUnclaimedUser( mod ) );
    }

    public String getUnclaimedUser( String mod ) {

        return MessageFormat.format(
                ChatColor.GOLD + "{0} is no longer handling your request. Please wait for another mod.",
                new Object[] { mod }
        );

    }

    public void sendUnclaimedMods( Player player, int id ) {
        player.sendMessage( this.getUnclaimedMods( id ) );
    }

    public String getUnclaimedMods( int id ) {

        return MessageFormat.format(
                ChatColor.GOLD + "Request #{0} is no longer assigned.",
                new Object[] { id }
        );

    }

    public void sendNotClaimed( Player player ) {
        player.sendMessage( this.getNotClaimed() );
    }

    public String getNotClaimed() {

        return ChatColor.RED + "You can only unclaim a claimed request.";

    }

    public void sendNotClaimedByMod( Player player ) {
        player.sendMessage( this.getNotClaimedByMod() );
    }

    public String getNotClaimedByMod() {

        return ChatColor.RED + "You can only unclaim requests that you have claimed.";

    }

}
