export default {
  async ts_tttien_ckchonso_dangky(
    ma_tinh,
    psmuc_chietkhau,
    ghi_chu,
    so_tb,
    pskieuso_id,
    userid,
    context
  ) {
    let response = await context.$root.context.post(
      `/ccbs/executor/ts_tttien_ckchonso_dangky`,
      {
        ma_tinh,
        psmuc_chietkhau,
        ghi_chu,
        so_tb,
        pskieuso_id,
        userid
      }
    );
    return response.data;
  },

  async ts_tttien_ckchonso_laykieuso(so_tb, context) {
    let response = await context.$root.context.post(
      `/ccbs/executor/ts_tttien_ckchonso_laykieuso`,
      {
        so_tb
      }
    );
    return response.data;
  },

  async ts_tttien_ckchonso_checktb(ma_tinh, so_tb, userid, context) {
    let response = await context.$root.context.post(
      `/ccbs/executor/ts_tttien_ckchonso_checktb`,
      {
        ma_tinh,
        so_tb,
        userid
      }
    );
    return response.data;
  },
  async ts_prepayment_dangky(
    so_tb,
    package_id,
    package_id_src,
    congvan_id,
    ghichu,
    ma_tinh,
    userid,
    context
  ) {
    let response = await context.$root.context.post(
      `/ccbs/executor/ts_prepayment_dangky`,
      {
        so_tb,
        package_id,
        package_id_src,
        congvan_id,
        ghichu,
        ma_tinh,
        userid
      }
    );
    return response.data;
  },
  async tt_ts_get_tb_esim(ma_tinh, soSim_moi, ma_tb, context) {
    let response = await context.$root.context.post(
      `/ccbs/executor/tt_ts_get_tb_esim`,
      {
        ma_tinh: ma_tinh,
        so_msin_moi: soSim_moi,
        so_tb: ma_tb
      }
    );
    return response.data;
  },
  async tt_ts_get_tb_sim(ma_tb, soSim_moi, ma_tinh, context) {
    let response = await context.$root.context.post(
      `/ccbs/executor/tt_ts_get_tb_sim`,
      {
        ma_tinh: ma_tinh,
        so_msin_moi: soSim_moi,
        so_tb: ma_tb
      }
    );
    return response.data;
  },
  async ts_tracuu_laytt_tb(ma_tb, user_neo, ip, ma_tinh, context) {
    let response = await context.$root.context.post(
      `/ccbs/qltn/ts_tracuu_laytt_tb`,
      {
        ma_tinh: ma_tinh,
        so_tb: ma_tb,
        userid: user_neo,
        userip: ip
      }
    );
    return response.data;
  },
  async ts_tracuu_laytt_tb_ts(ma_tb, ma_tinh, context) {
    let response = await context.$root.context.post(
      `/ccbs/executor/ts_tracuu_laytt_tb_ts`,
      {
        ma_tinh: ma_tinh,
        so_tb: ma_tb
      }
    );
    return response.data;
  },
  async ts_tamthu_cuocnong_tt_no(
    ma_tinh,
    ma_tinh_cu,
    ma_kh,
    so_tb,
    chukyno,
    userid,
    loaitien,
    context
  ) {
    let response = await context.$root.context.post(
      `/ccbs/executor/ts_tamthu_cuocnong_tt_no`,
      {
        ma_tinh,
        ma_tinh_cu,
        ma_kh,
        so_tb,
        chukyno,
        userid,
        loaitien
      }
    );
    return response.data;
  },
  async ts_tamthu_cuocnong_them_phieu(
    tongtien,
    ma_tinh,
    ma_kh,
    ghichu,
    phieu_id,
    ma_tn,
    type,
    ma_tinh_cu,
    so_tb,
    chukyno,
    httt_id,
    ngaynhap,
    ip,
    context
  ) {
    let rs = {
      IssSuccessed: "",
      Message: ""
    };
    try {
      let response = await context.$root.context.post(
        `/ccbs/executor/ts_tamthu_cuocnong_them_phieu`,
        {
          tongtien,
          ma_tinh,
          ma_kh,
          ghichu,
          phieu_id,
          ma_tn,
          type,
          ma_tinh_cu,
          so_tb,
          chukyno,
          httt_id,
          ngaynhap,
          ip
        }
      );
      let rsObj = response.data;
      rs.IssSuccessed = rsObj.result == "Thêm mới thành công.";
      rs.Message = rsObj.result;
    } catch (err) {
      rs.IssSuccessed = false;
      rs.Message = err.toString();
    }

    return rs;
  },
  get_kiemtra_tuvan_mytv: async (axios, data) =>
    axios.get(`/web-ccdv/tuvan_mytv/kiemtra_tuvan_mytv`, data)
};
