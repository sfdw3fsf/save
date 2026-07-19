export default {
  getLanTaoDuLieu: (axios, data) =>
    axios.get(
      "web-thuno/api/thu-no/xu-ly-thue-bao/nhac-no-thue-bao/lay-danh-sach-lan-tao-du-lieu-nhac-no",
      { params: data }
    ),
  getLanNhacNo: (axios, data) =>
    axios.get(
      "web-thuno/api/thu-no/xu-ly-thue-bao/nhac-no-thue-bao/lay-danh-sach-lan-nhac-no",
      { params: data }
    ),
  getListChuaNhacNo: (axios, data) =>
    axios.get(
      "web-thuno/api/thu-no/xu-ly-thue-bao/nhac-no-thue-bao/lay-danh-sach-thue-bao-chua-nhac-no",
      { params: data }
    ),
  getListNhacNo: (axios, data) =>
    axios.get("web-thuno/api/thu-no/xu-ly-thue-bao/ds-nhac-no", {
      params: data
    }),
  getListDaNhacNo: (axios, data) =>
    axios.get(
      "web-thuno/api/thu-no/xu-ly-thue-bao/nhac-no-thue-bao/lay-danh-sach-thue-bao-da-nhac-no",
      { params: data }
    ),
  chuyenNhacNoAction: (axios, data) =>
    axios.put(
      "web-thuno/api/thu-no/xu-ly-thue-bao/nhac-no-thue-bao/cap-nhat-chuyen-danh-sach-sang-nhac-no",
      data
    ),
  huyNhacNoAction: (axios, data) =>
    axios.put(
      "web-thuno/api/thu-no/xu-ly-thue-bao/nhac-no-thue-bao/huy-danh-sach-phieu-nhac-no",
      data
    ),
  huyLanNhacAction: (axios, data) =>
    axios.put(
      "web-thuno/api/thu-no/xu-ly-thue-bao/nhac-no-thue-bao/huy-lan-nhac",
      data
    ),
  capNhatNhacNoAction: (axios, data) =>
    axios.put(
      "web-thuno/api/thu-no/xu-ly-thue-bao/nhac-no-thue-bao/cap-nhat-trang-thai-hoan-thanh-lan-nhac-no",
      data
    )
};
