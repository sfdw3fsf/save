<template src="./index.html"></template>
<script>
import xlsx from "xlsx";
import { Workbook } from "@syncfusion/ej2-excel-export/index";

export default {
  props: {
  },
  
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
    onExportExcel(){
        if (this.tabDanhSach)
        {
             this.onStartDownload();
                let excelExportProperties = {
                    dataSource: this.dsLoi,
                    fileName:"dsFileLoi.xlsx"
                };
                this.$refs.tblDsLoi.excelExport(excelExportProperties)
        }
        else
        {
           
                let excelExportProperties = {
                    dataSource: this.dsKhongLoi,
                    fileName:"dsFile.xlsx",
                };
                this.$refs.tblDsDocFile.excelExport(excelExportProperties)

        }
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
              

              let response = await this.$root.context.post('/web-thuno/api/thu-no/quan-ly-dai-ly/kiem-tra-du-lieu-ke-hoach-thu-cps-v2', data );
              if (response.error_code == "BSS-00000000") {
                // let abc=[];
                // abc=response.data.data
                this.dsLoi=response.data.filter(item=>item.TRANGTHAI!=null);
                this.dsKhongLoi=response.data.filter(item=>item.TRANGTHAI==null);
                if (this.dsLoi.length > 0) 
                {
                    this.msgThongBao="Có thông tin lỗi"
                    this.tabDanhSach = true;
                }
                else
                {
                    this.tabDanhSach = false;
                    this.msgThongBao="Thông báo"
                }
                 
                      
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

