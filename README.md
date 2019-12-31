# Server Quests
 
## Installation
The jar for this plugin can be downloaded here [Spigot Link] . To use, add this to the plugin folder on your spigot server.
See the guide below to configure quests to use on your server. There are no hard dependencies, however if you have vault and 
an economy plugin installed you can give players money as a reward.

## Commands 

#### /sq start
Opens up a GUI to begin a new quest 

#### /sq start QuestId [coop, comp]
Starts a quest with the given ID and quest type

#### /sq view 
Opens up a GUI to see the progress of all active quests

#### /sq stop
Opens up a GUI that allows you to end active quests

#### /sq reload 
Reloads the configuration file

## Configuration
The config.yml provides a powerful customization system allowing you to create 
server-wide quests for your players to enjoy. All quests are created in the config.yml file and are started in game.

Under the ServerQuests section quests can be created, the field (the example below uses TestQuest),
must be unique.

Here is an example of a quest where everyone needs to kill a combined 100 zombies, pigs and zombie pigmen to complete. 
```yaml 
ServerQuests:
  TestQuest: # this value can be anything but it must be unique
    displayName: Zombie and Pig Slayer
    type: mobkill # required, see type list for available types
    entities: # This is an optional parameter, if it doesn't exist the quest will count all mob kills. entity reference: https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/entity/EntityType.html
      - Zombie
      - Pig
      - Pig_Zombie
    description: Kill 100 zombies and pigs!
    goal: 100 # The goal is the amount of the task to be completed
    rewards:
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

#### blocks (optional)
This is an optional parameter for the following quest types: 
blockbreak, blockplace. If the field is empty or non existent then all blocks will be considered.
You can include patterns in the blocks list such as 'Ore', and every material that has ore in it's name will 
be a part of the quest.

#### entities (optional)
This is an optional for the following quest types: mobkill, projectilekill.
If empty or not used then all mob types will be included.

#### rewards (optional)
The rewards that will be given to players after completing the quest. There are three types of 
rewards: money, experience and item rewards. Experience and money rewards are earned based on how much you contribute to the
quest. Item rewards are given to everyone in cooperative quests, and to the winner in competitive quests.
More reward options will be added in the next update.

Note: Only money rewards will be given to offline players now, I plan on addressing this in the future.
```yaml 
rewards:
    experience: 100
    money: 1000 # You must have Vault and an Economy plugin installed for this to work
    diamondsword: # this can be anything
      material: DIAMOND_SWORD
      amount: 1
    apples:
      material: APPLE
      amount: 12
```

### Competitive vs. Cooperative
There are two quest types to chose from: competitive and cooperative. If you're using the GUI which is opened from the /sp start command, after chosing the quest you'd like to run you're presented with a 
GUI to pick the type. You can also do /sq start [QuestId] [coop/comp] . I plan on adding the option to bind 
a quest to a specific type in the config going forward.

#### Cooperative 
Cooperative quests involve everyone on the server working together to complete the goal. For example in the quest created in the 
configuration section, the quest will be complete once 100 zombies, pigs and zombie pigmen have been killed. Note: This means 100 total of
any combination of zombies/pigs/zombiepigman kills that add to 100. 

Player's are rewarded based on how much they contribute to the quest. If the reward includes 1000 
money and Player1 kills 50 of the 100 zombies, Player1 will receive 500 of the server's currency.
Every player who contributes to the quest will be given the items.

#### Competitive
Competitive quests put player's against each other to see who can complete a goal first. Using our zombie/pig example
from above, the quest will end once a single player gets 100 kills for the correct mob types.

Money/experience awards work the same way as cooperative quests. If the money reward is 1000 the winner of the competition will 
get 1000 of the currency on the server. If Player1 came in second with 70 kills, he or she will
get 700. Only the winner of the quest will get the items.
 
### Quest Types
- mobKill
- catchFish
- playerKill
- blockBreak
- blockPlace
- projectileKill
- damageEntity
- shear
- tame 

