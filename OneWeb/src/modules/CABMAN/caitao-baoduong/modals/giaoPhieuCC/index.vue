<template src="./index.html"></template>
<script>
import Vue from "vue";
import DateTimeLib from "../../../../../utils/DateTimeLib";
import {
  GridPlugin,
  Page,
  Filter,
  CommandColumn,
  Resize,
} from "@syncfusion/ej2-vue-grids";
export default {
  name: "modal_giaophieu",
  provide: {
    grid: [Filter, Page, CommandColumn, Resize],
  },
  created: async function () {},
  components: {},
  watch: {
   trangthai: function (value) {
     this.setForm()
   }
  },
  computed: {},
  created:async function(){
     await this.initForm();     
  },
  methods: {
    initData: function (huonggiao_id) {
    console.log('huonggiao_id',huonggiao_id)

    this.huonggiao_id = huonggiao_id;
    },
    focusMyElement: async function () {     
        await this.initForm();           
    },
    hiddenModal: function(){
     this.resetForm()
    },
    initForm: async function () {
       try {
       this.$root.showLoading(true);   
      
       await this.LAY_DS_HUONGGIAO();
       await this.LAY_HUONGGIAO_TIEPTHEO(this.huonggiao_id);
       await this.HienThi_DanhSach()
       this.load = false;
     } catch (er) {
        console.log(er)
      } finally {
        this.$root.showLoading(false);
      }
    },
    tsbtnTimKiem_Click: async function (){
        await this.HienThi_DanhSach()
    },
    tsbtnGiaoPhieu_Click: async function (){
     try
      {
        this.$root.showLoading(true);
         if(this.dsPhieu.length<= 0){
           this.$root.toastError("Không có hồ sơ để giao. Bạn hãy kiểm tra lại!");
            return;
            }
          
          let ttph_id;
          let bGiaoPhieu = false;
          let phieu_id = 0;
          let hscc_id = 0;
          let donvi_id = 0;
          phieu_id = this.currentItem.PHIEUCC_ID;
          hscc_id = this.currentItem.HSCC_ID;
          donvi_id = this.currentItem.DONVI_NHAN_ID;
          let noidung_giao = this.stringToString(this.noidung_giao);
          let result = await this.GIAOPHIEU_CAITAO_BAODUONG(phieu_id,hscc_id,this.huonggiao_id,donvi_id,noidung_giao)
          console.log('result',result)
          if(result =='OK')
          {
              this.$root.toastSuccess("Đã giao phiếu thành công");
              await this.HienThi_DanhSach();
              this.KtThucHien = true;
              this.$emit('ok',this.KtThucHien)
          }else{
             this.$root.toastError(result)
             await this.HienThi_DanhSach();
             return;
          }
      }catch(e){
        this.$root.toastError('Giao phiếu thất bại')
        console.log(e)
      }
      finally
        {
          this.$root.showLoading(false);
        }
    },
    stringToString: function(xauthamso){
    if (xauthamso == null) return "";
            let letter;
            let Text11;
            let Text22 = "";
            Text11 = xauthamso;

            for (let j = 0; j < Text11.length; j++)
            {
                letter = Text11.substring(j,j+1);
                if (letter == "\r")
                    Text22 = Text22 + "\r";
                else if (letter == "\n")
                    Text22 = Text22 + "\n";
                else
                {
                    Text22 = (Text22 + letter);
                }
            }
            return Text22;
    },
    ok:function(){
    
    },
    tsbtnHuyGiao_Click: async function (){
     try
      {
        this.$root.showLoading(true);
         if(this.dsPhieu.length<= 0){
           this.$root.toastError("Không có hồ sơ để giao. Bạn hãy kiểm tra lại!");
            return;
            }
            let  ttph_id;
            let bGiaoPhieu = false;
            let phieu_id = 0;
            let hscc_id = 0;
            let donvi_id = 0;

          phieu_id = this.currentItem.PHIEUCC_ID;
          hscc_id = this.currentItem.HSCC_ID;
          donvi_id = this.currentItem.DONVI_NHAN_ID;
        let result = await this.HUY_GIAOPHIEU_CAITAO_BAODUONG(phieu_id,hscc_id,this.huonggiao_id,donvi_id);
        if(result =='OK')
          {
              this.$root.toastSuccess("Hủy giao phiếu thành công");
              await this.HienThi_DanhSach();
              this.KtThucHien = true;
             this.$emit('ok',this.KtThucHien)
          }else{
             this.$root.toastError(result)
             await this.HienThi_DanhSach();
             return;
          }
      }catch(e){
        this.$root.toastError('Hủy giao phiếu thất bại')
        console.log(e)
      }
    finally
        {
          this.$root.showLoading(false);
        }
    },
    tsbtnXuatFile_Click: async function (){
     //  this.$emit('ok')
    },
    huongGiao_change:async function(){
      this.dsHuongGiao = this.dsHuongGiao.filter(x=>x.HUONGGIAO_ID != "-1");
      if(this.huonggiao_id >=0){
      if(!this.load)
       await this.HienThi_DanhSach()
      }
    },
    setForm: function(){
      if(this.trangthai ==1){
        this.isDisableBtnHuyGiao = true;
        this.isDisableBtnTimKiem = false;
        this.isDisableBtnGiaoPhieu = false;
        this.noidung_giao = '';
      }else if(this.trangthai == 2){
        this.isDisableBtnHuyGiao = false;
        this.isDisableBtnTimKiem = true;
        this.isDisableBtnGiaoPhieu = true;
      }
      if(!this.load)
      this.HienThi_DanhSach()
    },
    GIAOPHIEU_CAITAO_BAODUONG: async function (phieu_id,hscc_id,huonggiao_id,donvi_nhan_id,noidung){
     let rs = await this.$root.context.post(
        "/web-cabman/CaiTaoBaoDuong/giaophieu_caitao_baoduong",
        {
          phieu_id: phieu_id,
          hscc_id:hscc_id,
          huonggiao_id:huonggiao_id,
          noidung:noidung,
          donvi_nhan_id:donvi_nhan_id
        }
      );
      return rs.data.RS
    },
     HUY_GIAOPHIEU_CAITAO_BAODUONG: async function (phieu_id,hscc_id,huonggiao_id,donvi_nhan_id){
     let rs = await this.$root.context.post(
        "/web-cabman/CaiTaoBaoDuong/huy_giaophieu_caitao_baoduong",
        {
          phieu_id: phieu_id,
          hscc_id:hscc_id,
          huonggiao_id:huonggiao_id,
          noidung:'',
          donvi_nhan_id:donvi_nhan_id
        }
      );
      console.log('rs.data',rs.data)
      return rs.data.RS
    },
    LAY_DS_HUONGGIAO: async function () {
     let rs = await this.$root.context.post(
        "/web-cabman/CaiTaoBaoDuong/LayDsHuongGiao",
        {
          nhom_qt_id: 8,
          quytrinh_id: 0,
          loaihd_id:0,
          dichvuvt_id:0
        }
      );
      let temp = [];
      temp = rs.data;
      this.dsHuongGiao = [...temp];
     if(this.huonggiao_id ==-1)
       this.dsHuongGiao.unshift({"HUONGGIAO_ID":-1,"HUONGGIAO":"Chọn hướng giao"}); 
     
    },
    LAY_HUONGGIAO_TIEPTHEO: async function (huonggiao_id) {
    let rs = await this.$root.context.get(
        "/web-cabman/CaiTaoBaoDuong/LayHuongGiaoTiepTheo/"+huonggiao_id);
     if(rs.data!=null && rs.data.length > 0)
      if(this.dsHuongGiao.find(x=>x.HUONGGIAO_ID == rs.data[0].HUONGGIAO_ID ))
      this.huonggiao_id =  rs.data[0].HUONGGIAO_ID 
    },
    LAY_DS_CC_BD_CHUAGIAO: async function (){
     this.dsPhieu = [];
     let data = {
          huonggiao_id: this.huonggiao_id,
          ma_cc: this.ma_cc == "" ? "0" : this.ma_cc,
        }
     let rs = await this.$root.context.post(
        "/web-cabman/CaiTaoBaoDuong/lay_ds_cc_bd_chuagiao",data      
      );
      console.log('rs:',rs)
      this.dsPhieu = rs.data
    },
    LAY_DS_CC_BD_DAGIAO: async function (){
     this.dsPhieu = [];
     let rs = await this.$root.context.post(
        "/web-cabman/CaiTaoBaoDuong/lay_ds_cc_bd_dagiao",
        {
          huonggiao_id: this.huonggiao_id,
          ma_cc:this.ma_cc,
        }
      );
      this.dsPhieu = rs.data
    },
    HienThi_DanhSach: async function (){
     try{
     this.$root.showLoading(true)
     if(this.huonggiao_id !=-1){
     if(this.trangthai ==1)
       await this.LAY_DS_CC_BD_CHUAGIAO();
      else if(this.trangthai ==2)
       await this.LAY_DS_CC_BD_DAGIAO(); 
     }    
     }catch(e){
       console.log(e)
     }
     finally{
       this.$root.showLoading(false)
     }
    },
    grid_phieu_selected:  function (args) {
      if(args!=null){     
      this.currentItem = args
      this.ma_cc = args.MA_HS_CC
      if(this.trangthai==2)
        this.noidung_giao = args.ND_GIAO
      }
    },
  },
  data: function () {
    return {
    phieuId:0, 
    hscc_id:0, 
    dsPhieu:[],
    dsHuongGiao:[],
    currentItem: {},
    waterMark: "Chọn ngày",
    dateVal:  Date,
    dateFormat: "dd/MM/yyyy",
    ngay_giao: new Date(),
    lydo_tra:0,
    ma_cc:'',
    trangthai:1,
    huonggiao_id:-1,
    noidung_giao:'',
    ghichu:''  ,
    load: true ,
    isFirst:false,
    loaidv_nhan_id:-1,
    KtThucHien:false,
    isDisableBtnGiaoPhieu:false,
    isDisableBtnHuyGiao:true,
    isDisableBtnTimKiem:false,
    cTemplateNgayBD: function () {
        return {
          template: Vue.component("columnTemplate", {
            template: `<div>
                {{getNgayBD}}
                </div>`,
            data: function () {
              return {
                data: {},
              };
            },
            computed: {
              getNgayBD: function () {
              //  return 1;
              //  console.log('data',this.data)
                if(this.data.NGAYBD_DK != null)
                return DateTimeLib.toDateDisplayWithFormat(this.data.NGAYBD_DK,"DD/MM/yyyy")
              }
              }  ,    
              methods: {
              },            
          }),
        };
      },
    cTemplateNgayKT: function () {
        return {
          template: Vue.component("columnTemplate", {
            template: `<div>
                {{getNgayKT}}
                </div>`,
            data: function () {
              return {
                data: {},
              };
            },
            computed: {
              getNgayKT: function () {
                if(this.data.NGAYKT_DK != null)
                return DateTimeLib.toDateDisplayWithFormat(this.data.NGAYKT_DK,"DD/MM/yyyy")
              }
              }  ,    
              methods: {
              },            
          }),
        };
      },
    };
  },
   validations: {

   }
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