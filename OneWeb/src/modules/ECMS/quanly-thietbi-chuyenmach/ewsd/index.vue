<template src="./index.template.html"></template>
<script>
import { required } from "vuelidate/lib/validators";
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
      currentItem: {
        TURACK_ID: null,
        LOAI_EWSD_ID: null,
        HOST_ID: null,
        EWSD_ID: null
      },
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
      },
      toql_id: null
    };
  },
  computed: {
    tenShelfEwsdInvalidStatus: function () {
      if (this.currentItem.EWSD_ID != undefined) {
        return this.$v.currentItem.TEN_EWSD.$invalid;
      } else return false;
    },
    tuRackInvalidStatus: function () {
      if (this.currentItem.EWSD_ID != undefined) {
        return this.$v.currentItem.TURACK_ID.$invalid;
      } else return false;
    },
    loaiThietbiInvalidStatus: function () {
      if (this.currentItem.EWSD_ID != undefined) {
        return this.$v.currentItem.LOAI_EWSD_ID.$invalid;
      } else return false;
    },
    hostInvalidStatus: function () {
      if (this.currentItem.EWSD_ID != undefined) {
        return this.$v.currentItem.HOST_ID.$invalid;
      } else return false;
    },
  },
  watch: {},
  validations: {
    currentItem: {
      TEN_EWSD: {
        required,
      },
      // TEN_EWSD: {
      //   required,
      // },
      // TEN_EWSD: {
      //   required,
      // },
      // TEN_EWSD: {
      //   required,
      // }
    
    },
  },
  created: async function () {
    
  },
  methods: {
    bindData: async function (item) {
      let itemData = await this.layEwsdTheoId(item.itemID);
      if (itemData != null) {
        this.currentItem = {
          EWSD_ID: itemData.EWSD_ID,
          TURACK_ID: itemData.TURACK_ID,
          TEN_EWSD: itemData.TEN_EWSD,
          LOAI_EWSD_ID: itemData.LOAI_EWSD_ID,
          HOST_ID: itemData.HOST_ID,
          ORG: itemData.ORIG,
        };
      this.dsLoaiEwsd = await this.getDsLoaiEwsd();
      this.dsHost = await this.getDsHost();
      this.dsRack = await this.loadDsTuRack(item.donvi_id);
      console.log('this.currentItem',this.currentItem)
      console.log('item.donvi_id',item.donvi_id)
      console.log('this.dsRack',this.dsRack)
      
        //this.dsCard = await this.getDsCard(itemData.SHELF_EWSD_ID);
      } else this.currentItem = {};
    },
    bindItem: async function (itemData) {
      try {
        this.loading(true)
        if (itemData != null) {
        this.dsRack = await this.getDsTuRack(itemData.DONVI_ID)
        
        this.dsLoaiEwsd = await this.getDsLoaiEwsd();
        this.dsHost = await this.getDsHost();
        this.currentItem.TURACK_ID = itemData.TURACK_ID;
        
      } else this.currentItem = {};
      } catch (error) {
        this.$root.toastError("Có lỗi xảy ra trong quá trình tải dữ liệu");
      }
      finally {
        this.loading(false)
      }
      
    },
    loadDsTuRack: async function() {
        try {
            var rs = await this.$root.context.get(`/web-ecms/danhmuc/turack/chitiet?turack_id=${this.currentItem.TURACK_ID}`);
            if(rs.data) {
              var rs = await this.$root.context.post('/web-ecms/danhmuc/turack/ds-turack-theo-donvi-va-kieutu', {
                  kieutu: 1,
                  donvi_id: rs.data.DONVI_ID
              });
              return rs.data;
            }
            return []
        } catch (error) {
            
        }
    },
    getDsLoaiEwsd: async function(){
      let result = await this.$root.context.get(
        "/web-ecms/thietbichuyenmach/lay-ds-loai-ewsd"
      );
      return result.data;
    },
    layEwsdTheoId: async function(id){
      let result = await this.$root.context.post(
        `/web-ecms/thietbichuyenmach/layEwsdTheoId?ewsdId=${id}`
      );
      return result.data;
    },
    getDsHost: async function(){
      let result = await this.$root.context.get(
        "/web-ecms/thietbichuyenmach/lay-ds-host",{loaidv_id: 16}
      );
      return result.data;
    },
    formNhapMoi: function () {
        this.$v.$reset()
      if (this.currentItem.TURACK_ID != undefined) {
        let rackID = this.currentItem.TURACK_ID;
        this.currentItem.EWSD_ID =  0;
        this.currentItem.TURACK_ID = rackID;
        this.currentItem.TEN_EWSD = '';
        if (this.dsLoaiEwsd.length > 0) {
          this.currentItem.LOAI_EWSD_ID = this.dsLoaiEwsd[0].LOAI_EWSD_ID;
        }
        else {
          this.currentItem.LOAI_EWSD_ID = 0;
        }
        if (this.dsHost.length > 0) {
          this.currentItem.HOST_ID = this.dsHost[0].DONVI_ID;
        }
        else {
          this.currentItem.HOST_ID = 0;
        }
        this.currentItem.ORG = ''
      } else {
        this.$root.toastError("Bạn chưa chọn thiết bị");
      }
    },
    formGhiLai: async function () {
      let result = false;
      if (this.currentItem.TURACK_ID != undefined) {
        this.$v.$touch();
        if (this.$v.$invalid) {
          let msg = "";
          if (this.$v.currentItem.TEN_EWSD.$invalid) {
            msg += "Tên Ewsd không được để trống";
          }
          this.$root.toastError(msg);
        } else {
          // console.log('this.currentItem from ewsd ghilai',this.currentItem)
          if (this.currentItem.EWSD_ID == 0) {
            let rs = await this.themEwsd(this.currentItem);
            if (rs != null) {
              result = true;
              this.currentItem.EWSD_ID = rs.EWSD_ID;
              this.$root.toastSuccess("Thêm mới thành công");
            }
          } else {
            let rs = await this.capNhatEwsd(this.currentItem);
            if (rs.result) {
              result = true;
              this.$root.toastSuccess("Cập nhật thành công");
            }
          }
        }
      } else {
        this.$root.toastError("Bạn chưa chọn thiết bị");
      }
      return result;
    },
    formHuy: function () {
        this.$v.$reset()
      if (this.currentItem.TURACK_ID != undefined) {
        this.currentItem = {};
        //this.dsCard = [];
      } else {
        this.$root.toastError("Bạn chưa chọn thiết bị");
      }
    },
    formXoa: async function () {
      let kq = false;
      if (
        !(
          this.currentItem.EWSD_ID == undefined ||
          this.currentItem.EWSD_ID == 0
        )
      ) {
        let check = await this.checkEwsd(this.currentItem.EWSD_ID)
        if(check.result){
          let rs = await this.xoaEwsd(this.currentItem.EWSD_ID);
          if (rs.result) {   
            kq = true;                  
            this.$root.toastSuccess("Xóa thành công");
          }
        }
        else {
          this.$root.toastSuccess("Ewsd này đang có shelf, không thể xóa !");
        }
      } else {
        this.$root.toastError("Bạn chưa chọn thiết bị");
      }
      return kq;
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
      let result = await this.$root.context.post('/web-ecms/danhmuc/turack/ds-turack-theo-donvi-va-kieutu', {
                    kieutu: 1,
                    donvi_id: donvi_id
                });
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
          loai_ewsd_id: itemData.LOAI_EWSD_ID,
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
          loai_ewsd_id: itemData.LOAI_EWSD_ID,
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
      let kq = false;
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
                      let check = await this.checkEwsd(this.currentItem.EWSD_ID)
                      if(check.result){
                        let rs = await this.xoaEwsd(this.currentItem.EWSD_ID);
                        if (rs.result) {   
                          kq = true;                  
                          this.$root.toastSuccess("Xóa thành công");
                        }
                      }
                      else {
                        this.$root.toastError("Ewsd này đang có shelf, không thể xóa !");
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
        return kq;
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
