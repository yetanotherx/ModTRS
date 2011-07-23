package yetanotherx.bukkitplugin.ModTRS.command;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;

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
    
}
