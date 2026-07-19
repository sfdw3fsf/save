<template>
  <div class="demo-ref">
    <h1>DemoRef</h1>
    <button v-on:click="handleClickInputFile" class="avatar">Thay doi avatar</button>
    <input ref="fileInputAvatar" type="file" style="display: none" />
  </div>
</template>

<script>
export default {
  name: 'demo-ref',
  data() {
    return {}
  },
  methods: {
    handleClickInputFile(e) {
      this.$refs.fileInputAvatar.click()
      console.log()
    }
  }
}
</script>

<style>
.demo-ref {
  margin-top: 100px;
  padding: 100px 30px;
  border: 1px solid #000;
}
.avatar {
  color: #fff;
  padding: 12px 15px #000;
  border: 2px solid #333;
  background-color: #333;
}
.avatar:hover {
  background-color: #fff;
}
</style>


