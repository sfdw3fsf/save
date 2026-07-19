<template>
  <div class="box-form">
    <div class="legend-title">Thông tin nhân viên</div>
    <div class="row">
      <div class="col-sm-9 col-12">
        <div class="row">
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w80">
                Mã NV
                <span class="required">*</span>
              </div>
              <div class="value">
                <div class="e-input-group" :class="{'e-error': formData.showError && !formData.maNhanVien.trim()}">
                  <input class="e-input highlight" type="text" v-model="formData.maNhanVien" @keypress.enter="searchStaffByCode">
                </div>
              </div>
            </div>
          </div>
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w80">Vị trí</div>
              <div class="value">
                <div class="select-custom">
                  <ejs-dropdownlist
                    popupWidth="auto"
                    :dataSource="danhSachViTri"
                    :allowFiltering='true'
                    :fields="{ text: 'vitri', value: 'vitri_id' }"
                    @filtering="onFilteringDropDownList($event, danhSachViTri, 'vitri')"
                  ></ejs-dropdownlist>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="info-row">
          <div class="key w80">Tên NV
            <span class="required">*</span>
          </div>
          <div class="value">
            <div class="e-input-group" :class="{'e-error': formData.showError && !formData.tenNhanVien.trim()}">
              <input class="e-input" type="text" v-model="formData.tenNhanVien" @keypress.enter="searchStaffByName">
            </div>
          </div>
        </div>
        <div class="info-row">
          <div class="key w80">Tên TN
            <span class="required">*</span>
          </div>
          <div class="value">
            <div class="e-input-group" :class="{'e-error': formData.showError && !formData.tenThuNgan.trim()}">
              <input class="e-input" type="text" v-model="formData.tenThuNgan">
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w80">Ngày sinh
                <span class="required">*</span>
              </div>
              <div class="value">
                <div :class="{'e-error e-input-group': formData.showError && !formData.ngaySinh}">
                  <ejs-datepicker v-model="formData.ngaySinh"></ejs-datepicker>
                </div>
              </div>
            </div>
          </div>
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w80">Điện thoại
                <span class="required">*</span>
              </div>
              <div class="value">
                <div class="e-input-group input-group-sdt" :class="{'e-error': formData.showError && !formData.dienThoai.trim()}">
                  <input class="e-input input-sdt" @input="checkSTD"  type="number" v-model="formData.dienThoai">
                  <Icon icon="entypo:check" v-if="formData.checkDT"  style="color: #00ff40" />
                  <Icon icon="maki:cross" v-else  style="color: #ff0000" />
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="info-row">
          <div class="key w80">Địa chỉ
            <span class="required">*</span>
          </div>
          <div class="value">
            <div class="input-more-button">
              <button class="btn" v-b-modal.popupDiaChiNhanVien>
                <span class="-ap icon-more_horiz"></span>
              </button>
              <div class="e-input-group" :class="{'e-error': formData.showError && !formData.diaChi.trim()}">
                <input class="e-input" type="text" v-model="formData.diaChi">
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w80">Tiền ký quỹ</div>
              <div class="value">
                <ejs-numerictextbox
                  locale='vi-VN'
                  format="n"
                  :showSpinButton="false"
                  v-model="formData.tienKyQuy" />
              </div>
            </div>
          </div>
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w80">Mail VNPT</div>
              <div class="value">
                <input type="text" class="form-control" v-model="formData.mailVNPT"/>
              </div>
            </div>
          </div>
        </div>
        <div class="info-row">
          <div class="key w80">Đơn vị
            <span class="required">*</span>
          </div>
          <div class="value">
            <div class="select-custom"
                 :class="{'e-error e-input-group': formData.showError && !formData.selectedDonVi}">
              <ejs-dropdownlist
                popupWidth="auto"
                :dataSource="danhSachDonVi"
                :allowFiltering='true'
                :fields="{ text: 'ten_ht', value: 'donvi_id' }"
                v-model="formData.selectedDonVi"
                @filtering="onFilteringDropDownList($event, danhSachDonVi, 'ten_ht')"
              ></ejs-dropdownlist>
            </div>
          </div>
        </div>
        <div class="info-row">
          <div class="key w80">Đơn vị DL</div>
          <div class="value">
            <div class="select-custom">
              <ejs-dropdownlist
                popupWidth="auto"
                :dataSource="danhSachDonViDL"
                :allowFiltering='true'
                :fields="{ text: 'ten_dv', value: 'donvi_id' }"
                v-model="formData.selectedDonViDL"
                @filtering="onFilteringDropDownList($event, danhSachDonViDL, 'ten_dv')"
              ></ejs-dropdownlist>
            </div>
          </div>
        </div>
      </div>
      <div class="col-sm-3 col-12">
        <div class="check-action mart7 marb10">
          <input type="checkbox" class="check" v-model="formData.gioiTinh"/>
          <span class="name">Nam</span>
        </div>
        <div class="info-row">
          <div class="key">Chữ ký</div>
          <div class="value w30">
            <button class="btn btn-second" @click="uploadFile">
              <span class="-ap icon-more_horiz"></span>
            </button>
          </div>
        </div>
        <div
          class="form-control nocorner"
          style="
            height: 200px;
            display: flex;
            align-items: center;
            justify-content: center;
          "
        >
          <img :src="formData.chuKy" alt="Chữ ký">
        </div>
      </div>
    </div>
    <div class="row">
      <div class="col-sm-4 col-12">
        <div class="info-row">
          <div class="key w80">Số thẻ</div>
          <div class="value">
            <input type="text" class="form-control" v-model="formData.soThe"/>
          </div>
        </div>
      </div>
      <div class="col-sm-4 col-12">
        <div class="info-row">
          <div class="key w80">Mã kênh</div>
          <div class="value">
            <input type="text" class="form-control" v-model="formData.maKenh"/>
          </div>
        </div>
      </div>
      <div class="col-sm-4 col-12">
        <div class="info-row">
          <div class="key w80">Hợp đồng</div>
          <div class="value">
            <div class="select-custom">
              <ejs-dropdownlist
                popupWidth="auto"
                :dataSource="danhSachHopDong"
                :allowFiltering='true'
                :fields="{ text: 'hinhthuc_hd', value: 'hthd_id' }"
                v-model="formData.selectedHopDong"
                @filtering="onFilteringDropDownList($event, danhSachHopDong, 'hinhthuc_hd')"
              ></ejs-dropdownlist>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="row">
      <div class="col-sm-4 col-12">
        <div class="info-row">
          <div class="key w80">User ví</div>
          <div class="value">
            <input type="text" class="form-control" v-model="formData.userVi"/>
          </div>
        </div>
      </div>
      <div class="col-sm-8 col-12">
        <div class="info-row">
          <div class="key w80">Ghi chú</div>
          <div class="value">
            <input type="text" class="form-control" v-model="formData.ghiChu"/>
          </div>
        </div>
      </div>
    </div>
    <div class="legend-title mart10">Đơn vị đã thực hiện</div>
    <div class="info-row">
      <div class="key w80">Loại đơn vị
      </div>
      <div class="value">
        <div class="select-custom">
          <ejs-dropdownlist
            popupWidth="auto"
            :dataSource="danhSachLoaiDonVi"
            allowFiltering='true'
            :fields="{ text: 'ten_loaidv', value: 'loaidv_id' }"
            v-model="formData.selectedLoaiDonVi"
            @change="onChangeLoaiDonVi"
            @filtering="onFilteringDropDownList($event, danhSachLoaiDonVi, 'ten_loaidv')"
          ></ejs-dropdownlist>
        </div>
      </div>
    </div>
    <modal-chon-dia-chi :isStrictMode="false" :data="{}" modalId="popupDiaChiNhanVien" @xacnhan="onChangeDiaChi"/>
  </div>
