<template src="./UpdateInstallGroupByFile.html"></template>
<style scoped src="./UpdateInstallGroupByFile.scss"></style>
<script>
import API from "./API";
import breadcrumb from "@/components/breadcrumb";
import ActionTop from "@/components/ActionTop.vue";
import KTable from "@/components/KTable.vue";
import DanhSachLoiModal from "./DanhSachLoiModal.vue";
import XLSX from "xlsx";
import moment from "moment";
export default {
  components: {
    breadcrumb,
    ActionTop,
    KTable,
    DanhSachLoiModal,
  },
  data: function () {
    return {
      header: {
        title: "Cập nhật tổ thi công theo file",
      },
      /* columns: [
        {
          field: "MA_TT",
          label: "Mã TB",
          allowFilter: true,
        },
        {
          field: "GIATRI",
          label: "DONVI_ID",
          allowFilter: true,
        },
        {
          field: "GHICHU",
          label: "Kiểu ĐV",
          allowFilter: true,
        },
      ], */
      dataSources: [],
      columnsError: [],
      columnsError1: [
        {
          field: "STT",
          label: "STT",
          allowFilter: false,
        },
        {
          field: "MA_TB",
          label: "Mã TB",
          allowFilter: false,
        },
        {
          field: "DONVI_ID",
          label: "DONVI_ID",
          allowFilter: false,
        },
        {
          field: "KIEUDV_ID",
          label: "Kiểu ĐV",
          allowFilter: false,
        },
        {
          field: "LOAITB_ID",
          label: "Loại TB",
          allowFilter: false,
        },
        {
          field: "LOI",
          label: "Lỗi",
          allowFilter: false,
        },
      ],
      columnsError2: [
        {
          field: "STT",
          label: "STT",
          allowFilter: false,
        },
        {
          field: "ma_tb",
          label: "Mã TB",
          allowFilter: false,
        },
        {
          field: "donvi_id",
          label: "DONVI_ID",
          allowFilter: false,
        },
        {
          field: "kieudv_id",
          label: "Kiểu ĐV",
          allowFilter: false,
        },
        {
          field: "loaitb_id",
          label: "Loại TB",
          allowFilter: false,
        },
        {
          field: "lydo_loi",
          label: "Lỗi",
          allowFilter: false,
        },
      ],
      dataSourcesError: [],
      file: null,
      file_full_path: "",
      danhsach_excel: [],
    };
  },
  validations: {},
  created() {},
  methods: {
    chooseFile() {
      this.dataSources = [];
      this.dataSourcesError = [];
      this.danhsach_excel = [];
      document.getElementById("file").click();
    },
    onChangeFile(event) {
      this.file_full_path = event.target.value;
      this.file = event.target.files[0];
      this.readFileExcel();
    },
    readFileExcel() {
      this.dataSources = [];
      this.dataSourcesError = [];
      this.danhsach_excel = [];
      let columns;
      try {
        this.loading(true);
        if (this.checkfile()) {
          const reader = new FileReader();
          reader.onload = (e) => {
            /* Parse data */
            const bstr = e.target.result;
            const wb = XLSX.read(bstr, { type: "binary" });
            /* Get first worksheet */
            const wsname = wb.SheetNames[0];
            const ws = wb.Sheets[wsname];
            /* Convert array of arrays */
            const data = XLSX.utils.sheet_to_json(ws, { header: 1 });
            columns = data[0];
            if (this.kiemTraDuLieu(columns)) {
              for (var i = 1; i < data.length; i++) {
                this.danhsach_excel.push({
                  MA_TB: data[i][0],
                  DONVI_ID: data[i][1],
                  KIEUDV_ID: data[i][2],
                  LOAITB_ID: data[i][3],
                });
              }
              //check lỗi null
              this.danhsach_excel.forEach((item) => {
                if (
                  item.MA_TB == undefined ||
                  item.MA_TB == null ||
                  item.MA_TB.toString().trim() == ""
                ) {
                  this.dataSourcesError.push({
                    MA_TB: "",
                    DONVI_ID: item.DONVI_ID == undefined ? "" : item.DONVI_ID,
                    KIEUDV_ID: item.KIEUDV_ID == undefined ? "" : item.KIEUDV_ID,
                    LOAITB_ID: item.LOAITB_ID == undefined ? "" : item.LOAITB_ID,
                    LOI: "Mã TB null",
                  });
                } else if (
                  item.DONVI_ID == undefined ||
                  item.DONVI_ID == null ||
                  item.DONVI_ID.toString().trim() == ""
                ) {
                  this.dataSourcesError.push({
                    MA_TB: item.MA_TB == undefined ? "" : item.MA_TB,
                    DONVI_ID: "",
                    KIEUDV_ID: item.KIEUDV_ID == undefined ? "" : item.KIEUDV_ID,
                    LOAITB_ID: item.LOAITB_ID == undefined ? "" : item.LOAITB_ID,
                    LOI: "Đơn vị ID  null",
                  });
                } else if (
                  item.KIEUDV_ID == undefined ||
                  item.KIEUDV_ID == null ||
                  item.KIEUDV_ID.toString().trim() == ""
                ) {
                  this.dataSourcesError.push({
                    MA_TB: item.MA_TB == undefined ? "" : item.MA_TB,
                    DONVI_ID: item.DONVI_ID == undefined ? "" : item.DONVI_ID,
                    KIEUDV_ID: "",
                    LOI: "Kiểu đơn vị ID  null",
                  });
                } else if (
                  item.KIEUDV_ID == undefined ||
                  item.KIEUDV_ID == null ||
                  item.KIEUDV_ID.toString().trim()  > 2
                ) {
                  this.dataSourcesError.push({
                    MA_TB: item.MA_TB == undefined ? "" : item.MA_TB,
                    DONVI_ID: item.DONVI_ID == undefined ? "" : item.DONVI_ID,
                    KIEUDV_ID:  item.KIEUDV_ID == undefined ? "" : item.KIEUDV_ID,
                    LOI: "KIEUDV_ID = 1 (Đơn vị đầu), 2 (Đơn vị cuối)",
                  });
                }
                else if (
                  item.LOAITB_ID == undefined ||
                  item.LOAITB_ID == null ||
                  item.LOAITB_ID.toString().trim() == ""
                ) {
                  this.dataSourcesError.push({
                    MA_TB: item.MA_TB == undefined ? "" : item.MA_TB,
                    DONVI_ID: item.DONVI_ID == undefined ? "" : item.DONVI_ID,
                    LOAITB_ID:  item.LOAITB_ID == undefined ? "" : item.LOAITB_ID,
                    LOI: "LOAITB_ID null",
                  });
                }
              });

              //showModal lỗi null
              if (this.dataSourcesError.length > 0) {
                this.columnsError = this.columnsError1;
                this.dataSourcesError.forEach((item, index) => {
                  item.STT = index + 1;
                });
                this.$refs.danhsachloi.showModal();
                return;
              }

              //check lỗi trùng
              //let ds_trung=this.findDuplicates(this.danhsach_excel)
              let ds_trung = this.findDuplicates(this.danhsach_excel);
              console.log("ds_trung", ds_trung);
              if (ds_trung.length > 0) {
                this.columnsError = this.columnsError1;
                ds_trung.forEach((item, index) => {
                  this.dataSourcesError.push({
                    STT: index + 1,
                    MA_TB: item.MA_TB,
                    DONVI_ID: item.DONVI_ID,
                    KIEUDV_ID: item.KIEUDV_ID,
                    LOI: "MA_TB, DONVI_ID, KIEUDV_ID bị lặp lại",
                  });
                });
                this.$refs.danhsachloi.showModal();
                return;
              }
              //FN_INS_KIEMTRA_MA_TB
              var danhsach = [];
              this.danhsach_excel.forEach((item) => {
                danhsach.push({
                  MA_TT: item.MA_TB,
                  GIATRI: item.DONVI_ID,
                  KIEU_ID: "40",
                  NGUOIDUNG_ID: this.$root.token.getNguoiDungID(),
                  GHICHU: item.KIEUDV_ID,
                  LOAITB_ID: item.LOAITB_ID
                });
              });
              this.insert_kiemtra(danhsach);
            }
          };
          reader.readAsBinaryString(this.file);
        }
      } catch (error) {
        this.$alert("Có lỗi xảy ra", "", {
          dangerouslyUseHTMLString: true,
          confirmButtonText: "OK",
          type: "error",
        });
      } finally {
        this.loading(false);
      }
    },
    checkfile: function () {
      if (this.file == null) {
        this.$alert("Chưa chọn file excel", "", {
          dangerouslyUseHTMLString: true,
          confirmButtonText: "OK",
          type: "error",
        });
        return false;
      }
      if (
        this.file.type !=
        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
      ) {
        this.$alert("File upload không đúng định dạng", "", {
          dangerouslyUseHTMLString: true,
          confirmButtonText: "OK",
          type: "error",
        });
        return false;
      }
      return true;
    },
    kiemTraDuLieu: function (columns) {
      if (columns == null) {
        this.$alert("File có cấu trúc không hợp lệ!", "", {
          dangerouslyUseHTMLString: true,
          confirmButtonText: "OK",
          type: "error",
        });
        return false;
      }
      if (
        !(
          columns[0].toUpperCase() == "MA_TB" &&
          columns[1].toUpperCase() == "DONVI_ID" &&
          columns[2].toUpperCase() == "KIEUDV_ID" &&
          columns[3].toUpperCase() == "LOAITB_ID"
        )
      ) {
        this.$alert("File có cấu trúc không hợp lệ!", "", {
          dangerouslyUseHTMLString: true,
          confirmButtonText: "OK",
          type: "error",
        });
        return false;
      }
      return true;
    },
    findDuplicates(array) {
      var result = [];
      array.forEach((item, index) => {
        if (
          array.findIndex(
            (x) =>
              x.MA_TB.toString().trim() == item.MA_TB.toString().trim() &&
              x.DONVI_ID.toString().trim() == item.DONVI_ID.toString().trim() && x.KIEUDV_ID.toString().trim() == item.KIEUDV_ID.toString().trim()
          ) != index
        ) {
          const index = result.findIndex(
            (r) =>
              r.MA_TB.toString().trim() == item.MA_TB.toString().trim() &&
              r.DONVI_ID.toString().trim() == item.DONVI_ID.toString().trim() && r.KIEUDV_ID.toString().trim() == item.KIEUDV_ID.toString().trim()
          );
          if (index == -1) {
            result.push(item);
          }
        }
      });
      return result;
    },
    async insert_kiemtra(data) {
      let apiParams = {
        nguoidung_id: this.$root.token.getNguoiDungID(),
        nhanvien_id: this.$root.token.getNhanVienID(),
        data: data,
      };
      try {
        this.dataSources = [];
        this.dataSourcesError = [];
        this.loading(true);
        let response = await API.layDSDocFile(this.axios, apiParams);
        if (
          response &&
          response.data &&
          response.data.error_code &&
          response.data.error_code == "BSS-00000000" &&
          response.data.data
        ) {
          if (response.data.data.loi.length > 0) {
            this.columnsError = this.columnsError2;
            response.data.data.loi.forEach((item, index) => {
              this.dataSourcesError.push({
                STT: index + 1,
                ma_tb: item.ma_tb,
                donvi_id: item.donvi_id,
                lydo_loi: item.lydo_loi,
              });
            });
            this.$refs.danhsachloi.showModal();
          } else {
            this.dataSources = data;
          }
        }
      } catch (e) {
        this.$alert("Đã có lỗi xảy ra" + e.toString(), "Lỗi", {
          dangerouslyUseHTMLString: true,
          confirmButtonText: "OK",
          type: "error",
        });
      } finally {
        this.loading(false);
      }
    },
    async capnhat_tram_hc_thuebao(nguoidungid, nhanvienid) {
      try {
        this.loading(true);
        let apiParams = {
          nguoidung_id: nguoidungid,
          nhanvien_id: nhanvienid,
        };
        let response = await API.btnCapNhat(this.axios, apiParams);
        if (
          response &&
          response.data &&
          response.data.error_code &&
          response.data.error_code == "BSS-00000000" &&
          response.data.data
        ) {
          if (response.data.data == "0") {
            this.$alert("Gán trạm cho thuê bao thành công!", "Thông báo", {
              dangerouslyUseHTMLString: true,
              confirmButtonText: "OK",
              type: "success",
            });
            this.file_full_path =null;
            this.dataSources =[];
          } else {
            this.$alert(response.data.data, "Lỗi", {
              dangerouslyUseHTMLString: true,
              confirmButtonText: "OK",
              type: "error",
            });
          }
        } else {
          this.$alert(response.data.data, "Lỗi", {
            dangerouslyUseHTMLString: true,
            confirmButtonText: "OK",
            type: "error",
          });
        }
      } catch (e) {
        this.$alert("Đã có lỗi xảy ra không thể ghi dữ liệu", "Lỗi", {
          dangerouslyUseHTMLString: true,
          confirmButtonText: "OK",
          type: "error",
        });
      } finally {
        this.loading(false);
      }
    },
    onExcelExport: function () {
      if (this.dataSources.length <= 0)
        this.$toast.error('Chưa có dữ liệu trên lưới để xuất excel!');
      else {
         let excelExportProperties = {
           fileName:"DanhSachCanCapNhat.xlsx",
           dataSource: this.dataSources
         };
         this.$refs.grid.excelExport(excelExportProperties);   
      }    
    },
    async capNhat() {
      if (this.dataSources.length == 0) {
        this.$alert("Chưa có thông tin dữ liệu từ file để ghi", "Thông báo", {
          dangerouslyUseHTMLString: true,
          confirmButtonText: "OK",
          type: "error",
        });
        return;
      }
      this.capnhat_tram_hc_thuebao(
        this.$root.token.getNguoiDungID(),
        this.$root.token.getNhanVienID()
      );
    },
    async xuatFile() {
       this.onExcelExport();
    },
    tsbtnMauEx_Click() {
      try {
        this.loading(true)
        let dt = [
          {
            MA_TB: 'A',
            DONVI_ID: '1',
            KIEUDV_ID: '2',
            LOAITB_ID: '58'
          },
         
        ]
        let fileMau = XLSX.utils.json_to_sheet(dt)
        let wb = XLSX.utils.book_new() 
        XLSX.utils.book_append_sheet(wb, fileMau, 'CAPNHATTOTHICONGTHEOFILE') 

        let dtdanhmuc  = [
          { LOAITB_ID: '1', LOAIHINH_TB: 'Điện thoại cố định' },
          { LOAITB_ID: '2', LOAIHINH_TB: 'Máy FAX' },
          { LOAITB_ID: '3', LOAIHINH_TB: 'Trung kế thường' },
          { LOAITB_ID: '4', LOAIHINH_TB: 'Trung kế tương tự' },
          { LOAITB_ID: '5', LOAIHINH_TB: 'Trung kế 2M' },
          { LOAITB_ID: '6', LOAIHINH_TB: 'Tổng đài' },
          { LOAITB_ID: '7', LOAIHINH_TB: 'Điện thoại thẻ' },
          { LOAITB_ID: '8', LOAIHINH_TB: 'VPabx' },
          { LOAITB_ID: '14', LOAIHINH_TB: 'ISDN 2B+D cáp quang' },
          { LOAITB_ID: '15', LOAIHINH_TB: 'ISDN 30B+D cáp đồng' },
          { LOAITB_ID: '16', LOAIHINH_TB: 'ISDN 2B+D cáp đồng' },
          { LOAITB_ID: '17', LOAIHINH_TB: 'ISDN 30B+D cáp quang' },
          { LOAITB_ID: '60', LOAIHINH_TB: 'Centrex' },
          { LOAITB_ID: '108', LOAIHINH_TB: 'Điện thoại vô tuyến' },
          { LOAITB_ID: '57', LOAIHINH_TB: 'Mega tỉnh khác' },
          { LOAITB_ID: '79', LOAIHINH_TB: 'Wifi Fiber' },
          { LOAITB_ID: '82', LOAIHINH_TB: 'VTV-Cab GPON' },
          { LOAITB_ID: '106', LOAIHINH_TB: 'Life TV' },
          { LOAITB_ID: '129', LOAIHINH_TB: 'VSAT-IP' },
          { LOAITB_ID: '130', LOAIHINH_TB: 'Frame relay' },
          { LOAITB_ID: '289', LOAIHINH_TB: 'Bảo hiểm' },
          { LOAITB_ID: '19', LOAIHINH_TB: 'HTVC FiberVNN Digital' },
          { LOAITB_ID: '10', LOAIHINH_TB: '1260' },
          { LOAITB_ID: '11', LOAIHINH_TB: 'Mega' },
          { LOAITB_ID: '18', LOAIHINH_TB: 'HTVC FiberVNN Analog' },
          { LOAITB_ID: '58', LOAIHINH_TB: 'Fiber' },
          { LOAITB_ID: '61', LOAIHINH_TB: 'MyTV' },
          { LOAITB_ID: '65', LOAIHINH_TB: 'SaiGonTV' },
          { LOAITB_ID: '76', LOAIHINH_TB: 'MegaConference' },
          { LOAITB_ID: '81', LOAIHINH_TB: 'IPTV' },
          { LOAITB_ID: '137', LOAIHINH_TB: 'SCTV Fiber' },
          { LOAITB_ID: '171', LOAIHINH_TB: 'MyTV B2B' },
          { LOAITB_ID: '210', LOAIHINH_TB: 'Wifi Mesh' },
          { LOAITB_ID: '266', LOAIHINH_TB: 'Tivi Samsung' },
          { LOAITB_ID: '271', LOAIHINH_TB: 'MyTV OTT' },
          { LOAITB_ID: '274', LOAIHINH_TB: 'Wifi Mesh TM' },
          { LOAITB_ID: '305', LOAIHINH_TB: 'MegaIOT' },
          { LOAITB_ID: '86', LOAIHINH_TB: 'MetroNet FE' },
          { LOAITB_ID: '87', LOAIHINH_TB: 'MetroNet GE' },
          { LOAITB_ID: '88', LOAIHINH_TB: 'MetroNet 2FE' },
          { LOAITB_ID: '172', LOAIHINH_TB: 'Hội nghị truyền hình' },
          { LOAITB_ID: '233', LOAIHINH_TB: 'MetroNet 10GE' },
          { LOAITB_ID: '244', LOAIHINH_TB: 'Metronet_POP' },
          { LOAITB_ID: '245', LOAIHINH_TB: 'Metronet_NNI' },
          { LOAITB_ID: '85', LOAIHINH_TB: 'Kênh truyền dẫn VNP' },
          { LOAITB_ID: '128', LOAIHINH_TB: 'Sợi quang trắng' },
          { LOAITB_ID: '23', LOAIHINH_TB: 'Megawan ADSL' },
          { LOAITB_ID: '25', LOAIHINH_TB: 'Megawan SHDSL' },
          { LOAITB_ID: '26', LOAIHINH_TB: 'Megawan quang FE' },
          { LOAITB_ID: '30', LOAIHINH_TB: 'Megawan quang GE' },
          { LOAITB_ID: '74', LOAIHINH_TB: 'Megawan ADSL 2M' },
          { LOAITB_ID: '107', LOAIHINH_TB: 'Cáp quang trắng' },
          { LOAITB_ID: '134', LOAIHINH_TB: 'Internet trực tiếp' },
          { LOAITB_ID: '246', LOAIHINH_TB: 'Megawan_POP' },
          { LOAITB_ID: '247', LOAIHINH_TB: 'Megawan_NNI' },
          { LOAITB_ID: '259', LOAIHINH_TB: 'IP Transit LL' },
          { LOAITB_ID: '260', LOAIHINH_TB: 'IP Port' },
          { LOAITB_ID: '275', LOAIHINH_TB: 'Megawan 10GE' },
          { LOAITB_ID: '306', LOAIHINH_TB: 'MegaWanIOT' },
          { LOAITB_ID: '93', LOAIHINH_TB: 'Leasedline V35' },
          { LOAITB_ID: '162', LOAIHINH_TB: 'Leasedline 34M (16E1)' },
          { LOAITB_ID: '163', LOAIHINH_TB: 'Leasedline 10GE' },
          { LOAITB_ID: '164', LOAIHINH_TB: 'Leasedline STM4' },
          { LOAITB_ID: '165', LOAIHINH_TB: 'Leasedline STM16' },
          { LOAITB_ID: '166', LOAIHINH_TB: 'Leasedline Gpon' },
          { LOAITB_ID: '22', LOAIHINH_TB: 'Leasedline FE' },
          { LOAITB_ID: '29', LOAIHINH_TB: 'Đôi cáp đồng' },
          { LOAITB_ID: '33', LOAIHINH_TB: 'Đôi cáp quang' },
          { LOAITB_ID: '66', LOAIHINH_TB: 'Leasedline GE' },
          { LOAITB_ID: '67', LOAIHINH_TB: 'Leasedline E1' },
          { LOAITB_ID: '68', LOAIHINH_TB: 'Leasedline STM1' },
          { LOAITB_ID: '69', LOAIHINH_TB: 'Leasedline nx64k' },
          { LOAITB_ID: '71', LOAIHINH_TB: 'Nail up' },
          { LOAITB_ID: '72', LOAIHINH_TB: 'Trực thông đồng' },
          { LOAITB_ID: '73', LOAIHINH_TB: 'Trực thông quang' },
          { LOAITB_ID: '94', LOAIHINH_TB: 'Leasedline V35' },
          { LOAITB_ID: '95', LOAIHINH_TB: 'TSL SHDSL' },
          { LOAITB_ID: '123', LOAIHINH_TB: 'Cáp quang trắng' },
          { LOAITB_ID: '257', LOAIHINH_TB: 'Kênh quá giang' },
          { LOAITB_ID: '258', LOAIHINH_TB: 'Kênh dung lượng' },
          { LOAITB_ID: '273', LOAIHINH_TB: 'Leasedline POP' },
          { LOAITB_ID: '320', LOAIHINH_TB: 'Kênh ngoài lãnh thổ' },
          { LOAITB_ID: '32', LOAIHINH_TB: 'Gphone trả sau' },
          { LOAITB_ID: '34', LOAIHINH_TB: 'Gphone trả trước' },
          { LOAITB_ID: '109', LOAIHINH_TB: 'B-FONE' },
          { LOAITB_ID: '62', LOAIHINH_TB: 'Thuê bao POTS' },
          { LOAITB_ID: '63', LOAIHINH_TB: 'Thuê bao SIP' },
          { LOAITB_ID: '64', LOAIHINH_TB: 'Thuê bao RGW' },
          { LOAITB_ID: '77', LOAIHINH_TB: 'Thuê bao Sip Trunking' },
          { LOAITB_ID: '84', LOAIHINH_TB: 'Camera An Ninh' },
          { LOAITB_ID: '31', LOAIHINH_TB: 'VNPT Camera' },
          { LOAITB_ID: '37', LOAIHINH_TB: 'IP Camera' },
          { LOAITB_ID: '70', LOAIHINH_TB: 'Camera học đường' },
          { LOAITB_ID: '83', LOAIHINH_TB: 'Cloud Camera' },
          { LOAITB_ID: '222', LOAIHINH_TB: 'Indoor Camera PT' },
          { LOAITB_ID: '224', LOAIHINH_TB: 'Outdoor Camera PT' },
          { LOAITB_ID: '588', LOAIHINH_TB: 'Home Cloud camera' }
        ];

        let sheetDM = XLSX.utils.json_to_sheet(dtdanhmuc);

        XLSX.utils.book_append_sheet(wb, sheetDM, 'DanhMuc');

        XLSX.writeFile(wb, 'FileMau.xls')
      } catch (e) {
        this.$root.toastError('Có lỗi khi xuất excel mẫu ' + e.toString())
      } finally {
        this.loading(false)
      }
    },
  },
};
</script>
