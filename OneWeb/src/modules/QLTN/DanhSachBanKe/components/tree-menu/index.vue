<template src="./index.html"></template>
<style scoped src="./style.scss">
</style>
<script>
import moment from "moment";
import $ from "jquery";

export default {
  name: "TreeMenu",
  components: {},
  created: async function () {
    $(document).on('click','.tree-label', (e)=> {
      $(document).find('.tree-label').removeClass('active');
      $(e.target).addClass("active");
      //  if (e.target !== this) return;
      //  console.log($(e.target))
    })
    // $(document).on('click', '.tree-plusDuy a', function (e) {
    //   console.log(e.target)
    //   if (e.target !== this) return;
    //   //  console.log(1111)
    // });
  },
  props: [ 'nodes', 'label', 'depth', 'data' ],
  data() {
     return {
       showChildren: false,
       itemSelected: null
      }
  },
  computed: {
    iconClasses() {
      return {
        'fa-plus-square-o': !this.showChildren,
        'fa-minus-square-o': this.showChildren
      }
    },
    labelClasses() {
      return { 'has-children': this.nodes }
    },
    indent() {
      return { transform: `translate(${this.depth * 50}px)` }
    }
  },
  methods: {
    toggleChildren() {
      console.log(this.label)
      console.log(this.data)
      this.itemSelected = this.data;
      this.showChildren = !this.showChildren;
      this.$emit("clickTree", this.data)
    },
    onClickTree() {
      this.$emit("clickTree", this.data)
    }
  }
};
</script>