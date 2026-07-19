export default{
    CSS_DICHVU_VT: async (axios, data,header) => axios.get('/web-quantri/danhmuc-chung/CSS_DICHVU_VT', data, header),
     
    lay_db_theo_matb_1: async (axios, data,header) => axios.post('/web-tracuu/tracuu_dichvu/lay_db_theo_matb_1', data, header), 
    sp_lay_ds_sudung_dichvu: async (axios, data,header) => axios.post('/web-tracuu/tracuu_dichvu/sp_lay_ds_sudung_dichvu', data, header),  
}


    