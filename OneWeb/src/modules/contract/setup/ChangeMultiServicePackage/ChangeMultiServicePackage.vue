<template src="./ChangeMultiServicePackage.html"></template>
<style src="./ChangeMultiServicePackage.scss"></style>

<script>
import breadcrumb from '@/components/breadcrumb'
import ActionTop from '@/components/kylq_components/ActionTop'
import {DataManager, ODataAdaptor, Predicate, Query} from '@syncfusion/ej2-data'
import ChangeMultiServicePackageAPI from './API'
// import SearchContractModal from '../ChangeInstallUnit/popup/SearchContract/SearchContractModal'
import SearchContractModal from "@/modules/contract/setup/HandoverTicket/components/SearchContractModal.vue"
import SearchAccount from '../../../search/subscriber/SearchAccount'
import {DichVuVienThong, LoaiHinhTB, LoaiHopDong, TrangThaiTB} from '../../../../const/enums'
import {KieuLapDat} from '../InstallNewSubs/Enums'
import {TrangThaiHD} from '../../../../utils/Enum'
import KTable from '@/components/kylq_components/KTable.vue'
import moment from 'moment'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
import KDatePicker from '@/components/kylq_components/KDatePicker.vue'
import DSTBCungMa from './popups/DSTBCungMa/DSTBCungMa'
import ThongTinLienHe from '../ChangeContractOwner/popup/ThongTinLienHe'
import ThongTinEmail from '../ChangeContractOwner/popup/ThongTinEmail'
import {TRANGTHAI_DONGBO} from '../../../../constants'

