import { Segment } from './Segment'

export function getSegmentsMaxValues(segments: Segment[]) {
  // get max X max Y
  const maxX = segments.reduce((previousValue, currentvalue) =>
    previousValue.maxX > currentvalue.maxX ? previousValue : currentvalue
  ).maxX

  const maxY = segments.reduce((previousValue, currentvalue) =>
    previousValue.maxY > currentvalue.maxY ? previousValue : currentvalue
  ).maxY

  return { maxX, maxY }
}
