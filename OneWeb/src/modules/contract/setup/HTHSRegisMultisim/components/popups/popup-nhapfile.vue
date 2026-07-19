<template>
  <b-modal
    id="modal-nhapfileVattuThuebao"
    size="xl"
    hide-footer
    hide-header
    hide-header-close
    body-class="p-0"
    no-close-on-backdrop
    @hide="resetData"
    @show="openForm"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span>Đọc file excel vật tư thuê bao
        </div>
        <div
          class="close -ap icon-close"
          data-dismiss="modal"
          @click="close()"
        ></div>
      </div>
      <div class="list-actions-top">
        <ul class="list">
          <li @click="popupFileExel">
            <a>
              <span class="icon one-xlsx-import"></span>
              File Excel
            </a>
          </li>
          <li @click="popupFileMau">
            <a href="javascript:;">
              <span class="icon one-excel"></span> File mẫu
            </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title">Danh sách phiếu thay thế vật tư Import bằng file </div>
          <DataGrid  v-bind:columns="collumnsPhieu"  
                    v-bind:dataSource="options.dsPhieu" 
                    ref="danhSach" 
                    :showFilter="true" 
                    :enabledSelectFirstRow="true" 
                    :enableVirtualization="false"
                    :allowPaging="true" 
                    :enablePagingServer="false"
                    panelDataHeight="250"/>
        </div>
      </div>
      <ejs-dialog
                :visible='false'
                ref="popup_frmNhapText"
                :animationSettings="{ effect: 'Zoom' }"
                :showCloseIcon=false 
                :closeOnEscape='true'
                :enableResize='true'
                :allowDragging='true'
                width='30%'
                :isModal='true'
                :cssClass="'popup-box'"
                >
                <frmNhapText ref="frmNhapText"></frmNhapText>
            </ejs-dialog>
      <input ref="fileInput" id="fileCapVT" type="file" @change="onChangeFileCapVT($event)" accept=".xls, .xlsx" style="display:none" class="form-control ">
      <input ref="fileInput" id="fileThuhoiVT" type="file" @change="onChangeFileThuhoiVT($event)" accept=".xls, .xlsx" style="display:none" class="form-control ">
    </div>
  </b-modal>
