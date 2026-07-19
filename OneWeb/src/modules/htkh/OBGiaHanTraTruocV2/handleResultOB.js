
import apiHelper from './api.helper'
import api from './API.js'



const openRouteInNewTab = (sef, name, query, path) => {
    const routeData = sef.$router.resolve({ name, query, path });
    
    window.open(routeData.href, '_blank');
};
const buildQuery = (sef, data, additionalParams = {}) => {
    let query = {
        ma_tb: data.MA_TB,
        loaitb_id: data.LOAITB_ID,
        ob_id: data.OB_ID,
        dichvuvt_id: data.DICHVUVT_ID,
        user_ipcc: sef.user_ipcc,
        ...additionalParams,
    };
    if(data.MA_TB_KT) query.ma_tb_kt = data.MA_TB_KT;
    return query
}
// KH đồng ý gia hạn KQ_OB == 1
export const handleResultOB_DongYGiaHan =  (sef, data) => {
    handleSwitchAutoExtendOB(sef, data)
};

// "KH yêu cầu thay đổi tốc độ" : KQ_OB == 2
export const handleResultOB_ThayDoiTocDo =  (sef, data) => {
    const query = buildQuery(sef, data, { tag: 0 });
    openRouteInNewTab(sef, 'ThayDoiTocDO', query, '/contract/ChangeInternetSpeed');
};


// "KH yêu cầu thanh toán gia hạn tại tháng T+1;T+2" : KQ_OB == 3
export const handleResultOB_YeuCauThanhToanGiaHan =  (sef, data) => {
    handleSwitchAutoExtendOB(sef, data)
};

// "KH yêu cầu chuyển sang trả sau" : KQ_OB == 4
export const handleResultOB_ChuyenTraSau =  (sef, data) => {
    const query = buildQuery(sef, data);
    openRouteInNewTab(sef, 'CollectIncurredDebt', query, '/contract/CollectIncurredDebt');
};

// KH yêu cầu tạm ngưng"" : KQ_OB == 5
export const handleResultOB_YeuCauTamNgung =  (sef, data) => {
    const query = buildQuery(sef, data);
    openRouteInNewTab(sef, 'ChangeService', query, '/contract/ChangeService');
};

// "KH không hài lòng về chất lượng dịch vụ" : KQ_OB == 6
export const handleResultOB_KhongHaiLongChatLuongDV =  (sef, data) => {
    // await sef.handleBaoHongTuDong(data.OB_ID);
};

// KH không hài lòng về cước KQ_OB == 7
export const handleResultOB_KhongHaiLongVeCuoc =  (sef, data) => {

};

// KH yêu cầu thanh lý : KQ_OB == 8
export const handleResultOB_YeuCauThanhLy =  (sef, data) => {};

// KH hẹn thanh toán/ hẹn liên lạc lại sau : KQ_OB == 9
export const handleResultOB_HenLienLacLaiSau =  (sef, data) => {};

// Tiếp nhận đơn hàng" KQ_OB == 10
export const handleResultOB_TiepNhanDonHang =  (sef, data) => {};

// "Tạo yêu cầu lắp đặt" KQ_OB == 11
export const handleResultOB_TaoYeuCauLapDat =  (sef, data) => {};

// KH yêu cầu chuyển sang trả sau không tạm thu : KQ_OB == 12
export const handleResultOB_ChuyenTraSauKhongTamThu =  (sef, data) => {};

// Không đúng chủ thuê bao : KQ_OB == 13
export const handleResultOB_KhongDungChuThueBao =  (sef, data) => {};

// Không gặp được khách hàng : KQ_OB == 14
export const handleResultOB_KhongGapDuocKH =  (sef, data) => {};

// KH yêu cầu thay đổi đặt cọc : KQ_OB == 15
export const handleResultOB_YeuCauThayDoiDatCoc =  (sef, data) => {
    const query = buildQuery(sef, data);
    openRouteInNewTab(sef, 'ThayDoiDatCoc', query, 'ext-hopdong/thaydoi-datcoc-v2');
};




//xử lý data auto gia hạn
const handleSwitchAutoExtendOB = (sef,data) =>{
    let query = {};
    const autoGiaHanData = data?.AUTO_GH_DATA   //{data: "MGD-202108-0000000001", errorCode: ERR-1, faultString: "Khong cung thue bao"}
    
    if(!autoGiaHanData)  return sef.$toast.error("Không tự động tạo được giao dịch gia hạn hợp đồng !");
    const mgd = autoGiaHanData?.data;
    const errorCode = autoGiaHanData?.errorCode;
 

    switch (errorCode) {
        case "ERR-0":
        case "ERR-2":
        case "default":
            query = buildQuery(sef, data, {ma_gd: mgd});
            openRouteInNewTab(sef, 'CapNhatThueBaoDatCoc2', query, '/ext-hopdong/Cap-Nhat-Thue-Bao-Dat-Coc-v2');
            break;
        case "ERR-3":
        case "ERR-4":
            query = buildQuery(sef, data);
            openRouteInNewTab(sef, 'ThayDoiDatCoc', query, 'ext-hopdong/thaydoi-datcoc-v2');
            break;
        case "ERR-1":
            break;
    }
    return;
}