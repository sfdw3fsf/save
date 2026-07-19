<template src="./index.html"></template>

<script>
import xlsx from "xlsx";
import { Workbook } from "@syncfusion/ej2-excel-export/index";
import { mapActions, mapState } from "vuex";
import breadcrumb from "@/components/breadcrumb";
import gridview from "@/components/Shared/gridview";
import api from "./api/index.js";
import ModalXuatDuLieu from "../../ECMS/TraCuuSplitterTheoPortOLT/ModalXuatDuLieu/index.vue";
export default {
  components: { breadcrumb, gridview, ModalXuatDuLieu },
  name: "",

  data() {
    return {
      header: {
        title: "Đọc file cập nhật chờ xử lý khiếu nại",
        list: [
          { name: "Xử lý khiếu nại", link: { name: "Ui.cards" } },
          {
            name: "Đọc file cập nhật chờ xử lý khiếu nại",
            link: { name: "Ui.buttons" },
          },
        ],
      },
      tenFile: '',
      showTT : false,
      tableCollumns: [
        {
          fieldName: "STT",
          headerText: "STT",
          allowFiltering: true,
          textAlign: "Center",
          width: 100,
        },
        {
          fieldName: "MA_TB",
          headerText: "Mã thuê bao",
          allowFiltering: true,
        },
        {
          fieldName: "KETQUA_XL",
          headerText: "Kết quả xử lý",
          allowFiltering: true,
          width: 100,
        },
        {
          fieldName: "KETQUA_LOI",
          headerText: "Kết quả lỗi",
          allowFiltering: true,
          width: 100,
        },
        {
          fieldName: "NGAY_XL",
          headerText: "Ngày XL",
          allowFiltering: true,
          width: 120,
        },
        {
          fieldName: "TIEN_GC",
          headerText: "Tiền GC",
          allowFiltering: true,
        },
        {
          fieldName: "NGUYENNHAN",
          headerText: "Nguyên nhân",
          allowFiltering: true,
          textAlign: "Left",
        },
        {
          fieldName: "ND_TRALOI",
          headerText: "Nội dung trả lời",
          allowFiltering: true,
          textAlign: "Left",
        },
      ],
      //MA_TB, KETQUA_XL, NGAY_XL, KETQUA_LOI, TIEN_GC, NGUYENNHAN, ND_TRALOI, MANV_XL
      tableCollumnsLoi: [
        {
          fieldName: "STT",
          headerText: "STT",
          allowFiltering: true,
          textAlign: "Center",
          width: 100,
        },
        {
          fieldName: "MA_TB",
          headerText: "Mã thuê bao",
          allowFiltering: true,
        },
        {
          fieldName: "KETQUA_XL",
          headerText: "Kết quả xử lý",
          allowFiltering: true,
          width: 100,
        },
        {
          fieldName: "KETQUA_LOI",
          headerText: "Kết quả lỗi",
          allowFiltering: true,
          width: 100,
        },
        {
          fieldName: "NGAY_XL",
          headerText: "Ngày XL",
          allowFiltering: true,
          width: 120,
        },
        {
          fieldName: "TIEN_GC",
          headerText: "Tiền GC",
          allowFiltering: true,
        },
        {
          fieldName: "NGUYENNHAN",
          headerText: "Nguyên nhân",
          allowFiltering: true,
          textAlign: "Left",
        },
        {
          fieldName: "ND_TRALOI",
          headerText: "Nội dung trả lời",
          allowFiltering: true,
          textAlign: "Left",
        },
        {
            fieldName: "ND_L",
            headerText: "Lỗi",
            allowFiltering: true,
            textAlign: "Left",
        }
      ],
      tableData: [],
      tableDataLoi: [],
      tableDataOk: [],
      fieldsRender: [],
      activeTabs: true,
    };
  },
  created() {},
  mounted() {},
  computed: {
    ...mapState("laydanhsachno", ["danhsachThueBao"]),
    dsThuocTinh() {
      return this.tableCollumnsLoi.map((x) => x.fieldName);
    },
  },
  watch: {
    optionTrangThai() {
      this.layDanhSachHDTB();
    },
  },
  methods: {
    async importExcel(e) {
      const files = e.target.files;
      console.log(e);
      if (!files.length) {
        return;
      } else if (!/\.(xls|xlsx)$/.test(files[0].name.toLowerCase())) {
        return this.$root.toastError("File excel không đúng định dạng!");
      }
       if( files[0].name ) {
        this.tenFile = files[0].name
      }
      this.readFile1(files);
    },

    uploadFile() {
      this.$refs.fileInput.click();
    },

    readFile1(files) {
      const fileReader = new FileReader();
      fileReader.onload = async (ev) => {
        try {
          const data = ev.target.result;
          const XLSX = xlsx;
          const workbook = XLSX.read(data, {
            type: "binary",
          });
          const wsname = workbook.SheetNames[0];
          console.log(wsname);
          const ws = XLSX.utils.sheet_to_json(workbook.Sheets[wsname], { header: 1 });
          //MA_TB, KETQUA_XL, NGAY_XL, KETQUA_LOI, TIEN_GC, NGUYENNHAN, ND_TRALOI
          let columns=ws[0]
          console.log('columns',columns);
          if (!(columns[0]!=undefined&&columns[0].toString().trim().toUpperCase() == "MA_TB" 
              &&columns[1]!=undefined&&columns[1].toString().trim().toUpperCase() == "KETQUA_XL"
              &&columns[2]!=undefined&&columns[2].toString().trim().toUpperCase() == "NGAY_XL"
              &&columns[3]!=undefined&&columns[3].toString().trim().toUpperCase() == "KETQUA_LOI"
              &&columns[4]!=undefined&&columns[4].toString().trim().toUpperCase() == "TIEN_GC"
              &&columns[5]!=undefined&&columns[5].toString().trim().toUpperCase() == "NGUYENNHAN"
              &&columns[6]!=undefined&&columns[6].toString().trim().toUpperCase() == "ND_TRALOI"
              &&columns[7]!=undefined&&columns[7].toString().trim().toUpperCase() == "MANV_XL")){
                this.$toast.error('File không đúng định dạng: MA_TB, KETQUA_XL, NGAY_XL, KETQUA_LOI, TIEN_GC, NGUYENNHAN, ND_TRALOI, MANV_XL')
                return
          }
          const excellist = [];
          if (ws.length > 1) {
            for (var i = 1; i < ws.length; i++) {
              excellist.push({
                MA_TB:ws[i][0]?ws[i][0].toString():'',
                KETQUA_XL:ws[i][1]?ws[i][1].toString():'',
                NGAY_XL:ws[i][2]?ws[i][2].toString():'',
                KETQUA_LOI:ws[i][3]?ws[i][3].toString():'',
                TIEN_GC:ws[i][4]?ws[i][4].toString():'',
                NGUYENNHAN:ws[i][5]?ws[i][5].toString():'',
                ND_TRALOI:ws[i][6]?ws[i][6].toString():'',
                MANV_XL:ws[i][7]?ws[i][7].toString():'',
              });
            }
          } else {
            this.$root.toastError("File không có data!");
          }

          console.log('excellist', excellist);

          this.tableDataLoi = [];
          this.tableDataOk = [];
          for (let k = 0; k < excellist.length; k++) {
            let item = excellist[k];
            let check = this.kiemTraDuLieuFile(item);
            let obj = {
              STT: k+1,
              MA_TB: item.MA_TB,
              KETQUA_XL: item.KETQUA_XL,
              KETQUA_LOI: item.KETQUA_LOI,
              NGAY_XL: item.NGAY_XL,
              TIEN_GC: item.TIEN_GC,
              NGUYENNHAN: item.NGUYENNHAN,
              ND_TRALOI: item.ND_TRALOI,
              MANV_XL: item.MANV_XL,
              ND_L: check
            };
            if (check != "OK") {
              this.tableDataLoi.push(obj);
            } else {
              this.tableDataOk.push(obj);
            }
          }

          // insert ban ghi
          let dataLuu = {
            vdsDocFile: JSON.stringify(this.tableDataOk),
            vdsLoi: JSON.stringify(this.tableDataLoi),
            vnguoidung_id: this.$root.token.getNguoiDungID(),
            vnhanvien_id: this.$root.token.getNhanVienID(),
            vip_cn: "",
            vmay_cn: "",
            vnguoi_cn: "",
          };

          let dataRs=await this.insertBanGhi(dataLuu)
          console.log('dataRs', dataRs)
          // await api.insertBanGhi(this.axios, dataLuu).then((res) => {
          //   console.log(res.data);
          //   if (res.data && res.data.error_code == "BSS-00000000") {
          //   }
          // });

          console.log('tableDataLoi',this.tableDataLoi);
          if (this.tableDataLoi.length > 0) {
            this.activeTabs = false;
                      
          } else {
            this.$root.toastSuccess("File hợp lệ!");
            this.renderTable(excellist);
          }

          //console.log("Read results", excellist); // At this point, you get an array containing objects that need to be processed
        } catch (e) {
          return alert("Read failure!" + e);
        }
      };
      fileReader.readAsBinaryString(files[0]);
    },
    async insertBanGhi(dataLuu){
        try{
            this.loading(true)
            let response = await api.insertBanGhi(this.axios, dataLuu)
            this.loading(false)
            if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                return response.data.data
            }else{
                return null
            }
        }catch(e){
            this.loading(false)
            return null
        }
    },
    kiemTraDuLieuFile(data) {
      let rt = "OK";
      if (data.MA_TB && data.MA_TB == "") {
        rt = "Mã thuê bao không được để trống!";
      }
      if (data.KETQUA_XL && data.KETQUA_XL == "") {
        rt = "Kết quả XL không được để trống!";
      }
      if (data.NGAY_XL && data.NGAY_XL == "") {
        rt = "Ngày XL không được để trống!";
      }
      if (data.MANV_XL && data.MANV_XL == "") {
        rt = "Nhân viên XL không được để trống!";
      }
      if (data.ND_TRALOI && data.ND_TRALOI == "") {
        rt = "Nội dung trả lời không được để trống!";
      }
      if (data.KETQUA_XL && data.KETQUA_XL != "1" && data.KETQUA_XL != "2") {
        rt =
          "Kết quả XL chỉ được nhập là 1 (Xử lý được) hoặc 2 (Không xử lý được)!";
      }
      // let regex_date = new RegExp(
      //   "^\d{4}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])$"
      // );
      let regex_date = /^\d{4}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])$/
      if (!regex_date.test(data.NGAY_XL.toString())) {
        rt = "Ngày XL không đúng định dạng, định dạng chuẩn là yyyyMMdd!";
      }
      return rt;
    },

    XuatFile() {
      if(this.tableDataLoi.length > 0){
        this.$bvModal.show("modal-xuat-dulieu");  
      }else{
        this.$root.toastError(
              "Không có dữ liệu lỗi để xuất file"
            );
      }
      
    },
    renderTable(data) {
      for (let i = 0; i < data.length; i++) {
        let obj = {
          STT: i,
          MA_TB: data[i].MA_TB,
          KQ_XL: data[i].KETQUA_XL,
          KQ_L: data[i].KETQUA_LOI,
          NGAY_XL: data[i].NGAY_XL,
          TIEN_GC: data[i].MANV_XL,
          NGUYEN_NHAN: data[i].NGUYENNHAN,
          ND_TL: data[i].ND_TRALOI,
        };
        this.tableData.push(obj);
      }
    },

    onTaiFileMauClicked() {
       let columns = [
        { index: 1, width: 130 },
        { index: 2, width: 130 },
        { index: 3, width: 130 },
        { index: 4, width: 130 },
        { index: 5, width: 130 },
        { index: 6, width: 130 },
        { index: 7, width: 130 },
      ];
      let rows = [];
      let headerRow = {
        index: 1,
        
        cells: [
          {
            index: 1,
            value: "MA_TB",
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
            value: "KETQUA_XL",
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
            value: "KETQUA_LOI",
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
            value: "NGAY_XL",
            colSpan: 1,
            style: {
              borders: {
                color: "#00000000",
                lineStyle: "thin",
              },
            },
          },
          {
            index: 5,
            value: "MANV_XL",
            colSpan: 1,
            style: {
              borders: {
                color: "#00000000",
                lineStyle: "thin",
              },
            },
          },
          {
            index: 6,
            value: "TIEN_GC",
            colSpan: 1,
            style: {
              borders: {
                color: "#00000000",
                lineStyle: "thin",
              },
            },
          },
          {
            index: 7,
            value: "NGUYENNHAN",
            colSpan: 1,
            style: {
              borders: {
                color: "#00000000",
                lineStyle: "thin",
              },
            },
          },
          {
            index: 8,
            value: "ND_TRALOI",
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
      workbook.save("filemau.xlsx");
    },
  

    chapNhan() {
      if (this.tableDataLoi.length > 0 || this.tableDataOk.length > 0) {
        let data = {
          vnguoidung_id: this.$root.token.getNguoiDungID(),
          vnhanvien_id: this.$root.token.getNhanVienID(),
          vmay_cn: "",
          vnguoi_cn: "",
          vip_cn: "",
        };
        this.loading(true)
        api.capNhatFile(this.axios, data).then((res) => {
          this.loading(false)
          console.log(res.data);
          if (res.data && res.data.error_code == "BSS-00000000") {
            this.$root.toastSuccess("Cập nhật kết quả thành công!");
          } else {
            this.$root.toastError(
              "Lỗi khi cập nhật kết quả: " + res.data.message
            );
          }
        }).catch(e=>{
          this.loading(false)
          this.$root.toastError(
            "Lỗi khi cập nhật kết quả"
          );
        });
      }else{
         this.$root.toastError(
              "Vui lòng import file để thực hiện!"
            );
      }
    },
  },
};
</script>

<style>
</style>