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
  computed: {
    getPosition () {
      if (isNaN(this.position.lat) || isNaN(this.position.lng) || this.position.lat * this.position.lng === 0) {
        return {...this.positionDefault}
      } else return {...this.position}
    }
  },
  async mounted () {
    try {

      this.getThongTinBanDo()
      // this.panzoom = Panzoom(document.getElementById('BanDo'), {
      //   maxScale: 10
      // })
    } catch (error) {
      console.log('error', error)
    }
  },
  data () {
    return {
      panzoom:"",
      mapTypeId: 'ROADMAP',
      positionDefault: {
        lat: 0, lng: 0
      },
      zoomDefault: 15,
      positionKetCuoi: {
        lat: 0, lng: 0
      },
      thongtinbando: {},
      searchAddressInput: this.position.lat + "," +this.position.lng
    }
  },
  methods: {
    getThongTinBanDo: async function () {
      try {
        var rs = await this.$root.context.get(
          '/web-cabman/bando_matdo/thongtin_bando'
        )
        if (rs.data != null && rs.data.length > 0) {
          this.thongtinbando = rs.data[0]
          this.positionDefault.lat = parseFloat(this.thongtinbando.C_LATITUS)
          this.positionDefault.lng = parseFloat(this.thongtinbando.C_LONGTITUS)
          this.zoomDefault = parseFloat(this.thongtinbando.DEFAULT_ZOOM)
          console.log('getThongTinBanDo', this.positionDefault)
        }
      } catch (error) {
        console.log('getThongTinBanDo', error)
      } finally {
        this.$root.showLoading(false)
      }
    },
    searchLocation () {
      console.log('searchLocation', this.searchAddressInput)
      var map = this.$refs.BanDo
      map.searchLocation(this.searchAddressInput.trim())
    },
    XacNhanToaDo () {
      const position = {...this.positionKetCuoi}
      this.$emit('XacNhanToaDo', position)
      this.$nextTick(() => {
        this.$bvModal.hide(this.idModalChonToaDo)
      })
    },
    changetypeMap () {
      console.log('this.mapTypeId', this.mapTypeId)
      if (this.mapTypeId === 'ROADMAP') {
        this.mapTypeId = 'SATELLITE'
      } else {
        this.mapTypeId = 'ROADMAP'
      }
    },
    dblclickMap (event) {
      this.positionKetCuoi.lat = event.lat
      this.positionKetCuoi.lng = event.lng
    },
    onClickZoomIn () {
      this.$refs.BanDo.zoomIn()
    },
    onClickZoomOut () {
      this.$refs.BanDo.zoomOut()
    },

  },
  watch: {
    position (val) {
      if (val.lat * val.lng === 0) {
        this.positionKetCuoi = {...this.positionDefault}
      } else {
        this.positionKetCuoi = {...this.position}
      }
    },
    diachi (val) {
      this.searchAddressInput = val
    }
  },
  created () {

  }
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
