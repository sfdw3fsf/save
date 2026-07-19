export default {
  getKyCuocHienHanh: (axios) => axios.get('/web-thuno/api/thu-no/common/ky-cuoc-hien-hanh'),
  getDanhSachThamSoMDMATS: async (axios, data) => axios.get('web-thuno/api/thu-no/mo-may-theo-danh-sach/lay-danh-sach-tham-so-md-mats', {
    params: data
  }),
  getDanhSachNhomKetQuaXuLy: async (axios, data) => axios.get('web-thuno/api/thu-no/xu-ly-thue-bao/duyet-ket-qua-xu-ly-no-cuoc/lay-danh-sach-nhom-ket-qua-xu-ly', {
    params: data
  }),
  getDanhSachDichVuKhoaMayXacMinh: async (axios, data) => axios.get('web-thuno/api/thu-no/xu-ly-thue-bao/duyet-ket-qua-xu-ly-no-cuoc/lay-danh-sach-dich-vu-khoa-may-xac-minh', {
    params: data
  }),
  getDanhSachLyDoTra: async (axios, data) => axios.get('web-thuno/api/thu-no/nguyen-nhan-no/lay-danh-sach-ly-do-tra', {
    params: data
  }),
  getDSThamSoMD: async (axios, data) => axios.get('web-thuno/api/thu-no/xu-ly-thue-bao/trung-tam-vien-thong-xac-minh-no-cuoc/lay-tat-ca-tham-so-mac-dinh', {
    params: data
  }),
  getDSQuyTrinhL1: async (axios, data) => axios.get('web-thuno/api/thu-no/xu-ly-thue-bao/ds-quy-trinh', {
    params: data
  }),
  getDSQuyTrinhL2: async (axios, data) => axios.get('web-thuno/api/thu-no/xu-ly-thue-bao/trung-tam-vien-thong-xac-minh-no-cuoc/lay-danh-sach-quy-trinh', {
    params: data
  }),
  getKetQua: async (axios, data) => axios.get('web-thuno/api/thu-no/xu-ly-thue-bao/duyet-ket-qua-xu-ly-no-cuoc/lay-danh-sach-ket-qua-xu-ly', {
    params: data
  }),
  getDSHDTBKhoaMoMayHC: async (axios, data) => axios.get('web-thuno/api/thu-no/xu-ly-thue-bao/duyet-ket-qua-xu-ly-no-cuoc/lay-danh-sach-hdtb-khoa-mo-may-cho-xac-nhan', {
    params: data
  }),
  getDSDiemTinNhiem: async (axios, data) => axios.get('web-thuno/api/thu-no/xu-ly-thue-bao/duyet-ket-qua-xu-ly-no-cuoc/lay-danh-sach-diem-tin-nhiem', data),
  getHuongGiaoQuyTrinh: async (axios, data) => axios.get('web-thuno/api/thu-no/xu-ly-thue-bao/duyet-ket-qua-xu-ly-no-cuoc/lay-danh-sach-huong-giao-quy-trinh', {
    params: data
  }),
  getDSControll: async (axios, data) => axios.get('web-thuno/api/thu-no/xu-ly-thue-bao/duyet-ket-qua-xu-ly-no-cuoc/lay-danh-sach-control', {
    params: data
  }),
  getDsLyDoHuy: async (axios, data) => axios.get('web-thuno/api/thu-no/xu-ly-thue-bao/duyet-ket-qua-xu-ly-no-cuoc/lay-danh-sach-ly-do-huy-theo-hop-dong-thue-bao', {
    params: data
  }),
  getDSNhanVienTheoPhieuId: async (axios, data) => axios.get('web-thuno/api/thu-no/xu-ly-thue-bao/duyet-ket-qua-xu-ly-no-cuoc/lay-danh-sach-nhan-vien-theo-phieu-id', {
    params: data
  }),
  getDSNhomKQKQXuLyId: async (axios, data) => axios.get('web-thuno/api/thu-no/xu-ly-thue-bao/duyet-ket-qua-xu-ly-no-cuoc/lay-danh-sach-nhom-kq-kq-xu-ly-id', {
    params: data
  }),
  getKiemTraDSGiaoPhieuTheoPhieuId: async (axios, data) => axios.get('web-thuno/api/thu-no/xu-ly-thue-bao/duyet-ket-qua-xu-ly-no-cuoc/kiem-tra-danh-sach-giao-phieu-theo-phieu-id', {
    params: data
  }),
  putCapNhatXacMinhNoCuoc: async (axios, data) => axios.put('web-thuno/api/thu-no/xu-ly-thue-bao/trung-tam-vien-thong-xac-minh-no-cuoc/cap-nhat-xac-minh-no-cuoc',
    data
  ),
  putCapNhatDuyetNoCuoc: async (axios, data) => axios.put('web-thuno/api/thu-no/xu-ly-thue-bao/duyet-ket-qua-xu-ly-no-cuoc/cap-nhat-duyet-yeu-cau',
    data
  ),
  deleteLyDoHuy: async (axios, data) => axios.delete('web-thuno/api/thu-no/xu-ly-thue-bao/duyet-ket-qua-xu-ly-no-cuoc/xoa-ly-do-huy',
  { data: data }
  ),
  getDanhSachNhomHuy: async (axios, data) => axios.get('web-thuno/api/thu-no/xu-ly-thue-bao/trung-tam-vien-thong-xac-minh-no-cuoc/lay-danh-sach-nhom-huy', {
    params: data
  }),
  getDanhSachLyDoHuy: async (axios, data) => axios.get('web-thuno/api/thu-no/xu-ly-thue-bao/trung-tam-vien-thong-xac-minh-no-cuoc/lay-danh-sach-ly-do-huy', {
    params: data
  }),
  putCapNhatThayDoiLyDoHuy: async (axios, data) => axios.put('web-thuno/api/thu-no/xu-ly-thue-bao/trung-tam-vien-thong-xac-minh-no-cuoc/cap-nhat-thay-doi-ly-do-huy',
  data
),
  getLichSuKS: (axios,id) => axios.get('/web-thuno/api/thu-no/quan-ly-dai-ly/lich-su-khao-sat-khong-luu-dong/thue-bao/' + id),
}
