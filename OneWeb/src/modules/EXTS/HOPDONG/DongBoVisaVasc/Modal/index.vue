<template src="./index.html"></template>
<script>
import {minLength,maxLength,numeric, required} from 'vuelidate/lib/validators'

export default {
  name: "chonIP",
  props: ["modalId"],
  computed: {},
  destroyed() {},
  watch: {
   chon_ip: async function(val){
    await this.lay_IP()
   }

  },
  methods: {
    setData: function(data){
      console.log('data chọn Ip:',data)
        if(data!=null){
          this.bras_id = data.bras_id
          this.in_tocdo_id = data.in_tocdo_id
        }
    },
    focusMyElement: async function () {
      try{
        this.$root.showLoading(true)
        await this.getDsBras()
        this.kt_load = true;
        await this.lay_IP()
      }catch(er){}
      finally{
         this.$root.showLoading(false)
      }
    },
    bras_change:  function () {
      if(this.kt_load)
       this.lay_IP()
    },
    lay_IP:async function(){
     if(this.dsBras.length>0){
       let ma_td = 0
       let s=''
       s = await this.checkMapId_new(this.in_tocdo_id,25) //this.checkMapId('profile_id','css.tocdo_adsl','tocdo_id = '+this.in_tocdo_id)
       if(s != '-1')
         ma_td = parseInt(s)        
      let vbras_vdc_id = -1
       s= await this.checkMapId_new(this.bras_id,22) //this.checkMapId('bras_vdc_id','css.bras','bras_id = '+this.bras_id)
       if(s!='-1')
         vbras_vdc_id = parseInt(s) 
      let result = [];
      if(this.chon_ip == 2|| this.chon_ip== 1){
       // Lấy IP tĩnh
       result = await this.$parent.getIp4(vbras_vdc_id, ma_td); //vdc.LayIPTinh(vbras_vdc_id, ma_td);
      }else{
        // Lấy Ip 6 
        result = await this.$parent.getIpsV6(vbras_vdc_id,ma_td);
      }
      let temp = [];
      if(result != null && result.length >0){
        for(let i= 0; i< result.length; i++){
          let obj =
          {
            stt : i +1,
            ip : result[i] 
          }
        temp.push(obj);
        }
        this.dsIp = [...temp] ;
      }
      
     }
    },
  checkMapId_new: async function(map_id,no){
      try {
        let rs = await this.$root.context.get(
          "/web-ccdv/hoanthien-hs-tachnhap-tbao/check_map_id",{
            map_id:map_id,
            no:no
          }
        );
        if (rs.data != null) {
        return rs.data[0].id
        }
      } catch (error) {
        return 0
      } finally {
      }    
    },
    // checkMapId: async function(id_neo,table,filter){
    //       try {
    //     var rs = await this.$root.context.post(
    //       "/web-hopdong/goi-da-dichvu/fn_map_id",{
    //         id_neo:id_neo,
    //         table:table,
    //         dk:'where '+filter
    //       }
    //     );
    //     if(rs.data!=null)
    //      return rs.data  
    //      else
    //      return ''   
    //   } catch (error) {
    //      return '' 
    //   } finally {
    //   }
    // },
    getDsBras: async function(){
     try {
        var rs = await this.$root.context.get(
         "/web-ecms/catchuyendslam/lietkeDsBras")
        if(rs.data!=null){
         this.dsBras = rs.data 
         this.bras_id = rs.data[0].BRAS_ID
        }        
      } catch (error) {
      } finally {
      }
    },
    hiddenModal:function(){
     Object.assign(this.$data, this.$options.data.call(this));
    },
    chon:async function(){
      try{
      if(this.dsIp ==[]){
        this.$root.toastError('Chưa có danh sách IP để chọn!')
        return
      }
      if(await this.KIEMTRA_IP_TB_FTTH(this.diachi_ip,this.chon_ip)> 0){
         this.$root.toastError("Địa chỉ IP " + this.diachi_ip + " đã được cấp cho thuê bao khác. Bạn hãy chọn IP khác !")
      }
      this.$emit('chonIP',this.diachi_ip)
      this.$bvModal.hide(this.modalId)
      }catch(e){
         this.$root.toastError('Có lỗi trong quá trình chọn IP.')
      }
    },
    KIEMTRA_IP_TB_FTTH:async function(diachi_ip,chon_ip){
       try {
        let rs = await this.$root.context.get(
          "/web-ccdv/dongbo-visa-vasc/kiemtra_ip_tb_ftth",{
            ip:diachi_ip,
            kieu:chon_ip
          }
        );
      return rs.data
      } catch (error) {
        return -1
      } finally {
      }
    },
    gridip_selectedRowChanged(item){
      if (item != null) {
        this.diachi_ip = item.ip;
      };
    }
 },
  data() {
    return {
     chon_ip:1,
     dsBras:[],
     dsIp:[],
     isDisable:true,
     in_tocdo_id:-1,
     bras_id:-1,
     diachi_ip:'',
     txtIp4LAN:'',
     txtIp4WAN:'',
     txtIp6LAN:'',
     kt_load:false
    }
  },
   validations: {}
};
</script>
<style>
</style>