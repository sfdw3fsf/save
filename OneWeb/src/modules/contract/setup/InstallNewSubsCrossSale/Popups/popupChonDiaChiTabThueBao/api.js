// URD2.1.033
export default {
    getDanhSachTinhBanCheo: (axios, data) =>  axios.post('web-bancheo/danhmuc/lay_ds_tinh', data),
    getDanhSachQuanBanCheo: (axios, data) =>  axios.post('web-bancheo/danhmuc/lay_ds_quan', data),
    getDanhSachPhuongBanCheo: (axios, data) =>  axios.post('web-bancheo/danhmuc/lay_ds_phuong', data),
    getDanhSachPhoApKhuBanCheo: (axios, data) =>  axios.post('web-bancheo/danhmuc/lay_ds_pho', data),

    getDanhSachDaciemBanCheo: (axios, data) =>  axios.post('web-bancheo/danhmuc/lay_ds_dacdiem', data),
}