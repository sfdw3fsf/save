<template>
  <b-modal ref="DownloadFileNghiemThu" size="lg" hide-header hide-header-close hide-footer body-class="p-0" no-close-on-backdrop @show="handleShowModal">
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title"><span class="icon one-download"></span>Download File Nghiệm Thu</div>
        <div class="close -ap icon-close" @click="hideModal"></div>
      </div>
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a href="javascript: void(0)" title="Huỷ tải lên" v-on:click.prevent="hideModal"> <span class="icon fa fa-sign-out"></span> Thoát </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <DataGrid
          :columns="[
            { fieldName: 'ten_file', headerText: 'Tên file' },
            { fieldName: 'dl', headerText: 'Tải về', width: '50', template: this.colTemplate(this) }
          ]"
          panelDataHeight="150px"
          :dataSource="dataTB"
        />
      </div>
    </div>
  </b-modal>
</template>
<script>
import api from '../API.js'
export default {
  props: {
    phanvung_id: {
      type: Number,
      default: null
    },
    hdtb_id: {
      type: Number,
      default: null
    }
  },
  data() {
    return {
      dataTB: []
    }
  },
  methods: {
    colTemplate(parent) {
      return function() {
        return {
          template: {
            template: `<div>
              <button class="btn btn-primary" 
              style="position: relative;left: 50%;transform: translateX(-50%);" 
              @click="downloadFile"><i class="icon fa fa-download"></i></button>
            </div>`,
            methods: {
              async downloadFile() {
                const res = await api.link_view_file(this.axios, { list_file: this.data.url })
                try {
                  var old_url = new URL(res.data.data[0].file_name)
                } catch (_) {
                  old_url = null
                }
                window.open(old_url ? old_url : res.data.data[0].file_url, '_blank')
              }
            }
          }
        }
      }
    },
    showModal() {
      this.$refs.DownloadFileNghiemThu.show()
    },
    hideModal() {
      this.$refs.DownloadFileNghiemThu.hide()
    },
    async handleShowModal() {
      let res = await api.downloadFileNT(this.axios, { vhdtb_id: this.hdtb_id, vphanvung_id: this.phanvung_id })
      if (res.data.error_code == 'BSS-00000204') {
        this.$toast.error('Không có dữ liệu file !')
        return
      }
      this.dataTB = res.data.data
    }
  }
}
</script>
