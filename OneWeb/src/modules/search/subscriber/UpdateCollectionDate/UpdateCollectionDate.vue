<template src="./UpdateCollectionDate.html"></template>
<style src="./UpdateCollectionDate.scss"></style>
<script>
import XLSX from 'xlsx'
import moment from 'moment'
import breadcrumb from '@/components/breadcrumb'
import api from './api.js'
import DatePicker from 'vue2-datepicker'
import { LoaiHinhTB, DichVuVienThong, LoaiHopDong, TrangThaiHD } from '@/const/enums/index.js'
import UpdateModal from './popups/Update/UpdateModal.vue'
export default {
  components: {
    XLSX,
    moment,
    breadcrumb,
    DatePicker,
    UpdateModal
  },
  name: 'UpdateCollectionDate',
  data() {
    return {
      loading: false,
      dateFormat: 'DD/MM/YYYY',
      dateFormat2:'DD/MM/YYYY HH:mm:ss',
      dateFormatMonth: 'MM/YYYY',
      typeFormat: 'datetime',
      typeFormatMonth: 'month',
      dateConfig: {
        dateFormat: 'd/m/Y',
        altFormat: 'd/m/Y'
      },
      loaiHD_PL: {
        checkbox: false,
        checked: 0,
        list: []
      },
      dichVu: {
        checkbox: false,
        checked: 0,
        list: []
      },
      trangThai: {
        checkbox: false,
        checked: 0,
        list: []
      },
      ngayTruyThu: {
        val: moment(new Date()).format('DD/MM/YYYY HH:mm:ss'),
        Enabled: true
      },
      maGD: '',
      maHDTTKD: '',
      soMayACC: {
        checked: 0,
        list: []
      },
      infoTraCuu: '',
      inputNgayYC: {
        checkbox: false,
        val: moment(new Date()).format('DD/MM/YYYY'),
        Enabled: true
      },
      showTimePanel: false,
      inputDenNgay: {
        val: moment(new Date()).format('DD/MM/YYYY'),
        Enabled: true
      },
      header: {
        title: 'Cập nhật ngày truy thu Megawan/Metronet/KTR',
        list: [
          { name: 'Lập hợp đồng', link: { name: 'Ui.cards' } },
          { name: 'Lắp đặt mới', link: { name: 'Ui.cards' } }
        ]
      },
      headerGridbox: [
        { fieldName: 'stt', headerText: 'Stt', allowFiltering: true, allowSorting: true, textAlign: 'center', width: 100 },
        { fieldName: 'ma_tb', headerText: 'Số máy/Acc', allowFiltering: true, allowSorting: true },
        { fieldName: 'ma_lt', headerText: 'Số ảo', allowFiltering: true, allowSorting: true },
        { fieldName: 'ngay_truythu', headerText: 'Ngày truy thu', allowFiltering: true, allowSorting: true },
        { fieldName: 'ten_tb', headerText: 'Tên thuê bao', allowFiltering: true, allowSorting: true },
        { fieldName: 'diachi_tb', headerText: 'Địa chỉ thuê bao', allowFiltering: true, allowSorting: true }, // KO BT
        { fieldName: 'ten_dvvt', headerText: 'Dịch vụ', allowFiltering: true, allowSorting: true },
        { fieldName: 'trangthai_hd', headerText: 'Trạng thái', allowFiltering: true, allowSorting: true },
        { fieldName: 'dv_ky', headerText: 'Đơn vị ký', allowFiltering: true, allowSorting: true },
        { fieldName: 'tocdo', headerText: 'Tốc độ kênh', allowFiltering: true, allowSorting: true },
        { fieldName: 'diachi_ld', headerText: 'Địa chỉ LĐ', allowFiltering: true, allowSorting: true },
        { fieldName: 'status', headerText: 'Status', allowFiltering: true, allowSorting: true },
        { fieldName: 'nhan_hd', headerText: 'Nhận HĐ', allowFiltering: true, allowSorting: true },
        { fieldName: 'ngay_nhanhd', headerText: 'Ngày nhận HĐ', allowFiltering: true, allowSorting: true }
      ],
      gridBox: {
        data: [],
        selected: null
      },
      headerGridboxKH: [
        { fieldName: 'stt', headerText: 'Stt', allowFiltering: true, allowSorting: true, textAlign: 'center', width: 130 },
        { fieldName: 'ma_gd', headerText: 'Mã giao dịch', allowFiltering: true, allowSorting: true },
        { fieldName: 'ngay_yc', headerText: 'Ngày YC', allowFiltering: true, allowSorting: true, textAlign: 'center' },
        { fieldName: 'ma_kh', headerText: 'Mã khách hàng', allowFiltering: true, allowSorting: true },
        { fieldName: 'ten_kh', headerText: 'Tên khách hàng', allowFiltering: true, allowSorting: true },
        { fieldName: 'diachi_kh', headerText: 'Địa chỉ khách hàng', allowFiltering: true, allowSorting: true }, // KO BT
        { fieldName: 'so_dt', headerText: 'Số ĐT', allowFiltering: true, allowSorting: true },
        { fieldName: 'ten_loaihd', headerText: 'Loại HĐ/PL', allowFiltering: true, allowSorting: true }
      ],
      gridBoxKH: {
        data: [],
        selected: null
      },
      allowDelete: false,
      vdichvuvt_id: 0,
      vhdtb_id: 0,
      vloaitb_id: 0,
      sloaihd_id: 0,
      vtthd_id: 0,
      mahd_ttkd: '',
      vma_tb: '',
      dataMGWAN: {
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
      dataModel: {
        vhdtb_id: 0,
        vma_tb: '',
        vdichvuvt_id: 0,
        isload: false
      }
    }
  },
  mounted() {
    this.form_load()
  },
  created() {},
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
        console.log(response.data.error_code)
      }
      return []
    },
    async form_load() {
      this.loading = true
      //cb loai hop dong
      let data = this.GetData(await api.cb_loaiHD(this.axios))
      if (data && data.length > 0) {
        this.loaiHD_PL.list = data.map((x) => ({
          id: x.LOAIHD_ID,
          text: x.TEN_LOAIHD
        }))
        this.loaiHD_PL.checked = 1
      }
      // cb dich vu
      let datadv = this.GetData(await api.cb_dichvu(this.axios))
      if (datadv && datadv.length > 0) {
        this.dichVu.list = datadv.map((x) => ({
          id: x.DICHVUVT_ID,
          text: x.TEN_DVVT
        }))
        this.dichVu.checked = 1
      }
      // cb trang thai
      let datatt = this.GetData(await api.cb_trangthai(this.axios))
      if (datatt && datadv.length > 0) {
        this.trangThai.list = datatt.map((x) => ({
          id: x.TTHD_ID,
          text: x.TRANGTHAI_HD
        }))
        this.trangThai.checked = datatt[0].TTHD_ID
      }
      // cb dk tra cuu tb
      let para = {
        p_schema: 'admin',
        p_table_name: 'kieu_tk',
        p_value: 'dieukien',
        p_text: 'kieu_tk',
        p_where: 'nhom_id =' + 1,
        p_order: 'thutu'
      }
      let datadk = this.GetData(await api.cb_dkTraCuu(this.axios, para))
      if (datadk && datadk.length > 0) {
        this.soMayACC.list = datadk.map((x) => ({
          id: x.ID,
          text: x.NAME
        }))
        this.soMayACC.checked = datadk[0].ID
      }
      this.loading = false
    },
    async get_dsHDTB(hdkh_id) {
      let para = {
        in_hdkh_id: hdkh_id
      }
      let data = this.GetData(await api.hienthi_ds_hdtb(this.axios, para))
      if (data.length > 0) {
        let ds = []
        data.forEach((element, index) => {
          element.stt = index + 1
          if(element.ngay_truythu != '' && element.ngay_truythu != null){
              element.ngay_truythu = moment(new Date(element.ngay_truythu)).format(this.dateFormat2)
          }
          ds.push(element)
        })
        this.gridBox.data = ds
      } else {
        this.gridBox.data = data
      }
      this.loading = false
    },
    btnSearch_Click() {
      let dieukien = ''
      let tthd_id = 0
      let loaihd_id = 0
      let dichvuvt_id = 0
      let vtungay_yc = ''
      let vdenngay_yc = ''
      let dk_tracuu = {
        status: false,
        val: ''
      }
      let itemtracuu = this.soMayACC.list.filter((item) => {
        return item.id === this.soMayACC.checked
      })
      if (itemtracuu && itemtracuu[0].text == 'Số ảo') {
        dk_tracuu.status = true
        dk_tracuu.val = 'Số ảo'
      } else if (itemtracuu && itemtracuu[0].text == 'Site ID') {
        dk_tracuu.status = true
        dk_tracuu.val = 'Site ID'
      }
      if (this.infoTraCuu) {
        dieukien = this.soMayACC.checked.replace(':values', this.infoTraCuu)
      } else {
        if (itemtracuu && itemtracuu[0].text == 'Site ID') {
          this.$toast.warning('Bạn chưa nhập thông tin Site ID để tra cứu !')
        }
      }
      if (this.loaiHD_PL.checkbox) {
        loaihd_id = parseInt(this.loaiHD_PL.checked)
      }
      if (this.dichVu.checkbox) {
        dichvuvt_id = parseInt(this.dichVu.checked)
      }
      if (this.trangThai.checkbox) {
        tthd_id = parseInt(this.trangThai.checked)
      }
      if (this.inputNgayYC.checkbox) {
        vtungay_yc = this.inputNgayYC.val
        vdenngay_yc = this.inputDenNgay.val
      }
      this.tra_cuu(dk_tracuu.status, dk_tracuu.val, dieukien, loaihd_id, tthd_id, 0, 0, 0, dichvuvt_id, vtungay_yc, vdenngay_yc, 0)
    },
    async btnUpdate_Click() {
      try {
        let dshdtb_truythu = {
          HDTB_ID: this.vhdtb_id,
          NGAY_TRUYTHU: this.ngayTruyThu.val,
          MAY_CN: await this.$root.token.getMachine() ? await this.$root.token.getMachine() : 'localhost',
          NGAY_CN: moment(new Date()).format('DD/MM/YYYY HH:mm:ss'),
          NGUOI_CN: this.$root.token.getUserName() ? this.$root.token.getUserName() : ''
        }
        let para = {
          p_js_hdtb_truythu: JSON.stringify(dshdtb_truythu)
        }
        if (this.allowDelete == false) {
          var check = await this.check_data(1)
          if (check) {
            try {
              this.GetData(await api.ps_insert_hdtb_truythu(this.axios, para))
              this.$toast.success('Cập nhật dữ liệu thành công !')
              this.btnSearch_Click()
            } catch (error) {
              this.$toast.error('lỗi: ' + error.data.message_detail)
            }
          }
        } else {
          var check = await this.check_data(1)
          if (check) {
            try {
              this.GetData(await api.ps_update_hdtb_truythu(this.axios, para))
              this.$toast.success('Cập nhật dữ liệu thành công !')
              this.btnSearch_Click()
            } catch (error) {
              this.$toast.error('lỗi: ' + error.data.message_detail)
            }
          }
        }
      } catch (error) {
        this.$toast.error('lỗi: ' + error)
      }
    },
    async btnDelete_Click() {
      var check = await this.check_data(3)
      if (check) {
        try {
          await api.ps_delete_hdtb_truythu(this.axios, { p_hdtb_id: this.vhdtb_id })
          this.btnSearch_Click()
          this.$toast.success('Xóa dữ liệu truy thu thành công !')
        } catch (error) {
          this.$toast.error('lỗi: ' + error.data.message_detail)
        }
      }
    },
    async btnUpdateMaHD_Click() {
      try {
        if (this.mahd_ttkd == this.maHDTTKD.trim()) {
          this.$toast.warning('Mã HĐ TTKD cũ và mới giống nhau !')
          return
        }
        if (!this.kt_capnhat_nhan_mahd_ttkd()) {
          return
        }
        if (this.vdichvuvt_id == DichVuVienThong.MEGAWAN) {
          this.TaoDuLieu_Insert_TDTT(1)
          this.Insert_TDTT(1)
        } else if (this.vdichvuvt_id == DichVuVienThong.TSL) {
          this.TaoDuLieu_Insert_TDTT(1)
          this.Insert_TDTT(2)
        }
        this.$toast.success('Cập nhật Mã HĐ - TTKD thành công !')
      } catch (error) {
        this.$toast.error('lỗi: ' + error.data.message_detail)
      }
    },
    async btnNhanHD_Click() {
      try {
        let nhan_hd = ''
        if (this.vdichvuvt_id == DichVuVienThong.MEGAWAN) nhan_hd = await this.fn_tt_hdtb_mgwan(this.vhdtb_id, 3)
        else if (this.vdichvuvt_id == DichVuVienThong.TSL) nhan_hd = await this.fn_tt_hdtb_tsl(this.vhdtb_id, 4)
        if (nhan_hd == '1') {
          this.$toast.warning('Hợp đồng thuê bao đã được nhận hồ sơ !')
          return
        }
        if (!this.kt_capnhat_nhan_mahd_ttkd()) {
          return
        }
        if (this.vdichvuvt_id == DichVuVienThong.MEGAWAN) {
          this.TaoDuLieu_Insert_TDTT(2)
          this.Insert_TDTT(1)
        } else if (this.vdichvuvt_id == DichVuVienThong.TSL) {
          this.TaoDuLieu_Insert_TDTT(2)
          this.Insert_TDTT(2)
        }
        let para = {
          p_dichvuvt_id: this.vdichvuvt_id,
          p_hdtb_id: this.vhdtb_id,
          p_nhan_hd: 1
        }
        await api.sp_update_nhanhd_megawan_tsl(this.axios, para)
        this.$toast.success('Nhận hồ sơ thành công !')
      } catch (error) {
        this.$toast.error('lỗi: ' + error.data.message_detail)
      }
    },
    async btnHuyNhanHD_Click() {
      try {
        let nhan_hd = ''
        if (this.vdichvuvt_id == DichVuVienThong.MEGAWAN) nhan_hd = await this.fn_tt_hdtb_mgwan(this.vhdtb_id, 3)
        else if (this.vdichvuvt_id == DichVuVienThong.TSL) nhan_hd = await this.fn_tt_hdtb_tsl(this.vhdtb_id, 4)
        if (nhan_hd == '0') {
          this.$toast.warning('Hợp đồng thuê bao chưa được nhận hồ sơ !')
          return
        }
        if (!this.kt_capnhat_nhan_mahd_ttkd()) {
          return
        }
        if (this.vdichvuvt_id == DichVuVienThong.MEGAWAN) {
          this.TaoDuLieu_Insert_TDTT(3)
          this.Insert_TDTT(1)
        } else if (this.vdichvuvt_id == DichVuVienThong.TSL) {
          this.TaoDuLieu_Insert_TDTT(3)
          this.Insert_TDTT(2)
        }
        let para = {
          p_dichvuvt_id: this.vdichvuvt_id,
          p_hdtb_id: this.vhdtb_id,
          p_nhan_hd: 0
        }
        await api.sp_update_nhanhd_megawan_tsl(this.axios, para)
        this.$toast.success('Hủy nhận hồ sơ thành công !')
      } catch (error) {
        this.$toast.error('lỗi: ' + error.data.message_detail)
      }
    },
    async btnCapNhatCTK_Click() {
      try {
        if (this.sloaihd_id != LoaiHopDong.DAT_MOI && this.sloaihd_id != LoaiHopDong.THAY_DOI_TOCDO_TSL && this.sloaihd_id != LoaiHopDong.THAYDOI_THONGSO_MEGAWAN && this.sloaihd_id != LoaiHopDong.CHUYENDOI_LH) {
          this.$toast.warning("Không được cập nhật Cước Megawan - TSL cho loại hợp đồng khác 'Đặt mới, Chuyển đổi loại hình, Thay đổi thông số Megawan, Thay đổi tốc độ TSL'")
          return
        }
        if (this.vtthd_id == TrangThaiHD.THOAITRA || this.vtthd_id == TrangThaiHD.HOAN_THANH) {
          this.$toast.warning('Hợp đồng đã Thoái trả hoặc hoàn thành. Bạn không thể cập nhật !')
          return
        }
        this.dataModel.vhdtb_id = this.vhdtb_id
        this.dataModel.vma_tb = this.vma_tb
        this.dataModel.vdichvuvt_id = this.vdichvuvt_id
        this.dataModel.isload = true
        this.$refs.UpdateCTK_CTCModal.showModal()
      } catch (error) {
        this.$toast.error('lỗi: ' + error.data.message_detail)
      }
    },
    async check_data(vkieu) {
      let arrparams = {
        HDTB_ID: this.vhdtb_id,
        DICHVUVT_ID: this.vdichvuvt_id,
        LOAITB_ID: this.vloaitb_id,
        LOAIHD_ID: this.sloaihd_id,
        TTHD_ID: this.vtthd_id,
        DTP_NGAYTRUYTHU:this.ngayTruyThu.val,
        KIEU: vkieu
      }
      let para = {
        p_ds_para: JSON.stringify(arrparams)
      }
      try {
        let data = this.GetData(await api.check_data(this.axios, para))
        var dataEr = JSON.parse(data)
        if (dataEr.ERROR_CODE == 0) {
          this.$toast.warning(dataEr.MESSAGE)
          return false
        } else if (dataEr.ERROR_CODE == 1) {
          return true
        }
      } catch (error) {
        this.$toast.error('lỗi: ' + error.data.message_detail)
      }
    },
    kt_capnhat_nhan_mahd_ttkd() {
      if (this.vhdtb_id == 0) {
        this.$toast.warning('Không có dữ liệu hợp đồng thuê bao !')
        return false
      }
      if (this.vdichvuvt_id != DichVuVienThong.MEGAWAN && this.vdichvuvt_id != DichVuVienThong.TSL) {
        this.$toast.warning('Không được cập nhật ngày truy thu cho dịch vụ khác Megawan/Metronet/KTR !')
        return false
      }
      return true
    },
    async TaoDuLieu_Insert_TDTT(vkieu) {
      let tdtt_db_id = await this.getKeys('TDTT_DB_ID')
      this.dataMGWAN.TDTT_DB_ID = tdtt_db_id
      this.dataMGWAN.HDTB_ID = this.vhdtb_id
      let vthuebao_id = await this.fn_tt_hd_thuebao(this.vhdtb_id)
      if (vthuebao_id != '0' && vthuebao_id != '-1') this.dataMGWAN.THUEBAO_ID = parseInt(vthuebao_id)
      else this.dataMGWAN.THUEBAO_ID = 0
      this.dataMGWAN.MA_TB = this.vma_tb
      this.dataMGWAN.NHANVIEN_ID = this.$root.token.getNhanVienID()
      this.dataMGWAN.DONVI_ID = this.$root.token.getDonViID()
      this.dataMGWAN.TRANGTHAI_ID = 2
      if (vkieu == 1) {
        this.dataMGWAN.MA_PL = this.mahd_ttkd
        this.dataMGWAN.MA_PL_CU = this.maHDTTKD.trim()
      } else if (vkieu == 2) this.dataMGWAN.NHAN_HD = '1'
      else if (vkieu == 3) this.dataMGWAN.NHAN_HD = '0'

      ;(this.dataMGWAN.MAY_CN = this.$root.token.getMachine() ? this.$root.token.getMachine() : ''), (this.dataMGWAN.NGAY_CN = moment(new Date()).format('DD/MM/YYYY HH:mm:ss')), (this.dataMGWAN.NGUOI_CN = this.$root.token.getUserName() ? this.$root.token.getUserName() : '')
    },
    async selectedItemsChangedGridbox(item) {
      if (item[0]) {
        this.vdichvuvt_id = item[0].dichvuvt_id
        this.vhdtb_id = item[0].hdtb_id
        this.vtthd_id = item[0].tthd_id
        this.vloaitb_id = item[0].loaitb_id
        this.vma_tb = item[0].ma_tb
        if (item[0].ngay_truythu != '' && item[0].ngay_truythu != null) {
          this.ngayTruyThu.val = item[0].ngay_truythu
          this.allowDelete = true
        } else {
          this.ngayTruyThu.val = moment(new Date()).format(this.dateFormat2)
          this.allowDelete = false
        }
        if (this.vdichvuvt_id == DichVuVienThong.MEGAWAN || this.vdichvuvt_id == DichVuVienThong.TSL) {
          this.mahd_ttkd = ''
          if (this.vdichvuvt_id == DichVuVienThong.MEGAWAN) this.mahd_ttkd = await this.fn_tt_hdtb_mgwan(this.vhdtb_id, 1)
          else this.mahd_ttkd = await this.fn_tt_hdtb_tsl(this.vhdtb_id, 3)
          if (this.mahd_ttkd == '-1') this.mahd_ttkd = ''

          this.maHDTTKD = this.mahd_ttkd
        } else this.maHDTTKD = ''
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
    
    //436 - type 3 /web-tratruoc/thongtin_tratruoc_dn/fn_tt_hdtb_mgwan
    //466 - type 3 /web-tratruoc/thongtin_tratruoc_dn/fn_tt_hdtb_mgwan
    //584 - type 1 /web-tratruoc/thongtin_tratruoc_dn/fn_tt_hdtb_mgwan
    async fn_tt_hdtb_mgwan(para, ptype) {
      let data = this.GetData(
        await api.fn_tt_hdtb_mgwan(this.axios, {
          param: para, 
          type: ptype 
        })
      )
      return data
    },
    
    
    //437 - type 4 /web-tratruoc/thongtin_tratruoc_dn/fn_tt_hdtb_tsl
    //467 - type 4 /web-tratruoc/thongtin_tratruoc_dn/fn_tt_hdtb_tsl
    //585 - type 3 /web-tratruoc/thongtin_tratruoc_dn/fn_tt_hdtb_tsl
    async fn_tt_hdtb_tsl(para, ptype) {
      let data = this.GetData(
        await api.fn_tt_hdtb_tsl(this.axios, {
          param: para, 
          type: ptype 
        })
      )
      return data
    },

    //553 /web-tratruoc/thongtin_tratruoc_dn/fn_tt_hd_thuebao
    async fn_tt_hd_thuebao(para) {
      let data = this.GetData(
        await api.fn_tt_hd_thuebao(this.axios, {
          param: para, 
          type: 5 
        })
      )
      return data
    },

    selectedItemsChangedGridboxKH(item) {
      if (item[0]) {
        this.loading = true
        this.sloaihd_id = item[0].loaihd_id
        this.get_dsHDTB(item[0].hdkh_id)
      }
    },
    disabledRange: function (date) {
      return date > new Date()
    },
    toggleTimePanel() {
      this.showTimePanel = !this.showTimePanel
    },
    handleOpenChange() {
      this.showTimePanel = false
    },
    handleRangeClose() {
      this.showTimeRangePanel = false
    },
    async tra_cuu(status, dk_tracuu, dieukien, loaihd_id, tthd_id, donvi_id, nhanvien_id, donvi_dl_id, dichvuvt_id, vtungay_yc, vdenngay_yc, kt_inhd) {
      let data = []
      this.loading = true
      if (status) {
        let arrparams = {
          DK_TRACUU: dk_tracuu,
          DICHVUVT_ID: dichvuvt_id,
          DIEUKIEN: dieukien,
          LOAIHD_ID: loaihd_id,
          TTHD_ID: tthd_id,
          DONVI_ID: donvi_id,
          NHANVIEN_ID: nhanvien_id,
          DONVI_DL_ID: donvi_dl_id,
          TUNGAY_YC: vtungay_yc,
          DENNGAY_YC: vdenngay_yc,
          KT_INHD: kt_inhd
        }
        let params = {
          p_ds_para: JSON.stringify(arrparams)
        }
        //call api kết hợp
        try {
          data = this.GetData(await api.hienthi_ds_kh_kethop(this.axios, params))
        } catch (error) {
          this.$toast.error('lỗi: ' + error.data.message_detail)
        }
      } else {
        let params = {
          vdieukien: dieukien,
          vloaihd_id: loaihd_id,
          vtthd_id: tthd_id,
          vdonvi_id: donvi_id,
          vnhanvien_id: nhanvien_id,
          vdonvi_dl_id: donvi_dl_id,
          vdichvuvt_id: dichvuvt_id,
          vtungay_yc: vtungay_yc,
          vdenngay_yc: vdenngay_yc,
          vin_hd: kt_inhd
        }
        try {
          data = this.GetData(await api.hienthi_ds_kh(this.axios, params))
        } catch (error) {
          this.$toast.error('lỗi: ' + error.data.message_detail)
        }
      }
      if (data && data.length > 0) {
        let ds = []
        data.forEach((element, index) => {
          element.stt = index + 1
          element.ngay_yc = moment(new Date(element.ngay_yc)).format(this.dateFormat)
          ds.push(element)
        })
        this.gridBoxKH.data = ds
      } else {
        this.gridBoxKH.data = data
        this.gridBox.data = []
      }
      this.loading = false
    },
    async Insert_TDTT(vkieu) {
      //vkieu = 1: mgwan; vkieu = 2: tsl
      if (vkieu == 1) {
        let params = {
          p_js_tdtt_db_mgwan: JSON.stringify(this.dataMGWAN),
          p_txt_mahd: this.maHDTTKD.trim(),
          p_hdtb_id: this.vhdtb_id
        }
        try {
          await api.ps_insert_tdtt_db_mgwan(this.axios, params)
        } catch (error) {
          this.$toast.error('lỗi: ' + error.data.message_detail)
        }
      }
      if (vkieu == 2) {
        let params = {
          p_js_tdtt_db_tsl: JSON.stringify(this.dataMGWAN),
          p_txt_mahd: this.maHDTTKD.trim(),
          p_hdtb_id: this.vhdtb_id
        }
        try {
          await api.ps_insert_tdtt_db_tsl(this.axios, params)
        } catch (error) {
          this.$toast.error('lỗi: ' + error.data.message_detail)
        }
      }
    }
  }
}
</script>
