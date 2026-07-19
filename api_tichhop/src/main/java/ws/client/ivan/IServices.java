/**
 * IServices.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.ivan;

public interface IServices extends java.rmi.Remote {
    public IVAN_Respond excuteRequest(String request_id, String client_id, String function_code, String parameters, String secure_code) throws java.rmi.RemoteException;
    public String sendRequest(String token, String userId, String password, String serviceId, String functionCode, String inputParams, byte[] fileAttachment, ws.client.ivan.holders.ArrayOfanyTypeHolder outputVal) throws java.rmi.RemoteException;
}
