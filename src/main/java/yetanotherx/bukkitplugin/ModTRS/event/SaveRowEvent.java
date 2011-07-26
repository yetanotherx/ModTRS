package yetanotherx.bukkitplugin.ModTRS.event;

public class SaveRowEvent extends Event {

    private Object model;

    public SaveRowEvent(Object model) {
        this.model = model;
    }

    public Object getModel() {
        return model;
    }

    public void setModel(Object model) {
        this.model = model;
    }
    
    @Override
    public Type getType() {
        return Type.SAVE_ROW;
    }
    
}
