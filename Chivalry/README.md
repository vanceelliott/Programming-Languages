# CHIVALRY: The preferred language of the middle age

This language was created by Vance Elliott as part of the honors programming languages curriculum at Westminster in the Spring 2021.

#The Basics

Every segment of code is ended by an exclamation point, as a knight is always excited to do a good job. Comments are started using `{`  and ended using  `}`.

## Variables

When travelling on a perilous journey, one often has to delegate work to squires. Any respected knight knows to give items and tasks to measley servants, allowing one's hands to be unburdened for any battles that may arise. 

### Declarations

When hiring a squire, there is very little work for the knight to do. The Chivalry language takes it upon itself to seek the local inns for worthy servants. All the user must do is use the keyword `squire` followed by a name and preceded by a type.

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

The word typing is noted by using the character `'`, i.e. `'red'`

### Changing thy item of thou squires

Squires cannot hold two things at once, so if you were to give a new item to a squire, they would drop the item they were currently holding.

There are several ways to modify an item held by a squire:

- num squires - all commands take in two numbers as input
    1. The command `combine` will add two numbers together
    2. The command `smash` will subtract the second number from the first number
    3. The command `witchcraft` will multiply two numbers together
    1. The command `chop` will divide the first number by the second.
    
  
    For example: smash(2, 3) will output 6 

- word squires
  1. The command `combine` will also add two words together
  2. The command `seer` will take in two inputs, one word squire and one num squire, selecting the letter at the location dictated by the num squire.
    3. The command `voodoo` will reverse the order of a word. i.e. `voodoo('red')` is equal to `'der'`

### MULTITASKING - A must for an honorable knight

To reduce the time spent finding a squire and giving it an object, the Chivalry language allows one to do both simultaneously. If one wishes to do so, one must use the words:
`num squire Jonathan hold 4`. Num can be substituted for any other type, as long as it matches the value of the squire's possession.

## FUNCTIONS

### Giving squires tasks

A squire can also perform tasks, able to take in the items of other squires as parameters
and coming forth with a result. If you would like to give a squire a function, one must declare it in this specific way:
`num squire Jonathan do (whatever task you prefer) with (whatever parameters you have)`.
The types of the task squires are the same as holding squires except for a new typing `ghoul` that allows the function to return void. When writing a parameter, or future squire, put the type of the squire and then a temporary name, i.e. `(num jonathan, word ronathan)`

### Calling forth the squire

If you wish to call upon the squire to do their task, one must simply state the name of the squire and what other holding/task squires they would like to seek aid from: `Jonathan with Bobathan and Frederickathan`.

Side Note - While it is not required that a name end with 'athan', we strongly urge you to do that very thing when naming your squires, as it is so epicly cool.

### Specifying the task objective

After defining a task squire and giving the squire a quest to complete, one must tell the squire what to return. This item, or quest, must be in correspondence with the squire's type. If there is no object to be retrieved, one must use the `ghoul` typing. In order for a hired squire to acquire an item of your desire, recant this spell:
`retrieve `  - followed by the object of retrieval.

## Trolls and Diviners

### Hot gossip about our very own diviners

A diviner is one of the knight's best tools to judge good and evil. They are often paired with moral squires, someone who possesses either the quality of goodness or evilness, something only a diviner can read. If you would like to witness the full power of a diviner, simply utter this phrase: `divine Jonathan (segment of code)` - Jonathan obviously being replacable by anything you want divined, as long as it maintains that moral typing.
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
    - Mountain trolls differ from river trolls in many ways like the fact that they don't need food to survive and are actually licensed diviners. Since trolls are evil, they are fueled by the power of darkness. They are fed a moral typing and will only run if it is evil. Once it is good, the trolls will stop whatever they are doing.
    - Mountain trolls are very introverted creatures and do not socialize much among themselves, so one can only use one mountain troll and therefore do not have to specify the amount of trolls.
    - One can enlist the help of the trolls by using these words: `feed mountain troll Jonathan (code)` - Jonathan obviously can be substituted with any moral value.
    
 
- Mischievous trolls
    - Don't use them.
    - These trolls play tricks on innocent knights, something an honest warrior will not partake in.
    - These pranks range in severity from a program crashing to stealing a squires item.
    - Don't use them.
    - But if you want to deal in the dark arts, utter this despicable sequence `Mischievous trolls play`.
    - Don't use them.

##Diviner Artifacts

There are many magical artifacts that the diviners use to expedite the goodness reading process. These objects allow the user to combine and modify the values of moral squires.

### Unicorn Based Products

Unicorn Based Products only take in one moral, a property derived from unicorns only having one stuffed animal during their childhood. Some wizards attempt to say that this is in fact due to their one horn, but the stuffed animal theory is much more prevalent.

There are two UBP's. One is an artifact that switches the value of the moral. One can utilize this artifact by using this keyword `switch()`. The other artifact is entitled the "Ring of Despair" and corrupts everything it comes across. One can use the ring by saying `despair()`.

### Two-Toed Sloth Objects

These objects take two parameters into consideration. This is due to the sloths' tendency to become double agents for the king. They are often looked over due to their laziness and therefore make great spies.

There are two TTSO's as well. The first object makes sure that both parameters are good, returning evil if one or both parameters are not of upstanding morals. This can be used by writing `holy()`. The second object checks to see if either parameter is good, returning evil if both or none have a good moral. One uses this object by declaring `realistic()`.

### Tri-Tip-Beef

These beefs take in three parameters, most likely due to the illuminati's influence on the farming industry.

There is only one TTB. This TTB takes in three morals, checking to see if two are good. To use this, write `prettygood()`.

## More of the Diviner's Arsenal

While there are many diviner artifacts used to combine/modify a certain morals, one can actually use other tools to create their own situations for the diviner to read. For example, one could compare two numbers to see which would be greater. If a knight wrote 5 is less than 2, a diviner would read that line and deem the moral to be evil. Here are a list of these tools:

-`better` sees if a number is greater than another number.

-`equal` sees if a number is equal to another number. Also works with words.

-`betterequal` sees if a number is greater or equal to another number

One could use these tools by saying `2 better 5`.

##Taverns

While having a squire hold a singe item is great, sometimes a knight needs multiple items to be held in one place. This is where taverns become a knight's best friend. Taverns hold multiple squires, all of the same type, and store them in the same place.

###Building a tavern

In order to create a place for your squires, you must define how many rooms will be needed in the tavern. To do this, write `build tavern tavernathan 6 rooms`. That would store 6 squires.

###Modifying the tavern's guests

One cannot change the size of a tavern, as that is impossible. But guests can be added, changed, and removed from each room in the building. To do so, use this syntax: `tavernathan room 6 guest Jonathan`. That would change the room 6 squire inhabitant to be Jonathan. If you would like to empty a room, use the keyword `noone`.

###Buzzing down squires and referencing the tavern

If you would like to call out a specific squire in a tavern just say the tavern's name and room number. i.e. `tavernathan room 6` would output Jonathan's item.

If you would like to reference the tavern, just use the tavern's name.

##The Knight's Essential Weapons Kit

We at Chivalry have provided many weapons and tools for the knight in our language, allowing many tasks to be performed more efficiently. Here's a list of some of those:

- `holler()` takes in a word and writes it to the screen.
- `hither()` does the same thing as holler, but writes on a new line.
