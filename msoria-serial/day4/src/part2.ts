import { createGame } from './createBingo'

export function part2() {
  const { bingo, drawnNumbers } = createGame('assets/input.txt')

  let winningBoardInfos
  let i = 0
  do {
    bingo.markNumber(drawnNumbers[i])
    do {
      winningBoardInfos = bingo.getWinningBoard()
      if (winningBoardInfos != undefined) {
        bingo.removeBoard(winningBoardInfos.boardIndex)
      }
    } while (winningBoardInfos != undefined)
    i++
  } while (bingo.numberOfBoards != 0 && i < drawnNumbers.length)

  console.log('--- PART 2 ---')
  console.log(i + ' turns')
  console.log('last board', bingo.lastWinningBoard.board)
  console.log('last board points', bingo.lastWinningBoard.points)
}
