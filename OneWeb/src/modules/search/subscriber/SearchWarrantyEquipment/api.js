export default{
    lay_ds_tracuu_bh_v2: async (axios, data) => axios.post("/web-tracuu/tracuu-thietbi-baohanh/lay_ds_tracuu_bh_v2", data),
    lay_ds_tracuu_bh_ct_v2: async (axios, data) => axios.post("web-tracuu/tracuu-thietbi-baohanh/lay_ds_tracuu_bh_ct_v2", data),
    lay_tt_tb_baohanh_tbi: async (axios, data) => axios.post("web-tracuu/tracuu-thietbi-baohanh/lay_tt_tb_baohanh_tbi", data),
  }
  