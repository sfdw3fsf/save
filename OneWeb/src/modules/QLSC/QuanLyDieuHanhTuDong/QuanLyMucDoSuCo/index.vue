<template src='./index.html'>
</template>

<script>
import Vue from 'vue'
import Color from './color'
Vue.directive("click-outside", {
  bind(el, binding, vnode) {
    el.clickOutsideEvent = (event) => {
      if (!(el === event.target || el.contains(event.target))) {
        vnode.context[binding.expression](event);
      }
    };
    document.body.addEventListener("click", el.clickOutsideEvent);
  },
  unbind(el) {
    document.body.removeEventListener("click", el.clickOutsideEvent);
  },
});
let mucDoTemplate = function() {
    return {
        template: {
            template: `
                    <div class="tb-content" :style="{ background: '#' + this.data.MAUNEN, color: '#' + this.data.MAUCHU }">{{this.data.MUCDO_SC}}</div> 
                `,
            data() {
                return {
                    data: {
                        
                    }
                }
            },
            computed: {
                parent() {
                    return this.$parent.$parent;
                },
                value() {
                    return `${this.data}`
                }
            }
        },
    }
}

let canhBaoDTTemplate = function() {
    return {
        template: {
            template: `
                    <div class="tb-content" :style="{ background: '#' + this.data.MAUNEN, color: '#' + this.data.MAUCHU }">{{this.data.CANHBAO_DT}}</div> 
                `,
            data() {
                return {
                    data: {
                        
                    }
                }
            },
            computed: {
                parent() {
                    return this.$parent.$parent;
                },
                value() {
                    return `${this.data}`
                }
            }
        },
    }
}

let mauNenTemplate = function() {
    return {
        template: {
            template: `
                    <div class="tb-content" :style="{ background: '#' + this.data.MAUNEN, color: '#' + this.data.MAUCHU }">{{this.data.MAUNEN}}</div> 
                `,
            data() {
                return {
                    data: {
                        
                    }
                }
            },
            computed: {
                parent() {
                    return this.$parent.$parent;
                },
                value() {
                    return `${this.data}`
                }
            }
        },
    }
}

