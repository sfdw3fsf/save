/**
 * MainSVR.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.qldt;

public interface MainSVR extends javax.xml.rpc.Service {
    public String getMainSVRSoapAddress();

    public MainSVRSoap_PortType getMainSVRSoap() throws javax.xml.rpc.ServiceException;

    public MainSVRSoap_PortType getMainSVRSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
    public String getMainSVRSoap12Address();

    public MainSVRSoap_PortType getMainSVRSoap12() throws javax.xml.rpc.ServiceException;

    public MainSVRSoap_PortType getMainSVRSoap12(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
