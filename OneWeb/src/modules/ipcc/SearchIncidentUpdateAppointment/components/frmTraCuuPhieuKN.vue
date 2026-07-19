<template>
<div>
     <p></p>     
</div>
</template>
<script>
export default {
  name: 'frmTraCuuPhieuKN',
  data() {
    return {
      txtMaTB: '',
      kieu_frm: ''
    }
  },
  methods: {
    frmTraCuuPhieuKN(txtMaTB) {
      return txtMaTB
    },
    txtMaTB_KeyUp() {
      return true
    }
  }
}
</script>
<style>
</style>