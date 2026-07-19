<template src="./template.html"/>
<script>
import breadcrumb from '@/components/breadcrumb'
import '@/assets/vendor/jquery/split'
import Const from './const';
import FormSearch from "./FormSearch/FormSearch";
import moment from "moment";
import API from "../../../complaint/ReceiveCustomerFeedback/API";
import {DBConstants} from "../../../../const/enums";

export default {
  name: 'SearchPaymentDebtHistory',
  components: {
    breadcrumb,
    FormSearch
  },
  data() {
    return {
      header: {
        title: 'Tra cứu lịch sử nợ thanh toán',
        list: [
          {name: 'Tra cứu', link: {name: 'Ui.cards'}},
          {name: 'Tra cứu trên CCBS', link: {name: 'Ui.cards'}},
          {name: 'Tra cứu lịch sử nợ thanh toán', link: {name: 'Ui.cards'}},
        ]
      },
      cols: Const.cols,
      dsLichSu: [],
      tttb: [],
      form: {
        lueTuKyHD: 0,
        lueDenKyHD: 0,
        txtTuKy: '',
        txtDenKy: '',
        txtMaKH: '',
        txtSoMay: '',
        txtTenKH: '',
        txtDiaChiKH: '',
      },
      vma_tinh: '',
      userInfo: {}
    }
  },
  created() {
    window.addEventListener('keydown', (e) => {
      if (e.key === 'F3') {
        this.btnTraCuu_Click();
      }
    });
  },
  async mounted() {
    this.axios.post('quantri/user/thongtin_nguoidung2', {})
      .then(rs => {
        this.userInfo = rs.data.data;
      })

    this.vma_tinh = await this.mapData('tentat', DBConstants.DB2 + ".tinh",
      "where tinh_id =" + this.$root.token.getPhanVungID());
  },
  methods: {
    dataBound() {
      this.$refs.dsLichSu.grid.autoFitColumns();
    },

    btnTraCuu_Click() {
      let ktra = this.GioiHanTraCuu();
      if (ktra === 0) {
        this.$toast.error("Chỉ tra cứu trong khoảng 6 tháng trở lại !!!");
        return;
      }
      if (this.form.txtSoMay.toString().trim() === "") {
        this.$toast.error("Số máy không được bỏ trống !!!");
        return;
      }

      this.TraCuu();
    },

    GioiHanTraCuu() {
      let ngay_bd = moment(this.form.txtTuKy, 'DD/MM/YYYY').format('YYYYMMDD');
      let ngay_kt = moment(this.form.txtDenKy, 'DD/MM/YYYY').subtract(6, 'months').format('YYYYMMDD');
      if (Number(ngay_kt) <= Number(ngay_bd)) {
        return 1;
      } else {
        return 0;
      }
    },
    async TraCuu() {
      try {
        this.loading(true);
        let rs;
        if (this.form.txtSoMay.toString().length > 0)
          rs = await this.axios.post('ccbs/executor/ts_tracuu_ls_no_tt_tra', {
              "ma_tinh": this.vma_tinh,
              "ma_kh": this.form.txtMaKH,
              "so_tb": "84" + this.form.txtSoMay,
              "tu_kyhoadon": this.form.lueTuKyHD.toString(),
              "den_kyhoadon": this.form.lueDenKyHD.toString(),
              "userid": this.userInfo.user_neo
            }
          )
        else
          rs = await this.axios.post('ccbs/executor/ts_tracuu_ls_no_tt_tra', {
              "ma_tinh": this.vma_tinh,
              "ma_kh": this.form.txtMaKH,
              "so_tb": '',
              "tu_kyhoadon": this.form.lueTuKyHD,
              "den_kyhoadon": this.form.lueDenKyHD,
              "userid": this.userInfo.user_neo
            }
          )

        this.dsLichSu = rs.data.data;
        if (this.dsLichSu && this.dsLichSu.length > 0) {
          rs = await this.axios.post('ccbs/executor/ts_tracuu_laytt_tb_ts', {
            "ma_tinh": this.vma_tinh,
            "so_tb": this.dsLichSu[0].MA_TB
          });

          this.tttb = rs.data.data;
        } else
          this.tttb = null;

        if (this.tttb && this.tttb.length > 0) {
          this.form.txtMaKH = this.dsLichSu[0].MA_KH;
          this.form.txtTenKH = this.tttb[0].TEN_TB;
          this.form.txtDiaChiKH = this.tttb[0].DIACHI;
        } else {
          this.form.txtTenKH = "";
          this.form.txtDiaChiKH = "";
        }
      } catch (ex) {
        console.log(ex);
        this.$toast.error(ex);
      } finally {
        this.loading(false);
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
