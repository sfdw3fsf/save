<template src="./MarketCapacityUpdate.html"></template>
<style scoped src="./MarketCapacityUpdate.scss"></style>
<script>
import breadcrumb from '@/components/breadcrumb'
import pagination from "./Pagination";
import '@/assets/vendor/jquery/split'
import API from "./API";
import GroupHoDan from "./DanhSachHoDan/GroupHoDan";
import EventBus from '@/utils/eventBus'
import ThongTinHoDan from './ThongTinHoDan';
import XLSX from "xlsx";
import Define from './define';
import ModalHienThiBanDo from '../../../CABMAN/XetDuyetPhanTuMangHoDanThuThap/ChonToaDo';

export default {
  components: {
    breadcrumb,
    pagination,
    GroupHoDan,
    ThongTinHoDan,
    ModalHienThiBanDo
  },
  name: 'MarketCapacityUpdate',
  data() {
    return {
      header: {
        title: 'CẬP NHẬT DUNG LƯỢNG THỊ TRƯỜNG',
        list: [
          {name: 'Quản lý DB', link: {name: 'Ui.cards'}},
          {name: 'Cập nhật dung lượng thị trường', link: {name: 'Ui.cards'}},
        ]
      },
      data: {
        dsHoDan: [],
        dsHoDanHienThi: {},
      },
      itemsPerPage: 100,
      currentPage: 1,
      totalPage: 0,
      idThongTinHoDanModal: 'idThongTinHoDanModal',
      idBanDoModal: 'idBanDoModal',
      hoDan: {},
      hiddenGroups: []
    }
  },
  mounted() {
    this.frm_load();

    EventBus.$on('reload', (item) => {
      // console.log(hoDan)
      setTimeout(() => {
        this.reload()
      }, 1000);
    });

    EventBus.$on('onShowHoDan', (hoDan) => {
      this.hoDan = hoDan;
      this.$bvModal.show(this.idThongTinHoDanModal);
    });

    EventBus.$on('onShowMap', hoDan => {
      this.hoDan = hoDan;
      this.$bvModal.show(this.idBanDoModal);
    })
  },
  destroyed() {
    EventBus.$off('reload');
    EventBus.$off('onShowHoDan');
    EventBus.$off('onShowMap');
  },
  computed: {
    totalItems() {
      return this.data.dsHoDan.length;
    },
    startIndex() {
      return this.itemsPerPage * (this.currentPage - 1) + 1;
    },
    endIndex() {
      return this.itemsPerPage * this.currentPage > this.totalItems ? this.totalItems : this.itemsPerPage * this.currentPage;
    },
    calculateTotalPage() {
      return parseInt(this.totalItems / this.itemsPerPage) + ((this.totalItems % this.itemsPerPage === 0) ? 0 : 1)
    },
    getPosition() {
      return {
        lat: parseFloat(this.hoDan.vido),
        lng: parseFloat(this.hoDan.kinhdo)
      }
    },
    getAddress() {
      return this.hoDan.diachi;
    },
    getCusName() {
      return this.hoDan.ten_kh;
    }
  },
  methods: {
    async frm_load() {
      try {
        this.loading(true)

        let dsHoDan = this.layDsHoDan()
        await Promise.all([dsHoDan]);

        this.loading(false)
      } catch (err) {
        this.$toast.err(err.message);
      }
    },

    reload() {
      this.frm_load();
    },

    hardReload() {
      this.currentPage = 1;
      this.reload();
    },

    exportExcel() {
      try {
        if (this.totalItems <= 0) {
          this.$toast.error('Chưa có dữ liệu trên lưới để xuất excel!');
          return;
        }
        let headers = [
          [
            'ID',
            // 'SDKD ID',
            'Tên Khách hàng',
            'Địa chỉ',
            'Số người',
            // 'ID loại hộ dân',
            'Loại hộ dân',
            'Người cập nhật',
            'Ngày cập nhật',
            // 'Máy cập nhật',
            // 'Mô tả',
            // 'Tuvan',
            'Trạng thái duyệt',
            // 'ID mức sống',
            'Mức sống',
            'Tên nhân viên',
            'Vĩ độ',
            'Kinh độ'
          ]
        ]

        let dataExport = this.data.dsHoDan.map(item => {
          let clone = {...item};
          delete clone.sdkh_id;
          delete clone.loaihodan_id;
          delete clone.may_cn;
          delete clone.mota;
          delete clone.tuvan;
          delete clone.mucsong_id;
          clone.tt_duyet = clone.tt_duyet === Define.stt.cho_duyet ? 'Chờ duyệt' :
            (clone.tt_duyet === Define.stt.khong_dat ? 'Không đạt' : 'Đã duyệt');

          return clone;
        });

        // console.log(dataExport);

        const wb = XLSX.utils.book_new();
        const ws = XLSX.utils.json_to_sheet([]);
        XLSX.utils.sheet_add_aoa(ws, headers);

        XLSX.utils.sheet_add_json(ws, dataExport, {origin: 'A2', skipHeader: true});
        XLSX.utils.book_append_sheet(wb, ws, 'DSHoDan');

        XLSX.writeFile(wb, "ds_ho_dan.xlsx");
      } catch (ex) {
        this.$toast.error("Có lỗi trong quá trình xuất file Excel !" + ex)
      }
    },

    pageChanged(page) {
      this.currentPage = page;
      this.hienThiDsHoDan();
    },

    async layDsHoDan() {
      this.loading(true)
      try {
        let rs = await API.lay_ds_hodan_thuthap_theo_nv(this.axios);
        let {data} = rs.data;
        if (data != null && data.length > 0) {
          data.sort((a, b) => {
            let aDate = a.ngay_cn.toLowerCase();
            let bDate = b.ngay_cn.toLowerCase();
            if (aDate > bDate) {
              return -1;
            } else if (aDate < bDate) {
              return 1;
            } else {
              return 0;
            }
          });

          this.$set(this.data, 'dsHoDan', data);
          this.hienThiPhanTrang();
          await this.hienThiDsHoDan();
        } else {
          this.$set(this.data, 'dsHoDan', []);
        }

        this.loading(false)
        return rs.data.message;
      } catch (err) {
        this.loading(false)
        this.$toast.error(err.message);
      }
    },

    hienThiPhanTrang() {
      this.totalPage = this.calculateTotalPage;
    },

    hienThiDsHoDan() {
      this.hiddenGroups = this.hiddenGroups.map(item => false);
      if (this.totalItems > 0) {
        let dataByPage = this.data.dsHoDan.slice(this.startIndex - 1, this.endIndex);
        let groupOfNameAndDate = {};
        dataByPage.forEach((item, index) => {
          let key = this.getGroupOfNameAndDate(item);
          if (!(key in groupOfNameAndDate)) {
            groupOfNameAndDate[key] = [];
          }

          groupOfNameAndDate[key].push(item);
        });

        this.data.dsHoDanHienThi = {...groupOfNameAndDate};
      }
    },

    getGroupOfNameAndDate: (item) => {
      let date = new Date(item.ngay_cn);
      return item.ten_nv + ' (' + date.getDate() + '/' + (date.getMonth() + 1) + '/' + date.getFullYear() + ')';
    },

    confirmPosition: position => {

    },

    toggleGroup(id) {
      id = Define.textToSlug(id);
      // console.log(this.hiddenGroups);
      if (this.hiddenGroups[id] === true) {
        delete this.hiddenGroups[id];
        $('#group' + id).show()
        $('#icon' + id).removeClass()
        $('#icon' + id).addClass("icon fa fa-angle-down")
      } else {
        this.hiddenGroups[id] = true;
        $('#group' + id).hide()
        $('#icon' + id).removeClass()
        $('#icon' + id).addClass("icon fa fa-angle-up")
      }
    }
  },
}
</script>
