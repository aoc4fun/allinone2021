import { Segment } from './Segment'

export class Grid {
  grid: number[][]

  constructor(height: number, width: number) {
    this.grid = Array.from(
      {
        length: width
      },
      () => new Array(height).fill(0)
    )
  }

  get dangerousZoneArea(): number {
    return this.grid
      .flat(2)
      .reduce(
        (previousValue, currentValue) =>
          previousValue + (currentValue >= 2 ? 1 : 0),
        0
      )
  }

  addSegment(segment: Segment) {
    if (segment.isVertical) {
      const x = segment.from.x
      const from = Math.min(segment.from.y, segment.to.y)
      const to = Math.max(segment.from.y, segment.to.y)
      for (let i = from; i <= to; i++) {
        this.addPoint(x, i)
      }
    } else if (segment.isHorizontal) {
      const y = segment.from.y
      const from = Math.min(segment.from.x, segment.to.x)
      const to = Math.max(segment.from.x, segment.to.x)
      for (let i = from; i <= to; i++) {
        this.addPoint(i, y)
      }
    } else {
      // is diagonal
      const XIncrement = segment.from.x > segment.to.x ? -1 : +1
      const YIncrement = segment.from.y > segment.to.y ? -1 : +1
      const fromX = segment.from.x
      const fromY = segment.from.y
      const toX = segment.to.x
      let i = -1
      do {
        i++
        this.addPoint(fromX + i * XIncrement, fromY + i * YIncrement)
      } while (fromX + i * XIncrement !== toX)
    }
  }

  toString(): string {
    const rotatedArray = this.grid[0].map((_, colIndex) =>
      this.grid.map((row) => row[colIndex])
    )
    let value = '\n'
    for (const r of rotatedArray) {
      let line = ''
      for (const l of r) {
        line += l === 0 ? '.' : l
      }
      value += line + '\n'
    }
    return value
  }

  private addPoint(x: number, y: number) {
    // console.log('addPoint', x, y)
    if (y === 32) {
      console.log('here')
    }
    this.grid[x][y]++
  }
}
