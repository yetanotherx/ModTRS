package yetanotherx.bukkitplugin.ModTRS.event;

import yetanotherx.bukkitplugin.ModTRS.command.ModTRSCommandSender;

public class CommandReloadEvent extends Event {

    private ModTRSCommandSender sender;

    public CommandReloadEvent(ModTRSCommandSender sender) {
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
        return Type.COMMAND_RELOAD;
    }
}
