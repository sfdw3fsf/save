function rel_site(){
    var custom_select_box = document.querySelector('.foot_rel_list');
    if(custom_select_box.classList.contains('active')) {
        custom_select_box.classList.remove('active');
    } else {
        custom_select_box.classList.add('active');
    }
}