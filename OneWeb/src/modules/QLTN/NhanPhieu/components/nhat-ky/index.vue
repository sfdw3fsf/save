<template src="./index.html"></template>
<style scoped>
.popup-box .popup-header {
  padding: 10px 15px;
  font-size: 20px;
  background-color: #fff;
  border-bottom: 1px solid #e0e0e0;
}
</style>
<script>
import moment from "moment";
import CommonsAPI from "@/modules/QLTN/api/Commons";
import UploadEProfileAPI from "@/modules/contract/profile/UploadEProfile/UploadEProfileAPI.js";
import XLSX from 'xlsx';
import ModalExport from '../modal-export'
import { createEditElement, initForeignKeyColumn } from "@syncfusion/ej2-vue-grids";
const msg = {
  success: {
    0: "Thêm mới dữ liệu thành công",
    2: "Cập nhật dữ liệu thành công",
    "-1": "Xóa dữ liệu thành công"
  },
  error: {
    0: "Thêm mới dữ liệu thất bại",
    2: "Có lỗi khi Sửa dữ liệu",
    "-1": "Có xóa khi Sửa dữ liệu"
  },
  confirm: {
    0: "Bạn có muốn thêm mới dữ liệu nhật ký không",
    2: "Bạn có muốn sửa dữ liệu nhật ký không",
    "-1": "Bạn có muốn xóa dữ liệu nhật ký không"
  }
};

