<template>
  <b-modal
    id="modal-docfilexoa"
    size="lg"
    hide-footer
    hide-header
    hide-header-close
    body-class="modal-body p-0"
    no-close-on-backdrop
    @hide="resetData"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span> Xóa các thuê bao đặc biệt trong
          tháng
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
            <a href="#" v-on:click.prevent="DocFile" title="Hiển thị danh sách">
              <span class="icon design_bullet-list-67 nc-icon-glyph"></span>
              Danh sách
            </a>
          </li>
          <li>
            <a href="#" v-on:click.prevent="ChapNhan" title="Chấp nhận">
              <span class="icon ui-1_check-circle-08 nc-icon-glyph"></span> Chấp
              nhận
            </a>
          </li>
          <li @click="XuatExcel()" :class="{ disabled: sources.length <= 0 }" title="Xuất Excel">
            <a href="javascript:;">
              <span class="icon one-excel"></span> Xuất Excel
            </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="info-row">
            <div class="key w80">Dịch vụ</div>
            <div class="value">
              <div class="select-custom">
                <select v-model="idDichVu" name="" id="" class="form-control">
                  <option
                    v-for="(item, index) in danhSachDichVuVienThong"
                    :key="index"
                    :value="item.DICHVUVT_ID"
                  >
                    {{ item.TEN_DVVT }}
                  </option>
                </select>
              </div>
            </div>
            <div class="value w30 nowrap text-main padt7">
              <span class="one-alert text-warning f20 inline vcenter"></span>
              File Excel gồm: MA_TB
            </div>
          </div>
          <div class="info-row">
            <div class="key w80">Tên File</div>
            <div class="value">
              <div class="input-more-button">
                <!-- <button class="btn">
                                        <span class="nc-icon-outline ui-1_attach-87"></span>
                                    </button> -->
                <label for="upload" class="btn">
                  <span class="-ap icon-more_horiz"></span>
                  <input
                    ref="fileInput"
                    type="file"
                    id="upload"
                    style="display: none"
                    @change="onLoadFile"
                    accept=".xls, .xlsx"
                    @click="$refs.fileInput.value = null"
                  />
                </label>
                <input v-model="duongDan" type="text" class="form-control" />
              </div>
            </div>
          </div>
        </div>
        <div class="box-form">
          <div class="legend-title">Danh sách thuê bao</div>
          <DataGrid
            :columns="fields"
            :dataSource="sources"
            :showFilter="true"
            ref="danhSach"
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
import { mapState } from "vuex";
import API from "../../api/KhachHangDacBietThang";

