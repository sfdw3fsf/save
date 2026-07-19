export default {
    getADMIN_LOAI_CT: async (axios) => axios.get('/web-quantri/danhmuc-chung/ADMIN_LOAI_CT'),
    getNhom_CN_LCT: async (axios) => axios.get('/web-quantri/danhmuc-chung/ADMIN_NHOM_CN'),
    getNhom_Menu_LCT: async (axios) => axios.get('/web-quantri/danhmuc-chung/ADMIN_NHOM_MENU'),
    getMenuCha_NMN: async (axios, data) => axios.post('/web-quantri/menu/sp_lay_nhom_menu', data),
    getChucNang_NCN: async (axios, data) => axios.post('/web-quantri/menu/sp_lay_ds_nhom_chucnang', data),
    getMenu: async (axios) => axios.get('/web-quantri/danhmuc-chung/ADMIN_MENU'),
    getChucNang: async (axios) => axios.get('/web-quantri/danhmuc-chung/ADMIN_CHUCNANG'),
    CapNhat: async (axios, data) => axios.post('/web-quantri/menu/update_menu', data),
    XoaMenu: async (axios, data) => axios.post('/web-quantri/menu/xoa_menu', data),
}