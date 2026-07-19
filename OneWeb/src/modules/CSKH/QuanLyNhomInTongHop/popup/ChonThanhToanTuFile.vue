<template>
  <!-- <b-modal
    id="popup-chonThanhToanTuFile"
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
        :header="'Chọn thanh toán từ file'"
        ref="popupChonThanhToanTuFile"
        showCloseIcon="true"
        width="50%"
        :visible="false"
        :open="dialogOpen"
        :close="closeDialog"
        :overlayClick="closeDialog"
        :isModal="true"
    >
    <div class="modal-content popup-box">
      <!-- <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span>Chọn thanh toán từ file
        </div>
        <div
          class="close -ap icon-close"
          data-dismiss="modal"
          @click="$bvModal.hide('popup-chonThanhToanTuFile')"
        ></div>
      </div> -->
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a href="javascript: void(0)" @click="importExcel" data-toggle="tooltip" title="Hiển thị danh sách">
              <span class="icon one-clipart-1"></span> Danh sách
            </a>
          </li>

          <li>
            <a href="javascript: void(0)" @click="capNhap" data-toggle="tooltip" title="Cập nhập">
              <span class="icon one-reload1"></span> Cập nhật
            </a>
          </li>
          <li>
            <a href="javascript: void(0)" data-toggle="tooltip" title="Xuất Excel">
              <downloadexcel :fields="exportExcelFields" :data="this.dsLoi" worksheet="export"
                            name="export.xls" :before-generate="onStartDownload">
                <span class="icon one-excel"></span> Xuất Excel
              </downloadexcel>
            </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
            <div class="item">
              <span class="one-alert f24 text-warning inline vcenter marr7" ></span>
              <span class="text-main">File excel gồm Sheet1, gồm 1 trường: MA_TT hoặc MA_TT_NEO. Xóa hết dòng tiêu đề</span>
            </div>
          <!-- <div class="note marb10">
            <div class="one-alert f20 marr5 inline vcenter text-main">File excel gồm Sheet1, gồm 1 trường: MA_TT hoặc MA_TT_NEO. Xóa hết
            dòng tiêu đề</div>

          </div> -->
          <div class="info-row">
            <div class="key w80">Đường dẫn</div>
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
                    @click="$refs.fileInput.value = null"
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
          <div class="legend-title">
            <div class="pull-left">Danh sách xử lý nợ</div>
            <div class="pull-right red">
              <div class="list-checks">
                <div class="item">00:00:00</div>
                <div class="item">Thông báo</div>
              </div>
            </div>
            <div class="clearfix"></div>
          </div>
          <div class="title-bg-main">
            <span class="title">{{this.tenNhom}}</span>
          </div>
          <div class="table-content">
            <DataGrid3
              :columns="fields"
              :dataSource="dsLoi"
              ref="dsKHLon"
              @selectedRowChanged="onRowSelected"
              :showColumnCheckbox="false"
              :showFilter="true"
              :allowPaging="true"
            :enablePagingServer="false"
            ></DataGrid3>
            <!-- <b-table
              :fields="fields"
              :items="dsLoi"
              selectable
              ref="tblMaTT"
              :showHeader="false"
              @row-selected="onRowSelected"
              class="'table-result table-gachle'"
            >
              <template #cell(Id)="{ rowSelected }">
                <template v-if="rowSelected">
                  <span aria-hidden="true" class="fa fa-play text-main"></span>
                </template>
                <template v-else>
                  <span aria-hidden="true">&nbsp;</span>
                </template>
              </template>
            </b-table> -->
          </div>
        </div>
      </div>
    </div>
  <!-- </b-modal> -->
  <appYesNoModal content="Bạn thật sự muốn cập nhập dữ liệu không ?" idModal='popup-confirmUpFile' @onOk="onUpFileConfirm" />
  </ejs-dialog>
</template>
<style scope>
.legend-title {
    font-weight: 600 !important;
}
.title-bg-main .title {
    font-weight: 600 !important;
}
</style>
<script>
import xlsx from "xlsx";
import CustomDataGrid from "../../utils/CustomDataGrid.vue";
import YesNoModal from "../../utils/YesNoModal.vue";
import downloadexcel from "vue-json-excel";

