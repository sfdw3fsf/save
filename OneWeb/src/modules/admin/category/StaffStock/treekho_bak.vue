<template>
    <ul>
      <li :class="labelClasses" @click.stop="toggleChildren">
        <a href="#" @click.prevent="treeItemClicked(hsid)">
          <span :class="iconClass" v-if="iconClass"></span>
          <div class="check-action" @click.stop="cbClicked(hsid)">
            <input type="checkbox" class="check" v-model="ischeck" v-if="hascheckbox && hascheckbox==true"> 
            <span class="name">{{ label }}</span>
          </div>
        </a>
        <template v-if="showChildren">
          <treekho @treeItemClicked="treeItemClicked" v-for="node in nodes" :key="node.kho_id" :nodes="node.$nodes" :label="node.ten_kho" :hsid="node.kho_id" :depth="depth + 1" :selected="node.isSelected" :hascheckbox="hascheckbox"></treekho>
        </template>
      </li>
  </ul>
</template>
<style scoped>
</style>
<script>
export default {
  name: 'treekho',
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
    ischeck: { 
      get: () => {return this.selected; },
      set: (val) => { this.selected=val; }
    },
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
      this.ischeck = !this.ischeck;
      this.$emit('treeItemClicked', data);
      if(this.nodes && this.nodes.length>0) {
        this.checkChange(this.nodes,this.ischeck);
      }
    },
    
    checkChange(childs,val) {
      for(var n of childs) {
        n.isSelected=val;
        checkChange(n.childs);
      }
    },
  }
}
</script>