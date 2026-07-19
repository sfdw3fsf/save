<template>
  <div class="map-canvas-box">
    <div class="map-canvas-main">
      <div id="map" class="map"></div>
    </div>
  </div>
</template>
<script>

import VnptMap from '@vnpt/maps-onebss';

export default {
  components: {},
  name: 'BanDo',
  props: {
    position: null,
    diachi: null,
    mapTypeId: null,
    zoomDefault: null
  },
  data() {
    return {
      lat: 0,
      lng: 0,
      txtSearch: "",
      map: null,
      point: null,
      token: null,
      iconUrl: "/static/icons/location.png"
    }
  },
  watch: {
    position: function (v) {
      if (v == undefined || v == null) {
        this.lng = 0;
        this.lat = 0;
      }
      else {
        this.lng = v.lng;
        this.lat = v.lat;
      }
      this.setPoint(this.lng, this.lat, this.diachi);
    },
    diachi: function (v) {
      let t = "";
      if (!(v == undefined || v == null)) {
        t = v;
      }
      this.setPoint(this.lng, this.lat, t);
    }
  },
  async mounted() {
    let tt = await this.getThongTinBanDo();
    if (tt == null || tt == undefined) {
      this.token = null;
    }
    else {
      this.token = tt.TOKEN;
      this.map = VnptMap.initMap("map");
      this.map.on("click", (e) => {
        this.$emit("dblclickMap", e.lngLat);
      });
      setTimeout(() => this.map.updateSized(), 10);
    }
    if (this.token == null) {
      this.$root.toastError("Lỗi truy cập đến thông tin bản đồ");
    }
  },
  methods: {
    searchLocation: function (searchAddressInput) {
      let basemapApi = VnptMap.initBasemapApi();
      basemapApi.getLonLatByAddress(searchAddressInput, this.token, (evt) => {
        if (evt.status) {
          let result = null;
          for (let i in evt.result) {
            if (evt.result != null) {
              if (evt.result[i].address === searchAddressInput) result = evt.result[i];
            }
          }
          if(evt.result != null)
            if (!result) result = evt.result[0];
          if (!(result === null || result.type === "ward" || result.type === "dist" || result.type === "city")) {
            this.setPoint(result.long, result.lat, result.address);
          }
        }
      });
    },
    setPoint: function (lng, lat, address) {
      this.lng = lng;
      this.lat = lat;
      let coord = [this.lng, this.lat];
      if (this.point == null) {
        this.point = VnptMap.initPoint(coord, {
          iconUrl: this.iconUrl,
          id: "Point",
          isDraggable: true,
          iconWidth: 48,
          iconHeight: 48
        });
        this.point.addLayerTo(this.map);
        this.point.onDrag('dragend', (e) => {
          this.lat = e.coordinates.lat;
          this.lng = e.coordinates.lng;
          this.$emit('dragend', { lng: this.lng, lat: this.lat });
        });
      }
      else {
        this.point.setData(coord);
      }
      //this.point.addPopup(address);
    },
    setPointFly: function (lng, lat, address) {
      this.setPoint(lng, lat, address);
      this.map.flyTo([this.lng, this.lat], 13);
    },
    zoomIn() {

    },
    zoomOut() {

    },
    getThongTinBanDo: async function () {
      try {
        let rs = await this.$root.context.post("/web-cabman/quanlyhatang/getThongTinBanDo");
        return rs.data;
      }
      catch (e) {
        this.$root.toastError("Lỗi truy cập đến thông tin bản đồ");
        return null;
      }
    },
  },
}
</script>
<style>
.map {
  position: initial;
  height: 400px;
  width: 100%;
}
.map-canvas-box {
  overflow: hidden;
}
</style>
