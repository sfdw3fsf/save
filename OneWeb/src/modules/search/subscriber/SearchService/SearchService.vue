<template src="./SearchService.html"></template>
<style scoped>
  .list-actions-top .list > li > div.button {
      display: block;
      padding: 2px 15px;
      color: #0176FF;
      border-left: 1px solid #BAE7FF;
      text-decoration: none;
      font-weight: 600;
      cursor: pointer;
  } 
</style> 
<script>
import breadcrumb from "@/components/breadcrumb";
import moment from "moment";
import DatePicker from "vue2-datepicker";
// import { KIEU_DL, TRANGTHAI_PH } from "./enum";
import SearchServiceAPI from "./API/SearchServiceAPI";
import CommonAPI from "./API/CommonAPI";
import CommonFunction from "./API/CommonFunction";
import gridview from "@/components/Shared/gridview";
// import uploadEProfile from "../../profile/UploadEProfile";
// import giaophieunhanvien from "../../setup/DeclareLandline/popup/giaophieunhanvien";
// import ModalChonDiaChi from "../../setup/InstallNewSubs/Popups/popupChonDiaChi";
// import BssErrorMarker from "@/components/BssErrorMarker";
// import BssRequiredMarker from "@/components/BssRequiredMarker";
// import ThongTinLienHe from "../../setup/ChangeContractOwner/popup/ThongTinLienHe.vue";
// import traphieu from "../../setup/ReturnInstallTicket/components/traphieu.vue";
// import ChuyenTo from "../../profile/CompleteProfileChangeAccount/Popups/ChuyenTo.vue";
// import TonPhieu from "../../setup/Payment/components/TonPhieu.vue";
// import SearchEProfile from "../../../search/subscriber/SearchEProfile";
// import GiaoPhieuPhoiHop from "./popups/popupGiaoPhieuPhoiHop/GiaoPhieuPhoiHop.vue";
// import LayDuLieuBC from "./popups/popupLayDuLieuBC/LayDuLieuBC.vue";

import "vue2-datepicker/locale/vi.js";
import "vue2-datepicker/index.css";
//CSS
import "@/assets/vendor/bootstrap-table/bootstrap-table.min.css";
import "@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.css";
//JS
import "@/assets/vendor/jquery/split";
import "@/assets/vendor/bootstrap-table/bootstrap-table.min";
import "@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.min";
import downloadexcel from "vue-json-excel";
import { required, minValue, maxValue } from "vuelidate/lib/validators";
// import {
//   DichVuVienThong,
//   TRANGBI,
//   DUNG_THU,
//   LoaiHopDong,
//   TrangThaiHD,
//   LoaiHinhTB,
//   ThoiHan,
//   KHOANMUC_TT,
//   KieuCaiDatDVGT,
//   THUESO,
//   TRANGTHAI_DONGBO,
//   KieuLapDat,
//   LOAI_DV,
//   Loai_KH,
//   HINHTHUC_TC,
//   KIEU_HD,
//   KIEU_KH,
//   PHANLOAI_KH,
//   Loai_NV,
//   LOAISO,
//   LOAI_KV,
// } from "../../setup/InstallNewSubs/Enums";
import treemenuVue from "../../../quantri/components/UploadFileHoSoDienTu/treemenu.vue";

