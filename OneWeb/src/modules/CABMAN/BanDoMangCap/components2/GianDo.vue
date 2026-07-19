<template>
  <div class="oneBSSDiagram">
    <ejs-diagram
      style="display: block"
      ref="diagramGianDo"
      id="diagramGianDo"
      :width="width"
      :height="height"
      :layout="layout"
      :getConnectorDefaults="getConnectorDefaults"
      :getNodeDefaults="getNodeDefaults"
      :tool="tool"
      :constraints="constraints"
      :mouseOver="mouseOver"
      :mouseLeave="mouseLeave"
      :snapSettings="snapSettings"
      :scrollSettings="scrollSettings"
      :selectedItems="selectedItems"
      enableAnimation="true"
      :click="onClick"
      :contextMenuSettings="contextMenuSettings"
      :contextMenuOpen="contextMenuOpen"
      :contextMenuClick="contextMenuClick"
      :doubleClick="onDbClick"
      mode="SVG"
      backgroundColor="white"
    >
    </ejs-diagram>
    <QuanLyCapReNhanh id="popupQuanLyCapReNhanh" :midspanObject="getMidspanObject" @napGD="napGD"></QuanLyCapReNhanh>
    <DaunoiThietBi id="gpopupDauNoiTbi" :doiTuong="doiTuong" :allowChoose="allowChoose"></DaunoiThietBi>
    <ModalQLCapExt modalID="ModalQLCapExt" ref="ModalQLCapExt" />
    <ModalGianDoTreeList ref="ModalGianDoTreeList" modalID="ModalGianDoTreeList"></ModalGianDoTreeList>
  </div>
</template>
<style>
.oneBSSDiagram {
  width: calc(100%);
  height: calc(100%);
  float: left;
  border: 1px solid rgba(206, 131, 131, 0.12);
  border-left: none;
}

.image-pattern-style {
  background-color: white;
  background-size: contain;
  background-repeat: no-repeat;
  height: 45px;
  width: calc((100% - 13px) / 3);
  cursor: pointer;
  border: 1px solid #d5d5d5;
  background-position: center;
  float: left;
}

.image-pattern-style:hover {
  border-color: gray;
  border-width: 2px;
}

.row {
  margin-left: 0px;
  margin-right: 0px;
}

.row-header {
  font-size: 13px;
  font-weight: 500;
}

.e-checkbox-wrapper .e-label {
  font-size: 12px;
}

.col-xs-6 {
  padding-left: 0px;
  padding-right: 0px;
}

.e-selected-style {
  border-color: #006ce6;
  border-width: 2px;
}

#diagramGianDocontent {
  overflow: hidden !important;
}
</style>

<script>
import Vue from 'vue'
import EventBus from '@/utils/eventBus'

import { ComplexHierarchicalTree, ConnectorConstraints, NodeConstraints, SelectorConstraints, DiagramPlugin, Diagram, DiagramTools, LineDistribution, SnapConstraints, LineRouting, DiagramConstraints, PrintAndExport, DiagramContextMenu, ConnectionPointOrigin, AnnotationConstraints } from '@syncfusion/ej2-vue-diagrams'

// import {
//   Toolbar,
//   ItemDirective,
//   ItemsDirective,
//   ToolbarComponent
// } from '@syncfusion/ej2-vue-navigations'

import QuanLyCapReNhanh from '../../QuanLyCapReNhanh/QuanLyCapReNhanh'
import DaunoiThietBi from '../../daunoi-thietbi/Modal'
import ModalQLCapExt from '../modal/QLCapExt2'
import ModalGianDoTreeList from '../modal/GianDoTreeList'
import GraphNode from '../structures/GraphNode'

import { mapActions, mapState, mapGetters } from 'vuex'
import { getterName, actionName, statePropertyName } from './../store'

Vue.use(DiagramPlugin)
Diagram.Inject(LineRouting, ComplexHierarchicalTree, LineDistribution)
let diagramInstance
let interval
interval = [1, 9, 0.25, 9.75, 0.25, 9.75, 0.25, 9.75, 0.25, 9.75, 0.25, 9.75, 0.25, 9.75, 0.25, 9.75, 0.25, 9.75, 0.25, 9.75]

let gridlines = {
  lineColor: '#e0e0e0',
  lineIntervals: interval
}

