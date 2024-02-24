This file will teach you how to configure the spawn_conditions module. You might be me but you'll still need this.

The default config looks like this:
```json
{
  "-tick_loaded": false,
  "-tag_loaded": true,
  "#": {
    
  },
  "-global": false,
  "minecraft": {
    
  },
  "naturalist": {
    
  }
}
```

## Namespaces:
"minecraft" and "naturalist" are namespaces, these are usually mod names, you can find out the namespaces ingame by
enabling advanced tooltips with F3+H and hover your mouse over the desired mob's spawn egg, you'll see a small gray text
saying something along the lines of "minecraft:cow", whatever is before : is the namespace.
If the entity you want to add doesn't have a spawn egg, you can also find out the namespace through the /summon command.

You can add more and remove namespaces from the config, just make sure you write your namespaces correctly, otherwise it won't work.

## Adding entities:
To add an entity into the list, simply type in the entity's name in the correct namespace, followed by : and a list of conditions or true/false.
Eg. "zombie": false - disables zombies from spawning altogether.

## Adding tags:
To add a tag, select the namespace you want to be affected by the condition, type in the valid tag, and the conditions or true/false.
Eg.
"#living": {"day_time": [0,12000], "spawn": true} - makes all living entities (anything that's not like armorstands, snowballs, items) spawn only during daytime.

## "-global" property:
Setting this to true makes the Main script copy the config's values to be used in other worlds *ONLY DURING THE SAME SESSION*,
if you quit Minecraft, the config values won't be replicated to other worlds, so it's best to write the configs into the
config file and the copy them into the Main script.
Note that the config files will save for the worlds that you entered during the same session!

## "-tick_loaded" & "-tag_loaded" properties:
These two properties control how entities are preserved in your world. These properties can be put inside of the conditions list, of tags or individual entities,
it even works in global tags! *When used inside of conditions **DO NOT ADD - infront!***.
-tick_loaded will determine whether entities that were allowed to load in, should tick. This means running the check for the entity every tick (approx. 0.05 seconds).
Setting -tick_loaded to true would mean that if you are next to an entity that was allowed to spawn, but the condition has changed eg. time of day for condition no longer
matches, the entity would just despawn right in-front of you.
-tag_loaded will determine whether entities that were spawned in should **EVER** check for the condition again.
If you set -tag_loaded to true, any entity that loads in, will always only despawn according to the game and not the module.
If -tag_loaded is false, entities will be despawned as soon as they attempt to be reloaded, eg. you leave the game and rejoin, or you walk too far away from the entity.
-tag_loaded will also make it so the entity will not perform the condition check, acting on behalf of -tick_loaded!
You would likely want to set tag_loaded to true for wolves or such tameable creatures.

## "#" - global tags:
Inside of the "#" section, you can type in ONLY tags (written at the bottom of this file). The tags you type into this section
should NOT have # infront of the tag. These tags will apply to EVERY namespace.

## Conditions:
Conditions are lists of conditions that all need to be true for the entity to either spawn or not spawn,
depending on the "spawn" property in the conditions list.

### Currently valid conditions are:
- dimension : ["the_nether","the_end"] - it's a comma (,) separated list of EXACT dimension names, modded dimensions require a namespace
- day_time : [0,24000] - a list of 2 numbers indicating day time in ticks when the entity can/can't spawn.
*Note: For some conditions, a max value is not required and will be assumed to be the absolute highest value it can be.*
- x : [min,max] - a list of 2 x coordinates where the entity can/can't spawn.
- y : [min,max] - a list of 2 y coordinates where the entity can/can't spawn. Can be used to make mobs only spawn underground for example.
- z : [min,max] - a list of 2 z coordinates where the entity can/can't spawn.
- area : [[min_x,min_y,min_z],[max_x,max_y,max_z]] - a list of 2 lists, which contain minimum and maximum coordinates where the entity can/can't spawn.
- spawn : true/false - REQUIRED, will determine if the entity will spawn when the conditions are true or when the conditions are false.


## Tags:
Tags are extra descriptors that allow you to select groups of entities or very specific entities.
When in the global "#" list, they do not require # to be added to front of the tag (and won't work if "#" is added).
When in any other namespace, tags require there to be # infront of the tag, to define that it is a tag.

Available tags:
- "*" - Refers to everything.
- valid - Any entity with health > 0
- living - Living entities, anything that isn't armorstands, projectiles, items, etc.
- projectile - Arrows, snowballs, eggs, anything that sort.
- minecarts - All minecarts.
- undead - Zombies, skeletons, drowneds, etc.
- arthropod - Spiders, bees, silverfish, etc.
- aquatic - All fish, turtles, squids.
- regular - Many entities that don't belong to undead, arthropod, aquatic, or illager.
- illager - Illager types, vindicators, evokers, witches, ravagers etc.
- monster - Monsters category, zombies, creepers, spiders, anything that prevents you from sleeping.
- creature - Normal creatures like pigs, cows, sheep, zombie horses, etc.
- ambient - Bat... and some modded entities.
- water_creature - Dolphins & squid. NOT GLOW SQUID!
- water_ambient - Fish
- misc - A lot of weird things, villagers, iron/snow-golems, fishing-bobbers, snowballs, etc.

- skeletons - Skeleton types
- raiders - Raider types
- beehive_inhabitors - I don't know why this exists, it's bees.
- arrows - All arrows
- impact_projectiles - Anything that can be launched and can land, eg. snowballs, eggs, arrows.

There are lots more tags that you can use, too many to list and many mods add their own tags, so I suggest you look into those.
