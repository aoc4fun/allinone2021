#/bin/bash

if [ -z "$ADVENT_SESSION" ]
then
  echo "\$ADVENT_SESSION is empty"
  exit 1
fi

if [ -z "$1" ]
then
  echo "\$please specify day number"
  exit 1
fi

echo "Le 1er paramètre est : $1"

curl "https://adventofcode.com/2021/day/$1/input" \
  -H 'authority: adventofcode.com' \
  -H 'cache-control: max-age=0' \
  -H 'sec-ch-ua: " Not A;Brand";v="99", "Chromium";v="96", "Google Chrome";v="96"' \
  -H 'sec-ch-ua-mobile: ?0' \
  -H 'sec-ch-ua-platform: "Linux"' \
  -H 'upgrade-insecure-requests: 1' \
  -H 'user-agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.45 Safari/537.36' \
  -H 'accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9' \
  -H 'sec-fetch-site: same-origin' \
  -H 'sec-fetch-mode: navigate' \
  -H 'sec-fetch-user: ?1' \
  -H 'sec-fetch-dest: document' \
  -H 'accept-language: fr-FR,fr;q=0.9,en-US;q=0.8,en;q=0.7' \
  -H "cookie: _ga=GA1.2.2140291972.1638334208; _gid=GA1.2.2041178474.1638334208; session=$ADVENT_SESSION" \
  --compressed > ../resources/day$1.txt