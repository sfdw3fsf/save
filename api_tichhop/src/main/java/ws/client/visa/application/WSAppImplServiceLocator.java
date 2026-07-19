/**
 * WSAppImplServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.visa.application;

public class WSAppImplServiceLocator extends org.apache.axis.client.Service implements WSAppImplService {

    public WSAppImplServiceLocator() {
    }


    public WSAppImplServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public WSAppImplServiceLocator(String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ApplicationBusinessServices
    private String ApplicationBusinessServices_address = "http://visas.vnpt.vn/visa/services/ApplicationBusinessServices";

    public String getApplicationBusinessServicesAddress() {
        return ApplicationBusinessServices_address;
    }

    // The WSDD service name defaults to the port name.
    private String ApplicationBusinessServicesWSDDServiceName = "ApplicationBusinessServices";

    public String getApplicationBusinessServicesWSDDServiceName() {
        return ApplicationBusinessServicesWSDDServiceName;
    }

    public void setApplicationBusinessServicesWSDDServiceName(String name) {
        ApplicationBusinessServicesWSDDServiceName = name;
    }

    public WSAppImpl getApplicationBusinessServices() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ApplicationBusinessServices_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getApplicationBusinessServices(endpoint);
    }

    public WSAppImpl getApplicationBusinessServices(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            ApplicationBusinessServicesSoapBindingStub _stub = new ApplicationBusinessServicesSoapBindingStub(portAddress, this);
            _stub.setPortName(getApplicationBusinessServicesWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setApplicationBusinessServicesEndpointAddress(String address) {
        ApplicationBusinessServices_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (WSAppImpl.class.isAssignableFrom(serviceEndpointInterface)) {
                ApplicationBusinessServicesSoapBindingStub _stub = new ApplicationBusinessServicesSoapBindingStub(new java.net.URL(ApplicationBusinessServices_address), this);
                _stub.setPortName(getApplicationBusinessServicesWSDDServiceName());
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
        if ("ApplicationBusinessServices".equals(inputPortName)) {
            return getApplicationBusinessServices();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://10.156.7.51/visa/services/ApplicationBusinessServices", "WSAppImplService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://10.156.7.51/visa/services/ApplicationBusinessServices", "ApplicationBusinessServices"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(String portName, String address) throws javax.xml.rpc.ServiceException {
        
if ("ApplicationBusinessServices".equals(portName)) {
            setApplicationBusinessServicesEndpointAddress(address);
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
