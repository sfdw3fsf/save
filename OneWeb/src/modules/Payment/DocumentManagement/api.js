
export default{
    Doc_File: (axios,data) => axios.post("web-tratruoc/thanhtoan_chungtu_tudong/fn_upload_file_chungtu",data),
    Upload_File: (axios,data) => axios.post("web-tratruoc/thanhtoan_chungtu_tudong/fn_capnhat_file_chungtu",data),
    Get_DS_Doc_File: (axios,data) => axios.get(`web-tratruoc/thanhtoan_chungtu_tudong/sp_lay_ds_chungtu_upload?upload_id=${data.upload_id}`),
    Get_DS_Upload_File: (axios,data) => axios.get(`web-tratruoc/thanhtoan_chungtu_tudong/sp_lay_ds_chungtu_capnhat?upload_id=${data.upload_id}`),

}