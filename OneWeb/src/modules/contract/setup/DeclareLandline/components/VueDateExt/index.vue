<template>
    <div ref="vuedate">
        <vue-date class="vue-date" labelWidth="0" :disable="disabled" :value="setValue" @input="Change" :type="type ? type : 'datetime'" :format="format ? format : 'DD/MM/YYYY hh:mm A'" />
    </div>
</template>
<script>
import moment from 'moment'
export default {
    data() {
        return {
            disabled : false,
            value : "",
            dateconfig: {
                altInput: true,
                allowInput: true
            },
        }
    },
    mounted() {
        this.disabled = this.disable
    },
    props: ['format','type', 'disable', 'nullable'],
    computed: {
        setValue() {
            if(this.value)
                return moment(this.value, this.format ? this.format : "DD/MM/YYYY hh:mm A").format(this.format ? this.format : "DD/MM/YYYY hh:mm A")
            else {
                if(!this.nullable)
                    return moment().format(this.format ? this.format : "DD/MM/YYYY hh:mm A")
                else 
                    return ""
            }
        }
    },
    watch: {
        value(value) {
            this.$emit('change', value)
        }
    },
    methods: {
        formatDate(value_, format = "DD/MM/YYYY HH:mm:ss", origin = "DD/MM/YYYY HH:mm:ss") {
            if (!value_.includes("/")) {
                return moment(value_, origin).format(format);
            } else {
                if(value_ && (value_.includes("AM") || value_.includes("PM"))) {
                    let date = value_.replace(" AM", "");
                    date = date.replace(" PM", "");
                    date = date + ":00"
                    return date
                } else {
                    return value_
                }
            }
        },
        Change(value) {
            if(value && value.includes("/")) {
                this.value = this.formatDate(value, "DD/MM/YYYY HH:mm:ss", "DD/MM/YYYY hh:mm A")
            } else {
                this.value = value
            }
            console.log("Change", this.value)
            this.$emit('change', value)
        },
        focus() {
            this.$refs.vuedate.focus()
        }
    }
}
</script>
<style>
    .vue-date .value {
        padding: 0 !important;
    }
</style>
