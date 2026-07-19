<template src="./index.html"></template>

<script>

// import Vue from 'Vue';
import define from "./define/index.js";
import api from "./api/index.js";
import moment from "moment";
import { GridPlugin, Page, Filter } from "@syncfusion/ej2-vue-grids";
import { L10n } from '@syncfusion/ej2-base';
import GiaoPhieuNV from '@/modules/contract/setup/DeclareLandline/popup/giaophieunhanvien';
import InBienBan from '@/modules/print/InBienBan/InBienBan.vue';
import Popup_TienKM from '@/modules/contract/setup/InstallNewSubs/Popups/popupTienKM.vue';

L10n.load({
    'vi': {
        // 'grids': {
        //     'Item': 'Artikel',
        //     'Items': 'Artikel'
        // },
        'pager': {
            'currentPageInfo': 'Bản ghi từ số {1} đến số {2} trong tổng số {0}',
            'totalItemsInfo': 'Bản ghi từ {1} đến số {2} trong tổng số {0}',
            'pagerDropDown': 'Bản ghi trên trang'
        }
    }
});
// Vue.use(GridPlugin);
export default {
    provide: {
        grid: [Page, Filter]
    },
    components: {
        api,
        moment,
        GiaoPhieuNV,
        InBienBan,
        Popup_TienKM
    },
    // props: ['tag'],
    async created() {
        // this.handleTag();
        await this.frmHoanCong_DangKyGoi_DaDV_Load();
        // await this.initData();
    },
    data() {
        return {
            ...define,
            params: {},
            selectionSettings: { mode: 'Row', type: 'Single' },
            index: 0,
            dulieuTienKM: {
                ds: [],
                cols: [
                    // {
                    //     headerText: 'Khoản mục id',
                    //     allowSorting: true,
                    //     fieldName: 'khoanmuctt_id'
                    // },
                    {fieldName: 'khoanmuctt_id', headerText: 'Khoản mục', allowFiltering: false, allowSorting: false, isPrimaryKey:true}, 
                    {fieldName: 'tongtien', textAlign:'right', allowEditing:true, editType: 'numericedit', format:'N', customAttributes:{class: 'tongtien'},  headerText: 'Tổng tiền', allowFiltering: false, allowSorting: false, edit:"tongtienParam"}, 
                    {fieldName: 'tien', textAlign:'right', allowEditing:true, editType: 'numericedit', format:'N', customAttributes:{class: 'cuoclapdat'},  headerText: 'Tiền', allowFiltering: false, allowSorting: false, edit:"tienParam"},
                    {fieldName: 'vat', textAlign:'right', format:'N', customAttributes:{class: 'vatlapdat'}, headerText: 'Vat', allowFiltering: false, allowSorting: false},
                ],
                tieude: 'Chi tiết tiền theo khoản mục',
                tieudebang: 'Chi tiết tiền theo khoản mục',
                allowExit: true
            }
        }
    },
    methods: {
        formCloseTienKM(val){
        if(val){
            this.ds_tien_khoanmuc = val;
            var dtien = 0;
            var dvat = 0;
            var dtongtien = 0;
            for (var i = 0; i < this.ds_tien_khoanmuc.length; i++)
            {
                dtien += this.ds_tien_khoanmuc[i]["tien"];
                dvat += this.ds_tien_khoanmuc[i]["vat"];
                dtongtien += this.ds_tien_khoanmuc[i]["tongtien"];
            }
            // this.list_ChiTietTien.tien = dtien;
            // this.list_ChiTietTien.tienvat = dvat;
            this.form.tong_tien = dtongtien;
        }
            this.$refs.popupTienKM.hide();
        },
        btnTien_Click(){
            try {
                this.dulieuTienKM.ds = [...this.ds_tien_khoanmuc];
                this.$refs.popupTienKM.show();
            } catch (error) {
                console.log(error);
            }
        },
        async btnNDTH_Click(){
            try {
                // if (this.phieu_id == 0) return;
                // bangts.Update_ND_THUCHIEN(phieu_id, txtNoiDungTH.Text.Trim());
                let res = await this.axios.post('/web-ccdv/HoanCongThuTienPhatSinh/CAPNHAT_NOIDUNG_GIAOPHIEU', { 
                    vphieu_id: this.phieu_id,
                    vnd_thuchien: this.form.noi_dung_th
                });
                if(res.data.error_code == 'BSS-00000000'){
                    this.$toast.success('Cập nhật thành công!'); // Message_Box.ShowTB("Cập nhật thành công!");
                }else{
                    this.$toast.warning('Cập nhật không thành công!');
                }
            } catch (error) {
                console.log(error);
            }
        },
        async frmHoanCong_DangKyGoi_DaDV_Load(){
            try {
                // handle tag
                if (this.$route.query.tag != null && 
                    this.$route.query.tag.length > 0) {
                    this.tag = this.$route.query.tag
                }
                // debugger
                // Gan vao theo tham so cua form
                if (this.tag != null && this.tag.length > 0) {
                    let arr = this.tag.split('+')
                    console.log('xxxxxxxx ',arr);
                    if (arr.length >= 1) this.tthd_id = parseInt(arr[0])
                    if (arr.length >= 2) this.dsloaihd_id = arr[1].replace(';',',');
                    if (arr.length >= 3) this.dsdichvuvt_id = arr[2].replace(';',',');
                } else {
                    console.log('yyyyyy');
                    this.dsdichvuvt_id = "2";
                    this.dsloaihd_id = "17";
                    this.tthd_id = this.TrangThaiHD.DANG_THI_CONG;
                }
                // this.handleTag();
                await this.getDsDichVu();
                await this.getKQXL();
                await this.getTTTienKhoanMuc(this.LoaiHopDong.THU_KHAC);
                await this.getDsNVGiaoViec();

                await this.initData();
            } catch (error) {
                console.log(error);
                // this.$toast.error(error)
            }
        },
        async initData(){
            try {
                this.tt_nd = await this.$root.token.getThongTinNguoiDung();
                this.phanvung_id = this.$root.token.getPhanVungID();
                this.donvi_id = this.$root.token.getDonViID();
                this.vnhanvien_id = this.$root.token.getNhanVienID();
                this.form.ngay_gv = moment(new Date()).format("DD/MM/YYYY HH:mm");
                this.form.ngay_ht = moment(new Date()).format("DD/MM/YYYY HH:mm");
                this.form.dich_vu_id = this.cbDichVu.length > 0 ? this.cbDichVu[0]['DICHVUVT_ID'] : '';
                this.form.nguoi_gv = this.cbNguoiGv.length > 0 ? this.cbNguoiGv[0]['nhanvien_id'] : '';

                this.HienThiDanhSachHDKH(true);
            } catch (error) {
                // this.$toast.error(error)
                console.log(error);
            }
        },
        
        clickButton(key) {
            // if (!this.button[key]) {
            //     return false;
            // }
            if (key == "layTT") {
                this.tsbtnLayTT_Click();
            }
            if (key == "capnhat") {
                this.tsbtnCapNhat_Click();
            }
            if (key == "giaoviec") {
                this.tsbtnGiaoViec_Click();
            }
            if (key == "hoancong") {
                this.tsbtnHoanCong_Click();
            }
            if (key == "xuatphieu") {
                this.tsbtnXuatPhieu_Click();
            }
            if(key == 'test'){
                // console.log(typeof this.form.ngay_gv);
                // console.log(this.form.ngay_ht);
                this.$confirm('Bạn thật sự muốn hoàn công không ?', 'Thông báo', {
                    // confirmButtonText: 'Có',
                    // cancelButtonText: 'Không'
                }).then(() => {
                    // console.log('xxx');
                }).catch(e => {
                    console.log(e);
                })
            }
        },
        tsbtnXuatPhieu_Click(){
            try{
                // this.param.hdkh_id = res.data.data[0].hdkh_id;
                // this.param.hdtb_id = res.data.data[0].hdtb_id;
                // this.param.ma_gd = res.data.data[0].ma_gd;
                // this.param.ngay_yc = res.data.data[0].ngay_yc.slice(0,10).split('-').reverse().join('/');
                // this.param.ngaylap_hd = res.data.data[0].ngaylap_hd.slice(0,10).split('-').reverse().join('/');
                if(this.options.hopdong.length > 0){
                    this.$refs.popupInHopDong.showModal();
                }else{
                    this.$alert("Hãy chọn phiếu", "Thông báo", {
                      dangerouslyUseHTMLString: true,
                      confirmButtonText: "OK",
                      type: "info",
                    });
                    // this.$alert('', 'Thông báo')
                }

            }catch(e){
                console.log(e);
            }
        },
        tsbtnHoanCong_Click(){
            this.Hoan_Cong();
        },
        // UR2.4.054_027
        async Hoan_Cong(){
            try
            {
                // if (this.options.hopdong.length)
                //     return;

                // if (!this.KiemTra_DL())
                //     return;

                if (!this.form.isChecked_KQ_XL || Number(this.form.kq_xl) == 0)
                {
                    this.$toast.warning("Bạn chưa chọn kết quả xử lý !");
                    return;
                }

                this.$confirm('Bạn thật sự muốn hoàn công không ?', 'Thông báo', {
                    confirmButtonText: 'Có',
                    cancelButtonText: 'Không'
                }).then(() => {
                    console.log('Thực hiện hoàn công');
                }).catch(e => {
                    return;
                })
                // if (this.$toast.warning("Bạn thật sự muốn hoàn công không ?", "Thông báo", 
                // MessageBoxButtons.YesNo, MessageBoxIcon.Information) != DialogResult.Yes) return;

                if (!this.CapNhat())
                    return;

                if (this.tthd_id != TrangThaiHD.THANH_TOAN)
                {
                    let dsphieu = [];
                    let hg_phieu_id = 0;
                    let donvi_nhan_id = 0;
                    let tthd_dich_id = 0;

                    
                    // dsphieu = bangts.Lay_DonVi_GiaoPhieu_HTHS(huonggiao_id, this.hdtb_id);

                    // if (dsphieu.Tables[0].Rows.Count > 0)
                    // {
                    //     hg_phieu_id = Convert.ToInt32(dsphieu.Tables[0].Rows[0]["huonggiao_id"].ToString());
                    //     donvi_nhan_id = Convert.ToInt32(dsphieu.Tables[0].Rows[0]["donvi_id"].ToString());
                    //     tthd_dich_id = Convert.ToInt32(dsphieu.Tables[0].Rows[0]["tthd_id"].ToString());
                    //     if (!bangts.KiemTra_Phieu_DaGiao(hdtb_id, hg_phieu_id))
                    //     {
                    //         Message_Box.ShowTB("Thuê bao " + dgvDs_HopDong.CurrentRow.Cells["clma_tb"].ToString() + " đã hoàn công rồi. Bạn hãy kiểm tra lại");
                    //         return;
                    //     }
                    //     else
                    //     {
                    //         if (Convert.ToInt32(cboPhanHoi.SelectedValue) == 18)
                    //         {
                    //             if (!ThanhToan_HD(hdtb_id))
                    //                 return;
                    //         }
                    //         hoancong.CAPNHAT_THONGTIN_PHIEU(phieu_id, Convert.ToInt32(cboNguoiGV.SelectedValue), (int)tt_nd.nhanvien_id, dtpNgayBG.Value, dtpNgayGV.Value);

                    //         string str = "";
                    //         str += " Update " + DatabaseConstants.DB2 + ".giaophieu ";
                    //         str += " set kqxl_id = " + Convert.ToInt32(cboPhanHoi.SelectedValue);
                    //         str += " where phieu_id = " + phieu_id;
                    //         bangts.ExeSQL(str);

                    //         hoancong.GIAOPHIEU_TD_KHI_HC(hdtb_id, phieu_id, hg_phieu_id, tt_nd.donvi_id, tt_nd.donvi_id, tt_nd.nhanvien_id, tt_nd.ma_nd, tt_nd.may_cn);
                    //         bangts.CapNhat_TT_Phieu_HTHS(hdtb_id);
                    //         hoancong.HOANCONG_HOPDONG_THUKHAC(hdtb_id, dtpNgayBG.Value);
                    //         Message_Box.ShowTB("Hoàn công thành công! ");
                    //     }
                    // }
                    // else
                    // {
                    //     Message_Box.ShowWarning("Chưa thiết lập đơn vị nhận phiếu. Liên hệ admin để xử lý !");
                    //     return;
                    // }
                    let tien = 0;
                    this.ds_tien_khoanmuc.forEach(e => {
                        tien+= e['tien'];
                    })
                    
                    let FN_FM_HCTIENPS_HOANCONG = await this.axios.post('/web-ccdv/HoanCongThuTienPhatSinh/FN_FM_HCTIENPS_HOANCONG', {
                        p_hdtb_id: this.hdtb_id,
                        p_ketqua_xl_id: this.form.kq_xl,
                        p_phieu_id: this.phieu_id,
                        p_nhanvien_gv_id: this.form.nguoi_gv,
                        p_nhanvien_th_id: this.nhanvien_id,
                        p_ngay_th: this.form.ngay_ht,
                        p_ngay_gv: this.form.ngay_gv,
                        p_donvi_id: this.donvi_id, 
                        P_NGUOI_CN: this.tt_nd.nguoi_cn,
                        P_MAY_CN: this.tt_nd.may_cn,
                        P_TIEN: tien
                    });
                    if(FN_FM_HCTIENPS_HOANCONG.data.data.toLowerCase() == 'ok'){
                        this.$toast.success('Hoàn công thành công!');
                    }else{
                        this.$toast.warning('Hoàn công chưa thành công!');
                    }
                }
                // else
                // {
                //     hoancong.CAPNHAT_THONGTIN_PHIEU(phieu_id, Convert.ToInt32(cboNguoiGV.SelectedValue), (int)tt_nd.nhanvien_id, dtpNgayBG.Value, dtpNgayGV.Value);
                //     bangts.CapNhat_TT_Phieu_HTHS(hdtb_id);
                //     hoancong.HOANCONG_HOPDONG_THUKHAC(hdtb_id, dtpNgayBG.Value);
                //     Message_Box.ShowTB("Hoàn công thành công! ");
                // }
                HienThiDanhSachHDKH();
            }
            catch (exp)
            {
                console.log(exp);
            }
        },
        tsbtnGiaoViec_Click(){
            if (!this.options.hopdong.length)
            {
                this.$toast.warning("Chưa có danh thuê bao. Bạn hãy kiểm tra lại! ");
                return;
            }
            if (!this.cboNguoiGV.length || !this.form.nguoi_gv)
            {
                this.$toast.warning("Hãy nhập nhân viên giao việc!");
                // cboNguoiGV.Focus();
                return;
            }
            if (!this.form.isChecked_ngayGV)
            {
                this.$toast.warning("Hãy nhập ngày giao việc!");
                // chkNgayGV.Focus();
                return;
            }
            if (this.isChecked_ngayHT)
            {
                // System.TimeSpan diffResult = dtpNgayBG.Value.Subtract(dtpNgayGV.Value);
                let tem = this.compareDate(this.form.ngay_gv, this.form.ngay_ht);
                if (tem)
                {
                    this.$toast.warning("Ngày giao việc không được lớn hơn ngày hoàn thành!");
                    return;
                }
            }
            this.$refs.dialogGiaoPhieuNV.show();
            this.LAY_DS_NHANVIEN_THEO_PHIEU_ID(this.phieu_id, this.vnhanvien_id, 2);
        },
        rowSelected(e){
            console.log('abc ', e.data, e);
            // e.data
                if(e){
                    this.index = this.$refs.grid_hopdong.getSelectedRowIndexes();
                    this.model.hopdong = e;
                    this.setParmas(e);
                    this.dgvDs_HopDong_SelectionChanged(e);
                }
        },
        setParmas(data){
            this.params.n_hdkh_id = data.hdkh_id;
            this.params.n_hdtb_id = data.hdtb_id;
            this.params.ma_gd = data.ma_gd;
            this.params.ngay_yc = data.ngay_yc.slice(0,10).split('-').reverse().join('/');;
            this.params.ngaylap_hd = data.ngaylap_hd.slice(0,10).split('-').reverse().join('/');;
            this.params.n_baohong_id = data.baohong_id ? data.baohong_id : 0;
            this.params.huonggiao_id = data.huonggiao_id ? data.huonggiao_id : 0;
            this.params.tentram_tb = data.tentram_tb ? data.tentram_tb : '';
            this.params.n_phieu_id = data.phieu_id ? data.phieu_id : 0;
            this.params.cv_thuchien = data.nd_thuchien ? data.nd_thuchien : '';
            this.params.nvth_id = data.nhanvien_id ? data.nhanvien_id : 0;
        },
        tsbtnLayTT_Click(){
            this.HienThiDanhSachHDKH(true);
        },
        async tsbtnCapNhat_Click(){
            try
            {
                if (await this.CapNhat())
                {
                    // let vmagd = this.form.ma_gd;
                    this.HienThiDanhSachHDKH(true);
                    this.$toast.success("Cập nhật dữ liệu lên viễn thông tỉnh thành công!");

                    // for (let i = 0; i < this.options.hopdong.length; i++)
                    // {
                    //     if (this.options.hopdong[i]["ma_gd"].toString() == vmagd.toString())
                    //     {
                    //         this.options.hopdong.CurrentCell = this.options.hopdong.Rows[i].Cells[0];
                    //         this.options.hopdong.CurrentRow.Selected = false;
                    //         this.options.hopdong.Rows[i].Selected = true;
                    //         break;
                    //     }
                    // }
                }else{
                    this.$toast.error("Cập nhật dữ liệu lên viễn thông tỉnh không thành công!")
                }
            }
            catch (error)
            {
                console.log("Có lỗi : " + error);
            }
        },
        async CapNhat(){
            try {
                // debugger
                if (!this.options.hopdong.length)
                    return false;
                if (!this.KiemTra_DL())
                    return false;
                
                if (this.form.isChecked_Doi_Toc_Do)
                {
                    // let str = "";
                    // if (this.i_dichvuvt_id == this.DichVuVienThong.ADSL)
                    // {
                    //     str += " Update " + DatabaseConstants.DB2 + ".hdtb_adsl ";
                    //     str += " set tocdo_id = " + Nunber(this.form.doi_toc_do);
                    //     str += " where hdtb_id = " + hdtb_id;
                    // }
                    // if (this.i_dichvuvt_id == this.DichVuVienThong.MEGAWAN)
                    // {
                    //     str += " Update " + DatabaseConstants.DB2 + ".hdtb_mgwan ";
                    //     str += " set tocdo_id = " + Nunber(this.form.doi_toc_do);
                    //     str += " where hdtb_id = " + hdtb_id;
                    // }
                    // if (this.i_dichvuvt_id == this.DichVuVienThong.TSL)
                    // {
                    //     str += " Update " + DatabaseConstants.DB2 + ".hdtb_tsl ";
                    //     str += " set tocdo_id = " + Nunber(this.form.doi_toc_do);
                    //     str += " where hdtb_id = " + hdtb_id;
                    // }
                    // bangts.ExeSQL(str);
                    if (this.i_dichvuvt_id == this.DichVuVienThong.ADSL)
                    {

                    }
                    await this.axios.post('web-ccdv/HoanCongThuTienPhatSinh/FN_FM_HCTIENPS_CAPNHAT', {
                        P_UPDATE_TOCDO: this.i_tocdo_id,
                        P_DICHVUVT_ID: this.form.dich_vu_id,
                        P_HDTB_ID: this.hdtb_id,
                        P_TOCDO_ID: this.form.doi_toc_do,
                        P_JS_DATCOC_HDTB: Json.stringify(this.dsDC_HDTB.KHUYENMAI_HDTB),
                        P_JS_CT_TIENHD: Json.stringify(this.TaoDuLieu_CT_TIENHD(true)),
                        P_PHIEU_ID: this.phieu_id,
                        P_NHANVIEN_GV_ID: this.form.nguoi_gv,
                        P_NHANVIEN_TH_ID: this.form.nv_yc,
                        P_NGAY_TH: this.form.ngay_ht,
                        P_NGAY_GV: this.form.ngay_gv
                    })
                }

                // this.TaoDuLieu_CT_TIENHD(true);
                // lapHD.CapNhat_Tien_Thukhac(hdtb_id, dsCTTHD, dsDC_HDTB);
                // hoancong.CAPNHAT_THONGTIN_PHIEU(phieu_id, Convert.ToInt32(cboNguoiGV.SelectedValue), (int)tt_nd.nhanvien_id, dtpNgayBG.Value, dtpNgayGV.Value);
                return true;
            } catch (error) {
                console.log(error);
                return false;
            }
        },
        async TaoDuLieu_CT_TIENHD(themmoi){
            try {
                this.dsCTTHD.CT_TIENHD = [];
                let rowCTTHD = {};
                for (let i = 0; i < this.ds_tien_khoanmuc.length; i++)
                {
                    rowCTTHD.HDTB_ID = this.hdtb_id;
                    if (themmoi == true)
                    {
                        rowCTTHD.CT_TIENHD_ID = await this.axios.post('/web-ccdv/tien_trinh_bao_hong/get_keys', {
                            keyname: 'CT_TIENHD'
                        });
                        
                    }
                    rowCTTHD.KHOANMUCTT_ID = Number(this.ds_tien_khoanmuc[i]["khoanmuctt_id"].toString());
                    if (Number(this.ds_tien_khoanmuc[i]["khoanmuctt_id"].toString()) == this.KHOANMUC_TT.KMTT_KM_LAPDAT
                        || Number(this.ds_tien_khoanmuc[i]["khoanmuctt_id"].toString()) == this.KHOANMUC_TT.KMTT_KM_THIETBI
                        || Number(this.ds_tien_khoanmuc[i]["khoanmuctt_id"].toString()) == this.KHOANMUC_TT.KMTT_KM_DATCOC)
                    {
                        if (Number(this.ds_tien_khoanmuc[i]["tien"].toString()) > 0)
                        {
                            rowCTTHD.TIEN = Number(this.ds_tien_khoanmuc[i]["tien"].toString()) * -1;
                            rowCTTHD.VAT = Number(this.ds_tien_khoanmuc[i]["vat"].toString()) * -1;
                        }
                        else
                        {
                            rowCTTHD.TIEN = Number(this.ds_tien_khoanmuc[i]["tien"].toString());
                            rowCTTHD.VAT = Number(this.ds_tien_khoanmuc[i]["vat"].toString());
                        }
                    }
                    else
                    {
                        rowCTTHD.TIEN = Number(this.ds_tien_khoanmuc[i]["tien"].toString());
                        rowCTTHD.VAT = Number(this.ds_tien_khoanmuc[i]["vat"].toString());
                    }
                    rowCTTHD.ID = Number(this.ds_tien_khoanmuc[i]["khoanmuctt_id"].toString());
                    rowCTTHD.LOAI_ID = 1;
                    rowCTTHD.phanvung_id = this.phanvung_id;
                    dsCTTHD.CT_TIENHD.push(rowCTTHD);
                    return dsCTTHD.CT_TIENHD;
                }
            } catch (error) {
                console.log(error);
            }
        },
        KiemTra_DL(){
            try
            {
                if (!this.form.isChecked_ngayGV)
                {
                    this.$toast.warning("Hãy chọn ngày giao việc !");
                    // chkNgayGV.Focus();
                    return false;
                }

                if (!this.form.isChecked_ngayHT)
                {
                    this.$toast.warning("Hãy nhập ngày hoàn thành!");
                    // chkNgayBG.Focus();
                    return false;
                }

                // System.TimeSpan diffResult = dtpNgayBG.Value.Subtract(dtpNgayGV.Value);
                let temCompare = this.compareDate(this.form.ngay_gv, this.form.ngay_ht);
                if (temCompare)
                {
                    this.$toast.warning("Ngày giao việc không được lớn hơn ngày hoàn thành!");
                    return false;
                }
                let ngay_sys;
                let ngay_ht;
                ngay_sys = this.model.hopdong["thangnam_sys"].toString().trim();
                ngay_ht = this.form.ngay_ht;
                if (!this.KiemTraDK_HoanThanh(ngay_ht, ngay_sys))//Nếu ngày thanh toán > ngày hoàn thành
                {
                    this.$toast.warning("Bạn không được phép chọn ngày hoàn thành lớn hơn ngày hiện tại");
                    return false;
                }
                let ngay_gv = "";
                ngay_gv = this.form.ngay_gv;
                if (!this.KiemTraDK_HoanThanh(ngay_gv, ngay_sys))//Nếu ngày giao viêc > ngày hiện tại
                {
                    this.$toast.warning("Bạn không được phép chọn ngày giao việc lớn hơn ngày hiện tại");
                    return false;
                }
                if (dtpNgayBG.Value < dtpNgayGV.Value)
                {
                    this.$toast.warning("Bạn không được chọn ngày thực hiện nhỏ hơn ngày giao việc !");
                    return false;
                }
                return true;
            }
            catch (ex)
            {
                console.log("Có lỗi : " + ex);
                return false;
            }
        },
        KiemTraDK_HoanThanh(thangnam_tt, thangnam_sys){
            let result = false;
            let tt = new Date(thangnam_tt).getTime();
            let sys = new Date(thangnam_sys).getTime();
            result = tt < sys ? true : false;
            return result;
        },
        compareDate(ngay_gv, ngay_ht){
            if(ngay_gv && ngay_ht){
                let ngayGV = new Date(ngay_gv);
                let ngayHT = new Date(ngay_ht);
    
                if(ngayGV.getTime() > ngayHT.getTime()){
                    return true;
                }
            }
            return false;
        },
        handleTag(){
            // Lay gia trị tag truyen tu url
            if (this.$route.query.tag != null && 
                this.$route.params.tag != '' && 
                this.$route.query.tag.length > 0) {
                this.tag = this.$route.query.tag
            }
            
            // Gan vao theo tham so cua form
            if (this.tag != null && this.Tag.length > 0) {
                let arr = this.tag.split('+')
                console.log(arr);
                if (arr.length >= 1) this.tthd_id = parseInt(arr[0])
                if (arr.length >= 2) this.dsloaihd_id = arr[1].replace(';',',');
                if (arr.length >= 3) this.dsdichvuvt_id = arr[2].replace(';',',');
            } else {
                this.dsdichvuvt_id = "2";
                this.dsloaihd_id = "17";
                this.tthd_id = this.TrangThaiHD.DANG_THI_CONG;
            }
        },
        async getDsDichVu(){
            try {
                console.log('dsdichvuvt_id', this.dsdichvuvt_id);
                let res = await api.getCBDichVu(this.axios);
                this.cbDichVu = res.data.data.length > 0 ? res.data.data.filter(dv => this.dsdichvuvt_id.includes(dv.DICHVUVT_ID)) : [];
            } catch (error) {
                // this.$toast.error(error)
                console.log(error);
            }
        },
        async getKQXL(){
            try {
                let res = await api.getKQXL(this.axios, {P_NHOMKQ_ID: 6, P_KIEU: 0});
                this.cbKQ_XL = [...res.data.data];
            } catch (error) {
                // this.$toast.error(error)
                console.log(error);
            }
        }, 
        async getTTTienKhoanMuc(vloaihd_id){
            try {
                let res = await api.getTTTienKhoanMuc(this.axios, {
                    P_LOAIHD_ID: vloaihd_id
                });
                this.ds_tien_khoanmuc = res.data.data;
            } catch (error) {
                // this.$toast.error(error)
                console.log(error);
            }
        },
        async getDsNVGiaoViec(){
            try {
                let res = await api.getDsNVGiaoViec(this.axios, {
                    P_DONVI_ID: this.$root.token.getDonViID()
                });
                this.cbNguoiGv = res.data.data;
            } catch (error) {
                this.$toast.error(error)
            }
        },
        async getDsQuyTrinh(){
            try {
                let res = await api.getDsQuyTrinh(this.axios, {
                    p_dsloaihd_id: this.dsloaihd_id ,
                    p_dichvuvt_id: this.form.dich_vu_id,
                    p_tthd_id: this.tthd_id == 2 ? this.TrangThaiHD.DA_THI_CONG_XONG : this.tthd_id, 
                    p_kieu: 0

                    // p_dsloaihd_id: "1",
                    // p_dichvuvt_id: "4",
                    // p_tthd_id: "1",
                    // p_kieu: 0 
                });
                this.cbQuyTrinh = res.data.data;
                // console.log(this.dsloaihd_id, this.form.dich_vu_id, this.tthd_id);
            } catch (error) {
                this.$toast.error(error);
            }
        },
        async getDsHDTBGiaoDenThuTien(vphieu_id, vloaihd_id, vhuonggiao_id, vnhanvien_id, vtthd_id, vhdkh_id){
            try {
                this.isLoading = true;
                let res = await api.getDsHDTBGiaoDenThuTien(this.axios, {
                    vphieu_id: vphieu_id,
                    vloaihd_id: vloaihd_id,
                    vhuonggiao_id: vhuonggiao_id,
                    vnhanvien_id: vnhanvien_id,
                    vtthd_id: vtthd_id,
                    vhdkh_id: vhdkh_id

                    // vphieu_id: 11639915,
                    // vloaihd_id: 11,
                    // vhuonggiao_id: 172,
                    // vnhanvien_id: 4448,
                    // vtthd_id: 1,
                    // vhdkh_id: 5264388
                });
                if(res.data.data.length > 0)
                    this.options.hopdong = res.data.data;
                this.isLoading = false
            } catch (error) {
                // this.$toast.error(error);
                console.log(error);
                this.isLoading = false;
            }
        },
        async HienThiDanhSachHDKH(kt_load){
            try {
                if(kt_load){
                    let i_huonggiao_id = this.huonggiao_id;
                    if (this.tthd_id == this.TrangThaiHD.THANH_TOAN){
                        i_huonggiao_id = 0;
                    }
                    await this.getDsHDTBGiaoDenThuTien(0, 17, i_huonggiao_id, this.vnhanvien_id, this.tthd_id, 0);
                    this.Clear();
                }
            } catch (error) {
                // this.$toast.error(error)
                console.log(error);
            }
        },
        Clear()
        {
            this.form.ten_kh = "";
            this.dia_chi_kh = "";
            this.ten_thanh_toan = "";
            this.dia_chi_thanh_toan = "";
            this.nv_yc = "";
            this.noi_dung_th = "";
            this.noi_dung_giao = "";
            this.txtGhiChu = "";

            this.kq_xl = 0;
            this.options.nhanvien = [];
            
            this.form.ma_gd = "";
            this.form.ma_tb = "";
            this.form.kieu_yc = "";

            this.form.isChecked_ngayHT = false;
            this.form.isChecked_ngayGV = false;
            this.form.ngay_ht = moment(new Date()).format("DD/MM/YYYY HH:mm");
            this.form.ngay_gv = moment(new Date()).format("DD/MM/YYYY HH:mm");

            this.vma_tb = "";
            this.hdtb_id = 0;
            this.hdkh_id = 0;
            this.phieu_id = 0;
            this.button.hoancong = false;
            this.button.giaophieu = false;

            this.form.isChecked_KQ_XL = false;
            this.form.isChecked_Doi_Toc_Do = false;
            this.form.chkKM = false;
            this.form.cuocDC = 0;
            this.form.so_thang = 0;
            this.form.tienTD = 0;
            this.form.tong_tien = 0;

            if(this.ds_tien_khoanmuc && this.ds_tien_khoanmuc.length > 0){
                for(let j = 0; j < this.ds_tien_khoanmuc.length; j++)
                {
                    this.ds_tien_khoanmuc[j]["tien"] = 0;
                    this.ds_tien_khoanmuc[j]["vat"] = 0;
                }
            }
        },
        async dgvDs_HopDong_SelectionChanged(row){
            try {
                this.hdkh_id = Number(row["hdkh_id"] ? row["hdkh_id"] : 0);
                this.form.ten_kh = row["ten_kh"] ? row["ten_kh"].toString() : '';
                this.form.dia_chi_kh = row["diachi_kh"] ? row["diachi_kh"].toString() : '';
                this.form.ma_gd = row["ma_gd"] ? row["ma_gd"].toString() : '';
                this.form.nv_yc = row["nv_lhd"] ? row["nv_lhd"].toString() : '';
                
                let i_huonggiao_id = this.huonggiao_id;;
                if (this.tthd_id == TrangThaiHD.THANH_TOAN)
                    i_huonggiao_id = 0;

                this.phieu_id = Number(row["phieu_id"] ? row["phieu_id"] : 0);
                this.hdtb_id = Number(row["hdtb_id"] ? row["hdtb_id"] : 0);
                this.form.ma_tb = row["ma_tb"] ? row["ma_tb"].toString() : '';
                this.form.kieu_yc = row["ten_kieuld"] ? row["ten_kieuld"].toString() : '';
                this.form.noi_dung_th = row["nd_thuchien"] ? row["nd_thuchien"].toString() : '';
                this.form.noi_dung_gv = row["nd_giao"] ? row["nd_giao"].toString() : '';
                this.isChecked_ngayHT = false;
                
                if (row["ngay_ht_gp"])
                {
                    if (this.tthd_id == this.TrangThaiHD.MOI || this.tthd_id == this.TrangThaiHD.THANH_TOAN)
                    {
                        let t = new Date();
                        t.setSeconds(t.getSeconds() + 30);
                        this.form.ngay_ht = moment(t).format("DD/MM/YYYY HH:mm");
                        this.button.hoancong = false;
                    }
                    else
                    {
                        this.form.isChecked_ngayHT = true;
                        this.form.ngay_ht = row["ngay_ht_gp"];
                        this.button.hoancong = true;
                    }
                }
                else
                {
                    let t = new Date();
                    t.setSeconds(t.getSeconds() + 30);
                    this.form.ngay_ht = moment(t).format("DD/MM/YYYY HH:mm");
                    this.button.hoancong = false;
                }

                if (this.tthd_id != this.TrangThaiHD.THANH_TOAN)
                {
                    if (this.PHAILAM("HOANCONG"))
                    {
                        if (row["ngay_ht_gp"].toString())
                            this.button.hoancong = true;
                        else
                            this.button.hoancong = false;
                    }
                    // NO NEED THIS BUTTON
                    // if (this.PHAILAM("GIAOPHIEU"))
                    // {
                    //     if (row["ngay_ht_gp"].toString())
                    //         this.button.giao = true;
                    //     else
                    //         this.button.giao = false;
                    // }
                }
                else
                {
                    this.button.hoancong = true;
                    // NO NEED BUTTON GIAO PHIEU
                    // tsbtnGiaoPhieu.Enabled = true;
                }

                this.form.ten_thanh_toan = row["ten_tt"] ? row["ten_tt"] : "";
                this.form.dia_chi_thanh_toan = row["diachi_tt"] ? row["diachi_tt"] : "";
                this.form.txtGhiChu = row["ghichu"] ? row["ghichu"] : "";
                this.vdoituong_id = row["doituong_id"] ? row["doituong_id"] : 1;
                this.i_loaitb_id = row["loaitb_id"] ? row["loaitb_id"] : 0;
                this.i_dichvuvt_id = row["dichvuvt_id"] ? row["dichvuvt_id"] : 0;
                
                this.i_tocdo_id = -1;
                this.i_muccuoc_id = 0;
                let dt = await this.getLayTocDoCuTocDoMoi(this.i_dichvuvt_id, this.hdtb_id);
                if (dt.Rows.Count > 0)
                {
                    this.i_tocdo_id = Number(dt[0]["tocdo_id"]);
                    if (this.i_loaitb_id == this.LoaiHinhTB.INTERNET_ADSL || this.i_loaitb_id == this.LoaiHinhTB.INTERNET_FTTH 
                    || this.i_loaitb_id == this.LoaiHinhTB.WIFI_FIBER)
                        this.i_muccuoc_id = Number(dt[0]["muccuoc_id"]);

                    if (this.i_dichvuvt_id == this.DichVuVienThong.ADSL || this.i_dichvuvt_id == this.DichVuVienThong.TSL 
                    || this.i_dichvuvt_id == this.DichVuVienThong.MEGAWAN)
                    {
                        if (Number(dt[0]["tocdo_id"]) != Number(dt[0]["tocdo_cu_id"]))
                            this.form.isChecked_Doi_Toc_Do = true;
                        else
                            this.form.isChecked_Doi_Toc_Do = false;
                    }
                }

                // set cb Toc do
                let tocdo = await this.axios.get('/web-ccdv/HoanCongThuTienPhatSinh/SP_LAY_THONGTIN_TOCDO_ADSL_TSL', {
                    params: {
                        P_DICHVUVT_ID: this.i_dichvuvt_id,
                        P_LOAITB_ID: this.i_loaitb_id,
                        P_TOCDO_ID: this.i_tocdo_id
                    }
                });
                this.cbDoiTocDo = tocdo.data.data;

                // set ds khuyen mai HT_DS_KhuyenMai_Combobox(i_loaitb_id, i_tocdo_id, i_muccuoc_id);
                this.HT_DS_KhuyenMai_Combobox(this.i_loaitb_id, this.i_tocdo_id, this.i_muccuoc_id);

                //Longdx: bổ sung lấy chi tiết KM
                let lay_chitiet_km_theo_hdtb_id = await this.get('/web-ccdv/HoanCongThuTienPhatSinh/lay_chitiet_km_theo_hdtb_id', {
                    params: {
                        vhdtb_id: this.hdtb_id,
                        vloai_km: 1
                    }
                });
                let dsTempTT = lay_chitiet_km_theo_hdtb_id.data.data;
                if (dsTempTT && dsTempTT.length > 0){
                    if (dsTempTT[0]["khuyenmai_id"])
                    {
                        this.form.km = dsTempTT[0]["khuyenmai_id"];
                        this.form.chkKM = true;
                    }
                    else
                    {
                        this.form.chkKM = false;
                    }

                    // DateTime tmp;
                    let ithang_huongdc = 0, ithang_huongkm = 0, ithang_tg = 0;
                    let dtiendatcoc_csd = 0, dtien_td = 0;

                    for(let i = 0; i < dsTempTT.length; i++)
                    {
                        let dr;
                        ithang_huongdc = dsTempTT[i]["thang_huongdc"];
                        ithang_huongkm = dsTempTT[i]["thang_huongkm"];
                        ithang_tg = dsTempTT[i]["sothang_tg"];
                        dtiendatcoc_csd = dsTempTT[i]["datcoc_csd"];
                        dtien_td = dsTempTT[i]["tien_td"];
                        dr.THANG_HUONGDC = ithang_huongdc;
                        dr.THANG_HUONGKM = ithang_huongkm;
                        dr.SOTHANG_TG = ithang_tg;
                        dr.DATCOC_CSD = dtiendatcoc_csd;
                        dr.TIEN_TD = dtien_td;

                        // txtTienTD.Text = dtien_td.ToString(Format.FM_TIEN_VND);
                        this.form.tienTD = this.currencyFormater(dtien_td);
                        // txtCuocDC.Text = dtiendatcoc_csd.ToString(Format.FM_TIEN_VND);
                        this.form.cuocDC = this.currencyFormater(dtiendatcoc_csd);
                        this.form.so_thang = ithang_huongdc;
                        dr.THANG_BD = null;
                        dr.THANG_KT = null;
                        dr.THANG_BDTG = null;
                        dr.THANG_KTTG = null;
                        dr.THANG_BDDC = null;
                        dr.THANG_KTDC = null;
                        // DỮ LIỆU 
                        // if (dsTempTT[i]["kieu_bdkm"] == 3)
                        // {
                        //     if (DateTime.TryParseExact(dsTempTT[i]["thang_bdkm"], "yyyyMM", culture, System.Globalization.DateTimeStyles.None, out tmp))
                        //     {
                        //         dr.THANG_BD = int.Parse(dsTempTT[i]["thang_bdkm"]);
                        //         dr.THANG_KT = int.Parse(tmp.AddMonths(ithang_huongkm).ToString("yyyyMM"));
                        //     }
                        // }
                        // if (dsTempTT[i]["kieu_bdtg"] == 3)
                        // {
                        //     if (DateTime.TryParseExact(dsTempTT[i]["thang_bdtg"], "yyyyMM", culture, System.Globalization.DateTimeStyles.None, out tmp))
                        //     {
                        //         dr.THANG_BDTG = int.Parse(dsTempTT[i]["thang_bdtg"]);
                        //         dr.THANG_KTTG = int.Parse(tmp.AddMonths(ithang_tg).ToString("yyyyMM"));
                        //     }
                        // }

                        // if (dsTempTT[i]["kieu_bddc"] == "3")
                        // {
                        //     if (DateTime.TryParseExact(dsTempTT[i]["thang_bddc"], "yyyyMM", culture, System.Globalization.DateTimeStyles.None, out tmp))
                        //     {
                        //         dr.THANG_BDDC = int.Parse(dsTempTT[i]["thang_bddc"]);
                        //         dr.THANG_KTDC = int.Parse(tmp.AddMonths(ithang_huongkm).ToString("yyyyMM"));
                        //     }
                        // }

                        //longdx: bổ sung thông tin tỷ lệ km
                        dr.TYLE_KMLD = dsTempTT[i]["tyle_kmld"];
                        dr.TYLE_SD = dsTempTT[i]["tyle_sd"];
                        dr.TYLE_TB = dsTempTT[i]["tyle_tb"];
                        dr.HDTB_ID = this.hdtb_id;
                        dr.CHITIETKM_ID = dsTempTT[i]["chitietkm_id"];
                        dr.KHUYENMAI_ID = dsTempTT[i]["khuyenmai_id"];
                        dr.KIEU_YC = Convert.ToInt32(dsTempTT[i]["kieu_yc"]);
                        dr.phanvung_id = this.phanvung_id;
                        this.dsDC_HDTB.KHUYENMAI_HDTB.push(dr);
                    }
                }
                else
                {
                    chkTraTruoc.Checked = false;
                    dsDC_HDTB = new DATCOC_HDTB_DATA();

                }

                this.ds_tien_khoanmuc = await this.getTTTienKhoanMuc(this.LoaiHopDong.THU_KHAC);
                let ds_km = this.LAY_TIENHD_KM_THEO_HDTB(this.hdtb_id);
                if (ds_km.length > 0)
                    this.ds_tien_khoanmuc = ds_km;
                for(let i = 0; i < this.ds_tien_khoanmuc.length; i++)
                {
                    if (this.ds_tien_khoanmuc[i]["khoanmuctt_id"] == this.KHOANMUC_TT.KMTT_DOITOCDO)
                    {
                        this.dtienld = this.ds_tien_khoanmuc[i]["tien"];
                        this.dvatld = this.ds_tien_khoanmuc[i]["vat"];
                    }
                    if (this.ds_tien_khoanmuc[i]["khoanmuctt_id"] == this.KHOANMUC_TT.KMTT_DATCOC)
                    {
                        this.dtiendatcoc_tt = this.ds_tien_khoanmuc[i]["tien"];
                        this.dvatdatcoc_tt = this.ds_tien_khoanmuc[i]["vat"];
                    }
                    if (this.ds_tien_khoanmuc[i]["khoanmuctt_id"] == this.KHOANMUC_TT.KMTT_DATCOC_KM)
                    {
                        dtiendatcoc_km = this.ds_tien_khoanmuc[i]["tien"];
                        dvatdatcoc_km = this.ds_tien_khoanmuc[i]["vat"];
                    }
                }
                this.form.cuocDC = Number(this.currencyFormater(this.dtiendatcoc_tt + this.dvatdatcoc_tt));
                this.LayTongTien_HDTB();

                let ds = this.LAY_DS_NHANVIEN_THEO_PHIEU_ID(this.phieu_id, this.vnhanvien_id, 2);
                this.HienThiDSNV(ds);
            } catch (error) {
                // this.$toast.error(error);
                console.log(error);
            }
        },
        async HienThiDSNV(ds){
            try {
                if(ds.length > 0)
                {
                    this.form.isChecked_ngayGV = true;
                    this.form.ngay_gv = ds[0]["ngaygiao"];
                    this.form.nguoi_gv = ds[0]["nhanvien_giao_id"];
                    this.options.nhanvien = [...ds];
                    
                    this.nhanvien_tc_id = Number(ds[0]["nhanvien_th_id"]);
                    this.congviec_th = ds[0]["nhiemvu"];
                }
                else
                {
                    this.options.nhanvien = [];
                    this.form.ngay_gv = moment(new Date()).format("DD/MM/YYYY HH:mm");
                    this.form.nguoi_gv = this.vnhanvien_id;
                    this.nhanvien_tc_id = -1;
                    this.congviec_th = "";
                }
            } catch (error) {
                this.$toast.error(error);
            }
        },
        async LAY_DS_NHANVIEN_THEO_PHIEU_ID(vphieu_id, nhanvien_id, kieu_id){
            try {
                let res = await this.axios.get('/web-ccdv/HoanCongThuTienPhatSinh/LAY_DS_NHANVIEN_THEO_PHIEU_ID?VPHIEU_ID=' + 
                vphieu_id + '&VNHANVIEN_ID=' + nhanvien_id + '&VKIEU_ID=' + kieu_id);
                return res.data.data;
            } catch (error) {
                this.$toast.error(error);
            }
        },
        LayTongTien_HDTB()
        {
            try
            {
                let dtongtien = 0;
                let dtongvat = 0;
                for(let i = 0; i < this.ds_tien_khoanmuc.length; i++)
                {
                    dtongtien += Number(ds_tien_khoanmuc[i]["tien"]);
                    dtongvat += Number(ds_tien_khoanmuc[i]["vat"]);
                }
                this.tong_tien = Number(this.currencyFormater(dtongtien + dtongvat));
            }
            catch (error)
            {
                this.$toast.error(error);
            }
        },
        async LayLuongThaoTac(luong_id){
            try {
                let res = await this.axios.post('/web-ccdv/khaibaoims/sp_lay_luong_thaotac', {
                    luong_id: luong_id,
                });
                return res.data.data;
            } catch (error) {
                // this.$toast.error(error);
                console.log(error);
            }
        },
        PHAILAM(thaotac){
            try {
                if(this.dtThaoTac.length > 0) {
                    let dr = this.dtThaoTac.filter(e => e.enable == 1 && e.code == thaotac);
                    if(dr.length)
                        return true;
                    return false;
                } else {
                    return false;
                }
            } catch (error) {
                // this.$toast.error(error);
                console.log(error);
            }
        },
        async getLayTocDoCuTocDoMoi(P_DICHVUVT_ID, P_HDTB_ID){
            try {
                let res = await api.getLayTocDoCuTocDoMoi(this.axios, {
                    P_DICHVUVT_ID: P_DICHVUVT_ID,
                    P_HDTB_ID: P_HDTB_ID
                });
                return res.data.data;
            } catch (error) {
                this.$toast.error(error);
            }
        },
        currencyFormater(c){
            try {
                // Create our number formatter.
                var formatter = new Intl.NumberFormat('en-US', {
                style: 'currency',
                currency: 'USD',

                // These options are needed to round to whole numbers if that's what you want.
                //minimumFractionDigits: 0, // (this suffices for whole numbers, but will print 2500.10 as $2,500.1)
                //maximumFractionDigits: 0, // (causes 2500.99 to be printed as $2,501)
                });
                return formatter.format(c);
            } catch (error) {
                this.$toast.error(error);
            }
        },
        async LAY_TIENHD_KM_THEO_HDTB(hdtb_id){
            try {
                let res = await this.axios.get('/web-ccdv/HoanCongThuTienPhatSinh/LAY_TIENHD_KM_THEO_HDTB?vhdtb_id=' + hdtb_id);
                return res.data.data;
            } catch (error) {
                this.$toast.error(error);
            }
        },
        async SP_LAY_HUONGGIAO_THEO_QUYTRINH(quytrinh_id, i_tthd_id){
            try { 
                let res = await this.axios.get('/web-ccdv/HoanCongThuTienPhatSinh/SP_LAY_HUONGGIAO_THEO_QUYTRINH?P_QUYTRINH_ID='+ quytrinh_id + '&P_TTHD_ID=' + i_tthd_id);
                return res.data.data
            } catch (error) {
                // this.$toast.error(error);
                console.log(error);
            }
        },
        async cboQuyTrinh_SelectedValueChanged(e){
            try {
                    // alert(e)
                    // let luong_id = 0, huonggiao_id = 0;
                    let quytrinh_id = Number(this.form.quy_trinh_id);
                    let i_tthd_id = this.tthd_id;
                    if (this.tthd_id == this.TrangThaiHD.THANH_TOAN)
                        i_tthd_id = this.TrangThaiHD.DA_THI_CONG_XONG;

                    let ds = [];
                    ds = await this.SP_LAY_HUONGGIAO_THEO_QUYTRINH(quytrinh_id, i_tthd_id);
                    if (ds.length > 0)
                    {
                        this.luong_id = Number(ds[0]["luong_id"]);
                        this.huonggiao_id = Number(ds[0]["huonggiao_id"]);
                        // let tenhg = ds[0]["huonggiao"];
                        // labelFunctionTitle.Text = tenhg.ToUpper();
                        // Text = tenhg;
                    }
                    else
                    {
                        this.$toast.warning("Không tìm thấy luồng quy trình nghiệp vụ này.\nBạn hãy liên hệ Admin kiểm tra lại");
                        this.huonggiao_id = -1;
                        await this.HienThiDanhSachHDKH(true);
                        return;
                    }
                    // await this.HienThiGiaoDien(this.luong_id);
                    await this.HienThiDanhSachHDKH(true);

                    if (!this.PHAILAM("GIAOVIEC"))
                    {
                        this.form.isChecked_ngayHT = true;
                        this.form.isChecked_ngayGV = true;
                        this.form.ngay_ht = moment().add(30, 'seconds').format('DD/MM/YYYY hh:mm');
                        this.form.ngay_gv = moment(new Date()).format("DD/MM/YYYY HH:mm");
                    }
                
            } catch (error) {
                // this.$toast.error(error);
                console.log(error);
            }
        },
        async sp_lay_ct_thaotac_control(thaotac_id){
            try {
                let result = await this.axios.post('/web-hopdong/thanhly/sp_lay_ct_thaotac_control', {
                    thaotac_id: thaotac_id
                });
                return result.data.data;
            } catch (error) {
                // this.$toast.error(error);
                console.log(error);
            }
        },
        async txtMaGD_KeyPress(e){
            try {
                // alert(e.target.value)
                if(e.target.value){
                    let vmagd = e.target.value
                    this.$refs.grid_hopdong.search(vmagd);
                }else{
                    this.$refs.grid_hopdong.ej2Instances.searchSettings.key = "";
                }
            } catch (error) {
                console.log(error);
            }
        },
        chkTocDo(e){
            try {
                // alert(e.target.checked);
                if(e.target.checked){
                    
                    if (this.i_dichvuvt_id == this.DichVuVienThong.ADSL || 
                        this.i_dichvuvt_id == this.DichVuVienThong.MEGAWAN || 
                        this.i_dichvuvt_id == this.DichVuVienThong.TSL)
                        this.HT_DS_KhuyenMai_Combobox(this.i_loaitb_id, this.form.doi_toc_do, this.i_muccuoc_id);
                    else
                        this.HT_DS_KhuyenMai_Combobox(this.i_loaitb_id, this.i_tocdo_id, this.i_muccuoc_id);
                }else{
                    this.HT_DS_KhuyenMai_Combobox(this.i_loaitb_id, this.i_tocdo_id, this.i_muccuoc_id);
                }
            } catch (error) {
                console.log(error);
            }
        },
        chkTTruoc(e){
            try {
                this.LaySothang_SD();
            } catch (error) {
                console.log(error);
            }
        },
        async LaySothang_SD(){
            try {
                let sothang_sd = 0;
                if (this.form.chkKM)
                {
                    let khuyenmai_id_tt = Number(this.form.km);
                    // let strStkm = checkdata.MAP_ID("sothang_sd",
                    // DatabaseConstants.DB2 + ".khuyenmai", "where khuyenmai_id =" + khuyenmai_id_tt);
                    let strStkm = await this.axios.get('HoanCongThuTienPhatSinh/FN_LAY_KHUYENMAI_SOTHANG_SD?P_KHUYENMAI_ID='+khuyenmai_id_tt);
                    let stkm = strStkm != "-1" ? Number(strStkm) : 0;
                    sothang_sd = stkm;
                }
                this.form.so_thang = sothang_sd;
            } catch (error) {
                
            }
        },
        async HT_DS_KhuyenMai_Combobox( i_loaitb_id, i_tocdo_id, i_muccuoc_id){
            try {
                let km = await this.axios.get('/web-ccdv/HoanCongThuTienPhatSinh/SP_LAY_DS_KHUYENMAI', {
                    params: {
                        P_KIEULD_ID: 550,
                        P_LOAITB_ID: i_loaitb_id,
                        P_MUCCUOC_ID: i_muccuoc_id, 
                        P_DOITUONG_ID: this.vdoituong_id, 
                        P_TOCDO_ID: i_tocdo_id,
                        P_THUEBAO_ID: 0, 
                        P_NHOM_DATCOC_ID: 1
                        
                        // P_KIEULD_ID: 550,
                        // P_LOAITB_ID: 58,
                        // P_MUCCUOC_ID: 1, 
                        // P_DOITUONG_ID: 1, 
                        // P_TOCDO_ID: 532,
                        // P_THUEBAO_ID: 0, 
                        // P_NHOM_DATCOC_ID: 1
                    }
                });
                this.cbKM = km.data.data;
            } catch (error) {
                console.log(error);
            }
        },
        cboKhuyenMai_SelectedValueChanged(){
            try {
                // dsDC_HDTB = new DATCOC_HDTB_DATA();
                this.dtiendatcoc_tt = 0;
                this.dvatdatcoc_tt = 0;
                this.HienThiTien_TraTruoc();
                this.form.tienTD = 0;
                this.form.cuocDC = 0;
                this.form.so_thang = 0;
            } catch (error) {
                console.log(error);
            }
        },
        async HienThiTien_TraTruoc(){
            try {
                for (let j = 0; j < this.ds_tien_khoanmuc.length; j++)
                {
                    if (this.ds_tien_khoanmuc[j]["khoanmuctt_id"].toString() == this.KHOANMUC_TT.KMTT_DATCOC_KM.toString())
                    {
                        this.ds_tien_khoanmuc[j]["tien"] = this.dtiendatcoc_tt;
                        this.ds_tien_khoanmuc[j]["vat"] = this.dvatdatcoc_tt;
                    }
                    if (this.ds_tien_khoanmuc[j]["khoanmuctt_id"].toString() == this.KHOANMUC_TT.KMTT_DATCOC.toString())
                    {
                        this.ds_tien_khoanmuc[j]["tien"] = this.dtiendatcoc_tt;
                        this.ds_tien_khoanmuc[j]["vat"] = this.dvatdatcoc_tt;
                    }
                }
                this.LayTongTien_HDTB();
            } catch (error) {
                console.log(error);
            }
        },
        LayTongTien_HDTB(){
            try {
                let dtongtien = 0;
                let dtongvat = 0;
                for (let i = 0; i < this.ds_tien_khoanmuc.length; i++)
                {

                    dtongtien += Number(this.ds_tien_khoanmuc[i]["tien"].toString());
                    dtongvat += Number(this.ds_tien_khoanmuc[i]["vat"].toString());

                }
                this.form.tong_tien = this.currencyFormater((dtongtien + dtongvat))
            } catch (error) {
                console.log();
            }
        }
        // async HienThiGiaoDien(luong_id){
        //     try {
        //         this.dtThaoTac = await this.LayLuongThaoTac(luong_id);
        //         if(this.dtThaoTac.length > 0)
        //         {
        //             for(let i = 0; i < this.dtThaoTac.length; i++)
        //             {
        //                 this.dtControl = await this.sp_lay_ct_thaotac_control(this.dtThaoTac[i]['thaotac_id']);
        //                 if (this.dtControl.length > 0)
        //                 {
        //                     for (let j = 0; j < this.dtControl; j++)
        //                     {
        //                         let control_name = this.dtControl[j]["control_name"];
        //                         let ctrl = this.Controls.Find(control_name, true);
        //                         if (ctrl.Length > 0)
        //                         {
        //                             switch (ctrl[0].GetType().FullName)
        //                             {
        //                                 case "System.Windows.Forms.Button":
        //                                     if (dtThaoTac.Rows[i]["enable"].ToString() == "1")
        //                                         ctrl[0].Enabled = true;
        //                                     else
        //                                         ctrl[0].Enabled = false;
        //                                     break;
        //                                 case "System.Windows.Forms.ComboBox":
        //                                     if (dtThaoTac.Rows[i]["enable"].ToString() == "1")
        //                                         ctrl[0].Enabled = true;
        //                                     else
        //                                         ctrl[0].Enabled = false;
        //                                     break;
        //                                 case "System.Windows.Forms.TextBox":
        //                                     if (dtThaoTac.Rows[i]["enable"].ToString() == "1")
        //                                     {
        //                                         ctrl[0].Enabled = true;
        //                                         ctrl[0].BackColor = Color.Cornsilk;
        //                                         ctrl[0].ForeColor = Color.Maroon;
        //                                     }
        //                                     else
        //                                         ctrl[0].Enabled = false;
        //                                     break;
        //                                 default:
        //                                     break;
        //                             }
        //                         }
        //                         else
        //                         {
        //                             ToolStripItem[] toolStrip = tlstpTop.Items.Find(control_name, true);
        //                             if (toolStrip.Length > 0)
        //                             {
        //                                 switch (toolStrip[0].GetType().FullName)
        //                                 {
        //                                     case "System.Windows.Forms.ToolStripButton":
        //                                         if (dtThaoTac.Rows[i]["enable"].ToString() == "1")
        //                                             toolStrip[0].Visible = true;
        //                                         else
        //                                             toolStrip[0].Visible = false;
        //                                         break;
        //                                     default:
        //                                         break;
        //                                 }
        //                             }
        //                         }
        //                     }
        //                 }
        //             }
        //         }
        //     } catch (error) {
        //         this.$toast.error(error);
        //     }
        // }
    },

    watch: {
        "form.dich_vu_id": async function(){
            await this.getDsQuyTrinh();
            this.form.quy_trinh_id = this.cbQuyTrinh.length > 0 ? this.cbQuyTrinh[0]['quytrinh_id'] : '';
        },
        "form.doi_toc_do": async function(){
            if (this.form.isChecked_Doi_Toc_Do)
            {
                if (this.i_dichvuvt_id == this.DichVuVienThong.ADSL || this.i_dichvuvt_id == this.DichVuVienThong.MEGAWAN || this.i_dichvuvt_id == this.DichVuVienThong.TSL)
                    this.HT_DS_KhuyenMai_Combobox(this.i_loaitb_id, this.form.doi_toc_do, this.i_muccuoc_id);
                else
                    this.HT_DS_KhuyenMai_Combobox(this.i_loaitb_id, this.i_tocdo_id, this.i_muccuoc_id);
                this.form.chkKM = false;
            }
        }
    }, 
}
</script>

<style>
.list a{
    cursor: pointer;
}
/* .e-grid td.e-selectionbackground {
    background-color: #F9E1A9!important;
}
.e-grid tr:hover {
    background-color: #FFF9EB !important;
}
.e-checkbox-wrapper .e-check::before, .e-css.e-checkbox-wrapper .e-check::before {
    content: "\e718";
    background-color: transparent;
    line-height: 16px;
}
.e-checkbox-wrapper .e-stop::before, .e-css.e-checkbox-wrapper .e-stop::before {
    content:  "\e7a5" !important
} */
</style>