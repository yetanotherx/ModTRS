package yetanotherx.bukkitplugin.ModTRS;

import java.io.File;
/*import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;*/

public class ModTRSUpdater {

    public static boolean checkSQLite(ModTRS parent) {

	File dataDirectory = new File("plugins" + File.separator + "ModTRS" + File.separator);

	dataDirectory.mkdirs();

	File file = new File("plugins" + File.separator + "ModTRS", "sqlitejdbc-v056.jar");

	if( !file.exists() ) {

	    ModTRS.log.severe("Please manually download the SQLite JDBC from http://www.zentus.com/sqlitejdbc/ and place in the plugins/ModTRS/ folder.");
	    parent.getServer().getPluginManager().disablePlugin(parent);
	    return false;
	    //This is broken, it corrupts the jar

	    /*
	    ModTRS.log.info("Downloading SQLite library from GitHub directory");

	    try {
		URL u = new URL("https://github.com/yetanotherx/ModTRS/raw/master/resources/sqlitejdbc-v056.jar");

		ReadableByteChannel rbc = Channels.newChannel(u.openStream());
		FileOutputStream fos = new FileOutputStream(file.getAbsolutePath());
		fos.getChannel().transferFrom(rbc, 0, 1 << 24);

		if( file.exists() ) {
		    ModTRS.log.info("Successfully downloaded the SQLite JDBC. Please restart the Minecraft server.");
		    parent.getServer().getPluginManager().disablePlugin(parent);
		    return false;
		}
		else {
		    ModTRS.log.severe("Error: Cannot download the SQLite JDBC. Please download manually from http://www.zentus.com/sqlitejdbc/ and place in the plugins/ModTRS/ folder.");
		    parent.getServer().getPluginManager().disablePlugin(parent);
		}
	    }
	    catch( Exception e ) {
		e.printStackTrace();
		ModTRS.log.severe("Error: Cannot download the SQLite JDBC. Please download manually from http://www.zentus.com/sqlitejdbc/ and place in the plugins/ModTRS/ folder.");
		parent.getServer().getPluginManager().disablePlugin(parent);
	    }
	}
	else {
	    ModTRS.log.debug("Found SQLite library. Will not download");
	}

	return true;*/
	}

	return true;

    }

}
