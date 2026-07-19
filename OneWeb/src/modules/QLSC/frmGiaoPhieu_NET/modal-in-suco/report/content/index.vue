<template src="./index.template.html"></template>
<script>
export default {
  props: {
    datasource: Array
  },
  data() {
    return {
      columnTemplateChon: function () {
          return { 
              template: {
                  template: `
                      <input type="radio" :checked="data.SELECTED" />
                  `,
                  data() {
                      return { 
                          data: { }
                      }
                  },
              }
          }
      },
    }
  }
};
</script>
<style>  
    .in-phieu-suco .e-grid div.e-gridheader div.e-headercontent thead tr.e-columnheader th,
    .in-phieu-suco .e-grid div.e-gridheader div.e-headercontent thead tr.e-columnheader th > div {
        padding: 0 !important;
    }

    .in-phieu-suco .e-grid .e-rowcell,
    .in-phieu-suco .e-grid .e-headertext {
      white-space: break-spaces !important;
      font-size: 10px !important;
    }
</style>

