<template>
   <b-modal 
    id="popupDocFile" 
    size="xl" 
    hide-footer 
    hide-header
    hide-header-close
    body-class="modal-body p-0" 
    no-close-on-backdrop 
    @show="onShow"    
    @hide="onHide"
    >

    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span>Đọc file giao kế hoạch CSKH
        </div>
        <div class="close -ap icon-close" data-dismiss="modal" @click="$bvModal.hide('popupDocFile')"></div>
      </div> 
      <div class="list-actions-top">
        <ul class="list">          
          <li>
            <a href="javascript: void(0)"
                            @click="onCapNhapClicked">
              <span class="icon one-calendar-sync"></span>Cập nhật
            </a>
            
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
      <div class="main-title">đọc file giao kế hoạch chăm sóc khách hàng</div>        
      
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
                  >File excel gồm 3 trường: MA_TT, DOITUONG, LOAI</span
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
            <div class="title pull-left">Danh sách phiếu chờ cập nhật</div>
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
                        fieldName: 'MA_TT',
                        headerText: 'Mã TT',
                        allowFiltering: true,
                    },
                    {
                        fieldName: 'DOITUONG',
                        headerText: 'DOITUONG',
                        allowFiltering: true,
                    },
                    {
                        fieldName: 'TEN_LOAI',
                        headerText: 'LOAI',
                        allowFiltering: true,
                    },
                    
                ]"
                :dataSource="dsKhongLoi"
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
                        fieldName: 'TRANGTHAI',
                        headerText: 'Lý do lỗi',
                        allowFiltering: true,
                    },
                    {
                        fieldName: 'MA_TT',
                        headerText: 'Mã TT',
                        allowFiltering: true,
                    },
                    {
                        fieldName: 'DOITUONG',
                        headerText: 'DOITUONG',
                        allowFiltering: true,
                    },
                    {
                        fieldName: 'TEN_LOAI',
                        headerText: 'LOAI',
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
  
   </b-modal> 
</template>
<script>
import xlsx from "xlsx";
import downloadexcel from "vue-json-excel";
import { Workbook } from "@syncfusion/ej2-excel-export/index";

export default {
  props: {
  },
  components: { downloadexcel},
  created() {},
  data() {
    return {
      txtFileName: '',
      gridDonViKSDaGanSelectedValues: [],
      gridDonViKSChuaGanSelectedValues: [],
      tabDanhSach: false,
      exportExcelFields: {          
          "Mã thanh toán": "MA_TT",
          "Đối tượng": "DOITUONG",
          "Loại": "LOAI",
      },
      dsLoi: [],
      dsDocTuFile: [],
      dsKhongLoi:[],
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
        this.dsKhongLoi = [];
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
   
    capNhatFile: async function(file){
      let dsCapNhat=[];
      file.forEach((x)=>{
        dsCapNhat.push({ maTT: x.MA_TT,
                  doiTuong: x.DOITUONG,
                  loai: x.LOAI});
      });
        let data = {
                ds:dsCapNhat
              };
              
              try {
                this.loading(true);
                let response = await this.$root.context.post('/web-thuno/api/thu-no/quan-ly-dai-ly/cap-nhat-ke-hoach-thu-cps-v2', data );
                if (response.error_code == "BSS-00000000") {
                  this.$toast.success('Cập nhật thành công');
                }
                this.loading(false);
              }
              catch (e) {
                console.log(e)
                this.loading(false);
              }
    },
    async onCapNhapClicked() {
        try {
            if (this.dsKhongLoi.length <= 0) 
            {
                this.$root.toastError("Không có dữ liệu để cập nhập!")
                return
            }
                      
            await this.capNhatFile(this.dsKhongLoi)          
        }
        catch (e) {
            console.log(e)
            
        }
        finally {
            this.$root.showLoading(false)
        }
    },
    async onChangeFile(e) {
      
      this.files = e.target.files;
      this.txtFileName = e.target.files[0].name;
      await this.importExcel();
    },
    async importExcel() {
      const files = this.files;
      
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

          
          if (excellist.length == 0) {
            this.$root.toastError("File trống!")
            return
          }
          if (
              !excellist[0].hasOwnProperty("DOITUONG") ||
              !excellist[0].hasOwnProperty("MA_TT") ||
              !excellist[0].hasOwnProperty("LOAI")
          ) {
              this.$root.toastError(
                  "Tên cột trong file không đúng định dạng : MA_TT,DOITUONG,LOAI"
              );
              return;
          }

          // excellist.forEach((element, index) => {
          //     if (element.MAIN_GHEP || element.MA_TT) {
          //         if (!element.MAIN_GHEP || !element.MA_TT)
          //             this.dsLoi.push({
          //                 DOITUONG: element.DOITUONG,
          //                 MA_TT: element.MA_TT,
          //                 LOAI:element.LOAI,
          //                 MSG: !element.MA_TT
          //                     ? "Mã thanh toán không được để trống!"
          //                     : !element.DOITUONG ? "Mã đối tượng không được để trống!"
          //                     : "",
          //             });
          //     }

          // });

          // if (this.dsLoi.length > 0) {
          //     this.tabDanhSach = true;
          //     return;
          // }
          // let dsCheckTrung = [];
          excellist.forEach((x) =>
              dsMaTB.push({
                  maTT: x.MA_TT,
                  doiTuong: x.DOITUONG,
                  loai: x.LOAI
              })
          );

          //dsCheckTrung = dsMaTB.map((x) => x.MA_TT);

          // dsMaTB.forEach((element) => {
          //     if (
          //         dsCheckTrung.indexOf(element.MA_TT) !=
          //             dsCheckTrung.lastIndexOf(element.MA_TT) &&
          //         !dsDuplicateMaTB.includes(element.MA_TT)
          //     )
          //         dsDuplicateMaTB.push(element);
          // });


          // dsDuplicateMaTB.forEach((element, index) => {
          //     this.dsLoi.push({
          //         MA_TT: element.MAIN_GHEP,
          //         DOITUONG: element.MA_TT,
          //         LOAI: element.LOAI,
          //         MSG: "Cặp MAIN_GHEP và MA_TT bị lặp lại",
          //     });
          // });

          // if (this.dsLoi.length > 0) {
          //     this.tabDanhSach = true;
          //     return;
          // }

          this.dsDocTuFile = excellist;
          console.log('dsDocTuFile',this.dsDocTuFile);
          if (this.dsDocTuFile.length > 0) {
              let data = {
                ds:dsMaTB
              };
              console.log('tmp', data)

              let response = await this.$root.context.post('/web-thuno/api/thu-no/quan-ly-dai-ly/kiem-tra-du-lieu-ke-hoach-thu-cps-v2', data );
              if (response.error_code == "BSS-00000000") {
                // let abc=[];
                // abc=response.data.data
                this.dsLoi=response.data.filter(item=>item.TRANGTHAI!=null);
                this.dsKhongLoi=response.data.filter(item=>item.TRANGTHAI==null);
                if (this.dsLoi.length > 0) 
                      this.tabDanhSach = true;
                // let responseKiemTraFile = await this.$root.context.post('/web-cskh/api/mig/gan-tu-file', {loai: 1, nguoiDungId: this.$auth.getNguoiDungID()})
                // this.dsLoi = []
                // if (responseKiemTraFile.error_code == "BSS-00000000")
                // {
                //     // if (responseKiemTraFile.data.listDataError && responseKiemTraFile.data.listDataError.length > 0) {
                    //     responseKiemTraFile.data.listDataError.forEach(e => {
                    //         this.dsLoi.push({MAIN_GHEP: e.MAIN_GHEP, MA_TT: e.MA_TT, MSG: e.MSG})
                    //     })
                    //     this.tabDanhSach = true;
                    //     this.$root.toastError(responseKiemTraFile.data.messageResult)
                    // }
                    // else {
                    //     this.tabDanhSach = false;
                    // }

                    
                // }
                // else {
                //     this.$root.toastError("Import file thất bại! " + response.data.message)
                // }
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
        if (this.dsLoi.length == 0) {
            this.$root.toastError('Không có dữ liệu để xuất Excel !')
            return
        }
    },
    onTaiFileMauClicked() {
       let columns = [
        { index: 1, width: 130 },
        { index: 2, width: 130 },
        { index: 3, width: 130 },
      ];
      let rows = [];
      let headerRow = {
        index: 1,
        cells: [
          {
            index: 1,
            value: "MA_TT",
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
            value: "DOITUONG",
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
            value: "LOAI",
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
      workbook.save("dskehoachCPS.xlsx");
    }
  },
};
</script>
