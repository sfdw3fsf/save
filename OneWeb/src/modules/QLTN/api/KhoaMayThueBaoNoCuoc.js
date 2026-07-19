export default {
  getDanhSachDonViQL: (axios, data) => axios.get("/web-thuno/api/thu-no/lay-danh-sach-don-vi-quan-ly", { params: data }),
  getDanhSachQuyTrinhKhoaMay: (axios, data) => axios.get("/web-thuno/api/thu-no/xu-ly-thue-bao/khoa-may-theo-so-may-va-tai-khoan/lay-quy-trinh-khoa-may", { params: data }),
  getDanhSachLoaiPhieu: (axios) => axios.get("/web-thuno/api/thu-no/common/loai-phieu-thu-cuoc"),
  khoaMayTheoFile: (axios, data) => axios.post("/web-thuno/api/thu-no/xu-ly-thue-bao/khoa-may-theo-file", data),
  getDanhSachKhoaMay: (axios, data) => axios.post("/web-thuno/api/thu-no/xu-ly-thue-bao/tao-ds-khoa-mo", data),
  getDanhSachKhoaMayKiemTraLapHD: (axios, data) => axios.post("/web-thuno/api/thu-no/xu-ly-thue-bao/tao-ds-khoa-mo-kiem-tra-lap-hd", data),
  capNhatDanhSach: (axios, data) => axios.post("/web-thuno/api/thu-no/xu-ly-thue-bao/chuyen-ds-khoa-mo-may-sang-hop-dong-khoa-mo", data),
  capNhatDanhSachV3: (axios, data) => axios.post("/web-thuno/api/thu-no/xu-ly-thue-bao/chuyen-ds-khoa-mo-may-sang-hop-dong-khoa-mo-v3", data),
  kiemtraFile: (axios, data) => axios.post("/web-thuno/api/thu-no/quan-ly-dai-ly/kiem-tra-danh-sach", data)
};
