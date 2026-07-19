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
  { fieldName: 'mathietbi', headerText: 'Mã thiết bị', allowFiltering: true },
  { fieldName: 'ten', headerText: 'Tên thiết bị', allowFiltering: true },
  { fieldName: 'kyhieu', headerText: 'Ký hiệu TB', allowFiltering: true },
  { fieldName: 'ips', headerText: 'IP Quản trị', allowFiltering: true },
  {
    fieldName: 'loaithietbi',
    headerText: 'Loại thiết bị',
    allowFiltering: true,
    textAlign: 'Left'
  },
  {
    fieldName: 'loaikhachhang',
    headerText: 'Khách hàng',
    allowFiltering: true,
    textAlign: 'Left',
    template: getColumnKhachHangTemplate()
  },
  { fieldName: 'serial_number', headerText: 'Serial Number', allowFiltering: true },
  { fieldName: 'model', headerText: 'Model Thiết bị', allowFiltering: true },
  { fieldName: 'part_number', headerText: 'Part Number', allowFiltering: true },
  {
    fieldName: 'hieu_luc',
    headerText: 'Hiệu Lực',
    allowFiltering: true,
    textAlign: 'Left',
    template: getColumnHieuLucTemplate()
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
    field: 'mathietbi', 
    headerText: 'Mã thiết bị', 
    textAlign: 'Left', 
    width: 120 
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
    field: 'ips', 
    headerText: 'IP Quản trị', 
    textAlign: 'Left', 
    width: 150 
  },
  {
    field: 'loaithietbi',
    headerText: 'Loại thiết bị',
    textAlign: 'Left',
    width: 150
  },
  { 
    field: 'loaikhachhang', 
    headerText: 'Khách hàng', 
    textAlign: 'Left', 
    width: 100,
    template: getColumnKhachHangTemplate()
  },
  { 
    field: 'serial_number', 
    headerText: 'Serial Number', 
    textAlign: 'Left', 
    width: 150 
  },
  {
    field: 'model',
    headerText: 'Model Thiết bị',
    textAlign: 'Left',
    width: 150
  },
  {
    field: 'part_number',
    headerText: 'Part Number',
    textAlign: 'Left',
    width: 150
  },
  {
    field: 'hieu_luc',
    headerText: 'Hiệu Lực',
    textAlign: 'Center',
    template: getColumnHieuLucTemplate(),
    width: 120
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
            <b-button size="sm" variant="outline-primary" v-if="canView()" @click="onView()">
              <i class="fa fa-eye"></i>
            </b-button>
            <b-button size="sm" variant="outline-success" class="ml-1" v-if="canEdit()" @click="onEdit()">
              <i class="fa fa-edit"></i>
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
          }
        }
      }
    }
  }
}
