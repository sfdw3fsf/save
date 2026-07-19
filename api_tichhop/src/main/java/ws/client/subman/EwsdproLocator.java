/**
 * EwsdproLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.subman;

public class EwsdproLocator extends org.apache.axis.client.Service implements Ewsdpro {

    public EwsdproLocator() {
    }


    public EwsdproLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public EwsdproLocator(String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ewsdproSoap
    private String ewsdproSoap_address = "http://10.35.60.66:66/ewsdpro.asmx";

    public String getewsdproSoapAddress() {
        return ewsdproSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private String ewsdproSoapWSDDServiceName = "ewsdproSoap";

    public String getewsdproSoapWSDDServiceName() {
        return ewsdproSoapWSDDServiceName;
    }

    public void setewsdproSoapWSDDServiceName(String name) {
        ewsdproSoapWSDDServiceName = name;
    }

    public EwsdproSoap getewsdproSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ewsdproSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getewsdproSoap(endpoint);
    }

    public EwsdproSoap getewsdproSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            EwsdproSoapStub _stub = new EwsdproSoapStub(portAddress, this);
            _stub.setPortName(getewsdproSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setewsdproSoapEndpointAddress(String address) {
        ewsdproSoap_address = address;
    }


    // Use to get a proxy class for ewsdproSoap12
    private String ewsdproSoap12_address = "http://10.35.60.66:66/ewsdpro.asmx";

    public String getewsdproSoap12Address() {
        return ewsdproSoap12_address;
    }

    // The WSDD service name defaults to the port name.
    private String ewsdproSoap12WSDDServiceName = "ewsdproSoap12";

    public String getewsdproSoap12WSDDServiceName() {
        return ewsdproSoap12WSDDServiceName;
    }

    public void setewsdproSoap12WSDDServiceName(String name) {
        ewsdproSoap12WSDDServiceName = name;
    }

    public EwsdproSoap getewsdproSoap12() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ewsdproSoap12_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getewsdproSoap12(endpoint);
    }

    public EwsdproSoap getewsdproSoap12(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            EwsdproSoap12Stub _stub = new EwsdproSoap12Stub(portAddress, this);
            _stub.setPortName(getewsdproSoap12WSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setewsdproSoap12EndpointAddress(String address) {
        ewsdproSoap12_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     * This service has multiple ports for a given interface;
     * the proxy implementation returned may be indeterminate.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (EwsdproSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                EwsdproSoapStub _stub = new EwsdproSoapStub(new java.net.URL(ewsdproSoap_address), this);
                _stub.setPortName(getewsdproSoapWSDDServiceName());
                return _stub;
            }
            if (EwsdproSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                EwsdproSoap12Stub _stub = new EwsdproSoap12Stub(new java.net.URL(ewsdproSoap12_address), this);
                _stub.setPortName(getewsdproSoap12WSDDServiceName());
                return _stub;
            }
        }
        catch (Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        String inputPortName = portName.getLocalPart();
        if ("ewsdproSoap".equals(inputPortName)) {
            return getewsdproSoap();
        }
        else if ("ewsdproSoap12".equals(inputPortName)) {
            return getewsdproSoap12();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://tempuri.org/", "ewsdpro");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "ewsdproSoap"));
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "ewsdproSoap12"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(String portName, String address) throws javax.xml.rpc.ServiceException {
        
if ("ewsdproSoap".equals(portName)) {
            setewsdproSoapEndpointAddress(address);
        }
        else 
if ("ewsdproSoap12".equals(portName)) {
            setewsdproSoap12EndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
