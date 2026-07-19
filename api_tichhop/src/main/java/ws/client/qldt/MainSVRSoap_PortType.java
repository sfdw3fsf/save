/**
 * MainSVRSoap_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.qldt;

public interface MainSVRSoap_PortType extends java.rmi.Remote {
    public String helloWorld() throws java.rmi.RemoteException;
    public String get_Cat_Department(String sUserID, String sPass) throws java.rmi.RemoteException;
    public String get_ProjectbyInvestmentField(String sUserID, String sPass) throws java.rmi.RemoteException;
    public String get_Cat_Staff(String sUserID, String sPass) throws java.rmi.RemoteException;
    public String get_Cat_PositionLevel(String sUserID, String sPass) throws java.rmi.RemoteException;
    public String add_Cat_Staff(String sUserID, String sPass, String sUserTTT, String iDepartID, String iDevisionID, String iPositionID, String sDisplayName, String sMobile, String sEmail) throws java.rmi.RemoteException;
    public String edit_Cat_Staff(String sUserID, String sPass, String sUserTTT, String iDepartID, String iDevisionID, String iPositionID, String sDisplayName, String sMobile, String sEmail) throws java.rmi.RemoteException;
    public String add_Cat_Department(String sUserID, String sPass, String sDepartmentName, String sParentTD) throws java.rmi.RemoteException;
    public String edit_Cat_Department(String sUserID, String sPass, String iDepartmentID, String sDepartmentName, String sParentTD) throws java.rmi.RemoteException;
    public String get_Cat_Tinh(String sUserID, String sPass) throws java.rmi.RemoteException;
    public String get_ProjectbyProjectYear_Tinh(String sUserID, String sPass, String iProjectYear, String iDepartmentID) throws java.rmi.RemoteException;
    public String get_ProjectbyProjectID(String sUserID, String sPass, String iProjectID) throws java.rmi.RemoteException;
    public String get_BudgetNoticebyProjectID_Year(String sUserID, String sPass, String iProjectID, String iYear) throws java.rmi.RemoteException;
    public String get_ProjectDecisionbyProjectID(String sUserID, String sPass, String iProjectID) throws java.rmi.RemoteException;
    public String get_ContractInfobyProjectID(String sUserID, String sPass, String iProjectID) throws java.rmi.RemoteException;
    public String get_DepreciationbyProjectID(String sUserID, String sPass, String iProjectID) throws java.rmi.RemoteException;
    public String get_ProjectbyProjectCode_Tinh(String sUserID, String sPass, String sStartDate, String sEndDate, String sDepartmentCode, String sSubDepartmentCode, String sProjectCode, String sBudgetSource) throws java.rmi.RemoteException;
    public String get_ProjectDecisionbyProjectCode_Tinh(String sUserID, String sPass, String sStartDate, String sEndDate, String sDepartmentCode, String sSubDepartmentCode, String sProjectCode, String sBudgetSource) throws java.rmi.RemoteException;
    public String get_ContractInfobyProjectCode_Tinh(String sUserID, String sPass, String sDepartmentCode, String sProjectCode) throws java.rmi.RemoteException;
    public String get_DepreciationbyProjectCode_Tinh(String sUserID, String sPass, String sDepartmentCode, String sProjectCode) throws java.rmi.RemoteException;
    public String get_BudgetNoticebyProjectCode_Year(String sUserID, String sPass, String sProjectCode, String iYear) throws java.rmi.RemoteException;
    public String get_ConfirmbyProjectCode_Year(String sUserID, String sPass, String sProjectCode, String iYear) throws java.rmi.RemoteException;
    public String check_User(String sUserID, String sPass, String sAcc) throws java.rmi.RemoteException;
    public String get_Staff(String sUserID, String sPass, String sUserName) throws java.rmi.RemoteException;
    public String get_User_Permission(String sUserID, String sPass, String sUserName) throws java.rmi.RemoteException;
    public String get_Vendor(String sUserID, String sPass) throws java.rmi.RemoteException;
    public String get_Projectby_SuppliesPlan(String sUserID, String sPass, String iSuppliesPlanID, String iYear, String iManagementCompanyID) throws java.rmi.RemoteException;
    public String get_ContractInfoby_Project_SP(String sUserID, String sPass, String iProjectID, String iVendorID, String iManagementCompanyID) throws java.rmi.RemoteException;
    public String get_ContractInfo_SP(String sUserID, String sPass, String iContractInfoID) throws java.rmi.RemoteException;
    public String get_SubContractInfo(String sUserID, String sPass, String iContractInfoID) throws java.rmi.RemoteException;
    public String get_Suppliesby_ContractInfo(String sUserID, String sPass, String iContractInfoID) throws java.rmi.RemoteException;
    public String get_SuppliesGroup(String sUserID, String sPass) throws java.rmi.RemoteException;
    public String get_Attachmentby_ContractInfo_SP(String sUserID, String sPass, String iContractInfoID) throws java.rmi.RemoteException;
    public String get_BaoCaoMSTT_ThoiGianHoanThanh_Cot21_22(String sUserID, String sPass, String iContractInfoID) throws java.rmi.RemoteException;
    public String get_BaoCaoMSTT_ThoiGianHoanThanh_Cot23_27(String sUserID, String sPass, String iProjectId) throws java.rmi.RemoteException;
    public String get_VendorDetail(String sUserID, String sPass, String iVendorId) throws java.rmi.RemoteException;
    public String get_BudgetNotice(String sUserID, String sPass, String sDepartmentCode, String sSubDepartmentCode, String iYear, String sBudget) throws java.rmi.RemoteException;
    public String get_ContractInfo(String sUserID, String sPass, String sDepartmentCode, String sSubDepartmentCode, String sProjectCode) throws java.rmi.RemoteException;
    public String get_ProjectBalanceGetApproval(String sUserID, String sPass, String sStartDate, String sEndDate, String sDepartmentCode, String sSubDepartmentCode, String sProjectCode) throws java.rmi.RemoteException;
    public String get_ListProject(String sUserID, String sPass, String sDepartmentCode) throws java.rmi.RemoteException;
    public String get_ProjectbyProjectCode(String sUserID, String sPass, String sProjectCode) throws java.rmi.RemoteException;
    public String get_ProjectTaskbyProjectCode(String sUserID, String sPass, String sProjectCode) throws java.rmi.RemoteException;
    public String get_ProjectDecisionbyProjectCode(String sUserID, String sPass, String sProjectCode) throws java.rmi.RemoteException;
    public String get_AccumulatebyProjectCode(String sUserID, String sPass, String sProjectCode) throws java.rmi.RemoteException;
    public String get_ProjectTaskProjectbyProjectID(String sUserID, String sPass, String iProjectID) throws java.rmi.RemoteException;
    public String get_BudgetNoticeProjectbyProjectID(String sUserID, String sPass, String iProjectID) throws java.rmi.RemoteException;
    public String get_BiddingAdvicePlantbyProjectID(String sUserID, String sPass, String iProjectID) throws java.rmi.RemoteException;
    public String get_SignOffContractbyProjectID(String sUserID, String sPass, String iProjectID) throws java.rmi.RemoteException;
    public String get_ProjectDecisionProjectbyProjectID(String sUserID, String sPass, String iProjectID) throws java.rmi.RemoteException;
    public String get_ProjectTechnicalReportbyProjectID(String sUserID, String sPass, String iProjectID) throws java.rmi.RemoteException;
    public String get_BiddingFolderbyProjectID(String sUserID, String sPass, String iProjectID) throws java.rmi.RemoteException;
    public String get_BiddingResultbyProjectID(String sUserID, String sPass, String iProjectID) throws java.rmi.RemoteException;
    public String get_AcceptedMilestonebyProjectID(String sUserID, String sPass, String iProjectID) throws java.rmi.RemoteException;
    public String get_SignOffContractPrimierbyProjectID(String sUserID, String sPass, String iProjectID) throws java.rmi.RemoteException;
    public String get_BiddingPackge(String sUserID, String sPass, String sStartDate, String sEndDate, String sDepartmentCode, String sProjectCode) throws java.rmi.RemoteException;
    public String get_UserbyGroup(String sUserID, String sPass, String iGroupID, String iDepartment) throws java.rmi.RemoteException;
    public String get_BiddingPackge_MSCP(String sUserID, String sPass, String iProjectID) throws java.rmi.RemoteException;
    public String get_ProjectBalanceGetApproval_byProjectCode(String sUserID, String sPass, String sProjectCode) throws java.rmi.RemoteException;
    public String get_BiddingPackage_AcceptanceMilestone_byProjectCode(String sUserID, String sPass, String sProjectCode) throws java.rmi.RemoteException;
    public String get_ContractInfo_byBiddingPackageCode(String sUserID, String sPass, String sBiddingPackageCode) throws java.rmi.RemoteException;
    public String get_AssetInfo_byProjectCode(String sUserID, String sPass, String sProjectCode) throws java.rmi.RemoteException;
    public String get_AssetDetails_byAssetGroupCode(String sUserID, String sPass, String sAssetGroupCode) throws java.rmi.RemoteException;
    public String get_Asset_byProjectID(String sUserID, String sPass, String sProjectCode, String iAssetType) throws java.rmi.RemoteException;
    public String get_Confirm(String sUserID, String sPass, String sDepartmentCode, String sSubDepartmentCode, String iYear, String sBudget) throws java.rmi.RemoteException;
    public String get_AssetCode_Psis(String sUserID, String sPass, String iContractInfoId, String iIsCable, String iAmount, String sSys) throws java.rmi.RemoteException;
    public String get_AssetCode_Psis_by_ContractInfo(String sUserID, String sPass, String iContractInfoId, String sSys) throws java.rmi.RemoteException;
    public String get_BSCI21(String sUserID, String sPass, String iMonth, String iYear, String sSys) throws java.rmi.RemoteException;
    public String get_AssetDetails_byHistory(String sUserID, String sPass, String sAssetCode) throws java.rmi.RemoteException;
    public String get_Asset_byKeys(String sUserID, String sPass, String sProjectCode, String sContractNumber, String sPONumber, String sStation) throws java.rmi.RemoteException;
    public String get_AcceptanceGeneralList_byProjectCode(String sUserID, String sPass, String sProjectCode) throws java.rmi.RemoteException;
    public String get_ContractInfo_byProjectCode_Station(String sUserID, String sPass, String sProjectCode, String sStation) throws java.rmi.RemoteException;
    public String get_EstimationProjectbyProjectCode_Tinh(String sUserID, String sPass, String sStartDate, String sEndDate, String sDepartmentCode, String sSubDepartmentCode, String sProjectCode, String sBudgetSource) throws java.rmi.RemoteException;
    public String get_BudgetNotice_Sum_byTinh(String sUserID, String sPass, String sDepartmentCode, String iYear, String sBudget) throws java.rmi.RemoteException;
    public String getUp_AcceptanceGeneralListFromAsset(String sUserID, String sPass, String sSys, String sProjectcode, String sContractNumber, String sSOPHIEU, java.util.Calendar dNGAY_CT, String sMA_DV_QL, String sMA_DV_SD, String sMA_CSHT, String sErea, String iMode, GetUp_AcceptanceGeneralListFromAssetListAssetTransfer listAssetTransfer) throws java.rmi.RemoteException;
    public String get_AcceptanceGeneralListFromAsset_Status(String sUserID, String sPass, String sSOPHIEU) throws java.rmi.RemoteException;
    public String getUp_Asset_Warranty(String sUserID, String sPass, String sSys, String sProjectcode, GetUp_Asset_WarrantyListAssetWarranty listAssetWarranty) throws java.rmi.RemoteException;
    public String get_ProjectBalanceGetApproval_byProject_Tinh(String sUserID, String sPass, String sDepartmentCode, String sProjectcode, String sStartDate, String sEndDate, String sLoai) throws java.rmi.RemoteException;
    public String get_FAC_byProjectCode(String sUserID, String sPass, String sDepartmentCode, String sProjectCode, String sStartDate, String sEndDate) throws java.rmi.RemoteException;
    public String get_ATP_byProjectCode(String sUserID, String sPass, String sDepartmentCode, String sProjectCode, String sStartDate, String sEndDate) throws java.rmi.RemoteException;
    public String getUp_Asset_RealityStation(String sUserID, String sPass, String sSys, String sProjectcode, String sContractNumber, GetUp_Asset_RealityStationListAssetRealityStation listAssetRealityStation) throws java.rmi.RemoteException;
    public String getUp_AssetGroup(String sUserID, String sPass, String sDepartmnetCode, String iAmount, String sSys) throws java.rmi.RemoteException;
    public String get_Asset_AssetMPHistory(String sUserID, String sPass, String sDepartmnetCode, String sProjectCode, String sStartDate, String sEndDate) throws java.rmi.RemoteException;
}
