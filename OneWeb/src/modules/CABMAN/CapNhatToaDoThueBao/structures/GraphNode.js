import { TimeSelect } from 'element-ui'

export default function GraphNode () {
  this.id = null
  this.id_polyline = null
  this.label = null
  this.style = null
  this.symbol = null
  this.tooltipText = null
  this.mouseover = false
  this.position = {
    lat: 0,
    lng: 0
  }
  this.point = null
  this.icon = null
  this.hovericon = null
  this.modified = false
  this.draggable = false
  this.isDiemuon = false
  this.visible = true
  this.icons = {
    0: 'CHUA_XAC_DINH.jpg',
    1: 'NHA_CAP.jpg',
    2: 'TU_CAP.jpg',
    3: 'HOP_CAP.jpg',
    4: 'MANG_XONG.jpg',
    5: 'SPLITTER.jpg',
    6: 'TU_QUANG_SPLITTER.jpg',
    7: 'KET_CUOI_AO.jpg',
    8: 'KHAY_ODF.jpg',
    9: 'TRAM_THIET_BI.jpg',
    10: 'HOST.jpg',
    11: 'MSAN_RSU_BTS.jpg',
    12: 'MSAN_RSU.jpg',
    13: 'BTS.jpg',
    14: 'CAP.jpg',
    15: 'STRIPE.jpg'
  }
  this.setIcon = style => {
    if (style === 100) {
      const icondiemuon = {
        path: 'M-20,0a20,20 0 1,0 40,0a20,20 0 1,0 -40,0',
        fillColor: '#ffa500',
        fillOpacity: 0.6,
        anchor: new window.google.maps.Point(0, 0),
        strokeWeight: 0,
        scale: 0.4
      }

      this.icon = icondiemuon
      return
    }
    if (style === 101) {
      const ballon = {
        fillColor: '#ffa500',
        fillOpacity: 0.6,
        anchor: new window.google.maps.Point(0, 0),
        strokeWeight: 0,
        scale: 0.4
      }

      this.icon = ballon
      return
    }
    let iconname = null
    try {
      iconname = this.icons[style]
    } catch (error) {
      iconname = 'CHUA_XAC_DINH.jpg'
    }
    if(iconname==null || iconname== undefined)
    iconname = 'CHUA_XAC_DINH.jpg'
    let icon = {
      url: require('../../../../../static/icons/icon100x100/' + iconname),
      size: new window.google.maps.Size(30, 30),
      scaledSize: new window.google.maps.Size(30, 30),
      labelOrigin: { x: 16, y: 40 },
      anchor: new window.google.maps.Point(15, 15)
    }
    let hovericon = {
      url: require('../../../../../static/icons/icon100x100/' + iconname.substr(0, iconname.lastIndexOf('.jpg')) + '_sel.jpg'),
      size: new window.google.maps.Size(30, 30),
      scaledSize: new window.google.maps.Size(30, 30),
      labelOrigin: { x: 16, y: 40 },
      anchor: new window.google.maps.Point(15, 15)
    }
    this.icon = icon
    this.hovericon = hovericon
  }
  this.getLabel = () => {
    return ({'text': this.symbol, 'color': 'black', 'fontSize': '12px', 'fontFamily': 'Arial', 'fontWeight': 'bold'})
  }
  this.getIcon = () => {
    if (this.mouseover) {
      return this.hovericon
    } else {
      return this.icon
    }
  }
  this.getID = () => {
    return this.id
  }
  this.getLabel = () => {
    return this.label
  }
  this.getStyle = () => {
    return this.style
  }
  this.setPosition = position => {
    try {
      if (typeof position === 'string') {
        let lat = position.split(',')[0]
        let lng = position.split(',')[1]
        this.position.lat = parseFloat(lat)
        this.position.lng = parseFloat(lng)
      }
      if (typeof position === 'object') {
        this.position.lat = position.lat
        this.position.lng = position.lng
      }
      return true
    } catch (error) {
      return false
    }
  }
  this.getPosition = () => {
    return this.position
  }
  this.setSymbol = symbol => {
    try {
      this.symbol = symbol
      return true
    } catch (error) {
      return false
    }
  }
  this.setIsDiemuon = value => {
    try {
      this.isDiemuon = value
      return true
    } catch (error) {
      return false
    }
  }
}
