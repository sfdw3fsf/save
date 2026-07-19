<template>
  <div>
    <GmapMap
      :options="{
        zoomControl: true,
        mapTypeControl: true,
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
      />
    </GmapMap>
  </div>
</template>
<script>
export default {
    props: ["lat", "lng", "description", "mode"],
    mounted() {
      
    },
    methods: {
      
    },
};
</script>
<style>
.pac-target-input {
  width: 100% !important;
  border: 0px;
}
</style>
