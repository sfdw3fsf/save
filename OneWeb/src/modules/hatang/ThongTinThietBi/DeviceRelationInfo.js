export default {
  name: 'DeviceRelationInfo',
  props: {
    connectorList: {
      type: Array,
      default: () => []
    },
    infraList: {
      type: Array,
      default: () => []
    },
    colocationList: {
      type: Array,
      default: () => []
    },
    historyList: {
      type: Array,
      default: () => []
    }
  },
  data() {
    return {
      currentTab: 1,
      connectorCols: [
        { fieldName: 'SOTHUTU', headerText: 'STT', width: 100, allowFiltering: false },
        { fieldName: 'MATHIETBI', headerText: 'Mã thiết bị', allowFiltering: true },
        { fieldName: 'TEN', headerText: 'Tên thiết bị', allowFiltering: true },
        { fieldName: 'TEN_PHANLOAI', headerText: 'Loại thiết bị', allowFiltering: true },
        { fieldName: 'LINK', headerText: 'Link', allowFiltering: true }
      ],
      infraCols: [
        { fieldName: 'SOTHUTU', headerText: 'STT', width: 100, allowFiltering: false },
        { fieldName: 'TENHATANG', headerText: 'Tên hạ tầng', allowFiltering: true },
        { fieldName: 'LOAIHATANG', headerText: 'Loại hạ tầng', allowFiltering: true },
        { fieldName: 'NGAY_TAO', headerText: 'Ngày tạo', allowFiltering: true },
        { fieldName: 'TEN_NGUOIQLY', headerText: 'Người quản lý', allowFiltering: true },
        { fieldName: 'TEN_DONVIQLY', headerText: 'Đơn vị quản lý', allowFiltering: true }
      ],
      colocationCols: [
        { fieldName: 'SOTHUTU', headerText: 'STT', width: 100, allowFiltering: false },
        { fieldName: 'MATHIETBI', headerText: 'Phiếu yêu cầu', allowFiltering: true },
        { fieldName: 'TENTHIETBI', headerText: 'Loại phiếu', allowFiltering: true },
        { fieldName: 'LOAITHIETBIMANG', headerText: 'Hạng mục triển khai', allowFiltering: true },
        { fieldName: 'LOAITHIETBIMANG', headerText: 'Loại công việc', allowFiltering: true },
        { fieldName: 'LOAITHIETBIMANG', headerText: 'Đơn vị thực hiện', allowFiltering: true },
        { fieldName: 'LOAITHIETBIMANG', headerText: 'Người thực hiện', allowFiltering: true },
        { fieldName: 'LOAITHIETBIMANG', headerText: 'Thời điểm hoàn thành', allowFiltering: true },
        { fieldName: 'LOAITHIETBIMANG', headerText: 'Trạng thái thực hiện', allowFiltering: true }
      ],
      historyCols: [
        { fieldName: 'SOTHUTU', headerText: 'STT', width: 100, allowFiltering: false },
        { fieldName: 'TACDONG', headerText: 'Tên tác động', allowFiltering: true },
        { fieldName: 'FIELD', headerText: 'Trường thông tin thay đổi', allowFiltering: true },
        { fieldName: 'OLD_VALUE', headerText: 'Giá trị cũ', allowFiltering: true },
        { fieldName: 'NEW_VALUE', headerText: 'Giá trị mới', allowFiltering: true },
        { fieldName: 'NGUOI_CN', headerText: 'Người thực hiện', allowFiltering: true },
        { fieldName: 'NGAY_CN', headerText: 'Thời điểm', allowFiltering: true }
      ]
    }
  },
  computed: {},
  methods: {
    getLoaiThietBiText(value) {
      return this.$parent.deviceTypeList.find((item) => item.ID === value)?.TEN ?? ''
    },
    getColumnLoaiThietBiTemplate() {
      return () => {
        return {
          template: {
            template: `<span>{{ getText() }}</span>`,
            methods: {
              getText() {
                return this.$parent.$parent.$parent.getLoaiThietBiText(this.data.LOAITHIETBI_ID)
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
    }
  }
}
