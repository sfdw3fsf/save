<template src="./index.template.html"></template>
<script>
export default {
  props: {
    datasource: Array
  }
};
</script>
<style>
  .in-phieu-suco div[class*=col-] {
    padding: 0 !important;
  }

  .in-phieu-suco .page {
    border: 1px solid #0066ff;
  }

  .in-phieu-suco .page .header {
    border-bottom: 1px solid #0066ff; height: 51px;
  }

  .in-phieu-suco .header .title {
    line-height: 50px;
    font-weight: bold; 
    font-size: 20px; 
    text-align: center;
    color: #0066ff;
  }

  .in-phieu-suco .body {
    padding: 20px;
  }

  .in-phieu-suco .body .row {
    margin-bottom: 10px !important;
  }

  .in-phieu-suco .body .title {
    font-weight: bold;
    font-size: 15px;
    color: #0066ff;
  }

  .in-phieu-suco .body .content {
    padding-left: 20px;
    line-height: 20px;
  }

  .in-phieu-suco .body .label {
    font-size: 13px;
    font-weight: bold;
  }

  .in-phieu-suco .body .text {
    font-size: 13px;
  }
</style>

