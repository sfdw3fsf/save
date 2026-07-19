<template src="./index.template.html"></template>
<script>
import { SymbolPalettePlugin, DiagramContextMenu, DiagramTools, SnapConstraints, SelectorConstraints, PrintAndExport, Diagram, LineRouting, DiagramConstraints, Rect } from '@syncfusion/ej2-vue-diagrams'
Diagram.Inject(LineRouting)
let constraints = DiagramConstraints.Default | DiagramConstraints.Virtualization | DiagramConstraints.Bridging
export default {
  components: {
    SymbolPalettePlugin
  },
  provide: {
    diagram: [DiagramContextMenu, PrintAndExport]
  },
  watch: {
    nodes: function(value) {
      console.log('🚀 ~ file: index.vue:26 ~ ej2-vue-diagrams value:', value)
      let diagramInstance = this.$refs.diagram.ej2Instances
      diagramInstance.clear()
      diagramInstance.refresh()
      diagramInstance.nodes = value
    },
    connectors: function(value) {
      console.log('🚀 ~ file: index.vue:32 ~ ej2-vue-diagrams value:', value)
      let diagramInstance = this.$refs.diagram.ej2Instances
      diagramInstance.connectors = value
    },
    refresh: function(value) {
      let diagramInstance = this.$refs.diagram.ej2Instances
      if (diagramInstance.width == '100%') {
        diagramInstance.width = '99.9%'
      } else {
        diagramInstance.width = '100%'
      }
    },
    zoomFactor: function(value) {
      let diagramInstance = this.$refs.diagram.ej2Instances
      diagramInstance.zoom(this.zoomFactor)
    },
    isTaoDoanCong: function(value) {
      if (value == true) {
        this.$toast.success('Chọn bể nguồn\r\nNhấn ESC để hủy')
      } else {
        this.isChonBe = false
      }
    }
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
    zoomFactor: null,
    isTaoDoanCong: false
  },
  mounted: function() {
    let diagramInstance = this.$refs.diagram.ej2Instances
    diagramInstance.fitToPage()
    diagramInstance.addEventListener('selectionChange', this.onSelectionChange) 
  },
  data: function() {
    let self = this
    return {
      idxColor: 0,
      timerInterval: null,
      width: '100%',
      height: '640px',
      isChonBe: false,
      constraints: constraints,
      tool: DiagramTools.SingleSelect | DiagramTools.DrawOnce | DiagramTools.ZoomPan,
      scrollSetting: {
        horizontalOffset: 10,
        verticalOffset: 10
      },
      snapSettings: { constraints: SnapConstraints.ShowLines },
      contextMenuSettings: {
        show: true,
        items: [
          {
            text: 'Di chuyển tới giản đồ của tuyến',
            id: 'MENU_ITEM_CHUYEN_GIANDO',
            target: '.e-diagramcontent',
            iconCss: 'icon text-main f20 one-forward'
          },
          {
            text: 'Tạo cổng tới bể tiếp theo',
            id: 'MENU_ITEM_LK_BE_TT',
            target: '.e-diagramcontent',
            iconCss: 'icon text-main f20 one-taocap'
          },
          {
            text: 'Tạo cổng kết nối với bể khác tuyến',
            id: 'MENU_ITEM_LK_BE_TK',
            target: '.e-diagramcontent',
            iconCss: 'icon text-main f20 one-taokn'
          },
          {
            id: 'MENU_ITEM_SEPARATOR_1',
            separator: true
          },
          {
            text: 'Xóa bể',
            id: 'MENU_ITEM_XOA_BE',
            target: '.e-diagramcontent',
            iconCss: 'icon text-main f20 one-trash'
          },
          {
            id: 'MENU_ITEM_SEPARATOR_2',
            separator: true
          },
          {
            text: 'Thuộc tính',
            id: 'MENU_ITEM_THUOC_TINH',
            target: '.e-diagramcontent',
            iconCss: 'icon text-main f20 -ap icon-info'
          },
          {
            text: 'Chuyển sang tuyến cống bể khác',
            id: 'MENU_ITEM_CHUYEN_TUYENCB',
            target: '.e-diagramcontent',
            iconCss: 'icon text-main f20 one-forward'
          },
          {
            text: 'Chèn bể',
            id: 'MENU_ITEM_THEM_BE',
            target: '.e-diagramcontent',
            iconCss: 'icon text-main f20 one-tc-chenbe'
          },
          {
            text: 'Đảo chiều đoạn cống',
            id: 'MENU_ITEM_LK_DAOCHIEU',
            target: '.e-diagramcontent',
            iconCss: 'icon text-main f20 one-tc-daochieu'
          },
          {
            text: 'Thay đổi bể A',
            id: 'MENU_ITEM_DOI_BE_A',
            target: '.e-diagramcontent',
            iconCss: 'icon text-main f20 one-tc-chenbea'
          },
          {
            text: 'Thay đổi bể B',
            id: 'MENU_ITEM_DOI_BE_B',
            target: '.e-diagramcontent',
            iconCss: 'icon text-main f20 one-tc-chenbeb'
          },
          {
            text: 'Xóa cống',
            id: 'MENU_ITEM_XOA_CONG',
            target: '.e-diagramcontent',
            iconCss: 'icon text-main f20 one-trash'
          },
          {
            id: 'MENU_ITEM_SEPARATOR_3',
            separator: true
          },
          {
            text: 'Thuộc tính',
            id: 'MENU_ITEM_THUOC_TINH_DC',
            target: '.e-diagramcontent',
            iconCss: 'icon text-main f20 -ap icon-info'
          }
        ],
        showCustomMenuOnly: true
      },
      selectedItems: {
        constraints: SelectorConstraints.None
      },
      contextMenuOpen: function(args) {
        if (this.selectedItems.nodes.length > 0) {
          if (this.selectedItems.nodes[0].dataBe.TUYENKHAC != 0) {
            args.hiddenItems.push('MENU_ITEM_SEPARATOR_1')
            args.hiddenItems.push('MENU_ITEM_SEPARATOR_2')
            args.hiddenItems.push('MENU_ITEM_SEPARATOR_3')
            args.hiddenItems.push('MENU_ITEM_LK_BE_TT')
            args.hiddenItems.push('MENU_ITEM_LK_BE_TK')
            args.hiddenItems.push('MENU_ITEM_XOA_BE')
            args.hiddenItems.push('MENU_ITEM_THUOC_TINH')
            args.hiddenItems.push('MENU_ITEM_CHUYEN_TUYENCB')
          } else {
            args.hiddenItems.push('MENU_ITEM_CHUYEN_GIANDO')
          }
          args.hiddenItems.push('MENU_ITEM_THEM_BE')
          args.hiddenItems.push('MENU_ITEM_LK_DAOCHIEU')
          args.hiddenItems.push('MENU_ITEM_DOI_BE_A')
          args.hiddenItems.push('MENU_ITEM_DOI_BE_B')
          args.hiddenItems.push('MENU_ITEM_XOA_CONG')
          args.hiddenItems.push('MENU_ITEM_THUOC_TINH_DC')
          args.hiddenItems.push('MENU_ITEM_SEPARATOR_3')
        } else if (this.selectedItems.connectors.length > 0) {
          args.hiddenItems.push('MENU_ITEM_CHUYEN_GIANDO')
          args.hiddenItems.push('MENU_ITEM_SEPARATOR_1')
          args.hiddenItems.push('MENU_ITEM_LK_BE_TT')
          args.hiddenItems.push('MENU_ITEM_LK_BE_TK')
          args.hiddenItems.push('MENU_ITEM_XOA_BE')
          args.hiddenItems.push('MENU_ITEM_THUOC_TINH')
          args.hiddenItems.push('MENU_ITEM_CHUYEN_TUYENCB')
          args.hiddenItems.push('MENU_ITEM_SEPARATOR_2')
        } else {
          args.cancel = true
        }
      },
      GetNode(objNode) {
        let diagramInstance = this.$refs.diagram.ej2Instances
        for (let k = 0; k < diagramInstance.nodes.length; k++) {
          let n = diagramInstance.nodes[k]
          if (n.id.toString() === objNode) {
            return n
          }
        }
        return null
      },
      contextMenuClick: function(args) {
        let value = ''
        let obj = Object
        let beId
        let doanCongId
        if (this.selectedItems.nodes.length > 0) {
          value = 'node: ' + this.selectedItems.nodes[0].id
        } else if (this.selectedItems.connectors.length > 0) {
          value = 'connector: ' + this.selectedItems.connectors[0].id
        }
        // Chọn bể khác tuyến
        if (args.element.id == 'MENU_ITEM_DOI_BE_A' || args.element.id == 'MENU_ITEM_DOI_BE_B') {
          doanCongId = value.split('_')[1]
          obj = {
            doanCongId: doanCongId,
            type: args.element.id
          }
          self.$emit('xacnhanCong', obj)
          this.vueInstance.$root.$bvModal.show('popupDsBe')
        }
        // Chọn tuyến khác
        if (args.element.id == 'MENU_ITEM_CHUYEN_TUYENCB') {
          beId = value.split('_')[1]
          obj = {
            beId: beId,
            type: 'MENU_ITEM_CHUYEN_TUYENCB'
          }
          self.$emit('xacnhan', obj)
          this.vueInstance.$root.$bvModal.show('popupDsTuyenCB')
        }
        // Tạo đoạn cống tới bể tiếp theo
        if (args.element.id == 'MENU_ITEM_LK_BE_TT') {
          value = this.selectedItems.nodes[0]
          obj = {
            dataBe: value.dataBe,
            type: args.element.id
          }
          console.log('🚀 ~ file: index.vue:243 ~ obj:', obj)
          self.isChonBe = true
          self.$toast.success('Chọn bể cần tạo đoạn cống tới\r\nNhấn ESC để hủy')
          self.$emit('taoDoanCong', obj)
          // this.vueInstance.$root.$bvModal.show('popupQLDoanCong')
        }
        // Tạo đoạn cống tới bể khacs tuyen
        if (args.element.id == 'MENU_ITEM_LK_BE_TK') {
          value = this.selectedItems.nodes[0]
          obj = {
            dataBe: value.dataBe,
            type: args.element.id
          }
          self.$emit('taoDoanCong', obj)
        }

        // Đảo chiều đoạn cống
        if (args.element.id == 'MENU_ITEM_LK_DAOCHIEU') {
          doanCongId = value.split('_')[1]
          obj = {
            doanCongId: doanCongId,
            type: args.element.id
          }
          self.$emit('xacnhanDC', obj)
        }

        // Chèn bể
        if (args.element.id == 'MENU_ITEM_THEM_BE') {
          doanCongId = value.split('_')[1]
          obj = {
            doanCongId: doanCongId,
            type: args.element.id
          }
          self.$emit('chenBe', obj)
        }

        // Tạo đoạn cống và ống cống
        if (args.element.id == 'MENU_ITEM_THUOC_TINH_DC') {
          doanCongId = value.split('_')[1]
          obj = {
            doanCongId: doanCongId,
            type: 'MENU_ITEM_THUOC_TINH_DC'
          }
          self.$emit('xacnhanDC', obj)
          this.vueInstance.$root.$bvModal.show('popupQLDoanCongVaOngCong')
        }
        // Xóa bể
        if (args.element.id == 'MENU_ITEM_XOA_BE') {
          beId = value.split('_')[1]
          obj = {
            beId: beId,
            type: 'MENU_ITEM_XOA_BE'
          }
          self.$emit('xacnhan', obj)
          this.vueInstance.$root.$bvModal.show('modalXoa')
        }
        // Xóa đoạn cống
        if (args.element.id == 'MENU_ITEM_XOA_CONG') {
          doanCongId = value.split('_')[1]
          obj = {
            doanCongId: doanCongId,
            type: 'MENU_ITEM_XOA_CONG'
          }
          self.$emit('xacnhanCong', obj)
          this.vueInstance.$root.$bvModal.show('modalXoaCong')
        }
        // Thuộc tính cột
        if (args.element.id == 'MENU_ITEM_THUOC_TINH') {
          beId = value.split('_')[1]
          obj = {
            beId: beId,
            type: 'MENU_ITEM_THUOC_TINH'
          }
          self.$emit('xacnhan', obj)
          this.vueInstance.$root.$bvModal.show('popupQLBe')
        }
        // Di chuyển tới giản đồ của tuyến
        if (args.element.id == 'MENU_ITEM_CHUYEN_GIANDO') {
          beId = value.split('_')[1]
          self.$emit('TIMKIEM_DOITUONG', 'BECAP|' + beId + '|TIM_THEO_ID', 5)
        }
      },
      scrollChange: (args) => {
        let panStatus = args.panState
      }
    }
  },
  created: function() {},
  destroyed() {
    let diagramInstance = this.$refs.diagram.ej2Instances
    if (diagramInstance.width == '100%') diagramInstance.width = '99.9%'
    else diagramInstance.width = '100%'
  },
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
    onSelectionChange: function (args) {
      let diagramInstance = this.$refs.diagram.ej2Instances
      diagramInstance.nodes.forEach(node => {
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
    selectedBe: function(id) {
      if (id != null) {
        let diagramInstance = this.$refs.diagram.ej2Instances
        diagramInstance.reset()
        let node = diagramInstance.nodes.find((x) => x.id == 'BC_' + id)
        if (node != null) {
          console.log('🚀 ~ file: index.vue:357 ~ node:', node)
          let bound = new Rect(node.offsetX - 200, node.offsetY - 300, 600, 600)
          setTimeout(() => {
            diagramInstance.select([node])
            diagramInstance.bringIntoView(bound)
          }, 500)
        }
      }
    },
    ZoomIn: function() {
      let diagramInstance = this.$refs.diagram.ej2Instances
      let zoomOptions = {
        type: 'ZoomIn',
        zoomFactor: 0.5
      }
      // zoomin the diagram
      diagramInstance.zoomTo(zoomOptions)
    },
    ZoomOut: function() {
      let diagramInstance = this.$refs.diagram.ej2Instances
      let zoomOptions = {
        type: 'ZoomOut',
        zoomFactor: 0.5
      }
      // zoomout the diagram
      diagramInstance.zoomTo(zoomOptions)
    },
    ZoomReset: function() {
      let diagramInstance = this.$refs.diagram.ej2Instances
      diagramInstance.fitToPage('width', 'page', { left: 15, top: 15, right: 0, bottom: 15 })
    },
    dbclick: function(args) {
      this.$emit('dbClick', {
        type: args.source.propName,
        id: args.source.id
      })
    },
    chonBe: function(args) {
      let index = 0
      let doituong = []
      if (args != null && args.element.id != '') {
        if (this.isChonBe) {
          if (args.element.id.includes('BC_')) {
            let value = args.element.id.split('_')
            let obj = {
              dataBe: args.element.dataBe,
              type: 'MENU_ITEM_LK_BE_TT_CHON'
            }
            this.$emit('taoDoanCong', obj)
          }
          this.isChonBe = false
        } else if (this.isTaoDoanCong) {
          if (args.element.id.includes('BC_')) {
            this.isChonBe = true
            this.$toast.success('Chọn bể cần tạo đoạn cống tới\r\nNhấn ESC để hủy')
            let obj = {
              dataBe: args.element.dataBe,
              type: 'MENU_ITEM_LK_BE_TT'
            }

            this.$emit('taoDoanCong', obj)
          }
        }
      }
    },
    keyDown: function(event) {
      if (this.isChonBe) {
        if (event.key == 'Escape') {
          this.isChonBe = false
          this.$toast.success('Đã hủy thao tác tạo cống')
          let obj = {
            type: 'MENU_ITEM_LK_BE_TT_HUY'
          }
          this.$emit('taoDoanCong', obj)
        }
      }
    }
  }
}
</script>
<style></style>
