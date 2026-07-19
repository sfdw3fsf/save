<template>
  <b-modal
    id="modal-docfilethem"
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
          <span class="icon one-notepad"></span> Đọc file danh sách các thuê bao
          đặc biệt trong tháng
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
              File Excel gồm: MA_TB, LYDO
            </div>
          </div>
          <div class="info-row">
            <div class="key w80">Tên File</div>
            <div class="value">
              <div class="input-more-button">
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
  name: "PopupDocFileThem",
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
          fieldName: "LYDO",
          headerText: "Lý do",
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
      this.$bvModal.hide("modal-docfilethem");
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
          this.ThemTheoFile();
        })
        .catch(() => {});
    },
    async ThemTheoFile() {
      const dsResult = this.sources.map((item) => ({
        maTB: item.MA_TB,
        dichVuVTId: this.idDichVu,
        ghiChu: `Nạp file, Lý do: ${item.LYDO}`,
        ngayTH: "",
      }));
      const data = {
        dsKHDB: dsResult,
        // dsKHDB: [
        //   {
        //     maTB: "hpgmytv608135",
        //     dichVuVTId: "3",
        //     ghiChu: "tesstt",
        //     ngayTH: null,
        //   },
        // ],
        kyCuoc: this.kyCuoc,
        kieu: 7,
      };
      this.loading(true);
      try {
        const response = await API.themMoiThueBao(this.axios, data);
        if (response.data.error_code === "BSS-00000000") {
          if (response.data.data.length >= 0) {
            this.$root.toastSuccess(`Cập nhật dữ liệu thành công!`);
            this.$emit("sendData", { msgReturn: "OK" });
            this.$bvModal.hide("modal-docfilethem");
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
          if (
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            const arr = response.data.data;
            const result = this.sources.map((itm)=>({...itm,...arr.find((item)=>(item.MA_TB.trim() == itm.MA_TB) && item)}))
            // this.sources = response.data.data.map(item=>({MA_TB:item.MA_TB,LYDO:"",TRANGTHAI:item.LOI}))
            this.sources = result.map(item=>({MA_TB:item.MA_TB,LYDO:item.LYDO,TRANGTHAI:item.LOI}))
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
          let dsMaTT2 = [];
          let dsDuplicateMaTT = [];
          // Edit data
          for (var i = 0; i < ws.length; i++) {
            excellist.push(ws[i]);
            dsMaTT.push({ MA_TB: ws[i].MA_TB, LYDO: ws[i].LYDO });
            dsMaTT2.push(ws[i].MA_TB);
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
          if (propCount != 2) {
            this.$root.toastError("Danh sách file phải đủ 2 cột: MA_TB, LYDO");
            return;
          }

          if (
            !excellist[0].hasOwnProperty("MA_TB") ||
            !excellist[0].hasOwnProperty("LYDO")
          ) {
            this.$root.toastError("Tên cột trong file không đúng định dạng : MA_TB, LYDO");
            return;
          }
          dsMaTT.forEach((element) => {
            if (
              dsMaTT2.indexOf(element.MA_TB) !=
                dsMaTT2.lastIndexOf(element.MA_TB) &&
              !dsDuplicateMaTT.some((item) => item.MA_TB === element.MA_TB)
            )
              dsDuplicateMaTT.push(element);
          });

          if (dsDuplicateMaTT.length === 0) {
            if (this.KiemTraThueBao(dsMaTT2)) {
              this.sources = dsMaTT.map((item) => ({
                MA_TB: item.MA_TB,
                LYDO: item.LYDO,
                TRANGTHAI: "ok",
              }));
              this.checkLoi = false;
            }
          } else {
            const dsMaTTDup = dsDuplicateMaTT.map((item) => item.MA_TB);
            const resultMa = dsMaTTDup.toString();
            this.$root.toastError(
              `Thuê bao: ${resultMa} xuất hiện nhiều lần trong danh sách!`
            );
            // dsDuplicateMaTT.forEach((element) => {
            //   this.sources.push({
            //     MA_TB: element.MA_TB,
            //     LYDO:element.LYDO,
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
