<template>
<b-modal
    ref="VungPhumodal"
    size="xl"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
    no-close-on-backdrop>
<div class="modal-content popup-box">
  <div class="popup-header d-flex ">
      <div class="flex-grow-1 ">
          Chọn vùng phủ    
      </div>
      <div class="close -ap icon-close" data-dismiss="modal" @click='hideModal()' ></div>
  </div>
  <div class="popup-body">
      <div class="row">
        <div class="col-sm-2 col-12" style="display: flex;">
          <div class="info-row">
            <div class="key">
              <div class="check-action" style="flex-grow: 1;">
                <input id="theo-phong-bh" type="radio" name="customer-search-type" v-model="customerSearchType" value="pbh" />
                <div for="theo-phong-bh" class="name">Theo phòng BH</div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-sm-6 col-12">
          <div class="info-row">
            <div class="key">Phòng bán hàng</div>
            <div class="value" style="display: flex;">
              <SelectExt style="flex-grow: 1;" :disabled="customerSearchType!='pbh'" class="" v-model="pbh_donvi_id" 
                @change="loadPbhKhuvucList()" :dataSource="pbh_donvi_list" dataTextField="ten_dv" dataValueField="donvi_id"></SelectExt>
            </div>
          </div>
        </div>
        <div class="col-sm-6 col-12">
          <div class="info-row">
            <div class="key w60">Khu vực</div>
            <div class="value">
              <!-- <SelectExt :disabled="customerSearchType!='pbh'" class="" v-model="pbh_khuvuc_id" 
                @change="changeKV()" :dataSource="pbh_khuvuc_list" dataTextField="ten_kv" dataValueField="khuvuc_id"></SelectExt> -->
                <div class="select-custom">
              <!-- <multiselect v-model="ttvt_khuvuc_id" label="ten_kv" track-by="khuvuc_id" :disabled="customerSearchType!='ttvt'"
              :options="ttvt_khuvuc_list" :multiple="true" :taggable="true" @change="changeKV()"></multiselect> -->
              <!-- <SelectExt :disabled="customerSearchType!='ttvt'" class="" v-model="ttvt_khuvuc_id" 
                @change="changeKV()" :dataSource="ttvt_khuvuc_list" dataTextField="ten_kv" dataValueField="khuvuc_id"></SelectExt> -->
                <ComboboxGrid
                ref='cboDuAn'
                :columns="[
                    { fieldName: 'khuvuc_id', headerText: '',width: '50'},
                    { fieldName: 'ten_kv', headerText: '' },
                ]"
                :enabled="customerSearchType=='pbh'"
                :dataSource='pbh_khuvuc_list'
                :allowPaging='false'
                :enabledSelectFirstRow="false"
                :enablePagingServer='false'
                :gridPanelDataHeight="'200px'"
                :showColumnCheckbox = 'true'
                textField='ten_kv'
                valueField='khuvuc_id'
                v-model='pbh_khuvuc_id'
              ></ComboboxGrid>
              </div>
            </div>
          </div>
        </div>          
      </div>
      
      <div class="row" >
        <div class="col-sm-2 col-md-2 col-12">
          <div class="info-row">
            <div class="key">
              <div class="check-action">
                <input id="theo-ttvt" type="radio" name="customer-search-type" v-model="customerSearchType" value="ttvt" />
                <div for="theo-ttvt" class="name">Theo TTVT</div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-sm-6 col-12">
          <div class="info-row">
            <div class="key">Trung tâm VT</div>
            <div class="value">
              <SelectExt :disabled="customerSearchType!='ttvt'" class="" v-model="ttvt_donvi_id" 
                @change="loadTtcList()" :dataSource="ttvt_donvi_list" dataTextField="ten_dv" dataValueField="donvi_id"></SelectExt>
                <!-- <ejs-dropdownlist
                :enabled="customerSearchType=='ttvt'"
                @change="loadTtcList()"
                                    class="form-control"
                                    locale='vi-VN'
                                    v-model="ttvt_donvi_id"
                                    :dataSource='ttvt_donvi_list' 
                                    :fields="{value:'donvi_id', text: 'ten_dv'}"
                                    :allowFiltering="true"
                                    >
                                </ejs-dropdownlist>  -->
            </div>
          </div>
        </div>
        <div class="col-sm-6 col-12">
          <div class="info-row">
            <div class="key w60">Trạm TC</div>
            <div class="value">
              <SelectExt :disabled="customerSearchType!='ttvt'" class="" v-model="ttc_donvi_id" 
                @change="loadTtvtKhuvucList()" :dataSource="ttc_donvi_list" dataTextField="ten_dv" dataValueField="donvi_id"></SelectExt>
            </div>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-sm-6 col-12">
          <div class="info-row">
            <div class="key">Khu vực</div>
            <div class="value">
              <div class="select-custom">
              <!-- <multiselect v-model="ttvt_khuvuc_id" label="ten_kv" track-by="khuvuc_id" :disabled="customerSearchType!='ttvt'"
              :options="ttvt_khuvuc_list" :multiple="true" :taggable="true" @change="changeKV()"></multiselect> -->
              <!-- <SelectExt :disabled="customerSearchType!='ttvt'" class="" v-model="ttvt_khuvuc_id" 
                @change="changeKV()" :dataSource="ttvt_khuvuc_list" dataTextField="ten_kv" dataValueField="khuvuc_id"></SelectExt> -->
                <ComboboxGrid
                ref='cboDuAn'
                :columns="[
                    { fieldName: 'khuvuc_id', headerText: '',width:'50'},
                    { fieldName: 'ten_kv', headerText: '' },
                ]"
                :enabled="customerSearchType=='ttvt'"
                :dataSource='ttvt_khuvuc_list'
                :allowPaging='false'
                :enabledSelectFirstRow="false"
                :enablePagingServer='false'
                :gridPanelDataHeight="'200px'"
                :showColumnCheckbox = 'true'
                textField='ten_kv'
                valueField='khuvuc_id'
                v-model='ttvt_khuvuc_id'
              ></ComboboxGrid>
              </div>
            </div>
          </div>
        </div>
      </div>      
  </div>
  <div class="popup-footer" style="margin-bottom: 15px;">
    <div class="row">
      <div class="col-sm-12 col-12 ">
        <div class="group-buttons -bottom center">
          <!-- <button class="btn btn-main" @click="Close()">Đóng</button> -->
          <button class="btn btn-primary" @click="accept()">Chấp nhận</button>
        </div>
      </div>
    </div>
  </div>
        
