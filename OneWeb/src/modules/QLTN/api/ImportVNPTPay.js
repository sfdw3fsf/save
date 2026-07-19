export default {
  themThanhToanCaNhan: (axios, data) =>
    axios.post(
      "/web-thuno/api/thu-no/vnpt-pay/them-thanh-toan-ca-nhan", data
    ),
  moiMoVi: (axios, data) =>
    axios.post(
      "/web-thuno/api/thu-no/vnpt-pay/import-moi-mo-vi", data
    ),
  themSoViCaNhan: (axios, data) =>
    axios.post(
      "/web-thuno/api/thu-no/vnpt-pay/them-so-vi-ca-nhan", data
    ),
  dsThanhToanCuocTraSauVNPTPay: (axios, data) =>
    axios.get(
      "/web-thuno/api/thu-no/vnpt-pay/ds-vi/" + data
    ),
  themDanhSachNapThe: (axios, data) =>
    axios.post(
      "/web-thuno/api/thu-no/vnpt-pay/them-ds-nap-the", data
    ),
  themDanhSachMucTieuBanGoi: (axios, data) =>
    axios.post(
      "/web-thuno/api/thu-no/vnpt-pay/import-muc-tieu-ban-goi", data
    ),
};
