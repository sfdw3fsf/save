<template>
  <div id="hoanthanh_capthietbi" >
    <div class="list-actions-top">
      <ul class="list">
        <li>
          <a href="#" @click.prevent="hoanthanhClick"> <span class="icon one-file-check"></span>Hoàn thành</a>
        </li>
      </ul>
    </div>
    <div class="page-content">
      <div class="box-form">
        <div class="legend-title">Thông tin thuê bao</div>
        <div class="row">
          <!-- cột 1 -->
          <div class="col-sm-6 col-12">
            <div class="row">
              <div class="col-sm-12 col-12">
                <div class="row">
                  <div class="col-sm-6 col-12">
                      <div class="info-row">
                          <div class="key w110">Phiếu xuất kho</div>
                          <div class="value">
                            <div class="input-more-button">
                              <button class="btn" @click="btnClick_layphieu">
                                <span class="-ap icon-more_horiz"></span>
                              </button>
                              <input type="text" ref="txtMaGD" v-model="phieu_xuatkho" class="form-control highlight fw6"
                                    v-on:keyup.enter="phieu_xuatkho_KeyPress" :disabled="true" >
                            </div>
                          </div>
                      </div>
                  </div>
                  <div class="col-sm-6 col-12">
                      <div class="info-row">
                          <div class="key w100">Số thuê bao</div>
                          <div class="value">
                              <input type="text" class="form-control" v-model="so_tb" :disabled="true" />
                          </div>
                      </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <!-- cột 2 -->
          <div class="col-sm-6 col-12">
            <div class="row">
              <div class="col-sm-12 col-12"> 
                <div class="info-row">
                    <div class="key w80">Tên KH</div>
                    <div class="value">
                        <input type="text" class="form-control" v-model="ten_kh" :disabled="true"  />
                    </div>
                </div>
              </div>
            </div>
          </div>
          </div>
        </div>
      </div>
      <!-- <div class="box-form">
      <div class="legend-title" style="color: rgb(0, 0, 0);">Thông tin thuê bao của KH</div>
      <div class="row">
        <div class="clearfix"></div>
        <div>
          <k-table-custom :dataSources="dataSources_hoanthanh" :columns="columns" :allowFilter="true" :allowCheckBox="true"
             :rowSelectedIndex="1" @onSelectedRow="onSelectedRow"  @dataCheckeds="CheckBox"/>
        </div>
      </div>
    </div> -->
      <b-modal id="popupComponent" size="huge" :title="popupComponentHeader"
        :hide-header="popupComponentHeader?false:true" hide-footer modal-class="modal-custom" body-class="modal-body p-0">
        <component :is="popupComponent" ref="generalPopup" :dulieu="popupComponentData" v-bind="popupComponentAttr"
        isPopup="true" v-on="popupComponentEvts" @form-close="popupClosed"></component>
      </b-modal>
    </div>
  </div>
</template>
<style>
</style>
<script>
import api from '../api.js'
import KTableCustom from "../components/KTableCustom.vue"