</template>

<script>
import ModalChonDiaChi from '@/modules/contract/setup/InstallNewSubs/Popups/popupChonDiaChi'
import { pagingAndFilter } from '../mixins/pagingAndFilter'
import {Icon} from '@iconify/vue2'

export default {
  mixins: [pagingAndFilter],
  components: {
    ModalChonDiaChi, Icon
  },
  props: {
    danhSachDonVi: Array,
    danhSachDonViDL: Array,
    danhSachHopDong: Array,
    danhSachLoaiDonVi: Array,
    danhSachViTri: Array,
    formData: Object
  },
  data: () => ({

  }),
  computed: {},
  watch: {
    'formData.dienThoai': function (val) {
      if(val.length === 10 && val[0] == 0)
      this.formData.checkDT = true;
      else this.formData.checkDT = false;
    }
  },
  methods: {
    onChangeDiaChi (e) {
      this.formData.diaChi = e.diachimoi
    },
    onChangeLoaiDonVi () {
      this.$emit('onChangeLoaiDonVi')
    },
    uploadFile () {
      let input = document.createElement('input')
      const typeAccept = 'image/png, image/gif, image/jpeg'
      input.type = 'file'
      input.accept = typeAccept
      input.onchange = () => {
        if (typeAccept.indexOf(input.files[0].type) === -1) {
          this.$toast.error('Vui lòng chọn file định dạng hình ảnh')
          return
        }
        const reader = new FileReader()
        reader.readAsDataURL(input.files[0])
        reader.onload = () => {
          const img = new Image()
          img.src = reader.result
          img.onload = () => {
            if (img.width > 350 || img.height > 200) {
              this.$toast.error('Kích thước file ảnh quá lớn,tối đa(Rộng:350px;Cao:200px).\n Mời bạn chọn lại file khác!')
            } else {
              this.formData.chuKy = reader.result
            }
          }
        }
      }
      input.click()
    },
    checkSTD(){
      if(this.formData.dienThoai.length === 10 && this.formData.dienThoai[0] === '0')
        this.formData.checkDT = true;
      else
        this.formData.checkDT = false;
    },
    searchStaffByCode () {
      if (!this.formData.maNhanVien || !this.formData.maNhanVien.trim()) {
        this.$toast.error('Vui lòng nhập mã nhân viên để tìm kiếm')
        return
      }
      this.$emit('searchStaffByCode', this.formData.maNhanVien.trim())
    },
    searchStaffByName () {
      if (!this.formData.tenNhanVien || !this.formData.tenNhanVien.trim()) {
        this.$toast.error('Vui lòng nhập tên nhân viên để tìm kiếm')
        return
      }
      this.$emit('searchStaffByName')
    }
  }
}
</script>
<style scoped>
img {
  width: 100%; /* or any custom size */
  height: 100%;
  object-fit: contain;
}
/* Chrome, Safari, Edge, Opera */
input[type="number"].input-sdt::-webkit-outer-spin-button,
input[type="number"].input-sdt::-webkit-inner-spin-button {
    -webkit-appearance: none;
    margin: 0;
}

/* Firefox */
input[type="number"].input-sdt {
    -moz-appearance: textfield;
}
.input-group-sdt{
  position: relative;
}
.input-group-sdt svg{
  position: absolute;
  right: 10px;
  top: 50%;
  transform: translateY(-50%);
}
</style>
