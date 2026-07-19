function checkResponse(res) {
    return res && 
            res.data.data && 
            res.data.data.list &&
            res.data.error_code && 
            res.data.error_code === 'BSS-00000000';
}

export async function getLsThayDoi(axios, thietBiMauId) {
    var data = [];
    var error = "";
    // TODO: Replace with actual API endpoint for equipment template change history
    await axios.get(`/web-ecms/thiet-bi-mau/lich-su-tac-dong/page?thietBiMauId=${thietBiMauId}&pageSize=100&pageNumber=1`)
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
