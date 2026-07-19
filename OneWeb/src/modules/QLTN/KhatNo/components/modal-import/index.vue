<template src="./index.html"></template>
<style scoped>
.body-content {
  padding: 8px 16px 16px;
}
.text-line {
  width: 100%;
  border-bottom: 1px solid #cbcbcb;
  line-height: 0.1em;
  margin: 5px 0 20px;
}
.text-line span,
label {
  font-weight: 600;
  background: #fff;
  display: initial;
}
</style>
<style>
 .overlay-common .loading {
  display: flex;
  justify-content: center;
  flex-direction: column;
}

.overlay-common.show {
  display: flex;
  justify-content: center;
}

.overlay-common {
  background-color: rgba(0, 0, 0, 0.7);
}

.overlay-common .loading-panel {
  display: flex;
  text-align: center;
  background-color: #fff;
  padding: 10px 15px;
  align-items: center;
  color: #035fcc;
  border: 1px solid #035fcc;
  border-radius: 7px;
}

.overlay-common .loading-panel .loading-content {
  flex-grow: 1;
  padding-left: 10px;
}
</style>
<script>
import xlsx from "xlsx";
import moment from "moment";
import { export_json_to_excel } from "../../../TraCuuDichVu/Export2Excel";

const isNumeric = (str) => {
  if(str.length == 0) return false;
  if (!isNaN(str)) return true;
  if (typeof str != "string") return false;
  return !isNaN(str) && !isNaN(parseFloat(str));
};

