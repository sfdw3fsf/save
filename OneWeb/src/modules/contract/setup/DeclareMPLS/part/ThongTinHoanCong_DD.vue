<template>
  <div>
    <div class="title-bg-main padt0 padb0">
      <div class="list-checks">
        <div class="item vcenter">
          <span class="title">Điểm đầu</span>
        </div>
        <div class="item vcenter">Chọn port</div>
        <div class="item vcenter">
          <button
            class="btn btn-second button-not-bg"
            @click="btnTDongOnClick()"
          >
            <span class="-ap icon-check"></span> T. động
          </button>
        </div>
        <div class="item vcenter">
          <button :class="options.options_controls && options.options_controls.btnChonPort && options.options_controls.btnChonPort.enabled ? 'btn btn-second button-not-bg' : 'btn btn-second button-not-bg disabled'" 
            @click="btnNCongOnClick()"
          >
            <span class="-ap icon-check"></span> N. công
          </button>
        </div>
        <div class="item vcenter dropdown">
          <button
            class="btn btn-main h31"
            ref="DDref"
            @click="setDisplayTable()"
          >
            <span ref="childrenDDref" class="fa fa-angle-down"></span>
          </button>
          <div
            ref="childrenDDrefTable"
            :class="
              formProps.isDisplayTable
                ? 'dropdown-menu padt0 d-block'
                : 'dropdown-menu padt0'
            "
            style="width: 520px"
          >
            <vue-table
              ref="vueTable"
              v-model="formProps.table.currentRecord"
              :options="formProps.table.options"
              :config="formProps.table.config"
              :loading="formProps.table.loading"
              :unique="'PHANLOAI_ID'"
              :multiple="false"
            ></vue-table>
          </div>
        </div>
        <div class="item red">Liên hệ: {{ formProps.lblLienHe_Dau }}</div>
      </div>
    </div>
    <div class="row">
      <div class="col-sm-6 col-12">
        <div class="row">
          <div class="col-sm-6 col-12">
            <vue-input
              v-model="formProps.txtSoAoDau"
              :labelWidth="'80'"
              :highlight="true"
              label="Số ảo"
              :checkbox="false"
              :disable="true"
            >
            </vue-input>
          </div>
          <div class="col-sm-6 col-12">
            <vue-select
              v-model="formProps.cboThietBiDau"
              :labelWidth="'70'"
              :options="options.options_thietBi"
              id="loaiHinh"
              labelField="ten_tbi"
              valueField="thietbidc_id"
              label="Thiết bị"
            ></vue-select>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-6 col-12">
            <vue-input
              v-model="formProps.txtSvlan_Dau"
              :disable="controls.txtSvlan_Dau.readOnly"
              :labelWidth="'80'"
              label="Svlan"
              class="vue-text-black vue-font-weight-bold tright" 
              :checkbox="false"
            >
            </vue-input>
          </div>
          <div class="col-sm-6 col-12">
            <vue-input
              v-model="formProps.txtCvlan_Dau"
              class="vue-text-black vue-font-weight-bold tright"
              :disable="controls.txtCvlan_Dau.readOnly"
              :labelWidth="'70'"
              label="Cvlan"
              :checkbox="false"
            >
            </vue-input>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-6 col-12">
            <vue-input
              v-model="formProps.txtAdsl_cap_Dau"
              :disable="controls.txtAdsl_cap_Dau.readOnly"
              :labelWidth="'80'"
              label="Adsl cáp"
              class="vue-text-black vue-font-weight-bold tright" 
              :checkbox="false"
            >
            </vue-input>
          </div>
          <div class="col-sm-6 col-12">
            <vue-input
              v-model="formProps.txtAdsl_port_Dau"
              :disable="controls.txtAdsl_port_Dau.readOnly"
              :labelWidth="'70'"
              label="Adsl port"
              class="vue-text-black vue-font-weight-bold tright"
              :checkbox="false"
            >
            </vue-input>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-6 col-12">
            <vue-input
              v-model="formProps.txtNasport_id_Dau"
              :disable="controls.txtNasport_id_Dau.readOnly"
              :labelWidth="'80'"
              class="vue-text-black vue-font-weight-bold"

              label="Nasport ID"
              :checkbox="false"
            >
            </vue-input>
          </div>
          <div class="col-sm-6 col-12">
            <vue-input
              v-model="formProps.txtVrf_Dau"
              :labelWidth="'70'"
              class="line-red tright"
              :required="true"
              label="Vrf/Vfi"
               :highlight="true"
              :checkbox="false"
            >
            </vue-input>
          </div>
        </div>
        <vue-input
          v-model="formProps.txtIpDslam_Dau"
          :disable="controls.txtIpDslam_Dau.readOnly"
          class="vue-text-black vue-font-weight-bold"
          :labelWidth="'80'"
          label="Ip Dslam"
          :checkbox="false"
        >
        </vue-input>
        <div class="row">
          <div class="col-sm-6 col-12">
            <vue-input
              v-model="formProps.txtTBi_Dau"
              :labelWidth="'80'"
              :highlight="true"
              class="line-red tright"
              :required="true"
              label="TB t.dẫn"
              :checkbox="false"
            >
            </vue-input>
          </div>
          <div class="col-sm-6 col-12">
            <!-- :disable="controls.txtIpwan_Dau.readOnly" -->
            <vue-input
              v-model="formProps.txtIpwan_Dau"
              :labelWidth="'70'"
              class="line-red tright"
              :required="true"
               :highlight="true"
              label="Ip Wan"
              :checkbox="false"
            >
            </vue-input>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-6 col-12">
            <!-- :disable="controls.txtTimeSlot_Dau.readOnly" -->

            <vue-input
              v-model="formProps.txtTimeSlot_Dau"
              :labelWidth="'80'"
              :highlight="true"
              class="line-red tright"
              :required="true"
              label="TimeSlot"
              :checkbox="false"
            >
            </vue-input>
          </div>
          <div class="col-sm-6 col-12">
            <!-- :disable="controls.txtLink_Dau.readOnly" -->

            <vue-input
              v-model="formProps.txtLink_Dau"
              :labelWidth="'70'"
              class="line-red tright"
              :required="true"
              :highlight="true"
              label="Link"
              :checkbox="false"
            >
            </vue-input>
          </div>
        </div>
        <vue-input
          v-model="formProps.txtDiaChiDau"
          :labelWidth="'80'"
              class="vue-text-black vue-font-weight-bold"

          label="Địa chỉ mới"
          :checkbox="false"
        >
        </vue-input>
        <vue-input
          v-model="formProps.txtLyDoTra_Dau"
          :disable="true"
          :labelWidth="'80'"
          label="Lý do trả"
          :checkbox="false"
        >
        </vue-input>

        <div class="info-row">
          <div class="key w80">ND TH <span class="required">*</span></div>
          <div class="value">
            <div class="input-more-button">
              <button class="btn" @click="btnNDTHOnClick()">
                <span class="nc-icon-glyph ui-1_edit-76 f20"></span>
              </button>
              <div class="value">
                <input
                  type="text"
                  class="form-control red"
                  maxlength="1000"
                  v-model="formProps.txtNoiDungTH" 
                />
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="col-sm-6 col-12">
        <div class="row">
          <div class="col-sm-6 col-12">
            <vue-input
              v-model="formProps.txtSerial_Dau"
              :disable="controls.txtSerial_Dau.readOnly"
              :labelWidth="'90'"
              label="Serial"
              :checkbox="false"
            >
            </vue-input>
          </div>
          <div class="col-sm-6 col-12">
            <vue-select
              v-model="formProps.cboLoaiCap_Dau"
              :disable="true"
              :labelWidth="'100'"
              :options="options.options_loaiCap"
              id="loaiHinh"
              labelField="LOAICAP"
              valueField="LOAICAP_ID"
              label="Loại cáp"
            ></vue-select>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-6 col-12">
            <vue-input
              v-model="formProps.txtCapGoc_Dau"
              :disable="controls.txtCapGoc_Dau.readOnly"
              :labelWidth="'90'"
              label="Cáp gốc"
              :checkbox="false"
            >
            </vue-input>
          </div>
          <div class="col-sm-6 col-12">
            <vue-input
              v-model="formProps.txtCapNgon_Dau"
              :disable="controls.txtCapNgon_Dau.readOnly"
              :labelWidth="'100'"
              label="Cáp ngọn"
              :checkbox="false"
            >
            </vue-input>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-6 col-12">
            <vue-input
              v-model="formProps.txtTuCapGoc_Dau"
              :disable="controls.txtTuCapGoc_Dau.readOnly"
              :labelWidth="'90'"
              label="Tủ cáp gốc"
              :checkbox="false"
            >
            </vue-input>
          </div>
          <div class="col-sm-6 col-12">
            <vue-input
              v-model="formProps.txtTuCapNgon_Dau"
              :disable="controls.txtTuCapNgon_Dau.readOnly"
              :labelWidth="'100'"
              label="Tủ cáp ngọn"
              :checkbox="false"
            >
            </vue-input>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-6 col-12">
            <vue-input
              v-model="formProps.txtDoiCapGoc_Dau"
              :disable="controls.txtDoiCapGoc_Dau.readOnly"
              :labelWidth="'90'"
              label="Đôi cáp gốc"
              :checkbox="false"
            >
            </vue-input>
          </div>
          <div class="col-sm-6 col-12">
            <vue-input
              v-model="formProps.txtDoiCapNgon_Dau"
              :disable="controls.txtDoiCapNgon_Dau.readOnly"
              :labelWidth="'100'"
              label="Đôi cáp ngọn"
              :checkbox="false"
            >
            </vue-input>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-6 col-12">
            <vue-input
              v-model="formProps.txtLienTu_Dau"
              :disable="controls.txtLienTu_Dau.readOnly"
              :labelWidth="'90'"
              label="Liên tủ"
              :checkbox="false"
            >
            </vue-input>
          </div>
          <div class="col-sm-6 col-12">
            <vue-input
              v-model="formProps.txtHopCapNgon_Dau"
              :disable="controls.txtHopCapNgon_Dau.readOnly"
              :labelWidth="'100'"
              label="Hộp cáp ngọn"
              :checkbox="false"
            >
            </vue-input>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-6 col-12">
            <vue-input
              v-model="formProps.txtSoMetDay_Dau"
              :disable="controls.txtSoMetDay_Dau.readOnly"
              :labelWidth="'90'"
              label="Cự ly"
              :checkbox="false"
            >
            </vue-input>
          </div>
          <div class="col-sm-6 col-12">
            <vue-input
              v-model="formProps.txtTuyenCap_Dau"
              :disable="controls.txtTuyenCap_Dau.readOnly"
              :labelWidth="'100'"
              label="Tuyến cáp"
              :checkbox="false"
            >
            </vue-input>
          </div>
        </div>
        <vue-select
          v-model="formProps.cboDonViDau"
          :labelWidth="'90'"
          :options="options.options_donVi"
          id="donVi"
          labelField="ten_dv"
          valueField="donvi_id"
          label="Đơn vị"
        ></vue-select>
        <vue-input
          v-model="formProps.txtDiaChiDau_Cu"
          :disable="true"
          :labelWidth="'90'"
          label="Địa chỉ cũ"
          :checkbox="false"
        >
        </vue-input>
        <vue-input
          v-model="formProps.txtNoiDungTra_Dau"
          :disable="true"
          :labelWidth="'90'"
          label="Nội dung trả"
          :checkbox="false"
        >
        </vue-input>
        <vue-input
          v-model="formProps.txtNDGiao"
          :labelWidth="'90'"
          label="ND giao"
          :checkbox="false"
        >
        </vue-input>
      </div>
    </div>
    <ChonPort ref="ref-chon-port" @select="onSelectPort"/>
  </div>
