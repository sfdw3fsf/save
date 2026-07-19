<template>
  <a href="#" @click.prevent="btnDel_GV_ButtonClick" class="btn-main btn-main__custom btn pad3 lh14">
    <span class="-ap icon-close"></span>
  </a>
</template>

<script>
export default {
  name: 'columnTemplate',
  data: () => ({
    data: {}
  }),
  mounted () {
    // this.path = this.value

    window.template = this
  },
  methods: {
    btnDel_GV_ButtonClick () {
      console.log('HELLO', this.data)
      this.$parent.$parent.btnDel_GV_ButtonClick(this.data)
    }
  }
}
</script>
<style scoped>
.btn-main {
  background-color: #0176FF !important;
  color: #fff !important;
}
.btn-main:hover {
  background-color: #035FCC !important;
}

.btn-main__custom {
  display: inline-block;
  font-weight: 400;
  line-height: 1.5;
  text-align: center;
  text-decoration: none;
  vertical-align: middle;
  cursor: pointer;
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
  border: 1px solid transparent;
  padding: 0.375rem 0.75rem;
  font-size: 1rem;
  border-radius: 0.25rem;
}
</style>
