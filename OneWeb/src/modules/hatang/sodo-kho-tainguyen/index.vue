<template src="./index.template.html"></template>

<script>
import {
  NodeConstraints,
  SnapConstraints,
  SelectorConstraints,
  ConnectorConstraints,
  AnnotationConstraints,
  RadialTree,
  DataBinding,
  SymmetricLayout,
  DiagramContextMenu,
  DiagramTools,
  DiagramConstraints,
} from "@syncfusion/ej2-vue-diagrams";
import ComponentDsTbLienQuan from "./components/ds-thietbi-lienquan/index.vue";
import ComponentDauNoiHaTangAoHoa from "./components/daunoi-hatang-aohoa/index.vue";
import ComponentDauNoiHaTangIDG from "./components/daunoi-hatang-idg/index.vue";
import ComponentGoDauNoiHaTangIDG from "./components/go-daunoi-hatang-idg/index.vue";
import ComponentChiTietIDC from "./components/chitiet-idc/index.vue";
import ComponentKhoHTM from "../kho-hatang-mang/index.vue";
import ComponentKhoHTAH from "../KhoHaTangAoHoa/index.vue";
import ComponentKhoHTCLOUD from "../KhoHaTangSmartCloud/index.vue";
import ComponentSoDoMucThietBi from "../SoDoMucThietBi/index.vue";
import ComponentDsDichVu from "./components/ds-dichvu/index.vue";
import ComponentDsKhachHang from "./components/ds-khachhang/index.vue";
import ComponentKhoHTIDG from "../IDG/IDG.vue";
const HaTangType = {
  IDC: "IDC",
  HA_TANG_MANG: "HTM",
  HA_TANG_AO_HOA: "HTAH",
  HA_TANG_CLOUD: "HTCLOUD",
  HA_TANG_IDG: "HTIDG",
};
const MENU_ITEM_CONFIG = {
  PREFIX: "M",
  SEPARATOR: "_",
  ONLYLASTITEM: "ONLYLASTITEM",
};
const ITEM_FORMAT = {
  SEPARATOR: "_",
};

