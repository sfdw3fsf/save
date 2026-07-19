<template>
  <b-modal
    ref="PopupChiTietLienHe"
    size="xl"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
    @show="handleShowModal"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span> Chi tiết cuộc gọi
        </div>
        <div class="close -ap icon-close" @click="hideModal"></div>
      </div>
        <div class="list-actions-top">
            <ul class="list">
                <li>
                    <a href="javascript: void(0)">
                        <span class="icon one-save"></span>Ghi lại
                    </a>
                </li>
            </ul>
        </div>
      <div class="popup-body">
        <div class="box-form">
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key w120">Cuộc gọi ID:</div>
                        <div class="value">
                            <input type="text" class="form-control highlight" v-model="txt_CallID"/>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key w120">Số gọi</div>
                        <div class="value">
                            <input type="text" class="form-control highlight" v-model="txt_SoGoi"/>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key w120">Giờ bắt đầu</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model="txt_GioBD"/>
                        </div>
                    </div>
                </div>
                <div class="col-sm-3 col-12">
                    <div class="info-row">
                        <div class="key w120">Giờ kết thúc</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model="txt_GioKT"/>
                        </div>
                    </div>
                </div>
                <div class="col-sm-3 col-12">
                    <div class="info-row">
                        <div class="key w120">Giờ kết thúc</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model="txt_ThoiLuong"/>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key w120">Người nhận</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model="txt_NguoiNhan"/>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key w120">Máy tiếp nhận</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model="txt_MayNhan"/>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key w120">Loại cuộc gọi</div>
                        <div class="value align-bottom">
                            <b-form-radio-group
                                v-model="rdb_LoaiCG"
                            >
                                <b-form-radio value="1">Cuộc gọi phản ánh</b-form-radio>
                                <b-form-radio value="2">Cuộc gọi hỗ trợ</b-form-radio>
                            </b-form-radio-group>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key w120">Tỉnh</div>
                        <div class="value">
                            <ejs-dropdownlist v-model="cbo_Tinh.EditValue" :dataSource="cbo_Tinh.DataSource" :fields="{ text: 'tentinh', value: 'tinh_id' }" noRecordsTemplate="Không có dữ liệu tìm kiếm" :allowFiltering="true"/>
                        </div>
                    </div>
                </div>
            </div>
            <div class="box-form">
                <div class="row">
                    <div class="col-sm-12 col-12">
                        <div class="info-row">
                            <div class="key w120">Tên khách hàng</div>
                            <div class="value">
                                <input type="text" class="form-control" v-model="txt_KhachHang"/>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-8 col-12">
                        <div class="info-row">
                            <div class="key w-100">Nội dung hỗ trợ</div>
                            <div class="value d-table-row mt-1">
                                <textarea ref="txt_NoiDungHT" v-model="txt_NoiDungHT"  class="form-control" style="height: 76px;resize: none;"></textarea>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key w-100">Phân loại hỗ trợ</div>
                            <div class="value d-table-row mt-1" style="height: 76px;">
                                <!-- :options="lay_ds_khuvuc_theo_diemchia" -->
                                <tree-view                                
                                    labelField="name"
                                    valueField="id"
                                    @isOpenChange="true"
                                    :selectedValue="tree_PhanLoaiTV.Selection">
                                </tree-view>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="box-form">
                <div class="row">
                    <div class="col-sm-6 col-12">
                        <div class="info-row">
                            <div class="key w120">Loại hình</div>
                            <div class="value">
                                <!-- <ejs-dropdownlist v-model="cbo_LoaiHinhTB.EditValue" :dataSource="cbo_LoaiHinhTB.DataSource" :fields="{ text: 'NAME', value: 'ID' }" noRecordsTemplate="Không có dữ liệu tìm kiếm" :allowFiltering="true"/> -->
                                <ComboboxGrid2  class="select-custom"
                                    :columns="[
                                        {fieldName: 'dichvuvt_id', headerText: 'Dịch vụ ID', allowFiltering: true, width:'130'},
                                        {fieldName: 'ten_dvvt', headerText: 'Dịch vụ', allowFiltering: true, width:'200'},
                                        {fieldName: 'loaitb_id', headerText: 'Loại hình ID', allowFiltering: true, width:'130'},
                                        {fieldName: 'loaihinh_tb', headerText: 'Loại hình TB', allowFiltering: true, width:'200'},
                                    ]"
                                    :dataSource="cbo_LoaiHinhTB.DataSource" textField="loaihinh_tb" valueField="loaitb_id"
                                    :enabledSelectFirstRow="false" :value="cbo_LoaiHinhTB.EditValue" :gridPanelDataHeight="'200px'"
                                    @selectedChanged="(item)=>{if(item) { cbo_LoaiHinhTB.EditValue = item.loaitb_id; }}">
                                </ComboboxGrid2>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-6 col-12">
                        <div class="info-row">
                            <div class="key w120">Mã thuê bao</div>
                            <div class="value">
                                <input type="text" class="form-control" v-model="txt_MaTB"/>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-12 col-12">
                        <div class="info-row">
                            <div class="key w120">Nội dung</div>
                            <div class="value">
                                <textarea ref="txt_NoiDungPA" v-model="txt_NoiDungPA"  class="form-control" style="height: 76px;resize: none;"></textarea>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
      </div>
    </div>
