/**
 * ChangeDslPortProfileByPortVisa.java
 * <p>
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.cts;

public class ChangeDslPortProfileByPortVisa implements java.io.Serializable {
    private java.lang.String visaString;

    private java.lang.String profileName;

    public ChangeDslPortProfileByPortVisa() {
    }

    public ChangeDslPortProfileByPortVisa(
            java.lang.String visaString,
            java.lang.String profileName) {
        this.visaString = visaString;
        this.profileName = profileName;
    }


    /**
     * Gets the visaString value for this ChangeDslPortProfileByPortVisa.
     *
     * @return visaString
     */
    public java.lang.String getVisaString() {
        return visaString;
    }


    /**
     * Sets the visaString value for this ChangeDslPortProfileByPortVisa.
     *
     * @param visaString
     */
    public void setVisaString(java.lang.String visaString) {
        this.visaString = visaString;
    }


    /**
     * Gets the profileName value for this ChangeDslPortProfileByPortVisa.
     *
     * @return profileName
     */
    public java.lang.String getProfileName() {
        return profileName;
    }


    /**
     * Sets the profileName value for this ChangeDslPortProfileByPortVisa.
     *
     * @param profileName
     */
    public void setProfileName(java.lang.String profileName) {
        this.profileName = profileName;
    }

    private java.lang.Object __equalsCalc = null;

    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ChangeDslPortProfileByPortVisa)) return false;
        ChangeDslPortProfileByPortVisa other = (ChangeDslPortProfileByPortVisa) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true &&
                ((this.visaString == null && other.getVisaString() == null) ||
                        (this.visaString != null &&
                                this.visaString.equals(other.getVisaString()))) &&
                ((this.profileName == null && other.getProfileName() == null) ||
                        (this.profileName != null &&
                                this.profileName.equals(other.getProfileName())));
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
        if (getVisaString() != null) {
            _hashCode += getVisaString().hashCode();
        }
        if (getProfileName() != null) {
            _hashCode += getProfileName().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
            new org.apache.axis.description.TypeDesc(ChangeDslPortProfileByPortVisa.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://vnpt.vn/", ">ChangeDslPortProfileByPortVisa"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("visaString");
        elemField.setXmlName(new javax.xml.namespace.QName("http://vnpt.vn/", "visaString"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("profileName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://vnpt.vn/", "profileName"));
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
            java.lang.String mechType,
            java.lang.Class _javaType,
            javax.xml.namespace.QName _xmlType) {
        return
                new org.apache.axis.encoding.ser.BeanSerializer(
                        _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
            java.lang.String mechType,
            java.lang.Class _javaType,
            javax.xml.namespace.QName _xmlType) {
        return
                new org.apache.axis.encoding.ser.BeanDeserializer(
                        _javaType, _xmlType, typeDesc);
    }

}
