export function ex2() {
  const lines = require('fs')
    .readFileSync('assets/input.txt', 'utf-8')
    .split('\n')

  const bitLength = lines[0].split('').length

  const getNumberOf = (array: Array<string>) => {
    const numberOfOne = new Array(bitLength).fill(0)
    const numberOfZero = new Array(bitLength).fill(0)

    for (const line of array) {
      const bits = line.split('')
      for (const [i, bit] of bits.entries()) {
        if (bit == '1') {
          numberOfOne[i] += 1
        } else {
          numberOfZero[i] += 1
        }
      }
    }
    return { numberOfZero, numberOfOne }
  }

  let oxygenGeneratorRatingLines = [...lines]

  for (let i = 0; i < bitLength && oxygenGeneratorRatingLines.length > 1; i++) {
    const { numberOfZero, numberOfOne } = getNumberOf(
      oxygenGeneratorRatingLines
    )
    const keep = numberOfOne[i] >= numberOfZero[i] ? '1' : '0'
    oxygenGeneratorRatingLines = oxygenGeneratorRatingLines.filter(
      (l: string) => l.split('')[i] === keep
    )
  }

  let CO2ScrubberRatingLines = [...lines]

  for (let i = 0; i < bitLength && CO2ScrubberRatingLines.length > 1; i++) {
    const { numberOfZero, numberOfOne } = getNumberOf(CO2ScrubberRatingLines)
    const keep = numberOfOne[i] >= numberOfZero[i] ? '0' : '1'
    CO2ScrubberRatingLines = CO2ScrubberRatingLines.filter(
      (l: string) => l.split('')[i] === keep
    )
  }

  const oxygenGeneratorRating = parseInt(oxygenGeneratorRatingLines[0], 2)
  const CO2ScrubberRating = parseInt(CO2ScrubberRatingLines[0], 2)
  const answer = oxygenGeneratorRating * CO2ScrubberRating

  console.log('--- PART 2 ---')
  console.log('oxygenGeneratorRatingLines', oxygenGeneratorRatingLines)
  console.log('CO2ScrubberRatingLines', CO2ScrubberRatingLines)
  console.log('CO2ScrubberRating', CO2ScrubberRating)
  console.log('CO2ScrubberRating', CO2ScrubberRating)
  console.log('answer', answer)
}
