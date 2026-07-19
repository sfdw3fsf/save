<template src="./index.template.html">
</template>
<script>
import moment from 'moment'
import {DataManager, ODataAdaptor, Predicate, Query} from '@syncfusion/ej2-data'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
import KDatePicker from '@/components/kylq_components/KDatePicker.vue'
import {TRANGBI} from './Enums'
import XLSX from 'xlsx'
import ComboboxGridEdit from './components/ComboboxGridEdit'
import Vue from 'vue'
import {KieuTbi} from './KieuTbi'

export default {
  name: 'CapVatTu_PTM',
  props: {
    inputData: {
      type: Object,
      default: function () {
        return null
      }
    }
  },
  components: {
    DataManager,
    ODataAdaptor,
    Predicate,
    Query,
    KDatePicker,
    ComboboxGridEdit
  },
  data () {
    return {
      actions: [
        {
          id: 'tsbtnNhapMoi',
          name: 'Nhập mới',
          active: true,
          icon_class: 'one-file-plus',
          visible: true
        },
        {
          id: 'tsbtnGhiLai',
          name: 'Ghi lại',
          active: true,
          icon_class: 'one-save',
          visible: true
        },
        {
          id: 'tsbtnHuyBo',
          name: 'Hủy',
          active: true,
          icon_class: 'nc-icon-glyph ui-1_circle-remove',
          visible: true
        },
        {
          id: 'tsbtnXoa',
          name: 'Xóa',
          active: true,
          icon_class: 'one-trash',
          visible: true
        },
        {
          id: 'tsbtnExcel',
          name: 'Xuất Excel',
          active: true,
          icon_class: 'one-xlsx-import',
          visible: true
        }
      ],
      cboKieuTbi: {
        list: [],
        value: 0,
        disabled: false
      },
      cboKieuTrangBi: {
        list: [],
        value: 0,
        disabled: false
      },
      cboTinhTrang: {
        list: [],
        value: 0,
        disabled: false
      },
      cboNhanVien: {
        list: [],
        value: 0,
        disabled: false
      },
      cboKho: {
        list: [],
        value: 0,
        disabled: false
      },
      cboVatTu: {
        list: [],
        value: 0,
        disabled: false
      },
      formThongTinThietBi: {
        dtpNgayCap: moment(new Date()).format('DD/MM/YYYY HH:mm A'),
        soluong: 0,
        txtGhiChu: ''
      },
      gridviewVatTu: {
        list: [],
        headers: [
          {
            fieldName: 'MA_TBI',
            headerText: 'Mã T.Bị',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'TEN_VT',
            headerText: 'Thiết bị',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'SERIAL',
            headerText: 'Seri',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'SOLUONG',
            headerText: 'Số lượng',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'KIEU_TBI',
            headerText: 'Kiểu thiết bị',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'MOTA',
            headerText: 'Mô tả',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'MAC',
            headerText: 'MAC',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'TT_TBI',
            headerText: 'Tình trạng',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'TRANGBI',
            headerText: 'Trang bị',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'NGAY_CAP',
            headerText: 'Ngày cấp',
            allowFiltering: true,
            allowSorting: false,
            format: {type: 'date', format: 'dd/MM/yyyy'},
            type: 'date'
          },
          {
            fieldName: 'NGUOI_CN',
            headerText: 'Người CN',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'TEN_DV',
            headerText: 'Đơn vị',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'GHICHU',
            headerText: 'Ghi chú',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'KHOCAP',
            headerText: 'Kho cấp',
            allowFiltering: true,
            allowSorting: false
          }
        ],
        selected: 0,
        checked: [],
        selectItem: null
      },
      grvDS_VT_Giao: {
        list: [],
        headers: [
          {
            fieldName: 'btnThuhoi',
            headerText: 'Thu hồi',
            allowFiltering: true,
            allowSorting: false,
            visible: true,
            textAlign: 'center',
            width: 90,
            template: function () {
              return {
                template: Vue.component('ThuHoiTemplate', {
                  template: `<a class="btn btn-thuhoivt lh14" @click="onThuHoiVatTu"
                                style="background-color: #3684e0 !important; padding: 5px 15px; color: red">
                  <span class="icon nc-icon-glyph ui-1_simple-remove"></span></a>`,
                  data () {
                    return {data: {}}
                  },
                  computed: {
                    parent () {
                      return this.$parent.$parent.$parent.$parent.$parent
                    }
                  },
                  methods: {
                    onThuHoiVatTu () {
                      this.parent.btnThuHoi_ButtonClick(this.data)
                    }
                  }
                })
              }
            }
          },
          {
            fieldName: 'MA_TBI',
            headerText: 'Mã T.Bị',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'TEN_VT_NEW',
            headerText: 'Thiết bị',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'SERIAL',
            headerText: 'Seri',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'SOLUONG',
            headerText: 'Số lượng',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'KIEU_TBI',
            headerText: 'Kiểu tbị',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'TINHTRANG_TBI',
            headerText: 'Tình trạng',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'TENTRANGBI',
            headerText: 'Trang bị',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'NGAY_SD',
            headerText: 'Ngày cấp',
            allowFiltering: true,
            allowSorting: false,
            format: {type: 'date', format: 'dd/MM/yyyy'},
            type: 'date',
            visible: true
          }
        ],
        selected: 0,
        selectItem: null,
        checked: [],
        showColumnCheckbox: false
      },
      disableBox: {
        dtpNgayCap: false,
        txtSoLuong: false
      },
      kt_id: null,
      phieucc_id: null,
      chitietcc_id: null,
      loaitbi_ecms_id: null,
      ptm_id: null,
      vdonvi_nhan_id: 0,
      vngay_ht: '',
      phvt_id: null,

      vsdvt_id: null,
      loaitbi_id: 0,
      kieu_f: 0,
      donvi_id: 0,
      xacnhan: false,
      tagfrm: '0',
      matinh_hientai: this.$root.token.getMaTinh(),
      canhbao_hoac_loi: false,
      sl_goc: 0,
      check_serial: '0',
      animationSettings: {effect: 'Zoom'},
      position: {X: 'center', Y: 'top'},
      DmMucDichCap: {
        data: [],
        disabled: false,
        textField: 'MUCDICH',
        selectDonViQL: '',
        valueField: 'MUCDICH_ID',
        panelDataHeight: 'auto',
        headers: [
          {
            fieldName: 'MUCDICH',
            headerText: 'Chọn mục đích cấp',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'NHOM_MD',
            headerText: 'Nhóm',
            allowFiltering: true,
            allowSorting: false,
            isGroupedColumn: true
          }
        ],
        selectMucDichCap: null
      },
      DmLyDoCap: {
        data: [],
        disable: false,
        textField: 'MUCDICH',
        valueField: 'MUCDICH_ID',
        panelDataHeight: 'auto',
        headers: [
          {
            fieldName: 'MUCDICH',
            headerText: 'Chọn lý do cấp',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'NHOM_MD',
            headerText: 'Nhóm',
            allowFiltering: true,
            allowSorting: false,
            isGroupedColumn: true
          }
        ],
        selectLyDoCap: null
      },
      DmVTGiao: {
        panelDataHeight: 'auto',
        selectVTGiao: 0,
        headers: [
          {
            fieldName: 'THIETBI_ID',
            headerText: '',
            textAlign: 'center',
            template: function () {
              return {
                template: Vue.component('TemplateEdit', {
                  template: `<a class="btn btn-main pad4 lh14"
                                style="background-color: #0176FF !important; width: 100%; color: white !important"
                                @click="onClickAddSerial"><span
                    class="-ap icon-add"></span></a>`,
                  data () {
                    return {data: {}}
                  },
                  computed: {
                    parent () {
                      return this.$parent.$parent.$parent.$parent.$parent.$parent
                    }
                  },
                  methods: {
                    onClickAddSerial () {
                      this.parent.CapVatTu()
                    }
                  }
                })
              }
            },
            allowFiltering: true,
            allowSorting: false,
            width: 50,
            editTemplate: function () {
              return {
                template: Vue.component('TemplateEdit', {
                  template: `<a class="btn btn-main pad4 lh14"
                                style="background-color: #0176FF !important; width: 100%; color: white !important"
                                @click="onClickAddSerial"><span
                    class="-ap icon-add"></span></a>`,
                  data () {
                    return {data: {}}
                  },
                  computed: {
                    parent () {
                      return this.$parent.$parent.$parent.$parent.$parent.$parent
                    }
                  },
                  methods: {
                    onClickAddSerial () {
                      this.parent.CapVatTu()
                    }
                  }
                })
              }
            }
          },
          {
            fieldName: 'TEN_VT',
            headerText: 'Tên vật tư',
            allowFiltering: true,
            allowSorting: false,
            allowEditing: false
          },
          {
            fieldName: 'MA_TBI',
            headerText: 'Mã T.Bị',
            allowFiltering: true,
            allowSorting: false,
            allowEditing: false
          },
          {
            fieldName: 'SL_TON',
            headerText: 'Số lượng',
            allowFiltering: true,
            allowSorting: false,
            allowEditing: false
          },
          {
            fieldName: 'LUONGCAP',
            headerText: 'SL cấp',
            allowFiltering: true,
            allowSorting: true,
            allowEditing: true
          },
          {
            fieldName: 'SERIAL',
            headerText: 'Serial',
            allowFiltering: true,
            allowSorting: false,
            allowEditing: false
          },
          {
            fieldName: 'SERIAL_GP',
            headerText: 'Serial GP',
            allowFiltering: true,
            allowSorting: false,
            allowEditing: false
          },
          {
            fieldName: 'MAC',
            headerText: 'MAC',
            allowFiltering: true,
            allowSorting: false,
            allowEditing: false
          },
          {
            fieldName: 'LOHANG',
            headerText: 'Lô hàng',
            allowFiltering: true,
            allowSorting: false,
            allowEditing: false
          },
          {
            fieldName: 'SL_GOC',
            headerText: 'Lô hàng',
            allowFiltering: true,
            allowSorting: false,
            allowEditing: false
          },
          {
            fieldName: 'DONGIA',
            headerText: 'Đơn giá',
            allowFiltering: true,
            allowSorting: false,
            allowEditing: false,
            type: 'Number',
            format: 'N0'
          },
          {
            fieldName: 'TIEN',
            headerText: 'Thành tiền',
            allowFiltering: true,
            allowSorting: false,
            allowEditing: false,
            type: 'Number',
            format: 'N0'
          },
          {
            fieldName: 'VAT',
            headerText: 'VAT',
            allowFiltering: true,
            allowSorting: false,
            allowEditing: false
          },
          {
            fieldName: 'NGAY_GIAO',
            headerText: 'Ngày giao',
            allowFiltering: true,
            allowSorting: false,
            allowEditing: false
          },
          {
            fieldName: 'DVI_TINH',
            headerText: 'ĐVT',
            allowFiltering: true,
            allowSorting: false,
            allowEditing: false
          },
          {
            fieldName: 'TINHTRANG_TBI',
            headerText: 'Tình trạng T.Bị',
            allowFiltering: true,
            allowSorting: false,
            allowEditing: false
          },
          {
            fieldName: 'NGAY_CT',
            headerText: 'Ngày CT',
            allowFiltering: true,
            allowSorting: false,
            allowEditing: false
          }
        ],
        editSettings: {
          allowEditing: true,
          allowAdding: false,
          allowDeleting: false,
          mode: 'Normal'
        },
        data: []
      },
      vpass_alt: '',
      vuser_alt: '',
      vpass_xtest: '',
      vuser_xtest: '',
      expanded: false,
      editSerial: null,
      nhanvienId: this.$root.token.getNhanVienID(),
      donviId: this.$root.token.getDonViID()
    }
  },
  watch: {
    'DmVTGiao.data': function (newValue) {
      console.log('🚀 ~ file: index.vue ~ line 588 ~ newValue', newValue)
    },
    'cboKieuTbi.value': function (newValue) {
      this.cboKieuTbi_EditValueChanged(newValue)
    },
    'cboNhanVien.value': function (newValue) {
      this.cboNhanVien_EditValueChanged(newValue)
    },
    'cboKho.value': function (newValue) {
      this.$refs.cboSerial.setDisable(false)
      this.Load_DS_VTTU()
    },
    'DmLyDoCap.selectLyDoCap': function (newValue) {
      if (this.cboKieuTbi.value.toString() === '3') {
        this.refreshData()
        if (newValue.toString() === '277') {
          this.setActiveActions(277)
        } else {
          this.cboKieuTbi_EditValueChanged(3)
        }
      } else {
        this.cboKieuTbi_EditValueChanged(this.cboKieuTbi.value)
      }
    }
  },
  methods: {
    cboNhanVien_EditValueChanged (newValue) {
      this.LAY_DS_KHO_THEO_NV(newValue)
    },
    cboKieuTbi_EditValueChanged (newValue) {
      // mới
      if (newValue.toString() === '1') {
        this.setVisibleButton('tsbtnGhiLai', false)
        this.setVisibleButton('tsbtnHuyBo', false)
        this.setActiveButton('tsbtnXoa', true)
        this.$refs.cboSerial.setDisable(false)
        this.disableBox.dtpNgayCap = false
        this.setActiveButton('tsbtnHuyBo', false)
        this.cboVatTu.disabled = true
        this.disableBox.txtSoLuong = true
        this.setVisibleButton('tsbtnNhapMoi', false)
        this.disableBox.dtpNgayCap = false
        this.SP_LAY_DS_TINHTRANG_TBI()
      } else {
        this.cboVatTu.disabled = false
        this.disableBox.dtpNgayCap = this.kieu_f !== 1
        this.dtpNgayCap = moment(new Date()).format('MM/DD/YYYY HH:mm A')
        this.disableBox.txtSoLuong = false
        this.$refs.cboSerial.setDisable(true)
        this.setVisibleButton('tsbtnNhapMoi', true)
        this.setVisibleButton('tsbtnGhiLai', true)
        this.setVisibleButton('tsbtnHuyBo', true)
        this.SP_LAY_DS_TINHTRANG_TBI_TH()
        this.refreshData()
        this.setActiveActions(1)
      }
    },
    async handleShowModal () {
      try {
        this.loading(true)
        this.LoadDataFromProps()
        await this.LoadCbo()
        await this.LoadGridVT()
        await this.LoadGrid_VatTuSD()
        this.ThietLap()
        await this.SP_LAY_DS_THAMSO_MD('0')
      } catch (error) {
        console.log(error)
      } finally {
        this.loading(false)
      }
    },
    hideModal () {
      this.$refs.popupVatTuPTM.hide()
    },
    LoadDataFromProps () {
      console.log('🚀 ~ file: index.vue ~ line 640 ~ LoadDataFromProps ~ this.inputData', this.inputData)
      if (this.inputData != null) {
        this.phieucc_id = this.inputData.phieucc_id ? this.inputData.phieucc_id : 0
        this.chitietcc_id = this.inputData.chitietcc_id ? this.inputData.chitietcc_id : 0
        this.ptm_id = this.inputData.ptm_id ? this.inputData.ptm_id : 0
        this.loaitbi_ecms_id = this.inputData.loaitbi_ecms_id ? this.inputData.loaitbi_ecms_id : 0
        this.kieu_f = this.inputData.kieu_f ? this.inputData.kieu_f : 0
        this.tagfrm = this.inputData.tagfrm ? this.inputData.tagfrm : '0'
      }
    },
    CapVatTu () {
      try {
        if (this.editSerial != null) {
          let newData = this.editSerial.data
          console.log('🚀 ~ file: index.vue ~ line 681 ~ CapVatTu ~ this.editSerial.data', this.editSerial.data)
          let serial = newData.SERIAL
          let matbi = newData.MA_TBI

          if (serial === null && matbi !== null) {
            this.Insert_NotSerial(newData, matbi)
          } else if (serial === null && matbi === null) {
            this.Insert_NotSerial(newData, '')
          } else {
            this.Insert_SErial_V2(newData, serial.toString().trim())
          }
          if (this.tagfrm === '2') {
            this.CAPNHAT_SUDUNG_VATTU(this.ptm_id, this.loaitbi_ecms_id, this.phieucc_id)
          }
        }
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Xảy ra lỗi khi thực hiện cấp vật tư')
      }
    },
    receiverInsertSerialData (arg) {
      if (arg != null) {
        this.editSerial = arg
      }
    },
    async Insert_NotSerial (newData, matbi) {
      try {
        let vNgayCap = this.getDate(this.formThongTinThietBi.dtpNgayCap).toDate()
        let vKieuTbi = 1
        let vTrangBi = TRANGBI.VIENTHONG_TRANGBI
        let vTuVTId = Number.parseInt(newData.TUVT_ID)
        let vThietBiId = Number.parseInt(newData.THIETBI_ID)
        let vSoLuong = Number.parseInt(newData.LUONGCAP)
        if (vNgayCap > new Date()) {
          this.$root.$toast.warning('Ngày cấp ko được lớn hơn ngày hiện tại')
          return
        }
        if (vSoLuong === 0) {
          this.$root.$toast.warning('Bạn chưa điền số lượng cần cấp.')
          return
        }
        let vTon = Number.parseInt(newData.SL_TON)
        if (vSoLuong > vTon) {
          this.$root.$toast.warning('Số lượng cấp vượt quá số lượng thực của thiết bị.')
          return
        }
        this.$root.showLoading(true)
        let KiemTraDay = await this.KiemTra_Day_CTBD(this.phieucc_id, this.chitietcc_id, vTuVTId)
        if (KiemTraDay.length > 0) {
          this.$root.$toast.warning(newData.TEN_VT + ' đã được cấp bạn ko thể cấp tiếp được')
          this.$root.showLoading(false)
          return
        }
        let vPhieuVTId = await this.POST_GET_KEYS('phieuvt_id')
        console.log('🚀 ~ file: index.vue ~ line 726 ~ Insert_NotSerial ~ vPhieuVTId', vPhieuVTId)
        let mathietbi = ''
        if (matbi !== '') {
          mathietbi = matbi
        }
        let vLyDoCap = this.DmLyDoCap.selectLyDoCap
        let tinhTrangTbi = this.cboTinhTrang.value

        let ktKq = await this.kiemtra_vtcapra_ptm(this.phieucc_id, this.chitietcc_id, vTuVTId)
        if (ktKq.length > 0) {
          if (ktKq[0].KT_KQ.toString() !== '1') {
            this.$root.$toast.warning(ktKq[0].KT_KQ.toString())
            this.$root.showLoading(false)
            return
          }
        }
        let vnhnIdTemp = this.nhanvienId
        if (this.cboNhanVien.value.toString() !== vnhnIdTemp.toString()) {
          vnhnIdTemp = Number.parseInt(this.cboNhanVien.value)
        }
        var ld = Number.parseInt(this.DmMucDichCap.selectMucDichCap)
        const request = {
          VSDVT_ID: '0',
          VPHIEUVT_ID: vPhieuVTId.toString(),
          VPHIEUCC_ID: this.phieucc_id.toString(),
          VNGAY_CAP: this.getDate(this.formThongTinThietBi.dtpNgayCap).format('DD/MM/YYYY HH:mm:ss'),
          VKIEUTBI_ID: vKieuTbi.toString(),
          VTRANGBI_ID: vTrangBi.toString(),
          VTUVT_ID: vTuVTId.toString(),
          VTHIETBI_ID: vThietBiId.toString(),
          VSOLUONG: vSoLuong.toString(),
          VSERIAL: '',
          VMA_TBI: mathietbi,
          VNHANVIEN_ID: vnhnIdTemp.toString(),
          VLYDOCAP_ID: vLyDoCap.toString(),
          VLYDOCAPTO_ID: ld.toString(),
          VCHITIETCC_ID: this.chitietcc_id.toString(),
          VPTM_ID: this.ptm_id.toString(),
          VLOAITBI_ECMS_ID: this.loaitbi_ecms_id.toString(),
          VTTPK: '1',
          VTINHTRANGTBI_ID: tinhTrangTbi.toString(),
          VGHICHU: this.formThongTinThietBi.txtGhiChu
        }
        console.log('🚀 ~ file: index.vue ~ line 774 ~ Insert_NotSerial ~ request', request)
        let kq = await this.INSERT_PHIEU_VATTU_PTM(request)

        if (kq.length > 0) {
          if (kq[0].RESULT !== '1') {
            this.$root.$toast.warning(kq[0].RESULT)
          }
        }
        this.$refs.cboSerial.setInputText('')
        await this.LoadGridVT()
        await this.Load_DS_VTTU()
        if (this.cboKho.value === null) {
          this.$refs.cboSerial.setDisable(true)
          this.DmVTGiao.data = []
        } else {
          this.$refs.cboSerial.setDisable(false)
          await this.Load_DS_VTTU()
        }
        this.$root.showLoading(false)
      } catch (error) {
        console.error(error)
        this.$root.showLoading(false)
        this.$root.$toast.warning('Xảy ra lỗi khi thực hiện cấp vật tư')
      }
    },
    async LoadCbo () {
      // CBO Kieu TBI
      await this.CSS_KIEU_TBI()
      // Cbo Ly do
      let dsLydo = await this.SP_LAY_DS_MUCDICH_LYDO('23')
      if (dsLydo.length > 0) {
        this.DmLyDoCap.data = dsLydo
        this.DmLyDoCap.selectLyDoCap = dsLydo[0].MUCDICH_ID
      }
      // Cbo Muc dich cap
      let dsMDCap = await this.SP_LAY_DS_MUCDICH_LYDO('22')
      if (dsMDCap.length > 0) {
        this.DmMucDichCap.data = dsMDCap
        this.DmMucDichCap.selectMucDichCap = dsMDCap[0].MUCDICH_ID
      }

      this.cboNhanVien.list = []
      this.cboNhanVien.value = 0
      let dsNV = await this.LAY_DS_NV_GIAO_NHAN()
      if (dsNV.length > 0) {
        this.cboNhanVien.list = dsNV
        let nhanvien = dsNV.find(item => item.NHANVIEN_ID.toString() === this.nhanvienId.toString())
        if (nhanvien != null) {
          this.cboNhanVien.value = parseInt(nhanvien.NHANVIEN_ID)
        } else {
          this.cboNhanVien.value = parseInt(dsNV[0].NHANVIEN_ID)
        }
      }

      // Nạp combobox vật tư
      let dtVatTu = await this.SP_GETDANHMUCVT_DVT_ALL()
      dtVatTu = dtVatTu.filter(item => item.VATTU_ID !== null)
      this.cboVatTu.list = dtVatTu
    },
    async LoadGridVT () {
      try {
        let dsLSVT = await this.lay_ds_phieuvt_ptm(this.phieucc_id, this.chitietcc_id, 0, 0)
        console.log('🚀 ~ file: index.vue ~ line 834 ~ LoadGridVT ~ dsLSVT', dsLSVT)
        if (this.kieu_f === 1 && dsLSVT.length > 0) {
          if (this.cboKieuTbi.value.toString() === '1') {
            let q = dsLSVT.filter(item => item.kieutbi_id === '1')
            if (q.length > 0) {
              this.gridviewVatTu.list = q
            }
          } else if (this.cboKieuTbi.value.toString() === '3') {
            let q = dsLSVT.filter(item => item.kieutbi_id === '3')
            if (q.length > 0) {
              this.gridviewVatTu.list = q
            }
          }
        } else {
          this.gridviewVatTu.list = dsLSVT
        }
      } catch (error) {
        console.log(error)
      }
    },
    async lay_ds_phieuvt_ptm (PHIEUCC_ID, CHITIETCC_ID, KIEUTBI, STATUS) {
      let reponse = await this.$root.context.post(
        '/web-cabman/capvattu-ptm/lay_ds_phieuvt_ptm', {PHIEUCC_ID, CHITIETCC_ID, KIEUTBI, STATUS}
      )
      return this.GetData(reponse)
    },
    async LoadGrid_VatTuSD () {
      try {
        this.grvDS_VT_Giao.list = await this.LAY_DS_VATTU_SD_PTM(this.ptm_id, this.loaitbi_ecms_id)
      } catch (error) {
        console.log(error)
        this.grvDS_VT_Giao.list = []
        this.$root.$toast.error('Không load được danh sách vật tư')
      }
    },
    ThietLap () {
      if (this.kieu_f === 1) {
        this.cboKieuTbi.disabled = true
        this.cboNhanVien.disabled = true
        this.cboKho.disabled = true
        this.setVisibleButton('tsbtnXoa', false)
        this.setVisibleButton('tsbtnGhiLai', true)
        this.$refs.cboSerial.setDisable(true)
      } else if (this.kieu_f === 2) {
        this.cboKieuTbi.disabled = false
        this.cboNhanVien.disabled = false
        this.cboKho.disabled = false
        this.setVisibleButton('tsbtnXoa', true)
        this.setVisibleButton('tsbtnGhiLai', false)
        this.$refs.cboSerial.setDisable(false)
      } else if (this.kieu_f === 10) {
        this.cboKieuTbi.value = 3
        this.DmLyDoCap.selectLyDoCap = 277
        this.cboKieuTbi.disabled = true
        this.cboNhanVien.disabled = true
        this.cboKho.disabled = true
        this.cboVatTu.disabled = true
        this.setActiveButton('tsbtnXoa', true)
      }
    },
    async Load_DS_VTTU () {
      let ds = await this.Load_DS_VTTU_PTM()
      if (ds.length > 0) {
        this.DmVTGiao.data = ds
      } else {
        this.DmVTGiao.data = []
      }
    },
    CAPNHAT_NGAY_CAP () {
      this.UPDATE_PVT_KT()
    },
    async CAPNHAT_PHIEUVT_THUHOI () {
      try {
        if (this.KiemTraDuLieu_Th()) {
          let vnhvIdTemp = this.nhanvienId
          if (this.cboNhanVien.value.toString() !== vnhvIdTemp.toString()) {
            vnhvIdTemp = this.cboNhanVien.value
          }
          // Kiểm tra thông tin
          let serial = this.$refs.cboSerial.getInputText().trim()
          let dt = await this.KiemTraPTM_SDVT(serial.toUpperCase())
          let phieuVtId = this.phvt_id
          let kieu = 3
          let tbiId = 0
          let ktSeri = '0'
          let sd = 0
          // Kiểm tra dữ liệu
          let vthietbi_id = 0
          let s_ri = ''
          let vkieu = 3
          if (this.loaitbi_id === 2) {
            if (serial !== '') {
              let dtTbiDaCap = await this.SP_LAY_DS_THIETBI_DACAP_SERIAL(serial.toUpperCase(), 0)
              if (dtTbiDaCap.length > 0 && this.getActiveButton('tsbtnNhapMoi') === false) {
                this.$root.$toast.warning('Serial ' + serial + ' đã được cấp vào  ngày ' +
                    dtTbiDaCap[0].NGAY_CAP + ' bởi user ' +
                    dtTbiDaCap[0].NGUOI_CN + '')
                return
              }
              if (this.getActiveButton('tsbtnNhapMoi') === false) {
                if (dt.length > 0) { // Nếu có
                  let checklogic = await this.FN_KIEMTRA_VT_THUHOI(this.ptm_id, serial.toUpperCase())
                  if (checklogic !== '1') {
                    this.$root.$toast.warning(checklogic)
                    return
                  }
                  if (dt[0].PTM_ID.toString() !== this.ptm_id.toString() || dt[0].LOAITBI_ECMS_ID.toString() !== this.LOAITBI_ECMS_ID.toString()) {
                    if (parseInt(dt[0].PHIEUVT_ID) !== 0) {
                      this.$root.$toast.warning('Serial ' + serial + ' đã được sử dụng bởi PTM ' + dtTbiDaCap[0].TEN_TBI + '. Bạn không có quyền được thu hồi')
                      return
                    }
                    return
                  }
                  let dtTbi = await this.SP_GETTHIETBI(serial.toUpperCase())
                  if (dtTbi.length > 0 && dtTbi[0].SL_TON.toString() === '1' && dtTbi[0].TONKHO.toString() === '1') {
                    this.$root.$toast.warning('Serial này đang ở kho ' + dtTbi[0].T)
                    return
                  }
                  sd = parseInt(dt[0].SDVT_ID)
                  kieu = 1
                }
              } else {
                let checklogic = await this.FN_KIEMTRA_VT_THUHOI(this.ptm_id, serial.toUpperCase())
                if (checklogic !== '1') {
                  this.$root.$toast.warning(checklogic)
                  return
                }
                let dtTbi = await this.SP_GETTHIETBI(serial.toUpperCase())
                if ((dtTbi.length > 0 && dtTbi[0].SL_TON.toString() === '0' && dtTbi[0].TONKHO.toString() === '1') ||
                    (dtTbi.length > 0 && dtTbi[0].TONKHO.toString() === '0')) {
                  // neu ton tai trong thiet bi
                  kieu = 2
                  tbiId = Number.parseInt(dtTbi[0].THIETBI_ID)
                } else if (dtTbi.length > 0 && dtTbi[0].SL_TON.toString() === '1' && dtTbi[0].TONKHO.toString() === '1') {
                  this.$root.$toast.warning('Serial này đang ở kho ' + dtTbi[0].T)
                  return
                } else {
                  kieu = 3
                  let accept = false
                  accept = await this.$confirm('Không tồn tại serial trong hệ thống. Bạn có muốn tiếp tục?', 'Thông báo', {
                    confirmButtonText: 'Có',
                    cancelButtonText: 'Không'
                  })
                  if (!accept) {
                    return
                  }
                }
              }
            } else if (this.vsdvt_id > 0) {
              let dtTbiDaCap = await this.SP_LAY_DS_THIETBI_DACAP_SERIAL(serial.toUpperCase(), this.vsdvt_id)
              if (dtTbiDaCap.length > 0 && this.actions[this.actions.findIndex(item => item.id === 'tsbtnNhapMoi')].active === false) {
                this.$root.$toast.warning('Serial ' + serial + ' đã được cấp vào  ngày ' + dtTbiDaCap[0].NGAY_CAP + ' bởi user ' + dtTbiDaCap[0].NGUOI_CN)
                return
              }
            }
          }

          if (this.loaitbi_id === 3) {
            // Loại thiết bị là 3
            if (this.vsdvt_id > 0) {
              kieu = 1
              let ktsl = await this.SP_GETSL_DD(this.vsdvt_id)
              if (ktsl.length > 0) {
                if (ktsl[0].SUM_SO_LUONG.toString() !== '0') {
                  let slTreo = Number.parseInt(ktsl[0].SUM_SO_LUONG)
                  if (this.sl_goc - slTreo <= this.formThongTinThietBi.soluong || this.sl_goc - slTreo <= 0) {
                    this.$root.$toast.warning('Hiện số lượng cấp thu hồi của thuê chưa QT là ' + slTreo + '/' + this.sl_goc + '. Bạn hãy kiểm tra lại số lượng thu hồi')
                    return
                  }
                }
              }

              if (this.getActiveButton('tsbtnNhapMoi') === false) {
                await this.CAPNHAT_PHIEUVT_THUHOI_API(this.vsdvt_id, 1, 0, this.chitietcc_id, this.phieucc_id,
                  this.ptm_id, this.loaitbi_ecms_id, vnhvIdTemp, this.cboTinhTrang.value, 'CUONG1094', moment(new Date()).format('DD/MM/YYYY HH:mm:ss'),
                  this.cboVatTu.value, tbiId, this.formThongTinThietBi.soluong, this.DmMucDichCap.selectMucDichCap, this.DmLyDoCap.selectLyDoCap, TRANGBI.VIENTHONG_TRANGBI, kieu, this.formThongTinThietBi.txtGhiChu)
                this.$root.$toast.success('Thu hồi thành công')
              } else {
                let accepted1 = false
                accepted1 = await this.$confirm('Bạn có muốn cập nhật dữ liệu không 1?', 'Thông báo', {
                  confirmButtonText: 'Có',
                  cancelButtonText: 'Không'
                })
                if (accepted1) {
                  await this.CAPNHAT_PHIEUVT_THUHOI_API(this.vsdvt_id, 2, phieuVtId, this.chitietcc_id, this.phieucc_id,
                    this.ptm_id, this.loaitbi_ecms_id, vnhvIdTemp, this.cboTinhTrang.value, 'CUONG1094', moment(new Date()).format('DD/MM/YYYY HH:mm:ss'),
                    this.cboVatTu.value, tbiId, this.formThongTinThietBi.soluong, this.DmMucDichCap.selectMucDichCap, this.DmLyDoCap.selectLyDoCap, TRANGBI.VIENTHONG_TRANGBI, kieu, this.formThongTinThietBi.txtGhiChu)
                  this.$root.$toast.success('Cập nhật dữ liệu thành công')
                } else {
                  return
                }
              }
              let vattu = this.gridviewVatTu.list[this.gridviewVatTu.list.findIndex(item => item.PHIEUVT_ID.toString() === phieuVtId.toString())]
              this.LoadDS(vattu)
              return
            }
            kieu = 3
            if (!this.getActiveButton('tsbtnNhapMoi')) {
              await this.CAPNHAT_PHIEUVT_THUHOI_API(this.vsdvt_id, 1, 0, this.chitietcc_id, this.phieucc_id,
                this.ptm_id, this.loaitbi_ecms_id, vnhvIdTemp, this.cboTinhTrang.value, 'CUONG1094', moment(new Date()).format('DD/MM/YYYY HH:mm:ss'),
                this.cboVatTu.value, tbiId, this.formThongTinThietBi.soluong, this.DmMucDichCap.selectMucDichCap, this.DmLyDoCap.selectLyDoCap, TRANGBI.VIENTHONG_TRANGBI, kieu, this.formThongTinThietBi.txtGhiChu)
              this.$root.$toast.success('Cập nhật dữ liệu thành công')
            } else {
              let confirm = await this.$confirm('Bạn có muốn cập nhật dữ liệu không?', 'Thông báo', {
                confirmButtonText: 'Có',
                cancelButtonText: 'Không'
              })
              if (confirm) {
                this.CAPNHAT_PHIEUVT_THUHOI_API(this.vsdvt_id, 2, phieuVtId, this.chitietcc_id, this.phieucc_id,
                  this.ptm_id, this.loaitbi_ecms_id, vnhvIdTemp, this.cboTinhTrang.value, 'CUONG1094', moment(new Date()).format('DD/MM/YYYY HH:mm:ss'),
                  this.cboVatTu.value, tbiId, this.formThongTinThietBi.soluong, this.DmMucDichCap.selectMucDichCap, this.DmLyDoCap.selectLyDoCap, TRANGBI.VIENTHONG_TRANGBI, kieu, this.formThongTinThietBi.txtGhiChu)
                this.$root.$toast.success('Cập nhật dữ liệu thành công')
              }
            }
            let vattu = this.gridviewVatTu.list[this.gridviewVatTu.list.findIndex(item => item.PHIEUVT_ID.toString() === phieuVtId.toString())]
            this.LoadDS(vattu)
            return
          }
          if (this.loaitbi_id !== 3 && this.loaitbi_id !== 2) {
            // Loại thiết thường
            if (this.vsdvt_id > 0) {
              kieu = 1
              let ktsl = await this.SP_GETSL_DD(this.vsdvt_id)
              if (ktsl.length > 0) {
                if (ktsl[0].SUM_SO_LUONG.toString() !== '0') {
                  let sltreo = Number.parseInt(ktsl.SUM_SO_LUONG)
                  if (this.sl_goc - sltreo <= this.formThongTinThietBi.soluong || this.sl_goc - sltreo <= 0) {
                    this.$root.$toast.warning('Hiện số lượng cấp thu hồi của thuê chưa QT là ' + sltreo + '/' + this.sl_goc + '. Bạn hãy kiểm tra lại số lượng thu hồi')
                    return
                  }
                }
              }
              if (!this.getActiveButton('tsbtnNhapMoi')) {
                await this.CAPNHAT_PHIEUVT_THUHOI_API(this.vsdvt_id, 1, 0, this.chitietcc_id, this.phieucc_id, this.ptm_id,
                  this.loaitbi_ecms_id, vnhvIdTemp, this.cboTinhTrang.value, 'DAYTB', moment(new Date()).format('DD/MM/YYYY HH:mm:ss'),
                  this.cboVatTu.value, tbiId, this.formThongTinThietBi.soluong, this.DmMucDichCap.selectMucDichCap, this.DmLyDoCap.selectLyDoCap, TRANGBI.VIENTHONG_TRANGBI, kieu, this.formThongTinThietBi.txtGhiChu)
                this.$root.$toast.success('Thu hồi thành công')
              } else {
                let accepted = false
                accepted = await this.$confirm('Bạn có muốn cập nhật dữ liệu không?', 'Thông báo', {
                  confirmButtonText: 'Có',
                  cancelButtonText: 'Không'
                })
                if (accepted) {
                  await this.CAPNHAT_PHIEUVT_THUHOI_API(this.vsdvt_id, 2, phieuVtId, this.chitietcc_id, this.phieucc_id,
                    this.ptm_id, this.loaitbi_ecms_id, vnhvIdTemp, this.cboTinhTrang.value, 'DAYTB', moment(new Date()).format('DD/MM/YYYY HH:mm:ss'),
                    this.cboVatTu.value, tbiId, this.formThongTinThietBi.soluong, this.DmMucDichCap.selectMucDichCap, this.DmLyDoCap.selectLyDoCap, TRANGBI.VIENTHONG_TRANGBI, kieu, this.formThongTinThietBi.txtGhiChu)
                  this.$root.$toast.success('Cập nhật dữ liệu thành công')
                }
              }
            } else {
              kieu = 1
              if (!this.getActiveButton('tsbtnNhapMoi')) {
                await this.CAPNHAT_PHIEUVT_THUHOI_API(this.vsdvt_id, 1, 0, this.chitietcc_id, this.phieucc_id,
                  this.ptm_id, this.loaitbi_ecms_id, vnhvIdTemp, this.cboTinhTrang.value, 'DAYTB', moment(new Date()).format('DD/MM/YYYY HH:mm:ss'),
                  this.cboVatTu.value, tbiId, this.formThongTinThietBi.soluong, this.DmMucDichCap.selectMucDichCap, this.DmLyDoCap.selectLyDoCap, TRANGBI.VIENTHONG_TRANGBI, kieu, this.formThongTinThietBi.txtGhiChu)
                this.$root.$toast.success('Thu hồi thành công')
              } else {
                let accepted = false
                accepted = await this.$confirm('Bạn có muốn cập nhật dữ liệu không?', 'Thông báo', {
                  confirmButtonText: 'Có',
                  cancelButtonText: 'Không'
                })
                console.log('🚀 ~ file: index.vue ~ line 1077 ~ CAPNHAT_PHIEUVT_THUHOI ~ accepted', accepted)
                if (accepted) {
                  await this.CAPNHAT_PHIEUVT_THUHOI_API(this.vsdvt_id, 2, phieuVtId, this.chitietcc_id, this.phieucc_id,
                    this.ptm_id, this.loaitbi_ecms_id, vnhvIdTemp, this.cboTinhTrang.value,
                    'DAYTB', moment(new Date()).format('DD/MM/YYYY HH:mm:ss'),
                    this.cboVatTu.value, tbiId, this.formThongTinThietBi.soluong, this.DmMucDichCap.selectMucDichCap, this.DmLyDoCap.selectLyDoCap, TRANGBI.VIENTHONG_TRANGBI, kieu, this.formThongTinThietBi.txtGhiChu)
                  this.$root.$toast.success('Cập nhật dữ liệu thành công')
                } else {
                  return
                }
              }
              let vattu = this.gridviewVatTu.list[this.gridviewVatTu.list.findIndex(item => item.PHIEUVT_ID.toString() === phieuVtId.toString())]
              this.LoadDS(vattu)
              return
            }
          }
          if (this.getActiveButton('tsbtnNhapMoi')) {
            if (phieuVtId === 0) {
              this.$root.$toast.warning('Chưa có thông tin về phiếu vật tư!')
              return
            }
          }
          await this.$confirm('Bạn có muốn cập nhật dữ liệu không 5?', 'Thông báo', {
            confirmButtonText: 'Có',
            cancelButtonText: 'Không'
          })
          let seriPop = ''
          if (serial !== '') {
            seriPop = serial
          }
          if (!this.getActiveButton('tsbtnNhapMoi')) {
            await this.CAPNHAT_PHIEUVT_THUHOI_API(this.vsdvt_id, 1, 0, this.chitietcc_id, this.phieucc_id,
              this.ptm_id, this.loaitbi_ecms_id, vnhvIdTemp, this.cboTinhTrang.value, seriPop, moment(new Date()).format('DD/MM/YYYY HH:mm:ss'),
              this.cboVatTu.value, tbiId, this.formThongTinThietBi.soluong, this.DmMucDichCap.selectMucDichCap, this.DmLyDoCap.selectLyDoCap, this.cboKieuTbi.value, kieu, this.formThongTinThietBi.txtGhiChu)
            this.$root.$toast.success('Thu hồi thành công')
          } else {
            await this.CAPNHAT_PHIEUVT_THUHOI_API(this.vsdvt_id, 2, phieuVtId, this.chitietcc_id, this.phieucc_id,
              this.ptm_id, this.loaitbi_ecms_id, vnhvIdTemp, this.cboTinhTrang.value, seriPop, moment(new Date()).format('DD/MM/YYYY HH:mm:ss'),
              this.cboVatTu.value, tbiId, this.formThongTinThietBi.soluong, this.DmMucDichCap.selectMucDichCap, this.DmLyDoCap.selectLyDoCap, this.cboKieuTbi.value, kieu, this.formThongTinThietBi.txtGhiChu)
            this.$root.$toast.success('Cập nhật dữ liệu thành công')
          }
          let vattu = this.gridviewVatTu.list[this.gridviewVatTu.list.findIndex(item => item.PHIEUVT_ID.toString() === phieuVtId.toString())]
          this.LoadDS(vattu)
          return
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 1157 ~ CAPNHAT_PHIEUVT_THUHOI ~ error', JSON.stringify(error))
        // this.$root.$toast.error(JSON.stringify(error))
      }
    },
    LoadDS (currentVatTu) {
      this.LoadGridVT()
      this.LoadGrid_VatTuSD()
      if (currentVatTu != null) {
        this.$refs.gridDanhSachVatTu.setCurrentSelectedRow(currentVatTu)
      }
    },
    KiemTraDuLieu_Th () {
      this.loaitbi_id = 0
      if (this.DmLyDoCap.selectLyDoCap === 0) {
        this.$root.$toast.warning('Bạn phải chọn lý do')
        return false
      }
      if (this.cboVatTu.value === 0) {
        this.$root.$toast.warning('Bạn phải chọn vật tư')
        return false
      }

      if (this.formThongTinThietBi.soluong === 0) {
        this.$root.$toast.warning('Bạn phải nhập số lượng')
        return false
      }

      if (this.cboTinhTrang.value === 0) {
        this.$root.$toast.warning('Bạn chưa chọn tình trạng thiết bị')
        return false
      }

      let itemVattu = this.cboVatTu.list.find(item => item.VATTU_ID.toString() === this.cboVatTu.value.toString())
      let serialText = this.$refs.cboSerial.getInputText()
      if (itemVattu != null) {
        if (itemVattu.LOAITBI_ID.toString() === '2') {
          this.loaitbi_id = 2
          if (serialText === '') {
            this.$root.$toast.warning('Loại vật tư này thuộc loại phải có seri. Ban hãy nhập serial cho thiết bị')
            this.$refs.cboSerial.$refs.inputText.focus()
            return false
          }
          if (serialText !== '') {
            if (this.formThongTinThietBi.soluong !== 1) {
              this.$root.$toast.warning('Vật tư này có seri, số lượng phải nhập = 1')
              this.$refs.numberSoluong.focus()
            }

            let reg = /[A-Z][0-9]/
            if (this.check_serial !== '0' && !reg.test(serialText)) {
              this.$root.$toast.warning('Serial có ký tự không hợp lệ. Các ký tự cho phép [A-Z,0-9]')
              return false
            }
          }
        } else if (itemVattu.LOAITBI_ID.toString().trim() === '3') {
          this.loaitbi_id = 3
          return true
        } else {
          if (serialText.trim() !== '') {
            this.$root.$toast.warning('Loại vật tư này thuộc loại phải không có seri. Ban hãy xóa dữ liệu serial')
            this.$refs.cboSerial.$refs.inputText.focus()
            return false
          }
        }
      }
      return true
    },
    async KiemTra_Day_CTBD (PHIEUCC_ID, CHITIETCC_ID, TUVT_ID) {
      let result = await this.$root.context.post(
        '/web-cabman/capvattu-ptm/kiemtra_day_ctbd', {PHIEUCC_ID, CHITIETCC_ID, TUVT_ID}
      )
      return this.GetData(result)
    },
    async CAPNHAT_SUDUNG_VATTU (PTM_ID, LOAITBI_ECMS_ID, PHIEUCC_ID) {
      await this.$root.context.post(
        '/web-cabman/capvattu-ptm/capnhat_sudung_vattu_ptm', { PTM_ID, LOAITBI_ECMS_ID, PHIEUCC_ID }
      )
    },
    async UPDATE_PVT_KT () {
      try {
        let request = {
          'PHIEUVT_ID': this.phvt_id,
          'NGAY_CAP': this.formThongTinThietBi.dtpNgayCap
        }
        let data = this.GetData(await this.$root.context.post('/web-cabman/capvattu-ptm/update_pvt_kt', request))
        if (data.length > 0) {
          if (data[0].RESULT === 'OK') {
            this.$root.$toast.success('Cập nhật thông tin vật tư thành công')
            this.xacnhan = true
            this.LoadGridVT()
          } else {
            this.$root.$toast.warning(data[0].RESULT)
          }
        } else {
          this.$root.$toast.warning('Có lỗi khi cập nhật ngày cấp cho vật tư')
        }
      } catch (error) {
        this.$root.$toast.warning('Có lỗi khi cập nhật ngày cấp cho vật tư')
      }
    },
    async KiemTraPTM_SDVT (VSERIAL) {
      return this.GetData(await this.$root.context.post('/web-cabman/capvattu-ptm/kiemtraptm_sdvt', {VSERIAL}))
    },
    async SP_LAY_DS_THIETBI_DACAP_SERIAL (VSERIAL, VSDVT_ID) {
      return this.GetData(await this.$root.context.post('/web-cabman/capvattu-ptm/getthietbi_dacap'), {VSERIAL, VSDVT_ID})
    },
    async FN_KIEMTRA_VT_THUHOI (VPTM_ID, VSERIAL) {
      let data = this.GetData(await this.$root.context.post('/web-cabman/capvattu-ptm/kiemtra_vt_thuhoi_ptm'), {VPTM_ID, VSERIAL})
      if (data.length > 0) {
        return data[0].RESULT
      } else return null
    },
    async SP_GETTHIETBI (VSERIAL) {
      return this.GetData(await this.$root.context.post('/web-cabman/capvattu-ptm/getthietbi'), {VSERIAL})
    },
    async SP_GETSL_DD (SDVT_ID) {
      return this.GetData(await this.$root.context.post('/web-cabman/capvattu-ptm/getsl_dd', {SDVT_ID}))
    },
    async CAPNHAT_PHIEUVT_THUHOI_API (VSDVT_ID, VINSERT, VPHIEUVT_ID, VCHITIETCC_ID, VPHIEUCC_ID, VPTM_ID, VLOAITBI_ECMS_ID, VNHANVIEN_ID, VTINHTRANGTBI_ID, VSERIAL, VNGAY_TH, VVATTU_ID, VTHIETBI_ID, VSOLUONG, VLYDOCAP_ID, VLYDOCAPTBI_ID, VTRANGBI_ID, VKIEU, VGHICHU) {
      const request = {
        VSDVT_ID,
        VINSERT,
        VPHIEUVT_ID,
        VCHITIETCC_ID,
        VPHIEUCC_ID,
        VPTM_ID,
        VLOAITBI_ECMS_ID,
        VNHANVIEN_ID: parseInt(VNHANVIEN_ID),
        VTINHTRANGTBI_ID: parseInt(VTINHTRANGTBI_ID),
        VSERIAL,
        VNGAY_TH,
        VVATTU_ID: parseInt(VVATTU_ID),
        VTHIETBI_ID,
        VSOLUONG: parseInt(VSOLUONG),
        VLYDOCAP_ID,
        VLYDOCAPTBI_ID,
        VTRANGBI_ID,
        VKIEU,
        VGHICHU
      }
      console.log('🚀 ~ file: index.vue ~ line 1261 ~ CAPNHAT_PHIEUVT_THUHOI_API ~ request', request)
      let result = this.GetData(await this.$root.context.post('/web-cabman/capvattu-ptm/capnhat_phieuvt_thuhoi_ptm', request))
      console.log('🚀 ~ file: index.vue ~ line 1282 ~ CAPNHAT_PHIEUVT_THUHOI_API ~ result', result)
      return result
    },
    async LAY_DS_KHO_THEO_NV (nhanvienId) {
      try {
        let data = this.GetData(await this.$root.context.post('/web-cabman/capvattu-ptm/lay_ds_kho_theo_nv', {NHANVIEN_ID: nhanvienId.toString()}))
        if (data.length > 0) {
          this.cboKho.list = data
          this.cboKho.value = data[0].KHO_ID
        }
      } catch (error) {
        this.cboKho.list = []
        this.cboKho.value = 0
      }
    },
    async Load_DS_VTTU_PTM () {
      try {
        // const data = {
      //   'KHO_ID': Number.parseInt(this.cboKho.value),
      //   'LOAITBI_ECMS_ID': Number.parseInt(this.loaitbi_ecms_id)
      // }
        const data = {
          'KHO_ID': 10119,
          'LOAITBI_ECMS_ID': 1
        }
        var reponse = await this.$root.context.post('/web-cabman/capvattu-ptm/lay_ds_tuvt_ptm', data)
        console.log('🚀 ~ file: index.vue ~ line 1516 ~ Load_DS_VTTU_PTM ~ reponse', reponse)
        return this.GetData(reponse)
      } catch (error) {
        return []
      }
    },
    async LAY_DS_VATTU_SD_PTM (PTM_ID, LOAITBI_ECMS_ID) {
      try {
        return this.GetData(await this.$root.context.post('/web-cabman/capvattu-ptm/lay_ds_vattu_sd_ptm', { PTM_ID, LOAITBI_ECMS_ID }))
      } catch (error) {
        return []
      }
    },
    async SP_LAY_DS_THAMSO_MD (kieuId) {
      let data = this.GetData(await this.$root.context.get('/web-ccdv/khaibaotsl/sp_lay_ds_loaihd_md', {p_kieu_id: kieuId}))
      if (data.length > 0) {
        let strVtemp = 'KIEMTRA_KYTU_SERIAL'
        let det = data.filter(item => item.ma_ts === strVtemp)
        if (det.length > 0) {
          let item = det[0]
          if (item.ten_ts.trim() !== '0') {
            this.check_serial = item.ten_ts.trim()
          }
        }
        strVtemp = 'CANHBAO_HOAC_LOI'
        det = data.filter(item => item.ma_ts === strVtemp)
        if (det.length > 0) {
          let item = det[0]
          if (item.ten_ts.trim() === '1') {
            this.canhbao_hoac_loi = true
          }
        }
      }
    },
    async SP_GETDANHMUCVT_DVT_ALL () {
      var reponse = await this.$root.context.post('/web-cabman/capvattu-ptm/getdanhmucvt_dvt_all')
      return this.GetData(reponse)
    },
    async LAY_DS_NV_GIAO_NHAN () {
      var data = {
        v_dvi_id: this.$root.token.getDonViID(),
        v_nv_id: this.$root.token.getNhanVienID()
      }
      var reponse = await this.$root.context.post('/web-cabman/capvattu-ptm/lay_ds_nv_giao_nhanv2', data)
      console.log('🚀 ~ file: index.vue ~ line 1584 ~ LAY_DS_NV_GIAO_NHAN ~ reponse', reponse)
      return this.GetData(reponse)
    },
    async CSS_KIEU_TBI () {
      var reponse = await this.$root.context.get('/web-cabman/capvattu-ptm/getkieutbi_ds')
      console.log('🚀 ~ file: index.vue ~ line 1591 ~ CSS_KIEU_TBI ~ reponse', reponse)
      let data = this.GetData(reponse)
      if (data.length > 0) {
        this.cboKieuTbi.list = data
        this.cboKieuTbi.value = data[0].KIEUTBI_ID
      }
    },
    async SP_LAY_DS_TINHTRANG_TBI () {
      try {
        var reponse = await this.$root.context.get('/web-cabman/capvattu-ptm/getds_tinhtrang')
        console.log('🚀 ~ file: index.vue ~ line 1602 ~ SP_LAY_DS_TINHTRANG_TBI ~ reponse', reponse)
        let data = this.GetData(reponse)
        if (data.length > 0) {
          this.cboTinhTrang.list = data
          this.cboTinhTrang.value = this.cboTinhTrang.list[0].TINHTRANGTBI_ID
        }
      } catch (error) {
        this.cboTinhTrang.list = []
        this.cboTinhTrang.value = 0
      }
    },
    async SP_LAY_DS_TINHTRANG_TBI_TH () {
      try {
        var reponse = await this.$root.context.get('/web-cabman/capvattu-ptm/getds_tinhtrang_th')
        console.log('🚀 ~ file: index.vue ~ line 1613 ~ SP_LAY_DS_TINHTRANG_TBI_TH ~ reponse', reponse)
        let data = this.GetData(reponse)
        if (data.length > 0) {
          this.cboTinhTrang.list = data
          this.cboTinhTrang.value = this.cboTinhTrang.list[0].TINHTRANGTBI_ID
        }
      } catch (error) {
        this.cboTinhTrang.list = []
        this.cboTinhTrang.value = 0
      }
    },
    async SP_LAY_DS_MUCDICH_LYDO (P_CHUOI) {
      var reponse = await this.$root.context.post('/web-cabman/capvattu-ptm/getmucdich_lydo', {P_CHUOI})
      console.log('🚀 ~ file: index.vue ~ line 1627 ~ SP_LAY_DS_MUCDICH_LYDO ~ reponse', reponse)
      return this.GetData(reponse)
    },
    async POST_GET_KEYS (keyname) {
      let key = ''
      let response = await this.$root.context.post('/web-quantri/donvitinh/get_keys', {keyname: keyname})
      console.log('🚀 ~ file: index.vue ~ line 1389 ~ POST_GET_KEYS ~ response', response)
      if (response && response.data && response.error_code && response.error_code === 'BSS-00000000') {
        key = response.data
      }
      return key
    },
    async kiemtra_vtcapra_ptm (PHIEUCC_ID, CHITIETCC_ID, TUVT_ID) {
      let data = this.GetData(await this.$root.context.post('/web-cabman/capvattu-ptm/kiemtra_vtcapra_ptm', {PHIEUCC_ID, CHITIETCC_ID, TUVT_ID}))
      return data
    },
    async INSERT_PHIEU_VATTU_PTM (request) {
      let data = this.GetData(await this.$root.context.post('/web-cabman/capvattu-ptm/insert_phieu_vattu_ptm', request))
      return data
    },
    async SP_XOA_PHIEU_VT_CAP_THUHOI (request) {
      try {
        let success = false
        let response = await this.$root.context.post('/web-cabman/capvattu-ptm/xoa_phieu_vt_cap_thuhoi', request)
        console.log('🚀 ~ file: index.vue ~ line 1661 ~ SP_XOA_PHIEU_VT_CAP_THUHOI ~ response', response)
        if (response && response.error_code === 'BSS-00000000') {
          success = true
        }
        return success
      } catch (error) {
        return false
      }
    },
    GetData: function (response) {
      if (response && response.error_code === 'BSS-00000000') {
        return response.data
      } else if (response && response.error_code === 'BSS-00000204') {
        return []
      } else {
        this.$root.$toast.error('Lấy dữ liệu không thành công')
      }
      return []
    },
    openDialog () {
      this.$refs.popupVatTuPTM.show()
    },
    async get_DSHinhAnh (VPHIEUVT_ID) {
      let data = this.GetData(await this.$root.context.post(
        '/web-cabman/capvattu-ptm/get_dshinhanh', {VPHIEUVT_ID}
      ))
      if (data.length > 0) {
        return data[0].MOTA
      } else {
        return null
      }
    },
    async load_img (link) {},
    async listImageClear () {},
    async gridviewVatTu_FocusedRowChanged (item) {
      try {
        if (item != null) {
          this.gridviewVatTu.selectItem = item
          console.log('🚀 ~ file: index.vue ~ line 1450 ~ gridviewVatTu_FocusedRowChanged ~ this.gridviewVatTu.selectItem', this.gridviewVatTu.selectItem)
          this.formThongTinThietBi.dtpNgayCap = moment(new Date(item.NGAY_CAP)).format('DD/MM/YYYY HH:mm A')
          let kt = item.PHIEUVT_ID
          if (kt != null && kt.toString() !== '0') {
            this.kt_id = Number.parseInt(kt)
            let link = await this.get_DSHinhAnh(kt)
            await this.load_img(link)
          } else {
            await this.listImageClear()
            this.phvt_id = 0
          }
        } else {
          await this.listImageClear()
          this.phvt_id = 0
        }
        if (this.cboKieuTbi.value.toString() === '1') {
          if (this.kieu_f === 1) {
            this.phvt_id = Number.parseInt(item.PHIEUVT_ID)
          }
          return
        }
        if (this.kieu_f !== 10) {
          if (this.cboKieuTbi.value.toString() === '3' && item.KIEUTBI_ID.toString() === '3') {
            this.LayTT_PVT(item)
          } else {
            this.cboVatTu.disabled = false
            this.disableBox.txtSoLuong = false
            this.$refs.cboSerial.setDisable(true)
            this.setActiveButton('tsbtnNhapMoi', true)
            this.setActiveButton('tsbtnGhiLai', true)
            this.setActiveButton('tsbtnXoa', true)
            this.refreshData()
            this.setActiveActions(1)
            return
          }
        }
      } catch (error) {
        this.$root.$toast.warning('Xảy ra lỗi khi hiển thị dữ liệu ', error)
      }
    },
    btnThuHoi_ButtonClick (item) {
      try {
        this.LayTT_Vattu_Thuhoi(item)
      } catch (error) {
        this.$root.$toast.error(error)
      }
    },
    LayTT_Vattu_Thuhoi (row) {
      console.log('🚀 ~ file: index.vue ~ line 1494 ~ LayTT_Vattu_Thuhoi ~ row', row)
      this.cboKieuTbi.value = KieuTbi.THUHOI
      let seri = row.SERIAL
      let matbi = row.MA_TBI
      this.setActiveButton('tsbtnNhapMoi', false)
      this.setActiveButton('tsbtnGhiLai', true)
      this.setActiveButton('tsbtnHuyBo', true)
      this.phvt_id = Number.parseInt(row.PHIEUVT_ID)
      if (seri === null && matbi === null) {
        this.$refs.cboSerial.setInputText('')
        this.disableBox.txtSoLuong = false
        this.cboVatTu.disabled = false
        this.$refs.cboSerial.setDisable(false)
      } else if (seri !== null && matbi === null) {
        this.$refs.cboSerial.setInputText(row.SERIAL)
        this.disableBox.txtSoLuong = true
        this.cboVatTu.disabled = true
        this.$refs.cboSerial.setDisable(true)
      } else if (seri === null && matbi !== null) {
        this.$refs.cboSerial.setInputText(row.MA_TBI)
        this.disableBox.txtSoLuong = false
        this.cboVatTu.disabled = false
        this.$refs.cboSerial.setDisable(false)
      }
      this.cboVatTu.value = Number.parseInt(row.VATTU_ID)
      let a = Number.parseInt(row.SOLUONG)
      this.formThongTinThietBi.soluong = a
      this.sl_goc = a
      this.vsdvt_id = Number.parseInt(row.SDVT_ID)
    },
    gridviewVatTuRowClick (index, item) {
      this.gridviewVatTu_FocusedRowChanged(item)
    },
    LayTT_PVT (row) {
      console.log('🚀 ~ file: index.vue ~ line 1777 ~ LayTT_PVT ~ row', row)
      try {
        this.setActiveButton('tsbtnNhapMoi', true)
        this.setActiveButton('tsbtnGhiLai', true)
        this.setActiveButton('tsbtnXoa', true)
        this.phvt_id = Number.parseInt(row.PHIEUVT_ID)
        if (row.SERIAL === null && row.MA_TBI === null) {
          this.$refs.cboSerial.setInputText('')
          this.disableBox.txtSoLuong = false
          this.disableBox.cboVatTu = true
          this.$refs.cboSerial.setDisable(true)
        } else if (row.SERIAL !== null && row.MA_TBI === null) {
          this.disableBox.txtSoLuong = true
          this.disableBox.cboVatTu = true
          this.$refs.cboSerial.setInputText(row.SERIAL)
          this.$refs.cboSerial.setDisable(true)
        } else if (row.SERIAL === null && row.MA_TBI !== null) {
          this.$refs.cboSerial.setInputText(row.MA_TBI)
          this.disableBox.txtSoLuong = false
          this.disableBox.cboVatTu = true
          this.$refs.cboSerial.setDisable(false)
        }
        this.cboVatTu.value = parseInt(row.VATTU_ID)
        this.formThongTinThietBi.soluong = parseInt(row.SOLUONG)
        this.cboTinhTrang.value = parseInt(row.TINHTRANGTBI_ID)
        this.DmMucDichCap.selectMucDichCap = parseInt(row.MUCDICH_ID)
        this.formThongTinThietBi.txtGhiChu = row.GHICHU
      } catch (error) {

      }
    },
    setActiveButton (id, status) {
      this.actions[this.actions.findIndex(item => item.id === id)].active = status
    },
    setVisibleButton (id, status) {
      this.actions[this.actions.findIndex(item => item.id === id)].visible = status
    },
    setActiveActions (kieu) {
      this.actions.forEach((item) => {
        if (item.id !== 'tsbtnExcel') {
          item.active = false
        }
      })
      this.disableBox.cboKieuTbi = false
      if (kieu === -1) { // bat dau
        this.setActiveButton('tsbtnGhiLai', true)
        this.setActiveButton('tsbtnHuyBo', true)
        this.setColThuHoiVisible(true)
        this.showColumnCheckbox = false
        this.grvDS_VT_Giao.showColumnCheckbox = false
      } else if (kieu === 0) { // bắt đầu
        this.setActiveButton('tsbtnNhapMoi', true)
        this.setColThuHoiVisible(true)
        this.grvDS_VT_Giao.showColumnCheckbox = false
      } else if (kieu === 1) { // thêm mới
        this.setActiveButton('tsbtnNhapMoi', true)
        this.setActiveButton('tsbtnHuyBo', true)
        this.setColThuHoiVisible(true)
        this.grvDS_VT_Giao.showColumnCheckbox = false
      } else if (kieu === 2) { // Huỷ
        this.setActiveButton('tsbtnNhapMoi', true)
        this.setActiveButton('tsbtnXoa', true)
        this.setColThuHoiVisible(true)
        this.grvDS_VT_Giao.showColumnCheckbox = false
      } else if (kieu === 3) { // edit
        this.setActiveButton('tsbtnNhapMoi', true)
        this.setActiveButton('tsbtnXoa', true)
        this.setActiveButton('tsbtnGhiLai', true)
        this.setActiveButton('tsbtnHuyBo', true)
        this.disableBox.cboKieuTbi = false
        this.disableBox.dropLyDoCap = true
        this.disableBox.dropMucDichCap = false
        this.setColThuHoiVisible(true)
        this.grvDS_VT_Giao.showColumnCheckbox = false
      } else if (kieu === 277) {
        this.setActiveButton('tsbtnNhapMoi', false)
        this.setActiveButton('tsbtnXoa', false)
        this.setActiveButton('tsbtnGhiLai', false)
        this.setActiveButton('tsbtnHuyBo', false)
        this.setColThuHoiVisible(false)
        this.grvDS_VT_Giao.showColumnCheckbox = true
      }
    },
    Huy () {
      this.refreshData()
      this.disableBox.cboVatTu = true
      this.disableBox.txtSoLuong = true
      this.setActiveButton('tsbtnNhapMoi', true)
      this.setActiveButton('tsbtnGhiLai', false)
      this.setActiveButton('tsbtnHuyBo', false)
      if (this.cboKieuTbi.value.toString() === '1') { // Moi
        this.setVisibleButton('tsbtnGhiLai', false)
        this.setActiveButton('tsbtnXoa', true)
        this.setVisibleButton('tsbtnHuyBo', false)
        this.disableBox.cboVatTu = true
        this.disableBox.txtSoLuong = true
        this.setVisibleButton('tsbtnNhapMoi', false)
        this.$refs.cboSerial.setDisable(false)
      } else {
        this.disableBox.cboVatTu = true
        this.disableBox.txtSoLuong = false
        this.$refs.cboSerial.setDisable(true)
        this.setVisibleButton('tsbtnNhapMoi', true)
        this.setVisibleButton('tsbtnGhiLai', true)
        this.setVisibleButton('tsbtnHuyBo', true)
        this.refreshData()
        this.setActiveActions(1)
      }
    },
    Xoa () {
      try {
        this.vdonvi_nhan_id = Number.parseInt(this.gridviewVatTu.selectItem.DONVI_ID)
        if (!this.KiemTraDuLieu_Tb()) {
          return
        }
        this.$confirm('Bạn có thực sự muốn xóa phiếu vật tư đang chọn không?', 'Thông báo', {
          confirmButtonText: 'Có',
          cancelButtonText: 'Không'
        }).then(async () => {
          let vphieuvt_id = this.gridviewVatTu.selectItem.PHIEUVT_ID
          let vtuvt_id = this.gridviewVatTu.selectItem.TUVT_ID
          let vsoluong = this.gridviewVatTu.selectItem.SOLUONG
          if (vtuvt_id === null || vtuvt_id === '') {
            vtuvt_id = -1
            vsoluong = 0
          }
          const request = {
            'VPHIEUVT_ID': vphieuvt_id,
            'VTUVT_ID': vtuvt_id,
            'VSOLUONG': vsoluong
          }
          console.log('🚀 ~ file: index.vue ~ line 1638 ~ Xoa ~ request', request)
          let success = await this.SP_XOA_PHIEU_VT_CAP_THUHOI(request)
          if (success) {
            this.$root.$toast.success('Xóa phiếu vật tư thành công')
            await this.LoadGridVT()
            await this.LoadGrid_VatTuSD()
            await this.Load_DS_VTTU()
            this.$refs.tsbtnNhapMoi.click()
          } else {
            this.$root.$toast.warning('Xóa phiếu vật tư không thành công')
          }
        })
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Xảy ra lỗi khi thực hiện xóa phiếu vật tư')
      }
    },
    KiemTraDuLieu_Tb () {
      if (this.phieucc_id === 0) {
        this.$root.$toast.warning('Chưa có thông tin phiếu. Hãy kiểm tra lại!')
        return false
      }
      if (this.vdonvi_nhan_id !== Number.parseInt(this.$root.token.getDonViID())) {
        this.$root.$toast.warning('Phiếu không cùng đơn vị với đơn vị của user. Bạn không được phép cập nhật.')
        return false
      }
      if (this.vngay_ht !== '') {
        let ngayHt = moment(new Date(this.vngay_ht)).format('MMYYYY')
        let now = moment(new Date()).format('MMYYYY')
        let currentTime = new Date()
        let lastMonth = moment(currentTime.setMonth(currentTime.getMonth() - 1)).format('MMYYYY')
        if (ngayHt !== now) {
          if (ngayHt !== lastMonth) {
            this.$root.$toast.warning('Bạn chỉ được phép thao tác phiếu thực hiện trong tháng hoặc tháng trước')
            return false
          }
        }
      }
      return true
    },
    async Insert_SErial_V2 (newData, serial) {
      try {
        let vNgayCap = this.getDate(this.formThongTinThietBi.dtpNgayCap).toDate()
        let kieuTb = 1
        let vTrangBi = TRANGBI.VIENTHONG_TRANGBI
        let vTuVTId = parseInt(newData.TUVT_ID)
        let vThietBiId = parseInt(newData.THIETBI_ID)
        let vSoLuong = 1
        let maTbi = ''
        let vLyDoCap = parseInt(this.DmLyDoCap.selectLyDoCap)
        if (vNgayCap > new Date()) {
          this.$root.$toast.warning('Ngày cấp ko được lớn hơn ngày hiện tại')
          return
        }
        let tinhtrangTbi = parseInt(this.cboTinhTrang.value)
        this.$confirm('Bạn có chắc chăn muốn cấp thiết bị có serial: ' + serial + ' không?', 'Thông báo', {
          confirmButtonText: 'Có',
          cancelButtonText: 'Không'
        }).then(async () => {
          // Kiểm tra kho đơn vị cấp ra
          this.$root.showLoading(true)
          let ktKQ = await this.kiemtra_vtcapra_ptm(this.phieucc_id, this.chitietcc_id, vTuVTId)
          if (ktKQ.length > 0) {
            if (ktKQ[0].KT_KQ.toString() !== '1') {
              this.$root.$toast.warning(ktKQ[0].KT_KQ.toString())
              this.$root.showLoading(false)
              return
            }
          }
          let vPhieuVTId = await this.POST_GET_KEYS('phieuvt_id')
          console.log('🚀 ~ file: index.vue ~ line 1720 ~ Insert_SErial_V2 ~ vPhieuVTId', vPhieuVTId)
          let vsvdtId = 0
          let vnhvIdTemp = this.nhanvienId
          if (this.cboNhanVien.value.toString() !== vnhvIdTemp.toString()) {
            vnhvIdTemp = this.cboNhanVien.value
          }
          var ld = Number.parseInt(this.DmMucDichCap.selectMucDichCap)
          const request = {
            VSDVT_ID: vsvdtId.toString(),
            VPHIEUVT_ID: vPhieuVTId.toString(),
            VPHIEUCC_ID: this.phieucc_id.toString(),
            VNGAY_CAP: vNgayCap,
            VKIEUTBI_ID: kieuTb.toString(),
            VTRANGBI_ID: vTrangBi.toString(),
            VTUVT_ID: vTuVTId.toString(),
            VTHIETBI_ID: vThietBiId.toString(),
            VSOLUONG: vSoLuong.toString(),
            VSERIAL: serial,
            VMA_TBI: maTbi,
            VNHANVIEN_ID: vnhvIdTemp.toString(),
            VLYDOCAP_ID: vLyDoCap.toString(),
            VLYDOCAPTO_ID: ld.toString(),
            VCHITIETCC_ID: this.chitietcc_id.toString(),
            VPTM_ID: this.ptm_id.toString(),
            VLOAITBI_ECMS_ID: this.loaitbi_ecms_id.toString(),
            VTTPK: '1',
            VTINHTRANGTBI_ID: tinhtrangTbi.toString(),
            VGHICHU: this.formThongTinThietBi.txtGhiChu
          }
          let kq = await this.INSERT_PHIEU_VATTU_PTM(request)
          if (kq.length > 0) {
            if (kq[0].RESULT !== '1') {
              this.$root.$toast.warning(kq[0].RESULT)
            }
          }
          this.$refs.cboSerial.setInputText('')
          await this.LoadGridVT()
          await this.LoadGrid_VatTuSD()
          if (this.cboKho.value === 0) {
            this.$refs.cboSerial.setDisable(true)
            this.DmVTGiao.data = []
          } else {
            this.$refs.cboSerial.setDisable(false)
            await this.Load_DS_VTTU()
          }
          this.$root.showLoading(false)
        })
      } catch (error) {
        this.$root.showLoading(false)
        this.$root.$toast.warning(JSON.stringify(error))
      }
    },
    XuatExcel () {
      if (this.gridviewVatTu.list.length > 0) {
        let data = XLSX.utils.json_to_sheet(this.gridviewVatTu.list)
        let wb = XLSX.utils.book_new() // make Workbook of Excel
        XLSX.utils.book_append_sheet(wb, data, 'DSVT') // sheetAName is name of Worksheet
        // export Excel file
        XLSX.writeFile(wb, 'D.S Vật tư')
      } else this.$root.$toast('Không có dữ liệu!')
    },
    onActionClick (action) {
      if (action.id === 'tsbtnNhapMoi') {
        this.refreshData()
        this.setActiveActions(1)
        this.disableBox.cboVatTu = true
        this.disableBox.txtSoLuong = false
        this.$refs.cboSerial.setDisable(false)
        this.setActiveButton('tsbtnNhapMoi', false)
        this.setActiveButton('tsbtnGhiLai', true)
        this.setActiveButton('tsbtnHuyBo', true)
      } else if (action.id === 'tsbtnGhiLai') {
        if (this.kieu_f === 1) {
          this.CAPNHAT_NGAY_CAP()
        } else {
          this.CAPNHAT_PHIEUVT_THUHOI()
        }
      } else if (action.id === 'tsbtnHuyBo') {
        this.Huy()
      } else if (action.id === 'tsbtnXoa') {
        this.Xoa()
      } else if (action.id === 'tsbtnExcel') {
        this.XuatExcel()
      }
    },
    refreshData () {
      this.vsdvt_id = 0
      this.formThongTinThietBi.soluong = 0
      this.cboVatTu.value = 0
      this.formThongTinThietBi.txtGhiChu = ''
      this.phvt_id = 0
      this.DmVTGiao.selectVTGiao = 0
    },
    handleHideModal () {
      this.cboNhanVien.value = 0
      this.gridviewVatTu.list = []
      this.grvDS_VT_Giao.list = []
      this.refreshData()
    },
    getActiveButton (id) {
      return this.actions[this.actions.findIndex(item => item.id === id)].active
    },
    getDate (dateStr) {
      return moment(dateStr, 'DD/MM/YYYY HH:mm A')
    },
    setColThuHoiVisible (status) {
      let colIndex = this.grvDS_VT_Giao.headers.findIndex(item => item.fieldName === 'btnThuHoi')
      if (colIndex >= 0) {
        this.grvDS_VT_Giao.headers[colIndex].visible = status
      }
    }
  }
}
</script>
<style>
.modal .modal-vattuptm-size {
  max-width: 85% !important;
}
.select-kho .select2-container {
  width: 336.219px !important;
}
.e-control {
  font-family: "Roboto", Arial;
}
.disabled {
  pointer-events: none;
  opacity: 0.7;
}
.disabled_color {
  color: gray !important;
}
</style>
