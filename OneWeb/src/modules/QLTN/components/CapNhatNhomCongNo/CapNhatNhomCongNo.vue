<style scoped src="./CapNhatNhomCongNo.scss"></style>
<template>
  <b-modal
    id="popup-capnhatnhomcongno"
    size="xl"
    no-close-on-backdrop
    hide-footer
    hide-header
    hide-header-close
    body-class="modal-body p-0"
    @shown="onModalShown"
    @hidden="close"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
          <div class="title"><span class="icon one-notepad"></span>Nhóm công nợ</div>
          <div
            class="close -ap icon-close"
            data-dismiss="modal"
            @click="close"
          ></div>
      </div>
      <div class="list-actions-top">
        <ul class="list">
            <li>
                <a href="#" v-on:click="nhapMoi()" :class="{'disable-btn': buttonState.isDisablebtnNhapMoi}">
                    <span class="icon one-file-plus"></span> Nhập mới
                </a>
            </li>
            <li>
                <a href="#" v-on:click="ghiLai()" :class="{'disable-btn': buttonState.isDisablebtnCapNhap}">
                    <span class="icon one-save"></span> Ghi lại
                </a>
            </li>
            <li>
                <a href="#" v-on:click="huy()" type="button" :disabled="buttonState.isDisablebtnHuy" :class="{'disable-btn': buttonState.isDisablebtnHuy}">
                    <span class="icon ui-1_circle-remove nc-icon-glyph"></span> Hủy
                </a>
            </li>
            <li>
                <a href="#" v-on:click="showModalXoa()" :disabled="buttonState.isDisablebtnXoa" :class="{'disable-btn': buttonState.isDisablebtnXoa}">
                    <span class="icon one-trash"></span> Xóa
                </a>
            </li>
            <li>
                <a href="#" v-on:keyup.enter="importFile">
                    <label for="uploadFile">
                      <span class="icon one-upload"></span>Đọc file
                      <input ref="fileInput" type="file" id="uploadFile" style="display: none" v-on:change="importExcel"
                        accept=".xls, .xlsx" v-on:click="$refs.fileInput.value=null" />
                    </label>
                </a>
            </li>
            <li>
                <a href="#" v-on:click="$bvModal.show('modal-xuat-dulieu')">
                  <span class="icon one-download"></span> Xuất file
                </a>
            </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
            <div class="legend-title">Thông tin nhóm công nợ</div>
            <div class="info-row">
                <div class="key">Nhóm công nợ<BssRequiredMarker/></div>
                <div class="value">
                    <input type="text" class="form-control highlight" v-model="dataSelect.tenNhom" ref="tenNhom" :class="{ 'is-invalid': $v.dataSelect.tenNhom.$error}">
                </div>
            </div>
            <div class="legend-title mart20">
                <div class="title">Danh sách nhóm công nợ</div>
            </div>
            <table-c 
              :columns="fields" 
              :dataSource="dsNhomCongNo"
              :showColumnCheckbox="false" 
              :showFilter="true"
              @rowClicked="rowClicked"
              ref="gridNhomCongNo"></table-c>
        </div>
    </div>
    </div>
    <b-modal v-model="isShowModal" cancelTitle="Hủy" okTitle="Đồng ý" title="Thông báo" size="md" @ok="docFileExcel()">
      <span>Bạn thật sự muốn thêm dữ liệu từ file Excel?</span>
    </b-modal>
    <b-modal v-model="isShowModalXoa" cancelTitle="Hủy" okTitle="Đồng ý" title="Thông báo" size="md" @ok="xoa()">
      <span>Bạn thật sự muốn xoá dữ liệu?</span>
    </b-modal>
    <ModalXuatDuLieu
        id="modal-xuat-dulieu"
        :thuocTinh="dsThuocTinh"
        :data="dsNhomCongNo">
    </ModalXuatDuLieu>
  </b-modal>
