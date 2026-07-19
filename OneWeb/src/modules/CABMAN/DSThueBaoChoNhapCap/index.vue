<template src="./index.html"></template>
<script>
import ModalDauNoiThueBao from './modal-daunoi-thuebao'
import BssRequiredMarker from '@/components/BssRequiredMarker'

export default {
  name: 'DSThueBaoChoNhapCap',
  components:{ModalDauNoiThueBao, BssRequiredMarker},
  data () {
    return {
      dsTTVT:[],
      cboTTVT: '',
      cboKieuYC: 0,
      thueBaoChitiet: null,
      dsYCNhapCap:[],
      thongTinKyThuat:null,
      dsThongTinDanDoCapRutGon:[],
      dsThongTinDanDoCapChiTiet:[],
      thongTinThueBao:{
        tenThueBao:'',
        loaiHinh:'',
        trangThai:'',
        diaChi:''
      },
      dsVeTinh:[],
      cboVeTinh: null,
      showTable:true,
      treeVeTinh: [],

      isDisableBtnDauNoi:true,
      isDisableBtnHoanThien:true,
    }
  },
  async created() {
    try {
      this.loading(true)
      //load cbo
      await this.getDsTTVT()
      if (this.cboTTVT){
        await this.getDsVeTinh(this.cboTTVT)
        if (this.dsVeTinh){
          this.treeVeTinh = this.createTreeSelect(this.dsVeTinh)
        }
      }
    }catch (e){
      console.log(e)
      // this.$toast.error(e)
    }finally {
      this.loading(false)
    }
  },
  methods:{
    async btnNapLai(){
      try {
        this.loading(true)
        if (this.cboVeTinh){
          await this.getDsYCNhapCap(this.cboVeTinh, this.cboKieuYC)
          if (this.dsYCNhapCap.length >0){
            this.onClickYCNhapCapChiTiet(this.dsYCNhapCap[0])
          }
        }else this.$root.toastError("Bạn chưa chọn vệ tinh")
      }catch (e){
      }finally {
        this.loading(false)
      }
    },
    // model Dau noi
    btnDauNoi(){
      let thongTinDauNoi = {
        choPhepDoiTramTbi: false,
        tramTbiId: this.cboVeTinh,
        chonTramTbiTheoTB: false,
        thueBaoId: this.thueBaoChitiet.THUEBAO_ID,
        kieu: 0
      }

      let modal = this.$refs['modal-daunoi-thuebao']
      modal.setData(thongTinDauNoi)
      modal.show()
    },
    onChangeDauNoi() {
      this.btnHoanThienDauChuyen()
    },
    hide() {
      this.$refs['modal-daunoi-thuebao'].hide()
    },
    // Luu thong tin dau chuyen
    async btnHoanThienDauChuyen() {
      if (this.isDisableBtnHoanThien) return
      //message
      let message = null
      if (this.thueBaoChitiet == null){
        this.$root.toastError('Chưa chọn thuê bao !')
        return
      }
      else if(this.cboKieuYC == 3){
        this.$root.toastError('Loại yêu cầu: Lắp mới không thể hoàn thiện đấu chuyển')
        return
      }
      let objectDauChuyen = new Object()
      // kieu: dau chuyen
      if (this.cboKieuYC === 0 && this.thueBaoChitiet.DKCC_ID != null){
        objectDauChuyen.kieuycId = this.cboKieuYC
        objectDauChuyen.phieuId = null
        objectDauChuyen.dkccId = this.thueBaoChitiet.DKCC_ID
      }
      //kieu: bao hong
      else if (this.cboKieuYC === 2 && this.thueBaoChitiet.BAOHONG_ID != null){
        objectDauChuyen.kieuycId = this.cboKieuYC
        objectDauChuyen.phieuId = this.thueBaoChitiet.PHIEU_ID
        objectDauChuyen.dkccId = null
      }
      // kieu: dich chuyen
      else if (this.cboKieuYC === 1 && this.thueBaoChitiet.IS_THUEBAO === 1 && this.thueBaoChitiet.DB_ID != null){
        objectDauChuyen.kieuycId = this.cboKieuYC
        objectDauChuyen.phieuId = this.thueBaoChitiet.PHIEU_ID
        objectDauChuyen.dkccId = null
      }
      try{
        this.loading(true)
        // let response = await this.hoanThienDauChuyen(objectDauChuyen)
        await this.$root.context.post("/web-cabman/yc-nhapcap/hoanthien-dauchuyen", objectDauChuyen)
        .then(response =>{
          if (response.data['result']){
            message = 'Hoàn thiện đấu chuyển thành công !'
          }
        }).catch(e =>{ throw e.response.data.message_detail})
      }catch (err){
        console.log(err)
        if (err)
          this.$root.toastError(err)
        else this.$root.toastError("Có lỗi xảy ra trong quá trình hoàn thiện đấu chuyển")
      }finally {
        await this.btnNapLai()
        if (message) this.$root.toastSuccess(message)
        this.loading(false)
      }
    },
    getDsTTVT: async function(){
      this.loading(true)
      await this.$root.context.get(
        "web-cabman/bando_tuyencot/dsttvt_theonv"
      ).then(res =>{
        this.dsTTVT = res.data
        if (res.data.length > 0){
          this.cboTTVT = res.data[0].DONVI_ID
        }
      }).catch(e=> {throw e}).finally(()=> this.loading(false))
    },
    getDsVeTinh: async function(donviId){
      this.loading(true)
      await this.$root.context.get(
        "/web-cabman/yc-nhapcap/lay-ds-vetinh",{ttvt:donviId}
      ).then(res =>{
        this.dsVeTinh = res.data
      }).catch(e=> {throw e}).finally(()=> this.loading(false))
    },
    getDsYCNhapCap: async function(tramtbiId, kieuycId){
      this.loading(true)
      if (this.cboVeTinh){
        await this.$root.context.get(
          "/web-cabman/yc-nhapcap/lay-ds-yeucau-nhapcap",{tramtbiId:tramtbiId,kieuycId:kieuycId}
        ).then(res =>{
          this.dsYCNhapCap = res.data
        }).catch(e=> {throw e}).finally(()=> this.loading(false))
      }
    },
    getDsThongTinKyThuat: async function(objectKyThuat){
      this.loading(true)
      await this.$root.context.post(
        "/web-cabman/yc-nhapcap/lay-thongtin-kythuat",objectKyThuat
      ).then(res =>{
        this.thongTinKyThuat = res.data
      }).catch(e=> {throw e}).finally(()=> this.loading(false))
    },
    getDsThongTinDanDoCap: async function(objectDanDoCap){
      this.loading(true)
      await this.$root.context.post(
        "/web-cabman/yc-nhapcap/lay-thongtin-dandocap",objectDanDoCap
      ).then(res =>{
        if (res.data.RUTGON.length > 0 && res.data.CHITIET){
          this.dsThongTinDanDoCapRutGon = res.data.RUTGON
          this.dsThongTinDanDoCapChiTiet = res.data.CHITIET
        }else{
          this.dsThongTinDanDoCapRutGon = []
          this.dsThongTinDanDoCapChiTiet = []
        }
      }).catch(e=> {throw e}).finally(()=> this.loading(false))
    },
    // Chon chi tiet thong tin thue bao cho nhap cap
    async onClickYCNhapCapChiTiet(prefix) {
      try {
        this.loading(true)
        // load thong tin thue bao
        if (prefix){
          this.thongTinThueBao.tenThueBao = prefix.TEN_TB != null ? prefix.TEN_TB : ""
          this.thongTinThueBao.loaiHinh = prefix.LOAIHINH_TB != null ? prefix.LOAIHINH_TB : ""
          this.thongTinThueBao.trangThai = prefix.TRANGTHAI_TB != null ? prefix.TRANGTHAI_TB : ""
          this.thongTinThueBao.diaChi = prefix.DIACHI_LD != null ? prefix.DIACHI_LD : ""
          // lay thong tin ky thuat va dan do cap
          let objectKyThuat = new Object()
          objectKyThuat.isThueBao = prefix.IS_THUEBAO
          objectKyThuat.dbId = prefix.DB_ID
          objectKyThuat.thuebaoId = prefix.THUEBAO_ID

          let objectDanDoCap = new Object()
          objectDanDoCap.ketcuoiId = prefix.KETCUOI_ID
          objectDanDoCap.vitri = prefix.VITRI
          await this.getDsThongTinKyThuat(objectKyThuat)
          await this.getDsThongTinDanDoCap(objectDanDoCap)
        }
      } catch (err) {
        console.log(err)
        // this.$toast.error(err)
      } finally {
        this.loading(false)
      }
    },
    // Clear Thong Tin Thue Bao Chi Tiet
    clearThongTinThueBao(){
      this.thongTinKyThuat = null
      this.dsThongTinDanDoCapRutGon = []
      this.dsThongTinDanDoCapChiTiet = []
      this.thueBaoChitiet = null
      this.thongTinThueBao.tenThueBao = ''
      this.thongTinThueBao.loaiHinh = ''
      this.thongTinThueBao.trangThai = ''
      this.thongTinThueBao.diaChi = ''
    },
    createTreeSelect(danhsach){
      let arr = []
      let parentNode = danhsach.filter(item => item.IS_VETINH === 0)
      let childrenNode = danhsach.filter(item => item.IS_VETINH ===1)
      parentNode.forEach(parent =>{
        let childrenNodeList = []
        childrenNode.forEach(children =>{
          if (parent.DONVI_ID === children.DONVI_CHA_ID){
            childrenNodeList.push({id:children.DONVI_ID, label:children.TEN_DV})
          }
        })
        arr.push({id:parent.DONVI_ID, label:parent.TEN_DV, children:childrenNodeList})
      })
      return arr
    },
    //Delete children node of tree select when children null
    normalizer(node){
      // Remove the properties of Children = NULL
      if(node.children==null||node.children=='null'){
        delete node.children;
      }
    },
    statusBtn(btnDauNoi, btnHoanThien){
      this.isDisableBtnDauNoi = btnDauNoi
      this.isDisableBtnHoanThien = btnHoanThien
    }
  },
  watch:{
    "cboTTVT":async function (value){
      await this.getDsVeTinh(value)
      if (this.dsVeTinh){
        this.treeVeTinh = this.createTreeSelect(this.dsVeTinh)
      }
      this.cboVeTinh = null
      this.dsYCNhapCap = []
      this.clearThongTinThueBao()
    },
    //chon VeTinh
    "cboVeTinh": async function(newVeTinh){
      if (newVeTinh){
        await this.getDsYCNhapCap(newVeTinh,this.cboKieuYC)
        if (this.dsYCNhapCap.length > 0){
          this.thueBaoChitiet = this.dsYCNhapCap[0]
          this.$refs.grid1.setCurrentSelectedRow(0);
          await this.onClickYCNhapCapChiTiet(this.thueBaoChitiet)
        }else this.clearThongTinThueBao()
      }else{
        this.dsYCNhapCap = []
        this.clearThongTinThueBao()
      }
    },

    // chon Kieu yeu cau
    "cboKieuYC":async function (newKieuYC) {
      if (this.cboVeTinh) {
        await this.getDsYCNhapCap(this.cboVeTinh,newKieuYC)
        if (this.dsYCNhapCap.length > 0){
          this.thueBaoChitiet = this.dsYCNhapCap[0]
          await this.onClickYCNhapCapChiTiet(this.thueBaoChitiet)
        }else this.clearThongTinThueBao()
      } else this.$root.toastError("Bạn chưa chọn vệ tinh")
    },
    "thueBaoChitiet": function (value){
      if (value == null){
        this.statusBtn(true,true)
      }else {
        this.statusBtn(false,false)
      }
    }

  },
  async mounted()  {
    try {
      this.$store.dispatch('setHeader', {
        title: 'DANH SÁCH THUÊ BAO CHỜ NHẬP CÁP'
      })
    } catch (err) {
      console.log(err)
    }
  }
}
</script>
<style>
div#boxLeft {
  width: 40%;
  float: left;
}div#boxRight {
   width: 60%;
   float: left;
 }
.disable-btn:hover {
  background-color: white !important;
}

.disable-btn a {
  color: #6c757d !important;
}

.disable-btn a:hover {
  cursor: default;
  color: #6c757d;
}
</style>
