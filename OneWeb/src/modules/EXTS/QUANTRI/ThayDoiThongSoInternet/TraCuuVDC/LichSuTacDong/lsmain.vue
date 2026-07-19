<template src="../LichSuTacDong/lsmain.html">
</template>

<script>
import service from "../service";
import moment from 'moment';

export default {
  name: "lsmain",
  props: {
  },
  data : function () {
    return {
      logObject : []
    }
  },
  methods: {
    show (data) {
      return new Promise((resolve, reject) => {
        this.$bvModal.show("lichsutacdong")
        this.logObject = [...data]
        this.resolvePromise = resolve
        this.rejectPromise = reject
      })
    },
    processData : async function (){
      for (const item of this.logObject) {
        item.dateTime = moment(item.dateTime).format('MM/DD/YYYY hh:mm:ss')
        let  itemAction = await service.getAction(this,item.actionId)
        if(itemAction.data.length >0){
          item.tacDong = itemAction.data[0].code_name
        }
      }
      this.logObject = this.logObject
    },
    grid1_selectedRowChanged(data){

    },
    closeModal: function () {
      this.$bvModal.hide("lichsutacdong")
    },
    showModal: function() {
      this.processData()
    }
  },
  mounted: async function () {



  },
  watch : {

  }
}
</script>

<style scoped>

</style>
