#!/bin/bash

PREVIOUS=-1
COUNT=0

# Read file line by line
while read -r line; do
  if [[ $PREVIOUS -ne -1 ]]; then
      if [[ $line -gt $PREVIOUS ]]; then
        # increased
        (( COUNT++ ))
        echo "$line (increased)"
      elif [[ $line -lt $PREVIOUS ]]; then
        # decreased
        echo "$line (decreased)"
      fi
  fi
  PREVIOUS=$line
done < "input.txt"
echo "COUNT $COUNT"