<template>
  <b-modal
    ref="modelct_tien_km"
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
          <span class="icon one-notepad"></span> Chi tiết tiền theo khoản mục
        </div>
        <div class="close -ap icon-close" @click="hideModal"></div>
      </div>
      <div class="list-actions-top">
        <ul class="list">
            <li>
                <a href="#" @click.prevent="hideModal">
                    <span class="icon one-save"></span>Chấp nhận
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
          <div class="legend-title">Chi tiết tiền theo khoản mục</div>
            <div v-for="item in listmoney" :key="item.tentat_kmtt">
                <div class="row">  
                    <div class="col-sm-4 col-12"> 
                        <div class="info-row"> 
                            <div class="key w100">{{item.tentat_kmtt}}</div> 
                        </div> 
                    </div> 
                    <div class="col-sm-4 col-12"> 
                        <div class="info-row"> 
                            <div class="key w100">Tiền</div>  
                        </div> 
                    </div> 
                    <div class="col-sm-4 col-12"> 
                        <div class="info-row">  
                            <div class="key w100">Vat</div> 
                        </div> 
                    </div> 
                </div>  
                <div class="row">
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="value"> <vue-numeric class="form-control tright" separator=","
                                                            v-model="item.s_tongtien"  id="totalPrice" placeholder="0"
                                                            output-type="Number">
                                                </vue-numeric> </div>
                        </div>
                    </div>
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="value"> <vue-numeric class="form-control tright" separator=","
                                                            v-model="item.s_tien"  id="totalPrice" placeholder="0"
                                                            output-type="Number">
                                                </vue-numeric></div>
                        </div>
                    </div>
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="value"> <vue-numeric class="form-control tright" separator=","
                                                            v-model="item.s_vat"  id="totalPrice" placeholder="0"
                                                            output-type="Number">
                                                </vue-numeric> </div>
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
    name: 'CT_Tien_KM',
    props:{
        loaihd_id:0,
        hdtb_id:0
    },
  
    data(){
        return {
            
            listbox:{
                ds_dv_tinh:{
                    list:[],
                    value:""
                },
            },
            listmoney:[],
        }
    },
    methods: {
        handleShowModal: async function () {
            this.loading(true);

            var list_tien = []; 
            var tongtien = 0, tien = 0 , vat = 0;
            // var s_tongtien = '', s_tien = '' , s_vat = '';

        //    this.hdtb_id = 6162590;
        //    this.loaihd_id = 1;
            if (this.hdtb_id != "" && this.loaihd_id != ""){
                await API.lay_tien_hopdong(this.axios,this.hdtb_id).then(response=>{
                if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
                    list_tien = response.data.data;
                }
                else
                list_tien =[];
            });

            // let moneyformat = Intl.NumberFormat('en-US');

            var data = {
                        "phanvung_id": this.$root.token.getPhanVungID(),
                        "vloaihd_id": this.loaihd_id
                        }
            API.lay_khoanmuc_tt_theo_loaihd(this.axios,data).then(response=>{
                var html = ' <div class="legend-title">Chi tiết tiền theo khoản mục</div> ';
                if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
                    response.data.data.forEach(element => {
                        tongtien = 0; tien = 0 ; vat = 0;
                        list_tien.forEach(item_tien=>{
                            if (item_tien.khoanMucTtId == element.khoanmuctt_id){
                                tien = Math.abs(item_tien.tien) ; 
                                vat = Math.abs(item_tien.vat);
                                tongtien = tien+ vat;
                                // s_tien = moneyformat.format(tien).replace(/\D/g, "").replace(/\B(?=(\d{3})+(?!\d))/g, ",");;
                                // s_vat = moneyformat.format(vat).replace(/\D/g, "").replace(/\B(?=(\d{3})+(?!\d))/g, ",");;
                                // s_tongtien = moneyformat.format(tongtien).replace(/\D/g, "").replace(/\B(?=(\d{3})+(?!\d))/g, ",");;

                                var money = {};
                                money.tentat_kmtt = element.tentat_kmtt;
                                money.s_tien = tien;
                                money.s_vat = vat;
                                money.s_tongtien = tongtien;

                                this.listmoney.push(money);
                            }
                        });

                        //html+=' <div class="row">  <div class="col-sm-4 col-12"> <div class="info-row"> <div class="key w100">'+element.tentat_kmtt+'</div> </div> </div> <div class="col-sm-4 col-12"> <div class="info-row"> <div class="key w100">Tiền</div>  </div> </div> <div class="col-sm-4 col-12"> <div class="info-row">  <div class="key w100">Vat</div> </div> </div> </div>  <div class="row"><div class="col-sm-4 col-12"><div class="info-row"><div class="value"> <Currency :money="'+s_tongtien+'"/> </div></div></div><div class="col-sm-4 col-12"><div class="info-row"><div class="value"> <Currency :money="'+s_tien+'"/></div></div></div><div class="col-sm-4 col-12"><div class="info-row"><div class="value"> <input v-on:blur="handleBlur"/></div></div></div></div>';
                    });
                    // this.$refs.mainbody.innerHTML = html;
            
                }
            });

            }

            this.loading(false);
        },

        hideModal () {
            this.$refs.modelct_tien_km.hide()

        },
        
        showModal () {
            this.$refs.modelct_tien_km.show();
        },

            handleBlur(e) {

            console.log('blur: ', e.target.value);
            e.target.value = '1234';

            }

        
    }
}
</script>
