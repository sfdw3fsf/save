/**
 * WSComImplServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.visa.common;

public class WSComImplServiceLocator extends org.apache.axis.client.Service implements WSComImplService {

    public WSComImplServiceLocator() {
    }


    public WSComImplServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public WSComImplServiceLocator(String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for CommonBusinessServices
    private String CommonBusinessServices_address = "http://visas.vnpt.vn/visa/services/CommonBusinessServices";

    public String getCommonBusinessServicesAddress() {
        return CommonBusinessServices_address;
    }

    // The WSDD service name defaults to the port name.
    private String CommonBusinessServicesWSDDServiceName = "CommonBusinessServices";

    public String getCommonBusinessServicesWSDDServiceName() {
        return CommonBusinessServicesWSDDServiceName;
    }

    public void setCommonBusinessServicesWSDDServiceName(String name) {
        CommonBusinessServicesWSDDServiceName = name;
    }

    public WSComImpl getCommonBusinessServices() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(CommonBusinessServices_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getCommonBusinessServices(endpoint);
    }

    public WSComImpl getCommonBusinessServices(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            CommonBusinessServicesSoapBindingStub _stub = new CommonBusinessServicesSoapBindingStub(portAddress, this);
            _stub.setPortName(getCommonBusinessServicesWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setCommonBusinessServicesEndpointAddress(String address) {
        CommonBusinessServices_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (WSComImpl.class.isAssignableFrom(serviceEndpointInterface)) {
                CommonBusinessServicesSoapBindingStub _stub = new CommonBusinessServicesSoapBindingStub(new java.net.URL(CommonBusinessServices_address), this);
                _stub.setPortName(getCommonBusinessServicesWSDDServiceName());
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
        if ("CommonBusinessServices".equals(inputPortName)) {
            return getCommonBusinessServices();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://10.156.7.25/visa/services/CommonBusinessServices", "WSComImplService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://10.156.7.25/visa/services/CommonBusinessServices", "CommonBusinessServices"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(String portName, String address) throws javax.xml.rpc.ServiceException {
        
if ("CommonBusinessServices".equals(portName)) {
            setCommonBusinessServicesEndpointAddress(address);
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
