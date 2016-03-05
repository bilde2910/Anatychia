# Anatychia

## What is this?

Anatychia is an easy to use, yet advanced world editor for Minecraft. Unlike other editors, Anatychia only lets you remove things from your world, not add anything new. The editor helps you remove specific elements of your world, like tall grass, with just a few clicks. It's possible to go advanced: Want to specifically remove all wither skeletons with more than 5 hearts of health? No problem, Anatychia handles it perfectly.

## Downloads

[Download here &gt;](https://varden.info/download.php?sid=16)

## Features

* Analyze and process any Minecraft worlds in Anvil format. Anvil has been the standard world type for several years
* Be highly specific about what you want to remove
* Remove lag after huge explosions by removing unneccessary Item entities
* Clear all tall grass from your world for easier building
* Lets you save and load advanced filters for later use

## Usage notes

* Advanced filters will dramatically increase processing time depending on complexity.
* Processing blocks with IDs above 255 is likely to yield unexpected results.
* Items laying on the ground are **NOT** Items in the advanced filter menu, they're *Entities* of type "Item".
* **ALWAYS** back up your world prior to processing it! I am not responsible if something goes wrong!
* Entities are removed even if they match a single filter.
* Normal filters act like wildcard filters. If you tell Anatychia to remove all skeletons, and also add an advanced filter to only remove skeletons with less than 5 hearts of health, all skeletons will be removed.
* If you add several criteria to one filter, all criteria have to be met for the filter to match. If you add each criteria to different filters, the entity will be removed even of only one filter matches.

## Known bugs

* Inventory/item filtering is not yet implemented.
* Processing blocks with IDs over 255 may cause glitches and crashes when playing the world.

## Screenshots

![Analyzed world](https://varden.info/image.php?sid=16&img=anatychia-main.png)
![Filter settings](https://varden.info/image.php?sid=16&img=anatychia-adv.png)
![Processing changes](https://varden.info/image.php?sid=16&img=anatychia-proc.png)
