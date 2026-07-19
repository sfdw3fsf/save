<template src="./index.html">
</template>

<script>
export default {  
    props:{
        isShow: Boolean,
        thuebao_id:null,
    },
     data() {
        return {
           dsKhuyenMai:[]
        } 
    },
    methods : {    
    getdsKhuyenMai: async function (thuebao) {
          console.log(thuebao);
            try {
                const rs = await this.$root.context.get(
                `/web-quantri/quan-ly-danh-ba/danhsach-chitiet-khuyenmai-thuebao?pThueBaoID=${thuebao}&pKieu=2`
                );
                            this.dsKhuyenMai=rs.data;
                //this.dsNhomLKH=this.tmp_dsNhomLKH;//.filter((item)=>item.LOAI===this.param.kieu_id);
            } catch (error) {}
            },
    closeModalKhuyenMai: function(data = {isUpdated: false}) {
            
            this.$emit('closeModalKhuyenMai', data);
        }            
    },
    // created : async function() {

    //         await this.getdsKhuyenMai(this.thuebao_id);        
    // },
    // mounted : async function() {

    //         await this.getdsKhuyenMai(this.thuebao_id);        
    // },
    watch : {
    'thuebao_id': async function(newVal, oldVal) {
          await this.getdsKhuyenMai(newVal); 
      },
   
  }
}
</script>    
<style scoped>
a.disable-btn {
  pointer-events: none;
  cursor: default;
  color: #6c757d !important;
}

#table-scroll {
  height:410px;
  overflow:auto;  
  margin-top:20px;
}
.btn-info {
  color: #0176ff;
  background-color: #bae7ff;
  border-color: #ffffff;
}

.icon {
  font-size: 12px;
}
</style>

