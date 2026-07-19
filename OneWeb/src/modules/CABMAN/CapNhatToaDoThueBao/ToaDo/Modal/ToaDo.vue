<template src="./ToaDo.html"></template>
<script>
// import './../../../../../utils/gmaps'
// import GMap from '../../Gmap/GMap.vue'
import BanDo from '../../components/BanDo.vue'
// import BanDoVNPT from '../../components/BanDoVNPT.vue'
import ModalChonDiaChi from '../../ModalChonDiaChi'
// import EventBus from '../../../../../utils/eventBus'
export default {
  name: 'ToaDo',
  props: {
    modalId: String,
    data: [],
    markers: {
      type: Array,
      default: function () {
        return [
          {
            VIDO: 0,
            KINHDO: 0,
            LOAI: 1
          }
        ]
      }
    },
    toaDo: String
  },
  created: async function () {},
  components: {
    // GMap,
    BanDo,
    ModalChonDiaChi
    // BanDoVNPT
  },
  computed: {
    getMarkers: function () {
      console.log('🚀 ~ file: ToaDo.vue:37 ~ this.markers.slice(0, 1):', this.markers.slice(0, 1))
      return this.markers.slice(0, 1)
    },
    radiusObj () {
      return this.radius
    },
    getChiTietDiaChi: function () {
      return {
        tinhThanh_id: this.tinh_id,
        quanHuyen_id: this.quan_id,
        phuongXa_id: this.phuong_id,
        duongPho_id: this.pho_id,
        ap_id: this.ap_id,
        khu_id: this.khu_id,
        dacDiem_id: this.dacdiem_id,
        soNha: this.sonha
      }
    }
  },
  watch: {
    nodes: function (val) {
      let map = this.$refs.mapcontrol
      map.clear()
      map.setNodes(this.nodes.slice(0))
      map.setLocationEx(null)
    }
  },
  methods: {
    focusMyElement: async function () {
      if (this.markers.length > 0) {
        this.center = {
          lat: this.markers[0].VIDO,
          lng: this.markers[0].KINHDO
        }
      }
      this.query = this.toaDo
    },
    xacnhan: async function () {
      this.$bvModal
        .msgBoxConfirm('Cập nhật tọa độ thuê bao vào danh bạ ?', {
          size: 'sm',
          okTitle: 'Đồng ý',
          cancelTitle: 'Không đồng ý'
        })
        .then(async (v) => {
          if (v) {
            try {
              this.loading(true)
              this.$emit('xacnhan', this.toado)
              this.$bvModal.hide(this.modalId)
            } catch (error) {
              this.$toast.error(error.messeage)
            } finally {
              this.loading(false)
            }
          }
        })
        .catch((err) => {})
    },
    printImage: async function () {
      this.loading(true)
      await html2canvas(document.querySelector('#element'), {
        useCORS: true
      }).then(function (canvas) {
        // document.body.appendChild(canvas);
        return Canvas2Image.saveAsPNG(canvas)
      })
      this.loading(false)
    },
    luuToaDoBanDo: function (nodes) {
      if (nodes != null) {
        let node = nodes[0]
        this.toado = node.lat + ',' + node.lng
      }
    },
    search: function () {
      if (this.checkToaDo()) {
        if (this.nodes.length == 2) this.nodes.splice(1, 1)
        var node = this.taoDuLieuNode('COT_2', this.query, '', 3)
        this.nodes.push(node)
      }
    },
    checkToaDo: function () {
      if (this.query == '') return false
      else {
        if (
          isNaN(parseFloat(this.query.split(',')[0])) ||
                isNaN(parseFloat(this.query.split(',')[1]))
        ) { return false }
      }
      return true
    },
    changetypeMap: function () {
      if (this.mapTypeId === 'ROADMAP') {
        this.mapTypeId = 'SATELLITE'
      } else {
        this.mapTypeId = 'ROADMAP'
      }
    },
    dblclickMap (event) {
      this.toado = event.lat + ';' + event.lng
    },
    timKiem: function () {
      try {
        this.loading(true)
        this.$refs.mapToaDo.searchLocation(this.query)
      } catch (error) {
        this.$root.toastError('Lỗi phát sinh: ' + error)
      } finally {
        this.loading(false)
      }
    },
    closeModalDiaChi: function (data) {
      this.isShowDiaChi = !this.isShowDiaChi
      if (data.isUpdated) {
        this.query = data.diaChi
        this.tinh_id = data.chiTietDiaChi.tinhThanh_id
        this.quan_id = data.chiTietDiaChi.quanHuyen_id
        this.phuong_id = data.chiTietDiaChi.phuongXa_id
        this.pho_id = data.chiTietDiaChi.duongPho_id
        this.ap_id = data.chiTietDiaChi.ap_id
        this.khu_id = data.chiTietDiaChi.khu_id
        this.dacdiem_id = data.chiTietDiaChi.dacDiem_id
        this.sonha = data.chiTietDiaChi.soNha
        this.timKiem()
      }
    }
  },
  async mounted () {
    // let vm = this
    // this.$refs.map.$mapPromise.then((map) => {
    //   vm.map = map
    // })
  },
  data: function () {
    return {
      toado: null,
      query: null,
      nodes: [],
      radius: [],
      center: {
        lat: 0,
        lng: 0
      },
      lat: -1,
      lng: -1,
      zoomDefault: 20,
      mapTypeId: 'ROADMAP',
      bankinhTK: 1000,
      map: null,
      tinhThanh_id: null,
      quanHuyen_id: null,
      phuongXa_id: null,
      duongPho_id: null,
      ap_id: null,
      khu_id: null,
      dacDiem_id: null,
      soNha: null,
      isShowDiaChi: false
    }
  }
}
</script>
<style>
div#popupTCTTTB___BV_modal_outer_ {
  z-index: 10 !important;
  margin-top: 100px;
}
.dashboard-header {
  z-index: 9 !important;
}
a.disabled {
  pointer-events: none;
  cursor: default;
  color: #6c757d !important;
}
</style>
