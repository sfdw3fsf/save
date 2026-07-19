<template src='./index.html'></template>
<script>
import DsLamSwitchGpon from "./DSLAM-Switch-Gpon";
import ThongTinBras from "./ThongTinBras";
import ThongTinPortSwitch from "./ThongTinPortSwitch";
import HuongKetNoi from "./HuongKetNoi";
import EventBus from "@/utils/eventBus";
import ModalChuyenTram from "./ChuyenTram/Modal";
import ModalLoaiDongBo from "./LoaiDongBo/Modal";
import ModalDMDongBo from "./DMDongBo/Modal";
import ModalDMRing from "./DMRing/Modal";
import ModalDDCTruyenDan from "./DDCTruyenDan/Modal";
import TuRack from "./TuRack";
import DsLamFrame from "./DSLAM-Frame";
import DsLamShelf from "./DSLAM-Shelf";
import DsLamCard from "./DSLAM-Card";
import DsLamModule from "./DSLAM-Module";
import ThongTinFrameTD from "./ThongTinFrameTruyenDan";
import ThongTinODF from "./ThongTinODF";
import GponCard from "./GPON-CARD";
import ManeCard from "./MANE-CARD";
import CardTD from "./CardTD";
import ThongTinPortGpon from "./ThongTinPortGpon";
import ManeModule from "./Mane-Module"
import SwitchCard from "./SWITCH-CARD"
import BrasCard from "./Bras-Card"
import CaySoDoThietBi from "../CaySoDoThietBiV2/index.vue";
import ThietBi from "../CaySoDoThietBi/ThietBi";
const ACTION_VIEW = "VIEW";
const ACTION_NEW = "NEW";
export default {
  components: {
    DsLamSwitchGpon,
    ThongTinBras,
    ThongTinPortSwitch,
    HuongKetNoi,
    ModalChuyenTram,
    ModalLoaiDongBo,
    ModalDMDongBo,
    ModalDMRing,
    ModalDDCTruyenDan,
    TuRack,
    DsLamFrame,
    DsLamShelf,
    DsLamModule,
    DsLamCard,
    ThongTinFrameTD,
    ThongTinODF,
    CaySoDoThietBi,
    GponCard,
    ManeCard,
    CardTD,
    ThongTinPortGpon,
    ManeModule,
    SwitchCard,
    BrasCard
  },
  created: function () {},
  data() {
    return {
      enableCheckL2SW: false,
      dataSend: null,
      currentItem: {}, // Sẽ sử dụng để truyền dữ liệu
      kieuTu: 2,
      turack_id: 0,
      view: 0,
      modalThongBao: false,
      thongbao: "",
      dslam_id: 0,
      framedsl_id: 0,
      shelfdsl_id: 0,
      carddsl_id: 0,
      moduledsl_id: 0,
      donvi_id: 0,
      frametd_id: 0,
      odf_id: 0,
      vitriShlefdsl: -1,
      gponcard_id: 0,
      cardtd_id: 0,
      cardmane_id: 0,
      modulemane_id: 0,
      menu: {
        tu_rack: false,
        bras: false,
        shelf: false,
        dslamcard: false,
        dslam: false,
        mane: false,
        frametd: false,
        odf: false,
        gpon: false,
        frame: false,
        dslamRack: false,
        module: false,
        chuyenDslam: false,
        switch: false,
        new: true,
        delete: true,
        cancel: true,
        save: true,
        lapCard: false,
      },
      tsbtnNhapMoi:{Enabled:false},
      tsbtnGhiLai:{Enabled:false},
      tsbtnXoa:{Enabled:false},
      tsbtnHuyBo:{Enabled:false},
    };
  },
  computed: {
    enabledSave: function () {
      return (
        this.currentItem.action == ACTION_NEW ||
        this.currentItem.action == ACTION_VIEW
      );
    },
    enabledNew: function () {
      return this.currentItem.action == ACTION_VIEW;
    },
    enabledDelete: function () {
      return this.currentItem.action == ACTION_VIEW;
    },
    showEmpty: function () {
      return (
        this.currentItem.itemID == undefined &&
        this.currentItem.itemType == undefined
      );
    },
    showTuRack: function () {
      return (
        this.currentItem.itemID != undefined &&
        this.currentItem.nhatram_id &&
        this.currentItem.itemType == ThietBi.PRE_TURACK
      );
    },
    showDsLamSwitchGpon: function () {
      return (
        this.currentItem.itemID != undefined &&
        this.currentItem.turack_id &&
        (this.currentItem.itemType == ThietBi.PRE_DSLAM ||
          this.currentItem.itemType == ThietBi.PRE_GPON ||
          this.currentItem.itemType == ThietBi.PRE_SWITCH ||
          this.currentItem.itemType == ThietBi.PRE_MANE)
      );
    },
    showBras: function () {
      return (
        this.currentItem.itemID != undefined &&
        this.currentItem.itemType == ThietBi.PRE_BRAS
      );
    },
    showPortSwitch: function () {
      return (
        this.currentItem.itemID != undefined &&
        (this.currentItem.itemType == ThietBi.PRE_SWITCH_PORT ||
          this.currentItem.itemType == ThietBi.PRE_SWITCH_CARD_PORT) &&
        !this.currentItem.upLink
      );
    },
    showHKN: function () {
      return (
        this.currentItem.itemID != undefined &&
        (this.currentItem.itemType == ThietBi.PRE_SWITCH_PORT ||
          this.currentItem.itemType == ThietBi.PRE_SWITCH_CARD_PORT ||
          this.currentItem.itemType == ThietBi.PRE_GPON_CARD_PORT ||
          this.currentItem.itemType == ThietBi.PRE_MANE_CARD_PORT ||
          this.currentItem.itemType == ThietBi.PRE_MANE_CARD_MODULE_PORT ||
          this.currentItem.itemType ==
            ThietBi.PRE_DSLAM_FRAME_SHELF_CARD_PORT ||
          this.currentItem.itemType ==
            ThietBi.PRE_DSLAM_FRAME_SHELF_CARD_MODULE_PORT) &&
        this.currentItem.upLink
      );
    },
    showFrameTD: function () {
      return (
        this.currentItem.itemID != undefined &&
        this.currentItem.nhatram_id &&
        this.currentItem.turack_id &&
        this.currentItem.itemType == ThietBi.PRE_FRAME
      );
    },
    showODF: function () {
      return (
        this.currentItem.itemID != undefined &&
        this.currentItem.turack_id != undefined &&
        this.currentItem.itemType == ThietBi.PRE_ODF
      );
    },
    showDsLamFrame: function () {
      return (
        this.currentItem.itemID != undefined &&
        this.currentItem.dslam_id &&
        this.currentItem.itemType == ThietBi.PRE_DSLAM_FRAME
      );
    },
    showDsLamShelf: function () {
      return (
        this.currentItem.itemID != undefined &&
        this.currentItem.framedsl_id &&
        this.currentItem.itemType == ThietBi.PRE_DSLAM_FRAME_SHELF
      );
    },
    showDsLamCard: function () {
      return (
        this.currentItem.itemID != undefined &&
        this.currentItem.shelfdsl_id &&
        this.currentItem.itemType == ThietBi.PRE_DSLAM_FRAME_SHELF_CARD
      );
    },
    showDsLamModule: function () {
      return (
        this.currentItem.itemID != undefined &&
        this.currentItem.carddsl_id &&
        this.currentItem.itemType == ThietBi.PRE_DSLAM_FRAME_SHELF_CARD_MODULE
      );
    },
    showGponCard: function () {
      return (
        this.currentItem.itemType == ThietBi.PRE_GPON_SLOT ||
        this.currentItem.itemType == ThietBi.PRE_GPON_CARD
      );
    },
    showCardTD: function () {
      return (
        this.currentItem.itemType == ThietBi.PRE_FRAME_CARD ||
        this.currentItem.itemType == ThietBi.PRE_FRAME_SLOT
      );
    },
    showPortGpon: function () {
      return (
        this.currentItem.itemID != undefined &&
        this.currentItem.itemType == ThietBi.PRE_GPON_CARD_PORT &&
        !this.currentItem.upLink
      );
    },
    showManeCard: function () {
      return (
        this.currentItem.itemType == ThietBi.PRE_MANE_CARD ||
        this.currentItem.itemType == ThietBi.PRE_MANE_SLOT
      );
    },
    showManeModule: function () {
      return (
        this.currentItem.itemID != undefined &&
        this.currentItem.itemType == ThietBi.PRE_MANE_CARD_MODULE
      );
    },
    showSwitchCard: function () {
      return (
        this.currentItem.itemID != undefined &&
        this.currentItem.itemType == ThietBi.PRE_SWITCH_SLOT || this.currentItem.itemType == ThietBi.PRE_SWITCH_CARD || this.currentItem.itemType == ThietBi.PRE_SWITCH_SLOT_SUB
      );
    },
    showBrasCard: function () {
      return (
        this.currentItem.itemID != undefined &&
        this.currentItem.itemType == ThietBi.PRE_BRAS_CARD
      );
    },
  },
  methods: {
    refreshTreeFromCurrentItem: async function (selected, tuRackID = this.turack_id) {
      console.log(this.currentItem, this.turack_id, tuRackID);
      await this.refreshTree(
        this.currentItem.itemType,
        this.currentItem.itemID,
        selected,
        tuRackID 
      );
    },
    refreshTree: async function (type, id, selected, tuRackID) {
      await this.$refs.caySoDoThietBi.refreshTreeFromNodeItem(
        type,
        id,
        selected,
        tuRackID
      );
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
            }
            if (kieu == 3)//Edit
            {
                this.tsbtnNhapMoi.Enabled = true;
                this.tsbtnXoa.Enabled = true;
                this.tsbtnGhiLai.Enabled = true;
                this.tsbtnHuyBo.Enabled = true;
            }
        },
    //#region  "CRUD"
    btnNhapMoi_Click: async function () {
      this.currentItem.itemID = 0;
      this.currentItem.action = ACTION_NEW;
      this.SetButton(1)
      if (this.currentItem.itemType == ThietBi.PRE_TURACK) {
        this.$refs.formTuRack.themMoi();
      } else if (
        this.currentItem.itemType == ThietBi.PRE_DSLAM ||
        this.currentItem.itemType == ThietBi.PRE_GPON ||
        this.currentItem.itemType == ThietBi.PRE_SWITCH ||
        this.currentItem.itemType == ThietBi.PRE_MANE
      ) {
        this.$refs.formDsLamSwitchGpon.themMoi();
      } else if (this.currentItem.itemType == ThietBi.PRE_BRAS) {
        this.$refs.formBras.formNhapMoi(this.turack_id);
      } else if (this.currentItem.itemType == ThietBi.PRE_FRAME) {
        this.$refs.formFrameTD.themMoi();
      } else if (this.currentItem.itemType == ThietBi.PRE_ODF) {
        this.$refs.formODF.themMoi();
      } else if (this.currentItem.itemType == ThietBi.PRE_DSLAM_FRAME) {
        this.$refs.formDsLamFrame.themMoi();
      } else if (this.currentItem.itemType == ThietBi.PRE_DSLAM_FRAME_SHELF) {
        this.$refs.formDsLamShelf.themMoi();
      } else if (
        this.currentItem.itemType == ThietBi.PRE_DSLAM_FRAME_SHELF_CARD
      ) {
        this.$refs.formDsLamCard.themMoi();
      } else if (
        this.currentItem.itemType == ThietBi.PRE_DSLAM_FRAME_SHELF_CARD_MODULE
      ) {
        this.$refs.formDsLamModule.themMoi();
      } else if (
        this.currentItem.itemType == ThietBi.PRE_GPON_SLOT ||
        this.currentItem.itemType == ThietBi.PRE_GPON_CARD
      ) {
        await this.$refs.formGponCard.themMoi(
          this.currentItem.itemParentID,
          this.currentItem.attributes.slotIndex
        );
      } else if (
        this.currentItem.itemType == ThietBi.PRE_MANE_SLOT ||
        this.currentItem.itemType == ThietBi.PRE_MANE_CARD
      ) {
        await this.$refs.formManeCard.themMoi(
          this.currentItem.itemParentID,
          this.currentItem.attributes.slotIndex
        );
      } else if (
        this.currentItem.itemType == ThietBi.PRE_FRAME_CARD ||
        this.currentItem.itemType == ThietBi.PRE_FRAME_SLOT
      ) {
        this.$refs.formCardTD.themMoi();
      } else if (this.currentItem.itemType == ThietBi.PRE_MANE_CARD_MODULE) {
        this.$refs.formManeModule.themMoi();
      } else if (this.currentItem.itemType == ThietBi.PRE_SWITCH_SLOT || this.currentItem.itemType == ThietBi.PRE_SWITCH_CARD || this.currentItem.itemType == ThietBi.PRE_SWITCH_SLOT_SUB) {
        this.$refs.formSwitchCard.themMoi();
      }
    },
    btnGhiLai_Click: async function () {
      this.isGhiLai = true;
      this.isXoa = false;
      this.modalThongBao = true;
      this.thongbao = "Bạn có muốn cập nhật dữ liệu?";
    },
    ghiLai: async function () {
      try {
        this.$root.showLoading(true);
        if (this.currentItem.itemType == ThietBi.PRE_TURACK) {
          let rs = await this.$refs.formTuRack.ghiLai();
          if (rs) {          
            this.currentItem.itemID = this.$refs.formTuRack.currentItem.turack_id;
            this.turack_id = this.$refs.formTuRack.currentItem.turack_id;
            await this.$refs.caySoDoThietBi.refreshTreeFromDonVi(
              this.currentItem.nhatram_id,
              this.currentItem.itemType,
              this.currentItem.itemID,
              this.currentItem.itemID
            );
            await this.showMenu(ThietBi.PRE_TURACK, 0);
            this.currentItem.action = ACTION_VIEW;
            this.SetButton(3)
          }
        } else if (
          this.currentItem.itemType == ThietBi.PRE_DSLAM ||
          this.currentItem.itemType == ThietBi.PRE_GPON ||
          this.currentItem.itemType == ThietBi.PRE_SWITCH ||
          this.currentItem.itemType == ThietBi.PRE_MANE
        ) {
          let rs = await this.$refs.formDsLamSwitchGpon.ghiLai();
          if (rs) {           
            console.log(this.$refs.formDsLamSwitchGpon.currentItem);
            let getTuRack = this.$refs.formDsLamSwitchGpon.currentItem.turack_id.find(v => v == this.turack_id);
            this.currentItem.itemID = this.$refs.formDsLamSwitchGpon.currentItem.dslam_id;
            this.dslam_id = this.$refs.formDsLamSwitchGpon.currentItem.dslam_id;
            await this.refreshTreeFromCurrentItem(true, getTuRack ? getTuRack : this.$refs.formDsLamSwitchGpon.currentItem.turack_id[0]);
            await this.showMenu(this.currentItem.itemType, 0);
            this.currentItem.action = ACTION_VIEW;
            this.SetButton(3)
          }
        } else if (this.currentItem.itemType == ThietBi.PRE_BRAS) {
          let rs = await this.$refs.formBras.formGhiLai();
          if (rs == true) {          
            this.currentItem.itemID = this.$refs.formBras.currentItem.BRAS_ID;
            await this.refreshTreeFromCurrentItem(true);
            this.currentItem.action = ACTION_VIEW;
            this.SetButton(3)
          }
        } else if (this.currentItem.itemType == ThietBi.PRE_FRAME) {
          let rs = await this.$refs.formFrameTD.ghiLai();
          if (rs) {          
            this.frametd_id = this.$refs.formFrameTD.currentItem.frame_id;
            this.currentItem.itemID = this.$refs.formFrameTD.currentItem.frame_id;
            await this.refreshTreeFromCurrentItem(true);
            await this.showMenu(ThietBi.PRE_FRAME, 0);
            this.currentItem.action = ACTION_VIEW;
            this.SetButton(3)
          }
        } else if (this.currentItem.itemType == ThietBi.PRE_ODF) {
          let rs = await this.$refs.formODF.ghiLai();
          if (rs) {            
            this.odf_id = this.$refs.formODF.currentItem.odf_id;
            this.currentItem.itemID = this.$refs.formODF.currentItem.odf_id;
            await this.refreshTreeFromCurrentItem(true);
            await this.showMenu(ThietBi.PRE_ODF, 0);
            this.currentItem.action = ACTION_VIEW;
            this.SetButton(3)
          }
        } else if (this.currentItem.itemType == ThietBi.PRE_DSLAM_FRAME) {
          let rs = await this.$refs.formDsLamFrame.ghiLai();
          if (rs) {         
            this.currentItem.itemID = this.$refs.formDsLamFrame.currentItem.framedsl_id;
            this.framedsl_id = this.$refs.formDsLamFrame.currentItem.framedsl_id;
            await this.refreshTreeFromCurrentItem(true);
            await this.showMenu(ThietBi.PRE_DSLAM_FRAME, 0);
            this.currentItem.action = ACTION_VIEW;
            this.SetButton(3)
          }
        } else if (this.currentItem.itemType == ThietBi.PRE_DSLAM_FRAME_SHELF) {
          let rs = await this.$refs.formDsLamShelf.ghiLai();
          if (rs) {          
            this.shelfdsl_id = this.$refs.formDsLamShelf.currentItem.shelfdsl_id;
            this.currentItem.itemID = this.$refs.formDsLamShelf.currentItem.shelfdsl_id;
            await this.refreshTreeFromCurrentItem(true);
            await this.showMenu(ThietBi.PRE_DSLAM_FRAME_SHELF, 0);
            this.currentItem.action = ACTION_VIEW;
            this.SetButton(3)
          }
        } else if (
          this.currentItem.itemType == ThietBi.PRE_DSLAM_FRAME_SHELF_CARD
        ) {
          let rs = await this.$refs.formDsLamCard.ghiLai();
          if (rs) {           
            this.currentItem.itemID = this.$refs.formDsLamCard.currentItem.carddsl_id;
            this.carddsl_id = this.$refs.formDsLamCard.currentItem.carddsl_id;
            await this.refreshTreeFromCurrentItem(true);
            await this.showMenu(ThietBi.PRE_DSLAM_FRAME_SHELF_CARD, 0);
            this.currentItem.action = ACTION_VIEW;
            this.SetButton(3)
          }
        } else if (
          this.currentItem.itemType == ThietBi.PRE_DSLAM_FRAME_SHELF_CARD_MODULE
        ) {
          let rs = await this.$refs.formDsLamModule.ghiLai();
          if (rs) {           
            this.currentItem.itemID = this.$refs.formDsLamModule.currentItem.moduledsl_id;
            this.moduledsl_id = this.$refs.formDsLamModule.currentItem.moduledsl_id;
            await this.refreshTreeFromCurrentItem(true);
            await this.showMenu(ThietBi.PRE_DSLAM_FRAME_SHELF_CARD_MODULE, 0);
            this.currentItem.action = ACTION_VIEW;
            this.SetButton(3)
          }
        } else if (
          this.currentItem.itemType == ThietBi.PRE_FRAME_CARD ||
          this.currentItem.itemType == ThietBi.PRE_FRAME_SLOT
        ) {
          let rs = await this.$refs.formCardTD.ghiLai();
          if (rs) {           
            this.cardtd_id = this.$refs.formCardTD.cardtd_id;
            this.currentItem.itemID = this.$refs.formCardTD.cardtd_id;
            this.currentItem.itemType = ThietBi.PRE_FRAME_CARD;
            await this.refreshTreeFromCurrentItem(true);
            await this.showMenu(ThietBi.PRE_FRAME_CARD, 0);
            this.currentItem.action = ACTION_VIEW;
            this.SetButton(3)
          }
        } else if (
          this.currentItem.itemType == ThietBi.PRE_GPON_SLOT ||
          this.currentItem.itemType == ThietBi.PRE_GPON_CARD
        ) {
          let rs = await this.$refs.formGponCard.formGhiLai();
          if (rs == true) {          
            if (this.currentItem.itemID == 0) {
              this.currentItem.itemID = this.$refs.formGponCard.currentItem.CARDGP_ID;
              this.currentItem.itemType = ThietBi.PRE_GPON_CARD;
            }
            await this.refreshTreeFromCurrentItem(true);
            this.currentItem.action = ACTION_VIEW;
            this.SetButton(3)
          }
        } else if (
          this.currentItem.itemType == ThietBi.PRE_MANE_SLOT ||
          this.currentItem.itemType == ThietBi.PRE_MANE_CARD
        ) {
          let rs = await this.$refs.formManeCard.formGhiLai();
          if (rs == true) {          
            if (this.currentItem.itemID == 0) {
              this.currentItem.itemID = this.$refs.formManeCard.currentItem.CARDMANE_ID;
              this.currentItem.itemType = ThietBi.PRE_MANE_CARD;
            }
            await this.refreshTreeFromCurrentItem(true);
            this.currentItem.action = ACTION_VIEW;
            this.SetButton(3)
          }
        }
        else if(this.currentItem.itemType == ThietBi.PRE_MANE_CARD_MODULE) {
          let rs = await this.$refs.formManeModule.ghiLai();
          if (rs) {          
            this.currentItem.itemID = this.$refs.formManeModule.currentItem.modulemane_id;
            this.modulemane_id = this.$refs.formManeModule.currentItem.modulemane_id;
            await this.refreshTreeFromCurrentItem(true);
            await this.showMenu(ThietBi.PRE_MANE_CARD_MODULE, 0);
            this.currentItem.action = ACTION_VIEW;
            this.SetButton(3)
          }
        } else if(this.currentItem.itemType == ThietBi.PRE_SWITCH_SLOT || this.currentItem.itemType == ThietBi.PRE_SWITCH_CARD || this.currentItem.itemType == ThietBi.PRE_SWITCH_SLOT_SUB) {
          let rs = await this.$refs.formSwitchCard.ghiLai();
          if(rs) {
            this.currentItem.itemID = this.$refs.formSwitchCard.currentItem.cardsw_id;
            this.currentItem.itemType = ThietBi.PRE_SWITCH_CARD;
            await this.refreshTreeFromCurrentItem(true);
            await this.showMenu(ThietBi.PRE_SWITCH_CARD, 0);
            this.currentItem.action = ACTION_VIEW;
            this.SetButton(3)
          }
        }
      } catch (e) {
      } finally {
        this.$root.showLoading(false);
      }
    },
    btnHuy_Click: function () {
      if (this.currentItem.itemType == ThietBi.PRE_TURACK) {
        this.$refs.formTuRack.clearForm();
      } else if (
        this.currentItem.itemType == ThietBi.PRE_DSLAM ||
        this.currentItem.itemType == ThietBi.PRE_GPON ||
        this.currentItem.itemType == ThietBi.PRE_SWITCH ||
        this.currentItem.itemType == ThietBi.PRE_MANE
      ) {
        this.$refs.formDsLamSwitchGpon.clearForm();
      } else if (this.currentItem.itemType == ThietBi.PRE_BRAS) {
        this.$refs.formBras.formHuyBo();
      } else if (this.currentItem.itemType == ThietBi.PRE_FRAME) {
        this.$refs.formFrameTD.clearForm();
      } else if (this.currentItem.itemType == ThietBi.PRE_ODF) {
        this.$refs.formODF.clearForm();
      } else if (this.currentItem.itemType == ThietBi.PRE_DSLAM_FRAME) {
        this.$refs.formDsLamFrame.clearForm();
      } else if (this.currentItem.itemType == ThietBi.PRE_DSLAM_FRAME_SHELF) {
        this.$refs.formDsLamShelf.clearForm();
      } else if (
        this.currentItem.itemType == ThietBi.PRE_DSLAM_FRAME_SHELF_CARD
      ) {
        this.$refs.formDsLamCard.clearForm();
      } else if (
        this.currentItem.itemType == ThietBi.PRE_DSLAM_FRAME_SHELF_CARD_MODULE
      ) {
        this.$refs.formDsLamModule.clearForm();
      } else if (
        this.currentItem.itemType == ThietBi.PRE_FRAME_CARD ||
        this.currentItem.itemType == ThietBi.PRE_FRAME_SLOT
      ) {
        this.$refs.formCardTD.clearForm();
      } else if (
        this.currentItem.itemType == ThietBi.PRE_GPON_SLOT ||
        this.currentItem.itemType == ThietBi.PRE_GPON_CARD
      ) {
        this.$refs.formGponCard.formHuy();
      } else if (
        this.currentItem.itemType == ThietBi.PRE_MANE_SLOT ||
        this.currentItem.itemType == ThietBi.PRE_MANE_CARD
      ) {
        this.$refs.formManeCard.formHuy();
      } else if (
        this.currentItem.itemType == ThietBi.PRE_MANE_CARD_MODULE
      ) {
        this.$refs.formManeModule.clearForm();
      } else if (this.currentItem.itemType == ThietBi.PRE_SWITCH_SLOT || this.currentItem.itemType == ThietBi.PRE_SWITCH_CARD || this.currentItem.itemType == ThietBi.PRE_SWITCH_SLOT_SUB) {
        this.$refs.formSwitchCard.clearForm();
      }
      this.SetButton(2)
    },
    btnXoa_Click: async function () {
      this.isGhiLai = false;
      this.isXoa = true;
      this.modalThongBao = true;
      this.thongbao = "Bạn có muốn xóa dữ liệu không?";
    },
    xoa: async function () {
      try {
        if (this.currentItem.hasChildren) {
        }
        this.$root.showLoading(true);
        let rs = false;
        if (this.currentItem.itemType == ThietBi.PRE_TURACK) {
          rs = await this.$refs.formTuRack.xoa();
          
        } else if (
          this.currentItem.itemType == ThietBi.PRE_DSLAM ||
          this.currentItem.itemType == ThietBi.PRE_GPON ||
          this.currentItem.itemType == ThietBi.PRE_SWITCH ||
          this.currentItem.itemType == ThietBi.PRE_MANE
        ) {
          rs = await this.$refs.formDsLamSwitchGpon.xoa();
        
        } else if (this.currentItem.itemType == ThietBi.PRE_BRAS) {
          rs = await this.$refs.formBras.formXoa();
         
        } else if (this.currentItem.itemType == ThietBi.PRE_FRAME) {
          rs = await this.$refs.formFrameTD.xoa();
         
        } else if (this.currentItem.itemType == ThietBi.PRE_ODF) {
          rs = await this.$refs.formODF.xoa();
         
        } else if (this.currentItem.itemType == ThietBi.PRE_DSLAM_FRAME) {
          rs = await this.$refs.formDsLamFrame.xoa();
         
        } else if (this.currentItem.itemType == ThietBi.PRE_DSLAM_FRAME_SHELF) {
          rs = await this.$refs.formDsLamShelf.xoa();
         
        } else if (
          this.currentItem.itemType == ThietBi.PRE_DSLAM_FRAME_SHELF_CARD
        ) {
          rs = await this.$refs.formDsLamCard.xoa();
         
        } else if (
          this.currentItem.itemType == ThietBi.PRE_DSLAM_FRAME_SHELF_CARD_MODULE
        ) {
          rs = await this.$refs.formDsLamModule.xoa();
          
        } else if (this.currentItem.itemType == ThietBi.PRE_FRAME_CARD) {
          rs = await this.$refs.formCardTD.xoa();
         
        } else if (this.currentItem.itemType == ThietBi.PRE_FRAME_SLOT) {
          rs = await this.$refs.formCardTD.xoa();
          
        } else if (this.currentItem.itemType == ThietBi.PRE_GPON_CARD) {
          rs = await this.$refs.formGponCard.formXoa();
         
        } else if (this.currentItem.itemType == ThietBi.PRE_MANE_CARD) {
          rs = await this.$refs.formManeCard.formXoa();
         
        } else if (this.currentItem.itemType == ThietBi.PRE_MANE_CARD_MODULE) {
          rs = await this.$refs.formManeModule.xoa();
         
        } else if (this.currentItem.itemType == ThietBi.PRE_SWITCH_CARD) {
          rs = await this.$refs.formSwitchCard.xoa();
        }
        if(rs) {
           this.SetButton(0)
          await this.refreshTree(
            this.currentItem.itemParentType,
            this.currentItem.itemParentID,
            true,
            this.turack_id
          );
        }
      } finally {     
        this.$root.showLoading(false);
      }
    },
    xacNhan: async function () {
      if (this.isGhiLai) this.ghiLai();
      else if (this.xoa) this.xoa();
    },
    caySoDoThietBi_nodeSelected: async function (args) {
      console.log("args:", args);
      
       this.currentItem = {
            
              itemType: null,
            };
      if (args != null && args.itemData != null) {
        this.$root.showLoading(true);
        try {
          this.turack_id = args.itemData.itemAttributes.TURACK // Để lưu giữ tủ rack id dùng cho các form khác
          let type = args.itemData.itemType;
          let id = args.itemData.itemID;
          let name = args.text;
          let level = args.node.ariaLevel;
          // let parentType = args.itemData.itemParentType;
          this.dataSend = {
            dslam_id: id,
            type: type,
          };
          console.log(type, level);
          await this.showMenu(type, level);
          if(level < 3) {
            this.SetButton();
          }
          else {
            this.SetButton(3);
          }
          if (type == ThietBi.PRE_DONVI) {
            this.donvi_id = id;
            this.currentItem = {};
          } else if (type == ThietBi.PRE_TURACK) {
            this.turack_id = id; // Để lưu giữ tủ rack id dùng cho các form khác
            // this.show_TuRack(id)
            this.currentItem = {
              itemID: id,
              nhatram_id: args.itemData.itemParentID,
              itemType: ThietBi.PRE_TURACK,
              action: ACTION_VIEW,
            };
            await this.$refs.formTuRack.bindData(this.currentItem);
          } else if (type == ThietBi.PRE_ODF) {
            this.odf_id = id;
            let rs = await this.layTuRack(args.itemData.itemParentID);
            this.currentItem = {
              itemID: id,
              nhatram_id: rs.DONVI_ID,
              turack_id: 0,
              itemType: ThietBi.PRE_ODF,
              action: ACTION_VIEW,
            };
            await this.$refs.formODF.bindData(this.currentItem);
          } else if (type == ThietBi.PRE_BRAS) {
            this.currentItem = {
              itemID: args.itemData.itemID,
              itemType: ThietBi.PRE_BRAS,
              action: ACTION_VIEW,
            };
            await this.$refs.formBras.bindData(args.itemData.itemID);
          } else if (type == ThietBi.PRE_BRAS_CARD) {
            let rs = await this.layBrasCard(id);
            this.currentItem = {
              itemID: id,
              itemType: ThietBi.PRE_BRAS_CARD,
              slot: args.text.split(' ')[0].substr(1),
              vitri: 0,
              bras_id: args.itemData.itemParentID,
              action: ACTION_VIEW,
              data: rs
            };
            await this.$refs.formBrasCard.bindData(this.currentItem);
          } else if (type == ThietBi.PRE_BRAS_CARD_PORT) {
            // this.showBras_Card_Port(id)
          } else if (type == ThietBi.PRE_GPON) {
            //  this.show_Gpon(id)
            this.dslam_id = id;
            this.currentItem = {
              itemID: id,
              loaiTb_id: 3,
              turack_id: args.itemData.itemParentID,
              itemType: ThietBi.PRE_GPON,
              action: ACTION_VIEW,
              slotType: ThietBi.PRE_GPON_SLOT,
            };
            await this.$refs.formDsLamSwitchGpon.bindData(this.currentItem);
          } else if (type == ThietBi.PRE_GPON_CARD) {
            this.gponcard_id = id;
            this.currentItem = {
              itemID: args.itemData.itemID,
              itemType: ThietBi.PRE_GPON_CARD,
              attributes: args.itemData.itemAttributes,
              action: ACTION_VIEW,
            };
            await this.$refs.formGponCard.bindData(args.itemData.itemID);
          } else if (type == ThietBi.PRE_GPON_SLOT) {
            this.currentItem = {
              itemID: 0,
              itemType: ThietBi.PRE_GPON_SLOT,
              itemParentID: args.itemData.itemParentID,
              itemParentType: args.itemData.itemParentType,
              attributes: args.itemData.itemAttributes,
              action: ACTION_NEW,
            };
            await this.$refs.formGponCard.themMoi(
              args.itemData.itemParentID,
              args.itemData.itemAttributes.slotIndex
            );
          } else if (type == ThietBi.PRE_GPON_CARD_PORT) {
            let rs = await this.layGponCard(args.itemData.itemParentID);
            if (rs == null) return;
            let port = await this.layGponPort(args.itemData.itemID);
            if (port == null) return;
            let edit = false;
            if (rs == null || port == null) return;
            if (rs.NHOMCARD_ID == 2 || rs.NHOMCARD_ID == 3) {
              // form Hướng Kết Nối
              if (port.LINK_CODE == 0) {
                if (port.TT_PORT_ID != 1 && port.TT_PORT_ID != 5) return;
              }
              if (port.LINK_CODE == 1 || port.LINK_CODE == 2) edit = true;
              let data = {
                connect: port.LINK_CODE,
                view: 1,
                port_DsLam: {
                  vitri: port.VITRI,
                  ma_dslamPort: port.PORTVL_ID,
                },
                edit: edit,
                fkey: ThietBi.PRE_GPON_CARD_PORT + "_" + port.PORTVL_ID,
                ten_tbi_nguon: rs.TEN_CARD,
                thietbi_n_id: rs.GPON_ID,
                loai_tbi_n: 3, //GPON:3
                loai_tbi_d: 0,
                port_dich: 0,
              };
              this.currentItem = {
                itemID: args.itemData.itemID,
                itemType: ThietBi.PRE_GPON_CARD_PORT,
                action: ACTION_VIEW,
                upLink: true,
              };
              await this.$refs.formHKN.bindData(data, args);
            } else {
              this.currentItem = {
                itemID: id,
                gponcard_id: this.gponcard_id,
                itemType: ThietBi.PRE_GPON_CARD_PORT,
                itemParentType: args.itemData.itemParentType,
                itemParentID: args.itemData.itemParentID,
                action: ACTION_VIEW,
                upLink: false
              };
              await this.$refs.formPortGpon.bindData(this.currentItem);
            }
          } else if (type == ThietBi.PRE_MANE) {
            // this.show_Mane(id)
            this.dslam_id = id;
            this.currentItem = {
              itemID: id,
              loaiTb_id: 17,
              turack_id: args.itemData.itemParentID,
              itemType: ThietBi.PRE_MANE,
              action: ACTION_VIEW,
              slotType: ThietBi.PRE_MANE_SLOT,
            };
            await this.$refs.formDsLamSwitchGpon.bindData(this.currentItem);
          } else if (type == ThietBi.PRE_MANE_SLOT) {
            this.currentItem = {
              itemID: 0,
              itemType: ThietBi.PRE_MANE_SLOT,
              itemParentID: args.itemData.itemParentID,
              itemParentType: args.itemData.itemParentType,
              attributes: args.itemData.itemAttributes,
              action: ACTION_NEW,
            };
            await this.$refs.formManeCard.themMoi(
              args.itemData.itemParentID,
              args.itemData.itemAttributes.slotIndex
            );
          } else if (type == ThietBi.PRE_MANE_CARD) {
            this.cardmane_id = id;
            this.currentItem = {
              itemID: args.itemData.itemID,
              itemType: ThietBi.PRE_MANE_CARD,
              attributes: args.itemData.itemAttributes,
              action: ACTION_VIEW,
            };
            await this.$refs.formManeCard.bindData(args.itemData.itemID);
          } else if (type == ThietBi.PRE_MANE_CARD_PORT) {
            // Hiển thị form upLink
            let edit = false;
            let card = await this.layManeCard(args.itemData.itemParentID);
            if (card == null) return;
            let port = await this.layManePort(args.itemData.itemID);
            if (port == null) return;
            if (port.LINK_CODE == 1 || port.LINK_CODE == 2) edit = true;
            let data = {
              connect: port.LINK_CODE,
              view: 1,
              port_DsLam: {
                vitri: port.VITRI,
                ma_dslamPort: port.PORTVL_ID,
              },
              edit: edit,
              fkey: ThietBi.PRE_MANE_CARD_PORT + "_" + port.PORTVL_ID,
              ten_tbi_nguon: card.LOAI_CARD,
              thietbi_n_id: card.DSLAM_ID,
              loai_tbi_n: 17, //DSLAM:1;SWITCH:2;GPON:3;MANE:17
              loai_tbi_d: 0,
              port_dich: 0,
            };
            this.currentItem = {
              itemID: args.itemData.itemID,
              itemType: ThietBi.PRE_MANE_CARD_PORT,
              action: ACTION_VIEW,
              upLink: true,
            };
            await this.$refs.formHKN.bindData(data, args);
          } else if (type == ThietBi.PRE_MANE_CARD_MODULE) {
            this.modulemane_id = id;
            this.currentItem = {
              itemID: id,
              cardmane_id: this.cardmane_id,
              itemType: ThietBi.PRE_MANE_CARD_MODULE,
              action: ACTION_VIEW,
            };
            await this.$refs.formManeModule.bindData(this.currentItem);
          } else if (type == ThietBi.PRE_MANE_CARD_MODULE_PORT) {
            // Hiển thị form upLink
            let edit = false;
            let module = await this.layManeModule(args.itemData.itemParentID);
            if (module == null) return;
            let port = await this.layManeModulePort(args.itemData.itemID);
            if (port == null) return;
            if (port.LINK_CODE == 0) {
              if (port.TT_PORT_ID != 1 && port.TT_PORT_ID != 5) return;
            }
            if (port.LINK_CODE == 1 || port.LINK_CODE == 2) edit = true;
            let data = {
              connect: port.LINK_CODE,
              view: 1,
              port_DsLam: {
                vitri: port.VITRI,
                ma_dslamPort: port.PORTVL_ID,
              },
              edit: edit,
              fkey: ThietBi.PRE_MANE_CARD_MODULE_PORT + "_" + port.PORTVL_ID,
              ten_tbi_nguon: module.LOAI_MODULE,
              thietbi_n_id: 0, //module.MODULEMANE_ID,
              loai_tbi_n: 17, //DSLAM:1;SWITCH:2;GPON:3;MANE:17
              loai_tbi_d: 0,
              port_dich: 0,
            };
            this.currentItem = {
              itemID: args.itemData.itemID,
              itemType: ThietBi.PRE_MANE_CARD_MODULE_PORT,
              action: ACTION_VIEW,
              upLink: true,
            };
            await this.$refs.formHKN.bindData(data, args);
          } else if (type == ThietBi.PRE_SWITCH) {
            //  this.show_Switch(id)
            this.dslam_id = id;
            this.currentItem = {
              itemID: id,
              loaiTb_id: 2,
              turack_id: args.itemData.itemParentID,
              itemType: ThietBi.PRE_SWITCH,
              action: ACTION_VIEW,
              slotType: ThietBi.PRE_SWITCH_PORT,
            };
            await this.$refs.formDsLamSwitchGpon.bindData(this.currentItem);
          } else if (type == ThietBi.PRE_SWITCH_PORT) {
            let rs = await this.checkPortSwitch(args.itemData.itemID);
            if (rs > 0) {
              this.currentItem = {
                itemID: args.itemData.itemID,
                itemType: ThietBi.PRE_SWITCH_PORT,
                action: ACTION_VIEW,
                upLink: false,
              };
              await this.$refs.formPortSwitch.bindData(args.itemData.itemID, 0);
            } else {
              // Hiển thị form upLink
              let edit = false;
              let port = await this.laySwitchPort(args.itemData.itemID);
              if (port == null) return;
              let sw = await this.layDslamGponSwitch(
                args.itemData.itemParentID,
                2
              );
              if (sw == null) return;
              if (port.LINK_CODE == 1 || port.LINK_CODE == 2) edit = true;
              let data = {
                connect: port.LINK_CODE,
                view: 1,
                port_DsLam: {
                  vitri: port.VITRI,
                  ma_dslamPort: port.PORTVL_ID,
                },
                edit: edit,
                fkey: ThietBi.PRE_SWITCH_PORT + "_" + port.PORTVL_ID,
                ten_tbi_nguon: sw.TENDSLAM,
                thietbi_n_id: sw.DSLAM_ID,
                loai_tbi_n: 2, //DSLAM:1;SWITCH:2;GPON:3;MANE:17
                loai_tbi_d: 0,
                port_dich: 0,
              };
              this.currentItem = {
                itemID: args.itemData.itemID,
                itemType: ThietBi.PRE_SWITCH_PORT,
                action: ACTION_VIEW,
                upLink: true,
              };
              await this.$refs.formHKN.bindData(data, args);
            }
          } else if (type == ThietBi.PRE_SWITCH_CARD) {
            let rs = await this.laySwitchCard(id);
            let arr = args.itemData.itemParentID.split('_');
            this.currentItem = {
              itemID: id,
              itemType: ThietBi.PRE_SWITCH_CARD,
              slot: arr[1],
              vitri: 0,
              dslam_id: arr[0],
              action: ACTION_VIEW,
              data: rs
            };
            await this.$refs.formSwitchCard.bindData(this.currentItem);
          } else if (type == ThietBi.PRE_SWITCH_SLOT) {
            if(!args.hasChildren) {
              this.currentItem = {
                itemID: 0,
                itemType: ThietBi.PRE_SWITCH_SLOT,
                slot: args.text.substr(1),
                vitri: -1,
                dslam_id: args.itemData.itemParentID,
                action: ACTION_VIEW,
              };
              await this.$refs.formSwitchCard.bindData(this.currentItem);
            }
            else {
              this.currentItem = {}
            }
          } else if (type == ThietBi.PRE_SWITCH_SLOT_SUB) {
            let arr = args.itemData.itemID.split('_');
            this.currentItem = {
              itemID: 0,
              itemType: ThietBi.PRE_SWITCH_SLOT_SUB,
              slot: arr[1],
              vitri: arr[2],
              dslam_id: arr[0],
              action: ACTION_VIEW,
            };
            await this.$refs.formSwitchCard.bindData(this.currentItem);
          } else if (type == ThietBi.PRE_SWITCH_CARD_PORT) {
            let rs = await this.laySwitchCard(args.itemData.itemParentID);
            if (!(rs.NHOMCARD_ID == 2 || rs.NHOMCARD_ID == 3)) {
              this.currentItem = {
                itemID: args.itemData.itemID,
                itemType: ThietBi.PRE_SWITCH_CARD_PORT,
                action: ACTION_VIEW,
                upLink: false,
              };
              await this.$refs.formPortSwitch.bindData(args.itemData.itemID, 1);
            } else {
              // Hiển thị form upLink
              let edit = false;
              let cardsw = await this.laySwitchCard(args.itemData.itemParentID);
              if (cardsw == null) return;
              let sw = await this.layDslamGponSwitch(cardsw.SWITCH_ID, 2);
              if (sw == null) return;
              let port = await this.laySwitchCardPort(args.itemData.itemID);
              if (port == null) return;
              if (port.LINK_CODE == 1 || port.LINK_CODE == 2) edit = true;
              let data = {
                connect: port.LINK_CODE,
                view: 1,
                port_DsLam: {
                  vitri: port.VITRI,
                  ma_dslamPort: port.PORTVL_ID,
                },
                edit: edit,
                fkey: ThietBi.PRE_SWITCH_CARD_PORT + "_" + port.PORTVL_ID,
                ten_tbi_nguon: sw.TENDSLAM,
                thietbi_n_id: sw.DSLAM_ID,
                loai_tbi_n: 2, //DSLAM:1;SWITCH:2;GPON:3;MANE:17
                loai_tbi_d: 0,
                port_dich: 0,
              };
              this.currentItem = {
                itemID: args.itemData.itemID,
                itemType: ThietBi.PRE_SWITCH_CARD_PORT,
                action: ACTION_VIEW,
                upLink: true,
              };
              await this.$refs.formHKN.bindData(data, args);
            }
          } else if (type == ThietBi.PRE_DSLAM) {
            //  this.show_Dslam(id)
            this.dslam_id = id;
            this.currentItem = {
              itemID: id,
              loaiTb_id: 1,
              turack_id: args.itemData.itemParentID,
              itemType: ThietBi.PRE_DSLAM,
              action: ACTION_VIEW,
              // parentType,
              slotType: ThietBi.PRE_DSLAM,
            };
            await this.$refs.formDsLamSwitchGpon.bindData(this.currentItem);
          } else if (type == ThietBi.PRE_DSLAM_FRAME) {
            //this.show_Dslam_Frame(id)
            this.framedsl_id = id;
            this.currentItem = {
              itemID: id,
              dslam_id: args.itemData.itemParentID,
              itemType: ThietBi.PRE_DSLAM_FRAME,
              action: ACTION_VIEW,
              turack_id: this.turack_id,
              // parentType,
            };
            await this.$refs.formDsLamFrame.bindData(this.currentItem);
          } else if (type == ThietBi.PRE_DSLAM_FRAME_SHELF) {
            //  this.show_Dslam_Frame_Shelf(id)
            this.shelfdsl_id = id;
            this.currentItem = {
              itemID: id,
              framedsl_id: args.itemData.itemParentID,
              itemType: ThietBi.PRE_DSLAM_FRAME_SHELF,
              action: ACTION_VIEW,
            };
            await this.$refs.formDsLamShelf.bindData(this.currentItem);
          } else if (type == ThietBi.PRE_DSLAM_FRAME_SHELF_SLOT) {
            this.vitriShlefdsl = args.text.substr(1);
            this.currentItem = {
              itemID: 0,
              shelfdsl_id: args.itemData.itemParentID,
              vitri: this.vitriShlefdsl,
              itemType: ThietBi.PRE_DSLAM_FRAME_SHELF_CARD,
              action: ACTION_VIEW,
            };
            await this.$refs.formDsLamCard.bindData(this.currentItem);
          } else if (type == ThietBi.PRE_DSLAM_FRAME_SHELF_CARD) {
            //this.show_Dslam_Frame_Shelf_Card(id)
            this.carddsl_id = id;
            this.currentItem = {
              itemID: id,
              shelfdsl_id: args.itemData.itemParentID,
              vitri: parseInt(args.text.substr(1)),
              ten: args.text.split(" ")[1],
              itemType: ThietBi.PRE_DSLAM_FRAME_SHELF_CARD,
              action: ACTION_VIEW,
            };
            await this.$refs.formDsLamCard.bindData(this.currentItem);
          } else if (type == ThietBi.PRE_DSLAM_FRAME_SHELF_CARD_PORT) {
            // Hiển thị form upLink
            let edit = false;
            let card = await this.layDslamCard(args.itemData.itemParentID);
            if (card == null) return;
            let port = await this.layDslamPort(args.itemData.itemID);
            if (port == null) return;
            if (port.LINK_CODE == 0) {
              if (port.TT_PORT_ID != 1 && port.TT_PORT_ID != 5) return;
            }
            if (port.LINK_CODE == 1 || port.LINK_CODE == 2) edit = true;
            let data = {
              connect: port.LINK_CODE,
              view: 1,
              port_DsLam: {
                vitri: port.VITRI,
                ma_dslamPort: port.PORTVL_ID,
              },
              edit: edit,
              fkey:
                ThietBi.PRE_DSLAM_FRAME_SHELF_CARD_PORT + "_" + port.PORTVL_ID,
              ten_tbi_nguon: card.LOAI_CARD,
              thietbi_n_id: 0, //card.DSLAM_ID,
              loai_tbi_n: 1, //DSLAM:1;SWITCH:2;GPON:3;MANE:17
              loai_tbi_d: 0,
              port_dich: 0,
            };
            this.currentItem = {
              itemID: args.itemData.itemID,
              itemType: ThietBi.PRE_DSLAM_FRAME_SHELF_CARD_PORT,
              action: ACTION_VIEW,
              upLink: true,
            };
            await this.$refs.formHKN.bindData(data, args);
          } else if (type == ThietBi.PRE_DSLAM_FRAME_SHELF_CARD_MODULE) {
            //this.show_Dslam_Frame_Shelf_Card_Module(id)
            this.moduledsl_id = id;
            this.currentItem = {
              itemID: id,
              carddsl_id: args.itemData.itemParentID,
              itemType: ThietBi.PRE_DSLAM_FRAME_SHELF_CARD_MODULE,
              action: ACTION_VIEW,
            };
            await this.$refs.formDsLamModule.bindData(this.currentItem);
          } else if (type == ThietBi.PRE_DSLAM_FRAME_SHELF_CARD_MODULE_PORT) {
            // Hiển thị form upLink
            let edit = false;
            let module = await this.layDslamModule(args.itemData.itemParentID);
            if (module == null) return;
            let port = await this.layDslamModulePort(args.itemData.itemID);
            if (port == null) return;
            if (port.LINK_CODE == 0) {
              if (port.TT_PORT_ID != 1 && port.TT_PORT_ID != 5) return;
            }
            if (port.LINK_CODE == 1 || port.LINK_CODE == 2) edit = true;
            let data = {
              connect: port.LINK_CODE,
              view: 1,
              port_DsLam: {
                vitri: port.VITRI,
                ma_dslamPort: port.PORTVL_ID,
              },
              edit: edit,
              fkey:
                ThietBi.PRE_DSLAM_FRAME_SHELF_CARD_MODULE_PORT +
                "_" +
                port.PORTVL_ID,
              ten_tbi_nguon: module.LOAI_MODULE,
              thietbi_n_id: 0, //module.MODULEDSL_ID,
              loai_tbi_n: 1, //DSLAM:1;SWITCH:2;GPON:3;MANE:17
              loai_tbi_d: 0,
              port_dich: 0,
            };
            this.currentItem = {
              itemID: args.itemData.itemID,
              itemType: ThietBi.PRE_DSLAM_FRAME_SHELF_CARD_MODULE_PORT,
              action: ACTION_VIEW,
              upLink: true,
            };
            await this.$refs.formHKN.bindData(data, args);
          } else if (type == ThietBi.PRE_FRAME) {
            //  this.show_Frame(id)
            this.frametd_id = id;
            let rs = await this.layTuRack(args.itemData.itemParentID);
            this.currentItem = {
              itemID: id,
              turack_id: args.itemData.itemParentID,
              nhatram_id: rs.DONVI_ID,
              itemType: ThietBi.PRE_FRAME,
              action: ACTION_VIEW,
            };
            await this.$refs.formFrameTD.bindData(this.currentItem);
          } else if (type == ThietBi.PRE_FRAME_CARD) {
            this.cardtd_id = id;
            this.currentItem = {
              itemID: id,
              frametd_id: args.itemData.itemParentID,
              cardtd_id: id,
              vitri: parseInt(args.text.substr(1)),
              itemType: ThietBi.PRE_FRAME_CARD,
              action: ACTION_VIEW,
            };
            await this.$refs.formCardTD.bindData(this.currentItem);
          } else if (type == ThietBi.PRE_FRAME_SLOT) {
            this.currentItem = {
              itemID: 0,
              frametd_id: args.itemData.itemParentID,
              cardtd_id: 0,
              vitri: parseInt(args.text.substr(1)),
              itemType: ThietBi.PRE_FRAME_SLOT,
              action: ACTION_VIEW,
            };
            await this.$refs.formCardTD.bindData(this.currentItem);
          } else {
            this.currentItem = {};
          }
          this.currentItem.hasChildren = args.hasChildren;
          this.currentItem.text = name;
          this.currentItem.itemParentID = args.itemData.itemParentID;
          this.currentItem.itemParentType = args.itemData.itemParentType;
        } finally {
          this.$root.showLoading(false);
        }
      }
    },
    showMenu: async function (code, level) {
      this.menu = {
        tu_rack: false,
        bras: false,
        shelf: false,
        dslamcard: false,
        dslam: false,
        mane: false,
        frametd: false,
        odf: false,
        gpon: false,
        frame: false,
        dslamRack: false,
        module: false,
        chuyenDslam: false,
        switch: false,
        new: true,
        delete: true,
        cancel: true,
        save: true,
        lapCard: false,
      };
      if (level == "1" || level == "2") return;
      console.log(code);
      switch (code) {
        case ThietBi.PRE_DONVI:
          this.menu.tu_rack = true;
          break;
        case ThietBi.PRE_TURACK:
          this.menu.bras = true;
          this.menu.dslam = true;
          this.menu.gpon = true;
          this.menu.switch = true;
          this.menu.mane = true;
          this.menu.odf = true;
          this.menu.frametd = true;
          break;
        case ThietBi.PRE_DSLAM:
          this.menu.frame = true;
          this.menu.chuyenDslam = true;
          break;
        case ThietBi.PRE_FRAME:
          this.menu.chuyenDslam = true;
          break;
        case ThietBi.PRE_ODF:
          this.menu.chuyenDslam = true;
          break;
        case ThietBi.PRE_DSLAM_FRAME:
          this.menu.shelf = true;
          break;
        case ThietBi.PRE_DSLAM_FRAME_SHELF_CARD:
          this.menu.module = true;
          this.menu.cancel = false;
          this.menu.new = false;
          break;
        case ThietBi.PRE_MANE_CARD:
          this.menu.module = true;
          this.menu.cancel = false;
          this.menu.new = false;
          break;
        case ThietBi.PRE_GPON_CARD:
          this.menu.cancel = false;
          this.menu.new = false;
          break;
        case ThietBi.PRE_FRAME_CARD:
          this.menu.cancel = false;
          this.menu.new = false;
          break;
        case ThietBi.PRE_MANE:
          this.menu.chuyenDslam = true;
          break;
        case ThietBi.PRE_GPON_CARD_PORT:
        case ThietBi.PRE_SWITCH_PORT:
        case ThietBi.PRE_MANE_CARD_PORT:
        case ThietBi.PRE_MANE_CARD_MODULE_PORT:
        case ThietBi.PRE_DSLAM_FRAME_SHELF_CARD_PORT:
        case ThietBi.PRE_DSLAM_FRAME_SHELF_CARD_MODULE_PORT:
        case ThietBi.PRE_SWITCH_CARD_PORT:
          this.menu.cancel = false;
          this.menu.new = false;
          this.menu.delete = false;
          this.menu.save = false;
          break;
        case ThietBi.PRE_DSLAM_FRAME_SHELF:
          this.menu.dslamcard = true;
          break;
        case ThietBi.PRE_SWITCH:
          this.menu.chuyenDslam = true;
          break;
        case ThietBi.PRE_GPON:
          this.menu.chuyenDslam = true;
          break;
        case ThietBi.PRE_DSLAM_FRAME_SHELF_SLOT:
          this.menu.new = false;
          this.menu.cancel = false;
          this.menu.delete = false;
          this.menu.lapCard = true;
          break;
        case ThietBi.PRE_FRAME_SLOT:
          this.menu.cancel = false;
          this.menu.new = false;
          this.menu.delete = false;
          this.menu.save = true;
          break;
        case ThietBi.PRE_SWITCH_SLOT:
        case ThietBi.PRE_SWITCH_SLOT_SUB:
        case ThietBi.PRE_MANE_SLOT:
        case ThietBi.PRE_GPON_SLOT:
          this.menu.cancel = false;
          this.menu.new = false;
          this.menu.delete = false;
          this.menu.save = true;
          break;
        case ThietBi.PRE_SWITCH_CARD:
          this.menu.cancel = true;
          this.menu.new = false;
          this.menu.delete = true;
          this.menu.save = true;
          break;
        default:
          break;
      }
    },
    //#endregion

    //#region "Show thông tin"
    thongTinBras: function () {
      this.SetButton(1);
      this.$refs.formBras.setTuRack_id(this.turack_id, true);
      this.currentItem = {
        itemID: 0,
        itemType: ThietBi.PRE_BRAS,
        action: ACTION_NEW,
      };
    },
    thongTinDslam: function () {
      this.SetButton(1);
      this.currentItem = {
        itemID: 0,
        loaiTb_id: 1,
        turack_id: this.turack_id,
        itemType: ThietBi.PRE_DSLAM,
        action: ACTION_NEW,
      };
      this.$refs.formDsLamSwitchGpon.setTuRack_id(this.currentItem);
    },
    thongTinFrameTD: async function () {
      this.SetButton(1);
      let rs = await this.layTuRack(this.turack_id);
      this.currentItem = {
        itemID: 0,
        turack_id: this.turack_id,
        nhatram_id: rs.DONVI_ID,
        itemType: ThietBi.PRE_FRAME,
        action: ACTION_NEW,
      };
      this.$refs.formFrameTD.bindData(this.currentItem);
    },
    thongTinODF: async function () {
      this.SetButton(1);
      let rs = await this.layTuRack(this.turack_id);
      this.currentItem = {
        itemID: 0,
        turack_id: this.turack_id,
        nhatram_id: rs.DONVI_ID,
        itemType: ThietBi.PRE_ODF,
        action: ACTION_NEW,
      };
      this.$refs.formODF.bindData(this.currentItem);
    },
    thongTinDslamFrame: async function () {
      this.SetButton(1);
      this.currentItem = {
        itemID: 0,
        dslam_id: this.dslam_id,
        itemType: ThietBi.PRE_DSLAM_FRAME,
        action: ACTION_NEW,
      };
      this.$refs.formDsLamFrame.bindData(this.currentItem);
    },
    thongTinDslamShelf: async function () {
      this.SetButton(1)
      this.currentItem = {
        itemID: 0,
        framedsl_id: this.framedsl_id,
        itemType: ThietBi.PRE_DSLAM_FRAME_SHELF,
        action: ACTION_NEW,
      };
      this.$refs.formDsLamShelf.bindData(this.currentItem);
    },
    thongTinDslamCard: async function () {
      this.currentItem = {
        itemID: 0,
        shelfdsl_id: this.shelfdsl_id,
        vitri: 1,
        itemType: ThietBi.PRE_DSLAM_FRAME_SHELF_CARD,
        action: ACTION_NEW,
      };
      await this.$refs.formDsLamCard.bindData(this.currentItem);
    },
    thongTinModule: async function () {
      this.SetButton(1)
      if(this.currentItem.itemType == ThietBi.PRE_DSLAM_FRAME_SHELF_CARD) {
        this.currentItem = {
          itemID: 0,
          carddsl_id: this.carddsl_id,
          itemType: ThietBi.PRE_DSLAM_FRAME_SHELF_CARD_MODULE,
          action: ACTION_NEW,
        };
        await this.$refs.formDsLamModule.bindData(this.currentItem);
      }
      else if(this.currentItem.itemType == ThietBi.PRE_MANE_CARD) {
        this.currentItem = {
          itemID: 0,
          cardmane_id: this.cardmane_id,
          itemType: ThietBi.PRE_MANE_CARD_MODULE,
          action: ACTION_NEW,
        };
        await this.$refs.formManeModule.bindData(this.currentItem);
      }
    },
    thongTinGPON: function () {
      this.SetButton(1);
      this.currentItem = {
        itemID: 0,
        loaiTb_id: 3,
        turack_id: this.turack_id,
        itemType: ThietBi.PRE_GPON,
        action: ACTION_NEW,
        slotType: ThietBi.PRE_GPON_SLOT,
      };
      this.$refs.formDsLamSwitchGpon.setTuRack_id(this.currentItem);
    },
    thongTinSwitch: function () {
      this.SetButton(1);
      this.currentItem = {
        itemID: 0,
        loaiTb_id: 2,
        turack_id: this.turack_id,
        itemType: ThietBi.PRE_SWITCH,
        action: ACTION_NEW,
        slotType: ThietBi.PRE_SWITCH_PORT,
      };
      this.$refs.formDsLamSwitchGpon.setTuRack_id(this.currentItem);
    },
    thongTinManE: function () {
      this.SetButton(1);
      this.currentItem = {
        itemID: 0,
        loaiTb_id: 17,
        turack_id: this.turack_id,
        itemType: ThietBi.PRE_MANE,
        action: ACTION_NEW,
        slotType: ThietBi.PRE_MANE_SLOT,
      };
      this.$refs.formDsLamSwitchGpon.setTuRack_id(this.currentItem);
    },
    thongTinTuRack: async function () {
      this.SetButton(1);
      this.currentItem = {
        itemID: 0,
        nhatram_id: this.donvi_id,
        itemType: ThietBi.PRE_TURACK,
        action: ACTION_NEW,
      };
      await this.$refs.formTuRack.bindData(this.currentItem);
    },
    //#endregion
    //#region "Hàm show node"
    show_TuRack: async function (id) {
      let itemData = await this.layTuRack(id);
      if (itemData != null) {
        this.currentItem = {
          // donvi_id: 6,
          // toql_id: 179,
          turack_id: itemData.TURACK_ID,
          kieu: itemData.KIEUTU,
          nhatram_id: itemData.DONVI_ID,
        };
        // let obj= { donvi_id: 6,
        //   toql_id: 179,
        //   nhatram_id: 674,
        //   kieu: 2,
        //   turack_id: 1502 }

        await this.$refs.formBras.bindData(args.itemData.itemID);
      }
    },
    lapCard: async function () {
      this.SetButton(1)
      this.currentItem = {
        itemID: 0,
        shelfdsl_id: this.shelfdsl_id,
        vitri: this.vitriShlefdsl,
        itemType: ThietBi.PRE_DSLAM_FRAME_SHELF_CARD,
        action: ACTION_VIEW,
      };
      await this.$refs.formDsLamCard.bindData(this.currentItem);
    },
    show_Odf: async function (id) {
      let itemData = await this.layOdf(id);
    },
    show_Bras: async function (id) {
      let itemData = await this.layBras(id);
    },
    show_Bras_Card: async function (id) {
      let itemData = await this.layBrasCard(id);
    },
    show_Bras_Card_Port: async function (id) {
      let itemData = await this.layBrasPort(id);
    },
    show_Gpon: async function (id) {
      let itemData = await this.layDslamGponSwitch(id, 3);
    },
    show_Gpon_Card: async function (id) {
      let itemData = await this.layGponCard(id);
    },
    show_Gpon_Card_Port: async function (id) {
      let itemData = await this.layGponPort(id);
    },
    show_Mane: async function (id) {
      let itemData = await this.layDslamGponSwitch(id, 17);
    },
    show_Mane_Card: async function (id) {
      let itemData = await this.layManeCard(id);
    },
    show_Mane_Card_Port: async function (id) {
      let itemData = await this.layManePort(id);
    },
    show_Mane_Card_Module: async function (id) {
      let itemData = await this.layManeModule(id);
    },
    show_Mane_Card_Module_Port: async function (id) {
      let itemData = await this.layManeModulePort(id);
    },
    show_Switch: async function (id) {
      let itemData = await this.layDslamGponSwitch(id, 2);
    },
    show_Switch_Port: async function (id) {
      let itemData = await this.laySwitchPort(id);
    },
    show_Switch_Card: async function (id) {
      let itemData = await this.laySwitchCard(id);
    },
    show_Switch_Card_Port: async function (id) {
      let itemData = await this.laySwitchCardPort(id);
    },
    show_Dslam: async function (id) {
      let itemData = await this.layDslamGponSwitch(id, 1);
    },
    show_Dslam_Frame: async function (id) {
      let itemData = await this.layDslamFrame(id);
    },
    show_Dslam_Frame_Shelf: async function (id) {
      let itemData = await this.layDslamShelf(id);
    },
    show_Dslam_Frame_Shelf_Card: async function (id) {
      let itemData = await this.layDslamCard(id);
    },
    show_Dslam_Frame_Shelf_Card_Port: async function (id) {
      let itemData = await this.layDslamPort(id);
    },
    show_Dslam_Frame_Shelf_Card_Module: async function (id) {
      let itemData = await this.layDslamModule(id);
    },
    show_Dslam_Frame_Shelf_Card_Module_Port: async function (id) {
      let itemData = await this.layDslamModulePort(id);
    },
    show_Frame: async function (id) {
      let itemData = await this.layFrameTruyenDan(id);
    },
    show_Frame_Card: async function (id) {
      let itemData = await this.layFrameCard(id);
    },
    //#endregion

    //#region "Hàm lấy api đối tượng theo id"
    layTuRack: async function (id) {
      try {
        let result = await this.$root.context.get(
          "/web-ecms/danhmuc/turack/chitiet",
          {
            turack_id: id,
          }
        );
        return result.data;
      } catch (e) {
        return null;
      }
    },
    layBras: async function (id) {
      try {
        let result = await this.$root.context.get(
          "/web-ecms/thietbi-truyendan/bras-theo-id/" + id
        );
        return result.data;
      } catch (e) {
        return null;
      }
    },
    layBrasCard: async function (id) {
      try {
        let result = await this.$root.context.get(
          "/web-ecms/thietbi-truyendan/bras-card-theo-id/" + id
        );
        return result.data;
      } catch (e) {
        return null;
      }
    },
    layBrasPort: async function (id) {
      try {
        let result = await this.$root.context.get(
          "/web-ecms/thietbi-truyendan/bras-port-theo-id/" + id
        );
        return result.data;
      } catch (e) {
        return null;
      }
    },
    layDslamGponSwitch: async function (id, loaitbi) {
      try {
        let result = await this.$root.context.get(
          "/web-ecms/thietbi-truyendan/dslam-theo-id",
          {
            id: id,
            loaitbi: loaitbi,
          }
        );
        return result.data;
      } catch (e) {
        return null;
      }
    },
    layGponCard: async function (id) {
      try {
        let result = await this.$root.context.get(
          "/web-ecms/thietbi-truyendan/gpon-card-theo-id/" + id
        );
        return result.data;
      } catch (e) {
        return null;
      }
    },
    layGponPort: async function (id) {
      try {
        let result = await this.$root.context.get(
          "/web-ecms/thietbi-truyendan/gpon-port-theo-id/" + id
        );
        return result.data;
      } catch (e) {
        return null;
      }
    },
    layManeCard: async function (id) {
      try {
        let result = await this.$root.context.get(
          "/web-ecms/thietbi-truyendan/mane-card-theo-id/" + id
        );
        return result.data;
      } catch (e) {
        return null;
      }
    },
    layManePort: async function (id) {
      try {
        let result = await this.$root.context.get(
          "/web-ecms/thietbi-truyendan/mane-port-theo-id/" + id
        );
        return result.data;
      } catch (e) {
        return null;
      }
    },
    layManeModule: async function (id) {
      try {
        let result = await this.$root.context.get(
          "/web-ecms/thietbi-truyendan/mane-module-theo-id/" + id
        );
        return result.data;
      } catch (e) {
        return null;
      }
    },
    layManeModulePort: async function (id) {
      try {
        let result = await this.$root.context.get(
          "/web-ecms/thietbi-truyendan/mane-module-port-theo-id/" + id
        );
        return result.data;
      } catch (e) {
        return null;
      }
    },
    laySwitchCard: async function (id) {
      try {
        let result = await this.$root.context.get(
          "/web-ecms/thietbi-truyendan/switch-card-theo-id/" + id
        );
        return result.data;
      } catch (e) {
        return null;
      }
    },
    laySwitchPort: async function (id) {
      try {
        let result = await this.$root.context.get(
          "/web-ecms/thietbi-truyendan/switch-port-theo-id/" + id
        );
        return result.data;
      } catch (e) {
        return null;
      }
    },
    laySwitchCardPort: async function (id) {
      try {
        let result = await this.$root.context.get(
          "/web-ecms/thietbi-truyendan/switch-card-port-theo-id/" + id
        );
        return result.data;
      } catch (e) {
        return null;
      }
    },
    layDslamFrame: async function (id) {
      try {
        let result = await this.$root.context.get(
          "/web-ecms/thietbi-truyendan/dslam-frame-theo-id/" + id
        );
        return result.data;
      } catch (e) {
        return null;
      }
    },
    layDslamShelf: async function (id) {
      try {
        let result = await this.$root.context.get(
          "/web-ecms/thietbi-truyendan/dslam-shelf-theo-id/" + id
        );
        return result.data;
      } catch (e) {
        return null;
      }
    },
    layDslamCard: async function (id) {
      try {
        let result = await this.$root.context.get(
          "/web-ecms/thietbi-truyendan/dslam-card-theo-id/" + id
        );
        return result.data;
      } catch (e) {
        return null;
      }
    },
    layDslamPort: async function (id) {
      try {
        let result = await this.$root.context.get(
          "/web-ecms/thietbi-truyendan/dslam-port-theo-id/" + id
        );
        return result.data;
      } catch (e) {
        return null;
      }
    },
    layDslamModule: async function (id) {
      try {
        let result = await this.$root.context.get(
          "/web-ecms/thietbi-truyendan/dslam-module-theo-id/" + id
        );
        return result.data;
      } catch (e) {
        return null;
      }
    },
    layDslamModulePort: async function (id) {
      try {
        let result = await this.$root.context.get(
          "/web-ecms/thietbi-truyendan/dslam-module-port-theo-id/" + id
        );
        return result.data;
      } catch (e) {
        return null;
      }
    },
    layFrameTruyenDan: async function (id) {
      try {
        let result = await this.$root.context.get(
          "/web-ecms/thietbi-truyendan/frame-td-theo-id/" + id
        );
        return result.data;
      } catch (e) {
        return null;
      }
    },
    layFrameCard: async function (id) {
      try {
        let result = await this.$root.context.get(
          "/web-ecms/thietbi-truyendan/frame-card-theo-id/" + id
        );
        return result.data;
      } catch (e) {
        return null;
      }
    },
    layOdf: async function (id) {
      try {
        let result = await this.$root.context.get(
          "/web-ecms/thietbi-truyendan/odf-theo-id/" + id
        );
        return result.data;
      } catch (e) {
        return null;
      }
    },
    checkPortSwitch: async function (id) {
      try {
        let result = await this.$root.context.get(
          "/web-ecms/thietbi-truyendan/check-port-switch/" + id
        );
        return result.data.RS;
      } catch (e) {
        return 0;
      }
    },
    //#endregion
    loadTree: async function (data) {
      let arrTuRack = data.turack_ids;
      let getTuRack = arrTuRack.find(v => v == this.turack_id);
      await this.refreshTreeFromCurrentItem(false, this.turack_id);
      await this.$refs.caySoDoThietBi.refreshTreeFromDonVi(
        data.tramvt_id,
        this.currentItem.itemType,
        this.currentItem.itemID,
        this.currentItem.itemID
      );
      await this.refreshTreeFromCurrentItem(true, getTuRack ? getTuRack : arrTuRack[0]);
      this.$bvModal.hide("popupChuyenTram");
    },
    formPortGpon_updatedNodeGponPort: function (data) {
      this.refreshTreeFromCurrentItem(true);
    },
    
  },
  watch: {},
  async mounted() {
    try {
      this.loading(true);
      let vm = this;
      await vm.$refs["caySoDoThietBi"].loadNodes();
      EventBus.$on("TrangThaiHuongKetNoi", (data) => {
        console.log('TrangThaiHuongKetNoi',data)
        let turack_1 = data.thietbi.turack_1;
        let turack_2 = data.thietbi.turack_2;
        this.refreshTree(
        this.currentItem.itemType,
        this.currentItem.itemID,
        false,
        turack_1 
      );
      this.refreshTree(
        this.currentItem.itemType,
        this.currentItem.itemID,
        false,
        turack_2 
      );
        this.refreshTree(
        this.currentItem.itemType,
        this.currentItem.itemID,
        true,
        this.turack_id 
      );
      });
    } catch (error) {
    } finally {
      this.loading(false);
    }
  },
  destroyed() {
    EventBus.$off("TrangThaiHuongKetNoi");
  },
};
</script>
<style>
a.disable-btn {
  pointer-events: none;
  cursor: default;
  color: #6c757d !important;
}
</style>
