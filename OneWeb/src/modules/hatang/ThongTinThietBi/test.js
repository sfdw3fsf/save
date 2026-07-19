import moment from 'moment'

export const LOAI_THIET_BI = {
  MAY_CHU: 'MAYCHU',
  MANG: 'MANG',
  IDC: 'IDC',
  TU_DIA: 'TUDIA',
  KHAC: 'KHAC'
}

export const PORT_TYPE = {
  INTERFACE: {
    ID: 1,
    NAME: 'Interface'
  },
  POWER: {
    ID: 2,
    NAME: 'Power'
  },
  CONSOLE: {
    ID: 3,
    NAME: 'Console'
  }
}

export const CUSTOMER_TYPES = [
  {
    id: 0,
    name: 'Ngoài'
  },
  {
    id: 1,
    name: 'Nội bộ'
  }
]

export const DEFAULT_PORT_TYPE = {
  thietbi_id: 0,
  phanloai: 0,
  loaicong_id: 0,
  so_cong: 0,
  bat_dau: 0,
  port_fname: '',
  mucdichsudung_cong_id: 0,
  bangthongcong_id: 0,
  thongtin_module: '',
  ketnoi: 0
}

export const ROOT_API = '/web-ecms'
// export const ROOT_API = 'http://localhost:10990'

export const DEFAULT_OBJ = {
  id: 0,
  // Thông tin chung
  mathietbi: '',
  ten: '',
  kyhieu: '',
  mathietbi_infra: '',
  thietbicha_id: 0,
  loaithietbi_id: 0,
  loaikhachhang: 0,
  serial_number: '',
  model: '',
  part_number: '',
  hsm: 0,
  hieu_luc: 1,
  ghi_chu: '',
  loaitudia_id: 0,
  chungloaithietbi_id: 0,
  ip_ids: [],
  // Đặc tính thiết bị
  vaitrothietbi_ids: [],
  psu_qty_in: 0,
  psu_qty_out: 0,
  cauhinh: '',
  hangsx_id: 0,
  namsx: 0,
  nhacungcap_id: 0,
  cs_dinhdanh: 0,
  cs_thucte: 0,
  hedieuhanh_id: 0,
  trangthai_sd: 1,
  ngay_lapdat: moment().format('YYYY-MM-DD'),
  ngay_baohanh: moment().format('YYYY-MM-DD'),
  ngay_sudung: moment().format('YYYY-MM-DD'),
  cpu_sokhe: 0,
  cpu_dacam: 0,
  cpu_total: 0,
  hdd_sokhe: 0,
  hdd_dacam: 0,
  hdd_total: 0,
  ram_sokhe: 0,
  ram_dacam: 0,
  ram_total: 0,
  // Vị trí lắp đặt TB
  idc_id: 0,
  building_id: 0,
  floor_id: 0,
  zone_id: 0,
  rack_id: 0,
  unit_qty: 0,
  up_unit_id: 0,
  down_unit_id: 0,
  // Thong tin kiem ke tai san
  project_id: 0,
  project_code: '',
  project_name: '',
  so_the_ts: '',
  donviqly_id: 0,
  phongbanqly_id: 0,
  nguoiqly_id: 0,
  // Thong tin cong thiet bi
  // interface
  so_linecard: 0,
  so_port_interface: 0,
  so_interface: 0,
  interface_start: 0,
  interface_fname: '',
  // power
  so_power: 0,
  so_port_power: 0,
  so_powerslot: 0,
  power_start: 0,
  power_fname: '',
  // console
  so_controlcard: 0,
  so_port_console: 0,
  so_console: 0,
  console_start: 0,
  console_fname: '',
  // Nang luc luu tru
  bodieukhien_qty: 0,
  bodieukhien_dau: 0,
  bodieukhien_fname: '',
  khaydia_qty: 0,
  khaydia_dau: 0,
  khaydia_fname: '',
  hdd_tong: 0,
  hdd_nangluc: 0,
  hdd_capphat: 0,
  ssd_tong: 0,
  ssd_nangluc: 0,
  ssd_capphat: 0,
  iops_tong: 0,
  iops_nangluc: 0,
  iops_capphat: 0,
  mem_cache: 0,
  flash_cache: 0
}

export const NEW_DEFAULT_OBJ = {
  id: null,
  // Thông tin chung
  mathietbi: '',
  ten: '',
  kyhieu: '',
  mathietbi_infra: '',
  thietbicha_id: null,
  loaithietbi_id: null,
  loaikhachhang: 0,
  serial_number: '',
  model: '',
  part_number: '',
  hsm: 0,
  hieu_luc: 1,
  ghi_chu: '',
  loaitudia_id: null,
  chungloaithietbi_id: null,
  ip_ids: [],
  // Đặc tính thiết bị
  vaitrothietbi_ids: [],
  psu_qty_in: 0,
  psu_qty_out: 0,
  cauhinh: '',
  hangsx_id: null,
  namsx: null,
  nhacungcap_id: null,
  cs_dinhdanh: 0,
  cs_thucte: 0,
  hedieuhanh_id: null,
  trangthai_sd: 1,
  ngay_lapdat: moment().format('yyyy-MM-DD'),
  ngay_baohanh: moment().format('yyyy-MM-DD'),
  ngay_sudung: moment().format('yyyy-MM-DD'),
  cpu_sokhe: 0,
  cpu_dacam: 0,
  cpu_total: 0,
  hdd_sokhe: 0,
  hdd_dacam: 0,
  hdd_total: 0,
  ram_sokhe: 0,
  ram_dacam: 0,
  ram_total: 0,
  bodieukhien_qty: 0,
  bodieukhien_dau: 0,
  bodieukhien_fname: '',
  khaydia_qty: 0,
  khaydia_dau: 0,
  khaydia_fname: '',
  // Vị trí lắp đặt TB
  idc_id: null,
  building_id: null,
  floor_id: null,
  zone_id: null,
  rack_id: null,
  unit_qty: 0,
  up_unit_id: null,
  down_unit_id: null,
  // Thong tin kiem ke tai san
  project_id: null,
  project_code: '',
  project_name: '',
  so_the_ts: '',
  donviqly_id: null,
  phongbanqly_id: null,
  nguoiqly_id: null,
  // Thong tin cong thiet bi
  // interface
  so_linecard: 0,
  so_port_interface: 0,
  so_interface: 0,
  interface_start: 0,
  interface_fname: '',
  // power
  so_power: 0,
  so_port_power: 0,
  so_powerslot: 0,
  power_start: 0,
  power_fname: '',
  // console
  so_controlcard: 0,
  so_port_console: 0,
  so_console: 0,
  console_start: 0,
  console_fname: '',
  // Nang luc luu tru
  hdd_tong: 0,
  hdd_nangluc: 0,
  hdd_capphat: 0,
  ssd_tong: 0,
  ssd_nangluc: 0,
  ssd_capphat: 0,
  iops_tong: 0,
  iops_nangluc: 0,
  iops_capphat: 0,
  mem_cache: 0,
  flash_cache: 0,
  // Loai thiet bi
  is_interface: null,
  is_power: null,
  is_console: null,
  is_controller: null,
  is_khaydia: null
}
