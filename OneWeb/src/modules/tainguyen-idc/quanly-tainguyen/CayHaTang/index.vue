<template src="./index.html"></template>

<script>
import Enum from '../Enum'
export default {
  props: {
    loadDataWhenCreate: false
  },
  data: function() {
    return {
      dsNodes: [],
      dsNodeFiltered: [],
      flagSearch: false
    }
  },
  created: async function() {
    if (this.loadDataWhenCreate == true) {
      this.$emit('beginCreate')
      try {
        await this.loadNodes()
      } finally {
        this.$emit('endCreate')
      }
    }
  },
  computed: {
    treeFields: function() {
      return {
        dataSource: this.dsNodesSorted,
        id: 'id',
        text: 'text',
        iconCss: 'icon',
        parentID: 'parentID',
        hasChildren: 'hasChildren'
      }
    },
    dsNodesSorted: function() {
      if (this.flagSearch) {
        return this.dsNodeFiltered.sort((a, b) => (a.text < b.text ? -1 : 1))
      } else {
        return this.dsNodes.sort((a, b) => (a.text < b.text ? -1 : 1))
      }
    }
  },
  methods: {
    tree_nodeExpanding: async function(args) {
      console.log('nodeExpanding', args)
      this.$emit('nodeExpanding', args)
    },
    tree_nodeSelected: function(args, isExpand = false) {
      const selectedNode = this.treeFields.dataSource.find((x) => x.id === args.nodeData.id)

      this.$emit('nodeSelected', selectedNode)

      if (isExpand && selectedNode && this.$refs.tree) {
        this.$nextTick(() => {
          const tree = this.$refs.tree
          tree.expandAll([selectedNode.parentID])
          setTimeout(() => {
            tree.ej2Instances.selectedNodes = [selectedNode.id]
          }, 100)
        })
      }
    },
    textBoxSearch_enter: function(args) {
      this.flagSearch = true
      this.dsNodeFiltered = this.getNodesFiltered()
      this.$emit('nodeFiltered', args)
    },
    btnCancel_click: function(args) {
      this.$refs.textBoxSearch.value = ''
      this.flagSearch = false
      this.dsNodeFiltered = []
      this.$emit('nodeFiltered', args)
    },
    btnReload_click: async function(args) {
      this.$root.showLoading(true)
      try {
        this.$refs.textBoxSearch.value = ''
        await this.loadNodes()
        this.$emit('reloadData', args)
      } finally {
        this.$root.showLoading(false)
      }
    },
    getNodesFiltered: function() {
      let textFiltered = this.$refs.textBoxSearch.value
      let result = []
      if (this.dsNodes != null) {
        let items = this.dsNodes.filter((item) =>
          item.text
            .toString()
            .toLowerCase()
            .trim()
            .includes(textFiltered.toString().toLowerCase().trim())
        )
        if (!(items == null || items.length == 0)) {
          for (let i = 0; i < items.length; i++) {
            const item = items[i]
            let ck = result.findIndex((x) => x.id == item.id && x.parentID == item.parentID)
            if (ck < 0) {
              result.push(item)
              if (item.parentID != null) {
                let ps = this.getParents(item.parentID)
                if (!(ps == null || ps.length == 0)) {
                  for (let j = 0; j < ps.length; j++) {
                    ck = result.findIndex((x) => x.id == ps[j].id && x.parentID == ps[j].parentID)
                    if (ck < 0) result.push(ps[j])
                  }
                }
              }
            }
          }
        }
      }
      return result
    },
    loadNodes: async function() {
      this.dsNodes = await this.getDsNodeSoDo()
      this.$emit('dataLoaded', this.dsNodes)
    },
    getDsNodeSoDo: async function() {
      let result = []
      let items = await this.getCaySoDoIDC()
      if (!(items == null || items.length == 0)) {
        items.forEach((item) => {
          let node = this.createNode(
            item.ITEM_ID,
            item.ITEM_TYPE,
            item.NAME,
            item.PARENT_ID,
            item.PARENT_TYPE,
            items.findIndex((x) => x.PARENT_ID == item.ITEM_ID && x.PARENT_TYPE == item.ITEM_TYPE) >= 0
          )
          result.push(node)
        })
      }
      return result
    },
    getNodeID: function(type, id) {
      let rs = null
      if (!(this.checkIsEmpty(type) || this.checkIsEmpty(id))) {
        rs = type.toString().toUpperCase() + Enum.SEPARATOR + id.toString()
      }
      return rs
    },
    createNode: function(id, type, text, parentID, parentType, hasChildren) {
      let attrs = {
        id: id,
        type: type,
        text: text,
        parentID: parentID,
        parentType: parentType
      }
      return {
        id: this.getNodeID(type, id),
        text: this.checkIsNull(text) ? '(null)' : text,
        parentID: this.getNodeID(parentType, parentID),
        icon: 'icon-' + type.toString().toLowerCase(),
        hasChildren: hasChildren,
        attributes: attrs
      }
    },
    getNode: function(nodeID) {
      return this.dsNodes.find((x) => x.id == nodeID)
    },
    getChilds: function(nodeParentID) {
      let result = []
      let childs = this.dsNodes.filter((x) => x.parentID == nodeParentID)
      if (!this.checkIsNull(childs)) {
        for (let i = 0; i < childs.length; i++) {
          const child = childs[i]
          result.push(child)
          let nextChilds = this.getChilds(child.id)
          if (!this.checkIsNull(nextChilds)) {
            for (let j = 0; j < nextChilds.length; j++) {
              result.push(nextChilds[j])
            }
          }
        }
      }
      return result
    },
    getParents: function(nodeParentID) {
      let rs = []
      let parent = this.dsNodes.find((x) => x.id == nodeParentID)
      if (parent != null) {
        rs.push(parent)
        if (parent.parentID != null) {
          let ps = this.getParents(parent.parentID)
          if (!(ps == null || ps.length == 0)) {
            for (let i = 0; i < ps.length; i++) {
              rs.push(ps[i])
            }
          }
        }
      }
      return rs
    },
    getDonViIDFromNode: function(nodeID) {
      let donViID = null
      let node = this.getNode(nodeID)
      if (node != null) {
        if (node.attributes.type == Enum.HATANG.DONVI && node.attributes.level == 3) {
          donViID = node.attributes.id
        } else {
          if (!this.checkIsEmpty(node.parentID)) {
            let parents = this.getParents(node.parentID)
            let parent = parents.filter((x) => x.attributes.type == Enum.HATANG.DONVI && x.attributes.level == 3)
            if (parent != null) {
              donViID = parent.attributes.id
            }
          }
        }
      }
      return donViID
    },
    getDonViIDFromNodeItem: function(type, id) {
      return this.getDonViIDFromNode(this.getNode(this.getNodeID(type, id)))
    },
    getCaySoDoIDC: async function() {
      let rs = []
      try {
        let result = await this.$root.context.post('/web-ecms/tainguyen-idc/khong-gian/get-cay-sodo-idc')
        rs = result.data
      } catch (error) {
        console.error(error)
      }
      return rs
    },
    checkIsNull: function(value) {
      return value == undefined || value == null
    },
    checkIsEmpty: function(value) {
      let rs = this.checkIsNull(value)
      if (!rs) {
        rs = value.toString().trim() == ''
      }
      return rs
    },
    refreshTree: async function(nodeID) {
      await this.loadNodes()
      if (!this.checkIsEmpty(nodeID)) {
        const node = this.getNode(nodeID)
        console.log('refreshTree node:', node)
        if (node) {
          await this.$nextTick()
          
          const parents = node.parentID ? this.getParents(node.parentID) : []
          const parentIds = parents.map(p => p.id).reverse() // Đảo ngược để expand từ trên xuống
          
          if (parentIds.length > 0 && this.$refs.tree) {
            this.$refs.tree.expandAll(parentIds)
          }
          
          setTimeout(() => {
            if (this.$refs.tree) {
              this.$refs.tree.ej2Instances.selectedNodes = [node.id]
              this.tree_nodeSelected({ nodeData: node }, false)
            }
          }, 300)
        }
      }
    }
  }
}
</script>
<style>
.cay-hatang {
}

.page .e-tree-plus.cay-hatang .e-treeview .e-list-item .e-list-text::before {
  display: none;
}

.e-tree-plus.cay-hatang .e-treeview .e-list-text {
  line-height: 26px;
}
</style>
