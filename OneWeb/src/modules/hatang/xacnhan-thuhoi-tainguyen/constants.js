import moment from 'moment'

export const YEU_CAU_CP_TABLES = {
  AO_HOA_CP_COLS: [
    { fieldName: 'SOTHUTU', headerText: 'STT', allowFiltering: true },
    // { fieldName: 'KIENTRUC_THANHPHAN', headerText: 'Kiến trúc thành phần', allowFiltering: true },
    // { fieldName: 'HATANG_AOHOA_CLOUD', headerText: 'Hạ tầng ảo hóa/Cloud', allowFiltering: true },
    { fieldName: 'CUMDICHVU', headerText: 'Cụm dịch vụ', allowFiltering: true },
    { fieldName: 'VM', headerText: 'Tên VM', allowFiltering: true },
    { fieldName: 'DONVIVH', headerText: 'Đơn vị vận hành', allowFiltering: true },
    { fieldName: 'NGUOI_QUANTRI', headerText: 'Người quản trị', allowFiltering: true },
    { fieldName: 'IPADDRESS', headerText: 'IP Address', allowFiltering: true },
    { fieldName: 'TRANGTHAI_THUHOI', headerText: 'Trạng thái thu hồi', allowFiltering: true }
  ],
  CONTAINER_CP_COLS: [
    { fieldName: 'SOTHUTU', headerText: 'STT', allowFiltering: true },
    // { fieldName: 'KIENTRUC_THANHPHAN', headerText: 'Kiến trúc thành phần', allowFiltering: true },
    // { fieldName: 'HATANG_AOHOA_CLOUD', headerText: 'Hạ tầng ảo hóa/Cloud', allowFiltering: true },
    { fieldName: 'TEN', headerText: 'Tên hạ tầng K8S', allowFiltering: true },
    { fieldName: 'DONVIVH', headerText: 'Đơn vị vận hành', allowFiltering: true },
    { fieldName: 'NGUOI_QTRI', headerText: 'Người quản trị', allowFiltering: true },
    { fieldName: 'IPADDRESS', headerText: 'IP Address', allowFiltering: true },
    { fieldName: 'TRANGTHAI_THUHOI', headerText: 'Trạng thái thu hồi', allowFiltering: true }
  ],
  IDG_CP_COLS: [
    { fieldName: 'SOTHUTU', headerText: 'STT', allowFiltering: true },
    // { fieldName: 'KIENTRUC_THANHPHAN', headerText: 'Kiến trúc thành phần', allowFiltering: true },
    // { fieldName: 'HATANG_AOHOA_CLOUD', headerText: 'Hạ tầng ảo hóa/Cloud', allowFiltering: true },
    { fieldName: 'TEN', headerText: 'Tên hạ tầng', allowFiltering: true },
    { fieldName: 'CONGNGHE', headerText: 'Công nghệ sử dụng', allowFiltering: true },
    { fieldName: 'DONVIVH', headerText: 'Đơn vị vận hành', allowFiltering: true },
    { fieldName: 'NGUOI_QTRI', headerText: 'Người quản trị', allowFiltering: true },
    { fieldName: 'LOAIGOI', headerText: 'Loại gói', allowFiltering: true },
    { fieldName: 'GIATRIGOI', headerText: 'Giá trị gói', allowFiltering: true },
    { fieldName: 'TRANGTHAI_THUHOI', headerText: 'Trạng thái thu hồi', allowFiltering: true }
  ],
  BACKUP_CP_COLS: [
    { fieldName: 'SOTHUTU', headerText: 'STT', allowFiltering: true },
    // { fieldName: 'KIENTRUC_THANHPHAN', headerText: 'Kiến trúc thành phần', allowFiltering: true },
    // { fieldName: 'HATANG_AOHOA_CLOUD', headerText: 'Hạ tầng ảo hóa/Cloud', allowFiltering: true },
    { fieldName: 'TEN', headerText: 'Tên hạ tầng', allowFiltering: true },
    { fieldName: 'LOAI_HATANG', headerText: 'Loại hạ tầng', allowFiltering: true },
    { fieldName: 'CONGNGHE', headerText: 'Công nghệ sử dụng', allowFiltering: true },
    { fieldName: 'DONVIVH', headerText: 'Đơn vị vận hành', allowFiltering: true },
    { fieldName: 'NGUOI_QTRI', headerText: 'Người quản trị', allowFiltering: true },
    { fieldName: 'TRANGTHAI_THUHOI', headerText: 'Trạng thái thu hồi', allowFiltering: true }
  ],
  // YEU_CAU_CP_COLS: [
  //   { fieldName: 'SOTHUTU', headerText: 'STT', allowFiltering: true },
  //   { fieldName: 'KIENTRUC_THANHPHAN', headerText: 'Số phiếu', allowFiltering: true },
  //   { fieldName: 'HATANG_AOHOA_CLOUD', headerText: 'Trạng thái cấp phát', allowFiltering: true },
  //   { fieldName: 'TEN_HATTANG', headerText: 'Đơn vị yêu cầu', allowFiltering: true },
  //   { fieldName: 'CONGNGHE_SUDUNG', headerText: 'Ngày gửi', allowFiltering: true },
  //   { fieldName: 'DONVI_VANHANH', headerText: 'Ngày hoàn thành', allowFiltering: true },
  //   { fieldName: 'NGUOI_QUANTRI', headerText: 'Người gửi', allowFiltering: true },
  //   { fieldName: 'TRANGTHAI_CAPPHAT', headerText: 'Mục đích sử dụng', allowFiltering: true },
  //   { fieldName: 'TRANGTHAI_CAPPHAT', headerText: 'Sản phẩm dịch vụ', allowFiltering: true },
  //   { fieldName: 'TRANGTHAI_CAPPHAT', headerText: 'Tên SPDV', allowFiltering: true },
  //   { fieldName: 'TRANGTHAI_CAPPHAT', headerText: 'Hạ tầng mạng', allowFiltering: true }
  // ]
}

const DEFAULT_DATE_DATA = {
  thoigian_khoitao: moment().format('YYYY-MM-DD'),
  thoigian_batdau: moment().format('YYYY-MM-DD'),
  thoigian_ketthuc: moment().format('YYYY-MM-DD')
}

export const DEFAULT_AO_HOA_DATA = {
  id: null,
  tmpId: null,
  kientruc: '',
  ...DEFAULT_DATE_DATA,
  vcpu: 0,
  vram: 0,
  vgpu: 0,
  ssd: 0,
  hdd: 0,
  iops: 0,
  throughput: 0,
  so_card_mang: 0
}

export const DEFAULT_CONTAINER_DATA = {}

export const DEFAULT_IDG_DATA = {}

export const DEFAULT_BACKUP_DATA = {
  id: null,
  tmpId: null,
  kientruc: '',
  ...DEFAULT_DATE_DATA,
  backup_taptrung: 0,
  backup_manual: 0,
  dungluong_backup: 0,
  dungluong_tangtruong: 0,
  vitri_backup: '',
  tansuat_backup: '',
  soban_backup: 0,
  luutru_ngoai: 0
}
