<template>
  <div>
    <vue-bread-crumb :header="header" />
    <vue-nav>
      <ul class="list">
        <li>
          <a @click="timkiem()"> <span class="icon one-search"></span>Tìm kiếm </a>
        </li>
        <li v-if="form.trangThai == 1">
          <a @click="giaophieu()">
            <span class="icon one-file-arrow-right1"></span>Giao phiếu
          </a>
        </li>
        <li v-if="form.trangThai == 2">
          <a @click="huygiao()"> <span class="icon one-cancel"></span>Huỷ giao </a>
        </li>
        <li>
          <a @click="capnhap()"> <span class="icon one-reload1"></span>Cập nhật ĐV </a>
        </li>
        <li>
          <a>
            <span class="icon one-circle-question"></span>Trợ giúp
          </a>
        </li>
      </ul>
    </vue-nav>
    <div class="page-content">
        <vue-card label="Thông tin tìm kiếm">
            <b-row>
                <b-col md="6">
                    <b-row>
                        <b-col md="6">
                            <vue-select labelWidth="110" v-model="form.dichVuVTId" labelField="NAME" valueField="ID" :options="options.dichvu" label="Dịch vụ" checkbox></vue-select>
                        </b-col>
                        <b-col md="6">
                            <vue-input
                            v-model="form.maTB"
                            label="Số máy/ Acc"
                            >
                            </vue-input>
                        </b-col>
                        <!-- v-model="form.huongGiaoId" -->
                        <vue-select labelWidth="110" v-model="form.quyTrinhId" labelField="NAME" valueField="ID" :options="options.quytrinh" label="Quy trình" :checkbox="true"></vue-select>
                    </b-row>
                </b-col>
                <b-col md="6">
                    <vue-date labelWidth="90"
                    v-model="form.ngayGiao" type="date" format="DD/MM/YYYY" label="Ngày giao"></vue-date>
                    <vue-select v-model="form.trangThai" labelField="NAME" valueField="ID" :options="options.ttphieu" labelWidth="90" label="Trạng thái"></vue-select>
                </b-col>
            </b-row>
        </vue-card>
        <vue-card label="Thông tin giao phiếu">
            <b-row>
                <b-col md="6">
                    <vue-select v-model="form.huongGiaoId" labelField="huongGiao" valueField="huongGiaoId" :options="options.huongiao" labelWidth="90" label="Hướng giao"></vue-select>
                    <vue-input v-model="phieugiao.nd_giao" labelWidth="90" label="Nội dung giao"></vue-input>
                </b-col>
                <b-col md="6">
                    <b-row>
                        <b-col md="6">
                            <vue-input v-model="phieugiao.ma_tb" disable labelWidth="90" label="Số máy/Acc"></vue-input>
                        </b-col>
                        <b-col md="6">
                            <vue-date v-model="phieugiao.ngaygiao" labelWidth="90" type="date" format="DD/MM/YYYY" label="Ngày giao"></vue-date>
                        </b-col>
                    </b-row>
                    <b-row>
                        <b-col md="6">
                            <vue-select labelField="NAME" valueField="ID" :options="options.donvi" v-model="donvinhan" labelWidth="90" label="Đơn vị nhận" ></vue-select>
                        </b-col>
                        <b-col md="6">
                            <vue-select v-model="mucdo" labelField="NAME" valueField="ID" :options="options.mucdo" labelWidth="90" label="Mức độ" ></vue-select>
                        </b-col>
                    </b-row>
                </b-col>
            </b-row>
        </vue-card>
        <vue-card label="Danh sách phiếu yêu cầu">
            <vue-table v-model="phieugiaoArr" ref="phieugiao" :multiple="true" unique="baohong_id" :config="config" :options="options.danhsach"></vue-table>
        </vue-card>
    </div>
  </div>
