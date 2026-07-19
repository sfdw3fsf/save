/**
 * VTNAPI.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vtn;

public interface VTNAPI extends java.rmi.Remote {
    public String setPasswordForRoot(String external_id, String subscirber_pass, String user, String pass) throws java.rmi.RemoteException, Exception;
    public String speedDialing(String external_id, String dial_code, String sub_entry, String user, String pass) throws java.rmi.RemoteException, Exception;
    public String callTransfer(String external_id, String user, String pass) throws java.rmi.RemoteException, Exception;
    public String callForwardingBusy(String external_id, String fw_to_number, String user, String pass) throws java.rmi.RemoteException, Exception;
    public String callForwardingNoAnswer(String external_id, String fw_to_number, String user, String pass) throws java.rmi.RemoteException, Exception;
    public String callForwardingDefault(String external_id, String fw_to_number, String user, String pass) throws java.rmi.RemoteException, Exception;
    public String callForwardingVoiceMail(String external_id, String fw_to_number, String user, String pass) throws java.rmi.RemoteException, Exception;
    public String simultaneousRinging(String external_id, String puid_num_list, String user, String pass) throws java.rmi.RemoteException, Exception;
    public String sequentialRinging(String external_id, String public_id, String user, String pass) throws java.rmi.RemoteException, Exception;
    public String rbt_single_upload(String scat_id, String cat_id, String song, String rbt_id, String price, String tone_url, String val_dur, String title_url, String singer, String exp_date, String user, String pass) throws java.rmi.RemoteException, Exception;
    public String rbt_attribute_change(String rbt_id, String song, String singer, String price, String exp_date, String vali_duration, String user, String pass) throws java.rmi.RemoteException, Exception;
    public String hotLine(String external_id, String nbr, String user, String pass) throws java.rmi.RemoteException, Exception;
    public String warmLine(String external_id, String nbr, String user, String pass) throws java.rmi.RemoteException, Exception;
    public String callBarring(String external_id, String pin, String user, String pass) throws java.rmi.RemoteException, Exception;
    public String callBlocking(String external_id, String pin, String user, String pass) throws java.rmi.RemoteException, Exception;
    public String selectiveCallAcceptance(String external_id, String msisdn0, String msisdn_list, String pin, String user, String pass) throws java.rmi.RemoteException, Exception;
    public String selecltiveCallRejection(String external_id, String selectiveDN, String pin, String user, String pass) throws java.rmi.RemoteException, Exception;
    public String conferenceCall(String external_id, String consize, String call_limit, String user, String pass) throws java.rmi.RemoteException, Exception;
    public String selectiveCallForwarding(String external_id, String msisdn0, String msisdn_list, String user, String pass) throws java.rmi.RemoteException, Exception;
    public String closedUserGroups(String external_id, String group, String user, String pass) throws java.rmi.RemoteException, Exception;
    public String deviceExtension(String external_id, String pmsisdn, String user, String pass) throws java.rmi.RemoteException, Exception;
    public String deviceExtensionServer(String external_id, String cmsisdn, String user, String pass) throws java.rmi.RemoteException, Exception;
    public String ipCentrex(String external_id, String ipcentrex, String user, String pass) throws java.rmi.RemoteException, Exception;
    public String toneSelection(String external_id, String tone_id, String user, String pass) throws java.rmi.RemoteException, Exception;
    public String registerService(String external_id, String service_code, String user, String pass) throws java.rmi.RemoteException, Exception;
    public String prbt_setting(String msisdn, String caller_type, String condition_type, String condition_value, String entid, String member, String tb_type, String tb_value, String user, String pass) throws java.rmi.RemoteException, Exception;
    public String prbt_resetsetting(String msisdn, String setid, String user, String pass) throws java.rmi.RemoteException, Exception;
    public String prbt_querysetting(String msisdn, String entid, String user, String pass) throws java.rmi.RemoteException, Exception;
    public String prbt_querymrbt(String msisdn, String user, String pass) throws java.rmi.RemoteException, Exception;
    public String prbt_deletemrbt(String msisdn, String entid, String user, String pass) throws java.rmi.RemoteException, Exception;
    public OfferResouceObject[] getOfferResource(String user, String pass) throws java.rmi.RemoteException, Exception;
    public OfferObject getOffer(String offer_code, String user, String pass) throws java.rmi.RemoteException, Exception;
    public AccountObject queryAccount(String account_code, String account_no, String user, String pass) throws java.rmi.RemoteException, Exception;
    public SubscriberObject[] querySubscriber(String external_id, String user, String pass) throws java.rmi.RemoteException, Exception;
    public String createAccount(AccountObject accountObject, String user, String pass) throws java.rmi.RemoteException, Exception;
    public String createAccByProvinceCode(AccountObject accountObject, String strProvinceCode, String user, String pass) throws java.rmi.RemoteException, Exception;
    public String createSubscriber(SubscriberObject subscriberObject, String strReasonId, String user, String pass) throws java.rmi.RemoteException, Exception;
    public String createSubByProvinceCode(SubscriberObject subscriberObject, String strReasonId, String strProvinceCode, String user, String pass) throws java.rmi.RemoteException, Exception;
    public void updateAccount(AccountObject accountObject, String strReasonId, String user, String pass) throws java.rmi.RemoteException, Exception;
    public void updateSubscriber(SubscriberObject subscriberObject, String strReasonId, String user, String pass) throws java.rmi.RemoteException, Exception;
    public void registerSupplementOffer(SubscriberObject subscriberObject, Object[] supplementOffer, String strReasonId, String user, String pass) throws java.rmi.RemoteException, Exception;
    public void removeSupplementOffer(SubscriberObject subscriberObject, Object[] supplementOffer, String strReasonId, String user, String pass) throws java.rmi.RemoteException, Exception;
    public void transferSubscriber(SubscriberObject subscriberObject, AccountObject accountObject, String strReasonId, String user, String pass) throws java.rmi.RemoteException, Exception;
    public void disconnectSubscriber(SubscriberObject subscriberObject, String strReasonId, String user, String pass) throws java.rmi.RemoteException, Exception;
    public void changeServiceActStatus(SubscriberObject subscriberObject, String strBareUnbare, String strBareType, String strBareReasonType, String strReasonId, String user, String pass) throws java.rmi.RemoteException, Exception;
    public AuditObject[] queryAudit(String external_id, String strFromDate, String strToDate, String user, String pass) throws java.rmi.RemoteException, Exception;
    public Object[] getResources(String user, String pass) throws java.rmi.RemoteException, Exception;
    public String removeService(String external_id, String service_code, String user, String pass) throws java.rmi.RemoteException, Exception;
    public String getPassword(String external_id, String user, String pass) throws java.rmi.RemoteException, Exception;
    public String setPassword(String external_id, String strOldPass, String strNewPass, String user, String pass) throws java.rmi.RemoteException, Exception;
}
