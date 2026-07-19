// Function to initialize the map
function initializeVietnamMap() {
    // Return a Promise so caller can wait for it
    return new Promise(function(resolve, _reject) {
        // Load the complete GeoJSON data
        $.getJSON('/static/vendor/vietnam-map/vietnam-map.json', function(data) {
            // Store GeoJSON data globally for validation
            window.geoJsonData = data;
            console.log('GeoJSON data loaded successfully for validation');
            
            var paper = new ScaleRaphael('vietnammap', '1220.464', '1913.021'); 
            paper.scaleAll(0.4);

            paper.setStart();

            // Process each feature in the GeoJSON
            data.features.forEach(function(feature) {
                var provinceName = feature.properties.TinhThanh || feature.properties.name || 'Unknown';
                var geometry = feature.geometry;
                
                if (geometry.type === 'Polygon') {
                    // Convert polygon coordinates to Raphael path
                    var pathString = convertPolygonToPath(geometry.coordinates[0]);
                    var p = paper.path(pathString);
                    // Get region color for the province
                    var region = getProvinceRegion(provinceName);
                    var regionColor = region ? window.REGION_COLORS[region] : '#DFDFDF';
                    
                    p.attr({
                        stroke: "#FEFEFE", 
                        fill: regionColor,
                        title: provinceName,
                        "stroke-width": .2,
                        "stroke-linejoin": "round", 
                        "stroke-opacity": 0.25
                    });
                    (function(p, provinceName){
                        p.onclick = function(){
                            $(document).trigger('provinceClick', [{
                                name: provinceName,
                                properties: feature.properties
                            }]);
                        };
                    })(p[0], provinceName);
                } else if (geometry.type === 'MultiPolygon') {
                    // Handle MultiPolygon geometries
                    geometry.coordinates.forEach(function(polygon) {
                        var pathString = convertPolygonToPath(polygon[0]);
                        var p = paper.path(pathString);
                        // Get region color for the province
                        var region = getProvinceRegion(provinceName);
                        var regionColor = region ? window.REGION_COLORS[region] : '#DFDFDF';
                        
                        p.attr({
                            stroke: "#FEFEFE", 
                            fill: regionColor,
                            title: provinceName,
                            "stroke-width": .2,
                            "stroke-linejoin": "round", 
                            "stroke-opacity": 0.25
                        });
                        (function(p, provinceName){
                            p.onclick = function(){
                                $(document).trigger('provinceClick', [{
                                    name: provinceName,
                                    properties: feature.properties
                                }]);
                            };
                        })(p[0], provinceName);
                    });
                }
            });

            var vn = paper.setFinish();   

            // Enhanced hover with region highlighting - highlight when hover
            var over = function () {
                    var provinceName = this.attr('title');
                    var region = getProvinceRegion(provinceName);
                    
                    // Highlight the entire region when hovering
                    if (region) {
                        window.vietnamMapSet.forEach(function(element) {
                            var elementProvince = element.attr('title');
                            if (window.PROVINCE_REGIONS[elementProvince] === region) {
                                // Make color brighter/darker for highlight effect
                                var originalColor = window.REGION_COLORS[region];
                                var highlightedColor = lightenColor(originalColor, 20);
                                element.attr({fill: highlightedColor});
                            }
                        });
                    } else {
                        // For provinces not in any region, highlight with gray
                        this.c = this.c || this.attr("fill");
                        this.attr({fill: "#AAA"});
                    }
                },
                out = function () {
                    var provinceName = this.attr('title');
                    var region = getProvinceRegion(provinceName);
                    
                    // Return to original region colors when hover out
                    if (region) {
                        window.vietnamMapSet.forEach(function(element) {
                            var elementProvince = element.attr('title');
                            if (window.PROVINCE_REGIONS[elementProvince] === region) {
                                element.attr({fill: window.REGION_COLORS[region]});
                            }
                        });
                    } else {
                        this.attr({fill: this.c});
                    }
                };

            vn.hover(over, out);
            
            // Add Hoàng Sa text label
            var hoangSaText = paper.text(1070.01715625, 870.20190625, 'Hoàng Sa');
            hoangSaText.attr({
                'font-size': '15',
                'font-family': 'Arial, sans-serif',
                'fill': '#333333',
                'text-anchor': 'middle'
            });
            
            // Add Trường Sa text label
            var truongSaText = paper.text(1052.51715625, 1690.2019062499999, 'Trường Sa');
            truongSaText.attr({
                'font-size': '15',
                'font-family': 'Arial, sans-serif',
                'fill': '#333333',
                'text-anchor': 'middle'
            });
            
            // Store map instance globally for external access
            window.vietnamMapPaper = paper;
            window.vietnamMapSet = vn;
            window.vietnamMapMarkers = [];
            
            // Initialize marker validation error handler
            window.initMarkerValidationErrorHandler();
            
            // Resolve the promise when map is ready
            resolve();
            
        }).fail(function(jqXHR, textStatus, errorThrown) {
            console.error('Failed to load GeoJSON data:', textStatus, errorThrown);
            // Initialize map without validation data
            var paper = new ScaleRaphael('vietnammap', '1220.464', '1913.021'); 
            paper.scaleAll(0.4);
            window.vietnamMapPaper = paper;
            window.vietnamMapSet = paper.set();
            window.vietnamMapMarkers = [];
            
            // Resolve anyway so markers can still be added
            resolve();
        });
    });
}

