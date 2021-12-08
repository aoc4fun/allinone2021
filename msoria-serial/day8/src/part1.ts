import * as FS from 'fs'

export function part1() {
  let lines = FS.readFileSync('assets/input.txt', 'utf-8').split('\n')

  lines = lines.map((l) => l.replace('\r', ''))

  const inputs = lines.map((line) => line.split(' | '))

  const outputs = inputs.map(([i, output]) => output.split(' '))

  const flatOutputs = outputs.flat(2)

  // 2 segments
  const oneDigitCount = flatOutputs.reduce(
    (previousValue, currentValue) =>
      previousValue + (currentValue.length === 2 ? 1 : 0),
    0
  )

  // 4 segments
  const fourDigitCount = flatOutputs.reduce(
    (previousValue, currentValue) =>
      previousValue + (currentValue.length === 4 ? 1 : 0),
    0
  )

  // 3 segments
  const sevenDigitCount = flatOutputs.reduce(
    (previousValue, currentValue) =>
      previousValue + (currentValue.length === 3 ? 1 : 0),
    0
  )

  // 7 segments
  const heightDigitCount = flatOutputs.reduce(
    (previousValue, currentValue) =>
      previousValue + (currentValue.length === 7 ? 1 : 0),
    0
  )

  const simpleDigitsOccurrences =
    oneDigitCount + fourDigitCount + sevenDigitCount + heightDigitCount

  console.log('--- PART 1 ---')
  console.log('oneDigitCount', oneDigitCount)
  console.log('simpleDigitsOccurrences', simpleDigitsOccurrences)
}
