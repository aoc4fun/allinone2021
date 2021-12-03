# [Advent of Code](/)

- [[About]](/2021/about)
- [[Events]](/2021/events)
- [[Shop]](https://teespring.com/stores/advent-of-code)
- [[Settings]](/2021/settings)
- [[Log Out]](/2021/auth/logout)

TecHunter 1\*

# {year=> [2021](/2021)}

- [[Calendar]](/2021)
- [[AoC++]](/2021/support)
- [[Sponsors]](/2021/sponsors)
- [[Leaderboard]](/2021/leaderboard)
- [[Stats]](/2021/stats)

Our [sponsors](/2021/sponsors) help make Advent of Code possible:

[13\|37](https://tretton37.com/join) \- Happy coding to everyone in this year's Advent of Code!

## \-\-\- Day 1: Sonar Sweep ---

You're minding your own business on a ship at sea when the overboard alarm goes off! You rush to see if you can help. Apparently, one of the Elves tripped and accidentally sent the sleigh keys flying into the ocean!

Before you know it, you're inside a submarine the Elves keep ready for situations like this. It's covered in Christmas lights (because of course it is), and it even has an experimental antenna that should be able to track the keys if you can boost its signal strength high enough; there's a little meter that indicates the antenna's signal strength by displaying 0-50 _stars_.

Your instincts tell you that in order to save Christmas, you'll need to get all _fifty stars_ by December 25th.

Collect stars by solving puzzles. Two puzzles will be made available on each day in the Advent calendar; the second puzzle is unlocked when you complete the first. Each puzzle grants _one star_. Good luck!

As the submarine drops below the surface of the ocean, it automatically performs a sonar sweep of the nearby sea floor. On a small screen, the sonar sweep report (your puzzle input) appears: each line is a measurement of the sea floor depth as the sweep looks further and further away from the submarine.

For example, suppose you had the following report:

```
199
200
208
210
200
207
240
269
260
263

```

This report indicates that, scanning outward from the submarine, the sonar sweep found depths of `199`, `200`, `208`, `210`, and so on.

The first order of business is to figure out how quickly the depth increases, just so you know what you're dealing with - you never know if the keys will get carried into deeper water by an ocean current or a fish or something.

To do this, count _the number of times a depth measurement increases_ from the previous measurement. (There is no measurement before the first measurement.) In the example above, the changes are as follows:

```
199 (N/A - no previous measurement)
200 (<em>increased</em>)
208 (<em>increased</em>)
210 (<em>increased</em>)
200 (decreased)
207 (<em>increased</em>)
240 (<em>increased</em>)
269 (<em>increased</em>)
260 (decreased)
263 (<em>increased</em>)

```

In this example, there are _`7`_ measurements that are larger than the previous measurement.

_How many measurements are larger than the previous measurement?_

Your puzzle answer was `1832`.

The first half of this puzzle is complete! It provides one gold star: \*

## \-\-\- Part Two ---

Considering every single measurement isn't as useful as you expected: there's just too much noise in the data.

Instead, consider sums of a _three-measurement sliding window_. Again considering the above example:

```
199  A
200  A B
208  A B C
210    B C D
200  E   C D
207  E F   D
240  E F G
269    F G H
260      G H
263        H

```

Start by comparing the first and second three-measurement windows. The measurements in the first window are marked `A` ( `199`, `200`, `208`); their sum is `199 + 200 + 208 = 607`. The second window is marked `B` ( `200`, `208`, `210`); its sum is `618`. The sum of measurements in the second window is larger than the sum of the first, so this first comparison _increased_.

Your goal now is to count _the number of times the sum of measurements in this sliding window increases_ from the previous sum. So, compare `A` with `B`, then compare `B` with `C`, then `C` with `D`, and so on. Stop when there aren't enough measurements left to create a new three-measurement sum.

In the above example, the sum of each three-measurement window is as follows:

```
A: 607 (N/A - no previous sum)
B: 618 (<em>increased</em>)
C: 618 (no change)
D: 617 (decreased)
E: 647 (<em>increased</em>)
F: 716 (<em>increased</em>)
G: 769 (<em>increased</em>)
H: 792 (<em>increased</em>)

```

In this example, there are _`5`_ sums that are larger than the previous sum.

Consider sums of a three-measurement sliding window. _How many sums are larger than the previous sum?_

Answer:

Although it hasn't changed, you can still [get your puzzle input](1/input).

You can also [Shareon
[Twitter](https://twitter.com/intent/tweet?text=I%27ve+completed+Part+One+of+%22Sonar+Sweep%22+%2D+Day+1+%2D+Advent+of+Code+2021&url=https%3A%2F%2Fadventofcode%2Ecom%2F2021%2Fday%2F1&related=ericwastl&hashtags=AdventOfCode) [Mastodon](javascript:void(0);)] this puzzle.

