# MxTags
A simple PlayerTags Plugin (Player suffixes)

Made by Maschlax

## Config
Enable or disable debug mode. Useful for getting more complex error messages.

Configure database settings. Not sure if something else than MariaDB works. 

## Formatting
Hex Colors can be used in tags under the format "<#ffffff>"

Recommendation for gradients: https://birdflop.com/resources/rgb/ with the same "Color Format" setting

## Commands
- /tag : Open tag selection GUI
- /tagmanager \<argument> : Main command - Alias: /tm
- /tm help : Show Help message
- /tm create <name> (\<slot>)
- /tm delete <tagID>
- /tm list (\<page>) : List all tags, Pagination with 10 results each page

## Planned features
- Add /tm modify Command
- Add permissions
- Check which databases are supported using my syntax
- Add Config for messages (with colorformatting support)
- (Add support for local storage)