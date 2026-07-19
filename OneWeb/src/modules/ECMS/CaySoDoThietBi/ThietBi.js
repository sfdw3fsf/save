export default {
  SEPARATOR: "_",
  PRE_DONVI: "DONVI",
  PRE_TURACK: "TURACK",
  PRE_ODF: "ODF",
  PRE_BRAS: "BRAS",
  PRE_BRAS_SLOT: "BRAS_SLOT",
  PRE_BRAS_SLOT_SUB: "BRAS_SLOT_SUB",
  PRE_BRAS_CARD: "BRAS_CARD",
  PRE_BRAS_CARD_PORT: "BRAS_CARD_PORT",
  PRE_GPON: "GPON",
  PRE_GPON_SLOT: "GPON_SLOT",
  PRE_GPON_CARD: "GPON_CARD",
  PRE_GPON_CARD_PORT: "GPON_CARD_PORT",
  PRE_MANE: "MANE",
  PRE_MANE_SLOT: "MANE_SLOT",
  PRE_MANE_CARD: "MANE_CARD",
  PRE_MANE_CARD_PORT: "MANE_CARD_PORT",
  PRE_MANE_CARD_MODULE: "MANE_CARD_MODULE",
  PRE_MANE_CARD_MODULE_PORT: "MANE_CARD_MODULE_PORT",
  PRE_SWITCH: "SWITCH",
  PRE_SWITCH_SLOT: "SWITCH_SLOT",
  PRE_SWITCH_SLOT_SUB: "SWITCH_SLOT_SUB",
  PRE_SWITCH_PORT: "SWITCH_PORT",
  PRE_SWITCH_CARD: "SWITCH_CARD",
  PRE_SWITCH_CARD_PORT: "SWITCH_CARD_PORT",
  PRE_DSLAM: "DSLAM",
  PRE_DSLAM_FRAME: "DSLAM_FRAME",
  PRE_DSLAM_FRAME_SHELF: "DSLAM_FRAME_SHELF",
  PRE_DSLAM_FRAME_SHELF_SLOT: "DSLAM_FRAME_SHELF_SLOT",
  PRE_DSLAM_FRAME_SHELF_CARD: "DSLAM_FRAME_SHELF_CARD",
  PRE_DSLAM_FRAME_SHELF_CARD_PORT: "DSLAM_FRAME_SHELF_CARD_PORT",
  PRE_DSLAM_FRAME_SHELF_CARD_MODULE: "DSLAM_FRAME_SHELF_CARD_MODULE",
  PRE_DSLAM_FRAME_SHELF_CARD_MODULE_PORT:
    "DSLAM_FRAME_SHELF_CARD_MODULE_PORT",
  PRE_FRAME: "FRAME",
  PRE_FRAME_SLOT: "FRAME_SLOT",
  PRE_FRAME_CARD: "FRAME_CARD",
  PRE_EWSD: "EWSD",
  PRE_EWSD_SHELF: "EWSD_SHELF",
  PRE_EWSD_SHELF_SLOT: "EWSD_SHELF_SLOT",
  PRE_EWSD_SHELF_CARD: "EWSD_SHELF_CARD",

  /**
   * Lấy tên hiển thị của thiết bị theo mã
   * @param {*} code Mã quy định thiết bị
   * @returns Tên thiết bị
   */
  getText: function (code) {
    let result = "";
    switch (code) {
      case this.PRE_DONVI:
        result = "Đơn vị";
        break;
      case this.PRE_TURACK:
        result = "Tủ rack";
        break;
      case this.PRE_EWSD:
        result = "Ewsd";
        break;
      case this.PRE_EWSD_SHELF:
        result = "Shelf";
        break;
      case this.PRE_EWSD_SHELF_CARD:
        result = "Card";
        break;
      default:
        break;
    }
    return result;
  },

  /**
   * Lấy mã của thiết bị con theo mã của thiết bị cha
   * @param {*} code Mã quy định thiết bị cha đang cần lấy thiết bị con
   * @returns Mã thiết bị con
   */
  getChildren: function (code) {
    let result = "";
    switch (code) {
      case this.PRE_DONVI:
        result = this.PRE_TURACK;
        break;
      case this.PRE_TURACK:
        result = this.PRE_EWSD;
        break;
      case this.PRE_EWSD:
        result = this.PRE_EWSD_SHELF;
        break;
      case this.PRE_EWSD_SHELF:
        result = this.PRE_EWSD_SHELF_CARD;
        break;
      default:
        break;
    }
    return result;
  },

  /**
   * Lấy tên hiển thị của thiết bị con theo mã của thiết bị cha
   * @param {*} code Mã quy định thiết bị cha đang cần lấy thiết bị con
   * @returns Tên thiết bị con
   */
  getChildrenText: function (code) {
    return this.getText(this.getChildren(code));
  },

  /**
   * Lấy mã của thiết bị cha theo mã của thiết bị con
   * @param {*} code Mã quy định thiết bị con đang cần lấy thiết bị cha
   * @returns Mã thiết bị cha
   */
  getParent: function (code) {
    let result = "";
    switch (code) {
      case this.PRE_TURACK:
        result = this.PRE_DONVI;
        break;
      case this.PRE_EWSD:
        result = this.PRE_TURACK;
        break;
      case this.PRE_EWSD_SHELF:
        result = this.PRE_EWSD;
        break;
      case this.PRE_EWSD_SHELF_CARD:
        result = this.PRE_EWSD_SHELF;
        break;
      default:
        break;
    }
    return result;
  },

  /**
   * Lấy tên hiển thị của thiết bị cha theo mã của thiết bị con
   * @param {*} code Mã quy định thiết bị con đang cần lấy thiết bị cha
   * @returns Tên thiết bị cha
   */
  getParentText: function (code) {
    return this.getText(this.getParent(code));
  }
}
