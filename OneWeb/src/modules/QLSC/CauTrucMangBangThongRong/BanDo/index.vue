/* eslint-disable standard/object-curly-even-spacing */
/* eslint-disable camelcase */
<template src="./index.template.html"></template>
<script>

import './../../../../utils/gmaps'
import BanDo from './BanDo'

export default {
  components: { BanDo },
  name: 'ModalChonToaDo',
  props: {
    idModalChonToaDo: null,
    position: null,
    diachi: null
  },
  data () {
    return {
      lat: 0,
      lng: 0,
      positionKetCuoi: {
        lat: 0, lng: 0
      },
    }
  },
  computed: {
    searchAddressInput: function(){
      return this.lat + "," + this.lng;
    }
  },
  watch: {
    positionKetCuoi: function (v) {
      if (v == undefined || v == null) {
        this.lng = 0;
        this.lat = 0;
      }
      else {
        this.lng = v.lng;
        this.lat = v.lat;
      }
      this.setPoint(this.lng, this.lat, this.searchAddressInput);
    }
  },
  async mounted () {
    await this.getThongTinBanDo();
  },
  methods: {
    getThongTinBanDo: async function () {
      try {
        let rs = await this.$root.context.get(
          '/web-cabman/bando_matdo/thongtin_bando'
        )
        if (rs.data != null && rs.data.length > 0) {
          let thongtinbando = rs.data[0]
          this.lat = parseFloat(thongtinbando.C_LATITUS)
          this.lng = parseFloat(thongtinbando.C_LONGTITUS)
        }
      } catch (error) {
        console.log('getThongTinBanDo', error)
      } finally {
        this.$root.showLoading(false)
      }
    },
    searchLocation () {
      this.$refs.BanDo.map.flyTo([this.lng, this.lat], 13);
      //this.$refs.BanDo.searchLocation(this.searchAddressInput.trim())
    },
    setPoint: function (lng, lat, address) {
      this.$refs.BanDo.setPoint(lng, lat, address);
    },
    XacNhanToaDo () {
      const position = {...this.positionKetCuoi}
      this.$emit('XacNhanToaDo', position)
      this.$nextTick(() => {
        this.$bvModal.hide(this.idModalChonToaDo)
      })
    },
    dblclickMap (event) {
      this.lat = event.lat;
      this.lng = event.lng;
      this.$refs.BanDo.setPoint(this.lng, this.lat, this.searchAddressInput);
    },
    onClickZoomIn () {
      this.$refs.BanDo.zoomIn()
    },
    onClickZoomOut () {
      this.$refs.BanDo.zoomOut()
    },
    banDo_dragend: function(args){
      this.lat = args.lat;
      this.lng = args.lng;
    },
    modalToaDo_Show: function () {
      this.$refs.BanDo.setPointFly(this.lng, this.lat, this.searchAddressInput);
    },
  },
}
</script>

<style>
.modal-fullscreen .modal-dialog {
    max-width: 100%;
    margin: 0;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
    height: 100vh;
    display: flex;
    position: fixed;
    z-index: 100000;
}
.dropbtn {
  border-radius:4px;
  height: 32px;
  font-size: 16px;
}

.dropdown {
  display: table-cell;
  width: 10px;
  vertical-align: top;
  white-space: nowrap;
}

.dropdown-content {
  display: none;
  position: absolute;
  background-color: #f1f1f1;
  min-width: 160px;
  box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
  z-index: 1;
}

.dropdown-content a {
  color: black;
  padding: 12px 16px;
  text-decoration: none;
  display: block;
}

.dropdown-content a:hover {background-color: #ddd;}

.dropdown:hover .dropdown-content {display: block;}

.dropdown:hover .dropbtn {}
</style>
