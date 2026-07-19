<template src="./index.template.html"></template>
<style src="./styles.scss" scoped></style>
<script>
import { PORT_TYPE, USAGE_STATUS_LIST } from '../../../../const.js'
import { createLookupTemplate } from '../../../../template/lookupTemplate.js'

export default {
  name: 'ModalListPort',
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
      formTitle: 'Danh sách cổng',
      dataSource: [],
      loaiCongSelect: [],
      mucDichSelect: [],
      bangThongSelect: []
    }
  },
  async created() {
    await this.getLoaiCong()
    await this.getMucDichSuDung()
    await this.getBangThong()
  },
  watch: {
    portType(val) {
      switch (this.portType) {
        case PORT_TYPE.INTERFACE.ID:
          this.formTitle = 'DANH SÁCH CỔNG INTERFACE'
          break
        case PORT_TYPE.POWER.ID:
          this.formTitle = 'DANH SÁCH CỔNG POWER'
          break
        case PORT_TYPE.CONSOLE.ID:
          this.formTitle = 'DANH SÁCH CỔNG CONSOLE'
          break
      }
    }
  },
  computed: {
    tableName() {
      const nameMapping = {
        [PORT_TYPE.INTERFACE.ID]: 'Interface',
        [PORT_TYPE.POWER.ID]: 'Power',
        [PORT_TYPE.CONSOLE.ID]: 'Console'
      }
      return nameMapping[this.portType] || 'Port'
    },
    dataCols() {
      return [
        { fieldName: 'parent_name', headerText: 'LineCard', allowFiltering: true, width: '150px' },
        { fieldName: 'sothutu', headerText: 'Số thứ tự', allowFiltering: true, width: '100px' },
        { fieldName: 'ten', headerText: 'Tên cổng', allowFiltering: true, width: '150px' },
        {
          fieldName: 'loaicong_id',
          headerText: 'Loại cổng',
          allowFiltering: true,
          width: '150px',
          template: createLookupTemplate({
            field: 'loaicong_id',
            list: this.loaiCongSelect,
            valueKey: 'ID',
            labelKey: 'TEN'
          })
        },
        {
          fieldName: 'mucdichsudung_cong_id',
          headerText: 'Mục đích',
          allowFiltering: true,
          width: '150px',
          template: createLookupTemplate({
            field: 'mucdichsudung_cong_id',
            list: this.mucDichSelect,
            valueKey: 'ID',
            labelKey: 'TEN'
          })
        },
        {
          fieldName: 'bangthongcong_id',
          headerText: 'Băng thông',
          allowFiltering: true,
          width: '150px',
          template: createLookupTemplate({
            field: 'bangthongcong_id',
            list: this.bangThongSelect,
            valueKey: 'ID',
            labelKey: 'TEN'
          })
        },
        {
          fieldName: 'hieu_luc',
          headerText: 'Hiệu lực',
          allowFiltering: true,
          width: '150px',
          template: createLookupTemplate({
            field: 'hieu_luc',
            list: USAGE_STATUS_LIST,
            valueKey: 'ID',
            labelKey: 'TEN'
          })
        },
        {
          fieldName: 'LOAITHIETBIMANG',
          headerText: 'Đấu nối',
          allowFiltering: true,
          width: '130px',
          template: this.getColumnTemplate(this)
        }
      ]
    }
  },
  methods: {
    async dialogOpen() {
      await this.getDanhSachPort()
      this.$refs.dialogPortList.show()
    },
    closeDialog() {
      this.$refs.dialogPortList.hide()
    },
    getColumnTemplate(parent) {
      return () => {
        return {
          template: {
            template: `
              <div style="display: flex; justify-content: center; align-items: center;">
                <button class="btn btn-second btn-detail" @click="parent.handleDetailClick(data)">Chi tiết</button>
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
    handleDetailClick(data) {
      this.$router.push(`/ha-tang/chi-tiet-cong-thiet-bi-thiet-bi/${data.id}/${data.thietbi_id}`)
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
</script>
