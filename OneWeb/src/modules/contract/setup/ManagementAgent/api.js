export default {
  getDanhMucCCB: async (axios, data) =>
    axios.get(`web-quantri/danhmuc-form/LAY_DANHMUC_CCBS/${data.ten_ds}`),
  getDSDaiLy: async (axios, data) =>
    axios.post(`ccbs/executor/ts_province_prepaid`),
  getDaiLyInfo: async (axios, data) =>
    axios.post(`ccbs/executor/ts_hd_nhancong_tt`, {
      so_tb: data.so_tb
    }),
  getDaiLyInfoByAgentId: async (axios, data) =>
    axios.post(`ccbs/executor/ts_hd_nhancong_getagentinfo`, {
      agent_id: data.agent_id
    }),
  getDaiLyPermission: async (axios, data) =>
    axios.post(`ccbs/executor/ts_select_role_daily`, {
      so_tb: data.so_tb
    }),
  updateDaiLyPermission: async (axios, data) =>
    axios.post(`ccbs/executor/ts_grant_role_daily`, {
      so_tb_daily: data.so_tb_daily,
      dk_hc: data.dk_hc,
      dk_capnhat_tt: data.dk_capnhat_tt
    }),
  resetPassword: async (axios, data) =>
    axios.post(`ccbs/executor/ts_hd_nhancong_resetmk`, {
      so_tb: data.so_tb
    }),
  getLichSuDaiLy: async (axios, data) =>
    axios.post(`ccbs/executor/ts_hd_nhancong_getlsdl`, {
      so_tb: data.so_tb
    }),
  taoDaiLy: async (axios, data) => {
    return axios.post(`ccbs/executor/ts_hd_nhancong_capnhat`, data);
  },
  createUpdateMemberDaiLy: async (axios, data) => {
    return axios.post("ccbs/executor/ts_hd_nhancong_add_agent_members", data);
  },
  listMemberDaiLy: async (axios, data) => {
    const { agent_id } = data;

    return axios.post("ccbs/executor/ts_list_agent_member", {
      agent_id,
      agentid: agent_id
    });
  }
};
