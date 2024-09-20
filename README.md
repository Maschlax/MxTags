# MxTags v0.8
A simple PlayerTags Plugin (Player suffixes) *Made by Maschlax*

## Important
### !! This plugin is 1.16+ only.
### !! This plugin might not work with a different database than MariaDB

## Commands
- /tag : Open tag selection GUI
- /tagmanager \<argument> : Main command - Alias: /tm
- /tm help : Show Help message
- /tm create \<name> (\<slot>)
- /tm delete \<tagID>
- /tm list (\<page>) : List all tags, Pagination with 10 results each page

## Permissions
mxtags.tag : Default permission, allows to open the tag selector GUI. **Applied by default**

mxtags.admin : Admin permission, allows to manage the tags using the /tagmanager command

## Config
Enable or disable debug mode. Useful for getting more complex error messages.

Configure database settings. Not sure if something else than MariaDB works.

## Formatting
Hex Colors can be used in tags under the format "<#ffffff>"

Recommendation for gradients: https://birdflop.com/resources/rgb/ with the same "Color Format" setting

## Planned features
- Add tab completion for /tagmanager arguments
- Add Config for messages (with colorformatting support)
- Improve UX (no flickering between GUI pages, no mouse reset on tag select)
- Check which databases are supported using my syntax
- (Add support for local storage)

## Known Bugs
- Tag slots not working