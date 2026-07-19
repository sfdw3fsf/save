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
 * @returns {Object} Map options
 */
export function createMapOptions(customOptions = {}) {
  const defaultOptions = {
    showZoomControl: false,
    showFullscreen: false,
    center: [105.8, 16.0],
    minZoom: 5,
    maxZoom: 5,
    dragPan: false, // Không cho phép kéo map
    boxZoom: false, // Không cho phép zoom bằng box
    doubleClickZoom: false, // Không cho phép zoom bằng double click
    scrollWheelZoom: false // Không cho phép zoom bằng scroll wheel
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
    weight: 3,
    opacity: 1,
    fillOpacity: 0.3,
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
    weight: 4,
    fillOpacity: 0.5,
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
    'Miền Bắc': '#4a90e2',
    'Miền Trung': '#50c878',
    'Miền Nam': '#ff6b6b'
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

