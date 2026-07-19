/**
 * MethodChangePayRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class MethodChangePayRequest  implements java.io.Serializable {
    private String no_Month;

    private String method;

    private String IPTVACCOUNT;

    private String startDate;

    private String operator;

    public MethodChangePayRequest() {
    }

    public MethodChangePayRequest(
           String no_Month,
           String method,
           String IPTVACCOUNT,
           String startDate,
           String operator) {
           this.no_Month = no_Month;
           this.method = method;
           this.IPTVACCOUNT = IPTVACCOUNT;
           this.startDate = startDate;
           this.operator = operator;
    }


    /**
     * Gets the no_Month value for this MethodChangePayRequest.
     * 
     * @return no_Month
     */
    public String getNo_Month() {
        return no_Month;
    }


    /**
     * Sets the no_Month value for this MethodChangePayRequest.
     * 
     * @param no_Month
     */
    public void setNo_Month(String no_Month) {
        this.no_Month = no_Month;
    }


    /**
     * Gets the method value for this MethodChangePayRequest.
     * 
     * @return method
     */
    public String getMethod() {
        return method;
    }


    /**
     * Sets the method value for this MethodChangePayRequest.
     * 
     * @param method
     */
    public void setMethod(String method) {
        this.method = method;
    }


    /**
     * Gets the IPTVACCOUNT value for this MethodChangePayRequest.
     * 
     * @return IPTVACCOUNT
     */
    public String getIPTVACCOUNT() {
        return IPTVACCOUNT;
    }


    /**
     * Sets the IPTVACCOUNT value for this MethodChangePayRequest.
     * 
     * @param IPTVACCOUNT
     */
    public void setIPTVACCOUNT(String IPTVACCOUNT) {
        this.IPTVACCOUNT = IPTVACCOUNT;
    }


    /**
     * Gets the startDate value for this MethodChangePayRequest.
     * 
     * @return startDate
     */
    public String getStartDate() {
        return startDate;
    }


    /**
     * Sets the startDate value for this MethodChangePayRequest.
     * 
     * @param startDate
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }


    /**
     * Gets the operator value for this MethodChangePayRequest.
     * 
     * @return operator
     */
    public String getOperator() {
        return operator;
    }


    /**
     * Sets the operator value for this MethodChangePayRequest.
     * 
     * @param operator
     */
    public void setOperator(String operator) {
        this.operator = operator;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof MethodChangePayRequest)) return false;
        MethodChangePayRequest other = (MethodChangePayRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.no_Month==null && other.getNo_Month()==null) || 
             (this.no_Month!=null &&
              this.no_Month.equals(other.getNo_Month()))) &&
            ((this.method==null && other.getMethod()==null) || 
             (this.method!=null &&
              this.method.equals(other.getMethod()))) &&
            ((this.IPTVACCOUNT==null && other.getIPTVACCOUNT()==null) || 
             (this.IPTVACCOUNT!=null &&
              this.IPTVACCOUNT.equals(other.getIPTVACCOUNT()))) &&
            ((this.startDate==null && other.getStartDate()==null) || 
             (this.startDate!=null &&
              this.startDate.equals(other.getStartDate()))) &&
            ((this.operator==null && other.getOperator()==null) || 
             (this.operator!=null &&
              this.operator.equals(other.getOperator())));
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
        if (getNo_Month() != null) {
            _hashCode += getNo_Month().hashCode();
        }
        if (getMethod() != null) {
            _hashCode += getMethod().hashCode();
        }
        if (getIPTVACCOUNT() != null) {
            _hashCode += getIPTVACCOUNT().hashCode();
        }
        if (getStartDate() != null) {
            _hashCode += getStartDate().hashCode();
        }
        if (getOperator() != null) {
            _hashCode += getOperator().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MethodChangePayRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "MethodChangePayRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("no_Month");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "No_Month"));
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
        elemField.setFieldName("IPTVACCOUNT");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "IPTVACCOUNT"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("startDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "StartDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("operator");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "Operator"));
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
