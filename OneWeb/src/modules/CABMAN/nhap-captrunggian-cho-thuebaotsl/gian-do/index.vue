<template src="./index.template.html"></template>
<script>
import {
  SymbolPalettePlugin,
  DiagramTools,
  SelectorConstraints,
  SnapConstraints,
} from "@syncfusion/ej2-vue-diagrams";
export default {
  props: {
    nodes: {
      type: Array,
      default: () => [],
    },
    connectors: {
      type: Array,
      default: () => [],
    },
    refresh: {
      type: Boolean,
      default: false
    }
  },
  watch: {
    nodes: function (value) {
      let diagramInstance = this.$refs.diagram.ej2Instances;
      diagramInstance.clear();
      diagramInstance.refresh();
      //diagramInstance.reset();
      //diagramInstance.doLayout();
    },
    connectors: function (value) {
      let diagramInstance = this.$refs.diagram.ej2Instances;
      diagramInstance.clear();
      diagramInstance.refresh();
      //diagramInstance.reset();
      //diagramInstance.doLayout();
    },
    refresh: function (value) {
      let diagramInstance = this.$refs.diagram.ej2Instances;
      if(diagramInstance.width == "100%")
        diagramInstance.width = "99.9%";
      else
        diagramInstance.width = "100%";
    },
  },
  components: {
    SymbolPalettePlugin,
  },
  data: function () {
    return {
      width: "100%",
      height: "700px",
      tool: DiagramTools.SingleSelect,
      selectedItems: {
        constraints: SelectorConstraints.None,
      },
      snapSettings: { constraints: SnapConstraints.ShowLines },
    };
  },
  computed: {
    nodesData: function () {
      if (this.nodes == null) return [];
      else return this.nodes;
    },
    connectorsData: function () {
      if (this.connectors == null) return [];
      else return this.connectors;
    },
  },
};
</script>
