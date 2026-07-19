/**
 * CustExtSysEnum.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class CustExtSysEnum implements java.io.Serializable {
    private String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected CustExtSysEnum(String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final String _NORMAR = "NORMAR";
    public static final String _SELFCARE = "SELFCARE";
    public static final String _ECOSYTEM = "ECOSYTEM";
    public static final String _B2B_NORMAL = "B2B_NORMAL";
    public static final String _B2B_IPRF = "B2B_IPRF";
    public static final CustExtSysEnum NORMAR = new CustExtSysEnum(_NORMAR);
    public static final CustExtSysEnum SELFCARE = new CustExtSysEnum(_SELFCARE);
    public static final CustExtSysEnum ECOSYTEM = new CustExtSysEnum(_ECOSYTEM);
    public static final CustExtSysEnum B2B_NORMAL = new CustExtSysEnum(_B2B_NORMAL);
    public static final CustExtSysEnum B2B_IPRF = new CustExtSysEnum(_B2B_IPRF);
    public String getValue() { return _value_;}
    public static CustExtSysEnum fromValue(String value)
          throws IllegalArgumentException {
        CustExtSysEnum enumeration = (CustExtSysEnum)
            _table_.get(value);
        if (enumeration==null) throw new IllegalArgumentException();
        return enumeration;
    }
    public static CustExtSysEnum fromString(String value)
          throws IllegalArgumentException {
        return fromValue(value);
    }
    public boolean equals(Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public String toString() { return _value_;}
    public Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static org.apache.axis.encoding.Serializer getSerializer(
           String mechType,
           Class _javaType,
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumSerializer(
            _javaType, _xmlType);
    }
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           String mechType,
           Class _javaType,
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumDeserializer(
            _javaType, _xmlType);
    }
    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CustExtSysEnum.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "CustExtSysEnum"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
