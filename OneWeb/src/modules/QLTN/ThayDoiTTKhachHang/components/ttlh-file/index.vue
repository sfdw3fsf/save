<template>
  <b-modal
    id="modal-ttlh-file"
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
          <span class="icon one-notepad"></span> Cập nhật thông tin liên hệ theo
          File
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
            <div class="pull-left">Thông tin liên hệ</div>
            <div class="pull-right red">{{ labelThoiGian }}</div>
            <div class="clearfix"></div>
          </div>
          <div class="note marb10 red fw6">
            Cấu trúc file Excel (MA_TT, TEN_LH, SO_DT, EMAIL, MUCDICH)
          </div>
          <div class="info-row">
            <div class="key w60">Chọn File</div>
            <div class="value">
              <div class="input-more-button">
                <!-- <button class="btn">
                            <span class="-ap icon-more_horiz"></span>
                        </button>
                        <input type="text" class="form-control "> -->
                <label for="uploadFileTTLH" class="btn">
                  <span class="-ap icon-more_horiz"></span>
                </label>
                <input
                  type="file"
                  @change="onChangeFile"
                  ref="fileInputTTLH"
                  accept=".xlsx, .xls"
                  id="uploadFileTTLH"
                  style="display: none"
                  @click="$refs.fileInputTTLH.value = null"
                />
                <input type="text" class="form-control" v-model="txtFileName" />
              </div>
            </div>
          </div>
        </div>
        <div class="box-form" v-if="!isLoi">
          <div class="legend-title">Danh sách liên hệ</div>
          <div class="table-content">
            <DataGrid
              v-bind:columns="[
                {
                  fieldName: 'MA_TT',
                  headerText: 'Mã thanh toán',
                  allowFiltering: true,
                  allowSorting: false,
                },
                {
                  fieldName: 'TEN_LH',
                  headerText: 'Tên liên hệ',
                  allowFiltering: true,
                  allowSorting: false,
                },
                {
                  fieldName: 'MUCDICH',
                  headerText: 'Địa chỉ',
                  allowFiltering: true,
                  allowSorting: false,
                },
                {
                  fieldName: 'SO_DT',
                  headerText: 'Số điện thoại',
                  allowFiltering: true,
                  allowSorting: false,
                },
                {
                  fieldName: 'EMAIL',
                  headerText: 'Email',
                  allowFiltering: true,
                  allowSorting: false,
                },
              ]"
              v-bind:dataSource="gridViewData"
              :showFilter="true"
              :allowPaging="true"
              :enablePagingServer="false"
              ref="tablettlh"
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
                  fieldName: 'MA_TT',
                  headerText: 'Mã thanh toán',
                  allowFiltering: true,
                  allowSorting: false,
                },
                {
                  fieldName: 'LOI',
                  headerText: 'Lỗi',
                  allowFiltering: true,
                  allowSorting: false,
                },
              ]"
              v-bind:dataSource="dsLoi"
              :showFilter="true"
              :allowPaging="true"
              :enablePagingServer="false"
              ref="tablettlhLoi"
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
      this.$bvModal.hide("modal-ttlh-file");
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
          ma_tt: item.MA_TT,
          tenLH: item.TEN_LH,
          soDT: item.SO_DT,
          email: item.EMAIL,
          mucDich: item.MUCDICH,
        })),
      };
      this.loading(true);
      try {
        const response = await API.capNhatDanhSachTTLHFile(this.axios, data);
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
          let dsMaTT = [];
          let dsDuplicateMaTT = [];
          for (let i = 0; i < ws.length; i++) {
            excellist.push(ws[i]);
            dsDanhSach.push({
              MA_TT: ws[i].MA_TT,
              TEN_LH: ws[i].TEN_LH,
              SO_DT: ws[i].SO_DT,
              EMAIL: ws[i].EMAIL,
              MUCDICH: ws[i].MUCDICH,
            });
            dsMaTT.push(ws[i].MA_TT);
          }

          const arrayCol = Object.keys(excellist[0]);
          if (
            arrayCol[0] != "MA_TT" ||
            arrayCol[1] != "TEN_LH" ||
            arrayCol[2] != "SO_DT" ||
            arrayCol[3] != "EMAIL" ||
            arrayCol[4] != "MUCDICH"
          ) {
            this.$root.toastError(
              "Tên cột trong file không đúng định dạng: MA_TT, TEN_LH, SO_DT, EMAIL, MUCDICH"
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
            this.isLoi = false;
          } else {
            dsDuplicateMaTT.forEach((element) => {
              this.dsLoi.push({
                MA_TT: element,
                LOI: "Thanh toán bị lặp lại",
              });
            });
            this.isLoi = true;
            return;
          }
          //checkloi
          const result = await this.checkLoiMaTT(dsDanhSach);
          if (result.length > 0) {
            this.dsLoi = result;
            this.isLoi = true;
          } else {
            this.gridViewData = dsDanhSach;
            this.isLoi = false;
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
        pKyCuoc: `${moment(new Date())
          .subtract(1, "months")
          .endOf("month")
          .format("YYYYMM")}01`,
        pKieuId: 24,
        pData: ds.map((item) => ({
          ma_tt: item.MA_TT,
          giatri: "",
        })),
      };
      this.loading(true);
      try {
        const response = await API.kiemtraLoiMaTTTTLHFile(this.axios, data);
        if (response.data.error_code === "BSS-00000000") {
          return response.data.data || [];
        }
      } catch (error) {
        // this.$root.toastError(error.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
  },
};
</script>
