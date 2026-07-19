export default {
  // lay_danhsach_tinh: async (axios) => axios.get('/web-tracuu/cntt_tskt/ds_tinh'),
  lay_danhsach_tinh: async axios =>
    axios.get("/web-gqkn/tiep-nhan-khieu-nai/lay_ds_tinh_diadanh"),
  lay_danhsach_quan_theo_tinh: async (axios, tinh_id) =>
    axios.get("/web-quantri/danhmuc/diaban/quan?tinh_id=" + tinh_id),

  // api chưa tường minh sài api khác tạm thời
  // lay_danhsach_phuong: async (axios) => axios.post('/web-quantri/danhmuc-chung/CSS_PHUONG',{}),
  lay_danhsach_phuong: async (axios, quan_id) =>
    axios.post("/web-quantri/danhmuc/diaban/phuong-theo-quan", {
      p_quan_id: quan_id
    }),
  lay_danhsach_pho: async (axios, phuong_id) =>
    axios.post("/web-quantri/danhmuc-chung/CSS_PHO", {
      ID: phuong_id,
      CATEGORY_NAME: 1
    }),

  // khu, ấp chưa rõ output ra không có dữ liệu test
  lay_danhsach_ap: async (axios, phuong_id) =>
    axios.post("/web-quantri/danhmuc-chung/CSS_PHO", {
      ID: phuong_id,
      CATEGORY_NAME: 2
    }),
  lay_danhsach_khu: async (axios, phuong_id) =>
    axios.post("/web-quantri/danhmuc-chung/CSS_PHO", {
      ID: phuong_id,
      CATEGORY_NAME: 3
    }),

  // chưa rõ model truyền vào và input trả ra dùng tạm api khác
  // lay_danhsach_dacdiem:  async (axios, data) => axios.post('/web-quantri/danhmuc-chung/CSS_DACDIEM',data),
  lay_danhsach_dacdiem: async (axios, pho_id, phuong_id) =>
    axios.post("/web-quantri/DonViDiaDanh/sp_lay_ds_dacdiem", {
      p_pho_id: pho_id,
      p_phuong_id: phuong_id
    })
};
