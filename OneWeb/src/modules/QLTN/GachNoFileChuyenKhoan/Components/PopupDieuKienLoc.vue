<template>
    <div class="dropdown-menu fullw pad10" style="max-width: 100%;">
        <div class="legend-title">
            <div class="pull-left" style="font-weight: 600!important">Điều kiện lọc</div>
                <div class="pull-right">
                    <button class="btn btn-main pad2" @click="close">
                        <span class="-ap icon-close lh16"></span>
                    </button>
                </div>
            <div class="clearfix"></div>
        </div>
        <div class="info-row">
            <div class="key w110">
                <div class="check-action">
                    <input type="checkbox" class="check" v-model="params.tieuChi0_Checked">
                    <span class="name">Tra cứu theo</span>
                </div>
            </div>
            <div class="value">
                <div class="select-custom">
                    <select name="" id="" class="form-control" v-model="params.tieuChi0_Selected" :disabled="!params.tieuChi0_Checked">
                        <option v-for="item in value" :key="item.KIEUTK_ID" :value="item.KIEUTK_ID">{{ item.KIEU_TK }}</option>
                    </select>
                </div>
            </div>
        </div>
        <div class="info-row">
            <div class="key w110">
                <div class="check-action">
                    <input type="checkbox" class="check" v-model="params.tieuChi1_Checked" :disabled="true">
                    <span class="name">Tra cứu theo</span>
                </div>
            </div>
            <div class="value">
                <div class="select-custom">
                    <select name="" id="" class="form-control" v-model="params.tieuChi1_Selected" :disabled="!params.tieuChi1_Checked">
                        <option v-for="item in value" :key="item.KIEUTK_ID" :value="item.KIEUTK_ID">{{ item.KIEU_TK }}</option>
                    </select>
                </div>
            </div>
        </div>
        <div class="info-row">
            <div class="key w110">
                <div class="check-action">
                    <input type="checkbox" class="check" v-model="params.tieuChi2_Checked" :disabled="true">
                    <span class="name">Tra cứu theo</span>
                </div>
            </div>
            <div class="value">
                <div class="select-custom">
                    <select name="" id="" class="form-control" v-model="params.tieuChi2_Selected" :disabled="!params.tieuChi2_Checked">
                        <option v-for="item in value" :key="item.KIEUTK_ID" :value="item.KIEUTK_ID">{{ item.KIEU_TK }}</option>
                    </select>
                </div>
            </div>
        </div>
        <div class="info-row">
            <div class="key w110">
                <div class="check-action">
                    <input type="checkbox" class="check" v-model="params.tieuChi3_Checked" :disabled="true">
                    <span class="name">Tra cứu theo</span>
                </div>
            </div>
            <div class="value">
                <div class="select-custom">
                    <select name="" id="" class="form-control" v-model="params.tieuChi3_Selected" :disabled="!params.tieuChi3_Checked">
                        <option v-for="item in value" :key="item.KIEUTK_ID" :value="item.KIEUTK_ID">{{ item.KIEU_TK }}</option>
                    </select>
                </div>
            </div>
        </div>
        <div class="group-buttons -bottom tright mart10">
            <button class="btn btn-search btn-main" @click="onTimKiem">
                <span class="one-search normal f20 inline vcenter"></span> Tìm kiếm
            </button>
        </div>
    </div>
</template>
<script>
import CommonAPI from '../../api/Commons.js'
export default {
    name: 'PopupDieuKienLoc',
    props: ['value','isShow'],
    data: function () {
        return {
            params: {
                tieuChi0_Checked: true,
                tieuChi1_Checked: false,
                tieuChi2_Checked: false,
                tieuChi3_Checked: false,
                tieuChi0_Selected: null,
                tieuChi1_Selected: null,
                tieuChi2_Selected: null,
                tieuChi3_Selected: null
            },
            notEmit: false

        }
    },
    beforeDestroy() {
        window.removeEventListener('click', this.checkClickOn)
    },
    methods: {
        checkClickOn (event) {
            if (this.clickCount !== 0 && this.$el.className.includes('show') && event.target instanceof HTMLElement && !this.$el.contains(event.target)) {
                this.$emit('close')
            }
            this.clickCount++
        },
        close () {
            this.$el.classList.remove('show')
            window.removeEventListener('click', this.checkClickOn)
        },
        onTimKiem(){
            this.$emit('clickTimKiem');
        }
    },
    watch: {
        isShow (val) {
            if (val) {
                window.addEventListener('click', this.checkClickOn)
                this.$el.classList.add('show')
                this.currentValue = this.value
                this.clickCount = 0
            } else {
                this.close()
            }
        },

        params: {
            handler(val) {
                if (this.notEmit)
                    this.notEmit = false;
                else
                    this.$emit('acceptChange', val);
            },
            deep: true
        },

        value (val) {
            if (val.length > 0) {
                this.params.tieuChi0_Selected = val[0].KIEUTK_ID;
                this.params.tieuChi1_Selected = val[0].KIEUTK_ID;
                this.params.tieuChi2_Selected = val[0].KIEUTK_ID;
                this.params.tieuChi3_Selected = val[0].KIEUTK_ID;
                this.notEmit = true;
            }
        }
    }
}
</script>
