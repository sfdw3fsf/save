<template>
  <div class="row">
    <div class="col-sm-8 col-12">
      
      <div class="info-row">
        <div class="key w90">Loại kênh</div>
        <div class="value">
          <div class="select-custom">
            <SelectExt 
                    placeholder="Tìm kiếm" 
                    class="disabled"  
                    v-model="formProps.loaikenh_id" 
                    :allowFiltering="true" 
                    dataTextField="LOAI_KENH"
                    dataValueField="LOAIKENH_ID" 
                    :dataSource="optionProps.cbx_loaikenh" 
                    @change="selectLoaikenh($event)">
                </SelectExt>
          </div>
        </div>
      </div>
      <div class="info-row">
        <div class="key w90">Loại thiết bị</div>
        <div class="value">
          <div class="select-custom">
            <SelectExt 
                    placeholder="Tìm kiếm" 
                    class="disabled"  
                    v-model="formProps.thietbidc_id" 
                    :allowFiltering="true" 
                    dataTextField="TEN_TBI"
                    dataValueField="THIETBIDC_ID" 
                    :dataSource="optionProps.cbx_loaitb" 
                    @change="selectLoaitb($event)">
                </SelectExt>
          </div>
        </div>
      </div>

      <div class="info-row">
        <div class="key w90">Cước TK</div>
        <div class="value">
          <input readonly  type="text" class="form-control" v-model="formProps.cuoc_tk">
        </div>
      </div>
      <div class="info-row">
        <div class="key w90">Cước TC</div>
        <div class="value">
          <input readonly  type="text" class="form-control" v-model="formProps.cuoc_tc">
        </div>
      </div>
      <div class="info-row">
        <div class="key w90">
          <div class="check-action">
            <input readonly  type="checkbox" class="check" />
            <span class="name">Đối tác</span>
          </div>
        </div>
        <div class="value">
          <div class="select-custom">
            <SelectExt 
                    placeholder="Tìm kiếm"  
                    class="disabled" 
                    v-model="formProps.doitac_id" 
                    :allowFiltering="true" 
                    dataTextField="ten_dt"
                    dataValueField="doitac_id" 
                    :dataSource="optionProps.cbx_doitac" 
                    @change="selectDoitac($event)">
                </SelectExt>
          </div>
        </div>
      </div>
      <div class="info-row">
        <div class="key w90">Mã HĐ đối tác</div>
        <div class="value">
          <input readonly  type="text" class="form-control" style="background-color: #f1f8ff"  v-model="formProps.mahd_dt"/>
        </div>
      </div>
      <div class="info-row">
        <div class="key w90">ĐC đầu</div>
        <div class="value">
          <input readonly  type="text" class="form-control"  v-model="formProps.diachi_dau"/>
        </div>
      </div>
      <div class="info-row">
        <div class="key w90">Đơn vị đầu</div>
        <div class="value">
          <div class="select-custom">
            <SelectExt 
                    placeholder="Tìm kiếm"  
                    class="disabled" 
                    v-model="formProps.donvi_id_dau" 
                    :allowFiltering="true" 
                    dataTextField="NAME"
                    dataValueField="ID" 
                    :dataSource="optionProps.cbx_donvi_dau" 
                    @change="selectDVdau($event)">
                </SelectExt>
          </div>
        </div>
      </div>
      <div class="info-row">
        <div class="key w90">ĐC cuối</div>
        <div class="value">
          <input readonly  type="text" class="form-control"  v-model="formProps.diachi_cuoi"/>
        </div>
      </div>
      <div class="info-row">
        <div class="key w90">Đơn vị cuối</div>
        <div class="value">
          <div class="select-custom">
            <SelectExt 
                    placeholder="Tìm kiếm"  
                    class="disabled" 
                    v-model="formProps.donvi_id_cuoi" 
                    :allowFiltering="true" 
                    dataTextField="NAME"
                    dataValueField="ID" 
                    :dataSource="optionProps.cbx_donvi_cuoi" 
                    @change="selectDVcuoi($event)">
                </SelectExt>
          </div>
        </div>
      </div>
    </div>
    <div class="col-sm-4 col-12">      

      <div class="info-row">
        <div class="key w80">TĐ kênh</div>
        <div class="value">
          <div class="select-custom">
            <SelectExt 
                    placeholder="Tìm kiếm"  
                    class="disabled" 
                    v-model="formProps.tocdo_id" 
                    :allowFiltering="true" 
                    dataTextField="TT_TOCDO"
                    dataValueField="TOCDO_ID" 
                    :dataSource="optionProps.cbx_tocdo_kenh" 
                    @change="selectTocdokenh($event)">
                </SelectExt>
          </div>
        </div>
      </div>
      <div class="info-row">
        <div class="key w80">Chủ quản</div>
        <div class="value">
          <div class="select-custom">
            <SelectExt 
                    placeholder="Tìm kiếm"
                    class="disabled"   
                    v-model="formProps.chuquan_id" 
                    :allowFiltering="true" 
                    dataTextField="ten_tat"
                    dataValueField="chuquan_id" 
                    :dataSource="optionProps.cbx_chuquan" 
                    @change="selectChuquan($event)">
                </SelectExt>
          </div>
        </div>
      </div>
      <div class="info-row">
        <div class="key w80">Cước HT</div>
        <div class="value">
          <input readonly  type="text" class="form-control"  v-model="formProps.cuoc_ht"/>
        </div>
      </div>
      <div class="info-row">
        <div class="key w80">Cước Tbi</div>
        <div class="value">
          <input readonly  type="text" class="form-control"  v-model="formProps.cuoc_tbi"/>
        </div>
      </div>
      <div class="info-row">
        <div class="key w80">Cước TKĐT</div>
        <div class="value">
          <input readonly 
            type="text"
            class="form-control tright"
            value="0"
            style="background-color: #f1f8ff"
            v-model="formProps.cuoc_tkdt"
          />
        </div>
      </div>
      <div class="info-row">
        <div class="key w80">Cước TCĐT</div>
        <div class="value">
          <input readonly 
            type="text"
            class="form-control tright"
            value="0"
            style="background-color: #f1f8ff"
            v-model="formProps.cuoc_tcdt"
          />
        </div>
      </div>
      <div class="info-row">
        <div class="key w80">KV đầu</div>
        <div class="value">
          <div class="input-more-button">
            <button class="btn">
              <span class="-ap icon-more_horiz"></span>
            </button>
            <input readonly  type="text" class="form-control" v-model="formProps.khuvuc_dau"/>
          </div>
        </div>
      </div>
      <div class="info-row">
        <div class="key w80">Site ID đầu</div>
        <div class="value">
          <input readonly  type="text" class="form-control" v-model="formProps.site_id_dau"/>
        </div>
      </div>
      <div class="info-row">
        <div class="key w80">KV cuối</div>
        <div class="value">
          <div class="input-more-button">
            <button class="btn">
              <span class="-ap icon-more_horiz"></span>
            </button>
            <input readonly  type="text" class="form-control" v-model="formProps.khuvuc_cuoi"/>
          </div>
        </div>
      </div>
      <div class="info-row">
        <div class="key w80">Site ID cuối</div>
        <div class="value">
          <input readonly  type="text" class="form-control" v-model="formProps.site_id_cuoi"/>
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
  name: "KenhThueRieng",
  props: ["formProps", "optionProps"],
  components: {
    KDatePicker,
  },
  data() {
    return {};
  },
  methods: {
      selectLoaikenh(){

      },
      selectLoaitb(){

      },
      selectDoitac(){

      },
      selectDVdau(){

      },
      selectDVcuoi(){

      },
      selectTocdokenh(){

      },
      selectChuquan(){

      },

  },
};
</script>            
