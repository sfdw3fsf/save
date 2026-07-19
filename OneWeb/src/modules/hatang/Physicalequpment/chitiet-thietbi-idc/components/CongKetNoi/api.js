export default {
    add_or_update_thiet_bi_port: async (axios,data) => axios.post('/web-ecms/idc/thiet-bi-vat-ly/add-or-update-thiet-bi-port',data),
    get_port_theo_loai_cong_id: async (axios,data) => axios.post('/web-ecms/idc/thiet-bi-vat-ly/get-port-theo-loai-cong-id',data),
    delete_thiet_bi_port: async (axios,data) => axios.post('/web-ecms/idc/thiet-bi-vat-ly/delete-thiet-bi-port',data),
}
