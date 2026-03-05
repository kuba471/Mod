# AnaMvmnt (Fabric 1.21.4)

Mega client mod pod **Minecraft 1.21.4**.

## Co jest zrobione
- GUI otwierane pod **Right Shift**
- Zakładki: **Hud / ESP / Animation**
- Inventory HUD (pełny ekwipunek 3x9) z przesuwaniem i zmianą koloru tła
- ESP graczy przez ściany (obrys hitboxa) z wyborem koloru
- Ustawienia animacji (freeze legs / sword swing down) jako gotowe przełączniki pod dalszą rozbudowę
- Branding w lewym górnym rogu:
  - `AnaMvmnt`
  - `discord.gg/piracik`

## Wymagania
- Java 21+
- Fabric Loader dla 1.21.4
- Fabric API
- Projekt ustawiony pod Gradle `9.4.0` (wrapper properties)

## Build
```bash
gradle build
```

Po udanym buildzie plik `.jar` będzie w:

```text
build/libs/
```

## Sterowanie
- `Right Shift` — otwarcie GUI AnaMvmnt
