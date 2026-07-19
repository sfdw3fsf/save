<template>
  <header class="header">
    <h1>{{titleheader}}</h1>
  </header>
</template>

<script>
export default {
    name: 'Header',
    props:{
      titleheader: String
    },
};
</script>

<style scoped>
    .header{
        background: black;
        color: #fff;
        text-align: center;
        padding: 10px;
    }
</style>