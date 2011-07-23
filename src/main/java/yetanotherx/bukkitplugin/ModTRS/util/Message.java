package yetanotherx.bukkitplugin.ModTRS.util;

import java.text.MessageFormat;
import org.bukkit.ChatColor;
import yetanotherx.bukkitplugin.ModTRS.ModTRS;

public class Message {
    
    public Message(ModTRS parent) {
    }
    
    public static String parse( String key, Object ... params ) {
        
        Object prop = MessageHandler.getInstance().getCustomMessages().get(key);
        if( prop == null || !(prop instanceof String) ) {
            return "<" + key + ">";
        }
        
        String message = (String) prop;
        
        //Thanks to LWC!
        for (ChatColor color : ChatColor.values()) {
            String colorKey = "%" + color.name().toLowerCase() + "%";
            
            if (message.contains(colorKey)) {
                message = message.replaceAll(colorKey, color.toString());
            }
        }
        
        return MessageFormat.format(message, params);
        
    }
    
}
