export default {
    get_list_nhomcongdoan: async (axios) => axios.post('web-khdn/quanlykenh/get_list_nhomcongdoan',{}),
    delete_nhomcongdoan: async (axios, nhomcb_id) => axios.post('web-khdn/quanlykenh/delete_nhomcongdoan',{
        p_id_nhomcongdoan:nhomcb_id
    }),

    insert_nhomcongdoan: async (axios, data) => axios.post('web-khdn/quanlykenh/insert_nhomcongdoan',data),
    update_nhomcongdoan: async (axios, data) => axios.post('web-khdn/quanlykenh/update_nhomcongdoan',data),
}