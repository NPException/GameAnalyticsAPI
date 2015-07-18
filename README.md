# GameAnalyticsAPI
An API to easily use the service of [Game Analytics](http://www.gameanalytics.com/) in your mod

## How to use -> *[Explanation Picture](http://i.imgur.com/viwbcPt.png)*
1. If you do not already have one, create an account at [Game Analytics](http://www.gameanalytics.com/) and set everything up for your game (or rather mod) there.
2. Create a static instance of [SimpleAnalytics](https://github.com/NPException42/GameAnalyticsAPI/blob/master/src/main/java/de/npe/gameanalytics/SimpleAnalytics.java) in your main mod class. (For this guide we assume you call it `analytics`)
3. You should call `analytics.markClientSide()`, if Minecraft is running on a client / not a dedicated server. I recommend doing that by calling `analytics.markClientSide()` from your ClientProxy class during the pre-initilization phase.
4. After that you can use the event methods of `analytics` to collect events. Depending on what you want to log, you should (as always in MC modding) keep an eye on `world.isRemote`. Only log events on a side where they belong.
