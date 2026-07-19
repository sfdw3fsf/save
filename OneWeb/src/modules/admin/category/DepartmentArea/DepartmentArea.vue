<template>
  <div class="main-wrapper">
    <KBreadCrumb title="Cập nhật danh sách đơn vị - Địa danh" />
    <ActionTop :actions="actions" @onActionClick="onActionClick" />
    <div class="page-content">
      <div class="row">
        <ThongTinDonVi ref="thongtindonvi" />
        <ThongTinDiaDanh ref="thongtindiadanh" />
      </div>
    </div>
    <Footer />
    <!-- Modal -->
    <LichSuSuaDoiModal ref="lichSuSuaDoiModal" :dataLS="dataLSThayDoi" />
  </div>
</template>
<script>
import Footer from '@/components/FooterStyle.vue'
import KBreadCrumb from '@/components/kylq_components/mirana/KBreadCrumb.vue'
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import ThongTinDonVi from './ThongTinDonVi.vue'
import ThongTinDiaDanh from './ThongTinDiaDanh.vue'
import LichSuSuaDoiModal from './LichSuSuaDoiModal.vue'
import DepartmentAreaAPI from './DepartmentAreaAPI'
export default {
  name: 'DepartmentArea',
  components: {
    KBreadCrumb,
    Footer,
    ActionTop,
    ThongTinDonVi,
    ThongTinDiaDanh,
    LichSuSuaDoiModal
  },
  data() {
    return {
      actions: [
        {
          id: 'capnhat',
          name: 'Cập nhật',
          active: true,
          icon_class: 'one-reload1'
        },
        {
          id: 'napdl',
          name: 'Nạp DL',
          active: true,
          icon_class: 'one-vt'
        },
        {
          id: 'xoanap',
          name: 'Xoá nạp',
          active: true,
          icon_class: 'one-trash'
        },
        {
          id: 'gandvql',
          name: 'Gán ĐVQL',
          active: true,
          icon_class: 'one-gan'
        },
        {
          id: 'lichsu_suadoi',
          name: 'Lịch sử sửa đổi',
          active: true,
          icon_class: 'one-reload'
        },
        {
          id: 'huongdan',
          name: 'Hướng dẫn',
          active: true,
          icon_class: 'one-book1'
        }
      ],
      dataLSThayDoi: []
    }
  },
  methods: {
    onActionClick(action) {
      if (action.id == 'capnhat') {
        this.CapNhat()
      } else if (action.id == 'napdl') {
        this.NapDL()
      } else if (action.id == 'xoanap') {
        this.XoaNap()
      } else if (action.id == 'gandvql') {
        this.GanDVQL()
      } else if (action.id == 'huongdan') {
      } else if (action.id == 'lichsu_suadoi') {
        this.LichSuSuaDoi()
      }
    },
    // Handle action
    CapNhat() {
      this.$refs.thongtindonvi.capNhatDuLieu()
    },
    async LichSuSuaDoi() {
      let loaidv_id = this.$refs.thongtindonvi.loai_donvi_selected ? this.$refs.thongtindonvi.loai_donvi_selected : 0
      let donvi_id = this.$refs.thongtindonvi.getDonViIDSleceted()
      this.dataLSThayDoi = await this.sp_lay_ds_donvi_px_log({
        donvi_id: donvi_id,
        loaidv_id: loaidv_id
      })
      console.log('LichSuSuaDoi ds', this.dataLSThayDoi)
      this.$refs.lichSuSuaDoiModal.showModal()
    },
    NapDL() {
      //var danhsach=this.$refs.personal.$data;
      if (this.$refs.thongtindonvi.getDonViIDSleceted() != null) {
        let donvi_ql = this.$refs.thongtindonvi.ds_donvi_ql.find((x) => x.DONVI_ID == this.$refs.thongtindonvi.donvi_ql_selected)
        if (!donvi_ql) {
          this.$toast.error('Bạn chưa chọn đơn vị quản lý để gán địa danh')
          return
        }
        var danhsach = this.$refs.thongtindiadanh.getDataNap(donvi_ql)
        //this.$refs.thongtindiadanh.cleanDataGan()

        if (danhsach.length > 0) {
          this.$refs.thongtindonvi.nhanDuLieuNap(danhsach)
        } else {
          this.$toast.error('Không có dữ liệu địa danh được gán')
        }
      } else {
        this.$toast.error('Hãy chọn đơn vị để Nạp DL')
      }
    },
    XoaNap() {
      this.$refs.thongtindonvi.xoaNap()
    },
    GanDVQL() {
      this.$refs.thongtindonvi.ganDVQL()
    },
    HuongDan() {
      console.log('HuongDan')
    },
    async sp_lay_ds_donvi_px_log(data) {
      try {
        this.loading(true)
        let response = await DepartmentAreaAPI.sp_lay_ds_donvi_px_log(this.axios, data)
        this.loading(false)
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          return response.data.data
        } else {
          return []
        }
      } catch (e) {
        this.loading(false)
        if (e.data && e.data.message) {
          this.$toast.error(e.data.message)
        } else if (e.response && e.response.data && e.response.data.message) {
          this.$toast.error(e.response.data.message)
        } else {
          this.$toast.error('Đã có lỗi khi lấy lịch sử sửa đổi')
        }
        return []
      }
    }
  }
}
</script>
<style>
.select2-selection {
  overflow: hidden !important;
}
.select2-container .select2-selection--single .select2-selection__rendered {
  white-space: normal !important;
  word-break: break-all !important;
}
/* asset css select2 form */
.select2-container {
  width: 100% !important;
}

.select2-container--default .select2-selection--single {
  border-color: #e0e0e0;
  outline: none;
}

.select2-dropdown {
  border: 1px solid #e0e0e0;
}

.select2-container--default .select2-selection--single .select2-selection__arrow b {
  font-family: 'FontAwesome';
  border: 0px;
  top: 0px;
  height: 1.428rem;
  bottom: 0px;
  line-height: 1.428rem;
  left: 5px;
  margin: auto;
}
.select2-container--default .select2-selection--single .select2-selection__arrow b:before {
  content: '';
}

.select2-container--default .select2-search--dropdown .select2-search__field {
  border: 0px;
  outline: none;
}
.select2-container--default.select2-container--disabled .select2-selection--single {
  background-color: #e9ecef !important;
}
</style>
