<template src="./index.template.html"></template>
<script>
import PopupGanDuAnIMS from './Modal'
export default {
    components: { PopupGanDuAnIMS },

    name: 'GanDuAnIMS',
        
    methods: {
      showPopup() {
        this.$refs.dlgGanDuAnIMS.showModal();
      }
    },
    
    data () {
        return {
          toanha_id: 1
        }
    }
}
</script>
