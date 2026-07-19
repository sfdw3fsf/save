<?xml version="1.0" encoding="utf-8"?>
<wsdl:definitions xmlns:s="http://www.w3.org/2001/XMLSchema" xmlns:soap12="http://schemas.xmlsoap.org/wsdl/soap12/" xmlns:http="http://schemas.xmlsoap.org/wsdl/http/" xmlns:mime="http://schemas.xmlsoap.org/wsdl/mime/" xmlns:tns="http://vnpt.vn/" xmlns:soap="http://schemas.xmlsoap.org/wsdl/soap/" xmlns:tm="http://microsoft.com/wsdl/mime/textMatching/" xmlns:soapenc="http://schemas.xmlsoap.org/soap/encoding/" targetNamespace="http://vnpt.vn/" xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">
  <wsdl:types>
    <s:schema elementFormDefault="qualified" targetNamespace="http://vnpt.vn/">
      <s:element name="Login">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="authHeader" type="tns:AuthHeaderWS" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="AuthHeaderWS">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="1" name="Username" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="Password" type="s:string" />
        </s:sequence>
      </s:complexType>
      <s:element name="LoginResponse">
        <s:complexType />
      </s:element>
      <s:element name="AuthHeaderWS" type="tns:AuthHeaderWS" />
      <s:element name="GetONTInfoByAccount">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="ProvinceCode" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="AccountName" type="s:string" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="GetONTInfoByAccountResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="GetONTInfoByAccountResult">
              <s:complexType>
                <s:sequence>
                  <s:element ref="s:schema" />
                  <s:any />
                </s:sequence>
              </s:complexType>
            </s:element>
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="ResetByPortVisa">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="visaString" type="s:string" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="ResetByPortVisaResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="ResetByPortVisaResult" type="tns:TaskOfString" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="TaskOfString">
        <s:complexContent mixed="false">
          <s:extension base="tns:Task" />
        </s:complexContent>
      </s:complexType>
      <s:complexType name="Task" />
      <s:element name="ResetDslPort">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="deviceIp" type="s:string" />
            <s:element minOccurs="1" maxOccurs="1" name="frame" type="s:int" />
            <s:element minOccurs="1" maxOccurs="1" name="slot" type="s:int" />
            <s:element minOccurs="1" maxOccurs="1" name="port" type="s:int" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="ResetDslPortResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="ResetDslPortResult" type="tns:TaskOfString" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="TestByPortVisa">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="PortInfo" type="s:string" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="TestByPortVisaResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="TestByPortVisaResult">
              <s:complexType>
                <s:sequence>
                  <s:element ref="s:schema" />
                  <s:any />
                </s:sequence>
              </s:complexType>
            </s:element>
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="TestByPort">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="deviceIp" type="s:string" />
            <s:element minOccurs="1" maxOccurs="1" name="frame" type="s:int" />
            <s:element minOccurs="1" maxOccurs="1" name="slot" type="s:int" />
            <s:element minOccurs="1" maxOccurs="1" name="port" type="s:int" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="TestByPortResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="TestByPortResult">
              <s:complexType>
                <s:sequence>
                  <s:element ref="s:schema" />
                  <s:any />
                </s:sequence>
              </s:complexType>
            </s:element>
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="TestOnuByPortVisa">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="PortInfo" type="s:string" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="TestOnuByPortVisaResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="TestOnuByPortVisaResult">
              <s:complexType>
                <s:sequence>
                  <s:element ref="s:schema" />
                  <s:any />
                </s:sequence>
              </s:complexType>
            </s:element>
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="TestOnuByAccount">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="accountName" type="s:string" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="TestOnuByAccountResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="TestOnuByAccountResult">
              <s:complexType>
                <s:sequence>
                  <s:element ref="s:schema" />
                  <s:any />
                </s:sequence>
              </s:complexType>
            </s:element>
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="TestOnuPort">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="deviceIp" type="s:string" />
            <s:element minOccurs="1" maxOccurs="1" name="frame" type="s:int" />
            <s:element minOccurs="1" maxOccurs="1" name="slot" type="s:int" />
            <s:element minOccurs="1" maxOccurs="1" name="port" type="s:int" />
            <s:element minOccurs="1" maxOccurs="1" name="onuIndex" type="s:int" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="TestOnuPortResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="TestOnuPortResult">
              <s:complexType>
                <s:sequence>
                  <s:element ref="s:schema" />
                  <s:any />
                </s:sequence>
              </s:complexType>
            </s:element>
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="GetNonTrafficFiberVNN">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="1" maxOccurs="1" name="UnitID" type="s:int" />
            <s:element minOccurs="1" maxOccurs="1" name="fromDate" type="s:dateTime" />
            <s:element minOccurs="1" maxOccurs="1" name="toDate" type="s:dateTime" />
            <s:element minOccurs="1" maxOccurs="1" name="backgroundTraffic" type="s:int" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="GetNonTrafficFiberVNNResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="GetNonTrafficFiberVNNResult">
              <s:complexType>
                <s:sequence>
                  <s:element ref="s:schema" />
                  <s:any />
                </s:sequence>
              </s:complexType>
            </s:element>
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="ResetOnuConnection">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="deviceIp" type="s:string" />
            <s:element minOccurs="1" maxOccurs="1" name="frame" type="s:int" />
            <s:element minOccurs="1" maxOccurs="1" name="slot" type="s:int" />
            <s:element minOccurs="1" maxOccurs="1" name="port" type="s:int" />
            <s:element minOccurs="1" maxOccurs="1" name="onuIndex" type="s:int" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="ResetOnuConnectionResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="ResetOnuConnectionResult" type="s:string" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="ResetOnuConnectionByPortVisa">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="visaString" type="s:string" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="ResetOnuConnectionByPortVisaResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="ResetOnuConnectionByPortVisaResult" type="s:string" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="UpdateUrlGponQualityDetailByUnit">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="1" maxOccurs="1" name="unitID" type="s:int" />
            <s:element minOccurs="1" maxOccurs="1" name="statisticDate" type="s:dateTime" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="UpdateUrlGponQualityDetailByUnitResponse">
        <s:complexType />
      </s:element>
      <s:element name="GetUrlQualityFile">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="1" maxOccurs="1" name="UnitID" type="s:int" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="GetUrlQualityFileResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="GetUrlQualityFileResult" type="s:string" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="ChangeDslPortProfile">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="deviceIp" type="s:string" />
            <s:element minOccurs="1" maxOccurs="1" name="frame" type="s:int" />
            <s:element minOccurs="1" maxOccurs="1" name="slot" type="s:int" />
            <s:element minOccurs="1" maxOccurs="1" name="port" type="s:int" />
            <s:element minOccurs="0" maxOccurs="1" name="profileName" type="s:string" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="ChangeDslPortProfileResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="ChangeDslPortProfileResult" type="s:string" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="ChangeDslPortProfileByPortVisa">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="visaString" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="profileName" type="s:string" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="ChangeDslPortProfileByPortVisaResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="ChangeDslPortProfileByPortVisaResult" type="s:string" />
          </s:sequence>
        </s:complexType>
      </s:element>
    </s:schema>
  </wsdl:types>
  <wsdl:message name="LoginSoapIn">
    <wsdl:part name="parameters" element="tns:Login" />
  </wsdl:message>
  <wsdl:message name="LoginSoapOut">
    <wsdl:part name="parameters" element="tns:LoginResponse" />
  </wsdl:message>
  <wsdl:message name="LoginAuthHeaderWS">
    <wsdl:part name="AuthHeaderWS" element="tns:AuthHeaderWS" />
  </wsdl:message>
  <wsdl:message name="GetONTInfoByAccountSoapIn">
    <wsdl:part name="parameters" element="tns:GetONTInfoByAccount" />
  </wsdl:message>
  <wsdl:message name="GetONTInfoByAccountSoapOut">
    <wsdl:part name="parameters" element="tns:GetONTInfoByAccountResponse" />
  </wsdl:message>
  <wsdl:message name="GetONTInfoByAccountAuthHeaderWS">
    <wsdl:part name="AuthHeaderWS" element="tns:AuthHeaderWS" />
  </wsdl:message>
  <wsdl:message name="ResetByPortVisaSoapIn">
    <wsdl:part name="parameters" element="tns:ResetByPortVisa" />
  </wsdl:message>
  <wsdl:message name="ResetByPortVisaSoapOut">
    <wsdl:part name="parameters" element="tns:ResetByPortVisaResponse" />
  </wsdl:message>
  <wsdl:message name="ResetByPortVisaAuthHeaderWS">
    <wsdl:part name="AuthHeaderWS" element="tns:AuthHeaderWS" />
  </wsdl:message>
  <wsdl:message name="ResetDslPortSoapIn">
    <wsdl:part name="parameters" element="tns:ResetDslPort" />
  </wsdl:message>
  <wsdl:message name="ResetDslPortSoapOut">
    <wsdl:part name="parameters" element="tns:ResetDslPortResponse" />
  </wsdl:message>
  <wsdl:message name="ResetDslPortAuthHeaderWS">
    <wsdl:part name="AuthHeaderWS" element="tns:AuthHeaderWS" />
  </wsdl:message>
  <wsdl:message name="TestByPortVisaSoapIn">
    <wsdl:part name="parameters" element="tns:TestByPortVisa" />
  </wsdl:message>
  <wsdl:message name="TestByPortVisaSoapOut">
    <wsdl:part name="parameters" element="tns:TestByPortVisaResponse" />
  </wsdl:message>
  <wsdl:message name="TestByPortVisaAuthHeaderWS">
    <wsdl:part name="AuthHeaderWS" element="tns:AuthHeaderWS" />
  </wsdl:message>
  <wsdl:message name="TestByPortSoapIn">
    <wsdl:part name="parameters" element="tns:TestByPort" />
  </wsdl:message>
  <wsdl:message name="TestByPortSoapOut">
    <wsdl:part name="parameters" element="tns:TestByPortResponse" />
  </wsdl:message>
  <wsdl:message name="TestByPortAuthHeaderWS">
    <wsdl:part name="AuthHeaderWS" element="tns:AuthHeaderWS" />
  </wsdl:message>
  <wsdl:message name="TestOnuByPortVisaSoapIn">
    <wsdl:part name="parameters" element="tns:TestOnuByPortVisa" />
  </wsdl:message>
  <wsdl:message name="TestOnuByPortVisaSoapOut">
    <wsdl:part name="parameters" element="tns:TestOnuByPortVisaResponse" />
  </wsdl:message>
  <wsdl:message name="TestOnuByPortVisaAuthHeaderWS">
    <wsdl:part name="AuthHeaderWS" element="tns:AuthHeaderWS" />
  </wsdl:message>
  <wsdl:message name="TestOnuByAccountSoapIn">
    <wsdl:part name="parameters" element="tns:TestOnuByAccount" />
  </wsdl:message>
  <wsdl:message name="TestOnuByAccountSoapOut">
    <wsdl:part name="parameters" element="tns:TestOnuByAccountResponse" />
  </wsdl:message>
  <wsdl:message name="TestOnuByAccountAuthHeaderWS">
    <wsdl:part name="AuthHeaderWS" element="tns:AuthHeaderWS" />
  </wsdl:message>
  <wsdl:message name="TestOnuPortSoapIn">
    <wsdl:part name="parameters" element="tns:TestOnuPort" />
  </wsdl:message>
  <wsdl:message name="TestOnuPortSoapOut">
    <wsdl:part name="parameters" element="tns:TestOnuPortResponse" />
  </wsdl:message>
  <wsdl:message name="TestOnuPortAuthHeaderWS">
    <wsdl:part name="AuthHeaderWS" element="tns:AuthHeaderWS" />
  </wsdl:message>
  <wsdl:message name="GetNonTrafficFiberVNNSoapIn">
    <wsdl:part name="parameters" element="tns:GetNonTrafficFiberVNN" />
  </wsdl:message>
  <wsdl:message name="GetNonTrafficFiberVNNSoapOut">
    <wsdl:part name="parameters" element="tns:GetNonTrafficFiberVNNResponse" />
  </wsdl:message>
  <wsdl:message name="GetNonTrafficFiberVNNAuthHeaderWS">
    <wsdl:part name="AuthHeaderWS" element="tns:AuthHeaderWS" />
  </wsdl:message>
  <wsdl:message name="ResetOnuConnectionSoapIn">
    <wsdl:part name="parameters" element="tns:ResetOnuConnection" />
  </wsdl:message>
  <wsdl:message name="ResetOnuConnectionSoapOut">
    <wsdl:part name="parameters" element="tns:ResetOnuConnectionResponse" />
  </wsdl:message>
  <wsdl:message name="ResetOnuConnectionAuthHeaderWS">
    <wsdl:part name="AuthHeaderWS" element="tns:AuthHeaderWS" />
  </wsdl:message>
  <wsdl:message name="ResetOnuConnectionByPortVisaSoapIn">
    <wsdl:part name="parameters" element="tns:ResetOnuConnectionByPortVisa" />
  </wsdl:message>
  <wsdl:message name="ResetOnuConnectionByPortVisaSoapOut">
    <wsdl:part name="parameters" element="tns:ResetOnuConnectionByPortVisaResponse" />
  </wsdl:message>
  <wsdl:message name="ResetOnuConnectionByPortVisaAuthHeaderWS">
    <wsdl:part name="AuthHeaderWS" element="tns:AuthHeaderWS" />
  </wsdl:message>
  <wsdl:message name="UpdateUrlGponQualityDetailByUnitSoapIn">
    <wsdl:part name="parameters" element="tns:UpdateUrlGponQualityDetailByUnit" />
  </wsdl:message>
  <wsdl:message name="UpdateUrlGponQualityDetailByUnitSoapOut">
    <wsdl:part name="parameters" element="tns:UpdateUrlGponQualityDetailByUnitResponse" />
  </wsdl:message>
  <wsdl:message name="UpdateUrlGponQualityDetailByUnitAuthHeaderWS">
    <wsdl:part name="AuthHeaderWS" element="tns:AuthHeaderWS" />
  </wsdl:message>
  <wsdl:message name="GetUrlQualityFileSoapIn">
    <wsdl:part name="parameters" element="tns:GetUrlQualityFile" />
  </wsdl:message>
  <wsdl:message name="GetUrlQualityFileSoapOut">
    <wsdl:part name="parameters" element="tns:GetUrlQualityFileResponse" />
  </wsdl:message>
  <wsdl:message name="GetUrlQualityFileAuthHeaderWS">
    <wsdl:part name="AuthHeaderWS" element="tns:AuthHeaderWS" />
  </wsdl:message>
  <wsdl:message name="ChangeDslPortProfileSoapIn">
    <wsdl:part name="parameters" element="tns:ChangeDslPortProfile" />
  </wsdl:message>
  <wsdl:message name="ChangeDslPortProfileSoapOut">
    <wsdl:part name="parameters" element="tns:ChangeDslPortProfileResponse" />
  </wsdl:message>
  <wsdl:message name="ChangeDslPortProfileAuthHeaderWS">
    <wsdl:part name="AuthHeaderWS" element="tns:AuthHeaderWS" />
  </wsdl:message>
  <wsdl:message name="ChangeDslPortProfileByPortVisaSoapIn">
    <wsdl:part name="parameters" element="tns:ChangeDslPortProfileByPortVisa" />
  </wsdl:message>
  <wsdl:message name="ChangeDslPortProfileByPortVisaSoapOut">
    <wsdl:part name="parameters" element="tns:ChangeDslPortProfileByPortVisaResponse" />
  </wsdl:message>
  <wsdl:message name="ChangeDslPortProfileByPortVisaAuthHeaderWS">
    <wsdl:part name="AuthHeaderWS" element="tns:AuthHeaderWS" />
  </wsdl:message>
  <wsdl:portType name="LineTestWSSoap">
    <wsdl:operation name="Login">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Login &lt;br&gt;
           Nếu có lỗi sẽ ra thông báo</wsdl:documentation>
      <wsdl:input message="tns:LoginSoapIn" />
      <wsdl:output message="tns:LoginSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="GetONTInfoByAccount">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Lấy thông tin ONT theo tài khoản Fiber&lt;br&gt;
			@Para string ProvinceCode Mã tỉnh &lt;br&gt;
            @Param string accountName Tên tài khoản&lt;br&gt;
            @Return DataTable: Manufactory (Nhà sx), Hardware, Firmware</wsdl:documentation>
      <wsdl:input message="tns:GetONTInfoByAccountSoapIn" />
      <wsdl:output message="tns:GetONTInfoByAccountSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="ResetByPortVisa">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Reset xDSL Port Visa&lt;br&gt;
            @Param string visaString Chuỗi visa truyền vào, format: ip:frame/slot/port&lt;br&gt;
            @Return string</wsdl:documentation>
      <wsdl:input message="tns:ResetByPortVisaSoapIn" />
      <wsdl:output message="tns:ResetByPortVisaSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="ResetDslPort">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Reset xDSL port&lt;br&gt;
            @Param string deviceIP Địa chỉ IP của thiết bị&lt;br&gt;
            @Param int frame thông số frame&lt;br&gt;
            @Param int slot thông số slot&lt;br&gt;
            @Param int port thông số port&lt;br&gt;
            @Return string</wsdl:documentation>
      <wsdl:input message="tns:ResetDslPortSoapIn" />
      <wsdl:output message="tns:ResetDslPortSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="TestByPortVisa">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Đo một port xDSL chỉ định bằng Visa String&lt;br&gt;
            @Param string PortInfo Chuỗi visa truyền vào, format: SYSTEMID=:-:RACKID=:-:SELFID=:-:SLOT=:-:PORT=:-:VPI=:-:VCI=&lt;br&gt;
            @Return String</wsdl:documentation>
      <wsdl:input message="tns:TestByPortVisaSoapIn" />
      <wsdl:output message="tns:TestByPortVisaSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="TestByPort">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Đo một port xDSL chỉ định&lt;br&gt;
            @Param string deviceIP Địa chỉ IP của thiết bị &lt;br&gt;
            @Param int frame thông số frame &lt;br&gt;
            @Param int slot thông số slot &lt;br&gt;
            @Param int port thông số port &lt;br&gt;
            @Return Dataset</wsdl:documentation>
      <wsdl:input message="tns:TestByPortSoapIn" />
      <wsdl:output message="tns:TestByPortSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="TestOnuByPortVisa">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Đo một ONT GPON chỉ định&lt;br&gt;
            @Param string PortInfo Chuỗi visa truyền vào, format: SYSTEMID=:-:RACKID=:-:SELFID=:-:SLOT=:-:PORT=:-:VPI=:-:VCI=&lt;br&gt;
            @Return Dataset</wsdl:documentation>
      <wsdl:input message="tns:TestOnuByPortVisaSoapIn" />
      <wsdl:output message="tns:TestOnuByPortVisaSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="TestOnuByAccount">
      <wsdl:input message="tns:TestOnuByAccountSoapIn" />
      <wsdl:output message="tns:TestOnuByAccountSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="TestOnuPort">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Đo một ONT GPON chỉ định&lt;br&gt;
            @Param string deviceIP Địa chỉ IP của thiết bị &lt;br&gt;
            @Param int frame thông số frame &lt;br&gt;
            @Param int slot thông số slot &lt;br&gt;
            @Param int port thông số port &lt;br&gt;
            @Param int onuIndex thông số onuIndex &lt;br&gt;
            @Return Dataset</wsdl:documentation>
      <wsdl:input message="tns:TestOnuPortSoapIn" />
      <wsdl:output message="tns:TestOnuPortSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="GetNonTrafficFiberVNN">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">--DESC--&lt;br&gt;Lấy danh sách thuê bao FiberVNN không phát sinh lưu lượng từ @fromDate đến @toDate và có tổng traffic &gt; 0 và &lt; @backgroundTraffic&lt;br&gt;--INPUT--&lt;br&gt;@UnitID string: Mã đơn vị&lt;br&gt;@fromDate DateTime: Ngày bắt đầu lấy dữ liệu&lt;br&gt;@toDate DateTime: Ngày kết thúc lấy dữ liệu&lt;br&gt;@backgroundTraffic int: Ngưỡng trên traffic để lấy dữ liệu&lt;br&gt;--OUTPUT--&lt;br&gt;Dataset&lt;br&gt;@StationName string: Tên trạm&lt;br&gt;@SystemName string: Tên thiết bị&lt;br&gt;@IpAddress string: Địa chỉ IP của thiết bị&lt;br&gt;@PortIndex string: Port visa&lt;br&gt;@AccountName string: Tên tài khoản&lt;br&gt;@SubName string: Tên khách hàng&lt;br&gt;@SubAddress string: Địa chỉ khách hàng&lt;br&gt;@Traffic long: Lưu lượng của thiết bị trong thời gian từ @fromDate đến @toDate</wsdl:documentation>
      <wsdl:input message="tns:GetNonTrafficFiberVNNSoapIn" />
      <wsdl:output message="tns:GetNonTrafficFiberVNNSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="ResetOnuConnection">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Reset ONU connection&lt;br&gt;
            @Param string deviceIP Địa chỉ IP của thiết bị&lt;br&gt;
            @Param int frame thông số frame&lt;br&gt;
            @Param int slot thông số slot&lt;br&gt;
            @Param int port thông số port&lt;br&gt;
            @Param int onuIndex thông số onuIndex&lt;br&gt;
            @Return string</wsdl:documentation>
      <wsdl:input message="tns:ResetOnuConnectionSoapIn" />
      <wsdl:output message="tns:ResetOnuConnectionSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="ResetOnuConnectionByPortVisa">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Reset ONU connection ByPortVisa&lt;br&gt;
            @Param string visaString Chuỗi visa truyền vào, format: ip:frame/slot/port/onuindex&lt;br&gt;
            @Return Dataset</wsdl:documentation>
      <wsdl:input message="tns:ResetOnuConnectionByPortVisaSoapIn" />
      <wsdl:output message="tns:ResetOnuConnectionByPortVisaSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="UpdateUrlGponQualityDetailByUnit">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Tạo file danh sách account thuê bao gpon theo ngày và đơn vị, sau khi gọi hàm này 15 phút sau gọi hàm GetUrlGponQualityDetailByUnit để lấy đường dẫn lấy file &lt;br&gt;
            @Param long unitID ID đơn vị&lt;br&gt;
            @Param DateTime statisticDate Ngày thống kê&lt;br&gt;
			@Return: File excel gồm các cột &lt;br&gt;
			 SystemName :  Tên hệ thống &lt;br&gt;
			 PortIndex :  ONU &lt;br&gt;
             VendorID :  VendorID &lt;br&gt;
             SerialNo :  SerialNo &lt;br&gt;
			 FirmWare :  FirmWare &lt;br&gt;
			 Slid :  SLID&lt;br&gt;
			 ProfileDS :  ProfileDS &lt;br&gt;
			 ProfileUS :  ProfileUS &lt;br&gt;
             Distance :  Độ dài (m) &lt;br&gt;  :
             AttenDS : suy hao DOWN &lt;br&gt;
             AttenUS : suy hao UP &lt;br&gt;
             QosDS :  Đánh giá &lt;br&gt;
			 FiberAccountName :  tài khoản FiberVNN &lt;br&gt;
			 MyTVAccountName :  MyTV &lt;br&gt;
			 FiberSubName :  Tên thuê bao thuê bao Fiber &lt;br&gt;
			 FiberServiceStatus :  Trạng thái trên Visa&lt;br&gt;
			 Mobile :  Điện thoại thuê bao Fiber &lt;br&gt;
			 FiberSubAddress :  Địa chỉ thuê bao Fiber &lt;br&gt;
			 ZoneName :  Địa bàn
		   </wsdl:documentation>
      <wsdl:input message="tns:UpdateUrlGponQualityDetailByUnitSoapIn" />
      <wsdl:output message="tns:UpdateUrlGponQualityDetailByUnitSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="GetUrlQualityFile">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Lấy url file danh sách account thuê bao gpon theo ngày và đơn vị&lt;br&gt;
            @Param long unitID ID đơn vị&lt;br&gt;
            @Return url file trả về có tên: UnitID_yyyyMMdd (năm tháng ngày lấy số liệu)</wsdl:documentation>
      <wsdl:input message="tns:GetUrlQualityFileSoapIn" />
      <wsdl:output message="tns:GetUrlQualityFileSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="ChangeDslPortProfile">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Thay đổi Line Profile của DSLAM&lt;br&gt;
            @Param string deviceIP Địa chỉ IP của thiết bị &lt;br&gt;
            @Param int frame thông số frame &lt;br&gt;
            @Param int slot thông số slot &lt;br&gt;
            @Param int port thông số port &lt;br&gt;
            @Param string profileName tên profile đích&lt;br&gt;
            @Return string</wsdl:documentation>
      <wsdl:input message="tns:ChangeDslPortProfileSoapIn" />
      <wsdl:output message="tns:ChangeDslPortProfileSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="ChangeDslPortProfileByPortVisa">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Thay đổi Line Profile của DSLAM&lt;br&gt;
            @Param string visaString Chuỗi visa truyền vào, format: ip:frame/slot/port&lt;br&gt;
            @Param string profileName tên profile đích&lt;br&gt;
            @Return string</wsdl:documentation>
      <wsdl:input message="tns:ChangeDslPortProfileByPortVisaSoapIn" />
      <wsdl:output message="tns:ChangeDslPortProfileByPortVisaSoapOut" />
    </wsdl:operation>
  </wsdl:portType>
  <wsdl:binding name="LineTestWSSoap" type="tns:LineTestWSSoap">
    <soap:binding transport="http://schemas.xmlsoap.org/soap/http" />
    <wsdl:operation name="Login">
      <soap:operation soapAction="http://vnpt.vn/Login" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
        <soap:header message="tns:LoginAuthHeaderWS" part="AuthHeaderWS" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="GetONTInfoByAccount">
      <soap:operation soapAction="http://vnpt.vn/GetONTInfoByAccount" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
        <soap:header message="tns:GetONTInfoByAccountAuthHeaderWS" part="AuthHeaderWS" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="ResetByPortVisa">
      <soap:operation soapAction="http://vnpt.vn/ResetByPortVisa" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
        <soap:header message="tns:ResetByPortVisaAuthHeaderWS" part="AuthHeaderWS" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="ResetDslPort">
      <soap:operation soapAction="http://vnpt.vn/ResetDslPort" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
        <soap:header message="tns:ResetDslPortAuthHeaderWS" part="AuthHeaderWS" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="TestByPortVisa">
      <soap:operation soapAction="http://vnpt.vn/TestByPortVisa" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
        <soap:header message="tns:TestByPortVisaAuthHeaderWS" part="AuthHeaderWS" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="TestByPort">
      <soap:operation soapAction="http://vnpt.vn/TestByPort" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
        <soap:header message="tns:TestByPortAuthHeaderWS" part="AuthHeaderWS" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="TestOnuByPortVisa">
      <soap:operation soapAction="http://vnpt.vn/TestOnuByPortVisa" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
        <soap:header message="tns:TestOnuByPortVisaAuthHeaderWS" part="AuthHeaderWS" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="TestOnuByAccount">
      <soap:operation soapAction="http://vnpt.vn/TestOnuByAccount" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
        <soap:header message="tns:TestOnuByAccountAuthHeaderWS" part="AuthHeaderWS" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="TestOnuPort">
      <soap:operation soapAction="http://vnpt.vn/TestOnuPort" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
        <soap:header message="tns:TestOnuPortAuthHeaderWS" part="AuthHeaderWS" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="GetNonTrafficFiberVNN">
      <soap:operation soapAction="http://vnpt.vn/GetNonTrafficFiberVNN" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
        <soap:header message="tns:GetNonTrafficFiberVNNAuthHeaderWS" part="AuthHeaderWS" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="ResetOnuConnection">
      <soap:operation soapAction="http://vnpt.vn/ResetOnuConnection" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
        <soap:header message="tns:ResetOnuConnectionAuthHeaderWS" part="AuthHeaderWS" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="ResetOnuConnectionByPortVisa">
      <soap:operation soapAction="http://vnpt.vn/ResetOnuConnectionByPortVisa" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
        <soap:header message="tns:ResetOnuConnectionByPortVisaAuthHeaderWS" part="AuthHeaderWS" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="UpdateUrlGponQualityDetailByUnit">
      <soap:operation soapAction="http://vnpt.vn/UpdateUrlGponQualityDetailByUnit" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
        <soap:header message="tns:UpdateUrlGponQualityDetailByUnitAuthHeaderWS" part="AuthHeaderWS" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="GetUrlQualityFile">
      <soap:operation soapAction="http://vnpt.vn/GetUrlQualityFile" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
        <soap:header message="tns:GetUrlQualityFileAuthHeaderWS" part="AuthHeaderWS" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="ChangeDslPortProfile">
      <soap:operation soapAction="http://vnpt.vn/ChangeDslPortProfile" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
        <soap:header message="tns:ChangeDslPortProfileAuthHeaderWS" part="AuthHeaderWS" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="ChangeDslPortProfileByPortVisa">
      <soap:operation soapAction="http://vnpt.vn/ChangeDslPortProfileByPortVisa" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
        <soap:header message="tns:ChangeDslPortProfileByPortVisaAuthHeaderWS" part="AuthHeaderWS" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
  </wsdl:binding>
  <wsdl:binding name="LineTestWSSoap12" type="tns:LineTestWSSoap">
    <soap12:binding transport="http://schemas.xmlsoap.org/soap/http" />
    <wsdl:operation name="Login">
      <soap12:operation soapAction="http://vnpt.vn/Login" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
        <soap12:header message="tns:LoginAuthHeaderWS" part="AuthHeaderWS" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="GetONTInfoByAccount">
      <soap12:operation soapAction="http://vnpt.vn/GetONTInfoByAccount" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
        <soap12:header message="tns:GetONTInfoByAccountAuthHeaderWS" part="AuthHeaderWS" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="ResetByPortVisa">
      <soap12:operation soapAction="http://vnpt.vn/ResetByPortVisa" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
        <soap12:header message="tns:ResetByPortVisaAuthHeaderWS" part="AuthHeaderWS" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="ResetDslPort">
      <soap12:operation soapAction="http://vnpt.vn/ResetDslPort" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
        <soap12:header message="tns:ResetDslPortAuthHeaderWS" part="AuthHeaderWS" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="TestByPortVisa">
      <soap12:operation soapAction="http://vnpt.vn/TestByPortVisa" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
        <soap12:header message="tns:TestByPortVisaAuthHeaderWS" part="AuthHeaderWS" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="TestByPort">
      <soap12:operation soapAction="http://vnpt.vn/TestByPort" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
        <soap12:header message="tns:TestByPortAuthHeaderWS" part="AuthHeaderWS" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="TestOnuByPortVisa">
      <soap12:operation soapAction="http://vnpt.vn/TestOnuByPortVisa" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
        <soap12:header message="tns:TestOnuByPortVisaAuthHeaderWS" part="AuthHeaderWS" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="TestOnuByAccount">
      <soap12:operation soapAction="http://vnpt.vn/TestOnuByAccount" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
        <soap12:header message="tns:TestOnuByAccountAuthHeaderWS" part="AuthHeaderWS" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="TestOnuPort">
      <soap12:operation soapAction="http://vnpt.vn/TestOnuPort" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
        <soap12:header message="tns:TestOnuPortAuthHeaderWS" part="AuthHeaderWS" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="GetNonTrafficFiberVNN">
      <soap12:operation soapAction="http://vnpt.vn/GetNonTrafficFiberVNN" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
        <soap12:header message="tns:GetNonTrafficFiberVNNAuthHeaderWS" part="AuthHeaderWS" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="ResetOnuConnection">
      <soap12:operation soapAction="http://vnpt.vn/ResetOnuConnection" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
        <soap12:header message="tns:ResetOnuConnectionAuthHeaderWS" part="AuthHeaderWS" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="ResetOnuConnectionByPortVisa">
      <soap12:operation soapAction="http://vnpt.vn/ResetOnuConnectionByPortVisa" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
        <soap12:header message="tns:ResetOnuConnectionByPortVisaAuthHeaderWS" part="AuthHeaderWS" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="UpdateUrlGponQualityDetailByUnit">
      <soap12:operation soapAction="http://vnpt.vn/UpdateUrlGponQualityDetailByUnit" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
        <soap12:header message="tns:UpdateUrlGponQualityDetailByUnitAuthHeaderWS" part="AuthHeaderWS" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="GetUrlQualityFile">
      <soap12:operation soapAction="http://vnpt.vn/GetUrlQualityFile" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
        <soap12:header message="tns:GetUrlQualityFileAuthHeaderWS" part="AuthHeaderWS" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="ChangeDslPortProfile">
      <soap12:operation soapAction="http://vnpt.vn/ChangeDslPortProfile" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
        <soap12:header message="tns:ChangeDslPortProfileAuthHeaderWS" part="AuthHeaderWS" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="ChangeDslPortProfileByPortVisa">
      <soap12:operation soapAction="http://vnpt.vn/ChangeDslPortProfileByPortVisa" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
        <soap12:header message="tns:ChangeDslPortProfileByPortVisaAuthHeaderWS" part="AuthHeaderWS" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
  </wsdl:binding>
  <wsdl:service name="LineTestWS">
    <wsdl:port name="LineTestWSSoap" binding="tns:LineTestWSSoap">
      <soap:address location="https://ctslinetest.vnpt.vn/LineTestWS.asmx" />
    </wsdl:port>
    <wsdl:port name="LineTestWSSoap12" binding="tns:LineTestWSSoap12">
      <soap12:address location="https://ctslinetest.vnpt.vn/LineTestWS.asmx" />
    </wsdl:port>
  </wsdl:service>
</wsdl:definitions>