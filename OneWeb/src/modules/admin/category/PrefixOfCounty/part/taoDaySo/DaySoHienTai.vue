<template>
  <div class="box-form">
    <div class="legend-title">Dải số hiên tại</div>
    <ul class="form-control" style="height: 580px; list-style: none; overflow: auto;">
    <li v-for="(item, index) of danhSachDaiCoDinh" :key="index">{{item.somay}}</li>
    </ul>
  </div>
</template>
<script>
import { mapActions, mapState } from 'vuex'
export default {
  data () {
    return {

    }
  },
  computed: {
    ...mapState('prefixOfCounty', [
      'danhSachDaiCoDinh'
    ])
  }
}
</script>
<style scoped>
.form-control li {
  cursor: pointer;
  padding: 3px;
}
.form-control li:hover {
  cursor: pointer;
  background: #BAE7FF;
}
</style>