<template>

  <div style="height: 800px;">
    <ModalQLKetCuoiExt modalID="modalQLKetCuoi" ref="ModalQLKetCuoiExt"/>
    <vue-simple-context-menu
      elementId="menuMap"
      :options="optionsMapMenu"
      ref="vueSimpleContextMenuMap"
      @option-clicked="menuMap_Clicked"
    />
    <vue-simple-context-menu
        elementId="menuNodeTram"
        :options="optionsNodeTramMenu"
        ref="vueSimpleContextMenuNodeTram"
        @option-clicked="menuNodeTram_Clicked"
      />
    <GmapMap
      id="map"
      ref="oneBSSGmap"
      :zoom="zoomLevel"
      :options="mapOptions"
      :center="getPositionCenter"
      style="width: 100%; height: calc(100% - 36px);"
      @dblclick="dblclickMap($event)"
      @rightclick="rightclickMap($event)"
      >
      <gmap-circle
        :key="getRadiusObj.id"
        :center="getRadiusObj.position"
        :radius="getRadiusObj.r"
        :options="{fillOpacity:0.3,strokeWeight: 0.3,  fillColor: '#4b6455', zIndex:-1}"
        @rightclick="rightclickMap($event)"
        @dblclick="dblclickMap($event)"
        >
      </gmap-circle>

      <gmap-marker
        :key="index+'m1'"
        v-for="(m, index) in getMarkers"
        :position="m.position"
        :icon="m.icon"
        :clickable="true"
        @mouseover="mouseoverNode($event, m)"
        @mouseout="mouseoutNode($event, m)"
        @dblclick="dblclickMarker($event)"
        @click="clickMarker"
        @rightclick="rightclickMarker($event)">

      </gmap-marker>
      <gmap-marker
        :key="'m2'"
        :position="getRadiusObj.position"
        :icon="getMarkerIcon()"
        @click="center=getRadiusObj.position"
        :clickable="true">
      </gmap-marker>
      <!-- <GmapPolyline :key="'p1'"
                    ref="GmapPolyline"
                    :path.sync="getPathPolyline"
                    :geodesic="true"
                    :clickable="true"
                    :options="RouteOptions()"
                    @mouseover="mouseOverRoute($event, getPathPolyline)"
                    @mouseout="mouseOutRoute($event, getPathPolyline)">
      </GmapPolyline> -->
      <GmapInfoWindow
        :options="infoOptionsNode"
        :position="infoPositionNode"
        :opened="infoOpenedNode"
        @closeclick="infoOpenedNode = false">
        <span v-html="infoContentNode"></span>
      </GmapInfoWindow>
      <GmapInfoWindow
        :options="infoOptionsRoute"
        :position="infoPositionRoute"
        :opened="infoOpenedRoute"
        @closeclick="infoOpenedRoute = false">
        <span v-html="infoContentRoute"></span>
      </GmapInfoWindow>
    </GmapMap>
    <b-modal id="modal-whereareyou" title="Bạn đang ở vị trí" ok-title="Đồng ý" ok-only>
      <label>{{locationrightclickMap != null ? locationrightclickMap.lat() : ''}}, {{locationrightclickMap != null  ? locationrightclickMap.lng() : ''}}</label>
    </b-modal>
    <DauNoiKC id="DauNoiKC" :ketcuoi_id="ketcuoi_id_selected"></DauNoiKC>
  </div>
</template>
<script>

