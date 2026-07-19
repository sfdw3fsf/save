<template>
  <div class="custom-cntt">
    <hr />
    <div class="table-content">
      <table class="table-result table-gachle">
        <tr class="highlight">
          <td class="w20 center selected1">
            <span class="fa fa-play text-main"></span>
          </td>
          <td class="highlight1">Số máy/Acc</td>
          <td>
            <input
              type="text"
              class="form-control h24 bor0 nocorner bg-none text-main"
              v-model="formProps.ma_tb"
            />
          </td>
        </tr>
        <tr>
          <td class="w20"></td>
          <td>Mã phụ lục</td>
          <td>
            <div class="input-icon-right">
              <input
                type="text"
                class="form-control h24 bor0 nocorner bg-none text-main"
                value="15/04/2020"
                v-model="formProps.ma_pl"
              />
              <span class="icon one-calendar"></span>
            </div>
          </td>
        </tr>
        <tr>
          <td class="w20"></td>
          <td>Chủ quản</td>
          <td>
                    
              <SelectExt 
                    class=""                    
                    v-model="formProps.chuquan_id" 
                    :allowFiltering="true" 
                    dataTextField="ten_tat"
                    dataValueField="chuquan_id" 
                    :dataSource="optionProps.cbx_chuquan" 
                    @change="selectChuquan($event)">
                </SelectExt>
            
          </td>
        </tr>
        <tr>
          <td class="w20"></td>
          <td>Đại lý</td>
          <td class="center">
            <div class="check-action">
              <input type="checkbox" class="check" checked v-model="formProps.daily"/>
              <span class="name"></span>
            </div>
          </td>
        </tr>
        <tr>
          <td class="w20"></td>
          <td>Phiên bản</td>
          <td>
            
              <SelectExt 
                    class=""
                    placeholder=""  
                    v-model="formProps.phienban_id" 
                    :allowFiltering="true" 
                    dataTextField="PHIENBAN"
                    dataValueField="PHIENBAN_ID" 
                    :dataSource="optionProps.cbx_phienban" 
                    @change="selectPhienban($event)">
                </SelectExt>
            
          </td>
        </tr>
        <tr>
          <td class="w20"></td>
          <td>Gói cước</td>
          <td>
            
              <SelectExt 
                    class=""                    
                    v-model="formProps.goicuoc_id" 
                    :allowFiltering="true" 
                    dataTextField="GOICUOC"
                    dataValueField="GOICUOC_ID" 
                    :dataSource="optionProps.cbx_goicuoc" 
                    @change="selectGoicuoc($event)">
                </SelectExt>
            
          </td>
        </tr>
        <tr>
          <td class="w20"></td>
          <td>Tỷ lệ VAT Phí duy trì(%)</td>
          <td>
            <input
              type="text"
              class="form-control h24 bor0 nocorner bg-none text-main"
              v-model="formProps.vat"
            />
          </td>
        </tr>
        <tr>
          <td class="w20"></td>
          <td>Phí duy trì(đ/tháng)</td>
          <td>
            <div class="select-custom">
              <input
              type="text"
              class="form-control h24 bor0 nocorner bg-none text-main"
              v-model="formProps.phi_dt"
            />
            </div>
          </td>
        </tr>
        <tr>
          <td class="w20"></td>
          <td>Ngày BĐ tính phí duy trì</td>
          <td>
            <div class="input-icon-right">              
              <k-date-picker 
                  class="" 
                  ref="ngay_sn" format="DD/MM/YYYY HH:mm:ss" 
                  type="datetime" 
                  v-model="formProps.ngay_bddt"/>
              
            </div>
          </td>
        </tr>
        <tr>
          <td class="w20"></td>
          <td>Ngày KT tính phí duy trì</td>
          <td>
            <div class="input-icon-right">              
              <k-date-picker 
                  class="" 
                  ref="ngay_sn" 
                  format="DD/MM/YYYY HH:mm:ss" 
                  type="datetime" 
                  v-model="formProps.ngay_ktdt"/>
              
            </div>
          </td>
        </tr>
      </table>
    </div>
  </div>
</template>
<style>
.custom-cntt .select2-container--default .select2-selection--single {border: none !important;border-color: none !important;background-color: none !important;}
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
