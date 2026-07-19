<template>
  <b-modal
    id="modal-docfile"
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
          <span class="icon one-notepad"></span>Đọc file đại lý nộp ngân hàng
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
            <a href="#" @click.prevent="GhiLai">
              <span class="icon one-save"></span>
              Ghi lại
            </a>
          </li>
          <li>
            <a href="#" v-on:click.prevent="XuatFile">
              <span class="icon one-download"></span> Xuất File
            </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="info-row">
            <div class="key w90">Tên File</div>
            <div class="value">
              <div class="input-more-button">
                <!-- <button class="btn">
                                        <span class="-ap icon-more_horiz"></span>
                                    </button> -->
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
                <input v-model="duongDan" type="text" class="form-control" />
              </div>
            </div>
            <div class="value w30 nowrap padt7">
              <a href="#" class="link underline" @click.prevent="TaiFileMau"
                >Tải biểu mẫu</a
              >
            </div>
          </div>
        </div>
        <div class="box-form">
          <div class="legend-title">Danh sách khách hàng</div>
          <DataGrid
            :columns="columns"
            :dataSource="sources"
            :showFilter="true"
            ref="gridKhachhang"
            :allowPaging="true"
            :enablePagingServer="false"
          />
        </div>
      </div>
    </div>
  </b-modal>
</template>
<style></style>

