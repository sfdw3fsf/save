<template>
  <b-modal
    id="modal-chitietlantaodulieu"
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
          <span class="icon one-notepad"></span>Chi tiết các lần tạo dữ liệu
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
            <a href="javascript:;" v-on:click.prevent="XuatExcel">
              <span class="icon one-download"></span>Xuất File
            </a>
          </li>
          <li>
            <a href="javascript:;" v-on:click.prevent="LSNo">
              <span class="icon one-file-edit3"></span>LS nợ
            </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="info-row">
          <div class="key w60">Đơn vị</div>
          <div class="value">
            <div class="select-custom">
              <select v-model="idLoaiNV" name="" id="" class="form-control">
                <option value="1">Tổ thu nợ</option>
                <option value="2">Tổ thanh lý pháp lý</option>
              </select>
            </div>
          </div>
          <div class="value w60 text-main nowrap fw6 padt7">
            <span class="icon one-alert text-warning inline f20 vcenter"></span>
            Nhấn phím A để mở rộng, Z thu gọn group
          </div>
        </div>
        <div class="box-form">
          <div class="legend-title">Danh sách khách hàng</div>
          <DataGrid
            :columns="fieldsRender"
            :dataSource="sources"
            :showFilter="true"
            ref="danhsach"
            @selectedRowChanged="KH_selectedRowChanged"
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
  name: "PopupChiTietLanTaoDuLieu",
  mounted() {},
  props: {
    data: {
      type: Object,
      default: {},
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
        // {
        //   fieldName: "MA_TT",
        //   headerText: 'Nhân viên TN',
        //   allowFiltering: false,
        //   isGroupedColumn: true
        // },
        // {
        //   fieldName: "MA_TB",
        //   headerText: "Nhân viên PL",
        //   allowFiltering: true,
        //   // cellCssClass: "text-right",
        // },
        {
          fieldName: "MA_XLN",
          headerText: "Mã XLN",
          allowFiltering: true,
        },
        {
          fieldName: "MA_TT",
          headerText: "Mã TT",
          allowFiltering: true,
        },
        {
          fieldName: "MA_KH",
          headerText: "Mã KH",
          allowFiltering: true,
        },
        {
          fieldName: "MATB_DD",
          headerText: "Mã đại diện",
          allowFiltering: true,
        },
        {
          fieldName: "TIENNO", //TONGNO
          headerText: "Tổng nợ",
          allowFiltering: true,
          type:'number',
          format:'N0',
          cssClass: 'text-danger'
        },
        {
          fieldName: "NO_DK",
          headerText: "Nợ đầu kỳ",
          allowFiltering: true,
          type:'number',
          format:'N0',
          cssClass: 'text-danger'
        },
        {
          fieldName: "NO_PS",
          headerText: "Nợ phát sinh",
          allowFiltering: true,
          type:'number',
          format:'N0',
          cssClass: 'text-primary'
        },
        {
          fieldName: "TEN_KH",
          headerText: "Tên khách hàng",
          allowFiltering: true,
        },
        {
          fieldName: "DIACHI_KH",
          headerText: "Địa chỉ khách hàng",
          allowFiltering: true,
        },
        {
          fieldName: "DIACHI_BC",
          headerText: "Địa chỉ báo cước",
          allowFiltering: true,
        },
        {
          fieldName: "SO_THANGNO",
          headerText: "Số tháng nợ",
          allowFiltering: true,
        },
        {
          fieldName: "KIEUNO",
          headerText: "Kiểu nợ",
          allowFiltering: true,
        },
      ],
      sources: [],
      idLoaiNV: "",
      fieldsRender: [],
      itemSelected: null,
    };
  },
  watch: {
    idLoaiNV: {
      handler: function (newVal) {
        if (newVal == "1") {
          const fieldAdds = [
            {
              fieldName: "NHANVIEN_TN",
              headerText: "Nhân viên TN",
              allowFiltering: false,
              isGroupedColumn: true,
            },
            {
              fieldName: "NHANVIEN_PL",
              headerText: "Nhân viên PL",
              allowFiltering: true,
            },
          ];
          this.fieldsRender = [...fieldAdds, ...this.fields];
        } else if (newVal == "2") {
          const fieldAdds = [
            {
              fieldName: "NHANVIEN_PL",
              headerText: "Nhân viên PL",
              allowFiltering: false,
              isGroupedColumn: true,
            },
            {
              fieldName: "NHANVIEN_TN",
              headerText: "Nhân viên TN",
              allowFiltering: true,
            },
          ];
          this.fieldsRender = [...fieldAdds, ...this.fields];
        }
      },
    },
  },
  methods: {
    openModal() {
      // this.tenKH = this.data.TEN_TT.toUpperCase()
      this.idLoaiNV = "1";
      this.fetchDanhSach();
    },
    close() {
      this.$bvModal.hide("modal-chitietlantaodulieu");
    },
    async fetchDanhSach() {
      const data = this.data.LANTAO_ID
      this.loading(true);
      try {
        const response = await API.getDsDotLayDuLieu(this.axios, data);
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
    LSNo() {
      if (this.itemSelected == null) {
        this.$root.toastError("Chưa chọn khách hàng!");
        return;
      }
      this.$emit("sendData", this.itemSelected);
    },
    KH_selectedRowChanged(data) {
      this.itemSelected = data;
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
