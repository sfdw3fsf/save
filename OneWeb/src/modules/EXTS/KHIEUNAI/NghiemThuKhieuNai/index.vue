<template src="./index.html"></template>
<script>
import Vue from 'vue'
import { mapState, mapActions, mapGetters } from 'vuex'
import { TiepNhanKN, getterName, actionName, statePropertyName } from '../TiepNhanKN/store/index'
import { DateTimePickerPlugin } from '@syncfusion/ej2-vue-calendars'
import TraPhieu from "../TraPhieuKhieuNai";

import moment from 'moment'
import BssErrorMarker from '@/components/BssErrorMarker'
import BssRequiredMarker from '@/components/BssRequiredMarker'
import { required, minValue, maxValue, maxLength, numeric, email } from 'vuelidate/lib/validators'
import { Sort, Filter, Toolbar, Edit, CommandColumn } from '@syncfusion/ej2-vue-grids'
import { L10n } from '@syncfusion/ej2-base'
//import RowTemplate from './RowTemplateGiamCuoc/index.vue'
import EventBus from '@/utils/eventBus'
import ChiTietKhieuNai from '../ChiTietKhieuNai/ChiTiet.vue'
//import DiaChiModal from './modal/ChonDiaChi/DiaChiModal.vue'
import { currency } from '@/filters/currency'
import GRID_COLUMS from './constant'

