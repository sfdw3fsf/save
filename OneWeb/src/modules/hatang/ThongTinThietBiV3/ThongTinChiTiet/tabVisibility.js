/**
 * Tab indices - Định nghĩa các chỉ số tab
 */
export const TAB_INDICES = {
  THONG_TIN_CHUNG: 1,
  THONG_TIN_LAP_DAT: 3,
  TAI_SAN: 4,
  LICH_SU_THAY_DOI: 5,
  THONG_TIN_KHACH_HANG: 6,
  NGHIEP_VU_IDC: 7,
  CONG_KET_NOI: 8,
  HE_THONG_DICH_VU: 99,
  CAU_HINH_PHAN_CUNG: 98,
  VUNG_DU_LIEU: 97,
  LUU_TRU_TAI_NGUYEN: 96,
  HA_TANG_AO_HOA: 1001,
  NANG_LUC_LUU_TRU: 1002
}

/**
 * Kiểm tra xem tab có được hiển thị dựa trên deviceTypeInfo
 * @param {Object} deviceTypeInfo - Thông tin loại thiết bị (chứa các flags như isServer, isNetwork, etc.)
 * @param {number} tabIndex - Index của tab cần kiểm tra
 * @returns {boolean} true nếu tab được hiển thị, false nếu không
 */
export const checkTabVisibility = (deviceTypeInfo, tabIndex) => {
  if (!deviceTypeInfo) return false
  const activeDeviceType = Object.keys(deviceTypeInfo).find(
    (key) => key.startsWith('is') && deviceTypeInfo[key] === true
  )
  const tabsForDeviceType = DEVICE_TYPE_TABS_CONFIG[activeDeviceType]
  if (!tabsForDeviceType) return false
  return tabsForDeviceType.includes(tabIndex)
}

export const DEVICE_TYPE_TABS_CONFIG = {
  isServer: [
    TAB_INDICES.THONG_TIN_CHUNG,
    TAB_INDICES.CAU_HINH_PHAN_CUNG,
    TAB_INDICES.CONG_KET_NOI,
    TAB_INDICES.THONG_TIN_LAP_DAT,
    TAB_INDICES.TAI_SAN,
    TAB_INDICES.HA_TANG_AO_HOA,
    TAB_INDICES.THONG_TIN_KHACH_HANG,
    TAB_INDICES.HE_THONG_DICH_VU,
    TAB_INDICES.LICH_SU_THAY_DOI,
    TAB_INDICES.NGHIEP_VU_IDC
  ],
  isServerBlade: [
    TAB_INDICES.THONG_TIN_CHUNG,
    TAB_INDICES.CAU_HINH_PHAN_CUNG,
    TAB_INDICES.CONG_KET_NOI,
    TAB_INDICES.THONG_TIN_LAP_DAT,
    TAB_INDICES.TAI_SAN,
    TAB_INDICES.HA_TANG_AO_HOA,
    TAB_INDICES.THONG_TIN_KHACH_HANG,
    TAB_INDICES.HE_THONG_DICH_VU,
    TAB_INDICES.LICH_SU_THAY_DOI,
    TAB_INDICES.NGHIEP_VU_IDC
  ],
  isBlade: [
    TAB_INDICES.THONG_TIN_CHUNG,
    TAB_INDICES.CAU_HINH_PHAN_CUNG,
    TAB_INDICES.TAI_SAN,
    TAB_INDICES.LICH_SU_THAY_DOI
  ],
  isTuDia: [
    TAB_INDICES.THONG_TIN_CHUNG,
    TAB_INDICES.NANG_LUC_LUU_TRU,
    TAB_INDICES.CAU_HINH_PHAN_CUNG,
    TAB_INDICES.CONG_KET_NOI,
    TAB_INDICES.THONG_TIN_LAP_DAT,
    TAB_INDICES.TAI_SAN,
    TAB_INDICES.THONG_TIN_KHACH_HANG,
    TAB_INDICES.HE_THONG_DICH_VU,
    TAB_INDICES.LICH_SU_THAY_DOI,
    TAB_INDICES.NGHIEP_VU_IDC,
    TAB_INDICES.VUNG_DU_LIEU,
    TAB_INDICES.LUU_TRU_TAI_NGUYEN
  ],
  isBoDieuKhien: [
    TAB_INDICES.THONG_TIN_CHUNG,
    TAB_INDICES.CONG_KET_NOI,
    TAB_INDICES.THONG_TIN_LAP_DAT,
    TAB_INDICES.TAI_SAN,
    TAB_INDICES.LICH_SU_THAY_DOI,
    TAB_INDICES.NGHIEP_VU_IDC
  ],
  isKhayDia: [
    TAB_INDICES.THONG_TIN_CHUNG,
    TAB_INDICES.CONG_KET_NOI,
    TAB_INDICES.THONG_TIN_LAP_DAT,
    TAB_INDICES.TAI_SAN,
    TAB_INDICES.LICH_SU_THAY_DOI,
    TAB_INDICES.NGHIEP_VU_IDC
  ],
  isNetwork: [
    TAB_INDICES.THONG_TIN_CHUNG,
    TAB_INDICES.CAU_HINH_PHAN_CUNG,
    TAB_INDICES.CONG_KET_NOI,
    TAB_INDICES.THONG_TIN_LAP_DAT,
    TAB_INDICES.TAI_SAN,
    TAB_INDICES.HA_TANG_AO_HOA,
    TAB_INDICES.THONG_TIN_KHACH_HANG,
    TAB_INDICES.HE_THONG_DICH_VU,
    TAB_INDICES.LICH_SU_THAY_DOI,
    TAB_INDICES.NGHIEP_VU_IDC
  ],
  isLinecard: [
    TAB_INDICES.THONG_TIN_CHUNG,
    TAB_INDICES.CONG_KET_NOI,
    TAB_INDICES.TAI_SAN,
    TAB_INDICES.LICH_SU_THAY_DOI
  ],
  isPowercard: [
    TAB_INDICES.THONG_TIN_CHUNG,
    TAB_INDICES.CONG_KET_NOI,
    TAB_INDICES.TAI_SAN,
    TAB_INDICES.LICH_SU_THAY_DOI
  ],
  isConsolecard: [
    TAB_INDICES.THONG_TIN_CHUNG,
    TAB_INDICES.CONG_KET_NOI,
    TAB_INDICES.TAI_SAN,
    TAB_INDICES.LICH_SU_THAY_DOI
  ],
  isCard: [
    TAB_INDICES.THONG_TIN_CHUNG,
    TAB_INDICES.CONG_KET_NOI,
    TAB_INDICES.TAI_SAN,
    TAB_INDICES.LICH_SU_THAY_DOI
  ]
}

