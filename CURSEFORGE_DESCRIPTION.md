# Selective Quest Submission

**Selective Quest Submission** is a lightweight Fabric 1.20.1 addon for **FTB Quests** that lets players choose exactly which inventory items to hand in.

Open the selection interface, see which items qualify, and choose what to contribute. Only the selected items are submitted.

## Features

- **Manual item selection:** Select individual inventory slots and quantities. Left-click to select a stack or right-click for one item.
- **Familiar quest interface:** Eligible items are clearly identified, selected slots have green borders, and ineligible items are darkened. **View Eligible Items** shows what the quest accepts.
- **FTB Quests compatibility:** Uses native item matching for regular items, item tags, supported Item Filters, modded equipment and applicable NBT conditions.
- **Server-validated submissions:** The server checks selected slots, inventory snapshots and current quest progress. Unselected inventory items are not automatically consumed through the selection interface.
- **Singleplayer and multiplayer:** The GUI follows the FTB Quests theme and requires no additional quest setup.

## How It Works

1. Open a consuming item-submission task in FTB Quests.
2. Select eligible items from your inventory.
3. Once enough items are selected, press **Submit Selected**.

Task-screen-only submissions continue using standard FTB Quests behaviour.

## Requirements

- Minecraft **1.20.1** (Fabric)
- Fabric API
- FTB Quests **2001.4.22**
- FTB Library **2001.2.13**

Install any dependencies required by FTB Quests, including FTB Teams and Architectury. For multiplayer, install Selective Quest Submission on both client and server.

Originally developed for **Path of the Ascended**.

**Source Code:** https://github.com/10bibbys-cmyk/Selective-Quest-Submission-Source

**License:** All Rights Reserved.

**AI-Assisted Development Disclosure:** This mod was written with the assistance of AI. All code has been reviewed, tested, and refined by a human developer to ensure functionality, compatibility, and performance.
