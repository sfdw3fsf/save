<template  src="./main.html">

</template>

<script>
import BssRequiredMarker from '@/components/BssRequiredMarker'
import ModalChonDiaChi from '../../BanDoTuyenCot/ChonDiaChi/Modal'
import moment from 'moment'

export default {
  name: '', provide: {
  },
  components :{BssRequiredMarker,ModalChonDiaChi,moment},
  computed: {
  },
  data () {
    return {
       category : {
        dichvu: 0,
        thongtinphongtro: 0,
        capnhatdautien: 0,
        capnhatgannhat: 0,
      },
      dataSelected :{
         soMay :"",
          dichVu:"",
          loaiHinh:"",
          tocDo:"",
          id:"",
        maNhom :"",
        maPhong :"",
        ngayHetHanThue :"",
          ghichu:"",
          nhanvienthuthap:"",
          ngaythuthap:"",
          nguoiCn:"",
        ngayCn:"",
        thuebaoId:"",
        nhomthueId:"",
        rowid:""
      },
      waterMark: '',
      dateVal: new Date(),
      dateFormat: 'dd/MM/yyyy',
      resolvePromise: null,
      rejectPromise: null,
      dataAll: []
    }
  },
  methods :{
    show (data) {
      return new Promise((resolve, reject) => {
        this.$bvModal.show("themmoiphongtro")
        this.dataAll = data
        this.initForm()
        console.log(this.dataAll)
        this.resolvePromise = resolve
        this.rejectPromise = reject
      })
    },initForm(){
      this.dataSelected.soMay = this.dataAll.MA_TB
      this.dataSelected.dichVu= this.dataAll.TEN_DVVT
      this.dataSelected.loaiHinh=this.dataAll.LOAIHINH_TB
      this.dataSelected.tocDo=this.dataAll.THUONGHIEU
      this.dataSelected.maNhom=this.dataAll.NHOMTHUE_ID
      this.dataSelected.thuebaoId=this.dataAll.THUEBAO_ID
    },resetModal(){
        this.dataSelected.soMay =""
      this.dataSelected.dichVu=""
      this.dataSelected.loaiHinh=""
      this.dataSelected.tocDo=""
      this.dataSelected.id=""
      this.dataSelected.maNhom=""
      this.dataSelected.ngayHetHanThue=""
      this.dataSelected.ghichu=""
      this.dataSelected.nhanvienthuthap=""
      this.dataSelected.ngaythuthap=""
      this.dataSelected.nguoiCn=""
      this.dataSelected.ngayCn=""
    },closeModal(){
    this.resolvePromise({ok: false, data: null})
      this.$bvModal.hide("themmoiphongtro")
    }, ghiLai: async function () {
      if (!this.dataSelected.maPhong) {
        this.$root.toastError("Mã phòng không được để trống!")
        return
      }
      await this.insert_phongtro()
    },
    insert_phongtro:async function(){
      this.loading(true)
      let ngayHetHanThue ;
      if(this.dataSelected.ngayHetHanThue){
        ngayHetHanThue = moment( this.dataSelected.ngayHetHanThue).format('DD/MM/YYYY')
      }
      console.log(ngayHetHanThue)
      await this.$root.context.post(

        `/web-cabman/quanly-nhatro/insert_phongtro`,{
          nhomthue_id: this.dataAll.NHOMTHUE_ID,
          thuebao_id: this.dataAll.THUEBAO_ID,
        quy_mo: this.dataSelected.maPhong,
        ngay_hethan_tro: ngayHetHanThue,
        nguoi_cn: this.$root.token.getUserName(),
        nhanvien_id: this.$root.token.getNhanVienID(),
        ghi_chu: this.dataSelected.ghichu
        }
      ).then(res =>{
        this.$toast.success('Thêm mới thành công.')
        this.resolvePromise({ok: true, data: true})
        this.$bvModal.hide("themmoiphongtro")
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
