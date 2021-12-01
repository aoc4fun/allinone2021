#!/bin/bash

Values=($(cat "input.txt"))
PREVIOUS=-1

length=${#Values[@]}
for ((index = 0 ; index < ((length - 2)); index++)); do
  SUM=$((Values[$index] + Values[$((index + 1))] + Values[$((index + 2))]))

  if [[ $PREVIOUS -ne -1 ]]; then
    if [[ $SUM -gt $PREVIOUS ]]; then
      # increased
      ((COUNT++))
      echo "$SUM (increased)"
    elif [[ $SUM -lt $PREVIOUS ]]; then
      # decreased
      echo "$SUM (decreased)"
    else
      echo "$SUM (no change)"
    fi
  else
    echo "$SUM (no previous data)"
  fi

  PREVIOUS=$SUM
done
echo COUNT $COUNT