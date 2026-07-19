<template src="./index.html"></template>
<script>
import CayHaTang from './CayHaTang/index.vue'
import Enum from './Enum'
import { debounce } from 'lodash'
import VietNamMap from './MapComponent/index.vue'
import DanhSachIDC from './QuanLyIDC/DanhSachIDC/index.vue'
import api from './api'
import * as XLSX from 'xlsx'

// Detail/View Components
const TabsMatSan = () => import(/* webpackChunkName: "sodomatsan" */ '../sodo-matsan/tabs-matsan.vue')
const TabsPhongZone = () => import(/* webpackChunkName: "sodophongzone" */ '../sodo-phong-zone/tabs-phong-zone.vue')
const ComponentChiTietToaNha = () =>
  import(/* webpackChunkName: "chitiettoanha" */ '../thongtin-chitiet-toanha/components/ModalThongTinToaNha/index.vue')
const ComponentChiTietRack = () =>
  import(/* webpackChunkName: "chitietrack" */ '../sodo-phong-zone/components/tab-content/RackDetailContent.vue')
const TabsIDC = () => import(/* webpackChunkName: "tabsidc" */ './QuanLyIDC/TabsIDC.vue')

// Add/Create Form Components
const FormThemToaNha = () => import(/* webpackChunkName: "formtoanha" */ '../danhmuc-toanha/popup/index.vue')
const FormThemIDC = () => import(/* webpackChunkName: "formidc" */ '../danhmuc-idc/components/DataForm/DataForm.vue')
const FormThemMatSan = () =>
  import(/* webpackChunkName: "formmatsan" */ '../DanhMucMatSan/components/MatSanDetailForm/MatSanDetailForm.vue')
const FormThemPhongZone = () =>
  import(
    /* webpackChunkName: "formphongzone" */ '../thongtin-phong-zone/components/PhongZoneDetailForm/PhongZoneDetailForm.vue'
  )
const FormThemRack = () => import(/* webpackChunkName: "formrack" */ '../ThongTinRack/components/thongtin-rack.vue')

