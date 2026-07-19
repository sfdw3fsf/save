<template>
  <b-modal ref="frmXemHS" size="xl" body-class="p-0" hide-header hide-header-close hide-footer>
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title"><span class="icon one-notepad"></span> Danh sách hồ sơ tỉnh khác</div>
        <div @click="hideModal" class="close -ap icon-close"></div>
      </div>
      <div class="popup-body">
        <div class="box-form">
          Double click vào dòng trên danh sách để xem/download file
          <DataGrid v-bind:columns="ds_hs.headers" v-bind:dataSource="ds_hs.data" :showColumnCheckbox="false" :enable-paging-server="false" :allowPaging="true" :showFilter="true" @rowSelected="rowSelected_HS" @rowDoubleClicked="rowDoubleClicked_HS"> </DataGrid>
        </div>
      </div>
    </div>
  </b-modal>
</template>
<style scoped>
.hand_click {
  cursor: pointer;
}
</style>
<script>
import api from '../api'
export default {
  props: {
    vhdtb_ban: {
      type: Number,
      default: 0
    }
  },
  data() {
    return {
      dulieu: {
        hdtb_tc: 0,
        tinh_id: 0,
        vurl_luoi: '',
        vname: '',
        ten_file: '',
        vloaifile_id: 0
      },
      // Khai báo biến trong form Danh sách hồ sơ tỉnh khác
      ds_hs: {
        headers: [
          { fieldName: 'loai_file', headerText: 'Loại file', allowFiltering: true, allowSorting: false },
          { fieldName: 'ten_file', headerText: 'Tên file', allowFiltering: true, allowSorting: false },
          { fieldName: 'loai_hd', headerText: 'Loại HĐ/PL', allowFiltering: true, allowSorting: false },
          { fieldName: 'ngay_yc', headerText: 'Ngày YC', allowFiltering: true, allowSorting: false },
          { fieldName: 'ten_nhomfile', headerText: 'Nhóm file', allowFiltering: true, allowSorting: false },
          { fieldName: 'nguoi_cn', headerText: 'Người CN', allowFiltering: true, allowSorting: false },
          { fieldName: 'ngay_cn', headerText: 'Ngày CN', allowFiltering: true, allowSorting: false }
        ],
        data: []
      }
    }
  },
  created() {
    this.khoiTao()
  },
  methods: {
    showModal() {
      this.$refs['frmXemHS'].show()
    },
    hideModal() {
      this.$refs['frmXemHS'].hide()
    },
    getHDBanCheoByHDTBBan: async function (hdtb_ban) {
      let result = []
      await api.getHDBanCheoByHDTBBan(this.axios, hdtb_ban).then((res) => {
        result = res.data.data ? res.data.data : []
      })
      return result
    },
    layDsFileHSBCv2: async function (tinhthicong_id, hdtb_id, loaifile_id) {
      let result = []
      await api
        .layDsFileHSBCv2(this.axios, {
          tinhthicong_id: tinhthicong_id,
          vhdtb_id: hdtb_id,
          vloaifile_id: loaifile_id
        })
        .then((res) => {
          result = res.data.data ? res.data.data : []
        })
      return result
    },
    layDsFileHSBC: async function (hdtb_id) {
      let result = []
      await api.layDsFileHSBC(this.axios, hdtb_id).then((res) => {
        result = res.data.data ? res.data.data : []
      })
      return result
    },
    khoiTao() {
      console.log('khoiTao')
      setTimeout(async () => {
        if (!this.hdtb_tc) {
          console.log('!this.hdtb_tc')
          let rs = await this.getHDBanCheoByHDTBBan(this.vhdtb_ban)
          if (rs.length > 0 && rs[0].hdtb_thicong) {
            this.hdtb_tc = rs[0].hdtb_thicong
            this.tinh_id = rs[0].tinh_thicong
            this.ds_hs.data = []
            if (this.vloaifile_id == 2) {
              this.ds_hs.data = await this.layDsFileHSBCv2(this.tinh_id, this.hdtb_tc, this.vloaifile_id)
            } else {
              // this.ds_hs.data = await this.layDsFileHSBC(this.hdtb_tc);
              this.ds_hs.data = (
                await api.lay_ds_file_hs_bc(this.axios, {
                  vhdtb_id: this.hdtb_tc,
                  tinh_thicong_id: this.tinh_id
                })
              ).data.data
            }
          }
        } else {
          console.log('else')
          console.log(this.hdtb_tc)
          this.ds_hs.data = []
          if (this.vloaifile_id == 2) {
            this.ds_hs.data = await this.layDsFileHSBCv2(this.tinh_id, this.hdtb_tc, this.vloaifile_id)
          } else {
            // this.ds_hs.data = await this.layDsFileHSBC(this.hdtb_tc);
            this.ds_hs.data = (
              await api.lay_ds_file_hs_bc(this.axios, {
                vhdtb_id: this.hdtb_tc,
                tinh_thicong_id: this.tinh_id
              })
            ).data.data
          }
        }
      }, 100)
    },
    rowSelected_HS(args) {
      this.vurl_luoi = args.data.url
      this.vname = args.data.name
      this.ten_file = args.data.ten_file
    },
    rowDoubleClicked_HS(rowIndex, dataItem) {
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
  }
}
</script>
