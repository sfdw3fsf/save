<template src="./index.template.html"></template>
<script>
import MarkerHoverPopup from './MarkerHoverPopup.vue'

export default {
  name: 'MapIDC',
  components: {
    MarkerHoverPopup
  },
  props: {
    idcList: {
      type: Array,
      default: () => []
    }
  },
  data() {
    return {
      selectedProvince: null,
      mapInstance: null,
      width: 800,
      height: 600,
      markerMode: false,
      markerCounter: 0,
      clickedMarkerId: null, // Track which marker was clicked
      hoverPopup: {
        show: false,
        data: null,
        position: { x: 0, y: 0 }
      }
    }
  },
  computed: {},
  async mounted() {
    await this.loadVietnamMapLibrary()
  },

  beforeDestroy() {
    // Cleanup marker mode when component is destroyed
    if (this.markerMode) {
      window.VietnamMapUtils.disableMarkerMode()
    }

    // Remove event listeners
    document.removeEventListener('hoverPopupUpdate', this.handleHoverPopupUpdate)
    
    // Reset Vue component usage flag
    window.isVueComponentUsage = false
  },
  methods: {
    mapApiDataToMarkers(apiData) {
      if (!Array.isArray(apiData)) {
        console.warn('API data is not an array:', apiData)
        return []
      }
      
      return apiData.map((item, index) => {
        // Map various possible field names to standard format
        const lat = item.LAT
        const lng = item.LNG
        
        // Validate coordinates
        if (!lat || !lng) {
          console.warn(`Invalid coordinates for item ${index}:`, item)
          return null
        }
        
        // Map other fields with fallbacks
        const name = item.TEN
        const id = item.ID
        const address = item.DIA_CHI
        
        const colors = ['#FF5722', '#2196F3', '#4CAF50', '#FF9800', '#9C27B0', '#00BCD4', '#795548', '#607D8B']
        const color = colors[index % colors.length]
        const radius = 8
        
        return {
          lat: parseFloat(lat),
          lng: parseFloat(lng),
          options: {
            title: name,
            fill: color,
            radius: radius,
            data: {
              name: name,
              id: id,
              address: address,
              operatingDate: item.THOI_DIEM,
              buildings: item.SO_TOANHA || 1,
              floors: item.SO_MATSAN || 1,
              racks: item.SO_RACK || 0,
              status: item.SUDUNG === 1 ? 'Đang hoạt động' : 'Tạm dừng',
              // Include all original data for reference
              originalData: item
            }
          }
        }
      }).filter(item => item !== null) // Remove invalid items
    },

    async loadVietnamMapLibrary() {
      try {
        this.$root.loading(true)
        
        // Set flag to prevent auto-initialization in map.js
        window.isVueComponentUsage = true
        
        await this.loadScript('/static/vendor/vietnam-map/js/raphael-2.3.0/raphael.min.js')
        await this.loadScript('/static/vendor/vietnam-map/js/scale.raphael.js')
        
        await this.$nextTick()
        
        if (this.$refs.mapContainer) {
          this.$refs.mapContainer.id = 'vietnammap'
          this.$refs.mapContainer.style.position = 'relative'
          this.$refs.mapContainer.style.width = '100%'
          this.$refs.mapContainer.style.height = '100%'
          this.$refs.mapContainer.style.display = 'flex'
          this.$refs.mapContainer.style.justifyContent = 'center'
          this.$refs.mapContainer.style.alignItems = 'center'
          
          // Load map.js
          await this.loadScript('/static/vendor/vietnam-map/js/map.js')
          
          // Wait for map.js to load
          await new Promise(resolve => setTimeout(resolve, 300))
          
          // Call the public function from map.js to initialize map and fetch GeoJSON
          if (window.initializeVietnamMapFromVue) {
            window.initializeVietnamMapFromVue()
          }
          
          // Wait for map initialization
          await new Promise(resolve => setTimeout(resolve, 500))
          
          // Then initialize Vue component
          this.initVietnamMap()
        }
      } catch (error) {
        console.error('Error loading Vietnam Map library:', error)
        if (this.$toast) {
          this.$toast.error('Không thể tải thư viện bản đồ. Vui lòng thử lại.')
        }
      } finally {
        this.$root.loading(false)
      }
    },
    loadScript(src) {
      return new Promise((resolve, reject) => {
        if (document.querySelector(`script[src="${src}"]`)) {
          resolve()
          return
        }
        const script = document.createElement('script')
        script.src = src
        script.onload = () => {
          resolve()
        }
        script.onerror = (error) => {
          reject(new Error(`Failed to load script: ${src}`))
        }
        document.head.appendChild(script)
      })
    },

    initVietnamMap() {
      try {
        if (!this.$refs.mapContainer) {
          if (this.$toast) {
            console.error('Container bản đồ không tìm thấy!')
          }
          return
        }

        // Check if map libraries are loaded
        if (!window.vietnamMapPaper || !window.vietnamMapSet) {
          console.error('Thư viện bản đồ chưa được tải đúng cách!')
          return
        }

        this.mapInstance = {
          paper: window.vietnamMapPaper,
          set: window.vietnamMapSet,
          markers: window.vietnamMapMarkers || [],
          destroy: () => {
            if (window.vietnamMapPaper && window.vietnamMapPaper.remove) {
              window.vietnamMapPaper.remove()
            }
          }
        }

        console.log('Vietnam Map initialized successfully')
        
        // Auto-add IDC list when map is initialized (no toast)
        if (this.idcList && this.idcList.length > 0) {
          const mappedMarkers = this.mapApiDataToMarkers(this.idcList)
          this.addMarkers(mappedMarkers, false)
          console.log(`Auto-added ${mappedMarkers.length} IDC markers to map`)
        }
        
        // Expose Vue app instance for toast notifications
        window.vueApp = this
        
        // Listen for province click events from map.js
        $(document)
          .off('provinceClick')
          .on('provinceClick', (event, province) => {
            this.handleProvinceClick(province)
          })

        // Listen for marker click events
        document.addEventListener('markerClick', (event) => {
          this.handleMarkerClick(event.detail)
        })

        // Listen for marker placement events
        document.addEventListener('markerPlaced', (event) => {
          this.handleMarkerPlaced(event.detail)
        })

        // Listen for hover popup update events from map.js
        document.addEventListener('hoverPopupUpdate', (event) => {
          this.handleHoverPopupUpdate(event.detail)
        })
      } catch (error) {
        console.error('Error initializing Vietnam Map:', error)
      }
    },

    handleProvinceClick(province) {
      this.selectedProvince = {
        name: province.name || province.TinhThanh
      }
    },

    handleProvinceHover(province) {
      // Handle hover events if needed
    },

    handleMarkerClick(markerData) {
      console.log('Marker clicked:', markerData)
      this.selectedProvince = {
        name: markerData.data.name || 'Marker',
        lat: markerData.lat,
        lng: markerData.lng,
        data: markerData.data,
        id: markerData.data.id || null
      }

      // Set clicked marker ID to keep popup visible
      this.clickedMarkerId = markerData.data.id || markerData.data.name

      // Ensure popup stays visible for clicked marker
      if (this.hoverPopup.data && this.hoverPopup.data.data.id === markerData.data.id) {
        this.hoverPopup.show = true
      }

      // Show toast notification
      console.info(`Đã chọn marker: ${markerData.data.name}`)
    },

    handleMarkerPlaced(eventDetail) {
      // Handle marker placement event from map.js
      console.log('Marker placed:', eventDetail)
      this.$toast.success(`Đã đặt marker ${eventDetail.counter} tại ${eventDetail.province || 'vị trí click'}`)

      // Update marker counter
      this.markerCounter = eventDetail.counter
    },

    handleHoverPopupUpdate(popupData) {
      // Don't hide popup if it's for a clicked marker
      if (!popupData.show && this.clickedMarkerId && this.hoverPopup.data) {
        const currentMarkerId = this.hoverPopup.data.data.id || this.hoverPopup.data.data.name
        if (currentMarkerId === this.clickedMarkerId) {
          // Keep popup visible for clicked marker
          return
        }
      }

      // Reset clicked marker if hovering over a different marker
      if (popupData.show && this.clickedMarkerId) {
        const newMarkerId = popupData.data.data.id || popupData.data.data.name
        if (newMarkerId !== this.clickedMarkerId) {
          this.clickedMarkerId = null
        }
      }

      this.hoverPopup = popupData
    },

    handlePopupClose() {
      // Close popup and reset clicked marker
      this.hoverPopup.show = false
      this.hoverPopup.data = null
      this.clickedMarkerId = null
    },

    handlePopupEdit(data) {
      // Handle edit action
      console.log('Edit marker:', data)
      this.$toast.info(`Chỉnh sửa marker: ${data.data.name}`)
    },

    handlePopupDetails(data) {
      // Handle details action
      console.log('View details:', data)
      this.$toast.info(`Xem chi tiết marker: ${data.data.name}`)
    },

    addMarker(lat, lng, options = {}) {
      // Check if map is initialized
      if (!window.VietnamMapUtils) {
        console.error('Map not initialized yet')
        return null
      }

      return window.VietnamMapUtils.addMarker(lat, lng, options)
    },

    removeMarker(marker) {
      if (window.VietnamMapUtils) {
        window.VietnamMapUtils.removeMarker(marker)
      }
    },

    clearMarkers() {
      window.VietnamMapUtils.clearMarkers()
      this.$emit('markers-cleared')
    },

    addMarkers(markersData, showToast = true) {
      // Check if map is initialized
      if (!window.VietnamMapUtils) {
        console.error('Map not initialized yet')
        return []
      }

      const addedMarkers = window.VietnamMapUtils.addMarkers(markersData)

      // Show summary of added markers only if showToast is true
      if (addedMarkers.length > 0 && showToast) {
        this.$toast.success(`Đã thêm ${addedMarkers.length} marker thành công`)
      }

      return addedMarkers
    },


    // Marker placement functionality - now uses methods from map.js
    toggleMarkerMode() {
      this.markerMode = !this.markerMode

      if (this.markerMode) {
        const success = window.VietnamMapUtils.enableMarkerMode()
        if (success) {
          // Hide popup when marker mode is enabled
          this.hoverPopup.show = false
          this.hoverPopup.data = null
            this.$toast.info(
              'Chế độ đánh dấu được bật. Nhấp vào bản đồ để đặt marker. Chỉ có thể đánh dấu trong phạm vi tỉnh/thành phố.'
            )
        } else {
          this.markerMode = false
            this.$toast.error('Không thể bật chế độ đánh dấu. Bản đồ chưa được khởi tạo.')
        }
      } else {
        window.VietnamMapUtils.disableMarkerMode()
          this.$toast.info('Chế độ đánh dấu đã tắt.')
      }
      
      // Emit event to parent component
      this.$emit('marker-mode-toggled', this.markerMode)
    }
  }
}
</script>

