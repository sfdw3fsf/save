<template>
  <div>
    <breadcrumb :header="header" />
    <div v-if="password.hasPermission">
      <ActionTop :actions="actions" @onActionClick="onActionClick" />
      <div class="page-content">
        <ThongTinNhomND ref="thongtinnhomnd" @changeNhomNguoiDung="changeNhomNguoiDung" />
        <div class="box-move-select-table">
          <div class="box-col box-form">
            <KDataGrid :columns="columnsChuaGan" :dataSource="dataSourcesChuaGan" :showColumnCheckbox="true" :enable-paging-server="false" :allowPaging="true" :showFilter="true" ref="gridChuaGan" :enabledSelectFirstRow="false" panelDataHeight="360px" />
          </div>
          <div class="actions">
            <button class="btn btn-main" @click="ganQuyen">
              <span class="-ap icon-plus3"></span>
            </button>
            <button class="btn btn-main" @click="goGanQuyen">
              <span class="-ap icon-minus3"></span>
            </button>
          </div>
          <div class="box-col box-form">
            <KDataGrid :columns="columnsDaGan" :dataSource="dataSourcesDaGan" :showColumnCheckbox="true" :enable-paging-server="false" :allowPaging="true" :showFilter="true" ref="gridDaGan" :enabledSelectFirstRow="false" panelDataHeight="360px" />
          </div>
        </div>
      </div>
    </div>
    <password-view style="padding: 10px;" v-else @onPasswordSubmittedSuccessListener="password.hasPermission=true" />
  </div>
