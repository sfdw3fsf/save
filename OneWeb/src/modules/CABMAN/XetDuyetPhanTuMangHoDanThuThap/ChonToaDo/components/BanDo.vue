<template>
  <div style="height: 400px;">
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
        :center="getPositionCenter"
        @dblclick="dblclickMap($event)"
        @rightclick="rightclickMap($event)"
        style="width: 100%; height: calc(100% - 0px);">
          <GmapMarker
              :position="getPositionCenter"
              :icon="getMarkerIcon2()"
              :label="getLabel2()"
              @click="resetPositionInit()"
          >
          </GmapMarker>
          <GmapMarker
              v-for="node in getNodes"
              :key="node.id"
              :draggable="true"
              :position="node.position"
              :icon="getMarkerIcon()"
              @dragend="dragendPositionNode($event,node)"
          >
          </GmapMarker>
        <gmap-marker
          :key="index+'m2'"
          v-for="(m, index) in radiusObj"
          :position="m.position"
          @click="center=m.position"
          :clickable="true">
        </gmap-marker>
        <gmap-circle v-if="radiusObj.length > 0"
          v-for="(pin, index) in radiusObj"
          :key="index+'circle'"
          :center="pin.position"
          :radius="pin.r"
          :visible="true"
          :options="{fillOpacity:0.5,strokeWeight: 0.5,  fillColor: '#4b6455'}"
          @rightclick="rightclickMap($event)"
        >
        </gmap-circle>
      </GmapMap>
    <b-modal id="popupTimLanCan" scrollable centered title="Tìm lân cận">
      <div>Nhập vào bán kính cần tìm (m):</div>
      <div>
        <input type="number" class="form-control" v-model="radius" ref="ref-timlancan" v-on:keyup.enter="TimLanCan()">
      </div>
      <template #modal-footer="{ ok, cancel }">
        <b-button variant="primary" style="color: white" @click="TimLanCan">Chấp nhận</b-button>
        <b-button variant="secondary" style="color: white" @click="cancel()">Hủy</b-button>

      </template>
    </b-modal>
    <b-modal id="modal-whereareyou" title="Bạn đang ở vị trí" ok-title="Đồng ý" ok-only>
      <label>{{center != null ? center.lat : ''}}, {{center != null  ? center.lng : ''}}</label>
    </b-modal>
  </div>
</template>
<script>

import {gmapApi} from 'gmap-vue'
import VueSimpleContextMenu from 'vue-simple-context-menu'
import MeasureTool from 'measuretool-googlemaps-v3'

export default {
  components: {'vue-simple-context-menu': VueSimpleContextMenu,MeasureTool },
  name: 'BanDo',
  props: {
    position: null,
    diachi: null,
    ten_kc:null,
    mapTypeId: null,
    zoomDefault: null
  },
  computed: {
    google: gmapApi,
    getPositionCenter () { return this.positionCenter },
    getNodes () {
      return this.nodes
    },
  },
  async created () {
    await this.$gmapApiPromiseLazy()
  },
  data () {
    return {
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
      positionCenter: this.position,
      measureTool: null,
      loadingMap:true,
      radius: null,
      radiusObj:[],
      center:{},


      optionsMapMenu: [
        {
          name: '<span ></span> Đo khoảng cách',
          slug: 'dokhoangcach'
        },
        {
          name: '<span ></span> Xóa phép đo',
          slug: 'xoaphepdo'
        },
        {
          type: 'divider'
        },
        {
          name: '<span ></span> Tìm lân cận',
          slug: 'tim_lan_can'
        },
        {
          name: '<span ></span> Đây là đâu ?',
          slug: 'day_la_dau'
        },
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

      this.loadingMap = false
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
    searchLocation: function (searchAddressInput) {
      var geocoder = new window.google.maps.Geocoder()
      geocoder.geocode({'address': searchAddressInput}, (results, status) => {
        console.log('searchLocation', status)
        if (status === 'OK') {
          var lat = results[0].geometry.location.lat()
          var lng = results[0].geometry.location.lng()
          console.log(lat, lng)
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
          // this.$emit('dblclickMap', { lat: lat, lng: lng })
          // fake result by input when status research OK (Due to lib geocode does not determine point location exactly on google map) follow request last user
          let inputLat = parseFloat(searchAddressInput.split(',')[0], 10)
          let inputLng = parseFloat(searchAddressInput.split(',')[1], 10)
          this.$emit('dblclickMap', { lat: inputLat, lng: inputLng })
        }
      })
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
        url: require('../pin.png'),
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
      return ({
        'text': this.ten_kc !== null ? this.ten_kc : this.position.lat + ';' + this.position.lng,
        'color': '#3225F6',
        'fontSize': '12px',
        'fontFamily': 'Arial',
        'fontWeight': 'bold',
        'className':'custom-label-marker2'
      })
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
    menuMap_Clicked (event) {
      console.log('menuMap_Clicked', event)
      switch (event.option.slug) {
        case 'tim_lan_can':
          this.center = {lat:this.locationrightclickMap.lat(), lng:this.locationrightclickMap.lng()}
          this.$bvModal.show("popupTimLanCan")
          break
        case 'day_la_dau':
          console.log('day_la_dau', this.locationrightclickMap.lat(), this.locationrightclickMap.lng())
          this.center = {lat:this.locationrightclickMap.lat(), lng:this.locationrightclickMap.lng()}
          this.$bvModal.show("modal-whereareyou")
          break
        case 'dokhoangcach':
          this.measureTool && this.measureTool.start()
          break
        case 'xoaphepdo':
          this.measureTool && this.measureTool.end()
          break
      }
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
    TimLanCan(){
      this.radiusObj.push({position:{ lat: this.locationrightclickMap.lat(), lng: this.locationrightclickMap.lng() }, r:parseInt(this.radius.toString(),10)})
      this.$bvModal.hide("popupTimLanCan")
    },
    resetPositionInit(){
      this.nodes = []
      this.$emit('dblclickMap', { lat: this.positionCenter.lat, lng: this.positionCenter.lng })
    }
  },
}
</script>
<style>
.custom-label-marker2{
  background-color: #FFF;
  padding: 5px;
  border: #CCC solid thin;
}
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
