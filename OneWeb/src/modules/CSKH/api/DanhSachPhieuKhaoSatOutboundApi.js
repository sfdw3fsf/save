import axios from "axios";

export default {
    getdsLoaiNhomKhaoSat: (axios) => axios.get('/web-cskh/api/danh-muc/loai-nhom-khao-sat'),
    getdsTrangThaiLuotGiaoKhaoSat: (axios) => axios.get('/web-cskh/api/danh-muc/tt-luot-giao-khao-sat'),
    getdsTrangThaiPhieu: (axios) => axios.get('/web-cskh/api/danh-muc/trang-thai-phieu'),
    getThongTinKhachHangHopDong: (axios, data) => axios.get('/web-cskh/api/phieu-khao-sat/thong-tin-hop-dong-kh', {params: data}),
    getdsThongTinCSKHThueBao: (axios, data) => axios.get('/web-cskh/api/phieu-khao-sat/cskh-thue-bao', {params: data}),
    layThongTinCanhBao: (axios) => axios.get('/web-cskh/api/phieu-khao-sat/lay-thong-tin-canh-bao'),
    
    getdsLuotGiaoNhanKhaoSat: (axios, pageNo, pageSize, data) => axios.post('/web-cskh/api/phieu-khao-sat/luot-giao-nhan?pageNo=' + pageNo + '&pageSize=' + pageSize, data),
    getdsPhieuDaGiaoKhaoSat: (axios, pageNo, pageSize, data) => axios.post('/web-cskh/api/phieu-khao-sat/phieu-da-giao?pageNo=' + pageNo + '&pageSize=' + pageSize, data),
    getdsNhanVienDiaBan: (axios, pageNo, pageSize, data) => axios.post('/web-cskh/api/phieu-khao-sat/nvql-dia-ban?pageNo=' + pageNo + '&pageSize=' + pageSize, data),
    getThamSoMacDinh: (axios, data) => axios.get('/web-cskh/api/danh-muc/tham-so-mac-dinh', {params: data}),
    layPhieuKS: (axios, data) => axios.get('/web-cskh/api/phieu-khao-sat/dang-ky', {params: data}),
    boQuaPhieuKS: (axios, data) => axios.get('/web-cskh/api/phieu-khao-sat/huy-dang-ky', {params: data}),
}