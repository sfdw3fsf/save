<template>
  <div class="box-form">
    <div class="legend-title" v-if="showHeader">{{ this.header }}</div>
    <div class="table-content">
      <b-table
        :items="displayItemsGetter"
        :fields="fields"
        show-empty
        selectable
        select-mode="single"
        :class="tbClass"
        ref="selectableTable"
        @row-selected="onRowSelected"
      >
        <template #head(isCheck)="slotData">
          <b-form-checkbox
            @change="onSelectAll"
            :checked="isSelectAll"
          ></b-form-checkbox>
        </template>
        <template #top-row="data">
          <b-th v-for="(item, index) in fields" :key="index">
            <div class="input-icon-right" v-if="fields[index].isSearch">
              <input
                type="text"
                class="form-control"
                @input="onChangeText(fields[index].key, $event.target.value)"
              />
              <span class="icon nc-icon-outline ui-1_zoom"></span>
            </div>
            <div v-else></div>
          </b-th>
        </template>
        <template #empty="scope" v-if="displayEmptyText">
          <h4 style="text-align: center">Không có dữ liệu để hiển thị</h4>
        </template>
        <template #emptyfiltered="scope" v-if="displayEmptyText">
          <h4 style="text-align: center">Không có dữ liệu để hiển thị</h4>
        </template>
        <template #cell(selectedColumn)="{ rowSelected }">
          <template v-if="rowSelected">
            <span aria-hidden="true" class="fa fa-play text-main"></span>
          </template>
          <template v-else>
            <span aria-hidden="true">&nbsp;</span>
          </template>
        </template>
        <template v-slot:cell(isCheck)="row">
          <b-form-checkbox
            v-model="row.item.isCheck"
            :key="row.index"
          ></b-form-checkbox>
        </template>
        <!-- <template v-slot:cell(STT)="row">
          {{ this.pageInfo.maxSize * this.pageInfo.page + row.index + 1}}
        </template> -->
      </b-table>
    </div>
    <div class="tc-pagination" v-if="showPagination">
      <button
        @click="firstPage()"
        :disabled="this.pageInfo.page == 0"
        class="btn one-arrow-left-forward"
      ></button>
      <button
        @click="previousPage()"
        :disabled="this.pageInfo.page == 0"
        class="btn fa fa-angle-double-left"
      ></button>
      <button
        @click="doSelectNearby(false)"
        class="btn fa fa-angle-left"
      ></button>
      <span class="text">Bản ghi</span>
      <div class="dropdown inline">
        <div class="fw6 input-number" data-toggle="dropdown">
          <span class="text-main">{{ getSelectedRow }}</span>
          <!-- <span class="text-main">{{ getSelectedRow }}</span
          > -->
          /{{ this.pageInfo.totalElement }}
        </div>
        <div class="dropdown-menu">
          <a
            href="javascript: void(0)"
            class="dropdown-item"
            @click="setPageSize(item)"
            v-for="item in this.pageSizeList"
            :key="item"
          >
            {{ item }}
          </a>
          <!-- <a href="#" class="dropdown-item">10</a>
          <a href="#" class="dropdown-item">20</a> -->
        </div>
      </div>
      <button
        @click="doSelectNearby(true)"
        class="btn fa fa-angle-right"
      ></button>
      <button
        @click="nextPage()"
        :disabled="this.pageInfo.page >= this.pageInfo.totalPages - 1"
        class="btn fa fa-angle-double-right"
      ></button>
      <button
        @click="lastPage()"
        :disabled="this.pageInfo.page >= this.pageInfo.totalPages - 1"
        class="btn one-arrow-right-forward"
      ></button>
    </div>
  </div>
