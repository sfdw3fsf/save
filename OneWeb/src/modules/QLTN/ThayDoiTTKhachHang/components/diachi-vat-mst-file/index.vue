<template>
  <b-modal
    id="modal-diachichungtu-file"
    size="xl"
    hide-footer
    hide-header
    hide-header-close
    body-class="modal-body p-0"
    no-close-on-backdrop
    @show="openForm"
    @hide="resetData"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span> {{ tieuDe }}
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
            <a href="#" @click.prevent="onSave()" :class="{ disabled: isLoi }">
              <span class="icon one-save"></span>Ghi lại
            </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title">
            <div class="pull-left">{{ labelCaption }}</div>
            <div class="pull-right red">{{ labelThoiGian }}</div>
            <div class="clearfix"></div>
          </div>
          <div class="note marb10 red fw6">
            {{ labelCauTruc }}
          </div>
          <div class="info-row">
            <div class="key w60">Chọn File</div>
            <div class="value">
              <div class="input-more-button">
                <label for="uploadFileDiaChiCT" class="btn">
                  <span class="-ap icon-more_horiz"></span>
                </label>
                <input
                  type="file"
                  @change="onChangeFile"
                  ref="fileInputDiaChiCT"
                  accept=".xlsx, .xls"
                  id="uploadFileDiaChiCT"
                  style="display: none"
                  @click="$refs.fileInputDiaChiCT.value = null"
                />
                <input type="text" class="form-control" v-model="txtFileName" />
              </div>
            </div>
          </div>
        </div>
        <div class="box-form" v-if="!isLoi">
          <div class="legend-title">{{ label2 }}</div>
          <div class="table-content">
            <DataGrid
              v-bind:columns="columns"
              v-bind:dataSource="gridViewData"
              :showFilter="true"
              :allowPaging="true"
              :enablePagingServer="false"
              ref="tabledcCT"
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
                  cssClass: 'red',
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
              ref="tabledcCTLoi"
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
  props: ["kieuId"],
  data() {
    return {
      labelThoiGian: "00:00:00",
      gridViewData: [],
      txtFileName: "",
      isLoi: false,
      dsLoi: [],
      kieu_id: 0,
      tieuDe: "Cập nhật địa chỉ chứng từ theo file",
      labelCaption: "Thông tin địa chỉ chứng từ",
      label2: "Danh sách thay đổi địa chỉ chứng từ",
      labelCauTruc: "Cấu trúc file Excel: MA_TT, DIACHI_CT",
      vKyCuoc: `${moment(new Date())
        .subtract(1, "months")
        .endOf("month")
        .format("YYYYMM")}01`,
      columns: [
        {
          fieldName: "MA_TT",
          headerText: "Mã thanh toán",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "DIACHI_CT",
          headerText: "Địa chỉ chứng từ",
          allowFiltering: true,
          allowSorting: false,
        },
      ],
    };
  },
  methods: {
    onClose() {
      this.$bvModal.hide("modal-diachichungtu-file");
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
        ds: this.gridViewData,
        kieuId: this.kieu_id,
      };
      this.loading(true);
      try {
        const response = await API.capNhatDSDiaChiCT(this.axios, data);
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
          const ws = XLSX.utils.sheet_to_json(workbook.Sheets[wsname], {
            defval: "",
          });
          const excellist = [];
          let dsDanhSach = [];
          if (ws.length <= 0) {
            this.$root.toastError(
              "Không tìm thấy dữ liệu trong file đã upload!"
            );
            return;
          }
          var propCount = 0;
          if (ws && ws.length > 0) {
            for (var prop in ws[0]) {
              if (ws[0].hasOwnProperty(prop)) propCount++;
            }
          }
          if (propCount < 2) {
            this.$root.toastError("File phải có 2 cột trở lên!");
            return;
          }
          const keyWord = this.columns[1].fieldName;
          if (
            !ws[0].hasOwnProperty("MA_TT") ||
            !ws[0].hasOwnProperty(keyWord)
          ) {
            this.$root.toastError(
              `Tên cột trong file không đúng định dạng : MA_TT, ${keyWord}`
            );
            return;
          }

          let dsMaTT = [];
          let dsDuplicateMaTT = [];
          for (let i = 0; i < ws.length; i++) {
            excellist.push(ws[i]);
            if (this.kieu_id == 25) {
              dsDanhSach.push({
                MA_TT: ws[i].MA_TT,
                DIACHI_CT: ws[i].DIACHI_CT,
              });
            } else if (this.kieu_id == 2) {
              dsDanhSach.push({
                MA_TT: ws[i].MA_TT,
                MST: ws[i].MST,
              });
            } else if (this.kieu_id == 3) {
              dsDanhSach.push({
                MA_TT: ws[i].MA_TT,
                SDT: ws[i].SDT,
              });
            } else if (this.kieu_id == 4) {
              dsDanhSach.push({
                MA_TT: ws[i].MA_TT,
                EMAIL: ws[i].EMAIL,
              });
            } else if (this.kieu_id == 50) {
              dsDanhSach.push({
                MA_TT: ws[i].MA_TT,
                TEN_TT: ws[i].TEN_TT,
              });
            }
            dsMaTT.push(ws[i].MA_TT);
          }
          dsMaTT.forEach((element) => {
            if (
              dsMaTT.indexOf(element) != dsMaTT.lastIndexOf(element) &&
              !dsDuplicateMaTT.includes(element)
            )
              dsDuplicateMaTT.push(element);
          });

          if (dsDuplicateMaTT.length > 0) {
            dsDuplicateMaTT.forEach((element) => {
              this.dsLoi.push({
                MA_TT: element,
                LOI: "Thanh toán bị lặp lại",
              });
            });
            this.isLoi = true;
            return;
          } else {
            let isCheckAPI = false;
            let dsRequest = dsDanhSach.map((item) => ({
              ma_tt: item.MA_TT,
              giatri: item[`${keyWord}`],
            }));
            const result = await this.checkLoiMaTT(dsRequest);
            if (result.length > 0) {
              this.dsLoi = result;
              isCheckAPI = true;
            } else {
              this.gridViewData = dsDanhSach;
            }
            this.isLoi = isCheckAPI;
          }
        } catch (error) {
          return this.$root.toastError("Kiểm tra dữ liệu file đầu vào!");
        }
      };
      fileReader.readAsBinaryString(files[0]);
    },
    openForm() {
      this.loadForm();
      this.resetData();
    },
    resetData() {
      this.gridViewData = [];
      this.dsLoi = [];
      this.txtFileName = "";
      this.isLoi = false;
    },
    loadForm() {
      this.kieu_id = this.kieuId;
      if (this.kieuId == 25) {
        // Chứng từ
        this.tieuDe = "Cập nhật địa chỉ chứng từ theo file";
        this.labelCaption = "Thông tin địa chỉ chứng từ";
        this.label2 = "Danh sách thay đổi địa chỉ chứng từ";
        this.labelCauTruc = "Cấu trúc file Excel: MA_TT, DIACHI_CT";
        this.columns = [
          {
            fieldName: "MA_TT",
            headerText: "Mã thanh toán",
            allowFiltering: true,
            allowSorting: true,
          },
          {
            fieldName: "DIACHI_CT",
            headerText: "Địa chỉ chứng từ",
            allowFiltering: true,
            allowSorting: true,
          },
        ];
      } else if (this.kieuId == 2) {
        this.tieuDe = "Cập nhật mã số thuế theo file";
        this.labelCaption = "Thông tin mã số thuế";
        this.label2 = "Danh sách thay đổi mã số thuế";
        this.labelCauTruc = "Cấu trúc file Excel: MA_TT, MST";
        this.columns = [
          {
            fieldName: "MA_TT",
            headerText: "Mã thanh toán",
            allowFiltering: true,
            allowSorting: true,
          },
          {
            fieldName: "MST",
            headerText: "Mã số thuế",
            allowFiltering: true,
            allowSorting: true,
          },
        ];
      } else if (this.kieuId == 3) {
        this.tieuDe = "Cập nhật số điện thoại theo file";
        this.labelCaption = "Thông tin số điện thoại";
        this.label2 = "Danh sách thay đổi số điện thoại";
        this.labelCauTruc = "Cấu trúc file Excel: MA_TT, SDT";
        this.columns = [
          {
            fieldName: "MA_TT",
            headerText: "Mã thanh toán",
            allowFiltering: true,
            allowSorting: true,
          },
          {
            fieldName: "SDT",
            headerText: "Số điện thoại",
            allowFiltering: true,
            allowSorting: true,
          },
        ];
      } else if (this.kieuId == 4) {
        this.tieuDe = "Cập nhật email theo file";
        this.labelCaption = "Thông tin Email";
        this.label2 = "Danh sách thay đổi Email";
        this.labelCauTruc = "Cấu trúc file Excel: MA_TT, EMAIL";
        this.columns = [
          {
            fieldName: "MA_TT",
            headerText: "Mã thanh toán",
            allowFiltering: true,
            allowSorting: true,
          },
          {
            fieldName: "EMAIL",
            headerText: "Email",
            allowFiltering: true,
            allowSorting: true,
          },
        ];
      } else if (this.kieuId == 50) {
        this.tieuDe = "Cập nhật tên thanh toán theo file";
        this.labelCaption = "Thông tin tên TT";
        this.label2 = "Danh sách thay đổi tên TT";
        this.labelCauTruc = "Cấu trúc file Excel: MA_TT, TEN_TT";
        this.columns = [
          {
            fieldName: "MA_TT",
            headerText: "Mã thanh toán",
            allowFiltering: true,
            allowSorting: true,
          },
          {
            fieldName: "TEN_TT",
            headerText: "Tên TT",
            allowFiltering: true,
            allowSorting: true,
          },
        ];
      }
    },
    async checkLoiMaTT(ds) {
      const data = {
        pKyCuoc: this.vKyCuoc,
        pKieuId: this.kieu_id,
        pData: ds,
      };
      this.loading(true);
      try {
        const response = await API.kiemtraLoiMaTT(this.axios, data);
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
