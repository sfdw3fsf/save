/**
 * SubscriberManagementSoap_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public interface SubscriberManagementSoap_PortType extends java.rmi.Remote {

    /**
     * Thay đổi thông tin GponCab
     */
    public ChangeSubscriberVO gponCab_Update(UpdateGponCabRequest objRequest) throws java.rmi.RemoteException;

    /**
     * Cập nhật đăng ký GponCab cho tài khoản MYTV
     */
    public ChangeSubscriberVO updateSubGponCab(UpdateSubGponCabRequest objRequest) throws java.rmi.RemoteException;

    /**
     * Khuyến mại STB, khai báo thông tin STB
     */
    public CommonVO STB_PROM_Register(STB_PromDTO stbDTO) throws java.rmi.RemoteException;

    /**
     * Khuyến mại STB, hoàn công STB
     */
    public CommonVO STB_PROM_Active(STB_Prom_ActiveDTO stbDTO) throws java.rmi.RemoteException;

    /**
     * Khuyến mại STB, thu hồi STB
     */
    public CommonVO STB_PROM_Back(STB_Prom_BackDTO stbDTO) throws java.rmi.RemoteException;

    /**
     * Khuyến mại STB, thống kê thuê bao PSC lũy kế
     */
    public CommonVO STB_PROM_Summary(STB_Prom_SummaryDTO stbDTO) throws java.rmi.RemoteException;

    /**
     * Khuyến mại STB, thống kê thuê bao PSC lũy kế theo CT Dùng thử
     * 3855
     */
    public CommonVO STB_PROM_Summary3855(STB_Prom_Summary3855DTO stbDTO) throws java.rmi.RemoteException;

    /**
     * Khuyến mại STB, chuyển lên dùng thật khi hết dùng thử
     */
    public CommonVO STB_PROM_Buy(STB_Prom_BuyDTO stbDTO) throws java.rmi.RemoteException;

    /**
     * Đăng ký thuê bao MyTV Hệ sinh thái - EcoSystem
     */
    public HstVO registerSubscriberHST(RegisterHstDTO hstDTO) throws java.rmi.RemoteException;

    /**
     * Thay đổi gói cước sử dụng dịch vụ
     */
    public CommonVO changePackageHST(ChangePackageHstDTO hstDTO) throws java.rmi.RemoteException;

    /**
     * Đăng ký mới khách sạn
     */
    public AddHotelVO addHotel(String strUserName, String strPassword, String strName, String strOwner, String strCard, String strAddress, String strSigndate, String strContractID, String strBillDate, String strMethod, String strPayMode, String strPackageID, String strProviceID, int iAccNumber) throws java.rmi.RemoteException;

    /**
     * Sửa thông tin khách sạn
     */
    public EditHotelVO editHotel(String strUsername, String strName, String strOwner, String strCard, String strAddress, String strSigndate, String strContractID, String strBillDate, String strPayMode, String strPackageID, int iAccNumber) throws java.rmi.RemoteException;

    /**
     * Xóa khách sạn
     */
    public DeleteHotelVO deleteHotel(String strUsername) throws java.rmi.RemoteException;

    /**
     * Thay đổi mật khẩu của thuê bao khách sạn
     */
    public ChangePWHotelVO changePWHotel(String strUsername, String oldPassword, String newPassword) throws java.rmi.RemoteException;

    /**
     * Thay đổi trạng thái khách sạn
     */
    public ChangeStatusHotelVO changeStatusHotel(String strUsername, String strStatus) throws java.rmi.RemoteException;

    /**
     * Thay đổi gói cước sử dụng dịch vụ 2013
     */
    public ChangePackageVO changePackageV3(ChangePackageRequest objRequest) throws java.rmi.RemoteException;

    /**
     * Thay đổi gói cước sử dụng dịch vụ
     */
    public ChangePackageVO changePackage(String IPTVAccount, String packageNo, String serviceCD, String serviceVAS, String reason) throws java.rmi.RemoteException;

    /**
     * Thay đổi gói cước sử dụng dịch vụ, gói tích hợp
     */
    public ChangePackageVO changePackageAndMegaVNN(String IPTVAccount, String packageNo, String serviceCD, String serviceVAS, String reason, String megaMyTV) throws java.rmi.RemoteException;

    /**
     * Thay đổi gói cước sử dụng dịch vụ, gói tích hợp, gói tích hợp
     * MyTV Combo
     */
    public ChangePackageVO changePackageAndMegaVNNV3(ChangePackageAndMegaVNNRequest objRequest) throws java.rmi.RemoteException;

    /**
     * Thay đổi gói cước tích hợp, gói tích hợp MyTV Combo không làm
     * thay đổi gói cước thuê bao
     */
    public ChangePackageVO changeMegaVNN(String IPTVAccount, String megaMyTV, String combo_FiberCD) throws java.rmi.RemoteException;

    /**
     * Thay đổi gói cước tích hợp, gói tích hợp MyTV Combo không làm
     * thay đổi gói cước thuê bao
     */
    public ChangePackageVO changeMegaVNNV3(ChangeMegaVNNRequest objReq) throws java.rmi.RemoteException;

    /**
     * Rời gói tích hợp cho các thuê bao MyTV đang tạm ngưng
     */
    public CommonVO cancelMegaForSuspendAccount(String username) throws java.rmi.RemoteException;

    /**
     * Truy vấn thông tin gói cước
     */
    public GetPackageNameVO getPackageName(String IPTVAccount) throws java.rmi.RemoteException;

    /**
     * Truy vấn danh sách khách hàng thay đổi gói cước trên EPG
     */
    public GetPackageEPGVO getPackageEPG(String starttime, String endtime, String IPTVAccount, String strAll) throws java.rmi.RemoteException;

    /**
     * Truy vấn thông tin mã đối tượng giảm trừ khuyến mại
     */
    public GetObjPromoCDVO getListObjPromoCD() throws java.rmi.RemoteException;

    /**
     * Cập nhật đăng ký thông tin mã đối tượng giảm trừ khuyến mại
     */
    public ChangeSubscriberVO updateObjPromoCD(UpdateObjPromoCDReq objRequest) throws java.rmi.RemoteException;

    /**
     * Cập mã khuyến mại HSBC cho HCM
     */
    public UpdatePromoVO updatePromotions(String IPTVACCOUNT, String promoCD) throws java.rmi.RemoteException;

    /**
     * Thay đổi mã hỗ trợ giá
     */
    public ChangeSubscriberVO changeID_STBDiscount(String IPTVACCOUNT, String sID_STB_Discount) throws java.rmi.RemoteException;

    /**
     * Thay đổi mã hỗ trợ giá dành cho VNPT HCM
     */
    public ChangeSubscriberVO changeFiberCD(String IPTVACCOUNT, String sFiberCD) throws java.rmi.RemoteException;

    /**
     * Thay đổi nhóm khách hàng ưu đãi
     */
    public ChangePromotionsVO changePromotions(String IPTVACCOUNT, String sPromo_ID) throws java.rmi.RemoteException;

    /**
     * Thay đổi nhóm khách hàng ưu đãi đăng ký tích hợp nhiều dịch
     * vụ
     */
    public ChangePromotionsVO changePromotionsV2(String IPTVACCOUNT, String sPromo_ID, String option) throws java.rmi.RemoteException;

    /**
     * Cập nhật đăng ký trả trước Khuyến Mại HCM
     */
    public ChangeSubscriberVO updatePrepaid(String myTVAccount, int iNo_Month) throws java.rmi.RemoteException;

    /**
     * Thêm mới điểm giao dịch
     */
    public DistrictVO addNewDistrict(String districtName, String provinceCD, String districtCD, String districtNote) throws java.rmi.RemoteException;

    /**
     * Thay đổi thông tin điểm giao dịch
     */
    public DistrictVO editDistrict(String districtCD, String districtName, String districtNote) throws java.rmi.RemoteException;

    /**
     * Xóa điểm giao dịch
     */
    public DistrictVO deleteDistrict(String districtID) throws java.rmi.RemoteException;

    /**
     * Thay đổi điểm giao dịch khách hàng
     */
    public ChangeDistrictSubscriberVO changeDistrictSubscriber(String IPTVACCOUNT, String districtNo) throws java.rmi.RemoteException;

    /**
     * Đăng ký thuê bao mới 2018, đầy đủ thông tin
     */
    public RegisterSubscriberResp registerSubscriberV3(RegisterSubscriberRequest objRequest) throws java.rmi.RemoteException;

    /**
     * Đăng ký thuê bao mới
     */
    public RegisterSubscriberVO registerSubscriber(String name, String birthday, String sex, String firstAdd, String secondAdd, String IDNo, String telNo, String cellNo, String areaCode, String email, String YM, String website, String faxNo, String contractCode, String IPTVAccount, String IPTVPW, String packageCode, String serviceCode, String serviceVASCode, String STBID, String status, String cateCode, String fixDate, String notFixReason, String contractSignDate, String loginAccount, String loginAcount_Password, String billAccount) throws java.rmi.RemoteException;

    /**
     * Đăng ký thuê bao mới
     */
    public RegisterSubscriberVO registerSubscriberPay(String name, String birthday, String sex, String firstAdd, String secondAdd, String IDNo, String telNo, String cellNo, String areaCode, String email, String YM, String website, String faxNo, String contractCode, String IPTVAccount, String IPTVPW, String packageCode, String serviceCode, String serviceVASCode, String STBID, String status, String cateCode, String fixDate, String notFixReason, String contractSignDate, String payment, String no_Month, String loginAccount, String loginAcount_Password, String billAccount) throws java.rmi.RemoteException;

    /**
     * Đăng ký thuê bao mới, quản lý thêm điểm giao dịch
     */
    public RegisterSubscriberVO registerSubscriberNew(String name, String birthday, String sex, String firstAdd, String secondAdd, String IDNo, String telNo, String cellNo, String areaCode, String districtID, String email, String YM, String website, String faxNo, String contractCode, String IPTVAccount, String IPTVPW, String packageCode, String serviceCode, String serviceVASCode, String STBID, String status, String cateCode, String fixDate, String notFixReason, String contractSignDate, String loginAccount, String loginAcount_Password, String billAccount) throws java.rmi.RemoteException;

    /**
     * Đăng ký thuê bao mới, quản lý thêm điểm giao dịch, trả trước,
     * sau , MegaMYTV
     */
    public RegisterSubscriberVO registerSubscriberFull(String name, String birthday, String sex, String firstAdd, String secondAdd, String IDNo, String telNo, String cellNo, String areaCode, String districtID, String email, String YM, String website, String faxNo, String contractCode, String IPTVAccount, String IPTVPW, String packageCode, String serviceCode, String serviceVASCode, String STBID, String status, String cateCode, String fixDate, String notFixReason, String contractSignDate, String payment, String no_Month, String loginAccount, String loginAcount_Password, String billAccount, String megaMyTV) throws java.rmi.RemoteException;

    /**
     * Đăng ký thuê bao mới, quản lý thêm điểm giao dịch, trả trước,
     * sau , MegaMYTV
     */
    public RegisterSubscriberVO registerSubscriberFull_Update(String name, String birthday, String sex, String firstAdd, String secondAdd, String IDNo, String telNo, String cellNo, String areaCode, String districtID, String email, String YM, String website, String faxNo, String contractCode, String IPTVAccount, String IPTVPW, String packageCode, String serviceCode, String serviceVASCode, String STBID, String status, String cateCode, String fixDate, String notFixReason, String contractSignDate, String payment, String no_Month, String loginAccount, String loginAcount_Password, String billAccount, String megaMyTV, String sPortDSL) throws java.rmi.RemoteException;

    /**
     * Đăng ký thuê bao mới, quản lý thêm điểm giao dịch, trả trước,
     * sau , MegaMYTV, BIDV
     */
    public RegisterSubscriberVO registerSubscriberV2(String name, String birthday, String sex, String firstAdd, String secondAdd, String IDNo, String telNo, String cellNo, String areaCode, String districtID, String email, String YM, String website, String faxNo, String contractCode, String IPTVAccount, String IPTVPW, String packageCode, String serviceCode, String serviceVASCode, String STBID, String status, String cateCode, String fixDate, String notFixReason, String contractSignDate, String payment, String no_Month, String loginAccount, String loginAcount_Password, String billAccount, String megaMyTV, String sPortDSL, String sPromo_id) throws java.rmi.RemoteException;

    /**
     * Đăng ký mới thuê bao, quản lý thêm điểm giao dịch, trả trước
     * trả sau, MegaVNN , Thêm Port, BIDV, ND-TV
     */
    public RegisterSubscriberVO registerSubscriberV2_1(String name, String birthday, String sex, String firstAdd, String secondAdd, String IDNo, String telNo, String cellNo, String areaCode, String districtID, String email, String YM, String website, String faxNo, String contractCode, String IPTVAccount, String IPTVPW, String packageCode, String serviceCode, String serviceVASCode, String STBID, String status, String cateCode, String fixDate, String notFixReason, String contractSignDate, String payment, String no_Month, String loginAccount, String loginAcount_Password, String billAccount, String megaMyTV, String sPortDSL, String sPromo_id, String sNDTV_CD) throws java.rmi.RemoteException;

    /**
     * Đăng ký thuê bao mới, quản lý thêm điểm giao dịch, trả trước,
     * sau , MegaMYTV, BIDV, KGG, ND-TV, Fiber MyTV B2B
     */
    public RegisterSubscriberVO registerSubscriberV2_1KGG(String name, String birthday, String sex, String firstAdd, String secondAdd, String IDNo, String telNo, String cellNo, String areaCode, String districtID, String email, String YM, String website, String faxNo, String contractCode, String IPTVAccount, String IPTVPW, String packageCode, String serviceCode, String serviceVASCode, String STBID, String status, String cateCode, String fixDate, String notFixReason, String contractSignDate, String payment, String no_Month, String loginAccount, String loginAcount_Password, String billAccount, String megaMyTV, String sPortDSL, String sPromo_id, String sID_STB_Discount, String sNDTV_CD, String sMultiCD) throws java.rmi.RemoteException;

    /**
     * Đăng ký thuê bao mới, quản lý thêm điểm giao dịch, trả trước,
     * sau , MegaMYTV, BIDV, HNI
     */
    public RegisterSubscriberVO registerSubscriberV2HNI(String name, String birthday, String sex, String firstAdd, String secondAdd, String IDNo, String telNo, String cellNo, String areaCode, String districtID, String email, String YM, String website, String faxNo, String contractCode, String IPTVAccount, String IPTVPW, String packageCode, String serviceCode, String serviceVASCode, String STBID, String status, String cateCode, String fixDate, String notFixReason, String contractSignDate, String payment, String no_Month, String loginAccount, String loginAcount_Password, String billAccount, String megaMyTV, String sPortDSL, String sPromo_id, String sID_STB_Discount) throws java.rmi.RemoteException;

    /**
     * Đăng ký thuê bao mới, quản lý thêm điểm giao dịch, trả trước,
     * sau , MegaMYTV, BIDV, HNI, ND-TV, Fiber MyTV B2B
     */
    public RegisterSubscriberVO registerSubscriberV2_1HNI(String name, String birthday, String sex, String firstAdd, String secondAdd, String IDNo, String telNo, String cellNo, String areaCode, String districtID, String email, String YM, String website, String faxNo, String contractCode, String IPTVAccount, String IPTVPW, String packageCode, String serviceCode, String serviceVASCode, String STBID, String status, String cateCode, String fixDate, String notFixReason, String contractSignDate, String payment, String no_Month, String loginAccount, String loginAcount_Password, String billAccount, String megaMyTV, String sPortDSL, String sPromo_id, String sID_STB_Discount, String sNDTV_CD, String sFiberB2BCD) throws java.rmi.RemoteException;

    /**
     * Đăng ký thuê bao mới, quản lý thêm điểm giao dịch, trả trước,
     * sau , MegaMYTV, BIDV, HCM
     */
    public RegisterSubscriberVO registerSubscriberV2Fiber(String name, String birthday, String sex, String firstAdd, String secondAdd, String IDNo, String telNo, String cellNo, String areaCode, String districtID, String email, String YM, String website, String faxNo, String contractCode, String IPTVAccount, String IPTVPW, String packageCode, String serviceCode, String serviceVASCode, String STBID, String status, String cateCode, String fixDate, String notFixReason, String contractSignDate, String payment, String no_Month, String loginAccount, String loginAcount_Password, String billAccount, String megaMyTV, String sPortDSL, String sPromo_id, String sFiberCD) throws java.rmi.RemoteException;

    /**
     * Đăng ký thuê bao mới, quản lý thêm điểm giao dịch, trả trước,
     * sau , MegaMYTV, BIDV, HCM, ND-TV
     */
    public RegisterSubscriberVO registerSubscriberV2_1Fiber(String name, String birthday, String sex, String firstAdd, String secondAdd, String IDNo, String telNo, String cellNo, String areaCode, String districtID, String email, String YM, String website, String faxNo, String contractCode, String IPTVAccount, String IPTVPW, String packageCode, String serviceCode, String serviceVASCode, String STBID, String status, String cateCode, String fixDate, String notFixReason, String contractSignDate, String payment, String no_Month, String loginAccount, String loginAcount_Password, String billAccount, String megaMyTV, String sPortDSL, String sPromo_id, String sFiberCD, String sNDTV_CD) throws java.rmi.RemoteException;

    /**
     * Thay đổi thông tin cá nhân của khách hàng 2013
     */
    public ChangeSubscriberVO changeSubscriberV3(ChangeSubscriberRequest objRequset) throws java.rmi.RemoteException;

    /**
     * Thay đổi thông tin cá nhân của khách hàng
     */
    public ChangeSubscriberVO changeSubscriber(String name, String birthday, String sex, String firstAdd, String secondAdd, String IDNo, String telNo, String cellNo, String areaCode, String email, String YM, String website, String faxNo, String IPTVAccount) throws java.rmi.RemoteException;

    /**
     * Thay đổi thông tin cá nhân của khách hàng, Port
     */
    public ChangeSubscriberVO changeSubscriberV2(String portDSL, String IPTVAccount) throws java.rmi.RemoteException;

    /**
     * Tạm ngưng sử dụng dịch vụ của khách hàng
     */
    public SuspendSubscriberVO suspendSubscriber(String IPTVAccount, String reason, String operator) throws java.rmi.RemoteException;

    /**
     * Tạm ngưng sử dụng dịch vụ của khách hàng theo Mã lý do
     */
    public SuspendSubscriberVO suspendSubscriberV2(String IPTVAccount, int reasonID) throws java.rmi.RemoteException;

    /**
     * Hủy sử dụng dịch vụ IPTV
     */
    public CancelSubscriberVO cancelSubscriber(String IPTVAccount, String reason) throws java.rmi.RemoteException;

    /**
     * Hủy sử dụng dịch vụ IPTV theo Mã lý do hủy
     */
    public CancelSubscriberVO cancelSubscriberV2(String IPTVAccount, int reasonID) throws java.rmi.RemoteException;

    /**
     * Khôi phục lại sử dụng dịch vụ cho khách hàng
     */
    public ResumeSubscriberVO resumeSubscriber(String IPTVAccount, String reason) throws java.rmi.RemoteException;

    /**
     * Truy vấn thông tin khách hàng
     */
    public GetSubscriberInfoVO getSubscriberInfo(String IPTVAccount) throws java.rmi.RemoteException;

    /**
     * Truy vấn thông tin khách hàng thêm điều kiện khác
     */
    public GetSubscriberVO getSubscriberInfoV2(GetSubscriberRequest objRequest) throws java.rmi.RemoteException;

    /**
     * Tra cứu username theo mã STB
     */
    public String[] getSubscriberBySTB_SN(String serialNumber) throws java.rmi.RemoteException;

    /**
     * Truy vấn thông tin khách hàng: Tivi 1,2 tình trạng STB
     */
    public GetSubscriberV3InfoVO getSubscriberInfoV3(String username) throws java.rmi.RemoteException;

    /**
     * Truy vấn thông tin khách hàng: Tivi 1,2 tình trạng STB
     */
    public GetSubscriberV3InfoVO[] getSubscriberInfoV3_List(String usernames) throws java.rmi.RemoteException;

    /**
     * Truy vấn thông tin khách hàng: Tivi 1,2 tình trạng STB
     */
    public String getSubscriberInfoV3_String(String usernames) throws java.rmi.RemoteException;

    /**
     * Truy vấn danh sách thông tin khách hàng
     */
    public GetListSubscriberVO getListSubscriber() throws java.rmi.RemoteException;

    /**
     * Thay đổi hệ thống MyTV - VMP
     */
    public ChangeSystemResp changeSystem(ChangeSystemReq objRequest) throws java.rmi.RemoteException;

    /**
     * Cập nhật tài khoản chính phụ
     */
    public ChangeSubscriberVO updateUserParent(UpdateUserParentReq objRequest) throws java.rmi.RemoteException;

    /**
     * Lấy thông tin tài khoản chính/phụ
     */
    public GetListSubscriberVO getUserParent(String iptvAccount) throws java.rmi.RemoteException;

    /**
     * Thay đổi mã hợp đồng
     */
    public ChangeContractVO changeContract(String IPTVACCOUNT, String contractNo) throws java.rmi.RemoteException;

    /**
     * Đăng ký thuê bao theo chương trình SelfCare
     */
    public CommonVO registerSelfCare(SelfCareDTO scDTO) throws java.rmi.RemoteException;

    /**
     * Active thuê bao SelfCare
     */
    public CommonVO activeSelfCare(ActiveSelfCareDTO scDTO) throws java.rmi.RemoteException;

    /**
     * Tra cứu thông tin mã lỗi
     */
    public String errorSolution(String error_code, String error_keyword) throws java.rmi.RemoteException;

    /**
     * Thông tin lịch sử đăng ký các gói AddOn/PayTV
     */
    public String addonPayTVInfo(String username, String service_cd) throws java.rmi.RemoteException;

    /**
     * Danh sách các gói AddOn/PayTV đăng ký trong tháng
     */
    public String addonPayTvInMonth(String username, String month) throws java.rmi.RemoteException;

    /**
     * Thông tin lịch sử login gần nhất
     */
    public String loginHistory(String username) throws java.rmi.RemoteException;

    /**
     * Reset CCU
     */
    public String resetCCU(String username) throws java.rmi.RemoteException;

    /**
     * Check CCU
     */
    public String checkCCU(String username) throws java.rmi.RemoteException;

    /**
     * Reset mật khẩu khóa trẻ em
     */
    public String resetPasswordLockChildren(String username, String lock_password) throws java.rmi.RemoteException;

    /**
     * Danh sách thiết bị đang chạy K+
     */
    public String getDeviceKplus(String username) throws java.rmi.RemoteException;

    /**
     * Unbind K+ - Xóa thiết bị đang chạy K+ của thuê bao
     */
    public String unbindKplus(String username) throws java.rmi.RemoteException;

    /**
     * Ubind thiết bị - Thay đổi bộ giải mã
     */
    public ChangeSTBVO unbindDevice(String username, String deviceCode, String areaCode) throws java.rmi.RemoteException;

    /**
     * Thay đổi bộ giải mã
     */
    public ChangeSTBVO changeSTB(String IPTVAccount, String reason) throws java.rmi.RemoteException;

    /**
     * Truy vấn thông tin bộ giải mã
     */
    public GetSTBInfoVO getSTBInfo(String IPTVAccount) throws java.rmi.RemoteException;

    /**
     * Truy vấn thông tin thiet bi dau cuoi
     */
    public String getDeviceInfo(String usernames) throws java.rmi.RemoteException;

    /**
     * Truy vấn thông tin bộ giải mã chi tiết
     */
    public StbInfoVO getSTBInfor(String stbid) throws java.rmi.RemoteException;

    /**
     * Truy vấn lịch sử sử dụng bộ giải mã
     */
    public GetSTBHistoryInfoVO getHistorySTB(String stbid) throws java.rmi.RemoteException;

    /**
     * Truy  vấn lịch sử sử thay đổi bộ giải mã của một tài khoản
     */
    public GetSTBHistoryInfoVO getHisSubscriberChangeSTB(String strMyTV) throws java.rmi.RemoteException;

    /**
     * Cập nhật mã thiết bị
     */
    public ChangeSubscriberVO updateDeviceInfo(String myTVAccount, String strDeviceName, String strPortID) throws java.rmi.RemoteException;

    /**
     * Cập nhật thiết bị do khách hàng tự trang bị hay do VNPT cung
     * cấp
     */
    public CommonVO STB_Rent_UpdateInfo(STBRentDTO stbDTO) throws java.rmi.RemoteException;

    /**
     * Cập nhật thuê bao thứ 2 theo kịch bản 3 gói cước mới 2019:
     * Chuẩn/Nâng cao/VIP
     */
    public CommonVO cust_UserMulti_UpdateInfo(CustUserMultiDTO custDTO) throws java.rmi.RemoteException;

    /**
     * Cập nhật thuê dùng thử theo kịch bản 3 gói cước mới 2019: Chuẩn/Nâng
     * cao/VIP
     */
    public CommonVO cust_Trial_UpdateInfo(CustTrialDTO custDTO) throws java.rmi.RemoteException;

    /**
     * Truy vấn thông tin khóa/mở gói PayTV
     */
    public StatusBlockPayTVInfor getStatusBlockPayTV(String iptvaccount) throws java.rmi.RemoteException;

    /**
     * Block PayTV V2
     */
    public BlockPayTVResp blockPayTV(BlockPayTVRequest objRequest) throws java.rmi.RemoteException;

    /**
     * Khóa mở K+, 2018 không sử dụng nữa, chuyển sang BlockPayTV
     */
    public ChangeSubscriberVO updateKplusInfo(String myTVAccount, String strOperator) throws java.rmi.RemoteException;

    /**
     * Đăng ký/hủy gói MyTV Plus
     */
    public ChangeSubscriberVO totalMyTVPlus(MyTVPlusRequest objRequest) throws java.rmi.RemoteException;

    /**
     * Đăng ký các gói AddOn PayTV của MyTV
     */
    public CommonVO registAddOn(RegistAddOnDTO addonDTO) throws java.rmi.RemoteException;

    /**
     * Hủy các gói AddOn PayTV của MyTV
     */
    public CommonVO cancelAddOn(CancelAddOnDTO addonDTO) throws java.rmi.RemoteException;

    /**
     * Truy  vấn lịch sử đăng ký, hủy gói Kplus, HBO, VTVcab, fafilm
     */
    public GetHisPayTVResp getHisPayTV(String strMyTV) throws java.rmi.RemoteException;

    /**
     * Đăng ký/Hủy khuyến mại Combo-FAF
     */
    public ChangeSubscriberVO comboFAF_Update(String myTVAccount, int monthFree, String operator) throws java.rmi.RemoteException;

    /**
     * Đăng ký khuyến mại mua PayTV trọn gói
     */
    public ChangeSubscriberVO payTVFull_Register(String myTVAccount, int month) throws java.rmi.RemoteException;

    /**
     * Đăng ký/hủy gói K+ addon
     */
    public CommonVO registKplus(KplusDTO kplusDTO) throws java.rmi.RemoteException;

    /**
     * Thay đổi mã ghép cước
     */
    public ChangeBillCodeVO changeBillCode(String myTVAccount, String strBillcodeNew) throws java.rmi.RemoteException;

    /**
     * Thay đổi ngày tính cước
     */
    public ChangeDateBillVO changeDateBill(String myTVAccount, String strDateBillNew) throws java.rmi.RemoteException;

    /**
     * Truy vấn thông tin gói cước của tháng
     */
    public GetBillByMonthVO getBillByMonth(String IPTVAccount, String month) throws java.rmi.RemoteException;

    /**
     * Truy vấn thông tin gói cước dịch vụ PayTV
     */
    public GetBillByContentVO getBillByContent(String IPTVAccount, String fromDate, String endDate) throws java.rmi.RemoteException;

    /**
     * Truy vấn thông tin các gói cước trong MyTV
     */
    public GetPackageVO getBillPackage() throws java.rmi.RemoteException;

    /**
     * [B2B] Đăng ký mới khách sạn
     */
    public B2BCommonVO b2B_AddHotel(B2B_HotelDTO hotelDTO) throws java.rmi.RemoteException;

    /**
     * [B2B] Sửa thông tin khách sạn
     */
    public B2BCommonVO b2B_EditHotel(B2B_HotelDTO hotelDTO) throws java.rmi.RemoteException;

    /**
     * [B2B] Xóa khách sạn
     */
    public B2BCommonVO b2B_DeleteHotel(String code) throws java.rmi.RemoteException;

    /**
     * [B2B] Đăng ký mới phòng thuộc khách sạn
     */
    public B2BCommonVO b2B_AddRoom(B2B_RoomDTO roomDTO) throws java.rmi.RemoteException;

    /**
     * [B2B] Xóa phòng thuộc khách sạn, khi thuê bao chưa sử dụng
     */
    public B2BCommonVO b2B_DeleteRoom(B2B_RoomDTO roomDTO) throws java.rmi.RemoteException;

    /**
     * [B2B] Đăng ký mới gói cước B2B
     */
    public B2BCommonVO b2B_Global_AddPackage(B2B_PackageDTO packDTO) throws java.rmi.RemoteException;

    /**
     * [B2B] Đăng ký mới gói cước AddOn B2B
     */
    public B2BCommonVO b2B_Global_AddAddOn(B2B_AddOnDTO packDTO) throws java.rmi.RemoteException;

    /**
     * [B2B] Đăng ký mới gói cước B2B cho khách sạn
     */
    public B2BCommonVO b2B_Hotel_AddPackage(B2B_HotelPackageDTO packDTO) throws java.rmi.RemoteException;

    /**
     * [B2B] Đăng ký mới gói cước AddOn B2B cho khách sạn
     */
    public B2BCommonVO b2B_Hotel_AddAddOn(B2B_HotelAddOnDTO packDTO) throws java.rmi.RemoteException;

    /**
     * [B2B] Đăng ký mới gói cước AddOn B2B cho khách sạn
     */
    public B2BCommonVO b2B_Room_RegistAddOn(B2B_RoomAddOnDTO rAddOnDTO) throws java.rmi.RemoteException;

    /**
     * [B2B] Hủy gói cước AddOn B2B cho phòng thuộc khách sạn
     */
    public B2BCommonVO b2B_Room_CancelAddOn(B2B_RoomAddOnDTO rAddOnDTO) throws java.rmi.RemoteException;

    /**
     * Thay đổi mật khẩu của thuê bao MyTV
     */
    public ChangePasswordVO changePassword(String IPTVAccount, String oldPassword, String newPassword) throws java.rmi.RemoteException;

    /**
     * Kiểm tra mat khau cua thue bao MyTV
     */
    public ValidPasswordVO validPassword(String IPTVAccount, String oldPassword) throws java.rmi.RemoteException;

    /**
     * Lấy lại mật khẩu MyTV
     */
    public ResetPasswordVO resetPassword(String IPTVAccount) throws java.rmi.RemoteException;

    /**
     * Thay đổi mật khẩu của thành viên
     */
    public ChangeUserPassVO changeUserPassword(String strUserName, String strOldPassword, String strNewPassword) throws java.rmi.RemoteException;

    /**
     * Xóa thông tin khách hàng
     */
    public DeleteMyTVVO deleteMyTV(String IPTVAccount) throws java.rmi.RemoteException;

    /**
     * Cập nhật đối tượng khách hàng dùng thử theo CV
     */
    public ChangeSubscriberVO updateSubTrial(UpdateSubTrialRequest objRequest) throws java.rmi.RemoteException;

    /**
     * Cập nhật gói cước daily
     */
    public ChangeSubscriberVO updatePackDaily(String myTVAccount, String operator) throws java.rmi.RemoteException;

    /**
     * Cập nhật mã gói cước FiberVNN
     */
    public ChangeFiberVNNVO changeFiberVNN(String IPTVACCOUNT, String fiberVNN) throws java.rmi.RemoteException;

    /**
     * Cập nhật tài khoản sang nhóm truyền hình hội nghị
     */
    public ChangeSubscriberVO updateUserConference(String IPTVAccount, String operator) throws java.rmi.RemoteException;

    /**
     * Thay đổi hình thức thanh toán trả trước, trả sau
     */
    public MethodChangePayVO methodChangePay(String IPTVACCOUNT, String method, String no_Month) throws java.rmi.RemoteException;

    /**
     * Thay đổi hình thức thanh toán trả trước, trả sau V2
     */
    public MethodChangePayVO methodChangePayV2(String IPTVACCOUNT, String method, String no_Month, String sStartDate) throws java.rmi.RemoteException;

    /**
     * Thay đổi hình thức thanh toán trả trước, trả sau V3, bao kèm
     * hình thức khuyến mãi
     */
    public MethodChangePayVO methodChangePayV3(MethodChangePayRequest objRequest) throws java.rmi.RemoteException;

    /**
     * Đăng ký khuyến mại gói năm 2021: K+, Danet
     */
    public CommonVO registerKplusPromotion2021(String username, String mobile, String area_code, String pack_cd, String pack_name, boolean renew) throws java.rmi.RemoteException;

    /**
     * Đăng ký khuyến mại gói năm 2021: K+
     */
    public CommonVO registerKplusPromotion2021_V2(String username, String mobile, String area_code, String pack_cd, String addon_cd, String pack_name, boolean renew) throws java.rmi.RemoteException;

    /**
     * Lấy danh sách yêu cầu đăng ký app mạng trong
     */
    public Smart_OTTGetMemberPendingVO smart_OTTGetMemberPending(Smart_OTTGetMemberPendingRequest req) throws java.rmi.RemoteException;

    /**
     * Đăng ký tài khoản GponCab không gắn với MyTV
     */
    public ChangeSubscriberVO gponCab_RegistWithoutMyTV(GponCabRequest objRequest) throws java.rmi.RemoteException;
}