</template>
<script>
import breadcrumb from '@/components/kylq_components/breadcrumb.vue'
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import KDataGrid from '@/components/kylq_components/KDataGrid'
import ThongTinNhomND from './ThongTinNhomND.vue'
import PasswordView from '@/modules/admin/category/GrantUserPermission/components/password-view.vue'
import GrantUserPermissionAPI from './GrantUserPermissionAPI'
export default {
  name: 'GrantUserPermission',
  components: {
    breadcrumb,
    ActionTop,
    ThongTinNhomND,
    KDataGrid,
    PasswordView
  },
  data() {
    return {
      header: {
        title: 'Phân quyền nhóm người dùng',
        list: [
          {
            name: 'Lập hợp đồng',
            link: { name: 'Ui.cards' },
            active: false
          },
          {
            name: 'Lắp đặt mới',
            link: { name: 'Ui.buttons' },
            active: true
          }
        ]
      },
      actions: [
        {
          id: 'ghilai',
          name: 'Ghi lại',
          active: true,
          icon_class: 'one-save'
        }
      ],
      columnsChuaGan: [
        {
          fieldName: 'ten_quyen',
          headerText: 'Tên quyền chưa gán',
          allowFiltering: true
        }
      ],
      dataSourcesChuaGan: [],
      columnsDaGan: [
        {
          fieldName: 'ten_quyen',
          headerText: 'Tên quyền đã gán',
          allowFiltering: true
        }
      ],
      dataSourcesDaGan: [],
      nhomNguoiDung: null,
      password: {
        hasPermission: false,
        value: ''
      }
    }
  },
  mounted() {
    // let password = prompt('Vui lòng nhập mật khẩu để sử dụng chức năng này')
    // console.log('password')
    // console.log(password)
    // while (password.length == 0) {
    //   alert('Please enter your name!')
    //   name = prompt('Vui lòng nhập mật khẩu để sử dụng chức năng này')
    // }
  },
  methods: {
    onActionClick(action) {
      if (action.id == 'ghilai') {
        this.$refs.thongtinnhomnd.ghiDuLieu()
      }
    },
    ganQuyen() {
      if (this.dataSourcesChuaGan.length == 0 || this.nhomNguoiDung == null) {
        return
      }
      let dataCheckedsChuaGan = this.$refs.gridChuaGan.getSelectedRecords()
      if (dataCheckedsChuaGan.length == 0) {
        return
      }
      //console.log('computer', computer())
      var ds_quyen = []
      dataCheckedsChuaGan.forEach((item) => {
        ds_quyen.push(item.quyen_id)
      })

      let nguoi_cn = this.$root.token.getUserName()
      let data = {
        nhom_nd_id: this.nhomNguoiDung.NHOM_ND_ID,
        may_cn: 'localhost',
        cn: nguoi_cn,
        ds_quyen: ds_quyen
      }
      this.add_ds_quyen_gan_nhomnd(data)
    },
    goGanQuyen() {
      if (this.dataSourcesDaGan.length == 0 || this.nhomNguoiDung == null) {
        return
      }
      let dataCheckedsGan = this.$refs.gridDaGan.getSelectedRecords()
      if (dataCheckedsGan.length == 0) {
        return
      }
      var ds_quyen = []
      dataCheckedsGan.forEach((item) => {
        ds_quyen.push(item.quyen_id)
      })

      let nguoi_cn = this.$root.token.getUserName()
      let data = {
        nhom_nd_id: this.nhomNguoiDung.NHOM_ND_ID,
        may_cn: 'localhost',
        cn: nguoi_cn,
        ds_quyen: ds_quyen
      }
      this.del_ds_quyen_gan_nhomnd(data)
    },
    changeNhomNguoiDung(item) {
      this.nhomNguoiDung = item
      this.lay_nhom_quyen_by_nd_id(item.NHOM_ND_ID)
    },
    async lay_nhom_quyen_by_nd_id(nhom_nd_id) {
      this.dataSourcesChuaGan = []
      this.dataSourcesDaGan = []
      try {
        this.loading(true)
        let response = await GrantUserPermissionAPI.lay_nhom_quyen_by_nd_id(this.axios, nhom_nd_id)
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          let result = response.data.data
          //api bị gán ngược cho nhau
          if (result.ds_dagan) {
            //this.dataSourcesDaGan=result.ds_dagan
            this.dataSourcesChuaGan = result.ds_dagan
          }
          if (result.ds_chuagan) {
            //this.dataSourcesChuaGan=result.ds_chuagan
            this.dataSourcesDaGan = result.ds_chuagan
          }
        } else {
          this.dataSourcesChuaGan = []
          this.dataSourcesDaGan = []
          if (response && response.data && response.data.message) {
            this.$toast.error(response.data.message)
          }
        }
      } catch (error) {
        if (error.data && error.data.message) {
          this.$toast.error(error.data.message)
        } else if (error.response && error.response.data && error.response.data.message) {
          this.$toast.error(error.response.data.message)
        } else {
          this.$toast.error('Không load được thông tin quyền nhóm người dùng!')
        }
      } finally {
        this.loading(false)
      }
    },
    async add_ds_quyen_gan_nhomnd(data) {
      try {
        this.loading(true)
        let response = await GrantUserPermissionAPI.add_ds_quyen_gan_nhomnd(this.axios, data)
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          let result = response.data.data
          if (result.ds_dagan) {
            this.dataSourcesDaGan = []
            this.$nextTick(() => {
              this.dataSourcesDaGan = result.ds_dagan.slice()
            })
          }
          if (result.ds_chuagan) {
            this.dataSourcesChuaGan = []
            this.$nextTick(() => {
              this.dataSourcesChuaGan = result.ds_chuagan.slice()
            })
          }
          this.$toast.success('Gán quyền thành công')
        } else {
          if (response && response.data && response.data.message) {
            this.$toast.error(response.data.message)
          }
        }
      } catch (error) {
        if (error.data && error.data.message) {
          this.$toast.error(error.data.message)
        } else if (error.response && error.response.data && error.response.data.message) {
          this.$toast.error(error.response.data.message)
        } else {
          this.$toast.error('Đã xảy ra lỗi, không thể gán quyền cho nhóm người dùng')
        }
      } finally {
        this.loading(false)
      }
    },
    async del_ds_quyen_gan_nhomnd(data) {
      try {
        this.loading(true)
        let response = await GrantUserPermissionAPI.del_ds_quyen_gan_nhomnd(this.axios, data)
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          let result = response.data.data
          if (result.ds_dagan) {
            this.dataSourcesDaGan = []

            this.$nextTick(() => {
              this.dataSourcesDaGan = result.ds_dagan.slice()
            })
          }
          if (result.ds_chuagan) {
            this.dataSourcesChuaGan = []
            this.$nextTick(() => {
              this.dataSourcesChuaGan = result.ds_chuagan.slice()
            })
          }
          this.$toast.success('Gỡ gán quyền thành công')
          // this.lay_nhom_quyen_by_nd_id(data.nhom_nd_id)
        } else {
          if (response && response.data && response.data.message) {
            this.$toast.error(response.data.message)
          }
        }
      } catch (error) {
        if (error.data && error.data.message) {
          this.$toast.error(error.data.message)
        } else if (error.response && error.response.data && error.response.data.message) {
          this.$toast.error(error.response.data.message)
        } else {
          this.$toast.error('Đã xảy ra lỗi, không thể gỡ quyền cho nhóm người dùng')
        }
      } finally {
        this.loading(false)
      }
    },
    async onPasswordSubmitted() {
      if (this.password.value.length == 0) {
        this.$toast.warning('Vui lòng nhập mật khẩu!')
        return;
      }
      try {
        var data = await GrantUserPermissionAPI.kiemtra_quyen_chucnang(this.axios, { password: this.password.value })
        if (data.data.data && data.data.data == 1) {
          this.password.hasPermission = true
        } else {
          this.$toast.error('Mật khẩu không đúng. Vui lòng thử lại!')
        }
      } catch (e) {
        this.$toast.error(`Đã xảy ra lỗi: ${e}`)
      }
    }
  }
}
</script>
<style scoped>
</style>
