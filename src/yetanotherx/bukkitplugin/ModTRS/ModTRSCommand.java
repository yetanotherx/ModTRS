package yetanotherx.bukkitplugin.ModTRS;

import java.sql.SQLException;

import org.bukkit.entity.Player;

import yetanotherx.bukkitplugin.ModTRS.sql.ModTRSRequest;
import yetanotherx.bukkitplugin.ModTRS.sql.ModTRSUser;

public class ModTRSCommand {


    public static boolean onModReqCommand( ModTRS parent, Player player, String[] args, String joined ) throws SQLException {

	ModTRSUser user = ModTRSUser.getUserFromName(player.getName(), parent);
	ModTRSRequest request = new ModTRSRequest();
	request.setUserId(user.getId());
	request.setText(joined);
	request.setTimestamp(System.currentTimeMillis());
	request.setWorld(player.getWorld().getName());
	request.setX(player.getLocation().getBlockX());
	request.setY(player.getLocation().getBlockY());
	request.setZ(player.getLocation().getBlockZ());
	request.insert(parent);
	
	System.out.println(user.getName());
	
	return false;

    }

    public static boolean onModReqCheckCommand( ModTRS parent, Player player, String[] args, String joined ) {
	System.out.println("Check");
	return false;

    }
    
    public static boolean onModReqInfoCommand( ModTRS parent, Player player, String[] args, String joined ) {
	System.out.println("Info");
	return false;

    }

    public static boolean onModReqClaimCommand( ModTRS parent, Player player, String[] args, String joined ) {
	System.out.println(args[0]);
	return false;

    }

    public static boolean onModReqHoldCommand( ModTRS parent, Player player, String[] args, String joined ) {
	System.out.println(args[0]);
	return false;

    }

    public static boolean onModReqCompleteCommand( ModTRS parent, Player player, String[] args, String joined ) {
	System.out.println(args[0]);
	return false;

    }

    public static boolean onModBroadcastCommand( ModTRS parent, Player player, String[] args, String joined ) {
	System.out.println(joined);
	return false;

    }

    public static boolean onModlistCommand( ModTRS parent, Player player, String[] args, String joined ) {
	System.out.println("List");
	return false;

    }
    
}
