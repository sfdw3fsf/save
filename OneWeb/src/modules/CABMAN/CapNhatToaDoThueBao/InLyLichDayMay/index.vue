<template src="./index.html">
</template>

<script>
import report from '../report';

export default {
    components: {
        report
    },
    props: {
        modalShow: Boolean,
        thongTinTBTraCuu: {},
        thongTinGianDo: [],
        thongTinBaoHong: [],
        doRenderPDF: false,
        showPanelReport: false,
        loaiTimKiem: '',
        soMay: String
    },
    methods: {
        closeModal: function() {
            this.$emit('closeModal');
        },
        traCuu: function() {
            this.$emit('traCuu', $("#inputThueBao").val(), $("input[name='radioDBorHD']:checked").val());
        }
    }
}
</script>
<style>
.no-position{
    position: unset !important;
}
.vue-html2pdf .pdf-preview {
  position: unset !important;
  width: 100% !important;
  transform: unset !important;
}
.vue-html2pdf .pdf-preview button {
    display: none !important;
}
.pdf-content {
  width: 100%;
  background: #fff;
  font-size: 13px;
  line-height: 185%;
}
.pdf-content .report-info {
  margin-top: 15px;
  width: 100%;
  display: block;
}
.pdf-content .rp-row {
  width: 100%;
  display: flex;
}
.pdf-content .rp-col {
  width: 50%;
  display: flex;
}
.pdf-content .report-header .rp-title {
  flex-grow: 1;
  text-align: center;
  font-weight: bold;
  font-size: 18px;
  padding: 10px 0;
}
.pdf-content .report-header .rp-somay,
.pdf-content .report-header .rp-tongdai {
  width: 33%;
  border: 1px solid #9e9e9e;
  padding: 10px;
  display: flex;
  flex-flow: column;
}
.pdf-content .report-header .rp-somay .label,
.pdf-content .report-header .rp-tongdai .label {
  width: auto;
  flex-grow: 1;
  font-weight: normal;
}
.pdf-content .report-info .info-header {
  font-weight: bold;
  text-decoration: underline;
}
.pdf-content .rp-row .label {
  white-space: nowrap;
}
.pdf-content .rp-row .value {
  padding-left: 5px;
}
.pdf-content .rp-row .label {
  width: 115px;
  flex: none;
  font-weight: bold;
}
.pdf-content .rp-cus .label {
  width: auto;
}
.pdf-content .text-right {
  text-align: right;
}
.pdf-content .text-center {
  text-align: center;
}
.pdf-content .text-bold {
  font-weight: bold;
}
.pdf-content .rp-table {
  width: 100%;
}
.pdf-content .rp-table th,
.pdf-content .rp-table td {
  padding: 5px;
  line-height: 140%;
  border-right: 1px solid #999;
}
.pdf-content .rp-table tr th:first-child,
.pdf-content .rp-table tr td:first-child {
  border-left: 1px solid #999;
}
.pdf-content .rp-table th {
  text-align: center;
}
.pdf-content .rp-table tr{
  page-break-inside:avoid;
  border-bottom: 1px solid #999;
}
.pdf-content .rp-table tr:first-child{
  border-top: 1px solid #999;
}
</style>

