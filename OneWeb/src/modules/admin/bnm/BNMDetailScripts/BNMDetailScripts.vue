<template>
  <div>
	  <breadcrumb :header="header" />
    <div class="list-actions-top">
      <ul class="list">
        <li>
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
        </li>
      </ul>
    </div>
    <div class="page-content">
      <div class="box-form">
        <div class="legend-title">Thông tin kịch bản BNM</div>
        <div class="row">
          <div class="col-sm-4 col-12">
            <div class="info-row">
              <div class="key w100">{{ cboTinh.text }}</div>
              <div class="value">
                <div class="select-custom" ref="cboTinh">
                  <select2 :z-index="2000" :settings="{ dropdownParent: $refs['cboTinh'] }"
                    v-model="cboTinh.value" class="select2"
                    :options="cboTinh.Items && cboTinh.Items.length > 0 ? cboTinh.Items.map(e=>({id:e.MA_TINH,text:e.TINH})) : []">
                  </select2>
                </div>
              </div>                  
            </div>
          </div>
          <div class="col-sm-4 col-12">
            <div class="info-row">
              <div class="key w100">{{ cboHangSX.text }}</div>
              <div class="value">
                <div class="select-custom" ref="cboHangSX">
                  <select2 :z-index="2000" :settings="{ dropdownParent: $refs['cboHangSX'] }"
                    v-model="cboHangSX.value" class="select2"
                    :options="cboHangSX.Items && cboHangSX.Items.length > 0 ? cboHangSX.Items.map(e=>({id:e.MA, text:`[ ${e.MA} ] ${e.HANGSX}`})) : []">
                  </select2>
                </div>
              </div>                  
            </div>
          </div>
          <div class="col-sm-4 col-12">
            <div class="info-row">
              <div class="key w100">{{ cboLoaiTBI.text }}</div>
              <div class="value">
                <div class="select-custom" ref="cboLoaiTBI">
                  <select2 :z-index="2000" :settings="{ dropdownParent: $refs['cboLoaiTBI'] }"
                    v-model="cboLoaiTBI.value" class="select2"
                    :options="cboLoaiTBI.Items && cboLoaiTBI.Items.length > 0 ? cboLoaiTBI.Items.map(e=>({id:e.LOAITBI, text:`[ ${e.LOAITBI} ] ${e.TEN_LOAITBI}`})) : []">
                  </select2>
                </div>
              </div>                  
            </div>
          </div>          
        </div>        
      </div>

      <div class="row">
        <div class="col-sm-6 col-12">
          <div class="box-form">
            <div class="legend-title">PARAMS BSS</div>
            <div class="info-row">
              <div class="key w100">{{ formBSS.txtParam1.text }}</div>
              <div class="value">
                <input type="text" class="form-control" v-model="formBSS.txtParam1.value">
              </div>                  
            </div>
            <div class="info-row">
              <div class="key w100">{{ formBSS.txtParam2.text }}</div>
              <div class="value">
                <input type="text" class="form-control" v-model="formBSS.txtParam2.value">
              </div>                  
            </div>
            <div class="info-row">
              <div class="key w100">{{ formBSS.txtParam3.text }}</div>
              <div class="value">
                <input type="text" class="form-control" v-model="formBSS.txtParam3.value">
              </div>                  
            </div>
          </div>          
        </div>      
        <div class="col-sm-6 col-12">        
          <div class="box-form">
            <div class="legend-title">PARAMS BNM</div>
            <div class="info-row">
              <div class="key w100">{{ formBNM.txtParam1.text }}</div>
              <div class="value">
                <input type="text" class="form-control" v-model="formBNM.txtParam1.value">
              </div>                  
            </div>
            <div class="info-row">
              <div class="key w100">{{ formBNM.txtParam2.text }}</div>
              <div class="value">
                <input type="text" class="form-control" v-model="formBNM.txtParam2.value">
              </div>                  
            </div>
            <div class="info-row">
              <div class="key w100">{{ formBNM.txtParam3.text }}</div>
              <div class="value">
                <input type="text" class="form-control" v-model="formBNM.txtParam3.value">
              </div>                  
            </div>
          </div>
        </div>      
      </div>      

      <div class="row">
        <div class="col-sm-6 col-12">
          <div class="box-form">
            <div class="legend-title">SCRIPTS TEMP</div>
            <div class="info-row">
              <div class="value">
                <pre v-html="JSON.stringify(formBSS, null, 2)"></pre>
              </div>                  
            </div>
          </div>          
        </div>      
        <div class="col-sm-6 col-12">        
          <div class="box-form">
            <div class="legend-title">SCRIPTS RESULT</div>
            <div class="info-row">
              <div class="value">
                <pre v-html="JSON.stringify(formBNM, null, 2)"></pre>
              </div>                  
            </div>
          </div>
        </div>      
      </div>  
    </div>
  </div>
</template>

<script>
import breadcrumb from "@/components/breadcrumb";
import moment from 'moment'
export default {
  name: "BNMDetailScripts",
  components: {
    breadcrumb,
  },
  props: ['dulieu'],
  data: () => ({
    header: {
      title: "Chi tiết kịch bản BNM",
      list: [
        {
          name: 'Chi tiết kịch bản BNM',
          link: { 
            name: 'Ui.buttons',
            active: true 
          }
        }
      ],
    },
    btnSave: {text: 'Cập nhật', Visibled: true},
    btnDelete: {text: 'Xóa', Visibled: true},
    cboTinh: {text: 'Tỉnh', value: '', Items: []},
    cboLoaiTBI: {text: 'Loại thiết bị', value: '', Items: []},
    cboHangSX: {text: 'Hãng SX', value: '', Items: []},
    formBSS: {
      txtParam1: {text: 'param1', value: ''},      
      txtParam2: {text: 'param2', value: '', readOnly: true},
      txtParam3: {text: 'param3', value: ''},
    },    
    formBNM: {
      txtParam1: {text: 'param1', value: ''},      
      txtParam2: {text: 'param2', value: '', readOnly: true},
      txtParam3: {text: 'param3', value: ''},
    },   
  }),
  methods: {
  },
  watch: {
    "dulieu"(val) {
      if (val) {
        this.cboTinh.value = val.TINH
        this.cboLoaiTBI.value = val.LOAITBI
        this.cboHangSX.value = val.HANGSX
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