</b-modal>
</template>
<script>
import { BFormRadioGroup, BFormRadio } from 'bootstrap-vue'
import TreeView from './TreeView/TreeView.vue'
import ComboboxGrid2 from '@/components/Controls/ComboboxGrid2'
import api from '../ReceivingCallsSwitchboardApi.js'
export default {
  name: "PopupChiTietLienHe",
  components: {
    BFormRadioGroup,
    BFormRadio,
    TreeView,
    ComboboxGrid2,
  },
  props: ['dulieu'],
  data: () => ({
    txt_CallID: "",
    txt_SoGoi: "",
    txt_GioBD: "",
    txt_GioKT: "",
    txt_ThoiLuong: "",
    txt_NguoiNhan: "",
    txt_MayNhan: "",
    cbo_Tinh: {
      EditValue: "",
      DataSource: []
    },
    rdb_LoaiCG: "1",
    txt_KhachHang: "",
    txt_NoiDungHT: "",
    tree_PhanLoaiTV: {
      Selection: null,
      DataSource: []
    },
    cbo_LoaiHinhTB: {
      EditValue: "",
      DataSource: []
    },
    txt_MaTB: "",
    txt_NoiDungPA: "",
    btn_Save: { Enabled: true },
  }),
  methods: {
    LowerCasePropertyList(obj) {
      return obj.map(function(item) {
        for (var key in item) {
          var upper = key.toLowerCase()
          // check if it already wasn't uppercase
          if (upper !== key) {
            item[upper] = item[key]
            delete item[key]
          }
        }
        return item
      })
    },
    UpperCasePropertyList(obj) {
      return obj.map(function(item) {
        for (var key in item) {
          var upper = key.toUpperCase()
          // check if it already wasn't uppercase
          if (upper !== key) {
            item[upper] = item[key]
            delete item[key]
          }
        }
        return item
      })
    },
    GetDataList: function (response) {
      if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
        return response.data.data
      } else if (response && response.data && response.data.error_code === 'BSS-00000204') {
        return []
      } else {
        this.$toast.error('Lỗi: ' + (response.data.message_detail ? response.data.message_detail : response.data.message))
        return []
      }
    },
    GetData: function (response) {
      if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
        return response.data.data
      } else if (response && response.data && response.data.error_code === 'BSS-00000204') {
        return ''
      } else {
        this.$toast.error('Lỗi: ' + (response.data.message_detail ? response.data.message_detail : response.data.message))
        return ''
      }
    },
    formLoad: async function () {
        if (this.dulieu) {
            this.txt_MaTB = this.dulieu['ma_tb']
            this.txt_CallID = this.dulieu['cuocgoi_id']
            this.cbo_Tinh.EditValue = this.dulieu['tinh_id']
            this.cbo_Tinh.DataSource = this.dulieu['dsTinh'] && this.dulieu['dsTinh'].length > 0 ? this.dulieu['dsTinh'] : []

            this.cbo_LoaiHinhTB.EditValue = this.dulieu['loaitb_id']
            this.cbo_LoaiHinhTB.DataSource = this.dulieu['dsLoaiHinh'] && this.dulieu['dsLoaiHinh'].length > 0 ? this.dulieu['dsLoaiHinh'] : []
            this.txt_NoiDungHT = this.dulieu['noidung_ht']
            this.txt_NoiDungPA = this.dulieu['noidung_ht']
            this.txt_KhachHang = this.dulieu['ten_kh']
        }
        this.cbo_Tinh.DataSource = this.cbo_Tinh.DataSource && this.cbo_Tinh.DataSource.length > 0 ? this.LowerCasePropertyList(this.cbo_Tinh.DataSource) : []
        this.cbo_LoaiHinhTB.DataSource = this.cbo_LoaiHinhTB.DataSource && this.cbo_LoaiHinhTB.DataSource.length > 0 ? this.LowerCasePropertyList(this.cbo_LoaiHinhTB.DataSource) : []
        this.tree_PhanLoaiTV.DataSource = await this.LayLoaiTV()

        var cuocgoi = this.LayChiTietCuocGoi(this.txt_CallID);   
        this.cbo_Tinh.EditValue = cuocgoi.DonVi.DonVi_ID ? cuocgoi.DonVi.DonVi_ID : this.cbo_Tinh.EditValue

        if (cuocgoi) {
            this.txt_GioBD = cuocgoi.GioBD;
            this.txt_GioKT = cuocgoi.GioBD + cuocgoi.ThoiLuong;
            this.txt_SoGoi = cuocgoi.ChuGoi;
            this.txt_NguoiNhan = cuocgoi.TenNguoiNhan;
            this.txt_MayNhan = cuocgoi.MayNghe;
            this.txt_ThoiLuong = cuocgoi.ThoiLuong
            if (cuocgoi.DonVi) this.cbo_Tinh.EditValue = cuocgoi.DonVi.DonVi_ID;

            if (cuocgoi.ChiTiet && cuocgoi.ChiTiet.NoiDung) {
                this.txt_NoiDungHT = cuocgoi.ChiTiet.NoiDung;
                this.txt_NoiDungPA = cuocgoi.ChiTiet.NoiDung;
            }                

            if (cuocgoi.Kieu_CG == Kieu_CG.CuocGoi_PhanAnh) {
                this.rdb_LoaiCG = 0;
                var chitietpa = cuocgoi.ChiTiet;
                this.txt_MaTB = chitietpa.Ma_TB;
                this.cbo_LoaiHinhTB.EditValue = chitietpa.LoaiTB_ID
            }
            if (cuocgoi.Kieu_CG == Kieu_CG.CuocGoi_TuVan) {
                this.rdb_LoaiCG = 1;
                var chitietht = cuocgoi.ChiTiet;
                this.txt_KhachHang = chitietht.TenKH;
                // var node = tree_PhanLoaiTV.FindNodeByKeyID(Convert.ChangeType(chitietht.PhanLoai_ID, _LoaiTV.Columns["LoaiTV_ID"].DataType));
                // tree_PhanLoaiTV.FocusedNode = node;
                // if (node != null)
                //     node.Selected = true;
            }
            if (!this.txt_MaTB || this.txt_MaTB.length == 0) {
                this.rdb_LoaiCG = 1;
                this.$toast.error('Bạn đang lựa chọn một liên hệ. Cần lựa chọn một thuê bao cụ thể để có thể thêm cuộc gọi phản ánh')
                // rdb_LoaiCG.Properties.ReadOnly = true;
                // rdb_LoaiCG.ToolTip = lbl_LoaiCG.OptionsToolTip.ToolTip
                //     = "Bạn đang lựa chọn một liên hệ. Cần lựa chọn một thuê bao cụ thể để có thể thêm cuộc gọi phản ánh";
            }
            // ChucNang.Instance.Nhanvien_ID_Goc --> ttnguoidung.nhanvien_id
            var Nhanvien_ID_Goc = await this.$root.token.getNhanVienID()
            if (cuocgoi.NhanVienNhan_ID != 0 && cuocgoi.NhanVienNhan_ID != Nhanvien_ID_Goc) {
                this.btn_Save.Enabled = false;
                this.$toast.error('Cuộc gọi này không phải do bạn tiếp nhận, không thể cập nhật')
                
                // btn_Save.ToolTipText = "Cuộc gọi này không phải do bạn tiếp nhận, không thể cập nhật";
                // MessageBox.Show(btn_Save.ToolTipText, "Thông báo");
            }
        }
    },
    LayLoaiTV: async function () {
        try {
            var data = this.GetData(await api.sp_tt_loai_tuvan(this.axios));
            return data        
        } catch (e) {
            if (e && e.data) {
                this.$toast.error('Lỗi sp_tt_loai_tuvan ' + JSON.stringify(e.data.message_detail ? e.data.message_detail : e.data.message))
            } else {
                this.$toast.error('Lỗi sp_tt_loai_tuvan ' + JSON.stringify(e))
            }
            return {}
        } finally {

        }
    },
    LayChiTietCuocGoi: async function (cuocgoi_id) {
        try {
            var input = { cuocgoi_id: cuocgoi_id }
            var data = this.GetDataList(await api.sp_lay_ds_cuocgoi(this.axios, input));
            return data[0]        
        } catch (e) {
            if (e && e.data) {
                this.$toast.error('Lỗi LayChiTietCuocGoi ' + JSON.stringify(e.data.message_detail ? e.data.message_detail : e.data.message))
            } else {
                this.$toast.error('Lỗi LayChiTietCuocGoi ' + JSON.stringify(e))
            }
            return {}
        } finally {

        }
    },
    showModal () {
    //   this.formLoad()
      this.$refs["PopupChiTietLienHe"].show()
    },
    hideModal () {
      this.$refs["PopupChiTietLienHe"].hide()
    },
    handleShowModal () {

    },
    btn_Save_Click: async function () {
        try {
            this.loading(true)
            var cg = {};
            cg.ID = this.txt_CallID;
            
            if (this.cbo_Tinh.EditValue) {
                cg.DonVi = this.cbo_Tinh.EditValue
            } else if (this.rdb_LoaiCG == 0) {
                this.$toast.error("Cần xác định tỉnh cho cuộc gọi");
                return;
            }

            if (this.rdb_LoaiCG == 0) //PhanAnh
            {
                var ctpa = {};
                ctpa.ID = this.txt_CallID;
                if (!this.cbo_LoaiHinhTB.EditValue) {
                    this.$toast.error("Cuộc gọi phản ánh cần có loại hình thuê bao");
                    return;
                }
                ctpa.LoaiTB_ID = this.cbo_LoaiHinhTB.EditValue;
                ctpa.DichVuVT_ID = this.cbo_LoaiHinhTB.DataSource.filter(e => e.loaitb_id == this.cbo_LoaiHinhTB.EditValue)[0]['dichvuvt_id']
                ctpa.NoiDung = this.txt_NoiDungPA;
                ctpa.Ma_TB = this.txt_MaTB;

                cg.ChiTiet = ctpa;
                cg.Kieu_CG = Kieu_CG.CuocGoi_PhanAnh;
                await this.GhiLai_CT_CuocGoi(cg);
            }
            else
            {
                var ctht = {};
                ctht.ID = this.txt_CallID;
                ctht.TenKH =this. txt_KhachHang;
                ctht.NoiDung = this.txt_NoiDungHT;
                if (!tree_PhanLoaiTV.Selection) {
                    this.$toast.error("Hãy chọn phân loại tư vấn chi tiết");
                    return;
                }

                ctht.PhanLoai_ID = tree_PhanLoaiTV.Selection

                cg.ChiTiet = ctht;
                cg.Kieu_CG = Kieu_CG.CuocGoi_TuVan;
                await this.GhiLai_CT_CuocGoi(cg);
            }
        } catch (e) {

        } finally {
            this.loading(false)
        }
    },
    GhiLai_CT_CuocGoi: async function (input) {
        try {
            let response = await api.sp_tt_loai_tuvan(this.axios, input);
            if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                this.$toast.success('Ghi lại chi tiết cuộc gọi thành công!')
            } else {
                this.$toast.error('Lỗi GhiLai_CT_CuocGoi: ' + (response.data.message_detail ? response.data.message_detail : response.data.message))
            }   
        } catch (e) {
            if (e && e.data) {
                this.$toast.error('Lỗi GhiLai_CT_CuocGoi ' + JSON.stringify(e.data.message_detail ? e.data.message_detail : e.data.message))
            } else {
                this.$toast.error('Lỗi GhiLai_CT_CuocGoi ' + JSON.stringify(e))
            }
        } finally {

        }
    }
  }
}
</script>
