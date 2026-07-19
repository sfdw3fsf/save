
export default {
     
    Get_Project: (axios, data) => axios.post(`/tichhop/jira/Get_Project`, data),
    Get_Issue_Info: async (axios, data,header) => axios.post('/tichhop/jira/get_Issue_Info', data, header),
    
    upload_file: async (axios, data,header) => axios.post('/web-quantri/upload', data,header),
    link_view_file: async (axios, data,header) => axios.post('/web-quantri/upload/link_view_file', data,header),
    upload_img_post: async (axios, data,header) => axios.post('/ccbs/pttb/upload_img_post', data,header),
    fn_tt_hd_thuebao: async (axios, data,header) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_hd_thuebao', data,header), //{ type =13, param = ma_tb }
    download_img_post: async (axios, data,header) => axios.post('/ccbs/pttb/download_img_post', data,header),
     
}