</template>
<script>
import api from "./api/index.js";
export default {
  data() {
    return {
      header: {
        title: "Giao phiếu báo hỏng",
        list: [
          { name: "Lập hợp đồng", link: { name: "Ui.cards" } },
          {
            name: "Lắp đặt mới",
            link: { name: "Ui.buttons" },
          },
        ],
      },
      ref: null,
      options: {
          dichvu: [],
          quytrinh: [],
          ttphieu: [],
          mucdo: [],
          danhsach: [],
          huongiao: [],
          donvi: []
      },
      form: {
          dichVuVTId: 0,
          trangThai: null,
          huongGiaoId: null,
          quyTrinhId: null,
          maTB: "",
          ngayGiao: new Date().toLocaleString('en-GB', {dateStyle: 'short'}),
      },
      phieugiao: {},
      phieugiaoArr: [],
      thongtinphieu: {
          ma_tb: '',
          ngaygiao: new Date().toLocaleString('en-GB', {dateStyle: 'short'}),
          nd_giao: '',
          donvi_id: null,
          ttbh_id: null
      },
      config: [
            {
                label: "Số máy/Acc",
                filterAble: true,
                key: "ma_tb",
            },
            {
                label: "Tên thuê bao",
                filterAble: true,
                key: "ten_tb"
            },
            {
                label: "Địa chỉ lắp đặt",
                filterAble: true,
                key: "diachi_ld",
            },
            {
                label: "Đơn vị nhận",
                filterAble: true,
                key: "donvinhan",
            },
        ]
    };
  },
  computed: {
    donvinhan: {
        get() {
            return this.phieugiao.donvi_id
        },
        set(val) {
            this.$set(this.phieugiao, 'donvi_id', val)
        }
    },
    mucdo: {
        get() {
            return String(this.phieugiao.ttbh_id)
        },
        set(val) {
            this.$set(this.phieugiao, 'ttbh_id', String(val))
        }
    }
  },
  mounted() {
      this.ref = this.$refs.phieugiao
      this.getDanhSachDichVu()
      this.getDanhSachTrangThaiPhieu()
      this.getDanhSachMucDo()

  },
  watch: {
      "ref.choose": {
        deep: true,
        handler(val) {
          for(let i in val) {
            this.$set(this.phieugiao, i, val[i])
          }
          const check = Date.parse(this.phieugiao.ngaygiao)
          if(isNaN(check) == false)
            this.phieugiao.ngaygiao = new Date(this.phieugiao.ngaygiao).toLocaleString('en-GB' ,{dateStyle: 'short'})
        }
      },
      "form.dichVuVTId"(val) {
        this.getDanhSachQuyTrinh(val)
      },
      "form.quyTrinhId"() {
        this.getDanhsachHuongGiao()
      },
      phieugiao: {
        deep: true,
        handler(val) {
          this.getDonVi(val.loaidv_id)
        }
      }
  },
  methods: {
      getDanhSachDichVu() {
          api.getDanhSachDichVu(this.axios).then(res => {
              this.options.dichvu = res.data.data ? res.data.data : []
          })
      },
      getDanhSachQuyTrinh(dichVuVTId) {
          api.getDanhSachQuyTrinh(this.axios, {dichVuVTId}).then(res => {
              this.options.quytrinh = res.data.data ? res.data.data : []
          })
      },
      getDanhSachTrangThaiPhieu() {
          api.getDanhSachTrangThaiPhieu(this.axios).then(res => {
              this.options.ttphieu = res.data.data ? res.data.data : []
              this.form.trangThai = this.options.ttphieu[0] ? this.options.ttphieu[0].ID : null
          })
      },
      getDanhSachMucDo() {
          api.getDanhSachMucDo(this.axios).then(res => {
              this.options.mucdo = res.data.data ? res.data.data : []
          })
      },
      getDanhsachHuongGiao() {
          api.getDanhsachHuongGiao(this.axios,{
                "theoNguoiDung": 0,
                "quyTrinhId": this.form.quyTrinhId,
                "dichVuVTId": this.form.dichVuVTId
            }).then(res => {
              this.options.huongiao = res.data.data ? res.data.data : []
              this.form.huongGiaoId = this.options.huongiao[0] ? this.options.huongiao[0].huongGiaoId : null
          })
      },
      timkiem() {
          this.loading(true)
          this.phieugiao = {}
          let data = {}
          if(this.form.trangThai)
            data.trangThai = this.form.trangThai
          if(this.form.huongGiaoId)
            data.huongGiaoId = this.form.huongGiaoId
          if(this.form.maTB)
            data.maTB = this.form.maTB
          if(this.form.ngayGiao)
            data.ngayGiao = this.form.ngayGiao

          api.timkiem(this.axios, data
          ).then(res => {
              this.options.danhsach = res.data.data ? res.data.data : []
          }).finally(() => {
              this.loading(false)
          })
      },
      huygiao() {
          this.phieugiaoArr.forEach(async phieu => {
            if(!phieu.ttbh_id) {
                this.$toast.error(phieu.ma_tb + " Chưa chọn mức độ")
            } else if(!phieu.baohong_id) {
                this.$toast.error(phieu.ma_tb + " Chưa chọn phiếu báo hỏng")
            } else {
                await api.huygiao(this.axios,{
                    "mucDoId": phieu.ttbh_id,
                    "baoHongId": phieu.baohong_id}
                ).then(res => {
                this.$toast.success(phieu.ma_tb + ": " + res.data.message)
                })
            }
          })
          this.timkiem()
      },
      async capnhap() {
        if(!this.phieugiao.ttbh_id) {
            this.$toast.error("Chưa chọn mức độ")
        } else if(!this.phieugiao.baohong_id) {
            this.$toast.error("Chưa chọn phiếu báo hỏng")
        } else {
            await this.capnhapAsync().then(res => {
                this.$toast.error(this.phieugiao.ma_tb + ": " + res.data.message)
            })
        }
      },
      capnhapAsync() {
          return api.capnhap(this.axios,{
                    "baoHongId": this.phieugiao.baohong_id,
                    "donViId": this.phieugiao.donvi_id,
                    "kieuDVId": this.phieugiao.kieudv_id,
                    "loaiDVId": this.phieugiao.loaidv_id
                }
            )
      },
      giaophieu() {
          this.phieugiaoArr.forEach(async phieu => {
                if(!phieu.ttbh_id) {
                    this.$toast.error(phieu.ma_tb + ": Chưa chọn mức độ")
                } else if(!phieu.baohong_id) {
                    this.$toast.error(phieu.ma_tb + ":Chưa chọn phiếu báo hỏng")
                } else {
                    await api.giaophieu(this.axios,{
                        "mucDoId": phieu.ttbh_id,
                        "baoHongId": phieu.baohong_id}
                    ).then(res => {
                    this.$toast.error(phieu.ma_tb + ": " +res.data.message)
                    })
                }
          })
          this.timkiem()
      },
      getDonVi(loaiDVId) {
          api.getDonVi(this.axios, {loaiDVId}).then(res => {
              this.options.donvi = res.data.data ? res.data.data : []
          })
      },
      formatDate(date) {
          return new Date(date).toLocaleString()

      }
  }
};
</script>
