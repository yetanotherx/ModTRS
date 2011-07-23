package yetanotherx.bukkitplugin.ModTRS.command;

public class CommandResult {
    
    private String output;
    private boolean result;

    public CommandResult(String output, boolean result) {
        this.output = output;
        this.result = result;
    }

    public String getOutput() {
        return output;
    }

    public boolean getResult() {
        return result;
    }
    
}
