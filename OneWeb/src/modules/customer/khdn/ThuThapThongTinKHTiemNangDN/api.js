export default {
    getListCustomer: (axios, data) => axios.post(`web-pthvkh/pthvkh/taomoi_khdn_thucong`, data),
    getNganhNghe: (axios, p_khoikhachhang) => axios.post('web-pthvkh/pthvkh/danhmuc_nganhnghe', {p_khoikhachhang}),

    getLinhVuc: (axios, p_nganhnghe) => axios.post('/web-pthvkh/pthvkh/danhmuc_linhvuc', {p_nganhnghe}),
    getLinhVucHoatDong: (axios) => axios.post('/web-pthvkh/pthvkh/danhmuc_linhvuc', {}),
    getKhuVucHoatDong : (axios) => axios.post('/web-pthvkh/pthvkh/danhmuc_khuvuc_hoatdong', {}),
    searchCusByTaxCode: (axios, taxId) => axios.post('/web-pthvkh/pthvkh/tracuu_masothue_khachhang', {
        "page": 0,
        "size": 1,
        "taxId": taxId
    }),

    createNewCustomer: (axios, createParam) => axios.post('/web-pthvkh/pthvkh/taomoi_khdn_thucong', createParam),
    updateCustomer: (axios, data) => axios.post('/web-pthvkh/pthvkh/capnhat_khdn_thucong', data),

}

/**
 * create Param input example:
 * {
    "enterpriseInfo": {
        "customerName": "Tên khách hàng",
        "taxId": "1403",
        "customerDepartment": 123,
        "activity": 456,
        "career": 789,
        "scale": 10,
        "revenue": 1000000,
        "workingArea": 500,
        "corporateDate": "Ngày thành lập công ty",
        "phoneNumber": "0963846561",
        "socialNetworkInfo": "Thông tin mạng xã hội"
    },
    "addressInfo": {
        "cityId": 123456,
        "cityName": "Tên thành phố",
        "districtId": 789012,
        "districtName": "Tên quận/huyện",
        "wardId": 345678,
        "wardName": "Tên phường/xã",
        "homeNumber": "Số nhà",
        "areaId": 901234,
        "areaName": "Tên khu vực",
        "streetId": 567890,
        "streetName": "Tên đường/phố",
        "hamletId": 1234567,
        "hamletName": "Tên ấp/thôn",
        "characteristic": "Đặc điểm",
        "additionalAddress": "Địa chỉ bổ sung",
        "acreage": "Diện tích (m2)",
        "infrastructure": "Cơ sở hạ tầng"
    },
    "relevantInfo": [
        {
            "relevantName": "Tên liên quan",
            "department": "Bộ phận",
            "dateOfBirth": "Ngày sinh",
            "gender": "Giới tính",
            "phoneNumber": "0963846561",
            "email": "Email"
        }
    ],
    "officerDecideInfo": [
        {
            "officerName": "Tên sĩ quan",
            "dateOfBirth": "Ngày sinh",
            "gender": "Giới tính",
            "phoneNumber": "0963846561",
            "email": "Email"
        }
    ],
    "createTime": null,
    "note": "Sobin"
}
}

 */

/**
 * update Param input example:
 * {
    "enterpriseInfo": {
        "taxIdentification": "1407",
    },
        "relevantInfo": [
        {
            "relevantName": "Nguyễn Liên Hoa",
            "department": "Kế Toán",
            "dateOfBirth": "1993/08/09",
            "gender": "Nữ",
            "phoneNumber": "0963846561",
            "email": "hoanl@gmail.com"
        }
    ],
    "officerDecideInfo": [
        {
            "officerName": "Hoàng Tuấn Kiệt",
            "dateOfBirth": "1991/05/05",
            "gender": "Nam",
            "phoneNumber": "0963846561",
            "email": "Email"
        }
    ],
   }

 */