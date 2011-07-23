package yetanotherx.bukkitplugin.ModTRS.model;

import yetanotherx.bukkitplugin.ModTRS.ModTRS;

public class ModTRSTableHandler {

    private ModTRSRequestTable requestTable;
    private ModTRSUserTable userTable;
    
    private ModTRSTableHandler() {
    }
    
    public static ModTRSTableHandler load(ModTRS parent) {
        ModTRSTableHandler instance = new ModTRSTableHandler();
        instance.requestTable = new ModTRSRequestTable(parent);
        instance.userTable = new ModTRSUserTable(parent);
        return instance;
    }

    public ModTRSRequestTable getRequest() {
        return requestTable;
    }

    public ModTRSUserTable getUser() {
        return userTable;
    }
    
}
