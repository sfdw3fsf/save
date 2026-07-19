/**
 * ChangeSubscriberResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class ChangeSubscriberResponse  implements java.io.Serializable {
    private ChangeSubscriberVO changeSubscriberResult;

    public ChangeSubscriberResponse() {
    }

    public ChangeSubscriberResponse(
           ChangeSubscriberVO changeSubscriberResult) {
           this.changeSubscriberResult = changeSubscriberResult;
    }


    /**
     * Gets the changeSubscriberResult value for this ChangeSubscriberResponse.
     * 
     * @return changeSubscriberResult
     */
    public ChangeSubscriberVO getChangeSubscriberResult() {
        return changeSubscriberResult;
    }


    /**
     * Sets the changeSubscriberResult value for this ChangeSubscriberResponse.
     * 
     * @param changeSubscriberResult
     */
    public void setChangeSubscriberResult(ChangeSubscriberVO changeSubscriberResult) {
        this.changeSubscriberResult = changeSubscriberResult;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof ChangeSubscriberResponse)) return false;
        ChangeSubscriberResponse other = (ChangeSubscriberResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.changeSubscriberResult==null && other.getChangeSubscriberResult()==null) || 
             (this.changeSubscriberResult!=null &&
              this.changeSubscriberResult.equals(other.getChangeSubscriberResult())));
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
        if (getChangeSubscriberResult() != null) {
            _hashCode += getChangeSubscriberResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ChangeSubscriberResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">ChangeSubscriberResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("changeSubscriberResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "ChangeSubscriberResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "ChangeSubscriberVO"));
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
