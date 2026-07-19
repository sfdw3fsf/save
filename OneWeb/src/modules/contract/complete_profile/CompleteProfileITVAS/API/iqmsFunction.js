import moment from "moment";
import api from './CompleteProfileAPI'
import {YEUCAU_LAY_DULIEU_TOKEN, YEUCAU_DUYET_CMS, CA_CHOXULY, GUI_FILE_HOSO_CMS} from './ca_objects'
import {LOAI_HOPDONG} from '@/constants'
import axios from 'axios'
export default {
  async DatMoiIQMS($this, data) {
    try {
      $this.loading(true);
      let req_cntt = await api.tao_req_cntt($this.axios, {vhdtb_id: data.hdtb_id}, null);
      if(req_cntt.data.error_code != "BSS-00000000")
      {
        throw (req_cntt.data.message + " - " + req_cntt.data.data);
      }
      let kq = await api.fn_datmoi_iqms($this.axios, {
        StrRequest: req_cntt.data.data.replace('\\r\\n', ''),
        StrLink: "",
        JwtToken: ""
      });
      if(kq.data.error_code == "BSS-00000000" && kq.data.data.errorCode == "0")
      {
        $this.$toast.success("Đặt mới IQMS thành công!");
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

  async ThayDoiThongTinIQMS($this, data)
  {
    try {
      $this.loading(true);
      let req_cntt = await api.tao_req_cntt($this.axios, {vhdtb_id: data.hdtb_id}, null);
      if(req_cntt.data.error_code != "BSS-00000000")
      {
        throw (req_cntt.data.message + " - " + req_cntt.data.data);
      }
      let kq = await api.fn_tdtt_iqms($this.axios, {
        StrRequest: req_cntt.data.data.replace('\\r\\n', ''),
        StrLink: "",
        JwtToken: ""
      });
      if(kq.data.error_code == "BSS-00000000" && kq.data.data.errorCode == "0")
      {
        $this.$toast.success("Thay đổi thông tin IQMS thành công!");
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
        throw kq.data.data.errCode + '-' + kq.data.data.errorMsg + ' - ' + kq.data;
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

  async GiaHanIQMS($this, data) {
    try {
      $this.loading(true);
      let req_cntt = await api.tao_req_cntt($this.axios, {vhdtb_id: data.hdtb_id}, null);
      if(req_cntt.data.error_code != "BSS-00000000")
      {
        throw (req_cntt.data.message + " - " + req_cntt.data.data);
      }
      let kq = await api.fn_giahan_iqms($this.axios, {
        StrRequest: req_cntt.data.data.replace('\\r\\n', ''),
        StrLink: "",
        JwtToken: ""
      });
      if(kq.data.error_code == "BSS-00000000" && kq.data.data.errorCode == "0")
      {
        $this.$toast.success("Gia hạn IQMS thành công!");
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
        throw kq.data.data.errCode + '-' + kq.data.data.errorMsg + ' - ' + kq.data;
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

  async ThanhLyIQMS($this, data) {
    try {
      $this.loading(true);
      let req_cntt = await api.tao_req_cntt($this.axios, {vhdtb_id: data.hdtb_id}, null);
      if(req_cntt.data.error_code != "BSS-00000000")
      {
        throw (req_cntt.data.message + " - " + req_cntt.data.data);
      }
      let kq = await api.fn_cathuy_iqms($this.axios, {
        StrRequest: req_cntt.data.data.replace('\\r\\n', ''),
        StrLink: "",
        JwtToken: ""
      });
      if(kq.data.error_code == "BSS-00000000" && kq.data.data.errorCode == "0")
      {
        $this.$toast.success("Thanh lý IQMS thành công!");
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
        throw kq.data.data.errCode + '-' + kq.data.data.errorMsg + ' - ' + kq.data;
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

  async ThayDoiSoLuongPhongIQMS($this, data) {
    try {
      $this.loading(true);
      let req_cntt = await api.tao_req_cntt($this.axios, {vhdtb_id: data.hdtb_id}, null);
      if(req_cntt.data.error_code != "BSS-00000000")
      {
        throw (req_cntt.data.message + " - " + req_cntt.data.data);
      }
      let kq = await api.fn_thaysoluong_phong_iqms($this.axios, {
        StrRequest: req_cntt.data.data.replace('\\r\\n', ''),
        StrLink: "",
        JwtToken: ""
      });
      if(kq.data.error_code == "BSS-00000000" && kq.data.data.errorCode == "0")
      {
        $this.$toast.success("Thay số lượng phòng IQMS thành công!");
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
        throw kq.data.data.errCode + '-' + kq.data.data.errorMsg + ' - ' + kq.data;
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

  async ChuyenChinhThucIQMS($this, data) {
    try {
      $this.loading(true);
      let req_cntt = await api.tao_req_cntt($this.axios, {vhdtb_id: data.hdtb_id}, null);
      if(req_cntt.data.error_code != "BSS-00000000")
      {
        throw (req_cntt.data.message + " - " + req_cntt.data.data);
      }
      let kq = await api.fn_chuyen_chinhthuc_iqms($this.axios, {
        StrRequest: req_cntt.data.data.replace('\\r\\n', ''),
        StrLink: "",
        JwtToken: ""
      });
      if(kq.data.error_code == "BSS-00000000" && kq.data.data.errorCode == "0")
      {
        $this.$toast.success("Chuyen chinh thuc IQMS thành công!");
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
        throw kq.data.data.errCode + '-' + kq.data.data.errorMsg + ' - ' + kq.data;
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
