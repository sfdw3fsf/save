<template src="./index.html"></template>
<script>
import BssRequiredMarker from '@/components/BssRequiredMarker'
import ModalChonToaDo from '../ChonToaDo'
import ModalChonDiaChi from '../ChonDiaChi/Modal'
import ModalAnhChup from "../AnhChup/Modal";

export default {
  components: {
    BssRequiredMarker, ModalChonToaDo, ModalChonDiaChi, ModalAnhChup
  },
  name: "QuanLyKetCuoi",
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
      loai_ket_cuoi: 0, // mac dinh
      dataSelected: {

      },

      diachi:{}, // bien truyen sang form dia chi


      dsChuQuan:[],
      dsPHANLOAI_KC:[],
      dsLOAI_KC:[],
      dsKIEU_LAP:[],
      dsNOI_SX:[],
      dsVITRI:[],
      dsLOAI_VO:[],
      dsTOQL:[],
      dsTRANGTHAI_SD:[],
      dsDuAn:[],
      dsTaiSan:[],
      dsToaNha:[],

    }
  },
  watch:{
    'dataSelected.DUAN_ID': async function (val) {
      if (val){
        await this.$root.context.get(
          '/web-cabman/ban-do-mang-cap/danh-muc', {loai: 'TAISAN', option: this.dataSelected.DUAN_ID === null ? 0 : this.dataSelected.DUAN_ID}
        ).then(res => {
          this.dsTaiSan = res.data
        })
      }
    },
  },
  computed: {
    getPosition () {
      return {
        lat: parseFloat(this.dataSelected.VIDO),
        lng: parseFloat(this.dataSelected.KINHDO)
      }
    },
    getDiachi () {
      return this.dataSelected.DIACHI
    },
    getKetCuoi(){
      this.dataSelected.CHUQUAN_ID = 1 // VNPT
      return this.dataSelected
    }
  },
  beforeDestroy() {
    // console.log("beforeDestroy")
    // this.$ref.ngayld.cleared = true
    // this.$ref.ngaysd.cleared = true
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
        this.loading(true)
        await this.thongtinKetCuoi()
        await this.initForm()
      }catch (e){}
      finally {
        this.loading(false)
      }
    },
    btnChonDiaChi(){
      this.diachi.diachi = this.dataSelected.DIACHI
      this.diachi.tinh_id = this.dataSelected.TINH_ID
      this.diachi.quan_id = this.dataSelected.QUAN_ID
      this.diachi.phuong_id = this.dataSelected.PHUONG_ID
      this.diachi.pho_id = this.dataSelected.PHO_ID
      this.diachi.ap_id = this.dataSelected.AP_ID
      this.diachi.khu_id = this.dataSelected.KHU_ID
      this.diachi.dacdiem_id = this.dataSelected.DACDIEM_ID
      this.diachi.so_nha = this.dataSelected.SONHA
      this.$bvModal.show('popupDiaChi')
    },
    getDiaChiTuFormDiaChi: function (data) {
      this.dataSelected.DIACHI = data.diachimoi;
      this.dataSelected.TINH_ID = data.tinh_id;
      this.dataSelected.QUAN_ID = data.quan_id;
      this.dataSelected.PHUONG_ID = data.phuong_id;
      this.dataSelected.PHO_ID = data.pho_id;
      this.dataSelected.AP_ID = data.ap_id;
      this.dataSelected.KHU_ID = data.khu_id;
      this.dataSelected.DACDIEM_ID = data.dacdiem_id;
      this.dataSelected.SONHA = data.so_nha;
    },
    thongtinKetCuoi: async function () {
      try {
        await this.$root.context.get(
          '/web-cabman/ketcuoi/layThongtinKetCuoi?ketcuoi_id=' + this.ketCuoiObject.KETCUOI_ID
        ).then(res =>{
          if (res.data != null) { this.dataSelected = res.data }
        }).catch(e =>{this.$root.toastError("Có lỗi xảy ra khi tải thông tin kết cuối")})
      } catch (error) {
      } finally {
      }
    },
    XacNhanToaDo (position) {
      this.dataSelected.VIDO = position.lat
      this.dataSelected.KINHDO = position.lng
    },
    initForm: async function () {
      try {
        rs = await this.$root.context.get(
          '/web-cabman/ban-do-mang-cap/danh-muc', {loai: 'CHUQUAN', option: ''}
        )
        this.dsChuQuan = rs.data

        var rs = await this.$root.context.get(
          '/web-cabman/ban-do-mang-cap/danh-muc', {loai: 'PHANLOAI_KC', option: ''}
        )
        this.dsPHANLOAI_KC = rs.data

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
          '/web-cabman/ban-do-mang-cap/danh-muc', {loai: 'TRANGTHAI_SD', option: ''}
        )
        this.dsTRANGTHAI_SD = rs.data

        rs = await this.$root.context.post(
          '/web-cabman/danhmuc/layDsDuAn',
        )
        this.dsDuAn = rs.data

        rs = await this.$root.context.get(
          '/web-cabman/danhmuc/lay-ds-toanha',
        )
        this.dsToaNha = rs.data

      } catch (error) {
      } finally {
      }
    },
    clearForm(){
      this.dataSelected = {}
      this.dsChuQuan = []
      this.dsPHANLOAI_KC = []
      this.dsLOAI_KC = []
      this.dsKIEU_LAP = []
      this.dsNOI_SX = []
      this.dsVITRI = []
      this.dsLOAI_VO = []
      this.dsTOQL = []
      this.dsTRANGTHAI_SD = []
      this.dsDuAn = []
      this.dsTaiSan = []
    }
  }
}
</script>

<style scoped>

</style>
