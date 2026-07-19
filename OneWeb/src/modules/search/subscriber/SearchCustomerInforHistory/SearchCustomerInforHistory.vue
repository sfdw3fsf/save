<template src="./template.html"/>
<script>
import breadcrumb from '@/components/breadcrumb'
import '@/assets/vendor/jquery/split'
import Const from './const';
import FormSearch from "./FormSearch/FormSearch";
import {DBConstants} from "../../../../const/enums";
import frmDsKhMaKh from "./popups/frmDS_Kh_MaKh";

export default {
  name: 'SearchPaymentDebtHistory',
  components: {
    breadcrumb,
    FormSearch,
    frmDsKhMaKh
  },
  data() {
    return {
      header: {
        title: 'Tra cứu lịch sử thông tin khách hàng',
        list: [
          {name: 'Tra cứu', link: {name: 'Ui.cards'}},
          {name: 'Tra cứu trên CCBS', link: {name: 'Ui.cards'}},
          {name: 'Tra cứu lịch sử thông tin khách hàng', link: {name: 'Ui.cards'}},
        ]
      },
      cols: Const.cols,
      dsLichSu: [],
      form: {
        txtMaKh: '',
        txtTenKh: '',
        txtMaNeo: '',
        txtDiachi: ''
      },
      tinh: '',
      userInfo: {},
      dt1: [],
      dtSource: []
    }
  },
  mounted() {
    this.frmTraCuu_TT_LS_KH();
  },
  methods: {
    async btnTraCuu_Click() {
      this.clear(1);
      this.dt1 = [];
      try {
        if (this.form.txtMaKh === "" && this.form.txtTenKh === "") {
          this.$toast.error("Không được để trống Mã khách hàng hoặc Tên khách hàng");
          return;
        }

        if (this.form.txtMaKh !== "") {
          this.loading(true);
          let dt1 = await this.sp_tracuu_tt_ls_kh(this.form.txtMaKh.toString().trim(), 1);
          if (dt1.length > 0) {
            this.form.txtDiachi = dt1[0]["diachi_tt"].toString();
            this.form.txtMaNeo = dt1[0]["ma_tt_neo"].toString();
            this.form.txtTenKh = dt1[0]["ten_tt"].toString();
            if (this.form.txtMaNeo === "") {
              this.dsLichSu = await this.ts_tracuu_ls_kh(this.userInfo.user_neo, this.tinh, this.form.txtMaKh, this.form.txtTenKh);
            } else {
              this.dsLichSu = await this.ts_tracuu_ls_kh(this.userInfo.user_neo, this.tinh, this.form.txtMaNeo, this.form.txtTenKh);
            }
          }
        } else {
          await this.layds_kh(this.form.txtTenKh);
          if (this.form.txtMaKh === "" && this.form.txtMaNeo === "") {
            return;
          }

          this.loading(true);
          if (this.form.txtMaNeo !== "") {
            this.dsLichSu = await this.ts_tracuu_ls_kh(this.userInfo.user_neo, this.tinh, this.form.txtMaNeo, this.form.txtTenKh);
          } else {
            this.dsLichSu = await this.ts_tracuu_ls_kh(this.userInfo.user_neo, this.tinh, this.form.txtMaKh, this.form.txtTenKh);
          }
        }
      } catch (ex) {
        console.log(ex);
        this.$toast.error(ex);
      } finally {
        this.loading(false);
      }
    },

    async frmTraCuu_TT_LS_KH() {
      this.axios.post('quantri/user/thongtin_nguoidung2', {})
        .then(rs => {
          this.userInfo = rs.data.data;
        })

      this.tinh = await this.mapData('tentat', DBConstants.DB2 + ".tinh",
        "where tinh_id =" + this.$root.token.getPhanVungID());
    },

    clear(a) {
      if (a === 1) {
        this.dsLichSu = [];
        this.form.txtMaNeo = "";
        this.form.txtDiachi = "";
      } else {
        this.dsLichSu = [];
        this.form.txtTenKh = "";
        this.form.txtMaNeo = "";
        this.form.txtDiachi = "";
        this.form.txtMaKh = "";
      }
    },

    async sp_tracuu_tt_ls_kh(text, kieu) {
      try {
        let rs = await this.axios.post('web-danhba/tracuu-thongtin/sp_tracuu_tt_ls_kh', {
          text, kieu
        });

        return rs.data.data || [];
      } catch (e) {
        console.log(e);
      }

      return [];
    },

    async ts_tracuu_ls_kh(userid, ma_tinh, ma_kh, ten_kh) {
      try {
          let rs = await this.axios.post('ccbs/executor/ts_tracuu_ls_kh', {
            userid, ma_tinh, ma_kh, ten_kh
          });

          return rs.data.data || [];
      } catch (e) {
        console.log(e);
      }

      return [];
    },

    async layds_kh(txtTenKh) {
      let dtDs_kh = await this.sp_tracuu_tt_ls_kh(txtTenKh.toString().toLowerCase(), 2);
      if (dtDs_kh.length > 0) {
        if (dtDs_kh.length > 1) {
          let frm = this.$refs.frmDsKhMaKh;
          frm.dsMa = dtDs_kh;

          let item = await this.$refs.frmDsKhMaKh.showModal();
          if (item) {
            this.form.txtDiachi = item.diachi_tt;
            this.form.txtMaKh = item.ma_tt;
            this.form.txtMaNeo = item.ma_tt_neo;
            this.$refs.frmDsKhMaKh.hideModal();
          }
        } else if (dtDs_kh.length === 1) {
          this.form.txtDiachi = dtDs_kh[0]["diachi_tt"].toString();
          this.form.txtMaKh = dtDs_kh[0]["ma_tt"].toString();
          this.form.txtMaNeo = dtDs_kh[0]["ma_tt_neo"].toString();

        }
      }
    },

    async mapData(id_neo, table, dk) {
      try {
        let rs = await this.axios.post(`web-ccdv/khaibaotsl/map_id`, {id_neo, table, dk});
        return rs.data.data || '';
      } catch (e) {
        return '';
      }
    },
  },
}
</script>
