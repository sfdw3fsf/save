<template src="./index.html"></template>
<style src="./styles.scss" scoped></style>

<script>
import API from '../api/DeXuatGiamTruCuocAPI'
import APICommon from '../api/APICommon'
import frmHoSoKhieuNai from '@/modules/complaint/SearchComplainDetail/popups/frmHoSoKhieuNai.vue'
import ChenNo from '@/modules/EXTS/KHIEUNAI/dexuat-giamtru-cuoc/ChenNo.vue'
import moment from 'moment'
import GiaoPhieuGTC from '@/modules/EXTS/KHIEUNAI/giaophieu-dexuat-gtc/index.vue'
import {OPTIONS, TRANGTHAI_DX} from '../Common/Constant'
import ChiTietKhieuNai from '../ChiTietKhieuNai/ChiTiet.vue'

export default {
  name: 'DeXuatGiamTruCuocKN',
  components: {
    frmHoSoKhieuNai,
    ChenNo,
    GiaoPhieuGTC,
    ChiTietKhieuNai
  },
  data() {
    return {
      currentItem: {
        // Thông tin khiếu nại
        KHIEUNAI_ID: null,
        PHIEUKN_ID: null,
        MA_KN: '',
        NGAY_KN: null,
        DICHVUVT_ID: 0,
        TEN_DVVT: '',
        LOAIHINH_TB_ID: 0,
        LOAIHINH_TB_TEN: '',
        NGUOI_KN: '',
        DIENTHOAI_LH: '',
        CHUDEKN_ID: 0,
        CHUDEKN_TEN: '',
        NOIDUNG_KN: '',
        GHICHU_KN: '',

        // Thông tin khách hàng
        TEN_KH: '',
        MA_KH: '',
        MST: '',      //Mã số thuế
        MA_TB: '',
        MA_TT: '',    //Mã ngân sách - Mã thanh toán
        TEN_TB: '',
        TEN_TT: '',
        MA_HD: '',    //Số hợp đồng
        CUOC_TB: 0,   //Giá trị HD
        NGAYLAP_HD: null,

        // Thông tin xác minh
        DS_KETQUA_XACMINH: [],
        XACMINH_KHAC: '',
        // ĐX GTC - 16
        DX_GTC: {
          LOAIPHIEU: '',
          HTTL_ID: null,
          YKIEN: '',
          NGAY_TH: null,
          NGAYGIAO: null,
          NV_TH_ID: null,
          NV_TH_TEN: null,
          GHI_CHU: null,
          DONVI_GIAO: null,
        },
        // Phê duyệt ĐX GTC - 17
        PHEDUYET_DX_GTC: {
          LOAIPHIEU: '',
          HTTL_ID: null,
          YKIEN: '',
          NGAY_TH: null,
          NGAYGIAO: null,
          NV_TH_ID: null,
          NV_TH_TEN: null,
          GHI_CHU: null,
          DONVI_GIAO: null,
        },
        // Thẩm định ĐX GTC - 18
        THAMDINH_DX_GTC: {
          LOAIPHIEU: '',
          HTTL_ID: null,
          YKIEN: '',
          NGAY_TH: null,
          NGAYGIAO: null,
          NV_TH_ID: null,
          NV_TH_TEN: null,
          GHI_CHU: null,
          DONVI_GIAO: null,
        },
        // Phê duyệt KQ Thẩm định ĐX GTC - 19
        PHEDUYET_KQTD: {
          LOAIPHIEU: '',
          HTTL_ID: null,
          YKIEN: '',
          NGAY_TH: null,
          NGAYGIAO: null,
          NV_TH_ID: null,
          NV_TH_TEN: null,
          GHI_CHU: null,
          DONVI_GIAO: null,
        },
        // Phê duyệt GTC - 20
        PHEDUYET_GTC: {
          LOAIPHIEU: '',
          HTTL_ID: null,
          YKIEN: '',
          NGAY_TH: null,
          NGAYGIAO: null,
          NV_TH_ID: null,
          NV_TH_TEN: null,
          GHI_CHU: null,
          DONVI_GIAO: null,
        },
        //DS thông tin ĐX GTC
        DS_DEXUAT_GTC: [],
      },
      showTabs: {
        XM: true,
        GP: true,
      },
      yKienTDOptions: [],
      tabNavigate: {
        activetab: OPTIONS.NAV_TAB.CHUA_GIAO,
        OTPChuaGiao: OPTIONS.KN_CHUAGIAO.TAT_CA,
        OTPDaGiao: OPTIONS.KN_DAGIAO.TAT_CA,
      },
      dsPhieuChuaGiao: [],
      dsPhieuDaGiao: [],
      buttons: {
        disableTaoMoi: false,
        disableGhiLai: true,
        disableSua: true,
        disableHuy: true
      },
      PADieuChinhHDOptions: [],
      PAXuLyDiLieuOptions: [],
      noiDungKhoaPhieu: '',
    }
  },
  mounted: async function() {
    const ma_kn = this.$route.query.MaKN != null ? this.$route.query.MaKN : ''
    if (ma_kn != '') {
      this.currentItem.MA_KH = ma_kn
      await this.maKN_keyPress()
    }
  },
  computed: {
    grdItemsChuaGiao: function() {
      return this.$refs.grdItemsChuaGiao
    },
    grdItemsDaGiao: function() {
      return this.$refs.grdItemsDaGiao
    },
  },
  created: async function() {
    try {
      this.$root.showLoading(true)
      // Load danh sách dịch vụ
      this.PADieuChinhHDOptions = await this.apiGetDSPADieuChinhHoaDon()
      this.PAXuLyDiLieuOptions = await this.apiGetDSPAXuLyDuLieu()
    } finally {
      this.$root.showLoading(false)
    }
  },

  methods: {
    async apiGetDSGiamTruCuoc(params) {
      this.loading(true)
      let dsResult = []
      try {
        const response = await API.getDSGiamTruCuoc(this.axios, params)
        if (response.data.error_code === 'BSS-00000000') {
          dsResult = response.data.data
        } else {
          dsResult = []
        }
      } catch (error) {
        console.log('apiGetDSGiamTruCuoc error: ', error)
        dsResult = []
      } finally {
        this.loading(false)
      }
      return dsResult
    },
    async apiGetDSPADieuChinhHoaDon() {
      this.loading(true)
      let dsResult = []
      try {
        const response = await API.getDSPADieuChinhHoaDon(this.axios)
        if (response.data.error_code === 'BSS-00000000') {
          dsResult = response.data.data
        } else {
          dsResult = []
        }
      } catch (error) {
        console.log('apiGetDSPADieuChinhHoaDon error: ', error)
        dsResult = []
      } finally {
        this.loading(false)
      }
      return dsResult
    },
    async apiGetDSPAXuLyDuLieu() {
      this.loading(true)
      let dsResult = []
      try {
        const response = await API.getDSPAXuLyDuLieu(this.axios)
        if (response.data.error_code === 'BSS-00000000') {
          dsResult = response.data.data
        } else {
          dsResult = []
        }
      } catch (error) {
        console.log('getDSPAXuLyDuLieu error: ', error)
        dsResult = []
      } finally {
        this.loading(false)
      }
      return dsResult
    },
    async apiGetDXGiamTruCuocTheoTT(params) {
      this.loading(true)
      let dsResult = []
      try {
        const response = await APICommon.getDXGiamTruCuocTheoTT(this.axios, params)
        if (response.data.error_code === 'BSS-00000000') {
          dsResult = response.data.data
        } else {
          dsResult = []
        }
      } catch (error) {
        dsResult = []
      } finally {
        this.loading(false)
      }
      return dsResult
    },
    async apiGetChiTietDXGiamTruCuocTheoMaKN(params) {
      this.loading(true)
      let resultDetail = null
      try {
        const response = await APICommon.getChiTietDXGiamTruCuocTheoMaKN(this.axios, params)
        if (response.data.error_code === 'BSS-00000000') {
          resultDetail = response.data.data[0]
        }
      } catch (error) {
        console.log('error', error);
        this.$root.toastError(error.data.message)
      } finally {
        this.loading(false)
        return resultDetail
      }
    },
    async apiCreateUpdatePhieuDeXuatGTC(params) {
      this.loading(true)
      let result = true
      try {
        const response = await APICommon.createOrUpdatePhieuDeXuatGTC(this.axios, params)
        if (response.data.error_code === 'BSS-00000000') {
          this.$root.toastSuccess('Lưu thông tin ĐX thành công!')
        } else {
          this.$root.toastError('Lưu thông tin ĐX không thành công!')
          result = false
        }
      } catch (error) {
        result = false
        console.log('error', error);
        this.$root.toastError(error.data.message)
      } finally {
        this.loading(false)
      }
      return result;
    },
    async apiXoaPhieu(params) {
      this.loading(true)
      let result = true
      try {
        const response = await APICommon.xoaPhieuDeXuatGiamTruCuoc(this.axios, params)
        if (response.data.error_code === 'BSS-00000000') {
          this.$root.toastSuccess('Xóa phiếu đề xuất giảm trừ cước thành công!')
        } else {
          this.$root.toastError('Xóa phiếu đề xuất giảm trừ cước thất bại!')
          result = false
        }
      } catch (error) {
        result = false
        console.log('error', error);
        this.$root.toastError(error.data.message)
      } finally {
        this.loading(false)
      }
      return result;
    },
    async apiKhoaPhieu(params) {
      this.loading(true)
      let result = true
      try {
        const response = await APICommon.khoaPhieuDeXuatGiamTruCuoc(this.axios, params)
        if (response.data.error_code === 'BSS-00000000') {
          this.$root.toastSuccess('Khóa phiếu đề xuất giảm trừ cước thành công!')
        } else {
          this.$root.toastError('Khóa phiếu đề xuất giảm trừ cước thất bại!')
          result = false
        }
      } catch (error) {
        result = false
        console.log('error', error);
        this.$root.toastError(error.data.message)
      } finally {
        this.loading(false)
      }
      return result;
    },
    clearForm() {
      this.currentItem = {
        // Thông tin khiếu nại
        KHIEUNAI_ID: null,
        PHIEUKN_ID: null,
        MA_KN: '',
        NGAY_KN: null,
        DICHVUVT_ID: 0,
        TEN_DVVT: '',
        LOAIHINH_TB_ID: 0,
        LOAIHINH_TB_TEN: '',
        NGUOI_KN: '',
        DIENTHOAI_LH: '',
        CHUDEKN_ID: 0,
        CHUDEKN_TEN: '',
        NOIDUNG_KN: '',
        GHICHU_KN: '',
        // Thông tin khách hàng
        TEN_KH: '',
        MA_KH: '',
        MST: '',      //Mã số thuế
        MA_TB: '',
        MA_TT: '',    //Mã ngân sách - Mã thanh toán
        TEN_TB: '',
        TEN_TT: '',
        MA_HD: '',    //Số hợp đồng
        CUOC_TB: 0,   //Giá trị HD
        NGAYLAP_HD: null,

        // Thông tin xác minh
        DS_KETQUA_XACMINH: [],
        XACMINH_KHAC: '',

        DX_GTC: {
          LOAIPHIEU: '',
          HTTL_ID: null,
          YKIEN: '',
          NGAY_TH: null,
          NGAYGIAO: null,
          NV_TH_ID: null,
          NV_TH_TEN: null,
          GHI_CHU: null,
          DONVI_GIAO: null,
        },
        // Phê duyệt ĐX GTC - 17
        PHEDUYET_DX_GTC: {
          LOAIPHIEU: '',
          HTTL_ID: null,
          YKIEN: '',
          NGAY_TH: null,
          NGAYGIAO: null,
          NV_TH_ID: null,
          NV_TH_TEN: null,
          GHI_CHU: null,
          DONVI_GIAO: null,
        },
        // Thẩm định ĐX GTC - 18
        THAMDINH_DX_GTC: {
          LOAIPHIEU: '',
          HTTL_ID: null,
          YKIEN: '',
          NGAY_TH: null,
          NGAYGIAO: null,
          NV_TH_ID: null,
          NV_TH_TEN: null,
          GHI_CHU: null,
          DONVI_GIAO: null,
        },
        // Phê duyệt KQ Thẩm định ĐX GTC - 19
        PHEDUYET_KQTD: {
          LOAIPHIEU: '',
          HTTL_ID: null,
          YKIEN: '',
          NGAY_TH: null,
          NGAYGIAO: null,
          NV_TH_ID: null,
          NV_TH_TEN: null,
          GHI_CHU: null,
          DONVI_GIAO: null,
        },
        // Phê duyệt GTC - 20
        PHEDUYET_GTC: {
          LOAIPHIEU: '',
          HTTL_ID: null,
          YKIEN: '',
          NGAY_TH: null,
          NGAYGIAO: null,
          NV_TH_ID: null,
          NV_TH_TEN: null,
          GHI_CHU: null,
          DONVI_GIAO: null,
        },
        //DS thông tin ĐX GTC
        DS_DEXUAT_GTC: [],
      }
    },
    onLoadButton() {
      this.buttons = {
        disableTaoMoi: this.tabNavigate.activetab == OPTIONS.NAV_TAB.DA_GIAO,
        disableGhiLai: true,
        disableSua: this.tabNavigate.activetab == OPTIONS.NAV_TAB.DA_GIAO || this.dsPhieuChuaGiao.length == 0,
        disableHuy: true
      }
    },
    onLayDanhSach: async function (value) {
      this.clearForm()
      if (value) {
        if (value == OPTIONS.NAV_TAB.CHUA_GIAO) {
          this.dsPhieuChuaGiao = await this.apiGetDXGiamTruCuocTheoTT({
            pTTKNId: TRANGTHAI_DX.DX_GTC,
            pChuaGiao: OPTIONS.NAV_TAB.CHUA_GIAO,
            pTTPHId: this.tabNavigate.OTPChuaGiao,      
          })
        } else {
          this.dsPhieuDaGiao = await this.apiGetDXGiamTruCuocTheoTT({
          pTTKNId: TRANGTHAI_DX.DX_GTC,
          pChuaGiao: OPTIONS.NAV_TAB.DA_GIAO,
          pTTPHId: this.tabNavigate.OTPDaGiao,          
        })
        }
      } else {
        this.dsPhieuChuaGiao = await this.apiGetDXGiamTruCuocTheoTT({
          pTTKNId: TRANGTHAI_DX.DX_GTC,
          pChuaGiao: OPTIONS.NAV_TAB.CHUA_GIAO,
          pTTPHId: this.tabNavigate.OTPChuaGiao,      
        })
        this.dsPhieuDaGiao = await this.apiGetDXGiamTruCuocTheoTT({
          pTTKNId: TRANGTHAI_DX.DX_GTC,
          pChuaGiao: OPTIONS.NAV_TAB.DA_GIAO,
          pTTPHId: this.tabNavigate.OTPDaGiao,          
        })
      }
      this.onLoadButton()
    },
    onclickTab: async function (currentActivetab) {
      try {
        this.loading(true)
        this.tabNavigate.activetab = currentActivetab
        await this.onLayDanhSach(currentActivetab)
      } catch (error) {
        this.$root.toastError(' ' + error)
      } finally {
        this.loading(false)
      }
    },
    onTaoMoi() {
      this.buttons.disableTaoMoi = true
      this.buttons.disableHuy = false
      this.buttons.disableGhiLai = false
      this.buttons.disableSua = true
      this.clearForm()
    },
    onEdit() {
      if (!this.currentItem.KHIEUNAI_ID) {
        return this.$root.toastError('Bạn cần chọn phiếu đề xuất trước!')
      }
      this.buttons.disableTaoMoi = true
      this.buttons.disableGhiLai = false
      this.buttons.disableSua = true
      this.buttons.disableHuy = false
    },
    async onHuy() {
      await this.onLayDanhSach()
    },
    async onDelete() {
      if (!this.currentItem.KHIEUNAI_ID) {
        return this.$root.toastError('Bạn cần chọn phiếu ĐX GTC trước!')
      }
      if (!this.currentItem.CUR_TTKN_ID || this.currentItem.CUR_TTKN_ID != TRANGTHAI_DX.DX_GTC) {
        return this.$root.toastError('Trạng thái phiếu không hợp lệ!')
      }
      const flag = await this.$confirm(`Bạn có muốn xóa ĐX GTC mã khiếu nại: <span class='red'> ${this.currentItem.MA_KN} </span> ?`, 'Xác nhận thực hiện', {
        confirmButtonText: 'Có',
        cancelButtonText: 'Không',
        type: 'info',
        dangerouslyUseHTMLString: true,
      })
      .then(() => true)
      .catch(() => false)

      if (!flag) return;
      const ketQua = await this.apiXoaPhieu({
          pKhieuNaiId: this.currentItem.KHIEUNAI_ID,
          pPhieuKNId: this.currentItem.PHIEUKN_ID
      })
      if (ketQua) {
        await this.onLayDanhSach()
      } 
    },
    async onSave() {
      let valid = true
      if (!this.currentItem.KHIEUNAI_ID) {
        this.$root.toastError('Khiếu nại ID của ĐX GTC là bắt buộc!')
        valid = false
      }
      if (!this.currentItem.DX_GTC.GHI_CHU || this.currentItem.DX_GTC.GHI_CHU.trim() == '') {
        this.$root.toastError('Nội dung ĐX GTC là bắt buộc!')
        valid = false
      }
      if (!valid) return;

      const flag = await this.$confirm('Bạn có muốn lưu thông tin đề xuất?', 'Xác nhận thực hiện', {
        confirmButtonText: 'Có',
        cancelButtonText: 'Không',
        type: 'info'
      })
      .then(() => true)
      .catch(() => false)

      if (!flag) return;

      const params = {
        pKhieuNaiId: this.currentItem.KHIEUNAI_ID,
        pPhieuKNId: this.currentItem.PHIEUKN_ID,
        pYKien: OPTIONS.YKIEN_DEXUAT[0].ID, //Đồng ý
        pNoiDung: this.currentItem.DX_GTC.GHI_CHU,
      }
      const response = await this.apiCreateUpdatePhieuDeXuatGTC(params);
      if (response) await this.onLayDanhSach()
    },
    async onGiaoPhieu() {
      try {
        this.loading(true)
        this.$refs.frmGiaoPhieuGTC.pTTKNId = TRANGTHAI_DX.DX_GTC
        this.$refs.frmGiaoPhieuGTC.show()
      } catch (e) {
        console.log('onGiaoPPhieu error', e);
      } finally {
        this.loading(false)
      }
    },
    async onTuChoi() {
      if (!this.currentItem.PHIEUKN_ID) {
        return this.$root.toastError('Khiếu nại chưa ĐX GTC!')
      }
      const flag = await this.$confirm('Bạn có muốn từ chối ĐX GTC không?', 'Xác nhận thực hiện', {
        confirmButtonText: 'Có',
        cancelButtonText: 'Không',
        type: 'info'
      })
      .then(() => true)
      .catch(() => false);

      if (!flag) return;
      
      const params = {pPhieuKNId: this.currentItem.PHIEUKN_ID}
      const res = await this.apiTuChoiDeXuatGiamTruCuoc(params)
      if (res) await this.onLayDanhSach()
    },
    async onXemHoSo() {
      if (!this.currentItem.KHIEUNAI_ID) {
        return this.$root.toastError('Bạn cần chọn phiếu để xem hồ sơ!')
      }
      this.$refs.frmHoSoKhieuNai.khieunai_id = this.currentItem.KHIEUNAI_ID
      this.$refs.frmHoSoKhieuNai.show()
    },
    async onKhoaPhieu() {
      this.noiDungKhoaPhieu = ''
      if (!this.currentItem.KHIEUNAI_ID) {
        return this.$toast.error('Bạn chưa chọn phiếu để khóa!')
      }
      if (!this.currentItem.PHIEUKN_ID) {
        return this.$toast.error('Bạn chưa chọn phiếu để khóa!')
      }
      this.$bvModal.show('popupKhoaPhieu')      
    },
    async onXacNhanKhoaPhieu() {
      if (!this.noiDungKhoaPhieu || this.noiDungKhoaPhieu.trim() == '') {
        return this.$toast.error('Bạn chưa nhập nội dung khóa phiếu!')
      }
      const result = await this.apiKhoaPhieu({
        pKhieuNaiId: this.currentItem.KHIEUNAI_ID,
        pPhieuKNId: this.currentItem.PHIEUKN_ID,
        pNoiDung: this.noiDungKhoaPhieu
      })
      if (result) {
        this.$bvModal.hide('popupKhoaPhieu')
        await this.onLayDanhSach()
      }
    },    
    onHuyKhoaPhieu() {
      this.$bvModal.hide('popupKhoaPhieu')
    },    
    onXemChiTietKhieuNai() {
      if (!this.currentItem.KHIEUNAI_ID) return;
      this.$refs.dialogChiTietKhieuNai.onGetData(1)
      this.$refs.dialogChiTietKhieuNai.dialogOpen()
    },   
    async onChangePhieuDXChuaGiao(item) {
      if (!item || this.tabNavigate.activetab != OPTIONS.NAV_TAB.CHUA_GIAO) return;
      const params = {
        pMaKN: item.MA_KN,
        pTTKNId: item.TTKN_ID,
      }
      const response = await this.apiGetChiTietDXGiamTruCuocTheoMaKN(params)
      if (response) {
         this.responseDetailToCurrentItem(response)
         this.currentItem = response
      }
    }, 
    async onChangePhieuDXDaGiao(item) {
      if (!item || this.tabNavigate.activetab != OPTIONS.NAV_TAB.DA_GIAO) return;
      const params = {
        pMaKN: item.MA_KN,
        pTTKNId: item.TTKN_ID,
      }
      const response = await this.apiGetChiTietDXGiamTruCuocTheoMaKN(params)
      if (response) {
         this.responseDetailToCurrentItem(response)
         this.currentItem = response
      }     
    },
    async maKNKeyPress() {
      if (!this.currentItem.MA_KN || this.currentItem.MA_KN.trim() == '') {
        return this.$root.toastError('Bạn cần nhập mã KN để tìm kiếm!')
      }
      const params = {
        pMaKN: this.currentItem.MA_KN,
        pTTKNId: TRANGTHAI_DX.DX_GTC,
      }
      const response = await this.apiGetChiTietDXGiamTruCuocTheoMaKN(params)
      if (response) {
         this.responseDetailToCurrentItem(response)
         this.currentItem = response
      } 
    },
    responseDetailToCurrentItem(response) {
      response.DS_KETQUA_XACMINH = response.DS_KETQUA_XACMINH ? JSON.parse(response.DS_KETQUA_XACMINH) : [];
      response.DS_DEXUAT_GTC = response.DS_DEXUAT_GTC ? JSON.parse(response.DS_DEXUAT_GTC) : [];
      const dsThongTinPhieuDX = JSON.parse(response.DS_PHIEU_DX);
      if (dsThongTinPhieuDX && dsThongTinPhieuDX.length > 0) {
        response.DX_GTC = dsThongTinPhieuDX.find(i => i.TTKN_ID == 16) || {}
        response.PHEDUYET_DX_GTC = dsThongTinPhieuDX.find(i => i.TTKN_ID == 17) || {}
        response.THAMDINH_DX_GTC = dsThongTinPhieuDX.find(i => i.TTKN_ID == 18) || {}
        response.PHEDUYET_KQTD = dsThongTinPhieuDX.find(i => i.TTKN_ID == 19) || {}
        response.PHEDUYET_GTC = dsThongTinPhieuDX.find(i => i.TTKN_ID == 20) || {}
      } else {
        response.DX_GTC = {}
        response.PHEDUYET_DX_GTC = {}
        response.THAMDINH_DX_GTC = {}
        response.PHEDUYET_KQTD = {}
        response.PHEDUYET_GTC = {}
      } 
    },
    async btnChenNo_Click() {
      if (!this.currentItem.KHIEUNAI_ID) {
        return this.$toast.error('Không có thông tin khiếu nại!')
      }
      this.$refs.refChenNo.khieunai_id = this.currentItem.KHIEUNAI_ID 
      this.$refs.refChenNo.openDialog()
    },
    responseDetailToCurrentItem(response) {
      response.DS_KETQUA_XACMINH = response.DS_KETQUA_XACMINH ? JSON.parse(response.DS_KETQUA_XACMINH) : [];
      response.DS_DEXUAT_GTC = response.DS_DEXUAT_GTC ? JSON.parse(response.DS_DEXUAT_GTC) : [];
      const dsThongTinPhieuDX = JSON.parse(response.DS_PHIEU_DX);
      if (dsThongTinPhieuDX && dsThongTinPhieuDX.length > 0) {
        response.DX_GTC = dsThongTinPhieuDX.find(i => i.TTKN_ID == 16) || {}
        response.PHEDUYET_DX_GTC = dsThongTinPhieuDX.find(i => i.TTKN_ID == 17) || {}
        response.THAMDINH_DX_GTC = dsThongTinPhieuDX.find(i => i.TTKN_ID == 18) || {}
        response.PHEDUYET_KQTD = dsThongTinPhieuDX.find(i => i.TTKN_ID == 19) || {}
        response.PHEDUYET_GTC = dsThongTinPhieuDX.find(i => i.TTKN_ID == 20) || {}
      } else {
        response.DX_GTC = {}
        response.PHEDUYET_DX_GTC = {}
        response.THAMDINH_DX_GTC = {}
        response.PHEDUYET_KQTD = {}
        response.PHEDUYET_GTC = {}
      } 
    },
    async popupGiaoPhieuClosed() {
      this.onLayDanhSach()
    },   
    onEditGTC: function(data) {
      console.log('onEditGTC', data)
    },
    onDeleteGTC: function(data) {
      console.log('onDeleteGTC', data)
    },
    getColumnTemplateChucNang(parent) {
      return function() {
        return {
          template: {
            template: `<div style="text-align: center" v-if="true">
                          <button class="btn btn-main pad4 lh14" @click="parent.onEditGTC(data)">
                            <span class="icon fa fa-pencil"></span>
                          </button>
                          <button class="btn btn-main pad4 lh14" @click="parent.onDeleteGTC(data)">
                            <span class="icon one-trash"></span>
                          </button>
                      </div>`,
            data() {
              return {
                parent: parent,
                data: this.data || {}
              }
            },
            watch: {},
            computed: {},
            methods: {}
          }
        }
      }
    },
    getColTemplateDieuChinhHoaDon(parent) {
      return function() {
        return {
          template: {
            template: `
          <div style="text-align:center">
            <SelectExt
              v-model="value"
              :dataSource="dataSource"
              dataTextField="TEXT"
              dataValueField="ID"
              :allowFiltering="false"
            />
          </div>`,
            data() {
              return {
                parent,
                dataSource: [],
                value: null,
                data: {}
              }
            },
            watch: {
              value(val) {
                this.parent.PADieuChinhHDOptions[0].ID = parseInt(val)
              }
            },
            mounted() {
              this.dataSource = this.parent.PADieuChinhHDOptions
            }
          }
        }
      }
    },
    getColTemplateXuLyDL(parent) {
      return function() {
        return {
          template: {
            template: `
              <div style="text-align:center">
                <SelectExt
                  v-model="value"
                  :dataSource="dataSource"
                  dataTextField="TEXT"
                  dataValueField="ID"
                  :allowFiltering="false"
                />
              </div>`,
            data() {
              return {
                parent,
                dataSource: [],
                value: null,
                data: {}
              }
            },
            watch: {
              value(val) {
                this.parent.PAXuLyDiLieuOptions[0].ID = parseInt(val)
              }
            },
            mounted() {
              this.dataSource = this.parent.PAXuLyDiLieuOptions
  
            }
          }
        }
      }
    },
    onActionComplete: function() {

    },
    // acceptChenNo(e) {
    //   console.log('onActionComplete', e)
    //   let dsGTC = e
    //   console.log(dsGTC, ' = dsGTC')
    //   this.dsThongTinGiamTru = []
    //   if (dsGTC.length != 0) {
    //     this.dsThongTinGiamTru = dsGTC
    //     this.dsThongTinGiamTru = [...this.dsThongTinGiamTru]
    //   }
    // },
  },
  watch: {
    'tabNavigate.OTPChuaGiao': async function(newVal, oldVal) {
      await this.onLayDanhSach(OPTIONS.NAV_TAB.CHUA_GIAO)
    },
    'tabNavigate.OTPDaGiao': async function(newVal, oldVal) {
      await this.onLayDanhSach(OPTIONS.NAV_TAB.DA_GIAO)
    },       
  }
}
</script>
