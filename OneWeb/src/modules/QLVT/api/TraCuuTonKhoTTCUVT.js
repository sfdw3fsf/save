import axios from "axios";

export default {
  getTraCuuVatTu: (axios, data) =>
    axios.post(`/web-qlvt/api/tra-cuu-ton-kho/ton-kho?page-no=${data.pageNo}&page-size=${data.pageSize}`, data.dataRequest),
  getDanhSachKho: axios => axios.get("/web-qlvt/api/tra-cuu-ton-kho/danh-sach-kho")
};
