import { Grid } from './Grid'
import { fileToSegments } from './fileToSegments'
import { getSegmentsMaxValues } from './getSegmentsMaxValues'

export function part1() {
  // get from file
  const segments = fileToSegments('assets/input.txt')

  // get max X max Y
  const { maxX, maxY } = getSegmentsMaxValues(segments)

  // build grid
  const grid = new Grid(maxX + 1, maxY + 1)

  // add segments
  for (const segment of segments) {
    grid.addSegment(segment)
  }

  console.log('--- PART 1 ---')
  console.log('maxX', maxX)
  console.log('maxX', maxY)
  // console.log('grid', grid.toString())
  console.log('grid.dangerousZoneArea', grid.dangerousZoneArea)
}
