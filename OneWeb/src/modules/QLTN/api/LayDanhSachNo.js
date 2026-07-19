export default {
  getDanhSachThueBao: (axios, data) =>
    axios.post(
      "/web-thuno/api/thu-no/xu-ly-thue-bao/lay-danh-sach-no/lay-thong-tin-thue-bao-no",
      data
    )
};
