/**
 * LineTestWSSoap_PortType.java
 * <p>
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.cts;

public interface LineTestWSSoap_PortType extends java.rmi.Remote {

    /**
     * Login <br>
     *            Nếu có lỗi sẽ ra thông báo
     */
    void login(ws.client.cts.AuthHeaderWS authHeader) throws java.rmi.RemoteException;

    /**
     * Lấy thông tin ONT theo tài khoản Fiber<br>
     *
     * @Para string ProvinceCode Mã tỉnh <br>
     *
     * @Param string accountName Tên tài khoản<br>
     *
     * @Return DataTable: Manufactory (Nhà sx), Hardware, Firmware
     */
    ws.client.cts.GetONTInfoByAccountResponseGetONTInfoByAccountResult getONTInfoByAccount(java.lang.String provinceCode, java.lang.String accountName) throws java.rmi.RemoteException;

    /**
     * Reset xDSL Port Visa<br>
     *
     * @Param string visaString Chuỗi visa truyền vào, format: ip:frame/slot/port<br>
     *
     * @Return string
     */
    ws.client.cts.TaskOfString resetByPortVisa(java.lang.String visaString) throws java.rmi.RemoteException;

    /**
     * Reset xDSL port<br>
     *
     * @Param string deviceIP Địa chỉ IP của thiết bị<br>
     *
     * @Param int frame thông số frame<br>
     *
     * @Param int slot thông số slot<br>
     *
     * @Param int port thông số port<br>
     *
     * @Return string
     */
    ws.client.cts.TaskOfString resetDslPort(java.lang.String deviceIp, int frame, int slot, int port) throws java.rmi.RemoteException;

    /**
     * Đo một port xDSL chỉ định bằng Visa String<br>
     *
     * @Param string PortInfo Chuỗi visa truyền vào, format: SYSTEMID=:-:RACKID=:-:SELFID=:-:SLOT=:-:PORT=:-:VPI=:-:VCI=<br>
     *
     * @Return String
     */
    ws.client.cts.TestByPortVisaResponseTestByPortVisaResult testByPortVisa(java.lang.String portInfo) throws java.rmi.RemoteException;

    /**
     * Đo một port xDSL chỉ định<br>
     *
     * @Param string deviceIP Địa chỉ IP của thiết bị <br>
     *
     * @Param int frame thông số frame <br>
     *
     * @Param int slot thông số slot <br>
     *
     * @Param int port thông số port <br>
     *
     * @Return Dataset
     */
    ws.client.cts.TestByPortResponseTestByPortResult testByPort(java.lang.String deviceIp, int frame, int slot, int port) throws java.rmi.RemoteException;

    /**
     * Đo một ONT GPON chỉ định<br>
     *
     * @Param string PortInfo Chuỗi visa truyền vào, format: SYSTEMID=:-:RACKID=:-:SELFID=:-:SLOT=:-:PORT=:-:VPI=:-:VCI=<br>
     *
     * @Return Dataset
     */
    ws.client.cts.TestOnuByPortVisaResponseTestOnuByPortVisaResult testOnuByPortVisa(java.lang.String portInfo) throws java.rmi.RemoteException;

    ws.client.cts.TestOnuByAccountResponseTestOnuByAccountResult testOnuByAccount(java.lang.String accountName) throws java.rmi.RemoteException;

    /**
     * Đo một ONT GPON chỉ định<br>
     *
     * @Param string deviceIP Địa chỉ IP của thiết bị <br>
     *
     * @Param int frame thông số frame <br>
     *
     * @Param int slot thông số slot <br>
     *
     * @Param int port thông số port <br>
     *
     * @Param int onuIndex thông số onuIndex <br>
     *
     * @Return Dataset
     */
    ws.client.cts.TestOnuPortResponseTestOnuPortResult testOnuPort(java.lang.String deviceIp, int frame, int slot, int port, int onuIndex) throws java.rmi.RemoteException;

    /**
     * --DESC--<br>Lấy danh sách thuê bao FiberVNN không phát sinh
     * lưu lượng từ 
     * @fromDate đến
     * @toDate và có tổng traffic > 0 và < 
     * @backgroundTraffic<br>--INPUT--<br>@UnitID string: Mã đơn vị<br>@fromDate
     * DateTime: Ngày bắt đầu lấy dữ liệu<br>@toDate DateTime: Ngày kết thúc
     * lấy dữ liệu<br>@backgroundTraffic int: Ngưỡng trên traffic để lấy
     * dữ liệu<br>--OUTPUT--<br>Dataset<br>@StationName string: Tên trạm<br>@SystemName
     * string: Tên thiết bị<br>@IpAddress string: Địa chỉ IP của thiết bị<br>@PortIndex
     * string: Port visa<br>@AccountName string: Tên tài khoản<br>@SubName
     * string: Tên khách hàng<br>@SubAddress string: Địa chỉ khách hàng<br>@Traffic
     * long: Lưu lượng của thiết bị trong thời gian từ 
     * @fromDate đến
     * @toDate
     */
    ws.client.cts.GetNonTrafficFiberVNNResponseGetNonTrafficFiberVNNResult getNonTrafficFiberVNN(int unitID, java.util.Calendar fromDate, java.util.Calendar toDate, int backgroundTraffic) throws java.rmi.RemoteException;

    /**
     * Reset ONU connection<br>
     *
     * @Param string deviceIP Địa chỉ IP của thiết bị<br>
     *
     * @Param int frame thông số frame<br>
     *
     * @Param int slot thông số slot<br>
     *
     * @Param int port thông số port<br>
     *
     * @Param int onuIndex thông số onuIndex<br>
     *
     * @Return string
     */
    java.lang.String resetOnuConnection(java.lang.String deviceIp, int frame, int slot, int port, int onuIndex) throws java.rmi.RemoteException;

    /**
     * Reset ONU connection ByPortVisa<br>
     *
     * @Param string visaString Chuỗi visa truyền vào, format: ip:frame/slot/port/onuindex<br>
     *
     * @Return Dataset
     */
    java.lang.String resetOnuConnectionByPortVisa(java.lang.String visaString) throws java.rmi.RemoteException;

    /**
     * Tạo file danh sách account thuê bao gpon theo ngày và đơn vị,
     * sau khi gọi hàm này 15 phút sau gọi hàm GetUrlGponQualityDetailByUnit
     * để lấy đường dẫn lấy file <br>
     *
     * @Param long unitID ID đơn vị<br>
     *
     * @Param DateTime statisticDate Ngày thống kê<br>
     *
     * @Return: File excel gồm các cột <br>
     * 			 SystemName :  Tên hệ thống <br>
     * 			 PortIndex :  ONU <br>
     *              VendorID :  VendorID <br>
     *              SerialNo :  SerialNo <br>
     * 			 FirmWare :  FirmWare <br>
     * 			 Slid :  SLID<br>
     * 			 ProfileDS :  ProfileDS <br>
     * 			 ProfileUS :  ProfileUS <br>
     *              Distance :  Độ dài (m) <br>  :
     *              AttenDS : suy hao DOWN <br>
     *              AttenUS : suy hao UP <br>
     *              QosDS :  Đánh giá <br>
     * 			 FiberAccountName :  tài khoản FiberVNN <br>
     * 			 MyTVAccountName :  MyTV <br>
     * 			 FiberSubName :  Tên thuê bao thuê bao Fiber <br>
     * 			 FiberServiceStatus :  Trạng thái trên Visa<br>
     * 			 Mobile :  Điện thoại thuê bao Fiber <br>
     * 			 FiberSubAddress :  Địa chỉ thuê bao Fiber <br>
     * 			 ZoneName :  Địa bàn
     */
    void updateUrlGponQualityDetailByUnit(int unitID, java.util.Calendar statisticDate) throws java.rmi.RemoteException;

    /**
     * Lấy url file danh sách account thuê bao gpon theo ngày và đơn
     * vị<br>
     *
     * @Param long unitID ID đơn vị<br>
     *
     * @Return url file trả về có tên: UnitID_yyyyMMdd (năm tháng ngày lấy
     * số liệu)
     */
    java.lang.String getUrlQualityFile(int unitID) throws java.rmi.RemoteException;

    /**
     * Thay đổi Line Profile của DSLAM<br>
     *
     * @Param string deviceIP Địa chỉ IP của thiết bị <br>
     *
     * @Param int frame thông số frame <br>
     *
     * @Param int slot thông số slot <br>
     *
     * @Param int port thông số port <br>
     *
     * @Param string profileName tên profile đích<br>
     *
     * @Return string
     */
    java.lang.String changeDslPortProfile(java.lang.String deviceIp, int frame, int slot, int port, java.lang.String profileName) throws java.rmi.RemoteException;

    /**
     * Thay đổi Line Profile của DSLAM<br>
     *
     * @Param string visaString Chuỗi visa truyền vào, format: ip:frame/slot/port<br>
     *
     * @Param string profileName tên profile đích<br>
     *
     * @Return string
     */
    java.lang.String changeDslPortProfileByPortVisa(java.lang.String visaString, java.lang.String profileName) throws java.rmi.RemoteException;
}
