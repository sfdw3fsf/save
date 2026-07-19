/**
 * GetUp_AcceptanceGeneralListFromAssetResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.qldt;

public class GetUp_AcceptanceGeneralListFromAssetResponse  implements java.io.Serializable {
    private String getUp_AcceptanceGeneralListFromAssetResult;

    public GetUp_AcceptanceGeneralListFromAssetResponse() {
    }

    public GetUp_AcceptanceGeneralListFromAssetResponse(
           String getUp_AcceptanceGeneralListFromAssetResult) {
           this.getUp_AcceptanceGeneralListFromAssetResult = getUp_AcceptanceGeneralListFromAssetResult;
    }


    /**
     * Gets the getUp_AcceptanceGeneralListFromAssetResult value for this GetUp_AcceptanceGeneralListFromAssetResponse.
     * 
     * @return getUp_AcceptanceGeneralListFromAssetResult
     */
    public String getGetUp_AcceptanceGeneralListFromAssetResult() {
        return getUp_AcceptanceGeneralListFromAssetResult;
    }


    /**
     * Sets the getUp_AcceptanceGeneralListFromAssetResult value for this GetUp_AcceptanceGeneralListFromAssetResponse.
     * 
     * @param getUp_AcceptanceGeneralListFromAssetResult
     */
    public void setGetUp_AcceptanceGeneralListFromAssetResult(String getUp_AcceptanceGeneralListFromAssetResult) {
        this.getUp_AcceptanceGeneralListFromAssetResult = getUp_AcceptanceGeneralListFromAssetResult;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof GetUp_AcceptanceGeneralListFromAssetResponse)) return false;
        GetUp_AcceptanceGeneralListFromAssetResponse other = (GetUp_AcceptanceGeneralListFromAssetResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.getUp_AcceptanceGeneralListFromAssetResult==null && other.getGetUp_AcceptanceGeneralListFromAssetResult()==null) || 
             (this.getUp_AcceptanceGeneralListFromAssetResult!=null &&
              this.getUp_AcceptanceGeneralListFromAssetResult.equals(other.getGetUp_AcceptanceGeneralListFromAssetResult())));
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
        if (getGetUp_AcceptanceGeneralListFromAssetResult() != null) {
            _hashCode += getGetUp_AcceptanceGeneralListFromAssetResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetUp_AcceptanceGeneralListFromAssetResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">GetUp_AcceptanceGeneralListFromAssetResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getUp_AcceptanceGeneralListFromAssetResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "GetUp_AcceptanceGeneralListFromAssetResult"));
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
