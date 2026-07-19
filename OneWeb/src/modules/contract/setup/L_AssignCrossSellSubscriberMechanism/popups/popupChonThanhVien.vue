<template>
    <div class="modal-content popup-box">
        <div class="popup-header">
            <div class="title"><span class="icon one-notepad"></span>Tra cứu số công văn</div>
            <div class="close -ap icon-close hidden" data-dismiss="modal"></div>
        </div>
        <div class="list-actions-top">
            <ul class="list">
                <li>
                    <a @click="timkiem()">
                      <span class="icon one-search"></span>Tìm kiếm
                    </a>
                </li>
                <li>
                    <a href="javascript:;" @click.prevent="btnXacNhan_Click">
                        <span class="icon nc-icon-glyph ui-1_check-circle-08"></span> Chấp nhận
                    </a>
                </li>
                <li>
                    <a
                      href="#"
                      @click.prevent="xuatfile"
                      :class="{ disabled: !isRowSelected }"
                      :aria-disabled="!isRowSelected"
                    >
                      <span class="icon fa fa-folder"></span> File đính kèm
                    </a>
                </li>
                <li>
                    <a href="javascript:;" @click.prevent="btnHuyBo_Click">
                        <span class="icon nc-icon-glyph ui-1_circle-remove"></span> Thoát
                    </a>
                </li>                
            </ul>
        </div>
        <div class="page-content">
          <FormTimKiem v-model="form" :traCuuCongVan="tra_cuu_CongVan"></FormTimKiem>
          <vue-card label="Danh sách công văn">
            <DataGrid :columns="table.config" :showFiltering="true" :enable-paging-server="false" :allowPaging="true" :dataSource="table.options" 
                    :showColumnCheckbox="false"
                    :enabledSelectFirstRow="false"
                    @selectedItemsChanged="getSelectedItem"/>
          </vue-card>
          <popupAttachFiles modalId="popupAttachFiles" :data="dsFileDinhKem" /> 
        </div>
      </div>
    </div>  
</template>
 
<style>
input.disabled,
select.disabled,
.disabled input,
.disabled select,
.e-grid .e-gridheader .e-sortfilter .e-headercelldiv,
.e-grid .e-gridheader .e-stackedheadercelldiv,
.e-grid .e-gridheader .e-headercell .e-headercelldiv.e-headerchkcelldiv {
padding: 0 5px 0 5px !important;
}
@media (min-width: 992px) {
  .modal .modal-huge {
    max-width: 95% !important;
    width: 100% !important;
  }
}
@media (min-width: 576px) {
  .modal .modal-huge {
    max-width: 95% !important;
    width: 100% !important;
  }
}
</style>

