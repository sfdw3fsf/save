<template>
  <div>
	  <breadcrumb :header="header" />
    <div class="list-actions-top">
      <ul class="list">
        <li>
          <a href="javascript:;" @click="btnLayTT_Click">
            <span class="icon one-file-attach"></span>Lấy TT
          </a>
        </li>
        <li>
          <a href="javascript:;" :class="{ disabled: !button.btnNhapMoi }" @click="btnNhapMoi_Click">
            <span class="icon one-file-plus"></span>Nhập mới
          </a>
        </li>
        <li>
          <a href="javascript:;" :class="{ disabled: !button.btnGhiLai }" @click="btnGhiLai_Click">
            <span class="icon one-save"></span>Ghi lại
          </a>
        </li>
        <li>
          <a href="javascript:;" :class="{ disabled: !button.btnXoa }" @click="btnXoa_Click">
            <span class="icon one-trash"></span>Xóa
          </a>
        </li>
        <li>
          <a href="javascript:;" :class="{ disabled: !button.btnHuyBo }" @click="btnHuyBo_Click">
            <span class="icon nc-icon-glyph ui-1_circle-remove"></span>Hủy
          </a>
        </li>
      </ul>
    </div>
    <div class="page-content">
      <div class="box-form">
        <div class="legend-title">Thông tin profile thiết bị</div>
        <div class="row">
          <div class="col-sm-12 col-12">
            <div class="info-row">
              <div class="key w150">Tỉnh</div>
              <div class="value">
                <div class="select-custom" ref="cboTinh">
                  <Select2 :z-index="2000" :settings="{ dropdownParent: $refs['cboTinh'] }"
                    v-model="cboTinh.value" class="select2" placeholder="Không chọn..." @clear="true" disabled
                    :options="cboTinh.Items && cboTinh.Items.length > 0 ? cboTinh.Items.map(e=>({id:e.tinh_id, text:e.tentinh})) : []">
                  </Select2>
                </div>
              </div>
            </div>
          </div>
          <div class="col-sm-3 col-12">
            <div class="info-row">
              <div class="key w150">{{ cboHangSX.text }}</div>
              <div class="value">
                <div class="select-custom" ref="cboHangSX">
                  <select2 :z-index="2000" :settings="{ dropdownParent: $refs['cboHangSX'] }"
                    v-model="cboHangSX.value" class="select2"
                    :options="cboHangSX.Items">
                  </select2>
                </div>
              </div>
            </div>
          </div>
          <div class="col-sm-3 col-12">
            <div class="info-row">
              <div class="key w150">{{ txtGemProfile.text }}</div>
              <div class="value">
                <input type="text" class="form-control bold" v-bind:value="txtGemProfile.value" @change="e => txtGemProfile.value = e.target.value">
              </div>
            </div>
          </div>
          <div class="col-sm-3 col-12">
            <div class="info-row">
              <div class="key w150">{{ txtUserTelnet.text }}</div>
              <div class="value">
                <input type="text" class="form-control bold" v-bind:value="txtUserTelnet.value" @change="e => txtUserTelnet.value = e.target.value">
              </div>
            </div>
          </div>
          <div class="col-sm-3 col-12">
            <div class="info-row">
              <div class="key w150">{{ txtPasswordTelnet.text }}</div>
              <div class="value">
                <input type="text" class="form-control bold" v-bind:value="txtPasswordTelnet.value" @change="e => txtPasswordTelnet.value = e.target.value">
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-3 col-12">
            <div class="info-row">
              <div class="key w150">{{ txtFTTHBandwidth.text }}</div>
              <div class="value">
                <input type="text" class="form-control bold" v-bind:value="txtFTTHBandwidth.value" @change="e => txtFTTHBandwidth.value = e.target.value">
              </div>
            </div>
          </div>
          <div class="col-sm-3 col-12">
            <div class="info-row">
              <div class="key w150">{{ txtFTTHShaper.text }}</div>
              <div class="value">
                <input type="text" class="form-control bold" v-bind:value="txtFTTHShaper.value" @change="e => txtFTTHShaper.value = e.target.value">
              </div>
            </div>
          </div>
          <div class="col-sm-3 col-12">
            <div class="info-row">
              <div class="key w150">{{ txtMyTVBandwidth.text }}</div>
              <div class="value">
                <input type="text" class="form-control bold" v-bind:value="txtMyTVBandwidth.value" @change="e => txtMyTVBandwidth.value = e.target.value">
              </div>
            </div>
          </div>
          <div class="col-sm-3 col-12">
            <div class="info-row">
              <div class="key w150">{{ txtMyTVShaper.text }}</div>
              <div class="value">
                <input type="text" class="form-control bold" v-bind:value="txtMyTVShaper.value" @change="e => txtMyTVShaper.value = e.target.value">
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-3 col-12">
            <div class="info-row">
              <div class="key w150">{{ txtIMSBandwidth.text }}</div>
              <div class="value">
                <input type="text" class="form-control bold" v-bind:value="txtIMSBandwidth.value" @change="e => txtIMSBandwidth.value = e.target.value">
              </div>
            </div>
          </div>
          <div class="col-sm-3 col-12">
            <div class="info-row">
              <div class="key w150">{{ txtIMSShaper.text }}</div>
              <div class="value">
                <input type="text" class="form-control bold" v-bind:value="txtIMSShaper.value" @change="e => txtIMSShaper.value = e.target.value">
              </div>
            </div>
          </div>
          <div class="col-sm-3 col-12">
            <div class="info-row">
              <div class="key w150">{{ txtTCONIMS.text }}</div>
              <div class="value">
                <input type="text" class="form-control bold" v-bind:value="txtTCONIMS.value" @change="e => txtTCONIMS.value = e.target.value">
              </div>
            </div>
          </div>
          <div class="col-sm-3 col-12">
            <div class="info-row">
              <div class="key w150">{{ txtTCONProfile.text }}</div>
              <div class="value">
                <input type="text" class="form-control bold" v-bind:value="txtTCONProfile.value" @change="e => txtTCONProfile.value = e.target.value">
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="box-form">
        <div class="legend-title">Danh sách profile thiết bị</div>
        <KDataGrid class="table-result table-gachle" :columns="gridDSThietBi.header" :dataSource="gridDSThietBi.list" :allowResizing="true"
          :enable-paging-server="false" :allowPaging="true" :showFilter="true" :showColumnCheckbox="false" :allowGrouping="true"
          :enabledSelectFirstRow="true" ref="gridDSThietBi" @selectedItemsChanged="gridDSThietBi_FocusedRowChanged"
          panelDataHeight="300px"/>
      </div>  
    </div>
  </div>
