export class Fish {
  timer: number
  constructor(timer = 8) {
    this.timer = timer
  }

  /**
   * decrease counter
   * and return true if child is born
   */
  liveADay(): boolean {
    if (this.timer === 0) {
      this.timer = 6
      return true
    }
    this.timer--

    return false
  }
}
