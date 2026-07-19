<template src="./PopupTaoBangKe.html"></template>

<script>
export default {
  data() {
    return{
        animationSettings: {}
    }
  },
  created(){
      console.log('PopupTaoBangKe')
  },
  methods:{
      show: function () {
      this.$refs.dialogObjTaoBangKe.show();
    },
  }
};
</script>

<style>
</style>