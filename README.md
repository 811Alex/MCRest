# MCRest
A plugin that runs on the Gflash vanilla Minecraft server and serves as an interface between the server and other services.

The plugin runs on the minecraft server and serves a REST API on a specified port, from where it communicates with other things.
It doesn't have a lot of functionality, it only has what other services require.
Feel free to use this code and develop on it further if you want extra functionality and want a decent starting point.

## Examples
- /biome?world=world&x=1000&y=100&z=1000
  - returns the biome at the specified location
- /perms?request=hasgroup&player=Alex811&group=mod
  - returns "true" if the specified player inherits a permission group
- /perms?request=hasperm&player=Alex811&node=seeplayerinventory.checkupdates
  - returns "true" if the specified player has permission on a specified node
