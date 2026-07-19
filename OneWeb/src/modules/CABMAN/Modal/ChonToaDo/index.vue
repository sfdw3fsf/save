<template src="./index.template.html"></template>
<script>

import '@/utils/gmaps'
import BanDo from './components/BanDo.vue'
import EventBus from '@/utils/eventBus'

export default {
  components: { BanDo },
  name: 'ModalChonToaDoExt',
  props: {
    idModalChonToaDo: null
  },
  computed: {
    getPosition () {
      if (this.position === null || (this.position.lat * this.position.lng === 0)) {
        return {...this.positionDefault}
      } else return {...this.position}
    }
  },
  async mounted () {
    this.getThongTinBanDo()
  },
  data () {
    return {
      position: {
        lat: 0, lng: 0
      },
      diachi: null,
      mapTypeId: 'ROADMAP',
      positionDefault: {
        lat: 0, lng: 0
      },
      zoomDefault: 15,
      positionKetCuoi: {
        lat: 0, lng: 0
      },
      thongtinbando: {},
      searchAddressInput: this.diachi,
      resolvePromise: null,
      rejectPromise: null
    }
  },
  methods: {
    InBanDo () {
      var file = 'map_' + new Date().toISOString().slice(0, 10)
      EventBus.$emit('IN_BANDO', file)
    },
    show (position, diachi) {
      return new Promise((resolve, reject) => {
        this.position = position
        this.diachi = diachi
        this.$bvModal.show(this.idModalChonToaDo)
        this.resolvePromise = resolve
        this.rejectPromise = reject
      })
    },
    closeModal: function () {
      this.resolvePromise({ok: false, data: null})
      this.$bvModal.hide(this.idModalChonToaDo)
    },
    getThongTinBanDo: async function () {
      try {
        this.$root.showLoading(true)
        var rs = await this.$root.context.get(
          '/web-cabman/bando_matdo/thongtin_bando'
        )
        if (rs.data != null && rs.data.length > 0) {
          this.thongtinbando = rs.data[0]
          this.positionDefault.lat = parseFloat(this.thongtinbando.C_LATITUS)
          this.positionDefault.lng = parseFloat(this.thongtinbando.C_LONGTITUS)
          this.zoomDefault = parseFloat(this.thongtinbando.DEFAULT_ZOOM)
        }
      } catch (error) {
        this.$root.showLoading(false)
      } finally {
        this.$root.showLoading(false)
      }
    },
    searchLocation () {
      var map = this.$refs.BanDo
      map.searchLocation(this.searchAddressInput.trim())
    },
    XacNhanToaDo () {
      const position = {...this.positionKetCuoi}
      this.resolvePromise({ok: true, data: position})
      this.$bvModal.hide(this.idModalChonToaDo)
    },
    changetypeMap () {
      if (this.mapTypeId === 'ROADMAP') {
        this.mapTypeId = 'SATELLITE'
      } else {
        this.mapTypeId = 'ROADMAP'
      }
    },
    dblclickMap (event) {
      this.positionKetCuoi.lat = event.lat
      this.positionKetCuoi.lng = event.lng
    }
  },
  watch: {
    position (val) {
      if (val === null || (val !== null && (val.lat * val.lng === 0))) {
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

<style scoped>
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
.btn-lg {
    background: #AED6F1 ;
    color: #000;
    border:2px solid;
}
</style>
