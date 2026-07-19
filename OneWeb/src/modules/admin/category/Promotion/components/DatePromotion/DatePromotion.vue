<template >
  <div class="modal modal-show km-date" id="  TCTTTB">
    <div class="modal-dialog" style="width: 100%; max-width: 30%">
      <div class="modal-content popup-box">
        <div class="popup-header">
          <div class="title">
            <span class="icon one-notepad"></span> Quản lý ngày hiệu lực khuyến
            mại
          </div>
          <div
            class="close -ap icon-close"
            data-dismiss="modal"
            @click="$emit('close')"
          ></div>
        </div>
        <div class="list-actions-top">
          <ul class="list">
            <li @click="handGhiLai">
              <a> <span class="icon one-save"></span> Ghi lại </a>
            </li>

            <li @click="handDelete" :class="currentRecord ? '' : 'disabled'">
              <a> <span class="icon one-trash"></span> Xóa </a>
            </li>
            <li @click="$emit('close')">
              <a> <span class="icon one-copy"></span> Thoát </a>
            </li>
          </ul>
        </div>
        <div class="popup-body">
          <div class="info-row">
            <div class="key w50">Ngày</div>
            <div class="value">
              <date-picker
                style="width: 100%"
                v-model="dateFrom"
                placeholder="dd/mm/yyyy"
                :formatter="momentFormat"
                valueType="format"
              ></date-picker>
            </div>
          </div>

          <div class="box-col box-form">
            <div
              class="grid-ext grid-ext-freeze"
              style="height: 270px; overflow: hidden"
            >
              <ejs-grid
                ref="gridDate"
                :dataSource="danhSachNgayHieuLuc"
                height="150px"
                :allowPaging="true"
                :pageSettings="pageSettings"
                :allowSorting="false"
                :allowFiltering="true"
                :allowSelection="true"
                :pagerTemplate="pagerTemplate"
                gridLines="Both"
                :rowSelected="rowSelected"
              >
                <e-columns>
                  <e-column
                    template="<span class='fa selected-flag'></span>"
                    :allowFiltering="false"
                    filterTemplate="<span></span>"
                    width="30"
                    maxWidth="30"
                    minWidth="30"
                    textAlign="center"
                    :customAttributes="{ class: 'column-selected-flag' }"
                    :freeze="'left'"
                    :allowResizing="false"
                    clipMode="Ellipsis"
                  ></e-column>
                  <e-column
                    field="ngay_hl"
                    headerText="Ngày hiệu lực"
                    :format="formatOptions"
                    type="date"
                  ></e-column>
                  <e-column
                    field="ngay_cn"
                    headerText="Ngày CN"
                    :format="formatOptions"
                    type="date"
                  ></e-column>
                  <e-column
                    field="nguoi_cn"
                    headerText="Người CN"
                    width="120"
                  ></e-column>
                </e-columns>
              </ejs-grid>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template> 
<style scoped src='./DatePromotion.scss'></style>
<script>
import { mapActions } from "vuex";
import * as moment from "moment";
import { mixin } from "../UpdatePromotionDetail/components/mixins/index";
import DatePicker from "vue2-datepicker";

