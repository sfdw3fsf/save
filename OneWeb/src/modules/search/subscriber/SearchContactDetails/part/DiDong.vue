<template>
  <div>
    <div class="info-row">
      <div class="key w90">Simcard</div>
      <div class="value">
        <input type="text" class="form-control" v-model="formProps.simcard" readonly>
      </div>
    </div>
    <div class="info-row">
      <div class="key w90">Số dư TK</div>
      <div class="value">
        <input type="text" class="form-control" v-model="formProps.sodu_tk" readonly>
      </div>
    </div>
    <div class="info-row">
      <div class="key w90">Gói cước</div>
      <div class="value">
        <input type="text" class="form-control" v-model="formProps.tengoicuoc" readonly>
      </div>
    </div>
  </div>
</template>
<script>
import KDatePicker from "@/components/kylq_components/KDatePicker.vue";
import moment from "moment";

import {
  DataManager,
  Query,
  ODataV4Adaptor,
  Predicate,
} from "@syncfusion/ej2-data";
export default {
  name: "DiDong",
  props: ["formProps", "optionProps"],
  components: {
    KDatePicker,
  },
  data() {
    return {};
  },
  methods: {},
};
</script>            
