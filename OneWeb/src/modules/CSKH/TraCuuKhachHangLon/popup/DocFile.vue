<template>
  <!-- <b-modal
    id="popup-docFile"
    size="xl"
    hide-footer
    hide-header
    hide-header-close
    body-class="modal-body p-0"
    @show="onShow"
    @hide="onHide"
  > -->
  <ejs-dialog
        :enableResize="true"
        :allowDragging="true"
        :header="'Cập nhật danh sách thanh toán vào mã in ghép theo file'"
        ref="popupDocFile"
        showCloseIcon="true"
        width="90%"
        :visible="false"
        :open="dialogOpen"
        :close="closeDialog"
        :overlayClick="closeDialog"
        :isModal="true"
    >
    <div class="modal-content popup-box">
      <!-- <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span>Cập nhật danh sách thanh toán
          vào mã in ghép theo file
        </div>
        <div class="close -ap icon-close" data-dismiss="modal" @click="$bvModal.hide('popup-docFile')"></div>
      </div> -->
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a href="javascript: void(0)"
                            @click="onLayDanhSachClicked">
              <span class="icon nc-icon-glyph design_bullet-list-67"></span>
              Danh sách
            </a>
          </li>
          <li>
            <a href="javascript: void(0)"
                            @click="onCapNhapClicked">
              <span class="icon one-calendar-sync"></span>Cập nhật
            </a>
            <appYesNoModal content="Bạn chắc chắn muốn cập nhập mã in ghép không?" idModal='popup-confirmGoMaIG' @onOk="onCapNhapConfirm" />
          </li>
          <li>
            <a href="javascript: void(0)"> 
              <downloadexcel :fields="exportExcelFields" :data="this.dsDocTuFile" worksheet="export" name="export.xls" :before-generate="onStartDownload">
                        <span class="icon one-excel"></span> Xuất Excel
              </downloadexcel>
              <!-- <span class="icon one-excel"></span> Xuất Excel  -->
            </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="row">
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w100">Đường dẫn</div>
                <div class="value">
                  <div class="input-more-button">
                    <label for="upload" class="btn">
                      <span class="-ap icon-more_horiz"></span>
                      <input
                        type="file"
                        id="upload"
                        ref="fileInput"
                        style="display: none"
                        @change="onChangeFile"
                        @click="$refs.fileInput.value=null"
                      />
                    </label>
                    <input
                      type="text"
                      class="form-control"
                      id="showFileNameUpload"
                      v-model="txtFileName"
                    />
                  </div>
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="note mart10">
                <span class="one-alert text-warning f20 vcenter inline"></span>
                <span class="text-main"
                  >File excel gồm 2 trường: MAIN_GHEP, MA_TT</span
                >
                <div class="float-right">
                  <a href="javascript: void(0)" @click="onTaiFileMauClicked">Tải file mẫu</a>
                </div>
              </div>
              
            </div>
          </div>
        </div>
        <div class="box-form">
          <div class="legend-title">
            <div class="title pull-left">Danh sách thanh toán</div>
            <div class="pull-right red fw6 font-weight-bold">
              <span class="time marr30">00:00:00 </span>
              <span> {{msgThongBao}}</span>
            </div>
            <div class="clearfix"></div>
          </div>
          <div class="nav tabs tab-over">
            <a
                @click="tabDanhSach = false"
                :class="{ active: !tabDanhSach }"
                >Danh sách</a
            >
            <a
                @click="tabDanhSach = true"
                :class="{ active: tabDanhSach }"
                >Lỗi</a
            >
          </div>
          <div class="tab-content">
            <DataGrid3
                v-show="!tabDanhSach"
                v-bind:columns="[
                    {
                        fieldName: 'MAIN_GHEP',
                        headerText: 'Mã in ghép',
                        allowFiltering: true,
                    },
                    {
                        fieldName: 'MA_TT',
                        headerText: 'Mã TT',
                        allowFiltering: true,
                    },
                    
                ]"
                :dataSource="dsDocTuFile"
                :showFilter="true"
                dataKeyField="STT"
                :allowPaging="true"
                :enablePagingServer="false"
                :enabledSelectFirstRow="true"
                ref="tblDsDocFile"
              ></DataGrid3>
              <DataGrid3
                v-show="tabDanhSach"
                v-bind:columns="[
                    {
                        fieldName: 'MAIN_GHEP',
                        headerText: 'Mã in ghép',
                        allowFiltering: true,
                    },
                    {
                        fieldName: 'MA_TT',
                        headerText: 'Mã TT',
                        allowFiltering: true,
                    },
                    {
                        fieldName: 'MSG',
                        headerText: 'Trạng thái',
                        allowFiltering: true,
                    },
                ]"
                :dataSource="dsLoi"
                :showFilter="true"
                dataKeyField="STT"
                :allowPaging="true"
                :enablePagingServer="false"
                :enabledSelectFirstRow="true"
                ref="tblDsLoi"
              >
            </DataGrid3>
          </div>
        </div>
      </div>
    </div>
  </ejs-dialog>
  <!-- </b-modal> -->
