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
    // buildRootNode: function (originalArray, fieldName, fieldValue, fieldParent) {
    //   this.$root.showLoading(true)
    //   const root = {
    //     name: 'Root',
    //     value: '0',
    //     parentValue: null,
    //     children: []
    //   }
    //   if (originalArray && originalArray.length > 0) {
    //     for (let i = 0; i < originalArray.length; i++) {
    //       this.buildNode(root, originalArray[i], fieldName, fieldValue, fieldParent)
    //     }
    //   }

    //   this.$root.showLoading(false)
    //   return root
    // },
    // buildNode: function (root, currentElement, fieldName, fieldValue, fieldParent) {
    //   const node = {
    //     name: currentElement[fieldName],
    //     value: currentElement[fieldValue].toString(),
    //     parentValue: (currentElement[fieldParent] === undefined || currentElement[fieldParent] === null)
    //       ? 0 : currentElement[fieldParent].toString(),
    //     children: []
    //   }

    //   if (this.isExistNode(root, node)) {
    //     return
    //   }

    //   if (!currentElement[fieldParent]) {
    //     root.children.push(node)
    //     return
    //   }

    //   const parentNode = this.getNodeByKey(root, currentElement[fieldParent])
    //   if (parentNode) {
    //     parentNode.children.push(node)
    //     return
    //   } else {
    //     const parentDepartment = this.getStores.find(i => i[fieldParent])
    //     if (parentDepartment) {
    //       this.buildNode(root, parentDepartment)
    //       return
    //     }
    //   }

    //   root.children.push(node)
    // },
    // isExistNode: function (root, node) {
    //   for (let i = 0; i < root.children.length; i++) {
    //     if (node.value === root.children[i].value) {
    //       return true
    //     }

    //     if (this.isExistNode(root.children[i], node)) {
    //       return true
    //     }
    //   }

    //   return false
    // },
    // getNodeByKey: function (root, key) {
    //   for (let i = 0; i < root.children.length; i++) {
    //     if (root.children[i].value === key.toString()) {
    //       return root.children[i]
    //     }

    //     if (root.children[i].children.length > 0) {
    //       const node = this.getNodeByKey(root.children[i], key)
    //       if (node) {
    //         return node
    //       }
    //     }
    //   }

    //   return null
    // },
    // getTreeNodeByNode: function (root, node) {
    //   if (node.parentValue) {
    //     const parentNode = this.getNodeByKey(root, node.parentValue)
    //     parentNode.children = [node]

    //     return this.getTreeNodeByNode(root, parentNode)
    //   }

    //   return node
    // },
    // new build tree
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
            children: []
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
    // cutTreeFromList (rootNode, originalList, fieldName, fieldId, fieldParentId) {
    //   let nodes = []
    //   for (let i = 0; i < originalList.length; i++) {
    //     nodes.push({
    //       name: originalList[i][fieldName].toString(),
    //       id: originalList[i][fieldId].toString(),
    //       pid: (originalList[i][fieldParentId] === undefined || originalList[i][fieldParentId] === null) ? '' : originalList[i][fieldParentId].toString(),
    //       children: []
    //     })
    //   }

    //   // get children for each node
    //   const children = []
    //   for (let i = 0; i < nodes.length; i++) {
    //     nodes[i] = this.getNodeById(rootNode, nodes[i].id)
    //     this.getChilren(nodes[i], children)
    //   }

    //   // get parent list
    //   const parents = []
    //   for (let i = 0; i < nodes.length; i++) {
    //     this.getParents(rootNode, nodes[i], parents)
    //   }

    //   // remove if duplicate
    //   const elements = [...parents, ...children]
    //   const finalElements = []
    //   elements.forEach((item, i) => {
    //     const found = finalElements.some(el => el.id === item.id)
    //     if (!found) {
    //       finalElements.push(item)
    //     }
    //   })

    //   // build parent tree
    //   const parentTree = this.buildTreeFromList([...finalElements], 'name', 'id', 'pid')

    //   return parentTree
    // },
    getNodeById: function (element, id) {
      if (element.id.toString() === id.toString()) {
        return element
      } else if (element.children && element.children.length > 0) {
        let i
        let result = null
        for (i = 0; result == null && i < element.children.length; i++) {
          result = this.getNodeById(element.children[i], id)
        }
        return result
      }
      return null
    },
    getParents (root, node, parents) {
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
    }
  }
}
