<template src="./index.html"></template>

<script>
import Vue from 'vue'
import {
  DiagramPlugin,
  SymbolPalettePlugin,
  PrintAndExport,
  NodeConstraints,
  SelectorConstraints,
  SnapConstraints,
  UndoRedo,
  Snapping,
  DataBinding,
  DiagramContextMenu,
  Size
} from '@syncfusion/ej2-vue-diagrams'
import RoomDetailModal from './components/modals/RoomDetailModal.vue'
import DrawPolygonModal from './components/modals/DrawPolygonModal.vue'
import ViewAllElementsModal from './components/modals/ViewAllElementsModal.vue'
import { CONTEXT_MENU_ITEMS } from './constants.js'
import FormMatSan from '../DanhMucMatSan/components/MatSanDetailForm/MatSanDetailForm.vue'
Vue.use(DiagramPlugin)
Vue.use(SymbolPalettePlugin)

export default {
  components: {
    RoomDetailModal,
    DrawPolygonModal,
    ViewAllElementsModal,
    FormMatSan
  },

  props: {
    modalItemID: {
      type: String || Number,
      default: ''
    }
  },

  provide: {
    diagram: [PrintAndExport, UndoRedo, Snapping, DataBinding, DiagramContextMenu]
  },

  data: function () {
    return {
      currentZoom: 1,
      showEditModal: false,
      // Định nghĩa workspace dạng hình chữ nhật (chiều dài x chiều rộng)
      floorWidth: 2000, // Chiều rộng mặt sàn (đơn vị: cm)
      floorHeight: 1500, // Chiều dài mặt sàn (đơn vị: cm)
      isFloorDataLoaded: false, // Flag để chờ load data xong mới render diagram
      // Phòng đã bố trí trên mặt sàn
      // Quy tắc: 1 pixel = 1 cm, diện tích = (width_cm * height_cm) / 10000 m²
      installedRooms: [],

      // Phòng/Zone chưa bố trí
      uninstalledRooms: [],

      allRoomsBackup: [],

      snapSettings: {
        constraints: SnapConstraints.ShowLines | SnapConstraints.SnapToLines,
        snapAngle: 5,
        snapObjectDistance: 10, // Snap mỗi 10cm khi kéo
        horizontalGridlines: {
          lineColor: '#e0e0e0',
          lineDashArray: '2,2',
          lineIntervals: [1, 59, 0.25, 59.75, 0.25, 59.75, 0.25, 59.75, 0.25, 59.75, 0.25, 59.75], // Grid mỗi 60cm
          snapIntervals: [10] // Snap mỗi 10cm (bước nhảy)
        },
        verticalGridlines: {
          lineColor: '#e0e0e0',
          lineDashArray: '2,2',
          lineIntervals: [1, 59, 0.25, 59.75, 0.25, 59.75, 0.25, 59.75, 0.25, 59.75, 0.25, 59.75], // Grid mỗi 60cm
          snapIntervals: [10] // Snap mỗi 10cm (bước nhảy)
        }
      },

      scrollSettings: {
        scrollLimit: 'Infinity',
        minZoom: 0.1,
        maxZoom: 3,
        canAutoScroll: false,
        padding: { left: 100, right: 100, top: 100, bottom: 100 }
      },

      contextMenuSettings: {
        show: true,
        items: CONTEXT_MENU_ITEMS,
        showCustomMenuOnly: true
      },

      // Command manager để chặn delete command
      commandManager: {
        commands: [
          {
            name: 'delete',
            canExecute: () => {
              // Chặn không cho thực thi lệnh delete
              return false
            }
          }
        ]
      },

      selectedItems: {
        constraints: SelectorConstraints.All & ~SelectorConstraints.Rotate & ~SelectorConstraints.ResizeAll
      },

      selectedRoomDetail: null,
      modalComponentKey: 0,
      // Polygon drawing state
      drawingRoomData: null, // Thông tin phòng đang vẽ
      tempDropPosition: null, // Vị trí drop tạm thời
      editingNodeId: null, // ID của node đang được edit polygon
      modalMode: 'ADD' // ADD | EDIT | VIEW
    }
  },

  computed: {
    palettes() {
      return [
        {
          id: 'rooms',
          expanded: true,
          title: 'Phòng/Zone chưa bố trí',
          symbols: this.uninstalledRooms.map((room) => this.createRoomPaletteNode(room))
        }
      ]
    },

    pageSettings() {
      // Đảm bảo page đủ lớn cho mặt sàn có kích thước lớn, tối thiểu 1000x1000
      // Thêm padding 150px mỗi bên để hiển thị grid headers bên ngoài
      const pageWidth = Math.max(this.floorWidth + 300, 1000)
      const pageHeight = Math.max(this.floorHeight + 300, 1000)
      return {
        orientation: 'Landscape',
        width: pageWidth,
        height: pageHeight,
        multiplePage: false,
        showPageBreaks: false,
        background: {
          color: 'white'
        },
        boundaryConstraints: 'Page'
      }
    },

    modalTitle() {
      return this.modalMode === 'ADD'
        ? 'Thêm mới Mặt sàn'
        : this.modalMode === 'EDIT'
          ? 'Chỉnh sửa Mặt sàn'
          : 'Thông tin Mặt sàn'
    },

    isZoomInDisabled() {
      // Use this.currentZoom which is reactive and updated by onScrollChange
      // Max zoom actual value is around 2.592 (when maxZoom = 3)
      return this.currentZoom >= 2.9
    },

    isZoomOutDisabled() {
      // Use this.currentZoom which is reactive and updated by onScrollChange
      // Min zoom actual value is around 0.1157 (when minZoom = 0.1)
      return this.currentZoom <= 0.12
    }
  },

  created: async function () {
    // Load thông tin mặt sàn trước
    if (this.modalItemID) {
      let rs = await this.$root.context.post('/web-ecms/danh-muc-mat-san/get-by-id', {
        id: this.modalItemID
      })
      if (rs.error_code == 'BSS-00000000') {
        let data = rs.data[0]
        this.floorWidth = data.CHIEU_RONG || data.TAMSAN_RONG
        this.floorHeight = data.CHIEU_DAI || data.TAMSAN_DAI
      } else {
        console.warn('⚠️ API error:', rs.error_code, rs.error_message)
      }
    }

    // Load dữ liệu phòng
    await this.loadRoomData()

    // FIX: Khởi tạo backup SAU khi load API xong
    this.allRoomsBackup = [
      ...JSON.parse(JSON.stringify(this.installedRooms)),
      ...JSON.parse(JSON.stringify(this.uninstalledRooms))
    ]

    // Đánh dấu đã load xong, cho phép render diagram
    this.isFloorDataLoaded = true
  },

  watch: {
    isFloorDataLoaded(newVal) {
      if (newVal) {
        // Đợi diagram được render
        this.$nextTick(() => {
          setTimeout(() => {
            if (this.$refs.diagramObj && this.$refs.diagramObj.ej2Instances) {
              const diagram = this.$refs.diagramObj.ej2Instances
              this.drawFloorBorder(diagram)
              // Load installed rooms sau khi diagram đã sẵn sàng
              this.loadInstalledRooms()
              // Fit to page sau khi load xong tất cả
              this.$nextTick(() => {
                diagram.dataBind()
                // Force render all nodes
                diagram.nodes.forEach(node => {
                  if (node.wrapper) {
                    node.wrapper.measure(new Size(node.width, node.height))
                    node.wrapper.arrange(node.wrapper.desiredSize)
                  }
                })

                setTimeout(() => {
                  // Reset zoom về 1 trước
                  diagram.reset()
                  // Fit to page để hiển thị toàn bộ
                  diagram.fitToPage({ mode: 'Width', region: 'Content', margin: { left: 50, right: 50, top: 50, bottom: 50 } })
                  this.currentZoom = diagram.scrollSettings.currentZoom
                }, 150)
              })
            } else {
              console.error(' Diagram ref not found')
            }
          }, 200)
        })
      }
    }
  },

  mounted() {
    // Nếu data đã load trước khi mounted (không có modalItemID)
    if (this.isFloorDataLoaded) {
      this.$nextTick(() => {
        setTimeout(() => {
          if (this.$refs.diagramObj && this.$refs.diagramObj.ej2Instances) {
            const diagram = this.$refs.diagramObj.ej2Instances
            this.drawFloorBorder(diagram)
            // Load installed rooms sau khi diagram đã sẵn sàng
            this.loadInstalledRooms()
            // Fit to page sau khi load xong tất cả
            this.$nextTick(() => {
              diagram.dataBind()
              // Force render all nodes
              diagram.nodes.forEach(node => {
                if (node.wrapper) {
                  node.wrapper.measure(new Size(node.width, node.height))
                  node.wrapper.arrange(node.wrapper.desiredSize)
                }
              })

              setTimeout(() => {
                // Reset zoom về 1 trước
                diagram.reset()
                // Fit to page để hiển thị toàn bộ
                diagram.fitToPage({ mode: 'Width', region: 'Content', margin: { left: 50, right: 50, top: 50, bottom: 50 } })
                this.currentZoom = diagram.scrollSettings.currentZoom
              }, 150)
            })
          }
        }, 200)
      })
    }
  },

  beforeDestroy() {
    // Cleanup if needed
  },

  methods: {
    handleModalSave() {
      this.showEditModal = false
      // Reload data
      this.loadRoomData()
    },
    onScrollChange(args) {
      // Update current zoom when scroll/zoom changes
      if (args && args.newValue && args.newValue.currentZoom !== undefined) {
        this.currentZoom = args.newValue.currentZoom
      }
    },

    onAddNewMatsan() {
      this.modalMode = 'ADD'
      this.selectedMatsanId = null
      this.showEditModal = true
    },
    onEditMatsan() {
      if (!this.modalItemID) {
        this.$toast.error('Không có Mặt sàn để chỉnh sửa.')
        return
      }
      this.modalMode = 'EDIT'
      this.selectedMatsanId = this.modalItemID
      this.showEditModal = true
    },
    // Tính bounding box của polygon phòng
    getRoomBounds(room) {
      if (!room.polygon || room.polygon.length === 0) {
        return { minX: 0, minY: 0, maxX: 100, maxY: 100, width: 100, height: 100 }
      }

      // Đảm bảo polygon là array và mỗi point là array [x, y]
      const polygon = Array.isArray(room.polygon) ? room.polygon : []

      if (polygon.length === 0) {
        return { minX: 0, minY: 0, maxX: 100, maxY: 100, width: 100, height: 100 }
      }

      const xs = polygon.map((p) => (Array.isArray(p) ? p[0] : 0))
      const ys = polygon.map((p) => (Array.isArray(p) ? p[1] : 0))

      const minX = Math.min(...xs)
      const maxX = Math.max(...xs)
      const minY = Math.min(...ys)
      const maxY = Math.max(...ys)

      return {
        minX,
        minY,
        maxX,
        maxY,
        width: maxX - minX,
        height: maxY - minY
      }
    },

    // Kiểm tra một điểm có nằm trong mặt sàn không
    isPointInFloor(x, y) {
      return x >= 0 && x <= this.floorWidth && y >= 0 && y <= this.floorHeight
    },

    // Kiểm tra polygon phòng có nằm hoàn toàn trong mặt sàn không
    isRoomInFloor(room, offsetX, offsetY) {
      const polygon = room.polygon || []

      for (let point of polygon) {
        const absX = offsetX + point[0]
        const absY = offsetY + point[1]

        if (!this.isPointInFloor(absX, absY)) {
          return false
        }
      }

      return true
    },

    drawFloorBorder(diagram) {
      if (!diagram) return

      // Xóa các nodes cũ
      const oldBorder = diagram.getObject('floorBorder')
      if (oldBorder) diagram.remove(oldBorder)

      const oldMask = diagram.getObject('floorMask')
      if (oldMask) diagram.remove(oldMask)

      const centerX = this.floorWidth / 2
      const centerY = this.floorHeight / 2
      const padding = 5000

      // Outer rectangle (clockwise) - DÙNG TỌA ĐỘ TƯƠNG ĐỐI
      const outerRect = `M ${-this.floorWidth / 2 - padding} ${-this.floorHeight / 2 - padding}
                         L ${this.floorWidth / 2 + padding} ${-this.floorHeight / 2 - padding}
                         L ${this.floorWidth / 2 + padding} ${this.floorHeight / 2 + padding}
                         L ${-this.floorWidth / 2 - padding} ${this.floorHeight / 2 + padding} Z`

      // Inner rectangle (counter-clockwise để tạo hole) - DÙNG TỌA ĐỘ TƯƠNG ĐỐI
      const innerRect = `M ${this.floorWidth / 2} ${-this.floorHeight / 2}
                         L ${-this.floorWidth / 2} ${-this.floorHeight / 2}
                         L ${-this.floorWidth / 2} ${this.floorHeight / 2}
                         L ${this.floorWidth / 2} ${this.floorHeight / 2} Z`

      const maskPath = outerRect + ' ' + innerRect

      // Node mask - fill trắng che vùng ngoài
      const maskNode = {
        id: 'floorMask',
        offsetX: centerX,
        offsetY: centerY,
        width: this.floorWidth + padding * 2,
        height: this.floorHeight + padding * 2,
        shape: {
          type: 'Path',
          data: maskPath
        },
        style: {
          fill: 'white',
          strokeColor: 'transparent',
          strokeWidth: 0
        },
        constraints: NodeConstraints.None,
        zIndex: -1
      }

      diagram.add(maskNode)

      // Vẽ viền mặt sàn
      const borderNode = {
        id: 'floorBorder',
        offsetX: centerX,
        offsetY: centerY,
        width: this.floorWidth,
        height: this.floorHeight,
        shape: {
          type: 'Basic',
          shape: 'Rectangle'
        },
        style: {
          fill: 'transparent',
          strokeColor: '#2196F3',
          strokeWidth: 4,
          strokeDashArray: ''
        },
        constraints: NodeConstraints.None,
        zIndex: 0
      }

      diagram.add(borderNode)

      this.drawFloorDimensionLabels(diagram)
    },

    drawFloorDimensionLabels(diagram) {
      if (!diagram) return

      // Xóa các label cũ nếu có
      const oldLabels = [
        'dimLabelWidth',
        'dimLabelHeight',
        'edgeLabelTop',
        'edgeLabelRight',
        'edgeLabelBottom',
        'edgeLabelLeft'
      ]
      oldLabels.forEach((id) => {
        const oldLabel = diagram.getObject(id)
        if (oldLabel) diagram.remove(oldLabel)
      })

      const fontSize = 12
      const offset = 15 // Offset vào trong mặt sàn

      // Edge labels cho 4 cạnh của mặt sàn - ĐẶT BÊN TRONG

      // Top edge (cạnh trên)
      const topLabel = {
        id: 'edgeLabelTop',
        offsetX: this.floorWidth / 2,
        offsetY: offset, // Offset vào trong
        width: 120,
        height: 20,
        shape: {
          type: 'Text',
          content: `${this.floorWidth} cm`
        },
        style: {
          color: '#2196F3',
          fontSize: fontSize,
          bold: true,
          fill: 'transparent',
          textAlign: 'Center'
        },
        constraints: 0,
        zIndex: 1000
      }

      // Right edge (cạnh phải) - xoay dọc
      const rightLabel = {
        id: 'edgeLabelRight',
        offsetX: this.floorWidth - offset, // Offset vào trong
        offsetY: this.floorHeight / 2,
        width: 120,
        height: 20,
        shape: {
          type: 'Text',
          content: `${this.floorHeight} cm`
        },
        style: {
          color: '#2196F3',
          fontSize: fontSize,
          bold: true,
          fill: 'transparent',
          textAlign: 'Center'
        },
        rotateAngle: 90,
        constraints: 0,
        zIndex: 1000
      }

      // Bottom edge (cạnh dưới)
      const bottomLabel = {
        id: 'edgeLabelBottom',
        offsetX: this.floorWidth / 2,
        offsetY: this.floorHeight - offset, // Offset vào trong
        width: 120,
        height: 20,
        shape: {
          type: 'Text',
          content: `${this.floorWidth} cm`
        },
        style: {
          color: '#2196F3',
          fontSize: fontSize,
          bold: true,
          fill: 'transparent',
          textAlign: 'Center'
        },
        constraints: 0,
        zIndex: 1000
      }

      // Left edge (cạnh trái) - xoay dọc
      const leftLabel = {
        id: 'edgeLabelLeft',
        offsetX: offset, // Offset vào trong
        offsetY: this.floorHeight / 2,
        width: 120,
        height: 20,
        shape: {
          type: 'Text',
          content: `${this.floorHeight} cm`
        },
        style: {
          color: '#2196F3',
          fontSize: fontSize,
          bold: true,
          fill: 'transparent',
          textAlign: 'Center'
        },
        rotateAngle: -90,
        constraints: 0,
        zIndex: 1000
      }

      diagram.add(topLabel)
      diagram.add(rightLabel)
      diagram.add(bottomLabel)
      diagram.add(leftLabel)

      // Vẽ grid với labels
      this.drawGridLabels(diagram)
    },

    // Vẽ grid với labels (AA, AB, AC... và 01, 02, 03...)
    drawGridLabels(diagram) {
      const cellSize = 60 // Kích thước mỗi ô grid (cm)

      // Tính số cột và hàng - BẰNG CEIL để bao gồm cả ô không đủ kích thước
      const cols = Math.ceil(this.floorWidth / cellSize)
      const rows = Math.ceil(this.floorHeight / cellSize)

      // Labels cho cột - CHỮ CÁI (AA, AB, AC... skip I) - TRỤC NGANG
      // Bỏ qua chữ I để tránh nhầm lẫn với số 1
      const colLabels = []
      let currentLabel = 'AA'
      for (let i = 0; i < cols; i++) {
        colLabels.push(currentLabel)
        // Tăng label
        const chars = currentLabel.split('')
        let carry = 1
        for (let j = chars.length - 1; j >= 0 && carry; j--) {
          let charCode = chars[j].charCodeAt(0) + carry
          if (charCode > 90) { // 'Z'
            chars[j] = 'A'
          } else {
            // Bỏ qua chữ I
            if (charCode === 73) charCode = 74 // I -> J
            chars[j] = String.fromCharCode(charCode)
            carry = 0
          }
        }
        if (carry) chars.unshift('A')
        currentLabel = chars.join('')
      }

      // Vẽ grid lines và cell labels
      for (let row = 0; row < rows; row++) {
        for (let col = 0; col < cols; col++) {
          const x = col * cellSize
          const y = row * cellSize

          // Tính kích thước thực tế của ô (ô cuối cùng có thể nhỏ hơn)
          let actualWidth = cellSize
          let actualHeight = cellSize

          // Ô ở cột cuối cùng
          if (col === cols - 1) {
            actualWidth = this.floorWidth - x
            if (actualWidth <= 0) continue
          }

          // Ô ở hàng cuối cùng
          if (row === rows - 1) {
            actualHeight = this.floorHeight - y
            if (actualHeight <= 0) continue
          }

          // Cell label: CHỮ + SỐ (AA01, AB01, AC02...)
          const rowLabel = String(row + 1).padStart(2, '0')
          const cellLabel = `${colLabels[col]}${rowLabel}`

          // Vẽ rectangle cho cell với kích thước thực tế
          diagram.add({
            id: `grid_cell_${row}_${col}`,
            offsetX: x,
            offsetY: y,
            width: actualWidth,
            height: actualHeight,
            pivot: { x: 0, y: 0 },
            shape: {
              type: 'Basic',
              shape: 'Rectangle'
            },
            style: {
              fill: 'transparent',
              strokeColor: '#E0E0E0',
              strokeWidth: 0.5,
              strokeDashArray: '2,2'
            },
            constraints: NodeConstraints.None,
            zIndex: -10,
            addInfo: {
              isGridCell: true,
              cellLabel: cellLabel
            }
          })

          // Vẽ label cho cell (ở góc trên trái) - chỉ hiển thị nếu ô đủ rộng
          if (actualWidth >= 30 && actualHeight >= 15) {
            diagram.add({
              id: `grid_label_${row}_${col}`,
              offsetX: x + 3,
              offsetY: y + 2,
              width: Math.min(40, actualWidth - 6),
              height: 15,
              pivot: { x: 0, y: 0 },
              shape: {
                type: 'Text',
                content: cellLabel
              },
              style: {
                fontSize: 9,
                fill: 'transparent',
                color: '#BDBDBD',
                bold: false,
                textAlign: 'Left'
              },
              constraints: NodeConstraints.None,
              zIndex: -9,
              addInfo: {
                isGridLabel: true
              }
            })
          }
        }
      }

      // Vẽ column headers - CHỮ CÁI (AA, AB, AC...) - PHÍA TRÊN (trục ngang)
      for (let col = 0; col < cols; col++) {
        const x = col * cellSize

        // Tính width thực tế cho header
        let headerWidth = cellSize
        if (col === cols - 1) {
          headerWidth = this.floorWidth - x
        }

        diagram.add({
          id: `grid_col_header_${col}`,
          offsetX: x + headerWidth / 2,
          offsetY: -30, // Nằm ngoài mặt sàn phía trên
          width: headerWidth,
          height: 20,
          shape: {
            type: 'Text',
            content: colLabels[col]
          },
          style: {
            fontSize: 12,
            fill: '#F5F5F5',
            color: '#424242',
            bold: true,
            textAlign: 'Center'
          },
          constraints: NodeConstraints.None,
          zIndex: -8,
          addInfo: {
            isGridHeader: true
          }
        })
      }

      // Vẽ row headers - SỐ (01, 02, 03...) - BÊN TRÁI (trục dọc)
      for (let row = 0; row < rows; row++) {
        const y = row * cellSize
        const rowLabel = String(row + 1).padStart(2, '0')

        // Tính height thực tế cho header
        let headerHeight = cellSize
        if (row === rows - 1) {
          headerHeight = this.floorHeight - y
        }

        diagram.add({
          id: `grid_row_header_${row}`,
          offsetX: -35, // Nằm ngoài mặt sàn phía trái
          offsetY: y + headerHeight / 2,
          width: 30,
          height: headerHeight,
          shape: {
            type: 'Text',
            content: rowLabel
          },
          style: {
            fontSize: 12,
            fill: '#F5F5F5',
            color: '#424242',
            bold: true,
            textAlign: 'Center'
          },
          constraints: NodeConstraints.None,
          zIndex: -8,
          addInfo: {
            isGridHeader: true
          }
        })
      }
    },

    // Tạo node phòng cho Symbol Palette
    createRoomPaletteNode(room) {
      const bounds = this.getRoomBounds(room)

      return {
        id: room.id,
        width: 100,
        height: 80,
        constraints: NodeConstraints.Default & ~NodeConstraints.Resize & ~NodeConstraints.Rotate,
        shape: {
          type: 'HTML',
          content: this.createRoomPaletteHtml(room)
        },
        handleConstraints: 0,
        addInfo: {
          roomId: room.id,
          originId: room.originId,
          roomName: room.name,
          roomType: room.type,
          status: room.status,
          area: room.area,
          capacity: room.capacity,
          usedCapacity: room.usedCapacity,
          polygon: room.polygon,
          color: room.color,
          mota: room.mota || '',
          actualWidth: bounds.width,
          actualHeight: bounds.height
        }
      }
    },

    // Tạo HTML cho room trong palette
    createRoomPaletteHtml(room) {
      if (!room.polygon) {
        return `
          <div style="
            width: 70px;
            height: 70px;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            background: white;
            border: 2px dashed ${room.color};
            border-radius: 4px;
            box-sizing: border-box;
            font-family: Arial, sans-serif;
            padding: 5px;
            gap: 5px;
          ">
            <div style="
              font-size: 30px;
              color: ${room.color};
            ">
            </div>
            <div style="
              font-size: 11px;
              font-weight: bold;
              color: #000;
              text-align: center;
              line-height: 1.2;
            ">
              ${room.name}
            </div>
          </div>
        `
      }

      return `
        <div style="
          width: 70px;
          height: 70px;
          display: flex;
          flex-direction: column;
          justify-content: center;
          background: white;
          border: 2px solid ${room.color};
          border-radius: 4px;
          box-sizing: border-box;
          font-family: Arial, sans-serif;
          padding: 5px;
          gap: 3px;
        ">
          <div style="
            font-size: 11px;
            font-weight: bold;
            color: #000;
            text-align: center;
            line-height: 1.2;
          ">
            ${room.name}
          </div>
          <div style="
            font-size: 8px;
            color: #666;
            text-align: center;
          ">
            ${room.area ? room.area + ' m²' : ''}
          </div>
        </div>
      `
    },

    // Tạo node phòng cho Diagram
    createRoomNode(room) {
      const bounds = this.getRoomBounds(room)

      return {
        id: room.id,
        originId: room.originId,
        width: bounds.width,
        height: bounds.height,
        zIndex: 100,
        shape: {
          type: 'HTML',
          content: this.createRoomNodeHtml(room)
        },
        constraints:
          NodeConstraints.Default &
          ~NodeConstraints.Resize &
          ~NodeConstraints.Rotate &
          ~NodeConstraints.ResizeAll &
          ~NodeConstraints.AspectRatio,
        addInfo: {
          roomId: room.id,
          originId: room.originId,
          roomName: room.name,
          roomType: room.type,
          status: room.status,
          area: room.area,
          capacity: room.capacity,
          usedCapacity: room.usedCapacity,
          polygon: room.polygon,
          color: room.color,
          mota: room.mota || '',
          actualWidth: bounds.width,
          actualHeight: bounds.height
        }
      }
    },

    // Tạo HTML cho room trong diagram - Hiển thị polygon
    createRoomNodeHtml(room) {
      const bounds = this.getRoomBounds(room)

      // Đảm bảo polygon là array hợp lệ
      if (!room.polygon || !Array.isArray(room.polygon) || room.polygon.length < 3) {
        console.error(' Invalid polygon for room:', room.name, room.polygon)
        return `<div style="width: ${bounds.width}px; height: ${bounds.height}px; background: red; color: white; text-align: center; display: flex; justify-content: center; align-items: center; font-family: Arial, sans-serif;">
            Phòng/Zone: ${room.name} không có polygon hợp lệ
          </div>`
      }

      // Tạo path SVG từ polygon (tọa độ đã là relative từ 0,0)
      const pathData =
        room.polygon.map((p, i) => (i === 0 ? `M ${p[0]} ${p[1]}` : `L ${p[0]} ${p[1]}`)).join(' ') + ' Z'

      // Tính centroid (tâm) của polygon để đặt text
      const centroid = this.calculatePolygonCentroid(room.polygon)

      // Tính độ dài các cạnh và tạo labels
      const edgeLabels = this.generateRoomEdgeLabels(room.polygon, bounds)

      // Sử dụng div wrapper để tránh SVG duplicate
      return `
        <div style="width: ${bounds.width}px; height: ${bounds.height}px; position: relative;">
          <svg width="${bounds.width}" height="${bounds.height}"
               style="position: absolute; top: 0; left: 0; pointer-events: none;"
               xmlns="http://www.w3.org/2000/svg">
            <defs>
              <clipPath id="clip-${room.id}">
                <path d="${pathData}" />
              </clipPath>
            </defs>

            <!-- Polygon background -->
            <path d="${pathData}"
              fill="${room.color}20"
              stroke="${room.color}"
              stroke-width="2"
              stroke-linejoin="round"
              stroke-linecap="round"
            />

            <!-- Tên phòng - Đặt tại centroid -->
            <text x="${centroid.x}" y="${centroid.y - 15}"
              font-family="Arial"
              font-size="13"
              font-weight="bold"
              fill="#000"
              text-anchor="middle"
              pointer-events="none">
              ${room.name}
            </text>

            <!-- Diện tích - Đặt tại centroid -->
            <text x="${centroid.x}" y="${centroid.y + 15}"
              font-family="Arial"
              font-size="9"
              fill="#666"
              text-anchor="middle"
              pointer-events="none">
              ${room.area ? room.area + ' m²' : ''}
            </text>

            <!-- Độ dài các cạnh -->
            ${edgeLabels}
          </svg>
        </div>
      `
    },

    // Tạo edge labels cho phòng (4 cạnh - nếu là hình chữ nhật)
    generateRoomEdgeLabels(polygon, bounds) {
      console.log('Generating edge labels for polygon:', polygon, 'bounds:', bounds)
      if (!polygon || polygon.length < 3) {
        return ''
      }

      let labels = ''

      // Tính độ dài từng cạnh
      for (let i = 0; i < polygon.length; i++) {
        const p1 = polygon[i]
        const p2 = polygon[(i + 1) % polygon.length]

        const dx = p2[0] - p1[0]
        const dy = p2[1] - p1[1]
        const length = Math.sqrt(dx * dx + dy * dy)

        // Vị trí giữa cạnh
        const midX = (p1[0] + p2[0]) / 2
        const midY = (p1[1] + p2[1]) / 2

        // Tính góc của cạnh
        const angle = Math.atan2(dy, dx)

        // Offset vào TRONG polygon (đảo dấu để luôn nằm trong)
        const offsetDistance = 12
        const labelX = midX + Math.sin(angle) * offsetDistance
        const labelY = midY - Math.cos(angle) * offsetDistance

        // Clamp vào bounds để đảm bảo không vượt ra ngoài polygon
        const labelXFinal = Math.max(15, Math.min(bounds.width - 15, labelX))
        const labelYFinal = Math.max(15, Math.min(bounds.height - 15, labelY))

        // Chuẩn hóa góc xoay
        let displayAngle = angle * (180 / Math.PI)
        if (displayAngle > 90) {
          displayAngle -= 180
        } else if (displayAngle < -90) {
          displayAngle += 180
        }

        const text = `${Math.round(length)} cm`

        labels += `
          <text x="${labelXFinal}"
                y="${labelYFinal}"
                font-family="Arial"
                font-size="10"
                font-weight="bold"
                fill="${polygon.length === 4 ? '#0066cc' : '#666'}"
                text-anchor="middle"
                dominant-baseline="middle"
                transform="rotate(${displayAngle} ${labelXFinal} ${labelYFinal})"
                pointer-events="none">
            ${text}
          </text>
        `
        console.log(`Edge ${i}: length=${length}, mid=(${midX},${midY}), angle=${displayAngle}`)
      }
      console.log('Generated labels:', labels)

      return labels
    },

    // Apply polygon vào node hiện tại (sau khi vẽ xong)
    applyPolygonToNode(node, room) {
      const bounds = this.getRoomBounds(room)

      // Update node shape sang HTML để hiển thị đầy đủ (bao gồm edge labels)
      node.shape = {
        type: 'HTML',
        content: this.createRoomNodeHtml(room)
      }

      // Update size
      node.width = bounds.width
      node.height = bounds.height

      // Update style
      node.style = {
        fill: 'transparent',
        strokeColor: 'transparent',
        strokeWidth: 0
      }

      node.annotations = []

      // Update constraints
      node.constraints =
        NodeConstraints.Default & ~NodeConstraints.Resize & ~NodeConstraints.Rotate & ~NodeConstraints.AspectRatio

      // Update addInfo
      node.addInfo.polygon = room.polygon
      node.addInfo.area = room.area
      node.addInfo.actualWidth = bounds.width
      node.addInfo.actualHeight = bounds.height
      node.addInfo.x = node.offsetX - bounds.width / 2
      node.addInfo.y = node.offsetY - bounds.height / 2

      // Make visible
      node.visible = true
    },

    // Phần này không đổi - keep as-is
    getDiagramNodeDefaults(node) {
      const width = (node.addInfo && node.addInfo.actualWidth) || (node.addInfo && node.addInfo.width) || 100
      const height = (node.addInfo && node.addInfo.actualHeight) || (node.addInfo && node.addInfo.height) || 80

      return {
        width: width,
        height: height,
        constraints:
          NodeConstraints.Default & ~NodeConstraints.Resize & ~NodeConstraints.Rotate & ~NodeConstraints.Select
      }
    },

    // Defaults cho nodes trong palette
    getNodeDefaults(node) {
      return {
        width: 100,
        height: 80,
        constraints: NodeConstraints.Default & ~NodeConstraints.Resize & ~NodeConstraints.Rotate
      }
    },

    // Defaults cho nodes trong diagram
    getDiagramNodeDefaults(node) {
      const width = (node.addInfo && node.addInfo.actualWidth) || 100
      const height = (node.addInfo && node.addInfo.actualHeight) || 80

      return {
        width: width,
        height: height,
        constraints:
          NodeConstraints.Default &
          ~NodeConstraints.Resize &
          ~NodeConstraints.Rotate &
          ~NodeConstraints.AspectRatio &
          ~NodeConstraints.Select,
        rotateAngle: 0,
        pivot: { x: 0.5, y: 0.5 }
      }
    },

    // Symbol info cho palette
    getSymbolInfo(symbol) {
      return {
        fit: true,
        description: {
          text: symbol.addInfo ? symbol.addInfo.roomName : symbol.id,
          overflow: 'Wrap'
        }
      }
    },

    // Xử lý khi drop node vào diagram
    onDrop(args) {
      if (args.element && args.element.addInfo) {
        const roomId = args.element.addInfo.roomId

        const room = {
          id: roomId,
          originId: args.element.addInfo.originId,
          name: args.element.addInfo.roomName,
          type: args.element.addInfo.roomType,
          status: args.element.addInfo.status,
          area: args.element.addInfo.area,
          capacity: args.element.addInfo.capacity,
          usedCapacity: args.element.addInfo.usedCapacity,
          polygon: args.element.addInfo.polygon,
          color: args.element.addInfo.color,
          mota: args.element.addInfo.mota || '',
          isTemp: args.element.addInfo.polygon ? false : true
        }

        // Nếu phòng chưa có polygon, mở modal vẽ polygon
        if (!room.polygon) {
          // Lưu vị trí drop
          this.tempDropPosition = {
            x: args.element.offsetX,
            y: args.element.offsetY
          }

          // Lưu dữ liệu phòng
          this.drawingRoomData = { ...room }

          // Ẩn node tạm thời (không xóa)
          args.element.visible = false

          // Gán cờ node tạm để không lấy dữ liệu
          args.element.addInfo.isTemp = true

          // Mở modal vẽ polygon
          this.$refs.drawPolygonModal.show(room)

          return
        }

        // Đã có polygon - update node với Path shape
        this.applyPolygonToNode(args.element, room)

        // Constraint vị trí drop trong mặt sàn - kiểm tra polygon
        const polygon = room.polygon
        const xs = polygon.map((p) => p[0])
        const ys = polygon.map((p) => p[1])
        const minX = Math.min(...xs)
        const maxX = Math.max(...xs)
        const minY = Math.min(...ys)
        const maxY = Math.max(...ys)
        const width = maxX - minX
        const height = maxY - minY

        let dropX = args.element.offsetX
        let dropY = args.element.offsetY

        // Tính tọa độ góc trên trái của node
        const nodeX = dropX - width / 2
        const nodeY = dropY - height / 2

        // Kiểm tra và điều chỉnh vị trí để polygon nằm trong mặt sàn
        let adjustedNodeX = nodeX
        let adjustedNodeY = nodeY

        // Kiểm tra từng điểm của polygon
        for (let point of polygon) {
          const absX = nodeX + point[0]
          const absY = nodeY + point[1]

          // Điều chỉnh nếu vượt biên trái
          if (absX < 0) {
            adjustedNodeX = Math.max(adjustedNodeX, -point[0])
          }
          // Điều chỉnh nếu vượt biên phải
          if (absX > this.floorWidth) {
            adjustedNodeX = Math.min(adjustedNodeX, this.floorWidth - point[0])
          }
          // Điều chỉnh nếu vượt biên trên
          if (absY < 0) {
            adjustedNodeY = Math.max(adjustedNodeY, -point[1])
          }
          // Điều chỉnh nếu vượt biên dưới
          if (absY > this.floorHeight) {
            adjustedNodeY = Math.min(adjustedNodeY, this.floorHeight - point[1])
          }
        }

        // Snap to grid 10cm
        adjustedNodeX = Math.round(adjustedNodeX / 10) * 10
        adjustedNodeY = Math.round(adjustedNodeY / 10) * 10

        // Tính lại center position từ adjusted node position
        const adjustedCenterX = adjustedNodeX + width / 2
        const adjustedCenterY = adjustedNodeY + height / 2

        // Apply vị trí đã validate
        args.element.offsetX = adjustedCenterX
        args.element.offsetY = adjustedCenterY

        // Xóa khỏi palette
        const index = this.uninstalledRooms.findIndex((r) => r.id === roomId)
        if (index !== -1) {
          this.uninstalledRooms.splice(index, 1)
        }
      }
    },

    // Xử lý khi đang kéo node (realtime)
    onDragging(args) {
      if (!args.element) return

      const node = args.element

      // Bỏ qua system nodes
      if (
        node.id === 'floorBorder' ||
        node.id === 'floorMask' ||
        node.id?.startsWith('dimLabel') ||
        node.id?.startsWith('edgeLabel_')
      ) {
        return
      }

      // Kiểm tra polygon phòng có nằm trong mặt sàn không
      if (node.addInfo && node.addInfo.polygon) {
        const polygon = node.addInfo.polygon

        // Kiểm tra vị trí hiện tại có trong mặt sàn không
        const isInside = this.isPolygonInsideFloor(polygon, node.offsetX, node.offsetY)

        if (!isInside) {
          // CANCEL dragging nếu ra ngoài (giống sodo-phong-zone)
          args.cancel = true
        }
      }
    },

    // Kiểm tra polygon có nằm hoàn toàn trong mặt sàn không
    isPolygonInsideFloor(polygon, centerX, centerY) {
      const xs = polygon.map((p) => p[0])
      const ys = polygon.map((p) => p[1])
      const minX = Math.min(...xs)
      const maxX = Math.max(...xs)
      const minY = Math.min(...ys)
      const maxY = Math.max(...ys)
      const width = maxX - minX
      const height = maxY - minY

      const nodeX = centerX - width / 2
      const nodeY = centerY - height / 2

      for (let point of polygon) {
        const absX = nodeX + point[0]
        const absY = nodeY + point[1]

        if (absX < 0 || absX > this.floorWidth || absY < 0 || absY > this.floorHeight) {
          return false
        }
      }

      return true
    },

    onPositionChange(args) {
      if (!args.newValue || !args.oldValue) return

      // FIX: Lấy node đúng cách từ args.source
      const node = args.source?.addInfo ? args.source : args.source?.properties?.nodes?.[0]

      if (!node) {
        console.warn('⚠️ No node found in onPositionChange')
        return
      }

      // Bỏ qua system nodes, temp nodes và grid nodes
      if (
        node.id === 'floorBorder' ||
        node.id === 'floorMask' ||
        node.id?.startsWith('dimLabel') ||
        node.id?.startsWith('edgeLabel') ||
        node.id?.startsWith('grid_') ||
        node.addInfo?.isTemp ||
        node.addInfo?.isGridCell ||
        node.addInfo?.isGridLabel ||
        node.addInfo?.isGridHeader
      ) {
        return
      }

      const diagram = this.$refs.diagramObj.ej2Instances

      // Kiểm tra polygon phòng có nằm trong mặt sàn không
      if (node.addInfo && node.addInfo.polygon) {
        const polygon = node.addInfo.polygon

        // Kiểm tra vị trí MỚI có nằm hoàn toàn trong mặt sàn không
        const newIsInside = this.isPolygonInsideFloor(polygon, args.newValue.offsetX, args.newValue.offsetY)

        if (!newIsInside) {
          // CANCEL và restore vị trí cũ
          args.cancel = true
          this.$nextTick(() => {
            const diagramNode = diagram.getObject(node.id)
            if (diagramNode) {
              diagramNode.offsetX = args.oldValue.offsetX
              diagramNode.offsetY = args.oldValue.offsetY
              diagram.dataBind()
            }
          })
          return
        }

        // KIỂM TRA OVERLAP VỚI CÁC PHÒNG KHÁC - CHỈ HIỂN THỊ THÔNG BÁO
        const otherRooms = diagram.nodes.filter(
          (n) =>
            n.id !== node.id &&
            n.id !== 'floorBorder' &&
            n.id !== 'floorMask' &&
            !n.id?.startsWith('dimLabel') &&
            !n.id?.startsWith('edgeLabel') &&
            !n.id?.startsWith('grid_') &&
            n.addInfo?.polygon &&
            !n.addInfo?.isTemp &&
            !n.addInfo?.isGridCell &&
            !n.addInfo?.isGridLabel &&
            !n.addInfo?.isGridHeader
        )

        if (otherRooms.length > 0) {
          // Tạo polygon với vị trí MỚI
          const newRoomPolygon = this.getRoomAbsolutePolygon(polygon, args.newValue.offsetX, args.newValue.offsetY)

          // Tạo polygon với vị trí CŨ
          const oldRoomPolygon = this.getRoomAbsolutePolygon(polygon, args.oldValue.offsetX, args.oldValue.offsetY)

          const overlappingRooms = []

          for (const otherRoom of otherRooms) {
            const otherPolygon = this.getRoomAbsolutePolygon(
              otherRoom.addInfo.polygon,
              otherRoom.offsetX,
              otherRoom.offsetY
            )

            const hasNewOverlap = this.isRoomOverlapping(newRoomPolygon, otherPolygon)
            const hadOldOverlap = this.isRoomOverlapping(oldRoomPolygon, otherPolygon)

            // Nếu có overlap mới (không phải overlap cũ)
            if (hasNewOverlap && !hadOldOverlap) {
              overlappingRooms.push(otherRoom.addInfo.roomName)
            }
          }

          // CHỈ HIỂN THỊ THÔNG BÁO, KHÔNG CHẶN
          if (overlappingRooms.length > 0) {
            const roomList = overlappingRooms.join(', ')
            this.$toast.warning(`Phòng "${node.addInfo.roomName}" đang chồng lên: ${roomList}`, {
              duration: 3000
            })
          }

          // Nếu đang kéo RA khỏi overlap
          const wasOverlapping = otherRooms.some((otherRoom) => {
            const otherPolygon = this.getRoomAbsolutePolygon(
              otherRoom.addInfo.polygon,
              otherRoom.offsetX,
              otherRoom.offsetY
            )
            return this.isRoomOverlapping(oldRoomPolygon, otherPolygon)
          })
          const isNowOverlapping = otherRooms.some((otherRoom) => {
            const otherPolygon = this.getRoomAbsolutePolygon(
              otherRoom.addInfo.polygon,
              otherRoom.offsetX,
              otherRoom.offsetY
            )
            return this.isRoomOverlapping(newRoomPolygon, otherPolygon)
          })

          if (wasOverlapping && !isNowOverlapping) {
            this.$toast.success(`Phòng "${node.addInfo.roomName}" đã tách khỏi vị trí chồng lấn`, {
              duration: 2000
            })
          }
        }
      }

      // Snap to grid 10cm - áp dụng cho args.newValue
      args.newValue.offsetX = Math.round(args.newValue.offsetX / 10) * 10
      args.newValue.offsetY = Math.round(args.newValue.offsetY / 10) * 10

      // Cập nhật addInfo
      if (node.addInfo) {
        node.addInfo.x = args.newValue.offsetX - node.width / 2
        node.addInfo.y = args.newValue.offsetY - node.height / 2
      }
    },

    // Khởi tạo diagram
    initializeDiagram() {
      this.loadInstalledRooms()
    },

    loadInstalledRooms() {
      if (!this.$refs.diagramObj || !this.$refs.diagramObj.ej2Instances) {
        console.error(' Diagram not ready yet')
        return
      }

      const diagram = this.$refs.diagramObj.ej2Instances

      this.installedRooms.forEach((room) => {
        const roomNode = this.createRoomNode(room)

        // Tính bounds của room để lấy kích thước
        const bounds = this.getRoomBounds(room)

        roomNode.offsetX = room.x + bounds.width / 2
        roomNode.offsetY = room.y + bounds.height / 2

        diagram.add(roomNode)

        const addedNode = diagram.nodes.find((n) => n.id === room.id)
      })

      this.$nextTick(() => {
        this.removeInstalledRoomsFromPalette()
      })
    },

    // Xóa các phòng đã bố trí khỏi Symbol Palette
    removeInstalledRoomsFromPalette() {
      const paletteObj = this.$refs.symbolpalette.ej2Instances
      if (!paletteObj || !paletteObj.palettes || paletteObj.palettes.length === 0) return

      const palette = paletteObj.palettes[0]
      if (!palette.symbols) return

      const installedIds = this.installedRooms.map((room) => room.id)

      palette.symbols = palette.symbols.filter((symbol) => !installedIds.includes(symbol.id))

      paletteObj.dataBind()
    },

    // Load dữ liệu phòng từ API
    async loadRoomData() {
      try {
        const [installed, uninstalled] = await Promise.all([this.getInstalledRooms(), this.getUninstalledRooms()])
        this.installedRooms = installed
        this.uninstalledRooms = uninstalled
      } catch (error) {
        console.error(' Error loading room data:', error)
        this.$toast.error('Không thể tải dữ liệu phòng')
      }
    },

    async getInstalledRooms() {
      let rs = await this.$root.context.post('/web-ecms/tainguyen-idc/khong-gian/get-ds-phong-daco-vitri', {
        id: this.modalItemID
      })
      return rs.data.map((room) => {
        // Parse polygon từ string
        let polygon = typeof room.KHONGGIAN === 'string' ? JSON.parse(room.KHONGGIAN) : room.KHONGGIAN

        // Normalize polygon về tọa độ tương đối (0,0 là góc trên trái)
        if (polygon && Array.isArray(polygon) && polygon.length > 0) {
          const xs = polygon.map((p) => p[0])
          const ys = polygon.map((p) => p[1])
          const minX = Math.min(...xs)
          const minY = Math.min(...ys)

          polygon = polygon.map((p) => [p[0] - minX, p[1] - minY])
        }

        return {
          id: 'NODE_' + room.ID,
          originId: room.ID,
          name: room.TENTAT || room.TEN,
          type: 'Server Room',
          status: 'active',
          area: room.DIENTICH,
          capacity: 50,
          usedCapacity: 50,
          x: room.TOADO_X,
          y: room.TOADO_Y,
          polygon: polygon,
          color: '#2196F3',
          mota: room.MOTA
        }
      })
    },

    async getUninstalledRooms() {
      let rs = await this.$root.context.post('/web-ecms/tainguyen-idc/khong-gian/get-ds-phong-chuaco-vitri', {
        id: this.modalItemID
      })

      return rs.data.map((room) => {
        // Parse polygon từ string
        let polygon = typeof room.KHONGGIAN === 'string' ? JSON.parse(room.KHONGGIAN) : room.KHONGGIAN

        // Normalize polygon về tọa độ tương đối (0,0 là góc trên trái)
        if (polygon && Array.isArray(polygon) && polygon.length > 0) {
          const xs = polygon.map((p) => p[0])
          const ys = polygon.map((p) => p[1])
          const minX = Math.min(...xs)
          const minY = Math.min(...ys)

          polygon = polygon.map((p) => [p[0] - minX, p[1] - minY])
        }

        return {
          id: 'NODE_' + room.ID,
          originId: room.ID,
          name: room.TENTAT || room.TEN,
          type: 'Server Room',
          status: 'active',
          area: room.DIENTICH,
          capacity: 50,
          usedCapacity: 50,
          x: room.TOADO_X,
          y: room.TOADO_Y,
          polygon: polygon,
          color: '#2196F3',
          mota: room.MOTA
        }
      })
    },

    // Lưu cấu hình diagram
    async onSave() {
      try {
        this.$root.showLoading(true)
        const diagram = this.$refs.diagramObj?.ej2Instances

        // FIX: Better validation
        if (!diagram) {
          throw new Error('Diagram chưa được khởi tạo')
        }

        // Lấy tất cả nodes (loại bỏ các node hệ thống như border, labels)
        const rooms = diagram.nodes
          .filter(
            (node) =>
              node.id !== 'floorBorder' &&
              node.id !== 'floorMask' &&
              node.id !== 'edgeLabelTop' &&
              node.id !== 'edgeLabelRight' &&
              node.id !== 'edgeLabelBottom' &&
              node.id !== 'edgeLabelLeft' &&
              !node.id?.startsWith('grid_') &&
              !node.addInfo?.isTemp &&
              !node.id.startsWith('dimLabel')
          )
          .map((node) => ({
            id: node.id,
            originId: node.addInfo?.originId || node.originId || node.id.replace('NODE_', ''),
            name: node.addInfo?.roomName || node.id,
            type: node.addInfo?.roomType || '',
            status: node.addInfo?.status || '',
            area: node.addInfo?.area || 0,
            capacity: node.addInfo?.capacity || 0,
            usedCapacity: node.addInfo?.usedCapacity || 0,
            x: Math.round(node.offsetX - node.width / 2), // Tọa độ góc trên-trái
            y: Math.round(node.offsetY - node.height / 2), // Tọa độ góc trên-trái
            polygon: node.addInfo?.polygon || [],
            color: node.addInfo?.color || '#2196F3',
            mota: node.addInfo?.mota || '',
            isTemp: node.addInfo?.isTemp
          }))

        // FIX: Check empty rooms
        if (rooms.length === 0) {
          this.$toast.warning('Không có phòng nào để lưu!')
          return
        }

        // KIỂM TRA OVERLAP TRƯỚC KHI LƯU
        const overlappingRooms = []
        for (let i = 0; i < rooms.length; i++) {
          const room1 = rooms[i]
          const node1 = diagram.nodes.find(n => n.id === room1.id)
          if (!node1 || !node1.addInfo?.polygon) continue

          const polygon1 = this.getRoomAbsolutePolygon(
            node1.addInfo.polygon,
            node1.offsetX,
            node1.offsetY
          )

          for (let j = i + 1; j < rooms.length; j++) {
            const room2 = rooms[j]
            const node2 = diagram.nodes.find(n => n.id === room2.id)
            if (!node2 || !node2.addInfo?.polygon) continue

            const polygon2 = this.getRoomAbsolutePolygon(
              node2.addInfo.polygon,
              node2.offsetX,
              node2.offsetY
            )

            if (this.isRoomOverlapping(polygon1, polygon2)) {
              overlappingRooms.push({
                room1: room1.name,
                room2: room2.name
              })
            }
          }
        }

        if (overlappingRooms.length > 0) {
          const message = overlappingRooms
            .map(pair => `"${pair.room1}" và "${pair.room2}"`)
            .join(', ')
          this.$toast.error(`Phát hiện các phòng chồng lấn: ${message}. Vui lòng điều chỉnh vị trí trước khi lưu!`)
          return
        }

        // TODO: Gọi API để lưu
        let rsOk = 0
        let rsFail = 0
        for (const room of rooms) {
          let rs = await this.$root.context.post('/web-ecms/tainguyen-idc/khong-gian/update-vitri-phong', {
            phongID: room.originId,
            toaDoX: room.x,
            toaDoY: room.y
          })
          if (rs.error_code == 'BSS-00000000') {
            rsOk++
          } else {
            rsFail++
          }
        }

        if (rsOk > 0) {
          this.$toast.success(`Lưu thành công ${rsOk} phòng`)
        }
        if (rsFail > 0) {
          this.$toast.error(`Lưu thất bại ${rsFail} phòng`)
        }
      } catch (error) {
        console.error(' Error saving diagram:', error)

        // FIX: Better error message
        const errorMsg = error.response?.data?.message || error.message || 'Lỗi khi lưu!'
        this.$toast.error(errorMsg)
      } finally {
        this.$root.showLoading(false)
      }
    },

    // Zoom in
    onZoomIn() {
      if (this.isZoomInDisabled) return
      const diagram = this.$refs.diagramObj.ej2Instances
      const newZoom = this.currentZoom * 1.2
      if (newZoom <= this.scrollSettings.maxZoom) {
        diagram.zoom(1.2, { x: 0.5, y: 0.5 })
        this.currentZoom = newZoom
      } else {
        // Zoom to exact max
        const factor = this.scrollSettings.maxZoom / this.currentZoom
        diagram.zoom(factor, { x: 0.5, y: 0.5 })
        this.currentZoom = this.scrollSettings.maxZoom
      }
      // Force update from diagram
      this.$nextTick(() => {
        if (diagram.scrollSettings && diagram.scrollSettings.currentZoom) {
          this.currentZoom = diagram.scrollSettings.currentZoom
        }
      })
    },

    // Zoom out
    onZoomOut() {
      if (this.isZoomOutDisabled) return
      const diagram = this.$refs.diagramObj.ej2Instances
      const newZoom = this.currentZoom / 1.2
      if (newZoom >= this.scrollSettings.minZoom) {
        diagram.zoom(1 / 1.2, { x: 0.5, y: 0.5 })
        this.currentZoom = newZoom
      } else {
        // Zoom to exact min
        const factor = this.scrollSettings.minZoom / this.currentZoom
        diagram.zoom(factor, { x: 0.5, y: 0.5 })
        this.currentZoom = this.scrollSettings.minZoom
      }
      // Force update from diagram
      this.$nextTick(() => {
        if (diagram.scrollSettings && diagram.scrollSettings.currentZoom) {
          this.currentZoom = diagram.scrollSettings.currentZoom
        }
      })
    },

    // Reset diagram
    onReset() {
      const diagram = this.$refs.diagramObj.ej2Instances
      diagram.reset()
      diagram.fitToPage()
      this.currentZoom = 1
    },

    // View all elements
    onViewAll() {
      if (!this.modalItemID) {
        this.$toast.warning('Chưa có thông tin mặt sàn!')
        return
      }
      this.$refs.viewAllElementsModal.show(this.modalItemID)
    },

    onContextMenuOpen(args) {
      const diagram = this.$refs.diagramObj.ej2Instances
      if (diagram.selectedItems.nodes.length > 0) {
        const node = diagram.selectedItems.nodes[0]
        // Bỏ qua grid cells, grid labels, grid headers, edge labels, floor border
        if (node.addInfo && (
          node.addInfo.isGridCell ||
          node.addInfo.isGridLabel ||
          node.addInfo.isGridHeader
        )) {
          args.cancel = true
          return
        }

        // Bỏ qua edge labels và floor border
        if (node.id && (
          node.id.startsWith('edgeLabel') ||
          node.id === 'floorBorder'
        )) {
          args.cancel = true
          return
        }

        args.hiddenItems = []
      } else {
        args.cancel = true
      }
    },

    async onContextMenuClick(args) {
      const diagram = this.$refs.diagramObj.ej2Instances

      if (args.item.id === 'viewDetail') {
        if (diagram.selectedItems.nodes.length > 0) {
          const node = diagram.selectedItems.nodes[0]
          this.showRoomDetail(node)
        }
      } else if (args.item.id === 'editPolygon') {
        // THÊM: Xử lý edit polygon
        if (diagram.selectedItems.nodes.length > 0) {
          const node = diagram.selectedItems.nodes[0]
          this.editRoomPolygon(node)
        }
      } else if (args.item.id === 'delete') {
        await this.$confirm('Bạn có chắc chắn muốn xóa phòng này?', 'Xác nhận', {
          confirmButtonText: 'Có',
          cancelButtonText: 'Không',
          type: 'warning'
        }).then(async () => {
          if (diagram.selectedItems.nodes.length > 0) {
            const node = diagram.selectedItems.nodes[0]
            const roomId = node.addInfo?.roomId

            if (roomId) {
              let rs = await this.$root.context.post('/web-ecms/tainguyen-idc/khong-gian/delete-vitri-phong', {
                id: roomId.replace('NODE_', '')
              })
              if (rs.error_code == 'BSS-00000000') {
                this.$toast.success('Xóa phòng thành công')
                this.returnRoomToPalette(roomId)
              } else {
                this.$toast.error('Xóa phòng thất bại')
              }
            }
          }
        })
        diagram.remove()
      }
    },

    returnRoomToPalette(roomId) {
      const roomBackup = this.allRoomsBackup.find((r) => r.id === roomId)
      if (!roomBackup) {
        return
      }

      const exists = this.uninstalledRooms.find((r) => r.id === roomId)
      if (exists) return

      const room = JSON.parse(JSON.stringify(roomBackup))
      delete room.x
      delete room.y
      delete room.mota

      this.uninstalledRooms.push(room)
    },

    showRoomDetail(node) {
      if (!node || !node.addInfo) return
      this.selectedRoomDetail = {
        id: node.addInfo.originId,
        name: node.addInfo.roomName,
        type: node.addInfo.roomType,
        area: node.addInfo.area,
        capacity: node.addInfo.capacity,
        usedCapacity: node.addInfo.usedCapacity,
        status: node.addInfo.status, // THÊM: status
        mota: node.addInfo.mota // THÊM: mô tả
      }

      this.$nextTick(() => {
        this.$refs.roomDetailModal.show()
      })
    },

    // THÊM: Sửa polygon của phòng
    editRoomPolygon(node) {
      if (!node || !node.addInfo) return

      const roomData = {
        id: node.addInfo.roomId,
        name: node.addInfo.roomName,
        type: node.addInfo.roomType,
        status: node.addInfo.status,
        area: node.addInfo.area,
        capacity: node.addInfo.capacity,
        usedCapacity: node.addInfo.usedCapacity,
        color: node.addInfo.color,
        mota: node.addInfo.mota || ''
      }

      // Lấy polygon hiện tại và chuyển về tọa độ tuyệt đối
      const currentPolygon = node.addInfo.polygon || []
      const nodeX = node.offsetX - node.width / 2
      const nodeY = node.offsetY - node.height / 2

      // Chuyển polygon từ tọa độ tương đối sang tọa độ tuyệt đối trên floor
      const absolutePolygon = currentPolygon.map((point) => [nodeX + point[0], nodeY + point[1]])

      // Lưu ID node đang edit
      this.editingNodeId = node.id

      // Lưu vị trí hiện tại (để giữ nguyên vị trí sau khi edit)
      this.tempDropPosition = {
        x: node.offsetX,
        y: node.offsetY
      }

      // Lưu dữ liệu phòng
      this.drawingRoomData = roomData

      // Mở modal với polygon hiện tại
      this.$refs.drawPolygonModal.show(roomData, absolutePolygon)
    },

    closeRoomDetail() {
      this.$refs.roomDetailModal.closeModal()
    },

    onModalClose() {
      this.selectedRoomDetail = null
    },

    // Xử lý khi người dùng xác nhận vẽ polygon
    async onPolygonDrawingConfirmed(result) {
      // Làm tròn area đến 2 chữ số thập phân
      result.area = Math.round(result.area * 100) / 100
      const { roomData, polygon, area } = result

      // Normalize polygon về tọa độ tương đối (0, 0 là góc trên trái)
      const xs = polygon.map((p) => p[0])
      const ys = polygon.map((p) => p[1])
      const minX = Math.min(...xs)
      const minY = Math.min(...ys)

      const normalizedPolygon = polygon.map((p) => [p[0] - minX, p[1] - minY])

      // THÊM: Kiểm tra nếu đang edit hay thêm mới
      if (this.editingNodeId) {
        // Đang edit phòng hiện có
        this.updateExistingRoomPolygon(normalizedPolygon, area, roomData.id.replace('NODE_', ''))
      } else {
        // Thêm phòng mới
        this.addNewRoomWithPolygon(roomData, normalizedPolygon, area)
      }

      // Reset trạng thái
      this.editingNodeId = null
      this.drawingRoomData = null
      this.tempDropPosition = null

      if (result.mode) {
        try {
          await this.$root.context.post('/web-ecms/tainguyen-idc/khong-gian/update-toado-phong', {
            phongID: roomData.id.replace('NODE_', ''),
            dsToaDo: polygon.map((p) => ({ toaDoX: p[0], toaDoY: p[1] }))
          })
        } catch (error) {
          console.error(' Error saving polygon:', error)
          this.$toast.error('Lỗi khi lưu polygon!')
        }
      } else {
        // Call API save polygon room - GỬI TỌA ĐỘ TUYỆT ĐỐI (polygon gốc)
        try {
          await this.$root.context.post('/web-ecms/tainguyen-idc/khong-gian/insert-toado-phong', {
            phongID: roomData.id.replace('NODE_', ''),
            dsToaDo: polygon.map((p) => ({ toaDoX: p[0], toaDoY: p[1] }))
          })
        } catch (error) {
          console.error(' Error saving polygon:', error)
          this.$toast.error('Lỗi khi lưu polygon!')
        }
      }
    },

    // THÊM: Update polygon cho phòng đã tồn tại
    async updateExistingRoomPolygon(normalizedPolygon, area, roomId) {
      const diagram = this.$refs.diagramObj.ej2Instances
      const node = diagram.getObject(this.editingNodeId)

      // Call API cập nhật lại diện tích
      let rs = await this.$root.context.post('/web-ecms/idc/phong-zone', {
        id: roomId,
        dientich: area
      })

      if (!node) {
        console.error(' Node not found:', this.editingNodeId)
        this.$toast.error('Không tìm thấy phòng để cập nhật!')
        return
      }

      // Cập nhật polygon trong installedRooms
      const roomIndex = this.installedRooms.findIndex((r) => r.id === node.addInfo.roomId)
      if (roomIndex !== -1) {
        this.installedRooms[roomIndex].polygon = normalizedPolygon
        this.installedRooms[roomIndex].area = area
      }

      // FIX: Cập nhật polygon trong allRoomsBackup để khi return về palette và kéo lại vào sẽ dùng polygon mới
      const backupIndex = this.allRoomsBackup.findIndex((r) => r.id === node.addInfo.roomId)
      if (backupIndex !== -1) {
        this.allRoomsBackup[backupIndex].polygon = normalizedPolygon
        this.allRoomsBackup[backupIndex].area = area
        console.log('✅ Updated polygon in backup for room:', node.addInfo.roomName)
      }

      // Tạo lại room object với polygon mới
      const updatedRoom = {
        id: node.addInfo.roomId,
        name: node.addInfo.roomName,
        type: node.addInfo.roomType,
        status: node.addInfo.status,
        area: area,
        capacity: node.addInfo.capacity,
        usedCapacity: node.addInfo.usedCapacity,
        polygon: normalizedPolygon,
        color: node.addInfo.color,
        mota: node.addInfo.mota || ''
      }

      // Apply polygon mới vào node
      this.applyPolygonToNode(node, updatedRoom)

      // Giữ nguyên vị trí
      if (this.tempDropPosition) {
        node.offsetX = this.tempDropPosition.x
        node.offsetY = this.tempDropPosition.y
      }

      // Refresh diagram
      diagram.dataBind()

      this.$toast.success(`Đã cập nhật polygon cho phòng ${updatedRoom.name}`)
    },

    // THÊM: Thêm phòng mới với polygon
    addNewRoomWithPolygon(roomData, normalizedPolygon, area) {
      // Cập nhật dữ liệu phòng với polygon vừa vẽ
      const updatedRoom = {
        ...roomData, // FIX: Dùng roomData từ result thay vì this.drawingRoomData
        polygon: normalizedPolygon,
        area: area,
        status: 'installed'
      }

      // Tính vị trí đặt phòng (center của bounding box)
      const bounds = this.getRoomBounds(updatedRoom)

      // Đặt phòng ở giữa mặt sàn
      const posX = this.floorWidth / 2
      const posY = this.floorHeight / 2

      // Lưu vị trí tuyệt đối (góc trên trái của bounding box)
      updatedRoom.x = posX - bounds.width / 2
      updatedRoom.y = posY - bounds.height / 2

      // Thêm vào danh sách installed rooms
      this.installedRooms.push(updatedRoom)

      // Xóa khỏi palette
      const index = this.uninstalledRooms.findIndex((r) => r.id === updatedRoom.id)
      if (index !== -1) {
        this.uninstalledRooms.splice(index, 1)
      }

      // Đợi nextTick để đảm bảo Vue update DOM
      this.$nextTick(() => {
        // Thêm node vào diagram
        const diagram = this.$refs.diagramObj.ej2Instances
        const node = this.createRoomNode(updatedRoom)

        // Set vị trí center
        node.offsetX = posX
        node.offsetY = posY

        diagram.add(node)

        // Force refresh Symbol Palette
        const paletteObj = this.$refs.symbolpalette
        if (paletteObj && paletteObj.ej2Instances) {
          paletteObj.ej2Instances.refresh()
        }
      })
    },

    // Xử lý khi người dùng hủy vẽ polygon
    onPolygonDrawingCancelled() {
      // Reset trạng thái
      this.editingNodeId = null
      this.drawingRoomData = null
      this.tempDropPosition = null
    },

    // ========== HELPER METHODS CHO POLYGON ==========

    // Tính centroid (tâm hình học) của polygon
    calculatePolygonCentroid(polygon) {
      if (!polygon || polygon.length < 3) {
        return { x: 0, y: 0 }
      }

      let area = 0
      let cx = 0
      let cy = 0

      for (let i = 0; i < polygon.length; i++) {
        const j = (i + 1) % polygon.length
        const xi = polygon[i][0]
        const yi = polygon[i][1]
        const xj = polygon[j][0]
        const yj = polygon[j][1]

        const cross = xi * yj - xj * yi
        area += cross
        cx += (xi + xj) * cross
        cy += (yi + yj) * cross
      }

      area *= 0.5

      // Tránh chia cho 0
      if (Math.abs(area) < 0.001) {
        // Fallback: Trung bình các điểm
        const sumX = polygon.reduce((sum, p) => sum + p[0], 0)
        const sumY = polygon.reduce((sum, p) => sum + p[1], 0)
        return {
          x: sumX / polygon.length,
          y: sumY / polygon.length
        }
      }

      cx = cx / (6 * area)
      cy = cy / (6 * area)

      return { x: cx, y: cy }
    },

    // ========== HELPER METHODS CHO OVERLAP DETECTION ==========

    // Lấy polygon tuyệt đối của phòng (chuyển từ tọa độ tương đối sang tuyệt đối)
    getRoomAbsolutePolygon(relativePolygon, centerX, centerY) {
      const xs = relativePolygon.map((p) => p[0])
      const ys = relativePolygon.map((p) => p[1])
      const minX = Math.min(...xs)
      const maxX = Math.max(...xs)
      const minY = Math.min(...ys)
      const maxY = Math.max(...ys)
      const width = maxX - minX
      const height = maxY - minY

      // Tính tọa độ góc trên trái
      const topLeftX = centerX - width / 2
      const topLeftY = centerY - height / 2

      // Chuyển polygon sang tọa độ tuyệt đối
      return relativePolygon.map((p) => [topLeftX + p[0], topLeftY + p[1]])
    },

    // Kiểm tra 2 phòng có overlap không (dùng thuật toán tương tự cage)
    isRoomOverlapping(polygon1Absolute, polygon2Absolute) {
      // Helper: Kiểm tra điểm có nằm trong polygon không (Ray casting)
      const isPointInPolygonHelper = (point, polygon) => {
        let inside = false
        for (let i = 0, j = polygon.length - 1; i < polygon.length; j = i++) {
          const xi = polygon[i][0]
          const yi = polygon[i][1]
          const xj = polygon[j][0]
          const yj = polygon[j][1]

          const intersect = yi > point[1] !== yj > point[1] && point[0] < ((xj - xi) * (point[1] - yi)) / (yj - yi) + xi

          if (intersect) inside = !inside
        }
        return inside
      }

      // Helper: Kiểm tra 2 đoạn thẳng có giao nhau không
      const doSegmentsIntersect = (p1, p2, p3, p4) => {
        const ccw = (A, B, C) => {
          return (C[1] - A[1]) * (B[0] - A[0]) > (B[1] - A[1]) * (C[0] - A[0])
        }
        return ccw(p1, p3, p4) !== ccw(p2, p3, p4) && ccw(p1, p2, p3) !== ccw(p1, p2, p4)
      }

      // Kiểm tra 1: Có điểm nào của polygon1 nằm trong polygon2 không?
      for (const point of polygon1Absolute) {
        if (isPointInPolygonHelper(point, polygon2Absolute)) {
          return true // Có overlap
        }
      }

      // Kiểm tra 2: Có điểm nào của polygon2 nằm trong polygon1 không?
      for (const point of polygon2Absolute) {
        if (isPointInPolygonHelper(point, polygon1Absolute)) {
          return true // Có overlap
        }
      }

      // Kiểm tra 3: Có cạnh nào của 2 polygon giao nhau không?
      for (let i = 0; i < polygon1Absolute.length; i++) {
        const p1 = polygon1Absolute[i]
        const p2 = polygon1Absolute[(i + 1) % polygon1Absolute.length]

        for (let j = 0; j < polygon2Absolute.length; j++) {
          const p3 = polygon2Absolute[j]
          const p4 = polygon2Absolute[(j + 1) % polygon2Absolute.length]

          if (doSegmentsIntersect(p1, p2, p3, p4)) {
            return true // Có overlap
          }
        }
      }

      // Không có overlap
      return false
    },
    handleModalClose() {

    }
  }
}
</script>

