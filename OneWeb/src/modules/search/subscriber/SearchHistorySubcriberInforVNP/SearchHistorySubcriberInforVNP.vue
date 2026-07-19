<template src="./SearchHistorySubcriberInforVNP.html"></template>
<style scoped src="./SearchHistorySubcriberInforVNP.scss"></style>

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


export default {
  components: { breadcrumb, VueFlatPickr, gridview, API,DataGridNV},
  name: "SearchHistorySubcriberInforVNP",
  created() {},

  mounted() {
    this.init();
    this.$nextTick(() => this.$refs.focus.focus());
  },

  watch: {
  },

  data() {
    return {

      header: {
        title: 'Tra cứu lịch sử thông tin thuê bao',
        list: [
          { name: 'Tra cứu', link: { name: 'Ui.cards' } },
          {
            name: 'Tra cứu lịch sử thông tin thuê bao',
            link: { name: 'Ui.buttons' }
          }
        ]
      },
      txtSoMay: "",
      txtDiaChiTB: "",
      txtTenTB: "",
      txtMaKH: "",
      thaotac_id: 0,
      options: {
        cbx_thaotac: []
      },

      ds_lichsu_thuebao: {
      header: [
        {
          headerText: "STT",
          fieldName: "ROW_STT",
          allowSorting: false,
          width: "50",
          textAlign: "center",
          autoFit: "true",
          allowFiltering: true,
        },
        {
          headerText: "Tên hiển thị",
          fieldName: "TEN_HIENTHI",
          allowSorting: false,
          width: "100",
          textAlign: "left",
          autoFit: "true",
          allowFiltering: true,
        },
        {
          headerText: "Ngày cập nhật",
          fieldName: "NGAY_CN",
          allowSorting: false,
          width: "100",
          textAlign: "left",
          autoFit: "true",
          allowFiltering: true,
        },
        {
          headerText: "Giá trị cũ",
          fieldName: "GIATRI_CU",
          allowSorting: false,
          width: "100",
          textAlign: "left",
          autoFit: "true",
          allowFiltering: true,
        },
        {
          headerText: "Giá trị mới",
          fieldName: "GIATRI_MOI",
          allowSorting: false,
          width: "100",
          textAlign: "left",
          autoFit: "true",
          allowFiltering: true,
        },
        {
          headerText: "Người cập nhật",
          fieldName: "GIATRI_MOI",
          allowSorting: false,
          width: "100",
          textAlign: "left",
          autoFit: "true",
          allowFiltering: true,
        },
        {
          headerText: "Máy cập nhật",
          fieldName: "MAY_CN",
          allowSorting: false,
          width: "100",
          textAlign: "left",
          autoFit: "true",
          allowFiltering: true,
        },
      ],
      list: [],
      },
    };
  },

  methods: {
    async init() {
      var data_thaotac = await API.ts_tracuu_ls_tb_tt(this.axios);
      if (
        data_thaotac.data.error_code == "BSS-00000000" &&
        data_thaotac.data.data.length > 0
      ) {
        this.options.cbx_thaotac = data_thaotac.data.data
      }
      else {
        this.$toast.warning("Không lấy được danh mục thao tác từ CCBS, liên hệ Admin để xử lý");
        return
      }
    },

    async Nhapmoi_Click()
    {
      this.txtMaKH = ''
      this.txtDiaChiTB = ''
      this.txtTenTB = ''
      this.ds_lichsu_thuebao.list = []
      this.txtSoMay = ''
      this.thaotac_id = 0
      this.$nextTick(() => this.$refs.focus.focus());
    },

    async Timkiem_Click()
    {
      this.ds_lichsu_thuebao.list = []

      // max length
      if (this.txtSoMay.length > 30 ||  this.txtMaKH.length > 30) {
          this.$toast.warning("Trường tìm kiếm nhập dữ liệu vượt quá độ dài cho phép là 30 ký tự!");
          this.txtSoMay = ""
          this.txtMaKH = ""
          this.$nextTick(() => this.$refs.focus.focus());
          return
      } 

      if (this.txtSoMay == "" &&  this.txtMaKH == "")
      {
        this.$toast.warning("Bạn chưa nhập thông tin tìm kiếm!");
        this.$nextTick(() => this.$refs.focus.focus());
        return
      }
      
      this.txtDiaChiTB = ""
      this.txtTenTB = ""
      this.ds_lichsu_thuebao.list = []

      var body = {
        "so_tb": "84" + this.txtSoMay,
        "page":"1",
        "ma_kh": this.txtMaKH,
        "thao_tac": this.thaotac_id.toString()
      }

      // Tra cứu dữ liệu lịch sử
      this.loading(true);
      var data_ls = await API.ts_tracuu_ls_tb(this.axios, body);
      if (
        data_ls.data.error_code == "BSS-00000000" &&
        data_ls.data.data.length > 0
      ) {
        this.loading(false);
        this.ds_lichsu_thuebao.list = data_ls.data.data;
      }
      else {
        this.loading(false);
        this.$toast.warning("Không tìm thấy lịch sử thông tin của thuê bao!");
      }

      // Tra cứu thông tin thuê bao
      if (this.txtSoMay != "")
      {
        var body1 = {"so_tb": "84" + this.txtSoMay}
        var data_thuebao = await API.ts_tracuu_laytt_tb_ts(this.axios, body1);
        if (
          data_thuebao.data.error_code == "BSS-00000000" &&
          data_thuebao.data.data.length > 0
        ) {
          this.loading(false);
          this.txtTenTB =  data_thuebao.data.data[0].TEN_TB
          this.txtDiaChiTB =  data_thuebao.data.data[0].DIACHI
        }
      }
      this.loading(false);
    }
  },
};
</script>