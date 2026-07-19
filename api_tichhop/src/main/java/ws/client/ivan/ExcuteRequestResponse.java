/**
 * ExcuteRequestResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.ivan;

public class ExcuteRequestResponse  implements java.io.Serializable {
    private IVAN_Respond excuteRequestResult;

    public ExcuteRequestResponse() {
    }

    public ExcuteRequestResponse(
           IVAN_Respond excuteRequestResult) {
           this.excuteRequestResult = excuteRequestResult;
    }


    /**
     * Gets the excuteRequestResult value for this ExcuteRequestResponse.
     * 
     * @return excuteRequestResult
     */
    public IVAN_Respond getExcuteRequestResult() {
        return excuteRequestResult;
    }


    /**
     * Sets the excuteRequestResult value for this ExcuteRequestResponse.
     * 
     * @param excuteRequestResult
     */
    public void setExcuteRequestResult(IVAN_Respond excuteRequestResult) {
        this.excuteRequestResult = excuteRequestResult;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof ExcuteRequestResponse)) return false;
        ExcuteRequestResponse other = (ExcuteRequestResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.excuteRequestResult==null && other.getExcuteRequestResult()==null) || 
             (this.excuteRequestResult!=null &&
              this.excuteRequestResult.equals(other.getExcuteRequestResult())));
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
        if (getExcuteRequestResult() != null) {
            _hashCode += getExcuteRequestResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ExcuteRequestResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">ExcuteRequestResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("excuteRequestResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "ExcuteRequestResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/System", "IVAN_Respond"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
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
