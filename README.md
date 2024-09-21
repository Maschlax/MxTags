# MxTags v0.9.1
A simple PlayerTags Plugin (Player suffixes) *Made by Maschlax*

## Important
### !! This plugin is 1.16+ only.
### !! This plugin needs either a MySQL or MariaDB database

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

## Install
- Put plugin jar in the plugins folder
- Start the server
- Stop the server
- Configure database settings in config.yml
- Start the server again
- Create as much tags as you want!

## Planned features
- Improve UX (no flickering between GUI pages, no mouse reset on tag select)
- Add Config for messages (with colorformatting support)
- Add support for local storage

## Known Bugs
- Tag slots not working