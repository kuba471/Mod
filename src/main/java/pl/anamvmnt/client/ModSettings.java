package pl.anamvmnt.client;

public final class ModSettings {
    public boolean inventoryHudEnabled = true;
    public int inventoryHudX = 10;
    public int inventoryHudY = 28;
    public int inventoryHudColor = 0xAA202020;

    public boolean espEnabled = false;
    public int espColor = 0xFF00FFAA;

    public boolean freezeLegAnimation = false;
    public boolean swordSwingDownStyle = false;

    public boolean showBranding = true;

    public void safetyReset() {
        inventoryHudEnabled = false;
        espEnabled = false;
        freezeLegAnimation = false;
        swordSwingDownStyle = false;
        showBranding = false;

        inventoryHudX = 10;
        inventoryHudY = 28;
        inventoryHudColor = 0xAA202020;
        espColor = 0xFF00FFAA;
    }
}
