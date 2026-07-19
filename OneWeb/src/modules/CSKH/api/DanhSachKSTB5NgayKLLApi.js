export default {
    getDsPhongBanHang: (axios, data) => axios.post('/web-cskh/api/don-vi/phong', data),
    getDSKhuVucTheoPhongBH: (axios, data) => axios.post('/web-cskh/api/don-vi/phong', data),
    getDSTuyenThu: (axios, data) => axios.get('/web-cskh/api/tuyen-thu', {params: data}),
    getThamSoMacDinh: (axios, data) => axios.get('/web-cskh/api/danh-muc/tham-so-mac-dinh', {params: data}),
    getDsTBSuyHaoTheoNVKD: (axios, data) => axios.post('/web-cskh/api/khao-sat-thue-bao/suy-hao-cao/nvkd?pageNo=' + data.pageNo + "&pageSize=" + data.pageSize, data),
    getDsTBSuyHaoTheoDiaBan: (axios, data) => axios.post('/web-cskh/api/khao-sat-thue-bao/suy-hao-cao/dia-ban?pageNo=' + data.pageNo + "&pageSize=" + data.pageSize, data),

    //khaosat
    getDsLyDoKoTraLoi: (axios) => axios.get('/web-cskh/api/danh-muc/ly-do-ktl'),
    getLichSuGoiChamSoc: (axios, data) => axios.get('/web-cskh/api/khao-sat-thue-bao/lich-su-lan-goi-cs', {params: data}),
    getDsCauHoiTheoNV: (axios, data) => axios.get('/web-cskh/api/khao-sat-thue-bao/cau-hoi?nhanVienId=25&hinhThuc=0'),
    getDsCauHoiPhu: (axios, data) => axios.get('/web-cskh/api/khao-sat-thue-bao/tra-loi', {params: data}),
    layThongTinTBKoLuuLuong: (axios, data) => axios.get('/web-cskh/api/danh-muc/thue-bao-khong-luu-luong', {params: data}),
//     1: tìm kiếm theo hợp đồng hdkh_id
//   2: báo hỏng: baohong_id
//   3: tbtl_id 
//   4: hdtb_id
//    5: ct_suyhao_id
    layThongTinTBTraLoi: (axios, data) => axios.get('/web-cskh/api/xac-nhan-phan-hoi-chat-luong/ds-thue-bao-tra-loi', {params: data}),

    layDSLoaiKS: (axios, data) => axios.get('/web-thicong/capnhatthongtin/sp_lay_danhsach_loai_ks'),

}
