<template>
  <div :style="{'height': height}">
      <vue-simple-context-menu
        elementId="menuMap"
        :options="optionsMapMenu"
        ref="vueSimpleContextMenuMap"
        @option-clicked="menuMap_Clicked"
      />
      <GmapMap
            id="map"
            ref="oneBSSGmap"
            :zoom="zoomLevel"
            :options="mapOptions"
            :center="position"
            @dblclick="dblclickMap($event)"
            @rightclick="rightclickMap($event)"
            style="width: 100%; height: calc(100% - 26px);">
              <GmapMarker
                  :position="position"
                  :icon="getMarkerIcon2()"
                  :label="getLabel2()">
              >
              </GmapMarker>
              <GmapMarker
                  v-for="node in getNodes"
                  :key="node.id"
                  :draggable="false"
                  :position="node.position"
                  :icon="getMarkerIcon()"
                  @dragend="dragendPositionNode($event,node)"
              >
              </GmapMarker>
      </GmapMap>
       <b-modal id="modal-bankinh" title="Nhập vào bán kình cần tìm" @ok="handleOk" ok-title="Đồng ý" cancel-title="Hủy">
         <input type="number" class="form-control" v-model="bankinhTk" />
        </b-modal>
        <b-modal id="modal-whereareyou" title="Bạn đang ở vị trí" ok-title="Đồng ý" ok-only>
         <label>{{locationrightclickMap ? locationrightclickMap.lat() : ''}}, {{locationrightclickMap ? locationrightclickMap.lng() : ''}}</label>
        </b-modal>
  </div>
</template>
<script>

