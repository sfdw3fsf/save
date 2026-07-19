<template  src="./main.html">

</template>

<script>
import BssRequiredMarker from '@/components/BssRequiredMarker'
import ModalChonDiaChi from '../../BanDoTuyenCot/ChonDiaChi/Modal'
import SearchAccount from "@/modules/search/subscriber/SearchAccount";
//import SearchAccount  from "../thumuctest/SearchAccount/SearchAccount";
import moment from 'moment'

export default {
  name: '', provide: {
  },
  components :{BssRequiredMarker,ModalChonDiaChi,SearchAccount,moment},
  computed: {
  },
  data () {
    return {
      isEdit:false,
       category : {
        dichvu: 0,
        thongtinchuho: 0,
        thongtinnhatro: 0,
        capnhatdautien: 0,
        capnhatgannhat: 0,
      },popupComponent: SearchAccount,
      dataSelected :{
         soMay :"",
          dichVu:"",
          loaiHinh:"",
          tocDo:"",
          id:"",
          tenChuHo:"",
          diachi:"",
          soCMND:"",
          ngaycap:"",
          sodt:"",
          email:"",
          ngaysinh:"",
          ghichu:"",
          soluongphong:"",
          nhavienthuthap:"",
          ngaythuthap:"",
          nguoiCn:"",
        mayCn:"",
      },
      maTb:"",
      thuebaoId:"",
      doituongId:"",
      waterMark: '',
      dateVal: new Date(),
      dateFormat: 'dd/MM/yyyy',
      modelChiTietThueBao:{},
      resolvePromise: null,
      rejectPromise: null,
    }
  },
  methods :{
    DiaChiXacNhan(data){
      this.dataSelected.diachi = data.diachimoi
    },  popupClosed: async function (data) {
      // if(data.ma_tb) {
      //   this.dataSelected.soMay = data.ma_tb ;
      // }
      this.dataSelected.soMay = data.ma_tb ? data.ma_tb : "";
      this.maTb = data.ma_tb ? data.ma_tb : "";
      this.thuebaoId = data.thuebao.thuebao_id ? data.thuebao.thuebao_id : "";
      this.doituongId = data.thuebao.doituong_id ? data.thuebao.doituong_id : "";
      await this.layChiTietThueBao()

      this.$refs.popupSearchAccount.hide();
    },showSearchAccount(){
      this.$refs.popupSearchAccount.show();
    },
    clearSoMay(){
      this.dataSelected.soMay = ''
    },
    show (isEdit,data) {
      return new Promise((resolve, reject) => {
        this.isEdit = isEdit
        this.$bvModal.show("themmoinhatro")
        this.resolvePromise = resolve
        this.rejectPromise = reject
      })
    },resetModal(){
        this.dataSelected.soMay =""
      this.dataSelected.dichVu=""
      this.dataSelected.loaiHinh=""
      this.dataSelected.tocDo=""
      this.dataSelected.id=""
      this.dataSelected.tenChuHo=""
      this.dataSelected.diachi=""
      this.dataSelected.soCMND=""
      this.dataSelected.ngaycap=""
      this.dataSelected.sodt=""
      this.dataSelected.email=""
      this.dataSelected.ngaysinh=""
      this.dataSelected.ghichu=""
      this.dataSelected.soluongphong=""
      this.dataSelected.nhavienthuthap=""
      this.dataSelected.ngaythuthap=""
      this.dataSelected.nguoiCn=""
      this.dataSelected.mayCn=""

    },closeModal(){
    this.resolvePromise({ok: false, data: null})
      this.$bvModal.hide("themmoinhatro")
    }, ghiLai: async function () {
      if (!this.dataSelected.tenChuHo) {
        this.$root.toastError("Tên chủ hộ không được để trống!")
        return
      }
      if (!this.dataSelected.diachi) {
        this.$root.toastError("Địa chỉ không được để trống!")
        return
      }
      if (!this.dataSelected.soluongphong) {
        this.$root.toastError("Số lượng không được để trống!")
        return
      }
      await this.insert_phongtro()
    },
    insert_phongtro:async function(){
      this.loading(true)
      let ngay_cap ;
      let ngay_sn;
      if(this.dataSelected.ngaycap){
        ngay_cap = moment( this.dataSelected.ngaycap).format('DD/MM/YYYY')
      }
      if(this.dataSelected.ngaysinh){
        ngay_sn = moment( this.dataSelected.ngaysinh).format('DD/MM/YYYY')
      }
      await this.$root.context.post(
        `/web-cabman/quanly-nhatro/them-nhatro`,{
          thuebao_id: this.thuebaoId,
          nhanvien_id: this.$root.token.getNhanVienID(),
          doituong_id: this.doituongId,
          ten_chuho: this.dataSelected.tenChuHo,
          diachi: this.dataSelected.diachi,
          so_dt: this.dataSelected.sodt,
          so_gt: this.dataSelected.soCMND,
          email: this.dataSelected.email,
          ngay_cap: ngay_cap ,
          ngay_sn: ngay_sn ,
          nguoi_cn: this.$root.token.getUserName(),
          quy_mo: this.dataSelected.soluongphong,
          ghi_chu: this.dataSelected.ghichu
        }
      ).then(res =>{
        this.$toast.success('Thêm mới thành công.')
        this.resolvePromise({ok: true, data: null})
        this.$bvModal.hide("themmoinhatro")
      }).catch(e=> {
        this.$toast.error('Thêm mới thất bại.')
      }).finally(()=> this.loading(false))
    },
    layChiTietThueBao: async function (){
      this.loading(true)
      await this.$root.context.get(
        "/web-cabman/quanly-nhatro/SP_GET_CHITIET_THUEBAO?thuebao_id=" + this.thuebaoId
      ).then(res =>{
        if(res.data.length>0){
          this.modelChiTietThueBao = res.data[0]
          this.dataSelected.dichVu = res.data[0].TEN_DVVT
          this.dataSelected.loaiHinh = res.data[0].LOAIHINH_TB
          this.dataSelected.tocDo = res.data[0].THUONGHIEU
          this.dataSelected.tenChuHo = res.data[0].TEN_TB
          this.dataSelected.diachi = res.data[0].DIACHI_LD
        } else {
          this.modelChiTietThueBao = {}
        }
      }).catch(e=> {throw e}).finally(()=> this.loading(false))
    },
    showModal(){
      this.resetModal()
    }
  },watch :{

  }
}
</script>

<style scope>
.bss-modal-tracuu-danhba {
}
.bss-modal-tracuu-danhba .breadcrumb-top {
  display: none;
}
.bss-modal-tracuu-danhba.bss-modal .modal-body .list-actions-top {
  position: inherit;
}
</style>