<style scoped>
.map-header {
  text-align: center;
  margin-bottom: 20px;
}

.map-header h3 {
  margin: 0 0 15px 0;
  font-size: 24px;
  color: #333;
}

.map-content {
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: flex-start;
  gap: 20px;
}

.map-wrapper {
  border: 1px solid #ddd;
  border-radius: 4px;
  height: 700px;
  width: 450px;
  overflow: hidden;
}

.vietnam-map {
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}

/* Marker mode styling */
.active {
  color: red !important;
  font-weight: bold;
}

.map-wrapper {
  position: relative;
}

.map-wrapper.marker-mode {
  cursor: crosshair;
}

.map-wrapper.marker-mode::after {
  content: 'Nhấp để đặt IDC';
  position: absolute;
  top: 8px;
  left: 50%;
  transform: translateX(-50%);
  background: rgba(33, 150, 243, 0.1); /* xanh nhạt, không gây rối mắt */
  color: #0d6efd; /* màu chữ xanh dễ đọc */
  padding: 6px 12px;
  border: 1px solid rgba(33, 150, 243, 0.3);
  border-radius: 6px;
  font-size: 13px;
  font-weight: 500;
  z-index: 1000;
  pointer-events: none;
  box-shadow: none;
  text-align: center;
}
</style>
