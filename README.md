# Shut The Cluck Up

A plugin that allows various means of silencing mobs. Config and permission driven. Options for silencing individual mobs or large groups.


## Commands

- `/shushwand [player]`
    - Gives you or the provided player a shush wand. Or silence wand. Or shut-up stick. Or whatever you wanna call it.
    - Aliases:
        - `/shutup-stick`
        - `/shhwand`
        - `/silence-wand`
    - Required Permission:
        - To use on self: `shush.wand`
        - To provide another player and give someone else: `shush.other.wand`


- `/shush <entity> <radius> <true|false>`
    - Sets the silence flag of selected entities within the provided radius to the provided flag. If no flag is
      provided, defaults to 'true'
        - Entity and Radius are required
        - Only works on Living Entities
    - Permissions:
        - `shush.command`
    - Aliases:
        - `/stfu`
        - `/stcu`
        - `/shh`
        - `/shutup`
        - `/silence`


- `/shush-reload`
    - Reloads the plugin configurations.
    - Permission: `shush.reload`

## Permissions

| Permission                 | Explanation                                                                                                                                                                               | Default | 
|:---------------------------|:------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|:--------|
| `shush.mob-type.basic`     | Gives user the ability to silence mobs that are configured in `config.yml`<br> Does not give the ability to obtain wand or use command.                                                   | `true`  |
| `shush.mob-type.<mobname>` | Gives user ability to silence a specific mob that is not added to the default allowed mobs.                                                                                               | `none`  |
| `shush.wand`               | Gives user the ability to use the `/shushwand` command to obtain the configured item that allows them to silence individual mobs. <br>Only works on mobs they have permissions to silence | `op`    |
| `shush.command`            | Gives user the ability to use `/shush <entity> <radius> <true\|false>` to silence mobs in an area                                                                                         | `op`    |                                                                                                                           
| `shush.other.wand`         | Gives user the ability to use `/shushwand <player>` to give a different player a wand item. <br>Does not automatically give cooldown override permission.                                 | `op`    |
| `shush.reload`             | Gives user the ability to reload plugin configuration                                                                                                                                     | `op`    |
| `shush.bypass.mob-type`    | Allows a user to silence any mob, regardless of configuration.                                                                                                                            | `op`    |
| `shush.bypass.radius`      | Allows a user to bypass the max radius in the `/shush` command                                                                                                                            | `op`    |
| `shush.bypass.cooldown`    | Allows a user to bypass the cooldown on spawning shush wands                                                                                                                              | `op`    |

## Default Config

```yml
# This configures the way that the item spawned in with `/sushwand` will look
# please note that currently these items will not update the way they look, old versions will maintain their previous configuration.
silence-wand:
custom-name: "<yellow>Shush Stick</yellow>" 
item-type: STICK # type the item actually is
item-model: "" # model location for the texture of the item
enchantment-glint: true # Whether the item should glow like it's enchanted
command-cooldown-seconds: 600 # How long after getting an item someone needs to wait to get one again. In seconds.
mobs: # Mobs that should be enabled by default, if someone gets a wand they can silence these mobs.
- CHICKEN
- COW
- PIG
- SHEEP
- ZOMBIE
- SKELETON
- WITCH
- SPIDER
- VILLAGER
max-radius: 10.0 # Max radius for silencing mobs in an area

```