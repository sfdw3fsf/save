<template>
  <div class="map-canvas-box">
    <div class="map-canvas-main">
      <div id="mapVNPTChonToaDo" class="mapVNPTChonToaDo"></div>
    </div>
  </div>
</template>
<script>
import VnptMap from '@vnpt/maps-onebss'
const TOKEN = 'VNPT-f76bf568-b093-45cf-9761-f6339d3e7d9a'
export default {
  components: {},
  name: 'BanDoVNPT',
  props: {
    position: null,
    diachi: null,
    mapTypeId: null,
    zoomDefault: null
  },
  computed: {
    getPositionCenter () { return this.positionCenter },
    getNodes () {
      return this.nodes
    }
  },
  async created () {
  },
  data () {
    return {
      pointSearch: null,
      basemapApi: null,
      mapVNPT: null,
      zoomLevel: this.zoomDefault,
      mapOptions: {
        zoomControl: false,
        scaleControl: true,
        disableDefaultUi: true,
        streetViewControl: false,
        mapTypeControl: false,
        fullscreenControl: false,
        maxZoom: this.maxZoom,
        draggableCursor: 'default',
        draggingCursor: 'pointer',
        disableDoubleClickZoom: true
      },
      maxZoom: 20,
      map: null,
      nodes: [],
      positionCenter: this.position
    }
  },
  watch: {
    mapTypeId (val) {
    },
    position (val) {
      console.log('🚀 ~ file: BanDoVNPT.vue:92 ~ position ~ val:', val)
    }
  },
  beforeDestroy () {
    this.mapVNPT = null
  },
  async mounted () {
    let vm = this
    let options = {
      showZoomControl: false,
      id: this.generateId()
    }
    console.log('🚀 ~ file: BanDoVNPT.vue:68 ~ mounted ~ options:', options)
    vm.mapVNPT = VnptMap.initMap('mapVNPTChonToaDo', options)
    setTimeout(() => this.mapVNPT.updateSized(), 10)
    vm.basemapApi = VnptMap.initBasemapApi()
    vm.mapVNPT.on('click', (e) => {
      if (e.originalEvent.detail === 2) {
        this.dblclickMap(e)
      }
    })
    let coord = [parseFloat(this.position.lng), parseFloat(this.position.lat)]
    let point = VnptMap.initPoint(coord, {
      id: 'NODE_' + this.generateId(),
      iconUrl: require('../placeholder.svg'),
      iconWidth: 30,
      iconHeight: 30,
      isDraggable: false
    })
    point.addLayerTo(this.mapVNPT)
    this.mapVNPT.flyTo(coord, 15)
  },
  methods: {
    generateId () {
      const randomId = Math.random().toString(36).substr(2, 9) + '_' + Date.now().toString(36)
      return randomId
    },
    searchLocation: function (searchAddressInput) {
      var re = new RegExp(/-?(\d*\.)?\d+,\s*-?(\d*\.)?\d+/)
      if (re.test(searchAddressInput)) {
        let arr = searchAddressInput.split(',')
        let coord = [parseFloat(arr[1]), parseFloat(arr[0])]
        this.pointSearch && this.pointSearch.remove()
        this.pointSearch = VnptMap.initPoint(coord, {
          id: 'NODE_' + this.generateId(),
          iconUrl: require('../position.png'),
          iconWidth: 30,
          iconHeight: 30,
          isDraggable: true
        })
        this.pointSearch.onDrag('drag', (e) => {
          this.$emit('dblclickMap', { lat: e.coordinates.lat, lng: e.coordinates.lng })
        })
        this.pointSearch.addLayerTo(this.mapVNPT)
        this.mapVNPT.flyTo(coord, 15)
        this.$emit('dblclickMap', { lat: parseFloat(arr[0]), lng: parseFloat(arr[1]) })
      } else {
        this.basemapApi.getLonLatByAddress(searchAddressInput + ', Việt Nam', TOKEN, (evt) => {
          console.log('🚀 ~ file: BanDoVNPT.vue:561 ~ this.basemapApi.getLonLatByAddress ~ evt:', evt)
          if (evt.status) {
            let result
            for (let i in evt.result) {
              if (evt.result[i].address === searchAddressInput + ', Việt Nam') result = evt.result[i]
            }
            if (!result) {
              result = evt.result[0]
              {
                let coord = [parseFloat(result.long), parseFloat(result.lat)]
                this.pointSearch && this.pointSearch.remove()
                this.pointSearch = VnptMap.initPoint(coord, {
                  id: 'NODE_' + this.generateId(),
                  iconUrl: require('../position.png'),
                  iconWidth: 30,
                  iconHeight: 30,
                  isDraggable: true
                })
                this.pointSearch.onDrag('drag', (e) => {
                  this.$emit('dblclickMap', { lat: e.coordinates.lat, lng: e.coordinates.lng })
                })
                this.pointSearch.addLayerTo(this.mapVNPT)
                this.mapVNPT.flyTo(coord, 15)
                this.$emit('dblclickMap', { lat: parseFloat(result.lat), lng: parseFloat(result.long) })
              }
            } else {
              let coord = [this.position.lng, this.position.lat]
              this.mapVNPT.flyTo(coord, 15)
            }
          } else {
            let coord = [this.position.lng, this.position.lat]
            this.mapVNPT.flyTo(coord, 15)
          }
        })
      }
    },
    dragendPositionNode (event, node) {
      node.position.lat = event.latLng.lat()
      node.position.lng = event.latLng.lng()
      this.$emit('dblclickMap', { lat: event.latLng.lat(), lng: event.latLng.lng() })
    },
    dblclickMap (event) {
      console.log('🚀 ~ file: BanDoVNPT.vue:157 ~ dblclickMap ~ event:', event.lngLat)
      let coord = [parseFloat(event.lngLat.lng), parseFloat(event.lngLat.lat)]
      this.pointSearch && this.pointSearch.remove()
      this.pointSearch = VnptMap.initPoint(coord, {
        id: 'NODE_' + this.generateId(),
        iconUrl: require('../position.png'),
        iconWidth: 30,
        iconHeight: 30,
        isDraggable: true
      })
      this.pointSearch.onDrag('drag', (e) => {
        this.$emit('dblclickMap', { lat: e.coordinates.lat, lng: e.coordinates.lng })
      })
      this.pointSearch.addLayerTo(this.mapVNPT)
      this.mapVNPT.flyTo(coord, 15)
      this.$emit('dblclickMap', { lat: parseFloat(event.lngLat.lat), lng: parseFloat(event.lngLat.lng) })
    },
    rightclickMap (event) { },
    getMarkerIcon () {
      return {
        url: require('../position.png'),
        size: {width: 40, height: 40, f: 'px', b: 'px'},
        scaledSize: {width: 40, height: 40, f: 'px', b: 'px'}
      }
    },
    getMarkerIcon2 () {
      return {
        labelOrigin: { x: 20, y: -10 },
        url: require('../placeholder.svg'),
        size: { width: 40, height: 40, f: 'px', b: 'px' },
        scaledSize: { width: 40, height: 40, f: 'px', b: 'px' }
      }
    },
    getLabel2 () {
      return ({'text': this.diachi !== null ? this.diachi : this.position.lat + ';' + this.position.lng, 'color': '#3225F6', 'fontSize': '12px', 'fontFamily': 'Arial', 'fontWeight': 'bold'})
    },
    reload () {
    }
  }
}
</script>
<style>
  .vue-simple-context-menu {
    top: 0;
    left: 0;
    margin: 0;
    padding: 0;
    display: none;
    list-style: none;
    position: absolute;
    z-index: 1000000;
    background-color: #ecf0f1;
    border-bottom-width: 0px;
    font-family: "Roboto", Arial;
    font-size: 1rem;
    box-shadow: 0 3px 6px 0 rgba(51, 51, 51, 0.2);
    border-radius: 4px;
  }
  .vue-simple-context-menu--active {
    display: block;
  }
  .vue-simple-context-menu__item {
    display: flex;
    color: #333;
    cursor: pointer;
    padding: 5px 15px;
    align-items: center;
  }
  .vue-simple-context-menu__item:hover {
    background-color: #f5f5f5;
  }
  .vue-simple-context-menu__divider {
    box-sizing: content-box;
    height: 1px;
    background-color: #c0cdd1;
    padding: 4px 0;
    background-clip: content-box;
    pointer-events: none;
  }
  .vue-simple-context-menu li:first-of-type {
    margin-top: 4px;
  }
  .vue-simple-context-menu li:last-of-type {
    margin-bottom: 4px;
  }
  .gm-style-iw > button {
  display: none !important;
  }
  .gm-style-iw {

  }
  .control-map{
    margin-top: 4px;
  }
  .mapVNPTChonToaDo {
    position: initial;
    height: 500px;
    width: 100%;
  }
  .map-canvas-box {
    overflow: hidden;
  }
</style>
