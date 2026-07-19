/**
 * Get_ProjectBalanceGetApproval_byProject_TinhResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.qldt;

public class Get_ProjectBalanceGetApproval_byProject_TinhResponse  implements java.io.Serializable {
    private String get_ProjectBalanceGetApproval_byProject_TinhResult;

    public Get_ProjectBalanceGetApproval_byProject_TinhResponse() {
    }

    public Get_ProjectBalanceGetApproval_byProject_TinhResponse(
           String get_ProjectBalanceGetApproval_byProject_TinhResult) {
           this.get_ProjectBalanceGetApproval_byProject_TinhResult = get_ProjectBalanceGetApproval_byProject_TinhResult;
    }


    /**
     * Gets the get_ProjectBalanceGetApproval_byProject_TinhResult value for this Get_ProjectBalanceGetApproval_byProject_TinhResponse.
     * 
     * @return get_ProjectBalanceGetApproval_byProject_TinhResult
     */
    public String getGet_ProjectBalanceGetApproval_byProject_TinhResult() {
        return get_ProjectBalanceGetApproval_byProject_TinhResult;
    }


    /**
     * Sets the get_ProjectBalanceGetApproval_byProject_TinhResult value for this Get_ProjectBalanceGetApproval_byProject_TinhResponse.
     * 
     * @param get_ProjectBalanceGetApproval_byProject_TinhResult
     */
    public void setGet_ProjectBalanceGetApproval_byProject_TinhResult(String get_ProjectBalanceGetApproval_byProject_TinhResult) {
        this.get_ProjectBalanceGetApproval_byProject_TinhResult = get_ProjectBalanceGetApproval_byProject_TinhResult;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof Get_ProjectBalanceGetApproval_byProject_TinhResponse)) return false;
        Get_ProjectBalanceGetApproval_byProject_TinhResponse other = (Get_ProjectBalanceGetApproval_byProject_TinhResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.get_ProjectBalanceGetApproval_byProject_TinhResult==null && other.getGet_ProjectBalanceGetApproval_byProject_TinhResult()==null) || 
             (this.get_ProjectBalanceGetApproval_byProject_TinhResult!=null &&
              this.get_ProjectBalanceGetApproval_byProject_TinhResult.equals(other.getGet_ProjectBalanceGetApproval_byProject_TinhResult())));
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
        if (getGet_ProjectBalanceGetApproval_byProject_TinhResult() != null) {
            _hashCode += getGet_ProjectBalanceGetApproval_byProject_TinhResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Get_ProjectBalanceGetApproval_byProject_TinhResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">Get_ProjectBalanceGetApproval_byProject_TinhResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("get_ProjectBalanceGetApproval_byProject_TinhResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "Get_ProjectBalanceGetApproval_byProject_TinhResult"));
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
