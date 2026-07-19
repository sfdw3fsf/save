export default {
  SEPARATOR: '_',
  PRE_NET: 'NET',
  PRE_IDC: 'IDC',
  PRE_HTM: 'HTM',
  PRE_HTAH: 'HTAH',
  PRE_HTCLOUD: 'HTCLOUD',
  PRE_HTIDG: 'HTIDG',
  PRE_TOANHA: 'TOANHA',
  PRE_MATSAN: 'MATSAN',
  PRE_ZONE: 'ZONE',
  PRE_RACK: 'RACK',
  PRE_DEVICE: 'THIETBI',
  PRE_PORT: 'PORT',
  PRE_VRF: 'VRF',
  PRE_ZONE_VRF: 'ZONE-VRF',
  PRE_VLAN: 'VLAN',
  PRE_SUBNET: 'SUBNET',
  PRE_IP_STORE: 'IP-STORE',
  PRE_IP_ADDRESS: 'IP-ADDRESS',
  /**
   * Lấy tên hiển thị của thiết bị theo mã
   * @param {*} code Mã quy định thiết bị
   * @returns Tên thiết bị
   */
  getText: function(code, subType) {
    let result = ''
    switch (code) {
      case this.PRE_NET:
        result = 'Mạng'
        break
      case this.PRE_IDC:
        result = 'IDC'
        break
      case this.PRE_HTM:
        result = 'Hạ tầng mạng'
        break
      case this.PRE_HTAH:
        result = 'Hạ tầng ảo hoá'
        break
      case this.PRE_HTCLOUD:
        result = 'Hạ tầng smartcloud'
        break
      case this.PRE_HTIDG:
        result = 'Hạ tầng IDG'
        break
      case this.PRE_TOANHA:
        result = 'Toàn nhà'
        break
      case this.PRE_MATSAN:
        result = 'Mặt sàn'
        break
      case this.PRE_ZONE:
        result = 'Zone'
        break
      case this.PRE_RACK:
        result = 'Rack'
        break
      case this.PRE_DEVICE:
        switch (subType) {
          case 'MANG':
            result = 'Thiết bị mạng'
            break
          case 'MAYCHU':
            result = 'Thiết bị máy chủ'
            break
          case 'TUDIA':
            result = 'Thiết bị tủ đĩa'
            break
          case 'IDC':
            result = 'Thiết bị IDC'
            break
          case 'KHAYDIA':
            result = 'Khay đĩa'
            break
          case 'CONTROLLER':
            result = 'Controller'
            break
          case 'KHAC':
            result = 'Thiết bị khác'
            break
          default:
        }
        break
      case this.PRE_PORT:
        switch (subType) {
          case 'INTERFACE':
            result = 'Port Interface'
            break
          case 'POWER':
            result = 'Port Power'
            break
          case 'CONSOLE':
            result = 'Port Console'
            break
          default:
        }
        break
      case this.PRE_VRF:
        result = 'VRF'
        break
      case this.PRE_ZONE_VRF:
        result = 'Network Zone'
        break
      case this.PRE_VLAN:
        result = 'VLAN'
        break
      case this.PRE_SUBNET:
        result = 'Subnet'
        break
      case this.PRE_IP_STORE:
        result = 'IP Store'
        break
      case this.PRE_IP_ADDRESS:
        result = 'IP Address'
        break
      default:
        result = code
        break
    }
    return result
  },

  getChildren: function(code) {
    let result = ''
    switch (code) {
      case this.PRE_DONVI:
        result = this.PRE_TURACK
        break
      case this.PRE_TURACK:
        result = this.PRE_EWSD
        break
      case this.PRE_EWSD:
        result = this.PRE_EWSD_SHELF
        break
      case this.PRE_EWSD_SHELF:
        result = this.PRE_EWSD_SHELF_CARD
        break
      default:
        break
    }
    return result
  },

  /**
   * Lấy tên hiển thị của thiết bị con theo mã của thiết bị cha
   * @param {*} code Mã quy định thiết bị cha đang cần lấy thiết bị con
   * @returns Tên thiết bị con
   */
  getChildrenText: function(code) {
    return this.getText(this.getChildren(code))
  },

  /**
   * Lấy mã của thiết bị cha theo mã của thiết bị con
   * @param {*} code Mã quy định thiết bị con đang cần lấy thiết bị cha
   * @returns Mã thiết bị cha
   */
  getParent: function(code) {
    let result = ''
    switch (code) {
      case this.PRE_TURACK:
        result = this.PRE_DONVI
        break
      case this.PRE_EWSD:
        result = this.PRE_TURACK
        break
      case this.PRE_EWSD_SHELF:
        result = this.PRE_EWSD
        break
      case this.PRE_EWSD_SHELF_CARD:
        result = this.PRE_EWSD_SHELF
        break
      default:
        break
    }
    return result
  },

  /**
   * Lấy tên hiển thị của thiết bị cha theo mã của thiết bị con
   * @param {*} code Mã quy định thiết bị con đang cần lấy thiết bị cha
   * @returns Tên thiết bị cha
   */
  getParentText: function(code) {
    return this.getText(this.getParent(code))
  }
}
