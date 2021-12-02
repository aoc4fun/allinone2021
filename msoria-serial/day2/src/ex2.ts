export function ex2(){
  let horizontalPosition = 0
  let depth = 0
  let aim = 0

  const increaseHorizontalPosition = (value: number) => {
    horizontalPosition += value
  }

  const increaseDepth = (value: number) => {
    depth += value
  }

  const increaseAim = (value: number) => {
    aim += value
  }

  const doInstruction = (instruction: string, value: number) => {
    switch (instruction){
      case 'forward':
        increaseHorizontalPosition(value)
        increaseDepth(aim*value)
        break
      case 'up':
        increaseAim(-value)
        break
      case 'down':
        increaseAim(value)
        break
    }
  }

  const lines = require('fs').readFileSync('assets/input.txt', 'utf-8')
    .split('\n')

  for (const line of lines){
    const [instruction, value] = line.split(' ')
    doInstruction(instruction, parseInt(value))
  }

  console.log('--- PART 2 ---')

  console.log('horizontalPosition: ', horizontalPosition)
  console.log('depth: ', depth)
  console.log('aim: ', aim)

  console.log('Answer: ', horizontalPosition * depth)
}