</template>

<script>
import breadcrumb from "@/components/breadcrumb";
import moment from 'moment'
import KDataGrid from "@/components/kylq_components/KDataGrid";
import api from "./API.js"
export default {
  name: "BNMDeviceParams",
  components: {
    breadcrumb,
    KDataGrid,
  },
  props: ['dulieu'],
  data: () => ({
    header: {
      title: "Khai báo profile thiết bị",
      list: [
        {
          name: 'Khai báo profile thiết bị',
          link: { 
            name: 'Ui.buttons',
            active: true 
          }
        }
      ],
    },
    button: {
      btnNhapMoi: false,
      btnGhiLai: false,
      btnXoa: false,
      btnHuyBo: false,
    },
    cboHangSX: {text: 'Hãng SX', value: '', Items: []},
    txtGemProfile: {text: 'TR-69 Gem Profile', value: ''},
    txtUserTelnet: {text: 'User Telnet', value: ''},
    txtPasswordTelnet: {text: 'Password Telnet', value: ''},
    txtFTTHBandwidth: {text: 'FTTH Bandwidth', value: ''},
    txtFTTHShaper: {text: 'FTTH Shaper', value: ''},
    txtMyTVBandwidth: {text: 'MyTV Bandwidth', value: ''},
    txtMyTVShaper: {text: 'MyTV Shaper', value: ''},
    txtIMSBandwidth: {text: 'IMS Bandwidth', value: ''},
    txtIMSShaper: {text: 'IMS Shaper', value: ''},
    txtTCONIMS: {text: 'TCON IMS', value: ''},
    txtTCONProfile: {text: 'TCON Profile', value: ''},
    cboTinh: {text: 'Tỉnh', value: 0, Items: []},
    gridDSThietBi: {
      header: [
        {
          fieldName: "vendor_name",
          headerText: "Hãng SX",
          allowFiltering: true,
        },
        {
          fieldName: "tr069_gem_profile",
          headerText: "TR-69 Gem Profile",
          allowFiltering: true,
        },
        {
          fieldName: "user_telnet",
          headerText: "User Telnet",
          allowFiltering: true,
        },
        {
          fieldName: "pass_telnet",
          headerText: "Password Telnet",
          allowFiltering: true,
        },
        {
          fieldName: "ftth_bandwidth_profile",
          headerText: "FTTH Bandwidth",
          allowFiltering: true,
        },
        {
          fieldName: "ftth_shaper_profile",
          headerText: "FTTH Shaper",
          allowFiltering: true,
        },
        {
          fieldName: "mytv_bandwidth_profile",
          headerText: "MyTV Bandwidth",
          allowFiltering: true,
        },
        {
          fieldName: "mytv_shaper_profile",
          headerText: "MyTV Shaper",
          allowFiltering: true,
        },
        {
          fieldName: "ims_bandwidth_profile",
          headerText: "IMS Bandwidth",
          allowFiltering: true,
        },
        {
          fieldName: "ims_shaper_profile",
          headerText: "IMS Shaper",
          allowFiltering: true,
        },
        {
          fieldName: "tcon_ims",
          headerText: "TCON IMS",
          allowFiltering: true,
        },
        {
          fieldName: "tcon_profile",
          headerText: "TCON Profile",
          allowFiltering: true,
        },
      ],
      list: [],
      selected: {},
    },
  }),
  async mounted() {    
    try {
      this.loading(true);
      await this.formLoad()
    } catch (e) {
    } finally {
      this.loading(false);
    }
  },
  methods: {
    formLoad: async function () {
      this.SetButton(0)
      await this.layDSTinh();
      await this.getHangSX();
      this.cboTinh.value = Number(this.$root.token.getPhanVungID())
      this.cboHangSX.value = this.cboHangSX.Items[0].id
      await this.lay_ds_thietbi('R')
    },
    layDSTinh: async function () {
      try {
        this.cboTinh.Items = [{tinh_id: 0, tentinh: 'Không chọn'}]
        let response = await api.SP_LAY_DS_TINH(this.axios);
        if (
          response &&
          response.data &&
          response.data.error_code &&
          response.data.error_code == "BSS-00000000" &&
          response.data.data && response.data.data.length > 0
        ) {
          this.cboTinh.Items = this.cboTinh.Items.concat(response.data.data)
        } else if (
          response.data.error_code == "BSS-00000204"
        ) {
        } else {
            this.$toast.error("Lỗi lấy danh sách tỉnh: " + response.data.message_detail);
        }
      } catch (e) {
        this.$toast.error("Lỗi lấy danh sách tỉnh: " + e);
      } 
    },
    getHangSX: async function () {
      this.cboHangSX.Items = []
      try {
        var res = await api.GetListVendor(this.axios)
        if (res.data.errorCode != 0) {
          this.$toast.error(res.data.faultString)
          return false
        }
        this.cboHangSX.Items =  res?.data?.data?.map(e => ({...e, id: e.vendor_id, text:`${e.vendor_id} - ${e.vendor_name}`})) || []
      } catch (error) {
        console.log('🚀 ~ file: BNMScripts.vue:2169 ~ GetListVendor ~ error:', error)
      }
    },
    lay_ds_thietbi: async function (type) {
      await this.sp_update_profile_bnm(type)
    },
    gridDSThietBi_FocusedRowChanged: function (item) {
      if (item && item.length > 0) {
        this.gridDSThietBi.selected = item[0]
        this.cboTinh.value = item[0].phanvung_id;
        this.cboHangSX.value = item[0].hangsx_id;
        this.txtGemProfile.value = item[0].tr069_gem_profile;
        this.txtUserTelnet.value = item[0].user_telnet;
        this.txtPasswordTelnet.value = item[0].pass_telnet;
        this.txtFTTHBandwidth.value = item[0].ftth_bandwidth_profile;
        this.txtFTTHShaper.value = item[0].ftth_shaper_profile;
        this.txtMyTVBandwidth.value = item[0].mytv_bandwidth_profile;
        this.txtMyTVShaper.value = item[0].mytv_shaper_profile;
        this.txtIMSBandwidth.value = item[0].ims_bandwidth_profile;
        this.txtIMSShaper.value = item[0].ims_shaper_profile;
        this.txtTCONIMS.value = item[0].tcon_ims;
        this.txtTCONProfile.value = item[0].tcon_profile;
        this.SetButton(3)
      } else {
        this.gridDSThietBi.selected = {}
        this.cboTinh.value = null
        this.cboHangSX.value = null
        this.txtGemProfile.value = ''
        this.txtUserTelnet.value = ''
        this.txtPasswordTelnet.value = ''
        this.txtFTTHBandwidth.value = ''
        this.txtFTTHShaper.value = ''
        this.txtMyTVBandwidth.value = ''
        this.txtMyTVShaper.value = ''
        this.txtIMSBandwidth.value = ''
        this.txtIMSShaper.value = ''
        this.txtTCONIMS.value = ''
        this.txtTCONProfile.value = ''
        this.SetButton(-1)
      }
    },
    SetButton: function (kieu) {
      this.button.btnNhapMoi = false;
      this.button.btnGhiLai = false;
      this.button.btnXoa = false;
      this.button.btnHuyBo = false;
      if (kieu == -1) {
        //Bat dau
        this.button.btnGhiLai = true;
        this.button.btnHuyBo = true;
      }
      if (kieu == 0) {
        //Bat dau
        this.button.btnNhapMoi = true;
        this.Clear();
      }
      if (kieu == 1) {
        //Them moi
        this.button.btnGhiLai = true;
        this.button.btnHuyBo = true;
        this.Clear();
      }
      if (kieu == 2) {
        //Huy
        this.button.btnNhapMoi = true;
        this.button.btnXoa = true;
        this.Clear();
      }
      if (kieu == 3) {
        //Edit
        this.button.btnNhapMoi = true;
        this.button.btnXoa = true;
        this.button.btnGhiLai = true;
        this.button.btnHuyBo = true;
      }
    },
    Clear: function () {
      this.cboTinh.value = Number(this.$root.token.getPhanVungID())
      this.cboHangSX.value = null
      this.txtGemProfile.value = ''
      this.txtUserTelnet.value = ''
      this.txtPasswordTelnet.value = ''
      this.txtFTTHBandwidth.value = ''
      this.txtFTTHShaper.value = ''
      this.txtMyTVBandwidth.value = ''
      this.txtMyTVShaper.value = ''
      this.txtIMSBandwidth.value = ''
      this.txtIMSShaper.value = ''
      this.txtTCONIMS.value = ''
      this.txtTCONProfile.value = ''
    },
    btnLayTT_Click: async function () {
      try {
        this.loading(true)
        await this.lay_ds_thietbi('R')
      } catch (e) {
        
      } finally {
        this.loading(false)
      }
    },
    btnNhapMoi_Click: function () {
      this.SetButton(1)
    },
    btnGhiLai_Click: async function () {
      try {
        this.loading(true)
        let type = this.button.btnNhapMoi ? 'U' : 'C'
        await this.sp_update_profile_bnm(type)
      } catch (e) {
       
      } finally {
        this.loading(false)
      }
    },
    sp_update_profile_bnm: async function (type) {
      try {
        if (type == 'R') {
          this.gridDSThietBi.list = []
        }
        let input = { 
          "vphanvung_id": this.cboTinh.value, 
          "vhangsx_id": this.cboHangSX.value, 
          "vuser_telnet": this.txtUserTelnet.value, 
          "vpass_telnet": this.txtPasswordTelnet.value, 
          "vftth_shaper_profile": this.txtFTTHShaper.value, 
          "vftth_bandwidth_profile": this.txtFTTHBandwidth.value, 
          "vmytv_shaper_profile": this.txtMyTVShaper.value, 
          "vmytv_bandwidth_profile": this.txtMyTVBandwidth.value, 
          "vims_shaper_profile": this.txtIMSShaper.value, 
          "vims_bandwidth_profile": this.txtIMSBandwidth.value, 
          "vtcon_ims": this.txtTCONIMS.value, 
          "vtcon_profile": this.txtTCONProfile.value, 
          "vtr069_gem_profile": this.txtGemProfile.value, 
          "vtype": type
        }
        // vtype in varchar2, --C: Create; R:Read; U: Update; D: Delete
        var response = await api.sp_update_profile_bnm(this.axios, input)
        if (response &&
          response.data &&
          response.data.error_code &&
          response.data.error_code == "BSS-00000000"
        ) {
          if (type == 'R') {
            this.gridDSThietBi.list = response.data.data
            var ten_hang=this.cboHangSX.Items
            this.gridDSThietBi.list.forEach(item => {
              const foundItem =  ten_hang.find(x => x.id === item.hangsx_id);
              if (foundItem) {
                item.vendor_name = foundItem.text;
              }
            });
            console.log("data ơ đây", this.gridDSThietBi.list)
          } else if (type == 'C' || type == 'U') {
            this.$toast.success('Ghi lại thành công!')
            await this.sp_update_profile_bnm('R')
          } else if (type == 'D') {
            this.$toast.success('Xóa thành công!')
            await this.sp_update_profile_bnm('R')
          }
        } else if (response.data.error_code == "BSS-00000204") {

        } else {
          this.$toast.error('Lỗi ' + (response?.data?.mesage_detail || response.data.mesage))
        }
      } catch (error) {
        console.log('🚀 ~ file: BNMDeviceParams.vue:2169 ~ sp_update_profile_bnm ~ error:', error)
      }
    },
    btnXoa_Click: async function () {
      try {
        this.loading(true)
        var xacNhan = confirm("Bạn có chắc chắn muốn xóa không?");
        if (xacNhan==true) {
        await this.sp_update_profile_bnm('D')
        }
      } catch (e) {
        
      } finally {
        this.loading(false)
      }
    },
    btnHuyBo_Click: function () {
      this.SetButton(2)
    },
  },
  watch: {
    "dulieu"(val) {
      if (val) {
      }
    }
  }
}
</script>
<style>
.full-w {
  width: 100% !important;
}
</style>
