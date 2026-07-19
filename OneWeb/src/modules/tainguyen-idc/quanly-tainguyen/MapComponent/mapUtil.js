/**
 * Tính toán bounds (min/max lat/lng) từ GeoJSON coordinates
 * @param {Object} geojson - GeoJSON Feature hoặc FeatureCollection
 * @returns {Array} Bounds array [[minLng, minLat], [maxLng, maxLat]]
 */
export function calculateBounds(geojson) {
  let minLng = Infinity
  let maxLng = -Infinity
  let minLat = Infinity
  let maxLat = -Infinity

  const processCoordinates = (coords) => {
    if (Array.isArray(coords[0])) {
      coords.forEach(coord => processCoordinates(coord))
    } else {
      const [lng, lat] = coords
      minLng = Math.min(minLng, lng)
      maxLng = Math.max(maxLng, lng)
      minLat = Math.min(minLat, lat)
      maxLat = Math.max(maxLat, lat)
    }
  }

  if (geojson.type === 'FeatureCollection') {
    geojson.features.forEach(feature => {
      if (feature.geometry) {
        processCoordinates(feature.geometry.coordinates)
      }
    })
  } else if (geojson.type === 'Feature' && geojson.geometry) {
    processCoordinates(geojson.geometry.coordinates)
  }

  return [[minLng, minLat], [maxLng, maxLat]]
}

/**
 * Tạo map options
 * @param {Object} customOptions - Options tùy chỉnh
 * @param {Array} maxBounds - Max bounds để giới hạn pan [[minLng, minLat], [maxLng, maxLat]]
 * @returns {Object} Map options
 */
export function createMapOptions(customOptions = {}, maxBounds = null) {
  const defaultOptions = {
    showZoomControl: true,
    showFullscreen: true,
    center: [105.8, 16.0],
    minZoom: 4.8,
    zoom: 4.8,
    maxZoom: 8,
    dragPan: true, // Bật kéo map
    boxZoom: true, // Bật zoom bằng box
    doubleClickZoom: true, // Bật zoom bằng double click
    scrollWheelZoom: true // Bật zoom bằng scroll wheel
  }

  // Thêm maxBounds nếu có
  if (maxBounds) {
    defaultOptions.maxBounds = maxBounds
  }

  return { ...defaultOptions, ...customOptions }
}

/**
 * Tạo style cho region layer
 * @param {String} regionColor - Màu của miền
 * @param {String} uniqueId - ID unique cho layer
 * @returns {Object} Style object
 */
export function createRegionStyle(regionColor, uniqueId) {
  return {
    id: uniqueId,
    color: regionColor,
    weight: 1.5,
    opacity: 1,
    fillOpacity: 0.15,
    fillColor: regionColor
  }
}

/**
 * Tạo hover style cho region layer
 * @param {String} regionColor - Màu của miền
 * @returns {Object} Hover style object
 */
export function createHoverStyle(regionColor) {
  return {
    weight: 2,
    fillOpacity: 0.3,
    fillColor: regionColor
  }
}


/**
 * Load và hiển thị GeoJSON layers trên map
 * @param {Object} mapInstance - Instance của VnptMap
 * @param {Object} geojsonData - GeoJSON FeatureCollection
 * @param {Array} layers - Array để lưu các layers
 */
export function loadRegionLayers(mapInstance, geojsonData, layers) {
  if (!geojsonData || geojsonData.type !== 'FeatureCollection' || !Array.isArray(geojsonData.features)) {
    return
  }

  const regionColors = {
    'Miền Bắc': '#6ba3d4',
    'Miền Trung': '#70d892',
    'Miền Nam': '#ff8a8a'
  }

  const features = geojsonData.features
  const regions = ['Miền Bắc', 'Miền Trung', 'Miền Nam']
  
  regions.forEach((regionName) => {
    const feature = features.find(f => {
      const name = (f.properties && f.properties.name) || ''
      return name === regionName
    })

    if (!feature || !feature.geometry) return

    const regionColor = regionColors[regionName]
    const uniqueId = `region-${regionName.replace(/\s+/g, '-')}`

    const style = createRegionStyle(regionColor, uniqueId)

    const regionFeature = {
      id: uniqueId,
      type: 'Feature',
      geometry: feature.geometry,
      properties: {
        name: regionName,
        ...feature.properties
      }
    }

    const polygonLayer = VnptMap.initPolygon(regionFeature, style)
    if (!polygonLayer) return

    polygonLayer.id = uniqueId
    polygonLayer.name = regionName
    polygonLayer.addLayerTo(mapInstance)

    const hoverStyle = createHoverStyle(regionColor)

    if (polygonLayer.on) {
      polygonLayer.on('mouseenter', () => {
        polygonLayer.setStyle(hoverStyle)
      })

      polygonLayer.on('mouseout', () => {
        polygonLayer.setStyle(style)
      })
    }

    layers.push(polygonLayer)
  })
}

/**
 * Tạo HTML content cho popup marker với inline styles giống thiết kế
 * @param {Object} markerData - Data của marker
 * @returns {String} HTML string
 */
