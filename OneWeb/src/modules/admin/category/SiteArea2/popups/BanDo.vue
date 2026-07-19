<template>
  <div style="height: 600px;">
    <!-- <gmap-autocomplete
          ref="SearchDiaDiem"
          placeholder="Gõ để tìm kiếm địa điểm"
          class="form-control"
          :options="{fields: ['geometry', 'formatted_address', 'address_components']}"
         /> -->
      <GmapMap
        id="map"
        ref="oneBSSGmap"
        :zoom="zoomLevel"
        :options="mapOptions"
        :center="getPositionCenter"
        @dblclick="dblclickMap($event)"
        @mousemove="mousemoveMap($event)"
        @click="clickMap"
        style="width: 100%; height: calc(100% - 26px);">

        <gmap-polygon v-for="polygon in polygonList" :key="polygon.id" :paths="polygon.paths" :editable="polygonSelected==polygon.id"
          @paths_changed="updateEdited($event, polygon.id)"
          @rightclick="handleClickForDelete($event, polygon.id, polygon.ref)"
          @click="handleClickPolygon(polygon.id, polygon.ref)"
          :options="polygon.options"
          :ref="polygon.ref"
        >
        </gmap-polygon>


        <GmapMarker
            :position="getPositionCenter"
            @click="resetPositionInit()"
        >
        </GmapMarker>
      </GmapMap>
  </div>
</template>
<script>
// GmapMarker :icon="getMarkerIcon()"
// :label="getLabel()"
//{lat: 21.02772261293581, lng: 105.8358304046256}
import {gmapApi} from 'gmap-vue'
import MeasureTool from 'measuretool-googlemaps-v3'

