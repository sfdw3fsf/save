<template src="./index.html"></template>

<script>
import { mapActions, mapState, mapGetters } from 'vuex'
import breadcrumb from '@/components/breadcrumb'
import moment from 'moment'
import { fieldsDS } from './fields'
import CapPhatIP from '../CapPhatIP'
import CapPhatTaiNguyen from '../CapPhatTaiNguyen/index.vue'
export default {
  components: {
    CapPhatIP,
    CapPhatTaiNguyen
  },
  data: function() {
    return {
      formData: {
        id: '',
        soPhieu: '123',
        tenMayAo: '',
        haTangAoHoaID: 0,
        haTangIDG: 'ha tang IDG test',
        vaiTroId: 0,
        soCardIP: '',
        IP: '',

        nguonYeuCauID: 0,
        phieuYeuCau: '',
        khachHang: '',
        ghiChu: '',
        tinhGiaHaTang: '1',
        trangThaiCapPhat: '',
        heDieuHanhID: 0,
        phanLoaiHaTangID: 0,
        trangThaiMayAoID: 0,
        trangThaiHoatDongID: 1,
        ngayTao: new Date(),
        ngayHetHan: '',
        nguoiCapNhat: this.$auth.getUserName(),
        ngayCapNhat: new Date(),
        donViQL: '',
        phongToQL: '',
        nguoiQL: '',
        cumDichVu: '',
        sanPhamDichVu: '',
        donViChuQuanID: null,
        donViChuQuan: '',
        vCPU: '',
        vGPU: '',
        vRAM: '',
        IOPS: '',
        SSD: '',
        HDD: '',
        Throughput: '',
        khachhang_id: 123,
        capphat_id: 0,
        hdtb_id: 0
      },
      chiTietHaTangAoHoa: null,
      dsHeDieuHanh: [],
      dsPhongToQL: [],
      dsPhanLoaiHaTang: [],
      dsHaTangIDG: [],
      dsNhanVien: [],
      dsVaiTro: [],
      dsCumDichVu: [],
      dsSPDV: [],
      dsHaTangAoHoa: [],
      dsHaTangAoHoaDuDieuKienCapPhat: [],
      dsTrangThaiMayAo: [],
      dsTrangThaiHoatDong: [
        { id: 1, text: 'Bật' },
        { id: 2, text: 'Tắt' },
        { id: 3, text: 'Đang reboot' }
      ],

      dsMguonYeuCau: [
        { id: 1, text: 'JIRA' },
        { id: 2, text: 'Văn bản' },
        { id: 3, text: 'Email' },
        { id: 4, text: 'CĐS KHDN ' }
      ],
      dataSource: [],
      dsSelected: [],
      dsIDC: [],
      dsCongNghe: [],
      dsHaTang: [],
      fieldsDS: fieldsDS,
      colsHaTang: [
        { fieldName: 'SOTHUTU', headerText: 'STT', allowFiltering: true, width: 50 },
        { fieldName: 'TEN', headerText: 'Tên hạ tầng', allowFiltering: true },
        { fieldName: 'VRAM', headerText: 'vRAM', allowFiltering: true },
        { fieldName: 'VCPU', headerText: 'vCPU', allowFiltering: true },
        { fieldName: 'VSTORAGE', headerText: 'vStorage', allowFiltering: true },
        { fieldName: 'TEN_IDC', headerText: 'IDC', allowFiltering: true },
        { fieldName: 'NGUOI_QTRI', headerText: 'Người quản trị', allowFiltering: true },
        { fieldName: 'HIEU_LUC', headerText: 'Hiệu lực', allowFiltering: true }
      ],
      dsSmartCloud: [],
      colsSmartCloud: [
        { fieldName: 'SOTHUTU', headerText: 'STT', allowFiltering: true, width: 50 },
        { fieldName: 'TEN', headerText: 'Tên hạ tầng', allowFiltering: true },
        { fieldName: 'VRAM', headerText: 'vRAM', allowFiltering: true },
        { fieldName: 'VCPU', headerText: 'vCPU', allowFiltering: true },
        { fieldName: 'VSTORAGE', headerText: 'vStorage', allowFiltering: true },
        { fieldName: 'TEN_IDC', headerText: 'IDC', allowFiltering: true },
        { fieldName: 'NGUOI_QTRI', headerText: 'Người quản trị', allowFiltering: true },
        { fieldName: 'HIEU_LUC', headerText: 'Hiệu lực', allowFiltering: true }
      ],
      dsIDG: [],
      colsIDG: [
        { fieldName: 'SOTHUTU', headerText: 'STT', allowFiltering: true, width: 50 },
        { fieldName: 'TEN', headerText: 'Tên hạ tầng', allowFiltering: true },
        { fieldName: 'LOAI_HATANG', headerText: 'Loại hạ tầng', allowFiltering: true },
        { fieldName: 'HATANG_AOHOA', headerText: 'Hạ tầng ảo hóa', allowFiltering: true },
        { fieldName: 'TEN_IDC', headerText: 'IDC', allowFiltering: true },
        { fieldName: 'NGUOI_QTRI', headerText: 'Người quản trị', allowFiltering: true },
        { fieldName: 'HIEU_LUC', headerText: 'Hiệu lực', allowFiltering: true }
      ],
      dsSubnet: [],
      colsSubnet: [
        { fieldName: 'SOTHUTU', headerText: 'STT', allowFiltering: true, width: 50 },
        { fieldName: 'SUBNET', headerText: 'Subnet', allowFiltering: true },
        { fieldName: 'SUBNETMASK', headerText: 'Subnet mask', allowFiltering: true },
        { fieldName: 'GATEWAY', headerText: 'Gateway', allowFiltering: true },
        { fieldName: 'TEN_VLAN', headerText: 'VLAN', allowFiltering: true },
        { fieldName: 'HIEU_LUC', headerText: 'Hiệu lực', allowFiltering: true }
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
        nhanVienId: 0,
        nhanhVienTen: '',
        donViId: 0,
        donViTen: ''
      },
      imageItem: '',
      currentTab: 1,
      currentItem: {
        id: 0,
        ten: null,
        maHaTang: null,
        IDCId: 0,
        congNgheId: 0,
        avatar: null,
        ghiChu: null,
        donViQuanLy: null,
        phongBanQuanLy: null,
        nguoiQuanLy: null,
        hieuLuc: 1,
        donViQuanLyId: 0,
        phongBanQuanLyId: 0,
        nguoiQuanLyId: 0
      },
      controls: {
        txtTen: {
          enabled: false,
          invalid: false
        },
        cboIDC: {
          enabled: false,
          invalid: false
        },
        txtMaHaTang: {
          enabled: false,
          invalid: false
        },
        cboCongNghe: {
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
        btnXacNhan: {
          enabled: true
        },
        btnCapPhatIP: {
          enabled: false
        }
      },
      thongTinChungControls: {
        txtTen: {
          enabled: false,
          invalid: false
        },
        selectHTAoHoa: {
          enabled: false,
          invalid: false
        },
        selectHTIDG: {
          enabled: false,
          invalid: false
        },
        selectVaiTro: {
          enabled: false,
          invalid: false
        },
        selectIDG: {
          enabled: false,
          invalid: false
        },
        txtCardMang: {
          enabled: false,
          invalid: false
        },
        checkTinhGia: {
          enabled: false
        },
        selectHDH: {
          enabled: false,
          invalid: false
        },
        selectPhanLoaiHT: {
          enabled: false,
          invalid: false
        },
        selectTTMayAo: {
          enabled: false,
          invalid: false
        },
        selectTTHD: {
          enabled: false,
          invalid: false
        },
        dateNgayTao: {
          enabled: false,
          invalid: false
        },
        dateNgayHetHan: {
          enabled: false,
          invalid: false
        },
        txtGhiChu: {
          enabled: false
        }
      },
      thongTinQuanLyControls: {
        selectPhong: {
          enabled: false
        },
        selectCaNhan: {
          enabled: false
        },
        selectCumDV: {
          enabled: false
        }
      }
    }
  },
  computed: {
    grdItems: function() {
      return this.$refs.grdItems
    },
    donViChuQuanData: function() {
      return {
        id_donvi: this.formData.donViChuQuanID,
        ten_donvi: this.formData.donViChuQuan
      }
    }
  },
  watch: {
    'formData.haTangAoHoaID': {
      handler: async function(value) {
        this.dsHaTangIDG = await this.apiDanhMucHaTangIDG(value)
        this.chiTietHaTangAoHoa = await this.apiChiTietHaTangAoHoa(value)
        console.log('Chi tiết hạ tầng ảo hóa:', this.chiTietHaTangAoHoa)
      }
    },
    'formData.phongToQL': {
      handler: async function(value) {
        this.dsNhanVien = await this.apiDanhSachNhanVienDonVi(value)
      }
    }
  },
  created: async function() {
    try {
      this.$root.showLoading(true)
      this.dsIDC = await this.apiIDC()
      this.dsCongNghe = await this.apiCongNgheSuDung()
      this.dataSource = await this.apiListDanhSach()
      this.dsHeDieuHanh = await this.apiDanhMuc('HE-DIEU-HANH')
      this.dsPhanLoaiHaTang = await this.apiDanhMuc('HATANG_PHANLOAI')
      this.dsHaTangAoHoa = await this.apiDanhMuc('HATANG-AOHOA')
      this.dsVaiTro = await this.apiDanhMuc('VAI-TRO')
      this.dsTrangThaiMayAo = await this.apiDanhMuc('TRANGTHAI-MAYAO')
      this.dsCumDichVu = await this.apiDanhMuc('CUMDICHVU')
      this.dsSPDV = await this.apiDanhMuc('SANPHAMDICHVU')
      //this.dsTrangThaiHoatDong=await this.apiDanhMuc('TRANGTHAI-MAYAO');

      // init user Info
      this.userInfo.nhanVienId = await this.$root.token.getNhanVienID()
      this.userInfo.nhanhVienTen = await this.$root.context.user.getTenNhanVien()
      this.userInfo.donViId = await this.$root.token.getDonViID()
      this.userInfo.donViTen = await this.$root.context.user.getTenDonVi()
      //this.buttons.btnSave.enabled=true
    } finally {
      this.$root.showLoading(false)
    }
  },
  methods: {
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
    apiChiTietHaTangAoHoa: async function(id) {
      let result = null
      if (id)
        try {
          let rs = await this.$root.context.post('/web-ecms/aohoa-cloud/get-item',{
            id: id
          })
          result = rs.data[0]
        } catch (e) {
          this.$toast.error(e.response.data.message_detail)
        }

      return result
    },
    apiIDC: async function() {
      let result = []
      try {
        let rs = await this.$root.context.get('/web-ecms/danhmuc/ha-tang-idc')
        result = rs.data
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result
    },
    validateCapNhat() {
      if (!this.formData.haTangAoHoaID) {
        this.$toast.error('Bạn chưa chọn hạ tầng ảo hóa')
        return false
      }
      return true
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

    apiDanhMucHaTangIDG: async function(id) {
      let result = []
      if (id)
        try {
          let rs = await this.$root.context.get('/web-ecms/cap-may-ao/danhmuc-hatang-aohoa-idg/' + id)
          result = rs.data
        } catch (e) {
          this.$toast.error(e.response.data.message_detail)
        }

      return result
    },
    apiDanhSachDonViCap2: async function(id) {
      let result = []
      if (id)
        try {
          let rs = await this.$root.context.get('/web-ecms/cap-may-ao/danhsach-donvi-cap2/' + id)
          result = rs.data
        } catch (e) {
          this.$toast.error(e.response.data.message_detail)
        }

      return result
    },
    apiDanhSachNhanVienDonVi: async function(id) {
      let result = []
      if (id)
        try {
          let rs = await this.$root.context.get('/web-ecms/cap-may-ao/danhsach-nhanvien-donvi/' + id)
          result = rs.data
        } catch (e) {
          this.$toast.error(e.response.data.message_detail)
        }

      return result
    },
    apiDanhMuc: async function(danhMuc) {
      let result = []
      let param = {
        loaiDanhMuc: danhMuc,
        thamSo1: '',
        thamSo2: ''
      }
      try {
        let rs = await this.$root.context.post('/web-ecms/danhmuc/hatang_mang', param)
        result = rs.data
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result
    },

    apiHaTangMang: async function(pId) {
      let result = []
      if (pId == null || pId < 1) {
        return result
      }
      try {
        let rs = await this.$root.context.get('/web-ecms/danhmuc/su-dung/ha-tang-mang-ao-hoa', { haTangMangId: pId })
        result = rs.data
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result
    },

    apiSmartCloud: async function(pId) {
      let result = []
      if (pId == null || pId < 1) {
        return result
      }
      try {
        let rs = await this.$root.context.get('/web-ecms/danhmuc/su-dung/ha-tang-mang-smartcloud', {
          haTangMangId: pId
        })
        result = rs.data
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result
    },
    apiIDG: async function(pId) {
      let result = []
      if (pId == null || pId < 1) {
        return result
      }
      try {
        let rs = await this.$root.context.get('/web-ecms/danhmuc/su-dung/ha-tang-mang-idg', { haTangMangId: pId })
        result = rs.data
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result
    },
    apiSubnet: async function(pId) {
      let result = []
      if (pId == null || pId < 1) {
        return result
      }
      try {
        let rs = await this.$root.context.get('/web-ecms/danhmuc/su-dung/ha-tang-mang-subnet', { haTangMangId: pId })
        result = rs.data
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result
    },
    apiIPAddress: async function(pId) {
      let result = []
      if (pId == null || pId < 1) {
        return result
      }
      try {
        let rs = await this.$root.context.get('/web-ecms/danhmuc/su-dung/ha-tang-mang-ip-address', {
          haTangMangId: pId
        })
        result = rs.data
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result
    },
    apiThietBi: async function(pId) {
      let result = []
      if (pId == null || pId < 1) {
        return result
      }
      try {
        let rs = await this.$root.context.get('/web-ecms/danhmuc/su-dung/ha-tang-mang-thiet-bi', { haTangMangId: pId })
        result = rs.data
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result
    },

    apiHistory: async function(pId) {
      let result = []
      if (pId == null || pId < 1) {
        return
      }
      try {
        let rs = await this.$root.context.get('/web-ecms/danhmuc/su-dung/ha-tang-mang-history', { haTangMangId: pId })
        result = rs.data
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result
    },
    apiListDanhSach: async function() {
      let result = []
      try {
        let rs = await this.$root.context.get('/web-ecms/cap-may-ao/danh-sach')
        result = rs.data
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result
    },
    apiSave: async function(para) {
      let result = false
      try {
        let postData = {
          id: para.id,
          ten: para.tenMayAo,
          hatangaohoa_id: para.haTangAoHoaID,
          hatangidg_id: para.haTangIDG,
          loaivaitro_id: para.vaiTroId,
          nguon_yc: para.nguonYeuCauID,
          hedieuhanh_id: para.heDieuHanhID,
          hatang_phanloai_id: para.phanLoaiHaTangID,
          trangthai_mayao_id: para.trangThaiMayAoID,
          trangthai_hoatdong_id: para.trangThaiHoatDongID,
          ngay_tao: moment.isDate(para.ngayTao) ? moment(para.ngayTao).format('DD/MM/yyyy') : para.ngayTap,
          ngay_hethan: moment.isDate(para.ngayHetHan) ? moment(para.ngayHetHan).format('DD/MM/yyyy') : para.ngayHetHan,
          tinhgia: para.tinhGiaHaTang,
          ghi_chu: para.ghiChu,
          hieu_luc: para.hieuLuc,
          nguoiqly_id: para.nguoiQL,
          cumdichvu: para.cumDichVu,
          ip_address_id: para.ip_address_id,
          network_card: para.network_card,
          phongbanqly_id: para.phongToQL
        }

        let rs = await this.$root.context.post('/web-ecms/cap-may-ao/cap-nhat', postData)
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
    apiXacNhan: async function(id) {
      let result = false
      try {
        let rs = await this.$root.context.post('/web-ecms/cap-may-ao/xac-nhan/' + id)
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
        let rs = await this.$root.context.post('/web-ecms/danhmuc/ha-tang-mang/xoa', {
          id: id
        })
        result = rs.error_code == 'BSS-00000000'
      } catch (e) {
        this.$root.toastError(e.response.data.message_detail)
        result = false
      }
      return result
    },
    enableControls: function(args) {
      this.buttons.btnSave.enabled = args;
      this.buttons.btnCancel.enabled = this.buttons.btnSave.enabled;
      this.buttons.btnXacNhan.enabled = !args;
      this.buttons.btnEdit.enabled = !args;

      // Enable/Disable cho form thông tin chung
      Object.keys(this.thongTinChungControls).forEach((key) => {
        if (this.thongTinChungControls[key].hasOwnProperty('enabled')) {
          this.thongTinChungControls[key].enabled = args
        }
      })

      // Enable/Disable cho form thông tin quản lý
      Object.keys(this.thongTinQuanLyControls).forEach((key) => {
        if (this.thongTinQuanLyControls[key].hasOwnProperty('enabled')) {
          this.thongTinQuanLyControls[key].enabled = args
        }
      })
    },

    chooseFiles(event) {
      event.preventDefault()
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
          this.buttons.btnAddImage = 'hidden'
          this.buttons.imgAvatar = 'img-fluid'
        }

        reader.onerror = (error) => {
          console.error('Lỗi khi đọc file:', error)
        }

        reader.readAsDataURL(file)
      }
    },
    setCurrentItemDefault: function() {
      this.currentItem.id = 0
      this.currentItem.ten = null
      this.currentItem.maHaTang = null
      this.currentItem.IDCId = 0
      this.currentItem.congNgheId = 0
      this.currentItem.ghiChu = null
      this.currentItem.hieuLuc = 1
      this.currentItem.donViQuanly = this.userInfo.donViTen
      this.currentItem.phongBanQuanLy = this.userInfo.donViTen
      this.currentItem.nguoiQuanLy = this.userInfo.nhanVienTen
      this.currentItem.donViQuanlyId = this.userInfo.donViId
      this.currentItem.phongBanQuanLyId = this.userInfo.donViId
      this.currentItem.nguoiQuanLyId = this.userInfo.nhanVienId

      this.dsHaTang = []
      this.dsSmartCloud = []
      this.dsIDG = []
      this.dsSubnet = []
      this.dsIPAddress = []
      this.dsThietBi = []
      this.dsHistory = []
    },
    setCurrentItem: async function(item) {
      this.dsPhongToQL = await this.apiDanhSachDonViCap2(item.DONVIQLY_ID)
      this.formData.id = item.ID
      this.formData.soPhieu = item.SOPHIEU
      this.formData.tenMayAo = item.TEN
      this.formData.haTangAoHoaID = item.HATANGAOHOA_ID
      this.formData.haTangIDG = item.HATANGIDG_ID
      this.formData.vaiTroId = item.LOAIVAITRO_ID
      this.formData.network_card = item.NETWORK_CARD
      this.formData.ip_address_id = item.IP_ADDRESS_ID
      this.formData.ip_address_ten = item.TEN_IP_ADDRESS
      this.formData.nguonYeuCauID = item.NGUON_YC
      this.formData.phieuYeuCau = item.PHIEU_YC
      this.formData.khachHang = item.KHACHHANG
      this.formData.ghiChu = item.GHI_CHU
      this.formData.tinhGiaHaTang = item.TINHGIA
      this.formData.trangThaiCapPhat = item.TRANGTHAI_CAPPHAT
      this.formData.heDieuHanhID = item.HEDIEUHANH_ID
      this.formData.phanLoaiHaTangID = item.HATANG_PHANLOAI_ID
      this.formData.trangThaiMayAoID = item.TRANGTHAI_MAYAO_ID
      this.formData.trangThaiHoatDongID = item.TRANGTHAI_HOATDONG
      this.formData.ngayTao = item.NGAY_TAO
      this.formData.ngayHetHan = item.NGAY_HETHAN
      this.formData.nguoiCapNhat = item.NGUOI_CAPNHAT
      this.formData.ngayCapNhat = item.NGAY_CAPNHAT
      this.formData.donViQL = item.TEN_DONVI_QLY
      this.formData.phongToQL = item.PHONGBANQLY_ID
      this.formData.nguoiQL = item.NGUOIQLY_ID
      this.formData.cumDichVu = item.CUMDICHVU_ID
      this.formData.sanPhamDichVu = item.SPDV_ID
      this.formData.donViChuQuanID = item.DONVI_CHUQUAN_ID
      this.formData.donViChuQuan = item.TEN_DONVI_CHUQUAN
      this.formData.vCPU = item.VCPU
      this.formData.vGPU = item.VGPU
      this.formData.vRAM = item.VRAM
      this.formData.IOPS = item.IOPS
      this.formData.HDD = item.HDD
      this.formData.SSD = item.SSD
      //this.formData.IOPS=item.DICHVU_IOPS_ID;
      this.formData.khachhang_id = item.KHACHHANG_ID
      this.formData.capphat_id = item.CAPPHAT_ID
      this.formData.hdtb_id = item.HDTB_ID
      this.formData.Throughput = item.THROUGHTPUT
      this.formData.trangThaiCapPhatId = item.TRANGTHAI_CAPPHAT_ID
      //this.onShowDataZone(item.ID);
      // this.buttons.btnEdit.enabled = true
    },

    validateControls: function() {
      if (!this.formData.tenMayAo) {
        this.$toast.error('Bạn chưa nhập tên máy ảo')
        return false
      }
      if (!this.formData.haTangAoHoaID) {
        this.$toast.error('Bạn chưa chọn hạ tầng ảo hóa')
        return false
      }
      if (!this.formData.haTangIDG) {
        this.$toast.error('Bạn chưa chọn hạ tầng ảo hóa IDG')
        return false
      }
      if (!this.formData.vaiTroId) {
        this.$toast.error('Bạn chưa chọn vai trò')
        return false
      }
      if (!this.formData.network_card) {
        this.$toast.error('Bạn chưa nhập số card mạng')
        return false
      }
      if (!this.formData.ip_address_ten) {
        this.$toast.error('Bạn chưa có thông tin về IP')
        return false
      }
      if (!this.formData.trangThaiCapPhat) {
        this.$toast.error('Bạn chưa có thông tin về trạng thái cấp phát')
        return false
      }

      if (!this.formData.heDieuHanhID) {
        this.$toast.error('Bạn chưa chọn hệ điều hành cho máy ảo')
        return false
      }
      if (!this.formData.phanLoaiHaTangID) {
        this.$toast.error('Bạn chưa chọn thong tin về phân loại hạ tầng')
        return false
      }
      if (!this.formData.trangThaiMayAoID) {
        this.$toast.error('Bạn chưa chọn thông tin về trạng thái máy ảo')
        return false
      }
      if (!this.formData.trangThaiHoatDongID) {
        this.$toast.error('Bạn chưa chọn thông tin về trạng thái hoạt động')
        return false
      }
      return true

      // let msg = "";
      // if (this.currentItem.IDCId < 1) {
      //   msg += ", chưa chọn IDC ";
      // }
      // if (this.currentItem.VRFId < 1) {
      //   msg += ", chưa chọn thuộc VRF ";
      // }

      // if (msg != "") {
      //   msg = "Bạn chưa chọn : " + msg.substring(2) + "\n";
      // }

      // if (msg != "") this.$root.toastError(msg);
      // return msg == "";
    },

    grdItems_RowSelected: async function(args) {
      if (this.buttons.btnSave.enabled) {
        return
      }
      this.setCurrentItem(args.data)
      this.buttons.btnCapPhatIP.enabled = true
      this.buttons.btnEdit.enabled = true
      setTimeout(() => {
        this.dsHaTangAoHoa = this.filterDsAoHoaDDK(args)
      }, 500)
    },
    filterDsAoHoaDDK: function(phieu) {
      console.log(phieu)
      console.log('this.dsHaTangAoHoa', this.dsHaTangAoHoa)
      return this.dsHaTangAoHoa.filter((item) => phieu.data.IOPS <= item.TONG_IOPS_THIETBI &&
        phieu.data.HDD <= item.TONG_HDD_THIETBI &&
        phieu.data.SSD <= item.TONG_SSD_THIETBI &&
        phieu.data.VCPU <= item.VCPU_NANGLUC &&
        phieu.data.VGPU <= item.GPU_NANGLUC &&
        phieu.data.VRAM <= item.VRAM_NANGLUC
      )
    },
    grdItems_RowDeselected: async function(args) {
      // if (this.buttons.btnSave.enabled) {
      //   return;
      // }
      // let items = this.grdItems.getSelectedRecords();
      // if (!(items == null || items.length == 0)) {
      //   this.setCurrentItem(items[0]);
      // } else {
      //   this.setCurrentItemDefault();
      // }
    },

    // onAddNew: async function (args) {
    //   this.enableControls(true);
    //   this.setCurrentItemDefault();
    // },
    onSave: async function(args) {
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
          //Kiểm tra năng lực hạ tầng ảo hoá đã chọn so với yêu cầu cấp phát
          console.log('this.formData', this.formData)
          console.log('this.chiTietHaTangAoHoa', this.chiTietHaTangAoHoa)
          let errorMessage = ''
          if (this.formData.vCPU > this.chiTietHaTangAoHoa.vcpu_nangluc) {
            errorMessage += `Số lượng vCPU yêu cầu (${this.formData.vCPU}) vượt quá năng lực hạ tầng ảo hóa (${this.chiTietHaTangAoHoa.vcpu_nangluc}) \n`
          }
          if (this.formData.vGPU > this.chiTietHaTangAoHoa.gpu_nangluc) {
            errorMessage += `Số lượng vGPU yêu cầu (${this.formData.vGPU}) vượt quá năng lực hạ tầng ảo hóa (${this.chiTietHaTangAoHoa.gpu_nangluc}) \n`
          }
          if (this.formData.vRAM > this.chiTietHaTangAoHoa.vram_nangluc) {
            errorMessage += `Dung lượng vRAM yêu cầu (${this.formData.vRAM}) vượt quá năng lực hạ tầng ảo hóa (${this.chiTietHaTangAoHoa.vram_nangluc}) \n`
          }
          let nangLucLuuTru = []
          let nangLucIOPS = null
          let nangLucHDD = null
          let nangLucSSD = null
          if (this.chiTietHaTangAoHoa && this.chiTietHaTangAoHoa.nangluc_luutru) {
            try {
              nangLucLuuTru = JSON.parse(this.chiTietHaTangAoHoa.nangluc_luutru)
              nangLucLuuTru.forEach((item) => {
                if (item.iops_nangluc > 0) {
                  nangLucIOPS += item.iops_nangluc
                }
                if (item.hdd_nangluc > 0) {
                  nangLucHDD += item.hdd_nangluc
                }
                if (item.ssd_nangluc > 0) {
                  nangLucSSD += item.ssd_nangluc
                }
              })
            } catch (error) {
              console.error('Error parsing nangluc_luutru:', error)
              nangLucLuuTru = []
            }
          }
          if (this.formData.IOPS > nangLucIOPS) {
            errorMessage += `Số lượng IOPS yêu cầu (${this.formData.IOPS}) vượt quá năng lực hạ tầng ảo hóa (${nangLucIOPS ?? 0}) \n`
          }
          if (this.formData.HDD > nangLucHDD) {
            errorMessage += `Dung lượng HDD yêu cầu (${this.formData.HDD}) vượt quá năng lực hạ tầng ảo hóa (${nangLucHDD ?? 0}) \n`
          }
          if (this.formData.SSD > nangLucSSD) {
            errorMessage += `Dung lượng SSD yêu cầu (${this.formData.SSD}) vượt quá năng lực hạ tầng ảo hóa (${nangLucSSD ?? 0}) \n`
          }
          if (errorMessage.length > 0) {
            this.$root.toastError(errorMessage)
            return;
          }
          this.$root.showLoading(true)
          let rs = await this.apiSave(this.formData)
          if (rs) {
            this.dataSource = await this.apiListDanhSach()
            this.$root.toastSuccess('Cập nhật thông tin thành công ')
          }
          this.enableControls(!rs)
        } finally {
          this.$root.showLoading(false)
        }
      }

      this.buttons.btnSave.enabled = false
      this.buttons.btnEdit.enabled = true
    },

    onCancel: async function (args) {
      let items = this.grdItems.getSelectedRecords();
      let item = null;
      if (!(items == null || items.length == 0)) {
        if (this.currentItem.id == 0) {
          item = items[0];
        }
        else {
          item = items.find(x => x.ID == this.currentItem.id);
        }
      }
      if (item != null) {
        this.setCurrentItem(item);
      } else {
        this.setCurrentItemDefault();
      }
      this.enableControls(false);
    },
    popupClosed_CapPhatIP() {},
    async onXacNhan() {
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
            console.log('this.formData', this.formData)
            let rs = await this.apiXacNhan(this.formData.id)
            if (rs) {
              this.dataSource = await this.apiListDanhSach()
              this.$root.toastSuccess('Xác nhận cấp máy ảo thành công ')
            }
            this.enableControls(!rs)
          } finally {
            this.$root.showLoading(false)
          }
        }

        this.buttons.btnSave.enabled = false
        this.buttons.btnEdit.enabled = true
      }
    },
    ShowPopup_CapPhatIP: async function() {
      // this.$bvModal.show('popupCapIPAddress')
      // console.log(this.$refs.popupCapIPAddress.$refs)
      this.$refs.ref_capphat_ip.setPropsData()
      this.$refs.popupCapIPAddress.ej2Instances.visible = true
    },
    async xacnhanIPHandler(args = []) {
      // console.log('xacnhanIPHandler', args)
      // this.formData.ip_address_id = args.id
      // const { id } = args
      const ipIds = args.map((el) => el.ID).join(',')
      const data = await this.updateIpAddress(this.formData.id, ipIds)
      const currentItem = data.find((item) => item.ID == this.formData.id)
      this.setCurrentItem(currentItem)
      this.dataSource = data
      this.$refs.popupCapIPAddress.ej2Instances.visible = false
    },
    onEdit: async function(args) {
      this.buttons.btnSave.enabled = true
      this.buttons.btnEdit.enabled = false
      this.enableControls(true)
    },
    updateIpAddress: async function(id, ipIds) {
      let result = []
      try {
        // const body = {
        //   id: id,
        //   ip_address_id: ipId
        // }
        const body = {
          id: id,
          ip_address_id_list: ipIds
        }
        let rs = await this.$root.context.post('/web-ecms/cap-may-ao/cap-nhat-ip', body)
        result = rs.data
      } catch (error) {
        this.$toast.error(error)
      } finally {
        return result
      }
    },
    showPhieuCapPhatDetail: async function() {
      const ref = this.$refs.popupPhieuCapPhat;
      ref.show();
      this.$refs.ref_capphat_tainguyen.refreshDataWithCapPhatId(this.formData.capphat_id)
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
.custom-modal {
  z-index: 1050 !important;
}
</style>
