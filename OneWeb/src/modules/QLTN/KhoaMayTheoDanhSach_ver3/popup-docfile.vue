<template>
  <b-modal id="modal-docfileKhoaMay" size="lg" hide-footer hide-header hide-header-close body-class="modal-body p-0"
    no-close-on-backdrop @hide="resetData">
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span>Đọc file khoá mở thuê bao
        </div>
        <div class="close -ap icon-close" data-dismiss="modal" @click="close()"></div>
      </div>
      <div class="list-actions-top">
        <ul class="list">
          <li @click="DocFile()">
            <a href="javascript:;">
              <span class="icon design_bullet-list-67 nc-icon-glyph"></span>
              Danh sách
            </a>
          </li>
          <li @click="ChapNhan()">
            <a href="javascript:;">
              <span class="icon ui-1_check-circle-08 nc-icon-glyph"></span> Chấp
              nhận
            </a>
          </li>
          <li @click="XuatExcel()">
            <a href="javascript:;">
              <span class="icon one-excel"></span> Xuất Excel
            </a>
          </li>
          <li @click="TaiFileMau()">
            <a href="javascript:;">
              <span class="icon one-file-download"></span> Tải File mẫu
            </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="note text-main">
            <span class="one-alert f20 text-warning"></span> File excel gồm 1
            trường: MA_TB. Chỉ khoá với thuê bao đang hoạt động bình thường
          </div>
          <div class="info-row">
            <div class="key w90">Tên File</div>
            <div class="value">
              <div class="input-more-button">
                <label for="upload" class="btn">
                  <span class="-ap icon-more_horiz"></span>
                  <input ref="fileInput" type="file" id="upload" style="display: none" @change="onLoadFile"
                    accept=".xls, .xlsx" @click="$refs.fileInput.value = null" />
                </label>
                <input autofocus ref="duongDan" v-model="duongDan" type="text" class="form-control highlight" />
              </div>
            </div>
          </div>
        </div>
        <div class="box-form">
          <div v-if="!checkLoi" class="legend-title">Danh sách thuê bao</div>
          <div v-if="checkLoi" class="legend-title red">
            Danh sách thuê bao lỗi
          </div>
          <DataGrid :columns="fields" :dataSource="dsLoi" :showFilter="true" ref="danhSach" :allowPaging="true"
            :enablePagingServer="false" />
        </div>
      </div>
    </div>
  </b-modal>
</template>
<style></style>
<script>
import xlsx from "xlsx";
import API from "../api/KhoaMayThueBaoNoCuoc";

