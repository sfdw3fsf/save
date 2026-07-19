import { DEFAULT_PORT_TYPE, PORT_TYPE } from './test.js'

export default {
  name: 'PortData',
  props: {
    deviceDetailData: {
      type: Object,
      default: () => ({})
    },
    deviceData: {
      type: Array,
      default: () => []
    },
    portType: {
      type: String,
      default: PORT_TYPE.INTERFACE.NAME
    },
    portTypeData: {
      type: Array,
      default: () => []
    },
    portListData: {
      type: Array,
      default: () => []
    },
    hasLineNumber: {
      type: Boolean,
      default: false
    }
  },
  inject: ['deletePortTypeItem', 'deleteCardItem', 'updateCardModule'],
  data() {
    return {
      deviceCols: [
        { fieldName: 'model', headerText: 'Model thiết bị', allowFiltering: false },
        {
          fieldName: 'hangsx_id',
          headerText: 'Nhà sản xuất',
          allowFiltering: true,
          template: this.getColumnNSXTemplate()
        },
        { fieldName: 'up_unit_id', headerText: 'Chiều cao', allowFiltering: true },
        { fieldName: 'unit_qty', headerText: 'Full Depth', allowFiltering: true },
        { fieldName: 'cs_dinhdanh', headerText: 'Công suất định danh', allowFiltering: true },
        {
          fieldName: 'hieu_luc',
          headerText: 'Hiệu lực',
          allowFiltering: true,
          template: this.getColumnHieuLucTemplate()
        }
        // { fieldName: 'CHI_TIET', headerText: 'Chi tiết', allowFiltering: false, template: this.getColumnTemplate(this) }
      ],
      typeInterfaceCols: [
        {
          fieldName: 'loaicong_id',
          headerText: 'Loại cổng',
          allowFiltering: true,
          template: this.getLoaiCongTemplate()
        },
        { fieldName: 'so_cong', headerText: 'Số cổng', allowFiltering: true },
        { fieldName: 'bat_dau', headerText: 'Bắt đầu từ', allowFiltering: true },
        { fieldName: 'port_fname', headerText: 'Định dạng tên', allowFiltering: true },
        {
          fieldName: 'mucdichsudung_cong_id',
          headerText: 'Mục đích sử dụng',
          allowFiltering: true,
          template: this.getMucDichTemplate()
        },
        {
          fieldName: 'bangthongcong_id',
          headerText: 'Băng thông',
          allowFiltering: true,
          template: this.getBangThongTemplate()
        },
        { fieldName: 'thongtin_module', headerText: 'Thông tin Module', allowFiltering: true },
        { fieldName: 'ketnoi', headerText: 'Kết nối', allowFiltering: true, template: this.getKetNoiTemplate() }
      ],
      interfaceListCols: [
        { fieldName: 'sothutu', headerText: 'Số thứ tự', allowFiltering: true },
        { fieldName: 'ten', headerText: 'Tên cổng', allowFiltering: true },
        {
          fieldName: 'loaicong_id',
          headerText: 'Loại cổng',
          allowFiltering: true,
          template: this.getLoaiCongTemplate()
        },
        {
          fieldName: 'mucdichsudung_cong_id',
          headerText: 'Mục đích sử dụng',
          allowFiltering: true,
          template: this.getMucDichTemplate()
        },
        {
          fieldName: 'bangthongcong_id',
          headerText: 'Băng thông',
          allowFiltering: true,
          template: this.getBangThongTemplate()
        },
        { fieldName: 'thongtin_module', headerText: 'Thông tin Module', allowFiltering: true },
        { fieldName: 'ketnoi', headerText: 'Kết nối', allowFiltering: true, template: this.getKetNoiTemplate() },
        { fieldName: 'trangthai_sd_text', headerText: 'Trạng thái sử dụng', allowFiltering: true },
        { fieldName: 'NULL', headerText: 'Chi tiết', allowFiltering: false, template: this.getColumnTemplate(this) }
      ],
      typeCols: [
        {
          fieldName: 'loaicong_id',
          headerText: 'Loại cổng',
          allowFiltering: true,
          template: this.getLoaiCongTemplate()
        },
        { fieldName: 'so_cong', headerText: 'Số cổng', allowFiltering: true },
        { fieldName: 'bat_dau', headerText: 'Bắt đầu từ', allowFiltering: true },
        { fieldName: 'port_fname', headerText: 'Định dạng tên', allowFiltering: true }
      ],
      listCols: [
        { fieldName: 'sothutu', headerText: 'Số thứ tự', allowFiltering: true },
        { fieldName: 'ten', headerText: 'Tên cổng', allowFiltering: true },
        {
          fieldName: 'loaicong_id',
          headerText: 'Loại cổng',
          allowFiltering: true,
          template: this.getLoaiCongTemplate()
        },
        { fieldName: 'trangthai_sd', headerText: 'Trạng thái sử dụng', allowFiltering: true },
        { fieldName: 'NULL', headerText: 'Chi tiết', allowFiltering: true, template: this.getColumnTemplate(this) }
      ]
    }
  },
  computed: {
    portListCols() {
      return this.portType === PORT_TYPE.INTERFACE.NAME ? this.interfaceListCols : this.listCols
    },
    portTypeCols() {
      return this.portType === PORT_TYPE.INTERFACE.NAME ? this.typeInterfaceCols : this.typeCols
    },
    deviceDataLabel() {
      return this.portType === PORT_TYPE.POWER.NAME ? 'Module' : 'Card'
    },
    createPortRef() {
      return this.$parent.$refs.createPortType
    },
    deviceCharacteristicsRef() {
      return this.$parent.$parent.$refs.DeviceCharacteristics
    },
    manufacturerList() {
      return this.deviceCharacteristicsRef.manufacturerList
    }
  },
  watch: {},
  async created() {},
  methods: {
    getHieuLucText(value) {
      return value == 1 ? 'Hiệu lực' : 'Không hiệu lực'
    },
    getNSXText(value) {
      return this.manufacturerList.find((item) => item.ID == value)?.TEN ?? ''
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
    getColumnNSXTemplate() {
      return () => {
        return {
          template: {
            template: `<span>{{ getText() }}</span>`,
            methods: {
              getText() {
                return this.$parent.$parent.$parent.getNSXText(this.data.hangsx_id)
              }
            }
          }
        }
      }
    },
    getColumnTemplate(parent) {
      return () => {
        return {
          template: {
            template: `
              <div style="display: flex; justify-content: center; align-items: center;">
                <button class="btn btn-second btn-detail" style="width: 50%;" @click="parent.handleDetailClick(data)">Chi tiết</button>
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
    handleDetailClick(data) {
      this.$router.push(`/ha-tang/chi-tiet-cong-thiet-bi-thiet-bi/${data.id}/${data.thietbi_id}`)
    },
    onCreatePortType() {
      this.createPortRef.dialogOpen()
    },
    grid_onCommandClicked(name, dataItem) {
      switch (name) {
        case 'SUA':
          this.handleUpdatePortType(dataItem)
          break
        case 'XOA':
          this.handleDeletePortType(dataItem)
      }
    },
    cardGridCommandClicked(name, dataItem) {
      switch (name) {
        case 'SUA':
          this.updateCardModule(dataItem)
          break
        case 'XOA':
          this.deleteCardItem(dataItem)
      }
    },
    handleDeletePortType(item) {
      // if (this.deletePortTypeInModal) {
      //   this.deletePortTypeInModal(this.portType, item)
      // } else if (this.deletePortTypeItem) {
      this.deletePortTypeItem(this.portType, item)
      // }
      // this.deletePortTypeInModal(this.portType, item)
    },
    handleUpdatePortType(item) {
      this.createPortRef.setPortTypeData(item)
      this.createPortRef.dialogOpen()
    },
    getLoaiCongText(value) {
      return this.createPortRef.loaiCongSelect.find((item) => item.ID == value)?.TEN ?? ''
    },
    getMucDichText(value) {
      return this.createPortRef.mucDichSelect.find((item) => item.ID == value)?.TEN ?? ''
    },
    getBangThongText(value) {
      return this.createPortRef.bangThongSelect.find((item) => item.ID == value)?.TEN ?? ''
    },
    getKetNoiText(value) {
      if (value != null) {
        return value == 1 ? 'Uplink' : 'Downlink'
      }
      return ''
    },
    getLoaiCongTemplate() {
      return () => {
        return {
          template: {
            template: `<span>{{ getText() }}</span>`,
            methods: {
              getText() {
                return this.$parent.$parent.$parent.getLoaiCongText(this.data.loaicong_id)
              }
            }
          }
        }
      }
    },
    getMucDichTemplate() {
      return () => {
        return {
          template: {
            template: `<span>{{ getText() }}</span>`,
            methods: {
              getText() {
                return this.$parent.$parent.$parent.getMucDichText(this.data.mucdichsudung_cong_id)
              }
            }
          }
        }
      }
    },
    getBangThongTemplate() {
      return () => {
        return {
          template: {
            template: `<span>{{ getText() }}</span>`,
            methods: {
              getText() {
                return this.$parent.$parent.$parent.getBangThongText(this.data.bangthongcong_id)
              }
            }
          }
        }
      }
    },
    getKetNoiTemplate() {
      return () => {
        return {
          template: {
            template: `<span>{{ getText() }}</span>`,
            methods: {
              getText() {
                return this.$parent.$parent.$parent.getKetNoiText(this.data.ketnoi)
              }
            }
          }
        }
      }
    }
  }
}
