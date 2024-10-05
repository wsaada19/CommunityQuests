## Table of Contents

-   [Summary](#Summary)
-   [Installation](#Installation)
-   [Commands](#Commands)
-   [Configuration](#Configuration)
-   [Rewards](#Rewards)
-   [Quest Types](#Objectives)
-   [Placeholders](#Placeholders)

## Summary

The Community Quests plugin allows server owners to create server-wide quests for players to participate in either working together or competing against each other.

## Installation

The jar file can be downloaded on [Spigot](https://www.spigotmc.org/resources/community-quests-13-0-16-5-%E2%AD%90run-server-wide-quests-%E2%AD%90.90798/). Add the file to the plugin folder on your Minecraft server. See the guide below to configure quests to use on your server. There are no hard dependencies, however, if you have vault and an economy plugin installed you can give players money as a reward for completing quests. If you have MythicMobs installed you can create quests that require players to kill custom MythicMobs enemies.

## Commands

### /cq start

Opens up a GUI to begin a new quest

### /cq start [QuestId] [coop, comp]

Starts a quest with the given ID and quest type

### /cq view

Opens up a GUI to see the progress of all active quests

### /cq stop

Opens up a GUI to end active quests

### /cq endall

Ends all active quests

### /cq reload

Reloads the configuration file

### /cq donate

Contribute to a donate quest, opens menu where players can place items that will be donated to a given quest.

### /cq donate <playerName> (optional flag -m / --message)

Opens up the donate quest menu for a specific player. Useful if you want the donate menu to be opened without players having to type the command such as clicking a sign.The -m flag will send a message to the player if there are no active donate quests.

### /cq deposit <amount>

Contribute to a money quests

### /cq rewards

Opens a GUI to claim rewards for completed quests

### /cq claim <playerName>

Claims rewards for a specific player. Rewards are directly given to the user

### /cq clearrewards <playerName>

Clear all rewards for a specific player. Do not specify a player name if you want to clear rewards for all players.

## Configuration

The config.yml is used to configure quests in the plugin. Once the quests have been created you can start them using the /cq start command.

Currently, the configuration file is the only way to create quest types. I hope to include a GUI to do this in-game or on a website in the future.

Under the Quests section quests can be created, the field (the example below uses ExampleQuest),
must be unique.

Here is an example of a quest where everyone on the server must work together to kill a combined 100 zombies, pigs, and zombie pigmen and 5 skeletons to complete the quest.

```yaml
Quests:
    ExampleQuest: # this value can be anything but it must be unique
        displayName: "&cZombie and Pig Slayer"
        displayItem: ZOMBIE_HEAD # optional parameter to set the item used in GUIs for a given quest
        worlds: # this parameter is optional, if included the quest will only occur in the specified worlds
            - world
        objectives: # at least one objective is required, each quest can have infinite objectives
            - type: mobkill ## required, see type list for available types
              goal: 100 # The goal is the amount of the task to be completed
              entities: # This is an optional parameter, if it doesn't exist the quest will count ALL mob kills. entity reference: https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/entity/EntityType.html
                  - Zombie
                  - Pig
                  - ZOMBIFIED_PIGLIN
              description: "Zombies & Pigs" # A short description of the objective used in the GUI
            - type: mobkill
              goal: 5
              entities:
                  - Skeleton
              description: Skeletons
        description: Kill 100 zombies and pigs!
        questDuration: 1d # The quest will last for 1 day or until the goal is reached, whichever comes first
        rewards: # set customized rewards for player's who contribute to the quest
            ... Rewards format is shown below ...
    OtherQuest: # Config for another quest
```

### displayName (required)

The name that will be shown for the quest you've created used in messages and GUIs.

### displayItem (optional)

The material that will be used to display a quest in /cq view, start and stop commands. [View this list](https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Material.html)
for available materials.

### description (required)

A short description of the quest used in messages and displays.

### objectives (required)

The objectives are the tasks that need to be completed to finish the quest. Each quest can have multiple objectives. The type of objective is required and the goal is required unless questDuration is set on the entire quest. The description is optional but recommended.

The quest below requires players to place 10 acacia saplings and 10 oak saplings.

```yaml
objectives:
    - type: blockplace
      goal: 10
      materials:
          - ACACIA_SAPLING
      description: Acacia Saplings
    - type: blockplace
      goal: 10
      materials:
          - OAK_SAPLING
      description: Oak Saplings
```

The quest below requires you to kill 15 zombified piglins and 10 zombie pigmen. For mob related quests you can use the customNames field to specify the name of the mob you want to kill. If you want to kill a specific mob type you can use the entities field and specify the mob type from the [entity list](https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/entity/EntityType.html).

```yaml
objectives:
    - type: mobkill
      goal: 15
      customNames:
          - "Zombified Piglin"
      description: Zombified Piglins
    - type: mobkill
      goal: 10
      entities:
          - ZOMBIE_PIGMAN
      description: Zombie Pigmen
```

### questDuration (required if no goal is set)

The time that the quest should run for in seconds, so if it is set to 60s the quest will last for one minute. You could also do 1m to achieve the same result. If both goal and time required are set then the quest will run until the goal is hit
or until the duration is hit, whichever comes first.

```yaml
questDuration: 6d7h30m24s
```

### beforeQuestCommand (optional)

Pass in a command to be run before a quest begins

### afterQuestCommand (optional)

Pass in a command to be run after a quest ends

### questFailedCommand (optional)

Pass in a command to be run if a quest fails, this will only be run if a quest has a time limit set and the goal is not reached. In this case the afterQuestCommand will not be run.

### worlds (optional)

Use this parameter to restrict quests to specific worlds. All worlds in the list will be able to participate in quests. When no value
is provided for worlds then the quest will be enabled in every world.

### materials (DEPRECATED use objectives instead)

Optional parameter for the following quest types:
blockbreak, blockplace, donate, enchantitem, craftitem and consumeitem. If the field is empty or nonexistent then all blocks will be considered.

### entities (DEPRECATED use objectives instead)

Optional field for the following quest types: mobkill, projectilekill and catchfish. If empty or not used, all mob types will be included.

### type (DEPRECATED use objectives instead)

The quest type, see here for a list of types:

### goal (DEPRECATED use objectives instead)

The amount you'll need to complete to finish the quest. To run a collaborative quest a goal is required even if you have a questDuration set.

### disableDuplicateBreaks

If set to true players will not be able to break the same item multiple times. This can cause some issues for items like carrots where planting a new carrot wont work properly since it's the same item.

### disableDuplicatePlaces

Same as above but for block place events.

## Rewards

The rewards are given to players after completing the quest. There are four types of rewards: money, experience, command, and item rewards. Experience and money rewards are earned based on how much you contribute to the
quest. Item rewards are given to everyone in cooperative quests, but only to the winner in competitive quests.
More reward options will be added in the future. Command rewards allow you to run a command for a given player when the quest completes.

The player can use the command /cq rewards to claim their items after a quest is complete. This way players who aren't currently online can still claim their rewards.

**Note: money, experience, commands and items are all optional, you do not need to specify the values you don't want to use.**

Rewards example

```yaml
rewards:
    rewardsLimit: 5 # only top 5 contributors get the rewards, if not set or 0 then everyone gets rewards
    rankedRewards:
        "1": # The key is the rank of the player
            experience: 100
            money: 1000
            rewardMessage: "You have been given 1 diamond sword and 100 experience for achieving first place in the quest!"
            commands:
                - "give %player% diamond_chestplate 1"
            items:
                - material: diamond_sword
                    amount: 1
                    displayName: "&bPowerful Diamond Sword"
        "2":
            experience: 50
            items:
                - material: iron_sword
                    amount: 1
                    displayName: "&7Iron Sword"
            rewardMessage: "You have been given 1 iron sword and 50 experience for achieving second place in the quest!"
        "*": # This is a wildcard, it will apply to all other players
            experience: 25
            rewardMessage: "You have been given 1 stone sword and 25 experience for participating in the quest!"
            items:
                - material: stone_sword
                    amount: 1
                    displayName: "&8Stone Sword"
            commands:
                - 'give %player% diamond_sword 1 0 {display:{Name:"{\"text\":\"Powerful Diamond Sword\",\"color\":\"aqua\"}"},ench:[{id:16,lvl:5}]}'

```

Old Rewards example (still works will be removed eventually)

```yaml
rewards:
    rewardsLimit: 5 # only top 5 contributors get the rewards, if not set or 0 then everyone gets rewards
    experience: 100
    money: 1000 # You must have Vault and an Economy plugin installed for this to work
    commands:
        - give %player% stick 15 # %player% will be replaced with the name of the player receiving the reward.
    items:
        - material: DIAMOND_SWORD
          amount: 1
          displayName: "&bPowerful Diamond Sword" # optionally provide a custom name to the item
        - material: APPLE
          amount: 12
          displayName: "&capples"
```

## Competitive vs. Cooperative

There are two quest types to chose from: competitive and cooperative. If you're using the GUI which is opened from the /sp start command, after choosing the quest you'd like to run you're presented with a
GUI to pick the type. You can also do /cq start [QuestId] [coop/comp].

### Cooperative

Cooperative quests involve everyone on the server working together to complete the goal. For example in the quest created in the configuration section, the quest will be complete once 100 zombies, pigs, and zombie pigmen have been killed. Note: This means 100 total of
any combination of zombies/pigs/zombie pigman kills that add to 100. If questDuration is set then the player's must complete the quest in the given time limit.
If the quest is not completed no rewards will be given out.

Player's are rewarded based on how much they contribute to the quest. If the reward is 1000
money and PlayerX kills 50 of the 100 zombies, PlayerX will receive 500. Item and command rewards are given to every player who contributes to the quest. You can use the **_rewardsLimit_** field in the rewards section of the config if you only want the top x number of players to get a reward.

### Competitive

Competitive quests put the players against each other to see who can complete a goal first. Using our zombie/pig example
from above, the quest will end once a single player gets 100 kills for the correct mob types. If a time limit is set and the quest ends before the goal is reached the top players will still get rewards based on their contributions.

Money/experience awards work the same way as cooperative quests. If the money reward is 1000 the winner of the competition will get 1000. If Player1 came in second with 70 kills, he or she will get 700. The winner is the only person who will get items and commands in a competitive quest. Think of it as more of a challenge than a quest.

## Objectives

-   **donate**- these quests require players to donate a specified number of a given material. The /cq donate command will open a GUI where the player can donate their items. (The items will be gone forever once placed!)
-   **mobkill** - activated when you kill an entity, use entities list to set the mobs to be killed
-   **catchfish**- catch a given number of fish

**Fishing Example:** use the fish names from this list in the entities field: https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Material.html for example: COD, SALMON etc.

```yaml
Fishing:
    displayName: Catch Fish
    objectives:
        - type: catchfish
          goal: 10
          entities:
              - Cod
              - Salmon
              - Pufferfish
    displayItem: FISHING_ROD
    description: Catch 3 fish!
    rewards:
        experience: 100
```

-   **playerkill**: kill other players
-   **blockbreak**: break a block specified in the materials list in the configuration
-   **blockplace**: place a block specified in the materials list in the configuration
-   **projectilekill**: kill entities with a projectile specify entities in the configuration
-   **shear**: shear sheep
-   **tame**: tame animals
-   **milkcow**: milk cow
-   **craftitem**: craft something
-   **consumeitem**: consume an item - like eating an apple
-   **enchantitem**: enchant something
-   **money**: players can contribute money with /cq deposit <amount>
-   **experience**: players must gather Minecraft experience
-   **carvepumpkin**: use shears on a pumpkin
-   **mythicmob**: Kill mobs from the mythicmob plugin (requires MythicMobs to be installed)

### MythicMobs Example

```yaml
MythicMobs:
    displayName: Kill 100 Cave Spiders and 10 Spider Monkeys
    objectives:
        - type: mythicmob
          goal: 100
          description: Cave Spiders
          entities:
              - CAVE_SPIDER
        - type: mythicmob
          description: Spider Monkeys
          goal: 10
          entities:
              - SPIDER_MONKEY
    displayItem: ZOMBIE_HEAD
    description: Kill 100 Cave Spiders before the time runs out!
    questDuration: 30m
    rewards:
        experience: 100
        money: 1000
        rewardMessage: "You have received 100 experience and 1000 coins from the Cave Spider Quest!"
```

## Placeholders

The questId is the key used in the yml file. In the quest above the id would be Fishing. If multiple quests with the same id are live it will chose the one that was created first. If you do not specify a questID, for example %communityquests_goal% it will use the active quest that was started first. Helpful if you only have one quest running at a time.

-   `%communityquests_goal_questId%`: quest goal
-   `%communityquests_complete_questId%`: amount complete for a quest
-   `%communityquests_time_remaining_questId%`: time remaining in the quest
-   `%communityquests_name_questId%`: the quest display name
-   `%communityquests_description_questId%`: the quest description
-   `%communityquests_you_questId%`: the amount you've contributed to the quest
-   `%communityquests_objective_goal_objId_questId%`: the goal for a specific objective (id is based on the order of the objectives in the yml file starting with 0)
-   `%communityquests_objective_completed_objId_questId%`: the amount completed for a specific objective
-   `%communityquests_objective_description_objId_questId%`: the description for the specified objective
-   `%communityquests_top_rank_name_questId%`: the name of the player with the provided rank. For example `%communityquests_top_1_name_questId%` would return the name of the player in first place.
-   `%communityquests_top_rank_contribution_questId%`: similar to the command above for the player's contribution
-   `%communityquests_progressbar_questId%` - a progress bar showing the current progress of the quest
-   `%communityquests_rank_you_questId%` - your rank in the quest

## Permissions

-   communityquests.view - the ability to view active quests and donate ongoing quests
-   communityquests.donate - the ability to use the /cq donate command
-   communityquests.donate.others - the ability to use the /cq donate command and specify another player name.
-   communityquests.money- the ability to use the /cq deposit command
-   communityquests.stop - the ability to stop an active quest
-   communityquests.start - the ability to start a new quest
-   communityquests.reload - the ability to reload the configuration
-   communityquests.bossbar.hide - if set the user will not see the boss bar.
-   communityquests.claim - the ability to claim rewards for completed quests (skips the GUI)
-   communityquests.claim.others - the ability to claim rewards for another player (mainly for OPs so rewards can be claimed with a custom UI)
-   communityquests.clearrewards - the ability to clear rewards for a player (OP default)
