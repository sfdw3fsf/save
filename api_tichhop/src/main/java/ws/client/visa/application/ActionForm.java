/**
 * ActionForm.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.visa.application;

public abstract class ActionForm  implements java.io.Serializable {
    private Object multipartRequestHandler;

    private Object servlet;

    private Object servletWrapper;

    public ActionForm() {
    }

    public ActionForm(
           Object multipartRequestHandler,
           Object servlet,
           Object servletWrapper) {
           this.multipartRequestHandler = multipartRequestHandler;
           this.servlet = servlet;
           this.servletWrapper = servletWrapper;
    }


    /**
     * Gets the multipartRequestHandler value for this ActionForm.
     * 
     * @return multipartRequestHandler
     */
    public Object getMultipartRequestHandler() {
        return multipartRequestHandler;
    }


    /**
     * Sets the multipartRequestHandler value for this ActionForm.
     * 
     * @param multipartRequestHandler
     */
    public void setMultipartRequestHandler(Object multipartRequestHandler) {
        this.multipartRequestHandler = multipartRequestHandler;
    }


    /**
     * Gets the servlet value for this ActionForm.
     * 
     * @return servlet
     */
    public Object getServlet() {
        return servlet;
    }


    /**
     * Sets the servlet value for this ActionForm.
     * 
     * @param servlet
     */
    public void setServlet(Object servlet) {
        this.servlet = servlet;
    }


    /**
     * Gets the servletWrapper value for this ActionForm.
     * 
     * @return servletWrapper
     */
    public Object getServletWrapper() {
        return servletWrapper;
    }


    /**
     * Sets the servletWrapper value for this ActionForm.
     * 
     * @param servletWrapper
     */
    public void setServletWrapper(Object servletWrapper) {
        this.servletWrapper = servletWrapper;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof ActionForm)) return false;
        ActionForm other = (ActionForm) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.multipartRequestHandler==null && other.getMultipartRequestHandler()==null) || 
             (this.multipartRequestHandler!=null &&
              this.multipartRequestHandler.equals(other.getMultipartRequestHandler()))) &&
            ((this.servlet==null && other.getServlet()==null) || 
             (this.servlet!=null &&
              this.servlet.equals(other.getServlet()))) &&
            ((this.servletWrapper==null && other.getServletWrapper()==null) || 
             (this.servletWrapper!=null &&
              this.servletWrapper.equals(other.getServletWrapper())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getMultipartRequestHandler() != null) {
            _hashCode += getMultipartRequestHandler().hashCode();
        }
        if (getServlet() != null) {
            _hashCode += getServlet().hashCode();
        }
        if (getServletWrapper() != null) {
            _hashCode += getServletWrapper().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ActionForm.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://action.struts.apache.org", "ActionForm"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("multipartRequestHandler");
        elemField.setXmlName(new javax.xml.namespace.QName("", "multipartRequestHandler"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anyType"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("servlet");
        elemField.setXmlName(new javax.xml.namespace.QName("", "servlet"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anyType"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("servletWrapper");
        elemField.setXmlName(new javax.xml.namespace.QName("", "servletWrapper"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anyType"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           String mechType,
           Class _javaType,
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           String mechType,
           Class _javaType,
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
