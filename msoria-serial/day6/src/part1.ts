import * as FS from 'fs'
import { Sea } from './sea'
import { Fish } from './fish'

export function part1() {
  const lines = FS.readFileSync('assets/example.txt', 'utf-8')
    .replace('\r', '')
    .split('\n')

  // get values
  const timers: Array<string> = lines[0].split(',')

  // create simulation
  const fishes = timers.map((t) => new Fish(parseInt(t)))
  const sea = new Sea(fishes)
  sea.simulateDays(80)

  console.log('--- PART 1 ---')
  console.log('number of fishes', sea.numberOfFishes)
}
