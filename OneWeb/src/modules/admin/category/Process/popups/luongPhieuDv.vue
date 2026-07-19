<template>
   <b-modal
    id="luongPhieuDv"
    title="Luồng phiếu - đơn vị"
    modal-class="luong-phieu-don-vi"
    header-class="popup-header"
    content-class="luong-phieu-don-vi-content"
    body-class="luong-phieu-don-vi-body"
    size="xl"
    hide-footer
  >
    <div class="list-actions-top">
        <ul class="list">
            <li @click="tsbtnGhiLai_Click">
                <a href="javascript:void(0)">
                    <span class="icon one-save"></span> Ghi lại
                </a>
            </li>
    
        </ul>
    
    </div>
    <div class="popup-body">
        <div class="box-form">
            <div class="info-row">
                <div class="key w60">Loại ĐV</div>
                <div class="value">
                    <div class="select-custom">
                        <Select2 ref="loaiDonVi" :disabled='false' v-model="cboLoaiDv.value"
                             :options="cboLoaiDv.list" :settings="settingSelect2" @select="cboLoaiDV_SelectedValueChanged"/>
                    </div>
                </div>
            </div>
            <div class="info-row">
                <div class="key w60">Tỉnh</div>
                <div class="value">
                    <div class="select-custom">
                        <Select2 ref="danhSachTinh" :disabled='false' v-model="cboDanhSachTinh.value"
                      :options="cboDanhSachTinh.list" :settings="settingSelect2" @select="changeTinh"/>
                    </div>
                </div>
            </div>
            <div class="box-move-select-table">
                <div class="box-col">
                    <div class="table-content">
                        <DataGrid
                            v-bind:columns="donViDaGan.header"                    
                            v-bind:dataSource="donViDaGan.list"
                            :enable-paging-server="false"                                        
                            :allowPaging="true"
                            :showFilter="true"
                            :showColumnCheckbox="true"
                            ref="DonViDaGan"
                            >
                        </DataGrid>
                    </div>
                </div>
                <div class="actions">
                    <button class="btn sm" @click="btnHDRight">
                        <span class="fa fa-angle-right"></span>
                    </button>
                    <button class="btn sm" @click="btnHDRightAll">
                        <span class="fa fa-angle-double-right"></span>
                    </button>
                    <button class="btn sm" @click="btnHDLeft">
                        <span class="fa fa-angle-left"></span>
                    </button>
                    <button class="btn sm" @click="btnHDLeftAll">
                        <span class="fa fa-angle-double-left"></span>
                    </button>
                </div>
                <div class="box-col">
                    <div class="table-content">
                       <DataGrid
                            v-bind:columns="donViChuaGan.header"                    
                            v-bind:dataSource="donViChuaGan.list"
                            :enable-paging-server="false"                                        
                            :allowPaging="true"
                            :showFilter="true"
                            :showColumnCheckbox="true"
                            ref="DonViChuaGan"
                            >
                        </DataGrid>
                    </div>
                </div>
            </div>
        </div>
    </div>
  </b-modal>
