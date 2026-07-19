<template>
  <b-modal
    ref="modelBGDoiTDMgWan"
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
          <span class="icon one-notepad"></span> Bảng giá đổi tốc độ MegaWan
        </div>
        <div class="close -ap icon-close" @click="hideModal"></div>
      </div>
      <div class="list-actions-top">
        <ul class="list">
            <li>
                <a href="#" @click.prevent="tsbtnNhapMoi_Click" :class="{disabled: !tsbtnNhapMoi.Enabled}">
                    <span class="icon one-file-plus"></span>Nhập mới
                </a>
            </li>
            <li>
                <a href="#" @click.prevent="tsbtnGhiLai_Click" :class="{disabled: !tsbtnGhiLai.Enabled}">
                    <span class="icon one-save"></span>Ghi lại
                </a>
            </li>
            <li>
                <a href="#" @click.prevent="tsbtnHuyBo_Click" :class="{disabled: !tsbtnHuyBo.Enabled}">
                    <span class="icon nc-icon-glyph ui-1_circle-remove"></span>Huỷ
                </a>
            </li>
            
            <li>
                <a href="#" @click.prevent="tsbtnXoa_Click" :class="{disabled: !tsbtnXoa.Enabled}" >
                    <span class="icon one-trash"></span>Xoá
                </a>
            </li>
             <li>
                <a href="#" @click.prevent="tsbtnChiTietMGW_Click">
                    <span class="icon one-trash"></span>Cước TC
                </a>
            </li>
            <CuocThueCongMGW ref = "popupCuocThueCongMGW"/>
            <li>
                <a href="#" @click.prevent="tsbtnThoat_Click">
                    <span class="icon nc-icon-glyph ui-1_circle-remove"></span>Thoát
                </a>
            </li>
            
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title">Thông tin tốc độ megawan</div>
          <div class="row">
            <div class="col-sm12 col-12">
              <div class="info-row">
                <div class="key w90">Kiểu chuyển đổi:</div>
                    <div class="value">
                        <input type="radio" value="1" v-model="kieu_chuyendoi"/>
                        <label for="one">Nâng tốc độ</label>
                        <input type="radio" value="2" v-model="kieu_chuyendoi"/>
                        <label for="one">Hạ tốc độ</label>
                    </div>
              </div>
              <div class="info-row">
                <div class="key w90">Tốc độ cũ</div>
                <div class="value">
                  <div class="select-custom">
                    <select2 :options="listbox.ds_tocdo_cu.list" v-model="listbox.ds_tocdo_cu.value" v-on:change="SelectList">
                        <option disabled value="0">Select one</option>
                    </select2>
                  </div>
                </div>
                <div class="key w90">Tiền chuyển đổi</div>
                 <div class="value">
                     <input type="text" class="form-control" v-model="txttiencd">
                  </div>
                
              </div>
              <div class="info-row">
                <div class="key w90">Cách chuyển đổi</div>
               <div class="value">
                     <input type="text" class="form-control" v-model="txtcachcd">
                  </div>
              </div>
             
            </div>
            
          </div>
        </div>
        <div class="box-form marb0">
          <div class="legend-title">Thông tin tốc độ mới </div>
          <div class="box-move-select-table" style="height: calc(100% - 40px);">
              <div class="box-col fullh">
                  <div class="table-content" style="height: 100%;">
                      <DataGrid
                          v-bind:columns="listbox.ds_tocdo_dagan.headers"                    
                          v-bind:dataSource="listbox.ds_tocdo_dagan.list"
                          :enable-paging-server="false" ref="DsDagan"  :enabledSelectFirstRow="false"                                     
                          :allowPaging="true"
                          :showFilter="true"
                          > 
                      </DataGrid> 
                  </div>
              </div>
              <div class="actions">
                  <button class="btn" @click="btnNext_Click">
                      <span class="-ap icon-chevron-thin-right"  ></span>
                  </button>
                  <button class="btn" @click="btnNextAll_Click">
                      <span class="fa fa-angle-double-right"></span>
                  </button>
                  <button class="btn" @click="btnPrevious_Click">
                      <span class="-ap icon-chevron-thin-left" ></span>
                  </button>
                  <button class="btn" @click="btnPreviousAll_Click">
                        <span class="fa fa-angle-double-left"></span>
                  </button>
              </div>
              <div class="box-col fullh">
                  <div class="table-content" style="height: 100%;">
                      <DataGrid
                          v-bind:columns="listbox.ds_tocdo_chuagan.headers"                    
                          v-bind:dataSource="listbox.ds_tocdo_chuagan.list"
                          :enable-paging-server="false" ref="DsChuagan"  :enabledSelectFirstRow="false"                                    
                          :allowPaging="true"
                          :showFilter="true"
                          > 
                      </DataGrid> 
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
import CuocThueCongMGW from './CuocThueCongMGW'

