<template src="./index.html"></template>
<script>
import DauNoiCap from './DauNoiCap'
export default {
  components:{DauNoiCap},
  name: "index",
  data: function (){
    return {
      capObject: {
        CAP_ID:''
      },
    }
  },
  methods:{
    btnShowDauNoiCap(){
      this.capObject.CAP_ID = 89077
      this.capObject.MIDSPAN_ID = 0
      this.$bvModal.show('popupDauNoiCap')
    }
  },
  async mounted()  {
    try {
      this.$store.dispatch('setHeader', {
        title: 'Đấu nối cáp'
      })
    } catch (err) {
      console.log(err)
    }
  }
}
</script>

<style scoped>

</style>
