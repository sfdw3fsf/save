// Column configuration for DataGrid

export const getDataCols = (parent) => [
  { fieldName: 'id', headerText: 'ID', allowFiltering: true, visible: false },
  { fieldName: 'maChungLoaiTB', headerText: 'Mã chủng loại TB', allowFiltering: true, textAlign: 'Left' },
  { fieldName: 'tenChungLoaiTB', headerText: 'Tên chủng loại thiết bị', allowFiltering: true, textAlign: 'Left' },
  { fieldName: 'congSuatDinhDanh', headerText: 'Công suất định danh', allowFiltering: true, textAlign: 'Left' },
  { fieldName: 'hangSanXuat', headerText: 'Hãng sản xuất', allowFiltering: true, textAlign: 'Left' },
  { fieldName: 'mangThietBi', headerText: 'Mảng thiết bị', allowFiltering: true, textAlign: 'Left' },
  { fieldName: 'loaiThietBi', headerText: 'Loại thiết bị', allowFiltering: true, textAlign: 'Left' },
  { fieldName: 'phanLoaiThietBi', headerText: 'Phân loại thiết bị', allowFiltering: true, textAlign: 'Left' },
  { fieldName: 'hieuLuc', headerText: 'Hiệu lực', allowFiltering: true, textAlign: 'Left', width: 120 },
  {
    fieldName: '__actions',
    headerText: 'Thao tác',
    allowFiltering: false,
    textAlign: 'Center',
    width: 140,
    template: getColumnActionsTemplate(parent)
  }
]

export const getColumnActionsTemplate = (parent) => {
  return () => {
    return {
      template: {
        template: `
          <div class="d-flex align-items-center justify-content-center">
            <b-button size="sm" variant="outline-primary" @click="onView()" title="Xem">
              <i class="fa fa-eye"></i>
            </b-button>
            <b-button size="sm" variant="outline-success" class="ml-1" @click="onEdit()" title="Sửa">
              <i class="fa fa-edit"></i>
            </b-button>
            <b-button size="sm" variant="outline-danger" class="ml-1" @click="onDelete()" title="Xóa">
              <i class="fa fa-trash"></i>
            </b-button>
          </div>
        `,
        methods: {
          onView() {
            parent.onView(this.data)
          },
          onEdit() {
            parent.onEdit(this.data)
          },
          onDelete() {
            parent.onDelete(this.data)
          }
        }
      }
    }
  }
}
