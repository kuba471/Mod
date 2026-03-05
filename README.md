# AnaMvmnt (Fabric 1.21.4)

Client mod pod **Minecraft 1.21.4**.

## Funkcje
- GUI pod **Right Shift**
- Zakładki: **Hud / ESP / Animation**
- Inventory HUD (pełny ekwipunek 3x9) z przesuwaniem i zmianą koloru tła
- ESP graczy (obrys hitboxa) z wyborem koloru
- Przełączniki animacji: `Freeze Legs` i `Sword Swing Down`
- Branding w lewym górnym rogu:
  - `AnaMvmnt`
  - `discord.gg/piracik`
- **Safety Reset** (w GUI): szybkie wyłączenie wszystkich modułów i reset ustawień

## Ważne
Projekt **nie zawiera** funkcji ukrywania śladów, auto-usuwania plików ani mechanizmów omijania wykrycia.

## Wymagania
- Java 21+
- Fabric Loader dla 1.21.4
- Fabric API
- Gradle `9.4.0` (ustawione w wrapper properties)

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
