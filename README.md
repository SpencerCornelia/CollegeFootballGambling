# College Football Gambling

The goal of this project is to create a simulator used to predict college football games.  This simulator will aim to predict the spread between two competing teams, in addition to predicting their total scores.

The process is to web scrape data from a college football stats website, scrape the schedules of two competing teams, run the teams through an algorithm, and produce a most likely outcome.

Three pieces of data:
1. The schedules of two teams.  This data will be used to evaluate how a team does against its level of competition.
2. Around 30 different stat categories used to evaluate the quality of a college football team.  These range from yards per play, pass completion percentage, and first down percentage on offense to points allowed per game and tackles for loss per game on defense.
3. Historical point spreads from Vegas casinos.

Since I have access to historical point spreads, team scores, and team statistics from the eyar 2008 until present, I will be able to write an algorithm and test against actual results.  My hope is that predicting past events will be similar to predicting future events.

This will be an ongoing project as I will need to constantly test my algorithm as more data becomes available (future games being played).

The world's best beat the spread around 57-58%.  The break even percentage is 52.38% with the casino's taking around a 10% cut.  The odds for college football games are always -110, which means a gambler will need to wager $110 to win $100.  In the event of a winning gamble, the gambler will receive $100 for his winnings and his original $110 back, walking away with $210 total.

My goal is to become one of the world's best.