function escapeHtml(text = '') {
  return String(text)
    .replace(/&/g, '&amp;')
    .replace(/</g, '&lt;')
    .replace(/>/g, '&gt;')
    .replace(/"/g, '&quot;')
    .replace(/'/g, '&#39;')
}

export function createPopupContent(markerData) {
  const name = markerData.options.title || markerData.options.data.name || 'Chưa có tên'
  const id = markerData.options.data.id || ''
  const address = markerData.options.data.address || 'Chưa có thông tin'
  const status = markerData.options.data.status
  const operatingDate = markerData.options.data.operatingDate || ''
  const buildings = markerData.options.data.buildings || 0
  const floors = markerData.options.data.floors || 0
  const racks = markerData.options.data.racks || 0
  const truncatedAddress = address.length > 30 ? address.substring(0, 30) + '...' : address
  const safeAddressTooltip = escapeHtml(address)
  const safeTruncatedAddress = escapeHtml(truncatedAddress)
  const statusBgColor = status === 'Đang hoạt động' ? '#e8f5e9' : '#fff3e0'
  const statusTextColor = status === 'Đang hoạt động' ? '#2e7d32' : '#e65100'
  const statusIconBg = status === 'Đang hoạt động' ? '#4caf50' : '#ff9800'

  return `
    <div style="min-width: 240px; max-width: 280px; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial, sans-serif;">
      <!-- Header -->
      <div style="padding: 10px 12px 8px 12px; background: #fff; border-bottom: 1px solid #f0f0f0; display: flex; justify-content: space-between; align-items: center;">
        <h4 style="margin: 0; font-size: 14px; font-weight: 600; color: #333; flex: 1;">${name}</h4>
      </div>
      
      <!-- Status Bar -->
      ${status ? `
      <div style="background: ${statusBgColor}; padding: 6px 12px; display: flex; align-items: center; gap: 6px;">
        <div style="width: 16px; height: 16px; border-radius: 50%; background: ${statusIconBg}; display: flex; align-items: center; justify-content: center; flex-shrink: 0;">
          <span style="color: #fff; font-size: 10px; font-weight: bold; line-height: 1;">i</span>
        </div>
        <span style="font-size: 12px; color: ${statusTextColor}; font-weight: 500;">${status}</span>
      </div>
      ` : ''}
      
      <!-- Main Info -->
      <div style="padding: 10px 12px; background: #fff;">
        ${id ? `
        <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 8px;">
          <span style="font-size: 11px; color: #666; font-weight: 500;">ID IDC:</span>
          <span style="font-size: 11px; color: #333; font-weight: 500;">${id}</span>
        </div>
        ` : ''}
        
        <div style="display: flex; justify-content: space-between; align-items: flex-start; margin-bottom: 8px;">
          <span style="font-size: 11px; color: #666; font-weight: 500; flex-shrink: 0; margin-right: 6px;">Địa chỉ:</span>
          <span title="${safeAddressTooltip}" style="font-size: 11px; color: #333; text-align: right; flex: 1; word-break: break-word; line-height: 1.3;">${safeTruncatedAddress}</span>
        </div>
        
        ${operatingDate ? `
        <div style="display: flex; justify-content: space-between; align-items: center;">
          <span style="font-size: 11px; color: #666; font-weight: 500;">Thời gian VH:</span>
          <span style="font-size: 11px; color: #333; font-weight: 500;">${operatingDate}</span>
        </div>
        ` : ''}
      </div>
      
      <!-- Statistics Section -->
      <div style="padding: 0 12px 10px 12px; background: #fff;">
        <div style="display: flex; gap: 6px;">
          <div style="flex: 1; background: #f5f5f5; border-radius: 4px; padding: 8px 6px; text-align: center;">
            <div style="font-size: 18px; font-weight: 700; color: #333; margin-bottom: 2px;">${buildings}</div>
            <div style="font-size: 10px; color: #666; font-weight: 500;">Tòa nhà</div>
          </div>
          <div style="flex: 1; background: #f5f5f5; border-radius: 4px; padding: 8px 6px; text-align: center;">
            <div style="font-size: 18px; font-weight: 700; color: #333; margin-bottom: 2px;">${floors}</div>
            <div style="font-size: 10px; color: #666; font-weight: 500;">Mặt sàn</div>
          </div>
          <div style="flex: 1; background: #f5f5f5; border-radius: 4px; padding: 8px 6px; text-align: center;">
            <div style="font-size: 18px; font-weight: 700; color: #333; margin-bottom: 2px;">${racks}</div>
            <div style="font-size: 10px; color: #666; font-weight: 500;">Rack</div>
          </div>
        </div>
      </div>
      
      <!-- Footer Buttons -->
      <div style="padding: 8px 12px; background: #fff; border-top: 1px solid #f0f0f0; display: flex; gap: 6px;">
        <button data-marker-id="${id}" data-action="edit" style="flex: 1; background: #2196F3; color: #fff; border: none; border-radius: 4px; padding: 6px 10px; font-size: 12px; font-weight: 500; cursor: pointer; display: flex; align-items: center; justify-content: center; gap: 4px; transition: background 0.2s;" onmouseover="this.style.background='#1976D2'" onmouseout="this.style.background='#2196F3'">
          <i class="fa fa-edit"></i>
          <span>Sửa</span>
        </button>
        <button data-marker-id="${id}" data-action="details" style="flex: 1; background: #757575; color: #fff; border: none; border-radius: 4px; padding: 6px 10px; font-size: 12px; font-weight: 500; cursor: pointer; display: flex; align-items: center; justify-content: center; gap: 4px; transition: background 0.2s;" onmouseover="this.style.background='#616161'" onmouseout="this.style.background='#757575'">
         <i class="fa fa-info-circle"></i>
          <span>Chi tiết</span>
        </button>
      </div>
    </div>
  `
}

