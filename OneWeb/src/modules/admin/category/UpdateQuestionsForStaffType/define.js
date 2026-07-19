export default {
  cboLoaiNhanvien: [],
  cboHinhthuc: [],
  gridDanhSachChuagan: [],
  gridDanhSachDagan: [],
  form: {
    loai_nv: null,
    hinhthuc: null
  },
  columns_nv: [
    {
      fieldName: "cauhoi_id",
      headerText: "CauHoi_id",
      allowFiltering: true,
      allowSorting: true,
      width: 'auto'
    },
    {
      fieldName: "noidung",
      headerText: "Nội dung",
      allowFiltering: true,
      allowSorting: true,
      width: 'auto'
    },
    {
      fieldName: "thutu",
      headerText: "Thứ tự",
      allowFiltering: true,
      allowSorting: true,
      width: 'auto'
    },
  ]
}
