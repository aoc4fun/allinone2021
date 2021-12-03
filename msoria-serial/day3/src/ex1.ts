export function ex1() {
  const lines = require('fs')
    .readFileSync('assets/input.txt', 'utf-8')
    .split('\n')

  const bitLength = lines[0].split('').length

  const numberOfOne = new Array(bitLength).fill(0)
  const numberOfZero = new Array(bitLength).fill(0)

  for (const line of lines) {
    const bits = line.split('')
    for (const [i, bit] of bits.entries()) {
      if (bit == '1') {
        numberOfOne[i] += 1
      } else {
        numberOfZero[i] += 1
      }
    }
  }

  // build gamma and epsilon rate
  let gamma = ''
  let epsilon = ''
  for (let i = 0; i < bitLength; i++) {
    if (numberOfOne[i] > numberOfZero[i]) {
      gamma += '1'
      epsilon += '0'
    } else {
      gamma += '0'
      epsilon += '1'
    }
  }

  // to decimal
  const decGamma = parseInt(gamma, 2)
  const decEpsilon = parseInt(epsilon, 2)
  const answer = decGamma * decEpsilon

  console.log('--- PART 1 ---')
  console.log('decGamma', decGamma)
  console.log('decEpsilon', decEpsilon)
  console.log('answer', answer)
}
