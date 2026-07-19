<template>
    <ul>
      <li :class="labelClasses" @click.stop="toggleChildren">
        <a href="#" @click.prevent="treeItemClicked(hsid)">
          <span :class="iconClass" v-if="iconClass"></span>
          <div class="check-action" @click.stop="cbClicked(hsid)">
            <input type="checkbox" class="check" :checked="this.selected" v-if="hascheckbox && hascheckbox==true"> 
            <span class="name">{{ label }}</span>
          </div>
        </a>
        <template v-if="showChildren">
          <treemenu @treeItemClicked="treeItemClicked" @cbClicked="cbClicked" v-for="node in nodes" :key="node.id" :nodes="node.childs" :label="node.text" :hsid="node.id" :depth="depth + 1" :selected="node.isSelected" :hascheckbox="hascheckbox"></treemenu>
        </template>
      </li>
  </ul>
</template>
<style scoped>
</style>
<script>
export default {
  name: 'treemenu',
  props: [ 'nodes', 'label', 'depth','hsid','hascheckbox','iconClass','selected' ],
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
        'haschild': this.nodes && this.nodes.length>0,
        'open': this.showChildren
      }
    },
    // ischeck: { 
    //   get: () => {return this.selected?this.selected:false; },
    //   //set: (val,val1) => { console.log(val,val1);this.selected=val; }
    // },
    // iconClasses() {
    //   return this.iconClass;
    // },
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
    cbClicked(data) {
      this.$emit('cbClicked', data);
      // if(this.nodes && this.nodes.length>0) {
      //   this.checkChange(this.nodes,this.ischeck);
      // }
    },
    // checkChange(childs,val) {
    //   for(var n of childs) {
    //     console.log(n,val);
    //     n.isSelected=val;
    //     this.checkChange(n.childs,val);
    //   }
    // },
  }
}
</script>