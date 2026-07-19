/**
 * GetBillPackageResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class GetBillPackageResponse  implements java.io.Serializable {
    private GetPackageVO getBillPackageResult;

    public GetBillPackageResponse() {
    }

    public GetBillPackageResponse(
           GetPackageVO getBillPackageResult) {
           this.getBillPackageResult = getBillPackageResult;
    }


    /**
     * Gets the getBillPackageResult value for this GetBillPackageResponse.
     * 
     * @return getBillPackageResult
     */
    public GetPackageVO getGetBillPackageResult() {
        return getBillPackageResult;
    }


    /**
     * Sets the getBillPackageResult value for this GetBillPackageResponse.
     * 
     * @param getBillPackageResult
     */
    public void setGetBillPackageResult(GetPackageVO getBillPackageResult) {
        this.getBillPackageResult = getBillPackageResult;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof GetBillPackageResponse)) return false;
        GetBillPackageResponse other = (GetBillPackageResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.getBillPackageResult==null && other.getGetBillPackageResult()==null) || 
             (this.getBillPackageResult!=null &&
              this.getBillPackageResult.equals(other.getGetBillPackageResult())));
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
        if (getGetBillPackageResult() != null) {
            _hashCode += getGetBillPackageResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetBillPackageResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">GetBillPackageResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getBillPackageResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "GetBillPackageResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "GetPackageVO"));
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
