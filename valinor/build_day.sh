curl -s "https://adventofcode.com/2021/day/$1/input" -H "cookie: session=$(cat .sessionid)" -o day$day/input.txt
