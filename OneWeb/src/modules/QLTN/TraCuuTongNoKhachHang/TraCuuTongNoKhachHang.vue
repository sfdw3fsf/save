<template src='./TraCuuTongNoKhachHang.html'></template>
<style scoped src='./TraCuuTongNoKhachHang.scss'></style>
<script>
import { mapActions, mapState } from "vuex";
import breadcrumb from "@/components/breadcrumb";
import KyCuoc from "../components/KyCuoc.vue";
import moment from "moment";
import ModalXuatDuLieu from "../../ECMS/TraCuuSplitterTheoPortOLT/ModalXuatDuLieu/index.vue";
import API from "../api/TraCuuTongNoKhachHangAPI";
import { currency } from '@/filters/currency'

export default {
  components: { breadcrumb, appKyCuoc: KyCuoc, appModalXuatDuLieu: ModalXuatDuLieu },
  name: "TraCuuTongNoKhachHang",
  mounted() {
    this.$refs.ttTraCuu.focus();
  },
  computed: {
    ...mapState("tracuutongnokhachhang", [
      "thongTinTT",
    ]),
    ...mapState("qltnCommon", ["danhsachDonVi"]),
    dsThuocTinh() {
      return this.fieldsTongNoKH.map((x) => x.fieldName);
    },
  },
  data() {
    return {
      captionTemplate : '<div>${key}</div>',
      selectedThueBao: "tt",
      selectedDonVi: null,
      thongtinKyHoaDon: "",
      fieldsTongNoKH: [
        { fieldName: "GROUP", headerText: "Mã thanh toán", allowFiltering: false, isGroupedColumn: true, captionTemplate: this.captionTemplate },
        { fieldName: "MA_TT", visible: false, headerText: "Mã thanh toán", allowFiltering: false},
        { fieldName: "CHUKYNO", headerText: "Chu kỳ nợ", allowFiltering: true },
        { fieldName: "MA_TB", headerText: "Số máy/Acc", allowFiltering: true },
        { fieldName: "NO_CD", headerText: "Nợ cố định", allowFiltering: true, type: "number", format: "N0"  },
        { fieldName: "NO_DD", headerText: "Nợ di động", allowFiltering: true, type: "number", format: "N0" },
        { fieldName: "NO_IN", headerText: "Nợ internet",  allowFiltering: true, type: "number",format: "N0"},
        { fieldName: "NO_GP", headerText: "Nợ Gpon", allowFiltering: true, type: "number",format: "N0"},
        { fieldName: "NO_IMS",  headerText: "Nợ IMS", allowFiltering: true, type: "number", format: "N0"},
        { fieldName: "NO_MGWAN",
          headerText: "Nợ Megawan",
          allowFiltering: true,
          type: "number",
          format: "N0"
        },
        {
          fieldName: "NO_TSL",
          headerText: "Nợ TSL",
          allowFiltering: true,
          type: "number",
          format: "N0"
        },
        {
          fieldName: "NO_KH",
          headerText: "Nợ khác",
          allowFiltering: true,
          type: "number",
          format: "N0"
        },
        {
          fieldName: "TONGNO",
          headerText: "Tổng nợ",
          allowFiltering: true,
          type: "number",
          format: "N0"
        },
        {
          fieldName: "VIETTAT",
          headerText: "Tiền",
          allowFiltering: true,
          cellCssClass: "text-right",
        },
      ],
      fieldsTongTraKH: [
        {
          fieldName: "TEN",
          headerText: "KMTT/Số máy-Acc",
          allowFiltering: true,
        },
        {
          fieldName: "NODK",
          headerText: "Nợ ĐK",
          allowFiltering: true,
          type: "number",
          format: "N0"
        },
        {
          fieldName: "NOPS",
          headerText: "Nợ PS",
          allowFiltering: true,
          type: "number",
          format: "N0"
        },
        {
          fieldName: "TRADK",
          headerText: "Trả ĐK",
          allowFiltering: true,
          type: "number",
          format: "N0"
        },
        {
          fieldName: "TRAPS",
          headerText: "Trả PS",
          allowFiltering: true,
          type: "number",
          format: "N0"
        },
        {
          fieldName: "DCDK",
          headerText: "Điều chỉnh ĐK",
          allowFiltering: true,
          type: "number",
          format: "N0"
        },
        {
          fieldName: "DCPS",
          headerText: "Điều chỉnh PS",
          allowFiltering: true,
          type: "number",
          format: "N0"
        },
        {
          fieldName: "CONNODK",
          headerText: "Còn nợ ĐK",
          allowFiltering: true,
          type: "number",
          format: "N0"
        },
        {
          fieldName: "CONNOPS",
          headerText: "Còn nợ PS",
          allowFiltering: true,
          type: "number",
          format: "N0"
        },
        // {
        //   key: 'NO_KHAC',
        //   label: 'Nợ khác',
        //   thClass: 'center'
        // }
      ],
      selected: "1",
      tenTT: "",
      ttTraCuu: "",
      diaChiTT: "",
      hinhThucTT: "",
      selectTab: 0,
      dataTC: "",
      selectedKieu: "1",
      maThanhToan: "",
      totalItemsTongNo:0,
      resetTongNo:1,
      totalItemsTongTra:0,
      resetTongTra:1,
      dsTongNo: [],
      dsTongTra: []
    };
  },
  methods: {
    ...mapActions("tracuutongnokhachhang", [
      "getThongTinTTThueBao",
      "getThongTinTTThanhToan",
      "clearData",
    ]),
    ...mapActions("qltnCommon", ["getDanhSachDonVi"]),
    changeTab(type) {
      this.selectTab = type;
    },
    fetchTraCuu() {
      if (this.selected === "1") {
        this.TraCuuTheoThongTin();
      } else {
        this.TraCuuTheoDonVi();
      }
    },
    TraCuuTheoDonVi() {
      // this.loading(true)
      this.resetTongNo += 1;
      this.resetTongTra += 1;
      this.fetchTongNo(0, 10);
      this.fetchTongTra(0, 10);
    },
    async TraCuuTheoThongTin() {
      if(this.ttTraCuu !== null)
        this.ttTraCuu = this.ttTraCuu.trim()

      if (this.ttTraCuu === "") {
        this.$root.toastError("Bạn chưa nhập thông tin tra cứu");
        this.$refs.ttTraCuu.focus();
        this.tenTT = "";
        this.diaChiTT = "";
        return;
      }
      this.loading(true);
      let data = {
        pKyCuoc: this.thongtinKyHoaDon,
        pMaTB: this.selectedThueBao === "tb" ? this.ttTraCuu : "",
        pMaTT: this.selectedThueBao === "tt" ? this.ttTraCuu : "",
      };
      let result = null;
      if (this.selectedThueBao === "tb") {
        const res = await this.getThongTinTTThueBao(data);
        if (res.data.data.length === 0) {
          this.loading(false);
          this.$refs.ttTraCuu.focus();
          this.$root.toastError(
            "Không tìm thấy khách hàng có mã thuê bao:" + this.ttTraCuu
          );
          this.$refs.ttTraCuu.select();
          this.clearData();
          this.resetThongTinTT();
          return;
        } else {
          result = res.data.data[0];
          this.maThanhToan = res.data.data[0].MA_TT;
        }
      } else {
        const res = await this.getThongTinTTThanhToan(data);
        if (res.data.data.length === 0) {
          this.loading(false);
          this.$refs.ttTraCuu.focus();
          this.$root.toastError("Không có mã thanh toán này!");
          this.$refs.ttTraCuu.select();
          this.clearData();
          this.resetThongTinTT();
          return;
        } else {
          result = res.data.data[0];
          this.maThanhToan = res.data.data[0].MA_TT;
        }
      }
      this.insertThongTinTT(result);
    },
    insertThongTinTT(data) {
      if (data) {
        this.tenTT = data.TEN_TT;
        this.diaChiTT = data.DIACHI_TT;
        this.hinhThucTT = data.HINHTHUC_TT;
        this.dataTC = `Nhân viên TC: ${data.MANV_TC}, Tuyến thu: ${data.MA_TUYENTHU} - ${data.TEN_TUYEN}`;
      } else {
        this.resetThongTinTT();
      }
      this.resetTongNo += 1;
      this.resetTongTra += 1;
      this.fetchTongNo(0, 10);
      this.fetchTongTra(0, 10);
    },
    resetThongTinTT() {
      this.tenTT = "";
      this.diaChiTT = "";
      this.hinhThucTT = "";
      this.dataTC = "";
      this.maThanhToan = " ";
      this.dsTongNo = [];
      this.dsTongTra = [];
    },
    async fetchTongNo(page, size) {
      let data = {
        pKyCuoc: this.thongtinKyHoaDon,
        pMaTT: this.selected === "1" ? this.maThanhToan : "",
        pDonViId: this.selected === "2" ? this.selectedDonVi : "",
        pageNum: page + 1,
        pageSize: size,
        totalRow: 1,
      };
      try {
        if (this.selectTab == 0) this.loading(true);
        const response = await API.getDanhSachTongNoKH(this.axios, data);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data.data.length > 0
        ) {
          //this.dsTongNo = response.data.data.data;
          let group_matt = response.data.data.data.map(x => x.MA_TT)
          let uniqueMATT = [...new Set(group_matt)]
          let ds = [...response.data.data.data]
          uniqueMATT.forEach(item => {
            let f = ds.filter(x => x.MA_TT === item)
            let total = f.reduce((acc, curr) => {
              return acc = Number(curr.TONGNO) + acc
            }, 0)
            for(let i = 0; i < ds.length; i++)
            {
              if(ds[i]['MA_TT'] === item)
              {
                ds[i]['GROUP'] = item + ' - Tổng nợ ' + currency(total)
              }
            }
          })

          this.dsTongNo = ds
          /*
          let rs = response.data.data.data.forEach(item => {
            let t = item
            t.GROUP = item.MA_TT
            let f =
          })
          */
          this.totalItemsTongNo = response.data.data.totalElement;
        } else {
          this.dsTongNo = [];
          this.totalItemsTongNo = 0;
          if (this.selectTab == 0) {
            this.$root.toastError(
              "Không tìm thấy dữ liệu tổng nợ thỏa điều kiện!"
            );
          }
        }
      } catch (error) {
        this.dsTongNo = [];
        this.totalItemsTongNo = 0;
        if (this.selectTab == 0) this.$root.toastError(error.data.message_detail);
      } finally {
        if (this.selectTab == 0) this.loading(false);
      }
    },
    tongno_gridChanged(args){
        this.fetchTongNo(args.pageIndex, args.pageSize);
    },
    async fetchTongTra(page, size) {
      let data = {
        pKyCuoc: this.thongtinKyHoaDon,
        pMaTT: this.selected === "1" ? this.maThanhToan : "",
        pMaTB: this.selected === "2" ? 0 : "",
        pKieu: this.selectedKieu,
        pageNum: page + 1,
        pageSize: size,
        totalRow: 1,
      };
        try {
        if (this.selectTab == 1) this.loading(true);
        const response = await API.getDanhSachTongTraKH(this.axios, data);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data.data.length > 0
        ) {
          this.dsTongTra = response.data.data.data;
          this.totalItemsTongTra = response.data.data.totalElement;
        } else {
          this.dsTongTra = [];
          this.totalItemsTongTra = 0;
          if (this.selectTab == 1) {
            this.$root.toastError(
              "Không tìm thấy dữ liệu tổng trả thỏa điều kiện!"
            );
          }
        }
      } catch (error) {
        this.dsTongTra = [];
        this.totalItemsTongTra = 0;
        if (this.selectTab == 1) this.$root.toastError(error.data.message_detail);
      } finally {
        if (this.selectTab == 1) this.loading(false);
      }
    },
    tongtra_gridChanged(args){
        this.fetchTongTra(args.pageIndex, args.pageSize);
    },
    handleKyHoaDon(e) {
      this.thongtinKyHoaDon =
        moment(e.p_kyhoadon).format("YYYYMM") + e.p_chuky_no;
    },
    XuatFile(){
      if(this.dsTongNo.length <= 0){
         this.$root.toastError("Không có dữ liệu để xuất File!");
         return;
      }
      this.$bvModal.show("modal-xuat-dulieu");
    },
  },
  async created() {
    this.clearData();
    const res = await this.getDanhSachDonVi();
    if (res.data.error_code === "BSS-00000000") {
      this.selectedDonVi = res.data.data[0].DONVI_ID;
    }
  },
};
</script>
