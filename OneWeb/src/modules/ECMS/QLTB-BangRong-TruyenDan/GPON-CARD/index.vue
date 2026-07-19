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
      dsTinhKhongTaoPort: ['AGG', 'BDG', 'BLU', 'BPC', 'BTE', 'CMU', 'CTO', 'DNI', 'HGG', 'HGI', 'KGG', 'LAN', 'LCI', 'LCU', 'LDG', 'LSN', 'STG', 'TBH', 'TGG', 'TNH', 'TVH', 'VLG', 'VTU']
    };
  },
  computed: {
    isValidViTri: function () {
      // let result = false;
      // if (!this.checkIsEmpty(this.currentItem.VITRI)) {
      //   if (this.currentItem.VITRI >= 0) result = !this.checkViTri;
      // }
      // return result;
      return true;
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
      soPort: {
        required,
        numeric,
        minValue: minValue(0),
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
        "/web-ecms/danhmuc/dslam-card/get-nhomcard-by-loaitb_id/10"
      );
      return result.data;
    },
    getDsLoaiCard: async function (nhomCardID) {
      let result = await this.$root.context.get(
        "/web-ecms/danhmuc/dslam-card/get-loaicard-by-nhomcardid",
        {
          nhomcard_id: nhomCardID,
          loaitb_id: 10,
        }
      );
      return result.data;
    },
    getDsPortCardGpon: async function () {
      let result = await this.$root.context.post(
        "/web-ecms/quanlythietbi/getDsPortCardGpon",
        {
          id: this.currentItem.CARDGP_ID,
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
    getThongTinCardGpon: async function (id) {
      let result = await this.$root.context.post(
        "/web-ecms/quanlythietbi/getThongTinCardGpon",
        {
          id: id,
        }
      );
      return result.data;
    },
    themCard: async function () {
      if (this.currentItem.TEN_CARD == "") {
        let loaiCard = this.dsLoaiCard.find(
          (x) => (x.LOAICARD_ID == this.currentItem.LOAICARD_ID)
        );
        if (loaiCard != null) this.currentItem.TEN_CARD = loaiCard.LOAI_CARD;
      }
      let maTinh = this.$root.token.getMaTinh();
      let result = await this.$root.context.post(
        "/web-ecms/quanlythietbi/themThongTinCardGpon",
        {
          gpon_id: this.currentItem.GPON_ID,
          loaicard_id: this.currentItem.LOAICARD_ID,
          partnumber: this.currentItem.PARTNUMBER,
          serial: this.currentItem.SERIAL,
          ten_card: this.currentItem.TEN_CARD,
          vitri: this.currentItem.VITRI,
          istaoportlg: !this.dsTinhKhongTaoPort.includes(maTinh) && this.currentItem.nhomCardID == 1
        }
      );
      return result.data;
    },
    capNhatCard: async function () {
      try {
        let maTinh = this.$root.token.getMaTinh();
        let result = await this.$root.context.post(
          "/web-ecms/quanlythietbi/capNhatThongTinCardGpon",
          {
            cardgp_id: this.currentItem.CARDGP_ID,
            gpon_id: this.currentItem.GPON_ID,
            loaicard_id: this.currentItem.LOAICARD_ID,
            partnumber: this.currentItem.PARTNUMBER,
            serial: this.currentItem.SERIAL,
            ten_card: this.currentItem.TEN_CARD,
            vitri: this.currentItem.VITRI,
            istaoportlg: !this.dsTinhKhongTaoPort.includes(maTinh) && this.currentItem.nhomCardID == 1
          }
        );
      return result.data;
      } catch (error) {
        this.$root.toastSuccess(error.response.data.message_detail ? error.response.data.message_detail : "Cập nhật thất bại");
      }
      
    },
    xoaCard: async function () {
      let result = await this.$root.context.post(
        "/web-ecms/quanlythietbi/xoaThongTinCardGpon",
        {
          id: this.currentItem.CARDGP_ID,
        }
      );
      return result.data;
    },
    kiemTraViTri: async function () {
      this.checkViTri = false;
      let result = await this.$root.context.post(
        "/web-ecms/quanlythietbi/kiemTraVitriCardGpon",
        {
          gpon_id: this.currentItem.GPON_ID,
          vitri: this.currentItem.VITRI,
        }
      );
      if (!(result.data == null || result.data == undefined))
        this.checkViTri = result.data.result;
    },
    kiemTraSerial: async function () {
      this.checkSerial = false;
      if (this.currentItem.SERIAL != this.currentItem.SERIAL_CU) {
        let result = await this.$root.context.post(
          "/web-ecms/quanlythietbi/kiemTraSerialThietBi",
          {
            serial: this.currentItem.SERIAL,
          }
        );
        if (!(result.data == null || result.data == undefined))
          this.checkSerial = result.data.result;
      }
    },
    kiemTraThayCard: async function () {
      this.checkThayCard = false;
      if (this.currentItem.soPort < this.currentItem.soPortCu) {
        let result = await this.$root.context.post(
          "/web-ecms/quanlythietbi/kiemTraThayCardGpon",
          {
            cardgp_id: this.currentItem.CARDGP_ID,
            soport_moi: this.currentItem.soPort,
          }
        );
        if (!(result.data == null || result.data == undefined))
          this.checkThayCard = result.data.result;
      }
    },
    kiemTraXoaCard: async function () {
      let result = await this.$root.context.post(
        "/web-ecms/quanlythietbi/kiemTraXoaCardGpon",
        {
          id: this.currentItem.CARDGP_ID,
        }
      );
      if (!(result.data == null || result.data == undefined))
        return !result.data.result;
      else return false;
    },
    kiemTraPortCardGponDangSuDung: async function () {
      let result = await this.$root.context.post(
        "/web-ecms/quanlythietbi/kiemTraPortCardGponDangSuDung",
        {
          id: this.currentItem.CARDGP_ID,
        }
      );
      if (!(result.data == null || result.data == undefined))
        return result.data.result;
      else return 0;
    },
    kiemTraRackShelf: async function(dslam_id) {
      let rsDsDaGan = await this.$root.context.post('/web-ecms/quanlythietbi/getDsRackDaGan', {id: dslam_id});
      return rsDsDaGan.data.length > 0;
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
        this.currentItem.portBD = loaiCard.PORT_BD;
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
    themMoi: function (gponID, viTri) {
      let item = this.createNewItem();
      item.GPON_ID = gponID;
      if(!this.checkIsEmpty(viTri))
        item.VITRI = viTri;
      this.bindItem(item);
    },
    bindData: async function (itemID) {
      let itemData = await this.getThongTinCardGpon(itemID);
      await this.bindItem(itemData);
    },
    bindItem: async function (itemData) {
      this.dsNhomCard = [];
      this.dsLoaiCard = [];
      this.currentItem = this.createNewItem();
      await this.bindNhomCard();
      if (!(itemData == null || itemData == undefined)) {
        this.currentItem.GPON_ID = itemData.GPON_ID;
        this.currentItem.VITRI = itemData.VITRI;
        if (itemData.CARDGP_ID > 0) {
          this.currentItem.CARDGP_ID = itemData.CARDGP_ID;
          this.currentItem.LOAICARD_ID = itemData.LOAICARD_ID;
          this.currentItem.PARTNUMBER = itemData.PARTNUMBER;
          this.currentItem.SERIAL = itemData.SERIAL;
          this.currentItem.SERIAL_CU = itemData.SERIAL;
          this.currentItem.TEN_CARD = itemData.TEN_CARD;
          let loaiCard = await this.getLoaiCard(this.currentItem.LOAICARD_ID);
          if (loaiCard != null) {
            this.currentItem.nhomCardID = loaiCard.NHOMCARD_ID;
            await this.bindLoaiCard();
            this.currentItem.LOAICARD_ID = loaiCard.LOAICARD_ID;
            this.currentItem.soPort = loaiCard.SO_PORT;
            this.currentItem.soPortCu = loaiCard.SO_PORT;
            this.currentItem.portBD = loaiCard.PORT_BD;
          }
          this.currentItem.dangSuDung = await this.kiemTraPortCardGponDangSuDung();
        }
      }
    },
    formNhapMoi: function () {
      this.bindItem(null);
    },
    formGhiLai: async function () {
      try {
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
          if (this.$v.currentItem.soPort.$invalid) {
            if (msg != "") msg += "\n";
            msg += "Card này chưa được thiết lập số port !";
          }
          this.$root.toastError(msg);
        } else {
          let check_rack_shelf = await this.kiemTraRackShelf(this.currentItem.GPON_ID);
          if(this.$root.token.getMaTinh() == 'HNI' && !check_rack_shelf) {
            this.$root.toastError("Thiết bị chưa được gán rack-shelf \n Không thể thực hiện thao tác này !");
            return;
          }
          await this.kiemTraSerial();
          if (this.checkSerial) {
            this.$root.toastError("Serial này đã được sử dụng !");
          } else {
            if (this.currentItem.CARDGP_ID == 0) {
              await this.kiemTraViTri();
              if (this.checkViTri) {
                this.$root.toastError(
                  "Tại vị trí " +
                    this.currentItem.VITRI +
                    " của thiết bị có ID = " +
                    this.currentItem.GPON_ID +
                    " đã được lắp card"
                );
              } else {
                let rs = await this.themCard();
                if (rs != null) {
                  this.currentItem.CARDGP_ID = rs.CARDGP_ID;
                  if (this.currentItem.soPort > 0)
                    this.currentItem.ports = await this.getDsPortCardGpon();
                  else this.currentItem.ports = [];
                  result = true;
                  this.$root.toastSuccess("Thêm mới thành công");
                }
              }
            } else {
              await this.kiemTraThayCard();
              if (this.checkThayCard) {
                this.$root.toastError(
                  "Card cũ đã được gán port logic ở vị trí lớn hơn card mới !"
                );
              } else {
                let rs = await this.capNhatCard();
                if (rs.result) {
                  if (this.currentItem.soPort > 0)
                    this.currentItem.ports = await this.getDsPortCardGpon();
                  else this.currentItem.ports = [];
                  result = true;
                  this.$root.toastSuccess("Cập nhật thành công");
                }
              }
            }
          }
        }
        return result;
      } catch (error) {
        console.log(error);
      }
      
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
          this.currentItem.CARDGP_ID == undefined ||
          this.currentItem.CARDGP_ID == 0
        )
      ) {
        let ck = await this.kiemTraXoaCard();
        if (ck) {
          let rs = await this.xoaCard();
          if (rs.result) {
            this.currentItem = this.createNewItem();
            result = true;
            this.$root.toastSuccess("Xóa thành công");
          }
        } else {
          this.$root.toastError(
            "Card này đã có port được ánh xạ logic. Không thể xóa !"
          );
        }
      } else {
        this.$root.toastError("Bạn chưa chọn thiết bị");
      }
      return result;
    },
    createNewItem: function () {
      return {
        GPON_ID: 0,
        CARDGP_ID: 0,
        LOAICARD_ID: 0,
        PARTNUMBER: "",
        SERIAL: "",
        SERIAL_CU: "",
        VITRI: 0,
        TEN_CARD: "",
        soPort: 0,
        soPortCu: 0,
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
