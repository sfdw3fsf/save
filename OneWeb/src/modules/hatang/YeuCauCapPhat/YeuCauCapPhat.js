import AohoaComponent from './TabAoHoa/index.vue' // import component Interface
import Containerk8sComponent from './TabContainerK8s/index.vue' // import component Power
import IdgComponent from './TabIDG/index.vue' // import component TabConsole
import BackupComponent from './TabBackup/index.vue' // import component TabConsole
import {
  DEFAULT_FORM_DATA,
  ROOT_API,
  YEU_CAU_CP_TABLES,
  TRANG_THAI_CP,
  KIENTRUC_THANHPHAN,
  APP_SECRET,
  DATE_FORMAT
} from './constants'
import moment from 'moment'

export default {
  components: {
    AohoaComponent,
    Containerk8sComponent,
    IdgComponent,
    BackupComponent
  },
  data: function() {
    return {
      imageItem: '',
      currentTab: 1,
      dsAoHoaCP: [],
      colsAoHoaCP: YEU_CAU_CP_TABLES.AO_HOA_CP_COLS,
      dsContainerK8sCP: [],
      colsContainerK8sCP: YEU_CAU_CP_TABLES.CONTAINER_CP_COLS,
      dsIDGCP: [],
      colsIDGCP: YEU_CAU_CP_TABLES.IDG_CP_COLS,
      dsBackupCP: [],
      colsBackupCP: YEU_CAU_CP_TABLES.BACKUP_CP_COLS,
      dsYeuCauCP: [],
      colsYeuCauCP: YEU_CAU_CP_TABLES.YEU_CAU_CP_COLS,
      aoHoaList: [],
      containerList: [],
      IDGList: [],
      backupList: [],
      currentItem: {
        id: 0,
        sophieu: '',
        trangthai: null,
        ten_dv_yc: '',
        donvi_id: null,
        nguoi_yc: '',
        nguoi_yc_id: null,
        // ngay_yc: moment().format('YYYY-MM-DD'),
        ngay_yc: moment().format(DATE_FORMAT),
        ngay_ht: '',
        ghi_chu: '',
        mucdich_yc: '',
        spdv_id: null,
        spdv_ten: '',
        hatangmang_id: null
      },
      userInfo: {},
      tab_index: 1, // Chỉ số tab đang chọn
      dsSanPhamDichVu: [], // Danh sách sản phẩm dịch vụ
      dsHaTangMang: [], // Danh sách hạ tầng mạng
      files: [],
      controls: {
        txtGhiChu: {
          enabled: false,
          invalid: true
        },
        txtSoPhieu: {
          enabled: false,
          invalid: true
        },
        txtMucDichSuDung: {
          enabled: false,
          invalid: true
        },
        txtTrangThaiCapPhat: {
          enabled: false,
          invalid: true
        },
        cboSanPhamDichVu: {
          enabled: false,
          invalid: true
        },
        txtDonViYeuCau: {
          enabled: false,
          invalid: true
        },
        txtTenSanPhamDichVu: {
          enabled: false,
          invalid: true
        },
        txtNguoiGui: {
          enabled: false,
          invalid: true
        },
        cboHaTangMang: {
          enabled: false,
          invalid: true
        },
        txtNgayGui: {
          enabled: false,
          invalid: true
        },
        txtNgayHoanThanh: {
          enabled: false,
          invalid: true
        },
        chkHieuLuc: {
          enabled: false,
          invalid: true
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
        btnSend: {
          enabled: false
        },
        btnAddImage: {
          enabled: true
        },
        imgAvatar: {
          enabled: true
        }
      }
    }
  },
  computed: {
    grdItems: function() {
      return this.$refs.grdItems
    },
    enableSendBtn() {
      return this.currentItem.id > 0 && (this.currentItem.trangthai == 0 || this.currentItem.trangthai == 5) // 1: da gui | 0: chua gui
    },
    trangthaiphieu() {
      return TRANG_THAI_CP.find((el) => el.value == this.currentItem.trangthai)?.name ?? ''
    }
  },
  watch: {
    'currentItem.ngay_yc': function(newVal, oldVal) {
      if (newVal !== oldVal) {
        if (!newVal) {
          this.currentItem.ngay_yc = moment().format(DATE_FORMAT)
        } else {
          const formatDate = moment(newVal, ['YYYY-MM-DD', DATE_FORMAT], true).format(DATE_FORMAT)
          this.currentItem.ngay_yc = formatDate
        }
      }
    },
    'currentItem.ngay_ht': function(newVal, oldVal) {
      if (newVal !== oldVal) {
        if (!newVal) {
          this.currentItem.ngay_ht = null
        } else {
          const formatDate = moment(newVal, ['YYYY-MM-DD', DATE_FORMAT], true)
          this.currentItem.ngay_ht = formatDate.isValid() ? formatDate.format(DATE_FORMAT) : null
        }
      }
    },
    'currentItem.id': function(newVal, oldVal) {
      if (newVal !== oldVal && !!newVal) {
        const path = this.currentItem.duong_dan
        if (path) {
          this.getFileMinio(path).then((res) => {
            this.files = [{ name: 'Tải file', url: res, file: null }]
          })
        }
        this.getYeuCauRelationInfo(newVal)
        this.enableControls(false)
      }

      const isEnabled = newVal > 0
      this.buttons.btnEdit.enabled = isEnabled
      this.buttons.btnDelete.enabled = isEnabled
      // this.buttons.btnSend.enabled = isEnabled
    },
    'currentItem.spdv_id': function(newVal) {
      if (newVal == -1 && this.buttons.btnSave.enabled == true) {
        this.controls.txtTenSanPhamDichVu.enabled = true
      } else {
        this.controls.txtTenSanPhamDichVu.enabled = false
      }

      if (newVal != -1) {
        this.currentItem.spdv_ten = ''
      }
    }
  },
  created: async function() {
    await this.loadInfo()
    this.getUserInfo()
  },
  methods: {
    //-------- common functions ------------//
    handleErrorResponse(error) {
      if (!(error.response == null || error.response == undefined)) {
        if (!(error.response.data == null || error.response.data == undefined)) {
          this.$root.toastError(error.response.data.message_detail)
        }
      }
    },
    //--------- End ------------//
    deleteAoHoaItem(item) {
      if (item.id) {
        this.deleteNCAoHoa(item.id).then((res) => {
          if (res) {
            this.$root.toastSuccess('Xóa thành công')
            this.aoHoaList = this.aoHoaList.filter((el) => el.id != item.id)
          } else {
            this.$root.toastError('Xóa thất bại')
          }
        })
      } else if (item.tmpId) {
        this.$root.toastSuccess('Xóa thành công')
        this.aoHoaList = this.aoHoaList.filter((el) => el.tmpId != item.tmpId)
      }
      // this.aoHoaList = this.aoHoaList.filter((el) => el.tmpId != item.tmpId || el.id != item.id)
    },
    deleteContainerItem(item) {
      // this.containerList = this.containerList.filter((el) => el.tmpId != item.tmpId || el.id != item.id)
      if (item.id) {
        this.deleteNCK8s(item.id).then((res) => {
          if (res) {
            this.$root.toastSuccess('Xóa thành công')
            this.containerList = this.containerList.filter((el) => el.id != item.id)
          } else {
            this.$root.toastError('Xóa thất bại')
          }
        })
      } else if (item.tmpId) {
        this.$root.toastSuccess('Xóa thành công')
        this.containerList = this.containerList.filter((el) => el.tmpId != item.tmpId)
      }
    },
    deleteIDGItem(item) {
      // this.IDGList = this.IDGList.filter((el) => el.tmpId != item.tmpId || el.id != item.id)
      if (item.id) {
        this.deleteNCIDG(item.id).then((res) => {
          if (res) {
            this.$root.toastSuccess('Xóa thành công')
            this.IDGList = this.IDGList.filter((el) => el.id != item.id)
          } else {
            this.$root.toastError('Xóa thất bại')
          }
        })
      } else if (item.tmpId) {
        this.$root.toastSuccess('Xóa thành công')
        this.IDGList = this.IDGList.filter((el) => el.tmpId != item.tmpId)
      }
    },
    deleteBackupItem(item) {
      // this.backupList = this.backupList.filter((el) => el.tmpId != item.tmpId || el.id != item.id)
      if (item.id) {
        this.deleteNCBackup(item.id).then((res) => {
          if (res) {
            this.$root.toastSuccess('Xóa thành công')
            this.backupList = this.backupList.filter((el) => el.id != item.id)
          } else {
            this.$root.toastError('Xóa thất bại')
          }
        })
      } else if (item.tmpId) {
        this.$root.toastSuccess('Xóa thành công')
        this.backupList = this.backupList.filter((el) => el.tmpId != item.tmpId)
      }
    },
    async loadInfo() {
      try {
        this.$root.showLoading(true)
        const tasks = [this.getDSYeuCauCPV2(), this.getDmSanPhamDichVu(), this.getDmHaTangMang()]
        const [YeuCauCP, SPDV, HaTangMang] = await Promise.all(tasks)

        this.dsYeuCauCP = YeuCauCP
        this.dsSanPhamDichVu = SPDV
        this.dsHaTangMang = HaTangMang
      } finally {
        this.$root.showLoading(false)
      }
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
    async getYeuCauRelationInfo(ycID) {
      const tasks = [
        this.getNhuCauAoHoa(ycID),
        this.getNhuCauK8s(ycID),
        this.getNhuCauIDG(ycID),
        this.getNhuCauBackup(ycID)
      ]
      const ttcpTasks = [
        this.getTTCPAoHoa(ycID),
        this.getTTCPK8s(ycID),
        this.getTTCPIDG(ycID),
        this.getTTCPBackup(ycID)
      ]

      const [
        nhuCauAoHoa,
        nhuCauK8s,
        nhuCauIDG,
        nhuCauBackup,
        // thong tin cap phat
        ttcpAoHoa,
        ttcpK8s,
        ttcpIDG,
        ttcpBackup
      ] = await Promise.all([...tasks, ...ttcpTasks])

      this.aoHoaList = nhuCauAoHoa
      this.containerList = nhuCauK8s
      this.IDGList = nhuCauIDG
      this.backupList = nhuCauBackup
      // thong tin cap phat
      this.dsAoHoaCP = ttcpAoHoa
      this.dsContainerK8sCP = ttcpK8s
      this.dsIDGCP = ttcpIDG
      this.dsBackupCP = ttcpBackup
    },
    formatDateInList(list = []) {
      return list.map((el) => ({
        ...el
        // batdau: moment(el.batdau, ['YYYY-MM-DD', DATE_FORMAT], true).format(DATE_FORMAT),
        // ketthuc: moment(el.ketthuc, ['YYYY-MM-DD', DATE_FORMAT], true).format(DATE_FORMAT),
        // ngaytao: moment(el.ngaytao, ['YYYY-MM-DD', DATE_FORMAT], true).format(DATE_FORMAT)
      }))
    },
    updateItemV2: async function() {
      let result = false
      const fileId = await this.createFileMinio()
      this.currentItem.file_yc = fileId
      const dataToSend = {
        ...this.currentItem,
        aohoa_list: this.formatDateInList(this.aoHoaList),
        container_list: this.formatDateInList(this.containerList),
        idg_list: this.formatDateInList(this.IDGList),
        backup_list: this.formatDateInList(this.backupList)
      }
      try {
        let rs = await this.$root.context.post(`${ROOT_API}/phieu-cap-phat`, dataToSend)
        result = rs.data
      } catch (e) {
        this.handleErrorResponse(e)
      }
      return result
    },
    createFileMinio: async function() {
      let result = null
      if (this.files.length > 0) {
        if (!this.files[0].file) return this.currentItem.file_yc
        const formData = new FormData()
        formData.append('p_module', 'HATANGCNTT')
        formData.append('p_file', this.files[0].file)
        const url = `/app-banhang/quanlyfile/upload_file`
        const headers = {
          'Content-Type': 'multipart/form-data',
          'App-secret': APP_SECRET
        }
        try {
          let rs = await this.axios.post(url, formData, { headers })
          result = rs.data.data.id_taptin
        } catch (error) {
          this.handleErrorResponse(error)
        } finally {
          return result
        }
      }
      return result
    },
    deleteItem: async function(id) {
      let result = false
      try {
        let rs = await this.$root.context.post('/web-ecms/chungloai-thietbi/xoa-chungloai-thietbi', { id: id })
        result = rs.data.result
      } catch (e) {
        if (e.response && e.response.data) {
          this.$root.toastError(e.response.data.message_detail)
        }
      }
      return result
    },
    enableControls: function(args) {
      for (const key in this.controls) {
        if (this.controls.hasOwnProperty(key)) {
          this.controls[key].enabled = args
        }
      }

      const isSPDV = this.currentItem.spdv_id == -1
      this.controls.txtTenSanPhamDichVu.enabled = isSPDV && args

      this.buttons.btnSave.enabled = args
      this.buttons.btnAddNew.enabled = !args
      this.buttons.btnCancel.enabled = this.buttons.btnSave.enabled
      this.buttons.btnDelete.enabled = !args

      if (this.currentItem.id > 0) this.buttons.btnEdit.enabled = !this.buttons.btnSave.enabled
    },
    setCurrentItem: async function(item) {
      this.currentItem = { ...item }
      this.files = []
    },
    setCurrentItemDefault: function() {
      this.currentItem = { ...DEFAULT_FORM_DATA }
      this.currentItem.nguoi_yc = this.userInfo.ten_nv
      this.currentItem.nguoi_yc_id = this.userInfo.id_nhanvien
      this.currentItem.ten_dv_yc = this.userInfo.ten_dv
      this.currentItem.donvi_id = this.userInfo.donvi_id
      this.imageItem = null
      this.files = []
      this.aoHoaList = []
      this.containerList = []
      this.IDGList = []
      this.backupList = []
      this.dsAoHoaCP = []
      this.dsContainerK8sCP = []
      this.dsIDGCP = []
      this.dsBackupCP = []
    },
    validateControls: function() {
      let msg = ''
      if (!this.currentItem.mucdich_yc) {
        msg += 'Mục đích sử dụng không được để trống.\n'
      }
      if (!this.currentItem.spdv_id) {
        msg += 'Sản phẩm dịch vụ không được để trống.\n'
      }
      if (!this.currentItem.spdv_ten && this.currentItem.spdv_id == -1) {
        msg += 'Tên SPDV không được để trống.\n'
      }
      if (!this.currentItem.hatangmang_id) {
        msg += 'Hạ tầng mạng không được để trống.\n'
      }
      if (msg) {
        this.$root.toastError(msg)
        return false
      }
      return true
    },
    grdItems_RowSelected: async function(args) {
      try {
        this.$root.showLoading(true)
        if (args.data.length) {
          this.setCurrentItem(args.data[0])
        } else {
          this.setCurrentItem(args.data)
        }
      } finally {
        this.$root.showLoading(false)
      }
    },
    grdItems_RowDeselected: async function(aaa) {
      let items = this.grdItems.getSelectedRecords()
      if (items && items.length > 0) {
        try {
          this.$root.showLoading(true)
          this.setCurrentItem(items[0])
        } finally {
          this.$root.showLoading(false)
        }
      } else {
        this.setCurrentItemDefault()
      }
    },
    onAddNew: async function() {
      this.setCurrentItemDefault()
      this.enableControls(true)
    },
    onSave: async function() {
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
            let rs = await this.updateItemV2()
            if (rs) {
              if (this.currentItem.id == 0) {
                this.$root.toastSuccess('Thêm yêu cầu thành công')
              } else {
                this.$root.toastSuccess('Cập nhật yêu cầu thành công')
              }
            } else {
              if (this.currentItem.id == 0) {
                this.$root.toastError('Thêm yêu cầu thất bại')
              } else {
                this.$root.toastError('Cập nhật yêu cầu thất bại')
              }
            }
            this.enableControls(!rs)
          } catch (error) {
            this.$root.toastError('Lỗi cập nhật yêu cầu')
          } finally {
            await this.loadInfo()
            this.$root.showLoading(false)
          }
        }
      }
    },
    onCancel: async function(args) {
      // Load lại thông tin thiết bị
      let items = this.grdItems.getSelectedRecords()
      let item = null
      if (items && items.length > 0) {
        if (this.currentItem.id == 0) {
          item = items[0]
        } else {
          item = items.find((x) => x.id == this.currentItem.id)
        }
      }
      if (item != null) {
        this.setCurrentItem(item)
        this.getYeuCauRelationInfo(item.id)
      } else {
        this.setCurrentItemDefault()
      }
      this.enableControls(false)
    },
    onEdit: async function(args) {
      this.enableControls(true)
    },
    onDelete: async function(args) {
      let items = this.grdItems.getSelectedRecords()
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
          this.$root.showLoading(true)
          let rs = false
          if (!(items == null || items == undefined)) {
            let ids = []
            items.forEach((item) => {
              ids.push(item.id)
            })
            rs = await this.deleteYeuCauCapPhat(ids)
          }
          if (rs) {
            this.setCurrentItemDefault()
            this.enableControls(false)
            this.$root.toastSuccess('Xóa thiết bị thành công')
          } else {
            this.$root.toastError('Xóa thiết bị thất bại')
          }
        } finally {
          await this.loadInfo()
          this.$root.showLoading(false)
        }
      }
    },
    async onSendRequest() {
      let msg = `Bạn có muốn gửi yêu cầu không?`
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
          this.$root.showLoading(true)
          let rs = false
          if (this.currentItem.id) {
            rs = await this.createPhieuCapPhat(this.currentItem.id)
          }
          if (rs) {
            this.enableControls(false)
            this.$root.toastSuccess('Gửi yêu cầu thành công')
          } else {
            this.$root.toastError('Gửi yêu cầu thất bại')
          }
        } finally {
          await this.loadInfo()
          this.$root.showLoading(false)
        }
      }
    },
    // Hàm chọn file ảnh
    chooseFiles() {
      if (this.$refs.fileUpload) {
        this.$refs.fileUpload.click()
      }
    },
    onFileChanged(event) {
      const file = event.target.files[0]
      if (file) {
        const reader = new FileReader()
        reader.onload = (e) => {
          this.imageItem = e.target.result
        }
        reader.readAsDataURL(file)
      }
    },
    handleFileUpload(event) {
      const uploadFile = event.target.files[0] // chi lay file dau tien
      if (uploadFile) {
        let extension = uploadFile.name.slice(-3)
        if (extension === 'exe' || extension === 'msi' || extension === 'bat' || extension === '.js') {
          return this.$toast.error('Vui lòng không upload file có định dạng .exe/.msi/.bat!')
        }
        const fileURL = URL.createObjectURL(uploadFile) // Tao URL tam thoi cho file
        const file = { name: uploadFile.name, url: fileURL, type: uploadFile.type }
        if (this.files.length > 0) {
          this.files.splice(0, 1, file) // Xoa file cu
        } else {
          // this.files.push({ name: uploadFile.name, url: fileURL, type: uploadFile.type, file: uploadFile })
          this.files.push({ name: uploadFile.name, url: fileURL, file: uploadFile })
        }
        // 🔥 Reset giá trị input để cho phép chọn lại file giống nhau
        this.$refs.fileUpload.value = ''
      }
    },
    openFile(file) {
      // const isImageOrPDF = file.type.includes('image') || file.type.includes('pdf')
      // if (isImageOrPDF) {
      //   window.open(file.url, '_blank') // Mo anh hoac PDF
      // } else {
      //   const a = document.createElement('a')
      //   a.href = file.url
      //   a.download = file.name // Ten file khi tai xuong
      //   document.body.appendChild(a)
      //   a.click()
      //   document.body.removeChild(a)
      // }
      const a = document.createElement('a')
      a.href = file.url
      a.download = file.name // Ten file khi tai xuong
      a.target = '_blank'
      document.body.appendChild(a)
      a.click()
      document.body.removeChild(a)
    },
    handleRemoveFile(index) {
      if (this.buttons.btnSave.enabled) {
        this.files.splice(index, 1)
      }
    },
    getUserInfo() {
      const userInfo = localStorage.getItem('OneBSS-Client')
      this.userInfo = JSON.parse(userInfo)
    },
    getThanhPhanText: function(value) {
      return KIENTRUC_THANHPHAN.find((el) => el.id == value)?.name ?? ''
    },
    getColumnThanhPhanTemplate: function() {
      return function() {
        return {
          template: {
            template: `<span>{{ getText() }}</span>`,
            methods: {
              getText: function() {
                return this.$parent.$parent.$parent.getThanhPhanText(this.data.thanhphan)
              }
            }
          }
        }
      }
    },
    getTrangThaiText: function(value) {
      return TRANG_THAI_CP.find((el) => el.value == value)?.name ?? ''
    },
    getColumnTrangThaiTemplate: function() {
      return function() {
        return {
          template: {
            template: `<span>{{ getText() }}</span>`,
            methods: {
              getText: function() {
                return this.$parent.$parent.$parent.getTrangThaiText(this.data.trangthai_capphat)
              }
            }
          }
        }
      }
    },
    async getDSYeuCauCP() {
      let result = []
      try {
        const rs = await this.$root.context.get(`/web-ecms/phieu-cap-phat/danh-sach`)
        result = rs.data
        result.forEach((item) => {
          item.spdv_id = item.spdv_id ? item.spdv_id : -1 // Đặt giá trị mặc định cho spdv_id nếu không có
        })
      } catch (error) {
        this.handleErrorResponse(error)
      }
      return result
    },
    async getDSYeuCauCPV2() {
      let result = []
      try {
        const rs = await this.$root.context.get(`${ROOT_API}/phieu-cap-phat`)
        result = rs.data
        result.forEach((item) => {
          item.spdv_id = item.spdv_id ? item.spdv_id : -1 // Đặt giá trị mặc định cho spdv_id nếu không có
        })
      } catch (error) {
        this.handleErrorResponse(error)
      }
      return result
    },
    getDmSanPhamDichVu: async function() {
      let result = []
      try {
        let rs = await this.$root.context.get(`${ROOT_API}/hatang/san-pham-dich-vu`)
        result = rs.data
      } catch (e) {
        this.handleErrorResponse(e)
      }
      return result
    },
    getDmHaTangMang: async function() {
      let result = []
      try {
        let rs = await this.$root.context.get('/web-ecms/danhmuc/ha-tang-mang/danh-sach')
        result = rs.data
      } catch (e) {
        this.handleErrorResponse(e)
      }
      return result
    },
    getFileMinio: async function(path) {
      let result = null
      try {
        let rs = await this.$root.context.post('/web-ecms/storage/getPresignedUrl', {
          fileSource: path
        })
        result = rs.data.result
      } catch (e) {
        this.handleErrorResponse(e)
      } finally {
        return result
      }
    },
    getNhuCauAoHoa: async function(ycID) {
      let result = []
      try {
        const rs = await this.$root.context.get(`${ROOT_API}/phieu-cap-phat/${ycID}/nhu-cau-ao-hoa`)
        result = rs.data
      } catch (error) {
        this.handleErrorResponse(error)
      }
      return result
    },
    getNhuCauK8s: async function(ycID) {
      let result = []
      try {
        const rs = await this.$root.context.get(`${ROOT_API}/phieu-cap-phat/${ycID}/nhu-cau-k8s`)
        result = rs.data
      } catch (error) {
        this.handleErrorResponse(error)
      }
      return result
    },
    getNhuCauIDG: async function(ycID) {
      let result = []
      try {
        const rs = await this.$root.context.get(`${ROOT_API}/phieu-cap-phat/${ycID}/nhu-cau-idg`)
        result = rs.data
      } catch (error) {
        this.handleErrorResponse(error)
      }
      return result
    },
    getNhuCauBackup: async function(ycID) {
      let result = []
      try {
        const rs = await this.$root.context.get(`${ROOT_API}/phieu-cap-phat/${ycID}/nhu-cau-backup`)
        result = rs.data
      } catch (error) {
        this.handleErrorResponse(error)
      }
      return result
    },
    getTTCPAoHoa: async function(ycID) {
      let result = []
      try {
        const rs = await this.$root.context.get(`${ROOT_API}/phieu-cap-phat/${ycID}/thong-tin-cap-phat-ao-hoa`)
        result = rs.data
      } catch (error) {
        this.handleErrorResponse(error)
      }
      return result
    },
    getTTCPK8s: async function(ycID) {
      let result = []
      try {
        const rs = await this.$root.context.get(`${ROOT_API}/phieu-cap-phat/${ycID}/thong-tin-cap-phat-k8s`)
        result = rs.data
      } catch (error) {
        this.handleErrorResponse(error)
      }
      return result
    },
    getTTCPIDG: async function(ycID) {
      let result = []
      try {
        const rs = await this.$root.context.get(`${ROOT_API}/phieu-cap-phat/${ycID}/thong-tin-cap-phat-idg`)
        result = rs.data
      } catch (error) {
        this.handleErrorResponse(error)
      }
      return result
    },
    getTTCPBackup: async function(ycID) {
      let result = []
      try {
        const rs = await this.$root.context.get(`${ROOT_API}/phieu-cap-phat/${ycID}/thong-tin-cap-phat-backup`)
        result = rs.data
      } catch (error) {
        this.handleErrorResponse(error)
      }
      return result
    },
    deleteNCAoHoa: async function(id) {
      let result = false
      try {
        const rs = await this.$root.context.post(`${ROOT_API}/phieu-cap-phat/nhu-cau-ao-hoa/${id}`)
        result = rs.data
      } catch (error) {
        this.handleErrorResponse(error)
      }
      return result
    },
    deleteNCK8s: async function(id) {
      let result = false
      try {
        const rs = await this.$root.context.post(`${ROOT_API}/phieu-cap-phat/nhu-cau-k8s/${id}`)
        result = rs.data
      } catch (error) {
        this.handleErrorResponse(error)
      }
      return result
    },
    deleteNCIDG: async function(id) {
      let result = false
      try {
        const rs = await this.$root.context.post(`${ROOT_API}/phieu-cap-phat/nhu-cau-idg/${id}`)
        result = rs.data
      } catch (error) {
        this.handleErrorResponse(error)
      }
      return result
    },
    deleteNCBackup: async function(id) {
      let result = false
      try {
        const rs = await this.$root.context.post(`${ROOT_API}/phieu-cap-phat/nhu-cau-backup/${id}`)
        result = rs.data
      } catch (error) {
        this.handleErrorResponse(error)
      }
      return result
    },
    deleteYeuCauCapPhat: async function(ids) {
      let result = false
      try {
        const rs = await this.$root.context.post(`${ROOT_API}/phieu-cap-phat/delete-items`, {
          id: ids
        })
        result = rs.data
      } catch (error) {
        this.handleErrorResponse(error)
      }
      return result
    },
    createPhieuCapPhat: async function(yc_id) {
      let result = false
      try {
        const rs = await this.$root.context.post(`${ROOT_API}/phieu-cap-phat/cap-phat`, {
          yc_id: yc_id
        })
        result = rs.data
      } catch (error) {
        this.handleErrorResponse(error)
      }
      return result
    }
  }
}
