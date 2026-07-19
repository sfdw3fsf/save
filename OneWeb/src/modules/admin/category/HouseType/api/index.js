export default{    
    sp_nhom_loainha_lay_ds: (axios) => axios.get('/web-thicong/capnhatthongtin/sp_nhom_loainha_lay_ds'),    
    sp_loainha_lay_ds: (axios) => axios.get('/web-thicong/capnhatthongtin/sp_loainha_lay_ds'),    
    fn_loainha_thucthi: (axios, data) => axios.post(`/web-thicong/capnhatthongtin/fn_loainha_thucthi`,data),
    
}