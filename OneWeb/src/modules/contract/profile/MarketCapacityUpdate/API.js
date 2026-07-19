// [UR1.5.025] - Cập nhật dung lượng thị trường
export default {
  lay_ds_hodan_thuthap_theo_nv: (axios) => axios.post(`web-danhba/dungluong_thitruong/lay_ds_hodan_thuthap_theo_nv`, {vdsttduyet_id: -1}),
  sp_hodan_xoa: (axios, id_hodan) => axios.post(`web-danhba/dungluong_thitruong/sp_hodan_xoa`, {hodan_id: id_hodan}),
  hodan_chi_tiet: (axios, id_hodan) => axios.post(`web-danhba/dungluong_thitruong/hodan_chi_tiet`, {hodan_id: id_hodan}),
  ds_thuebao_sdkhid: (axios, id_hodan) => axios.post(`web-danhba/dungluong_thitruong/ds_thuebao_sdkhid`, {sdkh_id: id_hodan}),
  ds_thanhtoan_sdkhid: (axios, id_hodan) => axios.post(`web-danhba/dungluong_thitruong/ds_thanhtoan_sdkhid`, {sdkh_id: id_hodan}),
  ds_khachhang_sdkhid: (axios, id_hodan) => axios.post(`web-danhba/dungluong_thitruong/ds_khachhang_sdkhid`, {sdkh_id: id_hodan}),
  ds_dichvudoithu_sdkhid: (axios, id_hodan) => axios.post(`web-danhba/dungluong_thitruong/ds_dichvudoithu_sdkhid`, {sdkh_id: id_hodan}),
  sp_duyet_hodan: (axios, data) => axios.post(`web-danhba/dungluong_thitruong/sp_duyet_hodan`, data),
}
