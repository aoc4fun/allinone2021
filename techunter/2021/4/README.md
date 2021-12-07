# [Advent of Code](/)

- [[About]](/2021/about)
- [[Events]](/2021/events)
- [[Shop]](https://teespring.com/stores/advent-of-code)
- [[Settings]](/2021/settings)
- [[Log Out]](/2021/auth/logout)

TecHunter 7\*

# sub y{ [2021](/2021)}

- [[Calendar]](/2021)
- [[AoC++]](/2021/support)
- [[Sponsors]](/2021/sponsors)
- [[Leaderboard]](/2021/leaderboard)
- [[Stats]](/2021/stats)

Our [sponsors](/2021/sponsors) help make Advent of Code possible:

[ZBRA](https://jobs.quickin.io/zbrasolutions) \- Já pensou em trabalhar em uma empresa criada \*por\* devs \*para\* devs? Venha codificar seu futuro na ZBRA!

## \-\-\- Day 4: Giant Squid ---

You're already almost 1.5km (almost a mile) below the surface of the ocean, already so deep that you can't see any sunlight. What you _can_ see, however, is a giant squid that has attached itself to the outside of your submarine.

Maybe it wants to play [bingo](https://en.wikipedia.org/wiki/Bingo_(American_version))?

Bingo is played on a set of boards each consisting of a 5x5 grid of numbers. Numbers are chosen at random, and the chosen number is _marked_ on all boards on which it appears. (Numbers may not appear on all boards.) If all numbers in any row or any column of a board are marked, that board _wins_. (Diagonals don't count.)

The submarine has a _bingo subsystem_ to help passengers (currently, you and the giant squid) pass the time. It automatically generates a random order in which to draw numbers and a random set of boards (your puzzle input). For example:

```
7,4,9,5,11,17,23,2,0,14,21,24,10,16,13,6,15,25,12,22,18,20,8,19,3,26,1

22 13 17 11  0 
 8  2 23  4 24 
21  9 14 16  7 
 6 10  3 18  5 
 1 12 20 15 19 
 
 3 15  0  2 22 
 9 18 13 17  5 
19  8  7 25 23 
20 11 10 24  4 
14 21 16 12  6 
 
14 21 17 24  4 
10 16 15  9 19 
18  8 23 26 20 
22 11 13  6  5 
 2  0 12  3  7 

```

After the first five numbers are drawn ( `7`, `4`, `9`, `5`, and `11`), there are no winners, but the boards are marked as follows (shown here adjacent to each other to save space):

```
22 13 17 <em>11</em>  0         3 15  0  2 22        14 21 17 24  <em>4</em>
 8  2 23  <em>4</em> 24         <em>9</em> 18 13 17  <em>5</em>        10 16 15  <em>9</em> 19
21  <em>9</em> 14 16  <em>7</em>        19  8  <em>7</em> 25 23        18  8 23 26 20
 6 10  3 18  <em>5</em>        20 <em>11</em> 10 24  <em>4</em>        22 <em>11</em> 13  6  <em>5</em>
 1 12 20 15 19        14 21 16 12  6         2  0 12  3  <em>7</em>

```

After the next six numbers are drawn ( `17`, `23`, `2`, `0`, `14`, and `21`), there are still no winners:

```
22 13 <em>17</em> <em>11</em>  <em>0</em>         3 15  <em>0</em>  <em>2</em> 22        <em>14</em> <em>21</em> <em>17</em> 24  <em>4</em>
 8  <em>2</em> <em>23</em>  <em>4</em> 24         <em>9</em> 18 13 <em>17</em>  <em>5</em>        10 16 15  <em>9</em> 19
<em>21</em>  <em>9</em> <em>14</em> 16  <em>7</em>        19  8  <em>7</em> 25 <em>23</em>        18  8 <em>23</em> 26 20
 6 10  3 18  <em>5</em>        20 <em>11</em> 10 24  <em>4</em>        22 <em>11</em> 13  6  <em>5</em>
 1 12 20 15 19        <em>14</em> <em>21</em> 16 12  6         <em>2</em>  <em>0</em> 12  3  <em>7</em>

```

Finally, `24` is drawn:

```
22 13 <em>17</em> <em>11</em>  <em>0</em>         3 15  <em>0</em>  <em>2</em> 22        <em>14</em> <em>21</em> <em>17</em> <em>24</em>  <em>4</em>
 8  <em>2</em> <em>23</em>  <em>4</em> <em>24</em>         <em>9</em> 18 13 <em>17</em>  <em>5</em>        10 16 15  <em>9</em> 19
<em>21</em>  <em>9</em> <em>14</em> 16  <em>7</em>        19  8  <em>7</em> 25 <em>23</em>        18  8 <em>23</em> 26 20
 6 10  3 18  <em>5</em>        20 <em>11</em> 10 <em>24</em>  <em>4</em>        22 <em>11</em> 13  6  <em>5</em>
 1 12 20 15 19        <em>14</em> <em>21</em> 16 12  6         <em>2</em>  <em>0</em> 12  3  <em>7</em>

```

At this point, the third board _wins_ because it has at least one complete row or column of marked numbers (in this case, the entire top row is marked: `<em>14 21 17 24  4</em>`).

The _score_ of the winning board can now be calculated. Start by finding the _sum of all unmarked numbers_ on that board; in this case, the sum is `188`. Then, multiply that sum by _the number that was just called_ when the board won, `24`, to get the final score, `188 * 24 = <em>4512</em>`.

To guarantee victory against the giant squid, figure out which board will win first. _What will your final score be if you choose that board?_

Your puzzle answer was `69579`.

The first half of this puzzle is complete! It provides one gold star: \*

## \-\-\- Part Two ---

On the other hand, it might be wise to try a different strategy: let the giant squid win.

You aren't sure how many bingo boards a giant squid could play at once, so rather than waste time counting its arms, the safe thing to do is to _figure out which board will win last_ and choose that one. That way, no matter which boards it picks, it will win for sure.

In the above example, the second board is the last to win, which happens after `13` is eventually called and its middle column is completely marked. If you were to keep playing until this point, the second board would have a sum of unmarked numbers equal to `148` for a final score of `148 * 13 = <em>1924</em>`.

Figure out which board will win last. _Once it wins, what would its final score be?_

Answer:

Although it hasn't changed, you can still [get your puzzle input](4/input).

You can also [Shareon
[Twitter](https://twitter.com/intent/tweet?text=I%27ve+completed+Part+One+of+%22Giant+Squid%22+%2D+Day+4+%2D+Advent+of+Code+2021&url=https%3A%2F%2Fadventofcode%2Ecom%2F2021%2Fday%2F4&related=ericwastl&hashtags=AdventOfCode) [Mastodon](javascript:void(0);)] this puzzle.

