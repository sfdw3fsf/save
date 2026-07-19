<template>
    <ul>
      <li :class="[labelClasses]"  @click.stop="toggleChildren">
        <a href="#" @click.prevent="treeItemClicked(hsid)"><span :class="iconClasses"></span> {{ label }} </a>
        <template v-if="showChildren">
          <treemenu :class="activeNode(node.hoso_id)" @treeItemClicked="treeItemClicked" v-for="node in nodes" :key="node.hoso_id" :nodes="node.$nodes" :label="node.doituong" :hsid="node.hoso_id" :depth="depth + 1" ></treemenu>
        </template>
      </li>
  </ul>
</template>
<script>
export default {
  name: 'treemenu',
  props: [ 'nodes', 'label', 'depth','hsid', 'showChildren' ],
  data() {
     return {
       // showChildren: false
       currentNode: "-1"
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
      this.currentNode = data
      //this.activeNode(data)
      this.$emit('treeItemClicked', data);
    },
    activeNode(data){
      return data == this.currentNode ? 'active' : ''
    }
  }
}
</script>
<style scoped>
.active{
  background-color: #F9E1A9 !important;
}
</style>