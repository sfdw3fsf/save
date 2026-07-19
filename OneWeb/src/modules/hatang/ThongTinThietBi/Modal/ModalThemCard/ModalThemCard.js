import DacTinhThietBi from '../../Components/DacTinhThietBi/index.vue'
import { CUSTOMER_TYPES, DEFAULT_OBJ, LOAI_THIET_BI, PORT_TYPE, ROOT_API } from '../../test'
import moment from 'moment'
import PortType from '../../Components/PortType/index.vue'

export default {
  name: 'ModalThemCard',
  components: {
    DacTinhThietBi,
    PortType
  },
  provide() {
    return {
      deletePortTypeItem: this.deletePortTypeListItem
    }
  },
  props: {},
  data() {
    return {
      target: '.main-wrapper',
      animationSettings: { effect: 'Zoom' },
      isController: false,
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
      UpperUList: [],
      LowerUList: [],
      //---- Thông tin quản lý ------//
      donViQuanLyList: [],
      phongBanQuanLyList: [],
      nguoiQuanLyList: [],
      //---- Thông tin khai báo loại cổng ------//
      portTypeInterface: [],
      portTypePower: [],
      portTypeConsole: [],
      portInterfaceList: [],
      portPowerList: [],
      portConsoleList: [],
      //------ Thông tin danh sách card ------//
      interfaceList: [],
      powerList: [],
      consoleList: [],
      //---- Current Item -----//
      currentItem: {
        tempId: null,
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
        chungloaithietbi_id: null,
        ip_ids: [],
        // Đặc tính thiết bị
        vaitrothietbi_ids: [],
        psu_qty_in: 0,
        psu_qty_out: 0,
        cauhinh: '',
        hangsx_id: null,
        namsx: null,
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
        is_khaydia: null,
        khaibao_port: []
      },
      //------------------------//
      thongTinChungControls: {
        txt_ten: {
          enabled: true,
          invalid: true
        },
        txt_kyhieu: {
          enabled: true
        },
        txt_mathietbi_infra: {
          enabled: true
        },
        select_loaithietbi_id: {
          enabled: false,
          invalid: false
        },
        select_loaikhachhang: {
          enabled: true
        },
        txt_serial_number: {
          enabled: true
        },
        checkbox_hsm: {
          enabled: true
        },
        checkbox_hieu_luc: {
          enabled: true
        },
        txt_ghi_chu: {
          enabled: true
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
          enabled: true
        },
        txt_part_number: {
          enabled: true
        },
        txt_model: {
          enabled: true
        },
        select_thietbicha_id: {
          enabled: false
        }
      },
      thongTinLapDatControls: {
        select_idc_id: {
          enabled: true
        },
        select_building_id: {
          enabled: true
        },
        select_floor_id: {
          enabled: true
        },
        select_zone_id: {
          enabled: true
        },
        select_rack_id: {
          enabled: true
        },
        txt_unit_qty: {
          enabled: true
        },
        select_up_unit_id: {
          enabled: true
        },
        select_down_unit_id: {
          enabled: true
        }
      },
      thongTinQuanLyControls: {
        txt_project_code: {
          enabled: true
        },
        txt_project_name: {
          enabled: true
        },
        txt_so_the_ts: {
          enabled: true
        },
        select_project_id: {
          enabled: true
        },
        select_donviqly_id: {
          enabled: true
        },
        select_phongbanqly_id: {
          enabled: true
        },
        select_nguoiqly_id: {
          enabled: true
        }
      },
      deviceType: 'KHAC',
      // Dùng cho thêm card và module
      cardModuleInfo: {
        thietbicha_id: null,
        portType: 0,
        isCardModuleMode: true
      },
      normalizer(node) {
        return {
          id: node.ID,
          label: node.IP
        }
      },
      _initialData: {}
    }
  },
  watch: {
    'currentItem.ten': function (val) {
      this.thongTinChungControls.txt_ten.invalid = !val
    },
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
    }
  },
  computed: {
    modalRef() {
      return this.$refs.modalThemCard
    },
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
    dacTinhThietBiRef() {
      return this.$refs.DeviceCharacteristics
    }
  },
  async created() {
    // Lưu toàn bộ snapshot của data ban đầu (deep clone để không bị tham chiếu)
    this._initialData = JSON.parse(JSON.stringify(this.$data))
  },
  methods: {
    async addCard(args = { thietbicha_id: null, portType: 1, id: null, tempId: null }) {
      this.modalRef.show()
      try {
        this.$root.showLoading(true)
        await this.loadInfo(args.thietbicha_id)

        if (args.id) {
          this.loadThietBiInfo(args.id)
        } else {
          this.currentItem.thietbicha_id = args.thietbicha_id
          this.currentItem.loaithietbi_id = this.deviceTypeList[0].ID

          if (args.portType == PORT_TYPE.INTERFACE.ID) {
            this.currentItem.is_interface = 1
          } else if (args.portType == PORT_TYPE.POWER.ID) {
            this.currentItem.is_power = 1
          } else if (args.portType == PORT_TYPE.CONSOLE.ID) {
            this.currentItem.is_console = 1
          }
        }
        this.cardModuleInfo.thietbicha_id = args.thietbicha_id
        this.cardModuleInfo.portType = args.portType

        this.dacTinhThietBiRef.enableControls(true)
      } catch (error) {
        this.$root.toastError('Lỗi khởi tạo thông tin')
      } finally {
        this.$root.showLoading(false)
      }
    },
    async updateCard(item) {
      this.modalRef.show()
      try {
        this.$root.showLoading(true)
        await this.loadInfo(item.thietbicha_id)
        if (item.id) {
          // lấy thông tin port của item và chia ra thanh các phần dữ liệu
          const thietBiPort = await this.getThietBiPort(item.id)
          const { portInterface, portPower, portConsole } = this.separatePort(thietBiPort)
          this.portInterfaceList = portInterface
          this.portPowerList = portPower
          this.portConsoleList = portConsole
        }

        if (!item.khaibao_port) {
          const portKhaiBao = await this.getThietBiPortKhaiBao(item.id)
          item.khaibao_port = portKhaiBao
        }

        // lấy dữ liệu khai báo từ item truyền vào và chia ra thành các phần dữ liệu
        const { portTypeInterface, portTypePower, portTypeConsole } = this.separatePortType(item.khaibao_port)
        this.portTypeInterface = portTypeInterface
        this.portTypePower = portTypePower
        this.portTypeConsole = portTypeConsole

        this.currentItem = { ...this.currentItem, ...item }
        if (this.currentItem.is_interface) {
          this.cardModuleInfo.portType = PORT_TYPE.INTERFACE.ID
        } else if (this.currentItem.is_power) {
          this.cardModuleInfo.portType = PORT_TYPE.POWER.ID
        } else if (this.currentItem.is_console) {
          this.cardModuleInfo.portType = PORT_TYPE.CONSOLE.ID
        }
        this.dacTinhThietBiRef.enableControls(true)
      } catch (error) {
        this.$root.toastError('Lỗi cập nhật thông tin')
      } finally {
        this.$root.showLoading(false)
      }
    },

    async callFromController(data) {
      this.isController = data.isController
      if (data.id) {
        let item = await this.getThietBiById(data.id)
        this.updateCard(item)
      }
      else {
        this.addCard(data)
      }
    },

    closeDialog() {
      this.resetAllData()
      this.modalRef.hide()
    },
    resetAllData() {
      Object.keys(this._initialData).forEach((key) => {
        if (key !== '_initialData') {
          this[key] = JSON.parse(JSON.stringify(this._initialData[key]))
        }
      })
    },
    onSave() {
      if (this.validateControls()) {
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
        this.currentItem.khaibao_port = portKhaiBao

        // nếu từ màn controller gọi modal này thì gọi hàm lưu thiết bị
        if (this.isController) {
          this.capnhatThietBi(this.currentItem)
        }

        this.$emit('send-data', this.currentItem)
        this.closeDialog()
      }
    },
    async loadThietBiInfo(thietBiId) {
      const tasks = [
        this.getThietBiById(thietBiId),
        this.getThietBiPortKhaiBao(thietBiId),
        this.getThietBiPort(thietBiId)
      ]
      const [thietbi, portKhaiBao, thietBiPort] = await Promise.all(tasks)
      this.currentItem = { ...this.currentItem, ...thietbi }
      const { portInterface, portPower, portConsole } = this.separatePort(thietBiPort)
      const { portTypeInterface, portTypePower, portTypeConsole } = this.separatePortType(portKhaiBao)

      this.portInterfaceList = portInterface
      this.portPowerList = portPower
      this.portConsoleList = portConsole
      this.portTypeInterface = portTypeInterface
      this.portTypePower = portTypePower
      this.portTypeConsole = portTypeConsole
    },
    async loadInfo(parrentID) {
      const tasks = [
        this.getDeviceList(),
        this.getDeviceType(),
        this.getDanhSachIP(),
        // thong tin lap dat thiet bi
        this.getIDC(),
        this.getIDCBuilding(),
        this.getIDCFloor(),
        this.getIDCZone()
      ]
      const [
        deviceList,
        deviceTypeList,
        IPList, // thong tin lap dat thiet bi
        IDC,
        IDCBuilding,
        IDCFloor,
        IDCZone
      ] = await Promise.all(tasks)

      const listChungLoaiKhac = deviceTypeList.filter((el) => el.PHANLOAI == LOAI_THIET_BI.KHAC)
      const listThietBiCha = deviceList.filter((el) => el.id == parrentID)
      this.parentDeviceList = listThietBiCha
      this.deviceTypeList = listChungLoaiKhac
      console.log(IPList, 'hieunph check')
      this.IPAddressList = IPList
      this.IDCList = IDC
      this.IDCBuildingList = IDCBuilding
      this.IDCFloorList = IDCFloor
      this.IDCZoneList = IDCZone
      this.customerTypeList = [...CUSTOMER_TYPES]
    },
    separatePortType(list) {
      const portTypeInterface = []
      const portTypePower = []
      const portTypeConsole = []
      list.forEach((el) => {
        if (el.phanloai == PORT_TYPE.INTERFACE.ID) {
          portTypeInterface.push(el)
        } else if (el.phanloai == PORT_TYPE.POWER.ID) {
          portTypePower.push(el)
        } else if (el.phanloai == PORT_TYPE.CONSOLE.ID) {
          portTypeConsole.push(el)
        }
      })
      return { portTypeInterface, portTypePower, portTypeConsole }
    },
    separatePort(list) {
      const portInterface = []
      const portPower = []
      const portConsole = []
      list.forEach((item) => {
        if (item.phanloai == PORT_TYPE.INTERFACE.ID) {
          portInterface.push(item)
        } else if (item.phanloai == PORT_TYPE.POWER.ID) {
          portPower.push(item)
        } else if (item.phanloai == PORT_TYPE.CONSOLE.ID) {
          portConsole.push(item)
        }
      })
      return { portInterface, portPower, portConsole }
    },
    validateControls() {
      let msg = ''
      if (this.thongTinChungControls.txt_ten.invalid) {
        msg = 'Tên thiết bị không được để trống.'
      }
      msg += this.dacTinhThietBiRef.validateControls()
      if (msg != '') this.$root.toastError(msg)
      return msg == ''
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
    // ================= END ==============
    async getThietBiById(id) {
      try {
        let rs = await this.$root.context.get(`${ROOT_API}/thong-tin-thiet-bi/${id}`)
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
    async getDanhSachIP(id = 0) {
      try {
        let rs = await this.$root.context.get(`/web-ecms/thong-tin-thiet-bi/${id}/thiet-bi-ip`)
        return rs.data.filter((item) => item.DA_GAN_MAYAO !== 1 && item.DA_GAN_THIETBI_KHAC !== 1 && item.DA_GAN_HATANG_BACKUP !== 1 && item.DA_GAN_HATANG_LUU_TRU !== 1)
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
    async getDeviceList() {
      try {
        let rs = await this.$root.context.get('/web-ecms/thong-tin-thiet-bi')
        // this.dataSource = rs.data
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
    handleErrorResponse(error) {
      if (!(error.response == null || error.response == undefined)) {
        if (!(error.response.data == null || error.response.data == undefined)) {
          this.$root.toastError(error.response.data.message_detail)
        }
      }
    },
    async capnhatThietBi(data) {
      try {
        let rs = await this.$root.context.post(`/web-ecms/thong-tin-thiet-bi`, data)
        this.$root.toastSuccess((data.id ? "Cập nhật" : "Thêm mới") + " thiết bị thành công");
      } catch (error) {
        this.handleErrorResponse(error)
      }
    },

  }
}
