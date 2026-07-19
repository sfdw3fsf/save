<template>
  <b-modal
    ref="popupTTMucCuocKhoanMuc"
    size="huge"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
    @show="handleShowModal"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span> Chi tiết khoản mục theo mức cước
        </div>
        <div class="close -ap icon-close" @click="hideModal"></div>
      </div>

      <ActionTop :actions="actions" @onActionClick="onActionClick" />

      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title">
            <span class="icon fa fa-angle-up"></span> Thông tin mức cước
          </div>
          <div class="row">
            <div class="col-sm-12 col-12">
              <div class="info-row">
                <div class="key w100">ID mức cước</div>
                <div class="value">
                  <input
                    ref="txt_MucCuocTB_ID"
                    type="text"
                    v-model="v_muccuoctb_id"
                    class="form-control"
                    disabled
                  />
                </div>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-sm-12 col-12">
              <div class="info-row">
                <div class="key w100">Tên mức cước</div>
                <div class="value">
                  <input
                    ref="txt_TenMucCuoc"
                    type="text"
                    v-model="txt_TenMucCuoc.value"
                    class="form-control"
                    disabled
                  />
                </div>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w100">Cước TB</div>
                <div class="value">
                  <input
                    ref="txtCuocTB"
                    type="text"
                    v-model="cp_txtCuocTB"
                    @keypress="isNumber($event)"
                    class="form-control tright"
                    disabled
                  />
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w100">Cước TB phụ</div>
                <div class="value">
                  <input
                    ref="txtCuocTB_Phu"
                    type="text"
                    v-model="cp_txtCuocTB_Phu"
                    @keypress="isNumber($event)"
                    class="form-control tright"
                    disabled
                  />
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="box-form">
          <div class="legend-title">
            <div class="pull-left">
              <span class="icon fa fa-angle-up"></span> Thông tin danh sách khoản mục
            </div>
            <div class="pull-right">
              <button class="btn btn-primary float-right" @click="btnThemKhoanMuc_Click">+</button>
            </div>
            <div class="clearfix"></div>            
          </div>
          <KDataGrid
            ref="gridKhoanMuc"
            :key="gridKhoanMuc.key"
            class="table-result table-gachle"
            panelDataHeight="250"
            :columns="gridKhoanMuc.header"
            v-bind:dataSource="gridKhoanMuc.list"
            :enable-paging-server="false"
            :allowPaging="true"
            :showFilter="true"
            :showColumnCheckbox="false"
            :enabledSelectFirstRow="true"
            :commandColumn="{headerText: 'Thao tác', width: 100, cellCssClass: 'center', firstColumn: false, show: true}"
            :commands="gridKhoanMuc.commands"
            @commandClicked="gridCommand_KhoanMuc"
          />
        </div>
      </div>
    </div>
  </b-modal>
