/**
 * Ewsdpro.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.subman;

public interface Ewsdpro extends javax.xml.rpc.Service {
    public String getewsdproSoapAddress();

    public EwsdproSoap getewsdproSoap() throws javax.xml.rpc.ServiceException;

    public EwsdproSoap getewsdproSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
    public String getewsdproSoap12Address();

    public EwsdproSoap getewsdproSoap12() throws javax.xml.rpc.ServiceException;

    public EwsdproSoap getewsdproSoap12(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
