import DacTinhThietBi from './Components/DacTinhThietBi/index.vue'
import StorageCapacity from './StorageCapacity.vue'
import PortType from './PortType.vue'
import DeviceRelationInfo from './DeviceRelationInfo.vue'
import AllocationInfo from './AllocationInfo.vue'
import ModalThemCard from './Modal/ModalThemCard/index.vue'
import { CUSTOMER_TYPES, LOAI_THIET_BI, NEW_DEFAULT_OBJ, PORT_TYPE, ROOT_API } from './test.js'
import moment from 'moment'

export default {
  components: {
    StorageCapacity,
    PortType,
    DeviceRelationInfo,
    AllocationInfo,
    ModalThemCard,
    DacTinhThietBi
  },
  provide() {
    return {
      deletePortTypeItem: this.deletePortTypeListItem,
      setCurrentItemValByProp: this.setCurrentItemValByProp,
      createCardModule: this.createCardModule,
      updateCardModule: this.updateCardModule,
      deleteCardItem: this.deleteCardItem
    }
  },
  data() {
    return {
      //---- View Type ----//
      isViewOnly: false,
      //---- General Info ----//
      deviceTypeList: [],
      customerTypeList: [],
      equipmentCategoryList: [],
      IPAddressList: [],
      parentDeviceList: [],
      discTypeList: [],
      //---- Characteristics Info ----//
      manufacturerList: [],
      deviceRoleList: [],
      supplierList: [],
      OSList: [],
      usageStatusList: [],
      //---- Installation location Info ----//
      IDCList: [],
      IDCBuildingList: [],
      alterBuildingList: [],
      IDCFloorList: [],
      alterFloorList: [],
      IDCZoneList: [],
      alterZoneList: [],
      rackList: [],
      alterRackList: [],
      UpperUList: [],
      LowerUList: [],
      //---- Device Characteristics ----//
      // interfaceList: [],
      // powerList: [],
      // consoleList: [],
      cardList: [],
      //---- Thông tin quản lý ------//
      donViQuanLyList: [],
      phongBanQuanLyList: [],
      nguoiQuanLyList: [],
      //---- Device Relation Info ----//
      connectorList: [],
      infraList: [],
      colocationList: [],
      deviceRelationHistory: [],
      //---- Allocation Info ------//
      VMList: [],
      NFSList: [],
      controllerList: [],
      shelvesList: [],
      // allocationHistory: [],
      //---- Thông tin khai báo loại cổng ------//
      portTypeInterface: [],
      portTypePower: [],
      portTypeConsole: [],
      portInterfaceList: [],
      portPowerList: [],
      portConsoleList: [],
      //---- Current Item -----//
      currentItem: {
        id: null,
        // Thông tin chung
        mathietbi: '',
        ten: '',
        kyhieu: '',
        mathietbi_infra: '',
        thietbicha_id: null,
        loaithietbi_id: null,
        loaikhachhang: 0,
        serial_number: '',
        model: '',
        part_number: '',
        hsm: 0,
        hieu_luc: 1,
        ghi_chu: '',
        loaitudia_id: null,
        chungloaithietbi_id: 0,
        ip_ids: [],
        // Đặc tính thiết bị
        vaitrothietbi_ids: [],
        psu_qty_in: 0,
        psu_qty_out: 0,
        cauhinh: '',
        hangsx_id: null,
        namsx: 0,
        nhacungcap_id: null,
        cs_dinhdanh: 0,
        cs_thucte: 0,
        hedieuhanh_id: null,
        trangthai_sd: 1,
        ngay_lapdat: moment().format('yyyy-MM-DD'),
        ngay_baohanh: moment().format('yyyy-MM-DD'),
        ngay_sudung: moment().format('yyyy-MM-DD'),
        cpu_sokhe: 0,
        cpu_dacam: 0,
        cpu_total: 0,
        hdd_sokhe: 0,
        hdd_dacam: 0,
        hdd_total: 0,
        ram_sokhe: 0,
        ram_dacam: 0,
        ram_total: 0,
        bodieukhien_qty: 0,
        bodieukhien_dau: 0,
        bodieukhien_fname: '',
        khaydia_qty: 0,
        khaydia_dau: 0,
        khaydia_fname: '',
        // Vị trí lắp đặt TB
        idc_id: null,
        building_id: null,
        floor_id: null,
        zone_id: null,
        rack_id: null,
        unit_qty: 0,
        up_unit_id: null,
        down_unit_id: null,
        // Thong tin kiem ke tai san
        project_id: null,
        project_code: '',
        project_name: '',
        so_the_ts: '',
        donviqly_id: null,
        phongbanqly_id: null,
        nguoiqly_id: null,
        // Thong tin cong thiet bi
        // interface
        so_linecard: 0,
        so_port_interface: 0,
        so_interface: 0,
        interface_start: 0,
        interface_fname: '',
        // power
        so_power: 0,
        so_port_power: 0,
        so_powerslot: 0,
        power_start: 0,
        power_fname: '',
        // console
        so_controlcard: 0,
        so_port_console: 0,
        so_console: 0,
        console_start: 0,
        console_fname: '',
        // Nang luc luu tru
        hdd_tong: 0,
        hdd_nangluc: 0,
        hdd_capphat: 0,
        ssd_tong: 0,
        ssd_nangluc: 0,
        ssd_capphat: 0,
        iops_tong: 0,
        iops_nangluc: 0,
        iops_capphat: 0,
        mem_cache: 0,
        flash_cache: 0,
        // Loai thiet bi
        is_interface: null,
        is_power: null,
        is_console: null,
        is_controller: null,
        is_khaydia: null
      },
      //-------------------------//
      dataSource: [],
      dataCols: [
        { fieldName: 'mathietbi', headerText: 'Mã thiết bị', allowFiltering: true },
        { fieldName: 'ten', headerText: 'Tên thiết bị', allowFiltering: true },
        { fieldName: 'kyhieu', headerText: 'Ký hiệu TB', allowFiltering: true },
        { fieldName: 'ips', headerText: 'IP Quản trị', allowFiltering: true },
        {
          fieldName: 'loaithietbi_id',
          headerText: 'Loại thiết bị',
          allowFiltering: true,
          textAlign: 'Left',
          template: this.getColumnLoaiThietBiTemplate()
        },
        {
          fieldName: 'loaikhachhang',
          headerText: 'Khách hàng',
          allowFiltering: true,
          textAlign: 'Left',
          template: this.getColumnKhachHangTemplate()
        },
        { fieldName: 'serial_number', headerText: 'Serial Number', allowFiltering: true },
        { fieldName: 'model', headerText: 'Model Thiết bị', allowFiltering: true },
        { fieldName: 'part_number', headerText: 'Part Number', allowFiltering: true },
        {
          fieldName: 'hieu_luc',
          headerText: 'Hiệu Lực',
          allowFiltering: true,
          template: this.getColumnHieuLucTemplate(),
          textAlign: 'Left'
        }
      ],
      buttons: {
        btnNew: {
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
        }
      },
      thongTinChungControls: {
        txt_ten: {
          enabled: false,
          invalid: false
        },
        txt_kyhieu: {
          enabled: false
        },
        txt_mathietbi_infra: {
          enabled: false
        },
        select_loaithietbi_id: {
          enabled: false,
          invalid: false
        },
        select_loaikhachhang: {
          enabled: false
        },
        txt_serial_number: {
          enabled: false
        },
        checkbox_hsm: {
          enabled: false
        },
        checkbox_hieu_luc: {
          enabled: false
        },
        txt_ghi_chu: {
          enabled: false
        },
        select_loaitudia_id: {
          enabled: false,
          invalid: true
        },
        select_chungloaithietbi_id: {
          enabled: false,
          invalid: true
        },
        select_ip_ids: {
          enabled: false
        },
        txt_part_number: {
          enabled: false
        },
        txt_model: {
          enabled: false
        },
        select_thietbicha_id: {
          enabled: false
        }
      },
      thongTinLapDatControls: {
        select_idc_id: {
          enabled: false
        },
        select_building_id: {
          enabled: false
        },
        select_floor_id: {
          enabled: false
        },
        select_zone_id: {
          enabled: false
        },
        select_rack_id: {
          enabled: false
        },
        txt_unit_qty: {
          enabled: false
        },
        select_up_unit_id: {
          enabled: false
        },
        select_down_unit_id: {
          enabled: false
        }
      },
      thongTinQuanLyControls: {
        txt_project_code: {
          enabled: false
        },
        txt_project_name: {
          enabled: false
        },
        txt_so_the_ts: {
          enabled: false
        },
        select_project_id: {
          enabled: false
        },
        select_donviqly_id: {
          enabled: false
        },
        select_phongbanqly_id: {
          enabled: false
        },
        select_nguoiqly_id: {
          enabled: false
        }
      },
      deviceType: 'MAYCHU',
      // Dùng cho thêm card và module
      cardModuleInfo: {
        thietbicha_id: null,
        portType: 0,
        isCardModuleMode: false
      },
      normalizer(node) {
        return {
          id: node.ID,
          label: node.IP
        }
      }
    }
  },
  computed: {
    gridItems() {
      return this.$refs.grdItems
    },
    // parentDeviceList() {
    //   return this.dataSource.filter((item) => item.id !== this.currentItem.id && item.id == this.currentItem.thietbicha_id)
    // }
    isServer() {
      if (this.deviceType == LOAI_THIET_BI.MAY_CHU) {
        return true
      } else {
        return false
      }
    },
    isNetwork() {
      if (this.deviceType == LOAI_THIET_BI.MANG) {
        return true
      } else {
        return false
      }
    },
    isDiskShelf() {
      if (this.deviceType == LOAI_THIET_BI.TU_DIA) {
        return true
      } else {
        return false
      }
    },
    isIDC() {
      if (this.deviceType == LOAI_THIET_BI.IDC) {
        return true
      } else {
        return false
      }
    },
    isOther() {
      if (this.deviceType == LOAI_THIET_BI.KHAC) {
        return true
      } else {
        return false
      }
    },
    interfaceList() {
      const list = this.cardList.filter((item) => item.thietbicha_id == this.currentItem.id && !!item.is_interface)
      return list
    },
    powerList() {
      const list = this.cardList.filter((item) => item.thietbicha_id == this.currentItem.id && !!item.is_power)
      return list
    },
    consoleList() {
      const list = this.cardList.filter((item) => item.thietbicha_id == this.currentItem.id && !!item.is_console)
      return list
    }
  },
  watch: {
    'currentItem.id': function (newVal, oldVal) {
      if (newVal && newVal !== oldVal) {
        this.$nextTick(() => this.getDeviceRelationInfo(newVal))
      }

      const isEnabled = newVal > 0
      this.buttons.btnEdit.enabled = isEnabled
      this.buttons.btnDelete.enabled = isEnabled
    },
    'currentItem.loaithietbi_id': function (val) {
      this.thongTinChungControls.select_loaithietbi_id.invalid = !val

      if (val) {
        const type = this.deviceTypeList.find((item) => item.ID == val).PHANLOAI
        this.deviceType = type
        this.updateParentDeviceList()
      }
    },
    'currentItem.ten': function (val) {
      this.thongTinChungControls.txt_ten.invalid = !val
    },
    'currentItem.chungloaithietbi_id': function (newVal, oldVal) {
      this.thongTinChungControls.select_chungloaithietbi_id.invalid = !newVal

      if (newVal && newVal != oldVal) {
        const chungloai = this.equipmentCategoryList.find((item) => item.ID == Number(newVal))

        if (!chungloai) return

        this.currentItem.hangsx_id = chungloai.HANGSANXUAT_ID
        this.currentItem.cs_dinhdanh = chungloai.CS_DINHDANH
        this.currentItem.so_linecard = chungloai.SO_LINECARD ?? this.currentItem.so_linecard
        this.currentItem.so_interface = chungloai.SO_INTERFACE ?? this.currentItem.so_interface
        this.currentItem.so_port_interface = chungloai.SO_PORT_INTERFACE ?? this.currentItem.so_port_interface
        this.currentItem.so_power = chungloai.SO_POWER ?? this.currentItem.so_power
        this.currentItem.so_port_power = chungloai.SO_PORT_POWER ?? this.currentItem.so_port_power
        this.currentItem.so_controlcard = chungloai.SO_CONTROLCARD ?? this.currentItem.so_controlcard
        this.currentItem.so_console = chungloai.SO_CONSOLE ?? this.currentItem.so_console
        this.currentItem.so_port_console = chungloai.SO_PORT_CONSOLE ?? this.currentItem.so_port_console
      }
    },
    'currentItem.loaitudia_id': function (val) {
      this.thongTinChungControls.select_loaitudia_id.invalid = !val
    },
    // IPAddressList: function(val) {
    //   if (Array.isArray(val) && val.length > 0) {
    //     this.currentItem.ip_ids = val.filter((item) => Boolean(item.CHK)).map((item) => item.ID)
    //   }
    // },
    'currentItem.idc_id': function (val) {
      if (val) {
        // set building list base on idc_id
        this.alterBuildingList = this.IDCBuildingList.filter((item) => item.IDC_ID == val)
      }
    },
    'currentItem.building_id': function (val) {
      if (val) {
        // set floor list base on building_id
        this.alterFloorList = this.IDCFloorList.filter((item) => item.TOANHA_ID == val)
      }
    },
    'currentItem.floor_id': function (val) {
      if (val) {
        // set zone list base on floor_id
        this.alterZoneList = this.IDCZoneList.filter((item) => item.MATSAN_ID == val)
      }
    },
    'currentItem.zone_id': function (val) {
      if (val) {
        // set zone list base on floor_id
        this.alterRackList = this.rackList.filter((item) => item.IDC_ZONE_ID == val)
      }
    }
  },
  async created() {
    await this.loadInfo()
    this.customerTypeList = [...CUSTOMER_TYPES]
  },
  updated() { },
  methods: {
    async loadInfo() {
      let deviceList;
      try {
        this.$root.showLoading(true)

        const tasks = [
          // thong tin chung
          await this.getDeviceList(),
          this.getDeviceType(),
          this.getChungLoaiThietBi(),
          this.getDanhSachIP(),
          this.getDanhMucLoaiTuDia(),
          // thong tin lap dat thiet bi
          this.getIDC(),
          this.getIDCBuilding(),
          this.getIDCFloor(),
          this.getIDCZone(),
          this.getIDCRack()
        ]

        const [
          // thong tin chung
          _deviceList,
          deviceType,
          chungLoaiThietBi,
          danhSachIP,
          danhMucLoaiTuDia,
          // thong tin lap dat thiet bi
          IDC,
          IDCBuilding,
          IDCFloor,
          IDCZone,
          IDCRack
        ] = await Promise.all(tasks)

        deviceList = _deviceList;
        this.dataSource = JSON.parse(JSON.stringify(deviceList))
        this.deviceTypeList = deviceType
        this.equipmentCategoryList = chungLoaiThietBi
        this.discTypeList = danhMucLoaiTuDia
        this.IPAddressList = danhSachIP
        this.IDCList = IDC
        this.IDCBuildingList = IDCBuilding
        this.IDCFloorList = IDCFloor
        this.IDCZoneList = IDCZone
        this.rackList = IDCRack
        this.getDeviceRelationInfo(deviceList[0]?.id)
      } finally {
        this.ganIPList(deviceList && deviceList[0]?.id)
        this.$root.showLoading(false)
      }
    },
    handleErrorResponse(error) {
      if (!(error.response == null || error.response == undefined)) {
        if (!(error.response.data == null || error.response.data == undefined)) {
          this.$root.toastError(error.response.data.message_detail ?? error.response.data.message ?? 'Đã có lỗi xảy ra trong quá trình xử lý dữ liệu')
        }
      }
    },
    async ganIPList(id) {
      this.IPAddressList = (await this.getDanhSachIP(id ?? this.currentItem.id)).filter((item) => item.DA_GAN_THIETBI_HIENTAI == 1 || (item.DA_GAN_MAYAO !== 1 && item.DA_GAN_THIETBI_KHAC !== 1 && item.DA_GAN_HATANG_BACKUP !== 1 && item.DA_GAN_HATANG_LUU_TRU !== 1))
    },
    updateParentDeviceList() {
      // Nếu trong module mode thì set lại giá trị cho parentDeviceList chỉ có 1 thiết bị cha
      if (this.cardModuleInfo.isCardModuleMode) {
        this.parentDeviceList = this.dataSource.filter((item) => item.id == this.cardModuleInfo.thietbicha_id)
        return
      }

      // Nếu không phải module mode thì set lại giá trị cho parentDeviceList
      this.parentDeviceList = this.dataSource.filter(
        (item) =>
          (item.id !== this.currentItem.id && item.loaithietbi_id == this.currentItem.loaithietbi_id) ||
          item.id == this.currentItem.thietbicha_id
      )
    },
    setCardModuleInfoDefault() {
      this.cardModuleInfo = { thietbicha_id: null, portType: 0, isCardModuleMode: false }
    },
    async getDeviceRelationInfo(deviceId) {
      const [
        thietBiDauNoi,
        thietBiInfra,
        thietBiLichSu
        // danhSachIP
      ] = await Promise.all([
        this.getThietBiDauNoi(deviceId),
        this.getThietBiInfra(deviceId),
        this.getThietBiLichSu(deviceId)
        // this.getDanhSachIP(deviceId)
      ])

      this.connectorList = thietBiDauNoi
      this.infraList = thietBiInfra
      this.deviceRelationHistory = thietBiLichSu
      // this.IPAddressList = danhSachIP

      if (this.isDiskShelf) {
        const [khayDia, controller, VM, NFS] = await Promise.all([
          this.getKhayDia(deviceId),
          this.getController(deviceId),
          this.getVM(deviceId),
          this.getNFS(deviceId)
        ])
        this.shelvesList = khayDia
        console.log('controller', controller)
        this.controllerList = controller
        this.VMList = VM
        this.NFSList = NFS
      } else {
        // chia port khai bao va port
        const [portKhaiBao, thietBiPort] = await Promise.all([
          this.getThietBiPortKhaiBao(deviceId),
          this.getThietBiPort(deviceId)
        ])
        const portInterface = []
        const portPower = []
        const portConsole = []
        const portTypeInterface = []
        const portTypePower = []
        const portTypeConsole = []

        portKhaiBao.forEach((item) => {
          if (item.phanloai == PORT_TYPE.INTERFACE.ID) {
            portTypeInterface.push(item)
          } else if (item.phanloai == PORT_TYPE.POWER.ID) {
            portTypePower.push(item)
          } else if (item.phanloai == PORT_TYPE.CONSOLE.ID) {
            portTypeConsole.push(item)
          }
        })

        thietBiPort.forEach((item) => {
          if (item.phanloai == PORT_TYPE.INTERFACE.ID) {
            portInterface.push(item)
          } else if (item.phanloai == PORT_TYPE.POWER.ID) {
            portPower.push(item)
          } else if (item.phanloai == PORT_TYPE.CONSOLE.ID) {
            portConsole.push(item)
          }
        })

        this.portInterfaceList = portInterface
        this.portInterfaceList.forEach((item, index) => {
          item.trangthai_sd_text = this.getTrangThaiText(item.trangthai_sd)
        });
        this.portPowerList = portPower
        this.portConsoleList = portConsole
        this.portTypeInterface = portTypeInterface
        this.portTypePower = portTypePower
        this.portTypeConsole = portTypeConsole

        // lay cac thiet bi la card
        this.cardList = JSON.parse(JSON.stringify(this.dataSource))
          .filter((el) => el.thietbicha_id == deviceId && (el.is_interface || el.is_power || el.is_console))
          .map((el, index) => ({ ...el, tempId: index }))
      }
      this.updateParentDeviceList()
    },
    enableControls(args) {
      // Enable/Disable cho form thông tin chung
      Object.keys(this.thongTinChungControls).forEach((key) => {
        if (this.thongTinChungControls[key].hasOwnProperty('enabled')) {
          this.thongTinChungControls[key].enabled = args
        }
      })
      // Check chung loai thiet bi
      this.thongTinChungControls.select_chungloaithietbi_id.enabled = !(
        this.currentItem.id && this.currentItem.chungloaithietbi_id
      )
      // Enable/Disable cho form thông tin lắp đặt
      Object.keys(this.thongTinLapDatControls).forEach((key) => {
        if (this.thongTinLapDatControls[key].hasOwnProperty('enabled')) {
          this.thongTinLapDatControls[key].enabled = args
        }
      })
      // Enable/Disable cho form thông tin quản lý
      Object.keys(this.thongTinQuanLyControls).forEach((key) => {
        if (this.thongTinQuanLyControls[key].hasOwnProperty('enabled')) {
          this.thongTinQuanLyControls[key].enabled = args
        }
      })

      this.$refs.DeviceCharacteristics.enableControls(args)
      this.$refs.StorageCapacity.enableControls(args)
      this.$refs.PortType.enableControls(args)

      this.buttons.btnSave.enabled = args
      this.buttons.btnNew.enabled = !args
      this.buttons.btnCancel.enabled = args
      if (this.currentItem.id > 0) {
        this.buttons.btnEdit.enabled = !this.buttons.btnSave.enabled
      }
    },
    getHieuLucText(value) {
      return value == 1 ? 'Hiệu lực' : 'Không hiệu lực'
    },
    getTrangThaiText(value) {
      return value == 1 ? 'Đã sử dụng' : 'Chưa sử dụng'
    },
    getCustomerText(value) {
      return CUSTOMER_TYPES.find((item) => item.id === value)?.name
    },
    getLoaiThietBiText(value) {
      return this.deviceTypeList.find((item) => item.ID === value)?.TEN ?? ''
    },
    getColumnKhachHangTemplate() {
      return () => {
        return {
          template: {
            template: `<span>{{ getText() }}</span>`,
            methods: {
              getText() {
                return this.$parent.$parent.$parent.getCustomerText(this.data.loaikhachhang)
              }
            }
          }
        }
      }
    },
    getColumnLoaiThietBiTemplate() {
      return () => {
        return {
          template: {
            template: `<span>{{ getText() }}</span>`,
            methods: {
              getText() {
                return this.$parent.$parent.$parent.getLoaiThietBiText(this.data.loaithietbi_id)
              }
            }
          }
        }
      }
    },
    getColumnHieuLucTemplate() {
      return () => {
        return {
          template: {
            template: `<span>{{ getText() }}</span>`,
            methods: {
              getText() {
                return this.$parent.$parent.$parent.getHieuLucText(this.data.hieu_luc)
              }
            }
          }
        }
      }
    },
    rowSelected(args) {
      // Trường hợp khi tạo module card
      if (this.cardModuleInfo.isCardModuleMode) {
        this.onNewClick()
        this.currentItem.thietbicha_id = this.cardModuleInfo.thietbicha_id
        // this.cardModuleInfo.isCardModuleMode = false
      } else {
        this.setCurrentItem(args.data)
      }
      this.$refs.StorageCapacity.loadDataFromParent(this.currentItem)
      this.ganIPList(args.data.id)
    },
    rowUnselected() {
      const items = this.gridItems.getSelectedRecords()
      if (!(items == null || items.length == 0)) {
        this.setCurrentItem(items[0])
      } else {
        this.setDefaultItem()
      }
    },
    setCurrentItem(item) {
      // for (const [key, value] of Object.entries(item)) {
      //   this.currentItem[key] = value
      // }
      this.currentItem = { ...NEW_DEFAULT_OBJ, ...item }
    },
    setDefaultItem() {
      this.currentItem = { ...NEW_DEFAULT_OBJ }
      // reset lại các thông tin liên quan
      //---- Device Relation Info ----//
      this.connectorList = []
      this.infraList = []
      this.colocationList = []
      this.deviceRelationHistory = []
      //---- Allocation Info ------//
      this.VMList = []
      this.NFSList = []
      this.controllerList = []
      this.shelvesList = []
      // allocationHistory: []
      //---- Thông tin khai báo loại cổng ------//
      this.portTypeInterface = []
      this.portTypePower = []
      this.portTypeConsole = []
      this.portInterfaceList = []
      this.portPowerList = []
      this.portConsoleList = []
      //----- Thong tin card ------------//
      this.cardList = []
    },
    validateControls() {
      let msg = ''
      if (this.thongTinChungControls.txt_ten.invalid) {
        msg = 'Tên thiết bị không được để trống.'
      }
      if (this.currentItem.ten != this.currentItem.ten.trim()) {
        msg = 'Tên thiết bị không được có khoảng trống đầu và cuối'
      }
      if (this.thongTinChungControls.select_loaithietbi_id.invalid) {
        if (msg != '') msg += '\n\r'
        msg += 'Loại thiết bị không được để trống.'
      }
      if (this.isDiskShelf) {
        if (this.thongTinChungControls.select_loaitudia_id.invalid) {
          if (msg != '') msg += '\n\r'
          msg += 'Loại tủ đĩa không được để trống.'
        }
        msg += this.$refs.StorageCapacity.validateControls()
      }
      if (this.isNetwork && this.thongTinChungControls.select_chungloaithietbi_id.invalid) {
        if (msg != '') msg += '\n\r'
        msg += 'Chủng loại thiết bị không được để trống.'
      }
      msg += this.$refs.DeviceCharacteristics.validateControls()
      if (msg != '') this.$root.toastError(msg)
      return msg == ''
    },
    onNewClick() {
      this.setDefaultItem()
      this.enableControls(true)
    },
    async onSaveClick() {
      if (this.validateControls()) {
        let id = null
        let flag = await this.$confirm('Có chắc bạn muốn lưu lại thiết bị đang chọn?', 'Xác nhận thực hiện', {
          confirmButtonText: 'Có',
          cancelButtonText: 'Không',
          type: 'info',
          dangerouslyUseHTMLString: true
        })
          .then((s) => true)
          .catch((e) => false)
        if (flag) {
          const portKhaiBao = []
          if (!this.currentItem.so_linecard) {
            portKhaiBao.push(...this.portTypeInterface)
          }
          if (!this.currentItem.so_power) {
            portKhaiBao.push(...this.portTypePower)
          }
          if (!this.currentItem.so_controlcard) {
            portKhaiBao.push(...this.portTypeConsole)
          }
          let nangLucLuuTru = this.$refs.StorageCapacity.getData()
          if (nangLucLuuTru) {
            this.currentItem.hdd_tong = nangLucLuuTru.hdd_tong
            this.currentItem.hdd_nangluc = nangLucLuuTru.hdd_nangluc
            this.currentItem.hdd_capphat = nangLucLuuTru.hdd_capphat
            this.currentItem.ssd_tong = nangLucLuuTru.ssd_tong
            this.currentItem.ssd_nangluc = nangLucLuuTru.ssd_nangluc
            this.currentItem.ssd_capphat = nangLucLuuTru.ssd_capphat
            this.currentItem.iops_tong = nangLucLuuTru.iops_tong
            this.currentItem.iops_nangluc = nangLucLuuTru.iops_nangluc
            this.currentItem.iops_capphat = nangLucLuuTru.iops_capphat
            this.currentItem.mem_cache = nangLucLuuTru.mem_cache
            this.currentItem.flash_cache = nangLucLuuTru.flash_cache
          }
          const dataToSend = {
            ...this.currentItem,
            khaibao_port: portKhaiBao,
            cards: this.cardList,
          }
          try {
            this.$root.showLoading(true)
            if (dataToSend.id == 0) {
              let rs = await this.addNUpdateItem(dataToSend)
              if (rs) {
                this.$root.toastSuccess('Thêm thiết bị thành công')
                id = rs
              } else {
                this.$root.toastError('Thêm thiết bị thất bại')
              }
              this.enableControls(!rs)
            } else {
              let rs = await this.addNUpdateItem(dataToSend)
              if (rs) {
                this.$root.toastSuccess('Cập nhật thiết bị thành công')
                id = dataToSend.id
              } else {
                this.$root.toastError('Cập nhật thiết bị thất bại')
              }
              this.enableControls(!rs)
            }
          } finally {
            // Đặt trạng thái module về mặc định nếu có
            if (this.cardModuleInfo.isCardModuleMode) {
              this.setCardModuleInfoDefault()
            }

            this.$root.showLoading(false)
            this.enableControls(false)
            await this.loadInfo()
          }
        }

        // Dùng cho thêm card và module
        return { flag, id }
      }
    },
    onCancelClick() {
      // Đặt trạng thái module về mặc định nếu có
      if (this.cardModuleInfo.isCardModuleMode) {
        this.setCardModuleInfoDefault()
      }

      // Load lại thông tin thiết bị
      let list = this.gridItems.getSelectedRecords()
      let item = null
      if (!(list == null || list.length == 0)) {
        if (!this.currentItem.id) {
          item = list[0]
        } else {
          item = list.find((x) => x.id == this.currentItem.id)
        }
      }
      if (item != null) {
        this.setCurrentItem(item)
      } else {
        this.setDefaultItem()
      }
      this.enableControls(false)
    },
    onEditClick() {
      this.enableControls(true)
    },
    async onDeleteClick() {
      let flag = await this.$confirm('Có chắc bạn muốn xóa dữ liệu không?', 'Xác nhận thực hiện', {
        confirmButtonText: 'Có',
        cancelButtonText: 'Không',
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
          let items = this.gridItems.getSelectedRecords()
          if (!(items == null || items == undefined)) {
            let ids = []
            items.forEach((item) => {
              ids.push(item.id)
            })
            rs = await this.deleteItems(ids)
          }
          if (rs) {
            this.setDefaultItem()
            this.enableControls(false)
            this.$root.toastSuccess('Xóa thông tin thiết bị thành công')
          } else {
            this.$root.toastError('Xóa thông tin thiết bị thất bại')
          }
        } finally {
          this.$root.showLoading(false)
          await this.loadInfo()
        }
      }
    },
    // ================= PROVIDE/INJECT ============
    deletePortTypeListItem(type, item) {
      switch (type) {
        case PORT_TYPE.INTERFACE.NAME:
          this.portTypeInterface = this.portTypeInterface.filter((el) => el.tmpId != item.tmpId || el.id != item.id)
          break
        case PORT_TYPE.POWER.NAME:
          this.portTypePower = this.portTypePower.filter((el) => el.tmpId != item.tmpId || el.id != item.id)
          break
        case PORT_TYPE.CONSOLE.NAME:
          this.portTypeConsole = this.portTypeConsole.filter((el) => el.tmpId != item.tmpId || el.id != item.id)
          break
      }
    },
    setCurrentItemValByProp(prop, value) {
      this.currentItem[prop] = value
    },
    async createCardModule(type) {
      try {
        this.$refs.modalThemCard.addCard({ thietbicha_id: this.currentItem.id, portType: type })
      } catch (error) {
        this.$root.toastError('Lỗi khởi tạo card hoặc module')
      }
    },
    async updateCardModule(item) {
      try {
        this.$refs.modalThemCard.updateCard(item)
      } catch (error) {
        this.$root.toastError('Lỗi cập nhật card hoặc module')
      }
    },
    async deleteCardItem(item) {
      let flag = await this.$confirm('Có chắc bạn muốn xóa dữ liệu đang chọn?', 'Xác nhận thực hiện', {
        confirmButtonText: 'Có',
        cancelButtonText: 'Không',
        type: 'info',
        dangerouslyUseHTMLString: true
      })
        .then((s) => true)
        .catch((e) => false)
      if (flag) {
        try {
          let rs = true
          if (item.id) {
            rs = this.deleteItems([item.id])
            this.dataSource = this.dataSource.filter((el) => {
              return el.id != item.id
            })
          }
          this.cardList = this.cardList.filter((el) => {
            return (item.id && el.id != item.id) || (item.tempId && el.tempId != item.tempId)
          })
          if (!rs) throw new Error('Lỗi api')
          this.$root.toastSuccess('Xóa thông tin thành công')
        } catch (error) {
          this.$root.toastError('Lỗi không xóa được thông tin')
        }
      }
    },
    // ================= END ===================
    handleModalThemCard(data) {
      if (data.id) {
        // update với bản ghi có sẵn
        const index = this.cardList.findIndex((el) => el.id == data.id)
        this.cardList.splice(index, 1, { ...data })
      } else {
        const index = this.cardList.findIndex((el) => el.tempId == data.tempId)
        if (index != -1) {
          // update
          this.cardList.splice(index, 1, { ...data })
        } else {
          // Gán tmpId mới
          const lastTmpId = this.cardList.length ? this.cardList[this.cardList.length - 1].tempId ?? 0 : 0
          data.tempId = lastTmpId + 1
          this.cardList = [...this.cardList, { ...data }]
        }
      }
    },
    // ================= CRUD =================
    async addNUpdateItem(args) {
      let result = false
      try {
        let rs = await this.$root.context.post(`${ROOT_API}/thong-tin-thiet-bi`, args)
        result = rs.data
      } catch (error) {
        this.handleErrorResponse(error)
      }
      return result
    },
    async deleteItems(ids) {
      let result = false
      try {
        let rs = await this.$root.context.post('/web-ecms/thong-tin-thiet-bi/delete-items', {
          id: ids
        })
        result = rs.data
      } catch (error) {
        this.handleErrorResponse(error)
      }
      return result
    },
    // ================ END ======================
    async getDeviceList() {
      try {
        let rs = await this.$root.context.get('/web-ecms/thong-tin-thiet-bi')
        // this.dataSource = rs.data
        return rs.data
      } catch (error) {
        this.handleErrorResponse(error)
      }
    },
    async getDeviceType() {
      try {
        let rs = await this.$root.context.get('/web-ecms/danh-muc-loai-thiet-bi/get-all?validity=1')
        // this.deviceTypeList = rs.data
        return rs.data
      } catch (error) {
        this.handleErrorResponse(error)
      }
    },
    async getIDC() {
      try {
        let rs = await this.$root.context.get('/web-ecms/danhmuc/ha-tang-idc')
        // this.IDCList = rs.data
        return rs.data
      } catch (error) {
        this.handleErrorResponse(error)
      }
    },
    async getIDCBuilding(idcID = null) {
      try {
        let rs = await this.$root.context.get(`/web-ecms/danhmuc/ha-tang-idc-toa-nha?idc_id=${idcID ?? ''}`)
        // this.IDCBuildingList = rs.data
        return rs.data
      } catch (error) {
        this.handleErrorResponse(error)
      }
    },
    async getIDCFloor(idcBuildingID = null) {
      try {
        let rs = await this.$root.context.get(
          `/web-ecms/danhmuc/ha-tang-idc-mat-san?building_id=${idcBuildingID ?? ''}`
        )
        // this.IDCFloorList = rs.data
        return rs.data
      } catch (error) {
        this.handleErrorResponse(error)
      }
    },
    async getIDCZone(idcFloorID = null) {
      try {
        let rs = await this.$root.context.get(`/web-ecms/danhmuc/ha-tang-idc-zone?floor_id=${idcFloorID ?? ''}`)
        // this.IDCZoneList = rs.data
        return rs.data
      } catch (error) {
        this.handleErrorResponse(error)
      }
    },
    async getIDCRack(idcZoneID = null) {
      try {
        let rs = await this.$root.context.get(`/web-ecms/danhmuc/ha-tang-idc-rack?zone_id=${idcZoneID ?? ''}`)
        // this.IDCZoneList = rs.data
        return rs.data
      } catch (error) {
        this.handleErrorResponse(error)
      }
    },
    async getThietBiDauNoi(id) {
      try {
        let rs = await this.$root.context.get(`/web-ecms/thong-tin-thiet-bi/${id}/thiet-bi-dau-noi`)
        // this.connectorList = rs.data
        return rs.data
      } catch (error) {
        this.handleErrorResponse(error)
      }
    },
    async getThietBiInfra(id) {
      try {
        let rs = await this.$root.context.get(`/web-ecms/thong-tin-thiet-bi/${id}/thiet-bi-ha-tang`)
        // this.infraList = rs.data
        return rs.data
      } catch (error) {
        this.handleErrorResponse(error)
      }
    },
    async getThietBiLichSu(id) {
      try {
        let rs = await this.$root.context.get(`/web-ecms/thong-tin-thiet-bi/${id}/thiet-bi-lich-su`)
        // this.deviceRelationHistory = rs.data
        return rs.data
      } catch (error) {
        this.handleErrorResponse(error)
      }
    },
    async getChungLoaiThietBi() {
      try {
        let rs = await this.$root.context.post(`/web-ecms/chungloai-thietbi/lay-ds-chungloai-thietbi`)
        return rs.data
      } catch (error) {
        this.handleErrorResponse(error)
      }
    },
    async getDanhSachIP(id) {
      if (!id) return []
      try {
        let rs = await this.$root.context.get(`/web-ecms/thong-tin-thiet-bi/${id}/thiet-bi-ip`)
        return rs.data
      } catch (error) {
        this.handleErrorResponse(error)
      }
    },
    async getDanhMucLoaiTuDia() {
      try {
        let rs = await this.$root.context.post(`/web-ecms/danhmuc/loai-tu-dia//danhsach`)
        return rs.data
      } catch (error) {
        this.handleErrorResponse(error)
      }
    },
    async getKhayDia(id) {
      try {
        let rs = await this.$root.context.get(`/web-ecms/khaydia-tudia/get-by-thietbi-id?id=${id}`)
        return rs.data
      } catch (error) {
        this.handleErrorResponse(error)
      }
    },
    async getController(id) {
      try {
        let rs = await this.$root.context.get(`/web-ecms/ha-tang/bo-dieu-khien?tb_id=${id}`)
        return rs.data
      } catch (error) {
        this.handleErrorResponse(error)
      }
    },
    async getVM(id) {
      try {
        let rs = await this.$root.context.get(`/web-ecms/thong-tin-thiet-bi/${id}/vm`)
        return rs.data
      } catch (error) {
        this.handleErrorResponse(error)
      }
    },
    async getNFS(id) {
      try {
        let rs = await this.$root.context.get(`/web-ecms/thong-tin-thiet-bi/${id}/nfs`)
        // this.NFSList = rs.data
        return rs.data
      } catch (error) {
        this.handleErrorResponse(error)
      }
    },
    async getThietBiPortKhaiBao(id) {
      try {
        let rs = await this.$root.context.get(`/web-ecms/thong-tin-thiet-bi/${id}/port-khai-bao`)
        return rs.data
      } catch (error) {
        this.handleErrorResponse(error)
      }
    },
    async getThietBiPort(id) {
      try {
        let rs = await this.$root.context.get(`/web-ecms/thong-tin-thiet-bi/${id}/thiet-bi-port`)
        return rs.data
      } catch (error) {
        this.handleErrorResponse(error)
      }
    },
    async loadThongTinThietBi(idDevice, isViewOnly = true) {
      try {
        if (this.dataSource.length === 0) {
          this.$root.showLoading(true)
          await this.loadInfo()
        }

        const item = this.dataSource.find((x) => x.id == idDevice)
        if (isViewOnly) {
          this.isViewOnly = true
        }
        setTimeout(async () => {
          if (item) {
            const originalRowSelected = this.rowSelected
            this.rowSelected = () => { }

            if (this.$refs.grdItems) {
              this.$refs.grdItems.selectRows([idDevice])
            }

            this.setCurrentItem(item)
            await this.getDeviceRelationInfo(idDevice)

            this.$nextTick(() => {
              this.rowSelected = originalRowSelected
            })
          } else {
            this.$root.toastError('Không tìm thấy thông tin thiết bị')
          }
        }, 300)
      } catch (error) {
        this.handleErrorResponse(error)
      } finally {
        this.$root.showLoading(false)
      }
    },
    updateListPort() {
      this.getDeviceRelationInfo(this.currentItem.id)
    }
  }
}
