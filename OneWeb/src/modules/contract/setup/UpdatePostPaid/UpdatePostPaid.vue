<template src="./UpdatePostPaid.html"></template>
<style scoped src="./UpdatePostPaid.scss"></style>

<script>
import breadcrumb from "@/components/breadcrumb";
import VueFlatPickr from "vue-flatpickr-component";
import API from "./api";
import gridview from "@/components/Shared/gridview";

//CSS
import "@/assets/vendor/bootstrap-table/bootstrap-table.min.css";
import "@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.css";
//JS
import "@/assets/vendor/jquery/split";
import "@/assets/vendor/bootstrap-table/bootstrap-table.min";
import "@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.min";

import KDatePicker from "@/components/kylq_components/KDatePicker.vue";

export default {
  components: { breadcrumb, VueFlatPickr, gridview, API, KDatePicker },
  name: "ThongTinKhachHang",
  props: ["ma_gd","isPopup"],
  created() {},

  mounted() { 
    this.init();
    // kiểm tra tag để load giao diện button
    for(var i = 1; i <= 4 ; i++){
        var anh = new Object();
        anh.image_base = null;
        anh.image_name = this.ganTenAnh(i);
        anh.image_file = null; //
        this.thongtin_anhht.push(anh);
    }
  },
  watch: {},
  data() {
    return {
      header: {
        title: 'TRA CỨU THÔNG TIN THUÊ BAO TRẢ TRƯỚC',
        list: [
          { name: 'Trả trước', link: { name: 'Ui.cards' } },
          {
            name: 'Cập nhật trả trước',
            link: { name: 'Ui.buttons' }
          }
        ]
      },

      // Khai báo thuộc tính:
      showPopup: false,
      displayStylePopup:"none",
      displayStyleImage :"none",
      anhDaChon : null,
      result: "",
      kiemtra: false,
      isActiceTabAnh: false,
      so_tb: "",
      ten_tb: "",
      diachi_tb: "",
      so_gt: "",
      ngay_cap: "",
      ngay_sinh: "",
      noi_dkhk: "",
      ghi_chu: "",
      loaigt_id: 0,
      loaikh_id: 0,
      nuoc_id: 0,
      tinh_id: 0,
      doituongsd_id: 0,
      gioi_tinh: null,
      trang_thai: "",
      thongtin_anhht:[],
      options: {
        cbx_dsloaigt: [],
        cbx_doituongsd: [],
        cbx_dsloaikh: [],
        cbx_dsnuoc: [],
        cbx_dstinh: [],
      },

      ds_lichsu_thaydoi_anh: {
        header: [
          {
            headerText: "Ngày cập nhật",
            fieldName: "NGAY_CN",
            allowSorting: false,
            width: "80",
            textAlign: "center",
            autoFit: "true",
            allowFiltering: true,
          },
          {
            headerText: "Số điện thoại",
            fieldName: "MSISDN",
            allowSorting: false,
            width: "80",
            textAlign: "center",
            autoFit: "true",
            allowFiltering: true,
          },
          {
            headerText: "Tên ảnh",
            fieldName: "FILE_NAME",
            allowSorting: false,
            width: "80",
            textAlign: "center",
            autoFit: "true",
            allowFiltering: true,
          },
          {
            headerText: "Loại ảnh",
            fieldName: "LOAI_ANH",
            allowSorting: false,
            width: "80",
            textAlign: "center",
            autoFit: "true",
            allowFiltering: true,
          },
          {
            headerText: "User thực hiện",
            fieldName: "USERID",
            allowSorting: false,
            width: "80",
            textAlign: "center",
            autoFit: "true",
            allowFiltering: true,
          },
        ],
        list: [],
      },
      ds_lichsu_thaydoi_tt: {
        header: [
          {
            headerText: "Ngày CN",
            fieldName: "REGISTERDATE",
            allowSorting: false,
            width: "80",
            textAlign: "center",
            autoFit: "true",
            allowFiltering: true,
          },
          {
            headerText: "Tên TB",
            fieldName: "FULLNAME",
            allowSorting: false,
            width: "120",
            textAlign: "center",
            autoFit: "true",
            allowFiltering: true,
          },
          {
            headerText: "Giới tính",
            fieldName: "GENDER",
            allowSorting: false,
            width: "75",
            textAlign: "center",
            autoFit: "true",
            allowFiltering: true,
          },
          {
            headerText: "Loại GT",
            fieldName: "TENLOAI",
            allowSorting: false,
            width: "100",
            textAlign: "center",
            autoFit: "true",
            allowFiltering: true,
          },
          {
            headerText: "Số GT",
            fieldName: "IDNUMBER",
            allowSorting: false,
            width: "75",
            textAlign: "center",
            autoFit: "true",
            allowFiltering: true,
          },
          {
            headerText: "Nơi cấp",
            fieldName: "PLACEOFISSUE",
            allowSorting: false,
            width: "100",
            textAlign: "center",
            autoFit: "true",
            allowFiltering: true,
          },
          {
            headerText: "Ngày cấp",
            fieldName: "PLACEDATE",
            allowSorting: false,
            width: "75",
            textAlign: "center",
            autoFit: "true",
            allowFiltering: true,
          },
          {
            headerText: "Ngày sinh",
            fieldName: "BIRTHDAY",
            allowSorting: false,
            width: "75",
            textAlign: "center",
            autoFit: "true",
            allowFiltering: true,
          },
          {
            headerText: "Nơi ĐKHKTT",
            fieldName: "ADDRESS",
            allowSorting: false,
            width: "180",
            textAlign: "center",
            autoFit: "true",
            allowFiltering: true,
          },
          {
            headerText: "Người CN",
            fieldName: "AGENTID",
            allowSorting: false,
            width: "75",
            textAlign: "center",
            autoFit: "true",
            allowFiltering: true,
          },
        ],
        list: [],
      },
    };
  },

  methods: {
    onTabAnhClicked() {
      this.isActiceTabAnh = true;
    },
    onTabTbClicked() {
      this.isActiceTabAnh = false;
    },
    onSelectedRowChange() {
    },

    TimThongTinKH() {
      var so_may = "84" + this.so_tb.trim();
      if (this.so_tb.trim() == null || this.so_tb.trim() == "") {
          this.$toast.error(
            "Không tìm thấy thông tin. Bạn hãy nhập lại số thuê bao tìm kiếm!"
          );
      this.$nextTick(() => this.$refs.focus.focus())
        return;
      }
      //get thông tin khách hàng by mã khách hàng
      this.app_tb_tracuu_thongtin_tt(so_may);
    },

    app_tb_tracuu_thongtin_tt(so_may) {
      var api_tc = API.app_tb_tracuu_thongtin_tt(this.axios, {
        so_tb: so_may,
      }).then((response) => {
        if (response.data.error_code == "BSS-00000000") {
          this.ten_tb = response.data.data[0].FULLNAME;
          this.noi_dkhk = response.data.data[0].ADDRESS;
          this.loaigt_id = response.data.data[0].IDNUMBERTYPE;
          this.loaikh_id = response.data.data[0].CUSTOMERTYPE;
          this.nuoc_id = response.data.data[0].NATIONALITYID;
          this.tinh_id = response.data.data[0].PLACEOFISSUE;
          this.doituongsd_id = response.data.data[0].CUSTOMER_USE_ID;
          this.gioi_tinh = response.data.data[0].GENDER;
          this.trang_thai = response.data.data[0].STATUS;
          this.ngay_sinh = response.data.data[0].BIRTHDAY;
          this.so_gt = response.data.data[0].IDNUMBER;
          this.ngay_cap = response.data.data[0].PLACEDATE;
          this.noi_dkhk = response.data.data[0].ADDRESS;
          this.ghi_chu = response.data.data[0].NOTE;
          this.kiemtra = true;
          this.ds_lichsu_thaydoi_tt.list = [];
          this.ds_lichsu_thaydoi_anh.list = [];
        } else if (response.data.error_code == "BSS-00000500") {
          this.$toast.error(
            "Request timed out after 30000ms. Liên hệ admin để xử lý!"
          );
          return;
        } else {
          this.HienThiGiaoDien();
          this.$toast.error(
            "Không tìm thấy thông tin. Bạn hãy nhập lại số thuê bao tìm kiếm!"
          );
        }
        this.loading(false);
      });
    },

    app_tb_tracuu_thongtin_anh(so_may1) {
      
      this.loading(true);
      var api_anh = API.app_tb_image_dktt(this.axios, {
        so_tb: so_may1,
      }).then((response) => {
        if (
          response.data.error_code == "BSS-00000000" &&
          response.data.data.length > 0
        ) {
          for(var i = 1; i <= 4; i++){
           
             var tmp_img =  response.data.data.filter((item) => item.type == i)[0]
             if(tmp_img !=null && tmp_img != undefined ){
               this.thongtin_anhht[i-1].image_base = tmp_img.image_base
               this.thongtin_anhht[i-1].image_file = tmp_img.image_name
             }else{
               this.thongtin_anhht[i-1].image_base = null
               this.thongtin_anhht[i-1].image_file = null
             }
            
          }
          this.loading(false);
        } else {
          for(var i = 1; i <= 4;i++){
            this.thongtin_anhht[i-1].image_base = null
          }
          this.loading(false);
          this.$toast.error(
            "Không tìm thấy thông tin ảnh. Bạn hãy nhập lại số thuê bao tìm kiếm!"
          );
          return;
        }
        this.loading(false);
      })
    },

    ganTenAnh(loai) {
      if(loai == 1)
        return "Ảnh CMT mặt trước"
      else if(loai == 2)
        return "Ảnh CMT mặt sau"
      else if(loai == 3)
        return "Ảnh người đăng ký "
      else if(loai == 4)
        return "Ảnh phiếu đang ký"
    },

    popup() {
      if (!this.kiemtra) {
        this.$toast.error(
          "Không tìm thấy thông tin ảnh. Bạn hãy nhập lại số thuê bao tìm kiếm!"
        );
        return;
      } else {
        // Hiển thị khi bấm nút xem ảnh
        this.displayStylePopup = "block";
        this.showPopup = true;
        var so_may1 = "84" + this.so_tb.trim();
        this.app_tb_tracuu_thongtin_anh(so_may1);
      }
    },

    closepopup() {
      this.displayStylePopup = "none";
    },
    imgClick(item){
      if(item != undefined && item != null){
        this.anhDaChon = item
      
        //this.showPopup = false
        this.displayStyleImage = "block"
      }
      
    },
    onLayLSTTClicked() {
      var data = { so_tb: "84" + this.so_tb };
      var api_tt = API.ts_lichsu_capnhat_thuebao_tratruoc(
        this.axios,
        data
      ).then((response) => {
        if (
          response &&
          response.data &&
          response.data.data &&
          response.data.error_code &&
          response.data.error_code === "BSS-00000000"
        ) {
          this.ds_lichsu_thaydoi_tt.list = response.data.data;
        } else {
          this.$toast.error(
            "Không tìm thấy lịch sử thay đổi thông tin thuê bao!"
          );
        }
      });
      var api_anh = API.ts_lichsu_capnhat_anh(this.axios, data).then(
        (response) => {
          if (
            response &&
            response.data &&
            response.data.data &&
            response.data.error_code &&
            response.data.error_code === "BSS-00000000"
          ) {
            this.ds_lichsu_thaydoi_anh.list = response.data.data;
          } else {
            this.$toast.error("Không tìm thấy lịch sử thay đổi ảnh thuê bao!");
          }
        }
      );
    },
    async init() {
      
      this.loading(true);
      this.watcherActive = false;
      var api1 = API.LOAI_GT_NEO(this.axios, { vchuoi: "LOAI_GT_NEO" }).then(
        (response) => {
          if (
            response &&
            response.data &&
            response.data.data &&
            response.data.error_code &&
            response.data.error_code === "BSS-00000000"
          ) {
            this.options.cbx_dsloaigt = response.data.data;
          }
          else {
            this.$toast.error("Có lỗi khi load danh sách loại giấy tờ từ DB. Liên hệ admin để xử lý!");
          }
        }
      );
      var api2 = API.DOITUONG_SD_NEO(this.axios, {
        vchuoi: "DOITUONG_SD_NEO",
      }).then((response) => {
        if (
          response &&
          response.data &&
          response.data.data &&
          response.data.error_code &&
          response.data.error_code === "BSS-00000000"
        ) {
          this.options.cbx_doituongsd = response.data.data;
        }
          else {
            this.$toast.error("Có lỗi khi load danh sách loại đối tượng từ DB. Liên hệ admin để xử lý!");
          }
      });

      var api3 = API.LOAI_KHCN_NEO(this.axios, {
        vchuoi: "LOAI_KHCN_NEO",
      }).then((response) => {
        if (
          response &&
          response.data &&
          response.data.data &&
          response.data.error_code &&
          response.data.error_code === "BSS-00000000"
        ) {
          this.options.cbx_dsloaikh = response.data.data;
        }
          else {
            this.$toast.error("Có lỗi khi load danh sách loại KH từ DB. Liên hệ admin để xử lý!");
          }
      });

      var api4 = API.QUOCTICH_NEO(this.axios, { vchuoi: "QUOCTICH_NEO" }).then(
        (response) => {
          if (
            response &&
            response.data &&
            response.data.data &&
            response.data.error_code &&
            response.data.error_code === "BSS-00000000"
          ) {
            this.options.cbx_dsnuoc = response.data.data;
          }
          else {
            this.$toast.error("Có lỗi khi load danh sách quốc tịch từ DB. Liên hệ admin để xử lý!");
          }
        }
      );

      var api5 = API.TINH_NEOS(this.axios, { vchuoi: "TINH_NEOS" }).then(
        (response) => {
          if (
            response &&
            response.data &&
            response.data.data &&
            response.data.error_code &&
            response.data.error_code === "BSS-00000000"
          ) {
            this.options.cbx_dstinh = response.data.data;
          }
          else {
            this.$toast.error("Có lỗi khi load danh sách các tỉnh từ DB. Liên hệ admin để xử lý!");
          }
        }
      );
      if(this.ma_gd){
        this.isPopup = true
      }
      this.loading(false);
      this.$nextTick(() => this.$refs.focus.focus())
    },

    HienThiGiaoDien() {
      //this.so_tb = "",
      (this.ten_tb = ""),
      (this.diachi_tb = ""),
      (this.so_gt = ""),
      (this.ngay_cap = ""),
      (this.ngay_sinh = ""),
      (this.noi_dkhk = ""),
      (this.ghi_chu = ""),
      (this.loaigt_id = 0),
      (this.loaikh_id = 0),
      (this.nuoc_id = 0),
      (this.tinh_id = 0),
      (this.doituongsd_id = 0),
      (this.gioi_tinh = null),
      (this.trang_thai = ""),
      (this.cbx_dsloaigt = []),
      (this.cbx_doituongsd = []),
      (this.cbx_dsloaikh = []),
      (this.cbx_dsnuoc = []),
      (this.cbx_dstinh = []),
      (this.ds_lichsu_thaydoi_tt.list = []),
      (this.ds_lichsu_thaydoi_anh.list = []),
      (this.kiemtra = false)
    },
   },
};

</script>