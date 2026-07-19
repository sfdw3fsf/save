<template>
 <div style="text-align: center">
    <span v-if="data.ckb_xacnhan != 0" class="text-warning one-alert f20"></span>
    <span v-if="data.ckb_xacnhan == 0"></span>
 </div>
</template>

<script>
export default {
  data: () => ({
    data: {}
  })
}
</script>
