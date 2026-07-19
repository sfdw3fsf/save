
<template>
  <b-modal :id='idModal' modal-class="fullscreen-modal" body-class="body-content" header-class="popup-header"
    content-class="popup-box" hide-footer no-close-on-backdrop no-close-on-esc>

      <div class="title">
        <span class="icon icon-notepad"></span>
       <h4>Cập Nhật Tiến Trình</h4> 
      </div>
      <div class="close -ap icon-close" data-dismiss="modal" @click="closeModal"></div>

    <div class="list-actions-top">
      <ul class="list">

        <li class="cursor-pointer">
          <a @click="btnUpdateClick"> <span class="icon one-file-plus"></span>Ghi Lại </a>
        </li>

        <li @click="closeModal" class="cursor-pointer">
          <a class="text-danger"> <span class="icon nc-icon-glyph ui-1_circle-remove"></span>Thoát</a>
        </li>
      </ul>
    </div>

    <div class="popup-body">
     
      <div class="box-form">
        <div class="legend-title">Nhập nội dung</div>
        <div class="info-row" :style="isEditMode ? 'display: none' : ''" >
              <div class="key w200">Nội dung nhắc mẫu:</div>
              <div class="value">
                <div class="select-custom">
                  <ejs-dropdownlist locale="vi-VN"   @change="oneHandleCboNDMauChange"
                    :dataSource="cboNoiDungMau.dataSource" :allowFiltering="true"
                    :fields="{ value: 'id', text: 'value' }" />
                </div>
              </div>
            </div>
       
        <div class="row">


          <div class="box-form">
            <textarea v-model='txtNoiDung' cols="4" rows="10" style='width: 100%; height: 100%;'>
                </textarea>
          </div>

        </div>
      </div>
    </div>
  </b-modal>
</template>

<script>
import Vue from 'vue';
import { DropDownListPlugin } from "@/components/duybn_components/Dropdowns/ej2-vue-dropdowns";
Vue.use(DropDownListPlugin);
import api from '../../api';
import apiHelper from '../../api.helper.js';

export default {
  props: {
    idModal: {
      type: String,
      default: "popupUpdateTientrinh",
    },
  },
  name: 'ModalUpdateTienTrinh',
  components: {
  },

  data() {
    return {
      txtNoiDung: "",
      isEditMode : false,
      params: null,
      phanVungId: null,
      cboNoiDungMau:{
        dataSource: [
          {id: 1, value:'KHDN cần sửa trước...'},
          {id: 2, value:'KH đang chờ CNVT đến sửa chữa, đề nghị đơn vị sửa chữa ngay, liên hệ anh/chị...SDT...'},
          {id: 3, value:'VNPT HN kính báo: KTV đến sửa chữa dịch vụ... Thuê bao khóa cửa. Vui lòng liên hệ lại KTV………….khi có nhà để được hỗ trợ. Trân trọng!'},
          {id: 4, value:'VNPT HN kính báo: KTV đã sửa chữa bên ngoài, Thuê bao khóa cửa. Vui lòng kiểm tra lại tín hiệu. LH KTV ...để được hỗ trợ thêm. Trân trọng!'},
          {id: 5, value:'VNPT HN kính báo: Dịch vụ báo hỏng của TB hiện đang mất tín hiệu do điện lực tiến hành thành thải, chỉnh trang tuyến phố. VNPT HN sẽ cung cấp tín hiệu trở lại ngay sau khi ĐL bàn giao mặt bằng. Vui lòng LH KTV... để biết thêm chi tiết. Trân trọng!'},
          {id: 6, value:'VNPT HN kính báo: Dịch vụ báo hỏng của TB hiện đang mất tín hiệu do sự cố...VNPT HN đang tiến hành xử lý, sẽ cung cấp tín hiệu sớm nhất, mong Quý KH thông cảm. Vui lòng LH KTV... để được hỗ trợ. Trân trọng!'},
          {id: 7, value:'Nội dung khác'},
        ],
      }
    }
  },

  methods: {

    onloadModal({isEditMode, phanVungId, data}) {
      this.params = data;
      this.txtNoiDung = data?.noidung;
      this.isEditMode = isEditMode;
      this.phanVungId = phanVungId;
   
      //doing
      if(!isEditMode){
        const textDefault =  `VNPT :  [ ${data.ma_tb } ; ${data?.LTB || ""}]: ${data?.MA_LOAIKH || ""},
        ${data?.ngay_yc || data?.ngay_bh },  ${data?.ten_tb}, LH: ${data?.dienthoai_lh || ""}
        `
        this.txtNoiDung = textDefault;
        return;
      }
    },
    oneHandleCboNDMauChange(event){
      if(!event.value) return;
      const ndMau = this.cboNoiDungMau.dataSource.find(e => e.id == event.value);
      if(!ndMau) return;
      this.txtNoiDung = ndMau.value;
    },

    async btnUpdateClick() {
      
      if (this.txtNoiDung == ""  ) return;

      const ipCN =  await this.$root.token.getIP();
      const userCN = this.$auth.getUserName();

      if(this.isEditMode && userCN != this.params.nguoi_cn){
        this.$toast.error("Bạn không có quyền cập nhật tiến trình này");
        return;
      }
      const baoHongId = this.params.tinh_id != 0 ? this.params.baohong_id_thicong : this.params.baohong_id;
      const paramUpdate = {
        p_ds_para: JSON.stringify({
          TIENTRINHBH_ID: this.params.tientrinhbh_id,
          BAOHONG_ID: baoHongId,
          MA_ND: userCN,
          NOIDUNG: this.txtNoiDung,
          LOAI_ID: this.isEditMode ? 2 : 1, //1 moi, 2 cap nhat
          IP_CN:  ipCN        
        }),
        phanvung_id: this.phanVungId
      }
      try {
        await this.saveTienTrinh(paramUpdate);
      }
      catch (e) {
        console.log(e)
      }
    },

    async saveTienTrinh(params) {
      try {
        this.loading(true);
        let response = await api.postUpdateTienTrinh(this.axios, params);
        if(apiHelper.isResponseApiSuccess(response)){
          this.txtNoiDung = "";
          this.closeModal();
          this.$toast.success("Lưu tiến trình thành công!");
          this.$emit('save-tientrinh-success-event', true);
          return;
        }
        this.loading(false);
        this.$toast.error("Lưu tiến trình không thành công");
        
      }
      catch (e) {
        this.$root.loading(false);
        console.log(e)
      }
    },
    onloadLayOutForm(){
      if( this.isEditMode){
        this.txtNoiDung = this.tienTrinhItem.noidung;
        return;
      }


    },

    closeModal() {
      this.$bvModal.hide(this.idModal);
    },

  },
}
</script>

<style  scoped>
.boxsizingBorder {
  -webkit-box-sizing: border-box;
  -moz-box-sizing: border-box;
  box-sizing: border-box;
}

.textwrapper {
  width: 100%;
}

.modal-content {
  width: 100%;
  height: 98%;
  font-size: 14px;
}

a {
  font-size: 14px;
}
</style>
