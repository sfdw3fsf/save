<template src="./TraCuuThueBaoKhaoSat.html"></template>
<style src="./TraCuuThueBaoKhaoSat.scss"></style>

<script>
import { mapActions, mapState } from "vuex";
import moment from "moment";
import select2 from "@/components/Select2.vue";
import API from "../api/TraCuuThueBaoKhaoSatApi";
import frmKhaoSatKH from "../KhaoSat/frmKhaoSatKH.vue"
import frmKhaoSatKH_LM from "../KhaoSat/frmKhaoSatKH_LM.vue"
import frmKhaoSatKH_HDTB from "../KhaoSat/frmKhaoSatKH_HDTB.vue"
import downloadexcel from "vue-json-excel";
import XuatFile from "../../ECMS/TraCuuSplitterTheoPortOLT/ModalXuatDuLieu";
import HuongDan from "../utils/HuongDan.vue"
import CSKHGrid from "../utils/CSKHGrid"
import { Workbook } from "@syncfusion/ej2-excel-export/index";
import SearchAccount from "@/modules/search/subscriber/SearchAccount";
import {
  Edit,
  Sort,
  Filter,
  ForeignKey,
  Resize,
  Freeze,
  Page,
  Group,
} from "@syncfusion/ej2-vue-grids";
export default {
  provide: {
    treegrid: [Page, Filter, Sort, Resize],
    grid: [Group]
  },
  components: {
    downloadexcel,
    appSelect2: select2,
    frmKhaoSatKH,
    frmKhaoSatKH_LM,
    frmKhaoSatKH_HDTB,
    appModalXuatDuLieu: XuatFile,
    appHuongDan: HuongDan,
    CSKHGrid: CSKHGrid,
    SearchAccount
  },
  name: "TraCuuThueBaoKhaoSat",
  data() {
    return {
      today: new Date(),

      pTxtThoiGianKSTu: new Date(),
      pTxtThoiGianKSDen: new Date(),
      pTxtNgayKS: '',
      pTxtNgayYC: '',
      pTxtMaTB: "",
      pTxtNgayHT: '',
      pTxtGhiChu: "",

      pCboAllCauHoi: false,

      p_TBTL_ID: 0,
      p_BAOHONG_ID: 0,
      p_HDKH_ID: 0,
      pIsAudio: 0,

      pDsChiTietTraLoi: [],
      pDsChiTietTraLoiGridData : [],
      pCurrentKhaoSatSelected: {},

      KHAOSAT_HDTB: false,
      isLoadingFinish: false,
      //
      columnDsThueBao: [
          { fieldName: "BAOHONG_ID", headerText: "BAOHONG_ID", allowFiltering: true, allowSorting: false, },
          { fieldName: "HDKH_ID", headerText: "HDKH_ID", allowFiltering: true, allowSorting: false, },
          { fieldName: "MA_LT", headerText: "MA_LT", allowFiltering: true, allowSorting: false, },
          { fieldName: "MA_TB", headerText: "MA_TB", allowFiltering: true, allowSorting: false, },
          { fieldName: "TEN_TB", headerText: "TEN_TB", allowFiltering: true, allowSorting: false, },
          { fieldName: "DIACHI_LD", headerText: "DIACHI_LD", allowFiltering: true, allowSorting: false, },
          { fieldName: "NGUOI_TL", headerText: "NGUOI_TL", allowFiltering: true, allowSorting: false, },
          { fieldName: "DIENTHOAI_LH", headerText: "DIENTHOAI_LH", allowFiltering: true, allowSorting: false, },
          { fieldName: "NGAY_HOI", headerText: "NGAY_HOI", allowFiltering: true, allowSorting: false, },
          { fieldName: "LOAIHINH_TB", headerText: "LOAIHINH_TB", allowFiltering: true, allowSorting: false, },
          { fieldName: "TOC_DO", headerText: "TOC_DO", allowFiltering: true, allowSorting: false, },
          { fieldName: "DONVI_NHAN", headerText: "DONVI_NHAN", allowFiltering: true, allowSorting: false, },
          { fieldName: "NGAY_YC", headerText: "NGAY_YC", allowFiltering: true, allowSorting: false, },
          { fieldName: "NGAY_HT", headerText: "NGAY_HT", allowFiltering: true, allowSorting: false, },
          { fieldName: "GHICHU", headerText: "GHICHU", allowFiltering: true, allowSorting: false, },
          { fieldName: "FILENAME", headerText: "FILENAME", allowFiltering: true, allowSorting: false, },
          { fieldName: "KHL", headerText: "KHL", allowFiltering: true, allowSorting: false, },
          { fieldName: "NGUOI_KS", headerText: "NGUOI_KS", allowFiltering: true, allowSorting: false, },
          { fieldName: "LOAI_HD", headerText: "LOAI_HD", allowFiltering: true, allowSorting: false, },
          { fieldName: "TEN_LOAIKH", headerText: "TEN_LOAIKH", allowFiltering: true, allowSorting: false, },
          { fieldName: "HDTB_ID", headerText: "HDTB_ID", allowFiltering: true, allowSorting: false, },
          { fieldName: "IPCC_ID", headerText: "IPCC_ID", allowFiltering: true, allowSorting: false, },
          { fieldName: "TRANGTHAI_KS", headerText: "TRANGTHAI_KS", allowFiltering: true, allowSorting: false, },
          { fieldName: "LYDO_KTL", headerText: "LYDO_KTL", allowFiltering: true, allowSorting: false, },
      ],
      nhanVienColumns: [
        {
            field: 'CHATLUONG_NV',
            headerText: 'Chất lượng',
            cssClass: "text-center",
            format: 'yMd',
            width: 120,
        },
        {
            field: 'KHL_NVKT_NV',
            headerText: 'KHL NVKT',
            width: 120,
        },
        {
            field: 'KHL_NVKD_NV',
            headerText: 'KHL NVKD',
            width: 120,
        }
      ],
      dichVuColumns: [
        {
            field: 'CHATLUONG_DV',
            headerText: 'Chất lượng',
            format: 'yMd',
            width: 120,
        },
        {
            field: 'TRALOI_PHU',
            headerText: 'Trả lời phụ',
            width: 120,
        }
      ],
      tongHopColumns: [
        {
            field: 'DOHAILONG',
            headerText: 'Độ hài lòng',
            format: 'yMd',
            width: 120,
        },
        {
            field: 'LYDO_KTL',
            headerText: 'Lý do KTL',
            width: 120,
        },
      ],
      fieldNoiDungKhaoSat: [
        {
          fieldName: "CAUHOI",
          headerText: "${key}",
          allowFiltering: false,
          isGroupedColumn: true,
        },
        {
          fieldName: "TRALOI",
          headerText: "Trả lời",
          allowFiltering: true,
        },
        {
          fieldName: "NOIDUNG",
          headerText: "Mô tả thêm",
          allowFiltering: true,
        },
      ],
      exportExcelFields: {
          "Số máy/ACC": "MA_TB",
          "Số ảo": "MA_LT",
          "Loại hình": "LOAIHINH_TB",
          "Tốc độ": "TOC_DO",
          "Loại YC": "LOAI_YC",
          "Người trả lời": "NGUOI_TL",
          "Số LH": "DIENTHOAI_LH",
          "Ngày YC": "NGAY_YC",
          "Ngày HT": "NGAY_HT",
          "Lần KS": "LAN_KS",
          "Ngày KS": "NGAY_HOI",
          "Trạng thái": "TRANG_THAI",
          "Loại KH": "LOAIKH",
          "Tên thuê bao": "TEN_TB",
          "Địa chỉ lắp đặt": "DIACHI_LD",
          "Ghi chú": "GHICHU",
          "Audio": "FILENAME",
          "Chất lượng": "CHATLUONG_NV",
          "KHL NVKT": "KHL_NVKT_NV",
          "KHL NVKD": "KHL_NVKD_NV",
          "Chất lượng": "CHATLUONG_DV",
          "Trả lời phụ": "TRALOI_PHU",
          "Độ hài lòng": "DOHAILONG",
          "Lý do KTL": "LYDO_KTL",
      },
      columnTemplate: function () {
        return {
          template: {
            template:
              '<span :id="data.index" :style="{display: display }" style="font-size: 12px;" class="col-mark-selected-row fa fa-play text-main"></span>',
            data() {
              return {
                data: {},
              };
            },
            computed: {
              display() {
                return parseInt(this.data.index) ==
                  this.$parent.getSelectedRowIndexes()[0]
                  ? "block"
                  : "none";
              },
            },
          },
        };
      },
      filterBarTemplate: {
        create: function (args) {
          let div = document.createElement("div");
          div.classList.add("input-icon-right");

          let input = document.createElement("input");
          input.classList.add("form-control");
          input.id = args.column.foreignKeyField;

          let span = document.createElement("span");
          span.classList.add("icon", "nc-icon-outline", "ui-1_zoom");

          div.append(input);
          div.append(span);

          return div;
        },
        write: function (args) {
          args.element
            .querySelector("input")
            .addEventListener("input", (args) => {
              let value = args.currentTarget.value;
              value = !!value ? value.trim() : value;

              if (!value) {
                this.parent.removeFilteredColsByField(args.currentTarget.id);
              } else {
                this.parent.filterByColumn(
                  args.currentTarget.id,
                  "contains",
                  value
                );
              }
            });
        },
      },
      filterValues: [],
      txtTongSoThueBao: 'Số lượng thuê bao không hài lòng / Tổng thuê bao khảo sát:',
      popupComponent: null,
      thongtinColumns: [
        { field: "MA_TB", headerText: "Số máy/Acc", allowFiltering: true, allowSorting: false, },
        { field: "MA_LT", headerText: "Số ảo", allowFiltering: true, allowSorting: false, },
        { field: "LOAIHINH_TB", headerText: "Loại hình", allowFiltering: true, allowSorting: false, },
        { field: "TOC_DO", headerText: "Tốc độ", allowFiltering: true, allowSorting: false, },
        { field: "LOAI_YC", headerText: "Loại YC", allowFiltering: true, allowSorting: false, },
        { field: "NGUOI_TL", headerText: "Người trả lời", allowFiltering: true, allowSorting: false, },
        { field: "DIENTHOAI_LH", headerText: "Số LH", allowFiltering: true, allowSorting: false, },
        { field: "NGAY_YC", headerText: "Ngày YC", allowFiltering: true, allowSorting: false, },
        { field: "NGAY_HT", headerText: "Ngày HT", allowFiltering: true, allowSorting: false, },
        { field: "LAN_KS", headerText: "Lần KS", allowFiltering: true, allowSorting: false, },
        { field: "NGAY_HOI", headerText: "Ngày KS", allowFiltering: true, allowSorting: false, },
        { field: "TRANGTHAI_KS", headerText: "Trạng thái", allowFiltering: true, allowSorting: false, },
        { field: "LOAIKH", headerText: "Loại KH", allowFiltering: true, allowSorting: false, },
        { field: "TEN_TB", headerText: "Tên thuê bao", allowFiltering: true, allowSorting: false, },
        { field: "DIACHI_LD", headerText: "Địa chỉ lắp đặt", allowFiltering: true, allowSorting: false, },
        { field: "GHICHU", headerText: "Ghi chú", allowFiltering: true, allowSorting: false, },
        { field: "FILENAME", headerText: "Audio", allowFiltering: true, allowSorting: false, template: ()=> {return this.audioTemplate()}}
      ],
      audioTemplate: function () {
        return {
          template: {
            template: `
                <div class="e-unboundcelldiv" style="display: inline-block;width: 100%;text-align: center;" v-if="data.FILENAME == 1">
                    <a href="javascript: void(0)" @click="playAudio">
                    <i class="nc-icon-glyph files_audio" style="zoom: 2"></i>
                    </a>
                </div>
                `,
            data() {
              return {
                data: {},
              };
            },
            computed: {
              parent() {
                return this.$parent.$parent.$parent;
              },
            },
            methods: {
              async playAudio(){
                await this.$parent.$parent.$parent.handleAudio(this.data,1)
              },
            },
          },
        };
      },
      srcAudio:'',
    };
  },
  computed: {
    ...mapState("cskhCommon", ["cayDonVi"]),
    ...mapState("tracuuthuebaokhaosat", ["DsCauHoi", "DsThueBaoKS"]),
    treeDonViFields() {
      return {
        dataSource: this.cayDonVi,
        id: "DONVI_ID",
        text: "TEN_DV",
        parentID: "DONVI_CHA_ID",
        hasChildren: "hasChild"
      }
    },
    treeCauHoiFields() {
      return {
        dataSource: this.DsCauHoi,
        id: "ID",
        text: "NOIDUNG",
        parentID: "PARENT_ID",
        hasChildren: "hasChild",
        tooltip: "NOIDUNG"
      }
    },
    // txtTongThueBaoKhaoSat() {
    //   return `Số lượng thuê bao không hài lòng / Tổng thuê bao khảo sát: 19001166 Miền Bắc - Báo hỏng: 0/19; Tổ Giao dịch Nam thành phố: 0/1; Kế toán và thu nợ Mỹ Lộc: 0/1; Tổ PT Trả sau nam Trực: 0/1; Tổ giao dịch Ý Yên: 0/1; Viễn Thông Nam Định: 0/73; Viễn Thông Nam Định: 0/73; Viễn Thông Nam Định: 0/73; Phòng bán hàng Thành phố Nam Định: 0/73`;
    // }
    dsThuocTinh(){
        return this.columnDsThueBao.map(item=>item.fieldName)
    }
  },
  async created() {
    this.clearData()
    if (this.$route.query.maTB) {
      this.pTxtMaTB = this.$route.query.maTB
    }
    await this.getCayDonViData_ver2();
    await this.getDsCauHoi();
  },
  async mounted() {
    try {
      this.$root.showLoading(true);

      this.KHAOSAT_HDTB = await this.getThamSoMacDinh({dsMaTs: 'KHAOSAT_HDTB'})
      await this.getTongThueBaoKhaoSat();
      this.isLoadingFinish = true
    } catch (e) {
      console.log(e);
    } finally {
      this.$root.showLoading(false);
    }

  },
  methods: {
    ...mapActions("cskhCommon", ["getCayDonViData_ver2", "getThamSoMacDinh"]),
    ...mapActions("tracuuthuebaokhaosat", [
      "getDsCauHoi",
      "getDsThueBaoKS",
      "clearData"
    ]),
    async handleAudio(data,type){
        const urlFile = data.URL
        const matb = data.MA_TB
        try {
            this.loading(true);
            const baseUrl = process.env.API;
            await this.axios({
            url: `${baseUrl}/web-cskh/api/common/get-recording-file`,
            method: "POST",
            responseType: "blob",
            data: {
                url: urlFile,
                ipcc_id: data.IPCC_ID, //146991973
                may_cn: data.NGUOI_TL //await this.$root.token.getMachine()
            },
            }).then(async (response) => {
                try {
                    let res = await JSON.parse(await response.data.text())
                    if(!res.success){
                        this.$root.toastError("Không có dữ liệu âm thanh!");
                        return;
                    }
                } catch (error) {
                    // console.log(error)
                }

                let blob = new Blob([response.data], { type: "audio/wav" });
                let blobUrl = URL.createObjectURL(blob);
                if(type == 1){
                    this.$nextTick(()=> this.srcAudio = blobUrl)
                    this.$bvModal.show("popup-audio");
                } else {
                    let a = document.createElement('a')
                    a.href = blobUrl
                    a.download = matb;
                    a.click();
                }
            });
        } catch (error) {
            this.$root.toastError("Có lỗi xảy ra. Mời thử lại!");
        } finally {
            this.loading(false);
        }
    },
    async gridThueBaoKS_selectedRowChanged(dataItem) {
    //   console.log('gridThueBaoKS_selectedRowChanged', dataItem)
      this.pCurrentKhaoSatSelected = dataItem
      this.p_HDKH_ID = 0
      this.p_BAOHONG_ID = 0
      if (dataItem) {
        this.p_TBTL_ID = dataItem.TBTL_ID;
        this.pIsAudio = dataItem.FILENAME;
        this.pTxtMaTB = dataItem.MA_TB;
        this.pTxtNgayKS = dataItem.NGAY_HOI;
        this.pTxtNgayYC = dataItem.NGAY_YC;
        this.pTxtNgayHT = dataItem.NGAY_HT;
        this.pTxtGhiChu = dataItem.GHICHU;
        this.p_HDKH_ID = dataItem.HDKH_ID || 0
        this.p_BAOHONG_ID = dataItem.BAOHONG_ID || 0
        await this.loadDsChiTiet();
      }
    },
    async onTimKiemClicked(isKeyPress) {
      let treeDonVi = document.getElementById("treeObj").ej2_instances[0];
      let donViId = treeDonVi.selectedNodes[0];

      if (!isKeyPress) {
        if (!this.pTxtThoiGianKSTu || !this.pTxtThoiGianKSDen) {
          this.$root.toastError("Từ ngày - Đến ngày không được để trống!")
          return
        }
      }

      if (!donViId) {
        this.$root.toastError("Chưa chọn đơn vị!");
        return;
      }

      let treeCauHoiObj = document.getElementById("treeCauHoiObj")
        .ej2_instances[0];
      let cauHoiId = treeCauHoiObj.selectedNodes[0];
      let CHId = 0;
      let TLId = 0;
      if (this.pCboAllCauHoi && cauHoiId) {
        let cauHoi = this.DsCauHoi.find(x => x.ID == cauHoiId);
        if (cauHoi) {
          CHId = cauHoi.CAUHOI_ID;
          TLId = cauHoi.TRALOI_ID;
        }
      }

      this.pTxtNgayKS = ""
      this.pTxtNgayYC = ""
      this.pTxtNgayHT = ""
      this.pTxtGhiChu = ""

      // let cauHoiId = this.pCboAllCauHoi ?
      //@fakedata
      let timKiemData = {
        donViId: donViId,
        tuNgay: isKeyPress ? '' : moment(this.pTxtThoiGianKSTu).format("DD/MM/YYYY"),
        denNgay: isKeyPress ? '' : moment(this.pTxtThoiGianKSDen).format("DD/MM/YYYY"),
        cauHoiId: CHId,
        traLoiId: TLId,
        maTb: isKeyPress ? this.pTxtMaTB : '',
        pageNo: 1,
        pageSize: 10000
      }

      try {
        this.$root.showLoading(true);
        this.pDsChiTietTraLoi = []
        await this.getDsThueBaoKS(timKiemData);
        await this.getTongThueBaoKhaoSat();
        // if (this.DsThueBaoKS.length > 0) {
        //   await this.gridThueBaoKS_rowClicked(0, this.DsThueBaoKS[0])
        // }
      } catch (e) {
        console.log(e);
        this.$root.toastError(e.message);
      } finally {
        this.$root.showLoading(false);
      }
    },
    async loadDsChiTiet() {
      try {
        this.$root.showLoading(true);
        this.pDsChiTietTraLoi = []
        let response = await API.getChiTietTraLoi(this.axios, {tbtlId: this.p_TBTL_ID})

        if (response.data.error_code === "BSS-00000000") {
          this.pDsChiTietTraLoi = response.data.data || []
          if (this.pDsChiTietTraLoi && this.pDsChiTietTraLoi.length > 0) {
            // this.pDsChiTietTraLoi.forEach(e => e.hasChildData = true)
          this.pDsChiTietTraLoiGridData = this.formatData(this.pDsChiTietTraLoi)
          }
        }
      } catch (e) {
        console.log(e);
      } finally {
        this.$root.showLoading(false);
      }
    },
    formatData(input) {
      let output = [];

      var tmp = input.reduce(function(r, a) {
        r[a.CAUHOI] = r[a.CAUHOI] || [];
        r[a.CAUHOI].push(a);
        return r;
      }, Object.create(null));

      output = Object.keys(tmp).map(function(key) {
        return {
          TRALOI: key,
          CAUHOI: key,
          Childs: tmp[key],
          isOpen: true,
          isSelected: false
        };
      });
      return output;
    },
    queryCellInfoEvent: function(args) {
      if (args.column.field === 'CAUHOI'){
        //columns span ten trung tam
        if (args.data.level === 0){
          args.colSpan = 2 // 4 column hien thi
        }

      }
    },
     isEmpty(obj) {
       return Object.keys(obj).length === 0;
    },
    async onChiTietKSClicked() {
      //@fakedata
      // this.$refs.frmKhaoSatKH_LM.hdkhId =  this.p_HDKH_ID
      // this.$refs.frmKhaoSatKH_LM.hdkhId =  1406008//this.p_HDKH_ID
      // this.$refs.frmKhaoSatKH_LM.openDialog()
     try {
      if(this.isEmpty(this.pCurrentKhaoSatSelected)) return this.$toast.error("Vui lòng tìm kiếm thuê bao KS");
      let hdkh_id = this.pCurrentKhaoSatSelected.HDKH_ID || 0
      let baohong_id = this.pCurrentKhaoSatSelected.BAOHONG_ID || 0
      let hdtb_id = this.pCurrentKhaoSatSelected.HDTB_ID || 0
      if (baohong_id > 0) {
        //frmKhaoSatKH
        console.log('this.p_TBTL_ID', this.p_TBTL_ID)
        this.$refs.frmKhaoSatKH.tbtlId = this.p_TBTL_ID
        this.$refs.frmKhaoSatKH.baoHongId = this.p_BAOHONG_ID
        this.$refs.frmKhaoSatKH.openDialog()
        return
      }

      if (this.KHAOSAT_HDTB) {
        this.$refs.frmKhaoSatKH_HDTB.hdtbId = hdtb_id
        this.$refs.frmKhaoSatKH_HDTB.openDialog()
        return
      }

      if (hdkh_id > 0) {
        this.$refs.frmKhaoSatKH_LM.hdkhId = this.p_HDKH_ID
        this.$refs.frmKhaoSatKH_LM.openDialog()
        return
      }
     } catch (error ) {

     }

    },
    onDataBoundTienTrinh() {
      let grid = this.$refs["grid-noidung-ks"];
      // grid.groupColumn("CAUHOI");
      // grid.selectRow(0);

      if (grid.getDataRows().length > 0) {
        // grid.selectRow(0)
      } else {
          let emptyRows = grid.$el.querySelectorAll('.e-emptyrow')
          let cell0 = emptyRows[0].cells[0]
          cell0.setAttribute('colspan', 4)
          cell0.style.setProperty('text-align', 'center', 'important')
      }
    },
    onActionCompleteTienTrinh(args) {
      this.onActionComplete(args);

      this.$refs["grid-noidung-ks"].$el
        .querySelectorAll(".e-indentcell")
        .forEach((item) => {
          item.classList.add("tree-first", "bg-white");

          let div1 = document.createElement("div");
          div1.classList.add("tree-line");

          let div2 = document.createElement("div");
          div2.classList.add("tree-indent");

          item.append(div1);
          item.append(div2);
        });
    },
    onRowSelected(args) {
      let refValue = args.row.closest(".e-grid").getAttribute("ref-value");
      this.$refs[refValue].$el
        .querySelectorAll(".col-mark-selected-row")
        .forEach((item) => {
          item.style.display =
            args.rowIndex == parseInt(item.id) ? "block" : "none";
        });
    },
    onActionComplete(args) {
      if (args.requestType == "filtering") {
        let filterObj = args.currentFilterObject;
        let grid = document
          .querySelector(`[e-mappinguid="${filterObj.uid}"]`)
          .closest(".e-grid");
        let filterInput = grid.querySelector(`#${filterObj.field}`);
        filterInput.click();
        filterInput.focus();
      }
    },
    onStartDownload() {
      if (this.DsThueBaoKS.length == 0) {
        this.$root.toastError('Không có dữ liệu để xuất Excel !')
        return
      }
    },
    onXuatFileClicked() {
        if (this.DsThueBaoKS.length <= 0) {
            this.$root.toastError("Không có dữ liệu để xuất!")
          return
        }
        this.DsThueBaoKS.forEach(e => e.LOAI_HD = e.LOAI_YC)
        this.$bvModal.show('modal-xuat-dulieu')
    },
    async gridThueBaoKS_rowClicked( dataItem) {
    //   console.log('gridThueBaoKS_rowClicked', dataItem)
        this.pCurrentKhaoSatSelected = dataItem
        this.p_HDKH_ID = 0
        this.p_BAOHONG_ID = 0
        if (dataItem) {
          this.p_TBTL_ID = dataItem.TBTL_ID;
          this.pIsAudio = dataItem.FILENAME;
          this.pTxtMaTB = dataItem.MA_TB;
          this.pTxtNgayKS = dataItem.NGAY_HOI;
          this.pTxtNgayYC = dataItem.NGAY_YC;
          this.pTxtNgayHT = dataItem.NGAY_HT;
          this.pTxtGhiChu = dataItem.GHICHU;
          this.p_HDKH_ID = dataItem.HDKH_ID || 0
          this.p_BAOHONG_ID = dataItem.BAOHONG_ID || 0
          await this.loadDsChiTiet();
      }
    },
    async getTongThueBaoKhaoSat() {
      try {
        let data = {
          tuNgay: moment(this.pTxtThoiGianKSTu).format("DD/MM/YYYY"),
          denNgay: moment(this.pTxtThoiGianKSDen).format("DD/MM/YYYY"),
        }
        this.txtTongSoThueBao = 'Số lượng thuê bao không hài lòng / Tổng thuê bao khảo sát: '
        let response = await this.$root.context.post("/web-cskh/api/common/tong-tb-ks?pageNo=1&pageSize=10000", data)
        if (response.error_code === "BSS-00000000") {
          let ds = response.data.data || []
          if (ds.length > 0) {
            this.txtTongSoThueBao = 'Số lượng thuê bao không hài lòng / Tổng thuê bao khảo sát: '
          }
          ds.forEach(e => {
            if (e.TEN_TRUNG_TAM == "TONG") {
              this.txtTongSoThueBao += "Tổng: " + (e.KHL == null ? 0 : e.KHL) + '/' + e.TONGSO + '; '
            }
            else {
              this.txtTongSoThueBao += e.TEN_TRUNG_TAM + ' ' + (e.KHL == null ? 0 : e.KHL) + '/' + e.TONGSO + '; '
            }

          })
        }
      }
      catch (e) {
        console.log(e)
      }
    },
    onExportExcel() {
      if (this.DsThueBaoKS.length == 0) {
        this.$root.toastError('Không có dữ liệu để xuất Excel !')
        return
      }

      let columns = [
        { index: 1, width: 130 },
        { index: 2, width: 130 },
        { index: 3, width: 130 },
        { index: 4, width: 130 },
        { index: 5, width: 130 },
        { index: 6, width: 130 },
        { index: 7, width: 130 },
        { index: 8, width: 130 },
        { index: 9, width: 130 },
        { index: 10, width: 130 },
        { index: 11, width: 130 },
        { index: 12, width: 130 },
        { index: 13, width: 130 },
        { index: 14, width: 130 },
        { index: 15, width: 130 },
        { index: 16, width: 130 },
        { index: 17, width: 130 },
        { index: 18, width: 130 },
        { index: 19, width: 130 },
        { index: 20, width: 130 },
        { index: 21, width: 130 },
        { index: 22, width: 130 },
        { index: 23, width: 130 },
        { index: 24, width: 130 },
        { index: 25, width: 130 },
      ];

      let borders = {
        color: "#00000000",
        lineStyle: "thin",
      }

      let headerRow_line1 = {
        index: 1,
        cells: [
          {
            index: 1,
            value: "Thông tin các lần khảo sát",
            colSpan: 18,
            style: { borders: borders, background: '#f0f0f0' },
          },
          {
            index: 19,
            value: "Nhân viên",
            colSpan: 3,
            style: { borders: borders,  color: '#f0f0f0'},
          },
          {
            index: 22,
            value: "Dịch vụ",
            colSpan: 2,
            style: { borders: borders,  color: '#f0f0f0'},
          },
          {
            index: 24,
            value: "Tổng hợp",
            colSpan: 2,
            style: { borders: borders,  color: '#f0f0f0'},
          }
        ]
      }

      let headerRow_line2 = {
        index: 2,
        cells: [
          {
            index: 1,
            value: "Số máy/ACC",
            colSpan: 1,
            style: { borders: borders, },
          },
          {
            index: 2,
            value: "Số ảo",
            colSpan: 1,
            style: { borders: borders, },
          },
          {
            index: 3,
            value: "Loại hình",
            colSpan: 1,
            style: { borders: borders, },
          },
          {
            index: 4,
            value: "Tốc độ",
            colSpan: 1,
            style: { borders: borders, },
          },
          {
            index: 5,
            value: "Loại YC",
            colSpan: 1,
            style: { borders: borders, },
          },
          {
            index: 6,
            value: "Người trả lời",
            colSpan: 1,
            style: { borders: borders, },
          },{
            index: 7,
            value: "Số LH",
            colSpan: 1,
            style: { borders: borders, },
          },
          {
            index: 8,
            value: "Ngày YC",
            colSpan: 1,
            style: { borders: borders, },
          },
          {
            index: 9,
            value: "Ngày HT",
            colSpan: 1,
            style: { borders: borders, },
          },
          {
            index: 10,
            value: "Lần KS",
            colSpan: 1,
            style: { borders: borders, },
          },
          {
            index: 11,
            value: "Ngày KS",
            colSpan: 1,
            style: { borders: borders, },
          },
          {
            index: 12,
            value: "Người KS",
            colSpan: 1,
            style: { borders: borders, },
          },{
            index: 13,
            value: "Trạng thái",
            colSpan: 1,
            style: { borders: borders, },
          },
          {
            index: 14,
            value: "Loại KH",
            colSpan: 1,
            style: { borders: borders, },
          },
          {
            index: 15,
            value: "Tên thuê bao",
            colSpan: 1,
            style: { borders: borders, },
          },
          {
            index: 16,
            value: "Địa chỉ lắp đặt",
            colSpan: 1,
            style: { borders: borders, },
          },
          {
            index: 17,
            value: "Ghi chú",
            colSpan: 1,
            style: { borders: borders, },
          },
          {
            index: 18,
            value: "Audio",
            colSpan: 1,
            style: { borders: borders, },
          },{
            index: 19,
            value: "Chất lượng",
            colSpan: 1,
            style: { borders: borders, },
          },
          {
            index: 20,
            value: "KHL NVKT",
            colSpan: 1,
            style: { borders: borders, },
          },
          {
            index: 21,
            value: "KHL NVKD",
            colSpan: 1,
            style: { borders: borders, },
          },
          {
            index: 22,
            value: "Chất lượng",
            colSpan: 1,
            style: { borders: borders, },
          },
          {
            index: 23,
            value: "Trả lời phụ",
            colSpan: 1,
            style: { borders: borders, },
          },
          {
            index: 24,
            value: "Độ hài lòng",
            colSpan: 1,
            style: { borders: borders, },
          },
          {
            index: 25,
            value: "Lý do KTL",
            colSpan: 1,
            style: { borders: borders, },
          },
        ]
      }

      let rows = [];
      rows.push(headerRow_line1)
      rows.push(headerRow_line2)

      let index = 3;
      this.DsThueBaoKS.forEach((record) => {
        let row = {
          index: index++,
          cells: [
            {index: 1, value: record.MA_TB, style: { bold: false, borders: { color: "#00000000", lineStyle: "thin", }, }},
            {index: 2, value: record.MA_LT, style: { bold: false, borders: { color: "#00000000", lineStyle: "thin", }, }},
            {index: 3, value: record.LOAIHINH_TB, style: { bold: false, borders: { color: "#00000000", lineStyle: "thin", }, }},
            {index: 4, value: record.TOC_DO, style: { bold: false, borders: { color: "#00000000", lineStyle: "thin", }, }},
            {index: 5, value: record.LOAI_YC, style: { bold: false, borders: { color: "#00000000", lineStyle: "thin", }, }},
            {index: 6, value: record.NGUOI_TL, style: { bold: false, borders: { color: "#00000000", lineStyle: "thin", }, }},
            {index: 7, value: record.DIENTHOAI_LH, style: { bold: false, borders: { color: "#00000000", lineStyle: "thin", }, }},
            {index: 8, value: record.NGAY_YC, style: { bold: false, borders: { color: "#00000000", lineStyle: "thin", }, }},
            {index: 9, value: record.NGAY_HT, style: { bold: false, borders: { color: "#00000000", lineStyle: "thin", }, }},
            {index: 10, value: record.LAN_KS, style: { bold: false, borders: { color: "#00000000", lineStyle: "thin", }, }},
            {index: 11, value: record.NGAY_HOI, style: { bold: false, borders: { color: "#00000000", lineStyle: "thin", }, }},
            {index: 12, value: record.NGUOI_KS, style: { bold: false, borders: { color: "#00000000", lineStyle: "thin", }, }},
            {index: 13, value: record.TRANGTHAI_KS, style: { bold: false, borders: { color: "#00000000", lineStyle: "thin", }, }},
            {index: 14, value: record.LOAIKH, style: { bold: false, borders: { color: "#00000000", lineStyle: "thin", }, }},
            {index: 15, value: record.TEN_TB, style: { bold: false, borders: { color: "#00000000", lineStyle: "thin", }, }},
            {index: 16, value: record.DIACHI_LD, style: { bold: false, borders: { color: "#00000000", lineStyle: "thin", }, }},
            {index: 17, value: record.GHICHU, style: { bold: false, borders: { color: "#00000000", lineStyle: "thin", }, }},
            {index: 18, value: record.FILENAME, style: { bold: false, borders: { color: "#00000000", lineStyle: "thin", }, }},
            {index: 19, value: record.CHATLUONG_NV, style: { bold: false, borders: { color: "#00000000", lineStyle: "thin", }, }},
            {index: 20, value: record.KHL_NVKT_NV, style: { bold: false, borders: { color: "#00000000", lineStyle: "thin", }, }},
            {index: 21, value: record.KHL_NVKD_NV, style: { bold: false, borders: { color: "#00000000", lineStyle: "thin", }, }},
            {index: 22, value: record.CHATLUONG_DV, style: { bold: false, borders: { color: "#00000000", lineStyle: "thin", }, }},
            {index: 23, value: record.TRALOI_PHU, style: { bold: false, borders: { color: "#00000000", lineStyle: "thin", }, }},
            {index: 24, value: record.DOHAILONG, style: { bold: false, borders: { color: "#00000000", lineStyle: "thin", }, }},
            {index: 25, value: record.LYDO_KTL, style: { bold: false, borders: { color: "#00000000", lineStyle: "thin", }, }},
          ],
        }
        rows.push(row)
      })

      let worksheets = [{ columns, rows }];
      worksheets[0].name = "export"
      let workbook = new Workbook({ worksheets }, "xlsx");
      workbook.save("Export.xlsx");
    },
    async onMaTBKeyPress(event) {
      if (event.keyCode == 13 && this.pTxtMaTB) {
        await this.onTimKiemClicked(true)
      }
    },
    onCayDonViDataBound() {
      let treeDonVi = document.getElementById("treeObj").ej2_instances[0];
      if (this.treeDonViFields.dataSource != null && this.treeDonViFields.dataSource.length > 0) {
        treeDonVi.selectedNodes = ['' + this.treeDonViFields.dataSource[this.treeDonViFields.dataSource.length - 1].DONVI_ID]
      }
    },
    onShowSearchAccount() {
      this.popupComponent = SearchAccount;
      this.Popup("popupComponents");
    },
    Popup(modalId) {
      this.$bvModal.show(modalId);
    },
    ClosePopup(modalId) {
      this.$bvModal.hide(modalId);
    },
    searchAccountClosed(val) {
      if(val){
        this.pTxtMaTB = val.ma_tb || "";
      }
      this.ClosePopup("popupComponents");
    },
  }
};
</script>