export default {
  name: "NhatKy",
  components: { ModalExport, },
  props: ["dsDichVu", "kyCuoc", "dsLyDoTra", "rowData", "passParam", "dsKiemTra"],
  data: function () {
    return {
      kyHD: [],
      chuKy: [],
      kyHDId: "",
      chuKyId: "",
      lyDo: [],
      hinhThuc: [],
      formData: {},
      dsNK: [],
      fileExcel: [],
      dataExport: [],
      columnsExport: [
        { fieldName: 'LANTHU', headerText: 'Lần đến thứ', allowFiltering: false, allowSorting: false },
        { fieldName: 'NGAYDEN_CONVERT', headerText: 'Ngày đôn đốc', allowFiltering: true, allowSorting: false },
        { fieldName: 'LYDO', headerText: 'Lý do', allowFiltering: true, allowSorting: false },
        { fieldName: 'HINHTHUC', headerText: 'Hình thức', allowFiltering: true, allowSorting: false },
        { fieldName: 'NOIDUNG', headerText: 'Nội dung', allowFiltering: true, allowSorting: false },
        { fieldName: 'KETQUA', headerText: 'Kết quả', allowFiltering: true, allowSorting: false },
        { fieldName: 'DEXUAT', headerText: 'Đề xuất', allowFiltering: true, allowSorting: false },
      ],
      stt: 0,
      dsNKFile: [{ "ID_FILE": 1, "TEN_FILE": "Hợp đồng", "FILE_PATH": "" }, { "ID_FILE": 2, "TEN_FILE": "Hóa đơn", "FILE_PATH": "" }, { "ID_FILE": 3, "TEN_FILE": "Thông báo nợ", "FILE_PATH": "" }],
      file: null,
      formUpdate: {
        ghiChu: "",
        noiDung: "",
        deXuat: "",
        ketQua: "",
        lyDoId: "",
        hinhThucId: "",
        ngayDen: new Date(),
        lanThu: 0 //0: addnew, 2 update , -1 delete
      },
      selectedRow: {}
    };
  },
  validations: {},
  watch: {},
  created: async function () {
    this.getDsKyHD();
    this.getDsHinhThuc();
    this.makeLyDo();
    this.getFormInfo({
      pMaTK: this.passParam.maTT,
      pKyHoaDon: this.passParam.kyHoaDon,
      pTrangThaiHDId: this.passParam.trangThaiHDId
    });
    this.getGridData({
      pMaTT: this.passParam.maTT,
      pKyHoaDon: this.passParam.kyHoaDon,
      pTrangThaiHDId: this.passParam.trangThaiHDId
    });
    //console.log('danh sach :', this.dsKiemTra)
  },
  async mounted() { },
  methods: {

    onChangeFileUpload(e) {
      //console.log('tets ', e);
      // const files = e.target.files;

      this.client_url = e.target.value.toString();
      let formData = new FormData();
      for (var file of e.target.files) {
        formData.append("files", file);
      }
      this.loading(true);
      UploadEProfileAPI.upload_file_hoso(this.axios, formData, {
        headers: { "Content-Type": "multipart/form-data" },
      })
        .then((response) => {
          this.loading(false);
          if (
            response.data.error_code &&
            response.data.error_code == "BSS-00000000"
          ) {
            this.server_url = response.data.data.join("|");
            var tmp = this.server_url.split("/");
            this.file = tmp.length > 0 ? tmp[tmp.length - 1] : "";

            this.reseUploadGridFile(this.stt, this.server_url);
          }
        })
        .catch(function (e) {
          console.log("Upload file  không thành công." + e);
        });
    },

    reseUploadGridFile(row, giatri) {
      let postData = [];
      let itemtData = {
        ID_FILE: 0,
        TEN_FILE: "",
        FILE_PATH: "",
        NGUOI_CN: "",
        NGAY_CN: ""
      }

      this.dsNKFile.forEach(item => {
        if (item.ID_FILE == row) {
          itemtData.ID_FILE = row;
          itemtData.TEN_FILE = item.TEN_FILE;
          itemtData.FILE_PATH = giatri;
          itemtData.NGUOI_CN = this.$auth.getUserName();
          itemtData.NGAY_CN = new Date();
          postData.push(itemtData);
        }
        else
          postData.push(item)
      });
      this.dsNKFile = [...postData];
      console.log(this.dsNKFile);
    },

    onClickButtonUpload(e) {

      document.getElementById("upload").click();
      this.$refs.fileInput.value = "";
    },
    onClickDownload() {

      this.dataExport = []
      this.dataExport = [...this.dsNK]
      if (this.dataExport !== undefined && this.dataExport !== null && this.dataExport.length > 0) {
        this.$bvModal.show('modal-export-nhatky')
      } else {
        this.$toast.error('Không có dữ liệu để xuất excel')
      }
    },
    async changPathFile(e) {
      console.log(this.$refs.docfile.value)
      await this.onDocFileExcel(e);

    },
    async onDocFileExcel(e) {
      let files = e.target.files
      if (!files.length) {
        return
      } else if (!/\.(xls|xlsx)$/.test(files[0].name.toLowerCase())) {
        document.getElementById('docfile').value = null
        return this.$toast.error('File excel không đúng định dạng!')
      }
      let f = files[0]

      var reader = new FileReader()
      const vm = this      
      reader.onload = function (e) {
        var data = new Uint8Array(e.target.result)
        var workbook = XLSX.read(data, { type: 'array' })
        let sheetName = workbook.SheetNames[0]
        /* DO SOMETHING WITH workbook HERE */
        // console.log(workbook);
        let worksheet = workbook.Sheets[sheetName]
        let exceldata = XLSX.utils.sheet_to_json(worksheet)

        if (exceldata.length <= 0) {
          vm.$toast.error('Không tìm thấy dữ liệu trong file đã upload.')
          document.getElementById('docfile').value = null
          return false
        }
        let exceldata1 = XLSX.utils.sheet_to_json(worksheet, { header: 1 })
        let columns = []
        exceldata1[0].forEach(item => {
          columns.push(item.toUpperCase())
        })

        if (!columns.includes('MA_TT')
          || !columns.includes('NGAY')
          || !columns.includes('HTDD_ID')
          || !columns.includes('LYDO_ID')
          || !columns.includes('NOI_DUNG')
          || !columns.includes('KET_QUA')
          || !columns.includes('DE_XUAT')
          || !columns.includes('GHI_CHU')
        ) {
          vm.$toast.error('file gồm MA_TT,NGAY,HTDD_ID,LYDO_ID,NOI_DUNG,KET_QUA,DE_XUAT,GHI_CHU')
          document.getElementById('docfile').value = null
          return false
        }
        //vm.fileExcel = exceldata;
        let soluong = 0;
        let dsKhongCo=[]
        if (exceldata.length > 0)
          exceldata.forEach((item) => {
            if (!vm.isExistMaTT(item.MA_TT)) {
              soluong = soluong + 1
              dsKhongCo.push(item.MA_TT)
            };
          });
        if (soluong != 0)

          vm.$toast.error("Có " + soluong + " mã thanh toán không thuộc danh sách \n "+dsKhongCo.join('\n'));
        else 
        {

           vm.capNhatNhatKyTheoFile(exceldata);
        }


      } // end of onload file
      reader.readAsArrayBuffer(f)
      document.getElementById('docfile').value = null

    },

    isExistMaTT(maTT) {
      return this.dsKiemTra.some(function (el) {
        return el.MA_TT === maTT
      })
    },

    kiemTraMaTTFile(dsKT) {
      let soluong = 0;
      this.dsKT.forEach(item => {
        if (!this.isExistMaTT(item.MA_TT)) { soluong = soluong + 1 }
      });
      if (soluong != 0)
        this.$toast.error("Có " + soluong + " mã thanh toán không thuộc danh sách");

    },
    onClickUpload(e) {

      document.getElementById("docfile").click();
      this.$refs.fileInput.value = "";
    },
    onClickButtonDownload(e) {
      let url_view = this.dsNKFile.filter(x => x.ID_FILE == this.stt)[0].FILE_PATH;


      if (url_view != "") {
        this.loading(true);
        let input = {
          list_file: url_view

        }
        UploadEProfileAPI.link_view_file(this.axios, input)
          .then((response) => {
            this.loading(false);
            if (
              response.data.error_code &&
              response.data.error_code == "BSS-00000000"
            ) {
              window.open(response.data.data[0].file_url);
            }
          })
          .catch(function (e) {
            console.log("Upload file  không thành công." + e);
          });
      }
    }
    ,
    getColumnTemplateThaoTac(parent) {
      return function () {
        return {
          template: {
            template: `     <div class="info-row">
                            <button class="btn btn-sm btn-primary lh14 pad3" @click="parent.onClickButtonUpload(data.NAME)" title="Upload file">
                                <span class="-ap icon-upload"></span>
                            </button>
                             <span>&emsp;</span> 
                            <button class="btn btn-sm btn-primary lh14 pad3" @click="parent.onClickButtonDownload(data.NAME)" title="Download file">
                                <span class="-ap icon-download"></span>
                            </button>
                            </div>                            
                        `,
            data() {
              return {
                parent: parent,
                data: {}
              };
            }
          }
        };
      };
    },
    onClose() {
      this.$emit("close");
    },
    onClickAddNewRow() {
      console.log('khanh');
      //this.dsNKFile.push({ TEN_FILE: "" });
    },
    onClickAddNew() {
      this.formUpdate.lanThu = 0;
      if (this.validateData()) {

        this.$bvModal
          .msgBoxConfirm(msg.confirm[this.formUpdate.lanThu], {
            title: "Thông báo",
            size: "sm",
            buttonSize: "md",
            okVariant: "primary",
            headerClass: "p-2 pb-0 border-bottom-0",
            footerClass: "p-2 pt-0 border-top-0 justify-content-center",
            centered: true,
            noCloseOnBackdrop: true,
            okTitle: "Đồng ý",
            cancelTitle: "Không đồng ý"
          })
          .then(value => {
            value && this.excuteAddNew();
          });
      }
    },
    onClickUpdate() {
      this.formUpdate.lanThu = 2;
      if (this.validateData()) {

        if (this.selectedRow === null) {
          this.$toast.error('Chưa chọn trong danh sách.')
          return
        }
        this.$bvModal
          .msgBoxConfirm(msg.confirm[this.formUpdate.lanThu], {
            title: "Thông báo",
            size: "sm",
            buttonSize: "md",
            okVariant: "primary",
            headerClass: "p-2 pb-0 border-bottom-0",
            footerClass: "p-2 pt-0 border-top-0 justify-content-center",
            centered: true,
            noCloseOnBackdrop: true,
            okTitle: "Đồng ý",
            cancelTitle: "Không đồng ý"
          })
          .then(value => {
            value && this.excuteUpdate();
          });
      }
    },
    onClickDelete() {
      this.formUpdate.lanThu = -1;

      if (this.selectedRow === null) {
        this.$toast.error('Chưa chọn trong danh sách.')
        return
      }

      if (this.validateData()) {
        this.$bvModal
          .msgBoxConfirm(msg.confirm[this.formUpdate.lanThu], {
            title: "Thông báo",
            size: "sm",
            buttonSize: "md",
            okVariant: "primary",
            headerClass: "p-2 pb-0 border-bottom-0",
            footerClass: "p-2 pt-0 border-top-0 justify-content-center",
            centered: true,
            noCloseOnBackdrop: true,
            okTitle: "Đồng ý",
            cancelTitle: "Không đồng ý"
          })
          .then(value => {
            value && this.excuteUpdate();
          });
      }
    },
    validateData() {
      if (this.formData.maTT === "") {
        this.$toast.error("Hãy tìm kiếm thông tin trước khi cập nhật!");
        return false;
      }
      if (!this.formUpdate.lyDoId) {
        this.$toast.error("Bạn chưa chọn lý do trả!");
        return false;
      }
      if (this.formUpdate.ngayDen > new Date()) {
        this.$toast.error("Ngày cập nhật không được lớn hơn ngày hiện tại");
        return false;
      }
      if (this.formUpdate.ghiChu === "") {
        this.$toast.error("Ghi chú không được để trống");
        return false;
      }



      return true;
    },
    capNhatNhatKyTheoFile: async function (file) {
      this.loading(true);    
      let body = {
        pKyHoaDon: `${this.kyHDId}${this.chuKyId}`,//this.passParam.kyHoaDon,
        dsFile: file
      }
      try {
        const { data } = await this.axios.post(
          "/web-thuno/api/thu-no/nhan-phieu/cap-nhat-nhat-ky/cap-nhat-ky-thu-no-theo-file",
          body
        );
        if (data && data.error == "200") {
          this.$toast.success("Cập nhật dữ liệu theo file thành công");
        }
        this.loading(false);
      } catch (error) {
        this.loading(false);
        if (error.data && error.data.message_detail) {
          this.$toast.error(error.data.message_detail);
        }
      }
    },
    excuteAddNew() {
      const body = {
        pNhatKyTNId: 0,
        pThanhToanId: this.formData.THANHTOAN_ID,
        pTrangThaiHDId: this.passParam.trangThaiHDId,
        pNgayDen: moment(this.formUpdate.ngayDen).format("DD/MM/YYYY"), //"01/03/2021",
        pLanThu: this.formUpdate.lanThu,
        pMayCN: "maythuc",
        pIPCN: "maythuc",
        pLyDoTraId: this.formUpdate.lyDoId,
        pKyHoaDon: `${this.kyHDId}${this.chuKyId}`,//this.passParam.kyHoaDon,
        pGhiChu: this.formUpdate.ghiChu,
        pNoiDung: this.formUpdate.noiDung,
        pKetQua: this.formUpdate.ketQua,
        pDeXuat: this.formUpdate.deXuat,
        pHTKNId: this.formUpdate.hinhThucId,
        dsFile: this.dsNKFile
      };
      this.updateData(body);
    },
    excuteUpdate() {
      const body = {
        pNhatKyTNId: this.selectedRow.NHATKYTN_ID,
        pThanhToanId: this.formData.THANHTOAN_ID,
        pTrangThaiHDId: this.passParam.trangThaiHDId,
        pNgayDen: moment(this.formUpdate.ngayDen).format("DD/MM/YYYY"), //"01/03/2021",
        pLanThu: this.formUpdate.lanThu,
        pMayCN: "OneBSS",
        pIPCN: "OneBSS",
        pLyDoTraId: this.formUpdate.lyDoId,
        pKyHoaDon: `${this.kyHDId}${this.chuKyId}`,//this.passParam.kyHoaDon,
        pGhiChu: this.formUpdate.ghiChu,
        pNoiDung: this.formUpdate.noiDung,
        pKetQua: this.formUpdate.ketQua,
        pDeXuat: this.formUpdate.deXuat,
        pHTKNId: this.formUpdate.hinhThucId,
        dsFile: this.dsNKFile
      };
      this.updateData(body);
    },
    onSelectRowGridFile(value) {
      if (value != null)
        this.stt = value.ID_FILE;
    },
    onSelectRowGrid(value) {
      if (value) {
        this.formUpdate.ngayDen = moment(
          value.NGAY_DEN,
          "DD/MM/YYYY"
          // "YYYY/MM/DD HH:mm:ss"
        ).toDate();
        this.formUpdate.lyDoId = value.LYDOTRA_ID;
        this.formUpdate.ghiChu = value.GHICHU;

      }
      this.selectedRow = value;
      this.getDSFileNhatKy(value.NHATKYTN_ID);

    },
    makeLyDo() {
      const lyDoCopy = JSON.parse(JSON.stringify(this.dsLyDoTra));
      this.lyDo = lyDoCopy.map(item => ({
        LYDOTRA_ID: item.LYDOTRA_ID,
        NOIDUNG: item.NOIDUNG
      }));
      //this.formUpdate.lyDoId = this.lyDo[0].LYDOTRA_ID;
    },
    getDsKyHD: async function () {
      try {
        const { data } = await this.axios.get(
          "/web-thuno/api/thu-no/common/ky-hoa-don"
        );
        if (data && data.error == "200") {
          if (data.data.length > 0) {
            // this.kyHDId = data.data[0].ID.toString().slice(0, -2);
            // this.getChuKyNoByKyHD(this.kyHDId);
            this.kyHD = data.data.map(item => ({
              ID: item.ID.toString().slice(0, -2),
              CHUKYNO: item.CHUKYNO.split(" ")[2].slice(0, -1)
            }));
            this.kyHDId = this.passParam.kyHoaDon.toString().slice(0, -2);
            this.getChuKyNoByKyHD(this.kyHDId);
          }
        }
      } catch (error) { }
    },
    getDsHinhThuc: async function () {
      try {
        const { data } = await this.axios.get(
          "/web-thuno/api/thu-no/quan-ly-dai-ly/danh-muc/HINHTHUCKN"
        );
        if (data && data.error == "200") {
          if (data.data.length > 0) {
            this.hinhThuc = data.data;
          }
        }
      } catch (error) { }
    },
    getChuKyNoByKyHD: async function () {
      CommonsAPI.getChuKyNoTheoKyCuoc(this.axios, this.kyHDId)
        .then(response => {
          const { data } = response;
          if (data && data.error == "200") {
            if (data.data.length > 0) {
              console.log(data)
              this.chuKyId = data.data[0].CHUKY;
              this.chuKy = data.data;
              this.getGridData({
                pMaTT: this.passParam.maTT,
                pKyHoaDon: `${this.kyHDId}${this.chuKyId}`,
                pTrangThaiHDId: this.passParam.trangThaiHDId
              });

              console.log(this.kyHDId)
            }
          }
        }).catch(function (err) { });
    },
    getFormInfo: async function (param) {
      try {
        const rs = await this.$root.context.get(
          "/web-thuno/api/thu-no/nhan-phieu/cap-nhat-nhat-ky/lay-thanh-toan-theo-dieu-kien",
          param
        );
        if (rs && rs.error == "200" && rs.data.length > 0) {
          this.formData = rs.data[0];
          this.formData = {
            ...rs.data[0],
            TIENNO: Number(rs.data[0].TIENNO).toLocaleString("en-En")
          };
        }
      } catch (error) { }
    },
    getGridData: async function (param) {
      console.log(param);
      try {
        const rs = await this.$root.context.get(
          "/web-thuno/api/thu-no/nhan-phieu/cap-nhat-nhat-ky/lay-nhat-ky-thu-no-theo-ma-thanh-toan-v2",
          param
        );
        // console.log(rs.data);
        if (rs && rs.error == "200") {
          this.dsNK = rs.data.map(item => ({
            ...item,
            NGAYDEN_CONVERT: item.NGAY_DEN//moment(item.NGAY_DEN).format('DD/MM/YYYY')
          }));
        }
      } catch (error) { }
    },
    getDSFileNhatKy: async function (param) {
      try {
        const rs = await this.$root.context.get(
          "/web-thuno/api/thu-no/nhan-phieu/cap-nhat-nhat-ky/lay-danh-sach-file-nhat-ky/" + param);
        // console.log(rs.data);
        if (rs && rs.error == "200") {
          this.dsNKFile = rs.data;
        }
      } catch (error) { }
    },
    updateData: async function (body) {
      console.log('updateData', body);
      try {
        const { data } = await this.axios.put(
          "/web-thuno/api/thu-no/nhan-phieu/cap-nhat-nhat-ky/thao-tac-nhat-ky-thu-no-theo-thanh-toan-v3",
          body
        );
        if (data && data.error == "200") {
          this.$toast.success(msg.success[this.formUpdate.lanThu]);
          this.getGridData({
            pMaTT: this.passParam.maTT,
            pKyHoaDon: `${this.kyHDId}${this.chuKyId}`,//this.passParam.kyHoaDon,
            pTrangThaiHDId: this.passParam.trangThaiHDId
          });
        }
      } catch (error) {
        if (error.data && error.data.message_detail) {
          this.$toast.error(error.data.message_detail);
        } else {
          this.$toast.error(msg.error[this.formUpdate.lanThu]);
        }
      }
    }
  }
};
</script>