L10n.load({
  'vi-VN': {
    grid: {
      EmptyRecord: 'Không có bản ghi nào để hiển thị',
      Item: 'Bản ghi',
      Items: 'Bản ghi',
      FilterbarTitle: ''
    }
  }
})
Vue.use(DateTimePickerPlugin)
const moduleName = 'TiepNhanKhieuNai'
export default {
  provide: {
    grid: [Sort, Filter, Edit, Toolbar, CommandColumn]
  },
  name: moduleName,
  components: {
    BssErrorMarker,
    BssRequiredMarker,
    ChiTietKhieuNai,
    TraPhieu,
  },
  props: {},

  validations: {
    KHIEUNAI: {
      CMT: {
        // numeric,
        maxLength: maxLength(100)
      },
      ND_KHIEUNAI: {
        required,
        maxLength: maxLength(4000)
      },
      GHICHU_KN: {
        required,
        maxLength: maxLength(4000)
      }
      // EMAIL: {
      //   email
      // }
    },
    
    NGUOI_KHIEUNAI: {
      DIENTHOAI_LH: {
        required,
        maxLength: maxLength(50),
        numeric
      },
      NGUOI_KN: {
        required,
        maxLength: maxLength(200)
      }
    }
  },
  computed: {
    ...mapState('khieunai/TiepNhanKN', statePropertyName),
    ...mapState('khieunai', ['TiepNhanKN']),
    ...mapGetters('khieunai/TiepNhanKN', getterName),
    getDS_CHITIETNO_THEO_KHOANMUC() {
      return this.DS_CHITIETNO_THEO_KHOANMUC
    }
    // dsGiamCuocAll: function() {
    //   if (!(this.DsGC == null || this.DsGC.length === 0)) {
    //     let f = this.DsGC.find((x) => x.ID === '-1')
    //     if (f == null) {
    //       let result = []
    //       let newItem = this.createNoItem()
    //       newItem.ID = '-1'
    //       result.push(newItem)
    //       for (let i = 0; i < this.DsGC.length; i++) {
    //         result.push(this.DsGC[i])
    //       }
    //       this.setState({ key: 'DsGC', value: result })
    //     }
    //   } else {
    //     this.setState({ key: 'DsGC', value: [] })
    //     let newItem = this.createNoItem()
    //     newItem.ID = '-1'
    //     this.setState({ key: 'DsGC', value: [newItem] })
    //   }
    //   return this.DsGC.filter((item) => {
    //     return item.ID != null
    //   })
    // }
  },
  beforeCreate() {},
  async created() {
    await this.createApiManager()
    await this.clearState()
    this.$root.showLoading(true)
    await this.loadData()
    await this.getDsLoaiGT()
    this.columnFields = GRID_COLUMS[1]
  },
  data() {
    return {
      activetab: 1,
      paneThongTinTraPhieu:false,
      enableBtnGiaoViec: true,
      enableBtnHuyGiao: false,
      enableBtnTraPhieu: false,
      enableBtnGiaoPhieu: false,
      enableBtnHoanThanh: false,
      enableBtnHoanThanh_GQKN: false,
      enableBtnKhoaPhieu: false,
      enableBtnGiaoPhoiHop: false,
      enableBtnGhiLai:false,
      Button:{
        enableGiaoViec:true,
        enableHuyGiaoViec:true,
        enableTraPhieu:true,
        enableKhoaPhieu:true,
      },
      dsThaoTacControl: [],
      isToTruong: true,
      columnFields: [],
      GRID_COLUMS: GRID_COLUMS,
      EnabledSelectFirstRow: true,
      dsThueBao: [],
      ReqValidationRules: {
        required: true
      },
      khieunai_id: null,
      HINHTHUC_KN: {
        VAN_BAN: 3
      },
      LOAI_DV: {
        DAILY_XLKN: 45,
        TOKD_XLKN: 427,
        TRAM_VT: 5
      },
      lnkKhieunai_BS: {
        Visible: false
      },
      THEM_KHDB_KHI_KHIEUNAI: -1,
      LOAI_CHUDEKN: {
        PHANHOI_KHONG_THUEBAO: 5
      },
      FocusedRowHandle: -1,
      isCalculatorShow: false,
      dataDiaChi: {
        diachi_id: 0,
        tinh_id: 0,
        quan_id: 0,
        phuong_id: 0,
        pho_id: 0,
        ap_id: 0,
        khu_id: 0,
        dacdiem_id: 0,
        sonha: '',
        block: '',
        tang: '',
        sophong: '',
        motathem: '',
        diachi: ''
      },
      DiaDanh: {},
      dsGiamCuoc: [],
      DS_DIACHI: [],
      DS_NGUYENNHAN_LOI:[],
      DS_XACMINH_GIAMCUOC:[],
      NGHIEMTHU:{},
      dsLoaiGT: [],
      checkAll: false,
      dsIdChon: [],
      themKHDBKhiKhieuNai: '',
      nhanTinGiaoViec: '',
      nhanTinGiaoPhieuXLKN: '',
    //   rowTemplate: function(e) {
    //     return {
    //       template: RowTemplate
    //     }
    //   },
      commands: [
        {
          type: 'Delete',
          buttonOption: { cssClass: 'e-flat', iconCss: 'e-delete e-icons' }
        }
      ],
      toolbar: [
        {
          text: 'Click vào dòng này để nhập mới',
          tooltipText: 'Click vào dòng này để nhập mới',
          prefixIcon: 'e-expand',
          id: 'add',
          align: 'Center'
        }
      ],
      editSettings: { allowEditing: true, allowAdding: true, allowDeleting: true },
      KHIEUNAI_BS: [],
      danhSachGC: [],
      danhSachGC2: [],
      txtNGUYENNHAN: '',
      dskhieunai_dv: [],
      dsKhieuNai: [],
      dsDiaChi: [],
      dsDiaChiKN: [],
      quytrinh_id: null,
      optXuLy: '0',
      chkHenTL: false,
      txtMaTB: '',
      daily_id: null,
      Kyhoadon: null,
      dtpNgayTN: null,
      dtpTuNgay: null,
      dtpDenNgay: null,
      dtpNgayBN: null,
      dtpNgayXL: null,
      Ngay_Cn: null,
      ky_kn: '',
      dtpNgayCapCMT: null,
      NGUOI_KHIEUNAI: {},
      KETQUA_KHIEUNAI: {},
      GIAOPHIEU: {},
      XACMINH_KHIEUNAI: {},
      THUEBAO: {},
      KHIEUNAI: {},
      dsNhanVienThucHien: [],
      nhanVienThucHienID: 0,
      Selected: {
        DICHVUVT_ID: '',
        LOAITB_ID: '',
        LOAIKH_ID: '',
        DOITUONG_ID: '',
        HTKN_ID: '',
        LOAI_ID: '',
        CHUDEKN_ID: '',
        NGUYENNHAN_ID: '',
        NHANVIEN_ID: '',
        PAXL_ID: '',
        HTTL_ID: '',
        HAILONG: '',
        LOAIGT_ID: '',
        HTNT_ID:''
      },
      ReadOnly: {
        txtDiaChi: false
      },
      Button: {
        tsbtnNhapMoi: false,
        tsbtnGhiLai: false,
        tsbtnXoa: false,
        tsbtnHuyBo: false,
        tsbtnKhoaPhieu: false,
        tsbtnGiaoPhieu: false,
        btnNhanVienLoi: false
      },
      gridDS_TB_KHIEUNAI_rowSelected: [],
      DS_ThangGC: [],
      DS_ThangApDung: [],
      showContent: {
        showKetQuaXuLy: true
      },
      collapseBangTTTB: false,
      collapseBangTTNKN: false,
      collapseBangTTKN: false,
      collapseBangTTCTN: false,
      collapseBangLSKN: false,
      collapseBangDSKN: false,
      ChenNo: { ma_tt: null, kycuoc: null, ma_tb: null },
      noiDungKhoaPhieu: null,
    }
  },
  watch: {
    checkAll(newVal) {
      console.log('🚀 ~ file: index.vue ~ line 298 ~ checkAll ~ newVal', newVal)
      if (newVal) {
        this.dsIdChon = this.DS_TB_KHIEUNAI.map((item) => item.KHIEUNAI_ID)
        return
      }
      if (this.dsIdChon.length > 0 && this.dsIdChon.length === this.DS_TB_KHIEUNAI.length) {
        this.dsIdChon = []
      }
    },
    dsIdChon(newVal) {
      this.checkAll = newVal.length > 0 && newVal.length === this.DS_TB_KHIEUNAI.length
    },
    optXuLy(val) {
      if (val === '0') {
        this.$nextTick(() => this.$refs.txtLyDo.focus())
        this.Button.tsbtnKhoaPhieu = false
        this.Button.tsbtnGiaoPhieu = true
      } else {
        this.$nextTick(() => this.$refs.txtNoiDungXL.focus())
        this.Button.tsbtnKhoaPhieu = true
        this.Button.tsbtnGiaoPhieu = false
        this.Selected.NHANVIEN_ID = this.DS_NHANVIEN[0].NHANVIEN_ID
      }
    },
    'Selected.DICHVUVT_ID'(val) {
      if (val) {
        this.getDANHMUC({ loai: 'DS_LOAIHINH_TB', option: val }).then((rep) => {})
      }
    },
    'Selected.NGUYENNHAN_ID'(val) {
      if (val) {
        this.Button.btnNhanVienLoi = true
        let ketqua=this.DS_NGUYENNHAN_LOI.filter(item=>item.NGUYENNHAN_ID==this.Selected.NGUYENNHAN_ID)[0]
        this.KETQUA_KHIEUNAI.DOITUONG_LOI=ketqua.DOITUONG_LOI_ID
        this.Selected.PAXL_ID=ketqua.GIAM_CUOC==1?"1":"0"     
      } else {
        this.Button.btnNhanVienLoi = false
      }
    }    
  },
  methods: {
    ...mapActions('khieunai/TiepNhanKN', actionName),
    status(validation) {
      return {
        error: validation.$error,
        dirty: validation.$dirty
      }
    },
    checkEmptyNumber: function(args) {
      return args == undefined || args == null
    },
    checkEmptyString: function(args) {
      return args == undefined || args == null || args.toString().trim() == ''
    },
    showKetQuaXuLy() {
      this.showContent.showKetQuaXuLy = !this.showContent.showKetQuaXuLy
    },
    handleCommand(e) {
      alert(e.rowData)
    },
    async tsbtnNhapExcel() {
      this.$refs.ModalDocFile.show().then((result) => {
        if (result.ok) {
        } else {
        }
      })
    },

    onAcceptDiaChi(data) {
      this.NGUOI_KHIEUNAI.DIACHI_LH = data.diachimoi
      this.DiaDanh.Ap_id = data.ap_id
      this.DiaDanh.DacDiem_id = data.dacdiem_id
      this.DiaDanh.Khu_id = data.khu_id
      this.DiaDanh.Pho_id = data.pho_id
      this.DiaDanh.Phuong_id = data.phuong_id
      this.DiaDanh.Quan_id = data.quan_id
      this.DiaDanh.Tinh_id = data.tinh_id
      this.DiaDanh.SoNha = data.sonha
      this.DiaDanh.diachi = data.diachimoi
      this.$refs.DIACHI_LH.focus()
    },
    txtEmail_Leave() {},
    async tsbtnHSKN_Click() {
      if (this.isEmptyObj(this.KHIEUNAI)) return
      if (this.KHIEUNAI.KHIEUNAI_ID === null) return
      this.$refs.frmHoSoKhieuNai.khieunai_id = this.KHIEUNAI.KHIEUNAI_ID
      this.$refs.frmHoSoKhieuNai.show()
    },

    async HienThiTheoID(khieunai_id) {
      try {
        await this.lay_ds_khieunai_theo_id(khieunai_id)
      } catch (error) {}
    },
    async lay_ds_khieunai_theo_id(KHIEUNAI_ID) {
      await this.getDS_TB_KHIEUNAI_ID({ KHIEUNAI_ID })
    },
    async LookupByAccount(vmatb, dichvuvt_id, loaitb_id) {
      await this.tsbtnNhapMoi_Click()
      this.txtMaTB = vmatb
      this.Selected.DICHVUVT_ID = Number(dichvuvt_id)
      this.Selected.LOAITB_ID = Number(loaitb_id)
      await this.txtMaTB_KeyPress()
    },

    async tsbtnGiaoPhieu_Click() {
      try {
        if (this.DS_TB_KHIEUNAI.length > 0) {
          if (this.KHIEUNAI.KHIEUNAI_ID === null) return
          if (this.KHIEUNAI.HTKN_ID === this.HINHTHUC_KN.VAN_BAN) {
            let data = {
              VKHIEUNAI_ID: this.KHIEUNAI.KHIEUNAI_ID
            }
            let rs = await this.getHOSO_KHIEUNAI(data)
            if (rs.data.length === 0) {
              await this.$alert('Chưa Upload file văn bản. Bạn không thể giao phiếu !', 'Thông báo', {
                confirmButtonText: 'Đóng',
                type: 'Error'
              })
              return
            }
          }
          let quytrinh_id = this.KHIEUNAI.QUYTRINH_ID
          let data = {
            VQUYTRINH_ID: quytrinh_id,
            VTHUTU_ID: 2
          }
          let rs = await this.getHUONGGIAO_QUYTRINH(data)
          if (rs.data.length > 0) {
            let vhuonggiao_id = parseInt(rs.data[0].HUONGGIAO_ID)
            let data = {
              VNHOM_QT_ID: 3,
              VQUYTRINH_ID: 0,
              VLOAIHD_ID: 0,
              VDICHVUVT_ID: 0,
              VTENBANG: 'HUONGGIAO',
              VNGUOIDUNG_ID: this.$root.token.getNguoiDungID()
            }
            rs = await this.getLAY_DS_HUONGGIAO(data)
            console.log('🚀 ~ file: TiepNhanKN.vue:434 ~tsbtnGiaoPhieu_Click ~ rs', rs)
            if (rs.data.length > 0) {
              var ds = rs.data.filter((e) => e.HUONGGIAO_ID === vhuonggiao_id)
              console.log('🚀 ~ file: TiepNhanKN.vue:437 ~tsbtnGiaoPhieu_Click ~ ds', ds)
              if (ds.length > 0) {
                if (vhuonggiao_id !== 0) {
                  let data = {
                    kieuId: 1,
                    huongGiaoId: vhuonggiao_id,
                    maTb: this.KHIEUNAI.MA_TB,
                    ttphId: 1,
                    ngaygiao: null
                  }
                  console.log('🚀 ~ file: TiepNhanKN.vue:460 ~ tsbtnGiaoPhieu_Click ~ data:', data)
                  let modal = this.$refs['ModalGiaoPhieuKN']
                  modal.setData(data)
                  modal.show()
                }
              } else {
                let modal = this.$refs['ModalGiaoPhieuKN']
                modal.setData(null)
                modal.show()
              }
            } else {
              let modal = this.$refs['ModalGiaoPhieuKN']
              modal.setData(null)
              modal.show()
            }
          }
        } else {
        }
      } catch (error) {}
    },
    hide() {
      this.getTB_KHIEUNAI(this.activetab)
    },
    onChange() {},
    getHeaderTemplateChonNhieu(parent) {
      return function() {
        return {
          template: {
            template: `
                            <div class="check-action" style="display: inline; left: 5px;">
                                <input type="checkbox" v-model="parent.checkAll" class="check"/>
                                <span class="name"></span>
                            </div>
                        `,
            data() {
              return {
                parent: parent
              }
            }
          }
        }
      }
    },
    getColumnTemplateChonNhieu(parent) {
      return function() {
        return {
          template: {
            template: `
                            <div class="check-action">
                                <input
                                    type="checkbox"
                                    class="check"
                                    v-model="parent.dsIdChon"
                                    :value="data.KHIEUNAI_ID"/>
                                <span class="name"></span>
                            </div>
                        `,
            data() {
              return {
                parent: parent,
                data: {}
              }
            }
          }
        }
      }
    },
    async btnChenNo_Click() {
      if (this.THUEBAO.MA_TT === null) return
      var kyht = null
      if (this.Button.tsbtnNhapMoi === false) {
        kyht = moment(this.Kyhoadon).format('YYYYMM') + '01'
      } else {
        var ky = this.KHIEUNAI.KY_KN
        if (ky !== null && ky !== undefined) {
          kyht = ky.substring(0, 4) + ky.substring(4, 6) + '01'
        } else {
          kyht = moment(this.Kyhoadon).format('YYYYMM') + '01'
        }
      }
      this.ChenNo.ma_tt = this.THUEBAO.MA_TT
      this.ChenNo.ma_tb = this.THUEBAO.MA_TB
      this.ChenNo.kycuoc = kyht
      this.$refs.refChenNo.openDialog()
      //await this.NAPCOMBO_TGC_KGC(this.THUEBAO.MA_TT)
      await this.NAP_DS_TIENGC(this.KHIEUNAI.KHIEUNAI_ID)
    },
    gridGC_refresh() {
      this.$refs.gridGC.refresh()
    },
    clickAddNew() {
      this.addRowGC()
    },
    themNo: function(args) {
      let item = this.createNoItem()
      item.ID = this.createID()
      item.KY_GC = args.KY_GC
      item.THANG_GC = args.THANG_GC
      item.TIEN = args.TIEN
      item.TIEN_GC = args.TIEN_GC
      item.VAT_GC = args.VAT_GC
      this.setState({ key: 'DsGC', value: [...this.DsGC, item] })
      let f = this.DsGC.find((x) => x.ID === '-1')
      if (f != null) {
        let t = { ...f }
        t.FOCUSED = false
        t.KY_GC = 0
        t.THANG_GC = 0
        t.TIEN = 0
        t.TIEN_GC = 0
        t.VAT_GC = 0
        this.setDataGC(t)
      }
    },
    addRowGC() {
      if (this.isEmptyObj(this.THUEBAO)) return
      try {
        var tmp = [...this.DsGC]
        tmp.push({ THANG_GC: null, KY_GC: null, TIEN: 0, TIEN_GC: 0, VAT_GC: 0, ISUPDATE: 0, ISADDNEW: 0 })
        this.setState({ key: 'DsGC', value: tmp })
        this.$refs.gridGC.refresh()
      } catch (error) {}
    },
    clickHandler(args) {
      if (args.item.id === 'add') {
        this.addRowGC()
      }
    },
    async loadData() {
      try {
        await Promise.all([
          // this.getDICHVU_VT({ VDICHVUVT_ID: '' }),
          // this.getDANHMUC({ loai: 'DS_LOAI_KH', option: '' }),
          // this.getDANHMUC({ loai: 'DS_DOITUONG', option: '' }),
          this.getDANHMUC({ loai: 'DS_HINHTHUC_KN', option: '' }),
          // this.getDANHMUC({ loai: 'DS_LOAI_KN', option: '' }),
          // this.getDANHMUC({ loai: 'DS_KETQUA_KN', option: '' }),
          // this.getDANHMUC({ loai: 'DS_HINHTHUC_TL', option: '' }),
          // this.getDANHMUC({ loai: 'DS_MD_HAILONG', option: '' }),
          this.getDANHMUC({ loai: 'DS_NHANVIEN', option: this.$root.token.getNhanVienID() })
          //this.getDANHMUC({loai: 'DS_KQ_LOI_KN', option: 2})
        ])
        if (this.DS_NHANVIEN.length > 0) this.Selected.NHANVIEN_ID = this.DS_NHANVIEN[0].NHANVIEN_ID
        if (this.DS_HINHTHUC_KN.length > 0) this.Selected.HTKN_ID = 2
        

        var dt = await this.LAY_DS_THAMSO_MD2({ P_THAMSO: 'THEM_KHDB_KHI_KHIEUNAI' })
        if (dt.length > 0) {
          let strvtemp = 'THEM_KHDB_KHI_KHIEUNAI'
          let o = dt.find((e) => e.MA_TS === strvtemp)
          if (o !== undefined) {
            if (o.TEN_TS.toString() === '1') {
              this.THEM_KHDB_KHI_KHIEUNAI = 1
            }
          }
        } else {
          this.THEM_KHDB_KHI_KHIEUNAI = 0
        }
      } catch (error) {
        console.log('🚀 ~ file: TiepNhanKN.vue:669 ~ loadData ~ error:', error)
      }
    },
    btnNhanVienLoi_Click() {
      if (this.KHIEUNAI.KHIEUNAI_ID === null) return
      this.$refs.ModalNhanVienLoi.show(this.KHIEUNAI.KHIEUNAI_ID).then((result) => {
        if (result.ok) {
        } else {
        }
      })
    },
    SetButton(kieu) {
      this.Button.tsbtnNhapMoi = false
      this.Button.tsbtnGhiLai = false
      this.Button.tsbtnXoa = false
      this.Button.tsbtnHuyBo = false
      if (kieu === -1) {
        // Bat dau
        this.Button.tsbtnGhiLai = true
        this.Button.tsbtnHuyBo = true
      }
      if (kieu === 0) {
        // Bat dau
        this.$refs.txtMaTB.focus()
        this.Button.tsbtnNhapMoi = true
      }
      if (kieu === 1) {
        // Them moi
        this.$refs.txtMaTB.focus()
        this.Button.tsbtnGhiLai = true
        this.Button.tsbtnHuyBo = true
        if (this.DS_LOAI_KN.length > 0) {
          let khieuNaiNghiepVu = this.DS_LOAI_KN.find((e) => e.TEN_LOAI === 'Khiếu nại nghiệp vụ')
          if (khieuNaiNghiepVu) {
            this.Selected.LOAI_ID = khieuNaiNghiepVu.LOAI_ID
          } else {
            this.Selected.LOAI_ID = this.DS_LOAI_KN[0].LOAI_ID
          }
        }
        this.Clear()
      }
      if (kieu === 2) {
        // Huy
        this.Button.tsbtnNhapMoi = true
        this.Button.tsbtnXoa = true
      }
      if (kieu === 3) {
        // Edit
        this.Button.tsbtnNhapMoi = true
        this.Button.tsbtnXoa = true
        this.Button.tsbtnGhiLai = true
        this.Button.tsbtnHuyBo = true
      }
    },
    kiemTraKhoaPhieuKN: async function(phieukn_id) {
      let rs = await this.$root.context.post('/web-gqkn/xu-ly-khieu-nai/kiemTraKhoaPhieuKN', {
        id: phieukn_id
      })
      return rs.data
    },
    layKetQuaXacMinhGiamCuoc: async function(phieukn_id) {
      let rs = await this.$root.context.get('/web-gqkn/xu-ly-khieu-nai/lay-danhmuc-xacminh-theo-khieunai-id/'+phieukn_id)
      return rs.data
    },
    kiemTraPhieuCon: async function(phieukn_id) {
      let rs = await this.$root.context.post('/web-gqkn/xu-ly-khieu-nai/kiemTraPhieuCon', {
        id: phieukn_id
      })
      return rs.data
    },
    async getTB_KHIEUNAI(tab) {
      try {
        this.$v.$reset()
        this.dsIdChon = []
        this.KHIEUNAI = {
          ND_KHIEUNAI: '',
          KY_KN: '',
          KHIEUNAI_ID: null
        }
        this.THUEBAO = {}
        this.NGUOI_KHIEUNAI = {}
        this.$root.showLoading(true)
        if (!tab) {
          await this.getDS_TB_KHIEUNAI_V2({
            TTKN_ID: 5,
            DA_GIAO: 0
          });
          await this.getDS_TB_KHIEUNAI_DAGIAO_V2({
            TTKN_ID: 5,
            DA_GIAO: 1
          });
        } else if (tab==1) {
          await this.getDS_TB_KHIEUNAI_V2({
            TTKN_ID: 5,
            DA_GIAO: 0
          });
        } else {      
          await this.getDS_TB_KHIEUNAI_DAGIAO_V2({
            TTKN_ID: 5,
            DA_GIAO: 1
          });
        }
      if (this.activetab == 1 && this.DS_TB_KHIEUNAI.length > 0) {
        await this.gridDS_TB_rowSelected(this.DS_TB_KHIEUNAI[0])
      } else if (this.activetab == 2 && this.DS_TB_KHIEUNAI_DAGIAO.length > 0) {
        await this.gridDS_TB_rowSelected(this.DS_TB_KHIEUNAI_DAGIAO[0])
      }
      } catch (error) {
      } finally {
        this.$root.showLoading(false)
      }
    },
    async Clear() {
      this.txtMaTB = ''
      this.chkHenTL = false
      this.optXuLy = '0'
      this.KHIEUNAI = {
        NGUOI_KN: '',
        ND_KHIEUNAI: '',
        KY_KN: '',
        KHIEUNAI_ID: null,
        LYDO_TON: ''
      }
      this.NGUOI_KHIEUNAI = {}
      this.daily_id = -1
      this.txtNGUYENNHAN = ''
      this.THUEBAO = {}
      this.dtpNgayCapCMT = null
      await this.getNgay_Cn()
      this.dtpTuNgay = this.Ngay_Cn
      this.dtpDenNgay = moment(this.Ngay_Cn)
        .clone()
        .add(2, 'days')
        .toDate()
      this.dtpNgayTN = this.Ngay_Cn
      this.dtpNgayXL = this.Ngay_Cn
      this.setState({ key: 'DS_TIENGC', value: [] })
      this.setState({ key: 'DS_KHIEUNAI_HT', value: [] })
      this.setState({ key: 'DS_CHITIETNO_THEO_KHOANMUC', value: [] })
      this.setState({ key: 'DSThangGC', value: [] })
      this.setState({ key: 'DSThangApDung', value: [] })
      this.setState({ key: 'DsGC', value: [] })
      this.$v.$reset()
    },
    async getNgay_Cn() {
      try {
        var rps = await this.getSYSDATE({})
        if (rps.data.length > 0) {
          this.Ngay_Cn = rps.data[0].NGAY_CN
        } else {
          this.Ngay_Cn = new Date()
        }
      } catch (error) {
        this.Ngay_Cn = new Date()
      }
    },
    async HienThiTTNo(kieu) {
      try {
        var data = {
          KYHOADON: moment(this.Kyhoadon).format('YYYYMM') + '01',
          MA_TB: this.THUEBAO.MA_TB,
          MA_TT: this.THUEBAO.MA_TT,
          DICHVUVT_ID: this.Selected.DICHVUVT_ID
        }
        var rep = await this.getCHUKYNO_THEOTB(data)
        if (rep.data.length) {
          var vcacchukyno = '('
          var vcacmatb = '('
          for (let index = 0; index < rep.data.length; index++) {
            const element = rep.data[index]
            vcacchukyno = vcacchukyno + element.CHUKYNO + ','
            vcacmatb = vcacmatb + "'" + this.THUEBAO.MA_TB + "'" + ','
          }
          vcacchukyno = vcacchukyno.slice(0, -1)
          vcacmatb = vcacmatb.slice(0, -1)
          vcacmatb = vcacmatb + ')'
          vcacchukyno = vcacchukyno + ')'
          data = {
            MA_TB: this.THUEBAO.MA_TB,
            MA_TT: this.THUEBAO.MA_TT,
            KYCUOC: moment(this.Kyhoadon).format('YYYYMM') + '01'
          }
          var rps = await this.getCHITIETNO_THEO_KHOANMUC(data)
          this.HienThi_ChitietNo_TheoKhoanMuc(rps.data, kieu)
        } else {
          this.setState({ key: 'DS_CHITIETNO_THEO_KHOANMUC', value: [] })
        }
      } catch (error) {}
    },
    DS_LOAI_KN_SelectedValueChanged() {},
    async HienThi_ChitietNo_TheoKhoanMuc(data, kieu) {
      var tmp = [...data]
      var row = {}
      row.TENKHOANMUC = 'Tổng cộng'
      var col1 = 0
      var col2 = 0
      var col3 = 0
      var col4 = 0
      var col5 = 0
      var col6 = 0
      for (let index = 0; index < tmp.length; index++) {
        const e = tmp[index]
        col1 = col1 + e.TONGNO
        col2 = col2 + e.NO_DK
        col3 = col3 + e.NO_PS
        col4 = col4 + e.CON_NO
        col5 = col5 + e.TONG_TRA
        col6 = col6 + e.DIEU_CHINH
      }
      row.TONGNO = currency(col1)
      row.NO_DK = currency(col2)
      row.NO_PS = currency(col3)
      row.CON_NO = currency(col4)
      row.TONG_TRA = currency(col5)
      row.DIEU_CHINH = currency(col6)
      tmp.push(row)
      this.setState({ key: 'DS_CHITIETNO_THEO_KHOANMUC', value: tmp })
    },
    // async HienThi_LichSu_KhieuNai(THUEBAO_ID) {
    //   try {
    //     var data = {
    //       THUEBAO_ID: THUEBAO_ID,
    //       KHIEUNAI_ID: 0
    //     }
    //     await this.getDS_KHIEUNAI_HT(data)
    //   } catch (error) {}
    // },
    // async NAPCOMBO_TGC_KGC(MA_TT) {
    //   try {
    //     if (MA_TT === '') return false
    //     var kyhd = '01' + moment(this.Kyhoadon).format('MMYYYY')
    //     var kynext = moment(this.Kyhoadon)
    //       .clone()
    //       .add(1, 'months')
    //       .toDate()
    //     this.ky_kn = ''
    //     if (this.Button.tsbtnNhapMoi === false) {
    //       kyhd = '01' + moment(this.Kyhoadon).format('MMYYYY')
    //       kynext = moment(this.Kyhoadon)
    //         .clone()
    //         .add(1, 'months')
    //         .toDate()
    //       this.ky_kn = moment(this.Kyhoadon).format('YYYYMM') + '01'
    //     } else {
    //       var ky = this.KHIEUNAI.KY_KN
    //       if (ky !== null && ky !== '' && ky !== undefined) {
    //         this.ky_kn = ky
    //         kyhd = '01' + this.ky_kn.substring(4, 6) + this.ky_kn.substring(0, 4)
    //         kynext = moment(kyhd, 'DDMMYYYY')
    //           .clone()
    //           .add(1, 'months')
    //           .toDate()
    //       } else {
    //         this.ky_kn = moment(this.Kyhoadon).format('YYYYMM') + '01'
    //         kyhd = '01' + moment(this.Kyhoadon).format('MMYYYY')
    //         kynext = moment(this.Kyhoadon)
    //           .clone()
    //           .add(1, 'months')
    //           .toDate()
    //       }
    //     }
    // var kyhd_next = moment(kynext).format('YYYYMM') + '01'
    // var data = {
    //   MA_TT: MA_TT,
    //   MA_TB: this.txtMaTB,
    //   KYHD: this.ky_kn,
    //   KYHD_NEXT: kyhd_next
    // }
    // var rps = await this.getDS_THANG_GC(data)
    // if (rps.data && rps.data.length > 0) {
    //   this.DS_ThangGC = rps.data
    // } else {
    //   this.DS_ThangGC = []
    // }
    // this.setState({ key: 'DSThangGC', value: this.DS_ThangGC })
    // data = {
    //   VKYHD: this.ky_kn,
    //   VKYHD_NEXT: kyhd_next
    // }
    // var rps2 = await this.getKY_APDUNG(data)
    // if (rps2.data && rps2.data.length > 0) {
    //   this.DS_ThangApDung = rps2.data
    // } else {
    //   this.DS_ThangApDung = []
    // }
    // this.setState({ key: 'DSThangApDung', value: this.DS_ThangApDung })
    // } catch (error) {}
    // },
    async NAP_DS_TIENGC(VKHIEUNAI_ID) {
      try {
        this.setState({ key: 'DsGC', value: [] })
        var data = {
          VKHIEUNAI_ID: VKHIEUNAI_ID === null ? 0 : VKHIEUNAI_ID
        }
        var rps = await this.getDULIEU_TIENGC(data)
      } catch (error) {}
    },
    async Lay_chudekn_theo_lhtb_plkh_v2(LOAI_ID, LOAITB_ID, THUEBAO_ID) {
      let data = {
        LOAI_ID,
        LOAITB_ID,
        THUEBAO_ID
      }
      await this.getLAY_CHUDEKN_THEO_LHTB_PLKH_V2(data)
    },
    async HienThiTTThueBao(THUEBAO, kieu, index) {
      try {
        this.THUEBAO = { ...THUEBAO }
        // this.Selected.DOITUONG_ID = this.THUEBAO.DOITUONG_ID
        // this.Selected.DICHVUVT_ID = this.THUEBAO.DICHVUVT_ID
        // this.Selected.LOAITB_ID = this.THUEBAO.LOAITB_ID
        // if ('DAILY_ID' in this.THUEBAO) {
        //   if (this.THUEBAO.DAILY_ID !== null) {
        //     this.daily_id = Number(this.THUEBAO.DAILY_ID)
        //   } else {
        //     this.daily_id = 0
        //   }
        // }
        //await this.NAPCOMBO_TGC_KGC(this.THUEBAO.MA_TT)
        //await this.NAP_DS_TIENGC(this.KHIEUNAI.KHIEUNAI_ID)
        // var response = await this.getLAY_DS_DBKH_THEO_KHID({ KHACHHANG_ID: this.THUEBAO.KHACHHANG_ID })
        // if (response.data.length > 0) {
        //   this.Selected.LOAIKH_ID = response.data[0].LOAIKH_ID
        //   this.THUEBAO.MA_KH = response.data[0].MA_KH
        //   let PHANLOAI_KH = await this.MAP_ID(
        //     'TEN_PLKH',
        //     'CSS',
        //     'PHANLOAI_KH',
        //     'PHANLOAIKH_ID',
        //     response.data[0].PHANLOAIKH_ID
        //   )
        //   this.THUEBAO.PHANLOAI_KH = PHANLOAI_KH
        //   if (kieu === 1) {
        //     this.NGUOI_KHIEUNAI.NGUOI_KN = response.data[0].TEN_KH
        //     this.NGUOI_KHIEUNAI.DIENTHOAI_LH = response.data[0].SO_DT
        //     this.NGUOI_KHIEUNAI.DIACHI_LH = response.data[0].DIACHI_KH
        //     this.KHIEUNAI.KY_KN = ''
        //     this.KHIEUNAI.ND_KHIEUNAI = ''
        //     this.KHIEUNAI.LYDO_TON = ''
        //     if (response.data[0].LOAIGT_ID !== null) {
        //       if (response.data[0].LOAIGT_ID === 1) {
        //         this.NGUOI_KHIEUNAI.CMT = response.data[0].SO_GT
        //         this.NGUOI_KHIEUNAI.NGAYCAP = response.data[0].NGAYCAP
        //         this.NGUOI_KHIEUNAI.NOICAP = response.data[0].NOICAP
        //         this.NGUOI_KHIEUNAI.EMAIL = response.data[0].EMAIL
        //       } else {
        //         this.NGUOI_KHIEUNAI.CMT = null
        //         this.NGUOI_KHIEUNAI.NGAYCAP = null
        //         this.NGUOI_KHIEUNAI.NOICAP = null
        //         this.NGUOI_KHIEUNAI.EMAIL = null
        //       }
        //     }
        //   }
        // }
        // if (kieu === 1) {
        //   await this.Lay_chudekn_theo_lhtb_plkh_v2(
        //     this.Selected.LOAI_ID,
        //     this.Selected.LOAITB_ID,
        //     this.THUEBAO.THUEBAO_ID
        //   )
        // }
        await this.HienThiTTNo(1)
        //await this.HienThi_LichSu_KhieuNai(this.THUEBAO.THUEBAO_ID)
      } catch (error) {}
    },
    isEmptyObj(Obj) {
      var isEmptyObj = !Object.keys(Obj).length
      return isEmptyObj
    },
    async KiemTraDuLieu() {
      if (this.Button.tsbtnNhapMoi === true) {
        if (this.isEmptyObj(this.KHIEUNAI) || this.DS_TB_KHIEUNAI.length === 0) {
          this.loading(false)
          await this.$alert('Hãy chọn thuê bao trên lưới danh sách khiếu nại để cập nhật thông tin !', 'Thông báo', {
            confirmButtonText: 'Đóng',
            callback: (action) => {
              return false
            }
          })
        }
      }
      this.$v.$touch()
      if (this.$v.$invalid && !this.$v.NGUOI_KHIEUNAI.NGUOI_KN.required) {
        this.$root.$toast.error('Bạn chưa nhập người khiếu nại !')
        this.$refs.NGUOI_KN.focus()
        this.loading(false)
        return false
      }

      if (this.$v.$invalid && !this.$v.NGUOI_KHIEUNAI.DIENTHOAI_LH.required) {
        this.$root.$toast.error('Bạn chưa nhập điện thoại liên hệ !')
        this.$refs.DIENTHOAI_LH.focus()
        this.loading(false)
        return false
      }

      //   if (this.$v.$invalid && !this.$v.NGUOI_KHIEUNAI.SO_GT.maxLength) {
      //     this.$root.$toast.error('Chứng minh thư không hợp lệ !')
      //     this.$refs.CMT.focus()
      //     this.loading(false)
      //     return false
      //   }

      if (this.$v.$invalid && !this.$v.NGUOI_KHIEUNAI.DIENTHOAI_LH.numeric) {
        this.$root.$toast.error('Điện thoại liên hệ phải là kiểu số !')
        this.$refs.DIENTHOAI_LH.focus()
        this.loading(false)
        return false
      }

      if (!(this.NGUOI_KHIEUNAI.EMAIL == null || this.NGUOI_KHIEUNAI.EMAIL == undefined)) {
        let mailformat = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/
        if (!this.NGUOI_KHIEUNAI.EMAIL.match(mailformat) && this.NGUOI_KHIEUNAI.EMAIL.length > 0) {
          this.$toast.error('Email không hợp lệ!')
          this.$refs.EMAIL.focus()
          this.loading(false)
          return false
        }
      }

      // if (this.$v.$invalid && !this.$v.KHIEUNAI.EMAIL.email) {
      //   this.$root.$toast.error('Định dạng email chưa đúng !')
      //   this.$refs.EMAIL.focus()
      //   this.loading(false)
      //   return false
      // }

      if (this.$v.$invalid && !this.$v.KHIEUNAI.ND_KHIEUNAI.required) {
        this.$root.$toast.error('Bạn chưa nhập nội dung khiếu nại !')
        this.$refs.ND_KHIEUNAI.focus()
        this.loading(false)
        return false
      }

      if (this.Selected.CHUDEKN_ID === '' || this.Selected.CHUDEKN_ID === null) {
        this.$root.$toast.error('Chưa có chủ đề khiếu nại !')
        this.loading(false)
        return false
      }

      if (this.Selected.LOAI_ID !== 5) {
        var ds = await this.Lay_thongtin_kn_theo_ma_tb(this.txtMaTB)
        if (ds.length < 1) {
          this.$root.$toast.error('Không tìm thấy thông tin thuê bao : ' + this.txtMaTB)
          this.loading(false)
          return false
        } else {
          if (this.Button.tsbtnNhapMoi === true) {
            if (this.DS_TB_KHIEUNAI.length === 0 || this.isEmptyObj(this.KHIEUNAI)) {
              this.$root.$toast.error(
                'Bạn không thể cập nhật dữ liệu do thuê bao : ' + this.txtMaTB + ' chưa có khiếu nại'
              )
              this.loading(false)
              return false
            } else {
              if (!this.isEmptyObj(this.KHIEUNAI)) {
                if (this.KHIEUNAI.THUEBAO_ID !== ds[0].THUEBAO_ID) {
                  this.$root.$toast.error(
                    "Bạn không thể cập nhật thuê bao '" +
                      this.txtMaTB +
                      "' cho khiếu nại '" +
                      this.KHIEUNAI.MA_KN +
                      "' !"
                  )
                  this.loading(false)
                  return false
                }
              }
            }
          }
        }
        if (this.Button.tsbtnNhapMoi === false) {
          let ds = await this.KIEMTRA_TT_MaTB(this.THUEBAO.THUEBAO_ID)
          if (ds.length > 0) {
            var chk
            this.loading(false)
            await this.$confirm('Thuê bao này đã được tiếp nhận khiếu nại. Bạn có muốn thêm mới không ?', 'Thông báo', {
              confirmButtonText: 'Có',
              cancelButtonText: 'Không',
              type: 'warning'
            })
              .then(() => {
                chk = true
                this.loading(true)
              })
              .catch(() => {
                return false
              })
            if (!chk) return false
          }
        }
      } else {
        if (
          this.NGUOI_KHIEUNAI.DIACHI_LH === '' ||
          this.NGUOI_KHIEUNAI.DIACHI_LH === null ||
          this.NGUOI_KHIEUNAI.DIACHI_LH === undefined
        ) {
          this.$root.$toast.error('Hãy nhập địa chỉ liên hệ !')
          this.$refs.DIACHI_LH.focus()
          this.loading(false)
          return false
        }
      }
      if (this.optXuLy === '1') {
        if (this.KETQUA_KHIEUNAI.NOIDUNG_GQ === '' || this.KETQUA_KHIEUNAI.NOIDUNG_GQ === null) {
          this.$root.$toast.error('Bạn phải nhập nội dung xử lý !')
          this.$refs.txtNoiDungXL.focus()
          this.loading(false)
          return false
        }
        if (this.KETQUA_KHIEUNAI.ND_PHUONGAN_XULY === '' || this.KETQUA_KHIEUNAI.ND_PHUONGAN_XULY === null) {
          this.$root.$toast.error('Bạn phải nhập nội dung xử lý !')
          this.$refs.txtNoiDungXL.focus()
          this.loading(false)
          return false
        }
      }
      return true
    },
    async KIEMTRA_TT_MaTB(THUEBAO_ID) {
      try {
        var data = {
          THUEBAO_ID: THUEBAO_ID
        }
        var rps = await this.getKIEMTRA_TT_MaTB(data)
        return rps.data
      } catch (error) {
        return []
      }
    },
    async Lay_thongtin_kn_theo_ma_tb(txtMaTB) {
      try {
        var data = {
          MA_TB: txtMaTB
        }
        var rps = await this.getLay_thongtin_kn_theo_ma_tb(data)
        return rps.data
      } catch (error) {
        return []
      }
    },
    async TaoMaKN() {
      var data = {
        NAM: ''
      }
      var rps = await this.getTaoMaKN(data)
      if (rps.data.length > 0) {
        this.KHIEUNAI.MA_KN = rps.data[0].MA_KN
      }
    },
    focusRowThemNo: function(value) {
      let f = this.DsGC.find((x) => x.ID === '-1')
      if (f != null) {
        let t = { ...f }
        t.FOCUSED = value
        this.setDataGC(t)
        let tmp = this.DsGC.filter((item) => {
          return item.ID != null
        })
        this.setState({ key: 'DsGC', value: tmp })
      }
    },
    async TaoDuLieuAo(themmoi) {
      this.dsKhieuNai = []
      let row = {}
      if (themmoi) {
        await this.TaoMaKN()
        row.KY_KN = moment(this.Kyhoadon).format('YYYYMM') + '01'
        await this.getNgay_Cn()
        this.dtpNgayTN = this.Ngay_Cn
        this.txtMaTB = await this.SINH_MATB_KHIEUNAI_AO()
        this.THUEBAO.MA_TB = this.txtMaTB
      } else {
        row.KY_KN = this.KHIEUNAI.KY_KN
      }
      row.MA_TB = this.THUEBAO.MA_TB
      row.KHIEUNAI_ID = this.KHIEUNAI.KHIEUNAI_ID !== undefined ? this.KHIEUNAI.KHIEUNAI_ID : null
      row.CONGVAN_XL = this.KHIEUNAI.CONGVAN_XL !== undefined ? this.KHIEUNAI.CONGVAN_XL : null
      row.MA_KN = this.KHIEUNAI.MA_KN
      row.NGAY_TN = moment(this.dtpNgayTN).format('DD/MM/YYYY H:mm:ss')
      row.NGUOI_KN = this.KHIEUNAI.NGUOI_KN
      row.DIENTHOAI_LH = this.KHIEUNAI.DIENTHOAI_LH !== undefined ? this.KHIEUNAI.DIENTHOAI_LH : null
      row.THUEBAO_ID = 0
      row.THANHTOAN_ID = 0
      row.DICHVUVT_ID = 0
      row.LOAITB_ID = 0
      row.TTKN_ID = 2
      row.DONVI_ID = this.$root.token.getDonViID()
      row.NHANVIEN_ID = this.$root.token.getNhanVienID()
      row.CHUDEKN_ID = this.Selected.CHUDEKN_ID
      row.ND_KHIEUNAI = this.KHIEUNAI.ND_KHIEUNAI !== undefined ? this.KHIEUNAI.ND_KHIEUNAI : null
      row.QUYTRINH_ID = this.quytrinh_id
      if (this.optXuLy === '0') {
        row.NOIDUNG_GQ = ''
      } else {
        row.NOIDUNG_GQ = this.KHIEUNAI.NOIDUNG_GQ !== undefined ? this.KHIEUNAI.NOIDUNG_GQ : null
      }
      if (this.chkHenTL) {
        row.HENGQ_TU = moment(this.dtpTuNgay).format('DD/MM/YYYY')
        row.HENGQ_DEN = moment(this.dtpDenNgay).format('DD/MM/YYYY')
      } else {
        row.HENGQ_TU = null
        row.HENGQ_DEN = null
      }
      row.CMT = this.KHIEUNAI.CMT !== undefined ? this.KHIEUNAI.CMT : null
      row.EMAIL = this.KHIEUNAI.EMAIL !== undefined ? this.KHIEUNAI.EMAIL : null
      row.NGAYCAP = this.dtpNgayCapCMT ? moment(this.dtpNgayCapCMT).format('DD/MM/YYYY') : null
      row.NGAY_BN = this.dtpNgayBN ? moment(this.dtpNgayBN).format('DD/MM/YYYY H:mm') : null
      row.NOICAP = this.KHIEUNAI.NOICAP !== undefined ? this.KHIEUNAI.NOICAP : null
      row.DIACHI_LH = this.KHIEUNAI.DIACHI_LH !== undefined ? this.KHIEUNAI.DIACHI_LH : null
      row.HTKN_ID = this.Selected.HTKN_ID
      row.NGUYENNHAN_ID = this.Selected.NGUYENNHAN_ID
      row.PAXL_ID = this.Selected.PAXL_ID
      row.HAILONG = this.Selected.HAILONG
      row.HTTL_ID = this.Selected.HTTL_ID
      this.dsKhieuNai.push(row)
      this.dskhieunai_dv = []

      let data = {
        QUYTRINH_ID: this.quytrinh_id
      }
      await this.getHUONGGIAO_LDV(data)
      for (let index = 0; index < this.DS_HUONGGIAO_LDV.length; index++) {
        let e = this.DS_HUONGGIAO_LDV[index]
        if (parseInt(e.LOAIDV_DICH_ID) === this.LOAI_DV.TRAM_VT) {
          let ds = await this.LAY_DONVI_THEO_LOAIDV_DIACHI_KHIEUNAI(
            this.LOAI_DV.TRAM_VT,
            this.DiaDanh.Phuong_id,
            this.DiaDanh.Pho_id,
            this.DiaDanh.Ap_id,
            this.DiaDanh.Khu_id,
            this.DiaDanh.DacDiem_id
          )
          if (ds.length > 0) {
            let rowDV = {}
            rowDV.KHIEUNAI_ID = this.KHIEUNAI.KHIEUNAI_ID
            rowDV.LOAIDV_ID = this.LOAI_DV.TRAM_VT
            rowDV.DONVI_ID = ds[0].DONVI_ID
            this.dskhieunai_dv.push(rowDV)
          }
        }
      }
    },
    createID: function() {
      return Math.floor(Math.random() * 10000).toString()
    },
    async SINH_MATB_KHIEUNAI_AO() {
      try {
        var rs = await this.$root.context.post('/web-gqkn/tiep-nhan-khieu-nai/sinh_matb_khieunai_ao')
        if (rs.data.length > 0) {
          return rs.data[0].MATB_KHIEUNAI_AO
        } else {
          return null
        }
      } catch (error) {
        return null
      }
    },
    async LAY_DONVI_THEO_LOAIDV_DIACHI_KHIEUNAI(LOAIDV_ID, PHUONG_ID, PHO_ID, AP_ID, KHU_ID, DACDIEM_ID) {
      try {
        var data = {
          LOAIDV_ID,
          PHUONG_ID,
          PHO_ID,
          AP_ID,
          KHU_ID,
          DACDIEM_ID
        }
        var rs = await this.$root.context.post(
          '/web-gqkn/tiep-nhan-khieu-nai/lay_donvi_theo_loaidv_diachi_khieunai',
          data
        )
        if (rs.data.length > 0) {
          return rs.data
        } else {
          return []
        }
      } catch (error) {
        return []
      }
    },
   
  
    createNoItem: function() {
      return {
        ID: '0',
        FOCUSED: false,
        KHIEUNAI_ID: 0,
        THANGGOC: 0,
        THANG_TD: 0,
        THANG_STD: 0,
        TIEN: 0,
        TIEN_GC: 0,
        VAT_GC: 0,
        ISUPDATE: '0',
        KY_GC: 0,
        THANG_GC: 0
      }
    },
    checkEmptyNumber: function(args) {
      return args === undefined || args == null
    },
    async GetKey(Key, SCHEMA) {
      try {
        var data = {
          VKEYNAME: Key,
          VKEY_SCHEMA: SCHEMA
        }
        var rs = await this.$root.context.post('/web-gqkn/tiep-nhan-khieu-nai/get_key', data)
        if (rs.data.length > 0) {
          return rs.data[0].KEY
        } else {
          return null
        }
      } catch (error) {
        return null
      }
    },
    async TaoDuLieu_DiaChi(themmoi) {
      this.dsDiaChi = []
      this.dsDiaChiKN = []
      var rowDiaChi = {}
      if (themmoi) {
        this.DiaDanh.diachi_id = await this.GetKey('DIACHI_QLTN', 'QLTN')
      }
      rowDiaChi.DIACHI_ID = this.DiaDanh.diachi_id
      rowDiaChi.TINH_ID = this.DiaDanh.Tinh_id
      rowDiaChi.QUAN_ID = this.DiaDanh.Quan_id
      rowDiaChi.PHUONG_ID = this.DiaDanh.Phuong_id
      rowDiaChi.PHO_ID = this.DiaDanh.Pho_id
      rowDiaChi.AP_ID = this.DiaDanh.Ap_id
      rowDiaChi.KHU_ID = this.DiaDanh.Khu_id
      rowDiaChi.DACDIEM_ID = this.DiaDanh.DacDiem_id
      rowDiaChi.SONHA = this.DiaDanh.SoNha
      rowDiaChi.DIACHI = this.NGUOI_KHIEUNAI.DIACHI_LH
      this.dsDiaChi.push(rowDiaChi)
      var rowDiaChiKN = {}
      rowDiaChiKN.DIACHI_ID = this.DiaDanh.diachi_id
      rowDiaChiKN.KHIEUNAI_ID = this.KHIEUNAI.KHIEUNAI_ID
      this.dsDiaChiKN.push(rowDiaChiKN)
    },
    async KiemTraDuLieu_KhoaPhieu() {
      if (this.DS_TB_KHIEUNAI.length === 0) {
        this.$root.$toast.error('Hãy chọn thuê bao trên lưới danh sách khiếu nại để khóa phiếu !')
        this.loading(false)
        return false
      }
      if (this.isEmptyObj(this.KHIEUNAI)) {
        this.$root.$toast.error('Hãy chọn thuê bao trên lưới danh sách khiếu nại để khóa phiếu !')
        this.loading(false)
        return false
      }

      if (this.Selected.NHANVIEN_ID === '' || this.Selected.NHANVIEN_ID === null) {
        this.$root.$toast.error('Bạn chưa nhập người xử lý khiếu nại !')
        this.loading(false)
        return false
      }

      if (this.KHIEUNAI.NOIDUNG_GQ === '') {
        this.$root.$toast.error('Bạn chưa nhập nội dung xử lý khiếu nại !')
        this.loading(false)
        return false
      }

      if (this.Selected.NGUYENNHAN_ID === '' || this.Selected.NGUYENNHAN_ID === null) {
        this.$root.$toast.error('Hãy chọn kết quả lỗi !')
        this.loading(false)
        return false
      }
      if (this.optXuLy === '1') {
        if (this.KETQUA_KHIEUNAI.NOIDUNG_GQ === '' || this.KETQUA_KHIEUNAI.NOIDUNG_GQ === null) {
          this.$root.$toast.error('Bạn phải nhập nội dung xử lý !')
          this.$refs.txtNoiDungXL.focus()
          this.loading(false)
          return false
        }
        if (this.KETQUA_KHIEUNAI.ND_PHUONGAN_XULY === '' || this.KETQUA_KHIEUNAI.ND_PHUONGAN_XULY === null) {
          this.$root.$toast.error('Bạn phải nhập nội dung xử lý !')
          this.$refs.txtNoiDungXL.focus()
          this.loading(false)
          return false
        }
      }
      let vhinhthuckn_id = await this.MAP_ID('HTKN_ID', 'QLTN', 'KHIEUNAI', 'KHIEUNAI_ID', this.KHIEUNAI.KHIEUNAI_ID)
      if (vhinhthuckn_id === this.HINHTHUC_KN.VAN_BAN) {
        let hoso = await this.MAP_ID('HOSOKN_ID', 'QLTN', 'HOSO_KN', 'KHIEUNAI_ID', this.KHIEUNAI.KHIEUNAI_ID)
        if (hoso.toString() === '-1') {
          this.$root.$toast.error('Chưa Upload file văn bản. Bạn không thể khóa phiếu !')
          this.loading(false)
          return false
        }
      }
      return true
    },
    async MAP_ID(P_MAP, P_SCHEMA, P_TABLENAME, P_PRIMARY, P_VALUE) {
      try {
        let data = {
          LOAI: P_TABLENAME,
          KHOA: P_PRIMARY,
          GIATRI: P_VALUE
        }
        let rs
        if (P_SCHEMA === 'QLTN') {
          rs = await this.$root.context.post('/web-cabman/ban-do-mang-cap/thong-tin-chi-tiet-v2', data)
        } else {
          rs = await this.$root.context.post('/web-cabman/ban-do-mang-cap/thong-tin-chi-tiet', data)
        }
        if (rs.data.length > 0) {
          var obj = rs.data[0]
          return obj[P_MAP]
        } else return null
      } catch (error) {
        return null
      }
    },
    async tsbtnKhoaPhieu_Click() {
      
    },
    async LAY_DS_THAMSO_MD() {
      try {
        var rps = await this.$root.context.get('/web-gqkn/tiep-nhan-khieu-nai/lay_ds_thamso_md')
        return rps.data
      } catch (error) {
        return []
      }
    },
    async LAY_DS_THAMSO_MD2(data) {
      try {
        var rps = await this.$root.context.post('/web-gqkn/tiep-nhan-khieu-nai/lay_ds_thamso_md2', data)
        return rps.data
      } catch (error) {
        return []
      }
    },
    async CapNhatGiamCuoc(khieunai_id) {},
    async txtMaTB_KeyPress() {
      this.$v.$reset()
      if (this.txtMaTB.trim() === '') {
        this.$refs.txtMaTB.focus()
        return
      }
      try {
        this.$root.showLoading(true)
        await this.getNgay_Cn()
        this.dtpNgayBN = moment(this.Ngay_Cn)
          .clone()
          .add(1, 'hours')
          .toDate()
        let data = {
          MA_TB: this.txtMaTB.trim(),
          DICHVUVT_ID: 0
        }
        await this.getTTThueBao(data)
        if (this.DS_TTTHUEBAO.length > 0) {
          if (this.DS_TTTHUEBAO.length === 1) {
            await this.HienThiTTThueBao(this.DS_TTTHUEBAO[0], 1, 0)
            this.Button.tsbtnNhapMoi = false
            this.Button.tsbtnGhiLai = true
            this.Button.tsbtnHuyBo = true
          } else {
            this.dsThueBao = [...this.DS_TTTHUEBAO]
            let rs = await this.$refs.formTraCuuTB.showModal()
            let index = 0
            if (rs.index !== -1) {
              index = rs.index
              await this.HienThiTTThueBao(this.DS_TTTHUEBAO[index], 1, index)
              this.Button.tsbtnNhapMoi = false
              this.Button.tsbtnGhiLai = true
              this.Button.tsbtnHuyBo = true
            } else {
              return
            }
          }
        } else {
          let data = {
            KYHOADON: moment(this.Kyhoadon).format('YYYYMM') + '01',
            MA_TT: this.txtMaTB.trim()
          }
          await this.getTTThueBaoMaTT(data)
          if (this.DS_TTTHUEBAO && this.DS_TTTHUEBAO.length > 0) {
            if (this.DS_TTTHUEBAO.length === 1) {
              await this.HienThiTTThueBao(this.DS_TTTHUEBAO[0], 1, 0)
              this.Button.tsbtnNhapMoi = false
              this.Button.tsbtnGhiLai = true
              this.Button.tsbtnHuyBo = true
            } else {
              this.dsThueBao = [...this.DS_TTTHUEBAO]
              let index = 0
              let rs = await this.$refs.formTraCuuTB.showModal()
              if (rs.index !== -1) {
                index = rs.index
                await this.HienThiTTThueBao(this.DS_TTTHUEBAO[index], 1, index)
                this.Button.tsbtnNhapMoi = false
                this.Button.tsbtnGhiLai = true
                this.Button.tsbtnHuyBo = true
              }
            }
          } else {
            await this.Clear()
            this.$root.showLoading(false)
            await this.$alert('Không tìm thấy thông tin thuê bao ' + this.txtMaTB.trim(), 'Thông báo', {
              confirmButtonText: 'Đóng',
              callback: (action) => {}
            })
          }
        }
      } catch (error) {
      } finally {
        this.$root.showLoading(false)
      }
    },
    KiemTra_DuLieu_TienGC(DsGC) {
      for (var i = 0; i < DsGC.length; i++) {
        let row = DsGC[i]
        let thang_gc = row.THANG_GC
        let tien = parseInt(row.TIEN)
        let tien_gc = parseInt(row.TIEN_GC)
        let ky_gc = row.KY_GC
        if (tien_gc !== null && tien_gc !== '') {
          if (tien_gc <= 0) {
            return 'Giá trị tiền phải >=0'
          }
        } else {
          return 'Giá trị tiền không đúng'
        }
        if (tien !== null && tien !== '') {
          if (tien <= 0) {
            return 'Giá trị tiền giảm cước phải >=0'
          }
        } else {
          return 'Giá trị tiền giảm cước không đúng'
        }
        if (ky_gc !== null && ky_gc !== '') {
          if (thang_gc !== null && thang_gc !== '') {
            if (this.Button.tsbtnNhapMoi === false) {
            } else {
            }
          }
        } else {
          return 'Bạn chưa chọn kỳ áp dụng giảm cước'
        }
        if (thang_gc !== null && thang_gc !== '') {
        } else {
          return 'Bạn chưa nhập tháng giảm trừ'
        }
      }
      var counts = []
      var chk = false
      for (var j = 0; j < DsGC.length; j++) {
        let row = DsGC[j]
        if (counts[row.THANG_GC] === undefined) {
          counts[row.THANG_GC] = 1
        } else {
          chk = true
        }
      }
      if (chk) {
        return 'Tháng giảm cước đã có, Bạn hãy chọn tháng giám cước khác.'
      }
      return null
    },
    async TAODULIEU_TIENGC(VKHIEUNAI_ID) {
      try {
        var data = ''
        if (this.DsGC.length === 0) {
          data = ''
        } else {
          const newArray = [...this.DsGC].map((item) => {
            delete item.FOCUSED
            delete item.ISUPDATE
            return item
          })
          data = JSON.stringify(newArray.filter((x) => x.ID !== '-1'))
        }
        var dt = {
          VKHIEUNAI_ID: VKHIEUNAI_ID === null ? 0 : VKHIEUNAI_ID,
          VDSTIENGC: data
        }
        var rps = await this.getTAODULIEU_TIENGC(dt)
      } catch (error) {}
    },
    async TAODULIEU_TIENGC_V2() {
      try {
        var data = []
        if (this.DsGC.length === 0) {
          data = []
        } else {
          const newArray = [...this.DsGC].map((item) => {
            delete item.FOCUSED
            delete item.ISUPDATE
            return item
          })
          data = newArray.filter((x) => x.ID !== '-1')
        }
        return data
      } catch (error) {
        console.log('🚀 ~ file: TiepNhanKN.vue:1803 ~ TAODULIEU_TIENGC_V2 ~ error:', error)
        return []
      }
    },
    lnkKhieunai_BS_LinkClicked() {
      try {
        alert('Tham chieu den form frmKhieuNai_BS')
      } catch (error) {}
    },
    async Insert_KNBS(khieunai_bs, khieunai_id) {
      try {
        if (khieunai_bs.length === 0) return
        let data = {
          KHIEUNAI_ID: khieunai_id,
          KY_TT: khieunai_bs[0].KY_TT,
          TIENTRA: khieunai_bs[0].TIENTRA,
          HENTRA: khieunai_bs[0].HENTRA,
          GOICUOC: khieunai_bs[0].GOICUOC
        }
        let rps = await this.$root.context.post('/web-gqkn/tiep-nhan-khieu-nai/insert_knbs', data)
      } catch (error) {}
    },
    async Insert_V2(DSKHIEUNAI, DSKHIEUNAI_DV, HUONGGIAO_ID, DSDIACHI, DSDIACHIKN) {
      try {
        var DsGC = this.DsGC.filter((x) => x.ID !== '-1')
        if (DsGC.length > 0) {
          let kq = this.KiemTra_DuLieu_TienGC(DsGC)
          if (kq !== null) {
            this.$root.$toast.error(kq)
            return
          }
        }
        var data = {
          HUONGGIAO_ID,
          DSKHIEUNAI,
          DSKHIEUNAI_DV,
          DSDIACHI,
          DSDIACHIKN
        }
        var rps = await this.setNHAP_KHIEUNAI_Insert_V2(data)
        if (rps.data && rps.data.length > 0) {
          if (rps.data[0].RESULT === 0) {
            this.loading(false)
            this.$root.$toast.error('Thêm mới khiếu nại không thành công !')
            return
          }
          var KHIEUNAI_ID = rps.data[0].KHIEUNAI_ID
          if (this.KHIEUNAI_BS.length > 0) {
            this.KHIEUNAI_BS[0].KHIEUNAI_ID = KHIEUNAI_ID
          }
          await this.Insert_KNBS(this.KHIEUNAI_BS, KHIEUNAI_ID)
          if (this.optXuLy === '0') {
            data = {
              PHIEUKN_ID: 0,
              KHIEUNAI_ID: KHIEUNAI_ID,
              LYDO_TON: this.KHIEUNAI.LYDO_TON === null ? '' : this.KHIEUNAI.LYDO_TON
            }
            rps = await this.setUPDATE_LYDO_KHONGXL_DUOC(data)
            data = {
              KHIEUNAI_ID: KHIEUNAI_ID,
              TIEN_GC_DT: 0,
              VAT_DT: 0,
              TIEN_GC_CP: 0,
              VAT_CP: 0
            }
            rps = await this.setCAPNHAT_GIAMCUOC(data)
          } else {
            await this.TAODULIEU_TIENGC(KHIEUNAI_ID)
            data = {
              PHIEUKN_ID: 0,
              KHIEUNAI_ID: KHIEUNAI_ID,
              LYDO_TON: ''
            }
            rps = await this.setUPDATE_LYDO_KHONGXL_DUOC(data)
            data = {
              KHIEUNAI_ID: KHIEUNAI_ID,
              TIEN_GC_DT: 0,
              VAT_DT: 0,
              TIEN_GC_CP: 0,
              VAT_CP: 0
            }
            rps = await this.setCAPNHAT_GIAMCUOC(data)
          }
          this.loading(false)
          this.$root.$toast.success('Thêm mới khiếu nại thành công !')
        }
      } catch (error) {
        console.log('🚀 ~ file: TiepNhanKN.vue:1794 ~ Insert_V2 ~ error:', error)
        this.$root.$toast.error('Lỗi thêm mới khiếu nại !!!' + '\r\n' + error.response.data.message_detail)
      }
    },
    async Insert_V4(DSKHIEUNAI, DSKHIEUNAI_DV, HUONGGIAO_ID, DSDIACHI, DSDIACHIKN) {
      try {
        var DsGC = this.DsGC.filter((x) => x.ID !== '-1')
        var DSTIENGC = []
        if (DsGC.length > 0) {
          let kq = this.KiemTra_DuLieu_TienGC(DsGC)
          if (kq !== null) {
            this.$root.$toast.error(kq)
            return
          }
        }
        if (this.optXuLy !== '0') {
          DSTIENGC = await this.TAODULIEU_TIENGC_V2()
        }
        var DSKHIEUNAI_BS = JSON.stringify(this.KHIEUNAI_BS.length > 0 ? this.KHIEUNAI_BS[0] : [])
        var data = {
          HUONGGIAO_ID,
          DSKHIEUNAI,
          DSKHIEUNAI_DV,
          DSDIACHI,
          DSDIACHIKN,
          DSKHIEUNAI_BS,
          XULY: Number(this.optXuLy),
          LYDO_TON: this.KHIEUNAI.LYDO_TON === null ? '' : this.KHIEUNAI.LYDO_TON,
          DSTIENGC: JSON.stringify(DSTIENGC)
        }
        var rps = await this.setNHAP_KHIEUNAI_Insert_V4(data)
        if (rps.data && rps.data.length > 0) {
          if (rps.data[0].RESULT === 0) {
            this.loading(false)
            this.$root.$toast.error('Thêm mới khiếu nại không thành công !')
            return
          }
          this.$root.$toast.success('Thêm mới khiếu nại thành công !')
        } else {
          this.$root.$toast.error('Lỗi thêm mới khiếu nại !!!')
        }
        this.loading(false)
      } catch (error) {
        console.log('🚀 ~ file: TiepNhanKN.vue:1937 ~ Insert_V4 ~ error:', error)
        this.$root.$toast.error('Lỗi thêm mới khiếu nại !!!' + '\r\n' + error.response.data.message_detail)
      }
    },
    DisabledAllButton() {
      this.Button = {
        tsbtnNhapMoi: false,
        tsbtnGhiLai: false,
        tsbtnXoa: false,
        tsbtnHuyBo: false,
        tsbtnKhoaPhieu: false,
        tsbtnGiaoPhieu: false
      }
    },
    async Update_V2(dsKhieuNai, dskhieunai_dv, huonggiao_tn, dsDiaChi, dsDiaChiKN) {
      try {
        var DsGC = this.DsGC.filter((x) => x.ID !== '-1')
        if (DsGC.length > 0) {
          let kq = this.KiemTra_DuLieu_TienGC(DsGC)
          if (kq !== null) {
            this.$root.$toast.error(kq)
            return
          }
        }
        var data = {
          HUONGGIAO_ID: huonggiao_tn,
          DSKHIEUNAI: dsKhieuNai,
          DSKHIEUNAI_DV: dskhieunai_dv,
          DSDIACHI: dsDiaChi,
          DSDIACHIKN: dsDiaChiKN
        }
        var rps = await this.setSUA_KHIEUNAI_Update_V2(data)
        if (rps.data && rps.data.length > 0) {
          if (rps.data[0].RESULT === 0) {
            this.loading(false)
            this.$root.$toast.error('Cập nhật khiếu nại không thành công !')
            return
          }
          var KHIEUNAI_ID = rps.data[0].KHIEUNAI_ID
          if (KHIEUNAI_ID === null) {
            return false
          }
          await this.Insert_KNBS(this.KHIEUNAI_BS, KHIEUNAI_ID)
          if (this.optXuLy === '0') {
            data = {
              PHIEUKN_ID: 0,
              KHIEUNAI_ID: KHIEUNAI_ID,
              LYDO_TON: this.KHIEUNAI.LYDO_TON === null ? '' : this.KHIEUNAI.LYDO_TON
            }
            rps = await this.setUPDATE_LYDO_KHONGXL_DUOC(data)
            data = {
              KHIEUNAI_ID: KHIEUNAI_ID,
              TIEN_GC_DT: 0,
              VAT_DT: 0,
              TIEN_GC_CP: 0,
              VAT_CP: 0
            }
            rps = await this.setCAPNHAT_GIAMCUOC(data)
          } else {
            await this.TAODULIEU_TIENGC(KHIEUNAI_ID)
            data = {
              PHIEUKN_ID: 0,
              KHIEUNAI_ID: KHIEUNAI_ID,
              LYDO_TON: ''
            }
            rps = await this.setUPDATE_LYDO_KHONGXL_DUOC(data)
            data = {
              KHIEUNAI_ID: KHIEUNAI_ID,
              TIEN_GC_DT: 0,
              VAT_DT: 0,
              TIEN_GC_CP: 0,
              VAT_CP: 0
            }
            rps = await this.setCAPNHAT_GIAMCUOC(data)
          }
          this.loading(false)
          this.$root.$toast.success('Cập nhật khiếu nại thành công !')
        }
      } catch (error) {}
    },
    gridDS_TB_KHIEUNAI_rowClicked(rowIndex, rowData) {},
    async kiemtra_tb_dacbiet_khieunai(vma_tb, vchudekn_id) {
      try {
        let rps = await this.$root.context.post('/web-gqkn/tiep-nhan-khieu-nai/Kiemtra_tb_dacbiet_khieunai', {
          vma_tb,
          vchudekn_id
        })
        if (rps.data.length > 0) {
          return rps.data[0].MSG
        } else {
          return 'ok'
        }
      } catch (error) {
        return 'ok'
      }
    },
    async Capnhat_khdb_theo_khieunai_id(khieunai_id) {
      try {
        let rps = await this.$root.context.post('/web-gqkn/tiep-nhan-khieu-nai/capnhat_khdb_theo_khieunai_id', {
          KHIEUNAI_ID: khieunai_id
        })
      } catch (error) {}
    },
    toJSON(arr) {
      if (arr.length === 0) {
        return null
      }
      var columnsIn = arr[0]
      var columns = []
      for (var key in columnsIn) {
        columns.push(key)
      }
      let tmp = [columns, ...arr]
      return JSON.stringify(tmp)
    },
    async tsbtnGhiLai_Click() { 
      
      if (this.Selected.NGUYENNHAN_ID==null ||  this.Selected.NGUYENNHAN_ID==undefined)
        {          
          this.$toast.error('Bạn chưa chọn nguyên nhân lỗi')
          return;
        }   
        if (this.KETQUA_KHIEUNAI.ND_PHUONGAN_XULY==null ||  this.KETQUA_KHIEUNAI.ND_PHUONGAN_XULY==undefined)
        {         
          this.$toast.error('Bạn chưa nhập phương án xử lý')
          return;
        }   
        if (this.KETQUA_KHIEUNAI.ND_TRALOI==null ||  this.KETQUA_KHIEUNAI.ND_TRALOI==undefined)
        {
          this.$toast.error('Bạn chưa nhập nội dung trả lời')
          return;
        }   
        
      //if (!this.Button.tsbtnGhiLai) return false
      try {
        this.loading(true)  
        if (await this.capNhatKetQuaXuLy())
          this.$toast.success('Cập nhật kết quả xử lý thành công')
        else
          this.$toast.error('Có lỗi xảy ra')
        this.loading(false)
      } catch (error) {        
        this.loading(false)
      }
    },
    async onChange_DICHVUVT(event) {},
    async tsbtnNhapMoi_Click() {
      if (!this.Button.tsbtnNhapMoi) return false
      this.SetButton(1)
      this.Selected.HTKN_ID = 2
      this.optXuLy = '0'
      await this.getNgay_Cn()
      this.dtpNgayBN = moment(this.Ngay_Cn)
        .clone()
        .add(1, 'hours')
        .toDate()
      this.txtMaTB = ''
    },
    tsbtnHuyBo_Click() {
      if (!this.Button.tsbtnHuyBo) return false
      this.SetButton(2)
      this.Clear()
    },
    tsbtnXoa_Click() {
      if (!this.Button.tsbtnXoa) return false
      try {
        if (this.DS_TB_KHIEUNAI.length === 0) {
          this.$root.$toast.error('Danh sách khiếu nại trống. Bạn không thể thực hiện xóa khiếu nại !')
        } else {
          this.$confirm(`Bạn thật sự muốn xóa khiếu nại không ?`, 'Thông báo', {
            confirmButtonText: 'Có',
            cancelButtonText: 'Không',
            type: 'info'
          })
            .then(async () => {
              try {
                if (this.dsIdChon.length === 0) {
                  this.$root.$toast.error('Bạn chưa chọn khiếu nại để xóa !')
                  return
                }
                this.loading(true)
                for (let index = 0; index < this.dsIdChon.length; index++) {
                  const KHIEUNAI_ID = this.dsIdChon[index]
                  await this.getXOA_KHIEUNAI({ KHIEUNAI_ID: KHIEUNAI_ID })
                }
                this.loading(false)
                this.$root.$toast.success('Xóa khiếu nại thành công !')
                this.getTB_KHIEUNAI(this.activetab)
              } catch (e) {
                this.loading(false)
                this.$root.$toast.error(e.message)
              }
            })
            .catch(() => {
              this.loading(false)
            })
        }
      } catch (error) {
        this.loading(false)
        this.$root.$toast.error(error)
      }
    },
    async cboChuDeKN_SelectedChanged(data) {
      console.log('🚀 ~ file: TiepNhanKN.vue:2011 ~ cboChuDeKN_SelectedChanged ~ data', data)
      try {
        if (data !== null) {
          var dsKtra = await this.Kiemtra_chudekn_knbs(parseInt(this.Selected.CHUDEKN_ID))
          if (dsKtra.length > 0) {
            if (dsKtra[0].toString() === '1') {
              this.lnkKhieunai_BS.Visible = true
            } else {
              this.lnkKhieunai_BS.Visible = false
            }
          } else {
            this.lnkKhieunai_BS.Visible = false
          }
          
          
          this.$refs.ND_KHIEUNAI.focus()
        }
      } catch (error) {}
    },
    async Kiemtra_chudekn_knbs(CHUDEKN_ID) {
      try {
        var data = {
          CHUDEKN_ID
        }
        var rs = await this.$root.context.post('/web-gqkn/tiep-nhan-khieu-nai/kiemtra_chudekn_knbs', data)
        if (rs.data.length > 0) {
          return rs.data
        } else {
          return []
        }
      } catch (error) {
        return []
      }
    },

    async capNhatKetQuaXuLy() {
      try {
        
        let data = {
            pNguyenNhanLoi:this.Selected.NGUYENNHAN_ID,
            pHTTLId:this.Selected.HTTL_ID,
            pHaiLong:this.Selected.HAILONG,
            pPAXLId:this.Selected.PAXL_ID,
            pNDXuLy:this.KETQUA_KHIEUNAI.ND_PHUONGAN_XULY,
            pNDTraLoi:this.KETQUA_KHIEUNAI.ND_TRALOI,
            pKhieuNaiId:this.KHIEUNAI.KHIEUNAI_ID
              }
              
        var rs = await this.$root.context.post('/web-gqkn/xu-ly-khieu-nai/capnhat-ketqua-xuly', data)
        
        if (rs.error_code=='BSS-00000000' ) {
              return true;
        } else {
          return false;
        }
      } catch (error) {
        return false;
      }
    },
    async HienThiTTDiaChi() {
      if (this.DS_DIACHI.length > 0) {
        this.DiaDanh.diachi_id = parseInt(this.DS_DIACHI[0].DIACHI_ID)
        this.DiaDanh.Tinh_id = parseInt(this.DS_DIACHI[0].TINH_ID)
        this.DiaDanh.Quan_id = parseInt(this.DS_DIACHI[0].QUAN_ID)
        this.DiaDanh.Phuong_id = parseInt(this.DS_DIACHI[0].PHUONG_ID)
        if (this.DS_DIACHI[0].PHO_ID === null) {
          this.DiaDanh.Pho_id = 0
        } else {
          this.DiaDanh.Pho_id = parseInt(this.DS_DIACHI[0].PHO_ID)
        }
        if (this.DS_DIACHI[0].AP_ID === null) {
          this.DiaDanh.Ap_id = 0
        } else {
          this.DiaDanh.Ap_id = parseInt(this.DS_DIACHI[0].AP_ID)
        }
        if (this.DS_DIACHI[0].KHU_ID === null) {
          this.DiaDanh.Khu_id = 0
        } else {
          this.DiaDanh.Khu_id = parseInt(this.DS_DIACHI[0].KHU_ID)
        }
        if (this.DS_DIACHI[0].DACDIEM_ID === null) {
          this.DiaDanh.DacDiem_id = 0
        } else {
          this.DiaDanh.DacDiem_id = parseInt(this.DS_DIACHI[0].DACDIEM_ID)
        }
        this.DiaDanh.SoNha = this.DS_DIACHI[0].SONHA
      } else {
        this.DiaDanh.diachi_id = 0
        this.DiaDanh.Tinh_id = 0
        this.DiaDanh.Quan_id = 0
        this.DiaDanh.Phuong_id = 0
        this.DiaDanh.Pho_id = 0
        this.DiaDanh.Ap_id = 0
        this.DiaDanh.Khu_id = 0
        this.DiaDanh.DacDiem_id = 0
        this.DiaDanh.SoNha = null
      }
    },
    async LAY_DS_DIACHI_THEO_KHIEUNAI_ID(KHIEUNAI_ID) {
      try {
        const DS_DIACHI = await this.getDS_DIACHI_THEO_KHIEUNAI_ID({ VKHIEUNAI_ID: KHIEUNAI_ID })
        this.DS_DIACHI = DS_DIACHI.data
      } catch (error) {
        this.DS_DIACHI = []
      }
    },
    capNhatPAXLDoHL: async function(khieunai_id, paxl_id, hailong) {
      let rs = await this.$root.context.post('/web-gqkn/xu-ly-khieu-nai/capNhatPAXLDoHL', {
        khieunai_id: khieunai_id,
        paxl_id: paxl_id,
        hailong: hailong
      })
      return rs.data
    },
    capNhatKetQuaNoiDung: async function(khieunai_id, ketquakn_id, noidung_gq) {
      let rs = await this.$root.context.post('/web-gqkn/xu-ly-khieu-nai/capNhatKetQuaNoiDung', {
        khieunai_id: khieunai_id,
        ketquakn_id: ketquakn_id,
        noidung_gq: noidung_gq
      })
      return rs.data
    },
    capNhatCongVan: async function(khieunai_id, congvan_xl) {
      let rs = await this.$root.context.post('/web-gqkn/xu-ly-khieu-nai/capNhatCongVan', {
        khieunai_id: khieunai_id,
        congvan_xl: congvan_xl
      })
      return rs.data
    },
    capNhatKHDB: async function(khieunai_id, nhanvien_id) {
      let rs = await this.$root.context.post('/web-gqkn/xu-ly-khieu-nai/capNhatKHDB', {
        khieunai_id: khieunai_id,
        nhanvien_id: nhanvien_id
      })
      return rs.data
    },
    capNhatBaoTonHT: async function(khieunai_id, ngay_gq, nhanvien_gq_id, noidung_gq, nd_ton, donvi_id, ttkn_id) {
      let rs = await this.$root.context.post('/web-gqkn/xu-ly-khieu-nai/capNhatBaoTonHT', {
        khieunai_id: khieunai_id,
        ngay_gq: moment(ngay_gq).format('YYYY-MM-DD HH:mm:ss'),
        nhanvien_gq_id: nhanvien_gq_id,
        noidung_gq: noidung_gq,
        nd_ton: nd_ton,
        donvi_id: donvi_id,
        ttkn_id: ttkn_id
      })
      return rs.data
    },
    abc() {
      if (this.phaiLam('KICHHOAT_TD')) {
        if (this.nhanVien_grid.items.length > 0) {
          if (this.thueBao_grid.selected.NGAY_HT_GP !== '' && this.thueBao_grid.selected.NGAY_HT_GP !== null) {
            this.gv_Params.ngayHT_Checked = true
            this.gv_Params.ngayHT = moment(this.thueBao_grid.selected.NGAY_HT_GP, 'DD/MM/YYYY HH:mm:ss').toDate()

            let trangThai_DB =
              Number(this.pyc_Params.dichVu_Selected) === DichVuVienThong.CO_DINH
                ? TRANGTHAI_DONGBO.DONGBO_SERVICE
                : TRANGTHAI_DONGBO.DONGBO_CM

            if (status === trangThai_DB) {
              if (this.phaiLam('GIAOPHIEU')) {
                this.display_Params.giaoPhieuBtn_Enable = true
                this.display_Params.kichHoatBtn_Enable = false
              } else {
                this.display_Params.hoanCongBtn_Enable = true
                this.display_Params.kichHoatBtn_Enable = false
              }

              if (this.phaiLam('CHECK_OMC')) {
                // Nếu có thao tác kiểm tra OMC thì -> làm
                let kq =
                  this.thueBao_grid.selected.GIAO_OMC !== '' && this.thueBao_grid.selected.GIAO_OMC !== null
                    ? Number(this.thueBao_grid.selected.GIAO_OMC)
                    : -1

                if (kq === -1) {
                  return this.$toast.error('Không lấy được thông tin kiểm tra giao phiếu cho bộ phận tiếp theo')
                } else if (kq === 2) {
                  this.display_Params.hoanCongBtn_Enable = true
                  this.display_Params.kichHoatBtn_Enable = false
                  this.display_Params.giaoPhieuBtn_Enable = false
                  this.display_Params.giaoPhieuBtn_Visible = false
                } else if (kq === 1) {
                  // Giao phiếu
                  this.display_Params.hoanCongBtn_Enable = false
                  this.display_Params.kichHoatBtn_Enable = false
                  this.display_Params.giaoPhieuBtn_Enable = true
                  this.display_Params.giaoPhieuBtn_Visible = true
                } else {
                  return
                }
              }
            } else {
              if (this.phaiLam('CHECK_OMC')) {
                // Nếu có thao tác kiểm tra OMC thì -> làm
                let kq =
                  this.thueBao_grid.selected.GIAO_OMC !== '' && this.thueBao_grid.selected.GIAO_OMC !== null
                    ? Number(this.thueBao_grid.selected.GIAO_OMC)
                    : -1

                if (kq === -1) {
                  return this.$toast.error('Không lấy được thông tin kiểm tra giao phiếu cho bộ phận tiếp theo')
                } else if (kq === 2) {
                  // Gpon
                  this.display_Params.hoanCongBtn_Enable = false
                  this.display_Params.kichHoatBtn_Enable = true
                  this.display_Params.giaoPhieuBtn_Enable = false
                  this.display_Params.giaoPhieuBtn_Visible = false
                } else if (kq === 1) {
                  // Aon
                  // Giao phiếu
                  this.display_Params.hoanCongBtn_Enable = false
                  this.display_Params.kichHoatBtn_Enable = false
                  this.display_Params.giaoPhieuBtn_Enable = true
                  this.display_Params.giaoPhieuBtn_Visible = true
                } else {
                  return
                }
              } else {
                this.display_Params.hoanCongBtn_Enable = false
                this.display_Params.kichHoatBtn_Enable = true
                this.display_Params.giaoPhieuBtn_Enable = false
              }
            }
          } else {
            this.gv_Params.ngayHT_Checked = false
            this.gv_Params.ngayHT = this.gv_Params.today

            this.display_Params.giaoPhieuBtn_Enable = false
            this.display_Params.hoanCongBtn_Enable = false
            this.display_Params.kichHoatBtn_Enable = false
          }
        } else {
          this.display_Params.giaoPhieuBtn_Enable = false
          this.display_Params.hoanCongBtn_Enable = false
          this.display_Params.kichHoatBtn_Enable = false
        }
      } else {
        // Nếu không phải kích hoạt

        // Nếu phải giao phiếu
        if (this.phaiLam('GIAOPHIEU')) {
          if (this.nhanVien_grid.items.length > 0) {
            if (this.thueBao_grid.selected.NGAY_HT_GP !== '' && this.thueBao_grid.selected.NGAY_HT_GP !== null) {
              this.gv_Params.ngayHT_Checked = true
              this.gv_Params.ngayHT = moment(this.thueBao_grid.selected.NGAY_HT_GP, 'DD/MM/YYYY HH:mm:ss').toDate()

              this.display_Params.giaoPhieuBtn_Enable = true
              this.display_Params.hoanCongBtn_Enable = false
              this.display_Params.kichHoatBtn_Enable = false
            } else {
              this.gv_Params.ngayHT_Checked = false
              this.gv_Params.ngayHT = this.gv_Params.today

              this.display_Params.giaoPhieuBtn_Enable = false
              this.display_Params.hoanCongBtn_Enable = false
              this.display_Params.kichHoatBtn_Enable = false
            }
          } else {
            this.display_Params.giaoPhieuBtn_Enable = false
            this.display_Params.hoanCongBtn_Enable = false
            this.display_Params.kichHoatBtn_Enable = false
          }
        } else {
          // Nếu không phải giao phiếu
          // Nếu phải kích hoạt tổng đài
          if (this.phaiLam('HOANCONG')) {
            if (this.nhanVien_grid.items.length > 0) {
              if (this.thueBao_grid.selected.NGAY_HT_GP !== '' && this.thueBao_grid.selected.NGAY_HT_GP !== null) {
                this.gv_Params.ngayHT_Checked = true
                this.gv_Params.ngayHT = moment(this.thueBao_grid.selected.NGAY_HT_GP, 'DD/MM/YYYY HH:mm:ss').toDate()
                this.display_Params.hoanCongBtn_Enable = true
              } else {
                this.gv_Params.ngayHT_Checked = false
                this.gv_Params.ngayHT = this.gv_Params.today
                this.display_Params.hoanCongBtn_Enable = false
              }
            } else {
              this.display_Params.giaoPhieuBtn_Enable = false
              this.display_Params.hoanCongBtn_Enable = false
              this.display_Params.kichHoatBtn_Enable = false
            }
          }
        }
      }
    },
    capNhatHTHS: async function(
      khieunai_id,
      phieukn_id,
      nhanvien_th_id,
      ngay_ht,
      ttkn_id,
      nguyennhan,
      traloi,
      ngay_gq
    ) {
      let rs = await this.$root.context.post('/web-gqkn/xu-ly-khieu-nai/capNhatHTHS', {
        khieunai_id: khieunai_id,
        phieukn_id: phieukn_id,
        nhanvien_th_id: nhanvien_th_id,
        ngay_ht: moment(ngay_ht).format('YYYY-MM-DD HH:mm:ss'),
        ttkn_id: ttkn_id,
        nguyennhan: nguyennhan,
        traloi: traloi,
        ngay_gq: moment(ngay_gq).format('YYYY-MM-DD HH:mm:ss')
      })
      return rs.data
    },
    giaoPhieuKNKhiHC: async function(khieunai_id, phieukn_id, huonggiao_id, donvi_nhan_id) {
      let rs = await this.$root.context.post('/web-gqkn/xu-ly-khieu-nai/giaoPhieuKNKhiHC', {
        khieunai_id: khieunai_id,
        phieukn_id: phieukn_id,
        huonggiao_id: huonggiao_id,
        donvi_giao_id: this.$root.token.getDonViID(),
        donvi_nhan_id: donvi_nhan_id,
        nhanvien_id: this.$root.token.getNhanVienID()
      })
      return rs.data
    },
    capNhatXuLyHT: async function(khieunai_id, macv, nd_ton, ngay_gq, nguyennhan, nhanvien_gq_id, noidung_gq) {
      let rs = await this.$root.context.post('/web-gqkn/xu-ly-khieu-nai/capNhatXuLyHT', {
        khieunai_id: khieunai_id,
        macv: macv,
        nd_ton: nd_ton,
        ngay_gq: moment(ngay_gq).format('YYYY-MM-DD HH:mm:ss'),
        nguyennhan: nguyennhan,
        nhanvien_gq_id: nhanvien_gq_id,
        noidung_gq: noidung_gq
      })
      return rs.data
    },
    capNhatTTDaGiao: async function(khieunai_id, phieukn_id, ngay_ht, nhanvien_th_id) {
      let rs = await this.$root.context.post('/web-gqkn/xu-ly-khieu-nai/capNhatTTDaGiao', {
        khieunai_id: khieunai_id,
        phieukn_id: phieukn_id,
        ngay_ht: moment(ngay_ht).format('YYYY-MM-DD HH:mm:ss'),
        nhanvien_th_id: nhanvien_th_id
      })
      return rs.data
    },

    getDanhSachPhieuTra: async function(khieunai_id, phieukn_id) {
      try{
        let rs = await this.$root.context.get('/web-gqkn/xu-ly-khieu-nai/lay-danhsach-phieutra-theo-phieu', {
        pKhieuNaiId: khieunai_id,
        pPhieuId: phieukn_id        
      })
      if (rs.error_code=='BSS-00000000')      
          return rs.data
      else
          return []  
      }
      catch (ex){return []}      
    },

    getHuongGiao: async function(quytrinh_id, ttkn_id) {
      let rs = await this.$root.context.post('/web-gqkn/xu-ly-khieu-nai/getHuongGiao', {
        quytrinh_id: quytrinh_id,
        ttkn_id: ttkn_id
      })
      return rs.data
    },
    capNhatThongTinGiamCuoc: async function(khieuNaiID) {
      let dsXml = ''
      let ds = this.dsGiamCuoc.filter((x) => x.ID != -1)
      for (let i = 0; i < ds.length; i++) {
        const item = ds[i]
        dsXml +=
          '<vt tgc="' +
          item.THANG_GC +
          '" tiengc="' +
          item.TIEN_GC +
          '" vat="' +
          item.VAT_GC +
          '" kygc="' +
          item.KY_GC +
          '" />'
      }
      if (dsXml != '') dsXml = '<ds>' + dsXml + '</ds>'
      let rs = await this.$root.context.post('/web-gqkn/xu-ly-khieu-nai/capNhatThongTinGiamCuoc', {
        khieunai_id: khieuNaiID,
        ds: dsXml
      })
      return rs.data
    },
    capNhatThongTinXacMinh: async function(phieukn_id, nhanvien_th_id, nd_xacminh) {
      let rs = await this.$root.context.post('/web-gqkn/xu-ly-khieu-nai/capNhatThongTinXacMinh', {
        phieukn_id: phieukn_id,
        nhanvien_th_id: nhanvien_th_id,
        nd_xacminh: nd_xacminh
      })
      return rs.data
    },
    async grid_SelectedRowChanged(row) {
      this.setState({ key: 'DsGC', value: [] })
      if (row) {
        try {
          this.loading(true)
          await this.getNgay_Cn()
          this.KHIEUNAI = { ...row }
          console.log(
            '🚀 ~ file: TiepNhanKN.vue:2105 ~ gridDS_TB_KHIEUNAI_SelectedRowChanged ~ this.KHIEUNAI',
            this.KHIEUNAI
          )
          // this.Selected.CHUDEKN_ID = parseInt(this.KHIEUNAI.CHUDEKN_ID)
          this.dsThaoTacControl = await this.getThongTinThaoTacControl(this.KHIEUNAI.LUONG_ID)
          await this.enableButtons()
          this.txtMaTB = this.KHIEUNAI.MA_TB
          this.dtpNgayCapCMT = this.KHIEUNAI.NGAYCAPCMT ? moment(this.KHIEUNAI.NGAYCAPCMT, 'DD/MM/YYYY').toDate() : null
          this.dtpNgayBN = this.KHIEUNAI.NGAY_BN ? moment(this.KHIEUNAI.NGAY_BN, 'DD/MM/YYYY H:mm').toDate() : null
          this.dtpNgayTN = this.KHIEUNAI.NGAY_TN ? moment(this.KHIEUNAI.NGAY_TN, 'DD/MM/YYYY H:mm:ss').toDate() : null
          this.dtpNgayXL = this.Ngay_Cn
          this.Selected.DICHVUVT_ID = parseInt(this.KHIEUNAI.DICHVUVT_ID)
          this.Selected.LOAITB_ID = parseInt(this.KHIEUNAI.LOAITB_ID)
          this.Selected.HTKN_ID = parseInt(this.KHIEUNAI.HTKN_ID)
          this.Selected.LOAI_ID = parseInt(this.KHIEUNAI.LOAI_ID)
          this.Selected.LOAIGT_ID = parseInt(this.KHIEUNAI.LOAIGT_ID)
          this.Selected.CHUDEKN_ID = parseInt(this.KHIEUNAI.CHUDEKN_ID)
          this.Selected.PAXL_ID = parseInt(this.KHIEUNAI.PAXL_ID)
          this.Selected.NGUYENNHAN_ID = parseInt(this.KHIEUNAI.NGUYENNHAN_ID)
          this.NGHIEMTHU.NGUOI_NT=this.$root.token.getUserName();
          this.NGHIEMTHU.NGAY_NT=new Date();
          this.NGHIEMTHU.NOIDUNG_NT=this.KHIEUNAI.GHICHU_TH;
          this.Selected.HTNT_ID=this.KHIEUNAI.HTNT_ID
          this.NGUOI_KHIEUNAI.NGUOI_KN = this.KHIEUNAI.NGUOI_KN
          this.NGUOI_KHIEUNAI.NOICAP = this.KHIEUNAI.NOICAP
          this.NGUOI_KHIEUNAI.DIENTHOAI_LH = this.KHIEUNAI.DIENTHOAI_LH
          this.NGUOI_KHIEUNAI.EMAIL = this.KHIEUNAI.EMAIL
          this.NGUOI_KHIEUNAI.NGAYCAP = this.KHIEUNAI.NGAYCAPCMT
            ? moment(this.KHIEUNAI.NGAYCAPCMT, 'DD/MM/YYYY H:mm').toDate()
            : null
          this.GIAOPHIEU.NOIDUNG = this.KHIEUNAI.ND_GIAO
          this.GIAOPHIEU.NGAY_GIAO = this.KHIEUNAI.NGAYGIAO
          this.GIAOPHIEU.NGUOI_GIAO = this.KHIEUNAI.NGUOI_GIAO
          this.GIAOPHIEU.DONVI_GIAO = this.KHIEUNAI.DONVI_GIAO
          this.GIAOPHIEU.HUONGGIAO = this.KHIEUNAI.HUONGGIAO
          
          this.THUEBAO.DICH_VU = this.KHIEUNAI.TEN_DVVT
          this.THUEBAO.LOAI_HINH = this.KHIEUNAI.LOAIHINH_TB

          this.NGUOI_KHIEUNAI.SO_GT = this.KHIEUNAI.CMT
          this.NGUOI_KHIEUNAI.DIACHI_LH = this.KHIEUNAI.DIACHI_LH
          
          this.KETQUA_KHIEUNAI.ND_PHUONGAN_XULY=this.KHIEUNAI.ND_XULY
          this.KETQUA_KHIEUNAI.ND_TRALOI=this.KHIEUNAI.NOIDUNG_GQ
          
          

          if (this.KHIEUNAI.HENGQ_TU !== null && this.KHIEUNAI.HENGQ_DEN !== null) {
            this.chkHenTL = true
            this.dtpTuNgay = this.KHIEUNAI.HENGQ_TU ? moment(this.KHIEUNAI.HENGQ_TU, 'DD/MM/YYYY').toDate() : null
            this.dtpDenNgay = this.KHIEUNAI.HENGQ_DEN ? moment(this.KHIEUNAI.HENGQ_DEN, 'DD/MM/YYYY').toDate() : null
          } else {
            this.chkHenTL = false
            this.dtpTuNgay = this.Ngay_Cn
            this.dtpDenNgay = moment(this.Ngay_Cn)
              .clone()
              .add(2, 'days')
              .toDate()
          }
          
          this.DS_NGUYENNHAN_LOI=await this.getDanhSachNguyenNhanLoi(this.KHIEUNAI.CHUDEKN_ID,this.KHIEUNAI.LOAITB_ID)
          const danhSachXacMinhGTC = await this.layKetQuaXacMinhGiamCuoc(this.KHIEUNAI.KHIEUNAI_ID);
          this.DS_XACMINH_GIAMCUOC= (danhSachXacMinhGTC && danhSachXacMinhGTC.length > 0) 
                                    ? JSON.parse(danhSachXacMinhGTC[0].NOIDUNG_XACMINH) 
                                    : [];
          this.KETQUA_KHIEUNAI.DOITUONG_LOI= this.DS_NGUYENNHAN_LOI.find(item=>item.NGUYENNHAN_ID==this.Selected.NGUYENNHAN_ID)?.DOITUONG_LOI_ID  
          this.Selected.HAILONG = this.KHIEUNAI.HAILONG
          this.Selected.HTTL_ID = this.KHIEUNAI.HTTL_ID
          var dsKNBS = await this.getKHIEUNAI_BS({ VKHIEUNAI_ID: this.KHIEUNAI.KHIEUNAI_ID })
          if (dsKNBS.data !== null && dsKNBS.data.length > 0) {
            this.KHIEUNAI_BS.push({
              KHIEUNAI_ID: this.KHIEUNAI.KHIEUNAI_ID,
              KY_TT: dsKNBS.data[0].KY_TT,
              TIENTRA: dsKNBS.data[0].TIENTRA,
              HENTRA: dsKNBS.data[0].HENTRA,
              GOICUOC: dsKNBS.data[0].GOICUOC
            })
          }
          if (this.activetab==2){
            let data_rs=await this.getDanhSachPhieuTra(this.KHIEUNAI.KHIEUNAI_ID,this.KHIEUNAI.PHIEUKN_ID)
            if (data_rs.length>0 )
            {
              this.paneThongTinTraPhieu=true;
            }
          }
          if (this.KHIEUNAI.THUEBAO_ID !== null && this.KHIEUNAI.THUEBAO_ID !== 0) {
            let data = {
              MA_TB: this.txtMaTB,
              DICHVUVT_ID: this.KHIEUNAI.DICHVUVT_ID
            }
            await this.getTTThueBao(data)
            if (this.DS_TTTHUEBAO.length > 0) {
              await this.HienThiTTThueBao(this.DS_TTTHUEBAO[0], 2, 0)
            } else {
              await this.Clear()
              return
            }
          } else {
            this.setState({ key: 'DS_KHIEUNAI_HT', value: [] })
            this.setState({ key: 'DS_CHITIETNO_THEO_KHOANMUC', value: [] })
          }
          this.SetButton(3)          
          this.Selected.CHUDEKN_ID = parseInt(this.KHIEUNAI.CHUDEKN_ID)
        } catch (error) {
          console.log('🚀 ~ file: TiepNhanKN.vue:2187 ~ gridDS_TB_KHIEUNAI_SelectedRowChanged ~ error', error)
        } finally {
          this.loading(false)
        }
      } else {
        this.KHIEUNAI = {}
        this.Clear()
      }
    },
    gridDS_TB_rowSelected(args) {
      this.FocusedRowHandle = args.rowIndex
      this.grid_SelectedRowChanged(args.data)
    },
    gridDS_TB_KHIEUNAI_rowSelectedOrDeSelected() {
      this.gridDS_TB_KHIEUNAI_rowSelected = this.$refs.gridDS_TB_KHIEUNAI.getSelectedRecords()
    },
    async layKyCuocHienHanh() {
      try {
        let response = await this.$root.context.get(`/web-thuno/api/thu-no/common/ky-cuoc-hien-hanh`)
        if (response.error_code === 'BSS-00000000' && response.data != null) {
          this.Kyhoadon = moment(response.data[0].KYCUOC, 'YYYYMMDD').toDate()
          return
        }
      } catch (err) {
        this.Kyhoadon = null
      }
    },
    gridDS_TB_KHIEUNAI_onQueryCellInfo(args) {
      let field = args.column.field
      let dataField = args.data
      if (field == 'MA_KN') {
        if (dataField.HETHAN_KN == 1) {
          args.cell.classList.add('background-red')
        }
      }
    },
    btnXemChiTiet_Click: function() {
      if (!this.KHIEUNAI.KHIEUNAI_ID) return;
      this.$refs.dialogChiTietKhieuNai.dialogOpen()
    },
    onclickTab: function(currentActivetab) {
      try {
        this.loading(true)

        this.activetab = currentActivetab
        this.columnFields = GRID_COLUMS[currentActivetab]
        //this.hienThiDanhSachCatChuyen(currentActivetab, 0);
        if (currentActivetab==2)
        {
          this.Button.enableGiaoViec=false
          this.Button.enableTraPhieu=false
          this.Button.enableHuyGiaoViec=false
          this.Button.enableKhoaPhieu=false
        }
        else
        {
          this.Button.enableGiaoViec=true
          this.Button.enableTraPhieu=true
          this.Button.enableHuyGiaoViec=true
          this.Button.enableKhoaPhieu=true
        }
      } catch (error) {
        this.$root.toastError(' ' + error)
      } finally {
        this.loading(false)
      }
    },
    getDsLoaiGT: async function() {
      try {
        const rs = await this.$root.context.get(`/web-quantri/danhmuc-chung/CSS_LOAI_GT`)
        this.dsLoaiGT = rs.data
      } catch (error) {}
    },
    btnLayTT_Click: async function(args) {
      this.$root.showLoading(true)
      try {
        this.getTB_KHIEUNAI(null)
      } finally {
        this.$root.showLoading(false)
      }
    },
    enableButtons: async function() {
      if (this.isToTruong) {
        if (!(this.dsThaoTacControl == null || this.dsThaoTacControl == undefined)) {
          if (this.dsThaoTacControl.length > 0) {
            this.enableBtnGiaoViec =
              this.dsThaoTacControl.findIndex((x) => x.ENABLE == 1 && x.CONTROL_NAME == 'tsbrnGiaoViec') > -1 
            this.enableBtnHuyGiao =
              this.dsThaoTacControl.findIndex((x) => x.ENABLE == 1 && x.CONTROL_NAME == 'tsbtnHuyGiao') > -1 
            this.enableBtnTraPhieu = !(
              this.dsThaoTacControl.findIndex((x) => x.ENABLE == 0 && x.CONTROL_NAME == 'tsbtnTraPhieu') > -1
            )
            this.enableBtnGiaoPhieu =
              this.dsThaoTacControl.findIndex((x) => x.ENABLE == 1 && x.CONTROL_NAME == 'tsbtnGiaoPhieu') > -1
            this.enableBtnHoanThanh =
              this.dsThaoTacControl.findIndex((x) => x.ENABLE == 1 && x.CONTROL_NAME == 'tsbtnHoanThanh') > -1
            this.enableBtnHoanThanh_GQKN =
              this.dsThaoTacControl.findIndex((x) => x.ENABLE == 1 && x.CONTROL_NAME == 'tsbtnHoanThanh_GQKN') > -1
            this.enableBtnKhoaPhieu =
              this.dsThaoTacControl.findIndex((x) => x.ENABLE == 1 && x.CONTROL_NAME == 'tsbtnKhoaPhieu') > -1
            this.enableBtnGiaoPhoiHop =
              this.dsThaoTacControl.findIndex((x) => x.ENABLE == 1 && x.CONTROL_NAME == 'tsbtnGiaoPhoiHop') > -1
              this.enableBtnGhiLai =
              this.dsThaoTacControl.findIndex((x) => x.ENABLE == 1 && x.CONTROL_NAME == 'tsbtnGhiLai') > -1
              
          }
        }
      }
    },
    checkPhaiLam: function(code) {
      if (!(this.dsThaoTac == null || this.dsThaoTac == undefined)) {
        return this.dsThaoTac.filter((x) => x.ENABLE == 1 && x.CODE == code).length > 0
      } else {
        return false
      }
    },
    giaoPhieuNhanVien: async function(phieukn_id, nhanvien_giao_id, nhanvien_th_id, ghichu) {
      let rs = await this.$root.context.post('/web-gqkn/xu-ly-khieu-nai/giaoPhieuNhanVien', {
        phieukn_id: phieukn_id,
        nhanvien_giao_id: nhanvien_giao_id,
        nhanvien_th_id: nhanvien_th_id,
        ghichu: ghichu
      })
      return rs.data
    },
    capNhatNhanVienThucHien: async function(khieunai_id, nhanvien_gq_id) {
      let rs = await this.$root.context.post('/web-gqkn/xu-ly-khieu-nai/capNhatNhanVienThucHien', {
        khieunai_id: khieunai_id,
        nhanvien_gq_id: nhanvien_gq_id
      })
      return rs.data
    },
    btnGiaoNV_Click: async function(args) {
      this.$root.showLoading(true)
      try {
        if (!this.nhanVienThucHienID || this.nhanVienThucHienID == 0) {
          return this.$root.toastError('Bạn chưa chọn nhân viên thực hiện!')
        }
        let rs = await this.giaoPhieuNhanVien(
          this.KHIEUNAI.PHIEUKN_ID,
          this.$root.token.getNhanVienID(),
          this.nhanVienThucHienID,
          this.KHIEUNAI.GHICHU_KN
        )
        if (!this.checkPhaiLam('XACMINH')) {
          await this.capNhatNhanVienThucHien(this.KHIEUNAI.KHIEUNAI_ID, this.nhanVienThucHienID)
        }
        if (this.nhanTinGiaoViec) {
          await this.xuLyThongBaoNVTH(this.KHIEUNAI.KHIEUNAI_ID)           
        }
        if (rs.Result == true) {
          this.$root.toastSuccess('Giao phiếu nhân viên thành công')
          await this.getTB_KHIEUNAI(null)
        } else {
          this.$root.toastError('Giao phiếu nhân viên thất bại')
        }
      } finally {
        this.$root.showLoading(false)
      }
    },
    xuLyThongBaoNVTH: async function(phieukn_id) {
      let rs = await this.$root.context.post('/web-gqkn/xu-ly-khieu-nai/xuLyThongBaoNVTH', {
        id: phieukn_id
      })
      return rs.data
    },
    xuLyThongBaoHuyGiao: async function(phieukn_id) {
      let rs = await this.$root.context.post('/web-gqkn/xu-ly-khieu-nai/xuLyThongBaoHuyGiao', {
        id: phieukn_id
      })
      return rs.data
    },
    btnHuyGiaoNV_Click: async function(args) {
      this.$root.showLoading(true)
      try {
        if (this.checkPhaiLam('XACMINH')) {
          await this.huyGiaoPhieu(this.KHIEUNAI.PHIEUKN_ID)
        } else {
          await this.huyNhanVienTH(this.KHIEUNAI.KHIEUNAI_ID)
        }
        if (this.nhanTinGiaoViec) {
          await this.xuLyThongBaoHuyGiao(this.KHIEUNAI.PHIEUKN_ID)
        }
        await this.getTB_KHIEUNAI(null)
      } finally {
        this.$root.showLoading(false)
      }
    },
    huyGiaoPhieu: async function(phieukn_id) {
      let rs = await this.$root.context.post('/web-gqkn/xu-ly-khieu-nai/huyGiaoPhieu', {
        id: phieukn_id
      })
      return rs.data
    },
    huyNhanVienTH: async function(khieunai_id) {
      let rs = await this.$root.context.post('/web-gqkn/xu-ly-khieu-nai/huyNhanVienTH', {
        id: khieunai_id
      })
      return rs.data
    },
    btnHoanThanh_Click: async function(args) {
      this.$root.showLoading(true)
      try {
        if (this.checkEmptyNumber(this.Selected.NHANVIEN_ID)) {
          this.$root.toastError('Bạn chưa chọn Nhân viên xử lý !')
        } else {
          if (this.checkEmptyString(this.KETQUA_KHIEUNAI.ND_TRALOI)) {
            this.$root.toastError('Bạn chưa nhập nội dung trả lời khiếu nại !')
          } else {
            if (this.checkEmptyString(this.KHIEUNAI.ND_KHIEUNAI)) {
              this.$root.toastError('Bạn chưa nhập nguyên nhân !')
            } else {
              if (this.checkPhaiLam('XACMINH') && this.checkEmptyString(this.XACMINH_KHIEUNAI.ND_XACMINH)) {
                this.$root.toastError('Bạn chưa nhập nội dung xác minh!')
              } else {
                let t11 = await this.kiemTraKhoaPhieuKN(this.KHIEUNAI.PHIEUKN_ID)
                if (t11 != 'ok') {
                  this.$root.toastError(t11)
                } else {
                  let t1 = await this.kiemTraPhieuCon(this.KHIEUNAI.PHIEUKN_ID)
                  if (t1.RS > 0) {
                    this.$root.toastError('Vẫn còn phiếu con chưa thực hiện nên Bạn chưa được phép Hoàn thành!')
                  } else {
                    let huongGiaoID = 0
                    //TRANGTHAI_KHIEU_NAI.XU_LY_XONG = 5
                    let t2 = await this.getHuongGiao(this.KHIEUNAI.QUYTRINH_ID, 5)
                    if (t2 != null) {
                      huongGiaoID = t2.HUONGGIAO_ID
                    }

                    await this.capNhatKetQuaXuLy();
                    

                    if (this.checkPhaiLam('XACMINH'))
                      await this.capNhatThongTinXacMinh(
                        this.KHIEUNAI.PHIEUKN_ID,
                        this.$root.token.getNhanVienID(),
                        this.KETQUA_KHIEUNAI.ND_TRALOI
                      )

                    await this.capNhatThongTinGiamCuoc(this.KHIEUNAI.KHIEUNAI_ID)

                    if (this.checkPhaiLam('TUDONG_HTHS')) {
                      await this.giaoPhieuKNKhiHC(
                        this.KHIEUNAI.KHIEUNAI_ID,
                        this.KHIEUNAI.PHIEUKN_ID,
                        huongGiaoID,
                        this.$root.token.getDonViID()
                      )

                      //TRANGTHAI_KHIEU_NAI.KHIEU_NAI_HOAN_THANH = 6
                      await this.capNhatHTHS(
                        this.KHIEUNAI.KHIEUNAI_ID,
                        this.KHIEUNAI.PHIEUKN_ID,
                        this.Selected.NHANVIEN_ID,
                        new Date(),
                        6,
                        this.KHIEUNAI.ND_KHIEUNAI,
                        this.KETQUA_KHIEUNAI.ND_TRALOI,
                        moment(this.dtpNgayXL).toDate()
                      )

                      if (this.themKHDBKhiKhieuNai) {
                        await this.capNhatKHDB(this.KHIEUNAI.KHIEUNAI_ID, this.$root.token.getNhanVienID())
                      }
                      this.$root.toastSuccess('Bạn đã khóa phiếu khiếu nại thành công !')
                    } else {
                      await this.capNhatXuLyHT(
                        this.KHIEUNAI.KHIEUNAI_ID,
                        this.KHIEUNAI.SO_VB,
                        '',
                        new Date(),
                        this.KHIEUNAI.ND_KHIEUNAI,
                        this.Selected.NHANVIEN_ID,
                        this.KETQUA_KHIEUNAI.ND_TRALOI
                      )
                      await this.giaoPhieuKNKhiHC(
                        this.KHIEUNAI.KHIEUNAI_ID,
                        this.KHIEUNAI.PHIEUKN_ID,
                        huongGiaoID,
                        this.KHIEUNAI.DONVI_ID
                      )
                      await this.capNhatTTDaGiao(
                        this.KHIEUNAI.KHIEUNAI_ID,
                        this.KHIEUNAI.PHIEUKN_ID,
                        new Date(),
                        this.Selected.NHANVIEN_ID
                      )
                      //TRANGTHAI_KHIEU_NAI.XU_LY_XONG = 5
                      await this.capNhatBaoTonHT(
                        this.KHIEUNAI.KHIEUNAI_ID,
                        new Date(),
                        this.Selected.NHANVIEN_ID,
                        this.KETQUA_KHIEUNAI.ND_TRALOI,
                        '',
                        this.$root.token.getDonViID(),
                        5
                      )
                      this.$root.toastSuccess('Bạn đã xử lý khiếu nại thành công !')
                    }
                    await this.loadDsKhieuNai()
                  }
                }
              }
            }
          }
        }
      } finally {
        this.$root.showLoading(false)
      }
    },
    btnHoanThanh_Click_1: async function(args) {
      this.$root.showLoading(true)
      try {
        if (this.checkEmptyNumber(this.Selected.NHANVIEN_ID)) {
          this.$root.toastError('Bạn chưa chọn Nhân viên xử lý !')
        } else {
          let ck = true
          if (this.dsTTKNID == '3') {
            if (this.checkEmptyString(this.XACMINH_KHIEUNAI.ND_XACMINH)) {
              this.$root.toastError('Bạn hãy nhập nội dung xác minh !')
              ck = false
            }
          }
          if (ck) {
            if (this.checkPhaiLam('XACMINH') && this.checkEmptyString(this.XACMINH_KHIEUNAI.ND_XACMINH)) {
              this.$root.toastError('Bạn chưa nhập nội dung xác minh!')
            } else {
              await this.capNhatTTPhieuGiao(this.KHIEUNAI.PHIEUKN_ID)
              if (this.checkPhaiLam('XACMINH'))
                await this.capNhatThongTinXacMinh(
                  this.KHIEUNAI.PHIEUKN_ID,
                  this.$root.token.getNhanVienID(),
                  this.XACMINH_KHIEUNAI.ND_XACMINH
                )
              this.$root.toastSuccess('Hoàn thành công việc thành công !')
              await this.loadDsKhieuNai()
            }
          }
        }
      } finally {
        this.$root.showLoading(false)
      }
    },
    btnKhoaPhieu_Click: async function(args) {
      this.$root.showLoading(true)
      try {      
                  this.$bvModal
                    .msgBoxConfirm('Bạn có muốn Khóa phiếu không ?', {
                      title: 'Thông báo',
                      size: 'sm',
                      okTitle: 'Đồng ý',
                      cancelTitle: 'Hủy',
                      centered: true
                    })
                    .then(async (v) => {
                      if (v) {
                        await this.khoaPhieu()
                      }
                    })
                
      } finally {
        this.$root.showLoading(false)
      }
    },
    khoaPhieu: async function(args) {
      let result = true
      try {
        let param={
          pKhieuNaiId:this.KHIEUNAI.KHIEUNAI_ID,
          pPhieuKNId:this.KHIEUNAI.PHIEUKN_ID,
          pHTTLId:this.Selected.HTNT_ID,
          pNoiDung: this.noiDungKhoaPhieu
        }
        this.loading(true)
        var rs = await this.$root.context.post(
          '/web-gqkn/xu-ly-khieu-nai/khoaphieu-nghiemthu-khieunai',param
        )
        if (rs.error_code=='BSS-00000000') {          
          this.$toast.success('Nghiệm thu thuê bao thành công');
          this.getDS_TB_KHIEUNAI()
        } else { 
          this.$toast.error('Nghiệm thu thất bại'); 
          }
      } catch (error) {
        this.$toast.error('Nghiệm thu thất bại :'+error.message); 
      } finally {
        this.loading(false)
      }
      return result;

      
    },
    btnHoSo_Click: function(args) {
      if (!this.checkEmptyNumber(this.currentItem.selectedKhieuNai.KHIEUNAI_ID)) {
        this.$refs.frmHoSoKhieuNai.khieunai_id = this.currentItem.selectedKhieuNai.KHIEUNAI_ID
        this.$refs.frmHoSoKhieuNai.show()
      }
      // this.$root.toastSuccess(
      //   "Chờ form tham chiếu để mở link: Quản lý hồ sơ khiếu nại (Tham chiếu UR4.1.005_Mục 1.1.7)"
      // );
    },
    btnGiamCuoc_Click() {
      let Link = this.$router.resolve({
        path: 'de-xuat-giam-tru-cuoc',
        query: { MaKN: this.KHIEUNAI.MA_KN }
      })
      window.open(Link.href, '_blank')
    },
    visibleDXGiamCuoc: function() {
      return this.dsTTKNID == '2, 4' && this.dsHuongGiaoID == '2252, 2256, 2279'
    },
    getThongTinThaoTacControl: async function(luong_id) {
      let rs = await this.$root.context.post('/web-gqkn/xu-ly-khieu-nai/getThongTinThaoTacControl', {
        id: luong_id
      })
      return rs.data
    },
    enableTxtNguyenNhan: function() {
      let result = false
      if (!(this.dsThaoTacControl == null || this.dsThaoTacControl == undefined)) {
        if (this.dsThaoTacControl.length > 0) {
          result = this.dsThaoTacControl.findIndex((x) => x.ENABLE == 1 && x.CONTROL_NAME == 'txtNguyenNhan') > -1
        }
      }
      return result
    },
    enableTxtNoiDungXL: function() {
      let result = false
      if (!(this.dsThaoTacControl == null || this.dsThaoTacControl == undefined)) {
        if (this.dsThaoTacControl.length > 0) {
          result = this.dsThaoTacControl.findIndex((x) => x.ENABLE == 1 && x.CONTROL_NAME == 'txtNoiDungXL') > -1
        }
      }
      return result
    },
    btnTraPhieu_Click() {
      this.$refs.modalTraPhieuKhieuNai.show()      
    },
    modalGiaoPhieuXacMinhPhoiHop_Shown: async function(args) {
      await this.$refs.formGiaoPhieuXacMinhPhoiHop.bindData(this.KHIEUNAI, this.nhanTinGiaoPhieuXLKN)
    },
    modalGiaoPhieuXacMinhPhoiHop_Hidden: async function(args) {
      //await this.loadDsKhieuNai();
    },
    btnGiaoPH_Click: function(args) {
      //this.$refs.modalGiaoPhieuXacMinhPhoiHop.setData(args)
      this.$refs.modalGiaoPhieuXacMinhPhoiHop.show()
    },

    btnGiaoPhieu_Click: async function(args) {
      //this.$refs.modalGiaoPhieuKhieuNai.show()
      try {
        if (this.DS_TB_KHIEUNAI.length > 0) {
          if (this.KHIEUNAI.KHIEUNAI_ID === null) return
          if (this.KHIEUNAI.HTKN_ID === this.HINHTHUC_KN.VAN_BAN) {
            let data = {
              VKHIEUNAI_ID: this.KHIEUNAI.KHIEUNAI_ID
            }
            let rs = await this.getHOSO_KHIEUNAI(data)
            if (rs.data.length === 0) {
              await this.$alert('Chưa Upload file văn bản. Bạn không thể giao phiếu !', 'Thông báo', {
                confirmButtonText: 'Đóng',
                type: 'Error'
              })
              return
            }
          }
          let quytrinh_id = this.KHIEUNAI.QUYTRINH_ID
          let data = {
            VQUYTRINH_ID: quytrinh_id,
            VTHUTU_ID: 3
          }
          let rs = await this.getHUONGGIAO_QUYTRINH(data)
          if (rs.data.length > 0) {
            let vhuonggiao_id = parseInt(rs.data[0].HUONGGIAO_ID)
            let data = {
              quytrinh_id: quytrinh_id,
              ttkn_id: 3
            }
            rs = await this.getDsHuongGiaoTheoQuyTrinhTTKN(data)
            console.log('🚀 ~ file: TiepNhanKN.vue:434 ~tsbtnGiaoPhieu_Click ~ rs', rs)
            if (rs.length > 0) {
              var ds = rs.filter((e) => e.HUONGGIAO_ID === vhuonggiao_id)
              console.log('🚀 ~ file: TiepNhanKN.vue:437 ~tsbtnGiaoPhieu_Click ~ ds', ds)
              if (ds.length > 0) {
                if (vhuonggiao_id !== 0) {
                  let data = {
                    kieuId: 1,
                    huongGiaoId: vhuonggiao_id,
                    maTb: this.KHIEUNAI.MA_TB,
                    ttphId: 1,
                    ngaygiao: null
                  }
                  console.log('🚀 ~ file: TiepNhanKN.vue:460 ~ tsbtnGiaoPhieu_Click ~ data:', data)
                  let modal = this.$refs['ModalGiaoPhieuKN']
                  modal.setData(data)
                  modal.show()
                }
              } else {
                let modal = this.$refs['ModalGiaoPhieuKN']
                modal.setData(null)
                modal.show()
              }
            } else {
              let modal = this.$refs['ModalGiaoPhieuKN']
              modal.setData(null)
              modal.show()
            }
          }
        } else {
        }
      } catch (error) {}
    },
    getDsThamSo: async function() {
      let rs = await this.$root.context.post('/web-gqkn/xu-ly-khieu-nai/getDsThamSo')
      return rs.data
    },
    dsGiamCuocAll: function() {
      if (!this.checkEmptyNumber(this.currentItem.selectedKhieuNai.KHIEUNAI_ID)) {
        if (!(this.dsGiamCuoc == null || this.dsGiamCuoc.length == 0)) {
          let f = this.dsGiamCuoc.find((x) => x.ID == -1)
          if (f == null) {
            let result = []
            let newItem = this.createNoItem()
            newItem.ID = -1
            result.push(newItem)
            for (let i = 0; i < this.dsGiamCuoc.length; i++) {
              this.checkEmptyNumber(this.dsGiamCuoc[i].ID)
              this.dsGiamCuoc[i].ID = this.createID()
              result.push(this.dsGiamCuoc[i])
            }
            this.dsGiamCuoc = result
          }
        } else {
          this.dsGiamCuoc = []
          let newItem = this.createNoItem()
          newItem.ID = -1
          this.dsGiamCuoc.push(newItem)
        }
      }
      return this.dsGiamCuoc.filter((item) => {
        return item.ID != null
      })
    },
    getDsNhanVienThucHien: async function() {
      let rs = await this.$root.context.post('/web-gqkn/xu-ly-khieu-nai/getDsNhanVienXuLyKNV2')
      return rs.data
    },
    getDsHuongGiaoTheoQuyTrinhTTKN: async function(data) {
      var rs = await this.$root.context.post(
        '/web-gqkn/tiep-nhan-khieu-nai/lay-danhsach-huonggiao-theo-quytirnh-ttkn',
        data
      )
      return rs.data
    },
    modalTraPhieuKhieuNai_Shown: async function(args) {
      await this.$refs.formTraPhieuKhieuNai.bindData(this.KHIEUNAI)
    },
    modalTraPhieuKhieuNai_Hidden: async function(args) {
      //await this.loadDsKhieuNai();
    },
    async getDanhSachNguyenNhanLoi (chudeknid,loaitbid) {
      try {
        let param={
          chuDeKNId:chudeknid,
          loaiTBId:loaitbid
        }
        var rs = await this.$root.context.post(
          '/web-gqkn/khieunai/lay-danhsach-nguyennhan-loi',param
        )
        if (rs.data.length > 0) {
          return rs.data
        } else { return null }
      } catch (error) {
        return null
      }
    },
    async onKhoaPhieu() {
      this.noiDungKhoaPhieu = ''
      if (!this.KHIEUNAI.KHIEUNAI_ID) {
        return this.$toast.error('Bạn chưa chọn phiếu để khóa!')
      }
      if (!this.KHIEUNAI.PHIEUKN_ID) {
        return this.$toast.error('Bạn chưa chọn phiếu để khóa!')
      }
      this.$bvModal.show('popupKhoaPhieu')      
    },
    async onXacNhanKhoaPhieu() {
      if (!this.noiDungKhoaPhieu || this.noiDungKhoaPhieu.trim() == '') {
        return this.$toast.error('Bạn chưa nhập nội dung khóa phiếu!')
      }
      const result = await this.khoaPhieu()
      if (result) {
        this.$bvModal.hide('popupKhoaPhieu')
        await this.btnLayTT_Click();
      }
    },    
    onHuyKhoaPhieu() {
      this.$bvModal.hide('popupKhoaPhieu')
    },     
  },

  async mounted() {
    try {
      let ts = await this.getDsThamSo()
      if (ts != null) {
        let THEM_KHDB_KHI_KHIEUNAI = ts.find((x) => x.MA_TS == 'THEM_KHDB_KHI_KHIEUNAI')
        if (THEM_KHDB_KHI_KHIEUNAI != null) {
          this.themKHDBKhiKhieuNai = THEM_KHDB_KHI_KHIEUNAI.TEN_TS.toString().trim() == '1'
        }
        let NHANTIN_GIAOVIEC_KHIEUNAI = ts.find((x) => x.MA_TS == 'NHANTIN_GIAOVIEC_KHIEUNAI')
        if (NHANTIN_GIAOVIEC_KHIEUNAI != null) {
          this.nhanTinGiaoViec = NHANTIN_GIAOVIEC_KHIEUNAI.TEN_TS.toString().trim() == '1'
        }
        let NHANTIN_GIAOPHIEU_XLKN = ts.find((x) => x.MA_TS == 'NHANTIN_GIAOPHIEU_XLKN')
        if (NHANTIN_GIAOPHIEU_XLKN != null) {
          this.nhanTinGiaoPhieuXLKN = NHANTIN_GIAOPHIEU_XLKN.TEN_TS.toString().trim() == '1'
        }
      }

      this.dsNhanVienThucHien = await this.getDsNhanVienThucHien()
      this.nhanVienThucHienID = await this.dsNhanVienThucHien.find(item => item.NHANVIEN_ID == this.$root.token.getNhanVienID())?.NHANVIEN_ID ?? null;
      await this.getNgay_Cn()
      this.dtpNgayBN = moment(this.Ngay_Cn)
        .clone()
        .add(1, 'hours')
        .toDate()
      await this.layKyCuocHienHanh()
      EventBus.$on('gridGC_refresh', this.gridGC_refresh)
      EventBus.$on('clickAddNew', this.clickAddNew)
      this.SetButton(3)
      // await this.getTB_KHIEUNAI()
      if ('ma_tb' in this.$route.query) {
        if (typeof this.$route.query.ma_tb === 'string' && this.$route.query.ma_tb !== '') {
          this.txtMaTB = this.$route.query.ma_tb
          //await this.txtMaTB_KeyPress();
          let ck = false
          await this.getDS_TB_KHIEUNAI({ TTKN_ID: 2, HUONGGIAO_ID: 0, MA_TB: this.txtMaTB })
          if (this.DS_TB_KHIEUNAI.length > 0) {
            //this.setState({ key: 'DS_TB_KHIEUNAI', value: this.DS_TB_KHIEUNAI.filter(x => x.MA_TB == this.txtMaTB) })
            if (this.DS_TB_KHIEUNAI.length > 0) {
              await this.gridDS_TB_KHIEUNAI_SelectedRowChanged(this.DS_TB_KHIEUNAI[0])
              ck = true
              this.SetButton(3)
            }
          }
          if (!ck) {
            await this.txtMaTB_KeyPress()
          }
          if ('sdt' in this.$route.query) {
            this.KHIEUNAI.DIENTHOAI_LH = this.$route.query.sdt
          }
        }
      }
      if ('makh' in this.$route.query) {
        if (typeof this.$route.query.makh === 'string' && this.$route.query.makh !== '') {
          this.txtMaTB = this.$route.query.makh
          let ck = false
          await this.getDS_TB_KHIEUNAI({ TTKN_ID: 2, HUONGGIAO_ID: 0, MA_TB: this.txtMaTB })
          if (this.DS_TB_KHIEUNAI.length > 0) {
            if (this.DS_TB_KHIEUNAI.length > 0) {
              await this.gridDS_TB_KHIEUNAI_SelectedRowChanged(this.DS_TB_KHIEUNAI[0])
              ck = true
              this.SetButton(3)
            }
          }
          if (!ck) {
            await this.txtMaTB_KeyPress()
          }
          if ('sdt' in this.$route.query) {
            this.KHIEUNAI.DIENTHOAI_LH = this.$route.query.sdt
          }
        }
      }
      //this.$refs.txtMaTB.focus()
    } catch (error) {
      console.log('mounted error', error);
      
    } finally {
      this.$root.showLoading(false)
    }
  }
}
</script>
<style src="./index.scss"></style>
