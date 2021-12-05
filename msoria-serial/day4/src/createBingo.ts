const FS = require('fs')
import { Board } from './Board'
import { Bingo } from './Bingo'

export function createGame(filePath: string) {
  const lines = FS.readFileSync(filePath, 'utf-8').replace('\r', '').split('\n')

  const drawnNumbers = lines[0].split(',')

  // get grids
  lines.shift()
  lines.shift()
  // create boards
  const boards: Board[] = []
  let newBoardNumbers: string[][] = []
  for (let line of lines) {
    line = line.replace('\r', '')
    if (line === '') {
      boards.push(new Board(newBoardNumbers))
      newBoardNumbers = []
    } else {
      const lineNumbers = line.trim().split(/[ ]+/)
      newBoardNumbers.push(lineNumbers)
    }
  }

  if (newBoardNumbers.length > 0) {
    boards.push(new Board(newBoardNumbers))
  }

  const bingo = new Bingo(boards)
  return { bingo, drawnNumbers }
}
