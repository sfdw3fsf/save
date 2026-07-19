<template>
    <div :key="getKey()">
        <div v-if="element.type == 'select'" class="info-row">
            <div class="key" :style="{ width: element.labelWidth + 'px' }">
                {{ element.label }}
            </div>
            <div class="value">
                <Select2 :disabled="element.disable !== undefined ? element.disable : false" :value="vvalue" @change="(e) => changeInput(e)" :options="(element.options && danhmuc[element.options]) ? (danhmuc[element.options].map((item) => ({
                    id: item[element.valueField], text: item[element.textField]
                }))) : []" />
            </div>
        </div>
        <div v-if="element.type == 'input'" class="info-row">
            <div class="key" :style="{ width: element.labelWidth + 'px' }">
                {{ element.label }}
            </div>
            <div class="value">
                <input :value="vvalue" :disabled="element.disable !== undefined ? element.disable : false" @change="(e) => changeInput(e.target.value)"
                    type="text" class="form-control">
            </div>
        </div>
        <div v-if="element.type == 'textarea'" class="info-row">
            <div class="key" :style="{ width: element.labelWidth + 'px' }">
                {{ element.label }}
            </div>
            <div class="value">
                <textarea @change="(e) => changeInput(e.target.value)" type="text"
                    class="form-control">{{ vvalue }}</textarea>
            </div>
        </div>
        <div v-if="element.type == 'date'" class="info-row">
            <div class="key" :style="{ width: element.labelWidth + 'px' }">
                {{ element.label }}
            </div>
            <div class="value">
                <vue-date @input="(e) => changeInput(e)" :value="vvalue" :type="'date'"
                    :format="'DD/MM/YYYY'">
                </vue-date>
            </div>
        </div>
    </div>
</template>
<script>
import moment from 'moment'
export default {
    props: {
        vvalue: {
            type: [String, Number, Date],
            default: ""
        },
        element: {
            type: Object,
            default: () => ({})
        },
        formData: {
            type: Object,
            default: () => ({})
        },
        danhmuc: {
            type: Object,
            default: () => ({})
        }
    },
    watch: {
        vvalue: {
            handler: function (value) {
                console.log(value)
            },
            immediate: true
        }
    },
    mounted() {
        this.$emit('mounted')
    },
    methods: {
        getKey() {
            return (new Date()).getTime() + Math.floor(Math.random() * 10000).toString()
        },
        changeInput(value) {
            this.$emit('change', value)
        }
    }
}
</script>