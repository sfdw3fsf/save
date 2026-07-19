import xml2js from "xml2js"; 
class xmlUtil {
    getFourthLevelContentXml(xmlDoc, { first, second, third, fourth }) {
        return xmlDoc
          .getElementsByTagName(first)[0]
          .getElementsByTagName(second)[0]
          .getElementsByTagName(third)[0]
          .getElementsByTagName(fourth).length > 0
          ? xmlDoc
              .getElementsByTagName(first)[0]
              .getElementsByTagName(second)[0]
              .getElementsByTagName(third)[0]
              .getElementsByTagName(fourth)[0].textContent
          : "";
      }
      getThirdLevelContentXml(xmlDoc, { first, second, third }) {
        return xmlDoc
          .getElementsByTagName(first)[0]
          .getElementsByTagName(second)[0]
          .getElementsByTagName(third).length > 0
          ? xmlDoc
              .getElementsByTagName(first)[0]
              .getElementsByTagName(second)[0]
              .getElementsByTagName(third)[0].textContent
          : "";
      }

      getFifthLevelContentXml(xmlDoc, { first, second, third, fourth, fifth }) {
        return xmlDoc
          .getElementsByTagName(first)[0]
          .getElementsByTagName(second)[0]
          .getElementsByTagName(third)[0]
          .getElementsByTagName(fourth)[0]
          .getElementsByTagName(fifth).length > 0
          ? xmlDoc
              .getElementsByTagName(first)[0]
              .getElementsByTagName(second)[0]
              .getElementsByTagName(third)[0]
              .getElementsByTagName(fourth)[0]
              .getElementsByTagName(fifth)[0].textContent
          : "";
      }

     ChuanHoa_XML(Xau) {
        let result = "";
        result =  Xau.replace("&", "&amp;");
        result =  result.replace("<", "&lt;");
        result =  result.replace(">", "&gt;");
        result =  result.replace("'", "&apos;");
        result =  result.replace('"', "&quot;");
        return result;
      }

      ObjectToXML(filexml) {
        let xmlDoc;
        if (window.DOMParser) {
          const parser = new DOMParser();
          xmlDoc = parser.parseFromString(filexml.trim(), "text/xml");
        } else {
          xmlDoc = new ActiveXObject("Microsoft.XMLDOM");
          xmlDoc.async = false;
          xmlDoc.loadXML(filexml.trim());
        }
        return xmlDoc;
      }
      GetXMLFromObject(objHoaDonDienTu) {
        let builder = new xml2js.Builder();
        let xml = builder.buildObject(objHoaDonDienTu);
        return xml;
      }

}

export default new xmlUtil()