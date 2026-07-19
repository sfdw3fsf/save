<template>
    <div class="map-canvas-box">
      <div class="map-canvas-main">
        <vue-simple-context-menu
          elementId="menuPolygonVNPTMAP"
          :options="optionsPolygonMenu"
          ref="vueSimpleContextMenuPolygon"
          @option-clicked="menuPolygon_Clicked"/>
        <div id="mapVNPTBanDoRanh" class="mapVNPTBanDoRanh"></div>
        <el-dialog title="Thuộc tính ranh" width="30%" :visible.sync="modalShow" @close="handleClose">
          <el-form size="mini" :model="form">
            <el-form-item size="small" label="Mã ranh" :label-width="formLabelWidth">
              <el-input ref="MA_TRAM" v-model="form.MA_TRAM" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item size="small" label="Màu nền" :label-width="formLabelWidth">
              <el-color-picker v-model="form.STYLE_FILL"></el-color-picker>
            </el-form-item>
            <el-form-item  size="small" label="Độ trong suốt màu nền" :label-width="formLabelWidth">
              <el-input type="number" v-model="form.STYLE_FILL_OPACITY" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item  size="small" label="Màu đường viền" :label-width="formLabelWidth">
              <el-color-picker v-model="form.STYLE_STROKE"></el-color-picker>
            </el-form-item>
            <el-form-item size="small" label="Màu đường viền khi active" :label-width="formLabelWidth">
              <el-color-picker v-model="form.STYLE_STROKE_ACTIVE"></el-color-picker>
            </el-form-item>
            <el-form-item size="small" label="Độ trong suốt màu đường viền" :label-width="formLabelWidth">
              <el-input type="number" v-model="form.STYLE_STROKE_OPACITY" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item  size="small" label="Độ dày đường viền" :label-width="formLabelWidth">
              <el-input type="number" v-model="form.STYLE_STROKE_WIDTH" autocomplete="off"></el-input>
            </el-form-item>
          </el-form>
          <span slot="footer" class="dialog-footer">
            <el-button size="small" @click="modalShow = false">Hủy bỏ</el-button>
            <el-button size="small" type="primary" @click="onConfirm">Lưu</el-button>
          </span>
        </el-dialog>
      </div>
    </div>
