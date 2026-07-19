<template>
  <div class="main-wrapper">
    <div class="breadcrumb-top">
      <!-- <breadcrumb :header="header" /> -->
    </div>
    <div class="list-actions-top">
      <ul class="list">
        <li>
          <a @click="btnNhapMoi" :class="{ disabled: !enabledBtnNhapMoi }"> <span class="icon one-file-plus"></span>Nhập mới </a>
        </li>

        <li>
          <a @click="btnGhiLai" :class="{ disabled: !enabledBtnGhiLai }"> <span class="icon one-save"></span>Ghi lại </a>
        </li>

        <li>
          <!-- <a @click="btnXoa" :class="{ disabled: !enabledBtnHuy }"> <span class="icon nc-icon-glyph ui-1_circle-remove"></span>Hủy </a> -->
        </li>

        <li>
          <a @click="btnXoa" :class="{ disabled: !enabledBtnDelete }"> <span class="icon one-trash"></span>Xóa </a>
        </li>
      </ul>
    </div>
    <!-- content page -->
    <div class="page-content">
      <!-- thong tin uu dai -->
      <div class="information-thue-bao-uu-dai">
        <div class="row">
          <div class="col-sm-12 col-12">
            <div class="box-form">
              <div class="legend-title">
                <div class="title-main">Thông tin loại sản phẩm</div>
                <!-- <div class="title-sub">Trạng thái dữ liệu:</div> -->
              </div>

              <div class="info-row">
                <div class="value">
                  <vue-input v-model="categoryName" :labelWidth="'100'" ref="refTenTB" label="Tên (*)" :class="{ disableInput: !enabledBtnGhiLai }"></vue-input>
                </div>
              </div>

              <div class="info-row">
                <div class="value">
                  <vue-textarea :labelWidth="'100'" v-model="notes" label="Mô tả" :height="'50'"></vue-textarea>
                </div>
              </div>
              <div class="info-row">
                <!-- <div class="key w100">Trang Thái</div> -->

                <vue-select v-model="isActive" :labelWidth="'100'" :options="statusList" labelField="name" valueField="value" label="Trạng Thái" :disable="isInsertRecord===1?true:false"></vue-select>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="box-form">
        <div class="legend-title">
          <div class="title-main">Danh sách loại sản phẩm</div>
          <!-- danh sach thue bao -->
          <div class="tab-pane active" id="tab1">
            <div class="table-content">
              <DataGrid :columns="categories.columns" :dataSource="categories.dataSource" :enable-paging-server="false" :showFilter="true" :allowPaging="true" :showColumnCheckbox="false" :enabledSelectFirstRow="true"  
              @selectedItemsChanged="selectedItemsChangedCG"> </DataGrid>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!--  <script src="../vendor/jquery/split.js"></script> -->
  </div>
</template>



