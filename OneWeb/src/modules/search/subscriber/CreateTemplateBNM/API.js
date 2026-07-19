export default{
    // UR2.4.121_001
    lay_ds_tonghop_thongtin_dangbang: async (axios) => axios.get('/web-hopdong/phattrienthuebao/lay_ds_tonghop_thongtin_dangbang'),
    // UR2.4.121_002
    capnhat_template_bnm: async (axios, data) => axios.post('/web-hopdong/phattrienthuebao/capnhat_template_bnm', data),
    // UR2.4.121_003
    lay_thongtin_template_theo_id: async (axios, data) => axios.get(`/web-hopdong/phattrienthuebao/lay_thongtin_template_theo_id?tempId=${data.tempId}`),
}