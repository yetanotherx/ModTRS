package yetanotherx.bukkitplugin.ModTRS.command;

import java.util.HashSet;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;

public class FakeCommandSender implements CommandSender {

    private StringBuilder result = new StringBuilder();
    private String name;

    public FakeCommandSender(String name) {
        this.name = name;
    }
    
    public FakeCommandSender() {
        this.name = "internal-code";
    }
    
    @Override
    public void sendMessage(String string) {
        this.result.append(string).append("\n");
    }

    @Override
    public boolean isOp() {
        return true;
    }

    @Override
    public Server getServer() {
        return Bukkit.getServer();
    }
    
    public String getResult() {
        return this.result.toString();
    }
    
    public void clearResult() {
        this.result = new StringBuilder();
    }

    public String getName() {
        return this.name;
    }

    @Override
    public boolean isPermissionSet(String string) {
        return true;
    }

    @Override
    public boolean isPermissionSet(Permission prmsn) {
        return true;
    }

    @Override
    public boolean hasPermission(String string) {
        return true;
    }

    @Override
    public boolean hasPermission(Permission prmsn) {
        return true;
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin, String string, boolean bln) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin, String string, boolean bln, int i) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin, int i) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void removeAttachment(PermissionAttachment pa) {
    }

    @Override
    public void recalculatePermissions() {
    }

    @Override
    public Set<PermissionAttachmentInfo> getEffectivePermissions() {
        return new HashSet<PermissionAttachmentInfo>();
    }

    @Override
    public void setOp(boolean bln) {
    }
    
}
