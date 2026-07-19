<template src='./index.html'></template>

<script>
import ThietBi from "./ThietBi";
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
        let items = this.dsNodes.filter((item) => {
          return item.text
            .toString()
            .toLowerCase()
            .includes(textFiltered.toString().toLowerCase());
        });
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
      let nodes = [];
      let dsDonVi = await this.getDsDonVi();
      if (!(dsDonVi == null || dsDonVi.length == 0)) {
        dsDonVi.forEach((donVi) => {
          let node_DONVI = this.createNode(
            donVi.DONVI_ID,
            ThietBi.PRE_DONVI,
            donVi.TEN_DV,
            donVi.DONVI_CHA_ID,
            ThietBi.PRE_DONVI,
            { MUC: donVi.MUC }
          );
          nodes.push(node_DONVI);
        });
      }
      // let dsThietBi = await this.getDsThietBi();
      // if (dsThietBi != null) {
      //   let dsTuRack = dsThietBi.TuRack;
      //   if (!(dsTuRack == null || dsTuRack.length == 0)) {
      //     dsTuRack.forEach((item) => {
      //       let node_id = ThietBi.PRE_TURACK + SEPARATOR + item.TURACK_ID.toString();
      //       let node_parentID = null;
      //       if (item.DONVI_ID != null)
      //         node_parentID = ThietBi.PRE_DONVI + SEPARATOR + item.DONVI_ID.toString();
      //       let node = this.createNode(node_id, item.TEN_TU, node_parentID);
      //       nodes.push(node);
      //     });
      //   }
      // }
      // let dsThietBi_TuRack = await this.getDsTuRack();
      // if (dsThietBi_TuRack != null) {
      //   let items = dsThietBi_TuRack.TuRack;
      //   if (!(items == null || items.length == 0)) {
      //     items.forEach((item) => {
      //       let node = this.createNode(
      //         item.TURACK_ID,
      //         ThietBi.PRE_TURACK,
      //         item.TEN_TU,
      //         item.DONVI_ID,
      //         ThietBi.PRE_DONVI
      //       );
      //       nodes.push(node);
      //     });
      //   }
      // }

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
              ThietBi.PRE_DONVI
            );
            nodes.push(node);
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
                      ThietBi.PRE_TURACK
                    );
                    nodes.push(node);
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
                      ThietBi.PRE_EWSD
                    );
                    nodes.push(node);
                    if (item.SO_SLOT > 0) {
                      for (let i = 0; i < item.SO_SLOT; i++) {
                        let nodeSlot = this.createNode(
                          item.SHELF_EWSD_ID +
                            ThietBi.SEPARATOR +
                            this.getNumberString(i),
                          ThietBi.PRE_EWSD_SHELF_SLOT,
                          this.getNumberString(i),
                          item.SHELF_EWSD_ID,
                          ThietBi.PRE_EWSD_SHELF,
                          {
                            slotIndex: i,
                            slotType: ThietBi.PRE_EWSD_SHELF_SLOT,
                          }
                        );
                        nodes.push(nodeSlot);
                      }
                    }
                  });
                }
              }
              let ewsdCardEwsd = await this.getDsCardEwsd();
              if (ewsdCardEwsd != null) {
                if (!(ewsdCardEwsd == null || ewsdCardEwsd.length == 0)) {
                  ewsdCardEwsd.forEach((item) => {
                    let r1 =
                      item.SHELF_EWSD_ID +
                      ThietBi.SEPARATOR +
                      this.getNumberString(item.VITRI);
                    let slot = nodes.find(
                      (x) =>
                        x.itemType == ThietBi.PRE_EWSD_SHELF_SLOT &&
                        x.itemID == r1
                    );
                    if (slot != null) {
                      slot.text += " " + item.TEN_CARD;
                      slot.itemID = item.CARD_EWSD_ID;
                      slot.itemType = ThietBi.PRE_EWSD_SHELF_CARD;
                      slot.icon = ThietBi.PRE_EWSD_SHELF_CARD.toLowerCase();
                      slot.id =
                        slot.itemType +
                        ThietBi.SEPARATOR +
                        slot.itemID.toString();
                    }
                    // let node = this.createNode(
                    //   item.CARD_EWSD_ID,
                    //   ThietBi.PRE_EWSD_SHELF_CARD,
                    //   item.TEN_CARD,
                    //   item.SHELF_EWSD_ID,
                    //   ThietBi.PRE_EWSD_SHELF
                    // );
                    // nodes.push(node);
                  });
                }
              }
            }
          } else if (this.kieuTu == 2) {
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
                    ThietBi.PRE_TURACK
                  );
                  nodes.push(node);
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
                      ThietBi.PRE_TURACK
                    );
                    nodes.push(node);
                    if (item.SO_SLOT > 0) {
                      for (
                        let i = item.SLOT_BD;
                        i < item.SLOT_BD + item.SO_SLOT;
                        i++
                      ) {
                        let nodeSlot = this.createNode(
                          item.BRAS_ID +
                            ThietBi.SEPARATOR +
                            this.getNumberString(i),
                          ThietBi.PRE_BRAS_SLOT,
                          this.getNumberString(i),
                          item.BRAS_ID,
                          ThietBi.PRE_BRAS,
                          {
                            slotIndex: i,
                            slotType: ThietBi.PRE_BRAS_SLOT,
                          }
                        );
                        nodes.push(nodeSlot);
                      }
                    }
                  });
                }
              }

              let dsThietBi_CardBras = dsThietBi_Bras_full.CardBras;
              if (dsThietBi_CardBras != null) {
                let items = dsThietBi_CardBras;
                if (!(items == null || items.length == 0)) {
                  items.forEach((item) => {
                    let r1 =
                      item.BRAS_ID +
                      ThietBi.SEPARATOR +
                      this.getNumberString(item.VITRI);
                    let slot = nodes.find(
                      (x) =>
                        x.itemType == ThietBi.PRE_BRAS_SLOT && x.itemID == r1
                    );
                    if (slot != null) {
                      slot.text += " " + item.TEN_CARD;
                      slot.itemID = item.CARDBRAS_ID;
                      slot.itemType = ThietBi.PRE_BRAS_SLOT;
                      slot.icon = ThietBi.PRE_BRAS_SLOT.toLowerCase();
                      slot.id =
                        slot.itemType +
                        ThietBi.SEPARATOR +
                        slot.itemID.toString();
                    }
                    // let node = this.createNode(
                    //   item.CARDBRAS_ID,
                    //   ThietBi.PRE_BRAS_CARD,
                    //   item.TEN_CARD,
                    //   item.BRAS_ID,
                    //   ThietBi.PRE_BRAS
                    // );
                    // nodes.push(node);
                  });
                }
              }

              let dsThietBi_PortBras = dsThietBi_Bras_full.PortBras;
              if (dsThietBi_PortBras != null) {
                let items = dsThietBi_PortBras;
                if (!(items == null || items.length == 0)) {
                  items.forEach((item) => {
                    let textPort = "Port " + this.getNumberString(item.VITRI);
                    let node = this.createNode(
                      item.PORTBRAS_ID,
                      ThietBi.PRE_BRAS_CARD_PORT,
                      textPort,
                      item.BRAS_ID,
                      ThietBi.PRE_BRAS_CARD
                    );
                    nodes.push(node);
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
                      ThietBi.PRE_TURACK
                    );
                    nodes.push(node);
                    if (item.SO_SLOT > 0) {
                      for (let i = 0; i < item.SO_SLOT; i++) {
                        let nodeSlot = this.createNode(
                          item.DSLAM_ID +
                            ThietBi.SEPARATOR +
                            this.getNumberString(i),
                          ThietBi.PRE_GPON_SLOT,
                          this.getNumberString(i),
                          item.DSLAM_ID,
                          ThietBi.PRE_GPON,
                          {
                            slotIndex: i,
                            slotType: ThietBi.PRE_GPON_SLOT,
                          }
                        );
                        nodes.push(nodeSlot);
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
                    let r1 =
                      item.DSLAM_ID +
                      ThietBi.SEPARATOR +
                      this.getNumberString(item.VITRI);
                    let slot = nodes.find(
                      (x) =>
                        x.itemType == ThietBi.PRE_GPON_SLOT && x.itemID == r1
                    );
                    if (slot != null) {
                      slot.text += " " + item.TEN_CARD;
                      slot.itemID = item.CARDGP_ID;
                      slot.itemType = ThietBi.PRE_GPON_CARD;
                      slot.icon = ThietBi.PRE_GPON_CARD.toLowerCase();
                      slot.id =
                        slot.itemType +
                        ThietBi.SEPARATOR +
                        slot.itemID.toString();
                    }
                  });
                }
              }

              let dsThietBi_PortGpon = dsThietBi_Gpon_full.PortGpon;
              if (dsThietBi_PortGpon != null) {
                let items = dsThietBi_PortGpon;
                if (!(items == null || items.length == 0)) {
                  items.forEach((item) => {
                    let textPort = "Port " + this.getNumberString(item.VITRI);
                    let node = this.createNode(
                      item.PORTVL_ID,
                      ThietBi.PRE_GPON_CARD_PORT,
                      textPort,
                      item.CARDGP_ID,
                      ThietBi.PRE_GPON_CARD
                    );
                    nodes.push(node);
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
                      ThietBi.PRE_TURACK
                    );
                    nodes.push(node);
                    if (item.SO_SLOT > 0) {
                      for (let i = 0; i < item.SO_SLOT; i++) {
                        let nodeSlot = this.createNode(
                          item.DSLAM_ID +
                            ThietBi.SEPARATOR +
                            this.getNumberString(i),
                          ThietBi.PRE_MANE_SLOT,
                          this.getNumberString(i),
                          item.DSLAM_ID,
                          ThietBi.PRE_MANE,
                          {
                            slotIndex: i,
                            slotType: ThietBi.PRE_MANE_SLOT,
                          }
                        );
                        nodes.push(nodeSlot);
                      }
                    }
                  });
                }
              }
              let maneCard = dsThietBi_Mane.CardMane;
              if (maneCard != null) {
                if (!(maneCard == null || maneCard.length == 0)) {
                  maneCard.forEach((item) => {
                    let r1 =
                      item.DSLAM_ID +
                      ThietBi.SEPARATOR +
                      this.getNumberString(item.VITRI);
                    let slot = nodes.find(
                      (x) =>
                        x.itemType == ThietBi.PRE_MANE_SLOT && x.itemID == r1
                    );
                    if (slot != null) {
                      slot.text += " " + item.TEN_CARD;
                      slot.itemID = item.CARDMANE_ID;
                      slot.itemType = ThietBi.PRE_MANE_CARD;
                      slot.icon = ThietBi.PRE_MANE_CARD.toLowerCase();
                      slot.id =
                        slot.itemType +
                        ThietBi.SEPARATOR +
                        slot.itemID.toString();
                    }
                    // let node = this.createNode(
                    //   item.CARDMANE_ID,
                    //   ThietBi.PRE_MANE_CARD,
                    //   item.TEN_CARD,
                    //   item.DSLAM_ID,
                    //   ThietBi.PRE_MANE
                    // );
                    // nodes.push(node);
                  });
                }
              }
              let manePort = dsThietBi_Mane.PortMane;
              if (manePort != null) {
                if (!(manePort == null || manePort.length == 0)) {
                  manePort.forEach((item) => {
                    let textPort = "Port " + this.getNumberString(item.VITRI);
                    let node = this.createNode(
                      item.PORTVL_ID,
                      ThietBi.PRE_MANE_CARD_PORT,
                      textPort,
                      item.CARDMANE_ID,
                      ThietBi.PRE_MANE_CARD
                    );
                    nodes.push(node);
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
                      ThietBi.PRE_MANE_CARD
                    );
                    nodes.push(node);
                  });
                }
              }
              let maneModulePort = dsThietBi_Mane.PortModuleMane;
              if (maneModulePort != null) {
                if (!(maneModulePort == null || maneModulePort.length == 0)) {
                  maneModulePort.forEach((item) => {
                    let textPort = "Port " + this.getNumberString(item.VITRI);
                    let node = this.createNode(
                      item.PORTVL_ID,
                      ThietBi.PRE_MANE_CARD_MODULE_PORT,
                      textPort,
                      item.MODULEMANE_ID,
                      ThietBi.PRE_MANE_CARD_MODULE
                    );
                    nodes.push(node);
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
                      ThietBi.PRE_TURACK
                    );
                    nodes.push(node);
                    if (item.SO_SLOT > 0) {
                      for (let i = 0; i < item.SO_SLOT; i++) {
                        let nodeSlot = this.createNode(
                          item.DSLAM_ID +
                            ThietBi.SEPARATOR +
                            this.getNumberString(i),
                          ThietBi.PRE_SWITCH_SLOT,
                          this.getNumberString(i),
                          item.DSLAM_ID,
                          ThietBi.PRE_SWITCH,
                          {
                            slotIndex: i,
                            slotType: ThietBi.PRE_SWITCH_SLOT,
                          }
                        );
                        nodes.push(nodeSlot);
                      }
                    }
                  });
                }
              }
              let swPort = dsThietBi_Switch.PortSwitch;
              if (swPort != null) {
                if (!(swPort == null || swPort.length == 0)) {
                  swPort.forEach((item) => {
                    let textPort = "Port " + this.getNumberString(item.VITRI);
                    let node = this.createNode(
                      item.PORTVL_ID,
                      ThietBi.PRE_SWITCH_PORT,
                      textPort,
                      item.DSLAM_ID,
                      ThietBi.PRE_SWITCH
                    );
                    nodes.push(node);
                  });
                }
              }
              let swCard = dsThietBi_Switch.CardSwitch;
              if (swCard != null) {
                if (!(swCard == null || swCard.length == 0)) {
                  swCard.forEach((item) => {
                    let r1 =
                      item.DSLAM_ID +
                      ThietBi.SEPARATOR +
                      this.getNumberString(item.VITRI);
                    let slot = nodes.find(
                      (x) =>
                        x.itemType == ThietBi.PRE_SWITCH_SLOT && x.itemID == r1
                    );
                    if (slot != null) {
                      slot.text += " " + item.TEN_CARD;
                      slot.itemID = item.CARDSW_ID;
                      slot.itemType = ThietBi.PRE_SWITCH_CARD;
                      slot.icon = ThietBi.PRE_SWITCH_CARD.toLowerCase();
                      slot.id =
                        slot.itemType +
                        ThietBi.SEPARATOR +
                        slot.itemID.toString();
                    }
                    // let node = this.createNode(
                    //   item.CARDSW_ID,
                    //   ThietBi.PRE_SWITCH_CARD,
                    //   item.TEN_CARD,
                    //   item.DSLAM_ID,
                    //   ThietBi.PRE_SWITCH
                    // );
                    // nodes.push(node);
                  });
                }
              }
              let swCardPort = dsThietBi_Switch.PortCardSw;
              if (swCardPort != null) {
                if (!(swCardPort == null || swCardPort.length == 0)) {
                  swCardPort.forEach((item) => {
                    let textPort = "Port " + this.getNumberString(item.VITRI);
                    let node = this.createNode(
                      item.PORTVL_ID,
                      ThietBi.PRE_SWITCH_CARD_PORT,
                      textPort,
                      item.CARDSW_ID,
                      ThietBi.PRE_SWITCH_CARD
                    );
                    nodes.push(node);
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
                      ThietBi.PRE_TURACK
                    );
                    nodes.push(node);
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
                      ThietBi.PRE_DSLAM
                    );
                    nodes.push(node);
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
                      ThietBi.PRE_DSLAM_FRAME
                    );
                    nodes.push(node);
                    if (item.SO_SLOT > 0) {
                      for (
                        let i = item.SLOT_BD;
                        i < item.SLOT_BD + item.SO_SLOT;
                        i++
                      ) {
                        let nodeSlot = this.createNode(
                          item.SHELFDSL_ID +
                            ThietBi.SEPARATOR +
                            this.getNumberString(i),
                          ThietBi.PRE_DSLAM_FRAME_SHELF_SLOT,
                          this.getNumberString(i),
                          item.SHELFDSL_ID,
                          ThietBi.PRE_DSLAM_FRAME_SHELF,
                          {
                            slotIndex: i,
                            slotType: ThietBi.PRE_DSLAM_FRAME_SHELF_SLOT,
                          }
                        );
                        nodes.push(nodeSlot);
                      }
                    }
                  });
                }
              }
              let dslamCardDsl = dsThietBi_Dslam.CardDsl;
              if (dslamCardDsl != null) {
                if (!(dslamCardDsl == null || dslamCardDsl.length == 0)) {
                  dslamCardDsl.forEach((item) => {
                    let r1 =
                      item.SHELFDSL_ID +
                      ThietBi.SEPARATOR +
                      this.getNumberString(item.VITRI);
                    let slot = nodes.find(
                      (x) =>
                        x.itemType == ThietBi.PRE_DSLAM_FRAME_SHELF_SLOT &&
                        x.itemID == r1
                    );
                    if (slot != null) {
                      slot.text += " " + item.TEN_CARD;
                      slot.itemID = item.CARDDSL_ID;
                      slot.itemType = ThietBi.PRE_DSLAM_FRAME_SHELF_CARD;
                      slot.icon = ThietBi.PRE_DSLAM_FRAME_SHELF_CARD.toLowerCase();
                      slot.id =
                        slot.itemType +
                        ThietBi.SEPARATOR +
                        slot.itemID.toString();
                    }
                    // let node = this.createNode(
                    //   item.CARDDSL_ID,
                    //   ThietBi.PRE_DSLAM_FRAME_SHELF_CARD,
                    //   item.TEN_CARD,
                    //   item.SHELFDSL_ID,
                    //   ThietBi.PRE_DSLAM_FRAME_SHELF
                    // );
                    // nodes.push(node);
                  });
                }
              }
              let dslamPortCardDsl = dsThietBi_Dslam.PortCardDsl;
              if (dslamPortCardDsl != null) {
                if (
                  !(dslamPortCardDsl == null || dslamPortCardDsl.length == 0)
                ) {
                  dslamPortCardDsl.forEach((item) => {
                    let textPort = "Port " + this.getNumberString(item.VITRI);
                    let node = this.createNode(
                      item.PORTVL_ID,
                      ThietBi.PRE_DSLAM_FRAME_SHELF_CARD_PORT,
                      textPort,
                      item.CARDDSL_ID,
                      ThietBi.PRE_DSLAM_FRAME_SHELF_CARD
                    );
                    nodes.push(node);
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
                      ThietBi.PRE_DSLAM_FRAME_SHELF_CARD
                    );
                    nodes.push(node);
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
                    let textPort = "Port " + this.getNumberString(item.VITRI);
                    let node = this.createNode(
                      item.PORTVL_ID,
                      ThietBi.PRE_DSLAM_FRAME_SHELF_CARD_MODULE_PORT,
                      textPort,
                      item.MODULEDSL_ID,
                      ThietBi.PRE_DSLAM_FRAME_SHELF_CARD_MODULE
                    );
                    nodes.push(node);
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
                      ThietBi.PRE_TURACK
                    );
                    nodes.push(node);
                    if (item.SO_SLOT > 0) {
                      for (
                        let i = item.SLOT_BD;
                        i < item.SLOT_BD + item.SO_SLOT;
                        i++
                      ) {
                        let nodeSlot = this.createNode(
                          item.FRAMETD_ID +
                            ThietBi.SEPARATOR +
                            this.getNumberString(i),
                          ThietBi.PRE_FRAME_SLOT,
                          this.getNumberString(i),
                          item.FRAMETD_ID,
                          ThietBi.PRE_FRAME,
                          {
                            slotIndex: i,
                            slotType: ThietBi.PRE_FRAME_SLOT,
                          }
                        );
                        nodes.push(nodeSlot);
                      }
                    }
                  });
                }
              }
              let frameCard = dsThietBi_Frame.CardTD;
              if (frameCard != null) {
                if (!(frameCard == null || frameCard.length == 0)) {
                  frameCard.forEach((item) => {
                    let r1 =
                      item.FRAMETD_ID +
                      ThietBi.SEPARATOR +
                      this.getNumberString(item.VITRI);
                    let slot = nodes.find(
                      (x) =>
                        x.itemType == ThietBi.PRE_FRAME_SLOT && x.itemID == r1
                    );
                    if (slot != null) {
                      slot.text += " " + item.VITRI;
                      slot.itemID = item.CARDTD_ID;
                      slot.itemType = ThietBi.PRE_FRAME_CARD;
                      slot.icon = ThietBi.PRE_FRAME_CARD.toLowerCase();
                      slot.id =
                        slot.itemType +
                        ThietBi.SEPARATOR +
                        slot.itemID.toString();
                    }
                    // let node = this.createNode(
                    //   item.CARDTD_ID,
                    //   ThietBi.PRE_FRAME_CARD,
                    //   item.VITRI,
                    //   item.FRAMETD_ID,
                    //   ThietBi.PRE_FRAME
                    // );
                    // nodes.push(node);
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
    createNode: function (
      nodeID,
      nodeType,
      nodeText,
      parentID,
      parentType,
      attributes
    ) {
      let id = nodeType + ThietBi.SEPARATOR + nodeID.toString();
      let icon = nodeType.toString().toLowerCase();
      let attrs = null;
      if (!this.checkIsNull(attributes)) {
        attrs = attributes;
        if (!this.checkIsNull(attributes.MUC)) {
          icon += ThietBi.SEPARATOR + attributes.MUC.toString();
        }
      }
      if (nodeText == null) nodeText = "(null)";
      let parent = null;
      if (parentID != null)
        parent = parentType + ThietBi.SEPARATOR + parentID.toString();
      return {
        id: id,
        text: nodeText,
        parentID: parent,
        icon: icon,
        hasChildren: false,
        expanded: false,
        selected: false,
        itemID: nodeID,
        itemType: nodeType,
        itemParentID: parentID,
        itemParentType: parentType,
        itemAttributes: attrs,
      };
    },
    getNode: function (nodeID, nodeType, parentID, parentType) {
      if (this.checkIsEmpty(parentID) || this.checkIsEmpty(parentType))
        return this.dsNodes.find(
          (x) => x.itemID == nodeID && x.itemType == nodeType
        );
      else
        return this.dsNodes.find(
          (x) =>
            x.itemID == nodeID &&
            x.itemType == nodeType &&
            x.itemParentID == parentID &&
            x.itemParentType == parentType
        );
    },
    addNode: function (
      nodeID,
      nodeType,
      nodeText,
      parentID,
      parentType,
      selected,
      attributes
    ) {
      let node = this.createNode(
        nodeID,
        nodeType,
        nodeText,
        parentID,
        parentType
      );
      this.dsNodes.push(node);
      let parentNode = this.dsNodes.find((x) => x.id == node.parentID);
      if (parentNode != null) parentNode.hasChildren = true;
      // let filters = this.dsNodes.filter((x) => x.id == node.parentID);
      // if (!(filters == null || filters.length == 0)) {
      //   for (let i = 0; i < filters.length; i++) {
      //     filters[i].hasChildren = true;
      //   }
      // }
      if (!(attributes == undefined || attributes == null)) {
        if (!(attributes.soSlot == undefined || attributes.soSlot == null)) {
          if (attributes.soSlot > 0) {
            let soBD = 0;
            if (!(attributes.soBD == undefined || attributes.soBD == null))
              soBD = attributes.soBD;
            for (let i = soBD; i < soBD + attributes.soSlot; i++) {
              let nodeSlot = this.createNode(
                i.toString(),
                attributes.slotType,
                this.getNumberString(i),
                node.itemID,
                node.itemType,
                {
                  slotIndex: i,
                  slotType: attributes.slotType,
                }
              );
              this.dsNodes.push(nodeSlot);
            }
            node.hasChildren = true;
            node.expanded = true;
          }
        }
      }
      if (selected === true) this.selectedItem(nodeID, nodeType);
    },
    updateNode: function (
      nodeID,
      nodeType,
      nodeText,
      parentID,
      parentType,
      selected,
      attributes
    ) {
      let id = nodeType + ThietBi.SEPARATOR + nodeID.toString();
      let isUpdateSlot = false;
      if (!(attributes == undefined || attributes == null)) {
        if (
          !(attributes.slotIndex == undefined || attributes.slotIndex == null)
        ) {
          isUpdateSlot = true;
        }
      }
      if (isUpdateSlot) {
        let parent = parentType + ThietBi.SEPARATOR + parentID.toString();
        let childs = this.dsNodes.filter(
          (x) =>
            x.parentID == parent &&
            !(
              x.itemAttributes == undefined ||
              x.itemAttributes == null ||
              x.itemAttributes.slotIndex == undefined ||
              x.itemAttributes.slotIndex == null
            )
        );
        if (childs != null) {
          for (let i = 0; i < childs.length; i++) {
            const child = childs[i];
            if (
              child.itemAttributes.slotIndex == attributes.slotIndex &&
              child.itemAttributes.slotType == attributes.slotType
            ) {
              child.id = id;
              child.text =
                this.getNumberString(child.itemAttributes.slotIndex) +
                " " +
                nodeText;
              child.itemID = nodeID;
              child.itemType = nodeType;
              child.icon = nodeType.toLowerCase();
            }
          }
          if (selected === true) this.selectedItem(nodeID, nodeType);
        }
      } else {
        let node = this.dsNodes.find((x) => x.id == id);
        if (node != null) {
          node.text = nodeText;
          if (!(parentType == undefined || parentType == null)) {
            let parent = null;
            let moveParent = false;
            if (parentID != null)
              parent = parentType + ThietBi.SEPARATOR + parentID.toString();
            if (node.parentID != parent) {
              let p1 = this.dsNodes.filter((x) => x.parentID == node.parentID);
              if (p1.length == 1) {
                let p2 = this.dsNodes.find((x) => x.id == node.parentID);
                if (p2 != null) {
                  p2.hasChildren = false;
                }
              }
              node.parentID = parent;
              node.itemParentID = parentID;
              node.itemParentType = parentType;
              moveParent = true;
            }
            if (moveParent) {
              let p3 = this.dsNodes.find((x) => x.id == parent);
              if (p3 != null) {
                p3.hasChildren = true;
              }
            }
            this.dsNodes = this.dsNodes.sort((a, b) =>
              a.text < b.text ? -1 : 1
            );
            // if (moveParent) {
            //   let newNode = this.createNode(
            //     node.itemID,
            //     node.itemType,
            //     node.text,
            //     node.itemParentID,
            //     node.itemParentType,
            //     node.itemAttributes
            //   );
            //   for (let i = 0; i < this.dsNodes.length; i++) {
            //     const n = this.dsNodes[i];
            //     if (n.id == node.id) {
            //       this.dsNodes.splice(i, 1);
            //       break;
            //     }
            //   }
            //   this.dsNodes.push(newNode);
            //   let p3 = this.dsNodes.find((x) => x.id == newNode.parentID);
            //   if(p3 != null){
            //     p3.hasChildren = true;
            //   }
            // }
          }
          if (!(attributes == undefined || attributes == null)) {
            if (
              !(attributes.soSlot == undefined || attributes.soSlot == null)
            ) {
              if (attributes.soSlot > 0) {
                let childs = this.dsNodes.filter(
                  (x) =>
                    x.parentID == node.id &&
                    !(
                      x.itemAttributes == undefined ||
                      x.itemAttributes == null ||
                      x.itemAttributes.slotIndex == undefined ||
                      x.itemAttributes.slotIndex == null
                    )
                );
                let deleteChilds = [];
                let countChilds = 0;
                if (childs != null) {
                  countChilds = childs.length;
                  if (childs.length > attributes.soSlot) {
                    for (let i = 0; i < childs.length; i++) {
                      const child = childs[i];
                      if (child.itemAttributes.slotIndex >= attributes.soSlot) {
                        deleteChilds.push(child);
                      }
                    }
                  }
                }
                if (countChilds < attributes.soSlot) {
                  for (let i = countChilds; i < attributes.soSlot; i++) {
                    let child = this.createNode(
                      i.toString(),
                      attributes.slotType,
                      this.getNumberString(i),
                      node.itemID,
                      node.itemType,
                      {
                        slotIndex: i,
                        slotType: attributes.slotType,
                      }
                    );
                    this.dsNodes.push(child);
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
          }
          if (selected === true) this.selectedItem(nodeID, nodeType);
        }
      }
    },
    deleteNode: function (nodeID, nodeType) {
      let id = nodeType + ThietBi.SEPARATOR + nodeID.toString();
      let node = this.dsNodes.find((x) => x.id == id);
      if (node != null) {
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
          node.id = node.itemType + ThietBi.SEPARATOR + node.itemID.toString();
        } else {
          this.dsNodes = this.dsNodes.filter((x) => x.id != id);
          let filters = this.dsNodes.filter((x) => x.selected == true);
          if (!(filters == null || filters.length == 0)) {
            for (let i = 0; i < filters.length; i++) {
              filters[i].selected = false;
            }
          }
        }
        let parent = this.dsNodes.find((x) => x.id == node.parentID);
        this.selectedItem(parent.itemID, parent.itemType);
        parent.hasChildren = false;
        parent.expanded = false;
        if (parent != null) {
          let childs = this.dsNodes.filter((x) => x.parentID == parent.id);
          if (!(childs == null || childs.length == 0)) {
            parent.hasChildren = true;
            parent.expanded = true;
          }
        }
      }
    },
    selectedItem: function (nodeID, nodeType) {
      let id = nodeType + ThietBi.SEPARATOR + nodeID.toString();
      this.selectedNodes = [id];
      let expandedNodes = this.dsNodes.filter((x) => x.expanded == true);
      if (expandedNodes != null) {
        for (let j = 0; j < expandedNodes.length; j++) {
          const expandedNode = expandedNodes[j];
          expandedNode.expanded = false;
        }
      }
      // let selectedNodes = this.dsNodes.find((x) => x.selected == true);
      // if (selectedNodes != null) {
      //   for (let j = 0; j < selectedNodes.length; j++) {
      //     const selectedNode = selectedNodes[j];
      //     selectedNode.selected = false;
      //   }
      // }

      let node = this.dsNodes.find((x) => x.id == id);
      if (node != null) {
        if (node.parentID != null) {
          let ps = this.getParents(node.parentID);
          if (!(ps == null || ps.length == 0)) {
            for (let i = 0; i < ps.length; i++) {
              ps[i].expanded = true;
            }
          }
        }
        // node.selected = true;
        // let filters = this.dsNodes.filter(
        //   (x) => x.selected == true && x.id != node.id
        // );
        // if (!(filters == null || filters.length == 0)) {
        //   for (let i = 0; i < filters.length; i++) {
        //     filters[i].selected = false;
        //   }
        // }
      }
    },
    getParents: function (parentID) {
      let rs = [];
      let parent = this.dsNodes.find((x) => x.id == parentID);
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
    getNumberString: function (value) {
      if (!(value == undefined || value == null)) {
        let result = value.toString();
        if (result.length == 1) result = "0" + result;
        return "#" + result;
      } else return "#00";
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
  },
};
</script>
