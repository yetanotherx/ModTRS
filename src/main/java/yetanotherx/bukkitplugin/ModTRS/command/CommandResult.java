package yetanotherx.bukkitplugin.ModTRS.command;

/**
 * Store the result of sending a command
 * 
 * @author yetanotherx
 */
public class CommandResult {
    
    /**
     * Text that was sent to the "user"
     */
    private String output;
    
    /**
     * Boolean value, if it returned true or false
     */
    private boolean result;

    /**
     * Constructor
     * @param output Text that was sent to the "user"
     * @param result Boolean value, if it returned true or false
     */
    public CommandResult(String output, boolean result) {
        this.output = output;
        this.result = result;
    }

    /**
     * Return the text that was sent using player.sendMessage()
     * @return 
     */
    public String getOutput() {
        return output;
    }

    /**
     * Returns if the onCommand method returned true or false
     * @return 
     */
    public boolean getResult() {
        return result;
    }
    
}
