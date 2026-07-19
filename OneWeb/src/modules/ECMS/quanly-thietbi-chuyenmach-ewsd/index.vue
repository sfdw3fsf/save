<template src="./index.template.html"></template>
<script>
import { isActionPrevent } from '@syncfusion/ej2-grids';
import { required, numeric, minValue } from "vuelidate/lib/validators";
import ModalChuyenTram from './ChuyenTram/Modal'
export default {
  components: {ModalChuyenTram},
  data: function () {
    return {
      currentNode: null,
      txtSearch: "",
      dsDonVi: [],
      dsEwsdFull: [],
      dsRack: [],
      dsHost: [],
      dsLoaiEwsd: [],
      dsCard: [],
      currentItem: {},
      isShowDiv: false,
      isCreateNew: false,
      isSuccess: false,
      isEnableButton: false,
      enabledSave: false,
      enabledNew: false,
      enabledCancel: false,
      enabledDelete: false,
      enabledChuyenTram: false,
      enabledThemShelf: false,
      enabledThemTB: false,
      enabledThemTBTemp: false,
      modalShow: false,
      dataSend : {
        dslam_id: -1,
        type: ''
      }

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
    disabledClass: function(){
    return !this.enabledChuyenTram ? "disabled" : ""
  }
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
    this.setButtonState(false, false, false, false, false, false, false)
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
    getDsLoaiEwsd: async function(){
      let result = await this.$root.context.get(
        "/web-ecms/thietbichuyenmach/lay-ds-loai-ewsd"
      );
      return result.data;
    },
    getDsHost: async function(){
      let result = await this.$root.context.get(
        "/web-ecms/thietbichuyenmach/lay-ds-host",{loaidv_id: 16}
      );
      return result.data;
    },
    loadDonVi: async function () {
      let ds = await this.getDsDonVi();
      if (!(ds == null || ds.length == 0)) {
        this.dsShelfEwsdFull = await this.getDsShelfEwsdFull();
        this.dsEwsdFull = await this.getDsEwsdFull();
        let tuRacks = this.getDsTuRackFromList(this.dsShelfEwsdFull);
        if (!(tuRacks == null || tuRacks.length == 0)) {
          for (let i = 0; i < tuRacks.length; i++) {
            const element = tuRacks[i];
            ds.push(element);
          }
        }
        let ewsds = this.getDsEwsdFromList(this.dsEwsdFull);
        if (!(ewsds == null || ewsds.length == 0)) {
          for (let i = 0; i < ewsds.length; i++) {
            const element = ewsds[i];
            ds.push(element);
          }
        }
        // let shelfEwsds = this.getDsShelfEwsdFromList(this.dsShelfEwsdFull);
        // if (!(shelfEwsds == null || shelfEwsds.length == 0)) {
        //   for (let i = 0; i < shelfEwsds.length; i++) {
        //     const element = shelfEwsds[i];
        //     ds.push(element);
        //   }
        // }
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
        console.log('itemitemitem ',item)
        if (item.ITEM_TYPE == "EWSD") {
          this.isShowDiv = true;
          this.isCreateNew = false;
          this.$root.showLoading(true);
          this.setButtonState(true, true, true, true, true, false, false)
          try {
            let itemID = item.DONVI_ID.replace(item.ITEM_TYPE + "_", "");
            console.log('itemID ',itemID)
            this.dsRack = await this.getDsTuRack(itemID);
            this.dsLoaiEwsd = await this.getDsLoaiEwsd();
            this.dsHost = await this.getDsHost();
            let itemData = this.dsEwsdFull.find(
              (x) => x.EWSD_ID == itemID
            );
            
            console.log('itemData ',itemData)
            if (itemData != null) {
              this.dataSend = {
                dslam_id: itemData.EWSD_ID,
                type: item.ITEM_TYPE
              }
              this.currentItem = {
                EWSD_ID: itemData.EWSD_ID,
                TURACK_ID: itemData.TURACK_ID,
                TEN_EWSD: itemData.TEN_EWSD,
                THIETBI_ID: itemData.LOAI_EWSD_ID,
                HOST_ID: itemData.HOST_ID,
                ORG: itemData.ORIG,
                
              };
            }
          } finally {
            this.$root.showLoading(false);
          }
        }
        else if (item.ITEM_TYPE === 'TURACK'){
          this.setButtonState(false, false, false, false, false, false, true, true)
          this.isShowDiv = false
          this.currentNode = item.DONVI_ID.replace(item.ITEM_TYPE + "_", "");;
        }
        else {
          this.setButtonState(false, false, false, false, false, false, false, false)
          this.currentItem = {};
          this.dsCard = [];
        }
        
      } else {
        this.setButtonState(false, false, false, false, false, false, false, false)
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
    getDsTuRack: async function (donvi_id) {
      let result = await this.$root.context.post(
        "/web-ecms/danhmuc/layDsTuRack",{donvi_id:donvi_id, kieu: 1 }
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
    getDsEwsdFull: async function () {
      let result = await this.$root.context.get(
        "/web-ecms/thietbichuyenmach/dsEWSD"
      );
      return result.data;
    },
    xoaEwsd: async function (id) {
      let result = await this.$root.context.post(
        `/web-ecms/thietbichuyenmach/xoaewsd?ewsd_id=${id}`
      );
      return result.data;
    },
    checkEwsd: async function (id) {
      let result = await this.$root.context.post(
        `/web-ecms/thietbichuyenmach/checkewsdhasshelf?ewsd_id=${id}`
      );
      return result.data;
    },
    capNhatEwsd: async function (itemData) {
      let result = await this.$root.context.post(
        "/web-ecms/thietbichuyenmach/capnhatewsd",
        {
          ewsd_id: itemData.EWSD_ID,
          ten_ewsd: itemData.TEN_EWSD,
          loai_ewsd_id: itemData.THIETBI_ID,
          turack_id: itemData.TURACK_ID,
          host_id: itemData.HOST_ID,
          orig: itemData.ORG,
        }
      );
      return result.data;
    },
    themEwsd: async function (itemData) {
        let result = await this.$root.context.post(
        "/web-ecms/thietbichuyenmach/themewsd",
        {
          ten_ewsd: itemData.TEN_EWSD,
          loai_ewsd_id: itemData.THIETBI_ID,
          turack_id: itemData.TURACK_ID,
          host_id: itemData.HOST_ID,
          orig: itemData.ORG,
        }
      );
      return result.data;
    },
    selectedItem: function (donViID) {
      let itemDonVi = this.dsDonVi.find((x) => x.DONVI_ID == donViID);
      console.log('itemDonVi ',itemDonVi)
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
    setButtonState: function(isenabledNew, isenabledSave, isenabledCancel, isenabledDelete, isenabledChuyenTram, isenabledThemShelf, isenabledThemTB, isenabledThemTBTemp){
      this.enabledSave = isenabledSave
      this.enabledNew = isenabledNew
      this.enabledDelete = isenabledDelete
      this.enabledCancel = isenabledCancel
      this.enabledChuyenTram = isenabledChuyenTram
      this.enabledThemShelf = isenabledThemShelf
      this.enabledThemTB = isenabledThemTB
      this.enabledThemTBTemp = isenabledThemTBTemp
    },
    btnThemShelf_Click: function(){
      alert("Phần này sẽ thực hiện sau khi rap code vào chức năng lớn. Kiểu = 1");
    },
    btnXoaThemTB_Click: async function(){
      try {
        this.$root.showLoading(true);
        this.isShowDiv = true
        this.isCreateNew = true
        this.isEnableButton = false
        let itemID = this.currentNode;
        this.dsRack = await this.getDsTuRack(itemID);
        this.dsLoaiEwsd = await this.getDsLoaiEwsd();
        this.dsHost = await this.getDsHost();
        this.currentItem = {
          TURACK_ID: itemID
        };
        this.setButtonState(false, true, true, true, false, false, false, false)
      } catch (ex) {
        this.$root.toastError("Có lỗi khi lấy dữ liệu.");
      }
      finally {
        this.$root.showLoading(false);
      }
      
    },
    btnNhapMoi_Click: function () {
      this.isCreateNew = true
      if (this.currentItem.TURACK_ID != undefined) {
        let tuID = this.currentItem.TURACK_ID;
        let tbID = this.currentItem.THIETBI_ID;
        let hostID = this.currentItem.HOST_ID;
        let org = this.currentItem.ORG;
        this.currentItem = {
          TURACK_ID: tuID,
          TEN_EWSD: '',
          THIETBI_ID: tbID,
          HOST_ID: hostID,
          ORG: org,
        };
        //this.dsCard = [];
      } else {
        this.$root.toastError("Bạn chưa chọn thiết bị");
      }
      
    },
    btnGhiLai_Click: async function () {
      this.$root.showLoading(true);
      try {
        if (this.currentItem.TURACK_ID != undefined) {
          if (this.Kiemtra_dulieu()) {
            if (this.isCreateNew){
              let rs = await this.themEwsd(this.currentItem);
              if (rs != null) {
                this.currentItem.EWSD_ID = rs.EWSD_ID;
                let itemID =
                  "EWSD_" + this.currentItem.EWSD_ID.toString();
                let ewsdData = {
                  DONVI_ID: itemID,
                  TEN_DV: this.currentItem.TEN_EWSD,
                  DONVI_CHA_ID: "TURACK_" + this.currentItem.TURACK_ID.toString(),
                  HasChildren: false,
                  Expanded: false,
                  Selected: false,
                  ITEM_TYPE: "EWSD",
                };
                this.dsDonVi.push(ewsdData);
                this.selectedItem(itemID);
                this.$root.toastSuccess("Thêm mới thành công");
                this.setButtonState(false, true, true, true, true, true, false, false)
              }
            }
            else{
              let rs = await this.capNhatEwsd(this.currentItem);
              if (rs.result) {
                let itemData = this.dsEwsdFull.find(
                  (x) => x.EWSD_ID == this.currentItem.EWSD_ID
                );
                if (itemData != null) {
                  itemData.EWSD_ID = this.currentItem.EWSD_ID;
                  itemData.TURACK_ID = this.currentItem.TURACK_ID;
                  itemData.TEN_EWSD = this.currentItem.TEN_EWSD;
                  itemData.TEN_EWSD = this.currentItem.TEN_EWSD;
                  itemData.HOST_ID = this.currentItem.HOST_ID;
                  itemData.ORIG = this.currentItem.ORIG;
                }
                let itemID =
                  "EWSD_" + this.currentItem.EWSD_ID.toString();

                let itemDonVi = this.dsDonVi.find((x) => x.DONVI_ID == itemID);
                if (itemDonVi != null) {
                  itemDonVi.TEN_DV = this.currentItem.TEN_EWSD;
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
      if (this.currentItem.TURACK_ID != undefined) {
        this.currentItem = {};
      } else {
        this.$root.toastError("Bạn chưa chọn thiết bị");
      }
    },
    btnXoa_Click: async function () {
      this.$root.showLoading(true);
      try {
        if (
          !(
            this.currentItem.EWSD_ID == undefined ||
            this.currentItem.EWSD_ID == 0
          )
        ) {
          this.$bvModal.msgBoxConfirm('Bạn chắc chắn muốn xóa?', {
                size: 'sm',
                okTitle: 'Đồng ý',
                cancelTitle: 'Hủy',
            }).then(async v => {
                if(v) {
                    try {
                        console.log('id: ',this.currentItem)
                        let check = await this.checkEwsd(this.currentItem.EWSD_ID)
                        if(check.result){
                          let rs = await this.xoaEwsd(this.currentItem.EWSD_ID);
                          if (rs.result) {
                            this.dsEwsdFull = this.dsEwsdFull.filter(
                              (x) => x.EWSD_ID != this.currentItem.EWSD_ID
                            );
                            let itemID =
                              "TURACK_" + this.currentItem.TURACK_ID.toString();
                              console.log('itemID ',itemID)
                              console.log('this.dsDonVi ',this.dsDonVi )
                            this.dsDonVi = this.dsDonVi.filter((x) => x.DONVI_ID != itemID);
                            this.selectedItem(itemID);
                            this.currentItem = {};
                      
                            this.$root.toastSuccess("Xóa thành công");

                          }
                        }
                        else {
                             this.$root.toastSuccess("Ewsd này đang có shelf, không thể xóa !");
                          }
                        
                    } catch (error) {
                        this.$root.toastError(error.response.data.message_detail);
                    } finally {
                        this.loading(false);
                    }
                  }
                }).catch(err => {
                    this.$toast.toastError('Xóa thất bại');
                })
          
          //this.dsCard = [];
        } else {
          this.$root.toastError("Bạn chưa chọn thiết bị");
        }
      } finally {
        this.$root.showLoading(false);
      }
    },
    btnChuyenTram_Click: async function(){
      this.$bvModal.show('popupChuyenTram')
    },
    btnXoaThemTBTemp_Click: function(){
      this.$root.toastSuccess("Chức năng này chưa thực hiện ở giai đoạn này !");
    },
    Kiemtra_dulieu: function(){
      if (this.currentItem.TEN_EWSD === '')
      {
          this.$root.toastError("Tên thiết bị không được để trống !");
          this.$refs.txtTenThietBi.focus();
          return false;
      }
      if (this.currentItem.TURACK_ID === -1 || this.currentItem.TURACK_ID === 0 || this.currentItem.TURACK_ID === undefined)
      {
          this.$root.toastError("Chưa chọn tủ rack !");
          this.$refs.cboTuRack.focus();
          return false;
      }
      if (this.currentItem.THIETBI_ID === -1 || this.currentItem.THIETBI_ID === 0 || this.currentItem.THIETBI_ID === undefined)
      {
          this.$root.toastError("Chưa chọn loại thiết bị !");
          this.$refs.cboLoaiThietBi.focus();
          return false;
      }
      if (this.currentItem.HOST_ID === -1 || this.currentItem.HOST_ID === 0 || this.currentItem.HOST_ID === undefined)
      {
          this.$root.toastError("Chưa chọn host !");
          this.$refs.cboHost.focus();
          return false;
      }
      return true;
    },
    chuyenTramDone: function(e){
      console.log(e)
      //this.dsDonVi = this.dsDonVi.filter((x) => x.DONVI_ID != itemID);
      let itemID = 6
      this.selectedItem(itemID);
    }
  },
};
</script>
<style scoped>
.disabled {
  
  color: #0176FF;
  border-left: 1px solid #BAE7FF;opacity: 0.65;
}
</style>
