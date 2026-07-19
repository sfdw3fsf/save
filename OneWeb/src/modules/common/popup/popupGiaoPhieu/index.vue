<template>
<div :class="{'popup-box':isPopup}" >
    <breadcrumb :header="header" v-if="!isPopup" />
    <div class="popup-header" v-if="isPopup">
      <div class="title"><span class="icon one-notepad"></span>Giao phiếu</div>
      <div class="close -ap icon-close" data-dismiss="modal" @click.prevent="terminateForm">
      </div>
    </div>
    <div class="list-actions-top">
      <ul class="list">
        <li @click="giaophieu" class="cursor-pointer">
          <a> <span class="icon one-file-attach"></span>Giao phiếu</a>
        </li>

        <li @click="closeModal" class="cursor-pointer">
          <a class="text-danger">
            <span class="icon nc-icon-glyph ui-1_circle-remove"></span>Thoát
          </a>
        </li>
      </ul>
    </div>

    <div class="page-content">
      <div class="row">
        <div class="col-sm-6 col-12">
          <div class="info-row">
            <div class="key w100  fw6">Đơn vị chủ trì</div>
            <div class="value">
              <div class="select-custom" ref="donvi">
                <SelectExt
                  v-model="donvichutris.value"
                  :dataSource="donvichutris.list"
                  :allowFiltering="true"
                  dataTextField="text"
                  dataValueField="id"
                ></SelectExt>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="row">
        <div class="col-sm-6 col-12">
          <div class="info-row">
            <div class="key w100  fw6">Đơn vị phối hợp</div>
            <div class="value">
              <div class="select-custom">
                <SelectExt
                  v-model="donviphoihops.value"
                  :dataSource="donviphoihops.list"
                  :allowFiltering="true"
                  dataTextField="text"
                  dataValueField="id"
                  @change="onChangeDonViPhoiHop()"
                ></SelectExt>
              </div>
            </div>
          </div>
        </div>

        <div class="col-sm-6 col-12">
          <div class="info-row">
            <div class="key w100  fw6">Người xử lý</div>
            <div class="value">
              <div class="select-custom">
                <SelectExt
                  v-model="users.value"
                  :dataSource="users.list"
                  :allowFiltering="true"
                  dataTextField="ten_nv"
                  dataValueField="nhanvien_id"
                ></SelectExt>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class='row'>
        <div class="col-sm-12 col-12">
          <div class="info-row">
            <div class="key w100  fw6">Nội dung giao</div>
            <div class="value">
              <textarea class="form-control" style="height: 100px" v-model="noidung"></textarea>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import api from './api'

  export default {
    name: 'PopupThongTinUser',
    props: {
      khachHangId: {
        type: Number,
        default: 263205
      },
      hdkh_id: {
        type: Number,
        default: 0
      },
      hdkh_id: {
        type: Number,
        default: 0
      },
      thongTinThueBao: {
        type: Object,
        default: {}
      },
      tinh_id: {
          type: Number,
          default: 0
      },
      isPopup: {
          type: Boolean,
          default: true
      },
    },
    data() {
      return {
        showModal: false,
        donvichutris: {
          value: '',
          list: []
        },
        users: {
          value: '',
          list: []
        },
        donviphoihops: {
          value: '',
          list: []
        },
        isPopup: {
          tyle: Boolean,
          default: false
        },
        noidung: ''
      }
    },
    async mounted() {
      this.$root.loading(true)
      console.log(11111)
      console.log(this.thongTinThueBao)
      
      await this.getDanhSachdonvichutri()
      this.$root.loading(false)
    },
    watch: {
      'donviphoihops.value': async function(newval, oldval) {
        if (newval) {
          await this.onChangeDonViPhoiHop();
        }
      },
    },
    methods: {
      terminateForm() {
        this.$emit('form-close');
      },
      async getDanhSachdonvichutri() {
        const payload = {
            "phanvung_id": this.tinh_id
        }
        const response = await api.getDanhSachDonvichutri(this.$root.axios, payload)
        const { error_code, data } = response.data

        if (error_code === 'BSS-00000000') {
          let arr = [];
          arr =  data.DONVI?.map(e=>{
            return { id: e.donvi_id,text: e.ten_dv}
          });

          this.donvichutris.list = arr;
          this.donviphoihops.list = arr;

          if (arr.length > 0) {
            this.donvichutris.value = this.thongTinThueBao.donvi_chutri
            this.donviphoihops.value = arr[0].id
          }
        }
      },
      async onChangeDonViPhoiHop() {
        const response = await api.getDSUser(this.$root.axios, {
          donvi_id : this.donviphoihops.value,
          phanvung_id: this.tinh_id
        });

        const { error_code, data } = response.data

        if (error_code === 'BSS-00000000') {
          this.users.list = response.data.data;
          // if (response.data.data.length > 0) {
          //   this.users.value = response.data.data
          // }
        }
      },
       closeModal() {
        this.$emit('form-close');
      },
      async giaophieu() {
        this.$root.loading(true)
        try {
          if (this.donvichutris.value == '' || this.donvichutris.value == 0) return this.$root.$toast.error('Đơn vị chủ trì không để trống !');
          if (this.donviphoihops.value == '' || this.donviphoihops.value == 0) return this.$root.$toast.error('Đơn vị phối hợp không để trống !');
          if (this.users.value == '' || this.users.value == 0) return this.$root.$toast.error('Người xử lý không để trống !');
          if (this.noidung == '') return this.$root.$toast.error('Nội dung giao không để trống !');

          let playload =  {
            "dv_giao": this.donvichutris.text,
            "donvi_nhan_id": this.donviphoihops.value,
            "nd_thuchien": this.noidung,
            "donvi_giao_id": this.donvichutris.value,
            "nhanvien_th_id": this.users.value,
            "doituong_tb": this.thongTinThueBao.doituong_tb,
            "dichvuvt_id": this.thongTinThueBao.dichvuvt_id,
            "loaihinh": this.thongTinThueBao.loaihinh,
            "diachi_ld": this.thongTinThueBao.diachi_ld,
            "ma_gd": this.thongTinThueBao.ma_gd,
            "diachi_tb": this.thongTinThueBao.diachi_tb,
            "hdkh_tinh_id": this.thongTinThueBao.hdkh_tinh_id,
            "phieu_tinh_id": this.thongTinThueBao.phieu_tinh_id,
            "tinh_id": this.thongTinThueBao.tinh_id,
            "nguoigiao": this.thongTinThueBao.nguoigiao,
            "hdtb_tinh_id": this.thongTinThueBao.hdtb_id,
            "loaihd_id": this.thongTinThueBao.loaihd_id,
            "ttph_id": this.thongTinThueBao.ttph_id,
            "ngay_yc": this.thongTinThueBao.ngay_yc,
            "huonggiao_id": this.thongTinThueBao.huonggiao_id,
            "dichvu": this.thongTinThueBao.dichvu,
            "tinh_ld": this.thongTinThueBao.tinh_ld,
            "kieu_yc": this.thongTinThueBao.kieu_yc,
            "diachi_kh": this.thongTinThueBao.diachi_kh,
            "loaitb_id": this.thongTinThueBao.loaitb_id,
            "phanvung_id": this.tinh_id,
            "ma_kh": this.thongTinThueBao.ma_kh,
            "tdtt_db_id": this.thongTinThueBao.tdtt_db_id
          }

          console.log(playload);

          const response = await api.giaophieu(this.$root.axios, playload);

          const { error_code, data } = response.data

          if (error_code === 'BSS-00000000') {
            return this.$root.$toast.success("Thành công.")
          } else {
            return this.$root.$toast.error("Có lỗi xảy ra : " + data.message);
          }
        } catch (error) {
          return this.$root.$toast.error("Có lỗi xảy ra : " + error);
        } finally {
          this.$root.loading(false)
        }
      }
    } 
  }
</script>
 
<style scoped>
  .cursor-pointer {
    cursor: pointer;
  }

  .fullscreen-modal .modal-dialog {
    max-width: 100%;
    margin: 0;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
    height: 100vh;
    padding: 25px;
    display: flex;
    position: fixed;
    z-index: 100000;
  }
</style>
