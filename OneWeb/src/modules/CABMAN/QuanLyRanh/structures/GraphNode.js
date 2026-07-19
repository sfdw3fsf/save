export default function GraphNode() {
  this.id = null;
  this.id_polyline = null;
  this.label = null;
  this.style = null;
  this.symbol = null;
  this.tooltipText = null;
  this.insideLabel = null;
  this.mouseover = false;
  this.position = {
    lat: 0,
    lng: 0
  };
  this.point = null;
  this.icon = null;
  this.hovericon = null;
  this.modified = false;
  this.draggable = false;
  this.isDiemuon = false;
  this.visible = true;
  this.icons = {
    0: "CHUA_XAC_DINH.jpg",
    1: "NHA_CAP.jpg",
    2: "T.png", // "TU_CAP.jpg", Thay doi theo yeu cau chuan hoa ma mang ngoai vi
    3: "H.png", // 3: "HOP_CAP.jpg", Thay doi theo yeu cau chuan hoa ma mang ngoai vi
    4: "M.png", // "MANG_XONG.jpg", Thay doi theo yeu cau chuan hoa ma mang ngoai vi
    5: "SPLITTER.jpg",
    6: "TU_QUANG_SPLITTER.jpg",
    7: "KET_CUOI_AO.jpg",
    8: "O.png", // "KHAY_ODF.jpg", Thay doi theo yeu cau chuan hoa ma mang ngoai vi
    9: "TRAM_THIET_BI.jpg",
    10: "HOST.jpg",
    11: "MSAN_RSU_BTS.jpg",
    12: "MSAN_RSU.jpg",
    13: "BTS.jpg",
    14: "CAP.jpg",
    15: "STRIPE.jpg",
    22: "HOP_OTB.jpg",
    23: "HOP_ATB.jpg",
    25: "CE.png", // Thay doi theo yeu cau chuan hoa ma mang ngoai vi
    26: "O(S1).png", // Thay doi theo yeu cau chuan hoa ma mang ngoai vi
    27: "O(S1).png", // Thay doi theo yeu cau chuan hoa ma mang ngoai vi
    28: "O{S2(1)}.png", // Thay doi theo yeu cau chuan hoa ma mang ngoai vi
    29: "T(S1).png", // Thay doi theo yeu cau chuan hoa ma mang ngoai vi
    30: "T(S1).png", // Thay doi theo yeu cau chuan hoa ma mang ngoai vi
    31: "T{S2(1)}.png", // Thay doi theo yeu cau chuan hoa ma mang ngoai vi

    32: "H(S1).png", // Thay doi theo yeu cau chuan hoa ma mang ngoai vi
    33: "H(S1).png", // Thay doi theo yeu cau chuan hoa ma mang ngoai vi
    34: "H{S2(1)}.png", // Thay doi theo yeu cau chuan hoa ma mang ngoai vi

    35: "M(S1).png", // Thay doi theo yeu cau chuan hoa ma mang ngoai vi
    36: "M(S1).png", // Thay doi theo yeu cau chuan hoa ma mang ngoai vi
    37: "M{S2(1)}.png" // Thay doi theo yeu cau chuan hoa ma mang ngoai vi
  };
  this.setIcon = style => {
    console.log("🚀 ~ file: GraphNode.js ~ line 42 ~ GraphNode ~ style", style);
    if (style === 100) {
      const icondiemuon = {
        path: "M-20,0a20,20 0 1,0 40,0a20,20 0 1,0 -40,0",
        fillColor: "#ffa500",
        fillOpacity: 0.6,
        anchor: new window.google.maps.Point(0, 0),
        strokeWeight: 0,
        scale: 0.4
      };
      this.icon = icondiemuon;
      return;
    }
    let iconname = null;
    try {
      if (isNaN(style)) {
        iconname = style + ".jpg";
      } else {
        iconname = this.icons[style];
      }
    } catch (error) {
      iconname = "CHUA_XAC_DINH.jpg";
    }
    if (iconname === undefined) {
      iconname = "CHUA_XAC_DINH.jpg";
    }
    if (iconname === "SPLITTER.jpg") {
      // Thay doi theo yeu cau chuan hoa ma mang ngoai vi
      // if (this.insideLabel === "S1") {
      //   iconname = "SPLITTER_S1.jpg";
      // }
      // if (this.insideLabel === "S2") {
      //   iconname = "SPLITTER_S2.jpg";
      // }
    }
    if (iconname === "TU_QUANG_SPLITTER.jpg") {
      if (this.insideLabel === "S1") {
        // Thay doi theo yeu cau chuan hoa ma mang ngoai vi
        // iconname = "TU_QUANG_SPLITTER_S1.jpg";
        iconname = "T(S1).png";
      }
      if (this.insideLabel === "S2") {
        // Thay doi theo yeu cau chuan hoa ma mang ngoai vi
        // iconname = "TU_QUANG_SPLITTER_S2.jpg";
        iconname = "T(S1).png";
      }
      if (this.insideLabel === "1") {
        iconname = "TU_QUANG_SPLITTER_1.jpg";
      }
      if (this.insideLabel === "2") {
        iconname = "TU_QUANG_SPLITTER_2.jpg";
      }
      if (this.insideLabel === "3") {
        iconname = "TU_QUANG_SPLITTER_3.jpg";
      }
      if (this.insideLabel === "4") {
        iconname = "TU_QUANG_SPLITTER_4.jpg";
      }
      if (this.insideLabel === "5") {
        iconname = "TU_QUANG_SPLITTER_5.jpg";
      }
    }
    console.log("🚀 ~ file: GraphNode.js:98 ~ GraphNode ~ iconname", iconname);
    let icon = {
      url: "/static/icons/icon100x100/" + iconname,
      // size: new window.google.maps.Size(30, 30),
      size: {
        width: 25,
        height: 25
      },
      // scaledSize: new window.google.maps.Size(30, 30),
      labelOrigin: { x: 16, y: 40 }
      // anchor: new window.google.maps.Point(15, 15)
    };
    let hovericon = {
      url:
        "/static/icons/icon100x100/" +
        iconname.substr(0, iconname.lastIndexOf(".jpg")) +
        ".jpg",
      size: {
        width: 25,
        height: 25
      },
      // size: new window.google.maps.Size(30, 30),
      // scaledSize: new window.google.maps.Size(30, 30),
      labelOrigin: { x: 16, y: 40 }
      // anchor: new window.google.maps.Point(15, 15)
    };
    this.icon = icon;
    this.hovericon = hovericon;
  };
  this.getLabel = () => {
    return {
      text: this.symbol,
      color: "black",
      fontSize: "12px",
      fontFamily: "Arial",
      fontWeight: "bold"
    };
  };
  this.getIcon = () => {
    // return this.icon;
    if (this.mouseover) {
      return this.hovericon;
    } else {
      return this.icon;
    }
  };
  this.getID = () => {
    return this.id;
  };
  this.getLabel = () => {
    return this.label;
  };
  this.getStyle = () => {
    return this.style;
  };
  this.setPosition = position => {
    try {
      if (position === null) {
        this.position.lat = 0;
        this.position.lng = 0;
        return true;
      }
      if (typeof position === "string") {
        if (position === null || position === "") {
          this.position.lat = 0;
          this.position.lng = 0;
        } else {
          let lat = position.split(",")[0];
          let lng = position.split(",")[1];
          this.position.lat = parseFloat(lat);
          this.position.lng = parseFloat(lng);
        }
      }
      if (typeof position === "object") {
        this.position.lat = position.lat;
        this.position.lng = position.lng;
      }
      return true;
    } catch (error) {
      console.log("setPosition", error);
      return false;
    }
  };
  this.getPosition = () => {
    return this.position;
  };
  this.setSymbol = symbol => {
    try {
      this.symbol = symbol;
      return true;
    } catch (error) {
      return false;
    }
  };
  this.setIsDiemuon = value => {
    try {
      this.isDiemuon = value;
      return true;
    } catch (error) {
      return false;
    }
  };
}
