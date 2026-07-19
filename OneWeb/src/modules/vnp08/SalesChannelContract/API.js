export default {
  dsDoiTuongBan: async (axios, data, header) => axios.post('/web-khdn/dxhopdong/dsDoiTuongBan', data, header),
  dsHangKenh: async (axios, data, header) => axios.post('/web-khdn/dxhopdong/dsHangKenh', data, header),
  dsLoaiFile: async (axios, data, header) => axios.post('/web-khdn/dxhopdong/dsLoaiFile', data, header),
  dsDonViDeXuat: async (axios, data, header) => axios.post('/web-khdn/dxhopdong/dsDonViDeXuat', data, header),
  dsNhanVienDeXuat: async (axios, data, header) => axios.post('/web-khdn/dxhopdong/dsNhanVienDeXuat', data, header),
  view_file: async (axios, data, header) => axios.post('/web-quantri/upload/link_view_file', data, header),
  dsFileHDK: async (axios, data, header) => axios.post('/web-khdn/dxhopdong/dsFileHDK', data, header),
  dsLoaiGT: async (axios, data, header) => axios.post('/web-khdn/dxhopdong/dsLoaiGT', data, header),

  // 1 Lấy danh sách thuộc thành viên kênh
  dsThanhVienKenh: async (axios, input) => axios.post(`/web-khdn/hopdongkenh/dsThanhVienKenh`, input),
  // 2 Lấy danh sách ngân hàng
  dsNganHang: async (axios, input) => axios.post('/web-khdn/hopdongkenh/dsNganHang', input),
  // 3 Lấy hình thức thanh toán
  dsHinhThucThanhToan: async (axios, input) => axios.post('/web-khdn/hopdongkenh/dsHinhThucThanhToan', input),
  // 4 Lấy loại hợp đồng
  dsLoaiHopDong: async (axios, input) => axios.post('/web-khdn/hopdongkenh/dsLoaiHopDong', input),
  // 5 Lấy tần suất hưởng
  dsTanXuatHuong: async (axios, input) => axios.post(`/web-khdn/hopdongkenh/dsTanXuatHuong`, input),
  // 6 Lấy dịch vụ 
  dsDichVuVT: async (axios, input) => axios.post(`/web-khdn/hopdongkenh/dsDichVuVT`, input),
  // 7 Lấy loại hình thuê bao
  dsLoaiHinhTB: async (axios, input) => axios.post(`/web-khdn/hopdongkenh/dsLoaiHinhTB`, input),
  // 8 Lấy nhân viên quản lý
  dsNhanVienQL: async (axios, input) => axios.post(`/web-khdn/hopdongkenh/dsNhanVienQL`, input),
  // 9 Lấy người ký
  dsNguoiKy: async (axios, input) => axios.post(`/web-khdn/hopdongkenh/dsNguoiKy`, input),
  // 10 Lấy trạng thái
  dsTrangThai: async (axios, input) => axios.post(`/web-khdn/hopdongkenh/dsTrangThai`, {
    doituongban: input.doituongban
  }),
  // 11 Danh sách lưới hợp đồng đã ký
  dsHopDongKenh: async (axios, input) => axios.post(`/web-khdn/hopdongkenh/dsHopDongKenh`, input),
  // 12 Dach sách mã phiếu
  dsMaPhieu: async (axios, input) => axios.post(`/web-khdn/hopdongkenh/dsMaPhieu`, input),
  // 13 Lấy công đoạn
  dsCongDoan: async (axios, input) => axios.post(`/web-khdn/hopdongkenh/dsCongDoan`, input),
  // 14 Danh sách cơ chế hợp đồng
  dsCoChe: async (axios, input) => axios.post(`/web-khdn/hopdongkenh/dsCoChe`, {
    hdkenh_id: input.hdkenh_id
  }),
  // Kiểm tra cơ chế
  kiemTraDauVaoCoChe: async (axios, input) => axios.post(`/web-khdn/hopdongkenh/kiemTraDauVaoCoChe`, {
    "tvkenh_id": input.tvkenh_id,
    "hdkenh_id": input.hdkenh_id,
    "noidung": input.noidung,
    "congdoan_id": input.congdoan_id,
    "dichvu_id": input.dichvu_id,
    "loaihinh_id": input.loaihinh_id,
    "tyle": input.tyle,
    "sotien": input.sotien,
    "loaithulao_id": input.loaithulao_id,
    "hdkcc_id": input.hdkcc_id,   
  }),
  // 15 Thêm mới cơ chế
  themMoiCoChe: async (axios, input) => axios.post(`/web-khdn/hopdongkenh/themMoiCoChe`, {
    "hdkenh_id": input.hdkenh_id,
    "noidung": input.noidung,
    "congdoan_id": input.congdoan_id,
    "dichvu_id": input.dichvu_id,
    "loaihinh_id": input.loaihinh_id,
    "tyle": input.tyle,
    "sotien": input.sotien,
    "tong_thulao": input.tong_thulao,
    "dt_ban_id": input.dt_ban_id,
    "loaithulao_id": input.loaithulao_id,
    "nvql_id": input.nvql_id,
    "hdk_id_update": input.hdk_id_update,
  }),
  // 16 Cập nhật cơ chế
  capNhatCoChe: async (axios, input) => axios.post(`/web-khdn/hopdongkenh/capNhatCoChe`, {
    "hdkenh_id": input.hdkenh_id,
    "noidung": input.noidung,
    "congdoan_id": input.congdoan_id,
    "dichvu_id": input.dichvu_id,
    "loaihinh_id": input.loaihinh_id,
    "tyle": input.tyle,
    "sotien": input.sotien,
    "tong_thulao": input.tong_thulao,
    "dt_ban_id": input.dt_ban_id,
    "loaithulao_id": input.loaithulao_id,
    "nvql_id": input.nvql_id,
    "hdk_id_update": input.hdk_id_update,
    "hdkcc_id": input.hdkcc_id,   
  }),
  // 17 Xóa cơ chế
  xoaCoChe: async (axios, input) => axios.post(`/web-khdn/hopdongkenh/xoaCoChe`, {
    "hdkcc_id": input.hdkcc_id,
  }),
  // 18 Lấy nhân viên chi trả
  dsNhanVienChiTra: async (axios, input) => axios.post(`/web-khdn/hopdongkenh/dsNhanVienChiTra`, input),
  // 19 Lấy đơn vị quản lý
  dsDonViQL: async (axios, input) => axios.post(`/web-khdn/hopdongkenh/dsDonViQL`, input),
  // 20 Cập nhật thông tin thanh toán
  capNhatThongTinTT: async (axios, input) => axios.post(`/web-khdn/hopdongkenh/capNhatThongTinTT`, {
    "hdkenh_id": input.hdkenh_id,
    "ma_tt": input.ma_tt,
    "ten_tt": input.ten_tt,
    "diachi_tt": input.diachi_tt,
    "stk": input.stk,
    "chutaikhoan": input.chutaikhoan,
    "nganhang_id": input.nganhang_id,
    "httt_id": input.httt_id,
    "donvi_qltt": input.donvi_qltt,
    "nv_chitra_id": input.nv_chitra_id,
    "vi_vnpay": input.vi_vnpay,
    "ghichu": input.ghichu,  
  }),
  // 21 Xóa hợp đồng
  xoaHopDong: async (axios, input) => axios.post(`/web-khdn/hopdongkenh/xoaHopDong`, {
    "hdkenh_id": input.hdkenh_id,
  }),
  // 22 Cập nhật/ thêm mới hợp đồng
  // - loai = 0 : khi Cập nhật thông tin hợp đồng ký với Kênh bán TH không qua đề xuất ký hợp đồng Kênh bán
  // - loai = 1 : khi Cập nhật thông tin hợp đồng ký với Kênh bán TH xuất phát từ đề xuất ký hợp đồng Kênh bán
  capNhatHopDong: async (axios, input) => axios.post(`/web-khdn/hopdongkenh/capNhatHopDong`, {
    "loai": input.loai,
    "ngay_yc": input.ngay_yc,  // DD/MM/YYYY
    "hangkenh_id": input.hangkenh_id,
    "ma_kenh": input.ma_kenh,
    "ten_kenh": input.ten_kenh,
    "diachi": input.diachi,
    "tinh_id": input.tinh_id,
    "quan_id": input.quan_id,
    "phuong_id": input.phuong_id,
    "pho_id": input.pho_id,
    "ap_id": input.ap_id,
    "khu_id": input.khu_id,
    "so_dt": input.so_dt,
    "fax": input.fax,
    "mst_cccd": input.mst_cccd,
    "nguoi_dd": input.nguoi_dd,
    "chucdanh": input.chucdanh,
    "gioitinh": input.gioitinh,
    "ngaysinh": input.ngaysinh,
    "dt_ban_id": input.dt_ban_id,
    "hdkenh_id": input.hdkenh_id,
    "ma_hd": input.ma_hd,
    "ngay_ky": input.ngay_ky,
    "nguoiky": input.nguoiky,
    "loaihd_id": input.loaihd_id,
    "htttk_id": input.htttk_id, //Tan suat huong
    "thuoc_tvkenh_id": input.thuoc_tvkenh_id,
    "nvql_id": input.nvql_id,
    "flag": input.flag, //trang thai thông tin thêm về kênh bán
    "ma_nd": input.ma_nd, //Username
    "loaithulao_id": input.loaithulao_id, 
    "tong_thulao": input.tong_thulao, 
    "ghichu": input.ghichu, 
    "stk": input.stk,     
    "chutaikhoan": input.chutaikhoan, 
    "nganhang_id": input.nganhang_id, 
    "httt_id": input.httt_id, 
    "donvi_qltt": input.donvi_qltt, 
    "nv_chitra_id": input.nv_chitra_id, 
    "mst_tt": input.mst_tt,
    "vi_vnpay": input.vi_vnpay,
    "ghichu_tt": input.ghichu_tt, 
    "loaigt_id": input.loaigt_id,
    "so_gt": input.so_gt,
    "noicap": input.noicap,
    "ngay_cap": input.ngay_cap,
    "coche_data": input.coche_data
  }),

  // POPUP
  // 15 Lưới cơ chế của kênh bán
  dsCoCheKenhBan: async (axios, input) => axios.post(`/web-khdn/kenhban/dsCoCheKenhBan`, {
    kenh_id: input.kenh_id,
    loctheo: input.loctheo, // loctheo: 0  -Tất cả; 1  - Hiện hữu
    tu_ngay: input.tu_ngay,
    den_ngay: input.den_ngay
  }), 
  // 23 Lấy loại thù lao
  dsLoaiThuLao: async (axios) => axios.post(`/web-khdn/hopdongkenh/dsLoaiThuLao`, {}), 
  // 16 Danh sách hợp đồng kênh
  dsHopDongKenhBan: async (axios, kenh_id) => axios.post(`/web-khdn/kenhban/dsHopDongKenhBan`, { kenh_id: kenh_id }), 
  // 17 Thêm mới hợp đồng kênh
  themMoiHopDongKenh: async (axios, input) => axios.post(`/web-khdn/kenhban/themMoiHopDong`, { 
    kenh_id: input.kenh_id,
    ma_hd: input.ma_hd,
    ngay_ky: input.ngay_ky,
    nguoiky: input.nguoiky,
    loaihd_id: input.loaihd_id,
    loaithulao_id: input.loaithulao_id,
    tong_thulao: input.tong_thulao,
    htttk_id: input.htttk_id,
    ghichu: input.ghichu,
    stk: input.stk,
    chutaikhoan: input.chutaikhoan,    
    nganhang_id: input.nganhang_id,
    httt_id: input.httt_id,   
    donvi_qltt: input.donvi_qltt,
    nv_chitra_id: input.nv_chitra_id,
    vi_vnpay: input.vi_vnpay,
    mst_tt: input.mst_tt,
    ghichu_tt: input.ghichu_tt,
    coche_data: input.coche_data
  }),
  // 18 Cập nhật hợp đồng kênh
  capNhatHopDongKenh: async (axios, input) => axios.post(`/web-khdn/kenhban/capNhatHopDong`, {
    hdkenh_id: input.hdkenh_id,
    kenh_id: input.kenh_id,
    ma_hd: input.ma_hd,
    ngay_ky: input.ngay_ky,
    nguoiky: input.nguoiky,
    loaihd_id: input.loaihd_id,
    loaithulao_id: input.loaithulao_id,
    tong_thulao: input.tong_thulao,
    htttk_id: input.htttk_id,
    ghichu: input.ghichu,
    stk: input.stk,
    chutaikhoan: input.chutaikhoan,
    nganhang_id: input.nganhang_id,
    httt_id: input.httt_id,
    donvi_qltt: input.donvi_qltt,
    nv_chitra_id: input.nv_chitra_id,
    vi_vnpay: input.vi_vnpay,
    mst_tt: input.mst_tt,
    ghichu_tt: input.ghichu_tt,
    coche_data: input.coche_data
  }),
}
