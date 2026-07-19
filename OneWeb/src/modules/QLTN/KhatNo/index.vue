<template src="./index.template.html"></template>
<style src="./style.scss" scoped></style>
<script>
import {
  maxLength,
  numeric,
  required,
  minValue
} from "vuelidate/lib/validators";
import breadcrumb from "@/components/breadcrumb";
import moment from "moment";
import ModalExport from "./components/modal-export";
import ModalImport from "./components/modal-import";
// import KyCuoc from "@/components/KyCuoc.vue";
import KyCuoc from "../components/KyCuoc.vue";
import BssRequiredMarker from '@/components/BssRequiredMarker';
import { helpers } from 'vuelidate/lib/validators'

const mustNotPast = value => {
   if(value) {
     return value >= new Date().setHours(0, 0, 0, 0);
   }
   return true
};

const mustANumber = value => {
  const str = value.toString().replaceAll(",", "");
  if(str.trim().length == 0) return false;
  if (!isNaN(str)) return true;
    if (typeof str != "string") return false;
      return !isNaN(str) && !isNaN(parseFloat(str));
}

const mustGreaterThan0 = value => {
  const str = value.toString().replaceAll(",", "");
  return str > 0;
}

const requiredDate = value => {
  return value != null;
}

const haveMaxlength = value => {
  return value.toString().replaceAll(",", "").length <= 12;
}

const haveLength = (param) =>
  helpers.withParams(
    { type: 'length', value: param },
    (value) => {
     return value.toString().replaceAll(",", "").length <= param;
    }
  )

  const maxByte100 = value => {
    return (new TextEncoder().encode(value.trim())).length <= 100;
  }

