<template src="./index.template.html"></template>
<script>
import ComboboxGrid from "@/components/Controls/ComboboxGrid"
export default {
    name:'PopupGanThueBaoVaoToaNha',

    components: { appCombobox: ComboboxGrid },

    props: {
        Ma_GD: {
            type: String,
            default: ''
        }
    },

    // async created() {
    //     await this.HT_ToaNha();
    // },

    data() {
        return {
            hdtb_id: 0,
            tag: '',
            vma_tb: '',
            vma_kh: '',
            txt_MaKH: {
                text: null,
                enabled: true,
                readOnly: false
            },
            txt_MaTB: {
                text: null,
                enabled: true,
                readOnly: false
            },
            txt_TenTB: {
                text: null,
                enabled: true,
                readOnly: false
            },
            cbo_QuanHuyen: {
                items: [],
                selected: null
            },
            chk_QuanHuyen: {
                enabled: true,
                checked: false
            },
            cbo_PhuongXa: {
                items: [],
                selected: null
            },
            chk_PhuongXa: {
                enabled: true,
                checked: false
            },
            cbo_ToaNha: {
                items: [],
                selected: null,
                readOnly: false
            },
            grid_ThueBao: {
                items: [],
                selected: {}
            },
            title: 'Gán thuê bao vào tòa nhà',
            notify: 'Thông báo',
            btn_Visible: {
                timKiem: true,
                ghiLai: true,
                xoa: true
            }
        }
    },

    methods: {
        async onShownModal() {
            await this.HT_ToaNha();
            await this.loadForm();

            let elemts = this.$refs.toaNhaCbo.$refs.grid.$el.querySelectorAll('.e-headercell');
            elemts.forEach(elment => {
                elment.style.display = 'none';
            });
        },

        onHiddenModal() {
            this.clear();
        },
        
        async loadForm() {
            await this.getDsQuan();
            if (this.cbo_QuanHuyen.items.length > 0)
                this.cbo_QuanHuyen.selected = this.cbo_QuanHuyen.items[0].id;
            if (this.tag === "tracuu_danhba" || this.tag === "tracuu_hopdong") {
                this.title = 'Tra cứu thông tin tòa nhà';
                this.btn_Visible.ghiLai = false;
                this.btn_Visible.xoa = false;
                this.notify = '';
                this.txt_MaKH.enabled = true;
                this.txt_MaTB.readOnly = true;
                this.txt_TenTB.readOnly = true;
                this.txt_MaKH.text = this.vma_kh;
                //label29.Text = "TRA CỨU THÔNG TIN TÒA NHÀ";
                this.chk_PhuongXa.checked = false;
                this.chk_QuanHuyen.checked = false;
                this.cbo_ToaNha.readOnly = true;
            } else {
                this.title = 'Gán thuê bao vào tòa nhà';
                this.notify = '(*) Gán thuê bao vào tòa nhà với các dịch vụ: Cố định, Băng rộng cố định, Megawan, Metronet, Truyền số liệu, IMS';
                this.btn_Visible.timKiem = false;
                this.txt_MaKH.enabled = false;
                this.txt_MaTB.enabled = false;
                this.txt_TenTB.enabled = false;
                this.chk_QuanHuyen.checked = true;
                this.chk_PhuongXa.checked = false;
                this.chk_QuanHuyen.checked = false;

                if (this.tag === 'capnhat_danhba')
                    this.txt_MaKH.text = this.vma_kh;
            }
            await this.onSearch();
        },

        showModal() {
            this.$refs['dlgGanThueBaoVaoToaNha'].show();
        },

        hideModal() {
            this.$refs['dlgGanThueBaoVaoToaNha'].hide();
            this.clear();
        },

        clear() {
            this.grid_ThueBao.items = [];
            this.txt_MaTB.text = '';
            this.txt_TenTB.text = '';
            this.txt_MaKH.text = '';
            this.chk_QuanHuyen.checked = false;
            this.chk_PhuongXa.checked = false;
            //cboQuan.Text = "";
            //cboToaNha.Text = "";
        },

        async onSearch() {
            let dt = [];
            if (this.tag === "tracuu_danhba" || this.tag === "capnhat_danhba") {
                let maTB = '0';
                let maKH = this.txt_MaKH.text !== '' ? this.txt_MaKH.text : '0';
                dt = await this.getDsThueBaoToaNhaDB(maTB, maKH);
                if (dt.length <= 0) {
                    this.clear();
                    return this.$toast.error('Không có dữ liệu, vui lòng thử lại!');
                }
                if (dt[0]['1'] === 1) {
                    this.clear();
                    return this.$toast.error('Không có thông tin khách hàng! Vui lòng kiểm tra lại!');
                }
            } else if (this.tag === 'tracuu_hopdong')
                dt = await this.getDsThueBaoToaNhaHD(this.Ma_GD);
            else 
                dt = await this.getDsThueBaoToaNhaHD(this.Ma_GD);
            this.grid_ThueBao.items = [...dt];
        },

        async onSave() {
            if (this.KiemTra())
                await this.CapNhat(1);
        },

        async onDelete() {
            if (this.KiemTra()) {
                await this.$bvModal.msgBoxConfirm('Bạn chắc chắn muốn xóa tòa nhà này?', {
                "title": 'Thông báo',
                "size": 'lg',
                "buttonSize": 'lg',
                "okVariant": 'primary',
                "okTitle": 'Đồng ý',
                "cancelTitle": 'Không đồng ý',
                "hideHeaderClose": false,
                "centered": true,
                "modal-class": ["f18","text-center"],
                })
                .then(async(value) => {
                    if (value){
                        await this.CapNhat(0);
                    }
                })
                .catch(err => {
                    console.log(err);
                })
            }
        },

        toaNhaSelected(args) {
            if (args)
                this.cbo_ToaNha.selected = args.toanha_id;
        },

        selectRow(toaNhaId) {
            let idx = toaNhaId ? this.cbo_ToaNha.items.findIndex(e => e.toanha_id === toaNhaId) : 0;
            this.$refs.toaNhaCbo.$refs.grid.setCurrentSelectedRow(idx !== -1 ? idx : 0);
        },

        async onThueBaoSelected(args) {
            if (args) {
                this.grid_ThueBao.selected = args.data;
                this.txt_MaKH.text = args.data.ma_kh;
                this.txt_MaTB.text = args.data.ma_tb;
                this.txt_TenTB.text = args.data.ten_tb;

                if (this.tag === "tracuu_danhba" || this.tag === "capnhat_danhba" || this.tag === "tracuu_hopdong") {
                    let toaNhaId = args.data.toanha_id;
                    if (toaNhaId == null || toanha_id === '') {
                        //cboQuan.Text = "";
                        //cboToaNha.Text = "";
                        this.chk_PhuongXa.checked = false;                        
                        return;
                    }

                    let dt = await this.getTTQuanPhuongTheoToaNha(toaNhaId);
                    if (dt.length > 0) {
                        let quanId = dt[0].quan_id;
                        if (quanId != null) {
                            this.chk_QuanHuyen.checked = true;
                            this.cbo_QuanHuyen.selected = quanId;
                        }
                        let phuongId = dt[0].phuong_id;
                        if (phuongId != null) {
                            this.chk_PhuongXa.checked = true;
                            this.cbo_PhuongXa.selected = phuongId;
                        }
                    }
                    this.selectRow(toaNhaId);
                } else {
                    this.hdtb_id = args.data.hdtb_id;
                    let dt = await this.getTTQuanPhuongTheoHDTB(this.hdtb_id);
                    if (dt.length > 0) {
                        let quanId = dt[0].quan_id;
                        if (quanId != null && this.chk_QuanHuyen.checked)
                            this.cbo_QuanHuyen.selected = quanId;
                        
                        let phuongId = dt[0].phuong_id;
                        if (phuongId != null && this.chk_PhuongXa.checked)
                            this.cbo_PhuongXa.selected = phuongId;
                    }
                    if (args.data.toanha_id != null || args.data.toanha_id != '')
                        this.selectRow(args.data.toanha_id);
                }
            }
        },

        async HT_ToaNha() {
            let quanId = this.chk_QuanHuyen.checked && this.cbo_QuanHuyen.selected != null ? this.cbo_QuanHuyen.selected : '';
            let phuongId = this.chk_PhuongXa.checked && this.cbo_PhuongXa.selected != null ? this.cbo_PhuongXa.selected : '';
            await this.getDsToaNhaTheoQuanPhuong(quanId, phuongId);
        },

        KiemTra() {
            if (this.grid_ThueBao.items.length <= 0) {
                this.$toast.error('Chưa có dữ liệu để thực hiện!');
                return false;
            }
            if (this.cbo_ToaNha.selected == null) {
                this.$toast.error('Không có thông tin tòa nhà!');
                return false;
            }
            if (this.tag === '' && this.hdtb_id === 0) {
                this.$toast.error('Không có thông tin hợp đồng thuê bao!');
                return false;
            }
            return true;
        },

        async CapNhat(loai) {
            let kieu = 0;
            let hdtbId = 0;
            let thueBaoId = 0;
            let toaNhaId = loai === 0 ? 0 : this.cbo_ToaNha.selected;
            let dichVuVTId = this.grid_ThueBao.selected.dichvuvt_id;
            let dauCuoiId = this.grid_ThueBao.selected.daucuoi_id;

            if (this.tag === 'capnhat_danhba') {
                kieu = 2; //Cập nhật Danh bạ con: 1. db_cd, 4. db_adsl, 8. db_mgwan, 9. db_tsl, 11. db_ims
                thueBaoId = this.grid_ThueBao.selected.thuebao_id;
            } else {
                kieu = 1; //Cập nhật Hợp đồng con: 1. hdtb_cd, 4. hdtb_adsl, 8. hdtb_mgwan, 9. hdtb_tsl, 11. hdtb_ims
                hdtbId = this.grid_ThueBao.selected.hdtb_id;
            }

            let rs = await this.getKiemTraQuyenToaNha(kieu, toaNhaId, kieu === 1 ? hdtbId : thueBaoId);
            if (!rs.startsWith('OK'))
                return this.$toast.error(rs);
            let rs1 = await this.updateThueBaoToaNha(kieu, dichVuVTId, hdtbId, thueBaoId, toaNhaId, dauCuoiId);
            if (rs1) {
                if (loai === 1)
                    this.$toast.success(`Cập nhật dữ liệu thành công!`);
                else this.$toast.success(`Xóa dữ liệu thành công!`);
            } else this.$toast.error(`Có lỗi trong quá trình cập nhật: thuebao_id: ${thueBaoId}, dichvuvt_id: ${dichVuVTId}`);
            
            await this.onSearch();
        },

        async getDsQuan() {
            try {
                this.loading(true);
                this.cbo_QuanHuyen.items = [];
                let url = `/web-toanha/thuebao-toanha/ds-quan`;
                let response = await this.$root.context.get(url);
                if (response.error_code === 'BSS-00000000' && response.data !== undefined) {
                    response.data.forEach(item => {
                        this.cbo_QuanHuyen.items.push({ id: item.quan_id, text: item.ten_quan });
                    });
                }
            }
            catch (error) {
                console.log(error);
            }
            finally {
                this.loading(false);
            }
        },

        async getDsPhuong(quanId) {
            try {
                this.loading(true);
                this.cbo_PhuongXa.items = [];
                let url = `/web-toanha/thuebao-toanha/ds-phuong?quanId=${quanId}`;
                let response = await this.$root.context.get(url);
                if (response.error_code === 'BSS-00000000' && response.data !== undefined) {
                    response.data.forEach(item => {
                        this.cbo_PhuongXa.items.push({ id: item.phuong_id, text: item.ten_phuong });
                    });
                }
            }
            catch (error) {
                console.log(error);
            }
            finally {
                this.loading(false);
            }
        },

        async getDsThueBaoToaNhaDB(maTB, maKH) {
            try {
                this.loading(true);
                let url = `/web-toanha/thuebao-toanha/ds-thuebao-toanha-db?maTB=${maTB}&maKH=${maKH}`;
                let response = await this.$root.context.get(url);
                if (response.error_code === 'BSS-00000000' && response.data !== undefined) {
                    return response.data;
                } return []
            }
            catch (error) {
                console.log(error);
                return [];
            }
            finally {
                this.loading(false);
            }
        },

        async getDsThueBaoToaNhaHD(maGD) {
            try {
                this.loading(true);
                let url = `/web-toanha/thuebao-toanha/ds-thuebao-toanha-hd?maGD=${maGD}`;
                let response = await this.$root.context.get(url);
                if (response.error_code === 'BSS-00000000' && response.data !== undefined) {
                    return response.data;
                } return []
            }
            catch (error) {
                console.log(error);
                return [];
            }
            finally {
                this.loading(false);
            }
        },

        async getTTQuanPhuongTheoToaNha(toaNhaId) {
            try {
                this.loading(true);
                let url = `/web-toanha/thuebao-toanha/tt-quanphuong-theo-toanha?toaNhaId=${toaNhaId}`;
                let response = await this.$root.context.get(url);
                if (response.error_code === 'BSS-00000000' && response.data !== undefined) {
                    return response.data;
                } return []
            }
            catch (error) {
                console.log(error);
                return [];
            }
            finally {
                this.loading(false);
            }
        },

        async getTTQuanPhuongTheoHDTB(hdtbId) {
            try {
                this.loading(true);
                let url = `/web-toanha/thuebao-toanha/tt-quanphuong-theo-hdtb?hdtbId=${hdtbId}`;
                let response = await this.$root.context.get(url);
                if (response.error_code === 'BSS-00000000' && response.data !== undefined) {
                    return response.data;
                } return []
            }
            catch (error) {
                console.log(error);
                return [];
            }
            finally {
                this.loading(false);
            }
        },

        async getDsToaNhaTheoQuanPhuong(quanId, phuongId) {
            try {
                this.loading(true);
                this.cbo_ToaNha.items = [];
                let url = `/web-toanha/thuebao-toanha/ds-toanha-theo-quanphuong?quanId=${quanId}&phuongId=${phuongId}`;
                let response = await this.$root.context.get(url);
                if (response.error_code === 'BSS-00000000' && response.data !== undefined) {
                    this.cbo_ToaNha.items = response.data;
                }
            }
            catch (error) {
                console.log(error);
            }
            finally {
                this.loading(false);
            }
        },

        async getKiemTraQuyenToaNha(kieu, id, ttvtId) {
            try {
                this.loading(true);
                let url = `/web-toanha/danhmuc-hopdong-dautu/kiemtra-quyen-toanha?chucNang=TB_TOANHA&kieu=${kieu}&id=${id}&ttvtId=${ttvtId}`;
                let response = await this.$root.context.get(url);
                if (response.error_code === 'BSS-00000000' && response.data != undefined) {
                    return response.data;
                } else {
                    return 'Có lỗi xảy ra khi gọi api kiểm tra quyền tòa nhà';
                }
            }
            catch (error) {
                console.log(error);
                return 'Có lỗi xảy ra khi gọi api kiểm tra quyền tòa nhà';
            }
            finally {
                this.loading(false);
            }
        },

        async updateThueBaoToaNha(kieu, dichVuVtId, hdtbId, thueBaoId, toaNhaId, dauCuoiId) {
            try {
                this.loading(true);
                let apiBody = {
                    kieu: kieu,
                    dichVuVtId: dichVuVtId,
                    hdtbId: hdtbId,
                    thueBaoId: thueBaoId,
                    toaNhaId: toaNhaId,
                    dauCuoiId: dauCuoiId
                };
                let url = `/web-toanha/thuebao-toanha/capnhat-thuebao-toanha`;
                let response = await this.$root.context.post(url, apiBody);
                if (response.error_code === 'BSS-00000000' && response.data != undefined) {
                    return response.data.startsWith('OK');
                } else return false;
            }
            catch (error) {
                console.log(error);
                return false;
            }
            finally {
                this.loading(false);
            }
        }
    },

    watch: {
        'chk_QuanHuyen.checked': async function(newVal, oldVal) {
            if (newVal)
                this.chk_PhuongXa.enabled = true;
            else {
                this.chk_PhuongXa.enabled = false;
                this.chk_PhuongXa.checked = false;
            }
            await this.HT_ToaNha();
        },

        'chk_PhuongXa.checked': async function(newVal, oldVal) {
            await this.HT_ToaNha();
        },

        'cbo_QuanHuyen.selected': async function(newVal, oldVal) {
            if (newVal) {
                await this.getDsPhuong(newVal);
                if (this.cbo_PhuongXa.items.length > 0)
                    this.cbo_PhuongXa.selected = this.cbo_PhuongXa.items[0].id;
            }
            if (this.chk_QuanHuyen.checked)
                await this.HT_ToaNha();
        },

        'cbo_PhuongXa.selected': async function(newVal, oldVal) {
            if (newVal && this.chk_PhuongXa.checked) {
                await this.HT_ToaNha();
            }
        },
    }
}
</script>
