<template>
  <!-- <b-modal
    id="popup-dsKhoaMoUploadXLS"
    size="xl"
    hide-footer
    hide-header
    hide-header-close
    body-class="modal-body p-0"
    @show="onShow"
  > -->
  <ejs-dialog
      :enableResize="true"
      :allowDragging="true"
      key="docFileThem"
      ref="frmDsKhoaMo_DocFileThem"
      :header="'Danh sách thuê bao không khảo sát'"
      showCloseIcon="true"
      width="60%"
      :visible="false"
      :open="dialogOpen"
      :close="closeDialog"
      :overlayClick="closeDialog"
      :isModal="true"
  >
    <div class="modal-content popup-box">
      <!-- <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span> Danh sách các thuê bao không
          khoá/không mở
        </div>
        <div
          class="close -ap icon-close"
          data-dismiss="modal"
          @click="$bvModal.hide('popup-dsKhoaMoUploadXLS')"
        ></div>
      </div> -->
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a href="javascript: void(0)" @click="importExcel">
              <span class="icon design_bullet-list-67 nc-icon-glyph"></span>
              Danh sách
            </a>
          </li>
          <li>
            <a href="javascript: void(0)" @click="CapNhap">
              <span class="icon ui-1_check-circle-08 nc-icon-glyph"></span> Chấp
              nhận
            </a>
          </li>
          <li>
            <a href="javascript: void(0)" @click="onExportExcel">
              <span class="icon one-excel"></span> Xuất Excel
            </a>
          </li>
        </ul>
        <!-- <div class="more">
          <a>
            <span class="icon -ap icon-dots-three-vertical"></span>
          </a>
        </div> -->
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="list-checks fw6 marb10">
            <div class="item">
              <span
                class="one-alert f24 text-warning inline vcenter marr7"
              ></span>
              <span class="text-main">File excel gồm 2 trường: </span>
            </div>
            <div class="item">
              <div class="check-action red">
                <input type="radio" checked v-model="loaiCot" value="1" />
                <span class="name" style="font-weight: bold"
                  >THUE BAO_ID, MA_TB</span
                >
              </div>
            </div>
            <div class="item">
              <div class="check-action red">
                <input type="radio" class="check" v-model="loaiCot" value="2" />
                <span class="name" style="font-weight: bold">MA_TB, LYDO</span>
              </div>
            </div>
            <div class="float-right">
              <a href="javascript: void(0)" @click="onTaiFileMauClicked">Tải file mẫu</a>
            </div>
          </div>
          <div class="info-row">
            <div class="key">Dịch vụ</div>
            <div class="value">
              <div class="select-custom">
                <b-form-select
                  v-model="loaiDanhMucVTSelected"
                  class="form-control"
                  :options="this.danhMucDichVu"
                >
                </b-form-select>
                <!-- <select name= id= class="form-control">
                                <option value=>HPG-30-802626</option>
                            </select> -->
              </div>
            </div>
          </div>
          <div class="info-row">
            <div class="key">Đường dẫn</div>
            <div class="value">
              <div class="input-more-button">
                <!-- <button class="btn">
                                <span class="-ap icon-more_horiz"></span>
                            </button> -->
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
          <div class="legend-title mart20">
            <div class="title pull-left">Danh sách thuê bao</div>
            <div class="pull-right fw6 red">
              <!-- <span class="inline">Thông báo: </span> -->
              <span class="inline marl20">00:00:00</span>
            </div>
            <div class="clearfix"></div>
          </div>
          <div class="table-content">
            <DataGrid3
              :columns="fields"
              :dataSource="dsLoi"
              :showColumnCheckbox="true"
              :showFilter="true"
              :allowPaging="true"
              :enablePagingServer="false"
              dataKeyField="STT"
              ref="tblDsLoi_Them"
            ></DataGrid3>
          </div>
        </div>
      </div>
    </div>
    <appYesNoModal
      content="Bạn thật sự muốn cập nhật dữ liệu không ?"
      idModal="popup-confirmThem"
      @onOk="onChapNhanConfirm"
    />
  <!-- </b-modal> -->
  </ejs-dialog>
