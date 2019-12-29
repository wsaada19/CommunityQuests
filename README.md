# Server Quests
 
## Installation
The jar for this plugin can be downloaded here : . To use, add this to the plugin folder on your spigot server.
See the guide below to configure quests to use on your server.

## Configuration
The config.yml provides a powerful customization system to allow you to create 
unique quests. 

Under the ServerQuests section quests can be created, the field (the example below uses TestQuest),
must be unique.

##### displayName (required) 

The name that will be shown for the quest you've created

##### description (required)

A short description of the quest

##### type (required)
The quest type, see here for a list of types: 

##### goal (required)
The amount you'll need to complete to finish the quest

##### blocks (optional)
This is an optional parameter for the following quest types: 
blockbreak, blockplace. If the field is empty or non existent then all blocks will be considered.

#####entities (optional)
This is an optional for the following quest types: mobkill, projectilekill.
If empty or not used then all mob types will be included.

#####rewards (optional)
The rewards that will be given to players upon completion of the quest. 

Note: Experience and money rewards are based on how much a player contributes to a quest.
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


### Quest Types
