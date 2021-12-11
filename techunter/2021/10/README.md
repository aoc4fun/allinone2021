# [Advent of Code](/)

- [[About]](/2021/about)
- [[Events]](/2021/events)
- [[Shop]](https://teespring.com/stores/advent-of-code)
- [[Settings]](/2021/settings)
- [[Log Out]](/2021/auth/logout)

TecHunter 10\*

# 0x0000\| [2021](/2021)

- [[Calendar]](/2021)
- [[AoC++]](/2021/support)
- [[Sponsors]](/2021/sponsors)
- [[Leaderboard]](/2021/leaderboard)
- [[Stats]](/2021/stats)

Our [sponsors](/2021/sponsors) help make Advent of Code possible:

[Ahrefs](https://ahrefs.com/) \- Leverage modern hardware and advanced programming to process real big data. Work on the next general purpose search engine. From anywhere in the world. OCaml, Reasonml, C++

## \-\-\- Day 10: Syntax Scoring ---

You ask the submarine to determine the best route out of the deep-sea cave, but it only replies:

```
Syntax error in navigation subsystem on line: <span title="Some days, that's just how it is.">all of them</span>
```

_All of them?!_ The damage is worse than you thought. You bring up a copy of the navigation subsystem (your puzzle input).

The navigation subsystem syntax is made of several lines containing _chunks_. There are one or more chunks on each line, and chunks contain zero or more other chunks. Adjacent chunks are not separated by any delimiter; if one chunk stops, the next chunk (if any) can immediately start. Every chunk must _open_ and _close_ with one of four legal pairs of matching characters:

- If a chunk opens with`(`, it must close with `)`.
- If a chunk opens with`[`, it must close with `]`.
- If a chunk opens with`{`, it must close with `}`.
- If a chunk opens with`<`, it must close with `>`.

So, `()` is a legal chunk that contains no other chunks, as is `[]`. More complex but valid chunks include `([])`, `{()()()}`, `<([{}])>`, `[<>({}){}[([])<>]]`, and even `(((((((((())))))))))`.

Some lines are _incomplete_, but others are _corrupted_. Find and discard the corrupted lines first.

A corrupted line is one where a chunk _closes with the wrong character_ \- that is, where the characters it opens and closes with do not form one of the four legal pairs listed above.

Examples of corrupted chunks include `(]`, `{()()()>`, `(((()))}`, and `<([]){()}[{}])`. Such a chunk can appear anywhere within a line, and its presence causes the whole line to be considered corrupted.

For example, consider the following navigation subsystem:

```
[({(<(())[]>[[{[]{<()<>>
[(()[<>])]({[<{<<[]>>(
{([(<{}[<>[]}>{[]{[(<()>
(((({<>}<{<{<>}{[]{[]{}
[[<[([]))<([[{}[[()]]]
[{[{({}]{}}([{[{{{}}([]
{<[[]]>}<{[{[{[]{()[[[]
[<(<(<(<{}))><([]([]()
<{([([[(<>()){}]>(<<{{
<{([{{}}[<[[[<>{}]]]>[]]

```

Some of the lines aren't corrupted, just incomplete; you can ignore these lines for now. The remaining five lines are corrupted:

- `{([(<{}[<>[]}>{[]{[(<()>` \- Expected `]`, but found `}` instead.
- `[[<[([]))<([[{}[[()]]]` \- Expected `]`, but found `)` instead.
- `[{[{({}]{}}([{[{{{}}([]` \- Expected `)`, but found `]` instead.
- `[<(<(<(<{}))><([]([]()` \- Expected `>`, but found `)` instead.
- `<{([([[(<>()){}]>(<<{{` \- Expected `]`, but found `>` instead.

Stop at the first incorrect closing character on each corrupted line.

Did you know that syntax checkers actually have contests to see who can get the high score for syntax errors in a file? It's true! To calculate the syntax error score for a line, take the _first illegal character_ on the line and look it up in the following table:

- `)`: `3` points.
- `]`: `57` points.
- `}`: `1197` points.
- `>`: `25137` points.

In the above example, an illegal `)` was found twice ( `2*3 = <em>6</em>` points), an illegal `]` was found once ( `<em>57</em>` points), an illegal `}` was found once ( `<em>1197</em>` points), and an illegal `>` was found once ( `<em>25137</em>` points). So, the total syntax error score for this file is `6+57+1197+25137 = <em>26397</em>` points!

Find the first illegal character in each corrupted line of the navigation subsystem. _What is the total syntax error score for those errors?_

To begin, [get your puzzle input](10/input).

Answer:

You can also [Shareon
[Twitter](https://twitter.com/intent/tweet?text=%22Syntax+Scoring%22+%2D+Day+10+%2D+Advent+of+Code+2021&url=https%3A%2F%2Fadventofcode%2Ecom%2F2021%2Fday%2F10&related=ericwastl&hashtags=AdventOfCode) [Mastodon](javascript:void(0);)] this puzzle.

