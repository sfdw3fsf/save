/**
 * BRASModel.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.visa.application;

public class BRASModel  implements java.io.Serializable {
    private String areaCode;

    private String areaName;

    private int beginIndex;

    private String bras_type;

    private long companyId;

    private String dealer;

    private String[] dealerByIndex;

    private int endIndex;

    private long id;

    private String ip;

    private String ipPart1;

    private String ipPart2;

    private String ipPart3;

    private String ipPart4;

    private String[] listDealer;

    private String listDealerCodes;

    private String[] listPostOffice;

    private String listPostOfficeCodes;

    private String name;

    private String owner;

    private String ownerName;

    private String port;

    private String postOffice;

    private String[] postOfficeByIndex;

    private String searchDealer;

    private String searchPostOffice;

    private String secret;

    private String usedUnits;

    private long userId;

    public BRASModel() {
    }

    public BRASModel(
           String areaCode,
           String areaName,
           int beginIndex,
           String bras_type,
           long companyId,
           String dealer,
           String[] dealerByIndex,
           int endIndex,
           long id,
           String ip,
           String ipPart1,
           String ipPart2,
           String ipPart3,
           String ipPart4,
           String[] listDealer,
           String listDealerCodes,
           String[] listPostOffice,
           String listPostOfficeCodes,
           String name,
           String owner,
           String ownerName,
           String port,
           String postOffice,
           String[] postOfficeByIndex,
           String searchDealer,
           String searchPostOffice,
           String secret,
           String usedUnits,
           long userId) {
           this.areaCode = areaCode;
           this.areaName = areaName;
           this.beginIndex = beginIndex;
           this.bras_type = bras_type;
           this.companyId = companyId;
           this.dealer = dealer;
           this.dealerByIndex = dealerByIndex;
           this.endIndex = endIndex;
           this.id = id;
           this.ip = ip;
           this.ipPart1 = ipPart1;
           this.ipPart2 = ipPart2;
           this.ipPart3 = ipPart3;
           this.ipPart4 = ipPart4;
           this.listDealer = listDealer;
           this.listDealerCodes = listDealerCodes;
           this.listPostOffice = listPostOffice;
           this.listPostOfficeCodes = listPostOfficeCodes;
           this.name = name;
           this.owner = owner;
           this.ownerName = ownerName;
           this.port = port;
           this.postOffice = postOffice;
           this.postOfficeByIndex = postOfficeByIndex;
           this.searchDealer = searchDealer;
           this.searchPostOffice = searchPostOffice;
           this.secret = secret;
           this.usedUnits = usedUnits;
           this.userId = userId;
    }


    /**
     * Gets the areaCode value for this BRASModel.
     * 
     * @return areaCode
     */
    public String getAreaCode() {
        return areaCode;
    }


    /**
     * Sets the areaCode value for this BRASModel.
     * 
     * @param areaCode
     */
    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }


    /**
     * Gets the areaName value for this BRASModel.
     * 
     * @return areaName
     */
    public String getAreaName() {
        return areaName;
    }


    /**
     * Sets the areaName value for this BRASModel.
     * 
     * @param areaName
     */
    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }


    /**
     * Gets the beginIndex value for this BRASModel.
     * 
     * @return beginIndex
     */
    public int getBeginIndex() {
        return beginIndex;
    }


    /**
     * Sets the beginIndex value for this BRASModel.
     * 
     * @param beginIndex
     */
    public void setBeginIndex(int beginIndex) {
        this.beginIndex = beginIndex;
    }


    /**
     * Gets the bras_type value for this BRASModel.
     * 
     * @return bras_type
     */
    public String getBras_type() {
        return bras_type;
    }


    /**
     * Sets the bras_type value for this BRASModel.
     * 
     * @param bras_type
     */
    public void setBras_type(String bras_type) {
        this.bras_type = bras_type;
    }


    /**
     * Gets the companyId value for this BRASModel.
     * 
     * @return companyId
     */
    public long getCompanyId() {
        return companyId;
    }


    /**
     * Sets the companyId value for this BRASModel.
     * 
     * @param companyId
     */
    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }


    /**
     * Gets the dealer value for this BRASModel.
     * 
     * @return dealer
     */
    public String getDealer() {
        return dealer;
    }


    /**
     * Sets the dealer value for this BRASModel.
     * 
     * @param dealer
     */
    public void setDealer(String dealer) {
        this.dealer = dealer;
    }


    /**
     * Gets the dealerByIndex value for this BRASModel.
     * 
     * @return dealerByIndex
     */
    public String[] getDealerByIndex() {
        return dealerByIndex;
    }


    /**
     * Sets the dealerByIndex value for this BRASModel.
     * 
     * @param dealerByIndex
     */
    public void setDealerByIndex(String[] dealerByIndex) {
        this.dealerByIndex = dealerByIndex;
    }

    public String getDealerByIndex(int i) {
        return this.dealerByIndex[i];
    }

    public void setDealerByIndex(int i, String _value) {
        this.dealerByIndex[i] = _value;
    }


    /**
     * Gets the endIndex value for this BRASModel.
     * 
     * @return endIndex
     */
    public int getEndIndex() {
        return endIndex;
    }


    /**
     * Sets the endIndex value for this BRASModel.
     * 
     * @param endIndex
     */
    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }


    /**
     * Gets the id value for this BRASModel.
     * 
     * @return id
     */
    public long getId() {
        return id;
    }


    /**
     * Sets the id value for this BRASModel.
     * 
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }


    /**
     * Gets the ip value for this BRASModel.
     * 
     * @return ip
     */
    public String getIp() {
        return ip;
    }


    /**
     * Sets the ip value for this BRASModel.
     * 
     * @param ip
     */
    public void setIp(String ip) {
        this.ip = ip;
    }


    /**
     * Gets the ipPart1 value for this BRASModel.
     * 
     * @return ipPart1
     */
    public String getIpPart1() {
        return ipPart1;
    }


    /**
     * Sets the ipPart1 value for this BRASModel.
     * 
     * @param ipPart1
     */
    public void setIpPart1(String ipPart1) {
        this.ipPart1 = ipPart1;
    }


    /**
     * Gets the ipPart2 value for this BRASModel.
     * 
     * @return ipPart2
     */
    public String getIpPart2() {
        return ipPart2;
    }


    /**
     * Sets the ipPart2 value for this BRASModel.
     * 
     * @param ipPart2
     */
    public void setIpPart2(String ipPart2) {
        this.ipPart2 = ipPart2;
    }


    /**
     * Gets the ipPart3 value for this BRASModel.
     * 
     * @return ipPart3
     */
    public String getIpPart3() {
        return ipPart3;
    }


    /**
     * Sets the ipPart3 value for this BRASModel.
     * 
     * @param ipPart3
     */
    public void setIpPart3(String ipPart3) {
        this.ipPart3 = ipPart3;
    }


    /**
     * Gets the ipPart4 value for this BRASModel.
     * 
     * @return ipPart4
     */
    public String getIpPart4() {
        return ipPart4;
    }


    /**
     * Sets the ipPart4 value for this BRASModel.
     * 
     * @param ipPart4
     */
    public void setIpPart4(String ipPart4) {
        this.ipPart4 = ipPart4;
    }


    /**
     * Gets the listDealer value for this BRASModel.
     * 
     * @return listDealer
     */
    public String[] getListDealer() {
        return listDealer;
    }


    /**
     * Sets the listDealer value for this BRASModel.
     * 
     * @param listDealer
     */
    public void setListDealer(String[] listDealer) {
        this.listDealer = listDealer;
    }


    /**
     * Gets the listDealerCodes value for this BRASModel.
     * 
     * @return listDealerCodes
     */
    public String getListDealerCodes() {
        return listDealerCodes;
    }


    /**
     * Sets the listDealerCodes value for this BRASModel.
     * 
     * @param listDealerCodes
     */
    public void setListDealerCodes(String listDealerCodes) {
        this.listDealerCodes = listDealerCodes;
    }


    /**
     * Gets the listPostOffice value for this BRASModel.
     * 
     * @return listPostOffice
     */
    public String[] getListPostOffice() {
        return listPostOffice;
    }


    /**
     * Sets the listPostOffice value for this BRASModel.
     * 
     * @param listPostOffice
     */
    public void setListPostOffice(String[] listPostOffice) {
        this.listPostOffice = listPostOffice;
    }


    /**
     * Gets the listPostOfficeCodes value for this BRASModel.
     * 
     * @return listPostOfficeCodes
     */
    public String getListPostOfficeCodes() {
        return listPostOfficeCodes;
    }


    /**
     * Sets the listPostOfficeCodes value for this BRASModel.
     * 
     * @param listPostOfficeCodes
     */
    public void setListPostOfficeCodes(String listPostOfficeCodes) {
        this.listPostOfficeCodes = listPostOfficeCodes;
    }


    /**
     * Gets the name value for this BRASModel.
     * 
     * @return name
     */
    public String getName() {
        return name;
    }


    /**
     * Sets the name value for this BRASModel.
     * 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * Gets the owner value for this BRASModel.
     * 
     * @return owner
     */
    public String getOwner() {
        return owner;
    }


    /**
     * Sets the owner value for this BRASModel.
     * 
     * @param owner
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }


    /**
     * Gets the ownerName value for this BRASModel.
     * 
     * @return ownerName
     */
    public String getOwnerName() {
        return ownerName;
    }


    /**
     * Sets the ownerName value for this BRASModel.
     * 
     * @param ownerName
     */
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }


    /**
     * Gets the port value for this BRASModel.
     * 
     * @return port
     */
    public String getPort() {
        return port;
    }


    /**
     * Sets the port value for this BRASModel.
     * 
     * @param port
     */
    public void setPort(String port) {
        this.port = port;
    }


    /**
     * Gets the postOffice value for this BRASModel.
     * 
     * @return postOffice
     */
    public String getPostOffice() {
        return postOffice;
    }


    /**
     * Sets the postOffice value for this BRASModel.
     * 
     * @param postOffice
     */
    public void setPostOffice(String postOffice) {
        this.postOffice = postOffice;
    }


    /**
     * Gets the postOfficeByIndex value for this BRASModel.
     * 
     * @return postOfficeByIndex
     */
    public String[] getPostOfficeByIndex() {
        return postOfficeByIndex;
    }


    /**
     * Sets the postOfficeByIndex value for this BRASModel.
     * 
     * @param postOfficeByIndex
     */
    public void setPostOfficeByIndex(String[] postOfficeByIndex) {
        this.postOfficeByIndex = postOfficeByIndex;
    }

    public String getPostOfficeByIndex(int i) {
        return this.postOfficeByIndex[i];
    }

    public void setPostOfficeByIndex(int i, String _value) {
        this.postOfficeByIndex[i] = _value;
    }


    /**
     * Gets the searchDealer value for this BRASModel.
     * 
     * @return searchDealer
     */
    public String getSearchDealer() {
        return searchDealer;
    }


    /**
     * Sets the searchDealer value for this BRASModel.
     * 
     * @param searchDealer
     */
    public void setSearchDealer(String searchDealer) {
        this.searchDealer = searchDealer;
    }


    /**
     * Gets the searchPostOffice value for this BRASModel.
     * 
     * @return searchPostOffice
     */
    public String getSearchPostOffice() {
        return searchPostOffice;
    }


    /**
     * Sets the searchPostOffice value for this BRASModel.
     * 
     * @param searchPostOffice
     */
    public void setSearchPostOffice(String searchPostOffice) {
        this.searchPostOffice = searchPostOffice;
    }


    /**
     * Gets the secret value for this BRASModel.
     * 
     * @return secret
     */
    public String getSecret() {
        return secret;
    }


    /**
     * Sets the secret value for this BRASModel.
     * 
     * @param secret
     */
    public void setSecret(String secret) {
        this.secret = secret;
    }


    /**
     * Gets the usedUnits value for this BRASModel.
     * 
     * @return usedUnits
     */
    public String getUsedUnits() {
        return usedUnits;
    }


    /**
     * Sets the usedUnits value for this BRASModel.
     * 
     * @param usedUnits
     */
    public void setUsedUnits(String usedUnits) {
        this.usedUnits = usedUnits;
    }


    /**
     * Gets the userId value for this BRASModel.
     * 
     * @return userId
     */
    public long getUserId() {
        return userId;
    }


    /**
     * Sets the userId value for this BRASModel.
     * 
     * @param userId
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof BRASModel)) return false;
        BRASModel other = (BRASModel) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.areaCode==null && other.getAreaCode()==null) || 
             (this.areaCode!=null &&
              this.areaCode.equals(other.getAreaCode()))) &&
            ((this.areaName==null && other.getAreaName()==null) || 
             (this.areaName!=null &&
              this.areaName.equals(other.getAreaName()))) &&
            this.beginIndex == other.getBeginIndex() &&
            ((this.bras_type==null && other.getBras_type()==null) || 
             (this.bras_type!=null &&
              this.bras_type.equals(other.getBras_type()))) &&
            this.companyId == other.getCompanyId() &&
            ((this.dealer==null && other.getDealer()==null) || 
             (this.dealer!=null &&
              this.dealer.equals(other.getDealer()))) &&
            ((this.dealerByIndex==null && other.getDealerByIndex()==null) || 
             (this.dealerByIndex!=null &&
              java.util.Arrays.equals(this.dealerByIndex, other.getDealerByIndex()))) &&
            this.endIndex == other.getEndIndex() &&
            this.id == other.getId() &&
            ((this.ip==null && other.getIp()==null) || 
             (this.ip!=null &&
              this.ip.equals(other.getIp()))) &&
            ((this.ipPart1==null && other.getIpPart1()==null) || 
             (this.ipPart1!=null &&
              this.ipPart1.equals(other.getIpPart1()))) &&
            ((this.ipPart2==null && other.getIpPart2()==null) || 
             (this.ipPart2!=null &&
              this.ipPart2.equals(other.getIpPart2()))) &&
            ((this.ipPart3==null && other.getIpPart3()==null) || 
             (this.ipPart3!=null &&
              this.ipPart3.equals(other.getIpPart3()))) &&
            ((this.ipPart4==null && other.getIpPart4()==null) || 
             (this.ipPart4!=null &&
              this.ipPart4.equals(other.getIpPart4()))) &&
            ((this.listDealer==null && other.getListDealer()==null) || 
             (this.listDealer!=null &&
              java.util.Arrays.equals(this.listDealer, other.getListDealer()))) &&
            ((this.listDealerCodes==null && other.getListDealerCodes()==null) || 
             (this.listDealerCodes!=null &&
              this.listDealerCodes.equals(other.getListDealerCodes()))) &&
            ((this.listPostOffice==null && other.getListPostOffice()==null) || 
             (this.listPostOffice!=null &&
              java.util.Arrays.equals(this.listPostOffice, other.getListPostOffice()))) &&
            ((this.listPostOfficeCodes==null && other.getListPostOfficeCodes()==null) || 
             (this.listPostOfficeCodes!=null &&
              this.listPostOfficeCodes.equals(other.getListPostOfficeCodes()))) &&
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            ((this.owner==null && other.getOwner()==null) || 
             (this.owner!=null &&
              this.owner.equals(other.getOwner()))) &&
            ((this.ownerName==null && other.getOwnerName()==null) || 
             (this.ownerName!=null &&
              this.ownerName.equals(other.getOwnerName()))) &&
            ((this.port==null && other.getPort()==null) || 
             (this.port!=null &&
              this.port.equals(other.getPort()))) &&
            ((this.postOffice==null && other.getPostOffice()==null) || 
             (this.postOffice!=null &&
              this.postOffice.equals(other.getPostOffice()))) &&
            ((this.postOfficeByIndex==null && other.getPostOfficeByIndex()==null) || 
             (this.postOfficeByIndex!=null &&
              java.util.Arrays.equals(this.postOfficeByIndex, other.getPostOfficeByIndex()))) &&
            ((this.searchDealer==null && other.getSearchDealer()==null) || 
             (this.searchDealer!=null &&
              this.searchDealer.equals(other.getSearchDealer()))) &&
            ((this.searchPostOffice==null && other.getSearchPostOffice()==null) || 
             (this.searchPostOffice!=null &&
              this.searchPostOffice.equals(other.getSearchPostOffice()))) &&
            ((this.secret==null && other.getSecret()==null) || 
             (this.secret!=null &&
              this.secret.equals(other.getSecret()))) &&
            ((this.usedUnits==null && other.getUsedUnits()==null) || 
             (this.usedUnits!=null &&
              this.usedUnits.equals(other.getUsedUnits()))) &&
            this.userId == other.getUserId();
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
        if (getAreaCode() != null) {
            _hashCode += getAreaCode().hashCode();
        }
        if (getAreaName() != null) {
            _hashCode += getAreaName().hashCode();
        }
        _hashCode += getBeginIndex();
        if (getBras_type() != null) {
            _hashCode += getBras_type().hashCode();
        }
        _hashCode += new Long(getCompanyId()).hashCode();
        if (getDealer() != null) {
            _hashCode += getDealer().hashCode();
        }
        if (getDealerByIndex() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDealerByIndex());
                 i++) {
                Object obj = java.lang.reflect.Array.get(getDealerByIndex(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        _hashCode += getEndIndex();
        _hashCode += new Long(getId()).hashCode();
        if (getIp() != null) {
            _hashCode += getIp().hashCode();
        }
        if (getIpPart1() != null) {
            _hashCode += getIpPart1().hashCode();
        }
        if (getIpPart2() != null) {
            _hashCode += getIpPart2().hashCode();
        }
        if (getIpPart3() != null) {
            _hashCode += getIpPart3().hashCode();
        }
        if (getIpPart4() != null) {
            _hashCode += getIpPart4().hashCode();
        }
        if (getListDealer() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getListDealer());
                 i++) {
                Object obj = java.lang.reflect.Array.get(getListDealer(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getListDealerCodes() != null) {
            _hashCode += getListDealerCodes().hashCode();
        }
        if (getListPostOffice() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getListPostOffice());
                 i++) {
                Object obj = java.lang.reflect.Array.get(getListPostOffice(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getListPostOfficeCodes() != null) {
            _hashCode += getListPostOfficeCodes().hashCode();
        }
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        if (getOwner() != null) {
            _hashCode += getOwner().hashCode();
        }
        if (getOwnerName() != null) {
            _hashCode += getOwnerName().hashCode();
        }
        if (getPort() != null) {
            _hashCode += getPort().hashCode();
        }
        if (getPostOffice() != null) {
            _hashCode += getPostOffice().hashCode();
        }
        if (getPostOfficeByIndex() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPostOfficeByIndex());
                 i++) {
                Object obj = java.lang.reflect.Array.get(getPostOfficeByIndex(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getSearchDealer() != null) {
            _hashCode += getSearchDealer().hashCode();
        }
        if (getSearchPostOffice() != null) {
            _hashCode += getSearchPostOffice().hashCode();
        }
        if (getSecret() != null) {
            _hashCode += getSecret().hashCode();
        }
        if (getUsedUnits() != null) {
            _hashCode += getUsedUnits().hashCode();
        }
        _hashCode += new Long(getUserId()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(BRASModel.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:groovy.model.hyframe.hyperlogy", "BRASModel"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("areaCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "areaCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("areaName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "areaName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("beginIndex");
        elemField.setXmlName(new javax.xml.namespace.QName("", "beginIndex"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bras_type");
        elemField.setXmlName(new javax.xml.namespace.QName("", "bras_type"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("companyId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "companyId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dealer");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dealer"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dealerByIndex");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dealerByIndex"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("endIndex");
        elemField.setXmlName(new javax.xml.namespace.QName("", "endIndex"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ip");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ip"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ipPart1");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ipPart1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ipPart2");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ipPart2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ipPart3");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ipPart3"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ipPart4");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ipPart4"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("listDealer");
        elemField.setXmlName(new javax.xml.namespace.QName("", "listDealer"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("listDealerCodes");
        elemField.setXmlName(new javax.xml.namespace.QName("", "listDealerCodes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("listPostOffice");
        elemField.setXmlName(new javax.xml.namespace.QName("", "listPostOffice"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("listPostOfficeCodes");
        elemField.setXmlName(new javax.xml.namespace.QName("", "listPostOfficeCodes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("name");
        elemField.setXmlName(new javax.xml.namespace.QName("", "name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("owner");
        elemField.setXmlName(new javax.xml.namespace.QName("", "owner"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ownerName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ownerName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("port");
        elemField.setXmlName(new javax.xml.namespace.QName("", "port"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("postOffice");
        elemField.setXmlName(new javax.xml.namespace.QName("", "postOffice"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("postOfficeByIndex");
        elemField.setXmlName(new javax.xml.namespace.QName("", "postOfficeByIndex"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("searchDealer");
        elemField.setXmlName(new javax.xml.namespace.QName("", "searchDealer"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("searchPostOffice");
        elemField.setXmlName(new javax.xml.namespace.QName("", "searchPostOffice"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("secret");
        elemField.setXmlName(new javax.xml.namespace.QName("", "secret"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("usedUnits");
        elemField.setXmlName(new javax.xml.namespace.QName("", "usedUnits"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "userId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
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
