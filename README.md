# Selective Quest Submission

An inventory-selection interface for consuming **FTB Quests** item tasks on **Minecraft Fabric 1.20.1**.

Instead of allowing automatic item consumption, choose the specific inventory slots and item quantities to contribute. Eligible items are shown clearly, selected slots have green outlines, and ineligible items are darkened. The interface follows the FTB Quests theme and includes **View Eligible Items**.

## Functionality

- Select full stacks with left-click, or one item with right-click.
- Submit only selected, eligible items using FTB Quests' own item matching.
- Supports matching by item, tags, compatible item filters, and applicable NBT.
- The Submit Selected button becomes available once the selection meets the remaining task quantity.
- Validates packet shape, selected slot indices, item snapshots, eligibility, task state and task progression on the server.
- Does not automatically take unselected inventory items.
- Intended for consuming item tasks; task-screen-only submissions follow FTB Quests' normal handling.

## Requirements

- Minecraft **1.20.1** / Fabric / Java 17
- Fabric API
- FTB Quests **2001.4.22**
- FTB Library **2001.2.13**
- Runtime dependencies required by FTB Quests, including FTB Teams and Architectury.

Install on **both client and server** in multiplayer. No additional configuration or quest script changes are required.

## Source provenance and build

This is the recovered **1.0.0-rc.1** source corresponding to the CurseForge release displayed as **1.0.0**. The production JAR was published as CurseForge project **1791778**, file **9510191** on **8 November 2026**.

The source was recovered from the published binary using Vineflower 1.12.0. Internal Minecraft references use intermediary names. This is a source recovery, not the original handwritten development history. The repository includes its Gradle 8.8 / Fabric Loom 1.7.4 build project.

The published 1.0.0 binary remains the verified gameplay baseline. A recovered-source rebuild must pass compilation and in-game regression tests before replacing it.

## Licence

**Copyright © 2026 Path of the Ascended. All Rights Reserved.** See [LICENSE](LICENSE). Public source does not imply permission to reuse or redistribute.

## AI-Assisted Development Disclosure

This mod was written with the assistance of AI. All code has been reviewed, tested, and refined by a human developer to ensure functionality, compatibility, and performance.
