# CHIVALRY: The preferred language of the middle age

This language was created by Vance Elliott as part of the honors programming languages curriculum at Westminster in the Spring 2021.

#The Basics

## Variables

When travelling on a perilous journey, one often has to delegate work to squires. Any respected knight knows to give items and tasks to measley servants, allowing one's hands to be unburdened for any battles that may arise. 

### Declarations

When hiring a squire, there is very little work for the knight to do. The Chivalry language takes it upon itself to seek the local inns and taverns for worthy servants. All the user must do is use the keyword `squire` followed by a name and preceded by a type.

For example: `num squire Jonathan`

There are three main types of squires available:

1. num squires
1. word squires
1. moral squires - more about moral squires in diviner section

Since math is difficult, our squires require a calculator for decimals. If you would like to give a squire a calculator use this syntax: `num squire Jonathan calc included`.

### Donning items to thou squires

Once a knight has enlisted the help of several townsfolk, 
one can give these people items, corresponding with their type.

To do so, use the command `give`

For example, `give(Jonathan, 4)`

### Changing thy item of thou squires

Squires cannot hold two things at once, so if you were to give a new item to a squire, they would drop the item they were currently holding.

There are several ways to modify an item held by a squire:

- num squires - all commands take in two numbers as input
    1. the command `combine` will add two numbers together
    2. the command `smash` will subtract the second number from the first number
    3. the command `witchcraft` will multiply two numbers together
    1. The command `chop` will divide the first number by the second.
    
  
    For example: smash(2, 3) will output 6 


### MULTITASKING - A must for an honorable knight

To reduce the time spent finding a squire and giving it an object, the Chivalry language allows one to do both simultaneously. If one wishes to do so, one must use the words:
`num squire Jonathan hold 4`. Num can be substituted for any other type, as long as it matches the value of the squire's possession.

## FUNCTIONS

### Giving squires tasks

A squire can also perform tasks, able to take in the items of other squires as parameters
and coming forth with a result. If you would like to give a squire a function, one must declare it in this specific way:
`num squire Jonathan do (whatever task you prefer)`.
The types of the task squires are the same as holding squires except for a new typing `ghoul` that allows the function to return void.

### Calling forth the squire

If you wish to call upon the squire to do their task, one must simply state the name of the squire and what other holding/task squires they would like to seek aid from: `Jonathan with Bobathan and Frederickathan`.

Side Note - While it is not required that a name end with 'athan', we strongly urge you to do that very thing when naming your squires, as it is so epicly cool.

### Specifying the task objective

After defining a task squire and giving the squire a quest to complete, one must tell the squire what to return. This item, or quest, must be in correspondence with the squire's type. If there is no object to be retrieved, one must use the `ghoul` typing. In order for a hired squire to acquire an item of your desire, recant this spell:
`retrieve `  - followed by the object of retrieval.

## Trolls and Diviners

### Hot gossip about our very own diviners

A diviner is one of the knight's tools to judge good and evil. They are often paired with moral squires, someone who possesses either the quality of goodness or not goodness, something only a diviner can read. If you would like to witness the full power of a diviner, simply utter this phrase: `divine Jonathan (segment of code)` - Jonathan obviously being replacable by anything you want divined, as long as it maintains that moral typing.
If the diviner reads `good`, the script directly afterwards will be read, but not if the diviner senses `evil`. 
The knight can follow up the diviner with a villain clause that reads if the moral squire is evil by enacting this spell: `villain (whatever the code is)`

### General troll knowledge

A knight often has to perform mundane tasks over and over and over and over and over again. In order to make life easier on our soldiers, we provide trolls to do most of those wearisome activities. There are three main types of trolls: river trolls, mountain trolls, and mischievous trolls.

- River trolls
    
    - These trolls are heavily reliant on food, a resource we have in abundance here at Chivalry.
    - Because of their affection for eating, they will do one task for every one food item they receive.
    - Only one river troll can work a task at a time, but one can call on multiple trolls to work for them. This would mean that each troll would eat one food, even though one troll can do the same amount of work as 5000 trolls. This can be useful during some moments in a knights journey.
    - In order to utilize the power of the trolls, one must write these words: `2 river trolls work for 2 food (the task the trolls would perform)`  - the numbers of course can be num squires or any other number. This specific line of code would run once.
    - The amount of food left is recorded by a squire hired by the trolls, so one not need worry about making those calculations himself. To access this variable, say `foodleft`. 
    - River trolls hate the letter z. So if one were to utilize that letter while defining the trolls boring quest, the troll would eat the knight, and the program will crash.


- Mountain trolls
    - These trolls do not need food to work, but in fact utilize diviners. Since trolls are evil, they are fueled by the power of darkness. They are fed a moral typing and will only run if it is evil. Once it is good, the trolls will stop whatever they are doing.
    - Mountain trolls are very introverted creatures and do not socialize much among themselves, so one can only use one mountain troll and therefore do not have to specify the amount of trolls.
    - One can enlist the help of the trolls by using these words: `feed mountain troll Jonathan (code)` - Jonathan obviously can be substituted with any moral value.
    
 
- Mischievous trolls
    - Don't use them.
    - These trolls play tricks on innocent knights, something an honest warrior will not partake in.
    - These pranks range in severity from death to program crashing to a squire being murdered.
    - Don't use them.
    - But if you want to deal in the dark arts, utter this despicable sequence `Mischievous trolls play`.
    - Don't use them.

##