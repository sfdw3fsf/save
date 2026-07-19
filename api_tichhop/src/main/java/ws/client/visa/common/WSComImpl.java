/**
 * WSComImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.visa.common;

public interface WSComImpl extends java.rmi.Remote {
    public Object[] getAccountByStatus(Login login, int type, String provinceCode, String fromDate, String toDate) throws java.rmi.RemoteException;
    public void changePartner(Login login, String accountName, String partnerCode, String note) throws java.rmi.RemoteException;
    public void changeMacAddress(Login login, String accountName, String macAddress, String note) throws java.rmi.RemoteException;
    public void changeGreenNet(Login login, String accountName, int status) throws java.rmi.RemoteException;
    public void changeVRF(Login login, String accountName, String vrf) throws java.rmi.RemoteException;
    public String initModel(ReportAccountModel ram, AccountModel am, AccountServiceModel asm, ServiceAttribute att, ReserveModel rvm) throws java.rmi.RemoteException;
    public Object[] findAccountsByPort(Login login, String codeOrg, String systemid, String rackid, String shelf, String slot, String port, String vpi, String vci) throws java.rmi.RemoteException;
    public void modifyServiceIPAccessMega(Login login, AccountModel accountModel, String ip, String ipv6Lan) throws java.rmi.RemoteException;
    public void modifyServiceIPAccessMegaIPv6(Login login, AccountModel accountModel, String ip, String ipv6Lan, String ipv4Wan, String ipv6Wan) throws java.rmi.RemoteException;
    public void registerMegaVNN(Login login, CustomerModel customerModel, AccountModel accountModel, AccountServiceModel service, Object[] childServices, Object[] attributes) throws java.rmi.RemoteException;
    public void modifyServiceTypeMegaVNN1(Login login, AccountModel accountModel, AccountServiceModel accountServiceModel, String ip, boolean autoIpFlag, boolean oldIp, String ipv6, boolean autoIpV6Flag) throws java.rmi.RemoteException;
    public void modifyServiceTypeMegaVNN(Login login, AccountModel accountModel, AccountServiceModel accountServiceModel, String ip, boolean autoIpFlag, boolean oldIp, String ipv6, boolean autoIpV6Flag) throws java.rmi.RemoteException;
    public void modifyServiceTypeMegaVNN1IPv6(Login login, AccountModel accountModel, AccountServiceModel accountServiceModel, String ip, boolean autoIpFlag, boolean oldIp, String ipv6, boolean autoIpV6Flag, String ipv4Wan, String ipv6Wan) throws java.rmi.RemoteException;
    public void modifyServiceTypeMegaVNNIPv6(Login login, AccountModel accountModel, AccountServiceModel accountServiceModel, String ip, boolean autoIpFlag, boolean oldIp, String ipv6, boolean autoIpV6Flag, String ipv4Wan, String ipv6Wan) throws java.rmi.RemoteException;
    public String checkDoFix(Login login, AccountModel accountModel) throws java.rmi.RemoteException;
    public Object[] lookupRASAccountNoLog(Login login, String accountName) throws java.rmi.RemoteException;
    public void updateCustomerAddress(Login login, CustomerModel customerModel) throws java.rmi.RemoteException;
    public Object[] lookupPackageSysByAccount(Login login, CustomerModel customerModel) throws java.rmi.RemoteException;
    public boolean checkGreenNet(Login login, long serviceInfoId) throws java.rmi.RemoteException;
    public void changeCGNAT(Login login, String accountName, int status) throws java.rmi.RemoteException;
}
