/**
 * CancelAddOnResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class CancelAddOnResponse  implements java.io.Serializable {
    private CommonVO cancelAddOnResult;

    public CancelAddOnResponse() {
    }

    public CancelAddOnResponse(
           CommonVO cancelAddOnResult) {
           this.cancelAddOnResult = cancelAddOnResult;
    }


    /**
     * Gets the cancelAddOnResult value for this CancelAddOnResponse.
     * 
     * @return cancelAddOnResult
     */
    public CommonVO getCancelAddOnResult() {
        return cancelAddOnResult;
    }


    /**
     * Sets the cancelAddOnResult value for this CancelAddOnResponse.
     * 
     * @param cancelAddOnResult
     */
    public void setCancelAddOnResult(CommonVO cancelAddOnResult) {
        this.cancelAddOnResult = cancelAddOnResult;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof CancelAddOnResponse)) return false;
        CancelAddOnResponse other = (CancelAddOnResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.cancelAddOnResult==null && other.getCancelAddOnResult()==null) || 
             (this.cancelAddOnResult!=null &&
              this.cancelAddOnResult.equals(other.getCancelAddOnResult())));
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
        if (getCancelAddOnResult() != null) {
            _hashCode += getCancelAddOnResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CancelAddOnResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">CancelAddOnResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cancelAddOnResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "CancelAddOnResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "CommonVO"));
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
