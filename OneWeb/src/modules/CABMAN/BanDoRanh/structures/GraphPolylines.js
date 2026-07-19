import GraphPolyline from './GraphPolyline'
export default function GraphPolylines () {
  this.polylines = []

  this.addPolyline = polyline => {
    this.polylines.push(polyline)
  }

  this.getPolylines = () => {
    return this.polylines
  }
}
