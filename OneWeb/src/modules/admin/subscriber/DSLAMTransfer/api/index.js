export default {
  // information Dslam
  getInformationBrasList: (axios, data) =>
    axios.post("/web-danhba/cat_chuyen_dslam/lay_ht_ds_bras", data),
  getInformationDslamList: (axios, data) =>
    axios.post("/web-danhba/cat_chuyen_dslam/sp_dslam_bras", data),
  getInformationDslamVDCList: (axios, data) =>
    axios.post("/web-danhba/cat_chuyen_dslam/sp_dslam_bras_vdc", data),

  // information new Dslam
  getInformationNewBrasList: (axios, data) =>
    axios.post("/web-danhba/cat_chuyen_dslam/lay_ht_ds_bras", data),
  getInformationNewDslamVDCList: (axios, data) =>
    axios.post("/web-danhba/cat_chuyen_dslam/sp_dslam_bras_vdc", data),

  // list thue bao
  getThueBaoList: (axios, data) =>
    axios.post("/web-danhba/cat_chuyen_dslam/sp_dslam_ds_doi", data),

  // create new bras
  saveBras: (axios, data) =>
    axios.post("/web-danhba/cat_chuyen_dslam/sp_dslam_update", data),

    //click ho
  completedDslam :(axios, data) =>
    axios.post("/web-danhba/cat_chuyen_dslam/sp_dslam_hoanthien", data), 

  // delete bras
  deleteBras: (axios, data) =>
    axios.post("/web-danhba/cat_chuyen_dslam/fn_dslam_delete", data)
};