export default {
name:'hoanthanh_capthietbi',
components:{
  KTableCustom
},
props: ["dulieu"],
data(){
    return{
    popup_1:{
        email:'',
        fullname:''
    },  
    row_tracuuchungtu:[],
    phieu_xuatkho:"",
    so_tb:"",
    ten_kh:"", 
    popupComponentName: '',
    popupComponentHeader: '',
    popupComponent: null,
    popupComponentData: null,
    popupComponentAttr: {},
    popupComponentEvts: {},
    dataSources_hoanthanh:[],
    columns:[
        {
          field: 'serial',
          label: 'Phiếu xuất kho',
          allowEdit: false,
          allowFilter: true,
          allowEditDate: false,
        },
        {
          field: 'ten_tb',
          label: 'Loại TB được cấp',
          allowEdit: false,
          allowFilter: true,
          allowEditDate: false,
        },
        {
          field: 'ma_tb',
          label: 'Serial thiết bị',
          allowEdit: false,
          allowFilter: true,
          allowEditDate: false,
        },
        {
          field: 'loai_tb',
          label: 'Chủng loại',
          allowEdit: false,
          allowFilter: true,
          allowEditDate: false,
        },
        {
          field: 'loai_cts',
          label: 'IMEI',
          allowEdit: false,
          allowFilter: true,
          allowEditDate: false,
        },
        {
          field: 'diachi_tb',
          label: 'Đơn giá',
          allowEdit: false,
          allowFilter: true,
          allowEditDate: false,
        },
        {
          field: 'so_gt',
          label: 'Ngày cấp mới',
          allowEdit: false,
          allowFilter: true,
          allowEditDate: false,
        },
        {
          field: 'so_dt',
          label: 'Ngầy đổi bảo hành',
          allowEdit: false,
          allowFilter: true,
          allowEditDate: false,
        },
    ],

    }
},
mounted() {
    if(this.dulieu.thongtin_tb.length != []){
      this.ten_kh=this.dulieu.thongtin_tb.ten_kh
      this.so_tb= this.dulieu.thongtin_tb.ma_tb
    }
},
methods:{
//   async initDulieu(){
//     console.log('Dữ liệu popup nhận được:',this.dulieu)
//     this.popup_1.email =  this.dulieu.email
//     this.popup_1.fullname = this.dulieu.fullname
//   },
  async hoanthanhClick(){
    if(this.row_tracuuchungtu.length <=0){
      this.$toast.error('Bạn chưa chọn phiếu xuất kho')
      return
    }
    if(this.dulieu.thongtin_tb.length <=0){
      this.$toast.error('Bạn chưa tìm kiếm số thuê bao')
      return
    }
    if(this.dulieu.rowclick.length <=0){
      this.$toast.error('Vui lòng quay lại và nhập mới')
      return
    }
    var ds=[{
      DSTB_ID:this.dulieu.thongtin_tb.dstb_id,
      TBI_TB_ID:this.dulieu.rowclick.tbi_tb_id,
      CHUNGTU_ID:this.row_tracuuchungtu.CHUNGTU_ID,
      CTCT_ID:this.row_tracuuchungtu.CTCT_ID,
      THIETBI_ID:this.row_tracuuchungtu.THIETBI_ID,
      MA_TB:this.dulieu.thongtin_tb.ma_tb,
    }]
    var input= {
      vkiu:0,
      vds:JSON.stringify(ds)
    }
    console.log("input hoàn thiện", input )
    const res = await api.sp_hoanthanh_thietbi_thuebao_dd(this.axios, input)
    console.log("sp_tt_thietbi_thuebao_dd_theo_matb",res)
    if(res.data.error_code === 'BSS-00000000'){
     this.$toast.success('Hoàn thành thành công!')
    }
    else{
      this.$toast.warning(res.data.message_detail.split('\n')[0].split(': ')[1])
    }
  },
  btnClick_layphieu(){
    this.popupComponent = () =>
       import(
         "./TraCuuChungTu.vue"
        );
      this.popupComponentName = "tracuuchungtu";
     this.popupComponentHeader = 'Tra cứu chứng từ';
     this.popupComponentData = {
       isPopup: true
      }; 
      this.Popup("popupComponent");
  },

  onSelectedRow (args){
    console.log("Tú kiểm tra đây là hàng được chọn", args)
  },
  CheckBox(args){
    console.log("đã nhấn check box hàng", args)
  },
  Popup(modalId) {
      this.$bvModal.show(modalId);
    },
  async ClosePopup(modalId) {
      this.$bvModal.hide(modalId);
      this.popupComponent = null;
    },
  async popupClosed(val) {
    if (!val) {
      this.ClosePopup('popupComponent')
      return
    }
    this.loading(true);
    try {
      switch (this.popupComponentName) {
        case "tracuuchungtu": 
          console.log("data",val);
          this.row_tracuuchungtu=val
           this.phieu_xuatkho=val.SOPHIEU
        break;
      }
    } catch (ex) {
      if (ex.data && ex.data.message_detail) {
        this.$toast.error("" + ex.data.message_detail);
      } else {
        this.$toast.error("" + ex);
      }
    } finally {
      this.ClosePopup("popupComponent");
      this.loading(false);
    }
  },
  async phieu_xuatkho_KeyPress(){
  },
  huy(){
        this.$emit('form-close', false);
    },
  clearAll(){
    },
}
}
</script>