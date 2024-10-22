# MxTags v1.0
A simple PlayerTags Plugin for Minecraft. *Made by Maschlax*

Tags are the same as a player suffixes

**!! MORE THAN ONE TAG SELECTION GUI PAGE IS NOT YET WORKING !!**

## Commands
- /tag : Open tag selection GUI
- /tagmanager \<argument> : Main command - Alias: /tm
- /tm help : Show Help message
- /tm create \<name> (\<priority>)
- /tm delete \<tagID>
- /tm list (\<page>) : List all tags, Pagination with 10 results each page

## Permissions
mxtags.tag : Default permission, allows to open the tag selector GUI. **Applied by default**

mxtags.admin : Admin permission, allows to manage the tags using the /tagmanager command

## Config
Enable or disable debug mode. Useful for getting more complex error messages

Configure database settings

## Formatting
Hex Colors can be used in tags under the format "<#ffffff>"

Recommendation for gradients: https://birdflop.com/resources/rgb/ with the same "Color Format" setting

## Installation
- Put plugin jar in the plugins folder
- Start the server
- Stop the server
- Configure database settings in config.yml
- Start the server again
- Create as much tags as you want!

## Requirements
- Minecraft version 1.16 - 1.20
- Java version 17 and above 
- MySQL or MariaDB database

## Planned features
- Complete rework, enhancing storage management and overall UX
- Add Config for messages (with colorformatting support)
- Add support for local storage

## License
The MxTags project is licensed under the MxPlugins License - see the [LICENSE](./LICENSE) file for details.

## Contact
- via Discord @maschlax (preferred)
- via Mail max@mxlx.dev