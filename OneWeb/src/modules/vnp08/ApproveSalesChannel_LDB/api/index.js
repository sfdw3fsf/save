export default {
    lay_thongtin_tinh: async (axios) => axios.get('/web-cabman/bando_tuyencot/lay_thongtin_tinh'),
    lay_ds_quan_huyen: async (axios, tinhId) => axios.get(`/web-cabman/bando_tuyencot/lay_ds_quan_huyen/${tinhId}`),
    lay_ds_phuong_xa: async (axios, huyenId) => axios.get(`/web-cabman/bando_tuyencot/lay_ds_phuong_xa/${huyenId}`),
    lay_ds_pho_ap_khu: async (axios, phuongId) => axios.get(`/web-cabman/bando_tuyencot/lay_ds_pho_ap_khu/${phuongId}`),
    lay_ds_thanhvienkenh: async (axios, data,header) => axios.post('/web-quantri/vnp08/dm_thanh_vien_kenh/sp_lay_ds_tv_kenh',data,header),
    lay_ds_hangkenh: async (axios, data,header) => axios.get('/web-quantri/vnp08/dm_hang_kenh/sp_lay_ds_hang_kenh', data,header),
    lay_ds_kenhban: async (axios, data,header) => axios.post('/web-quantri/vnp08/danhSachKenhBan', data,header),
    getKey_Kenhban: async (axios, data,header) => axios.post('/web-quantri/vnp08/sinhMaKenhBan', data,header),
    Themmoi_Kenhban: async (axios, data,header) => axios.post('/web-quantri/vnp08/themMoiKenhBan', data,header),
    Capnhat_Kenhban: async (axios, data,header) => axios.post('/web-quantri/vnp08/capNhatMoiKenhBan', data,header),
    getDsSanphamDagan: async (axios, data,header) => axios.post('/web-quantri/vnp08/danhSachSPDaGan', data,header),
    getDsSanphamChuagan: async (axios, data,header) => axios.post('/web-quantri/vnp08/danhSachSPChuaGan', data,header),
    ganSanpham: async (axios, data,header) => axios.post('/web-quantri/vnp08/ganSP', data,header),
    upload_file: async (axios, data,header) => axios.post('/web-quantri/upload', data,header),
    view_file: async (axios, data,header) => axios.post('/web-quantri/upload/link_view_file', data,header),
    lay_ds_loai_hop_dong: async (axios) => axios.get('/web-quantri/vnp08/dm_loai_hop_dong/sp_lay_ds_loai_hop_dong'),
    lay_ds_nhanvienQL: async (axios, data,header) => axios.post('/web-quantri/vnp08/dsNVQL', data,header),
    lay_ds_loaiGT: async (axios, data,header) => axios.post('/web-khdn/dxhopdong/dsLoaiGT', data,header),
    lay_dsFile_Kenhban: async (axios, data,header) => axios.post('/web-quantri/vnp08/dsFileKB', data,header),
    capNhatFileKB: async (axios, data,header) => axios.post('/web-quantri/vnp08/capNhatFileKB', data,header),
    
    lay_dsHTHD: async (axios, data,header) => axios.post('/web-quantri/vnp08/dsHTHD', data,header),
    lay_dsTTKenhBan: async (axios, data,header) => axios.post('/web-quantri/vnp08/dsTTKenhBan', data,header),

   
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
    lichSugiaoPhieu: async (axios, data,header) => axios.post('/web-khdn/giaophieu_kenh/lichSuGiaoPhieu ', data,header)
}






   
