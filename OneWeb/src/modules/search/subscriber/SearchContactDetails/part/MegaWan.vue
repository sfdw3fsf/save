<template>
  <div>
    <div class="row">
      <div class="col-sm-8 col-12">
        <div class="row">
          <div class="col-sm-7 col-12">
            <div class="info-row">
              <div class="key w90">Vệ tinh</div>
              <div class="value">
                <div class="select-custom">
                  <SelectExt class="disabled"  
                    placeholder="Tìm kiếm"  
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
              <div class="key w100">Mã TN</div>
              <div class="value">                
                <input readonly  type="text" class="form-control highlight" v-model="formProps.matb_tn" >
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-7 col-12">
            <div class="info-row">
              <div class="key w90">Chủ quản</div>
              <div class="value">
                <div class="select-custom">
                  <SelectExt class="disabled"  
                    placeholder="Tìm kiếm"  
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
          </div>
          <div class="col-sm-5 col-12">
            <div class="info-row">
              <div class="key w100">Modem</div>
              <div class="value">
                <div class="select-custom">
                  <SelectExt class="disabled"  
                    placeholder="Tìm kiếm"  
                    v-model="formProps.loaimd_id" 
                    :allowFiltering="true" 
                    dataTextField="loai_md"
                    dataValueField="loaimd_id" 
                    :dataSource="optionProps.cbx_loai_modem" 
                    @change="selectLoaimodem($event)">
                </SelectExt>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-7 col-12">
            <div class="info-row">
              <div class="key w90">Loại node</div>
              <div class="value w120" style="width:140px">
                <div class="select-custom">
                  <SelectExt class="disabled"  
                    placeholder="Tìm kiếm"                      
                    v-model="formProps.loainode_id" 
                    :allowFiltering="true" 
                    dataTextField="loainode"
                    dataValueField="loainode_id" 
                    :dataSource="optionProps.cbx_loainode" 
                    @change="selectLoainode($event)">
                </SelectExt>
                </div>
              </div>
              <div class="key w50 padl10">VRF/VFI</div>
              <div class="value">
                <input readonly  type="text" class="form-control" v-model="formProps.vrf" >
              </div>
            </div>
          </div>
          <div class="col-sm-5 col-12">
            <div class="info-row">
              <div class="key w100">Thời hạn</div>
              <div class="value">
                <div class="select-custom">
                  <SelectExt class="disabled"  
                    placeholder="Tìm kiếm"  
                    v-model="formProps.thoihan_id" 
                    :allowFiltering="true" 
                    dataTextField="thoihan"
                    dataValueField="thoihan_id" 
                    :dataSource="optionProps.cbx_thoihan" 
                    @change="selectThoihan($event)">
                </SelectExt>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-7 col-12">
            <div class="info-row">
              <div class="key w90">Tốc độ CIR</div>
              <div class="value">                
                <SelectExt class="disabled"  
                    placeholder="Tìm kiếm"  
                    v-model="formProps.tocdo_id" 
                    :allowFiltering="true" 
                    dataTextField="TT_TOCDO"
                    dataValueField="TOCDO_ID" 
                    :dataSource="optionProps.cbx_tocdo_kenh" 
                    @change="selectTocdocir($event)">
                </SelectExt>
              </div>
            </div>
            <div class="info-row">
              <div class="key w90">Hướng KN</div>

              <div class="value">
                <input readonly  type="text" class="form-control" :title="formProps.huongketnoi_megawan"  v-model="formProps.huongketnoi_megawan" />
              </div>
            </div>
            <div class="info-row">
              <div class="key w90">Địa chỉ POP</div>

              <div class="value">
                <input readonly  type="text" class="form-control"  v-model="formProps.diachi_pop" />
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
                <SelectExt class="disabled"  
                    placeholder="Tìm kiếm"  
                    v-model="formProps.doitac_id" 
                    :allowFiltering="true" 
                    dataTextField="ten_dt"
                    dataValueField="doitac_id" 
                    :dataSource="optionProps.cbx_doitac" 
                    @change="selectDoitac($event)">
                </SelectExt>
              </div>
            </div>
            <div class="info-row">
              <div class="key w90">Mã HĐ HT</div>

              <div class="value">
                <input readonly 
                  type="text"
                  class="form-control"
                  style="background-color: #f1f8ff"
                   v-model="formProps.mahd_dt"
                />
              </div>
            </div>
            <div class="info-row">
              <div class="key w90">Loại cáp</div>

              <div class="value">
                <div class="select-custom">
                  <SelectExt class="disabled"  
                      placeholder="Tìm kiếm"  
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
            <div class="info-row">
              <div class="key w90">Tủ cáp gốc</div>
              <div class="value">
                <input readonly  type="text" class="form-control" v-model="formProps.tucap_goc" />
              </div>
            </div>
            <div class="info-row">
              <div class="key w90">Đôi cáp gốc</div>

              <div class="value">
                <input readonly  type="text" class="form-control"  v-model="formProps.doicap_goc" />
              </div>
            </div>
            <div class="info-row">
              <div class="key w90">Cáp gốc</div>

              <div class="value">
                <input readonly  type="text" class="form-control"  v-model="formProps.cap_goc" />
              </div>
              <div class="key w70 padl0">Công nghệ</div>

              <div class="value">
                <input readonly  type="text" class="form-control"  v-model="formProps.congnghe" />
              </div>
            </div>
            <div class="info-row">
              <div class="key w90">Cước ISP</div>

              <div class="value">
                <input readonly  type="text" class="form-control"  v-model="formProps.cuoc_isp" />
              </div>
              <div class="key w70 padl0">Cước NIX</div>

              <div class="value">
                <input readonly  type="text" class="form-control"  v-model="formProps.cuoc_nix" />
              </div>
            </div>
          </div>
          <div class="col-sm-5 col-12">
            <div class="info-row">
              <div class="key w100">
                <div class="check-action">
                  <input readonly  type="checkbox" class="check" />
                  <span class="name text-main">Tốc độ PIR</span>
                </div>
              </div>
              <div class="value">
                <div class="select-custom">
                  <SelectExt class="disabled"  
                    placeholder="Tìm kiếm"  
                    v-model="formProps.tocdo_pir_id" 
                    :allowFiltering="true" 
                    dataTextField="TT_TOCDO"
                    dataValueField="TOCDO_ID" 
                    :dataSource="optionProps.cbx_tocdo_kenh" 
                    @change="selectTocdopir($event)">
                </SelectExt>
                </div>
              </div>
            </div>
            <div class="info-row">
              <div class="key w100">Site ID</div>
              <div class="value">
                <input readonly  type="text" class="form-control"  v-model="formProps.site_id" />
              </div>
            </div>
            <div class="info-row">
              <div class="key w100">Q.Gia POP</div>
              <div class="value">
                <div class="select-custom">
                  <SelectExt class="disabled"  
                    placeholder="Tìm kiếm"  
                    v-model="formProps.quocgia_pop" 
                    :allowFiltering="true" 
                    dataTextField="tenquoctich"
                    dataValueField="quoctich_id" 
                    :dataSource="optionProps.cbx_quoctich" 
                    @change="selectQuoctich($event)">
                </SelectExt>
                </div>
              </div>
            </div>
            <div class="info-row">
              <div class="key w100">Cước TKĐT</div>
              <div class="value">
                <input readonly 
                  type="text"
                  class="form-control tright"
                  style="background-color: #f1f8ff"
                   v-model="formProps.cuoc_tkdt" 
                />
              </div>
            </div>
            <div class="info-row">
              <div class="key w100">Cước TCĐT</div>
              <div class="value">
                <input readonly 
                  type="text"
                  class="form-control tright"
                  style="background-color: #f1f8ff"
                   v-model="formProps.cuoc_tcdt" 
                />
              </div>
            </div>
            <div class="info-row">
              <div class="key w100">Hộp cáp ngọn</div>
              <div class="value">
                <input readonly  type="text" class="form-control" v-model="formProps.hopcap_p" />
              </div>
            </div>
            <div class="info-row">
              <div class="key w100">Tủ cáp ngọn</div>
              <div class="value">
                <input readonly  type="text" class="form-control" v-model="formProps.tucap_p" />
              </div>
            </div>
            <div class="info-row">
              <div class="key w100">Đôi cáp ngọn</div>
              <div class="value">
                <input readonly  type="text" class="form-control" v-model="formProps.doicap_p" />
              </div>
            </div>
            <div class="info-row">
              <div class="key w100">Cáp ngọn</div>
              <div class="value">
                <input readonly  type="text" class="form-control" v-model="formProps.cap_p" />
              </div>
            </div>
            <div class="info-row">
            <div class="key w100">Cước IP</div>
            <div class="value">
              <input readonly  type="text" class="form-control" v-model="formProps.cuoc_ip"/>
            </div>
          </div>
          </div>
        </div>
      </div>
      <div class="col-sm-4 col-12">        
        <div class="info-row marb10">
          <div class="key">
            <div class="check-action">
              <input readonly  type="checkbox" class="check" v-model="formProps.giukenh"/>
              <span class="name text-main">Luồng giữ kênh</span>
            </div>
          </div>
        </div>
        <div class="info-row">
          <div class="key w80">Loại kênh</div>
          <div class="value">
            <div class="select-custom">
              <SelectExt class="disabled"  
                    placeholder="Tìm kiếm"  
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
          <div class="key w80">Cước TC</div>
          <div class="value">
            <input readonly  type="text" class="form-control" v-model="formProps.cuoc_tc"/>
          </div>
        </div>
        <div class="info-row">
          <div class="key w80">Cước TK</div>
          <div class="value">
            <input readonly  type="text" class="form-control" v-model="formProps.cuoc_tk"/>
          </div>
        </div>
        <div class="info-row">
          <div class="key w80">Cước HT</div>
          <div class="value">
            <input readonly  type="text" class="form-control" v-model="formProps.cuoc_ht"/>
          </div>
        </div>
        <div class="info-row">
          <div class="key w80">Hướng ƯT</div>
          <div class="value">
            <input readonly  type="text" class="form-control" v-model="formProps.huong_ut"/>
          </div>
        </div>
        <div class="info-row">
          <div class="key w80">Cước Tbi</div>
          <div class="value">
            <input readonly  type="text" class="form-control" v-model="formProps.cuoc_tbi"/>
          </div>
        </div>
        <div class="info-row">
          <div class="key w80">Adsl port</div>
          <div class="value">
            <input readonly  type="text" class="form-control" v-model="formProps.adsl_port"/>
          </div>
        </div>
        <div class="info-row">
          <div class="key w80">Port MDF</div>
          <div class="value">
            <input readonly  type="text" class="form-control" v-model="formProps.port_mdf"/>
          </div>
        </div>
        <div class="info-row">
          <div class="key w80">Tuyến cáp</div>
          <div class="value">
            <input readonly  type="text" class="form-control" v-model="formProps.tuyencap"/>
          </div>
        </div>
        <div class="info-row">
          <div class="key w80">Liên tủ</div>
          <div class="value">
            <input readonly  type="text" class="form-control" v-model="formProps.lientu"/>
          </div>
        </div>
        <div class="info-row">
          <div class="key w80">Cự ly</div>
          <div class="value">
            <input readonly  type="text" class="form-control" v-model="formProps.culy"/>
          </div>
        </div>   
        <div class="info-row">
          <div class="key tright">
            <div class="check-action">
              <input readonly  type="checkbox" class="check" v-model="formProps.capngoai_vnpt"/>
              <span class="name">Cáp ngoài VNPT</span>
            </div>
          </div>
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
  name: "MegaWan",
  props: ["formProps", "optionProps","dichvuvt_id"],
  components: {
    KDatePicker,
  },
  data() {
    return {
      DichVuVienThong: {
        TATCA: -1,
        CO_DINH: 1,
        DI_DONG: 2,
        ADSL: 4,
        DD_TRATRUOC: 5,
        EMAIL: 6,
        METRONET: 7,
        MEGAWAN: 8,
        TSL: 9,
        GPHONE: 10,
        IMS: 11,
        MEGA_EYES: 12,
        HOINGHI_TRUYENHINH: 13,
        ANTOAN_BAOMAT_TT: 14,
        DICHVU_CNTT: 15,
        TRUNGTAM_DULIEU: 16,
        HATANG_VIENTHONG: 19,

        IMS_DOITAC: 21,
        BRCD_DOITAC: 22,
        MGW_DOITAC: 23,
        TSL_DOITAC: 24
      },
    };
  },
  methods: {},
};
</script>            
