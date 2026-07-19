<template>
<b-card :header="header" header-tag="header" :header-class="'p-1 ' + headerClass" :body-class="'p-2 ' + bodyClass" :class="'endpoint mb-2 ' + cls">
	<template slot="header">
		<slot name="header"></slot>
	</template>
	<!-- <div :class="{'disabled':isDisabled}"> -->
  <div >
	<b-row>
		<b-col>
			<b-form-group label="Số ảo" label-cols="1" label-class="lblw-80">
				<b-form-input v-model="soAo" :readonly="true" v-b-tooltip.hover :title="soAo"></b-form-input>
			</b-form-group>
		</b-col>
		<b-col>
			<b-form-group label="Thiết bị" label-cols="1" label-class="lblw-80">
				<b-form-select :options="DS_THIETBI" v-model="thietBiId"
				value-field="thietbidc_id"
				text-field="ten_tbi"
        :disabled="isDisabled"
				>
				</b-form-select>
			</b-form-group>
		</b-col>
		<b-col>
			<b-form-group label="Serial" label-cols="1" label-class="lblw-80">
				<b-form-input v-model="$v.serial.$model"
				:class="{'is-invalid': $v.serial.$error}"
								ref="serial"
                :readonly="isDisabled" v-b-tooltip.hover :title="soAo"
				 ></b-form-input>
			</b-form-group>
		</b-col>
		<b-col>
			<b-form-group label="Loại cáp" label-cols="1" label-class="lblw-80">
				<b-form-select :options="DS_LOAICAP" v-model="loaiCapId"
				value-field="LOAICAP_ID"
				text-field="LOAI_CAP"
				:disabled="true"
				>
				</b-form-select>
			</b-form-group>
		</b-col>
	</b-row>
	<b-row>
		<b-col>
			<b-form-group label="Svlan" label-cols="1" label-class="lblw-80">
				<b-form-input v-model="sVlan"
        :readonly="true" v-b-tooltip.hover :title="sVlan"
				></b-form-input>
			</b-form-group>
		</b-col>
		<b-col>
			<b-form-group label="Cvlan" label-cols="1" label-class="lblw-80">
				<b-form-input v-model="cvLan"
        :readonly="true" v-b-tooltip.hover :title="cvLan"
				></b-form-input>
			</b-form-group>
		</b-col>
		<b-col>
			<b-form-group label="Cáp gốc" label-cols="1" label-class="lblw-80">
				<b-form-input v-model="$v.capGoc.$model"
				:class="{'is-invalid': $v.capGoc.$error}"
								ref="capGoc"
        :readonly="isDisabled" v-b-tooltip.hover :title="$v.capGoc.$model"
				></b-form-input>
			</b-form-group>
		</b-col>
		<b-col>
			<b-form-group label="Cáp ngọn" label-cols="1" label-class="lblw-80">
				<b-form-input v-model="$v.capNgon.$model"
				:class="{'is-invalid': $v.capNgon.$error}"
								ref="capNgon"
          :readonly="isDisabled" v-b-tooltip.hover :title="$v.capNgon.$model"
				></b-form-input>
			</b-form-group>
		</b-col>
	</b-row>
	<b-row>
		<b-col>
			<b-form-group label="Adsl cáp" label-cols="1" label-class="lblw-80">
				<b-form-input v-model="adslCap"  :readonly="true" v-b-tooltip.hover :title="adslCap"></b-form-input>
			</b-form-group>
		</b-col>
		<b-col>
			<b-form-group label="Adsl port" label-cols="1" label-class="lblw-80">
				<b-form-input v-model="adslPort" :readonly="true" v-b-tooltip.hover :title="adslPort"></b-form-input>
			</b-form-group>
		</b-col>
		<b-col>
			<b-form-group label="Tủ cáp gốc" label-cols="1" label-class="lblw-80">
				<b-form-input v-model="$v.tuCapGoc.$model"
				:class="{'is-invalid': $v.tuCapGoc.$error}"
								ref="tuCapGoc"
          :readonly="isDisabled" v-b-tooltip.hover :title="$v.tuCapGoc.model"
				></b-form-input>
			</b-form-group>
		</b-col>
		<b-col>
			<b-form-group label="Tủ cáp ngọn" label-cols="1" label-class="lblw-80">
				<b-form-input v-model="$v.tuCapNgon.$model"
				:class="{'is-invalid': $v.tuCapNgon.$error}"
								ref="tuCapNgon"
        :readonly="isDisabled" v-b-tooltip.hover :title="$v.tuCapNgon.$model"
				></b-form-input>
			</b-form-group>
		</b-col>
	</b-row>
	<b-row>
		<b-col>
			<b-form-group label="Nasport ID" label-cols="1" label-class="lblw-80">
				<b-form-input v-model="nasPortId" :readonly="true" v-b-tooltip.hover :title="nasPortId"></b-form-input>
			</b-form-group>
		</b-col>
		<b-col>
			<b-form-group label="Vrf/Vfi" label-cols="1" label-class="lblw-80">
				<b-form-input v-model="$v.vrfVfiId.$model"
				:class="{'is-invalid': $v.vrfVfiId.$error}"
								ref="vrfVfiId"
          :readonly="isDisabled" v-b-tooltip.hover :title="$v.vrfVfiId.$model"
				></b-form-input>
			</b-form-group>
		</b-col>
		<b-col>
			<b-form-group label="Đôi cáp gốc" label-cols="1" label-class="lblw-80">
				<b-form-input
				v-model="$v.doiCapGoc.$model"
				:class="{'is-invalid': $v.doiCapGoc.$error}"
								ref="doiCapGoc"
          :readonly="isDisabled" v-b-tooltip.hover :title="$v.doiCapGoc.$model"
				></b-form-input>
			</b-form-group>
		</b-col>
		<b-col>
			<b-form-group label="Đôi cáp ngọn" label-cols="1" label-class="lblw-80">
				<b-form-input v-model="$v.doiCapNgon.$model"
				:class="{'is-invalid': $v.doiCapNgon.$error}"
								ref="doiCapNgon"
          :readonly="isDisabled" v-b-tooltip.hover :title="$v.doiCapNgon.$model"
				></b-form-input>
			</b-form-group>
		</b-col>
	</b-row>

