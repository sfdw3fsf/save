//BSS-38525: Tra cứu gói cước thuê bao trả sau thường
//WinUI.WinUITraCuuCCBS.frmTraCuu_GoiCuocTBTS_Thuong
export default {
    //BSS-38525_004 -> Tìm kiếm
    tim_kiem: (axios, data) => axios.post(`/ccbs/executor/ts_tracuu_trasau_thuong`, data),
}