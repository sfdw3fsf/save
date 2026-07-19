<template src="./main.html"></template>
<script>

import XLSX from "xlsx";

export default {
  name: "main",
  provide: {
  },
  components:{},
  data: function () {
    return {
      listUpload :[],
      listBangGiaTSL :[],
      fileExcel:null,
      vds_upload:[],
      vds:[],
      disableDongBo: true,
      duongDan: "",
    };
  },computed:{

  },mounted: function () {
      this.lay_ds_banggia()
  },
  methods: {
    duongDan_click: function(args){
      this.$refs.fileInput.click();
    },
    async onChangeFile(e) {
      const files = e.target.files;
      if (!files.length) {
        return;
      } else if (!/\.(xls|xlsx)$/.test(files[0].name.toLowerCase())) {
        return this.$root.toastError("File excel không đúng định dạng!");
      }
      this.fileExcel = e.target.files[0];
      this.duongDan = this.fileExcel.name
      try {
        this.$root.showLoading(true);
        await this.txtDuongDan_ButtonClick();
      } finally {
        this.$root.showLoading(false);
      }
    },
    lay_ds_banggia : async function () {
      this.loading(true)

      await this.$root.context.get("web-toanha/banggiakenh-tsl/lay_tt_banggia_tsl_dt").then(res =>{
        this.loading(false)
        res.data.forEach(item =>{
          let formatter = new Intl.NumberFormat('en-US', {
            style: 'currency',
            currency: 'VND',
          });
          item.dongia = formatter.format(item.dongia)
        })
        this.listBangGiaTSL = res.data
      }).catch(err => {
        this.loading(false)
      })
    },
    btnLayTT :async function () {
      await this.lay_ds_banggia()
    },checkfile: function () {
      if (this.fileExcel == null) {
        this.$root.toastError("Chưa chọn file excel")
        return false
      }
      if (
        this.fileExcel.type !== "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" && this.fileExcel.type !== "application/vnd.ms-excel"
      ) {
        this.$toast.error("File không đúng định dạng cho phép (xls, xlsx) hoặc không đúng biểu mẫu vui lòng thử lại")
        return false
      }
      return true
    }, kiemTraDuLieu(tencot) {
      if (!(tencot.includes("TOCDO") && tencot.includes("DONVI") && tencot.includes("DONGIA"))) {
        this.$root.toastError("File không đúng cấu trúc TOCDO, DONVI, DONGIA")
        return false
      }
      return true
    },async kt_capnhat_tocdo_tsl_dt(danhsach) {
      let rs = await this.$root.context.post("web-toanha/banggiakenh-tsl/kt_capnhat_tocdo_tsl_dt",danhsach)
      return rs.data
    }, async txtDuongDan_ButtonClick() {
      this.tsbtnChapNhan = false
      let tencot
      try {
        this.loading(true)
        if (this.checkfile()) {
          let s = this.fileExcel.name.toLowerCase();
          if (s.endsWith(".xlsx") || s.endsWith(".xls")) {
            let reader = new FileReader();
            reader.onload = async (e) => {
              /* Parse data */
              const data = e.target.result;
              const workBook = XLSX.read(data, {type: "binary"});
              if (workBook != null) {
                for (let i = 0; i < workBook.SheetNames.length; i++) {
                  const sheetName = workBook.SheetNames[i];
                  this.vds_upload = XLSX.utils.sheet_to_json(
                        workBook.Sheets[sheetName]
                      );
                  if(this.vds_upload.length<= 0){
                    this.$root.toastError("Dữ liệu file excel không có giá trị")
                    return
                  }

                  if(Object.keys(this.vds_upload[0]).length !==3){
                    this.$root.toastError("Dữ liệu file excel chưa đúng số lượng cột")
                    return
                  }
                }
                tencot = Object.keys(this.vds_upload[0])

                if (this.kiemTraDuLieu(tencot)) {
                  let dtquyen = await this.kt_capnhat_tocdo_tsl_dt(this.vds_upload)
                 this.listUpload = dtquyen.map(o=>({...o}))

                  this.listUpload.forEach(item =>{
                    let formatter = new Intl.NumberFormat('en-US', {
                      style: 'currency',
                      currency: 'VND',
                    });
                    item.dongia = formatter.format(item.dongia)
                  })
                  let dtChuanHoa = dtquyen.filter(i => i.trangthai == '0')
                  if (dtChuanHoa.length > 0) {
                    this.$root.toastError("Vui lòng kiểm tra lại dữ liệu")
                    this.listUpload =[]
                    return
                  }
                  if (this.listUpload.length <= 0) {
                    this.$root.toastError("Không có dữ liệu!")
                    this.listUpload =[]
                    return
                  }
                  this.vds =  dtquyen.map(o=>({...o}))
                  this.tsbtnChapNhan = true
                }
              }
            }
            reader.readAsBinaryString(this.fileExcel)
          }
        }
      } catch (e) {
        console.log(e)
      } finally {
        this.loading(false)
      }
    },
    UpperCaseData(ds) {
      let _ds = []
      for (let item of ds){
        let row = {}
        for (let key in item){
          let k = key.toUpperCase()
          row[k] = item[key]
        }
        _ds.push(row)
      }
      return _ds
    },
    async fn_capnhat_tocdo_tsl_dt(danhsach) {
      let ds = this.UpperCaseData(danhsach)
      let rs = await this.$root.context.post("web-toanha/banggiakenh-tsl/fn_capnhat_tocdo_tsl_dt",ds)
      return rs.data
    },
  getIp:async function(){
    try{
      const response = await fetch("https://jsonip.com/")
      const data = await response.json();
      return data.ip;
    }catch(e) {
      return null
    }
  },
    btnDongBo :  async function () {
      try {
        if(!this.disableDongBo){
          this.loading(true)
          //let ip = await this.getIp()
          let kq = await this.fn_capnhat_tocdo_tsl_dt(this.vds)
          if (kq != 'OK') {
            this.$root.toastError("Có lỗi: " + kq)
            return
          }
          this.$root.toastSuccess("Cập nhật dữ liệu thành công!")
          await this.lay_ds_banggia()
          this.disableDongBo = false
          this.listUpload = []
          this.fileExcel = null
        }
      } catch (e) {

      } finally {
        this.loading(false)
      }
    },
    btnHuy :function (){
      this.fileExcel = null
      this.listUpload = []
    },
    btnXuatExcelMau :function (){
      try {
        this.loading(true)
        let dt = [
          {
            TOCDO: '2',
            DONVI: 'Gb',
            DONGIA: '20000000'
          },
          {
            TOCDO: '10',
            DONVI: 'Mb',
            DONGIA: '10000'
          }
        ]
        let fileMau = XLSX.utils.json_to_sheet(dt);
        let wb = XLSX.utils.book_new(); // make Workbook of Excel
        XLSX.utils.book_append_sheet(wb, fileMau, "BangGia_TSL_TEMP"); // sheetAName is name of Worksheet
        // export Excel file
        XLSX.writeFile(wb, "BangGia_TSL_TEMP.xls");
      } catch (e) {
        this.$root.toastError("Có lỗi khi xuất excel mẫu " + e.toString())
      } finally {
        this.loading(false)
      }
    },
    btnXuatDataMain :function (){
      if(this.listBangGiaTSL){
        let exportProperties = {
          dataSource: this.listBangGiaTSL
        }
        this.$refs.gridListBangGia.excelExport(exportProperties)
      }else {
        this.$toast.error("Không có dữ liệu để xuất Excel !");
      }

    },

  },
  watch:{
    // fileExcel: async function (value) {
    //   await this.txtDuongDan_ButtonClick()
    // }, 
    listUpload : function (value){
      if (value.length>0){
        this.disableDongBo = false
      }else{
        this.disableDongBo = true
      }
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
</style>

