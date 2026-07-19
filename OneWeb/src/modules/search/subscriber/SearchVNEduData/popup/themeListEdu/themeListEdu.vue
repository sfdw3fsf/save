<template>
  <div>
    <div>
      <!-- <breadcrumb :header="header" /> -->
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a @click="loadData"> <span class="icon one-search"></span>Lấy mẫu</a>
          </li>
          <li>
            <a @click="ChonMau"> <span class="icon one-save"></span>Ghi lại</a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title">Danh sách thông tin</div>
          <div class="table-content">
            <DataGrid ref="gridSoThueBaoM2M"
                      :columns="ds_ThemeEdu.header"
                      :dataSource="ds_ThemeEdu.list"
                      :showFilter="true" dataKeyField="code"
                      :allowPaging="true" :enablePagingServer="false"
                      @commandClick="commandClick"
                      @rowSelected="grid_FocusedRowChanged" />
          </div>
        </div>
      </div>
    </div>

    <ejs-dialog :enableResize='true' :isModal="true" :allowDragging="true" :visible="false" ref="dlgImgEdu"
                :header="'THÔNG TIN'"
                showCloseIcon=true  :closeOnEscape="true" width="50%" height="100%"
                @close="closedlgImgEdu">
      <div>
        <div class="popup-body">
          <img :src="img_Selected" v-if="isShow.frmIMG" />
        </div>
      </div>
    </ejs-dialog>

  </div>
</template>
<script>

import API from '../../api'
import Vue from 'vue'

export default {
  data() {
    return {
      ds_ThemeEdu: {
        header: [
        {
            headerText: 'ID mẫu',
            fieldName: 'id',
            allowSorting: false,
            width: '60',
            textAlign: 'left',
            autoFit: 'true',
            allowFiltering: true
          },
          {
            headerText: 'Mã code mẫu',
            fieldName: 'code',
            allowSorting: false,
            width: '60',
            textAlign: 'left',
            autoFit: 'true',
            allowFiltering: true
          },
          {
            headerText: 'Tên mẫu',
            fieldName: 'name',
            allowSorting: false,
            width: '100',
            textAlign: 'left',
            autoFit: 'true',
            allowFiltering: true
          },

          {
            fieldName: '',
            headerText: 'URL Trường',
            allowFiltering: true,
            allowSorting: false,
            width: 100,
            template: this.templateHyperlink,
            cssClass: 'text-center'
          },

          {
            headerText: 'Thao tác',
            width: 50,
            commands: [{ buttonOption: { content: 'Xem ảnh', cssClass: 'e-flat' } }],
            textAlign: 'center'
          }
        ],
        list: [],
        selected: {}
      },
      img_Selected: '',
      isShow: {
        frmIMG: false
      },
      mau_chon:{

      }
    }
  },
  methods: {
    async loadData() {
      try {
        this.loading(true)
        let rs = await API.themeList(this.axios)
        if (rs.data.data) {
          this.ds_ThemeEdu.list = rs.data.data

          console.log('mounted')
          console.log(this.ds_ThemeEdu.list)
        }
      } catch (e) {
        console.log(e)
      }
      finally {
        this.loading(false)
      }
    },
    async ChonMau(args) {
      try {
        console.log(this.mau_chon)
        this.$emit('form-close', this.mau_chon);
        this.$emit('emit_func', this.mau_chon);
         this.$bvModal.hide('popupComponents')
        }
       catch (e) {
        console.log(e)
      }
      finally {
        this.loading(false)
      }
    },
    templateHyperlink() {
      return {
        template: Vue.component('columnTemplate', {
          template: `
            <div>
              <a href="#" @click="onClick(data.preview_link)">{{ data.preview_link }}</a>
            </div>`,
          data() {
            return { data: {} }
          },
          methods: {
            onClick(preview_link) {
              window.open(preview_link)
            }
          }
        })
      }
    },
    commandClick(args) {
      this.img_Selected = args.rowData.avatar_url
      this.isShow.frmIMG = true
      this.$refs.dlgImgEdu.show()
    },
    grid_FocusedRowChanged(args)
    {
      this.mau_chon = args.data
    },
    closedlgImgEdu(){
      this.isShow.frmIMG = false
    }
  },
  async mounted() {
    await this.loadData()
  }
}
</script>
<style scoped>
.disabledInput {
  pointer-events: none;
  color: #666 !important;
  border-color: #9999 !important;
}

.disabledInput > a {
  color: #666 !important;
}

.popup-body {
  display: flex;
  justify-content: center; /* căn giữa theo chiều ngang */
  align-items: center; /* căn giữa theo chiều dọc */
}

.popup-body img {
  width: 100%; /* chiều ngang bằng với chiều ngang của popup-body */
  height: auto; /* tự động tính toán chiều cao dựa trên tỷ lệ khung của hình ảnh */
  object-fit: contain; /* giữ nguyên tỷ lệ khung của hình ảnh */
}

</style>
