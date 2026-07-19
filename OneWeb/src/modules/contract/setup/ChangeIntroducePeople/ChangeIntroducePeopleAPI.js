export default {
    lay_ds_hd_tuvan: async (axios, ma_gd, hdkh_id, kieu) => axios.post('/web-quantri/nguoi-gioithieu/lay_ds_hd_tuvan',{
        vma_gd: ma_gd,
        vhdkh_id: hdkh_id,
        vkieu: kieu
    }),
    capnhat_thongtin_gt_v2: async (axios, data) => axios.post('/web-quantri/nguoi-gioithieu/capnhat_thongtin_gt_v2',data),
}