<script>
import xlsx from "xlsx";
import API from "../../api/DaiLyNopNganHang";
import { currency } from "@/filters/currency";
export default {
  name: "PopupDocFile",
  props: {
    idNganHang: String,
    ma_tn: String,
    kyHoaDon: String,
    idQuayThu: String,
  },
  computed: {},
  mounted() {},
  data() {
    return {
      sources: [],
      sourceFile: null,
      duongDan: "",
      isCheckLoi: false,
      columns: [
        {
          fieldName: "MA_NVTC",
          headerText: "Mã NVTC",
          allowFiltering: true,
        },
        {
          fieldName: "TIENNOP",
          headerText: "Tiền nộp",
          allowFiltering: true,
          cssClass: "text-right"
        },
        {
          fieldName: "NGAYNOP",
          headerText: "Ngày nộp",
          allowFiltering: true,
        },
        {
          fieldName: "KIEUNOP_ID",
          headerText: "Kiểu nộp",
          allowFiltering: true,
        },
        {
          fieldName: "GHICHU",
          headerText: "Ghi chú",
          allowFiltering: true,
        },
        {
          fieldName: "LYDO_LOI",
          headerText: "Lỗi",
          allowFiltering: true,
        },
      ],
      isLoi: false,
    };
  },
  methods: {
    close() {
      this.$bvModal.hide("modal-docfile");
    },
    async GhiLai() {
      if (this.isLoi) {
        this.$root.toastError("File đang có lỗi, mời thử lại!");
        return;
      }
      this.isCheckLoi = false;
      if (this.sources.length <= 0) {
        this.$root.toastError("Chưa có dữ liệu để cập nhật!");
        return;
      }
      await this.CapNhat();
      if (!this.isCheckLoi) {
        this.resetData();
        this.$root.toastSuccess("Ghi dữ liệu đại lý nộp ngân hàng thành công!");
        this.$emit("sendData", { msgReturn: "OK" });
      } else {
        this.$root.toastError("Có lỗi xảy ra. Mời thử lại!");
      }
    },
    async CapNhat() {
      this.loading(true);
      try {
        const array = this.sources;
        for (let i = 0; i <= array.length; i++) {
          const dataBody = {
            nopNganHangId: null,
            soPhieu: null,
            maNVTC: array[i].MA_NVTC,
            nganHangId: this.idNganHang,
            tienNop: Number(array[i].TIENNOP.replace(/[^0-9.-]+/g,"")),
            ngayNop: array[i].NGAYNOP,
            kyHoaDon: this.kyHoaDon,
            maTN: this.ma_tn,
            ghiChu: array[i].GHICHU,
            donViId: this.idQuayThu,
            kieuNopId: array[i].KIEUNOP_ID,
          };
          try {
            const response = await API.themDaiLyNganHang(this.axios, dataBody);
            if (response.data.error_code === "BSS-00000000") {
              this.isCheckLoi = false;
            } else {
              this.isCheckLoi = true;
              break;
            }
          } catch (error) {
            this.$root.toastError(error.data.message_detail);
            this.isCheckLoi = true;
            break;
          }
        }
      } catch (error) {
        // console.log(error);
      } finally {
        this.loading(false);
      }
    },
    async onChangeFile(e) {
      const files = e.target.files;
      this.duongDan = e.target.files[0].name;
      this.sources = [];
      this.isLoi = false;
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
            type: "binary",cellDates: true, dateNF:"dd/mm/yyyy"
          });
          const wsname = workbook.SheetNames[0];
          const ws = XLSX.utils.sheet_to_json(workbook.Sheets[wsname],{raw: false});
          const excellist = [];
          let dsDanhSach = [];
          for (let i = 0; i < ws.length; i++) {
            excellist.push(ws[i]);
            let lyDo = "";
            //Kiemtra null
            lyDo = !ws[i].MA_NVTC
              ? "Mã NVTC null"
              : !ws[i].TIENNOP
              ? "Tiền nộp null"
              : !ws[i].NGAYNOP
              ? "Ngày nộp null"
              : !ws[i].KIEUNOP_ID
              ? "Kiểu nộp null"
              : "";
            if (lyDo) this.isLoi = true;
            //KiemTra NVTC
            const isLoi = await this.checkDaiLy(ws[i].MA_NVTC);
            if (!isLoi) {
              lyDo = "Mã NVTC không có trong dữ liệu";
              this.isLoi = true;
            }
            //kiemtra dinh dang ngay nop
            // if(moment(ws[i].NGAYNOP).isValid()) ws[i].NGAYNOP = moment(ws[i].NGAYNOP).format('DD/MM/YYYY')
            const isCheckDate = this.testDate(ws[i].NGAYNOP.toString());
            if (!isCheckDate) {
              lyDo = "Ngày nộp không đúng định dạng dd/MM/yyyy";
              this.isLoi = true;
            }
            //kiemtra tien nop
            if (isNaN(ws[i].TIENNOP)) {
              lyDo = "Tiền nộp không đúng định dạng số";
              this.isLoi = true;
            }
            dsDanhSach.push({
              MA_NVTC: ws[i].MA_NVTC,
              TIENNOP: isNaN(ws[i].TIENNOP) ? ws[i].TIENNOP : currency(ws[i].TIENNOP),
              NGAYNOP: ws[i].NGAYNOP,
              KIEUNOP_ID: ws[i].KIEUNOP_ID,
              GHICHU: ws[i].GHICHU,
              LYDO_LOI: lyDo,
            });
          }
          const arrayCol = Object.keys(excellist[0]);
          if (
            arrayCol[0] != "MA_NVTC" &&
            arrayCol[1] != "TIENNOP" &&
            arrayCol[2] != "NGAYNOP" &&
            arrayCol[3] != "KIEUNOP_ID" &&
            arrayCol[4] != "GHICHU"
          ) {
            this.$root.toastError(
              "Tên cột trong file không đúng định dạng: MA_NVTC, TIENNOP, NGAYNOP, KIEUNOP_ID, GHICHU!"
            );
            return;
          }
          this.sources = dsDanhSach;
        } catch (error) {
          return this.$root.toastError("Kiểm tra dữ liệu file đầu vào!");
        }
      };
      fileReader.readAsBinaryString(files[0]);
    },
    async checkDaiLy(maNVTC) {
      //   this.loading(true);
      try {
        const response = await API.getThongTinDaiLy(this.axios, maNVTC);
        if (response.data.error_code === "BSS-00000000") {
          return true;
        } else {
          return false;
        }
      } catch (error) {
        return false;
      } finally {
        this.loading(false);
      }
    },
    testDate(str) {
      if (!str) return false;
      var t = str.match(/^(\d{2})\/(\d{2})\/(\d{4})$/);
      if (t === null) return false;
      var d = +t[1],
        m = +t[2],
        y = +t[3];
      if (m >= 1 && m <= 12 && d >= 1 && d <= 31) {
        return true;
      }
      return false;
    },
    XuatFile() {
      if (this.sources <= 0) {
        this.$root.toastError("Không có dữ liệu để xuất File!");
        return;
      }
      this.loading(true);
      try {
        var dsach = xlsx.utils.json_to_sheet(this.sources);
        var wb = xlsx.utils.book_new(); // make Workbook of Excel
        xlsx.utils.book_append_sheet(wb, dsach, "DS"); // sheetAName is name of Worksheet
        // export Excel file
        xlsx.writeFile(wb, "mau.xlsx");
      } catch (error) {
      } finally {
        this.loading(false);
      }
    },
    resetData() {
      this.sources = [];
      this.duongDan = "";
    },
    TaiFileMau() {
      this.loading(true);
      try {
        const temp = [
          {
            MA_NVTC: "",
            TIENNOP: "",
            NGAYNOP: "23/01/2018",
            KIEUNOP_ID: 1,
            GHICHU: "",
          },
        ];
        var dsach = xlsx.utils.json_to_sheet(temp);
        var wb = xlsx.utils.book_new(); // make Workbook of Excel
        xlsx.utils.book_append_sheet(wb, dsach, "DS"); // sheetAName is name of Worksheet
        // export Excel file
        xlsx.writeFile(wb, "Bieumau_dailynopnh.xlsx");
      } catch (error) {
      } finally {
        this.loading(false);
      }
    },
  },
};
</script>
