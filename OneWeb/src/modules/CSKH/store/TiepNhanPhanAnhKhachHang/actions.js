import API from '@/modules/CSKH/api/TiepNhanPhanAnhKhachHangApi'

export const actions = {
  async getDsNganhNghe({ commit, state }, data) {
    try {
      let response = await API.getDsNganhNghe(this._vm.axios)
      let ds = response.data.data || []
      let nganhNghe = []
      nganhNghe.push({id: 999, text: 'Khác'})
      ds.forEach(element => {
        nganhNghe.push({
          id: element.NGANHNGHE_ID,
          text: element.TENNGANHNGHE
        })
      });
      commit("setDsNganhNghe", nganhNghe);
      return response;
    } catch (error) {
      commit("setDsNganhNghe", []);
      return error.response.data
    }
  },
  async getDsLoaiKH({ commit, state }, data) {
    try {
      let response = await API.getDsLoaiKH(this._vm.axios)
      let ds = response.data.data || []
      let nganhNghe = []
      ds.forEach(element => {
        nganhNghe.push({
          id: element.LOAIKH_ID,
          text: element.TEN_LOAIKH
        })
      });
      commit("setDsLoaiKH", nganhNghe);
      return response;
    } catch (error) {
      commit("setDsLoaiKH", []);
      return error.response.data
    }
  },
  async getDsLoaiKHLon({ commit, state }, data) {
    try {
      let response = await API.getDsLoaiKHLon(this._vm.axios, { nhomKhl: 0 })
      let ds = response.data.data || []
      let nganhNghe = []
      ds.forEach(element => {
        nganhNghe.push({
          id: element.KHLON_ID,
          text: element.TEN_LKHL
        })
      });
      commit("setDsLoaiKHLon", nganhNghe);
      return response;
    } catch (error) {
      commit("setDsLoaiKHLon", []);
      return error.response.data
    }
  },
  async getDsHinhThucKN({ commit, state }, data) {
    try {
      let response = await API.getDsHinhThucKN(this._vm.axios)
      let ds = response.data.data || []
      let nganhNghe = []
      ds.forEach(element => {
        nganhNghe.push({
          id: element.HTKN_ID,
          text: element.HINHTHUC_KN
        })
      });
      commit("setDsHinhThucKN", nganhNghe);
      return response;
    } catch (error) {
      commit("setDsHinhThucKN", []);
      return error.response.data
    }
  },
  async getDsNoiDungPA({ commit, state }, data) {
    try {
      let response = await API.getDsNoiDungPA(this._vm.axios)
      let ds = response.data.data || []
      let nganhNghe = []
      ds.forEach(element => {
        nganhNghe.push({
          id: element.CHUDEKN_ID,
          text: element.TENCHUDE
        })
      });
      commit("setDsNoiDungPA", nganhNghe);
      return response;
    } catch (error) {
      commit("setDsNoiDungPA", []);
      return error.response.data
    }
  },
  async getDsPAMoiTiepNhanTheoNV({ commit, state }, data) {
    try {
      let response = await API.getDsPAMoiTiepNhanTheoNV(this._vm.axios, data)
      //@fakedata
      let test = false
      if (test) {
        response = {
        data: {
          "error": "200",
          "error_code": "BSS-00000000",
          "message": "Thành công",
          "message_detail": "",
          "request_id": "",
          "data": {
            "page": 0,
            "maxSize": 10,
            "totalElement": 7,
            "totalPages": 1,
            "sort": null,
            "propertiesSort": null,
            "data": [
              {
                "PHANANH_ID": 69,
                "KHACHHANG_ID": 2013860,
                "MA_KH": "HCM000110572",
                "NGAY_LH": "2015-05-21T11:21:53.000+00:00",
                "NGUOI_LH": "NGA",
                "NOI_DUNG": "ĐL - 13. Ðề nghị chêm KC",
                "DIENTHOAI_LH": "35036577",
                "CHUDEKN_ID": 33,
                "ND_PHANANH": "KH ĐÃ THANH TOÁN HẾT CƯỚC VÀ YC CHÊM KC",
                "HENGQ_TU": null,
                "HENGQ_DEN": null,
                "GHICHU": "KH ĐÃ THANH TOÁN HẾT CƯỚC VÀ YC CHÊM KC",
                "NHANVIEN_GQ_ID": null,
                "NOIDUNG_GQ": null,
                "HTKN_ID": 2,
                "NHANVIEN_ID": 40173,
                "TRANGTHAI_PA": "Mới tiếp nhận",
                "TEN_NV": "Lý Thị Liên Phượng"
              },
              {
                "PHANANH_ID": 70,
                "KHACHHANG_ID": 1251707,
                "MA_KH": "HCM002766477",
                "NGAY_LH": "2015-05-21T11:24:51.000+00:00",
                "NGUOI_LH": "DIEN",
                "NOI_DUNG": "ĐL - 13. Ðề nghị chêm KC",
                "DIENTHOAI_LH": "35392005",
                "CHUDEKN_ID": 33,
                "ND_PHANANH": "KH ĐÃ THANH TOÁN HẾT CƯỚC VÀ YC CHÊM KC",
                "HENGQ_TU": null,
                "HENGQ_DEN": null,
                "GHICHU": "KH ĐÃ THANH TOÁN HẾT CƯỚC VÀ YC CHÊM KC",
                "NHANVIEN_GQ_ID": null,
                "NOIDUNG_GQ": null,
                "HTKN_ID": 2,
                "NHANVIEN_ID": 40173,
                "TRANGTHAI_PA": "Mới tiếp nhận",
                "TEN_NV": "Lý Thị Liên Phượng"
              },
              {
                "PHANANH_ID": 71,
                "KHACHHANG_ID": 1505090,
                "MA_KH": "HCM001949633",
                "NGAY_LH": "2015-05-21T11:25:37.000+00:00",
                "NGUOI_LH": "BẢY",
                "NOI_DUNG": "ĐL - 13. Ðề nghị chêm KC",
                "DIENTHOAI_LH": "39433940",
                "CHUDEKN_ID": 33,
                "ND_PHANANH": "KH ĐÃ THANH TOÁN HẾT CƯỚC VÀ YC CHÊM KC",
                "HENGQ_TU": null,
                "HENGQ_DEN": null,
                "GHICHU": "KH ĐÃ THANH TOÁN HẾT CƯỚC VÀ YC CHÊM KC",
                "NHANVIEN_GQ_ID": null,
                "NOIDUNG_GQ": null,
                "HTKN_ID": 2,
                "NHANVIEN_ID": 40173,
                "TRANGTHAI_PA": "Mới tiếp nhận",
                "TEN_NV": "Lý Thị Liên Phượng"
              },
              {
                "PHANANH_ID": 72,
                "KHACHHANG_ID": 1132578,
                "MA_KH": "HCM001285191",
                "NGAY_LH": "2015-05-21T15:26:50.000+00:00",
                "NGUOI_LH": "PHUNG",
                "NOI_DUNG": "ĐL - 13. Ðề nghị chêm KC",
                "DIENTHOAI_LH": "39431063",
                "CHUDEKN_ID": 33,
                "ND_PHANANH": "KH ĐÃ THANH TOÁN HẾT CƯỚC VÀ YC CHÊM KC",
                "HENGQ_TU": null,
                "HENGQ_DEN": null,
                "GHICHU": "KH ĐÃ THANH TOÁN HẾT CƯỚC VÀ YC CHÊM KC",
                "NHANVIEN_GQ_ID": null,
                "NOIDUNG_GQ": null,
                "HTKN_ID": 2,
                "NHANVIEN_ID": 40173,
                "TRANGTHAI_PA": "Mới tiếp nhận",
                "TEN_NV": "Lý Thị Liên Phượng"
              },
              {
                "PHANANH_ID": 76,
                "KHACHHANG_ID": 401766,
                "MA_KH": "HCM001338241",
                "NGAY_LH": "2015-05-29T15:43:36.000+00:00",
                "NGUOI_LH": "C.NGỌC",
                "NOI_DUNG": "ĐL - 14. Yêu cầu mở chêm",
                "DIENTHOAI_LH": "39405686",
                "CHUDEKN_ID": 34,
                "ND_PHANANH": "KH YC MỞ CHÊM KC mega-ntkimngoc368",
                "HENGQ_TU": null,
                "HENGQ_DEN": null,
                "GHICHU": "KH YC MỞ CHÊM KC mega-ntkimngoc368",
                "NHANVIEN_GQ_ID": null,
                "NOIDUNG_GQ": null,
                "HTKN_ID": 2,
                "NHANVIEN_ID": 40173,
                "TRANGTHAI_PA": "Mới tiếp nhận",
                "TEN_NV": "Lý Thị Liên Phượng"
              },
              {
                "PHANANH_ID": 80,
                "KHACHHANG_ID": 2637331,
                "MA_KH": "HCM000738185",
                "NGAY_LH": "2015-06-16T16:20:21.000+00:00",
                "NGUOI_LH": "C.HOA",
                "NOI_DUNG": "ĐL - 14. Yêu cầu mở chêm",
                "DIENTHOAI_LH": "0916200066",
                "CHUDEKN_ID": 34,
                "ND_PHANANH": "KH ĐÃ THANH TOÁN HẾT CƯỚC VÀ YC MỞ CHÊM KC.",
                "HENGQ_TU": null,
                "HENGQ_DEN": null,
                "GHICHU": "KH ĐÃ THANH TOÁN HẾT CƯỚC VÀ YC MỞ CHÊM KC.",
                "NHANVIEN_GQ_ID": null,
                "NOIDUNG_GQ": null,
                "HTKN_ID": 2,
                "NHANVIEN_ID": 40173,
                "TRANGTHAI_PA": "Mới tiếp nhận",
                "TEN_NV": "Lý Thị Liên Phượng"
              },
              {
                "PHANANH_ID": 81,
                "KHACHHANG_ID": 2642751,
                "MA_KH": "HPG-001017355",
                "NGAY_LH": "2015-06-16T16:23:13.000+00:00",
                "NGUOI_LH": "C.HOA",
                "NOI_DUNG": "ĐL - 14. Yêu cầu mở chêm",
                "DIENTHOAI_LH": "0916200066",
                "CHUDEKN_ID": 34,
                "ND_PHANANH": "KH ĐÃ THANH TOÁN HẾT CƯỚC VÀ YC MỞ CHÊM KC.",
                "HENGQ_TU": null,
                "HENGQ_DEN": null,
                "GHICHU": "KH ĐÃ THANH TOÁN HẾT CƯỚC VÀ YC MỞ CHÊM KC.",
                "NHANVIEN_GQ_ID": null,
                "NOIDUNG_GQ": null,
                "HTKN_ID": 2,
                "NHANVIEN_ID": 40173,
                "TRANGTHAI_PA": "Mới tiếp nhận",
                "TEN_NV": "Lý Thị Liên Phượng"
              }
            ]
          }
        }
      }
      }
      
      commit("setDsPAMoiTiepNhanTheoNV", response.data.data.data || []);
      return response;
    } catch (error) {
      commit("setDsPAMoiTiepNhanTheoNV", []);
      return error.response.data
    }
  },
  clearDsPAMoiTiepNhanTheoNV({ commit, state }, data) {
    commit("setDsPAMoiTiepNhanTheoNV", []);
  }
}