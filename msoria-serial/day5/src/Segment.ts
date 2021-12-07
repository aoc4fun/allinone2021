import { Point } from './Point'

export class Segment {
  public from: Point
  public to: Point

  constructor(from: Point, to: Point) {
    this.from = from
    this.to = to
  }

  get isVertical(): boolean {
    return this.from.x === this.to.x
  }

  get isHorizontal(): boolean {
    return this.from.y === this.to.y
  }

  get maxX(): number {
    return this.from.x > this.to.x ? this.from.x : this.to.x
  }

  get maxY(): number {
    return this.from.y > this.to.y ? this.from.y : this.to.y
  }
}
