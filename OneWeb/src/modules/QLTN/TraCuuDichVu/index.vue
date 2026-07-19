<template src="./index.template.html"></template>
<style src="./style.scss" scoped></style>
<script>
import breadcrumb from "@/components/breadcrumb";
import downloadexcel from "vue-json-excel";
import { export_json_to_excel } from "./Export2Excel";

export default {
  components: { breadcrumb, downloadexcel },
  data: function() {
    return {
      header: {
        title: "TRA CỨU SỬ DỤNG DỊCH VỤ",
        list: [
          { name: "Tra cứu", link: { name: "Ui.cards" } },
          {
            name: "Quản lý thu nợ",
            link: { name: "Ui.buttons" }
          },
          {
            name: "Tra cứu dịch vụ",
            link: { name: "Ui.buttons" }
          }
        ]
      },
      dsDichVu: [],
      searchForm: {
        idDichVu: "",
        maTb: "",
        tenTb: "",
        diaChiTb: "",
        maKh: "",
        tenKh: "",
        diaChiKh: "",
        diaChiLd: ""
      },
      tableData: {
        dsDVGiaTang: [],
        dsDVKhac: [],
        dsLSGiaTang: [],
        dsLSKhac: [],
        dsDBModal: [],
        tableDvKhacColumns: []
      },
      indexFocus: 0,
      dataItem: null,
      loaiId: 1,
      dbLength: 0
    };
  },
  validations: {},
  watch: {},
  created: async function() {
    this.dsDichVuDropDown();
    this.loaiId = this.$route.query.thanhtoan == 1 ? 2 : 1;
    // console.log(this.loaiId);
    this.initColums();
  },
  async mounted() {
    this.$refs.maTb.focus();
  },
  methods: {
    initColums() {
      // if (this.loaiId == 1) {
      //   this.tableData.tableDvKhacColumns = [
      //     {
      //       fieldName: "TEN_DVKHAC",
      //       headerText: "Tên dịch vụ",
      //       allowFiltering: true
      //     },
      //     {
      //       fieldName: "NGAYDK",
      //       headerText: "Ngày sử dụng",
      //       allowFiltering: true
      //     },
      //     { fieldName: "GHICHU", headerText: "Ghi chú", allowFiltering: true }
      //   ];
      // } else {
        this.tableData.tableDvKhacColumns = [
          {
            fieldName: "MA_TB",
            headerText: "Mã TB",
            allowFiltering: true,
            allowSorting:false
          },
          {
            fieldName: "MA_TT",
            headerText: "Mã TT",
            allowFiltering: true,
            allowSorting:false
          },
          {
            fieldName: "TEN_TT",
            headerText: "Tên TT",
            allowFiltering: true,
            allowSorting:false
          },
          {
            fieldName: "DIACHI_TT",
            headerText: "Địa chỉ TT",
            allowFiltering: true,
            allowSorting:false
          },
          {
            fieldName: "DIACHI_CT",
            headerText: "Địa chỉ VAT",
            allowFiltering: true,
            allowSorting:false
          },
          {
            fieldName: "TEN_DVKHAC",
            headerText: "Tên dịch vụ",
            allowFiltering: true,
            allowSorting:false
          },
          {
            fieldName: "NGAYDK",
            headerText: "Ngày sử dụng",
            allowFiltering: true,
            allowSorting:false
          },
          { fieldName: "GHICHU", headerText: "Ghi chú", allowFiltering: true, allowSorting:false }
        ];
      // }
    },
    doSearch: function(event) {
      if (this.searchForm.maTb) {
        this.getDanhBa();
      } else {
        this.resetSearchForm();
        this.$toast.warning("Bạn chưa nhập thông tin tìm kiếm.");
      }
    },
    onView() {
      if(this.loaiId == 1) {
        this.doSearch();
      } else {
        this.renderDataThanhToan();
      }
    },
    dsDichVuGiaTang: async function(thueBaoId) {
      try {
        var rs = await this.$root.context.get(
          `web-thuno/api/thu-no/tra-cuu-danh-ba/su-dung-dich-vu-gia-tang/thue-bao/${thueBaoId}`
        );
        if (rs && rs.data) {
          this.tableData.dsDVGiaTang = rs.data;
        }
      } catch (error) {
        console.log(error);
      } finally {
      }
    },
    dsDichVuGiaTangByDsTB: async function() {
      let tbArr = this.searchForm.maTb.trim().split(",");
      try {
        var rs = await this.$root.context.get(
          `web-thuno/api/thu-no/tra-cuu-danh-ba/su-dung-dich-vu-gia-tang/ds-ma`,
          {
            dsMa: tbArr.join(),
            loaiId: 1
          }
        );
        if (rs && rs.data) {
          this.tableData.dsDVGiaTang = rs.data;
          rs.data.length > 0 &&
            this.renderData(rs.data[0]);
        }
      } catch (error) {
        console.log(error);
      } finally {
      }
    },
    dsDichVuKhac: async function(thueBaoId) {
      try {
        var rs = await this.$root.context.get(
          `web-thuno/api/thu-no/tra-cuu-danh-ba/su-dung-dich-vu-khac`,
          {
            danhBaId: thueBaoId,
            loaiId: 1
          }
        );
        if (rs && rs.data) {
          this.tableData.dsDVKhac = rs.data;
        }
      } catch (error) {
        console.log(error);
      } finally {
      }
    },
    dsDichVuKhacByDsTB: async function() {
      let tbArr = this.searchForm.maTb.trim().split(",");
      try {
        var rs = await this.$root.context.get(
          `/web-thuno/api/thu-no/tra-cuu-danh-ba/su-dung-dich-vu-khac/ds-ma`,
          {
            dsMa: tbArr.join(),
            loaiId: 1
          }
        );
        if (rs && rs.data) {
          this.tableData.dsDVKhac = rs.data;
          rs.data.length > 0 && this.loaiId == 2 &&
          this.renderData(rs.data[0]);
        }
      } catch (error) {
        console.log(error);
      } finally {
      }
    },
    dsBDDichVuGiaTang: async function(thueBaoId) {
      try {
        var rs = await this.$root.context.get(
          `web-thuno/api/thu-no/tra-cuu-danh-ba/bien-dong-dich-vu-gia-tang/thue-bao/${thueBaoId}`
        );
        if (rs && rs.data) {
          this.tableData.dsBDDichVuGiaTang = rs.data;
        }
      } catch (error) {
        console.log(error);
      } finally {
      }
    },
    dsBDDichVuGiaTangByDsTB: async function() {
      let tbArr = this.searchForm.maTb.trim().split(",");
      try {
        var rs = await this.$root.context.get(
          `/web-thuno/api/thu-no/tra-cuu-danh-ba/bien-dong-dich-vu-gia-tang/ds-ma`,
          {
            dsMa: tbArr.join()
          }
        );
        if (rs && rs.data) {
          this.tableData.dsBDDichVuGiaTang = rs.data;
        }
      } catch (error) {
        console.log(error);
      } finally {
      }
    },
    dsBDDichVuKhac: async function(thueBaoId) {
      try {
        var rs = await this.$root.context.get(
          `web-thuno/api/thu-no/tra-cuu-danh-ba/bien-dong-dich-vu-khac/${thueBaoId}`
        );
        if (rs && rs.data) {
          this.tableData.dsBDDichVuKhac = rs.data;
        }
      } catch (error) {
        console.log(error);
      } finally {
      }
    },
    dsBDDichVuKhacByDsTB: async function() {
      let tbArr = this.searchForm.maTb.trim().split(",");
      try {
        var rs = await this.$root.context.get(
          `/web-thuno/api/thu-no/tra-cuu-danh-ba/bien-dong-dich-vu-khac/ds-ma`,
          {
            dsMa: tbArr.join()
          }
        );
        if (rs && rs.data) {
          this.tableData.dsBDDichVuKhac = rs.data;
        }
      } catch (error) {
        console.log(error);
      } finally {
      }
    },
    dsDichVuDropDown: async function() {
      try {
        var rs = await this.$root.context.get(
          "/web-thuno/api/thu-no/common/dich-vu-vien-thong"
        );
        this.dsDichVu = rs.data;
        if (rs.data) {
          this.searchForm.idDichVu = rs.data[0].DICHVUVT_ID;
        }
      } catch (error) {
      } finally {
      }
    },
    getDanhBa: async function() {
      try {
        var rs = await this.$root.context.get(
          "/web-thuno/api/thu-no/tra-cuu-danh-ba/danh-ba-thue-bao-theo-ma",
          { maTB: this.searchForm.maTb }
        );
        rs.data && this.mappingSearchForm(rs.data);
      } catch (error) {
        console.log(error);
      } finally {
      }
    },
    showMsgConfirm() {
      this.$bvModal
        .msgBoxOk("Không tìm thấy thông tin thuê bao vừa nhập!", {
          title: "Thông báo",
          size: "sm",
          buttonSize: "md",
          okVariant: "primary",
          headerClass: "p-2 border-bottom-0",
          footerClass: "p-2 border-top-0 justify-content-center",
          centered: true,
          noCloseOnBackdrop: true
        })
        .then(value => {
          this.resetSearchForm();
        })
        .catch(err => {});
    },
    mappingSearchForm(data) {
      let dataLength = data.length;
      this.dbLength = dataLength;
      if (dataLength > 0) {
        if (dataLength == 1) {
          if (this.loaiId == 1) {
            this.renderData(data[0]);
          } else {
            this.renderDataThanhToan();
          }
        } else {
          this.tableData.dsDBModal = data;
          this.dataItem = data[0];
          this.$bvModal.show("bv-modal-example");
        }
      } else {
        this.showMsgConfirm();
      }
    },
    resetSearchForm() {
      this.searchForm.tenTb = "";
      this.searchForm.diaChiTb = "";
      this.searchForm.maKh = "";
      this.searchForm.tenKh = "";
      this.searchForm.diaChiKh = "";
      this.searchForm.diaChiLd = "";

      this.tableData.dsDVGiaTang = [];
      this.tableData.dsDVKhac = [];
      this.tableData.dsBDDichVuGiaTang = [];
      this.tableData.dsBDDichVuKhac = [];
    },
    grid1_selectedRowChanged(dataItem) {
      this.dataItem = dataItem;
    },
    confirmChose() {
      this.$bvModal.hide("bv-modal-example");
      this.renderData(this.dataItem);
    },
    renderData(contact) {
      // console.log(contact);
      this.searchForm.tenTb = contact.TEN_TT;
      this.searchForm.diaChiTb = contact.DIACHI_TT;
      this.searchForm.maKh = contact.MA_KH;
      this.searchForm.tenKh = contact.TEN_KH;
      this.searchForm.diaChiKh = contact.DIACHI_KH;
      this.searchForm.diaChiLd = contact.DIACHI_LD;
      this.searchForm.idDichVu = contact.DICHVUVT_ID || contact.DVVT_ID;
      const { THUEBAO_ID } = contact;
      if (this.loaiId == 1 && THUEBAO_ID) {
        this.dsDichVuGiaTang(THUEBAO_ID);
        // this.dsDichVuKhac(THUEBAO_ID);
        this.dsDichVuKhacByDsTB();
        this.dsBDDichVuGiaTang(THUEBAO_ID);
        this.dsBDDichVuKhac(THUEBAO_ID);
      }
    },
    renderDataThanhToan() {
      this.dsDichVuGiaTangByDsTB();
      this.dsDichVuKhacByDsTB();
      this.dsBDDichVuGiaTangByDsTB();
      this.dsBDDichVuKhacByDsTB();
    },
    exportExcel() {
      if(this.tableData.dsDVKhac.length == 0) {
        this.$toast.warning("Không có dữ liệu để xuất Excel !");
        return;
      }
      let tHeader;
      let filterVal;
      // if (this.loaiId == 1) {
      //   tHeader = ["Tên dịch vụ", "Ngày sử dụng", "Ghi chú"];
      //   filterVal = ["TEN_DVKHAC","NGAYDK", "GHICHU"];
      // } else {
        tHeader = ["Mã TB", "Mã TT", "Tên TT", "Địa chỉ TT", "Địa chỉ VAT", "Tên dịch vụ", "Ngày sử dụng", "Ghi chú"];
        filterVal = ["MA_TB","MA_TT","TEN_TT","DIACHI_TT","DIACHI_CT","TEN_DVKHAC","NGAYDK", "GHICHU"];
      // }
      const data = this.formatJson(filterVal, this.tableData.dsDVKhac);
      export_json_to_excel({
        header: tHeader,
        data,
        filename: "export.xlsx",
        sheetname: "Danh sách sử dụng dịch vụ khác"
        // autoWidth: this.autoWidth,
        // bookType: this.bookType
      });
    },
    formatJson(filterVal, jsonData) {
      return jsonData.map(v =>
        filterVal.map(j => {
          return v[j];
        })
      );
    }
  }
};
</script>
