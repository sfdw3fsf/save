<template src="./index.html"></template>
<style src="./styles.scss" scoped></style>

<script>
import {OPTIONS, TRANGTHAI_DX} from '../Common/Constant'
import APICommon from '../api/APICommon'

import frmHoSoKhieuNai from '@/modules/complaint/SearchComplainDetail/popups/frmHoSoKhieuNai.vue'
import frmTraCuuTBKhieuNai from '@/modules/complaint/SearchComplainDetail/popups/frmTraCuuTBKhieuNai.vue'
import frmGiaoPhieuGTC from '@/modules/EXTS/KHIEUNAI/giaophieu-dexuat-gtc/index.vue'
import ChiTietKhieuNai from '../ChiTietKhieuNai/ChiTiet.vue'

export default {
  name: 'ThamDinhGiamTruCuoc',
  components: {
    frmHoSoKhieuNai,
    frmTraCuuTBKhieuNai,
    frmGiaoPhieuGTC,
    ChiTietKhieuNai,
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
        DICHVUVT_TEN: '',
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
        btnSua: true,
      }
    }
  },
  computed: {
    isBtnSuaEnabled() {
      return this.buttons.btnSua && this.currentItem.MA_KN && this.tabNavigate.activetab == 1
    },
    isDongYGiaoPhieu() {
      return this.currentItem.PHEDUYET_DX_GTC.HTTL_ID == 1
    }
  },
  created: async function () {},
  mounted: async function () {
    this.$root.showLoading(true)
    try {
      this.yKienTDOptions = OPTIONS.YKIEN_DEXUAT
    } catch (e) {
      console.log('mounted', e);
    } finally {
      this.$root.showLoading(false)
    }
  },
  methods: {
    // API
    apiGetDXGiamTruCuocTheoTT: async function (params) {
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
    apiGetChiTietDXGiamTruCuocTheoMaKN: async function (params) {
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
    async apiTuChoiDeXuatGiamTruCuoc(params) {
      this.loading(true)
      let result = true
      try {
        const response = await APICommon.tuChoiDeXuatGiamTruCuoc(this.axios, params)
        if (response.data.error_code === 'BSS-00000000') {
          this.$root.toastSuccess('Từ chối ĐX giảm trừ cước thành công!')
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
    onLayDanhSach: async function (value) {
      this.clearForm()
      if (value) {
        if (value == OPTIONS.NAV_TAB.CHUA_GIAO) {
          this.dsPhieuChuaGiao = await this.apiGetDXGiamTruCuocTheoTT({
            pTTKNId: TRANGTHAI_DX.PDDX_GTC,
            pChuaGiao: OPTIONS.NAV_TAB.CHUA_GIAO,
            pTTPHId: this.tabNavigate.OTPChuaGiao,      
          })
        } else {
          this.dsPhieuDaGiao = await this.apiGetDXGiamTruCuocTheoTT({
          pTTKNId: TRANGTHAI_DX.PDDX_GTC,
          pChuaGiao: OPTIONS.NAV_TAB.DA_GIAO,
          pTTPHId: this.tabNavigate.OTPDaGiao,          
        })
        }
      } else {
        this.dsPhieuChuaGiao = await this.apiGetDXGiamTruCuocTheoTT({
          pTTKNId: TRANGTHAI_DX.PDDX_GTC,
          pChuaGiao: OPTIONS.NAV_TAB.CHUA_GIAO,
          pTTPHId: this.tabNavigate.OTPChuaGiao,      
        })
        this.dsPhieuDaGiao = await this.apiGetDXGiamTruCuocTheoTT({
          pTTKNId: TRANGTHAI_DX.PDDX_GTC,
          pChuaGiao: OPTIONS.NAV_TAB.DA_GIAO,
          pTTPHId: this.tabNavigate.OTPDaGiao,          
        })
      }
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
    onEdit() {
      this.buttons.btnSua = false
    },
    async onHuy() {
      await this.onLayDanhSach()
    },
    async onSave() {
      let valid = true
      if (!this.currentItem.PHEDUYET_DX_GTC.GHI_CHU || this.currentItem.PHEDUYET_DX_GTC.GHI_CHU.trim() == '') {
        this.$root.toastError('Ghi chú ĐX GTC là bắt buộc!')
        valid = false
      }
      if (this.currentItem.PHEDUYET_DX_GTC.HTTL_ID == null) {
        this.$root.toastError('Ý kiến ĐX GTC là bắt buộc!')
        valid = false
      }
      if (!this.currentItem.PHIEUKN_ID) {
        this.$root.toastError('Khiếu nại chưa ĐX GTC!')
        valid = false
      }
      if (!valid) return;

      const flag = await this.$confirm('Bạn có muốn lưu đề xuất?', 'Xác nhận thực hiện', {
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
        pYKien: this.currentItem.PHEDUYET_DX_GTC.HTTL_ID,
        pNoiDung: this.currentItem.PHEDUYET_DX_GTC.GHI_CHU,
        
      }
      const response = await this.apiCreateUpdatePhieuDeXuatGTC(params);
      if (response) {
        await this.onLayDanhSach()
      }
    },
    async onGiaoPPhieu() {
      try {
        this.loading(true)
        this.$refs.frmGiaoPhieuGTC.maKN = this.currentItem.MA_KH
        this.$refs.frmGiaoPhieuGTC.pTTKNId = TRANGTHAI_DX.PDDX_GTC
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
    onXemChiTietKhieuNai() {
      if (!this.currentItem.KHIEUNAI_ID) return;
      this.$refs.dialogChiTietKhieuNai.onGetData(1)
      this.$refs.dialogChiTietKhieuNai.dialogOpen()
    },
    async popupGiaoPhieuClosed() {
      this.onLayDanhSach()
    },    
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
