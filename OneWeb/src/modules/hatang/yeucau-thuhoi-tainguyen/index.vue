<template src="./index.html"></template>

<script>
import { mapActions, mapState, mapGetters } from 'vuex'
import breadcrumb from '@/components/breadcrumb'
import moment from 'moment'
import ChonDonViNhanVien from '../ChonDonViNhanVien/index'

export default {
  data: function() {
    return {
      currentTab: 1, // Tab hiện tại (mặc định là tab 1)
      isModalOpen: false, // Trạng thái mở modal
      danhSachYeuCau: [],
      dsTrangThaiYeuCau: [],
      dsSelected: [],
      aoHoa: null,
      dsSPDV: [],
      dsCumDV: [],
      dsAoHoa: [],
      dsAoHoaAll: [],
      dsContainerK8All: [],
      dsIDGAll: [],
      dsBackupAll: [],
      colsAoHoa: [
        // Tab ảo hóa
        { fieldName: 'SOTHUTU', headerText: 'STT', allowFiltering: true, width: 50 },
        { fieldName: 'CUMDICHVU', headerText: 'Cụm dịch vụ', allowFiltering: true },
        { fieldName: 'VM', headerText: 'Tên VM', allowFiltering: true },
        { fieldName: 'DONVIVH', headerText: 'Đơn vị vận hành', allowFiltering: true },
        { fieldName: 'NGUOI_QUANTRI', headerText: 'Người quản trị', allowFiltering: true },
        { fieldName: 'IPADDRESS', headerText: 'Ip address', allowFiltering: true },
        { fieldName: 'TRANGTHAI_THUHOI', headerText: 'Trạng thái thu hồi', allowFiltering: true }
      ],
      colsAoHoaAll: [
        // Tab ảo hóa
        { fieldName: 'SOTHUTU', headerText: 'STT', allowFiltering: true, width: 50 },
        { fieldName: 'CUMDICHVU', headerText: 'Cụm dịch vụ', allowFiltering: true },
        { fieldName: 'VM', headerText: 'Tên VM', allowFiltering: true },
        { fieldName: 'DONVIVH', headerText: 'Đơn vị vận hành', allowFiltering: true },
        { fieldName: 'NGUOI_QUANTRI', headerText: 'Người quản trị', allowFiltering: true },
        { fieldName: 'IPADDRESS', headerText: 'Ip address', allowFiltering: true }
      ],
      dsContainerK8: [], // Tab Container K8s
      colsContainerK8: [
        { fieldName: 'SOTHUTU', headerText: 'STT', allowFiltering: true, width: 50 },
        { fieldName: 'TEN', headerText: 'Tên hạ tầng K8S', allowFiltering: true },
        { fieldName: 'DONVIVH', headerText: 'Đơn vị vận hành', allowFiltering: true },
        { fieldName: 'NGUOI_QTRI', headerText: 'Người quản trị', allowFiltering: true },
        { fieldName: 'IPADDRESS', headerText: 'Ip address', allowFiltering: true },
        { fieldName: 'TRANGTHAI_THUHOI', headerText: 'Trạng thái thu hồi', allowFiltering: true }
      ],

      dsIDG: [], // Tab IDG

      colsIDG: [
        { fieldName: 'SOTHUTU', headerText: 'STT', allowFiltering: true, width: 50 },
        { fieldName: 'TEN', headerText: 'Tên hạ tầng', allowFiltering: true },
        { fieldName: 'CONGNGHE', headerText: 'Công nghệ sử dụng', allowFiltering: true },
        { fieldName: 'DONVIVH', headerText: 'Đơn vị vận hành', allowFiltering: true },
        { fieldName: 'NGUOI_QTRI', headerText: 'Người quản trị', allowFiltering: true },
        { fieldName: 'LOAIGOI', headerText: 'Loại gói', allowFiltering: true },
        { fieldName: 'GIATRI_GOI', headerText: 'Giá trị gói', allowFiltering: true },
        { fieldName: 'TRANGTHAI_THUHOI', headerText: 'Trạng thái thu hồi', allowFiltering: true }
      ],
      colsIDGAll: [
        { fieldName: 'SOTHUTU', headerText: 'STT', allowFiltering: true, width: 50 },
        { fieldName: 'TEN', headerText: 'Tên hạ tầng', allowFiltering: true },
        { fieldName: 'CONGNGHE', headerText: 'Công nghệ sử dụng', allowFiltering: true },
        { fieldName: 'DONVIVH', headerText: 'Đơn vị vận hành', allowFiltering: true },
        { fieldName: 'NGUOI_QTRI', headerText: 'Người quản trị', allowFiltering: true },
        { fieldName: 'LOAIGOI', headerText: 'Loại gói', allowFiltering: true },
        { fieldName: 'GIATRI_GOI', headerText: 'Giá trị gói', allowFiltering: true }
      ],

      dsBackup: [], //Tab Backup
      colsBackup: [
        { fieldName: 'SOTHUTU', headerText: 'STT', allowFiltering: true, width: 50 },
        { fieldName: 'TEN', headerText: 'Tên hạ tầng', allowFiltering: true },
        { fieldName: 'BACKUP_NAME', headerText: 'Tên back up', allowFiltering: true },
        { fieldName: 'LOAIHATANG', headerText: 'Loại hạ tầng', allowFiltering: true },
        // { fieldName: 'CONGNGHE', headerText: 'Công nghệ sử dụng', allowFiltering: true },
        { fieldName: 'DONVIVH', headerText: 'Đơn vị vận hành', allowFiltering: true },
        { fieldName: 'NGUOI_QTRI', headerText: 'Người quản trị', allowFiltering: true },
        { fieldName: 'TRANGTHAI_THUHOI', headerText: 'Trạng thái thu hồi', allowFiltering: true }
      ],

      dsIPAddress: [],
      colsIPAddress: [
        { fieldName: 'SOTHUTU', headerText: 'STT', allowFiltering: true, width: 50 },
        { fieldName: 'TEN', headerText: 'Tên', allowFiltering: true },
        { fieldName: 'IP_PHANLOAI', headerText: 'Phân loại', allowFiltering: true },
        { fieldName: 'IP_STORE', headerText: 'IP Store', allowFiltering: true },
        { fieldName: 'SUBNET_QH', headerText: 'Mục đích cấp', allowFiltering: true },
        { fieldName: 'DONVI_CAP', headerText: 'Đơn vị cấp', allowFiltering: true },
        { fieldName: 'HIEU_LUC', headerText: 'Hiệu lực', allowFiltering: true }
      ],
      dsThietBi: [],
      colsThietBi: [
        { fieldName: 'SOTHUTU', headerText: 'STT', allowFiltering: true, width: 50 },
        { fieldName: 'MATHIETBI', headerText: 'Mã thiết bị', allowFiltering: true },
        { fieldName: 'TENTHIETBI', headerText: 'Tên thiết bị', allowFiltering: true },
        { fieldName: 'IP_ADDRESS', headerText: 'IP Quản trị', allowFiltering: true },
        { fieldName: 'VAI_TRO', headerText: 'Vai trò thiết bị', allowFiltering: true },
        { fieldName: 'NGAY_BS', headerText: 'Ngày bổ sung', allowFiltering: true },
        { fieldName: 'NGUOI_BS', headerText: 'Người bổ sung', allowFiltering: true }
      ],
      dsHistory: [],
      colsHistory: [
        { fieldName: 'SOTHUTU', headerText: 'STT', allowFiltering: false, width: 50 },
        { fieldName: 'TACDONG', headerText: 'Tác động', allowFiltering: true },
        { fieldName: 'FIELD', headerText: 'Trường thông tin thay đổi', allowFiltering: true },
        { fieldName: 'OLD_VALUE', headerText: 'Giá trị cũ', allowFiltering: true },
        { fieldName: 'NEW_VALUE', headerText: 'Giá trị mới', allowFiltering: true },
        { fieldName: 'NGUOI_THUCHIEN', headerText: 'Người thực hiện', allowFiltering: true },
        { fieldName: 'THOI_DIEM', headerText: 'Thời điểm', allowFiltering: true }
      ],
      userInfo: {
        phanVungId: 0,
        nhanVienId: 0,
        nhanVienTen: '',
        donViId: 0,
        donViTen: '',
        donViChaId: 0,
        donViChaTen: ''
      },
      imageItem: '',
      add: 0,
      currentTab: 1,
      appsecret:
        'eyJkZXZpY2VfaWQiOiI2MUVDNDg2RS02RUE2LTQ0OTUtQkRBRS0xQkFDMDExRjQzMDAiLCJkZXZpY2VfaXAiOiJlcnJvciIsImRldmljZV9uYW1lIjoiaVBob25lIC0gaVBob25lIDEyIFBybyBNYXgiLCJtYWNfYWRkcmVzcyI6IlVua25vdyIsIm1vYmlsZV9pZCI6IlVua25vdyIsImFwcF9pZCI6IjIiLCJhcHBfdmVyc2lvbiI6IjIuNC4xLjQwIiwib3NfdmVyc2lvbiI6ImlPUyAtIDE0LjUifQ==',
      fileName: '', // Lưu tên file
      id_taptin: '', // Lưu id file
      url_taptin: null,
      file_yc: null,
      isDeleteFile: false,
      currentItem: {
        id: 0,
        ten: null,
        socu: null,
        trangthai: null,
        donvi_yeucau: null,
        donViYeuCauId: 0,
        tenSPDV: null,
        nguoiGoi: null,
        nguoiGoiId: 0,
        urlFile: null,
        tenfile:'',
        cumDichVu: null,
        ngayGoi: '',
        ngayHoanThanh: null,
        ngayBatDauThuHoi: null,
        maHaTang: null,
        // IDCId: 0,
        sanPhamDichVuId: 0,
        yKienXacNhan: null,
        ngatTaiNguyen: null,
        yKienThuHoi: null,
        yKienThuHoiBacKup: null,
        trangThaiYeuCauId: 1,
        trangThaiThuHoi: null,
        avatar: null,
        ghiChu: null,
        hieuLuc: 1,
        donViQuanLyId: 0,
        donViQuanLy: null,
        phongBanQuanLyId: 0,
        phongBanQuanLy: null,
        nguoiQuanLyId: 0,
        nguoiQuanLy: null,
        fileId: 0
      },
      controls: {
        txtTen: {
          enabled: false,
          invalid: false
        },
        // cboIDC: {
        //   enabled: false,
        //   invalid: false
        // },
        txtMaHaTang: {
          enabled: false,
          invalid: false
        },
        txtNgayHoanThanh: {
          enabled: false,
          invalid: false
        },
        cboSPDV: {
          enabled: false,
          invalid: false
        },
        txtGhiChu: {
          enabled: false
        },
        chkHieuLuc: {
          enabled: false
        }
      },
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
        },
        btnThucHien: {
          enabled: false
        }
      }
    }
  },
  computed: {
    grdItems: function() {
      return this.$refs.grdItems
    },

    modalTitle() {
      switch (this.currentTab) {
        case 1:
          return 'Thêm mới cho tab Ảo hóa'
        case 2:
          return 'Thêm mới cho tab Container - K8s'
        case 3:
          return 'Thêm mới cho tab IDG'
        case 4:
          return 'Thêm mới cho tab Backup'
        default:
          return 'Thêm mới'
      }
    }
  },
  watch: {
    'currentItem.id': function(value) {
      this.buttons.btnEdit.enabled = value > 0
      this.buttons.btnDelete.enabled = value > 0
    },

    'currentItem.sanPhamDichVuId': function(newVal, oldVal) {
      if (newVal !== oldVal) {
        this.apiCumDichVu(newVal).then((result) => {
          this.dsCumDV = result
        })
      }
    },
    'currentItem.ngayBatDauThuHoi': function(newVal, oldVal) {
      if (newVal !== oldVal) {
        const ngayGoi = moment(this.currentItem.ngayGoi, 'DD/MM/YYYY')
        const ngayBatDauThuHoi = moment(newVal, 'DD/MM/YYYY')

        if (ngayBatDauThuHoi.isBefore(ngayGoi)) {
          this.$toast.error('Ngày bắt đầu phải lớn hơn hoặc bằng ngày gửi!')
          this.currentItem.ngayBatDauThuHoi = null
        } else {
          const formatDate = ngayBatDauThuHoi.format('DD/MM/YYYY')
        }
      }
    }

    // 'currentItem.ngayGoi': function(newVal, oldVal) {
    //       if (newVal !== oldVal) {
    //         const formatDate = moment(newVal).format('dd/MM/y')
    //         this.currentItem.ngayGoi = formatDate
    //       }
    //     },
    //     'currentItem.ngayBatDauThuHoi': function(newVal, oldVal) {
    //       if (newVal !== oldVal) {
    //         const formatDate = moment(newVal).format('dd/MM/y')
    //         this.currentItem.ngayBatDauThuHoi = formatDate
    //       }
    //     },
    //     'currentItem.ngayHoanThanh': function(newVal, oldVal) {
    //       if (newVal !== oldVal) {
    //         const formatDate = moment(newVal).format('dd/MM/y')
    //         this.currentItem.ngayHoanThanh = formatDate
    //       }
    //     },
  },

  created: async function() {
    try {
      this.$root.showLoading(true)
      //this.dsIDC=await this.apiIDC();

      this.danhSachYeuCau = await this.apiListDanhSach()
      this.dsTrangThaiYeuCau = await this.apiDanhSachTrangThaiYeuCau()
      this.dsSPDV = await this.apiDanhSachSPDV()

      // init user Info
      //this.userInfo.phanVungId = parseInt(this.$auth.getPhanVungID());
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
    } finally {
      this.$root.showLoading(false)
    }
  },
  methods: {
   
    grid_onCommandClicked(name, dataItem) {
      if (!this.controls.txtTen.enabled) {
    return; // Không xử lý nếu button bị disable
    }
      if (name === 'XOAAOHOA') {
        this.deleteRowAoHoa(dataItem)
      }
      if (name === 'XOAIDG') {
        this.deleteRowIDG(dataItem)
      }
      if (name === 'XOABACKUP') {
        this.deleteRowBackup(dataItem)
      }
      if (name === 'XOAK8') {
        this.deleteRowContainerK8(dataItem)
      }
    },
    deleteRowAoHoa: async function(args) {
      let msg = `Bạn có muốn xóa dữ liệu không?`
      let flag = await this.$confirm(msg, 'Xác nhận', {
        confirmButtonText: 'Đồng ý',
        cancelButtonText: 'Không đồng ý',
        type: 'warning',
        dangerouslyUseHTMLString: true
      })
        .then((s) => true)
        .catch((e) => {
          return false
        })
      if (flag) {
        try {
          this.removeAoHoaItem(args.ID)
          this.$root.toastSuccess('Xóa thành công!')
        } catch (error) {
          this.$root.toastError('Xóa ảo hóa thất bại')
        }
      }
    },
    deleteRowIDG: async function(args) {
      let msg = `Bạn có muốn xóa dữ liệu không?`
      let flag = await this.$confirm(msg, 'Xác nhận', {
        confirmButtonText: 'Đồng ý',
        cancelButtonText: 'Không đồng ý',
        type: 'warning',
        dangerouslyUseHTMLString: true
      })
        .then((s) => true)
        .catch((e) => {
          return false
        })
      if (flag) {
        try {
          this.removeIDGItem(args.ID)
          this.$root.toastSuccess('Xóa thành công!')
        } catch (error) {
          this.$root.toastError('Xóa IDG thất bại')
        }
      }
    },
    deleteRowContainerK8: async function(args) {
      let msg = `Bạn có muốn xóa dữ liệu không?`
      let flag = await this.$confirm(msg, 'Xác nhận', {
        confirmButtonText: 'Đồng ý',
        cancelButtonText: 'Không đồng ý',
        type: 'warning',
        dangerouslyUseHTMLString: true
      })
        .then((s) => true)
        .catch((e) => {
          return false
        })
      if (flag) {
        try {
          this.removeContainerK8Item(args.ID)
          this.$root.toastSuccess('Xóa thành công!')
        } catch (error) {
          this.$root.toastError('Xóa Container K8s thất bại')
        }
      }
    },
    deleteRowBackup: async function (args) {
      let msg = `Bạn có muốn xóa dữ liệu không?`
      let flag = await this.$confirm(msg, 'Xác nhận', {
        confirmButtonText: 'Đồng ý',
        cancelButtonText: 'Không đồng ý',
        type: 'warning',
        dangerouslyUseHTMLString: true
      })
        .then((s) => true)
        .catch((e) => {
          return false
        })
      if (flag) {
        try {
          this.removeBackupItem(args.ID)
          this.$root.toastSuccess('Xóa thành công!')
        } catch (error) {
          this.$root.toastError('Xóa Backup thất bại')
        }
      }
    },
    removeAoHoaItem: function(itemId) {
      this.dsAoHoa = this.dsAoHoa.filter((item) => item.ID !== itemId)
    },
    removeIDGItem: function(itemId) {
      this.dsIDG = this.dsIDG.filter((item) => item.ID !== itemId)
    },
    removeContainerK8Item: function(itemId) {
      this.dsContainerK8 = this.dsContainerK8.filter((item) => item.ID !== itemId)
    },
    removeBackupItem: function(itemId) {
      this.dsBackup = this.dsBackup.filter((item) => item.ID !== itemId)
    },

    setCurrentTab(tab) {
      this.currentTab = tab // Cập nhật tab hiện tại
    },
    openModal(tab) {
      this.currentTab = tab // Đảm bảo tab hiện tại được cập nhật
      this.isModalOpen = true // Mở modal
      this.loadTabData(tab) // Load dữ liệu của tab hiện tại
    },
    closeModal() {
      this.isModalOpen = false // Đóng modal
    },
    saveModal() {
      // Xử lý lưu dữ liệu từ modal
      if (this.currentTab === 1) {
        // Lấy các đối tượng được chọn từ grid dsAoHoaAll
        const selectedRecords = this.$refs.grdAoHoaAll.getSelectedRecords()
        if (selectedRecords && selectedRecords.length > 0) {
          // Thêm các đối tượng được chọn vào dsAoHoa
          this.dsAoHoa = [...this.dsAoHoa, ...selectedRecords]
          this.$root.toastSuccess(`${selectedRecords.length} đối tượng đã được thêm vào danh sách Ảo hóa.`)
          this.aoHoa = this.buildDanhSachAoHoa()
          console.log(this.aoHoa)
        } else {
          this.$root.toastError('Không có đối tượng nào được chọn.')
        }
      } else if (this.currentTab === 2) {
        // Xử lý lưu dữ liệu cho tab Container - K8s
        console.log('Lưu dữ liệu cho tab Container - K8s')
      } else if (this.currentTab === 3) {
        // Xử lý lưu dữ liệu cho tab IDG
        const selectedRecords = this.$refs.grdIDGAll.getSelectedRecords()
        if (selectedRecords && selectedRecords.length > 0) {
          // Thêm các đối tượng được chọn vào dsAoHoa
          this.dsIDG = [...this.dsIDG, ...selectedRecords]
          this.$root.toastSuccess(`${selectedRecords.length} đối tượng đã được thêm vào danh sách IDG.`)
        } else {
          this.$root.toastError('Không có đối tượng nào được chọn.')
        }
        console.log('Lưu dữ liệu cho tab IDG')
      } else if (this.currentTab === 4) {
        // Xử lý lưu dữ liệu cho tab Backup
        const selectedRecords = this.$refs.grdBackupAll.getSelectedRecords()
        if (selectedRecords && selectedRecords.length > 0) {
          // Thêm các đối tượng được chọn vào dsAoHoa
          this.dsBackup = [...this.dsBackup, ...selectedRecords]
          this.$root.toastSuccess(`${selectedRecords.length} đối tượng đã được thêm vào danh sách backup.`)
        } else {
          this.$root.toastError('Không có đối tượng nào được chọn.')
        }
      }
      console.log(`Lưu dữ liệu cho tab: ${this.currentTab}`)
      this.isModalOpen = false // Đóng modal sau khi lưu
    },
    async loadTabData(tab) {
      // Load dữ liệu của tab hiện tại
      switch (tab) {
        case 1:
          this.loadAoHoaAll()
          break
        case 2:
          // this.loadContainerK8All();
          break
        case 3:
          this.loadIDGAll()
          break
        case 4:
          this.loadBackupAll()
          break
        default:
          console.log('Tab không hợp lệ')
      }
    },
    async loadAoHoaAll() {
      this.dsAoHoaAll = await this.apiDanhSachAoHoaAll()
    },
    async loadIDGAll() {
      this.dsIDGAll = await this.apiDanhSachIdgAll()
    },
    async loadBackupAll() {
      this.dsBackupAll = await this.apiDanhSachBackupAll()
    },
    buildDanhSachAoHoa() {
      if (this.dsAoHoa == null || this.dsAoHoa.length < 1) {
        return []
      }
      if (this.dsAoHoa && this.dsAoHoa.length > 0) {
        this.dsAoHoa.forEach((item, index) => {
          if (!item.hasOwnProperty('SOTHUTU')) {
            item.SOTHUTU = index + 1 // Gán giá trị mặc định nếu không có key SOTHUTU
          }
        })
      }
      console.log('dsAoHoa', this.dsAoHoa)
      const danhsach_aohoa = this.dsAoHoa.map((item) => ({
        id: item.ID,
        soThuTu: item.SOTHUTU, // Kiểm tra và gán giá trị mặc định
        mayAoId: item.VM_ID ?? item.MAYAO_ID,
        donViVhId: item.DONVIVH_ID,
        nguoiQtriId: item.NGUOI_QUANTRI_ID,
        ipAddressId: item.IPADDRESS_ID,
        trangThaiThuHoi: this.currentItem.trangThaiThuHoiId
      }))
      return danhsach_aohoa
    },
    buildDanhSachK8() {
      if (this.dsContainerK8 == null || this.dsContainerK8.length < 1) {
        return []
      }
      if (this.dsContainerK8 && this.dsContainerK8.length > 0) {
        this.dsContainerK8.forEach((item, index) => {
          if (!item.hasOwnProperty('SOTHUTU')) {
            item.SOTHUTU = index + 1 // Gán giá trị mặc định nếu không có key SOTHUTU
          }
        })
      }
      const danhsach_k8 = this.dsContainerK8.map((item) => ({
        id: item.ID,
        soThuTu: item.SOTHUTU,
        tenHaTang: item.MAYAOID,
        donViVhId: item.DONVIVH_ID,
        nguoiQtriId: item.NGUOI_QUANTRI_ID,
        ipAddressId: item.IPADDRESS_ID,
        trangThaiThuHoi: this.currentItem.trangThaiThuHoiId
      }))
      return danhsach_k8
    },
    buildDanhSachIdg() {
      if (this.dsIDG == null || this.dsIDG.length < 1) {
        return []
      }
      if (this.dsIDG && this.dsIDG.length > 0) {
        this.dsIDG.forEach((item, index) => {
          if (!item.hasOwnProperty('SOTHUTU')) {
            item.SOTHUTU = index + 1 // Gán giá trị mặc định nếu không có key SOTHUTU
          }
        })
      }
      const danhsach_idg = this.dsIDG.map((item) => ({
        id: item.ID,
        soThuTu: item.SOTHUTU,
        tenHaTang: item.TENHATANG,
        congNghe: item.CONGNGHE,
        donViVhId: item.DONVIVH_ID,
        nguoiQtriId: item.NGUOI_QUANTRI_ID,
        loaiGoi: item.LOAIGOI,
        giaTriGoi: item.GIATRI_GOI,
        trangThaiThuHoi: this.currentItem.trangThaiThuHoiId
      }))
      return danhsach_idg
    },
    buildDanhSachBackup() {
      // console.log('buildDanhSachBackup', this.dsBackup)
      if (this.dsBackup == null || this.dsBackup.length < 1) {
        return []
      }
      if (this.dsBackup && this.dsBackup.length > 0) {
        this.dsBackup.forEach((item, index) => {
          if (!item.hasOwnProperty('SOTHUTU')) {
            item.SOTHUTU = index + 1 // Gán giá trị mặc định nếu không có key SOTHUTU
          }
        })
      }
      const danhsach_backup = this.dsBackup.map((item) => ({
        id: item.ID,
        soThuTu: item.SOTHUTU,
        tenHaTang: item.TENHATANG,
        loaiHaTangId: item.LOAIHATANG_ID,
        // congNghe: item.CONGNGHE_ID,
        backupId: item.BACKUP_ID,
        donViVhId: item.DONVIVH_ID,
        nguoiQtriId: item.NGUOI_QUANTRI_ID,
        trangThaiThuHoi: this.currentItem.trangThaiThuHoiId
      }))
      // console.log('danhsach_backup', danhsach_backup)
      return danhsach_backup
    },
    getHieuLucText: function(value) {
      return value == 1 ? 'Hiệu lực' : 'Không hiệu lực'
    },
    getColumnHieuLucTemplate: function() {
      return function() {
        return {
          template: {
            template: `<span>{{ getText() }}</span>`,
            methods: {
              getText: function() {
                return this.$parent.$parent.$parent.getHieuLucText(this.data.HIEU_LUC)
              }
            }
          }
        }
      }
    },
    apiGetDonViCha: async function(pId) {
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
    apiCongNgheSuDung: async function() {
      let result = []
      try {
        let rs = await this.$root.context.get('/web-ecms/danhmuc/ha-tang-cong-nghe')
        result = rs.data
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result
    },
    
    apiDanhSachTrangThaiYeuCau: async function() {
      let result = []
      try {
        let rs = await this.$root.context.post('/web-ecms/danhmuc/hatang_mang', {
          loaiDanhMuc: 'HATANG_TRANGTHAI_THUHOI',
          thamSo1: null,
          thamSo2: null
        })
        result = rs.data
      } catch (e) {
        this.handleApiError(e)
      }
      return result
    },
    apiCumDichVu: async function(pId) {
      let result = []
      if (pId == null || pId < 1) {
        return result
      }
      try {
        let rs = await this.$root.context.post('/web-ecms/hatang/thu-hoi-tai-nguyen/get-ds-cumdv-by-spdv', {
          idSpdv: pId
        })
        result = rs.data
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result
    },
    apiSendYeuCau: async function(pId) {
      let result = []
      if (pId == null || pId < 1) {
        return result
      }
      try {
        let rs = await this.$root.context.post('/web-ecms/hatang/thu-hoi-tai-nguyen/send-yeucau-thuhoi', {
          yeuCauId: pId
        })
        result = rs.data
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result
    },
    apiDanhSachAoHoa: async function(pId) {
      let result = []
      if (pId == null || pId < 1) {
        return result
      }
      try {
        let rs = await this.$root.context.post('/web-ecms/hatang/thu-hoi-tai-nguyen/get-aohoa-yeucau', {
          yeuCauId: pId
        })
        result = rs.data
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result
    },

    apiDanhSachAoHoaAll: async function() {
      let result = []
      try {
        let rs = await this.$root.context.post('/web-ecms/hatang/thu-hoi-tai-nguyen/get-ds-aohoa')
        result = rs.data
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result
    },
    apiDanhSachK8: async function(pId) {
      let result = []
      if (pId == null || pId < 1) {
        return result
      }
      try {
        let rs = await this.$root.context.post('/web-ecms/hatang/thu-hoi-tai-nguyen/get-k8s-yeucau', { yeuCauId: pId })
        result = rs.data
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result
    },

    apiDanhSachIdg: async function(pId) {
      let result = []
      if (pId == null || pId < 1) {
        return result
      }
      try {
        let rs = await this.$root.context.post('/web-ecms/hatang/thu-hoi-tai-nguyen/get-idg-yeucau', { yeuCauId: pId })
        result = rs.data
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result
    },
    apiDanhSachIdgAll: async function() {
      let result = []
      try {
        let rs = await this.$root.context.post('/web-ecms/hatang/thu-hoi-tai-nguyen/get-ds-idg', null)
        result = rs.data
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result
    },
    apiDanhSachBackup: async function(pId) {
      let result = []
      if (pId == null || pId < 1) {
        return result
      }
      try {
        let rs = await this.$root.context.post('/web-ecms/hatang/thu-hoi-tai-nguyen/get-backup-yeucau', {
          yeuCauId: pId
        })
        result = rs.data
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result
    },
    apiDanhSachBackupAll: async function() {
      let result = []
      try {
        let rs = await this.$root.context.post('/web-ecms/hatang/thu-hoi-tai-nguyen/get-ds-backup', null)
        result = rs.data
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result
    },
    // apiIDG: async function(pId) {
    //   let result = []
    //   if (pId == null || pId < 1) {
    //     return result
    //   }
    //   try {
    //     let rs = await this.$root.context.get('/web-ecms/danhmuc/su-dung/ha-tang-mang-idg', { haTangMangId: pId })
    //     result = rs.data
    //   } catch (e) {
    //     this.$toast.error(e.response.data.message_detail)
    //   }
    //   return result
    // },
    apiChiTietPhieuYeuCau: async function(pId) {
      let result = []
      if (pId == null || pId < 1) {
        return result
      }
      try {
        let rs = await this.$root.context.post('/web-ecms/hatang/thu-hoi-tai-nguyen/get-phieu-yeucau-by-id', {
          yeuCauId: pId
        })
        result = rs.data
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result
    },
    // apiSubnet: async function(pId) {
    //   let result = []
    //   if (pId == null || pId < 1) {
    //     return result
    //   }
    //   try {
    //     let rs = await this.$root.context.get('/web-ecms/danhmuc/su-dung/ha-tang-mang-subnet', { haTangMangId: pId })
    //     result = rs.data
    //   } catch (e) {
    //     this.$toast.error(e.response.data.message_detail)
    //   }
    //   return result
    // },
    // apiIPAddress: async function(pId) {
    //   let result = [];
    //   if (pId==null || pId<1) {
    //     return result;
    //   }
    //   try {
    //     let rs = await this.$root.context.get("/web-ecms/danhmuc/su-dung/ha-tang-mang-ip-address", {haTangMangId:pId});
    //     result = rs.data;
    //   } catch(e) {
    //     this.$toast.error(e.response.data.message_detail)
    //   }
    //   return result;
    // },

    apiListDanhSach: async function() {
      let result = []
      try {
        let rs = await this.$root.context.post('/web-ecms/hatang/thu-hoi-tai-nguyen/get-ds-phieu-yeucau-thuhoi')
        result = rs.data
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result
    },
    apiDanhSachSPDV: async function() {
      let result = []
      try {
        let rs = await this.$root.context.post('/web-ecms/hatang/thu-hoi-tai-nguyen/get-spdv')
        result = rs.data
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result
    },
    apiSave: async function(para) {
      let result = false
      let ngayHTFM
      let ngayGoiFM
      let ngayTHFM

      if (para.ngayHoanThanh instanceof Date) {
        try {
          ngayHTFM = moment(para.ngayHoanThanh).format('DD/MM/yyyy')
        } catch (e) {
          this.currentItem.ngayHoanThanh = null;
          alert('Vui lòng nhập ngày tháng năm đúng định dạng dd/MM/yyyy.');
        }
        ngayHTFM = moment(para.ngayHoanThanh).format('DD/MM/yyyy')
      } else {
        ngayHTFM = para.ngayHoanThanh
      }

      if (para.ngayGoi instanceof Date) {
        try {
          ngayGoiFM = moment(para.ngayGoi).format('DD/MM/yyyy')
        } catch (e) {
          this.currentItem.ngayGoi = null;
          alert('Vui lòng nhập ngày tháng năm đúng định dạng dd/MM/yyyy.');
          
        }
        
      } else {
        ngayGoiFM = para.ngayGoi
      }
      if (para.ngayBatDauThuHoi instanceof Date) {
        try {
          ngayTHFM = moment(para.ngayBatDauThuHoi).format('DD/MM/yyyy')
        } catch (e) {
          this.currentItem.ngayBatDauThuHoi = null;
          alert('Vui lòng nhập ngày tháng năm đúng định dạng dd/MM/yyyy.');
        }
        
      } else {
        ngayTHFM = para.ngayBatDauThuHoi
      }
      let fileId = this.id_taptin ? this.id_taptin :  para.fileId 

      try {
        let postData = {
          id: para.id,
          soPhieu: para.ten,
          trangThaiPhieu: para.trangThaiYeuCauId,
          donViId: para.donViYeuCauId,
          nguoiYcId: para.nguoiGoiId,
          ngayYc: ngayGoiFM,
          ngayThuHoi: ngayTHFM,
          spdvId: para.sanPhamDichVuId,
          spdvTen: para.tenSPDV,
          cumDichVu: para.cumDichVuId,
          soCuThuHoi: para.socu,
          ngayHt: ngayHTFM,
          thucHien: para.thucHien,
          thuHoiIp: para.thuHoiIp,
          fileYc: fileId,
          ghiChu: para.ghiChu,
          aoHoa: this.buildDanhSachAoHoa(),
          k8s: this.buildDanhSachK8(),
          idg: this.buildDanhSachIdg(),
          backup: this.buildDanhSachBackup()
        }
        let path =
          para.id > 0
            ? '/web-ecms/hatang/thu-hoi-tai-nguyen/update-phieu-yeucau-thuhoi'
            : '/web-ecms/hatang/thu-hoi-tai-nguyen/create-phieu-yeucau-thuhoi'
        let rs = await this.$root.context.post(path, postData)
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
    apiDelete: async function(id) {
      let result = false
      try {
        let rs = await this.$root.context.post('/web-ecms/hatang/thu-hoi-tai-nguyen/delete-phieu-yeucau-thuhoi', {
          yeuCauId: id
        })
        result = rs.error_code == 'BSS-00000000'
      } catch (e) {
        this.$root.toastError(e.response.data.message_detail)
        result = false
      }
      return result
    },
    triggerFileInput() {
      if (!this.buttons.btnEdit.enabled) {
        this.$refs.fileInput.click()
      }
      
    },
    onChangeFile(e) {
      this.files = null
      this.files = e.target.files
      console.log(this.files)
      if (this.files.length > 0) {
        for (let file of this.files) {
          let extension = file.name.slice(-3)
          if (extension === 'exe' || extension === 'msi' || extension === 'bat' || extension === '.js')
            return this.$toast.error('Vui lòng không upload file có định dạng .exe/.msi/.bat!')
        }
        this.fileName = this.files[0].name
      }
    },
    async uploadFile() {
      if (this.files != null) {
        let formData = new FormData()
        formData.append('p_module', 'HATANGCNTT')
        formData.append('p_file', this.files[0])
        let rs = await this.uploadFileToMinio(formData)
        if (rs == null) {
          return false
        } else {
          this.id_taptin = rs.id_taptin
          this.$toast.success('Upload file thành công!'); // Thông báo thành công
          return true
        }
      }
    },
    async uploadFileToMinio(formData) {
      try {
        this.loading(true)
        let headers = {
          'App-secret': this.appsecret
        }
        let url = `/app-banhang/quanlyfile/upload_file`
        let response = await this.axios.post(url, formData, { headers })
        return response.data.data
      } catch (error) {
        console.log(error)
        // this.$toast.error('Có lỗi xảy ra trong quá trình upload file!');
        return null
      } finally {
        this.loading(false)
      }
    },
    async getFile() {
      try {
        this.loading(true)
        if (this.currentItem.urlFile) {
          let headers = {
            'App-secret': this.appsecret
          }
          let url = `/app-banhang/quanlyfile/get_file`
          let response = await this.axios.post(url, { p_path: this.currentItem.urlFile }, { headers })
          if (response.data.data != undefined) {
            this.url_taptin = response.data.data.url_taptin
            this.fileName = response.data.data.ten_taptin
            return true
          } else {
            this.url_taptin = null
            return false
          }
        }
      } catch (error) {
        console.log(error)
      } finally {
        this.loading(false)
      }
    },
    async downloadFile() {
      if (this.currentItem.urlFile && this.files == null) {
        let rs = await this.getFile()
        if (rs) {
          const response = await fetch(this.currentItem.urlFile)
          const blob = await response.blob()
          const blobUrl = URL.createObjectURL(blob)
          const a = document.createElement('a')
          a.href = blobUrl
          a.download = this.fileName
          document.body.appendChild(a)
          a.click()
          document.body.removeChild(a)
        } else {
          this.$toast.error('Không tìm thấy file!')
        }
      } else {
        if (this.files) {
          const file = this.files[0]
          const url = URL.createObjectURL(file)
          const a = document.createElement('a')
          a.href = url
          a.download = file.name
          document.body.appendChild(a)
          a.click()
          document.body.removeChild(a)
          URL.revokeObjectURL(url)
        } else {
          this.$toast.error('Không tìm thấy file!')
        }
      }
    },
    async deleteFileFromMinio() {
      try {
        this.loading(true)
        let url = `/web-suco/storage/deleteFile`
        let response = await this.$root.context.post(url, { fileSource: this.currentItem.urlFile })
        if (response.error_code === 'BSS-00000000' && response.data != undefined) {
          return true
        } else return false
      } catch (error) {
        console.log(error)
        return false
      } finally {
        this.loading(false)
      }
    },

    enableControls: function(args) {
      this.controls.txtTen.enabled = args
      this.controls.txtMaHaTang.enabled = args
      // this.controls.cboIDC.enabled = args
      this.controls.cboSPDV.enabled = args
      this.controls.txtGhiChu.enabled = args
      this.controls.chkHieuLuc.enabled = args
      // this.txtNgayHoanThanh.enabled = args
      this.buttons.btnSave.enabled = args
      this.buttons.btnAddNew.enabled = !args
      this.buttons.btnCancel.enabled = this.buttons.btnSave.enabled
      this.buttons.btnDelete.enabled = !args

      if (this.currentItem.id > 0) this.buttons.btnEdit.enabled = !this.buttons.btnSave.enabled
    },

    onShowDataZone: async function(pId) {
      if (pId > 0) {
        try {
          this.$root.showLoading(true)

          // this.dsSmartCloud= await this.apiSmartCloud(pId);
          // this.dsIDG= await this.apiIDG(pId);
          // this.dsSubnet= await this.apiSubnet(pId);
          //this.dsIPAddress= await this.apiIPAddress(pId);
          // this.dsThietBi= await this.apiThietBi(pId);
          // this.dsHistory=   await this.apiHistory(pId);
        } finally {
          this.$root.showLoading(false)
        }
      }
    },

    chooseFiles(event) {
      event.preventDefault()
      // document.getElementById("fileUpload").click();
      this.$refs.fileUpload.click()
    },
    onFileChanged: function(event) {
      const file = event.target.files[0]
      console.log('file :', file)
      if (file) {
        const reader = new FileReader()

        // Khi FileReader hoàn thành việc đọc file
        reader.onload = () => {
          const fullBase64 = reader.result
          this.currentItem.avatar = fullBase64.split(',')[1] // Chuỗi Base64 (bao gồm 'data:image/...;base64,')
          this.imageItem = fullBase64
          //this.buttons.btnAddImage = 'hidden';
          this.buttons.imgAvatar = 'img-fluid'
        }

        reader.onerror = (error) => {
          console.error('Lỗi khi đọc file:', error)
        }

        reader.readAsDataURL(file)
      }
    },
    setCurrentItem: function(item) {
      this.currentItem.id = item.ID
      this.currentItem.ten = item.TEN
      this.currentItem.socu = item.SOCUTHUHOI
      this.currentItem.trangThaiYeuCauId = item.TRANGTHAI_YEUCAU_ID
      this.currentItem.trangthai = item.TRANGTHAI_YEUCAU
      this.currentItem.donvi_yeucau = item.DONVI_YC
      this.donViYeuCauId = item.DONVI_ID
      this.currentItem.nguoiGoiId = item.NGUOI_YC_ID
      this.currentItem.nguoiGoi = item.NGUOI_GOI
      this.currentItem.tenSPDV = item.TEN_SPDV
      this.currentItem.urlFile = item.PATH_FILE
      this.currentItem.fileId = item.FILE_YC
      this.currentItem.tenfile = item.NAME_FILE 
      // this.currentItem.cumDichVu = item.CUMDICHVU;
      this.currentItem.cumDichVuId = item.CUMDICHVU_ID
      this.currentItem.ngayGoi = item.NGAY_GOI
      this.currentItem.ngayHoanThanh = item.NGAY_HT
      this.currentItem.ngayBatDauThuHoi = item.NGAY_THUHOI
      this.currentItem.ghiChu = item.GHI_CHU
      this.currentItem.sanPhamDichVuId = item.SPDV_ID
      this.currentItem.yKienXacNhan = item.Y_KIEN_XACNHAN
      this.currentItem.ngatTaiNguyen = item.NGAT_TAINGUYEN
      this.currentItem.yKienThuHoi = item.Y_KIEN_THUCHIEN_MAYAO
      this.currentItem.yKienThuHoiBacKup = item.Y_KIEN_THUCHIEN_BACKUP
      this.currentItem.trangThaiThuHoi = item.TRANGTHAI_THUHOI_IP
      this.onShowDataZone(item.ID)
      if (this.currentItem.avatar != null && this.currentItem.avatar != '') {
        this.imageItem = 'data:image/*;base64,' + this.currentItem.avatar
        //this.buttons.btnAddImage = 'hidden';
        this.buttons.imgAvatar = 'img-fluid'
      } else {
        //this.buttons.btnAddImage = '';
        this.buttons.imgAvatar = 'hidden'
      }
    },
    setCurrentItemDefault: function() {
      const currentDate = new Date().toLocaleDateString('vi-VN', {
        day: '2-digit',
        month: '2-digit',
        year: 'numeric'
      })
      this.currentItem.id = 0
      this.currentItem.ten = null
      this.currentItem.socu = null
      this.currentItem.trangThaiYeuCauId = null
      this.currentItem.trangthai = null
      this.currentItem.donvi_yeucau = null
      this.currentItem.tenSPDV = null
      this.currentItem.nguoiGoi = null
      this.currentItem.cumDichVu = null
      this.currentItem.tenfile = null
      this.currentItem.ngayGoi = null
      this.currentItem.ngayHoanThanh = null
      this.currentItem.ngayBatDauThuHoi = null
      this.currentItem.ghiChu = null
      this.currentItem.sanPhamDichVuId = 0
      this.currentItem.yKienXacNhan = null
      this.currentItem.ngatTaiNguyen = null
      this.currentItem.yKienThuHoi = null
      this.currentItem.yKienThuHoiBacKup = null
      this.currentItem.trangThaiThuHoi = null
      this.currentItem.nguoiGoi = this.userInfo.nhanVienTen
      this.currentItem.donViYeuCauId = this.userInfo.donViId
      this.currentItem.donvi_yeucau = this.userInfo.donViTen
      this.currentItem.nguoiGoiId = this.userInfo.nhanVienId

      this.currentItem.ngayGoi = currentDate
      this.dsAoHoa = []
      this.dsContainerK8 = []
      this.dsIDG = []
      this.dsBackup = []

      // this.currentItem.maHaTang = null;
      // this.currentItem.IDCId = 0;
      // this.currentItem.sanPhamDichVuId = 0;
      // this.currentItem.ghiChu = null;
      // this.currentItem.hieuLuc =1;
      // this.currentItem.avatar ='';
      // this.currentItem.nguoiQuanLyId= this.userInfo.nhanVienId;
      // this.currentItem.nguoiQuanLy=this.userInfo.nhanVienTen;
      // this.currentItem.phongBanQuanLyId= this.userInfo.donViId;
      // this.currentItem.phongBanQuanLy= this.userInfo.donViTen;
      // this.currentItem.donViQuanlyId= this.userInfo.donViChaId;
      // this.currentItem.donViQuanly= this.userInfo.donViChaTen;
      // this.imageItem=null;
      // this.dsHaTang=[];
      // this.dsSmartCloud=[];
      // this.dsIDG=[];
      // this.dsSubnet=[];
      // this.dsIPAddress=[];
      // this.dsThietBi=[];
      // this.dsHistory=[];
    },

    validateControls: function() {
      let msg = ''
     
      if (this.currentItem.socu < 1) {
        msg += 'Chưa nhập sở cứ '
         msg += '\n\r'
      }
      if (this.currentItem.sanPhamDichVuId < 1) {
       
        msg += 'Chưa chọn sản phẩm dịch vụ '
         msg += '\n\r'
      } 
      if (!this.currentItem.ngayGoi) {
          
          msg += 'Ngày gửi không hợp lệ ';
           msg += '\n\r'
      }
      if(!this.currentItem.ngayBatDauThuHoi) {
          msg += 'Ngày bắt đầu thu hồi không hợp lệ ';
          msg += '\n\r';
        }
  

      if (msg != '') this.$root.toastError(msg)
      return msg == ''
    },

    grdItems_RowSelected: async function(args) {
      if (this.buttons.btnSave.enabled) {
        return
      }
      this.setCurrentItem(args.data)
      this.dsAoHoa = await this.apiDanhSachAoHoa(this.currentItem.id)
      this.dsContainerK8 = await this.apiDanhSachK8(this.currentItem.id)
      this.dsIDG = await this.apiDanhSachIdg(this.currentItem.id)
      this.dsBackup = await this.apiDanhSachBackup(this.currentItem.id)
      this.currentItem
      if ( this.currentItem.trangThaiYeuCauId == 0) {
        this.buttons.btnThucHien.enabled = true
      } else {
        this.buttons.btnThucHien.enabled = false
      }
    },
    grdItems_RowDeselected: async function(args) {
      if (this.buttons.btnSave.enabled) {
        return
      }
      let items = this.grdItems.getSelectedRecords()
      if (!(items == null || items.length == 0)) {
        this.setCurrentItem(items[0])
      } else {
        this.setCurrentItemDefault()
      }
    },

    onAddNew: async function(args) {
      this.add = 1
      this.enableControls(true)
      this.setCurrentItemDefault()
      this.currentItem.ngayTao = new Date()
      this.currentItem.trangThaiYeuCauId = 0
      // this.txtNgayHoanThanh.enabled = false
    },
    onSave: async function(args) {
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
            // Upload file mới nếu có
            let uploadSuccess = true
            if (this.files != null) {
              uploadSuccess = await this.uploadFile()
            }

            if (!uploadSuccess) {
              this.$root.toastError('Có lỗi xảy ra trong quá trình upload file!')
              return
            }
            let rs = await this.apiSave(this.currentItem)
            if (rs) {
              this.danhSachYeuCau = await this.apiListDanhSach();
               // Tìm và focus vào item vừa được cập nhật
                  const updatedItem = this.danhSachYeuCau.find(item => item.ID === this.currentItem.id);
                  if (updatedItem) {
                    this.setCurrentItem(updatedItem);

                    // Chọn lại item trên grid
                    const rowIndex = this.danhSachYeuCau.findIndex(item => item.ID === updatedItem.ID);
                    if (rowIndex !== -1) {
                      this.$refs.grdItems.selectRow(rowIndex); // Chọn hàng trên grid
                    }
                  }
              this.$root.toastSuccess('Cập nhật thông tin thành công ')
            }
            this.enableControls(!rs)
          } finally {
            this.$root.showLoading(false)
          }
        }
      }
    },
    onChangeSPDV: async function(args) {},
    onCancel: async function(args) {
      this.add = 0
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
        this.dsAoHoa = await this.apiDanhSachAoHoa(this.currentItem.id)
        this.dsContainerK8 = await this.apiDanhSachK8(this.currentItem.id)
        this.dsIDG = await this.apiDanhSachIdg(this.currentItem.id)
        this.dsBackup = await this.apiDanhSachBackup(this.currentItem.id)
      } else {
        this.setCurrentItemDefault()
      }
      this.enableControls(false)
    },
    onEdit: async function(args) {
      this.enableControls(true)
    },
    onThucHien: async function(args) {
      let items = this.grdItems.getSelectedRecords()
      let msg = `Bạn có muốn thực hiện gửi yêu cầu thu hồi đã chọn không?`
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
            isSuccess = await this.apiSendYeuCau(item.ID)
            if (!isSuccess) {
              break
            }
          }
          if (isSuccess) {
            this.setCurrentItemDefault()
            this.danhSachYeuCau = await this.apiListDanhSach()
            this.enableControls(false)
            this.$root.toastSuccess('Thực hiện thông tin thành công')
          }
        } finally {
          this.$root.showLoading(false)
        }
      }      

    },
    onDelete: async function(args) {
      //this.dsSelected.length
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
            this.danhSachYeuCau = await this.apiListDanhSach()
            this.enableControls(false)
            this.$root.toastSuccess('Xóa thông tin thành công')
          }
        } finally {
          this.$root.showLoading(false)
        }
      }
    }
  }
}
</script>
<style>
.check-action {
  top: 3px;
}

.e-rowcell .check-action input[type='checkbox']:checked:disabled + .name:before {
  background: #fff;
  border-color: #0176ff;
  color: #0176ff;
}

.e-rowcell .check-action input[type='checkbox']:disabled + .name:before {
  background: #fff;
  border-color: #0176ff;
}

.modal-90w {
  max-width: 90%;
}
</style>
