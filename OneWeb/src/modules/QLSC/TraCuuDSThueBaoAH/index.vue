<template src="./index.template.html"></template>
<script>
import DateTimeLib from '@/utils/DateTimeLib'
import moment from 'moment'
export default {
  data () {
    return {
      dsThueBao: [],
      dtpTuNgay: null,
      dtpDenNgay: null,
      txtMaSC: null,
      cboDichVu: -1,
      vcboDichVu: [],
      cboTrangThaiSC: -1,
      vcboTrangThaiSC: []
    }
  },
  watch: {
    dsThueBao (newVal) {

    }
  },
  async mounted () {
    this.dtpDenNgay = DateTimeLib.toDateDisplayWithFormat(new Date(), 'YYYY-MM-DD')
    var _dtpTuNgay = moment(this.dtpDenNgay).add(-1, 'M')
    this.dtpTuNgay = DateTimeLib.toDateDisplayWithFormat(_dtpTuNgay, 'YYYY-MM-DD')
    this.dsThueBao = []
    this.vcboDichVu = []
    this.vcboTrangThaiSC = []
    var HT_DichVuVT_Combobox = await this.HT_DichVuVT_Combobox()
    console.log('🚀 ~ file: index.vue ~ line 30 ~ mounted ~ HT_DichVuVT_Combobox', HT_DichVuVT_Combobox)
    if (HT_DichVuVT_Combobox.length > 0) {
      HT_DichVuVT_Combobox.forEach(element => {
        this.vcboDichVu.push({
          value: element.DICHVUVT_ID,
          text: element.TEN_DVVT
        })
      })
      this.cboDichVu = 13
    }
    var NAP_DS_TRANGTHAI_SC = await this.NAP_DS_TRANGTHAI_SC()
    console.log('🚀 ~ file: index.vue ~ line 32 ~ mounted ~ NAP_DS_TRANGTHAI_SC', NAP_DS_TRANGTHAI_SC)
    if (NAP_DS_TRANGTHAI_SC.length > 0) {
      NAP_DS_TRANGTHAI_SC.forEach(element => {
        this.vcboTrangThaiSC.push({
          value: element.TTSC_ID,
          text: element.TRANGTHAI_SC
        })
      })
      this.cboTrangThaiSC = 6
    }
  },
  methods: {
    btnXuatExcel () {
      try {
        let exportProperties = {
          dataSource: this.dsThueBao
        }
        this.$refs.dsThueBao.excelExport(exportProperties)
      } catch (e) {
        this.$root.toastError('Có lỗi khi xuất excel ' + e.toString())
      }
    },
    async btnLamMoi_Click () {
      try {
        var _ma_tinh = 'HPG'
        var _ma_sc = this.txtMaSC
        var _tu_ngay = this.dtpTuNgay
        console.log('🚀 ~ file: index.vue ~ line 54 ~ btnLamMoi_Click ~ _tu_ngay', _tu_ngay)
        var _den_ngay = this.dtpDenNgay
        console.log('🚀 ~ file: index.vue ~ line 56 ~ btnLamMoi_Click ~ _den_ngay', _den_ngay)
        var _dichvuvt_id = this.cboDichVu
        console.log('🚀 ~ file: index.vue ~ line 58 ~ btnLamMoi_Click ~ _dichvuvt_id', _dichvuvt_id)
        var _ttsc_id = this.cboTrangThaiSC
        console.log('🚀 ~ file: index.vue ~ line 60 ~ btnLamMoi_Click ~ _ttsc_id', _ttsc_id)
        var _list_matb = await this.Post_TraCuuDSTB_CNTT_AnhHuong(_ma_tinh, _ma_sc, _tu_ngay, _den_ngay, _dichvuvt_id, _ttsc_id)
        console.log('🚀 ~ file: index.vue ~ line 62 ~ btnLamMoi_Click ~ _list_matb', _list_matb)
        if (_list_matb.length > 0) {
          var dtDSTB = await this.SP_LAY_DSTB_THEO_JSON(_list_matb)
          console.log('🚀 ~ file: index.vue ~ line 64 ~ btnLamMoi_Click ~ dtDSTB', dtDSTB)
          this.dsThueBao = dtDSTB
        }
      } catch (error) {

      }
    },
    async SP_LAY_DSTB_THEO_JSON (_list_matb) {
      try {
        let rs = await this.$root.context.post('/web-suco/tracuu/thue-bao-anh-huong/sp-lay-dstb-theo-json', _list_matb)
        return rs.data
      } catch (error) {
        return []
      }
    },
    async Post_TraCuuDSTB_CNTT_AnhHuong (ma_tinh, ma_sc, tu_ngay, den_ngay, dichvuvt_id, ttsc_id) {
      try {
        // gnms.Post_TraCuuDSTB_CNTT_AnhHuong(_ma_tinh, _ma_sc, _tu_ngay, _den_ngay, _dichvuvt_id, _ttsc_id)
        var data = {ma_tinh, ma_sc, tu_ngay, den_ngay, dichvuvt_id, ttsc_id}
        let rs = await this.$root.context.post('/web-suco/tracuu/thue-bao-anh-huong/post-tracuudstb-cntt-anhhuong', data)
        console.log('🚀 ~ file: index.vue ~ line 90 ~ Post_TraCuuDSTB_CNTT_AnhHuong ~ rs', rs)
        var _list_matb = []
        _list_matb.push({ MA_TB: '3853020'})
        _list_matb.push({ MA_TB: 'TSL004138'})
        _list_matb.push({ MA_TB: '919583088'})
        return _list_matb
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 97 ~ Post_TraCuuDSTB_CNTT_AnhHuong ~ error', error)
        return []
      }
    },
    async HT_DichVuVT_Combobox () {
      try {
        let dvvt_id = '15,14,16,13'
        let rs = await this.$root.context.get('/web-suco/tracuu/thue-bao-anh-huong/ht-dichvuvt-combobox?dichvuvt_id=' + dvvt_id)
        return rs.data
      } catch (error) {
        return []
      }
    },
    async NAP_DS_TRANGTHAI_SC () {
      try {
        let rs = await this.$root.context.get('/web-suco/tracuu/thue-bao-anh-huong/nap-ds-trangthai-sc')
        return rs.data
      } catch (error) {
        return []
      }
    }
  }
}
</script>
