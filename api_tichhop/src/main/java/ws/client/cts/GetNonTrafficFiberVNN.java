/**
 * GetNonTrafficFiberVNN.java
 * <p>
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.cts;

public class GetNonTrafficFiberVNN implements java.io.Serializable {
    private int unitID;

    private java.util.Calendar fromDate;

    private java.util.Calendar toDate;

    private int backgroundTraffic;

    public GetNonTrafficFiberVNN() {
    }

    public GetNonTrafficFiberVNN(
            int unitID,
            java.util.Calendar fromDate,
            java.util.Calendar toDate,
            int backgroundTraffic) {
        this.unitID = unitID;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.backgroundTraffic = backgroundTraffic;
    }


    /**
     * Gets the unitID value for this GetNonTrafficFiberVNN.
     *
     * @return unitID
     */
    public int getUnitID() {
        return unitID;
    }


    /**
     * Sets the unitID value for this GetNonTrafficFiberVNN.
     *
     * @param unitID
     */
    public void setUnitID(int unitID) {
        this.unitID = unitID;
    }


    /**
     * Gets the fromDate value for this GetNonTrafficFiberVNN.
     *
     * @return fromDate
     */
    public java.util.Calendar getFromDate() {
        return fromDate;
    }


    /**
     * Sets the fromDate value for this GetNonTrafficFiberVNN.
     *
     * @param fromDate
     */
    public void setFromDate(java.util.Calendar fromDate) {
        this.fromDate = fromDate;
    }


    /**
     * Gets the toDate value for this GetNonTrafficFiberVNN.
     *
     * @return toDate
     */
    public java.util.Calendar getToDate() {
        return toDate;
    }


    /**
     * Sets the toDate value for this GetNonTrafficFiberVNN.
     *
     * @param toDate
     */
    public void setToDate(java.util.Calendar toDate) {
        this.toDate = toDate;
    }


    /**
     * Gets the backgroundTraffic value for this GetNonTrafficFiberVNN.
     *
     * @return backgroundTraffic
     */
    public int getBackgroundTraffic() {
        return backgroundTraffic;
    }


    /**
     * Sets the backgroundTraffic value for this GetNonTrafficFiberVNN.
     *
     * @param backgroundTraffic
     */
    public void setBackgroundTraffic(int backgroundTraffic) {
        this.backgroundTraffic = backgroundTraffic;
    }

    private java.lang.Object __equalsCalc = null;

    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetNonTrafficFiberVNN)) return false;
        GetNonTrafficFiberVNN other = (GetNonTrafficFiberVNN) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true &&
                this.unitID == other.getUnitID() &&
                ((this.fromDate == null && other.getFromDate() == null) ||
                        (this.fromDate != null &&
                                this.fromDate.equals(other.getFromDate()))) &&
                ((this.toDate == null && other.getToDate() == null) ||
                        (this.toDate != null &&
                                this.toDate.equals(other.getToDate()))) &&
                this.backgroundTraffic == other.getBackgroundTraffic();
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
        if (getFromDate() != null) {
            _hashCode += getFromDate().hashCode();
        }
        if (getToDate() != null) {
            _hashCode += getToDate().hashCode();
        }
        _hashCode += getBackgroundTraffic();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
            new org.apache.axis.description.TypeDesc(GetNonTrafficFiberVNN.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://vnpt.vn/", ">GetNonTrafficFiberVNN"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("unitID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://vnpt.vn/", "UnitID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fromDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://vnpt.vn/", "fromDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("toDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://vnpt.vn/", "toDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("backgroundTraffic");
        elemField.setXmlName(new javax.xml.namespace.QName("http://vnpt.vn/", "backgroundTraffic"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
