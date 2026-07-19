<template src="./index.html"></template>

<script>
import ThietBi from "../CaySoDoThietBi/ThietBi";
import $ from "jquery";
export default {
  props: {
    loadDataWhenCreate: false,
    kieuTu: {
      type: Number,
      default: 1,
    },
  },
  data: function () {
    return {
      dsNodes: [],
      //nodesFiltered: null,
      selectedNodes: [],
      expandedNodes: [],
      flagSearch: false,
    };
  },
  created: async function () {
    if (this.loadDataWhenCreate == true) {
      this.$emit("beginCreate");
      try {
        await this.loadNodes();
      } finally {
        this.$emit("endCreate");
      }
    }
  },
  computed: {
    treeFields: function () {
      return {
        dataSource: this.dsNodesSorted,
        id: "id",
        text: "text",
        iconCss: "icon",
        parentID: "parentID",
        hasChildren: "hasChildren",
        expanded: "expanded",
        selected: "selected",
      };
    },
    dsNodesSorted: function () {
      return this.dsNodes
        .filter((item) => {
          return item.text != null;
        })
        .sort((a, b) => (a.text < b.text ? -1 : 1));
      // if (this.nodesFiltered == null)
      //   return this.dsNodes
      //     .filter((item) => {
      //       return item.text != null;
      //     })
      //     .sort((a, b) => (a.text < b.text ? -1 : 1));
      // else {
      //   return this.nodesFiltered
      //     .filter((item) => {
      //       return item.text != null;
      //     })
      //     .sort((a, b) => (a.text < b.text ? -1 : 1));
      // }
    },
    dsNodesFiltered: function () {
      if (this.nodesFiltered == null)
        return this.dsNodes
          .filter((item) => {
            return item.text != null;
          })
          .sort((a, b) => (a.text < b.text ? -1 : 1));
      else {
        return this.nodesFiltered
          .filter((item) => {
            return item.text != null;
          })
          .sort((a, b) => (a.text < b.text ? -1 : 1));
      }
    },
  },
  methods: {
    tree_nodeExpanding: async function (args) {
      let node = this.getNode(args.nodeData.id);
      if (node.attributes.type == ThietBi.PRE_DONVI) {
        if (!(node.attributes.level != 3 || this.kieuTu == 3)) {
          let nextChilds = this.dsNodes.filter((x) => x.parentID == node.id);
          if (nextChilds.length == 0) {
            this.$root.showLoading(true);
            try {
              await this.loadDsNodeThietBi(node.attributes.id);
              this.selectedNode(node);
              this.expandedNodes.push(node.id);
            } finally {
              this.$root.showLoading(false);
            }
          }
        }
      }
    },
    tree_nodeSelected: function (args) {
      let item = this.dsNodes.find((x) => x.id == args.nodeData.id);
      console.log("tree_nodeSelected", item);
      if (item != null) {
        if(this.flagSearch){
          let top = $(".e-node-focus").parent(".e-list-parent").offset().top - 290 + $(".e-node-focus").position().top;
          $(".cay-sodo-tb").animate({
            scrollTop: top,
          });
        }
        this.flagSearch = false;
        let newArgs = {
          node: args.node,
          expanded: args.nodeData.expanded,
          hasChildren: args.nodeData.hasChildren,
          selected: args.nodeData.selected,
          text: args.nodeData.text,
          itemData: {
            itemID: item.attributes.id,
            itemType: item.attributes.type,
            itemParentID: item.attributes.parentID,
            itemParentType: item.attributes.parentType,
            itemAttributes: item.attributes,
          },
        };
        this.$emit("nodeSelected", newArgs);
      }
    },
    btnReload_click: async function () {
      this.$root.showLoading(true);
      try {
        this.$refs.textBoxSearch.value = "";
        //this.nodesFiltered = null;
        await this.loadNodes();
      } finally {
        this.$root.showLoading(false);
      }
    },
    btnSearch_click: async function () {
      let text = this.$refs.textBoxSearch.value;
      this.flagSearch = true;
      if (!this.checkIsEmpty(text)) {
        if (text.length > 3) {
          this.$root.showLoading(true);
          try {
            this.dsNodes = this.dsNodes.filter(
              (x) => x.attributes.type == ThietBi.PRE_DONVI
            );
            if (text.toLowerCase().startsWith("go ")) {
              let node = this.dsNodesSorted.find((x) =>
                x.text.toLowerCase().includes(text.substring(3).toLowerCase().trim())
              );
              if (node != undefined) this.selectedNode(node);
              else {
                this.clearSelectedNodes();
                this.$root.toastError("Không tìm thấy trạm thỏa mãn !");
              }
            } else {
              let rs = await this.timKiemThietBi(text);
              if (rs.result === true) {
                await this.loadDsNodeThietBi(rs.detail.donViID);
                let node = this.dsNodesSorted.find(
                  (x) =>
                    x.attributes.type == rs.detail.type &&
                    x.attributes.id == parseInt(rs.detail.thietBiID)
                );
                if (node != undefined) {
                  this.selectedNode(node);
                  this.expandedNodes.push(node.id);
                }
              } else {
                this.clearSelectedNodes();
                this.$root.toastError(rs.message);
              }
            }
          } finally {
            this.$root.showLoading(false);
          }
        }
      }
    },
    getNodesFiltered: function (textFiltered) {
      let result = [];
      if (this.dsNodes != null) {
        let items = this.dsNodes.filter((item) =>
          item.text
            .toString()
            .toLowerCase()
            .includes(textFiltered.toString().toLowerCase())
        );
        if (!(items == null || items.length == 0)) {
          for (let i = 0; i < items.length; i++) {
            const item = items[i];
            let ck = result.findIndex(
              (x) => x.id == item.id && x.parentID == item.parentID
            );
            if (ck < 0) {
              result.push(item);
              if (item.parentID != null) {
                let ps = this.getParents(item.parentID);
                if (!(ps == null || ps.length == 0)) {
                  for (let j = 0; j < ps.length; j++) {
                    ck = result.findIndex(
                      (x) => x.id == ps[j].id && x.parentID == ps[j].parentID
                    );
                    if (ck < 0) result.push(ps[j]);
                  }
                }
              }
            }
          }
        }
      }
      return result;
    },
    loadNodes: async function () {
      this.dsNodes = [];
      let nodes = await this.getDsNodeDonVi();
      //Set hasChildren
      for (let i = 0; i < nodes.length; i++) {
        const node = nodes[i];
        let ck = nodes.findIndex((x) => x.parentID == node.id);
        if (ck >= 0) {
          node.hasChildren = true;
        }
      }
      this.dsNodes = nodes;
    },
    getDsNodeDonVi: async function () {
      let result = [];
      let items = await this.getDmDonVi();
      if (!(items == null || items.length == 0)) {
        items.forEach((item) => {
          let node = this.createNode(
            item.ITEM_ID,
            item.ITEM_TYPE,
            item.ITEM_NAME,
            item.ITEM_PARENT_ID,
            item.ITEM_PARENT_TYPE,
            item.ITEM_LVL,
            item.ITEM_ATTS
          );
          if (node.attributes.SO_TURACK != "0") {
            node.hasChildren = true;
          }
          result.push(node);
        });
      }
      return result;
    },
    loadDsNodeThietBi: async function (donViID) {
      let result = [];
      let nodeID = ThietBi.PRE_DONVI + ThietBi.SEPARATOR + donViID.toString();
      let currentChilds = this.getChilds(nodeID);
      for (let i = 0; i < this.dsNodes.length; i++) {
        const node = this.dsNodes[i];
        let ck = true;
        for (let j = 0; j < currentChilds.length; j++) {
          if (currentChilds[j].id == node.id) {
            ck = false;
            break;
          }
        }
        if (ck) {
          result.push(node);
        }
      }
      let nodes = await this.getDsNodeThietBi(donViID);
      if (!this.checkIsNull(nodes)) {
        nodes.forEach((node) => {
          result.push(node);
        });
      }
      //Set hasChildren
      for (let i = 0; i < result.length; i++) {
        const node = result[i];
        let ck = result.findIndex((x) => x.parentID == node.id);
        if (ck >= 0) {
          node.hasChildren = true;
        }
      }
      this.dsNodes = result;
    },
    getDsNodeThietBi: async function (donViID) {
      let result = [];
      let items = await this.getDmThietBi(donViID);
      if (!(items == null || items.length == 0)) {
        items.forEach((item) => {
          let node = this.createNode(
            item.ITEM_ID,
            item.ITEM_TYPE,
            item.ITEM_NAME,
            item.ITEM_PARENT_ID,
            item.ITEM_PARENT_TYPE,
            item.ITEM_LVL,
            item.ITEM_ATTS
          );
          result.push(node);
        });
      }

      //Luu y: cai dat lai parentID cho ports neu remove thiet bi
      //BRAS
      let brasItems = result.filter((x) => x.attributes.type == ThietBi.PRE_BRAS);
      let cardBrasItems = result.filter(
        (x) => x.attributes.type == ThietBi.PRE_BRAS_CARD
      );
      for (let i = 0; i < brasItems.length; i++) {
        const brasItem = brasItems[i];
        if (brasItem.attributes.SO_SUBSLOT != 0) {
          for (
            let j = parseInt(brasItem.attributes.SLOT_BD);
            j <
            parseInt(brasItem.attributes.SO_SLOT) + parseInt(brasItem.attributes.SLOT_BD);
            j++
          ) {
            let nodeSlot = this.createNodeSlot(j, ThietBi.PRE_BRAS_SLOT, brasItem);
            nodeSlot.text = "S" + nodeSlot.text;
            result.push(nodeSlot);
            for (let k = 0; k < parseInt(brasItem.attributes.SO_SUBSLOT); k++) {
              let nodeSubSlot = this.createNodeSlot(
                k,
                ThietBi.PRE_BRAS_SLOT_SUB,
                nodeSlot
              );
              nodeSubSlot.text = "SubSlot" + nodeSubSlot.text;
              if (nodeSubSlot != null) {
                for (let l = 0; l < cardBrasItems.length; l++) {
                  const cardBrasItem = cardBrasItems[l];
                  if (
                    cardBrasItem.attributes.SLOT == nodeSlot.attributes.slotIndex &&
                    cardBrasItem.attributes.VITRI == nodeSubSlot.attributes.slotIndex
                  ) {
                    nodeSubSlot.text += " " + cardBrasItem.text;
                    nodeSubSlot.attributes.id = cardBrasItem.attributes.id;
                    nodeSubSlot.attributes.type = ThietBi.PRE_BRAS_CARD;
                    nodeSubSlot.icon = ThietBi.PRE_BRAS_CARD.toLowerCase();
                    if (cardBrasItem.attributes.NHOMCARD_ID == 1)
                      nodeSubSlot.icon +=
                        ThietBi.SEPARATOR +
                        cardBrasItem.attributes.NHOMCARD_ID.toString();

                    let childs = result.filter((x) => x.parentID == cardBrasItem.id);
                    childs.forEach((child) => {
                      child.parentID = nodeSubSlot.id;
                    });
                  }
                }
                result.push(nodeSubSlot);
              }
            }
          }
        }
      }
      let portCardBrasItems = result.filter(
        (x) => x.attributes.type == ThietBi.PRE_BRAS_CARD_PORT
      );
      portCardBrasItems.forEach((portCardBrasItem) => {
        if (
          portCardBrasItem.attributes.TT_PORT_ID >= 1 &&
          portCardBrasItem.attributes.TT_PORT_ID <= 9
        ) {
          portCardBrasItem.icon +=
            ThietBi.SEPARATOR + portCardBrasItem.attributes.TT_PORT_ID.toString();
        }
      });
      for (let i = 0; i < cardBrasItems.length; i++) {
        result = result.filter((x) => x.id != cardBrasItems[i].id);
      }

      //DSLAM
      let dsLamFrameShelfs = result.filter(
        (x) => x.attributes.type == ThietBi.PRE_DSLAM_FRAME_SHELF
      );
      for (let i = 0; i < dsLamFrameShelfs.length; i++) {
        const dsLamFrameShelf = dsLamFrameShelfs[i];
        if (dsLamFrameShelf.attributes.SO_SLOT > 0) {
          for (
            let j = parseInt(dsLamFrameShelf.attributes.SLOT_BD);
            j <
            parseInt(dsLamFrameShelf.attributes.SLOT_BD) +
              parseInt(dsLamFrameShelf.attributes.SO_SLOT);
            j++
          ) {
            let nodeSlot = this.createNodeSlot(
              j,
              ThietBi.PRE_DSLAM_FRAME_SHELF_SLOT,
              dsLamFrameShelf
            );
            if (nodeSlot != null) result.push(nodeSlot);
          }
        }
      }
      let dsLamFrameShelfCards = result.filter(
        (x) => x.attributes.type == ThietBi.PRE_DSLAM_FRAME_SHELF_CARD
      );
      for (let i = 0; i < dsLamFrameShelfCards.length; i++) {
        const dsLamFrameShelfCard = dsLamFrameShelfCards[i];
        let nodeSlot = result.find(
          (x) =>
            x.attributes.slotIndex == dsLamFrameShelfCard.attributes.VITRI &&
            x.attributes.slotType == ThietBi.PRE_DSLAM_FRAME_SHELF_SLOT &&
            x.attributes.parentID == dsLamFrameShelfCard.attributes.parentID &&
            x.attributes.parentType == dsLamFrameShelfCard.attributes.parentType &&
            x.attributes.TURACK == dsLamFrameShelfCard.attributes.TURACK
        );
        if (nodeSlot != null) {
          nodeSlot.text += " " + dsLamFrameShelfCard.attributes.LOAI_CARD;
          nodeSlot.attributes.id = dsLamFrameShelfCard.attributes.id;
          nodeSlot.attributes.type = dsLamFrameShelfCard.attributes.type;
          nodeSlot.icon = ThietBi.PRE_DSLAM_FRAME_SHELF_CARD.toLowerCase();
          if (dsLamFrameShelfCard.attributes.NHOMCARD_ID == 1)
            nodeSlot.icon +=
              ThietBi.SEPARATOR + dsLamFrameShelfCard.attributes.NHOMCARD_ID.toString();

          let childs = result.filter((x) => x.parentID == dsLamFrameShelfCard.id);
          childs.forEach((child) => {
            child.parentID = nodeSlot.id;
          });
        }
      }
      for (let i = 0; i < dsLamFrameShelfCards.length; i++) {
        result = result.filter((x) => x.id != dsLamFrameShelfCards[i].id);
      }
      let dsLamFrameShelfCardPorts = result.filter(
        (x) => x.attributes.type == ThietBi.PRE_DSLAM_FRAME_SHELF_CARD_PORT
      );
      for (let i = 0; i < dsLamFrameShelfCardPorts.length; i++) {
        const dsLamFrameShelfCardPort = dsLamFrameShelfCardPorts[i];
        dsLamFrameShelfCardPort.text = this.getNumberString(
          dsLamFrameShelfCardPort.attributes.VITRI,
          "Port "
        );
        if (
          dsLamFrameShelfCardPort.attributes.TT_PORT_ID >= 1 &&
          dsLamFrameShelfCardPort.attributes.TT_PORT_ID <= 9
        ) {
          dsLamFrameShelfCardPort.icon +=
            ThietBi.SEPARATOR + dsLamFrameShelfCardPort.attributes.TT_PORT_ID.toString();
        }
      }
      let dsLamFrameShelfCardModules = result.filter(
        (x) => x.attributes.type == ThietBi.PRE_DSLAM_FRAME_SHELF_CARD_MODULE
      );
      for (let i = 0; i < dsLamFrameShelfCardModules.length; i++) {
        const dsLamFrameShelfCardModule = dsLamFrameShelfCardModules[i];
        dsLamFrameShelfCardModule.text =
          this.getNumberString(dsLamFrameShelfCardModule.attributes.VITRI) +
          " " +
          dsLamFrameShelfCardModule.text;
      }
      let dsLamFrameShelfCardModulePorts = result.filter(
        (x) => x.attributes.type == ThietBi.PRE_DSLAM_FRAME_SHELF_CARD_MODULE_PORT
      );
      for (let i = 0; i < dsLamFrameShelfCardModulePorts.length; i++) {
        const dsLamFrameShelfCardModulePort = dsLamFrameShelfCardModulePorts[i];
        dsLamFrameShelfCardModulePort.text = this.getNumberString(
          dsLamFrameShelfCardModulePort.attributes.VITRI,
          "Port "
        );
        if (
          dsLamFrameShelfCardModulePort.attributes.TT_PORT_ID >= 1 &&
          dsLamFrameShelfCardModulePort.attributes.TT_PORT_ID <= 9
        ) {
          dsLamFrameShelfCardModulePort.icon +=
            ThietBi.SEPARATOR +
            dsLamFrameShelfCardModulePort.attributes.TT_PORT_ID.toString();
        }
      }

      //FRAMES
      let frames = result.filter((x) => x.attributes.type == ThietBi.PRE_FRAME);
      for (let i = 0; i < frames.length; i++) {
        const frame = frames[i];
        if (frame.attributes.SO_SLOT > 0) {
          for (
            let j = parseInt(frame.attributes.SLOT_BD);
            j < parseInt(frame.attributes.SLOT_BD) + parseInt(frame.attributes.SO_SLOT);
            j++
          ) {
            let nodeSlot = this.createNodeSlot(j, ThietBi.PRE_FRAME_SLOT, frame);
            if (nodeSlot != null) result.push(nodeSlot);
          }
        }
      }
      let frameCards = result.filter((x) => x.attributes.type == ThietBi.PRE_FRAME_CARD);
      for (let i = 0; i < frameCards.length; i++) {
        const frameCard = frameCards[i];
        let nodeSlot = result.find(
          (x) =>
            x.attributes.slotIndex == frameCard.attributes.VITRI &&
            x.attributes.slotType == ThietBi.PRE_FRAME_SLOT &&
            x.attributes.parentID == frameCard.attributes.parentID &&
            x.attributes.parentType == frameCard.attributes.parentType &&
            x.attributes.TURACK == frameCard.attributes.TURACK
        );
        if (nodeSlot != null) {
          nodeSlot.text += " " + frameCard.text;
          nodeSlot.attributes.id = frameCard.attributes.id;
          nodeSlot.attributes.type = frameCard.attributes.type;
          nodeSlot.icon = ThietBi.PRE_FRAME_CARD.toLowerCase();
          if (frameCard.attributes.NHOMCARD_ID == 1)
            nodeSlot.icon +=
              ThietBi.SEPARATOR + frameCard.attributes.NHOMCARD_ID.toString();

          let childs = result.filter((x) => x.parentID == frameCard.id);
          childs.forEach((child) => {
            child.parentID = nodeSlot.id;
          });
        }
      }
      for (let i = 0; i < frameCards.length; i++) {
        result = result.filter((x) => x.id != frameCards[i].id);
      }

      //GPONS
      let gpons = result.filter((x) => x.attributes.type == ThietBi.PRE_GPON);
      for (let i = 0; i < gpons.length; i++) {
        const gpon = gpons[i];
        if (gpon.attributes.SO_SLOT > 0) {
          for (
            let j = parseInt(gpon.attributes.CARD_BD);
            j < parseInt(gpon.attributes.CARD_BD) + parseInt(gpon.attributes.SO_SLOT);
            j++
          ) {
            let nodeSlot = this.createNodeSlot(j, ThietBi.PRE_GPON_SLOT, gpon);
            if (nodeSlot != null) result.push(nodeSlot);
          }
        }
      }
      let gponCards = result.filter((x) => x.attributes.type == ThietBi.PRE_GPON_CARD);
      for (let i = 0; i < gponCards.length; i++) {
        const gponCard = gponCards[i];
        let nodeSlot = result.find(
          (x) =>
            x.attributes.slotIndex == gponCard.attributes.VITRI &&
            x.attributes.slotType == ThietBi.PRE_GPON_SLOT &&
            x.attributes.parentID == gponCard.attributes.parentID &&
            x.attributes.parentType == gponCard.attributes.parentType &&
            x.attributes.TURACK == gponCard.attributes.TURACK
        );
        if (nodeSlot != null) {
          nodeSlot.text += " " + gponCard.text;
          nodeSlot.attributes.id = gponCard.attributes.id;
          nodeSlot.attributes.type = gponCard.attributes.type;
          nodeSlot.icon = ThietBi.PRE_GPON_CARD.toLowerCase();
          if (gponCard.attributes.NHOMCARD_ID == 1)
            nodeSlot.icon +=
              ThietBi.SEPARATOR + gponCard.attributes.NHOMCARD_ID.toString();

          let childs = result.filter((x) => x.parentID == gponCard.id);
          childs.forEach((child) => {
            child.parentID = nodeSlot.id;
          });
        }
      }
      for (let i = 0; i < gponCards.length; i++) {
        result = result.filter((x) => x.id != gponCards[i].id);
      }
      let gponCardPorts = result.filter(
        (x) => x.attributes.type == ThietBi.PRE_GPON_CARD_PORT
      );
      for (let i = 0; i < gponCardPorts.length; i++) {
        const gponCardPort = gponCardPorts[i];
        gponCardPort.text = this.getNumberString(gponCardPort.attributes.VITRI, "Port ");
        if (
          gponCardPort.attributes.TT_PORT_ID >= 1 &&
          gponCardPort.attributes.TT_PORT_ID <= 9
        ) {
          gponCardPort.icon +=
            ThietBi.SEPARATOR + gponCardPort.attributes.TT_PORT_ID.toString();
        }
      }

      //MANES
      let manes = result.filter((x) => x.attributes.type == ThietBi.PRE_MANE);
      for (let i = 0; i < manes.length; i++) {
        const mane = manes[i];
        if (mane.attributes.SO_SLOT > 0) {
          for (
            let j = parseInt(mane.attributes.CARD_BD);
            j < parseInt(mane.attributes.SO_SLOT) + parseInt(mane.attributes.CARD_BD);
            j++
          ) {
            let nodeSlot = this.createNodeSlot(j, ThietBi.PRE_MANE_SLOT, mane);
            if (nodeSlot != null) result.push(nodeSlot);
          }
        }
      }
      let maneCards = result.filter((x) => x.attributes.type == ThietBi.PRE_MANE_CARD);
      for (let i = 0; i < maneCards.length; i++) {
        const maneCard = maneCards[i];
        let nodeSlot = result.find(
          (x) =>
            x.attributes.slotIndex == maneCard.attributes.VITRI &&
            x.attributes.slotType == ThietBi.PRE_MANE_SLOT &&
            x.attributes.parentID == maneCard.attributes.parentID &&
            x.attributes.parentType == maneCard.attributes.parentType &&
            x.attributes.TURACK == maneCard.attributes.TURACK
        );
        if (nodeSlot != null) {
          nodeSlot.text += " " + maneCard.text;
          nodeSlot.attributes.id = maneCard.attributes.id;
          nodeSlot.attributes.type = maneCard.attributes.type;
          nodeSlot.icon = ThietBi.PRE_MANE_CARD.toLowerCase();
          if (maneCard.attributes.NHOMCARD_ID == 1)
            nodeSlot.icon +=
              ThietBi.SEPARATOR + maneCard.attributes.NHOMCARD_ID.toString();

          let childs = result.filter((x) => x.parentID == maneCard.id);
          childs.forEach((child) => {
            child.parentID = nodeSlot.id;
          });
        }
      }
      for (let i = 0; i < maneCards.length; i++) {
        result = result.filter((x) => x.id != maneCards[i].id);
      }
      let maneCardPorts = result.filter(
        (x) => x.attributes.type == ThietBi.PRE_MANE_CARD_PORT
      );
      for (let i = 0; i < maneCardPorts.length; i++) {
        const maneCardPort = maneCardPorts[i];
        maneCardPort.text = this.getNumberString(maneCardPort.attributes.VITRI, "Port ");
        if (
          maneCardPort.attributes.TT_PORT_ID >= 1 &&
          maneCardPort.attributes.TT_PORT_ID <= 9
        ) {
          maneCardPort.icon +=
            ThietBi.SEPARATOR + maneCardPort.attributes.TT_PORT_ID.toString();
        }
      }
      let maneCardModules = result.filter(
        (x) => x.attributes.type == ThietBi.PRE_MANE_CARD_MODULE
      );
      for (let i = 0; i < maneCardModules.length; i++) {
        const maneCardModule = maneCardModules[i];
        maneCardModule.text =
          this.getNumberString(maneCardModule.attributes.VITRI) +
          " " +
          maneCardModule.text;
      }
      let maneCardModulePorts = result.filter(
        (x) => x.attributes.type == ThietBi.PRE_MANE_CARD_MODULE_PORT
      );
      for (let i = 0; i < maneCardModulePorts.length; i++) {
        const maneCardModulePort = maneCardModulePorts[i];
        maneCardModulePort.text = this.getNumberString(
          maneCardModulePort.attributes.VITRI,
          "Port "
        );
        if (
          maneCardModulePort.attributes.TT_PORT_ID >= 1 &&
          maneCardModulePort.attributes.TT_PORT_ID <= 9
        ) {
          maneCardModulePort.icon +=
            ThietBi.SEPARATOR + maneCardModulePort.attributes.TT_PORT_ID.toString();
        }
      }

      //SWITCH
      let switchPorts = result.filter(
        (x) => x.attributes.type == ThietBi.PRE_SWITCH_PORT
      );
      result = result.filter((x) => x.attributes.type != ThietBi.PRE_SWITCH_PORT);
      let switchCards = result.filter(
        (x) => x.attributes.type == ThietBi.PRE_SWITCH_CARD
      );
      result = result.filter((x) => x.attributes.type != ThietBi.PRE_SWITCH_CARD);
      let switchCardPorts = result.filter(
        (x) => x.attributes.type == ThietBi.PRE_SWITCH_CARD_PORT
      );
      result = result.filter((x) => x.attributes.type != ThietBi.PRE_SWITCH_CARD_PORT);

      let switches = result.filter((x) => x.attributes.type == ThietBi.PRE_SWITCH);
      for (let i = 0; i < switches.length; i++) {
        let cards = switchCards.filter(
          (x) => x.attributes.parentID == switches[i].attributes.id
        );
        if (switches[i].attributes.SO_SLOT > 0) {
          for (
            let j = parseInt(switches[i].attributes.CARD_BD);
            j <
            parseInt(switches[i].attributes.CARD_BD) +
              parseInt(switches[i].attributes.SO_SLOT);
            j++
          ) {
            let nodeSlot = this.createNodeSlot(j, ThietBi.PRE_SWITCH_SLOT, switches[i]);
            if (nodeSlot != null) result.push(nodeSlot);
          }
          if (parseInt(switches[i].attributes.SUB_SLOT) > 0) {
            let nodeSlots = result.filter(
              (x) =>
                x.attributes.parentID == switches[i].attributes.id &&
                x.attributes.parentType == switches[i].attributes.type &&
                x.attributes.type == ThietBi.PRE_SWITCH_SLOT
            );
            if (nodeSlots.length > 0) {
              nodeSlots.forEach((nodeSlot) => {
                nodeSlot.text = "Slot " + nodeSlot.text;
                for (let k = 0; k < parseInt(switches[i].attributes.SUB_SLOT); k++) {
                  let nodeSubSlot = this.createNodeSlot(
                    k,
                    ThietBi.PRE_SWITCH_SLOT_SUB,
                    nodeSlot
                  );
                  nodeSubSlot.text = "SubSlot" + nodeSubSlot.text;
                  result.push(nodeSubSlot);
                  if (cards.length > 0) {
                    let switchCard = cards.find(
                      (x) =>
                        x.attributes.parentID == nodeSlot.attributes.parentID &&
                        x.attributes.SLOT == nodeSlot.attributes.slotIndex &&
                        x.attributes.VITRI == k
                    );
                    if (!this.checkIsNull(switchCard)) {
                      nodeSubSlot.text += " " + switchCard.text;
                      nodeSubSlot.attributes.id = switchCard.attributes.id;
                      nodeSubSlot.attributes.type = switchCard.attributes.type;
                      nodeSubSlot.icon = switchCard.icon;
                      if (switchCard.attributes.NHOMCARD_ID == 1)
                        nodeSubSlot.icon +=
                          ThietBi.SEPARATOR +
                          switchCard.attributes.NHOMCARD_ID.toString();

                      let cardPorts = switchCardPorts.filter(
                        (x) => x.attributes.parentID == nodeSubSlot.attributes.id
                      );

                      for (let k1 = 0; k1 < cardPorts.length; k1++) {
                        const switchCardPort = cardPorts[k1];
                        switchCardPort.text = this.getNumberString(
                          switchCardPort.attributes.VITRI,
                          "Port "
                        );
                        switchCardPort.parentID = nodeSubSlot.id;
                        if (
                          switchCardPort.attributes.TT_PORT_ID >= 1 &&
                          switchCardPort.attributes.TT_PORT_ID <= 9
                        ) {
                          switchCardPort.icon +=
                            ThietBi.SEPARATOR +
                            switchCardPort.attributes.TT_PORT_ID.toString();
                        }
                        result.push(switchCardPort);
                      }
                    }
                  }
                }
              });
            }
          } else {
            for (let j = 0; j < cards.length; j++) {
              const switchCard = cards[j];
              let nodeSlot = result.find(
                (x) =>
                  x.attributes.slotIndex == switchCard.attributes.SLOT &&
                  x.attributes.slotType == ThietBi.PRE_SWITCH_SLOT &&
                  x.attributes.parentID == switchCard.attributes.parentID &&
                  x.attributes.parentType == switchCard.attributes.parentType &&
                  x.attributes.TURACK == switchCard.attributes.TURACK
              );
              if (nodeSlot != null) {
                nodeSlot.text += " " + switchCard.text;
                nodeSlot.attributes.id = switchCard.attributes.id;
                nodeSlot.attributes.type = switchCard.attributes.type;
                nodeSlot.icon = switchCard.icon;
                if (switchCard.attributes.NHOMCARD_ID == 1)
                  nodeSlot.icon +=
                    ThietBi.SEPARATOR + switchCard.attributes.NHOMCARD_ID.toString();

                // let childs = result.filter((x) => x.parentID == switchCard.id);
                // childs.forEach((child) => {
                //   child.parentID = nodeSlot.id;
                // });

                let cardPorts = switchCardPorts.filter(
                  (x) => x.attributes.parentID == nodeSlot.attributes.id
                );

                for (let k1 = 0; k1 < cardPorts.length; k1++) {
                  const switchCardPort = cardPorts[k1];
                  switchCardPort.text = this.getNumberString(
                    switchCardPort.attributes.VITRI,
                    "Port "
                  );
                  switchCardPort.parentID = nodeSlot.id;
                  if (
                    switchCardPort.attributes.TT_PORT_ID >= 1 &&
                    switchCardPort.attributes.TT_PORT_ID <= 9
                  ) {
                    switchCardPort.icon +=
                      ThietBi.SEPARATOR + switchCardPort.attributes.TT_PORT_ID.toString();
                  }
                  result.push(switchCardPort);
                }
              }
            }
          }
        } else {
          let ports = switchPorts.filter(
            (x) => x.attributes.parentID == switches[i].attributes.id
          );
          for (let i = 0; i < ports.length; i++) {
            const switchPort = ports[i];
            switchPort.text = this.getNumberString(switchPort.attributes.VITRI, "Port ");
            if (
              switchPort.attributes.TT_PORT_ID >= 1 &&
              switchPort.attributes.TT_PORT_ID <= 9
            ) {
              switchPort.icon +=
                ThietBi.SEPARATOR + switchPort.attributes.TT_PORT_ID.toString();
            }
            result.push(switchPort);
          }
        }
      }

      // for (let i = 0; i < switchPorts.length; i++) {
      //   const switchPort = switchPorts[i];
      //   switchPort.text = this.getNumberString(
      //     switchPort.attributes.VITRI,
      //     "Port "
      //   );
      //   if (
      //     switchPort.attributes.TT_PORT_ID >= 1 &&
      //     switchPort.attributes.TT_PORT_ID <= 9
      //   ) {
      //     switchPort.icon +=
      //       ThietBi.SEPARATOR + switchPort.attributes.TT_PORT_ID.toString();
      //   }
      // }
      // for (let i = 0; i < switchCards.length; i++) {
      //   const switchCard = switchCards[i];
      //   let nodeSlot = result.find(
      //     (x) =>
      //       x.attributes.slotIndex == switchCard.attributes.VITRI &&
      //       x.attributes.slotType == ThietBi.PRE_SWITCH_SLOT &&
      //       x.attributes.parentID == switchCard.attributes.parentID &&
      //       x.attributes.parentType == switchCard.attributes.parentType &&
      //       x.attributes.TURACK == switchCard.attributes.TURACK
      //   );
      //   if (nodeSlot != null) {
      //     nodeSlot.text += " " + switchCard.text;
      //     nodeSlot.attributes.id = switchCard.attributes.id;
      //     nodeSlot.attributes.type = switchCard.attributes.type;
      //     nodeSlot.icon = switchCard.icon;
      //     if (switchCard.attributes.NHOMCARD_ID == 1)
      //       nodeSlot.icon +=
      //         ThietBi.SEPARATOR + switchCard.attributes.NHOMCARD_ID.toString();

      //     let childs = result.filter((x) => x.parentID == switchCard.id);
      //     childs.forEach((child) => {
      //       child.parentID = nodeSlot.id;
      //     });
      //   }
      // }
      // for (let i = 0; i < switchCards.length; i++) {
      //   result = result.filter((x) => x.id != switchCards[i].id);
      // }
      // for (let i = 0; i < switchCardPorts.length; i++) {
      //   const switchCardPort = switchCardPorts[i];
      //   switchCardPort.text = this.getNumberString(
      //     switchCardPort.attributes.VITRI,
      //     "Port "
      //   );
      //   if (
      //     switchCardPort.attributes.TT_PORT_ID >= 1 &&
      //     switchCardPort.attributes.TT_PORT_ID <= 9
      //   ) {
      //     switchCardPort.icon +=
      //       ThietBi.SEPARATOR + switchCardPort.attributes.TT_PORT_ID.toString();
      //   }
      // }

      //EWSD
      let ewsds = result.filter((x) => x.attributes.type == ThietBi.PRE_EWSD);
      for (let i = 0; i < ewsds.length; i++) {
        ewsds[i].icon += ThietBi.SEPARATOR + ewsds[i].attributes.LOAI_EWSD_ID.toString();
      }
      let ewsdShelfs = result.filter((x) => x.attributes.type == ThietBi.PRE_EWSD_SHELF);
      for (let i = 0; i < ewsdShelfs.length; i++) {
        if (ewsdShelfs[i].attributes.SO_SLOT > 0) {
          for (let j = 0; j < parseInt(ewsdShelfs[i].attributes.SO_SLOT); j++) {
            let nodeSlot = this.createNodeSlot(
              j,
              ThietBi.PRE_EWSD_SHELF_SLOT,
              ewsdShelfs[i]
            );
            if (nodeSlot != null) result.push(nodeSlot);
          }
        }
      }
      let ewsdShelfCards = result.filter(
        (x) => x.attributes.type == ThietBi.PRE_EWSD_SHELF_CARD
      );
      for (let i = 0; i < ewsdShelfCards.length; i++) {
        const ewsdShelfCard = ewsdShelfCards[i];
        let nodeSlot = result.find(
          (x) =>
            x.attributes.slotIndex == ewsdShelfCard.attributes.VITRI &&
            x.attributes.slotType == ThietBi.PRE_EWSD_SHELF_SLOT &&
            x.attributes.parentID == ewsdShelfCard.attributes.parentID &&
            x.attributes.parentType == ewsdShelfCard.attributes.parentType &&
            x.attributes.TURACK == ewsdShelfCard.attributes.TURACK
        );
        if (nodeSlot != null) {
          nodeSlot.text += " " + ewsdShelfCard.text;
          nodeSlot.attributes.id = ewsdShelfCard.attributes.id;
          nodeSlot.attributes.type = ewsdShelfCard.attributes.type;
          nodeSlot.icon = ThietBi.PRE_EWSD_SHELF_CARD.toLowerCase();
          if (ewsdShelfCard.NHOMCARD_ID == 1)
            nodeSlot.icon += ThietBi.SEPARATOR + ewsdShelfCard.NHOMCARD_ID.toString();

          let childs = result.filter((x) => x.parentID == ewsdShelfCard.id);
          childs.forEach((child) => {
            child.parentID = nodeSlot.id;
          });
        }
      }
      for (let i = 0; i < ewsdShelfCards.length; i++) {
        result = result.filter((x) => x.id != ewsdShelfCards[i].id);
      }
      return result;
    },
    createNodeID: function (type, id, rackID) {
      let result = null;
      if (!this.checkIsEmpty(id)) {
        result = type + ThietBi.SEPARATOR + id.toString();
        if (!this.checkIsEmpty(rackID)) {
          if (!(type == ThietBi.PRE_DONVI || type == ThietBi.PRE_TURACK)) {
            result += ThietBi.SEPARATOR + rackID.toString();
          }
        }
      }
      return result;
    },
    createNode: function (id, type, text, parentID, parentType, level, attributes) {
      let attrs = {};
      let icon = type.toString().toLowerCase();
      if (type == ThietBi.PRE_DONVI) {
        icon += ThietBi.SEPARATOR + level.toString();
      }
      attrs.id = id;
      attrs.type = type;
      attrs.parentID = parentID;
      attrs.parentType = parentType;
      attrs.level = level;
      if (!this.checkIsNull(attributes)) {
        let attrStrings = attributes.split(";");
        if (attrStrings.length > 0) {
          attrStrings.forEach((attrString) => {
            let items = attrString.split(":");
            if (items.length == 2) {
              attrs[items[0]] = items[1];
            }
          });
        }
      }
      let nodeID = this.createNodeID(type, id, attrs.TURACK);
      let nodeParentID = this.createNodeID(parentType, parentID, attrs.TURACK);
      return {
        id: nodeID,
        text: this.checkIsNull(text) ? "(null)" : text,
        parentID: nodeParentID,
        icon: icon,
        hasChildren: false,
        // expanded: false,
        // selected: false,
        attributes: attrs,
      };
    },
    createNodeSlot: function (slotIndex, slotType, parentNode) {
      let nodeSlot = this.createNode(
        parentNode.attributes.id + ThietBi.SEPARATOR + slotIndex,
        slotType,
        this.getNumberString(slotIndex),
        parentNode.attributes.id,
        parentNode.attributes.type,
        parentNode.attributes.level + 1,
        "TURACK:" +
          parentNode.attributes.TURACK +
          ";slotIndex:" +
          slotIndex +
          ";slotType:" +
          slotType
      );
      return nodeSlot;
    },
    createNodePort: function (portIndex, portType, parentNode) {
      let nodePort = this.createNode(
        parentNode.attributes.id + ThietBi.SEPARATOR + portIndex,
        portType,
        this.getNumberString(portIndex, "Port "),
        parentNode.attributes.id,
        parentNode.attributes.type,
        parentNode.attributes.level + 1,
        "TURACK:" +
          parentNode.attributes.TURACK +
          ";portIndex:" +
          portIndex +
          ";portType:" +
          portType
      );
      return nodePort;
    },
    getSelectedNode: function () {
      if (this.selectedNodes.length > 0) {
        return this.dsNodes.find((x) => x.id == this.selectedNodes[0]);
      } else {
        return null;
      }
    },
    getNode: function (nodeID) {
      return this.dsNodes.find((x) => x.id == nodeID);
    },
    getNodes: function (itemType, itemID) {
      return this.dsNodes.filter(
        (x) => x.attributes.type == itemType && x.attributes.id == itemID
      );
    },
    selectedNode: function (node) {
      if (node != null) {
        this.collapseAll();
        if (node.parentID != null) {
          let ps = this.getParents(node.parentID);
          if (!(ps == null || ps.length == 0)) {
            for (let i = 0; i < ps.length; i++) {
              this.expandNode(ps[i].id);
            }
          }
        }
        node.selected = true;
        this.selectedNodes = [node.id];
      }
    },
    clearSelectedNodes: function () {
      for (let i = 0; i < this.selectedNodes.length; i++) {
        const selectedNode = this.getNode(this.selectedNodes[i]);
        if (selectedNode != undefined) selectedNode.selected = false;
      }
      this.selectedNodes = [];
      this.collapseAll();
      let nodes = this.dsNodesSorted.filter((x) => x.parentID == null);
      if (nodes.length > 0) {
        this.selectedNode(nodes[0]);
      }
    },
    collapseAll: function () {
      if (this.expandedNodes.length > 0) {
        for (let i = 0; i < this.expandedNodes.length; i++) {
          let node = this.getNode(this.expandedNodes[i]);
          if (node != undefined) {
            node.expanded = false;
          }
        }
        this.expandedNodes = [];
      }
    },
    collapseNode: function (nodeID) {
      let node = this.getNode(nodeID);
      if (node != undefined) {
        var i = this.expandedNodes.indexOf(node.id);
        if (i > -1) {
          this.expandedNodes.splice(i, 1);
        }
        node.expanded = false;
      }
    },
    expandNode: function (nodeID) {
      let node = this.getNode(nodeID);
      if (node != undefined) {
        this.expandedNodes.push(node.id);
        node.expanded = true;
      }
    },
    selectedNodeItem: function (type, id, tuRackID) {
      let nodes = this.getNodes(type, id);
      if (nodes.length > 0) {
        if (this.checkIsNull(tuRackID)) this.selectedNode(nodes[0]);
        else {
          for (let i = 0; i < nodes.length; i++) {
            const node = nodes[i];
            if (
              node.attributes.type == ThietBi.PRE_TURACK ||
              node.attributes.type == ThietBi.PRE_DONVI ||
              node.attributes.TURACK == tuRackID
            ) {
              this.selectedNode(node);
              break;
            }
          }
        }
      }
    },
    refreshTreeFromNodeCurrentSelected: async function () {
      if (this.selectedNodes.length > 0) {
        let nodeDonVi = this.getDonViIDFromNode(this.selectedNodes[0]);
        if (nodeDonVi != null) {
          await this.loadDsNodeThietBi(nodeDonVi.attributes.id);
        }
      }
    },
    refreshTreeFromNode: async function (nodeID, selected) {
      let nodeDonVi = this.getDonViIDFromNode(nodeID);
      if (nodeDonVi != null) {
        await this.loadDsNodeThietBi(nodeDonVi.attributes.id);
        if (selected === true) {
          let node = this.getNode(nodeID);
          if (node != null) {
            this.selectedNode(node);
          }
        }
      }
    },
    refreshTreeFromNodeItem: async function (type, id, selected, tuRackID) {
      let donViID = this.checkIsEmpty(tuRackID)
        ? this.getDonViIDFromNodeItem(type, id)
        : this.getDonViID(tuRackID);
      if (donViID != null) {
        await this.loadDsNodeThietBi(donViID);
        if (selected === true) {
          this.selectedNodeItem(type, id, tuRackID);
        }
      }
    },
    refreshTreeFromDonVi: async function (donViID, selectedType, selectedID, tuRackID) {
      let nodeDonVi = this.dsNodes.find(
        (x) => x.attributes.type == ThietBi.PRE_DONVI && x.attributes.id == donViID
      );
      if (nodeDonVi != null) {
        await this.loadDsNodeThietBi(nodeDonVi.attributes.id);
        if (!(this.checkIsNull(selectedType) || this.checkIsNull(selectedID))) {
          this.selectedNodeItem(selectedType, selectedID, tuRackID);
        }
      }
    },
    getChilds: function (nodeParentID) {
      let result = [];
      let childs = this.dsNodes.filter((x) => x.parentID == nodeParentID);
      if (!this.checkIsNull(childs)) {
        for (let i = 0; i < childs.length; i++) {
          const child = childs[i];
          result.push(child);
          let nextChilds = this.getChilds(child.id);
          if (!this.checkIsNull(nextChilds)) {
            for (let j = 0; j < nextChilds.length; j++) {
              result.push(nextChilds[j]);
            }
          }
        }
      }
      return result;
    },
    getParents: function (nodeParentID) {
      let rs = [];
      let parent = this.dsNodes.find((x) => x.id == nodeParentID);
      if (parent != null) {
        rs.push(parent);
        if (parent.parentID != null) {
          let ps = this.getParents(parent.parentID);
          if (!(ps == null || ps.length == 0)) {
            for (let i = 0; i < ps.length; i++) {
              rs.push(ps[i]);
            }
          }
        }
      }
      return rs;
    },
    getDonViID: function (tuRackID) {
      let donViID = null;
      let nodeID = this.createNodeID(ThietBi.PRE_TURACK, tuRackID);
      let node = this.getNode(nodeID);
      if (node != null) {
        donViID = node.attributes.parentID;
      }
      return donViID;
    },
    getDonViIDFromNode: function (nodeID) {
      let donViID = null;
      let node = this.getNode(nodeID);
      if (node != null) {
        if (node.attributes.type == ThietBi.PRE_DONVI && node.attributes.level == 3) {
          donViID = node.attributes.id;
        } else {
          if (!this.checkIsEmpty(node.parentID)) {
            let parents = this.getParents(node.parentID);
            let parent = parents.filter(
              (x) => x.attributes.type == ThietBi.PRE_DONVI && x.attributes.level == 3
            );
            if (parent != null) {
              donViID = parent.attributes.id;
            }
          }
        }
      }
      return donViID;
    },
    getDonViIDFromNodeItem: function (type, id) {
      let donViID = null;
      let node = this.dsNodes.find(
        (x) => x.attributes.type == type && x.attributes.id == id
      );
      if (node != null) {
        if (node.attributes.type == ThietBi.PRE_DONVI && node.attributes.level == 3) {
          donViID = node.attributes.id;
        } else {
          if (!this.checkIsEmpty(node.parentID)) {
            let parents = this.getParents(node.parentID);
            let parent = parents.filter(
              (x) => x.attributes.type == ThietBi.PRE_DONVI && x.attributes.level == 3
            );
            if (parent != null) {
              donViID = parent.attributes.id;
            }
          }
        }
      }
      return donViID;
    },
    getDmDonVi: async function () {
      let result = await this.$root.context.post(
        "/web-ecms/quanlythietbi/getCayDanhMucDonViV2",
        { id: this.kieuTu }
      );
      return result.data;
    },
    getDmThietBi: async function (donViID) {
      let result = await this.$root.context.post(
        "/web-ecms/quanlythietbi/getCayDanhMucThietBiV2",
        { id: donViID, kieuTu: this.kieuTu }
      );
      return result.data;
    },
    getNumberString: function (value, pre) {
      let result = "#00";
      if (!this.checkIsEmpty(value)) {
        result = value.toString();
        if (result.length == 1) result = "0" + result;
        result = "#" + result;
      }
      if (!this.checkIsEmpty(pre)) result = pre + result;
      return result;
    },
    checkIsNull: function (value) {
      return value == undefined || value == null;
    },
    checkIsEmpty: function (value) {
      let rs = this.checkIsNull(value);
      if (!rs) {
        rs = value.toString().trim() == "";
      }
      return rs;
    },
    getAttributes: function (attributes) {
      let newAttributes = {};
      if (!this.checkIsNull(attributes)) {
        let keys = Object.keys(attributes);
        for (let i = 0; i < keys.length; i++) {
          const key = keys[i];
          if (
            !(
              key.toString().toLowerCase() == "itemparentids" ||
              key.toString().toLowerCase() == "selectedparentid"
            )
          )
            newAttributes[key] = attributes[key];
        }
      }
      return newAttributes;
    },
    timKiemThietBi: async function (text) {
      let result = await this.$root.context.post(
        "/web-ecms/quanlythietbi/timKiemCayDanhMucThietBiV2",
        { text: text, kieuTu: this.kieuTu }
      );
      return result.data;
    },
  },
};
</script>
<style>
.e-tooltip-wrap .e-arrow-tip-inner.e-tip-top {
  color: #fbce5f;
}

.e-tooltip-wrap .e-arrow-tip-outer.e-tip-top {
  border-bottom: #fbce5f;
}
</style>
