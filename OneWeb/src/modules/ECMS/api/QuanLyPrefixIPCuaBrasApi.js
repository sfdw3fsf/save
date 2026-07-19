export default function (apiCaller) {
    return {
        layDsPrefixIp: () => apiCaller.get('/web-ecms/prefix-ip-bras/lay-ds-prefix-ip'),
        layDsLoaiTbDaGan: (data) => apiCaller.get('/web-ecms/prefix-ip-bras/lay-ds-loaitb-dagan', data),
        layDsLoaiTbChuaGan: (data) => apiCaller.post('/web-ecms/prefix-ip-bras/lay-ds-loaitb-chuagan', data),
        layDsIp: (data) => apiCaller.get('/web-ecms/prefix-ip-bras/lay-ds-ip', data),
        themMoi: (data) => apiCaller.post('/web-ecms/prefix-ip-bras/themmoi', data),
        capNhat: (data) => apiCaller.post('/web-ecms/prefix-ip-bras/capnhat', data),
        kiemTraPrefix: (data) => apiCaller.post(`/web-ecms/prefix-ip-bras/kiemtra-prefix-ip?prefix=${data}`),
        xoaPrefix: (data) => apiCaller.post(`/web-ecms/prefix-ip-bras/xoa?prefix=${data}`),
        sinhIp: (data) => apiCaller.post('/web-ecms/prefix-ip-bras/sinh-ip', data),
        layDsDichVuVt: () => apiCaller.get('/web-ecms/prefix-ip-bras/dm-dichvu-vt'),
        layDsLoaiIp: () => apiCaller.get('/web-ecms/prefix-ip-bras/dm-loai-ip'),
        layDsBras: () => apiCaller.get('/web-ecms/danhmuc/quanlybras/danhsach'),
    }
}