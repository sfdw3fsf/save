<template>
  <div
    class="row"
    v-show="formProps.dichvuvt_id === 15 ||
      formProps.dichvuvt_id === 14 ||
      formProps.dichvuvt_id === 13 ||
      formProps.dichvuvt_id === 16
    "
  >
    
    <div class="table-content" id="table-scroll">
      <hr>
      <div class="table-content-cntt">
        <table id="db_cntt" class="table-result table-gachle">
          <tbody>
            <tr v-for="(tt, i) in formProps.dsThuocTinhHDTB_CNTT" :key="i" :class="tt.THUTU_ID==0?'highlight':''">
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
                  v-if="tt.ATT === 4 && tt.DS !== '[]'"
                >
                  <select
                    class="form-control h24 bor0 nocorner bg-none trans-bg pd-5"
                    :disabled="true"
                    v-model="tt.FIELD_VALUE"
                  >
                    <option v-for="item in tt.DS" :key="item[tt.PARENT_KEYFIELD]" :value="item[tt.PARENT_KEYFIELD]" >
                      {{ item[tt.PARENT_DESCFIELD] }}
                    </option>
                  </select>                
                </div>
                <div class="value" v-else-if="tt.ATT === 0">
                  <!-- <ejs-datepicker
                    class="form-control bg-none h24 bor0 trans-bg"
                    readonly="true"
                    v-model="tt.FIELD_VALUE"                  
                  >
                  </ejs-datepicker> -->
                  <input
                    type="text"
                    class="form-control bg-none h24 bor0 text-right" 
                    v-model="tt.FIELD_VALUE"
                    readonly="true"
                  />
                </div>

                <div class="input-more-button -right" v-else-if="tt.ATT === 5">
                  <!-- <ejs-datepicker
                    class="form-control bg-none h24 bor0 trans-bg"
                    readonly="true"
                    v-model="tt.FIELD_VALUE"                  
                  >
                  </ejs-datepicker> -->
                  <button class="btn h-auto pad4 lh14 w24" @click.prevent="repositoryButtonEdit_Click(tt)">
                    <span class="-ap icon-more_horiz"></span>
                </button>
                <input type="text" class="form-control padr30 h24 bor0 nocorner bg-none"  v-model="tt.FIELD_VALUE">
                </div>
                <div class="value" v-else>
                  <input
                    type="text"
                    class="form-control bg-none h24 bor0" 
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
  </div>
</template>
<style>
.custom-cntt .select2-container--default .select2-selection--single {
  border: none !important;
  border-color: none !important;
  background-color: none !important;
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
  props: ["formProps", "optionProps","cnttProps"],
  components: {
    KDatePicker,
  },
  data() {
    return {};
  },
  methods: {
    async repositoryButtonEdit_Click(tt) {
      console.log(tt);

      const thongtin_xacnhan_str = await (this.axios.post(`/web-hopdong/quanly_khdn_mnp/sp_lay_ds_dl_nghiepvu`, {
        p_nghiepvu: 'LAY_THONGTIN_BOSUNG_CNTT',
            p_ds_para:  JSON.stringify({...tt, vthuebao_id: this.formProps.thuebao_id})
      }))
      let data = thongtin_xacnhan_str.data.data[0]['ketqua'].toString();
      const h = this.$createElement
      const titleVNode = h('div', { domProps: { innerHTML: data } })
      // this.$bvModal.msgBoxOk([titleVNode], {
      //   centered: true,
      //   buttonSize: 'sm'
      // })
      this.$bvModal
        .msgBoxOk([titleVNode], {
          title: 'Thông tin hợp đồng bổ sung',
          size: 'lg',
          okTitle: 'Đóng',
          centered: true
        })
    }
  },
};
</script>            