export default {
    getDanhSachNVThuCuoc: async (axios, data) => axios.get('web-thuno/api/thu-no/common/nhan-vien-thu-cuoc', { params: data }),
    getDanhSachNVThuCuocAM: async (axios, data) => axios.get('web-thuno/api/thu-no/quan-ly-dai-ly/ds-nvtc-am', { params: data }),
    getDSThueBao: async (axios, data) => axios.post('/web-thuno/api/thu-no/quan-ly-dai-ly/ds-thue-bao-xac-nhan-khong-gia-han', data),
    dieuBienDongThueBao: async (axios, data) => axios.put('/web-thuno/api/thu-no/quan-ly-dai-ly/dieu-bien-dong-tb-khong-gia-han', data),
    capNhatXacNhanKGH: async (axios, data) => axios.put('/web-thuno/api/thu-no/quan-ly-dai-ly/cap-nhat-xac-nhan-khong-gia-han', data),
}
