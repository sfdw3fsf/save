<template src="./index.template.html"></template>
<script>
import { required, numeric, minValue } from "vuelidate/lib/validators";
export default {
  data: function () {
    return {
      currentItem: this.createNewItem(),
      dsNhomCard: [],
      dsLoaiCard: [],
      checkViTri: false,
      checkSerial: false,
      checkThayCard: false,
    };
  },
  computed: {
    isValidViTri: function () {
      let result = false;
      if (!this.checkIsEmpty(this.currentItem.VITRI)) {
        if (this.currentItem.VITRI >= 0) result = !this.checkViTri;
      }
      return result;
    },
    isValidSerial: function () {
      return !this.checkSerial;
    },
    isValidNhomCard: function () {
      let result = false;
      if (!this.checkIsEmpty(this.currentItem.nhomCardID)) {
        result = this.currentItem.nhomCardID > 0;
      }
      return result;
    },
    isValidLoaiCard: function () {
      let result = false;
      if (!this.checkIsEmpty(this.currentItem.LOAICARD_ID)) {
        result = this.currentItem.LOAICARD_ID > 0;
      }
      return result;
    },
  },
  validations: {
    currentItem: {
      nhomCardID: {
        required,
        numeric,
        minValue: minValue(1),
      },
      SERIAL: {
        required,
      },
      LOAICARD_ID: {
        required,
        numeric,
        minValue: minValue(1),
      },
    },
  },
  methods: {
    getDsNhomCard: async function () {
      let result = await this.$root.context.get(
        "/web-ecms/danhmuc/dslam-card/get-nhomcard-by-loaitb_id/21"
      );
      return result.data;
    },
    getDsLoaiCard: async function (nhomCardID) {
      let result = await this.$root.context.get(
        "/web-ecms/danhmuc/dslam-card/get-loaicard-by-nhomcardid",
        {
          nhomcard_id: nhomCardID,
          loaitb_id: 21,
        }
      );
      return result.data;
    },
    getDsPortCardMane: async function () {
      let result = await this.$root.context.post(
        "/web-ecms/quanlythietbi/getDsPortCardMane",
        {
          id: this.currentItem.CARDMANE_ID,
        }
      );
      return result.data;
    },
    getLoaiCard: async function (loaiCardID) {
      let result = await this.$root.context.get(
        "/web-ecms/danhmuc/dslam-card/get-loaicard-by-id/" +
          loaiCardID.toString()
      );
      return result.data;
    },
    getThongTinCardMane: async function (id) {
      let result = await this.$root.context.post(
        "/web-ecms/quanlythietbi/getThongTinCardMane",
        {
          id: id,
        }
      );
      return result.data;
    },
    themCard: async function () {
      let loaiCard = this.dsLoaiCard.find(
        (x) => (x.LOAICARD_ID == this.currentItem.LOAICARD_ID)
      );
      if (loaiCard != null) this.currentItem.TEN_CARD = loaiCard.LOAI_CARD;
      let result = await this.$root.context.post(
        "/web-ecms/quanlythietbi/themThongTinCardMane",
        {
          dslam_id: this.currentItem.DSLAM_ID,
          loaicard_id: this.currentItem.LOAICARD_ID,
          partnumber: this.currentItem.PARTNUMBER,
          serial: this.currentItem.SERIAL,
          ten_card: this.currentItem.TEN_CARD,
          vitri: this.currentItem.VITRI,
        }
      );
      return result.data;
    },
    capNhatCard: async function () {
      let loaiCard = this.dsLoaiCard.find(
        (x) => (x.LOAICARD_ID = this.currentItem.LOAICARD_ID)
      );
      if (loaiCard != null) this.currentItem.TEN_CARD = loaiCard.LOAI_CARD;
      let result = await this.$root.context.post(
        "/web-ecms/quanlythietbi/capNhatThongTinCardMane",
        {
          cardmane_id: this.currentItem.CARDMANE_ID,
          dslam_id: this.currentItem.DSLAM_ID,
          loaicard_id: this.currentItem.LOAICARD_ID,
          partnumber: this.currentItem.PARTNUMBER,
          serial: this.currentItem.SERIAL,
          ten_card: this.currentItem.TEN_CARD,
          vitri: this.currentItem.VITRI,
        }
      );
      return result.data;
    },
    xoaCard: async function () {
      let result = await this.$root.context.post(
        "/web-ecms/quanlythietbi/xoaThongTinCardMane",
        {
          id: this.currentItem.CARDMANE_ID,
        }
      );
      return result.data;
    },
    kiemTraPortCardManeDangSuDung: async function () {
      let result = await this.$root.context.post(
        "/web-ecms/quanlythietbi/kiemTraPortCardManeDangSuDung",
        {
          id: this.currentItem.CARDMANE_ID,
        }
      );
      if (!(result.data == null || result.data == undefined))
        return result.data.result;
      else return 0;
    },
    bindNhomCard: async function () {
      this.currentItem.nhomCardID = 0;
      this.dsNhomCard = await this.getDsNhomCard();
      if (!(this.dsNhomCard == null || this.dsNhomCard.length == 0)) {
        this.currentItem.nhomCardID = this.dsNhomCard[0].NHOMCARD_ID;
      }
      await this.bindLoaiCard();
    },
    bindLoaiCard: async function () {
      this.currentItem.LOAICARD_ID = 0;
      this.dsLoaiCard = await this.getDsLoaiCard(this.currentItem.nhomCardID);
      if (!(this.dsLoaiCard == null || this.dsLoaiCard.length == 0)) {
        this.currentItem.LOAICARD_ID = this.dsLoaiCard[0].LOAICARD_ID;
      }
      await this.bindSoPort();
    },
    bindSoPort: async function () {
      this.currentItem.soPort = 0;
      this.currentItem.portBD = 0;
      let loaiCard = await this.getLoaiCard(this.currentItem.LOAICARD_ID);
      if (loaiCard != null) {
        this.currentItem.soPort = loaiCard.SO_PORT;
        this.currentItem.portBD = this.checkIsNull(loaiCard.PORT_BD)
          ? 0
          : loaiCard.PORT_BD;
      }
    },
    selectNhomCard_OnChange: async function (args) {
      this.$root.showLoading(true);
      try {
        await this.bindLoaiCard();
      } catch (error) {
      } finally {
        this.$root.showLoading(false);
      }
    },
    selectLoaiCard_OnChange: async function (args) {
      this.$root.showLoading(true);
      try {
        await this.bindSoPort();
      } catch (error) {
      } finally {
        this.$root.showLoading(false);
      }
    },
    themMoi: function (daLamID, viTri) {
      let item = this.createNewItem();
      item.DSLAM_ID = daLamID;
      if (!this.checkIsEmpty(viTri)) item.VITRI = viTri;
      this.bindItem(item);
    },
    bindData: async function (itemID) {
      let itemData = await this.getThongTinCardMane(itemID);
      await this.bindItem(itemData);
    },
    bindItem: async function (itemData) {
      this.dsNhomCard = [];
      this.dsLoaiCard = [];
      this.currentItem = this.createNewItem();
      await this.bindNhomCard();
      if (!(itemData == null || itemData == undefined)) {
        this.currentItem.DSLAM_ID = itemData.DSLAM_ID;
        this.currentItem.VITRI = itemData.VITRI;
        if (itemData.CARDMANE_ID > 0) {
          this.currentItem.CARDMANE_ID = itemData.CARDMANE_ID;
          this.currentItem.LOAICARD_ID = itemData.LOAICARD_ID;
          this.currentItem.PARTNUMBER = itemData.PARTNUMBER;
          this.currentItem.SERIAL = itemData.SERIAL;
          this.currentItem.SERIAL_CU = itemData.SERIAL;
          this.currentItem.TEN_CARD = itemData.TEN_CARD;
          this.currentItem.dangSuDung =
            await this.kiemTraPortCardManeDangSuDung();
        }
      }
      let loaiCard = await this.getLoaiCard(this.currentItem.LOAICARD_ID);
      // if (loaiCard != null) {
      if (Object.keys(loaiCard).length > 0) {
        this.currentItem.nhomCardID = loaiCard.NHOMCARD_ID;
        await this.bindLoaiCard();
        this.currentItem.LOAICARD_ID = loaiCard.LOAICARD_ID;
        this.currentItem.soPort = loaiCard.SO_PORT;
        this.currentItem.portBD = this.checkIsNull(loaiCard.PORT_BD)
          ? 0
          : loaiCard.PORT_BD;
      }
    },
    formNhapMoi: function () {
      this.bindItem(null);
    },
    formGhiLai: async function () {
      let result = false;
      this.$v.$touch();
      if (this.$v.$invalid) {
        let msg = "";
        if (this.$v.currentItem.nhomCardID.$invalid) {
          msg += "Chưa chọn nhóm card";
        }
        if (this.$v.currentItem.LOAICARD_ID.$invalid) {
          if (msg != "") msg += "\n";
          msg += "Chưa chọn loại card!";
        }
        if (this.$v.currentItem.SERIAL.$invalid) {
          if (msg != "") msg += "\n";
          msg += "Chưa nhập serial cho card !";
        }
        this.$root.toastError(msg);
      } else {
        if (this.currentItem.CARDMANE_ID == 0) {
          let rs = await this.themCard();
          if (rs != null) {
            this.currentItem.CARDMANE_ID = rs.CARDMANE_ID;
            if (this.currentItem.soPort > 0)
              this.currentItem.ports = await this.getDsPortCardMane();
            else this.currentItem.ports = [];
            result = true;
            this.$root.toastSuccess("Thêm mới thành công");
          }
        } else {
          let rs = await this.capNhatCard();
          if (rs.result) {
            if (this.currentItem.soPort > 0)
              this.currentItem.ports = await this.getDsPortCardMane();
            else this.currentItem.ports = [];
            result = true;
            this.$root.toastSuccess("Cập nhật thành công");
          }
        }
      }
      return result;
    },
    formHuy: function () {
      this.currentItem.SERIAL = "";
      this.currentItem.TEN_CARD = "";
      this.currentItem.PARTNUMBER = "";
      this.currentItem.VITRI = 0;
    },
    formXoa: async function () {
      let result = false;
      if (
        !(
          this.currentItem.CARDMANE_ID == undefined ||
          this.currentItem.CARDMANE_ID == 0
        )
      ) {
        let rs = await this.xoaCard();
        if (rs.result) {
          this.currentItem = this.createNewItem();
          result = true;
          this.$root.toastSuccess("Xóa thành công");
        }
      } else {
        this.$root.toastError("Bạn chưa chọn thiết bị");
      }
      return result;
    },
    createNewItem: function () {
      return {
        DSLAM_ID: 0,
        CARDMANE_ID: 0,
        LOAICARD_ID: 0,
        PARTNUMBER: "",
        SERIAL: "",
        VITRI: 0,
        TEN_CARD: "",
        soPort: 0,
        portBD: 0,
        nhomCardID: 0,
        ports: [],
        dangSuDung: 0,
      };
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
    txtSerial_change: function (args) {
      this.checkSerial = false;
    },
    txtViTri_change: function (args) {
      this.checkViTri = false;
    },
  },
};
</script>