export default {
    components: { DatabaseConstants,CuocThueCongMGW },
    name: 'BGDoiTDMgWan',
  
    data(){
        return {
            kieu_chuyendoi:1,
            tsbtnNhapMoi:{Enabled : true},
            tsbtnGhiLai:{Enabled : true},
            tsbtnXoa:{Enabled : true},
            tsbtnHuyBo:{Enabled : true},
            txtcachcd:"",
            txttiencd:"",
            
            tocdo_megawan_info:{},
            listbox:{
                ds_tocdo_cu:{
                    list:[],
                    value:""
                },
                ds_tocdo_chuagan:{
                    list:[],
                      headers:[{fieldName:'toc_do',headerText:'Tốc độ mới chưa gán',allowfiltering: true}]
                },
                ds_tocdo_dagan:{
                    list:[],
                      headers:[{fieldName:'toc_do',headerText:'Tốc độ mới đã gán',allowfiltering: true}]
                }
            }
        }
    },
    methods: {
        handleShowModal: async function () {
            this.loading(true);


            // var data={
            //         "command":"select tocdo_id,tocdo||' '||donvi toc_do from "+DatabaseConstants.DB2+".tocdo_kenh order by tocdo"
            //     };

          await API.sp_lay_tocdo(this.axios,null).then(response=>{
            if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') 
                this.listbox.ds_tocdo_cu.list = response.data.data.map(x=>{return {id:x['tocdo_id'],text:x['toc_do']}});
            else
                this.listbox.ds_tocdo_cu.list = [];
            });

            
           

            //this.SelectList();
            //this.SetButton(0);

            this.loading(false);
        
        },
        hideModal () {
        this.$refs.modelBGDoiTDMgWan.hide()
        },
        
        showModal () {
            
            this.$refs.modelBGDoiTDMgWan.show()
        },

        SelectList:async function(){
          this.loading(true);


           var data = {
            "tocdo_id":this.listbox.ds_tocdo_cu.value,
            "kieu":this.kieu_chuyendoi,
            "loai":0
          };
          await API.lay_ds_tocdo_mgwan(this.axios,data).then(response=>{
            if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') 
                this.listbox.ds_tocdo_chuagan.list = response.data.data;
            else
                this.listbox.ds_tocdo_chuagan.list = [];
          });

          data = {
            "tocdo_id":this.listbox.ds_tocdo_cu.value,
            "kieu":this.kieu_chuyendoi,
            "loai":1
          };
          await API.lay_ds_tocdo_mgwan(this.axios,data).then(response=>{
            if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
                this.listbox.ds_tocdo_dagan.list = response.data.data;
                this.txtcachcd = response.data.data[0].cachchuyendoi;
                this.txttiencd = response.data.data[0].tien_cd;

            }
            else
                this.listbox.ds_tocdo_dagan.list = [];
          });
          
          this.loading(false);
            
        },

        SetButton(kieu)
        {
            this.tsbtnNhapMoi.Enabled = false;
            this.tsbtnGhiLai.Enabled = false;
            this.tsbtnXoa.Enabled = false;
            this.tsbtnHuyBo.Enabled = false;

            if (kieu == -1)//Bat dau
            {
                this.tsbtnGhiLai.Enabled = true;
                this.tsbtnHuyBo.Enabled = true;
            }

            if (kieu == 0)//Bat dau
            {
                this.tsbtnNhapMoi.Enabled = true;
                this.tocdo_megawan_info = {};
            }

            if (kieu == 1)//Them moi
            {
                this.tsbtnGhiLai.Enabled = true;
                this.tsbtnHuyBo.Enabled = true;
                this.tocdo_megawan_info = {};
            }

            if (kieu == 2)//Huy
            {
                this.tsbtnNhapMoi.Enabled = true;
                this.tsbtnXoa.Enabled = true;
                this.tocdo_megawan_info = {};
            }

            if (kieu == 3)//Edit
            {
                this.tsbtnNhapMoi.Enabled = true;
                this.tsbtnGhiLai.Enabled = true;
                this.tsbtnHuyBo.Enabled = true;
                this.tsbtnXoa.Enabled = true;
            }
            if (kieu == 4)//Edit
            {
                this.tsbtnNhapMoi.Enabled = false;
                this.tsbtnGhiLai.Enabled = false;
                this.tsbtnHuyBo.Enabled = false;
            }
        },

     

        btnPrevious_Click:function(){
          var grid_chuagan = this.$refs.DsChuagan;
          //remove selected item ds_tocdo_chuagan ds_tocdo_dagan
          grid_chuagan.getSelectedRecords().forEach(obj => {
            this.listbox.ds_tocdo_chuagan.list = this.listbox.ds_tocdo_chuagan.list.filter(element => element !== obj);
            this.listbox.ds_tocdo_dagan.list.push(obj);
          });
          
        },

        btnNext_Click:function(){
          var grid_dagan = this.$refs.DsDagan;
          //remove selected item
          grid_dagan.getSelectedRecords().forEach(obj => {
            this.listbox.ds_tocdo_dagan.list = this.listbox.ds_tocdo_dagan.list.filter(element => element !== obj);
            this.listbox.ds_tocdo_chuagan.list.push(obj);
          });

        },

        btnPreviousAll_Click:function(){
          
          this.listbox.ds_tocdo_dagan.list = Object.assign([],this.listbox.ds_tocdo_chuagan.list);
          this.listbox.ds_tocdo_chuagan.list = [];
          
        },

        btnNextAll_Click:function(){
          this.listbox.ds_tocdo_chuagan.list = Object.assign([],this.listbox.ds_tocdo_dagan.list);
          this.listbox.ds_tocdo_dagan.list = [];
        },


        tsbtnNhapMoi_Click: function()
        {
            //this.SetButton(1);
        },

        tsbtnHuyBo_Click: function()
        {
            //this.SetButton(0);
        },

        tsbtnGhiLai_Click: async function()
        {
          var ds_BG_DOI_TD_MGWAN = [];

          this.listbox.ds_tocdo_dagan.list.forEach(item=>{
            var rowbgdoitd = {};
            rowbgdoitd.cachchuyendoi = this.txtcachcd;
            rowbgdoitd.td_cu_bd = 0;//ko dung tai hgg
            rowbgdoitd.td_cu_kt = 0;//ko dung tai hgg
            rowbgdoitd.td_moi_bd = 0;//ko dung tai hgg
            rowbgdoitd.td_moi_kt = 0;//ko dung tai hgg
            rowbgdoitd.tocdocu_id = this.listbox.ds_tocdo_cu.value;
            rowbgdoitd.tocdomoi_id = item.tocdo_id;
            rowbgdoitd.tien_cd = this.txttiencd;
            ds_BG_DOI_TD_MGWAN.push(rowbgdoitd);
          });
          var data={
                      "action": 1,
                      "ds_bgdoitdmgwan":JSON.stringify(ds_BG_DOI_TD_MGWAN)
                    }

          console.log(data);

          await API.sp_bgdoimgwan_capnhat(this.axios,data).then(response=>{
            if(response.data.error_code === 'BSS-00000000') 
              this.$toast.success(response.data.message);
            else
              this.$toast.error(response.data.message);
          this.SelectList();
      });

          
        },

        tsbtnXoa_Click: async function()
        {
          if (this.listbox.ds_tocdo_dagan.list.length == 0){
            this.$toast.error("Chưa có dữ liệu để xóa!");
            return;
          }

          this.$confirm('Bạn thật sự muốn xóa dữ liệu không ?', 'Thông báo', {
            confirmButtonText: 'Có',
            cancelButtonText: 'Không'
          }).then(async () => {
          this.listbox.ds_tocdo_dagan.list.forEach(item=>{
              var data = {
                            "tocdocu_id": this.listbox.ds_tocdo_cu.value,
                            "tocdomoi_id": item.tocdo_id
                          };
              API.sp_bgdoimgwan_xoa(this.axios,data).then(response=>{});

            });
            this.SelectList();
            this.$toast.success("Xóa dữ liệu thành công!");
          })

          
        },

        tsbtnChiTietMGW_Click:function(){
          this.$refs.popupCuocThueCongMGW.showModal();
        },

        tsbtnThoat_Click:function(){
          this.$refs.modelBGDoiTDMgWan.hide()
        }
        
    }
}
</script>
