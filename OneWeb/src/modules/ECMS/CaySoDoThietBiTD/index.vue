<template src='./index.html'></template>

<script>
import ThietBi from "../CaySoDoThietBi/ThietBi";
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
      nodesFiltered: null,
      selectedNodes: [],
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
        dataSource: this.dsNodesFiltered,
        id: "id",
        text: "text",
        iconCss: "icon",
        parentID: "parentID",
        hasChildren: "hasChildren",
        expanded: "expanded",
        selected: "selected",
      };
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
    tree_nodeCollapsed: function (args) {
      this.tree_doSelected(args);
    },
    tree_nodeExpanded: function (args) {
      this.tree_doSelected(args);
    },
    tree_doSelected: function (args) {
      if (!args.nodeData.selected) {
        let doSelected = true;
        let currentSelected = this.dsNodes.find((x) => x.selected == true);
        if (currentSelected != null) {
          if (currentSelected.id != args.nodeData.id) {
            currentSelected.selected = false;
          } else {
            doSelected = false;
          }
        }
        if (doSelected) {
          this.selectedNodes = [args.nodeData.id];
        }
      }
    },
    tree_nodeSelected: function (args) {
      let item = this.dsNodes.find((x) => x.id == args.nodeData.id);
      if (item != null) {
        let newArgs = {
          node: args.node,
          expanded: args.nodeData.expanded,
          hasChildren: args.nodeData.hasChildren,
          selected: args.nodeData.selected,
          text: args.nodeData.text,
          itemData: {
            itemID: item.itemID,
            itemType: item.itemType,
            itemParentID: item.itemParentID,
            itemParentType: item.itemParentType,
            itemAttributes: item.itemAttributes,
          },
        };
        this.$emit("nodeSelected", newArgs);
      }
    },
    btnReload_click: async function () {
      this.$root.showLoading(true);
      try {
        this.$refs.textBoxSearch.value = "";
        this.nodesFiltered = null;
        await this.loadNodes();
      } finally {
        this.$root.showLoading(false);
      }
    },
    btnSearch_click: function () {
      this.$root.showLoading(true);
      try {
        let text = this.$refs.textBoxSearch.value;
        if (text != "") this.nodesFiltered = this.getNodesFiltered(text);
        else this.nodesFiltered = null;
      } finally {
        this.$root.showLoading(false);
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
      let nodes = [];
      let dsDonVi = await this.getDsDonVi();
      if (!(dsDonVi == null || dsDonVi.length == 0)) {
        dsDonVi.forEach((donVi) => {
          let node = this.createNode(
            donVi.DONVI_ID,
            ThietBi.PRE_DONVI,
            donVi.TEN_DV,
            donVi.DONVI_CHA_ID,
            ThietBi.PRE_DONVI,
            { MUC: donVi.MUC },
            null,
            nodes
          );
          nodes.push(node);
        });
        for (let i = 0; i < nodes.length; i++) {
          const node = nodes[i];
          if (!this.checkIsEmpty(node.itemParentID)) {
            if (this.checkIsEmpty(node.parentID)) {
              let parent = this.getNode(node.itemParentID, node.itemParentType);
              if (parent != null) node.parentID = parent.id;
              else {
                nodes.splice(i, 1);
                i--;
              }
            }
          }
        }
      }

      let dsThietBi_TuRack = await this.getDsTuRackTheoKieuTu(this.kieuTu);
      if (dsThietBi_TuRack != null) {
        let items = dsThietBi_TuRack.TuRack;
        if (!(items == null || items.length == 0)) {
          items.forEach((item) => {
            let node = this.createNode(
              item.TURACK_ID,
              ThietBi.PRE_TURACK,
              item.TEN_TU,
              item.DONVI_ID,
              ThietBi.PRE_DONVI,
              null,
              null,
              nodes
            );
            if (node.parentID != null) nodes.push(node);
          });

          // Chuyển mạch
          if (this.kieuTu == 1) {
            //EWSD
            let dsThietBi_Ewsd = await this.getDsEwsd();
            if (dsThietBi_Ewsd != null) {
              let ewsd = dsThietBi_Ewsd.Ewsd;
              if (ewsd != null) {
                if (!(ewsd == null || ewsd.length == 0)) {
                  ewsd.forEach((item) => {
                    let node = this.createNode(
                      item.EWSD_ID,
                      ThietBi.PRE_EWSD,
                      item.TEN_EWSD,
                      item.TURACK_ID,
                      ThietBi.PRE_TURACK,
                      {
                        iconType: item.LOAI_EWSD_ID,
                      },
                      null,
                      nodes
                    );
                    if (node.parentID != null) nodes.push(node);
                  });
                }
              }
              let ewsdShelfEwsd = dsThietBi_Ewsd.ShelfEwsd;
              if (ewsdShelfEwsd != null) {
                if (!(ewsdShelfEwsd == null || ewsdShelfEwsd.length == 0)) {
                  ewsdShelfEwsd.forEach((item) => {
                    let node = this.createNode(
                      item.SHELF_EWSD_ID,
                      ThietBi.PRE_EWSD_SHELF,
                      item.TEN_SHELF,
                      item.EWSD_ID,
                      ThietBi.PRE_EWSD,
                      null,
                      null,
                      nodes
                    );
                    if (node.parentID != null) {
                      nodes.push(node);
                      if (item.SO_SLOT > 0) {
                        for (let i = 0; i < item.SO_SLOT; i++) {
                          let nodeSlot = this.createNodeSlot(
                            i,
                            ThietBi.PRE_EWSD_SHELF_SLOT,
                            node,
                            nodes
                          );
                          if (nodeSlot != null) nodes.push(nodeSlot);
                        }
                      }
                    }
                  });
                }
              }
              let ewsdCardEwsd = await this.getDsCardEwsd();
              if (ewsdCardEwsd != null) {
                if (!(ewsdCardEwsd == null || ewsdCardEwsd.length == 0)) {
                  ewsdCardEwsd.forEach((item) => {
                    let slots = this.getNodeSlots(
                      item.VITRI,
                      ThietBi.PRE_EWSD_SHELF_SLOT,
                      item.SHELF_EWSD_ID,
                      ThietBi.PRE_EWSD_SHELF,
                      nodes
                    );
                    if (slots != null) {
                      for (let i = 0; i < slots.length; i++) {
                        const slot = slots[i];
                        if (slot != null) {
                          slot.text += " " + item.TEN_CARD;
                          slot.itemID = item.CARD_EWSD_ID;
                          slot.itemType = ThietBi.PRE_EWSD_SHELF_CARD;
                          slot.icon = ThietBi.PRE_EWSD_SHELF_CARD.toLowerCase();
                          if (item.NHOMCARD_ID == 1)
                            slot.icon +=
                              ThietBi.SEPARATOR + item.NHOMCARD_ID.toString();
                          slot.id = this.createID(slot.itemID, slot.itemType);
                        }
                      }
                    }
                  });
                }
              }
            }
          } else if (this.kieuTu == 2) {
            //ODF
            let dsThietBi_Odf = await this.getDsOdf();
            if (dsThietBi_Odf != null) {
              let items = dsThietBi_Odf.Odf;
              if (!(items == null || items.length == 0)) {
                items.forEach((item) => {
                  let node = this.createNode(
                    item.ODF_ID,
                    ThietBi.PRE_ODF,
                    item.TEN_ODF,
                    item.TURACK_ID,
                    ThietBi.PRE_TURACK,
                    null,
                    null,
                    nodes
                  );
                  if (node.parentID != null) nodes.push(node);
                });
              }
            }
            //BRAS
            let dsThietBi_Bras_full = await this.getDsBras();
            if (dsThietBi_Bras_full != null) {
              let dsThietBi_Bras = dsThietBi_Bras_full.Bras;
              if (dsThietBi_Bras != null) {
                let items = dsThietBi_Bras;
                if (!(items == null || items.length == 0)) {
                  items.forEach((item) => {
                    let node = this.createNode(
                      item.BRAS_ID,
                      ThietBi.PRE_BRAS,
                      item.TENBRAS,
                      item.TURACK_ID,
                      ThietBi.PRE_TURACK,
                      {
                        soSlot: item.SO_SLOT,
                        soSubSlot: item.SO_SUBSLOT,
                        slotType: ThietBi.PRE_BRAS_SLOT,
                        soBD: item.SLOT_BD,
                      },
                      null,
                      nodes
                    );
                    if (node.parentID != null) {
                      nodes.push(node);
                    }
                  });
                }
              }

              let dsThietBi_CardBras = dsThietBi_Bras_full.CardBras;
              if (dsThietBi_CardBras != null) {
                let items = dsThietBi_CardBras;

                let brasItems = nodes.filter(
                  (x) => x.itemType == ThietBi.PRE_BRAS
                );
                if (!this.checkIsNull(brasItems)) {
                  for (let i = 0; i < brasItems.length; i++) {
                    const bras = brasItems[i];
                    if (bras.itemAttributes.soSubSlot != 0) {
                      for (
                        let j = bras.itemAttributes.soBD;
                        j <
                        parseInt(bras.itemAttributes.soSlot) +
                          parseInt(bras.itemAttributes.soBD);
                        j++
                      ) {
                        let nodeSlot = this.createNodeSlot(
                          j,
                          ThietBi.PRE_BRAS_SLOT,
                          bras,
                          nodes
                        );
                        if (nodeSlot != null) {
                          nodeSlot.text = "S" + nodeSlot.text;
                          nodes.push(nodeSlot);
                          for (
                            let k = 0;
                            k < bras.itemAttributes.soSubSlot;
                            k++
                          ) {
                            let nodeSubSlot = this.createNodeSlot(
                              k,
                              ThietBi.PRE_BRAS_SLOT_SUB,
                              nodeSlot,
                              nodes
                            );
                            nodeSubSlot.text = "SubSlot" + nodeSubSlot.text;
                            if (nodeSubSlot != null) {
                              for (let l = 0; l < items.length; l++) {
                                const item = items[l];
                                if (
                                  item.SLOT == nodeSlot.itemID &&
                                  item.VITRI == nodeSubSlot.itemID
                                ) {
                                  nodeSubSlot.text += " " + item.LOAI_CARD;
                                  nodeSubSlot.itemID = item.CARDBRAS_ID;
                                  nodeSubSlot.itemType = ThietBi.PRE_BRAS_CARD;
                                  nodeSubSlot.icon =
                                    ThietBi.PRE_BRAS_CARD.toLowerCase();
                                  if (item.NHOMCARD_ID == 1)
                                    nodeSubSlot.icon +=
                                      ThietBi.SEPARATOR +
                                      item.NHOMCARD_ID.toString();
                                  nodeSubSlot.id = this.createID(
                                    nodeSubSlot.itemID,
                                    nodeSubSlot.itemType
                                  );
                                }
                              }
                              nodes.push(nodeSubSlot);
                            }
                          }
                        }
                      }
                    }
                  }
                }
              }

              let dsThietBi_PortBras = dsThietBi_Bras_full.PortBras;
              if (dsThietBi_PortBras != null) {
                let items = dsThietBi_PortBras;
                if (!(items == null || items.length == 0)) {
                  items.forEach((item) => {
                    let textPort = this.getNumberString(item.VITRI, "Port ");
                    let node = this.createNode(
                      item.PORTBRAS_ID,
                      ThietBi.PRE_BRAS_CARD_PORT,
                      textPort,
                      item.BRAS_ID,
                      ThietBi.PRE_BRAS_CARD,
                      item.TT_PORT_ID >= 1 && item.TT_PORT_ID <= 9
                        ? { iconType: item.TT_PORT_ID }
                        : null,
                      null,
                      nodes
                    );
                    if (node.parentID != null) nodes.push(node);
                  });
                }
              }
            }
            //GPON
            let dsThietBi_Gpon_full = await this.getDsGpon();
            if (dsThietBi_Gpon_full != null) {
              let dsThietBi_Gpon = dsThietBi_Gpon_full.Gpon;
              if (dsThietBi_Gpon != null) {
                let items = dsThietBi_Gpon;
                if (!(items == null || items.length == 0)) {
                  items.forEach((item) => {
                    let node = this.createNode(
                      item.DSLAM_ID,
                      ThietBi.PRE_GPON,
                      item.TENDSLAM,
                      item.TURACK_ID,
                      ThietBi.PRE_TURACK,
                      null,
                      null,
                      nodes
                    );
                    if (node.parentID != null) {
                      nodes.push(node);
                      if (item.SO_SLOT > 0) {
                        for (let i = 0; i < item.SO_SLOT; i++) {
                          let nodeSlot = this.createNodeSlot(
                            i,
                            ThietBi.PRE_GPON_SLOT,
                            node,
                            nodes
                          );
                          if (nodeSlot != null) nodes.push(nodeSlot);
                        }
                      }
                    }
                  });
                }
              }

              let dsThietBi_CardGpon = dsThietBi_Gpon_full.CardGpon;
              if (dsThietBi_CardGpon != null) {
                let items = dsThietBi_CardGpon;
                if (!(items == null || items.length == 0)) {
                  items.forEach((item) => {
                    let slots = this.getNodeSlots(
                      item.VITRI,
                      ThietBi.PRE_GPON_SLOT,
                      item.DSLAM_ID,
                      ThietBi.PRE_GPON,
                      nodes
                    );
                    for (let i = 0; i < slots.length; i++) {
                      const slot = slots[i];
                      if (slot != null) {
                        slot.text += " " + item.TEN_CARD;
                        slot.itemID = item.CARDGP_ID;
                        slot.itemType = ThietBi.PRE_GPON_CARD;
                        slot.icon = ThietBi.PRE_GPON_CARD.toLowerCase();
                        if (item.NHOMCARD_ID == 1)
                          slot.icon +=
                            ThietBi.SEPARATOR + item.NHOMCARD_ID.toString();
                        slot.id = this.createID(slot.itemID, slot.itemType);
                      }
                    }
                  });
                }
              }

              let dsThietBi_PortGpon = dsThietBi_Gpon_full.PortGpon;
              if (dsThietBi_PortGpon != null) {
                let items = dsThietBi_PortGpon;
                if (!(items == null || items.length == 0)) {
                  items.forEach((item) => {
                    let textPort = this.getNumberString(item.VITRI, "Port ");
                    let node = this.createNode(
                      item.PORTVL_ID,
                      ThietBi.PRE_GPON_CARD_PORT,
                      textPort,
                      item.CARDGP_ID,
                      ThietBi.PRE_GPON_CARD,
                      item.TT_PORT_ID >= 1 && item.TT_PORT_ID <= 9
                        ? { iconType: item.TT_PORT_ID }
                        : null,
                      null,
                      nodes
                    );
                    if (node.parentID != null) {
                      nodes.push(node);
                      this.copyToSameParent(node, nodes);
                    }
                  });
                }
              }
            }
            //MANE
            let dsThietBi_Mane = await this.getDsMane();
            if (dsThietBi_Mane != null) {
              let mane = dsThietBi_Mane.Mane;
              if (mane != null) {
                if (!(mane == null || mane.length == 0)) {
                  mane.forEach((item) => {
                    let node = this.createNode(
                      item.DSLAM_ID,
                      ThietBi.PRE_MANE,
                      item.TENDSLAM,
                      item.TURACK_ID,
                      ThietBi.PRE_TURACK,
                      null,
                      null,
                      nodes
                    );
                    if (node.parentID != null) {
                      nodes.push(node);
                      if (item.SO_SLOT > 0) {
                        for (let i = parseInt(item.CARD_BD); i < item.SO_SLOT + parseInt(item.CARD_BD); i++) {
                          let nodeSlot = this.createNodeSlot(
                            i,
                            ThietBi.PRE_MANE_SLOT,
                            node,
                            nodes
                          );
                          if (nodeSlot != null) nodes.push(nodeSlot);
                        }
                      }
                    }
                  });
                }
              }
              let maneCard = dsThietBi_Mane.CardMane;
              if (maneCard != null) {
                if (!(maneCard == null || maneCard.length == 0)) {
                  maneCard.forEach((item) => {
                    let slots = this.getNodeSlots(
                      item.VITRI,
                      ThietBi.PRE_MANE_SLOT,
                      item.DSLAM_ID,
                      ThietBi.PRE_MANE,
                      nodes
                    );
                    for (let i = 0; i < slots.length; i++) {
                      const slot = slots[i];
                      if (slot != null) {
                        slot.text += " " + item.TEN_CARD;
                        slot.itemID = item.CARDMANE_ID;
                        slot.itemType = ThietBi.PRE_MANE_CARD;
                        slot.icon = ThietBi.PRE_MANE_CARD.toLowerCase();
                        if (item.NHOMCARD_ID == 1)
                          slot.icon +=
                            ThietBi.SEPARATOR + item.NHOMCARD_ID.toString();
                        slot.id = this.createID(slot.itemID, slot.itemType);
                      }
                    }
                  });
                }
              }
              let manePort = dsThietBi_Mane.PortMane;
              if (manePort != null) {
                if (!(manePort == null || manePort.length == 0)) {
                  manePort.forEach((item) => {
                    let textPort = this.getNumberString(item.VITRI, "Port ");
                    let node = this.createNode(
                      item.PORTVL_ID,
                      ThietBi.PRE_MANE_CARD_PORT,
                      textPort,
                      item.CARDMANE_ID,
                      ThietBi.PRE_MANE_CARD,
                      item.TT_PORT_ID >= 1 && item.TT_PORT_ID <= 9
                        ? { iconType: item.TT_PORT_ID }
                        : null,
                      null,
                      nodes
                    );
                    if (node.parentID != null) {
                      nodes.push(node);
                      this.copyToSameParent(node, nodes);
                    }
                  });
                }
              }
              let maneModule = dsThietBi_Mane.ModuleMane;
              if (maneModule != null) {
                if (!(maneModule == null || maneModule.length == 0)) {
                  maneModule.forEach((item) => {
                    let textModule =
                      this.getNumberString(item.VITRI) + " " + item.TEN_MODULE;
                    let node = this.createNode(
                      item.MODULEMANE_ID,
                      ThietBi.PRE_MANE_CARD_MODULE,
                      textModule,
                      item.CARDMANE_ID,
                      ThietBi.PRE_MANE_CARD,
                      null,
                      null,
                      nodes
                    );
                    if (node.parentID != null) {
                      nodes.push(node);
                      this.copyToSameParent(node, nodes);
                    }
                  });
                }
              }
              let maneModulePort = dsThietBi_Mane.PortModuleMane;
              if (maneModulePort != null) {
                if (!(maneModulePort == null || maneModulePort.length == 0)) {
                  maneModulePort.forEach((item) => {
                    let textPort = this.getNumberString(item.VITRI, "Port ");
                    let node = this.createNode(
                      item.PORTVL_ID,
                      ThietBi.PRE_MANE_CARD_MODULE_PORT,
                      textPort,
                      item.MODULEMANE_ID,
                      ThietBi.PRE_MANE_CARD_MODULE,
                      item.TT_PORT_ID >= 1 && item.TT_PORT_ID <= 9
                        ? { iconType: item.TT_PORT_ID }
                        : null,
                      null,
                      nodes
                    );
                    if (node.parentID != null) {
                      nodes.push(node);
                      this.copyToSameParent(node, nodes);
                    }
                  });
                }
              }
            }
            //SWITCH
            let dsThietBi_Switch = await this.getDsSwitch();
            if (dsThietBi_Switch != null) {
              let sw = dsThietBi_Switch.Switch;
              if (sw != null) {
                if (!(sw == null || sw.length == 0)) {
                  sw.forEach((item) => {
                    let node = this.createNode(
                      item.DSLAM_ID,
                      ThietBi.PRE_SWITCH,
                      item.TENDSLAM,
                      item.TURACK_ID,
                      ThietBi.PRE_TURACK,
                      null,
                      null,
                      nodes
                    );
                    if (node.parentID != null) {
                      nodes.push(node);
                      if (item.SO_SLOT > 0) {
                        for (let i = 0; i < item.SO_SLOT; i++) {
                          let nodeSlot = this.createNodeSlot(
                            i,
                            ThietBi.PRE_SWITCH_SLOT,
                            node,
                            nodes
                          );
                          if (nodeSlot != null) nodes.push(nodeSlot);
                        }
                      }
                    }
                  });
                }
              }
              let swPort = dsThietBi_Switch.PortSwitch;
              if (swPort != null) {
                if (!(swPort == null || swPort.length == 0)) {
                  swPort.forEach((item) => {
                    let textPort = this.getNumberString(item.VITRI, "Port ");
                    let node = this.createNode(
                      item.PORTVL_ID,
                      ThietBi.PRE_SWITCH_PORT,
                      textPort,
                      item.DSLAM_ID,
                      ThietBi.PRE_SWITCH,
                      item.TT_PORT_ID >= 1 && item.TT_PORT_ID <= 9
                        ? { iconType: item.TT_PORT_ID }
                        : null,
                      null,
                      nodes
                    );
                    if (node.parentID != null) {
                      nodes.push(node);
                      this.copyToSameParent(node, nodes);
                    }
                  });
                }
              }
              let swCard = dsThietBi_Switch.CardSwitch;
              if (swCard != null) {
                if (!(swCard == null || swCard.length == 0)) {
                  swCard.forEach((item) => {
                    let slots = this.getNodeSlots(
                      item.VITRI,
                      ThietBi.PRE_SWITCH_SLOT,
                      item.DSLAM_ID,
                      ThietBi.PRE_SWITCH,
                      nodes
                    );
                    for (let i = 0; i < slots.length; i++) {
                      const slot = slots[i];
                      if (slot != null) {
                        slot.text += " " + item.TEN_CARD;
                        slot.itemID = item.CARDSW_ID;
                        slot.itemType = ThietBi.PRE_SWITCH_CARD;
                        slot.icon = ThietBi.PRE_SWITCH_CARD.toLowerCase();
                        if (item.NHOMCARD_ID == 1)
                          slot.icon +=
                            ThietBi.SEPARATOR + item.NHOMCARD_ID.toString();
                        slot.id = this.createID(slot.itemID, slot.itemType);
                      }
                    }
                  });
                }
              }
              let swCardPort = dsThietBi_Switch.PortCardSw;
              if (swCardPort != null) {
                if (!(swCardPort == null || swCardPort.length == 0)) {
                  swCardPort.forEach((item) => {
                    let textPort = this.getNumberString(item.VITRI, "Port ");
                    let node = this.createNode(
                      item.PORTVL_ID,
                      ThietBi.PRE_SWITCH_CARD_PORT,
                      textPort,
                      item.CARDSW_ID,
                      ThietBi.PRE_SWITCH_CARD,
                      item.TT_PORT_ID >= 1 && item.TT_PORT_ID <= 9
                        ? { iconType: item.TT_PORT_ID }
                        : null,
                      null,
                      nodes
                    );
                    if (node.parentID != null) {
                      nodes.push(node);
                      this.copyToSameParent(node, nodes);
                    }
                  });
                }
              }
            }
            //DSLAM
            let dsThietBi_Dslam = await this.getDsDslam();
            if (dsThietBi_Dslam != null) {
              let dslam = dsThietBi_Dslam.Dslam;
              if (dslam != null) {
                if (!(dslam == null || dslam.length == 0)) {
                  dslam.forEach((item) => {
                    let node = this.createNode(
                      item.DSLAM_ID,
                      ThietBi.PRE_DSLAM,
                      item.TENDSLAM,
                      item.TURACK_ID,
                      ThietBi.PRE_TURACK,
                      null,
                      null,
                      nodes
                    );
                    if (node.parentID != null) nodes.push(node);
                  });
                }
              }
              let dslamFrameDsl = dsThietBi_Dslam.FrameDsl;
              if (dslamFrameDsl != null) {
                if (!(dslamFrameDsl == null || dslamFrameDsl.length == 0)) {
                  dslamFrameDsl.forEach((item) => {
                    let node = this.createNode(
                      item.FRAMEDSL_ID,
                      ThietBi.PRE_DSLAM_FRAME,
                      item.TEN_FRAME,
                      item.DSLAM_ID,
                      ThietBi.PRE_DSLAM,
                      null,
                      null,
                      nodes
                    );
                    if (node.parentID != null) {
                      nodes.push(node);
                      this.copyToSameParent(node, nodes);
                    }
                  });
                }
              }
              let dslamShelfDsl = dsThietBi_Dslam.ShelfDsl;
              if (dslamShelfDsl != null) {
                if (!(dslamShelfDsl == null || dslamShelfDsl.length == 0)) {
                  dslamShelfDsl.forEach((item) => {
                    let node = this.createNode(
                      item.SHELFDSL_ID,
                      ThietBi.PRE_DSLAM_FRAME_SHELF,
                      item.TEN_SHELF,
                      item.FRAMEDSL_ID,
                      ThietBi.PRE_DSLAM_FRAME,
                      null,
                      null,
                      nodes
                    );
                    if (node.parentID != null) {
                      nodes.push(node);
                      if (item.SO_SLOT > 0) {
                        for (
                          let i = item.SLOT_BD;
                          i < item.SLOT_BD + item.SO_SLOT;
                          i++
                        ) {
                          let nodeSlot = this.createNodeSlot(
                            i,
                            ThietBi.PRE_DSLAM_FRAME_SHELF_SLOT,
                            node,
                            nodes
                          );
                          if (nodeSlot != null) nodes.push(nodeSlot);
                        }
                      }

                      this.copyToSameParent(node, nodes);
                    }
                  });
                }
              }
              let dslamCardDsl = dsThietBi_Dslam.CardDsl;
              if (dslamCardDsl != null) {
                if (!(dslamCardDsl == null || dslamCardDsl.length == 0)) {
                  dslamCardDsl.forEach((item) => {
                    let slots = this.getNodeSlots(
                      item.VITRI,
                      ThietBi.PRE_DSLAM_FRAME_SHELF_SLOT,
                      item.SHELFDSL_ID,
                      ThietBi.PRE_DSLAM_FRAME_SHELF,
                      nodes
                    );
                    for (let i = 0; i < slots.length; i++) {
                      const slot = slots[i];
                      if (slot != null) {
                        slot.text += " " + item.LOAI_CARD;
                        slot.itemID = item.CARDDSL_ID;
                        slot.itemType = ThietBi.PRE_DSLAM_FRAME_SHELF_CARD;
                        slot.icon =
                          ThietBi.PRE_DSLAM_FRAME_SHELF_CARD.toLowerCase();
                        if (item.NHOMCARD_ID == 1)
                          slot.icon +=
                            ThietBi.SEPARATOR + item.NHOMCARD_ID.toString();
                        slot.id = this.createID(slot.itemID, slot.itemType);
                      }
                    }
                  });
                }
              }
              let dslamPortCardDsl = dsThietBi_Dslam.PortCardDsl;
              if (dslamPortCardDsl != null) {
                if (
                  !(dslamPortCardDsl == null || dslamPortCardDsl.length == 0)
                ) {
                  dslamPortCardDsl.forEach((item) => {
                    let textPort = this.getNumberString(item.VITRI, "Port ");
                    let node = this.createNode(
                      item.PORTVL_ID,
                      ThietBi.PRE_DSLAM_FRAME_SHELF_CARD_PORT,
                      textPort,
                      item.CARDDSL_ID,
                      ThietBi.PRE_DSLAM_FRAME_SHELF_CARD,
                      item.TT_PORT_ID >= 1 && item.TT_PORT_ID <= 9
                        ? { iconType: item.TT_PORT_ID }
                        : null,
                      null,
                      nodes
                    );
                    if (node.parentID != null) {
                      nodes.push(node);
                      this.copyToSameParent(node, nodes);
                    }
                  });
                }
              }
              let dslamModuleDsl = dsThietBi_Dslam.ModuleDsl;
              if (dslamModuleDsl != null) {
                if (!(dslamModuleDsl == null || dslamModuleDsl.length == 0)) {
                  dslamModuleDsl.forEach((item) => {
                    let textModule =
                      this.getNumberString(item.VITRI) + " " + item.TEN_MODULE;
                    let node = this.createNode(
                      item.MODULEDSL_ID,
                      ThietBi.PRE_DSLAM_FRAME_SHELF_CARD_MODULE,
                      textModule,
                      item.CARDDSL_ID,
                      ThietBi.PRE_DSLAM_FRAME_SHELF_CARD,
                      null,
                      null,
                      nodes
                    );
                    if (node.parentID != null) {
                      nodes.push(node);
                      this.copyToSameParent(node, nodes);
                    }
                  });
                }
              }
              let dslamPortModuleDsl = dsThietBi_Dslam.PortModuleDsl;
              if (dslamPortModuleDsl != null) {
                if (
                  !(
                    dslamPortModuleDsl == null || dslamPortModuleDsl.length == 0
                  )
                ) {
                  dslamPortModuleDsl.forEach((item) => {
                    let textPort = this.getNumberString(item.VITRI, "Port ");
                    let node = this.createNode(
                      item.PORTVL_ID,
                      ThietBi.PRE_DSLAM_FRAME_SHELF_CARD_MODULE_PORT,
                      textPort,
                      item.MODULEDSL_ID,
                      ThietBi.PRE_DSLAM_FRAME_SHELF_CARD_MODULE,
                      item.TT_PORT_ID >= 1 && item.TT_PORT_ID <= 9
                        ? { iconType: item.TT_PORT_ID }
                        : null,
                      null,
                      nodes
                    );
                    if (node.parentID != null) {
                      nodes.push(node);
                      this.copyToSameParent(node, nodes);
                    }
                  });
                }
              }
            }
            //FRAME
            let dsThietBi_Frame = await this.getDsFrame();
            if (dsThietBi_Frame != null) {
              let frame = dsThietBi_Frame.FrameTD;
              if (frame != null) {
                if (!(frame == null || frame.length == 0)) {
                  frame.forEach((item) => {
                    let node = this.createNode(
                      item.FRAMETD_ID,
                      ThietBi.PRE_FRAME,
                      item.TEN_FRAME,
                      item.TURACK_ID,
                      ThietBi.PRE_TURACK,
                      null,
                      null,
                      nodes
                    );
                    if (node.parentID != null) {
                      nodes.push(node);
                      if (item.SO_SLOT > 0) {
                        for (
                          let i = item.SLOT_BD;
                          i < item.SLOT_BD + item.SO_SLOT;
                          i++
                        ) {
                          let nodeSlot = this.createNodeSlot(
                            i,
                            ThietBi.PRE_FRAME_SLOT,
                            node,
                            nodes
                          );
                          if (nodeSlot != null) nodes.push(nodeSlot);
                        }
                      }
                    }
                  });
                }
              }
              let frameCard = dsThietBi_Frame.CardTD;
              if (frameCard != null) {
                if (!(frameCard == null || frameCard.length == 0)) {
                  frameCard.forEach((item) => {
                    let slots = this.getNodeSlots(
                      item.VITRI,
                      ThietBi.PRE_FRAME_SLOT,
                      item.FRAMETD_ID,
                      ThietBi.PRE_FRAME,
                      nodes
                    );
                    for (let i = 0; i < slots.length; i++) {
                      const slot = slots[i];
                      if (slot != null) {
                        slot.text += " " + item.VITRI;
                        slot.itemID = item.CARDTD_ID;
                        slot.itemType = ThietBi.PRE_FRAME_CARD;
                        slot.icon = ThietBi.PRE_FRAME_CARD.toLowerCase();
                        if (item.NHOMCARD_ID == 1)
                          slot.icon +=
                            ThietBi.SEPARATOR + item.NHOMCARD_ID.toString();
                        slot.id = this.createID(slot.itemID, slot.itemType);
                      }
                    }
                  });
                }
              }
            }
          }
        }
      }

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
    createID: function (itemID, itemType) {
      return (
        itemType +
        ThietBi.SEPARATOR +
        itemID.toString() +
        ThietBi.SEPARATOR +
        Math.floor(Math.random() * 10000).toString()
      );
    },
    createNode: function (
      itemID,
      itemType,
      itemText,
      itemParentID,
      itemParentType,
      itemAttributes,
      parentNode,
      nodes
    ) {
      if (this.checkIsEmpty(parentNode)) {
        let currentNodes = !this.checkIsNull(nodes) ? nodes : this.dsNodes;
        parentNode = this.getNode(
          itemParentID,
          itemParentType,
          null,
          currentNodes
        );
      }
      let id = this.createID(itemID, itemType);
      let icon = itemType.toString().toLowerCase();
      let attrs = null;
      if (!this.checkIsNull(itemAttributes)) {
        attrs = itemAttributes;
        if (!this.checkIsEmpty(itemAttributes.MUC)) {
          icon += ThietBi.SEPARATOR + itemAttributes.MUC.toString();
        } else {
          if (!this.checkIsEmpty(itemAttributes.iconType)) {
            icon += ThietBi.SEPARATOR + itemAttributes.iconType.toString();
          }
        }
      }
      if (itemText == null) itemText = "(null)";
      return {
        id: id,
        text: itemText,
        parentID: this.checkIsEmpty(parentNode) ? null : parentNode.id,
        icon: icon,
        hasChildren: false,
        expanded: false,
        selected: false,
        itemID: itemID,
        itemType: itemType,
        itemParentID: itemParentID,
        itemParentType: itemParentType,
        itemAttributes: attrs,
      };
    },
    cloneNode: function (node) {
      return {
        id: this.createID(node.itemID, node.itemType),
        text: node.text,
        parentID: node.parentID,
        icon: node.icon,
        hasChildren: node.hasChildren,
        expanded: false,
        selected: false,
        itemID: node.itemID,
        itemType: node.itemType,
        itemParentID: node.itemParentID,
        itemParentType: node.itemParentType,
        itemAttributes: node.itemAttributes,
      };
    },
    createNodeSlot: function (slotIndex, slotType, node, nodes) {
      let nodeSlot = this.createNode(
        slotIndex,
        slotType,
        this.getNumberString(slotIndex),
        node.itemID,
        node.itemType,
        {
          slotIndex: slotIndex,
          slotType: slotType,
        },
        node,
        nodes
      );
      return nodeSlot;
    },
    createNodePort: function (portIndex, portType, node, nodes) {
      let nodePort = this.createNode(
        portIndex,
        portType,
        this.getNumberString(portIndex, "Port "),
        node.itemID,
        node.itemType,
        {
          portIndex: portIndex,
          portType: portType,
        },
        node,
        nodes
      );
      return nodePort;
    },
    getNodeByKey: function (id, nodes) {
      let currentNodes = !this.checkIsNull(nodes) ? nodes : this.dsNodes;
      return currentNodes.find((x) => x.id == id);
    },
    getNode: function (itemID, itemType, itemParentID, nodes) {
      if (!(this.checkIsEmpty(itemID) || this.checkIsEmpty(itemType))) {
        let currentNodes = !this.checkIsNull(nodes) ? nodes : this.dsNodes;

        if (this.checkIsEmpty(itemParentID))
          return currentNodes.find(
            (x) => x.itemID == itemID && x.itemType == itemType
          );
        else
          return currentNodes.find(
            (x) =>
              x.itemID == itemID &&
              x.itemType == itemType &&
              x.itemParentID == itemParentID
          );
      } else return null;
    },
    getNodes: function (itemID, itemType, itemParentID) {
      if (!(this.checkIsEmpty(itemID) || this.checkIsEmpty(itemType))) {
        if (this.checkIsEmpty(itemParentID))
          return this.dsNodes.filter(
            (x) => x.itemID == itemID && x.itemType == itemType
          );
        else
          return this.dsNodes.filter(
            (x) =>
              x.itemID == itemID &&
              x.itemType == itemType &&
              x.itemParentID == itemParentID
          );
      } else return null;
    },
    getNodeSlots: function (slotIndex, slotType, itemID, itemType, nodes) {
      let currentNodes = !this.checkIsNull(nodes) ? nodes : this.dsNodes;
      return currentNodes.filter(
        (x) =>
          x.itemParentID == itemID &&
          x.itemParentType == itemType &&
          x.itemAttributes.slotIndex == slotIndex &&
          x.itemAttributes.slotType == slotType
      );
    },
    addNode: function (
      itemID,
      itemType,
      itemText,
      itemParentID,
      itemParentType,
      selected,
      attributes
    ) {
      if (!this.checkIsNull(attributes)) {
        if (!this.checkIsNull(attributes.itemParentIDs)) {
          let newAttributes = this.getAttributes(attributes);
          for (let i = 0; i < attributes.itemParentIDs.length; i++) {
            const pid = attributes.itemParentIDs[i];
            let s = this.checkIsEmpty(attributes.selectedParentID)
              ? false
              : pid == attributes.selectedParentID;
            this.addSingleNode(
              itemID,
              itemType,
              itemText,
              pid,
              itemParentType,
              s,
              newAttributes
            );
          }
        } else {
          this.addSingleNode(
            itemID,
            itemType,
            itemText,
            itemParentID,
            itemParentType,
            selected,
            attributes
          );
        }
      } else {
        this.addSingleNode(
          itemID,
          itemType,
          itemText,
          itemParentID,
          itemParentType,
          selected,
          attributes
        );
      }
    },
    addSingleNode: function (
      itemID,
      itemType,
      itemText,
      itemParentID,
      itemParentType,
      selected,
      attributes
    ) {
      let node = this.createNode(
        itemID,
        itemType,
        itemText,
        itemParentID,
        itemParentType,
        attributes
      );
      this.dsNodes.push(node);
      let parentNode = this.getNodeByKey(node.parentID);
      if (parentNode != null) parentNode.hasChildren = true;
      let sameNode = this.dsNodes.find(
        (x) =>
          x.itemID == node.itemID &&
          x.itemType == node.itemType &&
          x.id != node.id
      );
      if (!this.checkIsNull(sameNode)) {
        this.copyChildNodes(sameNode.id, node);
      } else {
        if (!(attributes == undefined || attributes == null)) {
          if (
            !this.checkIsNull(attributes.soSubSlot) &&
            attributes.soSubSlot != 0
          ) {
            let hasChilds = false;
            for (
              let j = attributes.soBD;
              j < parseInt(attributes.soSlot) + parseInt(attributes.soBD);
              j++
            ) {
              let nodeSlot = this.createNodeSlot(j, attributes.slotType, node);
              if (nodeSlot != null) {
                nodeSlot.text = "S" + nodeSlot.text;
                this.dsNodes.push(nodeSlot);
                for (let k = 0; k < attributes.soSubSlot; k++) {
                  let nodeSubSlot = this.createNodeSlot(
                    k,
                    ThietBi.PRE_BRAS_SLOT_SUB,
                    nodeSlot
                  );
                  nodeSubSlot.text = "SubSlot" + nodeSubSlot.text;
                  this.dsNodes.push(nodeSubSlot);
                  nodeSlot.hasChildren = true;
                  nodeSlot.expanded = true;
                  if (!hasChilds) hasChilds = true;
                }
              }
            }
            node.hasChildren = hasChilds;
            node.expanded = hasChilds;
          } else if (!this.checkIsNull(attributes.soSlot)) {
            if (attributes.soSlot > 0) {
              let soBD = 0;
              if (!this.checkIsNull(attributes.soBD))
                soBD = parseInt(attributes.soBD);
              for (let i = soBD; i < soBD + parseInt(attributes.soSlot); i++) {
                let nodeSlot = this.createNodeSlot(
                  i,
                  attributes.slotType,
                  node
                );
                if (nodeSlot != null) this.dsNodes.push(nodeSlot);
              }
              node.hasChildren = true;
              node.expanded = true;
            }
          }
          if (!this.checkIsNull(attributes.soPort)) {
            if (attributes.soPort > 0) {
              let soPortBD = 0;
              if (!this.checkIsNull(attributes.soPortBD))
                soPortBD = parseInt(attributes.soPortBD);
              for (
                let i = soPortBD;
                i < soPortBD + parseInt(attributes.soPort);
                i++
              ) {
                let nodePort = this.createNodePort(
                  i,
                  attributes.portType,
                  node
                );
                if (!this.checkIsNull(attributes.portIDs)) {
                  if (attributes.portIDs.length > 0) {
                    let portID = attributes.portIDs.find((x) => x.viTri == i);
                    if (!this.checkIsNull(portID)) {
                      nodePort.itemID = portID.ID;
                      nodePort.icon =
                        nodePort.itemType.toLowerCase() +
                        ThietBi.SEPARATOR +
                        portID.trangThai.toString();
                    }
                  }
                }
                if (nodePort != null) this.dsNodes.push(nodePort);
              }
              node.hasChildren = true;
              node.expanded = true;
            }
          }
        }
      }

      let sameParents = this.dsNodes.filter(
        (x) =>
          x.itemID == parentNode.itemID &&
          x.itemType == parentNode.itemType &&
          x.id != parentNode.id
      );
      if (!this.checkIsNull(sameParents)) {
        if (sameParents.length > 0) {
          for (let i = 0; i < sameParents.length; i++) {
            const sameParent = sameParents[i];
            sameParent.hasChildren = true;
            let clone = this.cloneNode(node);
            clone.parentID = sameParent.id;
            this.dsNodes.push(clone);
            this.copyChildNodes(node.id, clone);
          }
        }
      }

      if (selected === true) this.selectedNode(node);
    },
    updateNode: function (
      itemID,
      itemType,
      itemText,
      itemParentID,
      itemParentType,
      selected,
      attributes
    ) {
      if (!this.checkIsNull(attributes)) {
        if (!this.checkIsNull(attributes.itemParentIDs)) {
          let newAttributes = this.getAttributes(attributes);
          let newNodes = [];
          for (let i = 0; i < attributes.itemParentIDs.length; i++) {
            const pid = attributes.itemParentIDs[i];
            let n = this.createNode(
              itemID,
              itemType,
              itemText,
              pid,
              itemParentType,
              newAttributes
            );
            newNodes.push(n);
          }
          let selectedParentID = this.checkIsEmpty(attributes.selectedParentID)
            ? newNodes[0].itemParentID
            : attributes.selectedParentID;
          let nodes = this.getNodes(itemID, itemType);
          if (nodes.length > newNodes.length) {
            for (let i = newNodes.length; i < nodes.length; i++) {
              this.deleteSingleNode(nodes[i].id);
              nodes.splice(i, 1);
              i--;
            }
          } else if (nodes.length < newNodes.length) {
            for (let i = nodes.length; i < newNodes.length; i++) {
              this.addSingleNode(
                newNodes[i].itemID,
                newNodes[i].itemType,
                newNodes[i].text,
                newNodes[i].itemParentID,
                newNodes[i].itemParentType,
                selectedParentID == newNodes[i].itemParentID,
                newNodes[i].itemAttributes
              );
              newNodes.splice(i, 1);
              i--;
            }
          }
          if (nodes.length == newNodes.length) {
            for (let i = 0; i < newNodes.length; i++) {
              const node = nodes[i];
              const newNode = newNodes[i];
              this.updateSingleNode(
                node,
                newNode,
                selectedParentID == newNode.itemParentID
              );
            }
          }
          this.dsNodes = this.dsNodes.sort((a, b) =>
            a.text < b.text ? -1 : 1
          );
        } else {
          let node = this.getNode(itemID, itemType);
          let newNode = this.createNode(
            itemID,
            itemType,
            itemText,
            itemParentID,
            itemParentType,
            attributes
          );
          this.updateSingleNode(node, newNode, selected);
        }
      } else {
        let node = this.getNode(itemID, itemType);
        let newNode = this.createNode(
          itemID,
          itemType,
          itemText,
          itemParentID,
          itemParentType,
          attributes
        );
        this.updateSingleNode(node, newNode, selected);
      }
    },
    updateSingleNode: function (node, newNode, selected) {
      let parent = this.getNodeByKey(newNode.parentID);
      let isUpdateSlot = false;
      if (
        !(newNode.itemAttributes == undefined || newNode.itemAttributes == null)
      ) {
        if (
          !(
            newNode.itemAttributes.slotIndex == undefined ||
            newNode.itemAttributes.slotIndex == null
          )
        ) {
          isUpdateSlot = true;
        }
      }
      if (isUpdateSlot) {
        let childs = this.dsNodes.filter(
          (x) =>
            x.itemParentID == parent.itemID &&
            x.itemParentType == parent.itemType &&
            !(
              this.checkIsNull(x.itemAttributes) ||
              this.checkIsNull(x.itemAttributes.slotIndex)
            )
        );
        if (childs != null) {
          for (let i = 0; i < childs.length; i++) {
            const child = childs[i];
            if (
              child.itemAttributes.slotIndex ==
                newNode.itemAttributes.slotIndex &&
              child.itemAttributes.slotType == newNode.itemAttributes.slotType
            ) {
              child.id = newNode.id;
              child.text =
                this.getNumberString(child.itemAttributes.slotIndex) +
                " " +
                newNode.text;
              child.itemID = newNode.itemID;
              child.itemType = newNode.itemType;
              child.icon = newNode.itemType.toLowerCase();
              if (!this.checkIsNull(newNode.itemAttributes.soPort)) {
                if (this.checkIsNull(child.itemAttributes))
                  child.itemAttributes = {};
                child.itemAttributes.soPortBD = parseInt(
                  newNode.itemAttributes.soPortBD
                );
                child.itemAttributes.soPort = parseInt(
                  newNode.itemAttributes.soPort
                );
                child.itemAttributes.portType = newNode.itemAttributes.portType;
                if (child.itemAttributes.soPort > 0) {
                  let soPortBD = child.itemAttributes.soPortBD;
                  for (
                    let j = soPortBD;
                    j < soPortBD + child.itemAttributes.soPort;
                    j++
                  ) {
                    let nodePort = this.createNodePort(
                      j,
                      child.itemAttributes.portType,
                      child
                    );
                    if (!this.checkIsNull(newNode.itemAttributes.portIDs)) {
                      if (newNode.itemAttributes.portIDs.length > 0) {
                        let portID = newNode.itemAttributes.portIDs.find(
                          (x) => x.viTri == j
                        );
                        if (!this.checkIsNull(portID)) {
                          nodePort.itemID = portID.ID;
                          nodePort.icon =
                            nodePort.itemType.toLowerCase() +
                            ThietBi.SEPARATOR +
                            portID.trangThai.toString();
                        }
                      }
                    }
                    if (nodePort != null) this.dsNodes.push(nodePort);
                  }
                  child.hasChildren = true;
                  child.expanded = true;
                }
              }
            }
          }
          if (selected === true) this.selectedNode(newNode);
        }
      } else {
        node.text = newNode.text;
        node.itemAttributes = newNode.itemAttributes;
        if (!this.checkIsNull(newNode.itemParentType)) {
          if (node.parentID != newNode.parentID) {
            let p1 = this.dsNodes.filter((x) => x.parentID == node.parentID);
            if (p1.length == 1) {
              let p2 = this.dsNodes.find((x) => x.id == node.parentID);
              if (p2 != null) {
                p2.hasChildren = false;
              }
            }
            node.parentID = newNode.parentID;
            node.itemParentID = newNode.itemParentID;
            node.itemParentType = newNode.itemParentType;
            if (parent != null) parent.hasChildren = true;
          }
          this.dsNodes = this.dsNodes.sort((a, b) =>
            a.text < b.text ? -1 : 1
          );
        }
        if (!this.checkIsNull(node.itemAttributes)) {
          if (!this.checkIsNull(node.itemAttributes.soSlot)) {
            if (node.itemAttributes.soSlot > 0) {
              let childs = this.dsNodes.filter(
                (x) =>
                  x.parentID == node.id &&
                  !(
                    this.checkIsNull(x.itemAttributes) ||
                    this.checkIsNull(x.itemAttributes.slotIndex)
                  )
              );
              let deleteChilds = [];
              let countChilds = 0;
              if (childs != null) {
                countChilds = childs.length;
                if (childs.length > node.itemAttributes.soSlot) {
                  for (let i = 0; i < childs.length; i++) {
                    const child = childs[i];
                    if (
                      child.itemAttributes.slotIndex >=
                      node.itemAttributes.soSlot
                    ) {
                      deleteChilds.push(child);
                    }
                  }
                }
              }
              if (countChilds < node.itemAttributes.soSlot) {
                for (let i = countChilds; i < node.itemAttributes.soSlot; i++) {
                  let child = this.createNodeSlot(
                    i,
                    node.itemAttributes.slotType,
                    node
                  );
                  if (child != null) this.dsNodes.push(child);
                }
              }
              if (deleteChilds.length > 0) {
                for (let i = 0; i < this.dsNodes.length; i++) {
                  const n = this.dsNodes[i];
                  for (let j = 0; j < deleteChilds.length; j++) {
                    const child = deleteChilds[j];
                    if (n.id == child.id) {
                      this.dsNodes.splice(i, 1);
                      i--;
                      break;
                    }
                  }
                }
              }
              node.hasChildren = true;
              node.expanded = true;
            }
          }
          if (!this.checkIsEmpty(node.itemAttributes.iconType)) {
            node.icon =
              node.itemType.toLowerCase() +
              ThietBi.SEPARATOR +
              node.itemAttributes.iconType;
          }
        }
        let sameNodes = this.dsNodes.filter(
          (x) =>
            x.itemID == node.itemID &&
            x.itemType == node.itemType &&
            x.parentID != node.parentID
        );
        if (!this.checkIsNull(sameNodes)) {
          for (let i = 0; i < sameNodes.length; i++) {
            const sameNode = sameNodes[i];
            sameNode.text = node.text;
            sameNode.icon = node.icon;
          }
        }
        if (selected === true) this.selectedNode(node);
      }
    },
    deleteNode: function (itemID, itemType, itemParentID) {
      let nodes = this.getNodes(itemID, itemType, itemParentID);
      if (nodes != null) {
        for (let i = 0; i < nodes.length; i++) {
          const node = nodes[i];
          this.deleteSingleNode(node.id);
        }
      }
    },
    deleteSingleNode: function (nodeID) {
      let node = this.dsNodes.find((x) => x.id == nodeID);
      if (node != null) {
        this.deleteChildNodes(node.id);
        let isDeleteSlot = false;
        if (
          !(node.itemAttributes == undefined || node.itemAttributes == null)
        ) {
          if (
            !(
              node.itemAttributes.slotIndex == undefined ||
              node.itemAttributes.slotIndex == null
            )
          ) {
            isDeleteSlot = true;
          }
        }
        if (isDeleteSlot) {
          node.text = this.getNumberString(node.itemAttributes.slotIndex);
          node.itemID = node.itemAttributes.slotIndex;
          node.itemType = node.itemAttributes.slotType;
          node.id = this.createID(node.itemID, node.itemType);
          node.icon = node.itemType.toLowerCase();
          node.hasChildren = false;
        } else {
          this.dsNodes = this.dsNodes.filter((x) => x.id != node.id);
          let filters = this.dsNodes.filter((x) => x.selected == true);
          if (!(filters == null || filters.length == 0)) {
            for (let i = 0; i < filters.length; i++) {
              filters[i].selected = false;
            }
          }
        }
        let parent = this.dsNodes.find((x) => x.id == node.parentID);
        parent.hasChildren = false;
        parent.expanded = false;
        if (parent != null) {
          let childs = this.dsNodes.filter((x) => x.parentID == parent.id);
          if (!(childs == null || childs.length == 0)) {
            parent.hasChildren = true;
            parent.expanded = true;
          }
        }
        this.selectedNode(parent);
      }
    },
    deleteChildNodes: function (nodeID) {
      let childs = this.getChilds(nodeID);
      if (childs.length > 0) {
        let childIDs = [];
        childs.forEach((child) => {
          childIDs.push(child.id);
        });
        this.dsNodes = this.dsNodes.filter((x) => !childIDs.includes(x.id));
      }
    },
    copyToSameParent: function (node, nodes) {
      let currentNodes = !this.checkIsNull(nodes) ? nodes : this.dsNodes;
      let parents = currentNodes.filter(
        (x) =>
          x.itemID == node.itemParentID &&
          x.itemType == node.itemParentType &&
          x.id != node.parentID
      );
      let childs = currentNodes.filter((x) => x.parentID == node.id);
      if (!this.checkIsNull(parents)) {
        parents.forEach((parent) => {
          let clone = this.cloneNode(node);
          clone.parentID = parent.id;
          currentNodes.push(clone);
          parent.hasChildren = true;
          if (!this.checkIsNull(childs)) {
            childs.forEach((child) => {
              let cloneChild = this.cloneNode(child);
              cloneChild.parentID = clone.id;
              currentNodes.push(cloneChild);
              clone.hasChildren = true;
            });
          }
        });
      }
    },
    copyChildNodes: function (fromNodeID, toNode, nodes) {
      let currentNodes = !this.checkIsNull(nodes) ? nodes : this.dsNodes;
      let childs = this.getChilds(fromNodeID, currentNodes);
      if (!this.checkIsNull(childs)) {
        if (childs.length > 0) {
          toNode.hasChildren = true;
          let newChilds = [];
          let mapChilds = [];
          for (let i = 0; i < childs.length; i++) {
            const child = this.cloneNode(childs[i]);
            if (child.parentID == fromNodeID) child.parentID = toNode.id;
            newChilds.push(child);
            mapChilds.push({ id: childs[i].id, newID: child.id });
          }
          for (let i = 0; i < newChilds.length; i++) {
            const newChild = newChilds[i];
            if (newChild.parentID != toNode.id) {
              let map = mapChilds.find((x) => x.id == newChild.parentID);
              newChild.parentID = map.newID;
            }
            currentNodes.push(newChild);
          }
        }
      }
    },
    selectedItem: function (itemID, itemType, itemParentID) {
      let node = this.getNode(itemID, itemType, itemParentID);
      this.selectedNode(node);
    },
    selectedNode: function (node) {
      if (node != null) {
        this.selectedNodes = [node.id];
        let expandedNodes = this.dsNodes.filter((x) => x.expanded == true);
        if (expandedNodes != null) {
          for (let j = 0; j < expandedNodes.length; j++) {
            const expandedNode = expandedNodes[j];
            expandedNode.expanded = false;
          }
        }
        if (node.parentID != null) {
          let ps = this.getParents(node.parentID);
          if (!(ps == null || ps.length == 0)) {
            for (let i = 0; i < ps.length; i++) {
              ps[i].expanded = true;
            }
          }
        }
      }
    },
    selectedNodeByKey: function (id) {
      let node = this.getNodeByKey(id);
      this.selectedNode(node);
    },
    getChilds: function (nodeParentID, nodes) {
      let currentNodes = !this.checkIsNull(nodes) ? nodes : this.dsNodes;
      let result = [];
      let childs = currentNodes.filter((x) => x.parentID == nodeParentID);
      if (!this.checkIsNull(childs)) {
        for (let i = 0; i < childs.length; i++) {
          const child = childs[i];
          result.push(child);
          let ch = this.getChilds(child.id, currentNodes);
          if (!this.checkIsNull(ch)) {
            for (let j = 0; j < ch.length; j++) {
              const c = ch[j];
              result.push(c);
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
    getDsDonVi: async function () {
      let result = await this.$root.context.post(
        "/web-ecms/danhmuc/layCayDonVi"
      );
      return result.data;
    },
    getDsThietBi: async function () {
      let result = await this.$root.context.post(
        "/web-ecms/quanlythietbi/getCayDanhMucThietBi"
      );
      return result.data;
    },
    getDsTuRackTheoKieuTu: async function (kieu) {
      let result = await this.$root.context.post(
        "/web-ecms/quanlythietbi/getDanhMucTuRackTheoKieuTu",
        {
          id: kieu,
        }
      );
      return result.data;
    },
    getDsTuRack: async function () {
      let result = await this.$root.context.post(
        "/web-ecms/quanlythietbi/getDanhMucTuRack"
      );
      return result.data;
    },
    getDsOdf: async function () {
      let result = await this.$root.context.post(
        "/web-ecms/quanlythietbi/getDanhMucOdf"
      );
      return result.data;
    },
    getDsBras: async function () {
      let result = await this.$root.context.post(
        "/web-ecms/quanlythietbi/getDanhMucBras"
      );
      return result.data;
    },
    getDsGpon: async function () {
      let result = await this.$root.context.post(
        "/web-ecms/quanlythietbi/getDanhMucGpon"
      );
      return result.data;
    },
    getDsMane: async function () {
      let result = await this.$root.context.post(
        "/web-ecms/quanlythietbi/getDanhMucMane"
      );
      return result.data;
    },
    getDsSwitch: async function () {
      let result = await this.$root.context.post(
        "/web-ecms/quanlythietbi/getDanhMucSwitch"
      );
      return result.data;
    },
    getDsDslam: async function () {
      let result = await this.$root.context.post(
        "/web-ecms/quanlythietbi/getDanhMucDslam"
      );
      return result.data;
    },
    getDsFrame: async function () {
      let result = await this.$root.context.post(
        "/web-ecms/quanlythietbi/getDanhMucFrame"
      );
      return result.data;
    },
    getDsEwsd: async function () {
      let result = await this.$root.context.post(
        "/web-ecms/quanlythietbi/getDanhMucEwsd"
      );
      return result.data;
    },
    getDsCardEwsd: async function () {
      let result = await this.$root.context.post(
        "/web-ecms/quanlythietbi/getDsCardEwsd"
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
  },
};
</script>
