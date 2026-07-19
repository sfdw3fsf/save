/**
 * CancelSubscriberV2Response.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class CancelSubscriberV2Response  implements java.io.Serializable {
    private CancelSubscriberVO cancelSubscriberV2Result;

    public CancelSubscriberV2Response() {
    }

    public CancelSubscriberV2Response(
           CancelSubscriberVO cancelSubscriberV2Result) {
           this.cancelSubscriberV2Result = cancelSubscriberV2Result;
    }


    /**
     * Gets the cancelSubscriberV2Result value for this CancelSubscriberV2Response.
     * 
     * @return cancelSubscriberV2Result
     */
    public CancelSubscriberVO getCancelSubscriberV2Result() {
        return cancelSubscriberV2Result;
    }


    /**
     * Sets the cancelSubscriberV2Result value for this CancelSubscriberV2Response.
     * 
     * @param cancelSubscriberV2Result
     */
    public void setCancelSubscriberV2Result(CancelSubscriberVO cancelSubscriberV2Result) {
        this.cancelSubscriberV2Result = cancelSubscriberV2Result;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof CancelSubscriberV2Response)) return false;
        CancelSubscriberV2Response other = (CancelSubscriberV2Response) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.cancelSubscriberV2Result==null && other.getCancelSubscriberV2Result()==null) || 
             (this.cancelSubscriberV2Result!=null &&
              this.cancelSubscriberV2Result.equals(other.getCancelSubscriberV2Result())));
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
        if (getCancelSubscriberV2Result() != null) {
            _hashCode += getCancelSubscriberV2Result().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CancelSubscriberV2Response.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">CancelSubscriberV2Response"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cancelSubscriberV2Result");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "CancelSubscriberV2Result"));
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
