import * as FS from 'fs'
import { SeaOptimized } from './seaOptimized'

export function part2() {
  const lines = FS.readFileSync('assets/input.txt', 'utf-8')
    .replace('\r', '')
    .split('\n')

  // get values
  const timers: Array<string> = lines[0].split(',')

  // create simulation
  const sea = new SeaOptimized(timers)
  console.log(sea)
  sea.simulateDays(256)

  console.log('--- PART 2 ---')
  console.log('number of fishes', sea.numberOfFishes)
  console.log(sea)
}
