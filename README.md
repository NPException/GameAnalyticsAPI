# GameAnalyticsAPI
An API to use the service of [Game Analytics](http://www.gameanalytics.com/) in minecraft mods. (Although you could easily just delete the few Minecraft related parts and use it for any game.)

*Any questions that are not answered in this readme, or just want to chat? Come to [#gaapi](http://irc.lc/esper/gaapi) on EsperNet! :)*

## How to use -> *[Explanation Picture](http://i.imgur.com/nYsnc27.png)*
1. If you do not already have one, create an account at [Game Analytics](http://www.gameanalytics.com/) and set everything up for your game (or rather mod) there.
2. Create a static instance of [MCSimpleAnalytics](https://github.com/NPException42/GameAnalyticsAPI/blob/master/src/main/java/de/npe/gameanalytics/minecraft/MCSimpleAnalytics.java) in your main mod class. (For this guide we assume you call it `analytics`)
3. If you only want to track how many active users your mod has over a given period of time, or how your playerbase is distributed over the world, you are done and can ignore the next part.

## Logging events
After you have created your ´analytics´ instance in your preInit method, you can use it to collect events.

_Depending on what you want to track, you should (as always in MC modding) keep an eye on `world.isRemote`. Only track events on a side where they belong. Otherwise you might get double events for single player sessions._

I know that the method names of `analytics` sound kind of weird, but they follow a pattern: `event[CATEGORY](..)` So the method `analytics.eventDesign(..)` has not much to do with designing events, but creates an event of the "design" category on Game Analytics.

`analytics.eventDesign(..)` is the method you will probably use most. It is used for all game design related events. So basically all events except for error messages and user infos. This is an example for how you log an event:

````java
// logs that a player was killed by my fabulous mod sword
analytics.eventDesign("ItemUse:MyFancySword:KilledPlayer");
````

The method `eventDesign` exists in variants of up to 3 parameters:
- `eventID` *(mandatory)*: This field specifies the exact type of event. GameAnalytics treats colons (`:`) as separators, which is nice. A common practice is building the event ID like this: `[category]:[sub_category]:[outcome/action]` You can also have more sub categories if necessary.
- `area`: This is to specify the region where the event happened. For example the dimension ("Nether") or a biome ("flatlands")
- `value`: a value that might be necessary for your event. You could track how many times a player swung your fancy sword within a certain amount of time and send this event afterwards, for example.

## Where do I get my GA game key and secret key again?
Navigate to the settings of your game on GA and there they are:
<img src="http://i.imgur.com/mSbaSbT.png"/>
