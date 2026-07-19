<template src="./index.html"></template>
<script>
import {API, LOAI_TRACUU, CONSTANT} from './Constant.js'
import frmHoSoKhieuNai from "@/modules/complaint/SearchComplainDetail/popups/frmHoSoKhieuNai.vue"
import frmXemTienTrinhKN from "@/modules/search/subscriber/ComplaintProcess/index.vue"

export default {
  name: 'DanhSachKhieuNai',
  components: {
    frmHoSoKhieuNai,
    frmXemTienTrinhKN,
  },
  watch: {
  },
  data: function () {
    return {
      position: { X: 'center', Y: 'top' },
      preSearchParams: null,
      params: {
        loaiTraCuuOptions: {
          value: 0,
          dataSource: [],
          text: ''
        },
        tuNgayDenNgay: {
          checkbox: false,
          tuNgay: new Date(),
          denNgay: new Date(),
        },
        chuDeOptions: {
          checkbox: false,
          value: 0,
          dataSource: []
        },
        dichVuOptions: {
          checkbox: false,
          value: 0,
          dataSource: []
        },
        loaiHinhOptions: {
          checkbox: false,
          value: 0,
          dataSource: []
        },
        trangThaiKNOptions: {
          checkbox: false,
          value: 0,
          dataSource: []
        },
        phieuTonOptions: {
          checkbox: false,
        },
        thoiHanOptions: {
          checkbox: false,
          value: 0,
        },
      },
      currentItem: {
        
      },
      tabDsKhieuNaiSelected: {
        ChuaGiao: true,
        DaGiao: false
      },
      dsPhieuChuaGiao: [],
      dsPhieuDaGiao: [],
      showTabs: true,
      nhanVienThucHien: {
        dataSource: [],
        value: null,
        soLuongGiao: 0,
      }
    }
  },
  mounted: async function () {
    this.loading(true);
    try {
      this.params.loaiTraCuuOptions.dataSource = LOAI_TRACUU
      this.params.chuDeOptions.dataSource = await this.apiGetDSChuDe()
      this.params.trangThaiKNOptions.dataSource = await this.apiGetDSTrangThaiKN()
      this.params.dichVuOptions.dataSource = await this.apiGetDSDichVuVT()
      this.nhanVienThucHien.dataSource = await this.apiGetDSNhanVienXuLyKN()
      this.nhanVienThucHien.dataSource = [...this.nhanVienThucHien.dataSource]
      this.nhanVienThucHien.value = this.nhanVienThucHien.dataSource.find(item => item.NHANVIEN_ID == this.$root.token.getNhanVienID())?.NHANVIEN_ID ?? null;
    } catch (e) {
      console.log(e);
    } finally {
      this.loading(false);
    }
    
  },
  methods: {
    // API
    apiGetDSNhanVienXuLyKN: async function() {
      this.loading(true)
      let dsResult = []
      try {
        const response = await API.getDSNhanVienXuLyKN(this.axios)
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
    apiGetDSChuDe: async function() {
      this.loading(true)
      let dsResult = []
      try {
        const response = await API.getDSChuDe(this.axios)
        if (response.data.error_code === 'BSS-00000000') {
          dsResult = response.data.data
        } else {
          dsResult = []
        }
      } catch (error) {
        dsResult = []
      } finally {
        this.loading(false)
        return dsResult
      }
    },
    apiGetDSDichVuVT: async function() {
      this.loading(true)
      let dsResult = []
      try {
        const response = await API.getDSDichVuVT(this.axios)
        if (response.data.error_code === 'BSS-00000000') {
          dsResult = response.data.data
        } else {
          dsResult = []
        }
      } catch (error) {
        dsResult = []
      } finally {
        this.loading(false)
        return dsResult
      }
    },
    apiGetDSLoaiHinhThueBao: async function(pDichVuId) {
      this.loading(true)
      let dsResult = []
      try {
        const response = await API.getDSLoaiHinhThueBao(this.axios, pDichVuId)
        if (response.data.error_code === 'BSS-00000000') {
          dsResult = response.data.data
        } else {
          dsResult = []
        }
      } catch (error) {
        dsResult = []
      } finally {
        this.loading(false)
        return dsResult
      }
    },
    apiGetDSTrangThaiKN: async function() {
      this.loading(true)
      let dsResult = []
      try {
        const response = await API.getDSTrangThaiKN(this.axios)
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
    apiGetDSKhieuNaiChuaGiao: async function(params) {
      this.loading(true)
      let dsResult = []
      try {
        const response = await API.getDSKhieuNaiChuaGiao(this.axios, params)
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
    apiGetDSKhieuNaiDaGiao: async function(params) {
      this.loading(true)
      let dsResult = []
      try {
        const response = await API.getDSKhieuNaiDaGiao(this.axios, params)
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
    apiGiaoPhieuNhanVien: async function(params) {
      this.loading(true)
      let result = false
      try {
        const response = await API.giaoNhanVien(this.axios, params)
        if (response.data.error_code === 'BSS-00000000') {
          result = true
        }
      } catch (error) {
        console.log('apiGiaoPhieuNhanVien - error: ', error);
      } finally {
        this.loading(false)
      }
      return result
    },
    apiHuyGiaoNhanVien: async function(params) {
      this.loading(true)
      let result = false
      try {
        const response = await API.huyGiaoNhanVien(this.axios, params)
        if (response.data.error_code === 'BSS-00000000') {
          result = true
        }
      } catch (error) {
        console.log('apiGiaoPhieuNhanVien - error: ', error);
      } finally {
        this.loading(false)
      }
      return result
    },
    apiLayPhieuNVTuDong:  async function() {
      this.loading(true)
      let result = false
      try {
        const response = await API.layPhieuNVTuDong(this.axios)
        if (response.data.error_code === 'BSS-00000000') {
          this.$root.toastSuccess(`Lấy phiếu tự động thành công!`)
          result = true
        }
      } catch (error) {
        console.log('apiLayPhieuNVTuDong - error: ', error);
        this.$root.toastError(error.data.message)
      } finally {
        this.loading(false)
      }
      return result
    },
    // VALID
    validTimKiem: function() {
      let result = true

      return result
    },
    validGiaoNhanVien: function() {
      let result = true
      if (!this.nhanVienThucHien.value) {
        this.$root.toastError("Nhân viên thực hiện là bắt buộc!")
        result = false
      }
      if (!this.dsPhieuChuaGiao || this.dsPhieuChuaGiao.length == 0) {
        this.$root.toastError("Không có phiếu khiếu nại!");
        result = false
      }
      if (!this.nhanVienThucHien.soLuongGiao || this.nhanVienThucHien.soLuongGiao < 1) {
        this.$root.toastError("Số lượng phiếu giao là bắt buộc và lớn hơn 0!")
        result = false
      }

      return result;
    },
    // EVENT ACTION
    onChangeLoaiHinhCheck: async function() {
      if (this.params.loaiHinhOptions.checkbox) {
        const dichVuVTId = this.params.dichVuOptions.value && this.params.dichVuOptions.value != 0 
                          ? this.params.dichVuOptions.value 
                          : null;  
        this.params.loaiHinhOptions.dataSource = await this.apiGetDSLoaiHinhThueBao(dichVuVTId)
      }
    },
    onChangeTrangThaiCheck() {
      if (this.params.trangThaiKNOptions.checkbox && this.params.trangThaiKNOptions.dataSource.length > 0) {
        this.params.trangThaiKNOptions.value = this.params.trangThaiKNOptions.dataSource[0].TTKN_ID
      } else {
        this.params.trangThaiKNOptions.value = null
      }
    },
    onLayDanhSach: async function() {
      if (!this.preSearchParams || this.dsPhieuChuaGiao.length == 0) {
        return this.$root.toastError("Không có phiếu khiếu nại!");
      }
      const params = this.preSearchParams;
      this.dsPhieuChuaGiao = await this.apiGetDSKhieuNaiChuaGiao(params)
      this.dsPhieuDaGiao = await this.apiGetDSKhieuNaiDaGiao(params)
    },
    onTimKiem: async function() {
      if (!this.validTimKiem()) return;      
      const params = {
        pLoaiTraCuu: this.params.loaiTraCuuOptions.value,
        pMaTraCuu: this.params.loaiTraCuuOptions.text.trim(),
        pTuNgayTN: this.params.tuNgayDenNgay.checkbox ? this.params.tuNgayDenNgay.tuNgay : null,
        pDenNgayTN: this.params.tuNgayDenNgay.checkbox ? this.params.tuNgayDenNgay.denNgay : null,
        pChuDeId: this.params.chuDeOptions.checkbox ? this.params.chuDeOptions.value : null,
        pDichVuId: this.params.dichVuOptions.checkbox ? this.params.dichVuOptions.value : null,
        pTTKNId: this.params.trangThaiKNOptions.checkbox ? this.params.trangThaiKNOptions.value : null,
        pPhieuTon: this.params.phieuTonOptions.checkbox ? 1 : 0,
        pTrongHan: this.params.thoiHanOptions.checkbox ? this.params.thoiHanOptions.value : null,
      }
      // save log params search
      this.preSearchParams = params
      this.dsPhieuChuaGiao = await this.apiGetDSKhieuNaiChuaGiao(params)
      this.dsPhieuDaGiao = await this.apiGetDSKhieuNaiDaGiao(params)
    },
    onGiaoNhanVien: async function() {
      if (!this.validGiaoNhanVien()) return
      const params = {
        pNhanVienTHId: this.nhanVienThucHien.value
      }
      // lấy ngẫu nhiên 1 phiếu bất kỳ
      if (this.nhanVienThucHien.soLuongGiao == 1) {
        const randomItem = this.dsPhieuChuaGiao[Math.floor(Math.random() * this.dsPhieuChuaGiao.length)];
        params.pPhieuKNList = [{
          KHIEUNAI_ID: randomItem.KHIEUNAI_ID,
          PHIEUKN_ID: randomItem.PHIEUKN_ID,
        }]
      // lấy sl phiếu ở trên xuống
      } else {
        params.pPhieuKNList = this.dsPhieuChuaGiao.slice(0, this.nhanVienThucHien.soLuongGiao)
                                                  .map(item => ({
                                                    KHIEUNAI_ID: item.KHIEUNAI_ID,
                                                    PHIEUKN_ID: item.PHIEUKN_ID,
                                                  }))
      }
      const tenNhanVienNhan = this.nhanVienThucHien.dataSource.find(item => item.NHANVIEN_ID == this.nhanVienThucHien.value).NAME || "";
      let confirm = await this.$confirm(
          `Bạn có muốn giao <span class='red'> ${this.nhanVienThucHien.soLuongGiao} </span> phiếu cho nhân viên <span class='red'>${tenNhanVienNhan} </span> không ?`,
          "Xác nhận",
          {
            confirmButtonText: "Có",
            cancelButtonText: "Không",
            type: "info",
            dangerouslyUseHTMLString: true,
          }
        )
          .then((s) => true)
          .catch((e) => {
            return false;
          });
      
      // cancel    
      if (!confirm) return;
        
      // confirm 
      const result = await this.apiGiaoPhieuNhanVien(params)
      if (result) {
        this.$root.toastSuccess("Giao phiếu nhân viên thành công!")
      } else {
        this.$root.toastError("Giao phiếu nhân viên thất bại")
      }
      // re-load 
      await this.onLayDanhSach()
    },
    onHuyGiaoNhanVien: async function() {
      let deletedItems = this.$refs.gridDsThueBaoKhieuNaiDaGiao.getSelectedRecords()
      if (!deletedItems || deletedItems.length == 0) {
        return this.$root.toastError("Bạn chưa chọn phiếu để hủy giao!")
      }
      let confirm = await this.$confirm(
          `Bạn có muốn hủy giao <span class='red'> ${deletedItems.length} </span> phiếu đã chọn không ?`,
          "Xác nhận",
          {
            confirmButtonText: "Có",
            cancelButtonText: "Không",
            type: "info",
            dangerouslyUseHTMLString: true,
          }
        )
          .then((s) => true)
          .catch((e) => {
            return false;
      });
      
      // cancel 
      if (!confirm) return;
      const params = {
        phieuKNIdList: deletedItems.map(item => item.PHIEUKN_ID)
      }
      const response = await this.apiHuyGiaoNhanVien(params)
      if (response) {
        this.$root.toastSuccess("Hủy giao phiếu thành công!")
      } else {
        this.$root.toastError("Hủy giao phiếu thất bại!")
      }
      // re-load
      await this.onLayDanhSach()
    },
    onLayPhieuTuDong: async function() {
      let confirm = await this.$confirm(
          `Hệ thống sẽ tự đông giao phiếu về giỏ riêng nhân viên?`,
          "Xác nhận",
          {
            confirmButtonText: "Xác nhận",
            cancelButtonText: "Không",
            type: "info",
            dangerouslyUseHTMLString: true,
          }
        )
          .then((s) => true)
          .catch((e) => {
            return false;
      });
      // cancel
      if (!confirm) return;

      // confirm 
      const response = await this.apiLayPhieuNVTuDong()
      if (response) await this.onLayDanhSach()
      // if (response) {
      //   this.$root.toastSuccess("Lấy phiếu nhân viên tự động thành công!")
      // } else {
      //   this.$root.toastError("Lấy phiếu nhân viên tự động thất bại!")
      // }
      // re-load
      
    },
    onXemHoSo: async function() {
      let items;
      if (this.tabDsKhieuNaiSelected.ChuaGiao) {
        items = this.$refs.gridDsThueBaoKhieuNaiChuaGiao.getSelectedRecords()
      } else {
        items = this.$refs.gridDsThueBaoKhieuNaiDaGiao.getSelectedRecords()
      }
      if (!items || items.length == 0)  {
        return this.$root.toastError("Bạn cần chọn phiếu khiếu nại để xem hồ sơ!")
      }
      console.log('onXemHoSo', items);
      this.$refs.frmHoSoKhieuNai.khieunai_id = items[0].KHIEUNAI_ID;
      this.$refs.frmHoSoKhieuNai.show();
    },
    onXemTienTrinh: async function() {
      this.$refs.frmXemTienTrinhKNPopup.show()
      console.log('onXemTienTrinh', this.$refs.frmXemTienTrinhKN)
      //return this.$root.toastError("Chức năng đang phát triển xin vui lòng liên hệ với admin!")
    },
    onChangeNVThucHien(value) {
      if (value && (!this.nhanVienThucHien.soLuongGiao || this.nhanVienThucHien.soLuongGiao < 1 )) {
        this.nhanVienThucHien.soLuongGiao = 1
      }
    },
    gridDsThueBaoKhieuNaiChuaGiaoRowSelected: async function(data) {
      // console.log('Phiếu chưa giao = ', data)
    },
    async gridDsThueBaoKhieuNaiDaGiaoRowSelected(data) {
      // console.log('Phiếu đã giao = ', data)
    },
    tabDsKhieuNai_Click: async function (args) {
      this.$root.showLoading(true);
      this.tabDsKhieuNaiSelected.DaGiao = (args == CONSTANT.DA_GIAO)
      this.tabDsKhieuNaiSelected.ChuaGiao = !this.tabDsKhieuNaiSelected.DaGiao;
      try {
        this.dsPhieuChuaGiao = [...this.dsPhieuChuaGiao]
        this.dsPhieuDaGiao = [...this.dsPhieuDaGiao]
      } finally {
        this.$root.showLoading(false);
      }
    },
  }
}
</script>
