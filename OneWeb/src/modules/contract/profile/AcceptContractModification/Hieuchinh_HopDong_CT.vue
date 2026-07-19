<template>
  <b-modal
    ref="modelhieuchinh_hd_ct"
    size="xl"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
    no-close-on-backdrop
    @show="handleShowModal"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span> Hiệu chỉnh hợp đồng
        </div>
        <div class="close -ap icon-close" @click="hideModal"></div>
      </div>
      <div class="list-actions-top">
        <ul class="list">
            <li>
                <a href="#" @click.prevent="btn_hoanthien">
                    <span class="icon one-save"></span>Hoàn thiện
                </a>
            </li>
            <li>
                <a href="#" @click.prevent="hideModal">
                    <span class="icon nc-icon-glyph ui-1_circle-remove"></span>Thoát
                </a>
            </li>
            
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form" ref="mainbody">
            <!-- <div>
                <div class="col-md-12">
                    <div class="info-row">
                    <div class="key nowrap">
                        Đơn vị
                    </div>
                    <div class="value">
                        <ComboboxGrid :valueField="cboDViTimKiem.valueField" :textField="cboDViTimKiem.textField"
                                    v-model="cboDViTimKiem.selectDViTimKiem" :value="cboDViTimKiem.selectDViTimKiem"
                                    :columns="cboDViTimKiem.headers"
                                    :panelDataHeight="cboDViTimKiem.panelDataHeight" :enabledSelectFirstRow="false"
                                    :dataSource="cboDViTimKiem.data" @selectedChanged="cbo_changed">
                        </ComboboxGrid>
                    </div>
                    </div>
                </div>
            </div> -->
            <div v-for="item of  listbox.list_display" :key="item.field_name" class="row" >
                <div v-if="item.att == 0" class="col-sm-12 col-12">
                    <div class="info-row">
                        <div class="key">{{item.ten_hienthi}}</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model="item.value">
                        </div>
                    </div>
                </div>
                <div v-if="item.att == 2" class="col-sm-12 col-12">
                    <div class="info-row">
                        <div class="key"> {{item.ten_hienthi}}</div>
                        <div class="value">
                            <div class="input-icon-right">
                                <flat-pickr class="form-control" :config="dateconfig" v-model="item.value" />
                                <span class="icon one-calendar"></span>
                            </div>
                        </div>
                    </div>
                </div>
                <div v-if="item.att == 4" class="col-sm-12 col-12">
                    <div class="info-row">
                        <div class="key"> {{item.ten_hienthi}}</div>
                        <div class="value">
                             <div class="select-custom">
                                <select2 :options="item.data" v-model="item.value" v-on:change="cbo_changed(item.tag)">
                                    <option disabled value="0">Select one</option>
                                </select2>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- <div v-if="item.att == 5" class="col-sm-12 col-12">
                    <div class="info-row">
                        <div class="key">button</div>
                        <div class="value">
                            <input type="text" class="form-control">
                        </div>
                    </div>
                </div> -->
                <div v-if="item.att == 6" class="col-sm-12 col-12">
                    <div class="info-row">
                       <div class="key nowrap">
                            {{item.ten_hienthi}}
                        </div>
                       <div class="value">
                            <ComboboxGrid :valueField="item.valueField" :textField="item.textField"
                                        v-model="item.value" :value="item.value"
                                        :columns="item.headers"
                                        :enabledSelectFirstRow="false"
                                        :dataSource="item.data"
                                        @selectedChanged="cbo_changed(item.valueField)">
                            </ComboboxGrid>
                        </div>
                    </div>
                </div>
                <div v-if="item.att == 7" class="col-sm-12 col-12">
                    <div class="info-row">
                        <div class="key">checkbox</div>
                        <div class="value">
                            <input type="text" class="form-control">
                        </div>
                    </div>
                </div>
                <div v-if="item.att == 10" class="col-sm-12 col-12">
                    <div class="info-row">
                        <div class="key">checcombobox</div>
                        <div class="value">
                            <input type="text" class="form-control">
                        </div>
                    </div>
                </div>

            </div>
          

        </div>
        
      </div>
    </div>
  </b-modal>
</template>

<script>

