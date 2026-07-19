function checkResponse(res) {
    return res && 
            res.data.data && 
            res.data.error_code && 
            res.data.error_code === 'BSS-00000000';
}

export async function getAllInitialData(axios) {
    var dsDonViIDC = [];
    var dsIDC = [];
    var dsToaNha = [];
    var dsMatSan = [];
    var dsPhongZone = [];
    var error = "";

    var req1 = axios.get("/web-ecms/idc/thiet-bi-vat-ly/get-danh-muc-dv-idc"); 
    var req2 = axios.get("/web-ecms/danhmuc/idc/get");
    var req3 = axios.get("/web-ecms/danhmuc/toanha/get");
    var req4 = axios.get("/web-ecms/danh-muc-mat-san/get-all");
    var req5 = axios.get("/web-ecms/idc/phong-zone");
    await Promise.all([req1, req2, req3, req4, req5])
        .then(([res1, res2, res3, res4, res5]) => {
            console.log(res1, res2, res3, res4, res5)
            if (checkResponse(res1) && checkResponse(res2) && checkResponse(res3)
                && checkResponse(res4) && checkResponse(res5)) {
                dsDonViIDC = res1.data.data;
                dsIDC = res2.data.data;
                dsToaNha = res3.data.data;
                dsMatSan = res4.data.data;
                dsPhongZone = res5.data.data;
            } else {
                error = "Đã có lỗi xảy ra trong quá trình lấy các dữ liệu hệ thống về vị trí lắp đặt."
            }
        }).catch((errors) => {
            console.error(errors);
            error = "Đã có lỗi xảy ra trong quá trình lấy các dữ liệu hệ thống về vị trí lắp đặt.";
        })

    return {
        dsDonViIDC: dsDonViIDC,
        dsIDC: dsIDC,
        dsToaNha: dsToaNha,
        dsMatSan: dsMatSan,
        dsPhongZone: dsPhongZone,
        error: error
    }
}

export async function getViTriLapDatThietBi(axios, thietBiId) {
    var insLoc;
    var error = "";

    await axios.post("/web-ecms/idc/thiet-bi-vat-ly/get-vi-tri-thiet-bi", {
        thietBiId: thietBiId
    }).then((res) => {
        console.log(res)
        if (checkResponse(res)) {
            insLoc = res.data.data;
        } else {
            error = "Đã có lỗi xảy ra trong quá trình lấy dữ liệu vị trí lắp đặt của thiết bị.";
        }
    }).catch((err) => {
        console.error(err);
        error = "Đã có lỗi xảy ra trong quá trình lấy dữ liệu vị trí lắp đặt của thiết bị.";
    })

    return {
        data: insLoc,
        error: error
    }
}

export async function createUpdateViTriLapDat(axios, data) {
    var error = "";
    await axios.post("/web-ecms/idc/thiet-bi-vat-ly/update-vi-tri-thiet-bi", data)
    .then((res) => {
        console.log(res);
        if (checkResponse(res)) {
            if (res.data.message === "fail") {
                error = res.data.data.message;
            }
        }
    }).catch((err) => {
        console.error(err);
        error = "Đã có lỗi xảy ra trong quá trình thêm mới hoặc cập nhật vị trí lắp đặt của thiết bị!"
    })

    return {
        error: error
    }
}