<script>
import breadcrumb from '@/components/breadcrumb'
// import VueNav from '../components/elements/VueNav'
import FormTimKiem from './part/FormTimKiem'
import moment from "moment";
import api from './api'
import ExportDataModal from "@/modules/contract/profile/ExportData/ExportDataModal.vue"
import popupAttachFiles from './popupAttachFiles.vue';
export default {
  components: {
    breadcrumb,
    FormTimKiem,
    ExportDataModal,
    popupAttachFiles
  },
  props: ["dulieu"],
  data () {
    return {
      dsFileDinhKem:[],
      isRowSelected: false,
      ds: [],
      socongvan_select:'',
      p_donvi_ph_id: 0,
      p_congvan_id:0,
      final_list: [],
      selected_list: [],
      form: {
        p_socongvan:'',
        p_donvi_ph_id: 0,
        p_ten_congvan: '',
        p_loai_cv_id: 0,
        p_tungay: '0',
        p_denngay: '0',
        
        
      },
      header: {
        title: 'Tra cứu phát triển',
        list: [
          { name: 'Lập hợp đồng', link: { name: 'Ui.cards' } },
          {
            name: 'Lắp đặt mới',
            link: { name: 'Ui.buttons' }
          }
        ]
      },
      table: {
        loading: false,
        options: [],
        config: [
          {
            headerText: 'STT',
            allowFiltering: true,width: '100',
            fieldName: 'stt'
          },
          {
            headerText: 'Năm',
            allowFiltering: true,width: '100',
            fieldName: 'nam'
          },
          {
            headerText: 'Ngày quyết định',
            allowFiltering: true,width: '100',
            fieldName: 'ngay_qd'
          },
          {
            headerText: 'Công văn',
            allowFiltering: true,width: '100',
            fieldName: 'socongvan'
          },
          {
            headerText: 'Tên công văn',
            allowFiltering: true,width: '100',
            fieldName: 'ten_cv'
          },
          {
            headerText: 'Hiệu lực',
            allowFiltering: true,width: '100',
            fieldName: 'hieuluc'
          },
          {
            headerText: 'Nội dung',
            allowFiltering: true,width: '100',
            fieldName: 'noidung'
          },
          {
            headerText: 'Công văn VNP',
            allowFiltering: true,width: '100',
            fieldName: 'cv_vnp_id'
          },
          {
            headerText: 'Đơn vị phát hành',
            allowFiltering: true,width: '100',
            fieldName: 'ten_dv'
          },
          {
            headerText: 'Người nhập',
            allowFiltering: true,width: '100',
            fieldName: 'nguoi_cn'
          },
          {
            headerText: 'Ngày nhập',
            allowFiltering: true,width: '100',
            fieldName: 'ngay_cn'
          }
        ],
        all_data: []
      }
    }
  },
  watch: {  
    "dulieu.ds"(val) {
        if (val && val.length > 0) {
            this.socongvan_select = val 
            console.log ('this.socongvan_select',this.socongvan_select)         
        }        
    }
},
computed: {},
created() {    
},
  methods: {
    timkiem() {
      this.tra_cuu_CongVan()
    },
    // ClosePopup() {
    //   // Logic để đóng popup
    // },
    xuatfile() {
      this.dsFileDinhKem=[]
      this.getDsFileDinhKem(this.p_congvan_id);
    },
    async getDsFileDinhKem(p_congvan_id) {
            api.getDsFileDinhKem(this.axios, p_congvan_id)
                .then((response) => {

                    if (response.data.error_code === "BSS-00000000" && response.data.data !== undefined && response.data.data.length > 0) {
                        this.dsFileDinhKem = response.data.data;
                    }
                    this.$bvModal.show('popupAttachFiles');
                    // this.$root.loading(false);
                }).catch((error) => {
                    console.log(error);
                    this.dsFileDinhKem = [];
                    // this.$root.loading(false);
                })
                .finally(() => {
                    this.loading(false);
                });
        },

    getSelectedItem: function (data) {
        this.selected_list = data
        console.log('hàng được chọn',this.selected_list)
        this.p_congvan_id=this.selected_list[0].congvan_id
        console.log('thiện',this.selected_list[0].congvan_id)
        if (this.selected_list.length > 0){
          this.isRowSelected = true
        }
    },
    btnXacNhan_Click: function () {
      let socongvan_demo = ''

          if (this.selected_list && this.selected_list.length > 0) {
            socongvan_demo = this.selected_list.map(item => item.socongvan);
            this.socongvan_select=socongvan_demo[0]

          }
          console.log('Số công văn chọn trong modal:', this.socongvan_select); 
          console.log('Kiểu dữ liệu của socongvan_select:', typeof this.socongvan_select);

          this.$emit("form-close", this.socongvan_select);
          this.$emit('socongvan-selected', this.socongvan_select);
    },

    btnHuyBo_Click: function () {
        this.$emit("form-close", this.dulieu.ds)
    },
    ClosePopup: function (val) {
        if (val) this.$emit("form-close", val)
    },
    tra_cuu_CongVan () {
      this.loading(true)
      console.log(this.form)
      this.form.p_loai_cv_id = Number(this.form.p_loai_cv_id);
      this.form.p_donvi_ph_id = Number(this.form.p_donvi_ph_id);
      // this.form.p_loai_cv_id=2
      console.log(this.form)
      api.tra_cuu_CongVan(this.axios, this.form).then(response => {
        this.table.options = response.data.data
      }).finally(() => {
        this.loading(false)
      })
    }
  }
}
</script>