import API from './API'
import DatabaseConstants from '@/const/enums/DBConstants'
export default {
    components: { DatabaseConstants },
    name: 'Hieuchinh_HopDong_CT',
    props:{
        nhomhc_id:"",
        loaihc_id:"",
        hdkh_id:"",
        hdtt_id:"",
        hdtb_id:"",
        tdtt_hdtb_id:""

    },
  
    data(){
        return {
            dateconfig: {
                altFormat: 'd/m/Y',
                altInput: true,
                dateFormat: 'd/m/Y',
                allowInput: true,
            },
            cboDViTimKiem: {
                data: [],
                textField: 'ten_dv',
                valueField: 'donvi_id',
                panelDataHeight: 'auto',
                headers: [
                {
                    fieldName: 'ten_dv',
                    headerText: 'Tên đơn vị',
                    allowFiltering: true,
                    allowSorting: false
                }
                ],
                selectDViTimKiem: 0
            },
            
            listbox:{
                ts_hieuchinh:[],
                list_display:[],
                test:[]
            }

        }
    },
    methods: {
        handleShowModal: async function () {
            this.loading(true);
            this.listbox.ts_hieuchinh = [];
            this.listbox.list_display = [];

         
          

            var data = {
                            "vnhomhc_id": this.nhomhc_id,
                            "vloaihc_id": this.loaihc_id,
                            "vhdkh_id":this.hdkh_id,
                            "vhdtt_id": this.hdtt_id,
                            "vhdtb_id" : this.hdtb_id
                        };
            await API.lay_thongtin_hieuchinh(this.axios,data).then(response=>{
                if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
                    this.listbox.ts_hieuchinh = response.data.data;
                }
                else{

                    this.$toast.error('Chưa thiết lập tham số. Liên hệ admin để được giải đáp. ('+this.nhomhc_id+'-'+this.hdkh_id+'-'+this.hdtt_id+'-'+this.hdtb_id+')!');
                    this.$refs.modelhieuchinh_hd_ct.hide();
                }
            });


            if (this.listbox.ts_hieuchinh.length==0){
                this.loading(false);
                return;
            }

            //this.listbox.ts_hieuchinh.forEach(async (item) => {
            for(let i = 0;i<this.listbox.ts_hieuchinh.length;i++) {
                var control={};
                if (this.listbox.ts_hieuchinh[i].att == 0 || this.listbox.ts_hieuchinh[i].att == 2 || this.listbox.ts_hieuchinh[i].att == 4 ){ // gridlookupedit
                    

                    control.tag = this.listbox.ts_hieuchinh[i].field_name;
                    control.att = this.listbox.ts_hieuchinh[i].att;
                    control.ten_hienthi = this.listbox.ts_hieuchinh[i].ten_hienthi;
                    control.depend_field = this.listbox.ts_hieuchinh[i].depend_field;
                    
                    // var sql = "";
                    
                    // if (this.listbox.ts_hieuchinh[i].lookup_sql!="" && this.listbox.ts_hieuchinh[i].lookup_sql != undefined){
                    //     sql = this.listbox.ts_hieuchinh[i].lookup_sql;
                    //     sql = sql.replaceAll('{?ADMIN}',DatabaseConstants.DB1);
                    //     sql = sql.replaceAll('{?CSS}',DatabaseConstants.DB2);
                    //     if (this.listbox.ts_hieuchinh[i].depend_field != "" || this.listbox.ts_hieuchinh[i].depend_field != undefined){
                    //         var gt = "";
                    //         this.listbox.list_display.forEach(control_item=>{
                    //             if (control_item.tag = this.listbox.ts_hieuchinh[i].depend_field)
                    //                 gt = "";

                    //         });
                    //         sql = sql.replaceAll('{?'+this.listbox.ts_hieuchinh[i].depend_field+'}',gt);
                    //     }
                    // }
                    // else{
                    //     sql = "select " + this.listbox.ts_hieuchinh[i].lookup_keyfield + "," + this.listbox.ts_hieuchinh[i].lookup_displayfield + " from " + this.listbox.ts_hieuchinh[i].lookup_table ;
                    //     if (this.listbox.ts_hieuchinh[i].lookup_descfield != "")
                    //         sql += " order by " + this.listbox.ts_hieuchinh[i].lookup_descfield;
                        
                    // }

                    // await API.execute_get_query(this.axios,{"str_query": sql}).then(response=>{
                    //     if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
                    //         control.data = response.data.data;
                    //     }
                    //     else
                    //         control.data = [];
                    // });    

                    control.data = this.listbox.ts_hieuchinh[i].lookup_sql_response;
                    if (this.listbox.ts_hieuchinh[i].att == 4)
                        control.data = this.listbox.ts_hieuchinh[i].lookup_sql_response.map(x=>{return {id:x[this.listbox.ts_hieuchinh[i].lookup_keyfield.toLowerCase()],text:x[this.listbox.ts_hieuchinh[i].lookup_displayfield.toLowerCase()]}});
                    
                    // control.textField = this.listbox.ts_hieuchinh[i].lookup_displayfield.split(';')[1].toLowerCase(); 
                    // control.valueField = this.listbox.ts_hieuchinh[i].lookup_keyfield.toLowerCase(); 

                //     var headers = [];
                //     var lookup_displayfield = this.listbox.ts_hieuchinh[i].lookup_displayfield.split(';');
                //     var lookup_captionfield = this.listbox.ts_hieuchinh[i].lookup_captionfield.split(';');

                //    for (let i = 0; i < lookup_displayfield.length; i++) {
                //         var header = {};
                //         header.fieldName = lookup_displayfield[i].toLowerCase(); 
                //         header.headerText = lookup_captionfield[i];
                //         header.allowFiltering = true;
                //         header.allowSorting = false;
                //         headers.push(header);
                //     }
                //     control.headers = headers;
                    control.value = "";
                    this.listbox.list_display.push(control);
                    
                }

            };
            this.loading(false);
        },

        cbo_changed:async function(control_tag){
            this.loading(true);
            
          

            //truyen vao tag cua control --> can lay ra value va independ control
            var val = "";
            var curren_tag = "";
            var respone_data = [];
            this.listbox.list_display.forEach(item => {
                if (item.tag == control_tag )
                    val = item.value;
            });

            var data = {
                        "vnhomhc_id": this.nhomhc_id,
                        "vloaihc_id": this.loaihc_id,
                        "vhdkh_id":this.hdkh_id,
                        "vhdtt_id": this.hdtt_id,
                        "vhdtb_id" : this.hdtb_id,
                        "depend_field_value": val
                    };

            await API.lay_thongtin_hieuchinh(this.axios,data).then(response=>{
                if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
                    var buff = response.data.data.filter(x=>(x.depend_field == control_tag))[0];
                    if (buff != "" && buff != undefined)
                    respone_data = buff.lookup_sql_response.map(x=>{return {id:x[buff.lookup_keyfield.toLowerCase()],text:x[buff.lookup_displayfield.toLowerCase()]}});
                    //console.log(respone_data);
                }
                else{
                    this.$toast.error('Chưa thiết lập tham số. Liên hệ admin để được giải đáp. ('+this.nhomhc_id+'-'+this.hdkh_id+'-'+this.hdtt_id+'-'+this.hdtb_id+')!');
                    this.$refs.modelhieuchinh_hd_ct.hide();
                }
            });

            this.listbox.ts_hieuchinh.forEach(item=>{
                if (item.depend_field == control_tag)
                    curren_tag = item.field_name;
                
            });

            this.listbox.list_display.forEach(item => {
                if (item.tag == curren_tag )
                    item.data = respone_data;
            });
           
            this.loading(false);
        },

        hideModal () {
            this.$refs.modelhieuchinh_hd_ct.hide();
        },
        
        showModal () {
            this.$refs.modelhieuchinh_hd_ct.show();
        },


        btn_hoanthien:async function(){
            var ds_ls_hieuchinh = [];
            this.loading(true);

            this.listbox.list_display.forEach(item=>{
                if (item.value != "" && item.value != null && item.value != undefined)
                    console.log(item.tag+':'+item.value);
            });

           

            for(let i = 0;i<this.listbox.ts_hieuchinh.length;i++){
                if (this.listbox.ts_hieuchinh[i].hieuchinh == 1){
                    var rowls_hieuchinh = {};
                    rowls_hieuchinh.HDKH_ID = this.hdkh_id;
                    rowls_hieuchinh.HDTT_ID = this.hdtt_id;
                    rowls_hieuchinh.HDTB_ID = this.hdtb_id;
                    rowls_hieuchinh.TABLE_NAME = this.listbox.ts_hieuchinh[i].table_name;
                    rowls_hieuchinh.FIELD_NAME = this.listbox.ts_hieuchinh[i].field_name;
                    rowls_hieuchinh.GIATRI_MOI = '';
                    rowls_hieuchinh.GIATRI_CU = '';
                    rowls_hieuchinh.TDTT_HDTB_ID = this.tdtt_hdtb_id;
                    console.log(rowls_hieuchinh);
                    ds_ls_hieuchinh.push(rowls_hieuchinh);
                }
            }
            var data = {
                            "vnhomhc_id": this.nhomhc_id,
                            "vloaihc_id": this.loaihc_id,
                            "ds_ls_hieuchinh": JSON.stringify(ds_ls_hieuchinh),
                            "vtdtt_hdtb_id": this.tdtt_hdtb_id
                        }
            console.log(data);
            await API.insert_ls_hieuchinh(this.axios,data).then(response=>{
                if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
                    this.$toast.success('Hiệu chỉnh thông tin thành công!');

                }
                else{
                    this.$toast.error(response.data.message);
                }
            });
            this.loading(false);
        },

        


       
        
    }
}
</script>
