<template src="./index.html">
</template>

<script>
export default {  
    props:{
        isShow: Boolean,
        dsLS:[],
    },
     data() {
        return {
           //dsLS:[]
        } 
    },
    methods : {    
    
    closeModalLSTB: function(data = {isUpdated: false}) {            
            this.$emit('closeModalLSTB', data);
        }            
    },
    
    watch : {
    
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

