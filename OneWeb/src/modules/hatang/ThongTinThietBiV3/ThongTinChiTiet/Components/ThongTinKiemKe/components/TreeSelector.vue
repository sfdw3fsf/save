<template>
  <div class="tree-selector" ref="container">
    <div class="input-more-button -right">
      <button class="btn" :disabled="disabled || (requireParentId && !parentId)" @click.stop="toggleDropdown">
        <span class="-ap icon-more_horiz"></span>
      </button>
      <input
        type="text"
        class="form-control"
        :value="displayText"
        readonly
        :placeholder="getPlaceholder"
        @click.stop="toggleDropdown"
      />
    </div>
    <div v-if="showDropdown" class="tree-dropdown" ref="dropdown" :style="dropdownStyle" @click.stop>
      <div class="tree-dropdown-header">
        <span>{{ title }}</span>
        <button class="btn-close" @click="closeDropdown">
          <span class="icon ui-1_circle-remove nc-icon-glyph"></span>
        </button>
      </div>
      <div class="tree-dropdown-body">
        <div v-if="requireParentId && !parentId" class="alert alert-warning">
          {{ parentRequiredMessage }}
        </div>
        <div v-else class="e-tree-plus">
          <ejs-treeview
            ref="tree"
            :fields="treeFields"
            :allowDragAndDrop="false"
            :allowMultiSelection="false"
            :showCheckBox="false"
            @nodeSelected="tree_nodeSelected"
            @nodeClicked="tree_nodeClicked"
          >
          </ejs-treeview>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { TreeView } from '@syncfusion/ej2-navigations'

