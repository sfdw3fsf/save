<template src="./index.template.html"></template>
<script>
import { required, numeric, minValue } from "vuelidate/lib/validators";
export default {
  components: {},
  data: function () {
    return {
      txtSearch: "",
      dsDonVi: [],
      dsShelfEwsdFull: [],
      dsCard: [],
      currentItem: {},
    };
  },
  computed: {
    tenShelfEwsdInvalidStatus: function () {
      if (this.currentItem.SHELF_EWSD_ID != undefined) {
        return this.$v.currentItem.TEN_SHELF.$invalid;
      } else return false;
    },
    soSlotInvalidStatus: function () {
      if (this.currentItem.SHELF_EWSD_ID != undefined) {
        return this.$v.currentItem.SO_SLOT.$invalid;
      } else return false;
    },
    enabledSave: function () {
      return this.currentItem.SHELF_EWSD_ID != undefined;
    },
    enabledNew: function () {
      return this.currentItem.SHELF_EWSD_ID > 0;
    },
    enabledDelete: function () {
      return !(
        this.currentItem.SHELF_EWSD_ID == 0 ||
        this.currentItem.SHELF_EWSD_ID == undefined
      );
    },
    treeFields: function () {
      return {
        dataSource: this.dsDonViFiltered,
        id: "DONVI_ID",
        text: "TEN_DV",
        parentID: "DONVI_CHA_ID",
        hasChildren: "HasChildren",
        expanded: "Expanded",
        selected: "Selected",
      };
    },
    dsDonViFiltered: function () {
      if (this.dsDonVi != null) {
        let items = this.dsDonVi.filter((item) => {
          return item.TEN_DV.toLowerCase().includes(
            this.txtSearch.toLowerCase()
          );
        });
        let rs = [];
        if (!(items == null || items.length == 0)) {
          for (let i = 0; i < items.length; i++) {
            const item = items[i];
            let ck = rs.find((x) => x.DONVI_ID == item.DONVI_ID);
            if (ck == null) {
              rs.push(item);
              if (item.DONVI_CHA_ID != null) {
                let ps = this.getParents(item.DONVI_CHA_ID);
                if (!(ps == null || ps.length == 0)) {
                  for (let j = 0; j < ps.length; j++) {
                    let ck2 = rs.find((x) => x.DONVI_ID == ps[j].DONVI_ID);
                    if (!ck2) rs.push(ps[j]);
                  }
                }
              }
            }
          }
        }
        return rs;
      } else return [];
    },
  },
  watch: {},
  validations: {
    currentItem: {
      TEN_SHELF: {
        required,
      },
      SO_SLOT: {
        required,
        numeric,
        minValue: minValue(0),
      },
    },
  },
  created: async function () {
    this.$root.showLoading(true);
    try {
      await this.loadDonVi();
    } finally {
      this.$root.showLoading(false);
    }
  },
  methods: {
    reloadDonVi: async function () {
      this.$root.showLoading(true);
      try {
        let m = await this.loadDonVi();
      } finally {
        this.$root.showLoading(false);
      }
    },
    loadDonVi: async function () {
      let ds = await this.getDsDonVi();
      if (!(ds == null || ds.length == 0)) {
        this.dsShelfEwsdFull = await this.getDsShelfEwsdFull();
        let tuRacks = this.getDsTuRackFromList(this.dsShelfEwsdFull);
        if (!(tuRacks == null || tuRacks.length == 0)) {
          for (let i = 0; i < tuRacks.length; i++) {
            const element = tuRacks[i];
            ds.push(element);
          }
        }
        let ewsds = this.getDsEwsdFromList(this.dsShelfEwsdFull);
        if (!(ewsds == null || ewsds.length == 0)) {
          for (let i = 0; i < ewsds.length; i++) {
            const element = ewsds[i];
            ds.push(element);
          }
        }
        let shelfEwsds = this.getDsShelfEwsdFromList(this.dsShelfEwsdFull);
        if (!(shelfEwsds == null || shelfEwsds.length == 0)) {
          for (let i = 0; i < shelfEwsds.length; i++) {
            const element = shelfEwsds[i];
            ds.push(element);
          }
        }
      }
      for (let i = 0; i < ds.length; i++) {
        const item = ds[i];
        let ck = ds.findIndex((f) => {
          return f.DONVI_CHA_ID == item.DONVI_ID;
        });
        if (ck >= 0) item.HasChildren = true;
      }
      for (let i = 0; i < ds.length; i++) {
        const item = ds[i];
        if (item.ITEM_TYPE == "DONVI" && item.DONVI_CHA_ID != null) {
          if (!item.HasChildren) {
            ds.splice(i, 1);
            i--;
          }
        }
      }
      for (let i = 0; i < ds.length; i++) {
        const item = ds[i];
        if (item.ITEM_TYPE == "DONVI" && item.DONVI_CHA_ID == null) {
          if (!item.HasChildren) {
            ds.splice(i, 1);
            i--;
          }
        }
      }
      this.dsDonVi = ds; //HasChildren
    },
    getParents: function (parentID) {
      let rs = [];
      let parent = this.dsDonVi.find((x) => x.DONVI_ID == parentID);
      if (parent != null) {
        rs.push(parent);
        if (parent.DONVI_CHA_ID != null) {
          let ps = this.getParents(parent.DONVI_CHA_ID);
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
      if (!(result.data == null || result.data.length == 0)) {
        let d = [];
        let items = result.data.filter((f) => {
          return f.DONVI_CHA_ID == null;
        });
        for (let i = 0; i < items.length; i++) {
          const item = items[i];
          let itemData = {
            DONVI_ID: item.DONVI_ID.toString(),
            TEN_DV: item.TEN_DV,
            DONVI_CHA_ID: null,
            HasChildren: false,
            Expanded: false,
            Selected: false,
            ITEM_TYPE: "DONVI",
          };
          d.push(itemData);
          let childs = result.data.filter((f) => {
            return f.DONVI_CHA_ID == item.DONVI_ID;
          });
          if (!(childs == null || childs.length == 0)) {
            for (let k = 0; k < childs.length; k++) {
              const child = childs[k];
              let childData = {
                DONVI_ID: child.DONVI_ID.toString(),
                TEN_DV: child.TEN_DV,
                DONVI_CHA_ID: item.DONVI_ID.toString(),
                HasChildren: false,
                Expanded: false,
                Selected: false,
                ITEM_TYPE: "DONVI",
              };
              d.push(childData);
              let r = this.getDsDonViRecursive(childData, result.data);
              if (r == null || r.length > 0) {
                for (let j = 0; j < r.length; j++) {
                  const element = r[j];
                  d.push(element);
                }
              }
            }
          }
        }
        return d;
      } else return [];
    },
    getDsDonViRecursive: function (parentItem, listItems) {
      let rs = [];
      let items = listItems.filter((f) => {
        return f.DONVI_CHA_ID == parentItem.DONVI_ID;
      });
      for (let i = 0; i < items.length; i++) {
        const item = items[i];
        let childData = {
          DONVI_ID: item.DONVI_ID.toString(),
          TEN_DV: item.TEN_DV,
          DONVI_CHA_ID: parentItem.DONVI_ID.toString(),
          HasChildren: false,
          Expanded: false,
          Selected: false,
          ITEM_TYPE: "DONVI",
        };
        rs.push(childData);
        let childs = this.getDsDonViRecursive(childData, listItems);
        if (!(childs == null || childs.length == 0)) {
          for (let j = 0; j < childs.length; j++) {
            const child = childs[j];
            rs.push(child);
          }
        }
      }
      return rs;
    },
    treeDonVi_nodeClicked: async function (args) {
      let item = this.dsDonVi.find((x) => x.DONVI_ID == args.node.dataset.uid);
      if (item != null) {
        if (item.ITEM_TYPE == "SHELF_EWSD") {
          this.$root.showLoading(true);
          try {
            let itemID = item.DONVI_ID.replace(item.ITEM_TYPE + "_", "");
            let itemData = this.dsShelfEwsdFull.find(
              (x) => x.SHELF_EWSD_ID == itemID
            );
            if (itemData != null) {
              this.currentItem = {
                SHELF_EWSD_ID: itemData.SHELF_EWSD_ID,
                TEN_SHELF: itemData.TEN_SHELF,
                EWSD_ID: itemData.EWSD_ID,
                SO_SLOT: itemData.SO_SLOT,
                DADUNG: itemData.DADUNG,
                VITRI: itemData.VITRI,
              };
            }
            this.dsCard = await this.getDsCard(itemID);
          } finally {
            this.$root.showLoading(false);
          }
        } else {
          this.currentItem = {};
          this.dsCard = [];
        }
      } else {
        this.currentItem = {};
        this.dsCard = [];
      }
    },
    getDsCard: async function (shelf_ewsd_id) {
      let result = await this.$root.context.get(
        "/web-ecms/thietbichuyenmach/layCardEwsdTheoShelf",
        {
          shelfEwsdId: shelf_ewsd_id,
        }
      );
      return result.data;
    },
    getDsTuRack: async function () {
      let result = await this.$root.context.post(
        "/web-ecms/danhmuc/layDsTuRack"
      );
      return result.data;
    },
    getDsEwsdTheoTuRack: async function (tuRackID) {
      let result = await this.$root.context.post(
        "/web-ecms/danhmuc/layDsEwsdTheoTuRack",
        {
          id: tuRackID,
        }
      );
      return result.data;
    },
    getDsShelfEwsdTheoEwsd: async function (ewsdID) {
      let result = await this.$root.context.post(
        "/web-ecms/danhmuc/layDsShelfEwsdTheoEwsd",
        {
          id: ewsdID,
        }
      );
      return result.data;
    },
    getDsTuRackFromList: function (listItems) {
      let result = [];
      if (!(listItems == null || listItems.length == 0)) {
        for (let i = 0; i < listItems.length; i++) {
          const tuRack = listItems[i];
          let donViID = "TURACK_" + tuRack.TURACK_ID.toString();
          let ck = result.findIndex((x) => x.DONVI_ID == donViID);
          if (ck < 0) {
            let tuRackData = {
              DONVI_ID: donViID,
              TEN_DV: tuRack.TEN_TU,
              DONVI_CHA_ID: tuRack.DONVI_ID,
              HasChildren: false,
              Expanded: false,
              Selected: false,
              ITEM_TYPE: "TURACK",
            };
            result.push(tuRackData);
          }
        }
      }
      return result;
    },
    getDsEwsdFromList: function (listItems) {
      let result = [];
      if (!(listItems == null || listItems.length == 0)) {
        for (let i = 0; i < listItems.length; i++) {
          const ewsd = listItems[i];
          let donViID = "EWSD_" + ewsd.EWSD_ID;
          let ck = result.findIndex((x) => x.DONVI_ID == donViID);
          if (ck < 0) {
            let ewsdData = {
              DONVI_ID: donViID,
              TEN_DV: ewsd.TEN_EWSD,
              DONVI_CHA_ID: "TURACK_" + ewsd.TURACK_ID,
              HasChildren: false,
              Expanded: false,
              Selected: false,
              ITEM_TYPE: "EWSD",
            };
            result.push(ewsdData);
          }
        }
      }
      return result;
    },
    getDsShelfEwsdFromList: function (listItems) {
      let result = [];
      if (!(listItems == null || listItems.length == 0)) {
        for (let i = 0; i < listItems.length; i++) {
          const shelfEwsd = listItems[i];
          let donViID = "SHELF_EWSD_" + shelfEwsd.SHELF_EWSD_ID.toString();
          let ck = result.findIndex((x) => x.DONVI_ID == donViID);
          if (ck < 0) {
            let shelfEwsdData = {
              DONVI_ID: donViID,
              TEN_DV: shelfEwsd.TEN_SHELF,
              DONVI_CHA_ID: "EWSD_" + shelfEwsd.EWSD_ID.toString(),
              HasChildren: false,
              Expanded: false,
              Selected: false,
              ITEM_TYPE: "SHELF_EWSD",
            };
            result.push(shelfEwsdData);
          }
        }
      }
      return result;
    },
    getDsShelfEwsdFull: async function () {
      let result = await this.$root.context.post(
        "/web-ecms/thietbichuyenmach/layDsShelfEwsdFull"
      );
      return result.data;
    },
    xoaShelfEwsd: async function (id) {
      let result = await this.$root.context.post(
        "/web-ecms/thietbichuyenmach/XoaEwsdShelf",
        {
          id: id,
        }
      );
      return result.data;
    },
    capNhatShelfEwsd: async function (itemData) {
      let result = await this.$root.context.post(
        "/web-ecms/thietbichuyenmach/capNhatEWSDSHELF",
        {
          ewsd_id: itemData.EWSD_ID,
          shelf_ewsd_id: itemData.SHELF_EWSD_ID,
          so_slot: itemData.SO_SLOT,
          ten_shelf: itemData.TEN_SHELF,
          vi_tri: itemData.VITRI,
        }
      );
      return result.data;
    },
    themShelfEwsd: async function (itemData) {
      let result = await this.$root.context.post(
        "/web-ecms/thietbichuyenmach/themEWSDSHELF",
        {
          ewsd_id: itemData.EWSD_ID,
          so_slot: itemData.SO_SLOT,
          ten_shelf: itemData.TEN_SHELF,
          vi_tri: itemData.VITRI,
        }
      );
      return result.data;
    },
    selectedItem: function (donViID) {
      let itemDonVi = this.dsDonVi.find((x) => x.DONVI_ID == donViID);
      if (itemDonVi != null) {
        if (itemDonVi.DONVI_CHA_ID != null) {
          let ps = this.getParents(itemDonVi.DONVI_ID);
          if (!(ps == null || ps.length == 0)) {
            for (let i = 0; i < ps.length; i++) {
              ps[i].Expanded = true;
            }
          }
        }
        itemDonVi.Selected = true;
      }
      let filters = this.dsDonVi.filter(
        (x) => x.Selected == true && x.DONVI_ID != itemDonVi.DONVI_ID
      );
      if (!(filters == null || filters.length == 0)) {
        for (let i = 0; i < filters.length; i++) {
          filters[i].Selected = false;
        }
      }
    },
    btnNhapMoi_Click: function () {
      if (this.currentItem.EWSD_ID != undefined) {
        let ewsdID = this.currentItem.EWSD_ID;
        this.currentItem = {
          SHELF_EWSD_ID: 0,
          TEN_SHELF: "",
          EWSD_ID: ewsdID,
          SO_SLOT: 0,
          DADUNG: 0,
          VITRI: 0,
        };
        this.dsCard = [];
      } else {
        this.$root.toastError("Bạn chưa chọn thiết bị");
      }
    },
    btnGhiLai_Click: async function () {
      this.$root.showLoading(true);
      try {
        if (this.currentItem.EWSD_ID != undefined) {
          this.$v.$touch();
          if (this.$v.$invalid) {
            let msg = "";
            if (this.$v.currentItem.TEN_SHELF.$invalid) {
              msg += "Tên Shelf không được để trống";
            }
            if (this.$v.currentItem.SO_SLOT.$invalid) {
              if (msg != "") msg += "\n";
              msg += "Số slot không không hợp lệ";
            }
            this.$root.toastError(msg);
          } else {
            if (this.currentItem.SHELF_EWSD_ID == 0) {
              let rs = await this.themShelfEwsd(this.currentItem);
              if (rs != null) {
                this.currentItem.SHELF_EWSD_ID = rs.SHELF_EWSD_ID;
                let itemID =
                  "SHELF_EWSD_" + this.currentItem.SHELF_EWSD_ID.toString();
                let shelfEwsdData = {
                  DONVI_ID: itemID,
                  TEN_DV: this.currentItem.TEN_SHELF,
                  DONVI_CHA_ID: "EWSD_" + this.currentItem.EWSD_ID.toString(),
                  HasChildren: false,
                  Expanded: false,
                  Selected: false,
                  ITEM_TYPE: "SHELF_EWSD",
                };
                this.dsDonVi.push(shelfEwsdData);
                this.selectedItem(itemID);
                this.$root.toastSuccess("Thêm mới thành công");
              }
            } else {
              let rs = await this.capNhatShelfEwsd(this.currentItem);
              if (rs.result) {
                let itemData = this.dsShelfEwsdFull.find(
                  (x) => x.SHELF_EWSD_ID == this.currentItem.SHELF_EWSD_ID
                );
                if (itemData != null) {
                  itemData.SO_SLOT = this.currentItem.SO_SLOT;
                  itemData.TEN_SHELF = this.currentItem.TEN_SHELF;
                  itemData.VITRI = this.currentItem.VITRI;
                }
                let itemID =
                  "SHELF_EWSD_" + this.currentItem.SHELF_EWSD_ID.toString();
                let itemDonVi = this.dsDonVi.find((x) => x.DONVI_ID == itemID);
                if (itemDonVi != null) {
                  itemDonVi.TEN_DV = this.currentItem.TEN_SHELF;
                  this.selectedItem(itemID);
                }
                this.$root.toastSuccess("Cập nhật thành công");
                //await this.reloadDonVi();
              }
            }
          }
        } else {
          this.$root.toastError("Bạn chưa chọn thiết bị");
        }
      } finally {
        this.$root.showLoading(false);
      }
    },
    btnHuy_Click: function () {
      if (this.currentItem.EWSD_ID != undefined) {
        this.currentItem = {};
        this.dsCard = [];
      } else {
        this.$root.toastError("Bạn chưa chọn thiết bị");
      }
    },
    btnXoa_Click: async function () {
      this.$root.showLoading(true);
      try {
        if (
          !(
            this.currentItem.SHELF_EWSD_ID == undefined ||
            this.currentItem.SHELF_EWSD_ID == 0
          )
        ) {
          let rs = await this.xoaShelfEwsd(this.currentItem.SHELF_EWSD_ID);
          if (rs.result) {
            this.dsShelfEwsdFull = this.dsShelfEwsdFull.filter(
              (x) => x.SHELF_EWSD_ID != this.currentItem.SHELF_EWSD_ID
            );
            let itemID =
              "SHELF_EWSD_" + this.currentItem.SHELF_EWSD_ID.toString();
            this.dsDonVi = this.dsDonVi.filter((x) => x.DONVI_ID != itemID);
            this.currentItem = {};
            this.$root.toastSuccess("Xóa thành công");
          }
          this.dsCard = [];
        } else {
          this.$root.toastError("Bạn chưa chọn thiết bị");
        }
      } finally {
        this.$root.showLoading(false);
      }
    },
  },
};
</script>
<style>
</style>
