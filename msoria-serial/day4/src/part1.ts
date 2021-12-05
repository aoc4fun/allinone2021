import { createGame } from './createBingo'

export function part1() {
  const { bingo, drawnNumbers } = createGame('assets/input.txt')

  let winningBoardPoints
  let i = 0
  do {
    winningBoardPoints = bingo.getWinningBoard()?.points
    bingo.markNumber(drawnNumbers[i])
    i++
  } while (winningBoardPoints === undefined)

  console.log('--- PART 1 ---')
  console.log('drawnNumbers', drawnNumbers)
  console.log('won in ' + i + ' turns')
  console.log('winning board points', winningBoardPoints)
}
