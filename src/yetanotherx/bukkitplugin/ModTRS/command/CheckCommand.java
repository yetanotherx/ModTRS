package yetanotherx.bukkitplugin.ModTRS.command;

//Java imports
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

//Bukkit imports
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

//ModTRS imports
import yetanotherx.bukkitplugin.ModTRS.ModTRS;
import yetanotherx.bukkitplugin.ModTRS.ModTRSMessage;
import yetanotherx.bukkitplugin.ModTRS.ModTRSPermissions;
import yetanotherx.bukkitplugin.ModTRS.ModTRSSettings;
import yetanotherx.bukkitplugin.ModTRS.sql.ModTRSRequest;
import yetanotherx.bukkitplugin.ModTRS.sql.ModTRSRequestTable;
import yetanotherx.bukkitplugin.ModTRS.sql.ModTRSSQL;
import yetanotherx.bukkitplugin.ModTRS.sql.ModTRSUserTable;
import yetanotherx.bukkitplugin.ModTRS.validator.CheckValidator;
import yetanotherx.bukkitplugin.ModTRS.validator.ModTRSValidatorHandler;

public class CheckCommand implements CommandExecutor {

    private ModTRS parent;

    public CheckCommand(ModTRS parent) {
	this.parent = parent;
	ModTRSValidatorHandler.getInstance().registerValidator( "check", new CheckValidator(this, parent) );    
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {

	//Default parameter values
	String[] parameters = this.getParameters(args);
	int page = Integer.parseInt(parameters[0]);
	String type = parameters[1];

	Player player = (Player) sender;

	if( !parameters[2].equals("") ) {
	    CheckIdCommand checkid = new CheckIdCommand(parent);

	    if( ModTRSValidatorHandler.getInstance().hasValidator("check-id")) {
		if( !ModTRSValidatorHandler.getInstance().getValidator("check-id").isValid( new String[] { parameters[2].trim() } ) ) {
		    return false;
		}
	    }
	    return checkid.onCommand(sender, command, commandLabel, new String[] { parameters[2].trim() } );
	}

	if( !ModTRSPermissions.has(player, "modtrs.command.check") ) {
	    ModTRSMessage.general.sendPermissionError(player);
	}
	else {


	    try {

		ArrayList<ModTRSRequest>requests = ModTRSRequestTable.getOpenRequests(type);

		String ucfirst = type.toUpperCase().substring(0, 1) + type.substring(1);
		ModTRSMessage.check.sendListIntro(player, requests.size(), ucfirst);

		int count = 0;
		if( requests.isEmpty() ) {
                    ModTRSMessage.check.sendNoRequests(player);
		}
		for( ModTRSRequest request : requests ) {
		    if( count < ( page * 5 ) - 5 ) {
			count++;
			continue;
		    }
		    if( count >= ( page * 5 ) ) {
                        ModTRSMessage.check.sendMorePages(player, requests.size() - 5);
			break;
		    }

		    Calendar calendar = Calendar.getInstance();
		    SimpleDateFormat sdf = new SimpleDateFormat(CommandHandler.TIMEDATE_FORMAT);
		    calendar.setTimeInMillis( request.getTimestamp() );

		    String substring = request.getText();

		    if( substring.length() >= 20 ) {
			substring = substring.substring(0, 20) + "...";
		    }

		    if( request.getStatus() == 1 ) {
                        ModTRSMessage.check.sendListItemClaimed(player, request.getId(), sdf.format(calendar.getTime()), ModTRSUserTable.getUserFromId(request.getUserId()).getName(), ModTRSUserTable.getUserFromId(request.getModId()).getName());
		    }
		    else {
                        ModTRSMessage.check.sendListItem(player, request.getId(), sdf.format(calendar.getTime()), ModTRSUserTable.getUserFromId(request.getUserId()).getName(), substring);
		    }

		    count++;
		}

		if( type.equals("open") && page == 1 && !ModTRSSettings.databases.isEmpty() ) {

		    String dbCounts = "";
		    Connection tempConn = null;
		    int tempCount;
		    int otherTotal = 0;
		    for(String name : ModTRSSettings.databases.keySet()) {

			tempCount = 0;

			String uri = ModTRSSettings.databases.get(name);

			tempConn = DriverManager.getConnection("jdbc:sqlite:" + uri);

			PreparedStatement prep = tempConn.prepareStatement(ModTRSSQL.getOpenRequests + " WHERE request_status = 0 OR request_status = 1");
			ResultSet rs = prep.executeQuery();

			while(rs.next()) {
			    tempCount++;
			}
			rs.close();

			otherTotal += tempCount;
			dbCounts += name + ": " + Integer.toString(tempCount) + ", ";

			tempConn.close();

		    }

		    dbCounts = dbCounts.substring(0, dbCounts.length() - 2);

                    ModTRSMessage.check.sendOtherServers(player, otherTotal, dbCounts);

		}



	    }
	    catch( SQLException e ) {
		e.printStackTrace();
		ModTRSMessage.general.sendInternalError(player);
	    }

	}

	return true;

    }

    private String[] getParameters(String[] args) {
	int page = 1;
	String type = "open";
	String id = "";

	for( String arg : args ) {
	    if( arg.length() < 2 ) {
		arg += " ";
	    }

	    if( arg.substring(0, 2).equals("p:") ) {
		page = Integer.parseInt( arg.substring(2) );
	    }
	    else if( arg.substring(0, 2).equals("t:") ) {
		type = arg.substring(2);
	    }
	    else {
		id = arg;
	    }
	}

	return new String[] { Integer.toString(page), type, id };
    }

}
