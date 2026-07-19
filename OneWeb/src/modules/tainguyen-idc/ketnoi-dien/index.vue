<template src="./index.template.html"></template>
<script>
import { created } from '@syncfusion/ej2-vue-grids'
import api from './api'
import ModalChuyenXuly from './ModalChuyenXuLy/index.vue'
import ModalChonKetNoi from './modal-chon-ketnoi/index.vue'
import ThongTinCongViec from '../components/ThongTinCongViec/index.vue'
import ModalChonThietBi from './modal-chon-thietbi/index.vue'
import TabHistory from '../components/Tabs/TabHistory/index.vue'

import {
  LOAI_DANH_MUC,
  LOAI_KETNOI,
  TRANG_THAI_PHIEU,
  port_type_options,
  trang_thai_kn_vl_options,
  trang_thai_kn_vh_options
} from './constant'

export default {
  name: 'TaoKetNoi',
  components: {
    ModalChuyenXuly,
    ModalChonKetNoi,
    ThongTinCongViec,
    ModalChonThietBi,
    TabHistory
  },
  props: {
    loai_ketnoi: {
      type: Number,
      default: () => LOAI_KETNOI.KN_DIEN.id
    },
    // action: {
    //   type: String,
    //   default: 'edit'
    // }
    // phieutc: {
    //   type: Number,
    //   default: () => null
    // }
  },
  data() {
    return {
      action: 'edit',
      listIDC: [],
      listLopKetNoi: [],
      listLoaiVatLieuKetNoi: [],
      listHinhThucKetNoi: [],
      listTbi: [],
      listTbiNguonCon: [],
      listTbiDichCon: [],
      listPortNguon: [],
      listPortDich: [],
      port_type_options: port_type_options,
      trang_thai_kn_vl_options: trang_thai_kn_vl_options,
      trang_thai_kn_vh_options: trang_thai_kn_vh_options,
      controls: {
        idc: false,
        loai_ketnoi: false,
        thoidiem: false,
        nguoi_taoketnoi: false,
        loai_port: false,
        port_nguon: false,
        loai_port_dich: false,
        port_dich: false,
        thanh_nguon: false,
        thanh_nguon_dich: false,
        o_cam_nguon: false,
        o_cam_dich: false
      },
      phieucv: {
        ma_cv: null,
        donvi: {
          id: null,
          text: null
        },
        loai_cv: null,
        nguoi_thuchien: null,
        hangmuc_trienkhai: null,
        ngay_tao: null,
        cancuphieuyc: null,
        trangthai: null,
        ma_cv_cha: null,
        thoi_diem_ht: null,
        khach_hang: null,
        ma_tb: null,
        id_ketnoi: null
      },
      current_item: {
        id: null,
        idc: null,
        nguoi_taoketnoi: null,
        loai_ketnoi: null,
        khoang_cach: null,
        hinhthuc: null,
        thoidiem: this.formatDate(new Date()),
        loai_vat_lieu: null,
        trangthai_vl: 1,
        lop: null,
        trangthai_vh: null,
        cancu_thuchien: null,
        tb_nguon_id: null,
        tb_nguon_ten: null,
        tb_nguon_con: null,
        loai_port: null,
        port_nguon: null,
        tb_nguon_phutro: null,
        tb_dich_id: null,
        tb_dich_ten: null,
        tb_dich_con: null,
        loai_port_dich: null,
        port_dich: null,
        tb_dich_phutro: null,
        port_nguon_ten: null,
        port_dich_ten: null,
        trangthai_phieuYC: null,
        thanh_nguon: null,
        thanh_nguon_dich: null,
        loai_o_cam_nguon: null,
        loai_o_cam_dich: null,
        o_cam_nguon: null,
        o_cam_dich: null,
        patch_pannel_nguon: null,
        patch_pannel_dich: null,
        loai_port_rack_nguon: null,
        loai_port_rack_dich: null,
        port_rack_nguon: null,
        port_rack_dich: null,
        port_rack_dich_ten: null,
        port_rack_nguon_ten: null,
        flag_TB_nguon: 0, // cờ phân biệt TB nguồn là TB hay rack
        flag_TB_dich: 0, // cờ phân biệt TB đích là TB hay rack
        thanh_nguon_ten: null,
        thanh_nguon_dich_ten: null
      },
      idPhieuTC: null,
      idKetNoi: null,
      buttons: {
        btnTranfer: {
          enabled: false
        },
        btnTraPhieu: {
          enabled: true
        },
        btnHoanThanh: {
          enabled: false
        }
      },
      loaicv: null,
      ds_ketnoi: [], // danh sách kết nối của phiếu thi công
      ds_lichsu: [],
      labelButtonThiCong: '',
      isEditThayDoi: false,
      phieuThiCongDetail: {
        MA_CV: null,
        TEN_DV: null,
        TEN_NV: null,
        LOAI_CV: null,
        DANHSACH_NV_PHOIHOP: null,
        TEN_CV: null,
        NGAY_YC: null,
        CANCU_YC: null,
        TRANGTHAI_ID: null,
        MA_CV_CHA: null,
        NGAY_HT: null,
        LOAICV_ID: null
      },
      idThicongkn: null,
      phieutc: null,
      isKhachHang: false,
      typeHeThong: 1, // 1: thiết bị nguồn, 2: thiết bị đích
      lyDoTraPhieu: '',
      tab_index: 1,
      listPortRackDich: [],
      listPortRackNguon: [],
      listThanhNguon: [],
      listLoaiOCam: [],
      listOCamDich: [],
      listPatchPannelNguon: [],
      listPatchPannelDich: [],
      listLoaiPortRack: [],
      listLoaiPortRack: [],
      listThanhNguonDich: [],
      phanloai_thietbi: 0,
      phanloai_rack: 0,
      selectedRow: null,
      daGhiLaiThongTinKetNoi: false
    }
  },
  computed: {
    isTiepNhan() {
      return this.phieuThiCongDetail.TRANGTHAI_ID == 21 && (this.loaicv == 30 || this.loaicv == 56)
    },
    isDisabled() {
      // thay doi thong tin kết nối
      if (this.loaicv == 66 || this.loaicv == 67 || this.phieuThiCongDetail.TRANGTHAI_ID == 81) {
        return true
      }
      // tiếp nhận
      // if (this.isTiepNhan) {
      //   return true
      // }
      return false
    },
    isValidForGhiLai() {
      if (this.ds_ketnoi.length > 0 || this.loaicv == 30 || this.loaicv == 56) {
        return true
      }
      return false
    },
    isThayDoi() {
      // lap dat
      console.log('this.phieuThiCongDetail', this.phieuThiCongDetail.TRANGTHAI_ID, this.action)
      if (
        this.phieuThiCongDetail.TRANGTHAI_ID === 81 ||
        ((this.loaicv == 56 || this.loaicv == 30) && this.action !== 'edit')
      ) {
        console.log('lap dat ', this.loaicv)
        return true // disable
      }

      if (this.loaicv == 68 || this.loaicv == 65) {
        console.log('xoa ', this.loaicv)
        return true // disable
      }

      // thay doi
      if ((this.loaicv == 66 || this.loaicv == 67) && (this.action !== 'edit' || this.idKetNoi == null)) {
        console.log('thay doi ', this.loaicv)
        console.log('action: ', this.action)
        return true // disable
      }

      return false
    },
    nguoiTaoKetNoiDisplay() {
      return this.$root.context.user.getTenNhanVien()
    },
    loaiKetNoiDisplay() {
      return Object.values(LOAI_KETNOI).find((x) => x.id === this.current_item.loai_ketnoi)?.text
    },
    btnHoanThanhDisabled() {
      console.log('this.phieuThiCongDetail.TRANGTHAI_ID', this.phieuThiCongDetail.TRANGTHAI_ID)
      return (
        this.phieuThiCongDetail.TRANGTHAI_ID == 23 ||
        this.phieuThiCongDetail.TRANGTHAI_ID == 24 ||
        this.phieuThiCongDetail.TRANGTHAI_ID == 61 ||
        this.idKetNoi === null 
      )
    },
    btnTranferEnabled() {
      return (
        this.phieuThiCongDetail.TRANGTHAI_ID === 21 ||
        this.phieuThiCongDetail.TRANGTHAI_ID === 81 ||
        this.idKetNoi === null
      )
    },
    btnTraPhieuEnabled() {
      return this.phieuThiCongDetail.TRANGTHAI_ID === 21 || this.idKetNoi === null
    },
    btnSaveDisabled() {
      return (
        this.phieuThiCongDetail.TRANGTHAI_ID === 23 ||
        this.phieuThiCongDetail.TRANGTHAI_ID === 24 ||
        this.loaicv == 68 ||
        this.loaicv == 65 ||
        (this.idKetNoi === null && (this.loaicv == 66 || this.loaicv == 67))
      )
    },
    isCreateMode() {
      return this.phieuThiCongDetail.TRANGTHAI_ID == 21 || this.phieuThiCongDetail.TRANGTHAI_ID == 81
    },
    isWaitProcessMode() {
      return (
        this.phieuThiCongDetail.TRANGTHAI_ID == 81 &&
        this.phieuThiCongDetail.NHANVIEN_TH_ID == this.$root.token.getNhanVienID()
      )
    },
    isThucHienThiCong() {
      return (
        this.phieuThiCongDetail.TRANGTHAI_ID == 22 &&
        this.phieuThiCongDetail.NHANVIEN_TH_ID == this.$root.token.getNhanVienID() &&
        !(this.loaicv == 65 || this.loaicv == 68)
      )
    }
  },
  mounted: async function() {
    await this.onLoadPhieuCV()
    await this.onLoadDanhMucForm()
    await this.onLoadDanhSachKetNoi()

    // if (this.loaicv == 65 || this.loaicv == 68 || this.loaicv == 66 || this.loaicv == 67) {
    //   await this.onLoadDanhSachKetNoi()
    // }
  },
  async created() {
    this.phieutc = Number(this.$route.query.phieutc)
    this.action = this.$route.query.isEditMode === 'true' ? 'edit' : 'view'
    this.idKetNoi = Number(this.$route.query.ketnoi_id) || null
    this.loai_ketnoi = parseInt(this.$route.query.loai_ketnoi) || LOAI_KETNOI.KN_DIEN.id
  },
  methods: {
    navigate: function(loaicv) {
      const type = this.isKhachHang ? 'khachhang' : 'idc'
      switch (loaicv) {
        case 30:
          this.$router.push({ path: '/tainguyen-idc/lapdat-ketnoi-dien/' + type })
          break
        case 56:
          this.$router.push({ path: '/tainguyen-idc/lapdat-ketnoi-mang/' + type })
          break
        case 65:
          this.$router.push({ path: '/tainguyen-idc/thaogo-ketnoi-dien/' + type })
          break
        case 68:
          this.$router.push({ path: '/tainguyen-idc/thaogo-ketnoi-mang/' + type })
          break
        case 66:
          this.$router.push({ path: '/tainguyen-idc/thaydoi-tt-ketnoi-dien/' + type })
          break
        case 67:
          this.$router.push({ path: '/tainguyen-idc/thaydoi-tt-ketnoi-mang/' + type })
          break
        default:
      }
    },
    onTransferProcess: async function() {
      this.$refs.ModalChuyenXuly.show(this.phieuThiCongDetail, 'ADD', this.phieutc)
    },
    chuyenXylySuccess: async function() {
      await this.onLoadPhieuCV()
    },
    onSaveKetNoi: async function() {
      if (this.phieuThiCongDetail.TRANGTHAI_ID !== 21) {
        if (!this.validateFormNguon()) {
          console.log('validate nguồn không được')
          return 0
        }
        if (!this.validateFormDich()) {
          console.log('validate đích không được')
          return 0
        }
      } else {
        if (!this.validateTbiNotNull()) {
          return 0
        }
      }

      try {
        const response = await api.saveKetNoi(this.axios, {
          ...this.current_item,
          phieutc: this.$route.query.phieutc,
          idLoaiCv: this.loaicv,
          idthicongkn: this.idThicongkn
        })

        if (response.data.error == 200) {
          this.idKetNoi = response.data.data
          this.$toast.success('Lưu thông tin kết nối thành công')
          await this.onLoadDanhSachKetNoi()
          // this.navigate(this.loaicv)
        }
      } catch (error) {
        this.$toast.error(error.data?.message_detail || 'Lỗi lưu thông tin kết nối!')
      }
    },
    onSaveThiCongKetNoiTuDanhSachKetNoi: async function() {
      try {
        // lấy thông tin các kết nối vừa tạo/chọn từ danh sách kết nối
        let dsIdKetNoi = this.ds_ketnoi.map((item) => this.parseKetNoiId(item.F_KETNOI_ID))
        let dsIdKetNoiString = dsIdKetNoi.join(',')
        let data = {
          idPhieuTc: this.phieutc,
          dsIdKetNoi: dsIdKetNoiString,
          nguoiThucHien: this.phieuThiCongDetail.NHANVIEN_TH_ID
        }

        // tạo thi công kết nối
        const response = await api.saveThiCongKetNoiByPhieu(this.axios, data)

        if (response.error == 200) {
          this.$toast.success('Ghi lại thành công')
        }
      } catch (error) {
        this.$toast.error(error.data?.message_detail || 'Lỗi lưu thông tin thi công kết nối!')
      }
    },
    onGhiLai: async function() {
      this.$root.showLoading(true)
      if (this.phieuThiCongDetail.TRANGTHAI_ID === 21) {
        // lưu thông tin phiếu thi công
        let rs = await this.$refs.thongTinCongViec.onSave()
        if (rs) {
          await this.loadPhieuThiCongDetail()
        }

        if (this.loaicv == 30 || this.loaicv == 56) {
          // lưu thông tin thiết bị nguồn/đích mà người tạo chọn
          const res = await this.onSaveKetNoi()
          if (res === 0) {
            return
          }
        } else {
          if (this.ds_ketnoi.length === 0) {
            return
          }
          // tạo thi công kết nối từ danh sách các kết nối
          await this.onSaveThiCongKetNoiTuDanhSachKetNoi()
        }

        // đã ghi lại
        this.daGhiLaiThongTinKetNoi = true
      } else {
        // lưu thông tin kết nối của người thực hiện
        await this.onSaveKetNoi()
      }
      this.$root.showLoading(false)
      // await this.onLoadDanhSachKetNoi()
    },
    onChuyenThucHien() {
      try {
        this.$confirm('Xác nhận chuyển phiếu thi công sang trạng thái Đang thực hiện?', {
          confirmButtonText: 'Xác nhận',
          cancelButtonText: 'Huỷ',
          type: 'warning'
        }).then(async () => {
          if (this.loaicv !== 30 && this.loaicv !== 56 && this.ds_ketnoi.length === 0) {
            this.$toast.error(
              'Vui lòng thêm ít nhất một kết nối trước khi chuyển phiếu thi công sang trạng thái Đang thực hiện!'
            )
            return
          }
          this.$root.showLoading(true)
          let rs = await this.$refs.thongTinCongViec.chuyenThucHien()
          if (rs) {
            await this.loadPhieuThiCongDetail()
          }
        })
      } catch (error) {
        this.$toast.error(error?.message || 'Lỗi chuyển phiếu thi công!')
      } finally {
        this.$root.showLoading(false)
      }
    },
    onXacNhanThucHien() {
      this.$confirm('Xác nhận thực hiện phiếu thi công này?', {
        confirmButtonText: 'Xác nhận',
        cancelButtonText: 'Huỷ',
        type: 'warning'
      }).then(async () => {
        this.$root.showLoading(true)
        let rs = await this.$refs.thongTinCongViec.xacNhanThucHien()
        if (rs) {
          this.action = 'edit'
          await this.loadPhieuThiCongDetail()
          await this.onLoadDanhSachKetNoi()
        }
        this.$root.showLoading(false)
      })
    },
    async loadPhieuThiCongDetail() {
      try {
        this.phieuThiCongDetail = await api.getChiThietPhieuThiCong(this.axios, this.phieutc)
        this.current_item.trangthai_phieuYC = this.phieuThiCongDetail.TRANGTHAI_ID
      } catch (error) {
        this.$root.toastError(error?.message_detail || 'Có lỗi xảy ra khi lấy thông tin phiếu thi công')
      }
    },
    onHoanThanh: async function() {
      try {
        this.$root.showLoading(true)

        // Gỡ kết nối
        if (this.loaicv == 65 || this.loaicv == 68) {
          // lấy danh sách id kết nối
          let dsIdKetNoi = this.ds_ketnoi.map((item) => this.parseKetNoiId(item.F_KETNOI_ID))
          let dsIdKetNoiString = dsIdKetNoi.join(',')

          const params = {
            idPhieuTc: this.phieutc,
            dsIdKetNoi: dsIdKetNoiString,
            nguoiThucHien: this.phieuThiCongDetail.NHANVIEN_TH_ID
          }

          await api.saveThiCongKetNoiByPhieu(this.axios, params)
          await api.deleteKetNoi(this.axios, params)
        }

        if (!this.validateTrangThaiThiCong(this.ds_ketnoi)) {
          this.$toast.error('Vui lòng ghi lại thông tin của tất cả các kết nối trước khi hoàn thành phiếu thi công!')
          return
        }

        // cập nhật trạng thái phiếu thi công
        const params = {
          phieutc: this.phieutc,
          trangThai: TRANG_THAI_PHIEU.HOAN_THANH
        }

        const response = await api.updateTrangThaiKetNoi(this.axios, params)

        if (response.data.error == 200) {
          this.$toast.success('Hoàn thành phiếu thành công!')
          this.navigate(this.loaicv)
        }
      } catch (error) {
        this.$toast.error(error.data?.message_detail || 'Lỗi hoàn thành phiếu thi công!')
      } finally {
        this.$root.showLoading(false)
      }
    },

    deleteOrUpdate: function(data) {
      if (this.phieuThiCongDetail.TRANGTHAI_ID !== 24 && (this.loaicv === 30 || this.loaicv === 56)) {
        // open confirm popup
        this.$confirm('Bạn có chắc chắn muốn gỡ bỏ kết nối này không?', 'Thông báo', {
          confirmButtonText: 'Đồng ý',
          cancelButtonText: 'Hủy',
          type: 'warning',
          dangerouslyUseHTMLString: true
        }).then(async (value) => {
          if (value) {
          }
        })
      }
    },

    formatDate: function(date) {
      const pad = (n) => (n < 10 ? '0' + n : n)
      return (
        date.getFullYear() +
        '-' +
        pad(date.getMonth() + 1) +
        '-' +
        pad(date.getDate()) +
        ' ' +
        pad(date.getHours()) +
        ':' +
        pad(date.getMinutes()) +
        ':' +
        pad(date.getSeconds())
      )
    },
    validateTrangThaiThiCong: function(dsKetNoi) {
      if (dsKetNoi.length > 0) {
        for (const item of dsKetNoi) {
          if (item.TRANGTHAI_THICONG_KN_ID !== 22 && item.TRANGTHAI_THICONG_KN_ID !== 24) {
            return false
          }
        }
        return true
      }
      return false
    },
    validateDanhSachThietBiTaoKetNoi: function(dsThietBi) {
      // nếu danh sách thiết bị khách hàng chưa đc kết nối > 0 thì báo lỗi không cho hoàn thành
    },
    validateTbiNotNull: function() {
      if (this.current_item.tb_nguon_id !== null) {
        return true
      }
      if (this.current_item.tb_dich_id !== null) {
        return true
      }
      return false
    },

    validateFormNguon: function() {
      // reset all flags
      for (const key in this.controls) {
        this.controls[key] = false
      }
      const requiredFieldsPort = [
        'idc',
        'loai_ketnoi',
        'thoidiem',
        'nguoi_taoketnoi',
        'loai_port',
        'port_nguon',
        'trangthai_vh',
        'hinhthuc',
        'lop',
        'loai_vat_lieu',
        'tb_nguon_id',
        'khoang_cach',
        'cancu_thuchien'
      ]
      const requiredFieldsThanhNguon = [
        'idc',
        'loai_ketnoi',
        'thoidiem',
        'nguoi_taoketnoi',
        'trangthai_vh',
        'hinhthuc',
        'lop',
        'loai_vat_lieu',
        'tb_nguon_id',
        'tb_dich_id',
        'khoang_cach',
        'cancu_thuchien',
        'o_cam_nguon',
        'thanh_nguon'
      ]
      const requiredFieldsPannel = [
        'idc',
        'loai_ketnoi',
        'thoidiem',
        'nguoi_taoketnoi',
        'trangthai_vh',
        'hinhthuc',
        'lop',
        'loai_vat_lieu',
        'tb_nguon_id',
        'tb_dich_id',
        'khoang_cach',
        'cancu_thuchien',
        'patch_pannel_nguon',
        'loai_port',
        'port_nguon'
      ]
      let requiredFields =
        this.current_item.flag_TB_nguon == 0
          ? requiredFieldsPort
          : this.loaicv === 30
          ? requiredFieldsThanhNguon
          : requiredFieldsPannel
      console.log('requiredFields: ', requiredFields)
      let isValid = true
      requiredFields.forEach((field) => {
        if (
          this.current_item[field] === null ||
          this.current_item[field] === undefined ||
          this.current_item[field] === ''
        ) {
          this.controls[field] = true
          isValid = false
        }
      })
      if (!isValid) {
        this.$toast.error('Vui lòng điền đủ các trường bắt buộc')
      }
      return isValid
    },
    validateFormDich: function() {
      // reset all flags
      for (const key in this.controls) {
        this.controls[key] = false
      }
      const requiredFieldsPort = [
        'idc',
        'loai_ketnoi',
        'thoidiem',
        'nguoi_taoketnoi',
        'loai_port_dich',
        'port_dich',
        'trangthai_vh',
        'hinhthuc',
        'lop',
        'loai_vat_lieu',
        'tb_nguon_id',
        'tb_dich_id',
        'khoang_cach',
        'cancu_thuchien'
      ]
      const requiredFieldsThanhNguon = [
        'idc',
        'loai_ketnoi',
        'thoidiem',
        'nguoi_taoketnoi',
        'trangthai_vh',
        'hinhthuc',
        'lop',
        'loai_vat_lieu',
        'tb_nguon_id',
        'tb_dich_id',
        'khoang_cach',
        'cancu_thuchien',
        'o_cam_dich',
        'thanh_nguon_dich'
      ]
      const requiredFieldsPannel = [
        'idc',
        'loai_ketnoi',
        'thoidiem',
        'nguoi_taoketnoi',
        'trangthai_vh',
        'hinhthuc',
        'lop',
        'loai_vat_lieu',
        'tb_nguon_id',
        'tb_dich_id',
        'khoang_cach',
        'cancu_thuchien',
        'tb_dich_con',
        'loai_port_dich',
        'port_dich'
      ]
      let requiredFields =
        this.current_item.flag_TB_dich == 0
          ? requiredFieldsPort
          : this.loaicv === 30 || this.loaicv === 65 || this.loaicv === 66
          ? requiredFieldsThanhNguon
          : requiredFieldsPannel
      console.log('requiredFields 2: ', requiredFields)
      let isValid = true
      requiredFields.forEach((field) => {
        if (
          this.current_item[field] === null ||
          this.current_item[field] === undefined ||
          this.current_item[field] === ''
        ) {
          this.controls[field] = true
          isValid = false
        }
        console.log('isValid: ', isValid)
      })
      if (!isValid) {
        this.$toast.error('Vui lòng điền đủ các trường bắt buộc')
      }
      return isValid
    },

    mapToTenLoaiKN: function(field, data, column) {
      if (field === 'LOAIKN_ID') {
        const item = Object.values(LOAI_KETNOI).find((x) => x.id === data.LOAIKN_ID)
        return item ? item.text : ''
      }
      return data[field]
    },
    mapToTrangThaiVL: function(field, data, column) {
      if (field === 'TRANGTHAI_VL_ID') {
        const item = trang_thai_kn_vl_options.find((x) => x.id === data.TRANGTHAI_VL_ID)
        return item ? item.text : ''
      }
      return data[field]
    },
    mapToTrangThaiVH: function(field, data, column) {
      if (field === 'TRANGTHAI_VH_ID') {
        const item = trang_thai_kn_vh_options.find((x) => x.id === data.TRANGTHAI_VH_ID)
        return item ? item.text : ''
      }
      return data[field]
    },
    getColumnTemplateChucNang(parent) {
      return function () {
        return {
          template: {
            template: `<div>
                        <button type="button" class="btn btn-primary" @click="parent.deleteOrUpdate">
                          <i class="fa fa-trash" aria-hidden="true"></i>
                        </button>
                      </div>`,
            data() {
              return {
                parent: parent,
                data: this.$parent?.data || {}
              }
            }
          }
        }
      }
    },
    getNameButtonThiCong() {
      switch (this.loaicv) {
        case 65:
        case 68:
          this.labelButtonThiCong = 'Tháo gỡ kết nối'
          break
        case 66:
        case 67:
          this.labelButtonThiCong = 'Thay đổi thông tin kết nối'
          break
      }
    },
    fillDataForm: function(data) {
      this.current_item.id = data.KETNOI_ID
      this.current_item.hinhthuc = data.HINHTHUCKN_ID
      this.current_item.lop = data.LOPKN_ID
      this.current_item.khoang_cach = data.KHOANGCACH_KN
      this.current_item.thoidiem = data.NGAY_TAO
      this.current_item.loai_vat_lieu = data.LOAIVATLIEUKN_ID
      this.current_item.trangthai_vl = data.TRANGTHAI_VL_ID
      this.current_item.trangthai_vh = data.TRANGTHAI_VH_ID
      this.current_item.cancu_thuchien = data.CANCU_TH
      this.current_item.tb_dich_phutro = data.TBI_PHUTRO_DICH
      this.current_item.idc = data.IDC_ID
      this.current_item.tb_nguon_ten = data.TB_NGUON_TEN
      this.current_item.tb_nguon_id = data.TB_NGUON_ID
      this.current_item.tb_nguon_con = data.TB_NGUON_CON_ID
      this.current_item.loai_port = data.LOAICONG_NGUON_ID
      this.current_item.port_nguon = data.PORT_NGUON_ID
      this.current_item.tb_nguon_phutro = data.TBI_PHUTRO_NGUON
      this.current_item.tb_dich_id = data.TB_DICH_ID
      this.current_item.tb_dich_ten = data.TB_DICH_TEN
      this.current_item.tb_dich_con = data.TB_DICH_CON_ID
      this.current_item.loai_port_dich = data.LOAICONG_DICH_ID
      this.current_item.port_dich = data.PORT_DICH_ID
      this.current_item.port_dich_ten = data.PORT_DICH_TEN
      this.current_item.port_nguon_ten = data.PORT_NGUON_TEN
      this.current_item.trangthai_phieuYC = this.phieuThiCongDetail.TRANGTHAI_ID
      this.current_item.flag_TB_nguon = data.FLAG_TB_NGUON || 0
      this.current_item.flag_TB_dich = data.FLAG_TB_DICH || 0
      this.current_item.thanh_nguon = data.THANHNGUON_NGUON_ID
      this.current_item.thanh_nguon_ten = data.THANHNGUON_NGUON_TEN
      this.current_item.thanh_nguon_dich = data.THANHNGUON_DICH_ID
      this.current_item.thanh_nguon_dich_ten = data.THANHNGUON_DICH_TEN
      // this.current_item.loai_o_cam_nguon = data.LOAIOCAM_NGUON_ID
      // this.current_item.loai_o_cam_dich = data.LOAIOCAM_DICH_ID
      this.current_item.o_cam_nguon = data.OCAM_NGUON_ID
      this.current_item.o_cam_dich = data.OCAM_DICH_ID
      console.log('this.current_item', this.current_item, this.loaicv)
    },
    onRowSelected: async function(row) {
      console.log('onRowSelected', row)
      this.idKetNoi = row.KETNOI_ID

      let rowIndex = this.ds_ketnoi.findIndex((item) => item.KETNOI_ID === this.idKetNoi)
      if (rowIndex == -1) {
        rowIndex = 0
      }
      this.idThicongkn = this.ds_ketnoi[rowIndex].THICONGKN_ID
      await this.onLoadChiTietKetNoi()
      await this.onLoadDanhMucForm()
    },

    //-------------------------------------------------
    onLoadDanhMucWithParams: async function(ten, thamSo1, thamSo2) {
      const response = await api.getDanhMuc(this.axios, { loaiDanhMuc: ten, thamSo1: thamSo1, thamSo2: thamSo2 })
      if (response && response.data && response.data.error == 200 && response.data.data) {
        return response.data.data.map((item) => ({
          id: item.ID,
          text: item.TEN,
          ...item
        }))
      }
      return []
    },
    onLoadPhieuCV: async function() {
      try {
        this.$root.showLoading(true)
        this.phieuThiCongDetail = await api.getChiThietPhieuThiCong(this.axios, this.phieutc)
        const response = await api.getPhieuCV(this.axios, this.$route.query.phieutc)
        if (response && response.data && response.data.error == 200 && response.data.data) {
          const data = response.data.data[0]
          this.loaicv = data.LOAICV_ID
          this.phieucv.ma_cv = data.MA_CV
          // this.idKetNoi = data.KETNOI_ID
          this.phieucv.donvi = {
            id: data.DONVI_ID,
            text: data.DONVI_TEN
          }
          this.current_item.trangthai_phieuYC = this.phieuThiCongDetail.TRANGTHAI_ID
          this.phieucv.id_ketnoi = data.KETNOI_ID
          this.phieucv.loai_cv = data.LOAI_CV_TEN
          this.phieucv.nguoi_thuchien = data.NV_TH_TEN
          this.phieucv.hangmuc_trienkhai = data.TEN_CV
          this.phieucv.ngay_tao = data.NGAY_TAO
          this.phieucv.cancuphieuyc = data.CANCU_YC
          this.phieucv.trangthai = data.TRANGTHAI_TEN
          this.phieucv.ma_cv_cha = data.PHIEUTC_CHA_ID
          this.phieucv.thoi_diem_ht = data.NGAY_HT
          this.phieucv.khach_hang = data.TEN_KH
          this.phieucv.ma_tb = data.MA_TB
          this.current_item.loai_ketnoi = data.LOAI_KN_ID
          this.current_item.nguoi_taoketnoi = this.$root.token.getNhanVienID()
          this.current_item.idc = this.phieuThiCongDetail.IDC_ID // đổ sẵn idc từ phiếu thi công
          this.isKhachHang = data.MA_TB !== null && data.MA_TB !== '' ? true : false
          // if (this.idKetNoi > 0) {
          //   await this.onLoadChiTietKetNoi()
          // }
          // Set label button sau khi có loaicv
          this.getNameButtonThiCong()
        }
      } catch (error) {
        this.$toast.error(error?.data?.message_detail || 'Lỗi lấy thông tin phiếu thi công!')
      } finally {
        this.$root.showLoading(false)
      }
    },
    onLoadDanhSachKetNoi: async function() {
      try {
        this.$root.showLoading(true)
        let response = null
        // lấy danh sách kết nối khách hàng từ phiếu thi công
        if (this.isKhachHang) {
          response = await api.getDSKetNoiKenhBan(this.axios, {
            idPhieuTc: this.phieutc,
            maTb: this.phieucv.ma_tb,
            trangthai_phieuYC: this.phieuThiCongDetail.TRANGTHAI_ID,
            loaicv: this.loaicv
          })
        }
        // lấy danh sách kết nối người tạo phiếu đã chọn
        else {
          let params = {
            idLoaiCv: this.loaicv,
            isKhachHang: this.isKhachHang ? 1 : 0,
            idPhieuTc: this.phieutc
          }
          response = await api.seachDSKetNoi(this.axios, params).then((res) => res.data)
        }

        if (response.error_code == 'BSS-00000000') {
          this.ds_ketnoi = response.data
          this.ds_ketnoi = this.ds_ketnoi.map((item, index) => ({
            ...item,
            F_KETNOI_ID:
              this.loaicv == 30 || this.loaicv == 65 || this.loaicv == 66
                ? this.isKhachHang
                  ? `KND_KH_${item.KETNOI_ID}`
                  : `KND_IDC_${item.KETNOI_ID}`
                : this.isKhachHang
                ? `KNM_KH_${item.KETNOI_ID}`
                : `KNM_IDC_${item.KETNOI_ID}` // thêm tiền tố để phân biệt kết nối điện và mạng của IDC và khách hàng
          }))

          if (this.ds_ketnoi.length > 0) {
            // nếu không tìm thấy thì lấy dòng đầu tiên trong danh sách
            let rowIndex = this.ds_ketnoi.findIndex((item) => item.KETNOI_ID === this.idKetNoi)
            if (rowIndex == -1) {
              rowIndex = 0
            }

            if (rowIndex >= 0) {
              this.idThicongkn = this.ds_ketnoi[rowIndex].THICONGKN_ID
              this.$refs.grdItems.selectRow(rowIndex) // auto select đúng dòng có ID cần
            }
          }
        } else {
          this.$toast.error(response?.message_detail || 'Lỗi lấy danh sách kết nối!')
        }
      } catch (error) {
        this.$toast.error(error?.message_detail || 'Lỗi lấy danh sách kết nối!')
      } finally {
        this.$root.showLoading(false)
      }
    },
    onLoadDanhMucForm: async function() {
      try {
        this.$root.showLoading(true)
        this.listIDC = await this.onLoadDanhMucWithParams(LOAI_DANH_MUC.IDC_BY_DONVI, this.$root.token.getDonViID())
        this.listLopKetNoi = await this.onLoadDanhMucWithParams(LOAI_DANH_MUC.LOP_KET_NOI)
        this.listLoaiVatLieuKetNoi = await this.onLoadDanhMucWithParams(LOAI_DANH_MUC.LOAI_VAT_LIEU_KN)
        this.listHinhThucKetNoi = await this.onLoadDanhMucWithParams(
          LOAI_DANH_MUC.HINH_THUC_KN,
          this.current_item.loai_ketnoi
        )

        this.current_item.loai_port = this.loaicv === 30 || this.loaicv === 65 || this.loaicv === 66 ? 2 : 1
        this.current_item.loai_port_dich = this.loaicv === 30 || this.loaicv === 65 || this.loaicv === 66 ? 2 : 1

        if (this.current_item.tb_nguon_id) {
          if (this.current_item.flag_TB_nguon === 1) {
            if (this.loaicv === 30 || this.loaicv === 65 || this.loaicv === 66) {
              this.getThanhNguon(1)
            } else if (this.loaicv === 56 || this.loaicv === 67 || this.loaicv === 68) {
              this.getPatchPanel(1)
            }
          }
          // else {
          this.onTbiNguonChange()
          // }
        }
        if (this.current_item.tb_dich_id) {
          if (this.current_item.flag_TB_dich === 1) {
            if (this.loaicv === 30 || this.loaicv === 65 || this.loaicv === 66) {
              this.getThanhNguon(2)
            } else if (this.loaicv === 56 || this.loaicv === 67 || this.loaicv === 68) {
              this.getPatchPanel(2)
            }
          }
          // else {
          this.onTbiDichChange()
          // }
        }

        this.listLoaiOCam = [
          { id: 1, text: 'Khác' },
          { id: 2, text: 'C13' },
          { id: 3, text: 'C19' }
        ]
        this.current_item.loai_o_cam_nguon = this.current_item.loai_o_cam_nguon || 2
        this.current_item.loai_o_cam_dich = this.current_item.loai_o_cam_dich || 2
      } catch (error) {
        this.$toast.error(error?.data?.message_detail || 'Lỗi lấy danh mục form!')
      } finally {
        this.$root.showLoading(false)
      }
    },
    onIDCChange: async function() {
      if (this.current_item.idc) {
        this.current_item.tb_nguon_id = null
        this.current_item.tb_nguon_ten = null
        this.current_item.tb_nguon_con = null
        this.current_item.port_nguon = null
        this.current_item.tb_dich_id = null
        this.current_item.tb_dich_ten = null
        this.current_item.tb_dich_con = null
        this.current_item.port_dich = null
        // this.listTbi = await this.onLoadDanhMucWithParams(LOAI_DANH_MUC.TBI_BY_IDC_ID, this.current_item.idc)
      }
    },
    onTbiNguonChange: async function() {
      if (this.current_item.tb_nguon_id) {
        this.listTbiNguonCon = await this.onLoadDanhMucWithParams(
          LOAI_DANH_MUC.TBI_BY_IDC_ID,
          this.current_item.idc,
          this.current_item.tb_nguon_id
        )
      }
      await this.onUpdatePortNguon()
    },
    onTbiDichChange: async function() {
      if (this.current_item.tb_dich_id) {
        this.listTbiDichCon = await this.onLoadDanhMucWithParams(
          LOAI_DANH_MUC.TBI_BY_IDC_ID,
          this.current_item.idc,
          this.current_item.tb_dich_id
        )
      }
      await this.onUpdatePortDich()
    },
    onUpdatePortNguon: async function() {
      if ((this.current_item.tb_nguon_id || this.current_item.tb_nguon_con) && this.current_item.loai_port) {
        const id_thietbi = this.current_item.tb_nguon_con
          ? this.current_item.tb_nguon_con
          : this.current_item.tb_nguon_id
        this.listPortNguon = await this.onLoadDanhMucWithParams(
          LOAI_DANH_MUC.PORT_BY_TBI_AND_PHANLOAI,
          id_thietbi,
          this.current_item.loai_port
        )
        if (this.current_item.port_nguon) {
          this.listPortNguon = [
            { id: this.current_item.port_nguon, text: this.current_item.port_nguon_ten },
            ...this.listPortNguon
          ]
        }
      }
    },
    onUpdatePortDich: async function() {
      if ((this.current_item.tb_dich_id || this.current_item.tb_dich_con) && this.current_item.loai_port_dich) {
        const id_thietbi = this.current_item.tb_dich_con ? this.current_item.tb_dich_con : this.current_item.tb_dich_id
        console.log('thiet bi con id', id_thietbi)
        this.listPortDich = await this.onLoadDanhMucWithParams(
          LOAI_DANH_MUC.PORT_BY_TBI_AND_PHANLOAI,
          id_thietbi,
          this.current_item.loai_port_dich
        )
        if (this.current_item.port_dich) {
          this.listPortDich = [
            { id: this.current_item.port_dich, text: this.current_item.port_dich_ten },
            ...this.listPortDich
          ]
        }
      }
    },
    async getChiTietKetNoi(idKetNoi, phieutc) {
      const rs = await api.getChiTietKetNoi(this.axios, idKetNoi, phieutc)
      let result = []
      if (rs.data.data.length != 0) result = rs.data.data
      return result
    },
    onLoadChiTietKetNoi: async function() {
      const response = await this.getChiTietKetNoi(this.idKetNoi, this.phieutc)
      if (response.length > 0) {
        const data = response[0]
        this.idKetNoi = data.KETNOI_ID
        this.idPhieuTC = data.PHIEUTC_ID
        this.fillDataForm(data)
      }
    },
    getListKetNoi() {
      this.navigate(this.loaicv)
    },
    onCancel() {
      this.$root.showLoading(true)
      if (this.idKetNoi) {
        this.onLoadChiTietKetNoi()
      } else {
        this.current_item = {
          idc: null,
          nguoi_taoketnoi: null,
          loai_ketnoi: this.loai_ketnoi || null,
          khoang_cach: null,
          hinhthuc: null,
          thoidiem: this.formatDate(new Date()),
          loai_vat_lieu: null,
          trangthai_vl: 1,
          lop: null,
          trangthai_vh: null,
          cancu_thuchien: null,
          tb_nguon: null,
          tb_nguon_con: null,
          loai_port: null,
          port_nguon: null,
          tb_nguon_phutro: null,
          tb_dich: null,
          tb_dich_con: null,
          loai_port_dich: null,
          port_dich: null,
          tb_dich_phutro: null,
          flag_TB_nguon: 0,
          flag_TB_dich: 0,
          thanh_nguon: null,
          thanh_nguon_dich: null,
          o_cam_nguon: null,
          o_cam_dich: null
        }
      }
      // Reset trạng thái validate
      for (const key in this.controls) {
        this.controls[key] = false
      }
      this.$root.showLoading(false)
    },
    // Mở modal ds kết nối
    async onChonKetNoi() {
      try {
        this.$root.showLoading(true)
        let params = {
          // idcId: this.current_item.idc,
          idLoaiCv: this.loaicv == 65 || this.loaicv == 66 ? 30 : this.loaicv == 67 || this.loaicv == 68 ? 56 : null,
          isKhachHang: this.isKhachHang ? 1 : 0,
          phuongThuc: (this.loaicv == 65 || this.loaicv == 68) ? 'thaogo' : (this.loaicv == 66 || this.loaicv == 67) ? 'thaydoi' : null
          // trangThaiVlId: this.loaicv == 65 || this.loaicv == 68 ? 1 : null,
          // trangThaiVhId: this.loaicv == 65 || this.loaicv == 68 ? 0 : null
        }
        let response = await api.seachDSKetNoi(this.axios, params)
        let listKetNoi = []
        if (response.data.error_code === 'BSS-00000000') {
          listKetNoi = response.data.data
          listKetNoi = listKetNoi.map((item, index) => ({
            ...item,
            F_KETNOI_ID:
              this.loaicv == 30 || this.loaicv == 65 || this.loaicv == 66
                ? this.isKhachHang
                  ? `KND_KH_${item.KETNOI_ID}`
                  : `KND_IDC_${item.KETNOI_ID}`
                : this.isKhachHang
                ? `KNM_KH_${item.KETNOI_ID}`
                : `KNM_IDC_${item.KETNOI_ID}` // thêm tiền tố để phân biệt kết nối điện và mạng của IDC và khách hàng
          }))
          this.$refs.ModalChonKetNoi.show(listKetNoi)
        } else {
          this.$toast.error(response?.data?.message_detail)
        }
      } catch (error) {
        console.log(' error', error)
        this.$toast.error(error?.data?.message_detail || 'Lỗi chọn kết nối!')
      } finally {
        this.$root.showLoading(false)
      }
    },
    // Chọn kết nối => fill dữ liệu vào form
    async onKetNoiSelected(data) {
      if (data) {
        if ((this.loaicv == 66 || this.loaicv == 67) && data.TRANGTHAI_PHIEUYC_ID != 81) {
          this.isEditThayDoi = true
        }
        // this.fillDataForm(data)
        // await this.onUpdatePortNguon()
        // await this.onUpdatePortDich()
        this.ds_ketnoi = data
        this.idKetNoi = data[0].KETNOI_ID
      }
    },
    // Chọn thiêt bị => fill dữ liệu vào hệ thống nguồn/đích
    onThietBiSelected(data) {
      this.onClearNguonDich()
      if (this.current_item.idc == null) {
        this.current_item.idc = data.IDC_ID
      }
      if (this.typeHeThong === 1) {
        // chọn thiết bị nguồn
        this.current_item.flag_TB_nguon = data.FLAG_RACK
        this.current_item.tb_nguon_id = this.current_item.flag_TB_nguon === 0 ? data.ID : data.RACK_ID
        this.current_item.tb_nguon_ten = this.current_item.flag_TB_nguon === 0 ? data.TEN : data.TEN_RACK
        if (this.current_item.flag_TB_nguon === 1) {
          if (this.loaicv === 30) {
            this.getThanhNguon(this.typeHeThong)
          } else if (this.loaicv === 56) {
            this.getPatchPanel(this.typeHeThong)
          }
        } else {
          this.onTbiNguonChange()
        }
      } else {
        // chọn thiết bị đích
        this.current_item.flag_TB_dich = data.FLAG_RACK
        this.current_item.tb_dich_id = this.current_item.flag_TB_dich === 0 ? data.ID : data.RACK_ID
        this.current_item.tb_dich_ten = this.current_item.flag_TB_dich === 0 ? data.TEN : data.TEN_RACK
        // Nếu là khách hàng và chọn thiết bị đích, lưu vào danh sách đã chọn
        // if (this.isKhachHang && this.typeHeThong === 2) {
        //   const thietBiId = data.FLAG_RACK === 0 ? data.ID : data.RACK_ID
        //   if (!this.ds_thietbi_dich_da_chon.some((item) => item.id === thietBiId)) {
        //     this.ds_thietbi_dich_da_chon.push({
        //       id: thietBiId,
        //       ten: data.FLAG_RACK === 0 ? data.TEN : data.TEN_RACK,
        //       flag_rack: data.FLAG_RACK
        //       // idc_id: data.IDC_ID
        //     })
        //   }
        // }

        if (this.current_item.flag_TB_dich === 1) {
          if (this.loaicv === 30) {
            this.getThanhNguon(this.typeHeThong)
          } else if (this.loaicv === 56) {
            this.getPatchPanel(this.typeHeThong)
          }
        } else {
          this.onTbiDichChange()
        }
      }
    },
    // Mở modal chọn thiết bị
    showModalChonTB(type) {
      this.typeHeThong = type
      const isChecked = this.isKhachHang ? type : null // nếu là khách hàng thì truyền thêm type để lấy đúng ds thiết bị nguồn/đích; nếu là IDC thì ds auto lấy đủ
      this.$refs.ModalChonThietBi.show(this.current_item, isChecked, this.typeHeThong)
    },
    async onTraPhieu() {
      this.lyDoTraPhieu = ''
      this.$refs.popupTraPhieu.show()
    },
    async confirmTraPhieu() {
      try {
        if (this.lyDoTraPhieu == null || this.lyDoTraPhieu.trim() == '') {
          this.$toast.error('Vui lòng nhập lý do trả phiếu!')
          return
        }
        this.$root.showLoading(true)
        const params = {
          // idKetNoi: this.idThicongkn,
          phieutc: this.phieutc,
          trangThai: TRANG_THAI_PHIEU.TRA_PHIEU,
          lyDoTraPhieu: this.lyDoTraPhieu
        }
        const response = await api.updateTrangThaiKetNoi(this.axios, params)
        if (response.data.error == 200) {
          this.$toast.success('Trả phiếu thành công!')
          this.navigate(this.loaicv)
        }
      } catch (e) {
        this.$toast.error(e.data.message_detail || 'Lỗi trả phiếu!')
      } finally {
        this.$root.showLoading(false)
      }
    },
    onClearFormAndAddNew() {
      this.current_item.id = null
      this.current_item.idc = null
      this.current_item.khoang_cach = null
      this.current_item.hinhthuc = null
      this.current_item.loai_vat_lieu = null
      this.current_item.lop = null
      this.current_item.trangthai_vh = null
      this.current_item.cancu_thuchien = null
      this.current_item.tb_nguon_id = null
      this.current_item.tb_nguon_ten = null
      this.current_item.tb_nguon_con = null
      this.current_item.loai_port = this.loaicv === 30 || this.loaicv === 65 || this.loaicv === 66 ? 2 : 1
      this.current_item.port_nguon = null
      this.current_item.tb_nguon_phutro = null
      this.current_item.tb_dich_id = null
      this.current_item.tb_dich_ten = null
      this.current_item.tb_dich_con = null
      this.current_item.loai_port_dich = this.loaicv === 30 || this.loaicv === 65 || this.loaicv === 66 ? 2 : 1
      this.current_item.port_dich = null
      this.current_item.tb_dich_phutro = null
      this.current_item.port_nguon_ten = null
      this.current_item.port_dich_ten = null
      this.current_item.thanh_nguon = null
      this.current_item.thanh_nguon_dich = null
      this.current_item.loai_o_cam_nguon = 2
      this.current_item.loai_o_cam_dich = 2
      this.current_item.o_cam_nguon = null
      this.current_item.o_cam_dich = null
      this.current_item.patch_pannel_nguon = null
      this.current_item.patch_pannel_dich = null
      this.current_item.loai_port_rack_nguon = null
      this.current_item.loai_port_rack_dich = null
      this.current_item.port_rack_nguon = null
      this.current_item.port_rack_dich = null
      // Reset trạng thái validate
      for (const key in this.controls) {
        this.controls[key] = false
      }
      this.idThicongkn = null
    },
    async huyTraPhieu() {
      this.$refs.popupTraPhieu.hide()
    },
    onChangeTab: function(index) {
      this.tab_index = index
    },
    parseKetNoiId: function(data) {
      // bỏ đi phần tiền tố, chỉ lấy số
      return data.replace(/[^0-9]/g, '')
    },
    getThanhNguon: async function(typeHeThong) {
      if (typeHeThong === 1) {
        this.listThanhNguon = await this.getDanhMucTheoRack(
          LOAI_DANH_MUC.IDC_THANHNGUON_RACK,
          this.current_item.tb_nguon_id
        )
        // if (this.current_item.thanh_nguon) {
        //   this.listThanhNguon = [
        //     { id: this.current_item.thanh_nguon, text: this.current_item.thanh_nguon_ten },
        //     ...this.listThanhNguon
        //   ]
        // }
        if (this.current_item.o_cam_nguon) {
          await this.getOCamTheoThanhNguon(typeHeThong)
        }
      } else if (typeHeThong === 2) {
        this.listThanhNguonDich = await this.getDanhMucTheoRack(
          LOAI_DANH_MUC.IDC_THANHNGUON_RACK,
          this.current_item.tb_dich_id
        )
        if (this.current_item.o_cam_dich) {
          await this.getOCamTheoThanhNguon(typeHeThong)
        }
        // if (this.current_item.thanh_nguon_dich) {
        //   this.listThanhNguonDich = [
        //     { id: this.current_item.thanh_nguon_dich, text: this.current_item.thanh_nguon_dich_ten },
        //     ...this.listThanhNguonDich
        //   ]
        // }
      }
    },
    getOCamTheoThanhNguon: async function(typeHeThong) {
      if (typeHeThong === 1) {
        this.listOCamNguon = await this.getDanhMucTheoRack(
          LOAI_DANH_MUC.IDC_OCAM_THANHNGUON,
          this.current_item.thanh_nguon,
          this.current_item.loai_o_cam_nguon
        )
      } else {
        this.listOCamDich = await this.getDanhMucTheoRack(
          LOAI_DANH_MUC.IDC_OCAM_THANHNGUON,
          this.current_item.thanh_nguon_dich,
          this.current_item.loai_o_cam_dich
        )
      }
    },
    getPatchPanel: async function(typeHeThong) {
      if (typeHeThong === 1) {
        this.listPatchPannelNguon = await this.getDanhMucTheoRack(
          LOAI_DANH_MUC.IDC_PATCHPANNEL_RACK,
          this.current_item.tb_nguon_id
        )
      } else {
        this.listPatchPannelDich = await this.getDanhMucTheoRack(
          LOAI_DANH_MUC.IDC_PATCHPANNEL_RACK,
          this.current_item.tb_dich_id
        )
      }
    },
    onChangePatchPanel: async function(typeHeThong) {
      if (typeHeThong == 1) {
        await this.onUpdatePortNguon()
      } else if (typeHeThong == 2) {
        await this.onUpdatePortDich()
      }
    },
    onChangeLoaiOCam: async function(typeHeThong) {
      if (typeHeThong == 1) {
        this.current_item.o_cam_nguon = null
        await this.getOCamTheoThanhNguon(typeHeThong)
      } else if (typeHeThong == 2) {
        this.current_item.o_cam_dich = null
        await this.getOCamTheoThanhNguon(typeHeThong)
      }
    },
    getDanhMucTheoRack: async function(ten, thamSo1, thamSo2) {
      const response = await api.getDanhMucTheoRack(this.axios, {
        loaiDanhMuc: ten,
        thamSo1: thamSo1,
        thamSo2: thamSo2
      })
      if (response && response.data && response.data.error == 200 && response.data.data) {
        return response.data.data.map((item) => ({
          id: item.ID,
          text: item.TEN,
          ...item
        }))
      }
      return []
    },
    onClearNguonDich() {
      if (this.typeHeThong === 1) {
        // xóa hệ thống nguồn
        this.current_item.tb_nguon_con = null
        this.current_item.loai_port = this.loaicv === 30 || this.loaicv === 65 || this.loaicv === 66 ? 2 : 1
        this.current_item.port_nguon = null
        this.current_item.port_nguon_ten = null
        this.listTbiNguonCon = []
        this.listPortNguon = []
        this.listThanhNguon = []
        this.current_item.thanh_nguon = null
        this.listOCamNguon = []
        this.current_item.o_cam_nguon = null
        this.current_item.loai_o_cam_nguon = 2
        this.current_item.patch_pannel_nguon = null
        this.listPatchPannelNguon = []
        this.current_item.loai_port_rack_nguon = null
        this.current_item.port_rack_nguon = null
        this.listPortRackNguon = []
      } else if (this.typeHeThong === 2) {
        // xóa hệ thống đích
        this.current_item.tb_dich_con = null
        this.current_item.loai_port_dich = this.loaicv === 30 || this.loaicv === 65 || this.loaicv === 66 ? 2 : 1
        this.current_item.port_dich = null
        this.current_item.port_dich_ten = null
        this.listTbiDichCon = []
        this.listPortDich = []
        this.listThanhNguonDich = []
        this.current_item.thanh_nguon_dich = null
        this.listOCamDich = []
        this.current_item.o_cam_dich = null
        this.current_item.loai_o_cam_dich = 2
        this.current_item.patch_pannel_dich = null
        this.listPatchPannelDich = []
        this.current_item.loai_port_rack_dich = null
        this.current_item.port_rack_dich = null
        this.listPortRackDich = []
      }
    },
    // actionColumn(parent) {
    //   return function() {
    //     return {
    //       template: {
    //         template: `<div class="action-buttons gap-1">
    //             <button class="btn btn-sm btn-outline-primary" title="Xóa" @click="onDeleteItem()">
    //               <i class="fa fa-trash"></i>
    //             </button>
    //           </div>`,
    //         computed: {},
    //         methods: {
    //           onDeleteItem() {
    //             parent.onDeleteItem(this.data, false)
    //           }
    //         }
    //       }
    //     }
    //   }
    // },
    // onDeleteItem(data) {
    //   try {
    //     console.log('onDeleteItem', data)
    //     const index = this.ds_ketnoi.findIndex((x) => x.KETNOI_ID === data.KETNOI_ID)
    //     if (index !== -1) {
    //       this.ds_ketnoi.splice(index, 1)
    //       // this.$toast.success('Đã xóa kết nối khỏi danh sách')
    //     }
    //   } catch (error) {
    //     this.$toast.error('Có lỗi xảy ra khi xử lý công việc')
    //   }
    // }
  }
}
</script>
<style>
.info-row .key.w180 {
  width: 180px;
}

a {
  cursor: pointer;
}

a.disable-btn {
  pointer-events: none;
  cursor: default;
  color: #6c757d !important;
}

/* Show red border for invalid inputs and custom selects */
.is-invalid {
  border: 1px solid #dc3545 !important;
  border-radius: 4px;
}
</style>
