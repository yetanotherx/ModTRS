package yetanotherx.bukkitplugin.ModTRS.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.InvalidConfigurationException;
import yetanotherx.bukkitplugin.ModTRS.ModTRS;

public class MessageHandler {

    private ModTRS parent;
    private Map<String, Object> customMessages;

    private MessageHandler() {
    }

    public void load(ModTRS parent)
        throws IOException, InvalidConfigurationException {
        ModTRS.log.debug("Loading messages...");

        File dataDirectory = parent.getDataFolder();

        dataDirectory.mkdirs();

        File customMessagesFile = new File(dataDirectory, "messages.yml");
        
        if (!customMessagesFile.exists()) {
            InputStream input = parent.getResource("messages.yml");
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
        }

        FileConfiguration customMessagesConf = new YamlConfiguration();
        customMessagesConf.load(customMessagesFile);
        
        InputStream defaultMessagesFile = parent.getResource("messages.yml");
        FileConfiguration defaultMessagesConf = new YamlConfiguration();
        defaultMessagesConf.load(defaultMessagesFile);

        for( String key : defaultMessagesConf.getKeys(true) ) {
            if( customMessagesConf.getString(key) == null ) {
                customMessagesConf.set(key, defaultMessagesConf.getString(key));
            }
        }
        
        this.customMessages = customMessagesConf.getValues(true);

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