</template>
<script>
import VnptPlugin from '@vnpt/maps-plugin'
import VnptMapDraw from '@vnpt/maps-draw'
import VnptMap from '@vnpt/maps-onebss'
import { mapActions, mapState, mapGetters } from 'vuex'
import { getterName, actionName, statePropertyName } from './../store'
import VueSimpleContextMenu from 'vue-simple-context-menu'
import EventBus from '../../../../utils/eventBus'
const TOKEN = 'VNPT-f76bf568-b093-45cf-9761-f6339d3e7d9a'
export default {
  components: {'vue-simple-context-menu': VueSimpleContextMenu},
  name: 'BanDoRanhVNPT',
  props: {
  },
  async created () {
  },
  destroyed () {
  },
  data () {
    return {
      form: {
          ID : null,
          MA_TRAM: '',
          STYLE_FILL: '#409EFF',
          STYLE_FILL_OPACITY: 1,
          STYLE_STROKE: '#405FF2',
          STYLE_STROKE_WIDTH :3,
          STYLE_STROKE_OPACITY: 1,
          STYLE_STROKE_ACTIVE : "#FFFFFF",
          boolean : false,
          type: [],
          resource: '',
          desc: ''
        },
        formLabelWidth: '220px',
      modalShow: false,
      user: {
          name: '',
          email: '',
          password: '',
      },
      multiPolygonList: [],
      polygonList: [],
      draw: null,
      geofency: null,
      point: {},
      popupInstance: null,
      basemapApi: null,
      mapVNPT: null,
      zoomLevel: 20,
      location: {lat: -1, lng: -1},
      mouselatLng: {},
      maxZoom: 20,
      loadingMap: true,
      locationrightclickMap: null,
    }
  },
  watch: {
  },
  computed: {
    ...mapState('cabman/BanDoRanh', statePropertyName),
    ...mapState('cabman', ['BanDoRanh']),
    ...mapGetters('cabman/BanDoRanh', getterName),
    getPositionCenter () { return this.location },
    optionsPolygonMenu () {
      return [
        {
          name: '<span class="icon text-main f20 one-taodiemuon"></span> Sửa tọa độ ranh trạm',
          slug: 'sua_toado_ranh'
        },
        {
          name: '<span class="icon text-main f20 -ap icon-info"></span> Thuộc tính ranh trạm',
          slug: 'sua_ranh'
        },
        {
          type: 'divider'
        },
        {
          name: '<span class="icon text-main f20 one-taodiemuon"></span> Xem tập điểm thuộc ranh trạm',
          slug: 'tapdiem_thuoc_ranh'
        },
        {
          type: 'divider'
        },
        {
          name: '<span class="icon text-main f20 one-trash"></span> Xoá ranh trạm',
          slug: 'xoa_ranh'
        }

      ]
    }
  },
  beforeDestroy() {
    this.mapVNPT.remove()
  },
  async mounted () {
    let vm = this
    let options = {
      showZoomControl: false,
      showFullscreen: false,
      id: this.generateId(),
      maxZoom: 24
    }
    vm.mapVNPT = VnptMap.initMap('mapVNPTBanDoRanh', options)
    vm.draw = new VnptMapDraw()
    VnptPlugin.addControlPlugin(vm.draw, vm.mapVNPT)
    vm.mapVNPT.on('load', (e) => {
      vm.basemapApi = VnptMap.initBasemapApi()
      vm.geofency = VnptMap.initGeofency()
      setTimeout(() => this.mapVNPT.updateSized(), 10)
    })
    this.mapVNPT.on('draw.update', (e) => {
      let feature=e.features[0]
      let index=this.polygonList.findIndex(x=>x.id==feature.id)
      if(index>-1)
      {
        this.polygonList[index].geometry.coordinates=feature.geometry.coordinates
      }
      index=this.multiPolygonList.findIndex(x=>x.id===feature.id)
      this.ve_lai_polygon(index,this.multiPolygonList[index],feature)
      })
    this.mapVNPT.on('draw.create', (e) => {
      let feature=e.features[0]
      if (feature.type==="Feature")
      {
        if (feature.geometry.type==="Polygon") {
          let coordinates=feature.geometry.coordinates
          if (coordinates.length>0) {
            this.ve_polygon(feature)
          }
        }
      }
    })
  },
  methods: {
    ...mapActions('cabman/BanDoRanh', actionName),
    onConfirm(){
      this.modalShow=false
      if (this.form.ID !==null) {
        this.ve_lai_style_polygon(this.form)
      }
    },
    handleClose(){
      this.form={
        ID: null,
        MA_TRAM: '',
        STYLE_FILL: '',
        STYLE_FILL_OPACITY: '',
        STYLE_STROKE: '',
        STYLE_STROKE_WIDTH :'',
        STYLE_STROKE_OPACITY: '',
        STYLE_STROKE_ACTIVE : ''
      }
    },
   async menuPolygon_Clicked (event) {
      switch (event.option.slug) {
        case 'tapdiem_thuoc_ranh':
          try {
            this.$root.showLoading(true)
            let polygon4=event.item
            let thamso1 = {
                ma : polygon4.name.toString(),
                id : this.ttvt_id.toString(),
                id2 : '',
                id3 : '',
                giatri : []
              }
            let input1={
              thuchien : "tapdiem_thuoc_ranh",
              thamso : JSON.stringify(thamso1)
            }
            let rs1 = await this.$root.context.post('/web-cabman/ban-do-mang-cap/thuchien', input1)
            let data=rs1.data || []
            for (let index = 0; index < data.length; index++)
            {
              const element = data[index];
              const point = [element.KINHDO, element.VIDO]
              const obj = {
                iconUrl: "https://maps.vnpt.vn/v2/assets/images/markers/start-marker.png",
                id: "Point",
                iconWidth: 30,
                iconHeight: 30
              }
              let pointLayer = VnptMap.initPoint(point, obj)
              pointLayer.addLayerTo(this.mapVNPT);
            }
              this.$root.showLoading(false)
            } catch (error) {
              this.$root.showLoading(false)
            }
            break
        case 'xoa_ranh':
          try {
            let msg=await this.$confirm(`Bạn có muốn xóa ranh trạm này ?`,'Xóa ranh trạm',{confirmButtonText: 'Có',cancelButtonText: 'Không',type: 'info'})
            console.log("🚀 ~ file: BanDoVNPT.vue:237 ~ menuPolygon_Clicked ~ msg:", msg)
            if (msg!=="confirm") return
            this.$root.showLoading(true)
            let polygon3=event.item
            let thamso = {
              ma : polygon3.name.toString(),
              id : this.ttvt_id.toString(),
              id2 : '',
              id3 : '',
              giatri : []
            }
            let input={
              thuchien : "xoa_ranh_tram",
              thamso : JSON.stringify(thamso)
            }
           console.log("🚀 ~ file: BanDoVNPT.vue:203 ~ menuPolygon_Clicked ~ myJsonString:", input)
           let rs = await this.$root.context.post('/web-cabman/ban-do-mang-cap/thuchien', input)
           console.log("🚀 ~ file: BanDoVNPT.vue:204 ~ menuPolygon_Clicked ~ rs:", rs)
           if (rs.data && rs.data[0].KETQUA===1)
           {
              this.$root.$toast.success('Xóa ranh trạm thành công')
              EventBus.$emit('LOAD_RANHTRAM', null)
           }
           else {
              this.$root.$toast.error('Lỗi xóa ranh trạm')
           }
            this.$root.showLoading(false)
          } catch (error) {
            this.$root.showLoading(false)
          }
          break
        case 'sua_toado_ranh':
          let polygon2=event.item
          console.log("🚀 ~ file: BanDoVNPT.vue:190 ~ menuPolygon_Clicked ~ polygon:", polygon2)
          const json2 = {
          'id': polygon2.id,
          'type': 'Feature',
          'geometry': {
            'type': 'Polygon',
            'coordinates': polygon2.coordinate
          }}
          this.draw.add(json2)
          break
        case 'sua_ranh':
          let polygon=event.item
          this.form = {
            ID: polygon.id,
            MA_TRAM: polygon.name,
            STYLE_FILL: polygon.properties.STYLE_FILL,
            STYLE_FILL_OPACITY: polygon.properties.STYLE_FILL_OPACITY,
            STYLE_STROKE: polygon.properties.STYLE_STROKE,
            STYLE_STROKE_WIDTH :polygon.properties.STYLE_STROKE_WIDTH,
            STYLE_STROKE_OPACITY: polygon.properties.STYLE_STROKE_OPACITY,
            STYLE_STROKE_ACTIVE: polygon.properties.STYLE_STROKE_ACTIVE,
          }
          this.modalShow=true
          setTimeout(() => {
            this.setFocusName()
          }, 200)
          break
      }
    },
    insertAt (arr, index, newItem) {
      return [
        ...arr.slice(0, index),
        newItem,
        ...arr.slice(index)
      ]
    },
    setFocusName() {
      this.$refs.MA_TRAM.focus();
    },
    generateId () {
      const randomId = Math.random().toString(36).substr(2, 9) + '_' + Date.now().toString(36)
      return randomId
    },
    create_UUID () {
      var dt = new Date().getTime()
      var uuid = 'xxxxxxxx_xxxx_4xxx_yxxx_xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
        var r = (dt + Math.random() * 16) % 16 | 0
        dt = Math.floor(dt / 16)
        return (c === 'x' ? r : (r & 0x3 | 0x8)).toString(16)
      })
      return uuid
    },
    pointInPolygon(polygonPath, coordinates) {
        let numberOfVertexs = polygonPath.length - 1;
        let inPoly = false;
        let { lat, lng } = coordinates;
        let lastVertex = polygonPath[numberOfVertexs];
        let vertex1, vertex2;
        let x = lat, y = lng;
        let inside = false;
        for (var i = 0, j = polygonPath.length - 1; i < polygonPath.length; j = i++) {
            let xi = polygonPath[i].lat, yi = polygonPath[i].lng;
            let xj = polygonPath[j].lat, yj = polygonPath[j].lng;
            let intersect = ((yi > y) != (yj > y))
                && (x < (xj - xi) * (y - yi) / (yj - yi) + xi);
            if (intersect) inside = !inside;
        }
        return inside;
    },
    async setinfoMapDefault (infoMapDefault) {
      try {
        this.location.lat = (infoMapDefault.center.lat)
        this.location.lng = (infoMapDefault.center.lng)
        this.mapVNPT.setCenter([
          this.location.lng,
          this.location.lat
        ])
        this.zoomLevel = 20
        this.mapVNPT.setZoom(this.zoomLevel)
      } catch (error) {
        console.log('🚀 ~ file: BanDoVNPT.vue:335 ~ setinfoMapDefault ~ error:', error)
      }
    },
    getPolygonFromID (polygon_id) {
      return this.multiPolygonList.find(e => e.id === polygon_id)
    },
    rightclickPolygon (event, polygon_id) {
      let polygon = this.getPolygonFromID(polygon_id)
      console.log("🚀 ~ file: BanDoVNPT.vue:1161 ~ rightclickPolygon ~ polygon:", polygon)
      this.diemuonLatlng = event.lngLat
      this.activePolyline = polygon
      this.mouselatLng.lat = event.lngLat.lat
      this.mouselatLng.lng = event.lngLat.lng
      this.$refs.vueSimpleContextMenuPolygon && this.$refs.vueSimpleContextMenuPolygon.hideContextMenu()
      this.$refs.vueSimpleContextMenuPolygon.showMenu(event, polygon)
      const menu = document.getElementById('menuPolygonVNPTMAP')
      var left = event.point.x
      var top = event.point.y
      menu.style.left = left + 'px'
      menu.style.top = top + 'px'
      menu.position = 'fixed'
      menu.classList.add('menu-context')
    },
    updateSized () {
      setTimeout(() => this.mapVNPT.updateSized(), 10)
    },
    cleanPolygons () {
      this.draw.deleteAll();
      // this.draw.delete(this.ids);
      for (let i = 0; i < this.polygonList.length; i++) {
        this.polygonList[i].geometry.coordinates = []
        this.draw.add(this.polygonList[i])
      }
      this.polygonList = []
      console.log("🚀 ~ file: BanDoVNPT.vue:325 ~ cleanPolygons ~ this.multiPolygonList:", this.multiPolygonList)
      for (let i = 0; i < this.multiPolygonList.length; i++) {
        let e = this.multiPolygonList[i]
        e.remove()
        e = null
      }
      this.multiPolygonList = []
    },
    rePolygon (polygon) {
      console.log('🚀 ~ rePolygon ~ polygon:', polygon)
      let polygonList = []
      for (let index = 0; index < polygon.coordinate.length; index++) {
        const coord = polygon.coordinate[index]
        polygonList.push([
          Number(coord[0]), Number(coord[1])
        ])
      }
      const multiPolygon = VnptMap.initPolygon([ polygonList ], {
        id: polygon.id,
        color: polygon.properties['stroke'],
        opacity: polygon.properties['stroke-opacity'],
        weight: polygon.properties['stroke-width'],
        fillColor: polygon.properties['fill'],
        fillOpacity: polygon.properties['fill-opacity']
      })
      multiPolygon.addLayerTo(this.mapVNPT)
    },
    SET_VISBLE(data) {
      if (data && data.length===0) {
        for (let index = 0; index < this.multiPolygonList.length; index++)
        {
          const element = this.multiPolygonList[index];
          element.setVisble(false)
        }
      }
      else
      {
        for (let index = 0; index < this.multiPolygonList.length; index++)
        {
          const element = this.multiPolygonList[index];
          if (data.includes(element.id))
          {
            element.setVisble(true)
          } else {
            element.setVisble(false)
          }
        }
      }
    },
    SET_ACTIVE(data) {
      for (let index = 0; index < this.multiPolygonList.length; index++) {
        const element = this.multiPolygonList[index];
        if (element.id===data.DONVI_ID + "_" + data.MA_TRAM) {
           element.setStyle({
            color: element.properties.STYLE_STROKE_ACTIVE,
            weight : element.properties.STYLE_STROKE_WIDTH_ACTIVE
          })
        }
      }
    },
    SET_DEACTIVE(data) {
      for (let index = 0; index < this.multiPolygonList.length; index++) {
        const element = this.multiPolygonList[index];
        if (element.id===data.DONVI_ID + "_" + data.MA_TRAM) {
           element.setStyle({
            color: element.properties.STYLE_STROKE,
            weight : element.properties.STYLE_STROKE_WIDTH
          })
        }
      }
    },
    ve_lai_style_polygon(data){
      let index=this.multiPolygonList.findIndex(x=>x.id===data.ID)
      if (index>-1) {
       let polygon= this.multiPolygonList[index]
       polygon.remove()
       const json2 = {
            'id': polygon.id,
            'type': 'Feature',
            'geometry': {
              'type': 'Polygon',
              'coordinates': polygon.coordinate
            }
        }
        const multiPolygon = VnptMap.initPolygon(json2, {
          'id': polygon.id,
          'color': data['STYLE_STROKE'],
          'opacity': data['STYLE_STROKE_OPACITY'],
          'weight': data['STYLE_STROKE_WIDTH'],
          'fillColor': data['STYLE_FILL'],
          'fillOpacity': data['STYLE_FILL_OPACITY']
        })
        multiPolygon.status="NEW"
        multiPolygon.name = data.MA_TRAM
        multiPolygon.coordinate = polygon.coordinate
        multiPolygon.id = polygon.id
        multiPolygon.properties = {}
        multiPolygon.properties['STYLE_STROKE'] = data['STYLE_STROKE']
        multiPolygon.properties['STYLE_STROKE_ACTIVE'] = data['STYLE_STROKE_ACTIVE']
        multiPolygon.properties['STYLE_STROKE_OPACITY'] = data['STYLE_STROKE_OPACITY']
        multiPolygon.properties['STYLE_STROKE_WIDTH'] = data['STYLE_STROKE_WIDTH']
        multiPolygon.properties['STYLE_STROKE_WIDTH_ACTIVE']=10
        multiPolygon.properties['STYLE_FILL'] = data['STYLE_FILL']
        multiPolygon.properties['STYLE_FILL_OPACITY'] = data['STYLE_FILL_OPACITY']
        console.log("🚀 ~ file: BanDoVNPT.vue:678 ~ ve_lai_style_polygon ~ multiPolygon:", multiPolygon)
        multiPolygon.addLayerTo(this.mapVNPT)
        let me=this
        multiPolygon.on('contextmenu', (e) => {
          me.rightclickPolygon(e, multiPolygon.id)
        })
        multiPolygon.on('mouseenter', (e) => {
          let lng = e.lngLat.lng
          let lat = e.lngLat.lat
          let content = '<b>' + (multiPolygon.name === null ? "Chưa có mã trạm" : multiPolygon.name) + '</b>'
          me.popupInstance && me.popupInstance.remove()
          me.popupInstance = VnptMap.initPopup([lng, lat], content, {
            offset: 20
          })
          me.popupInstance.addLayerTo(me.mapVNPT)
          multiPolygon.setStyle({
            color: multiPolygon.properties.STYLE_STROKE_ACTIVE,
            weight : multiPolygon.properties.STYLE_STROKE_WIDTH_ACTIVE
          })
        })
        multiPolygon.on('mouseout', (e) => {
          multiPolygon.setStyle({
            color: multiPolygon.properties.STYLE_STROKE,
            weight : multiPolygon.properties.STYLE_STROKE_WIDTH
          })
          me.popupInstance && me.popupInstance.remove()
        })
        this.multiPolygonList.splice(index,1)
        this.multiPolygonList.push(multiPolygon)
        polygon=null
      }
    },
    ve_lai_polygon(index,polygon, feature){
        polygon.remove()
        const json2 = {
            'id': polygon.id,
            'type': 'Feature',
            'geometry': {
              'type': 'Polygon',
              'coordinates': feature.geometry.coordinates
            }
        }
        const multiPolygon = VnptMap.initPolygon(json2, {
          'id': polygon.id,
          'color': polygon.properties['STYLE_STROKE'],
          'opacity': polygon.properties['STYLE_STROKE_OPACITY'],
          'weight': polygon.properties['STYLE_STROKE_WIDTH'],
          'fillColor': polygon.properties['STYLE_FILL'],
          'fillOpacity': polygon.properties['STYLE_FILL_OPACITY']
        })
        multiPolygon.status="NEW"
        multiPolygon.name = polygon.name
        multiPolygon.coordinate = feature.geometry.coordinates
        multiPolygon.id = polygon.id
        multiPolygon.properties = {}
        multiPolygon.properties['STYLE_STROKE'] = polygon.properties['STYLE_STROKE']
        multiPolygon.properties['STYLE_STROKE_ACTIVE'] = polygon.properties['STYLE_STROKE_ACTIVE']
        multiPolygon.properties['STYLE_STROKE_OPACITY'] = polygon.properties['STYLE_STROKE_OPACITY']
        multiPolygon.properties['STYLE_STROKE_WIDTH'] = polygon.properties['STYLE_STROKE_WIDTH']
        multiPolygon.properties['STYLE_STROKE_WIDTH_ACTIVE']=10
        multiPolygon.properties['STYLE_FILL'] = polygon.properties['STYLE_FILL']
        multiPolygon.properties['STYLE_FILL_OPACITY'] = polygon.properties['STYLE_FILL_OPACITY']
        console.log("🚀 ~ file: BanDoVNPT.vue:739 ~ ve_lai_polygon ~ multiPolygon:", multiPolygon)
        multiPolygon.addLayerTo(this.mapVNPT)
        let me=this
        multiPolygon.on('contextmenu', (e) => {
          me.rightclickPolygon(e, multiPolygon.id)
        })
        multiPolygon.on('mouseenter', (e) => {
          let lng = e.lngLat.lng
          let lat = e.lngLat.lat
          let content = '<b>' + (multiPolygon.name === null ? "Chưa có mã trạm" : multiPolygon.name) + '</b>'
          me.popupInstance && me.popupInstance.remove()
          me.popupInstance = VnptMap.initPopup([lng, lat], content, {
            offset: 20
          })
          me.popupInstance.addLayerTo(me.mapVNPT)
          multiPolygon.setStyle({
            color: multiPolygon.properties.STYLE_STROKE_ACTIVE,
            weight : multiPolygon.properties.STYLE_STROKE_WIDTH_ACTIVE
          })
        })
        multiPolygon.on('mouseout', (e) => {
          multiPolygon.setStyle({
            color: multiPolygon.properties.STYLE_STROKE,
            weight : multiPolygon.properties.STYLE_STROKE_WIDTH
          })
          me.popupInstance && me.popupInstance.remove()
        })
        this.multiPolygonList.splice(index,1)
        this.multiPolygonList.push(multiPolygon)
        polygon=null
    },
    randomNonGreenColor  () {
      var ranges = [[0, 60], [180, 359]]
      var total = 0
      var i
      for (i = 0; i < ranges.length; i += 1) {
        total += ranges[i][1] - ranges[i][0] + 1
      }
      var randomHue = Math.floor(Math.random() * total)
      var pos = 0
      for (i = 0; i < ranges.length; i += 1) {
        pos = ranges[i][0]
        if (randomHue + pos <= ranges[i][1]) {
          randomHue += pos
          break
        } else {
          randomHue -= (ranges[i][1] - ranges[i][0] + 1)
        }
      }
      let color = 'hsl(' + randomHue + ',100%,50%)'
      return color
    },
    ve_polygon(feature){
        let ma_tram=null
        let color=this.randomNonGreenColor()
        let style=
        {
          STYLE_STROKE : color,
          STYLE_STROKE_OPACITY : 1,
          STYLE_STROKE_WIDTH : 3,
          STYLE_FILL : color,
          STYLE_FILL_OPACITY : 0.30196078431372547,
          STYLE_STROKE_ACTIVE : "#FFFFFF"
        }
        let id = feature.id
        const json2 = {
            'id': id,
            'type': 'Feature',
            'geometry': {
              'type': 'Polygon',
              'coordinates': feature.geometry.coordinates
            }
        }
        const multiPolygon = VnptMap.initPolygon(json2, {
          'id': id,
          'color': style['STYLE_STROKE'],
          'opacity': style['STYLE_STROKE_OPACITY'],
          'weight': style['STYLE_STROKE_WIDTH'],
          'fillColor': style['STYLE_FILL'],
          'fillOpacity': style['STYLE_FILL_OPACITY']
        })
        multiPolygon.status="NEW"
        multiPolygon.name = ma_tram
        multiPolygon.coordinate = [feature.geometry.coordinates[0]]
        multiPolygon.id = id
        multiPolygon.properties = {}
        multiPolygon.properties['STYLE_STROKE'] = style['STYLE_STROKE']
        multiPolygon.properties['STYLE_STROKE_ACTIVE'] = style['STYLE_STROKE_ACTIVE']
        multiPolygon.properties['STYLE_STROKE_OPACITY'] = style['STYLE_STROKE_OPACITY']
        multiPolygon.properties['STYLE_STROKE_WIDTH'] = style['STYLE_STROKE_WIDTH']
        multiPolygon.properties['STYLE_STROKE_WIDTH_ACTIVE']=10
        multiPolygon.properties['STYLE_FILL'] = style['STYLE_FILL']
        multiPolygon.properties['STYLE_FILL_OPACITY'] = style['STYLE_FILL_OPACITY']
        console.log("🚀 ~ file: BanDoVNPT.vue:832 ~ ve_polygon ~ multiPolygon:", multiPolygon)
        multiPolygon.addLayerTo(this.mapVNPT)
        let me=this
        multiPolygon.on('contextmenu', (e) => {
          me.rightclickPolygon(e, multiPolygon.id)
        })
        multiPolygon.on('mouseenter', (e) => {
          let lng = e.lngLat.lng
          let lat = e.lngLat.lat
          let content = '<b>' + (multiPolygon.name === null ? "Chưa có mã trạm" : multiPolygon.name) + '</b>'
          console.log("🚀 ~ file: BanDoVNPT.vue:539 ~ multiPolygon.on ~ let content:", content)
          me.popupInstance && me.popupInstance.remove()
          me.popupInstance = VnptMap.initPopup([lng, lat], content, {
            offset: 20
          })
          me.popupInstance.addLayerTo(me.mapVNPT)
          multiPolygon.setStyle({
            color: multiPolygon.properties.STYLE_STROKE_ACTIVE,
            weight : multiPolygon.properties.STYLE_STROKE_WIDTH_ACTIVE
          })
        })
        multiPolygon.on('mouseout', (e) => {
          multiPolygon.setStyle({
            color: multiPolygon.properties.STYLE_STROKE,
            weight : multiPolygon.properties.STYLE_STROKE_WIDTH
          })
          me.popupInstance && me.popupInstance.remove()
        })
        this.multiPolygonList.push(multiPolygon)
        this.polygonList.push(json2)
    },
    VE_RANH_TTVT2 (features) {
      this.cleanPolygons()
      for (let i = 0; i < features.length; i++) {
        let feature = features[i]
        console.log("🚀 ~ file: BanDoVNPT.vue:579 ~ VE_RANH_TTVT2 ~ feature:", feature)
        let coordinates = feature.DAY_TOADO.split(';')
        let polygonList = []
        for (let index = 0; index < coordinates.length; index++) {
          const coord = coordinates[index].split(',')
          polygonList.push([
            Number(coord[0]), Number(coord[1])
          ])
        }
        let id = feature.DONVI_ID + '_' + feature.MA_TRAM
        const json2 = {
          'id': id,
          'type': 'Feature',
          'geometry': {
            'type': 'Polygon',
            'coordinates': [
              polygonList
            ]
          }
        }
        const multiPolygon = VnptMap.initPolygon(json2, {
          'id': id,
          'color': feature['STYLE_STROKE'],
          'opacity': feature['STYLE_STROKE_OPACITY'],
          'weight': feature['STYLE_STROKE_WIDTH'],
          'fillColor': feature['STYLE_FILL'],
          'fillOpacity': feature['STYLE_FILL_OPACITY']
        })
        multiPolygon.status=null
        multiPolygon.name = feature['MA_TRAM']
        multiPolygon.coordinate =[ polygonList ]
        multiPolygon.id = id
        multiPolygon.properties = {}
        multiPolygon.properties['STYLE_STROKE'] = feature['STYLE_STROKE']
        multiPolygon.properties['STYLE_STROKE_ACTIVE'] = feature['STYLE_STROKE_ACTIVE'] || "#FFFFFF"
        multiPolygon.properties['STYLE_STROKE_OPACITY'] = feature['STYLE_STROKE_OPACITY']
        multiPolygon.properties['STYLE_STROKE_WIDTH'] = feature['STYLE_STROKE_WIDTH']
        multiPolygon.properties['STYLE_STROKE_WIDTH_ACTIVE']=10
        multiPolygon.properties['STYLE_FILL'] = feature['STYLE_FILL']
        multiPolygon.properties['STYLE_FILL_OPACITY'] = feature['STYLE_FILL_OPACITY']
        console.log("🚀 ~ file: BanDoVNPT.vue:2068 ~ VE_RANH_TTVT2 ~ json2:", multiPolygon)
        let me = this
        multiPolygon.addLayerTo(this.mapVNPT)
        multiPolygon.on('contextmenu', (e) => {
          me.rightclickPolygon(e, multiPolygon.id)
        })
        multiPolygon.on('mouseenter', (e) => {
          let lng = e.lngLat.lng
          let lat = e.lngLat.lat
          let content = '<b>' + (multiPolygon.name === null ? "Chưa có mã trạm" : multiPolygon.name) + '</b>'
          me.popupInstance && me.popupInstance.remove()
          me.popupInstance = VnptMap.initPopup([lng, lat], content, {
            offset: 20
          })
          me.popupInstance.addLayerTo(me.mapVNPT)
          multiPolygon.setStyle({
            color: multiPolygon.properties.STYLE_STROKE_ACTIVE,
            weight : multiPolygon.properties.STYLE_STROKE_WIDTH_ACTIVE
          })

        })
        multiPolygon.on('mouseout', (e) => {
          multiPolygon.setStyle({
            color: multiPolygon.properties.STYLE_STROKE,
            weight : multiPolygon.properties.STYLE_STROKE_WIDTH
          })
          me.popupInstance && me.popupInstance.remove()
        })
        this.multiPolygonList.push(multiPolygon)
      }
      if (this.multiPolygonList.length>0) {
        this.mapVNPT.setCenter([this.multiPolygonList[0].coordinate[0][0], this.multiPolygonList[0].coordinate[0][1]])
        this.mapVNPT.setZoom(13)
      }
    },
    setLocationEx (position) {
      if (position === null || position === undefined) {
        if (this.pointLayers.length > 0) {
          this.location = {
            lat: this.pointLayers[0].position.lat,
            lng: this.pointLayers[0].position.lng
          }
        }
      } else {
        this.location = {
          lat: position.lat,
          lng: position.lng
        }
      }
      this.mapVNPT.setCenter([this.location.lng, this.location.lat])
      this.mapVNPT.setZoom(18)
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
  .my-marker-label-white{
    color:#fff !important;
    text-shadow: -1px 1px 0 #000,1px 1px 0 #000,1px -1px 0 #000,-1px -1px 0 #000;
  }
  .my-marker-label-black{
    color:#000 !important;
    text-shadow: -1px 1px 0 #fff,1px 1px 0 #fff,1px -1px 0 #fff,-1px -1px 0 #fff;
  }
  /* .mapVNPTBanDoRanh {
    position: initial;
    height: 800px;
    width: 100%;
  } */
  .map-canvas-box {
    overflow: hidden;
    height: calc(100%);
    width: 100%;
  }
  .vnptmaps-pointlabel
  {
    position: fixed;
    top: 22px;
    color: black;
    font-size: 11px;
    font-family: Arial;
    flex-wrap: nowrap;
    white-space : nowrap !important;
    text-shadow: 0.5px 0.5px white;
  }
</style>
