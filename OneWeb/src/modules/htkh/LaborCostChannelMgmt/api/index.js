export default {

    //API MỚI PHẦN ĐỀ XUẤT
    dsDoiTuongBan: async (axios, data,header) => axios.post('/web-khdn/dxhopdong/dsDoiTuongBan', data,header),
    dsHangKenh: async (axios, data,header) => axios.post('/web-khdn/dxhopdong/dsHangKenh', data,header),
    dsNhanVienDeXuat: async (axios, data,header) => axios.post('/web-khdn/dxhopdong/dsNhanVienDeXuat', data,header),
    dsLoaiFile: async (axios, data,header) => axios.post('/web-khdn/dxhopdong/dsLoaiFile', data,header),
    dsDonViDeXuat: async (axios, data,header) => axios.post('/web-khdn/dxhopdong/dsDonViDeXuat', data,header),
    dsDeXuatHopDongKenh: async (axios, data,header) => axios.post('/web-khdn/giaophieu_kenh/dsDeXuatHopDongKenh', data,header),
    sinhMaPhieu: async (axios, data,header) => axios.post('/web-khdn/dxhopdong/sinhMaPhieu', data,header),
    sinhMaKenh: async (axios, data,header) => axios.post('/web-khdn/dxhopdong/sinhMaKenh', data,header),
    dsLoaiFile: async (axios, data,header) => axios.post('/web-khdn/dxhopdong/dsLoaiFile', data,header),
    dsFileHDK: async (axios, data,header) => axios.post('/web-khdn/dxhopdong/dsFileHDK', data,header),
    capNhatFileHDK: async (axios, data,header) => axios.post('/web-khdn/dxhopdong/capNhatFileHDK', data,header),
    themMoiDeXuatHopDong: async (axios, data,header) => axios.post('/web-khdn/dxhopdong/themMoiDeXuatHopDong', data,header),
    capNhatDeXuatHopDong: async (axios, data,header) => axios.post('/web-khdn/dxhopdong/capNhatDeXuatHopDong', data,header),
    xoaDeXuatHopDong: async (axios, data,header) => axios.post('/web-khdn/dxhopdong/xoaDeXuatHopDong', data,header),
    dsDonViGiao: async (axios, data,header) => axios.post('/web-khdn/giaophieu_kenh/dsDonViGiao', data,header),
    dsHuongGiao: async (axios, data,header) => axios.post('/web-khdn/giaophieu_kenh/dsHuongGiao', data,header),
    dsAllHuongGiao: async (axios, data,header) => axios.post('/web-khdn/giaophieu_kenh/dsAllHuongGiao', data,header),
    dsNhanVienNhan: async (axios, data,header) => axios.post('/web-khdn/giaophieu_kenh/dsNhanVienNhan ', data,header),
    dsDonViNhan: async (axios, data,header) => axios.post('/web-khdn/giaophieu_kenh/dsDonViNhan ', data,header),
    dsNhanVienGiao: async (axios, data,header) => axios.post('/web-khdn/giaophieu_kenh/dsNhanVienGiao ', data,header),
    themMoiPhieuGiao: async (axios, data,header) => axios.post('/web-khdn/giaophieu_kenh/themMoiPhieuGiao ', data,header),
    dsPhieuGiao: async (axios, data,header) => axios.post('/web-khdn/giaophieu_kenh/dsPhieuGiao ', data,header),
    dsDonViDuyet: async (axios, data,header) => axios.post('/web-khdn/giaophieu_kenh/dsDonViDuyet ', data,header),
    duyetPhieuGiao: async (axios, data,header) => axios.post('/web-khdn/giaophieu_kenh/duyetPhieuGiao ', data,header),
    tuChoiPhieuGiao: async (axios, data,header) => axios.post('/web-khdn/giaophieu_kenh/tuChoiPhieuGiao ', data,header),
    lichSugiaoPhieu: async (axios, data,header) => axios.post('/web-khdn/giaophieu_kenh/lichSuGiaoPhieu ', data,header),
 
 
    fn_import_thuebao_tinhcuoc_nhancong: async (axios, data,header) => axios.post('/web-tracuu/tinhcuocnhancong/fn_import_thuebao_tinhcuoc_nhancong', data,header),
    sp_chuyen_duyet_tinhcuoc_nhancong: async (axios, data,header) => axios.post('/web-tracuu/tinhcuocnhancong/sp_chuyen_duyet_tinhcuoc_nhancong', data,header)
    
}






   
