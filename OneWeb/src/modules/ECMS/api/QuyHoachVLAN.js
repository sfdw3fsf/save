export default {
    layDsVungQuyHoach: (axios) => axios.get('/web-ecms/danhmuc/vlan/vungquyhoach'),
    layDsDichVu: (axios) => axios.get('/web-ecms/danhmuc/vlan/dichvu'),
    layDsLoaiDichVu: (axios, data) => axios.post('/web-ecms/danhmuc/vlan/loaidichvu', data),
    layDsQuyHoachVLAN: (axios, data) => axios.post(`/web-ecms/danhmuc/vlan/ds-quyhoach?page=${data.header.page}&offset=${data.header.offset}`, data.body),
    capNhatQuyHoachVLAN: (axios, data) => axios.post(`/web-ecms/danhmuc/vlan/capnhat/${data.quyHoachId}`, data.body),
    themMoiQuyHoachVLAN: (axios, data) => axios.post('/web-ecms/danhmuc/vlan/them', data),
    xoaQuyHoachVLAN: (axios, data) => axios.post(`/web-ecms/danhmuc/vlan/xoa/${data.quyHoachId}`),
}