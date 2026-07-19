export default function GraphPolyline () {
  this.id = null
  this.label = null
  this.source = null
  this.dest = null
  this.style = null
  this.path = []
  this.length_met = null
  this.length_km = null
  this.modified = false
  this.updatePath = false
  this.mouseover = false
  this.polylineOptions = () => {
    var style = this.style
    if (style === null) style = '0-0-0-1'
    var st = style.split('-')
    var opt = {strokeOpacity: 0, geodesic: true}

    var lineSymbol = {
      path: 'M 0,-1 0,1',
      strokeOpacity: 1,
      strokeWeight: 2,
      scale: 3
    }
    var doubleLine = {
      path: 'M 0.5,-1 0.5,1 M -0.5,-1 -0.5,1',
      strokeOpacity: 1,
      strokeWeight: 1,
      scale: 3
    }
    var color = ['#a52a2a', 'blue', '#000000']
    var icons = [
      [{
        icon: lineSymbol,
        offset: '0%',
        repeat: '6px'
      }],
      [{
        icon: lineSymbol,
        offset: '50%',
        repeat: '15px'
      }],
      [{
        icon: lineSymbol,
        offset: '80%',
        repeat: '25px'
      }],
      [{
        icon: lineSymbol,
        offset: '50%',
        repeat: '25px'
      }],
      [{
        icon: lineSymbol,
        offset: '5%',
        repeat: '10px'
      }]
    ]
    switch (st[0]) {
      case '0':
        opt = {
          ...opt,
          strokeWeight: 4
        }
        break
      case '1':
        opt = {
          ...opt,
          strokeWeight: 6
        }
        break
      default:
        opt = {
          ...opt,
          strokeWeight: 2
        }
    }
    switch (st[1]) {
      case '0':
        opt = {
          ...opt,
          strokeColor: this.mouseover ? '#FF0000' : color[0]
        }
        break
      case '1':
        opt = {
          ...opt,
          strokeColor: this.mouseover ? '#FF0000' : color[1]
        }
        break
      default:
        opt = {
          ...opt,
          strokeColor: this.mouseover ? '#FF0000' : color[2]
        }
    }
    switch (st[2]) {
      case '2': // Kéo cống        [▬▬  ▬▬  ▬▬  ▬▬  ▬▬  ▬]
        opt = {
          ...opt,
          icons: icons[1]
        }
        break
      case '3': // Kéo treo        [▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬]
        opt = {
          ...opt,
          icons: icons[0]
        }
        break
      case '4': // Chôn        [▬ ▬ ▬ ▬ ▬ ▬ ▬ ▬ ▬ ▬ ▬]
        opt = {
          ...opt,
          icons: icons[3]
        }
        break
      case '5': // Đề xuất kéo mới [▬▬▬ ▬ ▬▬▬ ▬ ▬▬▬ ▬ ▬▬▬]
        opt = {
          ...opt,
          icons: icons[2]
        }
        break
      default: // Không xác định   [• • • • • • • • • • •]
        opt = {
          ...opt,
          icons: icons[2]
        }
    }
    if (st[3] === '1') {
      opt.strokeWeight = opt.strokeWeight / 2
      opt = {
        ...opt,
        strokeOpacity: 0.5
      }
    }
    return opt
  }
}
