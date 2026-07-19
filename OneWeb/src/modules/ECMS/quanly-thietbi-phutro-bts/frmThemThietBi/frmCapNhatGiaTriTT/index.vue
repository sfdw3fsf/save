<template src="./index.template.html"></template>
<script>
export default {
    data() {
        return {
            TenThuocTinh: null,
            ChapNhan: false,
            lstThuocTinh: [],
            GiaTri: null,
            ThuocTinhId: null,
            Type: 0,
            grid_Value: {
                DataSource: []
            },
            resolvePromise: null,
        }
    },
    methods: {
        show() {
            this.$refs['ref-modal'].show()
            return new Promise((resolve, reject) => {
                this.resolvePromise = resolve
            })
        },
        hide() {
            this.resolvePromise({
                ChapNhan: this.ChapNhan, 
                GiaTri: this.GiaTri
            })
            this.$refs['ref-modal'].hide()
        },
        onModalHidden() {
            this.TenThuocTinh = null
            this.ChapNhan = false
            this.lstThuocTinh = []
            this.GiaTri = null
            this.ThuocTinhId = null
            this.Type = 0
            this.grid_Value.DataSource = []
            this.resolvePromise = null
        },
        async onModalShown() {
            
        },
        async frmCapNhatGiaTriTT_Load() {

            if (!this.TenThuocTinh) {
                this.TenThuocTinh = 'Giá trị'
            }

            await this.LoadGiaTri()
        },
        async LoadGiaTri() {

            this.lstThuocTinh = []
            let arrGiaTri = []
            let count = 0;

            if (!!this.GiaTri)
            {
                arrGiaTri = this.GiaTri.split('|');
                count = arrGiaTri.length;
            }
            else
            {
                arrGiaTri = []
                count = 1;
            }

            let i = 0;

            let dt = await this.lay_ds_thuoctinh_theo_ttid({
                type: this.Type,
                thuoctinh_id: this.ThuocTinhId
            });

            dt.forEach(ndr => {

                let vttt = {
                    ThuocTinh: ndr["THUOCTINH"]
                };
                
                if (!this.GiaTri) {
                    vttt.GiaTri = null
                } else {
                    if (i < count) {
                        vttt.GiaTri = arrGiaTri[i];
                    } else {
                        vttt.GiaTri = null
                    }
                }

                this.lstThuocTinh.push(vttt);

                i++;
            })
            
            this.grid_Value.DataSource = [...this.lstThuocTinh]
        },
        async lay_ds_thuoctinh_theo_ttid(data) {
            try {
                let response = await this.$root.context.post(`/web-ecms/them_thietbi/lay_ds_thuoctinh_theo_ttid`, data)
                return response.data
            } catch (err) {
                return []
            }
        },
        view_Value_CustomRowCellEdit(parent) {
            return function() {
                return { 
                    template: {
                        template: `
                            <input type="text" class="form-control" v-model="value">
                        `,
                        data() {
                            return { 
                                parent: parent,
                                value: null,
                                data: { }
                            }
                        },
                        watch: {
                            value(newVal) {
                                let thuocTinh = this.parent.lstThuocTinh.find(item => item.ThuocTinh == this.data.ThuocTinh)
                                thuocTinh.GiaTri = newVal
                            }
                        },
                        mounted() {
                            this.value = this.data.GiaTri
                        }
                    }
                }
            }
        },
        btnChapnhan_Click() {

            let giatri = ""
                      
            this.lstThuocTinh.forEach(vttt => {
                if (!!vttt.GiaTri)
                {
                    giatri += vttt.GiaTri + '|';
                } 
            })

            if (giatri.endsWith("|")) {
                giatri = giatri.substr(0, giatri.length - 1);
            }
                
            this.GiaTri = giatri;
            this.ChapNhan = true;

            this.hide()
        },
        tsbtnHuyBo_Click() {
            this.lstThuocTinh.forEach(item => item.GiaTri = null)
            this.grid_Value.DataSource = [...this.lstThuocTinh]
        }
    }
}
</script>