/**
 * SubscriberManagement.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.9  Built on : Nov 16, 2018 (12:05:37 GMT)
 */
package org.tempuri;


/*
 *  SubscriberManagement java interface
 */
public interface SubscriberManagement {
    /**
     * Auto generated method signature
     * [B2B] Đăng ký mới gói cước AddOn B2B cho khách sạn
     * @param b2B_Room_RegistAddOn
     * @param authHeader
     */
    public org.tempuri.B2B_Room_RegistAddOnResponse b2B_Room_RegistAddOn(
        org.tempuri.B2B_Room_RegistAddOn b2B_Room_RegistAddOn,
        org.tempuri.AuthHeaderE authHeader) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Thay đổi mã hỗ trợ giá
     * @param changeID_STBDiscount
     * @param authHeader0
     */
    public org.tempuri.ChangeID_STBDiscountResponse changeID_STBDiscount(
        org.tempuri.ChangeID_STBDiscount changeID_STBDiscount,
        org.tempuri.AuthHeaderE authHeader0) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Block PayTV V2
     * @param blockPayTV
     * @param authHeader1
     */
    public org.tempuri.BlockPayTVResponse blockPayTV(
        org.tempuri.BlockPayTV blockPayTV, org.tempuri.AuthHeaderE authHeader1)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * [B2B] Sửa thông tin khách sạn
     * @param b2B_EditHotel
     * @param authHeader2
     */
    public org.tempuri.B2B_EditHotelResponse b2B_EditHotel(
        org.tempuri.B2B_EditHotel b2B_EditHotel,
        org.tempuri.AuthHeaderE authHeader2) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Thay đổi bộ giải mã
     * @param changeSTB
     * @param authHeader3
     */
    public org.tempuri.ChangeSTBResponse changeSTB(
        org.tempuri.ChangeSTB changeSTB, org.tempuri.AuthHeaderE authHeader3)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Thay đổi điểm giao dịch khách hàng
     * @param changeDistrictSubscriber
     * @param authHeader4
     */
    public org.tempuri.ChangeDistrictSubscriberResponse changeDistrictSubscriber(
        org.tempuri.ChangeDistrictSubscriber changeDistrictSubscriber,
        org.tempuri.AuthHeaderE authHeader4) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Đăng ký thuê bao mới, quản lý thêm điểm giao dịch, trả trước, sau , MegaMYTV, BIDV, KGG, ND-TV, Fiber MyTV B2B
     * @param registerSubscriberV2_1KGG
     * @param authHeader5
     */
    public org.tempuri.RegisterSubscriberV2_1KGGResponse registerSubscriberV2_1KGG(
        org.tempuri.RegisterSubscriberV2_1KGG registerSubscriberV2_1KGG,
        org.tempuri.AuthHeaderE authHeader5) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Thay đổi trạng thái khách sạn
     * @param changeStatusHotel
     * @param authHeader6
     */
    public org.tempuri.ChangeStatusHotelResponse changeStatusHotel(
        org.tempuri.ChangeStatusHotel changeStatusHotel,
        org.tempuri.AuthHeaderE authHeader6) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Lấy danh sách yêu cầu đăng ký app mạng trong
     * @param smart_OTTGetMemberPending
     * @param authHeader7
     */
    public org.tempuri.Smart_OTTGetMemberPendingResponse smart_OTTGetMemberPending(
        org.tempuri.Smart_OTTGetMemberPending smart_OTTGetMemberPending,
        org.tempuri.AuthHeaderE authHeader7) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Khuyến mại STB, thống kê thuê bao PSC lũy kế
     * @param sTB_PROM_Summary
     * @param authHeader8
     */
    public org.tempuri.STB_PROM_SummaryResponse sTB_PROM_Summary(
        org.tempuri.STB_PROM_Summary sTB_PROM_Summary,
        org.tempuri.AuthHeaderE authHeader8) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * [B2B] Đăng ký mới gói cước AddOn B2B
     * @param b2B_Global_AddAddOn
     * @param authHeader9
     */
    public org.tempuri.B2B_Global_AddAddOnResponse b2B_Global_AddAddOn(
        org.tempuri.B2B_Global_AddAddOn b2B_Global_AddAddOn,
        org.tempuri.AuthHeaderE authHeader9) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Đăng ký/hủy gói MyTV Plus
     * @param totalMyTVPlus
     * @param authHeader10
     */
    public org.tempuri.TotalMyTVPlusResponse totalMyTVPlus(
        org.tempuri.TotalMyTVPlus totalMyTVPlus,
        org.tempuri.AuthHeaderE authHeader10) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Truy vấn thông tin mã đối tượng giảm trừ khuyến mại
     * @param getListObjPromoCD
     * @param authHeader11
     */
    public org.tempuri.GetListObjPromoCDResponse getListObjPromoCD(
        org.tempuri.GetListObjPromoCD getListObjPromoCD,
        org.tempuri.AuthHeaderE authHeader11) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Cập nhật thiết bị do khách hàng tự trang bị hay do VNPT cung cấp
     * @param sTB_Rent_UpdateInfo
     * @param authHeader12
     */
    public org.tempuri.STB_Rent_UpdateInfoResponse sTB_Rent_UpdateInfo(
        org.tempuri.STB_Rent_UpdateInfo sTB_Rent_UpdateInfo,
        org.tempuri.AuthHeaderE authHeader12) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Truy vấn thông tin khách hàng: Tivi 1,2 tình trạng STB
     * @param getSubscriberInfoV3_List
     * @param authHeader13
     */
    public org.tempuri.GetSubscriberInfoV3_ListResponse getSubscriberInfoV3_List(
        org.tempuri.GetSubscriberInfoV3_List getSubscriberInfoV3_List,
        org.tempuri.AuthHeaderE authHeader13) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Đăng ký thuê bao mới, quản lý thêm điểm giao dịch, trả trước, sau , MegaMYTV
     * @param registerSubscriberFull
     * @param authHeader14
     */
    public org.tempuri.RegisterSubscriberFullResponse registerSubscriberFull(
        org.tempuri.RegisterSubscriberFull registerSubscriberFull,
        org.tempuri.AuthHeaderE authHeader14) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * [B2B] Đăng ký mới gói cước B2B
     * @param b2B_Global_AddPackage
     * @param authHeader15
     */
    public org.tempuri.B2B_Global_AddPackageResponse b2B_Global_AddPackage(
        org.tempuri.B2B_Global_AddPackage b2B_Global_AddPackage,
        org.tempuri.AuthHeaderE authHeader15) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * [B2B] Xóa khách sạn
     * @param b2B_DeleteHotel
     * @param authHeader16
     */
    public org.tempuri.B2B_DeleteHotelResponse b2B_DeleteHotel(
        org.tempuri.B2B_DeleteHotel b2B_DeleteHotel,
        org.tempuri.AuthHeaderE authHeader16) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Khóa mở K+, 2018 không sử dụng nữa, chuyển sang BlockPayTV
     * @param updateKplusInfo
     * @param authHeader17
     */
    public org.tempuri.UpdateKplusInfoResponse updateKplusInfo(
        org.tempuri.UpdateKplusInfo updateKplusInfo,
        org.tempuri.AuthHeaderE authHeader17) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Thay đổi thông tin cá nhân của khách hàng
     * @param changeSubscriber
     * @param authHeader18
     */
    public org.tempuri.ChangeSubscriberResponse changeSubscriber(
        org.tempuri.ChangeSubscriber changeSubscriber,
        org.tempuri.AuthHeaderE authHeader18) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Cập nhật mã thiết bị
     * @param updateDeviceInfo
     * @param authHeader19
     */
    public org.tempuri.UpdateDeviceInfoResponse updateDeviceInfo(
        org.tempuri.UpdateDeviceInfo updateDeviceInfo,
        org.tempuri.AuthHeaderE authHeader19) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Cập nhật thuê bao thứ 2 theo kịch bản 3 gói cước mới 2019: Chuẩn/Nâng cao/VIP
     * @param cust_UserMulti_UpdateInfo
     * @param authHeader20
     */
    public org.tempuri.Cust_UserMulti_UpdateInfoResponse cust_UserMulti_UpdateInfo(
        org.tempuri.Cust_UserMulti_UpdateInfo cust_UserMulti_UpdateInfo,
        org.tempuri.AuthHeaderE authHeader20) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Cập nhật đối tượng khách hàng dùng thử theo CV
     * @param updateSubTrial
     * @param authHeader21
     */
    public org.tempuri.UpdateSubTrialResponse updateSubTrial(
        org.tempuri.UpdateSubTrial updateSubTrial,
        org.tempuri.AuthHeaderE authHeader21) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Tạm ngưng sử dụng dịch vụ của khách hàng theo Mã lý do
     * @param suspendSubscriberV2
     * @param authHeader22
     */
    public org.tempuri.SuspendSubscriberV2Response suspendSubscriberV2(
        org.tempuri.SuspendSubscriberV2 suspendSubscriberV2,
        org.tempuri.AuthHeaderE authHeader22) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Đăng ký các gói AddOn PayTV của MyTV
     * @param registAddOn
     * @param authHeader23
     */
    public org.tempuri.RegistAddOnResponse registAddOn(
        org.tempuri.RegistAddOn registAddOn,
        org.tempuri.AuthHeaderE authHeader23) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Thay đổi gói cước sử dụng dịch vụ 2013
     * @param changePackageV3
     * @param authHeader24
     */
    public org.tempuri.ChangePackageV3Response changePackageV3(
        org.tempuri.ChangePackageV3 changePackageV3,
        org.tempuri.AuthHeaderE authHeader24) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Truy vấn danh sách khách hàng thay đổi gói cước trên EPG
     * @param getPackageEPG
     * @param authHeader25
     */
    public org.tempuri.GetPackageEPGResponse getPackageEPG(
        org.tempuri.GetPackageEPG getPackageEPG,
        org.tempuri.AuthHeaderE authHeader25) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Hủy sử dụng dịch vụ IPTV
     * @param cancelSubscriber
     * @param authHeader26
     */
    public org.tempuri.CancelSubscriberResponse cancelSubscriber(
        org.tempuri.CancelSubscriber cancelSubscriber,
        org.tempuri.AuthHeaderE authHeader26) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Xóa điểm giao dịch
     * @param deleteDistrict
     * @param authHeader27
     */
    public org.tempuri.DeleteDistrictResponse deleteDistrict(
        org.tempuri.DeleteDistrict deleteDistrict,
        org.tempuri.AuthHeaderE authHeader27) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Đăng ký mới khách sạn
     * @param addHotel
     * @param authHeader28
     */
    public org.tempuri.AddHotelResponse addHotel(
        org.tempuri.AddHotel addHotel, org.tempuri.AuthHeaderE authHeader28)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Sửa thông tin khách sạn
     * @param editHotel
     * @param authHeader29
     */
    public org.tempuri.EditHotelResponse editHotel(
        org.tempuri.EditHotel editHotel, org.tempuri.AuthHeaderE authHeader29)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * [B2B] Hủy gói cước AddOn B2B cho phòng thuộc khách sạn
     * @param b2B_Room_CancelAddOn
     * @param authHeader30
     */
    public org.tempuri.B2B_Room_CancelAddOnResponse b2B_Room_CancelAddOn(
        org.tempuri.B2B_Room_CancelAddOn b2B_Room_CancelAddOn,
        org.tempuri.AuthHeaderE authHeader30) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Đăng ký/hủy gói K+ addon
     * @param registKplus
     * @param authHeader31
     */
    public org.tempuri.RegistKplusResponse registKplus(
        org.tempuri.RegistKplus registKplus,
        org.tempuri.AuthHeaderE authHeader31) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Khôi phục lại sử dụng dịch vụ cho khách hàng
     * @param resumeSubscriber
     * @param authHeader32
     */
    public org.tempuri.ResumeSubscriberResponse resumeSubscriber(
        org.tempuri.ResumeSubscriber resumeSubscriber,
        org.tempuri.AuthHeaderE authHeader32) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Đăng ký thuê bao mới, quản lý thêm điểm giao dịch
     * @param registerSubscriberNew
     * @param authHeader33
     */
    public org.tempuri.RegisterSubscriberNewResponse registerSubscriberNew(
        org.tempuri.RegisterSubscriberNew registerSubscriberNew,
        org.tempuri.AuthHeaderE authHeader33) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Thay đổi mật khẩu của thuê bao MyTV
     * @param changePassword
     * @param authHeader34
     */
    public org.tempuri.ChangePasswordResponse changePassword(
        org.tempuri.ChangePassword changePassword,
        org.tempuri.AuthHeaderE authHeader34) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Đăng ký thuê bao mới
     * @param registerSubscriberPay
     * @param authHeader35
     */
    public org.tempuri.RegisterSubscriberPayResponse registerSubscriberPay(
        org.tempuri.RegisterSubscriberPay registerSubscriberPay,
        org.tempuri.AuthHeaderE authHeader35) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Truy vấn danh sách thông tin khách hàng
     * @param getListSubscriber
     * @param authHeader36
     */
    public org.tempuri.GetListSubscriberResponse getListSubscriber(
        org.tempuri.GetListSubscriber getListSubscriber,
        org.tempuri.AuthHeaderE authHeader36) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Thay đổi gói cước sử dụng dịch vụ, gói tích hợp, gói tích hợp MyTV Combo
     * @param changePackageAndMegaVNN_TitanSportHome
     * @param authHeader37
     */
    public org.tempuri.ChangePackageAndMegaVNN_TitanSportHomeResponse changePackageAndMegaVNNV3(
        org.tempuri.ChangePackageAndMegaVNN_TitanSportHome changePackageAndMegaVNN_TitanSportHome,
        org.tempuri.AuthHeaderE authHeader37) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Thay đổi nhóm khách hàng ưu đãi
     * @param changePromotions
     * @param authHeader38
     */
    public org.tempuri.ChangePromotionsResponse changePromotions(
        org.tempuri.ChangePromotions changePromotions,
        org.tempuri.AuthHeaderE authHeader38) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Thay đổi mật khẩu của thành viên
     * @param changeUserPassword
     * @param authHeader39
     */
    public org.tempuri.ChangeUserPasswordResponse changeUserPassword(
        org.tempuri.ChangeUserPassword changeUserPassword,
        org.tempuri.AuthHeaderE authHeader39) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Thay đổi mã hợp đồng
     * @param changeContract
     * @param authHeader40
     */
    public org.tempuri.ChangeContractResponse changeContract(
        org.tempuri.ChangeContract changeContract,
        org.tempuri.AuthHeaderE authHeader40) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Đăng ký khuyến mại mua PayTV trĐn gói
     * @param payTVFull_Register
     * @param authHeader41
     */
    public org.tempuri.PayTVFull_RegisterResponse payTVFull_Register(
        org.tempuri.PayTVFull_Register payTVFull_Register,
        org.tempuri.AuthHeaderE authHeader41) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Tạm ngưng sử dụng dịch vụ của khách hàng
     * @param suspendSubscriber
     * @param authHeader42
     */
    public org.tempuri.SuspendSubscriberResponse suspendSubscriber(
        org.tempuri.SuspendSubscriber suspendSubscriber,
        org.tempuri.AuthHeaderE authHeader42) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Cập mã khuyến mại HSBC cho HCM
     * @param updatePromotions
     * @param authHeader43
     */
    public org.tempuri.UpdatePromotionsResponse updatePromotions(
        org.tempuri.UpdatePromotions updatePromotions,
        org.tempuri.AuthHeaderE authHeader43) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Đăng ký thuê bao mới, quản lý thêm điểm giao dịch, trả trước, sau , MegaMYTV, BIDV, HNI, ND-TV, Fiber MyTV B2B
     * @param registerSubscriberV2_1HNI
     * @param authHeader44
     */
    public org.tempuri.RegisterSubscriberV2_1HNIResponse registerSubscriberV2_1HNI(
        org.tempuri.RegisterSubscriberV2_1HNI registerSubscriberV2_1HNI,
        org.tempuri.AuthHeaderE authHeader44) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Thay đổi gói cước sử dụng dịch vụ
     * @param changePackageHST
     * @param authHeader45
     */
    public org.tempuri.ChangePackageHSTResponse changePackageHST(
        org.tempuri.ChangePackageHST changePackageHST,
        org.tempuri.AuthHeaderE authHeader45) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Cập nhật tài khoản sang nhóm truyĐn hình hội nghị
     * @param updateUserConference
     * @param authHeader46
     */
    public org.tempuri.UpdateUserConferenceResponse updateUserConference(
        org.tempuri.UpdateUserConference updateUserConference,
        org.tempuri.AuthHeaderE authHeader46) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Thay đổi gói cước sử dụng dịch vụ
     * @param changePackage
     * @param authHeader47
     */
    public org.tempuri.ChangePackageResponse changePackage(
        org.tempuri.ChangePackage changePackage,
        org.tempuri.AuthHeaderE authHeader47) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Truy vấn thông tin bộ giải mã
     * @param getSTBInfo
     * @param authHeader48
     */
    public org.tempuri.GetSTBInfoResponse getSTBInfo(
        org.tempuri.GetSTBInfo getSTBInfo, org.tempuri.AuthHeaderE authHeader48)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Cập nhật mã gói cước FiberVNN
     * @param changeFiberVNN
     * @param authHeader49
     */
    public org.tempuri.ChangeFiberVNNResponse changeFiberVNN(
        org.tempuri.ChangeFiberVNN changeFiberVNN,
        org.tempuri.AuthHeaderE authHeader49) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Đăng ký thuê bao mới, quản lý thêm điểm giao dịch, trả trước, sau , MegaMYTV, BIDV, HNI
     * @param registerSubscriberV2HNI
     * @param authHeader50
     */
    public org.tempuri.RegisterSubscriberV2HNIResponse registerSubscriberV2HNI(
        org.tempuri.RegisterSubscriberV2HNI registerSubscriberV2HNI,
        org.tempuri.AuthHeaderE authHeader50) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Thêm mới điểm giao dịch
     * @param addNewDistrict
     * @param authHeader51
     */
    public org.tempuri.AddNewDistrictResponse addNewDistrict(
        org.tempuri.AddNewDistrict addNewDistrict,
        org.tempuri.AuthHeaderE authHeader51) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Thay đổi gói cước tích hợp, gói tích hợp MyTV Combo không làm thay đổi gói cước thuê bao
     * @param changeMegaVNNV3
     * @param authHeader52
     */
    public org.tempuri.ChangeMegaVNNV3Response changeMegaVNNV3(
        org.tempuri.ChangeMegaVNNV3 changeMegaVNNV3,
        org.tempuri.AuthHeaderE authHeader52) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Truy vấn thông tin khóa/mở gói PayTV
     * @param getStatusBlockPayTV
     * @param authHeader53
     */
    public org.tempuri.GetStatusBlockPayTVResponse getStatusBlockPayTV(
        org.tempuri.GetStatusBlockPayTV getStatusBlockPayTV,
        org.tempuri.AuthHeaderE authHeader53) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Xóa thông tin khách hàng
     * @param deleteMyTV
     * @param authHeader54
     */
    public org.tempuri.DeleteMyTVResponse deleteMyTV(
        org.tempuri.DeleteMyTV deleteMyTV, org.tempuri.AuthHeaderE authHeader54)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Đăng ký thuê bao theo chương trình SelfCare
     * @param registerSelfCare
     * @param authHeader55
     */
    public org.tempuri.RegisterSelfCareResponse registerSelfCare(
        org.tempuri.RegisterSelfCare registerSelfCare,
        org.tempuri.AuthHeaderE authHeader55) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Đăng ký thuê bao MyTV Hệ sinh thái - EcoSystem
     * @param registerSubscriberHST
     * @param authHeader56
     */
    public org.tempuri.RegisterSubscriberHSTResponse registerSubscriberHST(
        org.tempuri.RegisterSubscriberHST registerSubscriberHST,
        org.tempuri.AuthHeaderE authHeader56) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Khuyến mại STB, khai báo thông tin STB
     * @param sTB_PROM_Register
     * @param authHeader57
     */
    public org.tempuri.STB_PROM_RegisterResponse sTB_PROM_Register(
        org.tempuri.STB_PROM_Register sTB_PROM_Register,
        org.tempuri.AuthHeaderE authHeader57) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Hủy sử dụng dịch vụ IPTV theo Mã lý do hủy
     * @param cancelSubscriberV2
     * @param authHeader58
     */
    public org.tempuri.CancelSubscriberV2Response cancelSubscriberV2(
        org.tempuri.CancelSubscriberV2 cancelSubscriberV2,
        org.tempuri.AuthHeaderE authHeader58) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Truy vấn thông tin thiet bi dau cuoi
     * @param getDeviceInfo
     * @param authHeader59
     */
    public org.tempuri.GetDeviceInfoResponse getDeviceInfo(
        org.tempuri.GetDeviceInfo getDeviceInfo,
        org.tempuri.AuthHeaderE authHeader59) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Kiểm tra mat khau cua thue bao MyTV
     * @param validPassword
     * @param authHeader60
     */
    public org.tempuri.ValidPasswordResponse validPassword(
        org.tempuri.ValidPassword validPassword,
        org.tempuri.AuthHeaderE authHeader60) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Truy vấn thông tin gói cước
     * @param getPackageName
     * @param authHeader61
     */
    public org.tempuri.GetPackageNameResponse getPackageName(
        org.tempuri.GetPackageName getPackageName,
        org.tempuri.AuthHeaderE authHeader61) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Thay đổi mã hỗ trợ giá dành cho VNPT HCM
     * @param changeFiberCD
     * @param authHeader62
     */
    public org.tempuri.ChangeFiberCDResponse changeFiberCD(
        org.tempuri.ChangeFiberCD changeFiberCD,
        org.tempuri.AuthHeaderE authHeader62) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Thay đổi mật khẩu của thuê bao khách sạn
     * @param changePWHotel
     * @param authHeader63
     */
    public org.tempuri.ChangePWHotelResponse changePWHotel(
        org.tempuri.ChangePWHotel changePWHotel,
        org.tempuri.AuthHeaderE authHeader63) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Khuyến mại STB, thu hồi STB
     * @param sTB_PROM_Back
     * @param authHeader64
     */
    public org.tempuri.STB_PROM_BackResponse sTB_PROM_Back(
        org.tempuri.STB_PROM_Back sTB_PROM_Back,
        org.tempuri.AuthHeaderE authHeader64) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Khuyến mại STB, thống kê thuê bao PSC lũy kế theo CT Dùng thử 3855
     * @param sTB_PROM_Summary3855
     * @param authHeader65
     */
    public org.tempuri.STB_PROM_Summary3855Response sTB_PROM_Summary3855(
        org.tempuri.STB_PROM_Summary3855 sTB_PROM_Summary3855,
        org.tempuri.AuthHeaderE authHeader65) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Cập nhật đăng ký GponCab cho tài khoản MYTV
     * @param updateSubGponCab
     * @param authHeader66
     */
    public org.tempuri.UpdateSubGponCabResponse updateSubGponCab(
        org.tempuri.UpdateSubGponCab updateSubGponCab,
        org.tempuri.AuthHeaderE authHeader66) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * [B2B] Đăng ký mới phòng thuộc khách sạn
     * @param b2B_AddRoom
     * @param authHeader67
     */
    public org.tempuri.B2B_AddRoomResponse b2B_AddRoom(
        org.tempuri.B2B_AddRoom b2B_AddRoom,
        org.tempuri.AuthHeaderE authHeader67) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Thay đổi gói cước tích hợp, gói tích hợp MyTV Combo không làm thay đổi gói cước thuê bao
     * @param changeMegaVNN
     * @param authHeader68
     */
    public org.tempuri.ChangeMegaVNNResponse changeMegaVNN(
        org.tempuri.ChangeMegaVNN changeMegaVNN,
        org.tempuri.AuthHeaderE authHeader68) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Truy vấn thông tin khách hàng: Tivi 1,2 tình trạng STB
     * @param getSubscriberInfoV3
     * @param authHeader69
     */
    public org.tempuri.GetSubscriberInfoV3Response getSubscriberInfoV3(
        org.tempuri.GetSubscriberInfoV3 getSubscriberInfoV3,
        org.tempuri.AuthHeaderE authHeader69) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Truy vấn thông tin khách hàng thêm điĐu kiện khác
     * @param getSubscriberInfoV2
     * @param authHeader70
     */
    public org.tempuri.GetSubscriberInfoV2Response getSubscriberInfoV2(
        org.tempuri.GetSubscriberInfoV2 getSubscriberInfoV2,
        org.tempuri.AuthHeaderE authHeader70) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Thay đổi hệ thống MyTV - VMP
     * @param changeSystem
     * @param authHeader71
     */
    public org.tempuri.ChangeSystemResponse changeSystem(
        org.tempuri.ChangeSystem changeSystem,
        org.tempuri.AuthHeaderE authHeader71) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Truy vấn thông tin khách hàng
     * @param getSubscriberInfo
     * @param authHeader72
     */
    public org.tempuri.GetSubscriberInfoResponse getSubscriberInfo(
        org.tempuri.GetSubscriberInfo getSubscriberInfo,
        org.tempuri.AuthHeaderE authHeader72) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Lấy thông tin tài khoản chính/phụ
     * @param getUserParent
     * @param authHeader73
     */
    public org.tempuri.GetUserParentResponse getUserParent(
        org.tempuri.GetUserParent getUserParent,
        org.tempuri.AuthHeaderE authHeader73) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Đăng ký tài khoản GponCab không gắn với MyTV
     * @param gponCab_RegistWithoutMyTV
     * @param authHeader74
     */
    public org.tempuri.GponCab_RegistWithoutMyTVResponse gponCab_RegistWithoutMyTV(
        org.tempuri.GponCab_RegistWithoutMyTV gponCab_RegistWithoutMyTV,
        org.tempuri.AuthHeaderE authHeader74) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Thay đổi thông tin điểm giao dịch
     * @param editDistrict
     * @param authHeader75
     */
    public org.tempuri.EditDistrictResponse editDistrict(
        org.tempuri.EditDistrict editDistrict,
        org.tempuri.AuthHeaderE authHeader75) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Cập nhật thuê dùng thử theo kịch bản 3 gói cước mới 2019: Chuẩn/Nâng cao/VIP
     * @param cust_Trial_UpdateInfo
     * @param authHeader76
     */
    public org.tempuri.Cust_Trial_UpdateInfoResponse cust_Trial_UpdateInfo(
        org.tempuri.Cust_Trial_UpdateInfo cust_Trial_UpdateInfo,
        org.tempuri.AuthHeaderE authHeader76) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Cập nhật đăng ký trả trước Khuyến Mại HCM
     * @param updatePrepaid
     * @param authHeader77
     */
    public org.tempuri.UpdatePrepaidResponse updatePrepaid(
        org.tempuri.UpdatePrepaid updatePrepaid,
        org.tempuri.AuthHeaderE authHeader77) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Thay đổi gói cước sử dụng dịch vụ, gói tích hợp
     * @param changePackageAndMegaVNN
     * @param authHeader78
     */
    public org.tempuri.ChangePackageAndMegaVNNResponse changePackageAndMegaVNN(
        org.tempuri.ChangePackageAndMegaVNN changePackageAndMegaVNN,
        org.tempuri.AuthHeaderE authHeader78) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Truy vấn thông tin gói cước của tháng
     * @param getBillByMonth
     * @param authHeader79
     */
    public org.tempuri.GetBillByMonthResponse getBillByMonth(
        org.tempuri.GetBillByMonth getBillByMonth,
        org.tempuri.AuthHeaderE authHeader79) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * RĐi gói tích hợp cho các thuê bao MyTV đang tạm ngưng
     * @param cancelMegaForSuspendAccount
     * @param authHeader80
     */
    public org.tempuri.CancelMegaForSuspendAccountResponse cancelMegaForSuspendAccount(
        org.tempuri.CancelMegaForSuspendAccount cancelMegaForSuspendAccount,
        org.tempuri.AuthHeaderE authHeader80) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Cập nhật gói cước daily
     * @param updatePackDaily
     * @param authHeader81
     */
    public org.tempuri.UpdatePackDailyResponse updatePackDaily(
        org.tempuri.UpdatePackDaily updatePackDaily,
        org.tempuri.AuthHeaderE authHeader81) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Thay đổi ngày tính cước
     * @param changeDateBill
     * @param authHeader82
     */
    public org.tempuri.ChangeDateBillResponse changeDateBill(
        org.tempuri.ChangeDateBill changeDateBill,
        org.tempuri.AuthHeaderE authHeader82) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Cập nhật đăng ký thông tin mã đối tượng giảm trừ khuyến mại
     * @param updateObjPromoCD
     * @param authHeader83
     */
    public org.tempuri.UpdateObjPromoCDResponse updateObjPromoCD(
        org.tempuri.UpdateObjPromoCD updateObjPromoCD,
        org.tempuri.AuthHeaderE authHeader83) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Đăng ký khuyến mại gói năm 2021: K+, HBO
     * @param registerKplusPromotion2021
     * @param authHeader84
     */
    public org.tempuri.RegisterKplusPromotion2021Response registerKplusPromotion2021(
        org.tempuri.RegisterKplusPromotion2021 registerKplusPromotion2021,
        org.tempuri.AuthHeaderE authHeader84) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Truy  vấn lịch sử sử thay đổi bộ giải mã của một tài khoản
     * @param getHisSubscriberChangeSTB
     * @param authHeader85
     */
    public org.tempuri.GetHisSubscriberChangeSTBResponse getHisSubscriberChangeSTB(
        org.tempuri.GetHisSubscriberChangeSTB getHisSubscriberChangeSTB,
        org.tempuri.AuthHeaderE authHeader85) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Truy vấn thông tin khách hàng: Tivi 1,2 tình trạng STB
     * @param getSubscriberInfoV3_String
     * @param authHeader86
     */
    public org.tempuri.GetSubscriberInfoV3_StringResponse getSubscriberInfoV3_String(
        org.tempuri.GetSubscriberInfoV3_String getSubscriberInfoV3_String,
        org.tempuri.AuthHeaderE authHeader86) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * [B2B] Đăng ký mới gói cước B2B cho khách sạn
     * @param b2B_Hotel_AddPackage
     * @param authHeader87
     */
    public org.tempuri.B2B_Hotel_AddPackageResponse b2B_Hotel_AddPackage(
        org.tempuri.B2B_Hotel_AddPackage b2B_Hotel_AddPackage,
        org.tempuri.AuthHeaderE authHeader87) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Khuyến mại STB, hoàn công STB
     * @param sTB_PROM_Active
     * @param authHeader88
     */
    public org.tempuri.STB_PROM_ActiveResponse sTB_PROM_Active(
        org.tempuri.STB_PROM_Active sTB_PROM_Active,
        org.tempuri.AuthHeaderE authHeader88) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Thay đổi thông tin GponCab
     * @param gponCab_Update
     * @param authHeader89
     */
    public org.tempuri.GponCab_UpdateResponse gponCab_Update(
        org.tempuri.GponCab_Update gponCab_Update,
        org.tempuri.AuthHeaderE authHeader89) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Đăng ký/Hủy khuyến mại Combo-FAF
     * @param comboFAF_Update
     * @param authHeader90
     */
    public org.tempuri.ComboFAF_UpdateResponse comboFAF_Update(
        org.tempuri.ComboFAF_Update comboFAF_Update,
        org.tempuri.AuthHeaderE authHeader90) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Active thuê bao SelfCare
     * @param activeSelfCare
     * @param authHeader91
     */
    public org.tempuri.ActiveSelfCareResponse activeSelfCare(
        org.tempuri.ActiveSelfCare activeSelfCare,
        org.tempuri.AuthHeaderE authHeader91) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Lấy lại mật khẩu MyTV
     * @param resetPassword
     * @param authHeader92
     */
    public org.tempuri.ResetPasswordResponse resetPassword(
        org.tempuri.ResetPassword resetPassword,
        org.tempuri.AuthHeaderE authHeader92) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Đăng ký mới thuê bao, quản lý thêm điểm giao dịch, trả trước trả sau, MegaVNN , Thêm Port, BIDV, ND-TV
     * @param registerSubscriberV2_1
     * @param authHeader93
     */
    public org.tempuri.RegisterSubscriberV2_1Response registerSubscriberV2_1(
        org.tempuri.RegisterSubscriberV2_1 registerSubscriberV2_1,
        org.tempuri.AuthHeaderE authHeader93) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Thay đổi hình thức thanh toán trả trước, trả sau
     * @param methodChangePay
     * @param authHeader94
     */
    public org.tempuri.MethodChangePayResponse methodChangePay(
        org.tempuri.MethodChangePay methodChangePay,
        org.tempuri.AuthHeaderE authHeader94) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Đăng ký thuê bao mới, quản lý thêm điểm giao dịch, trả trước, sau , MegaMYTV
     * @param registerSubscriberFull_Update
     * @param authHeader95
     */
    public org.tempuri.RegisterSubscriberFull_UpdateResponse registerSubscriberFull_Update(
        org.tempuri.RegisterSubscriberFull_Update registerSubscriberFull_Update,
        org.tempuri.AuthHeaderE authHeader95) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Tra cứu username theo mã STB
     * @param getSubscriberBySTB_SN
     * @param authHeader96
     */
    public org.tempuri.GetSubscriberBySTB_SNResponse getSubscriberBySTB_SN(
        org.tempuri.GetSubscriberBySTB_SN getSubscriberBySTB_SN,
        org.tempuri.AuthHeaderE authHeader96) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Thay đổi mã ghép cước
     * @param changeBillCode
     * @param authHeader97
     */
    public org.tempuri.ChangeBillCodeResponse changeBillCode(
        org.tempuri.ChangeBillCode changeBillCode,
        org.tempuri.AuthHeaderE authHeader97) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Khuyến mại STB, chuyển lên dùng thật khi hết dùng thử
     * @param sTB_PROM_Buy
     * @param authHeader98
     */
    public org.tempuri.STB_PROM_BuyResponse sTB_PROM_Buy(
        org.tempuri.STB_PROM_Buy sTB_PROM_Buy,
        org.tempuri.AuthHeaderE authHeader98) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Thay đổi nhóm khách hàng ưu đãi đăng ký tích hợp nhiĐu dịch vụ
     * @param changePromotionsV2
     * @param authHeader99
     */
    public org.tempuri.ChangePromotionsV2Response changePromotionsV2(
        org.tempuri.ChangePromotionsV2 changePromotionsV2,
        org.tempuri.AuthHeaderE authHeader99) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Đăng ký thuê bao mới
     * @param registerSubscriber
     * @param authHeader100
     */
    public org.tempuri.RegisterSubscriberResponse registerSubscriber(
        org.tempuri.RegisterSubscriber registerSubscriber,
        org.tempuri.AuthHeaderE authHeader100) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Truy  vấn lịch sử đăng ký, hủy gói Kplus, HBO, VTVcab, fafilm
     * @param getHisPayTV
     * @param authHeader101
     */
    public org.tempuri.GetHisPayTVResponse getHisPayTV(
        org.tempuri.GetHisPayTV getHisPayTV,
        org.tempuri.AuthHeaderE authHeader101) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Truy vấn thông tin bộ giải mã chi tiết
     * @param getSTBInfor
     * @param authHeader102
     */
    public org.tempuri.GetSTBInforResponse getSTBInfor(
        org.tempuri.GetSTBInfor getSTBInfor,
        org.tempuri.AuthHeaderE authHeader102) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * [B2B] Đăng ký mới khách sạn
     * @param b2B_AddHotel
     * @param authHeader103
     */
    public org.tempuri.B2B_AddHotelResponse b2B_AddHotel(
        org.tempuri.B2B_AddHotel b2B_AddHotel,
        org.tempuri.AuthHeaderE authHeader103) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Thay đổi thông tin cá nhân của khách hàng 2013
     * @param changeSubscriberV3
     * @param authHeader104
     */
    public org.tempuri.ChangeSubscriberV3Response changeSubscriberV3(
        org.tempuri.ChangeSubscriberV3 changeSubscriberV3,
        org.tempuri.AuthHeaderE authHeader104) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Đăng ký thuê bao mới, quản lý thêm điểm giao dịch, trả trước, sau , MegaMYTV, BIDV, HCM, ND-TV
     * @param registerSubscriberV2_1Fiber
     * @param authHeader105
     */
    public org.tempuri.RegisterSubscriberV2_1FiberResponse registerSubscriberV2_1Fiber(
        org.tempuri.RegisterSubscriberV2_1Fiber registerSubscriberV2_1Fiber,
        org.tempuri.AuthHeaderE authHeader105) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Thay đổi thông tin cá nhân của khách hàng, Port
     * @param changeSubscriberV2
     * @param authHeader106
     */
    public org.tempuri.ChangeSubscriberV2Response changeSubscriberV2(
        org.tempuri.ChangeSubscriberV2 changeSubscriberV2,
        org.tempuri.AuthHeaderE authHeader106) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Thay đổi hình thức thanh toán trả trước, trả sau V3, bao kèm hình thức khuyến mãi
     * @param methodChangePayV3
     * @param authHeader107
     */
    public org.tempuri.MethodChangePayV3Response methodChangePayV3(
        org.tempuri.MethodChangePayV3 methodChangePayV3,
        org.tempuri.AuthHeaderE authHeader107) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * [B2B] Xóa phòng thuộc khách sạn, khi thuê bao chưa sử dụng
     * @param b2B_DeleteRoom
     * @param authHeader108
     */
    public org.tempuri.B2B_DeleteRoomResponse b2B_DeleteRoom(
        org.tempuri.B2B_DeleteRoom b2B_DeleteRoom,
        org.tempuri.AuthHeaderE authHeader108) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Thay đổi hình thức thanh toán trả trước, trả sau V2
     * @param methodChangePayV2
     * @param authHeader109
     */
    public org.tempuri.MethodChangePayV2Response methodChangePayV2(
        org.tempuri.MethodChangePayV2 methodChangePayV2,
        org.tempuri.AuthHeaderE authHeader109) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Cập nhật tài khoản chính phụ
     * @param updateUserParent
     * @param authHeader110
     */
    public org.tempuri.UpdateUserParentResponse updateUserParent(
        org.tempuri.UpdateUserParent updateUserParent,
        org.tempuri.AuthHeaderE authHeader110) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Đăng ký thuê bao mới 2018, đầy đủ thông tin
     * @param registerSubscriberV3
     * @param authHeader111
     */
    public org.tempuri.RegisterSubscriberV3Response registerSubscriberV3(
        org.tempuri.RegisterSubscriberV3 registerSubscriberV3,
        org.tempuri.AuthHeaderE authHeader111) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Truy vấn thông tin gói cước dịch vụ PayTV
     * @param getBillByContent
     * @param authHeader112
     */
    public org.tempuri.GetBillByContentResponse getBillByContent(
        org.tempuri.GetBillByContent getBillByContent,
        org.tempuri.AuthHeaderE authHeader112) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Truy vấn lịch sử sử dụng bộ giải mã
     * @param getHistorySTB
     * @param authHeader113
     */
    public org.tempuri.GetHistorySTBResponse getHistorySTB(
        org.tempuri.GetHistorySTB getHistorySTB,
        org.tempuri.AuthHeaderE authHeader113) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * [B2B] Đăng ký mới gói cước AddOn B2B cho khách sạn
     * @param b2B_Hotel_AddAddOn
     * @param authHeader114
     */
    public org.tempuri.B2B_Hotel_AddAddOnResponse b2B_Hotel_AddAddOn(
        org.tempuri.B2B_Hotel_AddAddOn b2B_Hotel_AddAddOn,
        org.tempuri.AuthHeaderE authHeader114) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Hủy các gói AddOn PayTV của MyTV
     * @param cancelAddOn
     * @param authHeader115
     */
    public org.tempuri.CancelAddOnResponse cancelAddOn(
        org.tempuri.CancelAddOn cancelAddOn,
        org.tempuri.AuthHeaderE authHeader115) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Đăng ký thuê bao mới, quản lý thêm điểm giao dịch, trả trước, sau , MegaMYTV, BIDV
     * @param registerSubscriberV2
     * @param authHeader116
     */
    public org.tempuri.RegisterSubscriberV2Response registerSubscriberV2(
        org.tempuri.RegisterSubscriberV2 registerSubscriberV2,
        org.tempuri.AuthHeaderE authHeader116) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Truy vấn thông tin các gói cước trong MyTV
     * @param getBillPackage
     * @param authHeader117
     */
    public org.tempuri.GetBillPackageResponse getBillPackage(
        org.tempuri.GetBillPackage getBillPackage,
        org.tempuri.AuthHeaderE authHeader117) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Xóa khách sạn
     * @param deleteHotel
     * @param authHeader118
     */
    public org.tempuri.DeleteHotelResponse deleteHotel(
        org.tempuri.DeleteHotel deleteHotel,
        org.tempuri.AuthHeaderE authHeader118) throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     * Đăng ký thuê bao mới, quản lý thêm điểm giao dịch, trả trước, sau , MegaMYTV, BIDV, HCM
     * @param registerSubscriberV2Fiber
     * @param authHeader119
     */
    public org.tempuri.RegisterSubscriberV2FiberResponse registerSubscriberV2Fiber(
        org.tempuri.RegisterSubscriberV2Fiber registerSubscriberV2Fiber,
        org.tempuri.AuthHeaderE authHeader119) throws java.rmi.RemoteException;

    //
}
