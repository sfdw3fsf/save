export default{
    Lay_TT_ND: (axios, data) => axios.post('/quantri/user/thongtin_nguoidung2', data),
    LAY_DS_THAMSO_BNM: (axios, data) => axios.get('/web-hopdong/phattrienthuebao/lay_ds_tonghop_thongtin_dangbang', data),
    lay_template_khaibao: (axios, data,tempId) => axios.get('/web-hopdong/phattrienthuebao/lay_thongtin_template_theo_id?tempId='+tempId, data),
    capnhatdulieu: (axios, data) => axios.post('/web-hopdong/phattrienthuebao/capnhat_template_bnm', data),

  }