<template>
    <div class="modal-content popup-box">
        <div class="popup-header">
            <div class="title"><span class="icon one-notepad"></span>Chuẩn hóa HDDT</div>
            <div class="close -ap icon-close hidden" data-dismiss="modal"></div>
        </div>
        <div class="list-actions-top">
            <ul class="list">
                <li>
                    <a href="javascript:;" @click.prevent="btnXemMau_Click">
                        <span class="icon one-file-attach"></span> Xem mẫu
                    </a>
                </li>
                <li>
                    <a href="javascript:;" @click.prevent="btnDongBo_Click">
                        <span class="icon one-sync"></span> Đồng bộ
                    </a>
                </li>
                <li>
                    <a href="javascript:;" @click.prevent="btnTimKiem_Click">
                        <span class="icon one-search"></span> Tìm kiếm
                    </a>
                </li>
                <li>
                    <input
                    type="text"
                    v-model="txtMSTTimKiem.value"
                    :disabled="txtMSTTimKiem.isReadonly"
                    ref="txtMSTTimKiem"
                    class="form-control"
                    />
                </li>
        </ul>
        </div>
        <div class="popup-body">
            <div class="box-form">
                <div class="legend-title">
                    <span class="icon fa fa-angle-up"></span>Thông tin thuê bao phía Backend
                </div>
                <div class="grid-stack-box">
                    <div class="box-col box-form" id="Left_Form1" style="width: calc(50% - 8px);">
                        <div class="info-row">
                            <div class="key">Tên thuê bao (Công ty)</div>
                            <div class="value">
                                <input
                                type="text"
                                v-model="txtTenTB_CompanyName.value"
                                :disabled="txtTenTB_CompanyName.isReadonly"
                                ref="txtTenTB_CompanyName"
                                class="form-control"
                                />
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key">Mã khách hàng</div>
                            <div class="value">
                                <input
                                type="text"
                                v-model="txtMaPhu_CUS_ID.value"
                                :disabled="txtMaPhu_CUS_ID.isReadonly"
                                ref="txtMaPhu_CUS_ID"
                                class="form-control"
                                />
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key">Tên tắt công ty (Domain)</div>
                            <div class="value">
                                <input
                                type="text"
                                v-model="txtMa_Chuan_TenTat.value"
                                :disabled="txtMa_Chuan_TenTat.isReadonly"
                                ref="txtMa_Chuan_TenTat"
                                class="form-control"
                                />
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key">Tỉnh/ TP</div>
                                    <div class="value">
                                        <input
                                        type="text"
                                        v-model="txtOrg_MaTinh.value"
                                        :disabled="txtOrg_MaTinh.isReadonly"
                                        ref="txtOrg_MaTinh"
                                        class="form-control"
                                        />
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w90">Số lượng</div>
                                    <div class="value">
                                        <input
                                        type="text"
                                        v-model="txtSoLuong.value"
                                        :disabled="txtSoLuong.isReadonly"
                                        ref="txtSoLuong"
                                        class="form-control"
                                        />
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key">Trạng thái hệ thống</div>
                            <div class="value">
                                <input
                                type="text"
                                v-model="txtTrangThaiHeThong.value"
                                :disabled="txtTrangThaiHeThong.isReadonly"
                                ref="txtTrangThaiHeThong"
                                class="form-control"
                                />
                            </div>
                        </div>
                    </div>
                    <div class="gutter gutter-horizontal" style="width: 16px;"></div>
                    <div class="box-col box-form" id="Right_Form1" style="width: calc(50% - 8px);">
                        <div class="info-row">
                            <div class="key">Email</div>
                            <div class="value">
                                <input
                                type="text"
                                v-model="txtEmail.value"
                                :disabled="txtEmail.isReadonly"
                                ref="txtEmail"
                                class="form-control"
                                />
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key">Số ĐT</div>
                            <div class="value">
                                <input
                                type="text"
                                v-model="txtSoDT.value"
                                :disabled="txtSoDT.isReadonly"
                                ref="txtSoDT"
                                class="form-control"
                                />
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key">Người đại diện</div>
                            <div class="value">
                                <input
                                type="text"
                                v-model="txtNguoiDD_representperson.value"
                                :disabled="txtNguoiDD_representperson.isReadonly"
                                ref="txtNguoiDD_representperson"
                                class="form-control"
                                />
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key">Địa chỉ</div>
                            <div class="value">
                                <input
                                type="text"
                                v-model="txtDiaChiTB.value"
                                :disabled="txtDiaChiTB.isReadonly"
                                ref="txtDiaChiTB"
                                class="form-control"
                                />
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key">Thông tư</div>
                            <div class="value">
                                <input
                                type="text"
                                v-model="txtThongTu.value"
                                :disabled="txtThongTu.isReadonly"
                                ref="txtThongTu"
                                class="form-control"
                                />
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="box-form">
                <div class="legend-title">
                    <span class="icon fa fa-angle-up"></span>Danh sách mẫu đang dùng
                </div>
                <div class="table-content">
                    <DataGrid
                        class="table-result table-gachle"
                        ref="gdvMauHD"
                        v-bind:columns="gdvMauHD.header"
                        v-bind:dataSource="gdvMauHD.list"
                        :enable-paging-server="false"
                        :allowPaging="true"
                        :showFilter="true"
                        :showColumnCheckbox="false"
                        :commands="[
                            {
                                name: 'xem',
                                cssClass: 'btn btn-primary',
                                width: 20,
                                text: 'O',
                                title: 'Xem mẫu',
                            },
                        ]"
                        :commandColumn="{
                            headerText: '',
                            width: 80,
                            cellCssClass: 'css1',
                            firstColumn: false,
                            show: true,
                        }"
                        @commandClicked="btnXemMauHD_ButtonClick"
                    >
                    </DataGrid>  
                </div>
            </div>
        </div>
        <b-modal id="frmPreviewHTML" title="Mẫu Hóa Đơn Điện Tử" v-html="rawHtml" modal-class="hoso-dientu"
            header-class="popup-header" content-class="tracuu-content" body-class="none-padding tracuu-body" size="huge"
            hide-footer>
        </b-modal>
    </div>    
