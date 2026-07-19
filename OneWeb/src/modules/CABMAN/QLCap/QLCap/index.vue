<template src="./index.html"></template>
<script>
import ModalCsht from '../../BanDoTuyenCot/Csht/Modal'
import frmChonTBIChiTiet from './frmChonTBIChiTiet'
import frmChonGoiThauOnline from "./frmChonGoiThauOnline";
import frmChonHopDongIMSOnline from "./frmChonHopDongIMSOnline";
import QuyHoachSoiCap from './QuyHoachSoiCap'
import ModalTreoCap from './TreoCapLenCot/Modal'
import ModalChuiCap from './ChuiCapQuaOngCong/Modal'
import BssErrorMarker from '@/components/BssErrorMarker'
import BssRequiredMarker from '@/components/BssRequiredMarker'
import {required, minValue, maxValue, minLength, maxLength} from 'vuelidate/lib/validators'
import ModalNVQLCap from '../../gan-nhanvien-quanly-cap/Modal'

export default {
  components: {
    ModalTreoCap,
    ModalChuiCap,
    QuyHoachSoiCap,
    BssErrorMarker,
    BssRequiredMarker,
    ModalCsht,
    frmChonTBIChiTiet,
    frmChonGoiThauOnline,
    frmChonHopDongIMSOnline,
    ModalNVQLCap
  },
  name: "QLCap",
  props: {
    /***
     * case 1: cho phép INS: capObj:{...,không có CAP_ID}, taomoi:true , editable:false
     * case 2 cho phép UPD: capObj:{CAP_ID:123xx}, taomoi:false, editable:true
     * case 3: cho phép view: capObj:{CAP_ID:123xxx}, taomoi:false, editable:false
     */
    id: String,
    capObj: {
      type:Object,
      required: true
    },
    taomoi:Boolean,
    editable: Boolean,
    showQuyHoachTreoCap: {
      type: Boolean,
      default: true,
    },
  },
  data: function (){
    return {
      doiTuongObj:{
        LOAI_DT: 'CAP',
        DOITUONG_ID: null,
      },
      idPopupNVQLCap: 'idPopupNVQLCap',
      key: 1,
      isShown: false,
      // capId:530580,
      isDisableBtnGhiLai: true,
      isDisableBtnTreoCap:true,
      isDisableBtnChuiCap:true,
      isDisableBtnQuyHoach:true,

      taomoiCap: false,
      cap: {
        DUAN_ID:null
      },

      category: {
        phanloai: 0,
        dinhdanh: 0,
        dactinh: 0,
        lienket: 0,
        duan: 0,
        vitri: 0,
        quanly: 0,
        ngaythang: 0,
        macsht:0,
      },
      modalData: {
        KICHCO_CAP: [],
        DM_DIENCT:[],
        NOI_SX: [],
        TRANGTHAI_SD: [],
        LOAI_CAP: [],
        LOP_MC: [],
        NHOM_LC: [],
        KIEU_DC: [],
        KIEU_CAP: [],
        DUAN: [],
        TAISAN: [],
        TOQL: [],
        ODF_D:[],
        ODF_N:[],
      },
      waterMark: 'Chọn ngày',
      dateVal: new Date(),
      dateFormat: 'dd/MM/yyyy',
      maDuAn: '',
      TEN_NVQL: '',
    }
  },
  computed:{
    getChuiCap(){
      return this.cap
    },
    getTreoCap(){
      return this.cap
    }
  },
  watch:{
    'cap.DUAN_ID': async function(value){
      if (!value) return
      await this.$root.context.post(
        '/web-cabman/danhmuc/layDsTaiSan', {id:value}
      ).then((rs) => {
        this.modalData.TAISAN = rs.data
      }).catch(e => this.$root.toastError("Có lỗi xảy ra khi tải danh sách tài sản"))
    }
  },
  destroyed() {
    this.clearForm()
  },
  methods: {
    onHiddenModal() {
      this.isShown = false
      this.key++
      this.clearForm()
    },
    async onShownModal() {
      this.isShown = true
      try {
        this.loading(true)
        await this.initForm()

        this.cap = {...this.capObj}
        this.taomoiCap = this.taomoi

        if (this.taomoiCap === false  && this.editable === true){
          // case UPD
          this.statusBtn(false,false,false,false)
          await this.getThongTinCap(this.cap.CAP_ID)
        }
        else {
          if (this.taomoiCap === true && this.editable === false){
            // case INS
            this.statusBtn(false,true,true,true)
            this.createCap()
            await this.getThongTinTaoCap()
          }else if (this.taomoiCap === false && this.editable === false){
            // case VIEW
            this.statusBtn(true,true,true,true)
            await this.getThongTinCap(this.cap.CAP_ID)
          }
        }
        this.cap = {...this.cap}

      }catch (e){

      }finally {
        this.loading(false)
      }
    },
    async btnGhiLai(){
      if (this.isDisableBtnGhiLai) return
      try {
        this.loading(true)
        if (await this.validateData()) return
        if (this.taomoiCap){
          // INSERT]
          let object = {...this.cap}
          object.CAPGOC_ID = object.CAPGOC_ID.toString() === '-1' ? null : object.CAPGOC_ID
          object.KETCUOI_N_ID = object.KETCUOI_N_ID.toString() === '-1' ? null : object.KETCUOI_N_ID
          object.KETCUOI_D_ID = object.KETCUOI_D_ID.toString() === '-1' ? null : object.KETCUOI_D_ID
          object.DONVI_TD_ID = object.DONVI_TD_ID.toString() === '-1' ? null : object.DONVI_TD_ID
          await this.$root.context.post("web-cabman/mang-truyen-dan/tao-cap",object)
            .then(res =>{
              if (res.error === "200" && res.error_code === "BSS-00000000"){
                this.cap = res.data
                this.cap.NGAY_LD = new Date(this.cap.NGAY_LD)
                this.cap.NGAY_SD = new Date(this.cap.NGAY_SD)
                this.taomoiCap = false
                this.statusBtn(false,false, false, false)
                this.$root.toastSuccess("Tạo mới thành công")
                this.$emit("isOk","INSERT",res.data)
                this.$bvModal.hide(this.id)
              }
            }).catch(e => this.$root.toastError(e.response.data.message_detail==undefined?"Có lỗi xảy ra trong quá trình thêm mới cáp":e.response.data.message_detail))
        }else {
          // UPDATE
          this.cap.NGAY_LD = new Date(this.cap.NGAY_LD)
          this.cap.NGAY_SD = new Date(this.cap.NGAY_SD)
          await this.$root.context.post("web-cabman/mang-truyen-dan/capnhat-cap",this.cap)
            .then(res =>{
              if (res.error === "200" && res.error_code === "BSS-00000000"){
                this.$root.toastSuccess("Ghi lại thành công")
                this.$emit("isOk","UPDATE",res.data)
                this.$bvModal.hide(this.id)
              }
            }).catch(e => this.$root.toastError(e.response.data.message_detail==undefined?"Có lỗi xảy ra trong quá trình cập nhật cáp":e.response.data.message_detail))
        }
      }catch (e){
        console.log(e)
      }finally {
        this.loading(false)
      }
    },
    btnTreoCap(){
      if (this.isDisableBtnTreoCap) return
      this.$bvModal.show("popupTreoCap")
    },
    btnChuiCap(){
      if (this.isDisableBtnChuiCap) return
      this.$bvModal.show("popupChuiCap")

    },
    btnQuyHoach(){
      if (this.isDisableBtnQuyHoach) return
      this.$bvModal.show("popupQuyHoachSoiCap")
    },
    getMaIMS: function (args) {
      this.cap.IMS_ID = args
    },
    getMaGoiThauIMS: function (args) {
      this.cap.MA_GOITHAU_IMS = args
    },
    getMaHopDongIMS: function (args) {
      this.cap.MA_HOPDONG_IMS = args
    },
    getMaCsht: function (args) {
      this.cap.MA_CSHT = args
    },
    frmChonTBIChiTiet() {
      if (!this.cap.DUAN_ID) {
        this.$root.toastError("Bạn chưa chọn dự án!")
        return
      }
      this.maDuAn = ''
      let duan = this.modalData.DUAN.find(x => x.DUAN_ID == this.cap.DUAN_ID)
      if (duan) {
        this.maDuAn = duan.MA_CT
      }
      this.$bvModal.show('frmChonTBIChiTiet')
    },
    frmChonGoiThauOnline() {
      if (!this.cap.DUAN_ID) {
        this.$root.toastError("Bạn chưa chọn dự án!")
        return
      }
      this.maDuAn = ''
      let duan = this.modalData.DUAN.find(x => x.DUAN_ID == this.cap.DUAN_ID)
      if (!duan) {
        this.$root.toastError(`Dự án không tồn tại: [${this.cap.DUAN_ID}]`)
        return
      }
      this.maDuAn = duan.MA_CT
      this.$bvModal.show('frmChonGoiThauOnline')
    },
    frmChonHopDongIMSOnline() {
      if (!this.cap.MA_GOITHAU_IMS) {
        this.$root.toastError("Bạn chưa chọn gói thầu!")
        return
      }
      this.$bvModal.show('frmChonGoiThauOnline')
    },
    async getThongTinCap(capId){
      if (!capId) return
      this.doiTuongObj.DOITUONG_ID = capId
      await this.$root.context.get("web-cabman/mang-truyen-dan/lay-thongtin-cap",{capId:capId})
      .then(res => {
        this.cap = res.data
        // get thong odf for case hni
        if (this.cap.LOPMC_ID && this.cap.DONVI_ID) {
          this.$root.context.get("web-cabman/mang-truyen-dan/lay-ds-odf-nguon", {
            lopmc_id: this.cap.LOPMC_ID,
            donvi_id: this.cap.DONVI_ID
          }).then(rs => {
            this.modalData.ODF_N = rs.data
          }).catch(e => {})
        }
        if (this.cap.LOPMC_ID && this.cap.DONVI_TD_ID) {
          this.$root.context.get("web-cabman/mang-truyen-dan/lay-ds-odf-dich", {
            lopmc_id: this.cap.LOPMC_ID,
            donvitd_id: this.cap.DONVI_TD_ID
          }).then(rs => {
            this.modalData.ODF_D = rs.data
          }).catch(e => {})
        }
      }).catch(e => this.$root.toastError("Có lỗi xảy ra khi tải thông tin cáp"))
      let rsCap = await this.$root.context.post('/web-cabman/ban-do-mang-cap/sp_thongtin_cap', {P_CAP_ID: capId})
      this.TEN_NVQL = rsCap.data[0].DS_NHANVIEN_QL
    },
    async getThongTinTaoCap(){
      await this.$root.context.get("web-cabman/mang-truyen-dan/lay-thongtin-taocap",{LOAI:'LOP_MC', LOAI_ID:this.cap.LOPMC_ID})
      .then(res =>{
        if (res.data.length === 1)
          this.cap.LOP_MC = res.data[0].LOP_MC
        else this.cap.LOP_MC = ''
      }).catch(e => this.$root.toastError("Có lỗi xảy ra khi tải thông tin lớp mạng cáp"))

      await this.$root.context.get("web-cabman/mang-truyen-dan/lay-thongtin-taocap",{LOAI:'KIEU_CAP', LOAI_ID:this.cap.KIEUCAP_ID})
        .then(res =>{
          if (res.data.length === 1)
            this.cap.KIEU_CAP = res.data[0].KIEU_CAP
          else this.cap.KIEU_CAP = ''
        }).catch(e => this.$root.toastError("Có lỗi xảy ra khi tải thông tin kiểu cáp"))

      if (this.cap.CAPGOC_ID !== -1){
        await this.$root.context.get("web-cabman/mang-truyen-dan/lay-thongtin-taocap",{LOAI:'CAPGOC_KYHIEU', LOAI_ID:this.cap.CAPGOC_ID})
          .then(res =>{
            if (res.data.length === 1)
              this.cap.CAPGOC_KYHIEU = res.data[0].CAPGOC_KYHIEU
            else this.cap.CAPGOC_KYHIEU = ''
          }).catch(e => this.$root.toastError("Có lỗi xảy ra khi tải thông tin cáp gốc"))
      }else this.cap.CAPGOC_KYHIEU = ''

      if (this.cap.KETCUOI_N_ID !== -1){
        await this.$root.context.get("web-cabman/mang-truyen-dan/lay-thongtin-taocap",{LOAI:'KETCUOI_N_KYHIEU', LOAI_ID:this.cap.KETCUOI_N_ID})
          .then(res =>{
            if (res.data.length === 1)
              this.cap.KETCUOI_N_KYHIEU = res.data[0].KETCUOI_N_KYHIEU
            else this.cap.KETCUOI_N_KYHIEU = ''
          }).catch(e => this.$root.toastError("Có lỗi xảy ra khi tải thông tin kết cuối nguồn"))
      }else this.cap.KETCUOI_N_KYHIEU = ''

      if (this.cap.KETCUOI_D_ID !== -1){
        await this.$root.context.get("web-cabman/mang-truyen-dan/lay-thongtin-taocap",{LOAI:'KETCUOI_D_KYHIEU', LOAI_ID:this.cap.KETCUOI_D_ID})
          .then(res =>{
            if (res.data.length === 1)
              this.cap.KETCUOI_D_KYHIEU = res.data[0].KETCUOI_D_KYHIEU
            else this.cap.KETCUOI_D_KYHIEU = ''
          }).catch(e => this.$root.toastError("Có lỗi xảy ra khi tải thông tin kết cuối đích"))
      }else this.cap.KETCUOI_D_KYHIEU = ''

      if (this.cap.DONVI_ID !== -1){
        await this.$root.context.get("web-cabman/mang-truyen-dan/lay-thongtin-taocap",{LOAI:'TEN_TRAMTBI', LOAI_ID:this.cap.DONVI_ID})
          .then(res =>{
            if (res.data.length === 1)
              this.cap.TEN_TRAMTBI = res.data[0].TEN_TRAMTBI
            else this.cap.TEN_TRAMTBI = ''
          }).catch(e => this.$root.toastError("Có lỗi xảy ra khi tải thông tin trạm thiết bị"))
      }else this.cap.TEN_TRAMTBI = ''

      if (this.cap.TOQL_ID !== -1){
        await this.$root.context.get("web-cabman/mang-truyen-dan/lay-thongtin-taocap",{LOAI:'TEN_TOQUANLY', LOAI_ID:this.cap.TOQL_ID})
          .then(res =>{
            if (res.data.length === 1) {
              this.cap.TEN_TOQUANLY = res.data[0].TEN_TOQUANLY

            }
            else this.cap.TEN_TOQUANLY = ''
          }).catch(e => this.$root.toastError("Có lỗi xảy ra khi tải thông tin tổ quản lý"))
      }else this.cap.TEN_TOQUANLY = ''

      if (this.cap.DONVI_TD_ID !== -1){
        await this.$root.context.get("web-cabman/mang-truyen-dan/lay-thongtin-taocap",{LOAI:'TEN_TRAMTD', LOAI_ID:this.cap.DONVI_TD_ID})
          .then(res =>{
            if (res.data.length === 1)
              this.cap.TEN_TRAMTD = res.data[0].TEN_TRAMTD
            else this.cap.TEN_TRAMTD = ''
          }).catch(e => this.$root.toastError("Có lỗi xảy ra khi tải thông tin trạm truyền dẫn"))
      }else this.cap.TEN_TRAMTD = '';

      //Sinh ký hiệu cáp mặc định
      await this.$root.context.post(
        '/web-cabman/ban-do-mang-cap/sinh_kyhieu_cap_macdinh_v2',
        {
          VCAPGOC_ID: 0,
          VDONVI_ID: 0,
          VKETCUOI_N_ID: 0,
          VKETCUOI_D_ID: 0,
          VNHOMLC_ID: 0,
          VLOPMC_ID: 0,
          VKIEUCAP_ID: 1
        }
      ).then(rs =>{
        if (rs.data && rs.data.length > 0) {
          this.cap.KYHIEU =  rs.data[0].KYHIEU_CAP_MACDINH
        }
      });

    },
    showNVQLKetCuoi(){
      this.$bvModal.show(this.idPopupNVQLCap)
    },
    closeModalNVQLCap() {
      this.getThongTinCap(this.doiTuongObj.DOITUONG_ID)
    },
    modalNVQLCapShow() {

    },
    xoaMaDuAn() {
      this.cap.DUAN_ID = null
    },
    xoaMaTaiSan() {
      this.cap.TAISAN_ID = null
    },
    clearForm(){
      this.cap = {}
      this.cap.DUAN_ID = null

      //this.cap = {}
      this.modalData = {
        KICHCO_CAP: [],
        DM_DIENCT:[],
        NOI_SX: [],
        TRANGTHAI_SD: [],
        LOAI_CAP: [],
        LOP_MC: [],
        NHOM_LC: [],
        KIEU_DC: [],
        KIEU_CAP: [],
        DUAN: [],
        TAISAN: [],
        TOQL: [],
        ODF_N: [],
        ODF_D: [],
      }
      this.category = {
        phanloai: 0,
        dinhdanh: 0,
        dactinh: 0,
        lienket: 0,
        duan: 0,
        vitri: 0,
        quanly: 0,
        ngaythang: 0,
        macsht: 0
      }
    },
    createCap(){
      // this.cap = capObj
      this.cap.CAPGOC_ID = -1
      this.cap.KIEUDC_ID = 1
      this.cap.LOAICAP_ID = 1
      //this.LOP_MC xét theo nguồn và đích
      this.cap.NGAY_LD = new Date()
      this.cap.NGAY_SD = new Date()
      this.cap.NHOMLC_ID = 1
      this.cap.NOISX_ID = 1
      this.cap.TTSD_ID = 1

      this.cap.KICHCO_ID = 0
      this.cap.KC_COT = 0
      this.cap.TROKHANG = 0
      this.cap.SUYHAO = 0
      this.cap.SODOIDAY = 0
      this.cap.SODOI_SD = 0
      this.cap.SO_DH = 0
      this.cap.DIENCAOTHE = 0
      this.cap.CHIEUDAI = 0
      this.cap.NOISX_ID = 1 // chưa xác định
      this.cap.TTSD_ID = 1 // Đang sử dụng
    },
    statusBtn(btnGhiLai, btnTreoCap, btnChuiCap, btnQuyHoach){
      this.isDisableBtnGhiLai = btnGhiLai
      this.isDisableBtnTreoCap = btnTreoCap
      this.isDisableBtnChuiCap = btnChuiCap
      this.isDisableBtnQuyHoach = btnQuyHoach
    },
    async initForm(){
      await this.$root.context.post(
        '/web-cabman/danhmuc/layDsKichCoCap',
      ).then(rs =>{
        this.modalData.KICHCO_CAP = rs.data
      }).catch(e => this.$root.toastError("Có lỗi xảy ra khi tải danh sách kích cỡ cáp"))

      await this.$root.context.get(
        '/web-cabman/mang-truyen-dan/lay-ds-dienct',
      ).then(rs =>{
        this.modalData.DM_DIENCT = rs.data
      }).catch(e => this.$root.toastError("Có lỗi xảy ra khi tải danh sách điện cao thế"))

      await this.$root.context.post(
        '/web-cabman/danhmuc/layDsNoiSX',
      ).then(rs =>{
        this.modalData.NOI_SX = rs.data
      }).catch(e => this.$root.toastError("Có lỗi xảy ra khi tải danh sách nơi sản xuất"))

      await this.$root.context.post(
        '/web-cabman/danhmuc/layDsTrangThaiSuDung',
      ).then(rs =>{
        this.modalData.TRANGTHAI_SD = rs.data
      }).catch(e => this.$root.toastError("Có lỗi xảy ra khi tải danh sách trạng thái sử dụng"))

      await this.$root.context.post(
        '/web-cabman/danhmuc/layDsLoaiCap',
      ).then(rs =>{
        this.modalData.LOAI_CAP = rs.data
      }).catch(e => this.$root.toastError("Có lỗi xảy ra khi tải danh sách loại cáp"))

      await this.$root.context.post(
        '/web-cabman/danhmuc/layDsNhomLC',
      ).then(rs =>{
        this.modalData.NHOM_LC = rs.data
      }).catch(e => this.$root.toastError("Có lỗi xảy ra khi tải danh sách nhóm lớp mạng cáp"))

      await this.$root.context.post(
        '/web-cabman/danhmuc/layDsKieuDC',
      ).then(rs =>{
        this.modalData.KIEU_DC = rs.data
      }).catch(e => this.$root.toastError("Có lỗi xảy ra khi tải danh sách kiểu đi cáp"))

      await this.$root.context.post(
        '/web-cabman/danhmuc/layDsDuAn',
      ).then(rs =>{
        this.modalData.DUAN = rs.data
      }).catch(e => this.$root.toastError("Có lỗi xảy ra khi tải danh sách dự án"))
      await this.$root.context.get(
        '/web-cabman/danhmuc/quanlyCap/lay_ds_toql',
      ).then(rs =>{
        this.modalData.TOQL = rs.data
      }).catch(e => this.$root.toastError("Có lỗi xảy ra khi tải danh sách tổ quản lý"))

    },
    async validateData(){
      this.$v.$touch()
      let errorMessage = []
      if (!this.$v.cap.MA_CAP.maxLength || !this.$v.cap.MA_CAP.minLength){
        errorMessage.push('Mã cáp phải nằm trong khoản từ 0 đến 8 ký tự')
      }
      if (!this.$v.cap.KYHIEU.required) {
        errorMessage.push('Ký hiệu không được để trống')
      }else {
        //kiem tra tồn tại kyhieu
        await this.$root.context.post("/web-cabman/mang-truyen-dan/kiemtra-unique-kyhieu-cap",{CAP_ID:this.taomoiCap ? null : this.cap.CAP_ID,KYHIEU:this.cap.KYHIEU})
          .then(res=>{
            if (res.data != null && res.data.result === false){
              errorMessage.push('Ký hiệu đã tồn tại')
            }
          }).catch(e => this.$root.toastError("Có lỗi xảy ra trong quá trình kiểm tra ký hiệu tồn tại"))
        if (!this.$v.cap.KYHIEU.maxLength || !this.$v.cap.KYHIEU.minLength){
          errorMessage.push('Ký hiệu phải nằm trong khoản từ 1 đến 70 ký tự')
        }
      }
      if (!this.$v.cap.TEN_CAP.required) {
        errorMessage.push('Tên cáp không được để trống')
      }else {
        if (!this.$v.cap.TEN_CAP.maxLength || !this.$v.cap.TEN_CAP.minLength){
          errorMessage.push('Tên cáp phải nằm trong khoản từ 1 đến 70 ký tự')
        }
      }

      if (!this.$v.cap.GHICHU.maxLength || !this.$v.cap.GHICHU.minLength){
        errorMessage.push('Ký hiệu phải nằm trong khoản từ 0 đến 100 ký tự')
      }

      if (!this.$v.cap.KICHCO_ID.required) {
        errorMessage.push('Kích cỡ cáp không được để trống')
      }
      if (!this.$v.cap.SODOIDAY.minValue || !this.$v.cap.SODOIDAY.maxValue){
        errorMessage.push('Dung lượng phải nằm trong khoản từ 1 đến 99999')
      }

      if (!this.$v.cap.NOISX_ID.required) {
        errorMessage.push('Nơi sản xuất không được để trống')
      }

      if (!this.$v.cap.TTSD_ID.required) {
        errorMessage.push('Trạng thái sử dụng không được để trống')
      }

      if (!this.$v.cap.LOAICAP_ID.required) {
        errorMessage.push('Loại cáp không được để trống')
      }

      if (!this.$v.cap.LOPMC_ID.required) {
        errorMessage.push('Lớp mạng cáp không được để trống')
      }

      if (!this.$v.cap.NHOMLC_ID.required) {
        errorMessage.push('Nhóm lớp mạng cáp không được để trống')
      }

      if (!this.$v.cap.KIEUDC_ID.required) {
        errorMessage.push('Kiểu đi cáp không được để trống')
      }

      if (!this.$v.cap.KIEUCAP_ID.required) {
        errorMessage.push('Kiểu cáp không được để trống')
      }

      if (errorMessage.length > 0) {
        this.$root.toastError(errorMessage.join('\n'))
        return true
      }else {
        return false
      }
    },
    formatDate(dateString) {
      const date = new Date(dateString)
      const day = date.getDate().toString().padStart(2, '0')
      const month = (date.getMonth() + 1).toString().padStart(2, '0') // Months are 0-based
      const year = date.getFullYear()
      const hours = date.getHours().toString().padStart(2, '0')
      const minutes = date.getMinutes().toString().padStart(2, '0')
      const seconds = date.getSeconds().toString().padStart(2, '0')

      return `${hours}:${minutes}:${seconds}; ${day}/${month}/${year}`
    }
  },
  validations: {
    cap:
      {
        MA_CAP: {
          minLength: minLength(0),
          maxLength: maxLength(8)
        },
        KYHIEU: {
          required,
          minLength: minLength(1),
          maxLength: maxLength(70)
        },
        TEN_CAP: {
          required,
          minLength: minLength(1),
          maxLength: maxLength(70)
        },
        GHICHU:{
          minLength: minLength(0),
          maxLength: maxLength(100)
        },
        KICHCO_ID: {
          required
        },
        SODOIDAY: {
          minValue: minValue(1),
          maxValue: maxValue(99999)
        },
        NOISX_ID: {
          required
        },
        TTSD_ID: {
          required
        },
        LOAICAP_ID: {
          required
        },
        LOPMC_ID: {
          required
        },
        NHOMLC_ID: {
          required
        },
        KIEUDC_ID: {
          required
        },
        KIEUCAP_ID: {
          required
        },
      }
  },
}
</script>

<style>
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

.btn-del{
  margin: 5px 0px 5px 5px;
  position: relative;
  z-index: 999;
  border-left: 1px solid #e9ecef;
}
</style>
