<template src="./GiaoPhieuHoTro.html"></template>
<style scoped src="./GiaoPhieuHoTro.scss"></style>
<script>
import Vue from 'vue'
import { DialogPlugin } from '@syncfusion/ej2-vue-popups'
Vue.use(DialogPlugin)
export default {
  components: {},
  name: 'GiaoPhieuHoTro',
  mounted() {},
  watch: {
    c_HuongGiao: {
      handler: async function (data) {
        if (data) {
          for (const i of this.listHuongGiao) {
            if (i.HUONGGIAO_ID == data) {
              await this.getDsDonViXuLy(i.LUONG_ID)
              await this.getDonViTheoLuongHopDong(this.infoPhieuHoTro.HTKH_ID, i.LUONG_ID)
              return
            }
          }
        }
      }
    },
    c_trangThai: async function (data) {
      // console.log(' khanh:', data)
      if (data == 1) this.btnGiaoPhieu = true
      else this.btnGiaoPhieu = false
      await this.getDSPhieuHT()
    }
  },
  computed: {},
  created() {},
  props: { pQuyTrinhID: Number, pTTHTID: Number, pTTPHID: Number, pMa_TB: String },
  data() {
    return {
      btnGiaoPhieu: true,
      dialog: {
        target: '.main-wrapper',
        width: '85%',
        animationSettings: { effect: 'Zoom' },
        position: { X: 'center', Y: 'top' }
      },
      listGiaoPhieuBaoHanh: [],
      listTrangThai: [
        { text: 'Đã giao', value: 2 },
        { text: 'Chưa giao', value: 1 }
      ],
      c_trangThai: '',
      listHuongGiao: [],
      c_HuongGiao: '',
      c_maThueBao: '',
      c_DonViXuLy: '',
      listDonViXuLy: [],
      c_NoiDungGiao: '',
      c_Serial: '',
      c_NgayGiao: new Date(),
      dsThueBaoKhieuNaiChuaGiao: [],
      // pTTHTID: 2,
      // pTTPHID: 1,
      pTTHT_TT: '',
      infoPhieuHoTro: {
        HTKH_ID: '',
        PHIEUHT_ID: '',
        SO_CV: ''
      },
      temp_dsThueBaoKhieuNaiChuaGiao: []
    }
  },
  methods: {
    closePopup() {},
    openDialog() {
      this.$refs.popupGiaoPhieuHoTro.show()
      this.firstStart()
    },
    async firstStart() {
      this.c_maThueBao = this.pMa_TB
      this.c_trangThai = this.pTTHTID
      await this.getDSPhieuHT()
    },
    async timKiem() {
      await this.getDSPhieuHT()
    },
    async giaoPhieu() {
      try {
        if (!this.c_DonViXuLy || this.c_DonViXuLy == 0) {
          this.$toast.error('Bạn chưa chọn Đơn vị xử lý!')
          return
        }
        this.loading(true)
        let data = {
          pHTKHID: this.infoPhieuHoTro.HTKH_ID,
          pPHIEUHTID: this.infoPhieuHoTro.PHIEUHT_ID,
          pTTHTID: this.pTTHTID,
          pSoCV: this.infoPhieuHoTro.SO_CV,
          pNoiDung: this.c_NoiDungGiao,
          pDonviNhanId: this.c_DonViXuLy
        }
        var rs = await this.$root.context.post('web-cskh/api/xu-ly-thue-bao/giaophieu-hotro', data)
        if (rs.error_code === 'BSS-00000000') {
          this.$toast.success('Giao phiếu thành công!')
        }

        await this.timKiem()
      } catch (er) {
        this.$toast.error(er.response.data.message)
      } finally {
        this.loading(false)
      }
    },
    async huyGiao() {
      this.loading(true)
      try {
        let data = {
          pHTKHID: this.infoPhieuHoTro.HTKH_ID,
          pPHIEUHTID: this.infoPhieuHoTro.PHIEUHT_ID,
          pTTHTID: this.c_trangThai,
          pSoCV: this.infoPhieuHoTro.SO_CV,
          pNoiDung: this.c_NoiDungGiao,
          pDonviNhanId: this.c_DonViXuLy
        }
        var rs = await this.$root.context.post('web-cskh/api/xu-ly-thue-bao/huy-giaophieu-hotro', data)
        if (rs.error_code === 'BSS-00000000') {
          this.$toast.success('Hủy giao phiếu thành công!')
        }

        await this.timKiem()
      } catch (errrrrrr) {
      } finally {
        this.loading(false)
      }
    },
    async getDsHuongGiao(_pHuongGiaoId, _pQuyTrinhId) {
      try {
        let data = {
          pHuongGiaoId: _pHuongGiaoId,
          pQuyTrinhId: _pQuyTrinhId
        }
        var rs = await this.$root.context.post('web-cskh/api/xu-ly-thue-bao/lay-huonggiao-hotro-tieptheo', data)
        this.listHuongGiao = rs.data
        if (this.listHuongGiao.length > 0) this.c_HuongGiao = this.listHuongGiao[0].HUONGGIAO_ID
        //console.log('listHuongGiao = ', rs)
      } catch (errrrrrr) {}
    },
    async getDonViTheoLuongHopDong(pHTKHID, pLuongID) {
      try {
        var rs = await this.$root.context.post(`web-cskh/api/xu-ly-thue-bao/lay-danhsach-donvi-theo-htkh-luong?pHTKHID=${pHTKHID}&pLuongID=${pLuongID}`)
        if (rs.data.length > 0) {
          this.c_DonViXuLy = rs.data[0].DONVI_ID
        }
        console.log('getDonViTheoLuongHopDong = ', rs)
      } catch (errrrrrr) {}
    },
    async getDsDonViXuLy(pLuongID) {
      try {
        var rs = await this.$root.context.post(`web-cskh/api/xu-ly-thue-bao/lay-danhsach-donvi-theo-luong/${pLuongID}`)
        this.listDonViXuLy = rs.data

        console.log('listDonViXuLy = ', rs)
      } catch (errrrrrr) {
        console.log('errrrrrr = ', errrrrrr.response.data)
      }
    },
    closePopup() {
      this.c_maThueBao = null
      this.temp_dsThueBaoKhieuNaiChuaGiao = []
      this.$emit('closePopup')
    },
    async getDSPhieuHT() {
      try {
        this.loading(true)
        let data = {
          pTTHTID: this.pTTHTID,
          pLoaiPhieu: this.c_trangThai,
          pQuyTrinhID: this.pQuyTrinhID
        }
        var rs = await this.$root.context.post('web-cskh/api/xu-ly-thue-bao/lay-danhsach-phieu-hotro-theo-trangthai', data)

        this.dsThueBaoKhieuNaiChuaGiao = rs.data
        if (this.c_maThueBao !== null) {
          this.temp_dsThueBaoKhieuNaiChuaGiao = this.dsThueBaoKhieuNaiChuaGiao.filter((item) => item.MA_TB === this.c_maThueBao)
        } else {
          this.temp_dsThueBaoKhieuNaiChuaGiao = this.dsThueBaoKhieuNaiChuaGiao.filter((item) => item.MA_TB === this.c_maThueBao)
        }
      } catch (errrrrrr) {
        console.log(errrrrrr.response.data)
      } finally {
        this.loading(false)
      }
    },
    async gridDsThueBaoKhieuNaiChuaGiao_rowSelected(data) {
      if (data) {
        console.log('gridDsThueBaoKhieuNaiChuaGiao_rowSelected = ', data)
        this.infoPhieuHoTro.HTKH_ID = data.HTKH_ID
        this.infoPhieuHoTro.PHIEUHT_ID = data.PHIEUHT_ID
        this.infoPhieuHoTro.SO_CV = data.SO_CV
        await this.getDsHuongGiao(data.HUONGGIAO_ID, this.pQuyTrinhID)
      }
    }
  }
}
</script>
