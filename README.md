# Disable Lightning Render

A small client-side mod that stops lightning bolts from being rendered. The bolt still strikes, sets fires, makes its sound, and the sky still flashes, it's just that don't see the bolt itself.

Works on any server.

## Settings

Disable Lightning Render works on its own, with no dependencies. Out of the box, lightning is always hidden.

If [S-Lib](https://github.com/Stalemated/s-lib) 4.0.0 or newer is installed, you also get a setting to turn the mod on and off:

- Located in `config/disable_lightning_render.json5` as `hideLightning`.
- With [YACL](https://github.com/isXander/YetAnotherConfigLib) installed too, the config button in the mod list opens a config screen with a Hide Lightning toggle. 
  - The Fabric version also needs [Mod Menu](https://github.com/TerraformersMC/ModMenu) to display the config in-game.

## Versions

Each file covers a range of Minecraft versions, and its name ends with that range (for example `1.0.0+1.20-1.21.11`). Find your Minecraft version on the left; the cell under your loader is the file to download.

| Minecraft        | Fabric       | Forge       | NeoForge      |
|------------------|--------------|-------------|---------------|
| 1.20 - 1.20.1    | 1.20-1.21.11 | 1.20-1.20.1 |               |
| 1.20.2 - 1.20.3  | 1.20-1.21.11 |             |               |
| 1.20.4           | 1.20-1.21.11 |             | 1.20.4        |
| 1.20.5 - 1.21.11 | 1.20-1.21.11 |             | 1.20.5-26.1.2 |
| 26.1 - 26.1.2    | 26.1-26.3    |             | 1.20.5-26.1.2 |
| 26.2 - 26.3      | 26.1-26.3    |             | 26.2-26.3     |

## Building

Building needs JDK 25 and S-Lib in your mavenLocal, or set `slibLocal=false` in `gradle.properties` to pull from Modrinth's Maven. 

If going the first route, publish S-Lib first with `./gradlew publishToMavenLocal` in the [S-Lib repository](https://github.com/Stalemated/s-lib).

```bash
./gradlew build
```

```bash
./gradlew :1.21.1-neoforge:runClient
```

- Runs include S-Lib, YACL and Mod Menu (Fabric). `-PnoYacl` leaves out YACL and Mod Menu, and `-PnoSlib` leaves out S-Lib too, so you can test the mod on its own.

## License

[MIT](LICENSE)
