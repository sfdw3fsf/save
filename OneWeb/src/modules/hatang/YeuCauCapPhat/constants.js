import moment from 'moment'

export const YEU_CAU_CP_TABLES = {
  AO_HOA_CP_COLS: [
    { fieldName: 'sothutu', headerText: 'STT', allowFiltering: true },
    { fieldName: 'ten_thanhphan', headerText: 'Kiến trúc thành phần', allowFiltering: true },
    { fieldName: 'ten_hatangaohoa', headerText: 'Hạ tầng ảo hóa/Cloud', allowFiltering: true },
    { fieldName: 'ten_cumdichvu', headerText: 'Cụm dịch vụ', allowFiltering: true },
    { fieldName: 'ten_mayao', headerText: 'Tên VM', allowFiltering: true },
    { fieldName: 'donvi_vh', headerText: 'Đơn vị vận hành', allowFiltering: true },
    { fieldName: 'nguoi_qtr', headerText: 'Người quản trị', allowFiltering: true },
    { fieldName: 'ip', headerText: 'IP Address', allowFiltering: true },
    { fieldName: 'ten_trangthai_capphat', headerText: 'Trạng thái cấp phát', allowFiltering: true }
  ],
  CONTAINER_CP_COLS: [
    { fieldName: 'sothutu', headerText: 'STT', allowFiltering: true },
    { fieldName: 'ten_thanhphan', headerText: 'Kiến trúc thành phần', allowFiltering: true },
    { fieldName: 'hatang_aohoa', headerText: 'Hạ tầng ảo hóa/Cloud', allowFiltering: true },
    { fieldName: 'tenhatang', headerText: 'Tên hạ tầng K8S', allowFiltering: true },
    { fieldName: 'donvi_vh', headerText: 'Đơn vị vận hành', allowFiltering: true },
    { fieldName: 'nguoi_qtr', headerText: 'Người quản trị', allowFiltering: true },
    { fieldName: 'ip', headerText: 'IP Address', allowFiltering: true },
    { fieldName: 'ten_trangthai_capphat', headerText: 'Trạng thái cấp phát', allowFiltering: true }
  ],
  IDG_CP_COLS: [
    { fieldName: 'sothutu', headerText: 'STT', allowFiltering: true },
    { fieldName: 'ten_thanhphan', headerText: 'Kiến trúc thành phần', allowFiltering: true },
    { fieldName: 'hatang_aohoa', headerText: 'Hạ tầng ảo hóa/Cloud', allowFiltering: true },
    { fieldName: 'tenhatang', headerText: 'Tên hạ tầng', allowFiltering: true },
    { fieldName: 'congnghe', headerText: 'Công nghệ sử dụng', allowFiltering: true },
    { fieldName: 'donvi_vh', headerText: 'Đơn vị vận hành', allowFiltering: true },
    { fieldName: 'nguoi_qtr', headerText: 'Người quản trị', allowFiltering: true },
    { fieldName: 'cumdichvu', headerText: 'Cụm dịch vụ', allowFiltering: true },
    { fieldName: 'ten_trangthai_capphat', headerText: 'Trạng thái cấp phát', allowFiltering: true }
  ],
  BACKUP_CP_COLS: [
    { fieldName: 'sothutu', headerText: 'STT', allowFiltering: true },
    { fieldName: 'ten_thanhphan', headerText: 'Kiến trúc thành phần', allowFiltering: true },
    // { fieldName: 'hatang_aohoa', headerText: 'Hạ tầng ảo hóa/Cloud', allowFiltering: true },
    { fieldName: 'hatang_backup', headerText: 'Tên hạ tầng', allowFiltering: true },
    { fieldName: 'loaihatang', headerText: 'Loại hạ tầng', allowFiltering: true },
    { fieldName: 'donvi_vh', headerText: 'Đơn vị vận hành', allowFiltering: true },
    { fieldName: 'nguoi_qtr', headerText: 'Người quản trị', allowFiltering: true },
    { fieldName: 'ten_trangthai_capphat', headerText: 'Trạng thái cấp phát', allowFiltering: true }
  ],
  YEU_CAU_CP_COLS: [
    { fieldName: 'sothutu', headerText: 'STT', allowFiltering: true },
    { fieldName: 'sophieu', headerText: 'Số phiếu', allowFiltering: true },
    { fieldName: 'ten_trangthai', headerText: 'Trạng thái cấp phát', allowFiltering: true },
    { fieldName: 'ten_dv_yc', headerText: 'Đơn vị yêu cầu', allowFiltering: true },
    { fieldName: 'ngay_yc', headerText: 'Ngày gửi', allowFiltering: true },
    { fieldName: 'ngay_ht', headerText: 'Ngày hoàn thành', allowFiltering: true },
    { fieldName: 'nguoi_yc', headerText: 'Người gửi', allowFiltering: true },
    { fieldName: 'mucdich_yc', headerText: 'Mục đích sử dụng', allowFiltering: true },
    { fieldName: 'ten_spdv_chinh', headerText: 'Sản phẩm dịch vụ', allowFiltering: true },
    { fieldName: 'spdv_ten', headerText: 'Tên SPDV', allowFiltering: true },
    { fieldName: 'ten_hatangmang', headerText: 'Hạ tầng mạng', allowFiltering: true }
  ]
}

