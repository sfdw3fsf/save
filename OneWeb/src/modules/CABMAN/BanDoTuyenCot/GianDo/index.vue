<template src="./index.template.html"></template>

<script>
import { SymbolPalettePlugin, DiagramContextMenu, DiagramTools, SelectorConstraints, PrintAndExport, LineRouting, HierarchicalTree, DiagramConstraints, LineDistribution, Rect } from '@syncfusion/ej2-vue-diagrams'
//Diagram.Inject(LineRouting);
let constraints = DiagramConstraints.Default | DiagramConstraints.LineRouting
let diagramInstance
export default {
  components: {
    SymbolPalettePlugin
  },
  provide: {
    diagram: [DiagramContextMenu, PrintAndExport, HierarchicalTree, LineDistribution, LineRouting]
  },
  watch: {
    nodes: function(value) {
      // diagramInstance = this.$refs.diagram.ej2Instances;
      diagramInstance.clear()
      diagramInstance.refresh()
      //diagramInstance.nodes= value
      diagramInstance.fitToPage('Width')
      // this.ZoomReset()
    },
    connectors: function(value) {
      // let diagramInstance = this.$refs.diagram.ej2Instances;
      diagramInstance.clear()
      diagramInstance.refresh()
      diagramInstance.fitToPage('Width')
    },
    refresh: function(value) {
      // let diagramInstance = this.$refs.diagram.ej2Instances;
      if (diagramInstance.width == '100%') diagramInstance.width = '99.9%'
      else diagramInstance.width = '100%'
    },
    isTaoLienKet: function(value) {}
  },
  props: {
    nodes: {
      type: Array,
      default: () => []
    },
    connectors: {
      type: Array,
      default: () => []
    },
    refresh: {
      type: Boolean,
      default: false
    },
    isTaoLienKet: {
      type: Boolean,
      default: false
    }
  },
  mounted: function() {
    diagramInstance = this.$refs.diagram.ej2Instances
    diagramInstance.bringToFront()
    diagramInstance.dataBind()
    diagramInstance.fitToPage()
    diagramInstance.addEventListener('selectionChange', this.onSelectionChange)
  },
  data: function() {
    var self = this
    return {
      width: '100%',
      height: '1000px',
      isChonCot: false,
      constraints: constraints,
      tool: DiagramTools.SingleSelect | DiagramTools.ZoomPan, // DiagramTools.SingleSelect || DiagramTools.DrawOnce || DiagramTools.ZoomPan,
      scrollSetting: {
        horizontalOffset: 10,
        verticalOffset: 10
      },
      snapSettings: {
        horizontalGridlines: { lineColor: '#cdcdcd', dotIntervals: [0.95, 20.75], lineIntervals: [0.95, 20.75], snapIntervals: [10] },
        verticalGridlines: { lineColor: '#cdcdcd', dotIntervals: [0.95, 20.75], lineIntervals: [0.95, 20.75], snapIntervals: [10] },
        gridType: 'Dots'
        //constraints: SnapConstraints.ShowLines
      },
      commandManager: {
        commands: [
          {
            name: 'copy',
            canExecute: function() {
              return false
            }
          },
          {
            name: 'paste',
            canExecute: function() {
              return false
            }
          },
          {
            name: 'cut',
            canExecute: function() {
              return false
            }
          },
          {
            name: 'delete',
            canExecute: function() {
              return false
            }
          }
        ]
      },
      contextMenuSettings: {
        show: true,
        items: [
          {
            text: 'Liên kết tới cột tiếp theo',
            id: 'MENU_ITEM_LK_COT_TT',
            //ContextMenu can be visible based on the target in which you open the ContextMenu.
            target: '.e-diagramcontent',
            iconCss: 'icon text-main f20 one-taocap'
          },
          {
            text: 'Liên kết tới cột thuộc tuyến khác',
            id: 'MENU_ITEM_LK_COT_TK',
            target: '.e-diagramcontent',
            iconCss: 'icon text-main f20 one-taokn'
          },
          {
            id: 'MENU_ITEM_SEPARATOR_1',
            separator: true
          },
          {
            text: 'Xóa cột',
            id: 'MENU_ITEM_XOA_COT',
            target: '.e-diagramcontent',
            iconCss: 'icon text-main f20 one-trash'
          },
          {
            text: 'Hủy liên kết',
            id: 'MENU_ITEM_LK_HUY',
            target: '.e-diagramcontent',
            iconCss: 'icon text-main f16 nc-icon-glyph ui-1_circle-remove'
          },
          {
            separator: true
          },
          {
            text: 'Thuộc tính',
            id: 'MENU_ITEM_THUOC_TINH',
            target: '.e-diagramcontent',
            iconCss: 'icon text-main f20 -ap icon-info'
          },
          {
            text: 'Đảo chiều liên kết',
            id: 'MENU_ITEM_LK_DAOCHIEU',
            target: '.e-diagramcontent',
            iconCss: 'fa fa-random'
          }
        ],
        // Hides the default context menu items
        showCustomMenuOnly: true
      },
      selectedItems: {
        constraints: SelectorConstraints.None
      },
      contextMenuOpen: function(args) {
        if (this.selectedItems.nodes.length > 0) {
          args.hiddenItems.push('MENU_ITEM_LK_HUY')
          args.hiddenItems.push('MENU_ITEM_LK_DAOCHIEU')
        } else if (this.selectedItems.connectors.length > 0) {
          args.hiddenItems.push('MENU_ITEM_SEPARATOR_1')
          args.hiddenItems.push('MENU_ITEM_LK_COT_TT')
          args.hiddenItems.push('MENU_ITEM_LK_COT_TK')
          args.hiddenItems.push('MENU_ITEM_XOA_COT')
          args.hiddenItems.push('MENU_ITEM_THUOC_TINH')
        } else {
          args.cancel = true
        }
      },
      contextMenuClick: function(args) {
        let value = ''
        let obj = Object
        let cot_id_n
        if (this.selectedItems.nodes.length > 0) value = 'node: ' + this.selectedItems.nodes[0].id
        else if (this.selectedItems.connectors.length > 0) value = 'connector: ' + this.selectedItems.connectors[0].id
        //Chọn cột khác tuyến
        if (args.element.id == 'MENU_ITEM_LK_COT_TK') {
          cot_id_n = value.split('_')[1]
          obj = {
            cot_id: cot_id_n,
            type: 'MENU_ITEM_LK_COT_TK'
          }
          self.$emit('xacnhan', obj)
          this.vueInstance.$root.$bvModal.show('popupDsCot')
        }
        //Hủy liên kết
        if (args.element.id == 'MENU_ITEM_LK_HUY') {
          cot_id_n = value.split('_')[2]
          obj = {
            cot_id: cot_id_n,
            type: 'MENU_ITEM_LK_HUY'
          }
          self.$emit('xacnhan', obj)
          this.vueInstance.$root.$bvModal.show('modalHuy')
        }
        //Xóa cột
        if (args.element.id == 'MENU_ITEM_XOA_COT') {
          cot_id_n = value.split('_')[1]
          obj = {
            cot_id: cot_id_n,
            type: 'MENU_ITEM_XOA_COT'
          }
          self.$emit('xacnhan', obj)
          this.vueInstance.$root.$bvModal.show('modalXoa')
        }
        // Liên kết cột tiếp theo
        if (args.element.id == 'MENU_ITEM_LK_COT_TT') {
          cot_id_n = value.split('_')[1]
          obj = {
            cot_id: cot_id_n,
            type: 'MENU_ITEM_LK_COT_TT'
          }
          self.isChonCot = true
          self.$toast.info('Chọn cột cần liên kết tới\r\nNhấn ESC để hủy')
          self.$emit('xacnhan', obj)
        }
        //Thuộc tính cột
        if (args.element.id == 'MENU_ITEM_THUOC_TINH') {
          cot_id_n = value.split('_')[1]
          obj = {
            cot_id: cot_id_n,
            type: 'MENU_ITEM_THUOC_TINH'
          }
          self.$emit('xacnhan', obj)
        }
      },
      scrollChange: (args) => {
        //Obtains the pan status
        let panStatus = args.panState
      }
    }
  },
  async created() {},
  destroyed() {},
  computed: {
    nodesData: function() {
      if (this.nodes == null) return []
      else return this.nodes
    },
    connectorsData: function() {
      if (this.connectors == null) return []
      else return this.connectors
    }
  },
  methods: {
    onSelectionChange: function(args) {
      let diagramInstance = this.$refs.diagram.ej2Instances
      diagramInstance.nodes.forEach((node) => {
        if (args.state === 'Changed') {
          if (args.newValue.includes(node)) {
            node.style = { strokeColor: 'red', strokeWidth: 2, strokeOpacity: 0.5, strokeDashArray: '5,5' }
          } else {
            node.style = { strokeWidth: 0 }
          }
        }
      })
      diagramInstance.dataBind()
    },
    ZoomIn: function() {
      let diagramInstance = this.$refs.diagram.ej2Instances
      let zoomOptions = {
        type: 'ZoomIn',
        //Sets the factor by which we can zoom in or zoom out
        zoomFactor: 0.5
      }
      console.log('diagram', diagramInstance)
      //zoomin the diagram
      diagramInstance.zoomTo(zoomOptions)
    },
    ZoomOut: function() {
      let diagramInstance = this.$refs.diagram.ej2Instances
      let zoomOptions = {
        type: 'ZoomOut',
        //Sets the factor by which we can zoom in or zoom out
        zoomFactor: 0.5
      }
      //zoomout the diagram
      diagramInstance.zoomTo(zoomOptions)
    },
    ZoomReset: function() {
      let diagramInstance = this.$refs.diagram.ej2Instances
      diagramInstance.reset()
      diagramInstance.fitToPage()
    },
    selectedCot: function(id) {
      if (id != null) {
        let diagramInstance = this.$refs.diagram.ej2Instances
        diagramInstance.reset()

        let node = diagramInstance.nodes.filter((x) => x.id == id)
        if (node != null) {
          let bound = new Rect(node[0].offsetX - 200, node[0].offsetY - 300, 600, 600)
          diagramInstance.select(node)
          diagramInstance.bringIntoView(bound)
        }
      }
    },
    collectionChange: function(args) {},
    dbclick: function(args) {
      console.log('dbclick', args)
      let obj = Object
      try {
        if (args.source.id.includes('LINK_COT')) {
          let value = args.source.id.split('_')
          obj = {
            cot_id: value[2],
            cotsau_id: value[4]
          }
          this.$emit('Kc2Cot', obj)
        } else if (args.source.id.includes('COT')) {
          let value = args.source.id.split('_')
          obj = {
            cot_id: value[1]
          }
          this.$emit('thuoctinh_cot', obj)
        }
      } catch (e) {}
    },
    chonCot: function(args) {
      let index = 0
      let doituong = []
      if (args != null && args.element.id != '') {
        if (this.isChonCot) {
          if (args.element.id.includes('COT_')) {
            let value = args.element.id.split('_')
            let cotsau_id = value[1]
            this.$emit('getcotSau', cotsau_id)
          }
          this.isChonCot = false
        }
        if (this.isTaoLienKet) {
          if (args.element.id.includes('COT_')) {
            let value = args.element.id.split('_')
            doituong[i] = value[1]
            if (index == 0) this.$toast.info('Chọn cột cần liên kết tới\r\nNhấn ESC để hủy')
            i++
            if (index == 2) {
              this.isTaoLienKet = false
              this.$emit('tao_lien_ket', doituong)
            }
          }
        }
      }
    },
    keyDown: function(event) {
      if (this.isChonCot) {
        if (event.key == 'Escape') {
          this.isChonCot = false
          this.$toast.info('Đã hủy thao tác liên kết cột')
        }
      }
    },
    setNode(nodes) {
      this.nodes = nodes
    },
    addNode(Node) {
      diagramInstance.addNode(Node)
    },
    doLayout() {
      diagramInstance.doLayout()
      diagramInstance.fitToPage()
    }
  }
}
</script>
