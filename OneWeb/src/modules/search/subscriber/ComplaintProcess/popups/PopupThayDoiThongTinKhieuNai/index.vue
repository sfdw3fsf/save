<template src="./template.html"></template>
<style src="./style.scss"></style>
<script>
import api from './api'
import breadcrumb from '@/components/breadcrumb'
import moment from 'moment'
import ModalNhanVienLoi from '@/modules/EXTS/KHIEUNAI/TiepNhanKN/modal/KetQuaLoi/NhanVienLoi'
import ModalDonViLoi from '@/modules/EXTS/KHIEUNAI/TiepNhanKN/modal/KetQuaLoi/DonViLoi'
export default {
  components: { breadcrumb, ModalNhanVienLoi, ModalDonViLoi },
  name: 'PopupThayDoiThongTinKhieuNai',
  props: {
    isPopup: {
      type: Boolean,
      default: false
    },
    inputData: {
      type: Object,
      default: () => {
        return { khieunai_id: 8055 }
      }
    }
  },
  mounted() {
    this.loadData()
  },
  data() {
    return {
      header: {
        title: 'THAY ĐỔI THÔNG TIN KHIẾU NẠI',
        list: []
      },
      TTTTKN: { DienThoaiLH: null, NgayBaoNhan: new Date(), NoiDungKn: '', GhiChu: '', NguyenNhan: '', NoiDungTL: '' },
      CuocCP: { Giam: 0, Tien: 0, Vat: 0 },
      CuocDT: { Giam: 0, Tien: 0, Vat: 0 },
      KetQuaLoi: { data: [], selected: null, fields: { text: 'noidung', value: 'ketquakn_id' } }
    }
  },
  watch: {
    'CuocCP.Giam': {
      handler: function (val, oldval) {
        this.CuocCP.Tien = Math.round(val / 1.1)
        this.CuocCP.Vat = val - this.CuocCP.Tien
      },
      deep: true
    },
    'CuocCP.Tien': {
      handler: function (val, oldval) {
        this.CuocCP.Vat = Math.round(val * 0.1)
        this.CuocCP.Giam = Math.round(val * 1.1)
      },
      deep: true
    },
    'CuocDT.Giam': {
      handler: function (val, oldval) {
        this.CuocDT.Tien = Math.round(val / 1.1)
        this.CuocDT.Vat = val - this.CuocDT.Tien
      },
      deep: true
    },
    'CuocDT.Tien': {
      handler: function (val, oldval) {
        this.CuocDT.Vat = Math.round(val * 0.1)
        this.CuocDT.Giam = Math.round(val * 1.1)
      },
      deep: true
    }
  },
  computed: {
    enableKetQuaLoiBnt() {
      return this.KetQuaLoi.selected === 7 || this.KetQuaLoi.selected === 9
    }
  },
  methods: {
    GetData(response) {
      console.log(response)
      if (response.data.error === 200 || response.data.error === '200') {
        return response.data.data
      } else {
        console.log(response.data.error_code)
      }
      return []
    },
    async loadData() {
      await this.getKetQuaLoiCBData()
      await this.getDSKhieuNai()
    },
    async getKetQuaLoiCBData() {
      this.KetQuaLoi.data = []
      this.KetQuaLoi.selected = null
      try {
        this.loading(true)
        this.KetQuaLoi.data = this.GetData(await api.lay_ketqua_kn_theo_loai(this.axios))
        if (this.KetQuaLoi.data.length > 0) {
          this.KetQuaLoi.selected = this.KetQuaLoi.data[0].ketquakn_id
        } else {
          this.$root.$toast.error('Chưa có dữ liệu kết quả lỗi. Liên hệ admin để xử lý !')
        }
      } catch (e) {
        console.log(e)
        this.$root.$toast.error('Load dữ liệu không thành công')
      } finally {
        this.loading(false)
      }
    },
    async getDSKhieuNai() {
      this.TTTTKN = { DienThoaiLH: null, NgayBaoNhan: new Date(), NoiDungKn: '', GhiChu: '', NguyenNhan: '', NoiDungTL: '' }
      this.CuocCP = { Giam: 0, Tien: 0, Vat: 0 }
      this.CuocDT = { Giam: 0, Tien: 0, Vat: 0 }
      try {
        this.loading(true)
        const data = this.GetData(await api.sp_lay_ds_khieunai_by_id(this.axios, { p_khieunai_id: this.inputData.khieunai_id }))
        if (data.length > 0) {
          this.TTTTKN.DienThoaiLH = data[0].dienthoai_lh
          this.TTTTKN.NoiDungKn = data[0].nd_khieunai
          this.TTTTKN.GhiChu = data[0].nguyennhan_kn
          this.TTTTKN.NguyenNhan = data[0].noidung_gq
          this.TTTTKN.NoiDungTL = data[0].ghichu_kn
          this.CuocCP.Tien = data[0].giamcuoc_cp
          this.CuocDT.Tien = data[0].giamcuoc_dt
          this.KetQuaLoi.selected = data[0].ketquakn_id
          this.TTTTKN.NgayBaoNhan = new Date(data[0].ngay_bn)
        }
      } catch (e) {
        console.log(e)
        this.$root.$toast.error('Load dữ liệu không thành công')
      } finally {
        this.loading(false)
      }
    },
    async KetQuaLoiBnt() {
      if (this.KetQuaLoi.selected === 7) {
        this.$refs.ModalNhanVienLoi.show(this.inputData.khieunai_id).then((result) => {
          console.log('ModalNhanVienLoi', result)
          if (result.ok) {
          } else {
          }
        })
      }
      if (this.KetQuaLoi.selected === 9) {
        this.$refs.ModalDonViLoi.show(this.inputData.khieunai_id).then((result) => {
          console.log('ModalDonViLoi', result)
          if (result.ok) {
          } else {
          }
        })
      }
    },
    async CapNhatBnt() {
      this.$refs.lblNDXuLy.focus()
      if (this.TTTTKN.DienThoaiLH === '') {
        this.$root.$toast.warning('Số điện thoại không được để trống')
        return
      }
      if (this.TTTTKN.NoiDungKn === '') {
        this.$root.$toast.warning('Nội dung không được để trống')
        return
      }
      if (this.KetQuaLoi.selected === null) {
        this.$root.$toast.warning('Hãy chọn kết quả lỗi!')
        this.$refs.KetQuaLoiCB.$el.focus()
        return
      }

      try {
        this.loading(true)
        const response = await api.thaydoi_ttkn_v3(this.axios, {
          vkhieunai_id: this.inputData.khieunai_id,
          vnhanvien_id: this.$root.token.getNhanVienID(),
          vdonvi_id: this.$root.token.getDonViID(),
          vketquakn_id: this.KetQuaLoi.selected,
          vghichu: this.TTTTKN.GhiChu,
          vdienthoai_lh: this.TTTTKN.DienThoaiLH,
          vnd_khieunai: this.TTTTKN.NoiDungKn,
          vnoidung_gq: this.TTTTKN.NoiDungTL,
          vnguyennhan_kn: this.TTTTKN.NguyenNhan,
          vgiamcuoc_dt: this.CuocDT.Tien,
          vvat_dt: this.CuocDT.Vat,
          vgiamcuoc_cp: this.CuocCP.Tien,
          vvat_cp: this.CuocCP.Vat,
          vngay_cn: moment().format('DD/MM/YYYY hh:mm:ss'),
          vnguoi_cn: this.$root.token.getUserName(),
          vmay_cn: await this.$root.token.getMachine(),
          vip_cn: await this.$root.token.getIP(),
          vngay_bn: moment(this.TTTTKN.NgayBaoNhan).format('DD/MM/YYYY hh:mm:ss')
        })
        if ((response.data.error === 200 || response.data.error === '200') && response.data.error_code === 'BSS-00000000') {
          this.$root.$toast.success('Cập nhật thành công')
          this.$emit('onSuccess')
          // this.getDSKhieuNai()
        } else {
          this.$root.$toast.error('Cập nhật không thành công')
        }
      } catch (e) {
        console.log(e)
        this.$root.$toast.error('Không thể cập nhật')
      } finally {
        this.loading(false)
      }
    }
  }
}
</script>
