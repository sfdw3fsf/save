<template src="./index.html"></template>

<script>
import { mapActions, mapState, mapGetters } from 'vuex'
import breadcrumb from '@/components/breadcrumb'
import moment from 'moment'
import ChonDonViNhanVien from '../ChonDonViNhanVien/index'
import TaiNguyenNfs from '../tainguyen-nfs/index.vue'
import TaiNguyenBackup from '../TaiNguyenBackup/index.vue'
import TaiNguyenDisk from '../TaiNguyenDisk/index.vue'

export default {
  components: {
    TaiNguyenNfs,
    TaiNguyenBackup,
    TaiNguyenDisk
  },
  data: function () {
    return {
      dsCongNghe: [],
      dsHDH: [],
      dsLoaiHT: [],
      dsHaTangAoHoa: [],
      dsHaTang: [],
      dsHaTangIDG: [],
      dsTTMayAo: [],
      dsVaiTro: [],
      dsNguonYC: [
        {
          TEN: 'JIRA',
          ID: 1
        },
        {
          TEN: 'Văn bản',
          ID: 2
        },
        {
          TEN: 'Email',
          ID: 3
        },
        {
          TEN: 'CĐS KHDN',
          ID: 4
        }
      ],
      dsSPDV: [],
      dsDvChuQuan: [],
      dsIops: [],
      dsThroughtput: [],
      dsSelected: [],
      //Bảng lịch sử cấp phát
      dsLSCapPhat: [],
      colsLSCapPhat: [
        { fieldName: 'PHIEUYEUCAU_TEXT', headerText: 'Phiếu yêu cầu', allowFiltering: true },
        { fieldName: 'NGUON_YEUCAU_TEXT', headerText: 'Nguồn yêu cầu', allowFiltering: true },
        { fieldName: 'NGUOI_YEUCAU', headerText: 'Người yêu cầu', allowFiltering: true },
        { fieldName: 'NGAY_CAP', headerText: 'Ngày cấp', textAlign: 'center', allowFiltering: true, format: { type: 'date', format: 'dd/MM/yyyy HH:mm:ss' }, type: 'date' },
        { fieldName: 'NGUOI_CAP_TEXT', headerText: 'Người cấp', allowFiltering: true },
        { fieldName: 'VCPU_OLD', headerText: 'vCPU trước', allowFiltering: true, textAlign: 'center' },
        { fieldName: 'VCPU', headerText: 'vCPU sau', allowFiltering: true, textAlign: 'center' },
        { fieldName: 'VRAM_OLD', headerText: 'vRAM trước', allowFiltering: true, textAlign: 'center' },
        { fieldName: 'VRAM', headerText: 'vRAM sau', allowFiltering: true, textAlign: 'center' }
      ],
      //Bảng lịch sử thay đổi
      dsHistory: [],
      colsHistory: [
        { fieldName: 'SOTHUTU', headerText: 'STT', allowFiltering: false, width: 50 },
        { fieldName: 'TACDONG', headerText: 'Tên tác động', allowFiltering: true },
        { fieldName: 'FIELD', headerText: 'Trường thông tin thay đổi', allowFiltering: true },
        { fieldName: 'OLD_VALUE', headerText: 'Giá trị cũ', allowFiltering: true },
        { fieldName: 'NEW_VALUE', headerText: 'Giá trị mới', allowFiltering: true },
        { fieldName: 'NGUOI_THUCHIEN', headerText: 'Người thực hiện', allowFiltering: true },
        { fieldName: 'THOI_DIEM', headerText: 'Thời điểm', allowFiltering: true }
      ],
      //Bảng disk VM
      dsDiskVM: [],
      colsDiskVM: [
        { fieldName: 'SOTHUTU', headerText: 'STT', allowFiltering: true },
        { fieldName: 'TEN', headerText: 'Tên Disk', allowFiltering: true },
        { fieldName: 'LOAI', headerText: 'Loại Disk', allowFiltering: true },
        { fieldName: 'DUNGLUONG', headerText: 'Dung lượng', allowFiltering: true },
        { fieldName: 'HIEU_LUC', headerText: 'Hiệu lực', allowFiltering: true }
      ],
      //Bảng backup
      dsBackup: [],
      colsBackup: [
        { fieldName: 'SOTHUTU', headerText: 'STT', allowFiltering: true },
        { fieldName: 'TEN', headerText: 'Tên tài nguyên', allowFiltering: true },
        { fieldName: 'MAHATANG_BACKUP', headerText: 'Mã hạ tầng backup', allowFiltering: true },
        { fieldName: 'IP_ADDRESS', headerText: 'IP backup', allowFiltering: true },
        { fieldName: 'LINK', headerText: 'Đường dẫn backup', allowFiltering: true },
        { fieldName: 'BACKUP_LOAI_ID', headerText: 'Loại backup', allowFiltering: true },
        { fieldName: 'TANSUAT_ID', headerText: 'Tần suất', allowFiltering: true },
        { fieldName: 'HIEU_LUC', headerText: 'Hiệu lực', allowFiltering: true }
      ],
      //Bảng NFS/SMB
      dsNFSSMB: [],
      colsNFSSMB: [
        { fieldName: 'SOTHUTU', headerText: 'STT', allowFiltering: true },
        { fieldName: 'TEN', headerText: 'Tên NFS/SMB', allowFiltering: true },
        { fieldName: 'LINK', headerText: 'Đường dẫn', allowFiltering: true },
        { fieldName: 'GIAOTHUC', headerText: 'Giao thức', allowFiltering: true },
        { fieldName: 'HIEU_LUC', headerText: 'Hiệu lực', allowFiltering: true }
      ],
      //Bảng khách hàng
      dsKH: [],
      colsKH: [
        { fieldName: 'SOTHUTU', headerText: 'STT', allowFiltering: true },
        { fieldName: 'MA_KH', headerText: 'Mã khách hàng', allowFiltering: true },
        { fieldName: 'TEN', headerText: 'Tên khách hàng', allowFiltering: true },
        { fieldName: 'LOAI_KH', headerText: 'Loại KH', allowFiltering: true },
        { fieldName: 'MA_HD', headerText: 'Mã Hợp đồng', allowFiltering: true },
        { fieldName: 'THOIHAN_HD', headerText: 'Thời hạn HĐ', allowFiltering: true },
        { fieldName: 'SLA', headerText: 'Cam kết SLA', allowFiltering: true },
        { fieldName: 'HIEU_LUC', headerText: 'Hiệu lực', allowFiltering: true }
      ],
      //Bảng network
      dsNetwork: [],
      colsNetwork: [
        { fieldName: 'SOTHUTU', headerText: 'STT', allowFiltering: true },
        { fieldName: 'IP', headerText: 'IP', allowFiltering: true },
        { fieldName: 'NGAY_CAPNHAT', headerText: 'Ngày cập nhật', allowFiltering: true },
        { fieldName: 'NGUOI_CAPNHAT', headerText: 'Người cập nhật', allowFiltering: true },
        { fieldName: 'HIEU_LUC', headerText: 'Hiệu lực', allowFiltering: true }
      ],
      //Bảng máy ảo
      dsMayAo: [],
      colsMayAo: [
        { fieldName: 'TEN', headerText: 'Tên máy ảo', allowFiltering: true },
        { fieldName: 'HATANGAOHOA_TEXT', headerText: 'Hạ tầng ảo hóa', allowFiltering: true },
        { fieldName: 'HATANGIDG_TEXT', headerText: 'Hạ tầng IDG', allowFiltering: true },
        { fieldName: 'LOAIVAITRO_TEXT', headerText: 'Vai trò', allowFiltering: true },
        { fieldName: 'HEDIEUHANH_TEXT', headerText: 'Hệ điều hành', allowFiltering: true },
        { fieldName: 'NGAY_TAO', headerText: 'Ngày tạo', allowFiltering: true },
        { fieldName: 'NGAY_HETHAN', headerText: 'Ngày hết hạn', allowFiltering: true },
        { fieldName: 'TINHGIA_TEXT', headerText: 'Tính giá', allowFiltering: true },
        { fieldName: 'HATANG_PHANLOAI_TEXT', headerText: 'Phân loại HT', allowFiltering: true },
        { fieldName: 'TRANGTHAI_MAYAO_TEXT', headerText: 'Trạng thái máy ảo', allowFiltering: true },
        { fieldName: 'HIEULUC_TEXT', headerText: 'Hiệu lực', allowFiltering: true }
      ],
      //Thông tin user
      userInfo: {
        phanVungId: 0,
        nhanVienId: 0,
        nhanVienTen: '',
        donViId: 0,
        donViTen: '',
        donViChaId: 0,
        donViChaTen: ''
      },
      //Biến tab
      currentTab: 1,
      currentSubTab: 1,
      currentTab2: 1,
      //Item được chọn
      currentItem: {
        id: null,
        mamayao_infra: '',
        ten: '',
        hatangaohoa_id: null,
        hatangidg_id: null,
        loaivaitro_id: null,
        nguon_yeucau: null,
        nguoi_yeucau: null,
        hedieuhanh_id: null,
        hatang_phanloai_id: null,
        trangthai_mayao_id: null,
        ngay_tao: null,
        ngay_hethan: null,
        nguoi_capnhat: '',
        ngay_capnhat: null,
        tinhgia: 1,
        ghi_chu: '',
        hieu_luc: 1,
        donviqly_id: null,
        ten_dv: '',
        phongbanqly_id: null,
        ten_phongban: '',
        nguoiqly_id: null,
        ten_nguoiqly: '',
        cumdichvu_id: null,
        spdv_id: null,
        donvi_chuquan_id: null,
        vcpu: null,
        vram: null,
        dichvu_iops_id: null,
        dichvu_throughtput_id: null,
        gpu: null
      },
      //Form controls
      controls: {
        txtMaMayAo: {
          enabled: false,
          invalid: false
        },
        chkHieuLuc: {
          enabled: false
        },
        chkGiaHT: {
          enabled: false
        },
        txtTenMayAo: {
          enabled: false,
          invalid: false
        },
        cboHDH: {
          enabled: false,
          invalid: false
        },
        cboHTAoHoa: {
          enabled: false
        },
        cboLoaiHT: {
          enabled: false
        },
        cboHTIDG: {
          enabled: false
        },
        cboTTMayAo: {
          enabled: false
        },
        cboVaiTro: {
          enabled: false
        },
        cboNguonYC: {
          enabled: false
        },
        txtNguoiYC: {
          enabled: false,
          invalid: false
        },
        txtNguoiCapNhat: {
          enabled: false,
          invalid: false
        },
        txtGhiChu: {
          enabled: false,
          invalid: false
        },
        cboCumDV: {
          enabled: false
        },
        cboSPDV: {
          enabled: false
        },
        cboDvChuQuan: {
          enabled: false
        },
        cboIops: {
          enabled: false
        },
        cboThroughtput: {
          enabled: false
        },
        txtNgayTao: {
          enabled: false
        },
        txtNgayHetHan: {
          enabled: false
        },
        txtNgayCapNhat: {
          enabled: false
        },
        txtVRAM: {
          enabled: false
        },
        txtVCPU: {
          enabled: false
        },
        txtGPU: {
          enabled: false
        }
      },
      //Nút
      buttons: {
        btnAddNew: {
          enabled: true
        },
        btnSave: {
          enabled: false
        },
        btnCancel: {
          enabled: false
        },
        btnEdit: {
          enabled: false
        },
        btnDelete: {
          enabled: false
        }
      },
      selectedHTAoHoa: null
    }
  },
  computed: {
    grdItems: function () {
      return this.$refs.grdItems
    }
  },
  watch: {
    'currentItem.id': function (value) {
      this.buttons.btnEdit.enabled = value > 0
      this.buttons.btnDelete.enabled = value > 0
    },
    'currentItem.idcId': function (value) {
      this.controls.cboIDC.invalid = value == 0
    }
  },
  created: async function () {
    try {
      this.$root.showLoading(true)
      // init user Info
      this.userInfo.nhanVienId = await this.$root.token.getNhanVienID()
      this.userInfo.nhanVienTen = this.$root.context.user.getProperty('ten_nv')
      this.userInfo.donViId = this.$root.context.user.getProperty('donvi_id')
      this.userInfo.donViTen = this.$root.context.user.getProperty('ten_dv')
      this.userInfo.donViChaId = this.userInfo.donViId
      this.userInfo.donViChaTen = this.userInfo.donViTen
      let ds = await this.apiGetDonViCha(this.userInfo.donViId)
      if (ds != null && ds.length > 0) {
        this.userInfo.donViChaId = ds[0].DONVI_ID
        this.userInfo.donViChaTen = ds[0].TEN_DV
      }
      this.currentItem.ten_dv = this.userInfo.donViChaTen
      this.currentItem.ten_phongban = this.userInfo.donViTen
      this.currentItem.ten_nguoiqly = this.userInfo.nhanVienTen

      this.dsHaTangAoHoa = await this.getDsHTAoHoa()
      this.dsHaTangIDG = await this.getDsHTIDG()
      this.dsHDH = await this.getDsHDH()
      this.dsVaiTro = await this.getDsVaiTro()
      this.dsTTMayAo = await this.getDsTTMayAo()
      this.dsLoaiHT = await this.getDsLoaiHT()
      this.dsIops = await this.apiListIOPS()
      this.dsThroughtput = await this.apiListThroughtput()
      this.dsMayAo = await this.apiListDanhSach()
      this.dsCumDichVu = await this.getDsCumDv()
      this.dsSPDV = await this.getDsSpdv()

    } finally {
      this.$root.showLoading(false)
    }
  },
  methods: {
     async setPropsData() {
      console.log('setPropsData')
    },
    //Lấy ds hạ tầng ảo hóa
    getDsHTAoHoa: async function () {
      let result = []
      try {
        let rs = await this.$root.context.get('/web-ecms/danhmuc/ha-tang-ao-hoa')
        result = rs.data
      } catch (error) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result
    },

    //Lấy ds hạ tầng IDG
    getDsHTIDG: async function () {
      let result = []
      try {
        let rs = await this.$root.context.get('/web-ecms/danhmuc/ha-tang-idg')
        result = rs.data
      } catch (error) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result
    },

    //Lấy ds loại hạ tầng
    getDsLoaiHT: async function () {
      let result = []
      try {
        let rs = await this.$root.context.get('/web-ecms/danhmuc/ha-tang-phan-loai')
        result = rs.data
      } catch (error) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result
    },

    //Lấy ds vai trò
    getDsVaiTro: async function () {
      let result = []
      try {
        let rs = await this.$root.context.get('/web-ecms/danhmuc/vai-tro')
        result = rs.data
      } catch (error) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result
    },

    //Lấy ds hệ điều hành
    getDsHDH: async function () {
      let result = []
      try {
        let rs = await this.$root.context.get('/web-ecms/danhmuc/he-dieu-hanh')
        result = rs.data
      } catch (error) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result
    },

    //Lấy ds SPDV
    getDsSpdv: async function () {
      let result = []
      if (this.currentItem.cumdichvu_id) {
        try {
          let rs = await this.$root.context.get(`/web-ecms/danhmuc/spdv-by-cumdichvu?cumDvId=` + this.currentItem.cumdichvu_id)
          result = rs.data
        } catch (error) {
          this.$toast.error(e.response.data.message_detail)
        }
      } else {
        try {
          let rs = await this.$root.context.get('/web-ecms/danhmuc/spdv')
          result = rs.data
        } catch (error) {
          this.$toast.error(e.response.data.message_detail)
        }
      }

      return result
    },

    //Lấy ds SPDV
    getDsCumDv: async function () {
      let result = []
      try {
        let rs = await this.$root.context.get('/web-ecms/danhmuc/cumdichvu')
        result = rs.data
      } catch (error) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result
    },

    //Lấy ds don vi
    loadDonvi: async function (id) {
      let result = []
      try {
        let rs = await this.$root.context.get(`/web-ecms/danhmuc/chi-tiet-donvi-by-spdv-id?spdvId=` + id)
        result = rs.data
      } catch (error) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result
    },

    //Lấy ds SPDV by cum id
    loadSPDVByCumdichvuId: async function (id) {
      let result = []
      try {
        let rs = await this.$root.context.get(`/web-ecms/danhmuc/spdv-by-cumdichvu?cumDvId=` + id)
        result = rs.data
      } catch (error) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result
    },

    //Lấy ds trạng thái máy ảo
    getDsTTMayAo: async function () {
      let result = []
      try {
        let rs = await this.$root.context.get('/web-ecms/danhmuc/trang-thai-may-ao')
        result = rs.data
      } catch (error) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result
    },

    //Lấy ds đơn vị quản lý
    getDsDonVi: async function (param) {
      let result = []
      try {
        let rs = await this.$root.context.post('/web-ecms/danhmuc/find-don-vi', param)
        result = rs.data
      } catch (error) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result
    },

    //Lấy ds cá nhân quản lý
    getDsCaNhan: async function (id) {
      let result = []
      try {
        let rs = await this.$root.context.get(`/web-ecms/danhmuc/nhan-vien-don-vi?donViId=${id}`)
        result = rs.data
      } catch (error) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result
    },

    apiGetDonViCha: async function (pId) {
      let result = []
      if (pId == null || pId < 1) {
        return
      }
      try {
        let rs = await this.$root.context.get('/web-ecms/danhmuc/don-vi-cha', { donViId: pId })
        result = rs.data
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result
    },

    apiListDanhSach: async function () {
      let result = []
      try {
        let rs = await this.$root.context.get('/web-ecms/virtual-machine')
        result = rs.data
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result
    },

    apiListAllocationHistory: async function (pId) {
      let result = []
      try {
        let rs = await this.$root.context.get(`/web-ecms/virtual-machine/allocation-history/${pId}`)
        result = rs.data
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result
    },

    apiListDiskVM: async function (pId) {
      let result = []
      try {
        let rs = await this.$root.context.get(`/web-ecms/virtual-machine/disk-vm/${pId}`)
        result = rs.data
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result
    },
    apiListBackup: async function (pId) {
      let result = []
      try {
        let rs = await this.$root.context.get(`/web-ecms/virtual-machine/backup/${pId}`)
        result = rs.data
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result
    },
    apiListNFSSMB: async function (pId) {
      let result = []
      try {
        let rs = await this.$root.context.get(`/web-ecms/virtual-machine/nfs-smb/${pId}`)
        result = rs.data
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result
    },
    apiListIOPS: async function (pId) {
      let result = []
      try {
        let rs = await this.$root.context.get(`/web-ecms/danhmuc/iops`)
        result = rs.data
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result
    },
    apiListThroughtput: async function (pId) {
      let result = []
      try {
        let rs = await this.$root.context.get(`/web-ecms/danhmuc/throughtput`)
        result = rs.data
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result
    },
    apiListCustomers: async function (pId) {
      let result = []
      try {
        let rs = await this.$root.context.get(`/web-ecms/virtual-machine/customers/${pId}`)
        result = rs.data
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result
    },
    apiListNetwork: async function (pId) {
      let result = []
      try {
        let rs = await this.$root.context.get(`/web-ecms/virtual-machine/network/${pId}`)
        result = rs.data
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result
    },
    apiListHistory: async function (pId) {
      let result = []
      try {
        let rs = await this.$root.context.get(`/web-ecms/virtual-machine/history/${pId}`)
        result = rs.data
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result
    },
    apiSaveNfs: async function () {
      let result = false

      for (let i = 0; i < this.dsNFSSMB.length; i++) {
        console.log(this.dsNFSSMB[i].isNew)
        if (this.dsNFSSMB[i].isNew) {
          try {            
            let rs = await this.$root.context.post(
              `/web-ecms/virtual-machine/${this.currentItem.id}/nfsAdd?nfsId=${this.dsNFSSMB[i].id}`
            )
            result = rs.error_code == 'BSS-00000000'
          } catch (e) {
            this.$root.toastError(e.response?.data?.message_detail || e.message)
            result = false
          }
        }
      }
      this.dsNFSSMB = await this.apiListNFSSMB(this.currentItem.id)
    },

    apiSaveBackup: async function () {
      let result = false

      for (let i = 0; i < this.dsBackup.length; i++) {
        console.log(this.dsBackup[i].isNew)
        if (this.dsBackup[i].isNew) {
          try {            
            let rs = await this.$root.context.post(
              `/web-ecms/virtual-machine/${this.currentItem.id}/backupAdd?nfsId=${this.dsBackup[i].id}`
            )
            result = rs.error_code == 'BSS-00000000'
          } catch (e) {
            this.$root.toastError(e.response?.data?.message_detail || e.message)
            result = false
          }
        }
      }
      this.dsBackup = await this.apiListBackup(this.currentItem.id)
    },

    apiSaveDisk: async function () {
      let result = false
      for (let i = 0; i < this.dsDiskVM.length; i++) {
        if (this.dsDiskVM[i].isNew) {
          const data = {
            ten: this.dsDiskVM[i].TEN,
            loai: this.dsDiskVM[i].LOAI,
            dungluong: this.dsDiskVM[i].DUNGLUONG,
            hieu_luc: this.dsDiskVM[i].HIEU_LUC
          }

          try {            
            let rs = await this.$root.context.post(
              `/web-ecms/virtual-machine/${this.currentItem.id}/diskAdd`,
              data
            )
            result = rs.error_code == 'BSS-00000000'
          } catch (e) {
            this.$root.toastError(e.response?.data?.message_detail || e.message)
            result = false
          }
        }
      }
      this.dsDiskVM = await this.apiListDiskVM(this.currentItem.id)
    },
    
    apiSave: async function (param) {
      let result = false
      try {
        let postData = {
          maMayAoCDSDHIT: param.mamayao_infra,
          ten: param.ten,
          haTangAoHoaId: parseInt(param.hatangaohoa_id),
          haTangIdgId: parseInt(param.hatangidg_id),
          loaiVaiTroId: parseInt(param.loaivaitro_id),
          nguonYeuCau: parseInt(param.nguon_yeucau),
          nguoiYeuCau: this.userInfo.nhanVienId,
          heDieuHanhId: parseInt(param.hedieuhanh_id),
          haTangPhanLoaiId: parseInt(param.hatang_phanloai_id),
          trangThaiMayAoId: parseInt(param.trangthai_mayao_id),
          ngayTao: param.ngay_tao ? moment(param.ngay_tao).format('DD/MM/YYYY') : '',
          ngayHetHan: param.ngay_hethan ? moment(param.ngay_hethan).format('DD/MM/YYYY') : '',
          nguoiCapNhat: parseInt(this.userInfo.nhanVienId),
          ngayCapNhat: param.ngay_capnhat ? moment(param.ngay_capnhat).format('DD/MM/YYYY') : '',
          tinhGia: parseInt(param.tinhgia),
          ghiChu: param.ghi_chu,
          hieuLuc: parseInt(param.hieu_luc),
          donViQlyId: parseInt(param.donviqly_id),
          phongBanQlyId: parseInt(param.phongbanqly_id),
          nguoiQlyId: parseInt(param.nguoiqly_id),
          cumDichVuId: parseInt(param.cumdichvu_id),
          spdvId: parseInt(param.spdv_id) || null,
          donViChuQuanId: parseInt(param.donvi_chuquan_id) || null,
          vCPU: parseInt(param.vcpu),
          vRAM: parseInt(param.vram),
          gpu: parseInt(param.gpu),
          dichVuIopsId: parseInt(param.dichvu_iops_id),
          dichVuThroughtputId: parseInt(param.dichvu_throughtput_id)
        }
        let rs = null
        if (param.id == 0) {
          rs = await this.$root.context.post('/web-ecms/virtual-machine/create', postData)
        } else {
          rs = await this.$root.context.post(`/web-ecms/virtual-machine/update/${param.id}`, postData)
        }
        result = rs.error_code == 'BSS-00000000'
      } catch (e) {
        let msgErr = 'Lỗi : \n'
        if (e.response.data.message_detail == null) {
          if (e.response.data.data == null) {
            msgErr = e.response.data.message
          } else {
            let jsonObj = e.response.data.data
            for (var key in jsonObj) {
              msgErr += ' - ' + jsonObj[key] + '\n'
            }
          }
        } else {
          msgErr = e.response.data.message_detail
        }
        this.$root.toastError(msgErr)
        result = false
      }
      return result
    },

    apiDelete: async function (id) {
      let result = false
      try {
        let rs = await this.$root.context.post(`/web-ecms/virtual-machine/delete/${id}`)
        result = rs.error_code == 'BSS-00000000'
      } catch (e) {
        this.$root.toastError(e.response.data.message_detail)
        result = false
      }
      return result
    },

    enableControls: function (args) {
      this.controls.txtMaMayAo.enabled = args
      this.controls.chkHieuLuc.enabled = args
      this.controls.chkGiaHT.enabled = args
      this.controls.txtTenMayAo.enabled = args
      this.controls.cboHDH.enabled = args
      this.controls.cboHTAoHoa.enabled = args
      this.controls.cboLoaiHT.enabled = args
      this.controls.cboHTIDG.enabled = args
      this.controls.cboTTMayAo.enabled = args
      this.controls.cboVaiTro.enabled = args
      this.controls.cboNguonYC.enabled = args
      this.controls.txtNguoiYC.enabled = args
      this.controls.txtNguoiCapNhat.enabled = args
      this.controls.txtGhiChu.enabled = args
      this.controls.cboCumDV.enabled = args
      this.controls.cboSPDV.enabled = args
      this.controls.cboDvChuQuan.enabled = args ? false : false
      this.controls.txtNgayTao.enabled = args
      this.controls.txtNgayHetHan.enabled = args
      this.controls.txtNgayCapNhat.enabled = args
      this.controls.txtVCPU.enabled = args
      this.controls.txtVRAM.enabled = args
      this.controls.txtGPU.enabled = args
      this.controls.cboIops.enabled = args
      this.controls.cboThroughtput.enabled = args

      this.buttons.btnSave.enabled = args
      this.buttons.btnAddNew.enabled = !args
      this.buttons.btnCancel.enabled = this.buttons.btnSave.enabled
      this.buttons.btnDelete.enabled = !args

      if (this.currentItem.id > 0) {
        this.buttons.btnEdit.enabled = !this.buttons.btnSave.enabled
        this.controls.txtVCPU.enabled = false
        this.controls.txtVRAM.enabled = false
        if (this.currentItem.nguon_yeucau == 4) {
          this.controls.txtNguoiYC.enabled = false
          this.controls.txtNguoiCapNhat.enabled = false
        }
      }
    },

    onShowDataZone: async function (pId) {
      if (pId > 0) {
        try {
          this.$root.showLoading(true)
          this.dsLSCapPhat = await this.apiListAllocationHistory(pId)
          this.dsDiskVM = await this.apiListDiskVM(pId)
          this.dsBackup = await this.apiListBackup(pId)
          this.dsNFSSMB = await this.apiListNFSSMB(pId)
          this.dsKH = await this.apiListCustomers(pId)
          this.dsNetwork = await this.apiListNetwork(pId)
          this.dsHistory = await this.apiListHistory(pId)
        } finally {
          this.$root.showLoading(false)
        }
      }
    },

    setCurrentItem: async function (item) {
      this.currentItem.id = item.ID
      this.currentItem.ten = item.TEN
      this.currentItem.mamayao_infra = item.MAMAYAO_INFRA
      this.currentItem.hedieuhanh_id = item.HEDIEUHANH_ID
      this.currentItem.hatangidg_id = item.HATANGIDG_ID
      this.currentItem.hatang_phanloai_id = item.HATANG_PHANLOAI_ID
      this.currentItem.hatangaohoa_id = item.HATANGAOHOA_ID
      this.currentItem.hieu_luc = item.HIEU_LUC
      this.currentItem.donviqly_id = item.DONVIQLY_ID
      this.currentItem.ten_dv = item.DONVIQLY_TEXT
      this.currentItem.phongbanqly_id = item.PHONGBANQLY_ID
      this.currentItem.ten_phongban = item.PHONGBANQLY_TEXT
      this.currentItem.nguoiqly_id = item.NGUOIQLY_ID
      this.currentItem.ten_nguoiqly = item.NGUOIQLY_TEXT
      this.currentItem.ngay_tao = item.NGAY_TAO
      this.currentItem.ngay_hethan = item.NGAY_HETHAN
      this.currentItem.tinhgia = item.TINHGIA
      this.currentItem.trangthai_mayao_id = item.TRANGTHAI_MAYAO_ID
      this.currentItem.loaivaitro_id = item.LOAIVAITRO_ID
      this.currentItem.nguon_yeucau = item.NGUON_YC
      this.currentItem.nguoi_yeucau = item.NGUOIYC_TEXT
      this.currentItem.nguoi_capnhat = item.NGUOICAPNHAT_TEXT
      this.currentItem.ngay_capnhat = item.NGAY_CAPNHAT
      this.currentItem.ghi_chu = item.GHI_CHU
      this.currentItem.cumdichvu_id = item.CUMDICHVU_ID
      this.currentItem.spdv_id = item.SPDV_ID
      this.currentItem.donvi_chuquan_id = item.DONVI_CHUQUAN_ID
      this.currentItem.vcpu = item.VCPU
      this.currentItem.vram = item.VRAM
      this.currentItem.gpu = item.VGPU
      this.currentItem.dichvu_iops_id = item.DICHVU_IOPS_ID
      this.currentItem.dichvu_throughtput_id = item.DICHVU_THROUGHTPUT_ID
      this.selectedHTAoHoa = await this.getDetailHTAoHoa(item.HATANGAOHOA_ID)
      this.onShowDataZone(item.ID)
    },

    setCurrentItemDefault: async function () {
      this.currentItem.id = 0
      this.currentItem.mamayao_infra = ''
      this.currentItem.hieu_luc = 1
      this.currentItem.tinhgia = 1
      this.currentItem.ten = ''
      this.currentItem.hedieuhanh_id = null
      this.currentItem.hatangaohoa_id = null
      this.currentItem.hatang_phanloai_id = null
      this.currentItem.hatangidg_id = null
      this.currentItem.trangthai_mayao_id = null
      this.currentItem.loaivaitro_id = null
      this.currentItem.ngay_tao = moment(new Date()).toDate()
      this.currentItem.ngay_hethan = null
      this.currentItem.nguon_yeucau = null
      this.currentItem.nguoi_yeucau = this.userInfo.nhanVienTen
      this.currentItem.nguoi_capnhat = this.userInfo.nhanVienTen
      this.currentItem.ngay_capnhat = null
      this.currentItem.ghi_chu = ''
      this.currentItem.vcpu = null
      this.currentItem.vram = null
      this.currentItem.gpu = null
      this.currentItem.dichvu_iops_id = null
      this.currentItem.dichvu_throughtput_id = null
      this.currentItem.cumdichvu_id = null
      this.currentItem.spdv_id = null
      this.currentItem.donvi_chuquan_id = null

      this.currentItem.nguoiqly_id = this.userInfo.nhanVienId
      this.currentItem.ten_nguoiqly = this.userInfo.nhanVienTen
      this.currentItem.phongbanqly_id = this.userInfo.donViId
      this.currentItem.ten_phongban = this.userInfo.donViTen
      this.currentItem.donviqly_id = this.userInfo.donViChaId
      this.currentItem.ten_dv = this.userInfo.donViChaTen

      this.dsLSCapPhat = []
      this.dsDiskVM = []
      this.dsBackup = []
      this.dsNFSSMB = []
      this.dsKH = []
      this.dsNetwork = []
      this.dsHistory = []
      this.dsSPDV = await this.getDsSpdv()
      this.selectedHTAoHoa = null
    },

    validateControls: function () {
      let msg = ''
      let checkCPURAM = true
      if (!this.currentItem.mamayao_infra) {
        msg += ', Mã máy ảo CĐS-ĐHIT'
      }

      if (!this.currentItem.ten) {
        msg += ', Tên máy ảo'
      }

      if (!this.currentItem.hedieuhanh_id) {
        msg += ', Hệ điều hành'
      }

      if (!this.currentItem.loaivaitro_id) {
        msg += ', Vai trò'
      }

      if (!this.currentItem.vcpu) {
        msg += ', vCPU'
      }

      if (!this.currentItem.vram) {
        msg += ', vRAM'
      }

      if (!this.currentItem.gpu) {
        msg += ', GPU'
      }

      if (msg != '') {
        msg = 'Các trường: ' + msg.substring(2) + ' không được để trống!'
      }

      if (this.selectedHTAoHoa) {
        if (parseInt(this.currentItem.vcpu) > this.selectedHTAoHoa.VCPU_CAP) {
          checkCPURAM = false
          msg += ', vCPU không được lớn hơn ' + this.selectedHTAoHoa.VCPU_CAP
        }

        if (parseInt(this.currentItem.vram) > this.selectedHTAoHoa.VRAM_CAP) {
          checkCPURAM = false
          msg += ', vRAM không được lớn hơn ' + this.selectedHTAoHoa.VRAM_CAP
        }

        if (parseInt(this.currentItem.gpu) > this.selectedHTAoHoa.GPU_CAP) {
          checkCPURAM = false
          msg += ', GPU không được lớn hơn ' + this.selectedHTAoHoa.GPU_CAP
        }
      }

      if (msg != '') this.$root.toastError(msg)
      return msg == ''
    },

    grdItems_RowSelected: async function (args) {
      if (this.buttons.btnSave.enabled) {
        return
      }
      this.setCurrentItem(args.data)
    },

    grdItems_RowDeselected: async function (args) {
      if (this.buttons.btnSave.enabled) {
        return
      }
      setTimeout(() => {
        let items = this.grdItems.getSelectedRecords()
        if (!(items == null || items.length == 0)) {
          this.setCurrentItem(items[0])
        } else {
          this.setCurrentItemDefault()
        }
      }, 300)
    },

    onAddNew: async function (args) {
      await this.setCurrentItemDefault()
      this.enableControls(true)
    },

    onSave: async function (args) {
      if (this.validateControls()) {
        let flag = await this.$confirm('Bạn có muốn lưu thông tin này không ?', 'Xác nhận', {
          confirmButtonText: 'Có',
          cancelButtonText: 'Không',
          type: 'info',
          dangerouslyUseHTMLString: true
        })
          .then((s) => true)
          .catch((e) => {
            return false
          })
        if (flag) {
          try {
            this.$root.showLoading(true)
            let rs = await this.apiSave(this.currentItem)
            let rsNfs = await this.apiSaveNfs()
            let rsBackup = await this.apiSaveBackup()
            let rsDisk = await this.apiSaveDisk()

            if (rs && rsNfs && rsBackup && rsDisk) {
              this.dsMayAo = await this.apiListDanhSach()
              if (this.currentItem.id == 0) {
                this.$root.toastSuccess('Thêm mới thông tin thành công ')
              } else {
                this.$root.toastSuccess('Cập nhật thông tin thành công ')
              }
            }
            this.enableControls(!rs)
          } finally {
            this.$root.showLoading(false)
          }
        }
      }
    },

    onCancel: async function (args) {
      let items = this.grdItems.getSelectedRecords()
      let item = null
      if (!(items == null || items.length == 0)) {
        if (this.currentItem.id == 0) {
          item = items[0]
        } else {
          item = items.find((x) => x.ID == this.currentItem.id)
        }
      }
      if (item != null) {
        this.setCurrentItem(item)
      } else {
        this.setCurrentItemDefault()
      }
      this.enableControls(false)
    },

    onEdit: async function (args) {
      this.enableControls(true)
    },

    onDelete: async function (args) {
      let items = this.grdItems.getSelectedRecords()
      let msg = `Bạn có muốn xóa ${items.length} mẫu tin đã chọn không?`
      let flag = await this.$confirm(msg, 'Xác nhận', {
        confirmButtonText: 'Có',
        cancelButtonText: 'Không',
        type: 'warning',
        dangerouslyUseHTMLString: true
      })
        .then((s) => true)
        .catch((e) => {
          return false
        })
      if (flag) {
        try {
          this.$root.showLoading(true)
          let isSuccess = false
          for (const item of items) {
            isSuccess = await this.apiDelete(item.ID)
            if (!isSuccess) {
              break
            }
          }
          if (isSuccess) {
            this.setCurrentItemDefault()
            this.dsMayAo = await this.apiListDanhSach()
            this.enableControls(false)
            this.$root.toastSuccess('Xóa thông tin thành công')
          }
        } finally {
          this.$root.showLoading(false)
        }
      }
    },

    onChangeHaTangAoHoa: async function (args) {
      this.selectedHTAoHoa = await this.getDetailHTAoHoa(args)
    },

    onChangeSpdv: async function (args) {
      // Find the object based on the selected ID
      const selectedSpdv = this.dsSPDV.find(item => item.ID == args);
      let result = await this.loadDonvi(args);
      this.dsDvChuQuan = result;

      if (args.value !== null && selectedSpdv.TEN != 'SPDV Khác') {
        this.currentItem.donvi_chuquan_id = result[0].DONVI_ID;
        this.controls.cboDvChuQuan.enabled = false;
      } else {
        // update th spdv Khác
        this.controls.cboDvChuQuan.enabled = true;
      }
    },

    onChangeCumdichvu: async function (args) {
      if (args.value !== null) {
        let result = await this.loadSPDVByCumdichvuId(args);
        this.dsSPDV = result;
        this.currentItem.spdv_id = result[0].ID;
        const selectedSpdv = this.dsSPDV.find(item => item.ID == result[0].ID);
        let resultDonvi = await this.loadDonvi(result[0].ID);
        this.dsDvChuQuan = resultDonvi;

        if (args.value !== null && selectedSpdv.TEN != 'SPDV Khác') {
          this.currentItem.donvi_chuquan_id = resultDonvi[0].DONVI_ID;
          this.controls.cboDvChuQuan.enabled = false;
        } else {
          // update th spdv Khác
          this.controls.cboDvChuQuan.enabled = true;
        }
      }
    },

    getDetailHTAoHoa: async function (id) {
      let result = {}
      try {
        let rs = await this.$root.context.get(`/web-ecms/virtual-machine/ht-ao-hoa/${id}`)
        result = rs.data
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result
    },
    ShowPopup_CapNfs: async function() {
      this.$refs.ref_cap_nfs.setPropsData()
      this.$refs.popupCapNfs.ej2Instances.visible = true
    },
    async xacNhanNfsHandler(args = []) {
      this.dsNFSSMB.push({
        id: args.id,
        isNew: true,
        TEN: args.ten,
        LINK: args.linkBackup,
        GIAOTHUC: args.loaiLuuTruId === 1 ? 'NFS' : 'SMB',
        HIEU_LUC: args.hieuLuc === 1 ? 'Hiệu lực' : 'Hết hiệu lực',
      });

      this.$refs.popupCapNfs.ej2Instances.visible = false
    },
    ShowPopup_CapBackup: async function() {
      this.$refs.ref_cap_backup.setPropsData()
      this.$refs.popupCapBackup.ej2Instances.visible = true
    },
    async xacNhanBackupHandler(args = []) {
      this.dsBackup.push({
        id: args.id,
        isNew: true,
        TEN: args.ten,
        MAHATANG_BACKUP: args.hatangbackup_id,
        IP_ADDRESS: args.ip_capnhap, // Nếu cần lấy IP string thì bạn cần tra bảng IP để map id → địa chỉ
        LINK: args.link_backup,
        BACKUP_LOAI_ID: args.backup_loai_id,
        TANSUAT_ID: args.tansuat_id,
        HIEU_LUC: args.hieu_luc === 1 ? 'Hiệu lực' : 'Hết hiệu lực'
      });

      this.$refs.popupCapBackup.ej2Instances.visible = false
    },
    ShowPopup_CapDisk: async function() {
      this.$refs.ref_cap_disk.setPropsData()
      this.$refs.popupCapDisk.ej2Instances.visible = true
    },
    async xacNhanDiskHandler(args = []) {
      args.forEach((item, index) => {
        this.dsDiskVM.push({
          isNew: true,
          TEN: item.ten,
          LOAI: item.loai_disk,
          DUNGLUONG: item.dung_luong,
          HIEU_LUC: item.hieu_luc
        });
      });

      this.$refs.popupCapDisk.ej2Instances.visible = false
    }
  }
}
</script>
<style>
.check-action {
  top: 3px;
}

.e-rowcell .check-action input[type='checkbox']:checked:disabled+.name:before {
  background: #fff;
  border-color: #0176ff;
  color: #0176ff;
}

.e-rowcell .check-action input[type='checkbox']:disabled+.name:before {
  background: #fff;
  border-color: #0176ff;
}
</style>
