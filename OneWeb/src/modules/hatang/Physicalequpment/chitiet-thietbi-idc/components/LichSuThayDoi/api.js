function checkResponse(res) {
    return res && 
            res.data.data && 
            res.data.data.list &&
            res.data.error_code && 
            res.data.error_code === 'BSS-00000000';
}

export async function getLsThayDoi(axios, thietBiId) {
    var data = [];
    var error = "";
    await axios.get(`/web-ecms/idc/thiet-bi-vat-ly/lich-su-tac-dong/page?thietBiId=${thietBiId}&pageSize=100&pageNumber=1&khoaDuLieuList=1,2,3,4,181,,,&tenbang=THIETBI,THIETBI_PORT`)
    .then((res) => {
        console.log(res);
        if (checkResponse(res)) {
            data = res.data.data.list;
        } else {
            error = "Đã có lỗi xảy ra trong quá trình lấy dữ liệu lịch sử thay đổi.";
        }
    }).catch((err) => {
        console.error(err);
        error = "Đã có lỗi xảy ra trong quá trình lấy dữ liệu lịch sử thay đổi.";
    })
    
    return {
        data,
        error
    }
}

export async function getLsCongSuatThucTe(axios, thietBiId) {
    var data = [];
    var error = "";
    await axios.get(`/web-ecms/idc/thiet-bi-vat-ly/lich-su-tac-dong/page?thietBiId=${thietBiId}&pageSize=100&pageNumber=1&tenCotList=CS_THUCTE`)
    .then((res) => {
        console.log(res);
        if (checkResponse(res)) {
            data = res.data.data.list;
        } else {
            error = "Đã có lỗi xảy ra trong quá trình lấy dữ liệu lịch sử công suất thực tế.";
        }
    }).catch((err) => {
        console.error(err);
        error = "Đã có lỗi xảy ra trong quá trình lấy dữ liệu lịch sử công suất thực tế.";
    })
    
    return {
        data,
        error
    }
}