export default {
  components: { appYesNoModal: YesNoModal, downloadexcel, datagrid: CustomDataGrid },
//   props: ["nhomKHId", "tenNhom"],
  data() {
    return {
      files: [],
      txtFileName: "",
      dsLoi: [],
      rowsSelected: [],
      nhomKHId: 0,
      tenNhom: "",
      fields: [
        {
          fieldName: "MA_TT",
          headerText: "Mã thanh toán",
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
        "Mã thanh toán": "MA_TT",
        "Trạng thái": "TRANGTHAI"
      },
      maTTNeo: 0
    };
  },
  computed: {
    dataExcel() {
      return Object.values(this.dsLoi);
    },
    fetchData() {
      return this.dsLoi;
    },
  },
  methods: {
    onShow() {
        this.dsLoi = []
        this.txtFileName = "";
        this.files = []
        this.maTTNeo = 0
    },
    onRowSelected(items) {
      this.rowsSelected = items;
    },
    async onChangeFile(e) {
      this.files = e.target.files;
      this.txtFileName = e.target.files[0].name;
      await this.importExcel()
    },
    async capNhap() {
      // console.log(this.$refs.tblMaTT.selection);
      if (this.dsLoi.length == 0) {
        this.$root.toastError("Chưa có dữ liệu để nạp");
        return;
      }

      if (this.nhomKHId == 0) {
        this.$root.toastError("Chưa có thông tin nhóm khách hàng");
        return;
      }

      this.$bvModal.show("popup-confirmUpFile")

    },

    async onUpFileConfirm()
    {
        let records = this.dsLoi
        if (records.filter(x => x.TRANGTHAI != "OK").length > 0)
        {
            this.$root.toastError("Trạng thái không thỏa mãn điều kiện!");
            return;
        }

        try {
            this.loading(true)
            let response = await this.$root.context.post(`/web-cskh/api/nhom-in-tong-hop/up-nhom-khach-hang-file?nhomKhId=${this.nhomKHId}`)
            if(response.error_code === "BSS-00000000"){
                this.$root.toastSuccess("Cập nhật dữ liệu thành công")
                this.dsLoi = []
            }
        } catch (error) {
            console.log(error)
        } finally {
            this.loading(false)
        }

        // for await (const record of records)
        // {
        //     try
        //     {
        //         let data = {
        //             maTt: record.MA_TT,
        //             nhomKhId: this.nhomKHId,
        //             tuDong: 0,
        //             mayCn: this.$auth.getUserName(),
        //             nguoiCn: this.$auth.getUserName(),
        //             ipCn: "1.2.3.4",
        //         }

        //         let response = await this.$root.context.post("/web-cskh/api/nhom-in-tong-hop/them-mtt", data)
        //         if (response.error_code === "BSS-00000000")
        //         {
        //             dsThanhCong.push(record.MA_TT)
        //         }
        //         else
        //         {
        //             dsThatBai.push({maTT: record.MA_TT, message: response.message})
        //         }
        //     }
        //     catch (e)
        //     {
        //         console.log(e)
        //         dsThatBai.push({maTT: record.MA_TT, message: e.response.data.message || ''})
        //     }
        // }

        // if (dsThatBai.length <= 0)
        // {
        //     this.$root.toastSuccess("Cập nhật dữ liệu thành công")
        //     this.dsLoi = []
        //     // this.closeDialog()
        // }
        // else
        // {
        //     if (dsThanhCong.length > 0)
        //     {
        //         this.$root.toastSuccess("Cập nhật dữ liệu thành công các mã TT: " + dsThanhCong.join())
        //     }

        //     this.$root.toastError("Cập nhật dữ liệu thất bại các mã TT: \n" + dsThatBai.map(x => " - " + x.maTT + " " + x.message).join("\n"))
        //     this.dsLoi = this.dsLoi.filter(x => !dsThanhCong.includes(x.MA_TT))
        // }
    },
    async importExcel() {
        const files = this.files;
        if (!files.length)
        {
            return;
        }
        else if (!/\.(xls|xlsx)$/.test(files[0].name.toLowerCase()))
        {
            this.$root.toastError("Định dạng file không đúng (xls hoặc xlsx)")
            return
        }
        const fileReader = new FileReader();
      fileReader.onload = async (ev) => {
        try {
          this.dsLoi = [];
          this.maTTNeo = 0;
          const data = ev.target.result;
          const XLSX = xlsx;
          const workbook = XLSX.read(data, { type: "binary", });
          const wsname = workbook.SheetNames[0];
          const ws = XLSX.utils.sheet_to_json(workbook.Sheets[wsname]);
          const excellist = [];
          let dsMaTT = [];
          let dsDuplicateMaTT = [];
          // Edit data
          for (var i = 0; i < ws.length; i++) {
            excellist.push(ws[i]);
            dsMaTT.push(ws[i].MA_TT || ws[i].MA_TT_NEO);
          }

          var propCount = 0;
          if (excellist && excellist.length > 0) {
            for (var prop in excellist[0]) {
              if (excellist[0].hasOwnProperty(prop)) propCount++;
            }
          }

          if (propCount != 1) {
            this.$root.toastError( "Số lượng cột phải = 1 cột, hiện đang có " + propCount + " cột!" );
            return;
          }

          if (
            !(
              excellist[0].hasOwnProperty("MA_TT") ||
              excellist[0].hasOwnProperty("MA_TT_NEO")
            )
          ) {
            this.$root.toastError("File cần có chính xác 1 cột MA_TT hoặc MA_TT_NEO");
            return;
          }

          if(excellist[0].hasOwnProperty("MA_TT_NEO")) this.maTTNeo = 1

          dsMaTT.forEach((element) => {
            if ( dsMaTT.indexOf(element) != dsMaTT.lastIndexOf(element) && !dsDuplicateMaTT.includes(element) )
              dsDuplicateMaTT.push(element);
          });

          dsDuplicateMaTT.forEach((element) => {
            this.dsLoi.push({
              MA_TT: element,
              TRANGTHAI: "Thuê bao bị lặp lại",
              isCheck: false,
            });
          });

          if (this.dsLoi.length > 0) return;

          let dsMaTTLoi = [
            // {
            //   MA_TT: 11,
            //   TRANGTHAI: "OK",
            // },
            // {
            //   MA_TT: 222,
            //   TRANGTHAI: "OK",
            // },
            // {
            //   MA_TT: 333,
            //   TRANGTHAI: "OK",
            // },
          ];
          //   dsMaTTLoi = await DanhSachThueBaoKhongKhaoSatApi.getDSMaTTLoi(
          //     this.axios,
          //     {}
          //   );
          let tmp = {
            reset: 1,
            maInGhep: "",
            dsMaTt: dsMaTT.join(),
            // nguoiDungId: this.$auth.getNguoiDungID()
          }
          let response = await this.$root.context.post("/web-cskh/api/mig/up-file", tmp)
          if(response.error_code == 'BSS-00000000'){

          }

          this.loading(true)
          let tmp2 = {
            loai: 1,
            thamSo: JSON.stringify({
                LOAI_NEO: this.maTTNeo,
                NHOMKH_ID: this.nhomKHId
            })
          }
          let response2 = await this.$root.context.post("/web-cskh/api/kiem-tra/danh-sach-thong-tin", tmp2)
          if(response2.error_code == 'BSS-00000000'){
            this.dsLoi = response2.data
          }


        //   dsMaTT.forEach((element) => {
        //     this.dsLoi.push({
        //       MA_TT: element,
        //       TRANGTHAI: element.TRANGTHAI ? element.TRANGTHAI : 'OK',
        //       isCheck: false,
        //     });
        //   });

        } catch (e) {
          return this.$root.toastError("Đọc file thất bại!" + e);
        } finally {
          this.loading(false)
        }
      };

      fileReader.readAsBinaryString(files[0]);
      var input = document.getElementById("upload");
    },
    async dialogOpen() {
        await this.onShow();
    },
    openDialog() {
        this.$refs.popupChonThanhToanTuFile.show();
    },
    closeDialog() {
        this.$emit("onHideEvent");
        this.$refs.popupChonThanhToanTuFile.hide();
    },
    onStartDownload() {
        if (this.dataExcel.length <= 0)
        {
            this.$root.toastError("Không có dữ liệu để xuất Excel!")
            return
        }
    }
  },
};
</script>
