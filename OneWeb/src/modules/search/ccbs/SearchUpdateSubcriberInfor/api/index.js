export default {
    //Lấy danh mục
    getDanhmuc: (axios, data) => axios.post('/web-thicong/tracnghiem-atvsld/danhmuc', data),
    //Lấy danh sách đáp án theo câu hỏi
    getDsDapanbyCauhoi: (axios, data) => axios.post('/web-thicong/tracnghiem-atvsld/layds_dapan_theo_cauhoi', data),
   //Lấy danh sách câu hỏi theo mảng thi
    getDsCauhoibyMangthi: (axios, data) => axios.post('/web-thicong/tracnghiem-atvsld/layds_cauhoi_theo_mangthi', data),
    //Lấy danh sách câu hỏi theo chủ đề
    getDsCauhoibyChude: (axios, data) => axios.post('/web-thicong/tracnghiem-atvsld/layds_cauhoi_theo_chude', data),
    //Cập nhật câu hỏi
    Capnhat_Cauhoi: (axios, data) => axios.post('/web-thicong/tracnghiem-atvsld/capnhat_cauhoi', data),
    //Xóa câu hỏi
    Xoa_Cauhoi: (axios, data) => axios.post('/web-thicong/tracnghiem-atvsld/xoa_cauhoi', data),
    //Thêm mảng thi
    Them_mangthi: (axios, data) => axios.post('/web-thicong/tracnghiem-atvsld/fn_them_mangthi', data),
    //Thêm chủ đề
    Them_Chude: (axios, data) => axios.post('/web-thicong/tracnghiem-atvsld/fn_them_chude', data),
    //Xóa mảng thi
    Xoa_MangThi: (axios, data) => axios.post('/web-thicong/tracnghiem-atvsld/fn_xoa_mangthi', data),
    //Xóa chủ đề
    Xoa_Chude: (axios, data) => axios.post('/web-thicong/tracnghiem-atvsld/fn_xoa_chude', data),
    //Thêm đáp án
    Them_Dapan: (axios, data) => axios.post('/web-thicong/tracnghiem-atvsld/fn_them_dapan', data),
    //Thêm đáp án
    Sua_Dapan: (axios, data) => axios.post('/web-thicong/tracnghiem-atvsld/fn_sua_dapan', data),
    //Xóa đáp án
    Xoa_Dapan: (axios, data) => axios.post('/web-thicong/tracnghiem-atvsld/fn_xoa_dapan', data),
    
    
}
  