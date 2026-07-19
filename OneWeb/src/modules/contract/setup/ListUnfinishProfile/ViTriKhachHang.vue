<template>
    <div class="box-form">
        <div class="legend-title">
            <div class="pull-left mart5">
                Vị trí khách hàng
            </div>
            <div class="pull-right actions-icon">
                <a class="inline pad2 f24 nc-icon-glyph location_crosshair" @click="gotoPosition()"></a>
            </div>
            <div class="clearfix"></div>
        </div>
        <div class="map-canvas">
            <!-- <Map ref="mapRef"
                :lat="Number(map.lat)"
                :lng="Number(map.lng)"
                :description="map.dia_chi"
                :mode="modeMap"
            /> -->
            <MapVNPT ref="mapVNPT"
                :lat="Number(map.lat)"
                :lng="Number(map.lng)"
                :description="map.dia_chi"
                :mode="modeMap"
            />
        </div>
        <b-row style="padding-top: 10px">
            <vue-element label="Kinh độ" class="col-md-6">
                <ejs-textbox v-model="map.lng" disabled></ejs-textbox>
            </vue-element>
            <vue-element label="Vĩ độ" class="col-md-6">
                <ejs-textbox v-model="map.lat" disabled></ejs-textbox>
            </vue-element>
        </b-row>
    </div>
</template>
<script>
import MapVNPT from './MapVNPT.vue';
export default {
    name:'ViTriKhachHang',
    components:{
        MapVNPT
    },
    data() {
        return {
            model: {
                
            },
            modeMap: 1,
            map: {
                lat: 0,
                lng: 0,
                dia_chi: ""
            }
            //{lat: 21.02772261293581, lng: 105.8358304046256}
        }
    },
    computed: {

    },
    methods: {
        gotoPosition() {
            this.$refs.mapVNPT.gotoPosition({
                lat: this.map.lat,
                lng:this.map.lng
            })
        }
    },
    mounted() {
        
    },
}
</script>