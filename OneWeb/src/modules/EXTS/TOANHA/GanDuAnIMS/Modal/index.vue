<template src="./index.template.html"></template>
<style scoped src="./style.scss"></style>
<script>
import ComboboxGrid from "@/components/Controls/ComboboxGrid"
export default {
    name:'PopupGanDuAnIMS',

    components: { appCombobox: ComboboxGrid },

    props: {
        vtoanha_id: {
            type: Number,
            default: 0
        }
    },

    data() {
        return {
            cbo_ToaNha: {
                items: [],
                selected: null
            },
            cbo_DuAnIMS: {
                items: [],
                selected: null
            },
            txt_MaDuAn: null,
            txt_NamDuAn: null,
            txt_ChiPhiDuKien: 0,
            txt_ChiPhiVAT: 0,
            txt_SoHieu: null,
            txt_BanQuanLy: null,
            txt_DiaChi: null,
            txt_MoTa: null,
            grid_DuAn: {
                items: [],
                selected: {},
                key: 1
            },
            toanha_ims_id: 0
        }
    },

    methods: {
        hideComboboxTableHeader() {
            this.$nextTick(function () {
                let elemts = this.$refs.duAnIMSCbo.$refs.grid.$el.querySelectorAll('.e-headercell');
                elemts.forEach(elment => {
                    elment.style.display = 'none';
                });
            })
        },

        async showModal() {
            await this.getDsToaNha();
            this.cbo_ToaNha.selected = this.vtoanha_id;
            await this.getProjectByInvestmentField();
            await this.showData();
            this.$refs['dlgGiaoDuAnIMS'].show();
            this.hideComboboxTableHeader();
        },

        hideModal() {
            this.$refs['dlgGiaoDuAnIMS'].hide();
            this.grid_DuAn.items = [];
        },

        onValidate() {
            if (this.grid_DuAn.items.length > 0) {
                let ds = this.grid_DuAn.items.filter(e => e.duan_id === this.cbo_DuAnIMS.selected);
                if (ds.length > 0) {
                    this.$toast.error('Dự án IMS đã tồn tại!');
                    return false;
                }
            }
            if (this.cbo_DuAnIMS.selected == null) {
                this.$toast.error('Bạn hãy chọn dự án IMS cần gán vào tòa nhà!');
                return false;
            }
            return true;
        },

        async onAdd() {
            if (this.onValidate()) {
                this.toanha_ims_id = await this.addToaNhaIMS();
                if (this.toanha_ims_id !== 0) {
                    this.$toast.success('Thêm mới dữ liệu thành công!');
                    await this.showData();
                } else this.$toast.error('Thêm mới dữ liệu không thành công!');
            }
        },

        async onDelete() {
            try {
                if (this.grid_DuAn.items.length === 0)
                    return this.$toast.error('Chưa có dữ liệu để xóa!');
                await this.$bvModal.msgBoxConfirm('Bạn có chắc chắn muốn xóa?', {
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
                        let rs = await this.deleteToaNhaIMS(this.toanha_ims_id);
                        if (rs) {
                            this.$toast.success('Xóa dữ liệu thành công!');
                            await this.showData();
                        } else this.$toast.error('Xóa dữ liệu không thành công!');
                    }
                })
                .catch(err => {
                    console.log(err);
                })
            } catch (error) {
                console.log(error);
                this.$toast.error("Có lỗi xảy ra, vui lòng thử lại!")
            }
        },

        duAnIMSSelected(args) {
            if (args != null)
                this.cbo_DuAnIMS.selected = args.ProjectID;
        },

        async showData() {
            if (this.vtoanha_id === 0)
                return this.$toast.error('Không có thông tin tòa nhà');
            else {
                await this.getDsToaNhaIMS(this.vtoanha_id);
                if (this.grid_DuAn.items.length <= 0) {
                    this.txt_MaDuAn = '';
                    this.txt_NamDuAn = null;
                    this.txt_ChiPhiDuKien = 0;
                    this.txt_ChiPhiVAT = 0;
                    this.txt_SoHieu = '';
                    this.txt_BanQuanLy = '';
                    this.txt_MoTa = '';
                    this.txt_DiaChi = '';
                }
            }
        },

        onDuAnSelected(args) {
            if (args != null) {
                this.toanha_ims_id = args.data.toanha_ims_id;
                this.cbo_DuAnIMS.selected = args.data.duan_id;
                this.selectCboRow();
            }
        },

        selectCboRow() {
            let idx = this.cbo_DuAnIMS.items.findIndex(e => e.ProjectID === this.cbo_DuAnIMS.selected);
            if (idx !== -1) 
                this.$refs.duAnIMSCbo.$refs.grid.setCurrentSelectedRow(idx);
        },

        async getProjectByInvestmentField() {
            try {
                this.loading(true);
                this.cbo_DuAnIMS.items = [];
                let url = `/tichhop/qldt/get_ProjectbyInvestmentField`;
                let response = await this.$root.context.get(url);
                if (response.errorCode === 0 && response.data !== undefined) {
                    this.cbo_DuAnIMS.items = response.data;
                }
            }
            catch (error) {
                console.log(error);
            }
            finally {
                this.loading(false);
            }
        },

        async getDsToaNha() {
            try {
                this.loading(true);
                this.cbo_ToaNha.items = [];
                let url = `/web-toanha/gan-duan-ims/danhsach-toanha`;
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

        async getDsToaNhaIMS(toaNhaId) {
            try {
                this.loading(true);
                this.grid_DuAn.items = [];
                this.grid_DuAn.key++;
                let url = `/web-toanha/gan-duan-ims/danhsach-toanha-ims?toaNhaId=${toaNhaId}`;
                let response = await this.$root.context.get(url);
                if (response.error_code === 'BSS-00000000' && response.data !== undefined) {
                    this.grid_DuAn.items = response.data;
                }
            }
            catch (error) {
                console.log(error);
            }
            finally {
                this.loading(false);
            }
        },

        async addToaNhaIMS() {
            try {
                this.loading(true);
                let url = `/web-toanha/gan-duan-ims/them-toanha-ims`;
                let duAnIMS = this.cbo_DuAnIMS.items.find(e => e.ProjectID === this.cbo_DuAnIMS.selected);
                let tenDuAn = duAnIMS != null ? duAnIMS.ProjectName : '';
                let apiBody = {
                    toaNhaId: this.cbo_ToaNha.selected,
                    duAnId: this.cbo_DuAnIMS.selected,
                    maDuAn: this.txt_MaDuAn,
                    tenDuAn: tenDuAn,
                    namDuAn: this.txt_NamDuAn,
                    moTa: this.txt_MoTa,
                    soHieu: this.txt_SoHieu,
                    tien: this.txt_ChiPhiDuKien,
                    vat: this.txt_ChiPhiVAT,
                    banQuanLy: this.txt_BanQuanLy,
                    diaChi: this.txt_DiaChi
                };
                let response = await this.$root.context.post(url, apiBody);
                if (response.error_code === 'BSS-00000000' && response.data.startsWith('OK')) {
                    let arr = response.data.split('-');
                    return arr.length > 0 ? arr[1] : 0;
                } else return 0;
            }
            catch (error) {
                console.log(error);
                return 0;
            }
            finally {
                this.loading(false);
            }
        },

        async deleteToaNhaIMS(toaNhaId) {
            try {
                this.loading(true);
                let url = `/web-toanha/gan-duan-ims/xoa-toanha-ims/${toaNhaId}`;
                let response = await this.$root.context.post(url);
                if (response.error_code === 'BSS-00000000' && response.data !== undefined) {
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
        'cbo_DuAnIMS.selected': async function(newVal, oldVal) {
            if (newVal) {
                let duAn = this.cbo_DuAnIMS.items.find(e => e.ProjectID === newVal);
                if (duAn != null) {
                    this.txt_MaDuAn = duAn.ProjectCode;
                    this.txt_NamDuAn = duAn.ProjectYear;
                    this.txt_ChiPhiDuKien = duAn.AccumulatePlanVND ? duAn.AccumulatePlanVND : 0;
                    this.txt_ChiPhiVAT = duAn.AccumulatePlanVATVND ? duAn.AccumulatePlanVATVND : 0;
                    this.txt_SoHieu = duAn.ProjectTaskNumber;
                    this.txt_BanQuanLy = duAn.DepartmentName;
                    this.txt_DiaChi = duAn.ProjectLocation;
                    this.txt_MoTa = duAn.ProjectCapability;
                }
            }
        }
    }
}
</script>