</template>
<script>
import ActionTop from "@/components/kylq_components/ActionTop.vue";
import KDataGrid from "@/components/kylq_components/KDataGrid";
import api from "../SubscriberChargePriceAPI";
export default {
  name: "frmTTMucCuocKhoanMuc",
  components: {
    ActionTop,
    KDataGrid,
  },
  props: {
    muccuoctb_id: {
      type: Number,
      default: 0,
    },
  },
  created() {
            // Thực hiện hành động cần thiết khi component được tạo ra
            console.log('Component đã được tạo ra');
            this.$root.$on('bv::modal::shown', (bvEvent, modalId) => {
            document.getElementsByClassName('modal-content').forEach(function(v) {
              v.removeAttribute('tabindex')
        })
      })
          },
  data() {
    return {
      actions: [
        {
          id: "laytt",
          name: "Lấy TT",
          active: true,
          icon_class: "one-file-attach",
        },
        {
          id: "ghilai",
          name: "Ghi lại",
          active: true,
          icon_class: "one-save",
        },
      ],
      initMucCuoc: {},
      v_muccuoctb_id: this.muccuoctb_id,
      txt_MucCuocTB_ID: { value: null },
      txt_TenMucCuoc: { value: null },
      txt_CuocTB: { value: null },
      txt_CuocTB_Phu: { value: null },
      cbb_KhoanMuc: { value: null, Items: [] },
      gridKhoanMuc: {
        list: [],
        commands: [
          { name: 'edit', title: 'Sửa', cssClass: 'pad3 mar5 lh14 icon -ap one-edit btn-primary', iconCss: 'fa fa-times', width: 100 },
          { name: 'delete', title: 'Xóa', cssClass: 'pad3 mar5 lh14 icon -ap one-trash btn-primary', iconCss: 'fa fa-times', width: 100 },
        ],
        header: [
          {
            fieldName: "KHOANMUCTC_ID",
            headerText: "ID khoản mục",
            allowFiltering: true,
            textAlign: "right",
            type: "Number",
            width: 100,
          },
          {
            fieldName: "KHOANMUCTC_ID",
            headerText: "Tên khoản mục",
            allowFiltering: true,
            width: 300,
            template: () => {
              return {
                template: {
                  template: `<div>
                      <select2 :z-index="2000" class="select2" v-model="data.KHOANMUCTC_ID"
                        :options="ds_khoanmuc" 
                        @change="onChangeSelect(data, 'KHOANMUCTC_ID')" :disabled="!data.IS_EDIT">
                      </select2>
                    </div>`,
                  data: function () {
                    return {
                      data: {},
                    };
                  },
                  computed: {
                    parent() {
                      return this.$parent.$parent.$parent.$parent.$parent;
                    },
                    ds_khoanmuc() {
                      let ds = this.parent.cbb_KhoanMuc.Items.length > 0 ? this.parent.cbb_KhoanMuc.Items : []
                      return ds
                    }
                  },
                  methods: {
                    onChangeSelect: function (val, field) {
                      this.parent.onChangeSelect(val, field)
                    },
                  },
                },
              };
            },
          },
          {
            fieldName: "TIEN_KM",
            headerText: "Tiền khoản mục",
            allowFiltering: true,
            textAlign: "right",
            type: "Number",
            format: "N0",
            template: () => {
              return {
                template: {
                  template: `<div>
                      <input type="text" class="form-control text" :value="cp_tien_km" @keypress="isNumber($event)" @change="onChangeText" :disabled="!data.IS_EDIT">
                    </div>`,
                  data: function () {
                    return {
                      data: {},
                    };
                  },
                  computed: {
                    parent() {
                      return this.$parent.$parent.$parent.$parent.$parent;
                    },
                    cp_tien_km: {
                      get() {
                        var tien_km = this.data.TIEN_KM || this.data.TIEN_KM == 0
                          ? this.numberWithCommas(this.data.TIEN_KM)
                          : "";
                        return tien_km;
                      },
                      set(value) {
                        const replaced = value.replaceAll(",", "");
                        this.data.TIEN_KM = Number(replaced);
                      },
                    },
                  },
                  methods: {
                    onChangeText: function (e) {
                      this.data.TIEN_KM = e.target.value ? Number(e.target.value) : 0
                      this.parent.onChangeText(this.data, "TIEN_KM")
                    },
                    isNumber(evt) {
                      evt = evt ? evt : window.event;
                      var charCode = evt.which ? evt.which : evt.keyCode;
                      if (charCode > 31 && (charCode < 48 || charCode > 57)) {
                        evt.preventDefault();
                      } else {
                        return true;
                      }
                    },
                    numberWithCommas(n) {
                      var parts = n.toString().split(".");
                      return (
                        parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",") +
                        (parts[1] ? "." + parts[1] : "")
                      );
                    },
                  },
                },
              };
            },
          },
          {
            fieldName: "KIEU",
            headerText: "Kiểu khoản mục",
            allowFiltering: true,
            template: () => {
              return {
                template: {
                  template: `<div>
                      <select2 :z-index="2000" class="select2" v-model="data.KIEU"
                        :options="ds_kieu" @change="onChangeSelect(data, 'KIEU')" :disabled="!data.IS_EDIT">
                      </select2>
                    </div>`,
                  data: function () {
                    return {
                      data: {},
                      ds_kieu: [{ id: 1, text: 'Thuê bao chính' }, { id: 2, text: 'Thuê bao phụ' }]
                    };
                  },
                  computed: {
                    parent() {
                      return this.$parent.$parent.$parent.$parent.$parent;
                    },           
                  },
                  methods: {
                    onChangeSelect: function (val, field) {
                      this.parent.onChangeSelect(val, field)
                    },
                  },
                },
              };
            },
          },
        ],
        value: {},
        isEnabled: true,
        checked: [],
        key: 1,
      },
    };
  },
  methods: {
    LowerCasePropertyList(obj) {
      return obj.map(function (item) {
        for (var key in item) {
          var upper = key.toLowerCase();
          // check if it already wasn't uppercase
          if (upper !== key) {
            item[upper] = item[key];
            delete item[key];
          }
        }
        return item;
      });
    },
    UpperCasePropertyList(obj) {
      return obj.map(function (item) {
        for (var key in item) {
          var upper = key.toUpperCase();
          // check if it already wasn't uppercase
          if (upper !== key) {
            item[upper] = item[key];
            delete item[key];
          }
        }
        return item;
      });
    },
    async lay_ds_khoanmuc() {
      try {
        this.cbb_KhoanMuc.Items = []
        let response = await api.sp_ds_khoanmuctc_id(
          this.axios, this.initMucCuoc.DICHVUVT_ID, 0
        );
        if (
          response &&
          response.data &&
          response.data.error_code &&
          response.data.error_code == "BSS-00000000"
        ) {
          console.log(response.data.data);
          if (response.data.data.length > 0) {
            this.cbb_KhoanMuc.Items = response.data.data.map(e => ({...e, id: e?.KHOANMUCTC_ID || e.khoanmuctc_id, text: `${e?.KHOANMUCTC_ID || e.khoanmuctc_id} - ${e?.TEN_KM || e.ten_km}`}))
          }
        } else if (response.data.error_code == "BSS-00000204") {
          this.$toast.warning('Không có danh sách khoản mục!')
        } else {
          this.$toast.error(
            "Lỗi load thông tin " + response?.data?.message_detail || response?.data?.message || ''
          );
        }
      } catch (e) {}
    },
    async frmDSKhoanMuc_Load() {
      try {
        this.gridKhoanMuc.list = []
        let response = await api.sp_ds_mctb_kmtc(
          this.axios,
          this.v_muccuoctb_id
        );
        if (
          response &&
          response.data &&
          response.data.error_code &&
          response.data.error_code == "BSS-00000000"
        ) {
          console.log(response.data.data);
          if (response.data.data.length > 0) {
            let ds = this.UpperCasePropertyList(response.data.data)
            this.gridKhoanMuc.list = ds.map(e => ({...e, IS_EDIT: false, CODE: Math.random().toString(36).slice(2, 7)}))
          }
        } else if (response.data.error_code == "BSS-00000204") {
          // this.$toast.warning('Không có thông tin mức cước theo khoản mục!')
        } else {
          this.$toast.error(
            "Lỗi load thông tin " + response?.data?.message_detail || response?.data?.message || ''
          );
        }
      } catch (e) {}
    },
    gridCommand_KhoanMuc: function (type, dataItem) {
      if (type == 'edit') {
        for (let item of this.gridKhoanMuc.list) {
          if (item.CODE == dataItem.CODE) {
            item.IS_EDIT = !item.IS_EDIT
          }
        }
      } else if (type == 'delete') {
        this.gridKhoanMuc.list = this.gridKhoanMuc.list.filter(e => e.CODE != dataItem.CODE)
      }
      this.gridKhoanMuc.key ++
    },
    onChangeText: function (dataItem, field) {
      for (let item of this.gridKhoanMuc.list) {
        if (item.CODE == dataItem.CODE) {
          item[field] = dataItem[field]
        }
      }
    },
    onChangeSelect: function (dataItem, field) {
      if (field == 'KHOANMUCTC_ID') {
        let check_dup = this.gridKhoanMuc.list.filter(e => e.KHOANMUCTC_ID == dataItem.KHOANMUCTC_ID)
        if (check_dup && check_dup.length > 1) {
          this.$toast.error('Khoản mục bị trùng, Bạn đang chọn khoản mục đã được chọn trước đó!')
        }
      }
      
      for (let item of this.gridKhoanMuc.list) {
        if (item.CODE == dataItem.CODE) {
          item[field] = dataItem[field]
        }
      }
    },
    btnThemKhoanMuc_Click: function () {
      let row = {}
      row.IS_EDIT = true
      row.CODE = Math.random().toString(36).slice(2, 7),
      row.KHOANMUCTC_ID = 0
      row.KHOANMUC = ''
      row.TIEN_KM = 0
      row.KIEU = 0
      let ds = [row]
      ds = ds.concat(this.gridKhoanMuc.list)
      this.gridKhoanMuc.list = ds
      this.gridKhoanMuc.key ++
    },
    onActionClick(action) {
      if (action.id == "laytt") {
        this.handleShowModal();
      } else if (action.id == "ghilai") {
        this.tsbtnGhiLai_Click();
      }
    },
    async tsbtnGhiLai_Click() {
      try {
        if (!this.KiemTra_DuLieu()) return;
        this.loading(true)
        let ds_km = []
        if (this.gridKhoanMuc.list && this.gridKhoanMuc.list.length > 0) {
          ds_km = this.gridKhoanMuc.list.map(e => ({
            "MUCUOCTB_ID": this.v_muccuoctb_id, 
            "KHOANMUCTC_ID": Number(e.KHOANMUCTC_ID), 
            "TIEN_KM": Number(e.TIEN_KM), 
            "KIEU": Number(e.KIEU)
          }))
          
          var mt = {
            vkieu: 2, 
            vds: JSON.stringify(ds_km)
          };
          await this.CAPNHAT(mt);
        } else {
          this.$bvModal
            .msgBoxConfirm('Bạn thật sự muốn xóa hết khoản mục TC đã gán không?', {
              title: 'Thông báo',
              size: 'sm',
              okTitle: 'Đồng ý',
              cancelTitle: 'Hủy'
            })
            .then(async (v) => {
              if (v) {                
                ds_km = [{"MUCUOCTB_ID": this.v_muccuoctb_id, "KIEU": 0 }]                
                var mt = {
                  vkieu: 1, 
                  vds: JSON.stringify(ds_km)
                };
                await this.CAPNHAT(mt);
              }
            })
            .catch((e) => {
              console.log(e)
              this.ShowError('Lỗi ' + e)
            })          
        }
      } catch (ex) {
        this.$toast.error("Có lỗi: " + ex);
      } finally {
        this.loading(false)
      }
    },
    async CAPNHAT(dt) {
      try {
        let response = await api.sp_capnhat_mctb_kmtc(this.axios, dt);
        if (
          response &&
          response.data &&
          response.data.error_code &&
          response.data.error_code == "BSS-00000000"
        ) {
          if (response.data.data == 1 || response.data.data == '1') {
            this.$toast.success("Cập nhật thông tin thành công!");
            await this.frmDSKhoanMuc_Load();
          } else {
            this.$toast.error(
              "Lỗi cập nhật " + response.data.data
            );
          }          
        } else {
          this.$toast.error(
            "Lỗi cập nhật " + (response?.data?.message_detail || response.data.message)
          );
        }
      } catch (e) {}
    },
    KiemTra_DuLieu() {
      if (!this.txt_TenMucCuoc.value || this.txt_TenMucCuoc.value.trim() == '') {
        this.$toast.error('Tên mức cước không được để trống!')
        return false
      }
      if (
        (!this.txt_CuocTB.value && this.txt_CuocTB.value != 0) ||
        Number(this.txt_CuocTB.value.toString().trim()) < 0
      ) {
        this.$toast.error('"Cước thuê bao" phải >= 0!');
        this.$refs.txtCuocTB.focus();
        return false;
      }
      // if (
      //   (!this.txt_CuocTB_Phu.value && this.txt_CuocTB_Phu.value != 0) ||
      //   Number(this.txt_CuocTB_Phu.value.toString().trim()) < 0
      // ) {
      //   this.$toast.error('"Cước thuê bao phụ" phải >= 0!');
      //   this.$refs.txtCuocTB_Phu.focus();
      //   return false;
      // }
      // if (!this.gridKhoanMuc.list || this.gridKhoanMuc.list.length == 0) {
      //   this.$toast.error('Bạn chưa thêm khoản mục cho mức cước!')
      //   return false
      // }

      return true;
    },
    isNumeric(str) {
      if (typeof str != "string") return false;
      return !isNaN(str) && !isNaN(parseFloat(str));
    },
    showModal() {
      this.$refs["popupTTMucCuocKhoanMuc"].show();
    },
    hideModal() {
      this.$refs["popupTTMucCuocKhoanMuc"].hide();
    },
    async handleShowModal() {
      //init data show modal
      this.loading(true);
      try {
        this.txt_TenMucCuoc.value = this.initMucCuoc?.MUCCUOC || null
        this.txt_CuocTB.value = this.initMucCuoc?.CUOC_TB || 0
        this.txt_CuocTB_Phu.value = this.initMucCuoc?.CUOC_TB_PHU || 0
        await this.lay_ds_khoanmuc()
        await this.frmDSKhoanMuc_Load();
      } catch (e) {
        this.loading(false);
      } finally {
        this.loading(false);
      }
    },
    isNumber(evt) {
      evt = evt ? evt : window.event;
      var charCode = evt.which ? evt.which : evt.keyCode;
      if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        evt.preventDefault();
      } else {
        return true;
      }
    },
    numberWithCommas(n) {
      var parts = n.toString().split(".");
      return (
        parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",") +
        (parts[1] ? "." + parts[1] : "")
      );
    },
  },
  computed: {
    cp_txtCuocTB: {
      get() {
        if (Number(this.txt_CuocTB.value) > 1000000000) {
          this.$toast.error('"Cước thuê bao" phải nhỏ hơn 1,000,000,000!');
          this.txt_CuocTB.value = 999999999;
          this.$refs.txtCuocTB.focus();
        }
        var txtCuocTB = this.txt_CuocTB.value
          ? this.numberWithCommas(this.txt_CuocTB.value.toString())
          : this.txt_CuocTB.value == 0
          ? "0"
          : "";
        return txtCuocTB;
      },
      set(value) {
        const replaced = value.replaceAll(",", "");
        this.txt_CuocTB.value = replaced ? Number(replaced) : null;
      },
    },
    cp_txtCuocTB_Phu: {
      get() {
        if (Number(this.txt_CuocTB_Phu.value) > 1000000000) {
          this.$toast.error('"Cước thuê bao phụ" phải nhỏ hơn 1,000,000,000!');
          this.txt_CuocTB_Phu.value = 999999999;
          this.$refs.txtCuocTB_Phu.focus();
        }
        var txtCuocTB_Phu = this.txt_CuocTB_Phu.value
          ? this.numberWithCommas(this.txt_CuocTB_Phu.value.toString())
          : this.txt_CuocTB_Phu.value == 0
          ? "0"
          : "";
        return txtCuocTB_Phu;
      },
      set(value) {
        const replaced = value.replaceAll(",", "");
        this.txt_CuocTB_Phu.value = replaced ? Number(replaced) : null;
      },
    },
  },
  mounted() {},
  watch: {
    muccuoctb_id(val) {
      this.v_muccuoctb_id = val;
    },
  },
};
</script>
<style>
.btn-second {
	border-color: #0d6efd;
}
.modal .modal-huge {
  max-width: 95% !important;
  width: 100% !important;
}
</style>
