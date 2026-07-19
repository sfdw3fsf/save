<template src="./index.template.html"></template>
<script>
export default {
  props: {
    dataSource: Array,
  },
  data: function () {
    return {
      dsSoi: []
    }
  },
  watch: {
    // dataSource: function (value) {
    //   this.doDefaultSelect();
    // }
  },
  computed: {
    grid: function () {
      return this.$refs.grid.grid;
    }
  },
  methods: {
    getColumnCheckBox: function () {
      return function () {
        return {
          template: {
            template: `<div class="check-action">
                                    <input 
                                        type="checkbox" 
                                        class="check"
                                        @change='updateData' :disabled="!checkBoxEnabled" v-model="checked" />
                                    <span class="name"></span>
                                </div>`,
            computed: {
              checkBoxEnabled: function () {
                this.checked = this.data.CHON == "s1";
                let rs = this.data.READONLY != "s1";
                return rs;
              },
            },
            data: function () {
              return {
                checked: false
              }
            },
            methods: {
              updateData: function (args) {
                let d = this.$parent.$parent.$parent.dsSoi;
                if(this.checked){
                  d.push(this.data.SOI);
                }
                else {
                  let i = d.indexOf(this.data.SOI);
                  if(i > -1){
                    d.splice(i, 1);
                  }
                }
              }
            }
          }
        }
      }
    },
  },
};
</script>
