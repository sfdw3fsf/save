<template>
    <div>
    <breadcrumb :header="header" />
    <vue-nav>
        <div class="d-flex flex-row align-items-center">
            <ul class="list">
                <li v-for="action in actions" v-show="action.visible" :key="action.id"
                    @click="onClickAction(action.id)">
                    <a style="cursor: pointer;" :class="action.enable ? '' : 'disabled'"> <span class="icon"
                            :class="action.icon"></span> {{ action.name }} </a>
                </li>
            </ul>
        </div>
    </vue-nav>

    <div class="page-content">

        <div class="nav tab-over tabs">
            <a href="#tab1" data-toggle="tab" style="min-width: 200px !important;" class=" minw0 active" @click="setTabActive('tab1')" >Đăng ký
                nghỉ</a>
            <a href="#tab2" data-toggle="tab" style="min-width: 200px !important;" class="minw0" @click="setTabActive('tab2')" >Đăng ký chỉ
                tiêu ĐH</a>
        </div>
        <div class="tab-content">
            <div class="tab-pane active fullh" id="tab1">
                <DangKyNghi ref='refDangKyNghiCom' :isNew="isNew" @isNew-change="handleNewChange"/>
            </div>
            <div class="tab-pane  fullh" id="tab2">
                <DangKyChiTieu ref="refDangKyChiTieuCom" :isNew="isNew"  @isNew-change="handleNewChange"/>
            </div>
        </div>

    </div>
</div>
</template>

<script>
import breadcrumb from '@/components/breadcrumb'
import DatePicker from 'vue2-datepicker'
import moment from 'moment'
import DangKyChiTieu from './DangKyChiTieu.vue';
import DangKyNghi from './DangKyNghi.vue';

// import Utils from './utils'
import _ from 'lodash'
export default {
    name: 'DangKyChiTieuDonHang',
    components: { 
        breadcrumb , 
        DatePicker,
        DangKyChiTieu,
        DangKyNghi
    },
    data() {
        return {
            header: {
                title: 'Đăng ký chỉ tiêu',
                list: [
                    { name: 'Đăng ký chỉ tiêu', link: { name: 'Ui.cards' } },
                    { name: 'Đăng ký chỉ tiêu', link: { name: 'Ui.buttons' } }
                ]
            },
            actions: [
                { id: 'btnNew', icon: 'icon one-file-plus', name: 'Nhập mới', visible: true, enable: false },
                { id: 'btnSave', icon: 'icon one-save', name: 'Cập nhật', visible: true, enable: true },
                { id: 'btnDelete', icon: 'icon one-trash', name: 'Xoá ', visible: true, enable: true },
            ],
            tabActive: 'tab1',
            isNew: true,
            

           

        }
    },
    computed: {
        phanVungId() {
            return +this.$root.token.getPhanVungID()
        },
        donViId() {
            return +this.$root.token.getDonViID()
        },
        nhanVienId() {
            return +this.$root.token.getNhanVienID()
        },



    },
    async created() {

    },
    async mounted() {
       

    },
    methods: {


        async onClickAction(id) {
            switch (id) {
 
                case 'btnNew':
                    await this.btnNew_Click()
                    break
                case 'btnSave':
                    await this.btnSave_Click()
                    break
                case 'btnDelete':
                    await this.btnDelete_Click()
                    break
                default:
                    console.log("click Actions")
                    break
            }
        },
        

        btnNew_Click() {
            this.isNew = true;
            // tab DK nghi
            if(this.tabActive == 'tab1'){
                this.$refs.refDangKyNghiCom.clear()
            }

            //Tab DK Chi tieu
            if(this.tabActive == 'tab2'){
                this.$refs.refDangKyChiTieuCom.clear()
            }
            
            
        },

        async btnDelete_Click() {
            // tab DK nghi
            if(this.tabActive == 'tab1') await this.$refs.refDangKyNghiCom.deleteClick()
            
               //Tab DK Chi tieu
            if(this.tabActive == 'tab2') await this.$refs.refDangKyChiTieuCom.deleteClick() 
         
        },
        async  btnSave_Click() {
            // tab DK nghi
            if(this.tabActive == 'tab1')  await this.$refs.refDangKyNghiCom.saveClick()  
            
            //Tab DK Chi tieu
            if(this.tabActive == 'tab2')  await this.$refs.refDangKyChiTieuCom.saveClick()
        },

        setTabActive(tab) {
            this.tabActive = tab
           

        }, 

        onSetButtonEnable(id, enable) {
            this.actions.find(item => item.id === id).enable = enable
        },


        onSetButtonVisible(id, isVisible) {
            this.actions.find(item => item.id === id).visible = isVisible
        },
        handleNewChange(val) {
            this.isNew = val
        },
    },



    watch: {
        isNew(val) {
            this.onSetButtonEnable('btnNew', !val)
        }

    }
}

</script>