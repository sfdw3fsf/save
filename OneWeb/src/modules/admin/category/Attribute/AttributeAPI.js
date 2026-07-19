export default {
    lay_danhsach_kieu_dl: async (axios) => axios.post('/web-quantri/thuoctinh/ds_kieudl',{
        vthamso: 1
    }),
    lay_danhsach_thuoctinh: async (axios) => axios.post('/web-quantri/thuoctinh/lay_ds_thuoctinh',{
        vthamso: 1
    }),
    capnhat_thuoctinh: async (axios, data) => axios.post('/web-quantri/thuoctinh/capnhat_thuoctinh', data),
}