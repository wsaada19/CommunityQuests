# Community Quests

## Summary
The Server Quests plugin allows server owners and administers to create server wide quests for players to participate in either
working together or working against each other. 

## Installation
The jar for this plugin can be downloaded here [Spigot Link]. Add the jar file this to the plugin folder on your spigot server. See the guide below to configure quests to use on your server. There are no hard dependencies, however if you have vault and 
an economy plugin installed you can give players money as a reward for completing quests.

## Commands 

#### /cq start
Opens up a GUI to begin a new quest 

#### /cq start [QuestId] [coop, comp]
Starts a quest with the given ID and quest type

#### /cq view 
Opens up a GUI to see the progress of all active quests

#### /cq stop
Opens up a GUI to end active quests

#### /cq reload 
Reloads the configuration file

## Configuration
The config.yml provides a powerful customization system allowing you to create 
server-wide quests for your players to enjoy. All quests are created in the config.yml file and are started in game.

Currently, the configuration file is the only way to create quest types. I hope to include a GUI to do this in game or on a website in the future.

Under the Quests section quests can be created, the field (the example below uses ExampleQuest),
must be unique.

Here is an example of a quest where everyone on the server must work together to kill a combined 100 zombies, pigs and zombie pigmen to finish the quest. 
```yaml 
Quests:
  ExampleQuest: # this value can be anything but it must be unique
    displayName: "&cZombie and Pig Slayer"
    type: mobkill # required, see type list for available types
    entities: # This is an optional parameter, if it doesn't exist the quest will count ALL mob kills. entity reference: https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/entity/EntityType.html
      - Zombie
      - Pig
      - Pig_Zombie
    description: Kill 100 zombies and pigs!
    goal: 100 # The goal is the amount of the task to be completed
    rewards: # set customized rewards for player's who contribute to the quest
      ... Rewards format is shown below ...
  OtherQuest:
    Other quest info...
```

#### displayName (required) 

The name that will be shown for the quest you've created

#### description (required)

A short description of the quest

#### type (required)
The quest type, see here for a list of types: 

#### goal (required)
The amount you'll need to complete to finish the quest

#### materials (optional)
This is an optional parameter for the following quest types: 
blockbreak, blockplace, donate. If the field is empty or non existent then all blocks will be considered.
You can include patterns in the blocks list such as 'Ore', and every material that has ore in it's name will 
be a part of the quest.

#### entities (optional)
This is an optional field for the following quest types: mobkill, projectilekill.
If empty or not used then all mob types will be included.

#### rewards (optional)
The rewards that will be given to players after completing the quest. There are three types of 
rewards: money, experience and item rewards. Experience and money rewards are earned based on how much you contribute to the
quest. Item rewards are given to everyone in cooperative quests, and to the winner in competitive quests.
More reward options will be added in the future.

**Note: Only money rewards will be given to players who contributed but are offline when the quest is complete , I plan on addressing this in the future.**

Rewards example
```yaml 
rewards:
    experience: 100
    money: 1000 # You must have Vault and an Economy plugin installed for this to work
    diamondsword: # this can be anything
      material: DIAMOND_SWORD
      amount: 1
      displayName: "&bPowerful Diamond Sword" # optional ability to change the name used in messages
    apples:
      material: APPLE
      amount: 12
```

### Competitive vs. Cooperative
There are two quest types to chose from: competitive and cooperative. If you're using the GUI which is opened from the /sp start command, after choosing the quest you'd like to run you're presented with a 
GUI to pick the type. You can also do /sq start [QuestId] [coop/comp] . I plan on adding the option to bind 
a quest to a specific type in the config in a later update.

#### Cooperative 
Cooperative quests involve everyone on the server working together to complete the goal. For example in the quest created in the 
configuration section, the quest will be complete once 100 zombies, pigs and zombie pigmen have been killed. Note: This means 100 total of
any combination of zombies/pigs/zombie pigman kills that add to 100. 

Player's are rewarded based on how much they contribute to the quest. If the reward is 1000 
money and Player1 kills 50 of the 100 zombies, Player1 will receive 500. Item rewards are given to every player who contributes to 
the quest.

#### Competitive
Competitive quests put player's against each other to see who can complete a goal first. Using our zombie/pig example
from above, the quest will end once a single player gets 100 kills for the correct mob types.

Money/experience awards work the same way as cooperative quests. If the money reward is 1000 the winner of the competition will 
get 1000 . If Player1 came in second with 70 kills, he or she will get 700. The winner is the only person who will get items in a competitive quest.
Think of it as more of a challenge than a quest.

### Quest Types
- mobKill
- catchFish
- playerKill
- blockBreak
- blockPlace
- projectileKill
- shear
- tame 
- donate
- milkcow

