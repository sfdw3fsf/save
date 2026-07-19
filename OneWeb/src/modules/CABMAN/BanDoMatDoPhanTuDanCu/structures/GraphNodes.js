import GraphNode from '../structures/GraphNode'
export default function GraphNodes () {
  this.nodes = []
  this.location = {
    lat: 0,
    lng: 0
  }
  this.isDuplicateKey = (id, arr) => {
    let isDupl = false
    for (let i = 0; i < arr.length; i += 1) {
      const obj = arr[i]
      if (obj.id === id) {
        isDupl = true
        break
      }
    }
    return isDupl
  }

  this.getUniqueId = (prefix) => {
    var d = new Date().getTime()
    d += (parseInt(Math.random() * 100)).toString()
    if (undefined === prefix) {
      prefix = 'uid-'
    }
    d = prefix + d
    return d
  }

  this.addNodeDiemuon = (id, position) => {
    if (!this.isDuplicateKey(id, this.nodes)) {
      var n = new GraphNode(id, 'x', 100, 'x', 'x', true)
      n.setPosition(position)
      n.setIcon(100)
      n.isDiemuon = true
      n.visible = false
      n.draggable = true
      this.nodes.push(n)
      console.log('addNodeDiemuon', n)
      return n
    } else return null
  }

  this.addNode = node => {
    if (Object.keys(node).length === 0 && node.constructor === Object) {
      return null
    }
    if (node === null || node === undefined) return null
    if (node.id === null || node.position === null) return null
    if (!this.isDuplicateKey(node.id, this.nodes)) {
      var n = new GraphNode(
        node.id,
        node.label,
        node.style,
        node.symbol,
        node.tooltipText,
        node.visible
      )
      n.setPosition(node.position)
      n.setIcon(node.style)
      this.nodes.push(n)
      return n
    } else {
      return null
    }
  }
  // this.addNodeDiemUon = node => {
  //   if (Object.keys(node).length === 0 && node.constructor === Object) {
  //     return null
  //   }
  //   if (node === null || node === 'undefined') return null
  //   if (node.id === null || node.position === null) return null
  //   if (!this.isDuplicateKey(node.id, this.nodes)) {
  //     var n = new GraphNode(
  //       node.id,
  //       node.label,
  //       node.style,
  //       node.symbol,
  //       node.tooltipText,
  //       node.visible
  //     )
  //     n.setPosition(node.position)
  //     n.setIcon(node.style)
  //     n.draggable = true
  //     n.setIsDiemuon(true)
  //     this.nodes.push(n)
  //     return n
  //   } else {
  //     return null
  //   }
  // }
  this.delAllNode = () => {
    this.nodes.length = 0
    this.nodes = []
  }
  this.delNode = node => {
    if (Object.keys(node).length === 0 && node.constructor === Object) {
      return false
    }
    if (node === null || node === 'undefined') return false
    if (node.id === null) return false
    this.nodes = this.delElement(this.nodes, node.id)
    return true
  }
  this.getAllNode = () => {
    return this.nodes.filter(e => e.visible === true)
  }
  this.getNodes = () => {
    return this.nodes.filter(e => e.visible === true)
  }
  this.getNode = id => {
    return this.nodes.filter(e => e.id === id)
  }
  this.delElement = (arr, id) => {
    let idx = arr.findIndex(o => o.id === id)
    if (idx > -1) arr.splice(idx, 1)
    return arr
  }
  this.containsNode = node => {
    return this.isDuplicateKey(node.id, this.nodes)
  }
  this.setDraggable = (id, draggable) => {
    const objIndex = this.nodes.findIndex(obj => obj.id === id)
    if (objIndex >= 0) {
      this.nodes[objIndex].draggable = draggable
    }
  }
  this.setLocationEx = position => {
    if (position === null || position === 'undefined') {
      if (this.nodes.length > 0) {
        this.location = {
          lat: this.nodes[0].position.lat,
          lng: this.nodes[0].position.lng
        }
      }
    } else {
      this.location = {
        lat: position.lat,
        lng: position.lng
      }
    }
  }
  this.updatePosition = (node, position) => {
    const objIndex = this.nodes.findIndex(obj => obj.id === node.id)
    if (objIndex >= 0) {
      this.nodes[objIndex].position.lat = position.lat
      this.nodes[objIndex].position.lng = position.lng
      this.nodes[objIndex].modified = true
    }
  }
}
