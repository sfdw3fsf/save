<template>
  <div class="box-form">
    <div class="legend-title">Thông tin khách hàng</div>
    <div class="row">
      <div class="col-sm-6 col-12">
        <div class="row">
          <div class="col-sm-7 col-12">
            <div class="info-row">
              <div class="key w120">Mã hợp đồng</div>
              <div class="value">                
                <input type="text" class="form-control" v-model="formProps.ma_hd" readonly>
              </div>
            </div>
          </div>
          <div class="col-sm-5 col-12">
            <div class="info-row">
              <div class="key w90">Ngày lập HĐ</div>
              <div class="value">
                <div class="input-icon-right">                  
                  
                  <k-date-picker ref="ngaylap_hd" format="DD/MM/YYYY" type="datetime" v-model="formProps.ngaylap_hd" class="disabled"/>                  
                  
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-7 col-12">
            <div class="info-row">
              <div class="key w120">Mã KH (*)</div>
              <div class="value">
                <div class="input-more-button">
                  <button class="btn" @click="SearchDanhBa">
                    <span class="-ap icon-more_horiz"></span>
                  </button>                  
                  <input type="text" @keyup.enter="TimThongTinKH"  class="form-control highlight" v-model="formProps.ma_kh">
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="info-row">
          <div class="key w120">Tên khách hàng (*)</div>
          <div class="value">            
            <input type="text" class="form-control" v-model="formProps.ten_kh">
          </div>
        </div>
        <div class="row">
          <div class="col-sm-7 col-12">
            <div class="info-row">
              <div class="key w120">Địa chỉ KH (*)</div>
              <div class="value">
                <div class="input-more-button">
                  <button class="btn"  v-b-modal.popupDiaChi>
                    <span class="-ap icon-more_horiz"></span>
                  </button>                  
                  <input type="text" class="form-control" v-model="formProps.diachi_kh" readonly>
                </div>
              </div>
            </div>
          </div>
          <div class="col-sm-5 col-12">
            <div class="info-row">
              <div class="key w90">Điểm TN</div>
              <div class="value">
                <input type="text" class="form-control" v-model="formProps.diem_tn">
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-7 col-12">
            <div class="info-row">
              <div class="key w120">Loại GT (*)</div>
              <div class="value">
                <div class="select-custom">    
                    <SelectExt placeholder="Tìm kiếm"  v-model="formProps.loaigt_id" :allowFiltering="true" dataTextField="LOAI_GT"
                      dataValueField="LOAIGT_ID" :dataSource="optionProps.cbx_dsloaigt" @change="selectLoaigt($event)"></SelectExt>                                                                      
                </div>
              </div>
            </div>
          </div>
          <div class="col-sm-5 col-12">
            <div class="info-row">
              <div class="key w90">Số GT (*)</div>
              <div class="value">                
                <input type="text" class="form-control" v-model="formProps.so_gt">
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-7 col-12">
            <div class="info-row">
              <div class="key w120">Ngày cấp (*)</div>
              <div class="value">
                <k-date-picker ref="ngaycap" format="DD/MM/YYYY" type="datetime" v-model="formProps.ngaycap"/>                  
              </div>
            </div>
          </div>
          <div class="col-sm-5 col-12">
            <div class="info-row">
              <div class="key w90">Nơi cấp</div>
              <div class="value">                
                <input type="text" class="form-control" v-model="formProps.noicap">
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-7 col-12">
            <div class="info-row">
              <div class="key w120">Ngày hết hạn</div>
              <div class="value">                
                <k-date-picker ref="ngayhethan_gt" format="DD/MM/YYYY" type="datetime" v-model="formProps.ngayhethan_gt"/>
              </div>
            </div>
          </div>
          <div class="col-sm-5 col-12">
            <div class="info-row">
              <div class="key w90">Ngày sinh(*)</div>
              <div class="value">
                <k-date-picker ref="ngay_sn" format="DD/MM/YYYY" type="datetime" v-model="formProps.ngay_sn"/>
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-7 col-12">
            <div class="info-row">
              <div class="key w120">Giới tính</div>
              <div class="value">
                <div class="list-checks mart7">
                  <div class="item">
                    <div class="check-action">
                      <input type="radio" class="check" value="2" v-model="formProps.gioitinh" />
                      <span class="name">Không</span>
                    </div>
                  </div>
                  <div class="item">
                    <div class="check-action">
                      <input type="radio" class="check"  value="0" v-model="formProps.gioitinh" />
                      <span class="name">Nam</span>
                    </div>
                  </div>
                  <div class="item">
                    <div class="check-action">
                      <input type="radio" class="check"  value="1" v-model="formProps.gioitinh" />
                      <span class="name">Nữ</span>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="col-sm-5 col-12">
            <div class="info-row">
              <div class="key w90">Quốc tịch</div>
              <div class="value w20 padt7">
                <div class="check-action">
                  <input type="checkbox" class="check" v-model="optionProps.chkquoctich" />
                  <span class="name"></span>
                </div>
              </div>
              <div class="value">
                <div class="select-custom">
                    <SelectExt placeholder="Tìm kiếm" :disabled="!optionProps.chkquoctich"  v-model="formProps.quoctich_id" :allowFiltering="true" dataTextField="tenquoctich"
                      dataValueField="quoctich_id" :dataSource="optionProps.cbx_quoctich" @change="selectQuoctich($event)"></SelectExt>                                                        

                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-7 col-12">
            <div class="info-row">
              <div class="key w120">Ngành nghề</div>
              <div class="value">
                <div class="input-more-button">
                  <button class="btn"  @click="popupTieuNganh">
                    <span class="-ap icon-more_horiz"></span>
                  </button>
                  <div class="select-custom">                    
                    <SelectExt placeholder="Tìm kiếm"  v-model="formProps.nganhnghe_id" :allowFiltering="true" dataTextField="tennganhnghe"
                      dataValueField="nganhnghe_id" :dataSource="optionProps.cbx_nganhnghe" @change="selectNganhnghe($event)"></SelectExt>                                                        

                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="col-sm-5 col-12">
            <div class="info-row">
              <div class="key w90">Dân tộc</div>
              <div class="value w20 padt7">
                <div class="check-action">
                  <input type="checkbox" class="check" v-model="optionProps.chkdantoc"/>
                  <span class="name"></span>
                </div>
              </div>
              <div class="value">
                <div class="select-custom">
                    <SelectExt 
                        placeholder="Tìm kiếm"  
                        v-model="formProps.dantoc_id" 
                        :disabled="!optionProps.chkdantoc" 
                        :allowFiltering="true" 
                        dataTextField="tendantoc"
                        dataValueField="dantoc_id" 
                        :dataSource="optionProps.cbx_dantoc" 
                        @change="selectDantoc($event)">
                    </SelectExt>                                                        
                    
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="col-sm-6 col-12">
        <div class="row">
          <div class="col-sm-7 col-12">
            <div class="info-row">
              <div class="key w120">Loại KH</div>
              <div class="value">
                <div class="input-more-button">
                  <button class="btn" @click="btnLoaiKH_Click">
                    <span class="-ap icon-more_horiz"></span>
                  </button>
                  <div class="select-custom">
                      <ComboboxGrid ref="cboGridLoaiKH" v-bind:columns="[
                        {fieldName: 'kieu_kh', headerText: 'Kiểu KH', allowFiltering: true, isGroupedColumn:true},
                        {fieldName: 'ten_nhom', headerText: 'Nhóm LKH', allowFiltering: true, isGroupedColumn:true},
                        {fieldName: 'ma_loaikh', width:'70px', headerText: 'Mã loại KH', allowFiltering: true},
                        {fieldName: 'ten_loaikh', headerText: 'Tên loại KH', allowFiltering: true}
                        ]"
                                  v-bind:dataSource="optionProps.cbx_loaikh"       
                                  v-model="formProps.loaikh_id"
                                  :value="formProps.loaikh_id"                     
                                  textField="ten_loaikh"
                                  valueField="loaikh_id"                                  
                                  >
                    </ComboboxGrid>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="col-sm-5 col-12">
            <div class="info-row">
              <div class="key w90">PL KH</div>
              <div class="value">
                <input type="text" class="form-control" v-model="formProps.phanloai_kh" readonly>
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-7 col-12">
            <div class="info-row">
              <div class="key w120">Người đại diện</div>
              <div class="value">
                <input type="text" class="form-control" v-model="formProps.nguoi_dd">
              </div>
            </div>
          </div>
          <div class="col-sm-5 col-12">
            <div class="info-row">
              <div class="key w90">Chức vụ</div>
              <div class="value">
                <input type="text" class="form-control" v-model="formProps.chucdanh">
              </div>
            </div>
          </div>
        </div>
        <div class="info-row">
          <div class="key w120">Địa chỉ NĐD</div>
          <div class="value">
            <div class="input-more-button">
              <button class="btn"  v-b-modal.popupDiaChi_NDD>
                <span class="-ap icon-more_horiz"></span>
              </button>
              <input type="text" class="form-control" v-model="formProps.diachi_ndd"  readonly>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-7 col-12">
            <div class="info-row">
              <div class="key w120">Điện thoại</div>
              <div class="value">
                <input type="text" class="form-control" v-model="formProps.so_dt">
              </div>
            </div>
          </div>
          <div class="col-sm-5 col-12">
            <div class="info-row">
              <div class="key w90">Fax</div>
              <div class="value">
                <input type="text" class="form-control" v-model="formProps.so_fax">
              </div>
            </div>
          </div>
        </div>
        <div class="info-row">
          <div class="key w120">Hộ khẩu</div>
          <div class="value">
            <div class="input-more-button">
              <button class="btn">
                <span class="-ap icon-more_horiz"></span>
              </button>
              <input type="text" class="form-control" v-model="formProps.hokhau">
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-7 col-12">
            <div class="info-row">
              <div class="key w120">Nơi cấp HK</div>
              <div class="value">
                <input type="text" class="form-control" v-model="formProps.noicap_hk">

              </div>
            </div>
          </div>
          <div class="col-sm-5 col-12">
            <div class="info-row">
              <div class="key w90">Ngày cấp HK</div>
              <div class="value">
                <k-date-picker ref="ngay_hk" format="DD/MM/YYYY" type="datetime" v-model="formProps.ngay_hk"/>
              </div>
            </div>
          </div>
        </div>
        <div class="info-row">
          <div class="key w120">Ngân hàng</div>
          <div class="value w20 padt7">
            <div class="check-action">
              <input type="checkbox" class="check"  v-model="optionProps.chknganhang_kh"/>
              <span class="name"></span>
            </div>
          </div>
          <div class="value">
            <div class="select-custom">              
                <SelectExt placeholder="Tìm kiếm" :disabled="!optionProps.chknganhang_kh"  v-model="formProps.nganhang_id" :allowFiltering="true" dataTextField="ten_nh"
                      dataValueField="nganhang_id" :dataSource="optionProps.cbx_nganhang" @change="selectNganhang($event)"></SelectExt>                                    
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-7 col-12">
            <div class="info-row">
              <div class="key w120">Mã số thuế</div>
              <div class="value">
                <input type="text" class="form-control" v-model="formProps.mst">
              </div>
            </div>
          </div>
          <div class="col-sm-5 col-12">
            <div class="info-row">
              <div class="key w90">Tài khoản</div>
              <div class="value">
                <input type="text" class="form-control" v-model="formProps.stk">
              </div>
            </div>
          </div>
        </div>
        <div class="info-row">
          <div class="key w120">Đặc trưng</div>
          <div class="value">
            <input type="text" class="form-control" v-model="formProps.dactrung">
          </div>
        </div>
      </div>
    </div>
    <div class="info-row">
      <div class="key w120">Ghi chú</div>
      <div class="value">        
        <input type="text" class="form-control" v-model="formProps.ghichu">
      </div>
    </div>
    <ModalChonDiaChi modalId="popupDiaChi" :data="formProps.d_diachi_kh" @xacnhan="getDiaChi"/>
    <ModalChonDiaChi modalId="popupDiaChi_NDD" :data="formProps.d_diachi_ndd" @xacnhan="getDiaChi_ndd"/>
    <LoaiKH id="popupLoaiKH"  :objLoaiKH="this.objLoaiKH" @getLoaiKHID="getLoaiKHID"></LoaiKH>
    <ejs-dialog :enableResize="true" :visible="false" :position="position" ref="searchAccountDialog" :header="'Tra cứu danh bạ'" showCloseIcon="true" target="#app" width="80%" height="800" top="100" class="searchAccount-popup-dlg">
      <div class="searchAccount-popup popupBody">
        <searchAccount :isPopup="true" @form-close="searchAccountDialogClose" />
      </div>
    </ejs-dialog>
    
    <b-modal id="modal-tieunganh" modal-class="modal-tieunganh" hide-footer hide-header no-close-on-backdrop>
        <popupTieuNganh 
            :dulieu="{vnganhnghe_id:formProps.nganhnghe_id,tieunganh:formProps.d_tieunganh}"  
              @form-close="tieuNganhDialogClose" />      
    </b-modal>
  </div>
  
