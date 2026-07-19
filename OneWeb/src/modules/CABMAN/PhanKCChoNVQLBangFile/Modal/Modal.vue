<template src="./Modal.html"></template>
<script>
import downloadexcel from "vue-json-excel";
import XLSX from "xlsx";
export default {
  components: { downloadexcel, XLSX },
  name: "Modal",
  props: ["modalId"],
  computed: {},
  created: async function () {
    this.setDisableBtn(true, true);
  },
  destroyed() {},
  methods: {
      focusMyElement: async function () {
          this.dsKetCuoiNV=[]
          this.dsImport=[]
          this.dsNVKCTemp=[]
      },
    setDisableBtn: function (isNhapmoi, isGhilai, isHuy, isXoa) {},
    async grid_selectedRowChanged(dataItem) {},
    taiMau: function () {
      this.$root.showLoading(true);
      try {
        const data_tem = {
          MA_NV: "luongnt.hth",
          KYHIEU_KETCUOI: "O-NAAM03B01",
        };
        this.dataExcel.push(data_tem);
        let ds_ketcuoi_nv = XLSX.utils.json_to_sheet(this.dataExcel);
        let wb = XLSX.utils.book_new(); // make Workbook of Excel
        XLSX.utils.book_append_sheet(wb, ds_ketcuoi_nv, "KETCUOI_NHANVIEN"); // sheetAName is name of Worksheet
        // export Excel file
        XLSX.writeFile(wb, "filemau.xlsx");
      } catch (error) {
      } finally {
        this.$root.showLoading(false);
      }
    },
    chooseFiles: function () {
      document.getElementById("fileUpload").click()
    },
    onChange: async function () {

      let tencot;
      try {
        this.dsKetCuoiNV=[]
        this.dsImport=[]
        this.dsNVKCTemp=[]
        this.filexcel = this.$refs.fileUpload.$refs.input.files[0];
        this.$root.showLoading(true);
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
            tencot = data[0];
            if (this.kiemTraDuLieu(tencot)) {
              for (let i = 1; i < data.length; i++) {
                this.dsImport.push(data[i]);
              }
              this.getdulieu();
            }
          };
          reader.readAsBinaryString(this.filexcel);
        }
      } catch (error) {
        this.$toast.error("Có lỗi xảy ra");
      } finally {
        this.$root.showLoading(false);
      }
    },
    checkfile: function () {
      if (this.filexcel == null) {
        this.$toast.error("Chưa chọn file nào!");
        return false;
      }
      if (
        this.filexcel.type !=
        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" &&
        this.file.type != "application/vnd.ms-excel"
      ) {
        this.$toast.error("File không phải định dạng xlsx hoặc xls!");
        return false;
      }
      return true;
    },
    kiemTraDuLieu: function (tencot) {
      if (tencot == null) {
        this.$toast.error("File có cấu trúc không hợp lệ!");
        return false;
      }
      if (!(tencot[0] == "MA_NV" && tencot[1] == "KYHIEU_KETCUOI")) {
        this.$toast.error("File có cấu trúc không hợp lệ!");
        return false;
      }
      return true;
    },
    capNhat: async function () {
      if (this.dsKetCuoiNV == null || this.dsKetCuoiNV.length == 0) {
        this.$toast.error("Chưa có dữ liệu để cập nhật!");
      } else {
        this.$root.showLoading(true);
        try {
          for (let i = 0; i < this.dsKetCuoiNV.length; i++) {
            let nhanvien_id;
            let ketcuoi_id;
            let obj = new Object();
            if (!this.isDungTienTrinh) {
              ketcuoi_id = this.dsKetCuoiNV[i].KETCUOI_ID;
              if(ketcuoi_id==null ||ketcuoi_id=="")
               {
                 this.dsKetCuoiNV[i].TRANGTHAI= 'Lỗi: Kết cuối không tồn tại'
                 continue
               }
              nhanvien_id = this.dsKetCuoiNV[i].NHANVIEN_ID;
               if(nhanvien_id==null ||nhanvien_id=="")
               {
                 this.dsKetCuoiNV[i].TRANGTHAI= 'Lỗi: Mã nhân viên không chính xác hoặc không tồn tại'
                 continue
               }

              obj.nhanvien_id = nhanvien_id;
              obj.ketcuoi_id = ketcuoi_id;
              obj.cap_id=0;
              //Lấy thông tin nhân viên
              var rs = await this.$root.context.post(
                "/web-cabman/quanlyhatang/ganketcuoi_nhanvien_bang_file",
                obj
              );
              if (rs.error_code == "BSS-00000000") {
                this.ketquaCapNhat.hoanthanh += 1;
                this.ketquaCapNhat.tong+=1;
                this.dsKetCuoiNV[i].TRANGTHAI = "Hoàn thành";
              } else {
                this.ketquaCapNhat.loi += 1;
                this.ketquaCapNhat.tong+=1;
                this.dsKetCuoiNV[i].TRANGTHAI = rs.message_detail;
              }
            } else {
              break;
            }
          }
        } catch (err) {
          this.$toast.error("Có lỗi xảy ra trong quá trình đấu nối");
          return;
        } finally {
          this.setDisableBtn(false, false);
          this.$toast.success(
            "Đấu nối hoàn tất\nTổng cộng: " +
              this.ketquaCapNhat.tong +
              "\n Hoàn thành: " +
              this.ketquaCapNhat.hoanthanh +
              "\n Lỗi: " +
              this.ketquaCapNhat.loi
          );
          this.$root.showLoading(false);
        }
      }
    },
    layThongTinNV:async function(){
      try{
      let dsData = this.dsImport;
      console.log('dsData',dsData)
       for (let i = 0; i < dsData.length; i++) {
          let obj = new Object
          if(dsData[i]!= null && dsData[i].length >0){
          let ma_nv = dsData[i][0].trim();
          if (ma_nv == null || ma_nv=="") {
            obj.NHANVIEN_ID= ''
            obj.MA_NV = '';
            obj.TEN_NV = ''
            obj.DONVI_NV = ''
            obj.TRANGTHAI= ""

          }
        
          //Lấy thông tin nhân viên
          var rs = await this.$root.context.get(
            "/web-cabman/danhmuc/thongtin-nhanvien-theo-manv?nhanvien_ids="+ ma_nv 
          );
          console.log(rs)
          if (rs.data == null || rs.data.length==0) {
            obj.NHANVIEN_ID= ''
            obj.MA_NV = ma_nv;
            obj.TEN_NV = ''
            obj.DONVI_NV = ''
            obj.TRANGTHAI= "Lỗi: Không tìm thấy thông tin nhân viên"
          } else {
            let dt= rs.data
            obj.NHANVIEN_ID = dt[0].NHANVIEN_ID
            obj.MA_NV = ma_nv
            obj.TEN_NV = dt[0].TEN_NV
            obj.DONVI_NV = dt[0].DONVI_NV
            obj.TRANGTHAI= ""
          }
        
          this.dsNVKCTemp.push(obj)
        }
        }

         return true
        }catch(e){
          console.log('er',e)
           this.$root.toastError('Có lỗi xảy ra trong quá trình lấy thông tin nhân viên')
            return false
        }finally{

        }
    },
    layThongTinKC: async function(){
     try{
      let dsData = this.dsImport;
       for (let i = 0; i < dsData.length; i++) {
        if(dsData[i]!= null && dsData[i].length >0){
          let kyhieu = dsData[i][1].trim();
          if (kyhieu == null || kyhieu=="") {
             this.dsNVKCTemp[i].KYHIEU_KETCUOI= ""
             this.dsNVKCTemp[i].TRANGTHAI= ""
             this.dsNVKCTemp[i].KETCUOI_ID = ''
          }
          //Lấy thông tin nhân viên
          var rs  = await this.$root.context.post(
              "/web-cabman/ketcuoi/lay-id-theo-kyhieu",
              { kyhieu_kc: kyhieu }
            );
          if (rs.data == null|| rs.data.length==0) {
            this.dsNVKCTemp[i].KYHIEU_KETCUOI= kyhieu
            this.dsNVKCTemp[i].KETCUOI_ID = -1
            this.dsNVKCTemp[i].TRANGTHAI= "Lỗi: Không tìm thấy thông tin kết cuối"

          } else {
            let dt= rs.data
            this.dsNVKCTemp[i].KYHIEU_KETCUOI = kyhieu;
            this.dsNVKCTemp[i].KETCUOI_ID = dt.KETCUOI_ID
          }        
        }
        }
        return true
        }catch(e){
          console.log('er',e)
           this.$root.toastError('Có lỗi xảy ra trong quá trình lấy thông tin kết cuối')
          return true

        }
    },
    getdulieu: async function () {
      this.$root.showLoading(true);
      try {
      if(  await this.layThongTinNV()){
         this.sleep(500)
            if(await this.layThongTinKC()){
            this.sleep(500)
            this.dsKetCuoiNV= this.dsNVKCTemp}
      }
           } catch (err) {
        this.$toast.error("Có lỗi xảy ra trong quá trình lấy dữ liệu!");
        return;
      } finally {

        this.setDisableBtn(false, true);
        this.$root.showLoading(false);

      }
    },
    async grid1_selectedRowChanged(dataItem) {
      //console.log(dataItem.KETCUOI_ID);
    },
    setDisableBtn: function (isCapNhat, isDungTienTrinh) {
      this.isDisableBtnCapNhat = isCapNhat;
      this.isDisableBtnDungTienTrinh = isDungTienTrinh;
    },
    dungCapNhat: function () {
      this.isDungTienTrinh = true;
    },
    xuatExcel:function(){
      if(this.dsKetCuoiNV==null ||this.dsKetCuoiNV.length==0)
       this.$root.toastError('Danh sách không có dữ liệu để xuất!')
       return
    },
  sleep: function(ms) {
  return new Promise((resolve) => {
    setTimeout(resolve, ms);
  });
  },
  excel_dataGridCellCreated:function(e){
    if (e.cellFieldName == "TRANGTHAI") {
      if(e.cellText!='' && e.cellText.includes('Lỗi:')){
      $(e.cellElement).addClass('text-danger')
      }
       if(e.cellText!='' && e.cellText.includes('Hoàn thành')){
        $(e.cellElement).addClass('text-success')
      }
    }
  }
  },
  data() {
    return {
      dsKetCuoiNV: [],
      dataExcel: [],
      dsNVKCTemp:[],
      json_name_tc: "dsChitietKC_NV",
      dsImport: [],
      filexcel: null,
      ok: false,
      isDisableBtnCapNhat: true,
      isDisableBtnDungTienTrinh: true,
      isDungTienTrinh: false,
      ketquaCapNhat: {
        tong: 0,
        hoanthanh: 0,
        loi: 0,
      },
    };
  },
};
</script>
<style>
div#popupTCTTTB___BV_modal_outer_ {
  z-index: 10 !important;
  margin-top: 100px;
}
.dashboard-header {
  z-index: 9 !important;
}
a.disabled {
  pointer-events: none;
  cursor: default;
  color: #6c757d !important;
}
</style>
