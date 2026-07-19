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
        <div class="key w90">Bras</div>
        <div class="value">
          <div class="select-custom">
                    <SelectExt 
                      placeholder="Tìm kiếm" 
                      class="disabled"  
                      v-model="formProps.bras_id" 
                      :allowFiltering="true" 
                      dataTextField="tenbras"
                      dataValueField="bras_id" 
                      :dataSource="optionProps.cbx_bras" 
                      @change="selectBras($event)">
                  </SelectExt>
                  </div>
        </div>
      </div>

      <div class="row">
        <div class="col-sm-7 col-12">
          <div class="info-row">
            <div class="key w90">Username</div>
            <div class="value">
              <input type="text" class="form-control" v-model="formProps.username" readonly>
            </div>
          </div>
        </div>
        <div class="col-sm-5 col-12">
          <div class="info-row">
            <div class="key w100">Password</div>
            <div class="value">
              <input type="text" class="form-control" v-model="formProps.password" readonly>
            </div>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-sm-7 col-12">
          <div class="info-row">
            <div class="key w90">Slot</div>
            <div class="value">
              <input type="text" class="form-control" v-model="formProps.slot" readonly>
            </div>
          </div>
        </div>
        <div class="col-sm-5 col-12">
          <div class="info-row">
            <div class="key w100">Rack</div>
            <div class="value">
              <input type="text" class="form-control" v-model="formProps.rack" readonly>
            </div>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-sm-7 col-12">
          <div class="info-row">
            <div class="key w90">Port</div>
            <div class="value">
              <input type="text" class="form-control" v-model="formProps.port" readonly>
            </div>
          </div>
        </div>
        <div class="col-sm-5 col-12">
          <div class="info-row">
            <div class="key w100">Vpi</div>
            <div class="value">
              <input type="text" class="form-control" v-model="formProps.vpi" readonly>
            </div>
          </div>
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
            <div class="key w100">Hộp cáp ngọn</div>
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
            <div class="key w100">Tủ cáp ngọn</div>
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
            <div class="key w100">Đôi cáp ngọn</div>
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
            <div class="key w100">Cáp ngọn</div>
            <div class="value">
              <input type="text" class="form-control" v-model="formProps.cap_p" readonly>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="col-sm-4 col-12">      
      <div class="info-row">
        <div class="key w80">Thiết bị</div>
        <div class="value">
          <div class="select-custom">
            <SelectExt 
                    placeholder="Tìm kiếm"  
                    class="disabled" 
                    v-model="formProps.thietbi_id" 
                    :allowFiltering="true" 
                    dataTextField="TEN_TBI"
                    dataValueField="THIETBI_ID" 
                    :dataSource="optionProps.cbx_thietbi_ims" 
                    @change="selectThietbi($event)">
                </SelectExt>
          </div>
        </div>
      </div>
      <div class="info-row">
        <div class="key w80">Dslam</div>
        <div class="value">
          <div class="select-custom">
            <SelectExt 
                      placeholder="Tìm kiếm" 
                      class="disabled"  
                      v-model="formProps.dslam_id" 
                      :allowFiltering="true" 
                      dataTextField="NAME"
                      dataValueField="ID" 
                      :dataSource="optionProps.cbx_dslam" 
                      @change="selectDslam($event)">
                  </SelectExt>
          </div>
        </div>
      </div>
      <div class="info-row">
        <div class="key w80">System</div>
        <div class="value">
          <input type="text" class="form-control" v-model="formProps.system" readonly>
        </div>
      </div>
      <div class="info-row">
        <div class="key w80">Shelf</div>
        <div class="value">
          <input type="text" class="form-control" v-model="formProps.shelf" readonly>
        </div>
      </div>
      <div class="info-row">
        <div class="key w80">Vci</div>
        <div class="value">
          <input type="text" class="form-control" v-model="formProps.vci" readonly>
        </div>
      </div>
      <div class="info-row mart40">
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
  name: "IMS",
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
      selectThietbi(){

      },
      selectDslam(){

      },
      selectBras(){

      },
      selectLoaicap(){

      },
  },
};
</script>            
