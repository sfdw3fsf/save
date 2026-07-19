<template src="./index.html"></template>
<script>
import BssRequiredMarker from '@/components/BssRequiredMarker'
import {maxLength, minLength, required} from "vuelidate/lib/validators";
import BssErrorMarker from '@/components/BssErrorMarker'
import ModalChonToaDo from '../ChonToaDo'
import ModalChonDiaChi from '../ChonDiaChi/Modal'
import ModalAnhChup from "../AnhChup/Modal";

export default {
  components:{BssRequiredMarker,BssErrorMarker, ModalChonToaDo, ModalChonDiaChi, ModalAnhChup},
  name: "QuanLyKetCuoiDoiThu",
  props: {
    ketCuoiObject: {
      type: Object,
      required: true
    },
    id: String
  },
  data: function (){
    return {
      key: 1,
      isShown: false,
      ketCuoiDoiThu:{
        KYHIEU: null,
        TENKC: '',
        LOAIKC_ID: null,
        KIEULAP_ID: null,
        VITRI_ID: null,
        DIACHI: '',
        SODOI_SD: null,
        TONGSO_DD: null,
        TTSD_ID: null,
      },
      diaChiKetCuoiDoiThu:{
        DIACHI_ID:0,
        PHO_ID:0,
        PHUONG_ID:0,
        QUAN_ID:0,
        SONHA:"",
        DIACHI:"",
        AP_ID:0,
        KHU_ID:0,
        DACDIEM_ID:0,
        TINH_ID:0
      },
      diachi:{}, // bien truyen sang form dia chi

      dsChuQuan:[],
      dsKHUVUC:[],
      dsPHANLOAI_KC:[],
      dsLOAI_KC:[],
      dsKIEU_LAP:[],
      dsNOI_SX:[],
      dsVITRI:[],
      dsLOAI_VO:[],
      dsDUNGLUONG:[],
      dsTOQL:[],
      dsNVQL:[],
      dsTRANGTHAI_SD:[],
    }
  },
  computed: {
    getPosition () {
      return {
        lat: parseFloat(this.ketCuoiDoiThu.VIDO),
        lng: parseFloat(this.ketCuoiDoiThu.KINHDO)
      }
    },
    getDiachi () {
      return this.ketCuoiDoiThu.DIACHI
    },
    getKetCuoi(){
      return this.ketCuoiDoiThu
    }
  },
  methods:{
    onHiddenModal() {
      this.isShown = false
      this.key++
      this.clearForm()
    },
    async onShownModal() {
      this.clearForm()
      this.isShown = true
      try {
        this.$v.$reset()
        this.loading(true)
        await this.thongtinKetCuoiDoiThu(this.ketCuoiObject.KETCUOI_ID)
        await this.thongtinDiaChiKetCuoiDoiThu(this.ketCuoiObject.KETCUOI_ID)
        await this.initForm()
      }catch (e){}
      finally {
        this.loading(false)
      }
    },
    async btnGhiLai(){
      this.$v.$reset()
      if (await this.validateData()) return
      //cap nhat
      let object = new Object()
      object.ketcuoi_id = this.ketCuoiDoiThu.KETCUOI_ID
      object.ten_kc = this.ketCuoiDoiThu.TEN_KC
      object.kyhieu = this.ketCuoiDoiThu.KYHIEU
      object.chieudai = this.ketCuoiDoiThu.CHIEUDAI
      object.chieurong = this.ketCuoiDoiThu.CHIEURONG
      object.chieucao = this.ketCuoiDoiThu.CHIEUCAO
      object.diachi = this.ketCuoiDoiThu.DIACHI
      object.sodoi_sd = this.ketCuoiDoiThu.SODOI_SD
      object.tongso_dd = this.ketCuoiDoiThu.TONGSO_DD
      object.ttsd_id = this.ketCuoiDoiThu.TTSD_ID
      object.kieulap_id = this.ketCuoiDoiThu.KIEULAP_ID
      object.noisx_id = this.ketCuoiDoiThu.NOISX_ID
      object.vitri_id = this.ketCuoiDoiThu.VITRI_ID
      object.loaivo_id = this.ketCuoiDoiThu.LOAIVO_ID
      object.loaikc_id = this.ketCuoiDoiThu.LOAIKC_ID
      object.chuquan_id = this.ketCuoiDoiThu.CHUQUAN_ID
      object.khuvuc_id = this.ketCuoiDoiThu.KHUVUC_ID
      object.nhanvien_id = this.ketCuoiDoiThu.NHANVIEN_ID
      object.kinhdo = this.ketCuoiDoiThu.KINHDO
      object.vido = this.ketCuoiDoiThu.VIDO
      object.phanloaikc_id = this.ketCuoiDoiThu.PHANLOAIKC_ID
      object.nhanvien_ql_id = this.ketCuoiDoiThu.NHANVIEN_QL_ID
      object.toql_id = this.ketCuoiDoiThu.TOQL_ID
      object.diachi_id = this.diaChiKetCuoiDoiThu.DIACHI_ID
      object.tinh_id = this.diaChiKetCuoiDoiThu.TINH_ID
      object.pho_id = this.diaChiKetCuoiDoiThu.PHO_ID
      object.phuong_id = this.diaChiKetCuoiDoiThu.PHUONG_ID
      object.quan_id = this.diaChiKetCuoiDoiThu.QUAN_ID
      object.ap_id = this.diaChiKetCuoiDoiThu.AP_ID
      object.khu_id = this.diaChiKetCuoiDoiThu.KHU_ID
      object.dacdiem_id = this.diaChiKetCuoiDoiThu.DACDIEM_ID
      object.sonha = this.diaChiKetCuoiDoiThu.SONHA
      try {
        this.loading(true)
        await this.$root.context.post("/web-cabman/phantu_mang/capnhat_ketcuoi_doithu_diachi", object)
          .then(res =>{
            if (res.error === "200" && res.error_code === "BSS-00000000" && res.data.result === true){
              this.$root.toastSuccess("Cập nhật dữ liệu thành công !")
              // this.$emit("reload")
            }
          }).catch(e => this.$root.toastError("Có lỗi trong quá trình cập nhật dữ liệu: "+ e.response.data.message_detail))
      }catch (e){}
      finally {
        this.loading(false)
      }
    },
    // btnChonToaDo(){
    //   this.$bvModal.show('ModalChonToaDo')
    // },
    btnChonDiaChi(){
      this.diachi.diachi = this.diaChiKetCuoiDoiThu.DIACHI
      this.diachi.tinh_id = this.diaChiKetCuoiDoiThu.TINH_ID
      this.diachi.quan_id = this.diaChiKetCuoiDoiThu.QUAN_ID
      this.diachi.phuong_id = this.diaChiKetCuoiDoiThu.PHUONG_ID
      this.diachi.pho_id = this.diaChiKetCuoiDoiThu.PHO_ID
      this.diachi.ap_id = this.diaChiKetCuoiDoiThu.AP_ID
      this.diachi.khu_id = this.diaChiKetCuoiDoiThu.KHU_ID
      this.diachi.dacdiem_id = this.diaChiKetCuoiDoiThu.DACDIEM_ID
      this.diachi.so_nha = this.diaChiKetCuoiDoiThu.SONHA
      this.$bvModal.show('popupDiaChi')
    },
    XacNhanToaDo (position) {
      this.ketCuoiDoiThu.VIDO = Number.parseFloat(position.lat).toFixed(9)
      this.ketCuoiDoiThu.KINHDO = Number.parseFloat(position.lng).toFixed(9)
    },
    getDiaChiTuFormDiaChi: function (data) {
      this.diaChiKetCuoiDoiThu.DIACHI = data.diachimoi;
      this.diaChiKetCuoiDoiThu.TINH_ID = data.tinh_id;
      this.diaChiKetCuoiDoiThu.QUAN_ID = data.quan_id;
      this.diaChiKetCuoiDoiThu.PHUONG_ID = data.phuong_id;
      this.diaChiKetCuoiDoiThu.PHO_ID = data.pho_id;
      this.diaChiKetCuoiDoiThu.AP_ID = data.ap_id;
      this.diaChiKetCuoiDoiThu.KHU_ID = data.khu_id;
      this.diaChiKetCuoiDoiThu.DACDIEM_ID = data.dacdiem_id;
      this.diaChiKetCuoiDoiThu.SONHA = data.so_nha;
      //
      this.ketCuoiDoiThu.DIACHI = data.diachimoi
    },
    checkChieuDai(){
      if (!parseFloat(this.ketCuoiDoiThu.CHIEUDAI))
        this.ketCuoiDoiThu.CHIEUDAI = null
      else this.ketCuoiDoiThu.CHIEUDAI = parseFloat(this.ketCuoiDoiThu.CHIEUDAI)
    },
    checkChieuRong(){
      if (!parseFloat(this.ketCuoiDoiThu.CHIEURONG))
        this.ketCuoiDoiThu.CHIEURONG = null
      else this.ketCuoiDoiThu.CHIEURONG = parseFloat(this.ketCuoiDoiThu.CHIEURONG)
    },
    checkChieuCao(){
      if (!parseFloat(this.ketCuoiDoiThu.CHIEUCAO))
        this.ketCuoiDoiThu.CHIEUCAO = null
      else this.ketCuoiDoiThu.CHIEUCAO = parseFloat(this.ketCuoiDoiThu.CHIEUCAO)
    },
    checkKinhDo(){
      if (!parseFloat(this.ketCuoiDoiThu.KINHDO))
        this.ketCuoiDoiThu.KINHDO = null
      else this.ketCuoiDoiThu.KINHDO = parseFloat(this.ketCuoiDoiThu.KINHDO)
    },
    checkViDo(){
      if (!parseFloat(this.ketCuoiDoiThu.VIDO))
        this.ketCuoiDoiThu.VIDO = null
      else this.ketCuoiDoiThu.VIDO = parseFloat(this.ketCuoiDoiThu.VIDO)
    },
    thongtinKetCuoiDoiThu: async function (ketcuoiId) {
      await this.$root.context.get(
        '/web-cabman/phantu_mang/lay-thongtin-ketcuoi-doithu',{ketcuoiId:ketcuoiId}
      ).then(res =>{
        if (res.data != null) { this.ketCuoiDoiThu = res.data }
      }).catch(e =>{this.$root.toastError("Có lỗi xảy ra khi tải thông tin kết cuối đối thủ")})
    },
    thongtinDiaChiKetCuoiDoiThu: async function (ketcuoiId) {
      await this.$root.context.get(
        '/web-cabman/phantu_mang/lay-thongtin-diachi-ketcuoi-doithu',{ketcuoiId:ketcuoiId}
      ).then(res =>{
        if (res.data != null) { this.diaChiKetCuoiDoiThu = res.data }
      }).catch(e =>{this.$root.toastError("Có lỗi xảy ra khi tải thông tin kết cuối đối thủ")})
    },
    initForm: async function () {
      try {
        rs = await this.$root.context.get(
          '/web-cabman/ban-do-mang-cap/danh-muc', {loai: 'CHUQUAN', option: ''}
        )
        this.dsChuQuan = rs.data.filter(item => item.TENCHUQUAN !== "VNPT") // bỏ vnpt trong cbo đối thủ

        var rs = await this.$root.context.get(
          '/web-cabman/ban-do-mang-cap/danh-muc', {loai: 'PHANLOAI_KC', option: ''}
        )
        this.dsPHANLOAI_KC = rs.data

        var rs = await this.$root.context.get(
          '/web-cabman/danhmuc/lay-ds-khuvuc',
        )
        this.dsKHUVUC = rs.data

        rs = await this.$root.context.get(
          '/web-cabman/ban-do-mang-cap/danh-muc', {loai: 'LOAI_KC', option: ''}
        )
        this.dsLOAI_KC = rs.data

        rs = await this.$root.context.get(
          '/web-cabman/ban-do-mang-cap/danh-muc', {loai: 'KIEU_LAP', option: ''}
        )
        this.dsKIEU_LAP = rs.data

        rs = await this.$root.context.get(
          '/web-cabman/ban-do-mang-cap/danh-muc', {loai: 'NOI_SX', option: ''}
        )
        this.dsNOI_SX = rs.data

        rs = await this.$root.context.get(
          '/web-cabman/ban-do-mang-cap/danh-muc', {loai: 'VITRI', option: ''}
        )
        this.dsVITRI = rs.data

        rs = await this.$root.context.get(
          '/web-cabman/ban-do-mang-cap/danh-muc', {loai: 'LOAI_VO', option: ''}
        )
        this.dsLOAI_VO = rs.data

        rs = await this.$root.context.get(
          '/web-cabman/ban-do-mang-cap/danh-muc', {loai: 'TOQL', option: ''}
        )
        this.dsTOQL = rs.data

        rs = await this.$root.context.get(
            '/web-cabman/danhmuc/lay-ds-dungluong',
        )
        this.dsDUNGLUONG = rs.data

        var rs = await this.$root.context.get(
          '/web-cabman/danhmuc/dsnhanvien-quanly/' + this.ketCuoiDoiThu.TOQL_ID
        );
        this.dsNVQL = rs.data;

        rs = await this.$root.context.get(
          '/web-cabman/ban-do-mang-cap/danh-muc', {loai: 'TRANGTHAI_SD', option: ''}
        )
        this.dsTRANGTHAI_SD = rs.data

      } catch (error) {
      } finally {
      }
    },
    clearForm(){
      this.ketCuoiDoiThu = {}
      this.diaChiKetCuoiDoiThu = {}

      this.dsChuQuan = []
      this.dsKHUVUC = []
      this.dsPHANLOAI_KC = []
      this.dsLOAI_KC = []
      this.dsKIEU_LAP = []
      this.dsNOI_SX = []
      this.dsVITRI = []
      this.dsLOAI_VO = []
      this.dsDUNGLUONG = []
      this.dsTOQL = []
      this.dsNVQL = []
      this.dsTRANGTHAI_SD = []
    },
    async validateData () {
      this.$v.$touch()
      let errorMessage = []
      if (this.$v.$invalid && !this.$v.ketCuoiDoiThu.KYHIEU.required) {
        errorMessage.push('Ký hiệu không được để trống')
        this.$refs['KYHIEU'].focus()
      }else {
        if (!this.$v.ketCuoiDoiThu.KYHIEU.maxLength || !this.$v.ketCuoiDoiThu.KYHIEU.minLength){
          errorMessage.push('Ký hiệu không được nhập quá 250 ký tự')
          this.$refs['KYHIEU'].focus()
        }
        //kiem tra tồn tại kyhieu
        await this.$root.context.post("/web-cabman/phantu_mang/kiemtra-unique-ketcuoi-doithu",{fieldname:"KYHIEU",value:this.ketCuoiDoiThu.KYHIEU, ketcuoiId:this.ketCuoiDoiThu.KETCUOI_ID})
        .then(res=>{
          if (res.data != null && res.data.result === false){
            this.$refs['KYHIEU'].focus()
            errorMessage.push('Ký hiệu đã tồn tại')
          }
        }).catch(e => errorMessage.push("Lỗi xảy ra khi kiểm tra tồn tại Ký hiệu"))

      }
      if (!this.$v.ketCuoiDoiThu.TEN_KC.maxLength || !this.$v.ketCuoiDoiThu.TEN_KC.minLength) {
        this.$refs['TEN_KC'].focus()
        errorMessage.push('Tên kết cuối không được nhập quá 250 ký tự')
      }
      if (this.$v.$invalid && !this.$v.ketCuoiDoiThu.LOAIKC_ID.required) {
        this.$refs['LOAIKC_ID'].focus()
        errorMessage.push('Loại kết cuối không được để trống')
      }
      if (this.$v.$invalid && !this.$v.ketCuoiDoiThu.KIEULAP_ID.required) {
        this.$refs['KIEULAP_ID'].focus()
        errorMessage.push('Kiểu lắp không được để trống')
      }

      if (this.$v.$invalid && !this.$v.ketCuoiDoiThu.VITRI_ID.required) {
        this.$refs['VITRI_ID'].focus()
        errorMessage.push('Vị trí không được để trống')
      }
      if (parseFloat(this.ketCuoiDoiThu.CHIEUDAI+'') >= 100){
        this.$refs['CHIEUDAI'].focus()
        errorMessage.push('Trường Chiều dài dữ liệu vượt quá độ dài cho phép')
      }
      if (parseFloat(this.ketCuoiDoiThu.CHIEURONG+'') >= 100){
        this.$refs['CHIEURONG'].focus()
        errorMessage.push('Trường Chiều rộng dữ liệu vượt quá độ dài cho phép')
      }
      if (parseFloat(this.ketCuoiDoiThu.CHIEUCAO+'') >= 100){
        this.$refs['CHIEUCAO'].focus()
        errorMessage.push('Trường Chiều cao dữ liệu vượt quá độ dài cho phép')
      }
      if (!this.$v.ketCuoiDoiThu.DIACHI.required) {
        this.$refs['DIACHI'].focus()
        errorMessage.push('Địa chỉ không được để trống')
      }
      if (!this.$v.ketCuoiDoiThu.DIACHI.maxLength || !this.$v.ketCuoiDoiThu.DIACHI.minLength) {
        this.$refs['DIACHI'].focus()
        errorMessage.push('Địa chỉ phải nằm trong khoản từ 1 đến 400 ký tự')
      }
      if (this.$v.$invalid && !this.$v.ketCuoiDoiThu.TONGSO_DD.required) {
        this.$refs['TONGSO_DD'].focus()
        errorMessage.push('Dung lượng không được để trống')
      }
      if (this.$v.$invalid && !this.$v.ketCuoiDoiThu.SODOI_SD.required) {
        this.$refs['SODOI_SD'].focus()
        errorMessage.push('Số đôi đang dùng không được để trống')
      }
      if (this.$v.$invalid && !this.$v.ketCuoiDoiThu.TTSD_ID.required) {
        this.$refs['TTSD_ID'].focus()
        errorMessage.push('Trạng thái sử dụng không được để trống')
      }
      if (parseFloat(this.ketCuoiDoiThu.KINHDO+'') >= 1000000){
        this.$refs['KINHDO'].focus()
        errorMessage.push('Trường Kinh độ dữ liệu vượt quá độ dài cho phép')
      }
      if (parseFloat(this.ketCuoiDoiThu.VIDO+'') >= 1000000){
        this.$refs['VIDO'].focus()
        errorMessage.push('Trường Vĩ độ dữ liệu vượt quá độ dài cho phép')
      }
      if (errorMessage.length > 0) {
        this.$toast.error(errorMessage.join('\n'))
        return true
      }else {
        this.$v.$reset()
        return false
      }
    },
  },
  validations: {
    // validate theo app mobile
    ketCuoiDoiThu:
      {
        KYHIEU: {
          required,
          minLength: minLength(1),
          maxLength: maxLength(250)
        },
        TEN_KC: {
          minLength: minLength(0),
          maxLength: maxLength(250)
        },
        LOAIKC_ID: {
          required
        },
        KIEULAP_ID: {
          required
        },
        VITRI_ID: {
          required
        },
        CHIEUDAI:{
          minLength: minLength(0),
          maxLength: maxLength(5)
        },
        CHIEURONG:{
          minLength: minLength(0),
          maxLength: maxLength(5)
        },
        CHIEUCAO:{
          minLength: minLength(0),
          maxLength: maxLength(5)
        },
        DIACHI: {
          required,
          minLength: minLength(1),
          maxLength: maxLength(400)
        },
        SODOI_SD: {
          required
        },
        TONGSO_DD: {
          required
        },
        TTSD_ID: {
          required
        },
        KINHDO:{
          minLength: minLength(0),
          maxLength: maxLength(16)
        },
        VIDO:{
          minLength: minLength(0),
          maxLength: maxLength(16)
        },
      }
  },
}
</script>

<style scoped>

</style>
