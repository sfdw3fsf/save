<template src='./BoSungVT_TheoFile.html'></template>
<style scoped src='./BoSungVT_TheoFile.scss'></style>
<script>
import { mapActions, mapState, mapGetters, mapMutations } from "vuex";
import breadcrumb from "@/components/breadcrumb";
import moment from "moment";
import XLSX from "xlsx";
import {
  getterName,
  actionName,
  statePropertyName,
  mutationName,
} from "../store/bosungvttheofile";
export default {
  components: {XLSX},
  name: "BoSungVT_TheoFile",
  mounted() {},
  computed: {},
  data() {
    return {
      txtLanDoc : "",
      DS_Serial : [],
      ngaygio_tao: "",
      arrayImportFile: {
        filexcel:null,
        ok: false,
        dsImportExcel: [],
        dataExcel: [],
        ds_file: []
      },
    };
  },

  created: async function () {
    
  },

  methods: {
    ...mapActions("bosungvttheofile", actionName),
    ...mapMutations("bosungvttheofile", mutationName),
    tsbtnMauEx_Click(){
         
      let dt = [
        { 
          'VATTU_ID': 2245,
          'THUEBAO_ID': 6541343,
          'NGAY_SD': "23/02/2022",
          'SERIAL': '00514144F98022B',
          'SERIAL_GP': 'VNPT00300457',          
        },
        { 
          'VATTU_ID': 2245,
          'THUEBAO_ID': 6318297,
          'NGAY_SD': "24/02/2022",
          'SERIAL': '00513521F6C3E90',
          'SERIAL_GP': 'VNPT00D87D20',          
        },
        { 
          'VATTU_ID': 13073,
          'THUEBAO_ID': 5315916,
          'NGAY_SD': "25/02/2022",
          'SERIAL': 'ZTEGC0A9C75F',
          'SERIAL_GP': 'ZTEGC0A9C75F',          
        },
      ]
      
      
      let data1 = XLSX.utils.json_to_sheet(dt);
      let wb = XLSX.utils.book_new(); // make Workbook of Excel
      XLSX.utils.book_append_sheet(wb, data1, "Sheet1"); // sheetAName is name of Worksheet
      // export Excel file
      XLSX.writeFile(wb, "BoSungVT_TheoFile.xls");
    
    },
    async tsbtnExcelNhap_Click(){
      document.getElementById('fileUpload').click()
    },
    checkfile: function () {
      if (this.arrayImportFile.filexcel == null) {
        this.$toast.error("Chưa chọn file nào!")
        return false
      }
    
      if (
        this.arrayImportFile.filexcel.type != "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" &&
        this.arrayImportFile.filexcel.type != "application/vnd.ms-excel"
      ) {
        this.$toast.error("File không phải định dạng xlsx hoặc xls!")
        return false
      }
      return true
    },
    async onChange() {
      this.arrayImportFile.filexcel = this.$refs.fileUpload.$refs.input.files[0]
      
      try {
        this.loading(true)
        if (this.checkfile()) {       
          const reader = new FileReader()
          reader.onload = async (e) => {
            /* Parse data */
            const bstr = e.target.result
            const wb = XLSX.read(bstr, { type: "binary" })
            /* Get first worksheet */
            const wsname = wb.SheetNames[0]
            const ws = wb.Sheets[wsname]
            /* Convert array of arrays */
            const data = XLSX.utils.sheet_to_json(ws, { header: 1 })
            // retrieve headers (i.e. remove first row)
            let headers  = data.shift() // bỏ dòng header đầu tiên trong data
            
            // then build the json for each row
            var result = data.map(function(a) {
                var jsonRow = {};
                a.forEach(function(cellValue, cellIndex) { 
                    jsonRow[headers[cellIndex]] = cellValue;
                });
                headers.forEach(b=> {
                  if (!Object.keys(jsonRow).includes(b)){
                    jsonRow[b] = ''
                  }
                })
                return jsonRow;
            });
            
            console.log(result , " = result")
            if(result.length == 0){
              this.$toast.error("File không có dữ liệu")
              return
            }else{
              if(!("NGAY_SD" in result[0] && "SERIAL" in result[0] && "SERIAL_GP" in result[0] && "THUEBAO_ID" in result[0] && "VATTU_ID" in result[0])){
                this.$toast.error("Cấu trúc file phải có 5 cột NGAY_SD, SERIAL, SERIAL_GP, THUEBAO_ID, VATTU_ID")
                return
              }
            }
            await this.uploadBoSungFile(result)
          }
          reader.readAsBinaryString(this.arrayImportFile.filexcel)
          
        }
      } catch (error)  {
        this.$toast.error("Có lỗi xảy ra: " + error.data.message)
      } finally {
        this.$refs.fileUpload.reset()
        this.loading(false)
      }
      this.arrayImportFile.filexcel = null
    },
    async uploadBoSungFile(data){
      try{
        this.loading(true)
        let ketQua = await this.uploadDSBoSungTheoFile(data)
        console.log(ketQua , " = ketQua")
        if(ketQua != {}){
          this.txtLanDoc = ketQua.data.lantao_id
          this.DS_Serial = ketQua.data.danhsach
          this.ngaygio_tao = ketQua.data.ngaygio_tao
        }
      } catch (error)  {
        this.$toast.error(error.data.message)        
      } finally {
        this.loading(false)
      }
    },
    async tsbtnCapNhat_Click(){
      try{
        
        if(this.txtLanDoc == ""){
          this.$toast.error("Bạn phải đọc file serial trước !")
          return
        }
        
        // await this.uploadBoSungFile(this.DS_Serial)
        var isBoxConfirm = false;
        await this.$bvModal.msgBoxConfirm("Chương trình cập nhật những serial không có lỗi trên lưới. Bạn có muốn tiếp tục !", {
          title: "Thông báo",
          centered: true,
          size: "md",
          okTitle: "Đồng ý",
          cancelTitle: "Hủy",
        })
        .then((value) => {
          if (value) {
            isBoxConfirm = true;
          } else {
            return;
          }
        });

        if (!isBoxConfirm) {
          return;
        }
        this.$loading(true)
        let thuchien = await this.dayDanhSachBoSung({
          lantao_id: this.txtLanDoc,
          ngaygio_tao : this.ngaygio_tao
        })
        if(thuchien.length > 0){
          this.txtLanDoc = thuchien[0].lantao_id
          this.$toast.success(thuchien[0].message)
        }
      } catch (error)  {
        this.$toast.error(error.data.message)
      } finally {        
        this.loading(false)
      }
    },
  },
};
</script>
