<template>
  <div>
    <div class="list-actions-top">
      <ul class="list">
        <li @click="btnFilter_Click">
          <a> <span class="icon one-save"></span>Ghi lại</a>
        </li>
      </ul>
    </div>
    <div class="popup-body">
      <DataGrid ref="grvVT" :columns="grvVT.header" :dataSource="grvVT.list" :showFilter="true" :showColumnCheckbox="true" :enabledSelectFirstRow="false" :allowPaging="true" :enablePagingServer="false" />
    </div>
  </div>
</template>
<script>
import moment from 'moment'
import api from './api'
import DateTimeHelper from '../../helpers/dateHelper'
import { convertToUpcaseKey, isNullOrEmpty, getDateStringOrDefault } from '../../helpers'
import { FORM_CONFIG } from '../../const'

export default {
  emits: ['onBtnLaySerialClicked'],
  props: {
    kieu: {
      type: [String, Number],
      defaul: null,
      require: false
    },
    dsCT_ChungTu: {
      type: [String, Number],
      defaul: null,
      require: false
    }
  },
  data() {
    return {
      localLoading: 0,
      btnGhiLai: { visible: true, enabled: true },
      cboSMSTemp: { visible: true, enabled: true, list: [] },
      txtNoiDung: { visible: true, enabled: true, value: '' }
    }
  },
  computed: {},
  watch: {
    localLoading(val) {
      if (val > 0) this.loading(true)
      else this.loading(false)
    }
  },
  methods: {
    async callApiWrapper(action, body) {
      let result = null
      try {
        this.localLoading++
        result = await action(this.axios, body)
        return result.data.data ? result.data.data : null
      } catch (e) {
        if (e.data != null) {
          this.$toast.error(`${e.data.message}`)
        } else {
          this.$toast.error(`Có lỗi gọi API`)
        }
        return 'error'
      } finally {
        setTimeout(() => {
          this.localLoading--
        }, 500)
      }
      return null
    },
    async btnGhiLai_Click() {
      try {
        if (txtNoiDung.value.Length == 0) {
          this.$toast.warning('Bạn phải nhập nội dung tiến trình')
          return
        }

        if (tag == '') {
          //   if (vtientrinhsc_id == null)
          //   {
          //       tdan.EXECUTE_SQL(
          //           @"INSERT INTO {?DB13}.tientrinh_sc (tientrinhsc_id, suco_id, donvi_id, nhanvien_id, noidung, ngay_cn, nguoi_cn, may_cn, ip_cn)
          //       VALUES ({?DB13}.get_keys('SC_TIENTRINHSC_ID'), :vsuco_id, :vdonvi_id, :vnhanvien_id, :vnoidung, sysdate, :vnguoi_cn, :vmay_cn, :vip_cn)",
          //           "vsuco_id", vsuco_id,
          //           "vdonvi_id", ttnd.donvi_id,
          //           "vnhanvien_id", ttnd.nhanvien_id,
          //           "vnoidung", txtNoiDung.value,
          //           "vnguoi_cn", ttnd.ma_nd,
          //           "vmay_cn", ttnd.may_cn,
          //           "vip_cn", ttnd.ip
          //       );
          //   }
          //   else
          //   {
          //       tdan.EXECUTE_SQL(
          //           @"update {?DB13}.tientrinh_sc set noidung = :vnoidung, donvi_id = :vdonvi_id,
          //       nhanvien_id = :vnhanvien_id, ngay_cn = sysdate, nguoi_cn = :vnguoi_cn,
          //       may_cn = :vmay_cn, ip_cn = :vip_cn
          //       where tientrinhsc_id = :vtientrinhsc_id",
          //           "vnoidung", txtNoiDung.value,
          //           "vdonvi_id", ttnd.donvi_id,
          //           "vnhanvien_id", ttnd.nhanvien_id,
          //           "vnguoi_cn", ttnd.ma_nd,
          //           "vmay_cn", ttnd.may_cn,
          //           "vip_cn", ttnd.ip,
          //           "vtientrinhsc_id", vtientrinhsc_id
          //       );
          //   }
        } else if (tag == '1') {
          //   BusinessFacade.CheckDataFacade checkdata = new BusinessFacade.CheckDataFacade();
          //   let donvi_id = -1;
          //   let nhanvien_id = -1;
          //   if (vtientrinhbh_id == null)
          //   {
          //       var str = "";
          //       if (vtinh.tinhthicong_id == 0)
          //           str += " INSERT INTO " + DatabaseConstants.DB7 + ".tientrinh_bh (tientrinhbh_id, baohong_id, donvi_id, nhanvien_id, nguoidung_id, noidung, ngay_cn, nguoi_cn, ip_cn)";
          //       else
          //           str += " INSERT INTO BaoHong_" + vtinh.matinh + ".tientrinh_bh (tientrinhbh_id, baohong_id, donvi_id, nhanvien_id, nguoidung_id, noidung, ngay_cn, nguoi_cn, ip_cn)";
          //       str += " VALUES (";
          //       if (vtinh.tinhthicong_id == 0)
          //           str += DatabaseConstants.DB1 + ".get_keys('TIENTRINH_BH'), ";
          //       else
          //           str += "Admin_" + vtinh.matinh + ".get_keys('TIENTRINH_BH'), ";
          //       str += ":vbaohong_id,";
          //       str += ":vdonvi_id, ";
          //       str += ":vnhanvien_id, ";
          //       str += ":vnguoidung_id, ";
          //       str += ":vnoidung, ";
          //       str += "sysdate, ";
          //       str += ":vma_nd, ";
          //       str += ":vip";
          //       str += ")";
          //       if (vtinh.tinhthicong_id == 0)
          //           tdan.EXECUTE_SQL(str, "vbaohong_id", vbaohong_id,
          //                                "vdonvi_id", ttnd.donvi_id,
          //                                "vnhanvien_id", ttnd.nhanvien_id,
          //                                "vnguoidung_id", ttnd.nguoidung_id,
          //                                "vnoidung", txtNoiDung.value,
          //                                "vma_nd", ttnd.ma_nd,
          //                                "vip", ttnd.ip);
          //       else
          //       {
          //           System.Data.DataTable dt = bancheo.GET_DATA_OBJECT_PL(vtinh, "{?DB2}.SP_TT_NGUOIDUNG", "vreturnds", "vma_nd", ttnd.ma_nd);
          //               //bancheo.GET_DATA_SQL(vtinh, "select * from admin_" + vtinh.matinh + ".nguoidung where ma_nd='" + ttnd.ma_nd + "'");
          //           let nguoidung_id = -1;
          //           if (dt.Rows.Count > 0)
          //           {
          //               let.TryParse(dt.Rows[0]["nhanvien_id"], out nhanvien_id);
          //               let.TryParse(dt.Rows[0]["nguoidung_id"], out nguoidung_id);
          //               dt = bancheo.GET_DATA_OBJECT_PL(vtinh, "{?DB2}.SP_TT_NHANVIEN_2", "vreturnds", "vnhanvien_id", dt.Rows[0]["nhanvien_id"]);
          //                   //bancheo.GET_DATA_SQL(vtinh, "select * from admin_" + vtinh.matinh + ".nhanvien where nhanvien_id=" + dt.Rows[0]["nhanvien_id"]);
          //               if (dt.Rows.Count > 0)
          //                   let.TryParse(dt.Rows[0]["donvi_id"], out donvi_id);
          //           }
          //           //else
          //           //{
          //           //    this.$toast.warning("Không tồn tại user " + ttnd.ma_nd + " tại tỉnh thi công!");
          //           //    return;
          //           //}
          //           //long baohong_id_tc = Convert.ToInt32(checkdata.MAP_ID("baohong_id_thicong", DatabaseConstants.DB7 + ".baohong_bc", "where baohong_id=" + vbaohong_id + " and LOAIHD_BANCHEO=1"));
          //           //bancheo.EXECUTE_SQL(vtinh, str, "vbaohong_id", vbaohong_id,
          //           //                     "vdonvi_id", donvi_id,
          //           //                     "vnhanvien_id", nhanvien_id,
          //           //                     "vnguoidung_id", nguoidung_id,
          //           //                     "vnoidung", txtNoiDung.value,
          //           //                     "vma_nd", ttnd.ma_nd,
          //           //                     "vip", ttnd.ip);
          //           bancheo.EXECUTE_NON_QUERY(vtinh, "{?DB7}.SP_INSERT_TIENTRINH_BH", "vbaohong_id", vbaohong_id, "vdonvi_id", donvi_id, "vnhanvien_id", nhanvien_id,
          //                                                                             "vnguoidung_id", nguoidung_id, "vnoidung", txtNoiDung.value, "vma_nd", ttnd.ma_nd,
          //                                                                             "vip", ttnd.ip);
          //       }
          //       str = "";
          //       if (vtinh.tinhthicong_id == 0)
          //       {
          //           str += " UPDATE " + DatabaseConstants.DB7 + ".baohong";
          //           str += " SET tientrinhbh_id = (select max(tientrinhbh_id) from " + DatabaseConstants.DB7 + ".tientrinh_bh where baohong_id = " + vbaohong_id + ")";
          //       }
          //       else
          //       {
          //           str += " UPDATE BaoHong_" + vtinh.matinh + ".baohong";
          //           str += " SET tientrinhbh_id = (select max(tientrinhbh_id) from baohong_" + vtinh.matinh + ".tientrinh_bh where baohong_id = " + vbaohong_id + ")";
          //       }
          //       str += " WHERE baohong_id = " + vbaohong_id;
          //       if (vtinh.tinhthicong_id == 0)
          //           tdan.EXECUTE_SQL(str);
          //       else
          //           bancheo.EXECUTE_NON_QUERY(vtinh, "{?DB7}.SP_UPDATE_TIENTRINHBH_ID", "vbaohong_id", vbaohong_id);
          //           //bancheo.EXECUTE_SQL(vtinh, str);
          //   }
          //   else
          //   {
          //       var str = "";
          //       if (vtinh.tinhthicong_id == 0)
          //       {
          //           str += " UPDATE " + DatabaseConstants.DB7 + ".tientrinh_bh";
          //           donvi_id = ttnd.donvi_id;
          //       }
          //       else
          //       {
          //           System.Data.DataTable dt = bancheo.GET_DATA_OBJECT_PL(vtinh, "{?DB2}.SP_TT_NGUOIDUNG", "vreturnds", "vma_nd", ttnd.ma_nd);
          //               //bancheo.GET_DATA_SQL(vtinh, "select * from admin_" + vtinh.matinh + ".nguoidung where ma_nd='" + ttnd.ma_nd + "'");
          //           if (dt.Rows.Count > 0)
          //           {
          //               let.TryParse(dt.Rows[0]["nhanvien_id"], out nhanvien_id);
          //               dt = bancheo.GET_DATA_OBJECT_PL(vtinh, "{?DB2}.SP_TT_NHANVIEN_2", "vreturnds", "vnhanvien_id", nhanvien_id);
          //               if (dt.Rows.Count > 0)
          //                   let.TryParse(dt.Rows[0]["donvi_id"], out donvi_id);
          //           }
          //           //else
          //           //{
          //           //    this.$toast.warning("Không tồn tại user " + ttnd.ma_nd + " tại tỉnh thi công!");
          //           //    return;
          //           //}
          //           str += " UPDATE BaoHong_" + vtinh.matinh + ".tientrinh_bh";
          //       }
          //       str += " SET";
          //       str += " noidung = :vnoidung, ";
          //       str += " donvi_id = " + donvi_id + ", ";
          //       str += " nhanvien_id = " + ttnd.nhanvien_id + ", ";
          //       str += " nguoidung_id = " + ttnd.nguoidung_id + ", ";
          //       str += " ngay_cn = sysdate, ";
          //       str += " nguoi_cn = " + ttnd.ma_nd + ", ";
          //       str += " ip_cn = " + ttnd.ip;
          //       str += " WHERE tientrinhbh_id = " + vtientrinhbh_id;
          //       if (vtinh.tinhthicong_id == 0)
          //           tdan.EXECUTE_SQL(str);
          //       else
          //           bancheo.EXECUTE_NON_QUERY(vtinh, "{?DB7}.SP_UPDATE_TIENTRINH_BH", "vtientrinhbh_id", vtientrinhbh_id);
          //           //bancheo.EXECUTE_SQL(vtinh, str);
          //   }
        }

        DialogResult = DialogResult.OK
        Close()
      } catch (ex) {
        this.$error(`${ex}`)
      }
    },
    async frmNoiDungTT_Load() {
      if (tag == '' || tag == '2') {
        lbltxtNoiDung.Visibility = LayoutVisibility.Never
      } else if (tag == '1') {
        lbltxtNoiDung.Visibility = LayoutVisibility.Always
        var vdt_dvi_giao = tdan.GET_DATA_SQL(
          " select -1 id ,'' sms from dual union all " +
            " select 1 id,'KHDN cần sửa trước...' sms from dual union all " +
            " select 2 id,'KH đang chờ CNVT đến sửa chữa, đề nghị đơn vị sửa chữa ngay, liên hệ anh/chị...SDT...' sms from dual union all " +
            " select 3 id,'VNPT HN kính báo: KTV đến sửa chữa dịch vụ... Thuê bao khóa cửa. Vui lòng liên hệ lại KTV………….khi có nhà để được hỗ trợ. Trân trọng!' sms from dual union all" +
            " select 4 id,'VNPT HN kính báo: KTV đã sửa chữa bên ngoài, Thuê bao khóa cửa. Vui lòng kiểm tra lại tín hiệu. LH KTV ...để được hỗ trợ thêm. Trân trọng!' sms from dual union all " +
            " select 5 id,'VNPT HN kính báo: Dịch vụ báo hỏng của TB hiện đang mất tín hiệu do điện lực tiến hành thành thải, chỉnh trang tuyến phố. VNPT HN sẽ cung cấp tín hiệu trở lại ngay sau khi ĐL bàn giao mặt bằng. Vui lòng LH KTV... để biết thêm chi tiết. Trân trọng!' sms from dual union all " +
            " select 6 id,'VNPT HN kính báo: Dịch vụ báo hỏng của TB hiện đang mất tín hiệu do sự cố...VNPT HN đang tiến hành xử lý, sẽ cung cấp tín hiệu sớm nhất, mong Quý KH thông cảm. Vui lòng LH KTV... để được hỗ trợ. Trân trọng!' sms from dual union all " +
            " select 7 id,'Nội dung khác' sms from dual "
        )
        bts.HT_DS_GRIDLOOKUP(cboSMSTemp, vdt_dvi_giao, 'SMS', 'ID', true, 1, 400)
        cboSMSTemp.Properties.View.OptionsView.RowAutoHeight = true
        t.WordWrap = true
        cboSMSTemp.Properties.View.Columns[1].ColumnEdit = t
      }
    },
    async cboSMSTemp_EditValueChanged() {
      if (vtientrinhbh_id != null) return
      if (cboSMSTemp.value == '-1') txtNoiDung.value = cboSMSTemp.value + ' ' + vnoidung_goc
      else txtNoiDung.value = cboSMSTemp.value + ' ' + vnd_baohong
    }
  },
  async mounted() {
    await this.frmNoiDungTT_Load()
  }
}
</script>
<style scoped></style>
