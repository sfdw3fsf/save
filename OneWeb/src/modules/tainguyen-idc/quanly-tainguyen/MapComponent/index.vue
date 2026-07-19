<template src="./index.template.html"></template>
<script>
import geojsonData from './region.json'
import { createMapOptions, loadRegionLayers, calculateBounds, createPopupContent } from './mapUtil'

const TOKEN = 'VNPT-f76bf568-b093-45cf-9761-f6339d3e7d9a'

export default {
  name: 'MapIDC',
  components: {
  },
  props: {
    idcList: {
      type: Array,
      default: () => []
    },
    isMapLarge: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      selectedProvince: null,
      mapVNPT: null,
      provinceLayers: [],
      markerLayers: [], // Lưu các marker layers để quản lý
      markerMode: false,
      markerCounter: 0,
      markerDataMap: {}, // Map để lưu marker data theo ID
      
    }
  },
  computed: {
  },
  watch: {
    idcList: {
      handler(newVal) {
        if (this.mapVNPT) {
          const mappedMarkers = this.mapApiDataToMarkers(newVal)
          this.addMarkersToVnptMap(mappedMarkers)
        }
      },
      immediate: false
    }
  },
  async mounted() {
    await this.$nextTick()
    this.initMap()
    this.setupPopupButtonDelegation()
  },

  beforeDestroy() {
    // Cleanup marker mode when component is destroyed
    if (this.markerMode && window.VietnamMapUtils) {
      window.VietnamMapUtils.disableMarkerMode()
    }

    // Cleanup VnptMap
    if (this.mapVNPT) {
      // Remove water mask layer
      if (this.waterMaskLayer && this.waterMaskLayer.remove) {
        this.waterMaskLayer.remove()
      }
      // Remove marker layers
      this.markerLayers.forEach((layer) => {
        if (layer && layer.remove) {
          layer.remove()
        }
      })
      // Remove province layers
      this.provinceLayers.forEach((layer) => {
        if (layer && layer.remove) {
          layer.remove()
        }
      })
      this.mapVNPT.remove()
    }
  },
  methods: {
    initMap() {
      // Tính bounds từ region.json để tạo maxBounds
      const bounds = calculateBounds(geojsonData)
      
      // Mở rộng bounds để tạo maxBounds cho mask (vùng màu trắng)
      const padding = 10.0 // Cùng padding với createWaterMask
      const maxBounds = [
        [bounds[0][0] - padding, bounds[0][1] - padding],
        [bounds[1][0] + padding, bounds[1][1] + padding]
      ]
      
      const options = createMapOptions({}, maxBounds)
      const center = [(bounds[0][0] + bounds[1][0]) / 2 - 2, (bounds[0][1] + bounds[1][1]) / 2 + 1]

      options.center = center
      this.mapVNPT = VnptMap.initMap('mapVNPT', TOKEN, options)

      this.mapVNPT.on('load', () => {
        // Tạo polygon mask để fill màu nước ngoài vùng Việt Nam
        this.createWaterMask()
        loadRegionLayers(this.mapVNPT, geojsonData, this.provinceLayers)
        
        // Set maxBounds để giới hạn pan chỉ trong vùng màu trắng
        if (this.mapVNPT.setMaxBounds) {
          this.mapVNPT.setMaxBounds(maxBounds)
        } else {
          // Nếu không có setMaxBounds, sử dụng event listener để constrain pan
          this.setupPanConstraints(maxBounds)
        }
        
        if (this.idcList && this.idcList.length > 0) {
          const mappedMarkers = this.mapApiDataToMarkers(this.idcList)
          this.addMarkersToVnptMap(mappedMarkers)
        }
      })
    },

    setupPanConstraints(maxBounds) {
      // Lưu maxBounds để sử dụng trong constraints
      this.maxBounds = maxBounds
      
      // Listen to move event và constrain pan
      this.mapVNPT.on('move', () => {
        const center = this.mapVNPT.getCenter()
        const bounds = this.mapVNPT.getBounds()
        
        // Kiểm tra nếu map vượt quá maxBounds
        if (bounds.getWest() < maxBounds[0][0] ||
            bounds.getEast() > maxBounds[1][0] ||
            bounds.getSouth() < maxBounds[0][1] ||
            bounds.getNorth() > maxBounds[1][1]) {
          
          // Constrain center về trong bounds
          let constrainedLng = Math.max(maxBounds[0][0], Math.min(maxBounds[1][0], center.lng))
          let constrainedLat = Math.max(maxBounds[0][1], Math.min(maxBounds[1][1], center.lat))
          
          this.mapVNPT.setCenter([constrainedLng, constrainedLat])
        }
      })
    },

    createWaterMask() {
      const bounds = calculateBounds(geojsonData)
      const padding = 10.0
      const outerBounds = [
        [bounds[0][0] - padding, bounds[0][1] - padding],
        [bounds[1][0] + padding, bounds[1][1] + padding]
      ]
      const outerPolygon = [
        [outerBounds[0][0], outerBounds[0][1]],
        [outerBounds[1][0], outerBounds[0][1]],
        [outerBounds[1][0], outerBounds[1][1]],
        [outerBounds[0][0], outerBounds[1][1]],
        [outerBounds[0][0], outerBounds[0][1]]
      ]
      const innerRings = []
      if (geojsonData.type === 'FeatureCollection') {
        geojsonData.features.forEach(feature => {
          if (feature.geometry && feature.geometry.type === 'Polygon') {
            // Reverse coordinates để tạo hole (counter-clockwise)
            const reversedCoords = feature.geometry.coordinates[0].slice().reverse()
            innerRings.push(reversedCoords)
          } else if (feature.geometry && feature.geometry.type === 'MultiPolygon') {
            feature.geometry.coordinates.forEach(polygon => {
              if (polygon[0]) {
                const reversedCoords = polygon[0].slice().reverse()
                innerRings.push(reversedCoords)
              }
            })
          }
        })
      }
      const polygonWithHoles = [outerPolygon, ...innerRings]
      const waterMaskLayer = VnptMap.initPolygon(polygonWithHoles, {
        id: 'water-mask',
        color: 'transparent',
        weight: 0,
        opacity: 0,
        fillOpacity: 1,
        fillColor: '#fff'
      })
      
      if (waterMaskLayer) {
        waterMaskLayer.addLayerTo(this.mapVNPT)
        this.waterMaskLayer = waterMaskLayer
      }
    },

    addMarkersToVnptMap(markersData) {
      if (!this.mapVNPT || !markersData || markersData.length === 0) return

      // Clear existing markers first
      this.clearMarkersFromMap()

      markersData.forEach((markerData, index) => {
        try {
          // Create marker using VnptMap.initPoint API
          const pointLayer = VnptMap.initPoint([markerData.lng, markerData.lat], {
            id: `marker-${markerData.options.data.id || index}`,
            iconUrl: this.getMarkerIcon(),
            iconWidth: 30,
            iconHeight: 30,
            data: markerData.options.data
          })

          if (pointLayer) {
            pointLayer.addLayerTo(this.mapVNPT)

            // Store marker data for hover popup
            const markerInfo = {
              lat: markerData.lat,
              lng: markerData.lng,
              data: markerData.options.data
            }

            // Lưu markerInfo vào component instance để có thể truy cập từ onclick
            const markerId = markerData.options.data.id || `marker-${index}`
            if (!this.markerDataMap) {
              this.markerDataMap = {}
            }
            this.markerDataMap[markerId] = markerInfo

            // Add popup với inline styles từ util
            const popupContent = createPopupContent(markerData)
            pointLayer.addPopup(popupContent)

            // Add click event
            if (pointLayer.on) {
              pointLayer.on('click', (e) => {
                this.handleMarkerClick(markerInfo)
                this.attachPopupButtonHandlers(markerInfo)
              })
            }

            // Store layer for cleanup
            this.markerLayers.push(pointLayer)
          }
        } catch (error) {
          console.error('Error adding marker to VnptMap:', error)
        }
      })
    },

    getMarkerIcon() {
      // Sử dụng icon IDC từ static folder
      return '/static/icons/location.png'
    },

    clearMarkersFromMap() {
      this.markerLayers.forEach((layer) => {
        if (layer && layer.remove) {
          layer.remove()
        }
      })
      this.markerLayers = []
    },

    mapApiDataToMarkers(apiData) {
      if (!Array.isArray(apiData)) {
        console.warn('API data is not an array:', apiData)
        return []
      }

      return apiData
        .map((item, index) => {
          const lat = item.TOADO_Y
          const lng = item.TOADO_X

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
        })
        .filter((item) => item !== null) // Remove invalid items
    },

    handleProvinceClick(province) {
      this.selectedProvince = {
        name: province.name || province.TinhThanh
      }
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
      console.info(`Đã chọn marker: ${markerData.data.name}`)
    },

    setupPopupButtonDelegation() {
      // Sử dụng event delegation để handle clicks từ document level
      this.popupButtonHandler = (e) => {
        const target = e.target.closest('[data-action]')
        if (!target) return

        const action = target.getAttribute('data-action')
        const markerId = target.getAttribute('data-marker-id')
        
        if (!markerId || !this.markerDataMap) return

        const markerData = this.markerDataMap[markerId]
        if (!markerData) return

        e.stopPropagation()
        e.preventDefault()

        if (action === 'edit') {
          this.handleEditMarker(markerData)
        } else if (action === 'details') {
          this.handleViewDetails(markerData)
        }
      }

      document.addEventListener('click', this.popupButtonHandler)
    },

    attachPopupButtonHandlers(markerData) {
      // Tìm các buttons trong popup và attach event listeners
      const popupContainer = document.querySelector('.ecogis-popup-content')
      if (!popupContainer) return

      const editButton = popupContainer.querySelector('[data-action="edit"]')
      const detailsButton = popupContainer.querySelector('[data-action="details"]')

      if (editButton) {
        editButton.addEventListener('click', (e) => {
          e.stopPropagation()
          e.preventDefault()
          this.handleEditMarker(markerData)
        })
      }

      if (detailsButton) {
        detailsButton.addEventListener('click', (e) => {
          e.stopPropagation()
          e.preventDefault()
          this.handleViewDetails(markerData)
        })
      }
    },

    handleEditMarker(markerData) {
      console.log('Edit marker:', markerData)
      this.$emit('edit-marker', markerData)
    },

    handleViewDetails(markerData) {
      console.log('View details:', markerData)
      this.$emit('idc-details-clicked', markerData)
    },
  }
}
</script>

<style scoped>
.map-wrapper {
  display: flex;
  justify-content: center;
  border: 1px solid #ddd;
  border-radius: 4px;
  height: 100%;
  width: 100%;
}
</style>

<style>
.ecogis-popup-content {
  border-radius: 8px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.15);
  overflow: hidden;
  padding: 0;
  background: #fff;
}
</style>