<b-row>
		<b-col>
			<b-form-group label="TB t.dẫn" label-cols="1" label-class="lblw-80">
				<b-form-input v-model="$v.tbiTruyenDan.$model"
				:class="{'is-invalid': $v.tbiTruyenDan.$error}"
								ref="tbiTruyenDan"
          :readonly="isDisabled" v-b-tooltip.hover :title="$v.tbiTruyenDan.$model"
				></b-form-input>
			</b-form-group>
		</b-col>
		<b-col>
			<b-form-group  label="IP Wan" label-cols="1" label-class="lblw-80">
				<b-form-input v-b-tooltip.hover :title="$v.ipWan.$model" v-model="$v.ipWan.$model" :readonly="isDisabled"
				:class="{'is-invalid': $v.ipWan.$error}"
								ref="ipWan"
          
				></b-form-input>
			</b-form-group>
		</b-col>
		<b-col>
			<b-form-group label="Liên tủ" label-cols="1" label-class="lblw-80">
				<b-form-input v-model="$v.lienTu.$model"
					:class="{'is-invalid': $v.lienTu.$error}"
								ref="lienTu"
            :readonly="isDisabled" v-b-tooltip.hover :title="$v.lienTu.$model"
				></b-form-input>
			</b-form-group>
		</b-col>
		<b-col>
			<b-form-group label="Hộp cáp ngọn" label-cols="1" label-class="lblw-80">
				<b-form-input v-model="$v.hopCapNgon.$model"
					:class="{'is-invalid': $v.hopCapNgon.$error}"
								ref="hopCapNgon"
          :readonly="isDisabled" v-b-tooltip.hover :title="$v.hopCapNgon.$model"
				></b-form-input>
			</b-form-group>
		</b-col>
	</b-row>

	<b-row>
		<b-col>
			<b-form-group label="TimeSlot" label-cols="1" label-class="lblw-80">
				<b-form-input v-model="$v.timeSlot.$model"
					:class="{'is-invalid': $v.timeSlot.$error}"
								ref="timeSlot"
          :readonly="isDisabled" v-b-tooltip.hover :title="$v.timeSlot.$model"
				></b-form-input>
			</b-form-group>
		</b-col>
		<b-col>
			<b-form-group label="Link" label-cols="1" label-class="lblw-80">
				<b-form-input v-model="$v.link.$model"
					:class="{'is-invalid': $v.link.$error}"
								ref="link"
          :readonly="isDisabled" v-b-tooltip.hover :title="$v.link.$model"
				></b-form-input>
			</b-form-group>
		</b-col>
		<b-col>
			<b-form-group label="Cự ly" label-cols="1" label-class="lblw-80">
				<b-form-input v-model="$v.cuLy.$model"
					:class="{'is-invalid': $v.cuLy.$error}"
								ref="cuLy"
          :readonly="isDisabled" v-b-tooltip.hover :title="$v.cuLy.$model"
				></b-form-input>
			</b-form-group>
		</b-col>
		<b-col>
			<b-form-group label="Tuyến cáp" label-cols="1" label-class="lblw-80">
				<b-form-input v-model="$v.tuyenCap.$model"
				:class="{'is-invalid': $v.tuyenCap.$error}"
								ref="tuyenCap"
        :readonly="isDisabled" v-b-tooltip.hover :title="$v.tuyenCap.$model"
				></b-form-input>
			</b-form-group>
		</b-col>
	</b-row>
	<b-row>
		<b-col>
			<b-form-group label="Địa chỉ" label-cols="1" label-class="lblw-80">
				<b-form-input v-model="diaChi"
        :readonly="true" v-b-tooltip.hover :title="diaChi"
				></b-form-input>
			</b-form-group>
		</b-col>
		<b-col>
			<b-form-group label="Đơn vị" label-cols="1" label-class="lblw-80">
				<b-form-select :options="DS_DONVI"
				v-model="donViId"
				value-field="donvi_id"
				text-field="ten_dv"
				:disabled="true"
				>
				</b-form-select>
			</b-form-group>
		</b-col>
	</b-row>
	</div>
