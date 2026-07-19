export default {
  get: async (axios, data) =>
    axios.get("web-thuno/api/thu-no/lay-danh-sach-don-vi-quan-ly", {
      params: data
    }),
  getNam: async (axios, data) =>
    axios.get("web-thuno/api/thu-no/giao-san-luong/lay-nam-giao"),
  getThang: async (axios, data) =>
    axios.get("web-thuno/api/thu-no/giao-san-luong/lay-thang-giao"),
  getQLVTNhanVienChot: async (axios, data) =>
    axios.get("web-thuno/api/thu-no/giao-san-luong/lay-nhan-vien-chot"),
  putCapNhatKhoa: async (axios, data) =>
    axios.put(
      "web-thuno/api/thu-no/giao-san-luong/khoa-mo-giao-san-luong",
      data
    ),
  postKiemTraDaKhoaSL: async (axios, data) =>
    axios.post(
      "web-thuno/api/thu-no/giao-san-luong/kiem-tra-da-khoa-giao-san-luong",
      data
    ),
  getDSTLTC: async (axios, data) =>
    axios.get("web-thuno/api/thu-no/giao-san-luong/lay-danh-sach-tltc", {
      params: data
    }),
  getKhoaTLTC: async (axios, data) =>
    axios.get("web-thuno/api/thu-no/giao-san-luong/khoa-tltc", {
      params: data
    }),
  postCapNhatGiaoSL: async (axios, data) =>
    axios.put(
      "web-thuno/api/thu-no/giao-san-luong/giao-san-luong-nhan-vien-thu-cuoc",
      data
    ),
  postXoaGiaoSL: async (axios, data) =>
    axios.put("web-thuno/api/thu-no/giao-san-luong/huy-giao-san-luong", data)
};