export default {
  name: 'ChangeMultiServicePackage',
  components: {
    breadcrumb,
    ActionTop,
    DataManager,
    Query,
    ODataAdaptor,
    Predicate,
    SearchContractModal,
    SearchAccount,
    KTable,
    KDatePicker,
    ThongTinEmail,
    ThongTinLienHe,
    DSTBCungMa
  },
  data () {
    return {
      header: {
        title: 'THAY ĐỔI GÓI ĐA DỊCH VỤ',
        list: [
          {
            name: 'Lập hợp đồng',
            link: {name: 'Ui.cards'},
            active: false
          },
          {
            name: 'Lắp đặt mới',
            link: {name: 'Ui.buttons'},
            active: true
          }
        ]
      },
      actions: [
        {
          id: 'tsbtnNhapMoi',
          name: 'Nhập mới',
          active: true,
          icon_class: 'one-file-plus'
        },
        {
          id: 'tsbtnGhiLai',
          name: 'Ghi lại',
          active: true,
          icon_class: 'one-save'
        },
        {
          id: 'tsbtnHuyBo',
          name: 'Hủy',
          active: true,
          icon_class: 'nc-icon-glyph ui-1_circle-remove'
        },
        {
          id: 'tsbtnXoa',
          name: 'Xóa',
          active: true,
          icon_class: 'one-trash'
        },
        {
          id: 'tsbtnThemTB',
          name: 'Thêm TB',
          active: true,
          icon_class: 'one-mobile-plus'
        },
        {
          id: 'tsbtnXoaTB',
          name: 'Xóa TB',
          active: true,
          icon_class: 'one-phone-remove'
        },
        {
          id: 'tsbtnCapNhatDB',
          name: 'Hoàn Thiện',
          active: true,
          icon_class: 'one-file-check'
        },
        {
          id: 'tsbtnThongTinLH',
          name: 'Liên hệ',
          active: true,
          icon_class: 'nc-icon-glyph business_contacts'
        },
        {
          id: 'tsbtnEmail',
          name: 'Email',
          active: true,
          icon_class: 'one-email'
        }
      ],
      settingSelect2: {
        language: 'vi'
      },
      cboDichVuVT: {
        list: [],
        disable: false,
        value: 0
      },
      cboKieuLD: {
        list: [],
        disable: false,
        value: 0
      },
      cboHieuLuc: {
        list: [],
        disable: false,
        value: 0
      },
      cboLoaihinhTB: {
        list: [],
        disable: false,
        value: 0
      },
      cboNghiepvu: {
        list: [],
        disable: false,
        value: 0
      },
      cboTenGoi: {
        list: [],
        disable: false,
        value: 0
      },
      cboTenGoi2: {
        data: [],
        disabled: false,
        textField: 'ten_goi',
        valueField: 'goi_id',
        panelDataHeight: 'auto',
        headers: [
          {
            fieldName: 'ma_goi',
            headerText: 'Mã gói',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ten_goi',
            headerText: 'Tên gói',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ten_cv',
            headerText: 'Tên Công văn',
            allowFiltering: true,
            allowSorting: false
          }
        ],
        selectTenGoi2: null
      },
      USER: {'USER_NEO': 'vtngoc_hpg'},
      disabledBox: {
        txtMaTBHT: false,
        txtMaGD: false
      },
      visibleBox: {
        txtSoThang: false,
        txtGhiChu: true
      },
      formProps: {
        txtSoThang: '',
        txtMaGD: '',
        txtMaTB: '',
        txtTenKH: '',
        txtDiaChiKH: '',
        txtGhiChu: '',
        dtpNgayYeuCau: moment(new Date()).format('DD/MM/YYYY'),
        txtMaKH: '',
        txtMaTBHT: '',
        txtMoTa: '',
        lineLabel1: 'Thông tin gói cước hiện tại'
      },
      dsHDTB: [],
      dsHDKH: [],
      dsHDTBGOI: [],
      cv_hientai: [],
      goimoi: [],
      dichvuvt_id: 0,
      hdkh_id: 0,
      hdkh_cha_id: 0,
      nhomtb_id: 0,
      hdtb_id: 0,
      donvi_id: 0,
      khachhang_id: 0,
      phieucskh_id: 0,
      goi_id: 0,
      thuebao_id: 0,
      nhom_tach: 0,
      ma_tb: '',
      ten_tb: '',
      diachi_tb: '',
      doituong_id: 0,
      huonggiaotn_id: 0,
      quytrinh_id: 0,
      ts_sinhma_gd_theo_donvi: false,
      loi_sinh_magd: false,
      truyen_nhom_neo: 0,
      kieuld_id: '',
      dtpNgayLHD: new Date(),
      nhomtb_id_new: 0,
      didong_chunhom: '',
      capnhat_nhomtb_id_neo: '',
      VPHIEUCSKH_ID: 0,
      VPHIEUCSKHDV_ID: 0,
      ten_kh_goi: '',
      diachi_kh_goi: '',
      boqua_kichhoat_tb_dahuy: '',
      popupComponent: null,
      DONGBO_TEST: {TEST: false},
      nhanvienId: Number(this.$root.token.getNhanVienID()),
      donviId: Number(this.$root.token.getDonViID()),
      nguoicn: this.$root.token.getUserName(),
      phanvungId: Number(this.$root.token.getPhanVungID()),
      donviDLId: Number(this.$root.token.getDonViDuLieuID()),
      maycn: '',
      ipcn: '',
      dgvGoi_ht: {
        data: [],
        headers: [
          {
            fieldName: 'ten_goi',
            headerText: 'Tên gói',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ma_tb',
            headerText: 'Số máy/Acc',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'loaihinh_tb',
            headerText: 'Loại hình',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ngay_dk',
            headerText: 'Ngày ĐK',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'thang_bd',
            headerText: 'Tháng BĐ',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'chunhom',
            headerText: 'Chủ nhóm',
            allowFiltering: true,
            allowSorting: false
          }
        ],
        selected: 0,
        selectItem: null,
        checked: [],
        visible: true
      },
      dgvGoi: {
        data: [],
        headers: [
          {
            fieldName: 'ten_goi',
            headerText: 'Tên gói',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ma_tb',
            headerText: 'Số máy/Acc',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'loaihinh_tb',
            headerText: 'Loại hình',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ngay_dk',
            headerText: 'Ngày ĐK',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'thang_bd',
            headerText: 'Tháng BĐ',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'chunhom',
            headerText: 'Chủ nhóm',
            allowFiltering: true,
            allowSorting: false
          }
        ],
        selected: 0,
        selectItem: null,
        checked: [],
        visible: false,
        disabled: false
      },
      dgrList: {
        data: [],
        headers: [
          {
            fieldName: 'ma_tb',
            headerText: 'Số máy/Acc',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'ten_tb',
            headerText: 'Tên thuê bao',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'diachi_tb',
            headerText: 'Địa chỉ thuê bao',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'ten_goi',
            headerText: 'Gói hiện tại',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'ten_goi_moi',
            headerText: 'Gói mới',
            allowFiltering: true,
            allowSorting: false,
            visible: false
          },
          {
            fieldName: 'loaihinh_tb',
            headerText: 'Loại hình',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'goi_vnp_id',
            headerText: 'goi_vnp_id',
            allowFiltering: true,
            allowSorting: false,
            visible: false
          }
        ],
        selected: 0,
        selectItem: null,
        checked: []
      },
      dateFormat: 'DD/MM/YYYY',
      datetimeFormat: 'DD/MM/YYYY HH:mm',
      modelSearchContract: {
        loai_hd_id: 0,
        dichvu_id: 0,
        ngay_yc: '',
        ngay_kt: '',
        donvi_id: 0,
        donvi_dl_id: 0,
        trangthai_hd: TrangThaiHD.MOI
      },
      txtMaTBTemp: ''
    }
  },
  mounted () {
    this.frmThayDoiGoiDaDV_Load()
    this.LoadFromExternalForm()
  },
  computed: {},
  watch: {
    // Sự kiện người dùng chọn dịch vụ vt
    'cboDichVuVT.value': async function (newValue) {
      if (newValue) {
        this.dichvuvt_id = newValue
        await this.CSS_LOAIHINH_TB(newValue)
      }      
    },
    // Sự kiện người dùng chọn loại hình thuê bao
    'cboLoaihinhTB.value': function (newValue) {
      if (newValue) this.cboLoaihinhTB_SelectedValueChanged(newValue)
    },
    'cboKieuLD.value': function (newValue) {
      if (newValue) this.cboKieuLD_SelectedIndexChanged(newValue)
    },
    'cboNghiepvu.value': function (newValue) {
      this.cboNghiepvu_SelectedIndexChanged(newValue)
    },
    'cboHieuLuc.value': function (newValue) {
      this.cboHieuLuc_SelectedIndexChanged(newValue)
    },
    'cboTenGoi.value': function (newValue) {
      this.cboTenGoi_SelectedValueChanged(newValue)
    },
    'cboTenGoi2.selectTenGoi2': function (newValue) {
      this.cboTenGoi2_EditValueChanged(newValue)
    }
  },
  methods: {
    LoadFromExternalForm () {
      let phieucskhid = 0
      let phieucskhdvid = 0
      if (
        this.$route.query.phieucskh_id != null &&
        this.$route.query.phieucskh_id !== '' &&
        this.$route.query.phieucskh_id.length > 0
      ) {
        phieucskhid = Number(this.$route.query.phieucskh_id)
      }
      if (
        this.$route.query.phieucskhdv_id != null &&
        this.$route.query.phieucskhdv_id !== '' &&
        this.$route.query.phieucskhdv_id.length > 0
      ) {
        phieucskhdvid = Number(this.$route.query.phieucskhdv_id)
      }
      if (phieucskhdvid > 0) {
        this.LoadTuHopDongB2A(phieucskhid, phieucskhdvid)
      }
    },
    showPopupSearchContract () {
      this.modelSearchContract.trangthai_hd = Number(TrangThaiHD.MOI)
      this.modelSearchContract.loai_hd_id = Number(LoaiHopDong.THAYDOI_GOI_DADV)
      this.modelSearchContract.dichvu_id = 0
      this.modelSearchContract.ngay_yc = moment(new Date()).format('DD/MM/YYYY')
      this.modelSearchContract.donvi_dl_id = this.donviDLId
      this.modelSearchContract.donvi_id = this.donviId
      this.modelSearchContract.nhanvien_id = this.nhanvienId
      console.log(this.modelSearchContract)
      this.$refs.popupSearchContract.showModal()
    },
    acceptSearchContract (item) {
      this.formProps.txtMaGD = item.ma_gd
      if (this.formProps.txtMaGD && this.formProps.txtMaGD.trim() !== '') { this.txtMaGD_KeyPress() }
    },
    showPopupSearchContact () {
      this.popupComponent = SearchAccount
      this.Popup('popupComponents')
    },
    Popup (modalId) {
      this.$bvModal.show(modalId)
    },
    ClosePopup (modalId) {
      this.$bvModal.hide(modalId)
    },
    popupClosed (val) {
      if (this.popupComponent === SearchAccount) {
        if (val.ma_tb) this.formProps.txtMaTB = val.ma_tb
        this.$refs.txtMaTB.focus()
        this.txtMaTB_KeyPress()
      }
      this.ClosePopup('popupComponents')
    },
    dgvGoi_ht_SelectionChanged (item) {
      if (item != null) {
        this.dgvGoi_ht.selectItem = item
      }
    },
    dgvGoi_SelectionChanged (item) {
      if (item != null) {
        this.dgvGoi.selectItem = item
        this.goi_id = item.goi_id
        this.nhomtb_id = item.nhomtb_id
        this.cboTenGoi.value = item.goi_id
        this.formProps.txtMaTBHT = item.ma_tb
      }
    },
    async onGrGoiHTSelectedRow (item) {

    },
    dgrListActionComplete (args) {
      console.log('dgrListActionComplete', args)
      if (this.dgrList.data.length > 0) {
        if (this.txtMaTBTemp) {
          let index = args.rows.findIndex(item => item.data.ma_tb == this.txtMaTBTemp)
          if (index >= 0) {
            this.$refs.dgrList.selectRow(index, true)
          } else {
            this.$refs.dgrList.selectRow(0, true)
          }
        } else {
          this.$refs.dgrList.selectRow(0, true)
        }
      }
    },
    async dgrList_SelectionChanged (item) {
      try {
        if (item != null) {
          this.dgrList.selectItem = item
          this.hdtb_id = item.hdtb_id
          this.formProps.txtMaTB = item.ma_tb
          this.ten_tb = item.ten_tb
          this.diachi_tb = this.ChuanHoaTen(item.diachi_tb)
          this.loaitb_id = item.loaitb_id
          this.donvi_id = item.donvi_id
          this.doituong_id = item.doituong_id
          let ds = await this.SP_DS_HDTB_GOI_DADV_THEO_HDTB(this.hdtb_id)
          if (ds.length > 0) {
            this.nhomtb_id = ds[0].nhomtb_id
          }
          this.cboKieuLD.value = Number(item.kieuld_id)
          const hdDataColumns = Object.keys(this.dgrList.data[0])
          if (this.cboKieuLD.value == KieuLapDat.TDTB_GOIDADV && hdDataColumns.includes('ten_goi_moi')) {
            let ds = await this.SP_HDTB_GOI_DADV_THEO_KIEULD(item.hdtb_id, this.cboKieuLD.value)
            if (ds.length > 0) {
              this.cboTenGoi2.value = ds[0].goi_id
            }
          }
          let thang_huong = Number(item.thang_huong)
          if (thang_huong === 0 || thang_huong === 1) {
            this.cboHieuLuc.value = thang_huong
          } else {
            this.cboHieuLuc.value = 2
            this.formProps.txtSoThang = thang_huong
          }
          this.setActiveActions(3)
        }
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Có lỗi: ' + error.message)
      }
    },
    async txtMaTBHT_KeyPress () {
      try {
        this.loading(true)
        if (this.formProps.txtMaTBHT) {
          let ds = await this.LAY_DS_TB_GOI_DADV(this.formProps.txtMaTBHT.trim())
          if (ds.length > 0 && ds[0].ma_tb !== '') {
            if (this.cboKieuLD.value == KieuLapDat.TDTB_NHOMDADV && ds[0].nhomtb_id_neo) {
              let dsGoiNeo = await this.lay_ds_goi_theo_nhomneo(ds[0].nhomtb_id_neo)
              if (dsGoiNeo.length > 0) {
                this.dgvGoi.data = dsGoiNeo
              } else {
                this.dgvGoi.data = ds
              }
            } else {
              this.dgvGoi.data = ds
            }
            let ma_goi = await this.FN_TT_GOI_DADV(ds[0].goi_id)
            if (ma_goi == 'V39') {
              let dtThueBaoChuGoi = await this.sp_tt_db_thuebao(ds[0].ma_tb, 2)
              if (dtThueBaoChuGoi.length > 0) {
                this.ten_kh_goi = dtThueBaoChuGoi[0].ten_tb
                this.diachi_kh_goi = dtThueBaoChuGoi[0].diachi_tb
              }
            }
          } else {
            this.dgvGoi.data = []
            this.goi_id = 0
          }
        }
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Có lỗi:', error.message)
      } finally {
        this.loading(false)
      }
    },

    async FN_TT_GOI_DADV (goiid) {
      return this.GetData(await ChangeMultiServicePackageAPI.FN_TT_GOI_DADV(this.axios, { param: goiid, type: 1 }))
    },
    async sp_tt_db_thuebao (param, type) {
      return this.GetDataList(await ChangeMultiServicePackageAPI.sp_tt_db_thuebao(this.axios, { param: param, type: type }))
    },
    async FN_TT_GOI_DADV_LHTB (goiid, loaitbid) {
      return this.GetData(await ChangeMultiServicePackageAPI.FN_TT_GOI_DADV_LHTB(this.axios, { param: goiid, type: 1, param1: loaitbid }))
    },
    async txtMaGD_KeyPress () {
      try {
        this.loading(true)
        if (this.formProps.txtMaGD) {
          let dsHD = await this.LAY_DS_HOPDONG_THEO_MA_GD(this.formProps.txtMaGD.trim(), LoaiHopDong.THAYDOI_GOI_DADV, TrangThaiHD.MOI)
          this.thuebao_id=dsHD[0].thuebao_id
          await this.HienThiTTHopDongKH(dsHD)
          // Thêm TH load lại HĐ của tách gói ko có lưới dgvGoi
          if (this.dgvGoi.data.length === 0) {
            if (this.cboKieuLD.value != KieuLapDat.TDTB_NHOMDADV || this.cboKieuLD.value == KieuLapDat.TDTB_NHOMDADV && this.cboNghiepvu.value == 1) {
              let dsGoi = []
              if (this.nhomtb_id !== 0) {
                dsGoi = await this.LAY_DS_TB_GOI_DADV_1(this.nhomtb_id)
                if (dsGoi.length > 0 && dsGoi[0].ma_tb !== '') {
                  this.dgvGoi.data = dsGoi
                  if (dsHD.length > 0) {
                    this.formProps.txtMaTBHT = dsGoi[0].ma_tb
                  }
                } else {
                  this.dgvGoi.data = []
                  this.goi_id = 0
                  this.formProps.txtMaTBHT = ''
                }
              }
            }
          }
        }
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Có lỗi: ', error.message)
      } finally {
        this.loading(false)
      }
    },
    async txtMaTB_KeyPress () {
      try {
        this.loading(true)
        if (this.formProps.txtMaTB) {
          this.txtMaTBTemp = this.formProps.txtMaTB
          let dsHd = await this.LAY_DS_HOPDONG_THEO_MA_TB(this.formProps.txtMaTB, LoaiHopDong.THAYDOI_GOI_DADV,
            TrangThaiHD.MOI, Number(this.cboDichVuVT.value), Number(this.cboLoaihinhTB.value))
          if (dsHd.length > 0) {
            if (this.dgrList.data.length > 0) {
              this.dgrList.data = []
            }
            this.dgvGoi.data = []
            this.thuebao_id = dsHd[0].thuebao_id
            await this.HienThiTTHopDongKH(dsHd)
          } else {
            await this.HienThiTTDanhBa(this.formProps.txtMaTB.trim())
            if (this.formProps.txtMaGD == '') {
              // Hiển thị thông tin gói hiện tại của thuê bao nếu có
              let dsGoi = await this.LAY_DS_TB_GOI_DADV(this.formProps.txtMaTB.trim())
              if (dsGoi.length > 0 && dsGoi[0].ma_tb !== '') {
                this.dgvGoi_ht.data = dsGoi
                this.dgvGoi.data = dsGoi
                if (this.cboKieuLD.value != KieuLapDat.TDTB_NHOMDADV) {
                  this.formProps.txtMaTBHT = dsGoi[0].ma_tb
                } else if (this.cboNghiepvu.value != 2) {
                  this.formProps.txtMaTBHT = this.formProps.txtMaTB
                } else {
                  this.formProps.txtMaTBHT = ''
                  this.dgvGoi.data = []
                }
              } else {
                this.dgvGoi.data = []
                this.dgvGoi_ht.data = []
                this.goi_id = 0
                this.formProps.txtMaTBHT = ''
              }
            }
          }
        }
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Có lỗi ' + error.message)
      } finally {
        this.loading(false)
      }
    },
    dgvGoiActionComplete (args) {
      if (this.formProps.txtMaTB) {
        let index = this.dgvGoi.data.findIndex(item => item.ma_tb.toString() === this.formProps.txtMaTB.toString())
        if (index >= 0) {
          this.$refs.dgvGoi.selectRow(index, true)
        }
      } else {
        this.$refs.dgvGoi.selectRow(0, true)
      }
    },
    btnTenGoi_Click () {
      // TODO lam tiep
      this.$root.$toast.info('Chức năng đang được xây dựng')
    },
    // Hiển thị thông tin danh bạ
    async HienThiTTDanhBa (maTb) {
      // Lấy ra bất kỳ thuê bao nào
      let ds = await this.LAY_DANHBA_THEO_MATB(maTb, Number(this.cboDichVuVT.value), 0)
      if (ds.length > 0) {
        // HTH them bo bot hien thi thue bao di dong khong hoat dong
        if (this.cboDichVuVT.value == DichVuVienThong.DI_DONG) {
          ds = ds.filter(item => (item.trangthaitb_id != TrangThaiTB.THANHLY_NO && item.trangthaitb_id != TrangThaiTB.THANHLY))
        }

        if (ds.length < 1) {
          this.$root.$toast.error('Trạng thái thuê bao ' + this.formProps.txtMaTB + ' không hợp lệ!')
          return
        }

        if (ds.length > 1) {
          this.$refs.popupDSTBCungMa.showModal(ds)
          if (this.$refs.popupDSTBCungMa.getDialogResult()) {
            ds = this.$refs.popupDSTBCungMa.getDanhSach()
          }
        }
        this.thuebao_id = ds[0].thuebao_id

        // Thông tin khách hàng
        this.formProps.txtMaKH = ds[0].ma_kh
        if (ds[0].ngaylap_hd) {
          this.dtpNgayLHD = ds[0].ngaylap_hd
        } else {
          this.dtpNgayLHD = moment(new Date()).format('DD/MM/YYYY HH:mm:ss')
        }

        this.khachhang_id = ds[0].khachhang_id
        this.formProps.txtTenKH = this.ChuanHoaTen(ds[0].ten_kh)
        this.formProps.txtDiaChiKH = this.ChuanHoaTen(ds[0].diachi_kh)
        // Thông tin thuê bao
        this.formProps.txtMaTB = ds[0].ma_tb
        this.ten_tb = this.ChuanHoaTen(ds[0].ten_tb)
        this.diachi_tb = this.ChuanHoaTen(ds[0].diachi_tb)
        this.loaitb_id = ds[0].loaitb_id
        this.donvi_id = ds[0].donviql_id
        this.doituong_id = ds[0].doituong_id
        this.hdtb_id = 0
      } else {
        let dichvuvtidx = this.cboDichVuVT.list.findIndex(value => value.id == this.cboDichVuVT.value)
        this.$root.$toast.error('Không tìm thấy thông tin về mã thuê bao ' + this.formProps.txtMaTB.trim() + ' đối với dịch vụ ' + this.cboDichVuVT.list[dichvuvtidx].text)
      }
    },
    // Hiển thị danh sách hợp đồng khách hàng
    async HienThiTTHopDongKH (dsHD) {
      try {
        if (dsHD.length > 0) {
          const HD = dsHD[0]
          // Hiển thị thông tin hợp đồng khách hàng
          this.hdkh_id = Number(HD.hdkh_id)
          this.formProps.txtMaGD = HD.ma_gd
          this.formProps.txtMaKH = HD.ma_kh
          if (HD.khachhang_id != '') {
            this.khachhang_id = Number(HD.khachhang_id)
          }
          this.formProps.txtTenKH = this.ChuanHoaTen(HD.ten_kh)
          this.formProps.txtDiaChiKH = this.ChuanHoaTen(HD.diachi_kh)
          this.formProps.dtpNgayYeuCau = moment(new Date(HD.ngay_yc)).format('DD/MM/YYYY')
          this.dichvuvt_id = HD.dichvuvt_id
          this.formProps.txtGhiChu = this.ChuanHoaTen(HD.ghichu)
          this.cboDichVuVT.value = HD.dichvuvt_id
          if (HD.phieucskh_id != null) {
            this.VPHIEUCSKH_ID = HD.phieucskh_id
          }
          this.setActiveActions(3)
          await this.HienThiDanhSachHDTB()
        } else {
          this.dgrList.data = []
          this.dgvGoi.data = []
          this.setActiveActions(0)
        }
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Có lỗi: ', error.message)
      }
    },
    // Hiển thị danh sách hợp đông thuê bao
    async HienThiDanhSachHDTB () {
      try {
        let data = await this.LAY_DS_HOPDONG_TB_THEO_HDKH_ID(this.hdkh_id)
        data = data.sort(function (a, b) {
          let x = a.hdtb_id
          let y = b.hdtt_id
          return ((x < y) ? -1 : ((x > y) ? 1 : 0))
        })
        if (data.length > 0) {
          if (data.length <= 1) {
            this.actions[this.actions.findIndex(x => x.id === 'tsbtnXoaTB')].active = false
          } else {
            this.actions[this.actions.findIndex(x => x.id === 'tsbtnXoaTB')].active = true
            this.actions[this.actions.findIndex(x => x.id === 'tsbtnThemTB')].active = true
          }
          this.cboKieuLD.value = Number(data[0].kieuld_id)

          let ds = await this.SP_DS_HDTB_GOI_DADV_THEO_HDTB(data[0].hdtb_id)
          let dsGoi = []
          if (ds.length > 0) {
            if (this.cboKieuLD.value != KieuLapDat.THEM_TB_GOI_DADV) {
              dsGoi = await this.LAY_DS_TB_GOI_DADV_1(ds[0].nhomtb_id)
              /* dsGoi = await this.LAY_DS_TB_GOI_DADV_1(this.nhomtb_id)*/
            } else {
              dsGoi = await this.LAY_DS_TB_GOI_DADV(this.formProps.txtMaTBHT.trim())
            }
          }
          const hdDataColumns = Object.keys(data[0])
          if (!hdDataColumns.includes('ten_goi') && !hdDataColumns.includes('ten_goi_moi') && !hdDataColumns.includes('goi_vnp_id')) {
            data.forEach(item => {
              item.ten_goi = ''
              item.goi_vnp_id = 0
              item.ten_goi_moi = ''
            })
          }
          for (let i = 0; i < data.length; i++) {
            const hdtbId = data[i].hdtb_id
            let goicu = await this.SP_HDTB_GOI_DADV_THEO_KIEULD(hdtbId, '')
            if (goicu.length > 0) {
              const goiCuObj = goicu[0]
              if (goiCuObj.ten_goi) {
                data[i].ten_goi = goiCuObj.ten_goi
              }
              if (goiCuObj.goi_vnp_id) {
                data[i].goi_vnp_id = goiCuObj.goi_vnp_id
              }
            }

            if (this.cboKieuLD.value == KieuLapDat.TDTB_GOIDADV) {
              let goimoi = await this.SP_HDTB_GOI_DADV_THEO_KIEULD(hdtbId, this.cboKieuLD.value)
              this.goimoi = goimoi
              if (goimoi.length > 0) {
                const goiMoiObj = goimoi[0]
                if (goiMoiObj.ten_goi) {
                  data[i].ten_goi_moi = goiMoiObj.ten_goi
                }
                if (goiMoiObj.goi_vnp_id) {
                  data[i].goi_vnp_id = goiMoiObj.goi_vnp_id
                }
                console.log('goi moi id', goiMoiObj.goi_id)
                if (goiMoiObj.goi_id) {
                  const request = {
                    'id': goiMoiObj.goi_id
                  }
                  this.cboTenGoi2.selectTenGoi2 = goiMoiObj.goi_id
                  this.cv_hientai = await this.GetDataList(await ChangeMultiServicePackageAPI.SP_LAY_SOCONGVAN_GOIDADV(this.axios, request))
                }
              }
            }
            if (this.cboKieuLD.value == KieuLapDat.TDTB_NHOMDADV) {
              const request = {
                'vhdkh_id': data[i].hdkh_id
              }
              let tachghep = this.GetData(await ChangeMultiServicePackageAPI.KT_TACH_GHEP(this.axios, request))
              this.cboNghiepvu.value = tachghep ? Number(tachghep) : 0
              let goiht = await this.LAY_DS_TB_GOI_DADV(data[0].ma_tb)
              if (goiht.length > 0) {
                this.dgvGoi_ht.data = goiht
              } else {
                this.dgvGoi_ht.data = []
              }
            }
          }

          if (dsGoi.length > 0 && dsGoi[0].ma_tb) {
            if (this.cboKieuLD.value != KieuLapDat.TDTB_NHOMDADV || this.cboNghiepvu.value != 2) {
              if (this.cboKieuLD.value == KieuLapDat.HUY_GOI_DADV || this.cboKieuLD.value == KieuLapDat.TDTB_GOIDADV) {
                this.formProps.txtMaTBHT = data[0].ma_tb
              } else if (this.cboKieuLD.value != KieuLapDat.THEM_TB_GOI_DADV) {
                this.formProps.txtMaTBHT = dsGoi[0].ma_tb
              }
              if (this.cboKieuLD.value != KieuLapDat.THEM_TB_GOI_DADV) {
                this.dgvGoi.data = dsGoi
                let index = dsGoi.findIndex(item => item.ma_tb == this.formProps.txtMaTBHT && item.goi_id == this.goi_id)
                if (index >= 0) {
                  this.$refs.dgvGoi.selectRow(index, true)
                }
              }
            }
            if (this.cboKieuLD.value == KieuLapDat.TDTB_NHOMDADV || this.cboKieuLD.value == KieuLapDat.THEM_TB_GOI_DADV) {
              let ds = await this.SP_LAY_TT_GOI_THEO_NHOMNEO(this.formProps.txtMaTBHT, this.goi_id)
              if (ds.length > 0 && ds[0].ma_tb) {
                // HTH trỏ đúng row trên lưới load hiển thị
                let index = ds.findIndex(item => item.ma_tb == this.formProps.txtMaTBHT)
                this.dgvGoi.data = ds
                this.$refs.dgvGoi.selectRow(index, true)
              }
            }
          } else {
            this.dgvGoi.data = []
            this.goi_id = 0
            this.nhomtb_id = 0
            this.formProps.txtMaTBHT = ''
          }
          this.dgrList.data = data
        } else {
          this.dgrList.data = []
          this.dgvGoi.data = []
          this.setActiveActions(1)
        }
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Có lỗi khi hiển thị danh sách hợp đồng thuê bao ', error.message)
      }
    },
    async LAY_DANHBA_THEO_MATB (maTb, dichvuVTId, donviId) {
      const request = {
        'in_ma_tb': maTb,
        'in_dichvuvt_id': dichvuVTId,
        'in_donvi_dl_id': donviId
      }
      return this.GetDataList(await ChangeMultiServicePackageAPI.LAY_DANHBA_THEO_MATB(this.axios, request))
    },
    // Lấy thông tin nhom neo theo mã tb
    async SP_LAY_TT_GOI_THEO_NHOMNEO (maTb, goiId) {
      let request = {
        ma_tb: maTb,
        id: goiId
      }
      return this.GetDataList(await ChangeMultiServicePackageAPI.SP_LAY_TT_GOI_THEO_NHOMNEO(this.axios, request))
    },
    async lay_ds_goi_theo_nhomneo (nhomidneo) {
      return this.GetDataList(await ChangeMultiServicePackageAPI.lay_ds_goi_theo_nhomneo(this.axios, nhomidneo))
    },
    // Lấy danh sách hợp đồng thuê bao theo hợp đồng khách hàng
    async LAY_DS_HOPDONG_TB_THEO_HDKH_ID (hdkhId) {
      return this.GetDataList(await ChangeMultiServicePackageAPI.LAY_DS_HOPDONG_TB_THEO_HDKH_ID(this.axios, hdkhId))
    },
    async SP_DS_HDTB_GOI_DADV_THEO_HDTB (hdtbId) {
      const params = {
        hdtb_id: hdtbId
      }
      return this.GetDataList(await ChangeMultiServicePackageAPI.SP_DS_HDTB_GOI_DADV_THEO_HDTB(this.axios, params))
    },
    async SP_HDTB_GOI_DADV_THEO_KIEULD (hdtb_id, kieuld_id) {
      const req = {
        kieuld_id: kieuld_id,
        hdtb_id: hdtb_id
      }
      return this.GetDataList(await ChangeMultiServicePackageAPI.SP_HDTB_GOI_DADV_THEO_KIEULD(this.axios, req))
    },
    async LAY_DS_TB_GOI_DADV_1 (nhomtbId) {
      const params = {
        'vnhomtb_id': nhomtbId
      }
      return this.GetDataList(await ChangeMultiServicePackageAPI.LAY_DS_TB_GOI_DADV_1(this.axios, params))
    },
    async LAY_DS_HOPDONG_THEO_MA_GD (maGd, loaihdId, tthdId) {
      let data = []
      const request = {
        'in_loaihd_id': loaihdId,
        'in_ma_gd': maGd,
        'in_tthd_id': tthdId,
        'in_donvi_dl_id': 0
      }
      await ChangeMultiServicePackageAPI.LAY_DS_HOPDONG_THEO_MA_GD(this.axios, request).then(response => {
        console.log(response)
        if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
          data = response.data.data
        } else {
          data = []
          this.$root.$toast.error(response.data.message)
        }
      }).catch(error => {
        data = []
        this.$root.$toast.error('Có lỗi: ', error.data.message)
      })
      return data
    },
    async LAY_DS_HOPDONG_THEO_MA_TB (matb, loaihdid, tthdid, dichvuvtid, loaitbid) {
      const request = {
        'in_ma_tb': matb,
        'in_loaihd_id': loaihdid,
        'in_donvi_id': Number(this.$root.token.getDonViID()),
        'in_tthd_id': tthdid,
        'in_nhanvien_id': this.nhanvienId,
        'in_donvi_dl_id': 0,
        'in_dichvuvt_id': dichvuvtid,
        'in_loaitb_id': loaitbid
      }
      return this.GetDataList(await ChangeMultiServicePackageAPI.LAY_DS_HOPDONG_THEO_MA_TB(this.axios, request))
    },
    async LAY_DS_TB_GOI_DADV (maTb) {
      const params = {
        'vma_tb': maTb
      }
      return this.GetDataList(await ChangeMultiServicePackageAPI.LAY_DS_TB_GOI_DADV(this.axios, params))
    },
    ChuanHoaTen (ten) {
      if (!ten) return ''
      let ten_new = ''
      let str_con = ten.trim()
      let str_xl = ''
      let k = 0
      let found = true
      if (str_con.length < 1) return
      str_con = str_con.replaceAll(/\s+/g, ' ')
      try {
        while (found) {
          k = str_con.indexOf(' ')
          if (k > 0) {
            str_xl = str_con.substring(0, k)
            str_con = str_con.substring(k + 1)
            str_xl = this.ChuyenChuHoa(str_xl)
            if (ten_new !== '') { ten_new += ' ' + str_xl } else ten_new = str_xl
            found = true
          } else found = false
        }
        if (str_con !== '') {
          str_xl = str_con
          str_xl = this.ChuyenChuHoa(str_xl)
          if (ten_new !== '') { ten_new = ten_new + ' ' + str_xl } else { ten_new = str_xl }
        }
        return ten_new
      } catch (e) {
        return ten
      }
    },
    ChuyenChuHoa (str) {
      let kq = ''
      if (str.ismatch(/[A-Z][A-Z]/g)) kq = str.toUpperCase()
      else {
        if (str[0] === '(') {
          kq = str[0] + str[1].toUpperCase() + str.substring(2).toLowerCase()
        } else {
          kq = str[0].toUpperCase() + str.substring(1).toLowerCase()
        }
      }
      return kq
    },
    async frmThayDoiGoiDaDV_Load () {
      try {
        this.loading(true)
        this.maycn = await this.$root.token.getMachine()
        this.ipcn = await this.$root.token.getIP()
        await this.fetchDichVuVT()
        await this.GET_GOI_DA_DV_ALL()
        await this.SP_DS_HIEULUC()
        this.cboTenGoi.disable = true
        this.cboTenGoi2.disabled = true
        this.dgvGoi_ht.visible = false
        await this.SP_DS_NGHIEPVU()
        this.cboNghiepvu.disable = true
        await this.LAY_DS_GOI_CV_VNP_V1()
        this.setActiveActions(-1)
        let dsTs = await this.SP_DS_THAMSO_MD(0, null)
        this.ts_sinhma_gd_theo_donvi = dsTs.filter(item => item.ma_ts == 'SINH_MA_GD_THEO_DV').length > 0
        if (dsTs.length > 0) {
          const strCompare = 'BOQUA_KICHHOAT_TB_DAHUY'
          const det = dsTs.filter(item => item.ma_ts == strCompare)
          if (det.length > 0) {
            const firstItem = det[0]
            if (firstItem.ten_ts == '1') {
              this.boqua_kichhoat_tb_dahuy = true
            }
          }
        }
      } catch (error) {
        this.$root.$toast.error('Có lỗi khi load form ', error.message)
      } finally {
        this.loading(false)
      }
    },
    async CSS_LOAIHINH_TB (dichvuVTId) {
      let data = this.GetDataList(await ChangeMultiServicePackageAPI.CSS_LOAIHINH_TB(this.axios))
      if (data.length > 0) {
        this.cboLoaihinhTB.list = data.filter(item => item.DICHVUVT_ID == dichvuVTId).map(item => {
          return {
            id: item.LOAITB_ID,
            text: item.LOAIHINH_TB
          }
        })
        if (this.cboLoaihinhTB.list.length > 0) {
          if (dichvuVTId == DichVuVienThong.CO_DINH) {
            this.cboLoaihinhTB.value = LoaiHinhTB.DIENTHOAI_CD
          } else if (dichvuVTId == DichVuVienThong.ADSL) {
            this.cboLoaihinhTB.value = LoaiHinhTB.MEGA
          } else if (dichvuVTId == DichVuVienThong.TSL) {
            this.cboLoaihinhTB.value = LoaiHinhTB.LEASEDLINE
          } else if (dichvuVTId == DichVuVienThong.MEGAWAN) {
            this.cboLoaihinhTB.value = LoaiHinhTB.MEGAWAN_ADSL
          } else {
            this.cboLoaihinhTB.value = this.cboLoaihinhTB.list[0].id
          }          
        }
      } else {
        this.cboLoaihinhTB.list = []
        this.cboLoaihinhTB.value = 0
      }
    },
    async SP_DS_THAMSO_MD (kieuId, maTs) {
      const req = {
        'kieu_id': kieuId,
        'ma_ts': maTs
      }
      return this.GetDataList(await ChangeMultiServicePackageAPI.SP_DS_THAMSO_MD(this.axios, req))
    },
    GetDataList: function (response) {
      if (response && response.data && response.data.error_code === 'BSS-00000000') {
        return response.data.data == null ? [] : response.data.data
      } else if (response && response.data && response.data.error_code === 'BSS-00000204') {
      } else {
        this.$root.$toast.error('Lấy dữ liệu không thành công ' + response.data.message)
      }
      return []
    },
    GetData: function (response) {
      console.log(response)
      if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
        return response.data.data
      } else if (response && response.data && response.data.error_code === 'BSS-00000204') {
        return null
      }
      return null
    },
    async LoadTuHopDongB2A (phieucskh_id, phieucskhdv_id) {
      this.VPHIEUCSKH_ID = phieucskh_id
      this.VPHIEUCSKHDV_ID = phieucskhdv_id
      let dt = await this.sp_tt_db_thuebao(phieucskhdv_id, 1)
      if (dt.length > 0) {
        this.cboDichVuVT.value = Number(dt[0].dichvuvt_id)
        this.formProps.txtMaTB = dt[0].ma_tb
        await this.formProps.txtMaTB_KeyPress()
      } else {
        this.$root.$toast.warning('Không tìm thấy thuê bao theo phiếu')
      }
    },
    async cboTenGoi_SelectedValueChanged (newValue) {
      await this.SP_DS_GOI_DADV(newValue)
    },
    async cboTenGoi2_EditValueChanged (newValue) {
      await this.SP_DS_GOI_DADV(newValue)
    },
    cboHieuLuc_SelectedIndexChanged (newValue) {
      this.visibleBox.txtSoThang = newValue == 2
    },
    cboNghiepvu_SelectedIndexChanged (newValue) {
      if (this.cboDichVuVT.value == DichVuVienThong.DI_DONG) {
        if (this.cboKieuLD.value == KieuLapDat.TDTB_NHOMDADV) {
          if (newValue == 1 || newValue == 0) {
            this.dgvGoi.disabled = true
            this.disabledBox.txtMaTBHT = true
          } else {
            this.dgvGoi.disabled = false
            this.disabledBox.txtMaTBHT = false
            this.formProps.txtMaTBHT = ''
            this.dgvGoi.data = []
          }
        }
      }
    },
    cboKieuLD_SelectedIndexChanged (newValue) {
      if (this.cboDichVuVT.value == DichVuVienThong.DI_DONG) {
        if (newValue == KieuLapDat.TDTB_GOIDADV) {
          this.cboTenGoi2.disabled = false
          this.setVisibleColumn('ten_goi_moi', true)
          this.disabledBox.txtMaTBHT = true
          this.cboHieuLuc.value = 1
        } else {
          this.cboTenGoi2.disabled = true
          this.setVisibleColumn('ten_goi_moi', true)
          this.disabledBox.txtMaTBHT = false
          this.cboHieuLuc.value = 0
        }
        if (newValue == KieuLapDat.TDTB_NHOMDADV || newValue == KieuLapDat.TDTB_GOIDADV) {
          this.formProps.lineLabel1 = 'Thông tin gói cước chuyển đến'
        } else {
          this.formProps.lineLabel1 = 'Thông tin gói cước hiện tại'
        }
        if (newValue == KieuLapDat.TDTB_NHOMDADV) {
          this.dgvGoi_ht.visible = true
          this.visibleBox.txtGhiChu = false
          this.cboNghiepvu.disable = false
          this.dgvGoi.disabled = true
          this.disabledBox.txtMaTBHT = true
          this.cboTenGoi.disable = true
        } else {
          this.dgvGoi_ht.visible = false
          this.visibleBox.txtGhiChu = true
          this.cboNghiepvu.value = 0
          this.cboNghiepvu.disable = true
          this.dgvGoi.disabled = false
          this.disabledBox.txtMaTBHT = false
          this.cboTenGoi.disable = false
        }
        if (newValue == KieuLapDat.THEM_TB_GOI_DADV) {
          this.cboTenGoi.disable = false
        } else {
          this.cboTenGoi.disable = true
        }
      } else {
        this.setVisibleColumn('ten_goi_moi', false)
      }
    },
    setVisibleColumn (id, status) {
      let index = this.dgrList.headers.findIndex(item => item.fieldName == id)
      if (index > 0) {
        this.dgrList.headers[index].visible = status
      }
    },
    cboLoaihinhTB_SelectedValueChanged (newValue) {
      if (this.cboLoaihinhTB.list.length > 0) {
        this.loaitb_id = newValue
        this.CSS_KIEU_LD()
        this.LAY_DS_GOI_CV_VNP_V1()
      }
    },
    async CSS_KIEU_LD () {
      this.cboKieuLD.list = []
      var input = {
        vloaihd_id: LoaiHopDong.THAYDOI_GOI_DADV,
        vloaitb_id: Number(this.loaitb_id)
      }
      let data = this.GetDataList(await ChangeMultiServicePackageAPI.layds_kieuld_theo_lhtb(this.axios, input))
      if (data.length > 0) {
        this.cboKieuLD.list = data.filter(item => item.loaihd_id == LoaiHopDong.THAYDOI_GOI_DADV).map(item => {
          return {
            id: item.kieuld_id,
            text: item.ten_kieuld
          }
        })
      } else {
        this.$toast.error('Quy trình chưa được thiết lập, bạn hãy liên hệ admin để xử lý!')        
      }
    },
    async fetchDichVuVT () {
      let data = this.GetDataList(await ChangeMultiServicePackageAPI.GET_DICHVU_VT(this.axios))
      let ListDVVT = [1, 2, 4, 8, 9, 10, 11, 12, 13, 14, 15, 16]
      if (data.length > 0) {
        data.forEach(item => {
          if (ListDVVT.includes(item.DICHVUVT_ID)) {
            this.cboDichVuVT.list.push({
              id: item.DICHVUVT_ID,
              text: item.TEN_DVVT
            })
          }
        })
        if (this.cboDichVuVT.list.length > 0) {
          this.cboDichVuVT.value = this.cboDichVuVT.list[0].id
        }
      }
    },
    async GET_GOI_DA_DV_ALL () {
      const req = {'id': ''}
      let data = this.GetDataList(await ChangeMultiServicePackageAPI.GET_GOI_DA_DV_ALL(this.axios, req))
      if (data.length > 0) {
        this.cboTenGoi.list = data.map(item => {
          return {
            id: item.goi_id,
            text: item.ten_goi
          }
        })
        if (this.cboTenGoi.list.length > 0) {
          this.cboTenGoi.value = this.cboTenGoi.list[0].id
        }
      }
    },
    async SP_DS_GOI_DADV (goiId) {
      const req = {'id': goiId}
      let data = this.GetDataList(await ChangeMultiServicePackageAPI.GET_GOI_DA_DV_ALL(this.axios, req))
      if (data.length > 0) {
        this.formProps.txtMoTa = data[0].mota
      }
    },
    async SP_DS_HIEULUC () {
      let data = this.GetDataList(await ChangeMultiServicePackageAPI.SP_DS_HIEULUC(this.axios))
      console.log(data)
      if (data.length > 0) {
        this.cboHieuLuc.list = data.map(item => {
          return {
            id: item.maghep_id,
            text: item.maghep
          }
        })
        if (this.cboHieuLuc.list.length > 0) {
          this.cboHieuLuc.value = this.cboHieuLuc.list[0].id
        }
      } else {
        this.cboHieuLuc.list = []
        this.cboHieuLuc.value = 0
      }
    },
    async SP_DS_NGHIEPVU () {
      let data = this.GetDataList(await ChangeMultiServicePackageAPI.SP_DS_NGHIEPVU(this.axios))
      if (data.length > 0) {
        this.cboNghiepvu.list = data.map(item => {
          return {
            id: item.nghiepvu_id,
            text: item.nghiepvu
          }
        })
        if (this.cboNghiepvu.list.length > 0) {
          this.cboNghiepvu.value = this.cboNghiepvu.list[0].id
        }
      } else {
        this.cboNghiepvu.list = []
        this.cboNghiepvu.value = 0
      }
    },
    async LAY_DS_GOI_CV_VNP (goiId) {
      return this.GetDataList(await ChangeMultiServicePackageAPI.LAY_DS_GOI_CV_VNP(this.axios, {
        vgoi_id: goiId
      }))
    },
    async SP_TT_NHOM_GOI_TACH (maTb) {
      return this.GetDataList(await ChangeMultiServicePackageAPI.SP_TT_NHOM_GOI_TACH(this.axios, {
        ma_tb: maTb
      }))
    },
    async SP_LAY_SOCONGVAN_GOIDADV (goiId) {
      const request = {
        'id': goiId
      }
      return this.GetDataList(await ChangeMultiServicePackageAPI.SP_LAY_SOCONGVAN_GOIDADV(this.axios, request))
    },
    async SP_INS_HDTB_THAYDOI_GOIDADV () {
      const payload = {
        hd_thuebao: this.dsHDTB,
        hdtb_goi_dadv: this.dsHDTBGOI,
        huonggiao_id: this.huonggiaotn_id,
        phanvung_id: this.phanvungId
      }
      await ChangeMultiServicePackageAPI.SP_INS_HDTB_THAYDOI_GOIDADV(this.axios, payload).then(response => {
        if (response && response.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
          this.$root.$toast.success('Thêm mới hợp đồng thuê bao thành công')
        } else {
          this.$root.$toast.error('Thêm mới hợp đồng thuê bao thất bại:' + response.data.message)
        }
      }).catch(error => {
        console.log(error)
        this.$root.$toast.error('Thêm mới hợp đồng thuê bao thất bại:' + error.data.message)
      })
    },    
    async tsbtnGhiLai_Click () {
      try {
        this.loading(true)
        if (this.goi_id == 0) {
          this.$root.$toast.error('Chưa có thông tin về gói cước hiện tại')
          this.$refs.txtMaTBHT.focus()
          return
        }        
        
        if (this.cboKieuLD.value == 0) {
          this.$root.$toast.error('Bạn chưa chọn kiểu lắp đặt!')
          return
        }

        if (this.dgvGoi_ht.selectItem && this.dgvGoi_ht.selectItem.goi_neo_id && 
          (this.cboKieuLD.value == KieuLapDat.DANGKY_GOI_DADV ||
          this.cboKieuLD.value == KieuLapDat.HUY_GOI_DADV ||
          this.cboKieuLD.value == KieuLapDat.THEM_TB_GOI_DADV ||
          this.cboKieuLD.value == KieuLapDat.XOA_TB_GOI_DADV)) {
          
          var check_hieuluc = await this.KT_HIEULUC_GOI_DADV(this.goi_id)
          if (!check_hieuluc || check_hieuluc.length == 0) {
            this.$toast.error('Không kiểm tra được thông tin hiệu lực của gói ' + this.goi_id)
            return
          } 

          if (this.cboKieuLD.value == KieuLapDat.DANGKY_GOI_DADV || this.cboKieuLD.value == KieuLapDat.HUY_GOI_DADV) {
            if (check_hieuluc[0]['hieuluc_dk'] == 0 || check_hieuluc[0]['hieuluc_dk'] == '0') {
              this.$toast.error('Gói không còn hiệu lực, bạn không được phép đăng ký/hủy trên onebss!')
              return
            }
          }          

          if (this.cboKieuLD.value == KieuLapDat.THEM_TB_GOI_DADV || this.cboKieuLD.value == KieuLapDat.XOA_TB_GOI_DADV) {
            if (check_hieuluc[0]['hieuluc_bs'] == 0 || check_hieuluc[0]['hieuluc_bs'] == '0') {
              this.$toast.error('Bạn không được phép thêm/xóa thành viên!')
              return
            }
          }
        }

        if (this.cboKieuLD.value == KieuLapDat.TDTB_GOIDADV) {
          if (this.cboTenGoi2.selectTenGoi2 == null) {
            this.$root.$toast.error('Chưa có thông tin về gói cước mới !')
            return
          }
          if (this.cboTenGoi.value == this.cboTenGoi2.selectTenGoi2) {
            this.$root.$toast('Gói chuyển đến giống gói hiện tại. Hãy chọn gói chuyển mới!')
            return
          }
          if (this.cboTenGoi.value != 0 && this.cboTenGoi2.selectTenGoi2 != null) {
            const check = await this.KT_GDVP_HOME()
            if (check != '1') {
              this.$root.$toast.error('Hệ thống chỉ cho phép chuyển từ gói GD, VP => gói HOME hoặc gói GD, VP khác. Bạn không thể chuyển GD, VP sang gói khác!')
              return
            }
          }
        }
        if (this.cboKieuLD.value == KieuLapDat.TDTB_NHOMDADV) {
          if (this.cboNghiepvu.value == '0') {
            this.$root.$toast.error('Bạn chưa chọn nghiệp vụ gói!')
            return
          }
          if (this.dgvGoi_ht.data.length > 0) {
            if (this.cboNghiepvu.value == 2) {
              if (this.dgvGoi_ht.data.length == 0 || this.dgvGoi.data.length == 0) {
                this.$root.$toast.warning('Chưa có danh sách nhóm thuê bao để ghép gói!')
                this.$refs.txtMaTBHT.focus()
                return
              }
              if (this.dgvGoi.data.length > 0) {
                if (!this.dgvGoi.data[0]['nhomtb_id_neo']) {
                  this.$root.$toast.warning('Không tìm thấy nhóm thuê bao neo của nhóm mới, hãy kiểm tra lại')
                  return
                }
              }
            }
            if (this.dgvGoi_ht.data.length > 0) {
              if (this.dgvGoi_ht.selectItem.goi_neo_id) {
                this.$root.$toast.warning('Không thực hiện tách ghép với gói cước hiện tại. Hãy kiểm tra lại !')
                return
              }
              if (!this.dgvGoi_ht.selectItem.nhomtb_id_neo) {
                this.$root.$toast.warning('Không tìm thấy nhóm thuê bao neo của nhóm hiện tại, Hãy kiểm tra lại')
                return
              }
            }

            if (this.cboNghiepvu.value == 2) {
              if (this.dgvGoi.selectItem.goi_neo_id) {
                this.$root.$toast.warning('Gói cước thực hiện ghép không thỏa mãn điều kiện. Hãy kiểm tra lại !')
                return
              }
              if (this.dgvGoi.selectItem.nhomtb_id_neo == this.dgvGoi_ht.selectItem.nhomtb_id_neo) {
                this.$root.$toast.warning('Không thực hiện nghiệp vụ ghép với gói trùng nhau !')
                this.$refs.txtMaTBHT.focus()
                return
              }
            }
            if (this.cboNghiepvu.value == 1) {
              let yes = false
              yes = await this.$confirm('Bạn đang thực hiện tách thuê bao ' + this.formProps.txtMaTB + ' ra một gói mới?', 'Thông báo', {
                confirmButtonText: 'Có',
                cancelButtonText: 'Không'
              }).then(() => {
                yes = true
              })
              if (yes === false) {
                return
              }
            } else {
              let yes = false
              yes = await this.$confirm('Bạn đang thực hiện ghép tất cả các số của thuê bao ' + this.formProps.txtMaTB + ' vào nhóm của số thuê bao ' + this.formProps.txtMaTBHT + ' ?', 'Thông báo', {
                confirmButtonText: 'Có',
                cancelButtonText: 'Không'
              }).then(() => {
                yes = true
              })
              if (yes === false) {
                return
              }
            }
          } else {
            this.$root.$toast.warning('Không có danh sách các gói cước hiện tại, bạn không thể làm nghiệp vụ tách - ghép !')
            return
          }
        }
        if (this.actions[this.actions.findIndex(x => x.id === 'tsbtnNhapMoi')].active === false) {
          const gdadv = await this.KIEMTRA_THAYDOI_GOI_DADV(Number(this.cboKieuLD.value), this.thuebao_id, this.goi_id, this.nhomtb_id, this.hdkh_id)
          if (gdadv !== '1') {
            this.$root.$toast.error(gdadv)
            return
          }

          if (this.cboKieuLD.value == KieuLapDat.DANGKY_GOI_DADV) {
            // Sửa nếu là dịch vụ di động thì k cần kiểm tra cùng đôi cáp 07/02/2020
            if (this.cboDichVuVT.value != DichVuVienThong.DI_DONG) {
              if (await this.KT_FIBER_MYTV_CUNG_CAP(this.goi_id, 0, '') !== '1') {
                this.$root.$toast.error('Không thể tạo mới gói Gia đình / Văn phòng!')
              }
            }
          }
          if (this.cboKieuLD.value == KieuLapDat.THEM_TB_GOI_DADV) {
            //  Sửa nếu là dịch vụ di động thì k cần kiểm tra cùng đôi cáp 07/02/2020
            if (this.cboDichVuVT.value != DichVuVienThong.DI_DONG) {
              if (await this.KT_FIBER_MYTV_CUNG_CAP(this.goi_id, this.thuebao_id, this.formProps.txtMaTBHT) !== '1') {
                this.$root.$toast.error('Thuê bao: ' + this.formProps.txtMaTB + ' và thuê bao: ' + this.formProps.txtMaTBHT + ' không cùng cáp, Không thể thực hiện thêm vào gói Gia đình!')
                return
              }
            }
          }
          if (this.cboKieuLD.value == KieuLapDat.DANGKY_GOI_DADV ||
            this.cboKieuLD.value == KieuLapDat.THEM_TB_GOI_DADV ||
            this.cboKieuLD.value == KieuLapDat.XOA_TB_GOI_DADV) {
            let kt_hieuluc = await this.KT_GOI_DADV_HIEULUC(this.goi_id, this.thuebao_id, 0, this.cboKieuLD.value)

            if (this.cboDichVuVT.value == DichVuVienThong.DI_DONG) {
              kt_hieuluc = '1'
            }
            if (kt_hieuluc != '1') {
              this.$root.$toast.error(kt_hieuluc)
              return
            }
          }

          if (this.cboKieuLD.value == KieuLapDat.HUY_GOI_DADV) {
            let kt = await this.KT_HUYGOI_FAMILY(this.goi_id)
            if (this.cboDichVuVT.value == DichVuVienThong.DI_DONG) {
              kt = '1'
            }
            if (kt != '1') {
              this.$root.$toast.error(kt)
              return
            }
          }
          await this.TaoDuLieu(true)
          if (this.loi_sinh_magd) {
            this.loi_sinh_magd = false
            return
          }
          let request = {
            'huonggiao_id': this.huonggiaotn_id,
            'js_hd_khachhang': JSON.stringify(this.dsHDKH),
            'js_hd_thuebao': JSON.stringify(this.dsHDTB),
            'js_hdtb_goi_dadv': JSON.stringify(this.dsHDTBGOI)
          }
          let result = false
          await ChangeMultiServicePackageAPI.SP_INS_HOPDONG_THAYDOI_GOIDADV(this.axios, request).then(response => {
            if (response && response.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
              result = true
              this.$root.$toast.success('Thêm mới hợp đồng thành công')
            } else {
              result = false
              this.$root.$toast.error('Thêm mới hợp đồng thất bại: ' + response.data.message_detail)
            }
          }).catch(error => {
            console.log(error)
            result = false
            this.$root.$toast.error('Có lỗi khi thêm mới hợp đồng: ' + error.data.message_detail)
          })
          if (!result) {
            return
          }
          if (this.cboKieuLD.value == KieuLapDat.HUY_GOI_DADV ||
            this.cboKieuLD.value == KieuLapDat.TDTB_NHOMDADV ||
            this.cboKieuLD.value == KieuLapDat.TDTB_GOIDADV) {
            let ds = []
            // sửa goi id neo => gói gia đình - vp - home, còn lại thì k phải gói GD-VP-Home
            let dt_goi_neo_id = await this.LAY_DS_GOI_CV_VNP(this.cboTenGoi.value)
            if (dt_goi_neo_id.length > 0 && dt_goi_neo_id[0].goi_neo_id) {
              if (this.cboKieuLD.value == KieuLapDat.HUY_GOI_DADV ||
                this.cboKieuLD.value == KieuLapDat.TDTB_GOIDADV) {
                // Tu dong them hop dong voi cac thue bao con lai trong goi khi huy goi da dich vu
                ds = await this.SP_LAYDS_TB_THEO_NHOMTB_GOI_V1(this.goi_id, this.nhomtb_id, this.thuebao_id)
              }
            } else {
              this.truyen_nhom_neo = ''
              let nhom_neo = await this.FN_LAYNHOMNEO_THEO_NHOMTB_GOI(this.goi_id, this.nhomtb_id)
              if (nhom_neo && this.cboKieuLD.value == KieuLapDat.HUY_GOI_DADV) {
                this.truyen_nhom_neo = nhom_neo
                ds = await this.SP_LAYDS_TB_THEO_NHOMTB_GOI_V2(Number.parseInt(nhom_neo), this.thuebao_id)
              }
              // thêm cả khi thay đổi gói đa dịch vụ cũng load
              if (!nhom_neo && this.cboKieuLD.value == KieuLapDat.HUY_GOI_DADV) {
                ds = await this.SP_LAYDS_TB_THEO_NHOMTB_GOI_V3(this.goi_id, this.nhomtb_id, this.thuebao_id, this.cboDichVuVT.value)
              }
              // Thêm TH ghép gói
              if (this.cboKieuLD.value == KieuLapDat.TDTB_NHOMDADV && this.cboNghiepvu.value == 2) {
                if (!this.dgvGoi_ht.selectItem.nhomtb_id_neo) {
                  this.$root.$toast.warning('Không tìm thấy nhóm thuê bao neo của nhóm hiện tại. Hãy kiểm tra lại!')
                  return
                } else {
                  ds = await this.SP_LAYDS_TB_THEO_NHOMTB_GOI_V4(this.dgvGoi_ht.selectItem.nhomtb_id_neo, this.thuebao_id)
                }
              }
            }
            if (ds.length > 0) {
              for (let i = 0; i < ds.length; i++) {
                let item = ds[i]
                this.thuebao_id = item.thuebao_id
                this.formProps.txtMaTB = item.ma_tb
                this.ten_tb = item.ten_tb
                this.diachi_tb = item.diachi_tb
                this.doituong_id = Number.parseInt(item.doituong_id)
                this.dichvuvt_id = Number.parseInt(item.dichvuvt_id)
                this.loaitb_id = item.loaitb_id
                this.donvi_id = item.donvi_id
                await this.ThemHDThueBao1()
              }
            }
          }
        } else {
          let gda_dv = await this.KIEMTRA_THAYDOI_GOI_DADV(this.cboKieuLD.value, this.thuebao_id, this.goi_id, this.nhomtb_id, this.hdkh_id)
          if (gda_dv != '1') {
            this.$root.$toast.warning(gda_dv)
            return
          }
          await this.TaoDuLieu(false)
          let result = await this.SP_UPDATE_HDTB_GOIDADV(this.dsHDKH, this.dsHDTB, this.dsHDTBGOI)
          if (!result) {
            return
          }
        }

        this.$root.$toast.success('Đã cập nhật dữ liệu lên hệ thống của Viễn thông tỉnh thành công!')
        // Cap nhat trang thai ttphdv_id sau khi lien ket hd
        if (this.VPHIEUCSKHDV_ID > 0) {
          await this.B2A_HT_PHIEUCSKHDV(this.VPHIEUCSKH_ID, 1)
        }
        let dsHd = await this.LAY_DS_HOPDONG_THEO_MA_GD(this.formProps.txtMaGD.trim(), LoaiHopDong.THAYDOI_GOI_DADV, TrangThaiHD.MOI)
        console.log(dsHd)
        await this.HienThiTTHopDongKH(dsHd)
        this.setActiveActions(3)
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Có lỗi xảy ra khi cập nhật dữ liệu:' + error.message)
      } finally {
        this.loading(false)
      }
    },
    async B2A_HT_PHIEUCSKHDV (phieucskhdvid, kieu) {
      await ChangeMultiServicePackageAPI.B2A_HT_PHIEUCSKHDV(this.axios, {
        'phieucskhdv_id': phieucskhdvid,
        'kieu': kieu
      }).then(response => {
        if (response && response.data && response.data.error_code === 'BSS-00000000') {
        } else {
          this.$root.$toast.error('Cập nhật trạng thái ttphdv_id sau khi liên kết hd: ' + response.data.message)
        }
      }).catch(error => {
        console.log(error)
        this.$root.$toast.error('Có lỗi xảy ra khi cập nhật trạng thái ttphdv_id sau khi liên kết hd ' + error.data.message)
      })
    },
    async ThemHDThueBao1 () {
      try {
        let kthdtkhithemoi = await this.KIEMTRA_HDTB_KHI_THEMMOI(this.formProps.txtMaTB.trim(), this.hdkh_id, this.dichvuvt_id, this.loaitb_id)
        if (kthdtkhithemoi) {
          this.$root.$toast.warning('Thuê bao đã tồn tại trong hợp đồng, không thêm mới được')
          return
        }
        let ktdbkhithemmoi = await this.KIEMTRA_DB_KHI_THEMMOI(this.formProps.txtMaTB.trim(), 0, this.thuebao_id, this.dichvuvt_id)
        if (!ktdbkhithemmoi) {
          this.$root.$toast.warning('Thuê bao không tồn tại trong danh bạ, không thêm mới được')
          return
        }
        const gdadv = await this.KIEMTRA_THAYDOI_GOI_DADV(this.cboKieuLD.value, this.thuebao_id, this.goi_id, this.nhomtb_id, this.hdkh_id)
        if (gdadv != '1') {
          this.$root.$toast.warning(gdadv)
          return
        }
        await this.TaoDuLieu_HDTB(true)
        await this.TaoDuLieu_HDTB_GOI_DADV(true)
        await this.SP_INS_HDTB_THAYDOI_GOIDADV()
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Có lỗi xảy ra khi thêm hợp đồng thuê bao: ' + error.message)
      }
    },
    async SP_UPDATE_HDTB_GOIDADV (dsHDKH, dsHDTB, dsHDTBGOI) {
      let result = false
      await ChangeMultiServicePackageAPI.SP_UPDATE_HDTB_GOIDADV(this.axios, {
        'json_hdkh': JSON.stringify(dsHDKH),
        'json_hdtb': JSON.stringify(dsHDTB),
        'json_goi': JSON.stringify(dsHDTBGOI)
      }).then(response => {
        if (response && response.data && response.data.error_code === 'BSS-00000000') {
          result = true
          this.$root.$toast.success('Cập nhật hợp đồng gói đa dịch vụ thành công')
        } else {
          result = false
          this.$root.$toast.error('Cập nhật hợp đồng gói đa dịch vụ thất bại: ' + response.data.message_detail)
        }
      }).catch(error => {
        result = false
        console.log(error)
        this.$root.$toast.error('Có lỗi xảy ra khi cập nhật hợp đồng gói đa dịch vụ: ' + error.data.message_detail)
      })
      return result
    },
    async SP_LAYDS_TB_THEO_NHOMTB_GOI_V4 (nhomtbidneo, thuebaoid) {
      return this.GetDataList(await ChangeMultiServicePackageAPI.SP_LAYDS_TB_THEO_NHOMTB_GOI_V4(this.axios, {
        'nhomtb_id_neo': nhomtbidneo,
        'thuebao_id': thuebaoid
      }))
    },
    async SP_LAYDS_TB_THEO_NHOMTB_GOI_V3 (goiid, nhomtbid, thuebaoid, dichvuvtid) {
      return this.GetDataList(await ChangeMultiServicePackageAPI.SP_LAYDS_TB_THEO_NHOMTB_GOI_V3(this.axios, {
        'goi_id': goiid,
        'nhomtb_id': nhomtbid,
        'thuebao_id': thuebaoid,
        'dichvuvt_id': dichvuvtid
      }))
    },
    async SP_LAYDS_TB_THEO_NHOMTB_GOI_V2 (nhomidneo, thuebaoid) {
      return this.GetDataList(await ChangeMultiServicePackageAPI.SP_LAYDS_TB_THEO_NHOMTB_GOI_V2(this.axios, {
        'nhom_id_neo': nhomidneo,
        'thuebao_id': thuebaoid
      }))
    },
    async FN_LAYNHOMNEO_THEO_NHOMTB_GOI (goiId, nhomtbId) {
      return this.GetData(await ChangeMultiServicePackageAPI.FN_LAYNHOMNEO_THEO_NHOMTB_GOI(this.axios, goiId, nhomtbId))
    },
    async SP_LAYDS_TB_THEO_NHOMTB_GOI_V1 (goiid, nhomtbid, thuebaoid) {
      let req = {
        'goi_id': goiid,
        'nhomtb_id': nhomtbid,
        'thuebao_id': thuebaoid
      }
      return this.GetDataList(await ChangeMultiServicePackageAPI.SP_LAYDS_TB_THEO_NHOMTB_GOI_V1(this.axios, req))
    },
    async TaoDuLieu (themoi) {
      await this.TaoDuLieu_HDKH(themoi)
      if (this.loi_sinh_magd) {
        this.loi_sinh_magd = false
        return
      }
      await this.TaoDuLieu_HDTB(themoi)
      await this.TaoDuLieu_HDTB_GOI_DADV(themoi)
    },
    // Tao du lieu cho bang HDTB_GOI_DADV
    async TaoDuLieu_HDTB_GOI_DADV (themmoi) {
      let chumhom = 0
      let dt_goi_ccbs = []
      const rowGOI = {}
      const rowGOICHUYEN = {}
      this.dsHDTBGOI = []
      dt_goi_ccbs = this.LAY_DS_GOI_CV_VNP(this.cboTenGoi.value)

      if (this.cboKieuLD.value == KieuLapDat.XOA_TB_GOI_DADV) {
        rowGOI.GOI_ID = this.cboTenGoi.value
        rowGOI.NHOMTB_ID = this.nhomtb_id
        rowGOI.CHUNHOM = chumhom
        rowGOI.HDTB_ID = this.hdtb_id
        let thang_huong = Number(this.cboHieuLuc.value)
        if (thang_huong == 2) {
          thang_huong = Number.parseInt(this.formProps.txtSoThang)
        }
        rowGOI.THANG_HUONG = thang_huong
        this.dsHDTBGOI.push(rowGOI)
      }
      if (this.cboKieuLD.value == KieuLapDat.THEM_TB_GOI_DADV) {
        let nhomneo = 0
        let kt_nhomneo = 0
        rowGOI.GOI_ID = this.cboTenGoi.value
        let goi_nhom = await this.SP_LAY_NHOMNEO_GOI_THEO_MATB(this.formProps.txtMaTBHT.trim(), this.goi_id)
        if (goi_nhom[0].nhomtb_id_neo) {
          nhomneo = Number(goi_nhom[0].nhomtb_id_neo)
          let lay_nhom = await this.SP_LAY_NHOMTB_THEO_NHOMNEO(this.cboTenGoi.value, nhomneo)
          if (lay_nhom.length > 0) {
            for (let i = 0; i < lay_nhom.length; i++) {
              if (lay_nhom[i].goi_id == this.cboTenGoi.value) {
                kt_nhomneo = 1
                break
              }
            }
          }
        } else {
          for (let i = 0; i < goi_nhom.length; i++) {
            if (goi_nhom[i].goi_id === this.cboTenGoi.value) {
              rowGOI.NHOMTB_ID = goi_nhom[i].nhomtb_id
              kt_nhomneo = 1
              break
            }
          }
        }
        if (kt_nhomneo === 0) {
          let nhomtbid = await this.POST_GET_KEYS('NHOMTB')
          rowGOI.NHOMTB_ID = Number(nhomtbid)
        }
        rowGOI.CHUNHOM = chumhom
        rowGOI.HDTB_ID = this.hdtb_id
        let thangHuong = this.cboNghiepvu.value
        if (thangHuong == 2) {
          thangHuong = Number.parseInt(this.formProps.txtSoThang)
        }
        rowGOI.THANG_HUONG = thangHuong
        this.dsHDTBGOI.push(rowGOI)
      }
      if (this.cboKieuLD.value == KieuLapDat.HUY_GOI_DADV || this.cboKieuLD.value == KieuLapDat.TDTB_NHOMDADV) {
        if (dt_goi_ccbs['goi_neo_id']) {
          const req = {}
          if (this.truyen_nhom_neo !== 0) {
            req.nhomtb_id_neo = this.truyen_nhom_neo
          } else {
            req.nhomtb_id_neo = null
          }
          if ((this.cboKieuLD.value == KieuLapDat.HUY_GOI_DADV || this.cboKieuLD.value == KieuLapDat.TDTB_NHOMDADV) && this.cboNghiepvu.value == 1) {
            req.goi_id = this.goi_id
          } else {
            this.goi_id = null
          }
          let goi_nhom = await this.SP_LAY_NHOMTB_THEO_MA_TB(this.formProps.txtMaTB.trim(), req.goi_id, req.nhomtb_id_neo)
          if (goi_nhom.length > 0) {
            rowGOI.GOI_ID = goi_nhom[0].goi_id
            rowGOI.NHOMTB_ID = goi_nhom[0].nhomtb_id
          } else {
            rowGOI.GOI_ID = this.cboTenGoi.value
            rowGOI.NHOMTB_ID = this.nhomtb_id
          }
        } else {
          rowGOI.GOI_ID = this.cboTenGoi.value
          rowGOI.NHOMTB_ID = this.nhomtb_id
        }
        rowGOI.CHUNHOM = chumhom
        rowGOI.HDTB_ID = this.hdtb_id
        let thang_huong = this.cboHieuLuc.value
        if (thang_huong == 2) {
          thang_huong = Number.parseInt(this.formProps.txtSoThang)
        }
        rowGOI.THANG_HUONG = thang_huong
        this.dsHDTBGOI.push(rowGOI)
      }
      if (this.cboKieuLD.value == KieuLapDat.TD3_GOIDADV) {
        chumhom = 0
        let dsLoaiTBChuNhom = this.GetDataList(await ChangeMultiServicePackageAPI.SP_LAY_LOAITB_CHUNHOM(this.axios, {
          goi_id: this.cboTenGoi.value
        }))
        if (dsLoaiTBChuNhom.length > 0) {
          if (dsLoaiTBChuNhom[0].loaitb_id == this.cboLoaihinhTB.value) {
            chumhom = 1
          }
        }
        rowGOI.GOI_ID = this.cboTenGoi.value
        rowGOI.NHOMTB_ID = this.nhomtb_id
        rowGOI.CHUNHOM = chumhom
        rowGOI.HDTB_ID = this.hdtb_id
        let thang_huong = this.cboHieuLuc.value
        if (thang_huong == 2) {
          thang_huong = Number.parseInt(this.formProps.txtSoThang)
        }
        rowGOI.THANG_HUONG = thang_huong
        this.dsHDTBGOI.push(rowGOI)
      }
      if (this.cboKieuLD.value === KieuLapDat.TDTB_NHOMDADV && this.cboNghiepvu.value == 2) {
        let goi_new = 0
        let goi_nhom = await this.SP_LAY_NHOMTB_DOINHOMTB(this.formProps.txtMaTB.trim())
        if (goi_nhom.length > 0) {
          goi_new = Number(goi_nhom[0].goi_id)
        }
        let goi_nhom_1 = await this.SP_LAY_NHOMTB_DOINHOMTB(this.formProps.txtMaTBHT.trim())

        if (this.cboDichVuVT.value == 2 && goi_nhom_1.length > 0) {
          for (let i = 0; i < goi_nhom_1.length; i++) {
            if (goi_new == goi_nhom_1[i].goi_id) {
              rowGOI.NHOMTB_ID = goi_nhom_1[i].nhomtb_id
              rowGOI.GOI_ID = goi_new
              rowGOI.CHUNHOM = 0
              rowGOI.HDTB_ID = this.hdtb_id
              let thang_huong = this.cboHieuLuc.value
              if (thang_huong == 2) {
                thang_huong = Number.parseInt(this.formProps.txtSoThang)
              }
              rowGOI.THANG_HUONG = thang_huong
              rowGOI.KIEU_YC = 1
              this.dsHDTBGOI.push(rowGOI)
              break
            }
          }
        }
      }
      // Trường hợp thay đổi gói đa dịch vụ thì thêm 1 row gói đích với kieu_yc=1  HTH
      if (this.cboKieuLD.value == KieuLapDat.TDTB_GOIDADV) {
        let _dt_goi_ccbs_kt = await this.LAY_DS_GOI_CV_VNP(this.cboTenGoi.value)
        let cv_ht = await this.SP_LAY_SOCONGVAN_GOIDADV(this.cboTenGoi2.selectTenGoi2)
        if (_dt_goi_ccbs_kt.length > 0 && _dt_goi_ccbs_kt[0].goi_neo_id && _dt_goi_ccbs_kt.length > 0 && cv_ht.length > 0 && _dt_goi_ccbs_kt[0].cv_vnp_id == cv_ht[0].cv_vnp_id) {
          let nhomneo = 0
          rowGOI.GOI_ID = this.cboTenGoi.value
          rowGOI.NHOMTB_ID = this.nhomtb_id
          rowGOI.CHUNHOM = chumhom
          rowGOI.HDTB_ID = this.hdtb_id
          let thang_huong = this.cboHieuLuc.value
          if (thang_huong == 2) {
            thang_huong = Number.parseInt(this.formProps.txtSoThang)
          }
          rowGOI.THANG_HUONG = thang_huong
          this.dsHDTBGOI.push(rowGOI)

          rowGOICHUYEN.GOI_ID = Number.parseInt(this.cboTenGoi2.selectTenGoi2)
          let goi_nhom = await this.SP_LAY_NHOMTB_DOINHOMTB(this.formProps.txtMaTB)
          if (!goi_nhom[0].nhomtb_id_neo) {
            nhomneo = goi_nhom[0].nhomtb_id_neo
          }

          let lay_nhom = await this.SP_LAY_NHOMTB_THEO_NHOMNEO(this.cboTenGoi2.selectTenGoi2, nhomneo)

          if (lay_nhom.length > 0) {
            rowGOICHUYEN.NHOMTB_ID = lay_nhom[0].nhomtb_id
          } else {
            rowGOICHUYEN.NHOMTB_ID = await this.POST_GET_KEYS('NHOMTB')
          }
          rowGOICHUYEN.CHUNHOM = 0
          rowGOICHUYEN.HDTB_ID = this.hdtb_id
          rowGOICHUYEN.THANG_HUONG = thang_huong
          rowGOICHUYEN.KIEU_YC = 1
          this.dsHDTBGOI.push(rowGOICHUYEN)
        } else {
          chumhom = 0
          if (_dt_goi_ccbs_kt.length > 0 && String(_dt_goi_ccbs_kt[0].goi_neo_id).trim() !== '') {
            let dsTBTheoNhomTb = await this.SP_LAY_THUEBAO_THEO_NHOMTB(this.goi_id, this.nhomtb_id)
            let tbid = dsTBTheoNhomTb[0].THUEBAO_ID

            if (this.thuebao_id == tbid) {
              chumhom = 1
            }
          }
          rowGOI.GOI_ID = this.cboTenGoi.value
          rowGOI.NHOMTB_ID = this.nhomtb_id
          rowGOI.CHUNHOM = chumhom
          rowGOI.HDTB_ID = this.hdtb_id
          let thang_huong = this.cboHieuLuc.value
          if (thang_huong == 2) {
            thang_huong = Number.parseInt(this.formProps.txtSoThang)
          }
          rowGOI.THANG_HUONG = thang_huong
          // Row thu 2 HYN
          this.dsHDTBGOI.push(rowGOI)
          if (this.nhomtb_id_new === 0) {
            this.nhomtb_id_new = await this.POST_GET_KEYS('NHOMTB')
          }
          rowGOICHUYEN.GOI_ID = this.cboTenGoi2.selectTenGoi2
          rowGOICHUYEN.NHOMTB_ID = this.nhomtb_id_new
          rowGOICHUYEN.CHUNHOM = chumhom
          rowGOICHUYEN.HDTB_ID = this.hdtb_id
          rowGOICHUYEN.THANG_HUONG = 0
          rowGOICHUYEN.KIEU_YC = 1
          this.dsHDTBGOI.push(rowGOICHUYEN)
        }
      }
      if (this.cboKieuLD.value == KieuLapDat.TDTB_NHOMDADV && this.cboNghiepvu.value == 1) {
        // Kiểm tra TH có gói khi tách thì hủy luôn
        let congvan_vnp = await this.SP_LAY_SOCONGVAN_GOIDADV(this.cboTenGoi.value)
        if (congvan_vnp.length > 0 && congvan_vnp[0].cv_vnp_id != 148 && congvan_vnp[0].cv_vnp_id != 399 && congvan_vnp[0].cv_vnp_id != 308) {
          if (this.dgvGoi_ht.data.length > 0) {
            rowGOICHUYEN.GOI_ID = Number(this.dgvGoi_ht.selectItem.goi_id)
          } else {
            rowGOICHUYEN.GOI_ID = this.cboTenGoi.value
          }

          if (this.nhom_tach === 0) {
            rowGOICHUYEN.NHOMTB_ID = await this.POST_GET_KEYS('NHOMTB')
          } else {
            rowGOICHUYEN.NHOMTB_ID = this.nhom_tach
          }
          rowGOICHUYEN.CHUNHOM = 0
          rowGOICHUYEN.HDTB_ID = this.hdtb_id
          rowGOICHUYEN.KIEU_YC = 2 // TACH THI SET 2
          let thang_huong = Number(this.cboHieuLuc.value)
          if (thang_huong === 2) {
            thang_huong = Number.parseInt(this.formProps.txtSoThang)
          }
          rowGOICHUYEN.THANG_HUONG = thang_huong
          this.dsHDTBGOI.push(rowGOICHUYEN)
        }
      }
    },
    async SP_LAY_THUEBAO_THEO_NHOMTB (goiId, nhomtbIb) {
      let request = {
        'nhomtb_id': nhomtbIb,
        'phanvung_id': this.phanvungId,
        'goi_id': goiId
      }
      return this.GetDataList(await ChangeMultiServicePackageAPI.SP_LAY_THUEBAO_THEO_NHOMTB(this.axios, request))
    },
    async SP_LAY_NHOMTB_THEO_MA_TB (maTb, goiId, nhomtbIdNeo) {
      const request = {
        'ma_tb': maTb,
        'goi_id': goiId,
        'nhomtb_id_neo': nhomtbIdNeo
      }
      return this.GetDataList(await ChangeMultiServicePackageAPI.SP_LAY_NHOMTB_THEO_MA_TB(this.axios, request))
    },
    async SP_LAY_NHOMTB_DOINHOMTB (matb) {
      return this.GetDataList(await ChangeMultiServicePackageAPI.SP_LAY_NHOMTB_DOINHOMTB(this.axios, {
        ma_tb: matb
      }))
    },
    async SP_LAY_NHOMTB_THEO_NHOMNEO (goiId, nhomTbIdNeo) {
      return this.GetDataList(await ChangeMultiServicePackageAPI.SP_LAY_NHOMTB_THEO_NHOMNEO(this.axios, {
        'goi_id': goiId,
        'nhomtb_id_neo': nhomTbIdNeo
      }))
    },
    async SP_LAY_NHOMNEO_GOI_THEO_MATB (matb, goiid) {
      return this.GetDataList(await ChangeMultiServicePackageAPI.SP_LAY_NHOMNEO_GOI_THEO_MATB(this.axios, {
        ma_tb: matb,
        goi_id: goiid
      }))
    },
    async POST_GET_KEYS (keyname) {
      return this.GetData(await ChangeMultiServicePackageAPI.POST_GET_KEYS(this.axios, {
        keyname: keyname
      }))
    },
    // Tao du lieu cho bang HD_THUEBAO
    async TaoDuLieu_HDTB (themmoi) {
      this.dsHDTB = []
      const rowHDTB = {}
      if (themmoi) {
        let hdtbid = await this.POST_GET_KEYS('HD_THUEBAO')
        this.hdtb_id = Number(hdtbid)
      }
      rowHDTB.HDTB_ID = this.hdtb_id
      rowHDTB.HDKH_ID = this.hdkh_id
      if (this.thuebao_id > 0) {
        rowHDTB.THUEBAO_ID = this.thuebao_id
      }
      rowHDTB.MA_TB = this.formProps.txtMaTB
      if (!this.ten_tb) {
        rowHDTB.TEN_TB = this.ten_kh_goi
      } else {
        rowHDTB.TEN_TB = this.ten_tb
      }

      if (!this.diachi_tb) {
        rowHDTB.DIACHI_TB = this.StringToString(this.diachi_kh_goi)
      } else {
        rowHDTB.DIACHI_TB = this.StringToString(this.diachi_tb)
      }

      rowHDTB.KIEULD_ID = this.cboKieuLD.value
      rowHDTB.DOITUONG_ID = this.doituong_id
      rowHDTB.TTHD_ID = TrangThaiHD.MOI
      rowHDTB.LOAITB_ID = this.loaitb_id
      rowHDTB.DICHVUVT_ID = this.cboDichVuVT.value
      rowHDTB.DONVI_ID = this.donviId
      rowHDTB.NGUOI_CN = this.nguoicn
      rowHDTB.NGAY_CN = moment(new Date()).format('DD/MM/YYYY')
      rowHDTB.MAY_CN = this.maycn
      const req = {
        'loaihd_id': LoaiHopDong.THAYDOI_GOI_DADV,
        'phanvung_id': this.$root.token.getPhanVungID(),
        'kieuld_id': this.cboKieuLD.value
      }
      let dsHuongGiao = this.GetDataList(await ChangeMultiServicePackageAPI.SP_LAYHUONGGIAO_TIEPNHAN(this.axios, req))
      if (dsHuongGiao.length > 0) {
        this.huonggiaotn_id = dsHuongGiao[0].huonggiao_id
        this.quytrinh_id = dsHuongGiao[0].quytrinh_id
      }
      rowHDTB.QUYTRINH_ID = this.quytrinh_id
      let dsMucCuoc = this.GetDataList(await ChangeMultiServicePackageAPI.SP_LAY_MUCCUOC_TB(this.axios, {
        thuebao_id: this.thuebao_id
      }))
      if (dsMucCuoc.length > 0) {
        rowHDTB.MUCUOCTB_ID = dsMucCuoc[0].mucuoctb_id
        rowHDTB.MUCUOC_TB = dsMucCuoc[0].mucuoc_tb
      }
      this.dsHDTB.push(rowHDTB)
    },
    async SINH_MAGD_V2 (loaihdid, donviid) {
      let req = {
        'loaihd_id': loaihdid,
        'donvi_id': donviid
      }
      return this.GetData(await ChangeMultiServicePackageAPI.SINH_MAGD_V2(this.axios, req))
    },
    async SINH_MAGD (loaihdid) {
      return this.GetData(await ChangeMultiServicePackageAPI.SINH_MAGD_V2(this.axios, {'loaihd_id': loaihdid}))
    },
    async TaoDuLieu_HDKH (themmoi) {
      this.dsHDKH = []
      const rowHDKH = {}
      let magdtemp = ''
      if (themmoi) {
        this.hdkh_id = await this.POST_GET_KEYS('HD_KHACHHANG')
      }
      rowHDKH.HDKH_ID = this.hdkh_id
      if (themmoi) {
        if (this.ts_sinhma_gd_theo_donvi) {
          magdtemp = await this.SINH_MAGD_V2(LoaiHopDong.THAYDOI_GOI_DADV, this.donviId)
        } else {
          magdtemp = await this.SINH_MAGD(LoaiHopDong.THAYDOI_GOI_DADV)
        }
        if (!magdtemp) {
          this.loi_sinh_magd = true
        }
        this.formProps.txtMaGD = magdtemp
        rowHDKH.MA_GD = magdtemp
      } else {
        rowHDKH.MA_GD = this.formProps.txtMaGD
      }
      rowHDKH.MA_KH = this.formProps.txtMaKH
      if (this.khachhang_id > 0) {
        rowHDKH.KHACHHANG_ID = this.khachhang_id
      }
      if (!this.formProps.txtTenKH) {
        rowHDKH.TEN_KH = this.StringToString(this.ten_kh_goi)
      } else {
        rowHDKH.TEN_KH = this.StringToString(this.formProps.txtTenKH)
      }
      if (!this.formProps.txtDiaChiKH) {
        rowHDKH.DIACHI_KH = this.StringToString(this.diachi_kh_goi)
      } else {
        rowHDKH.DIACHI_KH = this.StringToString(this.formProps.txtDiaChiKH)
      }
      rowHDKH.NGAYLAP_HD = this.dtpNgayLHD
      rowHDKH.NGAY_YC = this.formProps.dtpNgayYeuCau
      if (this.cboKieuLD.value == KieuLapDat.TDTB_NHOMDADV && this.cboNghiepvu.value == 1) {
        rowHDKH.GHICHU = 'Tách nhóm. ' + this.formProps.txtGhiChu
      } else if (this.cboKieuLD.value == KieuLapDat.TDTB_NHOMDADV && this.cboNghiepvu.value == 2) {
        rowHDKH.GHICHU = 'Ghép nhóm.' + this.formProps.txtGhiChu
      } else {
        rowHDKH.GHICHU = this.formProps.txtGhiChu
      }

      rowHDKH.DONVI_ID = this.donviId
      rowHDKH.NHANVIEN_ID = this.nhanvienId
      rowHDKH.LOAIHD_ID = LoaiHopDong.THAYDOI_GOI_DADV
      rowHDKH.NGAY_CN = moment(new Date()).format('DD/MM/YYYY')
      rowHDKH.NGUOI_CN = this.nguoicn
      rowHDKH.MAY_CN = this.maycn
      rowHDKH.IP_CN = this.ipcn

      if (this.hdkh_cha_id > 0) {
        rowHDKH.HDKH_CHA_ID = this.hdkh_cha_id
        this.hdkh_cha_id = 0
      }

      if (this.VPHIEUCSKH_ID !== 0) {
        rowHDKH.PHIEUCSKH_ID = this.VPHIEUCSKH_ID
      }

      this.dsHDKH.push(rowHDKH)
    },
    async KT_GDVP_HOME () {
      const req = {
        'id_tu': this.cboTenGoi.value,
        'id_den': this.cboTenGoi2.selectTenGoi2
      }
      return this.GetData(await ChangeMultiServicePackageAPI.KT_GDVP_HOME(this.axios, req))
    },
    async KIEMTRA_THAYDOI_GOI_DADV (kieuldid, thuebaoid, goiid, nhomtbid, hdkhid) {
      const req = {
        'vhdkh_id': hdkhid,
        'vkieuld_id': kieuldid,
        'vthuebao_id': thuebaoid,
        'goi_id': goiid,
        'vnhomtb_id': nhomtbid
      }
      return this.GetData(await ChangeMultiServicePackageAPI.KIEMTRA_THAYDOI_GOI_DADV(this.axios, req))
    },
    async KT_FIBER_MYTV_CUNG_CAP (goiId, thueBaoID, maTb) {
      const req = {
        'vthuebao_id': thueBaoID,
        'vma_tb': maTb,
        'goi_id': goiId
      }
      return this.GetData(await ChangeMultiServicePackageAPI.KT_FIBER_MYTV_CUNG_CAP(this.axios, req))
    },
    async KT_GOI_DADV_HIEULUC (goiId, tbId, hdtbId, kieuldId) {
      const req = {
        'vhdtb_id': hdtbId,
        'vkieuld_id': kieuldId,
        'vthuebao_id': tbId,
        'goi_id': goiId
      }
      return this.GetData(await ChangeMultiServicePackageAPI.KT_GOI_DADV_HIEULUC(this.axios, req))
    },
    async KT_HIEULUC_GOI_DADV (goi_id) {
      return this.GetDataList(await ChangeMultiServicePackageAPI.fn_ktra_hieuluc_goi_dadv_lhtb(this.axios, goi_id))
    },
    async KT_HUYGOI_FAMILY (goiId) {
      return this.GetData(await ChangeMultiServicePackageAPI.KT_HUYGOI_FAMILY(this.axios, {goi_id: goiId}))
    },
    async LAY_DS_GOI_CV_VNP_V1 () {
      const params = {'loaitb_id': this.cboLoaihinhTB.value}
      let data = this.GetDataList(await ChangeMultiServicePackageAPI.LAY_DS_GOI_CV_VNP_V1(this.axios, params))
      if (data.length > 0) {
        this.cboTenGoi2.data = data
      } else {
        this.cboTenGoi2.data = []
      }
    },
    tsbtnXoa_Click () {
      this.$confirm('Bạn thật sự muốn xóa dữ liệu không ?', 'Thông báo', {
        confirmButtonText: 'Có',
        cancelButtonText: 'Không'
      }).then(async () => {
        this.loading(true)
        const req = {}
        let list = []
        const itemDelete = {
          hdkh_id: this.hdkh_id,
          loaihd_id: LoaiHopDong.THAYDOI_GOI_DADV
        }
        list.push(itemDelete)
        req.list = list
        await ChangeMultiServicePackageAPI.XOAHDKH_V2(this.axios, req).then(response => {
          if (response && response.data && response.data.error_code === 'BSS-00000000') {
            this.$root.$toast.success('Xóa hợp đồng thành công')
            this.dgrList.data = []
            this.dgvGoi.data = []
            this.setActiveActions(0)
          } else {
            this.$root.$toast.error('Xóa hợp đồng thất bại: ', response.data.message)
          }
        }).catch(error => {
          console.log(error)
          this.$root.$toast.error('Có lỗi khi xóa hợp đồng: ', error.data.message)
        })
        this.loading(false)
      })
    },
    async KIEMTRA_HDTB_KHI_THEMMOI (maTb, hdkhId, dichVuVtId, loaiTbId) {
      let result = 0
      let req = {
        maTb: maTb,
        hdkhId: hdkhId,
        dichVuVtId: dichVuVtId,
        loaiTbId: loaiTbId
      }
      let data = this.GetData(await ChangeMultiServicePackageAPI.KIEMTRA_HDTB_KHI_THEMMOI(this.axios, req))
      if (data != null) {
        result = Number(data)
      }
      return result === 1
    },
    async KIEMTRA_DB_KHI_THEMMOI (maTb, khachHangId, thueBaoId, dichVuVtId) {
      let result = 0
      let req = {
        maTb: maTb,
        khachHangId: khachHangId,
        thueBaoId: thueBaoId,
        dichVuVtId: dichVuVtId
      }
      let data = this.GetData(await ChangeMultiServicePackageAPI.KIEMTRA_DB_KHI_THEMMOI(this.axios, req))
      if (data != null) {
        result = Number(data)
      }
      return result === 1
    },
    async tsbtnThemTB_Click () {
      try {
        this.loading(true)
        let kthdtkhithemoi = await this.KIEMTRA_HDTB_KHI_THEMMOI(this.formProps.txtMaTB.trim(), this.hdkh_id, Number(this.dichvuvt_id), this.loaitb_id)

        if (kthdtkhithemoi) {
          this.$root.$toast.warning('Thuê bao đã tồn tại trong hợp đồng, không thêm mới được')
          return
        }

        let ktdbkhithemmoi = await this.KIEMTRA_DB_KHI_THEMMOI(this.formProps.txtMaTB.trim(), 0, this.thuebao_id, this.dichvuvt_id)
        if (!ktdbkhithemmoi) {
          this.$root.$toast.warning('Thuê bao không tồn tại trong danh bạ, không thêm mới được')
          return
        }
        const gdadv = await this.KIEMTRA_THAYDOI_GOI_DADV(Number(this.cboKieuLD.value), this.thuebao_id, this.goi_id, this.nhomtb_id, this.hdkh_id)
        if (gdadv != '1') {
          this.$root.$toast.warning(gdadv)
          return
        }       

        // HTH thêm điều kiện check khi thêm thuê bao chuyển gói
        if (this.cboKieuLD.value == KieuLapDat.TDTB_GOIDADV || this.cboKieuLD.value == KieuLapDat.THEM_TB_GOI_DADV) {
          let kt = null
          let _dt_goi_ccbs_kt = []
          let cv_ht = []
          if (this.cboKieuLD.value == KieuLapDat.TDTB_GOIDADV) {
            if (this.dgvGoi.data.length > 0) {
              for (let i = 0; i < this.dgvGoi.data.length; i++) {
                if (this.formProps.txtMaTB == this.dgvGoi.data[i].ma_tb) {
                  kt = '1'
                }
              }
              if (kt != '1') {
                this.$root.$toast.warning('Thuê bao không tồn tại trong gói, không thêm mới được! ')
                return
              }
            }

            if (this.cboTenGoi2.selectTenGoi2 == null) {
              this.$root.$toast.warning('Chưa có thông tin về gói cước mới !')
              return
            }
            if (this.cboTenGoi.value != 0 && this.cboTenGoi2.selectTenGoi2 && this.cboTenGoi.value == this.cboTenGoi2.selectTenGoi2) {
              this.$root.$toast.warning('Gói chuyển đến giống gói hiện tại. Hãy chọn gói chuyển mới!')
              return
            }

            _dt_goi_ccbs_kt = this.LAY_DS_GOI_CV_VNP(this.cboTenGoi.value)
            cv_ht = this.SP_LAY_SOCONGVAN_GOIDADV(this.cboTenGoi2.selectTenGoi2)
            if (_dt_goi_ccbs_kt.length > 0 && cv_ht.length > 0) {
              if (_dt_goi_ccbs_kt[0].cv_vnp_id == cv_ht[0].cv_vnp_id) {
                this.$root.$toast.warning('Thuê bao không cùng công văn, không thêm mới được')
                return
              }
            }
          }

          if (this.cboKieuLD.value == KieuLapDat.THEM_TB_GOI_DADV) {
            _dt_goi_ccbs_kt = this.LAY_DS_GOI_CV_VNP(this.cboTenGoi.value)
            cv_ht = this.SP_LAY_SOCONGVAN_GOIDADV(this.dgvGoi.selectItem.goi_id)
            if (_dt_goi_ccbs_kt.length > 0 && cv_ht.length > 0) {
              if (_dt_goi_ccbs_kt[0].cv_vnp_id == cv_ht[0].cv_vnp_id) {
                this.$root.$toast.warning('Thuê bao không cùng công văn, không thêm mới được')
                return
              }
            }
          }
        }
        // HTH thêm điều kiện check tach: phat sinh tach cho phép tách nhiều số cùng gói 26/03
        if (this.cboKieuLD.value == KieuLapDat.TDTB_GOIDADV && this.cboNghiepvu.value == 1) {
          if (this.dgvGoi_ht.data.length > 0) {
            if (this.dgvGoi_ht.selectItem.goi_neo_id) {
              this.$root.$toast.warning('Không thực hiện tách ghép với gói cước hiện tai, Hãy kiểm tra lại !')
              return
            }

            if (!this.dgvGoi_ht.selectItem.nhomtb_id_neo) {
              this.$root.$toast.warning('Không tìm thấy nhóm thuê bao neo của nhóm hiện tại. Hãy kiểm tra lại !')
              return
            }
            if (this.dgvGoi_ht.selectItem.ma_tb != this.formProps.txtMaTB.trim()) {
              this.$root.$toast.warning('Hãy chọn đúng số thuê bao đang thực hiện tách trên lưới.')
              return
            }
            if (this.dgrList.data.length > 0) {
              let laynhomtach = await this.SP_TT_NHOM_GOI_TACH(this.dgrList.data[0].ma_tb)
              if (this.dgvGoi_ht.selectItem.goi_id == laynhomtach[0].goi_id) {
                this.nhom_tach = laynhomtach[0].nhomtb_id
              } else {
                this.$root.$toast.warning('Không được tách thêm thuê bao không cùng gói !')
                return
              }
            }
          }
        }

        if (this.dgvGoi_ht.selectItem && this.dgvGoi_ht.selectItem.goi_neo_id && 
          (this.cboKieuLD.value == KieuLapDat.DANGKY_GOI_DADV ||
          this.cboKieuLD.value == KieuLapDat.HUY_GOI_DADV ||
          this.cboKieuLD.value == KieuLapDat.THEM_TB_GOI_DADV ||
          this.cboKieuLD.value == KieuLapDat.XOA_TB_GOI_DADV)) {
          
          var check_hieuluc = await this.KT_HIEULUC_GOI_DADV(this.goi_id)
          if (!check_hieuluc || check_hieuluc.length == 0) {
            this.$toast.error('Không kiểm tra được thông tin hiệu lực của gói ' + this.goi_id)
            return
          } 

          if (this.cboKieuLD.value == KieuLapDat.DANGKY_GOI_DADV || this.cboKieuLD.value == KieuLapDat.HUY_GOI_DADV) {
            if (check_hieuluc[0]['hieuluc_dk'] == 0 || check_hieuluc[0]['hieuluc_dk'] == '0') {
              this.$toast.error('Gói không còn hiệu lực, bạn không được phép đăng ký/hủy trên onebss!')
              return
            }
          }  

          if (this.cboKieuLD.value == KieuLapDat.THEM_TB_GOI_DADV || this.cboKieuLD.value == KieuLapDat.XOA_TB_GOI_DADV) {
            if (check_hieuluc[0]['hieuluc_bs'] == 0 || check_hieuluc[0]['hieuluc_bs'] == '0') {
              this.$toast.error('Bạn không được phép thêm/xóa thành viên!')
              return
            }
          }
        }

        if (this.cboKieuLD.value == KieuLapDat.DANGKY_GOI_DADV ||
          this.cboKieuLD.value == KieuLapDat.THEM_TB_GOI_DADV ||
          this.cboKieuLD.value === KieuLapDat.TDTB_GOIDADV) {
          let kt_hieuluc = await this.KT_GOI_DADV_HIEULUC(this.goi_id, this.thuebao_id, 0, this.cboKieuLD.value)
          if (kt_hieuluc != '1') {
            this.$root.$toast.warning(kt_hieuluc)
            return
          }
        }
        await this.TaoDuLieu_HDTB(true)
        await this.TaoDuLieu_HDTB_GOI_DADV(true)
        await this.SP_INS_HDTB_THAYDOI_GOIDADV()
        this.setActiveActions(3)
        this.nhomtb_id = 258
        let dsHD = await this.LAY_DS_HOPDONG_THEO_MA_GD(this.formProps.txtMaGD, LoaiHopDong.THAYDOI_GOI_DADV, TrangThaiHD.MOI)
        await this.HienThiTTHopDongKH(dsHD)
        let dsGoi = []
        if (this.nhomtb_id !== 0) {
          if (this.cboKieuLD.value != KieuLapDat.THEM_TB_GOI_DADV) {
            dsGoi = await this.LAY_DS_TB_GOI_DADV_1(this.nhomtb_id)
          } else {
            dsGoi = await this.LAY_DS_TB_GOI_DADV(this.formProps.txtMaTBHT.trim())
          }
        } else {
          this.dsGoi = []
        }
        if (dsGoi.length > 0 && dsGoi[0].ma_tb !== '') {
          if (this.cboKieuLD.value != KieuLapDat.THEM_TB_GOI_DADV) {
            this.dgvGoi.data = dsGoi
            if (dsHD.length > 0) {
              this.formProps.txtMaTBHT = this.dgvGoi.data[0].ma_tb
            }
          }
        } else {
          this.goi_id = 0
          this.dgvGoi.data = []
          this.formProps.txtMaTBHT = ''
        }
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Có lỗi: ' + error.message)
      } finally {
        this.loading(false)
      }
    },
    tsbtnXoaTB_Click () {
      if (this.cboKieuLD.value == KieuLapDat.HUY_GOI_DADV) {
        this.$root.$toast.error('Khi hủy gói, bạn phải thực hiện xóa cả hợp đồng !')
        return
      }
      if (this.cboKieuLD.value == KieuLapDat.TDTB_NHOMDADV) {
        this.$root.$toast.error('Khi hủy nhóm, phạn phải thực hiện xóa cả hợp đồng !')
        return
      }

      if (this.formProps.txtMaTB) {
        this.$confirm('Bạn thật sự muốn xóa thuê bao không ?', 'Thông báo', {
          confirmButtonText: 'Có',
          cancelButtonText: 'Không'
        }).then(async () => {
          await this.XoaHDTB()
        })
      } else {
        this.$root.$toast.success('Bạn phải chọn thuê bao cần xóa trên lưới dữ liệu !!')
      }
    },
    async XoaHDTB () {
      await ChangeMultiServicePackageAPI.XOA_HDTB(this.axios, this.hdtb_id).then(response => {
        if (response && response.data && response.data.error_code === 'BSS-00000000') {
          this.$root.$toast.success('Xóa thuê bao hợp đồng thành công')
          this.dgrList.data = []
          this.dgvGoi.data = []
          this.HienThiDanhSachHDTB()
        } else {
          this.$root.$toast.error('Xóa thuê bao hợp đồng không thành công:', response.data.message)
        }
      }).catch(error => {
        console.log(error)
        this.$root.$toast.error('Có lỗi khi xóa thuê bao:', error.message_detail)
      })
    },
    async tsbtnCapNhatDB_Click () {
      try {
        this.loading(true)
        let ds_matb_dd = ''
        let dsmatbdd = []
        let goi_id_pttb = 0
        let nhomtb_id_pttb = 0
        let goi_neo_id = ''
        if (this.hdkh_id != 0) {
          let dsTS = await this.SP_DS_THAMSO_MD(null, 'DONGBO_GOI_DA_DV')
          let _dt_goi_ccbs = await this.LAY_DS_GOI_CV_VNP(Number(this.cboTenGoi.value))
          let dongbo_dadv = -1
          if (dsTS.length > 0) {
            dongbo_dadv = Number(dsTS[0].ten_ts)
          }
          if (dongbo_dadv == 1) {
            for (let i = 0; i < this.dgrList.data.length; i++) {
              let item = this.dgrList.data[i]
              let loaitb = item.loaitb_id
              let dongbo = await this.FN_TT_GOI_DADV_LHTB(this.goi_id, loaitb)
              if (dongbo == '0') {
                continue
              }
              if (loaitb == LoaiHinhTB.DIDONGTRASAU) {
                // đăng ký gói cước lên ccbs
                if (TRANGTHAI_DONGBO.OPEN_SYNC_VINA_TRASAU && _dt_goi_ccbs.length > 0) {
                  dsmatbdd.push(item.ma_tb)
                }
              }
            }
            if (dsmatbdd.length > 0) {
              ds_matb_dd = dsmatbdd.join(',')
            }
            if (TRANGTHAI_DONGBO.OPEN_SYNC_VINA_TRASAU) {
              if (this.cboKieuLD.value == KieuLapDat.TDTB_NHOMDADV) {
                if (!this.formProps.txtMaTBHT) {
                  this.$root.$toast.warning('Chưa có danh sách nhóm thuê bao để thực hiện các nghiệp vụ gói!')
                  return
                }
              }
              let checkhdtb = await this.SP_LAY_TT_NHOMTB_SD_GOI(this.formProps.txtMaTBHT.trim())
              if (checkhdtb.length > 0) {
                goi_id_pttb = checkhdtb[0].goi_id
                nhomtb_id_pttb = checkhdtb[0].nhomtb_id
              }
              let tongktra = await this.FN_KTRA_GOI_CHUA_DIDONG(goi_id_pttb, nhomtb_id_pttb)
              // Ko check dong bo thi ko kich hoat CCBS
              if (tongktra != '0' && ds_matb_dd != '') {
                if (_dt_goi_ccbs.length > 0 && !_dt_goi_ccbs[0].goi_neo_id) {
                  if (this.cboKieuLD.value == KieuLapDat.TDTB_NHOMDADV && this.cboNghiepvu.value == 1) {
                    let yes = false
                    this.$confirm('Bạn thật sự muốn tách thuê bao ' + this.formProps.txtMaTB + ' ra khỏi gói?', 'Thông báo', {
                      confirmButtonText: 'Có',
                      cancelButtonText: 'Không'
                    }).then(() => {
                      yes = true
                    })
                    if (yes === false) {
                      return
                    }
                  }
                } else if (this.cboKieuLD.value == KieuLapDat.TDTB_NHOMDADV && this.cboNghiepvu.value == 2) {
                  let yes = false
                  this.$confirm('Bạn đang ghép toàn bộ danh sách của số thuê bao ' + this.formProps.txtMaTB + ' vào gói của thuê bao ' + this.formProps.txtMaTBHT + ' ?', 'Thông báo', {
                    confirmButtonText: 'Có',
                    cancelButtonText: 'Không'
                  }).then(() => {
                    yes = true
                  })
                  if (yes === false) {
                    return
                  }
                } else if (this.cboKieuLD.value == KieuLapDat.HUY_GOI_DADV) {
                  let yes = false
                  this.$confirm('Bạn thật sự muốn hủy danh sách gói của thuê bao ' + this.formProps.txtMaTB + ' không ?', 'Thông báo', {
                    confirmButtonText: 'Có',
                    cancelButtonText: 'Không'
                  }).then(() => {
                    yes = true
                  })
                  if (yes === false) {
                    return
                  }
                }
              }
            }
          }
          let req = {
            'boqua_kichhoat_tb_dahuy': this.boqua_kichhoat_tb_dahuy,
            'cb_dichvuvt_id': Number(this.dichvuvt_id),
            'cb_goi_id': Number(this.cboTenGoi.value),
            'cb_goi_id2': Number(this.cboTenGoi2.selectTenGoi2),
            'cb_hieuluc_id': Number(this.cboHieuLuc.value),
            'cb_kieuld_id': Number(this.cboKieuLD.value),
            'cb_loaihinh_tb': Number(this.cboLoaihinhTB.value),
            'cb_nghiepvu': Number(this.cboNghiepvu.value),
            'cv_vnp_id': this.cv_hientai[0] ? Number(this.cv_hientai[0].cv_vnp_id) : 0,
            'dsHDTB': this.dgrList.data,
            'ghi_chu': this.formProps.txtGhiChu,
            'goi_id': Number(this.goi_id),
            'goi_vnp_id': Number(this.goimoi[0] ? this.goimoi[0].goi_vnp_id : 0),
            'hdkh_id': this.hdkh_id,
            'hdtb_id': this.hdtb_id,
            'ma_tb': this.formProps.txtMaTB,
            'matb_ht': this.formProps.txtMaTBHT
          }
          await ChangeMultiServicePackageAPI.hoanthien_hoso(this.axios, req).then(response => {
            if (response && response.data && response.data.error_code === 'BSS-00000000') {
              this.$root.$toast.success(response.data.message)
              this.dgvGoi.data = []
              this.dgrList.data = []
              if (this.cboKieuLD.value == KieuLapDat.TDTB_NHOMDADV) {
                this.dgvGoi_ht.data = []
              }
              this.setActiveActions(0)
            } else {
              this.$root.$toast.error('Hoàn thiện hs thất bại: ' + response.data.message)
            }
          }).catch(error => {
            console.log(error)
            this.$root.$toast.error('Có lỗi xảy ra khi hoàn thiện hs: ' + error.data.message_detail)
          })
        } else {
          this.$root.$toast.warning('Hãy chọn hợp đồng để hoàn thiện !')
        }
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Có lỗi: ' + error.message)
      } finally {
        this.loading(false)
      }
    },

    async FN_KTRA_GOI_CHUA_DIDONG (goiid, nhomtbid) {
      return this.GetData(await ChangeMultiServicePackageAPI.FN_KTRA_GOI_CHUA_DIDONG(this.axios, {
        'nhomtb_id': goiid,
        'id': nhomtbid
      }))
    },
    async SP_LAY_TT_NHOMTB_SD_GOI (matb) {
      return this.GetDataList(await ChangeMultiServicePackageAPI.SP_LAY_TT_NHOMTB_SD_GOI(this.axios, {
        'ma_tb': matb
      }))
    },
    tsbtnThongTinLH_Click () {
      if (this.hdkh_id !== 0) {
        this.$refs.thongtinlienhe.show()
      } else {
        this.$root.$toast.warning('Bạn chưa chọn phiếu !')
      }
    },
    tsbtnEmail_Click () {
      if (this.hdkh_id !== 0) {
        this.$refs.thongtinemail.show()
      } else {
        this.$root.$toast.warning('Bạn chưa chọn phiếu !')
      }
    },
    onActionClick (action) {
      if (action.id === 'tsbtnNhapMoi') {
        this.setActiveActions(1)
      } else if (action.id === 'tsbtnGhiLai') {
        this.tsbtnGhiLai_Click()
      } else if (action.id === 'tsbtnHuyBo') {
        this.setActiveActions(0)
        this.dgrList.data = []
        this.dgvGoi.data = []
      } else if (action.id === 'tsbtnXoa') {
        this.tsbtnXoa_Click()
      } else if (action.id === 'tsbtnThemTB') {
        this.tsbtnThemTB_Click()
      } else if (action.id === 'tsbtnXoaTB') {
        this.tsbtnXoaTB_Click()
      } else if (action.id === 'tsbtnCapNhatDB') {
        this.tsbtnCapNhatDB_Click()
      } else if (action.id === 'tsbtnThongTinLH') {
        this.tsbtnThongTinLH_Click()
      } else if (action.id === 'tsbtnEmail') {
        this.tsbtnEmail_Click()
      }
    },
    setActiveActions (kieu) {
      this.actions.forEach((item) => {
        if (item.id !== 'tsbtnEmail' && item.id !== 'tsbtnThongTinLH') {
          item.active = false
        }
      })
      if (kieu === -1) { // bat dau
        this.actions[this.actions.findIndex(x => x.id === 'tsbtnGhiLai')].active = true
        this.actions[this.actions.findIndex(x => x.id === 'tsbtnHuyBo')].active = true
        this.$refs.txtMaTB.focus()
      } else if (kieu === 0) { // bắt đầu
        this.actions[this.actions.findIndex(x => x.id === 'tsbtnNhapMoi')].active = true
        this.clear()
      } else if (kieu === 1) { // thêm mới
        this.actions[this.actions.findIndex(x => x.id === 'tsbtnGhiLai')].active = true
        this.actions[this.actions.findIndex(x => x.id === 'tsbtnHuyBo')].active = true
        this.cboKieuLD.value = '0'
        this.$refs.txtMaTB.focus()
        this.clear()
      } else if (kieu === 2) { // Huỷ
        this.actions[this.actions.findIndex(x => x.id === 'tsbtnNhapMoi')].active = true
        this.actions[this.actions.findIndex(x => x.id === 'tsbtnXoa')].active = true
        this.clear()
      } else if (kieu === 3) { // edit
        this.actions[this.actions.findIndex(x => x.id === 'tsbtnNhapMoi')].active = true
        this.actions[this.actions.findIndex(x => x.id === 'tsbtnGhiLai')].active = true
        this.actions[this.actions.findIndex(x => x.id === 'tsbtnHuyBo')].active = true
        this.actions[this.actions.findIndex(x => x.id === 'tsbtnXoa')].active = true
        this.actions[this.actions.findIndex(x => x.id === 'tsbtnXoaTB')].active = true
        this.actions[this.actions.findIndex(x => x.id === 'tsbtnThemTB')].active = true
        this.actions[this.actions.findIndex(x => x.id === 'tsbtnCapNhatDB')].active = true
        if (this.dgrList.data.length > 0) {
          this.actions[this.actions.findIndex(x => x.id === 'tsbtnXoaTB')].active = true
        } else {
          this.actions[this.actions.findIndex(x => x.id === 'tsbtnXoaTB')].active = false
        }
      }
    },
    clear () {
      this.hdkh_id = 0
      this.nhomtb_id = 0
      this.formProps.txtMaGD = ''
      this.txtMaTBTemp = ''
      this.formProps.txtMaKH = ''
      this.khachhang_id = 0
      this.formProps.txtTenKH = ''
      this.formProps.txtDiaChiKH = ''
      this.formProps.dtpNgayYeuCau = ''
      this.nhomtb_id_new = 0
      this.hdtb_id = 0
      this.thuebao_id = 0
      this.cboKieuLD.value = '0'
      this.formProps.txtMaTB = ''
      this.formProps.txtGhiChu = ''
      this.cboNghiepvu.value = '0'
      this.truyen_nhom_neo = 0
      this.nhom_tach = 0
      this.dgrList.data = []
    },
    ChuanHoa_XML (str) {
      let result = ''
      result = str.replace('&', '&amp;')
      result = result.replace('<', '&lt;')
      result = result.Replace('>', '&gt;')
      result = result.Replace("'", '&apos;')
      return result
    },
    StringToString (str) {
      if (str === undefined || str === null) {
        return
      }
      let letter
      let text11
      let text22 = ''

      text11 = str
      for (let i = 0; i < text11.length; i++) {
        letter = text11.substr(i, 1)
        if (letter === '\r') {
          text22 = text22 + '\r'
        } else if (letter === '\n') {
          text22 = text22 + '\n'
        } else {
          text22 = (text22 + letter)
        }
      }
      return text22
    }
  }
}
</script>

<style>
.modal-body .page-content {
  position: inherit !important;
}
.text-view {
  background-color: white !important;
}

.disabled {
  pointer-events: none;
  opacity: 0.7;
}

.disabled_color {
  color: gray !important;
}
</style>
