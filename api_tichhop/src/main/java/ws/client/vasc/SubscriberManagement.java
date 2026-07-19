/**
 * SubscriberManagement.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public interface SubscriberManagement extends javax.xml.rpc.Service {
    public String getSubscriberManagementSoapAddress();

    public SubscriberManagementSoap_PortType getSubscriberManagementSoap() throws javax.xml.rpc.ServiceException;

    public SubscriberManagementSoap_PortType getSubscriberManagementSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
    public String getSubscriberManagementSoap12Address();

    public SubscriberManagementSoap_PortType getSubscriberManagementSoap12() throws javax.xml.rpc.ServiceException;

    public SubscriberManagementSoap_PortType getSubscriberManagementSoap12(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