// Initialize map on document ready
$(document).ready(function() {
    // Only initialize if vietnammap container exists (full page load)
    // AND if Vue component is not managing the initialization
    if (document.getElementById('vietnammap') && !window.isVueComponentUsage) {
        initializeVietnamMap().then(() => {
            // Map initialization successful, proceed with other map logic
            console.log('Vietnam Map initialized successfully.');
        }).catch(error => {
            console.error('Failed to initialize Vietnam Map:', error);
            // Initialize map without validation data
            var paper = new ScaleRaphael('vietnammap', '1220.464', '1913.021'); 
            paper.scaleAll(0.4);
            window.vietnamMapPaper = paper;
            window.vietnamMapSet = paper.set();
            window.vietnamMapMarkers = [];
        });
    }
    
    // Re-initialize map when returning to the page (for SPA navigation)
    $(document).on('show.bs.tab', function(e) {
        // Check if the activated tab contains the map
        var $tab = $(e.target).closest('[role="tab"]');
        if ($tab.attr('href') && document.querySelector($tab.attr('href') + ' #vietnammap')) {
            this.$nextTick(() => {
              initializeVietnamMap().then(() => {
                  // Map initialization successful, proceed with other map logic
                  console.log('Vietnam Map re-initialized successfully.');
              }).catch(error => {
                  console.error('Failed to re-initialize Vietnam Map:', error);
                  // Initialize map without validation data
                  var paper = new ScaleRaphael('vietnammap', '1220.464', '1913.021'); 
                  paper.scaleAll(0.4);
                  window.vietnamMapPaper = paper;
                  window.vietnamMapSet = paper.set();
                  window.vietnamMapMarkers = [];
              });
            })
        }
    });
});

// Export public function to initialize map from Vue component
window.initializeVietnamMapFromVue = function() {
    console.log('Map initialization called from Vue component');
    return initializeVietnamMap(); // Return the Promise
};

// Function to convert GeoJSON polygon coordinates to Raphael path string
function convertPolygonToPath(coordinates) {
    if (!coordinates || coordinates.length < 3) return '';
    
    // Convert first coordinate
    var lon = coordinates[0][0];
    var lat = coordinates[0][1];
    var x = (lon - 102) * 100 + 100;  // Scale longitude to 0-800 range
    var y = (24 - lat) * 100 + 100;   // Scale latitude to 0-1600 range (inverted)
    var path = 'M ' + x + ',' + y;
    
    // Convert remaining coordinates
    for (var i = 1; i < coordinates.length; i++) {
        lon = coordinates[i][0];
        lat = coordinates[i][1];
        x = (lon - 102) * 100 + 100;
        y = (24 - lat) * 100 + 100;
        path += ' L ' + x + ',' + y;
    }
    path += ' Z';
    return path;
}