import {gmapApi} from 'gmap-vue'
import html2canvas from 'html2canvas'
import EventBus from '@/utils/eventBus'
import VueSimpleContextMenu from 'vue-simple-context-menu'
import MeasureTool from 'measuretool-googlemaps-v3'
export default {
  components: {'vue-simple-context-menu': VueSimpleContextMenu, MeasureTool, html2canvas},
  name: 'BanDo',
  props: {
    position: null,
    diachi: null,
    mapTypeId: null,
    zoomDefault: null,
    height: {
      type: String,
      default: '800px'
    }
  },
  watch: {
    position (val) {
      console.log('position watch position watch', val)
    }
  },
  computed: {
    google: gmapApi,
    getPositionCenter () { return this.position },
    getNodes () {
      return this.nodes
    }
  },
  destroyed () {
    EventBus.$off('IN_BANDO', this.inBando)
  },
  async created () {
    await this.$gmapApiPromiseLazy()
    EventBus.$on('IN_BANDO', this.inBando)
  },
  data () {
    return {
      zoomLevel: this.zoomDefault,
      locationrightclickMap: null,
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
        disableDoubleClickZoom: true,
        
      },
      bankinhTk: '',
      maxZoom: 20,
      map: null,
      nodes: [],
      positionCenter: this.position,
      optionsMapMenu: 
      [
        {
          name: '<span class="icon text-main f20 one-chuyencap"></span> Đo khoảng cách',
          slug: 'dokhoangcach'
        },
        {
          name: '<span class="icon text-main f20 one-taodiemuon"></span> Xóa phép đo',
          slug: 'xoaphepdo'
        },
        // {
        //   name: '<span class="icon text-main f20 one-daochieu"></span> Tìm lân cận',
        //   slug: 'tim_dia_diem'
        // },
        {
          name: '<span class="icon text-main f20 one-trash"></span> Đây là đâu ?',
          slug: 'day_la_dau'
        }
        
        
      ],
    }
  },
  watch: {
    mapTypeId (val) {
      if (val === 'ROADMAP') {
        this.map.setMapTypeId(window.google.maps.MapTypeId.ROADMAP)
      } else {
        this.map.setMapTypeId(window.google.maps.MapTypeId.SATELLITE)
      }
    }
  },
  async  mounted () {
    let vm = this
    this.$refs.oneBSSGmap.$mapPromise.then((map) => {
      vm.map = map

      this.measureTool = new MeasureTool(this.map, {
        contextMenu: false,
        unit: MeasureTool.UnitTypeId.METRIC,
        tooltip: false
      })
      var polygon = new this.google.maps.Polygon({
          clickable: true
        })
      this.measureTool.addListener('measure_start', () => {
          console.log('started')
          polygon.setOptions({clickable: false})
      })
      this.measureTool.addListener('measure_end', (e) => {
        console.log('ended', e.result)
        polygon.setOptions({clickable: true})
      })
      this.measureTool.addListener('measure_change', (e) => {
        console.log('changed', e.result)
      })

    })
    
    
  },
  methods: {
    // searchLocation: function (searchAddressInput) {
    //   this.$refs.oneBSSGmap.$mapPromise.then((map) => {
    //     var geocoder = new window.google.maps.Geocoder()
    //     geocoder.geocode({'address': searchAddressInput }, (results, status) => {
    //       console.log('searchLocation', results, status)
    //       if (status === 'OK') {
    //         var lat = results[0].geometry.location.lat()
    //         var lng = results[0].geometry.location.lng()
    //         const idx = this.nodes.findIndex(obj => obj.id === 'x')
    //         if (idx > -1) {
    //           this.nodes[idx].position.lat = lat
    //           this.nodes[idx].position.lng = lng
    //         } else {
    //           this.nodes.push({
    //             id: 'x',
    //             position: {
    //               lat: lat,
    //               lng: lng
    //             }
    //           })
    //         }
    //         var myLatlng = new window.google.maps.LatLng(lat, lng)
    //         this.map.panTo(myLatlng)
    //         this.$emit('dblclickMap', { lat: lat, lng: lng })
    //       }
    //       else {
    //         this.$root.toastError('Không xác định được vị trí');
    //       }
    //     })
    //   })
    // },
    searchLocation: function (searchAddressInput) {
      var re = new RegExp(/-?(\d*\.)?\d+,\s*-?(\d*\.)?\d+/)
      if (re.test(searchAddressInput)) {
        let arr = searchAddressInput.split(',')
        this.$refs.oneBSSGmap.$mapPromise.then((map) => {
          let location = {
            lat: parseFloat(arr[0]),
            lng: parseFloat(arr[1])
          }
          console.log('🚀 ~ file: BanDo.vue:99 ~ this.$refs.oneBSSGmap.$mapPromise.then ~ location:', location)
          const idx = this.nodes.findIndex(obj => obj.id === 'x')
          if (idx > -1) {
            this.nodes[idx].position.lat = location.lat
            this.nodes[idx].position.lng = location.lng
          } else {
            this.nodes.push({
              id: 'x',
              position: {
                lat: location.lat,
                lng: location.lng
              }
            })
          }
          let panPoint = new window.google.maps.LatLng(location.lat, location.lng)
          map.panTo(panPoint)
          this.$emit('dblclickMap', { lat: location.lat, lng: location.lng })
        })
      } else {
        this.$refs.oneBSSGmap.$mapPromise.then((map) => {
          var geocoder = new window.google.maps.Geocoder()
          geocoder.geocode({'address': searchAddressInput + ', Việt Nam'}, (results, status) => {
            console.log('🚀 ~ file: BanDo.vue:93 ~ geocoder.geocode ~ results:', results)
            console.log('searchLocation', status)
            if (status === 'OK') {
              var lat = results[0].geometry.location.lat()
              var lng = results[0].geometry.location.lng()
              const idx = this.nodes.findIndex(obj => obj.id === 'x')
              if (idx > -1) {
                this.nodes[idx].position.lat = lat
                this.nodes[idx].position.lng = lng
              } else {
                this.nodes.push({
                  id: 'x',
                  position: {
                    lat: lat,
                    lng: lng
                  }
                })
              }
              var myLatlng = new window.google.maps.LatLng(lat, lng)
              this.map.panTo(myLatlng)
              this.$emit('dblclickMap', { lat: lat, lng: lng })
            }
            else {
              this.$root.toastError('Không xác định được vị trí');
            }
          })
        })
      }
    },
    dragendPositionNode (event, node) {
      node.position.lat = event.latLng.lat()
      node.position.lng = event.latLng.lng()
      this.$emit('dblclickMap', { lat: event.latLng.lat(), lng: event.latLng.lng() })
    },
    dblclickMap (event) {
      const idx = this.nodes.findIndex(obj => obj.id === 'x')
      if (idx > -1) {
        this.nodes[idx].position.lat = event.latLng.lat()
        this.nodes[idx].position.lng = event.latLng.lng()
      } else {
        this.nodes.push({
          id: 'x',
          position: {
            lat: event.latLng.lat(),
            lng: event.latLng.lng()
          }
        })
      }
      this.$emit('dblclickMap', { lat: event.latLng.lat(), lng: event.latLng.lng() })
    },    
    fromLatLngToPixel: function (position) {
      var scale = Math.pow(2, this.map.getZoom())
      var proj = this.map.getProjection()
      var bounds = this.map.getBounds()
      var nw = proj.fromLatLngToPoint(
        new window.google.maps.LatLng(
          bounds.getNorthEast().lat(),
          bounds.getSouthWest().lng()
        ))
      var point = proj.fromLatLngToPoint(position)
      return new window.google.maps.Point(
        Math.floor((point.x - nw.x) * scale),
        Math.floor((point.y - nw.y) * scale))
    },
    rightclickMap (event) {
      console.log('rightclickMap', event)
      this.locationrightclickMap = event.latLng
      this.$refs.vueSimpleContextMenuMap && this.$refs.vueSimpleContextMenuMap.hideContextMenu()
      this.$refs.vueSimpleContextMenuMap.showMenu(event)
      const menu = document.getElementById('menuMap')
      let pixelOffset = new window.google.maps.Point(10, -5)
      let mapDiv = this.map.getDiv()
      var mousePosition = this.fromLatLngToPixel(event.latLng)
      var mapSize = new window.google.maps.Size(mapDiv.offsetWidth, mapDiv.offsetHeight)
      var menuSize = new window.google.maps.Size(menu.offsetWidth, menu.offsetHeight)
      var left = mousePosition.x
      var top = mousePosition.y
      if (mousePosition.x > mapSize.width - menuSize.width - pixelOffset.x) {
        left = left - menuSize.width - pixelOffset.x
      } else {
        left += pixelOffset.x
      }
      if (mousePosition.y > mapSize.height - menuSize.height - pixelOffset.y) {
        top = top - menuSize.height - pixelOffset.y
      } else {
        top += pixelOffset.y
      }
      menu.style.left = left + 'px'
      menu.style.top = top + 'px'
      menu.position = 'fixed'
      menu.classList.add('menu-context')
     },
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
      this.bounds = new window.google.maps.LatLngBounds()
      this.map.setZoom(20)
      this.map.setCenter(
        new window.google.maps.LatLng(
          this.location.lat,
          this.location.lng
        )
      )
    },
    inBando (file) {
      var fin = document.getElementById('map')
      console.log('inBando', fin)
      html2canvas(fin, {
        useCORS: true,
        allowTaint: false,
        ignoreElements: (node) => {
          return node.nodeName === 'IFRAME'
        }}).then(function (canvas) {
        var imgSrc = canvas.toDataURL('image/png').replace('image/png', 'image/octet-stream')
        var link = document.createElement('a')
        link.href = imgSrc
        link.download = file + '.png'
        link.click()
      })
    },
    menuMap_Clicked (event) {
      console.log('menuMap_Clicked', event)
      switch (event.option.slug) {
        case 'dokhoangcach':
          this.measureTool && this.measureTool.start()
          break
        case 'xoaphepdo':
        this.measureTool && this.measureTool.end()
          break
        // case 'tim_dia_diem':
        //   // this.showSearchDiaDiem = !this.showSearchDiaDiem
        //   this.$bvModal.show('modal-bankinh');
        //   break
        case 'day_la_dau':
          console.log('day_la_dau', this.locationrightclickMap.lat(), this.locationrightclickMap.lng());
          this.$bvModal.show('modal-whereareyou');
          break
      }
    },
    handleOk () {
      console.log('ban kinh tim kiem ',this.bankinhTk);
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
</style>