<script>
import api from './api'
export default {
  data() {
    return {
      /* data header action */
      header: {
        title: 'Danh sach danh muc san pham',
        list: [
          { name: 'Danh mục sản phẩm', link: { name: 'Ui.cards' } },
          {
            name: 'Lắp đặt mới',
            link: { name: 'Ui.buttons' }
          }
        ]
      },
      categories: {
        columns: [
          {
            fieldName: 'ten_loai_sp',
            headerText: 'Tên',
            allowFiltering: true
          },
          {
            fieldName: 'mo_ta_sp',
            headerText: 'Mô tả',
            allowFiltering: true
          },
          {
            fieldName: 'trang_thai',
            headerText: 'Trạng Thái',
            // template: this.templateTrangThai('trang_thai'),
            allowFiltering: true
          }
        ],
        dataSource: []
      },
      statusList: [
        { name: 'Active', value: 1 },
        { name: 'DeActive', value: 0 }
      ],
      categoryName: '',
      notes: '',
      isActive: 1,
      isInsertRecord: 1,
      enabledBtnGhiLai: true,
      enabledBtnNhapMoi: true,
      // enabledBtnHuy: false,
      enabledBtnDelete: false,

      currentRecord: null,
      maSPSelected: ''
    }
  },
  computed: {},
  watch: {
    isInsertRecord(val) {
      if (val === 1) {
        this.enabledBtnNhapMoi = true
        this.enabledBtnGhiLai = true
        // this.enabledBtnHuy = true
        this.enabledBtnDelete = false
      }
      if (val === 0) {
        this.enabledBtnNhapMoi = true
        this.enabledBtnGhiLai = true
        // this.enabledBtnHuy = false
        this.enabledBtnDelete = true
      }
    },
    currentRecord(val) {
      if (val) {
        this.categoryName = val.ten_loai_sp;
        this.maSPSelected = val.ma_loai_sp;
        this.notes= val.mo_ta_sp;
        this.isActive= val.trang_thai === "Active" ? 1 : 0;
      }else{
        this.categoryName = "";
        this.maSPSelected = "";
        this.notes= "";
        this.isActive = 1;
      }
    }
  },
  async created() {
    await this.getCateList()
  },
  methods: {
    async btnGhiLai() {

      //check validate form
      if(!this.validateForm){
        return;
      }

      this.$root.loading(true); //loading

      let params = {}
      if (this.isInsertRecord === 1) {
        const responseMaSP = await api.getCodeCategories(this.axios)
        if (responseMaSP && responseMaSP.data && responseMaSP.data.error_code && responseMaSP.data.error_code === 'BSS-00000000') {
          const maSP = responseMaSP.data.data;
          params = { is_insert: this.isInsertRecord, ma_loai_sp: maSP, ten_loai_sp: this.categoryName, mo_ta_sp: this.notes, trang_thai: this.isActive }
        } else {
          this.$toast.error(response.data.message)
          return
        }
      }else{
          params = { is_insert: this.isInsertRecord, ma_loai_sp : this.maSPSelected, ten_loai_sp: this.categoryName, mo_ta_sp: this.notes, trang_thai: this.isActive }
        }
      const response = await api.insertUpdateCategory(this.axios, params );

      this.$root.loading(false);//end loading

      if (response && response.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
        await this.getCateList()
        this.$toast.success('Ghi lại dữ liệu thành công')
       
      } else {
        this.$toast.error(response.data.message)
        return
      }
    },

    async btnNhapMoi() {
      this.isInsertRecord = 1;
      this.categoryName = "";
      this.maSPSelected = "";
      this.notes= "";
      this.isActive = 1;
      this.enabledBtnNhapMoi=true;
    },

    async btnXoa() {
      this.$bvModal
        .msgBoxConfirm(`Bạn có muốn xoá bản ghi hiện tại không?`, {
          title: 'Xác nhận hành động',
          size: 'sm',
          okTitle: 'Đồng ý',
          cancelTitle: 'Hủy'
        })
        .then((v) => {
          if (v) {
            api.deleteCategory(this.axios, { ma_sp: this.maSPSelected }).then(async (response) => {
              if (response && response.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
             
                this.$toast.success('Xoa ban ghi thành công')
                await this.getCateList();
               
              }else{
                this.$toast.error(response.data.message)
              }
            }).catch(error=>{
              console.log(error)
            })
          }

          return v
        })
    },
    async getCateList() {
      try {
        const response = await api.getListCategories(this.axios)

        if (response && response.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {

         const  data = response.data.data.map(item => {
          if (item.trang_thai == 1) {
            item.trang_thai = 'Active'
          } else {
            item.trang_thai = 'Deactive'
          }
          return item
        })
          
          this.categories.dataSource = data

          // if (this.categories.dataSource.length > 0) {
          //   this.currentRecord = this.categories.dataSource[0]
          //   this.isInsertRecord = 0;
          // }
        }
      } catch (error) {
        console.log(error)
      }
    },
    selectedItemsChangedCG(obj){
      if(!obj) return;
      this.isInsertRecord = 0;
      this.currentRecord = obj[0];
    },
    validateForm(){
      if(!this.categoryName){
        this.$toast.error('Tên không được bỏ trống!')
        return false
      }
      return true;
      
    }
  }
}
</script>