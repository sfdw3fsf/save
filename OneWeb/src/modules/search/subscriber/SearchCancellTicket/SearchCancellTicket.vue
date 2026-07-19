<template src="./template.html"/>
<script>
import breadcrumb from '@/components/breadcrumb'
import '@/assets/vendor/jquery/split'
import Const from './const';
import FormSearch from "./FormSearch/FormSearch";
import moment from "moment";
import {DBConstants} from "../../../../const/enums";
import FormNeo from "./FormNeo/FormNeo";
import SearchAccount from '@/modules/search/subscriber/SearchAccount'

export default {
  name: 'SearchCancellTicket',
  components: {
    breadcrumb,
    FormSearch,
    FormNeo,
    SearchAccount
  },
  data() {
    return {
      header: {
        title: 'Tra cứu phiếu hủy',
        list: [
          {name: 'Tra cứu', link: {name: 'Ui.cards'}},
          {name: 'Tra cứu trên CCBS', link: {name: 'Ui.cards'}},
          {name: 'Tra cứu phiếu hủy', link: {name: 'Ui.cards'}},
        ]
      },
      cols: Const.cols,
      dsPhieuHuy: [],
      dsNeo: [],
      form: {
        chk: 2,
        dateFrom: moment().format('DD/MM/YYYY'),
        dateTo: moment().format('DD/MM/YYYY'),
        maKH: '',
        maTB: '',
        kyHD: 0,
        seri: ''
      },
      chukygoc: '',
      tinh: '',
      userInfo: {},
      ma_neo_vnp: '',
      ma_tt: '',
      ma_kh: '',
    }
  },
  mounted() {
    this.axios.post('quantri/user/thongtin_nguoidung2', {})
      .then(rs => {
        this.userInfo = rs.data.data;
      })
  },
  methods: {
    async btnTraCuu_Click() {
      try {
        this.loading(true);
        this.chukygoc = this.getKyHDDDMMYYYY();
        let rs = await this.axios.post('/web-hopdong/goi-da-dichvu/fn_map_id', {
          id_neo: "tentat",
          table: DBConstants.DB2 + ".tinh",
          dk: "where tinh_id =" + this.$root.token.getPhanVungID()
        });
        this.tinh = rs.data.data;

        if (this.form.chk == 2) {//rdbSoMay.Checked
          await this.TracuuSoTb();
        } else if (this.form.chk == 1) {//rdbMaTT.Checked
          await this.TracuuMaTT();
        }
        this.loading(false);
      } catch (ex) {
        this.$root.$toast.error("Có lỗi xảy ra trong quá trình kết nối với Sercvice VinaPhone!");
        console.log(ex);
      }
    },

    dataBound() {
      this.$refs.dsPhieuHuy.grid.autoFitColumns();
    },

    clear() {
      // this.form = {
      //   ...this.form,
      //   maTB: '',
      //   maKH: '',
      //   seri: '',
      // };

      this.dsPhieuHuy = [];
    },

    async TracuuSoTb() {
      this.dsPhieuHuy = await this.CCBS_ts_tracuu_phieuhuy(
        this.form.dateFrom,
        this.form.dateTo,
        this.tinh,
        "",
        "84" + this.form.maTB,
        this.form.seri,
        this.getKyHDMMYYYY(),
        this.userInfo.user_neo
      );

      if (this.dsPhieuHuy.length <= 0) {
        this.clear();
      }
    },

    async TracuuMaTT() {
      this.ma_neo_vnp = "";
      await this.NAP_DS_THUEBAO_THEOMTT(this.form.maKH, this.chukygoc);
      if (this.ma_neo_vnp === "") {
        this.clear();
        return;
      } else
        this.dsPhieuHuy = await this.CCBS_ts_tracuu_phieuhuy(
          this.form.dateFrom,
          this.form.dateTo,
          this.tinh,
          this.ma_neo_vnp,
          "",
          this.form.seri,
          this.getKyHDMMYYYY(),
          this.userInfo.user_neo
        );
      if (this.dsPhieuHuy.length <= 0) {
        this.clear();
      }
    },

    async CCBS_ts_tracuu_phieuhuy(tungay, denngay, ma_tinh, ma_kh, so_tb, soseri, chukyno, userid) {
      try {
        let rs = await this.axios.post('ccbs/executor/ts_tracuu_phieuhuy', {
          tungay,
          denngay,
          ma_tinh,
          ma_kh,
          so_tb,
          soseri,
          chukyno,
          userid
        });

        return rs.data.data != null ? rs.data.data : [];
      } catch (e) {
        console.log(e);
      }

      return [];
    },

    async NAP_DS_THUEBAO_THEOMTT(maKH, chukygoc) {
      try {
        console.log("Đang tìm kiếm mã thanh toán ...", "...");

        let rs = await this.axios.post('web-ccdv/capnhat_loai_thietbi/lay_matt_neo_vnp', {
          vma: maKH,
          vchuky: chukygoc
        });

        let dsNeo = rs.data.data.chitiet;
        this.form.maKH = "";
        this.ma_tt = "";
        this.ma_kh = "";

        if (dsNeo != null) {
          this.dsNeo = null;
          if (dsNeo.length === 0) {
            this.$root.$toast.error("Không tìm thấy mã thanh toán này");
            return;
          }

          if (dsNeo.length === 1) {
            this.ma_neo_vnp = dsNeo[0].ma_tt_neo;
            this.ma_tt = dsNeo[0].ma_tt;
            this.form.maKH = this.ma_tt;
          } else if (dsNeo.length > 1) {
            this.dsNeo = [...dsNeo];
            this.showNeo();
          }
        }
      } catch (ex) {
        this.$root.$toast.error("Lỗi khi tìm kiếm mã thanh toán");
        console.log(ex);
      }
    },

    getKyHDDDMMYYYY() {
      let {kyHD} = this.form;
      if (kyHD !== 0) {
        try {
          return kyHD.toString().substr(6, 2) +
            kyHD.toString().substr(4, 2) +
            kyHD.toString().substr(0, 4);
        } catch (e) {
          console.log(e);
        }
      }

      return '0';
    },

    getKyHDMMYYYY() {
      let {kyHD} = this.form;
      if (kyHD !== 0) {
        try {
          return kyHD.toString().substr(4, 2) +
            kyHD.toString().substr(0, 4);
        } catch (e) {
          console.log(e);
        }
      }

      return '0';
    },

    showNeo() {
      this.$refs.popupNeo.showModal();
    },

    chon(item) {
      if (item != null) {
        console.log(item);

        try {
          this.loading(true);
          let ma_khang = item.ma_tt;
          let ma_tt_neo = item.ma_tt_neo;
          this.form.maKH = ma_khang;
          this.dsPhieuHuy = this.CCBS_ts_tracuu_phieuhuy(
            this.form.dateFrom,
            this.form.dateTo,
            this.tinh,
            ma_tt_neo,
            "",
            this.form.seri,
            this.getKyHDMMYYYY(),
            this.userInfo.user_neo
          );

          this.loading(false);
        } catch (ex) {
          this.$root.$toast.error('Có lỗi xảy ra');
          console.log(ex);
        } finally {
          this.loading(false);
        }
      }
    },

    showFormTraCuuDanhBa() {
      this.$refs['popupSearchAccount'].show()
    },

    acceptSearchAccount(item) {
      if (this.form.chk == 2) {
        this.form.maTB = item.ma_tb;
      } else if (this.form.chk == 1) {
        this.form.maKH = item.ma_kh;
      }

      this.$refs['popupSearchAccount'].hide();
      this.btnTraCuu_Click();
    },
  },
}
</script>
<style scoped>
/deep/ .modal-body .page-content {
  position: inherit;
}
</style>