// Store province elements for color management
window.provinceElements = {};

// Store GeoJSON data for validation
window.geoJsonData = null;

// Define provinces by regions
window.PROVINCE_REGIONS = {
    'Hà Nội': 'north', 'Hải Phòng': 'north', 'Lào Cai': 'north', 'Tuyên Quang': 'north', 'Thái Nguyên': 'north',
    'Phú Thọ': 'north', 'Lạng Sơn': 'north', 'Cao Bằng': 'north', 'Điện Biên': 'north', 'Sơn La': 'north',
    'Lai Châu': 'north', 'Bắc Ninh': 'north', 'Hưng Yên': 'north', 'Ninh Bình': 'north', 'Quảng Ninh': 'north',
    
    'Thanh Hóa': 'central', 'Nghệ An': 'central', 'Hà Tĩnh': 'central', 'Quảng Trị': 'central', 'Huế': 'central',
    'Đà Nẵng': 'central', 'Quảng Ngãi': 'central', 'Khánh Hòa': 'central', 'Gia Lai': 'central', 'Đắk Lắk': 'central',
    'Lâm Đồng': 'central',
    
    'TP. Hồ Chí Minh': 'south', 'Đồng Nai': 'south', 'Tây Ninh': 'south', 'Vĩnh Long': 'south', 'Đồng Tháp': 'south',
    'Cà Mau': 'south', 'An Giang': 'south', 'Cần Thơ': 'south'
};

// Region colors
window.REGION_COLORS = {
    north: '#4CAF50',    // Green
    central: '#FF9800',  // Orange  
    south: '#2196F3'     // Blue
};

// Function to color a specific province
function colorProvince(provinceName, color) {
    if (window.vietnamMapSet) {
        window.vietnamMapSet.forEach(function(element) {
            if (element.attr('title') === provinceName) {
                element.attr({ fill: color });
                window.provinceElements[provinceName] = element;
            }
        });
    }
}

// Function to color all provinces in a region
function colorRegion(regionName, color) {
    if (window.vietnamMapSet && window.PROVINCE_REGIONS) {
        window.vietnamMapSet.forEach(function(element) {
            var provinceName = element.attr('title');
            if (window.PROVINCE_REGIONS[provinceName] === regionName) {
                element.attr({ fill: color });
            }
        });
    }
}

// Function to reset all provinces to default gray color
function resetAllProvinces(defaultColor) {
    defaultColor = defaultColor || '#DFDFDF';
    if (window.vietnamMapSet) {
        window.vietnamMapSet.forEach(function(element) {
            element.attr({ fill: defaultColor });
        });
    }
}

// Function to get region of a province
function getProvinceRegion(provinceName) {
    return window.PROVINCE_REGIONS[provinceName] || null;
}

// Function to lighten a hex color
function lightenColor(color, percent) {
    // Remove # if present
    var hex = color.replace('#', '');
    
    // Parse r, g, b values
    var r = parseInt(hex.substr(0, 2), 16);
    var g = parseInt(hex.substr(2, 2), 16);
    var b = parseInt(hex.substr(4, 2), 16);
    
    // Lighten by adding to each component
    r = Math.min(255, Math.floor(r + (255 - r) * percent / 100));
    g = Math.min(255, Math.floor(g + (255 - g) * percent / 100));
    b = Math.min(255, Math.floor(b + (255 - b) * percent / 100));
    
    // Convert back to hex
    var newHex = ((1 << 24) + (r << 16) + (g << 8) + b).toString(16).slice(1);
    return '#' + newHex;
}

// Function to get all provinces in a region
function getRegionProvinces(regionName) {
    var provinces = [];
    for (var province in window.PROVINCE_REGIONS) {
        if (window.PROVINCE_REGIONS[province] === regionName) {
            provinces.push(province);
        }
    }
    return provinces;
}

