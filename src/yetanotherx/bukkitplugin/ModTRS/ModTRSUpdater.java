package yetanotherx.bukkitplugin.ModTRS;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class ModTRSUpdater {

    public static void checkSQLite(ModTRS parent) {

	//TODO: Get code

	File dataDirectory = new File("plugins" + File.separator + "ModTRS" + File.separator);

	dataDirectory.mkdirs();

	File file = new File("plugins" + File.separator + "ModTRS", "sqlitejdbc-v056.jar");

	if( !file.exists() ) {
	    ModTRS.log.info("Downloading SQLite library from GitHub directory");

	    try {
		URL u = new URL("https://github.com/yetanotherx/ModTRS/tree/master/src/resources/sqlitejdbc-v056.jar");
		
		ReadableByteChannel rbc = Channels.newChannel(u.openStream());
		FileOutputStream fos = new FileOutputStream(file.getAbsolutePath());
		fos.getChannel().transferFrom(rbc, 0, 1 << 24);
		
		if( file.exists() ) {
		    ModTRS.log.info("Successfully downloaded the SQLite JDBC.");
		}
		else {
		    ModTRS.log.severe("Error: Cannot download the SQLite JDBC. Please download manually from http://www.zentus.com/sqlitejdbc/ and place in the plugins/ModTRS/ folder.");
		    parent.getPluginLoader().disablePlugin(parent);
		}
	    }
	    catch( Exception e ) {
		e.printStackTrace();
		ModTRS.log.severe("Error: Cannot download the SQLite JDBC. Please download manually from http://www.zentus.com/sqlitejdbc/ and place in the plugins/ModTRS/ folder.");
		parent.getPluginLoader().disablePlugin(parent);
	    }
	}
	else {
	    ModTRS.log.debug("Found SQLite library. Will not download");
	}

    }

}
