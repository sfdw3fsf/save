<template>
  <b-modal
    id="modal-maTBdocfile"
    size="lg"
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
          <span class="icon one-notepad"></span>Đọc file danh sách xử lý nợ
          (Kiểu XL: Mã thuê bao)
        </div>
        <div
          class="close -ap icon-close"
          data-dismiss="modal"
          @click="close()"
        ></div>
      </div>
      <div class="list-actions-top">
        <ul class="list">
          <li @click="DanhSach()">
            <a href="javascript:;">
              <span class="icon one-reload1"></span> Danh sách
            </a>
          </li>
          <li @click="ChapNhan()" :class="{ disabled: sources.length <= 0 }">
            <a href="javascript:;">
              <span class="icon nc-icon-glyph ui-1_check-circle-08"></span> Chấp
              nhận
            </a>
          </li>
          <li @click="XuatExcel()" :class="{ disabled: sources.length <= 0 }">
            <a href="javascript:;">
              <span class="icon one-excel"></span> Xuất Excel
            </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="row">
            <div class="note red mart7">
              <span class="one-alert text-warning f24 inline vcenter"></span>
              File excel gồm các trường: STT, MA_XLN, MA_TB, MA_NV, TT_HS. Viết
              hoa, không dấu, bỏ dòng tiêu đề
            </div>
          </div>
          <div class="info-row">
            <div class="key w90">Tên File <span class="required">*</span></div>
            <div class="value">
              <div class="input-more-button">
                <label for="upload" class="btn">
                  <span class="-ap icon-more_horiz"></span>
                  <input
                    ref="fileInput"
                    type="file"
                    id="upload"
                    style="display: none"
                    @change="onChangeFile"
                    accept=".xls, .xlsx"
                    @click="$refs.fileInput.value = null"
                  />
                </label>
                <input
                  autofocus
                  ref="duongDan"
                  v-model="duongDan"
                  type="text"
                  class="form-control highlight"
                />
              </div>
            </div>
          </div>
        </div>
        <div class="box-form">
          <div class="legend-title">
            <div class="pull-left">Danh sách xử lý nợ</div>
            <div class="pull-right red">00:00:00</div>
            <div class="clearfix"></div>
          </div>
          <div class="nav tabs tab-over">
            <a href="#tabDS" data-toggle="tab" class="active"> Danh sách </a>
            <a href="#tabLoi" data-toggle="tab"> Lỗi </a>
            <a href="#tabNV" data-toggle="tab"> Nhân viên </a>
          </div>
          <div class="tab-content">
            <div class="tab-pane active" id="tabDS">
              <DataGrid
                :columns="[
                  {
                    fieldName: 'STT',
                    headerText: 'STT',
                    allowFiltering: true,
                  },
                  {
                    fieldName: 'MA_XLN',
                    headerText: 'Mã thanh toán',
                    allowFiltering: true,
                  },
                  {
                    fieldName: 'MA_TB',
                    headerText: 'Số máy/Acc',
                    allowFiltering: true,
                  },
                  {
                    fieldName: 'MA_NV',
                    headerText: 'Mã nhân viên',
                    allowFiltering: true,
                  },
                  {
                    fieldName: 'TT_HS',
                    headerText: 'Trạng thái HS',
                    allowFiltering: true,
                  },
                ]"
                :dataSource="sources"
                :showFilter="true"
                ref="tabIn"
                :allowPaging="true"
                :enablePagingServer="false"
              ></DataGrid>
            </div>
            <div class="tab-pane" id="tabLoi">
              <DataGrid
                :columns="[
                  {
                    fieldName: 'STT',
                    headerText: 'STT',
                    allowFiltering: true,
                  },
                  {
                    fieldName: 'MA_XLN',
                    headerText: 'Mã thanh toán',
                    allowFiltering: true,
                  },
                  {
                    fieldName: 'MA_TB',
                    headerText: 'Số máy/Acc',
                    allowFiltering: true,
                  },
                  {
                    fieldName: 'MA_NV',
                    headerText: 'Mã nhân viên',
                    allowFiltering: true,
                  },
                  {
                    fieldName: 'TT_HS',
                    headerText: 'Trạng thái HS',
                    allowFiltering: true,
                  },
                  {
                    fieldName: 'TRANGTHAI',
                    headerText: 'Trạng thái',
                    allowFiltering: true,
                  },
                ]"
                :dataSource="dsLoi"
                :showFilter="true"
                ref="tabLoi"
                :allowPaging="true"
                :enablePagingServer="false"
              ></DataGrid>
            </div>
            <div class="tab-pane" id="tabNV">
              <DataGrid
                :columns="[
                  {
                    fieldName: 'MA_NV',
                    headerText: 'Mã nhân viên',
                    allowFiltering: true,
                  },
                  {
                    fieldName: 'TEN_NV',
                    headerText: 'Tên nhân viên',
                    allowFiltering: true,
                  },
                ]"
                :dataSource="dsNhanVien"
                :showFilter="true"
                ref="tabNV"
                :allowPaging="true"
                :enablePagingServer="false"
              ></DataGrid>
            </div>
          </div>
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
  name: "DocFileByMaTB",
  mounted() {},
  props: {
    kyCuoc: String,
    typeXuly: {
      type: String,
      default: "1",
    },
  },
  data() {
    return {
      sources: [],
      isTab: false,
      duongDan: "",
      dsLoi: [],
      dsNhanVien: [],
    };
  },
  watch: {},
  methods: {
    close() {
      this.$bvModal.hide("modal-maTBdocfile");
    },
    openModal() {
    },
    async onChangeFile(e) {
      const files = e.target.files;
      this.duongDan = e.target.files[0].name;
      this.sources = [];
      this.dsLoi = [];
      this.dsNhanVien = [];
      if (!files.length) {
        return;
      } else if (!/\.(xls|xlsx)$/.test(files[0].name.toLowerCase())) {
        return this.$root.toastError("File excel không đúng định dạng!");
      }
      const fileReader = new FileReader();
      fileReader.onload = async (ev) => {
        try {
          const data = ev.target.result;
          const XLSX = xlsx;
          const workbook = XLSX.read(data, {
            type: "binary",
          });
          const wsname = workbook.SheetNames[0];
          const ws = XLSX.utils.sheet_to_json(workbook.Sheets[wsname]);
          const excellist = [];
          let dsDanhSach = [];
          let dsMaTT = [];
          let dsDuplicateMaTT = [];
          for (let i = 0; i < ws.length; i++) {
            excellist.push(ws[i]);
            dsMaTT.push(ws[i].MA_XLN);
            dsDanhSach.push({
              STT: ws[i].STT,
              MA_XLN: ws[i].MA_XLN,
              MA_TB: ws[i].MA_TB,
              MA_NV: ws[i].MA_NV,
              TT_HS: ws[i].TT_HS,
            });
          }
          const arrayCol = Object.keys(excellist[0]);
          if (arrayCol.length != 5) {
            this.$root.toastError(
              "File excel gồm các trường: STT, MA_XLN, MA_TB, MA_NV, TT_HS!"
            );
            return;
          }
          if (
            arrayCol[0] != "STT" ||
            arrayCol[1] != "MA_XLN" ||
            arrayCol[2] != "MA_TB" ||
            arrayCol[3] != "MA_NV" ||
            arrayCol[4] != "TT_HS"
          ) {
            this.$root.toastError(
              "Tên cột trong file không đúng định dạng: STT, MA_XLN, MA_TB, MA_NV, TT_HS!"
            );
            return;
          }
          dsMaTT.forEach((element) => {
            if (
              dsMaTT.indexOf(element) != dsMaTT.lastIndexOf(element) &&
              !dsDuplicateMaTT.includes(element)
            )
              dsDuplicateMaTT.push(element);
          });
          if (dsDuplicateMaTT.length > 0) {
            dsDuplicateMaTT.forEach((element, index) => {
              this.dsLoi.push({
                STT: ++index,
                MA_XLN: element,
                TRANGTHAI: "Mã xử lý nợ bị lặp lại",
              });
              return;
            });
          }

          const result = await this.kiemTraFile(dsDanhSach);
          if (result) {
            this.sources = dsDanhSach;
          }
        } catch (error) {
          return this.$root.toastError("Kiểm tra dữ liệu file đầu vào!");
        }
      };
      fileReader.readAsBinaryString(files[0]);
    },
    async kiemTraFile(ds) {
      const dsFile = ds.map((item) => ({
        stt: item.STT,
        maXLN: item.MA_XLN,
        maTB: item.MA_TB,
        maNVPL: item.MA_NV,
        ttHS: item.TT_HS,
      }));
      const data = {
        kyCuoc: this.kyCuoc,
        kieuXL: 3,
        file: dsFile,
      };
      this.loading(true);
      try {
        const response = await API.kiemTraFileXLN(this.axios, data);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data.length > 0
        ) {
          this.dsLoi = response.data.data.map(item=>({...item,MA_NV:item.MA_NV_PL}));
          return false;
        } else {
          this.getDSNhanVien(data);
          return true;
        }
      } catch (error) {
        return false;
      } finally {
        this.loading(false);
      }
    },
    async getDSNhanVien(data) {
      try {
        const response = await API.getDSNhanVien(this.axios, data);
        if (response.data.error_code === "BSS-00000000") {
          this.dsNhanVien = response.data.data;
        }
      } catch (error) {
        this.dsNhanVien = [];
      }
    },
    DanhSach() {
      this.$confirm(`Bạn muốn mở file ?`, "Xác nhận", {
        confirmButtonText: "Đồng ý",
      })
        .then(() => {
          $("#upload").click();
        })
        .catch(() => {});
    },
    ChapNhan() {
      if (this.dsLoi.length > 0) {
        this.$root.toastError("File excel đang có lỗi, mời thử lại!");
        return;
      }
      const dsma_xln = this.sources.map(item=>item.MA_XLN)
      const result = dsma_xln.toString();
      this.$emit("chapNhan", result);
      this.$bvModal.hide("modal-maTBdocfile");
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
        xlsx.writeFile(wb, "mau.xlsx");
      } catch (error) {
      } finally {
        this.loading(false);
      }
    },
    resetData() {
      this.sources = [];
      this.dsLoi = [];
      this.dsNhanVien = [];
      this.duongDan = "";
    },
  },
};
</script>
