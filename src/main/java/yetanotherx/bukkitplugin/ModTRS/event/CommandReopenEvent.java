package yetanotherx.bukkitplugin.ModTRS.event;

import yetanotherx.bukkitplugin.ModTRS.command.ModTRSCommandSender;

public class CommandReopenEvent extends Event {

    private int id;
    private ModTRSCommandSender sender;

    public CommandReopenEvent(int id, ModTRSCommandSender sender) {
        this.id = id;
        this.sender = sender;
    }

    public ModTRSCommandSender getSender() {
        return sender;
    }

    public void setSender(ModTRSCommandSender player) {
        this.sender = player;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public Type getType() {
        return Type.COMMAND_REOPEN;
    }
}
