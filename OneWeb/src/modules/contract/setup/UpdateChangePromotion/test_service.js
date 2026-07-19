import service from "./service.js"
export default {
  delay (milliseconds) {
    return new Promise(resolve => setTimeout(resolve, milliseconds))
  },
  async lay_ds_phieu_chuatt (hdtb_id) {
    var result = await service.lay_ds_phieu_chuatt(4442813)
    return result
  },
  async lay_khuyenmai_hdtb (data) {
    await this.delay(1000)
    return [
      {
        "khuyenmai_id": 10958,
        "ten_km": "10958 . 3132/TTKD VNPT-HP-ĐHNV - Trả trước FiberVNN ( lắp mớichuyển mạng)",
        "chon": 0
      },
      {
        "khuyenmai_id": 11395,
        "ten_km": "11395 . Tra truoc F1-F6 nam 2021",
        "chon": 0
      },
      {
        "khuyenmai_id": 10924,
        "ten_km": "10924 . 917/TTKD VNPT-HP-ĐHNV - Đặt cọc thay thế thiết bị",
        "chon": 0
      },
      {
        "khuyenmai_id": 897,
        "ten_km": "897 . 1973/ TTKD VNPT-HP-ĐHNV - Gói  F1 - F6 trả trước (đang SD)",
        "chon": 0
      },
      {
        "khuyenmai_id": 809,
        "ten_km": "809 . Chính sách cơ sở cấp quận",
        "chon": 0
      },
      {
        "khuyenmai_id": 10938,
        "ten_km": "10938 . Đặt cọc ký quỹ FiberVNN",
        "chon": 0
      },
      {
        "khuyenmai_id": 11069,
        "ten_km": "11069 . Trả trước Fiber2019",
        "chon": 0
      }
    ]
  }

}
