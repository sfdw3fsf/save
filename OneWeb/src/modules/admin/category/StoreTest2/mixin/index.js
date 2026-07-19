import { customPaging } from '../parts/paging/customPaging'

export default {
  data: function () {
    return {
      pageSettings: { pageSize: 10, pageCount: 5 },
      selectionOptions: {
        enableToggle: false,
        type: 'Multiple'
      },
      pagerTemplate: customPaging,
      filterOptions: {
        operator: 'contains'
      }
    }
  },
  methods: {
    buildTreeFromList: function (originalList, fieldName, fieldId, fieldParentId) {
      // remove duplicate element
      const list = []
      for (let i = 0; i < originalList.length; i++) {
        const item = originalList[i]
        if (!list.find(l => l.id.toString() === item[fieldId].toString())) {
          list.push({
            name: (item[fieldName] === undefined || item[fieldName] === null) ? '' : item[fieldName].toString(),
            id: (item[fieldId] === undefined || item[fieldId] === null) ? '' : item[fieldId].toString(),
            pid: (item[fieldParentId] === undefined || item[fieldParentId] === null) ? '' : item[fieldParentId].toString(),
            children: [],
            data: item
          })
        }
      }

      const map = {}
      let node
      const roots = []

      for (let i = 0; i < list.length; i += 1) {
        map[list[i].id] = i // initialize the map
        // list[i].children = [] // initialize the children
      }

      for (let i = 0; i < list.length; i += 1) {
        node = list[i]
        if (node.pid && node.pid !== '-1' && list[map[node.pid]]) {
          // if you have dangling branches check that map[node.parentId] exists
          if (!list[map[node.pid]].children) {
            list[map[node.pid]].children = []
          }

          list[map[node.pid]].children.push(node)
        } else {
          roots.push(node)
        }
      }
      return roots
    },
    getNodeById: function (element, id) {
      if (element.id.toString() === id.toString()) {
        return element
      } else if (element.children && element.children.length > 0) {
        let i
        let result = null
        for (i = 0; result == null && i < element.children.length; i++) {
          result = this.getNodeById(element.children[i], id)
          if (result) { break }
        }
        return result
      }
    },
    getParents (root, node, parents) {
      if (!node.pid) { return }
      const parent = this.getNodeById(root, node.pid)
      if (parent) {
        parents.push(parent)
        this.getParents(root, parent, parents)
      }
    },
    getChilren (node, children) {
      children.push(node)
      for (let i = 0; i < node.children.length; i++) {
        this.getChilren(node.children[i], children)
      }
    },
    upperCaseKey: function (objArray) {
      const modifiedData = []
      for (var i = 0; i < objArray.length; i++) {
        var a = objArray[i]
        const b = {}
        for (var key in a) {
          if (a.hasOwnProperty(key)) {
            b[key.toUpperCase()] = a[key]
          }
        }
        modifiedData.push(b)
      }

      return modifiedData
    },
    expandNode (root, id) {
      const node = this.getNodeById(root, id)
      if (node) {
        node.isOpen = true
      }
      if (node.pid) {
        this.expandNode(root, node.pid)
      }
    },
    getParentTreeSplit: function (root, node) {
      if (!node.pid) return node

      const parent = this.getNodeById(root, node.pid)
      if (parent) {
        parent.children = [node]
        return this.getParentTreeSplit(root, parent)
      } else return node
    }
  }
}
