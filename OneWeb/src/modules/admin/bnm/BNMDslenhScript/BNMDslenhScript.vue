<template>
    <div>
        <breadcrumb v-if="!dulieu || dulieu.isPopup == false" :header="header" />
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a href="javascript:;" @click="getDSLogScript">
              <span class="icon one-file-attach"></span>Lấy TT
            </a>
          </li>
          <!-- <li>
            <a href="javascript:;">
              <span class="icon one-file-plus"></span>Nhập mới
            </a>
          </li>
          <li>
            <a href="javascript:;">
              <span class="icon one-file-plus"></span>Convert
            </a>
          </li>
          <li>
            <a href="javascript:;" >
              <span class="icon one-save"></span>Ghi lại
            </a>
          </li>
          <li>
            <a href="javascript:;" >
              <span class="icon one-trash"></span>Xóa
            </a>
          </li> -->
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
                      :options="cboTinh.Items">
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
          <div class="legend-title">Danh sách thay đổi Script</div>
          <div class="row"> 
            <DataGrid :columns="grdBNMRequests.header"
              :dataSource="grdBNMRequests.list" :enable-paging-server="false" :allowPaging="true"
              :showFilter="true" :showColumnCheckbox="false" :enabledSelectFirstRow="true" ref="grdBNMRequests" panelDataHeight="200px"
              @selectedItemsChanged="grd_SelectionChanged"/>
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
  import DatePicker from 'vue2-datepicker'
  import 'vue2-datepicker/locale/vi.js'
  import 'vue2-datepicker/index.css'
  import DataGrid from "@/components/Controls/DataGrid";
  import api from './API.js'
  
  export default {
    name: "BNMLogScripts",
    components: {
      breadcrumb,
      DatePicker,
      DataGrid
    },
    props: ['dulieu'],
    data: () => ({
      header: {
        title: "Danh sách lịch sử thay đổi Script",
        list: [
          {
            name: 'Danh sách lịch sử thay đổi Script',
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
      btnSave: {text: 'Cập nhật', Visibled: true},
      btnDelete: {text: 'Xóa', Visibled: true},
      kichban_id: 0,
      cboTinh: {text: 'Tỉnh', value: '', Items: [], Enabled: false},
      cboHangSX: {text: 'Hãng SX', value: '', Items: [], Enabled: false},
      cboDichVu: {text: 'Dịch vụ', value: '', Items: [], Enabled: false},
      cboLoaiCH: {text: 'Loại cấu hình', value: '', Items: [], Enabled: false},

     
      grdBNMRequests: {
        list: [],
        header: [
          {
            fieldName: "id",
            headerText: "Kịch bản ID",
            allowFiltering: true,
          },
          {
            fieldName: "script_bnm_id",
            headerText: "Scipt BNM ID",
            allowFiltering: true,
          },
          {
            fieldName: "script",
            headerText: "Script",
            allowFiltering: true,
          },
          {
            fieldName: "user_update",
            headerText: "Người CN",
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
        ]);
  
        if (this.dulieu && this.dulieu.isPopup == true) {
          this.kichban_id = this.dulieu?.id || null
          this.cboTinh.value = this.dulieu?.province_id || null
          this.cboHangSX.value = this.dulieu?.vendor_id || null
          this.cboDichVu.value = this.dulieu?.service_id || null
          this.cboLoaiCH.value = this.dulieu?.config_type || null
        }
  
        if (!this.dulieu || this.dulieu.isPopup == false) {
          this.cboTinh.value = this.cboTinh.Items[0]?.id || 0
          this.cboHangSX.value = this.cboHangSX.Items[0]?.id || 0
          this.cboDichVu.value = this.cboDichVu.Items[0]?.id || 0
          this.cboLoaiCH.value = this.cboLoaiCH.Items[0]?.id || 0
  
          this.cboTinh.Enabled = true;
          this.cboHangSX.Enabled = true;
          this.cboDichVu.Enabled = true
          this.cboLoaiCH.Enabled = true
        }
  
        await this.getDSLogScript()
  
        console.log(this.dulieu)
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
      getDSLogScript: async function () {
        try {
          this.grdBNMRequests.list = []
          var input = {
            script_bnm_id: this.kichban_id,
            from_date:'17-03-2024',// moment(this.dtpTuNgay.value).format('DD-MM-YYYY'),
            to_date: '19-03-2024',//moment(this.dtpTuNgay.value).format('DD-MM-YYYY'),
          };
          let res = await api.getListLogScript(this.axios, input);
          if (res.data.errorCode != 0) {
            this.$toast.error(res.data.faultString)
          }
          if (res.data.data && res.data.data.length > 0) {
            this.grdBNMRequests.list =  res.data.data
          }
        } catch (ex) {
          this.ShowError("Lỗi: " + (ex?.data?.faultString || ex));
        }
      },
      getid_Dslenh: async function (){
        try {
        this.grdBNMScripts.list = []
        var input = {
          province_id: this.cboTinh.value, // this.cboTinh.value,
          vendor_id: this.cboHangSX.value, //this.cboHangSX.value,
          service_id: this.cboDichVu.value, //this.cboDichVu.value,
          config_type: this.cboLoaiCH.value //this.cboLoaiCH.value
        };
        let res = await api.GetListScriptTempBNM(this.axios, input);
        if (res.data.errorCode != 0) {
          this.$toast.error(res.data.faultString)
        }
        if (res.data.data && res.data.data.length > 0) {
          for (let item of res.data.data) {
            item.province_name = this.cboTinh.Items?.find(e => e.id == item.province_id).text || null
            item.vendor_name = this.cboHangSX.Items?.find(e => e.id == item.vendor_id).text || null
            item.service_name = this.cboDichVu.Items?.find(e => e.id == item.service_id).text || null
            item.config_name = this.cboLoaiCH.Items?.find(e => e.id == item.config_type).text || null
          }
          this.grdBNMScripts.list =  res.data.data
        }
      } catch (ex) {
        this.ShowError("Lỗi: " + (ex?.data?.faultString || ex));
      }

      },
      grd_SelectionChanged: function (item) {
        if (item && item.length > 0) {
          // this.kichban_id = item[0].ID;
          // this.cboTinh.value = item[0].TINH;
          // this.cboLoaiTBI.value = item[0].LOAITBI;
          // this.cboHangSX.value = item[0].HANGSX;
          // this.txtNgayKhoiTao.value = item[0].NGAY_TAO;
          // this.txtNgayCapNhat.value = item[0].NGAY_CN;
          // this.txtNguoiTao.value = item[0].NGUOI_TAO;
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
        this.popupComponentHeader = 'Chi Tiết Request Từ BSS';
        this.popupComponentData = {
          isPopup: true,
          parrent: 'BNMLogScripts',
          ...item,
          province_id: this.cboTinh.value,
          vendor_id: this.cboHangSX.value,
          service_id: this.cboDichVu.value,
          config_type: this.cboLoaiCH.value
        }; 
        this.Popup("popupComponents");
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
  .disabled {
    cursor: not-allowed;
    color: #666 !important;
  }
  .mx-datepicker {
    width: 100% !important;
  }
  
  .mx-input.disabled,
  .mx-input:disabled {
    color: #666 !important;
  }
  
  .hoso-dientu .page-content {
    position: unset;
  }
  
  @media (min-width: 992px) {
    .modal .modal-huge {
      max-width: 95% !important;
      width: 100% !important;
    }
  }
  @media (min-width: 576px) {
    .modal .modal-huge {
      max-width: 95% !important;
      width: 100% !important;
    }
  }
  </style>