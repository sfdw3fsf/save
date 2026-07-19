export default function (apiCaller) {
    return {
        layDsDuAn: () => apiCaller.post('/web-cabman/duan/danhsach'),
        layDsTTVT: () => apiCaller.get('/web-cabman/bando_tuyencot/dsttvt_theonv'),
        layDsTaiSanTheoDuAn: (duAnId) => apiCaller.get('/web-cabman/taisan/danhSachTaiSanTheoDuAn', {duAnId: duAnId}),
        layDsTaiSanTheoMaTaiSan: (maTaiSan) => apiCaller.get('/web-cabman/taisan/danhSachTaiSanTheoMaTaiSan', {maTaiSan: maTaiSan}),
        themTaiSan: data => apiCaller.post('/web-cabman/taisan/themTaiSan', data),
        capNhatTaiSan: data => apiCaller.post('/web-cabman/taisan/capNhatTaiSan', data),
        xoaTaiSan: taiSanId => apiCaller.post('/web-cabman/taisan/xoaTaiSan', {taiSanId: taiSanId}),
        chuyenDuAn: data => apiCaller.post('/web-cabman/taisan/chuyen-duan', data),
    }
}