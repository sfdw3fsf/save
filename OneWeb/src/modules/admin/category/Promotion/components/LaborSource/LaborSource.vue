<template src='./LaborSource.html'></template>
<style  src='./LaborSource.scss'></style>

<script>
import { mixin } from ".././UpdatePromotionDetail/components/mixins";
import { mapActions, mapMutations } from "vuex";
import commonFn from "@/modules/contract/setup/ChangeLandlineIMSType/CommonFuntions"; 
import {
  required,
  email,
  minLength,
  sameAs,
  minValue,
} from "vuelidate/lib/validators";

export default {
  mixins: [mixin],
  props: ["formInput", "tyleVat", "chitietkm_id"],
  components: {},
  name: "LaborSource",
  data: () => ({
    selectionOptionsCheckboxMode: { checkboxMode: "ResetOnRowClick" },
    form: {
      tyle_vat: false,
      tien_kmld: 0,
      vat_kmld: 0,
      tyle_kmld: 0,
    },
    tyle_vat: 0,
  }),
  validations: {
    form: {
      tien_kmld: { required },
      vat_kmld: { required },
      tyle_kmld: { required },
    },
  },
  computed: {
    danhSachNguonKMDaGan() {
      return JSON.parse(
        JSON.stringify(
          this.$store.getters["promotionDetail/danhSachNguonKMDaGan"]
        )
      );
    },
    danhSachNguonKMChuaGan() {
      return JSON.parse(
        JSON.stringify(
          this.$store.getters["promotionDetail/danhSachNguonKMChuaGan"]
        )
      );
    },
  },
  async mounted() {
    this.$root.showLoading(true);

    if (this.formInput != null){
      this.form = this.formInput;
      this.form.tyle_kmld = this.formInput.tyleKMLD
      this.tyle_vat = this.formInput.tyle_vat
    }
    console.log("this.form", this.form)
     
    // const data = await this.getNguonLDDaGan({
    //   list_of_cols: "*",
    //   order: "",
    //   schema: "CSS",
    //   table_name: "CTKM_NGUON_KMLD",
    //   where: `chitietkm_id=${this.chitietkm_id}`,
    // });

    //fix bug ATTBM
    try{
 
      let data = await this.getNguonLDDaGan({"p_param": this.chitietkm_id, "p_type":1});
      if (!data) {
        data = []
      }
      console.log("getNguonLDDaGan", data)
      const dataChuaGan = await this.getNguonLDChuaGan();

      const r = dataChuaGan.filter(
        (elem) =>
          !data.find(({ nguonkm_ld_id }) => elem.nguonkm_id === nguonkm_ld_id)
      );
      this.setDanhSachNguonKMChuaGan(r);
      console.log("dataChuaGan", dataChuaGan)
      let op = data.map((e, i) => {
        let temp = dataChuaGan.find(
          (element) => element.nguonkm_id === e.nguonkm_ld_id
        );
        if (temp.ten_nguon) {
          e.ten_nguon = temp.ten_nguon;
          e.nguonkm_id = temp.nguonkm_id;
        }
        return e;
      });
      this.setDanhSachNguonKMDaGan(op);

      this.$root.showLoading(false);

    }catch(ex){
      this.$root.showLoading(false);
      commonFn.showException(this, ex);
    }
  },

  watch: {},
  methods: {
    ...mapMutations("promotionDetail", [
      "setDanhSachNguonKMDaGan",
      "setDanhSachNguonKMChuaGan",
    ]),

    ...mapActions("promotionDetail", ["getNguonLDDaGan", "getNguonLDChuaGan"]),
    cloneModal() {
      this.$emit("close");
    },
    resetForm() {
      this.form.tyle_vat = false;
      this.tien_kmld = 0;
      this.vat_kmld = 0;
      this.tyle_kmld = 0;
    },
    onChangInput() {
      this.form.vat_kmld = this.form.tien_kmld / (0.1 * 100);
    },
    handCheckBoxDaGanRemove() {
      let selectedrecordsDaGan =
        this.$refs.tabledanhSachNguonKMDaGan.getSelectedRecords();
      let selectedrecordsDaGanNew = (selectedrecordsDaGanNew =
        selectedrecordsDaGan.map((obj) => {
          obj.tien_kmld = null;
          obj.vat_kmld = null;
          obj.tyle_kmld = null;
          return obj;
        }));
      if (selectedrecordsDaGanNew) {
        const oldNotDelivered = [...this.danhSachNguonKMChuaGan];
        const oldDelivered = [...this.danhSachNguonKMDaGan];

        const newNotDelivered = [
          ...selectedrecordsDaGanNew,
          ...oldNotDelivered,
        ];
        const arrDelete = selectedrecordsDaGanNew.map((obj) => obj.nguonkm_id);

        const res = oldDelivered.filter(
          (f) => !arrDelete.includes(f.nguonkm_id)
        );
        this.setDanhSachNguonKMChuaGan(newNotDelivered);
        this.setDanhSachNguonKMDaGan(res);
      }
    },
    handCheckBoxDaChuaRemove(e) {
      let selectedrecordsChuaGan =
        this.$refs.tabledanhSachNguonKMChuaGan.getSelectedRecords();
      e.preventDefault();
      this.$v.$touch();

      if (selectedrecordsChuaGan.length == 0) {
        this.$toast.error("Bạn phải chọn nguồn KM!");
        return;
      }

      if (
        this.form.tyle_vat == true &&
        this.form.tyle_kmld == 0  
      ) {
        this.$toast.error("Bạn phải nhập tỷ lệ KMLĐ!");
        return;
      }

      if ( (this.form.tien_kmld == 0 && !this.form.tyle_vat)
      ) {
        this.$toast.error("Bạn phải nhập tiền KMLĐ!");
        return;
      }
      if ( (this.form.vat_kmld == 0 && !this.form.tyle_vat)
      ) {
        this.$toast.error("Bạn phải nhập VAT KMLĐ!");
        return;
      }

      let selectedrecordsChuaGanNew = [];

      if (!this.form.tyle_vat) {
        selectedrecordsChuaGanNew = selectedrecordsChuaGan.map((obj) => {
          obj.tien_kmld = this.form.tien_kmld;
          obj.vat_kmld = this.form.vat_kmld;
          obj.muc = this.danhSachNguonKMDaGan.length + 1;
          obj.tyle_kmld = 0;

          return obj;
        });
      } else {
        var sumDaGan = this.danhSachNguonKMDaGan.reduce(function (s, a) {
          return s + a.tyle_kmld;
        }, 0);

        selectedrecordsChuaGanNew = selectedrecordsChuaGan.map((obj) => {
          obj.tien_kmld = 0;
          obj.vat_kmld = 0;
          obj.muc = this.danhSachNguonKMDaGan.length + 1;
          obj.tyle_kmld = this.form.tyle_kmld;
          return obj;
        });

        var sumChuaGan = selectedrecordsChuaGanNew.reduce(function (s, a) {
          return s + a.tyle_kmld;
        }, 0);
        if (sumDaGan + sumChuaGan > 100) {
          this.$toast.error("Tổng tỷ lệ km vượt quá 100%");

          return;
        }
      }

      const oldNotDelivered = [...this.danhSachNguonKMDaGan];
      const oldDelivered = [...this.danhSachNguonKMChuaGan];

      const newNotDelivered = [
        ...selectedrecordsChuaGanNew,
        ...oldNotDelivered,
      ];
      const arrDelete = selectedrecordsChuaGanNew.map((obj) => obj.nguonkm_id);

      const res = oldDelivered.filter((f) => !arrDelete.includes(f.nguonkm_id));
      this.setDanhSachNguonKMDaGan(newNotDelivered);
      this.setDanhSachNguonKMChuaGan(res);
    },
    handDataToRight(e) {
      let selectedrecordsDaGannNew = [];

      selectedrecordsDaGannNew = this.danhSachNguonKMDaGan.map((obj) => {
        obj.tien_kmld = null;
        obj.vat_kmld = null;
        obj.tyle_kmld = null;

        return obj;
      });
      const temp = [
        ...selectedrecordsDaGannNew,
        ...this.danhSachNguonKMChuaGan,
      ];
      this.setDanhSachNguonKMChuaGan(temp);
      this.setDanhSachNguonKMDaGan([]);
    },
    handDataToLeftight(e) {
      let selectedrecordsChuaGanNew = [];

      if (
        this.form.tyle_vat == true &&
        this.form.tyle_kmld == 0 &&
        this.$v.form.tyle_kmld.$error
      ) {
        this.$toast.error("Bạn phải nhập tỷ lệ KMLĐ!");
        return;
      }

      if (
        this.$v.form.tien_kmld.$error ||
        (this.form.tien_kmld == 0 && !this.form.tyle_vat)
      ) {
        this.$toast.error("Bạn phải nhập tiền KMLĐ!");
        return;
      }
      if (
        this.$v.form.vat_kmld.$error ||
        (this.form.vat_kmld == 0 && !this.form.tyle_vat)
      ) {
        this.$toast.error("Bạn phải nhập VAT KMLĐ!");
        return;
      }
      if (!this.form.tyle_vat) {
        selectedrecordsChuaGanNew = this.danhSachNguonKMChuaGan.map((obj) => {
          obj.tien_kmld = this.form.tien_kmld;
          obj.vat_kmld = this.form.vat_kmld;
          return obj;
        });
      } else {
        var sumDaGan = this.danhSachNguonKMDaGan.reduce(function (s, a) {
          return s + a.tyle_kmld;
        }, 0);

        selectedrecordsChuaGanNew = this.danhSachNguonKMChuaGan.map((obj) => {
          obj.tyle_kmld = this.form.tyle_kmld;
          return obj;
        });

        var sumChuaGan = selectedrecordsChuaGanNew.reduce(function (s, a) {
          return s + a.tyle_kmld;
        }, 0);
        if (sumDaGan + sumChuaGan > 100) {
          this.$toast.error("Tổng tỷ lệ km vượt quá 100%");

          return;
        }
      }

      const temp = [...selectedrecordsChuaGanNew, ...this.danhSachNguonKMDaGan];
      this.setDanhSachNguonKMDaGan(temp);
      this.setDanhSachNguonKMChuaGan([]);
    },
    handChapNhan(e) {
      e.preventDefault();
      this.$emit("onChapNhapNguon", this.form.tyle_vat);
    },
  },
};
</script>
