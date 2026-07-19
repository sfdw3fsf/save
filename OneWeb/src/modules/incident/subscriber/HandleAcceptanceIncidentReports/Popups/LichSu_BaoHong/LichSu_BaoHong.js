export default {
    Lay_lichsu_baohong_thuebao: (axios, data, header) => axios.get(`/web-ccdv/tien_trinh_bao_hong/Lay_lichsu_baohong_thuebao/${data.thuebao_id}`, data, header),

}