<template src="./AssignManagement.html">
</template>
<script>
import GanNV from '@/modules/contract/setup/AssignManagement/AssignManagement.vue'
export default {
  name: "AssignManagement",
  props:{
    id: String,
  },
  components: {GanNV},
  data: function () {
    return {
      vtimkiem_hdkh_id: null,
      gan_hd: null,
    }
  },
}
</script>

<style>
.modal-assign-management .main-wrapper {
  position: unset !important;
  height: calc(100vh - 100px);
}
@media (min-width: 1200px) {
  .modal-xl {
    max-width: 95% !important;
  }
}
</style>
