export const mutations = {
  setFormSearchResult(state, data) {
    state.formResult = data
  },
  setdsNhanVienTheoLoai(state, data) {
    state.dsNhanVienTheoLoai = data
  },
  setBuocXuLyNo(state, data) {
    state.formData.p_buocxyly_no = data
  },
  setCheckNgayGiao(state, data) {
    state.formData.p_chk_ngaygiao = data
  },
  setNgayGiaoTuNgay(state, data) {
    state.formData.p_ngaygiao_tungay = data
  },
  setNgayGiaoDenNgay(state, data) {
    state.formData.p_ngaygiao_denngay = data
  },
  setCheckLanTao(state, data) {
    state.formData.p_chk_lantao = data
  },
  setCheckLoaiNhanVien(state, data) {
    state.formData.p_chk_loainhanvien = data
  },
  setLanTaoXuLyNo(state, data) {
    state.formData.p_lantao_xyly_no = data
  },
  setLoaiNhanVien(state, data) {
    state.formData.p_loai_nhanvien = data
  },
  setCheckNhanVien(state, data) {
    state.formData.p_chk_nhanvien = data
  },
  setNhanVien(state, data) {
    state.formData.p_nhanvien = data
  },
  setCheckLuotGiao(state, data) {
    state.formData.p_chk_luotgiao = data
  },
  setLuotGiao(state, data) {
    state.formData.p_luotgiao = data
  },
  setPageInfo(state, data) {
    state.pageInfo = data
  },
  setCurrentPage(state, data) {
    state.pageInfo.page = data
  },
  clearResult(state, data) {
    state.formResult = []
  }
}
