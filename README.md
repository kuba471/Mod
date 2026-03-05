# AnaMvmnt (1.21.4)

Client-side Fabric mod skeleton for Minecraft 1.21.4 with:
- Shift-opened GUI (Right Shift)
- Inventory HUD overlay with position controls
- Player ESP toggle with color cycling
- Animation settings toggles (freeze legs / sword swing style hook)

## Build

```bash
./gradlew build
```

## Controls

- `Right Shift` — open AnaMvmnt GUI

## Notes

This is a clean base implementation. The animation module is prepared as toggle state/hooks so you can extend with mixins for advanced model transforms.