// Export functions for external use
window.VietnamMapUtils = {
    // Convert lat/lng to map coordinates
    latLngToMapCoords: function(lat, lng) {
        var x = (lng - 102) * 100 + 100;
        var y = (24 - lat) * 100 + 100;
        return { x: x, y: y };
    },
    
    // Color a specific province
    colorProvince: function(provinceName, color) {
        colorProvince(provinceName, color);
    },
    
    // Add marker to map
    addMarker: function(lat, lng, options) {
        if (!window.vietnamMapPaper) {
            console.error('Map not initialized');
            return null;
        }
        
        // Check for duplicate markers
        if (this.isMarkerAtPosition(lat, lng)) {
            console.warn('Marker already exists at this position');
            return null;
        }
        
        var coords = this.latLngToMapCoords(lat, lng);
        var defaultOptions = {
            radius: 12,
            fill: '#FF5722',
            stroke: '#D32F2F',
            'stroke-width': 2,
            title: 'Marker',
            data: {}
        };
        
        var markerOptions = Object.assign({}, defaultOptions, options);
        
        var marker = window.vietnamMapPaper.circle(coords.x, coords.y, markerOptions.radius);
        marker.attr({
            fill: markerOptions.fill,
            stroke: markerOptions.stroke,
            'stroke-width': markerOptions['stroke-width'],
            title: markerOptions.title
        });
        
        // Store marker data with lat/lng only
        marker.markerData = {
            lat: lat,
            lng: lng,
            data: markerOptions.data
        };
        
        // Add click event
        marker.click(function() {
            if (markerOptions.onClick) {
                markerOptions.onClick(marker.markerData);
            }
            // Trigger both jQuery and custom events
            $(document).trigger('markerClick', [marker.markerData]);
            var event = new CustomEvent('markerClick', { 
                detail: marker.markerData 
            });
            document.dispatchEvent(event);
        });
        
        // Add hover effects
        marker.hover(
            function() {
                this.animate({r: markerOptions.radius * 1.5}, 200);
                // Add cursor pointer style
                if (this.node) {
                    this.node.style.cursor = 'pointer';
                }
                // Use the global hover handler
                window.handleMarkerHover({
                    marker: marker,
                    data: marker.markerData,
                    show: true
                });
            },
            function() {
                this.animate({r: markerOptions.radius}, 200);
                // Remove cursor pointer style
                if (this.node) {
                    this.node.style.cursor = 'default';
                }
                // Use the global hover handler
                window.handleMarkerHover({
                    marker: marker,
                    data: marker.markerData,
                    show: false
                });
            }
        );
        
        // Store marker reference
        if (!window.vietnamMapMarkers) {
            window.vietnamMapMarkers = [];
        }
        window.vietnamMapMarkers.push(marker);
        
        return marker;
    },
    
    // Remove marker from map
    removeMarker: function(marker) {
        if (marker && marker.remove) {
            marker.remove();
            var index = window.vietnamMapMarkers.indexOf(marker);
            if (index > -1) {
                window.vietnamMapMarkers.splice(index, 1);
            }
        }
    },
    
    // Clear all markers
    clearMarkers: function() {
        if (window.vietnamMapMarkers) {
            window.vietnamMapMarkers.forEach(function(marker) {
                if (marker && marker.remove) {
                    marker.remove();
                }
            });
            window.vietnamMapMarkers = [];
        }
    },
    
    // Add multiple markers from array
    addMarkers: function(markersData) {
        var markers = [];
        var self = this;
        markersData.forEach(function(markerData) {
            // Skip if marker already exists at this position
            if (!self.isMarkerAtPosition(markerData.lat, markerData.lng)) {
                var marker = self.addMarker(markerData.lat, markerData.lng, markerData.options || {});
                if (marker) {
                    markers.push(marker);
                }
            } else {
                console.warn('Skipping duplicate marker at ' + markerData.lat + ', ' + markerData.lng);
            }
        });
        return markers;
    },
    
    // Get map bounds
    getMapBounds: function() {
        return {
            north: 24,
            south: 8,
            east: 110,
            west: 102
        };
    },
    
    // Check if coordinates are within map bounds
    isWithinBounds: function(lat, lng) {
        var bounds = this.getMapBounds();
        return lat >= bounds.south && lat <= bounds.north && 
               lng >= bounds.west && lng <= bounds.east;
    },
    
    // Check if there's already a marker at the given position
    isMarkerAtPosition: function(lat, lng, tolerance) {
        tolerance = tolerance || 0.01; // Default tolerance of 0.01 degrees
        
        if (!window.vietnamMapMarkers) {
            return false;
        }
        
        for (var i = 0; i < window.vietnamMapMarkers.length; i++) {
            var marker = window.vietnamMapMarkers[i];
            if (marker && marker.markerData) {
                var latDiff = Math.abs(marker.markerData.lat - lat);
                var lngDiff = Math.abs(marker.markerData.lng - lng);
                
                if (latDiff <= tolerance && lngDiff <= tolerance) {
                    return true;
                }
            }
        }
        
        return false;
    },
    
    // Get map instance
    getMapInstance: function() {
        return {
            paper: window.vietnamMapPaper,
            set: window.vietnamMapSet,
            markers: window.vietnamMapMarkers
        };
    },
    
    // Region-based functions
    colorRegion: function(regionName, color) {
        colorRegion(regionName, color);
    },
    
    resetAllProvinces: function(defaultColor) {
        resetAllProvinces(defaultColor);
    },
    
    getProvinceRegion: function(provinceName) {
        return getProvinceRegion(provinceName);
    },
    
    getRegionProvinces: function(regionName) {
        return getRegionProvinces(regionName);
    },
    
    colorAllRegions: function(northColor, centralColor, southColor, permanent) {
        northColor = northColor || '#4CAF50';
        centralColor = centralColor || '#FF9800';
        southColor = southColor || '#2196F3';
        
        // If permanent is true, color all regions permanently
        // If false or undefined, just set the region colors for hover effect
        if (permanent) {
            this.colorRegion('north', northColor);
            this.colorRegion('central', centralColor);
            this.colorRegion('south', southColor);
        } else {
            // Just update the region colors for hover effect, keep default gray
            window.REGION_COLORS.north = northColor;
            window.REGION_COLORS.central = centralColor;
            window.REGION_COLORS.south = southColor;
        }
    },
    
    // Get region colors
    getRegionColors: function() {
        return window.REGION_COLORS;
    },
    
    // Set region colors
    setRegionColors: function(colors) {
        if (colors.north) window.REGION_COLORS.north = colors.north;
        if (colors.central) window.REGION_COLORS.central = colors.central;
        if (colors.south) window.REGION_COLORS.south = colors.south;
    },

    // Marker placement functionality
    enableMarkerMode: function() {
        if (!window.vietnamMapPaper) {
            console.error('Map not initialized');
            return false;
        }

        // Add click event listener to map container
        var mapContainer = document.getElementById('vietnammap');
        if (mapContainer) {
            mapContainer.style.cursor = 'crosshair';
            mapContainer.addEventListener('click', window.handleMapClick);
            markerMode = true;
            return true;
        }
        return false;
    },

    disableMarkerMode: function() {
        var mapContainer = document.getElementById('vietnammap');
        if (mapContainer) {
            mapContainer.style.cursor = 'default';
            mapContainer.removeEventListener('click', window.handleMapClick);
        }
        markerMode = false;
    },

    createMarkerAtPosition: function(mapX, mapY, options) {
        if (!window.vietnamMapPaper) {
            console.error('Map paper not initialized');
            return null;
        }

        // Convert map coordinates to lat/lng
        var latLng = mapCoordsToLatLng(mapX, mapY);
        
        // Check for duplicate markers using lat/lng
        if (this.isMarkerAtPosition(latLng.lat, latLng.lng)) {
            console.warn('Marker already exists at this position');
            return null;
        }

        var marker = window.vietnamMapPaper.circle(mapX, mapY, options.radius);
        marker.attr({
            fill: options.fill,
            stroke: options.stroke,
            'stroke-width': options['stroke-width'],
            title: options.title
        });

        // Store marker data with lat/lng only
        marker.markerData = {
            lat: latLng.lat,
            lng: latLng.lng,
            data: options.data
        };

        // Add click event
        marker.click(function() {
            if (options.data.onClick) {
                options.data.onClick(marker.markerData);
            }
            // Trigger both jQuery and custom events
            $(document).trigger('markerClick', [marker.markerData]);
            var event = new CustomEvent('markerClick', { 
                detail: marker.markerData 
            });
            document.dispatchEvent(event);
        });

        // Add hover effects
        marker.hover(
            function() {
                marker.animate({r: options.radius * 1.5}, 200);
                // Add cursor pointer style
                if (marker.node) {
                    marker.node.style.cursor = 'pointer';
                }
                // Use the global hover handler
                window.handleMarkerHover({
                    marker: marker,
                    data: marker.markerData,
                    show: true
                });
            },
            function() {
                marker.animate({r: options.radius}, 200);
                // Remove cursor pointer style
                if (marker.node) {
                    marker.node.style.cursor = 'default';
                }
                // Use the global hover handler
                window.handleMarkerHover({
                    marker: marker,
                    data: marker.markerData,
                    show: false
                });
            }
        );

        // Store marker reference
        if (!window.vietnamMapMarkers) {
            window.vietnamMapMarkers = [];
        }
        window.vietnamMapMarkers.push(marker);

        return marker;
    },

    screenToMapCoords: function(x, y, containerWidth, containerHeight) {
        // Use the same coordinate system as the map rendering
        // The map is scaled to 0.4 and centered
        var scale = 0.4;
        var mapWidth = 1220.464 * scale;
        var mapHeight = 1913.021 * scale;
        
        // Calculate the offset to center the map
        var offsetX = (containerWidth - mapWidth) / 2;
        var offsetY = (containerHeight - mapHeight) / 2;
        
        // Convert screen coordinates to map coordinates
        var mapX = (x - offsetX) / scale;
        var mapY = (y - offsetY) / scale;
        
        return { x: mapX, y: mapY };
    },
    
    // Reinitialize the map - useful when navigating back to page in SPA
    reinitialize: function() {
        console.log('Reinitializing Vietnam Map...');
        // Clear existing map elements
        if (window.vietnamMapPaper) {
            window.vietnamMapPaper.clear();
        }
        // Clear markers
        this.clearMarkers();
        // Reinitialize the map
        initializeVietnamMap();
    }
    
};