</template>

<style>
input.disabled,
select.disabled,
.disabled input,
.disabled select,
.disabled /deep/ .select2-selection {
background-color: #eee;
color: lightgray;
}
.e-grid .e-gridheader .e-sortfilter .e-headercelldiv,
.e-grid .e-gridheader .e-stackedheadercelldiv,
.e-grid .e-gridheader .e-headercell .e-headercelldiv.e-headerchkcelldiv {
padding: 0 5px 0 5px !important;
}
@media (min-width: 992px) {
  .modal .modal-huge {
    max-width: 95% !important;
    width: 100% !important;
  }
}
@media (min-width: 576px) {
  .modal .modal-huge {
    max-width: 95% !important;
    width: 100% !important;
  }
}
</style>

<script>
import breadcrumb from "@/components/breadcrumb";
import VueFlatPickr from "vue-flatpickr-component";
import moment from "moment";
import DatePicker from "vue2-datepicker";
import "vue2-datepicker/locale/vi.js";
import "vue2-datepicker/index.css";
import DataGrid from "@/components/Controls/DataGrid";
import {
    required,
    minLength,
    integer,
    maxValue,
} from "vuelidate/lib/validators";
import {
    LoaiHopDong,
    TrangThaiHD,
    DichVuVienThong,
    listDichVuCNTT,
    TRANGBI,
    KieuLapDat,
    KHOANMUC_TT,
    LoaiHinhTB,
    Loai_KM,
    NHOM_LNV,
    LOAI_DV,
} from "@/modules/admin/category/MultiServicePackage/enum.js";
import { Checkbox } from "element-ui";
import api from "./API";

