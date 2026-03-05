# BetterMvmnt (Fabric 1.21.4)

Rozbudowany client mod pod **Minecraft 1.21.4** z GUI pod Shift i dużą liczbą opcji wizualnych/HUD.

## Najważniejsze funkcje

### 1) GUI + Intro
- otwieranie pod **Right Shift**
- intro/loading screen z napisem `BetterMvmnt` + `discord.gg/piracik`
- 4 zakładki: **HUD / ESP / ANIM / EXTRA**
- szybkie przełączniki ON/OFF

### 2) HUD i pełna edycja położenia
- **Inventory HUD** (całe 3x9 inventory)
- **Armor HUD**, **Coords HUD**, **FPS HUD**
- **NameTags list** (gracze + dystans)
- osobny ekran **Change HUD (Drag & Drop)**
  - przeciągasz elementy myszką
  - bez klikania strzałkami
  - działa szybko i płynnie

### 3) ESP / Combat Visual
- obrys graczy (hitbox)
- **ESP przez ściany** (toggle)
- **tracers do graczy** (toggle + keybind)
- regulacja skali hitboxa
- zmiana koloru ESP
- **Chinese Hat** nad graczem + kolor

### 4) Ruch i utility
- **Freecam** (toggle w GUI + keybind)
- przełączniki animacji: `Freeze Legs` i `Sword Swing Down`

### 5) Keybindy
- `Right Shift` — open GUI
- `H` — open HUD editor
- `G` — toggle tracers
- `V` — toggle ESP
- `F6` — toggle freecam

### 6) Settings i bezpieczeństwo
- ustawienia zapisywane do `config/bettermvmnt.json`
- opcja **Safety Reset (disable all)** w GUI:
  - wyłącza moduły
  - resetuje pozycje/kolory

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
