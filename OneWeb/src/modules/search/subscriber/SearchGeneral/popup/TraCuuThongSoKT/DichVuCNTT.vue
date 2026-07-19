<template>
    <div class="table-content" id="table-scroll">
      <hr>
      <div class="table-content-cntt">
        <table id="db_cntt" class="table-result table-gachle">
          <tbody>
            <tr v-for="(tt, i) in dsThuocTinhHDTB_CNTT" :key="i" :class="tt.THUTU_ID==0?'highlight':''">
              <td
                style="display: none"
                id="tt.FIELD_NAME"
                ref="tt.FIELD_NAME"
              ></td>
              <td :class="tt.THUTU_ID==0?'highlight1':''">
                {{ tt.CAPTION }}
                <!--<span style="color: red">*</span>-->
              </td>
              <td class="padt0 padb0" colspan="2">
                <!--                                  :class="{ 'is-invalid': $v.dataSelected.loaiBeCapId.$error }"-->
                <div
                  class="select-custom"
                  v-if="tt.DATA_TYPE === 1 && tt.DS !== '[]' && tt.DS.length > 0"
                >
                  <select
                    class="form-control  bor0 nocorner bg-none trans-bg pd-5"
                    :disabled="true"
                    v-model="tt.FIELD_VALUE"
                  >
                    <option v-for="item in tt.DS" :key="item[tt.PARENT_KEYFIELD]" :value="item[tt.PARENT_KEYFIELD]" >
                      {{ item[tt.PARENT_DESCFIELD] }}
                    </option>
                  </select>                
                </div>
                <div class="value" v-else-if="tt.DATA_TYPE === 2">
                  <ejs-datepicker
                    class="form-control bg-none  bor0 trans-bg"
                    readonly="true"
                    v-model="tt.FIELD_VALUE"                  
                  >
                  </ejs-datepicker>
                </div>
                <div class="value" v-else>
                  <input
                    type="text"
                    class="form-control bg-none  bor0" 
                    v-model="tt.FIELD_VALUE"
                    readonly="true"
                  />
                </div>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
</template>
<style>
.custom-cntt .select2-container--default .select2-selection--single {
  border: none !important;
  border-color: none !important;
  background-color: none !important;
}
.e-input-group.e-error, .e-input-group.e-control-wrapper.e-error, .e-input-group.e-error:not(.e-float-icon-left), .e-input-group.e-control-wrapper.e-error:not(.e-float-icon-left) {
    border-color: #ced4da;
}
</style>
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
  name: "DichVuCNTT",  
  components: {
    KDatePicker,
  },
  data() {
    return {
      dsThuocTinhHDTB_CNTT: [],
    };
  },
  methods: {
    async hienthi_dulieu_thuebao(loaithuebao, thuebao_id){
      const response = await this.$root.context.get(
          `/web-quantri/quan-ly-danh-ba/danhsach-thuoctinh-capnhat-danhba?ploaitbID=${loaithuebao}&pThueBaoID=${thuebao_id}&pTenBang=HDTB_CNTT&pOptList=00000`
        );
        if (response &&response.data &&response.error_code &&response.error_code === "BSS-00000000") {          
          this.dsThuocTinhHDTB_CNTT = response.data;        
        }
    },
  },
};
</script>            
