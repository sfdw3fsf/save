export default{
    layDSDocFile: async(axios, data) => axios.post('/web-danhba/tothicong_theofile/upload_file_tram_hc', data),
    btnCapNhat: async(axios, data) => axios.post('/web-danhba/tothicong_theofile/capnhat_tram_hc_thuebao_v2', data),
    btnXuatFile: async(axios, data) => axios.post('/web-danhba/tothicong_theofile/upload_file_tram_hc', data)
}