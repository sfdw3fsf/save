<template>
  <ul v-if="depth==0 || show">
    <li v-if="depth==0">
      <input type="text" :value="txtSearchText" @change="v=>txtSearchText=v.target.value" @keyup="v=>txtSearchText=v.target.value" @keyup.enter="v=>{ txtSearchText=v.target.value; searchNodes(v.target.value); }" />
    </li>
    <li v-if="1==2">{{ upd }}</li>
    <li :class="labelClasses" @click.stop="toggleChildren">
      <a href="#" @click.prevent="treeItemClicked(hsid)">
        <span :class="iconClass" v-if="iconClass"></span>
        <div class="check-action" @click.stop="cbClicked(hsid)">
          <input type="checkbox" class="check" :checked="this.selected" v-if="hascheckbox && hascheckbox==true"> 
          <span class="name" :data-version="upd">{{ label }}</span>
        </div>
      </a>
      <template v-if="showChildren">
        <treemenu @treeItemClicked="treeItemClicked" @cbClicked="cbClicked" v-for="node in nodes" :upd="countUpdate+1" :show="!node.hide || node.isSelected"  :key="node.id" :nodes="node.childs" :label="node.text" :hsid="node.id" :depth="depth + 1" :selected="node.isSelected" :hascheckbox="hascheckbox"></treemenu>
      </template>
    </li>
  </ul>
</template>
<style scoped>
</style>
<script>
export default {
  name: 'treemenu',
  props: [ 'nodes', 'label', 'depth','hsid','hascheckbox','iconClass','selected','show', 'upd' ],
  data() {
    return {
      showChildren: false,
      txtSearchText: '',
      countUpdate: 0,
    }
  },
  mounted() {
    if(this.depth==0) this.toggleChildren();
    // if(this.txtSearchText) { 
    this.searchNodes(this.txtSearchText);
  },
  // updated() {
  //   if(this.depth==0) this.searchNodes(this.txtSearchText);
  // },
  computed: {
    labelClasses() {
      return { 
        'haschild': this.nodes && this.nodes.length>0,
        'open': this.showChildren
      }
    },
    indent() {
      return { transform: `translate(${this.depth * 50}px)` }
    },
  },
  watch: {
    'txtSearchText': {
      handler (newVal) {
        this.searchNodes(newVal);
      }
    }
  },
  methods: {
    searchNodes(kw) {
      for(var n of this.nodes) {
        if(this.matchSearchNode(n,kw)) n.hide = false;
        else n.hide=true;
      }
      this.countUpdate += 1;
    },
    matchSearchNode(node,kw) {
      if(!kw || node.text.indexOf(kw)>=0) {
        this.setShowChildren(node);
        return true;
      }
      var ret = false;
      for(var n of node.childs) {
        if(!kw || this.matchSearchNode(n,kw)) {
          ret = true;
          n.hide = false;
        }
        else n.hide=true;
      }
      return ret;
    },
    setShowChildren(node) {
      for(var n of node.childs) {
        n.hide=false;
        this.setShowChildren(n);
      }
    },
    toggleChildren() {
      this.showChildren = !this.showChildren;
    },
    treeItemClicked(data) {
      this.$emit('treeItemClicked', data);
    },
    cbClicked(data) {
      this.$emit('cbClicked', data);
    },
  }
}
</script>