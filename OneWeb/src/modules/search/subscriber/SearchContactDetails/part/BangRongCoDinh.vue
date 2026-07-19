<template>
  
    <div class="row">
      <div class="col-sm-8 col-12">        
        <div class="row">
          <div class="col-sm-7 col-12">
            <div class="info-row">
              <div class="key w90">Email</div>
              <div class="value">
                <input type="text" class="form-control" v-model="formProps.email" readonly>
              </div>
            </div>
          </div>
          <div class="col-sm-5 col-12">
            <div class="info-row">
              <div class="key w90">Mức cước</div>
              <div class="value">
                <div class="select-custom">
                  <SelectExt 
                    placeholder="Tìm kiếm" 
                    class="disabled" 
                    v-model="formProps.muccuoc_id" 
                    :allowFiltering="true" 
                    dataTextField="TENMUC"
                    dataValueField="MUCCUOC_ID" 
                    :dataSource="optionProps.cbx_muccuoc" 
                    @change="selectMuccuoc($event)">
                </SelectExt>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-7 col-12">
            <div class="info-row">
              <div class="key w90">Tốc độ</div>
              <div class="value">
                <div class="select-custom">
                  <SelectExt 
                    placeholder="Tìm kiếm"  
                    class="disabled" 
                    v-model="formProps.tocdo_id" 
                    :allowFiltering="true" 
                    dataTextField="thuonghieu"
                    dataValueField="tocdo_id" 
                    :dataSource="optionProps.cbx_tocdo" 
                    @change="selectTocdo($event)">
                </SelectExt>
                </div>
              </div>
            </div>
          </div>
          <div class="col-sm-5 col-12">
            <div class="info-row">
              <div class="key w90">Tốc độ thực</div>
              <div class="value">
                <div class="select-custom">
                  <SelectExt 
                    placeholder="Tìm kiếm"  
                    class="disabled" 
                    v-model="formProps.tocdothuc_id" 
                    :allowFiltering="true" 
                    dataTextField="thuonghieu"
                    dataValueField="tocdo_id" 
                    :dataSource="optionProps.cbx_tocdothuc" 
                    @change="selectTocdothuc($event)">
                </SelectExt>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-7 col-12">
            <div class="info-row">
              <div class="key w90">Tổ thi công</div>
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
          </div>
          <div class="col-sm-5 col-12">
            <div class="info-row">
              <div class="key w90">BRAS</div>
              <div class="value bras-value">
                <div class="input-more-button">
                  <button class="btn" @click="btbBrasClick">
                    <span class="-ap icon-info f20"></span>
                  </button> 
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
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-7 col-12">
            <div class="info-row">
              <div class="key w90">IP Dslam</div>
              <div class="value">
                <input type="text" class="form-control" v-model="formProps.ip_dslam" readonly>
              </div>
            </div>
          </div>
          <div class="col-sm-5 col-12">
            <div class="info-row">
              <div class="key w90">DSLAM</div>
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
          </div>
        </div>
        <div class="row">
          <div class="col-sm-7 col-12">
            <div class="info-row">
              <div class="key w90">System</div>
              <div class="value">
                <input type="text" class="form-control" v-model="formProps.system" readonly>
              </div>
            </div>
          </div>
          <div class="col-sm-5 col-12">
            <div class="info-row">
              <div class="key w90">Shelf</div>
              <div class="value">
                <input type="text" class="form-control" v-model="formProps.shelf" readonly>
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-7 col-12">
            <div class="info-row">
              <div class="key w90">Rack</div>
              <div class="value">
                <input type="text" class="form-control" v-model="formProps.rack" readonly>
              </div>
            </div>
          </div>
          <div class="col-sm-5 col-12">
            <div class="info-row">
              <div class="key w90">Port</div>
              <div class="value">
                <input type="text" class="form-control" v-model="formProps.port" readonly>
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
              <div class="key w90">VCI/CVLAN</div>
              <div class="value">
                <input type="text" class="form-control" v-model="formProps.vci" readonly>
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-7 col-12">
            <div class="info-row">
              <div class="key w90">VPI/SVLAN</div>
              <div class="value">
                <input type="text" class="form-control" v-model="formProps.vpi" readonly>
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
              <div class="key w90">Tủ cáp gốc</div>
              <div class="value">
                <input type="text" class="form-control" v-model="formProps.tucap_goc" readonly>
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
              <div class="key w90">Đôi cáp gốc</div>
              <div class="value">
                <input type="text" class="form-control" v-model="formProps.doicap_goc" readonly>
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
              <div class="key w90">Địa chỉ Mac</div>
              <div class="value">
                <input type="text" class="form-control" v-model="formProps.mac_addr" readonly>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="col-sm-4 col-12">        
        <div class="info-row">
          <div class="key w80">Mã TN</div>
          <div class="value">            
            <input type="text" class="form-control highlight" v-model="formProps.matb_tn" readonly>
          </div>
        </div>
        <div class="info-row">
          <div class="key w80">Username</div>
          <div class="value">
            <input type="text" class="form-control highlight" v-model="formProps.username" readonly>
          </div>
        </div>
        <div class="info-row">
          <div class="key w80">Psssword</div>
          <div class="value">
           <input type="text" class="form-control highlight" v-model="formProps.password" readonly>
          </div>
        </div>
        <div class="info-row">
          <div class="key w80">IP tĩnh</div>
          <div class="value">
            <input type="text" class="form-control highlight" v-model="formProps.ip" readonly>
          </div>
        </div>
        <div class="info-row">
          <div class="key w80">NasportID</div>
          <div class="value">
            <input type="text" class="form-control"  v-model="formProps.nasportid" readonly>
          </div>
        </div>
        <div class="info-row">
          <div class="key w80">Port Mdf</div>
          <div class="value">
            <input type="text" class="form-control" v-model="formProps.port_mdf" readonly>
          </div>
        </div>
        <div class="info-row">
          <div class="key w80">ADSL Port</div>
          <div class="value">
            <input type="text" class="form-control" v-model="formProps.adsl_port" readonly>
          </div>
          <div class="value w20 nowrap padt7">
            <div class="check-action text-main">
              <input type="checkbox" class="check disabled" v-model="formProps.vmp_checked"/>
              <span class="name">VMP</span>
            </div>
          </div>
        </div>
        <div class="info-row">
          <div class="key w80">Onu Pass</div>
          <div class="value">
            <input type="text" class="form-control" v-model="formProps.onu_password" readonly>
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
        <div class="info-row">
          <div class="key w80">Công nghệ</div>
          <div class="value">            
            <input type="text" class="form-control tright" v-model="formProps.congnghe" readonly>
          </div>
        </div>
      </div>
      <XemVB id="popupXemVB" :isShow="isShowXemVB"  :title="infoDBTB_title" :content="formProps.infoDBTB" @closeModalXemVB="closeModalXemVB"/>   
    </div>

</template>
<script>
import XemVB from "../popup/XemVB";
import KDatePicker from "@/components/kylq_components/KDatePicker.vue";
import moment from "moment";

import {
  DataManager,
  Query,
  ODataV4Adaptor,
  Predicate,
} from "@syncfusion/ej2-data";
export default {
  name: "BangRongCoDinh",
  props: ["formProps","optionProps",],
  components: {
    KDatePicker,XemVB
  },
  data() {
    return {
      infoDBTB_title:"Thông tin thuê bao",
      isShowXemVB:false,
    };
  },
  methods: {
    selectMuccuoc(){

    },
    selectTocdo(){

    },
    selectTocdothuc(){

    },
    selectTothicong(){

    },
    selectBras(){

    },
    selectDslam(){

    },
    selectLoaicap(){

    },
    btbBrasClick(){
      console.log('btbBrasClick')
      this.isShowXemVB = !this.isShowXemVB;
    },
    closeModalXemVB(){
      this.isShowXemVB = !this.isShowXemVB;
    },
  },
};
</script>            