export default {
name: "frmChuanHoaHDDT",
components: {
    required,
    minLength,
    integer,
    maxValue,
    DatePicker,
    VueFlatPickr,
    breadcrumb,
    DataGrid
},
props: ["dulieu"],
data() {
    return {   
        _mst: "",
        _hdtb_id: 0,
        _cus_id: "",
        lst : [],
        rawHtml: "",
        txtTenTB_CompanyName: {value: "", isReadonly: false},
        txtMaPhu_CUS_ID: {value: "", isReadonly: false},
        txtMa_Chuan_TenTat: {value: "", isReadonly: false},
        txtOrg_MaTinh: {value: "", isReadonly: false},
        txtSoLuong: {value: "", isReadonly: false},
        txtTrangThaiHeThong: {value: "", isReadonly: false},
        txtEmail: {value: "", isReadonly: false},
        txtSoDT: {value: "", isReadonly: false},
        txtNguoiDD_representperson: {value: "", isReadonly: false},
        txtDiaChiTB: {value: "", isReadonly: false},
        txtThongTu: {value: "", isReadonly: false},
        txtMSTTimKiem: {value: "", isReadonly: false},
        gdvMauHD: {
            list: [],
            header: [
                {
                    fieldName: "Invcatename",
                    headerText: "Loại mẫu",
                    allowFiltering: true,
                },
                {
                    fieldName: "Templatename",
                    headerText: "Tên mẫu",
                    allowFiltering: true,
                },
                {
                    fieldName: "",
                    headerText: "Xem mẫu",
                    allowFiltering: false,
                },
            ],
            value: {},
            isEnabled: true,
            checked: [],
        }
    };
},
watch: {  
    "dulieu.mst"(val) {
        if (val) {
            this._mst = val
        }        
    },
    "dulieu.hdtb_id"(val) {
        if (val) {
            this._hdtb_id = val
        }        
    }
},
computed: {},
mounted() { 
    this._mst = this.dulieu.mst ? this.dulieu.mst : ""
    this._hdtb_id = this.dulieu.hdtb_id ? this.dulieu.hdtb_id : 0
    this.frmChuanHoaHDDT_Load()
},
methods: {  
    closeForm: function (val) {
        if (val) this.$emit("form-close", val)
    },
    ShowSuccess: function (msg) {
      this.$toast.success(msg);
    },
    ShowError: function (msg) {
      this.$toast.error(msg);
    },
    frmChuanHoaHDDT_Load: async function () {
        try {
            this.lst = []
            await this.TimKiemThongTin();
            await this.LoadThongTinMauHoaDon(this._cus_id, this._mst);
            var kq = await this.LoadSoLuongDaDangKy(this._cus_id, this._mst);
            this.txtSoLuong.value = kq && kq.TotalInvoice ? kq.TotalInvoice : 0;
            if (this.gdvMauHD.list.length == 0)
            {
                var dldb = {};
                dldb.MA_PHU = this._cus_id;
                dldb.LOAITB_ID = LoaiHinhTB.TTDL_HDDT;
                dldb.SL_MAILING = kq && kq.TotalInvoice ? kq.TotalInvoice : 0;
                dldb.MA_PHU_CT = "";
                dldb.MA_CHUAN_CT = "";
                dldb.DOMAIN_CT = "";
                dldb.WEBSITE_CT = "";
                dldb.MA_CHUAN = this.txtMa_Chuan_TenTat.value;
                dldb.SO_DT = this.txtSoDT.value;
                dldb.EMAIL = this.txtEmail.value;
                dldb.TEN_TB = this.txtTenTB_CompanyName.value;
                this.lst.push(dldb);
            } else {
                for (var i = 0; i < this.gdvMauHD.list.length; i++)
                {
                    var dldb = {};
                    dldb.MA_PHU = this._cus_id;
                    dldb.LOAITB_ID = LoaiHinhTB.TTDL_HDDT;
                    dldb.SL_MAILING = kq && kq.TotalInvoice ? kq.TotalInvoice : 0;
                    dldb.MA_PHU_CT = this.gdvMauHD.list[i]["Invcateid"].toString();
                    dldb.MA_CHUAN_CT = this.gdvMauHD.list[i]["Identification"].toString();
                    dldb.DOMAIN_CT = this.gdvMauHD.list[i]["Invcatename"].toString();
                    dldb.WEBSITE_CT = this.gdvMauHD.list[i]["Invcatename"].toString();
                    dldb.MA_CHUAN = this.txtMa_Chuan_TenTat.value;
                    dldb.SO_DT = this.txtSoDT.value;
                    dldb.EMAIL = this.txtEmail.value;
                    dldb.TEN_TB = this.txtTenTB_CompanyName.value;
                    this.lst.push(dldb);
                }
            }
        }
        catch (ex) {
            console.log(ex)
        }
    },
    btnTimKiem_Click: async function () {
        this.lst = []
        this._mst = this.txtMSTTimKiem.value;
        await this.TimKiemThongTin();
        await this.LoadThongTinMauHoaDon(this._cus_id, this._mst);
        var kq = await this.LoadSoLuongDaDangKy(this._cus_id, this._mst);
        if (this.gdvMauHD.list.length == 0)
        {
            var dldb = {};
            dldb.MA_PHU = this._cus_id;
            dldb.LOAITB_ID = LoaiHinhTB.TTDL_HDDT;
            dldb.SL_MAILING = kq && kq.TotalInvoice ? kq.TotalInvoice : 0;
            dldb.MA_PHU_CT = "";
            dldb.MA_CHUAN_CT = "";
            dldb.DOMAIN_CT = "";
            dldb.WEBSITE_CT = "";
            dldb.MA_CHUAN = this.txtMa_Chuan_TenTat.value;
            dldb.SO_DT = this.txtSoDT.value;
            dldb.EMAIL = this.txtEmail.value;
            dldb.TEN_TB = this.txtTenTB_CompanyName.value;
            this.lst.push(dldb);

        }
        else
        {
            for (var i = 0; i < this.gdvMauHD.list.length; i++)
            {
                var dldb = {};
                dldb.MA_PHU = this._cus_id;
                dldb.LOAITB_ID = LoaiHinhTB.TTDL_HDDT;
                dldb.SL_MAILING = kq && kq.TotalInvoice ? kq.TotalInvoice : 0;
                dldb.MA_PHU_CT = this.gdvMauHD.list[i]["Invcateid"].toString();
                dldb.MA_CHUAN_CT = this.gdvMauHD.list[i]["Identification"].toString();
                dldb.DOMAIN_CT = this.gdvMauHD.list[i]["Invcatename"].toString();
                dldb.WEBSITE_CT = this.gdvMauHD.list[i]["Invcatename"].toString();
                dldb.MA_CHUAN = this.txtMa_Chuan_TenTat.value;
                dldb.SO_DT = this.txtSoDT.value;
                dldb.EMAIL = this.txtEmail.value;
                dldb.TEN_TB = this.txtTenTB_CompanyName.value;
                this.lst.push(dldb);
            }
        }
    },
    btnDongBo_Click: async function () {
      try {
        var input = {
            vhdtb_id: this._hdtb_id,
            vdata: JSON.stringify(this.lst)
        }
        let response = await api.dongbo_dulieu_hddt(this.axios, input);
        if (
          response &&
          response.data &&
          response.data.error_code == "BSS-00000000"
        ) {
            this.ShowSuccess("Đồng bộ thành công!");
        } else {
          this.ShowError("Lỗi đồng bộ HDDT " + response.data.message_detail);
        }
      } catch (ex) {
        console.log(ex);
      }
    },
    btnXemMau_Click: async function () {
        if (!this.$refs.gdvMauHD.getSelectedRecords() || this.$refs.gdvMauHD.getSelectedRecords().length == 0) {
            this.ShowError("Bạn chưa chọn mẫu HDDT đang sử dụng nào!")
            return
        }
        var id = this.$refs.gdvMauHD.getSelectedRecords()[0]["Identification"];
        var html = await this.HTMLMauHoaDon(id);
        if (html) {
            this.rawHtml = html
            this.$bvModal.show("frmPreviewHTML");
        }
    },
    btnXemMauHD_ButtonClick: async function (cmdName, rowData) {
        if (!rowData) {
            this.ShowError("Bạn chưa chọn mẫu HDDT đang sử dụng nào!")
            return
        }
        var id = rowData["Identification"];
        var html = await this.HTMLMauHoaDon(id);
        if (html) {
            this.rawHtml = html
            this.$bvModal.show("frmPreviewHTML");
        }
    },
    TimKiemThongTin: async function () {
      try {
        var ttkh = await this.LayTTKhachHangHDDT(this._mst);
        var kq = await this.$bvModal.msgBoxConfirm("Khách hàng có 2 hệ thống là 32, 78. Bạn đồng bộ hệ thống nào?", {
          title: "Thông báo",
          size: "m",
          okTitle: "Đồng bộ dữ liệu HT thông tư 78",
          cancelTitle: "Đồng bộ dữ liệu HT thông tư 32",
        })

        var index = 0;
        if (ttkh.Versions.length > 0) {
            if (kq == false)
            {
                for (var i = 0; i < ttkh.Versions.length; i++)
                {
                    if (ttkh.Versions[i].type == "Hệ thống HDDT 32")
                    {
                        index = i;
                        break;
                    }
                }
            }
            if (kq == true)
            {
                for (var i = 0; i < ttkh.Versions.length; i++)
                {
                    if (ttkh.Versions[i].type == "Hệ thống HDDT TT78")
                    {
                        index = i;
                        break;
                    }
                }
            }
            this._cus_id = ttkh.Versions[index].cus_id;
            this.txtTenTB_CompanyName.value = ttkh.company_name;
            this.txtEmail.value = ttkh.email;
            this.txtSoDT.value = ttkh.tel;
            this.txtOrg_MaTinh.value = ttkh.Versions[index].org;
            this.txtDiaChiTB.value = ttkh.address;
            this.txtMa_Chuan_TenTat.value = ttkh.Versions[index].short_name;
            this.txtMaPhu_CUS_ID.value = ttkh.Versions[index].cus_id;
            this.txtNguoiDD_representperson.value = ttkh.represent_person;
            this.txtTrangThaiHeThong.value = ttkh.Versions[index].status;
            this.txtThongTu.value = ttkh.Versions[index].type;
        }
        
      } catch (ex) {
        console.log(ex);
      }
    },
    LayTTKhachHangHDDT: async function (mst) {
      try {
        let response = await api.LayTTKhachHangHDDT(this.axios, mst);
        if (
          response &&
          response.data &&
          response.data.errorCode == 0
        ) {
          return response.data.data;
        } else {
          this.ShowError(
            "Không lấy được TT khách hàng HDDT " + response.data.faultString
          );
          return {}
        }
      } catch (ex) {
        console.log(ex);
        return {}
      }
    },
    LoadSoLuongDaDangKy: async function (cus_id, mst) {
      try {
        let response = await api.LaySoLuongHDDTDaDK(this.axios, cus_id, mst);
        if (
          response &&
          response.data &&
          response.data.errorCode == 0
        ) {
          return response.data.data;
        } else {
          this.ShowError(
            "Không lấy được SL đã DK " + response.data.faultString
          );
          return {}
        }
      } catch (ex) {
        console.log(ex);
        return {}
      }
    },
    LoadThongTinMauHoaDon: async function (cus_id, mst) {
        this.txtMSTTimKiem.value = mst;
        this.gdvMauHD.list = await this.LayDSMauHoaDonDaDangKy(cus_id, mst);
    },
    LayDSMauHoaDonDaDangKy: async function (cus_id, mst) {
      try {
        var input = {
            cus_id: cus_id,
            taxcode: mst
        }
        let response = await api.LayDSMauHoaDonDaDangKy(this.axios, input);
        if (
          response &&
          response.data &&
          response.data.errorCode == 0
        ) {
          return response.data.data.datas;
        } else {
          this.ShowError(
            "Không lấy được DS mẫu hóa đơn đã DK " + response.data.faultString
          );
          return []
        }
      } catch (ex) {
        console.log(ex);
        return []
      }
    },
    HTMLMauHoaDon: async function (id) {
      try {
        let response = await api.LayChiTietMauHoaDon(this.axios, id);
        if (
          response &&
          response.data &&
          response.data.errorCode == 0
        ) {
          return response.data.data;
        } else {
          this.ShowError(
            "Không lấy được CT mẫu hóa đơn đã DK " + response.data.faultString
          );
          return ""
        }
      } catch (ex) {
        console.log(ex);
        return ""
      }
    }
},
};
</script>