// các hàm xử lý các thuộc tính của đối tượng trong store
export const mutations = {
    setDanhMucVT_DVT (state, data) {
      state.DSVatTu_DVT = data
    },
    setDanhMucVT_KieuTB (state, data) {
      state.DSKieuTbi = data.filter(function (el) {
        return el.kieuTbiId == 1 || el.kieuTbiId == 3;
      })
    },
    setDSKhoGiao (state, data) {
      state.DSKhoGiao = data
    },
    setDSKhoNhan (state, data) {
      state.DSKhoNhan = data
    },
    setDSLoHang (state, data) {
      state.DSLoHang = data
    },
    setDSQuyetToanCT (state, data) {
      data.forEach(element => {
        element.NGAYLAP = element.NGAYLAP.replaceAll("-","/").slice(0,10)
      });
      state.DSCongTrinh = data
     
    },
    clearData (state, data) {
      state.DSVatTu_DVT = []
      state.DSKieuGiao = [];
      state.DSKhoGiao = [];
      state.DSKhoNhan = [];
      state.DSCongTrinh = [];
      state.DSLoHang = []
      state.DSVatTuCap = [],
      state.DSLichSuVatTu = []
      state.MucDichXuat = []
      state.MucDichNhapBH = []
      state.KTCapNhatPhieuVatTu = ""
      state.KTKiemTraHanChungTu = []
    },
    setDSPhieuVatTuQTCT (state, data) {
      data.forEach(element => {
        element.NGAY_CAP = element.NGAY_CAP.replaceAll("-","/").slice(0,10)
      });
      state.DSVatTuCap = data
    },
    clearDSLichSuVatTu(state, data) {
      state.DSLichSuVatTu = []
    },
    setDSLichSuVatTu (state, data) {
      data.forEach(element => {
        element.NGAY_CAP = element.NGAY_CAP.replaceAll("-","/").slice(0,10)
      });
      state.DSLichSuVatTu = data
    },
    setMucDichXuat (state, data) {
      state.MucDichXuat = data
    },
    setMucDichNhapBH (state, data) {
      state.MucDichNhapBH = data
    },
    setCapNhatPhieuVatTu (state, data) {
      state.KTCapNhatPhieuVatTu = data
    },
    setKiemTraHanChungTu (state, data) {
      state.KiemTraHanChungTu = data
    },
    setTaoPhieuChungTuV2 (state, data) {
      state.chungTuId = data
    },
    setQuyetToanVatTuCT (state, data) {
      state.KTQuyetToanVatTuCT = data
    },
    setKiemTraSerialThuHoi (state, data) {
      state.KTSerialThuHoi = data
    },
    setKiemTraKhoThuHoi (state, data) {
      state.KTKhoThuHoi = data
    },
    setThuHoiVatTuCongTrinh (state, data) {
      state.KTThuHoiVTCT = data
    },
    setXoaChungTuID (state, data) {
      state.KTXoaChungTu = data
    },
    setCapMaVatTuPVT (state, data) {
      state.KTCapMaVT = data
    },
    setCapNhatMaPVT (state, data) {
      state.KTCapNhapMaVT = data
    },
  }
    