export default{
    lay_danh_sach_tham_so: (axios,data) => axios.post("/web-thicong/capnhatthongtin/sp_lay_ds_thamso_md_edit",data),
    cap_nhat_tham_so:(axios,data) => axios.post("/web-thicong/capnhatthongtin/fn_update_thamso_md",data)
}