// Global marker mode state
var markerMode = false;
var markerCounter = 0;

// Global hover popup state
var hoverPopup = {
    show: false,
    data: null,
    position: { x: 0, y: 0 }
};

// Hover popup handler
window.handleMarkerHover = function(hoverDetail) {
    // Don't show popup if marker mode is active
    if (markerMode) {
        return;
    }
    
    if (hoverDetail.show) {
        // Show popup
        hoverPopup.show = true;
        hoverPopup.data = hoverDetail.data;
        
        // Get marker position on screen using the actual marker element
        if (hoverDetail.marker && hoverDetail.marker.node) {
            var markerElement = hoverDetail.marker.node;
            var rect = markerElement.getBoundingClientRect();
            
            hoverPopup.position = {
                x: rect.left + rect.width / 2, // Center horizontally
                y: rect.top - 15 // Move up a bit more for better arrow alignment
            };
        } else {
            // Fallback: calculate from coordinates
            var mapContainer = document.getElementById('vietnammap');
            if (mapContainer) {
                var containerRect = mapContainer.getBoundingClientRect();
                var markerCoords = window.VietnamMapUtils.latLngToMapCoords(
                    hoverDetail.data.lat, 
                    hoverDetail.data.lng
                );
                
                // Convert map coordinates to screen coordinates
                var scale = 0.4;
                var mapWidth = 1220.464 * scale;
                var mapHeight = 1913.021 * scale;
                var offsetX = (mapContainer.offsetWidth - mapWidth) / 2;
                var offsetY = (mapContainer.offsetHeight - mapHeight) / 2;
                
                var screenX = markerCoords.x * scale + offsetX;
                var screenY = markerCoords.y * scale + offsetY;
                
                hoverPopup.position = {
                    x: screenX + containerRect.left,
                    y: screenY + containerRect.top - 15 // Move up a bit more
                };
            }
        }
        
        // Trigger Vue component update
        var event = new CustomEvent('hoverPopupUpdate', {
            detail: hoverPopup
        });
        document.dispatchEvent(event);
    } else {
        // Hide popup - but let Vue component decide if it should stay visible
        var hideEvent = new CustomEvent('hoverPopupUpdate', {
            detail: {
                show: false,
                data: hoverDetail.data,
                position: hoverPopup.position
            }
        });
        document.dispatchEvent(hideEvent);
    }
};