</template>
<script>
import popupTieuNganh from '../popup/popupTieuNganh';
import LoaiKH from '../popup/LoaiKH';
import searchAccount from '@/modules/search/subscriber/SearchAccount'
import ModalChonDiaChi from '@/modules/contract/setup/InstallNewSubs/Popups/popupChonDiaChi'
import KDatePicker from "@/components/kylq_components/KDatePicker.vue";
import SelectExt from "@/components/Controls/SelectExt";
import ComboboxGrid from "../components/ComboboxGrid";
import moment from "moment";
import {
  DataManager,
  Query,
  ODataV4Adaptor,
  Predicate
  
} from "@syncfusion/ej2-data";
export default {
  name: "ThongTinKhachHang",
  props: ["formProps","optionProps"],
  components: {
    KDatePicker,ModalChonDiaChi,searchAccount,popupTieuNganh,ComboboxGrid,LoaiKH
  },
  watch: {    
    formProps: {
      handler (newValue, oldValue) {
        this.$emit('update-form-pr-kh', newValue)
      },
      deep: true
    }
  },
  data() {
    return {   
      dataSelected:[],
      position: { X: 'center', Y: 'top' },  
      // input form LoaiKH
      objLoaiKH:{},    
    };
  },
  methods: {
    SearchDanhBa: function () {
      this.$refs.searchAccountDialog.show()
    },
    searchAccountDialogClose(data) {
      
      if (data != null) {
        
        if (data.ma_tb!=null && data.ma_tb!='') {
          this.$parent.form_TTKH.ma_kh = data.ma_tb
          this.$parent.lay_ds_dbkh_theo_makh(data.ma_tb)
        }
        else {
          this.$parent.form_TTKH.ma_kh = data.ma_kh   
          this.$parent.lay_ds_dbkh_theo_makh(data.ma_kh)       
        }
        
        
      }
      this.$refs.searchAccountDialog.hide()
    },
    popupTieuNganh: function () {          
      this.$bvModal.show('modal-tieunganh');
    },
    tieuNganhDialogClose(data) {
      
      if (data != null) {        
        if (data.ma_tb!=null && data.ma_tb!='') {
          this.$parent.form_TTKH.ma_kh = data.ma_tb
          this.$parent.lay_ds_dbkh_theo_makh(data.ma_tb)
        }
        else {
          this.$parent.form_TTKH.ma_kh = data.ma_kh   
          this.$parent.lay_ds_dbkh_theo_makh(data.ma_kh)       
        }
        
        
      }
      this.$bvModal.hide('modal-tieunganh')
    },
      getDiaChi: function (data) {
        for (let key in data) {
          this.$set(this.$parent.form_TTKH.d_diachi_kh, key, data[key])
        }
      },
      getDiaChi_ndd: function (data) {
        for (let key in data) {
          this.$set(this.$parent.form_TTKH.d_diachi_ndd, key, data[key])
        }
      },
      selectLoaigt() {

      },
      selectQuoctich() {

      },
      selectNganhnghe() {

      },
      selectDantoc() {

      },
      selectLoaikh() {

      },
      selectNganhang() {

      },
      btnLoaiKH_Click(){
        
        if (this.$parent.form_TTKH.loaikh_id !== null && this.$parent.form_TTKH.loaikh_id !== "-1"){
          
          let loaiKH = this.$parent.options.cbx_loaikh.find(item => item.loaikh_id == this.$parent.form_TTKH.loaikh_id)
          let kieukh_id = -1
          let kieukh = loaiKH ? loaiKH.loai : ""
          if (kieukh !== "")
            kieukh_id = parseInt(kieukh.toString(),10)
          let nhomlkh_id = -1
          let nhomkh = loaiKH ? loaiKH.nhomlkh_id : ""
          if (nhomkh !== "")
            nhomlkh_id = parseInt(nhomkh.toString(),10)
          this.objLoaiKH = {}
          this.objLoaiKH.loaikh_id = this.$parent.form_TTKH.loaikh_id
          this.objLoaiKH.kieukh_id = kieukh_id
          this.objLoaiKH.nhomlkh_id = nhomlkh_id          
          this.$bvModal.show("popupLoaiKH")
        }
      },
      getLoaiKHID(loadkhId){
          // this.cbo_loaiKH.Value = loaikh_id
        if(loadkhId){        
            let grid = this.$refs['cboGridLoaiKH'].$children[0].grid
            let idx = grid.getCurrentViewRecords().findIndex(item => item.loaikh_id == loadkhId)        
            if (idx > -1) grid.selectRow(idx)
        }
      },
      TimThongTinKH(){          
          this.$parent.timkiem()//lay_ds_dbkh_theo_makh(this.$parent.form_TTKH.ma_kh)
      }
  },
};
</script>      