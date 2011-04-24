/**
 * This file is part of LWC (https://github.com/Hidendra/LWC), 
 * modified for use within ModTRS (https://github.com/yetanotherx/ModTRS)
 * ModTRS is under GPLv3, as is LWC
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.griefcraft.lwc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import yetanotherx.bukkitplugin.ModTRS.ModTRS;
import yetanotherx.bukkitplugin.ModTRS.ModTRSLogger;
import yetanotherx.bukkitplugin.ModTRS.ModTRSSettings;

public class Updater {

    /**
     * The latest plugin version
     */
    private double latestPluginVersion = 0.00;

    /**
     * The logging object for this class
     */
    private ModTRSLogger logger = ModTRS.log;

    /**
     * List of files to download
     */
    private List<UpdaterFile> needsUpdating = new ArrayList<UpdaterFile>();

    /**
     * The main plugin class
     */
    private ModTRS parent;

    /**
     * The folder where libraries are stored
     */
    public final static String DEST_LIBRARY_FOLDER = "plugins/ModTRS/";

    /**
     * URL to the Distribution repo
     */
    public final static String DIST_REPO = "https://github.com/yetanotherx/Distribution/raw/master/";

    /**
     * URL to the ModTRS repo
     */
    public final static String MODTRS_REPO = "https://github.com/yetanotherx/ModTRS/raw/master/";

    /**
     * Filename of the ModTRS jar
     */
    public final static String DIST_FILE = "ModTRS.jar";

    /**
     * Filename of the versions text file
     */
    public final static String VERSION_FILE = "versions.txt";

    public String db_name = "sqlite.jar";

    public Updater(ModTRS parent) {
	this.parent = parent;
    }

    /**
     * Check for dependencies
     * 
     * @return true if LWC should be reloaded
     */
    public void check() {
	String[] paths = new String[] { DEST_LIBRARY_FOLDER + "lib/" + db_name, getFullNativeLibraryPath() };

	for (String path : paths) {
	    File file = new File(path);

	    if (file != null && !file.exists() && !file.isDirectory()) {
		UpdaterFile updaterFile = new UpdaterFile(DIST_REPO + path.replaceAll(DEST_LIBRARY_FOLDER, ""));
		updaterFile.setLocalLocation(path);

		if (!needsUpdating.contains(updaterFile)) {
		    needsUpdating.add(updaterFile);
		}
	    }
	}

	if (ModTRSSettings.autoupdate) {
	    if (latestPluginVersion > Double.parseDouble(parent.getDescription().getVersion() )) {
		logger.info("Update detected for ModTRS");
		logger.info("Latest version: " + latestPluginVersion);
	    }
	}
    }

    /**
     * Check to see if the distribution is outdated
     * 
     * @return
     */
    public boolean checkDist() {
	check();

	if (latestPluginVersion > Double.parseDouble(parent.getDescription().getVersion() )) {
	    UpdaterFile updaterFile = new UpdaterFile(MODTRS_REPO + DIST_FILE);
	    updaterFile.setLocalLocation("plugins/ModTRS.jar");

	    needsUpdating.add(updaterFile);

	    try {
		update();
		logger.info("Updated successful");
		return true;
	    } catch (Exception e) {
		logger.severe("Update failed: " + e.getMessage());
		e.printStackTrace();
	    }
	}

	return false;
    }

    /**
     * @return the full path to the native library for sqlite
     */
    public String getFullNativeLibraryPath() {
	return getOSSpecificFolder() + getOSSpecificFileName();
    }

    /**
     * @return the latest plugin version
     */
    public double getLatestPluginVersion() {
	return latestPluginVersion;
    }

    /**
     * @return the os/arch specific file name for sqlite's native library
     */
    public String getOSSpecificFileName() {
	String osname = System.getProperty("os.name").toLowerCase();

	if (osname.contains("windows")) {
	    return "sqlitejdbc.dll";
	} else if (osname.contains("mac")) {
	    return "libsqlitejdbc.jnilib";
	} else { /* We assume linux/unix */
	    return "libsqlitejdbc.so";
	}
    }

    /**
     * @return the os/arch specific folder location for SQLite's native library
     */
    public String getOSSpecificFolder() {
	String osname = System.getProperty("os.name").toLowerCase();
	String arch = System.getProperty("os.arch").toLowerCase();

	if (osname.contains("windows")) {
	    return DEST_LIBRARY_FOLDER + "lib/native/Windows/" + arch + "/";
	} else if (osname.contains("mac")) {
	    return DEST_LIBRARY_FOLDER + "lib/native/Mac/" + arch + "/";
	} else { /* We assume linux/unix */
	    return DEST_LIBRARY_FOLDER + "lib/native/Linux/" + arch + "/";
	}
    }

    /**
     * Load the latest versions
     */
    public void loadVersions() {
	try {
	    URL url = new URL(MODTRS_REPO + VERSION_FILE);

	    InputStream inputStream = url.openStream();
	    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

	    latestPluginVersion = Double.parseDouble(bufferedReader.readLine());

	    bufferedReader.close();
	} catch (Exception e) {
	}

	try {
	    if (ModTRSSettings.autoupdate) {
		checkDist();
	    } else {
		check();
	    }

	    update();
	} catch (Exception e) {
	    e.printStackTrace();
	}

    }

    /**
     * Ensure we have all of the required files (if not, download them)
     */
    public void update() throws Exception {
	if (needsUpdating.isEmpty()) {
	    return;
	}

	/*
	 * Make the folder hierarchy if needed
	 */
	File folder = new File(getOSSpecificFolder());
	folder.mkdirs();
	folder = new File(DEST_LIBRARY_FOLDER + "lib/");
	folder.mkdirs();

	logger.info("Need to download " + needsUpdating.size() + " file(s)");

	Iterator<UpdaterFile> iterator = needsUpdating.iterator();

	while (iterator.hasNext()) {
	    UpdaterFile item = iterator.next();

	    String fileName = item.getRemoteLocation();
	    fileName = fileName.substring(fileName.lastIndexOf('/') + 1);

	    logger.info(" - Downloading file: " + fileName);

	    URL url = new URL(item.getRemoteLocation());
	    File file = new File(item.getLocalLocation());

	    if (file.exists()) {
		file.delete();
	    }

	    InputStream inputStream = url.openStream();
	    OutputStream outputStream = new FileOutputStream(file);

	    saveTo(inputStream, outputStream);

	    inputStream.close();
	    outputStream.close();

	    logger.info("  + Download complete");
	    iterator.remove();
	}

    }

    /**
     * Write an input stream to an output stream
     * 
     * @param inputStream
     * @param outputStream
     */
    private void saveTo(InputStream inputStream, OutputStream outputStream) throws IOException {
	byte[] buffer = new byte[1024];
	int len = 0;

	while ((len = inputStream.read(buffer)) > 0) {
	    outputStream.write(buffer, 0, len);
	}
    }

}