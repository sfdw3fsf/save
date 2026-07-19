export default {
    getDSDichVuVT: (axios, data) => axios.get('/web-toanha/chitiet-hddtu/sp-lay-dichvuvt'),
    getDSChiTietHDDTU: (axios, data) => axios.post(`/web-toanha/chitiet-hddtu/sp-lay-ds-chitiet-hddtu`, data),
    getThongTinQLDA: (axios, data) => axios.get(`/web-toanha/chitiet-hddtu/fn-lay-tt-qlda/${data.hddtu_id}`),
    getDanhSachDT: (axios, data) => axios.get(`/web-toanha/chitiet-hddtu/sp-lay-ds-dt/${data.loai}`),
    getThongTinLoaiHinhTB: (axios, data) => axios.get(`/web-toanha/chitiet-hddtu/sp-lay-tt-loaihinh-tb/${data.dichvuvt_id}`),
    KiemTraDaTonTai: (axios, data) => axios.post('/web-toanha/chitiet-hddtu/sp-kiemtra-datontai',data),
    CapNhatChiTietHDDTU: (axios, data) => axios.post('/web-toanha/chitiet-hddtu/fn-capnhat-chitiet-hddtu',data),
}