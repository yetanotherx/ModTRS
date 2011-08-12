package yetanotherx.bukkitplugin.ModTRS.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

class FakeCommand extends Command {

    public FakeCommand(String name) {
        super(name);
    }

    @Override
    public boolean execute(CommandSender cs, String string, String[] strings) {
        return true;
    }
    
}
