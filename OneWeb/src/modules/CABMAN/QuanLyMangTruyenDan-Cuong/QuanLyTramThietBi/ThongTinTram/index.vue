<template src="./index.html"></template>

<script>
import ModalChonDiaChi from '../ModalChonDiaChi'
import BanDo from '../BanDo/components/BanDo.vue'
import BanDoVNPT from '../BanDo/components/BanDoVNPT.vue'
import ModalChonToaDo from '../BanDo'
export default {
  props: {
    isActive: Boolean,
    tramTb_id: Number
  },
  components: {
    ModalChonDiaChi,
    BanDo,
    BanDoVNPT,
    ModalChonToaDo
  },
  data() {
    return {
      tramTb: {},
      TTVT: null,
      toVT: null,
      maTram: null,
      loaiTram: null,
      tenTram: null,
      diaChi: null,
      toaDo: null,
      isShowDiaChi: false,
      isShowToaDo: false,
      zoomDefault: 15,
      mapTypeId: 'ROADMAP',
      positionDefault: {
        lat: 0,
        lng: 0
      }
    }
  },
  watch: {
    toaDo: function (val) {
      this.positionDefault = {
        lat: parseFloat(val.split(',')[0]),
        lng: parseFloat(val.split(',')[1])
      }
      this.$refs.refBanDoVNPT.resetPoint()
      this.$refs.refBanDoVNPT.setMarkLocation(this.positionDefault)
    }
  },
  methods: {
    closeModalDiaChi: async function (data) {
      this.isShowDiaChi = !this.isShowDiaChi
      if (data.isUpdated) {
        this.diaChi = data.diaChi
        try {
          this.loading(true)
          let rs = await this.$root.context.post('/web-cabman/mang-truyen-dan/capnhat-diachi', {
            tramtb_id: this.tramTb.TRAMTB_ID,
            diachi: this.diaChi
          })
          if (rs.data.result) {
            this.$root.toastSuccess('Cập nhật thành công.')
          }
        } catch (error) {
          this.$root.toastError(error.response.data.message_detail ? error.response.data.message_detail : 'Cập nhật thất bại.')
        } finally {
          this.loading(false)
        }
      }
    },
    changetypeMap: function () {
      if (this.mapTypeId === 'ROADMAP') {
        this.mapTypeId = 'SATELLITE'
      } else {
        this.mapTypeId = 'ROADMAP'
      }
    },
    zoom: function (val) {
      this.zoomDefault += val
      console.log(this.zoomDefault)
    },
    XacNhanToaDo: async function (position) {
      try {
        this.loading(true)
        let rs = await this.$root.context.post('/web-cabman/mang-truyen-dan/capnhat-toado', {
          tramtb_id: this.tramTb.TRAMTB_ID,
          vido: parseFloat(position.lat),
          kinhdo: parseFloat(position.lng)
        })
        if (rs.data.result) {
          this.isShowToaDo = !this.isShowToaDo
          this.$emit('layToaDoMoi', {
            lat: parseFloat(position.lat),
            lng: parseFloat(position.lng)
          })
          this.positionDefault = position
          this.toaDo = position.lat + ', ' + position.lng
          this.$root.toastSuccess('Cập nhật tọa độ thành công')
        } else {
          this.$root.toastError('Cập nhật tọa độ thất bại')
        }
      } catch (error) {
        this.$root.toastError(error.response.data.message_detail ? error.response.data.message_detail : 'Cập nhật tọa độ thất bại')
      } finally {
        this.loading(false)
      }
    }
  },
  mounted: async function () {
    try {
      this.loading(true)
      if (this.tramTb_id) {
        let rs = await this.$root.context.get(`/web-cabman/mang-truyen-dan/lay-tt-tramtbi-by-id/${this.tramTb_id}`)
        this.tramTb = rs.data.length > 0 ? rs.data[0] : {}
        if (this.tramTb) {
          this.TTVT = this.tramTb.TEN_TTVT
          this.toVT = this.tramTb.TEN_TOVT
          this.maTram = this.tramTb.MA_TRAMTB
          this.loaiTram = this.tramTb.LOAI_TRAMTB
          this.tenTram = this.tramTb.TEN_TRAMTB
          this.diaChi = this.tramTb.DIACHI_TRAMTB
          this.toaDo = this.tramTb.TOADO_TRAMTB
          this.positionDefault = {
            lat: this.tramTb.VIDO,
            lng: this.tramTb.KINHDO
          }
        }
      }
    } catch (error) {
    } finally {
      this.loading(false)
    }
  }
}
</script>