export default {
  components: { MeasureTool },
  name: 'BanDo',
  props: {
    position: null,
    diachi: null,    mapTypeId: null,
    zoomDefault: null
  },
  computed: {
    google: gmapApi,
    getPositionCenter () {
      if(this.$root.token.getPhanVungID()==21){
        //Hni
        return {lat: 21.02772261293581, lng: 105.8358304046256}
      }else{
        //hcm
        return { lat: 10.82477099104633, lng: 106.63214587243222 }
      }
      //return this.positionCenter 
    }
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
      positionCenter: this.position,
      measureTool: null,
      loadingMap: true,
      radius: null,
      radiusObj: [],
      center: {},
      paths:[],
      isSuaVung:false,
      polygonOptionSelected:{
        strokeColor: "#FF0000",
        strokeOpacity: 0.8,
        strokeWeight: 2,
        fillColor: "#FF0000",
        fillOpacity: 0.35,
      },
      polygonOptionDefault:{
          strokeColor: "#000000",
          strokeOpacity: 0.8,
          strokeWeight: 2,
          fillColor: "#000000",
          fillOpacity: 0.35,
      },
      polygonList:[],
      polygonSelected:-1

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
    window['bando'] = this
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
          console.log(searchAddressInput)
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
          // this.$emit('dblclickMap', { lat: lat, lng: lng })
          // fake result by input when status research OK (Due to lib geocode does not determine point location exactly on google map) follow request last user
          let inputLat = parseInt(searchAddressInput.split(',')[0], 10)
          let inputLng = parseInt(searchAddressInput.split(',')[1], 10)
          this.$emit('dblclickMap', { lat: inputLat, lng: inputLng })
        }
      })
    },
    polygonPaths () {
      if(this.polygonList.length==0){
        return []
      }
      console.log('polygonPaths', this.polygonList)
      let paths=[]
      for(let i=0;i<this.polygonList.length;i++){
        if(this.polygonList[i].paths.length>0&&(this.polygonList[i].paths[0] instanceof Array)){
          paths=paths.concat(this.polygonList[i].paths)
        }else if(this.polygonList[i].paths.length>0&&(this.polygonList[i].paths[0] instanceof Object)){
          paths=paths.concat([this.polygonList[i].paths])
        }
      }
      console.log('polygonPaths path', paths)
      return paths

    },
    dblclickMap (event) {
      this.isSuaVung=true
      this.addPath(event)
      this.$emit('dblclickMap', { lat: event.latLng.lat(), lng: event.latLng.lng() })
    },
    clickMap(event){
      this.polygonSelected=-1
      this.updateColorPathDefault()
    },
    updateEdited: function (mvcPaths, id) {
      //update path
      if(!mvcPaths) return
      let paths = [];
      for (let i=0; i < mvcPaths.getLength(); i++) {
        let path = [];
        for (let j=0; j<mvcPaths.getAt(i).getLength(); j++) {
          let point = mvcPaths.getAt(i).getAt(j);
          path.push({lat: point.lat(), lng: point.lng()});
        }
        paths.push(path);
      }

      const index=this.polygonList.findIndex(x=>x.id==id)
      this.polygonList[index].paths=paths
    },
    handleClickForDelete($event, id, ref) {
      if ($event.vertex) {
        this.$refs[ref][0].$polygonObject.getPaths()
          .getAt($event.path)
          .removeAt($event.vertex)
      }
    },
    handleClickPolygon(id, ref){
      console.log('handleClickPolygon ', id)
      this.polygonSelected=id
      this.updateColorPathDefault()
      const index=this.polygonList.findIndex(x=>x.id==id)
      this.polygonList[index].options=this.polygonOptionSelected
    },
    onDragEndPolygon(event, id){
      console.log('handleMouseDownPolygon', event)
    },
    mousemoveMap(event){
      //console.log('mousemoveMap', { lat: event.latLng.lat(), lng: event.latLng.lng() })
    },
    searchLocationT(pos){
      this.setPositionCenter({
        lat:Number(pos.lat),
        lng:Number(pos.lng),
      })
    },
    searchAddress(input){
      this.searchLocation(input)
    },
    TaoVung(){
      this.isSuaVung=true
      this.addPath(null)
    },
    xoaVung(){
      const index=this.polygonList.findIndex(x=>x.id==this.polygonSelected)
      console.log('xoaVung', index)
      if(index>-1){
        this.polygonList.splice(index, 1)
        this.polygonSelected=-1
      }
    },
    addPath: function (event) {
      // obtain the bounds, so we can guess how big the polygon should be
      var bounds = this.$refs.oneBSSGmap.$mapObject.getBounds()
      var northEast = bounds.getNorthEast()
      var southWest = bounds.getSouthWest()
      var center = event?event.latLng:bounds.getCenter() //event.latLng//bounds.getCenter()
      var degree = 1;
      var f = Math.pow(0.4, degree)
      // Draw a triangle. Use f to control the size of the triangle.
      // i.e., every time we add a path, we reduce the size of the triangle
      var path = [
        { lng: center.lng(), lat: (1-f) * center.lat() + (f) * northEast.lat() },
        { lng: (1-f) * center.lng() + (f) * southWest.lng(), lat: (1-f) * center.lat() + (f) * southWest.lat() },
        { lng: (1-f) * center.lng() + (f) * northEast.lng(), lat: (1-f) * center.lat() + (f) * southWest.lat() },
      ]

      //add new po

      this.updateColorPathDefault()

      var id = 1
      if(this.polygonList.length>0){
        id = this.polygonList[this.polygonList.length-1].id+1
      }
      this.polygonList.push({
        id:id,
        ref:'polygon_'+id,
        options:this.polygonOptionSelected,
        paths:path
      })

      this.polygonSelected=id

      //this.paths.push(path)
    },
    updateColorPathDefault(){
      //#000000
      for(let i=0;i<this.polygonList.length;i++){
        this.polygonList[i].options=this.polygonOptionDefault
      }
    },
    getMarkerIcon() {
      return {
        labelOrigin: { x: 20, y: -10 },
        url: require('@/assets/img/icons/placeholder.svg'),
        size: { width: 40, height: 40, f: 'px', b: 'px' },
        scaledSize: { width: 40, height: 40, f: 'px', b: 'px' }
      }
    },
    getLabel () {
      return ({
        //'text': this.ten_kc !== null ? this.ten_kc : this.position.lat + ';' + this.position.lng,
        'text':this.positionCenter.lat + ';' + this.positionCenter.lng,
        'color': '#3225F6',
        'fontSize': '12px',
        'fontFamily': 'Arial',
        'fontWeight': 'bold',
        'className': 'custom-label-marker2'
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
    resetPositionInit () {
      this.$emit('dblclickMap', { lat: this.positionCenter.lat, lng: this.positionCenter.lng })
    },
    setPositionCenter(position){
      this.positionCenter=position

      this.map.panTo({lat: position.lat, lng: position.lng})
    },
    setSuaVung(isSuaVung){
      this.isSuaVung=isSuaVung
    },
    CleanPolygons(){
      this.polygonList=[]
    },
    setPolygonList(list){
      let polygons=[]
      console.log('list', list)
      for(let i=0;i<list.length;i++){
        polygons.push({
          id:i+1,
          ref:'polygon_'+(i+1),
          options:this.polygonOptionDefault,
          paths:[list[i].map(x=>{
            return {
              lat:Number(x.LAT),
              lng:Number(x.LNG)
            }
          })]
        })
      }
      this.polygonList=polygons.concat([])
      console.log('setPolygonList', this.polygonList)

      //move
      if(this.polygonList.length>0){
        this.map.panTo({lat: this.polygonList[0].paths[0][0].lat, lng: this.polygonList[0].paths[0][0].lng})
        this.map.setZoom(16)
      }

    },
    test(){
      let data="10.9072078,106.6566575;10.9076186,106.6569149;10.9079979,106.6571832;10.908493,106.6573226;10.9086616,106.6573012;10.9086932,106.6568828;10.9089144,106.6565824;10.9093674,106.6565287;10.9098942,106.6561854;10.9102418,106.6557348;10.9107159,106.6554666;10.9105684,106.6550052;10.9104736,106.6547048;10.9104525,106.6545224;10.9110741,106.6544366;10.9114955,106.6542757;10.9118695,106.6536587;10.9124489,106.6539645;10.9129914,106.6539377;10.9137183,106.6539216;10.9142082,106.6538036;10.9147086,106.6537875;10.914993,106.6537285;10.9153618,106.6535354;10.9157094,106.6532993;10.9161308,106.6534388;10.9165943,106.6535354;10.9171527,106.6532457;10.9175214,106.6531599;10.9176794,106.6527092;10.918164,106.6521943;10.9185748,106.6525805;10.9190384,106.6530955;10.9193439,106.6528487;10.919702,106.6518831;10.919702,106.65066;10.9193649,106.6497159;10.919091,106.6483426;10.9195335,106.6471624;10.9194703,106.6457033;10.9188593,106.6445017;10.9182272,106.6436863;10.9173001,106.6414547;10.9160781,106.6384077;10.9161203,106.6349316;10.9159938,106.6314983;10.9159096,106.6293526;10.9156567,106.6271639;10.9151089,106.6246319;10.9147297,106.6234088;10.913908,106.6207695;10.9137183,106.6194391;10.9126438,106.6175508;10.9112532,106.6174221;10.9099258,106.6177869;10.9093358,106.6189885;10.9085562,106.6198897;10.9079663,106.620748;10.9075238,106.6201901;10.9068706,106.6199541;10.9059857,106.6204906;10.9053115,106.6199756;10.9049954,106.6196966;10.9043001,106.6194606;10.9034151,106.6194177;10.9030569,106.6195464;10.9022563,106.6197824;10.9010131,106.6199112;10.8993749,106.6199809;10.8985742,106.6193426;10.8979789,106.6190851;10.8973574,106.6189885;10.8972415,106.6195571;10.8969676,106.6199541;10.8967147,106.6203189;10.8964513,106.6206622;10.896209,106.6210163;10.8954188,106.6216171;10.895324,106.6216975;10.895619,106.6222233;10.8958561,106.6225183;10.8961879,106.6229904;10.8965777,106.6235161;10.8969992,106.6241491;10.8975154,106.6247982;10.8981791,106.6258228;10.8988955,106.6268528;10.8990852,106.6271961;10.8989693,106.6280437;10.8987586,106.6286659;10.8985373,106.6293311;10.8983477,106.6299212;10.8986216,106.6301572;10.898885,106.6303879;10.8985479,106.630919;10.8985347,106.6310129;10.8989877,106.631265;10.8995461,106.6315359;10.8997805,106.6313696;10.8999754,106.6314232;10.8999227,106.6317612;10.8998648,106.6320562;10.899762,106.6326678;10.9001387,106.6326892;10.9006312,106.6327643;10.9008445,106.6328368;10.9008182,106.6331908;10.9007155,106.6336843;10.9006338,106.6342959;10.9004995,106.6346553;10.9004021,106.6351354;10.9004021,106.6356665;10.9004758,106.6361761;10.9005074,106.6363907;10.9003389,106.6364658;10.9000847,106.6365503;10.9002967,106.6368842;10.9005285,106.6371632;10.9010236,106.6377532;10.9013292,106.6380912;10.9015662,106.6383728;10.90233,106.638051;10.9025697,106.6379678;10.9028041,106.6379571;10.9029753,106.6381609;10.9030043,106.6382816;10.9032202,106.6382682;10.9033835,106.6382039;10.9035468,106.6386276;10.903639,106.6387913;10.9034968,106.6388476;10.9033756,106.6389227;10.9033072,106.6390407;10.9033809,106.6392472;10.9034415,106.6396335;10.9030806,106.6396683;10.9030912,106.639958;10.9031043,106.6402879;10.9038945,106.6402531;10.9042843,106.6401994;10.9045582,106.6407949;10.9048321,106.6414118;10.9050059,106.6418678;10.905775,106.6415191;10.9064229,106.6412401;10.9067074,106.641798;10.9068601,106.6418999;10.9069339,106.6420126;10.9067179,106.6421574;10.9062649,106.6422755;10.9065388,106.6430801;10.9066336,106.6434985;10.9066968,106.6439706;10.9072025,106.6440082;10.9078609,106.6441369;10.9089197,106.6443193;10.9090356,106.6452795;10.9091357,106.6457248;10.9090962,106.6462907;10.9089144,106.647259;10.9088038,106.6481066;10.9086932,106.6488361;10.9086405,106.6492867;10.9092094,106.6492009;10.9093674,106.65007;10.9093148,106.6506708;10.9092832,106.6512072;10.908788,106.6511536;10.9086037,106.6515398;10.9082402,106.6514808;10.9081981,106.6518831;10.9076713,106.6541147;10.9072078,106.6567326;10.9072078,106.6566575;10.9072078,106.6566575"
      let polygons=data.split('|')
      let ps=[]
      for(let i=0;i<polygons.length;i++){
        ps.push({
          id:i+1,
          ref:'polygon_'+(i+1),
          options:this.polygonOptionDefault,
          paths:[polygons[i].split(';').map(x=>{
            return {
              lat:Number(x.split(',')[0]),
              lng:Number(x.split(',')[1]),
            }
          })
          ]
        })
      }
      this.polygonList=ps.concat([])
      console.log('polygonList', this.polygonList)

      if(this.polygonList.length>0){
        this.map.panTo({lat: this.polygonList[0].paths[0][0].lat, lng: this.polygonList[0].paths[0][0].lng})
        this.map.setZoom(16)
      }
    }

  }
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
  .control-map{
    margin-top: 4px;
  }
</style>