//
export default {
  components: {
    breadcrumb,
    gridview,
    downloadexcel,
    // uploadEProfile,
    // giaophieunhanvien,
    // ModalChonDiaChi,
    // DatePicker,
    // BssErrorMarker,
    // BssRequiredMarker,
    // ThongTinLienHe,
    // traphieu,
    // ChuyenTo,
    // TonPhieu,
    // SearchEProfile,
    // GiaoPhieuPhoiHop,
    // LayDuLieuBC,
  },
  name: "SearchService",
  props: ["Tag"],
  validations: {},
  mounted() {
    CommonAPI.setAxios(this.axios);
    this.state.donvi_id = this.$root.token.getDonViID() * 1;
    this.state.nhanvien_id = this.$root.token.getNhanVienID() * 1;

    this.pageLoad();
  },

  data() {
    //Tra cứu dịch vụ, UR2.8.006, WinUI.WinUITraCuu.frmTraCuuDichVu,PTTB.exe,1
    console.log("this.$router.currentRoute", this.$router.currentRoute);
    var Tag = "1";
    var loai_id = 1;
    if (Tag.toString() != "") {
      var str = Tag.toString();
      if (str.indexOf("ShowDialog") > 0) {
        str = str.replaceAll("ShowDialog", "");
        str = str.replaceAll("+", "");
      }

      if (str != "") loai_id = parseInt(str);
    } else {
      loai_id = 1;
    }
    this.loai_id = loai_id;
    var currDate = new Date();

    return {
      popupComponent: null,
      popupComponentData: null,
      popupComponentAttr: null,
      popupComponentName: "",
      popupComponentEvents: {
        "form-close": "popupClosed",
        acceptChangeCTV: "popupClosed",
        acceptChangeNGT: "popupClosed",
      },
      header: {
        title: "TRA CỨU DỊCH VỤ",
        list: [
          { name: "Tra cứu dịch vụ", link: { name: "Ui.cards" } },
          {
            name: "Tra cứu",
            link: { name: "Ui.buttons" },
          },
        ],
      },

      selectionOptions: { type: "Multiple" },
      target: "#app",
      dateFormat: "DD/MM/YYYY",
      datetimeFormat: "DD/MM/YYYY HH:mm",
      tt_nd: {
        nhanvien_id: this.$root.token.getNhanVienID() * 1,
        donvi_id: this.$root.token.getDonViID() * 1,
        ngay_cn: moment(currDate).format("DD/MM/YYYY"),
        ngay_bg: moment(currDate.setSeconds(currDate.getSeconds() + 30)).format(
          "DD/MM/YYYY"
        ),
      },
      loai_id: 1,
      controls: {},
      state: {},
      listbox: {
        cboDichVu: {
          items: [],
          value: 0,
        },
      },

      gridDVK: {
        headers: [
          {
            fieldName: "MA_TB",
            headerText: "Mã TB",
            allowFiltering: false,
            allowSorting: true,
            visible: true,
          },
          {
            fieldName: "MA_TT",
            headerText: "Mã TT",
            allowFiltering: true,
            allowSorting: true,
            visible: true,
          },
          {
            fieldName: "TEN_TT",
            headerText: "Tên TT",
            allowFiltering: true,
            allowSorting: true,
            visible: true,
          },
          {
            fieldName: "DIACHI_TT",
            headerText: "Địa chỉ TT",
            allowFiltering: true,
            allowSorting: true,
            visible: true,
          },
          {
            fieldName: "DIACHI_CT",
            headerText: "Địa chỉ VAT",
            allowFiltering: true,
            allowSorting: true,
            visible: true,
          },
          {
            fieldName: "TEN_DVKHAC",
            headerText: "Tên dịch vụ",
            allowFiltering: true,
            allowSorting: true,
            visible: true,
          },
          {
            fieldName: "NGAYDK",
            headerText: "Ngày sử dụng",
            allowFiltering: true,
            allowSorting: true,
            visible: true, 

          },
          {
            fieldName: "GHICHU",
            headerText: "Ghi chú",
            allowFiltering: true,
            allowSorting: true,
            visible: true,
          },
        ],
        data: [],
      },
      gridDVGT: {
        headers: [
          {
            fieldName: "TEN_DVGT",
            headerText: "Tên dịch vụ",
            allowFiltering: true,
            allowSorting: true,
            visible: true,
          },
          {
            fieldName: "NGAYDK",
            headerText: "Ngày sử dụng",
            allowFiltering: true,
            allowSorting: true,
            visible: true,
          },
          {
            fieldName: "GHICHU",
            headerText: "Ghi chú",
            allowFiltering: true,
            allowSorting: true,
            visible: true,
          },
        ],
        data: [],
      },
      gridLS_DVGT: {
        headers: [
          {
            fieldName: "TEN_DVGT",
            headerText: "Tên dịch vụ",
            allowFiltering: true,
            allowSorting: true,
            visible: true,
          },
          {
            fieldName: "KIEUBD_ID",
            headerText: "Kiểu biến động",
            allowFiltering: true,
            allowSorting: true,
            visible: true,
          },
          {
            fieldName: "NGAY_BD",
            headerText: "Ngày thay đổi",
            allowFiltering: true,
            allowSorting: true,
            visible: true,
          },
          {
            fieldName: "NOIDUNG",
            headerText: "Nội dung",
            allowFiltering: true,
            allowSorting: true,
            visible: true,
          },
        ],
        data: [],
      },
      gridLS_DVK: {
        headers: [
          {
            fieldName: "TEN_DVKHAC",
            headerText: "Tên dịch vụ",
            allowFiltering: true,
            allowSorting: true,
            visible: true,
          },
          {
            fieldName: "NGAY_BD",
            headerText: "Kiểu biến động",
            allowFiltering: true,
            allowSorting: true,
            visible: true,
          },
          {
            fieldName: "NGAY_BD",
            headerText: "Ngày thay đổi",
            allowFiltering: true,
            allowSorting: true,
            visible: true,
          },
          {
            fieldName: "NOIDUNG",
            headerText: "Nội dung",
            allowFiltering: true,
            allowSorting: true,
            visible: true,
          },
        ],
        data: [],
      },
    };
  },

  computed: {},
  validations: {
    check_ngaygv: {
      required(value) {
        return value;
      },
    },
  },
  methods: {
    async pageLoad() {
      //Combo dịch vụ
      SearchServiceAPI.CSS_DICHVU_VT(this.axios, {}).then((res) => {
        //console.log("CSS_KIEU_HD", response);
        this.listbox.cboDichVu.items = res.data.data.map((x) => ({
          id: x.DICHVUVT_ID,
          text: x.TEN_DVVT,
        }));
        if (
          this.listbox.cboDichVu.items &&
          this.listbox.cboDichVu.items.length > 0
        )
          this.listbox.cboDichVu.value = this.listbox.cboDichVu.items[0].id;
      });

      await this.HienThi_GiaoDien();
    },

    async HienThi_GiaoDien() {
      if (this.loai_id == 1) {
        //theo thuê bao
        this.gridDVK.headers.filter(
          (x) => x.fieldName == "MA_TT"
        )[0].visible = false;
        this.gridDVK.headers.filter(
          (x) => x.fieldName == "TEN_TT"
        )[0].visible = false;
        this.gridDVK.headers.filter(
          (x) => x.fieldName == "MA_TB"
        )[0].visible = false;
        this.gridDVK.headers.filter(
          (x) => x.fieldName == "DIACHI_TT"
        )[0].visible = false;
        this.gridDVK.headers.filter(
          (x) => x.fieldName == "DIACHI_CT"
        )[0].visible = false;
      } else {
        this.gridDVK.headers.filter(
          (x) => x.fieldName == "MA_TT"
        )[0].visible = true;
        this.gridDVK.headers.filter(
          (x) => x.fieldName == "TEN_TT"
        )[0].visible = true;
        this.gridDVK.headers.filter(
          (x) => x.fieldName == "MA_TB"
        )[0].visible = true;
        this.gridDVK.headers.filter(
          (x) => x.fieldName == "DIACHI_TT"
        )[0].visible = true;
        this.gridDVK.headers.filter(
          (x) => x.fieldName == "DIACHI_CT"
        )[0].visible = true;
      }
    },
    async HienThiTT_DVGT(thuebao_id) {
      var loai_id = this.loai_id;
      if (loai_id == 1) {
        var dt = await CommonAPI.lay_ds_sudung_dichvu(
          this.axios,
          1,
          "",
          thuebao_id
        );
        if (!dt) return;
        console.log(dt);
        this.gridDVGT.data = CommonFunction.UpperKeyJSON(dt.o_dvgt);
        this.gridDVK.data = CommonFunction.UpperKeyJSON(dt.o_dvk);
        this.gridLS_DVGT.data = CommonFunction.UpperKeyJSON(dt.o_ls_dvgt);
        this.gridLS_DVK.data = CommonFunction.UpperKeyJSON(dt.o_ls_dvk);
      } else {
        var vds_matb = "";
        var _vma_tb = this.state.txtMaTB.Text.trim().split(",");
        for (var i = 0; i < _vma_tb.length; i++) {
          vds_matb += _vma_tb[i] + "','";
        }
        if (vds_matb.length > 0) {
          vds_matb = vds_matb.substring(0, vds_matb.length - 3);
          vds_matb = "('" + vds_matb + "')";
        }

        var dt = await CommonAPI.lay_ds_sudung_dichvu(
          this.axios,
          2,
          vds_matb,
          0
        );
        console.log(dt);
        if (!dt) return;

        if (dt.o_dvgt && dt.o_dvgt.length > 0) {
          var data = dt.o_dvgt[0];
          

          this.gridDVGT.data = CommonFunction.UpperKeyJSON(dt.o_dvgt);
        } else {
          this.gridDVGT.data = [];
        }

        if (dt.o_dvk && dt.o_dvk.length > 0) {
          var data = dt.o_dvk[0];
          

          this.gridDVK.data = CommonFunction.UpperKeyJSON(dt.o_dvk);
          console.log(this.gridDVK.data);
        } else {
          this.gridDVK.data = [];
        }

        if (dt.o_ls_dvgt && dt.o_ls_dvgt.length > 0) {
          this.gridLS_DVGT.data = CommonFunction.UpperKeyJSON(dt.o_ls_dvgt);
        } else {
          this.gridLS_DVGT.data = [];
        }

        if (dt.o_ls_dvk && dt.o_ls_dvk.length > 0) {
          this.gridLS_DVK.data = CommonFunction.UpperKeyJSON(dt.o_ls_dvk);
        } else {
          this.gridLS_DVK.data = [];
        }
      }

    },
    async HienThiDS(matb) {
      this.state.ds = await CommonAPI.lay_db_theo_matb_1(this.axios, matb.trim(), 0);

      if (this.state.ds.length > 0) {
          let data = this.state.ds[0];
          //Thông tin khách hàng
          this.state.txtMaKH = data["ma_kh"].toString();
          this.state.txtTenKH = CommonFunction.ChuanHoaTen(
            data["ten_kh"].toString()
          );
          this.state.txtDiaChiKH = CommonFunction.ChuanHoaTen(
            data["diachi_kh"].toString()
          );

          //Thông tin thuê bao
          this.state.txtTenTB = CommonFunction.ChuanHoaTen(
            data["ten_tb"].toString()
          );
          this.state.txtDiaChiTB = CommonFunction.ChuanHoaTen(
            data["diachi_tb"].toString()
          );
          this.state.txtDiaChiLD = CommonFunction.ChuanHoaTen(
            data["diachi_ld"].toString()
          );  

        if (this.state.ds.length == 1) {
          this.state.vdichvuvt_id = this.state.ds[0]["dichvuvt_id"] * 1;
          this.listbox.cboDichVu.value = this.state.vdichvuvt_id;
          this.state.thuebao_id = this.state.ds[0]["thuebao_id"] * 1;
          await this.HienThiTT_DVGT(this.state.thuebao_id);
        } 
        else
        {
            var dlist = CommonFunction.UpperKeyJSON(this.state.ds);

            this.popupComponent = ()=>import('@/modules/contract/setup/InstallNewSubs/Popups/popupChonGrid');
            this.popupComponentName = "frmTraCuu_ThueBao";
            this.popupComponentData = {"ds":dlist,cols:[ 
              {fieldName: 'MA_TB', headerText: 'Số máy/Account', allowFiltering: false, allowSorting: false}, 
              {fieldName: 'DICHVU_VT', headerText: 'Dịch vụ', allowFiltering: false, allowSorting: false}, 
              {fieldName: 'LOAIHINH_TB', headerText: 'Loại hình', allowFiltering: false, allowSorting: false},
              {fieldName: 'TEN_TB', headerText: 'Tên thuê bao', allowFiltering: false, allowSorting: false},
              {fieldName: 'MA_TB_SUB', headerText: 'Mã TB sub', allowFiltering: false, allowSorting: false},
              {fieldName: 'TRANGTHAI_TB', headerText: 'Trạng thái', allowFiltering: false, allowSorting: false},
              {fieldName: 'MA_TT', headerText: 'Mã thanh toán', allowFiltering: false, allowSorting: false},
              {fieldName: 'TEN_TT', headerText: 'Tên thanh toán', allowFiltering: false, allowSorting: false},
              {fieldName: 'DIACHI_LD', headerText: 'Địa chỉ LĐ', allowFiltering: false, allowSorting: false},
            ],keycol: "DONVIQL_ID",allowExit:false,tieude:"Danh sách thuê bao",tieudebang: "Địa chỉ: ",}
            this.Popup('popupComponents'); 
        }
      } else { 
        this.$toast.error(
          "Không tìm thấy thông tin thuê bao vừa nhập !"
        );
        this.state.txtTenTB = "";
        this.state.txtDiaChiTB = "";
        this.state.txtMaKH = "";
        this.state.txtTenKH = "";
        this.state.txtDiaChiKH = "";
        this.state.txtDiaChiLD = ""; 
        this.$refs.txtMaTB.focus()
        this.gridDVGT.data = [];
        this.gridDVK.data = [];
        return;
      }
    },
    async txtMaTB_KeyPress() {
      if (this.state.txtMaTB && this.state.txtMaTB.trim() != "") {
        this.loading(true);
        await this.HienThiDS(this.state.txtMaTB.trim());
        //HienThiTT_DanhBa(txtMaTB.Text);
        //SendKeys.Send("{TAB}");

        this.loading(false);
      }
    },
    async tsbtnXem_Click() {
      if (this.state.txtMaTB && this.state.txtMaTB != "" && this.state.thuebao_id && this.state.thuebao_id != 0)
      {
          console.log("thuebao_id", this.state.thuebao_id);
          this.loading(true);
          await this.HienThiTT_DVGT(this.state.thuebao_id);
          this.loading(false);
      }
      else
      {  
        if (this.state.txtMaTB && this.state.txtMaTB.trim() != ""){
          await txtMaTB_KeyPress();
          return;
        }
        this.$toast.error(
          "Bạn chưa nhập thông tin tìm kiếm!"
        );
        this.$refs.txtMaTB.focus();
      }
    },  
    async tsbtnXuatExcel_Click(){ 
        if (this.gridDVK.data.length == 0)
        {
          this.$toast.error(
            "Không có dữ liệu để xuất Excel !"
          );
          return [];
        } 
        return this.gridDVK.data;
    }, 
    async popupClosed(val) {
      if(!val) return;
      this.loading(true);
      try {
        switch(this.popupComponentName) {
          case "frmTraCuu_ThueBao":
            console.log("frmTraCuu_ThueBao was CLOSED!");
             
            this.state.txtTenTB.Text = val["ten_tb"].toString();
            this.state.txtDiaChiTB.Text = val["diachi_tb"].toString();
            this.state.txtMaKH.Text = val["ma_kh"].toString();
            this.state.txtTenKH.Text = val["ten_kh"].toString();
            this.state.txtDiaChiKH.Text = val["diachi_kh"].toString();
            this.state.txtDiaChiLD.Text = val["diachi_ld"].toString();
            if (val["dichvuvt_id"])
            {
                this.state.vdichvuvt_id = val["dichvuvt_id"].toString();
                this.listbox.cboDichVu.value = this.state.vdichvuvt_id;
            }
            this.state.thuebao_id = val["thuebao_id"]*1;
            this.HienThiTT_DVGT(this.state.thuebao_id);
            break;
        }
      }catch(ex){
        console.error("popupClosed", ex)
      }
    },
  },
};
</script>
