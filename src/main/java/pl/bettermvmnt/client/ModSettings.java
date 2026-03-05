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

    // 30+ extra modules/toggles
    public boolean noPush = false;
    public boolean noBlockParticles = false;
    public boolean sprint = false;
    public boolean autoJump = false;
    public boolean speed = false;
    public boolean longJump = false;
    public boolean highJump = false;
    public boolean lowGravity = false;
    public boolean step = false;
    public boolean safeWalk = false;
    public boolean noSlow = false;
    public boolean velocity = false;
    public boolean antiKnockback = false;
    public boolean fastPlace = false;
    public boolean fastBreak = false;
    public boolean reach = false;
    public boolean triggerBot = false;
    public boolean aimAssist = false;
    public boolean autoTotem = false;
    public boolean autoArmor = false;
    public boolean chestStealer = false;
    public boolean scaffold = false;
    public boolean blink = false;
    public boolean timer = false;
    public boolean fullBright = false;
    public boolean xray = false;
    public boolean itemEsp = false;
    public boolean playerRadar = false;
    public boolean crosshair = false;
    public boolean damageParticles = false;
    public boolean weather = false;
    public boolean timeChanger = false;

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

        noPush = false;
        noBlockParticles = false;
        sprint = false;
        autoJump = false;
        speed = false;
        longJump = false;
        highJump = false;
        lowGravity = false;
        step = false;
        safeWalk = false;
        noSlow = false;
        velocity = false;
        antiKnockback = false;
        fastPlace = false;
        fastBreak = false;
        reach = false;
        triggerBot = false;
        aimAssist = false;
        autoTotem = false;
        autoArmor = false;
        chestStealer = false;
        scaffold = false;
        blink = false;
        timer = false;
        fullBright = false;
        xray = false;
        itemEsp = false;
        playerRadar = false;
        crosshair = false;
        damageParticles = false;
        weather = false;
        timeChanger = false;

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
