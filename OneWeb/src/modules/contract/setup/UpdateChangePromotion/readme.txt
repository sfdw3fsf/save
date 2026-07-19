//MaTB CD test: 3246604, 3589114 (Điện thoại cố định-->ISDN) (Đã ok)
//MaTB IMS test: 3669761/3826662 (Thuê bao POTS) (Chuyển đổi Post MXU, Sip GPON sang Sip Trunking) (Đã ok)
//MaTB ADSL test: phuongvienadsl_hp . Tốc độ có mức cước: F1 - FTTH -->hình thưc chuyển đổi mega-->fiber (Đã ok)
//MaTB Megawan test: MW001010900 (Loại TB: MegawanADSL:23) --> hình thức chuyển đổi MegawanADSL-->MegawanSHDSL (Đã ok)
//MaTB Metronet test: MN000409196 (Loại TB: MetronetFE) (Đang test)
//MaTB TLS test: TSL015627 Tốc độ mới: 32 Mb(tocdo_id:72) (Loại TB: Đôi cáp đồng) (Chuyển đổi KTR (Điểm -Điểm) cáp đồng sang cáp quang) Loại TB mới: LeasedLineFE (Đã ok)
//MaTB Di động: 886404404/912551124/912412725(Trả sau)/888412555



Dùng trùng cho task [UR2.8.011] - Chi tiết chuyển đổi loại hình: search/subscriber/DetailChangeType
Tag: 

let query={
    hdkh_cha_id: <<value>>,
}
let routeData = this.$router.resolve({name: 'ChangeSubsType', query: query});
window.open(routeData.href, '_blank')