let mauChuTemplate = function() {
    return {
        template: {
            template: `
                    <div class="tb-content" :style="{ background: '#' + this.data.MAUNEN, color: '#' + this.data.MAUCHU }">{{this.data.MAUCHU}}</div> 
                `,
            data() {
                return {
                    data: {
                        
                    }
                }
            },
            computed: {
                parent() {
                    return this.$parent.$parent;
                },
                value() {
                    return `${this.data}`
                }
            }
        },
    }
}
export default {
    data() {
        return {
            dsMucDoSuCo: [],
            mucdo_id: null,
            mucdosuco: '',
            canhbaodt: null,
            mdsc: {},
            mauchu: {
                name: Color[0].name,
                value: Color[0].value
            },
            maunen: {
                name: Color[0].name,
                value: Color[0].value
            },
            mota: '',
            dsColor: Color,
            isDisableBtnNhapMoi: false,
            isDisableBtnGhiLai: true,
            isDisableBtnHuy: true,
            isDisableBtnXoa: true,
            isShowMauChu: false,
            isShowMauNen: false,
            
            columns: [
                {fieldName: 'MUCDO_SC', headerText: 'Mức độ sự cố', allowFiltering: true, allowSorting: false, template: mucDoTemplate, cssClass: 'p-relative' },
                {fieldName: 'CANHBAO_DT', headerText: 'Cảnh báo doanh thu', allowFiltering: true, allowSorting: false, template: canhBaoDTTemplate, cssClass: 'p-relative'},
                {fieldName: 'MAUNEN', headerText: 'Màu nền', allowFiltering: true, allowSorting: false, template: mauNenTemplate, cssClass: 'p-relative'},
                {fieldName: 'MAUCHU', headerText: 'Màu chữ', allowFiltering: true, allowSorting: false, template: mauChuTemplate, cssClass: 'p-relative'},
            ],

        }
    },
    methods: {
        setDisableBtn: function (isNhapmoi, isGhilai, isHuy, isXoa) {
            this.isDisableBtnNhapMoi = isNhapmoi;
            this.isDisableBtnGhiLai = isGhilai;
            this.isDisableBtnHuy = isHuy;
            this.isDisableBtnXoa = isXoa;
        },
        clickTab: function(data) {
            this.$emit('clickTab', data);
        },
        layDsMucDoSuCo: async function() {
            try {
                var rsMDSC = await this.$root.context.get('/web-suco/danhmuc/dieuhanhtudong/ds-mucdo-sc');
                this.dsMucDoSuCo = rsMDSC.data;
            } catch (error) {
                
            }
        },
        gridMDSC_selectedRowChanged: function(data) {
            if(data) {
                this.mdsc = data;
                this.mucdo_id = data.MUCDO_ID;
                this.mucdosuco = data.MUCDO_SC;
                let mc = this.dsColor.filter(v => v.value.substr(1) ==  data.MAUCHU);
                this.mauchu = mc.length > 0 ? mc[0] : [];
                let mn = this.dsColor.filter(v => v.value.substr(1) ==  data.MAUNEN);
                this.maunen = mn.length > 0 ? mn[0] : [];
                this.mota = data.MOTA;
                this.canhbaodt = data.CANHBAO_DT;
            }
            this.setDisableBtn(false, false, false, false)
        },
        lamMoi: async function() {
            try {
                this.loading(true);
                await this.layDsMucDoSuCo();
            } catch (error) {
                
            } finally {
                this.loading(false);
            }
        },
        nhapMoi: function() {
            if(this.isDisableBtnNhapMoi) {
                return;
            }
            this.mucdo_id = null;
            this.mucdosuco = null;
            this.mauchu = Color[0];
            this.maunen = Color[0];
            this.mota = '';
            this.canhbaodt = null;
            this.setDisableBtn(true, false, false, false)
        },
        checkForm: function() {
            let check = true;
            let error = '';

            if(this.mota && this.mota.length > 50) {
                check = false;
                error = 'Mô tả không được quá 50 ký tự !\n'.concat(error);
            }

            if(this.canhbaodt == null || this.canhbaodt === '') {
                check = false;
                error = 'Cảnh báo doanh thu không được để trống !\n'.concat(error);
            }
            else if(this.canhbaodt.length > 10){
                check = false;
                error = 'Cảnh báo doanh thu không được quá 10 ký tự !\n'.concat(error);
            }

            if(!this.mucdosuco) {
                check = false;
                error = 'Mức độ sự cố không được để trống !'
            }
            else if(this.mucdosuco.length > 50) {
                check = false;
                error = 'Mức độ sự cố không được quá 50 ký tự !\n'.concat(error);
            }
            
            

            if(!check) {
                this.$toast.error(error);
            }
            return check;
        },
        ghiLai: async function() {
            if(this.checkForm()){
                if(this.mucdo_id) {
                    try {
                        this.loading(true);
                        var rs = await this.$root.context.post(`/web-suco/danhmuc/dieuhanhtudong/capnhat-mucdo-sc/${this.mucdo_id}`, {
                            mucdo_sc: this.mucdosuco,
                            canhbao_dt: this.canhbaodt,
                            mota: this.mota,
                            maunen: this.maunen.value.substr(1),
                            mauchu: this.mauchu.value.substr(1)
                        });
                        if(rs.data.KETQUA) {
                            this.layDsMucDoSuCo();
                            this.$toast.success('Cập nhật thành công');
                        }
                    } catch (error) {
                        this.$toast.error(error.response.data.message_detail ? error.response.data.message_detail : 'Cập nhật thất bại');
                    } finally {
                        this.loading(false);
                    }
                }
                else {
                    try {
                        this.loading(true);
                        var rs = await this.$root.context.post('/web-suco/danhmuc/dieuhanhtudong/them-mucdo-sc', {
                            mucdo_sc: this.mucdosuco,
                            canhbao_dt: this.canhbaodt,
                            mota: this.mota,
                            maunen: this.maunen.value.substr(1),
                            mauchu: this.mauchu.value.substr(1)
                        });
                        if(rs.data) {
                            await this.layDsMucDoSuCo();
                            if(this.dsMucDoSuCo.length > 0) {
                                this.$refs.grid.setCurrentSelectedRow(0);
                            }
                            this.$toast.success('Thêm thành công');
                        }
                    } catch (error) {
                        this.$toast.error(error.response.data.message_detail ? error.response.data.message_detail : 'Thêm thất bại');
                    } finally {
                        this.loading(false);
                    }
                }
            }
        },
        huyBo: function() {
            if(this.mucdo_id == null) {
                this.mucdo_id = null;
                this.mucdosuco = null;
                this.mauchu = Color[0];
                this.maunen = Color[0];
                this.mota = '';
                this.canhbaodt = null;
            }
            else {
                this.gridMDSC_selectedRowChanged(this.mdsc);
            }
            this.setDisableBtn(false, false, false, false);
        },
        xoa: async function() {
            if(this.mucdo_id) {
                this.$bvModal.msgBoxConfirm('Bạn có chắc chắn xóa mức độ sự cố này không ?', {
                    size: 'sm',
                    okTitle: 'Đồng ý',
                    cancelTitle: 'Hủy',
                }).then(async v => {
                    if(v) {
                        try {
                            this.loading(true);
                            await this.$root.context.post(`/web-suco/danhmuc/dieuhanhtudong/xoa-mucdo-sc/${this.mucdo_id}`);
                            this.dsMucDoSuCo = this.dsMucDoSuCo.filter(val => val.MUCDO_ID != this.mucdo_id);
                            this.$toast.success('Xóa thành công');
                        } catch (error) {
                            this.$toast.error(error.response.data.message_detail ? error.response.data.message_detail : 'Xóa thất bại');
                        } finally {
                            this.loading(false);
                        }
                    }
                }).catch(err => {
                    this.$toast.error('Xóa thất bại');
                })
                
            }
        },
        getColorMauChu: function(color) {
            this.mauchu = color;
            this.isShowMauChu = false;
        },
        getColorMauNen: function(color) {
            this.maunen = color;
            this.isShowMauNen = false;
        },
        onClickOutsideMauChu: function() {
            this.isShowMauChu = false;
        },
        onClickOutsideMauNen: function() {
            this.isShowMauNen = false;
        }
    },
    async mounted() {
        try {
            this.loading(true);
            await this.layDsMucDoSuCo();
            $('.bg').css("background-color", "yellow");
        } catch (error) {
            this.$toast.error('Xảy ra lỗi');
        } finally {
            this.loading(false);
        }
    }
}
</script>

<style>
.list-color {
    overflow: scroll;
    max-height: 160px;
}
.list-color a {
    padding-left: 5px;
    padding-right: 5px;
}
a.disable-btn-qlmdsc {
  pointer-events: none;
  cursor: default;
  color: #6c757d !important;
}
.tb-content {
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    padding: 5px 7px;
}
.p-relative {
    position: relative;
}
.show-dropmenu {
    position: absolute; 
    will-change: transform; 
    top: 0px; left: 0px; 
    transform: translate3d(-1px, -68px, 0px);
    display: block;
}
</style>
