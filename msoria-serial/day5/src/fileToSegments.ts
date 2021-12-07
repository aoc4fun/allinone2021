import * as FS from 'fs'
import { Segment } from './Segment'

export function fileToSegments(filePath: string, allowDiagonal = false) {
  // get from file
  const lines = FS.readFileSync(filePath, 'utf-8').replace('\r', '').split('\n')

  const segments = []

  for (const line of lines) {
    const s = line.split(' -> ')
    if (s?.length === 2) {
      const p1 = s[0].split(',')
      const p2 = s[1].split(',')
      const segment = new Segment(
        { x: parseInt(p1[0]), y: parseInt(p1[1]) },
        { x: parseInt(p2[0]), y: parseInt(p2[1]) }
      )
      // if vertical or horizontal line
      if (allowDiagonal || segment.isHorizontal || segment.isVertical) {
        segments.push(segment)
      }
    }
  }
  return segments
}