export default {
  components: {
    CayHaTang,
    TabsMatSan,
    TabsPhongZone,
    ComponentChiTietToaNha,
    ComponentChiTietRack,
    TabsIDC,
    VietNamMap,
    DanhSachIDC,
    FormThemIDC,
    FormThemToaNha,
    FormThemMatSan,
    FormThemPhongZone,
    FormThemRack
  },
  data () {
    return {
      nodeSelected: {
        id: null,
        text: null,
        parentID: null,
        icon: null,
        hasChildren: null,
        attributes: {
          id: null,
          type: 'none',
          text: null,
          parentID: null,
          parentType: null
        }
      },
      componentKey: 0,
      // === MAP === //
      markerMode: false,
      idcList: [],
      activeMapTab: 'map',
      // === IDC Detail === //
      showIdcDetail: false,
      selectedIdcId: null,
      selectedIdcData: null,
      // === RACK Detail === //
      selectedRackDetail: null,
      // === DOM Detection === //
      hasListActionsTopInDOM: false,
      domObserver: null,
      detectListActionsTopDebounced: null,
      // === MODAL === //
      showAddModal: false,
      modalComponentKey: 0,
      // === MODAL CHILD === //
      showAddChildModal: false,
      modalChildComponentKey: 0,
      modalChildMode: 'ADD',
      // === LOADING === //
      isLoading: false,
      modalMode: 'ADD' // 'ADD' or 'EDIT'
    }
  },
  watch: {
    nodeSelected: {
      deep: true,
      handler: async function (newNode) {
        console.log('nodeSelected changed:', newNode)
        if (newNode.attributes.type === 'RACK') {
          this.componentKey += 1
          await this.$nextTick()

          // Set rack detail data
          this.selectedRackDetail = {
            id: newNode.attributes.id,
            name: newNode.text || newNode.attributes.text,
            type: newNode.attributes.rackType,
            totalU: newNode.attributes.totalU,
            usedU: newNode.attributes.usedU || 0,
            availableU: (newNode.attributes.totalU || 0) - (newNode.attributes.usedU || 0),
            ...newNode.attributes
          }
          console.log('selectedRackDetail set:', this.selectedRackDetail)
        }
      }
    },

    currentComponent: {
      immediate: true,
      handler: debounce(async function (newComponent, oldComponent) {
        if (newComponent !== oldComponent) {
          this.componentKey += 1

          await this.$nextTick()

          if (this.$refs.dynamicComponent) {
            try {
              await this.loadComponentData()
              // Detect list-actions-top trong DOM sau khi component load
              await this.detectListActionsTop()
            } catch (error) {
              this.handleError(error)
            }
          }
        }
      }, 250)
    },

    isMapLarge (newVal) {
      if (!newVal) {
        this.activeMapTab = 'map'
      }
    }
  },
  mounted () {
    // Create debounced version of detectListActionsTop
    this.detectListActionsTopDebounced = debounce(this.detectListActionsTop, 300)

    // Observe DOM changes để detect list-actions-top khi tab thay đổi
    this.$nextTick(() => {
      this.domObserver = new MutationObserver(() => {
        this.detectListActionsTopDebounced()
      })

      const targetNode = this.$el
      if (targetNode) {
        this.domObserver.observe(targetNode, {
          childList: true,
          subtree: true,
          attributes: false // Không cần observe attributes để giảm số lần trigger
        })
      }

      this.$once('hook:beforeDestroy', () => {
        if (this.domObserver) {
          this.domObserver.disconnect()
        }
      })
    })
  },
  computed: {
    currentComponent () {
      const componentMap = {
        MATSAN: 'TabsMatSan',
        ZONE: 'TabsPhongZone',
        TOANHA: 'ComponentChiTietToaNha',
        RACK: 'ComponentChiTietRack',
        IDC: 'TabsIDC'
      }
      return componentMap[this.nodeSelected.attributes.type] || null
    },
    isMapLarge () {
      return this.nodeSelected?.attributes?.type === 'none'
    },
    hasListActionsTop () {
      return this.hasListActionsTopInDOM
    },
    // Modal Component Mapping
    modalComponent () {
      const modalComponentMap = {
        IDC: 'FormThemIDC',
        MATSAN: 'FormThemMatSan',
        ZONE: 'FormThemPhongZone',
        TOANHA: 'FormThemToaNha',
        RACK: 'FormThemRack'
      }
      return modalComponentMap[this.nodeSelected.attributes.type] || null
    },
    modalTitle () {
      const titleMap = {
        MATSAN:
          this.modalMode === 'ADD'
            ? 'Nhập mới Mặt sàn'
            : this.modalMode === 'EDIT'
            ? 'Chỉnh sửa Mặt sàn'
            : 'Thông tin Mặt sàn',
        ZONE:
          this.modalMode === 'ADD'
            ? 'Nhập mới Phòng/Zone'
            : this.modalMode === 'EDIT'
            ? 'Chỉnh sửa Phòng/Zone'
            : 'Thông tin Phòng/Zone',
        TOANHA:
          this.modalMode === 'ADD'
            ? 'Nhập mới Tòa nhà'
            : this.modalMode === 'EDIT'
            ? 'Chỉnh sửa Tòa nhà'
            : 'Thông tin Tòa nhà',
        RACK:
          this.modalMode === 'ADD' ? 'Nhập mới Rack' : this.modalMode === 'EDIT' ? 'Chỉnh sửa Rack' : 'Thông tin Rack',
        IDC: this.modalMode === 'ADD' ? 'Nhập mới IDC' : this.modalMode === 'EDIT' ? 'Chỉnh sửa IDC' : 'Thông tin IDC'
      }
      return titleMap[this.nodeSelected.attributes.type] || 'Nhập mới '
    },
    newItemLabel () {
      const labelMap = {
        MATSAN: 'Mặt sàn',
        ZONE: 'Phòng/Zone',
        TOANHA: 'Tòa nhà',
        RACK: 'Rack',
        IDC: 'IDC'
      }
      return labelMap[this.nodeSelected.attributes.type] || 'Item'
    },
    // Child type mapping: Parent Type -> Child Type
    childTypeMap () {
      return {
        IDC: 'TOANHA',
        TOANHA: 'MATSAN',
        MATSAN: 'ZONE',
        ZONE: 'RACK'
      }
    },
    // Check if current node can have children
    canAddChild () {
      return !!this.childTypeMap[this.nodeSelected.attributes.type]
    },
    // Get child type for current node
    childType () {
      return this.childTypeMap[this.nodeSelected.attributes.type] || null
    },
    // Modal component for child
    modalChildComponent () {
      const modalComponentMap = {
        MATSAN: 'FormThemMatSan',
        ZONE: 'FormThemPhongZone',
        TOANHA: 'FormThemToaNha',
        RACK: 'FormThemRack'
      }
      return modalComponentMap[this.childType] || null
    },
    // Modal title for child
    modalChildTitle () {
      const titleMap = {
        MATSAN: 'Nhập mới Mặt sàn',
        ZONE: 'Nhập mới Phòng/Zone',
        TOANHA: 'Nhập mới Tòa nhà',
        RACK: 'Nhập mới Rack'
      }
      return titleMap[this.childType] || 'Nhập mới'
    },
    // Label for add child button
    addChildButtonLabel () {
      const labelMap = {
        TOANHA: 'Nhập mới Tòa nhà',
        MATSAN: 'Nhập mới Mặt sàn',
        ZONE: 'Nhập mới Phòng/Zone',
        RACK: 'Nhập mới Rack'
      }
      return labelMap[this.childType] || 'Nhập mới'
    }
  },
  created: function () {
    this.loadData()
  },
  methods: {
    // ===================== MODAL METHODS ===================== //
    onAddNewItemClick () {
      this.modalComponentKey += 1
      this.modalMode = 'ADD'
      this.showAddModal = true
    },
    onEditItemClick () {
      this.modalComponentKey += 1
      this.modalMode = 'EDIT'
      this.showAddModal = true
    },
    onAddChildClick () {
      this.modalChildComponentKey += 1
      this.modalChildMode = 'ADD'
      this.showAddChildModal = true
    },
    getNewItemLabel () {
      return `Nhập mới ${this.newItemLabel}`
    },
    getEditItemLabel () {
      return `Chỉnh sửa ${this.newItemLabel}`
    },
    handleModalClose () {
      this.showAddModal = false
    },
    handleModalChildClose () {
      this.showAddChildModal = false
    },
    handleModalSave (data) {
      console.log('Saving data:', data)
      // Xử lý save logic ở đây
      // Có thể emit event hoặc gọi API
      this.$emit('item-created', {
        type: this.nodeSelected.attributes.type,
        data: data
      })

      // Reload data sau khi save
      this.reloadCurrentComponent()

      // Close modal
      this.showAddModal = false
    },
    handleModalChildSave (data) {
      console.log('Saving child data:', data)
      // Emit event với child type
      this.$emit('child-item-created', {
        parentType: this.nodeSelected.attributes.type,
        childType: this.childType,
        parentId: this.nodeSelected.attributes.id,
        data: data
      })

      // Reload data sau khi save
      this.reloadCurrentComponent()

      // Close modal
      this.showAddChildModal = false
    },
    reloadCurrentComponent () {
      // Force re-render component hiện tại bằng cách tăng componentKey
      this.componentKey += 1

      // Reload lại component hiện tại
      const dynamicComponent = this.$refs.dynamicComponent
      if (dynamicComponent && dynamicComponent.loadData) {
        this.$nextTick(() => {
          dynamicComponent.loadData(this.nodeSelected.attributes.id)
        })
      }

      // Hoặc reload cây hạ tầng
      if (this.$refs.caySoDoThietBi && this.$refs.caySoDoThietBi.refreshTree) {
        this.$refs.caySoDoThietBi.refreshTree(this.nodeSelected.id)
      }
    },

    // ===================== EXISTING METHODS ===================== //
    cayHaTang_nodeSelected: function (args) {
      this.nodeSelected = args
      console.log('nodeSelected full data:', this.nodeSelected)
    },
    async loadComponentData () {
      const dynamicComponent = this.$refs.dynamicComponent

      if (!dynamicComponent) {
        console.warn('Không tìm thấy dynamic component sau khi đợi')
        return
      }

      console.log('Component hiện tại:', {
        type: this.nodeSelected.type,
        component: dynamicComponent,
        methods: Object.keys(dynamicComponent),
        props: dynamicComponent.$props
      })

      try {
        await this.invokeComponentMethod(dynamicComponent)
      } catch (error) {
        console.error(`Lỗi khi load dữ liệu cho node type ${this.nodeSelected.type}:`, error)
        throw error
      }
    },
    handleError (error) {
      this.error = error.message || 'Có lỗi xảy ra khi tải dữ liệu'
      console.error('Error in handleNodeSelected:', error)
    },

    async invokeComponentMethod (dynamicComponent) {
      const type = this.nodeSelected.attributes.type

      // RACK đã được xử lý trong watcher, không cần làm gì thêm
      if (type === 'RACK') {
        return
      }

      // Các component khác có thể có method loadData hoặc tương tự
      if (dynamicComponent && dynamicComponent.loadData) {
        await dynamicComponent.loadData(this.nodeSelected.attributes.id)
      }
    },

    // ===================== MAP UTILITIES ===================== //

    async loadData () {
      try {
        const rs = await api.getDanhSachIdc(this.axios)
        console.log('Raw API data:', rs)
        this.idcList = this.injectMockCoordinates(rs)
        console.log('Data with mock coordinates:', this.idcList)
      } catch (error) {
        console.log('Lỗi khi tải dữ liệu', error)
      }
    },

    generateRandomVietnamCoordinates () {
      // Vietnam coordinates bounds
      const minLat = 8.5 // Southernmost point
      const maxLat = 23.4 // Northernmost point
      const minLng = 102.1 // Westernmost point
      const maxLng = 109.5 // Easternmost point

      return {
        lat: (Math.random() * (maxLat - minLat) + minLat).toFixed(6),
        lng: (Math.random() * (maxLng - minLng) + minLng).toFixed(6)
      }
    },

    injectMockCoordinates (apiData) {
      if (!Array.isArray(apiData)) {
        console.warn('API data is not an array:', apiData)
        return []
      }

      return apiData.map((item, index) => {
        // Check if item already has coordinates
        const hasLat = item.TOADO_Y
        const hasLng = item.TOADO_X

        // If no coordinates, inject random Vietnam coordinates
        if (!hasLat || !hasLng) {
          const coords = this.generateRandomVietnamCoordinates()
          return {
            ...item,
            LAT: parseFloat(coords.lat),
            LNG: parseFloat(coords.lng)
          }
        }

        return item
      })
    },
    toggleMarkerMode () {
      this.$refs.vietnamMap.toggleMarkerMode()
    },
    clearMarkers () {
      this.$refs.vietnamMap.clearMarkers()
    },
    onMarkersCleared () {
      console.log('Markers cleared')
    },
    onMarkerModeToggled (mode) {
      this.markerMode = mode
    },
    onIdcDetailsClicked (markerData) {
      const idcId = markerData.data.id
      if (idcId) {
        // Expand menu cây
        this.$refs.caySoDoThietBi.tree_nodeSelected({ nodeData: { id: `IDC_${idcId}` } }, true)

        // Show component chi tiết
        this.selectedIdcId = idcId
        this.selectedIdcData = markerData.data
        this.showIdcDetail = true
      }
    },
    async onIdcEditClicked (markerData) {
      this.onIdcDetailsClicked(markerData)
      const idcId = markerData?.data?.id
      if (idcId) {
        await this.waitForNodeSelection(idcId, 'IDC')
      }

      this.modalMode = 'EDIT'
      this.modalComponentKey += 1
      this.showAddModal = true
    },
    closeIdcDetail () {
      this.showIdcDetail = false
      this.selectedIdcId = null
      this.selectedIdcData = null
    },
    handleManageDetails (idcId) {
      console.log('Quản lý chi tiết IDC:', idcId)
      // Có thể navigate đến trang chi tiết hoặc mở modal
    },
    handleViewToaNhaDetail(toaNhaData) {
      if (toaNhaData && toaNhaData.ID_TOANHA) {
        const nodeID = `TOANHA_${toaNhaData.ID_TOANHA}`
        console.log('Navigating to building in tree:', nodeID, toaNhaData)
        if (this.$refs.caySoDoThietBi && this.$refs.caySoDoThietBi.refreshTree) {
          this.$refs.caySoDoThietBi.refreshTree(nodeID)
        }
      } else {
        console.warn('Không tìm thấy ID_TOANHA trong dữ liệu:', toaNhaData)
      }
    },
    handleViewMatSanDetail(matSanData) {
      // ID mặt sàn có thể là field 'ID' hoặc 'MATSAN_ID'
      const matSanId = matSanData.ID || matSanData.MATSAN_ID
      if (matSanId) {
        const nodeID = `MATSAN_${matSanId}`
        console.log('Navigating to floor in tree:', nodeID, matSanData)
        if (this.$refs.caySoDoThietBi && this.$refs.caySoDoThietBi.refreshTree) {
          this.$refs.caySoDoThietBi.refreshTree(nodeID)
        }
      } else {
        console.warn('Không tìm thấy ID mặt sàn trong dữ liệu:', matSanData)
      }
    },
    handleViewRackDetail(rackData) {
      // ID rack có thể là field 'RACK_ID'
      console.log('rackData:', rackData)
      const rackId = rackData.RACK_ID
      if (rackId) {
        const nodeID = `RACK_${rackId}`
        console.log('Navigating to rack in tree:', nodeID, rackData)
        if (this.$refs.caySoDoThietBi && this.$refs.caySoDoThietBi.refreshTree) {
          this.$refs.caySoDoThietBi.refreshTree(nodeID)
        }
      } else {
        console.warn('Không tìm thấy RACK_ID trong dữ liệu:', rackData)
      }
    },
    detectListActionsTop () {
      // Detect xem có list-actions-top VISIBLE trong DOM không
      this.$nextTick(() => {
        const dynamicComponentEl = this.$refs.dynamicComponent?.$el
        if (dynamicComponentEl) {
          // Thử tìm trong toàn bộ document trước
          const elementInDoc = document.querySelector('.list-actions-top')
          const elementInComponent = dynamicComponentEl.querySelector('.list-actions-top')

          // Tìm tất cả elements có class chứa 'action' để debug
          const allActionElements = dynamicComponentEl.querySelectorAll('[class*="action"]')

          const hasElement = !!(elementInComponent || elementInDoc)

          // Chỉ log khi có thay đổi
          if (this.hasListActionsTopInDOM !== hasElement) {
            console.log('✅ List-actions-top status changed:', hasElement ? 'FOUND' : 'NOT FOUND')
            this.hasListActionsTopInDOM = hasElement
          }
        } else {
          if (this.hasListActionsTopInDOM !== false) {
            console.log('⚠️ Dynamic component not found, setting hasListActionsTopInDOM to false')
            this.hasListActionsTopInDOM = false
          }
        }
      })
    },

    handleSelectObjectTaiNguyen(object) {
      if (object && object.id && object.type) {
        const nodeID = `${object.type}_${object.id}`
        if (this.$refs.caySoDoThietBi && this.$refs.caySoDoThietBi.refreshTree) {
          this.$refs.caySoDoThietBi.refreshTree(nodeID)
        }
      }
    },
    async waitForNodeSelection (targetId, targetType) {
      const maxAttempts = 8
      const normalizedTargetId = String(targetId)

      for (let attempt = 0; attempt < maxAttempts; attempt++) {
        const currentId = this.nodeSelected?.attributes?.id
        const currentType = this.nodeSelected?.attributes?.type
        const isMatched = String(currentId) === normalizedTargetId && currentType === targetType

        if (isMatched) {
          return true
        }

        await this.$nextTick()
      }

      console.warn(`Không thể đồng bộ nodeSelected cho ${targetType}_${targetId} sau ${maxAttempts} lần thử`)
      return false
    },
    onExportClick() {
      const type = this.nodeSelected.attributes.type
      if (type === 'MATSAN') {
        this.exportMatSanData(this.nodeSelected.attributes.id)
      } else if (type === 'ZONE') {
        this.exportZoneData(this.nodeSelected.attributes.id)
      } else {
        console.warn('Export không hỗ trợ cho loại tài nguyên này:', type)
      }
    },
    async exportMatSanData(matSanId) {
      try {
        const rs = await this.$root.context.post('/web-ecms/tainguyen-idc/khong-gian/get-ds-phong-chuaco-vitri', {
          id: matSanId
        })
        console.log('Export Mặt sàn response:', rs)

        const data = rs?.data || rs

        if (data && Array.isArray(data) && data.length > 0) {
          // Lọc chỉ lấy các trường cần thiết
          const filteredData = data.map(item => ({
            ID: item.ID,
            TEN: item.TEN,
            TENTAT: item.TENTAT,
            MOTA: item.MOTA,
            TOADO_X: item.TOADO_X,
            TOADO_Y: item.TOADO_Y,
            DIENTICH: item.DIENTICH
          }))

          // Tạo worksheet từ dữ liệu đã lọc
          const ws = XLSX.utils.json_to_sheet(filteredData)

          // Tạo workbook
          const wb = XLSX.utils.book_new()
          XLSX.utils.book_append_sheet(wb, ws, 'Danh sách phòng')

          // Tạo tên file với timestamp
          const timestamp = new Date().toISOString().replace(/[:.]/g, '-').slice(0, -5)
          const fileName = `DS_Phong_MatSan_${matSanId}_${timestamp}.xlsx`

          // Export file
          XLSX.writeFile(wb, fileName)

          this.$toast.success('Export dữ liệu Mặt sàn thành công!')
        } else {
          this.$toast.warning('Không có dữ liệu để export')
        }
      } catch (error) {
        console.error('Lỗi khi export Mặt sàn:', error)
        this.$toast.error('Lỗi khi export dữ liệu Mặt sàn')
      }
    },
    async exportZoneData(zoneId) {
      try {
        const rs = await this.$root.context.post('/web-ecms/tainguyen-idc/khong-gian/get-ds-rack-chua-lapdat', {
          id: zoneId
        })
        console.log('Export Phòng/Zone response:', rs)

        const data = rs?.data || rs

        if (data && Array.isArray(data) && data.length > 0) {
          // Lọc chỉ lấy các trường cần thiết
          const filteredData = data.map(item => ({
            ID: item.ID,
            MA_TS: item.MA_TS,
            TEN: item.TEN,
            TENTAT: item.TENTAT,
            MOTA: item.MOTA,
            TOADO_X: item.TOADO_X,
            TOADO_Y: item.TOADO_Y,
            SO_U: item.SO_U,
            DAI: item.DAI,
            RONG: item.RONG,
            MA_MAU: item.MA_MAU
          }))

          // Tạo worksheet từ dữ liệu đã lọc
          const ws = XLSX.utils.json_to_sheet(filteredData)

          // Tạo workbook
          const wb = XLSX.utils.book_new()
          XLSX.utils.book_append_sheet(wb, ws, 'Danh sách rack')

          // Tạo tên file với timestamp
          const timestamp = new Date().toISOString().replace(/[:.]/g, '-').slice(0, -5)
          const fileName = `DS_Rack_Zone_${zoneId}_${timestamp}.xlsx`

          // Export file
          XLSX.writeFile(wb, fileName)

          this.$toast.success('Export dữ liệu Phòng/Zone thành công!')
        } else {
          this.$toast.warning('Không có dữ liệu để export')
        }
      } catch (error) {
        console.error('Lỗi khi export Phòng/Zone:', error)
        this.$toast.error('Lỗi khi export dữ liệu Phòng/Zone')
      }
    }
  }
}
</script>
<style scoped>
.page {
  background: #fff;
  min-height: calc(100vh - 138px);
}

