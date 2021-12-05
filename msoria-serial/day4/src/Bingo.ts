import { Board } from './Board'

export class Bingo {
  private readonly boards: Array<Board>

  private lastDrawnNumber: string
  private _lastWinningBoard: {
    board: Board
    boardIndex: number
    points: number
  }

  constructor(boards: Array<Board>) {
    this.boards = boards
  }

  public get numberOfBoards(): number {
    return this.boards.length
  }

  public get lastWinningBoard() {
    return this._lastWinningBoard
  }

  public removeBoard(boardIndex: number) {
    this.boards.splice(boardIndex, 1)
  }

  public markNumber(drawnNumber: string) {
    this.lastDrawnNumber = drawnNumber
    for (const board of this.boards) {
      board.markNumber(drawnNumber)
    }
  }

  public getWinningBoard():
    | { board: Board; boardIndex: number; points: number }
    | undefined {
    for (const [index, board] of this.boards.entries()) {
      if (board.hasWon()) {
        this._lastWinningBoard = {
          board,
          boardIndex: index,
          points: board.getPoints(this.lastDrawnNumber)
        }
        return this._lastWinningBoard
      }
    }
    return undefined
  }
}
