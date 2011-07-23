package yetanotherx.bukkitplugin.ModTRS.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import org.bukkit.util.config.Configuration;
import yetanotherx.bukkitplugin.ModTRS.ModTRS;

public class MessageHandler {

    private ModTRS parent;
    private Map<String, Object> customMessages;

    private MessageHandler() {
    }

    public void load(ModTRS parent) {
        ModTRS.log.debug("Loading messages...");

        File dataDirectory = new File("plugins" + File.separator + "ModTRS" + File.separator);

        dataDirectory.mkdirs();

        File customMessagesFile = new File("plugins" + File.separator + "ModTRS", "messages.yml");
        Configuration customMessagesConf = new Configuration(customMessagesFile);
        customMessagesConf.load();
        
        if (!customMessagesFile.exists()) {
            InputStream input = parent.getClass().getResourceAsStream("/defaults/messages.yml");
            if (input != null) {
                FileOutputStream output = null;

                try {
                    output = new FileOutputStream(customMessagesFile);
                    byte[] buf = new byte[8192];
                    int length = 0;
                    while ((length = input.read(buf)) > 0) {
                        output.write(buf, 0, length);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (input != null) {
                            input.close();
                        }
                    } catch (IOException e) {
                    }

                    try {
                        if (output != null) {
                            output.close();
                        }
                    } catch (IOException e) {
                    }
                }
            }

            customMessagesConf.load();
        }
        
        File defaultMessagesFile = new File(parent.getClass().getResource("/defaults/messages.yml").getFile());
        Configuration defaultMessagesConf = new Configuration(defaultMessagesFile);
        defaultMessagesConf.load();

        for( String key : defaultMessagesConf.getKeys() ) {
            if( customMessagesConf.getProperty(key) == null ) {
                customMessagesConf.setProperty(key, defaultMessagesConf.getString(key));
            }
        }
        
        this.customMessages = customMessagesConf.getAll();

        ModTRS.log.debug("Messages loaded");
    }

    public Map<String, Object> getCustomMessages() {
        return customMessages;
    }

    public static MessageHandler getInstance() {
        return MessageHandlerHolder.INSTANCE;
    }

    private static class MessageHandlerHolder {

        private static final MessageHandler INSTANCE = new MessageHandler();
    }
}