export const TAB_CONFIG = [
  {
    id: TAB_INDICES.THONG_TIN_CHUNG,
    href: '#tabThongTinChung',
    label: 'Thông tin chung',
    alwaysVisible: true
  },
  {
    id: TAB_INDICES.NANG_LUC_LUU_TRU,
    href: '#tabNangLucLuuTru',
    label: 'Năng Lực Lưu Trữ'
  },
  {
    id: TAB_INDICES.CAU_HINH_PHAN_CUNG,
    href: '#tabCauHinhPhanCung',
    label: 'Cấu hình phần cứng'
  },
  {
    id: TAB_INDICES.CONG_KET_NOI,
    href: '#tabCongKetNoi',
    label: 'Cổng kết nối'
  },
  {
    id: TAB_INDICES.THONG_TIN_LAP_DAT,
    href: '#tabThongTinLapDat',
    label: 'Thông tin lắp đặt'
  },
  {
    id: TAB_INDICES.TAI_SAN,
    href: '#tabThongTinKiemKe',
    label: 'Tài sản'
  },
  {
    id: TAB_INDICES.HA_TANG_AO_HOA,
    href: '#tabHaTangAoHoa',
    label: 'Hạ Tầng Ảo Hóa'
  },
  {
    id: TAB_INDICES.THONG_TIN_KHACH_HANG,
    href: '#tabThongTinKhachHang',
    label: 'Thông tin khách hàng'
  },
  {
    id: TAB_INDICES.HE_THONG_DICH_VU,
    href: '#tabHeThongDichVu',
    label: 'Hệ thống dịch vụ'
  },
  {
    id: TAB_INDICES.LICH_SU_THAY_DOI,
    href: '#tabLichSuThietBi',
    label: 'Lịch sử thay đổi'
  },
  {
    id: TAB_INDICES.NGHIEP_VU_IDC,
    href: '#tabNghiepVuIdc',
    label: 'Nghiệp vụ IDC'
  },
  {
    id: TAB_INDICES.VUNG_DU_LIEU,
    href: '#tabVungDuLieu',
    label: 'Vùng dữ liệu'
  },
  {
    id: TAB_INDICES.LUU_TRU_TAI_NGUYEN,
    href: '#tabLuuTruTaiNguyen',
    label: 'Lưu trữ tài nguyên'
  }
]
