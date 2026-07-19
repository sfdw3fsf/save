<template src="./index.template.html"></template>

<script>
export default {
  data() {
    return {
      id: this.$route.params.id,
      connectorList: [],
      connectorCols: [
        { fieldName: 'SOTHUTU', headerText: 'STT', allowFiltering: false, width: 100 },
        { fieldName: 'MATHIETBI', headerText: 'Mã thiết bị', allowFiltering: true },
        { fieldName: 'TENTHIETBI', headerText: 'Tên thiết bị', allowFiltering: true },
        { fieldName: 'LOAITHIETBIMANG', headerText: 'Loại thiết bị', allowFiltering: true },
        { fieldName: 'CHI_TIET', headerText: 'Chi tiết', allowFiltering: false, template: this.getColumnTemplate(this) }
      ],
      historyList: [],
      historyCols: [
        { fieldName: 'SOTHUTU', headerText: 'STT', allowFiltering: false, width: 100 },
        { fieldName: 'TACDONG', headerText: 'Tên tác động', allowFiltering: true },
        { fieldName: 'FIELD', headerText: 'Trường thông tin thay đổi', allowFiltering: true },
        { fieldName: 'OLD_VALUE', headerText: 'Giá trị cũ', allowFiltering: true },
        { fieldName: 'NEW_VALUE', headerText: 'Giá trị mới', allowFiltering: true },
        { fieldName: 'NGUOI_CN', headerText: 'Người thực hiện', allowFiltering: true },
        { fieldName: 'NGAY_CN', headerText: 'Thời điểm', allowFiltering: true }
      ],
      portPurposeData: [],
      portTypeData: [],
      portBandwidthData: [],
      moduleInfoData: [],
      connectData: [],
      tinhTrangHoatDongData: [],
      currentTab: 1, // 1: Thong tin đầu nối | 2: Lịch sử
      currentItem: {
        id: 0,
        parentDevice: 0,
        no: 0,
        portName: '',
        portClassification: 0,
        portPurpose: 0,
        validity: 0,
        portType: 0,
        portBandWidth: 0,
        moduleInfo: 0,
        connection: 0,
        usingState: 0,
        note: ''
      },
      selectedList: [],
      controls: {
        txt_sothutu: {
          enable: false
        },
        txt_tencong: {
          enable: false
        },
        comboboxPortPurpose: {
          enable: false
        },
        checkboxValidity: {
          enable: false
        },
        comboboxPortType: {
          enable: false
        },
        comboboxPortBandWidth: {
          enable: false
        },
        comboboxModuleInfo: {
          enable: false
        },
        comboboxConnection: {
          enable: false
        },
        comboboxUsingStatus: {
          enable: false
        },
        textNote: {
          enable: false
        }
      },
      buttons: {
        btnSave: {
          enabled: false
        },
        btnCancel: {
          enabled: false
        },
        btnEdit: {
          enabled: true
        }
      }
    }
  },
  updated() {},
  computed: {
    isInterface() {
      return this.currentItem.portClassification == 1 ?? false
      // return true
    },
    portType() {
      if (this.currentItem.portClassification == 1) {
        return 'Interface'
      } else if (this.currentItem.portClassification == 2) {
        return 'Power'
      } else if (this.currentItem.portClassification == 3) {
        return 'Power'
      } else {
        return 'Unknown'
      }
    }
  },
  watch: {},
  async created() {
    await this.load()
    this.connectData = [
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
    getColumnTemplate(parent) {
      return () => {
        return {
          template: {
            template: `
              <div style="display: flex; justify-content: center; align-items: center;">
                <button class="btn btn-second btn-detail" style="width: 50%;" @click="parent.handleDetailCLick(data)">Click</button>
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
    handleDetailCLick(data) {
      console.log(`🚀 handleDetailCLick ~ data`, data)
    },
    handleErrorResponse(error) {
      if (!(error.response == null || error.response == undefined)) {
        if (!(error.response.data == null || error.response.data == undefined)) {
          this.$root.toastError(error.response.data.message_detail)
        }
      }
    },
    enableControls(args) {
      Object.keys(this.controls).forEach((key) => {
        if (this.controls[key].hasOwnProperty('enable')) {
          this.controls[key].enable = args
        }
      })

      this.buttons.btnSave.enabled = args
      this.buttons.btnCancel.enabled = this.buttons.btnSave.enabled
      this.buttons.btnEdit.enabled = !this.buttons.btnSave.enabled
    },

    loadData(data) {
      console.log('data', data)
    },

    setCurrentItem(item) {
      this.currentItem.id = item.ID
      this.currentItem.parentDevice = item.THIETBI_ID
      this.currentItem.no = item.SOTHUTU
      this.currentItem.portName = item.TEN
      this.currentItem.portClassification = item.PHAN_LOAI
      this.currentItem.portPurpose = item.MUCDICHSUDUNG_CONG_ID
      this.currentItem.validity = item.HIEU_LUC
      this.currentItem.portType = item.LOAICONG_ID
      this.currentItem.portBandWidth = item.BANGTHONGCONG_ID
      this.currentItem.moduleInfo = item.THONGTINMODULE_ID
      this.currentItem.connection = item.KET_NOI
      this.currentItem.usingState = item.TRANG_THAI_SU_DUNG
      this.currentItem.note = item.GHI_CHU
    },
    validateControls() {},
    getHieuLucText(value) {
      return value == 1 ? 'Hiệu lực' : 'Không hiệu lực'
    },
    async load() {
      try {
        this.$root.showLoading(true)
        const generalInfo = [
          this.getPortType(),
          this.getPortPurpose(),
          this.getPortBandwidth(),
          this.getModuleInfo(),
          this.getTinhTrangHoatDong()
        ]
        const portInfo = [this.getItemById(), this.getHistory(), this.getConnector()]
        const [
          loaiCong,
          mucDichSuDungCong,
          bangThongCong,
          thongTinModule,
          tinhTrangHoatDong,
          congThietBi,
          lichSu,
          dauNoi
        ] = await Promise.all([...generalInfo, ...portInfo])
        this.portTypeData = loaiCong
        this.portPurposeData = mucDichSuDungCong
        this.portBandwidthData = bangThongCong
        this.moduleInfoData = thongTinModule
        this.tinhTrangHoatDongData = tinhTrangHoatDong
        this.setCurrentItem(congThietBi)
        this.connectorList = dauNoi
        this.historyList = lichSu
      } finally {
        this.$root.showLoading(false)
      }
    },
    async getPortType() {
      try {
        let rs = await this.$root.context.post(`/web-ecms/hatang/loai-cong/get-items`)
        // this.portTypeData = rs.data
        return rs.data
      } catch (error) {
        this.handleErrorResponse(error)
      }
    },
    async getPortPurpose() {
      try {
        let rs = await this.$root.context.post(`/web-ecms/hatang/mucdich-sudung-cong/get-items`)
        // this.portPurposeData = rs.data
        return rs.data
      } catch (error) {
        this.handleErrorResponse(error)
      }
    },
    async getPortBandwidth() {
      try {
        let rs = await this.$root.context.post(`/web-ecms/hatang/bangthong-cong/get-items`)
        // this.portBandwidthData = rs.data
        return rs.data
      } catch (error) {
        this.handleErrorResponse(error)
      }
    },
    async getModuleInfo() {
      try {
        let rs = await this.$root.context.post(`/web-ecms/danhmuc/thong-tin-module/danhsach`)
        // this.moduleInfoData = rs.data
        return rs.data
      } catch (error) {
        this.handleErrorResponse(error)
      }
    },
    async getTinhTrangHoatDong() {
      try {
        let rs = await this.$root.context.post(`/web-ecms/danhmuc-tinh-trang-hoat-dong/get-all-tinh-trang-hoat-dong`)
        // this.tinhTrangHoatDongData = rs.data
        return rs.data.data
      } catch (error) {
        this.handleErrorResponse(error)
      }
    },
    async getConnector() {
      const id = this.$route.params.id
      try {
        let rs = await this.$root.context.get(`/web-ecms/hatang/cong-thietbi/thiet-bi-noi-cong/${id}`)
        // this.connectorList = rs.data
        return rs.data
      } catch (error) {
        this.handleErrorResponse(error)
      }
    },
    async getHistory() {
      const id = this.$route.params.id
      try {
        let rs = await this.$root.context.get(`/web-ecms/hatang/cong-thietbi/lich-su/${id}`)
        // this.historyList = rs.data
        return rs.data
      } catch (error) {
        this.handleErrorResponse(error)
      }
    },
    async getItemById() {
      const id = this.$route.params.id
      let result = {}
      try {
        let rs = await this.$root.context.get(`/web-ecms/hatang/cong-thietbi/chi-tiet/${id}`)
        // result = rs.data[0]
        // this.setCurrentItem(result)
        return rs.data[0]
      } catch (error) {
        this.handleErrorResponse(error)
      }
    },
    async updateItem(args) {
      let result = false
      try {
        const body = {
          id: args.id,
          ten: args.portName,
          sothutu: args.no,
          ghiChu: args.note,
          hieuLuc: args.validity,
          loaiCongID: args.portType,
          mucDichSuDungCongID: args.portPurpose,
          bangThongCongID: args.portBandWidth,
          thongTinModuleID: args.moduleInfo
        }

        let rs = await this.$root.context.post(`/web-ecms/hatang/cong-thietbi/update`, body)
        return rs
      } catch (error) {
        this.handleErrorResponse(error)
      }
    },
    async handleCancelClick() {
      await this.load()
      this.enableControls(false)
    },
    async handleEditClick() {
      this.enableControls(true)
    },
    async handleSaveClick() {
      let flag = await this.$confirm('Có chắc bạn muốn lưu lại thông tin?', 'Xác nhận thực hiện', {
        confirmButtonText: 'Có',
        cancelButtonText: 'Không',
        type: 'info',
        dangerouslyUseHTMLString: true
      })
        .then((s) => true)
        .catch((e) => false)

      if (flag) {
        try {
          this.$root.showLoading(true)
          let rs = await this.updateItem(this.currentItem)
          if (rs.data) {
            this.$root.toastSuccess('Cập nhật thành công')
          } else {
            this.$root.toastError('Cập nhật thất bại')
          }
        } finally {
          this.$root.showLoading(false)
          this.enableControls(false)
          await this.load()
        }
      }
    }
  }
}
</script>
<style scoped></style>
