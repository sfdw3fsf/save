<template src="./index.html"></template>

<script>
import { mapActions, mapState, mapGetters } from 'vuex'
import breadcrumb from '@/components/breadcrumb'
import moment from 'moment'
import ThongTinChungVrfModal from './modals/thongtin-chung'
export default {
  components: {
    ThongTinChungVrfModal
  },
  data: function() {
    return {
      vrfDataList: [],
      dsLichSu: [],
      currentTab: 1,
      dsSelected: [],
    }
  },
  computed: {
    grdItems: function() {
      return this.$refs.grdItems
    },
    grdHistory: function() {
      return this.$refs.grdHistory
    }
  },
  watch: {
  },
  created: async function() {
    try {
      this.$root.showLoading(true)
      await this.reloadVrfList()

    } finally {
      this.$root.showLoading(false)
    }
  },
  methods: {
    getHieuLucText: function(value) {
      return value == 1 ? 'Hiệu lực' : 'Không hiệu lực'
    },
    getColumnHieuLucTemplate: function() {
      return function() {
        return {
          template: {
            template: `<span>{{ getText() }}</span>`,
            methods: {
              getText: function() {
                return this.$parent.$parent.$parent.getHieuLucText(this.data.HIEU_LUC)
              }
            }
          }
        }
      }
    },
    getDsLichSu: async function () {
      try {
        let rs = await this.$root.context.post("/web-ecms/danhmuc-quanly-vrf/get-danhmuc-vrf", {
          loaiDanhMuc: "DS-LICHSU-VRF",
          thamSo1:null,
        });
        this.dsLichSu = rs.data || [];
        this.dsLichSu.forEach((item, index) => {
          item.STT = index + 1; // Thêm trường STT
        });
      } catch (e) {
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail);
        }
      }
    },

    getVrfItems: async function() {
      let result = []
      try {
        let rs = await this.$root.context.post('/web-ecms/danhmuc-quanly-vrf/get-danhmuc-vrf', {
          loaiDanhMuc: 'DS-VRF'
        })
        result = rs.data
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result
    },
    deleteVrf: async function (id) {
      let result = false;
      try {
        let rs = await this.$root.context.post('/web-ecms/danhmuc-quanly-vrf/delete', { vrfId: id });
        result = rs.data;
      } catch (e) {
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail);
        }
      }
      return result;
    },


    reloadVrfList: async function () {
      this.vrfDataList = await this.getVrfItems();
      await this.getDsLichSu();
    },

    grdItems_RowSelected: async function(args) {
    },
    grdItems_RowDeselected: async function(args) {
      let items = this.grdItems.getSelectedRecords();
      if (!(items == null || items.length == 0)) {
        try {
          this.$root.showLoading(true);
          //this.setCurrentItem(items[0]);
        } finally {
          this.$root.showLoading(false);
        }
      }
    },
    onSelectRowCheckbox(itemsCheck) {
      this.dsSelected = itemsCheck
    },

    onSearch() {
      try {
        this.$root.showLoading(true);
        this.reloadVrfList();
      } finally {
        this.$root.showLoading(false);
      }
    },
    onVrfAllocated() {
      this.reloadVrfList();
    },

    onAddNew() {
      this.$refs.modalThongTinChungVrf.openModal({ mode: 'ADD' });
    },
    onEdit(vrfId) {
      this.$refs.modalThongTinChungVrf.openModal({ mode: 'EDIT', vrfId: vrfId });
    },

    onDelete: async function() {

      if (this.dsSelected.length <= 0) {
        console.log(this.dsSelected.length + ' items to delete');
        this.$toast.error('Chưa chọn danh mục cần xoá.')
        return
      }
      let items = this.grdItems.getSelectedRecords();
      let msg = `Bạn có muốn xóa dữ liệu không?`
      let flag = await this.$confirm(msg, "Xác nhận",
        {
          confirmButtonText: "Đồng ý",
          cancelButtonText: "Không đồng ý",
          type: "warning",
          dangerouslyUseHTMLString: true,
        }
      )
        .then((s) => true)
        .catch((e) => {
          return false;
        });
      if (flag) {
        try {
          this.$root.showLoading(true)
          let promises = []
          for (let item of this.dsSelected) {
            console.log("vrfid " + item.VRF_ID);
            promises.push(await this.deleteVrf(item.VRF_ID))

          }

          if ((await Promise.all(promises)).some(x => x == false)) {
            this.$toast.error(`Xoá không thành công`)
            return
          } else {
            this.$toast.success(`Đã xoá thành công`)
          }
          this.reloadVrfList()
        } finally {
          this.$root.showLoading(false);
        }
      }
    },
    // Hàm xóa 1 item (từ button trong row)
    onDeleteSingle: async function(vrfId) {
      let msg = `Bạn có muốn xóa vrf này không?`
      let flag = await this.$confirm(msg, "Xác nhận", {
        confirmButtonText: "Đồng ý",
        cancelButtonText: "Không đồng ý",
        type: "warning",
        dangerouslyUseHTMLString: true,
      })
      .then(() => true)
      .catch(() => false);

      if (flag) {
        try {
          this.$root.showLoading(true);
          console.log("Deleting vrfId:", vrfId);

          let result = await this.deleteVrf(vrfId);

          if (result) {
            this.$toast.success(`Đã xóa thành công`);
            await this.reloadVrfList();
          } else {
            this.$toast.error(`Xóa không thành công`);
          }
        } catch (e) {
          console.error('Error deleting:', e);
          this.$toast.error(`Có lỗi xảy ra khi xóa`);
        } finally {
          this.$root.showLoading(false);
        }
      }
    },


    actionColumn(parent) {
      return function() {
        return {
          template: {
            template: `<div class="action-buttons">
               <button class=" btn btn-sm btn-outline-primary ml-1" title="Chỉnh sửa" @click="editItem()">
                  <i class="fa fa-edit"></i>
                </button>
                <button class="btn btn-sm btn-outline-danger ml-1" title="Xóa" @click="deleteItem()" >
                  <i class="fa fa-trash"></i>
                </button>
              </div>`,
            methods: {
              editItem() {
                parent.onEdit(this.data.VRF_ID);
              },
              deleteItem: async function() {
                await parent.onDeleteSingle(this.data.VRF_ID);
              }
            }
          }
        }
      }
    }
  }
}
</script>
<style>
.check-action {
  top: 3px;
}

.e-rowcell .check-action input[type='checkbox']:checked:disabled + .name:before {
  background: #fff;
  border-color: #0176ff;
  color: #0176ff;
}

.e-rowcell .check-action input[type='checkbox']:disabled + .name:before {
  background: #fff;
  border-color: #0176ff;
}
</style>
