<template src="./ModalThongtinLongQuay.html"></template>

<script>
import RackDetailModal from '../../sodo-phong-zone/components/modals/RackDetailModal.vue'
import ModalChiTietThietBi from '../../sodo-phong-zone/components/chitiet-thietbi/index.vue'
import TabHistory from '../../components/Tabs/TabHistory/index.vue'
import ThanhNguonModal from '../../tacnghiep-khaibao-tnkg-rack/thaydoi-rack/modals/thaydoi-thanhnguon-modal.vue'

export default {
  props: {
    longquayId: {
      type: Number,
      default: 0
    }
  },
  components: {
    RackDetailModal,
    ModalChiTietThietBi,
    TabHistory,
    ThanhNguonModal
  },
  data: function() {
    return {
      currentItem: {
        LONGQUAY_ID: null,
        TEN: '',
        TENTAT: '',
        MOTA: '',
        GHICHU: '',
        CHIEUDAI: null,
        CHIEURONG: null,
        CHIEUCAO: null,
        CAMERA: 0,
        KHOA_ID: null,
        LOAILQ_ID: null,
        PHONG_ID: null,
        TEN_PHONG: '',
        TOADO_X: null,
        TOADO_Y: null,
        TRANGTHAI_VH: null,
        NGUOI_CN: '',
        NGAY_CN: '',
        TEN_SOHUU: '',
        DONVISH_ID: null,
        DV_IDC_ID: null,
        IDC_ID: null,
        TOANHA_ID: null,
        MATSAN_ID: null,
        TOADO_MATSAN_ID: null,
        SUDUNG: 1,
        // chọn nhiều rack -> mảng id
        RACK_ID: [],
        TOADO: null
      },
      actionMode: 1, // 1: view, 2: edit, 3: create
      listLoaiLongQuay: [],
      listCamera: [],
      listKhoa: [],
      listSoHuu: [],
      listTrangThaiVanHanh: [],
      listDVIDC: [],
      listIDC: [],
      listToaNha: [],
      listMatSan: [],
      listToaDoMatSan: [],
      listRack: [],
      listPhong: [],
      buttons: {
        btnGhiLai: {
          enabled: false
        },
        btnHuy: {
          enabled: false
        },
        btnSua: {
          enabled: true
        }
      },
      target: '.main-wrapper',
      animationSettings: { effect: 'Zoom' },
      currentTab: 1,
      dsRackTrongLongQuay: [],
      dsThietBi: [],
      dsCongSuatDien: [],
      dsKhachHang: [],
      // Pre-defined columns to avoid re-parsing on every render
      columnsRack: [
        { fieldName: 'ID', headerText: 'ID', width: 80, textAlign: 'Center' },
        { fieldName: 'TEN', headerText: 'Tên Rack', textAlign: 'Left' },
        { fieldName: 'SO_U', headerText: 'Năng lực không gian', width: 150, textAlign: 'Left' }, // Năng lực không gian = Số U trên Rack
        { fieldName: 'TYLE', headerText: 'Tỷ lệ sử dụng', width: 150, textAlign: 'Left' }, // Tỷ lệ sử dụng = (Số U đã sử dụng / Số U trên Rack) * 100%
        { fieldName: 'NANGLUC_DIEN', headerText: 'Năng lực điện (kW)', textAlign: 'Left' }, // Năng lực điện (kW) = Tổng công suất điện của các thanh nguồn trên Rack
        { fieldName: 'DONVISH', headerText: 'Sở hữu', textAlign: 'Left' },
        {
          fieldName: 'ACTIONS',
          headerText: 'Thao tác',
          allowSorting: false,
          allowFiltering: false,
          textAlign: 'Center',
          width: 100,
          template: null
        }
      ],
      columnsThietBi: [
        { fieldName: 'THIETBI_ID', headerText: 'ID', width: 80, textAlign: 'Center' },
        { fieldName: 'TEN_THIETBI', headerText: 'Tên thiết bị', textAlign: 'Left' },
        { fieldName: 'LOAI_THIETBI', headerText: 'Loại thiết bị', width: 120, textAlign: 'Left' },
        { fieldName: 'DONVI_QL', headerText: 'Đơn vị QL', textAlign: 'Left' },
        { fieldName: 'VITRI', headerText: 'Vị trí lắp đặt', textAlign: 'Left' },
        { fieldName: 'TRANGTHAI', headerText: 'Trạng thái', textAlign: 'Left' },
        {
          fieldName: 'ACTIONS',
          headerText: 'Thao tác',
          allowSorting: false,
          allowFiltering: false,
          textAlign: 'Center',
          width: 100,
          template: null
        }
      ],
      columnsCongSuat: [
        { fieldName: 'THANHNGUON_ID', headerText: 'ID rPDU', width: 80, textAlign: 'Center' },
        { fieldName: 'TEN_THANHNGUON', headerText: 'Tên rPDU', textAlign: 'Left' },
        { fieldName: 'TEN_RACK', headerText: 'Rack', width: 100, textAlign: 'Left' },
        { fieldName: 'CONG_SUAT', headerText: 'Công suất định danh (kW)', textAlign: 'Left' },
        { fieldName: 'CS_SUDUNG', headerText: 'Đã sử dụng (kW)', textAlign: 'Left' },
        { fieldName: 'TYLE', headerText: 'Tỷ lệ sử dụng', textAlign: 'Left' },
        {
          fieldName: 'ACTIONS',
          headerText: 'Thao tác',
          allowSorting: false,
          allowFiltering: false,
          textAlign: 'Center',
          width: 100,
          template: null
        }
      ],
      columnsKhachHang: [
        { fieldName: 'MA_KH', headerText: 'Mã KH', width: 100, textAlign: 'Center' },
        { fieldName: 'TEN_KH', headerText: 'Tên khách hàng', textAlign: 'Left' },
        { fieldName: 'LOAI_KH', headerText: 'Loại KH', width: 100, textAlign: 'Left' },
        { fieldName: 'SO_RACK', headerText: 'Số Rack', textAlign: 'Left' },
        { fieldName: 'NGAY_BD', headerText: 'Ngày bắt đầu', textAlign: 'Left' },
        { fieldName: 'NGAY_KT', headerText: 'Ngày kết thúc', textAlign: 'Left' },
        { fieldName: 'TRANGTHAI_HD', headerText: 'Trạng thái', textAlign: 'Left' }
        // {
        //   fieldName: 'ACTIONS',
        //   headerText: 'Thao tác',
        //   allowSorting: false,
        //   allowFiltering: false,
        //   textAlign: 'Center',
        //   width: 100,
        //   template: null
        // }
      ],
      columnsLichSu: [
        { fieldName: 'STT', headerText: 'STT', textAlign: 'Center', width: 80 },
        { fieldName: 'HANHDONG', headerText: 'Tên tác động', textAlign: 'Left', width: 150 },
        { fieldName: 'TENCOT', headerText: 'Trường thông tin thay đổi', textAlign: 'Left', width: 200 },
        { fieldName: 'GIATRI_CU', headerText: 'Giá trị cũ', textAlign: 'Left', width: 150 },
        { fieldName: 'GIATRI_MOI', headerText: 'Giá trị mới', textAlign: 'Left', width: 150 },
        { fieldName: 'NGUOI_CN', headerText: 'Người thực hiện', textAlign: 'Left', width: 150 },
        { fieldName: 'NGAY_CN', headerText: 'Thời điểm', textAlign: 'Left', width: 150 }
      ],
      selectedRackDetail: null, // Chi tiết rack được chọn để xem
      selectedThietbiId: null, // Chi tiết thiết bị được chọn để xem
      thongTinCongSuat: {
        tongCongSuat: 0,
        tongCSDaSuDung: 0,
        tongCSConLai: 0,
        soThanhNguon: 0,
        tyLeSuDung: 0
      },
      dsLichSuThayDoi: [],
      controls: {
        ddlLoaiLQ: {
          enabled: false,
          invalid: false
        },
        txtTenLQ: {
          enabled: false,
          invalid: false
        },
        txtChieuDai: {
          enabled: false,
          invalid: false
        },
        txtChieuRong: {
          enabled: false,
          invalid: false
        },
        txtChieuCao: {
          enabled: false,
          invalid: false
        },
        txtCamera: {
          enabled: false,
          invalid: false
        },
        txtKhoa: {
          enabled: false,
          invalid: false
        },
        txtSoHuu: {
          enabled: false,
          invalid: false
        },
        txtTrangThaiVH: {
          enabled: false,
          invalid: false
        },
        ddlDVIDC: {
          enabled: false,
          invalid: false
        },
        ddlIDC: {
          enabled: false,
          invalid: false
        },
        ddlToaNha: {
          enabled: false,
          invalid: false
        },
        ddlMatSan: {
          enabled: false,
          invalid: false
        },
        ddlPhong: {
          enabled: false,
          invalid: false
        }
        // ddlRack: {
        //   enabled: false,
        //   invalid: false
        // }
      }
    }
  },
  watch: {},
  created: async function() {
    try {
      this.$root.showLoading(true)
      // Assign action column templates once
      this.columnsRack[6].template = this.createActionColumnRack()
      this.columnsThietBi[6].template = this.createActionColumnTB()
      this.columnsCongSuat[6].template = this.createActionColumnCongSuat()
      // this.columnsKhachHang[7].template = this.createActionColumnColo()

      if (this.actionMode !== 3 && this.longQuayId > 0) {
        this.currentItem = await this.getChiTietLongQuay(this.longQuayId)
      }
    } finally {
      this.$root.showLoading(false)
    }
  },
  methods: {
    getListRack: async function() {
      let result = []
      try {
        const params = {
          id: this.currentItem.PHONG_ID || 0
        }
        // let rs = await this.$root.context.post('/web-ecms/tainguyen-idc/khong-gian/get-ds-rack-chua-lapdat', params)
        let rs = await this.$root.context.post(`/web-ecms/long-quay/danhsach-rack/${this.currentItem.PHONG_ID || 0}`)
        result = rs.data || []
      } catch (e) {
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail)
        }
      }
      return result
    },
    getListPhong: async function() {
      let result = []
      try {
        let rs = await this.$root.context.post(`/web-ecms/long-quay/danhsach-phong/${this.currentItem.MATSAN_ID || 0}`)
        result = rs.data || []
      } catch (e) {
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail)
        }
      }
      return result
    },
    getTrangThaiVanHanh: async function() {
      let result = []
      try {
        let rs = await this.$root.context.post('/web-ecms/idc/danh-muc/common', {
          loaiDanhMuc: 'IDC-TRANGTHAI-VH',
          thamSo1: null
        })
        result = rs.data || []
      } catch (e) {
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail)
        }
      }
      return result
    },

    getListLoaiLongQuay: async function() {
      let result = []
      try {
        // let rs = await this.$root.context.post('/web-ecms/thongtin-longquay/get-danhmuc-trangthai', {
        //   loaiDanhMuc: 'TRANGTHAI-VANHANH',
        //   thamSo1: null
        // })
        // result = rs.data || []
        result = [
          { id: 1, ten: 'Lồng quây tiêu chuẩn' },
          { id: 2, ten: 'Lồng quây cao cấp' },
          { id: 3, ten: 'Lồng quây VIP' }
        ]
      } catch (e) {
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail)
        }
      }
      return result
    },
    getDonViIDC: async function() {
      let result = []
      try {
        let rs = await this.$root.context.post('/web-ecms/idc/danh-muc/common', {
          loaiDanhMuc: 'IDC_LOAI_DV_IDC'
        })
        result = rs.data || []
      } catch (e) {
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail)
        }
      }
      return result
    },
    getListIDC: async function() {
      let result = []
      try {
        let rs = await this.$root.context.post('/web-ecms/idc/danh-muc/common', {
          loaiDanhMuc: 'DANHMUC_IDC',
          thamSo1: this.currentItem.DV_IDC_ID || null
        })
        result = rs.data || []
      } catch (e) {
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail)
        }
      }
      return result
    },
    getListToaNha: async function() {
      let result = []
      try {
        let rs = await this.$root.context.post(
          `/web-ecms/danhmuc/idc/toanha-statistic/?id=${this.currentItem.IDC_ID || 0}`
        )
        result =
          rs.data.map((item) => ({
            ID: item.ID_TOANHA,
            TEN: item.TEN_TOANHA
          })) || []
      } catch (e) {
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail)
        }
      }
      return result
    },
    getListMatSan: async function() {
      let result = []
      try {
        // let rs = await this.$root.context.post(`/web-ecms/toanha/get-mat-san?id=${this.currentItem.TOANHA_ID || 0}`)
        let rs = await this.$root.context.post(
          `/web-ecms/danhmuc/toanha/get-mat-san?id=${this.currentItem.TOANHA_ID || 0}`
        )
        result = rs.data || []
      } catch (e) {
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail)
        }
      }
      return result
    },
    getListToaDoMatSan: async function() {
      let result = []
      try {
        // let rs = await this.$root.context.post('/web-ecms/thongtin-longquay/get-danhmuc-toado-matsan', {
        //   loaiDanhMuc: 'TOADO-MATSAN',
        //   thamSo1: null
        // })
        // result = rs.data || []
        result = [
          { ID: 1, TEN: 'Hàng 1 - Cột 1' },
          { ID: 2, TEN: 'Hàng 1 - Cột 2' },
          { ID: 3, TEN: 'Hàng 2 - Cột 1' }
        ]
      } catch (e) {
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail)
        }
      }
      return result
    },
    getListCamera: async function() {
      let result = []
      try {
        // let rs = await this.$root.context.post('/web-ecms/thongtin-longquay/get-danhmuc-camera', {
        //   loaiDanhMuc: 'CAMERA',
        //   thamSo1: null
        // })
        // result = rs.data || []
        result = [
          { id: 1, ten: 'Có' },
          { id: 2, ten: 'Không' }
        ]
      } catch (e) {
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail)
        }
      }
      return result
    },
    getListKhoa: async function() {
      let result = []
      try {
        // let rs = await this.$root.context.post('/web-ecms/thongtin-longquay/get-danhmuc-khoa', {
        //   loaiDanhMuc: 'KHOA',
        //   thamSo1: null
        // })
        // result = rs.data || []
        result = [
          { id: 1, ten: 'Khóa từ' },
          { id: 2, ten: 'Khóa cơ' },
          { id: 3, ten: 'Không có' }
        ]
      } catch (e) {
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail)
        }
      }
      return result
    },
    getAllRackByIDC: async function() {
      let result = []
      try {
        let rs = await this.$root.context.post('/web-ecms/idc/danh-muc/common', {
          loaiDanhMuc: 'IDC-RACK-BY-PHONGZONE',
          thamSo1: this.currentItem.PHONG_ID || 0
        })
        result = rs.data || []
      } catch (e) {
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail)
        }
      }
      return result
    },
    getListSoHuuRack: async function() {
      let result = []
      try {
        // let rs = await this.$root.context.post(`/web-ecms/toanha/get-mat-san?id=${this.currentItem.TOANHA_ID || 0}`)
        let rs = await this.$root.context.post(`/web-ecms/long-quay/danhsach-sohuu-rack`)
        result = rs.data || []
        console.log('listSoHuuRack', result)
      } catch (e) {
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail)
        }
      }
      return result
    },
    getChiTietLongQuay: async function(id) {
      let result = []
      try {
        // let rs = await this.$root.context.post(`/web-ecms/long-quay/chitiet/${id}`)
        let rs = await this.$root.context.post(`/web-ecms/long-quay/chitiet/${id}`)

        result = rs.data[0] || [] // Lấy bản ghi đầu tiên từ mảng data
        result = {
          ...result,
          TOADO: result.TOADO_X && result.TOADO_Y ? `${result.TOADO_X}, ${result.TOADO_Y}` : null
        }
      } catch (e) {
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail)
        }
      }
      return result
    },
    onSave: async function() {
      console.log('currentItem to save', this.currentItem)
      let result = false
      try {
        const params = {
          longquay_id: this.currentItem.LONGQUAY_ID || null,
          loai_lq_id: this.currentItem.LOAILQ_ID || null,
          phong_id: this.currentItem.PHONG_ID || null,
          ten: this.currentItem.TEN || null,
          chieu_dai: this.currentItem.CHIEUDAI || null,
          chieu_rong: this.currentItem.CHIEURONG || null,
          chieu_cao: this.currentItem.CHIEUCAO || null,
          camera: this.currentItem.CAMERA ? 1 : 0,
          khoa_id: this.currentItem.KHOA_ID || null,
          trangthai_vh: this.currentItem.TRANGTHAI_VH || null,
          ghi_chu: this.currentItem.GHICHU || null,
          ten_tat: this.currentItem.TENTAT || null,
          mo_ta: this.currentItem.MOTA || null,
          su_dung: this.currentItem.SUDUNG ? 1 : 0,
          toa_do_x: this.currentItem.TOADO_X || null,
          toa_do_y: this.currentItem.TOADO_Y || null,
          nguoi_cn: this.$root.token.getUserName() || 'admin',
          donvi_sh: Number(this.currentItem.DONVISH_ID) || null,
          ds_rack: Array.isArray(this.currentItem.RACK_ID)
            ? this.currentItem.RACK_ID.join(',')
            : this.currentItem.RACK_ID
        }
        const rs = await this.$root.context.post('/web-ecms/long-quay/save', params)
        // const rs = await this.$root.context.post('/web-ecms/long-quay/save', params)
        result = rs.data
      } catch (e) {
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail)
        }
      }
      return result
    },
    defaultItem() {
      return {
        LONGQUAY_ID: null,
        TEN_LONG_QUAY: '',
        SUDUNG: 1,
        LOAILQ_ID: null,
        PHONG_ID: null,
        CHIEU_DAI: null,
        CHIEU_RONG: null,
        CHIEU_CAO: null,
        CAMERA: 0,
        KHOA_ID: null,
        TEN_SOHUU: '',
        DONVISH_ID: null,
        TRANGTHAI_VH: null,
        MO_TA_LONG_QUAY: '',
        GHI_CHU: '',
        TOADO_X: null,
        TOADO_Y: null,
        RACK_ID: []
      }
    },
    clearAll() {
      this.currentItem = this.defaultItem()
      this.listRack = []
    },

    closeModal() {
      this.clearAll()
      this.$refs.ModalThongTinLongQuay.hide()
    },
    async showModal(action) {
      this.listLoaiLongQuay = await this.getListLoaiLongQuay()
      this.listCamera = await this.getListCamera()
      this.listKhoa = await this.getListKhoa()
      this.listSoHuu = await this.getListSoHuuRack()
      this.listTrangThaiVanHanh = await this.getTrangThaiVanHanh()
      this.listDVIDC = await this.getDonViIDC()
      this.actionMode = action
      this.currentTab = 1
      this.clearAll()
      if (this.actionMode === 1) {
        this.enableControls(false)
      } else {
        this.enableControls(true)
      }
      await this.loadData()
      this.$refs.ModalThongTinLongQuay.show()
    },
    async loadData() {
      if (this.longquayId > 0 && this.actionMode !== 3) {
        this.currentItem = await this.getChiTietLongQuay(this.longquayId)
        console.log('currentItem loaded', this.currentItem)
        this.currentItem.RACK_ID = this.currentItem.DS_RACK ? this.currentItem.DS_RACK.split(',').map(Number) : []
        let dsAllRack = await this.getAllRackByIDC()
        if (this.currentItem.DV_IDC_ID) {
          this.listIDC = await this.getListIDC()
          if (this.currentItem.IDC_ID) {
            this.listToaNha = await this.getListToaNha()
            if (this.currentItem.TOANHA_ID) {
              this.listMatSan = await this.getListMatSan()
              if (this.currentItem.MATSAN_ID) {
                this.listPhong = await this.getListPhong()
                if (this.currentItem.PHONG_ID) {
                  this.listRack = await this.getListRack()
                  if (this.currentItem.RACK_ID && this.currentItem.RACK_ID.length > 0) {
                    dsAllRack = dsAllRack
                      .filter((rack) => this.currentItem.RACK_ID.includes(rack.ID))
                      .map((rack) => {
                        return {
                          ID: rack.ID,
                          TEN: rack.TEN ? rack.TEN : rack.TENTAT ? rack.TENTAT : ''
                        }
                      })
                    this.listRack = this.listRack.concat(dsAllRack)
                  }
                }
              }
            }
          }
        }
      }
    },
    enableControls: function(args) {
      for (const key in this.controls) {
        if (this.controls.hasOwnProperty(key)) {
          this.controls[key].enabled = args
        }
      }
      this.buttons.btnGhiLai.enabled = args
      this.buttons.btnHuy.enabled = this.buttons.btnGhiLai.enabled
      this.buttons.btnSua.enabled = !this.buttons.btnGhiLai.enabled
    },
    validateForm() {
      let errors = []
      Object.keys(this.controls).forEach((k) => (this.controls[k].invalid = false))
      if (!this.currentItem.TEN) {
        this.controls.txtTenLQ.invalid = true
        errors.push('Tên lồng quây không được để trống')
      }
      if (!this.currentItem.TRANGTHAI_VH) {
        this.controls.txtTrangThaiVH.invalid = true
        errors.push('Trạng thái vận hành không được để trống')
      }
      console.log('currentItem.LOAILQ_ID', this.currentItem.LOAILQ_ID)
      if (!this.currentItem.LOAILQ_ID) {
        this.controls.ddlLoaiLQ.invalid = true
        console.log('validate loailq', this.controls.ddlLoaiLQ.invalid)
        errors.push('Loại lồng quây không được để trống')
      }
      if (!this.currentItem.CHIEUDAI) {
        this.controls.txtChieuDai.invalid = true
        errors.push('Chiều dài không được để trống')
      }
      if (this.currentItem.CHIEUDAI && this.currentItem.CHIEUDAI <= 0) {
        this.controls.txtChieuDai.invalid = true
        errors.push('Chiều dài phải lớn hơn 0')
      }
      if (!this.currentItem.CHIEURONG) {
        this.controls.txtChieuRong.invalid = true
        errors.push('Chiều rộng không được để trống')
      }
      if (this.currentItem.CHIEURONG && this.currentItem.CHIEURONG <= 0) {
        this.controls.txtChieuRong.invalid = true
        errors.push('Chiều rộng phải lớn hơn 0')
      }
      if (!this.currentItem.CHIEUCAO) {
        this.controls.txtChieuCao.invalid = true
        errors.push('Chiều cao không được để trống')
      }
      if (this.currentItem.CHIEUCAO && this.currentItem.CHIEUCAO <= 0) {
        this.controls.txtChieuCao.invalid = true
        errors.push('Chiều cao phải lớn hơn 0')
      }
      // if (!this.currentItem.CAMERA) {
      //   this.controls.txtCamera.invalid = true
      //   errors.push('Thông tin camera không được để trống')
      // }
      if (!this.currentItem.KHOA_ID) {
        this.controls.txtKhoa.invalid = true
        errors.push('Khóa không được để trống')
      }
      if (!this.currentItem.DONVISH_ID) {
        this.controls.txtSoHuu.invalid = true
        errors.push('Sở hữu không được để trống')
      }
      if (this.currentItem.DV_IDC_ID === null || this.currentItem.DV_IDC_ID === undefined) {
        this.controls.ddlDVIDC.invalid = true
        errors.push('Đơn vị IDC không được để trống')
      }
      if (this.currentItem.IDC_ID === null || this.currentItem.IDC_ID === undefined) {
        this.controls.ddlIDC.invalid = true
        errors.push('IDC không được để trống')
      }
      if (this.currentItem.TOANHA_ID === null || this.currentItem.TOANHA_ID === undefined) {
        this.controls.ddlToaNha.invalid = true
        errors.push('Tòa nhà không được để trống')
      }
      if (this.currentItem.MATSAN_ID === null || this.currentItem.MATSAN_ID === undefined) {
        this.controls.ddlMatSan.invalid = true
        errors.push('Mặt sàn không được để trống')
      }
      if (this.currentItem.PHONG_ID === null || this.currentItem.PHONG_ID === undefined) {
        this.controls.ddlPhong.invalid = true
        errors.push('Phòng không được để trống')
      }
      return errors
    },

    btnGhiLai_Click: async function() {
      const errors = this.validateForm()

      if (errors.length) {
        this.$toast.error(errors.join('\n'))
        return
      } else {
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
            let rs = await this.onSave(this.currentItem)
            if (rs) {
              if (this.actionMode === 3) {
                this.$root.toastSuccess('Thêm mới lồng quây thành công')
                this.$refs.ModalThongTinLongQuay.hide()
              } else {
                this.$root.toastSuccess('Cập nhật lồng quây thành công')
              }
              this.$parent.reloadDSLongQuay()
              // this.$parent.reloadHistory()
            } else {
              if (this.actionMode === 3) {
                this.$root.toastError('Thêm lồng quây thất bại')
              } else {
                this.$root.toastError('Cập nhật lồng quây thất bại')
              }
            }
            this.enableControls(!rs)
            this.actionMode = 1
          } finally {
            this.$root.showLoading(false)
          }
        }
      }
    },
    btnSua_Click: async function() {
      this.actionMode = 2
      this.enableControls(true)
    },
    btnHuy_Click: async function() {
      try {
        this.$root.showLoading(true)
        if (this.actionMode === 3) {
          this.clearAll()
        } else {
          let rs = await this.getChiTietLongQuay(this.currentItem.LONGQUAY_ID)
          this.currentItem = rs
          console.log('currentItem after cancel', this.currentItem)
          // this.enableControls(false)
        }
      } finally {
        this.$root.showLoading(false)
      }
    },
    // Create action column templates once, not on every render
    createActionColumnRack() {
      const parent = this
      return function() {
        return {
          template: {
            template: `<div class="d-flex gap-1 justify-content-center">
              <button class="btn btn-sm btn-outline-primary" title="Xem" @click="onView">
                <i class="fa fa-eye"></i>
              </button>
              <button class="btn btn-sm btn-outline-danger" title="Xóa" @click="onDelete">
                <i class="fa fa-trash"></i>
              </button>
            </div>`,
            methods: {
              onView() {
                parent.onViewRack(this.data)
              },
              onDelete() {
                parent.thaoGoRack(this.data)
              }
            }
          }
        }
      }
    },
    createActionColumnTB() {
      const parent = this
      return function() {
        return {
          template: {
            template: `<div class="d-flex gap-1 justify-content-center">
              <button class="btn btn-sm btn-outline-primary" title="Xem" @click="onView">
                <i class="fa fa-eye"></i>
              </button>
            </div>`,
            methods: {
              onView() {
                parent.onViewThietBi(this.data)
              }
            }
          }
        }
      }
    },
    createActionColumnCongSuat() {
      const parent = this
      return function() {
        return {
          template: {
            template: `<div class="d-flex gap-1 justify-content-center">
              <button class="btn btn-sm btn-outline-primary" title="Xem" @click="onView">
                <i class="fa fa-eye"></i>
              </button>
            </div>`,
            methods: {
              onView() {
                parent.onViewCongSuat(this.data)
              }
            }
          }
        }
      }
    },
    createActionColumnColo() {
      const parent = this
      return function() {
        return {
          template: {
            template: `<div class="d-flex gap-1 justify-content-center">
              <button class="btn btn-sm btn-outline-primary" title="Xem" @click="onView">
                <i class="fa fa-eye"></i>
              </button>
            </div>`,
            methods: {
              onView() {
                parent.onViewKhachHang(this.data)
              }
            }
          }
        }
      }
    },
    // Action handlers
    async onViewRack(data) {
      // Set rack detail data
      this.selectedRackDetail = {
        id: String(data.ID)
      }
      // Show modal
      this.$refs.rackDetailModal.show()
    },
    onModalClose() {
      this.selectedRackDetail = null
    },
    onViewThietBi(data) {
      this.selectedThietbiId = data.THIETBI_ID
      console.log('View thiet bi:', this.selectedThietbiId)
      // Show modal
      this.$nextTick(() => {
        this.$refs.modalChiTietThietBi.show(null, 'VIEW')
      })
    },
    closeDeviceModal() {
      this.$refs.modalChiTietThietBi.hide()
      this.selectedThietbiId = null
    },
    onViewCongSuat(data) {
      console.log('View cong suat:', data)
      this.$refs.popupThanhNguon.dialogOpen(data.THANHNGUON_ID)
    },
    onViewKhachHang(data) {
      console.log('View khach hang:', data)
      // TODO: Implement view khach hang logic
    },
    actionColumnTB(parent) {
      return function() {
        return {
          template: {
            template: `<div class="action-buttons gap-1">
                <button class="btn btn-sm btn-outline-success" title="Xem" @click="onViewThietBi()">
                  <i class="fa fa-eye"></i>
                </button>
              </div>`,
            computed: {},
            methods: {
              onViewThietBi() {
                parent.onViewItem()
              }
            }
          }
        }
      }
    },
    actionColumnCongSuat(parent) {
      return function() {
        return {
          template: {
            template: `<div class="action-buttons gap-1">
                <button class="btn btn-sm btn-outline-success" title="Xem" @click="onViewCongSuat()">
                  <i class="fa fa-eye"></i>
                </button>
              </div>`,
            computed: {},
            methods: {
              onViewCongSuat() {
                parent.onViewItem()
              }
            }
          }
        }
      }
    },
    actionColumnColo(parent) {
      return function() {
        return {
          template: {
            template: `<div class="action-buttons gap-1">
                <button class="btn btn-sm btn-outline-success" title="Xem" @click="onViewKhachHang()">
                  <i class="fa fa-eye"></i>
                </button>
              </div>`,
            computed: {},
            methods: {
              onViewKhachHang() {
                parent.onViewItem()
              }
            }
          }
        }
      }
    },
    // Clean up to prevent memory leaks
    beforeDestroy() {
      // Clear all data arrays
      this.dsRackTrongLongQuay = []
      this.dsThietBi = []
      this.dsCongSuatDien = []
      this.dsKhachHang = []
      // Clear dropdown lists
      this.listCamera = []
      this.listKhoa = []
      this.listSoHuu = []
      this.listTrangThaiVanHanh = []
      this.listDVIDC = []
      this.listIDC = []
      this.listToaNha = []
      this.listMatSan = []
      this.listToaDoMatSan = []
      this.listRack = []
      this.listPhong = []
      this.dsLichSuThayDoi = []
      // Destroy grids
      if (this.$refs.grdRacks) {
        this.$refs.grdRacks.destroy()
      }
      if (this.$refs.grdThietBi) {
        this.$refs.grdThietBi.destroy()
      }
      if (this.$refs.grdCongSuatDien) {
        this.$refs.grdCongSuatDien.destroy()
      }
      if (this.$refs.grdKhachHang) {
        this.$refs.grdKhachHang.destroy()
      }
      // Clear column templates
      if (this.columnsRack && this.columnsRack[6]) {
        this.columnsRack[6].template = null
      }
      if (this.columnsThietBi && this.columnsThietBi[6]) {
        this.columnsThietBi[6].template = null
      }
      if (this.columnsCongSuat && this.columnsCongSuat[6]) {
        this.columnsCongSuat[6].template = null
      }
      // if (this.columnsKhachHang && this.columnsKhachHang[7]) {
      //   this.columnsKhachHang[7].template = null
      // }
      // Clear current item
      this.currentItem = null
    },
    onChangeTab: async function(index) {
      this.currentTab = index
      if (this.currentTab === 1) {
        //  await this.loadData()
      } else if (this.currentTab === 2) {
        this.dsRackTrongLongQuay = await this.getDSRack()
        this.dsRackTrongLongQuay = this.dsRackTrongLongQuay.map((item) => ({
          ...item,
          TYLE: item.TYLE ? item.TYLE + '%' : '0%'
        }))
      } else if (this.currentTab === 3) {
        this.dsThietBi = await this.getDSThietBi()
      } else if (this.currentTab === 4) {
        this.dsCongSuatDien = await this.getDSThanhNguon()
        this.thongTinCongSuat.tongCongSuat = this.dsCongSuatDien[0]?.TONG_CONGSUAT || 0
        this.thongTinCongSuat.tongCSDaSuDung = this.dsCongSuatDien[0]?.TONG_CS_SUDUNG || 0
        this.thongTinCongSuat.tongCSConLai = this.dsCongSuatDien[0]?.TONG_CS_CONLAI || 0
        this.thongTinCongSuat.soThanhNguon = this.dsCongSuatDien[0]?.SO_THANHNGUON || 0
        this.thongTinCongSuat.tyLeSuDung = this.dsCongSuatDien[0]?.TYLE || 0
      } else if (this.currentTab === 5) {
        this.dsKhachHang = await this.getDSKhachHang()
      } else if (this.currentTab === 6) {
        // this.dsLichSuThayDoi = await this.getDSLichSu()
        this.$refs.grdLichSu.refreshData()
      }
    },
    onOverlayClick: function() {
      this.clearAll()
      this.enableControls(false)
      this.$refs.ModalThongTinLongQuay.hide()
    },
    onChangeDVIDC: async function() {
      this.listIDC = await this.getListIDC()
      this.currentItem.IDC_ID = null
    },
    onChangeIDC: async function() {
      this.listToaNha = await this.getListToaNha()
      this.currentItem.TOANHA_ID = null
    },
    onChangeToaNha: async function() {
      this.listMatSan = await this.getListMatSan()
      this.currentItem.MATSAN_ID = null
    },
    onChangeMatSan: async function() {
      this.listPhong = await this.getListPhong()
      this.currentItem.PHONG_ID = null
    },
    onChangePhong: async function() {
      this.listRack = await this.getListRack()
      this.currentItem.RACK_ID = []
    },
    // Normalizer cho TreeSelect
    normalizer(node) {
      return {
        id: node.ID,
        label: node.TEN,
        children: node.children
      }
    },

    // Tháo gỡ Rack khỏi lồng quay
    async thaoGoRack(data) {
      try {
        const flag = await this.$bvModal.msgBoxConfirm(`Bạn có chắc chắn muốn tháo gỡ Rack khỏi lồng quây này không?`, {
          title: 'Thông báo',
          size: 'md',
          okTitle: 'Đồng ý',
          cancelTitle: 'Hủy',
          centered: true
        })
        if (flag) {
          try {
            this.$root.showLoading(true)
            // let response = await this.$root.context.post('/web-ecms/thongtin-longquay/delete', {
            //   id: data.ID_LONG_QUAY
            // })
            let response = await this.$root.context.post(`/web-ecms/long-quay/thaogo-rack/${data.ID}`)
            console.log('response delete', response)
            if (response && response.error_code === 'BSS-00000000') {
              this.$toast.success('Tháo gỡ Rack thành công')
              // Reload danh sách Rack trong lồng quay
              this.dsRackTrongLongQuay = await this.getDSRack()
              await this.loadData()
              this.$parent.reloadDSLongQuay()
            } else {
              this.$toast.error(response.message_detail)
            }
          } finally {
            this.$root.showLoading(false)
          }
        }
      } catch (error) {
        console.error('delete error:', error)

        this.$toast.error('Có lỗi xảy ra trong quá trình thực hiện')
      }
    },
    getDSRack: async function() {
      let result = []
      try {
        this.loading(true)
        // let rs = await this.$root.context.post('/web-ecms/long-quay/danh-sach', params)
        let rs = await this.$root.context.post(`/web-ecms/long-quay/rack-theo-longquay/${this.currentItem.LONGQUAY_ID}`)
        result = rs.data || []
      } catch (e) {
        console.log('Error fetching data:', e)
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail)
        }
      } finally {
        this.loading(false)
      }
      return result
    },
    getDSThietBi: async function() {
      let result = []
      try {
        this.loading(true)
        let rs = await this.$root.context.post(
          `/web-ecms/long-quay/thietbi-theo-longquay/${this.currentItem.LONGQUAY_ID}`
        )
        result = rs.data || []
      } catch (e) {
        console.log('Error fetching data:', e)
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail)
        }
      } finally {
        this.loading(false)
      }
      return result
    },
    getDSThanhNguon: async function() {
      let result = []
      try {
        this.loading(true)
        let rs = await this.$root.context.post(
          `/web-ecms/long-quay/thanhnguon-theo-longquay/${this.currentItem.LONGQUAY_ID}`
        )
        result = rs.data || []
      } catch (e) {
        console.log('Error fetching data:', e)
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail)
        }
      } finally {
        this.loading(false)
      }
      return result
    },
    getDSLichSu: async function() {
      let result = []
      try {
        this.loading(true)
        const params = {
          longquay_id: this.currentItem.LONGQUAY_ID || 0
        }
        let rs = await this.$root.context.post(`web-ecms/long-quay/lichsu-thaydoi-longquay`, params)
        result = rs.data || []
      } catch (e) {
        console.log('Error fetching data:', e)
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail)
        }
      } finally {
        this.loading(false)
      }
      return result
    },
    getDSKhachHang: async function() {
      let result = []
      try {
        this.loading(true)
        const params = {
          longquay_id: this.currentItem.LONGQUAY_ID || 0
        }
        let rs = await this.$root.context.post(`web-ecms/long-quay/khachhang`, params)
        result = rs.data || []
      } catch (e) {
        console.log('Error fetching data:', e)
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail)
        }
      } finally {
        this.loading(false)
      }
      return result
    },
    // isNumber: function(evt) {
    //   evt = evt ? evt : window.event
    //   var charCode = evt.which ? evt.which : evt.keyCode
    //   if (charCode > 31 && (charCode < 49 || charCode > 57) && evt.target.value == '') {
    //     evt.preventDefault()
    //   }
    // }
    isNumber(evt) {
      const charCode = evt.which ? evt.which : evt.keyCode
      // Cho phép các phím điều hướng / chỉnh sửa
      const allowedKeys = [8, 9, 13, 37, 39, 46] // Backspace, Tab, Enter, ←, →, Delete
      if (allowedKeys.includes(charCode)) return
      // Chỉ cho phép số (0–9)
      if (charCode < 48 || charCode > 57) {
        evt.preventDefault()
      }
    },
    // Khi dán, chỉ giữ lại chữ số
    handlePaste(field, e) {
      const text = (e.clipboardData || window.clipboardData).getData('text') || ''
      const digits = text.replace(/\D/g, '') // chỉ giữ số
      if (!digits) return // dán không có số -> bỏ qua
      let num = parseInt(digits, 10)

      // ép min = 1
      if (!Number.isFinite(num) || num < 0) num = 1

      // cập nhật model (giữ reactivity)
      this.$set(this.currentItem, field, num)
    }
  }
}
</script>
<style scoped>
.card-box {
  border-radius: 6px;
  border: 1px solid #dee2e6;
  box-shadow: 0 1px 2px rgba(0, 0, 0, 0.05);
  transition: all 0.2s ease-in-out;
}

.card-box:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.08);
}

.bg-light-blue {
  background-color: #f0f6ff;
  border-left: 4px solid #007bff;
}

.bg-light-red {
  background-color: #fff5f2;
  border-left: 4px solid #dc3545;
}

.bg-light-green {
  background-color: #f2fff5;
  border-left: 4px solid #28a745;
}

.card-title {
  font-size: 0.9rem;
  color: #333;
}

.card-body h2 {
  font-size: 1.75rem;
}
.tabs a.disabled {
  pointer-events: none;
  opacity: 0.5;
  cursor: not-allowed;
}
</style>
