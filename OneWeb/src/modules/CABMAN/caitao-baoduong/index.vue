<template src="./index.template.html"></template>
<script>
import NoiDungTTCC from './modals/NoiDungTTCC.vue'
import DateTimeLib from '../../../utils/DateTimeLib'
import TraPhieuVT from './modals/traphieuVT/Modal_TraPhieuVT.vue'
import GiaoPhieuCC from './modals/giaoPhieuCC'
import NhanTin_TB_AnhHuong from './modals/NhanTin_AnhHuong'
import FrmThemPhanTuMang from './frmThemPhanTuMang'
import frmCTHoSoDauChuyen from './modals/CTHoSoDauChuyen/CTHoSoDauChuyen.vue'
import DauNoiThueBao from '../DauNoiThueBao/index.vue'
import ModalDoiCapHangLoat from '../quanly-hoso-dauchuyen/modal-doicap-hangloat/index.vue'
import FrmGiaoPhieuCCNV from './frmGiaoPhieuCC_NV'
import frmCapVatTu_PTM from './modals/CapVatTu_PTM/index.vue'
export default {
  components: {
    NoiDungTTCC,
    TraPhieuVT,
    GiaoPhieuCC,
    FrmThemPhanTuMang,
    NhanTin_TB_AnhHuong,
    frmCTHoSoDauChuyen,
    DauNoiThueBao,
    ModalDoiCapHangLoat,
    FrmGiaoPhieuCCNV,
    frmCapVatTu_PTM
  },
  props: {
    Tag: {
      type: String,
      default: '5'
    }
  },
  data: function () {
    return {
      dsNhanVienGiaoViec: [],
      huongGiaoID: 0,
      dsThaoTac: [],
      dsControlThaoTac: [],
      dsKetQuaXuLy: [],
      dsFormControls: {
        dtpNgayBG: {
          enabled: true,
          visible: true,
          value: null
        },
        chkNgayBG: {
          enabled: true,
          visible: true,
          checked: false
        },
        dtpNgayGV: {
          enabled: true,
          visible: true,
          value: null
        },
        chkNgayGV: {
          enabled: true,
          visible: true,
          checked: false
        },
        chkNgayDuKien: {
          enabled: true,
          visible: true,
          checked: false
        },
        dtpNgayDuKien: {
          BD: null,
          KT: null
        },
        tsbtnGiaoPhieu: {
          enabled: true,
          visible: false
        },
        tsbtnGiaoViec: {
          enabled: true,
          visible: false
        },
        tsbtnHoanCong: {
          enabled: true,
          visible: false
        },
        tsbtnTraPhieu: {
          enabled: true,
          visible: false
        },
        tsbtnThemHSCC: {
          enabled: true,
          visible: false
        },
        tsbtnThemPTM: {
          enabled: true,
          visible: false
        },
        tsbtnXoaPTM: {
          enabled: true,
          visible: false
        },
        tsbtnHoanThanhPTM: {
          enabled: true,
          visible: false
        },
        tsbtnThemPTM_V2: {
          enabled: true,
          visible: false
        },
        tsbtnSmsTBAnhHuong: {
          enabled: true,
          visible: false
        },
        tsbtnGuiZalo: {
          enabled: true,
          visible: false
        },
        tsbtnDuyet: {
          enabled: true,
          visible: false
        }
      },
      dsPhanTuMang: [],
      dsGiaoViec: [],
      dsTienTrinh: [],
      dsThueBaoAnhHuong: [],
      dsDanhSachHoSo: [],
      currentItem: {
        hsccID: 0,
        phieuCCID: 0,
        chiTietCCID: 0,
        nhanVienGVID: null,
        currentGiaoViec: null
      },
      formNoiDungData: null,
      phaiLamVatTuMoi: true,
      dataDauNoiThueBao: {},
      dsThueBaoNhapCapHangLoat: [],
      modelVatTu: {}
    }
  },
  computed: {
    gridPhanTuMangCommandColumn: function () {
      return {
        headerText: ' ',
        width: this.phaiLamVatTuMoi ? 110 : 70,
        show: true,
        freeze: true
      }
    },
    gridPhanTuMangCommands: function () {
      return [
        {
          name: 'THEM_TIENTRINH',
          title: 'Thêm mới tiến trình',
          cssClass: 'btn-primary fa fa-plus',
          width: 20
        },
        {
          name: 'CAP_VATTU',
          title: 'Cấp vật tư',
          cssClass: 'btn-primary fa fa-cubes',
          width: 20,
          visible: this.phaiLamVatTuMoi
        },
        {
          name: 'DONGBO_THUEBAO',
          title: 'Đồng bộ thuê bao ảnh hưởng',
          cssClass: 'btn-primary fa fa-phone',
          width: 20
        }
      ]
    },
    gridPhanTuMang: function () {
      return this.$refs.gridPhanTuMang
    },
    gridGiaoViec: function () {
      return this.$refs.gridGiaoViec
    },
    gridTienTrinh: function () {
      return this.$refs.gridTienTrinh
    },
    gridThueBaoAnhHuong: function () {
      return this.$refs.gridThueBaoAnhHuong
    },
    gridDanhSachHoSo: function () {
      return this.$refs.gridDanhSachHoSo
    },
    ttccID: function () {
      let result = 0
      if (!(this.Tag == null || this.Tag == '')) {
        let arr = this.Tag.split('+')
        if (arr.length > 0) {
          result = arr[0]
        }
      }
      return result
    },
    chkNgayDuKienState: function () {
      let rs = this.dsFormControls.chkNgayDuKien.checked
      if (
        rs === true &&
        (!this.dsFormControls.chkNgayDuKien.enabled ||
          !this.dsFormControls.chkNgayDuKien.visible)
      ) {
        rs = false
      }
      // rs = true;
      return rs
    },
    chkNgayGVState: function () {
      let rs = this.dsFormControls.chkNgayGV.checked
      if (
        rs === true &&
        (!this.dsFormControls.chkNgayGV.enabled ||
          !this.dsFormControls.chkNgayGV.visible)
      ) {
        rs = false
      }
      return rs
    },
    chkNgayBGState: function () {
      let rs = this.dsFormControls.chkNgayBG.checked
      if (
        rs === true &&
        (!this.dsFormControls.chkNgayBG.enabled ||
          !this.dsFormControls.chkNgayBG.visible)
      ) {
        rs = false
      }
      return rs
    }
  },
  created: async function (args) {
    this.$root.showLoading(true)
    try {
      await this.form_Created(args)
    } catch (error) {
    } finally {
      this.$root.showLoading(false)
    }
  },
  methods: {
    getColumnTTTemplate: function () {
      return function () {
        return {
          template: {
            template: `<ejs-textbox v-model="data.ND_TONTAI" @change="updateData"></ejs-textbox>`,
            computed: {
              parent: function () {
                return this.$parent.$parent.$parent
              }
            },
            methods: {
              updateData: function (args) {
                this.parent.capNhatKQXL(this.data, 'ND_TONTAI')
              }
            }
          }
        }
      }
    },
    getColumnDXTemplate: function () {
      return function () {
        return {
          template: {
            template: `<ejs-textbox v-model="data.ND_DEXUAT" @change="updateData"></ejs-textbox>`,
            computed: {
              parent: function () {
                return this.$parent.$parent.$parent
              }
            },
            methods: {
              updateData: function (args) {
                this.parent.capNhatKQXL(this.data, 'ND_DEXUAT')
              }
            }
          }
        }
      }
    },
    getColumnKQXLTemplate: function () {
      return function () {
        return {
          template: {
            template: `<ejs-dropdownlist :allowFiltering="true" :dataSource="parent.dsKetQuaXuLy" :fields="{ text: 'KETQUA_XL', value: 'KQXL_ID' }" @change="updateData" v-model="data.KQXL_ID" placeholder="[Chưa chọn giá trị]"></ejs-dropdownlist>`,
            computed: {
              parent: function () {
                return this.$parent.$parent.$parent
              }
            },
            methods: {
              updateData: function (args) {
                this.parent.capNhatKQXL(this.data, 'KQXL_ID')
              }
            }
          }
        }
      }
    },
    capNhatKQXL: function (itemData, field) {
      let item = this.dsGiaoViec.find((x) => x.ID == itemData.ID)
      if (item != null) {
        item[field] = itemData[field]
      }
    },
    layDanhSachNhanVienGiaoViec: async function () {
      let rs = await this.$root.context.post(
        '/web-cabman/CaiTaoBaoDuong/layDanhSachNhanVienGiaoViec'
      )
      return rs.data
    },
    layHuongGiao: async function () {
      let v = 0
      let rs = await this.$root.context.post(
        '/web-cabman/CaiTaoBaoDuong/layHuongGiao',
        {
          id: this.ttccID
        }
      )
      if (rs.data != null) {
        v = rs.data.HUONGGIAO_ID
      }
      return v
    },
    layDsThaoTac: async function () {
      let rs = await this.$root.context.post(
        '/web-cabman/CaiTaoBaoDuong/layDsThaoTac',
        {
          id: this.huongGiaoID
        }
      )
      return rs.data
    },
    layDsControlHuongGiao: async function () {
      let rs = await this.$root.context.post(
        '/web-cabman/CaiTaoBaoDuong/layDsControlHuongGiao',
        {
          id: this.huongGiaoID
        }
      )
      return rs.data
    },
    layDsControlThaoTac: async function (thaoTacID) {
      let rs = await this.$root.context.post(
        '/web-cabman/CaiTaoBaoDuong/layDsControlThaoTac',
        {
          id: thaoTacID
        }
      )
      return rs.data
    },
    layDsHoSoCTBD: async function () {
      let rs = await this.$root.context.post(
        '/web-cabman/CaiTaoBaoDuong/layDsHoSoCTBD',
        {
          ttccID: this.ttccID,
          nhanVienID: this.$root.token.getNhanVienID(),
          maHSCC: '0'
        }
      )
      return rs.data
    },
    layDsPhanTuMangCTBD: async function () {
      let rs = await this.$root.context.post(
        '/web-cabman/CaiTaoBaoDuong/layDsPhanTuMangCTBD',
        {
          id: this.currentItem.hsccID
        }
      )
      return rs.data
    },
    getDsPhanTuMangCTBD: async function () {
      this.dsPhanTuMang = await this.layDsPhanTuMangCTBD()
      this.dsGiaoViec = []
      this.dsTienTrinh = []
      this.dsThueBaoAnhHuong = []
      this.currentItem.nhanVienGVID = null
      this.currentItem.currentGiaoViec = null
    },
    dongBoThueBaoBDPTM: async function (chiTietCCID) {
      let msg = ''
      let rs = await this.$root.context.post(
        '/web-cabman/CaiTaoBaoDuong/dongBoThueBaoBDPTM',
        {
          id: chiTietCCID
        }
      )
      if (rs.data == null || rs.data == undefined) msg = rs.message_detail
      return msg
    },
    capNhatGhiChuXuLyNVCTBD: async function (
      chiTietCCID,
      nhanVienTHID,
      nhiemVuID,
      ghiChu
    ) {
      let msg = ''
      let rs = await this.$root.context.post(
        '/web-cabman/CaiTaoBaoDuong/capNhatGhiChuXuLyNVCTBD',
        {
          chiTietCCID: chiTietCCID,
          nhanVienTHID: nhanVienTHID,
          nhiemVuID: nhiemVuID,
          ghiChu: ghiChu
        }
      )
      if (rs.data == null || rs.data == undefined) msg = rs.message_detail
      return msg
    },
    capNhatKQXLgiaoViecNVCTBD: async function (
      chiTietCCID,
      nhanVienTHID,
      nhiemVuID,
      ketQuaID,
      noiDungDeXuat,
      noiDungTonTai
    ) {
      let msg = ''
      let rs = await this.$root.context.post(
        '/web-cabman/CaiTaoBaoDuong/capNhatKQXLgiaoViecNVCTBD',
        {
          chiTietCCID: chiTietCCID,
          nhanVienTHID: nhanVienTHID,
          nhiemVuID: nhiemVuID,
          ketQuaID: ketQuaID,
          noiDungDeXuat: noiDungDeXuat,
          noiDungTonTai: noiDungTonTai
        }
      )
      if (rs.data == null || rs.data == undefined) msg = rs.message_detail
      return msg
    },
    hoanCongCTBD: async function (phieuID, hsccID, noiDung) {
      let msg = ''
      let rs = await this.$root.context.post(
        '/web-cabman/CaiTaoBaoDuong/hoanCongCTBD',
        {
          phieuCCID: phieuID,
          hsccID: hsccID,
          noiDung: noiDung
        }
      )
      if (rs.data == null || rs.data == undefined) msg = rs.message_detail
      else msg = rs.data.result
      return msg
    },
    giaoPhieuTDDuyetTDPTM: async function (phieuID, hsccID) {
      let msg = ''
      let rs = await this.$root.context.post(
        '/web-cabman/CaiTaoBaoDuong/giaoPhieuTDDuyetTDPTM',
        {
          phieuCCID: phieuID,
          hsccID: hsccID
        }
      )
      if (rs.data == null || rs.data == undefined) msg = rs.message_detail
      else msg = rs.data.result
      return msg
    },
    xuLyThongBaoSms: async function (chiTietCCID, nhanVienTHID, nhiemVuID) {
      let rs = await this.$root.context.post(
        '/web-cabman/CaiTaoBaoDuong/xuLyThongBaoSms',
        {
          chiTietCCID: chiTietCCID,
          nhanVienTHID: nhanVienTHID,
          nhiemVuID: nhiemVuID
        }
      )
      return rs.data
    },
    layThongTinSMS: async function (chiTietCCID, nhanVienTHID, nhiemVuID) {
      let rs = await this.$root.context.post(
        '/web-cabman/CaiTaoBaoDuong/layThongTinSMS',
        {
          chiTietCCID: chiTietCCID,
          nhanVienTHID: nhanVienTHID,
          nhiemVuID: nhiemVuID
        }
      )
      return rs.data
    },
    layThongTinSMSHoanThanh: async function () {
      let rs = await this.$root.context.post(
        '/web-cabman/CaiTaoBaoDuong/layThongTinSMS',
        {
          id: this.currentItem.hsccID
        }
      )
      return rs.data
    },
    // guiSms: async function (so_dt, noidung) {
    //   if (!(so_dt == null || so_dt == undefined || so_dt == '')) {
    //     let rs = await this.$root.context.post(
    //       '/web-cabman/traphieukhieunai/off-send-sms',
    //       {
    //         msisdn: so_dt,
    //         content: noidung,
    //         mt_type: 'reply'
    //       }
    //     )
    //     return rs.data
    //   } else return null
    // },
    xoaTienTrinhCatChuyen: async function (
      tienTrinhCCID,
      chiTietCCID,
      nhanVienID
    ) {
      let rs = await this.$root.context.post(
        '/web-cabman/CaiTaoBaoDuong/xoaTienTrinhCatChuyen',
        {
          tienTrinhCCID: tienTrinhCCID,
          chiTietCCID: chiTietCCID,
          nhanVienID: nhanVienID
        }
      )
      return rs.data.result
    },
    layDsThueBaoAnhHuongCTBD: async function (chiTietCCID) {
      let rs = await this.$root.context.post(
        '/web-cabman/CaiTaoBaoDuong/layDsThueBaoAnhHuongCTBD',
        {
          id: chiTietCCID
        }
      )
      return rs.data
    },
    getDsThueBaoAnhHuongCTBD: async function (chiTietCCID) {
      this.dsThueBaoAnhHuong = await this.layDsThueBaoAnhHuongCTBD(chiTietCCID)
    },
    layDsTienTrinhCTBD: async function (chiTietCCID) {
      let rs = await this.$root.context.post(
        '/web-cabman/CaiTaoBaoDuong/layDsTienTrinhCTBD',
        {
          id: chiTietCCID
        }
      )
      return rs.data
    },
    getDsTienTrinhCTBD: async function (chiTietCCID) {
      this.dsTienTrinh = await this.layDsTienTrinhCTBD(chiTietCCID)
    },
    layDsGiaoViecCTBD: async function (chiTietCCID) {
      let rs = await this.$root.context.post(
        '/web-cabman/CaiTaoBaoDuong/layDsGiaoViecCTBD',
        {
          chiTietCCID: chiTietCCID,
          phieuCCID: this.currentItem.phieuCCID
        }
      )
      return rs.data
    },
    getDsGiaoViecCTBD: async function (chiTietCCID) {
      this.dsGiaoViec = await this.layDsGiaoViecCTBD(chiTietCCID)
      for (let i = 0; i < this.dsGiaoViec.length; i++) {
        this.dsGiaoViec[i].ID = i + 1
      }
    },
    layDsKetQuaXuLy: async function () {
      let rs = await this.$root.context.post(
        '/web-cabman/CaiTaoBaoDuong/layDsKetQuaXuLy'
      )
      return rs.data
    },
    getDsKetQuaXuLy: async function () {
      this.dsKetQuaXuLy = await this.layDsKetQuaXuLy()
    },
    getDsThaoTac: async function () {
      this.dsThaoTac = []
      let v = await this.layDsThaoTac()
      if (!(v == null || v == undefined)) {
        this.dsThaoTac = v
      }
    },
    getDsControlThaoTac: async function () {
      this.dsControlThaoTac = []
      let v = await this.layDsControlHuongGiao()
      if (!(v == null || v == undefined)) {
        v.forEach((e) => {
          this.dsControlThaoTac.push({
            thaoTacID: e.THAOTAC_ID,
            controlName: e.CONTROL_NAME,
            enabled: e.ENABLE == 1
          })
        })
      }
      this.dsFormControls.tsbtnGiaoPhieu.visible =
        this.getControlVisibleValue('tsbtnGiaoPhieu')
      this.dsFormControls.tsbtnGiaoViec.visible =
        this.getControlVisibleValue('tsbtnGiaoViec')
      this.dsFormControls.tsbtnHoanCong.visible =
        this.getControlVisibleValue('tsbtnHoanCong')
      this.dsFormControls.tsbtnTraPhieu.visible =
        this.getControlVisibleValue('tsbtnTraPhieu')
      this.dsFormControls.tsbtnThemHSCC.visible =
        this.getControlVisibleValue('tsbtnThemHSCC')
      this.dsFormControls.tsbtnThemPTM.visible =
        this.getControlVisibleValue('tsbtnThemPTM')
      this.dsFormControls.tsbtnXoaPTM.visible =
        this.getControlVisibleValue('tsbtnXoaPTM')
      this.dsFormControls.tsbtnHoanThanhPTM.visible =
        this.getControlVisibleValue('tsbtnHoanThanhPTM')
      this.dsFormControls.tsbtnThemPTM_V2.visible =
        this.getControlVisibleValue('tsbtnThemPTM_V2')
      this.dsFormControls.tsbtnSmsTBAnhHuong.visible =
        this.getControlVisibleValue('tsbtnSmsTBAnhHuong')
      this.dsFormControls.tsbtnGuiZalo.visible =
        this.getControlVisibleValue('tsbtnGuiZalo')
      this.dsFormControls.tsbtnDuyet.visible =
        this.getControlVisibleValue('tsbtnDuyet')

      this.dsFormControls.tsbtnGiaoPhieu.enabled =
        this.checkPhaiLam('GIAOPHIEU')
      this.dsFormControls.tsbtnGiaoViec.enabled = this.checkPhaiLam('GIAOVIEC')
      this.dsFormControls.chkNgayBG.enabled =
        this.dsFormControls.tsbtnGiaoViec.enabled
      this.dsFormControls.dtpNgayBG.enabled =
        this.dsFormControls.tsbtnGiaoViec.enabled
      this.dsFormControls.dtpNgayBG.value = new Date()
      this.dsFormControls.chkNgayGV.enabled =
        this.dsFormControls.tsbtnGiaoViec.enabled
      this.dsFormControls.dtpNgayGV.enabled =
        this.dsFormControls.tsbtnGiaoViec.enabled
      this.dsFormControls.dtpNgayGV.value = new Date()
      this.dsFormControls.chkNgayBG.checked = true
      this.dsFormControls.chkNgayGV.checked = true

      this.dsFormControls.tsbtnHoanCong.enabled = this.checkPhaiLam('HOANCONG')
      this.dsFormControls.tsbtnTraPhieu.enabled =
        this.checkPhaiLam('TRAPHIEU_BH')
      this.dsFormControls.tsbtnThemHSCC.enabled = this.checkPhaiLam('INS_HSCC')
      this.dsFormControls.tsbtnThemPTM.enabled = this.checkPhaiLam('INS_PTM')
      this.dsFormControls.tsbtnXoaPTM.enabled = this.checkPhaiLam('DELETE_PTM')
      this.dsFormControls.tsbtnHoanThanhPTM.visible =
        this.checkPhaiLam('HOANTHANH_CTCC')
      this.dsFormControls.tsbtnHoanThanhPTM.enabled =
        this.dsFormControls.tsbtnHoanThanhPTM.visible
      this.dsFormControls.chkNgayDuKien.enabled =
        this.checkPhaiLam('CN_NGAYXL_CTBD')
      this.dsFormControls.tsbtnThemPTM_V2.enabled =
        this.checkPhaiLam('INS_PTM_V2')
      this.dsFormControls.tsbtnSmsTBAnhHuong.visible =
        this.checkPhaiLam('SMS_TB_ANHHUONG')

      // Note lai de chay chuc nang cap vat tu
      // Chay xong nen mo ra lai
      this.phaiLamVatTuMoi = this.checkPhaiLam('VAT_TU_MOI')
    },
    getControlVisibleValue: function (name) {
      let rs = false
      let c = this.dsControlThaoTac.find(
        (x) => x.controlName.toLowerCase().trim() == name.toLowerCase().trim()
      )
      if (!(c == null || c == undefined)) {
        let t = this.dsThaoTac.find((x) => x.THAOTAC_ID == c.thaoTacID)
        if (!(t == null || t == undefined)) {
          rs = t.ENABLE == 1
        }
      }
      return rs
    },
    checkPhaiLam: function (code) {
      if (!(this.dsThaoTac == null || this.dsThaoTac == undefined)) {
        return (
          this.dsThaoTac.filter((x) => x.ENABLE == 1 && x.CODE == code).length >
          0
        )
      } else {
        return false
      }
    },
    createPTMItem: function (ptm_id, hscc_id, chitietcc_id) {
      return (
        '<item ptm_id="' +
        ptm_id +
        '" hscc_id="' +
        hscc_id +
        '" chitietcc_id="' +
        chitietcc_id +
        '" trangthai_id="' +
        1 +
        '" />'
      )
    },
    getDsPTMSelectedXml: function () {
      let result = '<root>'
      let items = this.gridPhanTuMang.getSelectedRecords()
      if (!(items == null || items == undefined || items.length == 0)) {
        items.forEach((item) => {
          result += this.createPTMItem(
            item.PTM_ID,
            item.HSCC_ID,
            item.CHITIETCC_ID
          )
        })
      }
      result += '</root>'
      return result
    },
    layThongTinLoaiTBI: async function (loaiTBIID) {
      let rs = await this.$root.context.post(
        '/web-cabman/CaiTaoBaoDuong/layThongTinLoaiTBI',
        { id: loaiTBIID }
      )
      return rs.data
    },
    laySoLuongDaThemHSPTM: async function (hsptmID) {
      let rs = await this.$root.context.post(
        '/web-cabman/CaiTaoBaoDuong/laySoLuongDaThemHSPTM',
        { id: hsptmID }
      )
      return rs.data
    },
    layDsHoSoPTM: async function () {
      let rs = await this.$root.context.post(
        '/web-cabman/CaiTaoBaoDuong/layDsHoSoPTM',
        { id: this.currentItem.hsccID }
      )
      return rs.data
    },
    capNhatNgayThucHienTrenPhieu: async function () {
      let msg = ''
      let rs = await this.$root.context.post(
        '/web-cabman/CaiTaoBaoDuong/capNhatNgayThucHienTrenPhieu',
        {
          phieuCCID: this.currentItem.phieuCCID,
          ngayTH: DateTimeLib.toDateDisplayWithFormat(
            this.dsFormControls.dtpNgayBG.value,
            'yyyy-MM-DD HH:mm:ss'
          )
        }
      )
      if (rs.data == null || rs.data == undefined) msg = rs.message_detail
      return msg
    },
    capNhatNgayXuLyPTMCTBD: async function (ngayBD, ngayKT) {
      let msg = ''
      let rs = await this.$root.context.post(
        '/web-cabman/CaiTaoBaoDuong/capNhatNgayXuLyPTMCTBD',
        {
          dsCTPTM: this.getDsPTMSelectedXml(),
          ngayBD: DateTimeLib.toDateDisplayWithFormat(
            ngayBD,
            'yyyy-MM-DD HH:mm:ss'
          ),
          ngayKT: DateTimeLib.toDateDisplayWithFormat(
            ngayKT,
            'yyyy-MM-DD HH:mm:ss'
          )
        }
      )
      if (rs.data == null || rs.data == undefined) msg = rs.message_detail
      return msg
    },
    capNhatTrangThaiXuLyPTM: async function (noiDung) {
      let msg = ''
      let rs = await this.$root.context.post(
        '/web-cabman/CaiTaoBaoDuong/capNhatTrangThaiXuLyPTM',
        {
          dsCTPTM: this.getDsPTMSelectedXml(),
          phieuCCID: this.currentItem.phieuCCID,
          noiDung: noiDung
        }
      )
      if (rs.data == null || rs.data == undefined) msg = rs.message_detail
      return msg
    },
    xoaDsChiTietCCPTM: async function () {
      let msg = ''
      let rs = await this.$root.context.post(
        '/web-cabman/CaiTaoBaoDuong/xoaDsChiTietCCPTM',
        {
          hsccID: this.currentItem.hsccID,
          dsCTPTM: this.getDsPTMSelectedXml()
        }
      )
      if (rs.data == null || rs.data == undefined) msg = rs.message_detail
      else {
        msg = rs.data.result
      }
      return msg
    },
    laySoDTThueBao: async function (thueBaoID) {
      let msg = ''
      let rs = await this.$root.context.post(
        '/web-cabman/CaiTaoBaoDuong/laySoDTThueBao',
        {
          id: thueBaoID
        }
      )
      if (!(rs.data == null || rs.data == undefined)) {
        msg = rs.data.result
      }
      return msg
    },
    duyetCTBDPTM: async function () {
      let msg = ''
      let rs = await this.$root.context.post(
        '/web-cabman/CaiTaoBaoDuong/duyetCTBDPTM',
        {
          phieuID: this.currentItem.phieuCCID,
          hsccID: this.currentItem.hsccID
        }
      )
      if (!(rs.data == null || rs.data == undefined)) {
        msg = rs.data.result
      }
      return msg
    },
    xuLyThongBaoTBAnhHuong: async function (thueBaoID, chiTietCCID) {
      let rs = await this.$root.context.post(
        '/web-cabman/CaiTaoBaoDuong/xuLyThongBaoTBAnhHuong',
        {
          thueBaoID: thueBaoID,
          chiTietCCID: chiTietCCID
        }
      )
      if (!(rs.data == null || rs.data == undefined)) {
        return rs.data;
      }
      return null
    },
    layNoiDungSMSTBAnhHuong: async function (thueBaoID, chiTietCCID) {
      let msg = ''
      let rs = await this.$root.context.post(
        '/web-cabman/CaiTaoBaoDuong/layNoiDungSMSTBAnhHuong',
        {
          thueBaoID: thueBaoID,
          chiTietCCID: chiTietCCID
        }
      )
      if (!(rs.data == null || rs.data == undefined)) {
        msg = rs.data.result
      }
      return msg
    },
    hoanThienCatChuyen: async function (dkccID) {
      let result = false
      let rs = await this.$root.context.post(
        '/web-cabman/CaiTaoBaoDuong/hoanThienCatChuyen',
        {
          id: dkccID
        }
      )
      if (!(rs.data == null || rs.data == undefined)) {
        result = rs.data.result
      }
      return result
    },
    kiemTraPhanTuMang: async function () {
      let result = true
      if (this.checkPhaiLam('KIEMTRAHOANCONGPTM')) {
        let msg = ''
        let dsHS = await this.layDsHoSoPTM()
        if (!(dsHS == null || dsHS == undefined || dsHS.length == 0)) {
          for (let i = 0; i < dsHS.length; i++) {
            const hs = dsHS[i]
            let slDaThem = await this.laySoLuongDaThemHSPTM(hs.HSPTM_ID)
            if (parseInt(slDaThem.SOLUONG) < parseInt(hs.SOLUONG)) {
              msg +=
                '\n\r' +
                (parseInt(hs.SOLUONG) - parseInt(slDaThem.SOLUONG)) +
                ' phần tử ' +
                hs.TEN_LOAI
            }
          }
        }
        if (msg != '') {
          result = false
          this.$root.toastError('Bạn chưa thêm:' + msg)
        }
      }
      return result
    },
    kiemTraChonPTM: function () {
      let rs = this.gridPhanTuMang.getSelectedRecords()
      return !(rs == null || rs == undefined || rs.length == 0)
    },
    kiemTraDuLieu: function () {
      let result = true
      if (this.currentItem.nhanVienGVID == null) {
        result = false
        this.$root.toastError('Hãy nhập nhân viên giao việc!')
      }
      if (result) {
        if (
          !this.chkNgayGVState ||
          this.dsFormControls.dtpNgayGV.value == null ||
          this.dsFormControls.dtpNgayGV.value == undefined
        ) {
          result = false
          this.$root.toastError('Hãy nhập ngày giao việc!')
        }
        if (result) {
          let now = new Date()
          if (this.dsFormControls.dtpNgayGV.value > now) {
            result = false
            this.$root.toastError(
              'Bạn không được phép chọn ngày giao việc lớn hơn ngày hiện tại'
            )
          }
          if (result) {
            if (
              !this.chkNgayBGState ||
              this.dsFormControls.dtpNgayBG.value == null ||
              this.dsFormControls.dtpNgayBG.value == undefined ||
              this.dsFormControls.dtpNgayBG.value <
                this.dsFormControls.dtpNgayGV.value
            ) {
              result = false
              this.$root.toastError(
                'Ngày giao việc không được lớn hơn ngày thực hiện!'
              )
            }
          }
        }
      }
      return result
    },
    form_Created: async function (args) {
      this.dsFormControls.dtpNgayDuKien.BD = new Date()
      this.dsFormControls.dtpNgayDuKien.KT = new Date()
      this.dsNhanVienGiaoViec = await this.layDanhSachNhanVienGiaoViec()
      this.huongGiaoID = await this.layHuongGiao()
      if (this.huongGiaoID == 0) {
        this.$root.toastError('Lấy thông tin hướng giao thất bại!')
      }
      await this.getDsThaoTac()
      await this.getDsControlThaoTac()
      await this.getDsKetQuaXuLy()
      await this.btnLayTT_Click()
    },
    btnLayTT_Click: async function (args) {
      this.dsGiaoViec = [];
      this.dsTienTrinh = [];
      this.dsThueBaoAnhHuong = [];
      this.dsPhanTuMang = [];
      this.dsDanhSachHoSo = await this.layDsHoSoCTBD()
    },
    btnCapNhatPTM_Click: async function (args) {
      // this.getDsPTMSelectedXml();
      if (this.kiemTraChonPTM()) {
        if (this.dsFormControls.dtpNgayDuKien.BD == null) {
          this.$root.toastError('Chọn ngày bắt đầu')
        } else {
          let now = new Date()
          if (this.dsFormControls.dtpNgayDuKien.BD < now) {
            this.$root.toastError(
              'Bạn không được phép chọn ngày bắt đầu nhỏ hơn ngày hiện tại'
            )
          } else {
            if (this.dsFormControls.dtpNgayDuKien.KT == null) {
              this.$root.toastError('Chọn ngày kết thúc')
            } else {
              if (
                this.dsFormControls.dtpNgayDuKien.BD >
                this.dsFormControls.dtpNgayDuKien.KT
              ) {
                this.$root.toastError(
                  'Ngày bắt đầu không được lớn hơn ngày dự kiến hoàn thành!'
                )
              } else {
                let msg = await this.capNhatNgayXuLyPTMCTBD(
                  this.dsFormControls.dtpNgayDuKien.BD,
                  this.dsFormControls.dtpNgayDuKien.KT
                )
                if (msg == '') {
                  this.$root.toastSuccess('Cập nhật phiếu thành công!')
                  await this.getDsPhanTuMangCTBD()
                  await this.getDsTienTrinhCTBD()
                } else {
                  this.$root.toastError(msg)
                }
              }
            }
          }
        }
      } else {
        this.$root.toastError(
          'Hãy chọn phần tử mạng cải tạo/ bảo dưỡng cần xử lý'
        )
      }
    },
    btnCapNhat_Click: async function (args) {
      if (this.kiemTraPhanTuMang()) {
        if (!this.chkNgayBGState) {
          this.$root.toastError('Hãy nhập ngày thực hiện!')
        } else {
          let ck = this.gridDanhSachHoSo.getSelectedRecords()
          if (!(ck == null || ck == undefined || ck.length == 0)) {
            if (
              this.dsFormControls.dtpNgayBG.value == null ||
              this.dsFormControls.dtpNgayBG.value == ''
            ) {
              this.$root.toastError('Chưa chọn ngày thực hiện')
            } else {
              if (this.dsFormControls.dtpNgayBG.value > new Date()) {
                this.$root.toastError(
                  'Bạn không được phép chọn ngày thực hiện lớn hơn ngày hiện tại'
                )
              } else {
                let rs = await this.capNhatNgayThucHienTrenPhieu()
                if (rs == '') {
                  this.$root.toastSuccess('Cập nhật phiếu thành công!')
                  await this.btnLayTT_Click()
                } else {
                  this.$root.toastError(rs)
                }
              }
            }
          }
        }
      }
    },
    btnGiaoViec_Click: async function (args) {

      if (await this.kiemTraPhanTuMang()) {

        if (this.kiemTraChonPTM()) {

          if (this.kiemTraDuLieu()) {

            let s = this.gridPhanTuMang.getSelectedRecords()

            if (!(s == null || s == undefined || s.length == 0)) {

              let doc = document.implementation.createDocument("", "", null)
              let root = doc.createElement("root")

              s.forEach(item => {
                let node = doc.createElement("item")
                node.setAttribute("ptm_id", item.PTM_ID)
                node.setAttribute("chitietcc_id", item.CHITIETCC_ID)
                root.appendChild(node)
              })

              doc.appendChild(root)

              let vngaygiao = this.dsFormControls.dtpNgayGV.value;
              let vnhanvien_giao_id = parseInt(this.currentItem.nhanVienGVID);

              let frm = this.$refs.frmGiaoPhieuCC_NV
              frm.phieucc_id = this.currentItem.phieuCCID
              frm.hscc_id = this.currentItem.hsccID
              frm.hsptm_id = s[0].HSPTM_ID
              frm.ds_ctptm = (new XMLSerializer()).serializeToString(doc)
              frm.huonggiao_id = this.huongGiaoID
              frm.nhanvien_giao_id = vnhanvien_giao_id
              frm.ngaygiao = vngaygiao

              let result = await frm.show()

              if (result.KtThucHien) {
                  this.$root.showLoading(true)
                  try {
                    await this.getDsGiaoViecCTBD(this.currentItem.chiTietCCID)
                  } catch (error) {
                  } finally {
                    this.$root.showLoading(false)
                  }
              }
            } else {
              this.$root.toastError(
                'Hãy chọn phần tử mạng cải tạo/ bảo dưỡng cần xử lý'
              )
            }
          }
        } else {
          this.$root.toastError(
            'Hãy chọn phần tử mạng cải tạo/ bảo dưỡng cần xử lý'
          )
        }
      }
    },
    btnGiaoPhieu_Click: async function (args) {
      let s = this.gridDanhSachHoSo.getSelectedRecords()
      if (!(s == null || s == undefined || s.length == 0)) {
        this.$bvModal.show('modal-Giaophieu')
      } else {
        this.$root.toastError(
          'Hãy chọn phần tử mạng cải tạo/ bảo dưỡng cần xử lý'
        )
      }
    },
    btnTraPhieu_Click: async function (args) {
      let s = this.gridDanhSachHoSo.getSelectedRecords()
      if (!(s == null || s == undefined || s.length == 0)) {
        this.$bvModal.show('formTraPhieuVT')
      } else {
        this.$root.toastError(
          'Hãy chọn phần tử mạng cải tạo/ bảo dưỡng cần xử lý'
        )
      }
    },
    btnThemPTM_Click: async function (args) {
      let frm = this.$refs.frmThemPhanTuMang
      frm.vhscc_id = this.currentItem.hsccID
      frm.vphieu_id = this.currentItem.phieuCCID
      let kq = await frm.show()

      if (kq.KtThucHien) {
        try {
          this.$root.showLoading(true)
          await this.getDsPhanTuMangCTBD()
        } finally {
          this.$root.showLoading(false)
        }
      }
    },
    btnThemPTMV2_Click: async function (args) {
      await this.btnThemPTM_Click()
    },
    btnXoaPTM_Click: async function (args) {
      if (this.kiemTraChonPTM()) {
        this.$bvModal
          .msgBoxConfirm('Bạn có đồng ý xóa phần tử mạng không?', {
            title: 'Thông báo',
            size: 'sm',
            okTitle: 'Đồng ý',
            cancelTitle: 'Hủy',
            centered: true
          })
          .then(async (v) => {
            if (v) {
              let msg = await this.xoaDsChiTietCCPTM()
              if (msg == 'OK') {
                this.$root.showLoading(true)
                try {
                  this.$root.toastSuccess('Xóa phần tử mạng thành công')
                  await this.getDsPhanTuMangCTBD()
                } catch (error) {
                } finally {
                  this.$root.showLoading(false)
                }
              } else {
                this.$root.toastError(msg)
              }
            }
          })
      } else {
        this.$root.toastError(
          'Hãy chọn phần tử mạng cải tạo/ bảo dưỡng cần xử lý'
        )
      }
    },
    btnThemHS_Click: async function (args) {
      // this.$root.toastWarning(
      //   "Rất tiếc, chúng tôi đang chờ form tham chiếu frmCTHoSoDauChuyen"
      // );
      if (this.Tag) {
        this.$refs['frmCTHoSoDauChuyen'].Tag = 1
      }
      this.$refs['frmCTHoSoDauChuyen'].GiaoPhieuTuDong = true
      this.$refs['frmCTHoSoDauChuyen'].huonggiao_id = this.huongGiaoID
      this.$refs['frmCTHoSoDauChuyen'].show()
    },
    btnHoanCong_Click: async function (args) {
      this.$refs.modalHoanCong.show()
    },
    btnHoanThanhPTM_Click: async function (args) {
      if (this.kiemTraPhanTuMang()) {
        if (this.kiemTraChonPTM()) {
          this.$refs.modalGhiChuPTM.show()
        } else {
          this.$root.toastError(
            'Hãy chọn phần tử mạng cải tạo/ bảo dưỡng cần xử lý'
          )
        }
      }
    },
    btnXuatFile_Click: async function (args) {
      this.gridDanhSachHoSo.excelExport()
    },
    modalGiaoPhieu_Show: function () {
      this.$refs.formgiaophieu.initData(this.huongGiaoID)
    },
    modalGiaoPhieu_Ok: async function (v) {
      this.$bvModal.hide('modal-Giaophieu')
      this.dsDanhSachHoSo = await this.layDsHoSoCTBD()
    },
    btnSmsThueBao_Click: async function (args) {
      let tbs = this.gridThueBaoAnhHuong.getSelectedRecords()
      if (!(tbs == null || tbs == undefined || tbs.length == 0)) {
        for (let i = 0; i < tbs.length; i++) {
          await this.xuLyThongBaoTBAnhHuong(tbs[i].THUEBAO_ID, tbs[i].CHITIETCC_ID);
          // const tb = tbs[i]
          // let so_dt = await this.laySoDTThueBao(tb.THUEBAO_ID)
          // let noiDung = await this.layNoiDungSMSTBAnhHuong(
          //   tb.THUEBAO_ID,
          //   tb.CHITIETCC_ID
          // )
          // if (!(so_dt == null || so_dt == undefined || so_dt == '')) {
          //   await this.guiSms(so_dt, noiDung)
          // }
        }
        this.$root.toastSuccess(
          'Đã gửi SMS đến danh sách thuê bao bị ảnh hưởng đang chọn'
        )
      } else {
        this.$root.toastError('Bạn chưa chọn thuê bao ảnh hưởng.')
      }
    },
    btnNhanTinKH_Click: async function (args) {
      this.$bvModal.show('frmNhanTinAnhHuong')
      // this.$root.toastWarning(
      //   "Rất tiếc, chúng tôi đang chờ form tham chiếu frmNhanTin_TB_AnhHuong"
      // );
    },
    btnDuyet_Click: async function (args) {
      this.$root.showLoading(true)
      try {
        let msg = await this.duyetCTBDPTM()
        if (msg == 'OK') {
          this.$root.toastSuccess('Duyệt hồ sơ thành công!')
          await this.btnLayTT_Click()
        } else {
          this.$root.toastError(msg)
        }
      } catch (error) {
      } finally {
        this.$root.showLoading(false)
      }
    },
    gridPhanTuMang_RowSelected: async function (args) {
      this.$root.showLoading(true)
      try {
        let itemData = args.data
        if (args.isHeaderCheckboxClicked) itemData = args.data[0]
        this.currentItem.chiTietCCID = itemData.CHITIETCC_ID
        await this.getDsThueBaoAnhHuongCTBD(itemData.CHITIETCC_ID)
        await this.getDsTienTrinhCTBD(itemData.CHITIETCC_ID)
        await this.getDsGiaoViecCTBD(itemData.CHITIETCC_ID)
      } catch (error) {
      } finally {
        this.$root.showLoading(false)
      }
    },
    gridDanhSachHoSo_rowSelected: async function (args) {
      this.$root.showLoading(true)
      try {
        this.currentItem.hsccID = args.data.HSCC_ID
        this.currentItem.phieuCCID = args.data.PHIEUCC_ID
        this.currentItem.chiTietCCID = args.data.CHITIETCC_ID
        this.dsFormControls.tsbtnGiaoPhieu.enabled = (args.data.NGAY_TH != null);
        await this.getDsPhanTuMangCTBD()
      } catch (error) {
      } finally {
        this.$root.showLoading(false)
      }
    },
    gridPhanTuMang_CommandClicked: async function (name, args) {
      this.$root.showLoading(true)
      try {
        if (name.toUpperCase() == 'DONGBO_THUEBAO') {
          let rs = await this.dongBoThueBaoBDPTM(args.CHITIETCC_ID)
          if (rs == '') {
            this.$root.toastSuccess(
              'Đã đồng bộ các thuê bao ảnh hưởng thành công'
            )
            await this.getDsThueBaoAnhHuongCTBD(args.CHITIETCC_ID)
          } else {
            this.$root.toastError('Đồng bộ thất bại ' + rs)
          }
        } else if (name.toUpperCase() == 'THEM_TIENTRINH') {
          this.createFormNoiDungData(args.HSCC_ID, args.CHITIETCC_ID, 0, '')
          this.$refs.modalNoiDung.show()
        } else if (name.toUpperCase() == 'CAP_VATTU') {
          console.log('CAP_VATTU', args)
          this.modelVatTu.phieucc_id = this.currentItem.phieuCCID
          this.modelVatTu.chitietcc_id = args.CHITIETCC_ID
          this.modelVatTu.ptm_id = args.PTM_ID
          this.modelVatTu.loaitbi_ecms_id = args.LOAITBI_ID
          this.$refs.frmCapVatTu_PTM.openDialog()
          // this.$root.toastWarning(
          //   'Rất tiếc, chúng tôi đang chờ form tham chiếu \nTrên tài liệu URD (trang 12): Tham chiếu BSS-1446 UR2.4.007 Mục 1.1.3 – Cấp/Thu hồi vật tư \nCode DHSX: frmCapVatTu_PTM'
          // )
        }
      } catch (error) {
      } finally {
        this.$root.showLoading(false)
      }
    },
    gridGiaoViec_CommandClicked: async function (name, args) {
      this.$root.showLoading(true)
      try {
        if (name.toUpperCase() == 'CAPNHAT_GHICHU') {
          this.currentItem.currentGiaoViec = args
          this.$refs.modalGhiChuXLNV.show()
        } else if (name.toUpperCase() == 'CAPNHAT_KETQUAXL') {
          if (args.KQXL_ID == null) {
            this.$root.toastError('Bạn chưa chọn kết quả xử lý!')
          } else {
            let rs = await this.capNhatKQXLgiaoViecNVCTBD(
              this.currentItem.chiTietCCID,
              args.NHANVIEN_TH_ID,
              args.NHIEMVU_ID,
              args.KQXL_ID,
              args.ND_DEXUAT,
              args.ND_TONTAI
            )
            if (rs == '') {
              this.$root.toastSuccess('Cập nhật kết quả xử lý thành công')
              await this.getDsGiaoViecCTBD(this.currentItem.chiTietCCID)
            } else {
              this.$root.toastError('Đồng bộ thất bại ' + rs)
            }
          }
        } else if (name.toUpperCase() == 'NHANTIN_SMS') {
          let rs = await this.xuLyThongBaoSms(this.currentItem.chiTietCCID, args.NHANVIEN_TH_ID, args.NHIEMVU_ID);
          if(rs.Result == true){
            this.$root.toastSuccess(rs.Msg);
          }
          else {
            this.$root.toastError(rs.Msg);
          }
          // let rs = await this.layThongTinSMS(
          //   this.currentItem.chiTietCCID,
          //   args.NHANVIEN_TH_ID,
          //   args.NHIEMVU_ID
          // )
          // if (rs != null) {
          //   if (rs.SO_DT != null) {
          //     await this.guiSms(rs.SO_DT, rs.NOIDUNG)
          //     this.$root.toastSuccess('Nhắn tin thành công')
          //   } else {
          //     this.$root.toastError(
          //       'Không tìm thấy số điện thoại nhân viên thực hiện'
          //     )
          //   }
          // } else this.$root.toastError('Nhắn tin thất bại')
        }
      } catch (error) {
      } finally {
        this.$root.showLoading(false)
      }
    },
    gridTienTrinh_CommandClicked: async function (name, args) {
      this.$root.showLoading(true)
      try {
        if (name.toUpperCase() == 'SUA') {
          this.createFormNoiDungData(0, 0, args.TIENTRINHCC_ID, args.NOIDUNG)
          this.$refs.modalNoiDung.show()
        } else if (name.toUpperCase() == 'XOA') {
          if (args.KIEU == 2) {
            this.$root.toastError(
              'Bạn không thể xóa tiến trình lưu biến động cập nhật ngày bắt đầu, ngày hoàn thành!'
            )
          } else {
            this.$bvModal
              .msgBoxConfirm('Bạn có đồng ý xóa tiến trình không ?', {
                title: 'Thông báo',
                size: 'sm',
                okTitle: 'Đồng ý',
                cancelTitle: 'Hủy',
                centered: true
              })
              .then(async (v) => {
                if (v) {
                  let rs = await this.xoaTienTrinhCatChuyen(
                    args.TIENTRINHCC_ID,
                    args.CHITIETCC_ID,
                    args.NHANVIEN_ID
                  )
                  if (rs) {
                    this.$root.toastSuccess('Xóa tiến trình thành công!')
                    await this.getDsTienTrinhCTBD(this.currentItem.chiTietCCID)
                  } else {
                    this.$root.toastError('Xóa tiến trình thất bại')
                  }
                }
              })
          }
        }
      } catch (error) {
      } finally {
        this.$root.showLoading(false)
      }
    },
    gridThueBaoAnhHuong_CommandClicked: async function (name, args) {
      this.$root.showLoading(true)
      try {
        if (name.toUpperCase() == 'DOI_THONGTINCAP') {
          this.dataDauNoiThueBao = {
            choPhepDoiTramTbi: true,
            tramTbiId: -1,
            chonTramTbiTheoTB: false,
            thueBaoId: args.THUEBAO_ID,
            kieu: 0,
            dkccID: args.DKCC_ID
          }
          this.$refs.modalDauNoiThueBao.show()
        }
      } catch (error) {
      } finally {
        this.$root.showLoading(false)
      }
    },
    dataDauNoiThueBao_NhapCapThueBao: async function (args) {
      if (this.$refs.frmDauNoiThueBao.IsModifed) {
        let rs = await this.hoanThienCatChuyen(this.dataDauNoiThueBao.dkccID)
        if (rs) {
          await this.getDsThueBaoAnhHuongCTBD(this.currentItem.chiTietCCID)
        } else {
          this.$root.toastError('1. Thay đổi thông tin cáp: [Thành công]\r\n2. Hoàn thiện đấu chuyển: [Thất bại]')
        }
      }
    },
    createFormNoiDungData: function (
      hsccID,
      chiTietCCID,
      tienTrinhCCID,
      noiDung
    ) {
      this.formNoiDungData = {
        hsccID: hsccID,
        chiTietCCID: chiTietCCID,
        tienTrinhCCID: tienTrinhCCID,
        noiDung: noiDung
      }
    },
    modalNoiDung_Save: async function (args) {
      if (this.$refs.formNoiDung.validateForm()) {
        this.$root.showLoading(true)
        try {
          await this.$refs.formNoiDung.saveData()
          if (this.$refs.formNoiDung.tienTrinhCCID == 0) {
            this.$root.toastSuccess('Thêm tiến trình thành công')
          } else {
            this.$root.toastSuccess('Cập nhật tiến trình thành công')
          }
          await this.getDsTienTrinhCTBD(this.currentItem.chiTietCCID)
        } catch (error) {
        } finally {
          this.$root.showLoading(false)
        }
      } else {
        args.preventDefault()
      }
    },
    modalNoiDung_Show: function (args) {
      this.$refs.formNoiDung.fillData(
        this.formNoiDungData.hsccID,
        this.formNoiDungData.chiTietCCID,
        this.formNoiDungData.tienTrinhCCID,
        this.formNoiDungData.noiDung
      )
    },
    modalNoiDung_refresh: function (args) {
      this.modalNoiDung_Show(args)
    },
    modalGhiChuPTM_Save: async function (args) {
      if (this.$refs.formGhiChuPTM.validateForm()) {
        this.$root.showLoading(true)
        try {
          let m = await this.capNhatTrangThaiXuLyPTM(
            this.$refs.formGhiChuPTM.noiDung
          )
          if (m == '') {
            this.$root.toastSuccess('Cập nhật thành công')
            await this.getDsPhanTuMangCTBD()
          } else {
            this.$root.toastError(m)
          }
        } catch (error) {
        } finally {
          this.$root.showLoading(false)
        }
      } else {
        args.preventDefault()
      }
    },
    modalGhiChuPTM_Show: function (args) {
      this.$refs.formGhiChuPTM.fillData(0, 0, 0, '')
    },
    modalGhiChuXLNV_Save: async function (args) {
      if (this.$refs.formGhiChuXLNV.validateForm()) {
        this.$root.showLoading(true)
        try {
          // capNhatGhiChuXuLyNVCTBD
          let m = await this.capNhatGhiChuXuLyNVCTBD(
            this.currentItem.chiTietCCID,
            this.currentItem.currentGiaoViec.NHANVIEN_TH_ID,
            this.currentItem.currentGiaoViec.NHIEMVU_ID,
            this.$refs.formGhiChuXLNV.noiDung
          )
          if (m == '') {
            this.$root.toastSuccess('Cập nhật thành công')
            await this.getDsGiaoViecCTBD(this.currentItem.chiTietCCID)
          } else {
            this.$root.toastError('Cập nhật thất bại ' + m)
          }
        } catch (error) {
        } finally {
          this.$root.showLoading(false)
        }
      } else {
        args.preventDefault()
      }
    },
    modalGhiChuXLNV_Show: function (args) {
      this.$refs.formGhiChuXLNV.fillData(
        0,
        0,
        0,
        this.currentItem.currentGiaoViec.GHICHU_XL
      )
    },
    modalHoanCong_Save: async function (args) {
      if (this.$refs.formHoanCong.validateForm()) {
        this.$root.showLoading(true)
        try {
          let m = await this.hoanCongCTBD(
            this.currentItem.phieuCCID,
            this.currentItem.hsccID,
            this.$refs.formHoanCong.noiDung
          )
          if (m == 'OK') {
            //Chuyển SMS vào trong hàm hoàn công
            /*
            let sms = await this.layThongTinSMSHoanThanh()
            if (
              !(
                sms == null ||
                sms == undefined ||
                sms.SO_DT == null ||
                sms.SO_DT == undefined ||
                sms.SO_DT == ''
              )
            ) {
              await this.guiSms(sms.SO_DT, sms.NOIDUNG)
            }*/
            
            if (this.checkPhaiLam('TUDONG_GIAOPHIEU_DUYET_CAPVT')) {
              await this.giaoPhieuTDDuyetTDPTM(
                this.currentItem.phieuCCID,
                this.currentItem.hsccID
              )
            }
            if (this.checkPhaiLam('TUDONG_GIAOPHIEU_DUYET_THUHOIVT')) {
              await this.giaoPhieuTDDuyetTDPTM(
                this.currentItem.phieuCCID,
                this.currentItem.hsccID
              )
            }
            this.$root.toastSuccess('Hoàn công thành công')
            await this.btnLayTT_Click()
          } else {
            this.$root.toastError('Cập nhật thất bại ' + m)
          }
        } catch (error) {
        } finally {
          this.$root.showLoading(false)
        }
      } else {
        args.preventDefault()
      }
    },
    modalHoanCong_Show: function (args) {
      this.$refs.formHoanCong.fillData(0, 0, 0, '')
    },
    refreshGrid: function (args) {
      switch (args) {
        case 1:
          this.gridTienTrinh.refresh()
          break
        case 2:
          this.gridThueBaoAnhHuong.refresh()
          break
        default:
          this.gridGiaoViec.refresh()
          break
      }
    },
    gridThueBaoAnhHuong_PaginationCommandClick: async function (command, args) {
      if (command.name == 'DOI_CAP_HANG_LOAT') {
        this.dsThueBaoNhapCapHangLoat = this.gridThueBaoAnhHuong.getSelectedRecords()
        this.$bvModal.show('id-modal-doicap-hangloat')
      }
    },
    modalDoiCapHangLoat_Change: async function (args) {
      await this.getDsThueBaoAnhHuongCTBD(this.currentItem.chiTietCCID)
    },
    gridDanhSachHoSo_rowDataBound: function(args){
      if(args.data.MUCDO_ID == 2){
        args.row.classList.add("text-danger");
      }
    }
  }
}
</script>
<style>
.hoso-dauchuyen-daunoi-thuebao .page-content {
  overflow: visible !important;
  height: fit-content;
  background-color: white;
}
</style>