</template>
<style></style>
<script>
import XLSX from "xlsx";
import moment from 'moment'
import frmNhapText from './PopupCp_thuhoi/frmNhapText.vue';
import api from "../../api/index.js";
import {
  DichVuVienThong,
  LoaiHopDong,
  LoaiHinhTB,
  KHOANMUC_TT,
  TrangThaiHD,
  TRANGTHAI_DONGBO,
  KieuLapDat,
} from "../../define/index.js";
import collums from "../../define/collums.js";
export default {
  name: "PopupNhapFileExcelVattuThuebao",
  components: {
        frmNhapText
    },
  mounted() {},
  data() {
    return {
      ...collums,
      duongDan: "",
      form: 1,
      dsLoi: [],
      sourceFile: null,
      options: {
                lstDSDichvu: [],
                lstLoaiHinhTB: [],
                lstKieuYC: [],
                ds_tien_khoanmuc : [],
                dsThamso: [],
                dsHD_BH: [],
                dsPhieu: [],
                dsHDTBDV:[],
                dsHDKH:[],
                TTDBMoRong:[],
                dsHDTB:[],
                dsHDTB_DD:[],
                dsHDTB_ADSL:[],
                dsHDTB_CD:[],
                dsHDTB_GP:[],
                dsHDTB_TSL:[],
                dsHDTB_MGWAN:[],
                dsHDTB_IMS:[],
                dsCTTHD:[],
      },
      kieu_luachon:0,
      checkLoi:false,
      ts_sinhma_gd:false,
      file:null,
      dt_error: [],
      tt_nd:{
                ngay_cn:this.$auth.getNgayCapNhat(),
                tentat:"",
                donvi_id: this.$auth.getDonViID(),
                nhanvien_id: this.$auth.getNhanVienID(),
                may_cn: this.$auth.getUserName(),
                ma_nd: this.$auth.getUserName(),
                ip: this.$auth.getUserName(),
                //ten_dv:this.$auth.getTenDonVi()
            },
    };
  },
  methods: {
    resetData() {
        this.options.dsPhieu = []
	},
    openForm() {
         console.log(1)
        console.log(this.options.dsPhieu)
         setTimeout(()=>{
                 this.load_ds();
            },500)
     
    },
    close() {
      this.$bvModal.hide("modal-nhapfileVattuThuebao");
    },
    nhapExcelCapVT(){
          document.getElementById("fileCapVT").click()
    },
    nhapExcelThuhoiVT(){
          document.getElementById("fileThuhoiVT").click()
    },
    onChangeFileCapVT(event){
            this.file=event.target.files[0]
            this.readFileExcelCapVT()
    },
    onChangeFileThuhoiVT(event){
            this.file=event.target.files[0]
            this.readFileExcelThuhoiVT()
    },
    readFileExcelCapVT(){
            this.danhsach_excel=[]
            let columns
            try {
                this.loading(true)
                if (this.checkfile()) {
                    const reader = new FileReader()
                    reader.onload = (e) => {
                        /* Parse data */
                        const bstr = e.target.result
                        const wb = XLSX.read(bstr, { type: "binary" })
                        /* Get first worksheet */
                        const wsname = wb.SheetNames[0];
                        const ws = wb.Sheets[wsname];
                        /* Convert array of arrays */
                        const data = XLSX.utils.sheet_to_json(ws, { header: 1 });
                        columns=data[0]
                        if(this.kiemTraDuLieuCapVT(columns)){
                            for(var i=1;i<data.length;i++){
                                this.danhsach_excel.push({
                                    MA_TB:data[i][0]!=undefined?data[i][0]:null,
                                    MA_KHO:data[i][1]!=undefined?data[i][1]:null,
                                    VATTU_ID:data[i][2]!=undefined?data[i][2]:null,
                                    SOLUONG:data[i][3]!=undefined?data[i][3]:null,
                                    SERIAL:data[i][4]!=undefined?data[i][4]:null,
                                    MUCDICH:data[i][5]!=undefined?data[i][5]:null,
                                    DICHVUVT_ID:data[i][5]!=undefined?data[i][6]:null,
                                })
                                
                            }
                            if(columns.length>0){
                                this.docfile_vattu(this.danhsach_excel)
                            }else{
                                this.$toast.error('Danh sách trống!')
                            }
                        }
                        
                    }
                    reader.readAsBinaryString(this.file)
                }
            } catch (error) {
                this.$toast.error('Có lỗi xảy ra khi đọc file')
            } finally {
                this.loading(false)
            }
    },
     readFileExcelThuhoiVT(){
            this.danhsach_excel=[]
            let columns
            try {
                this.loading(true)
                if (this.checkfile()) {
                    const reader = new FileReader()
                    reader.onload = (e) => {
                        /* Parse data */
                        const bstr = e.target.result
                        const wb = XLSX.read(bstr, { type: "binary" })
                        /* Get first worksheet */
                        const wsname = wb.SheetNames[0];
                        const ws = wb.Sheets[wsname];
                        /* Convert array of arrays */
                        const data = XLSX.utils.sheet_to_json(ws, { header: 1 });
                        columns=data[0]
                        if(this.kiemTraDuLieuCapVT(columns)){
                            for(var i=1;i<data.length;i++){
                                this.danhsach_excel.push({
                                    MA_TB:data[i][0]!=undefined?data[i][0]:null,
                                    TINHTRANG_ID:data[i][1]!=undefined?data[i][1]:null,
                                    VATTU_ID:data[i][2]!=undefined?data[i][2]:null,
                                    SOLUONG:data[i][3]!=undefined?data[i][3]:null,
                                    SERIAL:data[i][4]!=undefined?data[i][4]:null,
                                    MUCDICH:data[i][5]!=undefined?data[i][5]:null,
                                    DICHVUVT_ID:data[i][5]!=undefined?data[i][6]:null,
                                })
                                
                            }
                            if(columns.length>0){
                                this.docfile_vattu(this.danhsach_excel)
                            }else{
                                this.$toast.error('Danh sách trống!')
                            }
                        }
                        
                    }
                    reader.readAsBinaryString(this.file)
                }
            } catch (error) {
                this.$toast.error('Có lỗi xảy ra khi đọc file')
            } finally {
                this.loading(false)
            }
    },
    checkfile: function () {
            if (this.file == null) {
                this.$toast.error('Chưa chọn file excel')
                return false
            }
            if (this.file.type != "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"){
                this.$toast.error('File upload không đúng định dạng')
                return false
            }
            return true
        },
    kiemTraDuLieuCapVT: function (columns) {
            if (columns == null) {
                this.$toast.error('File có cấu trúc không hợp lệ!')
                return false
            }
            if(columns.length<=1){
                this.$toast.error('File excel gồm 7 trường: MA_TB, MA_KHO, VATTU_ID, SOLUONG, SERIAL, MUCDICH, DICHVUVT_ID !')
                return false
            }
            if(columns[0].toString().toUpperCase() != "MA_TB"){
                this.$toast.error('Tên cột đầu tiên trong file không đúng định dạng : MA_TB')
                return false
            }
            if(this.kieu_luachon == 1){
              if(columns[1].toString().toUpperCase() != "MA_KHO"){
                this.$toast.error('Tên cột thứ 2 trong file không đúng định dạng : MA_KHO')
                return false
              }
            }
            if(this.kieu_luachon == 2){
              if(columns[1].toString().toUpperCase() != "TINHTRANG_ID"){
                this.$toast.error('Tên cột thứ 2 trong file không đúng định dạng : TINHTRANG_ID')
                return false
              }
            }
            if(columns[2].toString().toUpperCase() != "VATTU_ID"){
                this.$toast.error('Tên cột thứ 3 trong file không đúng định dạng : VATTU_ID')
                return false
            }
            if(columns[3].toString().toUpperCase() != "SOLUONG"){
                this.$toast.error('Tên cột thứ 4 trong file không đúng định dạng : SOLUONG')
                return false
            }
            if(columns[4].toString().toUpperCase() != "SERIAL"){
                this.$toast.error('Tên cột thứ 5 trong file không đúng định dạng : SERIAL')
                return false
            }
            if(columns[5].toString().toUpperCase() != "MUCDICH"){
                this.$toast.error('Tên cột thứ 6 trong file không đúng định dạng : MUCDICH')
                return false
            }
            if(columns[6].toString().toUpperCase() != "DICHVUVT_ID"){
                this.$toast.error('Tên cột thứ 6 trong file không đúng định dạng : DICHVUVT_ID')
                return false
            }
            return true
        },
        //TaoDuLieu_HDTB_DV
        async TaoDuLieu_HDTBDV(hdtb_id,thuebao_id)
        {
            try 
            {
                await api.TaoDuLieu_HDTBDV(this.axios,{
                    "insert": 1,
                    "thuebao_id": thuebao_id,
                    "hdtb_id": 0,
                    "quytrinh_id": 0
                }).then((res) => {
                    
                    if (
                        res.data.error_code === "BSS-00000000" &&
                        res.data.data !== undefined &&
                        res.data.data.length > 0
                    ) {
                        let row = {}
                        let rowdata = []
                        for (let i = 0; i < res.data.data.length; i++)
                        {
                           
                            row = res.data.data[i];
                            row.HDTB_ID = hdtb_id;
                            row.DONVI_ID = res.data.data[i].donvi_id;
                            row.LOAIDV_ID = res.data.data[i].loaidv_id;
                            row.KIEUDV_ID = res.data.data[i].kieudv_id;
                            rowdata.push(row);
                        }
                        this.options.dsHDTBDV = this.changeKeysToUpper(rowdata);
                        
                    }
                });
            }
            catch(err)
            {
               this.$toast.error("Lỗi tạo dữ liệu hợp đồng thuê bao đơn vị: "+ err.message);
            }
        },
         //Tao du lieu cho bang HDTB_DD
        async TaoDuLieu_HDTB_DD(hdtb_id,thuebao_id)
        {
            let rowHDTBD_DD = {};
            let data = [];
            data = this.GetData(await api.Lay_ds_db_dd_theo_tbid(this.axios, {"thuebao_id": thuebao_id}));
            rowHDTBD_DD = data[0];
            rowHDTBD_DD.HDTB_ID = hdtb_id;
            this.options.dsHDTB_DD.push(this.changeKeysToUpper(rowHDTBD_DD));
            
        },
        //Tao du lieu cho bang HDTB_ADSL
        async TaoDuLieu_HDTB_ADSL(hdtb_id,thuebao_id)
        {
            let  rowHDTB_ADSL = {};

            let data = [];
            data = this.GetData(await api.Lay_ds_danhba_adsl(this.axios, {"thuebao_id": thuebao_id}));
            rowHDTB_ADSL = data[0];
            rowHDTB_ADSL.HDTB_ID = hdtb_id;
            this.options.dsHDTB_ADSL.push(this.changeKeysToUpper(rowHDTB_ADSL));
        }, 
        //Tao du lieu cho bang HDTB_CD
        async TaoDuLieu_HDTB_CD(hdtb_id,thuebao_id)
        { 
            let rowHDTB_CD = {};
            let data = [];
            data = this.GetData(await api.Lay_ds_danhba_codinh(this.axios, {"thuebao_id": thuebao_id}));
            rowHDTBD_DD = data[0];
            rowHDTB_CD.HDTB_ID = hdtb_id;
            this.options.dsHDTB_CD.push(this.changeKeysToUpper(rowHDTB_CD));
        },
        //Tao du lieu cho bang HDTB_GP
        async TaoDuLieu_HDTB_GP(hdtb_id,thuebao_id)
        {
            let rowHDTB_GP = {};
            let data = [];
            data = this.GetData(await api.Lay_ds_danhba_gp(this.axios, {"thuebao_id": thuebao_id}));
            rowHDTB_GP = data[0];
            rowHDTB_GP.HDTB_ID = hdtb_id;
            this.options.dsHDTB_GP.push(this.changeKeysToUpper(rowHDTB_GP));
        },
        //TaoDuLieu_HDTB_TSL
        async TaoDuLieu_HDTB_TSL(hdtb_id,thuebao_id)
        {
            rowHDTB_TSL = {};
            let data = [];
            data = this.GetData(await api.Lay_ds_danhba_tsl(this.axios, {"thuebao_id": thuebao_id}));
            data.foreach (row =>
            {
                rowHDTB_TSL = row;
                rowHDTB_TSL.HDTB_ID = hdtb_id;
                this.options.dsHDTB_TSL.push(this.changeKeysToUpper(rowHDTB_TSL));
            });
        },
        //Tao du lieu cho bang HDTB_MGWAN
        async TaoDuLieu_HDTB_MGWAN(hdtb_id,thuebao_id)
        {
            let rowHDTBD_MGWAN = {};
            let data = [];
            data = this.GetData(await api.Lay_ds_danhba_megawan(this.axios, {"thuebao_id": thuebao_id}));
            rowHDTBD_MGWAN = data[0];
            rowHDTBD_MGWAN.HDTB_ID = hdtb_id;
            this.options.dsHDTB_MGWAN.push(this.changeKeysToUpper(rowHDTBD_MGWAN));
        },
            //Tao du lieu cho bang HDTB_IMS
        async TaoDuLieu_HDTB_IMS(hdtb_id,thuebao_id)
        {
            let rowHDTBD_IMS = {};
            let data = [];
            data = this.GetData(await api.Lay_ds_danhba_ims(this.axios, {"thuebao_id": thuebao_id}));
            rowHDTBD_IMS = data[0];
            rowHDTBD_IMS.HDTB_ID = hdtb_id;
            this.options.dsHDTB_IMS.push(this.changeKeysToUpper(rowHDTBD_IMS));
        },
        async docfile_vattu(dt){
            this.dt_error = []
            try{
                let dem = 0;
                let luu_magd = "Các MA_TB không tồn tại trong hệ thống: ";
                let luu_magd_loi = "Lỗi sinh MA_GD: ";
                let dong = "Dòng trong file: ";
                let thongbao = "Số lượng cấp vượt quá số lượng thực của thiết bị: ";
               
                for (let i = 0; i < dt.length; i++)
                {
                    let ma_tb = dt[i].MA_TB.toString();
                    let ma_kho = dt[i].MA_KHO;
                    let thuebao_id = 0;
                    let khachhang_id = 0; let hdkh_id = 0; let hdtb_id = 0; let doituong_id = 1; let kho_id = 0;
                    let kieuld_id_luoi; let loaitb_id = 0; let tocdotn_id = 0; let muccuoctn_id = 0; let donvi_id = 0;
                    let dichvuvt_id = dt[i].DICHVUVT_ID;
                    let ma_kh = "";let ma_hd= "";let ma_gd= "";let ten_kh= "";let diachi_kh= "";let ten_tb = "";let diachi_tb = "";let diachi_ld = "";
                    let input1 = {
                     "ma_tb":ma_tb,
                     "in_loaihd_id":LoaiHopDong.VATTU_THUEBAO,
                     "in_donvi_id":this.tt_nd.donvi_id,
                     "in_tthd_id":TrangThaiHD.MOI,
                     "in_nhanvien_id":this.tt_nd.nhanvien_id,
                     "in_donvi_dl_id":0,
                     "in_dichvuvt_id":dichvuvt_id,
                     "in_loaitb_id":0,
                    }
                    let ds = this.GetData(await api.Lay_ds_hopdong_theo_ma_tb(this.axios, input1));
                    if (ds.length <= 0)
                    {
                      
                        let input2 = {
                                "maTb": ma_tb,
                                "dichVuVtId": dichvuvt_id,
                                "donViDlId": null
                        }
                        let ds_2 = this.GetData(await api.getTTDanhBa_MaTB(this.axios, input2));
                       
                        if (ds_2 == null)
                        {
                            let obj = {
                                STT: i+1,
                                MA_TB: ma_tb,
                                LYDO_LOI: "Mã TB không tồn tại trong hệ thống"
                            };
                            this.dt_error.push(obj);
                            continue;
                        }
                        hdkh_id = 0;
                        thuebao_id = ds_2.thuebao_id;
                        ma_kh = ds_2.ma_kh.toString();
                        ma_hd = ds_2.ma_hd.toString();
                        khachhang_id = ds_2.khachhang_id.toString();
                        ten_kh = this.ChuanHoaTen(ds_2.ten_kh.toString());
                        diachi_kh = this.ChuanHoaTen(ds_2.diachi_kh.toString());
                        ten_tb = this.ChuanHoaTen(ds_2.ten_tb.toString());
                        diachi_tb = this.ChuanHoaTen(ds_2.diachi_tb.toString());
                        diachi_ld = this.ChuanHoaTen(ds_2.diachi_ld.toString());
                        loaitb_id = ds_2.loaitb_id.toString();
                        doituong_id = ds_2.doituong_id;
                        donvi_id = ds_2.donviql_id;
                        let ds_dichvu = [];
                        switch (dichvuvt_id)
                        {
                         
                            case DichVuVienThong.ADSL:
                                ds_dichvu = this.GetData(await api.Lay_ds_danhba_adsl(this.axios, {"thuebao_id": thuebao_id}));
                                break;
                            case DichVuVienThong.MEGA_EYES:
                                ds_dichvu = this.GetData(await api.Lay_ds_danhba_adsl(this.axios, {"thuebao_id": thuebao_id}));
                                break;
                            case DichVuVienThong.MEGAWAN:
                                ds_dichvu = this.GetData(await api.Lay_ds_danhba_megawan(this.axios, {"thuebao_id": thuebao_id}));
                                break;
                            case DichVuVienThong.TSL:
                                ds_dichvu = this.GetData(await api.Lay_ds_danhba_tsl(this.axios, {"thuebao_id": thuebao_id}));
                                break;
                            default:
                                ds_dichvu = this.GetData(await api.Lay_ds_danhba_codinh(this.axios, {"thuebao_id": thuebao_id}));
                                break;
                        }
                        
                        if (ds_dichvu.length > 0)
                        {
                            switch (dichvuvt_id)
                            {
                                case DichVuVienThong.CO_DINH:
                                    tocdotn_id = 0;
                                    muccuoctn_id = 0;
                                    break;

                                case DichVuVienThong.IMS:
                                    tocdotn_id = 0;
                                    muccuoctn_id = 0;
                                    break;
                                case DichVuVienThong.ADSL:
                                    if (ds_dichvu[0].tocdo_id != "")
                                        tocdotn_id = ds_dichvu[0].tocdo_id;
                                    if (loaitb_id != LoaiHinhTB.INTERNET_MYTV && loaitb_id != LoaiHinhTB.INTERNET_SGTV
                                        && loaitb_id != LoaiHinhTB.HTVC_FIBERVNN_ANALOG && loaitb_id != LoaiHinhTB.HTVC_FIBERVNN_DIGITAL)
                                    {
                                        if (ds_dichvu[0].muccuoc_id != "")
                                            muccuoctn_id = ds_dichvu[0].muccuoc_id;
                                    }
                                    else //MyTV và SGTV có muccuoc = 0 => trọn gói
                                        muccuoctn_id = 0;
                                    break;
                                case DichVuVienThong.MEGA_EYES:
                                    if (ds_dichvu[0].tocdo_id != "")
                                        tocdotn_id = ds_dichvu[0].tocdo_id;
                                    break;
                                case DichVuVienThong.MEGAWAN:
                                    if (ds_dichvu[0].tocdo_id != "")
                                        tocdotn_id = ds_dichvu[0].tocdo_id;
                                    break;
                                case DichVuVienThong.TSL:
                                    if (ds_dichvu[0].tocdo_id != "")
                                        tocdotn_id = ds_dichvu[0].tocdo_id;
                                    break;
                            }
                        }
                        //Thêm mới
                        let input3 = {
                              "kieuLdId": 72,
                              "loaiHdId": LoaiHopDong.THAY_DAY_MAY,
                              "loaiTbId": loaitb_id,
                              "mucCuocId":  muccuoctn_id,
                              "tocDoId": tocdotn_id}
                        let dset = this.GetData(await api.LayHuongGiao_TiepNhan(this.axios, input3));
                        let huonggiao_tn = 0;
                         let quytrinh_id = 0;
                        if(dset !== null){
                          huonggiao_tn = dset.huonggiao_id.toString();
                          quytrinh_id = dset.quytrinh_id.toString();
                        }
                        // Tao du lieu cho bang HD_KHACHHANG
                        var rowHDKH = {};
                        let loi_sinh_magd = false;
                    
                        hdkh_id = await this.getKey("HD_KHACHHANG");
                        rowHDKH.HDKH_ID = hdkh_id;
                        
                            if (!this.ts_sinhma_gd)
                                ma_gd = await this.lay_magd(LoaiHopDong.VATTU_THUEBAO);
                            else
                            {
                                let vmagd_temp = await this.lay_magd_v2(LoaiHopDong.VATTU_THUEBAO);
                                if (vmagd_temp.includes("ERROR:"))
                                {
                                    this.$toast.warning(vmagd_temp.split(':')[1]);
                                    loi_sinh_magd = true;
                                }
                                else
                                {
                                    ma_gd = vmagd_temp;
                                }
                            }
                            rowHDKH.MA_GD = ma_gd;
                            rowHDKH.MA_HD = ma_hd;
                    
                        rowHDKH.MA_KH = ma_kh;
                        if (khachhang_id > 0)
                            rowHDKH.KHACHHANG_ID = khachhang_id;
                        rowHDKH.TEN_KH = ten_kh;
                        rowHDKH.DIACHI_KH = diachi_kh;
                        rowHDKH.NGAYLAP_HD = this.tt_nd.ngay_cn;
                        rowHDKH.DONVI_ID = this.tt_nd.donvi_id;
                        rowHDKH.NHANVIEN_ID = this.tt_nd.nhanvien_id;
                        rowHDKH.KHLON_ID = 1;
                        rowHDKH.LOAIHD_ID = LoaiHopDong.VATTU_THUEBAO;
                        rowHDKH.NGUOI_CN = this.tt_nd.ma_nd;
                        rowHDKH.NGAY_CN = this.tt_nd.ngay_cn;
                        rowHDKH.MAY_CN = this.tt_nd.may_cn;
                        rowHDKH.IP_CN = this.tt_nd.ip;
                        rowHDKH.NGAY_YC = this.tt_nd.ngay_cn;
                        this.options.dsHDKH.push(rowHDKH);
                        //Tao du lieu cho bang HD_THUEBAO
                        this.options.dsHDTB=[];
                        var rowHDTB = {};
                        hdtb_id = await this.getKey("HD_THUEBAO");
                        rowHDTB.HDTB_ID = hdtb_id;
                        rowHDTB.HDKH_ID = hdkh_id;
                        if (thuebao_id > 0)
                            rowHDTB.THUEBAO_ID = thuebao_id;
                        rowHDTB.MA_TB = ma_tb;
                        rowHDTB.TEN_TB = ten_tb;
                        rowHDTB.DIACHI_TB = diachi_tb;
                        rowHDTB.DIACHI_LD = diachi_ld;
                        rowHDTB.GHICHU = "Thêm bằng file";
                        rowHDTB.KIEULD_ID = 72;
                        rowHDTB.DOITUONG_ID = doituong_id;
                        rowHDTB.TTHD_ID = TrangThaiHD.MOI;
                        rowHDTB.LOAITB_ID = loaitb_id;
                        rowHDTB.DICHVUVT_ID = dichvuvt_id;
                        rowHDTB.DONVI_ID = donvi_id;
                        rowHDTB.NGUOI_CN = this.tt_nd.ma_nd;
                        rowHDTB.NGAY_CN = this.tt_nd.ngay_cn;
                        rowHDTB.MAY_CN = this.tt_nd.may_cn;
                        rowHDTB.QUYTRINH_ID = quytrinh_id;      
                        this.options.dsHDTB.push(rowHDTB); 
                        //Tạo dữ liệu cho bảng CT_TIENHD
                        let rowCTTHD = {};

                        for (let i = 0; i < this.options.ds_tien_khoanmuc.length; i++)
                        {
                            rowCTTHD.HDTB_ID = hdtb_id;
                            let ct_tienhd_id  = await this.getKey("CT_TIENHD");
                            rowCTTHD.CT_TIENHD_ID = ct_tienhd_id;
                            rowCTTHD.KHOANMUCTT_ID = this.ds_tien_khoanmuc[i].khoanmuctt_id.toString();
                            rowCTTHD.TIEN = this.ds_tien_khoanmuc[i].tien.toString();
                            rowCTTHD.VAT = this.ds_tien_khoanmuc[i].vat.toString();

                            rowCTTHD.ID = parseInt(this.ds_tien_khoanmuc[i].khoanmuctt_id.toString());
                            rowCTTHD.LOAI_ID = 1;
                            this.options.dsCTTHD.push(rowCTTHD);
                        }
                        //TaoDuLieu_HDTB_DV
                        this.TaoDuLieu_HDTBDV(hdtb_id,thuebao_id);

                        if (dichvuvt_id == DichVuVienThong.DI_DONG)
                        {
                            this.TaoDuLieu_HDTB_DD(hdtb_id,thuebao_id);
                        }
                        if (dichvuvt_id == DichVuVienThong.ADSL)
                        {
                            this.TaoDuLieu_HDTB_ADSL(hdtb_id,thuebao_id);
                        }
                        if (dichvuvt_id == DichVuVienThong.CO_DINH)
                        {
                           this.TaoDuLieu_HDTB_CD(hdtb_id,thuebao_id);
                        }
                        if (dichvuvt_id == DichVuVienThong.GPHONE)
                        {
                            this.TaoDuLieu_HDTB_GP(hdtb_id,thuebao_id);
                        }
                        if (dichvuvt_id == DichVuVienThong.TSL)
                        {
                            this.TaoDuLieu_HDTB_TSL(hdtb_id,thuebao_id);
                        }
                        if (dichvuvt_id == DichVuVienThong.MEGAWAN)
                        {
                            this.TaoDuLieu_HDTB_MGWAN(hdtb_id,thuebao_id);
                        }
                        if (dichvuvt_id == DichVuVienThong.IMS)
                        {
                            this.TaoDuLieu_HDTB_IMS(hdtb_id,thuebao_id);
                        }
                        await api.Fn_them_hopdong_vattu_tb(this.axios, {
                            "vphanvung_id":this.$root.token.getPhanVungID(),
                            "vdshdkh":this.options.dsHDKH,
                            "vdshdtb":this.options.dsHDTB, 
                            "vdshdtbdd":this.options.dsHDTB_DD,
                            "vdshdtb_adsl":this.options.dsHDTB_ADSL, 
                            "vdshdtb_cd":this.options.dsHDTB_CD,
                            "vdshdtb_gp":this.options.dsHDTB_GP,
                            "vdshdtb_mgw":this.options.dsHDTB_MGWAN,
                            "vdshdtb_tsl":this.options.dsHDTB_TSL,
                            "vdshdtb_ims":this.options.dsHDTB_IMS,
                            "vhuonggiao_id":huonggiao_tn,
                            "vdshdtbdv":this.options.dsHDTBDV,
                            "vdsctthd":this.options.dsCTTHD,
                        }).then((res) => {
                            this.$root.$toast.success("Thêm mới dữ liệu thành công!");
                        });
                    }
                    else
                    {
                        hdkh_id = ds[0].hdkh_id;
                        ma_gd = ds[0].ma_gd;
                        ma_hd = ds[0].ma_hd;
                        ma_kh = ds[0].ma_kh;
                        if (ds[0].khachhang_id != "")
                            khachhang_id = ds[0].khachhang_id;
                        ten_kh = this.ChuanHoaTen(ds[0].ten_kh);
                        diachi_kh = this.ChuanHoaTen(ds[0].diachi_kh);
                    }
                
                    
                    let dtList = this.GetData(await api.Lay_ds_hopdong_tb_theo_hdkh_id(this.axios, {"in_hdkh_id": hdkh_id})); 
                   
                    if (dtList.length > 0)
                    {
                        let phieu_id = 0;
                        thuebao_id = dtList[0].thuebao_id;
                        ten_tb = dtList[0].ten_tb.toString();
                        diachi_tb = this.ChuanHoaTen(dtList[0].diachi_tb.toString());
                        diachi_ld = this.ChuanHoaTen(dtList[0]["diachi_ld"].toString());
                        kieuld_id_luoi = dtList[0].kieuld_id;
                        hdtb_id = dtList[0].hdtb_id;
                        donvi_id = dtList[0].donvi_id;
                         let input4= {
                            type:1,
                            param: hdtb_id
                        }
                        let sss = this.GetData(await api.fn_tt_giaophieu(this.axios,input4));
                      
                        if (sss != "" && sss != "-1")
                            phieu_id = parseInt(sss);
                        console.log(this.kieu_luachon)
                        if (this.kieu_luachon == 1)
                        {
                            // Cấp VT
                            let kho = this.GetData(await api.Kiemtra_tontai_makho(this.axios, {
                                "ma_kho":  ma_kho.toString()
                            })); 
                           if (kho.length > 0)
                            {
                                kho_id = kho[0].kho_id;
                            }
                            else
                            {
                                let obj = {
                                  STT: i+1,
                                  MA_TB: ma_tb,
                                  LYDO_LOI: "Kho " + ma_kho + " không tồn tại trong hệ thống!"
                                };
                                this.dt_error.push(obj);
                                continue;
                            }
                            let ds_vt_giao = this.changeKeysToUpper(this.GetData(await api.Lay_ds_tuvt_lhd_v2(this.axios, {
                                "kho_id":kho_id,
                                "loaitb_id":loaitb_id,
                                "loaihd_id":LoaiHopDong.VATTU_THUEBAO
                            })));
                           if (ds_vt_giao.length > 0)
                            {
                               
                                var check = dt[i].SERIAL == ""
                                    ? ds_vt_giao.filter((x) => x.VATTU_ID == dt[i].VATTU_ID)
                                    : ds_vt_giao.filter((x) => x.VATTU_ID == dt[i].VATTU_ID && x.SERIAL == dt[i].SERIAL )
                                  
                                if (check.length > 0)
                                {
                                    var table_seri = check;
                                    let serial = table_seri[0].SERIAL;
                                    let matbi = table_seri[0].MA_TBI;
                                    let sdvt_id = 0;
                                    let input5 = {
                                        type: 1,
                                        param: this.$root.token.getPhanVungID()
                                      }
                                    let vma_tinh = this.GetData(await api.fn_tt_tinh(this.axios,input5));
                                    let vlydoto_id = vma_tinh == "HNI" ? 10004 : 194; //10004: LD.2.3 | 194: TTT.1
                                   
                                    let ly_db = this.GetData(await api.Qlvt_mucdich(this.axios, {
                                         "ma_md": dt[i].MUCDICH
                                    }));
                                    let vlydocap = ly_db.length > 0 ? ly_db.Rows[0][0] : 110;
                                    // Nếu tìm thấy mucdich_id thì lấy - nếu ko lấy mặc định = 110: Máy mới 

                                    if (serial == "" && matbi != "")
                                    {
                                        let v_tuvt_id = table_seri[0].TUVT_ID;
                                        let v_thietbi_id = parseInt(table_seri[0].THIETBI_ID);
                                        let v_soluong = dt[0].SOLUONG;
                                        let t = this.GetData(await api.Fn_kiemtra_dm_vt(this.axios, {
                                                "p_hdtb_id": hdtb_id,
                                                "p_baohong_id": "0",
                                                "p_vattu_id": table_seri[0].VATTU_ID,
                                                "p_sl": v_soluong,
                                                "p_kho_nhan_id": kho_id
                                        }));
                                        
                                        if (t.toString() != "1")
                                        {
                                            let obj = {
                                                STT: i+1,
                                                MA_TB: ma_tb,
                                                LYDO_LOI: "Cảnh báo định mức: " + t.toString()
                                            };
                                            this.dt_error.push(obj);
                                            continue;
                                        }

                                        let v_ton = table_seri[0].SL_TON;
                                        if (v_soluong > v_ton)
                                        {
                                             let obj = {
                                                STT: i+1,
                                                MA_TB: ma_tb,
                                                LYDO_LOI: "Số lượng cấp vượt quá số lượng thực của thiết bị."
                                            };
                                            this.dt_error.push(obj);
                                            continue;
                                        }
                                        
                                        let ktd = this.GetData(await api.Qlvt_phieu_vt(this.axios, {
                                                    "phieu_id": phieu_id,
                                                    "hdtb_id": hdtb_id,
                                                    "baohong_id": 0,
                                                    "tuvt_id": table_seri[0].TUVT_ID
                                        }));
                                        if (ktd.length != 0)
                                        {
                                             let obj = {
                                                STT: i+1,
                                                MA_TB: ma_tb,
                                                LYDO_LOI: table_seri[0].TEN_VT + " đã được cấp bạn ko thể cấp tiếp được"
                                            };
                                            this.dt_error.push(obj);
                                            continue;
                                        }
                                        let v_phieuvt_id = await this.getKey("PHIEUVT_ID");
                                        let ma_tbi = "";
                                        if (matbi != "")
                                            ma_tbi = matbi.toString();
                                        let tinhtrang_tbi = 1;
                                        // Kiểm tra kho đơn vị cấp ra
                                        let temp_hdbh_id = 0;
                                        let vk = 0;
                                        if (hdtb_id != 0)
                                        {
                                            temp_hdbh_id = hdtb_id;
                                            vk = 1;
                                        }
                                        else
                                        {
                                            temp_hdbh_id = 0;
                                            vk = 2;
                                        }
                                        try
                                        {
                                          
                                            let kt_kq = this.GetData(await api.Kiemtra_vtcapra(this.axios, {
                                                  "vkieu": vk,
                                                  "vtemp_hdbh_id": temp_hdbh_id,
                                                  "vkieucap": "1",
                                                  "vtuvt_id": v_tuvt_id
                                            }));
                                            if (kt_kq.toString() != "1" || kt_kq == null)
                                            {
                                                  let obj = {
                                                    STT: i+1,
                                                    MA_TB: ma_tb,
                                                    LYDO_LOI: kt_kq.toString()
                                                };
                                                this.dt_error.push(obj);
                                                continue;
                                            }
                                        }
                                        catch (err)
                                        {

                                            if (err.message.includes("ORA-00904"))
                                            {
                                               this.$toast.warning("Hàm KIEMTRA_VTCAPRA không được tìm thấy. Bạn hãy liên hệ admin");
                                              return;
                                            }
                                            else
                                            {
                                              
                                                this.$toast.warning(" Có lỗi " + err);
                                                return;
                                            }

                                        }
                                       
                                        let vnhv_id_temp = this.tt_nd.nhanvien_id;
                                        let s = this.GetData(await api.Insert_phieu_vattu_v2(this.axios, {
                                                "vsdvt_id": "0",
                                                "vphieuvt_id": v_phieuvt_id,
                                                "vphieu_id": phieu_id.toString(),
                                                "vngay_cap": moment().format('DD/MM/YYYY HH:mm:ss').toString(),
                                                "vkieutbi_id": "1",
                                                "vtrangbi_id": "4",
                                                "vtuvt_id": v_tuvt_id,
                                                "vthietbi_id": v_thietbi_id,
                                                "vsoluong": v_soluong,
                                                "vserial": "",
                                                "vma_tbi": ma_tbi,
                                                "vnhanvien_id": vnhv_id_temp,
                                                "vdonvi_id": this.tt_nd.donvi_id,
                                                "vnguoi_cn": this.tt_nd.may_cn,
                                                "vmay_cn": this.tt_nd.ma_nd,
                                                "vip_cn": this.tt_nd.ip,
                                                "vlydocap_id": vlydocap,
                                                "vlydocapto_id": vlydoto_id,
                                                "vhdtb_id": hdtb_id,
                                                "vbaohong_id": "0",
                                                "vttpk": "1",
                                                "vtinhtrangbi_id": tinhtrang_tbi,
                                                "vghichu": "Thêm bằng file"
                                            }));
                                        
                                        if (s != "1")
                                        {
                                           let obj = {
                                                    STT: i+1,
                                                    MA_TB: ma_tb,
                                                    LYDO_LOI: s
                                            };
                                            this.dt_error.push(obj);
                                            continue;
                                        }
                                    }
                                    else if (serial == "" && matbi == "")
                                    {
                                        
                                        let v_tuvt_id = table_seri[0].TUVT_ID;
                                        let v_thietbi_id = parseInt(table_seri[0].THIETBI_ID);
                                        let v_soluong = dt[0].SOLUONG;

                                        let t = this.GetData(await api.Fn_kiemtra_dm_vt(this.axios, {
                                                "p_hdtb_id": hdtb_id,
                                                "p_baohong_id": "0",
                                                "p_vattu_id": table_seri[0].VATTU_ID,
                                                "p_sl": v_soluong,
                                                "p_kho_nhan_id": kho_id
                                        }));
                                        
                                        if (t.toString() != "1")
                                        {
                                            let obj = {
                                                    STT: i+1,
                                                    MA_TB: ma_tb,
                                                    LYDO_LOI: "Cảnh báo định mức: " + t.toString()
                                            };
                                            this.dt_error.push(obj);
                                            continue;
                                        }

                                        let v_ton = table_seri[0].SL_TON.toString();
                                        if (v_soluong > v_ton)
                                        {
                                           let obj = {
                                                    STT: i+1,
                                                    MA_TB: ma_tb,
                                                    LYDO_LOI: "Số lượng cấp vượt quá số lượng thực của thiết bị."
                                            };
                                            this.dt_error.push(obj);
                                            continue;
                                        }
                                        let ktd = this.GetData(await api.Qlvt_phieu_vt(this.axios, {
                                                    "phieu_id": phieu_id,
                                                    "hdtb_id": hdtb_id,
                                                    "baohong_id": 0,
                                                    "tuvt_id": table_seri[0].TUVT_ID
                                        }));
                                        if (ktd.length != 0)
                                        {
                                             let obj = {
                                                STT: i+1,
                                                MA_TB: ma_tb,
                                                LYDO_LOI: table_seri[0].TEN_VT + " đã được cấp bạn ko thể cấp tiếp được"
                                            };
                                            this.dt_error.push(obj);
                                            continue;
                                        }
                                        let v_phieuvt_id = await this.getKey("PHIEUVT_ID");
                                        let ma_tbi = "";
                                        if (matbi != "")
                                            ma_tbi = matbi.toString();
                                        let tinhtrang_tbi = 1;
                                        // Kiểm tra kho đơn vị cấp ra
                                        let temp_hdbh_id = 0;
                                        let vk = 0;
                                        if (hdtb_id != 0)
                                        {
                                            temp_hdbh_id = hdtb_id;
                                            vk = 1;
                                        }
                                        else
                                        {
                                            temp_hdbh_id = 0;
                                            vk = 2;
                                        }
                                        try
                                        {
                                          
                                            let kt_kq = this.GetData(await api.Kiemtra_vtcapra(this.axios, {
                                                  "vkieu": vk,
                                                  "vtemp_hdbh_id": temp_hdbh_id,
                                                  "vkieucap": "1",
                                                  "vtuvt_id": v_tuvt_id
                                            }));
                                            if (kt_kq.toString() != "1" || kt_kq == null)
                                            {
                                                  let obj = {
                                                    STT: i+1,
                                                    MA_TB: ma_tb,
                                                    LYDO_LOI: kt_kq.toString()
                                                };
                                                this.dt_error.push(obj);
                                                continue;
                                            }
                                        }
                                        catch (err)
                                        {

                                            if (err.message.includes("ORA-00904"))
                                            {
                                               this.$toast.warning("Hàm KIEMTRA_VTCAPRA không được tìm thấy. Bạn hãy liên hệ admin");
                                              return;
                                            }
                                            else
                                            {
                                              
                                                this.$toast.warning(" Có lỗi " + err);
                                                return;
                                            }

                                        }
                                        let vnhv_id_temp = this.tt_nd.nhanvien_id;
                                        
                                         let s = this.GetData(await api.Insert_phieu_vattu_v2(this.axios, {
                                                "vsdvt_id": "0",
                                                "vphieuvt_id": v_phieuvt_id,
                                                "vphieu_id": phieu_id.toString(),
                                                "vngay_cap": moment().format('DD/MM/YYYY HH:mm:ss').toString(),
                                                "vkieutbi_id": "1",
                                                "vtrangbi_id": "4",
                                                "vtuvt_id": v_tuvt_id,
                                                "vthietbi_id": v_thietbi_id,
                                                "vsoluong": v_soluong,
                                                "vserial": "",
                                                "vma_tbi": ma_tbi,
                                                "vnhanvien_id": vnhv_id_temp,
                                                "vdonvi_id": this.tt_nd.donvi_id,
                                                "vnguoi_cn": this.tt_nd.may_cn,
                                                "vmay_cn": this.tt_nd.ma_nd,
                                                "vip_cn": this.tt_nd.ip,
                                                "vlydocap_id": vlydocap,
                                                "vlydocapto_id": vlydoto_id,
                                                "vhdtb_id": hdtb_id,
                                                "vbaohong_id": "0",
                                                "vttpk": "1",
                                                "vtinhtrangbi_id": tinhtrang_tbi,
                                                "vghichu": "Thêm bằng file"
                                            }));
                                        if (s != "1")
                                        {
                                            let obj = {
                                                    STT: i+1,
                                                    MA_TB: ma_tb,
                                                    LYDO_LOI: s
                                            };
                                            this.dt_error.push(obj);
                                            continue;
                                        }
                                    }
                                    else
                                    {
                                        let v_tuvt_id = table_seri[0].TUVT_ID;
                                        let v_thietbi_id = table_seri[0].THIETBI_ID;
                                        let v_soluong = 1;
                                        let ma_tbi = "";
                                       
                                       let t = this.GetData(await api.Fn_kiemtra_dm_vt(this.axios, {
                                                "p_hdtb_id": hdtb_id,
                                                "p_baohong_id": "0",
                                                "p_vattu_id": table_seri[0].VATTU_ID,
                                                "p_sl": v_soluong,
                                                "p_kho_nhan_id": kho_id
                                        }));
                                        if (t.toString() != "1")
                                        {
                                            let obj = {
                                                    STT: i+1,
                                                    MA_TB: ma_tb,
                                                    LYDO_LOI: "Cảnh báo định mức: " + t.toString()
                                            };
                                            this.dt_error.push(obj);
                                            continue;
                                        }
                                        let ktd = this.GetData(await api.Qlvt_phieu_vt(this.axios, {
                                                    "phieu_id": phieu_id,
                                                    "hdtb_id": hdtb_id,
                                                    "baohong_id": 0,
                                                    "tuvt_id": table_seri[0].TUVT_ID
                                        }));
                                        if (ktd.length != 0)
                                        {
                                             let obj = {
                                                STT: i+1,
                                                MA_TB: ma_tb,
                                                LYDO_LOI: table_seri[0].TEN_VT + " đã được cấp bạn ko thể cấp tiếp được"
                                            };
                                            this.dt_error.push(obj);
                                            continue;
                                        }
                                        let tinhtrang_tbi = 1;
                                        // Kiểm tra kho đơn vị cấp ra
                                        let temp_hdbh_id = 0;
                                        let vk = 0;
                                        if (hdtb_id != 0)
                                        {
                                            temp_hdbh_id = hdtb_id;
                                            vk = 1;
                                        }
                                        else
                                        {
                                            temp_hdbh_id = 0;
                                            vk = 2;
                                        }
                                        try
                                        {
                                            let kt_kq = this.GetData(await api.Kiemtra_vtcapra(this.axios, {
                                                  "vkieu": vk,
                                                  "vtemp_hdbh_id": temp_hdbh_id,
                                                  "vkieucap": "1",
                                                  "vtuvt_id": v_tuvt_id
                                            }));
                                           
                                            if (kt_kq.toString() != "1" || kt_kq == null)
                                            {
                                                  let obj = {
                                                    STT: i+1,
                                                    MA_TB: ma_tb,
                                                    LYDO_LOI: kt_kq.toString()
                                                };
                                                this.dt_error.push(obj);
                                                continue;
                                            }
                                        }
                                        catch (err)
                                        {

                                            if (err.message.includes("ORA-00904"))
                                            {
                                                this.$toast.warning("Hàm KIEMTRA_VTCAPRA không được tìm thấy. Bạn hãy liên hệ admin");
                                                return;
                                            }
                                            else
                                            {
                                                this.$toast.warning(" Có lỗi " + err);
                                                return;
                                            }

                                        }
                                       let v_phieuvt_id = await this.getKey("PHIEUVT_ID");
                                        let vsvdt_id = 0;
                                        let vnhv_id_temp = this.tt_nd.nhanvien_id;
                                         let s = this.GetData(await api.Insert_phieu_vattu_v2(this.axios, {
                                                "vsdvt_id": "0",
                                                "vphieuvt_id": v_phieuvt_id,
                                                "vphieu_id": phieu_id.toString(),
                                                "vngay_cap": moment().format('DD/MM/YYYY HH:mm:ss').toString(),
                                                "vkieutbi_id": "1",
                                                "vtrangbi_id": "4",
                                                "vtuvt_id": v_tuvt_id,
                                                "vthietbi_id": v_thietbi_id,
                                                "vsoluong": v_soluong,
                                                "vserial": "",
                                                "vma_tbi": ma_tbi,
                                                "vnhanvien_id": vnhv_id_temp,
                                                "vdonvi_id": this.tt_nd.donvi_id,
                                                "vnguoi_cn": this.tt_nd.may_cn,
                                                "vmay_cn": this.tt_nd.ma_nd,
                                                "vip_cn": this.tt_nd.ip,
                                                "vlydocap_id": vlydocap,
                                                "vlydocapto_id": vlydoto_id,
                                                "vhdtb_id": hdtb_id,
                                                "vbaohong_id": "0",
                                                "vttpk": "1",
                                                "vtinhtrangbi_id": tinhtrang_tbi,
                                                "vghichu": "Thêm bằng file"
                                            }));
                                             
                                        if (s != "1")
                                        {
                                            let obj = {
                                                    STT: i+1,
                                                    MA_TB: ma_tb,
                                                    LYDO_LOI: s
                                            };
                                            this.dt_error.push(obj);
                                            continue;
                                        }
                                    }
                                }
                                else
                                {
                                  let obj = {
                                                    STT: i+1,
                                                    MA_TB: ma_tb,
                                                    LYDO_LOI: "VATTU_ID không tồn tại hoặc SERIAL không đúng/không tồn tại trong kho " + dt[0].MA_KHO
                                            };
                                            this.dt_error.push(obj);
                                            continue;
                                }
                            }
                            else
                            {                    
                                let obj = {
                                  STT: i+1,
                                  MA_TB: ma_tb,
                                  LYDO_LOI: "Không tìm thấy vật tư nào trong kho_id " + dt[0].MA_KHO
                                };
                                this.dt_error.push(obj);
                                continue;
                            }
                            
                        }
                        else
                        {
                            // THu Hồi
                            let loaitbi_id = 0;
                            let vnhv_id_temp = this.tt_nd.nhanvien_id;

                            let phieu_vt_id = 0;
                            let kieu = 3;
                            let tbi_id = 0;
                            let ktSeri = "0";
                            let sd = 0;
                            let vthietbi_id = 0;
                            let s_ri = "";
                            let vkieu = 3;
                            let ly_db = this.GetData(await api.Qlvt_mucdich(this.axios, {
                                         "ma_md": dt[i].MUCDICH
                                    }));
                            let vlydocap = ly_db.length > 0 ? ly_db[0][0] : 111;
                            let dtVatTu = this.GetData(await api.sp_getdanhmucvt_dvt_vattu_id(this.axios, {
                                         "vattu_id": dt[i].VATTU_ID
                                    }));
                           
                            if (dtVatTu.length > 0)
                            {
                                if (dtVatTu[0].loaitbi_id == "2")
                                {
                                    loaitbi_id = 2;
                                    if (dt[i].SERIAL == "")
                                    {
                                        let obj = {
                                                    STT: i+1,
                                                    MA_TB: ma_tb,
                                                    LYDO_LOI: "VATTU_ID: " + dt[i].VATTU_ID + " này thuộc loại phải có seri. Ban hãy nhập serial cho thiết bị "
                                            };
                                            this.dt_error.push(obj);
                                        continue;
                                    }
                                }
                                else if (dtVatTu[0].loaitbi_id == "3")
                                {
                                    loaitbi_id = 3;
                                }
                                else
                                {
                                  
                                    if (dt[i].SERIAL !== ""&&dt[i].SERIAL !== null)
                                    {
                                       let obj = {
                                                    STT: i+1,
                                                    MA_TB: ma_tb,
                                                    LYDO_LOI: "VATTU_ID: " + dt[i].VATTU_ID + " này thuộc loại phải không có seri. Ban hãy xóa dữ liệu serial "
                                            };
                                            this.dt_error.push(obj);
                                        continue;
                                    }
                                }
                            }

                            let serial_file = dt[i].SERIAL;
                            let dt_check = this.GetData(await api.sp_kiemtratb_sdvt(this.axios, {
                                         "p_serial": serial_file
                                    }));
                            if (loaitbi_id == 2)
                            {
                                let dt_tbidacap = this.GetData(await api.gan_thietbi_dacap(this.axios, {
                                         "p_serial": serial_file
                                    }));
                                       
                                if (dt_tbidacap.length > 0)
                                {
                                        let obj = {
                                                    STT: i+1,
                                                    MA_TB: ma_tb,
                                                    LYDO_LOI: "SERIAL: " + serial_file + " đã được cấp vào  ngày "
                                        + dt_tbidacap[0].NGAY_CAP + " bởi user " + dt_tbidacap[0].NGUOI_CN + ""
                                            };
                                            this.dt_error.push(obj);
                                        continue;
                                }
                                if (dt_check.length > 0)
                                {
                                  let checklogic = this.GetData(await api.fn_kiemtra_vt_thuhoi(this.axios, {
                                          "p_hdtb_id": hdtb_id,
                                          "p_baohong_id": "0",
                                          "p_serial": serial_file
                                    }));
                                    
                                    if (checklogic.toString() != "1")
                                    {
                                           let obj = {
                                                    STT: i+1,
                                                    MA_TB: ma_tb,
                                                    LYDO_LOI: checklogic.toString()
                                            };
                                            this.dt_error.push(obj);
                                        continue;
                                    }
                                    let dt_tbi = this.GetData(await api.sp_getthietbi(this.axios, {
                                           "p_serial": serial_file
                                    }));
                                   
                                    if (dt_tbi.length > 0 && dt_tbi[0].sl_ton.toString() == "1" &&
                                            dt_tbi[0].tonkho.toString() == "1")
                                    {
                                        let obj = {
                                                    STT: i+1,
                                                    MA_TB: ma_tb,
                                                    LYDO_LOI: "Serial này đang ở kho " + dt_tbi[0].t.toString() + ""
                                            };
                                            this.dt_error.push(obj);
                                        continue;
                                    }
                                    sd = dt_check[0].sdvt_id.toString();
                                    kieu = 1;
                                }
                                else
                                {
                                    let checklogic = this.GetData(await api.fn_kiemtra_vt_thuhoi(this.axios, {
                                          "p_hdtb_id": hdtb_id,
                                          "p_baohong_id": "0",
                                          "p_serial": serial_file
                                    }));
                                    if (checklogic.toString() != "1")
                                    {
                                           let obj = {
                                                    STT: i+1,
                                                    MA_TB: ma_tb,
                                                    LYDO_LOI: checklogic.toString()
                                            };
                                            this.dt_error.push(obj);
                                        continue;
                                    }
                                    let dt_tbi = this.GetData(await api.sp_getthietbi(this.axios, {
                                           "p_serial": serial_file
                                    }));
                                    if (dt_tbi.length > 0 && dt_tbi[0].sl_ton.toString() == "0" &&
                                            dt_tbi[0].tonkho.toString() == "1"
                                            || (dt_tbi.length > 0 && dt_tbi[0].tonkho.toString() == "0"))
                                    //neuton tại trong thiet bi
                                    {
                                        kieu = 2;
                                        tbi_id = dt_tbi[0].thietbi_id.toString();
                                    }
                                    else if (dt_tbi.length > 0 && dt_tbi[0].sl_ton.toString() == "1" &&
                                                 dt_tbi[0].tonkho.toString() == "1")
                                    {
                                        let obj = {
                                                    STT: i+1,
                                                    MA_TB: ma_tb,
                                                    LYDO_LOI: "Serial này đang ở kho " + dt_tbi[0].t.toString() +""
                                            };
                                            this.dt_error.push(obj);
                                        continue;
                                    }
                                    else
                                    {
                                        kieu = 3;
                                    }
                                }
                            }

                            if (loaitbi_id == 3)
                            {
                                          let obj = {
                                                    STT: i+1,
                                                    MA_TB: ma_tb,
                                                    LYDO_LOI: "LCU chưa dùng cuộn cáp"
                                            };
                                            this.dt_error.push(obj);
                                        continue;
                            }
                            if (loaitbi_id != 3 && loaitbi_id != 2)
                            {
                               let ds_vt_sd = this.GetData(await api.lay_ds_vattu_sd(this.axios, {
                                            "vthuebao_id": thuebao_id
                                    }));
                                var _is_sudung = ds_vt_sd.filter((x) => x.vattu_id == dt[i].VATTU_ID && x.serial ==  null);
                               
                                if (_is_sudung.length > 0)
                                {
                                   
                                    kieu = 1;
                                    let vsdvt_id = _is_sudung[0].sdvt_id;
                                    
                                    let ktsl = this.GetData(await api.get_sl_dd(this.axios, {
                                             "vsdvt_id": vsdvt_id
                                    }));
                                    if (ktsl.length > 0)
                                    {
                                        if (Object.values(ktsl[0])[0] != "0")
                                        {
                                            let sl_treo = Object.values(ktsl[0])[0];
                                            let sl_goc = dt[i].SOLUONG;
                                            if (sl_goc - sl_treo <= dt[i].SOLUONG || sl_goc - sl_treo <= 0)
                                            {
                                               let obj = {
                                                            STT: i+1,
                                                            MA_TB: ma_tb,
                                                            LYDO_LOI: "Hiện số lượng cấp thu hồi của thuê chưa QT là "
                                                                      + sl_treo + "/" + sl_goc + ". Bạn hãy kiểm tra lại số lượng thu hồi"
                                                    };
                                                    this.dt_error.push(obj);
                                                continue;
                                            }
                                        }
                                        // Insert PVT
                                    }
                                    let s = this.GetData(await api.capnhat_phieuvt_thuhoi(this.axios, {
                                                 "vsdvt_id": vsdvt_id,
                                                "vinsert": "1",
                                                "vphieuvt_id": "0",
                                                "vhdtb_id": hdtb_id,
                                                "vbaohong_id": "0",
                                                "vphieu_id": phieu_id,
                                                "vthuebao_id": thuebao_id,
                                                "vdonvi_id": this.tt_nd.donvi_id,
                                                "vnhanvien_id": vnhv_id_temp,
                                                "vnguoi_cn": this.tt_nd.ma_nd,
                                                "vmay_cn": this.tt_nd.may_cn,
                                                "vip_cn": this.tt_nd.ip,
                                                "vtinhtrangtbi_id": dt[i].TINHTRANG_ID,
                                                "vserial": "DAYTB",
                                                "vngay_th": moment().format('DD/MM/YYYY HH:mm:ss').toString(),
                                                "vvattu_id": dt[i].VATTU_ID,
                                                "vthietbi_id": tbi_id,
                                                "vsoluong": dt[i].SOLUONG,
                                                "vlydocap_id": 194,
                                                "vlydocaptbi_id": vlydocap,
                                                "vtrangbi_id": 4,
                                                "vkieu": kieu,
                                                "vghichu": "Thêm bằng file"
                                            }));
                                }
                                else
                                {
                                    kieu = 1;
                                    let s = this.GetData(await api.capnhat_phieuvt_thuhoi(this.axios, {
                                                 "vsdvt_id": "0",
                                                "vinsert": "1",
                                                "vphieuvt_id": "0",
                                                "vhdtb_id": hdtb_id,
                                                "vbaohong_id": "0",
                                                "vphieu_id": phieu_id,
                                                "vthuebao_id": thuebao_id,
                                                "vdonvi_id": this.tt_nd.donvi_id,
                                                "vnhanvien_id": vnhv_id_temp,
                                                "vnguoi_cn": this.tt_nd.ma_nd,
                                                "vmay_cn": this.tt_nd.may_cn,
                                                "vip_cn": this.tt_nd.ip,
                                                "vtinhtrangtbi_id": dt[i].TINHTRANG_ID,
                                                "vserial": "DAYTB",
                                                "vngay_th": moment().format('DD/MM/YYYY HH:mm:ss').toString(),
                                                "vvattu_id": dt[i].VATTU_ID,
                                                "vthietbi_id": tbi_id,
                                                "vsoluong": dt[i].SOLUONG,
                                                "vlydocap_id": 194,
                                                "vlydocaptbi_id": vlydocap,
                                                "vtrangbi_id": 4,
                                                "vkieu": kieu,
                                                "vghichu": "Thêm bằng file"
                                            }));
                                }
                                continue;
                            }
                          
                            let seripop = "";
                            let vsdvt_id_temp = 0;
                             let ds_vt_sd_temp = this.GetData(await api.lay_ds_vattu_sd(this.axios, {
                                            "vthuebao_id": thuebao_id
                                    }));
                            let _is_sudung_temp = ds_vt_sd_temp.filter((x) => x.vattu_id == dt[i].VATTU_ID && x.serial ==  serial_file);
                            if (_is_sudung_temp.length > 0)
                                vsdvt_id_temp = _is_sudung_temp[0].sdvt_id;
                            if (serial_file != "")
                                seripop = serial_file;
                            let s = this.GetData(await api.capnhat_phieuvt_thuhoi(this.axios, {
                                                 "vsdvt_id": vsdvt_id_temp,
                                                "vinsert": "1",
                                                "vphieuvt_id": "0",
                                                "vhdtb_id": hdtb_id,
                                                "vbaohong_id": "0",
                                                "vphieu_id": phieu_id,
                                                "vthuebao_id": thuebao_id,
                                                "vdonvi_id": this.tt_nd.donvi_id,
                                                "vnhanvien_id": vnhv_id_temp,
                                                "vnguoi_cn": this.tt_nd.ma_nd,
                                                "vmay_cn": this.tt_nd.may_cn,
                                                "vip_cn": this.tt_nd.ip,
                                                "vtinhtrangtbi_id": dt[i].TINHTRANG_ID,
                                                "vserial": "DAYTB",
                                                "vngay_th": moment().format('DD/MM/YYYY HH:mm:ss').toString(),
                                                "vvattu_id": dt[i].VATTU_ID,
                                                "vthietbi_id": tbi_id,
                                                "vsoluong": 1,
                                                "vlydocap_id": 194,
                                                "vlydocaptbi_id": vlydocap,
                                                "vtrangbi_id": 4,
                                                "vkieu": kieu,
                                                "vghichu": "Thêm bằng file"
                                            }));
                            this.load_ds()
                        }
                        this.dt_error = [];
                       
                    }
                    this.load_ds()
                }

                if (this.dt_error.length > 0)
                {
                   this.$toast.warning("Có " + this.dt_error.length + " bản ghi sai dữ liệu! Bạn hãy kiểm tra trong file!");
                  this.XuatExcel();
                }
            }
            catch (err)
            {
                this.$toast.error("Có lỗi : " + err);
            }
        },
        async load_ds(){
          let data = this.GetData(await api.Lay_ds_thaythe_file(this.axios, {
                    "vkieu": 0,
                    "in_hdkh_id": 0
                }));
            console.log(data)
            this.options.dsPhieu = data;
        },
         getDate (dateStr) {
            return moment(dateStr, 'DD/MM/YYYY HH:mm A')
          },
        async XacNhan_frmNhapText(){
          var frm = this.$refs.frmNhapText;
          this.kieu_luachon = frm.vkieu_luachon;
          if (this.form == 1 && frm.vkieu_luachon == 1){
              this.nhapExcelCapVT();
          }else if(this.form == 1 && frm.vkieu_luachon == 2){
              this.nhapExcelThuhoiVT();
          }
          else if(this.form == 2 && frm.vkieu_luachon == 1){
            this.taiFileMau("/static/files/MauFile_CapVatTu.xlsx");
          }
          else if(this.form == 2 && frm.vkieu_luachon ==2){
            this.taiFileMau("/static/files/MauFile_ThuHoiVatTu.xlsx")
          }
            
        },
    async popupFileExel() {
      this.form = 1;
     var frm = this.$refs.frmNhapText
              await frm.Load_frmNhapText()
              await frm.frmNhapText_3("Bạn hãy lựa chọn kiểu nhập vật tư", 1)
              frm.Text.title = "Danh sách kiểu nhập file"
              frm.functionName = 'btnXacNhan_Click'
              this.$refs.popup_frmNhapText.show();
    },
    XuatExcel() {
      if (this.dt_error.length <= 0) {
        this.$root.toastError("Không có dữ liệu lỗi để xuất file!");
        return;
      }
      this.loading(true);
      try {
        var dsach = XLSX.utils.json_to_sheet(this.dt_error);
        var wb = XLSX.utils.book_new(); // make Workbook of Excel
        XLSX.utils.book_append_sheet(wb, dsach, "DS"); // sheetAName is name of Worksheet
        XLSX.writeFile(wb, "dsLoi.xlsx");
      } 
      catch (error) {
      } finally {
        this.loading(false);
      }
   },
    async popupFileMau() {
      this.form = 2;
     var frm = this.$refs.frmNhapText
              await frm.Load_frmNhapText()
              await frm.frmNhapText_3("Bạn hãy lựa chọn kiểu xuất file", 1)
              frm.Text.title = "Danh sách kiểu xuất file"
              frm.functionName = 'btnXacNhan_Click'
              this.$refs.popup_frmNhapText.show()
    },
    taiFileMau(path){
            this.$confirm('Bạn có muốn tải file mẫu về không ?','Thông báo',{
                confirmButtonText: 'Đồng ý',
                cancelButtonText: 'Huỷ',
                type: 'info'
            }).then(() => {
                window.open(path);
            })
    },
     async getKey(keyname){
            try{
                let response = await api.get_keys(this.axios, keyname)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return Number(response.data.data)
                }else{
                    return 0
                }
            }catch(e){
                return 0
            }
        },
     ChuanHoaTen (ten) {
            if(ten!=null){
                let convertToArray = ten.toLowerCase().split(' ');
                let result = convertToArray.map(function(val) {
                        return val.replace(val.charAt(0), val.charAt(0).toUpperCase());
                        });
                return result.join(' ');
            } else return ''
    },
    changeKeysToUpper(obj) {
            var key, upKey;
            for (key in obj) {
                if (obj.hasOwnProperty(key)) {
                    upKey = key.toUpperCase();
                    if (upKey !== key) {
                        obj[upKey] = obj[key];
                        delete(obj[key]);
                    }
                    // recurse
                    if (typeof obj[upKey] === "object") {
                        this.changeKeysToUpper(obj[upKey]);
                    }
                }
            }
            return obj;
        },
    async lay_magd(loaihd_id){
            try{
                let response = await api.lay_magd(this.axios, loaihd_id)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return ''
                }
            }catch(e){
                return ''
            }
        },
    //Hàm call api
        GetData: function (response) {
            console.log(response)
            if (response.data.error === 200 || response.data.error === '200') {
                return response.data.data
            } else {
                console.log(response.data.error_code)
            }
            return []
        },
  }
};
</script>
