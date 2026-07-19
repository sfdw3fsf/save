<template src="./index.html"></template>

<script>
import xlsx from "xlsx";
import { mapActions, mapState } from "vuex";
import breadcrumb from "@/components/breadcrumb";
import gridview from "@/components/Shared/gridview";
import api from "./api/index.js";
import ModalXuatDuLieu from "../../ECMS/TraCuuSplitterTheoPortOLT/ModalXuatDuLieu/index.vue";
import moment from 'moment'
export default {
  components: { breadcrumb, gridview, ModalXuatDuLieu },
  name: "",

  data() {
    return {
      header: {
        title: "Đọc file trả phiếu khiếu nại",
        list: [
          { name: "Xử lý khiếu nại", link: { name: "Ui.cards" } },
          {
            name: "Đọc file trả phiếu khiếu nại",
            link: { name: "Ui.buttons" },
          },
        ],
      },
      tenFile: '',
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
          headerText: "Mã TB",
          allowFiltering: true,
          width: 150,
        },
        {
          fieldName: "NGAYTRA",
          headerText: "Ngày trả",
          allowFiltering: true,
          width: 150,
        },
        {
          fieldName: "ND_TRA",
          headerText: "Nội dung trả",
          allowFiltering: true,
        },
        {
          fieldName: "GHI_CHU",
          headerText: "Ghi chú",
          allowFiltering: true,
        },
      ],
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
          headerText: "Mã TB",
          allowFiltering: true,
          width: 150,
        },
        {
          fieldName: "NGAYTRA",
          headerText: "Ngày trả",
          allowFiltering: true,
          width: 150,
        },
        {
          fieldName: "ND_TRA",
          headerText: "Nội dung trả",
          allowFiltering: true,
        },
        {
          fieldName: "GHI_CHU",
          headerText: "Ghi chú",
          allowFiltering: true,
        },
        {
              fieldName: "ND_L",
              headerText: "Lỗi",
              allowFiltering: true,
              textAlign: "Left",
              customAttributes: { class: 'text-nd-loi' }
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
      if (!files.length) {
        return;
      } else if (!/\.(xls|xlsx)$/.test(files[0].name.toLowerCase())) {
        return this.$root.toastError("File excel không đúng định dạng!");
      }

      if( files[0].name ) {
        this.tenFile = files[0].name
      }
      this.readFile(files);
    },

    uploadFile() {
      this.$refs.fileInput.click();
    },

    readFile(files) {
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
          let columns=ws[0]
          if (!(columns[0]!=undefined&&columns[0].toString().trim().toUpperCase() == "MA_TB" 
              &&columns[1]!=undefined&&columns[1].toString().trim().toUpperCase() == "NGAYTRA"
              &&columns[2]!=undefined&&columns[2].toString().trim().toUpperCase() == "ND_TRA"
              &&columns[3]!=undefined&&columns[3].toString().trim().toUpperCase() == "GHI_CHU")){
                this.$toast.error('File có cấu trúc không hợp lệ!')
                return
          }
          // if (
          //   !excellist[0].hasOwnProperty("MA_TB") ||
          //   !excellist[0].hasOwnProperty("NGAYTRA") ||
          //   !excellist[0].hasOwnProperty("ND_TRA") ||
          //   !excellist[0].hasOwnProperty("GHI_CHU")
          // ) {
          //   this.$root.toastError("File không đúng định dạng!");
          //   return;
          // }

          const excellist = [];
          // Edit data
          if (ws.length > 1) {
            for (var i = 1; i < ws.length; i++) {
              excellist.push({
                MA_TB:ws[i][0]?ws[i][0].toString():'',
                NGAYTRA:ws[i][1]?ws[i][1]:'',
                ND_TRA:ws[i][2]?ws[i][2].toString():'',
                GHI_CHU:ws[i][3]?ws[i][3].toString():''
              });
            }
          } else {
            this.$root.toastError("File không có data!");
          }
          console.log('excellist cot', excellist[0])
          console.log('excellist', excellist)

          //this.clearData();
          // console.log(excellist);
          // this.fieldsRender = this.fields;
          // if (
          //   !excellist[0].hasOwnProperty("MA_TB") ||
          //   !excellist[0].hasOwnProperty("NGAYTRA") ||
          //   !excellist[0].hasOwnProperty("ND_TRA") ||
          //   !excellist[0].hasOwnProperty("GHI_CHU")
          // ) {
          //   this.$root.toastError("File không đúng định dạng!");
          //   return;
          // }

          
          this.tableDataLoi = [];
          this.tableDataOk = [];
          for (let k = 0; k < excellist.length; k++) {
            console.log(excellist[k]);
            let item = excellist[k];
            let check = this.kiemTraDuLieuFile(item);
            console.log(check);
            let obj = {
              STT: k+1,
              MA_TB: item.MA_TB,
              NGAYTRA: item.NGAYTRA,
              ND_TRA: item.ND_TRA,
              GHI_CHU: item.GHI_CHU,
              ND_L: check,
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
          console.log(dataLuu);
          api.kiemTrDuLieuTraPhieu(this.axios, dataLuu).then((res) => {
            console.log(res.data);
            if (res.data && res.data.error_code == "BSS-00000000") {
            }
          });
          console.log(this.tableDataLoi);
          if (this.tableDataLoi.length > 0) {
            this.activeTabs = false;
          } else {
            this.$root.toastSuccess("File hợp lệ!");
            this.renderTable(excellist);
          }
          //console.log("Read results", excellist); // At this point, you get an array containing objects that need to be processed
        } catch (e) {
          console.log(e)
          //return alert("Read failure!" + e);
        }
      };
      fileReader.readAsBinaryString(files[0]);
    },
    
    kiemTraDuLieuFile(data) {
      let rt = "OK";
     
      if (data.MA_TB && data.MA_TB == "") {
        rt = "Mã thuê bao không được để trống!";
        return rt
      }
      if (data.NGAYTRA && data.NGAYTRA == "") {
        rt = "Ngày trả không được để trống!";
        return rt
      }
      if (data.ND_TRA && data.ND_TRA == "") {
        rt = "Nội dung trả không được để trống!";
        return rt
      }
      if (data.GHI_CHU && data.GHI_CHU == "") {
        rt = "Nội dung ghi chú không được để trống!";
        return rt
      }
      // let regex_date = new RegExp(
      //   "^\d{4}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])$"
      // );
      // let regex_date = /^\d{4}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])$/
      // if (!regex_date.test(data.NGAYTRA.toString())) {
      //   rt = "Ngày XL không đúng định dạng, định dạng chuẩn là yyyyMMdd!";
      // } 
      //
      console.log('NGAYTRA', data.NGAYTRA.toString())
      console.log('NGAYTRA_BN', moment(data.NGAYTRA.toString(), 'YYYYMMDD').format('YYYYMMDD'))
      console.log('NGAYTRA_BN1', data.NGAYTRA.toString())

      if(moment(data.NGAYTRA.toString(), 'YYYYMMDD').format('YYYYMMDD') != data.NGAYTRA.toString()){
        rt = "Ngày XL không đúng định dạng, định dạng chuẩn là yyyyMMdd!";
        return rt
      }

      return rt;
    },

    XuatFile() {
      if(this.tableDataLoi.length == 0){
        this.$root.toastWarning("Không có dữ liệu lỗi để xuất!");
        return false;
      }
      this.$bvModal.show("modal-xuat-dulieu");
    },
    renderTable(data) {
      for (let i = 0; i < data.length; i++) {
        let obj = {
          STT: i,
          MA_TB: data[i].MA_TB,
          NGAYTRA: data[i].NGAYTRA,
          ND_TRA: item.ND_TRA,
          GHI_CHU: item.GHI_CHU,
        };
        this.tableData.push(obj);
      }
    },

    chapNhan() {
      if (this.tableDataLoi.length == 0 && this.tableDataOk.length > 0) {
        let data = {
          vnguoidung_id: this.$root.token.getNguoiDungID(),
          vnhanvien_id: this.$root.token.getNhanVienID(),
          vmay_cn: this.$root.token.getUserName(),
          vnguoi_cn: this.$root.token.getUserName(),
          vip_cn: '127.0.0.1',
        };
        api.capNhatDuLieuTraPhieu(this.axios, data).then((res) => {
          console.log(res.data);
          if (res.data && res.data.error_code == "BSS-00000000") {
            this.$root.toastSuccess("Cập nhật kết quả thành công!");
          } else {
            this.$root.toastError(
              "Lỗi khi cập nhật kết quả: " + res.data.message
            );
          }
        });
      }else{
         this.$root.toastError(
              "Vui lòng import file đúng định dạng để thực hiện!"
            );
      }
    },
  },
};
</script>

<style >
.e-grid .e-rowcell.text-nd-loi {
  color: red !important;
}
</style>