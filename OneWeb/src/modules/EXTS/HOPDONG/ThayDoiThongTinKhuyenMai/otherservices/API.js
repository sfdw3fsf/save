export default{
    lay_ds_dichvu_khac: async (axios, data,header) => axios.post('web-hopdong/thaydoithongtinkhuyenmai/LAY_DS_DICHVU_KHAC_DBID', data,header),
    getDanhBaNVTNTheoDiaDanh: async (axios, data) => axios.get('web-thuno/api/thu-no/tra-cuu-danh-ba/nhan-vien-thu-no/dia-danh', { params: data }),
}

