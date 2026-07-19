/**
 * MethodChangePay.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class MethodChangePay  implements java.io.Serializable {
    private String IPTVACCOUNT;

    private String method;

    private String no_Month;

    public MethodChangePay() {
    }

    public MethodChangePay(
           String IPTVACCOUNT,
           String method,
           String no_Month) {
           this.IPTVACCOUNT = IPTVACCOUNT;
           this.method = method;
           this.no_Month = no_Month;
    }


    /**
     * Gets the IPTVACCOUNT value for this MethodChangePay.
     * 
     * @return IPTVACCOUNT
     */
    public String getIPTVACCOUNT() {
        return IPTVACCOUNT;
    }


    /**
     * Sets the IPTVACCOUNT value for this MethodChangePay.
     * 
     * @param IPTVACCOUNT
     */
    public void setIPTVACCOUNT(String IPTVACCOUNT) {
        this.IPTVACCOUNT = IPTVACCOUNT;
    }


    /**
     * Gets the method value for this MethodChangePay.
     * 
     * @return method
     */
    public String getMethod() {
        return method;
    }


    /**
     * Sets the method value for this MethodChangePay.
     * 
     * @param method
     */
    public void setMethod(String method) {
        this.method = method;
    }


    /**
     * Gets the no_Month value for this MethodChangePay.
     * 
     * @return no_Month
     */
    public String getNo_Month() {
        return no_Month;
    }


    /**
     * Sets the no_Month value for this MethodChangePay.
     * 
     * @param no_Month
     */
    public void setNo_Month(String no_Month) {
        this.no_Month = no_Month;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof MethodChangePay)) return false;
        MethodChangePay other = (MethodChangePay) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.IPTVACCOUNT==null && other.getIPTVACCOUNT()==null) || 
             (this.IPTVACCOUNT!=null &&
              this.IPTVACCOUNT.equals(other.getIPTVACCOUNT()))) &&
            ((this.method==null && other.getMethod()==null) || 
             (this.method!=null &&
              this.method.equals(other.getMethod()))) &&
            ((this.no_Month==null && other.getNo_Month()==null) || 
             (this.no_Month!=null &&
              this.no_Month.equals(other.getNo_Month())));
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
        if (getIPTVACCOUNT() != null) {
            _hashCode += getIPTVACCOUNT().hashCode();
        }
        if (getMethod() != null) {
            _hashCode += getMethod().hashCode();
        }
        if (getNo_Month() != null) {
            _hashCode += getNo_Month().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MethodChangePay.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">MethodChangePay"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("IPTVACCOUNT");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "IPTVACCOUNT"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("method");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "Method"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("no_Month");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "No_Month"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
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
