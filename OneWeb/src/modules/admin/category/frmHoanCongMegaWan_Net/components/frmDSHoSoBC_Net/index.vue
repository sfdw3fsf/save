<template>
  <div>
    <div>
      <div class="list-actions-top" style="position: unset">
        <ul class="list" style="position: unset">
          <li v-if="tsbtnXoa.visible && ['NET'].includes(vform)" :class="{ disabledInput: !tsbtnXoa.enabled }" @click="tsbtnXoa_Clicked">
            <a> <span class="icon one-file-remove"></span>{{ tsbtnXoa.text }}</a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <p>Double click vào dòng trên danh sách để xem/download file</p>
          <DataGrid :columns="gridFile.headers" :dataSource="gridFile.list" :showColumnCheckbox="false" :enable-paging-server="false" :allowPaging="true" :showFilter="true" @selectedItemsChanged="gridFileSelectedItemsChanged" @rowSelected="gridFileRowSelected" @rowDoubleClicked="gridFileRowDoubleClicked"> </DataGrid>
        </div>
      </div>
    </div>
  </div>
</template>
<style scoped>
.hand_click {
  cursor: pointer;
}
</style>
<script>
import api from './api'
export default {
  props: {
    vform: {
      type: [String],
      default: 'NET',
      required: false
    },
    vhdtb_ban: {
      type: [Number, String],
      default: 0
    },
    vhdtb_tc: {
      type: [Number, String],
      default: 0
    },
    vtinh_id: {
      type: [Number, String],
      default: 0
    },
    vloaifile_id: {
      type: [Number, String],
      default: null
    }
  },
  data() {
    return {
      tsbtnXoa: { enabled: true, visible: true, text: 'Xoá' },
      gridFile: {
        headers: [
          { fieldName: 'loai_file', headerText: 'Loại file', allowFiltering: true, allowSorting: false },
          { fieldName: 'ten_file', headerText: 'Tên file', allowFiltering: true, allowSorting: false },
          { fieldName: 'loai_hd', headerText: 'Loại HĐ/PL', allowFiltering: true, allowSorting: false },
          { fieldName: 'ngay_yc', headerText: 'Ngày YC', allowFiltering: true, allowSorting: false },
          { fieldName: 'ten_nhomfile', headerText: 'Nhóm file', allowFiltering: true, allowSorting: false },
          { fieldName: 'nguoi_cn', headerText: 'Người CN', allowFiltering: true, allowSorting: false },
          { fieldName: 'ngay_cn', headerText: 'Ngày CN', allowFiltering: true, allowSorting: false }
        ],
        list: [],
        selected: [],
        value: null
      }
    }
  },
  methods: {
    async tsbtnLayTT_Clicked() {
      if ([0, '0', null].includes(this.vhdtb_tc)) {
        let kq = await api.get_hd_bancheo_by_hdtb_ban(this.axios, this.vhdtb_ban)
        if (kq.data.data.length > 0 && kq.data.data[0].hdtb_thicong) {
          this.vhdtb_tc = kq.data.data[0].hdtb_thicong
          this.vtinh_id = kq.data.data[0].tinh_thicong

          if (this.vloaifile_id == 2) {
            let kq_lay_ds_file_hs_bc_v2 = await api.lay_ds_file_hs_bc_v2(this.axios, {
              tinhthicong_id: this.vtinh_id,
              vhdtb_id: this.vhdtb_tc,
              vloaifile_id: this.vloaifile_id
            })

            this.gridFile.list = kq_lay_ds_file_hs_bc_v2.data.data ?? []
          } else {
            let kq_lay_ds_file_hs_bc = await api.lay_ds_file_hs_bc(this.axios, {
              vhdtb_id: this.vhdtb_tc,
              tinh_thicong_id: this.vtinh_id
            })
            this.gridFile.list = kq_lay_ds_file_hs_bc.data.data ?? []
          }
        }
      } else {
        if (this.vloaifile_id == 2) {
          let kq_lay_ds_file_hs_bc_v2 = await api.lay_ds_file_hs_bc_v2(this.axios, {
            tinhthicong_id: this.vtinh_id,
            vhdtb_id: this.vhdtb_tc,
            vloaifile_id: this.vloaifile_id
          })
          this.gridFile.list = kq_lay_ds_file_hs_bc_v2.data.data ?? []
        } else {
          let kq_lay_ds_file_hs_bc = await api.lay_ds_file_hs_bc(this.axios, {
            vhdtb_id: this.vhdtb_tc,
            tinh_thicong_id: this.vtinh_id
          })
          this.gridFile.list = kq_lay_ds_file_hs_bc.data.data ?? []
        }
      }
    },
    async tsbtnXoa_Clicked() {
      if (!this.gridFile.selected.length) {
        this.$toast.warning('Vui lòng chọn file cần xoá')
        return
      }
      if (!confirm('Bạn có chắc chắn muốn xoá file hồ sơ này không!')) return

      let kq = await api.cap_nhat_file_hoso(this.axios, {
        vkieu: 3,
        vhoso_id: -1,
        url: '',
        vghichu: '',
        vfile_cn: this.gridFile.value.file_id,
        vloaifile_id: this.gridFile.value.loaifile_id,
        vfile_clob: "0",
        // vfile_clob: JSON.stringify(
        //   this.gridFile.selected.map((e) => {
        //     return { FILE_ID: e.file_id }
        //   })
        // )
      })
      if ((kq.data.error_code = 'BSS-00000000')) {
        await this.tsbtnLayTT_Clicked()
        this.$toast.success(`${kq.data.message}`)
      } else {
        this.$toast.error(`${kq.data.message}`)
      }
    },
    gridFileRowSelected(args) {
      console.log(args)
      this.vurl_luoi = args.data.url
      this.vname = args.data.name
      this.ten_file = args.data.ten_file
      this.gridFile.value = args.data ?? null
    },
    async gridFileSelectedItemsChanged(e) {
      console.log('gridFileSelectedItemsChanged')
      console.log(e)
      this.gridFile.selected = e
    },
    gridFileRowDoubleClicked(rowIndex, dataItem) {
      if (dataItem.url.startsWith('http')) {
        window.open(dataItem.url)
      } else {
        api
          .getLinkFile(this.axios, {
            list_file: dataItem.url
          })
          .then((res) => {
            if (res.data.data && res.data.data.length > 0) {
              for (let i = 0; i < res.data.data.length; i++) {
                window.open(res.data.data[i].file_url)
              }
            } else {
              this.$toast.error('Không mở được file ' + res.data.message_detail)
            }
          })
          .catch((err) => {
            self.$toast.error('Có lỗi trong quá trình dowload và mở file ' + err.data.message_detail)
          })
      }
    }
  },
  async mounted() {
    await this.tsbtnLayTT_Clicked()
  }
}
</script>
<style scoped>
.disabledInput {
  pointer-events: none;
  opacity: 0.4;
}
</style>