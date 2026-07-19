import service from "./service"
export default{
  async lay_ds_hopdong_theo_matb (jsonData) {
    var result = await service.lay_ds_hopdong_theo_matb(jsonData)
    result.data[0].tthd_id = 5
    result.data[0].loaihd_id = 1
    return result
  }
}
