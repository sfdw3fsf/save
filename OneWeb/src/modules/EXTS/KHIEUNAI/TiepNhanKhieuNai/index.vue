<template src="./index.html"></template>
<script>
import Vue from 'vue'
import { mapState, mapActions, mapGetters } from 'vuex'
import { TiepNhanKN, getterName, actionName, statePropertyName } from '../TiepNhanKN/store/index'
import { DateTimePickerPlugin } from '@syncfusion/ej2-vue-calendars'
import moment from 'moment'
import BssErrorMarker from '@/components/BssErrorMarker'
import BssRequiredMarker from '@/components/BssRequiredMarker'
import { required, minValue, maxValue, maxLength, numeric, email } from 'vuelidate/lib/validators'
import { Sort, Filter, Toolbar, Edit, CommandColumn } from '@syncfusion/ej2-vue-grids'
import { L10n } from '@syncfusion/ej2-base'
import RowTemplate from './RowTemplateGiamCuoc/index.vue'
import EventBus from '@/utils/eventBus'
import ModalDocFile from './modal/DocFileV2'
import ModalNhanVienLoi from './modal/KetQuaLoi/NhanVienLoi'
import ModalDonViLoi from './modal/KetQuaLoi/DonViLoi'
import ModalGiaoPhieuKN from './modal/GiaoPhieu/index.vue'
import TraCuuDanhBa from './modal/TraCuuDanhBa/index.vue'
//import DiaChiModal from './modal/ChonDiaChi/DiaChiModal.vue'
import DiaChiModal from "@/modules/contract/setup/InstallNewSubs/Popups/popupChonDiaChi"

import ChiTietKhieuNai from '../ChiTietKhieuNai/ChiTiet.vue'

