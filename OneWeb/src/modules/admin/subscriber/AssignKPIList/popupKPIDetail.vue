<template>
  <div>
	  <breadcrumb :header="header" v-if="dulieu && dulieu.isPopup == false"/>
    <div class="list-actions-top">
      <ul class="list">
        <li>
          <a href="javascript:;">
            <span class="icon one-file-plus"></span>Nhập mới
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
        </li>
      </ul>
    </div>
    <div class="page-content">
      <div class="box-form">
        <!-- <div class="legend-title">Thông tin Nhân viên</div> -->
        <div class="row">
          <div class="col-sm-4 col-12">
            <div class="info-row">
              <div class="key w100">{{ txtMaNV.text }}</div>
              <div class="value">
                <!-- <div class="select-custom" ref="txtMaNV">
                  <select2 :z-index="2000" :settings="{ dropdownParent: $refs['txtMaNV'] }"
                    v-model="txtMaNV.value" class="select2"
                    :options="txtMaNV.Items && txtMaNV.Items.length > 0 ? txtMaNV.Items.map(e=>({id:e.MA_TINH,text:e.TINH})) : []">
                  </select2>
                </div> -->
                <input type="text" class="form-control highlight" v-model="txtMaNV.value" />
              </div>                  
            </div>
          </div>
          <div class="col-sm-4 col-12">
            <div class="info-row">
              <div class="key w100">{{ txtTenNV.text }}</div>
              <div class="value">
                <!-- <div class="select-custom" ref="txtTenNV">
                  <select2 :z-index="2000" :settings="{ dropdownParent: $refs['txtTenNV'] }"
                    v-model="txtTenNV.value" class="select2"
                    :options="txtTenNV.Items && txtTenNV.Items.length > 0 ? txtTenNV.Items.map(e=>({id:e.LOAITBI, text:`[ ${e.LOAITBI} ] ${e.TEN_LOAITBI}`})) : []">
                  </select2>
                </div> -->
                <input type="text" class="form-control highlight" v-model="txtTenNV.value" />
              </div>                  
            </div>
          </div>      
          <div class="col-sm-4 col-12">
            <div class="info-row">
              <div class="key w100">{{ cboLoaiNV.text }}</div>
              <div class="value">
                <!-- <div class="select-custom" ref="cboLoaiNV">
                  <select2 :z-index="2000" :settings="{ dropdownParent: $refs['cboLoaiNV'] }"
                    v-model="cboLoaiNV.value" class="select2"
                    :options="cboLoaiNV.Items && cboLoaiNV.Items.length > 0 ? cboLoaiNV.Items.map(e=>({id:e.MA, text:`[ ${e.MA} ] ${e.HANGSX}`})) : []">
                  </select2>                  
                </div> -->
                <input type="text" class="form-control highlight" v-model="cboLoaiNV.value" />
              </div>                  
            </div>
          </div>              
        </div>        
      </div>
      <div class="box-form">
        <div class="legend-title">Dịch vụ Fiber</div>
        <div class="row">
          <DataGrid :columns="grdFiber.header"
            :dataSource="grdFiber.list" :enable-paging-server="false" :allowPaging="true" :showFilter="true"
            :showColumnCheckbox="true" :enabledSelectFirstRow="true" ref="grdFiber"
            @selectedItemsChanged="grdFiber_SelectionChanged" />
        </div>        
      </div>
      <div class="box-form">
        <div class="legend-title">Dịch vụ MyTV</div>
        <div class="row">
          <DataGrid :columns="grdMyTV.header"
            :dataSource="grdMyTV.list" :enable-paging-server="false" :allowPaging="true" :showFilter="true"
            :showColumnCheckbox="true" :enabledSelectFirstRow="true" ref="grdMyTV"
            @selectedItemsChanged="grdMyTV_SelectionChanged" />
        </div>        
      </div>
      <div class="box-form">
        <div class="legend-title">Dịch vụ SMH</div>
        <div class="row">
          <DataGrid :columns="grdSMH.header"
            :dataSource="grdSMH.list" :enable-paging-server="false" :allowPaging="true" :showFilter="true"
            :showColumnCheckbox="true" :enabledSelectFirstRow="true" ref="grdSMH"
            @selectedItemsChanged="grdSMH_SelectionChanged" />
        </div>        
      </div>
    </div>
  </div>
</template>

