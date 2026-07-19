import API from '../../api/DuyetNhuCauCapPhatDoiVTAPI'

export const actions = {
  async getThamSoMacDinh ({ commit, state }, data) {
    let response = await API.getThamSoMacDinh(this._vm.axios, data)
    return response.data.data
  },  

  async getDSQuyenND({ commit, state }, data) {
    let response = await API.getDSQuyenND(this._vm.axios, data)
    return response.data.data
  },
  async getDSTenLoaiKhoTD({ commit, state }, data) {
    let response = await API.getDSTenLoaiKhoTD(this._vm.axios, data)
    return response.data.data
  },
  async getDSMaKhoTD({ commit, state }, data) {
    let response = await API.getDSMaKhoTD(this._vm.axios, data)
    return response.data.data
  },
  async getDSKhoDonViDK({ commit, state }, data) {
    let response = await API.getDSKhoDonViDK(this._vm.axios, data)
    return response.data.data
  },

  async getDSVatTuDaDK({ commit, state }, data) {
    let response = await API.getDSVatTuDaDK(this._vm.axios, data)
    return response.data.data
  },
  async getDSVatTuMDDK({ commit, state }, data) {
    let response = await API.getDSVatTuMDDK(this._vm.axios, data)
    return response.data.data
  },
  async getDSMucDich({ commit, state }, data) {
    let response = await API.getDSMucDich(this._vm.axios, data)
    return response.data.data
  },
  async getDSVatTuDKTheoMD({ commit, state }, data) {
    let response = await API.getDSVatTuDKTheoMD(this._vm.axios, data)
    return response.data.data
  },

  async getDSKho({ commit, state }, data) {
    let response = await API.getDSKho(this._vm.axios, data)
    return response.data.data
  },
  async getDSKhoTheoDVVT({ commit, state }, data) {
    let response = await API.getDSKhoTheoDVVT(this._vm.axios, data)
    return response.data.data
  },
  async getDSKhoDatDen({ commit, state }, data) {
    let response = await API.getDSKhoDatDen(this._vm.axios, data)
    return response.data.data
  },
  async getDSDangKyCapPhatV3({ commit, state }, data) {
    let response = await API.getDSDangKyCapPhatV3(this._vm.axios, data)
    return response.data.data
  },
  async getDanhSachDaDK({ commit, state }, data) {
    let response = await API.getDanhSachDaDK(this._vm.axios, data)
    return response.data.data
  },

  async getDSChungTu({ commit, state }, data) {
    let response = await API.getDSChungTu(this._vm.axios, data)
    return response.data.data
  },
  async getDSVatTuTonKho({ commit, state }, data) {
    let response = await API.getDSVatTuTonKho(this._vm.axios, data)
    return response.data.data
  },
  async getDSCuonCap({ commit, state }, data) {
    let response = await API.getDSCuonCap(this._vm.axios, data)
    return response.data.data
  },
  async getFileCVDK({ commit, state }, data) {
    let response = await API.getFileCVDK(this._vm.axios, data)
    return response.data.data
  },

  async getTenMenu({ commit, state }, data) {
    let response = await API.getTenMenu(this._vm.axios, data)
    return response.data.data
  },
  
  async KiemTraNguoiDuyet({ commit, state }, data) {
    let response = await API.KiemTraNguoiDuyet(this._vm.axios, data)
    return response.data.data
  },
  async NhanPhieuDuyetDK({ commit, state }, data) {
    let response = await API.NhanPhieuDuyetDK(this._vm.axios, data)
    return response.data.data
  },

  async TraPhieuDKCapPhat({ commit, state }, data) {
    let response = await API.TraPhieuDKCapPhat(this._vm.axios, data)
    return response.data.data
  },
  async CapNhatVatTuDK({ commit, state }, data) {
    let response = await API.CapNhatVatTuDK(this._vm.axios, data)
    return response.data.data
  },
  async getSLVatTuDK({ commit, state }, data) {
    let response = await API.getSLVatTuDK(this._vm.axios, data)
    return response.data.data
  },
  async getSLDangKyTheoTT({ commit, state }, data) {
    let response = await API.getSLDangKyTheoTT(this._vm.axios, data)
    try {
      return response.data.data.toString() || []
    } catch (error) {
      throw error
    }
    return response
  },

  async KiemTraTrangThaiDangKy({ commit, state }, data) {
    let response = await API.KiemTraTrangThaiDangKy(this._vm.axios, data)
    try {
      return response.data.data.toString() || []
    } catch (error) {
      throw error
    }
    return response
  },

  async CapNhatVatTuDKTheoFile({ commit, state }, data) {
    let response = await API.CapNhatVatTuDKTheoFile(this._vm.axios, data)
    return response.data.data
  },
  async SinhNoiDungDK({ commit, state }, data) {
    let response = await API.SinhNoiDungDK(this._vm.axios, data)
    return response.data.data
  },
  async TaoChungTuDKCapPhatV2({ commit, state }, data) {
    let response = await API.TaoChungTuDKCapPhatV2(this._vm.axios, data)
    return response.data.data
  },
  async TaoChungTuDKCapPhatV3({ commit, state }, data) {
    let response = await API.TaoChungTuDKCapPhatV3(this._vm.axios, data)
    return response.data.data
  },

  async TaoVatTuDk({ commit, state }, data) {
    let response = await API.TaoVatTuDk(this._vm.axios, data)
    return response.data.data
  },
  async SLChungTuDKTheoVT({ commit, state }, data) {
    let response = await API.SLChungTuDKTheoVT(this._vm.axios, data)
    try {
      return response.data.data.toString() || []
    } catch (error) {
      throw error
    }
    return response
  },
  async XoaVatTuDK({ commit, state }, data) {
    let response = await API.XoaVatTuDK(this._vm.axios, data)
    return response.data.data
  },
  async getDSVatTuChungTu({ commit, state }, data) {
    let response = await API.getDSVatTuChungTu(this._vm.axios, data)
    return response.data.data
  },

  async KiemTraHanChungTu({ commit, state }, data) {
    let response = await API.KiemTraHanChungTu(this._vm.axios, data)
    return response.data.data
  },
  async ThongTinXacQuyenTrenKho({ commit, state }, data) {
    let response = await API.ThongTinXacQuyenTrenKho(this._vm.axios, data)
    return response.data.data
  },
  async ThongTinTrangThaiPhieu({ commit, state }, data) {
    let response = await API.ThongTinTrangThaiPhieu(this._vm.axios, data)
    return response.data.data
  },
  async KiemTraXoaChungTu({ commit, state }, data) {
    let response = await API.KiemTraXoaChungTu(this._vm.axios, data)
    return response.data.data
  },

  async KiemTraXoaPhieuHoanThanh({ commit, state }, data) {
    let response = await API.KiemTraXoaPhieuHoanThanh(this._vm.axios, data)
    return response.data.data
  },
  async KiemTraXoaDongBo({ commit, state }, data) {
    let response = await API.KiemTraXoaDongBo(this._vm.axios, data)
    return response.data.data
  },
  async XoaChungTuBaoHanh({ commit, state }, data) {
    let response = await API.XoaChungTuBaoHanh(this._vm.axios, data)
    return response.data.data
  },
  async XoaTatCaChiTietChungTu({ commit, state }, data) {
    let response = await API.XoaTatCaChiTietChungTu(this._vm.axios, data)
    return response.data.data
  },

  async XoaChungTu({ commit, state }, data) {
    let response = await API.XoaChungTu(this._vm.axios, data)
    return response.data.data
  },
  async SLPhieuDKThayDoi({ commit, state }, data) {
    let response = await API.SLPhieuDKThayDoi(this._vm.axios, data)
    return response.data.data
  },
  async CapNhatDKTheoFile({ commit, state }, data) {
    let response = await API.CapNhatDKTheoFile(this._vm.axios, data)
    return response.data.data
  },
  async getDSDatCho({ commit, state }, data) {
    let response = await API.getDSDatCho(this._vm.axios, data)
    return response.data.data
  },

  async getDSVatTuLoHangBiTrung({ commit, state }, data) {
    let response = await API.getDSVatTuLoHangBiTrung(this._vm.axios, data)
    return response.data.data
  },
  async getNgayNhanThanhToan({ commit, state }, data) {
    let response = await API.getNgayNhanThanhToan(this._vm.axios, data)
    return response.data.data
  },
  async XoaChiTietChungTu({ commit, state }, data) {
    let response = await API.XoaChiTietChungTu(this._vm.axios, data)
    return response.data.data
  },

  async getThongTinDSVatTu({ commit, state }, data) {
    let response = await API.getThongTinDSVatTu(this._vm.axios, data)
    return response.data.data
  },

  async xemBaoCao_TongHopTheoThang({ commit, state }, data) {
    let response = await API.xemBaoCao_TongHopTheoThang(this._vm.axios, data)
    return response
  },
  async xemBaoCao_DSDuyetDK({ commit, state }, data) {
    let response = await API.xemBaoCao_DSDuyetDK(this._vm.axios, data)
    return response
  },
  async XuatExcel_DSDuyetDK({ commit, state }, data) {
    let response = await API.XuatExcel_DSDuyetDK(this._vm.axios, data)
    return response    
  },
  async GIAHAN_DATCHO_CHUNGTU({ commit, state }, data) {
    let response = await API.GIAHAN_DATCHO_CHUNGTU(this._vm.axios, data)
    return response.data.data
  },
  async CapNhatDoiMucDich({ commit, state }, data) {
    let response = await API.CapNhatDoiMucDich(this._vm.axios, data)
    return response.data.data
  },

  async xemBaoCao_PXDDK({ commit, state }, data) {
    let response = await API.xemBaoCao_PXDDK(this._vm.axios, data)
    return response
  },

  async xemBaoCao_PDDK_NET({ commit, state }, data) {
    let response = await API.xemBaoCao_PDDK_NET(this._vm.axios, data)
    return response
  },
  
  async xoaChungTu3In1({ commit, state }, data) {
    let response = await API.xoaChungTu3In1(this._vm.axios, data)
    return response.data.data    
  },
  async getDSTonKhoDatCho({ commit, state }, data) {
    let response = await API.getDSTonKhoDatCho(this._vm.axios, data)
    return response.data.data    
  },
  async getLinkFile({ commit, state },data) {
    let response = await API.getLinkFile(this._vm.axios,data)
    return response.data 
  },
  async checkFileExist({ commit, state },data) {
    let response = await API.checkFileExist(this._vm.axios,data)
    return response.data 
  },
  async capNhatLyDoDuyet({ commit, state },data) {
    let response = await API.capNhatLyDoDuyet(this._vm.axios,data)
    return response.data.data 
  },
  async capNhatLyDoDuyet({ commit, state },data) {
    let response = await API.capNhatLyDoDuyet(this._vm.axios,data)
    return response.data.data 
  },
  async getDSTinh({ commit, state },data) {
    let response = await API.getDSTinh(this._vm.axios,data)
    return response.data.data 
  },
  async taoChungTuKHDN({ commit, state },data) {
    let response = await API.taoChungTuKHDN(this._vm.axios,data)
    return response.data.data 
  },
  async layDSDeNghiKHDN({ commit, state },data) {
    let response = await API.layDSDeNghiKHDN(this._vm.axios,data)
    return response.data.data 
  },
  async themSuaXoaDieuChuyen({ commit, state },data) {
    let response = await API.themSuaXoaDieuChuyen(this._vm.axios,data)
    return response.data.data 
  },
  async getDanhSachDaDKKHDN({ commit, state },data) {
    let response = await API.getDanhSachDaDKKHDN(this._vm.axios,data)
    return response.data.data 
  },
  async getDSVatTuDeNghi({ commit, state },data) {
    let response = await API.getDSVatTuDeNghi(this._vm.axios,data)
    return response.data.data 
  },
}