import FormTraCuuTB from './modal/frmTraCuuThueBao'
import { currency } from '@/filters/currency'
import ChenNo from '@/modules/QLTN/GachNo/components/ChenNo.vue'
import frmHoSoKhieuNai from '@/modules/complaint/SearchComplainDetail/popups/frmHoSoKhieuNai.vue'
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
    ModalDocFile,
    ModalNhanVienLoi,
    ModalDonViLoi,
    ModalGiaoPhieuKN,
    TraCuuDanhBa,
    DiaChiModal,
    FormTraCuuTB,
    ChenNo,
    frmHoSoKhieuNai,
    ChiTietKhieuNai

  },
  props: {

  },

  validations: {
    KHIEUNAI:
    {
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
      },
      // EMAIL: {
      //   email
      // }
    },
    NGUOI_KHIEUNAI:
    {
      DIENTHOAI_LH: {
        required,
        maxLength: maxLength(50),
        numeric
      },
      NGUOI_KN: {
        required,
        maxLength: maxLength(200)

      },
    }
  },
  computed: {
    ...mapState('khieunai/TiepNhanKN', statePropertyName),
    ...mapState('khieunai', ['TiepNhanKN']),
    ...mapGetters('khieunai/TiepNhanKN', getterName),
    getDS_CHITIETNO_THEO_KHOANMUC () {
      return this.DS_CHITIETNO_THEO_KHOANMUC
    },
    dsGiamCuocAll: function () {
      if (!(this.DsGC == null || this.DsGC.length === 0)) {
        let f = this.DsGC.find((x) => x.ID === '-1')
        if (f == null) {
          let result = []
          let newItem = this.createNoItem()
          newItem.ID = '-1'
          result.push(newItem)
          for (let i = 0; i < this.DsGC.length; i++) {
            result.push(this.DsGC[i])
          }
          this.setState({ key: 'DsGC', value: result })
        }
      } else {
        this.setState({ key: 'DsGC', value: [] })
        let newItem = this.createNoItem()
        newItem.ID = '-1'
        this.setState({ key: 'DsGC', value: [newItem] })
      }
      return this.DsGC.filter((item) => {
        return item.ID != null
      })
    }
  },
  beforeCreate () {
  },
  async created () {
    await this.createApiManager()
    await this.clearState()
    this.$root.showLoading(true)
    await this.loadData()
    await this.getDsLoaiGT();
    this.columnFields=GRID_COLUMS[1];
  },
  data () {
    return {
      ls_khieunai_id:0,
      activetab: 1,
      columnFields:[],
      GRID_COLUMS:GRID_COLUMS,
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
      DS_DIACHI: [],
      DS_CHUDEKN:[],
      dsLoaiGT:[],
      checkAll: false,
      dsIdChon: [],
      rowTemplate: function (e) {
        return {
          template: RowTemplate
        }
      },
      commands: [
        {
          type: 'Delete', buttonOption: { cssClass: 'e-flat', iconCss: 'e-delete e-icons' }
        }
      ],
      toolbar: [{ text: 'Click vào dòng này để nhập mới', tooltipText: 'Click vào dòng này để nhập mới', prefixIcon: 'e-expand', id: 'add', align: 'Center' }],
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
      optXuLy: "0",
      chkHenTL: false,
      txtMaTB: '',
      daily_id: null,
      Kyhoadon: null,
      dtpNgayTN: null,
      dtpTuNgay: null,
      dtpDenNgay: null,
      dtpNgayBN: null,
      dtpNgayXL: moment(new Date()).format('DD/MM/YYYY hh:mm:ss'),
      Ngay_Cn: null,
      ky_kn: '',
      dtpNgayCapCMT: null,
      NGUOI_KHIEUNAI:{
        NGUOI_KN: '',
        DIENTHOAI_LH:'',
      },
      KETQUA_KHIEUNAI:{},
      THUEBAO: {},
      KHIEUNAI: {
        ND_KHIEUNAI: '',
        KY_KN: '',
        KHIEUNAI_ID: null,
        DIACHI_LH: null,
        GHICHU_KN:null,
      },
      Selected: {
        DICHVUVT_ID: '',
        LOAITB_ID: '',
        LOAIKH_ID: '',
        DOITUONG_ID: '',
        HTKN_ID: '',
        LOAI_ID: '',
        CHUDEKN_ID: '',
        KETQUAKN_ID: '',
        NHANVIEN_ID: '',
        PAXL_ID: '',
        HTTL_ID: '',
        HAILONG: '',
        LOAIGT_ID:'',
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
        btnNhanVienLoi: false,
        tsbtnHoSo:false,
        tsbtnDocFile:false,
      },
      gridDS_TB_KHIEUNAI_rowSelected: [],
      DS_ThangGC: [],
      DS_NGUYENNHAN_LOI:[],
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
      ChenNo: { ma_tt: null, kycuoc: null, ma_tb: null }
    }
  },
  watch: {
    checkAll (newVal) {
      console.log('🚀 ~ file: index.vue ~ line 298 ~ checkAll ~ newVal', newVal)
      if (newVal) {
        this.dsIdChon = this.DS_TB_KHIEUNAI.map(item => item.KHIEUNAI_ID)
        return
      }
      if (this.dsIdChon.length > 0 &&
        this.dsIdChon.length === this.DS_TB_KHIEUNAI.length) {
        this.dsIdChon = []
      }
    },
    dsIdChon (newVal) {
      this.checkAll = newVal.length > 0 && newVal.length === this.DS_TB_KHIEUNAI.length
    },
    'optXuLy' (val) {
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

    'Selected.DICHVUVT_ID' (val) {
      if (val) {
        this.getDANHMUC({ loai: 'DS_LOAIHINH_TB', option: val }).then((rep) => {
        })
      }
    },
    'Selected.KETQUAKN_ID' (val) {
      if (val) {
        this.Button.btnNhanVienLoi = true
      } else {
        this.Button.btnNhanVienLoi = false
      }
    },
    async 'Selected.LOAITB_ID' (val) {
      if (val) {
        this.DS_CHUDEKN=await this.getDanhSachChudeKhieuNaiTheoLoaiTB(this.Selected.LOAI_ID,this.Selected.LOAITB_ID);
        //this.DS_NGUYENNHAN_LOI=await this.getDanhSachNguyenNhanLoi(this.Selected.CHUDEKN_ID,this.Selected.LOAITB_ID);
      }
    },
    async 'Selected.CHUDEKN_ID' (val) {
      if (val) {
        console.log('🚀 ~ file: TiepNhanKN.vue:289 ~ Selected.CHUDEKN_ID', val)
        await this.cboChuDeKN_SelectedChanged(val)
      }
    },
    async 'Selected.NGUYENNHAN_ID' (val) {
      if (val) {
        let ketqua=this.DS_NGUYENNHAN_LOI.filter(item=>item.NGUYENNHAN_ID==this.Selected.NGUYENNHAN_ID)[0]
        this.KETQUA_KHIEUNAI.DOITUONG_LOI=ketqua.DOITUONG_LOI_ID
        this.Selected.PAXL_ID=ketqua.GIAM_CUOC==1?"1":"0"
        console.log('da toi day',ketqua);
      }
    },

    async 'Selected.LOAI_ID' (val) {
      if (val !== null) {
        if (val === 5) {
          this.ReadOnly.txtDiaChi = true
        } else {
          this.ReadOnly.txtDiaChi = false
        }
        let LOAITB_ID = null
        if (this.Selected.LOAITB_ID === '' || this.Selected.LOAITB_ID === 0) {
          LOAITB_ID = 1
        } else {
          LOAITB_ID = this.Selected.LOAITB_ID
        }
        this.DS_CHUDEKN=await this.getDanhSachChudeKhieuNaiTheoLoaiTB(this.Selected.LOAI_ID,this.Selected.LOAITB_ID);

      }
    }
  },
  methods: {
    ...mapActions('khieunai/TiepNhanKN', actionName),
    status (validation) {
      return {
        error: validation.$error,
        dirty: validation.$dirty
      }
    },
    showKetQuaXuLy () {
      this.showContent.showKetQuaXuLy = !this.showContent.showKetQuaXuLy
    },
    handleCommand (e) {
      alert(e.rowData)
    },
    async tsbtnNhapExcel () {
      this.$refs.ModalDocFile.show().then((result) => {
        if (result.ok) {
        } else {

        }
      })
    },
    onAcceptDiaChi (data) {
      this.NGUOI_KHIEUNAI.DIACHI_LH = data.diachimoi
      this.DiaDanh.Ap_id = data.ap_id
      this.DiaDanh.DacDiem_id = data.dacdiem_id
      this.DiaDanh.Khu_id = data.khu_id
      this.DiaDanh.Pho_id = data.pho_id
      this.DiaDanh.Phuong_id = data.phuong_id
      this.DiaDanh.Quan_id = data.quan_id
      this.DiaDanh.Tinh_id = data.tinh_id
      this.DiaDanh.SoNha = data.sonha
      this.DiaDanh.diachi= data.diachimoi
      this.$refs.DIACHI_LH.focus()

    },
    txtEmail_Leave () { },
    async tsbtnHSKN_Click () {
      if (this.isEmptyObj(this.KHIEUNAI)) return
      if (this.KHIEUNAI.KHIEUNAI_ID === null) return
      this.$refs.frmHoSoKhieuNai.khieunai_id = this.KHIEUNAI.KHIEUNAI_ID
      this.$refs.frmHoSoKhieuNai.show()
    },
    async bntLayMaKh_Click () {
      let modal = this.$refs['ModalTraCuuDanhBa']
      modal.setData(null)
      modal.show()
    },
    async HienThiTheoID (khieunai_id) {
      try {
        await this.lay_ds_khieunai_theo_id(khieunai_id)
      } catch (error) {
      }
    },
    async lay_ds_khieunai_theo_id (KHIEUNAI_ID) {
      await this.getDS_TB_KHIEUNAI_ID({ KHIEUNAI_ID })
    },
    async LookupByAccount (vmatb, dichvuvt_id, loaitb_id) {
      await this.tsbtnNhapMoi_Click()
      this.txtMaTB = vmatb
      this.Selected.DICHVUVT_ID = Number(dichvuvt_id)
      this.Selected.LOAITB_ID = Number(loaitb_id)
      await this.txtMaTB_KeyPress()
    },
    async btnDiaChi_Click () {
      let tinhId = this.$root.token.getPhanVungID()
      this.dataDiaChi.tinh_id = this.DiaDanh.Tinh_id === 0 || this.DiaDanh.Tinh_id === undefined ? tinhId.toString() : this.DiaDanh.Tinh_id.toString()
      this.dataDiaChi.quan_id = this.DiaDanh.Quan_id
      this.dataDiaChi.phuong_id = this.DiaDanh.Phuong_id
      this.dataDiaChi.pho_id = this.DiaDanh.Pho_id
      this.dataDiaChi.khu_id = this.DiaDanh.Khu_id
      this.dataDiaChi.ap_id = this.DiaDanh.Ap_id
      this.dataDiaChi.sonha = this.DiaDanh.SoNha
      this.dataDiaChi.dacdiem_id = this.DiaDanh.DacDiem_id
      //this.$refs.diaChiModal.showModal()
    },
    onChonThueBao (value) {
      try {
        if (value !== null) {
          this.txtMaTB = value.ma_tb
          this.$refs.txtMaTB.focus()
        }
      } catch (error) {
      }
    },
    async tsbtnGiaoPhieu_Click () {
      try {
        if (this.DS_TB_KHIEUNAI.length > 0) {
          if (this.KHIEUNAI.KHIEUNAI_ID === null) return
          if (this.KHIEUNAI.HTKN_ID === this.HINHTHUC_KN.VAN_BAN) {
            let data =
            {
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
          let data =
          {
            VQUYTRINH_ID: quytrinh_id,
            VTHUTU_ID: 2
          }
          let rs = await this.getHUONGGIAO_QUYTRINH(data)
          if (rs.data.length > 0) {
            let vhuonggiao_id = parseInt(rs.data[0].HUONGGIAO_ID)
            let data =
            {
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
              var ds = rs.data.filter(e => e.HUONGGIAO_ID === vhuonggiao_id)
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
      } catch (error) {
      }
    },
    hide () {
      this.getTB_KHIEUNAI()
    },
    onChange () { },
    getHeaderTemplateChonNhieu (parent) {
      return function () {
        return {
          template: {
            template: `
                            <div class="check-action" style="display: inline; left: 5px;">
                                <input type="checkbox" v-model="parent.checkAll" class="check"/>
                                <span class="name"></span>
                            </div>
                        `,
            data () {
              return {
                parent: parent
              }
            }
          }
        }
      }
    },
    // getHeaderTemplateChonAllSuCoCpn (parent) {
    //   return function () {
    //     return {
    //       template: {
    //         template: `
    //                         <div class="check-action">
    //                             <input type="checkbox" v-model="parent.checkAll" class="check"/>
    //                             <span class="name"></span>
    //                         </div>
    //                     `,
    //         data () {
    //           return {
    //             parent: parent
    //           }
    //         }
    //       }
    //     }
    //   }
    // },
    getColumnTemplateChonNhieu (parent) {
      return function () {
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
            data () {
              return {
                parent: parent,
                data: {}
              }
            }
          }
        }
      }
    },
    // getColumnTemplateChonSuCoCpn (parent) {
    //   return function () {
    //     return {
    //       template: {
    //         template: `
    //                         <div class="check-action">
    //                             <input
    //                                 type="checkbox"
    //                                 class="check"
    //                                 v-model="parent.dsIdChon"
    //                                 :value="data.KHIEUNAI_ID"/>
    //                             <span class="name"></span>
    //                         </div>
    //                     `,
    //         data () {
    //           return {
    //             parent: parent,
    //             data: { }
    //           }
    //         }
    //       }
    //     }
    //   }
    // },
    async btnChenNo_Click () {
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
      await this.NAPCOMBO_TGC_KGC(this.THUEBAO.MA_TT)
      await this.NAP_DS_TIENGC(this.KHIEUNAI.KHIEUNAI_ID)
    },
    gridGC_refresh () { this.$refs.gridGC.refresh() },
    clickAddNew () { this.addRowGC() },
    themNo: function (args) {
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
    addRowGC () {
      if (this.isEmptyObj(this.THUEBAO)) return
      try {
        var tmp = [...this.DsGC]
        tmp.push({ THANG_GC: null, KY_GC: null, TIEN: 0, TIEN_GC: 0, VAT_GC: 0, ISUPDATE: 0, ISADDNEW: 0 })
        this.setState({ key: 'DsGC', value: tmp })
        this.$refs.gridGC.refresh()
      } catch (error) {
      }
    },
    clickHandler (args) {
      if (args.item.id === 'add') {
        this.addRowGC()
      }
    },
    async loadData () {
      try {
        await Promise.all([
          this.getDICHVU_VT({ VDICHVUVT_ID: '' }),
          this.getDANHMUC({ loai: 'DS_LOAI_KH', option: '' }),
          this.getDANHMUC({ loai: 'DS_DOITUONG', option: '' }),
          this.getDANHMUC({ loai: 'DS_HINHTHUC_KN', option: '' }),
          this.getDANHMUC({ loai: 'DS_LOAI_KN', option: '' }),
          this.getDANHMUC({ loai: 'DS_KETQUA_KN', option: '' }),
          this.getDANHMUC({ loai: 'DS_HINHTHUC_TL', option: '' }),
          this.getDANHMUC({ loai: 'DS_MD_HAILONG', option: '' }),
          this.getDANHMUC({ loai: 'DS_NHANVIEN', option: this.$root.token.getNhanVienID() }),
          // this.getDANHMUC({loai: 'DS_KQ_LOI_KN', option: 2})
        ])
        if (this.DS_NHANVIEN.length > 0) this.Selected.NHANVIEN_ID = this.DS_NHANVIEN[0].NHANVIEN_ID
        if (this.DS_HINHTHUC_KN.length > 0) this.Selected.HTKN_ID = 2
        // if (this.DS_KQ_LOI_KN.length > 0) this.Selected.KETQUAKN_ID = this.DS_KQ_LOI_KN[0].KETQUAKN_ID

        // var dt = await this.LAY_DS_THAMSO_MD()
        // if (dt.length > 0) {
        //   let strvtemp = 'THEM_KHDB_KHI_KHIEUNAI'
        //   let o = dt.find(e => e.MA_TS === strvtemp)
        //   if (o !== undefined) {
        //     if (o.TEN_TS.toString() === '1') {
        //       this.THEM_KHDB_KHI_KHIEUNAI = 1
        //     }
        //   }
        // }

        var dt = await this.LAY_DS_THAMSO_MD2({ P_THAMSO: 'THEM_KHDB_KHI_KHIEUNAI' })
        if (dt.length > 0) {
          let strvtemp = 'THEM_KHDB_KHI_KHIEUNAI'
          let o = dt.find(e => e.MA_TS === strvtemp)
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
    btnNhanVienLoi_Click () {
      if (this.KHIEUNAI.KHIEUNAI_ID === null) return
      this.$refs.ModalNhanVienLoi.show(this.KHIEUNAI.KHIEUNAI_ID).then((result) => {
        if (result.ok) {
        } else {

        }
      })
    },
    SetButton (kieu) {
      this.Button.tsbtnNhapMoi = false
      this.Button.tsbtnGhiLai = false
      this.Button.tsbtnXoa = false
      this.Button.tsbtnHuyBo = false
      if (kieu === -1) // Bat dau
      {
        this.Button.tsbtnGhiLai = true
        this.Button.tsbtnHuyBo = true
      }
      if (kieu === 0) // Bat dau
      {
        this.$refs.txtMaTB.focus()
        this.Button.tsbtnNhapMoi = true
      }
      if (kieu === 1)// Them moi
      {
        this.$refs.txtMaTB.focus()
        this.Button.tsbtnGhiLai = true
        this.Button.tsbtnHuyBo = true
        this.Button.tsbtnXoa=false
        this.Button.tsbtnDocFile=true
        if (this.DS_LOAI_KN.length > 0) {
          let khieuNaiNghiepVu = this.DS_LOAI_KN.find(e => e.TEN_LOAI === 'Khiếu nại nghiệp vụ')
          if (khieuNaiNghiepVu) {
            this.Selected.LOAI_ID = khieuNaiNghiepVu.LOAI_ID
          } else { this.Selected.LOAI_ID = this.DS_LOAI_KN[0].LOAI_ID }
        }
        this.Clear()
      }
      if (kieu === 2)// Huy
      {
        this.Button.tsbtnNhapMoi = true
        this.Button.tsbtnXoa = true
      }
      if (kieu === 3)// Edit
      {
        this.Button.tsbtnNhapMoi = true
        this.Button.tsbtnXoa = true
        this.Button.tsbtnGhiLai = true
        this.Button.tsbtnHuyBo = false
      }
    },
    async getTB_KHIEUNAI () {
      try {
        this.$v.$reset()
        this.dsIdChon = []
        this.KHIEUNAI = {
          ND_KHIEUNAI: '',
          KY_KN: '',
          KHIEUNAI_ID: null
        }
        this.THUEBAO = {}
        this.NGUOI_KHIEUNAI={}
        this.$root.showLoading(true)
        var data = {
          TTKN_ID: 1,
          HUONGGIAO_ID: 0,
          TTPH_ID:1,
          DA_GIAO:0
        }
        await this.getDS_TB_KHIEUNAI_V2(data)
        data = {
          TTKN_ID: 6,
          HUONGGIAO_ID: 0,
          TTPH_ID:1,
          DA_GIAO:0
        }
        await this.getDS_TB_KHIEUNAI_DAKHOA(data)

        data = {
          TTKN_ID: 2,// lay phieu da giao xu lý
          HUONGGIAO_ID: 0,
          TTPH_ID:1,
          DA_GIAO:0
        }
        await this.getDS_TB_KHIEUNAI_DAGIAO_V2(data)
        if (this.activetab==1)
        if (this.DS_TB_KHIEUNAI.length > 0) {
          await this.grid_SelectedRowChanged(this.DS_TB_KHIEUNAI[0])
          this.SetButton(3)
        } else {
          this.SetButton(1)
        }
        if (this.activetab==2)
        if (this.DS_TB_KHIEUNAI_DAGIAO.length > 0) {
          await this.grid_SelectedRowChanged(this.DS_TB_KHIEUNAI_DAGIAO[0])
          this.SetButton(3)
        } else {
          this.SetButton(1)
        }
        if (this.activetab==3)
        if (this.DS_TB_KHIEUNAI_DAKHOA.length > 0) {
          await this.grid_SelectedRowChanged(this.DS_TB_KHIEUNAI_DAKHOA[0])
          this.SetButton(3)
        } else {
          this.SetButton(1)
        }
      } catch (error) {
      } finally { } (
        this.$root.showLoading(false)
      )
    },
    async Clear () {
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
      this.NGUOI_KHIEUNAI={}
      this.daily_id = -1
      this.txtNGUYENNHAN = ''
      this.THUEBAO = {}
      this.dtpNgayCapCMT = null
      await this.getNgay_Cn()
      this.dtpTuNgay = this.Ngay_Cn
      this.dtpDenNgay = moment(this.Ngay_Cn).clone().add(2, 'days').toDate()
      this.dtpNgayTN = this.Ngay_Cn
      this.dtpNgayXL = moment(new Date()).format('DD/MM/YYYY hh:mm:ss')
      this.setState({ key: 'DS_TIENGC', value: [] })
      this.setState({ key: 'DS_KHIEUNAI_HT', value: [] })
      this.setState({ key: 'DS_CHITIETNO_THEO_KHOANMUC', value: [] })
      this.setState({ key: 'DSThangGC', value: [] })
      this.setState({ key: 'DSThangApDung', value: [] })
      this.setState({ key: 'DsGC', value: [] })
      this.$v.$reset()
    },
    async getNgay_Cn () {
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
    async HienThiTTNo (kieu) {
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
            'MA_TB': this.THUEBAO.MA_TB,
            'MA_TT': this.THUEBAO.MA_TT,
            'KYCUOC': moment(this.Kyhoadon).format('YYYYMM') + '01'
          }
          var rps = await this.getCHITIETNO_THEO_KHOANMUC(data)
          this.HienThi_ChitietNo_TheoKhoanMuc(rps.data, kieu)
        } else {
          this.setState({ key: 'DS_CHITIETNO_THEO_KHOANMUC', value: [] })
        }
      } catch (error) {
      }
    },
    DS_LOAI_KN_SelectedValueChanged () { },
    async HienThi_ChitietNo_TheoKhoanMuc (data, kieu) {
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
    async HienThi_LichSu_KhieuNai (THUEBAO_ID) {
      try {
        var data = {
          THUEBAO_ID: THUEBAO_ID,
          KHIEUNAI_ID: 0
        }
        await this.getDS_KHIEUNAI_HT(data)
      } catch (error) {

      }
    },
    async grid_LSKN_rowSelected(args){
      this.ls_khieunai_id=args.data.KHIEUNAI_ID
      console.log('ls_khieunai_id',this.ls_khieunai_id)
    },
    async NAPCOMBO_TGC_KGC (MA_TT) {
      try {
        if (MA_TT === '') return false
        var kyhd = '01' + moment(this.Kyhoadon).format('MMYYYY')
        var kynext = moment(this.Kyhoadon).clone().add(1, 'months').toDate()
        this.ky_kn = ''
        if (this.Button.tsbtnNhapMoi === false) {
          kyhd = '01' + moment(this.Kyhoadon).format('MMYYYY')
          kynext = moment(this.Kyhoadon).clone().add(1, 'months').toDate()
          this.ky_kn = moment(this.Kyhoadon).format('YYYYMM') + '01'
        } else {
          var ky = this.KHIEUNAI.KY_KN
          if (ky !== null && ky !== '' && ky !== undefined) {
            this.ky_kn = ky
            kyhd = '01' + this.ky_kn.substring(4, 6) + this.ky_kn.substring(0, 4)
            kynext = moment(kyhd, 'DDMMYYYY').clone().add(1, 'months').toDate()
          } else {
            this.ky_kn = moment(this.Kyhoadon).format('YYYYMM') + '01'
            kyhd = '01' + moment(this.Kyhoadon).format('MMYYYY')
            kynext = moment(this.Kyhoadon).clone().add(1, 'months').toDate()
          }
        }
        var kyhd_next = moment(kynext).format('YYYYMM') + '01'
        var data = {
          MA_TT: MA_TT,
          MA_TB: this.txtMaTB,
          KYHD: this.ky_kn,
          KYHD_NEXT: kyhd_next
        }
        var rps = await this.getDS_THANG_GC(data)
        if (rps.data && rps.data.length > 0) {
          this.DS_ThangGC = rps.data
        } else {
          this.DS_ThangGC = []
        }
        this.setState({ key: 'DSThangGC', value: this.DS_ThangGC })
        data = {
          VKYHD: this.ky_kn,
          VKYHD_NEXT: kyhd_next
        }
        var rps2 = await this.getKY_APDUNG(data)
        if (rps2.data && rps2.data.length > 0) {
          this.DS_ThangApDung = rps2.data
        } else {
          this.DS_ThangApDung = []
        }
        this.setState({ key: 'DSThangApDung', value: this.DS_ThangApDung })
      } catch (error) {
      }
    },
    async NAP_DS_TIENGC (VKHIEUNAI_ID) {
      try {
        this.setState({ key: 'DsGC', value: [] })
        var data = {
          VKHIEUNAI_ID: VKHIEUNAI_ID === null ? 0 : VKHIEUNAI_ID
        }
        var rps = await this.getDULIEU_TIENGC(data)
      } catch (error) {
      }
    },
    async Lay_chudekn_theo_lhtb_plkh_v2 (LOAI_ID, LOAITB_ID, THUEBAO_ID) {
      let data = {
        LOAI_ID, LOAITB_ID, THUEBAO_ID
      }
      await this.getLAY_CHUDEKN_THEO_LHTB_PLKH_V2(data)
    },
    async HienThiTTThueBao (THUEBAO, kieu, index) {
      try {
        this.THUEBAO = { ...THUEBAO }
        this.Selected.DOITUONG_ID = this.THUEBAO.DOITUONG_ID
        this.Selected.DICHVUVT_ID = this.THUEBAO.DICHVUVT_ID
        this.Selected.LOAITB_ID = this.THUEBAO.LOAITB_ID
        if ('DAILY_ID' in this.THUEBAO) {
          if (this.THUEBAO.DAILY_ID !== null) {
            this.daily_id = Number(this.THUEBAO.DAILY_ID)
          } else {
            this.daily_id = 0
          }
        }

        await this.NAPCOMBO_TGC_KGC(this.THUEBAO.MA_TT)
        await this.NAP_DS_TIENGC(this.KHIEUNAI.KHIEUNAI_ID)
        var response = await this.getLAY_DS_DBKH_THEO_KHID({ KHACHHANG_ID: this.THUEBAO.KHACHHANG_ID })
        if (response.data.length > 0) {
          this.Selected.LOAIKH_ID = response.data[0].LOAIKH_ID
          this.THUEBAO.MA_KH = response.data[0].MA_KH
          let PHANLOAI_KH = await this.MAP_ID('TEN_PLKH', 'CSS', 'PHANLOAI_KH', 'PHANLOAIKH_ID', response.data[0].PHANLOAIKH_ID)
          this.THUEBAO.PHANLOAI_KH = PHANLOAI_KH
          if (kieu === 1) {
            this.NGUOI_KHIEUNAI.NGUOI_KN = response.data[0].TEN_KH
            this.NGUOI_KHIEUNAI.DIENTHOAI_LH = response.data[0].SO_DT
            this.NGUOI_KHIEUNAI.DIACHI_LH = response.data[0].DIACHI_KH
            this.KHIEUNAI.KY_KN = ''
            this.KHIEUNAI.ND_KHIEUNAI = ''
            this.KHIEUNAI.LYDO_TON = ''
            if (response.data[0].LOAIGT_ID !== null) {
              if (response.data[0].LOAIGT_ID === 1) {
                this.NGUOI_KHIEUNAI.CMT = response.data[0].SO_GT
                this.NGUOI_KHIEUNAI.NGAYCAP = response.data[0].NGAYCAP
                this.NGUOI_KHIEUNAI.NOICAP = response.data[0].NOICAP
                this.NGUOI_KHIEUNAI.EMAIL = response.data[0].EMAIL
              } else {
                this.NGUOI_KHIEUNAI.CMT = null
                this.NGUOI_KHIEUNAI.NGAYCAP = null
                this.NGUOI_KHIEUNAI.NOICAP = null
                this.NGUOI_KHIEUNAI.EMAIL = null
              }
            }
          }
        }
        // if (kieu === 1) {
        //   await this.Lay_chudekn_theo_lhtb_plkh_v2(this.Selected.LOAI_ID, this.Selected.LOAITB_ID, this.THUEBAO.THUEBAO_ID)
        // }
        await this.HienThiTTNo(1)
        await this.HienThi_LichSu_KhieuNai(this.THUEBAO.THUEBAO_ID)
      } catch (error) {
      }
    },
    isEmptyObj (Obj) {
      var isEmptyObj = !Object.keys(Obj).length
      return isEmptyObj
    },
    async KiemTraDuLieu () {
      if (this.Button.tsbtnNhapMoi === true) {
        if (this.isEmptyObj(this.KHIEUNAI) || this.DS_TB_KHIEUNAI.length === 0) {
          this.loading(false)
          await this.$alert('Hãy chọn thuê bao trên lưới danh sách khiếu nại để cập nhật thông tin !', 'Thông báo', {
            confirmButtonText: 'Đóng',
            callback: action => {
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

      if(!(this.NGUOI_KHIEUNAI.EMAIL == null || this.NGUOI_KHIEUNAI.EMAIL == undefined))
      {
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
              this.$root.$toast.error('Bạn không thể cập nhật dữ liệu do thuê bao : ' + this.txtMaTB + ' chưa có khiếu nại')
              this.loading(false)
              return false
            } else {
              if (!this.isEmptyObj(this.KHIEUNAI)) {
                if (this.KHIEUNAI.THUEBAO_ID !== ds[0].THUEBAO_ID) {
                  this.$root.$toast.error("Bạn không thể cập nhật thuê bao '" + this.txtMaTB + "' cho khiếu nại '" + this.KHIEUNAI.MA_KN + "' !")
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
            }).then(() => {
              chk = true
              this.loading(true)
            }).catch(() => {
              return false
            })
            if (!chk) return false
          }
        }
      } else {
        if (this.NGUOI_KHIEUNAI.DIACHI_LH === '' || this.NGUOI_KHIEUNAI.DIACHI_LH === null || this.NGUOI_KHIEUNAI.DIACHI_LH === undefined) {
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
    async KIEMTRA_TT_MaTB (THUEBAO_ID) {
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
    async Lay_thongtin_kn_theo_ma_tb (txtMaTB) {
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
    async TaoMaKN () {
      var data = {
        NAM: ''
      }
      var rps = await this.getTaoMaKN(data)
      if (rps.data.length > 0) {
        this.KHIEUNAI.MA_KN = rps.data[0].MA_KN
      }
    },
    focusRowThemNo: function (value) {
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
    async TaoDuLieuAo (themmoi) {
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
      row.TTKN_ID = 1
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
      row.NGUYENNHAN_ID = this.Selected.KETQUAKN_ID
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
          let ds = await this.LAY_DONVI_THEO_LOAIDV_DIACHI_KHIEUNAI(this.LOAI_DV.TRAM_VT, this.DiaDanh.Phuong_id, this.DiaDanh.Pho_id, this.DiaDanh.Ap_id, this.DiaDanh.Khu_id, this.DiaDanh.DacDiem_id)
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
    createID: function () {
      return Math.floor(Math.random() * 10000).toString()
    },
    async SINH_MATB_KHIEUNAI_AO () {
      try {
        var rs = await this.$root.context.post(
          '/web-gqkn/tiep-nhan-khieu-nai/sinh_matb_khieunai_ao'
        )
        if (rs.data.length > 0) {
          return rs.data[0].MATB_KHIEUNAI_AO
        } else { return null }
      } catch (error) {
        return null
      }
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
    async getDanhSachChudeKhieuNaiTheoLoaiTB (loai,loaitbid) {
      try {
        let param={
          loaiId:loai,
          loaiTBId:loaitbid
        }
        var rs = await this.$root.context.post(
          '/web-gqkn/khieunai/lay-danhsach-chude-theo-loaitb',param
        )
        if (rs.data.length > 0) {
          return rs.data
        } else { return null }
      } catch (error) {
        return null
      }
    },
    async LAY_DONVI_THEO_LOAIDV_DIACHI_KHIEUNAI (LOAIDV_ID, PHUONG_ID, PHO_ID, AP_ID, KHU_ID, DACDIEM_ID) {
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
          '/web-gqkn/tiep-nhan-khieu-nai/lay_donvi_theo_loaidv_diachi_khieunai', data
        )
        if (rs.data.length > 0) {
          return rs.data
        } else { return [] }
      } catch (error) {
        return []
      }
    },
    async TaoDuLieu (themmoi) {
      this.dsKhieuNai = []
      var row = {}
      if (themmoi) {
        await this.TaoMaKN()
        row.KY_KN = moment(this.Kyhoadon).format('YYYYMM') + '01'
        await this.getNgay_Cn()
        this.dtpNgayTN = this.Ngay_Cn
      } else {
        row.KY_KN = this.KHIEUNAI.KY_KN
      }

      row.MA_TB = this.THUEBAO.MA_TB
      row.KHIEUNAI_ID = this.KHIEUNAI.KHIEUNAI_ID !== undefined ? this.KHIEUNAI.KHIEUNAI_ID : null
      row.CONGVAN_XL = this.KHIEUNAI.CONGVAN_XL !== undefined ? this.KHIEUNAI.CONGVAN_XL : null
      row.MA_KN = this.KHIEUNAI.MA_KN
      row.NGAY_TN = moment(this.dtpNgayTN).format('DD/MM/YYYY H:mm:ss')
      row.NGUOI_KN = this.NGUOI_KHIEUNAI.NGUOI_KN
      row.DIENTHOAI_LH = this.NGUOI_KHIEUNAI.DIENTHOAI_LH !== undefined ? this.NGUOI_KHIEUNAI.DIENTHOAI_LH : null
      row.THUEBAO_ID = this.THUEBAO.THUEBAO_ID
      row.THANHTOAN_ID = this.THUEBAO.THANHTOAN_ID
      row.DICHVUVT_ID = this.Selected.DICHVUVT_ID
      row.LOAITB_ID = this.Selected.LOAITB_ID
      row.TTKN_ID = 1
      row.DONVI_ID = this.$root.token.getDonViID()
      row.NHANVIEN_ID = this.$root.token.getNhanVienID()
      row.CHUDEKN_ID = this.Selected.CHUDEKN_ID
      row.ND_KHIEUNAI = this.KHIEUNAI.ND_KHIEUNAI !== undefined ? this.KHIEUNAI.ND_KHIEUNAI : null
      row.QUYTRINH_ID = this.quytrinh_id
      row.GHICHU_KN=this.KHIEUNAI.GHICHU_KN
      row.LAP_LAI=this.KHIEUNAI.LAP_LAI?1:0
      row.KHAN_CAP=this.KHIEUNAI.KHAN_CAP?1:0
      row.VUOT_CAP=this.KHIEUNAI.VUOT_CAP?1:0
      if (this.optXuLy === '0') {
        row.NOIDUNG_GQ = '';
        row.ND_XULY=''
        row.NGUYENNHAN_ID=null
        row.PAXL_ID=null
        row.HAILONG = null
        row.HTTL_ID = null
      } else {
        row.NGUYENNHAN_ID = this.Selected.NGUYENNHAN_ID
        row.ND_XULY=this.KETQUA_KHIEUNAI.ND_PHUONGAN_XULY!=undefined?this.KETQUA_KHIEUNAI.ND_PHUONGAN_XULY:null;
        row.NOIDUNG_GQ = this.KETQUA_KHIEUNAI.ND_TRALOI!== undefined ? this.KETQUA_KHIEUNAI.ND_TRALOI : null
        row.PAXL_ID = this.Selected.PAXL_ID
        row.HAILONG = this.Selected.HAILONG
        row.HTTL_ID = this.Selected.HTTL_ID
      }
      if (this.chkHenTL) {
        row.HENGQ_TU = moment(this.dtpTuNgay).format('DD/MM/YYYY')
        row.HENGQ_DEN = moment(this.dtpDenNgay).format('DD/MM/YYYY')
      } else {
        row.HENGQ_TU = null
        row.HENGQ_DEN = null
      }
      row.CMT = this.NGUOI_KHIEUNAI.SO_GT !== undefined ? this.NGUOI_KHIEUNAI.SO_GT : null
      row.EMAIL = this.NGUOI_KHIEUNAI.EMAIL !== undefined ? this.NGUOI_KHIEUNAI.EMAIL : null
      row.NGAYCAP =this. NGUOI_KHIEUNAI.NGAYCAP ? moment(this. NGUOI_KHIEUNAI.NGAYCAP).format('DD/MM/YYYY') : null

      row.NGAY_BN = this.dtpNgayBN ? moment(this.dtpNgayBN).format('DD/MM/YYYY H:mm') : null
      row.NOICAP = this.NGUOI_KHIEUNAI.NOICAP !== undefined ? this.NGUOI_KHIEUNAI.NOICAP : null

      row.LOAIGT_ID = this.Selected.LOAIGT_ID !== undefined ? this.Selected.LOAIGT_ID : null
      row.DIACHI_LH = this.NGUOI_KHIEUNAI.DIACHI_LH !== undefined ? this.NGUOI_KHIEUNAI.DIACHI_LH : null

      row.HTKN_ID = this.Selected.HTKN_ID
      row.MA_TT = this.THUEBAO.MA_TT
      row.MA_KH = this.THUEBAO.MA_KH




      this.dsKhieuNai.push(row)
    },
    async TaoDuLieu_HDTBDV () {
      try {
        this.dskhieunai_dv = []
        let data = {
          QUYTRINH_ID: this.quytrinh_id
        }
        await this.getHUONGGIAO_LDV(data)
        let inloaidv_id = 0
        let indonvi_id = 0
        for (let index = 0; index < this.DS_HUONGGIAO_LDV.length; index++) {
          const element = this.DS_HUONGGIAO_LDV[index]
          inloaidv_id = parseInt(element.LOAIDV_DICH_ID)
          if (inloaidv_id === this.LOAI_DV.DAILY_XLKN || inloaidv_id === this.LOAI_DV.TOKD_XLKN) {
            let data = {
              TENBANG: 'NHANVIEN_TC',
              GIATRI: this.daily_id,
              LOAIDV_ID: inloaidv_id,
              LOAINV_ID: 40
            }
            let rps = await this.getLAY_DONVI_NGUOIDUNG_THEOBANG(data)
            if (rps.data !== null && rps.data.length > 0) {
              indonvi_id = parseInt(rps.data[0].DONVI_ID)
            } else {
              if (inloaidv_id === this.LOAI_DV.DAILY_XLKN) {
                this.loading(false)
                await this.$alert('Không tìm thấy đại lý để giao xử lý khiếu nại. Liên hệ admin để giải quyết !', 'Thông báo', {
                  confirmButtonText: 'Đóng',
                  callback: action => {
                  }
                })
              }
              if (inloaidv_id === this.LOAI_DV.TOKD_XLKN) {
                this.loading(false)
                await this.$alert('Không tìm thấy Tổ Kinh Doanh để giao xử lý khiếu nại. Liên hệ admin để giải quyết !', 'Thông báo', {
                  confirmButtonText: 'Đóng',
                  callback: action => {
                  }
                })
              }
              return false
            }
          } else {
            let data = {
              THUEBAO_ID: this.THUEBAO.THUEBAO_ID,
              LOAIDV_ID: inloaidv_id,
              QUYTRINH_ID: this.quytrinh_id
            }
            let rps = await this.getLAY_DONVI_NHAN_KHIEUNAI(data)
            if (rps.data === null || rps.data.length <= 0) {
              this.loading(false)
              await this.$alert('Loại đơn vị có ID = ' + inloaidv_id + ' chưa được gán đơn vị. Liên hệ Admin để gán !', 'Thông báo', {
                confirmButtonText: 'Đóng',
                callback: action => {
                }
              })
              return false
            }
            indonvi_id = rps.data[0].DONVI_ID
          }
          let row = {}
          row.KHIEUNAI_ID = this.KHIEUNAI.KHIEUNAI_ID !== undefined ? this.KHIEUNAI.KHIEUNAI_ID : null
          row.LOAIDV_ID = inloaidv_id
          row.DONVI_ID = indonvi_id
          this.dskhieunai_dv.push(row)
        }
        return true
      } catch (error) {
        return false
      }
    },
    createNoItem: function () {
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
    checkEmptyNumber: function (args) {
      return args === undefined || args == null
    },
    async GetKey (Key, SCHEMA) {
      try {
        var data = {
          VKEYNAME: Key,
          VKEY_SCHEMA: SCHEMA
        }
        var rs = await this.$root.context.post(
          '/web-gqkn/tiep-nhan-khieu-nai/get_key', data
        )
        if (rs.data.length > 0) {
          return rs.data[0].KEY
        } else { return null }
      } catch (error) {
        return null
      }
    },
    async TaoDuLieu_DiaChi (themmoi) {
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
    async KiemTraDuLieu_KhoaPhieu () {
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

      if (this.Selected.KETQUAKN_ID === '' || this.Selected.KETQUAKN_ID === null) {
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
    async MAP_ID (P_MAP, P_SCHEMA, P_TABLENAME, P_PRIMARY, P_VALUE) {
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
    async tsbtnKhoaPhieu_Click () {
      if (!this.Button.tsbtnKhoaPhieu) return false
      try {
        if (this.DS_TB_KHIEUNAI.length > 0) {
          this.loading(true)
          let kt = await this.KiemTraDuLieu_KhoaPhieu()
          if (kt === false) return false
          if (!this.isEmptyObj(this.KHIEUNAI)) {
            var khieunai_id = this.KHIEUNAI.KHIEUNAI_ID
            var phieukn_id = this.KHIEUNAI.PHIEUKN_ID
            var vquytrinh_id = this.KHIEUNAI.QUYTRINH_ID
            var hg_phieu_id = 0
            var data = {
              QUYTRINH_ID: vquytrinh_id,
              TTKN_ID: 5
            }
            var rps = await this.getLAY_HG_GQKN(data)
            if (rps.data && rps.data.length > 0) {
              hg_phieu_id = rps.data[0].HUONGGIAO_ID
            }

            data = {
              PHIEUKN_ID: phieukn_id,
              KHIEUNAI_ID: khieunai_id,
              LYDO_TON: ''
            }
            rps = await this.setUPDATE_LYDO_KHONGXL_DUOC(data)
            data = {
              KHIEUNAI_ID: khieunai_id,
              PHIEUKN_ID: phieukn_id,
              HUONGGIAO_ID: hg_phieu_id,
              DONVI_GIAO_ID: this.$root.token.getDonViID(),
              DONVI_NHAN_ID: this.$root.token.getDonViID(),
              NHANVIEN_ID: this.$root.token.getNhanVienID()
            }
            rps = await this.setGIAOPHIEU_KN_KHI_HC(data)

            data = {
              KHIEUNAI_ID: khieunai_id,
              NGAY_GQ: moment(this.dtpNgayXL).format('DD/MM/YYYY'),
              NHANVIEN_GQ_ID: this.Selected.NHANVIEN_ID,
              NOIDUNG_GQ: this.KETQUA_KHIEUNAI.ND_TRALOI,
              ND_XULY:this.KETQUA_KHIEUNAI.ND_PHUONGAN_XULY,
              ND_TON: " ",
              DONVI_ID: this.$root.token.getDonViID(),
              TTKN_ID: 6
            }
            rps = await this.setUpdate_KhoaPhieu_BaoTon_HoanThanh(data)

            data = {
              KHIEUNAI_ID: khieunai_id,
              KETQUAKN_ID: this.Selected.KETQUAKN_ID,
              NOIDUNG_GQ: this.KETQUA_KHIEUNAI.ND_TRALOI
            }
            rps = await this.setCAPNHAT_LOI(data)
            await this.CapNhatGiamCuoc(khieunai_id)

            data = {
              NHANVIEN_TH_ID: this.Selected.NHANVIEN_ID,
              KHIEUNAI_ID: khieunai_id,
              PHIEUKN_ID: phieukn_id
            }
            rps = await this.setUPDATE_TT_DAGIAO(data)

            data = {
              NHANVIEN_TH_ID: this.Selected.NHANVIEN_ID,
              KHIEUNAI_ID: khieunai_id,
              PHIEU_CHA_ID: phieukn_id
            }
            rps = await this.setCAPNHAT_TIEPNHAN_KHOAPHIEU(data)

            data = {
              KHIEUNAI_ID: khieunai_id
            }
            rps = await this.setCAPNHAT_TT_PHIEUKN_HC(data)

            data = {
              KHIEUNAI_ID: khieunai_id,
              NHANVIEN_GQ_ID: this.$root.token.getNhanVienID(),
              NOIDUNG_GQ: this.KHIEUNAI.NOIDUNG_GQ,
              ND_TON: '',
              MACV: '',
              NGUYENNHAN: this.txtNGUYENNHAN || ''
            }
            rps = await this.setUPDATE_XULY(data)


            data={
            KHIEUNAI_ID:khieunai_id,
            PAXL_ID:this.Selected.PAXL_ID,
            XACNHAN_KH_ID:this.Selected.HAILONG,
            NOIDUNG_XL:this.KETQUA_KHIEUNAI.ND_PHUONGAN_XULY
            }
            rps = await this.setUpdate_PhuongAn_XuLy(data)

            if (this.THEM_KHDB_KHI_KHIEUNAI === 1) {
              await this.Capnhat_khdb_theo_khieunai_id(this.KHIEUNAI.KHIEUNAI_ID)
            }
            this.loading(false)
            await this.$alert('Bạn đã khóa phiếu thành công !', 'Thông báo', {
              confirmButtonText: 'Đóng'
            })
            await this.getTB_KHIEUNAI()
          }
        } else {
          this.loading(false)
          await this.$alert('Chưa có khiếu nại trong danh sách.Bạn không thể khóa phiếu !', 'Thông báo', {
            confirmButtonText: 'Đóng',
            callback: action => {
            }
          })
        }
      } catch (error) {
        this.loading(false)
        await this.$alert(error, 'Thông báo', {
          confirmButtonText: 'Đóng',
          callback: action => {
          }
        })
      } finally {
        this.loading(false)
      }
    },
    async LAY_DS_THAMSO_MD () {
      try {
        var rps = await this.$root.context.get(
          '/web-gqkn/tiep-nhan-khieu-nai/lay_ds_thamso_md'
        )
        return rps.data
      } catch (error) {
        return []
      }
    },
    async LAY_DS_THAMSO_MD2 (data) {
      try {
        var rps = await this.$root.context.post(
          '/web-gqkn/tiep-nhan-khieu-nai/lay_ds_thamso_md2', data
        )
        return rps.data
      } catch (error) {
        return []
      }
    },
    async CapNhatGiamCuoc (khieunai_id) {


    },
    async txtMaTB_KeyPress () {
      this.$v.$reset()
      if (this.txtMaTB.trim() === '') {
        this.$refs.txtMaTB.focus()
        return
      }
      try {
        this.$root.showLoading(true)
        await this.getNgay_Cn()
        this.dtpNgayBN = moment(this.Ngay_Cn).clone().add(1, 'hours').toDate()
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
              callback: action => {
              }
            })
          }
        }

      } catch (error) {
      } finally {
        this.$root.showLoading(false)
      }
    },
    KiemTra_DuLieu_TienGC (DsGC) {
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
    async TAODULIEU_TIENGC (VKHIEUNAI_ID) {
      try {
        var data = ''
        if (this.DsGC.length === 0) {
          data = ''
        } else {
          const newArray = [...this.DsGC].map(item => {
            delete item.FOCUSED
            delete item.ISUPDATE
            return item
          })
          data = JSON.stringify(newArray.filter(x => x.ID !== '-1'))
        }
        var dt = {
          VKHIEUNAI_ID: VKHIEUNAI_ID === null ? 0 : VKHIEUNAI_ID,
          VDSTIENGC: data
        }
        var rps = await this.getTAODULIEU_TIENGC(dt)
      } catch (error) {
      }
    },
    async TAODULIEU_TIENGC_V2 () {
      try {
        var data = []
        if (this.DsGC.length === 0) {
          data = []
        } else {
          const newArray = [...this.DsGC].map(item => {
            delete item.FOCUSED
            delete item.ISUPDATE
            return item
          })
          data = (newArray.filter(x => x.ID !== '-1'))
        }
        return data
      } catch (error) {
        console.log('🚀 ~ file: TiepNhanKN.vue:1803 ~ TAODULIEU_TIENGC_V2 ~ error:', error)
        return []
      }
    },
    lnkKhieunai_BS_LinkClicked () {
      try {
        alert('Tham chieu den form frmKhieuNai_BS')
      } catch (error) {

      }
    },
    async Insert_KNBS (khieunai_bs, khieunai_id) {
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
      } catch (error) {

      }
    },
    async Insert_V2 (DSKHIEUNAI, DSKHIEUNAI_DV, HUONGGIAO_ID, DSDIACHI, DSDIACHIKN) {
      try {

        var DsGC = this.DsGC.filter(x => x.ID !== '-1')
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

          if (this.optXuLy === "0") {

            data = {
              PHIEUKN_ID:this.KHIEUNAI.PHIEUKN_ID,
              KHIEUNAI_ID: this.KHIEUNAI.KHIEUNAI_ID,
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
    async Insert_V4 (DSKHIEUNAI, DSKHIEUNAI_DV, HUONGGIAO_ID, DSDIACHI, DSDIACHIKN) {
      try {
        var DsGC = this.DsGC.filter(x => x.ID !== '-1')
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
    DisabledAllButton () {
      this.Button = {
        tsbtnNhapMoi: false,
        tsbtnGhiLai: false,
        tsbtnXoa: false,
        tsbtnHuyBo: false,
        tsbtnKhoaPhieu: false,
        tsbtnGiaoPhieu: false
      }
    },
    async Update_V2 (dsKhieuNai, dskhieunai_dv, huonggiao_tn, dsDiaChi, dsDiaChiKN) {

      try {
        var DsGC = this.DsGC.filter(x => x.ID !== '-1')
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
          console.log('update o day',this.optXuLy)
          if (this.optXuLy === '0') {
            data = {
              PHIEUKN_ID: this.KHIEUNAI.PHIEUKN_ID,
              KHIEUNAI_ID: KHIEUNAI_ID,
              LYDO_TON: this.KETQUA_KHIEUNAI.LYDO_TON === null ? '' : this.KETQUA_KHIEUNAI.LYDO_TON
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
      } catch (error) {
      }
    },
    gridDS_TB_KHIEUNAI_rowClicked (rowIndex, rowData) {
    },
    async kiemtra_tb_dacbiet_khieunai (vma_tb, vchudekn_id) {
      try {
        let rps = await this.$root.context.post('/web-gqkn/tiep-nhan-khieu-nai/Kiemtra_tb_dacbiet_khieunai', { vma_tb, vchudekn_id })
        if (rps.data.length > 0) {
          return rps.data[0].MSG
        } else {
          return 'ok'
        }
      } catch (error) {
        return 'ok'
      }
    },
    async Capnhat_khdb_theo_khieunai_id (khieunai_id) {
      try {
        let rps = await this.$root.context.post('/web-gqkn/tiep-nhan-khieu-nai/capnhat_khdb_theo_khieunai_id', { 'KHIEUNAI_ID': khieunai_id })
      } catch (error) {
      }
    },
    toJSON (arr) {
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
    async tsbtnGhiLai_Click () {
      if (!this.Button.tsbtnGhiLai) return false
      try {
        this.loading(true)
        if (await this.KiemTraDuLieu() === false) return false
        var data = {
          CHUDEKN_ID: this.Selected.CHUDEKN_ID
        }
        var rps = await this.getLAYHUONGGIAO_TIEPNHAN_XLKN(data)
        this.quytrinh_id = 0
        if (rps.data.length === 0) {
          this.loading(false)
          await this.$alert('Nghiệp vụ này chưa được thiết lập quy trình, bạn hãy liên hệ Admin để xử lý !', 'Thông báo', {
            confirmButtonText: 'Đóng'
          })
          return false
        }
        this.loading(true)
        if (this.Selected.LOAI_ID !== this.LOAI_CHUDEKN.PHANHOI_KHONG_THUEBAO) {
          let kiemtra_kn = await this.kiemtra_tb_dacbiet_khieunai(this.txtMaTB, this.Selected.CHUDEKN_ID)
          if (kiemtra_kn !== 'ok') {
            this.loading(false)
            let cf = await this.$confirm(kiemtra_kn,
              'Thông báo',
              {
                confirmButtonText: 'Có',
                cancelButtonText: 'Không'
              })
            console.log('🚀 ~ file: TiepNhanKN.vue:2018 ~ tsbtnGhiLai_Click ~ cf:', cf)
            if (cf !== 'confirm') return false
          }
        }

        var huonggiao_tn = parseInt(rps.data[0].HUONGGIAO_ID)
        this.quytrinh_id = parseInt(rps.data[0].QUYTRINH_ID)
        let index = this.FocusedRowHandle
        if (this.Button.tsbtnNhapMoi === false) {
          if (this.Selected.LOAI_ID !== this.LOAI_CHUDEKN.PHANHOI_KHONG_THUEBAO) {
            await this.TaoDuLieu(true)
            var re = await this.TaoDuLieu_HDTBDV()
            if (!re) return
          } else {
            await this.TaoDuLieuAo(true)
            //await this.TaoDuLieu_DiaChi(true)
          }
          if (this.DiaDanh.diachi_id!=0 &&this.DiaDanh.diachi_id!=null)
                await this.TaoDuLieu_DiaChi(true)


          await this.Insert_V4(JSON.stringify(this.dsKhieuNai[0]), JSON.stringify(this.dskhieunai_dv), huonggiao_tn, JSON.stringify(this.dsDiaChi), JSON.stringify(this.dsDiaChiKN))
          // if (this.$root.token.getPhanVungID() == 26 || this.$root.token.getPhanVungID() == 28) { await this.Insert_V3(JSON.stringify(this.dsKhieuNai[0]), JSON.stringify(this.dskhieunai_dv), huonggiao_tn, JSON.stringify(this.dsDiaChi), JSON.stringify(this.dsDiaChiKN)) } else { await this.Insert_V2(JSON.stringify(this.dsKhieuNai[0]), JSON.stringify(this.dskhieunai_dv), huonggiao_tn, JSON.stringify(this.dsDiaChi), JSON.stringify(this.dsDiaChiKN)) }
        } else {

          if (this.Selected.LOAI_ID !== this.LOAI_CHUDEKN.PHANHOI_KHONG_THUEBAO) {
            await this.TaoDuLieu(false)

            var re1 = await this.TaoDuLieu_HDTBDV()

            if (!re1) return
          } else {

            await this.TaoDuLieuAo(false)
            //await this.TaoDuLieu_DiaChi(false)
          }
          if (this.DiaDanh.diachi_id!=0 &&this.DiaDanh.diachi_id!=null)
                await this.TaoDuLieu_DiaChi(false)
           else
                await this.TaoDuLieu_DiaChi(true)


          await this.Update_V2(JSON.stringify(this.dsKhieuNai[0]), JSON.stringify(this.dskhieunai_dv), huonggiao_tn, JSON.stringify(this.dsDiaChi), JSON.stringify(this.dsDiaChiKN))
        }
        var temp = this.KHIEUNAI.KHIEUNAI_ID
        this.EnabledSelectFirstRow = false
        await this.getTB_KHIEUNAI()
        if (temp) {
          console.log('🚀 ~ file: TiepNhanKN.vue:1943 ~ tsbtnGhiLai_Click ~ temp', temp)
          this.$refs.gridDS_TB_KHIEUNAI.selectRowByRowData(temp)
        }

        if (this.THEM_KHDB_KHI_KHIEUNAI === 1) {
          await this.Capnhat_khdb_theo_khieunai_id(this.KHIEUNAI.KHIEUNAI_ID)
        }

        //this.Button.tsbtnGhiLai = true
        this.loading(false)
      } catch (error) {
        this.Button.tsbtnGhiLai = true
        this.loading(false)
      }
    },
    async onChange_DICHVUVT (event) {
    },
    async tsbtnNhapMoi_Click () {
      if (!this.Button.tsbtnNhapMoi) return false
      this.SetButton(1)
      this.Selected.HTKN_ID = 2
      this.optXuLy = '0'
      await this.getNgay_Cn()
      this.dtpNgayBN = moment(this.Ngay_Cn).clone().add(1, 'hours').toDate()
      this.txtMaTB = ''
    },
    tsbtnHuyBo_Click () {
      if (!this.Button.tsbtnHuyBo) return false
      this.SetButton(2)
      this.Clear()
    },
    tsbtnXoa_Click () {
      if (!this.Button.tsbtnXoa) return false
      try {
        if (this.DS_TB_KHIEUNAI.length === 0) {
          this.$root.$toast.error('Danh sách khiếu nại trống. Bạn không thể thực hiện xóa khiếu nại !')
        } else {
          this.$confirm(`Bạn có chắc chắn muốn xóa phiếu này không ?`,
            'Thông báo',
            {
              confirmButtonText: 'OK',
              cancelButtonText: 'Hủy',
              type: 'info'
            }).then(async () => {
            try {
              if (!this.KHIEUNAI.KHIEUNAI_ID) {
                this.$root.$toast.error('Bạn chưa chọn khiếu nại để xóa !')
                return
              }
              this.loading(true)
              const KHIEUNAI_ID = this.KHIEUNAI.KHIEUNAI_ID
              await this.getXOA_KHIEUNAI({ KHIEUNAI_ID: KHIEUNAI_ID })
              
              this.loading(false)
              this.$root.$toast.success('Xóa khiếu nại thành công !')
              this.getTB_KHIEUNAI()
            } catch (e) {
              this.loading(false)
              this.$root.$toast.error(e.message)
            }
          }).catch(() => { this.loading(false) })
        }
      } catch (error) {
        this.loading(false)
        this.$root.$toast.error(error)
      }
    },
    async cboChuDeKN_SelectedChanged (data) {
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
          //await this.getLay_nguyennhan_kn_theo_chude({ CHUDEKN_ID: parseInt(this.Selected.CHUDEKN_ID) })
          //await this.getLay_phuongan_xl_theo_chude({ CHUDEKN_ID: parseInt(this.Selected.CHUDEKN_ID) })
          this.DS_NGUYENNHAN_LOI=await this.getDanhSachNguyenNhanLoi(this.Selected.CHUDEKN_ID,this.Selected.LOAITB_ID)
          if (this.DS_NGUYENNHAN_LOI.length > 0) {
            this.Selected.KETQUAKN_ID = this.DS_NGUYENNHAN_LOI[0].NGUYENNHAN_ID
          }
          if (this.DS_PAXL.length > 0) {
            this.Selected.PAXL_ID = this.DS_PAXL[0].PAXL_ID
          }
          this.$refs.ND_KHIEUNAI.focus()
        }
      } catch (error) {
      }
    },
    async Kiemtra_chudekn_knbs (CHUDEKN_ID) {
      try {
        var data = {
          CHUDEKN_ID
        }
        var rs = await this.$root.context.post(
          '/web-gqkn/tiep-nhan-khieu-nai/kiemtra_chudekn_knbs', data
        )
        if (rs.data.length > 0) {
          return rs.data
        } else { return [] }
      } catch (error) {
        return []
      }
    },
    async HienThiTTDiaChi () {
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
    async LAY_DS_DIACHI_THEO_KHIEUNAI_ID (KHIEUNAI_ID) {
      try {
        const DS_DIACHI = await this.getDS_DIACHI_THEO_KHIEUNAI_ID({ VKHIEUNAI_ID: KHIEUNAI_ID })
        this.DS_DIACHI = DS_DIACHI.data
      } catch (error) {
        this.DS_DIACHI = []
      }
    },
    async grid_SelectedRowChanged (row) {
      this.setState({ key: 'DsGC', value: [] })
      if (row) {
        try {
          this.loading(true)
          await this.getNgay_Cn()
          this.KHIEUNAI = { ...row }
          console.log('🚀 ~ file: TiepNhanKN.vue:2105 ~ gridDS_TB_KHIEUNAI_SelectedRowChanged ~ this.KHIEUNAI', this.KHIEUNAI)

          // this.Selected.CHUDEKN_ID = parseInt(this.KHIEUNAI.CHUDEKN_ID)
          this.txtMaTB = this.KHIEUNAI.MA_TB
          this.dtpNgayCapCMT = this.KHIEUNAI.NGAYCAPCMT ? moment(this.KHIEUNAI.NGAYCAPCMT, 'DD/MM/YYYY').toDate() : null
          this.dtpNgayBN = this.KHIEUNAI.NGAY_BN ? moment(this.KHIEUNAI.NGAY_BN, 'DD/MM/YYYY H:mm').toDate() : null
          this.dtpNgayTN = this.KHIEUNAI.NGAY_TN ? moment(this.KHIEUNAI.NGAY_TN, 'DD/MM/YYYY H:mm:ss').toDate() : null
          // this.dtpNgayXL = this.Ngay_Cn
          this.Selected.DICHVUVT_ID = parseInt(this.KHIEUNAI.DICHVUVT_ID)
          this.Selected.LOAITB_ID = parseInt(this.KHIEUNAI.LOAITB_ID)
          this.Selected.HTKN_ID = parseInt(this.KHIEUNAI.HTKN_ID)
          this.Selected.LOAI_ID = parseInt(this.KHIEUNAI.LOAI_ID)
          this.Selected.LOAIGT_ID = parseInt(this.KHIEUNAI.LOAIGT_ID)
          //this.DS_CHUDEKN=await this.getDanhSachChudeKhieuNaiTheoLoaiTB(this.Selected.LOAI_ID,this.Selected.LOAITB_ID);


          this.NGUOI_KHIEUNAI.NGUOI_KN=this.KHIEUNAI.NGUOI_KN
          this.NGUOI_KHIEUNAI.NOICAP=this.KHIEUNAI.NOICAP
          this.NGUOI_KHIEUNAI.DIENTHOAI_LH=this.KHIEUNAI.DIENTHOAI_LH
          this.NGUOI_KHIEUNAI.EMAIL=this.KHIEUNAI.EMAIL
          this.NGUOI_KHIEUNAI.NGAYCAP=this.KHIEUNAI.NGAYCAPCMT ? moment(this.KHIEUNAI.NGAYCAPCMT, 'DD/MM/YYYY H:mm').toDate() : null

          this.NGUOI_KHIEUNAI.SO_GT=this.KHIEUNAI.CMT
          this.NGUOI_KHIEUNAI.DIACHI_LH=this.KHIEUNAI.DIACHI_LH
          this.KETQUA_KHIEUNAI.LYDO_TON=this.KHIEUNAI.LYDO_TON
          this.KETQUA_KHIEUNAI.ND_PHUONGAN_XULY=this.KHIEUNAI.ND_XULY
          this.KETQUA_KHIEUNAI.ND_TRALOI=this.KHIEUNAI.NOIDUNG_GQ
          if (this.KHIEUNAI.HENGQ_TU !== null && this.KHIEUNAI.HENGQ_DEN !== null) {
            this.chkHenTL = true
            this.dtpTuNgay = this.KHIEUNAI.HENGQ_TU ? moment(this.KHIEUNAI.HENGQ_TU, 'DD/MM/YYYY').toDate() : null
            this.dtpDenNgay = this.KHIEUNAI.HENGQ_DEN ? moment(this.KHIEUNAI.HENGQ_DEN, 'DD/MM/YYYY').toDate() : null
          } else {
            this.chkHenTL = false
            this.dtpTuNgay = this.Ngay_Cn
            this.dtpDenNgay = moment(this.Ngay_Cn).clone().add(2, 'days').toDate()
          }
          if (this.KHIEUNAI.NGUYENNHAN_ID === null || this.KHIEUNAI.NGUYENNHAN_ID === '') {
            this.optXuLy = '0'
            if (this.activetab==1)
            {
              this.Button.tsbtnKhoaPhieu = false
              this.Button.tsbtnGiaoPhieu = true
            }

          } else {
            this.optXuLy = '1'
            if (this.activetab==1)
            {
              this.Button.tsbtnKhoaPhieu = true
              this.Button.tsbtnGiaoPhieu = false
            }

          }

          //await this.LAY_DS_DIACHI_THEO_KHIEUNAI_ID(this.KHIEUNAI.KHIEUNAI_ID)
          //await this.getLay_nguyennhan_kn_theo_chude({ CHUDEKN_ID: parseInt(this.KHIEUNAI.CHUDEKN_ID) })


          if (this.DS_NGUYENNHAN_LOI.length > 0) {
            this.Selected.KETQUAKN_ID = this.DS_NGUYENNHAN_LOI[0].NGUYENNHAN_ID
          }

          await this.getLay_phuongan_xl_theo_chude({ CHUDEKN_ID: parseInt(this.KHIEUNAI.CHUDEKN_ID) })
          if (this.DS_PAXL.length > 0) {
            this.Selected.PAXL_ID = this.KHIEUNAI.PAXL_ID
          }

          this.Selected.HAILONG = this.KHIEUNAI.HAILONG
          this.Selected.HTTL_ID = this.KHIEUNAI.HTTL_ID

          await this.HienThiTTDiaChi()
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
          // let LOAITB_ID = null
          // if (this.Selected.LOAITB_ID === '' || this.Selected.LOAITB_ID === 0) {
          //   LOAITB_ID = 1
          // } else {
          //   LOAITB_ID = this.Selected.LOAITB_ID
          // }
          // let data = {
          //   LOAI_ID: this.Selected.LOAI_ID,
          //   LOAITB_ID: LOAITB_ID
          // }
          // await this.getCHUDEKN_THEO_GANQUYEN(data)

          //console.log('loi 2');
          this.Selected.CHUDEKN_ID = this.KHIEUNAI.CHUDEKN_ID
          this.DS_NGUYENNHAN_LOI=await this.getDanhSachNguyenNhanLoi(this.Selected.CHUDEKN_ID,this.Selected.LOAITB_ID)

          this.Selected.NGUYENNHAN_ID=this.KHIEUNAI.NGUYENNHAN_ID
          if (this.Selected.NGUYENNHAN_ID!=null)
          {
            let ketqua=this.DS_NGUYENNHAN_LOI.filter(item=>item.NGUYENNHAN_ID==this.Selected.NGUYENNHAN_ID)[0]
            this.KETQUA_KHIEUNAI.DOITUONG_LOI=ketqua.DOITUONG_LOI_ID
            this.Selected.PAXL_ID=ketqua.GIAM_CUOC==1?"1":"0"
          }


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
    gridDS_TB_rowSelected (args) {
      this.FocusedRowHandle = args.rowIndex
      this.grid_SelectedRowChanged(args.data)
    },
    gridDS_TB_KHIEUNAI_rowSelectedOrDeSelected () {
      this.gridDS_TB_KHIEUNAI_rowSelected = this.$refs.gridDS_TB_KHIEUNAI.getSelectedRecords()
    },
    async layKyCuocHienHanh () {
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
      let field = args.column.field;
      let dataField = args.data;
      if (field == "MA_KN") {
        if (dataField.HETHAN_KN == 1) {

          args.cell.classList.add("background-red");
        }
      }
    },
    gridDS_TB_KHIEUNAI_OnCellSelectedChange: async function (args) {
      if (!(args.selectedRowCellIndex == undefined || args.selectedRowCellIndex == null || args.selectedRowCellIndex.length == 0)) {
        for (let i = 0; i < args.selectedRowCellIndex.length; i++) {
          const rowCell = args.selectedRowCellIndex[i]
          if (rowCell.cellIndexes.length == 1) {
            if (rowCell.cellIndexes.includes(1)) {
              let row = this.$refs.gridDS_TB_KHIEUNAI.grid.getRowInfo(this.$refs.gridDS_TB_KHIEUNAI.grid.getRowByIndex(rowCell.rowIndex))
              if (!this.dsIdChon.includes(row.rowData.KHIEUNAI_ID)) { this.dsIdChon.push(row.rowData.KHIEUNAI_ID) }
            }
          }
        }
      }
      await this.grid_SelectedRowChanged(args.data)
      this.$refs.gridDS_TB_KHIEUNAI.grid.clearSelection()
    },
    btnXemChiTiet_Click:function(){
      this.$refs.dialogChiTietKhieuNai.dialogOpen()
    },
    onclickTab: function (currentActivetab) {
        try {

            this.loading(true)

            this.activetab = currentActivetab;
            this.columnFields=GRID_COLUMS[currentActivetab];
            //this.hienThiDanhSachCatChuyen(currentActivetab, 0);
            if (this.activetab==3)
            {
              this.Button.tsbtnGhiLai=false
              this.Button.tsbtnNhapMoi=false
              this.Button.tsbtnHuyBo=false
              this.Button.tsbtnGiaoPhieu=false
              this.Button.tsbtnKhoaPhieu=false
              this.Button.tsbtnXoa=false
              this.Button.tsbtnHoSo=false
              this.Button.tsbtnDocFile=false

            }
            if (this.activetab==2)
            {
              this.Button.tsbtnGhiLai=false
              this.Button.tsbtnNhapMoi=false
              this.Button.tsbtnHuyBo=false
              this.Button.tsbtnGiaoPhieu=false
            }
            if (this.activetab==1)
            {
              this.Button.tsbtnGhiLai=true
              this.Button.tsbtnNhapMoi=true
              this.Button.tsbtnHuyBo=true
              this.Button.tsbtnGiaoPhieu=true
            }
        } catch (error) {
            this.$root.toastError(' '+error);
        }
        finally {
            this.loading(false)
        }
    },
    getDsLoaiGT: async function () {
      try {
        const rs = await this.$root.context.get(
          `/web-quantri/danhmuc-chung/CSS_LOAI_GT`
        );
        this.dsLoaiGT = rs.data;
      } catch (error) { }
    },
  },

  async mounted () {
    try {
      await this.getNgay_Cn()
      this.dtpNgayBN = moment(this.Ngay_Cn).clone().add(1, 'hours').toDate()
      await this.layKyCuocHienHanh()
      EventBus.$on('gridGC_refresh', this.gridGC_refresh)
      EventBus.$on('clickAddNew', this.clickAddNew)
      this.SetButton(3)
      if ('ma_tb' in this.$route.query) {
        if (typeof (this.$route.query.ma_tb) === 'string' &&
          this.$route.query.ma_tb !== '') {
          this.txtMaTB = this.$route.query.ma_tb
          //await this.txtMaTB_KeyPress();
          let ck = false;
          await this.getDS_TB_KHIEUNAI({ TTKN_ID: 1, HUONGGIAO_ID: 0, MA_TB: this.txtMaTB });
          if (this.DS_TB_KHIEUNAI.length > 0){
            //this.setState({ key: 'DS_TB_KHIEUNAI', value: this.DS_TB_KHIEUNAI.filter(x => x.MA_TB == this.txtMaTB) })
            if (this.activetab==1)
            if (this.DS_TB_KHIEUNAI.length > 0) {
              await this.grid_SelectedRowChanged(this.DS_TB_KHIEUNAI[0]);
              ck = true;
              this.SetButton(3)
            }
          }

          if ('sdt' in this.$route.query) {
            this.KHIEUNAI.DIENTHOAI_LH = this.$route.query.sdt
          }
        }
      }
      if ('makh' in this.$route.query) {
        if (typeof (this.$route.query.makh) === 'string' &&
          this.$route.query.makh !== '') {
          this.txtMaTB = this.$route.query.makh
          let ck = false;
          await this.getDS_TB_KHIEUNAI({ TTKN_ID: 1, HUONGGIAO_ID: 0, MA_TB: this.txtMaTB });
          if (this.DS_TB_KHIEUNAI.length > 0){
            if (this.DS_TB_KHIEUNAI.length > 0) {
              await this.grid_SelectedRowChanged(this.DS_TB_KHIEUNAI[0]);
              ck = true;
              this.SetButton(3)
            }
          }
          if(!ck){
            await this.txtMaTB_KeyPress();
          }
          if ('sdt' in this.$route.query) {
            this.KHIEUNAI.DIENTHOAI_LH = this.$route.query.sdt
          }
        }
      }
      this.$refs.txtMaTB.focus()
    } catch (error) {
    } finally {
      this.$root.showLoading(false)
    }
  }
}
</script>
<style src="./index.scss"></style>
