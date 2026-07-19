/**
 * ResetDslPort.java
 * <p>
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.cts;

public class ResetDslPort implements java.io.Serializable {
    private java.lang.String deviceIp;

    private int frame;

    private int slot;

    private int port;

    public ResetDslPort() {
    }

    public ResetDslPort(
            java.lang.String deviceIp,
            int frame,
            int slot,
            int port) {
        this.deviceIp = deviceIp;
        this.frame = frame;
        this.slot = slot;
        this.port = port;
    }


    /**
     * Gets the deviceIp value for this ResetDslPort.
     *
     * @return deviceIp
     */
    public java.lang.String getDeviceIp() {
        return deviceIp;
    }


    /**
     * Sets the deviceIp value for this ResetDslPort.
     *
     * @param deviceIp
     */
    public void setDeviceIp(java.lang.String deviceIp) {
        this.deviceIp = deviceIp;
    }


    /**
     * Gets the frame value for this ResetDslPort.
     *
     * @return frame
     */
    public int getFrame() {
        return frame;
    }


    /**
     * Sets the frame value for this ResetDslPort.
     *
     * @param frame
     */
    public void setFrame(int frame) {
        this.frame = frame;
    }


    /**
     * Gets the slot value for this ResetDslPort.
     *
     * @return slot
     */
    public int getSlot() {
        return slot;
    }


    /**
     * Sets the slot value for this ResetDslPort.
     *
     * @param slot
     */
    public void setSlot(int slot) {
        this.slot = slot;
    }


    /**
     * Gets the port value for this ResetDslPort.
     *
     * @return port
     */
    public int getPort() {
        return port;
    }


    /**
     * Sets the port value for this ResetDslPort.
     *
     * @param port
     */
    public void setPort(int port) {
        this.port = port;
    }

    private java.lang.Object __equalsCalc = null;

    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ResetDslPort)) return false;
        ResetDslPort other = (ResetDslPort) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true &&
                ((this.deviceIp == null && other.getDeviceIp() == null) ||
                        (this.deviceIp != null &&
                                this.deviceIp.equals(other.getDeviceIp()))) &&
                this.frame == other.getFrame() &&
                this.slot == other.getSlot() &&
                this.port == other.getPort();
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
        if (getDeviceIp() != null) {
            _hashCode += getDeviceIp().hashCode();
        }
        _hashCode += getFrame();
        _hashCode += getSlot();
        _hashCode += getPort();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
            new org.apache.axis.description.TypeDesc(ResetDslPort.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://vnpt.vn/", ">ResetDslPort"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("deviceIp");
        elemField.setXmlName(new javax.xml.namespace.QName("http://vnpt.vn/", "deviceIp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("frame");
        elemField.setXmlName(new javax.xml.namespace.QName("http://vnpt.vn/", "frame"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("slot");
        elemField.setXmlName(new javax.xml.namespace.QName("http://vnpt.vn/", "slot"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("port");
        elemField.setXmlName(new javax.xml.namespace.QName("http://vnpt.vn/", "port"));
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
