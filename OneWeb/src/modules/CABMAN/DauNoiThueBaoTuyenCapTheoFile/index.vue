<template src="./index.html"></template>
<script>
import XLSX from "xlsx";
export default {
  data : function ()  {
    return {
      filexcel:null,
      ok: false,
      dsImportExcel: [],
      dataExcel: [],
      json_name_tc: "danh sách thuê bao đấu nối.csv",
      dsThueBaoDauNoi:[],

      isDisableBtnNhapExcel:true,
      isDisableBtnDauNoi:true,
      isDisableBtnDungDN:true,
      isDisableBtnXuatFile:true,
      isDisableBtnTaiMau: true,

      showProgressBar: false,
      progressBarPercent:0,
      isDungTienTrinh: false,
    }
  },
  created() {
    this.statusBtn(false,true,true,false,false)
  },
  watch:{
    dsThueBaoDauNoi : function (value){
      if (value.length === 0){
        this.statusBtn(false,true,true,false,false)
      }else {
        this.statusBtn(false,false,true,false,false)
      }
    }
  },
  methods: {
    chooseFiles: function () {
      document.getElementById("fileUpload").click()
    },
    onChange: async function () {
      this.dsImportExcel = []
      this.filexcel = this.$refs.fileUpload.$refs.input.files[0]
      let tencot
      try {
        this.$root.showLoading(true)
        if (this.checkfile()) {
          const reader = new FileReader()
          reader.onload = async (e) => {
            /* Parse data */
            const bstr = e.target.result
            const wb = XLSX.read(bstr, {type: "binary"})
            /* Get first worksheet */
            const wsname = wb.SheetNames[0]
            const ws = wb.Sheets[wsname]
            /* Convert array of arrays */
            const data = XLSX.utils.sheet_to_json(ws, {header: 1})
            tencot = data[0]
            if (this.kiemTraDuLieu(tencot)) {
              for (let i = 1; i < data.length; i++) {
                this.dsImportExcel.push(data[i])
                if (this.dsImportExcel.length === 200) break
              }
              await this.getdulieu()
            }
          }
          reader.readAsBinaryString(this.filexcel)
        }
      } catch (error) {
        console.log(error)
        this.$root.toastError("Có lỗi xảy ra")
      } finally {
        this.$root.showLoading(false)
      }
    },
    async getdulieu() {
      this.$root.showLoading(true);
      try {
        let dsData = this.dsImportExcel
        // reset data
        this.dsThueBaoDauNoi = []
        if (dsData[0].length > 0) {
          let maTbArr = dsData.map(item => item[0])
          // dsData.forEach(item=> maTbArr.push(item[0]))
          let rs = []
          await this.$root.context.post("/web-cabman/daunoi-thuebao-vao-cap-theo-file/lay-thongtin-ds-thuebao", {ds_ma_thuebao: maTbArr.toString()})
            .then(res => {
              rs = res.data
            }).catch(e => {
              // this.$root.toastError("Có lỗi xảy ra trong quá trình lấy thông tin thuê bao!")
              this.$root.toastError(e.response.data.message_detail)
            })
          if (rs.length > 0) {
            dsData.forEach(element => {
              let infor = rs.filter(item => item.MA_TB.toString() === (element[0] + ''))
              if (infor.length > 0) {
                this.dsThueBaoDauNoi.push({
                  KETCUOI: element[1],
                  VITRI: element[2],
                  VITRI_2: element[3],
                  MA_TB: infor[0].MA_TB,
                  TEN_TB: infor[0].TEN_TB,
                  DIACHI_LD: infor[0].DIACHI_LD,
                  TRANGTHAI: infor[0].TRANGTHAI,
                  THUEBAO_ID: infor[0].THUEBAO_ID
                })
              } else {
                // Không tìm thấy dữ liệu của MA_TB
                this.dsThueBaoDauNoi.push({
                  KETCUOI: element[1],
                  VITRI: element[2],
                  VITRI_2: element[3],
                  MA_TB: element[0],
                  TEN_TB: null,
                  DIACHI_LD: null,
                  TRANGTHAI: "Lỗi: Không tìm thấy thông tin thuê bao",
                  THUEBAO_ID: null
                })
              }
            })
          } else {
            //Khong tim thay du lieu
            dsData.forEach(element => {
              this.dsThueBaoDauNoi.push({
                KETCUOI: element[1],
                VITRI: element[2],
                VITRI_2: element[3],
                MA_TB: element[0],
                TEN_TB: null,
                DIACHI_LD: null,
                TRANGTHAI: "Lỗi: Không tìm thấy thông tin thuê bao",
                THUEBAO_ID: null
              })
            })
          }
        } else {
          this.$root.toastError("File không có dữ liệu. Vui lòng chọn lại")
        }
      } catch (err) {
        console.log(err)
        // this.$root.toastError("Có lỗi xảy ra trong quá trình lấy thông tin thuê bao!")
        // if (err.response && err.response.data.message_detail)
        this.$root.toastError(err.message)
      } finally {
        this.filexcel = null
        this.$root.showLoading(false);
      }
    },
    checkfile: function () {
      if (this.filexcel == null) {
        this.$root.toastError("Chưa chọn file excel")
        return false
      }
      if (
        this.filexcel.type !== "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" && this.filexcel.type !== "application/vnd.ms-excel"
      ) {
        this.$toast.error("File không đúng định dạng cho phép (xls, xlsx) hoặc không đúng biểu mẫu vui lòng thử lại")
        return false
      }
      return true
    },
    kiemTraDuLieu: function (tencot) {
      if (tencot == null) {
        this.$root.toastError("File có cấu trúc không hợp lệ!")
        return false
      }
      if (!(tencot[0] == "MA_TB" && tencot[1] == "KETCUOI" && tencot[2] == "VITRI" && tencot[3] == "VITRI_2")) {
        this.$root.toastError("File có cấu trúc không hợp lệ!")
        return false
      }
      return true
    },
    btnTaiMau(){
        this.$root.showLoading(true);
        try {
          const data_tem = {
            MA_TB: null,
            KETCUOI: null,
            VITRI:null,
            VITRI_2:null
          };
          this.dataExcel.push(data_tem);
          let fileMau = XLSX.utils.json_to_sheet(this.dataExcel);
          let wb = XLSX.utils.book_new(); // make Workbook of Excel
          XLSX.utils.book_append_sheet(wb, fileMau, "DAUNOI_THUEBAO_TUYENCAP"); // sheetAName is name of Worksheet
          // export Excel file
          XLSX.writeFile(wb, "filemau.xlsx");
        } catch (error) {
        } finally {
          this.$root.showLoading(false);
        }
    },
    btnDauNoi: async function (){
      if (this.isDisableBtnDauNoi) return
      // status btn
      this.statusBtn(true,true,false,true,true)
      // init progress bar
      this.showProgressBar = true
      this.progressBarPercent = 0

      let dsDauNoi = this.dsThueBaoDauNoi.filter(item => item.TRANGTHAI === null)
      console.log("dsDauNoi",dsDauNoi)
      try {
        // this.loading(true)
        // let dsDauNoi = this.dsThueBaoDauNoi.map(item =>({db_id:item.THUEBAO_ID, ketcuoi:item.KETCUOI, vitri:item.VITRI, vitri2:item.VITRI_2}))
        for (let [index,item] of dsDauNoi.entries()) {
          let thuebaoId = item.THUEBAO_ID
          let maTb = item.MA_TB
          let ketcuoi = item.KETCUOI
          let vitri = item.VITRI
          let vitri2 = item.VITRI_2
          let ketcuoiId = null

          if (this.isDungTienTrinh) {
            this.isDungTienTrinh = !this.isDungTienTrinh
            break
          }
          await this.$root.context.post("/web-cabman/ketcuoi/lay-id-theo-kyhieu",{kyhieu_kc:ketcuoi})
          .then(res =>{
            if (res.error === "200" && res.error_code === "BSS-00000000")
              ketcuoiId = res.data.KETCUOI_ID
          }).catch(e => ketcuoiId = null)

          this.progressBarPercent = ((index+1) / dsDauNoi.length) * 100

          if (!thuebaoId){
            item.TRANGTHAI = "Lỗi: Mã thuê bao trống hoặc không có trong CSDL"
            continue
          } else if (!ketcuoi){
            item.TRANGTHAI = "Lỗi: Kết cuối trống"
            continue
          } else if (!ketcuoiId){
            item.TRANGTHAI = "Lỗi: Kết cuối không có trong CSDL"
            continue
          } else if (vitri){
            if (vitri2 === "" || vitri2 === null || vitri2 === undefined) vitri2 = "0"
            // let t = parseInt(vitri,10)
            // let t2= parseInt(vitri2,10)
            let isNum1 = /^\d+$/.test(vitri);
            let isNum2 = /^\d+$/.test(vitri2);
            if (!isNum1){
              item.TRANGTHAI = "Lỗi: Vị trí " + vitri + " không hợp lệ"
              continue
            }
            if (!isNum2){
              item.TRANGTHAI = "Lỗi: Vị trí " + vitri2 + " không hợp lệ"
              continue
            }
            //
            vitri = parseInt(vitri)
            vitri2 = parseInt(vitri2)
            await this.$root.context.post("/web-cabman/daunoi-thuebao-vao-cap-theo-file/daunoi-thuebao", {db_id:thuebaoId, ketcuoi_id:ketcuoiId, vitri:vitri,vitri2:vitri2})
            .then(res =>{
              if (res.error === "200" && res.error_code === 'BSS-00000000')
                item.TRANGTHAI = "Hoàn thành"
            }).catch(e =>item.TRANGTHAI = "Lỗi: "+ e.response.data.message_detail)
          } else {
            item.TRANGTHAI = "Lỗi: Vị trí 1 trống"
          }
        }
        let tc = dsDauNoi.length
        let ht = dsDauNoi.filter(item => item.TRANGTHAI !== null && item.TRANGTHAI.includes("Hoàn thành")).length
        let loi = dsDauNoi.filter(item => item.TRANGTHAI !== null && item.TRANGTHAI.includes("Lỗi")).length
        this.$root.toastSuccess("Đấu nối hoàn tất!\r\nTổng cộng:"+tc+ "\r\nHoàn thành: "+ht+ "\r\nLỗi: "+loi)
      } catch (e){
        console.log(e)
        this.$root.toastError("Có lỗi xảy ra trong quá trình đấu nối\n"+ e)
      }finally {
        this.showProgressBar = false
        // this.loading(false)
        //map trang thai dau noi vao grid
        this.dsThueBaoDauNoi.forEach(item=>{
          dsDauNoi.forEach(i =>{
            if (item.THUEBAO_ID === i.THUEBAO_ID){
              item.TRANGTHAI = i.TRANGTHAI
            }
          })
        })
        this.dsThueBaoDauNoi = [...this.dsThueBaoDauNoi]
        this.statusBtn(false,false,true,false,false)
      }
    },
    btnXuatFile: function () {
      this.$root.showLoading(true);
      try {
        let dsXuatExcel = []
        if (this.dsThueBaoDauNoi.length > 0) {
          dsXuatExcel = this.dsThueBaoDauNoi.map(item => ({
            "Kết cuối":item.KETCUOI,
            "Vị trí":item.VITRI,
            "Vị trí 2": item.VITRI_2,
            "Mã TB":item.MA_TB,
            "Tên TB":item.TEN_TB,
            "Địa chỉ LD":item.DIACHI_LD,
            "Trạng thái":item.TRANGTHAI
          }))
        }else {
          dsXuatExcel = [{
            "Kết cuối": null,
            "Vị trí": null,
            "Vị trí 2": null,
            "Mã TB": null,
            "Tên TB": null,
            "Địa chỉ LD": null,
            "Trạng thái": null
          }]
        }
        let ds = XLSX.utils.json_to_sheet(dsXuatExcel);
        let wb = XLSX.utils.book_new(); // make Workbook of Excel
        XLSX.utils.book_append_sheet(wb, ds, "THUEBAO_DAUNOI"); // sheetAName is name of Worksheet
        // export Excel file
        XLSX.writeFile(wb, "ds_thuebao_daunoi.xlsx");
      } catch (error) {
      } finally {
        this.$root.showLoading(false);
      }
    },
    btnDungDN(){
      this.isDungTienTrinh = !this.isDungTienTrinh

    },
    statusBtn(btnNhapExcel, btnDauNoi, btnDungDN, btnXuatFile, btnTaiMau){
      this.isDisableBtnNhapExcel = btnNhapExcel
      this.isDisableBtnDauNoi = btnDauNoi
      this.isDisableBtnDungDN = btnDungDN
      this.isDisableBtnXuatFile = btnXuatFile
      this.isDisableBtnTaiMau = btnTaiMau
    },
  },
  async mounted()  {
    try {
      this.$store.dispatch('setHeader', {
        title: 'SỬA ĐỔI THÔNG TIN CÁP THEO DANH SÁCH'
      })
    } catch (err) {
      console.log(err)
    }
  }
}
</script>
<style>
.disable-btn:hover {
  background-color: white !important;
}

.disable-btn a {
  color: #6c757d !important;
}

.disable-btn a:hover {
  cursor: default;
  color: #6c757d;
}
.daunoi-thuebao-tuyencap-theofile{
  position: absolute;
  height: 100%;
  width: 100%;
  z-index: 9;
  background-color: #6964638a;
}
.daunoi-thuebao-tuyencap-theofile .progress{
  top: 0;
  bottom: 0;
  margin: auto;
  position: absolute;
  left: 50px;
  right: 50px;
}
</style>
