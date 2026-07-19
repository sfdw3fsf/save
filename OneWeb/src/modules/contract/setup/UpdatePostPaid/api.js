export default{
  app_tb_tracuu_thongtin_tt: async (axios, data) => axios.post("/ccbs/executor/app_tb_tracuu_thongtin_tt", data),
  ts_lichsu_capnhat_thuebao_tratruoc: async (axios, data) => axios.post("/ccbs/executor/ts_lichsu_capnhat_thuebao_tratruoc", data),
  ts_lichsu_capnhat_anh: async (axios, data) => axios.post("/ccbs/executor/ts_lichsu_capnhat_anh", data),
  app_tb_image_dktt: async (axios, data) => axios.post("/ccbs//oneBss/app_tb_image_dktt", data),
  LOAI_GT_NEO: (axios, data) => axios.get("web-quantri/danhmuc-form/DANHMUC_CN_TRATRUOC/LOAI_GT_NEO", data),
  TINH_NEOS: (axios, data) => axios.get("web-quantri/danhmuc-form/DANHMUC_CN_TRATRUOC/TINH_NEOS", data),
  DOITUONG_SD_NEO: (axios, data) => axios.get("web-quantri/danhmuc-form/DANHMUC_CN_TRATRUOC/DOITUONG_SD_NEO", data),
  LOAI_KHDN_NEO: (axios, data) => axios.get("web-quantri/danhmuc-form/DANHMUC_CN_TRATRUOC/LOAI_KHDN_NEO", data),
  LOAI_KHCN_NEO: (axios, data) => axios.get("web-quantri/danhmuc-form/DANHMUC_CN_TRATRUOC/LOAI_KHCN_NEO", data),
  QUOCTICH_NEO: (axios, data) => axios.get("web-quantri/danhmuc-form/DANHMUC_CN_TRATRUOC/QUOCTICH_NEO", data)
}
