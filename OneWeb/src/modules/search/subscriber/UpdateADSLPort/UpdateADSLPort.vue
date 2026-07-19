<template src="./UpdateADSLPort.html"></template>
<style scoped src="./UpdateADSLPort.scss"></style>

<script>
    import breadcrumb from "@/components/breadcrumb"
    import '@/assets/vendor/jquery/split'
    import api from './UpdateADSLPortAPI'
    import moment from 'moment'
    export default {
        components: {
            breadcrumb,
            api,
        },
        name: 'UpdateADSLPort',
        mounted () {
            Split(['#boxLeft', '#boxRight'], {
                sizes: [50,50],
                gutterSize: 16,
                onDragEnd: function (sizes) {
                },
            });
        },
        async created() {
            this.Loading = true;
            try {
                Promise.all([await this.getDsDonVi(), await this.getDsTramTC(), 
                             await this.getDsTramTB(), await this.getDsDslam(),
                             await this.getDsPortVciVpi()
                            ]);
                this.Loading = false;
            } catch (ex) {
                this.Loading = false;
            }
            this.Loading = false;
        },
        data() {
            return {
                adsl_port: "",
                ds_DonVi: [],
                selDonVi: 0,
                ds_TramTC: [],
                selTramTC: 0,
                ds_TramTB: [],
                selTramTB: 0,
                ds_Dslam: [],
                selDslam: 0,
                rp_id: 0,
                ds_port_vci_vpi: {
                    list: [],
                    header: [
                        { fieldName: "system", headerText: "System", allowFiltering: true, width: '120px'},
                        { fieldName: "rack", headerText: "Rack", allowFiltering: true, width: '50px'},
                        { fieldName: "shelf", headerText: "Shelf", allowFiltering: true,  width: '50px'},
                        { fieldName: "slot", headerText: "Slot", allowFiltering: true,  width: '40px'},
                        { fieldName: "port", headerText: "Port", allowFiltering: true, width: '40px'},
                        { fieldName: "vpi", headerText: "Vpi", allowFiltering: true, textAlign: 'right', customAttributes:{class: "redcolor"}, width: '40px'},
                        { fieldName: "vci", headerText: "Vci", allowFiltering: true, textAlign: 'right', width: '40px'},
                        { fieldName: "port_mdf", headerText: "Port MDF", allowFiltering: true, textAlign: 'left', customAttributes:{class: "bold"}, width: '60px'},
                        { fieldName: "frame", headerText: "Frame", allowFiltering: true, width: '50px'},
                        { fieldName: "adsl_port", headerText: "Adsl port", allowFiltering: true, textAlign: 'left', width: '60px'},
                        { fieldName: "rp_id", headerText: "RP_id", allowFiltering: true, textAlign: 'right', width: '50px'},
                        { fieldName: "port_id", headerText: "Port_id", allowFiltering: true, textAlign: 'right', width: '60px'},
                        { fieldName: "vci_vpi_id", headerText: "Vci_Vpi_id", allowFiltering: true, textAlign: 'right', width: '60px'},
                    ],
                    value: {},
                    isEnabled: true,
                    checked: [],
                },
                index: 0,
                is_update: false,
            };
        },
        methods:{
            getDsDonVi: async function () {
                let data = this.GetData(await api.get_ds_donvi(this.axios, {"loaidv_id": 3}));
                if (data && data.length > 0) {
                    this.ds_DonVi = data;
                    this.selDonVi = data[0].donvi_id;
                } else{
                    this.ds_DonVi = data;
                    this.selDonVi = 0;   
                }
            },
            DonVi_Changed: async function ()
            {
                this.Loading = true;
                await this.getDsTramTC();
                await this.getDsTramTB();
                await this.getDsDslam();
                await this.getDsPortVciVpi();
                this.Loading = false;
            },
            getDsTramTC: async function () {
                var input = {
                    "donvi_id": this.selDonVi,
                    "loaidv_id": 5,
                    "phanvung_id": this.$root.token.getPhanVungID()
                };
                let data = this.GetData(await api.get_ds_tramtc(this.axios, input));
                if (data && data.length > 0) {
                    this.ds_TramTC = data;
                    this.selTramTC = data[0].donvi_id;
                } else{
                    this.ds_TramTC = data;
                    this.selTramTC = 0;   
                }
                
            },
            TramTC_Changed: async function ()
            {
                this.Loading = true;
                await this.getDsTramTB();
                await this.getDsDslam();
                await this.getDsPortVciVpi();
                this.Loading = false;
            },
            getDsTramTB: async function () {
                var input = {
                    "p_donvi_id": this.selTramTC,
                    "p_loaidv_id": 39
                };
                let data = this.GetData(await api.get_ds_tramtb(this.axios, input));
                if (data && data.length > 0) {
                    this.ds_TramTB = data;
                    this.selTramTB = data[0].donvi_id;
                } else{
                    this.ds_TramTB = data;
                    this.selTramTB = 0;
                }
            },
            TramTB_Changed: async function ()
            {
                this.Loading = true;
                await this.getDsDslam();
                await this.getDsPortVciVpi();
                this.Loading = false;
            },
            getDsDslam: async function () {
                var input = {
                    "vdonvi_id": this.selTramTB,
                    "vthamso": 4
                };
                let data = this.GetData(await api.get_ds_dslam(this.axios, input));
                if (data && data.length > 0) {
                    this.ds_Dslam = data;
                    this.selDslam = data[0].dslam_id;
                } else{
                    this.ds_Dslam = data;
                    this.selDslam = 0;
                }
            },
            Dslam_Changed: async function ()
            {
                this.Loading = true;
                await this.getDsPortVciVpi();
                this.Loading = false;
            },
            getDsPortVciVpi: async function () {
                var input = {
                    "vdslam_id": this.selDslam
                };
                let data = this.GetData(await api.get_ds_port_vci_vpi(this.axios, input));
                if (data.length>0)
                {
                    data=data.map(x=>{
                        x.port=x.port?Number(x.port):''
                        x.rack=x.rack?Number(x.rack):''
                        x.shelf=x.shelf?Number(x.shelf):''
                        x.slot=x.slot?Number(x.slot):''
                        return x
                    })
                    console.log('getDsPortVciVpi', data)
                    if(this.is_update)
                    {
                        let dataSelect = data.filter((x) => x.rp_id == this.rp_id);
                        let dataNotSelect = data.filter((x) => x.rp_id != this.rp_id);
                        this.ds_port_vci_vpi.list = [...dataSelect, ...dataNotSelect];
                        // this.$refs.grid.setCurrentSelectedRow(this.index_update);
                        this.is_update = false;
                    }
                    else
                    {
                        this.ds_port_vci_vpi.list = data;
                        this.$refs.grid.setCurrentSelectedRow(this.index);
                    }
                }
                else
                {
                    this.ds_port_vci_vpi.list = data;
                    this.adsl_port = "";
                    this.index_update = 0;
                    this.rp_id = 0;
                }
            },
            rowClicked(i,obj)
            {
                if(!obj) { 
                    return; 
                }
            },
            gridChanged(obj)
            {
                if(!obj) { 
                    return; 
                }
                this.rp_id = obj.rp_id;
                this.adsl_port = obj.adsl_port;
            },
            btnCapNhat: async function ()
            {
                // if(this.adsl_port.trim().length > 10)
                // {
                //     this.ShowWarning("Độ dài Adsl port không vượt quá 10 ký tự");
                //     return;
                // }
                this.$bvModal.msgBoxConfirm("Bạn có chắc thực hiện đổi adsl port không", {
                    title: "Thông báo",
                    size: "m",
                    okTitle: "Đồng ý",
                    cancelTitle: "Không đồng ý",
                }).then(async v => {
                    if(!v) {
                        return;
                    }
                    this.Loading = true;
                    this.is_update = true;
                    var input = {
                        "p_adsl_port": this.adsl_port,
                        "p_rp_id": this.rp_id
                    };
                    // console.log(input);
                    let data = await api.update_adsl_port(this.axios, input);
                    if(data.data.error === 200 || data.data.error === "200")
                        this.ShowSuccess("Cập nhật adsl port thành công!");
                    else
                        this.ShowError("Cập nhật adsl port không thành công!");
                    await this.getDsPortVciVpi();
                    this.Loading = false;
                });
            },
            ShowSuccess: function (msg) {
                this.$toast.success(msg);
            },
            ShowWarning: function (msg) {
                this.$toast.warning(msg);
            },
            ShowError: function (msg) {
                this.$toast.error(msg);
            },
            GetData: function (response) {
                if (
                    response.data.error === 200 ||
                    response.data.error === "200" ||
                    response.data.error === 204 ||
                    response.data.error === "204"
                ) {
                    return response.data.data;
                } else {
                    // console.log(response.data.message);
                    this.ShowError(response.data.message);
                }
                return [];
            },
            // customiseCell: function(args) {
            //     if (args.column.fieldName === 'vpi'){
            //         args.cell.setAttribute('style', 'color: #d32f2f');
            //     }
            // }
        },
    }
</script>