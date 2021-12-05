export class Board {
  private numbers: Array<Array<string>>
  private numbersMarked: Array<Array<boolean>>

  constructor(numbers: Array<Array<string>>) {
    this.numbers = numbers
    this.numbersMarked = Array.from(
      {
        // create array of length `row`
        length: this.numbers.length
        // map function to generate values
      },
      () => new Array(this.numbers[0].length).fill(false)
    )
  }

  public markNumber(drawnNumber: string) {
    // console.log('before mark', this)
    for (const [i, row] of this.numbers.entries()) {
      for (const [j, number] of row.entries()) {
        if (number === drawnNumber) {
          this.numbersMarked[i][j] = true
        }
      }
    }
  }

  public hasWon(): boolean {
    // has a complete line
    for (const r of this.numbersMarked) {
      if (r.every((n) => n === true)) {
        return true
      }
    }

    //has a complete column
    for (let j = 0; j < this.numbersMarked[0].length; j++) {
      let hasFalse = false
      for (let i = 0; i < this.numbersMarked.length && !hasFalse; i++) {
        if (this.numbersMarked[i][j] === false) {
          hasFalse = true
        }
      }
      if (!hasFalse) {
        return true
      }
    }
    return false
  }

  public getPoints(lastDrawnNumber: string): number {
    return this.getUnMarkedNumbersSum() * parseInt(lastDrawnNumber)
  }

  private getUnMarkedNumbersSum(): number {
    let sum = 0
    for (const [i, row] of this.numbers.entries()) {
      for (const [j, number] of row.entries()) {
        if (this.numbersMarked[i][j] === false) {
          sum += parseInt(number)
        }
      }
    }
    return sum
  }
}
