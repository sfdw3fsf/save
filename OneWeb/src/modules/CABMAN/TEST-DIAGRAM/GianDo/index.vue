<template src="./index.template.html"></template>
<script>
import {
  SymbolPalettePlugin,
  PortVisibility,
  DiagramContextMenu,
  NodeConstraints,
  DiagramTools,
  ConnectorConstraints,
  SelectorConstraints
} from "@syncfusion/ej2-vue-diagrams";
let nodes = [
  {
    id: "NewIdea",
    height: 80,
    width: 80,
    offsetX: 150,
    offsetY: 150,
    constraints:
      NodeConstraints.Default &
      ~NodeConstraints.Rotate &
      ~NodeConstraints.Drag &
      ~NodeConstraints.Resize &
      ~NodeConstraints.Delete,
    shape: {
      type: "Flow",
      shape: "SummingJunction",
    },
    ports: [
      {
        id: "node_1_port_right",
        offset: { x: 1, y: 0.5 },
        shape: "Circle",
        visibility: PortVisibility.Hidden,
      },
    ],
    annotations: [
      {
        content: "COT_TEST_01\r\n75 Đinh Tiên Hoàng",
        margin: { bottom: 80 },
      },
    ],
  },
  {
    id: "Meeting",
    height: 80,
    width: 80,
    offsetX: 450,
    offsetY: 150,
    constraints:
      NodeConstraints.Default &
      ~NodeConstraints.Rotate &
      ~NodeConstraints.Drag &
      ~NodeConstraints.Resize &
      ~NodeConstraints.Delete,
    shape: { type: "Flow", shape: "SummingJunction" },
    ports: [
      {
        id: "node_2_port_left",
        offset: { x: 0, y: 0.5 },
        shape: "Circle",
        visibility: PortVisibility.Hidden,
      },
    ],
    annotations: [
      {
        content: "COT_TEST_01\r\n299 Đinh Tiên Hoàng",
        margin: { bottom: 80 },
      },
    ],
  },
];
let connectors = [
  {
    id: "connector1",
    //type: "Straight",
    sourceID: "NewIdea",
    targetPortID: "node_1_port_right",
    targetID: "Meeting",
    targetPortID: "node_2_port_left",
    style: { strokeWidth: 2, strokeColor: "#757575", strokeDashArray: "2,2" },
    targetDecorator: { shape: "None" },
    constraints:
      ConnectorConstraints.Default &
      ~ConnectorConstraints.Drag &
      ~ConnectorConstraints.AllowDrop &
      ~ConnectorConstraints.Delete &
      ~ConnectorConstraints.DragSegmentThumb,
  },
];
export default {
  components: {
    SymbolPalettePlugin
  },
  provide: {
    diagram: [DiagramContextMenu],
  },
  data: function () {
    return {
      width: "700px",
      height: "500px",
      nodes: nodes,
      connectors: connectors,
      tool: DiagramTools.SingleSelect,
      contextMenuSettings: {
        show: true,
        items: [
          {
            text: "Liên kết tới cột tiếp theo",
            id: "MENU_ITEM_LK_COT_TT",
            //ContextMenu can be visible based on the target in which you open the ContextMenu.
            target: ".e-diagramcontent",
            iconCss: "fa fa-arrows-h",
          },
          {
            text: "Liên kết tới cột thuộc tuyến khác",
            id: "MENU_ITEM_LK_COT_TK",
            target: ".e-diagramcontent",
            iconCss: "fa fa-exchange",
          },
          {
            id: "MENU_ITEM_SEPARATOR_1",
            separator: true,
          },
          {
            text: "Xóa cột",
            id: "MENU_ITEM_XOA_COT",
            target: ".e-diagramcontent",
            iconCss: "fa fa-trash-o",
          },
          {
            text: "Hủy liên kết",
            id: "MENU_ITEM_LK_HUY",
            target: ".e-diagramcontent",
            iconCss: "fa fa-trash-o",
          },
          {
            separator: true,
          },
          {
            text: "Thuộc tính",
            id: "MENU_ITEM_THUOC_TINH",
            target: ".e-diagramcontent",
            iconCss: "fa fa-info-circle",
          },
          {
            text: "Đảo chiều liên kết",
            id: "MENU_ITEM_LK_DAOCHIEU",
            target: ".e-diagramcontent",
            iconCss: "fa fa-random",
          },
        ],
        // Hides the default context menu items
        showCustomMenuOnly: true,
      },
      selectedItems: {
        constraints: SelectorConstraints.None,
      },
      snapSettings: { constraints: 0 },
      contextMenuOpen: function (args) {
        if (this.selectedItems.nodes.length > 0) {
          args.hiddenItems.push("MENU_ITEM_LK_HUY");
          args.hiddenItems.push("MENU_ITEM_LK_DAOCHIEU");
        } else if (this.selectedItems.connectors.length > 0) {
          args.hiddenItems.push("MENU_ITEM_SEPARATOR_1");
          args.hiddenItems.push("MENU_ITEM_LK_COT_TT");
          args.hiddenItems.push("MENU_ITEM_LK_COT_TK");
          args.hiddenItems.push("MENU_ITEM_XOA_COT");
          args.hiddenItems.push("MENU_ITEM_THUOC_TINH");
        } else {
          args.cancel = true;
        }
      },
      contextMenuClick: function (args) {
        let value = "";
        if (this.selectedItems.nodes.length > 0)
          value = "node: " + this.selectedItems.nodes[0].id;
        else if (this.selectedItems.connectors.length > 0)
          value = "connector: " + this.selectedItems.connectors[0].id;
        console.log(
          "itemClick: '" + args.element.id + "', arg: {" + value + "}"
        );
      },
    };
  },
};
</script>
