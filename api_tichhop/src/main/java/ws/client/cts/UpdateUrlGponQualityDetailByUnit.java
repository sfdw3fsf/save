/**
 * UpdateUrlGponQualityDetailByUnit.java
 * <p>
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.cts;

public class UpdateUrlGponQualityDetailByUnit implements java.io.Serializable {
    private int unitID;

    private java.util.Calendar statisticDate;

    public UpdateUrlGponQualityDetailByUnit() {
    }

    public UpdateUrlGponQualityDetailByUnit(
            int unitID,
            java.util.Calendar statisticDate) {
        this.unitID = unitID;
        this.statisticDate = statisticDate;
    }


    /**
     * Gets the unitID value for this UpdateUrlGponQualityDetailByUnit.
     *
     * @return unitID
     */
    public int getUnitID() {
        return unitID;
    }


    /**
     * Sets the unitID value for this UpdateUrlGponQualityDetailByUnit.
     *
     * @param unitID
     */
    public void setUnitID(int unitID) {
        this.unitID = unitID;
    }


    /**
     * Gets the statisticDate value for this UpdateUrlGponQualityDetailByUnit.
     *
     * @return statisticDate
     */
    public java.util.Calendar getStatisticDate() {
        return statisticDate;
    }


    /**
     * Sets the statisticDate value for this UpdateUrlGponQualityDetailByUnit.
     *
     * @param statisticDate
     */
    public void setStatisticDate(java.util.Calendar statisticDate) {
        this.statisticDate = statisticDate;
    }

    private java.lang.Object __equalsCalc = null;

    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof UpdateUrlGponQualityDetailByUnit)) return false;
        UpdateUrlGponQualityDetailByUnit other = (UpdateUrlGponQualityDetailByUnit) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true &&
                this.unitID == other.getUnitID() &&
                ((this.statisticDate == null && other.getStatisticDate() == null) ||
                        (this.statisticDate != null &&
                                this.statisticDate.equals(other.getStatisticDate())));
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
        _hashCode += getUnitID();
        if (getStatisticDate() != null) {
            _hashCode += getStatisticDate().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
            new org.apache.axis.description.TypeDesc(UpdateUrlGponQualityDetailByUnit.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://vnpt.vn/", ">UpdateUrlGponQualityDetailByUnit"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("unitID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://vnpt.vn/", "unitID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("statisticDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://vnpt.vn/", "statisticDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
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