export default Vue.extend({
  components: {
    QuanLyCapReNhanh,
    DaunoiThietBi,
    ModalQLCapExt,
    ModalGianDoTreeList
    // 'ejs-toolbar': ToolbarComponent,
    // 'e-items': ItemsDirective,
    // 'e-item': ItemDirective
  },
  data: function () {
    return {
      ds_dv: [],
      dataGianDoTreeList: [],
      caption: null,
      idxColor: 0,
      timerInterval: null,
      pageSettings: {
        width: 50,
        height: 50
      },
      optionsMenuTRAM: [
        { name: '<span class="icon text-main f20 one-daunoi"></span> Đấu nối thiết bị', slug: 'dau_noi_thiet_bi' },
        { name: '<span class="icon text-main f20 one-taocap"></span> Tạo cáp', slug: 'tao_cap' },
        { type: 'divider' },
        { name: '<span class="icon text-main f20 -ap icon-info"></span> Thuộc tính', slug: 'thuoc_tinh_cap' }
      ],
      optionsMenuCAP2: [
        { name: '<span class="icon text-main f20 one-daucuoi"></span> Đấu nối 2 đầu cáp', slug: 'dau_noi_2_dau_cap' },
        { name: '<span class="icon text-main f20 one-taomid"></span> Tạo Mid-Span', slug: 'tao_mid_span' },
        { type: 'divider' },
        { name: '<span class="icon text-main f20 one-trash"></span> Xoá cáp', slug: 'xoa_cap' },
        { type: 'divider' },
        { name: '<span class="icon text-main f20 one-chuyencap"></span> Chuyển nhánh cáp', slug: 'chuyen_nhanh_cap' },
        { name: '<span class="icon text-main f20 one-chenbe"></span> Chèn kết cuối', slug: 'chen_ket_cuoi' },
        { type: 'divider' },
        { name: '<span class="icon text-main f20 one-daochieu"></span> Đảo chiều cáp', slug: 'dao_chieu_cap' },
        { name: '<span class="icon text-main f20 -ap icon-apps"></span> Thuê bao đi qua cáp', slug: 'thue_bao_thuoc_cap' },
        { type: 'divider' },
        { name: '<span class="icon text-main f20 -ap icon-info"></span> Thuộc tính', slug: 'thuoc_tinh_cap' }
      ],
      optionsMenuKETCUOI: [
        { name: '<span class="icon text-main f20 one-daunoi"></span> Tạo kết cuối mới và nối cáp tới', slug: 'tao_ket_cuoi' },
        { name: '<span class="icon text-main f20 one-taocap"></span> Tạo cáp tới kết cuối cùng tuyến', slug: 'tao_cap_toi_ket_cuoi_cung_tuyen' },
        { name: '<span class="icon text-main f20 one-taokn"></span> Tạo cáp kết nối kết cuối khác tuyến', slug: 'tao_cap_toi_ket_cuoi_khac_tuyen' },
        { type: 'divider' },
        { name: '<span class="icon text-main f20 one-daunoi1"></span> Đấu nối bên trong', slug: 'dau_noi_ben_trong' },
        { name: '<span class="icon text-main f20 one-chuyen"></span> Chuyển vào trong kết cuối/trạm thiết bị', slug: 'chuyen_vao_trong' },
        { type: 'divider' },
        { name: '<span class="icon text-main f20 -ap icon-apps"></span> Thuê bao trên kết cuối', slug: 'thue_bao_thuoc_ket_cuoi' },
        { type: 'divider' },
        { name: '<span class="icon text-main f20 one-trash"></span> Xóa kết cuối', slug: 'xoa_ket_cuoi' },
        { type: 'divider' },
        { name: '<span class="icon text-main f20 -ap icon-info"></span> Thuộc tính', slug: 'thuoc_tinh_ket_cuoi' }
      ],
      contextMenuOpen: function (args) {
        if (this.selectedItems.nodes.length > 0) {
          console.log('this.selectedItems.nodes', this.selectedItems.nodes)
          if (this.selectedItems.nodes[0].id.startsWith('TRAM_')) {
            for (let index = 0; index < this.contextMenuSettings.items.length; index++) {
              const element = this.contextMenuSettings.items[index]
              if (element.group && element.group !== 'TRAM') {
                args.hiddenItems.push(element.id)
              }
            }
          } else {
            if (this.selectedItems.nodes[0].id.startsWith('KETCUOI_')) {
              for (let index = 0; index < this.contextMenuSettings.items.length; index++) {
                const element = this.contextMenuSettings.items[index]
                if (element.group && element.group !== 'KETCUOI') {
                  args.hiddenItems.push(element.id)
                }
              }
              return
            }
            if (this.selectedItems.nodes[0].id.startsWith('KETCUOIKHACTUYEN_')) {
              for (let index = 0; index < this.contextMenuSettings.items.length; index++) {
                const element = this.contextMenuSettings.items[index]
                if (element.group && element.group !== 'KETCUOIKHACTUYEN') {
                  args.hiddenItems.push(element.id)
                }
              }
              return
            }
            this.contextMenuSettings.items.forEach((element) => {
              args.hiddenItems.push(element.id)
            })
            args.cancel = true
          }
        } else if (this.selectedItems.connectors.length > 0) {
          this.contextMenuSettings.items.forEach((element) => {
            if (element.group && element.group !== 'CAP') {
              args.hiddenItems.push(element.id)
            }
          })
        } else {
          console.log('contextMenuOpen')
          this.contextMenuSettings.items.forEach((element) => {
            args.hiddenItems.push(element.id)
          })
          args.cancel = true
        }
      },
      contextMenuSettings: {
        show: true,
        items: [
          // CAP
          {
            text: 'Đấu nối 2 đầu cáp',
            id: 'MENU_CAP_DAUNOI_2DC',
            target: '.e-diagramcontent',
            iconCss: 'icon text-main f20 one-daucuoi',
            group: 'CAP'
          },
          {
            text: 'Tạo Mid-Span',
            id: 'MENU_CAP_TAO_MIDSPAN',
            target: '.e-diagramcontent',
            iconCss: 'icon text-main f20 one-taomid',
            group: 'CAP'
          },
          {
            id: 'MENU_CAP_ITEM_SEPARATOR_1',
            separator: true,
            group: 'CAP'
          },
          {
            text: 'Xoá cáp',
            id: 'MENU_CAP_XOA_CAP',
            target: '.e-diagramcontent',
            iconCss: 'icon text-main f20 one-trash',
            group: 'CAP'
          },
          {
            id: 'MENU_CAP_ITEM_SEPARATOR_2',
            separator: true,
            group: 'CAP'
          },
          {
            text: 'Chuyển nhánh cáp',
            id: 'MENU_CAP_CHUYEN_NHANH_CAP',
            target: '.e-diagramcontent',
            iconCss: 'icon text-main f20 one-chuyencap',
            group: 'CAP'
          },
          {
            text: 'Chèn kết cuối',
            id: 'MENU_CAP_CHEN_KC',
            target: '.e-diagramcontent',
            iconCss: 'icon text-main f20 one-chenbe',
            group: 'CAP'
          },
          {
            text: 'Đảo chiều cáp',
            id: 'MENU_CAP_DAOCHIEU_CAP',
            target: '.e-diagramcontent',
            iconCss: 'icon text-main f20 one-daochieu',
            group: 'CAP'
          },
          {
            id: 'MENU_CAP_ITEM_SEPARATOR_3',
            separator: true,
            group: 'CAP'
          },
          {
            text: 'Thuê bao đi qua cáp',
            id: 'MENU_CAP_TB_DIQUA_CAP',
            target: '.e-diagramcontent',
            iconCss: 'icon text-main f20 -ap icon-apps',
            group: 'CAP'
          },
          {
            id: 'MENU_CAP_ITEM_SEPARATOR_4',
            separator: true,
            group: 'CAP'
          },
          {
            text: 'Thuộc tính',
            id: 'MENU_CAP_THUOCTINH',
            target: '.e-diagramcontent',
            iconCss: 'icon text-main f20 -ap icon-info',
            group: 'CAP'
          },
          // TRAM
          {
            text: 'Đấu nối thiết bị',
            id: 'MENU_TRAM_DAUNOI_TB',
            target: '.e-diagramcontent',
            iconCss: 'icon text-main f20 one-daunoi',
            group: 'TRAM'
          },
          {
            text: 'Tạo cáp',
            id: 'MENU_TRAM_TAO_CAP',
            target: '.e-diagramcontent',
            iconCss: 'icon text-main f20 one-taocap',
            group: 'TRAM'
          },
          {
            id: 'MENU_TRAM_ITEM_SEPARATOR_1',
            separator: true,
            group: 'TRAM'
          },
          {
            text: 'Thuộc tính',
            id: 'MENU_TRAM_THUOC_TINH_TRAM',
            target: '.e-diagramcontent',
            iconCss: 'icon text-main f20 -ap icon-info',
            group: 'TRAM'
          },
          // KETCUOI
          {
            text: 'Tạo kết cuối mới và nối cáp tới',
            id: 'MENU_TAO_KC',
            target: '.e-diagramcontent',
            iconCss: 'icon text-main f20 one-daunoi',
            group: 'KETCUOI'
          },
          {
            id: 'MENU_ITEM_SEPARATOR_2',
            separator: true,
            group: 'KETCUOI'
          },
          {
            text: 'Tạo cáp tới kết cuối cùng tuyến',
            id: 'MENU_TAO_CAP_KC_CUNG_TUYEN',
            target: '.e-diagramcontent',
            iconCss: 'icon text-main f20 one-taocap',
            group: 'KETCUOI'
          },
          {
            text: 'Tạo cáp kết nối kết cuối khác tuyến',
            id: 'MENU_TAO_CAP_KC_KHAC_TUYEN',
            target: '.e-diagramcontent',
            iconCss: 'icon text-main f20 one-taokn',
            group: 'KETCUOI'
          },
          {
            id: 'MENU_ITEM_SEPARATOR_3',
            separator: true,
            group: 'KETCUOI'
          },
          {
            text: 'Đấu nối bên trong',
            id: 'MENU_DAU_NOI_BEN_TRONG',
            target: '.e-diagramcontent',
            iconCss: 'icon text-main f20 one-daunoi1',
            group: 'KETCUOI'
          },
          {
            text: 'Chuyển vào trong kết cuối/trạm thiết bị',
            id: 'MENU_CHUYEN_VAO_TRONG_KC',
            target: '.e-diagramcontent',
            iconCss: 'icon text-main f20 one-chuyen',
            group: 'KETCUOI'
          },
          {
            id: 'MENU_ITEM_SEPARATOR_4',
            separator: true,
            group: 'KETCUOI'
          },
          {
            text: 'Thuê bao trên kết cuối',
            id: 'MENU_THUE_BAO_TREN_KC',
            target: '.e-diagramcontent',
            iconCss: 'icon text-main f20 -ap icon-apps',
            group: 'KETCUOI'
          },
          {
            id: 'MENU_ITEM_SEPARATOR_5',
            separator: true,
            group: 'KETCUOI'
          },
          {
            text: 'Xóa kết cuối',
            id: 'MENU_XOA_KC',
            target: '.e-diagramcontent',
            iconCss: 'icon text-main f20 one-trash',
            group: 'KETCUOI'
          },
          {
            id: 'MENU_ITEM_SEPARATOR_6',
            separator: true,
            group: 'KETCUOI'
          },
          {
            text: 'Thuộc tính',
            id: 'MENU_THUOC_TINH_KC',
            target: '.e-diagramcontent',
            iconCss: 'icon text-main f20 -ap icon-info',
            group: 'KETCUOI'
          },
          {
            text: 'Di chuyển tới giản đồ của tuyến',
            id: 'MENU_DI_CHUYEN_TOI_GIANDO_KC_KHACTUYEN',
            target: '.e-diagramcontent',
            iconCss: 'icon text-main f20 -ap icon-move',
            group: 'KETCUOIKHACTUYEN'
          }
        ],
        showCustomMenuOnly: true
      },
      midspanObject: {
        MIDSPAN_ID: null,
        CAP_ID: null,
        KETCUOI_N_ID: null,
        KETCUOI_D_ID: null,
        KYHIEU_KETCUOI_N: null,
        KYHIEU_KETCUOI_D: null,
        DONVI_ID: null,
        DONVI_TD_ID: null,
        TEN_DV: null,
        TEN_DV_TD: null,
        LOPMC_ID: null,
        LOP_MC: null,
        KIEUDC_ID: null,
        KIEUCAP_ID: null,
        KIEU_CAP: null,
        KYHIEU: '',
        CHIEUDAI: null,
        DS_SOI: ''
      },
      TramTb_id: 0,
      doiTuong: {},
      vdoituong_id: null,
      toast: null,
      previousNode: null,
      width: '100%',
      tool: DiagramTools.SingleSelect | DiagramTools.ZoomPan,
      height: '100%',
      nodes: [],
      refresh: false,
      connectors: [],
      actualObject: null,
      constraints: DiagramConstraints.Default | DiagramConstraints.Tooltip,
      selectedItems: {
        constraints: SelectorConstraints.None
      },
      layout: {
        type: 'ComplexHierarchicalTree',
        orientation: 'LeftToRight',
        verticalSpacing: 150,
        horizontalSpacing: 600,
        horizontalAlignment: 'Left',
        verticalAlignment: 'Top',
        margin: { left: 0, right: 0, top: 0, bottom: 0 },
        connectionPointOrigin: ConnectionPointOrigin.DifferentPoint
        // arrangement: ChildArrangement.Linear
      },
      snapSettings: {
        horizontalGridlines: { lineColor: '#cdcdcd', dotIntervals: [0.95, 20.75], lineIntervals: [0.95, 20.75], snapIntervals: [10] },
        verticalGridlines: { lineColor: '#cdcdcd', dotIntervals: [0.95, 20.75], lineIntervals: [0.95, 20.75], snapIntervals: [10] },
        gridType: 'Dots',
        constraints: SnapConstraints.SnapToLines | SnapConstraints.ShowLines
        // constraints: SnapConstraints.None
      },
      snapSettings2: {
        horizontalGridlines: gridlines,
        verticalGridlines: gridlines
      },
      scrollSettings: {
        canAutoScroll: true,
        scrollLimit: 'Infinity',
        horizontalOffset: 10,
        verticalOffset: 10,
        padding: { right: 50, bottom: 50 }
      },
      getNodeDefaults: (obj) => {
        obj.constraints = (NodeConstraints.Default | NodeConstraints.Tooltip | NodeConstraints.ReadOnly | NodeConstraints.Select) & ~NodeConstraints.Drag & ~NodeConstraints.Resize
      },
      getConnectorDefaults: (obj) => {
        obj.type = 'Orthogonal'
        obj.connectorSpacing = 10
        obj.constraints = (ConnectorConstraints.Default | ConnectorConstraints.ReadOnly | NodeConstraints.Tooltip) & ~ConnectorConstraints.Drag
        obj.cornerRadius = 25
        obj.targetDecorator.height = 10
        obj.targetDecorator.width = 10
        obj.segments = [
          {
            type: 'Orthogonal',
            length: 30,
            direction: 'Bottom'
          }
        ]
        obj.targetDecorator = {
          style: {
            strokeColor: 'black',
            fill: 'black'
          }
        }
      },
      allowChoose: true
    }
  },
  provide: {
    diagram: [PrintAndExport, DiagramContextMenu, ComplexHierarchicalTree, LineDistribution]
  },
  watch: {
    refresh: function (value) {
      if (diagramInstance.width === '100%') {
        diagramInstance.width = '99.9%'
      } else {
        diagramInstance.width = '100%'
      }
    },
  },
  computed: {
    ...mapState('cabman/BanDoMangCap', statePropertyName),
    ...mapState('cabman', ['BanDoMangCap']),
    ...mapGetters('cabman/BanDoMangCap', getterName),
    getNodes() {
      return [...this.nodes]
    },
    getConnectors() {
      return [...this.connectors]
    },
    getMidspanObject() {
      return this.midspanObject
    },
    gettramTb_id() {
      return this.tramTb_id
    }
  },
  mounted: function () {
    diagramInstance = this.$refs.diagramGianDo.ej2Instances
    diagramInstance.bringToFront()
    diagramInstance.dataBind()
    diagramInstance.fitToPage({
      mode: 'Width'
    })
    this.hideToast()
  },
  methods: {
    ...mapActions('cabman/BanDoMangCap', actionName),
    napGD() {
      EventBus.$emit('NAP_GIANDO_V2', 'NAP_GIANDO_V2')
    },
    async ACTIVE_OBJECT(obj) {
      try {
        if (obj) {
          if (obj.LOAIDT === 'KETCUOI' || obj.LOAIDT === 'THUEBAO') {
            var objNodeId = obj.ID.toString()
            if (obj.PARENT_ID && obj.PARENT_ID !== null) {
              objNodeId = obj.PARENT_ID.toString()
            }
            let kcNodeActive = this.GetNode(this.GetNodeID(objNodeId))
            if (kcNodeActive) {
              clearInterval(this.timerInterval)
              let idxColor = this.idxColor
              let color = ['#027217', '#FF5733']
              kcNodeActive.annotations[0].style.color = 'red'
              kcNodeActive.style.strokeColor = color[idxColor]
              kcNodeActive.style.strokeWidth = 1.5
              idxColor = (idxColor + 1) % color.length
              this.timerInterval = setInterval(function () {
                kcNodeActive.style.strokeColor = color[idxColor]
                idxColor = (idxColor + 1) % color.length
              }, 500)
            }
          }
          if (obj.LOAIDT === 'CAP') {
            var objEdgeId = obj.ID.toString()
            let capEdgeActive = this.GetEdge(this.GetEdgeID(objEdgeId))
            if (capEdgeActive) {
              clearInterval(this.timerInterval)
              let idxColor = this.idxColor
              let color = [capEdgeActive.styleCap.strokeColor, '#FF5733']
              capEdgeActive.style.strokeColor = color[idxColor]
              capEdgeActive.annotations[0].style.color = 'red'
              capEdgeActive.annotations[0].style.bold = true
              idxColor = (idxColor + 1) % color.length
              this.timerInterval = setInterval(function () {
                capEdgeActive.style.strokeColor = color[idxColor]
                idxColor = (idxColor + 1) % color.length
              }, 500)
            }
          }
        }
      } catch (error) {
        console.log('🚀 ~ file: GianDo.vue ~ line 553 ~ ACTIVE_OBJECT ~ error', error)
      }
    },
    async ACTIVE_OBJECT_V2(obj) {
      try {
        if (obj.objData) {
          if (obj.objData.LOAIDT === 'KETCUOI' || obj.objData.LOAIDT === 'THUEBAO') {
            var objNodeId = obj.objData.ID.toString()
            if (obj.objData.PARENT_ID && obj.objData.PARENT_ID !== null) {
              objNodeId = obj.objData.PARENT_ID.toString()
            }
            let kcNodeActive = this.GetNode(this.GetNodeID(objNodeId))
            if (kcNodeActive) {
              let BringToCenter = {
                x: kcNodeActive.offsetX - kcNodeActive.width / 2,
                y: kcNodeActive.offsetY - kcNodeActive.height / 2,
                width: kcNodeActive.width,
                height: kcNodeActive.height
              }
              this.zoomChange('100%')
              diagramInstance.bringToCenter(BringToCenter)
              clearInterval(this.timerInterval)
              let idxColor = this.idxColor
              let color = ['#027217', '#FF5733']
              kcNodeActive.annotations[0].style.color = 'red'
              kcNodeActive.style.strokeColor = color[idxColor]
              kcNodeActive.style.strokeWidth = 1.5
              idxColor = (idxColor + 1) % color.length
              this.timerInterval = setInterval(function () {
                kcNodeActive.style.strokeColor = color[idxColor]
                idxColor = (idxColor + 1) % color.length
              }, 500)
            }
          }
          if (obj.objData.LOAIDT === 'CAP') {
            var objEdgeId = obj.objData.ID.toString()
            if (obj.objData.PARENT_ID && obj.objData.PARENT_ID !== null) {
              let objNodeId = obj.objData.PARENT_ID.toString()
              let kcNodeActive = this.GetNode(this.GetNodeID(objNodeId))
              if (kcNodeActive) {
                let BringToCenter = {
                  x: kcNodeActive.offsetX - kcNodeActive.width / 2,
                  y: kcNodeActive.offsetY - kcNodeActive.height / 2,
                  width: kcNodeActive.width,
                  height: kcNodeActive.height
                }
                this.zoomChange('100%')
                diagramInstance.bringToCenter(BringToCenter)
                clearInterval(this.timerInterval)
                let idxColor = this.idxColor
                let color = ['#027217', '#FF5733']
                kcNodeActive.annotations[0].style.color = 'red'
                kcNodeActive.style.strokeColor = color[idxColor]
                kcNodeActive.style.strokeWidth = 1.5
                idxColor = (idxColor + 1) % color.length
                this.timerInterval = setInterval(function () {
                  kcNodeActive.style.strokeColor = color[idxColor]
                  idxColor = (idxColor + 1) % color.length
                }, 500)
              }
            }
            let capEdgeActive = this.GetEdge(this.GetEdgeID(objEdgeId))
            if (capEdgeActive) {
              let idxColor = this.idxColor
              let color = [capEdgeActive.styleCap.strokeColor, '#FF5733']
              capEdgeActive.style.strokeColor = color[idxColor]
              capEdgeActive.annotations[0].style.color = 'red'
              capEdgeActive.annotations[0].style.bold = true
              idxColor = (idxColor + 1) % color.length
              this.timerInterval = setInterval(function () {
                capEdgeActive.style.strokeColor = color[idxColor]
                idxColor = (idxColor + 1) % color.length
              }, 500)
            }
          }
          if (obj.dsDANDO_VEGOC && obj.dsDANDO_VEGOC.length > 0) {
            obj.dsDANDO_VEGOC.forEach((element) => {
              let objEdgeId = element.CAP_ID.toString()
              let objEdgeId_MIDSPAN = element.MIDSPAN_ID.toString()
              let capEdgeActive = this.GetEdge(this.GetEdgeID_MIDSPAN(objEdgeId, objEdgeId_MIDSPAN))
              if (capEdgeActive) {
                let idxColor = this.idxColor
                let color = [capEdgeActive.styleCap.strokeColor, '#FF5733']
                capEdgeActive.style.strokeColor = color[idxColor]
                capEdgeActive.annotations[0].style.color = 'red'
                capEdgeActive.annotations[0].style.bold = true
                idxColor = (idxColor + 1) % color.length
                this.timerInterval = setInterval(function () {
                  capEdgeActive.style.strokeColor = color[idxColor]
                  idxColor = (idxColor + 1) % color.length
                }, 1000)
              }
            })
          }
        }

      } catch (error) {
        console.log('🚀 ~ file: GianDo.vue ~ line 553 ~ ACTIVE_OBJECT ~ error', error)
      }
    },
    GetNodeID(objNodeId) {
      return 'KETCUOI_' + objNodeId
    },
    GetEdgeID(objEdgeId) {
      return 'CAP_' + objEdgeId + '_0'
    },
    GetEdgeID_MIDSPAN(objEdgeId, midspan_id) {
      return 'CAP_' + objEdgeId + '_' + midspan_id
    },
    GetEdge(objEdge) {
      for (let k = 0; k < diagramInstance.connectors.length; k++) {
        let n = diagramInstance.connectors[k]
        if (n.id.toString() === objEdge) {
          return n
        }
      }
      return null
    },
    GetNode(objNode) {
      for (let k = 0; k < diagramInstance.nodes.length; k++) {
        let n = diagramInstance.nodes[k]
        if (n.id.toString() === objNode) {
          return n
        }
      }
      return null
    },
    async onClick(args) {
      console.log('🚀 ~ file: GianDo.vue:691 ~ onClick ~ args:', args)
      let clickedItem = args.actualObject
      if (clickedItem) {
        this.vdoituong_id = clickedItem.id.split('_')
        if (args.button === 'Left') {
          clearInterval(this.timerInterval)
          for (let k = 0; k < diagramInstance.connectors.length; k++) {
            diagramInstance.connectors[k].style = diagramInstance.connectors[k].styleCap
            diagramInstance.connectors[k].annotations[0].style.color = 'black'
            diagramInstance.connectors[k].annotations[0].style.bold = false
          }
          for (let k = 0; k < diagramInstance.nodes.length; k++) {
            diagramInstance.nodes[k].annotations[0].style.color = 'black'
            diagramInstance.nodes[k].style.strokeColor = 'black'
            diagramInstance.nodes[k].style.strokeWidth = 0
          }

          if (this.vdoituong_id[0] === 'THIEU') {
            let loai_dt = this.vdoituong_id[1] // Trạm | Kết cuối
            let nguondich = this.vdoituong_id[2] // Nguồn | Đích
            let cap_id = this.vdoituong_id[3] // Cáp cần thêm
            if (loai_dt === 'KETCUOI') {
              if (nguondich === 'DICH') {
                this.$emit('taoKetCuoiDich', cap_id)
              } else {
                this.$alert('Chức năng hiện chưa hỗ trợ chọn kết cuối nguồn !', 'Thông báo', {
                  confirmButtonText: 'Đóng',
                  type: 'warning',
                  callback: (action) => {}
                })
              }
            } else {
              this.$alert('Chức năng đang được xây dựng !', 'Thông báo', {
                confirmButtonText: 'Đóng',
                type: 'warning',
                callback: (action) => {}
              })
            }
            return
          } else {
            if (clickedItem instanceof Object && clickedItem.constructor.name === 'Connector') {
              let idxColor = this.idxColor
              let color = [clickedItem.styleCap.strokeColor, '#FF5733']
              clickedItem.style.strokeColor = color[idxColor]
              clickedItem.annotations[0].style.color = 'red'
              clickedItem.annotations[0].style.bold = true
              idxColor = (idxColor + 1) % color.length
              this.timerInterval = setInterval(function () {
                clickedItem.style.strokeColor = color[idxColor]
                idxColor = (idxColor + 1) % color.length
              }, 500)
            }
            if (clickedItem instanceof Object && clickedItem.constructor.name === 'Node') {
              let idxColor = this.idxColor
              let color = ['#027217', '#FF5733']
              clickedItem.annotations[0].style.color = 'red'
              clickedItem.style.strokeColor = color[idxColor]
              clickedItem.style.strokeWidth = 1.5
              idxColor = (idxColor + 1) % color.length
              this.timerInterval = setInterval(function () {
                clickedItem.style.strokeColor = color[idxColor]
                idxColor = (idxColor + 1) % color.length
              }, 500)
            }
          }
        }
        if (args.button === 'Right') {
          return
        }
        if (this.vaction !== 'None') {
          if (this.vaction === 'TaoCap') {
            console.log('🚀 ~ file: GianDo.vue ~ line 63000 ~ onClick ~ this.vnguon_id', this.vnguon_id)
            if (this.vnguon_id === null || this.vnguon_id.length === 0) {
              this.setState({ key: 'vnguon_id', value: this.vdoituong_id })
              this.toast && this.toast.close()
              this.showToast('Chọn kết cuối / trạm thiết bị đích', '<b>Nhấn ESC để hủy<b>')
              return
            }
            if (this.vnguon_id[0] !== this.vdoituong_id[0] || this.vnguon_id[1] !== this.vdoituong_id[1]) {
              if (this.vdoituong_id[0] == 'CAP') {
                this.toast && this.toast.close()
                this.setState({ key: 'vaction', value: 'None' })
                this.setState({ key: 'vnguon_id', value: null })
                this.setState({ key: 'vdich_id', value: null })
                return
              }
              this.toast && this.toast.close()
              this.setState({ key: 'vdich_id', value: this.vdoituong_id })
              var donvi_id = null
              var ketcuoi_n_id = null
              var ketcuoi_d_id = null
              var donvi_td_id = null
              console.log('🚀 ~ file: GianDo.vue:784 ~ onClick ~ this.vnguon_id:', this.vnguon_id)
              console.log('🚀 ~ file: GianDo.vue:784 ~ onClick ~ this.vdich_id:', this.vdich_id)
              if (this.vnguon_id[0] === 'TRAM') {
                donvi_id = this.vnguon_id[1]
              } else {
                ketcuoi_n_id = this.vnguon_id[1]
              }
              if (this.vdich_id[0] === 'TRAM') {
                donvi_td_id = this.vdich_id[1]
              } else {
                ketcuoi_d_id = this.vdich_id[1]
              }
              var data = {
                capgoc: this.capgoc,
                donvi: { DONVI_ID: donvi_id },
                donvi_td_id: donvi_td_id,
                ketcuoi_n_id: ketcuoi_n_id,
                ketcuoi_d_id: ketcuoi_d_id,
                kieucap_id: 0,
                toql: this.toql
              }
              console.log('🚀 ~ file: GianDo.vue:795 ~ onClick ~ data:', data)
              this.$refs.ModalQLCapExt.taoCap(data)
                .then((result) => {
                  if (result.ok) {
                    EventBus.$emit('NAP_GIANDO_V2', 'NAP_GIANDO_V2')
                  } else {
                  }
                  this.setState({ key: 'vaction', value: 'None' })
                  this.setState({ key: 'vnguon_id', value: null })
                  this.setState({ key: 'vdich_id', value: null })
                })
                .catch(() => {
                  this.setState({ key: 'vaction', value: 'None' })
                  this.setState({ key: 'vnguon_id', value: null })
                  this.setState({ key: 'vdich_id', value: null })
                })
            }
            return
          }
          if (this.vaction === 'KetNoiKetCuoiThieu') {
            if (this.vdoituong_id[0] !== 'KETCUOI') return
            await this.KETNOI_KETCUOI_VAO_CAPGOC_THIEU_DICH(this.vnguon_id, this.vdoituong_id[1])
            return
          }
          if (this.vaction === 'TaoCapMidSpan') {
            if (this.vnguon_id[0] !== this.vdoituong_id[0] || this.vnguon_id[1] !== this.vdoituong_id[1]) {
              this.toast && this.toast.close()
              this.setState({ key: 'vdich_id', value: this.vdoituong_id })
              var donvi_id = null
              var ketcuoi_n_id = null
              var ketcuoi_d_id = null
              var donvi_td_id = null
              if (this.vnguon_id[0] === 'TRAM') {
                donvi_id = this.vnguon_id[1]
              } else {
                ketcuoi_n_id = this.vnguon_id[1]
              }
              if (this.vdich_id[0] === 'TRAM') {
                donvi_td_id = this.vdich_id[1]
              } else {
                ketcuoi_d_id = this.vdich_id[1]
              }
              this.midspanObject.CAP_ID = this.vnguon_id[2]
              this.midspanObject.DONVI_ID = donvi_id
              this.midspanObject.DONVI_TD_ID = donvi_td_id
              this.midspanObject.KETCUOI_N_ID = ketcuoi_n_id
              this.midspanObject.KETCUOI_D_ID = ketcuoi_d_id
              this.midspanObject.KIEUCAP_ID = 0
              this.$bvModal.show('popupQuanLyCapReNhanh')
              this.setState({ key: 'vaction', value: 'None' })
              this.setState({ key: 'vnguon_id', value: null })
              this.setState({ key: 'vdich_id', value: null })
            }
          }
        }
      } else {
        diagramInstance.selectedItems.connectors.length = 0
        diagramInstance.selectedItems.nodes.length = 0
        diagramInstance.selectedItems.connectors = []
        diagramInstance.selectedItems.nodes = []
      }
    },
    async KETNOI_KETCUOI_VAO_CAPGOC_THIEU_DICH(vcap_id, vketcuoi_d_id) {
      console.log('🚀 ~ file: GianDo.vue ~ line 725 ~ KETNOI_KETCUOI_VAO_CAPGOC_THIEU_DICH ~ vcap_id, vketcuoi_d_id', vcap_id, vketcuoi_d_id)
      try {
        var data = {
          VCAP_ID: vcap_id,
          VKETCUOI_D_ID: vketcuoi_d_id
        }
        var rs = await this.$root.context.post('/web-cabman/ban-do-mang-cap/ketnoi_ketcuoi_vao_capgoc_thieu_dich', data)
        if (rs.data.length > 0) {
          EventBus.$emit('NAP_GIANDO_V2', 'NAP_GIANDO_V2')
        }
      } catch (error) {
        console.log('🚀 ~ file: GianDo.vue ~ line 738 ~ KETNOI_KETCUOI_VAO_CAPGOC_THIEU_DICH ~ error', error)
      }
      this.setState({ key: 'vaction', value: 'None' })
      this.setState({ key: 'vnguon_id', value: null })
      this.setState({ key: 'vdich_id', value: null })
    },
    QuanLyTramThietBiIsOk(data) {},
    onItemClick(args) {
      var diagram = document.getElementById('diagramGianDo').ej2_instances[0]
      if (args) {
        switch (args.item.tooltipText) {
          case 'Zoom In':
            var zoomin = { type: 'ZoomIn', zoomFactor: 0.2 }
            diagram.zoomTo(zoomin)
            break
          case 'Zoom Out':
            var zoomout = { type: 'ZoomOut', zoomFactor: 0.2 }
            diagram.zoomTo(zoomout)
            break
          case 'Reset':
            diagram.reset()
            break
        }
      }
    },
    contextMenuClick: function (args) {
      console.log('🚀 ~ file: contextMenuClick GianDo.vue:970 ~ args:', args)
      // KETCUOI
      if (args.element.id === 'MENU_CHUYEN_VAO_TRONG_KC') {
        if (diagramInstance.selectedItems.nodes.length > 0) {
          let id = diagramInstance.selectedItems.nodes[0].id
          console.log('🚀 ~ file: GianDo.vue ~ line 628 ~ diagramInstance.selectedItems.nodes[0]', diagramInstance.selectedItems.nodes[0].annotations[0].content)
          let node = {
            KETCUOI_ID: id,
            KYHIEU_KC: diagramInstance.selectedItems.nodes[0].annotations[0].content
          }
          console.log('🚀 ~ file: GianDo.vue:875 ~ node:', node)
          this.$emit('chuyenvaoTrongTram', node)
        }
        return
      }

      if (args.element.id === 'MENU_DAU_NOI_BEN_TRONG') {
        if (diagramInstance.selectedItems.nodes.length > 0) {
          let id = diagramInstance.selectedItems.nodes[0].id
          let node = {
            KETCUOI_ID: id,
            KYHIEU_KC: diagramInstance.selectedItems.nodes[0].annotations[0].content
          }
          console.log('🚀 ~ file: GianDo.vue:888 ~ node:', node)
          this.$emit('dauNoiBenTrong', node)
        }
        return
      }

      if (args.element.id === 'MENU_THUE_BAO_TREN_KC') {
        if (diagramInstance.selectedItems.nodes.length > 0) {
          let id = diagramInstance.selectedItems.nodes[0].id
          this.$emit('thueBaoThuocKetCuoi', id)
        }
        return
      }

      if (args.element.id === 'MENU_TAO_KC') {
        if (diagramInstance.selectedItems.nodes.length > 0) {
          let id = diagramInstance.selectedItems.nodes[0].id
          this.$emit('taoKetcuoi', id)
        }
        return
      }

      if (args.element.id === 'MENU_TAO_CAP_KC_CUNG_TUYEN') {
        if (diagramInstance.selectedItems.nodes.length > 0) {
          let id = diagramInstance.selectedItems.nodes[0].id
          this.setState({ key: 'vaction', value: 'TaoCap' })
          this.setState({ key: 'vnguon_id', value: id.split('_') })
          this.toast && this.toast.close()
          this.showToast('Tạo cáp đến kết cuối cùng tuyến', 'Chọn kết cuối / trạm thiết bị đích<br><b>Nhấn ESC để hủy<b>')
        }
        return
      }

      if (args.element.id === 'MENU_TAO_CAP_KC_KHAC_TUYEN') {
        if (diagramInstance.selectedItems.nodes.length > 0) {
          let id = diagramInstance.selectedItems.nodes[0].id
          this.$emit('taoCapKetNoiKetCuoiKhacTuyen', id)
        }
        return
      }

      if (args.element.id === 'MENU_XOA_KC') {
        if (diagramInstance.selectedItems.nodes.length > 0) {
          let id = diagramInstance.selectedItems.nodes[0].id
          this.$emit('xoaKetcuoi', id)
        }
        return
      }
      // TRAM
      if (args.element.id === 'MENU_TRAM_DAUNOI_TB') {
        if (diagramInstance.selectedItems.nodes.length > 0) {
          let id = diagramInstance.selectedItems.nodes[0].id
          var doituong = id.split('_')
          this.doiTuong.tramtb_id = doituong[1]
          this.$bvModal.show('gpopupDauNoiTbi')
        }
        return
      }

      if (args.element.id === 'MENU_TRAM_THUOC_TINH_TRAM') {
        if (diagramInstance.selectedItems.nodes.length > 0) {
          let id = diagramInstance.selectedItems.nodes[0].id
          this.$emit('TramThietBi', parseFloat(id.split('_')[1]))
        }
        return
      }
      if (args.element.id === 'MENU_TRAM_TAO_CAP') {
        if (diagramInstance.selectedItems.nodes.length > 0) {
          let id = diagramInstance.selectedItems.nodes[0].id
          var DT = id.split('_')
          this.setState({ key: 'vaction', value: 'TaoCap' })
          this.setState({ key: 'vnguon_id', value: DT })
          this.setState({ key: 'vdich_id', value: null })
          EventBus.$emit('SHOW_TOAST', { s1: 'Chọn kết cuối / trạm thiết bị đích', s2: '<b>Nhấn ESC để hủy</b>' })
        }
        return
      }
      // CAP
      if (args.element.id === 'MENU_CAP_THUOCTINH') {
        if (diagramInstance.selectedItems.connectors.length > 0) {
          let id = diagramInstance.selectedItems.connectors[0].id
          this.$emit('suaCap', id)
        }
        return
      }

      if (args.element.id === 'MENU_CAP_XOA_CAP') {
        if (diagramInstance.selectedItems.connectors.length > 0) {
          let id = diagramInstance.selectedItems.connectors[0].id
          this.$emit('xoaCap', id)
        }
        return
      }

      if (args.element.id === 'MENU_CAP_TAO_MIDSPAN') {
        if (diagramInstance.selectedItems.connectors.length > 0) {
          let id = diagramInstance.selectedItems.connectors[0].id
          let nguon_id = (diagramInstance.selectedItems.connectors[0].targetID + '_' + this.vdoituong_id[1]).split('_')
          this.setState({ key: 'vaction', value: 'TaoCapMidSpan' })
          this.setState({ key: 'vnguon_id', value: nguon_id })
          this.toast && this.toast.close()
          this.showToast('Tạo Mid-Span', 'Chọn kết cuối / trạm thiết bị đích<br><b>Nhấn ESC để hủy<b>')
        }
        return
      }
      if (args.element.id === 'MENU_CAP_DAOCHIEU_CAP') {
        if (diagramInstance.selectedItems.connectors.length > 0) {
          let id = diagramInstance.selectedItems.connectors[0].id
          this.$emit('daochieuCap', id)
        }
        return
      }

      if (args.element.id === 'MENU_CAP_CHEN_KC') {
        if (diagramInstance.selectedItems.connectors.length > 0) {
          let id = diagramInstance.selectedItems.connectors[0].id
          this.$emit('chenketcuoi', id)
        }
        return
      }
      if (args.element.id === 'MENU_CAP_CHUYEN_NHANH_CAP') {
        if (diagramInstance.selectedItems.connectors.length > 0) {
          let id = diagramInstance.selectedItems.connectors[0].id
          this.$emit('chuyennhanhcap', id)
        }
        return
      }

      if (args.element.id === 'MENU_CAP_TB_DIQUA_CAP') {
        if (diagramInstance.selectedItems.connectors.length > 0) {
          let id = diagramInstance.selectedItems.connectors[0].id
          this.$emit('thueBaoThuocCap', id)
        }
        return
      }
      if (args.element.id === 'MENU_CAP_DAUNOI_2DC') {
        if (diagramInstance.selectedItems.connectors.length > 0) {
          let id = diagramInstance.selectedItems.connectors[0].id
          this.$emit('dauNoiCap', id)
        }
        return
      }
      if (args.element.id === 'MENU_THUOC_TINH_CAP') {
        if (diagramInstance.selectedItems.connectors.length > 0) {
          let id = diagramInstance.selectedItems.connectors[0].id
          this.$emit('suaCap', id)
        }
        return
      }
      if (args.element.id === 'MENU_THUOC_TINH_KC') {
        if (diagramInstance.selectedItems.nodes.length > 0) {
          let id = diagramInstance.selectedItems.nodes[0].id
          this.$emit('suaKetcuoi', id)
        }
      }
      if (args.element.id === 'MENU_DI_CHUYEN_TOI_GIANDO_KC_KHACTUYEN') {
        if (diagramInstance.selectedItems.nodes.length > 0) {
          let id = diagramInstance.selectedItems.nodes[0].id.split('_')
          this.TIMKIEM_DOITUONG('KETCUOI|' + id[1] + '|TIM_THEO_ID', 1)
        }
      }
    },
    ds_donvi: async function () {
      try {
        var rs = await this.$root.context.get('/web-cabman/ban-do-mang-cap/danh-sach-dv-nv')
        return rs.data
      } catch (error) {
        return []
      } finally {
      }
    },
    async TIMKIEM_DOITUONG(searchquery, loai) {
      var query = searchquery + '|NGUOIDUNG_ID=' + this.$root.token.getNguoiDungID()
      console.log('🚀 ~ file: index.vue ~ line 400 ~ TIMKIEM_DOITUONG ~ query', query)
      try {
        var rs = await this.$root.context.post('/web-cabman/ban-do-mang-cap/timkiem-doituong-v2', { query: query })
        console.log('🚀 ~ file: index.vue ~ line 405 ~ TIMKIEM_DOITUONG ~ rs', rs)
        if (rs.data.dsDOITUONG.length === 0) {
          await this.$alert('Không tìm thấy tuyến cáp của đối tượng', 'Thông báo', {
            confirmButtonText: 'Đóng',
            type: 'error'
          })
          return
        } else {
          // Kiểm tra quyền thao tác với tuyến của tổ kỹ thuật khác
          if (this.ds_dv.length === 0) {
            this.ds_dv = await this.ds_donvi()
          }
          if (rs.data.dsTOKT.length > 0 && this.ds_dv.length > 0) {
            var tmp = rs.data.dsTOKT.filter((e) => {
              return e.SELECTED === 1
            })
            if (tmp.length > 0) {
              var isPermitted = false
              for (let index = 0; index < tmp.length; index++) {
                const DONVI_ID = tmp[index].ID
                const idx = this.ds_dv.findIndex((e) => e.DONVI_ID === DONVI_ID)
                if (idx > -1) {
                  isPermitted = true
                  break
                }
              }
              if (!isPermitted) {
                await this.$alert('Đối tượng thuộc quản lý của tổ kỹ thuật ' + tmp[0].NAME + ', bạn không có quyền thao tác.', 'Thông báo', {
                  confirmButtonText: 'Đóng',
                  type: 'error'
                })
                return
              }
            }
          }
          // End Kiểm tra quyền thao tác với tuyến của tổ kỹ thuật khác
          var dsDOITUONG = rs.data.dsDOITUONG
          console.log('🚀 ~ file: GianDo.vue:1236 ~ TIMKIEM_DOITUONG ~ dsDOITUONG:', dsDOITUONG)
          if (dsDOITUONG[0].NHOMLC_ID === 0) {
            this.set_nhomlc(0)
          } else {
            this.set_nhomlc(1)
          }
          EventBus.$emit('SET_STATE', { dsTTVTS: rs.data.dsTTVTS, dsTOKT: rs.data.dsTOKT, dsTONGDAI: rs.data.dsTONGDAI, dsDOITUONG: rs.data.dsDOITUONG, dsDANDO_VEGOC: rs.data.dsDANDO_VEGO })
        }
      } catch (error) {
        console.log('TIMKIEM_DOITUONG', error.response)
        this.$alert(error.response.data.message_detail, error.response.data.message, {
          confirmButtonText: 'Đóng',
          type: 'error'
        })
      }
    },
    ShowToast(s1, s2) {
      this.toast && this.toast.close()
      this.showToast(s1, s2)
    },
    ShowToast2(a) {
      this.toast && this.toast.close()
      this.showToast(a.s1, a.s2)
    },
    optionClicked(event) {
      switch (event.option.slug) {
        case 'thuoc_tinh_ket_cuoi':
          this.$emit('suaKetcuoi', event.item.id)
          break
        case 'thuoc_tinh_cap':
          this.$emit('suaCap', event.item.id)
          break
      }
    },
    onDbClick(args) {
      const id = args['source'].id
      if (id) {
        console.log('onDbClick', id)
        if (id.startsWith('CAP_')) {
          this.$emit('suaCap', id)
        }
        if (id.startsWith('KETCUOI_')) {
          this.$emit('suaKetcuoi', id)
        }
        if (id.startsWith('KETCUOIKHACTUYEN_')) {
          this.$emit('xemKetcuoi', id)
        }
        if (id.startsWith('TRAM_')) {
          this.$emit('TramThietBi', parseFloat(id.split('_')[1]))
        }
      }
    },
    setNodes(nodes) {
      this.nodes = nodes
    },
    setConnectors(connectors) {
      this.connectors = connectors
    },
    setRefresh() {
      this.refresh = !this.refresh
    },
    mouseOver: (args) => {
      //console.log('🚀 ~ file: GianDo.vue:1214 ~ args:', args)
      let vm = this
      try {
        if (vm == undefined || args == undefined || args.actualObject == undefined || args.actualObject.constructor == undefined) return

        vm.actualObject = args.actualObject
        if (vm.actualObject.constructor.name === 'Connector') {
          let connect = diagramInstance.connectors.find((x) => x.id === vm.actualObject.id)
          if (connect) {
            connect.style.strokeColor = 'red'
          }
        }
      } catch (error) {
        console.log('🚀 ~ file: GianDo.vue:1224 ~ error:', error)
      }
    },
    mouseLeave: (args) => {
      //console.log('🚀 ~ file: GianDo.vue:1224 ~ args:', args)
      let vm = this
      try {
        if (vm == undefined || args == undefined || args.actualObject == undefined || args.actualObject.constructor == undefined) return

        vm.actualObject = args.element
        if (vm.actualObject.constructor.name === 'Connector') {
          let connect = diagramInstance.connectors.find((x) => x.id === vm.actualObject.id)
          if (connect) {
            connect.style.strokeColor = connect.styleCap.strokeColor
          }
        }
      } catch (error) {
        console.log('🚀 ~ file: GianDo.vue:1238 ~ error:', error)
      }
    },
    showToast(title, msg) {
      this.toast = this.$notify.success({
        title: title,
        message: msg,
        showClose: false,
        dangerouslyUseHTMLString: true,
        duration: 0
      })
      console.log('this.toast', this.toast)
    },
    hideToast() {
      try {
        this.toast && this.toast.close()
        diagramInstance.selectedItems.connectors = []
        diagramInstance.selectedItems.nodes = []
        this.setState({ key: 'vaction', value: 'None' })
        this.setState({ key: 'vnguon_id', value: null })
        this.setState({ key: 'vdich_id', value: null })
      } catch (error) {}
    },
    async HIENTHIGIANDOTREELIST(title) {
      try {
        var data = []
        var edges = diagramInstance.connectors
        edges.forEach((edge) => {
          console.log('🚀 ~ file: GianDo.vue ~ line 1014 ~ HIENTHIGIANDOTREELIST ~ edge', edge)
          let row = {
            ID: null,
            PARENT_ID: null,
            NAME: null
          }
          row.ID = edge.id
          let srcNode = diagramInstance.getObject(edge.sourceID)
          let desNode = diagramInstance.getObject(edge.targetID)
          let sedges = diagramInstance.connectors
          let s1 = edge.tenKyHieuCap.content
          let s2 = srcNode.tooltip.content
          let s3 = desNode.tooltip.content
          row.NAME = s1 + ' | ' + s2 + ' --> ' + s3
          row.NAME = row.NAME.replaceAll('<strong>', '')
          row.NAME = row.NAME.replaceAll('</strong>', '')
          row.NAME = row.NAME.replaceAll('<b>', '')
          row.NAME = row.NAME.replaceAll('</b>', '')
          row.NAME = row.NAME.replaceAll('<br>', ' ')
          var parentID = 'NaN'
          for (let index = 0; index < sedges.length; index++) {
            const element = sedges[index]
            if (edge.sourceID === element.targetID) {
              parentID = element.id
              break
            }
          }
          if (parentID !== 'NaN') {
            row.PARENT_ID = parentID
            if (row.PARENT_ID === row.ID) {
              row.ID = row.ID + row.PARENT_ID
            }
          }
          data.push(row)
        })
        this.dataGianDoTreeList = data
        var result = await this.$refs.ModalGianDoTreeList.show(title, this.dataGianDoTreeList)
      } catch (error) {
        console.log('🚀 ~ file: GianDo.vue ~ line 1041 ~ HIENTHIGIANDOTREELIST ~ error', error)
      }
    },
    inGiando(file) {
      let printOptions = {}
      printOptions.mode = 'Data'
      printOptions.region = 'PageSettings'
      printOptions.multiplePage = false
      printOptions.pageHeight = 800
      printOptions.pageWidth = 800
      diagramInstance.print(printOptions)
    },
    addNode(Node) {
      diagramInstance.addNode(Node)
    },
    addConnector(Connector) {
      let conn = diagramInstance.addConnector(Connector);
        if(!(conn.sourceID == undefined || conn.sourceID == null || conn.sourceID == "")){
          let source = diagramInstance.nodes.find((x) => x.id == conn.sourceID);
          if(source != null){
            if(!source.inEdges.includes(conn.id))
            source.inEdges.push(conn.id);
          }
        }
        if(!(conn.targetID == undefined || conn.targetID == null || conn.targetID == "")){
          let target = diagramInstance.nodes.find((x) => x.id == conn.targetID);
          if(target != null){
            if(!target.outEdges.includes(conn.id))
            target.outEdges.push(conn.id);
          }
        }
    },
    add(obj) {
      try {
        diagramInstance.add(obj)
      } catch (error) {
        console.log('add', error)
      }
    },
    doLayout(enableRouting, connectors_noidai) {
      //console.log('🚀 ~ file: GianDo.vue:1342 ~ doLayout ~ enableRouting:', enableRouting)
      diagramInstance.layout.enableRouting = enableRouting
      // diagramInstance.fitToPage()
      diagramInstance.dataBind()
      try{
        diagramInstance.doLayout()
      } catch(e){

      }
      // setTimeout(() => {
      //   diagramInstance.doLayout()
      // }, 0)
      if (connectors_noidai.length > 0) {
        //console.log('🚀 ~ file: GianDo.vue:1329 ~ doLayout ~ connectors_noidai:', connectors_noidai)
        let m = (connectors_noidai.length / 2).toFixed()
        //console.log('🚀 ~ file: GianDo.vue:1331 ~ doLayout ~ m:', m)
        for (let index = 0; index < connectors_noidai.length; index++) {
          const element = connectors_noidai[index]
          let connector = diagramInstance.getObject(element.id)
          //console.log('🚀 ~ file: GianDo.vue:1333 ~ doLayout ~ connector:', connector)
          if (connector) {
            if (connector.targetID.startsWith('TRAM_') && connector.sourceID.startsWith('TRAM_')) {
              connector.targetDecorator = {
                shape: 'None'
              }
            }
            if (index < m) {
              let length_r = (index + 1) * 15
              let length_b = (index + 1) * 100
              connector.segments = [
                {
                  type: 'Orthogonal',
                  direction: 'Right',
                  length: length_r
                },
                {
                  type: 'Orthogonal',
                  direction: 'Bottom',
                  length: length_b
                }
              ]
            } else {
              let length_r = (index - m + 1) * 15
              let length_b = (index - m + 1) * 100
              connector.segments = [
                {
                  type: 'Orthogonal',
                  direction: 'Right',
                  length: length_r
                },
                {
                  type: 'Orthogonal',
                  direction: 'Top',
                  length: length_b
                }
              ]
            }
          }
        }
        diagramInstance.dataBind()
      }
      diagramInstance.fitToPage({
        mode: 'Width'
      })
      // this.zoomChange('100%')
    },
    doLayoutV2(nodes, connectors, enableRouting) {
      //console.log('🚀 ~ file: GianDo.vue:1398 ~ doLayoutV2 ~ enableRouting:', enableRouting)
      if (enableRouting === undefined) {
        enableRouting = true
      }
      diagramInstance.layout.enableRouting = enableRouting
      diagramInstance.dataBind()
      diagramInstance.nodes = nodes
      diagramInstance.connectors = connectors
      setTimeout(() => {
        diagramInstance.doLayout()
      }, 0)
      diagramInstance.fitToPage({
        mode: 'Width'
      })
    },
    clear() {
      diagramInstance.clear()
    },
    saveDiagram() {
      let _saveDiagram = diagramInstance.saveDiagram()
      return _saveDiagram
    },
    setScrollSettings(data) {
      try {
        let scrollSettings = data.scrollSettings
        //console.log('🚀 ~ file: GianDo.vue:1271 ~ setScrollSettings ~ scrollSettings:', scrollSettings)
        // diagramInstance.reset()
        // // // diagramInstance.dataBind()
        // diagramInstance.scrollSettings.horizontalOffset = (scrollSettings.horizontalOffset)
        // diagramInstance.scrollSettings.verticalOffset = (scrollSettings.verticalOffset)
        // diagramInstance.scrollSettings.viewPortHeight = (scrollSettings.viewPortHeight)
        // diagramInstance.scrollSettings.viewPortWidth = (scrollSettings.viewPortWidth)
        // let zoomOptions = { x: 100,
        //   y: 100}
        // diagramInstance.zoom(scrollSettings.currentZoom, zoomOptions)
      } catch (error) {
        console.log('🚀 ~ file: GianDo.vue:1280 ~ setScrollSettings ~ error:', error)
      }
    },
    zoomIn() {
      const zoomOptions = {
        type: 'ZoomIn',
        zoomFactor: 0.2
      }
      diagramInstance.zoomTo(zoomOptions)
    },
    zoomOut() {
      const zoomOptions = {
        type: 'ZoomOut',
        zoomFactor: 0.2
      }
      diagramInstance.zoomTo(zoomOptions)
    },
    zoomChange(text) {
      console.log('🚀 ~ file: GianDo.vue:1458 ~ zoomChange ~ text:', text)
      if (text === 'Fit To Screen') {
        diagramInstance.fitToPage({ mode: 'Page', region: 'Content', margin: { left: 0, top: 0, right: 0, bottom: 0 } })
        return
      }
      let currentZoom = diagramInstance.scrollSettings.currentZoom
      let zoomOptions = {}
      if (text === '400%') {
        zoomOptions.zoomFactor = 4 / currentZoom - 1
      }
      if (text === '300%') {
        zoomOptions.zoomFactor = 3 / currentZoom - 1
      }
      if (text === '200%') {
        zoomOptions.zoomFactor = 2 / currentZoom - 1
      }
      if (text === '150%') {
        zoomOptions.zoomFactor = 1.5 / currentZoom - 1
      }
      if (text === '100%') {
        zoomOptions.zoomFactor = 1 / currentZoom - 1
      }
      if (text === '75%') {
        zoomOptions.zoomFactor = 0.75 / currentZoom - 1
      }
      if (text === '50%') {
        zoomOptions.zoomFactor = 0.5 / currentZoom - 1
      }
      if (text === '25%') {
        zoomOptions.zoomFactor = 0.25 / currentZoom - 1
      }
      diagramInstance.zoomTo(zoomOptions)
    },
    showDoiTuong(isVisible) {
      diagramInstance.nodes.forEach((item) => {
        item.visible = isVisible;
      });
      diagramInstance.dataBind();
    },
    showLienKetKhac124896(isVisible) {
      diagramInstance.connectors.forEach((item) => {
        if(!(item.dungLuong == 12 || item.dungLuong == 48 || item.dungLuong == 96)){
          item.visible = isVisible;
        }
      });
      diagramInstance.dataBind();
    },
    showLienKet12(isVisible) {
      diagramInstance.connectors.forEach((item) => {
        if(item.dungLuong == 12){
          item.visible = isVisible;
        }
      });
      diagramInstance.dataBind();
    },
    showLienKet48(isVisible) {
      diagramInstance.connectors.forEach((item) => {
        if(item.dungLuong == 48){
          item.visible = isVisible;
        }
      });
      diagramInstance.dataBind();
    },
    showLienKet96(isVisible) {
      diagramInstance.connectors.forEach((item) => {
        if(item.dungLuong == 96){
          item.visible = isVisible;
        }
      });
      diagramInstance.dataBind();
    },
    showTitle(isVisible) {
      diagramInstance.nodes.forEach((item) => {
        item.annotations[0].visibility = isVisible;
      });
      diagramInstance.connectors.forEach((item) => {
        item.annotations.forEach((i) => {
          i.visibility = isVisible;
        });
      });
      diagramInstance.dataBind();
    },
    taoNode: function (nodeID, text, stringStyle, tooltip, insideLabel) {
      try {
        let annotations2 = []
        let text2 = tooltip.replaceAll('<br>', '\n').replaceAll('<strong>', '').replaceAll('</strong>', '').replaceAll('<b>', '').replaceAll('</b>', '');
        let ss = text2.split("\n");
        let txt = "";
        if(ss.length > 1){
          txt = "<div class='text-nowrap' style='font-size: 14px; font-weight: bold; overflow: hidden; text-overflow: ellipsis;'>"+ss[0]+"</div>";
          txt = txt + "<div class='text-nowrap' style='overflow: hidden; text-overflow: ellipsis;'>"+ss[1]+"</div>";
        }
        txt = "<div style='opacity: 0.8; font-family: Arial; font-size: 12px; padding: 5px 7px; border: 1px solid #b5b5b5; background-color: #EFECE7; border-radius: 5px; text-align: center;'>" + txt + "</div>";
        annotations2.push({
          template: txt,
          margin: { top: 24 },
          width: 170,
          height: 49,
          offset:{
            x: 0.5,
            y: 1
          }
        });
        let node = DiagramLib.taoNodeTheoStyle(stringStyle, nodeID, null, annotations2)
        node.height = 64
        node.width = 64
        if (stringStyle === '5') {
        }
        if (stringStyle === '6' || stringStyle === 'TU_QUANG_SPLITTER') {
          if (insideLabel === 'S1') {
            let iconFile = 'T(S1).png'
            node.shape = {
              type: 'Image',
              source: '/static/icons/icon100x100/' + iconFile,
              scale: 'None'
            }
          }

          if (insideLabel === 'S2') {
            let iconFile = 'T(S1).png'
            node.shape = {
              type: 'Image',
              source: '/static/icons/icon100x100/' + iconFile,
              scale: 'None'
            }
          }

          if (insideLabel === '1') {
            let iconFile = 'TU_QUANG_SPLITTER_1.jpg'
            node.shape = {
              type: 'Image',
              source: '/static/icons/icon100x100/' + iconFile,
              scale: 'None'
            }
          }

          if (insideLabel === '2') {
            let iconFile = 'TU_QUANG_SPLITTER_2.jpg'
            node.shape = {
              type: 'Image',
              source: '/static/icons/icon100x100/' + iconFile,
              scale: 'None'
            }
          }

          if (insideLabel === '3') {
            let iconFile = 'TU_QUANG_SPLITTER_3.jpg'
            node.shape = {
              type: 'Image',
              source: '/static/icons/icon100x100/' + iconFile,
              scale: 'None'
            }
          }
          if (insideLabel === '4') {
            let iconFile = 'TU_QUANG_SPLITTER_4.jpg'
            node.shape = {
              type: 'Image',
              source: '/static/icons/icon100x100/' + iconFile,
              scale: 'None'
            }
          }

          if (insideLabel === '5') {
            let iconFile = 'TU_QUANG_SPLITTER_5.jpg'
            node.shape = {
              type: 'Image',
              source: '/static/icons/icon100x100/' + iconFile,
              scale: 'None'
            }
          }
          if (insideLabel === '6') {
            let iconFile = 'TU_QUANG_SPLITTER_6.jpg'
            node.shape = {
              type: 'Image',
              source: '/static/icons/icon100x100/' + iconFile,
              scale: 'None'
            }
          }
          if (insideLabel === '7') {
            let iconFile = 'TU_QUANG_SPLITTER_7.jpg'
            node.shape = {
              type: 'Image',
              source: '/static/icons/icon100x100/' + iconFile,
              scale: 'None'
            }
          }
          if (insideLabel === '8') {
            let iconFile = 'TU_QUANG_SPLITTER_8.jpg'
            node.shape = {
              type: 'Image',
              source: '/static/icons/icon100x100/' + iconFile,
              scale: 'None'
            }
          }
          if (insideLabel === '9') {
            let iconFile = 'TU_QUANG_SPLITTER_9.jpg'
            node.shape = {
              type: 'Image',
              source: '/static/icons/icon100x100/' + iconFile,
              scale: 'None'
            }
          }

          if (insideLabel === '9') {
            let iconFile = 'TU_QUANG_SPLITTER_9.jpg'
            node.shape = {
              type: 'Image',
              source: '/static/icons/icon100x100/' + iconFile,
              scale: 'None'
            }
          }

          if (insideLabel === '9') {
            let iconFile = 'TU_QUANG_SPLITTER_9.jpg'
            node.shape = {
              type: 'Image',
              source: '/static/icons/icon100x100/' + iconFile,
              scale: 'None'
            }
          }

          if (insideLabel === '10') {
            let iconFile = 'TU_QUANG_SPLITTER_10.jpg'
            node.shape = {
              type: 'Image',
              source: '/static/icons/icon100x100/' + iconFile,
              scale: 'None'
            }
          }

          if (insideLabel === '11') {
            let iconFile = 'TU_QUANG_SPLITTER_11.jpg'
            node.shape = {
              type: 'Image',
              source: '/static/icons/icon100x100/' + iconFile,
              scale: 'None'
            }
          }

          if (insideLabel === '12') {
            let iconFile = 'TU_QUANG_SPLITTER_12.jpg'
            node.shape = {
              type: 'Image',
              source: '/static/icons/icon100x100/' + iconFile,
              scale: 'None'
            }
          }

          if (insideLabel === '13') {
            let iconFile = 'TU_QUANG_SPLITTER_13.jpg'
            node.shape = {
              type: 'Image',
              source: '/static/icons/icon100x100/' + iconFile,
              scale: 'None'
            }
          }

          if (insideLabel === '14') {
            let iconFile = 'TU_QUANG_SPLITTER_14.jpg'
            node.shape = {
              type: 'Image',
              source: '/static/icons/icon100x100/' + iconFile,
              scale: 'None'
            }
          }

          if (insideLabel === '15') {
            let iconFile = 'TU_QUANG_SPLITTER_15.jpg'
            node.shape = {
              type: 'Image',
              source: '/static/icons/icon100x100/' + iconFile,
              scale: 'None'
            }
          }
        }

        node.tooltip = {
          content: tooltip,
          position: 'TopCenter',
        }
        return node
      } catch (error) {
        console.log('🚀 ~ file: index.vue:838 ~ error:', error)
      }
    },
  },
  updated() {
    diagramInstance.refresh()
  },
  async created() {
    EventBus.$on('IN_GIANDO', this.inGiando)
    EventBus.$on('GIANDO_ZOOMIN', this.zoomIn)
    EventBus.$on('GIANDO_ZOOMOUT', this.zoomOut)
    EventBus.$on('GIANDO_ZOOM', this.zoomChange)
    EventBus.$on('SHOW_TOAST', this.ShowToast2)
    EventBus.$on('HIDE_TOAST', this.hideToast)
    EventBus.$on('HIENTHI_GIANDO_TREELIST', this.HIENTHIGIANDOTREELIST)
    EventBus.$on('ACTIVE_OBJECT', this.ACTIVE_OBJECT)
    EventBus.$on('ACTIVE_OBJECT_V2', this.ACTIVE_OBJECT_V2)
    let that = this
    document.addEventListener('keyup', function (evt) {
      if (evt.keyCode === 27) {
        that.hideToast()
      }
    })
  },
  destroyed() {
    EventBus.$off('IN_GIANDO', this.inGiando)
    EventBus.$off('GIANDO_ZOOMIN', this.zoomIn)
    EventBus.$off('GIANDO_ZOOMOUT', this.zoomOut)
    EventBus.$off('GIANDO_ZOOM', this.zoomChange)
    EventBus.$off('SHOW_TOAST', this.ShowToast2)
    EventBus.$off('HIDE_TOAST', this.hideToast)
    EventBus.$off('HIENTHI_GIANDO_TREELIST', this.HIENTHIGIANDOTREELIST)
    EventBus.$off('ACTIVE_OBJECT', this.ACTIVE_OBJECT)
    EventBus.$off('ACTIVE_OBJECT_V2', this.ACTIVE_OBJECT_V2)
  }
})
</script>
