import moment from "moment";
import api from './CompleteProfileAPI'
import {YEUCAU_LAY_DULIEU_TOKEN, YEUCAU_DUYET_CMS, CA_CHOXULY, GUI_FILE_HOSO_CMS} from './ca_objects'
import {LOAI_HOPDONG} from '@/constants'
import axios from 'axios'
export default {
  async DatMoiEKYC($this, data) {
    try {
      $this.loading(true);
      let req_cntt = await api.tao_req_cntt($this.axios, {vhdtb_id: data.hdtb_id}, null);
      if(req_cntt.data.error_code != "BSS-00000000")
      {
        throw (req_cntt.data.message + " - " + req_cntt.data.data);
      }
      let kq = await api.fn_datmoi_ekyc($this.axios, {
        StrRequest: req_cntt.data.data.replace('\\r\\n', ''),
        StrLink: "",
        JwtToken: ""
      });
      if(kq.data.error_code == "BSS-00000000" && kq.data.data.message == "IDG-00000000")
      {
        $this.$toast.success("Đặt mới EKYC thành công!");
        let kq_status =  await api.cap_nhat_status_hdtb($this.axios, {
          vhdtb_id: data.hdtb_id,
          vstatus: 6
        });
      }
      else
      {
        let kq_status =  await api.cap_nhat_status_hdtb($this.axios, {
          vhdtb_id: data.hdtb_id,
          vstatus: 0
        });
        throw kq.data.data.errCode + '-' + kq.data.data.errMsg;
      }
    }
    catch (ex)
    {
      throw  ex;
    }
    finally {
      $this.loading(false);
    }
  },

  async GiaHanEKYC($this, data) {
    try {
      $this.loading(true);
      let req_cntt = await api.tao_req_cntt($this.axios, {vhdtb_id: data.hdtb_id}, null);
      if(req_cntt.data.error_code != "BSS-00000000")
      {
        throw (req_cntt.data.message + " - " + req_cntt.data.data);
      }
      let kq = await api.fn_giahan_ekyc($this.axios, {
        StrRequest: req_cntt.data.data.replace('\\r\\n', ''),
        StrLink: "",
        JwtToken: ""
      });
      if(kq.data.error_code == "BSS-00000000" && kq.data.data.message == "IDG-00000000")
      {
        $this.$toast.success("Gia hạn EKYC thành công!");
        let kq_status =  await api.cap_nhat_status_hdtb($this.axios, {
          vhdtb_id: data.hdtb_id,
          vstatus: 6
        });
      }
      else
      {
        let kq_status =  await api.cap_nhat_status_hdtb($this.axios, {
          vhdtb_id: data.hdtb_id,
          vstatus: 0
        });
        throw kq.data.data.errCode + '-' + kq.data.data.errMsg;
      }
    }
    catch (ex)
    {
      throw  ex;
    }
    finally {
      $this.loading(false);
    }
  },

  async ThanhLyEKYC($this, data) {
    try {
      $this.loading(true);
      let req_cntt = await api.tao_req_cntt($this.axios, {vhdtb_id: data.hdtb_id}, null);
      if(req_cntt.data.error_code != "BSS-00000000")
      {
        console.log(req_cntt);
        throw (req_cntt.data.message + " - " + req_cntt.data.data);
      }
      let kq = await api.fn_thanhly_ekyc($this.axios, {
        StrRequest: req_cntt.data.data.replace('\\r\\n', ''),
        StrLink: "",
        JwtToken: ""
      });
      if(kq.data.error_code == "BSS-00000000" && kq.data.data.message == "IDG-00000000")
      {
        $this.$toast.success("Thanh lý EKYC thành công!");
        let kq_status =  await api.cap_nhat_status_hdtb($this.axios, {
          vhdtb_id: data.hdtb_id,
          vstatus: 6
        });
      }
      else
      {
        let kq_status =  await api.cap_nhat_status_hdtb($this.axios, {
          vhdtb_id: data.hdtb_id,
          vstatus: 0
        });
        throw kq.data.data.errCode + '-' + kq.data.data.errMsg;
      }
    }
    catch (ex)
    {
      throw  ex;
    }
    finally {
      $this.loading(false);
    }
  },

  async ThemREQ($this, data) {
    try {
      $this.loading(true);
      let req_cntt = await api.tao_req_cntt($this.axios, {vhdtb_id: data.hdtb_id}, null);
      if(req_cntt.data.error_code != "BSS-00000000")
      {
        throw (req_cntt.data.message + " - " + req_cntt.data.data);
      }
      let kq = await api.fn_themreq_ekyc($this.axios, {
        StrRequest: req_cntt.data.data.replace('\\r\\n', ''),
        StrLink: "",
        JwtToken: ""
      });
      if(kq.data.error_code == "BSS-00000000" && kq.data.data.message == "IDG-00000000")
      {
        $this.$toast.success("Thêm REQ EKYC thành công!");
        let kq_status =  await api.cap_nhat_status_hdtb($this.axios, {
          vhdtb_id: data.hdtb_id,
          vstatus: 6
        });
      }
      else
      {
        let kq_status =  await api.cap_nhat_status_hdtb($this.axios, {
          vhdtb_id: data.hdtb_id,
          vstatus: 0
        });
        throw kq.data.data.errCode + '-' + kq.data.data.errMsg;
      }
    }
    catch (ex)
    {
      throw  ex;
    }
    finally {
      $this.loading(false);
    }
  },
}