// Point-in-polygon validation function
function isPointInPolygon(point, polygon) {
    var x = point.x, y = point.y;
    var inside = false;
    
    for (var i = 0, j = polygon.length - 1; i < polygon.length; j = i++) {
        var xi = polygon[i][0], yi = polygon[i][1];
        var xj = polygon[j][0], yj = polygon[j][1];
        
        if (((yi > y) !== (yj > y)) && (x < (xj - xi) * (y - yi) / (yj - yi) + xi)) {
            inside = !inside;
        }
    }
    
    return inside;
}

// Convert map coordinates back to lat/lng for validation
function mapCoordsToLatLng(mapX, mapY) {
    var lng = (mapX - 100) / 100 + 102;
    var lat = 24 - (mapY - 100) / 100;
    return { lat: lat, lng: lng };
}

// Check if a point is within any province using GeoJSON data
function isPointInAnyProvince(mapX, mapY) {
    if (!window.geoJsonData || !window.geoJsonData.features) {
        console.warn('GeoJSON data not loaded for validation');
        return false;
    }
    
    var latLng = mapCoordsToLatLng(mapX, mapY);
    var lat = latLng.lat;
    var lng = latLng.lng;
    
    // Quick bounds check first to avoid expensive polygon calculations
    if (lat < 8 || lat > 24 || lng < 102 || lng > 110) {
        return null; // Outside Vietnam bounds
    }
    
    // Check against each province's geometry from GeoJSON
    for (var i = 0; i < window.geoJsonData.features.length; i++) {
        var feature = window.geoJsonData.features[i];
        var provinceName = feature.properties.TinhThanh || feature.properties.name;
        var geometry = feature.geometry;
        
        if (!provinceName || !geometry) continue;
        
        // Check if point is within this province's geometry
        if (isPointInFeatureGeometry(lat, lng, geometry)) {
            return provinceName;
        }
    }
    
    return null;
}