export default {
  name: "PopupDocFileKhoaMay",
  props: ["kycuoc", "idDichVuVT", "chieuKhoa"],
  mounted() { },
  data() {
    return {
      fields: [
        {
          fieldName: "MA_TB",
          headerText: "Mã TB",
          allowFiltering: true,
        },
        {
          fieldName: "TRANGTHAI",
          headerText: "Trạng thái",
          allowFiltering: true,
        },
      ],
      duongDan: "",
      dsLoi: [],
      sourceFile: null,
      checkLoi: false,
    };
  },
  methods: {
    close() {
      this.$bvModal.hide("modal-docfileKhoaMay");
    },
    XuatExcel() {
      if (this.dsLoi.length <= 0) {
        this.$root.toastError("Chưa có danh sách để xuất Excel!");
        return;
      }
      this.loading(true);
      try {
        var dsach = xlsx.utils.json_to_sheet(this.dsLoi);
        var wb = xlsx.utils.book_new(); // make Workbook of Excel
        xlsx.utils.book_append_sheet(wb, dsach, "TB"); // sheetAName is name of Worksheet
        xlsx.writeFile(wb, "mau.xlsx");
      } catch (error) {
      } finally {
        this.loading(false);
      }
    },
    DocFile() {
      if (this.duongDan === "") {
        this.$root.toastError("Hãy chọn file dữ liệu!");
        return;
      }
      this.onChangeFile(this.sourceFile);
    },
    onLoadFile(e) {
      this.sourceFile = e;
      this.duongDan = e.target.files[0].name;
      this.dsLoi = [];
    },
    async onChangeFile(e) {
      const files = e.target.files;

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
          let dsMaTT = [];
          let dsDuplicateMaTT = [];
          // Edit data
          for (var i = 0; i < ws.length; i++) {
            excellist.push(ws[i]);
            dsMaTT.push(ws[i].MA_TB);
          }

          var propCount = 0;
          if (excellist && excellist.length > 0) {
            for (var prop in excellist[0]) {
              if (excellist[0].hasOwnProperty(prop)) propCount++;
            }
          }

          if (propCount != 1) {
            this.$root.toastError(
              "Số lượng cột phải = 1 cột, hiện đang có " + propCount + " cột!"
            );
            return;
          }

          if (!excellist[0].hasOwnProperty("MA_TB")) {
            this.$root.toastError("File cần có chính xác 1 cột MA_TB!");
            return;
          }
          dsMaTT.forEach((element) => {
            if (
              dsMaTT.indexOf(element) != dsMaTT.lastIndexOf(element) &&
              !dsDuplicateMaTT.includes(element)
            )
              dsDuplicateMaTT.push(element);
          });
          if (dsDuplicateMaTT.length === 0) {
            let isCheckAPI = false;
            let dsRequest = dsMaTT.map((item) => ({
              maTB: item,
              dichVuVTId: this.idDichVuVT,
            }));
            const result = await this.checkLoiMaTB(dsRequest);
            if (result.length > 0) {
              this.dsLoi = result;
              isCheckAPI = true;
            } else {
              this.dsLoi = dsMaTT.map((item) => ({
                MA_TB: item,
                TRANGTHAI: "ok",
              }));
            }
            this.checkLoi = isCheckAPI;
          } else {
            dsDuplicateMaTT.forEach((element) => {
              this.dsLoi.push({
                MA_TB: element,
                TRANGTHAI: "Thuê bao bị lặp lại",
              });
            });
            this.checkLoi = true;
          }
          //console.log("Read results", excellist); // At this point, you get an array containing objects that need to be processed
        } catch (e) {
          returnthis.$root.toastError("Kiểm tra dữ liệu file đầu vào!");
        }
      };
      fileReader.readAsBinaryString(files[0]);
    },
    async checkLoiMaTB(ds) {
      const data = {
        kyCuoc: this.kycuoc,
        kieuId: 7,
        ds: ds,
        chieuKhoaMo: this.chieuKhoa
      };
      this.loading(true);
      try {
        const response = await API.kiemtraFile(this.axios, data);
        if (response.data.error_code === "BSS-00000000") {
          return response.data.data || [];
        }
      } catch (error) {
        return [];
      } finally {
        this.loading(false);
      }
    },
    ChapNhan() {
      if (this.dsLoi.length === 0 || this.checkLoi) {
        this.$root.toastError("Chưa có danh sách thuê bao hợp lệ!");
        return;
      }
      this.$emit("sendData", {
        dsThueBao: this.dsLoi,
      });
      this.$bvModal.hide("modal-docfileKhoaMay");
    },
    resetData() {
      this.duongDan = "";
      this.dsLoi = [];
    },
    TaiFileMau() {
      this.loading(true);
      try {
        const temp = [{ MA_TB: "" }];
        var dsach = xlsx.utils.json_to_sheet(temp);
        var wb = xlsx.utils.book_new(); // make Workbook of Excel
        xlsx.utils.book_append_sheet(wb, dsach, "DS"); // sheetAName is name of Worksheet
        xlsx.writeFile(wb, "Bieumau_khoamaytheodanhsach.xlsx");
      } catch (error) {
      } finally {
        this.loading(false);
      }
    },
  },
};
</script>
