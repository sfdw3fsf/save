/**
 * UpdateSubTrialResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class UpdateSubTrialResponse  implements java.io.Serializable {
    private ChangeSubscriberVO updateSubTrialResult;

    public UpdateSubTrialResponse() {
    }

    public UpdateSubTrialResponse(
           ChangeSubscriberVO updateSubTrialResult) {
           this.updateSubTrialResult = updateSubTrialResult;
    }


    /**
     * Gets the updateSubTrialResult value for this UpdateSubTrialResponse.
     * 
     * @return updateSubTrialResult
     */
    public ChangeSubscriberVO getUpdateSubTrialResult() {
        return updateSubTrialResult;
    }


    /**
     * Sets the updateSubTrialResult value for this UpdateSubTrialResponse.
     * 
     * @param updateSubTrialResult
     */
    public void setUpdateSubTrialResult(ChangeSubscriberVO updateSubTrialResult) {
        this.updateSubTrialResult = updateSubTrialResult;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof UpdateSubTrialResponse)) return false;
        UpdateSubTrialResponse other = (UpdateSubTrialResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.updateSubTrialResult==null && other.getUpdateSubTrialResult()==null) || 
             (this.updateSubTrialResult!=null &&
              this.updateSubTrialResult.equals(other.getUpdateSubTrialResult())));
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
        if (getUpdateSubTrialResult() != null) {
            _hashCode += getUpdateSubTrialResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(UpdateSubTrialResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">UpdateSubTrialResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("updateSubTrialResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "UpdateSubTrialResult"));
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
