/**
 * WSAppImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.visa.application;

public interface WSAppImpl extends java.rmi.Remote {
    public void doDeleteServiceChangeUseTypeNextMonth(Login login, AccountModel accountModel) throws java.rmi.RemoteException;
    public Object[] findAccountbyPort(Login login, long brasid, long dslamid, String systemid, String rackid, String shelf, String slot, String port, String vpi, String vci) throws java.rmi.RemoteException;
    public void doDeleteNextMonth(Login login, AccountModel accountModel) throws java.rmi.RemoteException;
    public void modifyPasswordVNN(Login login, AccountModel accountModel) throws java.rmi.RemoteException;
    public Object[] reportByServiceMegaVNN(Login login, String beginData, String endDate, String actionIds, String userCode, String flag, int pageNum) throws java.rmi.RemoteException;
    public Object[] reportByServiceVNN126X(Login login, String beginData, String endDate, String actionIds, String userCode, String flag, int pageNum) throws java.rmi.RemoteException;
    public void updateCustomer(Login login, CustomerModel customerModel, AccountModel accountModel, String contract) throws java.rmi.RemoteException;
    public void changeCustomerType(Login login, CustomerModel mCustomer, String note) throws java.rmi.RemoteException;
    public void changeCustomerType(Login login, ChangeCustomerTypeForm mChange) throws java.rmi.RemoteException;
    public void changePlanId(Login login, AccountModel accountModel, String planId, String note) throws java.rmi.RemoteException;
    public void changeBillingInfo(Login login, AccountModel accountModel, String strBilling, String note) throws java.rmi.RemoteException;
    public void createMailRas(Login login, AccountModel accModel) throws java.rmi.RemoteException;
    public void deleteBRAS(Login login, String brasId) throws java.rmi.RemoteException;
    public void updateBRAS(Login login, BRASModel mBRAS) throws java.rmi.RemoteException;
    public long insertBRAS(Login login, BRASModel mBRAS) throws java.rmi.RemoteException;
    public AccountModel retrieveMailRas(Login login, AccountModel accountModel) throws java.rmi.RemoteException;
    public Paginated reportByFrameIpAddress(Login login, String operation, String value, int pageNum) throws java.rmi.RemoteException;
    public Paginated reportAccountLog(Login login, AccountModel accModel, int pageNum) throws java.rmi.RemoteException;
    public Paginated reportByCustomer(Login login, String customerInfo, String operation, String value, int pageNum) throws java.rmi.RemoteException;
    public Paginated reportByAccountName(Login login, AccountModel accountModel, String operation, String value) throws java.rmi.RemoteException;
    public Paginated retrieveSysLog(Login login, ActionLogModel actionLogModel) throws java.rmi.RemoteException;
    public Paginated reportCollectionByBras(Login login, String areaCode, long brasId, long dslamId, String systemId, int rack, int shelf, int slot, int port, int vciCvlan, int vpiSvlan, int pageNum, String typeCollection) throws java.rmi.RemoteException;
    public Object[] reportGrowVNN1260(Login login, java.util.Calendar beginDate, java.util.Calendar endDate) throws java.rmi.RemoteException;
    public Object[] reportGrowMegaVNN(Login login, java.util.Calendar beginDate, java.util.Calendar endDate) throws java.rmi.RemoteException;
    public Object[] reportCustomerByBras(Login login, String areaCode, long brasId, long dslamId, String systemId, int rack, int shelf, int slot, int port, int vciCvlan, int vpiSvlan, String flag, int pageNum) throws java.rmi.RemoteException;
    public Object[] reportByCusType(Login login, String areaCode, String serviceType, String cusType, String userCode, String flag, int pageNum) throws java.rmi.RemoteException;
    public Object[] reportByServicePackage(Login login, String areaCode, String serviceType, String servicePackageStr, String userCode, String flag, int pageNum) throws java.rmi.RemoteException;
    public Object[] reportByServiceMailPlus(Login login, String beginData, String endDate, String actionIds, String companyId, String userCode, String flag, int pageNum) throws java.rmi.RemoteException;
    public Object[] reportGrowMailPlus(Login login, java.util.Calendar beginDate, java.util.Calendar endDate) throws java.rmi.RemoteException;
    public long restoreAccount(Login login, AccountModel accModel) throws java.rmi.RemoteException;
    public void reCreateEmailAccount(Login login, AccountModel accModel) throws java.rmi.RemoteException;
    public void synchronizeFS(Login login, AccountModel accModel) throws java.rmi.RemoteException;
    public Object[] searchMailPlus(Login login, String accountName, long domainId) throws java.rmi.RemoteException;
    public void alertToAdmin(Login login, AccountModel accModel) throws java.rmi.RemoteException;
    public void reCreateVNNAccount(Login login, AccountModel accModel) throws java.rmi.RemoteException;
    public void synchronizeLDAP(Login login, AccountModel accModel) throws java.rmi.RemoteException;
    public Object[] searchVNNAccount(Login login, AccountModel accountModel) throws java.rmi.RemoteException;
    public void changeAcceptanceTime(Login login, AccountModel accountModel) throws java.rmi.RemoteException;
    public long getBrasIdByAccount(Login login, String accountName) throws java.rmi.RemoteException;
    public Object[] getReserveWarning(Login login, int time2WarnRes) throws java.rmi.RemoteException;
    public Paginated retrieveReservedAccount(Login login, ReserveModel reserverModel) throws java.rmi.RemoteException;
    public String modifyRestrictPhone(Login login, AccountModel accountModel, String restrictPhone, boolean action) throws java.rmi.RemoteException;
    public String retrieveRestrictVNN(Login login, AccountModel accountModel) throws java.rmi.RemoteException;
    public void changeMailPlusQuota(Login login, AccountModel accountModel, String quota) throws java.rmi.RemoteException;
    public AccountModel retrieveAccountsbyId(Login login, AccountModel accountModel) throws java.rmi.RemoteException;
    public AccountModel retrieveAccountsbyName(Login login, AccountModel accountModel) throws java.rmi.RemoteException;
    public void reReservedAccount(Login login, String account_name, java.util.Calendar endDate) throws java.rmi.RemoteException;
    public void reReservedAccount(Login login, ReserveModel[] reserveList) throws java.rmi.RemoteException;
    public void unReservedAccount(Login login, String account_name) throws java.rmi.RemoteException;
    public long reserveMailPlusAccount(Login login, String accountName, long domainId, ReserveModel reserveModel) throws java.rmi.RemoteException;
    public Object[] lookupMailPlusAccount(Login login, String accountName, long domainId, boolean lookupDomain) throws java.rmi.RemoteException;
    public long reserveRASAccount(Login login, AccountModel accountModel, ReserveModel reserveModel) throws java.rmi.RemoteException;
    public Object[] lookupRASAccount(Login login, String accountName) throws java.rmi.RemoteException;
    public void changePortMegaVNN(Login login, AccountModel account, Object[] attributes) throws java.rmi.RemoteException;
    public void change1260ToMega(Login login, AccountModel accountModel, AccountServiceModel accSerModel, Object[] attributes) throws java.rmi.RemoteException;
    public void modifyNameMailPlus(Login login, AccountModel accountModel) throws java.rmi.RemoteException;
    public void modifyPasswordMailPlus(Login login, AccountModel accountModel) throws java.rmi.RemoteException;
    public void modifyTariffType(Login login, AccountModel accoutModel, AccountServiceModel accoutSerModel, String effectType) throws java.rmi.RemoteException;
    public void modifyMailRASName(Login login, AccountModel accountModel, String newAccountName) throws java.rmi.RemoteException;
    public void modifyPasswordMailRAS(Login login, AccountModel accountModel, String newPassword) throws java.rmi.RemoteException;
    public void modifyAccountNameVNN(Login login, AccountModel accountModel) throws java.rmi.RemoteException;
    public void modifyVASService(Login login, AccountModel accModel, AccountServiceModel accSerModel, String status, long serviceParentId, boolean isRestrictPhone) throws java.rmi.RemoteException;
    public void modifyLookuIpService(Login login, AccountModel accountModel, AccountServiceModel lookuIpService, String status) throws java.rmi.RemoteException;
    public Object[] getLookupIpServices(Login login, AccountServiceModel accSerModel) throws java.rmi.RemoteException;
    public Object[] getLookupIps(Login login, AccountServiceModel accSerModel) throws java.rmi.RemoteException;
    public Object[] removeDomain(Login login, String domain, String reason) throws java.rmi.RemoteException;
    public void removeEmailPlus(Login login, AccountModel accountModel, String reason) throws java.rmi.RemoteException;
    public Object[] modifyStatusDomain(Login login, String domain, String status, String reason) throws java.rmi.RemoteException;
    public void modifyStatusMailPlus(Login login, AccountModel accountModel, String reason) throws java.rmi.RemoteException;
    public void removeVNN(Login login, AccountModel accountModel, String reason, String note) throws java.rmi.RemoteException;
    public void updateStatusVNN(Login login, AccountModel accountModel, String reason, String note) throws java.rmi.RemoteException;
    public AccountModel[] registerMailPlus(Login login, CustomerModel customerModel, AccountModel[] accountModel, AccountServiceModel[] service, Object[] serviceAttributes, long domainId, String newDomain) throws java.rmi.RemoteException;
    public void registerVNN1260(Login login, CustomerModel customerModel, AccountModel accountModel, AccountServiceModel service, Object[] childServices, Object[] attributes) throws java.rmi.RemoteException;
    public void registerMegaVnnWard(Login login, CustomerModel customerModel, AccountModel accountModel, AccountServiceModel service, Object[] childServices, Object[] attributes) throws java.rmi.RemoteException;
    public AccountModel getLastHistoryByAccountName(Login login, AccountModel accModel) throws java.rmi.RemoteException;
    public void updateDSLAM(Login login, DSLAMModel dslamModel) throws java.rmi.RemoteException;
    public void deleteDSLAMDevice(Login login, long dslamId) throws java.rmi.RemoteException;
    public long insertDslam(Login login, DSLAMModel dslamModel) throws java.rmi.RemoteException;
    public boolean verifyPasswordRASAccount(Login login, String accountName, String password) throws java.rmi.RemoteException;
    public boolean verifyPasswordMailPlus(Login login, String accountName, String password) throws java.rmi.RemoteException;
    public LabelValueBean getLabelValueBean() throws java.rmi.RemoteException;
    public GrowReportModel getGrowReportModel() throws java.rmi.RemoteException;
    public LogModel getLogModel() throws java.rmi.RemoteException;
    public ServiceAttribute getServiceAttribute() throws java.rmi.RemoteException;
    public void registerMegaVNN_BACKUP(Login login, CustomerModel customerModel, AccountModel accountModel, AccountServiceModel service, Object[] childServices, Object[] attributes) throws java.rmi.RemoteException;
    public void modifyServiceIPAccessMega(Login login, AccountModel accountModel, String ip) throws java.rmi.RemoteException;
    public void registerMegaVNN(Login login, CustomerModel customerModel, AccountModel accountModel, AccountServiceModel service, Object[] childServices, Object[] attributes) throws java.rmi.RemoteException;
    public void modifyServiceTypeMegaVNN1(Login login, AccountModel accountModel, AccountServiceModel accountServiceModel, String ip, boolean autoIpFlag, boolean oldIp) throws java.rmi.RemoteException;
    public void modifyServiceTypeMegaVNN(Login login, AccountModel accountModel, AccountServiceModel accountServiceModel, String ip, boolean autoIpFlag, boolean oldIp) throws java.rmi.RemoteException;
}
