<template src="./ModalPromise.html" /> 

<script>
export default {
  name: "ModalPromise",
  data() { 
    //console.log("Making data");
    return {
      data: { list: [], title: "", type: ""},
      resolvePromise: null,
      rejectPromise: null,
      cancelPromise: null,
      dataReturn: null, 
    };
  },
  methods: {
    show(data) { 
        if(!data) {
            console.error("Chua co data input") 
        }
        this.status = false;
        this.data = data;
        this.$bvModal.show("modalPromise"); 
      //console.log("`show` called", data);
      return new Promise((resolve, reject) => { 
        this.resolvePromise = (flag, event) => { 
            if(this.data.type === "dataGrid"){
                let seletedRows = this.$refs.gridList.getSelectedRecords();
                if(!seletedRows || seletedRows.length == 0){
                    this.$toast.error("Bạn chưa chọn bản ghi nào!");
                    event.preventDefault();
                    return;
                }
                this.dataReturn = seletedRows;
            }
            if(this.data.type === "form"){ 
                let form = this.$refs.formModalPromise;
                var isvalid = true;
                form.getElementsByTagName('input').forEach((item)=>{
                    if(isvalid && !item.validity.valid) {
                        isvalid = false;
                        this.$toast.error("Yêu cầu nhập " + item.getAttribute('title'));
                        item.focus();
                    }
                });

                if(isvalid == false){                  
                    event.preventDefault();
                    return;
                }
 
                const formData = new FormData(form)
                const data = {}; 
                for (let [key, val] of formData.entries()) {
                    Object.assign(data, {[key]: val})
                }
                this.dataReturn = data; 
            } 
            
            resolve(this.dataReturn);
        }; 
        this.cancelPromise = flag => {
            resolve(null);
        };
        this.rejectPromise = error => {
          //console.log(`Calling reject(${error})`);
          reject(error);
        };
      });
    }, 
     
  }
};
</script>