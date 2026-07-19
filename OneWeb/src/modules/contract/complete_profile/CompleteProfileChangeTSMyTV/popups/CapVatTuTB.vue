<template src="./CapVatTuTB.html">
</template>
<script>
import moment from 'moment'
import {DataManager, ODataAdaptor, Predicate, Query} from '@syncfusion/ej2-data'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
import CapVatTuTBAPI from './CapVatTuTBAPI'
import {LoaiHopDong} from '../../../../../utils/Enum'
import {TRANGBI} from '../../../setup/InstallNewSubs/Enums'
import KDatePicker from '@/components/kylq_components/KDatePicker.vue'
import {DICHVU_VIENTHONG} from '../../../../../constants'
import XLSX from 'xlsx'
import ComboboxGridEdit from '../components/ComboboxGridEdit'
import Vue from 'vue'
import {KieuTbi} from './KieuTbi'
import XemBaoCao from '../../../../print/XemBaoCao/XemBaoCao'
import VatTuImage from '../components/VatTuImage'
export default {
  name: 'CapVatTuTB',
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
    ComboboxGridEdit,
    XemBaoCao,
    KDatePicker,
    VatTuImage
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
          id: 'tsbtnAutoCap',
          name: 'Tự động cấp',
          active: true,
          icon_class: 'one-list-search',
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
        },
        {
          id: 'tsbtnKHCapNhat',
          name: 'KH bồi thường',
          active: true,
          icon_class: 'one-mail-refresh',
          visible: true
        }
      ],
      xuatPhieuShow: false,
      settingSelect2: {
        language: 'vi'
      },
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
        disabled: true
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
            fieldName: 'ngay_cap',
            headerText: 'Ngày cấp',
            allowFiltering: true,
            allowSorting: false,
            template: function () {
              return {
                template: Vue.component('NgayCapTemplate', {
                  template: `<span class="bold">{{ ngaycap }}</span>`,
                  data () {
                    return {data: {}}
                  },
                  computed: {
                    ngaycap () {
                      return moment(this.data.ngay_cap, 'DD/MM/YYYY HH:mm:ss').format('DD/MM/YYYY')
                    },
                    parent () {
                      return this.$parent.$parent.$parent.$parent.$parent.$parent
                    }
                  }
                })
              }
            }
          },
          {
            fieldName: 'ma_tbi',
            headerText: 'Mã T.Bị',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ten_vt',
            headerText: 'Thiết bị',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'serial',
            headerText: 'Seri',
            allowFiltering: true,
            allowSorting: false,
            template: function () {
              return {
                template: Vue.component('SerialEdit', {
                  template: `<span class="text-danger bold">{{ data.serial }}</span>`,
                  data () {
                    return {data: {}}
                  },
                  computed: {
                    parent () {
                      return this.$parent.$parent.$parent.$parent.$parent.$parent
                    }
                  }
                })
              }
            }
          },
          {
            fieldName: 'soluong',
            headerText: 'Số lượng',
            allowFiltering: true,
            allowSorting: false,
            template: function () {
              return {
                template: Vue.component('SoLuongTemplate', {
                  template: `<span class="bold">{{ data.soluong }}</span>`,
                  data () {
                    return {data: {}}
                  },
                  computed: {
                    parent () {
                      return this.$parent.$parent.$parent.$parent.$parent.$parent
                    }
                  }
                })
              }
            }
          },
          {
            fieldName: 'kieu_tbi',
            headerText: 'Kiểu thiết bị',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'mota',
            headerText: 'Mô tả',
            allowFiltering: true,
            allowSorting: false,
            textAlign: 'Center',
            width: 120,
            template: function () {
              return {
                template: Vue.component('MoTaTemplate', {
                  template: ` <div class="input-icon-right" @click="btnViewImageClick">
                                <strong>Xem ảnh</strong>
                                <span class="icon nc-icon-outline ui-1_zoom"></span>
                              </div>`,
                  data () {
                    return {data: {}}
                  },
                  computed: {
                    parent () {
                      return this.$parent.$parent.$parent.$parent.$parent
                    }
                  },
                  methods: {
                    btnViewImageClick () {
                      this.parent.showPopupVatTuImage(this.data)
                    }
                  }
                })
              }
            }
          },
          {
            fieldName: 'mac',
            headerText: 'MAC',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'tt_tbi',
            headerText: 'Tình trạng',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'trangbi',
            headerText: 'Trang bị',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'nguoi_cn',
            headerText: 'Người CN',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ten_dv',
            headerText: 'Đơn vị',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ghichu',
            headerText: 'Ghi chú',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'khocap',
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
                                style="background-color: #3684e0 !important; padding: 5px 15px; color: white">
                  <span class="icon one-td-arrow-up"></span></a>`,
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
            fieldName: 'ma_tbi',
            headerText: 'Mã T.Bị',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'ten_vt_new',
            headerText: 'Thiết bị',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'serial',
            headerText: 'Seri',
            allowFiltering: true,
            allowSorting: false,
            visible: true,
            template: function () {
              return {
                template: Vue.component('SerialTemplate', {
                  template: `<span class="text-danger bold">{{ data.serial }}</span>`,
                  data () {
                    return {data: {}}
                  },
                  computed: {
                    parent () {
                      return this.$parent.$parent.$parent.$parent.$parent.$parent
                    }
                  }
                })
              }
            }
          },
          {
            fieldName: 'soluong',
            headerText: 'Số lượng',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'kieu_tbi',
            headerText: 'Kiểu tbị',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'tinhtrang_tbi',
            headerText: 'Tình trạng',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'tentrangbi',
            headerText: 'Trang bị',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'ngay_cn',
            headerText: 'Ngày cấp',
            allowFiltering: true,
            allowSorting: false,
            // format: {type: 'date', format: 'dd/MM/yyyy'},
            // type: 'date',
            visible: true
          }
        ],
        selected: 0,
        selectItem: null,
        checked: [],
        showColumnCheckbox: true,
        selectionOptions: {
          type: 'Single',
          checkboxMode: 'ResetOnRowClick'
        }
      },
      disableBox: {
        dtpNgayCap: false,
        txtSoLuong: false
      },
      phieu_id: 0,
      hdtb_id: 0,
      baohong_id: 0,
      thuebao_id: 0,
      vma_tbcur: '',
      loaihd_id: 0,
      loaitb_id: 0,
      vdvvt_id: 0,
      vdonvi_nhan_id: 0,
      vngay_ht: '',
      phvt_id: 0,
      vsdvt_id: 0,
      loaitbi_id: 0,
      kieu_f: 0,
      kt_id: 0,
      donvi_id: 0,
      xacnhan: false,
      sl_goc: 0,
      username: this.$root.token.getUserName(),
      tagfrm: '0', // 1 thuong 2//GDV  thu cap, //3 GDV tam thu,
      matinh_hientai: this.$root.token.getMaTinh(),
      kh_boithuong: false,
      canhbao_hoac_loi: false,
      chophep_serial_thuhoi_thucong: false,
      check_serial: '0',
      animationSettings: {effect: 'Zoom'},
      position: {X: 'center', Y: 'top'},
      dtVatTu: [],
      tsbtnInBienBanVisible: {
        tsbtnInBienBanNTWifi: true,
        tsbtnInBienBanBG: true,
        tsbtnInBienBanTH: false
      },
      DmMucDichCap: {
        data: [],
        disabled: false,
        textField: 'mucdich',
        selectDonViQL: '',
        valueField: 'mucdich_id',
        panelDataHeight: 'auto',
        headers: [
          {
            fieldName: 'mucdich',
            headerText: 'Chọn mục đích cấp',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'nhom_md',
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
        disabled: false,
        textField: 'mucdich',
        valueField: 'mucdich_id',
        panelDataHeight: 'auto',
        headers: [
          {
            fieldName: 'mucdich',
            headerText: 'Chọn lý do cấp',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'nhom_md',
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
            fieldName: 'thietbi_id',
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
            allowFiltering: false,
            allowSorting: false,
            width: 100,
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
            fieldName: 'ten_vt',
            headerText: 'Tên vật tư',
            allowFiltering: true,
            allowSorting: false,
            allowEditing: false
          },
          {
            fieldName: 'ma_tbi',
            headerText: 'Mã T.Bị',
            allowFiltering: true,
            allowSorting: false,
            allowEditing: false
          },
          {
            fieldName: 'sl_ton',
            headerText: 'Số lượng',
            allowFiltering: true,
            allowSorting: false,
            allowEditing: false,
            template: function () {
              return {
                template: Vue.component('SoLuongTemplate', {
                  template: `<span class="bold">{{ data.sl_ton }}</span>`,
                  data () {
                    return {data: {}}
                  },
                  computed: {
                    parent () {
                      return this.$parent.$parent.$parent.$parent.$parent.$parent
                    }
                  }
                })
              }
            }
          },
          {
            fieldName: 'luongcap',
            headerText: 'SL cấp',
            allowFiltering: true,
            allowSorting: true,
            allowEditing: true
          },
          {
            fieldName: 'serial',
            headerText: 'Serial',
            allowFiltering: true,
            allowSorting: false,
            allowEditing: false,
            type: 'Number',
            format: 'N0',
            template: function () {
              return {
                template: Vue.component('SerialEdit', {
                  template: `<span class="text-danger bold">{{ data.serial }}</span>`,
                  data () {
                    return {data: {}}
                  },
                  computed: {
                    parent () {
                      return this.$parent.$parent.$parent.$parent.$parent.$parent
                    }
                  }
                })
              }
            }
          },
          {
            fieldName: 'serial_gp',
            headerText: 'Serial GP',
            allowFiltering: true,
            allowSorting: false,
            allowEditing: false,
            template: function () {
              return {
                template: Vue.component('SerialGPTemplate', {
                  template: `<span class="bold">{{ data.serial_gp }}</span>`,
                  data () {
                    return {data: {}}
                  },
                  computed: {
                    parent () {
                      return this.$parent.$parent.$parent.$parent.$parent.$parent
                    }
                  }
                })
              }
            }
          },
          {
            fieldName: 'mac',
            headerText: 'MAC',
            allowFiltering: true,
            allowSorting: false,
            allowEditing: false
          },
          {
            fieldName: 'lohang',
            headerText: 'Lô hàng',
            allowFiltering: true,
            allowSorting: false,
            allowEditing: false
          },
          {
            fieldName: 'dongia',
            headerText: 'Đơn giá',
            allowFiltering: true,
            allowSorting: false,
            allowEditing: false,
            type: 'Number',
            format: 'N0'
          },
          {
            fieldName: 'tien',
            headerText: 'Thành tiền',
            allowFiltering: true,
            allowSorting: false,
            allowEditing: false,
            type: 'Number',
            format: 'N0'
          },
          {
            fieldName: 'dvi_tinh',
            headerText: 'ĐVT',
            allowFiltering: true,
            allowSorting: false,
            allowEditing: false
          },
          {
            fieldName: 'tinhtrang_tbi',
            headerText: 'Tình trạng T.Bị',
            allowFiltering: true,
            allowSorting: false,
            allowEditing: false
          },
          {
            fieldName: 'ngay_ct',
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
      donviId: this.$root.token.getDonViID(),
      maycn: '',
      ipcn: '',
      phieuvt_id: '0',
      xembaocao: {
        ma_bc: '',
        dsGiaTri: []
      },
      clickBtnVTTH: false,
      rowVTTH: null
    }
  },
  watch: {
    'cboKieuTbi.value': async function (newValue) {
      await this.cboKieuTbi_EditValueChanged(newValue)
      if (this.clickBtnVTTH) {
        this.setInfoVTThuHoi(this.rowVTTH)
        this.clickBtnVTTH = false
        this.rowVTTH = null
      }
    },
    'cboNhanVien.value': async function (newValue) {
      await this.cboNhanVien_EditValueChanged(newValue)
    },
    'cboKho.value': async function (newValue) {
      this.$refs.cboSerial.setDisable(false)
      await this.Load_DS_VTTU()
    },
    'DmLyDoCap.selectLyDoCap': async function (newValue) {
      if (this.cboKieuTbi.value == 3) {
        this.refreshData()
        if (newValue.toString() == '277') {
          this.setActiveActions(277)
        } else {
          await this.cboKieuTbi_EditValueChanged(3)
        }
      } else {
        await this.cboKieuTbi_EditValueChanged(this.cboKieuTbi.value)
      }
    }
  },
  methods: {
    cboNhanVien_EditValueChanged (newValue) {
      this.LAY_DS_KHO_THEO_NV(newValue, this.donviId)
    },
    async cboKieuTbi_EditValueChanged (newValue) {
      // mới
      if (newValue.toString() == '1') {
        this.setVisibleButton('tsbtnGhiLai', false)
        this.setVisibleButton('tsbtnAutoCap', true)
        this.setActiveButton('tsbtnXoa', true)
        this.disableBox.dtpNgayCap = false
        this.setActiveButton('tsbtnHuyBo', false)
        this.cboVatTu.disabled = true
        this.disableBox.txtSoLuong = true
        this.$refs.cboSerial.setVisible(true)
        this.$refs.cboSerial.setDisable(false)
        this.setVisibleButton('tsbtnNhapMoi', false)
        this.disableBox.dtpNgayCap = false
        await this.SP_LAY_DS_TINHTRANG_TBI()
      } else {
        this.setVisibleButton('tsbtnAutoCap', false)
        this.cboVatTu.disabled = false
        this.disableBox.dtpNgayCap = this.kieu_f != 1
        this.dtpNgayCap = moment(new Date()).format('MM/DD/YYYY HH:mm A')
        this.disableBox.txtSoLuong = false
        this.$refs.cboSerial.setVisible(false)
        this.$refs.cboSerial.setDisable(false)
        this.setVisibleButton('tsbtnNhapMoi', true)
        this.setVisibleButton('tsbtnGhiLai', true)
        this.setVisibleButton('tsbtnHuyBo', true)
        await this.SP_LAY_DS_TINHTRANG_TBI_TH()
        this.refreshData()
        this.setActiveActions(1)
      }
    },
    async frmCapVatTuTB_Load () {
      try {
        this.loading(true)
        this.maycn = await this.$root.token.getMachine()
        this.ipcn = await this.$root.token.getIP()
        let matinh = this.$root.token.getMaTinh()
        if (matinh == 'HNI' && this.baohong_id != 0) {
          this.tsbtnInBienBanVisible.tsbtnInBienBanTH = true
        }
        this.LoadDataFromProps()
        await this.SP_KT_KH_BOITHUONG()
        await this.LoadCbo()
        await this.LoadGridVT()
        await this.LoadGrid_VatTuSD()
        this.ThietLap()
        if (this.loaihd_id == LoaiHopDong.CHAMDUT_SD) {
          this.cboKieuTbi.value = 3
          this.cboKieuTbi.disabled = true
        }
        await this.SP_LAY_DS_THAMSO_MD('0')
      } catch (error) {
        console.log(error)
      } finally {
        this.loading(false)
      }
    },
    hideModal () {
      this.$refs.popupVatTuTB.hide()
    },
    LoadDataFromProps () {
      if (this.inputData != null) {
        console.log('input data ', this.inputData)
        this.loaitb_id = this.inputData.loaitb_id ? this.inputData.loaitb_id : 0
        this.phieu_id = this.inputData.phieu_id ? this.inputData.phieu_id : 0
        this.loaihd_id = this.inputData.loaihd_id ? this.inputData.loaihd_id : 0
        this.hdtb_id = this.inputData.hdtb_id ? this.inputData.hdtb_id : 0
        this.thuebao_id = this.inputData.thuebao_id ? this.inputData.thuebao_id : 0
        this.vdvvt_id = this.inputData.vdvvt_id ? this.inputData.vdvvt_id : 0
        this.vma_tbcur = this.inputData.vma_tbcur ? this.inputData.vma_tbcur : 0
        this.baohong_id = this.inputData.baohong_id ? this.inputData.baohong_id : 0
        this.kieu_f = this.inputData.kieu_f ? this.inputData.kieu_f : 0
        this.tagfrm = this.inputData.tagfrm ? this.inputData.tagfrm : '0'
      }
    },
    async SP_KT_KH_BOITHUONG () {
      let data = this.GetData(await CapVatTuTBAPI.SP_KT_KH_BOITHUONG(this.axios))
      if (data.length > 0) {
        if (data[0].trangthaisd_id) {
          this.kh_boithuong = true
        }
      }
    },
    CapVatTu () {
      try {
        if (this.editSerial != null) {
          let newData = this.editSerial.data
          let serial = newData.serial
          let matbi = newData.ma_tbi
          if (!serial && matbi) {
            this.Insert_NotSerial(newData, matbi, 0)
          } else if (!serial && !matbi) {
            this.Insert_NotSerial(newData, '', 0)
          } else {
            this.Insert_SErial_V2(newData, serial.toString().trim())
          }
          if (this.tagfrm == '2') {
            this.CAPNHAT_SUDUNG_VATTU(this.hdtb_id, this.baohong_id, this.phieu_id)
          }
          // reset serial
          this.editSerial = null
        }
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Xảy ra lỗi khi thực hiện cấp vật tư: ' + error.message)
      }
    },
    receiverInsertSerialData (arg) {
      if (arg != null) {
        this.editSerial = arg
      }
    },
    async Insert_NotSerial (newData, matbi, sdvtId) {
      try {
        this.loading(true)
        let vNgayCap = this.getDate(this.formThongTinThietBi.dtpNgayCap).toDate()
        let vKieuTbi = 1
        let vTrangBi = this.cboKieuTrangBi.value
        let vTuVTId = newData.tuvt_id
        let vThietBiId = newData.thietbi_id
        let vSoLuong = Number.parseFloat(newData.luongcap)
        let thongbao = ''
        let dieukiencap = await this.DIEUKIEN_CAP_VT(this.hdtb_id, this.loaihd_id, this.loaitbi_id, vThietBiId)
        if (dieukiencap != '1') {
          this.$root.$toast.error(dieukiencap)
          return
        }
        let kiemtradmvt = await this.FN_KIEMTRA_DM_VT(this.hdtb_id, this.baohong_id, newData.vattu_id, vSoLuong, this.cboKho.value)
        if (kiemtradmvt != '1') {
          thongbao = thongbao + kiemtradmvt + '\r\n'
        }
        if (thongbao != '') {
          // Nếu có tham số thì chặn
          if (this.canhbao_hoac_loi) {
            this.$root.$toast.error(thongbao)
            return
          }
          let accept = false
          this.$confirm('Cảnh báo định mức \r\n ' + thongbao, 'Thông báo', {
            confirmButtonText: 'Có',
            cancelButtonText: 'Không'
          }).then(() => {
            accept = true
          })
          if (accept == false) {
            return
          }
        }
        if (vNgayCap > new Date()) {
          this.$root.$toast.warning('Ngày cấp ko được lớn hơn ngày hiện tại')
          return
        }
        if (vSoLuong == 0) {
          this.$root.$toast.warning('Bạn chưa điền số lượng cần cấp cho thuê bao.')
          return
        }
        let vTon = Number.parseInt(newData.sl_ton)
        if (vSoLuong > vTon) {
          this.$root.$toast.warning('Số lượng cấp vượt quá số lượng thực của thiết bị.')
        }
        let KiemTraDay = await this.SP_KIEMTRA_DAY(this.phieu_id, this.hdtb_id, this.baohong_id, vTuVTId)
        if (KiemTraDay.length > 0) {
          this.$root.$toast.warning(newData.ten_vt + 'đã được cấp bạn ko thể cấp tiếp đc \r\n')
          return
        }
        let vPhieuVTId = await this.POST_GET_KEYS('phieuvt_id')
        let mathietbi = ''
        if (matbi != '') {
          mathietbi = matbi
        }
        let vLyDoCap = this.DmLyDoCap.selectLyDoCap
        let tinhTrangTbi = this.cboTinhTrang.value
        // Kiểm tra kho đơn vị cấp ra
        let tempHDBHId = 0
        let vk = 0
        if (this.hdtb_id != 0) {
          tempHDBHId = this.hdtb_id
          vk = 1
        } else {
          tempHDBHId = this.baohong_id
          vk = 2
        }
        if (this.vdvvt_id == DICHVU_VIENTHONG.TSL) {
          let ktKq = await this.FN_KIEMTRA_VTCAPRA_V2(vk, tempHDBHId, 1, vTuVTId, this.phieu_id)
          if (ktKq != '1') {
            this.$root.$toast.warning(ktKq)
            return
          }
        } else {
          let ktKq = await this.KIEMTRA_VTCAPRA(vk, tempHDBHId, 1, vTuVTId)
          if (ktKq != '1') {
            this.$root.$toast.warning(ktKq)
            return
          }
        }
        let vnhnIdTemp = this.nhanvienId
        if (this.cboNhanVien.value.toString() != vnhnIdTemp.toString()) {
          vnhnIdTemp = Number.parseInt(this.cboNhanVien.value)
        }
        // Create du lieu
        const request = {
          'vsdvt_id': '0',
          'vphieuvt_id': vPhieuVTId,
          'vphieu_id': this.phieu_id.toString(),
          'vngay_cap': this.getDate(this.formThongTinThietBi.dtpNgayCap).format('DD/MM/YYYY HH:mm:ss'),
          'vkieutbi_id': vKieuTbi,
          'vtrangbi_id': vTrangBi.toString(),
          'vtuvt_id': vTuVTId.toString(),
          'vthietbi_id': vThietBiId.toString(),
          'vsoluong': vSoLuong,
          'vserial': '',
          'vma_tbi': mathietbi,
          'vnhanvien_id': vnhnIdTemp,
          'vdonvi_id': this.donviId.toString(),
          'vnguoi_cn': this.$root.token.getUserName(),
          'vmay_cn': this.maycn,
          'vip_cn': this.ipcn,
          'vlydocap_id': vLyDoCap.toString(),
          'vlydocapto_id': this.DmMucDichCap.selectMucDichCap.toString(),
          'vhdtb_id': this.hdtb_id.toString(),
          'vbaohong_id': this.baohong_id.toString(),
          'vttpk': '1',
          'vtinhtrangbi_id': tinhTrangTbi.toString(),
          'vghichu': this.formThongTinThietBi.txtGhiChu
        }
        let kq = await this.INSERT_PHIEU_VATTU_V2(request)
        if (kq != '1') {
          this.$root.$toast.warning(kq)
        }
        this.$refs.cboSerial.setInputText('')
        await this.LoadGridVT()
        await this.Load_DS_VTTU()
        if (this.cboKho.value == 0) {
          this.$refs.cboSerial.setDisable(true)
          this.DmVTGiao.data = []
        } else {
          // this.$refs.cboSerial.setDisable(false)
          await this.Load_DS_VTTU()
        }
      } catch (error) {
        console.error(error)
        this.$root.$toast.error('Xảy ra lỗi khi thực hiện cấp vật tư: ' + error.message)
      } finally {
        this.loading(false)
      }
    },
    async LoadCbo () {
      // CBO Kieu TBI
      await this.CSS_KIEU_TBI()
      // Cbo Ly do
      let dsLydo = !this.kh_boithuong ? await this.SP_LAY_DS_MUCDICH_LYDO('7,8') : await this.SP_LAY_DS_MUCDICH_LYDO('7,8,19')
      if (dsLydo.length > 0) {
        this.DmLyDoCap.data = dsLydo
        this.DmLyDoCap.selectLyDoCap = dsLydo[0].mucdich_id
      }
      // Cbo kieu Trangbi
      await this.CSS_TRANGBI()
      // Cbo Muc dich cap
      let dsMDCap = await this.SP_LAY_DS_MUCDICH_LYDO('6,5')
      let dsMDCapIds = dsMDCap.map(item => item.mucdich_id)
      let dsLHMD = this.GetData(await CapVatTuTBAPI.SP_LAY_DS_LOAIHD_MD(this.axios))
      let filterMDC = dsLHMD.filter(item => dsMDCapIds.includes(item.mucdich_id))
      let dsMDS = filterMDC.filter(item => item.loaihd_id == this.loaihd_id)
      let newIds = dsMDS.map(item => item.mucdich_id)
      let source = dsMDCap.filter(item => newIds.includes(item.mucdich_id))
      source = source.filter(item => item.nhom_md)
      if (dsMDS.length > 0) {
        this.DmMucDichCap.data = source
        this.DmMucDichCap.selectMucDichCap = source[0].mucdich_id
      } else {
        this.DmMucDichCap.data = []
        this.DmMucDichCap.selectMucDichCap = null
      }
      if (this.tagfrm != '3') {
        let dsNV = await this.LAY_DS_NV_GIAO_NHAN()
        if (dsNV.length > 0) {
          this.cboNhanVien.list = dsNV.map(item => {
            return {
              id: item.nhanvien_id,
              text: item.ten_nv
            }
          })
          let nhanvien = dsNV.find(item => item.nhanvien_id == this.nhanvienId.toString())
          if (nhanvien != null) {
            this.cboNhanVien.value = nhanvien.nhanvien_id
          } else {
            this.cboNhanVien.value = this.cboNhanVien.list[0].id
          }
        }
      }
      // Nạp combobox vật tư
      if (this.tagfrm == '3') {
        this.setVisibleButton('tsbtnAutoCap', false)
      }
      this.dtVatTu = await this.SP_GETDANHMUCVT_DVT_ALL()
      this.dtVatTu = this.dtVatTu.filter(item => item.vattu_id != null)
      let dsVatTu = this.dtVatTu
      // chi show vattu co hien thi = 1
      dsVatTu = dsVatTu.filter(item => item.hienthi == '1')
      this.cboVatTu.list = dsVatTu.map(item => {
        return {
          id: item.vattu_id,
          text: item.ten_vt
        }
      })
      if (this.loaihd_id == LoaiHopDong.CHAMDUT_SD && (this.tagfrm == '2' || this.tagfrm == '3')) {
        this.cboKieuTbi.value = 3
        if (this.tagfrm == '3') {
          this.cboKieuTbi.disabled = true
        }
      }
      await this.SP_LAY_TK_DOKIEM()
    },
    LoadGridVT: async function () {
      try {
        this.loading(true)
        if (this.tagfrm == '3') {
          this.gridviewVatTu.list = await this.SP_LAY_DS_VATTU_GDV(this.hdtb_id)
        } else {
          let dsLSVT = await this.LAY_DS_PHIEUVT_HDBH(this.phieu_id, this.baohong_id,
            this.hdtb_id, 0, 0)
          if (this.kieu_f == 1 && dsLSVT.length > 0) {
            if (this.cboKieuTbi.value.toString() == '1') {
              let q = dsLSVT.filter(item => item.kieutbi_id == '1')
              if (q.length > 0) {
                this.gridviewVatTu.list = q
              }
            } else if (this.cboKieuTbi.value.toString() == '3') {
              let q = dsLSVT.filter(item => item.kieutbi_id == '3')
              if (q.length > 0) {
                this.gridviewVatTu.list = q
              }
            }
          } else {
            this.gridviewVatTu.list = dsLSVT
          }
        }
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Có lỗi khi hiển thị GridVT: ' + error.message)
      } finally {
        this.loading(false)
      }
    },
    showPopupVatTuImage (args) {
      console.log(args)
      this.phieuvt_id = args.phieuvt_id
      this.$refs.popupVatTuImage.showModal()
    },
    async LoadGrid_VatTuSD () {
      try {
        this.loading(true)
        let ds = await this.LAY_DS_VATTU_SD(this.thuebao_id)
        if (ds.length > 0) {
          this.grvDS_VT_Giao.list = ds
        } else {
          this.grvDS_VT_Giao.list = []
        }
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Không load được danh sách vật tư sử dụng: ' + error.message)
      } finally {
        this.loading(false)
      }
    },
    ThietLap () {
      if (this.kieu_f == 1) {
        this.cboKieuTbi.disabled = true
        this.cboNhanVien.disabled = true
        this.cboKho.disabled = true
        this.setVisibleButton('tsbtnXoa', false)
        this.setVisibleButton('tsbtnGhiLai', true)
        this.$refs.cboSerial.setDisable(true)
      } else if (this.kieu_f == 2) {
        this.cboKieuTbi.disabled = false
        this.cboNhanVien.disabled = false
        this.cboKho.disabled = false
        this.setVisibleButton('tsbtnXoa', true)
        this.setVisibleButton('tsbtnGhiLai', false)
        this.$refs.cboSerial.setDisable(false)
      } else if (this.kieu_f == 10) {
        // Menu dich chuyen goi., chi hien thi boi thuong khach hang
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
      let ds = await this.LAY_DS_TUVT_LHD_V2()
      if (ds.length > 0) {
        this.DmVTGiao.data = ds.map(item => {
          return {
            ...item,
            sl_ton: Number(item.sl_ton),
            dongia: Number(item.dongia),
            tien: Number(item.tien)
          }
        })
      } else {
        this.DmVTGiao.data = []
      }
    },
    CAPNHAT_NGAY_CAP () {
      this.UPDATE_PVT_KT()
    },
    async CAPNHAT_PHIEUVT_THUHOI () {
      try {
        this.loading(true)
        if (this.KiemTraDuLieu_Th()) {
          if (this.tagfrm != '3') {
            let vnhvIdTemp = this.nhanvienId
            if (this.cboNhanVien.value.toString() != vnhvIdTemp.toString()) {
              vnhvIdTemp = this.cboNhanVien.value
            }
            // Kiểm tra thông tin
            let serial = this.$refs.cboSerial.getInputText().trim()
            let dt = await this.SP_KIEMTRATB_SDVT(serial)
            let phieuVtId = this.phvt_id
            let kieu = 3
            let tbiId = 0
            // Kiểm tra dữ liệu
            if (this.loaitbi_id == 2) {
              if (serial != '') {
                let dtTbiDaCap = await this.SP_LAY_DS_THIETBI_DACAP_SERIAL(serial, 0)
                if (dtTbiDaCap.length > 0 && this.getActiveButton('tsbtnNhapMoi')) {
                  this.$root.$toast.warning('Serial ' + serial + ' đã được cấp vào  ngày ' +
                    dtTbiDaCap[0].ngay_cap + ' bởi user ' +
                    dtTbiDaCap[0].nguoi_cn + '')
                  return
                }
                if (!this.getActiveButton('tsbtnNhapMoi')) {
                  if (dt.length > 0) { // Nếu có
                    let ktvtth = await this.FN_KIEMTRA_VT_THUHOI(this.hdtb_id, this.baohong_id, serial.toUpperCase())
                    if (ktvtth != '1') {
                      this.$root.$toast.error(ktvtth)
                      return
                    }
                    if (dt[0].thuebao_id.toString() != this.thuebao_id.toString()) {
                      if (dt[0].phieuvt_id != '0') {
                        // Kiem Tra duoc phep sua ko
                        this.$root.$toast.warning('Serial ' + serial +
                          ' đã được sử dụng bởi thuê bao ' +
                          dt[0].ma_tb +
                          '. Bạn không có quyền được thu hồi')
                      }
                      let accept = false
                      await this.$confirm('Serial ' + serial +
                        ' đã được sử dụng bởi thuê bao ' +
                        dt[0].ma_tb +
                        '. Bạn có chắc chắn muốn tiếp tục thu hồi không?', 'Thông báo', {
                        confirmButtonText: 'Có',
                        cancelButtonText: 'Không'
                      }).then(async () => {
                        accept = true
                      }).catch(() => {
                        accept = false
                      })
                      if (!accept) {
                        return
                      }
                    }
                    let dtTbi = await this.SP_GETTHIETBI(serial)
                    if (dtTbi.length > 0 && dtTbi[0].sl_ton == '1' && dtTbi.tonkho == '1') {
                      this.$root.$toast.warning('Serial này đang ở kho' + dtTbi[0].t)
                      return
                    }
                    kieu = 1
                  }
                } else {
                  let ktvtth = await this.FN_KIEMTRA_VT_THUHOI(this.hdtb_id, this.baohong_id, serial.toUpperCase())
                  if (ktvtth != '1') {
                    this.$root.$toast.error(ktvtth)
                    return
                  }
                  let dtTbi = await this.SP_GETTHIETBI(serial.toUpperCase())
                  if ((dtTbi.length > 0 && dtTbi[0].sl_ton == '0' && dtTbi[0].tonkho == '1') ||
                    (dtTbi.length > 0 && dtTbi[0].tonkho == '0')) {
                    // neu ton tai trong thiet bi
                    kieu = 2
                    tbiId = Number.parseInt(dtTbi[0].thietbi_id)
                  } else if (dtTbi.length > 0 && dtTbi[0].sl_ton == '1' && dtTbi[0].tonkho == '1') {
                    this.$root.$toast.warning('Serial này đang ở kho ' + dtTbi[0].t)
                    return
                  } else {
                    kieu = 3
                    let accept = false
                    await this.$confirm('Không tồn tại serial trong hệ thống. Bạn có muốn tiếp tục?', 'Thông báo', {
                      confirmButtonText: 'Có',
                      cancelButtonText: 'Không'
                    }).then(async () => {
                      accept = true
                    }).catch(() => {
                      accept = false
                    })
                    if (!accept) {
                      return
                    }
                  }
                }
              } else if (this.vsdvt_id > 0) {
                console.log(22222222)
                let dtTbiDaCap = await this.SP_LAY_DS_THIETBI_DACAP_SERIAL(serial.toUpperCase(), this.vsdvt_id)
                if (dtTbiDaCap.length > 0 && this.actions[this.actions.findIndex(item => item.id == 'tsbtnNhapMoi')].active == false) {
                  this.$root.$toast.warning('Serial ' + serial + ' đã được cấp vào  ngày ' + dtTbiDaCap[0].ngay_cap + ' bởi user ' + dtTbiDaCap[0].nguoi_cn)
                  return
                }
              }
            }
            if (this.loaitbi_id == 3) {
              // Loại thiết bị là 3
              if (this.vsdvt_id > 0) {
                kieu = 1
                let ktsl = await this.SP_GETSL_DD(this.vsdvt_id)
                if (ktsl.length > 0) {
                  if (ktsl[0].sum_so_luong != '0') {
                    let slTreo = Number.parseInt(ktsl[0].sum_so_luong)
                    if (this.sl_goc - slTreo <= this.formThongTinThietBi.soluong || this.sl_goc - slTreo <= 0) {
                      this.$root.$toast.warning('Hiện số lượng cấp thu hồi của thuê chưa QT là ' + slTreo + '/' + this.sl_goc + '. Bạn hãy kiểm tra lại số lượng thu hồi')
                      return
                    }
                  }
                }
                if (!this.getActiveButton('tsbtnNhapMoi')) {
                  let output = await this.CAPNHAT_PHIEUVT_THUHOI_API(this.vsdvt_id, 1, 0, this.hdtb_id, this.baohong_id,
                    this.phieu_id, this.thuebao_id, this.donviId, vnhvIdTemp, this.$root.token.getUserName(),
                    this.maycn, this.ipcn, this.cboTinhTrang.value,
                    'CUONG1094', moment(new Date()).format('DD/MM/YYYY HH:mm:ss'),
                    this.cboVatTu.value, tbiId, Number(this.formThongTinThietBi.soluong), this.DmMucDichCap.selectMucDichCap, this.DmLyDoCap.selectLyDoCap,
                    this.cboKieuTrangBi.value, kieu, this.formThongTinThietBi.txtGhiChu)
                  if (output) {
                    this.$root.$toast.success('Thu hồi thành công')
                  }
                } else {
                  let accepted1 = false
                  await this.$confirm('Bạn có muốn cập nhật dữ liệu không?', 'Thông báo', {
                    confirmButtonText: 'Có',
                    cancelButtonText: 'Không'
                  }).then(() => {
                    accepted1 = true
                  }).catch(() => {
                    accepted1 = false
                  })
                  if (accepted1) {
                    let output = await this.CAPNHAT_PHIEUVT_THUHOI_API(this.vsdvt_id, 2, phieuVtId, this.hdtb_id, this.baohong_id,
                      this.phieu_id, this.thuebao_id, this.donviId, vnhvIdTemp, this.$root.token.getUserName(),
                      this.maycn, this.ipcn, this.cboTinhTrang.value,
                      'CUONG1094', moment(new Date()).format('DD/MM/YYYY HH:mm:ss'),
                      this.cboVatTu.value, tbiId, Number(this.formThongTinThietBi.soluong), this.DmMucDichCap.selectMucDichCap, this.DmLyDoCap.selectLyDoCap,
                      this.cboKieuTrangBi.value, kieu, this.formThongTinThietBi.txtGhiChu)
                    if (output) {
                      this.$root.$toast.success('Cập nhật dữ liệu thành công')
                    }
                  } else {
                    return
                  }
                }
                let vattu = this.gridviewVatTu.list[this.gridviewVatTu.list.findIndex(item => item.phieuvt_id == phieuVtId.toString())]
                await this.LoadDS(vattu)
              }
              kieu = 3
              if (!this.getActiveButton('tsbtnNhapMoi')) {
                let output = await this.CAPNHAT_PHIEUVT_THUHOI_API(this.vsdvt_id, 1, 0, this.hdtb_id, this.baohong_id,
                  this.phieu_id, this.thuebao_id, this.donviId, vnhvIdTemp, this.$root.token.getUserName(),
                  this.maycn, this.ipcn, this.cboTinhTrang.value,
                  'CUONG1094', moment(new Date()).format('DD/MM/YYYY HH:mm:ss'),
                  this.cboVatTu.value, tbiId, Number(this.formThongTinThietBi.soluong), this.DmMucDichCap.selectMucDichCap, this.DmLyDoCap.selectLyDoCap,
                  this.cboKieuTrangBi.value, kieu, this.formThongTinThietBi.txtGhiChu)
                if (output) {
                  this.$root.$toast.success('Thu hồi thành công')
                }
              } else {
                this.$confirm('Bạn có muốn cập nhật dữ liệu không?', 'Thông báo', {
                  confirmButtonText: 'Có',
                  cancelButtonText: 'Không'
                }).then(async () => {
                  let output = await this.CAPNHAT_PHIEUVT_THUHOI_API(this.vsdvt_id, 2, phieuVtId, this.hdtb_id, this.baohong_id,
                    this.phieu_id, this.thuebao_id, this.donviId, vnhvIdTemp, this.$root.token.getUserName(),
                    this.maycn, this.ipcn, this.cboTinhTrang.value,
                    'CUONG1094', moment(new Date()).format('DD/MM/YYYY HH:mm:ss'),
                    this.cboVatTu.value, tbiId, Number(this.formThongTinThietBi.soluong), this.DmMucDichCap.selectMucDichCap, this.DmLyDoCap.selectLyDoCap,
                    this.cboKieuTrangBi.value, kieu, this.formThongTinThietBi.txtGhiChu)
                  if (output) {
                    this.$root.$toast.success('Cập nhật dữ liệu thành công')
                  }
                })
              }
              let vattu = this.gridviewVatTu.list[this.gridviewVatTu.list.findIndex(item => item.phieuvt_id == phieuVtId.toString())]
              await this.LoadDS(vattu)
              return
            }
            if (this.loaitbi_id != 3 && this.loaitbi_id != 2) {
              // Loại thiết thường
              if (this.vsdvt_id > 0) {
                kieu = 1
                let ktsl = await this.SP_GETSL_DD(this.vsdvt_id)
                if (ktsl.length > 0) {
                  if (ktsl[0].sum_so_luong != '0') {
                    let sltreo = Number.parseInt(ktsl.sum_so_luong)
                    if (Number(this.sl_goc) - sltreo <= Number(this.formThongTinThietBi.soluong) || Number(this.sl_goc) - sltreo <= 0) {
                      this.$root.$toast.warning('Hiện số lượng cấp thu hồi của thuê chưa QT là ' + sltreo + '/' + this.sl_goc + '. Bạn hãy kiểm tra lại số lượng thu hồi')
                      return
                    }
                  }
                }
                // Insert PVT
                if (!this.getActiveButton('tsbtnNhapMoi')) {
                  let output = await this.CAPNHAT_PHIEUVT_THUHOI_API(this.vsdvt_id, 1, 0, this.hdtb_id, this.baohong_id,
                    this.phieu_id, this.thuebao_id, this.donviId, vnhvIdTemp, this.$root.token.getUserName(),
                    this.maycn, this.ipcn, this.cboTinhTrang.value,
                    'DAYTB', moment(new Date()).format('DD/MM/YYYY HH:mm:ss'),
                    this.cboVatTu.value, tbiId, Number(this.formThongTinThietBi.soluong), this.DmMucDichCap.selectMucDichCap, this.DmLyDoCap.selectLyDoCap,
                    this.cboKieuTrangBi.value, kieu, this.formThongTinThietBi.txtGhiChu)
                  if (output) {
                    this.$root.$toast.success('Thu hồi thành công')
                  }
                } else {
                  let accepted = false
                  await this.$confirm('Bạn có muốn cập nhật dữ liệu không?', 'Thông báo', {
                    confirmButtonText: 'Có',
                    cancelButtonText: 'Không'
                  }).then(() => {
                    accepted = true
                  }).catch(() => {
                    accepted = false
                  })
                  if (accepted) {
                    let output = await this.CAPNHAT_PHIEUVT_THUHOI_API(this.vsdvt_id, 2, phieuVtId, this.hdtb_id, this.baohong_id,
                      this.phieu_id, this.thuebao_id, this.donviId, vnhvIdTemp, this.$root.token.getUserName(),
                      this.maycn, this.ipcn, this.cboTinhTrang.value,
                      'DAYTB', moment(new Date()).format('DD/MM/YYYY HH:mm:ss'),
                      this.cboVatTu.value, tbiId, Number(this.formThongTinThietBi.soluong), this.DmMucDichCap.selectMucDichCap, this.DmLyDoCap.selectLyDoCap,
                      this.cboKieuTrangBi.value, kieu, this.formThongTinThietBi.txtGhiChu)
                    if (output) {
                      this.$root.$toast.success('Cập nhật dữ liệu thành công')
                    }
                  }
                }
              } else {
                kieu = 1
                if (!this.getActiveButton('tsbtnNhapMoi')) {
                  let output = await this.CAPNHAT_PHIEUVT_THUHOI_API(this.vsdvt_id, 1, 0, this.hdtb_id, this.baohong_id,
                    this.phieu_id, this.thuebao_id, this.donviId, vnhvIdTemp, this.$root.token.getUserName(),
                    this.maycn, this.ipcn, this.cboTinhTrang.value,
                    'DAYTB', moment(new Date()).format('DD/MM/YYYY HH:mm:ss'),
                    this.cboVatTu.value, tbiId, Number(this.formThongTinThietBi.soluong), this.DmMucDichCap.selectMucDichCap, this.DmLyDoCap.selectLyDoCap,
                    this.cboKieuTrangBi.value, kieu, this.formThongTinThietBi.txtGhiChu)
                  if (output) {
                    this.$root.$toast.success('Cập nhật dữ liệu thành công')
                  }
                } else {
                  let accepted = false
                  await this.$confirm('Bạn có muốn cập nhật dữ liệu không?', 'Thông báo', {
                    confirmButtonText: 'Có',
                    cancelButtonText: 'Không'
                  }).then(() => {
                    accepted = true
                  }).catch(() => {
                    accepted = false
                  })
                  if (accepted) {
                    let output = await this.CAPNHAT_PHIEUVT_THUHOI_API(this.vsdvt_id, 2, phieuVtId, this.hdtb_id, this.baohong_id,
                      this.phieu_id, this.thuebao_id, this.donviId, vnhvIdTemp, this.$root.token.getUserName(),
                      this.maycn, this.ipcn, this.cboTinhTrang.value,
                      'DAYTB', moment(new Date()).format('DD/MM/YYYY HH:mm:ss'),
                      this.cboVatTu.value, tbiId, Number(this.formThongTinThietBi.soluong), this.DmMucDichCap.selectMucDichCap, this.DmLyDoCap.selectLyDoCap,
                      this.cboKieuTrangBi.value, kieu, this.formThongTinThietBi.txtGhiChu)
                    if (output) {
                      this.$root.$toast.success('Thu hồi thành công')
                    }
                  } else {
                    return
                  }
                }
              }
              let vattu = this.gridviewVatTu.list[this.gridviewVatTu.list.findIndex(item => item.phieuvt_id == phieuVtId.toString())]
              await this.LoadDS(vattu)
              return
            }
            if (this.getActiveButton('tsbtnNhapMoi')) {
              if (phieuVtId == 0) {
                this.$root.$toast.warning('Chưa có thông tin về phiếu vật tư!')
                return
              }
            }
            let test = false
            await this.$confirm('Bạn có muốn cập nhật dữ liệu không?', 'Thông báo', {
              confirmButtonText: 'Có',
              cancelButtonText: 'Không'
            }).then(() => {
              test = true
            }).catch(() => {
              test = false
            })
            if (test) {
              let seriPop = ''
              if (serial != '') {
                seriPop = serial
              }
              if (!this.getActiveButton('tsbtnNhapMoi')) {
                let output = await this.CAPNHAT_PHIEUVT_THUHOI_API(this.vsdvt_id, 1, 0, this.hdtb_id, this.baohong_id,
                  this.phieu_id, this.thuebao_id, this.donviId, vnhvIdTemp, this.$root.token.getUserName(),
                  this.maycn, this.ipcn, this.cboTinhTrang.value,
                  seriPop, moment(new Date()).format('DD/MM/YYYY HH:mm:ss'),
                  this.cboVatTu.value, tbiId, Number(this.formThongTinThietBi.soluong), this.DmMucDichCap.selectMucDichCap, this.DmLyDoCap.selectLyDoCap,
                  this.cboKieuTrangBi.value, kieu, this.formThongTinThietBi.txtGhiChu)
                if (output) {
                  this.$root.$toast.success('Thu hồi thành công')
                  let vattu = this.gridviewVatTu.list[this.gridviewVatTu.list.findIndex(item => item.phieuvt_id == phieuVtId.toString())]
                  await this.LoadDS(vattu)
                }
              } else {
                let output = await this.CAPNHAT_PHIEUVT_THUHOI_API(this.vsdvt_id, 2, phieuVtId, this.hdtb_id, this.baohong_id,
                  this.phieu_id, this.thuebao_id, this.donviId, vnhvIdTemp, this.$root.token.getUserName(),
                  this.maycn, this.ipcn, this.cboTinhTrang.value,
                  seriPop, moment(new Date()).format('DD/MM/YYYY HH:mm:ss'),
                  this.cboVatTu.value, tbiId, Number(this.formThongTinThietBi.soluong), this.DmMucDichCap.selectMucDichCap, this.DmLyDoCap.selectLyDoCap,
                  this.cboKieuTrangBi.value, kieu, this.formThongTinThietBi.txtGhiChu)
                if (output) {
                  this.$root.$toast.success('Cập nhật dữ liệu thành công')
                }
              }
              let vattu = this.gridviewVatTu.list[this.gridviewVatTu.list.findIndex(item => item.phieuvt_id == phieuVtId.toString())]
              await this.LoadDS(vattu)
            }
          } else {
            // Nhập vào tạm thu
            let serial = this.$refs.cboSerial.getInputText().trim()
            if (!this.getActiveButton('tsbtnNhapMoi')) {
              if (this.loaitbi_id != 2) {
                this.$root.$toast.warning('Hiện tại đang áp dụng thu thiết bị có serial. Chưa áp dụng với vật tư khác')
                return
              }
              // Kiểm tra xem có 2 serial trùng nhau trong 1 HĐ không chặn
              if (this.loaitbi_id == 2) {
                let ds = await this.SP_KIEMTRA_TAMTHU_THEO_SERIAL(serial, this.hdtb_id)
                if (ds.length > 0 && ds[0].so_luong != '0') {
                  this.$root.$toast.warning(serial + ' được được nhập không thể nhập tiếp')
                  return
                }
              }
              if (this.loaitbi_id == 2) {
                // Nếu là serial
                const request = {
                  'vphieu_id': this.phieu_id.toString(),
                  'vngay_cap': this.formThongTinThietBi.dtpNgayCap,
                  'vkieutbi_id': this.cboKieuTbi.value.toString(),
                  'vtrangbi_id': this.cboKieuTrangBi.value.toString(),
                  'vvattu_id': this.cboVatTu.value.toString(),
                  'vsoluong': '1',
                  'vserial': serial,
                  'vma_tbi': '',
                  'vghichu': this.formThongTinThietBi.txtGhiChu,
                  'vnhanvien_id': this.nhanvienId.toString(),
                  'vdonvi_id': this.donviId.toString(),
                  'vnguoi_cn': this.$root.token.getUserName(),
                  'vmay_cn': this.maycn,
                  'vip_cn': this.ipcn,
                  'vhdtb_id': this.hdtb_id.toString(),
                  'vmucdich_id': this.DmLyDoCap.selectLyDoCap.toString(),
                  'vlydothu_id': this.DmMucDichCap.selectMucDichCap ? this.DmMucDichCap.selectMucDichCap : '0',
                  'vtinhtrangtbi_id': this.cboTinhTrang.value.toString()
                }
                let req = {p_js_tamthu_vt: JSON.stringify(request)}
                await this.SP_INSERT_TAMTHU_VT(req)
                await this.LoadGridVT()
                await this.LoadGrid_VatTuSD()
              }
            } else {
              this.$root.$toast.warning('Không được sửa vật tư. Bạn hãy xóa đi nhập lại')
            }
          }
        }
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Có lỗi:' + error.message)
      } finally {
        this.loading(false)
      }
    },
    async LoadDS (currentVatTu) {
      if (currentVatTu != null) {
        this.gridviewVatTu.selectItem = currentVatTu
      }
      await this.LoadGridVT()
      await this.LoadGrid_VatTuSD()
    },
    KiemTraDuLieu_Th () {
      if (this.tagfrm == '2' && this.loaitb_id != LoaiHopDong.CHAMDUT_SD) {
        this.$root.$toast.error('Chức năng thu hồi dành cho GDV chưa được xây dựng.')
        return false
      }
      this.loaitbi_id = 0
      if (this.DmLyDoCap.selectLyDoCap == null) {
        this.$root.$toast.warning('Bạn phải chọn lý do')
        return false
      }
      if (this.cboVatTu.value == 0) {
        this.$root.$toast.warning('Bạn phải chọn vật tư')
        return false
      }
      if (this.formThongTinThietBi.soluong == 0) {
        this.$root.$toast.warning('Bạn phải nhập số lượng')
        return false
      }
      if (this.cboTinhTrang.value == 0) {
        this.$root.$toast.warning('Bạn chưa chọn tình trạng thiết bị')
        return false
      }
      let itemVattu = this.dtVatTu.find(item => item.vattu_id.toString() == this.cboVatTu.value.toString())
      let serialText = this.$refs.cboSerial.getInputText()
      console.log('itemVattu', itemVattu)
      console.log('Serial text: ', serialText)
      if (itemVattu != null) {
        if (itemVattu.loaitbi_id == '2') {
          this.loaitbi_id = 2
          if (serialText == '') {
            this.$root.$toast.warning('Loại vật tư này thuộc loại phải có seri. Ban hãy nhập serial cho thiết bị')
            this.$refs.cboSerial.$refs.inputText.focus()
            return false
          }
          if (serialText != '') {
            if (this.formThongTinThietBi.soluong != 1) {
              this.$root.$toast.warning('Vật tư này có seri, số lượng phải nhập = 1')
              this.$refs.txtSoLuong.focus();
              return;
            }
            let reg = new RegExp(this.check_serial)
            console.log('check_serial: ', this.check_serial)
            console.log(reg.test(serialText))
            if (this.check_serial !== '0' && !reg.test(serialText)) {
              this.$root.$toast.warning('Serial có ký tự không hợp lệ. Các ký tự cho phép [A-Z,0-9]')
              return false
            }
          }
        } else if (itemVattu.loaitbi_id.toString().trim() == '3') {
          this.loaitbi_id = 3
          return true
        } else {
          if (serialText.trim() != '') {
            this.$root.$toast.warning('Loại vật tư này thuộc loại phải không có seri. Ban hãy xóa dữ liệu serial')
            this.$refs.cboSerial.$refs.inputText.focus()
            return false
          }
        }
      }
      return true
    },
    async FN_KIEMTRA_VTCAPRA_V2 (kieu, tempHDBHId, kieucap, tuvtId, phieuId) {
      let result = ''
      await CapVatTuTBAPI.FN_KIEMTRA_VTCAPRA_V2(this.axios, {
        'p_kieu': kieu.toString(),
        'p_temp_hdbh_id': tempHDBHId.toString(),
        'p_kieucap': kieucap.toString(),
        'p_tuvt_id': tuvtId.toString(),
        'p_phieu_id': phieuId.toString()
      }).then(response => {
        if (response && response.data && response.data.error_code == 'BSS-00000000' && response.data.data) {
          result = response.data.data
        } else {
          if (response.data.message_detail.includes('ORA-00904')) {
            this.$root.$toast.warning('Hàm KIEMTRA_VTCAPRA không được tìm thấy. Bạn hãy liên hệ admin')
          }
        }
      })
      return result
    },
    async SP_KIEMTRA_DAY (phieuId, hdtbId, baohongId, tuVTID) {
      let result = []
      await CapVatTuTBAPI.SP_KIEMTRA_DAY(this.axios, {
        'p_phieu_id': phieuId.toString(),
        'p_hdtb_id': hdtbId.toString(),
        'p_baohong_id': baohongId.toString(),
        'p_tuvt_id': tuVTID.toString()
      }).then(response => {
        if (response && response.data && response.data.error_code == 'BSS-00000000' && response.data.data) {
          result = response.data.data
        }
      })
      return result
    },
    async FN_KIEMTRA_DM_VT (hdtbId, baohongId, vattuId, vsl, khonhanId) {
      let result = ''
      await CapVatTuTBAPI.FN_KIEMTRA_DM_VT(this.axios, {
        'p_hdtb_id': hdtbId.toString(),
        'p_baohong_id': baohongId.toString(),
        'p_vattu_id': vattuId.toString(),
        'p_sl': vsl.toString(),
        'p_kho_nhan_id': khonhanId.toString()
      }).then(response => {
        if (response && response.data && response.data.error_code == 'BSS-00000000' && response.data.data) {
          result = response.data.data
        }
      })
      return result
    },
    async DIEUKIEN_CAP_VT (hdtbId, loaiHDId, loaiTBId, thietbiId) {
      let result = ''
      await CapVatTuTBAPI.DIEUKIEN_CAP_VT(this.axios, {
        'vhdtb_id': hdtbId.toString(),
        'vloaihd_id': loaiHDId.toString(),
        'vloaitb_id': loaiTBId.toString(),
        'vthietbi_id': thietbiId.toString()
      }).then(response => {
        if (response && response.data && response.data.error_code == 'BSS-00000000' && response.data.data) {
          result = response.data.data
        }
      })
      return result
    },
    async CAPNHAT_SUDUNG_VATTU (hdtbId, baohongId, phieuId) {
      await CapVatTuTBAPI.CAPNHAT_SUDUNG_VATTU(this.axios, {
        'vhdtb_id': hdtbId.toString(),
        'vbaohong_id': baohongId.toString(),
        'vphieu_id': phieuId.toString(),
        'vmay_cn': 'admin',
        'vnguoi_cn': 'admin'
      }).then(response => {
        console.log(response)
      })
    },
    async UPDATE_PVT_KT () {
      let request = {
        'vphieuvt_id': this.phvt_id,
        'vngay_cap': this.formThongTinThietBi.dtpNgayCap
      }
      await CapVatTuTBAPI.UPDATE_PVT_KT(this.axios, request).then(response => {
        if (response && response.data && response.data.error_code == 'BSS-00000000' && response.data.data) {
          if (response.data.data == 'OK') {
            this.$root.$toast.success('Cập nhật thông tin vật tư thành công')
            this.xacnhan = true
            this.LoadGridVT()
          } else {
            this.$root.$toast.warning(response.data.data)
          }
        }
      }).catch(err => {
        console.log(err)
        this.$root.$toast.error('Có lỗi khi cập nhật ngày cấp cho vật tư:' + err.data.message)
      })
    },
    async SP_KIEMTRATB_SDVT (pSerial) {
      return this.GetData(await CapVatTuTBAPI.SP_KIEMTRATB_SDVT(this.axios, {
        'p_serial': pSerial
      }))
    },
    async SP_LAY_DS_THIETBI_DACAP_SERIAL (pSerial, sdVTId) {
      return this.GetData(await CapVatTuTBAPI.SP_LAY_DS_THIETBI_DACAP_SERIAL(this.axios, {
        'p_serial': pSerial,
        'p_sdvt_id': sdVTId.toString()
      }))
    },
    async FN_KIEMTRA_VT_THUHOI (hdtbId, baohongId, serial) {
      return this.GetData(await CapVatTuTBAPI.FN_KIEMTRA_VT_THUHOI(this.axios, {
        'p_hdtb_id': hdtbId.toString(),
        'p_baohong_id': baohongId.toString(),
        'p_serial': serial
      }))
    },
    async SP_GETTHIETBI (pSerial) {
      return this.GetData(await CapVatTuTBAPI.SP_GETTHIETBI(this.axios, {
        'p_serial': pSerial
      }))
    },
    async SP_GETSL_DD (pSdvtId) {
      return this.GetData(await CapVatTuTBAPI.SP_GETSL_DD(this.axios, {
        'p_sdvt_id': pSdvtId.toString()
      }))
    },
    async CAPNHAT_PHIEUVT_THUHOI_API (vsdvtId, vinsert, vphieuvtId, vhdtbId, vbaohongId,
      vphieuId, vthuebaoId, vdonviId, vnhanvienId, vnguoiCn,
      vmayCn, vipCn, vtinhtrangtbiId, vserial, vngayTh, vvattuId,
      vthietbiId, vsoluong, vlydocapId, vlydocaptbiId, vtrangbiId,
      vkieu, vghichu) {
      let output = false
      const request = {
        'vsdvt_id': vsdvtId.toString(),
        'vinsert': vinsert.toString(),
        'vphieuvt_id': vphieuvtId.toString(),
        'vhdtb_id': vhdtbId.toString(),
        'vbaohong_id': vbaohongId.toString(),
        'vphieu_id': vphieuId.toString(),
        'vthuebao_id': vthuebaoId.toString(),
        'vdonvi_id': vdonviId.toString(),
        'vnhanvien_id': vnhanvienId.toString(),
        'vnguoi_cn': vnguoiCn,
        'vmay_cn': vmayCn,
        'vip_cn': vipCn,
        'vtinhtrangtbi_id': vtinhtrangtbiId.toString(),
        'vserial': vserial,
        'vngay_th': vngayTh,
        'vvattu_id': vvattuId.toString(),
        'vthietbi_id': vthietbiId.toString(),
        'vsoluong': vsoluong.toString(),
        'vlydocap_id': vlydocapId ? vlydocapId.toString() : '0',
        'vlydocaptbi_id': vlydocaptbiId.toString(),
        'vtrangbi_id': vtrangbiId.toString(),
        'vkieu': vkieu.toString(),
        'vghichu': vghichu
      }
      await CapVatTuTBAPI.CAPNHAT_PHIEUVT_THUHOI(this.axios, request).then(response => {
        console.log(response)
        if (response && response.data && response.data.error_code == 'BSS-00000000') {
          output = true
        } else {
          this.$root.$toast.error('Cập nhật phiếu vật tư thu hồi thất bại: ' + response.data.message_detail)
        }
      }).catch(error => {
        console.log(error)
        this.$root.$toast.error('Có lỗi: ' + error.data.message_detail)
      })
      return output
    },
    async SP_KIEMTRA_TAMTHU_THEO_SERIAL (serial, hdtbId) {
      return this.GetData(await this.GetData(CapVatTuTBAPI.SP_KIEMTRA_TAMTHU_THEO_SERIAL(this.axios, {
        'p_serial': serial,
        'p_hdtb_id': hdtbId.toString()
      })))
    },
    async SP_INSERT_TAMTHU_VT (request) {
      await CapVatTuTBAPI.SP_INSERT_TAMTHU_VT(this.axios, request).then(response => {
        if (response && response.data && response.data.error_code == 'BSS-00000000') {
          this.$root.$toast.success('Thu hồi vật tư thành công')
        } else {
          this.$root.$toast.error('Thu hồi vật tư thất bại: ' + response.data.message_detail)
        }
      }).catch(error => {
        console.log(error)
        this.$root.$toast.error('Có lỗi: ' + error.data.message_detail)
      })
    },
    async LAY_DS_KHO_THEO_NV (nhanvienId, donviId) {
      let data = this.GetData(await CapVatTuTBAPI.LAY_DS_KHO_THEO_NV(this.axios, {
        'p_nhanvien_id': nhanvienId.toString(),
        'p_donvi_id': donviId.toString()
      }))
      if (data.length > 0) {
        this.cboKho.list = data.map(item => {
          return {
            id: item.kho_id,
            text: item.ten_k_new
          }
        })
        this.cboKho.value = this.cboKho.list[0].id
      } else {
        this.cboKho.list = []
        this.cboKho.value = 0
      }
    },
    async LAY_DS_TUVT_LHD_V2 () {
      const request = {
        'kho_id': Number.parseInt(this.cboKho.value),
        'loaitb_id': this.loaitb_id,
        'loaihd_id': this.loaihd_id
      }
      return this.GetData(await CapVatTuTBAPI.LAY_DS_TUVT_LHD_V2(this.axios, request))
    },
    async LAY_DS_VATTU_SD (thuebaoId) {
      return this.GetData(await CapVatTuTBAPI.LAY_DS_VATTU_SD(this.axios, {
        'vthuebao_id': thuebaoId
      }))
    },
    async LAY_DS_PHIEUVT_HDBH (phieuId, baohongId, hdtbId, kieutbi, status) {
      let req = {
        'phieu_id': phieuId.toString(),
        'baohong_id': baohongId.toString(),
        'hdtb_id': hdtbId.toString(),
        'kieutbi': kieutbi.toString(),
        'status': status.toString()
      }
      return this.GetData(await CapVatTuTBAPI.SP_LAY_DS_PHIEUVT_HDBH(this.axios, req))
    },
    async SP_LAY_DS_THAMSO_MD (kieuId) {
      let data = this.GetData(await CapVatTuTBAPI.SP_LAY_DS_THAMSO_MD(this.axios, {
        p_kieu_id: kieuId
      }))
      if (data.length > 0) {
        let strVtemp = 'KIEMTRA_KYTU_SERIAL'
        let det = data.filter(item => item.ma_ts == strVtemp)
        if (det.length > 0) {
          let item = det[0]
          if (item.ten_ts.trim() != '0') {
            this.check_serial = item.ten_ts.trim()
          }
        }
        strVtemp = 'CANHBAO_HOAC_LOI'
        det = data.filter(item => item.ma_ts == strVtemp)
        if (det.length > 0) {
          let item = det[0]
          if (item.ten_ts.trim() == '1') {
            this.canhbao_hoac_loi = true
          }
        }
        strVtemp = 'CHOPHEP_SERIAL_THUHOI_THUCONG'
        det = data.filter(item => item.ma_ts == strVtemp)
        if (det.length > 0) {
          let item = det[0]
          if (item.ten_ts.trim() == '1') {
            this.chophep_serial_thuhoi_thucong = true
          }
        }
      }
    },
    async SP_LAY_DS_VATTU_GDV (hdtbId) {
      return this.GetData(await CapVatTuTBAPI.SP_LAY_DS_VATTU_GDV, {
        p_hdtb_id: hdtbId
      })
    },
    async SP_LAY_TK_DOKIEM () {
      let data = this.GetData(await CapVatTuTBAPI.SP_LAY_TK_DOKIEM(this.axios))
      if (data.length > 0) {
        this.vuser_alt = data[0].user_alt
        this.vpass_alt = data[0].pass_alt
        this.vuser_xtest = data[0].user_xtest
        this.vpass_xtest = data[0].pass_xtest
      }
    },
    async SP_GETDANHMUCVT_DVT_ALL () {
      return this.GetData(await CapVatTuTBAPI.SP_GETDANHMUCVT_DVT_ALL(this.axios))
    },
    async LAY_DS_NV_GIAO_NHAN () {
      return this.GetData(await CapVatTuTBAPI.LAY_DS_NV_GIAO_NHAN(this.axios))
    },
    async CSS_KIEU_TBI () {
      let data = this.GetData(await CapVatTuTBAPI.CSS_KIEU_TBI(this.axios))
      if (data.length > 0) {
        this.cboKieuTbi.list = data
        .filter(item => item.KIEUTBI_ID === 1 || item.KIEUTBI_ID === 3)
        .map(item => {
          return {
            id: item.KIEUTBI_ID,
            text: item.KIEU_TBI
          }
        });
        this.cboKieuTbi.value = this.cboKieuTbi.list[0].id
      } else {
        this.cboKieuTbi.list = []
      }
    },
    async SP_LAY_DS_TINHTRANG_TBI () {
      let data = this.GetData(await CapVatTuTBAPI.SP_LAY_DS_TINHTRANG_TBI(this.axios))
      if (data.length > 0) {
        let matinh = this.$root.token.getMaTinh()
        let list = []
        if (matinh == 'KGG') {
          list = data.filter(item => (item.tinhtrangtbi_id == '1' || item.tinhtrangtbi_id == '2'))
        } else if (matinh == 'BDH') {
          list = data.filter(item => item.tinhtrangtbi_id == '1' || item.tinhtrangtbi_id == '2' || item.tinhtrangtbi_id == '3' || item.tinhtrangtbi_id == '4')
        } else {
          list = data.filter(item => item.tinhtrangtbi_id == '1')
        }
        if (list.length > 0) {
          this.cboTinhTrang.list = list.map(item => {
            return {
              id: item.tinhtrangtbi_id,
              text: item.tinhtrang_tbi
            }
          })
          this.cboTinhTrang.value = this.cboTinhTrang.list[0].id
        } else {
          this.cboTinhTrang.list = []
        }
      } else {
        this.cboTinhTrang.list = []
      }
    },
    async SP_LAY_DS_TINHTRANG_TBI_TH () {
      let data = this.GetData(await CapVatTuTBAPI.SP_LAY_DS_TINHTRANG_TBI(this.axios))
      if (data.length > 0) {
        let list = data.filter(item => item.tinhtrangtbi_id == '5' || item.tinhtrangtbi_id == '6')
        if (list.length > 0) {
          this.cboTinhTrang.list = list.map(item => {
            return {
              id: item.tinhtrangtbi_id,
              text: item.tinhtrang_tbi
            }
          })
          this.cboTinhTrang.value = this.cboTinhTrang.list[0].id
        } else {
          this.cboTinhTrang.list = []
        }
      } else {
        this.cboTinhTrang.list = []
      }
    },
    async QLVT_DVI_TINH () {
      return this.GetData(await CapVatTuTBAPI.QLVT_DVI_TINH(this.axios))
    },
    async SP_LAY_DS_MUCDICH_LYDO (chuoi) {
      return this.GetData(await CapVatTuTBAPI.SP_LAY_DS_MUCDICH_LYDO(this.axios, {
        'p_chuoi': chuoi
      }))
    },
    async LAY_DS_PORT_THEOHD_TB (hdtbId, thuebaoId) {
      return this.GetData(await CapVatTuTBAPI.LAY_PORT_THEO_HDTB(this.axios, {
        'vhdtb_id': hdtbId.toString(),
        'vthuebao_id': thuebaoId.toString()
      }))
    },
    async TEST_PORT (request) {
      let result = null
      await CapVatTuTBAPI.TEST_PORT(this.axios, request).then(response => {
        if (response && response.data && response.data.error_code == 'BSS-00000000' && response.data.data) {
          result = response.data.data
        }
      })
      return result
    },
    async GETPORT_QUANLITYV2 (request) {
      let result = null
      await CapVatTuTBAPI.GETPORT_QUANLITYV2(this.axios, request).then(response => {
        if (response && response.data && response.data.error_code == 'BSS-00000000' && response.data.data) {
          result = response.data.data
        }
      })
      return result
    },
    async LAY_THAMSO_MACDINH (mats, nguoidungid) {
      let result = ''
      await CapVatTuTBAPI.LAY_THAMSO_MACDINH(this.axios, {
        'vma_ts': mats,
        'vnguoidung_id': nguoidungid
      }).then(response => {
        if (response && response.data && response.data.error_code == 'BSS-00000000' && response.data.data) {
          result = response.data.data
        }
      })
      return result
    },
    async SP_LAY_DS_TAMUNG_THEO_SERIAL (serial) {
      return this.GetData(await CapVatTuTBAPI.SP_LAY_DS_TAMUNG_THEO_SERIAL(this.axios, {
        'p_nhanvien_id': this.$root.token.getNhanVienID().toString(),
        'p_serial': serial
      }))
    },
    async POST_GET_KEYS (keyname) {
      let key = ''
      await CapVatTuTBAPI.POST_GET_KEYS(this.axios, {
        keyname: keyname
      }).then(response => {
        if (response && response.data && response.data.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
          key = response.data.data
        }
      }).catch(error => {
        this.$root.$toast.error('Có lỗi: ' + error.data.message_detail)
      })
      return key
    },
    async KIEMTRA_VTCAPRA (vkieu, vtemphdbhId, vkieucap, vtuvtId) {
      return this.SendData(await CapVatTuTBAPI.KIEMTRA_VTCAPRA(this.axios, {
        vkieu: vkieu.toString(),
        vtemp_hdbh_id: vtemphdbhId.toString(),
        vkieucap: vkieucap.toString(),
        vtuvt_id: vtuvtId.toString()
      }).catch(error => {
        this.$root.$toast.error('Đẩy dữ liệu không thành công:'  + error.data.message_detail)
      }))
    },
    async INSERT_PHIEU_VATTU_V2 (request) {
      let result = ''
      await CapVatTuTBAPI.INSERT_PHIEU_VATTU_V2(this.axios, request).then(response => {
        if (response && response.data && response.data.error_code == 'BSS-00000000' && response.data.data) {
          result = response.data.data
        }
      })
      return result
    },
    async SP_XOA_PHIEU_VT_CAP_THUHOI (request) {
      let success = false
      await CapVatTuTBAPI.SP_XOA_PHIEU_VT_CAP_THUHOI(this.axios, request).then(response => {
        console.log(response)
        if (response && response.data && response.data.error_code == 'BSS-00000000') {
          success = true
        } else {
          this.$root.$toast.error('Xóa phiếu vật tư thất bại: ' + response.data.message_detail)
        }
      }).catch(error => {
        this.$root.$toast.error('Có lỗi: ' + error.data.message_detail)
      })
      return success
    },
    async CSS_TRANGBI () {
      let data = this.GetData(await CapVatTuTBAPI.CSS_TRANGBI(this.axios))
      if (data.length > 0) {
        this.cboKieuTrangBi.list = data.map(item => {
          return {
            id: item.TRANGBI_ID,
            text: item.TENTRANGBI
          }
        })
        this.cboKieuTrangBi.value = TRANGBI.VIENTHONG_TRANGBI
      } else {
        this.cboKieuTrangBi.list = []
        this.cboKieuTrangBi.value = 0
      }
    },
    GetData: function (response) {
      if (response && response.data && response.data.error_code == 'BSS-00000000') {
        return response.data.data == null ? [] : response.data.data
      } else if (response && response.data && response.data.error == 200 && response.data.error_code == 'BSS-00000500') {
        return response.data.data
      } else if (response && response.data && response.data.error_code == 'BSS-00000204') {
        return []
      } else {
        this.$root.$toast.error('Lấy dữ liệu không thành công')
      }
      return []
    },
    SendData: function (response) {
      if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
        return response.data.data
      } else if (response && response.data && response.data.error_code === 'BSS-00000204') {
        return null
      } else {
        this.$root.$toast.error('Đẩy dữ liệu không thành công:'  + response.data.message_detail)
      }
      return null
    },
    openDialog () {
      this.$refs.popupVatTuTB.show();
      this.formThongTinThietBi.dtpNgayCap = moment(new Date()).format('DD/MM/YYYY HH:mm A');
    },
    grvDS_VT_Giao_SelectedItemsChanged (items) {
      this.grvDS_VT_Giao.checked = items
    },
    gridviewVatTuActionComplete (args) {
      if (this.gridviewVatTu.selectItem != null) {
        let index = this.gridviewVatTu.list.findIndex(item => item.phieuvt_id == this.gridviewVatTu.selectItem.phieuvt_id)
        if (index >= 0) {
          this.$refs.gridDanhSachVatTu.selectRow(index, true)
        } else {
          if (this.gridviewVatTu.list.length > 0) {
            this.$refs.gridDanhSachVatTu.selectRow(0, true)
          }
        }
      } else {
        if (this.gridviewVatTu.list.length > 0) {
          this.$refs.gridDanhSachVatTu.selectRow(0, true)
        }
      }
    },
    gridviewVatTu_FocusedRowChanged (item) {
      try {
        if (item != null) {
          this.gridviewVatTu.selectItem = item
          this.formThongTinThietBi.dtpNgayCap = this.getDate(item.ngay_cap, 'DD/MM/YYYY HH:mm:ss').format('DD/MM/YYYY HH:mm A')
          let kt = item.phieuvt_id
          if (kt != null && kt != '0') {
            this.kt_id = Number.parseInt(kt)
          } else {
            this.phvt_id = 0
          }
          if (this.cboKieuTbi.value.toString() == '1') {
            if (this.kieu_f == 1) {
              this.phvt_id = Number.parseInt(item.phieuvt_id)
            }
            return
          }
          if (this.kieu_f != 10) {
            if (this.cboKieuTbi.value == 3 && item.kieutbi_id == '3') {
              this.LayTT_PVT(item)
            } else {
              this.cboVatTu.disabled = false
              this.disableBox.txtSoLuong = false
              this.$refs.cboSerial.setVisible(false)
              this.setActiveButton('tsbtnNhapMoi', true)
              this.setActiveButton('tsbtnGhiLai', true)
              this.setActiveButton('tsbtnXoa', true)
              this.refreshData()
              this.setActiveActions(1)
            }
          }
        }
      } catch (error) {
        console.log(error)
        this.$root.$toast.warning('Xảy ra lỗi khi show dữ liệu gridviewVattu ', error.message)
      }
    },
    btnThuHoi_ButtonClick (item) {
      try {
        this.LayTT_Vattu_Thuhoi(item)
      } catch (error) {
        console.log(error)
        this.$root.$toast.error(error.message)
      }
    },
    async LayTT_Vattu_Thuhoi (row) {
      this.cboKieuTbi.value = KieuTbi.THUHOI
      let seri = row.serial
      let matbi = row.ma_tbi
      this.setActiveButton('tsbtnNhapMoi', false)
      this.setActiveButton('tsbtnGhiLai', true)
      this.setActiveButton('tsbtnHuyBo', true)
      this.phvt_id = Number.parseInt(row.phieuvt_id)
      if (!seri && !matbi) {
        this.$refs.cboSerial.setInputText('')
        this.disableBox.txtSoLuong = false
        this.cboVatTu.disabled = false
        this.$refs.cboSerial.setDisable(false)
      } else if (seri && !matbi) {
        this.$refs.cboSerial.setInputText(seri)
        this.disableBox.txtSoLuong = true
        this.cboVatTu.disabled = true
        this.$refs.cboSerial.setDisable(true)
      } else if (!seri && matbi) {
        this.$refs.cboSerial.setInputText(matbi)
        this.disableBox.txtSoLuong = false
        this.cboVatTu.disabled = false
        this.$refs.cboSerial.setDisable(false)
      }
      this.clickBtnVTTH = true
      this.setInfoVTThuHoi(row)
      this.rowVTTH = row
    },
    setInfoVTThuHoi (row) {
      this.cboVatTu.value = Number.parseInt(row.vattu_id)
      console.log(this.cboVatTu.value)
      let a = Number.parseInt(row.soluong)
      this.formThongTinThietBi.soluong = a
      console.log(this.formThongTinThietBi.soluong)
      this.sl_goc = a
      this.vsdvt_id = Number.parseInt(row.sdvt_id)
    },
    gridviewVatTuRowClick (index, item) {
      this.gridviewVatTu_FocusedRowChanged(item)
    },
    LayTT_PVT (row) {
      try {
        this.setActiveButton('tsbtnNhapMoi', true)
        this.setActiveButton('tsbtnGhiLai', true)
        this.setActiveButton('tsbtnXoa', true)
        this.phvt_id = Number.parseInt(row.phieuvt_id)
        if (!row.serial && !row.ma_tbi) {
          this.disableBox.txtSoLuong = false
          this.cboVatTu.disabled = true
          this.$refs.cboSerial.setDisable(true)
          this.$refs.cboSerial.setInputText('')
        } else if (row.serial && !row.ma_tbi) {
          this.$refs.cboSerial.setInputText(row.serial)
          this.disableBox.txtSoLuong = false
          this.cboVatTu.disabled = true
          this.$refs.cboSerial.setDisable(true)
        } else if (!row.serial && row.ma_tbi) {
          this.$refs.cboSerial.setInputText(row.ma_tbi)
          this.disableBox.txtSoLuong = false
          this.cboVatTu.disabled = true
          this.$refs.cboSerial.setDisable(false)
        }
        this.cboVatTu.value = row.vattu_id
        this.formThongTinThietBi.soluong = row.soluong
        this.cboTinhTrang.value = row.tinhtrangtbi_id
        this.cboKieuTrangBi.value = row.trangbi_id
        this.DmLyDoCap.selectLyDoCap = row.mucdich_id
        this.formThongTinThietBi.txtGhiChu = row.ghichu
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Có lỗi: ' + error.message)
      }
    },
    setActiveButton (id, status) {
      this.actions[this.actions.findIndex(item => item.id == id)].active = status
    },
    setVisibleButton (id, status) {
      this.actions[this.actions.findIndex(item => item.id == id)].visible = status
    },
    setActiveActions (kieu) {
      this.setActiveButton('tsbtnNhapMoi', false)
      this.setActiveButton('tsbtnGhiLai', false)
      this.setActiveButton('tsbtnXoa', false)
      this.setActiveButton('tsbtnHuyBo', false)
      this.cboKieuTbi.disabled = false
      this.DmMucDichCap.disabled = false
      this.DmLyDoCap.disabled = false
      if (kieu == -1) { // bat dau
        this.setActiveButton('tsbtnGhiLai', true)
        this.setActiveButton('tsbtnHuyBo', true)
        this.setVisibleButton('tsbtnKHCapNhat', false)
        this.setColThuHoiVisible(true)
        this.grvDS_VT_Giao.showColumnCheckbox = false
      } else if (kieu == 0) { // bắt đầu
        this.setActiveButton('tsbtnNhapMoi', true)
        this.setVisibleButton('tsbtnKHCapNhat', false)
        this.setVisibleButton('tsbtnKHCapNhat', false)
        this.setColThuHoiVisible(true)
        this.grvDS_VT_Giao.showColumnCheckbox = false
      } else if (kieu == 1) { // thêm mới
        this.setActiveButton('tsbtnGhiLai', true)
        this.setActiveButton('tsbtnHuyBo', true)
        this.setVisibleButton('tsbtnKHCapNhat', false)
        this.setColThuHoiVisible(true)
        this.grvDS_VT_Giao.showColumnCheckbox = false
      } else if (kieu == 2) { // Huỷ
        this.setActiveButton('tsbtnNhapMoi', true)
        this.setActiveButton('tsbtnXoa', true)
        this.setVisibleButton('tsbtnKHCapNhat', false)
        this.setColThuHoiVisible(true)
        this.grvDS_VT_Giao.showColumnCheckbox = false
      } else if (kieu == 3) { // edit
        this.setActiveButton('tsbtnNhapMoi', true)
        this.setActiveButton('tsbtnXoa', true)
        this.setActiveButton('tsbtnGhiLai', true)
        this.setActiveButton('tsbtnHuyBo', true)
        this.cboKieuTbi.disabled = true
        this.DmMucDichCap.disabled = true
        this.DmLyDoCap.disabled = true
        this.setVisibleButton('tsbtnKHCapNhat', false)
        this.setColThuHoiVisible(true)
        this.grvDS_VT_Giao.showColumnCheckbox = false
      } else if (kieu == 277) {
        this.setActiveButton('tsbtnNhapMoi', false)
        this.setActiveButton('tsbtnXoa', false)
        this.setActiveButton('tsbtnGhiLai', false)
        this.setActiveButton('tsbtnHuyBo', false)
        this.setVisibleButton('tsbtnKHCapNhat', true)
        this.setColThuHoiVisible(false)
        this.grvDS_VT_Giao.showColumnCheckbox = true
      }
    },
    tsbtnHuyBo_Click () {
      this.refreshData()
      this.cboVatTu.disabled = true
      this.disableBox.txtSoLuong = true
      this.setActiveButton('tsbtnNhapMoi', true)
      this.setActiveButton('tsbtnGhiLai', false)
      this.setActiveButton('tsbtnHuyBo', false)
      if (this.cboKieuTbi.value == 1) { // Moi
        this.setVisibleButton('tsbtnGhiLai', false)
        this.setActiveButton('tsbtnXoa', true)
        this.setVisibleButton('tsbtnHuyBo', false)
        this.cboVatTu.disabled = false
        this.disableBox.txtSoLuong = true
        this.setVisibleButton('tsbtnNhapMoi', false)
        this.$refs.cboSerial.setDisable(false)
      } else {
        this.cboVatTu.disabled = true
        this.disableBox.txtSoLuong = false
        this.$refs.cboSerial.setDisable(false)
        this.$refs.cboSerial.setVisible(false)
        this.setVisibleButton('tsbtnNhapMoi', true)
        this.setVisibleButton('tsbtnGhiLai', true)
        this.setVisibleButton('tsbtnHuyBo', true)
        this.refreshData()
        this.setActiveActions(1)
      }
    },
    tsbtnXoa_Click () {
      try {
        this.loading(true)
        this.vdonvi_nhan_id = Number.parseInt(this.gridviewVatTu.selectItem.donvi_id)
        if (!this.KiemTraDuLieu_Tb()) {
          return
        }
        this.$confirm('Bạn có thực sự muốn xóa phiếu vật tư đang chọn không?', 'Thông báo', {
          confirmButtonText: 'Có',
          cancelButtonText: 'Không'
        }).then(async () => {
          try {
            this.loading(true)
            let vPhieuVTId = this.gridviewVatTu.selectItem.phieuvt_id
            let vTuVTId = this.gridviewVatTu.selectItem.tuvt_id
            let vSoluong = this.gridviewVatTu.selectItem.soluong
            let vTamThuId = this.gridviewVatTu.selectItem.tamthu_id
            const request = {
              'p_tagform': this.tagfrm,
              'p_khboithuong': this.kh_boithuong ? 1 : 0,
              'p_phieuvt_id': Number(vPhieuVTId),
              'p_tuvt_id': Number(vTuVTId),
              'p_soluong': Number(vSoluong),
              'p_hdtb_id': Number(this.hdtb_id),
              'p_tamthu_id': Number(vTamThuId) || 0
            }
            let success = await this.SP_XOA_PHIEU_VT_CAP_THUHOI(request)
            if (success) {
              this.$root.$toast.success('Xóa phiếu vật tư thành công')
              this.gridviewVatTu.selectItem = null
              await this.LoadGridVT()
              await this.Load_DS_VTTU()
              this.tsbtnNhapMoi_Click()
            }
          } catch (error) {
            console.log(error)
            this.$root.$toast.error('Có lỗi: ' + error.message)
          } finally {
            this.loading(false)
          }
        })
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Xảy ra lỗi khi thực hiện xóa phiếu vật tư: ' + error.message)
      } finally {
        this.loading(false)
      }
    },
    KiemTraDuLieu_Tb () {
      if (this.phieu_id == 0) {
        this.$root.$toast.warning('Chưa có thông tin phiếu. Hãy kiểm tra lại!')
        return false
      }
      if (this.vdonvi_nhan_id != Number.parseInt(this.$root.token.getDonViID())) {
        this.$root.$toast.warning('Phiếu không cùng đơn vị với đơn vị của user. Bạn không được phép cập nhật.')
        return false
      }
      if (this.vngay_ht != '') {
        let ngayHt = moment(new Date(this.vngay_ht)).format('MMYYYY')
        let now = moment(new Date()).format('MMYYYY')
        let currentTime = new Date()
        let lastMonth = moment(currentTime.setMonth(currentTime.getMonth() - 1)).format('MMYYYY')
        if (ngayHt != now) {
          if (ngayHt != lastMonth) {
            this.$root.$toast.warning('Bạn chỉ được phép thao tác phiếu thực hiện trong tháng hoặc tháng trước')
            return false
          }
        }
      }
      return true
    },
    async Insert_SErial_V2 (newData, serial) {
      try {
        this.loading(true)
        let vNgayCap = this.getDate(this.formThongTinThietBi.dtpNgayCap).toDate()
        let kieuTb = 1
        let vTrangBi = this.cboKieuTrangBi.value
        let vTuVTId = newData.tuvt_id
        let vThietBiId = newData.thietbi_id
        let vSoLuong = 1
        let maTbi = ''
        let thongbao = ''
        let dieukiencap = await this.DIEUKIEN_CAP_VT(this.hdtb_id, this.loaihd_id, this.loaitbi_id, vThietBiId)
        if (dieukiencap != '1') {
          this.$root.$toast.error(dieukiencap)
          return
        }
        let kiemtradmvt = await this.FN_KIEMTRA_DM_VT(this.hdtb_id, this.baohong_id, newData.vattu_id, vSoLuong, this.cboKho.value)
        if (kiemtradmvt != '1') {
          thongbao = thongbao + kiemtradmvt + '\r\n'
        }
        if (thongbao != '') {
          // Nếu có tham số thì chặn
          if (this.canhbao_hoac_loi) {
            this.$root.$toast.error(thongbao)
            return
          }
          let accept = false
          this.$confirm('Cảnh báo định mức \r\n ' + thongbao, 'Thông báo', {
            confirmButtonText: 'Có',
            cancelButtonText: 'Không'
          }).then(async () => {
            accept = true
          })
          if (!accept) {
            return
          }
        }
        let vLyDoCap = this.DmLyDoCap.selectLyDoCap
        if (vNgayCap > new Date()) {
          this.$root.$toast.warning('Ngày cấp ko được lớn hơn ngày hiện tại')
          return
        }
        let tinhtrangTbi = this.cboTinhTrang.value
        this.$confirm('Bạn có chắc chăn muốn cấp thiết bị có serial: ' + serial + ' không?', 'Thông báo', {
          confirmButtonText: 'Có',
          cancelButtonText: 'Không'
        }).then(async () => {
          // Kiểm tra kho đơn vị cấp ra
          let tempHDBHId = 0
          let vk = 0
          if (this.hdtb_id != 0) {
            tempHDBHId = this.hdtb_id
            vk = 1
          } else {
            tempHDBHId = this.baohong_id
            vk = 2
          }
          let ktKQ = await this.KIEMTRA_VTCAPRA(vk, tempHDBHId, 1, vTuVTId)
          if (ktKQ != '1') {
            this.$root.$toast.warning(ktKQ)
            return
          }
          let vPhieuVTId = await this.POST_GET_KEYS('PHIEUVT_ID')
          let vsvdtId = 0
          let vnhvIdTemp = this.nhanvienId
          if (this.cboNhanVien.value.toString() != vnhvIdTemp.toString()) {
            vnhvIdTemp = this.cboNhanVien.value
          }
          // Create du lieu
          const request = {
            'vsdvt_id': vsvdtId.toString(),
            'vphieuvt_id': vPhieuVTId,
            'vphieu_id': this.phieu_id.toString(),
            'vngay_cap': this.getDate(this.formThongTinThietBi.dtpNgayCap).format('DD/MM/YYYY HH:mm:ss'),
            'vkieutbi_id': kieuTb,
            'vtrangbi_id': vTrangBi.toString(),
            'vtuvt_id': vTuVTId.toString(),
            'vthietbi_id': vThietBiId.toString(),
            'vsoluong': vSoLuong,
            'vserial': serial.trim(),
            'vma_tbi': maTbi,
            'vnhanvien_id': vnhvIdTemp,
            'vdonvi_id': this.donviId.toString(),
            'vnguoi_cn': this.username,
            'vmay_cn': this.maycn,
            'vip_cn': this.ipcn,
            'vlydocap_id': vLyDoCap.toString(),
            'vlydocapto_id': this.DmMucDichCap.selectMucDichCap.toString(),
            'vhdtb_id': this.hdtb_id.toString(),
            'vbaohong_id': this.baohong_id.toString(),
            'vttpk': '1',
            'vtinhtrangbi_id': tinhtrangTbi.toString(),
            'vghichu': this.formThongTinThietBi.txtGhiChu
          }
          let s = await this.INSERT_PHIEU_VATTU_V2(request)
          if (s != '1') {
            this.$root.$toast.error(s)
          }
          this.$refs.cboSerial.setInputText('')
          await this.LoadGridVT()
          await this.LoadGrid_VatTuSD()
          if (this.cboKho.value == 0) {
            this.$refs.cboSerial.setDisable(true)
            this.DmVTGiao.data = []
          } else {
            // this.$refs.cboSerial.setDisable(false)
            await this.Load_DS_VTTU()
          }
        })
      } catch (error) {
        console.log(error)
        this.$root.$toast.warning('Có lỗi: ' + error.message)
      } finally {
        this.loading(false)
      }
    },
    async Insert_SErial (item) {
      try {
        let vngaycap = new Date(this.formThongTinThietBi.dtpNgayCap)
        let vKieuTbi = 1
        let vTrangBi = Number.parseInt(this.cboKieuTrangBi.value)
        let vTuVTId = Number.parseInt(item.tuvt_id)
        let vThietBiID = Number.parseInt(item.thietbi_id)
        let vSoLuong = 1
        let maTbi = ''
        let vlydocap = Number.parseInt(this.DmLyDoCap.selectLyDoCap)
        if (vngaycap > new Date()) {
          this.$root.$toast.warning('Ngày cấp ko được lớn hơn ngày hiện tại')
        }
        let tinhtrangTbi = Number.parseInt(this.cboTinhTrang.value)
        let accept1 = await this.$confirm('Bạn có chắc chăn muốn cấp thiết bị có serial: ' + item.serial + ' không?', 'Thông báo', {
          confirmButtonText: 'Có',
          cancelButtonText: 'Không'
        })
        if (!accept1) {
          return
        }
        let tempHdBHID = 0
        let vk = 0
        if (this.hdtb_id != 0) {
          tempHdBHID = this.hdtb_id
          vk = 1
        } else {
          tempHdBHID = this.baohong_id
          vk = 2
        }
        let ktKq = await this.KIEMTRA_VTCAPRA(vk.toString(), tempHdBHID.toString(), '1', vTuVTId.toString())
        if (ktKq != '1') {
          if (!ktKq.includes('MDWIFI')) {
            let accept = await this.$confirm(ktKq.split('-')[1], 'Thông báo', {
              confirmButtonText: 'Có',
              cancelButtonText: 'Không'
            })
            if (accept == false) {
              return
            }
          } else {
            this.$root.$toast.warning(ktKq)
          }
        }
        let vPhieuvtId = await this.POST_GET_KEYS('phieuvt_id')
        let vnhvIdTemp = this.$root.token.getNhanVienID()
        if (this.cboNhanVien.value != Number.parseInt(vnhvIdTemp)) {
          vnhvIdTemp = this.cboNhanVien.value
        }
        // Create du lieu
        const request = {
          'vsdvt_id': '0',
          'vphieuvt_id': vPhieuvtId,
          'vphieu_id': this.phieu_id.toString(),
          'vngay_cap': this.getDate(this.formThongTinThietBi.dtpNgayCap).format('DD/MM/YYYY HH:mm:ss'),
          'vkieutbi_id': vKieuTbi,
          'vtrangbi_id': vTrangBi.toString(),
          'vtuvt_id': vTuVTId.toString(),
          'vthietbi_id': vThietBiID.toString(),
          'vsoluong': vSoLuong,
          'vserial': item.serial.trim(),
          'vma_tbi': maTbi,
          'vnhanvien_id': vnhvIdTemp,
          'vdonvi_id': this.$root.token.getDonViID().toString(),
          'vnguoi_cn': 'admin',
          'vmay_cn': 'admin',
          'vip_cn': '192.168.1.1',
          'vlydocap_id': vlydocap.toString(),
          'vlydocapto_id': this.DmMucDichCap.selectMucDichCap.toString(),
          'vhdtb_id': this.hdtb_id.toString(),
          'vbaohong_id': this.baohong_id.toString(),
          'vttpk': '1',
          'vtinhtrangbi_id': tinhtrangTbi.toString(),
          'vghichu': this.formThongTinThietBi.txtGhiChu
        }
        let resp = await this.INSERT_PHIEU_VATTU_V2(request)
        if (resp != '1') {
          this.$root.$toast.warning(resp)
        }
        await this.LoadGridVT()
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Xảy ra lỗi khi thực hiện tự động cấp')
      }
    },
    async tsbtnAutoCap_Click () {
      try {
        this.loading(true)
        let z = await this.DoKiem()
        console.log('tsbtnAutoCap_Click ', z)
        if (z != 'OK') {
          if (z.includes('ERROR')) {
            this.$root.$toast.warning(z)
            return
          }
          if (z.includes('VNPT') || z.includes('ZTE')) {
            let tamung = await this.SP_LAY_DS_TAMUNG_THEO_SERIAL(z.toUpperCase())
            if (tamung.length > 0) {
              await this.Insert_SErial(tamung[0])
            } else {
              this.$root.$toast.warning('Không tìm thấy serial GP ' + z + ' trong kho')
            }
          } else {
            this.$root.$toast.warning('Không đúng cấp trúc VNPT hoặc ZTE không thể tự động cấp ( ' + z + ')')
          }
        } else {
          this.$root.$toast.warning('Thuê bao không đo kiểm được')
        }
      } catch (error) {
        console.log('Có lỗi khi tìm phiếu', error)
      } finally {
        this.loading(false)
      }
    },
    async KHCapNhat () {
      try {
        this.loading(true)
        if (this.cboKieuTbi.value.toString() != '3') {
          this.$root.$toast.warning('"Kiểu thiết bị phải là thu hồi !')
          return
        }
        if (this.DmLyDoCap.selectLyDoCap.toString() != '277') {
          this.$root.$toast.warning('Lý do cấp phải là khách hàng bồi thường do làm mất thiết bị !')
          return
        }
        if (this.grvDS_VT_Giao.list.length <= 0) {
          this.$root.$toast.warning('Không có danh sách vật tư thuê bao đang sử dụng !')
          return
        }
        if (this.grvDS_VT_Giao.checked.length <= 0) {
          this.$root.$toast.warning('Bạn chưa tích chọn vật tư nào !')
          return
        }
        let s = []
        for (let i = 0; i < this.grvDS_VT_Giao.checked.length; i++) {
          let item = this.grvDS_VT_Giao.checked[i]
          let keys = Object.keys(item)
          for (let j = keys.length - 1; j >= 0; j--) {
            if (keys[j] != 'sdvt_id') {
              delete item[keys[j]]
            }
          }
          s.push(item)
        }
        let request = {
          'vdata': JSON.stringify(s),
          'vphieu_id': this.phieu_id,
          'vhdtb_id': this.hdtb_id,
          'vbaohong_id': this.baohong_id,
          'vlydocap_id': this.DmMucDichCap.selectMucDichCap ? this.DmMucDichCap.selectMucDichCap : 0,
          'vlydocaptbi_id': this.DmLyDoCap.selectLyDoCap ? this.DmLyDoCap : 0,
          'vnhanvien_id': this.nhanvienId,
          'vdonvi_id': this.donviId,
          'vnguoi_cn': this.$root.token.getUserName(),
          'vmay_cn': this.maycn,
          'vip_cn': this.ipcn,
          'vghichu': this.formThongTinThietBi.txtGhiChu
        }
        let result = this.SendData(await CapVatTuTBAPI.KH_BOITHUONG_TB(this.axios, request))
        if (result != '1') {
          this.$root.$toast.error(result)
          return
        }
        await this.LoadGridVT()
        await this.LoadGrid_VatTuSD()
        this.$root.$toast.success('Cập nhật thành công !')
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Lỗi KH bồi thường: ' + error.message)
      } finally {
        this.loading(false)
      }
    },
    async DoKiem () {
      console.log('vddt_id ', this.vdvvt_id)
      let isServerDK = Number.parseInt(await this.LAY_THAMSO_MACDINH('SERVER_DK', this.$root.token.getNguoiDungID().toString()))
      if ((this.vuser_alt == '' || this.vpass_alt == '') && isServerDK == 1) {
        return 'OK'
      }
      if ((this.vuser_xtest == '' || this.vpass_xtest == '') && isServerDK == 2) {
        return 'OK'
      }
      if (this.vdvvt_id != DICHVU_VIENTHONG.ADSL) {
        return 'OK'
      }
      let vip = ''
      let vportVisa = ''
      let vonuId = ''
      let vportAdsl = ''
      let vloaiTbi = ''
      let thamso = ''
      let vIPXtest = ''
      let vportBnm = ''
      let vloaiGpon = ''
      let vInputXtest = ''
      let vInput = this.vma_tbcur
      let dsPort = await this.LAY_DS_PORT_THEOHD_TB(this.hdtb_id, -1)
      if (dsPort.length > 0) {
        if (dsPort[0].port_id == null) {
          return 'Thuê bao chưa được cấp port (ERROR)'
        }
        if (dsPort[0].loaitbi_id != 3) {
          return 'OK'
        }
        if (dsPort[0].thamso == 4 && this.$root.token.getMaTinh() != 'HNI') {
          return 'OK'
        }
        vip = dsPort[0].ip
        vportAdsl = dsPort[0].port_adsl
        vportVisa = dsPort[0].port_visa
        vonuId = dsPort[0].onu_id
        vInput = vip + ':' + vportVisa
        vloaiTbi = dsPort[0].loaitbi_id
        thamso = dsPort[0].thamso
        vIPXtest = dsPort[0].portinfo
        if (vonuId != '') {
          vInput += '/' + vonuId
        }
        if (vip == '') {
          return 'Thuê bao chưa được cấp port vip rỗng'
        }
        let keys = Object.keys(dsPort[0])
        if (isServerDK == 3 && keys.includes('loai_gpon')) {
          vloaiGpon = dsPort[0].loai_gpon
          vportBnm = dsPort[0].port_bnm
          vInputXtest = vip + ':' + vportBnm + ':' + vloaiGpon + ':' + dsPort[0].loaitbi_id
        }
      } else {
        return 'Thuê bao chưa được cấp port (ERROR)'
      }
      if (this.$root.token.getMaTinh() != 'HNI') {
        const request = {
          'linetestUser': (isServerDK == 1 ? this.vuser_alt : this.vuser_xtest) + "'" + isServerDK + "'" + vloaiTbi + "'" + thamso,
          'linetestPassword': isServerDK == 1 ? this.vpass_alt : this.vpass_xtest,
          'input': isServerDK == 1 ? vInput : vIPXtest,
          'mac': '0'
        }
        let dsss = await this.TEST_PORT(request)
        if (dsss != null) {
          if (dsss.errorCode != 0) {
            return 'Không đọc được thiết bị (ERROR)'
          } else {
            let dta = dsss.data
            let arrDta = dta.split('\n')
            let kq = ''
            arrDta.forEach(item => {
              if (item.includes('Serial Number')) {
                kq = item.substring(item.indexOf(':') + 1).trim()
              }
            })
            return kq
          }
        }
      } else {
        const request = {
          'linetestUser': this.vuser_xtest,
          'linetestPassword': this.vpass_xtest,
          'input': vInputXtest,
          'att_gpon': '30',
          'att_adsl': '0',
          'snr_adsl': '0',
          'ip': '10.72.105.101',
          'rack': '1',
          'shelf': '1',
          'slot': '01',
          'port': '09',
          'onu_id': '33',
          'server_dk': '5'
        }
        let dsss = await this.GETPORT_QUANLITYV2(request)
        if (dsss != null) {
          if (dsss.errorCode != 0) {
            return 'Không đọc được thiết bị (ERROR)'
          } else {
            let dta = dsss.data
            let arrDta = dta.split('\n')
            let kq = ''
            arrDta.forEach(item => {
              if (item.includes('Serial Number')) {
                kq = item.replace(':', '')
              }
            })
            return kq
          }
        }
      }
      return 'OK'
    },
    XuatExcel () {
      if (this.gridviewVatTu.list.length > 0) {
        let data = XLSX.utils.json_to_sheet(this.gridviewVatTu.list)
        let wb = XLSX.utils.book_new() // make Workbook of Excel
        XLSX.utils.book_append_sheet(wb, data, 'DSVT') // sheetAName is name of Worksheet
        // export Excel file
        XLSX.writeFile(wb, 'DanhSachVatTu.xls')
      } else this.$root.$toast('Không có dữ liệu!')
    },
    tsbtnNhapMoi_Click () {
      this.refreshData()
      this.setActiveActions(1)
      this.cboVatTu.disabled = false
      this.disableBox.txtSoLuong = false
      this.$refs.cboSerial.setVisible(true)
      this.setActiveButton('tsbtnNhapMoi', false)
      this.setActiveButton('tsbtnGhiLai', true)
      this.setActiveButton('tsbtnHuyBo', true)
    },
    tsbtnGhiLai_Click () {
      if (this.kieu_f == 1) {
        this.CAPNHAT_NGAY_CAP()
      } else {
        this.CAPNHAT_PHIEUVT_THUHOI()
      }
    },
    tsbtnInBienBanNTWifi_Click () {
      this.xembaocao.dsGiaTri = []
      if (this.hdtb_id != 0) {
        this.xembaocao.ma_bc = 'WIFIBB'
        this.xembaocao.dsGiaTri.push()
        this.xembaocao.dsGiaTri.push(this.hdtb_id)
        this.xembaocao.dsGiaTri.push(this.nhanvienId)
        this.$refs.popupXemBaoCao.XemNgay()
      }
      if (this.baohong_id != 0) {
        this.xembaocao.ma_bc = 'WIFIBB_BH'
        this.xembaocao.dsGiaTri.push(this.baohong_id)
        this.xembaocao.dsGiaTri.push(this.nhanvienId)
        this.$refs.popupXemBaoCao.XemNgay()
      }
    },
    tsbtnInBienBanBG_Click () {
      if (this.hdtb_id != 0) {
        this.xembaocao.ma_bc = 'BBGVT'
        this.xembaocao.dsGiaTri.push(this.hdtb_id)
        this.xembaocao.dsGiaTri.push(this.nhanvienId)
        this.$refs.popupXemBaoCao.XemNgay()
      }
      if (this.baohong_id != 0) {
        this.xembaocao.ma_bc = 'BBGVT_BH'
        this.xembaocao.dsGiaTri.push(this.baohong_id)
        this.xembaocao.dsGiaTri.push(this.nhanvienId)
        this.$refs.popupXemBaoCao.XemNgay()
      }
    },
    tsbtnInBienBanTH_Click () {
      if (this.baohong_id != 0) {
        this.xembaocao.ma_bc = 'BC_TH_BH'
        this.xembaocao.dsGiaTri.push(this.phieu_id)
        this.$refs.popupXemBaoCao.XemNgay()
      }
    },
    onActionClick (action) {
      if (action.id == 'tsbtnNhapMoi') {
        this.tsbtnNhapMoi_Click()
      } else if (action.id == 'tsbtnGhiLai') {
        this.tsbtnGhiLai_Click()
      } else if (action.id == 'tsbtnHuyBo') {
        this.tsbtnHuyBo_Click()
      } else if (action.id == 'tsbtnXoa') {
        this.tsbtnXoa_Click()
      } else if (action.id == 'tsbtnAutoCap') {
        this.tsbtnAutoCap_Click()
      } else if (action.id == 'tsbtnExcel') {
        this.XuatExcel()
      } else if (action.id == 'tsbtnKHCapNhat') {
        this.KHCapNhat()
      }
    },
    refreshData () {
      this.vsdvt_id = 0
      this.formThongTinThietBi.soluong = 0
      this.cboVatTu.value = 0
      this.formThongTinThietBi.txtGhiChu = ''
      this.phvt_id = 0
      this.cboKieuTrangBi.value = TRANGBI.VIENTHONG_TRANGBI
      this.DmVTGiao.selectVTGiao = null
    },
    handleHideModal () {
      this.cboNhanVien.value = 0
      this.gridviewVatTu.list = []
      this.grvDS_VT_Giao.list = []
      this.gridviewVatTu.selectItem = null
      this.refreshData()
    },
    getActiveButton (id) {
      return this.actions[this.actions.findIndex(item => item.id == id)].active
    },
    getDate (dateStr) {
      return moment(dateStr, 'DD/MM/YYYY HH:mm A')
    },
    setColThuHoiVisible (status) {
      let colIndex = this.grvDS_VT_Giao.headers.findIndex(item => item.fieldName == 'btnThuHoi')
      if (colIndex >= 0) {
        this.grvDS_VT_Giao.headers[colIndex].visible = status
      }
    }
  }
}
</script>
<style>
.modal .modal-vattutb-size {
  max-width: 85% !important;
}
.select2-selection {
  overflow: hidden;
}
.select2-container .select2-selection--single .select2-selection__rendered {
  white-space: normal !important;
  word-break: break-all;
}
.e-control {
  font-family: "Roboto", Arial, sans-serif;
}
.disabled {
  pointer-events: none;
  opacity: 0.7;
}
.disabled_color {
  color: gray !important;
}
.dropdown-btn {
  position: relative;
  display: inline-block;
}
.dropdown-content-btn {
  display: none;
  position: absolute;
  background-color: #f9f9f9;
  min-width: 160px;
  box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.2);
  padding: 12px 16px;
  z-index: 1;
  color: #007bff;
}
.dropdown-btn:hover .dropdown-content-btn {
  display: block;
}
.dropdown-content-btn {
  display: none;
  position: absolute;
  background-color: #f9f9f9;
  min-width: 200px;
  box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.2);
  padding: 12px 16px;
  z-index: 1;
  color: #007bff;
}
.dropdown-btn:hover .dropdown-content-btn {
  display: block;
}
/* Remove by Quoc Ky chuyển css lên trên style html, tránh xung đột sài bên ngoài */
/* .list-actions-top .list {
  margin-right: 30px;
  overflow: inherit !important;
  white-space: inherit !important;
} */
.xuat-phieu:hover {
  background-color: rgba(1, 118, 255, 0.1);
  font-weight: 600
}
a:hover {
  color: #0056b3;
  text-decoration: none !important;
}
.ui-xuat-phieu {
  position: absolute;
  margin-top: 8px;
  width: 200px;
  padding-left: 0rem;
  list-style: none;
  background-color: #fff;
  background-clip: padding-box;
  border: 1px solid rgba(0, 0, 0, 0.15);
}
.list-actions-top a {
  cursor: pointer;
  text-decoration: none;
  font-weight: 600;
}
</style>