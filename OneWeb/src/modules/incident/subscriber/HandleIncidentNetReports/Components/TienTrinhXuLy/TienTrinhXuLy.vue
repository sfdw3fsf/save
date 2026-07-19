<template src="./TienTrinhXuLy.html"></template>
<script>

import Vue from 'vue'
import api from '../../api.js'
import apiHelper from '../../api.helper.js'
export default {
  name: 'TienTrinhXuLy',
  props: {
    baoHongItem: {
      type: Object,
    },
    
  },
  components: {
  },

  data() {
    return {
      columnsTTXL: [
        { fieldName: "nguoi_cn", headerText: "Người CN", textAlign: "left", width: "200px" },
        { fieldName: "ngay_cn", headerText: "Ngày CN", textAlign: "left", width: "200px" },
        // { fieldName: "tientrinhbh_id", headerText: "ID", textAlign: "left", autoFit: "true" },
        { fieldName: "noidung", headerText: "Nội dung", textAlign: "left", autoFit: "true" },
        { fieldName: "donvi_cn", headerText: "Đơn vị", textAlign: "left", autoFit: "true" },

        {
          fieldName: "btn_SMS",
          headerText: "SMS",
          textAlign: "center",
          autoFit: "true",
          template: function () {
            return {
              template: Vue.component("btnEdit_Template", {
                template: `<button class="btn btn-main pad4 lh14 w60 btn-primary" @click="btnSMS_ButtonClick">
                                  <span class="fa fa-commenting-o"></span>
                              </button>`,
                methods: {
                  btnSMS_ButtonClick() {
                    this.$parent.$parent.$parent.handleShowSendSMS()
                  }
                }
              }),
            };
          },
        },

        {
          fieldName: "",
          headerText: "Sửa",
          textAlign: "center",
          autoFit: "true",
          template: function () {
            return {
              template: Vue.component("btnEdit_Template", {
                template: `<button class="btn btn-main pad4 lh14 w60 btn-primary" @click="btnUpdate_ButtonClick">
                                  <span class="fa fa-pencil-square"></span>
                              </button>`,
                data: function () {
                  return { data: {} };
                },
                methods: {
                  btnUpdate_ButtonClick() {
                    this.$parent.$parent.$parent.handleShowUpdateForm(this.data)
                  },
                },
              }),
            };
          },
        },

        {
          fieldName: "btnXoa",
          headerText: "Xóa",
          textAlign: "center",
          autoFit: "true",
          template: function () {
            return {
              template: Vue.component("btnEdit_Template", {
                template: `<button class="btn btn-main pad4 lh14 w60 btn-primary" @click="btnDeleteTT_ButtonClick">
                                  <span class="fa fa-trash"></span>
                              </button>`,
                data: function () {
                  return { data: {} };
                },
                methods: {
                  btnDeleteTT_ButtonClick() {
                    this.$parent.$parent.$parent.handleDeleteTienTrinh(this.data);
                  },
                },
              }),
            };
          },
        },
      ],
      gridTienTrinhXL: {
        dataSource: []
      }
    }
  },
  methods: {

    async getTienTrinhXuLy(baoHong) {

      if (!baoHong) return;

      if (!baoHong.baohong_id || !baoHong.baohong_id_thicong) return;

      this.loading(true);
      try {
        const vPhanVungId = baoHong.tinh_id != 0 ? baoHong.tinh_id : this.$root.token.getPhanVungID();
        const vBaoHongId = baoHong.tinh_id != 0 ? baoHong.baohong_id_thicong : baoHong.baohong_id;
        const resTienTrinhBH = await api.getDSTienTrinhBH(this.axios, vBaoHongId, vPhanVungId);
        this.gridTienTrinhXL.dataSource = apiHelper.getDataFromResponseApiReturnArray(resTienTrinhBH) || [];
      } catch (err) {
        console.log(err)
      } finally {
        this.loading(false);
      }
    },

    onReloadComponent(){
       this.getTienTrinhXuLy(this.baoHongItem);
    },
    onRowSelected(item){
      // console.log("tien trinh selected", item)
      this.$emit('on-row-selected', item.data);
    },

    handleShowUpdateForm(data) {
      this.$emit('show-form-update-event', data);
    },

    async handleDeleteTienTrinh(data) {

      if (!data) return;

      const userCN = this.$auth.getUserName();
      if(data.nguoi_cn != userCN)
        return  this.$toast.error("Bạn không có quyền xoá tiến trình này!");

      await this.$bvModal
        .msgBoxConfirm(
          "Bạn có thực sự muốn xóa tiến trình xử lý đang chọn không ?",
          {
            okVariant: "success",
            okTitle: "Đồng ý",
            cancelTitle: "Hủy",
            centered: true,
          }
        )
        .then(async (value) => {

          if (value) {
            const params = {
              p_ds_para: JSON.stringify({
                TIENTRINHBH_ID: data.tientrinhbh_id,
                BAOHONG_ID: data.baohong_id,
              }),
              phanvung_id:   data.phanvung_id
            }
            await this.deleteTienTrinh(params);
            
          }
        })
        .catch((err) => {
          console.log(err)
        });
    },

    async deleteTienTrinh(param) {

      try {
        this.loading(true);
        let response = await api.postDeleteTienTrinh(this.axios, param);
        if(apiHelper.isResponseApiSuccess(response)){
          this.$toast.success("Xóa tiến trình thành công!");
          this.getTienTrinhXuLy(this.baoHongItem);
          return;
        }
        this.$toast.error("Xoá tiến trình không thành công");
  
      } catch (error) {
        console.log(error);
        this.$toast.error(error.data.message);
      } finally {
        this.loading(false);
      }
    },

    handleShowSendSMS() {
      this.$parent.$refs.dlg_GuiSmsTienTrinh.initModal()
    },


  },
  watch: {
    baoHongItem(value) {
      if (value) {
         this.getTienTrinhXuLy(value)
      }
    }
  }


}
</script>
<style scoped>
e-pagerdropdown input {
  display: none;
}
</style>
