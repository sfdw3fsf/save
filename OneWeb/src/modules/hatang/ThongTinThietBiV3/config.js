import { CUSTOMER_TYPES, USAGE_STATUS_LIST } from './const'
import { createLookupTemplate } from './template/lookupTemplate.js'

export const getColumnKhachHangTemplate = () => {
  return createLookupTemplate({
    field: 'loaikhachhang',
    list: CUSTOMER_TYPES,
    valueKey: 'id',
    labelKey: 'name'
  })
}

export const getColumnHieuLucTemplate = () => {
  return createLookupTemplate({
    field: 'hieu_luc',
    list: USAGE_STATUS_LIST,
    valueKey: 'ID',
    labelKey: 'TEN'
  })
}

// Centralized column factory for the main DataGrid
export const getDataCols = (parent) => [
  { fieldName: 'id', headerText: 'ID', allowFiltering: true, visible: false },
  { fieldName: 'ma_tbi', headerText: 'Mã TB', allowFiltering: true },
  { fieldName: 'ten', headerText: 'Tên Thiết bị', allowFiltering: true },
  { fieldName: 'kyhieu', headerText: 'Ký hiệu', allowFiltering: true },
  { fieldName: 'serial_number', headerText: 'Serial', allowFiltering: true },
  {
    fieldName: 'so_huu',
    headerText: 'Sỡ hữu',
    allowFiltering: true,
    textAlign: 'Left'
  },
  {
    fieldName: 'idc_ten',
    headerText: 'IDC',
    allowFiltering: true,
    textAlign: 'Left'
  },
  {
    fieldName: 'building_ten',
    headerText: 'Tòa nhà',
    allowFiltering: true,
    textAlign: 'Left'
  },
  {
    fieldName: 'rack_ten',
    headerText: 'Rack',
    allowFiltering: true,
    textAlign: 'Left'
  },
  {
    fieldName: 'trangthai_vh',
    headerText: 'Trạng thái vận hành',
    allowFiltering: true,
    textAlign: 'Left'
  },
  {
    fieldName: '__actions',
    headerText: 'Thao tác',
    allowFiltering: false,
    textAlign: 'Center',
    width: 140,
    template: getColumnActionsTemplate(parent)
  }
]

// Columns definition for child DataGrid (detail grid)
export const getChildGridColumns = (parent) => [
  { 
    field: 'id', 
    headerText: 'ID', 
    textAlign: 'Left', 
    width: 80 
  },
  { 
    field: 'ten', 
    headerText: 'Tên thiết bị', 
    textAlign: 'Left' 
  },
  { 
    field: 'kyhieu', 
    headerText: 'Ký hiệu TB', 
    textAlign: 'Left', 
    width: 120 
  },
  { 
    field: 'serial_number', 
    headerText: 'Serial Number', 
    textAlign: 'Left', 
    width: 150 
  },
  {
    field: 'so_huu',
    headerText: 'Sở hữu',
    textAlign: 'Left',
    width: 100
  },
  {
    field: 'loai_tbi',
    headerText: 'Loại thiết bị',
    textAlign: 'Left',
    width: 150
  },
  {
    field: 'rack_ten',
    headerText: 'Rack',
    textAlign: 'Left',
    width: 150
  },
  {
    field: '__actions',
    headerText: 'Thao tác',
    textAlign: 'Center',
    width: 140,
    template: getColumnActionsTemplate(parent)
  }
]

// ========= Actions config (view/edit) theo loại thiết bị =========
// Khai báo các quyền action cho từng loại thiết bị (ID loại thiết bị)
export const DEVICE_ACTION_CONFIG = {
  1: { view: true, edit: true },
  2: { view: true, edit: false }
}

export const getActionsForDeviceType = (deviceId) => {
  const cfg = DEVICE_ACTION_CONFIG[deviceId]
  if (cfg) return cfg
  return { view: true, edit: true }
}

export const getColumnActionsTemplate = (parent) => {
  return () => {
    return {
      template: {
        template: `
          <div class="d-flex align-items-center justify-content-center">
            <b-button size="sm" variant="outline-primary" v-if="canView()" @click="onView()" title="Xem">
              <i class="fa fa-eye"></i>
            </b-button>
            <b-button size="sm" variant="outline-success" class="ml-1" v-if="canEdit()" @click="onEdit()" title="Sửa">
              <i class="fa fa-edit"></i>
            </b-button>
            <b-button size="sm" variant="outline-danger" class="ml-1" @click="onDelete()" title="Xóa">
              <i class="fa fa-trash"></i>
            </b-button>
          </div>
        `,
        methods: {
          canView() {
            const cfg = getActionsForDeviceType(this.data.id)
            return !!cfg.view
          },
          canEdit() {
            const cfg = getActionsForDeviceType(this.data.id)
            return !!cfg.edit
          },
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