export default {
  name: "ModalImport",
  components: {},
  props: ["dsDichVu", "kyCuoc", "ngayHenTra"],
  data: function() {
    return {
      dichVuId: 0,
      dvIdLock: 0,
      files: [],
      txtFileName: "",
      gridViewData: [],
      gridViewColumns: [],
      tableTitle: "",
      validData: [],
      isSubmitting: false
    };
  },
  validations: {},
  watch: {},
  created: async function() {
    if (this.dsDichVu) this.dichVuId = this.dsDichVu[0].DICHVUVT_ID;
  },
  async mounted() {},
  methods: {
    async onChangeFile(e) {
      const files = e.target.files;
      this.files = files;
      if (files.length > 0) {
        this.txtFileName = files[0].name;
        await this.importExcel();
      }
    },
    clearFileInput() {
      this.$refs.fileInput.value = "";
    },
    onAccept() {
      if (this.validData.length == 0) {
        this.$toast.error(
          "Danh sách trong file đang lỗi, hãy kiểm tra lại file đầu vào! "
        );
      } else {
        this.addNew();
      }
    },
    onLoadDs() {
      if (this.files.length == 0) {
        this.$toast.error("Chưa chọn tập tin!");
      } else {
        this.importExcel();
      }
    },
    async importExcel() {
      this.$root.showLoading(true);
      this.validData = [];
      const files = this.files;
      const fileReader = new FileReader();
      fileReader.onload = async ev => {
        try {
          const data = ev.target.result;
          const XLSX = xlsx;
          const workbook = XLSX.read(data, {
            type: "binary"
          });
          const wsname = workbook.SheetNames[0];
          const ws = XLSX.utils.sheet_to_json(workbook.Sheets[wsname], {
            defval: " "
          });

          if (!ws || ws.length == 0) {
            const txt = XLSX.utils.sheet_to_csv(workbook.Sheets[wsname]);
            if (txt.length > 0) {
              const errorMsg = [];
              const txtArr = txt.split(",");
              if (txtArr.length < 3) {
                errorMsg.push(
                  "Danh sách file phải đủ 3 cột : MA_TB, TIENNO, LYDO"
                );
              } else {
                txtArr[0] != "MA_TB" &&
                  errorMsg.push(
                    "Tên cột đầu tiên không đúng định dạng : MA_TB"
                  );
                txtArr[1] != "TIENNO" &&
                  errorMsg.push("Tên cột thứ 2 không đúng định dạng : TIENNO");
                txtArr[2].slice(0, -1) != "LYDO" &&
                  errorMsg.push("Tên cột thứ 3 không đúng định dạng : LYDO");
              }
              errorMsg.length > 0 && this.$toast.error(errorMsg.join("\n"));
            }
          } else {
            if (!this.checkFormat(ws)) {
              ws.length > 0 && (await this.getDataView(ws));
            } else {
              this.$root.showLoading(false);
              this.gridViewData = [];
            }
          }
        } catch (e) {
          return alert("Read failure!" + e);
        }
      };

      fileReader.readAsBinaryString(files[0]);
      // var input = document.getElementById("upload");
    },
    checkFormat(data) {
      const errorMsg = [];
      if (data && data.length > 0) {
        let keys = Object.keys(data[0]);
        data.forEach(el => {
          let key = Object.keys(el);
          keys = [...new Set(keys.concat(key))];
        });
        if (keys.length < 3) {
          errorMsg.push("Danh sách file phải đủ 3 cột : MA_TB, TIENNO, LYDO");
        } else {
          keys[0] != "MA_TB" &&
            errorMsg.push("Tên cột đầu tiên không đúng định dạng : MA_TB");
          keys[1] != "TIENNO" &&
            errorMsg.push("Tên cột thứ 2 không đúng định dạng : TIENNO");
          keys[2] != "LYDO" &&
            errorMsg.push("Tên cột thứ 3 không đúng định dạng : LYDO");
        }

        if (errorMsg.length == 0) {
          let maTbDuplicateStr = "";
          let matbArr = [];
          data.forEach(element => {
            let matb = element.MA_TB.toString().trim();
            if (matbArr.includes(matb)) {
              maTbDuplicateStr += `${matb},`;
            } else {
              matbArr.push(matb);
            }
          });
          maTbDuplicateStr &&
            errorMsg.push(
              `Thuê bao ${maTbDuplicateStr.slice(
                0,
                -1
              )} xuất hiện nhiều lần trong danh sách!`
            );
        }
        if (errorMsg.length == 0) {
          data.every(element => {
            if(!isNumeric(element.TIENNO.toString().trim())) {
              errorMsg.push(`Thuê bao ${element.MA_TB} tiền nợ không đúng định dạng`);
              return false;
            }
          });
        }
        if (errorMsg.length == 0) {
          data.every(element => {
            if(element.TIENNO.toString().trim().length > 12) {
              errorMsg.push(`Thuê bao ${element.MA_TB} tiền nợ quá 12 ký tự`);
              return false;
            }
          });
        }

        if (errorMsg.length == 0) {
          data.every(element => {
            if( (new TextEncoder().encode(element.LYDO.trim())).length > 100) {
              errorMsg.push(`Thuê bao ${element.MA_TB} lý do quá 100 ký tự`);
              return false;
            }
          });
        }

      } else {
        errorMsg.push("File không có dữ liệu!");
      }
      if (errorMsg.length > 0) {
        this.$toast.error(errorMsg.join("\n"));
        return true;
      } else {
        return false;
      }
    },
    getDataView: async function(srcData) {
      const dstData = [];
      //Make danh sach null ly do
      srcData.map(item => {
        if (
          item.MA_TB &&
          item.MA_TB.toString().trim() != "" &&
          (!item.LYDO || (item.LYDO && item.LYDO.toString().trim() == ""))
        ) {
          dstData.push({
            MA_TB: item.MA_TB,
            TRANGTHAI: "Lý do bị null"
          });
        }
      });
      if (dstData.length > 0) {
        this.tableTitle = "Danh sách thuê bao không thỏa mãn";
      }

      //Make danh sach thue bao khong ton tai
      if (dstData.length == 0) {
        for (let index = 0; index < srcData.length; index++) {
          let exist = await this.isExistTB(srcData[index].MA_TB, this.dichVuId);
          if (!exist) {
            dstData.push({
              MA_TB: srcData[index].MA_TB,
              TRANGTHAI: "Sai dịch vụ viễn thông"
            });
          }
        }

        if (dstData.length > 0) {
          this.tableTitle = "Danh sách thuê bao lỗi";
        }
      }
      //Make danh sach thue bao da khat no
      if (dstData.length == 0) {
        const dsKhatNo = await this.getDsKhatNo();
        if (dsKhatNo) {
          const maTbs = dsKhatNo.map(item => {
            return item.MA_TB;
          });
          srcData.map(item => {
            if (maTbs.includes(item.MA_TB.toString().trim())) {
              dstData.push({
                MA_TB: item.MA_TB,
                TRANGTHAI: "Thuê bao đã khất nợ"
              });
            }
          });
          if (dstData.length > 0) {
            this.tableTitle = "Danh sách thuê bao lỗi";
          }
        }
      }

      //Make danh sach thue bao hop le
      if (dstData.length == 0) {
        srcData.forEach(item => {
          dstData.push({
            MA_TB: item.MA_TB,
            TRANGTHAI: ""
          });
        });
        this.validData = srcData;
        this.tableTitle = "Danh sách thuê bao khóa mở";
      }

      if (dstData.length > 0) {
        this.gridViewData = dstData;
        this.dvIdLock = this.dichVuId;
        this.gridViewColumns = [
          {
            fieldName: "MA_TB",
            headerText: "Mã TB",
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: "TRANGTHAI",
            headerText: "Trạng thái",
            allowFiltering: true,
            allowSorting: false
          }
        ];
        this.$refs.tabelmodal.rowClick(0, null);
      }
      this.$root.showLoading(false);
    },
    isExistTB: async function(maTb, dichVuId) {
      let isEx = false;
      try {
        var rs = await this.$root.context.get(
          "/web-thuno/api/thu-no/tra-cuu-danh-ba/danh-ba-thue-bao-theo-ma",
          { maTB: maTb.trim() }
        );
        if (rs && rs.data.length > 0) {
          rs.data.forEach(el => {
            if (el.DICHVUVT_ID == dichVuId) {
              isEx = true;
            }
          });
        }
        return isEx;
      } catch (error) {
        return false;
      }
    },
    downFileMau() {
      this.exportTemplate();
    },
    exportTemplate() {
      const tHeader = ["MA_TB", "TIENNO", "LYDO"];
      const data = [];
      export_json_to_excel({
        header: tHeader,
        data,
        filename: "template",
        sheetname: "data"
      });
    },
    getDsKhatNo: async function() {
      try {
        var rs = await this.$root.context.get(
          `/web-thuno/api/thu-no/xu-ly-thue-bao/ds-khat-no/${this.kyCuoc}`
        );
        if (rs && rs.data) {
          return rs.data;
        }
        return [];
      } catch (error) {
        return [];
      } finally {
      }
    },
    addNew: async function() {
      this.isSubmitting = true;
      let dsKhatNo = [];
      this.validData.forEach(el => {
        dsKhatNo.push({
          maTB: el.MA_TB.trim(),
          dichVuVTId: this.dvIdLock,
          lyDo: el.LYDO.trim(),
          ngayHenTra: moment(this.ngayHenTra).format("DD/MM/YYYY"),
          tienNo: el.TIENNO
        });
      });
      const body = {
        kyCuoc: this.kyCuoc,
        dsKhatNo: dsKhatNo
      };
      console.log(body)
      try {
        const { data } = await this.axios.put(
          "web-thuno/api/thu-no/xu-ly-thue-bao/cap-nhat-thue-bao-khat-no-theo-file",
          body
        );
        if (data && data.error == "200") {
          this.$toast.success(
            "Cập nhật thông tin khất nợ vào cơ sở dữ liệu thành công!"
          );
          this.isSubmitting = false;
          this.$emit("importSucess");
        } else {
          this.$toast.error(
            "Cập nhật thông tin khất nợ vào cơ sở dữ liệu thất bại!"
          );
          this.isSubmitting = false;
        }
      } catch (error) {
        this.$toast.error(
          "Cập nhật thông tin khất nợ vào cơ sở dữ liệu thất bại!"
        );
        this.isSubmitting = false;
      }
    },
    exportExcel() {
      const tHeader = ["Mã TB", "Trạng thái"];
      const filterVal = ["MA_TB", "TRANGTHAI"];
      const data = this.formatJson(filterVal, this.gridViewData);
      export_json_to_excel({
        header: tHeader,
        data,
        filename: "export",
        sheetname: "export"
      });
    },
    formatJson(filterVal, jsonData) {
      return jsonData.map(v =>
        filterVal.map(j => {
          return v[j];
        })
      );
    }
  }
};
</script>
