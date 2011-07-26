package yetanotherx.bukkitplugin.ModTRS.model;

import com.avaje.ebean.validation.NotNull;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.bukkit.ChatColor;
import yetanotherx.bukkitplugin.ModTRS.ModTRS;

@Entity
@Table(name="modtrs_request")
/**
 * Model for a single row in the modtrs_request table
 */
public class ModTRSRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private int id;
    
    @NotNull
    private int userId;
    
    private int modId = 0;
    private long modTimestamp = 0;
    private String modComment = "";
    
    @NotNull
    private long tstamp;
    
    @NotNull
    private String world;
    
    @NotNull
    private int x, y, z;
    
    @NotNull
    private String text;
    private int status = 0;
    private int notifiedOfCompletion = 0;

    public ModTRSRequest() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModComment() {
        return modComment;
    }

    public void setModComment(String modComment) {
        this.modComment = modComment;
    }

    public int getModId() {
        return modId;
    }

    public void setModId(int modId) {
        this.modId = modId;
    }

    public long getModTimestamp() {
        return modTimestamp;
    }

    public void setModTimestamp(long modTimestamp) {
        this.modTimestamp = modTimestamp;
    }

    public int getNotifiedOfCompletion() {
        return notifiedOfCompletion;
    }

    public void setNotifiedOfCompletion(int notifiedOfCompletion) {
        this.notifiedOfCompletion = notifiedOfCompletion;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getTstamp() {
        return tstamp;
    }

    public void setTstamp(long time_stamp) {
        this.tstamp = time_stamp;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getWorld() {
        return world;
    }

    public void setWorld(String world) {
        this.world = world;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }
    
    
    
    public String getStatusText( boolean color ) {
	
	if( color ) {
	    switch( this.status ) {

	    case 0:
		return ChatColor.YELLOW + "Open";
	    case 1:
		return ChatColor.RED + "Claimed";
	    case 2:
		return ChatColor.LIGHT_PURPLE + "On Hold";
	    case 3:
		return ChatColor.GREEN + "Closed";
	    }
	}
	else {
	    switch( this.status ) {

	    case 0:
		return "Open";
	    case 1:
		return "Claimed";
	    case 2:
		return "On Hold";
	    case 3:
		return "Closed";
	    }
	}
	return "";
    }

    public void save(ModTRS parent) {
        parent.getAPI().saveRow(this);
    }

}
