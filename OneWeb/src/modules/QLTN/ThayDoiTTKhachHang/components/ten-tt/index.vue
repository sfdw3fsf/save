<template src="./index.html"></template>
<style scoped>
.popup-box .popup-header {
  padding: 10px 15px;
  font-size: 20px;
  background-color: #fff;
  border-bottom: 1px solid #e0e0e0;
}
</style>
<script>
import xlsx from "xlsx";
import moment from "moment";
import { export_json_to_excel } from "../../../TraCuuDichVu/Export2Excel";

export default {
  name: "TenTT",
  components: {},
  props: [""],
  data: function () {
    return {
      txtFileName: "",
      gridViewData: [],
      gridViewColumns: [
        {
          fieldName: "MA_TT",
          headerText: "Mã TT",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "TEN_TT",
          headerText: "Tên TT",
          allowFiltering: true,
          allowSorting: false,
        },
      ],
      labelThoiGian: "00:00:00",
    };
  },
  validations: {},
  watch: {},
  created: async function () {},
  async mounted() {},
  methods: {
    onClose() {
      this.$emit("close");
    },
    onSave() {
      if (this.gridViewData.length > 0) {
        const tt = this.gridViewData.map(item=> ({ma_tt: item.MA_TT, giatri: item.TEN_TT}));
        const body = {
          pKyCuoc: `${moment(new Date()).subtract(1,'months').endOf('month').format('YYYYMM')}01`,
          pNguoiDungId: this.$root.token.getNguoiDungID(),
          pKieuId: 5,
          pNguoiCN: "Test",
          pMayCN: "mayTest",
          pData:tt
        };
        this.update(body);
      }
    },
    async onChangeFile(e) {
      const files = e.target.files;
      this.files = files;
      console.log(files.length);
      if (files.length > 0) {
        this.txtFileName = files[0].name;
        await this.importExcel();
      }
    },
    async importExcel() {
      this.validData = [];
      const files = this.files;
      const fileReader = new FileReader();
      fileReader.readAsBinaryString(files[0]);
      fileReader.onload = async (ev) => {
        try {
          const data = ev.target.result;
          const XLSX = xlsx;
          const workbook = XLSX.read(data, {
            type: "binary",
          });
          const wsname = workbook.SheetNames[0];
          const ws = XLSX.utils.sheet_to_json(workbook.Sheets[wsname]);
          console.log(ws);
          if (this.checkFormatData(ws)) {
            this.getDataView(ws);
          } else {
            this.gridViewData = [];
          }
        } catch (e) {
          return alert("Read failure!" + e);
        }
      };
    },
    checkFormatData(data) {
      // const dsLoi = [];
      if (data && data.length > 0) {
        let keys = Object.keys(data[0]);
        data.forEach((el) => {
          let key = Object.keys(el);
          keys = [...new Set(keys.concat(key))];
        });
        if (keys.length < 2 || keys[0] != "MA_TT" || keys[1] != "TEN_TT") {
          this.$toast.error(
            "File Excel nhập vào phải đúng định dạng!\n - Chỉ có các trường theo thứ tự MA_TT, TEN_TT"
          );
          return false;
        }
      }
      return true;
    },
    getDataView: async function (srcData) {
      const dstData = [];
      srcData.map((item) => {
        if (item.MA_TT && item.MA_TT.toString().trim() != "") {
          dstData.push({
            MA_TT: item.MA_TT,
            TEN_TT: item.TEN_TT,
          });
        }
      });
      if (dstData.length > 0) {
        this.gridViewData = dstData;
        setTimeout(() => {
          this.$refs.table && this.$refs.table.rowClick(0, null);
        }, 200);
      }
    },
    update: async function (body) {
      console.log(body);
      try {
        const rs = await this.$root.context.post(
          `/web-thuno/api/thu-no/thong-tin-khach-hang/cap-nhat-thong-tin-tu-file`, body
        );
        console.log(rs);
        if(rs.error == "200") {
          this.$toast.success("Cập nhật thành công");
        }
      } catch (error) {}
    },
  },
};
</script>