</template>
<script>
import xlsx from "xlsx";
import downloadexcel from "vue-json-excel";
import { Workbook } from "@syncfusion/ej2-excel-export/index";
import YesNoModal from "../../utils/YesNoModal.vue";
export default {
  props: {
  },
  components: { downloadexcel, appYesNoModal: YesNoModal },
  created() {},
  data() {
    return {
      txtFileName: '',
      gridDonViKSDaGanSelectedValues: [],
      gridDonViKSChuaGanSelectedValues: [],
      tabDanhSach: false,
      exportExcelFields: {
          "Mã in ghép": "MAIN_GHEP",
          "Mã thanh toán": "MA_TT",
      },
      dsLoi: [],
      dsDocTuFile: [],
      msgThongBao: 'Thông báo',
      khachHangId: 0,
    };
  },
  computed: {
  },
  methods: {
    async dialogOpen() {
        await this.onShow();
    },
    openDialog() {
        this.$refs.popupDocFile.show();
    },
    closeDialog() {
        this.$emit("onHideEvent");
        this.$refs.popupDocFile.hide();
    },
    async onShow() {
      try {
        this.txtFileName = "";
        this.dsLoi = [];
        this.dsDocTuFile = [];
        this.tabDanhSach = false;
      }
      catch (e) {

      }
      finally {
        this.$root.showLoading(false)
      }
    },
    onHide() {
      this.$emit('onHide')
    },
    async onLayDanhSachClicked() {
      await this.importExcel();
    },
    async onCapNhapConfirm() {
      try {
        let responseXuLy = await this.$root.context.post('/web-cskh/api/mig/gan-tu-file', {loai: 2, nguoiDungId: this.$auth.getNguoiDungID()})
        if (responseXuLy.error_code == "BSS-00000000") {
          if (responseXuLy.data.messageResult != '') {
            
            if (responseXuLy.data.listDataError.length > 0) {
              responseXuLy.data.listDataError.forEach(x => {
                this.dsLoi.push({
                  MAIN_GHEP: x.MAIN_GHEP,
                  MA_TT: x.MA_TT,
                  MSG: x.MSG
                })
              })
              this.tabDanhSach = true
              this.$root.toastError("Có lỗi xảy ra!"  + responseXuLy.data.messageResult)
            }
            else {
              this.$root.toastSuccess("Cập nhập thành công!")
              this.$bvModal.hide('popup-docFile')
            }
            
          }
        }
      }
      catch (e) {
        console.log(e)
        this.$root.toastError(e.response.data.message)
      }
      finally {
        this.$root.showLoading(false)
      }
    },
    async onCapNhapClicked() {
        try {
            if (this.dsDocTuFile.length <= 0) 
            {
                this.$root.toastError("Không có dữ liệu để cập nhập!")
                return
            }
            if (this.dsLoi.length > 0) {
            this.$root.toastError('Có thông tin lỗi. Bạn hãy kiểm tra lại!')
            return
            }
            this.$bvModal.show('popup-confirmGoMaIG')
            //this.$modals[popupconfirmGoMaIG].show()
            
        }
        catch (e) {
            console.log(e)
            this.$root.toastError(e.response.data.message)
        }
        finally {
            this.$root.showLoading(false)
        }
    },
    async onChangeFile(e) {
      console.log(e);
      this.files = e.target.files;
      this.txtFileName = e.target.files[0].name;
      await this.importExcel();
    },
    async importExcel() {
      const files = this.files;
      console.log(files);
      if (!files.length) {
        return;
      } else if (!/\.(xls|xlsx)$/.test(files[0].name.toLowerCase())) {
        this.$root.toastError("File không đúng định dạng xlsx hoặc xls");
        return
      }
      const fileReader = new FileReader();
      fileReader.onload = async (ev) => {
        try {
          this.dsLoi = [];
          const data = ev.target.result;
          const XLSX = xlsx;
          const workbook = XLSX.read(data, {
            type: "binary",
          });
          const wsname = workbook.SheetNames[0];
          const ws = XLSX.utils.sheet_to_json(workbook.Sheets[wsname]);

          const excellist = [];
          let dsMaTB = [];
          let dsDuplicateMaTB = [];
          // Edit data
          for (var i = 0; i < ws.length; i++) {
              excellist.push(ws[i]);
          }

          console.log('excellist', excellist)
          if (excellist.length == 0) {
            this.$root.toastError("File trống!")
            return
          }
          if (
              !excellist[0].hasOwnProperty("MAIN_GHEP") ||
              !excellist[0].hasOwnProperty("MA_TT")
          ) {
              this.$root.toastError(
                  "Tên cột trong file không đúng định dạng : MAIN_GHEP, MA_TT"
              );
              return;
          }

          excellist.forEach((element, index) => {
              if (element.MAIN_GHEP || element.MA_TT) {
                  if (!element.MAIN_GHEP || !element.MA_TT)
                      this.dsLoi.push({
                          MAIN_GHEP: element.MAIN_GHEP,
                          MA_TT: element.MA_TT,
                          MSG: !element.MAIN_GHEP
                              ? "Mã in ghép không được để trống!"
                              : !element.MA_TT ? "Mã thanh toán không được để trống!"
                              : "",
                      });
              }

          });

          if (this.dsLoi.length > 0) {
              this.tabDanhSach = true;
              return;
          }
          let dsCheckTrung = [];
          excellist.forEach((x) =>
              dsMaTB.push({
                  MAIN_GHEP: x.MAIN_GHEP,
                  MA_TT: x.MA_TT,
                  GOP: x.MAIN_GHEP + "" + x.MA_TT,
              })
          );

          dsCheckTrung = dsMaTB.map((x) => x.GOP);

          dsMaTB.forEach((element) => {
              if (
                  dsCheckTrung.indexOf(element.GOP) !=
                      dsCheckTrung.lastIndexOf(element.GOP) &&
                  !dsDuplicateMaTB.includes(element.GOP)
              )
                  dsDuplicateMaTB.push(element);
          });


          dsDuplicateMaTB.forEach((element, index) => {
              this.dsLoi.push({
                  MAIN_GHEP: element.MAIN_GHEP,
                  MA_TT: element.MA_TT,
                  MSG: "Cặp MAIN_GHEP và MA_TT bị lặp lại",
              });
          });

          if (this.dsLoi.length > 0) {
              this.tabDanhSach = true;
              return;
          }

          this.dsDocTuFile = excellist;

          if (this.dsDocTuFile.length > 0) {
              let data = {
                  reset: 1,
                  maInGhep: this.dsDocTuFile.map((x) => x.MAIN_GHEP).join(),
                  nguoiDungId: this.$auth.getNguoiDungID(),
                  dsMaTt: this.dsDocTuFile
                      .map((x) => x.MA_TT)
                      .join(),
              };
              console.log('tmp', data)

              let response = await this.$root.context.post('/web-cskh/api/mig/up-file', data );
              if (response.error_code == "BSS-00000000") {
                let responseKiemTraFile = await this.$root.context.post('/web-cskh/api/mig/gan-tu-file', {loai: 1, nguoiDungId: this.$auth.getNguoiDungID()})
                this.dsLoi = []
                if (responseKiemTraFile.error_code == "BSS-00000000")
                {
                    if (responseKiemTraFile.data.listDataError && responseKiemTraFile.data.listDataError.length > 0) {
                        responseKiemTraFile.data.listDataError.forEach(e => {
                            this.dsLoi.push({MAIN_GHEP: e.MAIN_GHEP, MA_TT: e.MA_TT, MSG: e.MSG})
                        })
                        this.tabDanhSach = true;
                        this.$root.toastError(responseKiemTraFile.data.messageResult)
                    }
                    else {
                        this.tabDanhSach = false;
                    }
                    
                }
                else {
                    this.$root.toastError("Import file thất bại! " + response.data.message)
                }
                //  this.$root.toastSuccess("Đọc file thành công!")
              }
          }
        } catch (e) {
          console.log(e);
          this.$root.toastError("Đọc file thất bại: " + e)
          //return alert("Read failure!" + e);
        }
      };

      fileReader.readAsBinaryString(files[0]);
      var input = document.getElementById("upload");
    },
    onStartDownload() {
        if (this.dsDocTuFile.length == 0) {
            this.$root.toastError('Không có dữ liệu để xuất Excel !')
            return
        }
    },
    onTaiFileMauClicked() {
       let columns = [
        { index: 1, width: 130 },
        { index: 2, width: 130 },
      ];
      let rows = [];
      let headerRow = {
        index: 1,
        cells: [
          {
            index: 1,
            value: "MAIN_GHEP",
            colSpan: 1,
            style: {
              borders: {
                color: "#00000000",
                lineStyle: "thin",
              },
            },
          },
          {
            index: 2,
            value: "MA_TT",
            colSpan: 1,
            style: {
              borders: {
                color: "#00000000",
                lineStyle: "thin",
              },
            },
          }
        ],
      };

      rows.push(headerRow);
      let worksheets = [{ columns, rows }];
      let workbook = new Workbook({ worksheets }, "xlsx");
      workbook.save("template.xlsx");
    }
  },
};
</script>
