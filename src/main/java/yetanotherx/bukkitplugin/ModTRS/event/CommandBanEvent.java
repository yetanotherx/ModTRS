package yetanotherx.bukkitplugin.ModTRS.event;

import yetanotherx.bukkitplugin.ModTRS.command.ModTRSCommandSender;

public class CommandBanEvent extends Event {

    private ModTRSCommandSender sender;
    private String username;

    public CommandBanEvent(String username, ModTRSCommandSender sender) {
        this.sender = sender;
        this.username = username;
    }

    public ModTRSCommandSender getSender() {
        return sender;
    }

    public void setSender(ModTRSCommandSender player) {
        this.sender = player;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    @Override
    public Type getType() {
        return Type.COMMAND_BAN;
    }
}
