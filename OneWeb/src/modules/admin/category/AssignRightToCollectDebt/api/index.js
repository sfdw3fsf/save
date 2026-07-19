export default {
    NAP_DS_DONVI: (axios) => axios.get('/web-hopdong/ganquyen-gachno/sp_ds_donvi_gn'),
    kiemtra_dulieu: (axios, data) => axios.post('/web-thicong/thuebao/kiemtra_dulieu', data),

}