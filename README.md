# BetterMvmnt (Fabric 1.21.4)

Rozbudowany client mod pod **Minecraft 1.21.4** z GUI pod Shift i dużą liczbą opcji wizualnych/HUD.

## Najważniejsze funkcje

### 1) GUI
- otwieranie pod **Right Shift**
- 4 zakładki: **HUD / ESP / ANIM / EXTRA**
- szybkie przyciski ON/OFF i regulacja pozycji HUD
- branding w lewym górnym rogu:
  - `BetterMvmnt`
  - `discord.gg/piracik`

### 2) HUD
- **Inventory HUD** (całe 3x9 inventory)
- zmiana tła inventory HUD (kilka kolorów)
- przesuwanie inventory HUD w osi X/Y

### 3) ESP
- obrys graczy (hitbox)
- ESP przez ściany (toggle)
- tracers do graczy
- regulacja skali hitboxa
- zmiana koloru ESP
- Chinese Hat nad graczem + kolor

### 4) EXTRA HUD
- **Armor HUD** (render itemów pancerza)
- **Coords HUD** (pozycja XYZ)
- **FPS HUD**
- przesuwanie HUD współrzędnych

### 5) Settings i bezpieczeństwo
- ustawienia zapisywane do `config/bettermvmnt.json`
- opcja **Safety Reset (disable all)** w GUI:
  - wyłącza moduły
  - resetuje pozycje/kolory
- brak funkcji ukrywania śladów, usuwania plików, omijania wykrycia

## Wymagania
- Java 21+
- Fabric Loader dla 1.21.4
- Fabric API
- Gradle `9.4.0` (wrapper properties)

## Build
```bash
gradle build
```

Po udanym buildzie plik `.jar` znajdziesz w:

```text
build/libs/
```

## Sterowanie
- `Right Shift` — otwarcie GUI BetterMvmnt
