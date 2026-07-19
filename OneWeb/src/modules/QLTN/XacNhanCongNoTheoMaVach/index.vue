<template src="./index.template.html"></template>
<style scoped src="./style.scss"></style>
<script>
import breadcrumb from "@/components/breadcrumb"
import API from '../api/CongNoAPI.js'
import CommonAPI from '../api/Commons.js'
import { mapActions, mapGetters } from "vuex"
import { required } from "vuelidate/lib/validators"
import EventBus from '@/utils/eventBus'
import moment from 'moment'

export default {
    components: { breadcrumb },

    async created() {
        EventBus.$on('onPhieuCheckboxChanged', this.onPhieuCheckboxChangedHandler);
        await this.initForm();
    },

    destroyed () {
        EventBus.$off('onPhieuCheckboxChanged', this.onPhieuCheckboxChangedHandler);
    },

    mounted() {
        this.$refs.maTT.focus();
    },

    computed: {
        ...mapGetters('qltnCommon', [
            'danhSachLanTaoCongNoGetter',
            'danhSachLyDoXacNhanGetter',
            'danhSachNguyenNhanXacNhanGetter'
        ])
    },

    data() {
        return {
            params: {
                maTT: '',
                soDu: 0,
                soDu_ReadOnly: true,
                today: new Date(),
                ngayGui: new Date(),
                ngayXTKH: new Date(),
                ngayTHT: new Date(),
                ngayTHT_Enabled: false,
                lanTao_Options: [],
                lanTao_Selected: null,
                ketQua_Options: [
                    { id: null, text: 'Chọn kết quả'},
                    { id: 0, text: 'Không xác nhận được'},
                    { id: 1, text: 'Xác nhận được - số dư đúng'},
                    { id: 2, text: 'Xác nhận được - số dư sai'}
                ],
                ketQua_Selected: null,
                lyDo_Enabled: false,
                lyDo_Options: [],
                lyDo_Selected: null,
                nguyenNhan_Enabled: false,
                nguyenNhan_Options: [],
                nguyenNhan_Selected: null,
                updateBtn_Enabled: false,
                soLuongTao: ''
            },
            message_detail: '',
            congNo_grid: {
                items: [],
                checked: [],
                key: 1,
                pageInfo: {
                    page: 0,
                    maxSize: 10,
                    totalElement: 0,
                    totalPages: 0,
                    currentRow: 0,
                    sort: null,
                    propertiesSort: null
                }
            },
            checkboxColumnTemplate: function () {
                return {
                template: {
                    template: `<div class="check-action" style="vertical-align: middle;">
                                <input type="checkbox" class="check" @change="onChanged" v-model="data.CHON"/>
                                <span class="name"></span>
                            </div>`,
                    data() { return { data: {}}},
                    methods: {
                        onChanged() {
                            EventBus.$emit('onPhieuCheckboxChanged', this.data);
                        }
                    }
            }}},
        }
    },

    validations: {
        params: {
            ketQua_Selected: {
                required
            }
        }
    },

    methods: {
        ...mapActions('qltnCommon', [
            'getDanhSachLanTaoCongNo',
            'getDanhSachLyDoXacNhan',
            'getDanhSachNguyenNhanXacNhan'
        ]),

        async initForm() {
            await this.getDanhSachLanTaoCongNo();
            if (this.danhSachLanTaoCongNoGetter != null && this.danhSachLanTaoCongNoGetter.length > 0) {
                this.danhSachLanTaoCongNoGetter.forEach(item => {
                    this.params.lanTao_Options.push({ id: item.LANTAO_ID, text: item.KYHOADON });
                });
                this.params.lanTao_Selected = this.params.lanTao_Options[0].id;
            }

            if (this.danhSachLyDoXacNhanGetter == null || this.danhSachLyDoXacNhanGetter.length === 0)
                await this.getDanhSachLyDoXacNhan();
            if (this.danhSachLyDoXacNhanGetter.length > 0) {
                this.danhSachLyDoXacNhanGetter.forEach(item => {
                    this.params.lyDo_Options.push({ id: item.LYDOKXN_ID, text: item.LYDO_KXN });
                });
                this.params.lyDo_Selected = this.params.lyDo_Options[0].id;
            }

            if (this.danhSachNguyenNhanXacNhanGetter == null || this.danhSachNguyenNhanXacNhanGetter.length === 0)
                await this.getDanhSachNguyenNhanXacNhan();
            if (this.danhSachNguyenNhanXacNhanGetter.length > 0) {
                this.danhSachNguyenNhanXacNhanGetter.forEach(item => {
                    this.params.nguyenNhan_Options.push({ id: item.NGUYENNHANXN_ID, text: item.NGUYENNHAN_XN });
                });
                this.params.nguyenNhan_Selected = this.params.nguyenNhan_Options[0].id;
            }
        },

        onCongNoSelected(args) {
            this.params.soDu = args.data.TONGNO - Math.abs(args.data.NOAM);
            this.params.ngayXTKH = args.data.NGAY_BN != null && args.data.NGAY_BN !== '' ? moment(item.data.NGAY_BN, 'DD/MM/YYYY').toDate() : this.params.today;
            this.params.ngayGui = args.data.NGAY_GUI != null && args.data.NGAY_GUI !== '' ? moment(item.data.NGAY_GUI, 'DD/MM/YYYY').toDate() : this.params.today;
        },

        onPhieuCheckboxChangedHandler(args) {
            let index = this.congNo_grid.items.findIndex(e => e.CONGNO_ID === args.CONGNO_ID);
            if (index !== -1) {
                //CHECKED
                if (args.CHON) {
                    this.congNo_grid.items[index].CHON = true;
                    this.congNo_grid.checked.push(args.CONGNO_ID);
                } else {
                //UN-CHECKED
                    this.congNo_grid.items[index].CHON = false;
                    let indexChecked = this.congNo_grid.checked.findIndex(e => e === args.CONGNO_ID);
                        if (indexChecked !== -1)
                        this.congNo_grid.checked.splice(indexChecked, 1);
                }
            }
        },

        async onCNGridPageChange(e) {
            this.congNo_grid.pageInfo.page = e.pageIndex;
            this.congNo_grid.pageInfo.maxSize = e.pageSize;
            await this.getDanhSachCongNoTheoMaVach(this.params.maTT.trim());
        },
        
        /* type: 1 - button; 2: keypress*/
        async onSearch(type) {
            this.$v.$reset(); //Reset validator
            this.resetPaging();
            this.params.maTT = this.params.maTT.trim();
            if (type === 2 && this.params.maTT === '')
                return this.$toast.error('Vui lòng nhập mã vạch để tìm kiếm!');
            
            await this.getDanhSachCongNoTheoMaVach(this.params.maTT);
            if (this.congNo_grid.items.length <= 0) {
                if (this.message_detail != '') {
                    this.$toast.error(this.message_detail);
                    this.message_detail = '';
                } else this.$toast.error('Không có dữ liệu, vui lòng thử lại!');
                this.params.soDu = 0;
                this.params.updateBtn_Enabled = false;
            } else {
                this.params.updateBtn_Enabled = true;
                this.params.soLuongTao = this.congNo_grid.items.length;
            }
        },

        onValidate() {
            this.$v.$touch();
            let errorMessage = [];
            if (!this.$v.params.ketQua_Selected.required)
                errorMessage.push('Vui lòng chọn Kết quả xác nhận công nợ!');
            if (this.params.ngayGui === null)
                errorMessage.push('Ngày gửi không đúng định dạng!');
            if (this.params.ngayXTKH === null)
                errorMessage.push('Ngày xác thực KH không đúng định dạng!');
            else 
                this.params.ngayXTKH.setHours(0, 0, 0, 0);
            if (this.params.ngayTHT === null)
                errorMessage.push('Ngày thu hồi thư không đúng định dạng!');
            else
                this.params.ngayTHT.setHours(0, 0, 0, 0);
                                    
            if (this.params.ngayXTKH && this.params.ngayTHT && this.params.ngayXTKH.getTime() > this.params.ngayTHT.getTime())
                errorMessage.push('Ngày thu hồi thư không thể nhỏ hơn ngày xác thực KH!');

            if (errorMessage.length > 0) {
                this.$toast.error(errorMessage.join("\r\n"));
                return false;
            } else return true;
        },

        async onUpdate() {
            if (this.congNo_grid.items.length <= 0)
                return this.$toast.error('Chưa có dữ liệu trên lưới để thực hiện!');
            
            if (this.congNo_grid.checked.length <= 0)
                return this.$toast.error('Bạn chưa chọn dữ liệu trên lưới để cập nhật!');
            
            if (this.onValidate()) {
                try {                
                    await this.$bvModal.msgBoxConfirm('Bạn thật sự muốn cập nhật dữ liệu không?', {
                    "title": 'Thông báo',
                    "size": 'sm',
                    "buttonSize": 'md',
                    "okVariant": 'primary',
                    "okTitle": 'Đồng ý',
                    "cancelTitle": 'Hủy bỏ',
                    "hideHeaderClose": false,
                    "centered": true,
                    "modal-class": ["f18","text-center"],
                    })
                    .then(async(value) => {
                        if (value){
                            let isError = false;
                            for (let item of this.congNo_grid.checked) {
                                let result = await this.updateXacNhanKetQuaCongNoTheoId(item);
                                if (!result) {
                                    isError = true;
                                    break;
                                }
                            }

                            if (!isError)
                                this.$toast.success("Xác nhận công nợ thành công!");
                            await this.getDanhSachCongNoTheoMaVach('');
                            this.params.soDu = 0;
                            this.params.ketQua_Selected = null;
                            this.$v.$reset(); //Reset validator
                            this.params.maTT = '';
                        }
                    })
                    .catch(err => {
                        // An error occurred
                        console.log(err);
                    })
                } catch (error) {
                    this.$toast.error("Có lỗi xảy ra, vui lòng thử lại!")
                }
            }
        },

        resetPaging() {
            this.congNo_grid.items = [];
            this.congNo_grid.pageInfo = {
                page: 0,
                maxSize: 10,
                totalElement: 0,
                totalPages: 0,
                currentRow: 0,
                sort: null,
                propertiesSort: null
            }
        },

        async getDanhSachCongNoTheoMaVach(maTT) {
            try {
                this.loading(true);
                this.congNo_grid.key++;
                this.params.soLuongTao = '';
                this.congNo_grid.checked = [];
                let apiParams = {                    
                    lanTaoId: this.params.lanTao_Selected,
                    maVach: maTT,
                    pageNum: maTT !== '' ? null : this.congNo_grid.pageInfo.page + 1,
                    pageSize: maTT !== '' ? null : this.congNo_grid.pageInfo.maxSize,
                    totalRow: maTT !== '' ? null : 1
                };
                let response = await API.getDanhSachCongNoTheoMaVach(this.axios, apiParams);
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {
                    if (maTT !== '') {
                        this.congNo_grid.items = response.data.data;
                        this.congNo_grid.pageInfo = {
                            page: response.data.data.length > 0 ? 1 : 0,
                            totalElement: response.data.data.length,
                            totalPages: response.data.data.length / this.congNo_grid.pageInfo.maxSize
                        };
                    } else {
                        this.congNo_grid.items = response.data.data.data;
                        this.congNo_grid.pageInfo = {
                            page : response.data.data.page,
                            maxSize: response.data.data.maxSize,
                            totalElement: response.data.data.totalElement,
                            totalPages: response.data.data.totalPages,
                            sort: response.data.data.sort,
                            propertiesSort: response.data.data.propertiesSort,
                            currentRow: 0
                        };
                    }
                } else {                    
                    this.resetPaging();                
                }
            }
            catch (error) {
                this.resetPaging();
                this.message_detail = error.data.message_detail;
            }
            finally {
                this.loading(false);
            }
        },

        async updateXacNhanKetQuaCongNoTheoId(congNoId) {
            try {
                this.loading(true);                
                let apiBody = {
                    congNoId: congNoId,
                    ketQua: this.params.ketQua_Selected,
                    lyDoKXN: this.params.lyDo_Selected,
                    ngayBN: moment(this.params.ngayXTKH).format('DD/MM/YYYY'),
                    ngayGui: moment(this.params.ngayGui).format('DD/MM/YYYY'),
                    ngayHT: moment(this.params.ngayTHT).format('DD/MM/YYYY'),
                    nguyenNhanXNId: this.params.nguyenNhan_Selected,
                    soDu: this.params.soDu
                };
                let response = await API.updateXacNhanKetQuaCongNoTheoId(this.axios, apiBody);
                if (response.data.error_code === 'BSS-00000000') {
                    return true;
                } else return false;
            }
            catch (error) {
                this.$toast.error(error.data.message_detail);
                return false;
            }
            finally {
                this.loading(false);
            }
        }
    },

    watch: {
        'params.ketQua_Selected': async function(newVal, oldVal) {
            if (newVal !== null) {
                if (newVal === 0) {
                    this.params.lyDo_Enabled = true;
                    this.params.nguyenNhan_Enabled = false;
                    this.params.ngayTHT_Enabled = true;
                    this.params.soDu_ReadOnly = true;
                } else if (newVal === 1) {
                    this.params.lyDo_Enabled = false;
                    this.params.nguyenNhan_Enabled = false;
                    this.params.ngayTHT_Enabled = true;
                    this.params.soDu_ReadOnly = true;
                } else { //=2
                    this.params.lyDo_Enabled = false;
                    this.params.nguyenNhan_Enabled = true;
                    this.params.ngayTHT_Enabled = true;
                    this.params.soDu_ReadOnly = false;
                }
            } else { //Load form
                this.params.lyDo_Enabled = false;
                this.params.nguyenNhan_Enabled = false;
                this.params.ngayTHT_Enabled = false;
                this.params.soDu_ReadOnly = true;
            }
        },
    }
}
</script>