<script>
import breadcrumb from "@/components/breadcrumb";
import moment from 'moment'
import DataGrid from "@/components/Controls/DataGrid";
export default {
  name: "popupKPIDetail",
  components: {
    breadcrumb,
    DataGrid
  },
  props: ['dulieu'],
  data: () => ({
    header: {
      title: "Chi tiết Phân giao KPI",
      list: [
        {
          name: 'Chi tiết Phân giao KPI',
          link: { 
            name: 'Ui.buttons',
            active: true 
          }
        }
      ],
    },
    btnSave: {text: 'Cập nhật', Visibled: true},
    btnDelete: {text: 'Xóa', Visibled: true},
    txtMaNV: {text: 'Mã nhân viên(*)', value: '', Items: []},
    txtTenNV: {text: 'Tên nhân viên(*)', value: '', Items: []},
    cboLoaiNV: {text: 'Loại nhân viên', value: '', Items: []},    
    grdFiber: {
      list: [],
      header: [
        {
          fieldName: "ID",
          headerText: "ID",
          allowFiltering: true,
        },
        {
          fieldName: "LOAI_CT",
          headerText: "Loại chỉ tiêu",
          allowFiltering: true,
        },
        {
          fieldName: "GIATRI",
          headerText: "Giá trị",
          allowFiltering: true,
          format:'N'
        },        
      ],
      value: {},
      isEnabled: true,
      checked: [],
    },
    grdMyTV: {
      list: [],
      header: [
        {
          fieldName: "ID",
          headerText: "ID",
          allowFiltering: true,
        },
        {
          fieldName: "LOAI_CT",
          headerText: "Loại chỉ tiêu",
          allowFiltering: true,
        },
        {
          fieldName: "GIATRI",
          headerText: "Giá trị",
          allowFiltering: true,
          format:'N'
        },        
      ],
      value: {},
      isEnabled: true,
      checked: [],
    },
    grdSMH: {
      list: [],
      header: [
        {
          fieldName: "ID",
          headerText: "ID",
          allowFiltering: true,
        },
        {
          fieldName: "LOAI_CT",
          headerText: "Loại chỉ tiêu",
          allowFiltering: true,
        },
        {
          fieldName: "GIATRI",
          headerText: "Giá trị",
          allowFiltering: true,
          format:'N'
        },        
      ],
      value: {},
      isEnabled: true,
      checked: [],
    },
  }),
  mounted: function () {
    this.formLoad()
  },
  methods: {
    formLoad: async function () {
      this.txtMaNV.value = this.dulieu.MA_NV
      this.txtTenNV.value = this.dulieu.TEN_NV
      this.cboLoaiNV.value = this.dulieu.LOAI_NV

      try {
        await Promise.all([this.getFiberList(), this.getMyTVList(), this.getSMHList()])
      } catch (ex) {
        console.log(ex)
      }        
    },
    getFiberList: function () {
      this.grdFiber.list = [
        {
          ID: 1,
          LOAI_CT: 'TB PTM',
          GIATRI: 1000
        },
        {
          ID: 2,
          LOAI_CT: 'DT PTM',
          GIATRI: 50000000
        }
      ]
    },
    grdFiber_SelectionChanged: function (item) {
      if (item && item.length > 0) {
      }      
    },
    getMyTVList: function () {
      this.grdMyTV.list = [
        {
          ID: 1,
          LOAI_CT: 'TB MyTV FIX PTM',
          GIATRI: 1000
        },
        {
          ID: 2,
          LOAI_CT: 'DT MyTV PTM',
          GIATRI: 50000000
        }
      ]
    },
    grdMyTV_SelectionChanged: function (item) {
      if (item && item.length > 0) {
      }      
    },
    getSMHList: function () {
      this.grdSMH.list = [
        {
          ID: 1,
          LOAI_CT: 'TB Camera PTM',
          GIATRI: 1000
        },
        {
          ID: 2,
          LOAI_CT: 'DT Camera PTM',
          GIATRI: 50000000
        }
      ]
    },
    grdSMH_SelectionChanged: function (item) {
      if (item && item.length > 0) {
      }      
    },
  },
  watch: {
    "dulieu"(val) {
      if (val) {
        this.txtMaNV.value = val.MA_NV
        this.txtTenNV.value = val.TEN_NV
        this.cboLoaiNV.value = val.LOAI_NV
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