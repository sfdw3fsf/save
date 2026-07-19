<template src="./index.template.html"></template>
<script>
import TraCuuChiTiet from "../tracuu-chitiet-thuebao-theofile";
import CapKetCuoiFilterTemplate from "./CapKetCuoiFilterTemplate.vue";
import ModalXuatDuLieu from "../TraCuuSplitterTheoPortOLT/ModalXuatDuLieu/index.vue";
import { L10n } from "@syncfusion/ej2-base";
L10n.load({
  "vi-VN": {
    dropdowns: {
      noRecordsTemplate: "Không có dữ liệu",
    },
  },
});
export default {
  components: { TraCuuChiTiet, ModalXuatDuLieu },
  data: function () {
    return {
      dsTTVT: [],
      dsToKT: [],
      dsVeTinh: [],
      kqTimKiem: [],
      kqGoiNhac: [],
      capKetCuoiID: 0,
      checkboxVetinhChecked: {
        type: Boolean,
        default: false,
      },
      checkboxTTVTChecked: {
        type: Boolean,
        default: false,
      },
      itemSearchTemplate: function (e) {
        return {
          template: CapKetCuoiFilterTemplate,
        };
      },
      txtSearch: null,
      loaiTraCuu: 1,
      dsThuocTinh: [],
    };
  },
  created: async function () {
    this.$root.showLoading(true);
    try {
      await this.getDsTTVT();
      await this.getToKT();
      await this.getDsVeTinh();
      this.checkboxVetinhChecked = false;
      this.checkboxTTVTChecked = true;
      $("#inputSearch").focus();
    } catch (error) {
    } finally {
      this.$root.showLoading(false);
    }
  },
  computed: {
    haveData: function () {
      return !(this.kqTimKiem == null || this.kqTimKiem.length == 0);
    },
    disabledVeTinh: function () {
      return !this.checkboxVetinhChecked || !this.checkboxTTVTChecked;
    },
    viTriTuText: function () {
      if (this.loaiTraCuu == 1) return "Từ vị trí";
      else return "Từ đôi/sợi";
    },
    viTriDenText: function () {
      if (this.loaiTraCuu == 1) return "Đến vị trí";
      else return "Đến đôi/sợi";
    },
    //Nhập ký hiệu {0} cuối cần tra cứu
    placeHolderSearchText: function () {
      if (this.loaiTraCuu == 1) return "Nhập ký hiệu kết cuối cuối cần tra cứu";
      else return "Nhập ký hiệu cáp cuối cần tra cứu";
    },
    dsThueBaoFields: function () {
      return [
        {
          fieldName: "STT",
          headerText: "STT",
          allowSorting: false,
          width: 100,
          cellCssClass: "text-right",
        },
        {
          fieldName: "DOICAP_VT",
          headerText: "Vị trí",
          width: 100,
          cellCssClass: "text-right",
        },
        { fieldName: "MA_TB", headerText: "Số máy/account" },
        { fieldName: "MA_LT", headerText: "Mã ảo" },
        {
          fieldName: "TEN_TB",
          headerText: "Tên TB",
          cellCssClass: "text-wrap",
          width: 230,
        },
        { fieldName: "TEN_DVVT", headerText: "Dịch vụ VT" },
        { fieldName: "LOAIHINH_TB", headerText: "Loại hình" },
        {
          fieldName: "SDT_LH",
          headerText: "Điện thoại liên hệ",
          cellCssClass: "text-center",
        },
        {
          fieldName: "DIACHI_LD",
          headerText: "Địa chỉ LĐ",
          cellCssClass: "text-wrap",
          width: 350,
        },
        { fieldName: "TRANGTHAI_TB", headerText: "Trạng thái thuê bao" },
        { fieldName: "TRANGTHAI_DN", headerText: "Trạng thái đấu nối" },
      ];
    },
  },
  methods: {
    getDsTTVT: async function () {
      let rs = await this.$root.context.post(
        "/web-ecms/danhmuc/layDsTTVTTrongToken"
      );
      this.dsTTVT = rs.data;
    },
    getToKT: async function () {
      let rs = await this.$root.context.post("/web-ecms/danhmuc/layDsToKT", {
        id: $("#selectTTVT").val(),
      });
      this.dsToKT = rs.data;
    },
    getKqTK: async function () {
      if (this.capKetCuoiID != 0) {
        this.$root.showLoading(true);
        try {
          var loai_dt = $("#selectLoaiTraCuu").val() == 1 ? 0 : 1;
          let rs = await this.$root.context.post(
            "/web-cabman/thueBaoCapKetCuoi/dsthuebaocapketcuoi",
            {
              doituong_id: this.capKetCuoiID,
              loaidt_id: loai_dt,
              vitri_bd: $("#inputTuViTri").val(),
              vitri_kt: $("#inputDenViTri").val(),
            }
          );
          this.kqTimKiem = rs.data;
        } catch (error) {
        } finally {
          this.$root.showLoading(false);
        }
      } else {
        this.$root.toastError(
          "Vui lòng chọn cáp hoặc kết cuối trước khi tra cứu"
        );
      }
    },
    selectLoaiTraCuu_change: async function (args) {
      this.txtSearch = null;
      this.kqGoiNhac = [];
    },
    inputSearch_change: async function (args) {
      if (!(args.item == undefined || args.item == null)) {
        if (args.itemData != null) {
          if (args.itemData.LOAI == 1) {
            await this.getKetCuoi(args.itemData.SEARCH_QUERY);
          } else {
            await this.getCap(args.itemData.SEARCH_QUERY);
          }
          await this.getKqTK();
        } else {
          $("#inputDenViTri").val(1);
        }
      }
    },
    inputSearch_filtering: async function (args) {
      this.$refs.inputSearch.showSpinner();
      if (args.text.length >= 3) {
        this.kqGoiNhac = await this.getGoiNhacTimKiem(args.text);
      }
      args.updateData(
        this.kqGoiNhac.filter((x) =>
          x.OBJ_NAME.toString()
            .toLowerCase()
            .includes(args.text.toString().trim().toLowerCase())
        )
      );
      if (args.text.length < 3) {
        if (this.kqGoiNhac.length == 0) {
          this.$refs.inputSearch.hidePopup();
        }
      }
    },
    getKqGoiNhac: async function () {
      this.kqTimKiem = [];
      this.capKetCuoiID = 0;
      let txt =
        $("#inputSearch").val().toLowerCase().trim() +
        "|PHANLOAIKC_ID=" +
        $("#selectNhom").val() +
        ",TOQL_ID=" +
        $("#selectToKT").val() +
        ",TRAMTBI_ID=";
      if (!this.disabledVeTinh) {
        txt += $("#selectVeTinh").val();
      } else {
        txt += "NULL";
      }
      // let rs = await this.$root.context.post(
      //   "/web-cabman/thueBaoCapKetCuoi/goiNhacTimKiem",
      //   { searchQuery: txt, types: $("#selectLoaiTraCuu").val() }
      // );
      // this.kqGoiNhac = rs.data;
      this.kqGoiNhac = await this.goiNhacTimKiem(txt); // rs.data;
      var loai_dt = $("#selectLoaiTraCuu").val() == 1 ? 0 : 1;
      if (this.kqGoiNhac.length > 0) {
        $("#inputTuViTri").val(1);
        if (loai_dt == 0) {
          //Ket cuoi : doituong = KETCUOI_ID
          await this.getKetCuoi(this.kqGoiNhac[0].SEARCH_QUERY);
        } else {
          //Cap doituong = S2-HUVUG01B02;
          await this.getCap(this.kqGoiNhac[0].SEARCH_QUERY);
        }
      } else {
        let msg = "Không tìm thấy thông tin ";
        if (loai_dt == 0) msg += "kết cuối";
        else msg += "cáp";
        msg += ". Vui lòng kiểm tra lại thông tin tra cứu";
        this.$root.toastError(msg);
      }
    },
    goiNhacTimKiem: async function (searchQuery) {
      let rs = await this.$root.context.post(
        "/web-cabman/thueBaoCapKetCuoi/goiNhacTimKiem",
        { searchQuery: searchQuery, types: $("#selectLoaiTraCuu").val() }
      );
      let v = [];
      if (!(rs.data == null || rs.data == undefined)) {
        rs.data.forEach((r) => {
          let ck = v.find((x) => x.SEARCH_QUERY == r.SEARCH_QUERY);
          if (ck == null || ck == undefined) {
            v.push(r);
          }
        });
      }
      return v;
    },
    getGoiNhacTimKiem: async function (text) {
      this.kqTimKiem = [];
      this.capKetCuoiID = 0;
      let txt =
        text.toString().toLowerCase().trim() +
        "|PHANLOAIKC_ID=" +
        $("#selectNhom").val() +
        ",TOQL_ID=" +
        $("#selectToKT").val() +
        ",TRAMTBI_ID=";
      if (!this.disabledVeTinh) {
        txt += $("#selectVeTinh").val();
      } else {
        txt += "NULL";
      }
      // let rs = await this.$root.context.post(
      //   "/web-cabman/thueBaoCapKetCuoi/goiNhacTimKiem",
      //   { searchQuery: txt, types: $("#selectLoaiTraCuu").val() }
      // );
      // return rs.data;
      return await this.goiNhacTimKiem(txt);
    },
    getKetCuoi: async function (stringValue) {
      var itemID = stringValue.split("|")[1];
      let rs = await this.$root.context.post(
        "/web-cabman/thueBaoCapKetCuoi/layKetCuoiTheoID",
        {
          id: itemID,
        }
      );
      if (rs.data != null) {
        $("#inputDenViTri").val(rs.data.TONGSO_DD);
        this.capKetCuoiID = rs.data.KETCUOI_ID;
      }
    },
    getCap: async function (stringValue) {
      var itemID = stringValue.split("|")[1];
      let rs = await this.$root.context.post(
        "/web-cabman/thueBaoCapKetCuoi/layCapTheoID",
        {
          id: itemID,
        }
      );
      if (rs.data != null) {
        $("#inputDenViTri").val(rs.data.SODOIDAY);
        this.capKetCuoiID = rs.data.CAP_ID;
      }
    },
    getDsVeTinh: async function () {
      let p = $("#selectToKT").val();
      if(p == null || p == undefined)
        p = 0;
      let rs = await this.$root.context.post("/web-ecms/danhmuc/layDsVeTinh", {
        id: p,
      });
      if (!(rs.data == null || rs.data.length == 0)) this.dsVeTinh = rs.data;
      else this.dsVeTinh = [{ DONVI_ID: 0, TEN_DV: "(Lựa chọn ...)" }];
    },
    selectTTVT_changed: async function () {
      this.$root.showLoading(true);
      try {
        await this.getToKT();
        await this.getDsVeTinh();
      } catch (error) {
      } finally {
        this.$root.showLoading(false);
      }
    },
    selectToKT_changed: async function () {
      this.$root.showLoading(true);
      try {
        await this.getDsVeTinh();
      } catch (error) {
      } finally {
        this.$root.showLoading(false);
      }
    },
    goinhacKQ_click: async function () {
      this.$root.showLoading(true);
      try {
        await this.getKqGoiNhac();
      } catch (error) {
      } finally {
        this.$root.showLoading(false);
      }
    },
    xuatExcel: function () {
      if (!(this.kqTimKiem == null || this.kqTimKiem.length == 0)) {
        this.dsThuocTinh = this.getExcelColumns();
        this.$bvModal.show("modal-xuat-dulieu");
      } else {
        this.$root.toastError("Không có dữ liệu tra cứu để xuất file");
      }
    },
    getColumnWidth: function (value) {
      let w = 100;
      if (!(value == undefined || value == "")) w = value;
      return w;
    },
    getExcelColumns: function () {
      let rs = [];
      this.dsThueBaoFields.forEach((element) => {
        rs.push(element.fieldName);
      });
      return rs;
    },
    btnTraCuuThueBaoChiTiet_Click: function (args) {
      this.$refs.modalTraCuuChiTiet.show();
    },
    modalTraCuuChiTiet_Show: async function (args) {
      await this.$refs.formTraCuuChiTiet.bindDataExcel(this.kqTimKiem);
    },
  },
};
</script>
<style>
.autocomplete-item {
  line-height: 1.2;
}
.autocomplete-item div {
  padding-top: 0px;
  padding-bottom: 0px;
}
.autocomplete-item,
.autocomplete-item .autocomplete-item-text,
.autocomplete-item .autocomplete-item-info {
  padding-top: 3px;
  padding-bottom: 3px;
}
.autocomplete-item .autocomplete-item-info {
  font-size: 90%;
}
.autocomplete-item .autocomplete-item-text {
  font-weight: bold;
}
.autocomplete-item .autocomplete-item-image {
  font-size: 150%;
  color: rgb(224, 0, 131);
  text-align: center;
}
#inputSearch {
  height: 30px;
}
</style>
