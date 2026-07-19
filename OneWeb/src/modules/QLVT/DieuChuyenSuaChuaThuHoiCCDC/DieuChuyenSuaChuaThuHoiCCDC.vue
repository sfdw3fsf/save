<template src='./DieuChuyenSuaChuaThuHoiCCDC.html'></template>
<style scoped src='./DieuChuyenSuaChuaThuHoiCCDC.scss'></style>
<script>
import { mapActions, mapState, mapGetters, mapMutations } from "vuex";
import breadcrumb from "@/components/breadcrumb";
import {
  getterName,
  actionName,
  statePropertyName,
  mutationName,
} from "../store/dieuchuyensuachuathuhoiccdc";
import moment from "moment";
import Vue from "vue";
import { Freeze, Edit, Group, Page, Filter, Sort, Resize,GridPlugin, Aggregate, Toolbar} from "@syncfusion/ej2-vue-grids";
import { currency } from '@/filters/currency'
import EventBus from '@/utils/eventBus'
import GanThuocTinh from "../GanThuocTinh/GanThuocTinh.vue";
import { Query,Predicate } from '@syncfusion/ej2-data';
import ChonVatTuCCDC from "../ChonVatTuCCDC/ChonVatTuCCDC.vue";



export default {
  components: { ChonVatTuCCDC,GanThuocTinh,breadcrumb },
  name: "DieuChuyenSuaChuaThuHoiCCDC",

  data() {
    return {
      header: {
        title: "ĐIỀU CHUYỂN, THU HỒI VÀ SỬA CHỮA CÔNG CỤ DỤNG CỤ",

        list: [
          {
            name: "Quản lí vật tư",
            link: { name: "Ui.buttons" },
          },
          {
            name: "Điều chuyển, thu hồi và sửa chữa công cụ dụng cụ",
            link: { name: "Ui.buttons" },
          },   
        ],
      },
      vnxcc_id : 0,
      vttph_id: 1,
      vchonct_id : 0,
      vchon_sp: "",
      vchungtu_goc: 0,
      dtVT_NV : [],
      dtVT_DV: [],
      dtVatTu_Le : [],
      dtThemVT: [],
      showTuDonVi:'none',
      showDenDonVi:'none',
      isFirstRow : true,
      isTabTongHop: true,
      params: {
        ngayYeuCau: new Date(),
        ngayChungTu: new Date(),
        tuNgayYC: new Date((new Date()).getTime() - 7*24*60*60*1000),
        denNgayYC: new Date(),
        mucDichId: -1,
        tuDonViId: -1,
        tenTuDonVi: "",
        denDonViId: -1,
        tenDenDonVi: "",
        tuNhanVienId: -1,
        denNhanVienId: -1,
        Tien: 0,
        chungTu: "",
        soPhieu: "",
        noiDung: ""
      },
      Enabled:{
       tuNhanVien: false,
       denNhanVien: false,
       nhapMoi: false,
       huyBo:false,
       ghiLai:false,
       Xoa:false,
       hoanThanh:false,
       xoaVT: false,
       chonVT: false
      },
      Checkbox:{
        denNhanVien:false,
        checkPhieuAll : false,
      },
      thongTinNguoiDung: {        
        donViId:  this.$root.token.getDonViID(),
        nhanVienId : this.$root.token.getNhanVienID(),
        phanVungId: this.$root.token.getPhanVungID(),
        nguoiCN: this.$root.token.getUserName(),
        mayCN: "DESKTOP-IDG67LD", //this.$auth.MAYCN
        ipCN: "10.59.90.128", //this.$auth.MAYCN
      },
      dataResponse:{
        DSNhanVien : [],
        DSMucDich: [],
        DSDonVi : [],
        DSTuNhanVien: [],
        DSDenNhanVien: [],
        DSTongHop: [],
        DSVatTu : [],
        DSChungTu: [],
      },
      VatTuFocus: {},
      MUCDICH_CCDC:{
        NHAP: "CCDC.N",
        CHUYEN: "CCDC.C",
        SUACHUA: "CCDC.S",
        THUHOI: "CCDC.T",
        THANHLY: "CCDC.TL",
        DAUKY: "CCDC.DK"
      },
      dateConfig: {
        default: new Date(),
        formatDay: "dd/MM/yyyy",
        waterMark: "Chọn ngày",
      },
      onFiltering: function(e) {
        var query = new Query();
        let predicate;  
        
        //frame the query based on search string with filter type.
        if (e.text !== ''){
          predicate  = new Predicate('TEN_NV', 'contains', e.text.trim(), true);              
          query.where(predicate)
        }

        //pass the filter data source, filter query to updateData method.
        e.updateData(this.dataSource, query);
      },
      
      
      editSettings:{ allowEditing: true, mode:'Batch'},
      customTemplate: function() {
        return  { template : Vue.component('footerTemplate', {
            template: `<input type="text" class="form-control tright" v-model="data.Custom" :disabled="true" style="margin: 7px 0px 7px 0px;" />`,
            data () { return { data: {data: {}}}; }
          })
        }
      },
      deleteTemplate: function () {
        return { template : Vue.component('columnTemplate', {
            template: `<div>
                          <button class="btn btn-danger lh14 pad3" @click="onDeleteRow" title="Xóa chi tiết">
                            <span class="-ap icon-close"></span>
                          </button>
                      </div>`,                        
            data () { 
              return { data: {} }; 
            },
            methods: {
              onDeleteRow() {                  
                  EventBus.$emit('onDeleteRow', this.data);
              },              
            }
          })
        }
      },
      onClickGanThuocTinh: function () {
        return { template : Vue.component('columnTemplate', {
            template: `<div>
                        <button class="btn btn-main lh14 pad3" @click="openFrmGanThuocTinh" title="Gán thuộc tính">
                          <span class="act -ap  icon-tick text-white"></span>  
                        </button>
                      </div>`,                        
            data () { 
              return { data: {} }; 
            },
            methods: {
              openFrmGanThuocTinh() {                  
                  EventBus.$emit('openFrmGanThuocTinh', this.data);
              },              
            }
          })
        }
      },
    };
  },

  computed: {
    ...mapState("dieuchuyensuachuathuhoiccdc", statePropertyName),
    ...mapGetters("dieuchuyensuachuathuhoiccdc", getterName),

    PTAggregatesColumns () {
      return [        
        // { field: 'TIEN_PB', type: 'Custom', footerTemplate: this.customTemplate, customAggregateFn: this.customAggregate },
        { field: 'TIEN', type: 'Custom', footerTemplate: this.customTemplate, customAggregateFn: this.SUMGiaTriConLai },
      
      ]
    },
    PTAggregatesColumnsTongHop () {
      return [       
        { field: 'TIEN', type: 'Custom', footerTemplate: this.customTemplate, customAggregateFn: this.customAggregateColTien }
      ]
    },
  },
  methods: {
    ...mapActions("dieuchuyensuachuathuhoiccdc", actionName),
    ...mapMutations("dieuchuyensuachuathuhoiccdc", mutationName),
    changeMoney(){
      this.params.Tien = currency(this.params.Tien)
    },
    customAggregate() {
      var TongTienPB = 0
      this.dataResponse.DSVatTu.forEach(e => {
        TongTienPB += parseInt(e.TIEN_PB)
      })
      return currency(TongTienPB);
    },
    SUMGiaTriConLai() {
      var TongTienPB = 0
      this.dataResponse.DSVatTu.forEach(e => {
        TongTienPB += parseInt(e.TIEN)
      })
      return currency(TongTienPB);
    },
    customAggregateColTien() {
      var TongTienPB = 0
      this.dataResponse.DSTongHop.forEach(e => {
        TongTienPB += e.TIEN
      })
      return currency(TongTienPB);
    },

    f_DateToString: function (value, format = "DD/MM/YYYY") {
      return moment(value).format(format);
    },
    f_MonthYearToString: function (value, format = "MM/YYYY") {
      return moment(value).format(format);
    },
    convertDateTime_DDMMYYYY_to_YYYYMMDD(datetime){
      datetime = datetime.split(" ")
      var thisDate = datetime[0].split('-');
      var newDate = [thisDate[2],thisDate[1],thisDate[0]].join("/");
      return newDate
    },
    async NAP_COMBO(){
      this.loading(true)
      this.dataResponse.DSMucDich = await this.getDSMucDich()
     
      this.dataResponse.DSDonVi = await this.getDSDonVi(this.thongTinNguoiDung.nhanVienId)
      
      // tối ưu performance k lấy full DS nhân viên, lấy ds nhân viên theo mục đích và đơn vị sau khi change
      // this.dataResponse.DSNhanVien = await this.getDSNhanVien()
      // this.dataResponse.DSTuNhanVien = this.dataResponse.DSNhanVien
      // this.dataResponse.DSDenNhanVien = this.dataResponse.DSNhanVien
      
      this.loading(false)
    },

    CLEAR(){
      this.loading(true)
      this.$refs.tuNhanVien.ej2Instances.value = null;
      this.$refs.tuNhanVien.dataBind();
      this.$refs.denNhanVien.ej2Instances.value = null;
      this.$refs.denNhanVien.dataBind();
      this.vnxcc_id = 0
      this.params.Tien = 0
      this.params.ngayYeuCau =  new Date()
      this.params.ngayChungTu =  new Date()
      this.dataResponse.DSTongHop = []
      this.params.chungTu = ""
      this.params.mucDichId = -1
      this.params.tuDonViId = -1
      this.params.tenTuDonVi = ""
      this.params.denDonViId = -1
      this.params.tenDenDonVi = ""
      this.params.tuNhanVienId = -1
      this.params.denNhanVienId = -1
      this.Enabled.tuNhanVien = false
      this.Enabled.denNhanVien = false
      this.params.noiDung = ""
      this.params.soPhieu = ""
      this.dataResponse.DSVatTu = []
      this.loading(false)
    },
    async onClickLayTT(){
      try{
        this.loading(true)
        this.SETBUTTON(0)
        await this.LAY_DS_CHUNGTU_CCDC()
        this.isFirstRow = true
        this.loading(false)
      }catch(e){
        this.$toast.error(e.data.message);
      }finally{
        this.loading(false);
      }
    },
    SETBUTTON(kieu){
      this.Enabled.tuNhanVien = false
      this.Enabled.denNhanVien = false
      this.Enabled.nhapMoi = false
      this.Enabled.huyBo = false
      this.Enabled.ghiLai = false
      this.Enabled.Xoa = false
      this.Enabled.hoanThanh = false
      if(kieu == 0){
        this.Enabled.nhapMoi = true
        this.CLEAR()
      }else if(kieu == 1){
        this.Enabled.huyBo = true
        this.Enabled.ghiLai = true
        this.CLEAR()
      }else if(kieu == 3){
        this.Enabled.tuNhanVien = true
        this.Enabled.denNhanVien = true
        this.Enabled.nhapMoi = true
        this.Enabled.huyBo = true
        this.Enabled.ghiLai = true
        this.Enabled.Xoa = true
        this.Enabled.hoanThanh = (this.vttph_id != 4)
      }
    },
    async LAY_DS_CHUNGTU_CCDC(){
      var DSChungTu = await this.getDSChungTuCCDC({
        tatCa: this.Checkbox.checkPhieuAll ? 1 : 0,
        donViId: this.thongTinNguoiDung.donViId,        
        nhanVienId: this.thongTinNguoiDung.nhanVienId,
        tuNgay: this.f_DateToString(this.params.tuNgayYC),
        denNgay: this.f_DateToString(this.params.denNgayYC),
      })

      DSChungTu.forEach(e=>{
        e.TIEN =  e.TIEN == "" || e.TIEN == null ?  0 : parseInt(e.TIEN)
        e.NGAY_CT = e.NGAY_CT.replaceAll("-","/")
      })

      this.dataResponse.DSChungTu = DSChungTu
     
    },
    gridChungTuSelectRow(){  
  
      if(this.isFirstRow && this.dataResponse.DSChungTu.length > 0){
        this.vnxcc_id = this.dataResponse.DSChungTu[0].NXCC_ID
        this.isFirstRow = false
      }
      var lstIndex = []    
      lstIndex.push(this.dataResponse.DSChungTu.map(x => x.NXCC_ID).indexOf(this.vnxcc_id.toString()))  
      this.$refs.gridChungTu.grid.selectRows(lstIndex)
    },
    async DSChungTuSelected(row) { 
      // lần đầu tự focus vào row đầu
      if(row){
        this.loading(true)
        await this.LAY_TT_CHUNGTU_CCDC(row)
        this.loading(false)
      }
    },
    async LAY_TT_CHUNGTU_CCDC(ChungTuFocus){
      this.vnxcc_id = ChungTuFocus.NXCC_ID
      this.params.soPhieu = ChungTuFocus.SOPHIEU
      this.params.mucDichId = ChungTuFocus.MUCDICH_ID
      this.params.noiDung = ChungTuFocus.ND_GIAO == null ? "" : ChungTuFocus.ND_GIAO
      this.params.ngayYeuCau = new Date(this.convertDateTime_DDMMYYYY_to_YYYYMMDD(ChungTuFocus.NGAYLAP.replaceAll("/","-")))
      this.params.ngayChungTu = new Date(this.convertDateTime_DDMMYYYY_to_YYYYMMDD(ChungTuFocus.NGAY_CT.replaceAll("/","-")))
      this.params.chungTu = ChungTuFocus.SO_CT
      this.vttph_id = ChungTuFocus.TTPH_ID
      this.Enabled.hoanThanh = (this.vttph_id != 4)
      this.Enabled.Xoa = (this.vttph_id != 4)
      this.vchungtu_goc = ChungTuFocus.CHUNGTU_ID == null ? 0 : ChungTuFocus.CHUNGTU_ID   
      this.params.Tien = ChungTuFocus.TIEN == null ? 0 : ChungTuFocus.TIEN
      this.Checkbox.denNhanVien = ChungTuFocus.LOAI == "1" ? true : false

      var DSVatTu = await this.getDSChiTietVatTu(this.vnxcc_id)

      DSVatTu.forEach(e=>{
        e.SOTHANG_PB =  e.SOTHANG_PB == "" || e.SOTHANG_PB == null ?  0 : parseInt(e.SOTHANG_PB)
        e.SOLUONG =  e.SOLUONG == "" || e.SOLUONG == null ?  0 : parseInt(e.SOLUONG)
        e.DONGIA =  e.DONGIA == "" || e.DONGIA == null ?  0 : parseInt(e.DONGIA)
        e.TIEN_PB =  e.TIEN_PB == "" || e.TIEN_PB == null ?  0 : parseInt(e.TIEN_PB)
        e.TIEN =  e.TIEN == "" || e.TIEN == null ?  0 : parseInt(e.TIEN)
      })
      this.dataResponse.DSVatTu = DSVatTu
    
      if(this.dataResponse.DSVatTu.length > 0 ){
        this.params.tuDonViId = this.dataResponse.DSVatTu[0].DONVI_GIAO_ID

        // Lấy danh sách từ nhân viên theo đơn vị, mục đích
        this.dataResponse.DSTuNhanVien = await this.getDSNVTheoMucDichDonVi({
          mucDichId : this.params.mucDichId,
          nhanVienId : this.thongTinNguoiDung.nhanVienId,
          donViId : this.params.tuDonViId,
          thangBD : this.f_MonthYearToString(this.params.ngayYeuCau)
        })

        var tuDonViId = this.params.tuDonViId
        var TuDonViFilter = this.dataResponse.DSDonVi.filter(function (el) {
          return el.donViId == tuDonViId;
        });
     
        this.params.tenTuDonVi = TuDonViFilter.length > 0 ? TuDonViFilter[0].tenDonVi : ""
        this.params.tuNhanVienId = this.dataResponse.DSVatTu[0].NHANVIEN_GIAO_ID.toString()
        this.params.denDonViId = this.dataResponse.DSVatTu[0].DONVI_ID

        // Lấy danh sách từ nhân viên theo đơn vị, mục đích
        this.dataResponse.DSDenNhanVien = await this.getDSNVTheoMucDichDonVi({
          mucDichId : this.params.mucDichId,
          nhanVienId : this.thongTinNguoiDung.nhanVienId,
          donViId : this.params.denDonViId,
          thangBD : this.f_MonthYearToString(this.params.ngayYeuCau)
        })

        var denDonViId = this.params.denDonViId
        var DenDonViFilter = this.dataResponse.DSDonVi.filter(function (el) {
          return el.donViId == denDonViId;
        });
        this.params.tenDenDonVi = DenDonViFilter.length > 0 ? DenDonViFilter[0].tenDonVi : ""
        this.params.denNhanVienId = this.dataResponse.DSVatTu[0].NHANVIEN_ID.toString()
        
      }else{
        this.dataResponse.DSTuNhanVien = []
        this.dataResponse.DSDenNhanVien = []
        this.params.tuDonViId = -1
        this.params.tenTuDonVi = ""
        this.params.tuNhanVienId = -1
        this.params.denDonViId = -1
        this.params.tenDenDonVi = ""
        this.params.denNhanVienId = -1
      }

      var DSTongHop = await this.getDSTongHop(this.vnxcc_id)

      DSTongHop.forEach(e=>{
        e.TIEN =  e.TIEN == "" || e.TIEN == null ?  0 : parseInt(e.TIEN)
        e.SOLUONG =  e.SOLUONG == "" || e.SOLUONG == null ?  0 : parseInt(e.SOLUONG)
        e.DONGIA =  e.DONGIA == "" || e.DONGIA == null ?  0 : parseInt(e.DONGIA)
      })
      this.dataResponse.DSTongHop = DSTongHop

      this.dtThemVT = []
      this.dtVatTu_Le = []
      this.SETBUTTON(3)
    },
    async DSTongHopSelected(row){

    },
    async DSVatTuSelected(row){
      if(row){        
        this.VatTuFocus = row
      }else{
        this.VatTuFocus = {}
      }

    },
    onClickNhapMoi(){
      this.SETBUTTON(1)
    },
    onClickHuyBo(){
      this.SETBUTTON(0)
    },
    async onClickXoa(){
      try{
        var isBoxConfirm = false;
        await this.$bvModal
          .msgBoxConfirm("Bạn có chắc chắn muốn xóa chứng từ không ?", {
            title: "Thông báo",
            centered: true,
            size: "md",
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
        this.loading(true)
        var ketQua = await this.xoaChungTuCCDC(this.vnxcc_id)
      
        if(ketQua == "1"){
          this.$toast.success("Xóa chứng từ thành công");
        }else{
          this.$toast.error("Lôi hàm xóa chứng từ " + ketQua);     
        }
        await this.LAY_DS_CHUNGTU_CCDC()
        this.dtThemVT = []
        this.dtVatTu_Le = []
        //focus lại row đầu
        this.isFirstRow = true
        this.loading(false)
      }catch(e){
        this.$toast.error(e.data.message);
      }finally{
        this.loading(false);
      }
    },
    async onClickHoanThanh(){
      try{
        if(this.vnxcc_id == 0){
          this.$toast.error("Bạn chưa chọn chứng từ để hoàn thiện");
          return
        }
        this.loading(true)
        var ketQua = await this.hoanThienCCDC({
          nxccId: this.vnxcc_id,
          nhanVienId: this.thongTinNguoiDung.nhanVienId,
          nguoiCN:this.thongTinNguoiDung.nguoiCN,
          mayCN:"",
          ipCN: ""
        })
    
        if(ketQua != "1"){
          this.$toast.error(ketQua);
        }else{
          this.$toast.success("Hoàn thiện chứng từ thành công");       
        }
        await this.LAY_DS_CHUNGTU_CCDC()
        this.loading(false)
      }catch(e){
        this.$toast.error(e.data.message);
      }finally{
        this.loading(false);
      }
    },

    async onClickGhiLai(){
      try{
        var loai = this.Checkbox.denNhanVien ? 1 : 2
        var maMucDich = this.getMaMucDich()
        var tien = maMucDich == this.MUCDICH_CCDC.SUACHUA ? this.params.Tien : 0
        var kiemTra = this.KIEMTRA_CAPNHAT()
        if(!kiemTra){
          return
        }
        if(!this.Enabled.nhapMoi){
          if(this.dtThemVT.length == 0 && this.dtVatTu_Le.length == 0 && this.dtVT_DV.length == 0 && this.dtVT_NV.length == 0){
            this.$toast.error("Bạn chưa có danh sách vật tư thiết bị");
            return
          }

          var isBoxConfirm = false;
          await this.$bvModal
            .msgBoxConfirm("Bạn có muốn thêm mới chứng từ công cụ dụng cụ này không ?", {
              title: "Thông báo",
              centered: true,
              size: "md",
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
          var kieu = 0
          var dt_temp = []
          if ((maMucDich == this.MUCDICH_CCDC.CHUYEN || 
              maMucDich == this.MUCDICH_CCDC.THUHOI || 
              maMucDich == this.MUCDICH_CCDC.SUACHUA || 
              maMucDich == this.MUCDICH_CCDC.THANHLY) && 
              this.dtVT_DV.length > 0)
          {
              kieu = 1;
              dt_temp = this.dtVT_DV;
          }
          else if ((maMucDich == this.MUCDICH_CCDC.CHUYEN || 
                    maMucDich == this.MUCDICH_CCDC.THUHOI || 
                    maMucDich == this.MUCDICH_CCDC.SUACHUA || 
                    maMucDich == this.MUCDICH_CCDC.THANHLY) && 
                    this.dtVT_NV.length > 0)
          {
              kieu = 2;
              dt_temp = this.dtVT_NV;
          }
          else
          {
            return;
          }
          
          this.loading(true)
          let vdata = []
          dt_temp.forEach(a=>{
            vdata.push({
              tbi: a.THIETBI_ID,
              vtt: a.VATTU_ID,
              sl: a.SOLUONG,
              tpb: a.TIEN_PB,
              st: a.SOTHANG_PB,
              tbd: a.THANG_BD,
              tkt: a.THANG_KT,
              gc: a.GHICHU, 
              sd: a.SDCC_ID,
              dv: this.params.denDonViId,
              nv: this.params.denNhanVienId,
              ti: a.TIEN,
              tt: a.TINHTRANGTBI_ID            
              })
          })
          let ketQua = await this.ThemMoiChungTuCCDC({
            soct :  this.params.chungTu,
            chungTuId: 0,
            ngayCT: this.f_DateToString(this.params.ngayYeuCau),
            ngayLap: this.f_DateToString(this.params.ngayChungTu) ,
            mucDichId: this.params.mucDichId == -1 ? 0 : this.params.mucDichId,
            donViGiaoId: this.params.tuDonViId,
            nhanVienGiaoId : this.params.tuNhanVienId,
            khoGiaoId : 0,
            nhanVienId : this.thongTinNguoiDung.nhanVienId,
            donViId : this.thongTinNguoiDung.donViId,
            ttphId :  1,
            nguoiCN : this.thongTinNguoiDung.nguoiCN, //ttnd.ma_nd
            mayCN : this.thongTinNguoiDung.mayCN, 
            ipcn : this.thongTinNguoiDung.ipCN, 
            noiDung : this.params.noiDung,
            data : JSON.stringify(vdata),
            dataTT : "",
            kieu : kieu,
            loai : loai,
            donViNhanId : this.params.denDonViId,
            tien : tien
          })
    
          if(typeof parseInt(ketQua) != 'number'){
            this.$toast.error(ketQua)
            this.loading(false)
            return
          }
          this.vnxcc_id = ketQua
            
        }else{
          if(this.vnxcc_id != 0){
            
            var isBoxConfirm = false;
            await this.$bvModal
              .msgBoxConfirm("Bạn có muốn cập nhật chứng từ công cụ dụng cụ này không ?", {
                title: "Thông báo",
                centered: true,
                size: "md",
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

            this.loading(true)
            var kieu = 0
            var dt_temp = []
            if ((maMucDich == this.MUCDICH_CCDC.CHUYEN || 
                maMucDich == this.MUCDICH_CCDC.THUHOI || 
                maMucDich == this.MUCDICH_CCDC.SUACHUA || 
                maMucDich == this.MUCDICH_CCDC.THANHLY) && 
                this.dtVT_DV > 0)
            {
                kieu = 1;
                dt_temp = this.dtVT_DV;
            }
            else if ((maMucDich == this.MUCDICH_CCDC.CHUYEN || 
                      maMucDich == this.MUCDICH_CCDC.THUHOI || 
                      maMucDich == this.MUCDICH_CCDC.SUACHUA || 
                      maMucDich == this.MUCDICH_CCDC.THANHLY) && 
                      this.dtVT_NV > 0)
            {
                kieu = 2;
                dt_temp = this.dtVT_NV;
            }
            else if(kieu == 0)
            {
              if(this.dataResponse.DSVatTu.length > 0){
                dt_temp = this.dataResponse.DSVatTu
              }
            }

            let vdata = []
            dt_temp.forEach(a=>{
              vdata.push({
                tbi: a.THIETBI_ID,
                vtt: a.VATTU_ID,
                sl: a.SOLUONG,
                tpb: a.TIEN_PB,
                st: a.SOTHANG_PB,
                tbd: a.THANG_BD,
                tkt: a.THANG_KT,
                gc: a.GHICHU, 
                sd: a.SDCC_ID,
                dv: this.params.denDonViId,
                nv: this.params.denNhanVienId,
                ti: a.TIEN,
                tt: a.TINHTRANGTBI_ID            
                })
            })
          
            let ketQua = await this.CapNhapChungTuCCDC({
              nxccId :  this.vnxcc_id,
              soct: this.params.chungTu,
              chungTuId : 0,
              ngayCt: this.f_DateToString(this.params.ngayYeuCau),
              ngayLap: this.f_DateToString(this.params.ngayChungTu),
              mucDichId: this.params.mucDichId == -1 ? 0 : this.params.mucDichId,
              donViGiaoId: this.params.tuDonViId,
              nhanVienGiaoId : this.params.tuNhanVienId,
              khoGiaoId : 0,
              nhanVienId : this.thongTinNguoiDung.nhanVienId,
              donViId : this.thongTinNguoiDung.donViId,
              nguoiCN : this.thongTinNguoiDung.nguoiCN,
              mayCN : this.thongTinNguoiDung.mayCN,
              ipcn : this.thongTinNguoiDung.ipCN,
              noiDung : this.params.noiDung,
              data : JSON.stringify(vdata),
              kieu : kieu,
              loai : loai,
              tien : tien
            })
          
            if(ketQua != "1"){
              this.$toast.error(ketQua);
            }
          }
          
        }

        this.params.denNgayYC = this.params.ngayChungTu
        this.params.tuNgayYC = new Date((this.params.denNgayYC).getTime() - 7*24*60*60*1000)
        await this.LAY_DS_CHUNGTU_CCDC()
        this.dtVT_DV = []
        this.dtVT_NV = []
        this.dtThemVT = []
        this.dtVatTu_Le = []
        this.loading(false)
      }catch(e){
        this.$toast.error(e.data.message);
      }finally{
        this.loading(false);
      }
    },

    KIEMTRA_CAPNHAT(){
      var mess = ""
      if(!this.Enabled.nhapMoi){
        if (this.dtThemVT.length > 0)
        { 
          var vchonct_id = this.vchonct_id
          this.dtThemVT.forEach(e=>{
            if(e.CHUNGTU_ID != vchonct_id){
              mess += "Danh sách thiết bị không đúng chứng từ \t\n"
              return
            }
          })
          // var q = dtThemVT.AsEnumerable().Where(l => l["CHUNGTU_ID"].ToString() != vchonct_id.ToString());
          // if (q.AsDataView().Count > 0)
          // {
          //     Message_Box.ShowWarning("Danh sách thiết bị không đúng chứng từ");
          //     return false;
          // }
        }
      }

      if(this.params.chungTu == ""){
        mess += "Bạn phải nhập số chứng từ \t\n"
      }
      if(this.params.mucDichId == -1){
        mess += "Bạn phải chọn mục đích cho chứng từ \t\n"
      }
      if(this.params.tuDonViId == -1){
        mess += "Bạn phải chọn từ đơn vị nào \t\n"
      }
      if(this.params.denDonViId == -1){
        mess += "Bạn phải chọn đến đơn vị nào \t\n"
      }

      if(this.params.denNhanVienId == -1 || this.params.denNhanVienId == null){
        mess += "Bạn phải chọn đến nhân viên nào \t\n"
      }

      if(this.params.denDonViId == -1 && (this.params.denNhanVienId == -1 || this.params.denNhanVienId == null)){
        mess += "Bạn phải chọn đến đơn vị hoặc đến nhân viên nào \t\n"
      }
     
      if(this.getMaMucDich() == this.MUCDICH_CCDC.SUACHUA && this.dtVT_DV.length > 0){
        if(this.params.denDonViId != this.params.tuDonViId || this.params.tuNhanVienId != this.params.denNhanVienId){
          mess += "Bạn sửa chữa cho đơn vị nhận vào nào phải chọn đúng từ nhân viên đến nhân viên đó. \t\n"
        }

        if(this.Checkbox.denNhanVien){
          mess += "Bạn sửa chữa cho đơn vị Không được check đến nhân viên \t\n"
        }
      }else if(this.getMaMucDich() == this.MUCDICH_CCDC.SUACHUA && this.dtVT_NV.length > 0){
        if(this.params.denDonViId != this.params.tuDonViId || this.params.tuNhanVienId != this.params.denNhanVienId){
          mess += "Bạn sửa chữa cho đơn vị nhận vào nào phải chọn đúng từ nhân viên đến nhân viên đó. \t\n"
        }

        if(!this.Checkbox.denNhanVien){
          mess += "Bạn sửa chữa cho nhân viên Không được check đến đơn vị \t\n"
        }
      }
      if(mess != ""){
        this.$toast.error(mess);
        return false
      }
      return true
    },

    getMaMucDich(){      
      var mucDichId = this.params.mucDichId
      var thongTinMucDich = this.dataResponse.DSMucDich.filter(function (el) {
        return el.mucDichId == mucDichId;
      });
      if(thongTinMucDich.length > 0){
        return thongTinMucDich[0].maMd
      }else{
        return ""
      }
      
    },
    onClickXoaHet(){
      this.dataResponse.DSVatTu = []
    },
    async onClickMucDich(){
      if(this.params.mucDichId == -1){
        return
      }
      this.loading(true)
      this.dataResponse.DSTuNhanVien = []
      this.Enabled.tuNhanVien = this.params.tuDonViId != -1 && this.params.tuDonViId != null
      if(this.params.mucDichId == 202){
        var ThangTH = this.f_MonthYearToString(this.params.ngayChungTu)
        this.dataResponse.DSTuNhanVien = await this.getDSNVSuDungCCDC(ThangTH)        
      }else{
        // this.dataResponse.DSTuNhanVien = this.dataResponse.DSNhanVien
        if(this.params.tuDonViId != -1 && this.params.tuDonViId != null){
          this.dataResponse.DSTuNhanVien = await this.getDSNVTheoMucDichDonVi({
            mucDichId : this.params.mucDichId,
            nhanVienId : this.thongTinNguoiDung.nhanVienId,
            donViId : this.params.tuDonViId,
            thangBD : this.f_MonthYearToString(this.params.ngayYeuCau)
          })          
        }
      }

      this.Enabled.denNhanVien = this.params.denDonViId != -1 && this.params.denDonViId != null
      this.dataResponse.DSDenNhanVien = []
      if(this.params.denDonViId != -1 && this.params.denDonViId != null){
        this.dataResponse.DSDenNhanVien = await this.getDSNVTheoMucDichDonVi({
          mucDichId : this.params.mucDichId,
          nhanVienId : this.thongTinNguoiDung.nhanVienId,
          donViId : this.params.denDonViId,
          thangBD : this.f_MonthYearToString(this.params.ngayYeuCau)
        })
      }

      this.params.tuNhanVienId = -1
      this.params.denNhanVienId = -1

      this.loading(false)
    },
   
    async onClickTuDonVi(row){
      this.params.tuDonViId  = row.donViId;
      this.params.tenTuDonVi = row.tenDonVi;
      this.loading(true)

      //clear box tuNhanVien
      this.$refs.tuNhanVien.ej2Instances.value = null;
      this.$refs.tuNhanVien.dataBind();
      this.params.tuNhanVienId = -1
  
      if(!this.Enabled.nhapMoi){
        if(this.params.tuDonViId != -1 && this.params.tuDonViId != null && 
          this.params.mucDichId != 0 && 
          this.params.mucDichId != -1 && 
          this.params.mucDichId != null ){ // NGhiệp vụ mới, lấy nhân viên theo mục đích và đơn vị, tối ưu load nhân viên

          this.Enabled.tuNhanVien = true  
          
          this.dataResponse.DSTuNhanVien = await this.getDSNVTheoMucDichDonVi({
            mucDichId : this.params.mucDichId,
            nhanVienId : this.thongTinNguoiDung.nhanVienId,
            donViId : this.params.tuDonViId,
            thangBD : this.f_MonthYearToString(this.params.ngayYeuCau)
          })
      
          var TuDonViId = this.params.tuDonViId          
        
          if(this.dataResponse.DSTuNhanVien.length == 2){
            var DSCayDonVi = await this.getDSCayDonVi(this.params.tuDonViId)
            if(DSCayDonVi.length > 0){
              for(const i in DSCayDonVi){
                var DSDonViThiCong = this.getDSDonViThiCong(DSCayDonVi[i].donViId)
                if(DSDonViThiCong.length > 0){
                  var DSTuNhanVien = this.dataResponse.DSTuNhanVien
                  var KTToTruong = await this.kiemTraToTruong(DSCayDonVi[i].donViId)

                  
                  if(KTToTruong[0] != 0){                    
                    var DSNVFilterDonVi2 = DSTuNhanVien.filter(function (el) {
                      return  el.donViId == TuDonViId ||
                              el.donViId == -1 ||
                              el.nhanVienId == KTToTruong[0];                    
                    })
                    if(this.params.mucDichId == 202){
                      DSNVFilterDonVi2 = DSTuNhanVien.filter(function (el) {
                        return  el.donViId == TuDonViId                                      
                      })
                    }
                    this.dataResponse.DSTuNhanVien = DSNVFilterDonVi2
                  }
                }else{
                  this.loading(false)
                  return
                }
                break
              }
            }
          }

        }else{     
          this.Enabled.tuNhanVien = false
          this.dataResponse.DSTuNhanVien = []
        }
      }else{
        if(this.params.tuDonViId != -1 && this.params.tuDonViId != null && 
          this.params.mucDichId != 0 && 
          this.params.mucDichId != -1 && 
          this.params.mucDichId != null) { // NGhiệp vụ mới, lấy nhân viên theo mục đích và đơn vị, tối ưu load nhân viên

          this.Enabled.tuNhanVien = true  
          
          this.dataResponse.DSTuNhanVien = await this.getDSNVTheoMucDichDonVi({
            mucDichId : this.params.mucDichId,
            nhanVienId : this.thongTinNguoiDung.nhanVienId,
            donViId : this.params.tuDonViId,
            thangBD : this.f_MonthYearToString(this.params.ngayYeuCau)
          })
          console.log(this.dataResponse.DSTuNhanVien , " = DSTuNhanVien ")
          
          var TuDonViId = this.params.tuDonViId
         

          if(this.dataResponse.DSTuNhanVien.length == 2){
            var DSCayDonVi = await this.getDSCayDonVi(this.params.tuDonViId)
     
            if(DSCayDonVi.length > 0){
              for(const i in DSCayDonVi){
                var DSDonViThiCong = this.getDSDonViThiCong(DSCayDonVi[i].donViId)
                if(DSDonViThiCong.length > 0){
                  var DSTuNhanVien = this.dataResponse.DSTuNhanVien
                  var KTToTruong = await this.kiemTraToTruong(DSCayDonVi[i].donViId)

                  
                  if(KTToTruong[0] != 0){                    
                    var DSNVFilterDonVi2 = DSTuNhanVien.filter(function (el) {
                      return  el.donViId == TuDonViId ||
                              el.donViId == -1 ||
                              el.nhanVienId == KTToTruong[0];                    
                    })
                    if(this.params.mucDichId == 202){
                      DSNVFilterDonVi2 = DSTuNhanVien.filter(function (el) {
                        return  el.donViId == TuDonViId                                      
                      })
                    }
                    this.dataResponse.DSTuNhanVien = DSNVFilterDonVi2
                  }
                }else{
                  this.loading(false)
                  return
                }
                break
              }
            }
          }      
        }else{          
          this.Enabled.tuNhanVien = false
          this.dataResponse.DSTuNhanVien = []
        }
      }

      
      this.loading(false)
    },
    async onClickDenDonVi(row){
      this.params.denDonViId  = row.donViId;
      this.params.tenDenDonVi = row.tenDonVi;
      this.loading(true)

      //clear box tuNhanVien
      this.$refs.denNhanVien.ej2Instances.value = null;
      this.$refs.denNhanVien.dataBind();
      this.params.denNhanVienId = -1
      // this.dataResponse.DSDenNhanVien = this.dataResponse.DSNhanVien
      if(!this.Enabled.nhapMoi){
        if(this.params.denDonViId != -1 && this.params.denDonViId != null && 
          this.params.mucDichId != 0 && 
          this.params.mucDichId != -1 && 
          this.params.mucDichId != null){       

          this.Enabled.denNhanVien = true  
          
          this.dataResponse.DSDenNhanVien = await this.getDSNVTheoMucDichDonVi({
            mucDichId : this.params.mucDichId,
            nhanVienId : this.thongTinNguoiDung.nhanVienId,
            donViId : this.params.denDonViId,
            thangBD : this.f_MonthYearToString(this.params.ngayYeuCau)
          })

          var denDonViId = this.params.denDonViId
          
          if(this.dataResponse.DSDenNhanVien.length == 2){
            var DSCayDonVi = await this.getDSCayDonVi(this.params.denDonViId)
          
            if(DSCayDonVi.length > 0){
              for(const i in DSCayDonVi){
                var DSDonViThiCong = this.getDSDonViThiCong(DSCayDonVi[i].donViId)
                if(DSDonViThiCong.length > 0){
                  var DSDenNhanVien = this.dataResponse.DSDenNhanVien
                  var KTToTruong = await this.kiemTraToTruong(DSCayDonVi[i].donViId)

                  if(KTToTruong[0] != 0){                    
                    var DSNVFilterDonVi2 = DSDenNhanVien.filter(function (el) {
                      return  el.donViId == denDonViId ||
                              el.donViId == -1 ||
                              el.nhanVienId == KTToTruong[0];                    
                    })              
                    this.dataResponse.DSDenNhanVien = DSNVFilterDonVi2
                  }
                }else{
                  this.loading(false)
                  return
                }
                break
              }
            }
          }

        }else{
          this.Enabled.denNhanVien = false
          this.dataResponse.DSDenNhanVien = []
         
        }
      }else{
        if(this.params.denDonViId != -1 &&this.params.denDonViId != null && this.params.mucDichId != 0 && 
          this.params.mucDichId != -1 && 
          this.params.mucDichId != null){          
          this.Enabled.denNhanVien = true

          var DenDonViId = this.params.denNhanVienId

          this.dataResponse.DSDenNhanVien = await this.getDSNVTheoMucDichDonVi({
            mucDichId : this.params.mucDichId,
            nhanVienId : this.thongTinNguoiDung.nhanVienId,
            donViId : this.params.denDonViId,
            thangBD : this.f_MonthYearToString(this.params.ngayYeuCau)
          })
       
          if(this.dataResponse.DSDenNhanVien.length == 2){
            var DSCayDonVi = await this.getDSCayDonVi(this.params.denDonViId)

            if(DSCayDonVi.length > 0){
              for(const i in DSCayDonVi){
                var DSDonViThiCong = this.getDSDonViThiCong(DSCayDonVi[i].donViId)
                if(DSDonViThiCong.length > 0){
                  var DSDenNhanVien = this.dataResponse.DSDenNhanVien
                  var KTToTruong = await this.kiemTraToTruong(DSCayDonVi[i].donViId)

                  
                  if(KTToTruong[0] != 0){                    
                    var DSNVFilterDonVi2 = DSDenNhanVien.filter(function (el) {
                      return  el.donViId == DenDonViId ||
                              el.donViId == -1 ||
                              el.nhanVienId == KTToTruong[0];                    
                    })
                    
                    this.dataResponse.DSDenNhanVien = DSNVFilterDonVi2
                  }
                }else{
                  this.loading(false)
                  return
                }
                break
              }
            }
          }      
        }else{       
          this.Enabled.denNhanVien = false
          this.dataResponse.DSDenNhanVien = []
        }
      }
      this.loading(false)
    },
    async editGridVatTuGiao(arg) {      
      let gridTongHop = this.$refs.gridTongHop
      let changes = gridTongHop.getBatchChanges() 
      if(changes.changedRecords.length == 0){
        return
      }
      if(arg.columnName == "SOLUONG" ){    
        if(changes.changedRecords[0].SOLUONG < 0 ){
          changes.changedRecords[0].SOLUONG = 0             
        }
      }
      gridTongHop.batchUpdate(changes)
      // this.$refs.gridTongHop.grid.refreshColumns()
    },
    async editGridVatTu(arg) {      
      let gridVatTu = this.$refs.gridVatTu
      let changes = gridVatTu.getBatchChanges() 
      if(changes.changedRecords.length == 0){
        return
      }
      
      gridVatTu.batchUpdate(changes)
      // this.$refs.gridTongHop.grid.refreshColumns()
    },
    onDeleteRowHandler(item){ 
      var NewDSVatTu = this.dataResponse.DSVatTu.filter(function (el) {
        return  el.VATTU_ID != item.VATTU_ID ||
                el.NHANVIEN_ID != item.NHANVIEN_ID ||
                el.DONVI_ID != item.DONVI_ID ||
                el.THIETBI_ID != item.THIETBI_ID;
      })      
      NewDSVatTu.forEach(e=>{
        e.SOTHANG_PB =  e.SOTHANG_PB == "" || e.SOTHANG_PB == null ?  0 : parseInt(e.SOTHANG_PB)
        e.SOLUONG =  e.SOLUONG == "" || e.SOLUONG == null ?  0 : parseInt(e.SOLUONG)
        e.DONGIA =  e.DONGIA == "" || e.DONGIA == null ?  0 : parseInt(e.DONGIA)
        e.TIEN_PB =  e.TIEN_PB == "" || e.TIEN_PB == null ?  0 : parseInt(e.TIEN_PB)
        e.TIEN =  e.TIEN == "" || e.TIEN == null ?  0 : parseInt(e.TIEN)
      })
      this.dataResponse.DSVatTu = NewDSVatTu
    },
    async openFrmGanThuocTinh(item){
      this.loading(true)
      var f = this.$refs.frmGanThuocTinh
      f.params.vatTuId = item.VATTU_ID
      f.params.thietBiId = item.THIETBI_ID
      f.Open()
      this.loading(false)
    },
    async chonVatTuCCDCTuDonVi(){
      await this.openfrmChonVatTuCCDC(2)
    },
    async chonVatTuCCDCTuNhanVien(){
      await this.openfrmChonVatTuCCDC(1)
    },
    async openfrmChonVatTuCCDC(loai){
      
      var maMD = this.getMaMucDich()

      if(this.params.mucDichId == 0 || 
         this.params.mucDichId == -1 || 
         this.params.mucDichId == null ||
         maMD == this.MUCDICH_CCDC.NHAP){
        this.$toast.error("Bạn chưa chọn mục đích");
        return
      }

      if(maMD == this.MUCDICH_CCDC.SUACHUA && this.params.Tien <= 0){
        this.$toast.error("Bạn phải nhập tổng số tiền sửa chữa trước");
        return;
      }

      if(this.params.tuDonViId == 0 || this.params.tuDonViId == -1 || this.params.tuDonViId == null){
        this.$toast.error("Bạn chưa chọn đơn vị");
        return;
      }
    
      if(this.params.tuNhanVienId == 0 || this.params.tuNhanVienId == -1 || this.params.tuNhanVienId == null){
        this.$toast.error("Bạn chưa chọn nhân viên");
        return;
      }
      
      var nhanvien_id = this.params.tuNhanVienId      
      var NhanVien = this.dataResponse.DSTuNhanVien.filter(function (el) {
        return el.NHANVIEN_ID == nhanvien_id;
      })      
 
      let f = this.$refs.frmChonVatTuCCDC;
      f.kieu = 1
      f.donvi_id = this.params.tuDonViId
      f.nhanvien_id = this.params.tuNhanVienId
      f.tenNhanVien = NhanVien.length > 0 ? NhanVien[0].TEN_NV : ""
      f.ma_md = maMD      
      f.dtp = this.params.ngayYeuCau
      f.loai = loai
      f.tongtien = this.params.Tien
      f.openChonVatTuCCDC();
    },
    async closeChonVatTuCCDCTuDonVi(){
      try{
        this.loading(true);
        
        var maMD = this.getMaMucDich()
        let f = this.$refs.frmChonVatTuCCDC;

        if (f.xacnhan) {
          this.dtVT_NV = []
          this.dtVT_DV = []
          for(const i in f.dt){
            if(maMD == this.MUCDICH_CCDC.SUACHUA){

              var vk = 0
              if(f.pb == "1"){ //1 tháng
                var row = {
                  CTCC_ID: 0,
                  DONGIA: f.dt[i].DONGIA,
                  CHUNGTU_ID: null,
                  DVI_TINH: f.dt[i].DVI_TINH,
                  GHICHU: "",      
                  LOAI_TBI: "",
                  LOAI_VT: "",          
                  MA_TBI: "",
                  MA_VT: f.dt[i].MA_VT,      
                  SDCC_ID: 0,
                  SERIAL: "",        
                  SOLUONG: f.dt[i].SL_XUAT,
                  SOTHANG_PB: 0,     
                  TEN_VT: f.dt[i].TEN_VT,  
                  THANG_BD: "",
                  THANG_KT: "",
                  THIETBI_ID: f.dt[i].THIETBI_ID,
                  TIEN_CL: 0 ,
                  TIEN: 0,
                  TIEN_PB: 0,
                  TINHTRANGTBI_ID: 0,   
                  VATTU_ID: f.dt[i].VATTU_ID,
                }

                var tien = parseInt(f.dt[i].TIEN_CL)
                var tienpb_goc = parseInt(f.dt[i].TIEN_PB)
                var slgoc = parseInt(f.dt[i].SOLUONG)
                var slxuat = parseInt(f.dt[i].SL_XUAT)
              
                var tempDate = new Date(this.params.ngayYeuCau)
                
                tempDate.setMonth(tempDate.getMonth() - 1) 
                
                // Đặt tên củ chuối theo source cũ C#
                var dt1 = new Date(tempDate.setDate(1))

                // Đặt tên củ chuối theo source cũ C#
                var a = new Date(f.dt[i].THANG_BD.toString().slice(0,4), f.dt[i].THANG_BD.toString().slice(4,6) -1, 1)              
                
                // Lấy số tháng chênh lệch
                var soThangPhanBo = parseInt((dt1 - a)/ (1000 * 3600 * 24 * 30) + 1);
              
                if(soThangPhanBo > 0){

                  vk = 2 
              
                  if(parseInt(f.dt[i].SOTHANG_PB) - soThangPhanBo > 0){
                    row.TIEN_PB = tienpb_goc                
                    row.SOTHANG_PB = soThangPhanBo
                    row.LOAI_TBI = f.dt[i].LOAI_TBI
                    row.LOAI_VT = f.dt[i].LOAI_VT
                    row.TIEN = tien
                    row.SERIAL = f.dt[i].SERIAL
                    row.MA_TBI = f.dt[i].MA_TBI
                    row.SDCC_ID = f.dt[i].SDCC_ID
                    row.CTCC_ID = f.dt[i].CTCC_ID
                    row.THANG_BD = this.f_MonthYearToString(a)
                    row.THANG_KT = this.f_MonthYearToString(dt1)
                    row.TINHTRANGTBI_ID = f.dt[i].TINHTRANGTBI_ID
                    this.dtVT_DV.push(row)
                
                  }else{
                    row.TIEN_PB = tienpb_goc
                    row.TIEN = tien
                    row.SOTHANG_PB = 24
                    row.LOAI_TBI = f.dt[i].LOAI_TBI
                    row.LOAI_VT = f.dt[i].LOAI_VT                  
                    row.SERIAL = f.dt[i].SERIAL
                    row.MA_TBI = f.dt[i].MA_TBI
                    row.SDCC_ID = f.dt[i].SDCC_ID
                    row.CTCC_ID = f.dt[i].CTCC_ID
                    row.THANG_BD = this.f_MonthYearToString(a)                 
                    row.THANG_KT = this.f_MonthYearToString(dt1)
                  }
                }

                if(vk == 2){
                  var tien_sc = parseInt(f.dt[i].TIEN_SC)
                  var row2 = {
                    CTCC_ID: f.dt[i].CTCC_ID,
                    DONGIA: f.dt[i].DONGIA,
                    CHUNGTU_ID: null,
                    DVI_TINH: f.dt[i].DVI_TINH,
                    GHICHU: "",      
                    LOAI_TBI: f.dt[i].LOAI_TBI,
                    LOAI_VT: f.dt[i].LOAI_VT,          
                    MA_TBI: f.dt[i].MA_TBI,
                    MA_VT: f.dt[i].MA_VT,      
                    SDCC_ID: parseInt(f.dt[i].SOTHANG_PB) - soThangPhanBo > 0 ? 0 : f.dt[i].SDCC_ID,
                    SERIAL: f.dt[i].SERIAL,        
                    SOLUONG: f.dt[i].SL_XUAT,
                    SOTHANG_PB: 1,     
                    TEN_VT: f.dt[i].TEN_VT,  
                    THANG_BD: this.f_MonthYearToString(this.params.ngayYeuCau),
                    THANG_KT: this.f_MonthYearToString(this.params.ngayYeuCau),
                    THIETBI_ID: f.dt[i].THIETBI_ID,
                    TIEN_CL: 0 ,
                    TIEN:  parseInt(tien - tienpb_goc * soThangPhanBo),
                    TIEN_PB: parseInt(parseInt(f.dt[i].TIEN_PB) + tien_sc/slxuat),
                    TINHTRANGTBI_ID: f.dt[i].TINHTRANGTBI_ID,   
                    VATTU_ID: f.dt[i].VATTU_ID,
                  }
                  this.dtVT_DV.push(row2)          
                  var tempDate = new Date(this.params.ngayYeuCau)
                
                  tempDate.setMonth(tempDate.getMonth())
                  
                  // Đặt tên củ chuối theo source cũ C#
                  var dt_t = new Date(tempDate.setDate(1))          
                  // Đặt tên củ chuối theo source cũ C#
                  var dt_s = new Date(f.dt[i].THANG_KT.toString().slice(0,4), f.dt[i].THANG_KT.toString().slice(4,6) -1, 1)              
                  
                  // Lấy số tháng chênh lệch
                  var soThangPhanBo_s = parseInt((dt_s - dt_t)/ (1000 * 3600 * 24 * 30) + 1);               
                  if(soThangPhanBo_s != 0){

                    var slg = parseInt(f.dt[i].SOLUONG)
                    var sl = parseInt(f.dt[i].SL_XUAT)
                
                    var tempDate = new Date(dt_t)
                    tempDate.setMonth((tempDate.getMonth()) + parseInt(f.dt[i].SOTHANG_PB) - 1) 
                    var THANG_KT = new Date(tempDate)                
                    var row3 = {
                      CTCC_ID: f.dt[i].CTCC_ID,
                      DONGIA: f.dt[i].DONGIA,
                      CHUNGTU_ID: null,
                      DVI_TINH: f.dt[i].DVI_TINH,
                      GHICHU: "",      
                      LOAI_TBI: f.dt[i].LOAI_TBI,
                      LOAI_VT: f.dt[i].LOAI_VT,          
                      MA_TBI: f.dt[i].MA_TBI,
                      MA_VT: f.dt[i].MA_VT,      
                      SDCC_ID: 0,
                      SERIAL: f.dt[i].SERIAL,        
                      SOLUONG: f.dt[i].SL_XUAT,
                      SOTHANG_PB: soThangPhanBo_s > 0 ? soThangPhanBo_s : parseInt(f.dt[i].SOTHANG_PB) - 1,     
                      TEN_VT: f.dt[i].TEN_VT,  
                      THANG_BD: this.f_MonthYearToString(dt_t),
                      THANG_KT: soThangPhanBo_s > 0 ? this.f_MonthYearToString(dt_s) :this.f_MonthYearToString(THANG_KT),
                      THIETBI_ID: f.dt[i].THIETBI_ID,
                      TIEN_CL: 0 ,
                      TIEN: parseInt(tien + tien_sc / sl / soThangPhanBo - tienpb_goc * soThangPhanBo - parseInt(f.dt[i].TIEN_PB) - tien_sc / slxuat),
                      TIEN_PB: parseInt(f.dt[i].TIEN_PB),
                      TINHTRANGTBI_ID: f.dt[i].TINHTRANGTBI_ID,   
                      VATTU_ID: f.dt[i].VATTU_ID,
                    }                
                    this.dtVT_DV.push(row3)                
                  }

                }else{ // vk = 3 theo C#
                  var tien_sc = parseInt(f.dt[i].TIEN_SC)
                  var row2 = {
                    CTCC_ID: f.dt[i].CTCC_ID,
                    DONGIA: f.dt[i].DONGIA,
                    CHUNGTU_ID: null,
                    DVI_TINH: f.dt[i].DVI_TINH,
                    GHICHU: "",      
                    LOAI_TBI: f.dt[i].LOAI_TBI,
                    LOAI_VT: f.dt[i].LOAI_VT,          
                    MA_TBI: f.dt[i].MA_TBI,
                    MA_VT: f.dt[i].MA_VT,      
                    SDCC_ID: 0,
                    SERIAL: f.dt[i].SERIAL,        
                    SOLUONG: f.dt[i].SL_XUAT,
                    SOTHANG_PB: 1,     
                    TEN_VT: f.dt[i].TEN_VT,  
                    THANG_BD: this.f_MonthYearToString(this.params.ngayYeuCau),
                    THANG_KT: this.f_MonthYearToString(this.params.ngayYeuCau),
                    THIETBI_ID: f.dt[i].THIETBI_ID,
                    TIEN_CL: 0 ,
                    TIEN: tien,
                    TIEN_PB: parseInt(parseInt(f.dt[i].TIEN_PB) + tien_sc/slxuat),
                    TINHTRANGTBI_ID: f.dt[i].TINHTRANGTBI_ID,   
                    VATTU_ID: f.dt[i].VATTU_ID,
                  }                
                  this.dtVT_DV.push(row2)               
                  var slg = parseInt(f.dt[i].SOLUONG)
                  var sl = parseInt(f.dt[i].SL_XUAT)

                  var tempDate = new Date(this.params.ngayYeuCau)
                  tempDate.setMonth(tempDate.getMonth() + 1)
                  // Đặt tên củ chuối theo source cũ C#
                  var dt_t = new Date(tempDate.setDate(1))

                  // Đặt tên củ chuối theo source cũ C#          
                  var dt_s = new Date(f.dt[i].THANG_KT.toString().slice(0,4), f.dt[i].THANG_KT.toString().slice(4,6) -1, 1)              
                  
                  // Lấy số tháng chênh lệch
                  var soThangPhanBo_s = parseInt((dt_s - dt_t)/ (1000 * 3600 * 24 * 30) + 1);

                  var row3 = {
                    CTCC_ID: f.dt[i].CTCC_ID,
                    DONGIA: f.dt[i].DONGIA,
                    CHUNGTU_ID: null,
                    DVI_TINH: f.dt[i].DVI_TINH,
                    GHICHU: "",      
                    LOAI_TBI: f.dt[i].LOAI_TBI,
                    LOAI_VT: f.dt[i].LOAI_VT,          
                    MA_TBI: f.dt[i].MA_TBI,
                    MA_VT: f.dt[i].MA_VT,      
                    SDCC_ID: 0,
                    SERIAL: f.dt[i].SERIAL,        
                    SOLUONG: f.dt[i].SL_XUAT,
                    SOTHANG_PB: soThangPhanBo_s,     
                    TEN_VT: f.dt[i].TEN_VT,  
                    THANG_BD: this.f_MonthYearToString(dt_t),
                    THANG_KT: this.f_MonthYearToString(dt_s),
                    THIETBI_ID: f.dt[i].THIETBI_ID,
                    TIEN_CL: 0 ,
                    TIEN: tien,
                    TIEN_PB: parseInt(f.dt[i].TIEN_PB),
                    TINHTRANGTBI_ID: f.dt[i].TINHTRANGTBI_ID,   
                    VATTU_ID: f.dt[i].VATTU_ID,
                  }                
                  this.dtVT_DV.push(row3)         
                }
        
              }else{ // f.pb == "2" Phân bổ đều

                var row = {
                  CTCC_ID: 0,
                  DONGIA: f.dt[i].DONGIA,
                  CHUNGTU_ID: null,
                  DVI_TINH: f.dt[i].DVI_TINH,
                  GHICHU: "",      
                  LOAI_TBI: "",
                  LOAI_VT: "",          
                  MA_TBI: "",
                  MA_VT: f.dt[i].MA_VT,      
                  SDCC_ID: 0,
                  SERIAL: "",        
                  SOLUONG: f.dt[i].SL_XUAT,
                  SOTHANG_PB: 0,     
                  TEN_VT: f.dt[i].TEN_VT,  
                  THANG_BD: "",
                  THANG_KT: "",
                  THIETBI_ID: f.dt[i].THIETBI_ID,
                  TIEN_CL: 0 ,
                  TIEN: 0,
                  TIEN_PB: 0,
                  TINHTRANGTBI_ID: 0,   
                  VATTU_ID: f.dt[i].VATTU_ID,
                }

                var tien = parseInt(f.dt[i].TIEN_CL)
                var tienpb_goc = parseInt(f.dt[i].TIEN_PB)
                var slgoc = parseInt(f.dt[i].SOLUONG)
                var slxuat = parseInt(f.dt[i].SL_XUAT)
              
                var tempDate = new Date(this.params.ngayYeuCau)
                tempDate.setMonth(tempDate.getMonth() - 1)
                
                // Đặt tên củ chuối theo source cũ C#
                var dt1 = new Date(tempDate.setDate(1))

                // Đặt tên củ chuối theo source cũ C#
                var dt2 = new Date(f.dt[i].THANG_BD.toString().slice(0,4), f.dt[i].THANG_BD.toString().slice(4,6) -1, 1)              
                
                // Lấy số tháng chênh lệch
                var soThangPhanBo = parseInt((dt1 - dt2)/ (1000 * 3600 * 24 * 30) + 1);
                
                if(soThangPhanBo > 0){

                  vk = 2 
              
                  if(parseInt(f.dt[i].SOTHANG_PB) - soThangPhanBo > 0){
                    row.TIEN_PB = tienpb_goc                
                    row.SOTHANG_PB = soThangPhanBo
                    row.LOAI_TBI = f.dt[i].LOAI_TBI
                    row.LOAI_VT = f.dt[i].LOAI_VT
                    row.TIEN = tien
                    row.SERIAL = f.dt[i].SERIAL
                    row.MA_TBI = f.dt[i].MA_TBI
                    row.SDCC_ID = f.dt[i].SDCC_ID
                    row.CTCC_ID = f.dt[i].CTCC_ID
                    row.THANG_BD = this.f_MonthYearToString(dt2)
                    row.THANG_KT = this.f_MonthYearToString(dt1)
                    row.TINHTRANGTBI_ID = f.dt[i].TINHTRANGTBI_ID
                    this.dtVT_DV.push(row)
                    console.log(row , " = row 111111")
                  }else{
                    row.TIEN_PB = tienpb_goc
                    row.SOTHANG_PB = 24
                    row.LOAI_TBI = f.dt[i].LOAI_TBI
                    row.LOAI_VT = f.dt[i].LOAI_VT
                    row.TIEN = tien
                    row.SERIAL = f.dt[i].SERIAL
                    row.MA_TBI = f.dt[i].MA_TBI
                    row.SDCC_ID = f.dt[i].SDCC_ID
                    row.CTCC_ID = f.dt[i].CTCC_ID
                    row.THANG_BD = this.f_MonthYearToString(this.params.ngayYeuCau)   

                    var tempDate = new Date(this.params.ngayYeuCau)
                    tempDate.setMonth(tempDate.getMonth()+ parseInt(f.dt[i].SOTHANG_PB) - 1)
                    
                    // Đặt tên củ chuối theo source cũ C#
                    var THANG_KT = new Date(tempDate)
                    row.THANG_KT = this.f_MonthYearToString(THANG_KT)
                  }
                }

                if(vk == 2){
                  
                  var tien_sc = parseInt(f.dt[i].TIEN_SC)
                  var slg = parseInt(f.dt[i].SOLUONG)
                  var sl = parseInt(f.dt[i].SL_XUAT)

                  var tempDate = new Date(this.params.ngayYeuCau)
                  tempDate.setMonth(tempDate.getMonth())  
                  
                  // Đặt tên củ chuối theo source cũ C#
                  var dt_t = new Date(tempDate.setDate(1))
                  console.log(dt_t , " = dt_t")
                  console.log(this.params.ngayYeuCau , " = this.params.ngayYeuCau")
                  // Đặt tên củ chuối theo source cũ C#
                  var dt_s = new Date(f.dt[i].THANG_KT.toString().slice(0,4), f.dt[i].THANG_KT.toString().slice(4,6) -1, 1)              
                  
                  // Lấy số tháng chênh lệch
                  var soThangPhanBo_s = parseInt((dt_s - dt_t)/ (1000 * 3600 * 24 * 30) + 1);
                  
                  var tiensc_pc = soThangPhanBo_s > 0 ? parseInt(tien_sc / sl / soThangPhanBo_s) : parseInt(tien_sc / sl / parseInt(f.dt[i].SOTHANG_PB))
                  var row2_TIEN = tien - tienpb_goc * soThangPhanBo + tien_sc       
                  

                  var tempDate = new Date(this.params.ngayYeuCau)
                  tempDate.setMonth(tempDate.getMonth()  + parseInt(f.dt[i].SOTHANG_PB) - 1) 
                  
                  // Đặt tên củ chuối theo source cũ C#
                  var THANG_KT = new Date(tempDate) 
                  console.log(THANG_KT , " - THANG_KT")
                  console.log(dt_s , " - dt_s")
                  console.log(soThangPhanBo_s , " = soThangPhanBo_s")
                  var row2 = {
                    CTCC_ID: f.dt[i].CTCC_ID,
                    DONGIA: f.dt[i].DONGIA,
                    CHUNGTU_ID: null,
                    DVI_TINH: f.dt[i].DVI_TINH,
                    GHICHU: "",      
                    LOAI_TBI: f.dt[i].LOAI_TBI,
                    LOAI_VT: f.dt[i].LOAI_VT,          
                    MA_TBI: f.dt[i].MA_TBI,
                    MA_VT: f.dt[i].MA_VT,      
                    SDCC_ID: parseInt(f.dt[i].SOTHANG_PB) - soThangPhanBo > 0 ? 0 : f.dt[i].SDCC_ID,
                    SERIAL: f.dt[i].SERIAL,        
                    SOLUONG: f.dt[i].SL_XUAT,
                    SOTHANG_PB: soThangPhanBo_s > 0 ?  soThangPhanBo_s :  f.dt[i].SOTHANG_PB,     
                    TEN_VT: f.dt[i].TEN_VT,  
                    THANG_BD: this.f_MonthYearToString(dt_t),
                    THANG_KT: soThangPhanBo_s > 0 ? this.f_MonthYearToString(dt_s) : this.f_MonthYearToString(THANG_KT),
                    THIETBI_ID: f.dt[i].THIETBI_ID,
                    TIEN_CL: 0 ,
                    TIEN: row2_TIEN,
                    TIEN_PB: parseInt(f.dt[i].TIEN_PB) + tiensc_pc,
                    TINHTRANGTBI_ID: f.dt[i].TINHTRANGTBI_ID,   
                    VATTU_ID: f.dt[i].VATTU_ID,
                  }
                  this.dtVT_DV.push(row2) 
                  console.log(row2 , " = row2 111111")
                  
                }else{ // vk = 3 theo C#
                  var tien_sc = parseInt(f.dt[i].TIEN_SC)
                  var slg = parseInt(f.dt[i].SOLUONG)
                  var sl = parseInt(f.dt[i].SL_XUAT)

                  var dt_t = new Date(f.dt[i].THANG_BD.toString().slice(0,4), f.dt[i].THANG_BD.toString().slice(4,6) -1, 1)  

                  var dt_s = new Date(f.dt[i].THANG_KT.toString().slice(0,4), f.dt[i].THANG_KT.toString().slice(4,6) -1, 1)  

                  var soThangPhanBo_s = parseInt((dt_s - dt_t)/ (1000 * 3600 * 24 * 30) + 1);

                  var row3 = {
                    CTCC_ID: f.dt[i].CTCC_ID,
                    DONGIA: f.dt[i].DONGIA,
                    CHUNGTU_ID: null,
                    DVI_TINH: f.dt[i].DVI_TINH,
                    GHICHU: "",      
                    LOAI_TBI: f.dt[i].LOAI_TBI,
                    LOAI_VT: f.dt[i].LOAI_VT,          
                    MA_TBI: f.dt[i].MA_TBI,
                    MA_VT: f.dt[i].MA_VT,      
                    SDCC_ID: 0,
                    SERIAL: f.dt[i].SERIAL,        
                    SOLUONG: f.dt[i].SL_XUAT,
                    SOTHANG_PB: soThangPhanBo_s,     
                    TEN_VT: f.dt[i].TEN_VT,  
                    THANG_BD: this.f_MonthYearToString(dt2),
                    THANG_KT: this.f_MonthYearToString(dt_s),
                    THIETBI_ID: f.dt[i].THIETBI_ID,
                    TIEN_CL: 0 ,
                    TIEN: parseInt(f.dt[i].TIEN_CL) + tien_sc,
                    TIEN_PB: parseInt((tien + tien_sc / sl) / (soThangPhanBo_s)),
                    TINHTRANGTBI_ID: f.dt[i].TINHTRANGTBI_ID,   
                    VATTU_ID: f.dt[i].VATTU_ID,
                  }                
                  this.dtVT_DV.push(row3)                 
                  console.log(row3 , " = row3 111111")
                }

              }
            }else{ // Mục đích khác

              var tien = parseInt(f.dt[i].TIEN_CL)
              var tienpb_goc = parseInt(f.dt[i].TIEN_PB)
              var slgoc = parseInt(f.dt[i].SOLUONG)
              var slxuat = parseInt(f.dt[i].SL_XUAT)


              // Đặt tên củ chuối theo source cũ C#
              var tempDate = new Date(this.params.ngayYeuCau.getTime())
              
              var dt1 = new Date(tempDate.setDate(1)) // MUCDICH_CCDC.THUHOI            
              console.log(this.f_MonthYearToString(dt1) , " = dt1")
              var maMD = this.getMaMucDich()
              if(maMD != this.MUCDICH_CCDC.THUHOI){
                var ngayYeuCau_date = this.params.ngayYeuCau.getDate()
                console.log(ngayYeuCau_date , " = ngayYeuCau_date")
                if(ngayYeuCau_date > 15){
                  var tempDate = new Date(this.params.ngayYeuCau.getTime())                
                  dt1.setMonth(tempDate.getMonth() + 1) 
                }    
              }
        
              var dt2 = new Date(f.dt[i].THANG_BD.toString().slice(0,4), f.dt[i].THANG_BD.toString().slice(4,6) -1, 1)  
              var soThangPhanBo = parseInt((dt1 - dt2)/ (1000 * 3600 * 24 * 30));
      
              var TIEN_PB = 0
              var TIEN = 0
              var SOTHANG_PB = 0
              if(soThangPhanBo > 0){
                if(parseInt(f.dt[i].SOTHANG_PB) - soThangPhanBo > 0 ){
                  TIEN_PB = tienpb_goc;
                  TIEN = tien - tienpb_goc * soThangPhanBo;
                  SOTHANG_PB = parseInt(f.dt[i].SOTHANG_PB) - soThangPhanBo;
                }else{
                  TIEN_PB = tienpb_goc;
                  TIEN = tien;
                  SOTHANG_PB = parseInt(f.dt[i].SOTHANG_PB); 
                }
              }else if(soThangPhanBo == 0){
                TIEN_PB = tienpb_goc;
                TIEN = tien;
                SOTHANG_PB = parseInt(f.dt[i].SOTHANG_PB) - soThangPhanBo;             
              }else{
                TIEN_PB = parseInt(f.dt[i].SL_XUAT)*parseInt(f.dt[i].DONGIA);
                TIEN = tien;
                SOTHANG_PB = 0;    
              }

              // var THANG_BD = this.f_MonthYearToString(this.params.ngayYeuCau)
              // var THANG_KT = this.f_MonthYearToString(this.params.ngayYeuCau)
              // if(maMD == this.MUCDICH_CCDC.THUHOI){
              //   TIEN_PB = tienpb_goc;            
              //   SOTHANG_PB = parseInt(f.dt[i].SOTHANG_PB);  
              // }else{
              //   var ngayYeuCau_date = this.params.ngayYeuCau.getDate()            
              //   if(ngayYeuCau_date > 15){
              //     var tempDate_THANG_BD = new Date(this.params.ngayYeuCau.getTime())
              //     tempDate_THANG_BD.setMonth(tempDate_THANG_BD.getMonth() + 1) 
              //     THANG_BD = this.f_MonthYearToString(tempDate_THANG_BD)

              //     var tempDate_THANG_KT = new Date(this.params.ngayYeuCau.getTime())
              //     tempDate_THANG_KT.setMonth(tempDate_THANG_KT.getMonth()+ parseInt(f.dt[i].SOTHANG_PB) - soThangPhanBo) 

              //     THANG_KT = this.f_MonthYearToString(tempDate_THANG_KT)
              //   }else{
              //     var tempDate_THANG_KT = new Date(this.params.ngayYeuCau.getTime())
              //     tempDate_THANG_KT.setMonth(tempDate_THANG_KT.getMonth() + parseInt(f.dt[i].SOTHANG_PB)  - soThangPhanBo - 1)

              //     THANG_KT = this.f_MonthYearToString(tempDate_THANG_KT)
              //   }
              // }


              // SỬA THEO CODE LIVE DHSX 18/03/2022
              var THANG_BD = this.f_MonthYearToString(this.params.ngayYeuCau)
              var THANG_KT = this.f_MonthYearToString(this.params.ngayYeuCau)
              if(maMD == this.MUCDICH_CCDC.THUHOI){
                  TIEN_PB = tienpb_goc;            
                  SOTHANG_PB = parseInt(f.dt[i].SOTHANG_PB);  
              }else{              
                var ngayYeuCau_date = this.params.ngayYeuCau.getDate()
                if(ngayYeuCau_date > 15){
                  var tempDate_THANG_BD = new Date(this.params.ngayYeuCau.getTime())
                  tempDate_THANG_BD.setMonth(tempDate_THANG_BD.getMonth() + 1) 
                  THANG_BD = this.f_MonthYearToString(tempDate_THANG_BD)              
                }
                
                if(parseInt(f.dt[i].SOTHANG_PB) - soThangPhanBo > 0){
                  if(ngayYeuCau_date > 15){
                    var tempDate_THANG_KT = new Date(this.params.ngayYeuCau.getTime())
                    tempDate_THANG_KT.setMonth(tempDate_THANG_KT.getMonth() + parseInt(f.dt[i].SOTHANG_PB) - soThangPhanBo )

                    THANG_KT = this.f_MonthYearToString(tempDate_THANG_KT)
                  }else{
                    var tempDate_THANG_KT = new Date(this.params.ngayYeuCau.getTime())
                    tempDate_THANG_KT.setMonth(tempDate_THANG_KT.getMonth() + parseInt(f.dt[i].SOTHANG_PB)  - soThangPhanBo - 1)

                    THANG_KT = this.f_MonthYearToString(tempDate_THANG_KT)
                  }
                }else{   
                  if(ngayYeuCau_date > 15){
                    var tempDate_THANG_KT = new Date(this.params.ngayYeuCau.getTime())
                    tempDate_THANG_KT.setMonth(tempDate_THANG_KT.getMonth() + parseInt(f.dt[i].SOTHANG_PB) ) 

                    THANG_KT = this.f_MonthYearToString(tempDate_THANG_KT)
                  }else{
                    
                    var tempDate_THANG_KT = new Date(this.params.ngayYeuCau.getTime())                  
                    tempDate_THANG_KT.setMonth(tempDate_THANG_KT.getMonth() + parseInt(f.dt[i].SOTHANG_PB) - 1)     
                    THANG_KT = this.f_MonthYearToString(tempDate_THANG_KT)
                  }
                }                
              }
              
              var row = {
                CTCC_ID: f.dt[i].CTCC_ID,
                DONGIA: f.dt[i].DONGIA,
                CHUNGTU_ID: null,
                DVI_TINH: f.dt[i].DVI_TINH,
                GHICHU: "",      
                LOAI_TBI: f.dt[i].LOAI_TBI,
                LOAI_VT: f.dt[i].LOAI_VT,          
                MA_TBI: f.dt[i].MA_TBI,
                MA_VT: f.dt[i].MA_VT,      
                SDCC_ID: f.dt[i].SDCC_ID,
                SERIAL: f.dt[i].SERIAL,        
                SOLUONG: f.dt[i].SL_XUAT,
                SOTHANG_PB: SOTHANG_PB,     
                TEN_VT: f.dt[i].TEN_VT,  
                THANG_BD: THANG_BD,
                THANG_KT: THANG_KT,
                THIETBI_ID: f.dt[i].THIETBI_ID,
                TIEN_CL: 0 ,
                TIEN: TIEN,
                TIEN_PB: TIEN_PB,
                TINHTRANGTBI_ID: f.dt[i].TINHTRANGTBI_ID,   
                VATTU_ID: f.dt[i].VATTU_ID,
              }                
              this.dtVT_DV.push(row) 
            }
          }
          this.dtVT_DV.forEach(e=>{
            e.SOTHANG_PB =  e.SOTHANG_PB == "" || e.SOTHANG_PB == null ?  0 : parseInt(e.SOTHANG_PB)
            e.SOLUONG =  e.SOLUONG == "" || e.SOLUONG == null ?  0 : parseInt(e.SOLUONG)
            e.DONGIA =  e.DONGIA == "" || e.DONGIA == null ?  0 : parseInt(e.DONGIA)
            e.TIEN_PB =  e.TIEN_PB == "" || e.TIEN_PB == null ?  0 : parseInt(e.TIEN_PB)
            e.TIEN =  e.TIEN == "" || e.TIEN == null ?  0 : parseInt(e.TIEN)
          })
          this.dataResponse.DSVatTu = this.dtVT_DV
          this.vchonct_id = 0
          this.vchon_sp = ""
          this.vchungtu_goc = this.Enabled.nhapMoi ? 0 : this.vchungtu_goc
          this.isTabTongHop = false
        }
        this.loading(false);
      }catch(e){
        this.$toast.error(e.data.message);
      }finally{
        this.loading(false);
      }
    },
    async closeChonVatTuCCDCTuNhanVien(){
      try{
        this.loading(true);
        
        var maMD = this.getMaMucDich()
        let f = this.$refs.frmChonVatTuCCDC;
       
        if (f.xacnhan) {
        this.dtVT_NV = []
        this.dtVT_DV = []
        console.log(f.dt , " = f.dt")
        for(const i in f.dt){
            if(maMD == this.MUCDICH_CCDC.SUACHUA){

              var vk = 0
              if(f.pb == "1"){ //1 tháng
                var row = {
                  CTCC_ID: 0,
                  DONGIA: f.dt[i].DONGIA,
                  CHUNGTU_ID: null,
                  DVI_TINH: f.dt[i].DVI_TINH,
                  GHICHU: "",      
                  LOAI_TBI: "",
                  LOAI_VT: "",          
                  MA_TBI: "",
                  MA_VT: f.dt[i].MA_VT,      
                  SDCC_ID: 0,
                  SERIAL: "",        
                  SOLUONG: f.dt[i].SL_XUAT,
                  SOTHANG_PB: 0,     
                  TEN_VT: f.dt[i].TEN_VT,  
                  THANG_BD: "",
                  THANG_KT: "",
                  THIETBI_ID: f.dt[i].THIETBI_ID,
                  TIEN_CL: 0 ,
                  TIEN: 0,
                  TIEN_PB: 0,
                  TINHTRANGTBI_ID: 0,   
                  VATTU_ID: f.dt[i].VATTU_ID,
                }

                var tien = parseInt(f.dt[i].TIEN_CL)
                var tienpb_goc = parseInt(f.dt[i].TIEN_PB)
                var slgoc = parseInt(f.dt[i].SOLUONG)
                var slxuat = parseInt(f.dt[i].SL_XUAT)
              
                var tempDate = new Date(this.params.ngayYeuCau)
                
                tempDate.setMonth(tempDate.getMonth() - 1) 
                
                // Đặt tên củ chuối theo source cũ C#
                var dt1 = new Date(tempDate.setDate(1))

                // Đặt tên củ chuối theo source cũ C#
                var a = new Date(f.dt[i].THANG_BD.toString().slice(0,4), f.dt[i].THANG_BD.toString().slice(4,6) -1, 1)              
                
                // Lấy số tháng chênh lệch
                var soThangPhanBo = parseInt((dt1 - a)/ (1000 * 3600 * 24 * 30) + 1);
              
                if(soThangPhanBo > 0){

                  vk = 2 
              
                  if(parseInt(f.dt[i].SOTHANG_PB) - soThangPhanBo > 0){
                    row.TIEN_PB = tienpb_goc                
                    row.SOTHANG_PB = soThangPhanBo
                    row.LOAI_TBI = f.dt[i].LOAI_TBI
                    row.LOAI_VT = f.dt[i].LOAI_VT
                    row.TIEN = tien
                    row.SERIAL = f.dt[i].SERIAL
                    row.MA_TBI = f.dt[i].MA_TBI
                    row.SDCC_ID = f.dt[i].SDCC_ID
                    row.CTCC_ID = f.dt[i].CTCC_ID
                    row.THANG_BD = this.f_MonthYearToString(a)
                    row.THANG_KT = this.f_MonthYearToString(dt1)
                    row.TINHTRANGTBI_ID = f.dt[i].TINHTRANGTBI_ID
                    this.dtVT_NV.push(row)
                
                  }else{
                    row.TIEN_PB = tienpb_goc
                    row.TIEN = tien
                    row.SOTHANG_PB = 24
                    row.LOAI_TBI = f.dt[i].LOAI_TBI
                    row.LOAI_VT = f.dt[i].LOAI_VT                  
                    row.SERIAL = f.dt[i].SERIAL
                    row.MA_TBI = f.dt[i].MA_TBI
                    row.SDCC_ID = f.dt[i].SDCC_ID
                    row.CTCC_ID = f.dt[i].CTCC_ID
                    row.THANG_BD = this.f_MonthYearToString(a)                 
                    row.THANG_KT = this.f_MonthYearToString(dt1)
                  }
                }

                if(vk == 2){
                  var tien_sc = parseInt(f.dt[i].TIEN_SC)
                  var row2 = {
                    CTCC_ID: f.dt[i].CTCC_ID,
                    DONGIA: f.dt[i].DONGIA,
                    CHUNGTU_ID: null,
                    DVI_TINH: f.dt[i].DVI_TINH,
                    GHICHU: "",      
                    LOAI_TBI: f.dt[i].LOAI_TBI,
                    LOAI_VT: f.dt[i].LOAI_VT,          
                    MA_TBI: f.dt[i].MA_TBI,
                    MA_VT: f.dt[i].MA_VT,      
                    SDCC_ID: parseInt(f.dt[i].SOTHANG_PB) - soThangPhanBo > 0 ? 0 : f.dt[i].SDCC_ID,
                    SERIAL: f.dt[i].SERIAL,        
                    SOLUONG: f.dt[i].SL_XUAT,
                    SOTHANG_PB: 1,     
                    TEN_VT: f.dt[i].TEN_VT,  
                    THANG_BD: this.f_MonthYearToString(this.params.ngayYeuCau),
                    THANG_KT: this.f_MonthYearToString(this.params.ngayYeuCau),
                    THIETBI_ID: f.dt[i].THIETBI_ID,
                    TIEN_CL: 0 ,
                    TIEN:  parseInt(tien - tienpb_goc * soThangPhanBo),
                    TIEN_PB: parseInt(parseInt(f.dt[i].TIEN_PB) + tien_sc/slxuat),
                    TINHTRANGTBI_ID: f.dt[i].TINHTRANGTBI_ID,   
                    VATTU_ID: f.dt[i].VATTU_ID,
                  }
                  this.dtVT_NV.push(row2)          
                  var tempDate = new Date(this.params.ngayYeuCau)
                
                  tempDate.setMonth(tempDate.getMonth())
                  
                  // Đặt tên củ chuối theo source cũ C#
                  var dt_t = new Date(tempDate.setDate(1))          
                  // Đặt tên củ chuối theo source cũ C#
                  var dt_s = new Date(f.dt[i].THANG_KT.toString().slice(0,4), f.dt[i].THANG_KT.toString().slice(4,6) -1, 1)              
                  
                  // Lấy số tháng chênh lệch
                  var soThangPhanBo_s = parseInt((dt_s - dt_t)/ (1000 * 3600 * 24 * 30) + 1);               
                  if(soThangPhanBo_s != 0){

                    var slg = parseInt(f.dt[i].SOLUONG)
                    var sl = parseInt(f.dt[i].SL_XUAT)
                
                    var tempDate = new Date(dt_t)
                    tempDate.setMonth((tempDate.getMonth()) + parseInt(f.dt[i].SOTHANG_PB) - 1) 
                    var THANG_KT = new Date(tempDate)                
                    var row3 = {
                      CTCC_ID: f.dt[i].CTCC_ID,
                      DONGIA: f.dt[i].DONGIA,
                      CHUNGTU_ID: null,
                      DVI_TINH: f.dt[i].DVI_TINH,
                      GHICHU: "",      
                      LOAI_TBI: f.dt[i].LOAI_TBI,
                      LOAI_VT: f.dt[i].LOAI_VT,          
                      MA_TBI: f.dt[i].MA_TBI,
                      MA_VT: f.dt[i].MA_VT,      
                      SDCC_ID: 0,
                      SERIAL: f.dt[i].SERIAL,        
                      SOLUONG: f.dt[i].SL_XUAT,
                      SOTHANG_PB: soThangPhanBo_s > 0 ? soThangPhanBo_s : parseInt(f.dt[i].SOTHANG_PB) - 1,     
                      TEN_VT: f.dt[i].TEN_VT,  
                      THANG_BD: this.f_MonthYearToString(dt_t),
                      THANG_KT: soThangPhanBo_s > 0 ? this.f_MonthYearToString(dt_s) :this.f_MonthYearToString(THANG_KT),
                      THIETBI_ID: f.dt[i].THIETBI_ID,
                      TIEN_CL: 0 ,
                      TIEN: parseInt(tien + tien_sc / sl - tienpb_goc * soThangPhanBo - parseInt(f.dt[i].TIEN_PB) - tien_sc / slxuat),
                      TIEN_PB: parseInt(f.dt[i].TIEN_PB),
                      TINHTRANGTBI_ID: f.dt[i].TINHTRANGTBI_ID,   
                      VATTU_ID: f.dt[i].VATTU_ID,
                    }                
                    this.dtVT_NV.push(row3)                
                  }

                }else{ // vk = 3 theo C#
                  var tien_sc = parseInt(f.dt[i].TIEN_SC)
                  var row2 = {
                    CTCC_ID: f.dt[i].CTCC_ID,
                    DONGIA: f.dt[i].DONGIA,
                    CHUNGTU_ID: null,
                    DVI_TINH: f.dt[i].DVI_TINH,
                    GHICHU: "",      
                    LOAI_TBI: f.dt[i].LOAI_TBI,
                    LOAI_VT: f.dt[i].LOAI_VT,          
                    MA_TBI: f.dt[i].MA_TBI,
                    MA_VT: f.dt[i].MA_VT,      
                    SDCC_ID: 0,
                    SERIAL: f.dt[i].SERIAL,        
                    SOLUONG: f.dt[i].SL_XUAT,
                    SOTHANG_PB: 1,     
                    TEN_VT: f.dt[i].TEN_VT,  
                    THANG_BD: this.f_MonthYearToString(this.params.ngayYeuCau),
                    THANG_KT: this.f_MonthYearToString(this.params.ngayYeuCau),
                    THIETBI_ID: f.dt[i].THIETBI_ID,
                    TIEN_CL: 0 ,
                    TIEN: tien,
                    TIEN_PB: parseInt(parseInt(f.dt[i].TIEN_PB) + tien_sc/slxuat),
                    TINHTRANGTBI_ID: f.dt[i].TINHTRANGTBI_ID,   
                    VATTU_ID: f.dt[i].VATTU_ID,
                  }                
                  this.dtVT_NV.push(row2)               
                  var slg = parseInt(f.dt[i].SOLUONG)
                  var sl = parseInt(f.dt[i].SL_XUAT)

                  var tempDate = new Date(this.params.ngayYeuCau)
                  tempDate.setMonth(tempDate.getMonth() + 1)
                  // Đặt tên củ chuối theo source cũ C#
                  var dt_t = new Date(tempDate.setDate(1))

                  // Đặt tên củ chuối theo source cũ C#          
                  var dt_s = new Date(f.dt[i].THANG_KT.toString().slice(0,4), f.dt[i].THANG_KT.toString().slice(4,6) -1, 1)              
                  
                  // Lấy số tháng chênh lệch
                  var soThangPhanBo_s = parseInt((dt_s - dt_t)/ (1000 * 3600 * 24 * 30) + 1);

                  var row3 = {
                    CTCC_ID: f.dt[i].CTCC_ID,
                    DONGIA: f.dt[i].DONGIA,
                    CHUNGTU_ID: null,
                    DVI_TINH: f.dt[i].DVI_TINH,
                    GHICHU: "",      
                    LOAI_TBI: f.dt[i].LOAI_TBI,
                    LOAI_VT: f.dt[i].LOAI_VT,          
                    MA_TBI: f.dt[i].MA_TBI,
                    MA_VT: f.dt[i].MA_VT,      
                    SDCC_ID: 0,
                    SERIAL: f.dt[i].SERIAL,        
                    SOLUONG: f.dt[i].SL_XUAT,
                    SOTHANG_PB: soThangPhanBo_s,     
                    TEN_VT: f.dt[i].TEN_VT,  
                    THANG_BD: this.f_MonthYearToString(dt_t),
                    THANG_KT: this.f_MonthYearToString(dt_s),
                    THIETBI_ID: f.dt[i].THIETBI_ID,
                    TIEN_CL: 0 ,
                    TIEN: tien,
                    TIEN_PB: parseInt(f.dt[i].TIEN_PB),
                    TINHTRANGTBI_ID: f.dt[i].TINHTRANGTBI_ID,   
                    VATTU_ID: f.dt[i].VATTU_ID,
                  }                
                  this.dtVT_NV.push(row3)         
                }
        
              }else{ // f.pb == "2" Phân bổ đều

                var row = {
                  CTCC_ID: 0,
                  DONGIA: f.dt[i].DONGIA,
                  CHUNGTU_ID: null,
                  DVI_TINH: f.dt[i].DVI_TINH,
                  GHICHU: "",      
                  LOAI_TBI: "",
                  LOAI_VT: "",          
                  MA_TBI: "",
                  MA_VT: f.dt[i].MA_VT,      
                  SDCC_ID: 0,
                  SERIAL: "",        
                  SOLUONG: f.dt[i].SL_XUAT,
                  SOTHANG_PB: 0,     
                  TEN_VT: f.dt[i].TEN_VT,  
                  THANG_BD: "",
                  THANG_KT: "",
                  THIETBI_ID: f.dt[i].THIETBI_ID,
                  TIEN_CL: 0 ,
                  TIEN: 0,
                  TIEN_PB: 0,
                  TINHTRANGTBI_ID: 0,   
                  VATTU_ID: f.dt[i].VATTU_ID,
                }

                var tien = parseInt(f.dt[i].TIEN_CL)
                var tienpb_goc = parseInt(f.dt[i].TIEN_PB)
                var slgoc = parseInt(f.dt[i].SOLUONG)
                var slxuat = parseInt(f.dt[i].SL_XUAT)
              
                var tempDate = new Date(this.params.ngayYeuCau)
                tempDate.setMonth(tempDate.getMonth() - 1) 
                
                // Đặt tên củ chuối theo source cũ C#
                var dt1 = new Date(tempDate.setDate(1))

                // Đặt tên củ chuối theo source cũ C#
                var dt2 = new Date(f.dt[i].THANG_BD.toString().slice(0,4), f.dt[i].THANG_BD.toString().slice(4,6) -1, 1)              
                
                // Lấy số tháng chênh lệch
                var soThangPhanBo = parseInt((dt1 - dt2)/ (1000 * 3600 * 24 * 30) + 1);
                
                if(soThangPhanBo > 0){

                  vk = 2 
              
                  if(parseInt(f.dt[i].SOTHANG_PB) - soThangPhanBo > 0){
                    row.TIEN_PB = tienpb_goc                
                    row.SOTHANG_PB = soThangPhanBo
                    row.LOAI_TBI = f.dt[i].LOAI_TBI
                    row.LOAI_VT = f.dt[i].LOAI_VT
                    row.TIEN = tien
                    row.SERIAL = f.dt[i].SERIAL
                    row.MA_TBI = f.dt[i].MA_TBI
                    row.SDCC_ID = f.dt[i].SDCC_ID
                    row.CTCC_ID = f.dt[i].CTCC_ID
                    row.THANG_BD = this.f_MonthYearToString(dt2)
                    row.THANG_KT = this.f_MonthYearToString(dt1)
                    row.TINHTRANGTBI_ID = f.dt[i].TINHTRANGTBI_ID
                    this.dtVT_NV.push(row)
                    console.log(row , " = row 111111")
                  }else{
                    row.TIEN_PB = tienpb_goc
                    row.SOTHANG_PB = 24
                    row.LOAI_TBI = f.dt[i].LOAI_TBI
                    row.LOAI_VT = f.dt[i].LOAI_VT
                    row.TIEN = tien
                    row.SERIAL = f.dt[i].SERIAL
                    row.MA_TBI = f.dt[i].MA_TBI
                    row.SDCC_ID = f.dt[i].SDCC_ID
                    row.CTCC_ID = f.dt[i].CTCC_ID
                    row.THANG_BD = this.f_MonthYearToString(this.params.ngayYeuCau)   

                    var tempDate = new Date(this.params.ngayYeuCau)
                    tempDate.setMonth(tempDate.getMonth() + 24 - 1)
                    
                    // Đặt tên củ chuối theo source cũ C#
                    var THANG_KT = new Date(tempDate)
                    row.THANG_KT = this.f_MonthYearToString(THANG_KT)
                    row.TINHTRANGTBI_ID = f.dt[i].TINHTRANGTBI_ID
                  }
                }

                if(vk == 2){
                  
                  var tien_sc = parseInt(f.dt[i].TIEN_SC)
                  var slg = parseInt(f.dt[i].SOLUONG)
                  var sl = parseInt(f.dt[i].SL_XUAT)

                  var tempDate = new Date(this.params.ngayYeuCau)
                  tempDate.setMonth(tempDate.getMonth())  
                  
                  // Đặt tên củ chuối theo source cũ C#
                  var dt_t = new Date(tempDate.setDate(1))
                  console.log(dt_t , " = dt_t")
                  console.log(this.params.ngayYeuCau , " = this.params.ngayYeuCau")
                  // Đặt tên củ chuối theo source cũ C#
                  var dt_s = new Date(f.dt[i].THANG_KT.toString().slice(0,4), f.dt[i].THANG_KT.toString().slice(4,6) -1, 1)              
                  
                  // Lấy số tháng chênh lệch
                  var soThangPhanBo_s = parseInt((dt_s - dt_t)/ (1000 * 3600 * 24 * 30) + 1);
                  
                  var tiensc_pc = soThangPhanBo_s > 0 ? parseInt(tien_sc / sl / soThangPhanBo_s) : parseInt(tien_sc / sl / 24)
                  var row2_TIEN = soThangPhanBo_s > 0 ? parseInt(f.dt[i].TIEN_PB) +  tiensc_pc :  tiensc_pc   
                  

                  var tempDate = new Date(this.params.ngayYeuCau)
                  tempDate.setMonth(tempDate.getMonth()  + 24 - 1) 
                  
                  // Đặt tên củ chuối theo source cũ C#
                  var THANG_KT = new Date(tempDate)               
                  var row2 = {
                    CTCC_ID: f.dt[i].CTCC_ID,
                    DONGIA: f.dt[i].DONGIA,
                    CHUNGTU_ID: null,
                    DVI_TINH: f.dt[i].DVI_TINH,
                    GHICHU: "",      
                    LOAI_TBI: f.dt[i].LOAI_TBI,
                    LOAI_VT: f.dt[i].LOAI_VT,          
                    MA_TBI: f.dt[i].MA_TBI,
                    MA_VT: f.dt[i].MA_VT,      
                    SDCC_ID: parseInt(f.dt[i].SOTHANG_PB) - soThangPhanBo > 0 ? 0 : f.dt[i].SDCC_ID,
                    SERIAL: f.dt[i].SERIAL,        
                    SOLUONG: f.dt[i].SL_XUAT,
                    SOTHANG_PB: soThangPhanBo_s > 0 ?  soThangPhanBo_s : 24,     
                    TEN_VT: f.dt[i].TEN_VT,  
                    THANG_BD: this.f_MonthYearToString(dt_t),
                    THANG_KT: soThangPhanBo_s > 0 ? this.f_MonthYearToString(dt_s) : this.f_MonthYearToString(THANG_KT),
                    THIETBI_ID: f.dt[i].THIETBI_ID,
                    TIEN_CL: 0 ,
                    TIEN: row2_TIEN,
                    TIEN_PB: parseInt(f.dt[i].TIEN_PB) + tiensc_pc,
                    TINHTRANGTBI_ID: f.dt[i].TINHTRANGTBI_ID,   
                    VATTU_ID: f.dt[i].VATTU_ID,
                  }
                  this.dtVT_NV.push(row2) 
                  console.log(row2 , " = row2 111111")
                  
                }else{ // vk = 3 theo C#
                  var tien_sc = parseInt(f.dt[i].TIEN_SC)
                  var slg = parseInt(f.dt[i].SOLUONG)
                  var sl = parseInt(f.dt[i].SL_XUAT)

                  var dt_t = new Date(f.dt[i].THANG_BD.toString().slice(0,4), f.dt[i].THANG_BD.toString().slice(4,6) -1, 1)  

                  var dt_s = new Date(f.dt[i].THANG_KT.toString().slice(0,4), f.dt[i].THANG_KT.toString().slice(4,6) -1, 1)  

                  var soThangPhanBo_s = parseInt((dt_s - dt_t)/ (1000 * 3600 * 24 * 30) + 1);

                  var row3 = {
                    CTCC_ID: f.dt[i].CTCC_ID,
                    DONGIA: f.dt[i].DONGIA,
                    CHUNGTU_ID: null,
                    DVI_TINH: f.dt[i].DVI_TINH,
                    GHICHU: "",      
                    LOAI_TBI: f.dt[i].LOAI_TBI,
                    LOAI_VT: f.dt[i].LOAI_VT,          
                    MA_TBI: f.dt[i].MA_TBI,
                    MA_VT: f.dt[i].MA_VT,      
                    SDCC_ID: 0,
                    SERIAL: f.dt[i].SERIAL,        
                    SOLUONG: f.dt[i].SL_XUAT,
                    SOTHANG_PB: soThangPhanBo_s,     
                    TEN_VT: f.dt[i].TEN_VT,  
                    THANG_BD: this.f_MonthYearToString(dt2),
                    THANG_KT: this.f_MonthYearToString(dt_s),
                    THIETBI_ID: f.dt[i].THIETBI_ID,
                    TIEN_CL: 0 ,
                    TIEN: parseInt(f.dt[i].TIEN_CL) + tien_sc,
                    TIEN_PB: parseInt((tien + tien_sc / sl) / (soThangPhanBo_s)),
                    TINHTRANGTBI_ID: f.dt[i].TINHTRANGTBI_ID,   
                    VATTU_ID: f.dt[i].VATTU_ID,
                  }                
                  this.dtVT_NV.push(row3)                 
                  console.log(row3 , " = row3 111111")
                }

              }
            }else{ // Mục đích khác

              var tien = parseInt(f.dt[i].TIEN_CL)
              var tienpb_goc = parseInt(f.dt[i].TIEN_PB)
              var slgoc = parseInt(f.dt[i].SOLUONG)
              var slxuat = parseInt(f.dt[i].SL_XUAT)


              // Đặt tên củ chuối theo source cũ C#
              var tempDate = new Date(this.params.ngayYeuCau.getTime())
              
              var dt1 = new Date(tempDate.setDate(1)) // MUCDICH_CCDC.THUHOI
              
              var maMD = this.getMaMucDich()
              if(maMD != this.MUCDICH_CCDC.THUHOI){
                  var ngayYeuCau_date = this.params.ngayYeuCau.getDate()
                  if(ngayYeuCau_date > 15){
                    var tempDate = new Date(this.params.ngayYeuCau.getTime())
                    dt1.setMonth(tempDate.getMonth() + 1)
                  }
              }
          
              var dt2 = new Date(f.dt[i].THANG_BD.toString().slice(0,4), f.dt[i].THANG_BD.toString().slice(4,6) -1, 1)         
              var soThangPhanBo = parseInt((dt1 - dt2)/ (1000 * 3600 * 24 * 30));         
              var TIEN_PB = 0
              var TIEN = 0
              var SOTHANG_PB = 0
             
              if(soThangPhanBo > 0){
                  if(parseInt(f.dt[i].SOTHANG_PB) - soThangPhanBo > 0 ){
                    TIEN_PB = tienpb_goc;
                    
                    //TIEN = tien - tienpb_goc * soThangPhanBo;
                    TIEN = tienpb_goc * (parseInt(f.dt[i].SOTHANG_PB) - soThangPhanBo) * slxuat // Chỉnh sửa mới 10/07
                    SOTHANG_PB = parseInt(f.dt[i].SOTHANG_PB) - soThangPhanBo;
                  }else{
                    TIEN_PB = tienpb_goc;
                    TIEN = tien;
                    SOTHANG_PB = parseInt(f.dt[i].SOTHANG_PB); 
                  }
              }else if(soThangPhanBo == 0){
                  TIEN_PB = tienpb_goc;
                  TIEN = tien;
                  SOTHANG_PB = parseInt(f.dt[i].SOTHANG_PB) - soThangPhanBo;             
              }else{
                  TIEN_PB = parseInt(f.dt[i].SL_XUAT)*parseInt(f.dt[i].DONGIA);
                  TIEN = tien;
                  SOTHANG_PB = 0;    
              }

              var THANG_BD = this.f_MonthYearToString(this.params.ngayYeuCau)
              var THANG_KT = this.f_MonthYearToString(this.params.ngayYeuCau)
              if(maMD == this.MUCDICH_CCDC.THUHOI){
                  TIEN_PB = tienpb_goc;            
                  SOTHANG_PB = parseInt(f.dt[i].SOTHANG_PB);  
              }else{              
                var ngayYeuCau_date = this.params.ngayYeuCau.getDate()
                if(ngayYeuCau_date > 15){
                  var tempDate_THANG_BD = new Date(this.params.ngayYeuCau.getTime())
                  tempDate_THANG_BD.setMonth(tempDate_THANG_BD.getMonth() + 1) 
                  THANG_BD = this.f_MonthYearToString(tempDate_THANG_BD)
                
                }
                
                if(parseInt(f.dt[i].SOTHANG_PB) - soThangPhanBo > 0){
                  if(ngayYeuCau_date > 15){
                    var tempDate_THANG_KT = new Date(this.params.ngayYeuCau.getTime())
                    tempDate_THANG_KT.setMonth(tempDate_THANG_KT.getMonth() + parseInt(f.dt[i].SOTHANG_PB) - soThangPhanBo )

                    THANG_KT = this.f_MonthYearToString(tempDate_THANG_KT)
                  }else{
                    var tempDate_THANG_KT = new Date(this.params.ngayYeuCau.getTime())
                    tempDate_THANG_KT.setMonth(tempDate_THANG_KT.getMonth() + parseInt(f.dt[i].SOTHANG_PB)  - soThangPhanBo - 1)

                    THANG_KT = this.f_MonthYearToString(tempDate_THANG_KT)
                  }
                }else{   
                  if(ngayYeuCau_date > 15){
                    var tempDate_THANG_KT = new Date(this.params.ngayYeuCau.getTime())
                    tempDate_THANG_KT.setMonth(tempDate_THANG_KT.getMonth() + parseInt(f.dt[i].SOTHANG_PB) ) 

                    THANG_KT = this.f_MonthYearToString(tempDate_THANG_KT)
                  }else{
                    
                    var tempDate_THANG_KT = new Date(this.params.ngayYeuCau.getTime())                  
                    tempDate_THANG_KT.setMonth(tempDate_THANG_KT.getMonth() + parseInt(f.dt[i].SOTHANG_PB) - 1)     
                    THANG_KT = this.f_MonthYearToString(tempDate_THANG_KT)
                  }
                }                
              }

              
              var row = {
                  CTCC_ID: f.dt[i].CTCC_ID,
                  DONGIA: f.dt[i].DONGIA,
                  CHUNGTU_ID: null,
                  DVI_TINH: f.dt[i].DVI_TINH,
                  GHICHU: "",      
                  LOAI_TBI: f.dt[i].LOAI_TBI,
                  LOAI_VT: f.dt[i].LOAI_VT,          
                  MA_TBI: f.dt[i].MA_TBI,
                  MA_VT: f.dt[i].MA_VT,      
                  SDCC_ID: f.dt[i].SDCC_ID,
                  SERIAL: f.dt[i].SERIAL,        
                  SOLUONG: f.dt[i].SL_XUAT,
                  SOTHANG_PB: SOTHANG_PB,     
                  TEN_VT: f.dt[i].TEN_VT,  
                  THANG_BD: THANG_BD,
                  THANG_KT: THANG_KT,
                  THIETBI_ID: f.dt[i].THIETBI_ID,
                  TIEN_CL: 0 ,
                  TIEN: TIEN,
                  TIEN_PB: TIEN_PB,
                  TINHTRANGTBI_ID: f.dt[i].TINHTRANGTBI_ID,   
                  VATTU_ID: f.dt[i].VATTU_ID,
              }                
              this.dtVT_NV.push(row) 
            }
        }
        this.dtVT_NV.forEach(e=>{
            e.SOTHANG_PB =  e.SOTHANG_PB == "" || e.SOTHANG_PB == null ?  0 : parseInt(e.SOTHANG_PB)
            e.SOLUONG =  e.SOLUONG == "" || e.SOLUONG == null ?  0 : parseInt(e.SOLUONG)
            e.DONGIA =  e.DONGIA == "" || e.DONGIA == null ?  0 : parseInt(e.DONGIA)
            e.TIEN_PB =  e.TIEN_PB == "" || e.TIEN_PB == null ?  0 : parseInt(e.TIEN_PB)
            e.TIEN =  e.TIEN == "" || e.TIEN == null ?  0 : parseInt(e.TIEN)
        })
        this.dataResponse.DSVatTu = this.dtVT_NV
        console.log(this.dtVT_NV , " = this.dtVT_NV")
        this.vchonct_id = 0
        this.vchon_sp = ""
        this.vchungtu_goc = this.Enabled.nhapMoi ? 0 : this.vchungtu_goc
        this.isTabTongHop = false
        }        
        this.loading(false);    
      }catch(e){
        this.$toast.error(e.data.message);
      }finally{
        this.loading(false);
      }    
    },
    clickTabTongHop(){
      this.isTabTongHop = true
    },
    clickTabVatTu(){
      this.isTabTongHop = false
    }
  },
  
  mounted: async function () {
    document.addEventListener('click', event => {
      if (typeof (this.$refs["title-tu-don-vi"]) == 'undefined') {
          return
      }
      if (this.$refs["title-tu-don-vi"].contains(event.target)) {
          this.showTuDonVi = this.showTuDonVi == 'none' ? 'block' : 'none'
      } else if (this.$refs["table-tu-don-vi"].contains(event.target)) {
          if (event.target.tagName.toLowerCase() == 'td') {
              this.showTuDonVi = 'none'
          } else {
              this.showTuDonVi = 'block'
          }
      } else {
          this.showTuDonVi = 'none'
      }

      if (this.showTuDonVi == 'block') {
          this.popper = new Popper(this.$refs["title-tu-don-vi"], this.$refs["table-tu-don-vi"], {
              placement: 'bottom-start',
              modifiers: [{
                  name: 'offset',
                  options: {
                      offset: [0, 50]
                  },
              }]
          })
      } else {
          if (this.popper) {
              this.popper.destroy()
          }
          this.popper = null
      }
    })

    document.addEventListener('click', event => {
      if (typeof (this.$refs["title-den-don-vi"]) == 'undefined') {
          return
      }
      if (this.$refs["title-den-don-vi"].contains(event.target)) {
          this.showDenDonVi = this.showDenDonVi == 'none' ? 'block' : 'none'
      } else if (this.$refs["table-den-don-vi"].contains(event.target)) {
          if (event.target.tagName.toLowerCase() == 'td') {
              this.showDenDonVi = 'none'
          } else {
              this.showDenDonVi = 'block'
          }
      } else {
          this.showDenDonVi = 'none'
      }

      if (this.showDenDonVi == 'block') {
          this.popper = new Popper(this.$refs["title-den-don-vi"], this.$refs["table-den-don-vi"], {
              placement: 'bottom-start',
              modifiers: [{
                  name: 'offset',
                  options: {
                      offset: [0, 50]
                  },
              }]
          })
      } else {
          if (this.popper) {
              this.popper.destroy()
          }
          this.popper = null
      }
    })

  },
  created: async function () {
    try{
      this.loading(true)
      EventBus.$on('onDeleteRow', this.onDeleteRowHandler);
      EventBus.$on('openFrmGanThuocTinh', this.openFrmGanThuocTinh);
      await this.NAP_COMBO()
      this.CLEAR()
      await this.onClickLayTT()
      
      this.loading(false)
    }catch(e){
      this.$toast.error(e.data.message);
    }finally{
      this.loading(false);
    }
  },
  destroyed () {      
    EventBus.$off('onDeleteRow', this.onDeleteRowHandler);    
    EventBus.$off('openFrmGanThuocTinh', this.openFrmGanThuocTinh);
     
  },
  provide: {
    grid: [ Freeze, Edit,Group, Page,Filter,Sort,Resize ,Aggregate,Toolbar]
  },
};
</script>
