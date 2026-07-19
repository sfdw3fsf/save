<template src="./index.html">
</template>

<script>
export default {  
    props:{
        modalId:String,
        isShow: Boolean,
        title: "",
        content:"",
    },
     data() {
        return {           
        } 
    },
    methods : {    
      onClickBtnSaoChep() {
            
            this.$refs['ref-textarea'].select()
            document.execCommand('copy')

            if (window.getSelection) {
                window.getSelection().removeAllRanges()
            }
            else if (document.selection) {
                document.selection.empty()
            }
        }, 
        closeModal: function(data = {isUpdated: false}) {            
            this.$emit('closeModalXemVB', data);
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