export default {
  name: 'TreeSelector',
  props: {
    value: {
      type: [Number, String],
      default: null
    },
    dataSource: {
      type: Array,
      default: () => []
    },
    parentId: {
      type: [Number, String],
      default: null
    },
    requireParentId: {
      type: Boolean,
      default: false
    },
    textField: {
      type: String,
      default: 'TEN'
    },
    valueField: {
      type: String,
      default: 'ID'
    },
    parentField: {
      type: String,
      default: null
    },
    disabled: {
      type: Boolean,
      default: false
    },
    placeholder: {
      type: String,
      default: '-- Chọn --'
    },
    parentRequiredMessage: {
      type: String,
      default: 'Vui lòng chọn đơn vị quản lý trước'
    },
    title: {
      type: String,
      default: 'Chọn'
    },
    type: {
      type: String,
      default: 'donvi', // 'donvi' or 'phongban'
      validator: (value) => ['donvi', 'phongban'].includes(value)
    }
  },
  data() {
    return {
      showDropdown: false,
      treeFields: {
        dataSource: [],
        id: 'id',
        text: 'name',
        child: 'children',
        parentID: 'parentId',
        hasChildren: 'hasChild',
        expanded: 'expanded'
      },
      treeData: [],
      localSelected: null,
      dropdownStyle: {},
      clickTimer: null,
      lastClickedNode: null,
      isSelectingNode: false, // Flag to prevent auto-trigger when selecting node programmatically
      isOpeningDropdown: false // Flag to prevent click outside from closing immediately
    }
  },
  computed: {
    displayText() {
      if (!this.value || !this.dataSource || this.dataSource.length === 0) return ''
      const item = this.dataSource.find((x) => x[this.valueField] == this.value)
      return item ? item[this.textField] : ''
    },
    getPlaceholder() {
      if (this.requireParentId && !this.parentId) {
        return this.parentRequiredMessage
      }
      return this.placeholder
    },
    filteredDataSource() {
      if (!this.dataSource || this.dataSource.length === 0) return []

      // If requireParentId and has parentId, filter by parent
      if (this.requireParentId && this.parentId) {
        const filtered = this.dataSource.filter((item) => {
          return item.DONVI_CHA_ID == this.parentId
        })

        if (filtered.length === 0 && this.dataSource.length > 0) {
          return this.dataSource
        }

        return filtered
      }

      return this.dataSource
    },
    getParentFieldName() {
      if (this.parentField) return this.parentField

      return 'DONVI_CHA_ID'
    }
  },
  watch: {
    filteredDataSource: {
      handler(newVal) {
        if (newVal && newVal.length > 0) {
          this.transformDataToTree(newVal)
        } else {
          this.treeData = []
          this.treeFields = {
            ...this.treeFields,
            dataSource: []
          }
        }
      },
      immediate: true,
      deep: true
    },
    parentId: {
      handler(newVal, oldVal) {
        // Reset selection when parentId changes
        if (oldVal && newVal !== oldVal) {
          if (this.value) {
            this.$emit('input', null)
          }
        }
      }
    },
    value: {
      immediate: true,
      handler(newVal) {
        this.localSelected = newVal
      }
    },
    showDropdown(newVal) {
      if (newVal) {
        this.isOpeningDropdown = true
        this.$nextTick(() => {
          this.updateDropdownPosition()
          this.selectNodeInTree(this.value)
          // Reset flag after a short delay to allow dropdown to fully render
          setTimeout(() => {
            this.isOpeningDropdown = false
          }, 200)
        })
      } else {
        this.isOpeningDropdown = false
      }
    }
  },
  mounted() {
    // Close dropdown when clicking outside
    document.addEventListener('click', this.handleClickOutside)
  },
  beforeDestroy() {
    document.removeEventListener('click', this.handleClickOutside)
    // Clear timer
    if (this.clickTimer) {
      clearTimeout(this.clickTimer)
    }
  },
  methods: {
    transformDataToTree(data) {
      // Check if data is already in tree format
      if (data.length > 0 && data[0].children !== undefined) {
        this.treeData = this.mapTreeData(data)
        this.treeFields = {
          ...this.treeFields,
          dataSource: this.treeData
        }
        return
      }

      // Transform flat data to tree structure
      const nodeMap = new Map()
      const rootNodes = []
      const parentFieldName = this.getParentFieldName

      // First pass: create all nodes
      data.forEach((item) => {
        const nodeId = item[this.valueField]

        // let parentId = null
        let parentId = item[parentFieldName] || null
        // if (this.type === 'phongban') {
        //   parentId = item.PARENT_ID || item.PHONGBAN_CHA_ID || item.PHONGBAN_QLY_CHA_ID || item.CHA_ID || null
        // } else {
        // }

        nodeMap.set(nodeId, {
          id: nodeId,
          name: item[this.textField],
          parentId: parentId,
          hasChild: false,
          expanded: false,
          children: [],
          originalData: item
        })
      })

      // Second pass: build tree structure
      nodeMap.forEach((node, nodeId) => {
        if (node.parentId && nodeMap.has(node.parentId)) {
          const parent = nodeMap.get(node.parentId)
          parent.children.push(node)
          parent.hasChild = true
        } else {
          rootNodes.push(node)
        }
      })

      this.treeData = rootNodes
      this.treeFields = {
        ...this.treeFields,
        dataSource: rootNodes
      }
    },
    mapTreeData(data) {
      return data.map((item) => {
        // let parentId = null
        let parentId = item[parentFieldName] || null
        // if (this.type === 'phongban') {
        //   parentId = item.PARENT_ID || item.PHONGBAN_CHA_ID || null
        // } else {
        //   const parentFieldName = this.getParentFieldName
        // }

        return {
          id: item[this.valueField],
          name: item[this.textField],
          parentId: parentId,
          hasChild: item.children && item.children.length > 0,
          expanded: false,
          children: item.children ? this.mapTreeData(item.children) : [],
          originalData: item
        }
      })
    },
    toggleDropdown() {
      if (this.disabled || (this.requireParentId && !this.parentId)) return
      this.showDropdown = !this.showDropdown
    },
    closeDropdown() {
      this.showDropdown = false
    },
    handleClickOutside(event) {
      if (this.isOpeningDropdown) return

      const container = this.$refs.container
      const dropdown = this.$refs.dropdown

      if (container && dropdown) {
        if (!container.contains(event.target) && !dropdown.contains(event.target)) {
          this.closeDropdown()
        }
      } else if (container && !container.contains(event.target)) {
        this.closeDropdown()
      }
    },
    updateDropdownPosition() {
      if (!this.$refs.container) return

      const container = this.$refs.container
      const rect = container.getBoundingClientRect()

      // Use fixed position relative to viewport
      this.dropdownStyle = {
        position: 'fixed',
        top: `${rect.bottom + 5}px`,
        left: `${rect.left}px`,
        width: `${rect.width}px`,
        minWidth: '300px',
        maxWidth: '400px',
        zIndex: 10000
      }
    },
    tree_nodeSelected(args) {
      if (this.isSelectingNode) return

      if (args && args.nodeData) {
        this.localSelected = args.nodeData.id
        // if node is selected
        this.confirmSelection()
      }
    },
    tree_nodeClicked (args) { },
    selectNodeInTree(nodeId) {
      if (!nodeId || !this.$refs.tree || !this.$refs.tree.ej2Instances) return

      try {
        this.isSelectingNode = true // Set flag to prevent auto-trigger
        const treeObj = this.$refs.tree.ej2Instances
        const node = this.findNode(this.treeData, nodeId)
        if (node) {
          const parentIds = this.getAllParentIds(node)
          parentIds.forEach((parentId) => {
            const parentNode = this.findNode(this.treeData, parentId)
            if (parentNode) {
              parentNode.expanded = true
            }
          })

          this.treeFields = {
            ...this.treeFields,
            dataSource: JSON.parse(JSON.stringify(this.treeData))
          }

          setTimeout(() => {
            treeObj.selectedNodes = [nodeId]
            // Reset flag after selection is complete
            setTimeout(() => {
              this.isSelectingNode = false
            }, 150)
          }, 100)
        } else {
          this.isSelectingNode = false
        }
      } catch (error) {
        console.error('Error selecting node in tree:', error)
        this.isSelectingNode = false
      }
    },
    findNode(nodes, id) {
      for (const node of nodes) {
        if (node.id == id) return node
        if (node.children && node.children.length > 0) {
          const found = this.findNode(node.children, id)
          if (found) return found
        }
      }
      return null
    },
    getAllParentIds(node) {
      const parentIds = []
      const processedIds = new Set()

      const collectParentIds = (currentNode) => {
        if (!currentNode || processedIds.has(currentNode.id)) {
          return
        }

        processedIds.add(currentNode.id)

        if (currentNode.parentId) {
          if (!parentIds.includes(currentNode.parentId)) {
            parentIds.push(currentNode.parentId)
          }

          const parentNode = this.findNode(this.treeData, currentNode.parentId)
          if (parentNode) {
            parentNode.expanded = true
            collectParentIds(parentNode)
          }
        }
      }

      collectParentIds(node)
      return parentIds
    },
    confirmSelection() {
      // Use nextTick to ensure the emit happens after current execution context
      this.$nextTick(() => {
        this.$emit('input', this.localSelected)
        this.closeDropdown()
      })
    }
  }
}
</script>

<style scoped>
.tree-selector {
  position: relative;
  width: 100%;
}

.tree-dropdown {
  position: fixed;
  z-index: 10000;
  background: white;
  border: 1px solid #ddd;
  border-radius: 4px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  max-height: 400px;
  overflow: hidden;
  display: flex;
  flex-direction: column;
}

.tree-dropdown-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 8px 12px;
  border-bottom: 1px solid #eee;
  background-color: #f8f9fa;
  font-weight: 500;
}

.btn-close {
  background: none;
  border: none;
  cursor: pointer;
  padding: 0;
  display: flex;
  align-items: center;
  color: #666;
}

.btn-close:hover {
  color: #333;
}

.tree-dropdown-body {
  padding: 10px;
  overflow-y: auto;
  max-height: 350px;
}

.e-tree-plus {
  width: 100%;
}

.e-treeview {
  width: 100%;
}

.alert {
  padding: 10px;
  margin: 0;
  border-radius: 4px;
}

.alert-warning {
  background-color: #fff3cd;
  border: 1px solid #ffc107;
  color: #856404;
}
</style>