</template>
<script>
import { mapActions, mapState, mapGetters } from "vuex";
import API from "../../api/CapNhapNhomCongNo.js"
import moment from "moment"
import xlsx from "xlsx";
import downloadexcel from "vue-json-excel";
import BssRequiredMarker from '@/components/BssRequiredMarker';
import { required, maxLength } from 'vuelidate/lib/validators'
import ModalXuatDuLieu from '../../../ECMS/TraCuuSplitterTheoPortOLT/ModalXuatDuLieu'
export default {
  name: 'CapNhatNhomCongNo',
  components: {
    downloadexcel,
    BssRequiredMarker,
    ModalXuatDuLieu
  },
  computed: {
    ...mapState("capnhapnhomcongno", [
      "dsNhomCongNo",
    ]),
  },
  async mounted () {
    await this.fetchData()
    this.dataSelect.nhomCNoId = this.dsNhomCongNo[0].NHOMCNO_ID
    this.dataSelect.tenNhom = this.dsNhomCongNo[0].TEN_NHOM
    // console.log("nhóm công nợ: ", this.dsNhomCongNo)
  },
  validations: {
    dataSelect: {
      tenNhom: {
        required,
        maxLength: maxLength(100)
      }
    }
  },
  data () {
    return {
      fields: [
        {
          fieldName: "STT",
          headerText: "STT",
          allowFiltering: true,
          cellCssClass: 'text-right'
        },
        {
          fieldName: "NHOMCNO_ID",
          headerText: "ID",
          allowFiltering: true,
          cellCssClass: 'text-right'
        },
        {
          fieldName: "TEN_NHOM",
          headerText: "Nhóm công nợ",
          allowFiltering: true,
        },
        {
          fieldName: "NGUOI_CN",
          headerText: "Người CN",
          allowFiltering: true,
        },
        {
          fieldName: "NGAY_CN",
          headerText: "Ngày CN",
          allowFiltering: true,
          cellCssClass: 'text-right'
        },
        {
          fieldName: "MAY_CN",
          headerText: "Máy CN",
          allowFiltering: true,
        },
      ],
      // exportExcelFields: {
      //   "STT": "STT",
      //   "ID": "NHOMCNO_ID",
      //   "Nhóm công nợ": "TEN_NHOM",
      //   "Người CN": "NGUOI_CN",
      //   "Ngày CN": "NGAY_CN",
      //   "Máy CN": "MAY_CN"
      // },
      dsThuocTinh: ["STT", "NHOMCNO_ID", "TEN_NHOM", "NGUOI_CN", "NGAY_CN", "MAY_CN"],
      dataSelect: {
        nhomCNoId: "",
        tenNhom: ""
      },
      buttonState: {
        isDisablebtnNhapMoi: false,
        isDisablebtnCapNhap: false,
        isDisablebtnHuy: false,
        isDisablebtnXoa: false
      },
      checkCapNhap: true,
      isShowModal: false,
      isShowModalXoa: false,
      dataImport: [],
    }
  },
  methods: {
    ...mapActions("capnhapnhomcongno", [
      "getDanhSachNhomCongNo",
      "themMoiNhomCongNo",
      // "capNhapNhomCongNo",
      "xoaNhomCongNo",
      "docFile",
    ]),
    async fetchData() {
      await this.getDanhSachNhomCongNo()
      // this.dsNhomCongNo.forEach(x => {
      //   x.NGAY_CN = moment(x.NGAY_CN).format("DD/MM/YYYY")
      // })
    },
    rowClicked(rowIndex) {
      this.checkCapNhap = true
      let item = this.$refs.gridNhomCongNo.dataItems[rowIndex]
      this.dataSelect = {
        nhomCNoId: item.NHOMCNO_ID,
        tenNhom: item.TEN_NHOM
      }
      this.setButtonState(false, false, false, false)
    },
    setButtonState (nhapMoi, capNhap, huy, xoa) {
      this.buttonState.isDisablebtnNhapMoi = nhapMoi
      this.buttonState.isDisablebtnCapNhap = capNhap
      this.buttonState.isDisablebtnHuy = huy
      this.buttonState.isDisablebtnXoa = xoa
    },
    nhapMoi() {
      this.checkCapNhap = false
      this.dataSelect = {
        nhomCNoId: "",
        tenNhom: ""
      }
      this.setButtonState(true, false, false, true)
    },
    async ghiLai() {
      this.$root.loading(true)
      console.log("cập nhập: ", this.checkCapNhap)
      this.dataSelect.tenNhom = this.dataSelect.tenNhom.trim()
      if (this.validateDate(this.dataSelect)) {
        this.$root.loading(false)
        return
      }
      if (this.checkCapNhap) {
        // await this.capNhapNhomCongNo(this.dataSelect)
        API.capNhapNhomCongNo(this.axios, this.dataSelect).then(async (response) => {
          await this.getDanhSachNhomCongNo()
          this.$toast.success("Cập nhập nhóm công nợ thành công!")
          this.dsNhomCongNo.forEach(element => {
            if (element.TEN_NHOM === this.dataSelect.tenNhom) {
              this.$refs.gridNhomCongNo.setCurrentSelectedRow(element.STT - 1)
            }
          })
        }).catch (error => {
          this.$toast.error(error.data.data)
        })
      } else {
        API.themMoiNhomCongNo(this.axios, this.dataSelect.tenNhom).then(async (response) => {
          await this.getDanhSachNhomCongNo()
          this.$toast.success("Tạo mới nhóm công nợ thành công!")
          this.dsNhomCongNo.forEach(element => {
            if (element.TEN_NHOM === this.dataSelect.tenNhom) {
              this.$refs.gridNhomCongNo.setCurrentSelectedRow(element.STT - 1)
              this.dataSelect.nhomCNoId = element.NHOMCNO_ID
            }
          })
        }).catch (error => {
          this.$toast.error(error.data.message)
        })
      }
      this.setButtonState(false, false, false, false)
      this.$root.loading(false)
    },
    huy() {
      this.dataSelect = {
        nhomCNoId: "",
        tenNhom: ""
      }
      this.setButtonState(false, true, true, false)
    },
    showModalXoa() {
      if (!this.dataSelect.nhomCNoId) {
        this.$toast.error("Vui lòng chọn nhóm công nợ muốn xóa.")
        this.$root.loading(false)
        return
      }
      this.isShowModalXoa = true
    },
    async xoa() {
      this.$root.loading(true)
      // if (!this.dataSelect.nhomCNoId) {
      //   this.$toast.error("Vui lòng chọn nhóm công nợ muốn xóa.")
      //   this.$root.loading(false)
      //   return
      // }
      await this.xoaNhomCongNo(this.dataSelect.nhomCNoId)
      this.dataSelect = {
        nhomCNoId: "",
        tenNhom: ""
      }
      this.$root.loading(false)
    },
    importFile() {
      document.getElementById("uploadFile").click()
    },
    async importExcel(e) {
      const files = e.target.files
      if (!files.length) {
        return
      } else if (!/\.(xls|xlsx)$/.test(files[0].name.toLowerCase())) {
        return  this.$root.toastWarning("File excel không đúng định dạng!")
      }
      this.readFile(files)
    },
    readFile(file) {
      const fileReader = new FileReader()
      fileReader.onload = (ev) => {
        try {
          const data = ev.target.result
          const XLSX = xlsx
          const workbook = XLSX.read(data, {
              type: "binary",
          })
          const wsname = workbook.SheetNames[0]
          const ws = XLSX.utils.sheet_to_json(workbook.Sheets[wsname])
          const excellist = [];
          let ds = []

          for (var i = 0; i < ws.length; i++) {
            excellist.push(ws[i]);
            ds.push(ws[i].TEN_NHOM)
          }

          console.log(ds)

          if (excellist.length <= 0) {
            this.$root.toastError("File excel không đúng định dạng")
            return
          }

          if (!excellist[0].hasOwnProperty("TEN_NHOM")) {
            this.$root.toastError("File cần có chính xác 1 cột TEN_NHOM!");
            return
          }

          this.dataImport = ds
          this.isShowModal = true

          // if (ds.length > 0) {
          //   this.docFile(ds)
          // }

        } catch (error) {
          return alert("Read failure! " + error)
        }
      }
      fileReader.readAsBinaryString(file[0])
    },
    async docFileExcel() {
      let success = false
      this.$root.loading(true)
      if (this.dataImport.length > 0) {
        for (var i = 0; i < this.dataImport.length; i++) {
          let response = await API.themMoiNhomCongNo(this.axios, this.dataImport[i])
          if (response.data.error_code === "BSS-00000000") {
            success = true
          }
        }
        if (success) {
          await this.getDanhSachNhomCongNo()
          this.$toast.success("Đọc file thành công")
          this.dsNhomCongNo.forEach(element => {
            if (element.TEN_NHOM === this.dataImport[0]) {
              this.$refs.gridNhomCongNo.setCurrentSelectedRow(element.STT - 1)
            }
          })
        }
      }
      this.dsNhomCongNo.forEach(element => {
        if (element.TEN_NHOM === this.dataImport[0]) {
          console.log(element.STT)
        }
      })
      this.$root.loading(false)
    },
    close() {
      this.$bvModal.hide('popup-capnhatnhomcongno')
      if (this.dsNhomCongNo.length > 0) {
        this.dataSelect.nhomCNoId = this.dsNhomCongNo[0].NHOMCNO_ID
        this.dataSelect.tenNhom = this.dsNhomCongNo[0].TEN_NHOM
      }
      this.setButtonState(false, false, false, false)
    },
    onModalShown: async function(){
      console.log('test');
    },
    validateDate(data) {
      this.$v.$touch();
      let errorMessage = []

      if (this.$v.dataSelect.tenNhom.$error) {
        this.$refs.tenNhom.focus()

        if (!data.tenNhom.toString()) {
          errorMessage.unshift("Trường tên nhóm công nợ bắt buộc nhập.")
        } else if (!data.tenNhom.maxLength) {
          errorMessage.unshift("Trường nhóm công nợ dữ liệu vượt quá độ dài cho phép.")
        }
      }

      if (errorMessage.length > 0) {
        this.$toast.error(errorMessage.join('\n'))
        return true
      } else {
        return false
      }
    }
  }
}
</script>
