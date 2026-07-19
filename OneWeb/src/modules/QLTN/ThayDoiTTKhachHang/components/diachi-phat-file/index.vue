<template>
  <b-modal
    id="modal-diachiphat-file"
    size="xl"
    hide-footer
    hide-header
    hide-header-close
    body-class="modal-body p-0"
    no-close-on-backdrop
    @show="resetData"
    @hide="resetData"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span> Thay đổi địa chỉ phát theo file
        </div>
        <div
          class="close -ap icon-close"
          data-dismiss="modal"
          @click="onClose"
        ></div>
      </div>
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a href="#" @click.prevent="onSave()" :class="{disabled : isLoi}">
              <span class="icon one-save"></span>Ghi lại
            </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title">
            <div class="pull-left">Thông tin địa chỉ phát</div>
            <div class="pull-right red">{{ labelThoiGian }}</div>
            <div class="clearfix"></div>
          </div>
          <div class="note marb10 red fw6">
            Cấu trúc file Excel: MA_TT, TEN_QUAN, TEN_PHUONG, TEN_PHO, TEN_AP,
            TEN_KHU, SONHA, DACDIEM
          </div>
          <div class="info-row">
            <div class="key w60">Chọn File</div>
            <div class="value">
              <div class="input-more-button">
                <label for="uploadDiaChiPhat" class="btn">
                  <span class="-ap icon-more_horiz"></span>
                </label>
                <input
                  type="file"
                  @change="onChangeFile"
                  ref="fileInputDiaChiPhat"
                  accept=".xlsx, .xls"
                  id="uploadDiaChiPhat"
                  style="display: none"
                  @click="$refs.fileInputDiaChiPhat.value = null"
                />
                <input type="text" class="form-control" v-model="txtFileName" />
              </div>
            </div>
          </div>
        </div>
        <div class="box-form" v-if="!isLoi">
          <div class="legend-title">Danh sách thay đổi địa chỉ phát</div>
          <div class="table-content">
            <DataGrid
              v-bind:columns="[
                {
                  fieldName: 'STT',
                  headerText: 'STT',
                  allowFiltering: true,
                  allowSorting: false,
                },
                {
                  fieldName: 'MA_TT',
                  headerText: 'Mã thanh toán',
                  allowFiltering: true,
                  allowSorting: false,
                },
                {
                  fieldName: 'TEN_QUAN',
                  headerText: 'Tên quận',
                  allowFiltering: true,
                  allowSorting: false,
                },
                {
                  fieldName: 'TEN_PHUONG',
                  headerText: 'Tên phường',
                  allowFiltering: true,
                  allowSorting: false,
                },
                {
                  fieldName: 'TEN_PHO',
                  headerText: 'Tên phố',
                  allowFiltering: true,
                  allowSorting: false,
                },
                {
                  fieldName: 'TEN_AP',
                  headerText: 'Tên ấp',
                  allowFiltering: true,
                  allowSorting: false,
                },
                {
                  fieldName: 'TEN_KHU',
                  headerText: 'Tên khu',
                  allowFiltering: true,
                  allowSorting: false,
                },
                {
                  fieldName: 'SONHA',
                  headerText: 'Số nhà',
                  allowFiltering: true,
                  allowSorting: false,
                },
                {
                  fieldName: 'DACDIEM',
                  headerText: 'Đặc điểm',
                  allowFiltering: true,
                  allowSorting: false,
                },
              ]"
              v-bind:dataSource="gridViewData"
              :showFilter="true"
              :allowPaging="true"
              :enablePagingServer="false"
              ref="tabledcPhat"
            >
            </DataGrid>
          </div>
        </div>
        <div class="box-form" v-if="isLoi">
          <div class="legend-title red">Thông tin lỗi từ file Excel</div>
          <div class="table-content">
            <DataGrid
              v-bind:columns="[
                {
                  fieldName: 'STT',
                  headerText: 'STT',
                  allowFiltering: true,
                  allowSorting: false,
                },
                {
                  fieldName: 'MA_TT',
                  headerText: 'Mã thanh toán',
                  allowFiltering: true,
                  allowSorting: false,
                },
                {
                  fieldName: 'TEN_QUAN',
                  headerText: 'Tên quận',
                  allowFiltering: true,
                  allowSorting: false,
                },
                {
                  fieldName: 'TEN_PHUONG',
                  headerText: 'Tên phường',
                  allowFiltering: true,
                  allowSorting: false,
                },
                {
                  fieldName: 'TEN_PHO',
                  headerText: 'Tên phố',
                  allowFiltering: true,
                  allowSorting: false,
                },
                {
                  fieldName: 'TEN_AP',
                  headerText: 'Tên ấp',
                  allowFiltering: true,
                  allowSorting: false,
                },
                {
                  fieldName: 'TEN_KHU',
                  headerText: 'Tên khu',
                  allowFiltering: true,
                  allowSorting: false,
                },
                {
                  fieldName: 'SONHA',
                  headerText: 'Số nhà',
                  allowFiltering: true,
                  allowSorting: false,
                },
                {
                  fieldName: 'DACDIEM',
                  headerText: 'Đặc điểm',
                  allowFiltering: true,
                  allowSorting: false,
                },
                {
                  fieldName: 'LOI',
                  headerText: 'Trạng thái',
                  allowFiltering: true,
                  allowSorting: false,
                },
              ]"
              v-bind:dataSource="dsLoi"
              :showFilter="true"
              :allowPaging="true"
              :enablePagingServer="false"
              ref="tabledcPhatLoi"
            >
            </DataGrid>
          </div>
        </div>
      </div>
    </div>
  </b-modal>
