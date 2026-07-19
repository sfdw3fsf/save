<template src="./SearchGeneral_CCBS.html"></template>
<style scoped src="./SearchGeneral_CCBS.scss"></style>

<script>
import breadcrumb from "@/components/breadcrumb";
import VueFlatPickr from "vue-flatpickr-component";
import API from "./api";
import gridview from "@/components/Shared/gridview";
import DataGridNV from "./components/DataGrid";

//CSS
import "@/assets/vendor/bootstrap-table/bootstrap-table.min.css";
import "@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.css";
//JS
import "@/assets/vendor/jquery/split";
import "@/assets/vendor/bootstrap-table/bootstrap-table.min";
import "@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.min";

// Đổi tên do trùng với module khác
export default {
  components: { breadcrumb, VueFlatPickr, gridview, API,DataGridNV},
  name: "SearchGeneral_CCBS",
  created() { 
  },

  mounted() { 
    this.init();
    this.$nextTick(() => this.$refs.focus.focus());
  },

  watch: {
  },

  data() {
    return {
      header: {
        title: 'TRA CỨU TỔNG HỢP',
        list: [
          { name: 'Tra cứu', link: { name: 'Ui.cards' } },
          {
            name: 'Tra cứu tổng hợp',
            link: { name: 'Ui.buttons' }
          }
        ]
      },

      loai_tk_id: 0,
      trangthai_id: 0,
      ruiro_id: 0,
      check_hd: true,
      txtDiaChiCQ : "",
      txtDiaChiTB : "",
      txtMaCQ : "",
      txtMaKH : "",
      txtSoGIayTo : "",
      txtSoSim : "",
      txtTenCQ : "",
      txtTenTB : "",
      txtTenTT : "",
      txtSoMay : "",
      ds_tracuu_tonghop: {
        header: [
          {
            headerText: "STT",
            fieldName: "ROW_STT",
            allowSorting: false,
            width: "30",
            textAlign: "center",
            autoFit: "true",
            allowFiltering: true,
          },
          {
            headerText: "Mã KH",
            fieldName: "MA_KH",
            allowSorting: false,
            width: "60",
            textAlign: "left",
            autoFit: "true",
            allowFiltering: true,
          },
          {
            headerText: "Số máy",
            fieldName: "MA_TB",
            allowSorting: false,
            width: "60",
            textAlign: "left",
            autoFit: "true",
            allowFiltering: true,
          },
          {
            headerText: "Tên thanh toán",
            fieldName: "TEN_TT",
            allowSorting: false,
            width: "60",
            textAlign: "left",
            autoFit: "true",
            allowFiltering: true,
          },
          {
            headerText: "Tên thuê bao",
            fieldName: "TEN_TB",
            allowSorting: false,
            width: "60",
            textAlign: "left",
            autoFit: "true",
            allowFiltering: true,
          },
          {
            headerText: "Địa chỉ thuê bao",
            fieldName: "DIACHITB",
            allowSorting: false,
            width: "130",
            textAlign: "left",
            autoFit: "true",
            allowFiltering: true,
          },
          {
            headerText: "Trạng thái",
            fieldName: "TRANG_THAI",
            allowSorting: false,
            width: "80",
            textAlign: "left",
            autoFit: "true",
            allowFiltering: true,
          },
        ],
        list: [],
      },
      options: {
        cbx_hopdong_danhba: [],
        cbx_ruiro: [],
        cbx_trangthai: []
      },
    };
  },

  methods: 
  {
    async init() {
      this.loading(true);
      var response = await API.ts_tracuu_tonghop_hd_db(this.axios);
        if (
          response.data.error_code == "BSS-00000000" &&
          response.data.data && response.data.data.length > 0
        ) {
              this.loading(false);
              var data_tk = response.data.data
              this.options.cbx_hopdong_danhba = data_tk;
              this.loai_tk_id = data_tk[0].id;
              this.select_timtheo()
        } else {
          this.loading(false);
          this.$toast.error("Có lỗi khi lấy danh mục từ CCBS, liên hệ admin để xử lý!");
          return;
        }

      var response2 = await API.ts_tracuu_tonghop_kh(this.axios);
        if (
          response2.data.error_code == "BSS-00000000" &&
          response2.data.data && response2.data.data.length > 0
        ) {
              this.loading(false);
              var data_rr = response2.data.data
                data_rr.push({
                  id: 0,
                  name: "Không chọn",
                });
              this.options.cbx_ruiro = data_rr;
        } else {
          this.loading(false);
          this.$toast.error("Có lỗi khi lấy danh mục từ CCBS, liên hệ admin để xử lý!");
          return;
        }
      this.loading(false);
    },

    async select_timtheo() {
      this.loading(true);
      this.trangthai_id = 0
      this.options.cbx_trangthai = []
      this.check_hd = false

      var data = { hd_db: this.loai_tk_id };
      var response = await API.ts_tracuu_tonghop_tt(this.axios, data);
        if (
          response.data.error_code == "BSS-00000000" &&
          response.data.data && response.data.data.length > 0
        ) {
          this.loading(false);

          if (this.loai_tk_id != 1){
          this.check_hd = false
          var data_trangthai = response.data.data
          data_trangthai.push({
                  TRANGTHAI_ID: 0,
                  TEN: "Không chọn",
                });
          this.options.cbx_trangthai = data_trangthai
          }
          else{
          this.check_hd = true  
          var data_trangthai = response.data.data
          data_trangthai.push({
                  TRANGTHAIHD_ID: 0,
                  TEN: "Không chọn",
                });
          this.options.cbx_trangthai = data_trangthai
          }
        } else {
          this.loading(false);
          this.$toast.error("Có lỗi khi lấy danh mục từ CCBS, liên hệ admin để xử lý!");
          return;
        }
      this.loading(false);
    },

    async Timkiem_Click()
    {
      this.ds_tracuu_tonghop.list = []
      var body = ""
      var ruiro = ""
      if(this.ruiro_id != 0) ruiro = this.ruiro_id.toString()

      if (this.txtDiaChiCQ == '' && this.txtDiaChiTB == '' && this.txtMaKH == '' && this.txtMaCQ == '' && this.txtSoGIayTo == ''
        && this.txtSoSim == '' && this.txtSoMay == '' && this.txtTenCQ == '' && this.txtTenTB == '' && this.txtTenTT == ''
        )
        {
          this.$toast.warning("Bạn chưa nhập thông tin tra cứu!");
          return;
        } 

      body = {
          "diachicq": this.txtDiaChiCQ,
          "diachitb": this.txtDiaChiTB,
          "hd_or_db": this.loai_tk_id.toString(),
          "kh_rr": ruiro,
          "ma_kh": this.txtMaKH,
          "macq": this.txtMaCQ,
          "page_num": "1",
          "page_rec": "300",
          "so_gt": this.txtSoGIayTo,
          "so_sim": this.txtSoSim,
          "so_tb": "84" + this.txtSoMay,
          "tencq": this.txtTenCQ,
          "tentb": this.txtTenTB,
          "tentt": this.txtTenTT,
          "trangthai": this.trangthai_id.toString()
        }

        this.loading(true);
        var response = await API.ts_tracuu_tonghop(this.axios, body);
        if (
          response.data.error_code == "BSS-00000000" &&
          response.data.data && response.data.data.length > 0
        ) {
          this.loading(false);
          this.ds_tracuu_tonghop.list = response.data.data
        }
        else {
          this.loading(false);
          this.$toast.warning("Không có dữ liệu tra cứu thông tin thuê bao từ CCBS!");
        }
    },

    Nhapmoi_Click()
    {
      this.ruiro_id = 0
      this.trangthai_id = 0
      this.txtDiaChiCQ = ""
      this.txtDiaChiTB = ""
      this.txtMaCQ = ""
      this.txtMaKH = ""
      this.txtSoGIayTo = ""
      this.txtSoSim = ""
      this.txtSoMay = ""
      this.txtTenCQ = ""
      this.txtTenTB = ""
      this.txtTenTT = ""
      this.ds_tracuu_tonghop.list = []
      this.$nextTick(() => this.$refs.focus.focus())
    }
  }

};
</script>