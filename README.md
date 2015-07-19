# GameAnalyticsAPI
An API to use the service of [Game Analytics](http://www.gameanalytics.com/) in minecraft mods. (Although you could easily just delete the few Minecraft related parts and use it for any game.)

## How to use -> *[Explanation Picture](http://i.imgur.com/viwbcPt.png)*
1. If you do not already have one, create an account at [Game Analytics](http://www.gameanalytics.com/) and set everything up for your game (or rather mod) there.
2. Create a static instance of [SimpleAnalytics](https://github.com/NPException42/GameAnalyticsAPI/blob/master/src/main/java/de/npe/gameanalytics/SimpleAnalytics.java) in your main mod class. (For this guide we assume you call it `analytics`)
3. You should call `analytics.markClientSide()`, if Minecraft is running on a client / not a dedicated server. I recommend doing that by calling `analytics.markClientSide()` from your ClientProxy class during the pre-initilization phase.
4. After that you can use the event methods of `analytics` to collect events. Depending on what you want to log, you should (as always in MC modding) keep an eye on `world.isRemote`. Only log events on a side where they belong.

## Logging events
First, I know that the method names sound kind of weird, but they follow a pattern: `event[CATEGORY](..)` So the method `analytics.eventDesign(..)` has not much to do with designing events, but creates an event of the "design" category on Game Analytics.

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
