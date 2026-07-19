<template src="./ReceiveConsultingContract.html"></template>
<style src="./ReceiveConsultingContract.scss"></style>
<script>
import breadcrumb from '@/components/breadcrumb'
import ReceiveConsultingContractAPI from './ReceiveConsultingContractAPI'
import moment from 'moment'
import EventBus from '@/utils/eventBus'
import Vue from 'vue'
//CSS
import '@/assets/vendor/bootstrap-table/bootstrap-table.min.css';
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.css';
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
//JS
import '@/assets/vendor/jquery/split'
import '@/assets/vendor/bootstrap-table/bootstrap-table.min'
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.min'
import { NumericEditCell } from '@syncfusion/ej2-grids';
//

export default {
  components: { breadcrumb },
  name: 'ReceiveConsultingContractAPI',
  mounted () {
    //load ds yêu cầu
    ReceiveConsultingContractAPI.lay_yc(this.axios, {}).then((response) => {
    if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000' && response.data.data) {
        this.listbox.yeu_cau.list = response.data.data;
        }
      }
    );
    //load ds trạng thái
    ReceiveConsultingContractAPI.load_trang_thai(this.axios, {}).then((response) => {
    if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000' && response.data.data) {
        this.listbox.trang_thai.list = response.data.data;
        }
      }
    );
    // load danh sách lý do trả
    ReceiveConsultingContractAPI.ly_do_tra(this.axios, {}).then((response) => {
    if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000' && response.data.data) {
        this.listbox.ly_do_tra.list = response.data.data;
        }
      }
    );
    // load danh sách loại giấy tờ
    ReceiveConsultingContractAPI.loai_giay_to(this.axios, {"hdkh_id": 5122772}).then((response) => {
    if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000' && response.data.data) {
        this.listbox.loai_giay_to.list = response.data.data;
        }
      }
    );
   this.btnSearchClicked();
  },
  data () {
    return {
      header: {
        title: 'Tiếp nhận hợp đồng qua kênh CTV kỹ thuật',
        list: [
          { 
            name: 'Lắp đặt mới', 
            link: { name: 'Ui.cards' } 
          },
          {
            name: 'Lắp đặt mới',
            link: { name: 'Ui.buttons' }
          },
        ]
      },
      LapDatMoiTemplate: function () {
                return { template : Vue.component('columnTemplate', {
                    template: `<div>
                                <a class="btn btn-primary lh14 pad3" href="https://onebss.vnpt.vn/#/contract/InstallNewSubs" title="Lắp đặt mới" target="_blank">
                                    <span class="icon nc-icon-glyph files_single-content-02"></span>
                                </a>
                                <a class="btn btn-primary lh14 pad3" title="" href="https://onebss.vnpt.vn/#/contract/ChangeSubsType" title="Thay đổi loại hình" target="_blank">
                                    <span class="icon one-file-back"></span>
                                </a>
                                </div>`,                        
                    data () { 
                        return { data: {} }; 
                    },
                    methods: {
                      lapdatmoi(){

                      }
                    }})
                }
          },
      txtTraCuu: "",
      TxtMaGD: "",
      TxtNoiDung: "",
      TxtKieuYC: "",
      TxtTenKH: "",
      TxtDiaChi: "",
      TxtNgayTiepNhan: "",
      checkNgayNhan: false,
      tiepnhan: true,
      traphieu: true,
      ketquatimkiem: {
          list: [],
          header:[],
          value: {},
          columns: [
          { fieldName: 'ma_gd', headerText: 'Mã GD', allowFiltering: true },
          { fieldName: 'ngay_yc', headerText: 'Ngày YC', allowFiltering: true },
          { fieldName: 'ngaygiao', headerText: 'Ngày giao', allowFiltering: true },
          { fieldName: 'ten_kh', headerText: 'Tên KH', allowFiltering: true },
          { fieldName: 'so_dt', headerText: 'Số ĐT', allowFiltering: true, cssClass: 'text-right'},
          { fieldName: 'diachi_kh', headerText: 'Địa chỉ', allowFiltering: true },
          { fieldName: 'dv_giao', headerText: 'Đơn vị giao', allowFiltering: true },
          { fieldName: 'ten_kieuld', headerText: 'Kiểu YC', allowFiltering: true },
          { fieldName: 'ghichu', headerText: 'Ghi chú', allowFiltering: true }                            
        ],
      },
      listbox: {
        yeu_cau:{
          list:[],
          value: 750,
          isEnabled:false,
        },
        trang_thai:{
          list:[],
          value: 1,
          isEnabled:false,
        },
        ly_do_tra: {
          list:[],
          value: 109,
          isEnabled:false,
        },
        loai_giay_to: {
          list:[],
          value: 1,
          isEnabled:false,
        },
        ketquatimkiem3: {
          list:[],
          value: "",
          isEnabled:false,
        },
        listImage: {
          list:[],
          value: "",
        }
      },
       config: {
                dateConfig: {
                    value: new Date(),
                    format: "dd/MM/yyyy",
                    max: new Date()
                }
            },
    }
  },
  methods: {
    btnSearchClicked() {
      this.ketquatimkiem.list = null;
      var pdata = {};
      pdata.vnhanvien_id = 4531;
      pdata.vdonvi_id = 1;
      pdata.vtthd_id = 1;
      pdata.vttph_id = this.listbox.trang_thai.value;
      if (this.listbox.trang_thai.value == 1){
        this.traphieu = false;
        this.tiepnhan = true;
      }
      if (this.listbox.trang_thai.value == 2){
        this.tiepnhan = false;
        this.traphieu = true;
      }
      ReceiveConsultingContractAPI.tracuu(this.axios, pdata).then((response) => {
        if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000' && response.data.data) {
            if(response.data.data.length == 0){
              this.$toast.error('Không tìm thấy danh sách phiếu')
            }
            this.loading(true);
            this.ketquatimkiem.list = response.data.data;
            this.loading(false);
        }
      });
    },
    LocData(){
      for(var i = 0; i<this.ketquatimkiem.list.length; i++){
        
      }
    },
    selectedRowChanged(obj){
      this.listbox.listImage.list = "";
      this.ketquatimkiem.value = obj;
      this.listbox.ketquatimkiem3.list = obj;
      this.TxtMaGD = this.listbox.ketquatimkiem3.list.ma_gd;
      this.TxtTenKH = this.listbox.ketquatimkiem3.list.ten_kh;
      this.TxtKieuYC = this.listbox.ketquatimkiem3.list.ten_kieuld;
      this.TxtDiaChi = this.listbox.ketquatimkiem3.list.diachi_kh;
      this.TxtNgayTiepNhan = this.listbox.ketquatimkiem3.list.ngaygiao;
      if (this.listbox.ketquatimkiem3.list.listImage != null){
        this.listbox.listImage.list = this.listbox.ketquatimkiem3.list.listImage[0];
      }
    },
    TiepNhan(){
      var pdata = {};
        pdata.kieu = 1;
        pdata.nd_tra = this.TxtNoiDung;
        pdata.lydotra_id = this.listbox.ly_do_tra.value;
        pdata.json_dsphieu = this.listbox.ly_do_tra.value;
      var json_data = {};
        json_data.phieu_id = this.listbox.ketquatimkiem3.list.phieu_id;
        json_data.phieu_cha_id = this.listbox.ketquatimkiem3.list.phieu_cha_id;
        json_data.hdtb_id = this.listbox.ketquatimkiem3.list.hdkh_id;
        json_data = JSON.stringify(json_data);
      pdata.json_dsphieu = json_data;
      ReceiveConsultingContractAPI.thao_tac_phieu(this.axios, pdata).then((response) => {
        if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000' && response.data.data) {
          this.btnSearchClicked();
          this.$toast.success('Tiếp nhận phiếu tư vấn thành công')
        }
      });
    },
    TraPhieu(){
      if (this.TxtNoiDung == ""){
        this.$toast.error('Bạn chưa điền nội dung trả phiếu')
      }
      else
      {
      var pdata = {};
        pdata.kieu = 2;
        pdata.nd_tra = this.TxtNoiDung;
        pdata.lydotra_id = this.listbox.ly_do_tra.value;
        pdata.json_dsphieu = this.listbox.ly_do_tra.value;
      var json_data = {};
        json_data.phieu_id = this.listbox.ketquatimkiem3.list.phieu_id;
        json_data.phieu_cha_id = this.listbox.ketquatimkiem3.list.phieu_cha_id;
        json_data.hdtb_id = this.listbox.ketquatimkiem3.list.hdkh_id;
        json_data = JSON.stringify(json_data);
      pdata.json_dsphieu = json_data;
      ReceiveConsultingContractAPI.thao_tac_phieu(this.axios, pdata).then((response) => {
        if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000' && response.data.data) {
          this.btnSearchClicked();
          this.$toast.success('Trả phiếu tư vấn thành công')
        }
      });
      }
    },
    HuyBo(){
      var pdata = {};
        pdata.kieu = 3;
        pdata.nd_tra = this.TxtNoiDung;
        pdata.lydotra_id = this.listbox.ly_do_tra.value;
        pdata.json_dsphieu = this.listbox.ly_do_tra.value;
      var json_data = {};
        json_data.phieu_id = this.listbox.ketquatimkiem3.list.phieu_id;
        json_data.phieu_cha_id = this.listbox.ketquatimkiem3.list.phieu_cha_id;
        json_data.hdtb_id = this.listbox.ketquatimkiem3.list.hdkh_id;
        json_data = JSON.stringify(json_data);
      pdata.json_dsphieu = json_data;
      ReceiveConsultingContractAPI.thao_tac_phieu(this.axios, pdata).then((response) => {
        if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000' && response.data.data) {
          this.btnSearchClicked();
          this.$toast.success('Hủy bỏ phiếu tư vấn thành công')
        }
      });
    },
    created: async function () {
      this.loading(true);
      this.loadData();
      this.loading(false);
    },
  },
};

</script>