</template>
<style></style>
<script>
import xlsx from "xlsx";
import API from "../../../api/ThayDoiTTKH";
import moment from "moment";
export default {
  props: {},
  data() {
    return {
      labelThoiGian: "00:00:00",
      gridViewData: [],
      txtFileName: "",
      isLoi: false,
      dsLoi: [],
    };
  },
  methods: {
    onClose() {
      this.$bvModal.hide("modal-diachiphat-file");
    },
    async onSave() {
      if (this.isLoi) {
        this.$root.toastError("Dữ liệu đang có lỗi. Mời thử lại!");
        return;
      }
      if (this.gridViewData.length <= 0) {
        this.$root.toastError("Chưa có danh sách để cập nhật");
        return;
      }
      const data = {
        ds: this.gridViewData.map((item) => ({
          maTT: item.MA_TT,
          tenQuan: item.TEN_QUAN,
          tenPhuong: item.TEN_PHUONG,
          tenPho: item.TEN_PHO,
          tenAp: item.TEN_AP,
          tenKhu: item.TEN_KHU,
          soNha: item.SONHA,
          dacDiem: item.DACDIEM,
        })),
      };
      this.loading(true);
      try {
        const response = await API.capNhatDanhSachDiaChi_BC(this.axios, data);
        if (response.data.error_code === "BSS-00000000") {
          this.resetData();
          this.$root.toastSuccess(
            "Cập nhật dữ liệu lên viễn thông tỉnh thành công!"
          );
        }
      } catch (error) {
        this.$root.toastError(error.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
    onChangeFile(e) {
      const files = e.target.files;
      this.txtFileName = e.target.files[0].name;
      this.gridViewData = [];
      this.dsLoi = [];
      this.isLoi = false;
      let isCheck = false;
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
          const ws = XLSX.utils.sheet_to_json(workbook.Sheets[wsname],{defval:""});
          const excellist = [];
          let dsDanhSach = [];
          if (ws.length <= 0) {
            this.$root.toastError(
              "Không tìm thấy dữ liệu trong file đã upload!"
            );
            return;
          }
          if (
            !ws[0].hasOwnProperty("MA_TT") ||
            !ws[0].hasOwnProperty("TEN_QUAN") ||
            !ws[0].hasOwnProperty("TEN_PHUONG") ||
            !ws[0].hasOwnProperty("TEN_PHO") ||
            !ws[0].hasOwnProperty("TEN_AP") ||
            !ws[0].hasOwnProperty("TEN_KHU") ||
            !ws[0].hasOwnProperty("SONHA") ||
            !ws[0].hasOwnProperty("DACDIEM")
          ) {
            this.$root.toastError(
              "Tên cột trong file không đúng định dạng : MA_TT, TEN_QUAN, TEN_PHUONG, TEN_PHO, TEN_AP, TEN_KHU, SONHA, DACDIEM"
            );
            return;
          }
          for (let i = 0; i < ws.length; i++) {
            excellist.push(ws[i]);
            let lydoLoi = "";
            lydoLoi = !ws[i].MA_TT
              ? "Thiếu mã thanh toán!"
              : !ws[i].TEN_QUAN
              ? "Thiếu tên quận!"
              : !ws[i].TEN_PHUONG
              ? "Thiếu tên phường!"
              : !ws[i].TEN_PHO && !ws[i].TEN_AP && !ws[i].TEN_KHU
              ? "Thiếu tên phố (hoặc ấp, khu)!"
              : "";
            if (lydoLoi) isCheck = true;
            dsDanhSach.push({
              STT: i+1,
              MA_TT: ws[i].MA_TT,
              TEN_QUAN: ws[i].TEN_QUAN,
              TEN_PHUONG: ws[i].TEN_PHUONG,
              TEN_PHO: ws[i].TEN_PHO,
              TEN_AP: ws[i].TEN_AP,
              TEN_KHU: ws[i].TEN_KHU,
              SONHA: ws[i].SONHA,
              DACDIEM: ws[i].DACDIEM,
              LOI: lydoLoi,
            });
          }

          if (isCheck) {
            this.dsLoi = dsDanhSach;
            this.isLoi = true;
            return;
          } else {
            let isCheckAPI = false;
            const result = await this.checkLoiMaTT(dsDanhSach);
            result.map((item, index) => {
              if (item.TRANGTHAI) {
                this.dsLoi.push({ ...item, STT: ++index, LOI: item.TRANGTHAI });
                isCheckAPI = true;
              } else {
                this.gridViewData.push({ ...item, STT: ++index });
              }
            });
            this.isLoi = isCheckAPI;
          }
        } catch (error) {
          return this.$root.toastError("Kiểm tra dữ liệu file đầu vào!");
        }
      };
      fileReader.readAsBinaryString(files[0]);
    },
    resetData() {
      this.gridViewData = [];
      this.dsLoi = [];
      this.txtFileName = "";
      this.isLoi = false;
    },
    async checkLoiMaTT(ds) {
      const data = {
        kyCuoc: `${moment(new Date())
          .subtract(1, "months")
          .endOf("month")
          .format("YYYYMM")}01`,
        // kyCuoc: "20211001",
        ds: ds.map((item) => ({
          maTT: item.MA_TT,
          tenQuan: item.TEN_QUAN,
          tenPhuong: item.TEN_PHUONG,
          tenPho: item.TEN_PHO,
          tenAp: item.TEN_AP,
          tenKhu: item.TEN_KHU,
          soNha: item.SONHA,
          dacDiem: item.DACDIEM,
        })),
      };
      this.loading(true);
      try {
        const response = await API.kiemtraLoiDiaChi_BC(this.axios, data);
        if (response.data.error_code === "BSS-00000000") {
          return response.data.data || [];
        }
      } catch (error) {
        return [];
        // this.$root.toastError(error.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
  },
};
</script>
