<template src="./CreateTemplateBNM.html"></template>
<style scoped src="./CreateTemplateBNM.scss"></style>
<script>
import API from './API.js'
import breadcrumb from '@/components/breadcrumb'
import moment from 'moment'
import DataGrid3 from '@/components/Controls/DataGrid3'
import Vue from 'vue'

export default {
    name: "CreateTemplateBNM",
    components: { breadcrumb, DataGrid3 },
    data() {
        return {
            header: {
                title: 'Khai báo Template BNM',
                list: [
                    { name: 'Phát triển thuê bao', link: {name: 'Ui.cards'} },
                    { name: 'Khai báo template BNM',link: {name: 'Ui.buttons'} }
                ]
            },
            dsThamSo: {
                list: [],
                header: [
                    {fieldName: 'column_name', headerText: '', allowFiltering: true}, 
                ]
            },
            dsTemplate: {
                list: []
            },
            frm: {
                tempId: null,
                loaiTacDong: "",
                loaiOnuId: null,
                hangSxId: null,
                brId: "",
                ftth: 0,
                mytv: 0,
                ims: 0,
                bsMytv: 0,
                bsIms: 0,
                active: 0,
                boQuaLoaiOnu: 0,
                template: ""
            },
            options: {
                loaiOnu: [],
                hangSx: []
            },
            noteBnm: "",
            cFtthTemplate: function () {
                return { template : Vue.component('columnTemplate',{
                    template: `<div v-if=cData class="template_checkbox">
                                <input type="checkbox" checked readonly/>
                            </div>
                            <div v-else class="template_checkbox">
                                <input type="checkbox" readonly/>
                            </div>`,
                    data: function() {
                        return {
                            data: {}
                        }
                    },
                    computed: {
                        cData: function() {
                            return Number(this.data.ftth)
                        }
                    }
                })}
            },
            cMytvTemplate: function () {
                return { template : Vue.component('columnTemplate',{
                    template: `<div v-if=cData class="template_checkbox">
                                <input type="checkbox" checked readonly/>
                            </div>
                            <div v-else class="template_checkbox">
                                <input type="checkbox" readonly/>
                            </div>`,
                    data: function() {
                        return {
                            data: {}
                        }
                    },
                    computed: {
                        cData: function() {
                            return Number(this.data.mytv)
                        }
                    }
                })}
            },
            cImsTemplate: function () {
                return { template : Vue.component('columnTemplate',{
                    template: `<div v-if=cData class="template_checkbox">
                                <input type="checkbox" checked readonly/>
                            </div>
                            <div v-else class="template_checkbox">
                                <input type="checkbox" readonly/>
                            </div>`,
                    data: function() {
                        return {
                            data: {}
                        }
                    },
                    computed: {
                        cData: function() {
                            return Number(this.data.ims)
                        }
                    }
                })}
            },
            cBoSungTemplate: function () {
                return { template : Vue.component('columnTemplate',{
                    template: `<div v-if=cData class="template_checkbox">
                                <input type="checkbox" checked readonly/>
                            </div>
                            <div v-else class="template_checkbox">
                                <input type="checkbox" readonly/>
                            </div>`,
                    data: function() {
                        return {
                            data: {}
                        }
                    },
                    computed: {
                        cData: function() {
                            return Number(this.data.bosung)
                        }
                    }
                })}
            },
            cBoSungImsTemplate: function () {
                return { template : Vue.component('columnTemplate',{
                    template: `<div v-if=cData class="template_checkbox">
                                <input type="checkbox" checked readonly/>
                            </div>
                            <div v-else class="template_checkbox">
                                <input type="checkbox" readonly/>
                            </div>`,
                    data: function() {
                        return {
                            data: {}
                        }
                    },
                    computed: {
                        cData: function() {
                            return Number(this.data.bosung_ims)
                        }
                    }
                })}
            },
            cActiveTemplate: function () {
                return { template : Vue.component('columnTemplate',{
                    template: `<div v-if=cData class="template_checkbox">
                                <input type="checkbox" checked readonly/>
                            </div>
                            <div v-else class="template_checkbox">
                                <input type="checkbox" readonly/>
                            </div>`,
                    data: function() {
                        return {
                            data: {}
                        }
                    },
                    computed: {
                        cData: function() {
                            return Number(this.data.is_active)
                        }
                    }
                })}
            },
            cLoaiTacDongTemplate: function () {
                return { template : Vue.component('columnTemplate',{
                    template: `<div style="color: blue; font-weight: bold;">{{cData}}</div>`,
                    data: function() {
                        return {
                            data: {}
                        }
                    },
                    computed: {
                        cData: function() {
                            return this.data.ten_loaitd
                        }
                    }
                })}
            },
            cLoaiOnuTemplate: function () {
                return { template : Vue.component('columnTemplate',{
                    template: `<div style="color: darkred; font-weight: bold;">{{cData}}</div>`,
                    data: function() {
                        return {
                            data: {}
                        }
                    },
                    computed: {
                        cData: function() {
                            return this.data.ten_onu
                        }
                    }
                })}
            }
        }
    },
    mounted(){
        this.loadDsThamSo()
    },
    methods: {
        layDl(){
            this.loadDsThamSo()
        },
        themMoi(){
            this.frm = {
                tempId: null,
                loaiTacDong: "",
                loaiOnuId: null,
                hangSxId: null,
                brId: "",
                ftth: 0,
                mytv: 0,
                ims: 0,
                bsMytv: 0,
                bsIms: 0,
                active: 0,
                boQuaLoaiOnu: 0,
                template: ""
            }
        },
        async capNhat(){ 
            await this.$bvModal.msgBoxConfirm('Bạn chắc chắn muốn lưu dữ liệu ?', {
                title: 'Thông báo',
                size: 'md',
                okTitle: 'Có',
                cancelTitle: 'Không',
            }).then(async v => {
                if(!v) {
                return;
                }
                let kq
                if(this.frm.tempId === null)
                    kq = await this.capNhatDuLieu('A')
                else
                    kq = await this.capNhatDuLieu('U')
                
                if (kq == "ok"){
                    this.$toast.success('Thực hiện thêm mới dữ liệu thành công')
                    this.loadDsThamSo()
                }
                else
                    this.$toast.error(kq)
            }); 
        },
        async xoa(){
            if(this.frm.tempId === null){
                this.$toast.error('Bạn chưa chọn dữ liệu cần xoá !')
                return
            }
            let kq = await this.capNhatDuLieu('D')
            if (kq == "ok"){
                this.$toast.success('Xóa dữ liệu thành công !')
                this.loadDsThamSo()
            }
            else
                this.$toast.error(kq)
        },
        async loadDsThamSo(){
            this.loading(true)
            try{
                await API.lay_ds_tonghop_thongtin_dangbang(this.axios)
                .then(response => {
                    this.loading(false)
                    if (response && response.data && response.data.error_code === 'BSS-00000000') {
                        this.dsThamSo.list = response.data.data[0]
                        this.options.loaiOnu = response.data.data[1]
                        this.options.hangSx = response.data.data[2]
                        this.noteBnm = response.data.data[3][0].note_bnm
                        this.dsTemplate.list = response.data.data[4]
                        this.dsTemplate.list.forEach(ele => {
                            ele.ngay_cn = moment(ele.ngay_cn, 'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY HH:mm:ss')
                        })                        
                    }
                })
            } catch(e){
                this.$toast.error('Có lỗi trong quá trình lấy dữ liệu ' + e)
                this.loading(false)
            }
        },
        async selectTemplateItem(item){
            if(item.data==null || !item.data)
                return

            this.loading(true)
            try{
                await API.lay_thongtin_template_theo_id(this.axios, {
                    tempId: item.data.temp_id
                })
                .then(response => {
                    this.loading(false)
                    if (response && response.data && response.data.error_code === 'BSS-00000000') {
                        let temp = response.data.data
                        this.frm.tempId = temp.temp_id
                        this.frm.loaiTacDong = temp.loai_td
                        this.frm.loaiOnuId = temp.onu_id
                        this.frm.hangSxId = temp.hangsx_id
                        this.frm.brId = temp.bnm_br_id
                        this.frm.ftth = Number(temp.ftth)
                        this.frm.mytv = Number(temp.mytv)
                        this.frm.ims = Number(temp.ims)
                        this.frm.bsMytv = Number(temp.bosung)
                        this.frm.bsIms = Number(temp.bosung_ims)
                        this.frm.active = Number(temp.is_active)
                        this.frm.boQuaLoaiOnu = Number(temp.skip_onu_type)
                        this.frm.template = temp.template

                        //dat con tro chuot dau dong
                        setTimeout(() => {
                            this.$refs.txtTemplate.selectionStart = this.$refs.txtTemplate.selectionEnd = 0
                        }, 10)
                    }
                })
            } catch(e){
                this.$toast.error('Có lỗi trong quá trình lấy dữ liệu ' + e)
                this.loading(false)
            }
        },
        async capNhatDuLieu(vtype){ // A: Inserted; U: Updated; D: Deleted
            this.loading(true)
            try{
                let params = {
                    temp_id: this.frm.tempId,
                    hangsx_id: this.frm.hangSxId,
                    template: this.frm.template,
                    bnm_br_id: this.frm.brId,
                    onu_id: this.frm.loaiOnuId,
                    ftth: this.frm.ftth?1:0,
                    mytv: this.frm.mytv?1:0,
                    ims: this.frm.ims?1:0,
                    bosung: this.frm.bsMytv?1:0,
                    bosung_ims: this.frm.bsIms?1:0,
                    skip_onu: this.frm.boQuaLoaiOnu?1:0,
                    is_active: 1, //api hien chua co param nay,
                    loai_td: this.frm.loaiTacDong,
                    type: vtype 
                }
                return await API.capnhat_template_bnm(this.axios, params)
                .then(response => {
                    this.loading(false)
                    if (response && response.data && response.data.error_code === 'BSS-00000000') {
                        let kq = response.data.data
                        if(kq.status != 'ok')
                            return kq.detail
                        else
                            return 'ok'
                    }
                })
            } catch(e){
                this.loading(false)
                return 'Có lỗi trong quá trình lấy dữ liệu ' + e
            }
        },
        rowThamSoDoubleClicked(index, data){
            this.insertSomething('[$'+data.column_name+'$]')
        },
        insertSomething: function(insert) {
            var tArea = this.$refs.txtTemplate
            // filter:
            if (0 == insert) {
                return;
            }
            if (0 == cursorPos) {
                return;
            }

            // get cursor's position:
            var startPos = tArea.selectionStart,
                endPos = tArea.selectionEnd,
                cursorPos = startPos,
                tmpStr = tArea.value

            // insert:
            this.frm.template = tmpStr.substring(0, startPos) + insert + tmpStr.substring(endPos, tmpStr.length)

            // move cursor:
            setTimeout(() => {
                cursorPos += insert.length
                tArea.selectionStart = tArea.selectionEnd = cursorPos
            }, 10)
        }
    }
}
</script>