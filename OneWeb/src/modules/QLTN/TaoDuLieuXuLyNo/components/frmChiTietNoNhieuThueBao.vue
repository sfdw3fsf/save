<template>
  <b-modal
    id="modal-chitietnonhieuthuebao"
    size="xl"
    hide-footer
    hide-header
    hide-header-close
    body-class="modal-body p-0"
    no-close-on-backdrop
    @show="openModal"
    @hide="resetData"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span>Chi tiết thông tin nợ thuê bao
        </div>
        <div
          class="close -ap icon-close"
          data-dismiss="modal"
          @click="close()"
        ></div>
      </div>
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a href="javascript:;" v-on:click.prevent="openModal">
              <span class="icon design_bullet-list-67 nc-icon-glyph"></span>
              Danh sách
            </a>
          </li>
          <li @click="XuatExcel()">
            <a href="javascript:;">
              <span class="icon one-xlsx-import"></span> Xuất Excel
            </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title">Thông tin nợ khách hàng</div>
          <DataGrid
            :columns="fields"
            :dataSource="sources"
            :showFilter="true"
            ref="danhsach"
            :allowPaging="true" :enablePagingServer="false"
          />
        </div>
      </div>
    </div>
  </b-modal>
</template>
<style></style>
<script>
import xlsx from "xlsx";
import API from "../../api/TaoDuLieuXuLyNo";

export default {
  name: "PopupChiTietNoNhieuThueBao",
  mounted() {},
  props: {
    dsMaXLN: {
      type: Array,
      default: [],
    },
    kyCuoc: String,
    typeXuly: {
      type: String,
      default: "1",
    },
  },
  data() {
    return {
      fields: [
        {
          fieldName: "MA_TT",
          headerText: "Mã TT",
          allowFiltering: true,
        },
        {
          fieldName: "MA_TB",
          headerText: "Mã thuê bao",
          allowFiltering: true,
        },
        {
          fieldName: "TEN_KH",
          headerText: "Tên KH",
          allowFiltering: true,
        },
        {
          fieldName: "DIACHI_KH",
          headerText: "Địa chỉ KH",
          allowFiltering: true,
        },
        {
          fieldName: "TONGNO1",
          headerText: "Tổng nợ 1",
          allowFiltering: true,
          type:'number',
          format:'N0'
        },
        {
          fieldName: "TONGNO2",
          headerText: "Tổng nợ 2",
          allowFiltering: true,
          type:'number',
          format:'N0'
        },
        {
          fieldName: "TONGNO3",
          headerText: "Tổng nợ 3",
          allowFiltering: true,
          type:'number',
          format:'N0'
        },
        {
          fieldName: "TONGNO4",
          headerText: "Tiền nợ",
          allowFiltering: true,
          type:'number',
          format:'N0'
        },
        {
          fieldName: "TONGNO",
          headerText: "Tổng nợ",
          allowFiltering: true,
          type:'number',
          format:'N0',
          cssClass: 'text-danger'
        },
      ],
      sources: [],
    };
  },
  watch: {},
  methods: {
    openModal() {
      this.fetchDanhSach();
    },
    close() {
      this.$bvModal.hide("modal-chitietnonhieuthuebao");
    },
    async fetchDanhSach() {
      const params = {
        kyCuoc: this.kyCuoc, //20210301
        maXLN: this.dsMaXLN.toString(), //HPG-06-506987
        kieuXLId: this.typeXuly, // 1
      };
      this.loading(true);
      try {
        const response = await API.getChiTietNo(this.axios, params);
        if (response.data.error_code === "BSS-00000000") {
          this.sources = response.data.data;
        } else {
          this.sources = [];
          this.$root.toastError("Không tìm thấy dữ liêu, mời thử lại!");
        }
      } catch (error) {
      } finally {
        this.loading(false);
      }
    },
    XuatExcel() {
      if (this.sources.length <= 0) {
        this.$root.toastError("Chưa có danh sách để xuất Excel!");
        return;
      }
      this.loading(true);
      try {
        var dsach = xlsx.utils.json_to_sheet(this.sources);
        var wb = xlsx.utils.book_new(); // make Workbook of Excel
        xlsx.utils.book_append_sheet(wb, dsach, "TB"); // sheetAName is name of Worksheet
        // export Excel file
        xlsx.writeFile(wb, "mau.xlsx");
      } catch (error) {
      } finally {
        this.loading(false);
      }
    },
    resetData() {
      // this.sources = []
    },
  },
};
</script>
