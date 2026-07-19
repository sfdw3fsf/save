<template>
  <div>
    <div class="info-row">
      <div class="key w90">Trạm VT</div>
      <div class="value">
        <div class="select-custom">
          <SelectExt 
                    placeholder="Tìm kiếm"  
                    class="disabled" 
                    v-model="formProps.donvi_id" 
                    :allowFiltering="true" 
                    dataTextField="NAME"
                    dataValueField="ID" 
                    :dataSource="optionProps.cbx_tothicong" 
                    @change="selectTothicong($event)">
                </SelectExt>
        </div>
      </div>
    </div>
    <div class="info-row">
      <div class="key w90">Simcard</div>
      <div class="value">        
        <input type="text" class="form-control" v-model="formProps.simcard" readonly>
      </div>
    </div>
    <div class="info-row">
      <div class="key w90">Cell</div>
      <div class="value">
        <input type="text" class="form-control" v-model="formProps.cell1" readonly>
      </div>
    </div>
    <div class="info-row">
      <div class="key w90">Tọa độ</div>
      <div class="value">
        <input type="text" class="form-control" v-model="formProps.toado" readonly>
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
  name: "MacDinh",
  props: ["formProps", "optionProps"],
  components: {
    KDatePicker,
  },
  data() {
    return {};
  },
  methods: {
      selectTothicong(){

      },
  },
};
</script>