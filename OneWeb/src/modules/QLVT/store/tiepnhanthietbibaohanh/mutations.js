import moment from 'moment'
// các hàm xử lý các thuộc tính của đối tượng trong store
export const mutations = {
    setDanhSachDonVi (state, data) {
      state.danhsachDonVi = data
    },

    setDanhSachKieuBH (state, data) {
        state.danhSachKieuBH = data
    },

    setDSTinhTrang (state, data) {
        state.danhSachTinhTrang = data
    },

    setDSNhomThietBi (state, data) {
        state.danhSachNhomTB = data
    },
      
    setDSLoaiThietBi (state, data) {
        state.danhSachLoaiTB = data
    },

    setDSVatTu (state, data) {
        state.danhSachVatTu = data
    },
      
    setDSTiepNhanBH (state, data) {
        if (data){
          data.forEach(a=>a.NGAY_TN = moment(a.NGAY_YC,'DD-MM-YYYY hh:mm:ss').format('DD/MM/YYYY'))
          state.danhSachTiepNhanBH = data
          // state.TiepNhanBHPage.page = data.page
          // state.TiepNhanBHPage.maxSize = data.maxSize
          // state.TiepNhanBHPage.totalElement = data.totalElement
          // state.TiepNhanBHPage.totalPages = data.totalPages
        }
    },
    setTiepNhanBHPage(state, data){
      state.TiepNhanBHPage.page = data.page
      state.TiepNhanBHPage.maxSize = data.maxSize
    },

    setDSThamSoMacDinh (state, data) {
      state.danhSachThamSoMD = data
    },
    setObjSearch (state, data) {
      state.objSearch = data
    },
    clearData (state, data) {
      state.danhsachDonVi = [];
      state.danhSachKieuBH = [];
      state.danhSachTinhTrang = [];
      state.danhSachNhomTB = [];
      state.danhSachLoaiTB = [{'id':0,'text': "Lựa chọn loại thiết bị"}];
      state.danhSachVatTu = [];
      state.danhSachTiepNhanBH = [];
      state.danhSachThamSoMD = [];
      state.objSearch = {};
      state.thongTinBaoHanhTB = "Không có thông tin thuê bao";
      state.vvt_id = 0;
      state.ttBH = []
    //   state.thongTinTT = null
    },
    clearLoaiTB (state, data) {
      state.danhSachLoaiTB = [{'id':0,'text': "Lựa chọn loại thiết bị"}]
    },

    setThongTinBaoHanhTB (state, data) {
      state.thongTinBaoHanhTB = data ? data : "Không có thông tin thuê bao"
    },

    setMapID (state, data) {
      state.vvt_id = data
    },

    setTinhTrangBHTheoID (state, data) {
      state.ttBH = data
    },

    clearThongTinThueBao (state, data) {
      state.thongTinBaoHanhTB = ""
    },
    
  }
  