<template>
    <ul>
      <li :class="labelClasses" @click.stop="toggleChildren">
        <a href="#" @click.prevent="treeItemClicked(hsid)"> {{ label }}</a>
        <template v-if="showChildren">
          <treemenu @treeItemClicked="treeItemClicked" v-for="node in nodes" :key="node.hoso_id" :nodes="node.$nodes" :label="node.doituong" :hsid="node.hoso_id" :depth="depth + 1"></treemenu>
        </template>
      </li>
  </ul>
</template>
<script>
export default {
  name: 'treemenu',
  props: [ 'nodes', 'label', 'depth','hsid' ],
  data() {
     return {
       showChildren: false
     }
  },
  mounted() {
    if(this.depth==0) this.toggleChildren();
  },
  computed: {
    labelClasses() {
      return { 
        'haschild': this.nodes,
        'open': this.showChildren
       }
    },
    iconClasses() {
      return {
        'icon nc-icon-glyph ui-1_home-simple':this.depth==1,
        'icon nc-icon-glyph files_single-content-02': this.depth==0,
      }
    },
    indent() {
      return { transform: `translate(${this.depth * 50}px)` }
    },
  },
  methods: {
    toggleChildren() {
       this.showChildren = !this.showChildren;
    },
    treeItemClicked(data) {
      this.$emit('treeItemClicked', data);
    },
  }
}
</script>