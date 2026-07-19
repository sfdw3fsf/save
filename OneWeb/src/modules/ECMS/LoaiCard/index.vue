<template src="./index.html"></template>
import {numeric, required} from 'vuelidate/lib/validators'
<script>
import { maxLength, numeric, required } from "vuelidate/lib/validators";
import BssRequiredMarker from "@/components/BssRequiredMarker";

export default {
  components: { BssRequiredMarker },
  data: function () {
    return {
      dsLoaiCard: [],
      dsLoaiTbi: [],
      dsHangSx: [],
      dsNhomCard: [],
      loaicardInput: "",
      cboNhomcard: null,
      soportInput: "",
      cboLoaiTbi: null,
      portBdInput: "",
      cboHangSx: null,
      kieumoduleInput: "0",
      loaiCardChiTiet: null,
      modalXoa: false,

      isDisableBtnNhapMoi: false,
      isDisableBtnGhiLai: false,
      isDisableBtnHuyBo: false,
      isDisableBtnXoa: false,
      totalItems: 0
    };
  },
  created: async function () {
    await this.initForm()
  },
  watch: {},
  methods: {
    async initForm() {
      try {
        this.loading(true);
        //await this.getDsLoaiCard(1, 20, true);
        await this.updateDsLoaiCardCount();
        await this.getDsLoaiTbi();
        await this.getDsHangSx();
        await this.getDsNhomCard();
      } catch (e) {
        this.$toast.error(e);
      } finally {
        this.loading(false);
      }
    },
    grid_PageChanged: async function (args) {
      this.$root.showLoading(true);
      try {
        await this.getDsLoaiCard(args.pageIndex, args.pageSize);
      } finally {
        this.$root.showLoading(false);
      }
    },
    getDsLoaiCard: async function (pageIndex, pageSize) {
      var rs = await this.$root.context.post(
        "/web-ecms/danhmuc/loaicard/lay-ds-loaicard-paging",
        {
          pageIndex: pageIndex,
          pageSize: pageSize,
        }
      );
      if (rs.data.allItems != this.totalItems) this.totalItems = rs.data.allItems;
      this.dsLoaiCard = rs.data.pageItems;
    },
    updateDsLoaiCardCount: async function () {
      var rs = await this.$root.context.post(
        "/web-ecms/danhmuc/loaicard/lay-ds-loaicard-paging",
        {
          pageIndex: 0,
          pageSize: 1,
        }
      );
      if (rs.data.allItems != -9999) {
        if (this.totalItems == rs.data.allItems) this.$refs.gridLoaiCard.reloadCurrentPage()
        this.totalItems = rs.data.allItems;
      } else {
        let total = 0
        if (this.totalItems == total) this.$refs.gridLoaiCard.reloadCurrentPage()
        this.totalItems = total;
        this.dsLoaiCard = []
      }
      // this.totalItems = rs.data.allItems;
    },
    getDsLoaiTbi: async function () {
      var rs = await this.$root.context.get(
        "/web-ecms/danhmuc/loaitbi/danhsach"
      );
      this.dsLoaiTbi = rs.data;
    },
    getDsNhomCard: async function () {
      var rs = await this.$root.context.get(
        "/web-ecms/danhmuc/loaicard/lay-ds-nhomcard"
      );
      this.dsNhomCard = rs.data;
    },
    getDsHangSx: async function () {
      var rs = await this.$root.context.get(
        "/web-ecms/danhmuc/HangSX/DanhSach"
      );
      this.dsHangSx = rs.data;
    },
    grid1_selectedRowChanged(dataItem) {
      //change button status
      this.statusBtn(false, false, false, false);
      this.onClickChiTietLoaiCard(dataItem);
    },
    onClickChiTietLoaiCard(item) {
      if (item) {
        this.loaicardInput = item.LOAI_CARD;
        this.soportInput = item.SO_PORT;
        this.portBdInput = item.PORT_BD;
        this.cboNhomcard = item.NHOMCARD_ID;
        this.cboLoaiTbi = item.LOAITBI_ID;
        this.cboHangSx = item.HANGSX_ID;
        this.kieumoduleInput = item.KIEU_MODULE;
        this.loaiCardChiTiet = item;
      }
    },
    resetField() {
      this.loaicardInput = "";
      this.soportInput = "";
      this.portBdInput = "";
      this.cboNhomcard =
        this.dsNhomCard.length > 0 ? this.dsNhomCard[0].NHOMCARD_ID : "";
      this.cboLoaiTbi =
        this.dsLoaiTbi.length > 0 ? this.dsLoaiTbi[0].LOAITBI_ID : "";
      this.cboHangSx =
        this.dsHangSx.length > 0 ? this.dsHangSx[0].HANGSX_ID : "";
      this.kieumoduleInput = "0";
      this.loaiCardChiTiet = null;
    },
    addLoaiCard() {
      this.$v.$reset();
      //change button status
      this.statusBtn(true, false, false, true);
      //reset input,cbo
      this.resetField();
    },
    async saveLoaiCard() {
      //change button status
      //validate
      // if (this.loaicardInput.length == 0){
      //   this.$toast.error("Loại card không được để trống !")
      //   return
      // }
      //them moi loaicard
      if (this.loaiCardChiTiet == null && this.isDisableBtnNhapMoi == true) {
        let loaiCardObj = new Object();
        loaiCardObj.loaicard = this.loaicardInput;
        loaiCardObj.soPort = this.soportInput;
        loaiCardObj.portBd = this.portBdInput;
        loaiCardObj.loaitbiId = this.cboLoaiTbi;
        loaiCardObj.hangSxId = this.cboHangSx;
        loaiCardObj.kieuModule = this.kieumoduleInput;
        //validate data
        if (this.validateData(loaiCardObj)) return;
        this.loading(true);
        //them nhom card thiet bi
        let nhomCardTbiObj = new Object();
        nhomCardTbiObj.nhomcardId = this.cboNhomcard ? this.cboNhomcard : null;
        nhomCardTbiObj.loaitbiId = this.cboLoaiTbi ? this.cboLoaiTbi : null;

        await this.$root.context
          .post("/web-ecms/danhmuc/loaicard/tao-nhomcardtbi", nhomCardTbiObj)
          .then(async (res) => {
            if (res.error_code == "BSS-00000000" && res.error == "200") {
              loaiCardObj.cardTbiId = res.data.CARDTBI_ID;
              // them moi loaicard
              this.$root.context
                .post("/web-ecms/danhmuc/loaicard/tao-loaicard", loaiCardObj)
                .then((response) => {
                  if (
                    response.error_code == "BSS-00000000" &&
                    response.error == "200"
                  ) {
                    // this.dsLoaiCard.unshift(response.data);
                    this.$toast.success("Thêm mới thành công");
                    // this.dsLoaiCard = [...this.dsLoaiCard];
                  }
                })
                .catch((e) => {
                  let error = e.response.data.message_detail.split(":")[1];
                  this.$toast.error(error);
                })
                .finally(() => this.loading(false));
            }
          })
          .catch((e) => {
            // let error = e.response.data.message_detail.split(":")[1];
            this.$toast.error(e.response.data.message_detail);
          })
          .finally(() => this.loading(false));
      } else {
        //cap nhat loaicard
        let loaiCardObj = new Object();
        loaiCardObj.loaicardId = this.loaiCardChiTiet.LOAICARD_ID;
        loaiCardObj.loaicard = this.loaicardInput;
        loaiCardObj.soPort = this.soportInput;
        loaiCardObj.portBd = this.portBdInput;
        loaiCardObj.loaitbiId = this.cboLoaiTbi;
        loaiCardObj.hangSxId = this.cboHangSx;
        loaiCardObj.kieuModule = this.kieumoduleInput;
        //validate data
        if (this.validateData(loaiCardObj)) return;
        this.loading(true);
        //them nhom card thiet bi
        let nhomCardTbiObj = new Object();
        nhomCardTbiObj.nhomcardId = this.cboNhomcard ? this.cboNhomcard : null;
        nhomCardTbiObj.loaitbiId = this.cboLoaiTbi ? this.cboLoaiTbi : null;

        await this.$root.context
          .post("/web-ecms/danhmuc/loaicard/tao-nhomcardtbi", nhomCardTbiObj)
          .then((res) => {
            if (res.error_code == "BSS-00000000" && res.error == "200") {
              loaiCardObj.cardTbiId = res.data.CARDTBI_ID;
              //update loaicard
              this.$root.context
                .post(
                  "/web-ecms/danhmuc/loaicard/capnhat-loaicard",
                  loaiCardObj
                )
                .then((response) => {
                  if (
                    response.error_code == "BSS-00000000" &&
                    res.error == "200"
                  ) {
                    // this.loaiCardChiTiet = response.data;
                    // cap nhat ds
                    // let idx = this.dsLoaiCard.findIndex(item => item.LOAICARD_ID == this.loaiCardChiTiet.LOAICARD_ID)
                    // if (idx > -1)
                    //   this.dsLoaiCard[idx] = response.data
                    // this.dsLoaiCard = [...this.dsLoaiCard]
                    //this.getDsLoaiCard()
                    this.$toast.success("Cập nhật loại card thành công !");
                  }
                })
                .catch((e) => {
                  this.$toast.error(e.response.message_detail);
                });
            }
          })
          .catch((e) => {
            this.$toast.error(e.response.message_detail);
          })
          .finally(() => this.loading(false));
      }
      await this.initForm()
    },
    verifyDeleteLoaiCard: function () {
      this.modalXoa = !this.modalXoa;
    },
    async deleteLoaiCard() {
      if (this.loaiCardChiTiet != null) {
        let loaicardId = this.loaiCardChiTiet.LOAICARD_ID;
        this.loading(true);
        await this.$root.context
          .post(
            "/web-ecms/danhmuc/loaicard/xoa-loaicard?loaicardId=" + loaicardId
          )
          .then((res) => {
            if (res.error_code == "BSS-00000000" && res.error == "200") {
              this.dsLoaiCard = this.dsLoaiCard.filter(
                (item) => item.LOAICARD_ID !== this.loaiCardChiTiet.LOAICARD_ID
              );
              this.loaiCardChiTiet = null;
              this.resetField();
              this.$toast.success("Xóa thành công !");
            }
          })
          .catch((e) => {
            this.$toast.error(e.response.data.message_detail);
          })
          .finally(() => this.loading(false));
      }
      await this.initForm()
    },
    huyBo() {
      this.$v.$reset();
      //status btn
      this.statusBtn(false, false, false, false);
      //reset field
      this.loaicardInput = "";
      this.soportInput = "";
      this.portBdInput = "";
      this.cboNhomcard = "";
      this.cboLoaiTbi = "";
      this.cboHangSx = "";
      this.kieumoduleInput = "0";
      // get loai card chi tiet
      this.onClickChiTietLoaiCard(this.loaiCardChiTiet);
    },
    statusBtn(nhapMoi, ghiLai, huyBo, xoa) {
      (this.isDisableBtnNhapMoi = nhapMoi),
        (this.isDisableBtnGhiLai = ghiLai),
        (this.isDisableBtnHuyBo = huyBo),
        (this.isDisableBtnXoa = xoa);
    },
    validateData(data) {
      this.$v.$touch();
      let errorMessage = [];
      if (!data.loaicard.toString()) {
        this.$refs.loaiCard.focus();
        errorMessage.push("Trường Loại card không được trống.");
      }
      if (!this.cboNhomcard) {
        // this.$v.cboNhomcard.$touch()
        errorMessage.push("Trường Nhóm card chưa được chọn.");
      }
      if (this.$v.soportInput.$error) {
        this.$refs.soPort.focus();
        if (!data.soPort.toString()) {
          errorMessage.push("Trường Số port không được trống.");
        }
        if (parseInt(data.soPort, 10) < 0) {
          errorMessage.push("Trường Số port không được âm.");
        }
      }
      if (!data.loaitbiId) {
        errorMessage.push("Trường Loại thiết bị chưa được chọn.");
      }

      if (this.$v.portBdInput.$error) {
        this.$refs.portBd.focus();
        if (!data.portBd.toString()) {
          errorMessage.push("Trường Port bắt đầu không được trống.");
        }
        if (parseInt(data.portBd, 10) < 0) {
          errorMessage.push("Trường Port bắt đầu không được âm.");
        }
      }

      if (!data.hangSxId) {
        errorMessage.push("Trường Hãng sản xuất chưa được chọn.");
      }
      if (errorMessage.length > 0) {
        this.$toast.error(errorMessage.join("\n"));
        return true;
      } else {
        return false;
      }
    },
  },
  validations: {
    loaicardInput: { required },
    cboHangSx: { required },
    cboLoaiTbi: { required },
    cboNhomcard: { required },
    soportInput: {
      required,
      maxlength: maxLength(4),
      numeric,
    },
    portBdInput: {
      required,
      maxlength: maxLength(1),
      numeric,
    },
  },
  async mounted() {
    this.$store.dispatch("setHeader", {
      title: "Loại card",
    });
  },
};
</script>
<style>
.disable-btn:hover {
  background-color: white !important;
}

.disable-btn a {
  color: #6c757d !important;
}

.disable-btn a:hover {
  cursor: default;
  color: #6c757d;
}
.required:after {
  content: " *";
  color: red;
}
</style>
