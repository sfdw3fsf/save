import axios from "axios";

export default {
  getListLoaiCT: axios =>
    axios.get("/web-qlvt/api/tra-cuu-chung-tu/loai-chung-tu"),
  getListMucDich: axios => axios.get("/web-qlvt/api/tra-cuu-chung-tu/muc-dich"),
  getListTrangThaiCT: axios =>
    axios.get("/web-qlvt/api/tra-cuu-chung-tu/trang-thai-chung-tu"),
  getListDonViDK: (axios) =>
    axios.get(`/web-qlvt/api/tra-cuu-chung-tu/ds-donvi-dk`),
  getListVatTuDK: axios =>
    axios.get("/web-qlvt/api/tra-cuu-chung-tu/ds-vat-tu/"),
  getListDonVi: (axios) =>
    axios.get(`/web-qlvt/api/tra-cuu-chung-tu/all-don-vi`),
  getListKhoDonVi: (axios, nhanVienId, donViId) =>
    axios.post(
      `/web-qlvt/api/tra-cuu-chung-tu/kho-donvi/${nhanVienId}/${donViId}`
    ),
  getListTuKho: (axios) =>
    axios.get(`/web-qlvt/api/tra-cuu-chung-tu/ds-tu-kho`),
  getKieuPhieu: (axios, loaiCtId) =>
    axios.get(`/web-qlvt/api/tra-cuu-chung-tu/lay-ds-kieu-phieu/${loaiCtId}`),
  getTraCuuChungTu: (axios, data) =>
    axios.post(`/web-qlvt/api/tra-cuu-chung-tu/tra-cuu-ds-chung-tu?page-no=${data.pageNo}&page-size=${data.pageSize}&total-row=${data.totalRow}`, data.dataRequest),
  getChungTuVatTu: (axios, chungTuId) =>
    axios.get(
      `/web-qlvt/api/tra-cuu-chung-tu/lay-ds-vattu-chungtu/${chungTuId}`
    ),
  getChungTuThietBi: (axios, chungTuId) =>
    axios.get(
      `/web-qlvt/api/tra-cuu-chung-tu/lay-ds-thietbi-chungtu/${chungTuId}`
    ),
  tra_cuu_chung_tu_da_xoa: (axios, soPhieu, kieu) =>
    axios.get(
      `/web-qlvt/api/tra-cuu-chung-tu/lay-ds-chungtu-da-xoa/?soPhieu=${soPhieu}&kieu=${kieu}`
    )
};