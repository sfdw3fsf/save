import axios from 'axios'
import api from '../../modules/contract/complete_profile/API/CompleteProfileAPI'


export const actions = {
  async KichHoatRASmartCA ({ commit, state }, postdata) {
    try {
      let kq = await axios.post('/web-thicong/cnttdynamic/api', {
        hdtb_id: postdata.hdtb_id,
        vdem: 0,
        vtype: postdata.nv_thaotac
      })
      console.log(kq)
      if (kq.data.data.status == '00') {
        return true
      }
      else
      {
        let loi = kq.data.data.message;
        throw new Error(loi);
      }
    }
    catch (e)
    {
      throw  e;
    }
  },
  async TaoRequestSMCA({ commit, state }, hdtb_id)
  {
    try {
      let req_duyet = await axios.post("/web-thicong/vnptsmartca/taoreqduyetsmca", {hdtb_id: hdtb_id});
      return req_duyet.data;
    }
    catch (e)
    {
      throw  e;
    }
  },
  async TaoMessageDuyetSMCA({ commit, state }, req_duyet)
  {
    try {
      let hash_data = await axios.post("/web-thicong/vnptsmartca/TaoMessageDuyetSMCA", req_duyet);
      console.log(state);
      return hash_data.data.data;
    }
    catch (e)
    {
      throw  e;
    }
  },
  async KyBangSmartCA({ commit, state }, obj_taoyc_ky)
  {
    try {
      console.log(state)
      let tao_yc_smca = await axios.post("/web-thicong/vnptsmartca/GuiYeuCauKySmartCA", obj_taoyc_ky)
      console.log(tao_yc_smca.data.data)
      let confirm_req = {
        sp_password: "ZmI4OTY1NDQ-ZWU1Ni00Njgz",
        sp_id: "4683-638193082754533115.apps.smartcaapi.com",
        user_id: obj_taoyc_ky.user_id,
        password: state.matkhau_smca,
        transaction_id: tao_yc_smca.data.data.data.transaction_id,
        sad: tao_yc_smca.data.data.data.sad
      }
      let confirm_yc = await axios.post("/web-thicong/vnptsmartca/ConfirmKySoSmartCA", confirm_req)
      let signature_value = confirm_yc.data.data.data.signatures[0].signature_value
      return signature_value
    }
    catch (e)
    {
      throw e;
    }
  },
   async layThongTinNhanVienCA({ commit, state }, postdata)
   {
      try {
        let kq = await axios.post('/web-thicong/dvcntt/laythongtinnguoiduyet', postdata)
        console.log(postdata);
        console.log(kq.data.data);
        try {
          if (kq.data.data[0].so_gt != state.user_ca_infor[0].so_gt) {
            commit("setMatKhau", "");
          }
        }
        catch {}
        commit("setSmartCAUser", kq.data.data)
      }
      catch (e)
      {
        throw e;
      }
   },
  async LayThongTinSmartCAUser(context, postdata)
  {
    try {
      let kq = await axios.post('/web-thicong/vnptsmartca/LayThongTinCTSSmartCA', postdata)
      context.commit("setDuLieuSmartCA", kq.data.data)
    }
    catch (e)
    {
      throw  e;
    }
  },
   setMatKhau({commit}, data)
   {
     commit("setMatKhau", data);
   }
}
