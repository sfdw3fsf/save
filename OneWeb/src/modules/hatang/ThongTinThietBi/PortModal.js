import { PORT_TYPE } from './test.js'

export default {
  props: {
    portType: {
      type: Number,
      default: 1
    },
    deviceList: {
      type: Array,
      default: () => []
    }
  },
  data() {
    return {
      target: '.main-wrapper',
      animationSettings: { effect: 'Zoom' },
      formTitle: 'DANH SÁCH CỔNG',
      dataSource: [],
      dataCols: [
        { fieldName: 'parent_name', headerText: 'LineCard', allowFiltering: true },
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
          headerText: 'Mục đích',
          allowFiltering: true,
          template: this.getMucDichTemplate()
        },
        {
          fieldName: 'bangthongcong_id',
          headerText: 'Băng thông',
          allowFiltering: true,
          template: this.getBangThongTemplate()
        },
        {
          fieldName: 'hieu_luc',
          headerText: 'Hiệu lực',
          allowFiltering: true,
          template: this.getHieuLucTemplate()
        },
        {
          fieldName: 'LOAITHIETBIMANG',
          headerText: 'Chi tiết',
          allowFiltering: true,
          template: this.getColumnTemplate(this)
        }
      ]
    }
  },
  watch: {
    portType(val) {
      switch (this.portType) {
        case PORT_TYPE.INTERFACE.ID:
          this.dataCols[0].headerText = 'Line Card'
          break
        case PORT_TYPE.POWER.ID:
          this.dataCols[0].headerText = 'Module'
          break
        case PORT_TYPE.CONSOLE.ID:
          this.dataCols[0].headerText = 'Console Card'
          break
      }
    }
  },
  computed: {
    tableName() {
      switch (this.portType) {
        case PORT_TYPE.INTERFACE.ID:
          return 'Line Card'
        case PORT_TYPE.POWER.ID:
          return 'Module'
        case PORT_TYPE.CONSOLE.ID:
          return 'Console Card'
        default:
          break
      }
    },
    createPortRef() {
      return this.$parent.$refs.createPortType
    }
  },
  methods: {
    handleErrorResponse(error) {
      if (!(error.response == null || error.response == undefined)) {
        if (!(error.response.data == null || error.response.data == undefined)) {
          this.$root.toastError(error.response.data.message_detail)
        }
      }
    },
    getHieuLucText(value) {
      return value == 1 ? 'Hiệu lực' : 'Không hiệu lực'
    },
    getHieuLucTemplate() {
      return () => {
        return {
          template: {
            template: `<span>{{ getText() }}</span>`,
            methods: {
              getText() {
                return this.$parent.$parent.$parent.$parent.getHieuLucText(this.data.hieu_luc)
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
    getLoaiCongText(value) {
      return this.createPortRef.loaiCongSelect.find((item) => item.ID == value)?.TEN ?? ''
    },
    getMucDichText(value) {
      return this.createPortRef.mucDichSelect.find((item) => item.ID == value)?.TEN ?? ''
    },
    getBangThongText(value) {
      return this.createPortRef.bangThongSelect.find((item) => item.ID == value)?.TEN ?? ''
    },
    getLoaiCongTemplate() {
      return () => {
        return {
          template: {
            template: `<span>{{ getText() }}</span>`,
            methods: {
              getText() {
                return this.$parent.$parent.$parent.$parent.getLoaiCongText(this.data.loaicong_id)
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
                return this.$parent.$parent.$parent.$parent.getMucDichText(this.data.mucdichsudung_cong_id)
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
                return this.$parent.$parent.$parent.$parent.getBangThongText(this.data.bangthongcong_id)
              }
            }
          }
        }
      }
    },
    async dialogOpen() {
      this.$refs.dialogPortList.show()
      await this.getDanhSachPort()
    },
    closeDialog() {
      this.$refs.dialogPortList.hide()
    },
    async getDanhSachPort() {
      try {
        const requests = this.deviceList.map((item) =>
          this.$root.context.get(`/web-ecms/thong-tin-thiet-bi/${item.id}/thiet-bi-port`)
        )
        const responses = await Promise.all(requests)
        // Gộp tất cả dữ liệu vào this.dataSource
        this.dataSource = responses.flatMap((rs) => rs.data)
      } catch (error) {
        this.handleErrorResponse(error)
      }
    }
  }
}
