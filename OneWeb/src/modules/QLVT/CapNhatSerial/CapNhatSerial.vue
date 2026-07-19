<template src='./CapNhatSerial.html'></template>
<style scoped src='./CapNhatSerial.scss'></style>
<script>
import { mapActions, mapState, mapGetters } from "vuex";
import breadcrumb from "@/components/breadcrumb";
import xlsx from "xlsx";
export default {
  components: { breadcrumb },
  name: "CapNhatSerial",
  mounted() {},
  watch: {

    RowChecked: function (value) {
      this.RowChecked = value;  
    },
    checkAll: function (value) {
      this.RowChecked = [];
      if (value) {
        for (const e of this.listSerialCN) {
          this.RowChecked.push(e.KEY);        
        }
      }      
    },
  },
  data() {
    return {
      header: {
        title: "Cập nhật Serial",
        list: [
          {
            name: "Quản lí vật tư",
            link: { name: "Ui.buttons" },
          },
          {
            name: "Nhập| Xuất| Điều chuyển vật tư",
            link: { name: "Ui.buttons" },
          },
          {
            name: "Cập nhật Serial",
            link: { name: "Ui.buttons" },
          },
        ],
      },
      statusCheDo: false,
      statusLayTheoFile: true,
      listFileMau: [
        {
          SERIAL_GOC: "00312221F0BC871",
          SERIAL_MOI: "00312221F0BC871",
          SERIAL_GP_MOI: "ABC",
          MATHUNG: "00312221F0BC871",
        },
        {
          SERIAL_GOC: "1",
          SERIAL_MOI: "2",
          SERIAL_GP_MOI: "3",
          MATHUNG: "4",
        },
      ],
      listSerialCN: [],
      listSerialCN_Selected: [],
      serial_moi: "",
      serial_goc: "",
      nguoi_cn: "",
      listSerialCN_TraCuu: [],
      keySessionIDVT: "",

      checkAll: false,
      CheckboxAll: function () {
        return {
          template: {
            template: `
                            <div class="check-action">
                                <input type="checkbox" v-model="parent.checkAll" class="uncheck"/>
                                <span class="name"></span>
                            </div>
                        `,
            data: function () {
              return {};
            },            
            computed: {
              parent() {
                return this.$parent.$parent.$parent;
              },
            },
          },
        };
      },
      RowChecked : [],
      Checkbox: function () {
        return {
          template: {
            template: `
                            <div class="check-action">
                                <input
                                    type="checkbox"
                                    class="uncheck"                                    
                                    v-model="parent.RowChecked"
                                    :value="data.KEY"/>
                                <span class="name"></span>
                            </div>
                        `,
            data() {
              return {
                data: {},
              };
            },
            computed: {
              parent() {
                return this.$parent.$parent.$parent;
              },
            },
          },
        };
      },
    };
  },
  methods: {
    ...mapActions("capnhatserial", [
      "getDSSerialCN",
      "capNhatSerialTraCuu",
      "getLogCapNhatSerial",
      "getKeySessionIDVT",
      "getKetQuaCN",
    ]),
    getColumnTemplage(parent) {
      return function () {
        return {
          template: {
            template: `
                            <span :class="icon" :style="{color: color}">{{text}}</span>
                        `,
            data() {
              return {
                parent: parent,
                icon: null,
                title: null,
                color: "transparent",
                text: "",
                data: {},
              };
            },
            mounted() {
              if (this.data.NEW.toString() != "1") {
                if (this.data.TRANG_THAI.toString() == "1") {
                  this.color = "green";
                  this.icon = "icon one-check";
                } else {
                  this.color = "orange";
                  this.icon = "icon one-alert";
                }
              } else {
                if (this.data.TRANG_THAI.toString() == "1") {
                  this.color = "black";
                  this.text = "-";
                } else {
                  this.color = "orange";
                  this.icon = "icon one-alert";
                }
              }
            },
          },
        };
      };
    },
    async fetchDSSerialCN(_dsSerial) {
      try{
        this.loading(true);
        let data = {
          phanVungId: this.$root.token.getPhanVungID(),
          kieu: 1,
          dsSerial: _dsSerial,
        };
        const response = await this.getDSSerialCN(data);
        console.log("fetchDSSerialCN = ",response.data);
        if (response.error_code === "BSS-00000000") {
          let i = 0
          for (const element of response.data) {
            let rs = {
              KEY: i,
              TEN_VT: element.SERIAL_GOC ? element.TEN_VT : "-",
              LOHANG: element.SERIAL_GOC ? element.LOHANG : "",
              SERIAL_GOC: element.SERIAL_GOC.toUpperCase(),
              SERIAL_MOI: element.SERIAL_MOI.toUpperCase(),
              SERIAL_GP_MOI: element.SERIAL_GP_MOI.toUpperCase(),
              THIETBI_ID: element.THIETBI_ID,
              VATTU_ID: element.VATTU_ID,
              KHO_ID: element.KHO_ID,
              KHO: element.SERIAL_GOC ? element.KHO : "-",
              MATHUNG: element.MATHUNG,
              LOG: element.LOG,
              ISCHECK: element.ISCHECK,
              ISOK: element.ISOK,
              TRANG_THAI: element.LOG.toString() != "" ? 0 : 1,
              NEW: 1,
            };
            this.listSerialCN.push(rs);
            i = i + 1;
          }
        }
        console.log(this.listSerialCN , " = listSerialCN")
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false);
      }
        
    },
    selectedSerialCN_checkbox(data) {
      this.listSerialCN_Selected = [];
      this.listSerialCN_Selected = data;

    },
    changeStatusLayFile() {
      this.statusLayTheoFile = !this.statusLayTheoFile;
      this.listSerialCN = [];
      this.checkAll = false
      this.RowChecked = []
      this.listSerialCN_TraCuu = [];
    },
    changeStatusCheDo() {
      this.statusCheDo = !this.statusCheDo;
      this.listSerialCN = [];
      this.checkAll = false
      this.RowChecked = []
      this.listSerialCN_TraCuu = [];
    },
    async importExcel(e) {
      const files = e.target.files;
      if (!files.length) {
        return;
      } else if (!/\.(xls|xlsx)$/.test(files[0].name.toLowerCase())) {
        return this.$root.toastError("File excel không đúng định dạng!");
      }
      const fileReader = new FileReader();
      fileReader.onload = async (ev) => {
        try {
          const data = ev.target.result;
          const XLSX = xlsx;
          const workbook = XLSX.read(data, {
            type: "binary",
          });
          const wsname = workbook.SheetNames[0];
          const ws = XLSX.utils.sheet_to_json(workbook.Sheets[wsname]);
          const excellist = [];
          let dsCapNhatSerial = [];
          // Edit data          
          for (var i = 0; i < ws.length; i++) {          
            excellist.push(ws[i]);
            let temp = {
              SERIAL_GOC: ws[i].SERIAL_GOC.toString().toUpperCase(),
              SERIAL_MOI: ws[i].SERIAL_MOI.toString().toUpperCase(),
              SERIAL_GP_MOI: "SERIAL_GP_MOI" in ws[i] ? ws[i].SERIAL_GP_MOI.toString().toUpperCase() : "",
              MATHUNG: ws[i].MATHUNG,
              NGUOIDUNG_ID : this.$auth.getNguoiDungID()
            };
            dsCapNhatSerial.push(temp);
          }

          var propCount = 0;
          if (excellist && excellist.length > 0) {
            for (var prop in excellist[0]) {
              if (excellist[0].hasOwnProperty(prop)) propCount++;
            }
          }

          if (propCount == 0 || propCount > 4) {
            this.$root.toastError("Dữ liệu file excel chưa đúng số lượng cột");
            return;
          }

          if (
            !excellist[0].hasOwnProperty("SERIAL_GOC") ||
            !excellist[0].hasOwnProperty("SERIAL_MOI")
          ) {
            this.$root.toastError(
              "File không đúng cấu trúc SERIAL_GOC, SERIAL_MOI"
            );
            return;
          }
          await this.fetchDSSerialCN(dsCapNhatSerial);
        } catch (e) {
          this.$toast.error(e.data.message);
        }
      };
      fileReader.readAsBinaryString(files[0]);
      var input = document.getElementById("upload");
    },
    XuatFileMau() {
      console.log("File mẫu", this.listFileMau.length);
      if (this.listFileMau.length <= 0) {
        this.$root.toastError("Chưa có danh sách để xuất Excel!");
        return;
      }
      this.loading(true);
      try {
        var dsach = xlsx.utils.json_to_sheet(this.listFileMau);
        var wb = xlsx.utils.book_new();
        xlsx.utils.book_append_sheet(wb, dsach, "DS");
        xlsx.writeFile(wb, "fileMau.xlsx");
      } catch (error) {
      } finally {
        this.loading(false);
      }
    },
    XuatExcel() {
      if (this.listSerialCN_TraCuu.length <= 0) {
        this.$root.toastError("Chưa có danh sách để xuất Excel!");
        return;
      }
      this.loading(true);
      try {
        var dsach = xlsx.utils.json_to_sheet(this.listSerialCN_TraCuu);
        var wb = xlsx.utils.book_new();
        xlsx.utils.book_append_sheet(wb, dsach, "DS");
        xlsx.writeFile(wb, "CapNhatSerial.xlsx");
      } catch (error) {
      } finally {
        this.loading(false);
      }
    },
    async CapNhatSerial() {
      try{
        this.loading(true);
        let check = 0;
        this.listSerialCN.forEach((el) => {
          if (el.ISCHECK == "1" && el.ISOK == "-1") {
            check += 1;
          }
        });
        console.log("Check ISOK", check);
        if (
          this.listSerialCN.length <= 0 ||
          this.RowChecked.length <= 0 ||
          check == 0
        ) {
          this.$root.toastError("Danh sách chưa có dữ liệu, không thể thực hiện");
          return;
        } 
          
        let listPush = [];        
        this.RowChecked.forEach((element) => {
          this.listSerialCN.forEach((el) => {
            if (element === el.KEY) {
              listPush.push({
                SERIAL_GOC: el.SERIAL_GOC.toUpperCase(),
                SERIAL_MOI: el.SERIAL_MOI.toUpperCase(),
                SERIAL_GP_MOI: "SERIAL_GP_MOI" in el ? el.SERIAL_GP_MOI.toUpperCase() : "",
                THIETBI_ID: el.THIETBI_ID,
                VATTU_ID: el.VATTU_ID,
                MATHUNG: el.MATHUNG,
              });
            }
          });
        });               
        await this.fetchCapNhatSerialTraCuu(listPush);        
        await this.fetchKetQuaCN(this.keySessionIDVT);
          
        
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false);
      }
    },
    async fetchCapNhatSerialTraCuu(_listPush) {     
      await this.getKey();
      let data = {
        sessionId: this.keySessionIDVT,
        dsSerial: _listPush,
        mayCn: this.$root.token.getUserName(),
        nguoiCn: this.$root.token.getUserName(),
        ipCn: "10.59.90.121",
        kieu: this.statusLayTheoFile == false ? "1" : "0",
      };
      const response = await this.capNhatSerialTraCuu(data);
      console.log(5);
      if (response.error_code === "BSS-00000000") {
        this.$root.toastSuccess("Cập nhật thành công!");
      }     
    },
    async getKey() {
      try {
        console.log(3.1);
        let body = {
          phanVungId: this.$root.token.getPhanVungID(),
          keyName: "SESSION_ID_VT",
          numBlockSize: 0,
          numRetry: 0,
        };
        const response = await this.getKeySessionIDVT(body);
        if (response.error_code === "BSS-00000000") {
          this.keySessionIDVT = response.data;
        }
        console.log("3.2", response.data);
      } catch (error) {
        this.$root.toastError(error.data.message_detail);
      }
    },
    async fetchLogCapNhatSerial() {
      try{
        this.loading(true);
        if (!this.serial_goc && !this.serial_moi && !this.nguoi_cn) {
          this.$root.toastError(
            "Series gốc, Series mới, Người CN không được để trống"
          );
        } else {
          
          let value = {
            phanVungId: this.$root.token.getPhanVungID(),
            data: [
              {
                SERIAL_CU: this.serial_goc,
                SERIAL_MOI: this.serial_moi,
                NGUOI_CN: this.nguoi_cn,
              },
            ],
          };
          const response = await this.getLogCapNhatSerial(value);
          if (response.error_code === "BSS-00000000") {
            this.listSerialCN_TraCuu = response.data;
          }
          if (this.listSerialCN_TraCuu.length === 0) {
            this.$root.toastError("Không có kết quả tra cứu");
          }
          
        }
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false);
      }
    },
    async fetchKetQuaCN(_ss) {
      
      let data = { sessionId: _ss };
      try {
        this.loading(true);
        const response = await this.getKetQuaCN(data);
        console.log("log kết quả", response.data);
        this.listSerialCN = [];
        this.checkAll = false
        this.RowChecked = []
        if (response.error_code === "BSS-00000000") {
          for (let index = 0; index < response.data.length; index++) {
            const element = response.data[index];
            this.listSerialCN.push({
              TRANG_THAI: 1,
              ISOK: element.ISOK,
              KHO: element.KHO,
              KHO_ID: element.KHO_ID,
              LOG: element.LOG,
              LOHANG: element.LOHANG,
              MATHUNG: element.MATHUNG,
              SERIAL_GOC: element.SERIAL_GOC,
              SERIAL_MOI: element.SERIAL_MOI,
              SERIAL_GP_MOI: element.SERIAL_GP_MOI,
              TEN_VT: element.TEN_VT,
              THIETBI_ID: element.THIETBI_ID,
              VATTU_ID: element.VATTU_ID,
            });
          }
        }
      } catch (er) {
        this.$root.toastError(
          `Lấy kết quả không thành công: ${er.data.message}`
        );
      } finally {
        this.loading(false);
      }
    },
    gridSerialCN_Tracuu_selectedRowChanged() {},
    selectedSerialCN_Tracuu_checkbox() {},
    LamMoiButton() {
      this.listSerialCN = [];
      this.listSerialCN_Selected = [];
      this.RowChecked = []
      this.checkAll =  false
      this.serial_moi = "";
      this.serial_goc = "";
      this.nguoi_cn = "";
      this.listSerialCN_TraCuu = [];
      this.keySessionIDVT = "";
    },
  },
  created() {},
};
</script>
