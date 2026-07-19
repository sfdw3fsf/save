<template src="./template.html"/>
<script>
import breadcrumb from '@/components/breadcrumb'
import '@/assets/vendor/jquery/split'
import Const from './const';
import FormSearch from "./FormSearch/FormSearch";
import {DBConstants} from "../../../../const/enums";

export default {
  name: 'SearchCustomerDebt',
  components: {
    breadcrumb,
    FormSearch
  },
  data() {
    return {
      header: {
        title: 'Tra cứu nợ của khách hàng',
        list: [
          {name: 'Tra cứu', link: {name: 'Ui.cards'}},
          {name: 'Tra cứu trên CCBS', link: {name: 'Ui.cards'}},
          {name: 'Tra cứu nợ khách hàng', link: {name: 'Ui.cards'}},
        ]
      },
      cols: Const.cols,
      dsNo: [],
      form: {
        txtSoMay: '',
        txtTenKH: '',
        txtMaKH: '',
        lueKieuNo: '',
        lueKyHD: '',
        txtDiaChiKH: '',
        lueDonVi: '',
        txtTienNoTu: '',
        lueBuuCuc: '',
        lueTuyenThu: '',
        txtTienNoDen: '',
        lueNhanVienTC: ''
      },
      vma_tinh: '',
      userInfo: {},
      page: 1
    }
  },
  mounted() {
    this.frmTraCuu_NoKH();
  },
  methods: {
    btnTraCuu_Click() {
      this.TraCuu();
    },

    async frmTraCuu_NoKH() {
      this.axios.post('quantri/user/thongtin_nguoidung2', {})
        .then(rs => {
          this.userInfo = rs.data.data;
        })

      this.vma_tinh = await this.mapData('tentat', DBConstants.DB2 + ".tinh",
        "where tinh_id =" + this.$root.token.getPhanVungID());
    },

    async TraCuu() {
      try {
        this.loading(true);
        let rs = await this.axios.post('ccbs/executor/ts_tracuu_conno', {
          "kieuno": this.form.lueKieuNo,
          "ma_tinh": this.vma_tinh,
          "ma_kh": this.form.txtMaKH,
          "kyhoadon": this.form.lueKyHD.toString().trim().replaceAll('/', ''),
          "tientu": this.form.txtTienNoTu,
          "so_tb": "84" + this.form.txtSoMay,
          "page": this.page,
          "tienden": this.form.txtTienNoDen,
          "userid": this.userInfo.user_neo
        });

        this.dsNo = rs.data.data;
      } catch (ex) {
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

    onPageChanged(event) {
      let page = event.target.value
      try {
        this.page = Number.parseInt(page);
        if (this.page <= 1) {
          this.page = 1;
        }
        this.TraCuu();
      } catch (e) {
        this.page = 1;
        console.log(e);
      }
    },

    minusPage() {
      let page = this.page;
      try {
        page = Number.parseInt(page);
        if (page > 1) {
          page -= 1;
          this.page = page;
        } else {
          return;
        }
      } catch (e) {
        this.page = 1;
        console.log(e);
      }

      this.TraCuu();
    },

    plusPage() {
      let page = this.page;
      try {
        page = Number.parseInt(page);
        page += 1;
        this.page = page;
      } catch (e) {
        this.page = 1;
        console.log(e);
      }

      this.TraCuu();
    }
  },
}
</script>
