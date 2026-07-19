<template>
  <div>
	  <breadcrumb v-if="!dulieu || dulieu.isPopup == false" :header="header" />
    <div class="list-actions-top">
      <ul class="list">
        <li>
          <a href="javascript:;" @click="isShowForm = !isShowForm">
            <span class="icon fa bold" :class="{'fa-angle-up': isShowForm, 'fa-angle-down': !isShowForm,}"></span>Tạo Script
          </a>
        </li>
        <li>
          <a href="javascript:;" v-if="isShowForm == false" @click="btnShowLog_click">
            <span class="icon one-file-detail"></span> Log thay đổi Script
          </a>
        </li>
        <li>
          <a href="javascript:;" v-if="isShowForm == false && isEdit == false" @click="display" >
            <span class="icon one-edit"></span> Sửa
          </a>
        </li>
        <li>
          <a href="javascript:;" v-if="isShowForm == false && isEdit == false" @click="btnTest_click">
            <span class="icon one-file-check"></span> Sinh Script
          </a>
        </li>
        <li>
          <a href="javascript:;" v-if="isShowForm == false && isEdit == false" @click="btnthunghiem_click">
            <span class="icon one-file-check"></span> Thử nghiệm
          </a>
        </li>
        <li>
          <a href="javascript:;" v-if="isShowForm == true && isEdit == false" @click="btncreate_click">
            <span class="icon one-file-check"></span> Tạo mới
          </a>
        </li>
        <li>
          <a href="javascript:;" v-if="isShowForm == true && isEdit == true" @click="btnsave_click">
            <span class="icon one-save"></span> Lưu
          </a>
        </li>
        <li>
          <a href="javascript:;" v-if="isShowForm == true && isEdit == true">
            <span class="icon one-trash"></span>Xóa
          </a>
        </li>
        <li>
          <a href="javascript:;" v-if="isShowForm == true && isEdit == true" @click="btncancel_click">
            <span class="fa fa-remove f20 inline vcenter"></span> Hủy
          </a>
        </li>
      </ul>
    </div>
    <div class="page-content">
      <div class="box-form">
        <div class="legend-title">Thông tin kịch bản BNM</div>
        <div class="row">
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w100">{{ cboTinh.text }}</div>
              <div class="value">
                <div class="select-custom" ref="cboTinh">
                  <select2 :z-index="2000" :settings="{ dropdownParent: $refs['cboTinh'] }"
                    v-model="cboTinh.value" class="select2"
                    :options="cboTinh.Items"
                    :disabled = cboTinh.isLocked >
                  </select2>
                </div>
              </div>                  
            </div>
          </div>
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w100">{{ cboHangSX.text }}</div>
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
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w100">{{ cboDichVu.text }}</div>
              <div class="value">
                <div class="select-custom" ref="cboDichVu">
                  <select2 :z-index="2000" :settings="{ dropdownParent: $refs['cboDichVu'] }"
                    v-model="cboDichVu.value" class="select2"
                    :options="cboDichVu.Items">
                  </select2>
                </div>
              </div>
            </div>
          </div>
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w100">{{ cboLoaiCH.text }}</div>
              <div class="value">
                <div class="select-custom" ref="cboLoaiCH">
                  <select2 :z-index="2000" :settings="{ dropdownParent: $refs['cboLoaiCH'] }"
                    v-model="cboLoaiCH.value" class="select2"
                    :options="cboLoaiCH.Items">
                  </select2>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>   

      <div class="box-form">        
        <div class="row" v-if="isShowForm == true">
          <!-- <div class="col-sm-4 col-12" style="vertical-align: baseline">
            <div class="box-form">
              <div class="legend-title">PARAMS BSS</div>
              <div class="info-row">
                <div class="value">
                  <div class="row">
                      <div class="col-sm-6 col-12">
                        <div class="info-row">
                          <div class="key w100 font-weight-bold">PARAM</div>
                        </div>
                      </div>
                      <div class="col-sm-6 col-12">
                        <div class="info-row">
                          <div class="key w100 font-weight-bold ">VALUE</div>
                        </div>
                      </div>
                    </div>
  
                  <div class="scroll-container">
                  <ul class="post-list">  
                  <li v-for="(item, index) in gridParamBSS.list.map(key =>key.param_name )">
                    <div class="row">
                      <div class="col-sm-6 col-12">
                        <div class="info-row">
                          <div class="key w80">{{ item }}</div>
                        </div>
                      </div>
                      <div class="col-sm-6 col-12">
                        <div class="info-row">
                          <div class="key w100">
                            <input type="text" v-model="ValuesParam[item]" >
                              
                            </input>
                          </div>
                        </div>
                      </div>
                    </div>
                  </li>
                </ul>
                  </div> -->
                  <!-- <DataGrid class="table-result table-gachle" :columns="gridParamBSS.header"
                    :dataSource="gridParamBSS.list" :enable-paging-server="false" :allowPaging="true"
                    :showFilter="true" :showColumnCheckbox="false" :enabledSelectFirstRow="false" ref="gridParamBSS"/> -->
                <!-- </div>
              </div>
              <br>
              <div class="legend-title">THÔNG TIN PORT BỔ SUNG</div>
              <div class="list-actions-top" style="position: unset !important">
                <ul class="list">
                  <li>
                    <a href="javascript: void(0)" @click="btnThemDK_click">
                      <span class="-ap icon icon-plus"></span>Thêm điều kiện
                    </a>
                  </li>
                </ul>
              </div>
              <div class="value" :key="gridPort.key">
                <DataGrid class="table-result table-gachle" :columns="gridPort.header"
                  :dataSource="gridPort.list" :enable-paging-server="false" :allowPaging="true"
                  :showFilter="true" :showColumnCheckbox="false" :enabledSelectFirstRow="false" ref="gridPort"
                  :commandColumn="{headerText: '', width: 40, cellCssClass: 'center', firstColumn: true, show: true}"
                  :commands="gridPort.commands" @commandClicked="gridCommand_gridPort"/>
              </div>
            </div>
          </div> -->
          <div class="col-sm-12 col-12" style="vertical-align: baseline">
            <div class="box-form">
              <div class="legend-title">LỆNH TEMP</div>
              <div class="info-row">
                <div class="value" >
                  <!-- <pre v-html="JSON.stringify(scriptTemp, null, 2)" v-if="isEdit == false"></pre> -->
                  <textarea name="scriptTemp" class="form-control" style="height: 500px;" v-bind:value="scriptTemp"
                    @change="e => scriptTemp = e.target.value" :disabled="commit_Edit == false"></textarea>
                </div>                  
              </div>
            </div>          
          </div>      
          <!-- <div class="col-sm-4 col-12" style="vertical-align: baseline">        
            <div class="box-form">
              <div class="legend-title">KẾT QUẢ</div>
              <div class="info-row">
                <div class="value"> -->
                  <!-- <pre v-html="JSON.stringify(scriptResult, null, 2)"></pre> -->
                  <!-- <textarea name="scriptResult" class="form-control" style="height: 1200px;" v-bind:value="scriptResult"
                    @change="e => scriptResult = e.target.value" disabled></textarea> -->
                <!-- </div>                  
              </div>
            </div>
          </div>  -->
        </div>
      </div>      
    </div>

    <b-modal id="popupComponents" size="huge" :title="popupComponentHeader" :hide-header="popupComponentHeader?false:true"
      hide-footer modal-class="modal-custom" body-class="modal-body p-0">
      <component :is="popupComponent" ref="generalPopup" :dulieu="popupComponentData" v-bind="popupComponentAttr"
        isPopup="true" v-on="popupComponentEvts"></component>
    </b-modal>
  </div>
