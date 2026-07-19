/**
 * CancelSubscriberResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class CancelSubscriberResponse  implements java.io.Serializable {
    private CancelSubscriberVO cancelSubscriberResult;

    public CancelSubscriberResponse() {
    }

    public CancelSubscriberResponse(
           CancelSubscriberVO cancelSubscriberResult) {
           this.cancelSubscriberResult = cancelSubscriberResult;
    }


    /**
     * Gets the cancelSubscriberResult value for this CancelSubscriberResponse.
     * 
     * @return cancelSubscriberResult
     */
    public CancelSubscriberVO getCancelSubscriberResult() {
        return cancelSubscriberResult;
    }


    /**
     * Sets the cancelSubscriberResult value for this CancelSubscriberResponse.
     * 
     * @param cancelSubscriberResult
     */
    public void setCancelSubscriberResult(CancelSubscriberVO cancelSubscriberResult) {
        this.cancelSubscriberResult = cancelSubscriberResult;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof CancelSubscriberResponse)) return false;
        CancelSubscriberResponse other = (CancelSubscriberResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.cancelSubscriberResult==null && other.getCancelSubscriberResult()==null) || 
             (this.cancelSubscriberResult!=null &&
              this.cancelSubscriberResult.equals(other.getCancelSubscriberResult())));
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
        if (getCancelSubscriberResult() != null) {
            _hashCode += getCancelSubscriberResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CancelSubscriberResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">CancelSubscriberResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cancelSubscriberResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "CancelSubscriberResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "CancelSubscriberVO"));
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
