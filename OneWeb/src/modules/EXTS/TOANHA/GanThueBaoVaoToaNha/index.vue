<template src="./index.template.html"></template>
<script>
import PopupGanThueBaoVaoToaNha from './Modal'
export default {
    components: { PopupGanThueBaoVaoToaNha },

    name: 'GanThueBaoVaoToaNha',
    
    methods: {
      showPopup() {
        this.$refs.dlgGanThueBaoVaoToaNha.showModal();
      }
    },
    
    data () {
        return {
        }
    }
}
</script>
