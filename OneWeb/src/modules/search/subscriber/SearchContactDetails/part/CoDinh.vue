<template>  
    <div class="row">
      <div class="col-sm-8 col-12">        
        <div class="info-row">
          <div class="key w90">Trạm VT</div>
          <div class="value">
            <div class="select-custom">
              <SelectExt 
                    placeholder="Tìm kiếm"  
                    class="disabled" 
                    v-model="formProps.tram_id" 
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
          <div class="key w90">Trạm NE</div>
          <div class="value">
            <input type="text" class="form-control" v-model="formProps.tram_ne" readonly>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-7 col-12">
            <div class="info-row">
              <div class="key w90">Loại cáp</div>
              <div class="value">
                <div class="select-custom">
                  <SelectExt 
                      placeholder="Tìm kiếm"  
                      class="disabled" 
                      v-model="formProps.loaicap_id" 
                      :allowFiltering="true" 
                      dataTextField="LOAI_CAP"
                      dataValueField="LOAICAP_ID" 
                      :dataSource="optionProps.cbx_loaicap" 
                      @change="selectLoaicap($event)">
                  </SelectExt>
                </div>
              </div>
            </div>
          </div>
          <div class="col-sm-5 col-12">
            <div class="info-row">
              <div class="key w90">Hộp cáp ngọn</div>
              <div class="value">
                <input type="text" class="form-control" v-model="formProps.hopcap_p" readonly>
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-7 col-12">
            <div class="info-row">
              <div class="key w90">Tủ cáp gốc</div>
              <div class="value">
                <input type="text" class="form-control" v-model="formProps.tucap_goc" readonly>
              </div>
            </div>
          </div>
          <div class="col-sm-5 col-12">
            <div class="info-row">
              <div class="key w90">Tủ cáp ngọn</div>
              <div class="value">
                <input type="text" class="form-control" v-model="formProps.tucap_p" readonly>
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-7 col-12">
            <div class="info-row">
              <div class="key w90">Đôi cáp gốc</div>
              <div class="value">
                <input type="text" class="form-control" v-model="formProps.doicap_goc" readonly>
              </div>
            </div>
          </div>
          <div class="col-sm-5 col-12">
            <div class="info-row">
              <div class="key w90">Đôi cáp ngọn</div>
              <div class="value">
                <input type="text" class="form-control" v-model="formProps.doicap_p" readonly>
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-7 col-12">
            <div class="info-row">
              <div class="key w90">Cáp gốc</div>
              <div class="value">
                <input type="text" class="form-control" v-model="formProps.cap_goc" readonly>
              </div>
            </div>
          </div>
          <div class="col-sm-5 col-12">
            <div class="info-row">
              <div class="key w90">Cáp ngọn</div>
              <div class="value">
                <input type="text" class="form-control" v-model="formProps.cap_p" readonly>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="col-sm-4 col-12">        
        <div class="info-row">
          <div class="key w80">Số chính</div>
          <div class="value">
             <input type="text" class="form-control highlight" v-model="formProps.sochinh" readonly>
          </div>
        </div>
        <div class="info-row">
          <div class="key w80">NE</div>
          <div class="value">
            <input type="text" class="form-control highlight" v-model="formProps.ne" readonly>
          </div>
        </div>
        <div class="info-row">
          <div class="key w80">Mã TN</div>
          <div class="value">
            <input type="text" class="form-control highlight" v-model="formProps.matb_tn" readonly>
          </div>
        </div>
        <div class="info-row">
          <div class="key w80">Tuyến cáp</div>
          <div class="value">
            <input type="text" class="form-control" v-model="formProps.tuyencap" readonly>
          </div>
        </div>
        <div class="info-row">
          <div class="key w80">Liên tủ</div>
          <div class="value">
            <input type="text" class="form-control" v-model="formProps.lientu" readonly>
          </div>
        </div>
        <div class="info-row">
          <div class="key w80">Cự ly</div>
          <div class="value">
            <input type="text" class="form-control" v-model="formProps.culy" readonly>
          </div>
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
  name: "CoDinh",
  props: ["formProps","optionProps"],
  components: {
    KDatePicker,
  },
  data() {
    return {};
  },
  methods: {
    selectTothicong(){

    },
    selectLoaicap(){

    },
  },
};
</script>