export default {
  name: "PopupDocFileXoa",
  props: {
    kyCuoc: String,
  },
  computed: {
    ...mapState("qltnCommon", ["danhSachDichVuVienThong"]),
  },
  mounted() {
    this.idDichVu = this.danhSachDichVuVienThong[0].DICHVUVT_ID;
  },
  data() {
    return {
      sources: [],
      fields: [
        {
          fieldName: "MA_TB",
          headerText: "Số máy/Acc",
          allowFiltering: true,
        },
        {
          fieldName: "TRANGTHAI",
          headerText: "Trạng thái",
          allowFiltering: true,
        },
      ],
      idDichVu: "",
      duongDan: "",
      sourceFile: null,
      checkLoi: false,
    };
  },
  methods: {
    close() {
      this.$bvModal.hide("modal-docfilexoa");
    },
    ChapNhan() {
      if (this.sources.length === 0 || this.checkLoi) {
        this.$root.toastError(
          "Danh sách trong file đang lỗi, hãy kiểm tra lại file đầu vào!"
        );
        return;
      }
      this.$confirm(`Bạn thật sự muốn cập nhật dữ liệu không?`, "Xác nhận", {
        confirmButtonText: "Có",
        cancelButtonText: "Không",
      })
        .then(() => {
          this.XoaTheoFile();
        })
        .catch(() => {});
    },
    async XoaTheoFile() {
      const dsResult = this.sources.map((item) => ({
        maTB: item.MA_TB,
        dichVuVTId: this.idDichVu,
        ghiChu: "",
        ngayTH: "",
      }));
      // console.log("dsResult", dsResult);
      const data = {
        dsKHDB: dsResult,
        kyCuoc: this.kyCuoc,
        kieu: 20,
      };
      this.loading(true);
      try {
        const response = await API.xoaThueBao(this.axios, data);
        if (response.data.error_code === "BSS-00000000") {
          if (response.data.data.length >= 0) {
            this.$root.toastSuccess(`Xóa thuê bao thành công!`);
            this.$emit("sendData", { msgReturn: "OK" });
            this.$bvModal.hide("modal-docfilexoa");
          } else {
            this.$root.toastError(`Lỗi xảy ra. Mời thử lại!`);
          }
        } else {
          this.$root.toastError(`Lỗi xảy ra. Mời thử lại!`);
        }
      } catch (error) {
        this.$root.toastError(error.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
    async KiemTraThueBao(ds) {
      const data = {
        maTT: ds.toString(),
        kyCuoc: this.kyCuoc,
        kieu: 7,
        dichVuVTId: this.idDichVu,
      };
      this.loading(true);
      try {
        const response = await API.kiemTraMaTB(this.axios, data);
        if (response.data.error_code === "BSS-00000000") {
          if (response.data.data.length > 0) {
            this.sources = response.data.data.map(item=>({MA_TB:item.MA_TB,TRANGTHAI:item.LOI}))
            this.checkLoi = true;
            return false;
          } else {
            return true;
          }
        } else {
          return false;
        }
      } catch (error) {
        this.$root.toastError(error.data.message_detail);
        return false;
      } finally {
        this.loading(false);
      }
    },
    DocFile() {
      if (this.duongDan === "") {
        this.$root.toastError("Chưa có thông tin về đường dẫn!");
        return;
      }
      if (!this.checkLoi) {
        this.onChangeFile(this.sourceFile);
      }
    },
    onLoadFile(e) {
      this.sourceFile = e;
      this.duongDan = e.target.files[0].name;
      this.checkLoi = false;
      this.sources = [];
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
          if (propCount == 0) {
            this.$root.toastError("File không có dữ liệu!");
            return;
          }
          if (propCount != 1) {
            this.$root.toastError("Danh sách file phải có cột: MA_TB!");
            return;
          }

          if (!excellist[0].hasOwnProperty("MA_TB")) {
            this.$root.toastError(
              "Tên cột trong file không đúng định dạng: MA_TB!"
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

          if (dsDuplicateMaTT.length === 0) {
            if (this.KiemTraThueBao(dsMaTT)) {
              this.sources = dsMaTT.map((item) => ({
                MA_TB: item,
                TRANGTHAI: "ok",
              }));
              this.checkLoi = false;
            }
          } else {
            const resultMa = dsDuplicateMaTT.toString();
            this.$root.toastError(
              `Thuê bao: ${resultMa} xuất hiện nhiều lần trong danh sách!`
            );
            // dsDuplicateMaTT.forEach((element) => {
            //   this.sources.push({
            //     MA_TB: element,
            //     TRANGTHAI: "Thuê bao bị lặp lại",
            //   });
            // });
            this.checkLoi = true;
            return;
          }
        } catch (e) {
          return this.$root.toastError("Kiểm tra dữ liệu file đầu vào!");
        }
      };
      fileReader.readAsBinaryString(files[0]);
    },
    XuatExcel() {
      if (this.sources <= 0) {
        this.$root.toastError("Chưa có danh sách để xuất Excel!");
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
    resetData() {
      this.duongDan = "";
      this.sources = [];
      this.idDichVu = this.danhSachDichVuVienThong[0].DICHVUVT_ID;
    },
  },
};
</script>
