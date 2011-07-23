package yetanotherx.bukkitplugin.ModTRS.event;

import yetanotherx.bukkitplugin.ModTRS.command.ModTRSCommandSender;

public class CommandHelpEvent extends Event {

    private ModTRSCommandSender sender;

    public CommandHelpEvent(ModTRSCommandSender sender) {
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
        return Type.COMMAND_HELP;
    }
}