import {gmapApi} from 'gmap-vue'
import VueSimpleContextMenu from 'vue-simple-context-menu'
import MeasureTool from 'measuretool-googlemaps-v3'
import ModalQLKetCuoiExt from '../../../CABMAN/BanDoMangCap/modal/QLKetCuoiExt'
import DauNoiKC from '../DauNoiKC'
export default {
  components: {'vue-simple-context-menu': VueSimpleContextMenu, MeasureTool, ModalQLKetCuoiExt, DauNoiKC
  },
  name: 'BanDo',
  props: {
    markers: [],
    radiusObj: Object,
    center: Object
  },
  watch: {
    center: function (value) {
      console.log('watch', this.center)
    }
  },
  computed: {
    google: gmapApi,
    getPositionCenter () { return this.center },
    getRadiusObj () {
      return this.radiusObj
    },
    getPathPolyline () {
      return this.pathPolyline
    },
    getMarkers () {
      console.log('getMarkers', this.markers)
      let nodes = []
      for (let index = 0; index < this.markers.length; index++) {
        const marker = this.markers[index]
        if (marker.KETCUOI_ID === 'KETCUOI_1') {
          nodes.push({
            id: marker.KETCUOI_ID,
            position: {
              lat: marker.VIDO,
              lng: marker.KINHDO
            },
            title: marker.KYHIEU,
            icon: null
          })
        } else {
          nodes.push({
            id: marker.KETCUOI_ID,
            position: {
              lat: marker.VIDO,
              lng: marker.KINHDO
            },
            title: marker.KYHIEU,
            tooltipText: marker.STYLE.ToolTip,
            icon: {
              url: marker.STYLE.ImageUrl,
              size: new window.google.maps.Size(marker.STYLE.WIDTH, marker.STYLE.HEIGHT),
              scaledSize: new window.google.maps.Size(marker.STYLE.WIDTH, marker.STYLE.HEIGHT),
              labelOrigin: { x: 16, y: 40 },
              anchor: new window.google.maps.Point(15, 15)
            }
          })
        }
      }
      console.log('getMarkers nodes', nodes)
      return nodes
    }
  },
  async created () {
    await this.$gmapApiPromiseLazy()
  },
  data () {
    return {
      ketcuoi_id_selected: null,
      // variable of polyline
      pathPolyline: [],
      infoOptionsRoute: {
        pixelOffset: {
          width: 0,
          height: -15
        }
      },
      infoPositionRoute: null,
      infoOpenedRoute: true,
      infoContentRoute: null,

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
        {
          name: '<span class="icon text-main f20 one-taodiemuon"></span> Đây là đâu',
          slug: 'day_la_dau'
        }
      ],
      optionsNodeTramMenu: [
        {
          name: '<span class="icon text-main f20 one-daunoi"></span> Xem đấu nối bên trong',
          slug: 'xem_dau_noi_thiet_bi'
        }
      ],
      zoomLevel: 17,
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
      node: null,
      nodes: [],
      locationrightclickMap: null,
      location: {lat: 16.07511849179389, lng: 108.2093338123915},
      maxZoom: 20,
      map: null,
      infoOptionsNode: {
        shouldFocus: false,
        pixelOffset: {
          width: 0,
          height: -10
        }
      },
      infoContentNode: null,
      infoPositionNode: null,
      infoOpenedNode: false
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
    RouteOptions () {
      try {
        var opt = { strokeOpacity: 1, geodesic: true }
        opt = {
          ...opt,
          strokeWeight: this.Width
        }
        opt = {
          ...opt,
          strokeColor: '#FF0000'
        }
        return opt
      } catch (error) {
        console.log('RouteOptions', error)
      }
    },
    mouseOverRoute (event, Route) {
      this.infoPositionRoute = event.latLng
      this.infoContentRoute = '<b>' + this.haversine_distance(Route[0], Route[1]).toFixed(2) + ' m' + '</b>'
      this.infoOpenedNode = false
      this.infoOpenedRoute = true
    },
    mouseOutRoute (event, Route) {
      this.infoOpenedNode = false
      this.infoOpenedRoute = false
    },
    haversine_distance (mk1, mk2) {
      let R = 3958.8 // Radius of the Earth in miles
      let rlat1 = mk1.lat * (Math.PI / 180) // Convert degrees to radians
      let rlat2 = mk2.lat * (Math.PI / 180) // Convert degrees to radians
      let difflat = rlat2 - rlat1 // Radian difference (latitudes)
      let difflon = (mk2.lng - mk1.lng) * (Math.PI / 180) // Radian difference (longitudes)

      let d = 2 * R * Math.asin(Math.sqrt(Math.sin(difflat / 2) * Math.sin(difflat / 2) + Math.cos(rlat1) * Math.cos(rlat2) * Math.sin(difflon / 2) * Math.sin(difflon / 2)))
      return d * 1609.344 // convert to meters
    },
    mouseoverNode (event, node) {
      this.infoPositionNode = event.latLng
      this.infoContentNode = node.tooltipText
      this.infoOpenedNode = true
      this.node = node
    },
    mouseoutNode (event, node) {
      this.infoOpenedNode = false
    },
    setmapType (type) {
      if (type === 'ROADMAP') {
        this.map.setMapTypeId(window.google.maps.MapTypeId.ROADMAP)
      } else {
        this.map.setMapTypeId(window.google.maps.MapTypeId.SATELLITE)
      }
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
    getMarkerIcon () {
      return {
        url: require('../pin.png'),
        size: {width: 20, height: 20, f: 'px', b: 'px'},
        scaledSize: {width: 20, height: 20, f: 'px', b: 'px'}
      }
    },
    dblclickMap (event) {
      try {
        console.log('🚀 ~ file: BanDo.vue:321 ~ dblclickMap ~ event:', event)
        // this.pathPolyline[0] = {lat: event.latLng.lat(), lng: event.latLng.lng()}
        // this.pathPolyline = [...this.pathPolyline]
        this.radiusObj.position.lat = event.latLng.lat()
        this.radiusObj.position.lng = event.latLng.lng()
        this.$emit('dblclickMap', { lat: event.latLng.lat(), lng: event.latLng.lng() })
      } catch (error) {
        console.log('🚀 ~ file: BanDo.vue:329 ~ dblclickMap ~ error:', error)
      }
    },
    dblclickMarker (event) {
      console.log('dbclick event', this.node)
      var dataQLKetCuoi = {}
      dataQLKetCuoi.cmd = 'ViewKetcuoi'
      dataQLKetCuoi.data = {
        ketcuoi_id: this.node.id
      }
      this.$refs.ModalQLKetCuoiExt.show(dataQLKetCuoi).then((result) => {
        if (result.ok) {
          console.log('Sửa kết cuối', result)
        } else {

        }
      })
    },
    clickMarker () {
      try {
        console.log('🚀 ~ file: BanDo.vue:345 ~ clickMarker ~ this.pathPolyline:')
        this.pathPolyline = [
          this.radiusObj.position,
          this.node.position
        ]
      } catch (error) {
        console.log('🚀 ~ file: BanDo.vue:355 ~ clickMarker ~ error:', error)
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
    rightclickMarker (event) {
      console.log('rightclickMarker', event)
      this.locationrightclickMap = event.latLng
      this.$refs.vueSimpleContextMenuNodeTram && this.$refs.vueSimpleContextMenuNodeTram.hideContextMenu()
      this.$refs.vueSimpleContextMenuNodeTram.showMenu(event)
      const menu = document.getElementById('menuNodeTram')
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

    menuMap_Clicked (event) {
      console.log('menuMap_Clicked', event)
      switch (event.option.slug) {
        case 'dokhoangcach':
          this.measureTool && this.measureTool.start()
          break
        case 'xoaphepdo':
          this.measureTool && this.measureTool.end()
          break
        case 'day_la_dau':
          console.log('day_la_dau', this.locationrightclickMap.lat(), this.locationrightclickMap.lng())
          this.$bvModal.show('modal-whereareyou')
          break
      }
    },
    menuNodeTram_Clicked (event) {
      switch (event.option.slug) {
        case 'xem_dau_noi_thiet_bi':
          console.log('daunoiThietBi', event)
          this.ketcuoi_id_selected = this.node.id
          this.$bvModal.show('DauNoiKC')
          break
      }
    },
    geolocate: function () {
      navigator.geolocation.getCurrentPosition(position => {
        this.center = {
          lat: position.coords.latitude,
          lng: position.coords.longitude
        }
      })
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