</template>
<script>
export default {
  props: {
    fields: Array,
    itemsAssign: Array,
    selectMode: String,
    header: String,
    showPagination: {
      type: Boolean,
      default: true,
    },
    showHeader: {
      type: Boolean,
      default: true,
    },
    pageIndex: {
      type: Number,
      default: 0,
    },
    pageSize: {
      type: Number,
      default: 10,
    },
    pageSizeList: {
      type: Array,
      default: function () {
        return [10, 20];
      },
    },
    selectedRow: {
      type: Number,
      default: 0,
    },
    getDataFunc: {
      type: Function,
    },
    onRowSelectedFunc: {
      type: Function,
    },
    tbClass: {
      type: Array,
      default: function () {
        return ['table-result', 'table-gachle'];
      },
    }
  },
  async mounted() {
    if (this.itemsAssign && this.itemsAssign.length > 0)
      this.items = this.itemsAssign;
    else {
      if (this.getDataFunc) await this.fetchData();
    }

    this.currentSelectedRow = 0;
    this.$refs.selectableTable.selectRow(this.currentSelectedRow);
  },
  computed: {
    getSelectedRow: function () {
      return (
        this.pageInfo.maxSize * this.pageInfo.page + this.currentSelectedRow + 1
      );
    },
    displayItemsGetter() {
      let displayItems = this.items;
      if (this.searchArr.length > 0) {
        for (var i = 0; i < this.searchArr.length; i++) {
          let search = this.searchArr[i];
          displayItems = displayItems.filter((x) => {
            const itemValue = x[search.searchField] + "" || "";
            return (
              itemValue
                .toLowerCase()
                .indexOf(search.searchValue.toLowerCase()) > -1
            );
          });
        }
      }

      return displayItems;
    },
  },
  data() {
    return {
      items: [],
      isSelectAll: false,
      searchArr: [],
      displayEmptyText: true,
      currentPageSize: this.pageSize,
      currentPageIndex: this.pageIndex,
      currentSelectedRow: this.selectedRow,
      pageInfo: {
        page: 0,
        maxSize: this.pageSizeList[0],
        totalElement: 0,
        totalPages: 0,
        sort: null,
        propertiesSort: null,
      },
    };
  },
  watch: {
    // isSelectAll: {
    //       handler: function (value) {
    //           console.log('isSelectAll', this.isSelectAll)
    //       }
    //   }
    currentSelectedRow: {
      handler: function (value) {
        this.$refs.selectableTable.selectRow(value);
      },
    },
  },
  methods: {
    onSelectAll() {
      this.isSelectAll = !this.isSelectAll;
      this.items.forEach((element) => {
        element.isCheck = this.isSelectAll;
      });
    },
    async onRowSelected(item) {
      if (this.onRowSelectedFunc) await this.onRowSelectedFunc(item);
    },
    async fetchData() {
      let response = await this.getDataFunc(
        this.pageInfo.page + 1,
        this.pageInfo.maxSize
      );
      //   let response = JSON.parse(
      //     '{"data":{"error":"200","error_code":"BSS-00000000","message":"Thành công","message_detail":"","request_id":"","data":{"page":0,"maxSize":10,"totalElement":57,"totalPages":6,"sort":null,"propertiesSort":null,"data":[{"HDKH_ID":0,"TBTL_ID":2316929,"BAOHONG_ID":4442617,"MA_TB":"hpgmytv617111","FILENAME":0,"URLFILE":null,"NGUOI_HOI":"Đỗ Thị Diệu Hồng","NOIDUNG_CSKH":"HL2 ( Khách hàng trả lời bình thường/được rồi/tạm được)","LOAI_PHIEU":"Báo hỏng","LOAIHINH_TB":"MyTV","GHICHU_KS":null,"NGAY_HOI":"2021-01-02T18:58:25.000+00:00","DIENTHOAI_LH":"0984010902","TRAM_VT":"Tổ Kỹ thuật Ngô Quyền","TEN_TB":"Khúc Quang Khải","DIACHI_LD":"60/152, Đường Lê Lai, Phường Máy Chai, Quận Ngô Quyền, Hải Phòng","NGAY_HT":"2020-12-31T15:25:52.000+00:00","NGUOI_TL":"Khải","NGUOI_XL":null,"TEN_PLKH":"CB3","CHITIEU_TG":24,"GHICHU_TB":"dieu lai phieu tu bo phan khao sat khach hang: đêm hay mất mạng","GHICHU_XL":"| 31/12/2020 15:25: Ok","LYDO_HONG":"Treo thiết bị","SO_DT":"0984010902 0984010902","LAN_KS":1,"TEN_LOAIHD":"Báo hỏng","TEN_KIEULD":"Xử lý sự cố thuê bao: MyTV","PHIEU_ID":4442617,"NGAYHEN_TU":null,"NGAYHEN_DEN":null},{"HDKH_ID":0,"TBTL_ID":2316867,"BAOHONG_ID":4442887,"MA_TB":"xuanhung1948","FILENAME":0,"URLFILE":null,"NGUOI_HOI":"Đỗ Thị Diệu Hồng","NOIDUNG_CSKH":"HL1 ( Khách hàng trả lời hài lòng)","LOAI_PHIEU":"Báo hỏng","LOAIHINH_TB":"Fiber","GHICHU_KS":null,"NGAY_HOI":"2021-01-02T18:26:40.000+00:00","DIENTHOAI_LH":"0253629290","TRAM_VT":"Tổ Kỹ thuật Ngô Quyền","TEN_TB":"Vũ Xuân Hùng","DIACHI_LD":"456, Đường Đà Nẵng, Phường Đông Hải 1, Quận Hải An, Hải Phòng","NGAY_HT":"2020-12-31T11:40:49.000+00:00","NGUOI_TL":"Hùng","NGUOI_XL":null,"TEN_PLKH":"CB3","CHITIEU_TG":24,"GHICHU_TB":"KH báo không vào được mạng, ychttt lh 02253629290","GHICHU_XL":"| 31/12/2020 11:40: Dây Lan lên tầng lỏng, cắm lại ok","LYDO_HONG":"Hỗ trợ KH","SO_DT":"2253629290 0776601645","LAN_KS":1,"TEN_LOAIHD":"Báo hỏng","TEN_KIEULD":"Xử lý sự cố thuê bao: Fiber","PHIEU_ID":4442887,"NGAYHEN_TU":null,"NGAYHEN_DEN":null},{"HDKH_ID":0,"TBTL_ID":2316764,"BAOHONG_ID":4443127,"MA_TB":"y3654130","FILENAME":0,"URLFILE":null,"NGUOI_HOI":"Đỗ Thị Diệu Hồng","NOIDUNG_CSKH":"HL1 ( Khách hàng trả lời hài lòng)","LOAI_PHIEU":"Báo hỏng","LOAIHINH_TB":"Fiber","GHICHU_KS":null,"NGAY_HOI":"2021-01-02T17:43:04.000+00:00","DIENTHOAI_LH":"0936925527","TRAM_VT":"Tổ Kỹ thuật Ngô Quyền","TEN_TB":"Lê Văn ý","DIACHI_LD":"196 - Lê Thánh Tông - Máy Chai - Ngô Quyền","NGAY_HT":"2020-12-31T15:54:06.000+00:00","NGUOI_TL":"0936925527","NGUOI_XL":null,"TEN_PLKH":"CB3","CHITIEU_TG":24,"GHICHU_TB":"Trạng thái port: /LOS ,KH báo mất tín hiệu, yc httt, LH 0936925527","GHICHU_XL":"| 31/12/2020 15:54: Ok","LYDO_HONG":"Đứt, hỏng dây thuê bao đoạn trong nhà (Indoor)","SO_DT":"0936925527 3654130","LAN_KS":1,"TEN_LOAIHD":"Báo hỏng","TEN_KIEULD":"Xử lý sự cố thuê bao: Fiber","PHIEU_ID":4443127,"NGAYHEN_TU":null,"NGAYHEN_DEN":null},{"HDKH_ID":0,"TBTL_ID":2316685,"BAOHONG_ID":4442928,"MA_TB":"dthuehp_23052020","FILENAME":0,"URLFILE":null,"NGUOI_HOI":"Đỗ Thị Diệu Hồng","NOIDUNG_CSKH":"HL1 ( Khách hàng trả lời hài lòng)","LOAI_PHIEU":"Báo hỏng","LOAIHINH_TB":"Fiber","GHICHU_KS":null,"NGAY_HOI":"2021-01-02T16:54:36.000+00:00","DIENTHOAI_LH":"0914212985","TRAM_VT":"Tổ Kỹ thuật Ngô Quyền","TEN_TB":"Đào Thị Huệ","DIACHI_LD":"1/8/450, Đường Phủ Thượng Đoạn, Phường Đông Hải 1, Quận Hải An, Hải Phòng","NGAY_HT":"2020-12-31T10:23:20.000+00:00","NGUOI_TL":"0914212985","NGUOI_XL":null,"TEN_PLKH":"CB3","CHITIEU_TG":24,"GHICHU_TB":"lỗi mạng","GHICHU_XL":"| 31/12/2020 10:23: Ok","LYDO_HONG":"Các lỗi do đấu nối phía trong nhà/trạm viễn thông","SO_DT":"0914212985 0914212985","LAN_KS":1,"TEN_LOAIHD":"Báo hỏng","TEN_KIEULD":"Xử lý sự cố thuê bao: Fiber","PHIEU_ID":4442928,"NGAYHEN_TU":null,"NGAYHEN_DEN":null},{"HDKH_ID":0,"TBTL_ID":2316682,"BAOHONG_ID":4442696,"MA_TB":"huuphong_hp","FILENAME":0,"URLFILE":null,"NGUOI_HOI":"Đỗ Thị Diệu Hồng","NOIDUNG_CSKH":"HL1 ( Khách hàng trả lời hài lòng)","LOAI_PHIEU":"Báo hỏng","LOAIHINH_TB":"Fiber","GHICHU_KS":null,"NGAY_HOI":"2021-01-02T16:52:04.000+00:00","DIENTHOAI_LH":"0906028586","TRAM_VT":"Tổ Kỹ thuật Ngô Quyền","TEN_TB":"Bùi Hữu Phong","DIACHI_LD":"1a/74/50/286, Lê Lai, Máy Chai, Ngô Quyền","NGAY_HT":"2020-12-31T10:45:27.000+00:00","NGUOI_TL":"0906028586","NGUOI_XL":null,"TEN_PLKH":"CB3","CHITIEU_TG":24,"GHICHU_TB":"mất tín hiệu khách hàng yêu cầu kiểm tra","GHICHU_XL":"| 31/12/2020 10:45: Ok","LYDO_HONG":"Đứt, hỏng dây thuê bao đoạn ngoài trời (Outdoor)","SO_DT":"0906028586 0389282700","LAN_KS":1,"TEN_LOAIHD":"Báo hỏng","TEN_KIEULD":"Xử lý sự cố thuê bao: Fiber","PHIEU_ID":4442696,"NGAYHEN_TU":null,"NGAYHEN_DEN":null},{"HDKH_ID":4988153,"TBTL_ID":2316666,"BAOHONG_ID":0,"MA_TB":"hpgmytv150199","FILENAME":0,"URLFILE":null,"NGUOI_HOI":"Bùi Thị Vân Anh","NOIDUNG_CSKH":"Đồng ý","LOAI_PHIEU":"Thi công","LOAIHINH_TB":"MyTV","GHICHU_KS":null,"NGAY_HOI":"2021-01-02T16:39:15.000+00:00","DIENTHOAI_LH":"0912162109","TRAM_VT":"Tổ Kỹ thuật Ngô Quyền","TEN_TB":"Nguyễn Văn Tuấn","DIACHI_LD":"17/219, Đường Phương Lưu, Phường Vạn Mỹ, Quận Ngô Quyền, Hải Phòng","NGAY_HT":"2021-01-15T17:32:38.000+00:00","NGUOI_TL":"0912162109","NGUOI_XL":null,"TEN_PLKH":"CB2","CHITIEU_TG":72,"GHICHU_TB":null,"GHICHU_XL":null,"LYDO_HONG":null,"SO_DT":"0912162109 0912162109","LAN_KS":2,"TEN_LOAIHD":"Dịch chuyển","TEN_KIEULD":"Dịch chuyển Mytv kèm Fiber VNN","PHIEU_ID":5564433,"NGAYHEN_TU":null,"NGAYHEN_DEN":null},{"HDKH_ID":4988153,"TBTL_ID":2316664,"BAOHONG_ID":0,"MA_TB":"tuan3780811","FILENAME":0,"URLFILE":null,"NGUOI_HOI":"Bùi Thị Vân Anh","NOIDUNG_CSKH":"Đồng ý","LOAI_PHIEU":"Thi công","LOAIHINH_TB":"Fiber","GHICHU_KS":null,"NGAY_HOI":"2021-01-02T16:38:52.000+00:00","DIENTHOAI_LH":"0912162109","TRAM_VT":"Tổ Kỹ thuật Ngô Quyền","TEN_TB":"Nguyễn Văn Tuấn","DIACHI_LD":"17/219, Đường Phương Lưu, Phường Vạn Mỹ, Quận Ngô Quyền, Hải Phòng","NGAY_HT":"2021-01-15T17:32:02.000+00:00","NGUOI_TL":"0912162109","NGUOI_XL":null,"TEN_PLKH":"CB2","CHITIEU_TG":72,"GHICHU_TB":null,"GHICHU_XL":null,"LYDO_HONG":null,"SO_DT":"0912162109 0912162109","LAN_KS":1,"TEN_LOAIHD":"Dịch chuyển","TEN_KIEULD":"Dịch chuyển trên đường không số","PHIEU_ID":5562523,"NGAYHEN_TU":"2021-01-02T16:38:37.000+00:00","NGAYHEN_DEN":"2021-01-05T18:38:37.000+00:00"},{"HDKH_ID":5004106,"TBTL_ID":2316662,"BAOHONG_ID":0,"MA_TB":"hpgmytv779330","FILENAME":0,"URLFILE":null,"NGUOI_HOI":"Bùi Thị Vân Anh","NOIDUNG_CSKH":"Đồng ý","LOAI_PHIEU":"Thi công","LOAIHINH_TB":"MyTV","GHICHU_KS":null,"NGAY_HOI":"2021-01-02T16:38:12.000+00:00","DIENTHOAI_LH":"0902521999","TRAM_VT":"Tổ Kỹ thuật Ngô Quyền","TEN_TB":"Nguyễn Ngọc Hoàng","DIACHI_LD":"Số 115 Lô 7 TĐC Cái Hòm, Lô 1,2,3,3a,3b,3c,3d,3e,6,6b,7,7a,7b,7c,8,8a, Đường Lê Hồng Phong, Phường Đông Khê, Quận Ngô Quyền, Hải Phòng","NGAY_HT":"2021-01-08T10:20:49.000+00:00","NGUOI_TL":"0902521999","NGUOI_XL":null,"TEN_PLKH":"CB4","CHITIEU_TG":72,"GHICHU_TB":null,"GHICHU_XL":null,"LYDO_HONG":null,"SO_DT":"0902521999 0902521999","LAN_KS":1,"TEN_LOAIHD":"Lắp đặt mới","TEN_KIEULD":"Đặt mới MyTV kèm FTTH","PHIEU_ID":5580514,"NGAYHEN_TU":null,"NGAYHEN_DEN":null},{"HDKH_ID":5004106,"TBTL_ID":2316661,"BAOHONG_ID":0,"MA_TB":"nnhoang84731","FILENAME":0,"URLFILE":null,"NGUOI_HOI":"Bùi Thị Vân Anh","NOIDUNG_CSKH":"Đồng ý","LOAI_PHIEU":"Thi công","LOAIHINH_TB":"Fiber","GHICHU_KS":null,"NGAY_HOI":"2021-01-02T16:37:59.000+00:00","DIENTHOAI_LH":"0902521999","TRAM_VT":"Tổ Kỹ thuật Ngô Quyền","TEN_TB":"Nguyễn Ngọc Hoàng","DIACHI_LD":"Số 115 Lô 7 TĐC Cái Hòm, Lô 1,2,3,3a,3b,3c,3d,3e,6,6b,7,7a,7b,7c,8,8a, Đường Lê Hồng Phong, Phường Đông Khê, Quận Ngô Quyền, Hải Phòng","NGAY_HT":"2021-01-08T10:21:39.000+00:00","NGUOI_TL":"0902521999","NGUOI_XL":null,"TEN_PLKH":"CB4","CHITIEU_TG":72,"GHICHU_TB":null,"GHICHU_XL":null,"LYDO_HONG":null,"SO_DT":"0902521999 0902521999","LAN_KS":1,"TEN_LOAIHD":"Lắp đặt mới","TEN_KIEULD":"Đặt mới Fiber không số cáp quang","PHIEU_ID":5580513,"NGAYHEN_TU":"2021-01-02T16:37:43.000+00:00","NGAYHEN_DEN":"2021-01-05T18:37:43.000+00:00"},{"HDKH_ID":0,"TBTL_ID":2316652,"BAOHONG_ID":4443151,"MA_TB":"pqthe75862","FILENAME":0,"URLFILE":null,"NGUOI_HOI":"Đỗ Thị Diệu Hồng","NOIDUNG_CSKH":"HL1 ( Khách hàng trả lời hài lòng)","LOAI_PHIEU":"Báo hỏng","LOAIHINH_TB":"Fiber","GHICHU_KS":null,"NGAY_HOI":"2021-01-02T16:33:26.000+00:00","DIENTHOAI_LH":"0986732053","TRAM_VT":"Tổ Kỹ thuật Ngô Quyền","TEN_TB":"Phan Quang Thế","DIACHI_LD":"3/140, Số chẵn: từ số nhà:2->136, Đường Lê Lai, Phường Lạc Viên, Quận Ngô Quyền, Hải Phòng","NGAY_HT":"2020-12-31T16:28:48.000+00:00","NGUOI_TL":"0986732053","NGUOI_XL":null,"TEN_PLKH":"CB3","CHITIEU_TG":24,"GHICHU_TB":"KH báo modem không sáng đèn-ycht","GHICHU_XL":"| 31/12/2020 16:28: Ok","LYDO_HONG":"Cáp/mạng nội bộ khách hàng: LAN Switch, Router, PC (lỗi hệ điều hành/bị virut …), TV …","SO_DT":"0986732053 0986732053","LAN_KS":1,"TEN_LOAIHD":"Báo hỏng","TEN_KIEULD":"Xử lý sự cố thuê bao: Fiber","PHIEU_ID":4443151,"NGAYHEN_TU":null,"NGAYHEN_DEN":null}]}}}'
      //   );

      if (response.data.error_code === "BSS-00000000") {
        if (
          response.data.data !== undefined &&
          response.data.data.data !== undefined &&
          response.data.data.data.length > 0
        ) {
          this.items = response.data.data.data;

          if (this.items) {
            this.items.forEach((element) => {
              element.isCheck = false;
            });
          }

          this.pageInfo = {
            page: response.data.data.page,
            maxSize: response.data.data.maxSize,
            totalElement: response.data.data.totalElement,
            totalPages: response.data.data.totalPages,
            sort: response.data.data.sort,
            propertiesSort: response.data.data.propertiesSort,
          };
        } else {
          this.$root.$toast.warning("Không tìm thấy dữ liệu thỏa điều kiện.");
        }
      } else {
        this.$root.$root.toastError(respone.data.message);
      }
    },
    onChangeText(key, value) {
      this.searchArr = this.searchArr.filter((x) => x.searchField !== key);
      if (value) {
        this.searchArr.push({
          searchField: key,
          searchValue: value,
        });
      }
    },
    onClearSearch() {
      this.displayItems = this.items;
    },
    filterChange: function () {
      if (this.currentSelectedRow > -1) this.setCurrentSelectedRow(0);
    },
    doSelectNearby: async function (next) {
      if (next) {
        if (this.currentSelectedRow < this.pageSize - 1) {
          this.currentSelectedRow += 1;
        } else {
          if (this.pageInfo.page < this.pageInfo.totalPages)
            await this.nextPage();
        }
      } else {
        if (this.currentSelectedRow > 0) {
          this.currentSelectedRow -= 1;
        } else {
          if (this.pageInfo.page > 0) await this.previousPage();
        }
      }
    },
    getCurrentSelectedRow: function () {},
    setCurrentSelectedRow: function (value) {
      //   if (this.currentSelectedRow == value) {
      //     if (value > this.pageRowCount - 1) value = this.pageRowCount - 1;
      //   } else {
      //     if (value > this.pageRowCount - 1 || value < 0) {
      //       if (this.currentSelectedRow == -1) {
      //         if (value > this.currentSelectedRow) value = 0;
      //         else value = this.pageRowCount - 1;
      //       } else value = -1;
      //     }
      //   }
      //   this.currentSelectedRow = value;
      //   this.$emit(
      //     "selectedRowChanged",
      //     !(this.dataItems == null || this.dataItems.length == 0)
      //       ? this.currentSelectedRow > -1
      //         ? this.dataItems[this.currentSelectedRow]
      //         : null
      //       : null
      //   );
    },
    setPageSize: async function (value) {
      this.pageInfo.maxSize = value;
      await this.fetchData();
    },
    nextPage: async function () {
      this.pageInfo.page += 1;
      await this.fetchData();
      this.currentSelectedRow = 0;
    },
    lastPage: async function () {
      this.pageInfo.page = this.pageInfo.totalPages;
      await this.fetchData();
      this.currentSelectedRow = 0;
    },
    previousPage: async function () {
      this.pageInfo.page -= 1;
      await this.fetchData();
      this.currentSelectedRow = this.displayItemsGetter.length - 1;
    },
    firstPage: async function () {
      this.pageInfo.page = 1;
      await this.fetchData();
      this.currentSelectedRow = this.displayItemsGetter.length - 1;
    },
  },
};
</script>
