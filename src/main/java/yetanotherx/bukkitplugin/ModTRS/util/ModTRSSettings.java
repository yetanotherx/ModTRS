package yetanotherx.bukkitplugin.ModTRS.util;

import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import yetanotherx.bukkitplugin.ModTRS.ModTRS;
import yetanotherx.bukkitplugin.ModTRS.util.config.Configuration;

public class ModTRSSettings {

    /**
     * Settings
     */
    public static boolean debugMode = false;
    public static boolean logCommands = false;
    public static boolean notifyMods = true;
    public static List<String> blacklist = new ArrayList<String>();
    public static int maxRequests = -1;
    public static int reqsPerPage = 5;
    /**
     * Bukkit config class
     */
    public static Configuration config = null;

    /**
     * Load and parse the YAML config file
     */
    public static void load(ModTRS parent) {

        File dataDirectory = parent.getDataFolder();

        dataDirectory.mkdirs();

        File file = new File(dataDirectory, "config.yml");

        ModTRS.log.debug("Loading config file: " + file.getPath());

        config = new Configuration(file);
        config.load();

        if (!file.exists()) {
            saveConfigFromResource(file);

            config.load();
        }

        setSettings();

        ModTRS.log.debug("Settings loaded");


    }

    /**
     * Sets the internal variables
     */
    private static void setSettings() {

        debugMode = config.getBoolean("modtrs.debug", false);
        logCommands = config.getBoolean("modtrs.log_commands", false);
        notifyMods = config.getBoolean("modtrs.notify_mods", true);
        blacklist = config.getStringList("modtrs.blacklist", new ArrayList<String>());

        //Why do all this? Because Bukkit is weird and sets the property if it isn't found.
        Integer o = castInt(config.getProperty("modtrs.max_requests"));
        if (o == null) {
            maxRequests = -1;
        } else {
            maxRequests = o;
        }

        o = castInt(config.getProperty("modtrs.requests_per_page"));
        if (o == null) {
            reqsPerPage = 5;
        } else {
            reqsPerPage = o;
        }

    }

    public static Integer castInt(Object o) {
        if (o == null) {
            return null;
        } else if (o instanceof Byte) {
            return (int) (Byte) o;
        } else if (o instanceof Integer) {
            return (Integer) o;
        } else if (o instanceof Double) {
            return (int) (double) (Double) o;
        } else if (o instanceof Float) {
            return (int) (float) (Float) o;
        } else if (o instanceof Long) {
            return (int) (long) (Long) o;
        } else {
            return null;
        }
    }

    public static void saveConfigFromResource(File file) {
        InputStream input = ModTRS.class.getResourceAsStream("config.yml");
        if (input != null) {
            FileOutputStream output = null;

            try {
                output = new FileOutputStream(file);
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
}