</template>
<script>
import moment from "moment";
import Vue from 'vue'
import API from '../API'
//CSS
import '@/assets/vendor/bootstrap-table/bootstrap-table.min.css';
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.css';
//JS
import '@/assets/vendor/jquery/split'
import '@/assets/vendor/bootstrap-table/bootstrap-table.min'
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.min'
import gridView from '@/components/Shared/gridview.vue'
export default {
  props: ['luongId', 'idx'],
  components: {
    API,
    gridView
  },
  name: 'luongPhieuDv',
  mounted() {
    this.frmLuongPhieuDV_Load()
    this.getDanhSachTinh()
  },
  data() {
    return {
      luong_dv : [],
      dsLUONGDV : [],
      loaidv_id : 0,
      idx_data : 0,
      luong_id_data : 0,
      load : true,
      settingSelect2: {
        language: 'vi'
      },
      cboLoaiDv : {
        list : [],
        value : 0
      },
      donViDaGan: {
        list: [],
        header: [ {fieldName: 'TEN_DV', headerText: 'Đơn vị đã gán', allowFiltering: true, width: 'auto'},],
        value: {},
        isEnabled: true,
      },
      donViChuaGan: {
        list: [],
        header: [ {fieldName: 'TEN_DV', headerText: 'Đơn vị chưa gán', allowFiltering: true, width: 'auto'},],
        value: {},
        isEnabled: true,
      },
      cboDanhSachTinh : {
        list : [],
        value : "",
        disable :false
      },
    }
  },
  watch: {
    luongId(val) {
        this.luong_id_data = val
    },
    idx(val) {
        this.idx_data = val
    }
  },
  methods: {
    show() {
      this.$bvModal.show("luongPhieuDv");
      this.frmLuongPhieuDV_Load()
      this.getDanhSachTinh()
    },
    ShowAlert(mesage)
    {
      this.$toast.error(mesage);
    },
    showSuccess(mesage) {
      this.$toast.success(mesage);
    },
    GetData: function (response) {
      if (response.data.error === 200 || response.data.error === '200') {
        return response.data.data
      } else {
        console.log(response.data.error_code)
      }
      return []
    },
    async frmLuongPhieuDV_Load() {
        //bangts.HT_Loai_DVI_Combobox(cboLoaiDV);
        let input = {}
        let loaiDv = this.GetData(await API.ADMIN_LOAI_DVI(this.axios, input))
        if (loaiDv && loaiDv.length > 0) {
            this.cboLoaiDv.list = loaiDv.map(item => {
                return {
                    id : item.LOAIDV_ID,
                    text : item.TEN_LOAIDV
                }
            })
            this.cboLoaiDv.value = this.idx_data
        }
        console.log('loai dv is ', loaiDv)
        //cboLoaiDV.SelectedValue = idx;
        this.load = false;
        this.cboLoaiDV_SelectedValueChanged()
    },

    async cboLoaiDV_SelectedValueChanged() {
        // if (this.cboLoaiDv.list.length <= 0) return;
        if (this.load) return;
        let loaidv_id = this.cboLoaiDv.value
        let obj = {
            // LUONG_ID : this.luong_id,
            // LOAIDV_ID : this.cboLoaiDv.value
            LUONG_ID : this.luong_id_data,
            LOAIDV_ID : this.cboLoaiDv.value
        }
        let input = {
            "p_ds_para"  : JSON.stringify(obj),
            "phanvung_id" : this.cboDanhSachTinh.value
        }
        let data = this.GetData(await API.fn_lay_dvi_theo_luong_dv(this.axios, input))
        data = JSON.parse(data)
        if (data && data.ERROR_CODE == 1) {
            this.donViChuaGan.list = data.RESULT.LDV_CHUAGAN ? data.RESULT.LDV_CHUAGAN : []
            this.donViDaGan.list =  data.RESULT.LDV_DAGAN ? data.RESULT.LDV_DAGAN : []
        }
    },
    async getDanhSachTinh(){
      let input = {}
      let data = this.GetData(await API.CSS_TINH(this.axios, input))
      console.log('data is ', data)
      if (data.length > 0) {
        this.cboDanhSachTinh.list = data.map(item => {
          return {
            id : item.TINH_ID,
            text : item.TENTINH
          }
        })
        this.cboDanhSachTinh.value = this.$root.token.getPhanVungID();
        // this.cboDanhSachGanTinh.list = this.cboDanhSachTinh.list
      }

    },
    changeTinh() {
      console.log('run in this')
    },
    async tsbtnGhiLai_Click() {
        let luong_dv = []
        if (this.donViDaGan.list.length > 0) {
            this.donViDaGan.list.map(item => {
                luong_dv.push({
                    'LUONG_ID' : this.luong_id_data,
                    'DONVI_ID' : item.DONVI_ID
                })
            })
        }
        let obj = {
            'LUONG_ID' : this.luong_id_data,
            'LUONG_DV' : luong_dv
        }
        let input = {
            'p_ds_para' : JSON.stringify(obj)
        }

        let data = this.GetData(await API.fn_ins_luong_dv(this.axios, input))
        data= JSON.parse(data)
        if (data) {
            if (data.ERROR_CODE) {
                this.showSuccess('Cập nhật dữ liệu đơn vị cho luồng phiếu thành công !')
            }
        }
    },
    btnHDRight()
    {
      // var items = this.listTable.hop_dong_thu_tien.list.filter(a => a.ischecked);
      let items = this.$refs.DonViDaGan.getSelectedRecords();
      if (items.length > 0) {
        for (var item of items) {
          this.donViChuaGan.list.push(
            {
              'DONVI_ID': item.DONVI_ID, 
              'TEN_DV': item.TEN_DV,
            }
          );
        }
      }
      var temps = this.donViDaGan.list.filter(a => !items.includes(a));
      this.donViDaGan.list = [];
      this.donViDaGan.list = temps;
    },

    btnHDRightAll()
    {
      if (this.donViDaGan.list.length > 0) {
        for (var item of this.donViDaGan.list) {
          this.donViChuaGan.list.push(
            {
              'DONVI_ID': item.DONVI_ID, 
              'TEN_DV': item.TEN_DV,
            }
          );
        }
        this.donViDaGan.list = [];
      }
    },

    btnHDLeft()
    {
      // var items = this.listTable.hop_dong_chua_tra_tien.list.filter(a => a.ischecked);
      let items = this.$refs.DonViChuaGan.getSelectedRecords();
      if (items.length > 0) {
        for (var item of items) {
          this.donViDaGan.list.push(
            {
              'DONVI_ID': item.DONVI_ID, 
              'TEN_DV': item.TEN_DV,
            }
          );
        }
      }
      var temps = this.donViChuaGan.list.filter(a => !items.includes(a));
      this.donViChuaGan.list = [];
      this.donViChuaGan.list = temps;
    },

    btnHDLeftAll()
    {
      if (this.donViChuaGan.list.length > 0) {
        for (var item of this.donViChuaGan.list) {
          this.donViDaGan.list.push(
            {
              'DONVI_ID': item.DONVI_ID, 
              'TEN_DV': item.TEN_DV,
            }
          );
        }
        this.donViChuaGan.list = [];
      }
    },

  }
}
</script>
