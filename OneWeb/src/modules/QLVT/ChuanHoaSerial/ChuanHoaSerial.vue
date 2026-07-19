<template src="./ChuanHoaSerial.html"></template>

<script>
import API from "../api/ChuanHoaSerial.js";
import xlsx from "xlsx";
import ExportExcel from "./utils/ExportExcel.js";
export default {
  name: "ChuanHoaSerial",
  data() {
    return {
      dtDsSerial: [],
    };
  },
  methods: {
    tsbtnMauEx() {
      let column = [
        {
          fieldName: "STT",
          headerText: "STT",
          width: 80,
        },
        {
          fieldName: "TEN_TB",
          headerText: "TEN_TB",
          width: 130,
        },
        {
          fieldName: "SN",
          headerText: "SN",
          width: 130,
        },
        {
          fieldName: "THUNG",
          headerText: "THUNG",
          width: 130,
        },
      ];
      ExportExcel([...column], [{ STT: 1 }], "fileXuLySN", "XL");
    },
    tsbtnExcelDK(e) {
      const files = e.target.files;
      if (!files.length) {
        return;
      } else if (!/\.(xls|xlsx)$/.test(files[0].name.toLowerCase())) {
        return this.$toast.error("File excel không đúng định dạng!");
      }
      const fileReader = new FileReader();
      let dataReads = [];
      fileReader.onload = async (ev) => {
        try {
          this.maTT_Items = [];
          const data = ev.target.result;
          const workbook = xlsx.read(data, { type: "binary" });
          const wsname = workbook.SheetNames[0];
          const ws = xlsx.utils.sheet_to_json(workbook.Sheets[wsname]);

          //Validate input file:
          if (Object.keys(ws[0]).length <= 1) {
            this.$toast.error("File excel không đúng định dạng!");
            return;
          }
          let ExcelCollumns = ["STT", "TEN_TB", "SN", "THUNG"];
          for (const iterator of ExcelCollumns) {
            if (!ws[0].hasOwnProperty(iterator)) {
              this.$toast.error(
                "Tên cột trong file không đúng định dạng: " +
                  iterator +
                  "\n\r (Trường bắt buộc:STT, TEN_TB, SN,THUNG)!"
              );
              return;
            }
          }
          if (ws.length > 0) {
            ws.forEach((item) => {
              dataReads.push(item);
            });
          }
          this.Load_DL(dataReads);
        } catch (e) {
          console.log(e);
          return this.$toast.error("Kiểm tra dữ liệu file đầu vào!");
        }
        if (dataReads.length > 0) {
          this.$toast.success(`Có ${dataReads.length} thành phần từ file`);
        }
      };
      fileReader.readAsBinaryString(files[0]);
    },
    async Load_DL(ds) {
      try {
        this.$root.loading(true);
        await ds.forEach(async (e) => {
          let ttthuebao = await (
            await API.getThongTinTBSN(this.axios, { serial: e.SN })
          ).data.data;
          if (ttthuebao.length) {
            e["MA_THUEBAO"] = ttthuebao[0]["MA_TB"];
            e["TEN_THUEBAO"] = ttthuebao[0]["TEN_TB"];
            e["SDVT_ID"] = ttthuebao[0]["SDVT_ID"];
          }
        });
        this.dtDsSerial = ds;
      } catch (error) {
        console.log(error);
        this.toas;
      } finally {
        this.$root.loading(false);
      }
    },
    onCellSaved(args) {
      console.log(args);
      if (args) {
        if (args.columnName == "MA_THUEBAO") {
          this.dtDsSerial.forEach((e) => {
            if ((e.STT = args.rowData.STT)) {
              e["MA_THUEBAO"] = args.value;
            }
          });
        }
        if (args.columnName == "TEN_THUEBAO") {
          this.dtDsSerial.forEach((e) => {
            if ((e.STT = args.rowData.STT)) {
              e["TEN_THUEBAO"] = args.value;
            }
          });
        }
      }
    },
    async tsbtnXuLy() {
      try {
        this.$root.loading(true);
        if (this.dtDsSerial.length) {
          let serialCheckeds = this.$refs.gridDSSerial.getSelectedRecords();
          if (serialCheckeds.length) {
            let datas = [];
            serialCheckeds.forEach((e) => {
              datas.push({
                SERIAL: e.SN,
                TENTBI: e.TEN_TB,
                THUNG: e.THUNG,
                SDVT_ID: e.SDVT_ID,
              });
            });
            let response = await API.postChuanHoaSerial(this.axios, datas);
            if (response.data.data[0].CODE == 1) {
              this.$toast.success("Cập nhật thành công!");
            } else {
              this.$toast.error(response.data.data[0].MSG);
            }
          } else {
            this.$toast.error("Chưa chọn dữ liệu!");
          }
        } else {
          this.$toast.error("Không có dữ liệu trên lưới!");
        }
      } catch (error) {
        console.log(error);
        this.$toast.error(error.data.error_code);
      } finally {
        this.$root.loading(false);
      }
    },
  },
};
</script>

<style src="./ChuanHoaSerial.scss">
</style>