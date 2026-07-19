export default {
  headerGridboxDSTBKHGoc: [
    { fieldName: 'ma_tt', headerText: 'Mã thanh toán', allowFiltering: true, allowSorting: true },
    { fieldName: 'ma_tb', headerText: 'Số máy/Account', allowFiltering: true, allowSorting: true },
    { fieldName: 'main_ghep', headerText: 'Mã in ghép', allowFiltering: true, allowSorting: true },
    { fieldName: 'ten_dvvt', headerText: 'Dịch vụ', allowFiltering: true, allowSorting: true },
    { fieldName: 'loaihinh_tb', headerText: 'Loại hình', allowFiltering: true, allowSorting: true },
    { fieldName: 'trangthai_tb', headerText: 'Trạng thái', allowFiltering: true, allowSorting: true }
  ],
  headerGridboxDSTBKHGhep: [
    { fieldName: 'ma_tb', headerText: 'Số máy/Account', allowFiltering: true, allowSorting: true },
    { fieldName: 'loaihinh_tb', headerText: 'Loại hình', allowFiltering: true, allowSorting: true },
    { fieldName: 'trangthai_tb', headerText: 'Trạng thái', allowFiltering: true, allowSorting: true, customAttributes: { class: 'customTrangThai' } }
  ],
  headerGridboxDSHDKHGhep: [
    { fieldName: 'ma_tt', headerText: 'Mã thanh toán', allowFiltering: true, allowSorting: true },
    { fieldName: 'main_ghep', headerText: 'Mã in ghép', allowFiltering: true, allowSorting: true },
    { fieldName: 'ten_tt', headerText: 'Tên thanh toán', allowFiltering: true, allowSorting: true },
    { fieldName: 'diachi_bc', headerText: 'Địa chỉ BC', allowFiltering: true, allowSorting: true },
    { fieldName: 'diachi_ig', headerText: 'Địa chỉ in ghép', allowFiltering: true, allowSorting: true }
  ]
}
