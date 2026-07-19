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
           <a href="#" @click.prevent="exportFileMau" :class="{ disabled: (linkFile == null ||  linkFile == '' || isInsertRecord == 1 )}" target="blank"> <span class="icon one-download"></span> Download File mẫu </a>
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
                <div class="title-main">Thông tin loại hợp đồng</div>
                <!-- <div class="title-sub">Trạng thái dữ liệu:</div> -->
              </div>

              <div class="info-row">
                <div class="value">
                  <vue-input v-model="name" :labelWidth="'100'" ref="refTenTB" label="Tên"></vue-input>
                </div>
              </div>

              <div class="info-row">
                <div class="value">
                  <vue-textarea :labelWidth="'100'" v-model="ghiChu" label="Mô tả" :height="'50'"></vue-textarea>
                </div>
              </div>

              <div class="info-row">
                <!-- <div class="key w100">Trang Thái</div> -->
                <div class="value">
                  <vue-select v-model="isActive" :labelWidth="'100'" :options="statusList" labelField="name" valueField="value" label="Trạng Thái"></vue-select>
                </div>
              </div>
              <div class="info-row">
                <div class="key w100">Upload file mẫu</div>

                <div class="input-group">
                  <div class="input-text" style="width: 97% !important; padding-left: 10px">
                    <input type="text" v-model="tenFile" class="form-control" @click="$refs.fileInput.click()" />
                  </div>
                  <div class="input-addon">
                    <button class="btn" @click="$refs.fileInput.click()">
                      <span class="-ap icon-more_horiz"></span>
                      <input ref="fileInput" type="file" id="upload" style="display: none" @change="uploadFileMauHandle" accept=".pdf, .doc, .docx" @click="$refs.fileInput.value = null" hidden />
                    </button>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="box-form">
        <div class="legend-title">
          <div class="row">
            <div class="title-main">Danh sách loại hợp đồng</div>
            <!-- <div class="pull-right"><input type="radio" v-model="color" value="0" />Active <input type="radio" v-model="color" value="1" />DeActive</div> -->
          </div>

          <!-- danh sach thue bao -->
          <div class="tab-pane active" id="tab1">
            <div class="table-content">
              <DataGrid :columns="categories.columns" :dataSource="categories.dataSource" :enable-paging-server="false" :showFilter="true" :allowPaging="true" 
              :showColumnCheckbox="false" :enabledSelectFirstRow="true"
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
            fieldName: 'hinhthuc_hd',
            headerText: 'Tên',
            allowFiltering: true
          },
          {
            fieldName: 'mota',
            headerText: 'Mô tả',
            allowFiltering: true
          },
          {
            fieldName: 'trang_thai',
            headerText: 'Trạng Thái',
            allowFiltering: true
          },
          {
            fieldName: 'ngay_tao',
            headerText: 'Ngày Tạo',
            allowFiltering: true
          },
          {
            fieldName: 'ma_nguoi_tao',
            headerText: 'nguoi Tạo',
            allowFiltering: true
          },
          {
            fieldName: 'ma_nguoi_cn',
            headerText: 'Người cập nhât',
            allowFiltering: true
          },
          {
            fieldName: 'ngay_cn',
            headerText: 'Ngày Cập nhật',
            allowFiltering: true
          }
        ],
        dataSource: []
      },

      statusList: [
        { name: 'Active', value: '1' },
        { name: 'DeActive', value: '0' }
      ],
      currentRecord: null,
      isActive: 1,
      isInsertRecord: 1,
      enabledBtnGhiLai: true,
      enabledBtnNhapMoi: true,
      enabledBtnDelete: true,
      tenFile: '',
      ghiChu: '',
      name: '',
      contractCode: '',
      linkFile: ''
    }
  },
  async created() {
    await this.getListContracts()
  },
  watch: {
    isInsertRecord(val) {
      this.enabledBtnGhiLai = true
      if (val === 1) {
        this.enabledBtnNhapMoi = true
        // this.enabledBtnHuy = true
        this.enabledBtnDelete = false
      }
      if (val === 0) {
        this.enabledBtnNhapMoi = true
       
        // this.enabledBtnHuy = false
        this.enabledBtnDelete = true
      }
    },
    currentRecord(val) {
      if (val) {
        this.name = val.hinhthuc_hd
        this.ghiChu = val.mota
        this.contractCode = val.hthd_id
        this.isActive = val.trang_thai === 'Active' ? 1 : 0
        this.linkFile = val.full_url
        this.tenFile = val.url // hien thi tren input
      } else {
        this.name = ''
        this.ghiChu = ''
        this.notes = ''
        this.isActive = 1
        this.linkFile = ""
        this.tenFile = ""
      }
    }
  },
  methods: {
    async btnGhiLai() {
      //check validate form
      if (!this.validateForm) {
        return
      }

      this.$root.loading(true) //loading

      try {
        let params = {}
        if (this.isInsertRecord === 1) {
          const responseMaSP = await api.getCodeContract(this.axios)
          if (responseMaSP && responseMaSP.data && responseMaSP.data.error_code && responseMaSP.data.error_code === 'BSS-00000000') {
            const maSP = responseMaSP.data.data
            params = { is_insert: this.isInsertRecord, hthd_id: maSP, hinhthuc_hd: this.name, mota: this.ghiChu, url_file: this.linkFile }
          } else {
            this.$toast.error(response.data.message)
            return
          }
        } else {
          params = { is_insert: this.isInsertRecord, hthd_id: this.contractCode, hinhthuc_hd: this.name, mota: this.ghiChu, url_file: this.linkFile }
        }
        const response = await api.insertUpdateContracts(this.axios, params)

        this.$root.loading(false) //end loading

        if (response && response.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
          await this.getListContracts()
          this.$toast.success('Ghi lại dữ liệu thành công')
        } else {
          this.$toast.error(response.data.message)
          return
        }
      } catch (error) {
        console.log(error)
        this.$toast.error('Có lỗi xảy ra. Vui lòng liên hệ quản trị viên!')
        this.$root.loading(false) //end loading
      }
    },

    async btnNhapMoi() {
      this.isInsertRecord = 1
      this.name = ''
      this.ghiChu = ''
      this.tenFile = ''
      this.isActive = 1
      this.contractCode = ''
      this.enabledBtnNhapMoi = true
      this.linkFile = ''
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
            api
              .deleteContract(this.axios, { ma_sp: this.contractCode })
              .then(async (response) => {
                if (response && response.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                  this.$toast.success('Xoa ban ghi thành công')
                  await this.getListContracts()
                } else {
                  this.$toast.error(response.data.message)
                }
              })
              .catch((error) => {
                console.log(error)
              })
          }

          return v
        })
    },
    uploadFileMauHandle(event) {
      let formData = new FormData()

      const file = event.target.files ? event.target.files[0] : null
      this.$root.loading(true)
      if (file) {
        formData.append('files', file)
        api
          .uploadFile(this.axios, formData)
          .then((response) => {
            if (response.data.error_code === 'BSS-00000000') {
              this.linkFile = response.data.data[0]
              this.tenFile = file.name
              this.$root.loading(false)
              console.log('upload_file done!', this.linkFile)
            }
          })
          .catch((er) => {
            this.$root.loading(false)
            console.log('upload_file er:', er)
          })
      }
    },
    exportFileMau() {
      if(!this.linkFile){
        this.$toast.error("Không có file mẫu được upload.")
        return;
      }
      window.open(this.linkFile);
      // window.location.href =this.linkFile
      
    },
    async getListContracts() {
      try {
        const response = await api.getListContracts(this.axios)

        if (response && response.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
          const data = response.data.data.map((item) => {
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
    selectedItemsChangedCG(obj) {
      if (!obj) return
      this.isInsertRecord = 0
      this.currentRecord = obj[0]
    },
    validateForm() {
      if (!this.name) {
        this.$toast.error('Tên không được bỏ trống!')
        return false
      }
      if (!this.tenFile) {
        this.$toast.error('file không được bỏ trống!')
        return false
      }
      return true
    }
  }
}
</script>