export default {
  components: { breadcrumb, ModalExport, ModalImport,AppKyCuoc: KyCuoc, BssRequiredMarker },
  filters: {
    currency: function(value) {
      let valNum = parseInt(value.toString().replaceAll(",", ""));
      if (valNum >= 0) return valNum.toLocaleString("en-En");
      return value;
    }
  },
  data: function() {
    return {
      header: {
        title: "THUÊ BAO KHẤT NỢ",
        list: [
          { name: "Xử lý thuê bao", link: { name: "Ui.cards" } },
          {
            name: "Khất nợ",
            link: { name: "Ui.buttons" }
          }
        ]
      },
      kyHd: this.getKyHd(),
      chuKy: "01",
      kyCuoc:"",
      dsDichVu: [],
      dsKhatNo: [],
      dsDBModal: [],
      dataItem: null,
      form: {
        status: 0,
        idDichVu: "",
        ngayHenTra: new Date(),
        maTb: "",
        tenTb: "",
        diaChiTb: "",
        tienNo: 0,
        lyDo: "",
        thueBaoId: "",
        submit: false
      },
      waterMark: "Chọn ngày",
      dateFormat: "dd/MM/yyyy",
      indexFocus: 0,
      isAddNew: false,
      dataSelected: null,
      removeArr: [],
      isSaving: false,
      noticeMsg:''
    };
  },
  validations: {
    form: {
      maTb: {
        required
      },
      tienNo: {
        required,
        // mustGreaterThan0
        // numeric,
        // minValue: minValue(1),
        mustANumber,
        mustGreaterThan0,
        haveMaxlength:haveLength(12)
      },
      ngayHenTra: {
        // required,
        requiredDate,
        mustNotPast,
      },
      lyDo: {
        required,
        maxByte100
      }
    }
  },
  watch: {},
  created: async function() {
    this.dsDichVuDropDown();
  },
  async mounted() {
    this.$refs.maTb.focus();
  },
  methods: {
    handleKyHoaDon(e) {
      this.kyCuoc = (moment(e.p_kyhoadon).format("YYYYMM") + e.p_chuky_no);
      // console.log(this.kyCuoc);
      this.getDsKhatNo();
    },
    validateData() {
      this.$v.$touch();
      let errorMessage = [];

      if (!this.$v.form.maTb.required) {
        errorMessage.push("Bạn chưa nhập mã thuê bao!");
      }

      if (!this.$v.form.tienNo.required) {
        errorMessage.push("Bạn chưa nhập số tiền khất nợ!");
      } else if (!this.$v.form.tienNo.mustANumber) {
        errorMessage.push("Số tiền khất nợ không hợp lệ!");
      } else if (!this.$v.form.tienNo.mustGreaterThan0) {
        errorMessage.push("Số tiền khất nợ  phải lớn hơn 0!");
      } else if(!this.$v.form.tienNo.haveMaxlength) {
        errorMessage.push("Bạn không được nhập tiền khất quá 12 ký tự");
      }

      if (!this.$v.form.ngayHenTra.requiredDate) {
        errorMessage.push("Bạn chưa nhập ngày hẹn trả!");
      } else if (!this.$v.form.ngayHenTra.mustNotPast) {
        errorMessage.push(
          "Ngày hẹn trả không được nhập nhỏ hơn ngày hiện tại!"
        );
      }

      if (!this.$v.form.lyDo.required) {
        errorMessage.push("Bạn chưa nhập lý do!");
      } else if (!this.$v.form.lyDo.maxByte100) {
         errorMessage.push("Bạn không được nhập lý do quá 100 ký tự");
      }

      if (!this.dataSelected) {
        errorMessage.push("Chưa có thông tin treo nợ. Bạn hãy kiểm tra lại!");
      }

      if (errorMessage.length > 0) {
        this.$toast.error(errorMessage.join("\n"));
        return true;
      } else {
        return false;
      }
    },
    getKyHd() {
      let current = new Date();
      let month =
        current.getMonth() < 10 ? `0${current.getMonth()}` : current.getMonth();
      let year = current.getFullYear();
      return `${month}/${year}`;
    },
    dsKhatNo_selectedRowChanged(dataItem) {
      if (dataItem) {
        this.isAddNew = false;
        this.renderData(dataItem);
      }
    },
    grid1_selectedItemChanged(value) {
      this.removeArr = value;
    },
    doSearch: async function(event) {
      if (this.form.maTb && this.form.maTb.trim()) {
        let maTb = this.form.maTb;
        if (this.checkExitTB(maTb)) {
          this.noticeMsg=`Thuê bao ${maTb} đã tồn tại trong danh sách`;
            this.$bvModal.show('notice-exist');
            setTimeout(() => {
               document.getElementById("ok-exit").focus(); 
            }, 500);
        } else {
          const db = await this.getDanhBa(maTb);
          if (db && db.data && db.data.length > 0) {
            if (db.data.length == 1) {
              let contact = db.data[0];
              this.resetForm();
              this.isAddNew = true;
              this.renderData(contact);
            } else {
              this.dsDBModal = db.data;
              this.dataItem = db.data[0];
              this.$bvModal.show("bv-modal-example");
            }
          } else {
            this.noticeMsg=`Không tìm thấy thông tin thuê bao ${maTb} vừa nhập!`;
            this.$toast.error(this.noticeMsg);
            // this.$bvModal.show('notice-notfound');
            // setTimeout(() => {
            //    document.getElementById("ok-md").focus(); 
            // }, 500);
          }
        }
      } else {
        this.resetForm();
        this.$toast.error("Bạn chưa nhập thông tin tìm kiếm.");
      }
    },
    closeNoticeNotFound() {
      this.$bvModal.hide('notice-notfound');
      this.noticeMsg='';
      this.resetFormNotFocus();
    },
    closeNoticeExist() {
      this.$bvModal.hide('notice-exist');
      this.noticeMsg='';
      this.dsKhatNo = this.dsKhatNo.filter(item => item.MA_TB == this.form.maTb);
      // this.$refs.maTb.focus();
      this.renderData(this.dsKhatNo[0]);
    },
    grid1_selectedRowChanged(dataItem) {
      this.dataItem = dataItem;
    },
    confirmChose() {
      this.$bvModal.hide("bv-modal-example");
      this.isAddNew = true;
      this.renderData(this.dataItem);
    },
    onClickDS: async function() {
      await this.getDsKhatNo();
      // this.$refs.table.rowClick(0,null);
    },
    renderData(data) {
      // console.log(data);
      if(data) {
        this.dataSelected = data;
        this.form.maTb = data.MA_TB;
        this.form.tenTb = data.TEN_TT || data.TEN_TB;
        this.form.diaChiTb = data.DIACHI_TT || data.DIACHI_TB || data.DIACHI_LD;
        this.form.idDichVu = data.DICHVUVT_ID;
        this.form.lyDo = data.LYDO || "";
        this.form.tienNo = data.TIENNO || 0;
        this.form.thueBaoId = data.THUEBAO_ID | "";
        this.form.ngayHenTra = data.NGAYHENTRA ? moment(data.NGAYHENTRA, "DD/MM/YYYY").toDate(): new Date();
      }
    },
    resetForm() {
      this.dataSelected = null;
      this.form.submit = false;
      this.form.maTb = "";
      this.form.ngayHenTra = new Date();
      this.form.tenTb = "";
      this.form.diaChiTb = "";
      this.form.tienNo = 0;
      this.form.lyDo = "";
      this.$refs.maTb.focus();
    },
      resetFormNotFocus() {
      this.dataSelected = null;
      this.form.submit = false;
      this.form.maTb = "";
      this.form.ngayHenTra = new Date();
      this.form.tenTb = "";
      this.form.diaChiTb = "";
      this.form.tienNo = 0;
      this.form.lyDo = "";
      // this.$refs.maTb.focus();
    },
    checkExitTB(maTb) {
      let isExits = false;
      if (this.dsKhatNo.length > 0) {
        this.dsKhatNo.forEach(item => {
          if (item.MA_TB == maTb) {
            isExits = true;
          }
        });
        return isExits;
      }
    },
    getDanhBa: async function(maTb) {
      try {
        var rs = await this.$root.context.get(
          "/web-thuno/api/thu-no/tra-cuu-danh-ba/danh-ba-thue-bao-theo-ma",
          { maTB: maTb }
        );
        return rs;
      } catch (error) {
        console.log(error);
        return null;
      } finally {
      }
    },
    getDsKhatNo: async function() {
      try {
        var rs = await this.$root.context.get(
          `/web-thuno/api/thu-no/xu-ly-thue-bao/ds-khat-no/${this.kyCuoc}`
        );
        if (rs && rs.data) {
          // console.log(rs.data);
          this.dsKhatNo = rs.data.map(item => {
            return {
              ...item,
              TIENNO: Number(item.TIENNO).toLocaleString("en-En"),
              // NGAYHENTRA: moment(item.NGAYHENTRA).format('DD/MM/YYYY'),
              NGAYHENTRABASE: item.NGAYHENTRA
            };
          });
          this.dsKhatNo.sort(function(a, b) {
                var c = new Date(a.NGAYHENTRABASE);
                var d = new Date(b.NGAYHENTRABASE);
                return c-d;
            });
          if(this.dsKhatNo.length == 0) {
            this.resetForm();
          }
        }
      } catch (error) {
        console.log(error);
      } finally {
      }
    },
    dsDichVuDropDown: async function() {
      try {
        var rs = await this.$root.context.get(
          "/web-thuno/api/thu-no/common/dich-vu-vien-thong"
        );
        this.dsDichVu = rs.data.sort(function(a, b) {
          return a.DICHVUVT_ID - b.DICHVUVT_ID;
        });
        if (rs.data) {
          this.form.idDichVu = rs.data[0].DICHVUVT_ID;
        }
      } catch (error) {
      } finally {
      }
    },
    onSave() {
      this.form.submit = true;
      this.form.tienNo = this.form.tienNo.toString().replaceAll(",", "");
      this.form.lyDo = this.form.lyDo.trim().replace(/\s\s+/g, ' ');
      if (this.validateData()) {
        return;
      }
      this.form.submit = false;
      if (this.isAddNew) {
        this.addNew();
      } else {
        this.update();
      }
    },
    update: async function() {
      if (this.form.thueBaoId) {
        const { thueBaoId, lyDo, ngayHenTra, tienNo } = this.form;
        const body = {
          kyCuoc: this.kyCuoc,
          thueBaoId: thueBaoId,
          lyDo: lyDo,
          ngayHenTra: moment(ngayHenTra).format("DD/MM/YYYY"),
          tienNo: tienNo
        };
        try {
          const { data } = await this.axios.put(
            "web-thuno/api/thu-no/xu-ly-thue-bao/cap-nhat-thue-bao-khat-no",
            body
          );
          if (data && data.error == "200") {
            this.$toast.success("Cập nhật thông tin thành công!");
            this.getDsKhatNo();
            // this.$refs.table.rowClick(0,null);
          } else {
            this.$toast.error("Cập nhật thông tin thất bại!");
          }
        } catch (error) {
          this.$toast.error("Cập nhật thông tin thất bại!");
        }
      }
    },
    addNew: async function() {
      if (this.form.maTb) {
        this.isSaving = true;
        const { idDichVu, lyDo, ngayHenTra, tienNo, maTb } = this.form;
        const body = {
          kyCuoc: this.kyCuoc,
          dsKhatNo: [
            {
              maTB: maTb,
              dichVuVTId: idDichVu,
              lyDo: lyDo,
              ngayHenTra: moment(ngayHenTra).format("DD/MM/YYYY"),
              tienNo: tienNo
            }
          ]
        };
        try {
          const { data } = await this.axios.put(
            "web-thuno/api/thu-no/xu-ly-thue-bao/cap-nhat-thue-bao-khat-no-theo-file",
            body
          );
          if (data && data.error == "200") {
            this.$toast.success("Thêm mới thông tin thành công!");
            this.getDsKhatNo();
            this.isSaving = false;
            // this.$refs.table.rowClick(0,null);
          } else {
            this.isSaving = false;
            this.$toast.error("Thêm mới thông tin thất bại!");
          }
        } catch (error) {
          this.isSaving = false;
          const {data} = error;
          if(data.error_code == "BSS-00009491") {
            this.$toast.error(data.message_detail);
          } else {
            this.$toast.error("Thêm mới thông tin thất bại!");
          }
        }
      }
    },
    remove: async function() {
      try {
        const {
          data
        } = await this.axios.delete(
          "web-thuno/api/thu-no/xu-ly-thue-bao/ds-thue-bao-khat-no",
          { data: { dsThueBaoId: this.removeArr } }
        );
        if (data && data.error == "200") {
          this.$toast.success("Xóa thông tin thành công!");
          this.getDsKhatNo();
        } else {
          this.$toast.error("Xóa thông tin thất bại!");
        }
      } catch (error) {
        this.$toast.error("Xóa thông tin thất bại!");
      }
    },
    onAddNew() {
      this.isAddNew = true;
      this.resetForm();
      // this.$refs.table.rowClick(-1,null);
      // this.$refs.table.setCurrentSelectedRow(0);
      this.$nextTick(() => {
        this.$refs.maTb.focus()
      })
    },
    onCancel() {
      if (this.isAddNew) {
        this.resetForm();
        this.isAddNew = false;
        this.onClickDS();
      } else {
        this.renderData(this.dataSelected);
      }
    },
    onRemove() {
      if (this.removeArr.length == 0) {
        this.$toast.error("Bạn chưa chọn thuê bao!");
      } else {
        this.$bvModal
          .msgBoxConfirm(
            `Bạn có thật sự muốn xóa ${this.removeArr.length} thuê bao đang được chọn không ?`,
            {
              title: "Thông báo",
              size: "sm",
              id: "modal-notice",
              buttonSize: "md",
              okVariant: "primary",
              headerClass: "p-2 pb-0 border-bottom-0",
              footerClass: "p-2 pt-0 border-top-0 justify-content-center",
              centered: true,
              noCloseOnBackdrop: true,
              okTitle: "Đồng ý",
              cancelTitle: "Hủy"
            }
          )
          .then(value => {
            value && this.remove();
          });
      }
    },
    onShowModalExport() {
      this.$bvModal.show("modal-export");
    },
    onShowModalImport() {
      this.$bvModal.show("modal-import");
    },
    importSucess(){
      this.$bvModal.hide("modal-import");
      this.getDsKhatNo();
    }
  }
};
</script>