export default {
  provide: {
    diagram: [RadialTree, DiagramContextMenu],
    //diagram: [DataBinding, SymmetricLayout],
  },
  components: {
    ComponentDsTbLienQuan,
    ComponentDauNoiHaTangAoHoa,
    ComponentChiTietIDC,
    ComponentKhoHTM,
    ComponentKhoHTAH,
    ComponentDauNoiHaTangIDG,
    ComponentGoDauNoiHaTangIDG,
    ComponentSoDoMucThietBi,
    ComponentDsDichVu,
    ComponentDsKhachHang,
    ComponentKhoHTIDG,
    ComponentKhoHTCLOUD,
  },
  data: function () {
    return {
      dsIDC: [],
      currentItem: {
        IDC: 0,
      },
      width: "100%",
      height: "100%",
      selectedItems: {
        constraints:
          SelectorConstraints.All &
          ~SelectorConstraints.ResizeAll &
          ~SelectorConstraints.Rotate,
      },
      layout: {
        type: "RadialTree",
      },
      snapSettings: {
        constraints: SnapConstraints.None,
      },
      constraints: DiagramConstraints.Default | DiagramConstraints.Bridging,
      tool: DiagramTools.SingleSelect | DiagramTools.DrawOnce | DiagramTools.ZoomPan,
      // scrollSetting: {
      //   horizontalOffset: 10,
      //   verticalOffset: 10,
      // },
      getNodeDefaults: (node) => {
        node.height = 35;
        node.width = 125;
        node.style = {
          strokeWidth: 1,
          fill: "#81c3ff",
          strokeColor: "#467dae",
          textAlign: "Center",
        };
        node.shape = { type: "Basic", shape: "Rectangle", cornerRadius: 10 };
        // node.style = { fill: color, strokeWidth: 1 };
        // node.shape = { type: "Basic", shape: "Rectangle", cornerRadius: 10 };
        node.constraints = NodeConstraints.Default | NodeConstraints.Tooltip;
        node.tooltip = { content: node.addInfo.NAME.toString() };
        node.annotations = [
          {
            id: "ann_" + node.id.toString(),
            content: node.addInfo.NAME.toString(),
            constraints: AnnotationConstraints.ReadOnly,
          },
        ];
        switch (node.addInfo.ITEM_TYPE) {
          case HaTangType.IDC:
            node.style.fill = "#81c3ff";
            node.style.strokeColor = "#467dae";
            break;
          case HaTangType.HA_TANG_MANG:
            node.style.fill = "#f8cecc";
            node.style.strokeColor = "#bf6763";
            break;
          case HaTangType.HA_TANG_CLOUD:
          case HaTangType.HA_TANG_AO_HOA:
            node.style.fill = "#ee8208";
            node.style.strokeColor = "#fbca8a";
            node.shape.cornerRadius = 0;
            node.annotations[0].offset = {
              x: 0.5,
              y: 1.4,
            };
            node.height = 60;
            node.width = 60;
            break;
          case HaTangType.HA_TANG_IDG:
            node.style.fill = "#326de6";
            node.style.strokeColor = "#326de6";
            node.shape.cornerRadius = 0;
            node.shape.shape = "Octagon";
            node.annotations[0].offset = {
              x: 0.5,
              y: 1.4,
            };
            node.height = 60;
            node.width = 60;
            break;
          default:
            break;
        }
        return node;
      },
      getConnectorDefaults: (connector) => {
        //connector.type = "Orthogonal";
        let color = "#FF0000";
        switch (connector.addInfo.ITEM_TYPE) {
          case HaTangType.IDC:
          case HaTangType.HA_TANG_MANG:
            color = "#b85450";
            break;
          case HaTangType.HA_TANG_CLOUD:
          case HaTangType.HA_TANG_AO_HOA:
            color = "#0707ff";
            break;
          case HaTangType.HA_TANG_IDG:
            color = "#00994d";
            break;
          default:
            break;
        }

        connector.cornerRadius = 10;
        connector.style = {
          strokeWidth: 2,
          strokeDashArray: "5",
          strokeColor: color,
        };
        connector.sourceDecorator.shape = "IndentedArrow";
        connector.sourceDecorator.style.strokeColor = color;
        connector.sourceDecorator.style.fill = color;
        connector.targetDecorator.style.strokeColor = color;
        connector.targetDecorator.style.fill = color;
        connector.constraints = ConnectorConstraints.None;
        // connector.wrapper = {
        //   actualSize: {
        //     width: 100,
        //     height: 100,
        //   },
        // };
        //connector.wrapper.actualSize.width = 100;
        //connector.wrapper.actualSize.height = 100;
        return connector;
      },
      contextMenuSettings: {
        show: true,
        showCustomMenuOnly: true,
        items: [
          {
            text: "Thông tin chi tiết",
            id: MENU_ITEM_CONFIG.PREFIX.concat(MENU_ITEM_CONFIG.SEPARATOR).concat(
              HaTangType.HA_TANG_MANG,
              MENU_ITEM_CONFIG.SEPARATOR,
              HaTangType.HA_TANG_AO_HOA,
              MENU_ITEM_CONFIG.SEPARATOR,
              HaTangType.HA_TANG_CLOUD,
              MENU_ITEM_CONFIG.SEPARATOR,
              HaTangType.HA_TANG_IDG,
              MENU_ITEM_CONFIG.SEPARATOR,
              HaTangType.IDC,
              MENU_ITEM_CONFIG.SEPARATOR,
              "TTCT"
            ),
            target: ".e-diagramcontent",
            iconCss: "e-menu-icon text-main -ap icon-info_outline",
          },
          {
            id: MENU_ITEM_CONFIG.PREFIX.concat(MENU_ITEM_CONFIG.SEPARATOR).concat(
              HaTangType.HA_TANG_MANG,
              MENU_ITEM_CONFIG.SEPARATOR,
              HaTangType.HA_TANG_AO_HOA,
              MENU_ITEM_CONFIG.SEPARATOR,
              HaTangType.HA_TANG_CLOUD,
              MENU_ITEM_CONFIG.SEPARATOR,
              HaTangType.HA_TANG_IDG,
              MENU_ITEM_CONFIG.SEPARATOR,
              HaTangType.IDC,
              MENU_ITEM_CONFIG.SEPARATOR,
              "1_SP"
            ),
            separator: true,
          },
          {
            text: "Danh sách thiết bị liên quan",
            id: MENU_ITEM_CONFIG.PREFIX.concat(MENU_ITEM_CONFIG.SEPARATOR).concat(
              HaTangType.HA_TANG_MANG,
              MENU_ITEM_CONFIG.SEPARATOR,
              HaTangType.HA_TANG_AO_HOA,
              MENU_ITEM_CONFIG.SEPARATOR,
              HaTangType.HA_TANG_CLOUD,
              MENU_ITEM_CONFIG.SEPARATOR,
              // HaTangType.HA_TANG_IDG,
              // MENU_ITEM_CONFIG.SEPARATOR,
              "DSTBLQ"
            ),
            target: ".e-diagramcontent",
            iconCss: "e-menu-icon text-main -ap icon-list2",
          },
          {
            text: "Sơ đồ đi dây đấu nối",
            id: MENU_ITEM_CONFIG.PREFIX.concat(MENU_ITEM_CONFIG.SEPARATOR).concat(
              HaTangType.HA_TANG_MANG,
              MENU_ITEM_CONFIG.SEPARATOR,
              HaTangType.HA_TANG_AO_HOA,
              MENU_ITEM_CONFIG.SEPARATOR,
              HaTangType.HA_TANG_CLOUD,
              MENU_ITEM_CONFIG.SEPARATOR,
              HaTangType.HA_TANG_IDG,
              MENU_ITEM_CONFIG.SEPARATOR,
              "SDDDDN"
            ),
            target: ".e-diagramcontent",
            iconCss: "e-menu-icon text-main one-tc-icon1",
          },
          {
            text: "Thêm mới cụm hạ tầng mạng",
            id: MENU_ITEM_CONFIG.PREFIX.concat(MENU_ITEM_CONFIG.SEPARATOR).concat(
              HaTangType.IDC,
              MENU_ITEM_CONFIG.SEPARATOR,
              "TMCHTM"
            ),
            target: ".e-diagramcontent",
            iconCss: "e-menu-icon text-main -ap icon-add_circle_outline",
          },
          {
            text: "Đấu nối cụm hạ tầng ảo hóa",
            id: MENU_ITEM_CONFIG.PREFIX.concat(MENU_ITEM_CONFIG.SEPARATOR).concat(
              HaTangType.HA_TANG_MANG,
              MENU_ITEM_CONFIG.SEPARATOR,
              "DAUNOI"
            ),
            target: ".e-diagramcontent",
            iconCss: "e-menu-icon text-main one-daunoi",
          },
          {
            text: "Đấu nối cụm hạ tầng IDG",
            id: MENU_ITEM_CONFIG.PREFIX.concat(MENU_ITEM_CONFIG.SEPARATOR).concat(
              HaTangType.HA_TANG_AO_HOA,
              MENU_ITEM_CONFIG.SEPARATOR,
              HaTangType.HA_TANG_CLOUD,
              MENU_ITEM_CONFIG.SEPARATOR,
              "DAUNOI"
            ),
            target: ".e-diagramcontent",
            iconCss: "e-menu-icon text-main one-daunoi",
          },
          {
            id: MENU_ITEM_CONFIG.PREFIX.concat(MENU_ITEM_CONFIG.SEPARATOR).concat(
              HaTangType.HA_TANG_AO_HOA,
              MENU_ITEM_CONFIG.SEPARATOR,
              HaTangType.HA_TANG_CLOUD,
              MENU_ITEM_CONFIG.SEPARATOR,
              HaTangType.HA_TANG_IDG,
              MENU_ITEM_CONFIG.SEPARATOR,
              MENU_ITEM_CONFIG.ONLYLASTITEM,
              MENU_ITEM_CONFIG.SEPARATOR,
              "2_SP"
            ),
            separator: true,
          },
          {
            text: "Gỡ đấu nối",
            id: MENU_ITEM_CONFIG.PREFIX.concat(MENU_ITEM_CONFIG.SEPARATOR).concat(
              HaTangType.HA_TANG_AO_HOA,
              MENU_ITEM_CONFIG.SEPARATOR,
              HaTangType.HA_TANG_CLOUD,
              MENU_ITEM_CONFIG.SEPARATOR,
              HaTangType.HA_TANG_IDG,
              MENU_ITEM_CONFIG.SEPARATOR,
              MENU_ITEM_CONFIG.ONLYLASTITEM,
              MENU_ITEM_CONFIG.SEPARATOR,
              "GODAUNOI"
            ),
            target: ".e-diagramcontent",
            iconCss: "e-menu-icon text-main one-trash",
          },
          {
            id: MENU_ITEM_CONFIG.PREFIX.concat(MENU_ITEM_CONFIG.SEPARATOR).concat(
              HaTangType.HA_TANG_MANG,
              MENU_ITEM_CONFIG.SEPARATOR,
              HaTangType.HA_TANG_AO_HOA,
              MENU_ITEM_CONFIG.SEPARATOR,
              HaTangType.HA_TANG_CLOUD,
              MENU_ITEM_CONFIG.SEPARATOR,
              // HaTangType.HA_TANG_IDG,
              // MENU_ITEM_CONFIG.SEPARATOR,
              "3_SP"
            ),
            separator: true,
          },
          {
            text: "Danh sách dịch vụ",
            id: MENU_ITEM_CONFIG.PREFIX.concat(MENU_ITEM_CONFIG.SEPARATOR).concat(
              HaTangType.HA_TANG_MANG,
              MENU_ITEM_CONFIG.SEPARATOR,
              HaTangType.HA_TANG_AO_HOA,
              MENU_ITEM_CONFIG.SEPARATOR,
              HaTangType.HA_TANG_CLOUD,
              MENU_ITEM_CONFIG.SEPARATOR,
              // HaTangType.HA_TANG_IDG,
              // MENU_ITEM_CONFIG.SEPARATOR,
              "DSDV"
            ),
            target: ".e-diagramcontent",
            iconCss: "e-menu-icon text-main -ap icon-list5",
          },
          {
            text: "Khách hàng sử dụng dịch vụ",
            id: MENU_ITEM_CONFIG.PREFIX.concat(MENU_ITEM_CONFIG.SEPARATOR).concat(
              HaTangType.HA_TANG_MANG,
              MENU_ITEM_CONFIG.SEPARATOR,
              HaTangType.HA_TANG_AO_HOA,
              MENU_ITEM_CONFIG.SEPARATOR,
              // HaTangType.HA_TANG_CLOUD,
              // MENU_ITEM_CONFIG.SEPARATOR,
              // HaTangType.HA_TANG_IDG,
              // MENU_ITEM_CONFIG.SEPARATOR,
              "KHSDDV"
            ),
            target: ".e-diagramcontent",
            iconCss: "e-menu-icon text-main -ap icon-users",
          },
        ],
      },
      modalSelectedItem: {
        IDC: 0,
        loaiHaTang: "",
        haTangID: 0,
      },
    };
  },
  computed: {
    selectedIDCID: function () {
      if (this.modalSelectedItem.loaiHaTang == HaTangType.IDC) {
        return this.modalSelectedItem.haTangID;
      }
      return 0;
    },
    selectedHTIDGID: function () {
      if (this.modalSelectedItem.loaiHaTang == HaTangType.HA_TANG_IDG) {
        return this.modalSelectedItem.haTangID;
      }
      return 0;
    },
    selectedHTCLOUDID: function () {
      if (this.modalSelectedItem.loaiHaTang == HaTangType.HA_TANG_CLOUD) {
        return this.modalSelectedItem.haTangID;
      }
      return 0;
    },
    selectedHTAHID: function () {
      if (this.modalSelectedItem.loaiHaTang == HaTangType.HA_TANG_AO_HOA) {
        return this.modalSelectedItem.haTangID;
      }
      return 0;
    },
    selectedHTMID: function () {
      if (this.modalSelectedItem.loaiHaTang == HaTangType.HA_TANG_MANG) {
        return this.modalSelectedItem.haTangID;
      }
      return 0;
    },
    diagram: function () {
      if (!(this.$refs.diagram == null || this.$refs.diagram == undefined))
        return this.$refs.diagram;
      return null;
    },
    diagramEj2: function () {
      if (this.diagram != null) return this.diagram.ej2Instances;
      return null;
    },
    nodes: function () {
      if (this.diagramEj2 != null) return this.diagramEj2.nodes;
      return [];
    },
    connectors: function () {
      if (this.diagramEj2 != null) return this.diagramEj2.connectors;
      return [];
    },
    MENU_ITEM_PR_SP: function () {
      return MENU_ITEM_CONFIG.PREFIX.concat(MENU_ITEM_CONFIG.SEPARATOR);
    },
  },
  watch: {},
  mounted: async function (args) {
    try {
      this.$root.showLoading(true);
      let ds = await this.getDanhSachIDC();
      if (ds.result) {
        ds.data.unshift({
          ID: 0,
          TEN: "(Tất cả)",
        });
        this.dsIDC = ds.data;
      }
      await this.loadDiagram(args);
    } finally {
      this.$root.showLoading(false);
    }
  },
  methods: {
    getPathToIDC: function (nodeID) {
      let path = [];
      let currentNode = this.nodes.find((x) => x.id == nodeID);

      if (currentNode) {
        path.push(currentNode);

        // Nếu node hiện tại không phải IDC thì tìm đường đi lên node cha
        while (currentNode && currentNode.addInfo.ITEM_TYPE !== HaTangType.IDC) {
          // Tìm connector có targetID là node hiện tại
          let connector = this.connectors.find((x) => x.targetID == currentNode.id);
          if (connector) {
            // Lấy node nguồn của connector
            currentNode = this.nodes.find((x) => x.id == connector.sourceID);
            if (currentNode) {
              path.push(currentNode);
            }
          } else {
            break; // Không tìm thấy đường đi tiếp
          }
        }
      }

      return path;
    },

    getNodeIDCfromNodeID: function (nodeID) {
      let path = this.getPathToIDC(nodeID);
      // Tìm node IDC trong path (thường là node cuối cùng)
      let nodeIDC = path.find((node) => node.addInfo.ITEM_TYPE === HaTangType.IDC);
      return nodeIDC ? nodeIDC.addInfo : null;
    },
    getSelectedItem: function () {
      let result = null;
      if (this.diagram != null) {
        let selector = this.diagramEj2.selectedItems;
        if (selector.nodes.length > 0) {
          result = selector.nodes[0].addInfo;
        }
      }
      return result;
    },
    addNode: function (args) {
      if (this.diagram != null) {
        let id = args.ITEM_TYPE + ITEM_FORMAT.SEPARATOR + args.ITEM_ID;
        let node = this.diagram.nodes ? this.diagram.nodes.find((x) => x.id == id) : null;
        if (node == null) {
          node = this.diagram.addNode({
            id: id,
            addInfo: { ID: id, ...args },
          });
        } else {
          node.addInfo = args;
        }
        this.diagram.updateData();
        return node;
      }
      return null;
    },
    addConnector: function (itemID, itemType, parentID, parentType) {
      if (this.diagram != null) {
        let args = {
          ITEM_ID: itemID,
          ITEM_TYPE: itemType,
          PARENT_ID: parentID,
          PARENT_TYPE: parentType,
        };
        if (args.PARENT_ID != null) {
          let id =
            args.PARENT_TYPE +
            ITEM_FORMAT.SEPARATOR +
            args.PARENT_ID +
            ITEM_FORMAT.SEPARATOR +
            args.ITEM_TYPE +
            ITEM_FORMAT.SEPARATOR +
            args.ITEM_ID;
          let connector = this.diagram.connectors
            ? this.diagram.connectors.find((x) => x.id == id)
            : null;
          if (connector == null) {
            let sourceID = args.PARENT_TYPE + ITEM_FORMAT.SEPARATOR + args.PARENT_ID;
            let targetID = args.ITEM_TYPE + ITEM_FORMAT.SEPARATOR + args.ITEM_ID;
            connector = this.diagram.addConnector({
              addInfo: { ID: id, sourceID: sourceID, targetID: targetID, ...args },
              id: id,
              sourceID: sourceID,
              targetID: targetID,
            });
            // connector.segments[0].type = "Orthogonal";
            connector.segments[0].length = 50;
          } else {
            connector.addInfo = args;
          }
          this.diagram.updateData();
          return connector;
        }
      }
      return null;
    },
    checkLastNode: function (args) {
      let result = false;
      if (this.connectors.length > 0) {
        return this.connectors.findIndex((x) => x.sourceID == args.ID) < 0;
      }
      return result;
    },
    loadDiagram: async function (args) {
      this.diagram.clear();
      this.diagram.reset();
      let items = await this.getThongTinSoDo(this.currentItem.IDC);
      if (items.result) {
        for (let i = 0; i < items.data.length; i++) {
          let item = items.data[i];
          this.addNode(item);
          if (item.PARENTS.length > 0) {
            for (let j = 0; j < item.PARENTS.length; j++) {
              const parent = item.PARENTS[j];
              this.addConnector(
                item.ITEM_ID,
                item.ITEM_TYPE,
                parent.ITEM_ID,
                parent.ITEM_TYPE
              );
            }
          }
        }
      } else {
        this.$root.toastError(items.msg);
      }
      this.diagram.doLayout();
      this.diagram.fitToPage();
    },
    diagram_contextMenuOpen: function (args) {
      let item = this.getSelectedItem();
      if (item != null) {
        args.hiddenItems = args.items
          .filter(
            (x) =>
              !x.id.includes(
                MENU_ITEM_CONFIG.SEPARATOR.concat(
                  item.ITEM_TYPE,
                  MENU_ITEM_CONFIG.SEPARATOR
                )
              )
          )
          .map((x) => x.id);
        if (!this.checkLastNode(item)) {
          let items = args.items.filter((x) =>
            x.id.includes(
              MENU_ITEM_CONFIG.SEPARATOR.concat(
                MENU_ITEM_CONFIG.ONLYLASTITEM,
                MENU_ITEM_CONFIG.SEPARATOR
              )
            )
          );
          items.forEach((it) => {
            args.hiddenItems.push(it.id);
          });
        }
      } else {
        args.hiddenItems = args.items.map((x) => x.id);
      }
    },
    diagram_contextMenuClick: async function (args) {
      let itemArgs = {
        menuItem: args.item.id,
        dataItem: this.getSelectedItem(),
      };
      this.modalSelectedItem.loaiHaTang = itemArgs.dataItem.ITEM_TYPE;
      this.modalSelectedItem.haTangID = itemArgs.dataItem.ITEM_ID;
      switch (itemArgs.menuItem) {
        case this.MENU_ITEM_PR_SP.concat(
          HaTangType.HA_TANG_MANG,
          MENU_ITEM_CONFIG.SEPARATOR,
          HaTangType.HA_TANG_AO_HOA,
          MENU_ITEM_CONFIG.SEPARATOR,
          HaTangType.HA_TANG_CLOUD,
          MENU_ITEM_CONFIG.SEPARATOR,
          HaTangType.HA_TANG_IDG,
          MENU_ITEM_CONFIG.SEPARATOR,
          HaTangType.IDC,
          MENU_ITEM_CONFIG.SEPARATOR,
          "TTCT"
        ):
          //Thông tin chi tiết
          this.thongTinChiTiet(itemArgs);
          break;
        case this.MENU_ITEM_PR_SP.concat(
          HaTangType.HA_TANG_MANG,
          MENU_ITEM_CONFIG.SEPARATOR,
          HaTangType.HA_TANG_AO_HOA,
          MENU_ITEM_CONFIG.SEPARATOR,
          HaTangType.HA_TANG_CLOUD,
          MENU_ITEM_CONFIG.SEPARATOR,
          "DSTBLQ"
        ):
          //Danh sách thiết bị liên quan
          this.dsThietBiLienQuan(itemArgs);
          break;
        case this.MENU_ITEM_PR_SP.concat(
          HaTangType.HA_TANG_MANG,
          MENU_ITEM_CONFIG.SEPARATOR,
          HaTangType.HA_TANG_AO_HOA,
          MENU_ITEM_CONFIG.SEPARATOR,
          HaTangType.HA_TANG_CLOUD,
          MENU_ITEM_CONFIG.SEPARATOR,
          HaTangType.HA_TANG_IDG,
          MENU_ITEM_CONFIG.SEPARATOR,
          "SDDDDN"
        ):
          //Sơ đồ đi dây đấu nối
          this.soDoDiDayDauNoi(itemArgs);
          break;
        case this.MENU_ITEM_PR_SP.concat(
          HaTangType.IDC,
          MENU_ITEM_CONFIG.SEPARATOR,
          "TMCHTM"
        ):
          //Thêm mới cụm hạ tầng mạng
          this.themMoiCumHTM(itemArgs);
          break;
        case this.MENU_ITEM_PR_SP.concat(
          HaTangType.HA_TANG_MANG,
          MENU_ITEM_CONFIG.SEPARATOR,
          "DAUNOI"
        ):
          //Đấu nối cụm hạ tầng ảo hóa
          this.dauNoiCumHTAH(itemArgs);
          break;
        case this.MENU_ITEM_PR_SP.concat(
          HaTangType.HA_TANG_AO_HOA,
          MENU_ITEM_CONFIG.SEPARATOR,
          HaTangType.HA_TANG_CLOUD,
          MENU_ITEM_CONFIG.SEPARATOR,
          "DAUNOI"
        ):
          //Đấu nối Cụm hạ tầng IDG
          this.dauNoiCumHTIDG(itemArgs);
          break;
        case this.MENU_ITEM_PR_SP.concat(
          HaTangType.HA_TANG_AO_HOA,
          MENU_ITEM_CONFIG.SEPARATOR,
          HaTangType.HA_TANG_CLOUD,
          MENU_ITEM_CONFIG.SEPARATOR,
          HaTangType.HA_TANG_IDG,
          MENU_ITEM_CONFIG.SEPARATOR,
          MENU_ITEM_CONFIG.ONLYLASTITEM,
          MENU_ITEM_CONFIG.SEPARATOR,
          "GODAUNOI"
        ):
          //Gỡ đấu nối
          await this.goDauNoi(itemArgs);
          break;
        case this.MENU_ITEM_PR_SP.concat(
          HaTangType.HA_TANG_MANG,
          MENU_ITEM_CONFIG.SEPARATOR,
          HaTangType.HA_TANG_AO_HOA,
          MENU_ITEM_CONFIG.SEPARATOR,
          HaTangType.HA_TANG_CLOUD,
          MENU_ITEM_CONFIG.SEPARATOR,
          "DSDV"
        ):
          //Danh sách các dịch vụ
          this.dsDichVu(itemArgs);
          break;
        case this.MENU_ITEM_PR_SP.concat(
          HaTangType.HA_TANG_MANG,
          MENU_ITEM_CONFIG.SEPARATOR,
          HaTangType.HA_TANG_AO_HOA,
          MENU_ITEM_CONFIG.SEPARATOR,
          // HaTangType.HA_TANG_CLOUD,
          // MENU_ITEM_CONFIG.SEPARATOR,
          "KHSDDV"
        ):
          //Khách hàng sử dụng dịch vụ
          this.dsKhachHang(itemArgs);
          break;
        default:
          console.log("diagram_contextMenuClick", itemArgs);
          break;
      }
    },
    thongTinChiTiet: function (args) {
      console.log("thongTinChiTiet", args);
      switch (args.dataItem.ITEM_TYPE) {
        case HaTangType.IDC:
          this.$refs.modalChiTietIDC.show();
          break;
        case HaTangType.HA_TANG_MANG:
          this.$refs.modalKhoHTM.show();
          break;
        case HaTangType.HA_TANG_AO_HOA:
          this.$refs.modalKhoHTAH.show();
          break;
        case HaTangType.HA_TANG_CLOUD:
          this.$refs.modalKhoHTCLOUD.show();
          break;
        case HaTangType.HA_TANG_IDG:
          this.$refs.modalKhoHTIDG.show();
          break;
        default:
          break;
      }
    },
    dsThietBiLienQuan: function (args) {
      console.log("dsThietBiLienQuan", args);
      this.$refs.modalDsTbLienQuan.show();
    },
    soDoDiDayDauNoi: function (args) {
      console.log("soDoDiDayDauNoi", args);
      switch (args.dataItem.ITEM_TYPE) {
        case HaTangType.HA_TANG_MANG:
          break;
        case HaTangType.HA_TANG_AO_HOA:
          break;
        case HaTangType.HA_TANG_IDG:
          break;
        default:
          break;
      }
      this.$refs.modalSoDoMucThietBi.show();
    },
    themMoiCumHTM: function (args) {
      console.log("themMoiCumHTM", args);
      this.$refs.modalKhoHTM.show();
    },
    dauNoiCumHTAH: function (args) {
      console.log("dauNoiCumHTAH", args);
      this.$refs.modalDauNoiHaTangAoHoa.show();
    },
    dauNoiCumHTIDG: function (args) {
      console.log("dauNoiCumHTIDG", args);
      this.$refs.modalDauNoiHaTangIDG.show();
    },
    goDauNoi: async function (args) {
      console.log("goDauNoi", args);
      if (args.dataItem.ITEM_TYPE == HaTangType.HA_TANG_IDG) {
        this.$refs.modalGoDauNoiHaTangIDG.show();
      } else {
        let flag = await this.$confirm(
          `Có chắc bạn muốn gỡ đấu nối cho hạ tầng ${args.dataItem.NAME}?`,
          "Xác nhận thực hiện",
          {
            confirmButtonText: "Có",
            cancelButtonText: "Không",
            type: "info",
            dangerouslyUseHTMLString: true,
          }
        )
          .then((s) => true)
          .catch((e) => {
            return false;
          });
        if (flag) {
          try {
            this.$root.showLoading(true);
            let item = await this.goDauNoiHT(
              args.dataItem.ITEM_TYPE,
              args.dataItem.ITEM_ID
            );
            if (item.result) {
              if (item.data.result) {
                this.$root.toastSuccess("Gỡ đấu nối thành công");
                await this.loadDiagram(args);
              } else {
                this.$root.toastError("Gỡ đấu nối thất bại");
              }
            } else {
              this.$root.toastError(item.msg);
            }
          } finally {
            this.$root.showLoading(false);
          }
        }
      }
    },
    dsDichVu: function (args) {
      console.log("dsDichVu", args);
      this.$refs.modalDsDichVu.show();
    },
    dsKhachHang: function (args) {
      console.log("dsKhachHang", args);
      this.$refs.modalDsKhachHang.show();
      // switch (args.dataItem.ITEM_TYPE) {
      //   case HaTangType.HA_TANG_MANG:
      //     break;
      //   case HaTangType.HA_TANG_AO_HOA:
      //     break;
      //   case HaTangType.HA_TANG_IDG:
      //     break;
      //   default:
      //     break;
      // }
      // this.$root.toastSuccess("Đang chờ thông tin phản hồi từ nghiệp vụ");
    },
    modalKhoHTM_Shown: async function (args) {
      if (this.selectedIDCID > 0) {
        await this.$refs.formKhoHTM.onAddNew(args);
      } else {
        await this.$refs.formKhoHTM.onCancel(args);
      }
    },
    modalKhoHTAH_Shown: function (args) {},
    modalKhoHTCLOUD_Shown: function (args) {},
    getTypeForDeviceDiagram: function (type) {
      let result = 'NOTFOUND';
      switch (type) {
        case HaTangType.HA_TANG_MANG:
          result = 'HATANGMANG';
          break;
        case HaTangType.HA_TANG_AO_HOA:
          result = 'HATANGAOHOA';
          break;
        case HaTangType.HA_TANG_CLOUD:
          result = 'HATANGSMARTCLOUD';
          break;
        case HaTangType.HA_TANG_IDG:
          result = 'HATANGIDG';
          break;
        default:
          break;
      }
      return result;
    },
    modalSoDoMucThietBi_Shown: async function (args) {
      let nodeSelected = this.getSelectedItem();
      let path = this.getPathToIDC(nodeSelected.ID);

      if (path.length > 0) {
        let nodeIDC = path.find((node) => node.addInfo.ITEM_TYPE === HaTangType.IDC);
        let listHTMId = path
          .filter((node) => node.addInfo.ITEM_TYPE !== HaTangType.IDC)
          .map((node) => node.addInfo.ITEM_ID + "-" + this.getTypeForDeviceDiagram(node.addInfo.ITEM_TYPE)); ;
        if (nodeIDC) {
          await this.$refs.formSoDoMucThietBi.loadDiagramFromPopup(
            nodeIDC.addInfo.ITEM_ID,
            listHTMId
          );
          return path.map((node) => ({
            id: node.addInfo.ID,
            name: node.addInfo.NAME,
            type: node.addInfo.ITEM_TYPE,
            itemId: node.addInfo.ITEM_ID,
          }));
        }
      }


      this.$refs.modalSoDoMucThietBi.hide();
      this.$root.toastError("Không tìm thấy thông tin IDC");
      return [];
    },
    modalDsDichVu_Shown: async function (args) {
      await this.$refs.formDsDichVu.loadData(
        this.modalSelectedItem.loaiHaTang,
        this.modalSelectedItem.haTangID
      );
    },
    modalDsKhachHang_Shown: async function (args) {
      await this.$refs.formDsKhachHang.loadData(
        this.modalSelectedItem.loaiHaTang,
        this.modalSelectedItem.haTangID
      );
    },
    modalDauNoiHaTangAoHoa_Shown: async function (args) {
      await this.$refs.formDauNoiHaTangAoHoa.loadData(this.selectedHTMID);
    },
    modalKhoHTIDG_Shown: function (args) {},
    modalDauNoiHaTangIDG_Shown: async function (args) {
      await this.$refs.formDauNoiHaTangIDG.loadData(
        this.modalSelectedItem.loaiHaTang,
        this.modalSelectedItem.haTangID
      );
    },
    modalGoDauNoiHaTangIDG_Shown: async function (args) {
      await this.$refs.formGoDauNoiHaTangIDG.loadData(this.modalSelectedItem.haTangID);
    },
    combobox_Changed: async function (args) {
      try {
        this.$root.showLoading(true);
        await this.loadDiagram(args);
      } finally {
        this.$root.showLoading(false);
      }
    },
    modalDsTbLienQuan_Shown: async function (args) {
      console.log("modalDsTbLienQuan_Shown", this.modalSelectedItem);
      await this.$refs.formDsTbLienQuan.loadData(
        this.modalSelectedItem.loaiHaTang,
        this.modalSelectedItem.haTangID
      );
    },
    modalChiTietIDC_Shown: function (args) {
      this.$refs.formChiTietIDC.loadData(this.selectedIDCID);
    },
    getThongTinSoDo: async function (id) {
      let result = {
        result: false,
        data: null,
        msg: "",
      };
      try {
        let rs = await this.$root.context.post(
          "/web-ecms/hatang/sodo-logic/get-thongtin-sodo",
          {
            id: id,
          }
        );
        let items = [];
        items.push({
          ITEM_ID: 1,
          ITEM_TYPE: "NET",
          NAME: "VNPT NET",
          PARENTS: [
            {
              ITEM_ID: null,
              ITEM_TYPE: null,
            },
          ],
        });
        rs.data.forEach((dataItem) => {
          let item = items.find(
            (x) => x.ITEM_ID == dataItem.ITEM_ID && x.ITEM_TYPE == dataItem.ITEM_TYPE
          );
          if (item == null) {
            items.push({
              ITEM_ID: dataItem.ITEM_ID,
              ITEM_TYPE: dataItem.ITEM_TYPE,
              NAME: dataItem.NAME,
              PARENTS: [
                {
                  ITEM_ID: dataItem.PARENT_ID,
                  ITEM_TYPE: dataItem.PARENT_TYPE,
                },
              ],
            });
          } else {
            if (
              item.PARENTS.find(
                (x) =>
                  x.ITEM_ID == dataItem.PARENT_ID && x.ITEM_TYPE == dataItem.PARENT_TYPE
              ) == null
            ) {
              item.PARENTS.push({
                ITEM_ID: dataItem.PARENT_ID,
                ITEM_TYPE: dataItem.PARENT_TYPE,
              });
            }
          }
        });
        result.data = items;
        result.result = true;
      } catch (e) {
        result.msg = "Có lỗi xảy ra khi lấy thông tin sơ đồ";
      }
      return result;
    },
    getDanhSachIDC: async function () {
      let result = {
        result: false,
        data: null,
        msg: "",
      };
      try {
        let rs = await this.$root.context.post(
          "/web-ecms/hatang/sodo-logic/get-danhsach-idc"
        );
        result.data = rs.data;
        result.result = true;
      } catch (e) {
        result.msg = "Có lỗi xảy ra khi lấy danh sách IDC";
      }
      return result;
    },
    btnPhongTo_Click: function (args) {
      this.doZoomIn(args);
    },
    doZoomIn: function (args) {
      if (this.diagram != null) {
        this.diagram.zoomTo({ type: "ZoomIn", zoomFactor: 0.2 });
      }
    },
    btnThuNho_Click: function (args) {
      this.doZoomOut(args);
    },
    doZoomOut: function (args) {
      if (this.diagram != null) {
        this.diagram.zoomTo({ type: "ZoomOut", zoomFactor: 0.2 });
      }
    },
    modalDauNoiHaTangAoHoaHuyBo_Click: function (args) {
      this.$refs.modalDauNoiHaTangAoHoa.hide();
    },
    modalDauNoiHaTangAoHoaLuu_Click: async function (args) {
      try {
        this.$root.showLoading(true);
        let item = await this.$refs.formDauNoiHaTangAoHoa.dauNoi();
        if (item.result) {
          if (item.data) {
            this.$refs.modalDauNoiHaTangAoHoa.hide();
            this.$root.toastSuccess("Đấu nối thành công");
            await this.loadDiagram(args);
          } else {
            this.$root.toastError("Đấu nối thất bại");
          }
        } else {
          this.$root.toastError(item.msg);
        }
      } finally {
        this.$root.showLoading(false);
      }
    },
    modalDauNoiHaTangIDGHuyBo_Click: function (args) {
      this.$refs.modalDauNoiHaTangIDG.hide();
    },
    modalDauNoiHaTangIDGLuu_Click: async function (args) {
      try {
        this.$root.showLoading(true);
        let item = await this.$refs.formDauNoiHaTangIDG.dauNoi();
        if (item.result) {
          if (item.data) {
            this.$refs.modalDauNoiHaTangIDG.hide();
            this.$root.toastSuccess("Đấu nối thành công");
            await this.loadDiagram(args);
          } else {
            this.$root.toastError("Đấu nối thất bại");
          }
        } else {
          this.$root.toastError(item.msg);
        }
      } finally {
        this.$root.showLoading(false);
      }
    },
    modalGoDauNoiHaTangIDGHuyBo_Click: function (args) {
      this.$refs.modalGoDauNoiHaTangIDG.hide();
    },
    modalGoDauNoiHaTangIDGLuu_Click: async function (args) {
      let validResult = await this.$refs.formGoDauNoiHaTangIDG.validateForm();
      if (!validResult.result) {
        this.$root.toastError(validResult.msg);
      } else {
        try {
          this.$root.showLoading(true);
          let item = await this.$refs.formGoDauNoiHaTangIDG.goDauNoi();
          if (item.result) {
            if (item.data > 0) {
              this.$refs.modalGoDauNoiHaTangIDG.hide();
              this.$root.toastSuccess("Gỡ đấu nối thành công");
              await this.loadDiagram(args);
            } else {
              this.$root.toastError("Gỡ đấu nối thất bại");
            }
          } else {
            this.$root.toastError(item.msg);
          }
        } finally {
          this.$root.showLoading(false);
        }
      }
    },
    goDauNoiHT: async function (loaiHaTang, id) {
      let apiUrl = "";
      switch (loaiHaTang) {
        case HaTangType.HA_TANG_AO_HOA:
          apiUrl = "/web-ecms/hatang/sodo-logic/go-daunoi-htm-htah";
          break;
        case HaTangType.HA_TANG_CLOUD:
          apiUrl = "/web-ecms/hatang/sodo-logic/go-daunoi-htm-htcloud";
          break;
        case HaTangType.HA_TANG_IDG:
          apiUrl = "/web-ecms/hatang/sodo-logic/go-daunoi-htidg";
          break;
        default:
          break;
      }
      let result = {
        result: false,
        data: null,
        msg: "",
      };
      if (apiUrl != "") {
        try {
          let rs = await this.$root.context.post(apiUrl, {
            id: id,
          });
          result.data = rs.data;
          result.result = true;
        } catch (e) {
          result.msg = "Có lỗi xảy ra khi gỡ đấu nối";
        }
      } else {
        result.msg = "Không xác định đối tượng đấu nối";
      }
      return result;
    },
  },
};
</script>
<style>
.page .page-content {
  display: flex;
  flex: 1;
}

.page .page-content .row-diagram {
  width: 100%;
  height: 100%;
  padding: 0 16px;
}
</style>
