<template src="./index.html"> </template>

<script>
import EventBus from "../../../../utils/eventBus";
import BssErrorMarker from '@/components/BssErrorMarker'
import BssRequiredMarker from '@/components/BssRequiredMarker'
export default {
  components: {BssErrorMarker,BssRequiredMarker },
  data() {
    return {
      dsHuongKetNoi:[],
      dsThietBiDsLamTemp:[],
      dsThietBiDsLamFinal:[],
      dsThietBiDsLam:[],
      dsThietBiSwitch:[],
      dsThietBiGpon:[],
      dsThietBiMane:[],
      ma_ketnoi:"",
      demClickDsLam:0,
      thongTinKN:Object,
      form:{
        connect:'1',
        view:1,
        port_DsLam:{
          vitri:0,
          ma_dslamPort:0
        },
        edit:false,
        fkey:"",
        ten_tbi_nguon:"",
        thietbi_n_id:0,
        loai_tbi_n:0,
        loai_tbi_d:0,
        port_dich:0    
      },
      dslam:{
        thietbi_d_id:null,
        dsCardDsl:[],
        dsPort:[],
        dsModule:[],
        dsPort_Module:[],
        cardDsl_id:null,//id card thiết bị
        port_id:null,// id port Card
        module_id:null,// id Module Card
        port_module:null, // id port module
        card_dslam:1,  // xác định check port / module của card
        ma_ketnoi:""
      },
      switch_:{
        thietbi_d_id:null,
        dsPort:[],
        port_id:null,
        ma_ketnoi:""
      },
      gpon:{
        thietbi_d_id:null,
        dsCardGpon:[],
        cardGpon_id:null,
        dsPort:[],
        port_id:null,
        ma_ketnoi:""
      },
      mane:{
        thietbi_d_id:null,
        dsCardMane:[],
        dsPort:[],
        dsModule:[],
        dsPort_Module:[],
        cardMane_id:null,
        port_id:null,
        module_id:null,
        port_module:null,
        card_mane:1,
        ma_ketnoi:""
      },
      type:0,
      huong_kn:1,
      args:{},
      thietbi:{
        tbi_n_id:null,
        tbi_d_id:null,
        turack_1:null,
        turack_2:null
      }
    };
  },
  watch:{
     huong_kn:async function(val){
       await this.getdsThietBi()
     },
     //#region "dslam"
    'dslam.thietbi_d_id': async function(){
      await  this.getdsCardDslam()
    },
    'dslam.cardDsl_id': async function(){
      await  this.getDsPort_Module_CardDslam()
    },
    'dslam.module_id': async function(){
      await  this.getPortFree_Module_dsLam()
    },
     //#endregion
    
     //#region  "switch"
     'switch_.thietbi_d_id': async function(){
       await this.getPortSwitch()
     },
     //#endregion
    
     //#region "Gpon"
     'gpon.thietbi_d_id' : async function(){
      await  this.getdsCardGpon()
     },
     'gpon.cardGpon_id': async function(){
      await this.getdsPortGpon()
     },
     //#endregion 
    
     //#region "mane"
    'mane.thietbi_d_id': async function(){
       await  this.getdsCardMane()
    },
    'mane.cardMane_id': async function(){
            await  this.getDsPort_Module_CardMane()
    },
    'mane.module_id': async function(){
            await  this.getPortFree_Module_Mane()
    },
     //#endregion
  },
    methods: {
      //#region "Init"
      resetForm:async function(){
         await this.getdsHuongKetNoi()   
         this.dslam={
        thietbi_d_id:null,
        dsCardDsl:[],
        dsPort:[],
        dsModule:[],
        dsPort_Module:[],
        cardDsl_id:null,//id card thiết bị
        port_id:null,// id port Card
        module_id:null,// id Module Card
        port_module:null, // id port module
        card_dslam:1,  // xác định check port / module của card
        ma_ketnoi:""
      }
      this.switch_={
        thietbi_d_id:null,
        dsPort:[],
        port_id:null,
        ma_ketnoi:""
      },
      this.gpon={
        thietbi_d_id:null,
        dsCardGpon:[],
        cardGpon_id:null,
        dsPort:[],
        port_id:null,
        ma_ketnoi:""
      },
      this.mane={
        thietbi_d_id:null,
        dsCardMane:[],
        dsPort:[],
        dsModule:[],
        dsPort_Module:[],
        cardMane_id:null,
        port_id:null,
        module_id:null,
        port_module:null,
        card_mane:1,
        ma_ketnoi:""
      } 
      },
      loadForm:async function(){    
       await this.resetForm() 
        this.type = this.form.edit?1:0
       if(this.form.edit)
         this.viewInfo()    
      },
      bindData: async function(data,args){
       await this.getdsHuongKetNoi()
        Object.assign(this.args,args)
        console.log('args', this.args)
       if(data!=null){
         Object.assign(this.form,data)
         console.log('data form', this.form)
         this.thietbi.tbi_n_id  =  this.form.thietbi_n_id;
         if(this.form.connect == 0)
         this.form.connect = 1
         this.loadForm()
       }

      },
      viewInfo: async  function(){
        try{
         await this.getThongTinKetNoi()
          
          if(this.thongTinKN!=null){
            this.huong_kn = this.thongTinKN.HUONG_KN
            this.form.port_dich =this.thongTinKN.PORVL_ID_D
            this.form.loai_tbi_d= this.thongTinKN.HUONG_KN
            // this.form.connect = this.thongTinKN.LINK_CODE
            this.thietbi.tbi_d_id  = this.thongTinKN.THIETBI_ID           
         await   this.getMaKetNoi(this.form.port_dich)
            if(this.form.loai_tbi_d==1)
             this.initDsLam()
            if(this.form.loai_tbi_d==2)
             this.initSwitch()
            if(this.form.loai_tbi_d==3)
             this.initGpon()
            if(this.form.loai_tbi_d==17)
             this.initMane() 
          }
          }catch(err){
           console.log(err)
          }
      },
      initDsLam: function(){
          this.dslam.thietbi_d_id = this.thongTinKN.THIETBI_ID
          this.dslam.cardDsl_id = this.thongTinKN.CARD_ID
          if(this.thongTinKN.MODULE_ID!=null){
            this.dslam.card_dslam=2
            this.dslam.module_id=this.thongTinKN.MODULE_ID
            this.dslam.port_module= parseInt(this.thongTinKN.PORVL_ID_D)
          }else{
            this.dslam.card_dslam=1
            this.dslam.port_id = this.thongTinKN.PORVL_ID_D
          }
          this.dslam.ma_ketnoi = this.ma_ketnoi
      },
      initSwitch: function(){
          this.switch_.thietbi_d_id = this.thongTinKN.THIETBI_ID
          this.switch_.port_id = parseInt(this.thongTinKN.PORVL_ID_D)
          this.switch_.ma_ketnoi = this.ma_ketnoi
      },
      initGpon: function(){
          this.gpon.thietbi_d_id = this.thongTinKN.THIETBI_ID
          this.gpon.cardGpon_id = this.thongTinKN.CARD_ID
          this.gpon.port_id = parseInt(this.thongTinKN.PORVL_ID_D)
          this.gpon.ma_ketnoi = this.ma_ketnoi
      },
      initMane: function(){
          this.mane.thietbi_d_id = this.thongTinKN.THIETBI_ID
          this.mane.cardMane_id = this.thongTinKN.CARD_ID
          if(this.thongTinKN.MODULE_ID!=null){
        
            this.mane.card_mane=2
            this.mane.module_id=this.thongTinKN.MODULE_ID
            this.mane.port_module=parseInt(this.thongTinKN.PORVL_ID_D)
          }else{
            this.mane.card_mane=1
            this.mane.port_id = this.thongTinKN.PORVL_ID_D
          }
          this.mane.ma_ketnoi = this.ma_ketnoi
      },
      getMaKetNoi: async function(port_dich){
         try {
          this.$root.showLoading(true);
          let rs = await this.$root.context.post(
             "web-ecms/thongtin-ketnoi/laymakn",{
              madslam_port:this.form.port_DsLam.ma_dslamPort,
              port_dich: port_dich 
             }        
          );  
          if(rs.data!=null)
          this.ma_ketnoi =rs.data.MA_KN 
          else
          this.ma_ketnoi='' 
        } catch (error) {
        } finally {
          this.$root.showLoading(false);
        }
      },
      getThongTinKetNoi: async function(){
        try {
          this.$root.showLoading(true);
          let rs = await this.$root.context.get(
             "web-ecms/thongtin-ketnoi/load-tt-ketnoi/"+
             this.form.port_DsLam.ma_dslamPort       
          );  
       
          this.thongTinKN =rs.data     
        } catch (error) {
        } finally {
          this.$root.showLoading(false);
        }
      },
      getdsHuongKetNoi: async function(id) {
        try {
          this.$root.showLoading(true);
          this.dsHuongKetNoi=[]
          let rs = await this.$root.context.get(
             "web-ecms/thongtin-ketnoi/huong-ket-noi"       
          );  
        // if(rs.data!=null &&rs.data.length>0)
          this.dsHuongKetNoi =rs.data   
         await this.getdsThietBi()         
        } catch (error) {
        } finally {
          this.$root.showLoading(false);
        }
      },
      getdsThietBi:async function(){
        try {
          this.$root.showLoading(true);
          let rs = await this.$root.context.post(
             "web-ecms/thongtin-ketnoi/dsThietBi/"+this.huong_kn
             )  
          if(rs.data!=null &&rs.data.length>0){
               
              if(this.huong_kn ==1){
                if(!this.form.edit){
                 this.demClickDsLam = 0
                 this.dsThietBiDsLamTemp.push({DSLAM_ID:-1,TENDSLAM:'---Chọn thiết bị---'})
                 this.dsThietBiDsLam = [...this.dsThietBiDsLamTemp]
                 this.dsThietBiDsLamFinal=rs.data                
                }else{
                   this.demClickDsLam=1
                   this.dsThietBiDsLam =rs.data
                }
              }
              if(this.huong_kn ==2){
                this.dsThietBiSwitch =rs.data   
                if(!this.form.edit)             
                this.switch_.thietbi_d_id = this.dsThietBiSwitch[0].DSLAM_ID
              await  this.getPortSwitch()
                }
              if(this.huong_kn ==3){
                 this.dsThietBiGpon =rs.data
                 if(!this.form.edit)   
                 this.gpon.thietbi_d_id = this.dsThietBiGpon[0].DSLAM_ID
               await  this.getdsCardGpon()
                 }
              if(this.huong_kn ==17){
                 this.dsThietBiMane = rs.data
                if(!this.form.edit)   
                 this.mane.thietbi_d_id = this.dsThietBiMane[0].DSLAM_ID
               await  this.getdsCardMane()
                 }
          }
                   
        } catch (error) {
        } finally {
          this.$root.showLoading(false);
        }
      },
      convertData: function(){
          if(this.demClickDsLam == 0){        
          this.dsThietBiDsLam = [...this.dsThietBiDsLamFinal]
          this.demClickDsLam =  1
          }
      },
      onChangeHuongKN:async function(){
      await  this.getdsThietBi()
      },
      onChangeThietBiSwich:async function(){

      },
      //#endregion

      //#region "dsLam"      
      thietBiDsLam_selectedChanged:async function(data){
        if(data!=null){
        this.dslam.thietbi_d_id = data.DSLAM_ID
        await this.getdsCardDslam()
        }
      },
      getdsCardDslam: async function(){
         try {
          if(this.dslam.thietbi_d_id==null)
          return 
          let rs = await this.$root.context.get(
             "web-ecms/thongtin-ketnoi/dsCardDsl/"+this.dslam.thietbi_d_id
             )  
           this.dslam.dsCardDsl =rs.data   
          if(rs.data!=null &&rs.data.length>0){            
              if(!this.form.edit)   
              this.dslam.cardDsl_id = this.dslam.dsCardDsl[0].CARDDSL_ID
           await   this.getDsPort_Module_CardDslam()
          }                   
        } catch (error) {
        } finally {
          this.$root.showLoading(false);
        }
      },
      onChangeCardDsl: async function(){
      await  this.getDsPort_Module_CardDslam()
      },
      getDsPort_Module_CardDslam:async function(){
         try {
           if(this.dslam.cardDsl_id==null)
          return
          let rs = await this.$root.context.post(
             "web-ecms/thongtin-ketnoi/ds-port-module-carddsl",{
                id:this.dslam.cardDsl_id,
                type:this.type
             }
             )  
          if(rs.data!=null){
              this.dslam.dsPort =rs.data.dsPort
              if(this.dslam.dsPort.length>0)
              if(!this.form.edit)   
              this.dslam.port_id =this.dslam.dsPort[0].PORTVL_ID
              this.dslam.dsModule = rs.data.dsModule
              if(this.dslam.dsModule.length>0){
                if(!this.form.edit)   
              this.dslam.module_id =this.dslam.dsModule[0].MODULEDSL_ID
             await  this.getPortFree_Module_dsLam()
               }
          }                   
        } catch (error) {
        } finally {
          this.$root.showLoading(false);
        }
      },
      onChangeModuleDsl:async function(){
       await this.getPortFree_Module_dsLam()
      },
      getPortFree_Module_dsLam: async function(){
        try {
          if(this.dslam.module_id==null)
          return
          let rs = await this.$root.context.post(
             "web-ecms/thongtin-ketnoi/dsPort_ModuleDsLam",{
                id: this.dslam.module_id,
                type:this.type
             }
             )  
         this.dslam.dsPort_Module =rs.data                   
        } catch (error) {
        } finally {
          this.$root.showLoading(false);
        }
      },
      //#endregion
      
      //#region  "Switch"
      getPortSwitch: async function(){
         try {
          this.$root.showLoading(true);
          if(this.switch_.thietbi_d_id==null)
          return
          let rs = await this.$root.context.post(
             "web-ecms/thongtin-ketnoi/dsPortSwitch", {
                id:this.switch_.thietbi_d_id,
                type:this.type
             }      
          );  
            this.switch_.dsPort =rs.data 
          if(rs.data!=null &&rs.data.length>0){
         
          if(!this.form.edit)   
          this.switch_.port_id= this.switch_.dsPort[0].PORTVL_ID}      
        } catch (error) {
        } finally {
          this.$root.showLoading(false);
        }
      },
      //#endregion

      //#region "GPon"
      onChangeThietBiGpon: function(){
        this.getdsCardGpon()
      },
      getdsCardGpon: async function(){
         try {
          this.$root.showLoading(true);
           if(this.gpon.thietbi_d_id==null)
           return
          let rs = await this.$root.context.get(
             "web-ecms/thongtin-ketnoi/dsCardGpon/"+ this.gpon.thietbi_d_id
          );  
            this.gpon.dsCardGpon =rs.data 
          if(rs.data!=null &&rs.data.length>0){
        
          if(!this.form.edit)   
          this.gpon.cardGpon_id= this.gpon.dsCardGpon[0].CARDGP_ID
           await this.getdsPortGpon()}      
        } catch (error) {
        } finally {
          this.$root.showLoading(false);
        }  
      },
      onChangeCardGpon:async function(){
       await this.getdsPortGpon()
      },
      getdsPortGpon: async function(){
        try {
          this.$root.showLoading(true);
          if(this.gpon.cardGpon_id==null)
          return
          let rs = await this.$root.context.get(
             "web-ecms/thongtin-ketnoi/dsPort_CardGpon/"+ this.gpon.cardGpon_id
          ); 
          this.gpon.dsPort =rs.data  
          if(rs.data!=null &&rs.data.length>0){
 
          if(!this.form.edit)   
          this.gpon.port_id= this.gpon.dsPort[0].PORTVL_ID}      
        } catch (error) {
        } finally {
          this.$root.showLoading(false);
        }  
      },
      //#endregion
     
     //#region "Mane" 
     onChangeThietBiMane:async function(){
     await this.getdsCardMane()
     },
     getdsCardMane: async function(){
        try {
          if(this.mane.thietbi_d_id==null)
          return
          let rs = await this.$root.context.get(
             "web-ecms/thongtin-ketnoi/dsCardMane/"+this.mane.thietbi_d_id
             )  
          this.mane.dsCardMane =rs.data
          if(rs.data!=null &&rs.data.length>0){
            
              if(!this.form.edit)
              this.mane.cardMane_id = this.mane.dsCardMane[0].CARDMANE_ID
            await  this.getDsPort_Module_CardMane()
          }                   
        } catch (error) {
        } finally {
          this.$root.showLoading(false);
        }
     },
     onChangeCardMane:async function(){
    await this.getDsPort_Module_CardMane()
     },
      getDsPort_Module_CardMane:async function(){
         try {
          if(this.mane.cardMane_id==null)
          return
          let rs = await this.$root.context.post(
             "web-ecms/thongtin-ketnoi/dsPortModule_CardMane",{
                id:this.mane.cardMane_id,
                type:this.type
             }
             ) 
           this.mane.dsPort =rs.data.dsPort 
          if(rs.data!=null){

              if(this.mane.dsPort.length>0)
              if(!this.form.edit)   
              this.mane.port_id =this.mane.dsPort[0].PORTVL_ID
              this.mane.dsModule = rs.data.dsModule
              if(this.mane.dsModule.length>0){
                if(!this.form.edit)   
              this.mane.module_id =this.mane.dsModule[0].MODULEMANE_ID
              await this.getPortFree_Module_Mane()
              }
          }                   
        } catch (error) {
        } finally {
          this.$root.showLoading(false);
        }
      },
      onChangeModuleMane:async function(){
      await  this.getPortFree_Module_Mane()
      },
      getPortFree_Module_Mane: async function(){
        try {
          if(this.mane.module_id==null)
          return
          let rs = await this.$root.context.post(
             "web-ecms/thongtin-ketnoi/dsPort_ModuleMane",{
                id: this.mane.module_id,
                type:this.type
             }
             )  
          this.mane.dsPort_Module =rs.data   
          if(rs.data!=null &&rs.data.length>0){             
               if(!this.form.edit)   
               this.mane.port_module= this.mane.dsPort_Module[0].PORTVL_ID
          }                   
        } catch (error) {
        } finally {
          this.$root.showLoading(false);
        }
      },
     //#endregion
      ketNoi:async function(){
        try{
       this.$root.showLoading(true);
        if(this.huong_kn==1)
          await  this.ketNoiDsLam()
        else if(this.huong_kn==2)
          await  this.ketNoiSwitch()
       else if(this.huong_kn==3)
         await   this.ketNoiGpon()
       else if(this.huong_kn==17)
         await   this.ketNoiMane()
         else
         this.$root.toastError('Chưa chọn hướng kết nối!')  
        //Load lại sơ đồ thiết bị
        }finally{
           this.$root.showLoading(false);
        }
      },
      ketNoiDsLam:async function(){
        try{
         let port_id
         if(this.dslam.thietbi_d_id==null ||this.dslam.thietbi_d_id ==-1 ){
         this.$root.toastError('Chưa chọn thiết bị đích để kết nối!')  
         return
         }
         if(this.dslam.cardDsl_id==null){
           this.$root.toastError('Chưa chọn card dslam để kết nối!') 
             return
         }
       
         if(this.dslam.card_dslam==1){
           if(this.dslam.port_id==null){
             this.$root.toastError('Card điều khiển không còn port trống !')
             return
           }else
            port_id= this.dslam.port_id
           }else{
             if(this.dslam.port_module==null){
             this.$root.toastError('Module không còn port trống !')
             return
           }else
            port_id= this.dslam.port_module
           }
           this.form.port_dich= port_id
          let rs = await this.$root.context.post(
             "web-ecms/thongtin-ketnoi/ketnoi",{
                  madslam_port:this.form.port_DsLam.ma_dslamPort,
                  loaithietbi_id_n:this.form.loai_tbi_n,
                  loaithietbi_id_d:this.huong_kn,
                  thietbi_id_n:this.form.thietbi_n_id,
                  thietbi_id_d:this.dslam.thietbi_d_id,
                  link_code:this.form.connect,
                  port_dich:port_id,
                  chk_port: this.dslam.card_dslam -1 })
            if(rs.error_code=="BSS-00000000"){
              this.dslam.ma_ketnoi = rs.data.MA_KN//await this.getMaKetNoi(port_id)
              this.thietbi.tbi_d_id = this.dslam.thietbi_d_id
              this.transferStatus(3)
              this.$root.toastSuccess('Kết nối thành công !')             
              this.form.edit=true

            }
        }catch(err){
          this.$root.toastError('Kết nối thất bại !')
          console.log(err)
        }
         
      },
      ketNoiSwitch: async function(){
        try{
         let port_id

         if(this.switch_.port_id==null){
             this.$root.toastError('Card điều khiển không còn port trống !')
             return          
           }else
            port_id= this.switch_.port_id
          this.form.port_dich= port_id
          let rs = await this.$root.context.post(
             "web-ecms/thongtin-ketnoi/ketnoi",{
                  madslam_port:this.form.port_DsLam.ma_dslamPort,
                  loaithietbi_id_n:this.form.loai_tbi_n,
                  loaithietbi_id_d:this.huong_kn,
                  thietbi_id_n:this.form.thietbi_n_id,
                  thietbi_id_d:this.switch_.thietbi_d_id,
                  link_code:this.form.connect,
                  port_dich:port_id,
                  chk_port: 0})
            if(rs.error_code=="BSS-00000000"){
               this.switch_.ma_ketnoi =rs.data.MA_KN//await this.getMaKetNoi(port_id)
                this.thietbi.tbi_d_id = this.switch_.thietbi_d_id
               this.transferStatus(3)
               this.$root.toastSuccess('Kết nối thành công !')            
                this.form.edit=true
            }
        }catch(err){
          this.$root.toastError('Kết nối thất bại !')
           console.log(err)
        }
      },
      ketNoiGpon: async function(){
          try{
         let port_id=0
         if(this.gpon.port_id==null){
             this.$root.toastError('Card điều khiển không còn port trống !')
             return          
           }else
            port_id= this.gpon.port_id
           this.form.port_dich= port_id
           let data={
                  madslam_port:this.form.port_DsLam.ma_dslamPort,
                  loaithietbi_id_n:this.form.loai_tbi_n,
                  loaithietbi_id_d:this.huong_kn,
                  thietbi_id_n:this.form.thietbi_n_id,
                  thietbi_id_d:this.gpon.thietbi_d_id,
                  link_code:this.form.connect,
                  port_dich:port_id,
                  chk_port: 0}       
          let rs = await this.$root.context.post(
             "web-ecms/thongtin-ketnoi/ketnoi",data)
            if(rs.error_code=="BSS-00000000"){
              this.gpon.ma_ketnoi =rs.data.MA_KN// await this.getMaKetNoi(port_id)
               this.thietbi.tbi_d_id = this.gpon.thietbi_d_id
              this.transferStatus(3)
               this.$root.toastSuccess('Kết nối thành công !')             
               this.form.edit=true
            }
        }catch(err){
          this.$root.toastError('Kết nối thất bại !')
        }
      },
      ketNoiMane: async function(){
          try{
         let port_id
         if(this.mane.thietbi_d_id==null)
         {
           this.$root.toastError('Chưa chọn thiết bị đích để kết nối!')  
             return
         }       
         if(this.mane.cardMane_id==null)
         {
          this.$root.toastError('Chưa chọn card mane đích để kết nối!')  
          return
         }
       
         if(this.mane.card_mane==1){
           if(this.mane.port_id==null){
             this.$root.toastError('Card điều khiển không còn port trống !')
             return
           }else
            port_id= this.mane.port_id
           }else{
             if(this.mane.port_module==null){
             this.$root.toastError('Module không còn port trống !')
             return
           }else
            port_id= this.mane.port_module
           }
          this.form.port_dich= port_id
          let rs = await this.$root.context.post(
             "web-ecms/thongtin-ketnoi/ketnoi",{
                  madslam_port:this.form.port_DsLam.ma_dslamPort,
                  loaithietbi_id_n:this.form.loai_tbi_n,
                  loaithietbi_id_d:this.huong_kn,
                  thietbi_id_n:this.form.thietbi_n_id,
                  thietbi_id_d:this.mane.thietbi_d_id,
                  link_code:this.form.connect,
                  port_dich:port_id,
                  chk_port: this.mane.card_mane-1})
            if(rs.error_code=="BSS-00000000"){
              this.mane.ma_ketnoi =rs.data.MA_KN//await this.getMaKetNoi(port_id)
               this.thietbi.tbi_d_id = this.mane.thietbi_d_id
              this.transferStatus(3)
               this.$root.toastSuccess('Kết nối thành công !')             
                this.form.edit=true
            }
        }catch(err){
          this.$root.toastError('Kết nối thất bại !')
        }
         
      },
      goKetNoi:async function(){
        try{
          this.$root.showLoading(true);
          let rs = await this.$root.context.post(
             "web-ecms/thongtin-ketnoi/goketnoi",{
                  madslam_port:this.form.port_DsLam.ma_dslamPort,
                  loaithietbi_id_n:this.form.loai_tbi_n,
                  loaithietbi_id_d:this.huong_kn,
                  port_dich:this.form.port_dich})
            if(rs.error_code=="BSS-00000000"){
               //Load lại sơ đồ thiết bị
                this.form.edit=false
                this.transferStatus(1)
               this.$root.toastSuccess('Gỡ kết nối thành công !')              
               await this.resetForm()
            }              
            else
               this.$root.toastError('Gỡ kết nối thất bại !')         
        }catch(err){
          this.$root.toastError('Gỡ kết nối thất bại !')
        }finally{
           this.$root.showLoading(false);
        }
      },
    transferStatus:async  function(status){
        await this.getdsTuRack();
        EventBus.$emit('TrangThaiHuongKetNoi',
            {args:this.args,
             status:status,
             thietbi: this.thietbi
            })
    },
    getdsTuRack: async function(){
       let rs = await this.$root.context.post(
             "web-ecms/thongtin-ketnoi/dsTuRack_theo_tbi",{
               tbi_d_id:this.thietbi.tbi_d_id,
               tbi_n_id:this.thietbi.tbi_n_id
             });
      if(rs.data != null && rs.data.length > 0)
       this.thietbi.turack_1 = rs.data[0].TURACK_ID
      if (rs.data.length > 1)
       this.thietbi.turack_2 = rs.data[1].TURACK_ID
    }
  },
  created() {

  },
  mounted() {
   
  },
  destroyed() {
    EventBus.$off("themMoiBras");
  }
};
</script>
<style>
.disable-btn {
  pointer-events: none;
  cursor: default;
  color: #6c757d !important;
}
</style>
