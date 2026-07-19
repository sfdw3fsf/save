/**
 * UpdatePrepaidResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class UpdatePrepaidResponse  implements java.io.Serializable {
    private ChangeSubscriberVO updatePrepaidResult;

    public UpdatePrepaidResponse() {
    }

    public UpdatePrepaidResponse(
           ChangeSubscriberVO updatePrepaidResult) {
           this.updatePrepaidResult = updatePrepaidResult;
    }


    /**
     * Gets the updatePrepaidResult value for this UpdatePrepaidResponse.
     * 
     * @return updatePrepaidResult
     */
    public ChangeSubscriberVO getUpdatePrepaidResult() {
        return updatePrepaidResult;
    }


    /**
     * Sets the updatePrepaidResult value for this UpdatePrepaidResponse.
     * 
     * @param updatePrepaidResult
     */
    public void setUpdatePrepaidResult(ChangeSubscriberVO updatePrepaidResult) {
        this.updatePrepaidResult = updatePrepaidResult;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof UpdatePrepaidResponse)) return false;
        UpdatePrepaidResponse other = (UpdatePrepaidResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.updatePrepaidResult==null && other.getUpdatePrepaidResult()==null) || 
             (this.updatePrepaidResult!=null &&
              this.updatePrepaidResult.equals(other.getUpdatePrepaidResult())));
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
        if (getUpdatePrepaidResult() != null) {
            _hashCode += getUpdatePrepaidResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(UpdatePrepaidResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">UpdatePrepaidResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("updatePrepaidResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "UpdatePrepaidResult"));
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