</div>
</b-modal>

</template>
<script>
import Vue from "vue";
import Multiselect from 'vue-multiselect'
import ComboboxGrid from '../../../../../components/Controls/ComboboxGrid'
export default {
    name: "VungPhuModal",
    comments :{Multiselect,ComboboxGrid},
    props: {
      data:{
          type:Object,
          default:{}
      }
    },
    data () {
        return{
            result:this.data,
            customerSearchType: 'pbh',
            pbh_donvi_list: [],
            pbh_donvi_id: null,
            pbh_khuvuc_list: [],
            pbh_khuvuc_id: null,
            phb_quan_list: [],
            pbh_quan_id: null,
            phb_phuong_list: [],
            pbh_phuong_id: null,
            ttvt_donvi_id: [],
            ttvt_donvi_list: null,
            ttc_donvi_id: [],
            ttc_donvi_list: null,
            ttvt_khuvuc_list: [],
            ttvt_khuvuc_id: null,
            input_vungphu:''
        }
    },
    mounted(){
        this.loadPbhAndTtvt();
    },
    computed:{
      vungphu_created(){
        return this.pbh_kv_created+this.ttvt_kv_created;
      }
    },
    watch :{
      vungphu_created(val){
        this.input_vungphu=val
      },
    },
    methods: {
      showModal() {
      this.$refs["VungPhumodal"].show()
      },
      hideModal() {
      this.$refs["VungPhumodal"].hide()
      },
      async loadPbhAndTtvt() {
        try{
          this.loading(true)
          var pbh_data = await this.axios.post('/web-hopdong/hopdong/b2a_lay_ds_donvi_ql', { vdonvi_id: 900, vloaidv_id: 2 })
          if (pbh_data.data.data && pbh_data.data.data.length > 0) {
              this.pbh_donvi_list = pbh_data.data.data
              this.pbh_donvi_id = this.pbh_donvi_list[0].donvi_id
          } else {
              this.pbh_donvi_list = []
              this.pbh_donvi_id = null
          }
          var ttvt_data = await this.axios.post('/web-hopdong/hopdong/b2a_lay_ds_donvi_ql', { vdonvi_id: 900, vloaidv_id: 3 })
          if (ttvt_data.data.data && ttvt_data.data.data.length > 0) {
              this.ttvt_donvi_list = ttvt_data.data.data
              this.ttvt_donvi_id = this.ttvt_donvi_list[0].donvi_id
          } else {
              this.ttvt_donvi_list = []
              this.ttvt_donvi_id = null
          }
          this.loading(false)
        }
        catch(err){

        }
      },
      async loadPbhKhuvucList() {
        try{
          this.result.customerSearchType = this.customerSearchType;
          this.result.pbh_id = this.pbh_donvi_id;
          this.loading(true)
          var data = await this.axios.post('/web-hopdong/hopdong/b2a_lay_khuvuc_theo_donvi', { vdonvi_id: this.pbh_donvi_id })
          if (data.data.data && data.data.data.length > 0) {
              this.pbh_khuvuc_list = data.data.data
              this.pbh_khuvuc_id = this.pbh_khuvuc_list[0].khuvuc_id
          } else {
              this.pbh_khuvuc_list = []
              this.pbh_khuvuc_id = null
          }
          this.loading(false)
        }
        catch(err){

        }
      },
      async loadTtcList() {
        try{
          this.result.customerSearchType = this.customerSearchType;
          this.result.ttvt_id = this.ttvt_donvi_id;
          this.loading(true)
          var data = await this.axios.post('/web-hopdong/hopdong/b2a_lay_ds_donvi_ql', { vdonvi_id: this.ttvt_donvi_id, vloaidv_id: 5 })
          if (data.data.data && data.data.data.length > 0) {
              this.ttc_donvi_list = data.data.data
              this.ttc_donvi_id = this.ttc_donvi_list[0].donvi_id
              this.loadTtvtKhuvucList();
          } else {
              this.ttc_donvi_list = []
              this.ttc_donvi_id = null
          }
          this.loading(false)
        }
        catch(err){

        }
      },
      async loadTtvtKhuvucList() {
        try{
          this.result.tram_id = this.ttc_donvi_id;
          this.loading(true)
          //console.log('this.ttc_donvi_id')
          //console.log(this.ttc_donvi_id)
          var data = await this.axios.post('/web-hopdong/hopdong/b2a_lay_khuvuc_theo_donvi', { vdonvi_id: this.ttc_donvi_id })
          if (data.data.data && data.data.data.length > 0) {
              this.ttvt_khuvuc_list = data.data.data
              this.ttvt_khuvuc_id = this.ttvt_khuvuc_list[0].khuvuc_id
          } else {
              this.ttvt_khuvuc_list = []
              this.ttvt_khuvuc_id = null
          }
          this.loading(false)
        }
        catch(err){

        }
      },



      changeKV(){
        this.result.khuvuc_id = this.customerSearchType == 'pbh' ?  this.pbh_khuvuc_id :  this.ttvt_khuvuc_id; 

      },
      Close(){
        this.$parent.hide()
      },
      ttvt_kv_created(){
        let name ='';
        for(let i =0 ; i < this.result.khuvuc_id.length ; i++){
          let kv = this.ttvt_khuvuc_list.find(x=>x.khuvuc_id==this.result.khuvuc_id[i])
          if(kv!=undefined){
            name = name + kv.ten_kv + ', '
          }
        }
        return name;
      },
      pbh_kv_created(){
        let name ='';
        for(let i =0 ; i < this.result.khuvuc_id.length ; i++){
          let kv = this.pbh_khuvuc_list.find(x=>x.khuvuc_id==this.result.khuvuc_id[i])
          if(kv!=undefined){
            name = name + kv.ten_kv + ', '
          }
        }
        return name;
      },     
      accept(){
        this.input_vungphu = '';
        if(this.customerSearchType == "pbh"){
          this.result.pbh_id = this.pbh_donvi_id;
          this.result.khuvuc_id = this.pbh_khuvuc_id;          
          this.input_vungphu = this.pbh_kv_created();
        }
        if(this.customerSearchType == "ttvt"){
          this.result.ttvt_id = this.ttvt_donvi_id;
          this.result.tram_id = this.ttc_donvi_id;
          this.result.khuvuc_id = this.ttvt_khuvuc_id;
          this.input_vungphu = this.ttvt_kv_created();
        }
        this.result.customerSearchType = this.customerSearchType;
        console.log(this.result)
        let data=Object.assign({}, this.result)
        this.$emit('accept', Object.assign(data,{
          vungphu:this.input_vungphu
        }))
        this.hideModal()
      },
      addTag (newTag) {
      const tag = {
        name: newTag,
        code: newTag.substring(0, 2) + Math.floor((Math.random() * 10000000))
      }
      this.options.push(tag)
      this.value.push(tag)
    }
    }
}
</script>