</template>
<script>

import ChonPort from "@/modules/EXTS/QUANTRI/ThayDoiThongSoInternet/chon-port"
import {TRANGTHAI_PORT, LOAI_HOPDONG} from "@/constants"
import api from "../DeclareMPLSAPI"
import CommonFuntions from '../../ChangeLandlineIMSType/CommonFuntions'
import commonFn from "@/modules/contract/setup/ChangeLandlineIMSType/CommonFuntions"; 

export default {
  components: {ChonPort},
  props: ["formProps", "options", "controls"],
  data: () => ({
    port_dau_id: 0,
    tramtb_dau_id: 0,
    vci_vpi_dau_id: 0,
    port_dau_id_tmp: 0,
    vci_vpi_dau_id_tmp: 0,
    hdtb_id: 0,
    thuebao: null, 
    tt_nd: {}
  }),
  
  watch: {},
  computed: {},
  created() {},
  mounted() {},
  methods: {
    formatYear(e) {
      console.log(e);
      return String(e).substring(0, 4);
    },
    btnTDongOnClick() {
      //alert("btnTDongOnClick");
    },
    async btnNCongOnClick() {
      console.log("btnNCongOnClick", this.options)
      console.log("formProps", this.formProps) 
      this.tt_nd = this.options.options_data.tt_nd;
      
      if (!this.options.options_thuebao || !this.options.options_thuebao.loaitb_id)
      { 
          this.$toast.error("Không có thuê bao để chọn Port");
          return;
      }

      let thuebao = this.options.options_thuebao;
      let hdtb = this.options.options_hdtb; 
      if(!hdtb || hdtb == null){
        this.$toast.error("Chưa có thông tin Port!");
        return;
      }

      if(!thuebao.kieuld_id || thuebao.kieuld_id.length == 0){
        this.$toast.error("Không tìm thấy thông tin!");
        return;
      }
 
      let tt_port = TRANGTHAI_PORT.CHUA_SD;
      let modal = this.$refs["ref-chon-port"];
      // let vdonvi_id = thuebao.donvi_id*1;
      let vdonvi_id = this.formProps.cboDonViDau
      let vtramtb_id = hdtb.tramtb_id ? hdtb.tramtb_id : 0;
      let vport_id = 0;
      let vkieu = 0;
      let vdichvuvt_id = thuebao.dichvuvt_id*1;
      let vloaitb_id = thuebao.loaitb_id;
      let vdiachi_id = hdtb.diachi_id;
      let vthamso = "-1";
      let vhdtb_id = thuebao.hdtb_id*1;
      let vthuebao_id = thuebao.thuebao_id ? thuebao.thuebao_id : 0;
      let vmadoicap = null;
      let vtramql_id = thuebao.donviql_id*1;

      this.hdtb_id = vhdtb_id;
      this.thuebao = thuebao;

      vthamso = await this.Lay_thamso_port(vloaitb_id, 1, 0);
     
      this.port_dau_id_tmp = hdtb.port_id ? hdtb.port_id : 0;
      this.vci_vpi_dau_id_tmp = hdtb.vci_vpi_id ? hdtb.vci_vpi_id : 0;

      let diachi_dau_id = -1;
       
      if (thuebao.loaihd_id*1 == LOAI_HOPDONG.DAT_MOI
        || thuebao.loaihd_id*1 == LOAI_HOPDONG.KHOIPHUC_SD
        || thuebao.loaihd_id*1 == LOAI_HOPDONG.DI_CHUYEN){
        //diachi_dau_id = (await CommonFuntions.MAP_ID(this.axios, "diachi_id", "css.hdtb_tsl", "where daucuoi_id = 1 and hdtb_id = " + thuebao.hdtb_id))*1;
        diachi_dau_id = commonFn.GetData(await api.fn_tt_hdtb_tsl(this.axios,  { param: thuebao.hdtb_id, type: 2 } ))
      }
      else{
        //diachi_dau_id = (await CommonFuntions.MAP_ID(this.axios, "diachi_id", "css.db_tsl", "where daucuoi_id = 1 and thuebao_id = " + thuebao.thuebao_id))*1;
        diachi_dau_id = commonFn.GetData(await api.fn_tt_db_tsl(this.axios,  { param: thuebao.thuebao_id, type: 2 } ))
      }

       vdiachi_id = diachi_dau_id;
      
      if (this.options.options_data.luong_id && this.options.options_data.luong_id > 0 && await CommonFuntions.phailam(this.axios, this.options.options_data.luong_id, "AON_CHI_CHON_DSLAM_TINH"))
      {
          //Chỉ chọn dslam tỉnh, ko chọn port đối với thuê bao AON
          //f.ChiCanChonDSLAM = true;
          //ChiCanChonDSLAM = true;
      }

      let frmChonPortData = {};
      frmChonPortData.vdonvi_id = vdonvi_id;
      frmChonPortData.vtramtb_id = vtramtb_id;
      frmChonPortData.vport_id = vport_id;
      frmChonPortData.vkieu = vkieu;
      frmChonPortData.vdichvuvt_id = vdichvuvt_id; 
      frmChonPortData.vloaitb_id = vloaitb_id; 
      frmChonPortData.vdiachi_id = vdiachi_id; 
      frmChonPortData.vthamso = vthamso;
      frmChonPortData.vhdtb_id = vhdtb_id*1;
      frmChonPortData.vthuebao_id = vthuebao_id*1; 
      frmChonPortData.vmadoicap = vmadoicap;
      frmChonPortData.vtramql_id  = vtramql_id;

      console.log("frmChonPortData: ", frmChonPortData)
      modal.frmChonPort(
          frmChonPortData.vdonvi_id, 
          frmChonPortData.vtramtb_id, 
          frmChonPortData.vport_id, 
          frmChonPortData.vkieu, 
          frmChonPortData.vdichvuvt_id, 
          frmChonPortData.vloaitb_id, 
          frmChonPortData.vdiachi_id, 
          frmChonPortData.vthamso, 
          frmChonPortData.vhdtb_id, 
          frmChonPortData.vthuebao_id, 
          frmChonPortData.vmadoicap, 
          frmChonPortData.vtramql_id
        );
      modal.show();
    },
    btnNDTHOnClick() {
      this.$emit("btnNDTHOnClick");
    },
    setDisplayTable() {
      console.log("setDisplayTableDD");
      this.$emit("display-table");
    },
    async onSelectPort(f) {
     
      if (f.ChiCanChonDSLAM && f.IsAONTinh && f.ChiCanChonDSLAM === true && f.IsAONTinh === true)
      {
          // //Chỉ cập nhật lại dslam tỉnh
          // var sql = "update {?DB2}.hdtb_tsl a set a.dslam_tinh_id={?dslam_tinh_id} where hdtb_id={?hdtb_id} and daucuoi_id = 1";
          // sql = sql.ReplaceSchema()
          //     .Replace("{?dslam_tinh_id}", f.dslam_tinh_id + "")
          //     .Replace("{?hdtb_id}", hdtb_id + "");
          // bangts.ExeSQL(sql); 

          await api.sp_capnhat_dslam_hdtb_tsl(this.axios, {
              "dslam_tinh_id": f.dslam_tinh_id,
              "hdtb_id": this.hdtb_id
          }).then((response) => {
              if (response.data.message !== "Success") { 
                  this.$toast.success(response.data.message);
                }
          });
      }
      else{ 
           
          this.formProps.txtSvlan_Dau = f.vpi;
          this.formProps.txtCvlan_Dau = f.vci;
          this.formProps.txtAdsl_cap_Dau = f.port_mdf;
          this.formProps.txtAdsl_port_Dau = f.vadsl_port;
          
          //this.formProps.txtPort_Dau = f.port;
          if(!f.system) f.system = "";
          if (f.selfid01 && f.selfid02 && f.selfid03 && f.selfid04 && f.selfid01 != "" && f.selfid02 != "" && f.selfid03 != "" && f.selfid04 != "")
              this.formProps.txtNasport_id_Dau = f.system + "/" + f.selfid01 + "/" + f.selfid02 + "/" + f.selfid03 + "/" + f.selfid04;
          else
              this.formProps.txtNasport_id_Dau = f.system;

           
          // //txtIpDsLam.Text = f.ip_dslam_tinh.ToString();
          this.tramtb_dau_id = f.tramtb_id;
          // //txtTramTB.Text = checkdata.MAP_ID("ten_dv", DatabaseConstants.DB1 + ".donvi", "where donvi_id = " + tramtb_id);
          this.port_dau_id = f.port_id;
          this.vci_vpi_dau_id = f.vci_vpi_id;
          // //cboBrasADSL.SelectedValue = f.bras_id_vdc;
          // //cboDSLAM.SelectedValue = f.dslam_id_vdc;
          //this.$refs.txtAdsl_port_Dau.focus();
          this.loading(true);
          try{ 
            await this.CapNhat_port(this.hdtb_id, this.thuebao.ma_tb, this.vci_vpi_dau_id, this.port_dau_id, this.port_dau_id_tmp, this.vci_vpi_dau_id_tmp, this.tramtb_dau_id);    
            this.$emit("onCapNhatPortDau", f);
          }
          catch(e){
            console.log(e);
            this.$toast.error("Lỗi cập nhật Port: "+ e); 
          }

          this.loading(false);
      } 
    },
    async Lay_thamso_port(vloaitb_id, vaon, vgpon){ 
      let data = "";
      await api.lay_thamso_port(this.axios, {
            "vgpon": vgpon,
            "vaon": vaon,
            "vloaitb_id": vloaitb_id
        }).then((response) => {
          if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
              data = response.data.data;
          }
      });
      return data;
    },
    async CapNhat_port(hdtb_id, txtMaTB, vci_vpi_dau_id, port_dau_id, port_dau_id_tmp, vci_vpi_dau_id_tmp, tram_id){  
      console.log('vci_vpi_dau_id', vci_vpi_dau_id);
      console.log('port_dau_id', port_dau_id);
      console.log('port_dau_id_tmp', port_dau_id_tmp);
      console.log('vci_vpi_dau_id_tmp', vci_vpi_dau_id_tmp);
        
          if (port_dau_id != 0)
          {
            if (port_dau_id === port_dau_id_tmp && vci_vpi_dau_id === vci_vpi_dau_id_tmp)  //Có sự thay đổi port mới update trạng thái
            {
              return;
            }

          
            //Cập nhật port cũ
            console.log('Cập nhật port cũ')
            let dsktradb = await this.ktra_dk_port(txtMaTB, port_dau_id, port_dau_id_tmp, 1);
            let dsktrahd = await this.ktra_dk_port(txtMaTB, port_dau_id, vci_vpi_dau_id_tmp, 2);
            
            
              if (dsktradb && dsktrahd && dsktradb.length == 0 && dsktrahd.length == 0)
              {
                  await this.CapNhat_TrangThai_Port(port_dau_id_tmp, vci_vpi_dau_id_tmp, TRANGTHAI_PORT.CHUA_SD);
                  await this.CapNhat_TrangThai_PortID(port_dau_id_tmp, TRANGTHAI_PORT.CHUA_SD);
                  await this.CapNhat_ds_matb(port_dau_id_tmp, vci_vpi_dau_id_tmp, "");
                  await this.Capnhat_port_vatly(port_dau_id_tmp, TRANGTHAI_PORT.CHUA_SD);
              } 
              else if (dsktradb && dsktrahd && dsktradb.length != 0 && dsktrahd.length == 0)
              {
                  let vds_matb = "";
                  for (var i = 0; i < dsktradb.length; i++)
                  {
                      vds_matb = vds_matb + dsktradb[i]["ma_tb"].toString() + "; ";
                      if (vds_matb.length > 3000)
                          break;
                  }
                  if (vds_matb != "")
                      vds_matb = vds_matb.substring(0, vds_matb.length - 2);

                  await this.CapNhat_TrangThai_Port(port_dau_id_tmp, vci_vpi_dau_id_tmp, TRANGTHAI_PORT.DA_SD);
                  await this.CapNhat_TrangThai_PortID(port_dau_id_tmp, TRANGTHAI_PORT.DA_SD);
                  await this.CapNhat_ds_matb(port_dau_id_tmp, vci_vpi_dau_id_tmp, vds_matb);
                  await this.Capnhat_port_vatly(port_dau_id_tmp, TRANGTHAI_PORT.DA_SD);
              }
              else if (dsktradb && dsktrahd && dsktradb.length == 0 && dsktrahd.length != 0)
              {
                  let vds_matb = "";
                  for (var i = 0; i < dsktrahd.length; i++)
                  {
                      vds_matb = vds_matb + dsktrahd[i]["ma_tb"].toString() + "; ";
                      if (vds_matb.length > 3000)
                          break;
                  }
                  if (vds_matb != "")
                      vds_matb = vds_matb.substring(0, vds_matb.length - 2);

                  await this.CapNhat_TrangThai_Port(port_dau_id_tmp, vci_vpi_dau_id_tmp, TRANGTHAI_PORT.TAM_CAP);
                  await this.CapNhat_TrangThai_PortID(port_dau_id_tmp, TRANGTHAI_PORT.DA_SD);
                  await this.CapNhat_ds_matb(port_dau_id_tmp, vci_vpi_dau_id_tmp, vds_matb);
                  await this.Capnhat_port_vatly(port_dau_id_tmp, TRANGTHAI_PORT.DA_SD);
              }
              else if (dsktradb && dsktrahd && dsktradb.length != 0 && dsktrahd.length != 0)
              {
                  let vds_matb = "";
                  for (var i = 0; i < dsktradb.length; i++)
                  {
                      vds_matb = vds_matb + dsktradb[i]["ma_tb"].toString() + "; ";
                  }
                  for (var i = 0; i < dsktrahd[0].length; i++)
                  {
                      vds_matb = vds_matb + dsktrahd[i]["ma_tb"].toString() + "; ";
                      if (vds_matb.length > 3000)
                          break;
                  }

                  if (vds_matb != "")
                      vds_matb = vds_matb.substring(0, vds_matb.length - 2); 
                  
                  await this.CapNhat_TrangThai_Port(port_dau_id_tmp, vci_vpi_dau_id_tmp, TRANGTHAI_PORT.DA_SD);
                  await this.CapNhat_TrangThai_PortID(port_dau_id_tmp, TRANGTHAI_PORT.DA_SD);
                  await this.CapNhat_ds_matb(port_dau_id_tmp, vci_vpi_dau_id_tmp, vds_matb);
                  await this.Capnhat_port_vatly(port_dau_id_tmp, TRANGTHAI_PORT.DA_SD);
              }
              
              
              //Cập nhật port mới
              console.log('Cập nhật port cũ >> Cập nhật port mới')
              dsktradb = await this.ktra_dk_port(txtMaTB, port_dau_id, vci_vpi_dau_id, 1);
              dsktrahd = await this.ktra_dk_port(txtMaTB, port_dau_id, vci_vpi_dau_id, 2);
            
              if (dsktradb && dsktrahd && dsktradb.length == 0 && dsktrahd.length == 0)
              {
                  await this.CapNhat_TrangThai_Port(port_dau_id, vci_vpi_dau_id, TRANGTHAI_PORT.TAM_CAP);
                  await this.CapNhat_TrangThai_PortID(port_dau_id, TRANGTHAI_PORT.DA_SD);
                  await this.CapNhat_ds_matb(port_dau_id, vci_vpi_dau_id, txtMaTB);
                  await this.Capnhat_port_vatly(port_dau_id, TRANGTHAI_PORT.DA_SD);
              }
              else if (dsktradb && dsktrahd && dsktradb.length != 0 && dsktrahd.length == 0)
              {
                  let vds_matb = "";
                  for (var i = 0; i < dsktradb.length; i++)
                  {
                      vds_matb = vds_matb + dsktradb[i]["ma_tb"].toString() + "; ";
                      if (vds_matb.Length > 3000)
                          break;
                  }
                  vds_matb = vds_matb + this.txtMaTB;

                  await this.CapNhat_TrangThai_Port(port_dau_id, vci_vpi_dau_id, TRANGTHAI_PORT.DA_SD);
                  await this.CapNhat_TrangThai_PortID(port_dau_id, TRANGTHAI_PORT.DA_SD);
                  await this.CapNhat_ds_matb(port_dau_id, vci_vpi_dau_id, vds_matb);
                  await this.Capnhat_port_vatly(port_dau_id, TRANGTHAI_PORT.DA_SD); 
              }
              else if (dsktradb && dsktrahd && dsktradb.length == 0 && dsktrahd.length != 0)
              {
                  let vds_matb = "";
                  for (var i = 0; i < dsktrahd.length; i++)
                  {
                      vds_matb = vds_matb + dsktrahd[i]["ma_tb"].toString() + "; ";
                      if (vds_matb.length > 3000)
                          break;
                  }
                  vds_matb = vds_matb + this.txtMaTB;

                  await this.CapNhat_TrangThai_Port(port_dau_id, vci_vpi_dau_id, TRANGTHAI_PORT.TAM_CAP);
                  await this.CapNhat_TrangThai_PortID(port_dau_id, TRANGTHAI_PORT.DA_SD);
                  await this.CapNhat_ds_matb(port_dau_id, vci_vpi_dau_id, vds_matb);
                  await this.Capnhat_port_vatly(port_dau_id, TRANGTHAI_PORT.DA_SD);  
              }
              else if (dsktradb && dsktrahd && dsktradb.length != 0 && dsktrahd.length != 0)
              {
                  let vds_matb = "";
                  for (var i = 0; i < dsktradb.length; i++)
                  {
                      vds_matb = vds_matb + dsktradb[i]["ma_tb"].toString() + "; ";
                      if (vds_matb.length > 3000)
                          break;
                  }
                  for (var i = 0; i < dsktrahd.length; i++)
                  {
                      vds_matb = vds_matb + dsktrahd[i]["ma_tb"].toString() + "; ";
                      if (vds_matb.length > 3000)
                          break;
                  }
                  vds_matb = vds_matb + txtMaTB;

                  await this.CapNhat_TrangThai_Port(port_dau_id, vci_vpi_dau_id, TRANGTHAI_PORT.DA_SD);
                  await this.CapNhat_TrangThai_PortID(port_dau_id, TRANGTHAI_PORT.DA_SD);
                  await this.CapNhat_ds_matb(port_dau_id, vci_vpi_dau_id, vds_matb);
                  await this.Capnhat_port_vatly(port_dau_id, TRANGTHAI_PORT.DA_SD);
              }
            
          }
          else{
            //Cập nhật port mới
            console.log('Cập nhật port mới')
            dsktradb = await this.ktra_dk_port(txtMaTB, port_dau_id, vci_vpi_dau_id, 1);
            dsktrahd = await this.ktra_dk_port(txtMaTB, port_dau_id, vci_vpi_dau_id, 2);
    
            
                if (dsktradb && dsktrahd && dsktradb.length == 0 && dsktrahd.length == 0)
                {
                    await this.CapNhat_TrangThai_Port(port_dau_id, this.vci_vpi_dau_id, TRANGTHAI_PORT.TAM_CAP);
                    await this.CapNhat_TrangThai_PortID(port_dau_id, TRANGTHAI_PORT.DA_SD);
                    await this.CapNhat_ds_matb(port_dau_id, vci_vpi_dau_id, txtMaTB);
                    await this.Capnhat_port_vatly(port_dau_id, TRANGTHAI_PORT.DA_SD);
                }
                else if (dsktradb && dsktrahd && dsktradb.length != 0 && dsktrahd.length == 0)
                {
                    let vds_matb = "";
                    for (var i = 0; i < dsktradb.length; i++)
                    {
                        vds_matb = vds_matb + dsktradb[i]["ma_tb"].toString() + "; ";
                        if (vds_matb.Length > 3000)
                            break;
                    }
                    vds_matb = vds_matb + this.txtMaTB;

                    await this.CapNhat_TrangThai_Port(port_dau_id, vci_vpi_dau_id, TRANGTHAI_PORT.DA_SD);
                    await this.CapNhat_TrangThai_PortID(port_dau_id, TRANGTHAI_PORT.DA_SD);
                    await this.CapNhat_ds_matb(port_dau_id, vci_vpi_dau_id, vds_matb);
                    await this.Capnhat_port_vatly(port_dau_id, TRANGTHAI_PORT.DA_SD); 
                }
                else if (dsktradb && dsktrahd && dsktradb.length == 0 && dsktrahd.length != 0)
                {
                    let vds_matb = "";
                    for (var i = 0; i < dsktrahd.length; i++)
                    {
                        vds_matb = vds_matb + dsktrahd[i]["ma_tb"].toString() + "; ";
                        if (vds_matb.length > 3000)
                            break;
                    }
                    vds_matb = vds_matb + txtMaTB;

                    await this.CapNhat_TrangThai_Port(port_dau_id, vci_vpi_dau_id, TRANGTHAI_PORT.TAM_CAP);
                    await this.CapNhat_TrangThai_PortID(port_dau_id, TRANGTHAI_PORT.DA_SD);
                    await this.CapNhat_ds_matb(port_dau_id, vci_vpi_dau_id, vds_matb);
                    await this.Capnhat_port_vatly(port_dau_id, TRANGTHAI_PORT.DA_SD);  
                }
                else if (dsktradb && dsktrahd && dsktradb.length != 0 && dsktrahd.length != 0)
                {
                    let vds_matb = "";
                    for (var i = 0; i < dsktradb.length; i++)
                    {
                        vds_matb = vds_matb + dsktradb[i]["ma_tb"].toString() + "; ";
                        if (vds_matb.length > 3000)
                            break;
                    }
                    for (var i = 0; i < dsktrahd.length; i++)
                    {
                        vds_matb = vds_matb + dsktrahd[i]["ma_tb"].toString() + "; ";
                        if (vds_matb.length > 3000)
                            break;
                    }
                    vds_matb = vds_matb + txtMaTB;

                    await this.CapNhat_TrangThai_Port(port_dau_id, vci_vpi_dau_id, TRANGTHAI_PORT.DA_SD);
                    await this.CapNhat_TrangThai_PortID(port_dau_id, TRANGTHAI_PORT.DA_SD);
                    await this.CapNhat_ds_matb(port_dau_id, vci_vpi_dau_id, vds_matb);
                    await this.Capnhat_port_vatly(port_dau_id, TRANGTHAI_PORT.DA_SD);
                }  
            
          }
          

          let dsdslam = await this.Lay_dslam_theo_port_id(port_dau_id);
          if (dsdslam && dsdslam.length > 0)
              tram_id =  dsdslam[0]["donvi_id"]*1;

          //Cập nhật thông số port vào bảng hdtb_tsl
          await this.CAPNHAT_THONGTIN_PORT_TSL(hdtb_id, port_dau_id, vci_vpi_dau_id, tram_id, 1);
          await this.TuDong_Gan_TB_ToaNha(1, hdtb_id);
          console.log('Cap nhat port thanh cong!');
          this.$toast.success("Cập nhật port thành công !"); 
    },
    async ktra_dk_port(ma_tb, port_id, vci_vpi_id, vkieu){ 
      var ds = [];
      await api.ktra_dk_port(this.axios, {
            "ma_tb": ma_tb,
            "port_id": port_id,
            "kieu": vkieu,
            "vci_vpi_id": vci_vpi_id
        }).then((response) => {
          if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
              ds = response.data.data;
          }
          if(response.data.message != "Success" && response.data.message != "No Content"){ 
            throw "ktra_dk_port: " + response.data.message; 
          }
      }).catch(function (error) {
        console.error(error);
        if (error.response) {
          throw "ktra_dk_port: " + error.response.data.message;  
        }
      });

      return ds;
    },
    async CapNhat_TrangThai_Port(port_id, vci_vpi, trangthai){ 
      var ds = true;
      await api.capnhat_trangthai_port(this.axios, {
            "trangthai": trangthai,
            "port_id": port_id,
            "vci_vpi": vci_vpi
        }).then((response) => {
          if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
              //ds = response.data.data;
          }
          if(response.data.message != "Success"){ 
            throw "capnhat_trangthai_port: " + response.data.message; 
            ds = false;
          }
      }); 

      return ds;
    },
    async CapNhat_TrangThai_PortID(port_id, tt_port_id){ 
      var ds = true;
      await api.capnhat_trangthai_portid(this.axios, {
            "tt_port_id": tt_port_id,
            "port_id": port_id
        }).then((response) => {
          if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
              ds = response.data.data;
          }
          if(response.data.message != "Success"){ 
            throw "capnhat_trangthai_portid: " + response.data.message; 
            ds = false;
          }
      }); 

      return ds;
    },
    async CapNhat_ds_matb(port_id, vci_vpi_id, ds_matb){ 
      var ds = true;
      await api.capnhat_ds_matb(this.axios, {
          "ds_matb": ds_matb,
          "port_id": port_id,
          "vci_vpi_id": vci_vpi_id
      }).then((response) => {
          if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
              ds = response.data.data;
          }
          if(response.data.message != "Success"){ 
            throw "capnhat_ds_matb: " + response.data.message; 
            ds = false;
          }
      }); 

      return ds;
    },
    async Capnhat_port_vatly(vport_id, vtt_port_id  ){ 

      var ds = true;
      await api.capnhat_port_vatly(this.axios, {
          "vtt_port_id": vtt_port_id,
          "vport_id": vport_id
      }).then((response) => {
          if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
              ds = response.data.data;
          }
          if(response.data.message != "Success"){ 
             
            ds = false;
            throw "capnhat_port_vatly: " + response.data.message;
          }

      }); 

      return ds;
    },
    async CAPNHAT_THONGTIN_PORT_TSL(vhdtb_id, vport_id, vvci_vpi_id, vtramtb_id, vdaucuoi_id){ 
      var ds = true;
      await api.capnhat_thongtin_port_tsl(this.axios, {
          "vhdtb_id": vhdtb_id,
          "vdaucuoi_id": vdaucuoi_id,
          "vtramtb_id": vtramtb_id,
          "vport_id": vport_id,
          "vvci_vpi_id": vvci_vpi_id
      }).then((response) => {
          if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
              ds = response.data.data;
          }
          if(response.data.message != "Success"){ 
            throw "capnhat_thongtin_port_tsl: " + response.data.message; 
            ds = false;
          }
      }); 
      return ds;
    },

    
    async Lay_dslam_theo_port_id(port_id){ 
      var ds = [];
      await api.lay_dslam_theo_port_id(this.axios, {
          "port_id": port_id
      }).then((response) => {
          if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
              ds = response.data.data;
          }
          if(response.data.message != "Success"){ 
            //this.$toast.error(response.data.message); 
            throw "Lay_dslam_theo_port_id: " + response.data.message
          }
      }); 
      return ds;
    },
    
    async TuDong_Gan_TB_ToaNha(vdaucuoi_id, vhdtb_id){ 
      var ds = [];
      await api.tudong_capnhat_toanha(this.axios, {
          "vdaucuoi_id": vdaucuoi_id,
          "vhdtb_id": vhdtb_id,
          "vip_cn": this.tt_nd.ip_cn,
          "vthuebao_id": 0
        }).then((response) => {
          if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
              let kq = ds = response.data.data; 
              if (kq.indexOf("OK")>=0)
              {
                  if (kq.split('-')[1] != "NULL")
                    this.$toast.success("Tự động gán thuê bao vào tòa nhà: " + kq.split('-')[1]);  
              }
              else
              { 
                  this.$toast.warning(kq.toString());  
              }
          }
          if(response.data.message != "Success"){ 
            //this.$toast.error(response.data.message); 
            throw "tudong_capnhat_toanha: " + response.data.message
          }
      }); 
      return ds;
    },

  },
   
};
</script>

