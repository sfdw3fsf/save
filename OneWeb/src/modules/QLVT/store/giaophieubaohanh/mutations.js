export const mutations = {
  // setObjSearch (state, data) {
  //   state.objSearch = data
  // }
  setDanhSachKho(state, data) {
    state.danhSachKho = data;
  },
  setDSHeThongQuyTrinh(state, data) {
    state.DSHeThongQuyTrinh = data;
  },
  setDSHuongGiao(state, data) {
    state.DSHuongGiao = data;
  },
  setTag(state, data) {
    state.Tag = data;
  },
  setQuyTrinh(state, data) {
    state.qt_id = data;
  },
  setHuongGiao(state, data) {
    state.hg_id = data;
  },
  setDSDonViTheoLoaiDichVu(state, data) {
    state.DSDonViNhan = data;
  },
  setDSGiaoPhieuBaoHanh(state, data) {
    state.DSGiaoPhieuBH = data;
  },
  setTaoChungTu(state, data) {
    state.chungTu = data;
  },
  setDSLuongThaoTac(state, data) {
    state.DSLuongThaoTac = data;
  },
  setDSThietBiBaoHanhTra(state, data) {
    state.DSThietBiBaoHanhTra = data;
  },
  setDSMucDichXuatVatTuKho(state, data) {
    state.DSMucDichXuatVatTuKho = data;
  },
  setDSKhoGiaoTheoToTruong(state, data) {
    state.DSKhoGiaoTheoToTruong = data;
  },
  setCapNhatGPBHHuongGiao(state, data) {
    state.CapNhatGPBHHuongGiao = data;
  },
  setCapNhatBaoHanhKho(state, data) {
    state.CapNhatBaoHanhKho = data;
  },
  setDSThietBiKhoGiao(state, data) {
    state.DSThietBiKhoGiao = data;
  },
  setCapNhatBaoHanhThietBi(state, data) {
    state.CapNhatBaoHanhThietBi = data;
  },
  setDSThietBiBaoHanh(state, data) {
    state.DSThietBiBaoHanh = data;
  },
  setDSKiemTraVatTuTrongBH(state, data) {
    state.DSKiemTraVatTuTrongBH = data;
  },
  setDSKiemTraVatTuTrongKho(state, data) {
    state.DSKiemTraVatTuTrongKho = data;
  },
  setDSKiemTraTaoChungTu(state, data) {
    state.DSKiemTraTaoChungTu = data;
  },
  setCapNhatGiaoPhieuBH(state, data) {
    state.CapNhatGiaoPhieuBH = data;
  },
  setDSKieuBaoHanh(state, data) {
    state.DSKieuBaoHanh = data;
  },
  setHoanThienBaoHanh(state, data) {
    state.statusHoanThienBaoHanh = data;
  },
  setDSLoaiDichVuHuongGiao(state, data) {
    state.DSLoaiDichVuHuongGiao = data;
  },
  setTaoGiaoPhieuBaoHanh(state, data) {
    state.statusTaoGiaoPhieuBaoHanh = data;
  },
  setCapNhatDonViBH(state, data) {
    state.statusCapNhatDonViBH = data;
  },
  clearData(state, data) {
    state.danhSachKho= [],
    state.DSHeThongQuyTrinh= [],
    state.DSHuongGiao= [],
    state.DSDonViNhan= [],
    state.DSGiaoPhieuBH= [],
    state.chungTu= "",
    state.DSLuongThaoTac= [],
    state.DSThietBiBaoHanhTra= [],
    state.DSMucDichXuatVatTuKho= [],
    state.DSKhoGiaoTheoToTruong= [],
    state.CapNhatGPBHHuongGiao= "",
    state.CapNhatBaoHanhKho= "",
    state.DSThietBiKhoGiao= "",
    state.CapNhatBaoHanhThietBi= "",
    state.DSThietBiBaoHanh= [],
    state.DSKiemTraVatTuTrongBH= [],
    state.DSKiemTraVatTuTrongKho= [],
    state.DSKiemTraTaoChungTu= [],
    state.CapNhatGiaoPhieuBH= "",
    state.DSKieuBaoHanh= [],
    state.statusHoanThienBaoHanh= "",
    state.DSLoaiDichVuHuongGiao= [],
    state.statusTaoGiaoPhieuBaoHanh= "",
    state.statusCapNhatDonViBH= ""
  },
  setMapTKQuyTrinh(state, data) {
    state.nhomQtId = data;
  },
};
