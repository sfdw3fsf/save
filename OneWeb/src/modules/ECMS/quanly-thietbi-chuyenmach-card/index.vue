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
      dsCardEwsdFull: [],
      dsPort: [],
      dsNhomCard: [],
      dsLoaiCard: [],
      dsTTPort: [],
      currentItem: {},
      id_shelf: -1,
      enabledSave: false,
      enabledLapCard: false,
      enabledDelete: false,
      enabledExcel: false,
      enabledDoiTT: false,
      enabledTrangThai: true,
      isShowDiv: false,
      ok:true,
      isError:false,
      dataExcelEx: [],
      isCreateNew: false,
      dsSelectedPort: [],
      card_id: -1
    };
  },
  computed: {
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
          console.log(shelfEwsds)
          for (let i = 0; i < shelfEwsds.length; i++) {
            const element = shelfEwsds[i];
            ds.push(element);
          }
        }
        this.dsCardEwsdFull = await this.getDsCardEwsdFull();
        console.log('this.dsCardEwsdFull');
        let ewsdCard = this.getDsCardEwsdFromList(this.dsCardEwsdFull);
        if (!(ewsdCard == null || ewsdCard.length == 0)) {
          console.log(ewsdCard)
          for (let i = 0; i < ewsdCard.length; i++) {
            const element = ewsdCard[i];
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
        try {
          this.loading(true)
          console.log("item click",item)
          if (item.ITEM_TYPE == "SHELF") {
            this.enabledLapCard = true
            this.isShowDiv = false
            this.enabledSave = false
            this.enabledDelete = false
            this.id_shelf = item.DONVI_ID.replace(item.ITEM_TYPE + "_", "");
            
          }
          if (item.ITEM_TYPE === "CARD_EWSD"){
            try {
                this.isCreateNew = false
                this.isShowDiv = true
                this.enabledSave = true
                this.enabledDelete = true
                this.enabledLapCard = false
                let itemID = item.DONVI_ID.replace(item.ITEM_TYPE + "_", "");
                let itemData = this.dsCardEwsdFull.find(
                  (x) => x.CARD_EWSD_ID == itemID
                );
                let cardUsed = await this.getPortInCard(itemData.CARD_EWSD_ID)
                this.dsNhomCard = await this.layDsNhomCard(15)
                this.dsLoaiCard = await this.layDsLoaiCard(itemData.NHOMCARD_ID,15)
                this.dsTTPort = await this.layDsTrangThai()
                this.dsPort = await this.layPortTheoCard(itemID);
                this.card_id = itemID;
                let tt_port_id = -1
                if (this.dsPort.length > 0) {
                  tt_port_id = this.dsPort[0].TT_PORT_ID
                  this.enabledExcel = true
                }
                if (itemData != null) {
                  this.currentItem = {
                    CARD_EWSD_ID: itemData.CARD_EWSD_ID,
                    SHELF_EWSD_ID: itemData.SHELF_EWSD_ID,
                    VITRI_CAM: itemData.VITRI,
                    VITRI_QUYUOC: itemData.VITRI_QU,
                    NHOMCARD_ID: itemData.NHOMCARD_ID,
                    LOAICARD_ID: itemData.LOAICARD_ID,
                    SOLUONG_PORT: itemData.SO_PORT,
                    DANGSUDUNG: cardUsed.USED,
                    TT_PORT: tt_port_id,
                    TEN_CARD: itemData.LOAI_CARD
                  };
                }
                            
            } finally {
              // this.$root.showLoading(false);
            }
          } else {
            this.currentItem = {};
            this.dsPort = [];
            this.enabledExcel = false
          }
        } catch (error) {
          
        } finally{
          this.loading(false)
        }
      } else {
        this.currentItem = {};
        this.dsPort = [];
        this.enabledExcel = false
      }
    },
    layPortTheoCard: async function (shelf_ewsd_id) {
      let result = await this.$root.context.get(
        "/web-ecms/thietbichuyenmach/layPortTheoCard",
        {
          cardEwsdId: shelf_ewsd_id,
        }
      );
      return result.data;
    },
    layDsNhomCard: async function (id) {
      let result = await this.$root.context.get(
        "/web-ecms/thietbichuyenmach/lay-ds-nhom-card",
        {
          loaitbi_id: id,
        }
      );
      return result.data;
    },
    layDsLoaiCard: async function (id1, id2) {
      let result = await this.$root.context.get(
        "/web-ecms/thietbichuyenmach/lay-ds-loai-card",
        {
          nhomcard_id: id1,
          loaitbi_id: id2
        }
      );
      return result.data;
    },
    layDsTrangThai: async function () {
      let result = await this.$root.context.get(
        "/web-ecms/thietbichuyenmach/lay-ds-trangthai-port"
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
          let donViID = "SHELF_" + shelfEwsd.SHELF_EWSD_ID.toString();
          let ck = result.findIndex((x) => x.DONVI_ID == donViID);
          if (ck < 0) {
            let shelfEwsdData = {
              DONVI_ID: donViID,
              TEN_DV: shelfEwsd.TEN_SHELF,
              DONVI_CHA_ID: "EWSD_" + shelfEwsd.EWSD_ID.toString(),
              HasChildren: false,
              Expanded: false,
              Selected: false,
              ITEM_TYPE: "SHELF",
            };
            result.push(shelfEwsdData);
          }
        }
      }
      return result;
    },
    getDsCardEwsdFromList: function (listItems) {
      let result = [];
      if (!(listItems == null || listItems.length == 0)) {
        listItems = listItems.slice(0, 500)
        for (let i = 0; i < listItems.length; i++) {
          const shelfEwsd = listItems[i];
          let donViID = "CARD_EWSD_" + shelfEwsd.CARD_EWSD_ID.toString();
          let ck = result.findIndex((x) => x.DONVI_ID == donViID);
          if (ck < 0) {
            // if (id_shelf === shelfEwsd.SHELF_EWSD_ID.toString())
            {
              let shelfEwsdData = {
                DONVI_ID: donViID,
                TEN_DV: shelfEwsd.LOAI_CARD,
                DONVI_CHA_ID: "SHELF_" + shelfEwsd.SHELF_EWSD_ID.toString(),
                HasChildren: false,
                Expanded: true,
                Selected: false,
                ITEM_TYPE: "CARD_EWSD",
              };
              result.push(shelfEwsdData);
            }

          }
        }
      }
      console.log('result ',result)
      return result;
    },
    getDsShelfEwsdFull: async function () {
      let result = await this.$root.context.post(
        "/web-ecms/thietbichuyenmach/layDsShelfEwsdFull"
      );
      return result.data;
    },
    getDsCardEwsdID: async function (id) {
      let result = await this.$root.context.get(
        "/web-ecms/thietbichuyenmach/ds-card-theo-shelf-id",
        {shelf_id: id}
      );
      return result.data;
    },
    getDsCardEwsdFull: async function () {
      let result = await this.$root.context.get(
        "/web-ecms/thietbichuyenmach/dsCardEwsd"
      );
      return result.data;
    },
    getPortInCard: async function (id) {
      let result = await this.$root.context.get(
        "/web-ecms/thietbichuyenmach/lay-soluong-port-card-id",
        {card_id: id}
      );
      return result.data;
    },
    xoa: async function (id) {
      let result = await this.$root.context.post(
        `/web-ecms/thietbichuyenmach/xoaewsdcard?cardEwsdId=${id}`
      );
      return result.data;
    },
    kiemtraXoa: async function (id) {
      let result = await this.$root.context.get(
        "/web-ecms/thietbichuyenmach/check-port-logic-delete-card",
        {
          card_ewsd_id: id,
        }
      );
      return result.data;
    },
    capNhat: async function (itemData) {
      let result = await this.$root.context.post(
        "/web-ecms/thietbichuyenmach/capnhatewsdcard",
        {
          loaicard_id: itemData.LOAICARD_ID,
          ten_card: itemData.TEN_CARD,
          shelf_ewsd_id: itemData.SHELF_EWSD_ID,
          vitri_qu: itemData.VITRI_QUYUOC,
          card_ewsd_id: itemData.CARD_EWSD_ID,
          vitri: itemData.VITRI_CAM,


        }
      );
      return result.data;
    },
    them: async function (itemData) {
      let result = await this.$root.context.post(
        "/web-ecms/thietbichuyenmach/themewsdcard",
        {
          loaicard_id: itemData.LOAICARD_ID,
          ten_card: itemData.TEN_CARD,
          shelf_ewsd_id: this.id_shelf,
          vitri_qu: itemData.VITRI_QUYUOC,
          vitri: itemData.VITRI_CAM,
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
        this.dsPort = [];
        this.enabledExcel = false
      } else {
        this.$root.toastError("Bạn chưa chọn thiết bị");
      }
    },
    btnGhiLai_Click: async function () {
      
      try {
        if (this.Kiemtra_dulieu()) {
            if (this.currentItem.CARD_EWSD_ID === undefined) {
              this.loading(true);
              let rs = await this.them(this.currentItem);
              if (rs != null) {
                this.currentItem.CARD_EWSD_ID = rs.CARD_EWSD_ID;
                let itemID = "CARD_EWSD_" + this.currentItem.CARD_EWSD_ID.toString();
                let shelfEwsdData = {
                  DONVI_ID: itemID,
                  TEN_DV: this.currentItem.TEN_CARD,
                  DONVI_CHA_ID: "SHELF_" + this.id_shelf,
                  HasChildren: false,
                  Expanded: false,
                  Selected: false,
                  ITEM_TYPE: "CARD_EWSD",
                };
                this.dsDonVi.push(shelfEwsdData);
                this.selectedItem(itemID);
                this.$root.toastSuccess("Thêm mới thành công");
              }
            } else {
              this.loading(true);
              console.log('update ',this.currentItem)
              let rs = await this.capNhat(this.currentItem);
              if (rs.result) {
                let itemData = this.dsCardEwsdFull.find(
                  (x) => x.CARD_EWSD_ID == this.currentItem.CARD_EWSD_ID
                );
                console.log('itemData ',itemData)
                if (itemData != null) {
                  itemData.CARD_EWSD_ID = this.currentItem.CARD_EWSD_ID;
                  itemData.LOAICARD_ID = this.currentItem.LOAICARD_ID;
                  itemData.LOAI_CARD = this.currentItem.TEN_CARD;
                  itemData.NHOMCARD_ID = this.currentItem.NHOMCARD_ID
                  itemData.SHELF_EWSD_ID = this.currentItem.SHELF_EWSD_ID
                  itemData.VITRI = this.currentItem.VITRI_CAM
                  itemData.VITRI_QU = this.currentItem.VITRI_QUYUOC
                }
                let itemID =
                  "CARD_EWSD_" + this.currentItem.CARD_EWSD_ID.toString();
                let itemDonVi = this.dsDonVi.find((x) => x.DONVI_ID == itemID);
                if (itemDonVi != null) {
                  itemDonVi.TEN_DV = this.currentItem.TEN_CARD;
                  this.selectedItem(itemID);
                }
                this.$root.toastSuccess("Cập nhật thành công");
              }
            }
          }        
      } finally {
        this.loading(false);
      }
    },
    Kiemtra_dulieu: function(){

    },
    btnLapCard_Click: async function () {
      this.isShowDiv = true
      this.isCreateNew = true
      this.currentItem.SHELF_EWSD_ID = this.shelf_id;
      this.dsPort = [];
      this.enabledExcel = false
      this.enabledSave = true
      this.dsNhomCard = await this.layDsNhomCard(15)
      if (this.dsNhomCard.length > 0) {
        this.currentItem.NHOMCARD_ID = this.dsNhomCard[0].NHOMCARD_ID
        this.loadLoaiCard();
      }
      this.enabledDelete = false

    },
    btnXoa_Click: async function () {
      this.loading(true);
      try {
        if (
          !(
            this.currentItem.CARD_EWSD_ID == undefined ||
            this.currentItem.CARD_EWSD_ID == 0
          )
        ) {
          this.$bvModal.msgBoxConfirm('Bạn chắc chắn muốn xóa?', {
                size: 'sm',
                okTitle: 'Đồng ý',
                cancelTitle: 'Hủy',
            }).then(async v => {
                if(v) {
                    let checkDel = await this.kiemtraXoa(this.currentItem.CARD_EWSD_ID)
                    if (checkDel[0].KQ > 0){
                      this.$root.toastError("Card này đã có port được ánh xạ logic. Không thể xóa !");
                    }
                    let rs = await this.xoa(this.currentItem.CARD_EWSD_ID);
                    if (rs.result) {
                      this.dsCardEwsdFull = this.dsCardEwsdFull.filter(
                        (x) => x.CARD_EWSD_ID != this.currentItem.CARD_EWSD_ID
                      );
                      let itemID =
                        "CARD_" + this.currentItem.CARD_EWSD_ID.toString();
                      this.dsDonVi = this.dsDonVi.filter((x) => x.DONVI_ID != itemID);
                      this.currentItem = {};
                      this.$root.toastSuccess("Xóa thành công");
                    }
                    this.dsPort = [];
                    this.enabledExcel = false
                  } else {
                    this.$root.toastError("Bạn chưa chọn thiết bị");
                  }
                }).catch(err => {
                    this.$toast.toastError('Xóa thất bại');
                })
                
        }
      } finally {
        this.loading(false);
      }
    },
    Kiemtra_dulieu: function(){
      if (this.currentItem.NHOMCARD_ID === null || this.currentItem.NHOMCARD_ID === -1)
      {
          this.$root.toastError("Chưa chọn nhóm card !");
          this.$refs.cboNhomCard.focus()
          this.isError=true
          return false;
      }
      if (this.currentItem.LOAICARD_ID === null || this.currentItem.LOAICARD_ID === -1)
      {
          this.$root.toastError("Chưa chọn loại card !");
          this.$refs.cboLoaiCard.focus()
          this.isError=true
          return false;
      }
      return true;
    },
    btnExcel_Click: function(){
      try
      {
        if (this.dsPort.length === 0)
        {
          this.$toast.error("Không có dữ liệu để xuất Excel !")
          return
        }
        this.dataExcelEx = Object.values(this.dsPort)
        document.getElementById("exportexcel").click();
      }
      catch (ex)
      {
        this.$toast.error("Có lỗi trong quá trình xuất file Excel !" +ex)
      }
    },
    btnDoiTT_Click: function(){
      this.capnhatPort()
    },
    changeLoaiCard: function(){
      let itemData = this.dsLoaiCard.find((x) => x.LOAICARD_ID == this.currentItem.LOAICARD_ID);
      this.currentItem.TEN_CARD = itemData.LOAI_CARD
      console.log('this.currentItem ',this.currentItem)
    },
    loadLoaiCard: async function(){
      if(this.currentItem.NHOMCARD_ID > 0){
        this.dsLoaiCard = await this.layDsLoaiCard(this.currentItem.NHOMCARD_ID, 15)
        if (this.dsLoaiCard.length > 0){
          this.currentItem.LOAICARD_ID = this.dsLoaiCard[0].LOAICARD_ID
          this.currentItem.TEN_CARD = this.dsLoaiCard[0].LOAI_CARD
        }
        console.log('this.currentItem ',this.currentItem)
      }
    },
    selectedItemsPort: async function(data){
      if (data.length > 0){
        this.enabledDoiTT = true
        this.enabledTrangThai = false
      }
      this.dsSelectedPort = []
      let dsSelected = []
      dsSelected = [...this.dsPort]
      for(let i = 0; i< data.length; i++){
        let row = data[i]
        const check = (obj => obj.NE === row)
        if (dsSelected.some(check)){
            //Find index of specific object using findIndex method.    
            let objIndex = dsSelected.findIndex(check);
            //Update object's name property.
            dsSelected[objIndex].CHON = "1"
        }
      }
      var filtered = dsSelected.filter(function(el) { return el.CHON != "0"; })
      this.dsSelectedPort = filtered
    },
    capnhatPort: async function(){
      try
      {
        this.loading(true)
        if (this.dsSelectedPort.length == 0)
        {
            this.$root.toastError("Bạn chưa chọn port.");
            return;
        }
        
        let tbkq = "";
        for (let i = 0; i < this.dsSelectedPort.length; i++)
        {
          let vnevl_id = this.dsSelectedPort[i].NEVL_ID
          console.log('vnevl_id ',vnevl_id)
          let kq = await this.$root.context.post(
            `/web-ecms/thietbichuyenmach/capnhat-trangthai-port?nevl_id=${vnevl_id}&tt_port_id=${this.currentItem.TT_PORT}`
          );
          
          if (kq.error_code === 'BSS-00000000')
          {
            if (kq.data !== "OK")
              tbkq += kq.message_detail;
          }
        }
        if (tbkq === "")
        {
          this.$root.toastSuccess("Cập nhật trạng thái port thành công !");
        }
        else
        {
          this.$root.toastError(tbkq);
        }
        await this.HienThiDanhSach();
      }
      catch (ex)
      {
          this.$root.toastError(" " + ex);
      }
      finally {
        this.loading(false)
      }
    },
    HienThiDanhSach: async function() {
      this.dsTTPort = await this.layDsTrangThai()
      this.dsPort = await this.layPortTheoCard(this.card_id);
      let tt_port_id = -1
      if (this.dsPort.length > 0) {
        tt_port_id = this.dsPort[0].TT_PORT_ID
        this.enabledExcel = true
      }
    },
  },
};
</script>
<style>
</style>
