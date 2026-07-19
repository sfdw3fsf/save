export default {
    lay_dm_thue_ip: async (axios, data) => axios.post('/web-quantri/quanly-thueip/lay_dm_thue_ip', data),
    lay_tt_thue_ip: async (axios, data) => axios.post('/web-quantri/quanly-thueip/lay_tt_thue_ip', data),
    capnhat_thue_ip: async (axios, data) => axios.post('/web-quantri/quanly-thueip/capnhat_thue_ip', data),
 
}