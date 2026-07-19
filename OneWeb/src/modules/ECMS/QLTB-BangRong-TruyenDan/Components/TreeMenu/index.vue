<template src='./index.html'>
</template>

<script>
import EventBus from "../../../../../utils/eventBus";
export default {
    data(){
         return{}
    },
    methods:{
        nodeClick: function(type){
          let data={
              id: 392,
              turack_id:1
          }
          EventBus.$emit('nodeClick',type,data)
        }
    }
}
</script>
