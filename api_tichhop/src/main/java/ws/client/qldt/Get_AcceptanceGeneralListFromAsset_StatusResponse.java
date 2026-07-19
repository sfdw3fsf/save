/**
 * Get_AcceptanceGeneralListFromAsset_StatusResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.qldt;

public class Get_AcceptanceGeneralListFromAsset_StatusResponse  implements java.io.Serializable {
    private String get_AcceptanceGeneralListFromAsset_StatusResult;

    public Get_AcceptanceGeneralListFromAsset_StatusResponse() {
    }

    public Get_AcceptanceGeneralListFromAsset_StatusResponse(
           String get_AcceptanceGeneralListFromAsset_StatusResult) {
           this.get_AcceptanceGeneralListFromAsset_StatusResult = get_AcceptanceGeneralListFromAsset_StatusResult;
    }


    /**
     * Gets the get_AcceptanceGeneralListFromAsset_StatusResult value for this Get_AcceptanceGeneralListFromAsset_StatusResponse.
     * 
     * @return get_AcceptanceGeneralListFromAsset_StatusResult
     */
    public String getGet_AcceptanceGeneralListFromAsset_StatusResult() {
        return get_AcceptanceGeneralListFromAsset_StatusResult;
    }


    /**
     * Sets the get_AcceptanceGeneralListFromAsset_StatusResult value for this Get_AcceptanceGeneralListFromAsset_StatusResponse.
     * 
     * @param get_AcceptanceGeneralListFromAsset_StatusResult
     */
    public void setGet_AcceptanceGeneralListFromAsset_StatusResult(String get_AcceptanceGeneralListFromAsset_StatusResult) {
        this.get_AcceptanceGeneralListFromAsset_StatusResult = get_AcceptanceGeneralListFromAsset_StatusResult;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof Get_AcceptanceGeneralListFromAsset_StatusResponse)) return false;
        Get_AcceptanceGeneralListFromAsset_StatusResponse other = (Get_AcceptanceGeneralListFromAsset_StatusResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.get_AcceptanceGeneralListFromAsset_StatusResult==null && other.getGet_AcceptanceGeneralListFromAsset_StatusResult()==null) || 
             (this.get_AcceptanceGeneralListFromAsset_StatusResult!=null &&
              this.get_AcceptanceGeneralListFromAsset_StatusResult.equals(other.getGet_AcceptanceGeneralListFromAsset_StatusResult())));
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
        if (getGet_AcceptanceGeneralListFromAsset_StatusResult() != null) {
            _hashCode += getGet_AcceptanceGeneralListFromAsset_StatusResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Get_AcceptanceGeneralListFromAsset_StatusResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">Get_AcceptanceGeneralListFromAsset_StatusResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("get_AcceptanceGeneralListFromAsset_StatusResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "Get_AcceptanceGeneralListFromAsset_StatusResult"));
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
