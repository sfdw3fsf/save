<template src="./index.html"> </template>
<script>
export default {
  data() {
    return {
      dsThueBao:[],
      currentItem:{}
    };
  },
    methods: {
      bindData:async function(port_id,loai){
          this.currentItem = {
            LOAI : loai,
            PORT_ID : port_id
          }
           await this.getdsThueBao()
      },
      getdsThueBao: async function() {
        try {
          this.$root.showLoading(true);
          this.dsThueBao=[]
           let rs = await this.$root.context.post(
             "web-ecms/thietbi-truyendan/port-switch-theo-loai",{             
                 loai:this.currentItem.LOAI,
                 port_id:this.currentItem.PORT_ID
                 }                     
          );  
          if(rs.data!=null && rs.data.length>0)
          this.dsThueBao =rs.data            
        } catch (error) {
          this.$root.toastError("Có lỗi xảy ra")
        } finally {
          this.$root.showLoading(false);
        }
      }
  },
  created: async function() {
  
  },
  mounted() {  
  },
  destroyed() {
  }
};
</script>
