## ABOUT ##

Freeze is a plugin those just prevent one or more player to move. It can be useful sometimes so I've decided to share it.

I hope you'll enjoy it !

(This command can be also found in the plugin AdminCmd. Thanks for the idea.)

Last version : '''1.4.2.1'''

## COMMANDS ##

/freeze <player>

Freeze <player>. If you enter just some characters of the player name, all the players who have those characters in their name will be freezed.

/tmpfreeze <player> <time>

Temporarily freeze <player> for <time>. If you enter just some characters of the player name, all the players who have those characters in their name will be freezed.
<time> can be an amount of seconds or time amount using "like-Essentials" time format (3d4h5m3s for 3 days 4 hours 5 minutes and 3 seconds)

/freezeall [world]

Freeze every players. [world] is optional but if you define it, only the players in [world] will be freezed. 

/unfreezeall

Unfreeze every players.

/rldfreeze

Reload the plugin and it's config.

/freezelist

List all freezed players and temporarily freezed players.

/freezecheck

Check if there is a new update.

## PERMISSIONS ##

freeze.*

Give permissions for /freeze, /freezeall, /unfreezeall, /freezelist, /freezecheck and /rldfreeze.

freeze.freeze

Be able to use /freeze command. Default enabled for ops.

freeze.tmpfreeze

Be able to use /tmpfreeze command. Default enabled for ops.

freeze.freezeall

Be able to use /freezeall command. Default enabled for ops.

freeze.unfreezeall

Be able to use /unfreezeall command. Default enabled for ops.

freeze.reload

Be able to use /rldfreeze command. Default enabled for ops.

freeze.list

Be able to use /freezelist command. Default enabled for ops.

freeze.never

Never be freezed by anyone. Default enabled for ops.

freeze.checkupdate

Be able to use /freezecheck command and receive a message when joining the server if there is a new update for the plugin and if alertupdate option is activated.
Default enabled for ops.

## TWITTER ##

You can follow the twitter account of the plugin for see all news about the development (in real time) and more : @FreezeBukkit.

## COMPILE ##

To compile this plugin your self it is easiest with maven2. You will need to have this install.
When you have this, you only need to run ""mvn clean package"" from any command prompt or terminal.

## CHANGELOG ##

1.0.0

First release.

1.1.0

Add /freezeall and /unfreezeall commands.
Add freeze.never permission.

1.1.1

Bug with BlockIgniteEvent fixed.

1.2.0

Add options for let (or not) people who are freezed use commands or speak in the global chat.

1.3.0

Add option for let (or not) freezed players freezed when the server reboots.
Add /tmpfreeze command for temporarily freeze players.

1.4.0

Lots of changes in code. (Thanks a lot to aumgn for his help !)
Add /rldfreeze command and world argument in /freezeall command.
Add /freezelist command.
Add message option.
<time> argument for /tmpfreeze can now be seconds or "like-Essentials" time format (3d4h5m3s for 3 days 4 hours 5 minutes and 3 seconds)
Temporarily freeze time is also save with freezed players when the server reboot or is reloaded. (when save option is enabled)
Bug fixed when you give freeze.tmpfreeze node without freeze.freeze node.

1.4.1

Add /freezecheck command.
The plugin automatically check if there is a new update and send a message in the console (or to ops or players who have freeze.checkupdate permission if alertupdate option is set to true).
Add freeze.checkupdate permission and alertupdate option.
Fix command option bug.
Spelling corrections.

1.4.2

Updating to CB-1.3.1-R2.0
Using AsyncPlayerChatEvent instead of PlayerChatEvent (deprecated).
Adding lookaround option.

1.4.2.1
Added maven build system to allow for easier building of project by third parties.
