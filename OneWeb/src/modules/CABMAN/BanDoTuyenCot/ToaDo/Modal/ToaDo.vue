<template src="./ToaDo.html"></template>
<script>
import './../../../../../utils/gmaps'
import GMap from '../../Gmap/GMap.vue'
import GraphNode from '../../structures/GraphNode'
import ModalChonDiaChi from "../../ChonDiaChi/Modal";
export default {
  name: "ToaDo",
  props: ["modalId", "data"],
  created: async function () {
  },
  components:{GMap,ModalChonDiaChi},
  computed: {},
  watch:{
  nodes: function(val){
     let map = this.$refs.mapcontrol
      map.clear()
      map.setNodes(this.nodes.slice(0))
      map.setLocationEx(null)
  }
  },
  methods: {
    focusMyElement: async function () {   
      this.nodes=[]  
      this.edit=false;
       
      let macdinh= await this.getThongTinBanDo()
      let diachi=""
      if (this.data == null) {
        this.toado=macdinh
      }
      else {
        if(this.data.toado=="0,0")
      this.toado  =macdinh
      else{
      this.toado  = this.data.toado
      }

      diachi= this.data.diachi!=""?this.data.diachi:"Vị trí mặc định theo tỉnh"
      }
      this.query= this.toado 

      var node_n = this.taoDuLieuNode('COT_1',this.toado,diachi,101)	
      this.nodes.push(node_n)

    },
    getThongTinBanDo: async function () {
      try {
        var rs = await this.$root.context.get(
          "/web-cabman/bando_matdo/thongtin_bando"
        );
        if (rs.data != null && rs.data.length > 0){
          return rs.data[0].C_LATITUS +','+rs.data[0].C_LONGTITUS;}
         else
          return '0,0' 
      } catch (error) {
      } finally {
        this.$root.showLoading(false);
      }
    },
    taoDuLieuNode:function(id,toado,diachi,icon){
     let node  = new GraphNode()
     try{
     if(toado!=null){
        node.id = id
        if(diachi!="")
        node.tooltipText = diachi
        node.setPosition(toado)       
        node.setIcon(icon)
        node.draggable = true
        }
     return node
     }catch(e){
        return node
     }
    },
   xacnhan:async function(){
    try {
       this.$root.showLoading(true);
       this.$emit('xacnhan',this.toado)
       this.$bvModal.hide(this.modalId)
      } catch (error) {
        this.$toast.error(error.messeage);
      } finally {
       this.$root.showLoading(false);
      }
    },
   print: function(){
           var divContents = document.getElementById("chonbando").innerHTML;
            var a = window.open('', '', 'height=500, width=500');
            a.document.write('<html>');
            a.document.write('<body > <h1>Bản đồ<br>');
            a.document.write(divContents);
            a.document.write('</body></html>');
            a.document.close();
            a.print();
   },
   luuToaDoBanDo: function(nodes){
    if(nodes!=null){
     let node=nodes[0]
     this.toado=node.lat+","+node.lng
    }
   },
   timkiem:function(event){
     try{
      if(event.key=="Enter"){     
      this.isHere()  
      }
     }catch(e){console.log('err:',e)}
   },
  checkToaDo:function(){
    if(this.query=="")
    return false
    else
    {
    if(isNaN(parseFloat(this.query.split(',')[0])) ||isNaN(parseFloat(this.query.split(',')[1])))
    return false
    }
    return true
  },
  getDiaChi: function(diachi){
    this.query= diachi.diachimoi
  },
   isHere:function(){
    if(this.checkPoint2(this.query))
    {
      if(this.nodes.length==2)
       this.nodes.splice(1, 1);
       var node = this.taoDuLieuNode('COT_2',this.query,"Địa chỉ mới",101)	
      this.nodes.push(node)
      return;
    }else{
    const address = this.query
    const geocoder = new window.google.maps.Geocoder();
         geocoder
    .geocode({ address: address })
    .then((response) => {
      if (response.results[0]) {
     
          this.point=response.results[0].geometry.location.lat()+","+
                          response.results[0].geometry.location.lng()
 // console.log('Địa chỉ:',this.point)
      this.address=response.results[0].formatted_address;
       if(this.nodes.length==2)
       this.nodes.splice(1, 1);
       var node = this.taoDuLieuNode('COT_2',this.point,this.address,101)	
      this.nodes.push(node)
      // let map = this.$refs.mapcontrol
      //   map.setLocation(response.results[0].geometry.location.lat(),
      //   response.results[0].geometry.location.lng())
      } else {
         this.$root.toastInfo("Không tìm thấy");
      }
    })
    .catch((e) =>  this.$root.toastError("Lỗi phát sinh: " + e));
    }
    },
    checkPoint2:function(text){
     if(text == null || text == "")
      return false;
     let [lat,lng] = text.split(",");
     if(lat == null || lng == null)
     return false;
     if(!Number.isNaN(lat) && !Number.isNaN(lng))
     return true;
     return false;
    }

  },
  data: function () {
    return {    
      toado:"",
      query:"",
      nodes:[],
      point:"",
      address:""
    };
  }, 
};
</script>
<style>
div#popupTCTTTB___BV_modal_outer_ {
  z-index: 10009 !important;
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