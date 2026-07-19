<template>
  <b-modal
    id="modal-danhsachChuaPhanTuyen"
    size="xl"
    hide-footer
    hide-header
    hide-header-close
    body-class="modal-body p-0"
    no-close-on-backdrop
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span> Danh sách khách hàng chưa phân
          tuyến
        </div>
        <div
          class="close -ap icon-close"
          data-dismiss="modal"
          @click="close()"
        ></div>
      </div>
      <div class="list-actions-top">
        <ul class="list">
          <li @click="XuatExcel()">
            <a href="javascript:;">
              <span class="icon one-xlsx-import"></span> Xuất Excel
            </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <DataGrid
            :columns="fields"
            :dataSource="sources"
            :showFilter="true"
            :allowPaging="true" :enablePagingServer="false"
          ></DataGrid>
        </div>
      </div>
    </div>
  </b-modal>
</template>
<style></style>
<script>
import xlsx from "xlsx";

export default {
  name: "PopupDanhSachChuaPhanTuyen",
  props: {
    sources: Array,
  },
  mounted() {
    // console.log('sources',this.sources)
  },
  data() {
    return {
      fields: [
        {
          fieldName: "STT",
          headerText: "STT",
          allowFiltering: true,
          cellCssClass: "text-right",
          width: 80
        },
        {
          fieldName: "MA_TT",
          headerText: "Mã thanh toán",
          allowFiltering: true,
        },
        {
          fieldName: "MATB_DD",
          headerText: "Mã đại diện",
          allowFiltering: true,
        },
        {
          fieldName: "TEN_TT",
          headerText: "Tên thanh toán",
          allowFiltering: true,
        },
        {
          fieldName: "DIACHI_TT",
          headerText: "Địa chỉ thanh toán",
          allowFiltering: true,
        },
        {
          fieldName: "DIACHI_BC",
          headerText: "Địa chỉ báo cước",
          allowFiltering: true,
        },
        {
          fieldName: "SONHA_TT",
          headerText: "Số nhà",
          allowFiltering: true,
        },
        {
          fieldName: "TEN_NH",
          headerText: "Ngân hàng",
          allowFiltering: true,
        },
        {
          fieldName: "THUTU_IN",
          headerText: "Thứ tự in",
          allowFiltering: true,
          cellCssClass: "text-right",
        },
        {
          fieldName: "DOTGIAO_ID",
          headerText: "Đợt giao",
          allowFiltering: true,
          cellCssClass: "text-right",
        },
        {
          fieldName: "MAIN_GHEP",
          headerText: "Mã in ghép",
          allowFiltering: true,
        },
        {
          fieldName: "THANHTOAN_ID",
          headerText: "Mã phát hóa đơn",
          allowFiltering: true,
        },
      ],
    };
  },
  methods: {
    close() {
      this.$bvModal.hide("modal-danhsachChuaPhanTuyen");
    },
    XuatExcel() {
      if (this.sources <= 0) {
        this.$toast.warning("Chưa có danh sách để xuất Excel!");
        return;
      }
      this.loading(true);
      try {
        var dsach = xlsx.utils.json_to_sheet(this.sources);
        var wb = xlsx.utils.book_new(); // make Workbook of Excel
        xlsx.utils.book_append_sheet(wb, dsach, "DS"); // sheetAName is name of Worksheet
        xlsx.writeFile(wb, "mau.xlsx");
      } catch (error) {
      } finally {
        this.loading(false);
      }
    },
  },
};
</script>
