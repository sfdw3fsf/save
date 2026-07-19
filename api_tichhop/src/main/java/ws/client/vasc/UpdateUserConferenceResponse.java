/**
 * UpdateUserConferenceResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class UpdateUserConferenceResponse  implements java.io.Serializable {
    private ChangeSubscriberVO updateUserConferenceResult;

    public UpdateUserConferenceResponse() {
    }

    public UpdateUserConferenceResponse(
           ChangeSubscriberVO updateUserConferenceResult) {
           this.updateUserConferenceResult = updateUserConferenceResult;
    }


    /**
     * Gets the updateUserConferenceResult value for this UpdateUserConferenceResponse.
     * 
     * @return updateUserConferenceResult
     */
    public ChangeSubscriberVO getUpdateUserConferenceResult() {
        return updateUserConferenceResult;
    }


    /**
     * Sets the updateUserConferenceResult value for this UpdateUserConferenceResponse.
     * 
     * @param updateUserConferenceResult
     */
    public void setUpdateUserConferenceResult(ChangeSubscriberVO updateUserConferenceResult) {
        this.updateUserConferenceResult = updateUserConferenceResult;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof UpdateUserConferenceResponse)) return false;
        UpdateUserConferenceResponse other = (UpdateUserConferenceResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.updateUserConferenceResult==null && other.getUpdateUserConferenceResult()==null) || 
             (this.updateUserConferenceResult!=null &&
              this.updateUserConferenceResult.equals(other.getUpdateUserConferenceResult())));
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
        if (getUpdateUserConferenceResult() != null) {
            _hashCode += getUpdateUserConferenceResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(UpdateUserConferenceResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">UpdateUserConferenceResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("updateUserConferenceResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "UpdateUserConferenceResult"));
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
