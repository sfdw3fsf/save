<template src="./Template.html"></template>
<style scoped>
.popNVTC {
  z-index: 9999;
}
</style>
<script>
import api from './api.js'
import { LoaiHinhTB, DichVuVienThong, LoaiHopDong, TrangThaiHD } from '@/const/enums/index.js'
export default {
  components: {},
  name: 'UpdateModal',
  props: {
    data: {
      type: Object,
      default: {}
    }
  },
  data() {
    return {
      loading: false,
      dataModal: this.data,
      dataUpdate: {
        THUEBAO_ID: 0,
        MA_TB: '',
        NHANVIEN_ID: 0,
        DONVI_ID: 0,
        TRANGTHAI_ID: 0,
        NGAY_DK: '',
        NGAY_HT: '',
        GHICHU: '',
        NGAY_CN: '',
        NGUOI_CN: '',
        MAY_CN: '',
        CUOC_TC: '',
        CUOC_TK: '',
        CUOC_TBI: '',
        CUOC_HT: '',
        CUOC_TKDT: '',
        CUOC_TCDT: '',
        TEN_TBI: '',
        BRAS_ID: '',
        DSLAM_ID: '',
        PORT_ID: 0,
        VCI_VPI_ID: 0,
        SLOT: '',
        PORT: '',
        TIMESLOT: '',
        LINK: '',
        IP_WAN: '',
        VRF: '',
        TUYENCAP: '',
        LIENTU: '',
        LOAICAP_ID: '',
        TUCAP_GOC: '',
        DOICAP_GOC: '',
        CAP_GOC: '',
        TUCAP_P: '',
        HOPCAP_P: '',
        DOICAP_P: '',
        CAP_P: '',
        CAP_ID: '',
        DOICAP: '',
        SERIAL: '',
        CULY: '',
        CUOC_TC_CU: '',
        CUOC_TK_CU: '',
        CUOC_TBI_CU: '',
        CUOC_HT_CU: '',
        CUOC_TKDT_CU: '',
        CUOC_TCDT_CU: '',
        TEN_TBI_CU: '',
        BRAS_ID_CU: '',
        DSLAM_ID_CU: '',
        PORT_ID_CU: 0,
        VCI_VPI_ID_CU: 0,
        SLOT_CU: '',
        PORT_CU: '',
        TIMESLOT_CU: '',
        LINK_CU: '',
        IP_WAN_CU: '',
        VRF_CU: '',
        TUYENCAP_CU: '',
        LIENTU_CU: '',
        LOAICAP_ID_CU: '',
        TUCAP_GOC_CU: '',
        DOICAP_GOC_CU: '',
        CAP_GOC_CU: '',
        TUCAP_P_CU: '',
        HOPCAP_P_CU: '',
        DOICAP_P_CU: '',
        CAP_P_CU: '',
        CAP_ID_CU: '',
        DOICAP_CU: '',
        SERIAL_CU: '',
        CULY_CU: '',
        DAUCUOI_ID: 0,
        TBIDC_DAU_ID: '',
        TBIDC_DAU_ID_CU: '',
        TBIDC_CUOI_ID: '',
        TBIDC_CUOI_ID_CU: '',
        TDTT_DB_ID: '',
        HDTB_ID: this.vhdtb_id,
        MA_PL: '',
        MA_PL_CU: '',
        NHAN_HD: '',
        KETCUOI_ID_CU: '',
        KETCUOI_ID: '',
        VITRI_CU: '',
        VITRI: '',
        KIEU: '',
        NGUON_YC: '',
        CHUQUANLD_ID: '',
        CHUQUAN_ID: '',
        CHUQUAN_ID_CU: '',
        CHUQUANLD_ID_CU: '',
        TEN_KH_CU: '',
        TEN_KH: '',
        DIACHI_ID_CU: '',
        DIACHI_ID: '',
        TOCDO_ID_CU: '',
        TOCDO_ID: '',
        MA_VTT_CU: '',
        MA_VTT: '',
        VTT_ID: '',
        VTT_ID_CU: '',
        MA_HD: '',
        MA_HD_CU: ''
      },
      hienTai: {
        thueKenh: 0,
        thueCong: 0,
        haTang: 0,
        thietBi: 0
      },
      dieuChinh: {
        thueKenh: 0,
        thueCong: 0,
        haTang: 0,
        thietBi: 0
      }
    }
  },
  mounted() {},
  watch: {
    loading: function (val, oldval) {
      this.$root.showLoading(val)
    }
  },
  methods: {
    GetData: function (response) {
      if (response.data.error === 200 || response.data.error === '200') {
        return response.data.data
      } else {
        console.log(response.data.error_code + ' - ' + response.data.message)
      }
      return []
    },
    showModal() {
      this.form_load()
      this.$refs.popupCapNhatCTK.show()
    },
    hideModal() {
      this.$refs.popupCapNhatCTK.hide()
    },
    async btnCapNhat_Click() {
      try {
        if (this.hienTai.haTang.toString() == '') {
          this.$toast.warning('Nhập sai dữ liệu Cước hạ tầng cũ !')
          this.$refs.hatanght.focus()
          return
        }
        if (!this.isNan(this.hienTai.haTang)) {
          this.$toast.warning('Dữ liệu tiền phải là kiểu số !')
          this.$refs.hatanght.focus()
          return
        }
        if (this.dieuChinh.haTang.toString() == '') {
          this.$toast.warning('Nhập sai dữ liệu Cước hạ tầng mới !')
          this.$refs.hatangdc.focus()
          return
        }
        if (!this.isNan(this.dieuChinh.haTang)) {
          this.$toast.warning('Dữ liệu tiền phải là kiểu số !')
          this.$refs.hatangdc.focus()
          return
        }
        if (this.hienTai.thietBi.toString() == '') {
          this.$toast.warning('Nhập sai dữ liệu Cước thiết bị cũ !')
          this.$refs.thietbiht.focus()
          return
        }
        if (!this.isNan(this.hienTai.thietBi)) {
          this.$toast.warning('Dữ liệu tiền phải là kiểu số !')
          this.$refs.thietbiht.focus()
          return
        }
        if (this.dieuChinh.thietBi.toString() == '') {
          this.$toast.warning('Nhập sai dữ liệu Cước thiết bị mới !')
          this.$refs.thietbidc.focus()

          return
        }
        if (!this.isNan(this.dieuChinh.thietBi)) {
          this.$toast.warning('Dữ liệu tiền phải là kiểu số !')
          this.$refs.thietbidc.focus()
          return
        }
        if (this.hienTai.thueCong.toString() == '') {
          this.$toast.warning('Nhập sai dữ liệu Cước thuê cổng cũ !')
          this.$refs.thueconght.focus()
          return
        }
        if (!this.isNan(this.hienTai.thueCong)) {
          this.$toast.warning('Dữ liệu tiền phải là kiểu số !')
          this.$refs.thueconght.focus()
          return
        }
        if (this.dieuChinh.thueCong.toString() == '') {
          this.$toast.warning('Nhập sai dữ liệu Cước thuê cổng mới !')
          this.$refs.thuecongdc.focus()
          return
        }
        if (!this.isNan(this.dieuChinh.thueCong)) {
          this.$toast.warning('Dữ liệu tiền phải là kiểu số !')
          this.$refs.thuecongdc.focus()
          return
        }
        if (this.hienTai.thueKenh.toString() == '') {
          this.$toast.warning('Nhập sai dữ liệu Cước thuê kênh cũ !')
          this.$refs.thuekenhht.focus()
          return
        }
        if (!this.isNan(this.hienTai.thueKenh)) {
          this.$toast.warning('Dữ liệu tiền phải là kiểu số !')
          this.$refs.thuekenhht.focus()
          return
        }
        if (this.dieuChinh.thueKenh.toString() == '') {
          this.$toast.warning('Nhập sai dữ liệu Cước thuê kênh mới !')
          this.$refs.thuekenhdc.focus()
          return
        }
        if (!this.isNan(this.dieuChinh.thueKenh)) {
          this.$toast.warning('Dữ liệu tiền phải là kiểu số !')
          this.$refs.thuekenhdc.focus()
          return
        }
        if (this.dataModal.vdichvuvt_id == DichVuVienThong.MEGAWAN || this.dataModal.vdichvuvt_id == DichVuVienThong.METRONET) {
          await this.TaoDuLieu_Update()
          this.Insert_TDTT_MGWAN()
        } else if (this.dataModal.vdichvuvt_id == DichVuVienThong.TSL) {
          await this.TaoDuLieu_Update()
          this.Insert_TDTT_TSL()
        }
      } catch (error) {
        this.$toast.error('Có lỗi: ' + error)
      }
    },
    isNan(n) {
      return !isNaN(parseFloat(n)) && !isNaN(n - 0)
    },
    async TaoDuLieu_Update() {
      let tdtt_db_id = await this.getKeys('TDTT_DB_ID')
      this.dataUpdate.TDTT_DB_ID = tdtt_db_id
      this.dataUpdate.HDTB_ID = this.dataModal.vhdtb_id
      let vthuebao_id = await this.fn_tt_hd_thuebao(this.vhdtb_id)
      if (vthuebao_id != '0' && vthuebao_id != '-1') this.dataMGWAN.THUEBAO_ID = parseInt(vthuebao_id)
      else this.dataUpdate.THUEBAO_ID = 0
      this.dataUpdate.MA_TB = this.dataModal.vma_tb
      this.dataUpdate.NHANVIEN_ID = this.$root.token.getNhanVienID()
      this.dataUpdate.DONVI_ID = this.$root.token.getDonViID()
      this.dataUpdate.TRANGTHAI_ID = 2

      this.dataUpdate.CUOC_TC = parseFloat(this.dieuChinh.thueCong)
      this.dataUpdate.CUOC_TC_CU = parseFloat(this.hienTai.thueCong)
      this.dataUpdate.CUOC_TK = parseFloat(this.dieuChinh.thueKenh)
      this.dataUpdate.CUOC_TK_CU = parseFloat(this.hienTai.thueKenh)
      this.dataUpdate.CUOC_HT = parseFloat(this.dieuChinh.haTang)
      this.dataUpdate.CUOC_HT_CU = parseFloat(this.hienTai.haTang)
      this.dataUpdate.CUOC_TBI = parseFloat(this.dieuChinh.thietBi)
      this.dataUpdate.CUOC_TBI_CU = parseFloat(this.hienTai.thietBi)

      this.dataUpdate.MAY_CN = this.$root.token.getMachine() ? this.$root.token.getMachine() : ''
      this.dataUpdate.NGAY_CN = moment(new Date()).format('DD/MM/YYYY HH:mm:ss')
      this.dataUpdate.NGUOI_CN = this.$root.token.getUserName() ? this.$root.token.getUserName() : ''
    },
    //267 /web-tratruoc/thongtin_tratruoc_dn/fn_tt_hd_thuebao
    async fn_tt_hd_thuebao(para) {
      let data = this.GetData(
        await api.fn_tt_hd_thuebao(this.axios, {
          param: para, 
          type: 5 
        })
      )
      return data
    },

    async Insert_TDTT_MGWAN() {
      try {
        let para = {
          dstdtt_mgwan: JSON.stringify(this.dataUpdate),
          dichvuvt_id: 8,
          cuoc_tk: 1,
          cuoc_tc: 1,
          cuoc_ht: 1,
          cuoc_tbi: 1
        }
        let data = this.GetData(await api.fn_insert_tdtt_mgwan_hdtb_mgwan(this.axios, para))
        if(data == 1){
            this.$toast.success('Cập nhật dữ liệu thành công !')
        }else{
           this.$toast.error(data)
        }
      } catch (error) {
        this.$toast.error('lỗi: ' + error.data.message_detail)
      }
    },
    async Insert_TDTT_TSL() {
      try {
        let para = {
          dstdtt_tsl: JSON.stringify(this.dataUpdate),
          dichvuvt_id: 8,
          cuoc_tk: 1,
          cuoc_tc: 1,
          cuoc_ht: 1,
          cuoc_tbi: 1
        }
        let data = this.GetData(await api.fn_insert_tdtt_tsl_hdtb_tsl(this.axios, para))
        if(data == 1){
            this.$toast.success('Cập nhật dữ liệu thành công !')
        }else{
           this.$toast.error(data)
        }
      } catch (error) {
        this.$toast.error('lỗi: ' + error.data.message_detail)
      }
    },
    async getKeys(keyname) {
      try {
        const response = await api.getKeys(this.axios, { keyname, numblocksize: 0, numretry: 0 })
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : ''
      } catch (e) {
        this.$root.$toast.error('Không thể lấy key')
        return []
      }
    },
    async form_load() {
      try {
        if (this.dataModal) {
          let ds = []
          ds = this.LAY_DS_HDTB_TSL_THEO_HDTB_ID("s")
          if (this.dataModal.vdichvuvt_id == DichVuVienThong.MEGAWAN || this.dataModal.vdichvuvt_id == DichVuVienThong.METRONET)
              ds = this.LAY_DS_HDTB_MGWAN_THEO_HDTB_ID(this.dataModal.vhdtb_id);
          else if (this.dataModal.vdichvuvt_id == DichVuVienThong.TSL)
              ds = this.LAY_DS_HDTB_TSL_THEO_HDTB_ID(this.dataModal.vhdtb_id);
          if (ds && ds.length > 0) {
            var itemData = ds[0]
            if (itemData.cuoc_tk) this.hienTai.thueKenh = this.dieuChinh.thueKenh = parseFloat(itemData.cuoc_tk).toLocaleString()
            else this.hienTai.thueKenh = this.dieuChinh.thueKenh = 0
            if (itemData.cuoc_tc) this.hienTai.thueCong = this.dieuChinh.thueCong = parseFloat(itemData.cuoc_tc).toLocaleString()
            else this.hienTai.thueCong = this.dieuChinh.thueCong = 0
            if (itemData.cuoc_ht) this.hienTai.haTang = this.dieuChinh.haTang = parseFloat(itemData.cuoc_ht).toLocaleString()
            else this.hienTai.haTang = this.dieuChinh.haTang = 0
            if (itemData.cuoc_tbi) this.hienTai.thietBi = this.dieuChinh.thietBi = parseFloat(itemData.cuoc_tbi).toLocaleString()
            else this.hienTai.thietBi = this.dieuChinh.thietBi = 0
          } else {
            this.$toast.warning('Không tìm thấy thông tin hợp đồng !')
          }
        }
      } catch (error) {
        this.$toast.error('có lỗi: ' + error)
      }
    },
    async LAY_DS_HDTB_MGWAN_THEO_HDTB_ID(vhdtb_id) {
      try {
        let para = {
          hdtb_id: 4938380
        }
        let data = this.GetData(await api.lay_ds_hdtb_mgwan_theo_hdtb_id(this.axios, para))
        return data
      } catch (error) {
        this.$toast.error('lỗi: ' + error.data.message_detail)
      }
    },
    async LAY_DS_HDTB_TSL_THEO_HDTB_ID(vhdtb_id) {
      try {
        let para = {
          hdtbId: 2085048
        }
        let data = this.GetData(await api.lay_ds_hopdong_thuebao_tls(this.axios, para))
        return data
      } catch (error) {
        this.$toast.error('lỗi: ' + error.data.message_detail)
      }
    }
  }
}
</script>