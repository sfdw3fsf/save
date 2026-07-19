<template src="./index.template.html"></template>
<script>
import CaySoDoThietBi from "../CaySoDoThietBiV2/index.vue";
import ThietBi from "../CaySoDoThietBi/ThietBi";
import FormTuRack from "./turack";
import FormShelf from "./shelf";
import FormCard from "./card";
import FormEWSD from "./ewsd";
import ChuyenTram from "./ewsd/ChuyenTram/Modal";
const ACTION_VIEW = "VIEW";
const ACTION_NEW = "NEW";
export default {
  components: {
    CaySoDoThietBi,
    FormTuRack,
    FormShelf,
    FormCard,
    FormEWSD,
    ChuyenTram,
  },
  data: function () {
    return {
      currentItem: {},
      actionItem: {},
      parentItemFlag: null,
      kieuTu: 1,
      donvi_id: 0,
      dataSend: null,
      ewsd_id: -1,
      turack_id:0,
      tsbtnNhapMoi:{Enabled:false},
      tsbtnGhiLai:{Enabled:false},
      tsbtnXoa:{Enabled:false},
      tsbtnHuyBo:{Enabled:false},
 
    };
  },
  computed: {
    showBtnNhapMoi: function () {
      return !(this.actionItem.itemType == ThietBi.PRE_EWSD_SHELF_SLOT || this.actionItem.itemType == ThietBi.PRE_EWSD_SHELF_CARD)
    },
    showBtnGhiLai: function () {
      return !(this.actionItem.itemType == ThietBi.PRE_EWSD_SHELF_CARD && this.actionItem.itemID != 0)
    },
    showBtnHuyBo: function () {
      return !(this.actionItem.itemType == ThietBi.PRE_EWSD_SHELF_SLOT || this.actionItem.itemType == ThietBi.PRE_EWSD_SHELF_CARD)
    },
    showBtnXoa: function () {
      return this.actionItem.itemType != ThietBi.PRE_EWSD_SHELF_SLOT && !(this.actionItem.itemType == ThietBi.PRE_EWSD_SHELF_CARD && this.actionItem.itemID == 0)
    },
    showBtnChuyenTram: function () {
      return this.actionItem.itemType == ThietBi.PRE_EWSD || (this.actionItem.itemType == ThietBi.PRE_EWSD_SHELF && this.actionItem.itemID == 0);
    },
    showBtnThemTB: function () {
      return (
        this.actionItem.itemType == ThietBi.PRE_TURACK &&
        this.actionItem.itemID > 0
      );
    },
    showBtnLapCard: function () {
      return (
       // (this.actionItem.itemType == ThietBi.PRE_EWSD_SHELF || 
        this.actionItem.itemType == ThietBi.PRE_EWSD_SHELF_SLOT) || ( this.actionItem.itemType == ThietBi.PRE_EWSD_SHELF_CARD && this.currentItem.itemID == 0)
        // &&  this.actionItem.itemID > 0
     // );
    },
    getTuRack_id: function(){
     return true,this.currentItem.itemAttributes.TURACK
    },
    showBtnThemRack: function () {
      return this.actionItem.itemType == ThietBi.PRE_DONVI;
    },
    showBtnThemShelf: function () {
      return this.actionItem.itemType == ThietBi.PRE_EWSD || (this.actionItem.itemType == ThietBi.PRE_EWSD_SHELF && this.actionItem.itemID == 0);
    },
    enabledSave: function () {
      if (!this.checkIsNull(this.actionItem.itemType))
        return this.actionItem.itemType != ThietBi.PRE_DONVI;
      else return false;
    },
    enabledNew: function () {
      if (!this.checkIsNull(this.actionItem.itemType))
        return !(
          this.actionItem.itemType == ThietBi.PRE_DONVI ||
          this.actionItem.action == ACTION_NEW
        );
      else return false;
    },
    enabledDelete: function () {
      if (!this.checkIsNull(this.actionItem.itemType))
        return !(
          this.actionItem.itemType == ThietBi.PRE_DONVI ||
          this.actionItem.itemID == 0
        );
      else return false;
    },
    showEmpty: function () {
      return (
        this.actionItem.itemType == undefined ||
        this.actionItem.itemType == null ||
        this.actionItem.itemType == ThietBi.PRE_DONVI
      );
    },
    showTuRack: function () {
      return this.actionItem.itemType == ThietBi.PRE_TURACK;
    },
    showShelf: function () {
      return this.actionItem.itemType == ThietBi.PRE_EWSD_SHELF;
    },
    showEwsd: function () {
      return this.actionItem.itemType == ThietBi.PRE_EWSD;
    },
    showEwsdCard: function () {
      return (
        this.actionItem.itemType == ThietBi.PRE_EWSD_SHELF_CARD ||
        this.actionItem.itemType == ThietBi.PRE_EWSD_SHELF_SLOT
      );
    },
  },
  mounted: async function () {
    this.$root.showLoading(true);
    try {
      await this.$refs.caySoDoThietBi.loadNodes();
      //TURACK
      await this.$refs.formTuRack.bindTTVT();
      await this.$refs.formTuRack.bindLoaiTu();
      //EWSD
      // await this.$refs.formEwsd.bindOther();
    } finally {
      this.$root.showLoading(false);
    }
  },
  methods: {
    caySoDoThietBi_nodeSelected: async function (args) {
      this.parentItemFlag = null;
      this.$root.showLoading(true);
      try {
        let level =  args.node.ariaLevel
        if (level == "1" || level == "2") {
          this.SetButton()
          this.currentItem = {};
          this.setActionItem();
          return;
        }
        this.currentItem = args.itemData;
        this.SetButton(3);
        this.setActionItem(ACTION_VIEW);
        await this.bindDetailNodeSelected();
      } finally {
        this.$root.showLoading(false);
      }
    },
    refreshTreeFromCurrentItem: async function (selected, tuRackID = this.currentItem.itemAttributes.TURACK) {
      if (this.currentItem.itemType == ThietBi.PRE_TURACK) {
        await this.$refs.caySoDoThietBi.refreshTreeFromDonVi(
          this.currentItem.itemParentID,
          this.currentItem.itemType,
          this.currentItem.itemID,
          this.currentItem.itemID
        );
      } else {
        await this.refreshTree(
          this.currentItem.itemType,
          this.currentItem.itemID,
          selected,
          tuRackID
        );
      }
    },
    refreshTree: async function (type, id, selected, tuRackID) {
      await this.$refs.caySoDoThietBi.refreshTreeFromNodeItem(
        type,
        id,
        selected,
        tuRackID
      );
    },   
    bindDetailNodeSelected: async function () {
      this.ShowMenu(true,true,true,true)
      switch (this.currentItem.itemType) {
        case ThietBi.PRE_DONVI:
          break;
        case ThietBi.PRE_TURACK:
          await this.$refs.formTuRack.bindData(this.currentItem.itemID);
          break;
        case ThietBi.PRE_EWSD_SHELF:
          await this.$refs.formShelf.bindData(this.currentItem.itemID);
          break;
        case ThietBi.PRE_EWSD_SHELF_CARD:
          await this.$refs.formCard.bindData({
           shelf_ewsd_id:this.currentItem.itemParentID,
           card_ewsd_id: this.currentItem.itemID,
          });
          break;
        case ThietBi.PRE_EWSD_SHELF_SLOT:
          this.ShowMenu(false,true,false,false)
          await this.$refs.formCard.bindData({
           shelf_ewsd_id:this.currentItem.itemID,
           card_ewsd_id: 0,
          });
          break;
        case ThietBi.PRE_EWSD:
          await this.$refs.formEwsd.bindData(this.currentItem);
          break;
        default:
          this.currentItem = {};
          break;
      }
    },
    setActionItem: function (action) {
      this.actionItem = {
        itemID: this.currentItem.itemID,
        itemType: this.currentItem.itemType,
        itemParentID: this.currentItem.itemParentID,
        itemParentType: this.currentItem.itemParentType,
        action: action,
      };
    },
    btnNhapMoi_Click: async function () {
      this.currentItem.itemID = 0;
      this.setActionItem(ACTION_NEW);
      this.SetButton(1);
      if (this.currentItem.itemType == ThietBi.PRE_TURACK) {
        this.$refs.formTuRack.formNhapMoi();
      } else if (this.currentItem.itemType == ThietBi.PRE_EWSD_SHELF) {
        this.$refs.formShelf.formNhapMoi();
      } else if (this.currentItem.itemType == ThietBi.PRE_EWSD) {
        this.$refs.formEwsd.formNhapMoi();
      } else if (this.currentItem.itemType == ThietBi.PRE_EWSD_SHELF_CARD) {
        this.$refs.formCard.formNhapMoi();
      }
    },
    btnGhiLai_Click: async function () {
      this.parentItemFlag = null;
      this.$root.showLoading(true);
      try {
        if (this.currentItem.itemType == ThietBi.PRE_TURACK) {
          let rs = await this.$refs.formTuRack.formGhiLai();
          if (rs == true) {
            this.currentItem.itemID =
              this.$refs.formTuRack.currentItem.TURACK_ID;
            await this.refreshTreeFromCurrentItem(true);
          }
        } else if (this.currentItem.itemType == ThietBi.PRE_EWSD_SHELF) {
          let rs = await this.$refs.formShelf.formGhiLai();
          if (rs == true) {
            this.currentItem.itemID =
              this.$refs.formShelf.currentItem.SHELF_EWSD_ID;
            await this.refreshTreeFromCurrentItem(true);
          }
        } else if (this.currentItem.itemType == ThietBi.PRE_EWSD) {
          let rs = await this.$refs.formEwsd.formGhiLai();
          if (rs == true) {
            this.currentItem.itemID = this.$refs.formEwsd.currentItem.EWSD_ID;
             await this.refreshTree(ThietBi.PRE_EWSD,this.currentItem.itemID,true,this.$refs.formEwsd.currentItem.TURACK_ID)
          }
        } else if (
          this.currentItem.itemType == ThietBi.PRE_EWSD_SHELF_CARD ||
          this.currentItem.itemType == ThietBi.PRE_EWSD_SHELF_SLOT
        ) {
          let rs = await this.$refs.formCard.formGhiLai();
          if (rs == true) {
            this.currentItem.itemID =
                 this.$refs.formCard.currentItem.CARD_EWSD_ID;
              await this.refreshTree(ThietBi.PRE_EWSD_SHELF_CARD,this.currentItem.itemID,true,this.getTuRack_id)        
          }
        }
         this.SetButton(3);
        this.setActionItem(ACTION_VIEW);
      } catch(e){
        this.$root.toastError(e.response.data.message_detail ? e.response.data.message_detail : "Thêm mới thất bại");
      }    
      finally {
        this.$root.showLoading(false);
      }
    },
    btnHuy_Click: async function () {
      this.currentItem.itemID = this.currentItem.itemAttributes.id;
      this.currentItem.itemType = this.currentItem.itemAttributes.type;
      this.setActionItem(ACTION_VIEW);
       this.SetButton(2);
      await this.bindDetailNodeSelected();
    },
    btnXoa_Click: async function () {
      this.parentItemFlag = null;
      this.$root.showLoading(true);
      try {
        this.$bvModal
          .msgBoxConfirm("Bạn chắc chắn muốn xóa?", {
            size: "sm",
            okTitle: "Đồng ý",
            cancelTitle: "Hủy",
            centered: true,
          })
          .then(async (v) => {
            if (v) {
              await this.xoaThietBi();
            }
          });
      } finally {
        
        this.$root.showLoading(false);
      }
    },
    xoaThietBi: async function () {
      let rs = false;
      if (this.currentItem.itemType == ThietBi.PRE_TURACK) {
        rs = await this.$refs.formTuRack.formXoa();
      } else if (this.currentItem.itemType == ThietBi.PRE_EWSD_SHELF) {
        rs = await this.$refs.formShelf.formXoa();
      } else if (this.currentItem.itemType == ThietBi.PRE_EWSD) {
        rs = await this.$refs.formEwsd.formXoa();
      } else if (this.currentItem.itemType == ThietBi.PRE_EWSD_SHELF_CARD) {
        rs = await this.$refs.formCard.formXoa();
      }
      if (rs == true) {
         this.SetButton(0);
        if (this.currentItem)
          await this.refreshTree(
            this.currentItem.itemParentType,
            this.currentItem.itemParentID,
            true,
            this.currentItem.itemAttributes.TURACK
          );
      }
    },
    btnThemShelfClick: async function () {
      this.currentItem.itemID = 0;
      this.currentItem.itemType = ThietBi.PRE_EWSD_SHELF;
      this.currentItem.itemParentID = this.currentItem.itemAttributes.id;
      this.currentItem.itemParentType = this.currentItem.itemAttributes.type;
      this.setActionItem(ACTION_VIEW);
      this.SetButton(1);
      let shelf = {
        SHELF_EWSD_ID: 0,
        EWSD_ID: this.currentItem.itemParentID,
      };
      await this.$refs.formShelf.bindItem(shelf);
      this.$refs.formShelf.formNhapMoi();
    },
    btnThemRack_Click: async function () {
      this.currentItem.itemID = 0;
      this.currentItem.itemType = ThietBi.PRE_TURACK;
      this.currentItem.itemParentID = this.currentItem.itemAttributes.id;
      this.currentItem.itemParentType = this.currentItem.itemAttributes.type;
      this.setActionItem(ACTION_VIEW);
      this.SetButton(1);
      let tuRack = {
        TURACK_ID: 0,
        DONVI_ID: this.currentItem.itemParentID,
      };
      await this.$refs.formTuRack.bindItem(tuRack);
      this.$refs.formTuRack.formNhapMoi();
    },
    btnThemTB_Click: async function () {
      this.parentItemFlag = {
        itemID: this.currentItem.itemID,
        itemType: this.currentItem.itemType,
        action: this.currentItem.action,
        idParent: this.currentItem.itemParentID
      };
      this.SetButton(1);
      this.currentItem = {
        itemID: 0,
        itemType: ThietBi.PRE_EWSD,
        action: ACTION_NEW,
      };
      this.actionItem.itemType = ThietBi.PRE_EWSD;
      let ewsd = {
        TURACK_ID: this.parentItemFlag.itemID,
        DONVI_ID: this.parentItemFlag.idParent,
      };
      await this.$refs.formEwsd.bindItem(ewsd);
      this.$refs.formEwsd.formNhapMoi();
    },
    btnThemTBTemp_Click: async function () {
      this.$root.toastSuccess("Chức năng này chưa thực hiện ở giai đoạn này !");
    },
    btnLapCard_Click: async function () {
      this.parentItemFlag = {
        itemID: this.currentItem.itemID,
        itemType: this.currentItem.itemType,
        action: this.currentItem.action,
      };
      this.currentItem = {
        itemID: 0,
        itemType: ThietBi.PRE_EWSD_SHELF_CARD,
        action: ACTION_NEW,
      };
      this.actionItem.itemID = 0
      this.actionItem.itemType = ThietBi.PRE_EWSD_SHELF_CARD;
      let shelf_card = {
        shelf_ewsd_id:this.parentItemFlag.itemID,
        card_ewsd_id: 0,
      };
      await this.$refs.formCard.bindData(shelf_card);
      // this.$refs.formCard.formNhapMoi();
    },
    btnChuyenTram_Click: async function () {
      this.dataSend={
        dslam_id:this.currentItem.itemID,
        type:this.currentItem.itemType,
      }
      this.$bvModal.show("popupChuyenTram");
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
    chuyenTramDone: async function (data) {
      this.$root.showLoading(true);
      let arrTuRack = data.turack_ids;
      let getTuRack = arrTuRack.find(v => v == this.getTuRack_id);
      await this.refreshTreeFromCurrentItem(false, this.getTuRack_id);
      // this.currentItem.itemType = ThietBi.PRE_TURACK
      // this.currentItem.itemID = this.getTuRack_id
      await this.$refs.caySoDoThietBi.refreshTreeFromDonVi(
        data.tramvt_id,
        this.currentItem.itemType,
        this.currentItem.itemID,
        this.currentItem.itemID
      );
      await this.refreshTreeFromCurrentItem(true, getTuRack ? getTuRack : arrTuRack[0]);
      // let temp = await this.getDsShelfEwsdTheoEwsd(this.ewsd_id);
      // let soSlot = -1;
      // if (temp != []) soSlot = temp.length;
      // else soSlot = 0;
      // let arrTuRack = data.turack_ids;
      // this.$refs.caySoDoThietBi.deleteNode(
      //   this.currentItem.itemID,
      //   this.currentItem.itemType
      // );
      // arrTuRack.forEach((element) => {
      //   this.$refs.caySoDoThietBi.addNode(
      //     this.currentItem.itemID,
      //     this.currentItem.itemType,
      //     this.currentItem.text,
      //     element,
      //     ThietBi.PRE_TURACK,
      //     true,
      //     {
      //       soSlot: soSlot,
      //       slotType: ThietBi.PRE_EWSD_SHELF,
      //     }
      //   );
      // });
      // $bvModal.hide()
      this.$bvModal.hide("popupChuyenTram");
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
     SetButton : function(kieu)
        {
            //Set button cho form cha (để thực hiện các thao tác trong form con.
            this.tsbtnNhapMoi.Enabled = false;
            this.tsbtnGhiLai.Enabled = false;
            this.tsbtnXoa.Enabled = false;
            this.tsbtnHuyBo.Enabled = false;
            if (kieu == -1)//Bat dau
            {
                this.tsbtnGhiLai.Enabled = true;
                this.tsbtnHuyBo.Enabled = true;
            }
            if (kieu == 0)//Bat dau
            {
                this.tsbtnNhapMoi.Enabled = true;
            }
            if (kieu == 1)//Them moi
            {
                this.tsbtnGhiLai.Enabled = true;
                this.tsbtnHuyBo.Enabled = true;

            }
            if (kieu == 2)//Huy
            {
                this.tsbtnNhapMoi.Enabled = true;
                this.tsbtnXoa.Enabled = true;
                this.tsbtnGhiLai.Enabled = true;
            }
            if (kieu == 3)//Edit
            {
                this.tsbtnNhapMoi.Enabled = true;
                this.tsbtnXoa.Enabled = true;
                this.tsbtnGhiLai.Enabled = true;
                this.tsbtnHuyBo.Enabled = true;
            }
    },
    ShowMenu:function(creta,save,del,cancel){
      this.tsbtnNhapMoi.Visible = creta;
      this.tsbtnGhiLai.Visible = save;
      this.tsbtnXoa.Visible = del;
      this.tsbtnHuyBo.Visible = cancel;
    },
    closeForm: function() {
      this.actionItem = {}
    }
  },
};
</script>
<style>
.disable-btn {
  pointer-events: none;
  cursor: default;
  color: #6c757d !important;
}
</style>