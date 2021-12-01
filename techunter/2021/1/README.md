# [Advent of Code](http://adventofcode.com/)

- [[About]](http://adventofcode.com/2021/about)
- [[Events]](http://adventofcode.com/2021/events)
- [[Shop]](https://teespring.com/stores/advent-of-code)
- [[Log In]](http://adventofcode.com/2021/auth/login)

# // [2021](http://adventofcode.com/2021)

- [[Calendar]](http://adventofcode.com/2021)
- [[AoC++]](http://adventofcode.com/2021/support)
- [[Sponsors]](http://adventofcode.com/2021/sponsors)
- [[Leaderboard]](http://adventofcode.com/2021/leaderboard)
- [[Stats]](http://adventofcode.com/2021/stats)

Our [sponsors](http://adventofcode.com/2021/sponsors) help make Advent of Code possible:

[Replit](https://2021-aoc-templates.util.repl.co/) \- Code and host in your browser with no setup in Python, React, Kaboom.js, Java, C, Nix, you name it, even Solidity. Happy coding!

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

To play, please identify yourself via one of these services:

[[GitHub]](http://adventofcode.com/auth/github) [[Google]](http://adventofcode.com/auth/google) [[Twitter]](http://adventofcode.com/auth/twitter) [[Reddit]](http://adventofcode.com/auth/reddit)\- [[How Does Auth Work?]](http://adventofcode.com/about#faq_auth)

