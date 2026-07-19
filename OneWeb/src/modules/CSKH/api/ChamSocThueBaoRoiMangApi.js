import axios from "axios";
export default {
    getDsNguyenNhanRoiMang: (axios) => axios.get('/web-cskh/api/danh-muc/nguyen-nhan-roi-mang'),
    getDsChinhSach: (axios)=> axios.get('/web-cskh/api/danh-muc/noi-dung'),
    // layDsRoiMangTheoNV: async (axios, data) => {
    //     return JSON.parse(`
    //     {
    //         "error": "200",
    //         "error_code": "BSS-00000000",
    //         "message": "Thành công",
    //         "message_detail": "",
    //         "request_id": "",
    //         "data": {
    //             "page": 0,
    //             "maxSize": 10,
    //             "totalElement": 0,
    //             "totalPages": 0,
    //             "sort": null,
    //             "propertiesSort": null,
    //             "data": [
    //                 {
    //                     "HDTB_ID": 2612190,
    //                     "THUEBAO_ID": 9262361,
    //                     "MA_TB": "ftth.pvt0120.dlk",
    //                     "TEN_TB": "Phạm Văn Thức",
    //                     "DIACHI_TB": "Thôn Quyết Tiến 1, Xã Ea Tíh, Huyện Ea Kar, Đắk Lắk",
    //                     "TEN_KIEULD": "Thanh lý Internet"
    //                 },
    //                 {
    //                     "HDTB_ID": 2612007,
    //                     "THUEBAO_ID": 9333222,
    //                     "MA_TB": "ftth.hien0820.dlk",
    //                     "TEN_TB": "Trương Thị Thu Hiền",
    //                     "DIACHI_TB": "34, Thôn Trung Hòa, Xã Ea Tíh, Huyện Ea Kar, Đắk Lắk",
    //                     "TEN_KIEULD": "Thanh lý Internet"
    //                 },
    //                 {
    //                     "HDTB_ID": 2609951,
    //                     "THUEBAO_ID": 6162875,
    //                     "MA_TB": "ftth.945888058.dlk",
    //                     "TEN_TB": "Nguyễn Chí Trung",
    //                     "DIACHI_TB": "Thôn 14, Căn Tin Trường Trần Nhân Tông, Xã Ea Đar, Ea Kar, Đăk Lắk, Việt Nam",
    //                     "TEN_KIEULD": "Thanh lý Internet"
    //                 },
    //                 {
    //                     "HDTB_ID": 1430415,
    //                     "THUEBAO_ID": 5347887,
    //                     "MA_TB": "ftth.ptnn.dlk",
    //                     "TEN_TB": "Phạm Thị Nhung Nhớ",
    //                     "DIACHI_TB": "61 - Thôn Quyết Thắng 2 - Xã Ea Tíh - Ea Kar - Đắk Lắk",
    //                     "TEN_KIEULD": "Thanh lý Internet"
    //                 }
    //             ]
    //         }
    //     }
    //     `)
    // },
    // layLichSuChamSoc: async (axios, data) => {
    //     return JSON.parse(`
    //     {
    //         "error": "200",
    //         "error_code": "BSS-00000000",
    //         "message": "Thành công",
    //         "message_detail": "",
    //         "request_id": "",
    //         "data": {
    //             "page": 0,
    //             "maxSize": 10,
    //             "totalElement": 0,
    //             "totalPages": 0,
    //             "sort": null,
    //             "propertiesSort": null,
    //             "data": [
    //                 {
    //                     "TEN_NV": "Phạm Thị Thơm",
    //                     "NGUYENNHAN": "Sóng wifi yếu",
    //                     "NOIDUNG": "Tối ưu hoá vị trí đặt thiết bị đầu cuối",
    //                     "NGAY_CN": "2019-10-22T16:44:41.000+00:00"
    //                 }
    //             ]
    //         }
    //     }
    //     `)
    // },
    layLichSuChamSoc: (axios, data) => axios.get('/web-cskh/api/cham-soc-tb-roi-mang/lich-su', {params: data}),
    layDsRoiMangTheoNV: (axios, data) => axios.get('/web-cskh/api/cham-soc-tb-roi-mang/danh-sach-theo-nhan-vien', {params: data}),
    capNhapThongTinChamSoc: (axios, data) => axios.post('/web-cskh/api/cham-soc-tb-roi-mang', data),
}