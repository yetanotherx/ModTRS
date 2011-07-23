package yetanotherx.bukkitplugin.ModTRS.event;

import yetanotherx.bukkitplugin.ModTRS.command.ModTRSCommandSender;

public class CommandModlistEvent extends Event {

    private ModTRSCommandSender sender;

    public CommandModlistEvent(ModTRSCommandSender sender) {
        this.sender = sender;
    }

    public ModTRSCommandSender getSender() {
        return sender;
    }

    public void setSender(ModTRSCommandSender player) {
        this.sender = player;
    }

    @Override
    public Type getType() {
        return Type.COMMAND_MODLIST;
    }
}