</b-card>
</template>

<style src='./endpoint.scss'></style>

<script>
import {endpointEvent} from "./Enum"
import { integer, minValue, helpers, maxLength } from "vuelidate/lib/validators"
const isNumberConstraint = (field) => !field || (Number.isInteger(parseInt(field)) && field >= 0)
export default {
  name: "endpoint",
  props: {
    header: { type: String, "default": "Điểm đầuu" },
    cls: { type: String, "default": "" },
    bodyClass: { type: String, "default": "" },
    headerClass: { type: String, "default": "" },

    SO_AO: { type: String, "default": "" },
    THIETBI: { type: Number, "default": 0 },
    SERIAL: { type: String, "default": "" },
    LOAI_CAP: { type: Number, "default": 0 },
    SVLAN: { type: Number, "default": 0 },
    CVLAN: { type: Number, "default": 0 },
    CAP_GOC: { type: String, "default": "" },
    CAP_NGON: { type: String, "default": "" },
    ADSL_CAP: { type: String, "default": "" },
    ADSL_PORT: { type: String, "default": "" },
    TUCAP_GOC: { type: String, "default": "" },
    TUCAP_NGON: { type: String, "default": "" },
    NASPORT_ID: { type: String, "default": "" },
    VRF_VFI: { type: String, "default": "" },
    DOICAP_GOC: { type: Number, "default": 0 },
    DOICAP_NGON: { type: Number, "default": 0 },
    THIETBI_TRUYENDAN: {type: String, "default": ""},
    IP_WAN: { type: String, "default": "" },
    LIEN_TU: { type: String, "default": "" },
    HOPCAP_NGON: { type: String, "default": "" },
    TIME_SLOT: { type: String, "default": "" },
    LINK: { type: String, "default": "" },
    CULY: { type: Number, "default": 1 },
    TUYEN_CAP: { type: String, "default": "" },
    DIACHI: { type: String, "default": "" },
    DONVI: { type: Number, "default": 0 },
    DS_THIETBI: [],
    DS_LOAICAP: [],
    DS_DONVI: [],
    isDisabled: false
  },

  created () {

  },
  data: function () {
    return {
      thietBiId: this.THIETBI,
      loaiCapId: this.LOAI_CAP,
      donViId: this.DONVI,
      portId: 0,
      vci_vpi_id: 0,
      soAo: this.SO_AO,
      serial: this.SERIAL,
      sVlan: this.SVLAN,
      cvLan: this.CVLAN,
      capGoc: this.CAP_GOC,
      capNgon: this.CAP_NGON,
      adslCap: this.ADSL_CAP,
      adslPort: this.ADSL_PORT,
      tuCapGoc: this.TUCAP_GOC,
      tuCapNgon: this.TUCAP_NGON,
      nasPortId: this.NASPORT_ID,
      vrfVfiId: this.VRF_VFI,
      doiCapGoc: this.doiCapGoc,
      doiCapNgon: this.DOICAP_NGON,
      tbiTruyenDan: this.THIETBI_TRUYENDAN,
      ipWan: this.IP_WAN,
      lienTu: this.LIEN_TU,
      hopCapNgon: this.HOPCAP_NGON,
      timeSlot: this.TIME_SLOT,
      link: this.LINK,
      cuLy: this.CULY,
      tuyenCap: this.TUYEN_CAP,
      diaChi: this.DIACHI

    }
  },
  validations: {

    // portId: 0,
    // vci_vpi_id: 0,
    // soAo: this.SO_AO,
    serial: {maxLength: maxLength(50)},
    // sVlan: this.SVLAN,
    // cvLan: this.CVLAN,
    capGoc: {maxLength: maxLength(200)},
    capNgon: {maxLength: maxLength(200)},
    adslCap: {maxLength: maxLength(10)},
    adslPort: {maxLength: maxLength(30)},
    tuCapGoc: {maxLength: maxLength(200)},
    tuCapNgon: {maxLength: maxLength(200)},
    // nasPortId: this.NASPORT_ID,
    vrfVfiId: {maxLength: maxLength(80)},
    doiCapGoc: {
      integer,
      maxLength: maxLength(12)
    },
    doiCapNgon: {
      integer,
      maxLength: maxLength(12)
    },
    // tbiTruyenDan: { integer,
    //   maxLength: maxLength(5)},
    tbiTruyenDan:{},
    ipWan: {maxLength: maxLength(100)},
    lienTu: {maxLength: maxLength(20)},
    hopCapNgon: {maxLength: maxLength(200)},
    timeSlot: {maxLength: maxLength(50)},
    link: {maxLength: maxLength(100)},
    cuLy: {integer, minValue: minValue(0), maxLength: maxLength(10)},
    tuyenCap: {maxLength: maxLength(50)}

  },
  watch: {
    thietBiId: function (oldValue, newValue) {
      this.$emit(endpointEvent.thietBiChangedEvent, this.thietBiId)
    }
  },
  methods: {
    HienThiThongSo (dsThongSo) {
      this.thietBiId = dsThongSo.thietBiId
      this.loaiCapId = dsThongSo.loaiCapId
      this.donViId = dsThongSo.donViId
      this.vci_vpi_id = dsThongSo.vci_vpi_id

      this.portId = dsThongSo.portId
      this.soAo = dsThongSo.soAo
      this.serial = dsThongSo.serial
      this.sVlan = dsThongSo.sVlan
      this.cvLan = dsThongSo.cvLan
      this.capGoc = dsThongSo.capGoc
      this.capNgon = dsThongSo.capNgon
      this.adslCap = dsThongSo.adslCap
      this.adslPort = dsThongSo.adslPort
      this.tuCapGoc = dsThongSo.tuCapGoc
      this.tuCapNgon = dsThongSo.tuCapNgon
      this.nasPortId = dsThongSo.nasPortId
      this.vrfVfiId = dsThongSo.vrfVfiId
      this.doiCapGoc = dsThongSo.doiCapGoc
      this.doiCapNgon = dsThongSo.doiCapNgon
      this.tbiTruyenDan = dsThongSo.tbiTruyenDan
      this.ipWan = dsThongSo.ipWan
      this.lienTu = dsThongSo.lienTu
      this.hopCapNgon = dsThongSo.hopCapNgon
      this.timeSlot = dsThongSo.timeSlot
      this.link = dsThongSo.link
      this.cuLy = dsThongSo.cuLy
      this.tuyenCap = dsThongSo.tuyenCap
      this.diaChi = dsThongSo.diaChi
    },
    validInput (isFocused = false, prefix_msg) {
      var dsError = []
      var vmsg = ""
      var vref = null
      this.$v.$touch()
      if (!this.$v.serial.maxLength) {
        vmsg = `Serial khong duoc qua ${this.$v.serial.$params.maxLength.max} ky tu`
        vref = this.$refs.serial
        dsError.push({msg: vmsg, ref: vref})
      }

      if (!this.$v.capGoc.maxLength) {
        vmsg = `Cap goc khong duoc qua ${this.$v.capGoc.$params.maxLength.max} ky tu`
        vref = this.$refs.capGoc
        dsError.push({msg: vmsg, ref: vref})
      }
      if (!this.$v.capNgon.maxLength) {
        vmsg = `Cap ngon khong duoc qua ${this.$v.capNgon.$params.maxLength.max} ky tu`
        vref = this.$refs.capNgon
        dsError.push({msg: vmsg, ref: vref})
      }

      if (!this.$v.tuCapGoc.maxLength) {
        vmsg = `Tủ cáp gốc khong duoc qua ${this.$v.tuCapGoc.$params.maxLength.max} ky tu`
        vref = this.$refs.tuCapGoc
        dsError.push({msg: vmsg, ref: vref})
      }

      if (!this.$v.tuCapNgon.maxLength) {
        vmsg = `Tủ cáp ngọn khong duoc qua ${this.$v.tuCapNgon.$params.maxLength.max} ky tu`
        vref = this.$refs.tuCapNgon
        dsError.push({msg: vmsg, ref: vref})
      }

      if (!this.$v.vrfVfiId.maxLength) {
        vmsg = `Vrf/Vfi khong duoc qua ${this.$v.vrfVfiId.$params.maxLength.max} ky tu`
        vref = this.$refs.vrfVfiId
        dsError.push({msg: vmsg, ref: vref})
      }

      if (!this.$v.doiCapGoc.maxLength) {
        vmsg = `"Đôi cáp gốc khong duoc qua ${this.$v.doiCapGoc.$params.maxLength.max} ky tu`
        vref = this.$refs.doiCapGoc
        dsError.push({msg: vmsg, ref: vref})
      }

      if (!this.$v.doiCapGoc.integer) {
        vmsg = "Đôi cáp gốc phải là giá trị số nguyên "
        vref = this.$refs.doiCapGoc
        dsError.push({msg: vmsg, ref: vref})
      }

      if (!this.$v.doiCapNgon.maxLength) {
        vmsg = `Đôi cáp ngọn khong duoc qua ${this.$v.doiCapNgon.$params.maxLength.max} ky tu`
        vref = this.$refs.doiCapNgon
        dsError.push({msg: vmsg, ref: vref})
      }

      if (!this.$v.doiCapNgon.integer) {
        vmsg = "Đôi cáp ngọn phải là giá trị số nguyên "
        vref = this.$refs.doiCapNgon
        dsError.push({msg: vmsg, ref: vref})
      }

      // if (!this.$v.tbiTruyenDan.maxLength) {
      //   vmsg = `Thiết bị truyền dẫn khong duoc qua ${this.$v.tbiTruyenDan.$params.maxLength.max} ky tu`
      //   vref = this.$refs.tbiTruyenDan
      //   dsError.push({msg: vmsg, ref: vref})
      // }

      // if (!this.$v.tbiTruyenDan.integer) {
      //   vmsg = "Thiết bị truyền dẫn phải là giá trị số nguyên "
      //   vref = this.$refs.tbiTruyenDan
      //   dsError.push({msg: vmsg, ref: vref})
      // }

      if (!this.$v.ipWan.maxLength) {
        vmsg = `ipWan khong duoc qua ${this.$v.ipWan.$params.maxLength.max} ky tu`
        vref = this.$refs.ipWan
        dsError.push({msg: vmsg, ref: vref})
      }
      if (!this.$v.lienTu.maxLength) {
        vmsg = `Liên tủ khong duoc qua ${this.$v.lienTu.$params.maxLength.max} ky tu`
        vref = this.$refs.lienTu
        dsError.push({msg: vmsg, ref: vref})
      }
      if (!this.$v.hopCapNgon.maxLength) {
        vmsg = `Hộp cáp ngọn khong duoc qua ${this.$v.hopCapNgon.$params.maxLength.max} ky tu`
        vref = this.$refs.hopCapNgon
        dsError.push({msg: vmsg, ref: vref})
      }
      if (!this.$v.timeSlot.maxLength) {
        vmsg = `Timeslot khong duoc qua ${this.$v.timeSlot.$params.maxLength.max} ky tu`
        vref = this.$refs.timeSlot
        dsError.push({msg: vmsg, ref: vref})
      }
      if (!this.$v.link.maxLength) {
        vmsg = `link khong duoc qua ${this.$v.link.$params.maxLength.max} ky tu`
        vref = this.$refs.link
        dsError.push({msg: vmsg, ref: vref})
      }
      if (!this.$v.cuLy.maxLength) {
        vmsg = `Cự ly khong duoc qua ${this.$v.cuLy.$params.maxLength.max} ky tu`
        vref = this.$refs.cuLy
        dsError.push({msg: vmsg, ref: vref})
      }
      if (!this.$v.cuLy.integer || !this.$v.cuLy.minValue) {
        vmsg = "Cự ly phải là số nguyên không âm"
        vref = this.$refs.cuLy
        dsError.push({msg: vmsg, ref: vref})
      }
      if (!this.$v.tuyenCap.maxLength) {
        vmsg = `Tuyến cáp khong duoc qua ${this.$v.tuyenCap.$params.maxLength.max} ky tu`
        vref = this.$refs.tuyenCap
        dsError.push({msg: vmsg, ref: vref})
      }
      if (dsError.length > 0) {
        dsError.forEach(item => {
          this.$toast.warning(prefix_msg + item.msg)
          if (item.ref && !isFocused) {
            item.ref.focus()
            isFocused = true
          }
        }
        )
        return false
      }
      return true
    }
  }
}
</script>
