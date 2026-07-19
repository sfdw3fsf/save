import { PORT_TYPE, TRANG_THAI_SD } from './constant'
import ModalDauNoiThietBi from './ModalDauNoiThietBi/index.vue'
import ComponentThongTinThietBi from '../ThongTinThietBi/index.vue';
export default {
  props: {
    modalItemID: 0,
    fromDiagram: false
  },
  components: {
    ModalDauNoiThietBi,
    ComponentThongTinThietBi
  },
  data() {
    return {
      showGridDauNoi: true,
      showThongTinThietBi: false,
      enableBtnDauNoi: true,
      showDauNoiThietBi: false,
      id: this.$route.params.id ?? this.modalItemID,
      deviceId: this.$route.params.deviceid ?? 0,
      deviceDetail: {},
      idcDetail: {},
      connectorList: [],
      connectorCols: [
        { fieldName: 'THIETBI_MA', headerText: 'Mã thiết bị', allowFiltering: true },
        { fieldName: 'THIETBI_TEN', headerText: 'Tên thiết bị', allowFiltering: true },
        { fieldName: 'LOAITHIETBI_TEN', headerText: 'Loại thiết bị', allowFiltering: true },
        { fieldName: 'THIETBI_PORT_TEN', headerText: 'Port đích', allowFiltering: true },
        { fieldName: 'THIETBI_PORT_PHANLOAI', headerText: 'Loại port đích', allowFiltering: true },
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
        usingState: 0
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
  updated() { },
  computed: {
    isInterface() {
      return this.currentItem.portClassification == PORT_TYPE.INTERFACE.ID ?? false
      // return true
    },
    portType() {
      const portMap = {
        [PORT_TYPE.INTERFACE.ID]: PORT_TYPE.INTERFACE.NAME,
        [PORT_TYPE.POWER.ID]: PORT_TYPE.POWER.NAME,
        [PORT_TYPE.CONSOLE.ID]: PORT_TYPE.CONSOLE.NAME
      }

      return portMap[this.currentItem.portClassification] || 'Unknown'
    },
    thongTinModuleList() {
      const bangThongCong = this.portBandwidthData.find((el) => el.ID == this.currentItem.portBandWidth)
      if (bangThongCong) {
        const bangThong = bangThongCong.BANGTHONG
        const moduleFilter = this.moduleInfoData.filter((el) => el.BANGTHONG <= bangThong)
        return moduleFilter
      }

      return this.moduleInfoData
    },
    loaiCongList() {
      return this.portTypeData.filter((el) => el.PHANLOAI == this.currentItem.portClassification)
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
    this.tinhTrangHoatDongData = TRANG_THAI_SD
    this.getDeviceDetail()
  },
  methods: {
    async successDauNoi() {
      try {
        this.$root.toastSuccess('Đấu nối thiết bị thành công!')
        this.enableBtnDauNoi = true
        this.connectorList = []
        this.$refs.grdConnector.refresh()
        this.connectorList = await this.getConnector()
        this.closeModalDauNoiThietBi()
      } catch (error) {
        console.log('Error in successDauNoi:', error)
      }
    },
    getDeviceDetail() {
      this.$root.context.get('/web-ecms/thong-tin-thiet-bi' + `/${this.deviceId}`)
        .then((response) => {
          this.deviceDetail = response.data
          if (this.deviceDetail && this.deviceDetail.idc_id) {
            this.$root.context.get('/web-ecms/danhmuc/ha-tang-idc')
              .then((response) => {
                this.idcDetail = response.data.find((item) => item.ID === this.deviceDetail.idc_id)
                console.log('IDC Detail:', this.idcDetail)
              })
              .catch((error) => {
                this.handleErrorResponse(error)
              })
          }
        })
        .catch((error) => {
          this.handleErrorResponse(error)
        })
    },
    showModalDauNoiThietBi() {
      this.showDauNoiThietBi = true
      this.$refs.modalDauNoiThietBi.show()
    },
    closeModalDauNoiThietBi() {
      this.showDauNoiThietBi = false
      this.$refs.modalDauNoiThietBi.hide()
    },
    async goDauNoiThietBi() {
      try {
        await this.$confirm(`Bạn có chắc muốn gỡ đầu nối từ port hiện tại đến port "${this.connectorList[0].THIETBI_PORT_TEN}" thuộc thiết bị "${this.connectorList[0].THIETBI_TEN}"?`, 'Xác nhận thực hiện', {
          confirmButtonText: 'Có',
          cancelButtonText: 'Không',
          type: 'warning',
          dangerouslyUseHTMLString: true
        });

        this.$root.showLoading(true);
        const data = {
          id: this.connectorList[0].ID,
        };

        const response = await this.$root.context.post(`/web-ecms/hatang/daunoi-thietbi/delete-link`, data);
        
        if (response.error_code === 'BSS-00000000') {
          this.$root.toastSuccess('Gỡ đầu nối thành công');
          this.enableBtnDauNoi = true;
          this.connectorList = await this.getConnector();
          this.$refs.grdConnector.refresh();
        } else {
          this.$root.toastError('Gỡ đầu nối thất bại');
        }
      } catch (error) {
        console.log('Operation cancelled or error occurred:', error);
        if (error.response) {
          this.handleErrorResponse(error);
        }
      } finally {
        this.$root.showLoading(false);
      }
    },
    getColumnTemplate(parent) {
      return () => {
        return {
          template: {
            template: `
              <div style="display: flex; justify-content: center; align-items: center;">
                <button class="btn btn-second btn-detail" style="width: 50%;" @click="parent.handleDetailCLick(data)">Chi tiết</button>
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
      this.showThongTinThietBi = true;
      this.$refs.modalChiTietThietBi.show();

      this.$nextTick(() => {
        if (this.$refs.thongTinThietBi) {
          this.$refs.thongTinThietBi.loadThongTinThietBi(data.THIETBI_ID);
        } else {
          console.error('Không tìm thấy component thông tin thiết bị');
        }
      });
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
    setCurrentItem(item) {
      if (!item) {
        return;
      }
      this.currentItem.id = item.ID
      this.currentItem.parentDevice = item.MA_TB_CHA
      this.currentItem.no = item.SOTHUTU
      this.currentItem.portName = item.TEN
      this.currentItem.portClassification = item.PHANLOAI
      this.currentItem.portPurpose = item.MUCDICHSUDUNG_CONG_ID
      this.currentItem.validity = item.HIEU_LUC
      this.currentItem.portType = item.LOAICONG_ID
      this.currentItem.portBandWidth = item.BANGTHONGCONG_ID
      this.currentItem.moduleInfo = item.THONGTINMODULE_ID
      this.currentItem.connection = item.KETNOI
      this.currentItem.usingState = item.TRANGTHAI_SD
    },
    validateControls() { },
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
          this.getModuleInfo()
          // this.getTinhTrangHoatDong()
        ]
        let portInfo = []
        if (!this.fromDiagram) {
          portInfo = [this.getItemById(), this.getHistory(), this.getConnector()]
        } else {
          if (this.modalItemID && this.modalItemID > 0) {
            portInfo = [this.getItemById(), this.getHistory(), this.getConnector()]
          }
        }
        const [
          loaiCong,
          mucDichSuDungCong,
          bangThongCong,
          thongTinModule,
          // tinhTrangHoatDong,
          congThietBi,
          lichSu,
          dauNoi
        ] = await Promise.all([...generalInfo, ...portInfo])
        console.log(congThietBi, 'congThietBi')
        this.portTypeData = loaiCong
        this.portPurposeData = mucDichSuDungCong
        this.portBandwidthData = bangThongCong
        this.moduleInfoData = thongTinModule
        // this.tinhTrangHoatDongData = tinhTrangHoatDong
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
      const id = this.$route.params.id ?? this.modalItemID
      try {
        const data = {
          id: this.deviceId
        }
        let rs = await this.$root.context.post(`/web-ecms/hatang/daunoi-thietbi/get-link-info`, data)
        let dataReturn = rs.data.result.filter((item) => item.THIETBI_PORT_ID_NGUON == id || item.THIETBI_PORT_ID_DICH == id)
        if (dataReturn.length > 0) {
          this.enableBtnDauNoi = false
        } else {
          this.enableBtnDauNoi = true
        }
        return dataReturn
      } catch (error) {
        this.handleErrorResponse(error)
      }
    },
    async getHistory() {
      const id = this.$route.params.id ?? this.modalItemID
      try {
        let rs = await this.$root.context.get(`/web-ecms/hatang/cong-thietbi/lich-su/${id}`)
        // this.historyList = rs.data
        return rs.data
      } catch (error) {
        this.handleErrorResponse(error)
      }
    },
    async getItemById() {
      const id = this.$route.params.id ?? this.modalItemID
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
          sothutu: args.no,
          ten: args.portName,
          loaiCongID: args.portType,
          mucDichSuDungCongID: args.portPurpose,
          bangThongCongID: args.portBandWidth,
          thongTinModuleID: args.moduleInfo,
          ketnoi: args.connection,
          trangthai_sd: args.usingState,
          hieuLuc: args.validity
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