</template>
<style scoped>
.title {
    font-weight: 600 !important;
}
</style>
<script>
import xlsx from "xlsx";
import { mapActions, mapState, mapGetters } from "vuex";
import DanhSachThueBaoKhongKhaoSatApi from "../../api/DanhSachThueBaoKhongKhaoSatApi";
import CustomDataGrid from "../../utils/CustomDataGrid.vue";
import downloadexcel from "vue-json-excel";
import YesNoModal from "../../utils/YesNoModal.vue";
import { Workbook } from "@syncfusion/ej2-excel-export/index";
export default {
  name: "DSKhoaMoUploadXLS",
  components: {
    downloadexcel,
    datagrid: CustomDataGrid,
    appYesNoModal: YesNoModal,
  },
  data() {
    return {
      loaiDs: -1,
      ngayTH: '',
      ngayTHDen: '',
      lyDo: '',
      ngayTHDenCheck: false,

      loaiCot: 2,
      dsRow: [],
      items: [],
      danhMucDichVu: [],
      loaiDanhMucVTSelected: "",
      dsLoi: [],
      files: [],
      txtFileName: "",
      fields: [
        {
          fieldName: "MA_TB",
          headerText: "Số máy/Acc",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "TRANGTHAI_TB",
          headerText: "Trạng thái TB",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "LYDO",
          headerText: "Lý do",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "TRANGTHAI",
          headerText: "Trạng thái",
          allowFiltering: true,
          allowSorting: false,
        },
      ],
      exportExcelFields: {
        "Số máy/ACC": "MA_TB",
        "Trạng thái TB": "TRANGTHAI_TB",
        "Lý do": "LYDO",
        "Trạng thái": "TRANGTHAI",
      },
      toastMessages: {
        error: [],
        success: [],
      }
    };
  },
  created() {},
  computed: {
    ...mapGetters("cskhCommon", ["loaiDanhMucVTGetter"]),
    ...mapState("cskhCommon", ["loadDanhMucVT"]),
    dataExcel() {
      return Object.values(this.dsLoi);
    },
  },
  mounted() {
    this.loaiDanhMucVTSelected =
      this.loaiDanhMucVTGetter && this.loaiDanhMucVTGetter.length > 0
        ? this.loaiDanhMucVTGetter[0].value
        : "";
  },
  methods: {
    ...mapActions("cskhCommon", [
        "getLoaiDSThueBaoKhongKhaoSat",
        "getDanhMucVT",
    ]),
    async dialogOpen() {
      try {
        this.toastMessages = {
          error: [],
          success: [],
        }
        await this.onShow()
      }
      catch(e) {
        console.log(e)
      }
    },
    openDialog() {
        // this.getDsLoaiKhachHang()
        this.$refs.frmDsKhoaMo_DocFileThem.show();
    },
    closeDialog() {
        this.$emit("onHide");
        this.$refs.frmDsKhoaMo_DocFileThem.hide();
    },
    countMaTB(maTB) {},
    async onChangeFile(e) {
      this.files = e.target.files;
      this.txtFileName = e.target.files[0].name;
      await this.importExcel();
    },
    onStartDownload() {
      if (this.dsLoi.length == 0) {
        this.$root.toastError('Không có dữ liệu để xuất Excel !')
        return
      }
    },
    async CapNhapRow(element) {
      try {
        let data = {
          thueBaoId: element.THUEBAO_ID,
          thanhToanId: element.THANHTOAN_ID,
          dichVuVtId: element.DICHVUVT_ID,
          loaiTbId: element.LOAITB_ID,
          loaiDs: this.loaiDs,
          lyDo: element.LYDO ? element.LYDO : this.lyDo,
          ghiChu: "Nạp file",
          nhanVienId: this.$auth.getNhanVienID(),
          mayCn: this.$auth.getUserName(),
          nguoiCn: this.$auth.getUserName(),
          ipCn: this.$auth.getUserName(),
          ngayTh: this.ngayTH,
          ngayThDen: this.ngayTHDen,
        };
        let response =
          await DanhSachThueBaoKhongKhaoSatApi.capNhapThueBaoKhongKhoaMo(
            this.axios,
            data
          );
        if (response.data.error_code === "BSS-00000000")
        {
          this.toastMessages.success.push(element.MA_TB)
        }
        else
        {
          this.toastMessages.error.push(element.MA_TB)
        }
      } catch (e) {
        console.log(e)
        this.toastMessages.error.push(element.MA_TB)
      } finally {
      }
    },
    async onChapNhanConfirm() {
      let dsUpdate = this.dsLoi.filter((x) =>
        this.$refs.tblDsLoi_Them.getSelectedRecords().map(x => x.STT).includes(x.STT)
      );
      let promises = [];
      this.toastMessages = {
        error: [],
        success: [],
      }

      dsUpdate.forEach(async (element) => {
        promises.push(this.CapNhapRow(element));
      });

      try {
        this.loading(true);
        await Promise.all(promises);
        if (this.toastMessages.success.length > 0) {
          this.$root.toastSuccess("Cập nhập thành công các mã thuê bao: " + this.toastMessages.success.join())
        }
        if (this.toastMessages.error.length > 0) {
          this.$root.toastError("Cập nhập thất bại các mã thuê bao: " + this.toastMessages.error.join())
        }
        // this.$bvModal.hide("popup-dsKhoaMoUploadXLS");
      } catch (e) {
        this.$root.toastError("Có lỗi: " + e.data.message)
        console.log(e);
      } finally {
        this.loading(false);
      }
    },
    async onShow() {
      try {
        this.$root.showLoading(true)
        this.txtFileName = "";
        this.dsLoi = [];
        this.loaiCot = 2
        await this.getDanhMucDichVu()
      }
      catch (e) {

      }
      finally {
        this.$root.showLoading(false)
      }
    },
    async CapNhap() {
      let check = true;
      if (this.dsLoi.length == 0) {
        check = false;
        this.$root.toastError("Chưa có dữ liệu để nạp");
        return;
      }

      if (!check) return;

      if (this.$refs.tblDsLoi_Them.getSelectedRecords().length == 0) {
        this.$root.toastError("Bạn chưa chọn thuê bao.");
        return;
      }

      this.$refs.tblDsLoi_Them.getSelectedRecords().forEach((element) => {
        if (!check) return;
        if (element.TRANGTHAI != "OK") {
          check = false;
          this.$root.toastError("Trạng thái không thỏa mãn điều kiện!");
        }
      });

      if (!check) return;

      this.$bvModal.show("popup-confirmThem");
    },
    async importExcel() {
      const files = this.files;
      if (!files.length) {
        return;
      } else if (!/\.(xls|xlsx)$/.test(files[0].name.toLowerCase())) {
        this.$root.toastError("File không đúng định dạng xlsx hoặc xls");
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
          let dsGiaTri = []
          let dsMaTB = [];
          let dsDuplicateMaTB = [];
          // Edit data
          for (var i = 0; i < ws.length; i++) {
            excellist.push(ws[i]);
            dsMaTB.push(ws[i].MA_TB);
            dsGiaTri.push(this.loaiDanhMucVTSelected)
          }

          var propCount = 0;
          if (excellist && excellist.length > 0) {
            for (var prop in excellist[0]) {
              if (excellist[0].hasOwnProperty(prop)) propCount++;
            }
          }

          if (propCount != 2) {
            this.$root.toastError(
              "Số lượng cột phải = 2 cột, hiện đang có " + propCount + " cột!"
            );
            return;
          }

          if (
            this.loaiCot == 1 &&
            !(
              excellist[0].hasOwnProperty("THUEBAO_ID") &&
              excellist[0].hasOwnProperty("MA_TB")
            )
          ) {
            this.$root.toastError(
              "File cần có chính xác 2 cột THUEBAO_ID và MA_TB!"
            );
            return;
          } else if (
            this.loaiCot == 2 &&
            !(
              excellist[0].hasOwnProperty("LYDO") &&
              excellist[0].hasOwnProperty("MA_TB")
            )
          ) {
            this.$root.toastError("File cần có chính xác 2 cột LYDO và MA_TB!");
            return;
          }

          dsMaTB.forEach((element) => {
            if (
              dsMaTB.indexOf(element) != dsMaTB.lastIndexOf(element) &&
              !dsDuplicateMaTB.includes(element)
            )
              dsDuplicateMaTB.push(element);
          });

          dsDuplicateMaTB.forEach((element, index) => {
            this.dsLoi.push({
              STT: index + 1,
              MA_TB: element,
              TRANGTHAI_TB: "",
              LYDO: "",
              TRANGTHAI: "Thuê bao bị lặp lại",
            });
          });

          if (this.dsLoi.length > 0) return;

          try {
            this.loading(true);
            let dsGhiChu =
              this.loaiCot == 1
                ? JSON.stringify(excellist.map((x) => x.THUEBAO_ID))
                : JSON.stringify(excellist.map((x) => x.LYDO));

            let dataUpload = {
              reset: 1,
              dsMatt: excellist.map((x) => x.MA_TB).join(),
              nguoiDungId: this.$auth.getNguoiDungID(),
              dsGhiChu: dsGhiChu,
              dsGiaTri: dsGiaTri.join(),
              kieuId: this.loaiDs,
            };
            // if (this.loaiCot == 2 && excellist[0].hasOwnProperty('LYDO')) {
            //   dataUpload.dsGhiChu = dsGhiChu
            // }
            // else if (this.loaiCot == 1 && excellist[0].hasOwnProperty('THUEBAO_ID')) {
            //   dataUpload.dsGiaTri = dsGhiChu
            // }



            let response = await DanhSachThueBaoKhongKhaoSatApi.uploadFile(
              this.axios,
              dataUpload
            );
            if (response.data.error_code === "BSS-00000000") {
              let response2 = await DanhSachThueBaoKhongKhaoSatApi.getDSMaTTLoi(
                this.axios,
                {
                  nguoiDungId: this.$auth.getNguoiDungID(),
                  kieuId: this.loaiDs,
                  pageNo: 1,
                  pageSize: excellist.length,
                }
              );
              if (response2.data.error_code === "BSS-00000000") {
                let ds2 = response2.data.data.data || [];
                ds2.forEach((element, index) => {
                  this.dsLoi.push({
                    STT: index + 1,
                    MA_TB: element.MA_TB,
                    TRANGTHAI_TB: element.TRANGTHAI_TB,
                    LYDO: element.hasOwnProperty("LYDO") ? element.LYDO : "",
                    TRANGTHAI: element.TRANGTHAI,
                    THANHTOAN_ID: element.THANHTOAN_ID,
                    DICHVUVT_ID: element.DICHVUVT_ID,
                    LOAITB_ID: element.LOAITB_ID,
                    TRANGTHAITB_ID: element.TRANGTHAITB_ID,
                    THUEBAO_ID: element.THUEBAO_ID,
                  });
                });
              }
            }
          } catch (e) {
            this.$root.toastError("Có lỗi: " + e.data.message)
          } finally {
            this.loading(false);
          }

          // let dsMaTTLoi = [];
          // // dsMaTTLoi = await DanhSachThueBaoKhongKhaoSatApi.getDSMaTTLoi(
          // //   this.axios,
          // //   {}
          // // );
          // excellist.forEach((element, index) => {
          //   this.dsLoi.push({
          //     STT: index + 1,
          //     MA_TB: element.MA_TB,
          //     TRANGTHAI_TB: element.TRANGTHAI_TB,
          //     LYDO: element.hasOwnProperty("LYDO") ? element.LYDO : "",
          //     TRANGTHAI: "OK",
          //     isCheck: false
          //   });
          // });

          // dsMaTTLoi.forEach((element, index) => {
          //   this.dsLoi.push({
          //     STT: index + 1,
          //     MA_TB: element.THUEBAO_ID,
          //     TRANGTHAI_TB: element.TRANGTHAI_TB,
          //     LYDO: element.LYDO,
          //     TRANGTHAI: element.TRANGTHAI,
          //     isCheck: false
          //   });
          // });

          //console.log("Read results", excellist); // At this point, you get an array containing objects that need to be processed
        } catch (e) {
          console.log(e);
          return alert("Read failure!" + e);
        }
      };

      fileReader.readAsBinaryString(files[0]);
      var input = document.getElementById("upload");
    },
    onExportExcel() {
      if (this.dsLoi.length == 0) {
        this.$root.toastError('Không có dữ liệu để xuất Excel !')
        return
      }

      let propName = {
        MA_TB: "Số máy/Acc",
        TRANGTHAI_TB: "Trạng thái TB",
        LYDO: "Lý do",
        TRANGTHAI: "Trạng thái",
      };

      let columns = [
        { index: 1, width: 130 },
        { index: 2, width: 130 },
        { index: 3, width: 130 },
        { index: 4, width: 130 },
      ];

      let rows = [];
      let headerRow = {
        index: 1,
        cells: [
          {
            index: 1,
            value: "Số máy/Acc",
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
            value: "Trạng thái TB",
            colSpan: 1,
            style: {
              borders: {
                color: "#00000000",
                lineStyle: "thin",
              },
            },
          },
          {
            index: 3,
            value: "Lý do",
            colSpan: 1,
            style: {
              borders: {
                color: "#00000000",
                lineStyle: "thin",
              },
            },
          },
          {
            index: 4,
            value: "Trạng thái",
            colSpan: 1,
            style: {
              borders: {
                color: "#00000000",
                lineStyle: "thin",
              },
            },
          },
        ],
      };

      rows.push(headerRow);
      let rowIndex = 2
      this.dsLoi.forEach((element, index) => {
        let row = {
          index: rowIndex++,
          cells: [
                {
                  index: 1,
                  value: element.MA_TB,
                  style: {
                    borders: {
                      color: "#00000000",
                      lineStyle: "thin",
                    },
                  }
                },
                {
                  index: 2,
                  value: element.TRANGTHAI_TB,
                  style: {
                    borders: {
                      color: "#00000000",
                      lineStyle: "thin",
                    },
                    fontColor: '#FF0000'
                  }
                },
                {
                  index: 3,
                  value: element.LYDO,
                  style: {
                    borders: {
                      color: "#00000000",
                      lineStyle: "thin",
                    },
                  }
                },
                {
                  index: 4,
                  value: element.TRANGTHAI,
                  style: {
                    borders: {
                      color: "#00000000",
                      lineStyle: "thin",
                    },
                  }
                },
              ]
        };
        rows.push(row);
      });
      let footerRow = {
        index: rowIndex++,
        cells: [
          {index: 1, value: this.dsLoi.length, style: { borders: { color: "#00000000", lineStyle: "thin", }}},
          {index: 2, value: '', style: { borders: { color: "#00000000", lineStyle: "thin", }}},
          {index: 3, value: '', style: { borders: { color: "#00000000", lineStyle: "thin", }}},
          {index: 4, value: '', style: { borders: { color: "#00000000", lineStyle: "thin", }}}
        ]
      }
      rows.push(footerRow)
      let worksheets = [{ columns, rows }];
      let workbook = new Workbook({ worksheets }, "xlsx");
      workbook.save("Export.xlsx");
    },
    async getDanhMucDichVu() {
      try {
        let response = await this.$root.context.get('/web-cskh/api/danh-muc/dich-vu-vt')
        if (response.error_code === "BSS-00000000") {
          let ds = response.data || []
          let dsDichVuVT = [1,2,4,7,8,9,10,11,12,14,15,16]
          this.danhMucDichVu = []
          ds.forEach(element => {
            if (dsDichVuVT.includes(element.DICHVUVT_ID))
              this.danhMucDichVu.push({value: element.DICHVUVT_ID, text: element.TEN_DVVT})
          })
          if (this.danhMucDichVu.length > 0) {
            this.loaiDanhMucVTSelected = this.danhMucDichVu[0].value
          }
        }
      }
      catch (e) {

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
            value: this.loaiCot == 1 ? "THUE BAO_ID" : "MA_TB",
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
            value: this.loaiCot == 1 ? "MA_TB" : "LYDO",
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
