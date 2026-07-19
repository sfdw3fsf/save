<template src="./index.html"></template>
<script>
import breadcrumb from '@/components/breadcrumb'
import {actionName, statePropertyName} from '../../store/HuongKetNoiBras'
import {mapActions, mapState} from 'vuex'
import BssRequiredMarker from '@/components/BssRequiredMarker'
import {maxLength, numeric, required} from 'vuelidate/lib/validators'
import BssErrorMarker from '@/components/BssErrorMarker'


export default {
    components: {breadcrumb,BssRequiredMarker,BssErrorMarker},
    name: "HknBras",
    computed: {
      ...mapState("ecms/hknBras", statePropertyName),
      loaiKetNoi () {
        return !this.loaiKnId ? '' : this.loaiKn.filter(item => item.LOAIKN_ID === this.loaiKnId)[0].LOAI_KN
      },
      tenHuongKetNoi () {
        return !this.thietBiId ? '' : (this.thietBiKn.filter(item => item.KENH_ID === this.thietBiId)[0].TEN_KENH)
      }

    },
  watch:{
      'vungId':async function (val){
        if(this.loaiKn && this.thietBiKn){
          this.thietBiKn =  this.thietBiFirst.filter(item =>  {
            return (item.DONVI_QL_ID == this.vungId && item.LOAIKN_ID == this.loaiKnId ) }  )
          console.log("x",this.thietBiKn  )
          if(this.thietBiKn.length >0){
          //  this.thietBiId = this.thietBiKn[0].KENH_ID
          }else {
           // this.thietBiId = ''
          }
        }
      },'loaiKnId':async function (val){
      if(this.loaiKn && this.thietBiKn){
        this.thietBiFirst = this.danhMucHkn.THIETBI_KN
        this.thietBiKn =  this.thietBiFirst.filter(item =>  {
          return  (item.DONVI_QL_ID == this.vungId && item.LOAIKN_ID == this.loaiKnId ) }  )
        console.log("x",this.thietBiKn  )

        if(this.thietBiKn.length >0){
        //  this.thietBiId = this.thietBiKn[0].KENH_ID
        }else {
        //  this.thietBiId = ''
        }
      }
      },'dsHknNoiTinh':async function (val){
      if(val.length === 0){
        this.isDisableBtnXoa = true
        this.thietBiId = ''
      }
    }

  },
    methods: {
      ...mapActions("ecms/hknBras", actionName),
      clearForm() {
        this.$refs['table'].grid.clearSelection()
        this.isCreated = true
        this.vungId = ''
        this.loaiKnId = ''
        this.thietBiId = ''
        this.$v.$reset()
        this.setDisableBtn(true, false, false, true)
      }, cancelForm() {
        this.isCreated = true
        if(this.isDisableBtnNhapMoi){
          let itemFirstOfPage = this.$refs.table._data.currentPageIndex * 10
          this.$refs['table'].grid.selectRow(0)
        }else {
          let dataItem = this.$refs['table'].grid.getSelectedRecords()[0]
          if(dataItem){
            this.loaiKnId = dataItem.LOAIKN_ID
            this.huongKnId = dataItem.HUONGKN_ID
            this.thietBiId = dataItem.THIETBI
            this.vungId = dataItem.DONVI_QL_ID
          }

        }
        this.setDisableBtn(false, false, false, false)
      }, async SaveAs() {
        this.setDisableBtn(false, false, false, this.isDisableBtnXoa)
        if (this.isCreated) {
          await this.createHkn()
        } else {
          await this.updateHkn()
        }
      }, validateData() {
        let errorMessage = []
        this.$v.$touch();

        if(!this.$v.vungId.required){
          errorMessage.push("Trường vùng kết nối bắt buộc nhập dữ liệu.");
        }

        if (!this.loaiKnId) {
          errorMessage.push("Trường loại kết nối bắt buộc nhập dữ liệu.");
        }

        if (!this.$v.thietBiId.required) {
          errorMessage.push("Trường cổng kết nối bắt buộc nhập dữ liệu.");
        }

        if (errorMessage.length > 0) {
          this.$toast.error(errorMessage.join('\n'))
          return true
        } else {
          return false
        }
      }, cancelFunc: function () {
        this.setDisableBtn(false, true, true, false)
        if (!this.isDisableBtnNhapMoi) {
        }
      },
      async createHkn() {
        if (this.validateData()) {
          return
        }
        let objAdd = {
          dslamId: this.thietBiId.split(';')[0],
          kieu: 4,
          loaiknId: this.loaiKnId,
          tenHuong: this.tenHuongKetNoi,
          thietBi: this.thietBiId
        }

        try {
          let response = await this.themMoiHknAction(objAdd)
          if (response.error_code === 'BSS-00000000') {
            this.$toast.success('Thêm mới thành công.')
            await this.layDanhSachHkn()
            this.dsHknNoiTinh.forEach((item,index,array) => {
              if(item.HUONGKN_ID === response.data.HUONGKN_ID){


              let out = array.splice(index, 1) [0];
                this.dsHknNoiTinh.splice(0,0,item)
                item.STT = 1
              }else {
                item.STT = index + 1
              }
            })
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
      }, async updateHkn() {
        if (!this.thietBiId) {
          this.$toast.error("Trường cổng kết nối bắt buộc nhập dữ liệu.");
          return
        }
        if (!this.huongKnId) {
          this.$toast.error("Trường hướng kết nối bắt buộc nhập dữ liệu.");
          return
        }
        try {
          let objUpd = {
            huongknId: this.huongKnId,
            dslamId: this.thietBiId.split(';')[0],
            kieu: 4,
            loaiknId: this.loaiKnId,
            tenHuong: this.tenHuongKetNoi,
            thietBi: this.thietBiId
          }
          if (this.validateData(objUpd)) {
            return true
          }
          let response = await this.capNhatHknAction(objUpd)
          if (response.error_code === 'BSS-00000000') {
            this.$toast.success('Cập nhật thành công.')
            let objDs = {
              vKieu: 4,
              vVungId: this.vungId
            }
            await this.layDanhSachHkn()
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
      }, async grid_selectedRowChanged(dataItem) {
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
      }, async layDanhSachHkn() {
        try {
          let objDs = {
            vKieu: 4,
            vVungId: this.vungId
          }
          let response = await this.$root.context.post('/web-ecms/danhmuc/huongketnoi/danhsach',objDs)

          if (response.error_code === 'BSS-00000000' && response.data != null) {
            this.dsHknNoiTinh = response.data
            this.dsHknNoiTinh.forEach((item,index,array) => {
              item.STT = index + 1
            })
          } else {
            this.dsHknNoiTinh = []
          }
        } catch (err) {
        }
      }, setDisableBtn: function (isNhapmoi, isGhilai, isHuy, isXoa) {
        this.isDisableBtnNhapMoi = isNhapmoi
        this.isDisableBtnGhiLai = isGhilai
        this.isDisableBtnHuy = isHuy
        this.isDisableBtnXoa = isXoa
      },

      xoaHknNoiTinh: function () {
        if(this.isDisableBtnXoa){
          return
        }
        if(this.$refs['table'].grid.getSelectedRowIndexes().length <=0){
          this.$toast.error( 'Bạn chưa chọn hướng kết nối nào để xóa.')
          return
        }
        if (!this.isDisableBtnXoa) {
          this.modalXoa = !this.modalXoa
        }
      },
      async xacNhanXoaHknNoiTinh() {
        try {


          var rs = await this.xoaHknAction(this.huongKnId)
          this.setDisableBtn(false, true, true, true)
          if (rs.data.result == true) {
            this.$toast.success('Xóa thành công.')
            await this.layDanhSachHkn()
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
  async mounted() {
    try {
      this.loading(true);
      await this.layDMHknNoiTinhAction();
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

      if(this.loaiKn && this.thietBiKn){
        this.thietBiKn =  this.thietBiFirst.filter(item =>  item.DONVI_QL_ID === this.vungId && item.LOAIKN_ID === this.loaiKnId )
        if(this.thietBiKn.length >0){
        }
      }

      await this.layDanhSachHkn()
    } catch (err) {
      console.log(err);
    } finally {
      this.loading(false);
    }
  },
  data() {
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
      dsHknNoiTinh: [],
      thietBiId: '',
      loaiKnId: '',
      vungId: '',
      huongKnId: '',
      loaiKnItem: '',
      isCreated: true,
      modalXoa: false
    };
  }, validations: {
    vungId: {
      required
    }, loaiKnId: {
      required
    }, thietBiId: {
      required
    }
  },
  created() {
    this.clearState();
    this.createApiManager();
  },
  destroyed() {
    this.clearState();
  },
};
</script>

<style scoped src="./index.scss">
</style>

