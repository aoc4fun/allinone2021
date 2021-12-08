import { fileToSegments } from './fileToSegments'
import { Grid } from './Grid'
import { getSegmentsMaxValues } from './getSegmentsMaxValues'

export function part2() {
  // get from file
  const segments = fileToSegments('assets/input.txt', true)

  // get max X max Y
  const { maxX, maxY } = getSegmentsMaxValues(segments)

  // build grid
  // quick fix by incrementing size (+2), but there's something wrong
  const grid = new Grid(maxX + 2, maxY + 2)

  // add segments
  for (const segment of segments) {
    grid.addSegment(segment)
  }
  console.log('--- PART 2 ---')
  console.log('maxX', maxX)
  console.log('maxY', maxY)
  console.log('dangerousZoneArea', grid.dangerousZoneArea)
  // console.log('grid', grid.toString())
}
