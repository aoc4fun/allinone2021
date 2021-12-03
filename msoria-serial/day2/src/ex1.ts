export function ex1(){
  let horizontalPosition = 0
  let depth = 0

  const increaseHorizontalPosition = (value: number) => {
    horizontalPosition += value
  }

  const increaseDepth = (value: number) => {
    depth += value
  }

  const doInstruction = (instruction: string, value: number) => {
    switch (instruction){
      case 'forward':
        increaseHorizontalPosition(value)
        break
      case 'up':
        increaseDepth(-value)
        break
      case 'down':
        increaseDepth(value)
        break
    }
  }

  const lines = require('fs').readFileSync('assets/input.txt', 'utf-8')
    .split('\n')

  for (const line of lines){
    const [instruction, value] = line.split(' ')
    doInstruction(instruction, parseInt(value))
  }

  console.log('--- PART 1 ---')

  console.log('horizontalPosition: ', horizontalPosition)
  console.log('depth: ', depth)

  console.log('Answer: ', horizontalPosition * depth)
}