<style scoped>
/* Page layout */
.page-content {
  padding: 0px;
  height: 100%;
  position: relative;
}

/* Workspace */
.diagram-workspace {
  display: flex;
  height: 100%;
  -webkit-user-select: none;
  /* Cho Chrome, Safari, Opera */
  -moz-user-select: none;
  /* Cho Firefox */
  -ms-user-select: none;
  /* Cho IE/Edge */
  user-select: none;
}

.left-panel {
  width: 250px;
  background: white;
  overflow: hidden;
  display: flex;
  flex-direction: column;
  position: relative;
  z-index: 1000;
}

.panel-header {
  background: #2f6ee4;
  color: white;
  padding: 12px;
  text-align: center;
}

.panel-header h4 {
  margin: 0;
  font-size: 16px;
}

.panel-body {
  flex: 1;
  overflow: auto;
}

.right-panel {
  flex: 1;
  background: white;
  overflow: hidden;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  position: relative;
  z-index: 1;
}

/* Disabled button style */
.list-actions-top .list li a.disabled {
  opacity: 0.5;
  cursor: not-allowed;
  pointer-events: none;
}

.diagram-container {
  width: 100%;
  height: 100%;
  position: relative;
}

/* Info note - Thông tin đơn vị */
.info-note {
  background: #fff3cd;
  border: 1px solid #ffc107;
  border-radius: 4px;
  padding: 10px 15px;
  margin-bottom: 15px;
  color: #856404;
  font-size: 14px;
}

.info-note strong {
  color: #856404;
  font-weight: bold;
}

/* Responsive */
@media (max-width: 1200px) {
  .statistics-panel {
    flex-direction: column;
  }

  .diagram-workspace {
    flex-direction: column;
    height: auto;
  }

  .left-panel {
    width: 100%;
    height: 300px;
  }

  .right-panel {
    height: 600px;
  }

  .coordinate-display {
    margin-left: 0;
    margin-top: 10px;
    width: 100%;
  }
}
</style>

<style>
/* #diagram ::-webkit-scrollbar {
  display: none !important;
  width: 0 !important;
  height: 0 !important;
} */

#diagram {
  -ms-overflow-style: none !important;
  scrollbar-width: none !important;
  user-select: none !important;
}

.e-dragclone {
  z-index: 99999 !important;
  pointer-events: none !important;
}

.e-diagram .e-symbolpalette {
  z-index: 1000;
}

.modal-edit-matsan {
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