export const DATE_FORMAT = 'DD/MM/YYYY'

const DEFAULT_NC_DATA = {
  id: null,
  tmpId: null,
  thanhphan: 1,
  ngaytao: moment().format(DATE_FORMAT),
  batdau: moment().format(DATE_FORMAT),
  ketthuc: moment().format(DATE_FORMAT)
}

export const DEFAULT_AO_HOA_DATA = {
  ...DEFAULT_NC_DATA,
  vcpu: 0,
  vram: 0,
  vgpu: 0,
  ssd: 0,
  hdd: 0,
  iops: 0,
  throughtput: 0,
  network_card: 0
}

export const DEFAULT_CONTAINER_DATA = {
  ...DEFAULT_NC_DATA,
  cpu: 0,
  ram: 0,
  // ssd: 0,
  // hdd: 0,
  storage: 0,
  pod: 0
}

export const DEFAULT_IDG_DATA = {
  ...DEFAULT_NC_DATA,
  ma_rabbitmq: '',
  idg_object: 0,
  ma_kafka: '',
  ma_api_gateway: '',
  ma_mongo: '',
  ma_mysql: '',
  ma_search_engine: ''
}

export const DEFAULT_BACKUP_DATA = {
  ...DEFAULT_NC_DATA,
  tap_trung: 1,
  manual: 1,
  dungluong: 0,
  dungluong_tangtruong: 0,
  vitri_backup: '',
  tansuat: '',
  sl_backup: 0,
  external_backup: 0
}

export const DEFAULT_FORM_DATA = {
  id: 0,
  sophieu: '',
  trangthai_phieu: '',
  ten_dv: '',
  donvi_id: null,
  ten_nv: '',
  nguoi_yc_id: null,
  ngay_yc: moment().format('YYYY-MM-DD'),
  ngay_ht: '',
  ghi_chu: '',
  mucdich_yc: '',
  spdv_id: null,
  spdv_ten: '',
  hatangmang_id: null
}

export const FILE_MODULE = 'test' // HATANGCNTT

export const KIENTRUC_THANHPHAN = [
  { id: 1, name: 'Web' },
  { id: 2, name: 'APP' },
  { id: 3, name: 'DB' }
]

// export const ROOT_API = 'http://localhost:10990' // ///web-ecms
export const ROOT_API = '/web-ecms' // /web-ecms

export const TRANG_THAI_CP = [
  {
    name: 'Lưu tạm',
    value: 0
  },
  {
    name: 'Chờ cấp phát',
    value: 1
  },
  {
    name: 'Đang cấp phát',
    value: 2
  },
  {
    name: 'Hoàn thành cấp phát nội bộ',
    value: 3
  },
  {
    name: 'Hoàn thành',
    value: 4
  },
  {
    name: 'Từ chối cấp phát',
    value: 5
  }
]

export const BACKUP_OPTIONS = [
  { name: 'Không', value: 0 },
  { name: 'Có', value: 1 }
]

export const APP_SECRET =
  'eyJkZXZpY2VfaWQiOiI2MUVDNDg2RS02RUE2LTQ0OTUtQkRBRS0xQkFDMDExRjQzMDAiLCJkZXZpY2VfaXAiOiJlcnJvciIsImRldmljZV9uYW1lIjoiaVBob25lIC0gaVBob25lIDEyIFBybyBNYXgiLCJtYWNfYWRkcmVzcyI6IlVua25vdyIsIm1vYmlsZV9pZCI6IlVua25vdyIsImFwcF9pZCI6IjIiLCJhcHBfdmVyc2lvbiI6IjIuNC4xLjQwIiwib3NfdmVyc2lvbiI6ImlPUyAtIDE0LjUifQ=='
