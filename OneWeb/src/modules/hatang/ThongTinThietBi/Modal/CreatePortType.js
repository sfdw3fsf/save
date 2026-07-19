import { DEFAULT_PORT_TYPE, PORT_TYPE } from '../test.js'

export default {
  name: 'CreatePortType',
  props: {
    portType: {
      type: Number,
      default: 1
    },
    portTypeList: {
      type: Array,
      default: () => []
    }
  },
  data() {
    return {
      target: '.main-wrapper',
      animationSettings: { effect: 'Zoom' },
      loaiCongSelect: [],
      mucDichSelect: [],
      bangThongSelect: [],
      ketNoiSelect: [],
      moduleSelect: [],
      portTypeData: {
        id: null,
        thietbi_id: null,
        phanloai: 0,
        loaicong_id: 0,
        so_cong: 0,
        bat_dau: 0,
        port_fname: '',
        mucdichsudung_cong_id: null,
        bangthongcong_id: null,
        thongtinmodule_id: null,
        ketnoi: null, // 0 | 1 => Downlink | Uplink
        // temp data
        tmpId: null
      },
      controls: {
        select_loaicong_id: {
          enabled: false,
          invalid: false
        },
        txt_so_cong: {
          enabled: false,
          invalid: false
        },
        txt_bat_dau: {
          enabled: false,
          invalid: false
        },
        txt_port_fname: {
          enabled: false,
          invalid: false
        },
        select_mucdichsudung_cong_id: {
          enabled: false
        },
        select_bangthongcong_id: {
          enabled: false
        },
        txt_thongtin_module: {
          enabled: false
        },
        select_ketnoi: {
          enabled: false
        }
      }
    }
  },
  watch: {
    'portTypeData.loaicong_id': function(val) {
      this.controls.select_loaicong_id.invalid = !val
    },
    'portTypeData.so_cong': function(val) {
      this.controls.txt_so_cong.invalid = !val
    },
    'portTypeData.bat_dau': function(val) {
      this.controls.txt_bat_dau.invalid = val === ''
    },
    'portTypeData.port_fname': function(val) {
      this.controls.txt_port_fname.invalid = val === ''
    }
  },
  computed: {
    isInterface() {
      return this.portType == PORT_TYPE.INTERFACE.ID
    },
    portTypeName() {
      switch (this.portType) {
        case PORT_TYPE.INTERFACE.ID:
          return PORT_TYPE.INTERFACE.NAME
        case PORT_TYPE.POWER.ID:
          return PORT_TYPE.POWER.NAME
        case PORT_TYPE.CONSOLE.ID:
          return PORT_TYPE.CONSOLE.NAME
      }
    },
    portTypeSelect() {
      if (this.loaiCongSelect.length > 0) {
        const newList = this.loaiCongSelect.filter((item) => {
          return item.PHANLOAI == this.portType
        })
        return newList
      }
      return []
    },
    modalRef() {
      return this.$refs.modalPortType
    }
  },
  async created() {
    await Promise.all([this.getLoaiCong(), this.getMucDichSuDung(), this.getBangThong(), this.getThongtinModule()])
    this.ketNoiSelect = [
      {
        id: 0,
        name: 'DownLink'
      },
      {
        id: 1,
        name: 'Uplink'
      }
    ]
  },
  methods: {
    dialogOpen() {
      this.modalRef.show()

      this.controls.select_loaicong_id.invalid = !this.portTypeData.loaicong_id
      this.controls.txt_so_cong.invalid = !this.portTypeData.so_cong
      this.controls.txt_bat_dau.invalid = isNaN(this.portTypeData.bat_dau) || this.portTypeData.bat_dau === ''
      this.controls.txt_port_fname.invalid = this.portTypeData.port_fname === ''
    },
    closeDialog() {
      this.$refs.modalPortType.hide()

      this.portTypeData = { ...DEFAULT_PORT_TYPE }
    },
    validateControls() {
      let msg = ''
      if (this.controls.select_loaicong_id.invalid) {
        msg = 'Loại cổng không được để trống.'
      }
      if (this.controls.txt_so_cong.invalid) {
        if (msg != '') msg += '\n\r'
        msg += 'Số cổng không được để trống.'
      }
      if (this.controls.txt_bat_dau.invalid) {
        if (msg != '') msg += '\n\r'
        msg += 'Bắt đầu từ không được để trống.'
      }
      if (this.controls.txt_port_fname.invalid) {
        if (msg != '') msg += '\n\r'
        msg += 'Định dạng tên không được để trống.'
      }
      if (msg != '') this.$root.toastError(msg)
      return msg == ''
    },
    onSave() {
      if (!this.validateControls()) return

      if (!this.portTypeData.id && !this.portTypeData.tmpId) {
        this.createPortType()
      } else {
        this.updatePortType()
      }
      this.closeDialog()
    },
    createPortType() {
      // Gán tmpId mới
      const lastTmpId = this.portTypeList.length ? this.portTypeList[this.portTypeList.length - 1].tmpId ?? 0 : 0
      this.portTypeData.tmpId = lastTmpId + 1
      this.portTypeData.phanloai = this.portType

      this.portTypeList.push({ ...this.portTypeData })
    },
    updatePortType() {
      const index = this.portTypeList.findIndex(
        (item) => item.id == this.portTypeData.id || item.tmpId == this.portTypeData.tmpId
      )

      if (index === -1) {
        return this.$root.toastError('Không tìm thấy item cần cập nhật.')
      }

      this.portTypeList.splice(index, 1, {
        ...this.portTypeList[index],
        ...this.pickPortTypeData(this.portTypeData)
      })
    },
    pickPortTypeData(portTypeData) {
      return {
        loaicong_id: portTypeData.loaicong_id,
        so_cong: portTypeData.so_cong,
        bat_dau: portTypeData.bat_dau,
        port_fname: portTypeData.port_fname,
        mucdichsudung_cong_id: portTypeData.mucdichsudung_cong_id,
        bangthongcong_id: portTypeData.bangthongcong_id,
        thongtinmodule_id: portTypeData.thongtinmodule_id,
        ketnoi: portTypeData.ketnoi
      }
    },
    setPortTypeData(data) {
      this.portTypeData = { ...data }
    },
    async getLoaiCong() {
      try {
        let rs = await this.$root.context.post(`/web-ecms/hatang/loai-cong/get-items`)
        this.loaiCongSelect = rs.data
      } catch (error) {
        this.handleErrorResponse(error)
      }
    },
    async getMucDichSuDung() {
      try {
        let rs = await this.$root.context.post(`/web-ecms/hatang/mucdich-sudung-cong/get-items`)
        this.mucDichSelect = rs.data
      } catch (error) {
        this.handleErrorResponse(error)
      }
    },
    async getBangThong() {
      try {
        let rs = await this.$root.context.post(`/web-ecms/hatang/bangthong-cong/get-items`)
        this.bangThongSelect = rs.data
      } catch (error) {
        this.handleErrorResponse(error)
      }
    },
    async getThongtinModule() {
      try {
        let rs = await this.$root.context.post(`/web-ecms/danhmuc/thong-tin-module/danhsach`)
        this.moduleSelect = rs.data
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
    }
  }
}
