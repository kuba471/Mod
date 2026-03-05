package pl.bettermvmnt.client;

public final class ModSettings {
    public boolean inventoryHudEnabled = true;
    public int inventoryHudX = 10;
    public int inventoryHudY = 28;
    public int inventoryHudColor = 0xAA202020;

    public boolean armorHudEnabled = true;
    public int armorHudX = 10;
    public int armorHudY = 90;

    public boolean coordsHudEnabled = true;
    public int coordsHudX = 10;
    public int coordsHudY = 110;

    public boolean fpsHudEnabled = true;
    public int fpsHudX = 10;
    public int fpsHudY = 126;

    public boolean nametagsEnabled = true;

    public boolean espEnabled = false;
    public int espColor = 0xFF00FFAA;
    public boolean espThroughWalls = true;
    public boolean tracersEnabled = false;
    public float hitboxScale = 1.00f;

    public boolean chineseHatEnabled = false;
    public int hatColor = 0xFFFF5555;

    public boolean freecamEnabled = false;

    public boolean freezeLegAnimation = false;
    public boolean swordSwingDownStyle = false;

    public boolean showBranding = true;

    public void safetyReset() {
        inventoryHudEnabled = false;
        armorHudEnabled = false;
        coordsHudEnabled = false;
        fpsHudEnabled = false;
        nametagsEnabled = false;

        espEnabled = false;
        espThroughWalls = true;
        tracersEnabled = false;
        hitboxScale = 1.00f;

        chineseHatEnabled = false;
        freecamEnabled = false;

        freezeLegAnimation = false;
        swordSwingDownStyle = false;
        showBranding = false;

        inventoryHudX = 10;
        inventoryHudY = 28;
        inventoryHudColor = 0xAA202020;
        armorHudX = 10;
        armorHudY = 90;
        coordsHudX = 10;
        coordsHudY = 110;
        fpsHudX = 10;
        fpsHudY = 126;
        espColor = 0xFF00FFAA;
        hatColor = 0xFFFF5555;
    }
}