/deep/ .list-actions-top {
  position: absolute !important;
  top: -57px !important;
  left: -20% !important;
  width: 100vw !important;
}

.padding-top-0 {
  padding-top: 0 !important;
}

.padding-top-4 {
  padding-top: 58px !important;
}

/deep/ .page-content {
  margin-top: -50px;
  min-height: 100vh !important;
}

.error-message {
  padding: 12px;
  margin: 8px 0;
  background-color: #ffebee;
  color: #c62828;
  border-radius: 4px;
  border: 1px solid #ef9a9a;
}

/* Map Container Styles */
.map-container {
  z-index: 10;
  transform: translateZ(0);
}

.idc-list-panel {
  width: 40%;
  height: calc(100vh - 200px);
}

.idc-detail-panel {
  width: 100%;
  min-height: calc(100vh - 200px);
}

.map-large {
  position: relative;
  top: 20px;
  left: 22%;
  width: 60%;
  height: calc(100vh - 200px);
  display: flex;
}

.hidden-tree {
  display: none;
}

.sodo-hatang {
  border: 1px solid #ccc;
  padding: 10px;
  background: white;
}
</style>
<style>
.modal-add-tainguyen {
  .modal-dialog {
    max-width: 90vw !important;
    width: 90vw !important;
  }

  .modal-body {
    padding: 0 !important;

    .page-content {
      padding-top: 65px;
    }
  }

  .list-actions-top {
    position: relative !important;
    width: 100% !important;
    left: 0 !important;
    top: 0 !important;
  }
}
</style>
