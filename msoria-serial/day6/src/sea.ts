import { Fish } from './fish'

export class Sea {
  fishes: Fish[]

  constructor(fishes: Fish[]) {
    this.fishes = fishes
  }

  get numberOfFishes() {
    return this.fishes.length
  }

  simulateDays(numberOfDays: number, displayLog = false) {
    displayLog && console.log('Initial state: ', this.stateToString())
    for (let i = 0; i < numberOfDays; i++) {
      this.simulateDay()
      displayLog &&
        console.log('After ' + (i + 1) + ' days: ', this.stateToString())
    }
  }

  private stateToString() {
    let value = ''
    for (const fish of this.fishes) {
      value += fish.timer + ','
    }
    return value
  }

  private simulateDay() {
    let fishesToAdd = 0
    for (const fish of this.fishes) {
      // if has child
      if (fish.liveADay()) {
        fishesToAdd += 1
      }
    }
    for (let i = 0; i < fishesToAdd; i++) {
      this.addNewFish()
    }
  }

  private addNewFish() {
    this.fishes.push(new Fish())
  }
}
