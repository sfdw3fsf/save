<template src="./index.template.html"></template>
<script>
import './../../../../utils/gmaps'
import GMap from '../components/GMap.vue'
import GraphNode from '../structures/GraphNode'
import GraphPolyline from '../structures/GraphPolyline'
import GraphPolylines from '../structures/GraphPolylines'
export default {
  props:["gian_do_cot"],
  name: 'rightPaneContent',
  components: { GMap },
  data () {
    return {
      showtabpane: false,
      nodes: [],
      polylines: [],
      cotsau_id:null
    }
  },
  computed: {  
      getgian_do_cot () {
      return this.gian_do_cot
    }
  },
  watch: {
    getgian_do_cot: function (val) {
      let data = val
      var map = this.$refs.map
      var nodes = []
      var polylines = []
      map.clear()
      for (let i = 0; i < data.length; i++) {
        //Tạo node
        var node_n = this.taoDuLieuNode(data[i])	
	      let objIndex = nodes.findIndex(obj => obj.id === node_n.id)
        if (objIndex < 0) nodes.push(node_n)
        if(data[i].COTSAU_ID!=null){
         var polyline = new GraphPolyline()
         let lat = parseFloat(data[i].TOADO.split(',')[0])
         let lng = parseFloat(data[i].TOADO.split(',')[1])
         polyline.path.push({id: data[i].COT_ID, lat: lat, lng: lng})
         //Lấy cột sau để tạo liên kết
         let cotD = this.getcotByID(data[i].COTSAU_ID);
         let lat2 = parseFloat(cotD.TOADO.split(',')[0])
         let lng2 = parseFloat(cotD.TOADO.split(',')[1])
         polyline.path.push({id: cotD.COT_ID, lat: lat2, lng: lng2})

        polyline.id = 'LINK_' + data[i].COT_ID+"_"+cotD.COT_ID
        polyline.label = data[i].KC_COTSAU==null?"0 m":data[i].KC_COTSAU+" m"
        polyline.source = data[i].COT_ID
        polyline.dest = cotD.COT_ID
        polyline.style = "0-1-4-0"
           objIndex = polylines.findIndex(obj => obj.id === polyline.id)
        if (objIndex < 0) polylines.push(polyline)

        var node_d =this.taoDuLieuNode(cotD)
    
       objIndex = nodes.findIndex(obj => obj.id === node_d.id)
        if (objIndex < 0) nodes.push(node_d)        }
      }
     map.setNodes(nodes.slice(0))
 
      map.setPolylines(polylines.slice(0)) 
      map.setLocationEx(null)
       this.nodes = nodes
      this.polylines = polylines
    }
  },
  methods: {
    taoDuLieuNode:function(cot){
     let node  = new GraphNode()
     if(cot!=null){
        node.id = cot.COT_ID
        node.label = cot.TEN_COT 
        node.style = cot.ICON_STYLE
        node.symbol = cot.SO_HIEU + ' '+ cot.DIACHI
        node.tooltipText = cot.TEN_COT
        node.setPosition(cot.TOADO)       
        node.setIcon(cot.ICON_STYLE)
        node.draggable = true
        }
     return node
    },
     getcotByID: function (Cot_id) {
      return this.gian_do_cot.filter((x) => x.COT_ID == Cot_id)[0];
    },
    getPosition: function (marker) {
      return {
        lat: parseFloat(marker.lat),
        lng: parseFloat(marker.lng)
      }
    },
    kc2cot:function(obj){
    try{
    if(obj !=null){

         this.$emit('Kc2Cot',obj);      
    }   
    }catch(e){} 
    },
    create_UUID () {
      var dt = new Date().getTime()
      var uuid = 'xxxxxxxx_xxxx_4xxx_yxxx_xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
        var r = (dt + Math.random() * 16) % 16 | 0
        dt = Math.floor(dt / 16)
        return (c === 'x' ? r : (r & 0x3 | 0x8)).toString(16)
      })
      return uuid
    },
    async xoaCap (id) {
      const vdoituongid = id.split('_')
      if (vdoituongid[2] === '0') {
        this.$confirm(`Bạn có muốn xóa cáp đang chọn không ?`,
          'Giản đồ mạng cáp',
          {
            confirmButtonText: 'Có',
            cancelButtonText: 'Không'
          }).then(() => {
          this.loading(true)
          this.api_set_xoa_cap({cap_id: vdoituongid[1], xoa_batbuoc: 1}).then((rep) => {
            this.$refs.map.reload()
            // Call Reload  gian do
            this.loading(false)
            this.$toast.info(rep.message)
          }).catch((e) => {
            this.loading(false)
            this.$toast.error(e.message)
          })
        }).catch((e) => {})
      } else {
        if (vdoituongid[2] !== '0') {
          this.$confirm(`Bạn có muốn xóa cáp mid-span đang chọn không ?`,
            'Giản đồ mạng cáp',
            {
              confirmButtonText: 'Có',
              cancelButtonText: 'Không'
            }).then(() => {
          }).catch((e) => {})
        }
      }
    },
    async daochieuCap (id) {
      const vdoituongid = id.split('_')
      this.$confirm(`Sau khi đảo chiều cáp thành công, thông tin đấu nối vào 2 đầu của cáp hiện tại sẽ bị gỡ bỏ. Bạn có chắc muốn thực hiện ?`,
        'Giản đồ mạng cáp',
        {
          confirmButtonText: 'Có',
          cancelButtonText: 'Không'
        }).then(() => {
        this.loading(true)
        this.api_set_daochieu_cap({cap_id: vdoituongid[1]}).then((rep) => {
          this.$refs.map.reload()
          // Call Reload  gian do
          this.loading(false)
          this.$toast.info(rep.message)
        }).catch((e) => {
          this.loading(false)
          this.$toast.error(e.message)
        })
      }).catch((e) => {})
    },
    async xoaKetcuoi (id) {
      const vdoituongid = id.split('_')
      this.$confirm(`Bạn có muốn xóa kết cuối đang chọn không ?`,
        'Giản đồ mạng cáp',
        {
          confirmButtonText: 'Có',
          cancelButtonText: 'Không'
        }).then(() => {
        this.loading(true)
        this.api_set_xoa_ketcuoi({ketcuoi_id: vdoituongid[1], xoa_batbuoc: 1}).then((rep) => {
          // this.$refs.map.reload()
          // Call Reload  gian do
          this.loading(false)
          this.$toast.info(rep.message)
        }).catch((e) => {
          this.loading(false)
          this.$toast.error(e.message)
        })
      }).catch((e) => {})
    },
    async luuToaDoBanDo (nodes, polylines) {
      if (polylines.length === 0 && nodes.length === 0) return
      this.$confirm(`Bạn có muốn lưu tọa độ cáp và kết cuối ?`,
        'Giản đồ mạng cáp',
        {
          confirmButtonText: 'Có',
          cancelButtonText: 'Không'
          // type: 'warning'
        }).then(() => {
        try {
          this.loading(true)
          if (nodes && nodes.length > 0) {
           // const myData = JSON.stringify(nodes)
            this.$emit('luuToaDoBanDo',nodes);
          }
          this.loading(false)
        } catch (e) {
          this.loading(false)
          this.$toast.error(e.message)
        }
      }).catch(() => {})
    },
    isDuplicateKey (id, arr) {
      let isDupl = false
      for (let i = 0; i < arr.length; i += 1) {
        const obj = arr[i]
        if (obj.info.id === id) {
          isDupl = true
          break
        }
      }
      return isDupl
    },
    getUniqueId (prefix) {
      var d = new Date().getTime()
      d += (parseInt(Math.random() * 100)).toString()
      if (undefined === prefix) {
        prefix = 'uid-'
      }
      d = prefix + d
      return d
    },
    setZoomMap: function(step){
     this.$refs.map.setZoomMap(step)
    },
    //#region "Hàm xử lý"
    node_action: function(data){
     if(data!=null){      
       let value= data.value
       if(data.type==0){
         let cot_id_n= value.split("_")[1];
         let obj={
          cot_id:cot_id_n,
          type:"MENU_ITEM_LK_COT_TT"
          }   
         this.$toast.info("Chọn cột cần liên kết tới\r\nNhấn ESC để hủy");   
         this.$emit('xacnhan',obj);    
        }
       //Chọn cột khác tuyến  
        if(data.type==1){
          let cot_id_n= value.split("_")[1];
          let obj={
          cot_id:cot_id_n,
          type:"MENU_ITEM_LK_COT_TK"}
            this.$emit('xacnhan',obj);
         this.$root.$bvModal.show('popupDsCot');
        }
        if(data.type==2){
           let cot_id_n= value.split("_")[1];
          let obj={
          cot_id:cot_id_n,
          type:"MENU_ITEM_XOA_COT"
        }
         this.$emit('xacnhan',obj);
         this.$root.$bvModal.show('modalXoa');
        }
         if(data.type==3){
          let cot_id_n= value.split("_")[1];
          let obj={
          cot_id:cot_id_n,
          type:"MENU_ITEM_THUOC_TINH"
          } 
         this.$emit('xacnhan',obj); 
        }
     }
    },
    polyline_action: function(data){
     if(data!=null){
       let value= data.value
       if(data.type==0){
         let cot_id_n= value.split("_")[2];
         let obj={
          cot_id:cot_id_n,
          type:"MENU_ITEM_LK_HUY"
          }   
         this.$emit('xacnhan',obj);
         this.$root.$bvModal.show('modalHuy'); 
        }
       //Chọn cột khác tuyến  
        if(data.type==1){
         //this.$root.$bvModal.show('popupDsCot');
        }
     }
    },
    getcotSau: function(cot_id){
     if(cot_id !=null)
      this.$emit('getcotSau',cot_id);    
    }
    //#endregion
  }
}
</script>

<style> 
</style>
