<template>
  <!-- <b-modal
    id="popup-dsKhoaMoUploadXLSXoa"
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
      ref="frmDsKhoaMo_DocFileXoa"
      key="docFileXoa"
      :header="'Xóa danh sách thuê bao không khảo sát'"
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
          <span class="icon one-notepad"></span> Xóa danh sách các thuê bao không
          khoá/không mở
        </div>
        <div
          class="close -ap icon-close"
          data-dismiss="modal"
          @click="$bvModal.hide('popup-dsKhoaMoUploadXLSXoa')"
        ></div>
      </div> -->
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a href="javascript: void(0)"  @click="importExcel_Xoa">
              <span class="icon design_bullet-list-67 nc-icon-glyph"></span>
              Danh sách
            </a>
          </li>
          <li>
            <a href="javascript: void(0)"  @click="CapNhap">
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
                <span class="name" style="font-weight: bold;"
                  >THUEBAO_ID, MA_TB</span
                >
              </div>
            </div>
            <div class="item">
              <div class="check-action red">
                <input type="radio" class="check" v-model="loaiCot" value="2" />
                <span class="name" style="font-weight: bold;">MA_TB</span>
              </div>
            </div>
            <div class="float-right">
              <a href="javascript: void(0)" @click="onTaiFileXoaMauClicked">Tải file mẫu</a>
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
                <label for="upload_Xoa" class="btn">
                  <span class="-ap icon-more_horiz"></span>
                  <input
                    type="file"
                    id="upload_Xoa"
                    style="display: none"
                    ref="fileInput_Xoa"
                    @change="onChangeFileXoa"
                    @click="$refs.fileInput_Xoa.value=null"
                  />
                </label>
                <input
                  type="text"
                  class="form-control"
                  id="showFileNameUpload_Xoa"
                  v-model="txtFileName"
                />
              </div>
            </div>
          </div>
          <div class="legend-title mart20">
            <div class="title pull-left">Xóa danh sách thuê bao không được phép mở chêm</div>
            <div class="pull-right fw6 red">
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
              ref="tblDsLoi_Xoa"
            ></DataGrid3>
          </div>
        </div>
      </div>
    </div>
    <appYesNoModal content="Bạn thật sự muốn cập nhật dữ liệu không ?" idModal='popup-confirm' @onOk="onChapNhanConfirm" />
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
import YesNoModal from "../../utils/YesNoModal.vue"
import { Workbook } from "@syncfusion/ej2-excel-export/index";
export default {
  name: "DSKhoaMoUploadXLSXoa",
  components: { downloadexcel, datagrid: CustomDataGrid, appYesNoModal: YesNoModal },
  data() {
    return {
      loaiDs: -1,
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
          fieldName: "THUEBAO_ID",
          headerText: "Thuê bao ID",
          allowFiltering: true,
          allowSorting: false
        },
        {
          fieldName: "MA_TB",
          headerText: "Số máy/Acc",
          allowFiltering: true,
          allowSorting: false
        },
        {
          fieldName: "TRANGTHAI_TB",
          headerText: "Trạng thái TB",
          allowFiltering: true,
          allowSorting: false
        },
        {
          fieldName: "TRANGTHAI",
          headerText: "Trạng thái",
          allowFiltering: true,
          allowSorting: false
        }
      ],
      toastMessages: {
        error: [],
        success: [],
      }
    };
  },
  created() {},
  computed: {
    ...mapGetters("cskhCommon", ["loaiDanhMucVTGetter"]),
    dataExcel() {
      return Object.values(this.dsLoi);
    }
  },
  mounted() {
    this.loaiDanhMucVTSelected =
      this.loaiDanhMucVTGetter && this.loaiDanhMucVTGetter.length > 0
        ? this.loaiDanhMucVTGetter[0].value
        : "";
  },
  methods: {
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
        this.$refs.frmDsKhoaMo_DocFileXoa.show();
    },
    closeDialog() {
        this.$emit("onHide");
        this.$refs.frmDsKhoaMo_DocFileXoa.hide();
    },
    countMaTB(maTB) {},
    async onChangeFileXoa(e) {
      this.files = e.target.files;
      this.txtFileName = e.target.files[0].name;
      await this.importExcel_Xoa();
    },
    async CapNhapRow(data) {
      try {
        let response = await DanhSachThueBaoKhongKhaoSatApi.xoaThueBaoKhongKhoaMo(
          this.axios,
          data
        );
        if (response.data.error_code === "BSS-00000000")
          this.$root.toastSuccess("Cập nhật dữ liệu thành công: THUEBAOID = " + data.thueBaoId);
        else this.$root.toastError("Cập nhật dữ liệu thất bại: THUEBAOID = " + data.thueBaoId);
      } catch (e) {
        this.$root.toastError("Cập nhật dữ liệu thất bại: THUEBAOID = " + data.thueBaoId);
      } finally {
      }
    },
    async onChapNhanConfirm() {
      let dsUpdate = this.dsLoi.filter(x =>
        this.$refs.tblDsLoi_Xoa.getSelectedRecords().map(x => x.STT).includes(x.STT)
      );
      let promises = []

      let data = {
        dsMaTb: dsUpdate.map(x => x.MA_TB).join(";"),
        loaiDs: this.loaiDs,
        nguoiHuy: this.$auth.getUserName(),
        ipHuy: this.$auth.getUserName()
      }

      try {
        this.loading(true)
        let response = await DanhSachThueBaoKhongKhaoSatApi.xoaThueBaoKhongKhoaMoTheoFile(
          this.axios,
          data
        );
        if (response.data.error_code === "BSS-00000000")
        {
          this.$root.toastSuccess("Xóa dữ liệu thành công");
          // this.dsLoi = []
        }
        else
        {
          this.$root.toastError("Xóa dữ liệu thất bại");
        }
        // this.$bvModal.hide('popup-dsKhoaMoUploadXLSXoa')
      }
      catch (e) {
        this.$root.toastError("Có lỗi: " + e.data.message)
        console.log(e)
      }
      finally {
        this.loading(false)
      }
    },
    async CapNhap() {
      let check = true
      if (this.dsLoi.length == 0) {
        check = false
        this.$root.toastError("Chưa có dữ liệu để nạp");
        return;
      }

      if (!check) return

      this.$refs.tblDsLoi_Xoa.getSelectedRecords().forEach(element => {
        if (!check) return
        if (element.TRANGTHAI != "OK") {
          check = false
          this.$root.toastError("Trạng thái không thỏa mãn điều kiện!");
        }
      });

      if (!check) return

      if (this.$refs.tblDsLoi_Xoa.getSelectedRecords().length == 0) {
        this.$root.toastError("Bạn chưa chọn thuê bao.");
        return;
      }

      this.$bvModal.show("popup-confirm")
    },
    async importExcel_Xoa() {
      const files = this.files;
      if (!files.length) {
        return;
      } else if (!/\.(xls|xlsx)$/.test(files[0].name.toLowerCase())) {
        this.$root.toastError("File không đúng định dạng xlsx hoặc xls");
      }
      const fileReader = new FileReader();
      fileReader.onload = async ev => {
        try {
          this.dsLoi = [];
          const data = ev.target.result;
          const XLSX = xlsx;
          const workbook = XLSX.read(data, {
            type: "binary"
          });
          const wsname = workbook.SheetNames[0];
          const ws = XLSX.utils.sheet_to_json(workbook.Sheets[wsname]);
          const excellist = [];
          let dsMaTB = [];
          let dsDuplicateMaTB = [];
          let dsGiaTri = []
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

          if (this.loaiCot == 1 && propCount != 2) {
            this.$root.toastError(
              "Số lượng cột phải = 2 cột, hiện đang có " + propCount + " cột!"
            );
            return;
          }

          if (this.loaiCot == 2 && propCount != 1) {
            this.$root.toastError(
              "Số lượng cột phải = 1 cột, hiện đang có " + propCount + " cột!"
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
              excellist[0].hasOwnProperty("MA_TB")
            )
          ) {
            this.$root.toastError("File cần có chính xác cột MA_TB!");
            return;
          }

          dsMaTB.forEach(element => {
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
              TRANGTHAI: "Thuê bao bị lặp lại"
            });
          });

          if (this.dsLoi.length > 0) return;

          try {
            this.loading(true);
            let dsGhiChu =
              this.loaiCot == 1
                ? excellist.map(x => x.THUEBAO_ID).join()
                : "";
            let dataUpload = {
              reset: 1,
              dsMatt: excellist.map(x => x.MA_TB).join(),
              dsGhiChu: dsGhiChu,
              dsGiaTri: dsGiaTri.join(),
              nguoiDungId: this.$auth.getNguoiDungID(),
              kieuId: this.loaiDs
            };
            let response = await DanhSachThueBaoKhongKhaoSatApi.uploadFile(
              this.axios,
              dataUpload
            );
            if (response.data.error_code === "BSS-00000000") {
              let response2 = await DanhSachThueBaoKhongKhaoSatApi.getDsMaTTXoaLoi(
                this.axios,
                {
                  nguoiDungId: this.$auth.getNguoiDungID(),
                  kieuId: this.loaiDs,
                  pageNo: 1,
                  pageSize: excellist.length
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
    onTaiFileXoaMauClicked() {
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
            value: this.loaiCot == 1 ? "MA_TB" : "",
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

      if (this.loaiCot == 2) {
        headerRow = {
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
          ],
        };
      }

      rows.push(headerRow);
      let worksheets = [{ columns, rows }];
      let workbook = new Workbook({ worksheets }, "xlsx");
      workbook.save("template.xlsx");
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
            value: "Thuê bao ID",
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
            index: 3,
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
                  value: element.THUEBAO_ID,
                  style: {
                    borders: {
                      color: "#00000000",
                      lineStyle: "thin",
                    },
                  }
                },
                {
                  index: 2,
                  value: element.MA_TB,
                  style: {
                    borders: {
                      color: "#00000000",
                      lineStyle: "thin",
                    },
                  }
                },
                {
                  index: 3,
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
  }
};
</script>