// Check if a point is within a GeoJSON feature geometry
function isPointInFeatureGeometry(lat, lng, geometry) {
    if (geometry.type === 'Polygon') {
        return isPointInPolygonCoords(lat, lng, geometry.coordinates[0]);
    } else if (geometry.type === 'MultiPolygon') {
        // Check all polygons in the multipolygon
        for (var i = 0; i < geometry.coordinates.length; i++) {
            if (isPointInPolygonCoords(lat, lng, geometry.coordinates[i][0])) {
                return true;
            }
        }
    }
    return false;
}

// Check if a point is within polygon coordinates (lat/lng format)
function isPointInPolygonCoords(lat, lng, coordinates) {
    var inside = false;
    var j = coordinates.length - 1;
    
    for (var i = 0; i < coordinates.length; i++) {
        var xi = coordinates[i][0]; // longitude
        var yi = coordinates[i][1]; // latitude
        var xj = coordinates[j][0]; // longitude
        var yj = coordinates[j][1]; // latitude
        
        if (((yi > lat) !== (yj > lat)) && (lng < (xj - xi) * (lat - yi) / (yj - yi) + xi)) {
            inside = !inside;
        }
        j = i;
    }
    
    return inside;
}

// Check if there's already a marker at the given position (within a small radius)
function isMarkerAtPosition(mapX, mapY, tolerance) {
    tolerance = tolerance || 20; // Default tolerance of 20 pixels
    
    if (!window.vietnamMapMarkers) {
        return false;
    }
    
    for (var i = 0; i < window.vietnamMapMarkers.length; i++) {
        var marker = window.vietnamMapMarkers[i];
        if (marker && marker.markerData) {
            // Convert marker's lat/lng to map coordinates for comparison
            var markerCoords = window.VietnamMapUtils.latLngToMapCoords(marker.markerData.lat, marker.markerData.lng);
            var dx = Math.abs(markerCoords.x - mapX);
            var dy = Math.abs(markerCoords.y - mapY);
            var distance = Math.sqrt(dx * dx + dy * dy);
            
            if (distance <= tolerance) {
                return true;
            }
        }
    }
    
    return false;
}


