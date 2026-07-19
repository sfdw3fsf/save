//MaTB CD test: 3246604, 3589114 (Điện thoại cố định-->ISDN) (Đã ok)
//MaTB IMS test: 3669761/3826662 (Thuê bao POTS) (Chuyển đổi Post MXU, Sip GPON sang Sip Trunking) (Đã ok)
//MaTB ADSL test: phuongvienadsl_hp . Tốc độ có mức cước: F1 - FTTH -->hình thưc chuyển đổi mega-->fiber (Đã ok)
//MaTB Megawan test: MW001010900 (Loại TB: MegawanADSL:23) --> hình thức chuyển đổi MegawanADSL-->MegawanSHDSL (Đã ok)
//MaTB Metronet test: MN000409196 (Loại TB: MetronetFE) (Đang test)
//MaTB TLS test: TSL015627 Tốc độ mới: 32 Mb(tocdo_id:72) (Loại TB: Đôi cáp đồng) (Chuyển đổi KTR (Điểm -Điểm) cáp đồng sang cáp quang) Loại TB mới: LeasedLineFE (Đã ok)
//MaTB Di động: 886404404/912551124/912412725(Trả sau)/888412555



Dùng trùng cho task [UR2.8.011] - Chi tiết chuyển đổi loại hình: search/subscriber/DetailChangeType
Tag: 

MaGD test DangKyGoiDaDichVu: HPG-LD/00736242

let query={
    hdkh_cha_id: <<value>>,
}
let routeData = this.$router.resolve({name: 'ChangeSubsType', query: query});
window.open(routeData.href, '_blank')

frmDangKyGoiDaDVLM-->Button Goi Da
link-api: https://api-onebss-dev.vnptit3.vn/web-hopdong/swagger-ui/#/UR2.1.004/fn_lay_tt_donvi_nganhngheUsingPOST
link-khảo sát: https://docs.google.com/spreadsheets/d/1-L0ojT6OkWz4CTJR5sUeHlbzChRzO5i_tE9G_RrZ9Vc/edit#gid=1906551485&range=B3357
Giao diện: file:///Users/kunsubin/VNPT/OneBss/onebss_design/HTML/BSS-1027--LapHopDongLapDatMoi/DangKyGoiDaDichVu.html

//UR2.11.007_008
//LAY_DS_HOPDONG_THEO_MA_GD/BusinessFacade.TraCuuFacade.TCHopDongFacade.LAY_DS_HOPDONG_THEO_MA_GD
https://docs.google.com/spreadsheets/d/1-L0ojT6OkWz4CTJR5sUeHlbzChRzO5i_tE9G_RrZ9Vc/edit#gid=1791360228&range=D41
POST web-hopdong/hopdong/lay_ds_hopdong_theo_ma_gd
{
  "in_loaihd_id": 0,
  "in_ma_gd": "VTLH-11/0611954",
  "in_tthd_id": 0
}
test 

HPG-TT/01167627    3892109

HPG-LD/00736242