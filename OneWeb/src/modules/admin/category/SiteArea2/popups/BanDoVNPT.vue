<template>
  <div id="mapVNPT"></div>
</template>
<script>
import VnptMap from '@vnpt/maps-onebss'
import VnptPlugin from '@vnpt/maps-plugin'
import VnptMapDraw from '@vnpt/maps-draw'
export default {
  name: 'BanDoVNPT',
  props: ['position'],
  async created () {

  },
  data () {
    return {
      zoomDefault: 16,
      map: null,
      positionCenter: this.position,
      center: {},
      paths:[],
      polygonList:[],

      basemapApi: null,
      mapVNPT: null,
      draw:null,
      pointLayer:null
    }
  },
  mounted () {
    let options = {
      showZoomControl: false,
      zoom: this.zoomDefault
      //center: [106.69565920830763, 10.77326028548758],
    }
    this.mapVNPT =  VnptMap.initMap('mapVNPT', options)
    this.basemapApi = VnptMap.initBasemapApi()
    console.log('🚀 ~ created ~ mapVNPT:', this.mapVNPT)
    
    this.draw=new VnptMapDraw()

    VnptPlugin.addControlPlugin(this.draw, this.mapVNPT)

    this.mapVNPT.on('draw.create', (e) => {
      //console.log('draw.create', e)  
      let feature=e.features[0]
      this.polygonList.push({
        id:feature.id,
        type: 'Feature',
        geometry: {
          type: 'Polygon',
          coordinates:feature.geometry.coordinates,
        }
      })
    })

    this.mapVNPT.on('draw.update', (e) => {
      console.log('draw.update', e)
      let feature=e.features[0]
      const index=this.polygonList.findIndex(x=>x.id==feature.id)
      if(index>-1){
        this.polygonList[index].geometry.coordinates=feature.geometry.coordinates
      }
    })
    this.mapVNPT.on('draw.delete', (e) => {
      let feature=e.features[0]
      const index=this.polygonList.findIndex(x=>x.id==feature.id)
      if(index>-1){
        this.polygonList.splice(index,1)
      }
    })

    this.mapVNPT.on('load', (e) => {
        //add point
        const point = [this.getPositionCenter().lng, this.getPositionCenter().lat];
        this.pointLayer = VnptMap.initPoint(point, {
            iconUrl: "https://maps.vnpt.vn/v2/assets/images/markers/start-marker.png",
            id: "Point",
            iconWidth: 32,
            iconHeight: 32
        });
        this.pointLayer.addLayerTo(this.mapVNPT);
    })

    // this.mapVNPT.on('draw.selectionchange', (e) => {
    //   console.log('draw.selectionchange', e)
    // })

    // this.mapVNPT.on('click', (e) => {
    //   console.log('click', e)
    // })
  },
  beforeDestroy() {
    this.mapVNPT.remove()
    this.pointLayer.remove()
  },
  methods: {
    searchLocation(pos){
      this.setPositionCenter({
        lat:Number(pos.lat),
        lng:Number(pos.lng),
      })
    },
    async searchAddress(input){
      //this.searchLocation(input)
      try{
        this.loading(true)
        const response = await fetch(`https://maps.vnpt.vn/kong/api/places/v1/geocoding/latlong_by_address?q=address:${input}`, {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
            "Authorization":" VNPT-68ad957d-313e-4cf9-8a07-b4c033aa2937"
          }
        })
        this.loading(false)
        const result = await response.json()
        console.log("Success:", result)
        if(result.msg=="OK"&&result.result.length>0){
          let posFirst=result.result[0]
          console.log('posFirst', posFirst)
          this.pointLayer.setData([Number(posFirst.long),  Number(posFirst.lat)])
          this.mapVNPT.flyTo([Number(posFirst.long),  Number(posFirst.lat)], 13);
        }
      }catch(e){
        this.loading(false)
        this.$toast.error('Đã xảy ra lỗi')
      }
     
    },
    updateSizeMap(){
      setTimeout(()=>{
        this.mapVNPT.updateSized()
      },500)
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
    TaoVung(){
      for(let i=0;i<this.polygonList.length;i++){
        this.polygonList[i].geometry.coordinates=[]
        this.draw.add(this.polygonList[i])
      }
      // let allLayers=this.mapVNPT.getAllLayers()
      // console.log('TaoVung', allLayers)

      //remove vùng chọn
      //this.draw.trash()
    },
    polygonPaths(){
      let polygons=[]
      for(let i=0;i<this.polygonList.length;i++){
        polygons.push(this.polygonList[i].geometry.coordinates[0])
      }
      return polygons
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
        'text':this.positionCenter.lat + ';' + this.positionCenter.lng,
        'color': '#3225F6',
        'fontSize': '12px',
        'fontFamily': 'Arial',
        'fontWeight': 'bold',
        'className': 'custom-label-marker2'
      })
    },
    setPositionCenter(position){
      this.positionCenter=position
      this.pointLayer.setData([Number(this.positionCenter.lng),  Number(this.positionCenter.lat)])
      this.mapVNPT.flyTo([Number(this.positionCenter.lng),  Number(this.positionCenter.lat)], 13);
    },
    CleanPolygons(){
      for(let i=0;i<this.polygonList.length;i++){
        this.polygonList[i].geometry.coordinates=[]
        this.draw.add(this.polygonList[i])
      }
    },
    setPolygonList(list){
      this.CleanPolygons()
      this.polygonList=[]
      console.log('list', list)
      for(let i=0;i<list.length;i++){
        this.polygonList.push({
          id:crypto.randomUUID(),
          type: 'Feature',
          geometry: {
            type: 'Polygon',
            coordinates: [list[i].map(x=>{
                return [
                  Number(x.LNG),
                  Number(x.LAT),
                ]
            })],
          }
        })
      }
      console.log('setPolygonList', this.polygonList)
      for(let i=0;i<this.polygonList.length;i++){
        this.draw.add(this.polygonList[i])
      }

      //panTo 
      if(this.polygonList.length>0){
        let locationPanTo=this.polygonList[0].geometry.coordinates[0][0]
        console.log('locationPanTo', locationPanTo)
        //move
        if(this.polygonList.length>0){
          this.mapVNPT.flyTo([Number(locationPanTo[0]),  Number(locationPanTo[1])], 13);
          // this.mapVNPT.panTo({lat: locationPanTo[1], lng: locationPanTo[0]})
          // this.mapVNPT.setZoom(14)
        }
      }

    },
    getStylesMapDraw(){
      return {
        styles: [
        {
          id: 'gl-draw-polygon-fill-inactive', // style polygon khi inactive
          type: 'fill',
          paint: {
            'fill-color': 'black',
            'fill-outline-color': 'black',
            'fill-opacity': 0.1,
          },
        },
        {
          id: 'gl-draw-polygon-fill-active', // style polygon khi active
          type: 'fill',
          paint: {
            'fill-color': '#fbb03b',
            'fill-outline-color': '#fbb03b',
            'fill-opacity': 0.1,
          },
        },
        {
          id: 'gl-draw-polygon-midpoint', // style của điểm kéo thả
          type: 'circle',
          paint: {
            'circle-radius': 3,
            'circle-color': '#fbb03b',
          },
        },
        {
          id: 'gl-draw-polygon-stroke-inactive', // style của đường viền của polygon khi inactive
          type: 'line',
          layout: {
            'line-cap': 'round',
            'line-join': 'round',
          },
          paint: {
            'line-color': '#3bb2d0',
            'line-width': 2,
          },
        },
        {
          id: 'gl-draw-polygon-stroke-active', // style của đường viền của polygon khi active
          type: 'line',
          layout: {
            'line-cap': 'round',
            'line-join': 'round',
          },
          paint: {
            'line-color': '#fbb03b',
            'line-dasharray': [0.2, 2],
            'line-width': 2,
          },
        },
      ],
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
#mapVNPT {
  height: 550px !important;
  width: auto !important;
}
@media only screen and (min-height: 768px){
   #mapVNPT {
    height: 450px !important;
    width: auto !important;
  }
}
@media only screen and (min-height: 900px){
   #mapVNPT {
    height: 550px !important;
    width: auto !important;
  }
}
@media only screen and (min-height: 1080px){
   #mapVNPT {
    height: 650px !important;
    width: auto !important;
  }
}
@media only screen and (min-height: 2160px){
   #mapVNPT {
    height: 1000px !important;
    width: auto !important;
  }
}

</style>