// Global marker click handler
window.handleMapClick = function(event) {
    if (!markerMode) return;

    try {
        // Get click position relative to map container
        var rect = event.currentTarget.getBoundingClientRect();
        var x = event.clientX - rect.left;
        var y = event.clientY - rect.top;

        // Use the same coordinate conversion as the map rendering
        var coords = window.VietnamMapUtils.screenToMapCoords(x, y, rect.width, rect.height);
        
        // Validate that the click is within a province
        var provinceName = isPointInAnyProvince(coords.x, coords.y);
        
        if (!provinceName) {
            console.log(coords);
            // Throw error for invalid province
            throw new Error('Vị trí đánh dấu không hợp lệ. Vui lòng đánh dấu trong phạm vi một tỉnh/thành phố.');
        }
        
        // Check if there's already a marker at this position
        if (isMarkerAtPosition(coords.x, coords.y)) {
            // Throw error for duplicate marker
            throw new Error('Vị trí này đã có marker. Vui lòng chọn vị trí khác.');
        }
        
        // Create marker using the exact same coordinate system as the map
        markerCounter++;
        var markerOptions = {
            title: 'Marker ' + markerCounter,
            fill: '#FF5722',
            stroke: '#D32F2F',
            'stroke-width': 2,
            radius: 12,
            data: {
                name: 'Marker ' + markerCounter,
                type: 'Đánh dấu thủ công',
                id: markerCounter,
                province: provinceName
            }
        };

        // Create marker directly at the click position using map coordinates
        var marker = window.VietnamMapUtils.createMarkerAtPosition(coords.x, coords.y, markerOptions);
        
        // Trigger custom event for Vue component
        if (marker) {
            var event = new CustomEvent('markerPlaced', {
                detail: {
                    marker: marker,
                    counter: markerCounter,
                    position: coords,
                    province: provinceName
                }
            });
            document.dispatchEvent(event);
        }
    } catch (error) {
        // Dispatch error event for Vue component to handle
        var errorEvent = new CustomEvent('markerValidationError', {
            detail: {
                message: error.message
            }
        });
        document.dispatchEvent(errorEvent);
    }
};

// Store the error handler function to prevent duplicate listeners
window.markerValidationErrorHandler = null;

// Initialize marker validation error handler
window.initMarkerValidationErrorHandler = function() {
    // Remove old listener if it exists
    if (window.markerValidationErrorHandler) {
        document.removeEventListener('markerValidationError', window.markerValidationErrorHandler);
    }
    
    // Create new handler function
    window.markerValidationErrorHandler = function(event) {
        console.log('Marker validation error:', event.detail);
        
        // Show error message using toast notification
        if (event.detail && event.detail.message) {
            // Try to use Vue's toast if available
            if (window.vueApp && window.vueApp.$toast) {
                window.vueApp.$toast.error(event.detail.message);
            } else {
                // Fallback to console error
                console.error('Marker Error: ' + event.detail.message);
            }
        }
    };
    
    // Add the new listener
    document.addEventListener('markerValidationError', window.markerValidationErrorHandler);
};
