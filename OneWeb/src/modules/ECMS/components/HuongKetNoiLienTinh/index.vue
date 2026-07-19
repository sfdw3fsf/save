<template src="./index.html"></template>
<script>
import breadcrumb from '@/components/breadcrumb'
import {actionName, statePropertyName} from '../../store/HuongKetNoiLienTinh'
import {mapActions, mapState} from 'vuex'
import BssRequiredMarker from '@/components/BssRequiredMarker'
import {maxLength, numeric, required} from 'vuelidate/lib/validators'

export default {
  components: {breadcrumb, BssRequiredMarker},
  name: 'HknNoiTinh',
  computed: {
    ...mapState('ecms/hknNoiTinh', statePropertyName),
    loaiKetNoi () {
      return !this.loaiKnId ? null : this.loaiKn.filter(item => item.LOAIKN_ID === this.loaiKnId)[0].LOAI_KN
    },
    tenHuongKetNoi () {
      return !this.thietBiId ? null : (this.thietBiKn.filter(item => item.KENH_ID === this.thietBiId)[0].TEN_KENH)
    }

  },
  watch: {
    'vungId': async function (val) {
      if (this.loaiKn && this.thietBiKn) {
        this.thietBiKn =  this.thietBiFirst.filter(item =>{return ( item.DONVI_QL_ID === this.vungId && item.LOAIKN_ID === this.loaiKnId )} )
        if (this.thietBiKn.length > 0) {
          this.thietBiId = this.thietBiKn[0].KENH_ID
        }
      }
    }, 'loaiKnId': async function (val) {
      if (this.loaiKn && this.thietBiKn) {
        this.thietBiKn =  this.thietBiFirst.filter(item =>{return ( item.DONVI_QL_ID === this.vungId && item.LOAIKN_ID === this.loaiKnId )} )
        if (this.thietBiKn.length > 0) {
          this.thietBiId = this.thietBiKn[0].KENH_ID
        }
      }
    }, 'danhSachHkn': async function (val) {
      if (val.length === 0) {
        this.isDisableBtnXoa = true
        this.thietBiId = null
      }
    }

  },
  methods: {
    ...mapActions('ecms/hknNoiTinh', actionName),
    clearForm () {
      this.$refs.table.grid_RecordClick(-1,null);

      this.isCreated = true
      this.vungId = ''
      this.loaiKnId = ''
      this.thietBiId = ''
      this.$v.$reset()
      this.setDisableBtn(true, false, false, true)
    },
    async cancelForm () {
      this.$refs.table.grid_RecordClick(0, null)
      this.isCreated = true
      this.clearForm()
      if (this.isDisableBtnNhapMoi) {
        let itemFirstOfPage = this.$refs.table._data.currentPageIndex * 10
      } else {
      }
      this.setDisableBtn(false, false, false, false)
      location.reload()
    },
    async SaveAs () {
      this.setDisableBtn(false, false, false, this.isDisableBtnXoa)
      if (this.isCreated) {
        await this.createHkn()
      } else {
        await this.updateHkn()
      }
    },
    validateData (data) {
      let errorMessage = []
      window.mydata = data
      this.$v.$touch()

      if (!data.thietBi) {
        errorMessage.push('Cổng kết nối không tồn tại')
      }

      if (!data.loaiknId) {
        errorMessage.push('Loại kết nối không tồn tại')
      }

      if (errorMessage.length > 0) {
        this.$toast.error(errorMessage.join('\n'))
        return true
      } else {
        return false
      }
    },
    cancelFunc: function () {
      this.setDisableBtn(false, true, true, false)
      if (!this.isDisableBtnNhapMoi) {
      }
    },
    async createHkn () {
      if (!this.thietBiId) {
        this.$toast.error('Trường cổng kết nối bắt buộc nhập dữ liệu.')
        return
      }
      let objAdd = {
        dslamId: this.thietBiId.split(';')[0],
        kieu: 2,
        loaiknId: this.loaiKnId,
        tenHuong: this.tenHuongKetNoi,
        thietBi: this.thietBiId
      }
      if (this.validateData(objAdd)) {
        return true
      }
      try {
        let response = await this.themMoiHknAction(objAdd)
        console.log('response', response)
        if (response.error_code === 'BSS-00000000') {
          this.$toast.success('Thêm mới thành công!')
          let objDs = {
            vKieu: 2,
            vVungId: this.vungId
          }
          await this.layDsHknNoiTinhAction(objDs)
        } else {
          this.$toast.error('Có lỗi xảy ra')
        }
      } catch (err) {
        if (err.response) {
          let message = err.response.data.message
          let message_detail = err.response.data.message_detail
          if (message_detail !== null) {
            this.$toast.error(message_detail)
          } else if (message !== null) {
            this.$toast.error(message)
          }
        }
      } finally {

      }
    }, async updateHkn () {
      if (!this.thietBiId) {
        this.$toast.error('Trường cổng kết nối bắt buộc nhập dữ liệu.')
        return
      }
      if (!this.huongKnId) {
        this.$toast.error('Trường hướng kết nối bắt buộc nhập dữ liệu.')
        return
      }
      try {
        let objUpd = {
          huongknId: this.huongKnId,
          dslamId: this.thietBiId.split(';')[0],
          kieu: 2,
          loaiknId: this.loaiKnId,
          tenHuong: this.tenHuongKetNoi,
          thietBi: this.thietBiId
        }
        if (this.validateData(objUpd)) {
          return true
        }
        let response = await this.capNhatHknAction(objUpd)
        if (response.error_code === 'BSS-00000000') {
          this.$toast.success('Cập nhật thành công!')
          let objDs = {
            vKieu: 2,
            vVungId: this.vungId
          }
          await this.layDsHknNoiTinhAction(objDs)
        } else {
          this.$toast.error('Có lỗi xảy ra')
        }
      } catch (err) {
        window.myerror = err
        let message = err.response.data.message
        if (message !== null) {
          this.$toast.error(message)
        }
      } finally {

      }
    },
    async grid_selectedRowChanged (dataItem) {
      try {
        if (dataItem) {
          this.setDisableBtn(false, false, false, false)
          this.isCreated = false
          this.loaiKnId = dataItem.LOAIKN_ID
          this.huongKnId = dataItem.HUONGKN_ID
          this.thietBiId = dataItem.THIETBI
          this.vungId = dataItem.DONVI_QL_ID
        }
      } catch (err) {
        console.log(err)
      } finally {
      }
    },
    setDisableBtn: function (isNhapmoi, isGhilai, isHuy, isXoa) {
      this.isDisableBtnNhapMoi = isNhapmoi
      this.isDisableBtnGhiLai = isGhilai
      this.isDisableBtnHuy = isHuy
      this.isDisableBtnXoa = isXoa
    },
    xoaHknNoiTinh: function () {
      if (this.$refs.table.currentSelectedRow === -1) {
        this.$toast.error('Bạn chưa chọn hướng kết nối nào để xóa.')
        return
      }
      if (!this.huongKnId) {
        this.$toast.error('Bạn chưa chọn hướng kết nối nào để xóa.')
        return
      }
      if (!this.isDisableBtnXoa) {
        this.modalXoa = !this.modalXoa
      }
    },
    async xacNhanXoaHknNoiTinh () {
      try {

        var rs = await this.xoaHknAction(this.huongKnId)
        this.setDisableBtn(false, true, true, true)
        if (rs.data.result == true) {
          this.$toast.success('Xóa thành công')
          let objDs = {
            vKieu: 2,
            vVungId: this.vungId
          }
          await this.layDsHknNoiTinhAction(objDs)
        } else {
          this.$toast.error('Xóa thất bại!')
        }
      } catch (err) {
        let message = err.response.data.message
        let message_detail = err.response.data.message_detail
        if (message_detail !== null) {
          this.$toast.error(message_detail)
        } else if (message !== null) {
          this.$toast.error(message)
        }
      } finally {
        this.loading(false)
      }
    }
  },
  async mounted () {
    try {
      this.loading(true)
      await this.layDMHknNoiTinhAction()
      this.thietBiKn = this.danhMucHkn.THIETBI_KN
      this.thietBiFirst = this.danhMucHkn.THIETBI_KN
      this.loaiKn = this.danhMucHkn.LOAI_KN
      this.vungKn = this.danhMucHkn.VUNG_KN
      this.kieuKn = this.danhMucHkn.KIEU_KN
      if (this.vungKn) {
        this.vungId = this.vungKn[0].DONVI_ID
      }
      if (this.loaiKn) {
        this.loaiKnId = this.loaiKn[0].LOAIKN_ID
      }
      let objDs = {
        vKieu: 2,
        vVungId: this.vungId
      }
      await this.layDsHknNoiTinhAction(objDs)
      this.dsHknNoiTinh = this.danhSachHkn
    } catch (err) {
      console.log(err)
    } finally {
      this.loading(false)
    }
  },
  data () {
    return {
      isDisableBtnNhapMoi: false,
      isDisableBtnGhiLai: false,
      isDisableBtnHuy: false,
      isDisableBtnXoa: false,
      thietBiKn: [],
      loaiKn: [],
      vungKn: [],
      kieuKn: [],
      thietBiFirst: [],
      dsHknNoiTinh: '',
      thietBiId: '',
      loaiKnId: null,
      vungId: '',
      kieuId: '',
      huongKnId: '',
      loaiKnItem: '',
      isCreated: true,
      modalXoa: false
    }
  }, validations: {
    vungId: {
      required,
    }, portBd: {
      required,
    }, thietBiId: {
      required
    }
  },
  created () {
    this.clearState()
    this.createApiManager()
  },
  destroyed () {
    this.clearState()
  },
}
</script>

<style scoped src="./index.scss">
</style>

