<template>
  <div>
    <breadcrumb :header="header" />
    <vue-nav>
      <ul class="list">
        <li>
          <a @click="search">
            <span class="icon one-search"></span> Tìm kiếm
          </a>
        </li>
        <li>
          <a @click="giaophieu">
            <span class="icon one-file-arrow-right"></span> Giao phiếu
          </a>
        </li>
        <li>
          <a @click="huyphieu"> <span class="icon one-cancel"></span> Huỷ giao </a>
        </li>
        <li>
          <a @click="capnhapdv"> <span class="icon one-reload1"></span> Cập nhật ĐV </a>
        </li>
      </ul>
    </vue-nav>
    <div class="page-content">
      <ThongTinTimKiem ref="timkiem" v-model="thongtintimkiem" />
      <vue-card label="Danh sách phiếu yêu cầu">
        <vue-table
          :loading="loading"
          :config="config"
          :options="options"
          v-model="thongtin"
        ></vue-table>
      </vue-card>
    </div>
  </div>
</template>
<script>
import ThongTinTimKiem from "./part/ThongTinTimKiem";
import moment from 'moment';
import breadcrumb from "@/components/breadcrumb";
import api from "./api";
export default {
  data() {
    return {
      thongtintimkiem: {
      },
      thongtin: {},
      loading: false,
      error: '',
      header: {
        title: "GIAO PHIẾU XỬ LÝ KHIẾU NẠI",
        list: [
          { name: "Lập hợp đồng", link: { name: "Ui.cards" } },
          {
            name: "Lắp đặt mới",
            link: { name: "Ui.buttons" },
          },
        ],
      },
      options: [],
      config: [
        {
          label: "Mã khiếu nại",
          filterAble: true,
          key: "SOPHIEU",
        },
        {
          label: "Ngày tiếp nhận",
          filterAble: true,
          key: "NGAYGIAO",
        },
        {
          label: "Số máy/Acc",
          filterAble: true,
          key: "MA_TB",
        },
        {
          label: "Tên thuê bao",
          filterAble: true,
          key: "TEN_TB",
        },
        {
          label: "Địa chỉ TB",
          filterAble: true,
          key: "DIACHI_TB",
        },
        {
          label: "Đơn vị nhận",
          filterAble: true,
          key: "DONVINHAN",
        },
        {
          label: "Loại CĐ",
          filterAble: true,
          key: "TEN_TTYC",
        },
        {
          label: "Nội dung KN",
          filterAble: true,
          key: "ND_GIAO",
        },
        {
          label: "Nhân viên",
          filterAble: true,
          key: "DONVINHAN",
        },
        {
          label: "Phòng bán hàng",
          filterAble: true,
          key: "TEN_TTYC",
        },
      ],
    };
  },
  props: {
    open: Boolean,
  },
  components: {
    ThongTinTimKiem,
    breadcrumb,
  },
  watch: {
    thongtin: {
      handler(val) {
        let tempHuonggiao = this.thongtintimkiem.HUONGGIAO
        this.thongtintimkiem = val
        this.thongtintimkiem.HUONGGIAO = tempHuonggiao
      },
      deep: true
    }
  },
  methods: {
    getThamSo() {
      api.getThamSo(this.axios, {
        "vkieu_id": 1
      }).then(res => {
        console.log(res.data)
      })
    },
    capnhapdv() {
      api.capnhapdv(this.axios,{
        "vdonvi_id": this.thongtintimkiem.DONVINHAN_ID,
        "vkhieunai_id": this.thongtintimkiem.PHIEU_ID,
        "vloaidvi_id": this.thongtintimkiem.PHIEU_ID
      }).then(res => {
        this.alertMessage(res.data.message)
      })
    },
    async huyphieu() {
      api.huyphieu(this.axios,{
        "vkhieunai_id": this.thongtintimkiem.PHIEU_ID,
        "vma_tb": this.thongtintimkiem.MA_TB,
        "vphanvung_id": 26,
        "vphieukn_id": this.thongtintimkiem.PHIEU_ID,
        "vttph_id": this.thongtintimkiem.TTPH_ID
      }).then(res => {
        this.alertMessage(res.data.message)
      })
    },
    async giaophieu() {
      api.giaophieu(this.axios,this.thongtintimkiem.ttgp).then(res => {
        this.alertMessage(res.data.message)
      })
    },
    async search() {
      this.$refs.timkiem.checkValidate()

      for(let index in this.$refs.timkiem.error) {
        if(this.$refs.timkiem.error[index] && this.$refs.timkiem.error[index].length > 0 ) {
          return false
        }
      }

      this.loading = true;
      if (this.thongtintimkiem.TTHD_ID === '2') {
        await this.getSearchDaGiao();
      } else {
        await this.getSearchChuaGiao();
      }
      this.loading = false;
    },
    getSearchChuaGiao() {
      return api
        .getSearchChuaGiao(this.axios, {
          "vhuonggiao_id": this.thongtintimkiem.HUONGGIAO,
          "vma_tb": this.thongtintimkiem.MA_TB,
          "vkyhoadon": moment().subtract(1, 'months').startOf('month').format('YYYYMMDD'),
          "vloaihd_id": null,
          "vma_gd": "",
          "vdichvuvt_id": null
        })
        .then((res) => {
          this.options = res.data.data ? res.data.data.data : [];
        });
    },
    getSearchDaGiao() {
      return api
        .getSearchDaGiao(this.axios, {
          "vhuonggiao_id": this.thongtintimkiem.HUONGGIAO,
          "vma_tb": this.thongtintimkiem.MA_TB,
          "vngay_giao": this.thongtintimkiem.NGAYGIAO,
          "vkyhoadon":  moment().subtract(1, 'months').startOf('month').format('YYYYMMDD'),
          "vloaihd_id": null,
          "vma_gd": "",
          "vdichvuvt_id": null,
        })
        .then((res) => {
          this.options = res.data.data ? res.data.data.data : [];
        });
    },
    alertMessage(message) {
      this.$bvToast.show()
      this.$bvToast.toast(message, {
          title: 'Thông báo',
          toaster: 'b-toaster-top-center',
          autoHideDelay: 5000,
          appendToast: false
      })
    }
  },
};
</script>
<style>
  .toast:not(.showing):not(.show) {
    opacity: 1;
  }
</style>
