<template>
  <div style="height: 400px; width:100%;">
      <div id="mapVNPT"></div>
  </div>
</template>
<script>
import VnptMap from '@vnpt/maps-onebss'
export default {
  name: 'BanDoVNPT',
  props: ["lat", "lng", "description", "mode"],
  data(){
    return {
        basemapApi: null,
        mapVNPT: null,
        zoomDefault: 16,
        pointLayer:null
    }
  },
  computed:{
    
  },
  methods:{
    gotoPosition(position){
        setTimeout(()=>{
            this.mapVNPT.flyTo(position, 13);
            if(this.pointLayer){
                this.pointLayer.setData([position.lng, position.lat])
            }
        }, 500)
    },
    getPositionCenter () {
      if(this.$root.token.getPhanVungID()==21){
        //Hni
        return {lat: 21.02772261293581, lng: 105.8358304046256}
      }else{
        //hcm
        return { lat: 10.82477099104633, lng: 106.63214587243222 }
      }
      //return this.positionCenter 
    },
  },
  mounted(){
    let options = {
      showZoomControl: false,
      zoom: this.zoomDefault,
    }
    this.mapVNPT =  VnptMap.initMap('mapVNPT', options)
    this.basemapApi = VnptMap.initBasemapApi()
    console.log('🚀 ~ created ~ mapVNPT:', this.mapVNPT)
    this.mapVNPT.on('load', (e) => {
        //add point
        const point = [this.getPositionCenter().lng, this.getPositionCenter().lat];
        this.mapVNPT.flyTo(point, 13);
        this.pointLayer = VnptMap.initPoint(point, {
            iconUrl: "https://maps.vnpt.vn/v2/assets/images/markers/start-marker.png",
            id: "Point",
            iconWidth: 30,
            iconHeight: 30
        });
        this.pointLayer.addLayerTo(this.mapVNPT);
    })
    this.mapVNPT.on('click', (e) => {
        if(this.pointLayer){
            this.pointLayer.setData([e.lngLat.lng, e.lngLat.lat])
        }
    })
  },
  watch:{
    lat(val){
        this.gotoPosition({lat: this.lat, lng: this.lng})
    }
  },
  beforeDestroy() {
    this.mapVNPT.remove()
    this.pointLayer.remove()
  },
}
</script>
<style>
.custom-label-marker2{
  background-color: #FFF;
  padding: 5px;
  border: #CCC solid thin;
}
#mapVNPT {
  height: 400px !important;
  width: auto !important;
}
</style>
