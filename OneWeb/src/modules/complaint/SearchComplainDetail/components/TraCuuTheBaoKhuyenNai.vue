<template>
  <div>
      <vue-nav>
        <ul class="list">
          <li>
            <a @click="tracuuTB"> <span class="icon one-search"></span> Tìm kiếm </a>
          </li>
          <li>
            <a @click="sendData">
              <span class="icon ui-1_check-circle-08 nc-icon-glyph"></span> Chấp
              nhận
            </a>
          </li>
        </ul>
      </vue-nav>
      <div class="popup-body">
        <vue-card>
          <vue-card label="Thông tin tra cứu" :noborder="true">
            <b-row>
              <b-col md="6">
                <vue-select
                :options="options.ktk"
                v-model="form.vdieukien"
                labelField="kieu_tk" valueField="dieukien"
                labelWidth="120" label="Tra cứu theo"></vue-select>
              </b-col>
              <b-col md="6">
                <vue-input
                  v-model="form.tttc"
                  labelWidth="150"
                  label="Thông tin tra cứu"
                ></vue-input>
              </b-col>
            </b-row>
            <b-row>
              <b-col md="6">
                <vue-select
                  labelWidth="120"
                  :checkbox="true"
                  v-model="form.vdichvuvt_id"
                  labelField="ten_dvvt" valueField="dichvuvt_id" :options="options.dv"
                  label="Dịch vụ VT"
                ></vue-select>
              </b-col>
              <b-col md="6">
                <vue-select
                  :checkbox="true"
                  :options="options.trangthai"
                  v-model="form.vttkn_id"
                  labelField="trangthai_kn" valueField="ttkn_id"
                  label="Trạng thái khiếu nại"
                  labelWidth="150"
                ></vue-select>
              </b-col>
            </b-row>
            <b-row>
              <b-col md="4">
                <div class="info-row">
                  <div class="key w120">
                    <div class="check-action">
                      <input @change="disable.ngay_tn = !disable.ngay_tn" type="checkbox" class="check" />
                      <span class="name">Ngày tiếp nhận</span>
                    </div>
                  </div>
                  <vue-date :disable="disable.ngay_tn" type="date" format="YYYYMMDD" v-model="form.vngay_tn"  />
                </div>
              </b-col>
              <b-col md="2">
                <vue-date :disable="disable.ngay_tn" v-model="form.vdenngay" format="YYYYMMDD" type="date" labelWidth="80" label="Đến ngày"></vue-date>
              </b-col>
              <b-col md="6">
                <vue-input v-model="form.ma_tb" labelWidth="150" label="Số máy/Acc"></vue-input>
              </b-col>
            </b-row>
          </vue-card>
          <vue-card :noborder="true" label="Danh sách thuê bao khiếu nại">
            <DataGrid
            id="danh_sach" 
            ref="danh_sach"
            :columns="config"
            :showFilter="false" 
            :allowPaging="false"
            panelDataHeight="290"
            :enablePagingServer="false"
            v-bind:dataSource="options.tb" />
          </vue-card>
        </vue-card>
      </div>
  </div>
</template>
<script>
import api from '../api'
const formData = {
    vdenngay: "",
    vdichvuvt_id: null,
    vdieukien: "",
    vdonvi_dl_id: 0,
    vdonvi_id: 0,
    vngay_tn: "",
    vnhanvien_id: 0,
    vttkn_id: 0,
    tttc: '',
    ma_tb: ''
}
export default {
  name: 'TraCuuTheBaoKhuyenNai',
  data() {
    return {
        form: formData,
        options: {
            trangthai: [],
            dv: [],
            ktk: [],
            tb: []
        },
        inputValue: null,
        table: {},
        disable: {
          ngay_tn: true
        },
        config: [
            {
                headerText: 'Số máy/Acc',
                filterAble: true,
                fieldName: 'ma_tb'
            },
            {
                headerText: 'Tên thuê bao',
                filterAble: true,
                fieldName: 'ten_tb'
            },
            {
                headerText: 'Địa chỉ thuê bao',
                filterAble: true,
                fieldName: 'diachi_tb'
            },
            {
                headerText: 'Dịch vụ VT',
                filterAble: true,
                fieldName: 'ten_dvvt'
            },
            {
                headerText: 'Trạng thái',
                filterAble: true,
                fieldName: 'trangthai_tb'
            }
        ]
    };
  },
  props: ['input'],
  computed: {
      tttc() {
          if(this.form.tttc)
            return this.form.vdieukien.replace(':values', this.form.tttc)
          else
            return ''
      }
  },
  methods: {
    init() {
      if(this.input.dichvuvt_id) {
        this.form.vdichvuvt_id = this.input.dichvuvt_id
      }

      if(this.input.ttkn_id) {
        this.form.vttkn_id = this.input.ttkn_id
      }
      this.getDanhSachTrangThaiPhieu()
      this.getDichvuVienthong()
      this.getKieuTaiKhoan()
    },
    sendData() {
        let data = this.$refs.danh_sach.getSelectedRecords()
        if(data.length > 0) {
          this.$emit('chapnhan', data[0].ma_tb)
        } else {
          this.$toast.error("Chưa có thuê bao nào được chọn")
        }
    },
    getDanhSachTrangThaiPhieu() {
      api.getDanhSachTrangThaiPhieu(this.axios).then((res) => {
        this.options.trangthai = res.data.data ? res.data.data : [];
      });
    },
    getDichvuVienthong() {
        api.getDichvuVienthong(this.axios).then(res => {
            this.options.dv = res.data.data ? res.data.data : []
        })
    },
    getKieuTaiKhoan() {
        api.getKieuTaiKhoan(this.axios, {
            "p_nhom_id":1
        }).then(res => {
            this.options.ktk = res.data.data ? res.data.data : []
        })
    },
    async tracuuTB() {
      try {
          let validate = false
          if(!this.form.vdichvuvt_id) {
            validate = true
            this.$toast.error("Bạn chưa chọn loại dịch vụ viễn thông!")
          }

          if(!this.form.vngay_tn) {
            validate = true
            this.$toast.error("Bạn chưa chọn ngày tiếp nhận!")
          }

          if(!this.form.vdenngay) {
            validate = true
            this.$toast.error("Bạn chưa chọn thông tin đến ngày!")
          }

          if(validate) {
            return false
          }
          this.loading(true)
          this.table = {}
          const data = {
              vdenngay: this.form.vdenngay,
              vdichvuvt_id: this.form.vdichvuvt_id,
              vdieukien: this.tttc,
              vdonvi_dl_id: 0,
              vdonvi_id: 0,
              vngay_tn: this.form.vngay_tn,
              vnhanvien_id: 0,
              vttkn_id: this.form.vttkn_id,
          }
          await api.tracuuTB(this.axios, data).then(res => {
              this.options.tb = res.data.data ? res.data.data : []
          })
          this.loading(false)
      } catch(e) {
        console.log(e)
        this.$toast.error("Đã có lỗi xảy ra")
        this.loading(false)
      }
    }
  },
};
</script>
<style>
.none-padding {
  padding: 0 !important;
}
.modal-dialog {
  border-radius: 0px !important;
  width: 100% !important;
  max-width: 1220px !important;
}
.popup-box {
    background: #fafafa38;
}
</style>
