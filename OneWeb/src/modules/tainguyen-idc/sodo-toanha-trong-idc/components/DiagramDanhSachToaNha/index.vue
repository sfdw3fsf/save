<template src="./index.html"></template>

<script>
import ModalThongTinToaNha from '../../../thongtin-chitiet-toanha/components/ModalThongTinToaNha/index.vue'
import { DiagramComponent } from '@syncfusion/ej2-vue-diagrams'
import { NodeConstraints, DataBinding } from '@syncfusion/ej2-diagrams'
import api from './api'
import defaultIdcImage from '@/assets/img/idc/idcdanang_demo.jpg'

export default {
  name: 'DiagramDanhSachToaNha',
  components: {
    ModalThongTinToaNha,
    'ejs-diagram': DiagramComponent
  },
  props: {
    id: {
      type: Number,
      default: 1
    }
  },
  provide: {
    diagram: [DataBinding]
  },
  data() {
    return {
      isLoading: false,
      showDialog: false,
      width: '100%',
      height: '100%',
      defaultIdcImage,
      // Tắt snap và grid
      snapSettings: {
        constraints: 0 
      },
      // Giới hạn scroll
      scrollSettings: {
        scrollLimit: 'Diagram',
        canAutoScroll: false,
        horizontalOffset: 0,
        verticalOffset: 0
      },
      // Cấu hình trang - ẩn lưới
      pageSettings: {
        showPageBreaks: false,
        multiplePage: false, 
        orientation: 'Landscape'
      },
      diagramData: {
        nodes: [],
        connectors: []
      },
      selectedBuilding: null,
      tenToaNha: '',
      listTTVH: [],
      selectedTrangThai: 0,
    }
  },
  async mounted() {
    await Promise.all([
      this.loadDanhMuc(),
      this.loadData(),
    ])
    this.initializeDiagram()
  },
  methods: {
    // ========= Template node =========
    getNodeTemplate(data) {
      return (`
        <div class="card-node" style="
          width: 100%; height: 100%;
          border: 1px solid #e0e0e0; border-radius: 10px;
          box-shadow: 0 2px 6px rgba(0,0,0,0.05); overflow: hidden; display: flex; flex-direction: column;
        ">
          <div class="card-header" style="
            display: flex; height: 60px; box-sizing: border-box;  
            justify-content: space-between; align-items: center; padding: 10px 15px; font-weight: 700; font-size: 15px; color: #000;
          ">
            <span style="flex: 1; margin-right: 8px; overflow: hidden; text-overflow: ellipsis;   
              display: -webkit-box; -webkit-line-clamp: 2; -webkit-box-orient: vertical;">
              ${data.title}
            </span>
            <button class="btn btn-sm btn-outline-primary" 
                    style="font-weight: 500; border-radius: 6px; flex-shrink: 0;"
                    onclick="window.openBuildingDetail && window.openBuildingDetail(${data.id})">
              Chi tiết <i class="fa fa-plus" style="margin-left: 4px;"></i>
            </button>
          </div>

          <img src="${data.imageUrl}" alt="${data.title}" style="width: 100%; height: 160px; object-fit: cover;" />

          <div class="card-body" style="height: 45px; box-sizing: border-box; padding: 12px 15px;
            font-size: 14px; border-top: 1px solid #f0f0f0; display: flex;
            justify-content: space-between; align-items: center;">
            <span>${data.description}</span>
            <span>${data.area} m²</span>
          </div>

          <div class="card-footer" style=" height: 45px; box-sizing: border-box; display: flex;
            justify-content: space-between; background-color: transparent; align-items: center;
            padding: 10px 15px; border-top: 1px solid #f0f0f0; font-size: 14px;">
            <span>${data.usage}% sử dụng</span>
            <span>${data.racks} Rack</span>
          </div>
        </div>
      `);
    },


    getNodeDefaults(node) {
      node.constraints = NodeConstraints.Default & ~NodeConstraints.Select
      return node
    },
    initializeDiagram() {
      // Setup global callback for button clicks
      window.openBuildingDetail = (buildingId) => {
        this.xemChiTietById(buildingId)
      }
    },
    handleNodeClick(args) {
      if (args.element && args.element.id) {
        const nodeId = args.element.id
        const node = this.diagramData.nodes.find((n) => n.id === nodeId)

        if (node && node.data) {
          this.xemChiTiet(node)
        }
      }
    },
    // ========= load data ========
    async loadData () {
      this.diagramData = { nodes: [], connectors: [] }

      try {
        this.$root.showLoading(true)
        const payload = {
          idIdc: this.id,
          ten: this.tenToaNha,
          ttvh: this.selectedTrangThai ? Number(this.selectedTrangThai) : null
        }
        const response = await api.getDsToaNhaTrongIdc(this.axios, payload)

        if (response && Array.isArray(response)) {
          await this.loadDiagramData(response)

          // Force refresh diagram sau khi load data
            this.$nextTick(() => {
            if (this.$refs.diagram && this.$refs.diagram.ej2Instances) {
              this.$refs.diagram.ej2Instances.dataBind()
              this.$refs.diagram.ej2Instances.refresh()
            }
          })
        }
      } catch (error) {
        this.$toast.error(error.message || 'Lỗi khi tải dữ liệu sơ đồ tòa nhà trong IDC')
      } finally {
        this.$root.showLoading(false)
      }

    },

    async loadPresignedUrl(toaNhaImage) {
      if (!toaNhaImage) return 

      try {
        return await api.getPresignedUrlFromMinio(this.axios, toaNhaImage)
      } catch (error) {
        console.error('Error loading presigned URL:', error)
      }
    },

    async loadDanhMuc() {
      try {
        const [listIdc, listTTVH] = await Promise.all([
          api.getDanhMuc(this.axios, 'IDC-DS-IDC', null, 'VIEW'),
          api.getDanhMuc(this.axios, 'IDC-TTVH-KHONGGIAN')
        ])

        this.listIdc = listIdc || []
        this.listTTVH = listTTVH || []

        this.listIdc = [{ id: 0, text: '-- Tất cả IDC --' }].concat(this.listIdc)
        this.listTTVH = [{ id: 0, text: '-- Tất cả trạng thái --' }].concat(this.listTTVH)
      } catch (error) {
        this.$toast.error(error.message || 'Lỗi khi tải danh sách IDC')
      }
    },

  async loadDiagramData (buildings) {
    
    const containerWidth = document.querySelector('.diagram-container').offsetWidth
    const nodeWidth = 280
    const gapX = 50
    const gapY = 50
    const nodesPerRow = Math.floor(containerWidth / (nodeWidth + gapX))

    // use Promise.all to build nodes in parallel
      const buildingUrls = await Promise.all(buildings.map((b) => {
        return b.HINHANH ? this.loadPresignedUrl(b.HINHANH) : ''
      }))

    const newNodes = []

    for (let i = 0; i < buildings.length; i++) {
      const building = buildings[i]
      const col = i % nodesPerRow
      const row = Math.floor(i / nodesPerRow)

      const node = {
        id: `building-${building.ID_TOANHA}`,
        width: nodeWidth,
        height: 320,
        offsetX: 200 + col * (nodeWidth + gapX),
        offsetY: 200 + row * (320 + gapY),
        shape: {
          type: 'HTML',
          content: this.getNodeTemplate({
            title: building.TEN_TOANHA,
            imageUrl: buildingUrls[i] || this.defaultIdcImage,
            description: building.TEN_MUCDICHSD || 'Trung tâm dữ liệu',
            area: building.DIENTICH,
            usage: building.TONG_RACK_THIETKE != 0 ? ((building.TONG_RACK_DA_LAPDAT / building.TONG_RACK_THIETKE) * 100
            ).toFixed(2) : 0,
            racks: building.TONG_RACK_THIETKE,
            id: building.ID_TOANHA
          })
        },
        constraints: NodeConstraints.Default & ~NodeConstraints.Select
      }

      newNodes.push(node)
    }
      this.diagramData = {
        nodes: newNodes,
        connectors: []
    }
  },

    // ======== handle ui interaction
    handleCloseDialog() {
      this.showDialog = false
    },

    xemChiTietById (buildingId) {
      console.log('Building ID clicked:', buildingId)
      this.selectedBuilding = buildingId
      this.showDialog = true
    },
    onClickThemToaNha () {
      this.$router.push({ path: '/tainguyen-idc/danhmuc-toanha'})
    },
    async onSearch () {
      await this.loadData()
    }


  },
  beforeDestroy() {
    // Cleanup global callback
    if (window.openBuildingDetail) {
      delete window.openBuildingDetail
    }
  }
}
</script>

<style scoped>
.so-do-toa-nha-trong-idc {
  padding: 20px;
}

.diagram-container {
  position: relative;
  border: 1px solid #ddd;
  background: #fafafa;
  min-height: 400px;
  background-color: #ffffff;
}
</style>
