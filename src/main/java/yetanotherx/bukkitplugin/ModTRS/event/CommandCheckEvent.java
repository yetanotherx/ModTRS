package yetanotherx.bukkitplugin.ModTRS.event;

import yetanotherx.bukkitplugin.ModTRS.command.ModTRSCommandSender;

public class CommandCheckEvent extends Event {

    private int page;
    private String type;
    private ModTRSCommandSender sender;

    public CommandCheckEvent(int page, String type, ModTRSCommandSender sender) {
        this.page = page;
        this.type = type;
        this.sender = sender;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public ModTRSCommandSender getSender() {
        return sender;
    }

    public void setSender(ModTRSCommandSender player) {
        this.sender = player;
    }

    public String getRequestType() {
        return type;
    }

    public void setRequestType(String type) {
        this.type = type;
    }

    @Override
    public Type getType() {
        return Type.COMMAND_CHECK;
    }
}
