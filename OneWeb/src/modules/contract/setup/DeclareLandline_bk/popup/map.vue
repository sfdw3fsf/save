<template>
  <div>
    <GmapMap
      :options="{
        zoomControl: true,
        mapTypeControl: false,
        scaleControl: false,
        streetViewControl: false,
        rotateControl: false,
        fullscreenControl: true,
        disableDefaultUI: false,
      }"
      :center="{ lat: lat, lng: lng }"
      :zoom="15"
      ref="bando"
      style="width: 100%; height: 400px"
    >
      <GmapMarker
        :position="{ lat: this.lat, lng: this.lng }"
        :clickable="false"
        :draggable="true"
        @dragend="update"
      />
    </GmapMap>
  </div>
</template>
<script>
import api from '../api/index.js'
export default {
  props: ["lat", "lng", "description", "hdtb_id", "mode"],
  mounted() {
    if(this.lat * this.lng) {
      this.checkLatLng();
    } else if(this.description) {
      this.checkAdress();
    } else {
      this.$toast.error("Chưa có thông tin về bản đồ địa chỉ thuê bao")
    }
  },
  methods: {
    async update(val) {
      await api.fn_capnhat_toado_hdtb(this.axios, {"lat":val.latLng.lat(),"lng":val.latLng.lng(),"hdtb_id":this.hdtb_id,"mode":this.mode})
      this.$toast.success("Cập nhật tọa độ thành công!")
    },
    checkAdress() {
      this.$refs.bando.$mapPromise.then(() => {
        var geocoder = new google.maps.Geocoder();

        var address = this.description;

        geocoder.geocode({ address: address }, async (results, status) => {
          if (status == google.maps.GeocoderStatus.OK) {
            var lat = results[0].geometry.location.lat();
            var lng = results[0].geometry.location.lng();
            await api.fn_capnhat_toado_hdtb(this.axios, {"lat":lat,"lng":lng,"hdtb_id":this.hdtb_id,"mode":this.mode})
            this.$toast.success("Cập nhật tọa độ thành công!")
          }
        });
      });
    },
    async checkLatLng(lat, lng) {
      this.$refs.bando.$mapPromise.then(() => {
        var latlng = new google.maps.LatLng(lat, lng);
        var geocoder = new google.maps.Geocoder();
        geocoder.geocode({ 'latLng': latlng },  async (results, status) =>{
            // This is checking to see if the Geoeode Status is OK before proceeding
            console.log(status)
            if (status == google.maps.GeocoderStatus.OK) {
              await api.fn_capnhat_toado_hdtb(this.axios, {"lat":lat,"lng":lng,"hdtb_id":this.hdtb_id,"mode":this.mode})
              this.$toast.success("Cập nhật tọa độ thành công!")
            }
        });
      });
    },
  },
};
</script>
<style>
.pac-target-input {
  width: 100% !important;
  border: 0px;
}
</style>
