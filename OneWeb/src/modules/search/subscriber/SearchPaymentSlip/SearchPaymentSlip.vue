<template src="./template.html"/>
<script>
import breadcrumb from '@/components/breadcrumb'
import '@/assets/vendor/jquery/split'
import Const from './const';
import ThuHo from "./FormSearch/ThuHo";
import NhoThuHo from "./FormSearch/NhoThuHo";
import moment from "moment";

export default {
  name: 'SearchPaymentSlip',
  components: {
    breadcrumb,
    ThuHo,
    NhoThuHo
  },
  data() {
    return {
      header: {
        title: 'Tra cứu phiếu thanh toán',
        list: [
          {name: 'Tra cứu', link: {name: 'Ui.cards'}},
          {name: 'Tra cứu trên CCBS', link: {name: 'Ui.cards'}},
          {name: 'Tra cứu phiếu thanh toán', link: {name: 'Ui.cards'}},
        ]
      },
      cols: Const.cols,
      dsThuHo: [],
      dsNhoThuHo: [],
      dsKyHoaDon: [],
      dsTT: [],
      dsTinh: [],
      dsThuNgan: [],
      dsQuayThu: [],
      dsBuuCuc: [],
      tabThuHo: 1,
      tabNhoThuHo: 2,
      tabActive: 1,
      formThuHo: {
        dateFrom: moment().format('DD/MM/YYYY'),
        dateTo: moment().format('DD/MM/YYYY'),
        kyHD: "",
        tt: "",
        tinh: "",
        thungan: '',
        quaythu: '',
        buucuc: ''
      },
      formNhoThuHo: {
        dateFrom: moment().format('DD/MM/YYYY'),
        dateTo: moment().format('DD/MM/YYYY'),
        kyHD: "",
        tt: "",
        tinh: "",
      }
    }
  },
  mounted() {
    this.loadData();
  },
  methods: {
    btnTraCuu_Click() {

    },

    setActiveTab(tab) {
      this.tabActive = tab;
    },

    async loadData() {
      let rs;

      try {
        rs = await this.axios.post('/ccbs/danhMuc/ts_danhmuc_tracuu_phieu_thuho_tt');
        this.dsTT = rs.data.data;
        this.formThuHo.tt = this.formNhoThuHo.tt = this.dsTT[0].ID;
      } catch (e) {
        console.log(e);
      }

      try {
        rs = await this.axios.post('/ccbs/executor/ts_dm_dstinh');
        this.dsTinh = rs.data.data
        this.formThuHo.tinh = this.formNhoThuHo.tinh = this.dsTinh[0].AGENT_ID;
      } catch (e) {
        console.log(e);
      }

      try {
        rs = await this.axios.post('/ccbs/executor/tinh_ds_nhanvien_tcs', {"page_num": "1", "page_rec": "100"})
        this.dsThuNgan = rs.data.data
        this.formThuHo.thungan = this.dsThuNgan[0].MA_NV
      } catch (e) {
        console.log(e);
      }

      try {
        rs = await this.axios.post('/ccbs/executor/tinh_quaythus');
        this.dsQuayThu = rs.data.data
        this.formThuHo.quaythu = this.dsQuayThu[0].MAQUAY
      } catch (e) {
        console.log(e);
      }

      try {
        rs = await this.axios.post('ccbs/executor/tinh_buucucthus');
        this.dsBuuCuc = rs.data.data;
        this.formThuHo.buucuc = this.dsBuuCuc[0].MA_BC
      } catch (e) {
        console.log(e);
      }
    }
  },
}
</script>
