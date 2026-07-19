<template src='./TraCuuDonHangConHan.html'></template>
<style scoped src='./TraCuuDonHangConHan.scss'></style>
<script>
import { mapActions, mapState, mapGetters, mapMutations } from "vuex";
import breadcrumb from "@/components/breadcrumb";
import {
  getterName,
  actionName,
  statePropertyName,
  mutationName,
} from "../store/tracuudonhangconhan";
import moment from "moment";
import Vue from "vue";
import {
  GridPlugin,
  Group,
  Page,
  Filter,
  Sort,
  Resize,
} from "@syncfusion/ej2-vue-grids";
import frmChiaCuonCap from "../frmChiaCuonCap/frmChiaCuonCap.vue";
import { Query,Predicate } from '@syncfusion/ej2-data';

Vue.use(GridPlugin);

export default {
  components: { frmChiaCuonCap,breadcrumb },
  name: "TraCuuDonHangConHan",

  data() {
    return {
      header: {
        title: "ĐƠN HÀNG ĐÃ NGHIỆM THU",

        list: [
          {
            name: "Quản lí vật tư",
            link: { name: "Ui.buttons" },
          },
          {
            name: "Đơn hàng đã nghiệm thu",
            link: { name: "Ui.buttons" },
          },   
        ],
      },
      
      gridVatTu: [
        {fieldName: 'MA_VT', headerText: 'Mã vật tư', allowFiltering: true},
        {fieldName: 'TEN_VT', headerText: 'Tên vật tư', allowFiltering: true},
        {fieldName: 'DVI_TINH', headerText: 'ĐVT', allowFiltering: true},
        {fieldName: 'SL_NT', headerText: 'Số lượng', allowFiltering: true},
        {fieldName: 'SL_KD', headerText: 'SL KD', allowFiltering: true},
        {fieldName: 'DONGIA', headerText: 'Đơn giá', allowFiltering: true},
        {fieldName: 'TIEN', headerText: 'Thành tiền', allowFiltering: true},
        {fieldName: 'LOAI_TBI', headerText: 'Loại TB', allowFiltering: true},   
        {fieldName: '', headerText: 'SL xuất', allowFiltering: true},              
      ],

      params: {
       tuNgay: new Date("2017/02/05"),
       denNgay: new Date(),
       donViId:""
      },
     
      thongTinNguoiDung: {     
        donViId:  this.$root.token.getDonViID(),
        nhanVienId : this.$root.token.getNhanVienID(),
        phanVungId: this.$root.token.getPhanVungID()
      },
      
      dataResponse:{
        DSDonVi: [],
        DSDonHang: [],
        DSVatTuChiTiet: []
      },

      dateConfig: {
        default: new Date(),
        formatDay: "dd/MM/yyyy",
        waterMark: "Chọn ngày",
      },
     
      filterOptions: {
        enableCaseSensitivity: false,
        mode: "Immediate",
        ignoreAccent: true,
      },
      taskParent : "",
      dt: [],
      XacNhan: false,
      ma_hd: "",
      ngay_ky: "",
      ngay_nhap: "",
      so_hd: "",
      vdonhang_id:'',
      kho_id: 0,
      quyen: false,
      is_cc: false,
      rowFocus : {},
      filterDonVi: function(e) {
        var query = new Query();
        let predicate;  
        
        if (e.text !== ''){
          predicate  = new Predicate('TEN_DV', 'contains', e.text.trim(), true);              
          query.where(predicate)
        }
        e.updateData(this.dataSource, query);
      },
    };
  },

  computed: {
    ...mapState("tracuudonhangconhan", statePropertyName),
    ...mapGetters("tracuudonhangconhan", getterName),
  },
  methods: {
    ...mapActions("tracuudonhangconhan", actionName),
    ...mapMutations("tracuudonhangconhan", mutationName),

    f_DateToString: function (value, format = "DD/MM/YYYY") {
      return moment(value).format(format);
    },
    f_StringToDate(value,format='DD/MM/YYYY'){
      return moment(value,format).toDate();
    },

    async loadDataDonHang(){
      
      this.dataResponse.DSDonVi = await this.getDSDonVi(this.thongTinNguoiDung.nhanVienId)
     
      if(this.dataResponse.DSDonVi.length > 0 ){
        this.params.donViId = this.dataResponse.DSDonVi[0].DONVI_ID
      }
    
      await this.LAY_DS_DONHANG()
      
      console.log(this.thongTinNguoiDung.phanVungId , " = phanVungId")
      
    },
    async LAY_DS_DONHANG(){
    
      if(this.params.donViId == ""){
        return
      }
  
      var DSDonHang = await this.getDSDonHang({
        donViId: this.params.donViId,
        tuNgay: this.f_DateToString(this.params.tuNgay),
        denNgay: this.f_DateToString(this.params.denNgay)
      })
      DSDonHang.forEach(e=>{
        e.NGAY_NH = e.NGAY_NH.replaceAll("-","/").slice(0,10)
        e.NGAY_NK = e.NGAY_NK.replaceAll("-","/")
        e.NGAY_NT = e.NGAY_NT.replaceAll("-","/")
      })
      this.dataResponse.DSDonHang = DSDonHang
      console.log(this.dataResponse.DSDonHang , " = DSDonHang")      
    },

    async selectDonHang(data) {      
      if (data){
        this.rowFocus = data
        this.vdonhang_id = parseInt(data.DONHANG_ID) || 0
        this.so_hd = data.SO_HD || ''
        this.ma_hd = data.MA_HD || ''
        this.ngay_ky = data.NGAY_KY && this.f_DateToString( this.f_StringToDate(data.NGAY_KY,'DD-HH-YYYY hh:mm:ss'),'DD/MM/YYYY')  || ''
        this.ngay_nhap = data.NGAY_NK && this.f_DateToString( this.f_StringToDate(data.NGAY_NK,'DD-HH-YYYY hh:mm:ss'),'DD/MM/YYYY') || ''
        this.kho_id = parseInt(data.KHO_ID) || ''
      }

      var DSVatTuChiTiet = await this.getDSVatTuDonHang(data.DONHANG_ID)
      
      console.log(DSVatTuChiTiet , " = DSVatTuChiTiet")
      if(DSVatTuChiTiet.length >0){
        DSVatTuChiTiet.forEach(e=>{
          e.SL_NT =  e.SL_NT == "" || e.SL_NT == null ?  0 : parseInt(e.SL_NT)   
          e.SL_KD =  e.SL_KD == "" || e.SL_KD == null ?  0 : parseInt(e.SL_KD)   
          e.DONGIA =  e.DONGIA == "" || e.DONGIA == null ?  0 : parseInt(e.DONGIA)   
          e.TIEN =  e.TIEN == "" || e.TIEN == null ?  0 : parseInt(e.TIEN)   
        })
        
        if(DSVatTuChiTiet[0]["SL_XUAT"] !== undefined ){
          this.gridVatTu = [
            {fieldName: 'MA_VT', headerText: 'Mã vật tư', allowFiltering: true},
            {fieldName: 'TEN_VT', headerText: 'Tên vật tư', allowFiltering: true},
            {fieldName: 'DVI_TINH', headerText: 'ĐVT', allowFiltering: true},
            {fieldName: 'SL_NT', headerText: 'Số lượng', allowFiltering: true ,type:'number', format:'N3'},
            {fieldName: 'SL_KD', headerText: 'SL KD', allowFiltering: true ,type:'number', format:'N3'},
            {fieldName: 'DONGIA', headerText: 'Đơn giá', allowFiltering: true ,type:'number', format:'N3'},
            {fieldName: 'TIEN', headerText: 'Thành tiền', allowFiltering: true ,type:'number', format:'N3'},
            {fieldName: 'LOAI_TBI', headerText: 'Loại TB', allowFiltering: true},   
            {fieldName: 'SL_XUAT', headerText: 'SL xuất', allowFiltering: true },                
          ]
        }else{
          this.gridVatTu = [
            {fieldName: 'MA_VT', headerText: 'Mã vật tư', allowFiltering: true},
            {fieldName: 'TEN_VT', headerText: 'Tên vật tư', allowFiltering: true},
            {fieldName: 'DVI_TINH', headerText: 'ĐVT', allowFiltering: true},
            {fieldName: 'SL_NT', headerText: 'Số lượng', allowFiltering: true ,type:'number', format:'N3'} ,            
            {fieldName: 'DONGIA', headerText: 'Đơn giá', allowFiltering: true ,type:'number', format:'N3'},
            {fieldName: 'TIEN', headerText: 'Thành tiền', allowFiltering: true ,type:'number', format:'N3'},
            {fieldName: 'LOAI_TBI', headerText: 'Loại TB', allowFiltering: true},                        
          ]
        }
        if(this.thongTinNguoiDung.phanVungId == 28){       
          this.gridVatTu.push({fieldName: 'CHIA_SL', headerText: 'Chia SL', allowFiltering: true , allowEditing: true }) 
        }        
      }
      this.dataResponse.DSVatTuChiTiet = DSVatTuChiTiet
    },
    async onClickTimKiem(){
      try{
        this.loading(true)
        await this.LAY_DS_DONHANG()
      }catch(e){
        this.$toast.error(e.data.message);
      }finally{
        this.loading(false);
      }
    },
    async onClickXacNhan(){
      try{
        var a = this.$refs.gridVatTu.getSelectedRecords()
        if (!a.length){
          this.$toast.error("Chưa chọn vật tư nào!")
          this.XacNhan = false
          return
        }
        if(this.quyen){
          var cuoncap = a.filter(
            function (el) {
              return (
                el.LOAITBI_ID != 2
              );
            }
          );

          if(cuoncap.length > 0){
            var isBoxConfirm = false;
            await this.$bvModal
              .msgBoxConfirm("Có thiết bị không phải Serial. Bạn có muốn chia cuộn cáp không ???", {
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
            for(const i in cuoncap){          
              if(cuoncap[i].CHIA_SL == 0 || cuoncap[i].CHIA_SL === undefined){              
                this.$toast.error("Bạn chưa chia số lượng - mã vật tư [" + cuoncap[i].MA_VT + "]")
                return
              }
            }
            var f = this.$refs.frmChiaCuonCap
            f.dt_vattu = a
            f.dt_cuoncap = cuoncap    
            // kiểm tra parent để tránh trường hợp gọi từ menu vật tư  
            if(this.taskParent != ""){
              this.$parent.hide()
            }          
            f.Open()
          }
        }else{
          this.dt = a 
          this.XacNhan = true
          this.Close()
        }
      }catch(e){
        this.$toast.error(e.data.message);
      }finally{
        this.loading(false);
      }
    },
    async closeChiaCuonCap() {
      if(this.taskParent != ""){
        this.$parent.show()
      }      
      var f = this.$refs.frmChiaCuonCap
      this.dt = f.chia
      this.is_cc = f.is_cc
      if(this.is_cc){
        this.XacNhan = true
        this.Close()
      }
    },
    async Close(){
      if(this.taskParent != ""){
        this.$parent.hide()
      }
    },
    async onClickHuy(){
      try{
        if(this.rowFocus == {}){
          return
        }
        this.loading(true)        
        await this.selectDonHang(this.rowFocus)
        this.XacNhan = false
        this.loading(false)
      }catch(e){
        this.$toast.error(e.data.message);
      }finally{
        this.loading(false);
      }
    }
  },
  

  created: async function () {
    try{
      this.loading(true)
      if(this.thongTinNguoiDung.phanVungId == 28){
        this.quyen = true
      }
      await this.loadDataDonHang()
      this.loading(false)
    }catch(e){
      this.$toast.error(e.data.message);
    }finally{
      this.loading(false);
    }   
  },
  provide: {
    grid: [Group, Page, Filter, Sort, Resize],
  },
};
</script>