export default {
  components: { DatePicker },
  mixins: [mixin],
  props: ["value"],
  data() {
    return {
      momentFormat: {
        stringify: (date) => {
          return date ? moment(date).format("DD/MM/YYYY") : "";
        },
        parse: (value) => {
          return value ? moment(value, "DD/MM/YYYY").toDate() : null;
        },
      },
      currentRecord: null,
      dateFrom: moment(new Date()).format("DD/MM/YYYY"),
      formatOptions: { type: "date", format: "dd/MM/yyyy" },
      detailHeader: [
        { label: "Ngày hiệu lực", filterAble: true, key: "ngay_hl" },
        { label: "Ngày CN", filterAble: true, key: "ngay_cn" },
        { label: "Người CN", filterAble: true, key: "nguoi_cn" },
      ],
    };
  },
  watch: {
    dateFrom(newval, oldval) {
      if (newval == null) {
        this.dateFrom = moment(new Date()).format("DD/MM/YYYY");
      }
    },
  },
  computed: {
    danhSachNgayHieuLuc() {
      return this.$store.getters["promotionCommon/danhSachNgayHieuLuc"];
    },
  },
  mounted() {
    console.log(this.value); 
    this.$store.dispatch("promotionCommon/getNgayHieuLuc", {"p_param": this.value.khuyenmai_id, "p_type":1});
  },
  methods: {
    handGhiLai() {
   
      const valTo = this.value.dateTo.split(" ")[0];
      var datePartsTo = valTo.split("/");
      var dateObjectTo = new Date(+datePartsTo[2], datePartsTo[1] - 1, +datePartsTo[0]);
      const valFrom = this.value.dateFrom.split(" ")[0];
      var datePartsFrom = valFrom.split("/");
      var dateObjectFrom = new Date(+datePartsFrom[2], datePartsFrom[1] - 1, +datePartsFrom[0]);
      var datePartsChosse = this.dateFrom.split("/");
      var dateObjectChosse = new Date(
        +datePartsChosse[2],
        datePartsChosse[1] - 1,
        +datePartsChosse[0]
      );
      if (dateObjectChosse < dateObjectFrom) {
        this.$toast.error("Vui lòng chọn ngày lớn hơn : " + valTo);
        return;
      }
        if (dateObjectChosse > dateObjectTo) {
        this.$toast.error("Vui lòng chọn ngày nhỏ hơn : " + valTo);
        return;
      }
      this.$store
        .dispatch("promotionCommon/insertDate", {
          khuyenmai_id: this.value.khuyenmai_id,
          ngay_hl: this.dateFrom,
        })
        .then((rs) => {
          if (rs.data.message == "Success") {
            this.$toast.success("Cập nhật thành công");
            this.$store.dispatch("promotionCommon/getNgayHieuLuc", {"p_param": this.value.khuyenmai_id, "p_type":1});
            // this.$store.dispatch("promotionCommon/getNgayHieuLuc", {
            //   list_of_cols: "khuyenmai_id,ngay_hl,ngay_cn,nguoi_cn",
            //   order: "",
            //   schema: "css",
            //   table_name: "km_ngayhl",
            //   where: `khuyenmai_id=${this.value.khuyenmai_id}`,
            // });
          }
        })
        .catch((err) => {
          if (err.data.error_code == "BSS-00000500")
            this.$toast.error(
              "Trùng dữ liệu hiệu lực, bạn không thể thêm mới!"
            );
        });
    },
    handDelete() {
      var today = new Date(this.currentRecord.ngay_hl);
      var tomorrow = new Date(this.currentRecord.ngay_hl);
      tomorrow.setDate(today.getDate() + 1);
      this.$store
        .dispatch("promotionCommon/deleteDate", {
          khuyenmai_id: this.currentRecord.khuyenmai_id,
          ngay_hl: moment(today).format("DD/MM/YYYY"),
        })
        .then((rs) => {
          if (rs.data.message == "Success") {
            this.$toast.success("Xóa thành công");
            this.$store.dispatch("promotionCommon/getNgayHieuLuc", {"p_param": this.value.khuyenmai_id, "p_type":1});
            // this.$store.dispatch("promotionCommon/getNgayHieuLuc", {
            //   list_of_cols: "khuyenmai_id,ngay_hl,ngay_cn,nguoi_cn",
            //   order: "",
            //   schema: "css",
            //   table_name: "km_ngayhl",
            //   where: `khuyenmai_id=${this.value.khuyenmai_id}`,
            // });
          }
        });
    },
    rowSelected() {
      let selectedRecord = this.$refs.gridDate.getSelectedRecords();
      console.log(selectedRecord[0]);
      this.currentRecord = selectedRecord[0];
    },
  },
};
</script>