</template>

<style src="../BNMScripts/BNMScripts.scss"></style>

<script>
import breadcrumb from "@/components/breadcrumb";
import moment from 'moment'
import KDataGrid from "@/components/kylq_components/KDataGrid";
import api from './API.js'

export default {
  name: "BNMScriptRequests",
  components: {
    breadcrumb,
    KDataGrid
  },
  props: ['dulieu'],
  data: () => ({
    header: {
      title: "Tạo mới kịch bản BNM và khai báo Script",
      list: [
        {
          name: 'Tạo mới kịch bản BNM và khai báo Script',
          link: { 
            name: 'Ui.buttons',
            active: true 
          }
        }
      ],
    },
    popupComponentName: '',
    popupComponentHeader: '',
    popupComponent: null,
    popupComponentData: null,
    popupComponentAttr: {},
    popupComponentEvts: {
      
    },
    username : "",
    code_province:"",
    profile_bnm:[],
    phanvung_id: "",
    isShowForm: true,
    isEdit: false,
    commit_Edit: true,
    btnSave: {text: 'Cập nhật', Visibled: true},
    btnDelete: {text: 'Xóa', Visibled: true},
    kichban_id: 0,
    cboTinh: {text: 'Tỉnh', value: '', Items: [],isLocked:true},
    cboHangSX: {text: 'Hãng SX', value: '', Items: []},
    cboDichVu: {text: 'Dịch vụ', value: '', Items: []},
    cboLoaiCH: {text: 'Loại cấu hình', value: '', Items: []},

    param :{
      idlenh:'',
      slot:'',
      port:'',
      pass_onu:'',
      onu_num:'',
      max_unicast_mac: '',
      card_onu: '',
      t_con_profile:'',
      sernum:'',
      mytv_shaper_profile:''
    },

    ValuesParam :{},
    test :[],

    PARAM_BSS: {
      txtParam1: {text: 'param1', value: ''},      
      txtParam2: {text: 'param2', value: '', readOnly: true},
      txtParam3: {text: 'param3', value: ''},
    }, 
    scriptTemp: "",
    Param_nhapvao :[],
    scriptTemp: "", 
    scriptResult: "",
    gridParamBSS: {
      list: [],
      header: [
        {
          fieldName: "param_name",
          headerText: "PARAM",
          allowFiltering: true,
        },
        {
          fieldName: "param_value",
          headerText: "VALUE",
          allowFiltering: true,
        },
      ]
    },
    gridPort: {
      key: 1,
      list: [],
      commands: [
        { name: 'delete', cssClass: 'pad3 mar5 lh14 icon -ap icon-delete btn-danger btn-plus', iconCss: 'fa fa-times', width: 100 },
      ],
      header: [
        {
          fieldName: "FIELD",
          headerText: "PARAM",
          allowFiltering: true,
          template: () => {
            return {
              template: {
                template: `<div>
                    <input type="text" class="form-control text" :value="data.FIELD" @change="onChangeText">
                  </div>`,
                data: function () {
                  return {
                    data: {},
                  };
                },
                computed: {
                  parent() {
                    return this.$parent.$parent.$parent;
                  },           
                },
                methods: {
                  onChangeText: function (e) {
                    var index = this.parent.gridPort.list.findIndex(e => e.ID == this.data.ID)
                    this.parent.gridPort.list[index].FIELD = e.target.value
                  },
                },
              },
            };
          },
        },
        {
          fieldName: "VALUE",
          headerText: "VALUE",
          allowFiltering: true,
          template: () => {
            return {
              template: {
                template: `<div>
                    <input type="text" class="form-control text" :value="data.VALUE" @change="onChangeText">
                  </div>`,
                data: function () {
                  return {
                    data: {},
                  };
                },
                computed: {
                  parent() {
                    return this.$parent.$parent.$parent;
                  },           
                },
                methods: {
                  onChangeText: function (e) {
                    var index = this.parent.gridPort.list.findIndex(e => e.ID == this.data.ID)
                    this.parent.gridPort.list[index].VALUE = e.target.value
                  },
                },
              },
            };
          },
        },
      ]
    },
    grdBNMRequests: {
      list: [],
      header: [
        {
          fieldName: "ID",
          headerText: "ID",
          allowFiltering: true,
        },
        {
          fieldName: "NGAY_TAO",
          headerText: "Ngày Khởi tạo",
          allowFiltering: true,
        },
        {
          fieldName: "PARAM_BSS",
          headerText: "Param BSS",
          allowFiltering: true,
        },
        {
          fieldName: "SCRIPT",
          headerText: "Script",
          allowFiltering: true,
        },
        {
          fieldName: "NOTE",
          headerText: "Ghi chú",
          allowFiltering: true,
        },        
        {
          fieldName: "action",
          headerText: "",
          textAlign: "center",
          width: 70,
          template: () => {
            return {
              template: {
                template: `<div>
                    <button type="button" class="btn btn-primary" @click="onbtnChitiet_Click">
                      Chi tiết
                    </button>
                  </div>`,
                data: function () {
                  return {
                    data: {},
                  };
                },
                methods: {
                  onbtnChitiet_Click() {
                    this.$parent.$parent.$parent.btnChitiet_click(this.data);
                  },
                },
              },
            };
          },
        },
      ],
      value: {},
      isEnabled: true,
      checked: [],
    },
  }),
  mounted () {
    this.formLoad()

  },
  methods: {
    formLoad: async function () {
      await Promise.all([
        this.getDSTinh(),
        this.getHangSX(),
        this.getDSDichVu(),
        this.getDSLoaiCH(),
        this.getDSParamBSS()
      ]);

      if (this.dulieu) {
      //  this.kichban_id = this.dulieu?.id || null
        this.cboTinh.value = this.dulieu?.province_id || null
        this.cboHangSX.value = this.dulieu?.vendor_id || null
        this.cboDichVu.value = this.dulieu?.service_id || null
        this.cboLoaiCH.value = this.dulieu?.config_type || null
        let tmp = this.dulieu?.script_temp || null
        // this.scriptTemp = tmp
      }
      console.log(this.dulieu)

      this.grdBNMRequests.list = [
        { ID: "1", PARAM_BSS: "123", SCRIPT: 'abc', NOTE: 'test', NGAY_TAO: "01/05/2023 10:10:10"},
        { ID: "2", PARAM_BSS: "234", SCRIPT: 'bcd', NOTE: 'test', NGAY_TAO: "02/05/2023 10:10:10"},
        { ID: "3", PARAM_BSS: "345", SCRIPT: 'ghi', NOTE: 'test', NGAY_TAO: "03/05/2023 10:10:10"},
        { ID: "4", PARAM_BSS: "456", SCRIPT: 'ikl', NOTE: 'test', NGAY_TAO: "04/05/2023 10:10:10"},
      ]
    },


    getVauesParam: async function () { 
      this.test = this.gridParamBSS.list.map((item) => {
        const paramName = item.param_name
        const paramValue = this.ValuesParam[item.param_name]
        return { [paramName]:paramValue };
      })
      const param_script= this.GetParam(this.scriptTemp)
      var scriptResult = this.scriptTemp 
      param_script.forEach((key, index) => {
        const item = this.test.find(obj => obj.hasOwnProperty(key));
        if (item) {
          const regex = new RegExp(`${this.Param_nhapvao[index]}`, 'g');
          scriptResult = scriptResult.replace(regex,item[key] );
        }
      });
      this.scriptResult=scriptResult;
      if (this.scriptResult.includes("undefined")){
        this.$toast.error("Nhâp thiếu Param, Vui lòng kiểm tra lại!")
      }
      else  this.$toast.success("Map Param thành công!");
      // this.param.idlenh = this.ValuesParam["idlenh"];
      // this.param.slot = this.ValuesParam["slot"];
      // this.param.port = this.ValuesParam["port"];
      // this.param.pass_onu = this.ValuesParam["pass_onu"];
      // this.param.onu_num = this.ValuesParam["onu_num"];
      // this.param.max_unicast_mac = this.ValuesParam["max_unicast_mac"];
      // this.param.card_onu = this.ValuesParam["card_onu"];
      // this.param.t_con_profile = this.ValuesParam["t_con_profile"];
      // this.param.sernum = this.ValuesParam["sernum"];
      // this.param.mytv_shaper_profile = this.ValuesParam["mytv_shaper_profile"];

      // if (this.scriptTemp.length > 0){
      //   let wordsToReplace = {
      //     "\\{param.idlenh\\}": this.param.idlenh ,
      //     "\\{param.slot\\}" : this.param.slot,
      //     "\\{param.port\\}" : this.param.port,
      //     "\\{param.pass_onu\\}" : this.param.pass_onu,
      //     "\\{param.onu_num\\}" : this.param.onu_num,
      //     "\\{param.max_unicast_mac\\}" :  this.param.max_unicast_mac,
      //     "\\{param.card_onu}" : this.param.card_onu,
      //     "\\{param.t_con_profile\\}" : this.param.t_con_profile,
      //     "\\{param.sernum\\}" : this.param.sernum,
      //     "\\{mytv_shaper_profile}" : this.mytv_shaper_profile,
      //   };  
      //   if (this.gridPort.list.length > 0){
      //     for (var i = 0; i < this.gridPort.list.length; i++) {
      //       let param="\\{param."+this.gridPort.list[i].FIELD+"\\}";
      //       let value_param =  this.gridPort.list[i].VALUE;
      //       wordsToReplace[param]=value_param;
      //     };
      //   };
      //   let scriptResult = this.scriptTemp;
      //   for (let word in wordsToReplace) {
      //     let regex = new RegExp(word, "gi"); // Tạo regex cho từ hiện tại
      //     scriptResult = scriptResult.replace(regex, wordsToReplace[word]); // Thay thế từ
      //   }
      //   this.scriptResult=scriptResult;

      // };
    },
    GetParam : function (data) {    
      const regex = /\{(.*?)\}/g;
      const matches = [];
      let match;
      while ((match = regex.exec(data)) !== null) {
        matches.push(match[1]);       
      }
      const values_paramscript = new Set(matches.map(match => match.split('.')[1]));
      const arr_values_paramscript = [...values_paramscript]

      this.Param_nhapvao = [...new Set(matches.map(value => `{${value}}`))];
      return arr_values_paramscript
    },

    getDSTinh: async function () {      
      try {
        this.cboTinh.Items = []
        var res = await api.GetListProvince(this.axios)
        if (res.data.errorCode != 0) {
          this.$toast.error(res.data.faultString)
          return false
        }
        this.cboTinh.Items =  res?.data?.data?.map(e => ({...e, text: (e.code + ' - ' + e.name)})) || []
        var matinh_ccos = await this.$root.token.getThongTinNguoiDung()
        this.username = matinh_ccos.user_vtn
        this.phanvung_id = matinh_ccos.phanvung_id
        this.cboTinh.Items.forEach(item => {
          if (item.code === matinh_ccos.tentat_ccbs){
            this.cboTinh.value = item.id
            this.code_province = item.code
          }
        });
      } catch (error) {
        console.log('🚀 ~ file: BNMScripts.vue:2169 ~ GetListProvince ~ error:', error)
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
    getDSDichVu: async function () {
      this.cboDichVu.Items = []
      try {
        var res = await api.GetListService(this.axios)
        if (res.data.errorCode != 0) {
          this.$toast.error(res.data.faultString)
          return false
        }
        this.cboDichVu.Items =  res?.data?.data?.map(e => ({...e, id: e.service_id, text:`${e.service_id} - ${e.service_name}`})) || []
      } catch (error) {
        console.log('🚀 ~ file: BNMScripts.vue:2169 ~ GetListService ~ error:', error)
      }
    },
    getDSLoaiCH: async function () {
      this.cboLoaiCH.Items = []
      try {
        var res = await api.GetListConfig(this.axios)
        if (res.data.errorCode != 0) {
          this.$toast.error(res.data.faultString)
          return false
        }
        this.cboLoaiCH.Items =  res?.data?.data?.map(e=>({...e, id: e.config_type, text:`${e.config_type} - ${e.config_name}`})) || []
      } catch (error) {
        console.log('🚀 ~ file: BNMScripts.vue:2169 ~ GetListConfig ~ error:', error)
      }
    },
    getDSParamBSS: async function () {
      try {
        this.gridParamBSS.list = []
        var input = {};
        let res = await api.getListParamName(this.axios, input);
        if (res.data.errorCode != 0) {
          this.$toast.error(res.data.faultString)
        }
        if (res.data.data && res.data.data.length > 0) {
          this.gridParamBSS.list =  res.data.data
        }
      } catch (ex) {
        this.ShowError("Lỗi: " + (ex?.data?.faultString || ex));
      }
    },
    closeForm: function (val) {
      if (val) this.$emit("form-close", val)
    },
    Popup(modalId) {
      this.$bvModal.show(modalId);
    },
    ClosePopup(modalId) {
      this.$bvModal.hide(modalId);
      this.popupComponent = null;
    },
    grdBNMRequests_SelectionChanged: function (item) {
      if (item && item.length > 0) {
        // this.kichban_id = item[0].ID;
        // this.cboTinh.value = item[0].TINH;
        // this.cboLoaiTBI.value = item[0].LOAITBI;
        // this.cboHangSX.value = item[0].HANGSX;
        // this.txtNgayKhoiTao.value = item[0].NGAY_TAO;
        // this.txtNgayCapNhat.value = item[0].NGAY_CN;
        // this.txtNguoiTao.value = item[0].NGUOI_TAO;
        this.PARAM_BSS = item[0].PARAM_BSS
      }      
    },
    btnThemDK_click: function() {
      var row = {}
      row.ID = Math.random().toString(36).slice(2, 7),
      row.FIELD = ''
      row.VALUE = ''
      
      this.gridPort.list.push(row)
      this.gridPort.key += 1
    },
    gridCommand_gridPort: function (type, dataItem) {
      if (type == 'delete') {
        this.gridPort.list = this.gridPort.list.filter(e => e.ID != dataItem.ID)
      }
    },
    clearForm: function () {
    },
    ShowSuccess: function (msg) {
      this.$toast.success(msg);
    },
    ShowError: function (msg) {
      this.$toast.error(msg);
    },
    btnChitiet_click: function (item) {
      console.log('chitiet')
      // console.log(item)
      this.popupComponent = () =>
        import(
          "@/modules/admin/bnm/BNMScriptRequests/popup/BNMRequestDetail.vue"
        );
      this.popupComponentName = "popup_ChiTietYeuCau";
      this.popupComponentHeader = 'Chi Tiết Thay Đổi Script';
      this.popupComponentData = {
        isPopup: true,
        parrent: 'BNMScriptRequests',
        v_script: this.dulieu,
        v_request: item  
      }; 
      this.Popup("popupComponents");
    },
    btnShowLog_click: function (item) {
      console.log('Show Log')
      // console.log(item)
      this.popupComponent = () =>
        import(
          "@/modules/admin/bnm/BNMLogScripts/BNMLogScripts.vue"
        );
      this.popupComponentName = "popup_BNMLogScripts";
      this.popupComponentHeader = 'Danh sách log thay đổi Script';
      this.popupComponentData = {
        isPopup: true,
        parrent: '',
        ...this.dulieu,
      }; 
      this.Popup("popupComponents");
    },
    btnTest_click: async function () {
      try {
        this.loading(true)
        await this.testScript()
      } catch (e) {} finally { this.loading(false) }
    },

    btnthunghiem_click: async function (){
      try {
        this.loading(true)
        await this.getVauesParam()
      }
      catch (e) {} finally { this.loading(false) }
    },

    btncreate_click:async function () {
      try {
        this.loading(true)
        await this.getVauesParam()
        await this.savescript()
      } 
      catch (e) {} 
      finally { this.loading(false) }
    },
    btnsave_click : async function (){
      try {
        this.loading(true)
        await this.getVauesParam()
        await this.savescript_edit()
      } 
      catch (e) {} 
      finally { this.loading(false) }
    },
    btncancel_click:function (){
      this.isEdit = !this.isEdit
      this.scriptTemp = this

    },
    btn_create: function (data) {
      if (data ==true){
        console.log("test ok ák")
      }

    },
    savescript_edit : async function () {
      try {

        let input ={
          "script_bnm_id" : this.kichban_id ,
          "script_temp": this.scriptResult, 
          "user_update": this.$root.token.getUserName(),
        } 
        console.log("đây là input", input);
        var res = await api.updateScriptTempBnm(this.axios, input)
        if (res.data.errorCode != 0) {
          this.$toast.error("lỗi rồi")
          return false
        }
        else 
        {
          console.log("ok nha")
          this.$toast.success("Sửa script thành công")
        }   
      }catch (error) {
        console.log('🚀 ~ file: BNMScripts.vue:2169 ~ testScript ~ error:', error)
      }
    },
    savescript:async function () {
      try {

        let input ={
          "province_id":this.cboTinh.value ,
          "vendor_id": this.cboHangSX.value, 
          "service_id": this.cboDichVu.value, 
          "script_temp": this.scriptTemp, 
          "config_type": this.cboLoaiCH.value, 
          "user_update": this.$root.token.getUserName(),
        }
        console.log(input)
        var res = await api.AddScriptTempBnm(this.axios, input)
        if (res.data.errorCode != 0) {
          this.$toast.error(res.data.faultString)
          return false
        }
        else 
        {
          console.log("ok nha")
          this.$toast.success(res.data.data.Result)
        }
      }catch (error) {
        console.log('🚀 ~ file: BNMScripts.vue:2169 ~ testScript ~ error:', error)
      }
    },
    testScript: async function () {
      try {  
        let fields = ["bosung_ims","max_num_group","queue_hsi","queue_vod","queue_ims","loai_thiet_bi_id","bosung","mgc_udp_port","port_telnet","card_onu",
        "vlan_hsi","vlan_vod","vlan_ims","vlan_tr069","cvlan","vlan_thoai","serviceport_tr069","vlan_tr069_2","port_onu","port_mytv","ims_svlan","slot",
        "port","onu_num","max_unicast_mac","loai_onu_id","port_onu"];
        this.test = this.gridParamBSS.list.map((item) => {
        const paramName = item.param_name
        const paramValue = this.ValuesParam[item.param_name]
        return { [paramName]:paramValue };
        })
        this.test.forEach(item => {
          const key = Object.keys(item)[0]; 
          const value = item[key];
        if(value === undefined){
            item[key]= "";
          }
        });
        const json_param = Object.assign({}, ...this.test);
        //var check_bnm = await this.sp_update_profile_bnm()
        this.scriptResult = ""
        //if (check_bnm){
        let input = {
          "idlenh": "" ,
         // "slot": this.ValuesParam["slot"]> 0 ? Number(this.ValuesParam["slot"]) : "",
         // "port": this.ValuesParam["port"]> 0 ?  Number(this.ValuesParam["port"]) : "",
          "pass_onu": "",
         // "onu_num": this.ValuesParam["onu_num"]> 0 ?  Number(this.ValuesParam["onu_num"]) : "",
         // "max_unicast_mac": this.ValuesParam["max_unicast_mac"]> 0 ?  Number(this.ValuesParam["max_unicast_mac"]) : "",
        //  "card_onu": this.ValuesParam["card_onu"] > 0 ?  Number(this.ValuesParam["card_onu"]) : "",
        //  "t_con_profile": this.ValuesParam["t_con_profile"] > 0 ? this.ValuesParam["t_con_profile"].toString() : "" ,
        //  "sernum":  this.ValuesParam["sernum"] > 0 ? this.ValuesParam["sernum"].toString() : "" ,
        //  "mytv_shaper_profile": this.ValuesParam["mytv_shaper_profile"]> 0 ? this.ValuesParam["mytv_shaper_profile"].toString() : "" ,
        //  "ftth_shaper_profile":  this.profile_bnm["ftth_shaper_profile"] > 0 ? this.profile_bnm["ftth_shaper_profile"] : "",
        //  "ftth_bandwidth_profile": "",
         // "mytv_bandwidth_profile": this.profile_bnm["mytv_bandwidth_profile"].length > 0 ? this.profile_bnm["mytv_bandwidth_profile"] : "" ,
        //  "ims_shaper_profile": this.profile_bnm["ims_shaper_profile"].length > 0 ? this.profile_bnm["ims_shaper_profile"] : "" ,
        // "ims_bandwidth_profile":  this.profile_bnm["ims_bandwidth_profile"].length > 0 ? this.profile_bnm["ims_bandwidth_profile"] : "" ,
        //  "vlan_hsi": "",
         // "vlan_vod": "",
        //  "vlan_ims": "",
        //  "vlan_tr069": "",
        //  "cvlan": "",
        //  "vlan_thoai": "",
         // "serviceport_tr069": "",
        //  "vlan_tr069_2": "",
        //  "tr069_tcon_profile": "Fiber300M",
        //  "tr069_gem_profile": this.profile_bnm["tr069_gem_profile"].length > 0 ? this.profile_bnm["tr069_gem_profile"] : "" ,
        //  "tr069_Name": "",
          "bosung_ims": "",
          "max_num_group": "",
          "queue_hsi": "",
          "queue_vod": "",
          "queue_ims": "",
          "hangsx_id": this.cboHangSX.value > 0 ? Number(this.cboHangSX.value) : "", // (truyền vào id hãng sx)
        //  "loai_onu_id": "",
          "loai_thiet_bi_id": "",
          "loaitb_id": this.cboDichVu.value > 0 ? Number(this.cboDichVu.value) : "", // (truyền vào id của dịch vụ)
        //  "port_onu": "",
         // "port_mytv": "",
          "bosung": "",
          "account_ftth": "",
          "password_ftth": "",
          "mgc_udp_port": "",
          "ftp_server_ip": "",
         // "tcon_ims": this.profile_bnm["tcon_ims"].length > 0 ? this.profile_bnm["tcon_ims"] : "" ,
          "ont_svr_profile": "",
        //  "ims_serviceName": "",
         // "ims_name": "",
        //  "ims_vlan_profile": "",
         // "ims_svlan": "",
          "ftp_server_username": "",
          "ftp_server_password": "",
          "so_dien_thoai": [
          ""
          ],
          "so_dien_thoai_pass": [
          ""
          ],
          "vtt": this.code_province.length >0 ? this.code_province.toString() : "", //(truyền vào code province)
          "planned_card_type": "",
          "sw_ver_pland": "",
          "ip_node": "",
          "user_telnet":  "" ,
          "password_telnet": "" ,
          "password_enable": "",
          "port_telnet": "",
          "loai_cau_hinh": this.cboLoaiCH.value > 0 ? Number(this.cboLoaiCH.value) : "", // Loại cấu hình 
          "user_update": this.username.length >0 ? this.username.toString() : "",
        }
        var mergedObject = {...input, ...json_param}
        Object.keys(mergedObject).forEach(key => {
            const value = mergedObject[key];
            if (fields.includes(key) && (value === "" || value === undefined || value === null)) {
                mergedObject[key] = 0;
            }
        });
        var res = await api.testScript(this.axios, mergedObject)
        if (res.data.errorCode != 0) {
          this.$toast.error(res.data.data.errors.param[0])
          return false
        }
        else { this.$toast.success("Thử thành công")}
        this.scriptResult =  res?.data?.data.result || ""
      //}
      } catch (error) {
        this.$toast.error("Có lỗi xảy ra! Hãy kiểm tra API testScript", )
      }
    },
  },
  watch: {
    "dulieu"(val) {
      if (val) {
        this.kichban_id = val?.id || null
        this.cboTinh.value = val?.province_id || null
        this.cboHangSX.value = val?.vendor_id || null
        this.cboDichVu.value = val?.service_id || null
        this.cboLoaiCH.value = val?.config_type || null
      }
    }
  }
}
</script>
<style>
.scroll-container {
  height: 400px;
  overflow-y: auto;
}

.post-list {
  list-style-type: none;
  padding: 0;
}
.post-list li {
  margin: 0 10px 0;
}
</style>
