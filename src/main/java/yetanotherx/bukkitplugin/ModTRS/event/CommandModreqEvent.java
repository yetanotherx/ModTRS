package yetanotherx.bukkitplugin.ModTRS.event;

import yetanotherx.bukkitplugin.ModTRS.command.ModTRSCommandSender;

public class CommandModreqEvent extends Event {

    private String text;
    private ModTRSCommandSender sender;

    public CommandModreqEvent(String text, ModTRSCommandSender sender) {
        this.text = text;
        this.sender = sender;
    }

    public ModTRSCommandSender getSender() {
        return sender;
    }

    public void setSender(ModTRSCommandSender sender) {
        this.sender = sender;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public Type getType() {
        return Type.COMMAND_MODREQ;
    }
}
