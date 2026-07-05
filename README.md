# Gura Client

A cosmetic/QoL Fabric client mod for Minecraft **1.21.4** — custom click-GUI,
restyled HUD, custom crosshair, aspect-ratio letterboxing, and a small module
framework you can keep extending. Built for use with **Fold Craft Launcher**
(Android Fabric).

Everything here is client-side rendering/QoL only — no reach, no combat
automation, no packet spoofing. Safe for use on most servers, but always
check individual server rules.

## What's included

- `GuraClient.java` — mod entrypoint, wires everything together
- `module/` — `Module` base class, `Setting` (sliders/toggles), `ModuleManager`
  - `module/impl/` — example modules: Zoom, Fullbright, Toggle Sprint, FPS Boost
- `gui/ClickGuiScreen.java` — in-game menu to toggle modules & tweak settings
- `hud/HudRenderer.java` — FPS / coords / ping overlay with fade-in animation
- `hud/CrosshairRenderer.java` — custom crosshair (cross / dot / circle, color, thickness, outline)
- `hud/AspectRatioRenderer.java` — letterbox/pillarbox bars to lock a target aspect ratio
- `mixin/CrosshairMixin.java` — cancels the vanilla crosshair so the custom one shows
- `config/GuraConfig.java` — in-memory settings (swap for Gson persistence later)

## Building

Requires JDK 21 and an internet connection (Gradle needs to download Fabric
Loom, Yarn mappings, and the Fabric API — my sandbox couldn't do this part
for you, since it has no network access).

```bash
cd gura-client
./gradlew build
```

The output jar lands in `build/libs/gura-client-0.1.0.jar`.

**Opening in Cursor:** open the `gura-client` folder as the project root.
Cursor will pick up the Gradle project; let it sync once so it can resolve
`net.minecraft.*` classes. If `CrosshairMixin`'s `renderCrosshair` target
doesn't resolve after sync, open `InGameHud` (Cursor can navigate to it once
sources are attached) and rename the `@Inject(method = "...")` string to
whatever the actual crosshair-drawing method is called in that Yarn build —
Minecraft's internal method names occasionally shift between mapping
releases.

## Installing in Fold Craft Launcher

1. Build the jar (above), or grab it from wherever you build it (a CI runner,
   your desktop, etc.) since FCL itself can't compile mods.
2. In FCL, create/open a game instance running **Fabric 1.21.4**.
3. Copy `gura-client-0.1.0.jar` into that instance's `mods/` folder (FCL
   exposes this via its file manager, or you can push it with adb:
   `adb push gura-client-0.1.0.jar /storage/emulated/0/games/org.fcl.fold/.minecraft/mods/`
   — the exact path depends on your FCL version/instance name).
4. Also drop the matching **Fabric API** jar for 1.21.4 into the same
   `mods/` folder — Gura Client depends on it.
5. Launch the instance. Open the click-GUI in-game with **Right Shift**
   (rebind in FCL's touch-control editor if you'd rather have an on-screen
   button trigger that key).

## Extending it

Add a new module:

```java
public class MyModule extends Module {
    public MyModule() {
        super("My Module", "does a cool thing", ModuleCategory.QOL, false);
    }
}
```

Register it in `ModuleManager.init()`. Add `Setting.slider(...)` or
`Setting.toggle(...)` fields inside your module's constructor via
`register(...)` and they'll automatically show up in the click-GUI.

## Known rough edges / next steps

- Zoom module tracks a target FOV but doesn't yet override `GameRenderer`'s
  FOV calculation — wire that up with a `GameRenderer#getFov` mixin or
  Fabric API's FOV event if one's available in the API version you land on.
- Config is in-memory only; add Gson read/write in `GuraConfig` if you want
  settings to survive a restart.
- Click-GUI is single-column-per-category and very plain — swap in real
  panel dragging/animation once the functional side is solid.
  
