<template src="./index.template.html"></template>
<script>
import { mapActions, mapState, mapGetters } from 'vuex'
import { getterName, actionName, statePropertyName } from './store'

import Vue from 'vue'
import EventBus from '@/utils/eventBus'
// import ModalQLCapExt from '@/modules/CABMAN/BanDoMangCap/modal/QLCapExt'
// import ModalTracnghiem from './modal/TracNghiemExt'
// import ModalXemTruocKhiIn from './modal/XemTruocKhiIn'
// import ModalInNhanh_XemTruocKhiIn from './modal/InNhanh_XemTruocKhiIn'

const leftPaneContent = Vue.component('leftPaneContent', require('./leftPane').default)
const rightPaneContent = Vue.component('rightPaneContent', require('./rightPane2').default)

const ModalQLCapExt = () => import('@/modules/CABMAN/BanDoMangCap/modal/QLCapExt2')
const ModalTracnghiem = () => import('./modal/TracNghiemExt')
const ModalXemTruocKhiIn = () => import('./modal/XemTruocKhiIn')
const ModalInNhanh_XemTruocKhiIn = () => import('./modal/InNhanh_XemTruocKhiIn')

export default {
  components: { leftPaneContent, rightPaneContent, ModalQLCapExt, ModalTracnghiem, ModalXemTruocKhiIn, ModalInNhanh_XemTruocKhiIn },
  name: 'BanDoMangCap',
  computed: {
    ...mapState('cabman/BanDoMangCap', statePropertyName),
    ...mapState('cabman', ['BanDoMangCap']),
    ...mapGetters('cabman/BanDoMangCap', getterName),
    getKyHieuCapGoc () {
      if (this.capgoc.KYHIEU !== null & this.capgoc.KYHIEU !== undefined) return 'Cáp gốc: ' + this.capgoc.KYHIEU
      else return 'Cáp gốc: <chưa chọn>'
    }
  },
  data () {
    return {
      leftPaneContent: function () {
        return { template: leftPaneContent }
      },
      rightPaneContent: function () {
        return { template: rightPaneContent }
      },
      modalShow: false
    }
  },
  methods: {
    ...mapActions('cabman/BanDoMangCap', actionName),
    GIANDO_ZOOMIN () {
      if (this.isEmpty(this.capgoc)) return
      EventBus.$emit('GIANDO_ZOOMIN', 'GIANDO_ZOOMIN')
    },
    GIANDO_ZOOMOUT () {
      if (this.isEmpty(this.capgoc)) return
      EventBus.$emit('GIANDO_ZOOMOUT', 'GIANDO_ZOOMOUT')
    },
    huongDan () {
      this.$alert('Tài liệu đang được cập nhật', 'Thông báo', {
        confirmButtonText: 'Đóng',
        callback: action => {

        }
      })
    },
    ModalInNhanh_XemTruocKhiIn_Close () {
      if (this.isEmpty(this.capgoc)) return
      EventBus.$emit('NAP_GIANDO_V2', 'NAP_GIANDO_V2')
    },
    isEmpty (inputObject) {
      return Object.keys(inputObject).length === 0
    },
    taoCapGoc () {
      if (this.ds_tram.length === 0) return
      var data = {
        cap_id: null,
        capgoc: Object, // this.capgoc,
        donvi: this.donvi, // Trạm thiết bị
        donvi_td_id: null, // Đơn vị truyền dẫn
        ketcuoi_n_id: null,
        ketcuoi_d_id: null,
        kieucap_id: 0, // Cáp thuê bao
        toql: this.toql
      }
      this.$refs.ModalQLCapGoc.taoCap(data).then((result) => {
        if (result.ok) {
          EventBus.$emit('NAP_DS_CAP', 'NAP_DS_CAP')
        }
      })
    },
    async napGianDo () {
      try {
        let rs = await this.$root.context.get('/web-cabman/ban-do-mang-cap/clearAllCaches')
        console.log('🚀 ~ file: index.vue:90 ~ napGianDo ~ rs:', rs)
      } catch (error) {
        console.log('🚀 ~ file: index.vue:91 ~ napGianDo ~ error:', error)
      }
      if (this.isEmpty(this.capgoc)) return
      EventBus.$emit('NAP_GIANDO_V2', 'NAP_GIANDO_V2')
    },
    inGianDo () {
      // EventBus.$emit('EXPORT_GIANDO', 'EXPORT_GIANDO')
      // return
      try {
        var tracnghiem = {
          title: 'Chọn chế độ in',
          caption: 'Chọn 1 chế độ bên dưới',
          options: ['In kèm khung kỹ thuật', 'In nhanh(Độ phân giải cao)', 'In dạng danh sách'],
          data: null
        }
        this.$refs.ModalTracnghiem.showWithSetChecked(tracnghiem, 0)
          .then(result => {
            if (result.ok) {
              if (result.option === 0) {
                this.$bvModal.show('ModalXemTruocKhiIn')
              } else if (result.option === 1) {
                this.$bvModal.show('ModalInNhanh_XemTruocKhiIn')
              } else {
                this.HIENTHI_GIANDO_TREELIST()
              }
            } else {
              console.log('Cancel')
            }
          })
      } catch (error) {
        this.$root.showLoading(false)
        console.log('🚀 ~ file: index.vue ~ line 335 ~ dauNoiBenTrong ~ error', error)
      }
    },
    HIENTHI_GIANDO_TREELIST () {
      EventBus.$emit('HIENTHI_GIANDO_TREELIST', this.getKyHieuCapGoc)
    },
    getThongTinMap: async function () {
      try {
        this.$root.showLoading(true)
        var rs = await this.$root.context.get('/web-cabman/bando_matdo/thongtin_bando')
        if (rs.data != null && rs.data.length > 0) {
          this.setMap({
            lat: parseFloat(rs.data[0].C_LATITUS),
            lng: parseFloat(rs.data[0].C_LONGTITUS),
            zoom: rs.data[0].DEFAULT_ZOOM,
            mapTypeId: 'roadmap',
            cluster_map_google: Number(rs.data[0].CLUSTER_MAP_GOOGLE) === 1
          })
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue:145 ~ error:', error)
      } finally {
        this.$root.showLoading(false)
      }
    },
    getThongTinGianDo: async function () {
      try {
        this.$root.showLoading(true)
        var rs = await this.$root.context.post(
          '/web-cabman/ban-do-mang-cap/load_giando_config'
        )
        console.log('🚀 ~ file: index.vue:152 ~ rs:', rs)
        if (rs.data != null && rs.data.length > 0) {
          this.setState({key: 'giando_config', value: rs.data[0]})
        } else {
          this.setState({key: 'giando_config', value: null})
        }
      } catch (error) {
        this.setState({key: 'giando_config', value: null})
      } finally {
        this.$root.showLoading(false)
      }
    }
  },
  watch: {
  },
  async created () {
    this.clearState()
    this.createApiManager()
    this.set_TabPageIndex(0)
    await this.getThongTinMap()
    await this.getThongTinGianDo()
    EventBus.$on('NAPLAIGIANDO', this.napGianDo)
  },
  async mounted () {

  }
}
</script>
<style scoped src="./index.scss">
</style>
