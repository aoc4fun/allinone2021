export class SeaOptimized {
  fishTypes = new Array(9).fill(0)

  constructor(fishTimers: string[]) {
    const countOccurrences = (arr: Array<string>, val: string) =>
      arr.reduce((a, v) => (v === val ? a + 1 : a), 0)
    for (let i = 0; i < 9; i++) {
      this.fishTypes[i] = countOccurrences(fishTimers, i.toString())
    }
  }

  get numberOfFishes() {
    return this.fishTypes.reduce((partial_sum, a) => partial_sum + a, 0)
  }

  simulateDays(numberOfDays: number) {
    for (let i = 0; i < numberOfDays; i++) {
      this.simulateDay()
    }
  }

  private simulateDay() {
    this.fishTypes = [
      this.fishTypes[1],
      this.fishTypes[2],
      this.fishTypes[3],
      this.fishTypes[4],
      this.fishTypes[5],
      this.fishTypes[6],
      this.fishTypes[7] + this.fishTypes[0],
      this.fishTypes[8],
      this.fishTypes[0]
    ]
  }
}
