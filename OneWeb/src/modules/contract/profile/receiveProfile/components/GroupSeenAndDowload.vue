<template>
<div style="text-align: center">
    <!-- <a href="#" @click.prevent="btnXemFile_ButtonClick" class="btn btn-main btn-main__custom pad3">
        <span class="one-search lh14"></span>
    </a> -->
    <a href="#" @click.prevent="btnDowLoad_ButtonClick($event)" class="btn btn-main btn-main__custom pad3 w24 Dow">
        <span class="icon nc-icon-glyph arrows-2_square-download"></span>
    </a>
</div>
</template>

<script>
export default {
  name: 'GroupSeenAndDowload',
  data: () => ({
    data: {}
  }),
  mounted () {
    // this.path = this.value

  },
  methods: {
    btnXemFile_ButtonClick () {
      this.$parent.$parent.btnXemFile_ButtonClick(this.data)
    },
    btnDowLoad_ButtonClick (e) {
      this.$parent.$parent.btnDowLoad_ButtonClick(e, this.data)
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
