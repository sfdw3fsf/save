import moment from "moment";
import api from './CompleteProfileAPI'
import {YEUCAU_LAY_DULIEU_TOKEN, YEUCAU_DUYET_CMS, CA_CHOXULY, GUI_FILE_HOSO_CMS} from './ca_objects'
import {LOAI_HOPDONG} from '@/constants'
import axios from 'axios'
export default{
    async DatMoiHKD($this, data) {
      console.log(data);
      let req_hkd = await api.fn_tao_req_cntt($this.axios, { vhdtb_id: data.hdtb_id });
      console.log(req_hkd.data);
      console.log(req_hkd.data.data.replace('\\r\\n', ''));
      let kq = await api.fn_datmoi_hkd($this.axios, {
        StrRequest: req_hkd.data.data.replace('\\r\\n', ''),
        StrLink: "",
        JwtToken: ""
      });
      console.log(kq);

    },
    async GiaHanHKD($this,data)
    {
      try {
        $this.loading(true);
        let req_hkd = await api.fn_tao_req_cntt($this.axios, { vhdtb_id: data.hdtb_id });
        if(req_hkd.data.error_code != "BSS-00000000")
        {
          throw (req_hkd.data.message + " - " + req_hkd.data.data);
        }
        let kq = await api.fn_giahan_hkd($this.axios, {
          StrRequest: req_hkd.data.data.replace('\\r\\n', ''),
          StrLink: "",
          JwtToken: ""
        });

        if(kq.data.error_code == "BSS-00000000" && kq.data.data.errCode.replace('-','') == "200")
        {
          $this.$toast.success(kq.data.data.errMsg);
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
       // $this.loading(false);
      }
      catch (ex)
      {
         // $this.loading(false);
          throw ex;
      }
      finally {
        $this.loading(false);
      }
    },
    async ThayDoiGoiCuocHKD($this, data)
    {
      try {
        $this.loading(true);
        let req_hkd = await api.fn_tao_req_cntt($this.axios, { vhdtb_id: data.hdtb_id });
        if(req_hkd.data.error_code != "BSS-00000000")
        {
          throw (req_hkd.data.message + " - " + req_hkd.data.data);
        }
        let req_cle = req_hkd.data.data.replace('\\r\\n', '');
        console.log(req_cle);
        let kq = await api.fn_nanggoi_hkd($this.axios, {
          StrRequest: req_cle,
          StrLink: "",
          JwtToken: ""
        });
        console.log(kq);
        //console.log(req_hkd.data.data.replace('\\r\\n', ''))
        if(kq.data.error_code == "BSS-00000000" && (kq.data.data.errCode.replace('-','')) == "200")
        {
          $this.$toast.success('Doi goi HKD thanh cong');
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
          throw ex;
      }
      finally {
        $this.loading(false);
      }
    },
    async DongBoHKD($this,data)
    {
      try {
        $this.loading(true);
        let req_hkd = await api.fn_tao_req_cntt($this.axios, { vhdtb_id: data.hdtb_id });
        let mst = "0";
        if(req_hkd.data.error_code == "BSS-00000000")
        {
          mst = JSON.parse(req_hkd.data.data).ThongTinKhachHang.MST;
        }
        if(mst.length < 4)
        {
          throw 'Không lấy được mã sô thuế!';
          return;
        }
        let thongtin_hkd = await api.lay_tt_thuebao_hkd($this.axios, {mst: mst}, null);
        let vtocdo_id = 0;
        if(thongtin_hkd.data.error_code == "BSS-00000000")
        {
          vtocdo_id = thongtin_hkd.data.data[0].dsGoiDichVu.find(x => x.tinhTrangSuDung == 1 && x.goiDichVuId > 16900).goiDichVuId;
          let kq_db = await api.fn_dongbo_hkd($this.axios, {vhdtb_id : data.hdtb_id, vtocdo_id: vtocdo_id});
          if(kq_db.data.error_code == "BSS-00000000") {
            $this.$toast.success("Đồng bộ thành công!");
          }
        }

      }
      catch (ex)
      {
        throw ex;
      }
      finally {
        $this.loading(false);
      }
    },
    async TangSoLuongHoaDonHKD($this,data)
    {
      try {
        $this.loading(true);
        let req_hkd = await api.fn_tao_req_cntt($this.axios, { vhdtb_id: data.hdtb_id });
        if(req_hkd.data.error_code != "BSS-00000000")
        {
          throw (req_hkd.data.message + " - " + req_hkd.data.data);
        }
        let kq = await api.fn_tang_sl_hoadon($this.axios, {
          StrRequest: req_hkd.data.data.replace('\\r\\n', ''),
          StrLink: "",
          JwtToken: ""
        });
        if(kq.data.error_code == "BSS-00000000" && kq.data.data.errCode.replace('-','') == "200")
        {
          let kq_status =  await api.cap_nhat_status_hdtb($this.axios, {
            vhdtb_id: data.hdtb_id,
            vstatus: 6
          });
          $this.$toast.success(kq.data.data.errMsg);
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
        throw ex;
      }
      finally {
        $this.loading(false);
      }
    }
}
