/**
 * MainSVRLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.qldt;

public class MainSVRLocator extends org.apache.axis.client.Service implements MainSVR {

    public MainSVRLocator() {
    }


    public MainSVRLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public MainSVRLocator(String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for MainSVRSoap
    private String MainSVRSoap_address = "http://10.193.69.74:8081/MainSVR.asmx";

    public String getMainSVRSoapAddress() {
        return MainSVRSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private String MainSVRSoapWSDDServiceName = "MainSVRSoap";

    public String getMainSVRSoapWSDDServiceName() {
        return MainSVRSoapWSDDServiceName;
    }

    public void setMainSVRSoapWSDDServiceName(String name) {
        MainSVRSoapWSDDServiceName = name;
    }

    public MainSVRSoap_PortType getMainSVRSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(MainSVRSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getMainSVRSoap(endpoint);
    }

    public MainSVRSoap_PortType getMainSVRSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            MainSVRSoap_BindingStub _stub = new MainSVRSoap_BindingStub(portAddress, this);
            _stub.setPortName(getMainSVRSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setMainSVRSoapEndpointAddress(String address) {
        MainSVRSoap_address = address;
    }


    // Use to get a proxy class for MainSVRSoap12
    private String MainSVRSoap12_address = "http://10.193.69.74:8081/MainSVR.asmx";

    public String getMainSVRSoap12Address() {
        return MainSVRSoap12_address;
    }

    // The WSDD service name defaults to the port name.
    private String MainSVRSoap12WSDDServiceName = "MainSVRSoap12";

    public String getMainSVRSoap12WSDDServiceName() {
        return MainSVRSoap12WSDDServiceName;
    }

    public void setMainSVRSoap12WSDDServiceName(String name) {
        MainSVRSoap12WSDDServiceName = name;
    }

    public MainSVRSoap_PortType getMainSVRSoap12() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(MainSVRSoap12_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getMainSVRSoap12(endpoint);
    }

    public MainSVRSoap_PortType getMainSVRSoap12(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            MainSVRSoap12Stub _stub = new MainSVRSoap12Stub(portAddress, this);
            _stub.setPortName(getMainSVRSoap12WSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setMainSVRSoap12EndpointAddress(String address) {
        MainSVRSoap12_address = address;
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
            if (MainSVRSoap_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                MainSVRSoap_BindingStub _stub = new MainSVRSoap_BindingStub(new java.net.URL(MainSVRSoap_address), this);
                _stub.setPortName(getMainSVRSoapWSDDServiceName());
                return _stub;
            }
            if (MainSVRSoap_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                MainSVRSoap12Stub _stub = new MainSVRSoap12Stub(new java.net.URL(MainSVRSoap12_address), this);
                _stub.setPortName(getMainSVRSoap12WSDDServiceName());
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
        if ("MainSVRSoap".equals(inputPortName)) {
            return getMainSVRSoap();
        }
        else if ("MainSVRSoap12".equals(inputPortName)) {
            return getMainSVRSoap12();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://tempuri.org/", "MainSVR");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "MainSVRSoap"));
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "MainSVRSoap12"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(String portName, String address) throws javax.xml.rpc.ServiceException {
        
if ("MainSVRSoap".equals(portName)) {
            setMainSVRSoapEndpointAddress(address);
        }
        else 
if ("MainSVRSoap12".equals(portName)) {
            setMainSVRSoap12EndpointAddress(address);
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
