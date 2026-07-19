<template src="./Hopdong.html"></template>
<style scoped src="./Hopdong.scss"></style>
<script>
  import ActionTop from '@/components/kylq_components/ActionTop.vue'
 export default {
      name:'DSHopdong',
      props:{
        dsFileKenhBan:[],
          dichvuvt_id:{
              type:Number,
              default:4
              //default:9
          },
          hdtb_id:{
              type:Number,
              default:0
              //default:4130927
          },
          kieu:{
              type:Number,
              default:0 //0 : hợp đồng, 1 : danh bạ
          },
          thuebao_id:{
              type:Number,
              default:0
              //default:1196535
          },
          ma_tb:{
              type:String,
              default:''
          }
      },
      components:{
          ActionTop,
      },
      data(){
          return {
              DsLoaihopdong:[],
              DsSanpham:[],
              DsQuyen:[],
              items:{}
          }
      },
      methods:{
          showModal() {
              this.$refs["nhapHopdongModal"].show()
          },
          hideModal() {
              this.$refs["nhapHopdongModal"].hide()
          },
          changeFileUpload(){

          },
          async handleShowModal(){
              //Khi show modal
              setTimeout(()=>{
                 //this.initDuLieu()
              },500)
              
          },
          async initDuLieu(){
              this.input_ma_tb=this.ma_tb
  
              if(this.dichvu_selected==DichVuVienThong.CO_DINH){
                await this.$refs.coDinh.initDuLieu()
              }else if(this.dichvu_selected==DichVuVienThong.ADSL){
                await this.$refs.bangRongCoDinh.initDuLieu()
              }else if(this.dichvu_selected==DichVuVienThong.MEGAWAN||this.dichvu_selected==DichVuVienThong.METRONET){
                this.ds_tocdokenh=await this.lay_ds_tocdo_kenh()
                this.ds_loaithietbi=await this.sp_lay_ds_loai_tbi(this.dichvu_selected)
                await this.$refs.megawanMetronet.initDuLieu(this.ds_tocdokenh, this.ds_loaithietbi)
              }else if(this.dichvu_selected==DichVuVienThong.TSL){
                this.ds_tocdokenh=await this.lay_ds_tocdo_kenh()
                this.ds_loaithietbi=await this.sp_lay_ds_loai_tbi(9)
                await this.$refs.kenhThueRieng.initDuLieu(this.ds_tocdokenh, this.ds_loaithietbi)
              }else if(this.dichvu_selected==DichVuVienThong.GPHONE){
                await this.$refs.gphone.initDuLieu()
              }else if(this.dichvu_selected==DichVuVienThong.IMS){
                await this.$refs.ims.initDuLieu()
              }
  
              if(this.kieu==0){
                  this.loai=0
                  if(this.hdtb_id != 0 && this.dichvuvt_id != 0){
                      await this.hienthi_ttkt(this.hdtb_id, this.dichvuvt_id)
                  }
              }else{
                  this.loai=1
                  console.log(this.thuebao_id+'|'+this.dichvuvt_id)
                  if(this.thuebao_id!=0&&this.dichvuvt_id!=0){
                      await this.hienthi_ttkt(this.thuebao_id, this.dichvuvt_id)
                  }
              }
          },
          async lay_ds_db_con_theo_tbid(thuebao_id, dichvuvt_id){
              try{
                  this.loading(true)
                  let response = await API.lay_ds_db_con_theo_tbid(this.axios, thuebao_id, dichvuvt_id)
                  this.loading(false)
                  if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                      return response.data.data
                  }else{
                      return []
                  }
              }catch(e){
                  this.loading(false)
                  return []
              }
          },
      }
  }
  </script>