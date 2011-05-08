package yetanotherx.bukkitplugin.ModTRS.message;

import java.text.MessageFormat;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ModreqMessages extends ModTRSMessageBase implements IModTRSMessage {

    public void sendMessageSentUser( Player player ) {
        player.sendMessage(this.getMessageSentUser());
    }

    public String getMessageSentUser() {

        return ChatColor.GOLD + "Thank you. Your message has been sent. A moderator should be with you shortly.";

    }


    public void sendMessageSentMod( Player player ) {
        player.sendMessage(this.getMessageSentMod());
    }

    public String getMessageSentMod() {

        return ChatColor.GREEN + "New mod request filed; use /check for more.";

    }

    public void sendHelpCommand( Player player, String command, String text ) {
        player.sendMessage(this.getHelpCommand( command, text ));
    }

    public String getHelpCommand( String command, String text ) {

        return MessageFormat.format(
                ChatColor.WHITE + "/{0} " + ChatColor.GRAY + " - " + ChatColor.YELLOW+ "{1}",
                new Object[] { command, text }
        );

    }





    public void sendBlacklisted( Player player ) {
        player.sendMessage(this.getBlacklisted());
    }

    public String getBlacklisted() {

        return ChatColor.RED + "Your message is invalid. Please read the instructions.";

    }

    public void sendError( Player player ) {
        player.sendMessage(this.getError());
    }

    public String getError() {

        return ChatColor.RED + "Your message could not be sent at this time.";

    }

    public void sendAlreadyOpen( Player player, int count ) {
        player.sendMessage(this.getAlreadyOpen( count ));
    }

    public String getAlreadyOpen(int count) {

        return MessageFormat.format(
                ChatColor.RED + "You already have {0} open mod request(s). Please wait for them to be completed.",
                new Object[] { count }
        );

    }

    public void sendThrottled( Player player, int num, String unit  ) {
        player.sendMessage(this.getThrottled( num, unit ));
    }

    public String getThrottled( int num, String unit ) {

        return MessageFormat.format(
                ChatColor.RED + "You may not file another mod request for another {0} {1}.",
                new Object[] { num, unit }
        );

    }

}
