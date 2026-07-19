/**
 * GetBillByMonthResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class GetBillByMonthResponse  implements java.io.Serializable {
    private GetBillByMonthVO getBillByMonthResult;

    public GetBillByMonthResponse() {
    }

    public GetBillByMonthResponse(
           GetBillByMonthVO getBillByMonthResult) {
           this.getBillByMonthResult = getBillByMonthResult;
    }


    /**
     * Gets the getBillByMonthResult value for this GetBillByMonthResponse.
     * 
     * @return getBillByMonthResult
     */
    public GetBillByMonthVO getGetBillByMonthResult() {
        return getBillByMonthResult;
    }


    /**
     * Sets the getBillByMonthResult value for this GetBillByMonthResponse.
     * 
     * @param getBillByMonthResult
     */
    public void setGetBillByMonthResult(GetBillByMonthVO getBillByMonthResult) {
        this.getBillByMonthResult = getBillByMonthResult;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof GetBillByMonthResponse)) return false;
        GetBillByMonthResponse other = (GetBillByMonthResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.getBillByMonthResult==null && other.getGetBillByMonthResult()==null) || 
             (this.getBillByMonthResult!=null &&
              this.getBillByMonthResult.equals(other.getGetBillByMonthResult())));
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
        if (getGetBillByMonthResult() != null) {
            _hashCode += getGetBillByMonthResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetBillByMonthResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">GetBillByMonthResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getBillByMonthResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "GetBillByMonthResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "GetBillByMonthVO"));
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
